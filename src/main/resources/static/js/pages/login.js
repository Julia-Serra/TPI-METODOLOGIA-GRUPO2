const API = "http://localhost:8080";

async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const res = await fetch(`${API}/clientes/me`, {
        headers: {
            "Authorization": "Basic " + btoa(email + ":" + password)
        }
    });

    if (!res.ok) {
        alert("Credenciales inválidas");
        return;
    }

    const data = await res.json();

    localStorage.setItem("auth", JSON.stringify({
        email,
        password,
        rol: data.rol
    }));

    window.location.href = "/pages/index.html";
}