
export class LocalStorageManager {
    static createStorage = (storageName, storage) => {
        if (!localStorage.getItem(storageName)) {
            this.setStorage(storageName, storage);
        }
    }

    static readValue = (storageName, itemName) => {
        let result = null;
        if (this.getStorage(storageName)) {
            const storage = this.getStorage(storageName);
            if(storage){
                result = storage[itemName];
            }
        }
        return result;
    }

    static writeValue = (storageName, itemName, value) => {
        if (this.getStorage(storageName)) {
            const storage = this.getStorage(storageName);
            if(storage[itemName]){
                storage[itemName] = value;
                this.setStorage(storageName, storage);
            }
        }
    }

    static getStorage = (storageName) => {
        return JSON.parse(localStorage.getItem(storageName));
    }

    static update = (storageName, storageNew) => {
        let storageOld = JSON.parse(localStorage.getItem(storageName));
        if(storageOld){
            let mergedObj;
            if(Array.isArray(storageNew)){
                mergedObj = [...storageOld, ...storageNew];
            } else {
                mergedObj = { ...storageOld, ...storageNew};   
            }
            this.flash(storageName, mergedObj);
        } else {
            this.flash(storageName, storageNew);
        }
    }

    static setStorage = (storageName ,storage) => {
        localStorage.setItem(storageName, JSON.stringify(storage));
    }

    static flash = (storageName ,storage) => {
        localStorage.setItem(storageName, JSON.stringify(storage));
    }

    isHasValue(key){
        return localStorage.hasOwnProperty(key);
    }

    saveToMap(value){
        if(!localStorage.hasOwnProperty(value)) {
            localStorage.setItem(value, value);
        }
    }
}
