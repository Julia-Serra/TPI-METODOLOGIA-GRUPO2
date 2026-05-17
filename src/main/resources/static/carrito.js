
const API = "http://localhost:8080";
let clienteActual = null;

let carritoId =
    localStorage.getItem("carritoId");

async function iniciar(){

    if(!carritoId){

        const res =
            await fetch(`${API}/carritos`,{
                method:"POST"
            });

        const data = await res.json();

        carritoId = data.data.id;

        localStorage.setItem(
            "carritoId",
            carritoId
        );
    }
    await cargarCliente();
    cargar();
}

async function cargar(){

    const res =
        await fetch(
            `${API}/carritos/${carritoId}`
        );

    const data = await res.json();
    if(!data.data){
        localStorage.removeItem("carritoId");
        location.reload();
        return;
    } //RECIEN
    const carrito = data.data;
    if(!carrito){
        alert("Error cargando carrito");
        return;
    }

    const div =
        document.getElementById("items");

    div.innerHTML = "";

    if(carrito.items.length === 0){

        div.innerHTML =
            `<p class="empty">
                Tu carrito está vacío 🛒
            </p>`;

        document.getElementById("total")
            .innerHTML = "Total: $0";

        return;
    }

    carrito.items.forEach(item => {

        const subtotal =
            item.producto.precio *
            item.cantidad;

        div.innerHTML += `

            <div class="item">

                <h3>${item.producto.nombre}</h3>

                <p>Cantidad: ${item.cantidad}</p>

                <p>Subtotal: $${subtotal}</p>

                <div class="acciones">

                    <button
                        class="btn-sumar"
                        onclick="modificar(${item.producto.id}, ${item.cantidad + 1})"
                    >
                        +
                    </button>

                    <button
                        class="btn-restar"
                        onclick="modificar(${item.producto.id}, ${item.cantidad - 1})"
                    >
                        -
                    </button>

                    <button
                        class="btn-eliminar"
                        onclick="eliminar(${item.producto.id})"
                    >
                        Eliminar
                    </button>

                </div>

            </div>
        `;
    });

    document.getElementById("total")
        .innerHTML =
        `Total: $${carrito.total}`;
}

async function modificar(productoId,cantidad){

    if(cantidad <= 0){

        eliminar(productoId);

        return;
    }

    const res =
        await fetch(
            `${API}/carritos/${carritoId}/modificar/${productoId}?cantidad=${cantidad}`,
            {
                method:"PUT"
            }
        );

    if(!res.ok){

        alert("Stock insuficiente");

        return;
    }

    cargar();
}

async function eliminar(productoId){

    await fetch(
        `${API}/carritos/${carritoId}/quitar/${productoId}`,
        {
            method:"DELETE"
        }
    );

    cargar();
}

async function vaciar(){

    await fetch(
        `${API}/carritos/${carritoId}/vaciar`,
        {
            method:"DELETE"
        }
    );

    cargar();
}

iniciar();
async function cargarCliente() {

    const response = await fetch(
        `${API}/clientes/me`,
        {
            headers: {
                "Authorization":
                    "Basic " + btoa("cliente@bodypaint.com:1234")
            }
        }
    );

    if(!response.ok){
        alert("No se pudo cargar el cliente");
        return;
    }

    clienteActual = await response.json();

    cargarDirecciones(clienteActual.direcciones);
}
async function confirmarPedido() {
    if(clienteActual == null){
        alert("Cliente no cargado");
        return;
    }

    const direccionIndex =
        document.getElementById("direccionSelect").value;

    const formaPago =
        document.getElementById("formaPago").value;

    if(direccionIndex === ""){
        alert("Seleccione un domicilio");
        return;
    }

    if(formaPago === ""){
        alert("Seleccione forma de pago");
        return;
    }

    const direccion =
        clienteActual.direcciones[direccionIndex];

    const body = {
        carritoId: carritoId,
        emailCliente: clienteActual.email,
        domicilioEnvio: direccion,
        formaPago: formaPago
    };

    const response = await fetch(
        `${API}/pedidos/confirmar`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization":
                    "Basic " + btoa("cliente@bodypaint.com:1234")
            },
            body: JSON.stringify(body)
        }
    );

    if(response.ok){
        alert("Pedido confirmado");
    } else {

        const error = await response.text();

        alert(error);
    }
}

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