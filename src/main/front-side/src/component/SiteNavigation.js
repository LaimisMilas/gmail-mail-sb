import {inject, observer} from "mobx-react";
import {useEffect} from "react";
import {Utils} from "html-evaluate-utils/Utils";
import {getTime} from "html-evaluate-utils/DateFormat";
import {CustomTimeout, TimeoutStatus} from "../utils/CustomTimeout";

/**
 Klase SiteNavigation pagrindinis tikslas yra pakeisti puslapiu pagal konfiguracija(cfg)
 changePath() metodas pasiledzia su timeoutu, kuris yra nurodytas cfg objekte. Baigus
 darba nustatomas cfg.nav.siteNavigation.run = true ir kada pasileidzia vel run() metodas, sukuriamas
 naujas timeoutas, kur bus paleistas changePath() metodas.
 run() metodas pasileidziamas is cfg.nav.rootInterval, kuris yra nustatomas cfg objekte,
 index.js faile cfg.nav.rootInterval inicijuojamas, kai pasileidzia index.js.
 zodis cfg - yra objektas, json formato, kuris yra importuojamas is Cfg.js failo.
 **/
const SiteNavigation =  inject("navigationState")(observer(({ navigationState }) => {


    const stepClick = (button) => {
        if (button) {
            button.click();
        }
    }

    //** @param {Object} navCfg 
    //** @param {string} navCfg.xPath path to element
    //** @param {Object} this buttonToClick is element to click
    //** @param {boolean} navCfg currentPage is current page
    //** @param {string} next is cfg of next page
    /**
     {
     key: "messaging",
     path: "https://www.linkedin.com/messaging/",
     xPath:"//nav/ul/li[3]/a",
     timeOnPage: [111554, 211554],
     nextPageKey: "invitation-manager",
     currentPage: false,
     run: false
     }
     **/
    const changePath = (navCfg) => {
        console.log(getTime() + " SiteNavigation.changePath() begin, navCfg:" + navCfg.key);
        if(!navCfg.run){
            console.log(getTime() + " SiteNavigation.changePath() end");
            return;
        }
        let buttonToClick = Utils.getElByXPath(navCfg.xPath);
        if (buttonToClick) {
            buttonToClick.click();

            console.log(getTime() + " SiteNavigation.changePath() -> clickerState.reset()");
            navigationState.rootStore.clickerState.reset();
            
            navigationState.nav.currentPage = navCfg.key;
            navCfg.currentPage = false;
            let next = navigationState.getNextCurrentPageNavCfg(navCfg);
            console.log(getTime() + " SiteNavigation.changePath() next:" + next.key + " run:" + next.run);
            if (next && next.run) {
                next.currentPage = true;
            }
        }
        console.log(getTime() + " SiteNavigation.changePath() end");
    }

    const siteNavRun = () => {
        console.log(getTime() + " SiteNavigation.siteNavRun() begin");
        if (!navigationState.nav.root.run || navigationState.stopAllAction) {
            console.log(getTime() + " SiteNavigation.siteNavRun() end, nav.root.run:" + navigationState.nav.root.run);
            return;
        }
        if (
            !navigationState.nav.rootInterval ||
            navigationState.nav.root.counter > navigationState.nav.root.counterClear
        ) {
            console.log(getTime() + " SiteNavigation.siteNavRun() -> reset()");
            reset();
        } else {
            
            let navCfg = navigationState.getCurrentPageNavCfg();
            let timeOut = Utils.getRandomValue(navCfg.timeOnPage);
            if(navigationState.currentTimeOut && navigationState.currentTimeOut.status === TimeoutStatus.COMPLETED){
                navigationState.currentTimeOut = new CustomTimeout(changePath, timeOut, navCfg);
                navigationState.nav.root.counter++;
                console.log(getTime() + " SiteNavigation.siteNavRun() -> changePath() customTimeout: " + navigationState.currentTimeOut.timeoutId + ", after: " + timeOut + "ms");
            } else{
                console.log(getTime() + " SiteNavigation.siteNavRun() -> currentTimeOut is active, elapsedTime: " + navigationState.currentTimeOut.triggerAfter());
            }
            //console.log(getTime() + " SiteNavigation.run -> changePath(), counter:" + navigationState.nav.root.counter);
            //let navCfg = navigationState.getCurrentPageNavCfg();
            //navigationState.saveTimeOuts(setTimeout(changePath.bind(this), timeOut, navCfg));
            
        }
        console.log(getTime() + " SiteNavigation.siteNavRun() end");
    };

    const reset = () => {
        console.log(getTime()  + " SiteNavigation.reset() begin");
        navigationState.nav.root.counter = 0;
        navigationState.nav.rootTimeout = Utils.getRandomValue(navigationState.nav.root.range);
        console.log(getTime()  + " SiteNavigation.reset() end");
    };

    useEffect(() => {
        console.log(getTime()  + " SiteNavigation.useEffect() rootTimeout:" + navigationState.nav.rootTimeout + "ms");
        navigationState.nav.rootInterval = setInterval(siteNavRun, navigationState.nav.rootTimeout);
    }, []);
    
}));
 
export default SiteNavigation;