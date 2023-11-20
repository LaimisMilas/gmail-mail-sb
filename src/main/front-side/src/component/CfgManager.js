import {inject, observer} from "mobx-react";
import {useEffect} from "react";
import {Utils} from "html-evaluate-utils/Utils";

const CfgManager =  inject("clickerState", "navigationState", "cfgPanelState")(observer(({ clickerState, navigationState, cfgPanelState}) => {    
    
    let rootInterval = null;
    let rootTimeout = 3123;
    let rootTimeoutRange = [5101, 7112];
    let counter = 0;
    let counterClear = 200;

    const runAll = () => {
      //  navigationSpeed();
      //  clickLikeSpeed();
        
    }
    
    const navigationSpeed = () => {
        if(cfgPanelState.badge.follower > 40 && cfgPanelState.badge.follower < 60){
            navigationState.nav.pages[1].timeOnPage = [51554, 52554]; // def [131554, 141554]
        }
        if(cfgPanelState.badge.follower > 60 && cfgPanelState.badge.follower < 80){
            navigationState.nav.pages[1].timeOnPage = [21554, 22554];
        }
        if(cfgPanelState.badge.follower > 80 && cfgPanelState.badge.follower < 100){
            navigationState.nav.pages[1].timeOnPage = [11554, 12554];
        }
        if(cfgPanelState.badge.follower > 100 && cfgPanelState.badge.follower < 120){
            navigationState.nav.pages[1].timeOnPage = [5554, 6554];
        }
    }
    
    const clickLikeSpeed = () => {
        
        if(cfgPanelState.badge.like > 40 && cfgPanelState.badge.like < 60){
            clickerState.cfg.linkedInLike.like.range = [50215, 54215]; // def :  [25215, 27215]
        }
        if(cfgPanelState.badge.like > 60 && cfgPanelState.badge.like < 80){
            clickerState.cfg.linkedInLike.like.range = [100215, 104215];
        }
        if(cfgPanelState.badge.like > 80 && cfgPanelState.badge.like < 100){
            clickerState.cfg.linkedInLike.like.range = [200215, 204215];
        }
        if(cfgPanelState.badge.like > 100 && cfgPanelState.badge.like < 120){
            clickerState.cfg.linkedInLike.like.range = [400215, 404215];
        }
    }

    const run = () => {
        if(clickerState.stopAllAction){
            return;
        }
        if (!rootInterval
            || counter > counterClear) {
            reset();
        } else {
            runAll();
            counter++;
        }
    }
    
    const reset = () => {
        clearInterval(rootInterval);
        counter = 0;
        rootTimeout = Utils.getRandomValue(rootTimeoutRange);
        rootInterval = setInterval(run, rootTimeout);
    }

    useEffect(() => {
        run();
    }, []);
}));

export default CfgManager;