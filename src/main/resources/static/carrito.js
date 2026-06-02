const API = "http://localhost:8080";
let clienteActual = null;

let carritoId = localStorage.getItem("carritoId");
let cuponAplicado = null;
let descuentoAplicado = 0;
async function iniciar(){

    if(!carritoId){

        const res = await fetch(`${API}/carritos`,{
            method:"POST"
        });

        const data = await res.json();

        carritoId = data.data.id;

        localStorage.setItem(
            "carritoId",
            carritoId
        );
    }
    cargar();
}

async function cargar(){

    const res = await fetch(
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

    const div = document.getElementById("items");

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

        const subtotal = item.producto.precio * item.cantidad;

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

    let totalFinal =
        carrito.total;

    if(descuentoAplicado > 0){

        totalFinal =
            carrito.total -
            descuentoAplicado;
    }

    document.getElementById("total")
        .innerHTML =
        `Total: $${totalFinal}`;
}

async function modificar(productoId,cantidad){

    if(cantidad <= 0){
        eliminar(productoId);
        return;
    }

    const res = await fetch(
        `${API}/carritos/${carritoId}/modificar/${productoId}?cantidad=${cantidad}`,
        {
            method:"PUT"
        }
    );

    if(!res.ok){
        // Intentamos leer el error del backend, si no viene usamos el mensaje por defecto
        let msgError = "Stock insuficiente";
        try {
            const dataError = await res.json();
            if(dataError && dataError.message) msgError = dataError.message;
        } catch(e) {}

        // Alerta estética con SweetAlert2 para el límite de stock
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

    // Alerta rápida para avisar que se vació correctamente
    Swal.fire({
        title: 'Carrito vacío',
        text: 'Se quitaron todos los productos',
        icon: 'success',
        confirmButtonColor: '#bc2e7e',
        background: '#231932',
        color: '#ffffff',
        timer: 1500
    });
    cuponAplicado = null;
    descuentoAplicado = 0;

    document.getElementById(
        "descuentoAplicado"
    ).innerHTML = "";

    cargar();
}

iniciar();

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
        ).value.trim();

    if (!email) {
        alert("Por favor ingresa un email");
        return;
    }

    try {
        const response =
            await fetch(
                `${API}/clientes/email/${email}`
            );

        if(!response.ok){
            const error = await response.json();
            alert("Cliente no encontrado: " + (error.message || "El cliente no existe en el sistema"));
            return;
        }

        const data = await response.json();

        clienteActual = data.data;

        if (!clienteActual) {
            alert("Error al cargar datos del cliente");
            return;
        }

        cargarDirecciones(
            clienteActual.direcciones || []
        );
        await cargarCupones(
            clienteActual.email
        );

        alert("✅ Cliente encontrado: " + clienteActual.nombre + " " + clienteActual.apellido);

    } catch (e) {
        console.error(e);
        alert("❌ Error de conexión: " + e.message);
    }
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
async function cargarCupones(email){

    try{

        const response =
            await fetch(
                `${API}/cupones/cliente/${email}`
            );

        const cupones =
            await response.json();

        const select =
            document.getElementById(
                "cuponSelect"
            );

        select.innerHTML =
            `<option value="">
                Seleccione un cupón
            </option>`;

        cupones.forEach(cupon => {

            select.innerHTML += `
                <option value="${cupon.codigo}">
                    ${cupon.codigo}
                </option>
            `;
        });

    }catch(error){

        console.error(error);
    }
}
async function aplicarCupon(){

    const codigo =
        document.getElementById(
            "cuponSelect"
        ).value;

    if(!codigo){

        Swal.fire({
            icon:"warning",
            title:"Seleccione un cupón"
        });

        return;
    }

    const totalTexto =
        document.getElementById("total")
            .innerText;

    const total =
        parseFloat(
            totalTexto.replace("Total: $","")
        );

    try{

        const response =
            await fetch(
                `${API}/cupones/aplicar`,
                {
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify({
                        emailCliente:
                            clienteActual.email,
                        codigoCupon:
                            codigo,
                        total:
                            total
                    })
                }
            );

        const data =
            await response.json();

        if(!response.ok){

            Swal.fire({
                icon:"error",
                title:
                    data.message ||
                    "No se pudo aplicar el cupón"
            });

            return;
        }

        cuponAplicado =
            data.codigoCupon;

        descuentoAplicado =
            data.descuentoAplicado;

        document
            .getElementById(
                "descuentoAplicado"
            )
            .innerHTML =
            `
            <p>
                Cupón: ${data.codigoCupon}
            </p>

            <p>
                Descuento: $${data.descuentoAplicado}
            </p>

            <p>
                Total Final: $${data.totalFinal}
            </p>
            `;

        document.getElementById("total")
            .innerHTML =
            `Total: $${data.totalFinal}`;

        Swal.fire({
            icon:"success",
            title:"Cupón aplicado correctamente"
        });

    }catch(error){

        console.error(error);

        Swal.fire({
            icon:"error",
            title:"Error",
            text:error.message
        });
    }
}