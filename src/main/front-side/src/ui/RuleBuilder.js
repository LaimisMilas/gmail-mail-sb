import {inject, observer} from "mobx-react";
import React from "react";
import './css/RuleBuilder.css';

const RuleBuilder = inject("ruleState")(observer(({ruleState}) => {
    const handleApplyButtonClick = () => {
        ruleState.saveEditRuleSet();
    };

    const handleAddBuilderLine = () => {

    };

    const handleDeleteBuilderLine = () => {

    }
    
    /**
     *  Parametras event yra pasirinkimo laukas ir reikšmė.
     *  Objektas rule yra tokio formato:
     *
     *  {
     *             id: 1,
     *             action: "Like",
     *             actionKey: "like",
     *             conjunction: "if", // if, if_not
     *             ruleTarget: "description", // name, description, text
     *             ruleOperator: "not", // true, false
     *             ruleIntent: "indian_name", // it_sector, indian_name
     *  }
     *
     * */
    const handleSelectChange = (event, rule, paramName) => {
        // reikia surasti taisykliu rinkini ir taisykle pagal id.
       if(ruleState.findRule(rule)){
           rule[paramName] = event.target.value;
           // kada randa tai reiskia tai atnaujinama taisyklė
           ruleState.updateRuleByActionKey(rule.actionKey, rule);
       }else {
           // Logika kada neranda taisykles, tada reiškia kad tai nauja taisyklė
           ruleState.addRule(rule);
       }
    };

    /** 
     *  Perametras index yra taisykles indeksas
     *  Perametras rule yra taisykle, kuri bus atvaizduojama.
     *  Objektas rule yra tokio formato:
     *  
     *  {
     *             id: 1,
     *             action: "Like",
     *             actionKey: "like",
     *             conjunction: "if", // if, if_not
     *             ruleTarget: "description", // name, description, text
     *             ruleOperator: "not", // not, is
     *             ruleIntent: "indian_name", // it_sector, indian_name
     *  }
     *  
     * */
    const createSubSettingLine = (rule, index) => {
        return (
            <li key={index} className="rule-builder-line">
                <span className="rule-builder-line-action">{rule.action}</span>
                <span className="rule-builder-line-conjunction"> {rule.conjunction}</span>
                <select value={rule.ruleTarget} className="rule-builder-target-dropDown"
                        onChange={(event) => handleSelectChange(event, rule, "ruleTarget")}>
                    {ruleState.ruleTarget.map((option) => (
                        <option key={option.value} value={option.value}>
                            {option.label}
                        </option>
                    ))}
                </select>
                <select value={rule.ruleOperator} className="rule-builder-operator-dropDown"
                        onChange={(event) => handleSelectChange(event, rule, "ruleOperator")}>
                    {ruleState.ruleOperator.map((option) => (
                        <option key={option.value} value={option.value}>
                            {option.label}
                        </option>
                    ))}
                </select>
                <select value={rule.ruleIntent} className="rule-builder-intent-dropDown"
                        onChange={(event) => handleSelectChange(event, rule, "ruleIntent")}>
                    {ruleState.ruleIntent.map((option) => (
                        <option key={option.value} value={option.value}>
                            {option.label}
                        </option>
                    ))}
                </select>
                <span className="rule-builder-line-confidence"> {rule.confidence}</span>
            </li>
        );
    }

    const createSubSettingLines = () => {
        //cia gauname taisykliu rinkini pagal key
        let ruleSet = ruleState.editRuleSet;
        return (<ul>
            {
                // reikia logikos kada taisykliu nėra, tada atvaizduojame mygtuką "Add"
                
                // taisykliu rinkinio masyvas, kiekviena taisykle atvaizduojame
                ruleSet.rules.map((rule, index) => (
                    //cia perduodame taisykle ir jos indeksa, html elementas sugeneruoti
                    createSubSettingLine(rule, index)
                ))
            }
        </ul>);
    }
    
    return (
        <div className="rule-builder-container" hidden={ruleState.openRuleDialog}>
            <div className="exit-button-line">
                <span>Rule Builder</span>
                <button className="exit-button"
                        onClick={(event) => ruleState.setShowRuleDialog(true, "like")}>
                    X
                </button>
            </div>
            
            {createSubSettingLines()}
            
            <div className="apply-rule-line">
                <button
                    className="apply-rule"
                    onClick={handleApplyButtonClick}>
                    Apply
                </button>
            </div>
        </div>
    );
}));
export default RuleBuilder;
//  <span>Like</span> laukas bus dinaminis persiduos iš globalState 