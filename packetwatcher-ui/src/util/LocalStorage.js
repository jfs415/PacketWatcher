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

export { useLocalState };