const API = "http://localhost:8080";

let clienteActual = null;
let carritoId = localStorage.getItem("carritoId");

// ========================
// INICIALIZAR CARRITO
// ========================
async function iniciar() {
    try {

        if (!carritoId) {
            const res = await fetch(`${API}/carritos`, {
                method: "POST"
            });

            const data = await res.json();

            if (!res.ok || !data.data) {
                throw new Error("No se pudo crear el carrito");
            }

            carritoId = data.data.id;
            localStorage.setItem("carritoId", carritoId);
        }

        await cargar();

    } catch (e) {
        console.error(e);
        Swal.fire("Error", "No se pudo iniciar el carrito", "error");
    }
}

// ========================
// CARGAR CARRITO
// ========================
async function cargar() {
    try {

        const res = await fetch(`${API}/carritos/${carritoId}`);
        const data = await res.json();

        const carrito = data.data;

        const div = document.getElementById("items");

        if (!carrito || !carrito.items) {
            div.innerHTML = `<p>Carrito inválido</p>`;
            return;
        }

        if (carrito.items.length === 0) {
            div.innerHTML = `<p class="empty">Carrito vacío 🛒</p>`;
            document.getElementById("total").innerHTML = "Total: $0";
            return;
        }

        div.innerHTML = "";

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

        document.getElementById("total").innerHTML =
            `Total: $${carrito.total}`;

    } catch (e) {
        console.error(e);
        Swal.fire("Error", "No se pudo cargar el carrito", "error");
    }
}

// ========================
// MODIFICAR CANTIDAD
// ========================
async function modificar(productoId, cantidad) {

    if (cantidad <= 0) {
        return eliminar(productoId);
    }

    try {

        const res = await fetch(
            `${API}/carritos/${carritoId}/modificar/${productoId}?cantidad=${cantidad}`,
            { method: "PUT" }
        );

        const data = await res.json().catch(() => ({}));

        if (!res.ok) {
            Swal.fire("Error", data.message || "Stock insuficiente", "warning");
            return;
        }

        await cargar();

    } catch (e) {
        Swal.fire("Error", "Error modificando producto", "error");
    }
}

// ========================
// ELIMINAR PRODUCTO
// ========================
async function eliminar(productoId) {

    try {

        await fetch(
            `${API}/carritos/${carritoId}/quitar/${productoId}`,
            { method: "DELETE" }
        );

        await cargar();

    } catch (e) {
        Swal.fire("Error", "No se pudo eliminar", "error");
    }
}

// ========================
// VACIAR CARRITO
// ========================
async function vaciar() {

    try {

        await fetch(
            `${API}/carritos/${carritoId}/vaciar`,
            { method: "DELETE" }
        );

        Swal.fire("OK", "Carrito vacío", "success");

        await cargar();

    } catch (e) {
        Swal.fire("Error", "No se pudo vaciar carrito", "error");
    }
}

// ========================
// BUSCAR CLIENTE
// ========================
async function buscarCliente() {

    const email = document.getElementById("emailCliente").value;

    try {

        const res = await fetch(`${API}/clientes/email/${email}`);
        const data = await res.json();

        if (!res.ok) {
            Swal.fire("Error", data.message || "Cliente no encontrado", "error");
            return;
        }

        clienteActual = data.data;

        cargarDirecciones(clienteActual.direcciones || []);

    } catch (e) {
        Swal.fire("Error", "Error buscando cliente", "error");
    }
}

// ========================
// DIRECCIONES
// ========================
function cargarDirecciones(direcciones) {

    const select = document.getElementById("direccionSelect");

    select.innerHTML = '<option value="">Seleccione un domicilio</option>';

    direcciones.forEach((d, i) => {

        const opt = document.createElement("option");

        opt.value = i;
        opt.textContent = `${d.calle} ${d.numero} - ${d.localidad}`;

        select.appendChild(opt);
    });
}

// ========================
// CONFIRMAR PEDIDO
// ========================
async function confirmarPedido() {

    if (!carritoId) {
        Swal.fire("Error", "No existe carrito", "error");
        return;
    }

    if (!clienteActual) {
        Swal.fire("Error", "Cliente no cargado", "error");
        return;
    }

    const direccionIndex = document.getElementById("direccionSelect").value;
    const formaPago = document.getElementById("formaPago").value;

    if (direccionIndex === "" || formaPago === "") {
        Swal.fire("Error", "Faltan datos del pedido", "error");
        return;
    }

    const direccion = clienteActual.direcciones[direccionIndex];

    const body = {
        carritoId: Number(carritoId),
        emailCliente: clienteActual.email,
        domicilioEnvio: direccion,
        formaPago: formaPago
    };

    try {

        const res = await fetch(`${API}/pedidos/confirmar`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });

        const data = await res.json();

        if (!res.ok) {
            Swal.fire("Error", data.message || "No se pudo confirmar", "error");
            return;
        }

        localStorage.removeItem("carritoId");

        Swal.fire("OK", "Pedido confirmado", "success");

        location.reload();

    } catch (e) {
        Swal.fire("Error", "Error al confirmar pedido", "error");
    }
}

// iniciar app
iniciar();