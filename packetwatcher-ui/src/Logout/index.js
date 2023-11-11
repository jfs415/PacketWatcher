import secureLocalStorage from "react-secure-storage";

function Logout() {
    fetch(`/logout?token=${secureLocalStorage.getItem("jwt")}`, {
    }).then(response => {
        secureLocalStorage.clear();
        localStorage.clear();
        window.location.href = "/";
    });
}

export default Logout;