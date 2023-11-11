import { useEffect, useState } from "react";
import secureLocalStorage from "react-secure-storage";

function useLocalState(defaultValue, key) {
    const [value, setValue] = useState(() => {
        const localStorageValue = secureLocalStorage.getItem(key);

        return localStorageValue !== null ? JSON.parse(localStorageValue.toString()) : defaultValue;
    });

    useEffect(() => {
        secureLocalStorage.setItem(key, JSON.stringify(value));
    }, [key, value]);

    return [value, setValue];
}

function getFromLocalStorage(key) {
    let item = secureLocalStorage.getItem(key);
    
    if (item == null) {
        throw SecureStorageItemNotFoundError;
    }
    
    return item.replaceAll("\"", "");
}

class SecureStorageItemNotFoundError extends Error {

    constructor() {
        super("Secure Storage Item Not Found!");
        this.name = "SecureStorageItemNotFoundError"
    }
    
}

export { useLocalState, getFromLocalStorage, SecureStorageItemNotFoundError };