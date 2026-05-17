const auth = localStorage.getItem("auth");
const rol = localStorage.getItem("rol");

const navbar = document.getElementById("navbar");

function link(href, text){
    return `<a href="${href}"><button>${text}</button></a>`;
}

if(!auth){
    navbar.innerHTML =
        link("producto.html","Productos") +
        link("login.html","Login") +
        link("registro.html","Registrarse");
}
else if(rol === "CLIENTE"){
    navbar.innerHTML =
        link("producto.html","Productos") +
        link("carrito.html","Carrito") +
        link("pedidos.html","Pedidos") +
        link("domicilios.html","Domicilios") +
        `<button onclick="logout()">Logout</button>`;
}
else if(rol === "ADMIN"){
    navbar.innerHTML =
        link("admin.html","Admin") +
        link("producto.html","Productos") +
        `<button onclick="logout()">Logout</button>`;
}

function logout(){
    localStorage.clear();
    window.location.href = "index.html";
}