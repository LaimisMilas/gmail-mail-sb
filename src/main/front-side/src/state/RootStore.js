// rootStore.js
import {ClickerState} from './ClickerState';
import {CfgPanelState} from './CfgPanelState';
import {RuleState} from './RuleState';
import {LocalStorageManager} from "../store/LocalStorageManager";
import {ScrollState} from "./ScrollState";
import {NavigationState} from "./NavigationState";
import {ClickActionState} from "./ClickActionState";
import {getTime} from "html-evaluate-utils/DateFormat";
import {SessionStorageManager} from "../store/SessionStorageManager";
import {ActorState} from "./ActorState";
export class RootStore {

    actorState = null;
    clickerState = null;
    cfgPanelState = null;
    ruleState = null;
    scrollState = null;
    navigationState = null;
    clickActionState = null;
    prefix = "lc_"
    
    constructor() {
        this.actorState = new ActorState(this);
        this.ruleState = new RuleState(this);
        this.clickerState = new ClickerState(this);
        this.cfgPanelState = new CfgPanelState(this);
        this.scrollState = new ScrollState(this);
        this.navigationState = new NavigationState(this);
        this.clickActionState = new ClickActionState(this);
        this.setupStoreRelationships("RootStore.constructor()");
    }

    setupStoreRelationships(caller) {
        console.log(getTime() + " RootStore.setupStoreRelationships() begin, caller:" + caller);
        this.actorState.setup(this);
        this.ruleState.setup(this);
        this.clickerState.setup(this); // sets default cfg values
        this.cfgPanelState.setup(this);
        this.scrollState.setup(this);
        this.navigationState.setup(this);
        this.clickActionState.setup(this);
        if(LocalStorageManager.getStorage("lc_store_state")){
            this.reverseLoudLocalStorage(caller);
        }else {
            this.initializeLocalStorage(caller);
        }
        
        window.addEventListener('message', (event) => {
            if (event.data.type === 'lc_printCSV') {
                this.actorState.printCSV();
            }
        });

        window.addEventListener('message', (event) => {
            if (event.data.type === 'lc_extension_stop') {
                this.stop();
            }
        });

        window.addEventListener('message', (event) => {
            if (event.data.type === 'lc_extension_run') {
                this.reRun();
            }
        });

        console.log(getTime() + " RootStore.setupStoreRelationships() end, caller:" + caller);
    }
    
    loudLocalStorage(caller) {
        console.log(getTime() + " RootStore.loudLocalStorage() begin, caller:" + caller);
        /** gražinamas rezultatas yra JSON formatu, parsinamas iš string objekto */
        this.actorState.ignoredActor = [...LocalStorageManager.getStorage("lc_ignored_actor"),...this.actorState.ignoredActor];
        this.actorState.friendActor = [...LocalStorageManager.getStorage("lc_friend_actor"),...this.actorState.friendActor];
        this.actorState.actorPostData = [...LocalStorageManager.getStorage("lc_action_data"),...this.actorState.actorPostData];
        this.clickerState.cfg = {...LocalStorageManager.getStorage("lc_cfg"), ...this.clickerState.cfg};
        this.cfgPanelState.rowConfig = {...LocalStorageManager.getStorage("lc_rowConfig"),...this.cfgPanelState.rowConfig};
        this.scrollState.cfg = {...LocalStorageManager.getStorage("lc_scrollCfg"),...this.scrollState.cfg};
        this.navigationState.nav = {...LocalStorageManager.getStorage("lc_navCfg"),...this.navigationState.nav};
        this.cfgPanelState.badge = {...SessionStorageManager.getStorage("lc_badgeLc"),...this.cfgPanelState.badge};
        this.ruleState.ruleSets = [...LocalStorageManager.getStorage("lc_ruleSets"),...this.ruleState.ruleSets];
        this.ruleState.currentIntent = LocalStorageManager.getStorage("lc_currentIntent");
        console.log(getTime() + " RootStore.loudLocalStorage() end, caller:" + caller);
    }

    reverseLoudLocalStorage(caller) {
        console.log(getTime() + " RootStore.reverseLoudLocalStorage() begin, caller:" + caller);
        /** gražinamas rezultatas yra JSON formatu, parsinamas iš string objekto */
        this.actorState.ignoredActor = [...this.actorState.ignoredActor, ...LocalStorageManager.getStorage("lc_ignored_actor")];
        this.actorState.friendActor = [...this.actorState.friendActor, ...LocalStorageManager.getStorage("lc_friend_actor")];
        this.actorState.actorPostData = [...this.actorState.actorPostData, ...LocalStorageManager.getStorage("lc_action_data")];
        this.clickerState.cfg = { ...this.clickerState.cfg, ...LocalStorageManager.getStorage("lc_cfg")};
        this.cfgPanelState.rowConfig = {...this.cfgPanelState.rowConfig, ...LocalStorageManager.getStorage("lc_rowConfig")};
        this.scrollState.cfg = {...this.scrollState.cfg, ...LocalStorageManager.getStorage("lc_scrollCfg")};
        this.navigationState.nav = {...this.navigationState.nav, ...LocalStorageManager.getStorage("lc_navCfg")};
        this.cfgPanelState.badge = {...this.cfgPanelState.badge, ...SessionStorageManager.getStorage("lc_badgeLc")};
        this.ruleState.ruleSets = [...this.ruleState.ruleSets, ...LocalStorageManager.getStorage("lc_ruleSets")];
        this.ruleState.currentIntent = LocalStorageManager.getStorage("lc_currentIntent");
        console.log(getTime() + " RootStore.reverseLoudLocalStorage() end, caller:" + caller);
    }

    saveStorage(caller) {
        console.log(getTime() + " RootStore.saveStorageToLocal() begin, caller:" + caller);
        LocalStorageManager.flash("lc_ignored_actor", this.actorState.ignoredActor);
        LocalStorageManager.flash("lc_friend_actor", this.actorState.friendActor);
        LocalStorageManager.flash("lc_action_data", this.actorState.actorPostData);
        LocalStorageManager.flash("lc_cfg", this.clickerState.cfg);
        LocalStorageManager.flash("lc_rowConfig", this.cfgPanelState.rowConfig);
        LocalStorageManager.flash("lc_scrollCfg", this.scrollState.cfg);
        LocalStorageManager.flash("lc_navCfg", this.navigationState.nav);
        SessionStorageManager.flash("lc_badgeLc", this.cfgPanelState.badge);
        LocalStorageManager.flash("lc_ruleSets", this.ruleState.ruleSets);
        LocalStorageManager.flash("lc_store_state", 1);
        LocalStorageManager.flash("lc_currentIntent", this.ruleState.currentIntent);
        console.log(getTime() + " RootStore.saveStorageToLocal() end, caller:" + caller);
    }
    
    initializeLocalStorage(caller) {
        console.log(getTime() + " RootStore.initializeLocalStorage() begin, caller:" + caller);
        LocalStorageManager.flash("lc_ignored_actor", this.actorState.ignoredActor);
        LocalStorageManager.flash("lc_friend_actor", this.actorState.friendActor);
        LocalStorageManager.flash("lc_action_data", this.actorState.actorPostData);
        LocalStorageManager.flash("lc_cfg", this.clickerState.cfg);
        LocalStorageManager.flash("lc_rowConfig", this.cfgPanelState.rowConfig);
        LocalStorageManager.flash("lc_scrollCfg", this.scrollState.cfg);
        LocalStorageManager.flash("lc_navCfg", this.navigationState.nav);
        SessionStorageManager.flash("lc_badgeLc", this.cfgPanelState.badge);
        LocalStorageManager.flash("lc_ruleSets", this.ruleState.ruleSets);
        LocalStorageManager.flash("lc_store_state", 1);
        LocalStorageManager.flash("lc_currentIntent", this.ruleState.currentIntent);
        console.log(getTime() + " RootStore.initializeLocalStorage() end, caller:" + caller);
    }

    stop(caller) {
        console.log(getTime() + " RootStore.stop() begin, caller:" + caller);
        this.actorState.stopAllAction = true;
        this.clickerState.stopAllAction = true;
        this.cfgPanelState.setStopAllAction(true);
        this.ruleState.stopAllAction = true;
        this.scrollState.stopAllAction = true;
        this.navigationState.stopAllAction = true;
        this.clickActionState.stopAllAction = true;
        console.log(getTime() + " RootStore.stop() stop, caller:" + caller);
    }
    
    reRun(caller){
        console.log(getTime() + " RootStore.reRun() begin, caller:" + caller);
        this.actorState.stopAllAction = false;
        this.clickerState.stopAllAction = false;
        this.cfgPanelState.setStopAllAction(false);
        this.ruleState.stopAllAction = false;
        this.scrollState.stopAllAction = false;
        this.navigationState.stopAllAction = false;
        this.clickActionState.stopAllAction = false;
        console.log(getTime() + " RootStore.reRun() stop, caller:" + caller);
    }
}
