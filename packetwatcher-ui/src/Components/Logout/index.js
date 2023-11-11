import secureLocalStorage from "react-secure-storage";

function Logout() {
    fetch("/disconnect", {
        headers: {
            "Content-Type": "application/json"
        },
        method: "post",
        body: secureLocalStorage.getItem("jwt").replaceAll("\"", ""),
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