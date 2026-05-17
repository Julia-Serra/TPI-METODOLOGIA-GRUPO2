const API = "http://localhost:8080";

async function login(){

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const res = await fetch(`${API}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    });

    if(!res.ok){
        alert("Credenciales incorrectas");
        return;
    }

    const data = await res.json();

    // guardamos sesión
    localStorage.setItem("auth", JSON.stringify({
        token: data.token,
        rol: data.rol
    }));

    // redirección por rol
    if(data.rol === "ROLE_ADMIN"){
        window.location.href = "admin.html";
    } else {
        window.location.href = "index.html";
    }
}