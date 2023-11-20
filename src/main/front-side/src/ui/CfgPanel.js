import {inject, observer} from 'mobx-react';
import React, {useState} from 'react';
import './css/CfgPanel.css';
import {getTime} from "html-evaluate-utils/DateFormat";

const CfgPanel =
    inject("clickerState", "ruleState", "scrollState", "cfgPanelState")(
        observer(({clickerState, ruleState, scrollState, cfgPanelState}) => {

            const handleCheckboxChange = (event, key) => {
                console.log(getTime() + " CfgPanelState.handleCheckboxChange()");
                if (key in cfgPanelState.rowConfig) {

                    cfgPanelState.updateRowConfigCheckValue(key, event.target.checked);

                    if (key === "scroll") {
                        scrollState.cfg.root.run = event.target.checked;
                    } else {
                        clickerState.cfg.linkedInLike[key].run = event.target.checked;
                    }
                    setApplyButtonStyle({className: "apply-button-save"});
                }
            };

            const [applyButtonStyle, setApplyButtonStyle] = useState({
                className: "apply-button",
            });
            
            const [checkBoxContainerState, setCheckBoxContainerState] = useState(false);
            const [stopAllAction, setStopAllAction] = useState(cfgPanelState.getIsActionsStop());

            const handleApplyButtonClick = () => {
                // Veiksmai paspaudus "Apply" mygtuką
                console.log(getTime() + " CfgPanelState.handleApplyButtonClick()");
                setApplyButtonStyle({className: "apply-button-apply"});
                setTimeout(
                    () => setApplyButtonStyle({className: "apply-button"}), 700
                )
            };

            const handleCollapseButtonClick = () => {
                console.log(getTime() + " CfgPanelState.handleCollapseButtonClick()");
                checkBoxContainerState === true ? setCheckBoxContainerState(false) : setCheckBoxContainerState(true);
            }
            
            const handleStopButtonClick = () => {
                console.log(getTime() + " CfgPanelState.handleStopButtonClick()");
                stopAllAction === false ? setStopAllAction(true) : setStopAllAction(false);
                cfgPanelState.handleStopButtonClick(stopAllAction);
            }
            
            const totalBadge = () => {
                const sum = (cfgPanelState.badge.like
                    + cfgPanelState.badge.follower
                    + cfgPanelState.badge.subscriber
                    + cfgPanelState.badge.accepter
                    + cfgPanelState.badge.connector);
                return sum > 0 ? (sum > 99 ? "99+": sum) : ''
            }

            const totalBadgeTitle = () => {
                const sum = (cfgPanelState.badge.like
                    + cfgPanelState.badge.follower
                    + cfgPanelState.badge.subscriber
                    + cfgPanelState.badge.accepter
                    + cfgPanelState.badge.connector);
                return sum > 0 ? sum: ''
            }

            return (
                <div className="console-box" id="labas_as_krabas" hidden={cfgPanelState.stopAllAction}>
                    <div className="tab-container">
                        <span title={totalBadgeTitle()}
                            className="badge">{totalBadge()}
                        </span>
                        <span className="activeTime">{cfgPanelState.active.fromDate} wt.: {cfgPanelState.active.timeDiff} min.</span>
                        <button className="exit-button"
                                onClick={() => handleCollapseButtonClick()}>
                            {checkBoxContainerState === true ? "▼" : "▲"}
                        </button>
                        <div hidden={checkBoxContainerState}>
                            <div className="checkbox-row">
                                <select className="rule-intent-dropDown" value={ruleState.currentIntent}
                                        onChange={(event) => {
                                            setApplyButtonStyle({className: "apply-button-save"});
                                            ruleState.setCurrentIntent(event.currentTarget.value);
                                            clickerState.rootStore.saveStorage("CFGPanel.render() intent onChange");
                                        }}>
                                    {ruleState.ruleIntent.map((option) => (
                                        <option key={option.value} value={option.value}>
                                            {option.label}
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="checkbox-row">
                                <span className="badge"></span>
                                <label
                                    htmlFor={cfgPanelState.rowConfig.newPoster.id}>{cfgPanelState.rowConfig.newPoster.label}</label>
                                <input
                                    type="checkbox"
                                    id={cfgPanelState.rowConfig.newPoster.id}
                                    name={cfgPanelState.rowConfig.newPoster.name}
                                    checked={cfgPanelState.rowConfig.newPoster.checkValue}
                                    onChange={(event) => handleCheckboxChange(event, cfgPanelState.rowConfig.newPoster.key)}
                                />
                            </div>
                            <div className="checkbox-row">
                                <span
                                    className="badge notification-badge__count">{cfgPanelState.badge.like > 0 ? (cfgPanelState.badge.like > 99 ? "99+": cfgPanelState.badge.like) : ''}</span>
                                <label
                                    htmlFor={cfgPanelState.rowConfig.like.id}>{cfgPanelState.rowConfig.like.label}</label>
                                <input
                                    type="checkbox"
                                    id={cfgPanelState.rowConfig.like.id}
                                    name={cfgPanelState.rowConfig.like.name}
                                    checked={cfgPanelState.rowConfig.like.checkValue}
                                    onChange={(event) => handleCheckboxChange(event, cfgPanelState.rowConfig.like.key)}
                                />
                            </div>
                            <div className="checkbox-row">
                                <span
                                    className="badge notification-badge__count">{cfgPanelState.badge.follower > 0 ? (cfgPanelState.badge.follower > 99 ? "99+": cfgPanelState.badge.follower) : ''}</span>
                                <label
                                    htmlFor={cfgPanelState.rowConfig.follower.id}>{cfgPanelState.rowConfig.follower.label}</label>
                                <input
                                    type="checkbox"
                                    id={cfgPanelState.rowConfig.follower.id}
                                    name={cfgPanelState.rowConfig.follower.name}
                                    checked={cfgPanelState.rowConfig.follower.checkValue}
                                    onChange={(event) => handleCheckboxChange(event, cfgPanelState.rowConfig.follower.key)}
                                />
                            </div>
                            <div className="checkbox-row">
                                <span
                                    className="badge">{cfgPanelState.badge.subscriber > 0 ? (cfgPanelState.badge.subscriber > 99 ? "99+": cfgPanelState.badge.subscriber)  : ''}</span>
                                <label
                                    htmlFor={cfgPanelState.rowConfig.subscriber.id}>{cfgPanelState.rowConfig.subscriber.label}</label>
                                <input
                                    type="checkbox"
                                    id={cfgPanelState.rowConfig.subscriber.id}
                                    name={cfgPanelState.rowConfig.subscriber.name}
                                    checked={cfgPanelState.rowConfig.subscriber.checkValue}
                                    onChange={(event) => handleCheckboxChange(event, cfgPanelState.rowConfig.subscriber.key)}
                                />
                            </div>
                            <div className="checkbox-row">
                                <span
                                    className="badge">{cfgPanelState.badge.accepter > 0 ? (cfgPanelState.badge.accepter > 99 ? "99+": cfgPanelState.badge.accepter)  : ''}</span>
                                <label
                                    htmlFor={cfgPanelState.rowConfig.accepter.id}>{cfgPanelState.rowConfig.accepter.label}</label>
                                <input
                                    type="checkbox"
                                    id={cfgPanelState.rowConfig.accepter.id}
                                    name={cfgPanelState.rowConfig.accepter.name}
                                    checked={cfgPanelState.rowConfig.accepter.checkValue}
                                    onChange={(event) => handleCheckboxChange(event, cfgPanelState.rowConfig.accepter.key)}
                                />
                            </div>
                            <div className="checkbox-row">
                                <span
                                    className="badge">{cfgPanelState.badge.connector > 0 ? (cfgPanelState.badge.connector > 99 ? "99+": cfgPanelState.badge.connector) : ''}</span>
                                <label
                                    htmlFor={cfgPanelState.rowConfig.connector.id}>{cfgPanelState.rowConfig.connector.label}</label>
                                <input
                                    type="checkbox"
                                    id={cfgPanelState.rowConfig.connector.id}
                                    name={cfgPanelState.rowConfig.connector.name}
                                    checked={cfgPanelState.rowConfig.connector.checkValue}
                                    onChange={(event) => handleCheckboxChange(event, cfgPanelState.rowConfig.connector.key)}
                                />
                            </div>
                            <div className="checkbox-row">
                                <span className="badge"></span>
                                <label htmlFor={"scroll_id"}>{cfgPanelState.rowConfig.scroll.label}</label>
                                <input
                                    type="checkbox"
                                    id={"scroll_id"}
                                    name={"scroll_name"}
                                    checked={cfgPanelState.rowConfig.scroll.checkValue}
                                    onChange={(event) => handleCheckboxChange(event, cfgPanelState.rowConfig.scroll.key)}
                                />
                            </div>
                            <button className={applyButtonStyle.className} onClick={handleApplyButtonClick}>Apply
                            </button>
                            <button className={ stopAllAction === true ? "stop-button stop-all-action-true" : "stop-button"} onClick={handleStopButtonClick}>
                                {
                                    stopAllAction === false ? "Stop" : "Start"
                                }
                            </button>
                        </div>
                    </div>
                </div>
            );
        }));

export default CfgPanel;