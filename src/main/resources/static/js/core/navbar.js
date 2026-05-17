function renderNavbar() {
    const nav = document.getElementById("navbar");
    const auth = getAuth();

    // Si NO hay auth válido
    if (!auth || !auth.email || !auth.password || !auth.rol) {
        nav.innerHTML = `
            <a href="/pages/index.html">Productos</a>
            <a href="/pages/login.html">Login</a>
            <a href="/pages/registro.html">Registrarse</a>
        `;
        return;
    }

    if (auth.rol === "ROLE_CLIENTE") {
        nav.innerHTML = `
            <a href="/pages/index.html">Productos</a>
            <a href="/pages/carrito.html">Carrito</a>
            <a href="/pages/pedidos.html">Pedidos</a>
            <a href="/pages/cliente.html">Domicilios</a>
            <a href="#" onclick="logout()">Logout</a>
        `;
    }

    if (auth.rol === "ROLE_ADMIN") {
        nav.innerHTML = `
            <a href="/pages/admin.html">Admin</a>
            <a href="/pages/index.html">Productos</a>
            <a href="#" onclick="logout()">Logout</a>
        `;
    }

    if (auth.rol === "ROLE_VENDEDOR") {
        nav.innerHTML = `
            <a href="/pages/pedidos.html">Vendedor</a>
            <a href="#" onclick="logout()">Logout</a>
        `;
    }
}

function logout() {
    localStorage.clear();
    window.location.href = "/pages/login.html";
}

document.addEventListener("DOMContentLoaded", renderNavbar);