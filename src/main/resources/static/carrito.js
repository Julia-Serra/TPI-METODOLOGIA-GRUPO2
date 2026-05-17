const API = "http://localhost:8080";
const auth = JSON.parse(localStorage.getItem("auth"));

if (!auth) {
    window.location.href = "login.html";
}

if (auth.rol !== "ROLE_CLIENTE") {
    window.location.href = "productos.html";
}

let clienteActual = null;

let carritoId = localStorage.getItem("carritoId");

async function iniciar() {

    if (!carritoId) {
        const res = await fetch(`${API}/carritos`, {
            method: "POST",
            headers: authHeaders()
        });

        const data = await res.json();
        carritoId = data.data.id;

        localStorage.setItem("carritoId", carritoId);
    }

    await cargarCliente();
    await cargar();
}

async function cargar() {

    const res = await fetch(
        `${API}/carritos/${carritoId}`,
        { headers: authHeaders() }
    );

    if (res.status === 404) {
        localStorage.removeItem("carritoId");
        location.reload();
        return;
    }

    const data = await res.json();
    const carrito = data.data;

    const div = document.getElementById("items");
    div.innerHTML = "";

    if (carrito.items.length === 0) {
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

    const res = await fetch(
        `${API}/carritos/${carritoId}/modificar/${productoId}?cantidad=${cantidad}`,
        {
            method: "PUT",
            headers: authHeaders()
        }
    );

    if(!res.ok){
        let msgError = "Stock insuficiente";
        try {
            const dataError = await res.json();
            if(dataError && dataError.message) msgError = dataError.message;
        } catch(e) {}

        Swal.fire({
            title: '¡Atención!',
            text: msgError,
            icon: 'warning',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff'
        });

        return;
    }

    cargar();
}

async function eliminar(productoId){

await fetch(
    `${API}/carritos/${carritoId}/quitar/${productoId}`,
    {
        method: "DELETE",
        headers: authHeaders()
    }
);

    cargar();
}

async function vaciar(){

    await fetch(
        `${API}/carritos/${carritoId}/vaciar`,
        {
            method: "DELETE",
            headers: authHeaders()
        }
    );

    Swal.fire({
        title: 'Carrito vacío',
        text: 'Se quitaron todos los productos',
        icon: 'success',
        confirmButtonColor: '#bc2e7e',
        background: '#231932',
        color: '#ffffff',
        timer: 1500
    });

    cargar();
}

iniciar();
async function cargarCliente() {

    const response = await fetch(
        `${API}/clientes/me`,
        { headers: authHeaders() }
    );

    if (!response.ok) {
        Swal.fire("Error", "No se pudo cargar el cliente", "error");
        return;
    }

    const data = await response.json();
    clienteActual = data.data;

    cargarDirecciones(clienteActual.direcciones);
}
async function confirmarPedido() {

    const direccionIndex = document.getElementById("direccionSelect").value;
    const formaPago = document.getElementById("formaPago").value;

    if (direccionIndex === "" || formaPago === "") {
        Swal.fire("Atención", "Complete los datos", "warning");
        return;
    }

    const direccion = clienteActual.direcciones[direccionIndex];

    const body = {
        carritoId: carritoId,
        domicilioEnvio: direccion,
        formaPago: formaPago
    };

    const response = await fetch(
        `${API}/pedidos/confirmar`,
        {
            method: "POST",
            headers: authHeaders(),
            body: JSON.stringify(body)
        }
    );

    if (response.ok) {
        Swal.fire("Éxito", "Pedido confirmado", "success");
        localStorage.removeItem("carritoId");
        location.reload();
    }
}
/*
async function buscarCliente() {

    const email =
        document.getElementById(
            "emailCliente"
        ).value;

    const response =
        await fetch(
            `${API}/clientes/email/${email}`
        );

    if(!response.ok){

        alert("Cliente no encontrado");

        return;
    }

    const data = await response.json();

    clienteActual = data.data;

    cargarDirecciones(
        clienteActual.direcciones
    );
}
*/

function cargarDirecciones(direcciones){

    const select = document.getElementById("direccionSelect");

    select.innerHTML =
        '<option value="">Seleccione un domicilio</option>';

    direcciones.forEach((direccion, index) => {

        const option = document.createElement("option");

        option.value = index;

        option.textContent =
            direccion.calle + " " +
            direccion.numero + " - " +
            direccion.localidad;

        select.appendChild(option);
    });
}

/*No repetir headers*/ 
function authHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${auth.token}`
    };
}

function logout(){
    localStorage.removeItem("auth");
    window.location.href = "index.html";
}