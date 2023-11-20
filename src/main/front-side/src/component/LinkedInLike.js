import {inject, observer} from "mobx-react";
import {useEffect} from "react";
import {Utils} from "html-evaluate-utils/Utils";
import {getTime} from "html-evaluate-utils/DateFormat";
import {CustomTimeout, TimeoutStatus} from "../utils/CustomTimeout";

const LinkedInLike =  inject("clickerState", "navigationState", "cfgPanelState", "clickActionState" )(observer(({clickerState, navigationState, cfgPanelState, clickActionState}) => {
    
    const runAll = (counter) => {
        let cfg = clickerState.cfg.linkedInLike;
        const parentId = clickerState.currentTimeOut.timeoutId;
        console.log(getTime() + " LinkedInLike.runAll(" + cfg.key + ") begin, parentId:" + parentId + ", counter:" + counter);
        if(cfg.newPoster.run  && navigationState.nav.currentPage === navigationState.pages.feed){
            let ct = new CustomTimeout(clickActionState.clickByCfg, 2000, cfg.newPoster, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") newPoster.run , parentId:"  + parentId);
        }
        if(cfg.subscriber.run && navigationState.nav.currentPage === navigationState.pages.network) {
            let ct = new CustomTimeout(clickActionState.clickByCfg, 2100, cfg.subscriber, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") subscriber.run , parentId:"  + parentId);
        }
        if(cfg.accepter.run && navigationState.nav.currentPage === navigationState.pages.network) {
            let ct= new CustomTimeout(clickActionState.clickByCfg, 2200, cfg.accepter, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") accepter.run , parentId:" + parentId);
        }
        if(cfg.connector.run && navigationState.nav.currentPage === navigationState.pages.network) {
            let ct= new CustomTimeout(clickActionState.clickByCfg, 2300, cfg.connector, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") connector.run , parentId:" + parentId);
        }
        if(cfg.withdraw.run && navigationState.nav.currentPage === navigationState.pages.network){
            let ct= new CustomTimeout(clickActionState.clickByCfg, 2400, cfg.withdraw, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") withdraw.run , parentId:" + parentId);
        }
        if(cfg.welcome.run && navigationState.nav.currentPage === navigationState.pages.feed) {
            let ct= new CustomTimeout(clickActionState.clickByCfg, 2500, cfg.welcome, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") welcome.run , parentId:" + parentId);
        }
        if(cfg.follower.run && navigationState.nav.currentPage === navigationState.pages.network) {
            let ct = new CustomTimeout(clickActionState.clickByCfg, 2600, cfg.follower, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") follower.run , parentId:" + parentId + ", -> ct.timeoutId: " +  ct.timeoutId);
        }
        if(cfg.like.run && navigationState.nav.currentPage === navigationState.pages.feed) {
            let ct = new CustomTimeout(clickActionState.doClickLike, 2700, cfg.like, callback, clickerState.currentTimeOut.timeoutId);
            clickerState.timeOuts.push(ct);
            console.log( getTime() + " LinkedInLike.runAll(" + cfg.key + ") like.run, , parentId:" + parentId + ", -> ct.timeoutId: " +  ct.timeoutId);
        }
        
       clickerState.rootStore.saveStorage("LinkedInLike.runAll(" + cfg.key + "), parentId:" + parentId);
        
       console.log(getTime() + " LinkedInLike.runAll(" + cfg.key + ") 🔚️, parentId:" + parentId + ", counter:" + counter);
    }
    
    const callback = (key, result, parentId) => {
        console.log(getTime() + " LinkedInLike.callback(" + key + ") key:" + key + " result:" + result + " parentId:" + parentId);
        if(result){
            cfgPanelState.updateBadge(key, cfgPanelState.badge[key] + 1);
        }
    }

    const runClicker = () => {
        let counter = clickerState.cfg.linkedInLike.root.counter;
        console.log(getTime() + " LinkedInLike.runClicker() begin," + " counter:" + counter);
        if (clickerState.cfg.linkedInLike.root.run && !clickerState.stopAllAction) {
            if (clickerState.cfg.linkedInLike.rootInterval
                || clickerState.cfg.linkedInLike.root.counter < clickerState.cfg.linkedInLike.root.counterClear) {
                if(clickerState.currentTimeOut && clickerState.currentTimeOut.status === TimeoutStatus.COMPLETED){
                    clickerState.currentTimeOut = new CustomTimeout(runAll, Utils.getRandomValue(clickerState.cfg.linkedInLike.root.range), counter); //[5000, 6000],
                    clickerState.cfg.linkedInLike.root.counter++;
                    console.log(getTime() + " LinkedInLike.runClicker() -> runAll(), currentTimeOut.timeoutId:" + clickerState.currentTimeOut.timeoutId);
                } else {
                    console.log(getTime() + " LinkedInLike.runClicker() has TimeoutStatus.ACTIVE, counter:" + counter);
                }
            } else {
                console.log(getTime() + " LinkedInLike.runClicker() -> reset()," + " counter:" + counter);
                clickerState.reset();
            }
        } else {
            console.log(getTime() + " LinkedInLike.runClicker() -> linkedInLike.root.run = false," + " counter:" + counter);
        }
        cfgPanelState.updateTimeDiff();
        console.log(getTime() + " LinkedInLike.runClicker() 🔚️," + " counter:" + counter);
    }

    useEffect(() => {
        clickerState.cfg.linkedInLike.rootInterval = setInterval(runClicker, clickerState.cfg.linkedInLike.rootTimeout);
        console.log(getTime() + " LinkedInLike.useEffect() setInterval:" + clickerState.cfg.linkedInLike.rootTimeout + "ms, rootIntervalId:" + clickerState.cfg.linkedInLike.rootInterval);
    }, []);
    
}));

export default LinkedInLike;

