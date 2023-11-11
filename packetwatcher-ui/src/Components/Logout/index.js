import secureLocalStorage from "react-secure-storage";
import {getFromLocalStorage} from "../../util/LocalStorage";

function Logout() {
    fetch("/disconnect", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "post",
        body: getFromLocalStorage("jwt"),
    }).then(response => {
        fetch("/logout", { // Call default spring /logout endpoint
        }).then();
    }).finally(() => {
        secureLocalStorage.clear();
        localStorage.clear();
        window.location.href = "/";
    });
}

export default Logout;