import {makeAutoObservable} from 'mobx';
import {getTime} from "html-evaluate-utils/DateFormat";
import {TimeoutStatus} from "../utils/CustomTimeout";

export class ClickerState {

    rootStore = null;
    cfg = null;
    timeOuts = [];
    stopAllAction = false;
    currentTimeOut = {status:TimeoutStatus.COMPLETED};
    ///(?=.*like)(?=[\s\S]*1542)/
    
    constructor() {
        makeAutoObservable(this);
    }

    setup(rootStore) {
        this.rootStore = rootStore;
        this.cfg = {
            linkedInLike: {
                root: {
                    logPrefix: "LI_1 ",
                    run: true,
                    log: true,
                    range: [22000, 22540],
                    counter: 0,
                    counterClear: 20,
                    key: 'root',
                    randomize: {
                        interval: 5000,
                        begin: 5000,
                        middle: 6000,
                        end: 10000
                    }
                },
                click: {
                    enable: true
                },
                like: {
                    postIdXPath: "", // //div[contains(@data-id, 'urn:li:activity')]
                    postsXPath: "//div[contains(@data-id, 'urn:li:activity')]", //div[contains(@class,\"social-details-social-activity update-v2-social-activity\")]",
                    postTextXPath: "/div/*/button[contains(string(), \"Like\")]",
                    buttonXPath: "//div/*/button[contains(string(), \"Like\")]", // //span[contains(@class, 'artdeco-button__text react-button__text')]/../../../../button
                    validateElXPath: "/span/*/*",
                    validateValue: "svg",
                    enable: true,
                    run: false,
                    log: false,
                    path: '',
                    key: 'like',
                    validate: true,
                    range: [9056, 10055],
                    counter: 0,
                    wit: {
                        run: true,
                        ruleSet: this.rootStore.ruleState.findRuleSet("like")
                    },
                    paths: {
                        name:        "/../../../..//div[contains(@class, 'update-components-actor')]/div/div/a/span[1]/span/span",
                        description: "/../../../..//div[contains(@class, 'update-components-actor')]/div/div/a/span[2]/span",
                        text: "/../../../..//div[contains(@class, 'update-components-actor')]/div/div/a/span[2]/span",
                        href: "/../../../..//a",
                        dataUrn: "/../../../../.." //"urn:li:activity:67787612312312
                    },
                    interacted: []  
                },
                newPoster: {
                    run: true,
                    log: false,
                    path: '//button/div/span[contains(string(), \"New posts\")]/../..',
                    key: 'newPoster',
                    validate: false,
                    range: [2215, 3215],
                    counter: 0,
                    wit: {
                        run: false,
                        ruleSet: null,
                        href: "/../../..//a"
                    },
                },
                follower: {
                    run: false,
                    log: false,
                    path: '//button/span[text() = \"Follow\"]',
                    key: 'follower',
                    validate: false,
                    range: [9056, 10055],
                    counter: 0,
                    wit: {
                        run: true,
                        ruleSet: this.rootStore.ruleState.findRuleSet("follower")
                    },
                    pathName: '',
                    pathDescription: '',
                    pathText: '',
                    paths: {
                        name: "/../../..//span[contains(@class, 'discover-person-follow-card__name')]",
                        description: "/../../..//span[contains(@class, 'discover-person-follow-card__occupation')]",
                        text: null,
                        href: "/../../..//a",
                        dataUrn: null,
                        company: {
                            name: "/../../..//span[contains(@class, 'discover-company-card__name')]",
                            description: "/../../..//span[contains(@class, 'discover-company-card__name')]",
                            text: null,
                            href: "/../../..//a",
                        } 
                    },
                    interacted: []
                },
                subscriber: {
                    run: true,
                    log: false,
                    path: "//button/span[text() = \"Subscribe\"]",
                    key: 'subscriber',
                    validate: false,
                    range: [9056, 10055],
                    counter: 0,
                    wit: {
                        run: true,
                        ruleSet: this.rootStore.ruleState.findRuleSet("subscriber")
                    },
                    paths: {
                        name: null,
                        description: "/../../..//div[contains(@class, 'discover-series-card__info')]",
                        text: null,
                        href: "/../../..//a"
                    },
                    interacted: []
                },
                accepter: {
                    run: true,
                    log: false,
                    path: '//button/span[text() = \"Accept\"]',
                    key: 'accepter',
                    validate: false,
                    range: [2456, 3875],
                    counter: 0,
                    wit: {
                        run: false,
                        ruleSet: this.rootStore.ruleState.findRuleSet("accepter")
                    },
                    paths: {
                        name: null,
                        description: null,
                        text: null,
                        href: "/../../..//a"
                    },
                    interacted: []
                },
                connector: {
                    run: false,
                    log: false,
                    path: '//button/span[text() = \"Connect\"]',
                    key: 'connector',
                    validate: false,
                    range: [12465, 16123],
                    counter: 0,
                    wit: {
                        run: true,
                        ruleSet: this.rootStore.ruleState.findRuleSet("connector")
                    },
                    paths: {
                        name: "/../../../..//span[contains(@class, 'discover-person-card__name')]",
                        description: "/../../../..//span[contains(@class, 'discover-person-card__occupation')]",
                        text: null,
                        href: "/../../../..//a"
                    },
                    interacted: []
                },
                withdraw: {
                    run: false,
                    log: false,
                    path: '//button/span[text() = \"Withdraw\"]',
                    key: 'withdraw',
                    validate: false,
                    range: [5465, 7523],
                    counter: 0,
                    wit: {
                        run: false,
                        ruleSet: this.rootStore.ruleState.findRuleSet("withdraw")
                    },
                    paths: {
                        name: null,
                        description: null,
                        text: null,
                        href: null
                    },
                    interacted: []
                },
                welcome: {
                    run: false,
                    log: false,
                    path: '//button/span/span[@class = "conversations-quick-replies__reply-content"]/..',
                    validateElXPath: '//li[@class = \'msg-s-message-list__typing-indicator-container--without-seen-receipt\']',
                    key: 'welcome',
                    validate: false,
                    range: [4465, 9523],
                    val: function (params) {

                    },
                    counter: 0
                },
                scroll: {
                    counter: 0
                },
                rootTimeout: 5000,
                rootInterval: null
            },
            panel: {
                consoleOutId: "console_out_id",
                consoleOutTabId: "console_out_tab_id",
                cfgTabId: "cfg_tab_id",
                viewCfgTabId: "view_cfg_tab_id",
                cfgTextareaId: "cfg_textarea_id",
                menuButtonsId: "menu_buttons_id"
            },
            autoClick: {
                enable: true,
            }
        };

    }

    reset(){
        console.log(getTime() + " ClickerState.reset() begin");
        this.cfg.linkedInLike.root.counter = 0;
        if(this.timeOuts && this.timeOuts.length > 0){
            this.timeOuts.forEach((tmOut) => {
                tmOut.cancel();
            });
            this.timeOuts = [];
        }
        console.log(getTime() + " ClickerState.reset() end");
    }
};