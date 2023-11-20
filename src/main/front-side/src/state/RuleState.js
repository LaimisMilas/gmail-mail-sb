import {makeAutoObservable} from 'mobx';

export class RuleState {

    rootStore = null;
    ruleSets = [];
    stopAllAction = false;
    
    currentIntent = null;
    
    setup(rootStore) {
        this.rootStore = rootStore;
        this.initialize();
    }
    
    initialize() {
        this.currentIntent = "it_sector";
        this.ruleSets = [
            //kokio veiksmo("like") taisykliu rinkinys
            {
                id: 1,
                key: "like",
                rules: [{ //taisykliu sarasas
                    id: 1,
                    action: "Like",
                    actionKey: "like",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", // is, not
                    ruleIntent: "it_sector", // it_sector, indian_name
                    confidence: 0.98
                },{ 
                    id: 2,
                    action: "Like",
                    actionKey: "like",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", // is, not
                    ruleIntent: "business_owner", // it_sector, indian_name
                    confidence: 0.98
                }]
            },
            {
                id: 2,
                key: "follower",
                rules: [{
                    id: 1,
                    action: "Follow",
                    actionKey: "follower",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", //  is, not
                    ruleIntent: "it_sector", // it_sector, indian_name
                    confidence: 0.99
                },{
                    id: 2,
                    action: "Follow",
                    actionKey: "follower",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", //  is, not
                    ruleIntent: "business_owner", // it_sector, indian_name
                    confidence: 0.99
                }]
            },

            {
                id: 3,
                key: "subscriber",
                rules: [{
                    id: 1,
                    action: "Subscribe",
                    actionKey: "subscriber",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", //  is, not
                    ruleIntent: "it_sector", // it_sector, indian_name
                    confidence: 0.99
                },{
                    id: 2,
                    action: "Subscribe",
                    actionKey: "subscriber",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", //  is, not
                    ruleIntent: "business_owner", // it_sector, indian_name
                    confidence: 0.99
                }]
            },

            {
                id: 4,
                key: "accepter",
                rules: [{
                    id: 1,
                    action: "Accept",
                    actionKey: "accepter",
                    conjunction: "if", // or, and
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", //  is, not
                    ruleIntent: "it_sector", // it_sector, indian_name
                    confidence: 0.99
                },{
                    id: 2,
                    action: "Accept",
                    actionKey: "accepter",
                    conjunction: "if", // or, and
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", //  is, not
                    ruleIntent: "business_owner", // it_sector, indian_name
                    confidence: 0.99
                }]
            }, {
                id: 5,
                key: "connector",
                rules:  [{
                    id: 1,
                    action: "Connect",
                    actionKey: "connector",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", // is, not
                    ruleIntent: "it_sector", // it_sector, indian_name, business_owner
                    confidence: 0.99
                },{
                    id: 2,
                    action: "Connect",
                    actionKey: "connector",
                    conjunction: "if", // if, if_not
                    ruleTarget: "description", // name, description, text
                    ruleOperator: "is", // is, not
                    ruleIntent: "business_owner", // it_sector, indian_name, business_owner
                    confidence: 0.99
                }]
            }];
    }

    //html elemento select reiksmes
    ruleTarget = [
        {value: "name", label: "Name"},
        {value: "description", label: "Description"},
        {value: "text", label: "Text"}
    ];
    //html elemento select reiksmes
    ruleOperator = [
        {value: "not", label: "NOT"},
        {value: "is", label: "IS"}
    ];
    //html elemento select reiksmes
    //{value: "indian_name", label: "UnFriendly Name", id: 205288198998753},
    ruleIntent = [
        {value: "it_sector", label: "IT Sector Intent", id: 686099342948469},
        {value: "business_owner", label: "Business Owner Intent", id: 1402925466926079},
    ];
    
    editRuleSet = {
        id: 5,
        key: "connector",
        rules: [{
            id: 1,
            action: "Connect",
            actionKey: "connector",
            conjunction: "if", // if, if_not
            ruleTarget: "text", // name, description, text
            ruleOperator: "not", // is, not
            ruleIntent: "it_sector", // it_sector, indian_name, business_owner
        }]
    }; 

    currentRuleKey = "like";
    openRuleDialog = true;

    constructor() {
        makeAutoObservable(this);
    }

    setShowRuleDialog(value, ruleKey) {
        this.editRuleSet = this.findRuleSet(ruleKey);
        this.openRuleDialog = value;
    }

    saveEditRuleSet() {
        if(this.editRuleSet){
            this.updateRuleSet(this.editRuleSet);
        }
        this.openRuleDialog = true;
    }

    updateRuleByActionKey(actionKey, newRuleData) {
        for (let i = 0; i < this.ruleSets.length; i++) {
            for (let j = 0; j < this.ruleSets[i].rules.length; j++) {
                if (this.ruleSets[i].rules[j].actionKey === actionKey) {
                    // Atnaujiname narį pagal actionKey
                    this.ruleSets[i].rules[j] = {...this.ruleSets[i].rules[j], ...newRuleData};
                }
            }
        }
    }

    mergeRule(rule){
      return { ...this.findRule(rule), ...rule};
    }

    addRule(rule) {
        this.findRuleSet(rule.actionKey).rules.push(rule);
    }

    findRule(rule) {
        let result = null;
        let ruleSet = this.findRuleSet(rule.actionKey);
        ruleSet.rules.forEach((rule) => {
            if (rule.id === rule.id) {
                result = ruleSet;
            }
        });
        return result;
    }

    getRuleByKey(key) {
        return this.findRuleSet(key);
    }

    findRuleSet(ruleKey) {
        let result = null;
        this.ruleSets.forEach((ruleSet) => {
            if (ruleSet.key === ruleKey) {
                result = ruleSet;
            }
        });
        return result;
    }

    findRuleSetByIntent(key, ruleIntent) {
        let result = null;
        this.ruleSets.forEach((ruleSet) => {
            if (ruleSet.key === key) {
                ruleSet.rules.forEach((rule) => {
                    if (rule.ruleIntent === ruleIntent) {
                        result = rule;
                    }
                });
            }
        });
        return result;
    }

    findCurrentRuleSetByKey(key) {
        return this.findRuleSetByIntent(key, this.currentIntent);
    }
    
    removeRule(ruleName) {

    }

    getCurrentRule(currentRuleKey) {
        return this.findRuleSet(currentRuleKey);
    }
    
    updateRuleSet(editRuleSet) {
        for (let i = 0; i < this.ruleSets.length; i++) {
            if (this.ruleSets[i].key === editRuleSet.key) {
                // Atnaujiname pagrindinį objektą
                this.ruleSets[i] = {...this.ruleSets[i], ...editRuleSet};

                // Atnaujiname taisyklių masyvą
                for (let j = 0; j < this.ruleSets[i].rules.length; j++) {
                    const mergeRule = editRuleSet.rules.find(r => r.id === this.ruleSets[i].rules[j].id);
                    if (mergeRule) {
                        this.ruleSets[i].rules[j] = {...this.ruleSets[i].rules[j], ...mergeRule};
                    }
                }
                // Pridėti naujas taisykles, kurios nėra esamame masyve
                editRuleSet.rules.forEach(mergeRule => {
                    if (!this.ruleSets[i].rules.some(rule => rule.id === mergeRule.id)) {
                        this.ruleSets[i].rules.push(mergeRule);
                    }
                });
            }
        }
    }
    
    setCurrentIntent(intent){
        this.currentIntent = intent;
    }

}