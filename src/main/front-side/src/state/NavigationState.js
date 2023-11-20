import {makeAutoObservable} from 'mobx';
import {getTime} from "html-evaluate-utils/DateFormat";
import {TimeoutStatus} from "../utils/CustomTimeout";

export class NavigationState {

    constructor() {
        makeAutoObservable(this);
    }
    
    rootStore = null;
    nav = null;
    pages = null;
    stopAllAction = false;
    currentTimeOut = {status:TimeoutStatus.COMPLETED};

    setup(rootStore) {
        this.rootStore = rootStore;
        this.pages = {
            feed: "feed",
            network: "network",
            messaging: "messaging",
            jobs: "jobs",
            invitationManager: "invitation-manager"};
        this.nav = {
            root: {
                logPrefix: "SN_1 ",
                postsXPath: "//",
                run: true,
                log: true,
                range: [11000, 16000],
                counter: 0,
                counterClear: 20,
                key: 'root',
            },
            pages: [
                {
                    key: this.pages.feed,
                    path: "https://www.linkedin.com/feed/",
                    xPath: "//nav/ul/li[1]/a",
                    timeOnPage: [231554, 241554],
                    nextPageKey: this.pages.network,
                    currentPage: true,
                    run: true
                },
                {
                    key: this.pages.network,
                    path: "https://www.linkedin.com/mynetwork/",
                    xPath: "//nav/ul/li[2]/a",
                    timeOnPage: [231554, 241554],
                    nextPageKey: this.pages.feed,
                    currentPage: false,
                    run: true
                },
                {
                    key: this.pages.messaging,
                    path: "https://www.linkedin.com/messaging/",
                    xPath: "//nav/ul/li[3]/a",
                    timeOnPage: [111554, 211554],
                    nextPageKey: this.pages.feed,
                    currentPage: false,
                    run: true
                },
                {
                    key: this.pages.jobs,
                    path: "https://www.linkedin.com/jobs/",
                    xPath: "//nav/ul/li[4]/a",
                    timeOnPage: [211554, 351154],
                    nextPageKey: this.pages.feed,
                    currentPage: false,
                    run: true
                },
                {
                    key: this.pages.invitationManager,
                    path: "https://www.linkedin.com/mynetwork/invitation-manager/sent/?invitationType=ORGANIZATION",
                    xPath: "//nav/ul/li[2]/a",
                    steps: ["//span[text() = 'Manage']", "//button[text() = 'Sent']"],
                    timeOnPage: [2554, 3540],
                    nextPageKey: this.pages.feed,
                    currentPage: false,
                    run: true
                }
            ],
            rootTimeout: 60000 * 2,
            rootInterval: null,
            currentPage: this.pages.feed,
            timeOuts: []
        }
    }
    ;

    clearTimeOuts() {
        console.log(getTime() + " SiteNavigation.clearTimeOuts()");
        this.nav.timeOuts.forEach((tmOut) => {
            clearTimeout(tmOut);
        });
        this.nav.timeOuts = [];
        this.rootStore.saveStorageToLocal("SiteNavigation.clearTimeOuts()");
    }

    saveTimeOuts(tmOut) {
        console.log(getTime() + " SiteNavigation.saveTimeOuts() tmOut:" + tmOut);
        this.nav.timeOuts.push(tmOut);
    }

    // Metodas paga duota rakta suranda cfg objekta
// ** @param {String} key cfg objekto raktas    
    getByKeyNavCfg = (key) => {
        console.log(getTime() + " SiteNavigation.getByKeyNavCfg() key: " + key);
        let newArray = this.nav.pages.filter(function (el) {
                return el.key === key;
            }
        );
        console.log(getTime() + " SiteNavigation.getByKeyNavCfg() filter: " + newArray[0].key);
        return newArray[0];
    }

    // Metodas grazina cfg objekta, kuris turi currentPage = true
    // tai yra aktyvu langa
    getCurrentPageNavCfg = () => {
        console.log(getTime() + " SiteNavigation.getCurrentPageNavCfg()");
        let newArray = this.nav.pages.filter(function (el) {
                return el.currentPage;
            }
        );
        console.log(getTime() + " SiteNavigation.getCurrentPageNavCfg() filter: " + newArray[0].key);
        return newArray[0];
    }


    // Metodas suranda kuris puslapis bus sekantis, naudojama rekursija
    // ** @param {Object} navCfg nextPageKey sekancio puslapio cfg raktas
    // ** @param {Object} next sekancio puslapio cfg
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
    const
    getNextCurrentPageNavCfg = (navCfg) => {
        console.log(getTime() + " NavigationState.getNextCurrentPageNavCfg()");
        let next = this.getByKeyNavCfg(navCfg.nextPageKey);
        if (!next.run) {
            next = this.getNextCurrentPageNavCfg(next);
        }
        return next;
    }

}
