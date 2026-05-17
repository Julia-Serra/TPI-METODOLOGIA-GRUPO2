function getAuth() {
    return JSON.parse(localStorage.getItem("auth"));
}

function authHeaders() {
    const auth = getAuth();

    if (!auth) return { "Content-Type": "application/json" };

    return {
        "Content-Type": "application/json",
        "Authorization": "Basic " + btoa(auth.email + ":" + auth.password)
    };
}

function requireLogin(roles = []) {
    const auth = getAuth();

    if (!auth) {
        window.location.href = "/pages/login.html";
        return;
    }

    if (roles.length && !roles.includes(auth.rol)) {
        window.location.href = "/pages/index.html";
    }
    document.querySelectorAll(".hidden").forEach(e => e.classList.remove("hidden"));
}