const API = "http://localhost:8080";

requireLogin(["ROLE_CLIENTE"]);

let clienteActual = null;

async function iniciar() {
    await cargarCliente();
    await cargar();
}

async function cargar() {
    const res = await fetch(
        `${API}/carritos/mio`,
        { headers: authHeaders() }
    );

    const data = await res.json();
    const carrito = data.data;

    const div = document.getElementById("items");
    div.innerHTML = "";

    if (!carrito.items || carrito.items.length === 0) {
        div.innerHTML = `<p class="empty">Tu carrito está vacío 🛒</p>`;
        document.getElementById("total").innerHTML = "Total: $0";
        return;
    }

    carrito.items.forEach(item => {
        const subtotal = item.producto.precio * item.cantidad;

        div.innerHTML += `
            <div class="item">
                <h3>${item.producto.nombre}</h3>
                <p>Cantidad: ${item.cantidad}</p>
                <p>Subtotal: $${subtotal}</p>

                <div class="acciones">
                    <button onclick="modificar(${item.producto.id}, ${item.cantidad + 1})">+</button>
                    <button onclick="modificar(${item.producto.id}, ${item.cantidad - 1})">-</button>
                    <button onclick="eliminar(${item.producto.id})">Eliminar</button>
                </div>
            </div>
        `;
    });

    document.getElementById("total")
        .innerHTML = `Total: $${carrito.total}`;
}

async function modificar(productoId, cantidad){
    if(cantidad <= 0){
        eliminar(productoId);
        return;
    }

    await fetch(`${API}/carritos/modificar`, {
        method: "PUT",
        headers: authHeaders({
            "Content-Type":"application/json"
        }),
        body: JSON.stringify({ productoId, cantidad })
    });

    cargar();
}

async function eliminar(productoId){
    await fetch(`${API}/carritos/quitar/${productoId}`, {
        method: "DELETE",
        headers: authHeaders()
    });

    cargar();
}

async function vaciar(){
    await fetch(`${API}/carritos/vaciar`, {
        method: "DELETE",
        headers: authHeaders()
    });

    Swal.fire("Carrito vacío", "", "success");
    cargar();
}