import React from 'react';
import {Provider} from "mobx-react";
import Scroll from "./component/Scroll";
import CfgPanel from "./ui/CfgPanel";
import SiteNavigation from "./component/SiteNavigation";
import LinkedinLike from "./component/LinkedInLike";
import CfgManager from "./component/CfgManager";
import RuleBuilder from "./ui/RuleBuilder";
import {RootStore} from "./state/RootStore";

const rootStore = new RootStore();

const App = () => {

    return (
        <Provider
            rootStore={rootStore.actorState}
            navigationState={rootStore.navigationState} 
            scrollState={rootStore.scrollState}
            clickerState={rootStore.clickerState}
            cfgPanelState={rootStore.cfgPanelState}
            ruleState={rootStore.ruleState}
            clickActionState={rootStore.clickActionState}
        >
            <div className="App">
                <Scroll/>
                <CfgPanel/>
                <RuleBuilder/>
                <LinkedinLike/>
                <SiteNavigation/>
                <CfgManager/>
            </div>
        </Provider>
    );
}

export default App;