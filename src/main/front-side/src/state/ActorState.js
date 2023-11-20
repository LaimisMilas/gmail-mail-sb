import {Utils} from "html-evaluate-utils/Utils";
import {compareTextLD} from "../utils/Text";
import {makeAutoObservable} from "mobx";
import {getDate, getTime} from "html-evaluate-utils/DateFormat";

export class ActorState {
    
    rootStore = null;
    ignoredActor = [];
    friendActor = [];
    actorPostData = [];
    stopAllAction = false;

    constructor() {
        makeAutoObservable(this);
    }

    setup(rootStore) {
        this.rootStore = rootStore;
        this.ignoredActor = [];
        this.friendActor = [];
        this.actorPostData = [];
        this.ignoredActor = [
            "remigijus bauzys",
            "mantas sasnauskas",
            "marius lapukas",
            "Donagh Kiernan"
        ];
        this.friendActor = [
            "Rima Barusaite",
            "Monika Mazaliauskaitė",
            "Justė Janulytė"
        ]
    }
    
    isNameOnIgnoreList = (element, cfg, parentId, childId, callFrom) => {
        console.log(getTime() + " ActorState.isNameOnIgnoreList() begin, cfg:" + cfg.key + ", parentId:" + parentId + ", childId:" + childId + ", callFrom:" + callFrom);
        let result = false;
        const rootXPath = Utils.getXPathByEl(element);
        let name = this.getActorName(rootXPath, cfg);
        let description = this.getActorDescription(rootXPath, cfg);
        if (name && description && cfg.interacted && !cfg.interacted.includes(description)) {
            this.ignoredActor.forEach((ignoredActor) => {
                if (compareTextLD(name, ignoredActor) > 95) {
                    result = true;
                    cfg.interacted.push(description);
                }
            });
        }
        console.log(getTime() + " ActorState.isNameOnIgnoreList() end, result:" + result + ", name:" + name + ", cfg:" + cfg.key + ", parentId:" + parentId + ", childId:" + childId + ", callFrom:" + callFrom);
        return result;
    }

    isNameOnFriendList = (element, cfg, parentId, childId, callFrom) => {
        console.log(getTime() + " ActorState.isNameOnFriendList() begin, cfg:" + cfg.key + ", parentId:" + parentId + ", childId:" + childId + ", callFrom:" + callFrom);
        let result = false;
        const rootXPath = Utils.getXPathByEl(element);
        let name = this.getActorName(rootXPath, cfg);
        let description = this.getActorDescription(rootXPath, cfg);
        if (name && description && cfg.interacted && !cfg.interacted.includes(description)) {
            this.friendActor.forEach((friendActor) => {
                if (compareTextLD(name, friendActor) > 95) {
                    result = true;
                    cfg.interacted.push(description);
                }
            });
        }
        console.log(getTime() + " ActorState.isNameOnFriendList() end, result:" + result + ", name:" + name + ", cfg:" + cfg.key + ", parentId:" + parentId + ", childId:" + childId + ", callFrom:" + callFrom);
        return result;
    }
    
    getActorName = (rootXPath, cfg) => {
        let name = null;
        if(cfg.paths && cfg.paths.name && Utils.getElByXPath(rootXPath + cfg.paths.name)){
            name = Utils.getElByXPath(rootXPath + cfg.paths.name).innerText;
        }
        return name;
    }
    
    getActorDescription = (rootXPath, cfg) => {
        let description = null;
        if(cfg.paths && cfg.paths.description && Utils.getElByXPath(rootXPath + cfg.paths.description)) {
            description = Utils.getElByXPath(rootXPath + cfg.paths.description).innerText;
        }
        return description;
    }

    getActorHref = (rootXPath, cfg) => {
        let href = null;
        if(cfg.paths && cfg.paths.href && Utils.getElByXPath(rootXPath + cfg.paths.href)) {
            href = Utils.getElByXPath(rootXPath + cfg.paths.href).href;
        }
        return href;
    }

    getActorDataUrn = (rootXPath, cfg) => {
        let dataUrn = null;
        if(cfg.paths && cfg.paths.dataUrn && Utils.getElByXPath(rootXPath + cfg.paths.dataUrn)) {
            dataUrn = Utils.getElByXPath(rootXPath + cfg.paths.dataUrn).getAttribute("data-urn");
        }
        return dataUrn;
    }

    storePost = (element, cfg, parentId, responseIntent) => {
        console.log(getTime() + " ActorState.storePost(" + cfg.key + ") begin, parentId: " + parentId);

        const elementXPath = Utils.getXPathByEl(element);

        if (elementXPath) {
            try {
                const name = this.getActorName(elementXPath, cfg);
                const description = this.getActorDescription(elementXPath, cfg);
                const href = this.getActorHref(elementXPath, cfg);
                const dataUrn = this.getActorDataUrn(elementXPath, cfg);
                const data =
                      " " + getDate() + " " + getTime()
                    + ", " + (name ? name.replaceAll(",", "").replaceAll("\"", "") : null)
                    + ", \"" + (description ? description.replaceAll(",", "").replaceAll("\"", "") : null) + "\""
                    + ", " + (href ? href : null)
                    + ", " + (dataUrn ? dataUrn : null)
                    + ", " + cfg.key
                    + ", " + (responseIntent ? responseIntent.name : null)
                    + ", " + (responseIntent ? responseIntent.confidence : null)
                    + ", " + parentId + "";

                console.log(getTime() + " ActorState.storePost(" + cfg.key + ")" + data);
                if (this.actorPostData) {
                    this.actorPostData.push(data);
                }
            } catch (e) {
                console.log(getTime() + " ActorState.storePost(" + cfg.key + ") e:" + e);
            }
        }

        // document.evaluate('//button/span[text() = \"Follow\"]/../../..//a', document, null, XPathResult.UNORDERED_NODE_ITERATOR_TYPE, null).iterateNext().href;
        console.log(getTime() + " ActorState.storePost(" + cfg.key + ") end, parentId: " + parentId);
    }
    
    
    // window.postMessage({ type: 'lc_printCSV'});
    printCSV = () => {
        console.log(getTime() + " ActorState.printCSV() begin");
        const header = "\"date\",\"name\",\"description\",\"href\",\"data-urn\",\"key\",\"intent\",\"intent-confidence\",\"parentId\"\n";
        let csv = ""    
        if (this.actorPostData) {
            this.actorPostData.forEach((data) => {
                csv += data + "\n";
            });
        }
        console.log(header + csv);
        console.log(getTime() + " ActorState.printCSV() end");
    }
    
    
}