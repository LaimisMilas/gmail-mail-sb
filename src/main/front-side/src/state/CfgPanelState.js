import {DateFormat} from "html-evaluate-utils/DateFormat";
import { makeAutoObservable} from "mobx";

export class CfgPanelState {

    rootStore = null;

    rowConfig = {};

    badge = {};

    active = {}

    stopAllAction = false;

    constructor() {
        makeAutoObservable(this);
    }
    
    setStopAllAction(stopAllAction) {
        this.stopAllAction = stopAllAction;
    }

    setup(rootStore) {
        this.rootStore = rootStore;
        this.initializeRowConfig();
    }

    initializeRowConfig() {
        this.rowConfig = {
            newPoster: {
                checkValue: this.rootStore.clickerState.cfg.linkedInLike.newPoster.run,
                label: "New poster",
                id: "newPoster_id",
                name: "newPoster_name",
                key: "newPoster"
            },
            like: {
                checkValue: this.rootStore.clickerState.cfg.linkedInLike.like.run,
                label: "Liker" ,
                id: "like_id",
                name: "like_name",
                key: "like"
            },
            follower: {
                checkValue: this.rootStore.clickerState.cfg.linkedInLike.follower.run,
                label: "Follower",
                id: "follower_id",
                name: "follower_name",
                key: "follower"
            },
            subscriber: {
                checkValue: this.rootStore.clickerState.cfg.linkedInLike.subscriber.run,
                label: "Subscriber",
                id: "subscriber_id",
                name: "subscriber_name",
                key: "subscriber"
            },
            accepter: {
                checkValue: this.rootStore.clickerState.cfg.linkedInLike.accepter.run,
                label: "Accepter" ,
                id: "saccepter_id",
                name: "accepter_name",
                key: "accepter"
            },
            connector: {
                checkValue: this.rootStore.clickerState.cfg.linkedInLike.connector.run,
                label: "Connector",
                id: "connector_id",
                name: "connector_name",
                key: "connector"
            },
            scroll: {
                checkValue : true,
                label: "Scroller" ,
                id: "scroll_id",
                name: "scroll_name",
                key: "scroll"
            }
        };
        this.badge = {
            newPoster: 0,
            like: 0,
            follower: 0,
            subscriber: 0,
            accepter: 0,
            connector: 0
        };
        this.active = {
            fromDate: DateFormat.formatDate(new Date()),
            from: Date.now(),
            timeDiff: 0
        }
        this.stopAllAction = false;
    }

    updateBadge(fieldName, value) {
        this.badge[fieldName] = value;
    }

    updateTimeDiff() {
        this.active.timeDiff = DateFormat.calculateTimeDifferenceInMinutes(Date.now(), this.active.from);
    }

    updateRowConfigCheckValue(fieldName, newValue) {
        this.rowConfig[fieldName].checkValue = newValue;
    }
    
    handleStopButtonClick(stop) {
        this.rootStore.clickerState.cfg.linkedInLike.root.run = stop === true;
        this.rootStore.scrollState.cfg.root.run = stop === true;
        this.rootStore.navigationState.nav.root.run = stop === true;
    }

    getIsActionsStop() {
        return this.rootStore.clickerState.cfg.linkedInLike.root.run === false;
    }
}
