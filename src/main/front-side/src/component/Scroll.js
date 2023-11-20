import {useEffect} from 'react';
import {inject, observer} from "mobx-react";
import { Utils} from "html-evaluate-utils/Utils";
import {getTime} from "html-evaluate-utils/DateFormat";
import {CustomTimeout, TimeoutStatus} from "../utils/CustomTimeout";

const Scroll = inject("scrollState")(observer(({scrollState}) => {

    const scrollDown = () => {
        let down = Utils.getRandomValue(scrollState.cfg.scroll.scrollerDown);
        window.scrollBy({top: down, left: 0, behavior: 'smooth'});
        scrollState.updateBadge('scroll', scrollState.badge.scroll + 1)
    }

    const runScroll = () => {
        if (!scrollState.cfg.root.run || scrollState.stopAllAction) {
            return;
        }
        let timeOut = Utils.getRandomValue(scrollState.cfg.root.range);
        if (
            !scrollState.cfg.rootInterval ||
            scrollState.cfg.root.counter > scrollState.cfg.root.counterClear
        ) {
            console.log("Scroll.runScroll() reset");
            reset();
        } else {
            if(scrollState.currentTimeOut && scrollState.currentTimeOut.status === TimeoutStatus.COMPLETED){
                scrollState.currentTimeOut = new CustomTimeout(scrollDown, timeOut);
                scrollState.cfg.root.counter++;
            }
            // setTimeout(scrollDown, timeOut);
            // console.log(getTime() + " Scroll setTimeout:" + timeOut + "ms, "+ " tmOut:" + tmOut );
            
        }
    };

    const reset = () => {
        scrollState.cfg.root.counter = 0;
    };

    useEffect(() => {
        console.log(getTime()  + " Scroll useEffect() setTimeout:" + scrollState.cfg.rootTimeout + "ms");
        scrollState.cfg.rootInterval = setInterval(runScroll, scrollState.cfg.rootTimeout);
    }, []);

}));
export default Scroll;
