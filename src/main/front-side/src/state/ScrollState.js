import {makeAutoObservable} from 'mobx';
import {TimeoutStatus} from "../utils/CustomTimeout";

export class ScrollState {
    
    cfg = null;
    stopAllAction = false;
    currentTimeOut = {status:TimeoutStatus.COMPLETED};
    constructor() {
        makeAutoObservable(this);
    }

    setup(rootStore) {
        this.rootStore = rootStore;
        this.cfg = {
            root: {
                logPrefix: "S_1 ",
                run: true,
                log: true,
                key: "root",
                range: [5000, 8000],
                counter: 5,
                counterClear: 20,
                randomize: {
                    interval: 5000,
                    begin: 5000,
                    middle: 6000,
                    end: 10000
                }
            },
            scroll: {
                run: true,
                log: true,
                key: "scroll",
                scrollerDown: [111, 444]
            },
            rootTimeout: 5000,
            rootInterval: null
        }
    }

    badge = {
        scroll: 0
    }

    updateBadge(fieldName, value) {
        this.badge[fieldName] = value;
    }
}