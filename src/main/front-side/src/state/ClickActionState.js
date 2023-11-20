import {makeAutoObservable} from 'mobx';
import {getTime} from "html-evaluate-utils/DateFormat";
import {Utils} from "html-evaluate-utils/Utils";
import {validate} from "../utils/Validator";
import {directWitCall} from "../wit/callWit";
import {CustomTimeout, TimeoutStatus} from "../utils/CustomTimeout";

export class ClickActionState {

    cfg = null;
    rootStore = null; 
    timeOuts = new Map();
    stopAllAction = false;
    
    constructor() {
        makeAutoObservable(this);
    }

    setup(rootStore) {
        this.rootStore = rootStore;
        this.cfg = {};
    }
    
    hasActiveTimeOut = (key, parentId) => {
        console.log(getTime() + " ClickAction.hasActiveTimeOut(" + key + ") begin, parentId: " + parentId);
        let result = false;
        if(this.timeOuts.has(key)){
            this.timeOuts.get(key).forEach((customTimeout) => {
                if(customTimeout.status !== TimeoutStatus.COMPLETED){
                    result = true;
                }
            });
        }
        console.log(getTime() + " ClickAction.hasActiveTimeOut(" + key + ") 🔚️, parentId: " + parentId + ", result: " + result);
        return result;
    }

    clearTimeOuts = (key) => {
        if(this.timeOuts.get(key)){
            this.timeOuts.get(key).clear();
        }
    }
    
    doClearTimeOuts = (key, parentId) => {
        let result = false;
        if(this.hasActiveTimeOut(key, parentId)){
            console.log(getTime() + " ClickAction.doClearTimeOuts(" + key + ") hasActiveTimeOut = true, parentId: " + parentId);
            result = true;
        } else {
            console.log(getTime() + " ClickAction.doClearTimeOuts(" + key + ") hasActiveTimeOut = false, parentId: " + parentId + ", call clearTimeOuts()");
            this.clearTimeOuts(key);
        }
        return result;
    }

    clickByCfg = (cfg, callback, parentId) => {
        console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") begin, parentId: " + parentId);
        let hasActiveTimeOut = this.doClearTimeOuts(cfg.key, parentId);
        console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") parentId: " + parentId + ", doClick: " + hasActiveTimeOut + ", cfg.run: " + cfg.run);
        if (!hasActiveTimeOut && cfg.run) {
            let timeOut = Utils.getRandomValue(cfg.range);
            let elements = Utils.myXPath(cfg.path);
            if (elements) {
                console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") elements:"+ (elements) +", parentId: " + parentId);
                let element = elements.iterateNext();
                console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") element:"+ (element) +", parentId: " + parentId);
                while (element) {
                    try {
                        let isInIgnoreList = this.rootStore.actorState.isNameOnIgnoreList(element, cfg, parentId, null, "clickByCfg");
                        let isInFriendList = false;
                        if (isInIgnoreList) {
                            console.log(getTime() + " ClickAction.validateIsNameOnIgnoreList(" + cfg.key + ") return TRUE, parentId:" + parentId);
                        } else {
                            isInFriendList = this.rootStore.actorState.isNameOnFriendList(element, cfg, parentId, null, "clickByCfg");
                        }
                        if (!isInIgnoreList) {
                            if (!isInFriendList && cfg.wit.run) {
                                console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") wit.run, parentId:" + parentId);
                                let ct;
                                if (this.timeOuts.has(cfg.key)) {
                                    ct = new CustomTimeout(this.doClickElement, timeOut, element, cfg, callback, parentId);
                                    console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") wit.run, push CT, parentId:" + parentId + ", childId: " + ct.timeoutId);
                                    this.timeOuts.get(cfg.key).push(ct);
                                } else {
                                    ct = new CustomTimeout(this.doClickElement, timeOut, element, cfg, callback, parentId);
                                    console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") wit.run, new CT parentId:" + parentId + ", childId: " + ct.timeoutId);
                                    this.timeOuts.set(cfg.key, [ct]);
                                }
                            } else {
                                console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") direct click, isInFriendList: " + isInFriendList + " parentId: " + parentId);
                                new CustomTimeout(this.directAutoClick, timeOut, element, cfg, callback, parentId);
                            }
                            timeOut += timeOut;
                        }else {
                            console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") isInIgnoreList, parentId:" + parentId);
                        }
                        element = elements.iterateNext();
                    } catch (e) {
                        element = null;
                        console.log(getTime() + " Cli ckAction.clickByCfg(" + cfg.key + ") e:" + e + ", parentId: " + parentId);
                    }
                }
            }
        }
        console.log(getTime() + " ClickAction.clickByCfg(" + cfg.key + ") 🔚️, parentId: " + parentId);
    }

    directAutoClick = (element, cfg, callback, parentId) => {
        console.log(getTime() + " ClickAction.directAutoClick(" + cfg.key + ") begin, parentId: " + parentId);
        if (this.autoClick(element)) {
            this.rootStore.actorState.storePost(element, cfg, parentId, null);
            callback(cfg.key, true, parentId);
        }
        console.log(getTime() + " ClickAction.directAutoClick(" + cfg.key + ") 🔚️, parentId: " + parentId);
    }
    
    doClickElement = (element, cfg, callback, parentId, childId) => {
        console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") begin, parentId: " + parentId + ", childId: " + childId);
        if (element && validate(element, cfg)) {
            console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") element valid👌️, parentId: " + parentId + ", childId: " + childId);
            let elHoldText = Utils.getElementXPath(element);
            if(cfg.wit.ruleSet){
                let rule = null;
                // surasti taisykles kaip kreiptis į wit
                //rule = cfg.wit.ruleSet.rules[0];
                rule = this.rootStore.ruleState.findCurrentRuleSetByKey(cfg.key);
                if(rule){
                    // nustatyti is kur paimti teksta ir perduoti i wit
                    let path = cfg.paths[rule.ruleTarget];
                    let href = this.rootStore.actorState.getActorHref(elHoldText, cfg);
                    if(href && href.includes("company")){
                        path =  cfg.paths.name;
                    }
                    elHoldText = Utils.getElByXPath(elHoldText + path);
                }
            }
            if (elHoldText && elHoldText.innerText.length > 0) {
                console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") elHoldText: " + elHoldText.innerText  + ", parentId: " + parentId + ", childId: " + childId);
                let textToWit = elHoldText.innerText.substring(0, 280);
                if(cfg.interacted && !cfg.interacted.includes(textToWit)){
                    console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") directWitCall prepare call textToWit: " + textToWit + ", parentId: " + parentId + ", childId: " + childId);
                    directWitCall(textToWit, this.witMessageCallBack, element, cfg, callback, parentId, childId);
                    console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") directWitCall called, parentId: " + parentId + ", childId: " + childId);
                    cfg.interacted.push(textToWit);
                } else {
                    console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") interacted🥝️, textToWit: " + textToWit + ", parentId: " + parentId + ", childId: " + childId);
                }
            } else {
                console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") elHoldText not found, parentId: " + parentId + ", childId: " + childId);
            }
        } else {
            console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") param element not found, parentId: " + parentId + ", childId: " + childId);
        }
        console.log(getTime() + " ClickAction.doClickElement(" + cfg.key + ") 🔚️, parentId: " + parentId + ", childId: " + childId);
    }
    
    witMessageCallBack = (xhttp, element, cfg, callback, parentId, childId) => {
        console.log(getTime() + " ClickAction.witMessageCallBack(" + cfg.key + ") begin, parentId: " + parentId + ", childId: " + childId);
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            let responseIntent = JSON.parse(xhttp.responseText).intents[0];
            if(responseIntent) {
                console.log(getTime() + " ClickAction.witMessageCallBack(" + cfg.key + ") " + xhttp.responseText + ", parentId: " + parentId + ", childId: " + childId);
                if (cfg.wit.run && cfg.wit.ruleSet) {
                    this.witResponseApplyRule(element, cfg, responseIntent, callback, parentId);
                } else {
                    this.witResponseAutoClick(element, cfg, responseIntent, callback, parentId);
                }
            } else {
                console.log(getTime() + " ClickAction.witMessageCallBack(" + cfg.key + ") responseIntent not found, parentId: " + parentId + ", childId: " + childId);
            }
        } else {
            console.log(getTime() + " ClickAction.witMessageCallBack(" + cfg.key + ") xhttp.readyState:" + xhttp.readyState + " xhttp.status:" + xhttp.status + ", parentId: " + parentId + ", childId: " + childId);
        }
        console.log(getTime() + " ClickAction.witMessageCallBack(" + cfg.key + ") 🔚️, parentId: " + parentId + ", childId: " + childId);
    }

    witResponseApplyRule = (element, cfg, responseIntent,  callback, parentId) => {
        console.log(getTime() + " ClickAction.witResponseApplyRule(" + cfg.key + ") 🔚️, parentId: " + parentId);
        let rule;
        //ule = cfg.wit.ruleSet.rules[0];
        rule = this.rootStore.ruleState.findCurrentRuleSetByKey(cfg.key, responseIntent);
        if(rule){
            console.log(getTime() + " ClickAction.witResponseApplyRule(" + cfg.key + "), rule.ruleIntent: " + rule.ruleIntent);
            console.log(getTime() + " ClickAction.witResponseApplyRule(" + cfg.key + "), responseIntent.name: " + responseIntent.name);
            if(responseIntent &&  rule.ruleIntent === responseIntent.name){
                this.witResponseAutoClick(element, cfg, responseIntent, rule, callback, parentId);
            }
        }
    }
    
    witResponseAutoClick = (element, cfg, responseIntent, rule, callback, parentId) => {
        console.log(getTime() + " ClickAction.witResponseAutoClick(" + cfg.key + "), parentId: " + parentId);
        console.log(getTime() + " ClickAction.witResponseAutoClick(" + cfg.key + "), responseIntent: " + responseIntent);
        console.log(getTime() + " ClickAction.witResponseAutoClick(" + cfg.key + "), rule.confidence: " +  rule.confidence);
        if (responseIntent && responseIntent.confidence >= rule.confidence) {
            if (this.autoClick(element)) {
                this.rootStore.actorState.storePost(element, cfg, parentId, responseIntent);
                callback(cfg.key, true, parentId);
            }
        }
    }

    /** doClickLike(cfg)
     * Suranda postų srauta(sąrašą) ir kiekvienam sukuria Timeout'ą su skirtingu laiku.
     * Kreipiasi į metodą "doClickLikeByElement" ir perduoda elementą ir cfg.
     * @param {Object} cfg - konfigūracijos objektas.
     cfg:{
     run: true,
     range: [1000, 2000],
     postsXPath: "//div]",
     buttonXPath: "svg"
     validateElXPath: "/span\/*\/*",
     validateValue: "svg"
     }
     */
    /**
     * Performs automatic liking of LinkedIn posts based on provided configuration.
     *
     * @param {object} cfg - The configuration object containing various settings.
     * @param {boolean} cfg.run - Indicates whether the liking process should run.
     * @param {number[]} cfg.range - The range for generating random time intervals.
     * @param {string} cfg.postsXPath - XPath expression to locate LinkedIn posts.
     * @throws {Error} If an error occurs while executing the like action on a post.
     * cfg:{
     *      run: true,
     *      range: [1000, 2000],
     *      postsXPath: "//div]",
     *      buttonXPath: "svg"
     *      validateElXPath: "/span\/*\/*",
     *      validateValue: "svg"
     *      }
     */
    doClickLike = (cfg, callback, parentId) => {
        console.log(getTime() + " ClickAction.doClickLike(" + cfg.key + ") begin, parentId: " + parentId);
        /**
         * Executes the like action on a specific post element.
         *
         * @param {HTMLElement} element - The DOM element representing a LinkedIn post.
         * @param {object} cfg - The configuration object.
         */
        // kontruolioja like veikima taip/ne
        if (!cfg.run) {
            return;
        }
        // randome skaičius is rėžio "cfg.linkedInLike.like.range"
        let timeOut = Utils.getRandomValue(cfg.range);
        // surandame elementus pagal "cfg.linkedInLike.root.postsXPath"
        // postsXPath: "//div[contains(@class,\"social-details-social-activity update-v2-social-activity\")]",
        /** //div[contains(@data-id, 'urn:li:activity')] butu geriau nes jis turi unikalu id pagal kuri
         * galima butu patikrinti ar jau buvo paspaustas like.
         *
         * document.evaluate("//div[contains(@data-id, 'urn:li:activity')]", document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null).snapshotItem(2);
         */
        let elements = Utils.myXPath(cfg.postsXPath);
        if (elements) {
            // elementas is saraso cia ne buttonas, cia visas blokas
            let element = elements.iterateNext();
            while (element) {
                try {
                    // timeout'as metodui "doClickLikeByElement"
                    // timeOut - laikas
                    // element - ia visas blokas su visais mygtukais
                    const ctId = new CustomTimeout(this.doLikeElement, timeOut, element, cfg, callback, parentId);
                    console.log(getTime() + " ClickAction.doClickLike(" + cfg.key 
                        + ") begin, parentId: " + parentId 
                        + ", childId: " + ctId.timeoutId 
                        + ", triggerAfter: " + ctId.triggerAfter()
                        + ", timeOut: " + timeOut
                    );
                    // elementas sekantis is saraso
                    element = elements.iterateNext();
                    // timeOut - laikas + random skaičius is rėžio "cfg.linkedInLike.like.range"
                    // reikia kad nebutu visi laikai vienodi ir būtų tarpas tarp laikų
                    timeOut += Utils.getRandomValue(cfg.range);
                } catch (e) {
                    element = null;
                }
            }
        }
    }

    /** doLikeElement(element, cfg)
     * @param {Object} element - DOM elementas, visas blogas su buttonais.
     * @param {Object} cfg - konfigūracijos objektas.
     cfg:{
     buttonXPath: "svg"
     validateElXPath: "/span\/*\/*",
     validateValue: "svg"
     }
     */
    doLikeElement = (element, cfg, callback, parentId, childId) => {
        console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") begin, parentId: " + parentId + ", childId: " + childId);
        let isInFriendList = false;
        let isInIgnoreList = false;
        let elHoldText = null;
        let rule = null;
        let textToWit = null;
        let path = null;
        let href = null;
        let name = null;
        let dataUrn = null;
        let elementXPath = Utils.getXPathByEl(element);
        if(!elementXPath){
            console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") elementXPath not found, parentId: " + parentId + ", childId: " + childId);
            return;
        }
        // buna sukurta struktura bet ne visa tik konteineris, cia kada nebuvo srolinimo.
        let button = Utils.getElByXPath(elementXPath + cfg.buttonXPath);
        if(!button){
            console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") button not found, parentId: " + parentId + ", childId: " + childId);
            return;
        }
        let buttonXPath = Utils.getElementXPath(button);
        if(!buttonXPath){
            console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") buttonXPath not found, parentId: " + parentId + ", childId: " + childId);
            return;
        }
        isInIgnoreList = this.rootStore.actorState.isNameOnIgnoreList(button, cfg, parentId, childId, "doLikeElement");
        if(isInIgnoreList){
            console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") isNameOnIgnoreList return TRUE, parentId:" + parentId);
            button = null;
        } else {
            isInFriendList = this.rootStore.actorState.isNameOnFriendList(button, cfg, parentId, childId, "doLikeElement");
        }
        if (!isInIgnoreList && button && validate(button, cfg)) {
            if (!isInFriendList && cfg.wit.run) {
                elHoldText = Utils.getElementXPath(button);
                if(cfg.wit.ruleSet){
                    // surasti taisykles kaip kreiptis į wit
                    // rule = cfg.wit.ruleSet.rules[0];
                    rule = this.rootStore.ruleState.findCurrentRuleSetByKey(cfg.key);
                    if(rule){
                        // nustatyti is kur paimti teksta ir perduoti i wit
                         path = cfg.paths[rule.ruleTarget];
                         href = this.rootStore.actorState.getActorHref(elHoldText, cfg);
                         name = this.rootStore.actorState.getActorName(elHoldText, cfg);
                         dataUrn = this.rootStore.actorState.getActorDataUrn(elHoldText, cfg);
                        if(href && href.includes("company")){
                            path =  cfg.paths.name;
                        }
                        elHoldText = Utils.getElByXPath(elHoldText + path);
                    }
                }
                if (elHoldText && elHoldText.innerText.length > 0) {
                    textToWit = elHoldText.innerText.substring(0, 280);
                    if(cfg.interacted && !cfg.interacted.includes(dataUrn)){
                        console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") elHoldText not interacted -> directWitCall(), parentId: " + parentId + ", childId: " + childId);
                        directWitCall(textToWit, this.witMessageCallBack, button, cfg, callback, parentId, childId);
                        cfg.interacted.push(dataUrn);
                    } else {
                        console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") interacted🥝️, textToWit: " + textToWit + ", parentId: " + parentId + ", childId: " + childId);
                    }
                } else {
                    console.log(getTime() + " ClickAction.doLikeElement(" + cfg.key + ") elHoldText not found, parentId: " + parentId + ", childId: " + childId);
                }

            } else {
                if (this.autoClick(element)) {
                    this.rootStore.actorState.storePost(element, cfg, parentId, null);
                    callback(cfg.key, true, parentId);
                }
            }
        }
        console.log(getTime() 
            + " ClickAction.doLikeElement(" + cfg.key + ") end, parentId: " + parentId 
            + ", childId: " + childId 
            + ", isInIgnoreList: " + isInIgnoreList 
            + ", isInFriendList: " + isInFriendList
            + ", button: " + button
            + ", elHoldText: " + elHoldText
            + ", textToWit: " + textToWit
            + ", href: " + href
            + ", path: " + path
            + ", name: " + name
            + ", dataUrn: " + dataUrn);
    }

    /**
     * xpth iki pranešimo href nuorodos kur galima patikrinti ar yra tekstas "company"
     * id(\"ember1222\")/../../../..//div[contains(@class, 'update-components-actor')]/div/div/a
     * xpth iki pranešimo teksto
     "id(\"ember1222\")/../../../..//div[contains(@class, 'update-components-actor')]/div/div/a/span[2]/span"
     document.evaluate("id(\"ember3529\")/../../../..//div[contains(@class, 'update-components-actor')]/div/div/a", document, null, XPathResult.UNORDERED_NODE_ITERATOR_TYPE, null).iterateNext().href.includes("in");
     */

    /**
     * Simulates a click event on the provided DOM element if auto-click is enabled.
     *
     * @param {Object} buttonToClick - The DOM element to be clicked.
     * @returns {boolean} - Indicates whether the click event was successfully triggered.
     */
    autoClick = (buttonToClick) => {
        /**
         * Tries to simulate a click event on the provided DOM element.
         *
         * @throws {Error} If an error occurs while trying to simulate the click event.
         */
        let result = false;
        console.log(getTime() + " ClickAction.autoClick() begin");
        try {
            console.log(getTime() + " ClickAction.autoClick() begin, buttonToClick:" + Utils.getElementXPath(buttonToClick));
            if (buttonToClick) {
                buttonToClick.focus();
                buttonToClick.click();
                result = true;
            }
        } catch (e) {
            console.log(e.stack);
        }
        console.log(getTime() + " ClickAction.autoClick() end");
        return result;
    }
    
}