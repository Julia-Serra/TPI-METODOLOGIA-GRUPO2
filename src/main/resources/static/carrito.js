const API = "http://localhost:8080";
let clienteActual = null;

let carritoId = localStorage.getItem("carritoId");
let cuponAplicado = null;
let descuentoAplicado = 0;

// Configuración estética centralizada para SweetAlert2
const swalConfig = {
    confirmButtonColor: '#bc2e7e',
    background: '#231932',
    color: '#ffffff'
};

async function iniciar(){
    if(!carritoId){
        const res = await fetch(`${API}/carritos`,{
            method:"POST"
        });
        const data = await res.json();
        carritoId = data.data.id;
        localStorage.setItem("carritoId", carritoId);
    }
    cargar();
}

async function cargar(){
    const res = await fetch(`${API}/carritos/${carritoId}`);
    const data = await res.json();
    
    if(!data.data){
        localStorage.removeItem("carritoId");
        location.reload();
        return;
    }
    
    const carrito = data.data;
    if(!carrito){
        Swal.fire({
            ...swalConfig,
            title: 'Error',
            text: 'Ocurrió un problema al cargar el contenido de tu carrito.',
            icon: 'error'
        });
        return;
    }

    const div = document.getElementById("items");
    div.innerHTML = "";

    if(carrito.items.length === 0){
        div.innerHTML = `
            <p class="empty">
                Tu carrito está vacío 🛒
            </p>
        `;
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
                    <button class="btn-sumar" onclick="modificar(${item.producto.id}, ${item.cantidad + 1})">+</button>
                    <button class="btn-restar" onclick="modificar(${item.producto.id}, ${item.cantidad - 1})">-</button>
                    <button class="btn-eliminar" onclick="eliminar(${item.producto.id})">Eliminar</button>
                </div>
            </div>
        `;
    });

    let totalFinal = carrito.total;
    if(descuentoAplicado > 0){
        totalFinal = carrito.total - descuentoAplicado;
    }

    document.getElementById("total").innerHTML = `Total: $${totalFinal}`;
}

async function modificar(productoId, cantidad){
    if(cantidad <= 0){
        eliminar(productoId);
        return;
    }

    const res = await fetch(
        `${API}/carritos/${carritoId}/modificar/${productoId}?cantidad=${cantidad}`,
        { method:"PUT" }
    );

    if(!res.ok){
        let msgError = "Stock insuficiente";
        try {
            const dataError = await res.json();
            if(dataError && dataError.message) msgError = dataError.message;
        } catch(e) {}

        Swal.fire({
            ...swalConfig,
            title: '¡Atención!',
            text: msgError,
            icon: 'warning'
        });
        return;
    }
    cargar();
}

async function eliminar(productoId){
    await fetch(
        `${API}/carritos/${carritoId}/quitar/${productoId}`,
        { method:"DELETE" }
    );
    cargar();
}

async function vaciar(){
    await fetch(
        `${API}/carritos/${carritoId}/vaciar`,
        { method:"DELETE" }
    );

    Swal.fire({
        ...swalConfig,
        title: 'Carrito vacío',
        text: 'Se quitaron todos los productos',
        icon: 'success',
        timer: 1500,
        showConfirmButton: false
    });
    
    cuponAplicado = null;
    descuentoAplicado = 0;
    document.getElementById("descuentoAplicado").innerHTML = "";
    cargar();
}

// ==========================================
// BUSCAR CLIENTE OPTIMIZADO
// ==========================================
async function buscarCliente() {
    const email = document.getElementById("emailCliente").value.trim();

    if (!email) {
        Swal.fire({
            ...swalConfig,
            icon: "warning",
            title: "Campo incompleto",
            text: "Por favor ingresa un correo electrónico."
        });
        return;
    }

    // Spinner de carga mientras consulta al backend
    Swal.fire({
        ...swalConfig,
        title: 'Buscando cliente...',
        text: 'Consultando base de datos.',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const response = await fetch(`${API}/clientes/email/${email}`);

        if(!response.ok){
            const error = await response.json();
            Swal.fire({
                ...swalConfig,
                icon: "error",
                title: "Cliente no encontrado",
                text: error.message || "El cliente no existe en el sistema"
            });
            return;
        }

        const data = await response.json();
        clienteActual = data.data;

        if (!clienteActual) {
            Swal.fire({
                ...swalConfig,
                icon: "error",
                title: "Error",
                text: "Ocurrió un error al procesar los datos del cliente."
            });
            return;
        }

        cargarDirecciones(clienteActual.direcciones || []);
        await cargarCupones(clienteActual.email);

        // Alerta de éxito limpia sin romper la UI
        Swal.fire({
            ...swalConfig,
            icon: "success",
            title: "Cliente verificado",
            text: `Bienvenido/a, ${clienteActual.nombre} ${clienteActual.apellido}`,
            timer: 2000,
            showConfirmButton: false
        });

    } catch (e) {
        console.error(e);
        Swal.fire({
            ...swalConfig,
            icon: "error",
            title: "Error de conexión",
            text: "No se pudo establecer comunicación con el servidor."
        });
    }
}

function cargarDirecciones(direcciones){
    const select = document.getElementById("direccionSelect");
    select.innerHTML = '<option value="">Seleccione un domicilio</option>';

    direcciones.forEach((direccion, index) => {
        const option = document.createElement("option");
        option.value = index;
        option.textContent = `${direccion.calle} ${direccion.numero} - ${direccion.localidad}`;
        select.appendChild(option);
    });
}

async function cargarCupones(email){
    try{
        const response = await fetch(`${API}/cupones/cliente/${email}`);
        const cupones = await response.json();
        const select = document.getElementById("cuponSelect");

        select.innerHTML = `<option value="">Seleccione un cupón</option>`;
        cupones.forEach(cupon => {
            select.innerHTML += `<option value="${cupon.codigo}">${cupon.codigo}</option>`;
        });
    }catch(error){
        console.error(error);
    }
}

async function aplicarCupon(){
    const codigo = document.getElementById("cuponSelect").value;

    if(!codigo){
        Swal.fire({ ...swalConfig, icon:"warning", title:"Seleccione un cupón" });
        return;
    }

    const totalTexto = document.getElementById("total").innerText;
    const total = parseFloat(totalTexto.replace("Total: $",""));

    try{
        const response = await fetch(`${API}/cupones/aplicar`, {
            method:"POST",
            headers:{ "Content-Type":"application/json" },
            body:JSON.stringify({
                emailCliente: clienteActual.email,
                codigoCupon: codigo,
                total: total
            })
        });

        const data = await response.json();

        if(!response.ok){
            Swal.fire({
                ...swalConfig,
                icon:"error",
                title: data.message || "No se pudo aplicar el cupón"
            });
            return;
        }

        cuponAplicado = data.codigoCupon;
        descuentoAplicado = data.descuentoAplicado;

        document.getElementById("descuentoAplicado").innerHTML = `
            <p>Cupón: ${data.codigoCupon}</p>
            <p>Descuento: $${data.descuentoAplicado}</p>
            <p>Total Final: $${data.totalFinal}</p>
        `;

        document.getElementById("total").innerHTML = `Total: $${data.totalFinal}`;

        Swal.fire({ ...swalConfig, icon:"success", title:"Cupón aplicado correctamente", timer: 1500, showConfirmButton: false });

    }catch(error){
        console.error(error);
        Swal.fire({ ...swalConfig, icon:"error", title:"Error", text: error.message });
    }
}

// ==========================================
// CONFIRMAR PEDIDO OPTIMIZADO
// ==========================================
async function confirmarPedido() {
    if(clienteActual == null){
        Swal.fire({
            ...swalConfig,
            icon: "warning",
            title: "Faltan datos",
            text: "Por favor, busca e ingresa un cliente antes de continuar."
        });
        return;
    }

    const direccionIndex = document.getElementById("direccionSelect").value;
    const formaPago = document.getElementById("formaPago").value;

    if(direccionIndex === ""){
        Swal.fire({ ...swalConfig, icon: "warning", title: "Domicilio requerido", text: "Seleccione un domicilio de entrega." });
        return;
    }

    if(formaPago === ""){
        Swal.fire({ ...swalConfig, icon: "warning", title: "Método de pago requerido", text: "Seleccione una forma de pago." });
        return;
    }

    // Pantalla de espera interactiva mientras corre la transacción en Spring Boot
    Swal.fire({
        ...swalConfig,
        title: 'Procesando pedido...',
        text: 'Estamos registrando tu compra en el sistema.',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    const direccion = clienteActual.direcciones[direccionIndex];
    const body = {
        carritoId: carritoId,
        emailCliente: clienteActual.email,
        domicilioEnvio: direccion,
        formaPago: formaPago
    };

    try {
        const response = await fetch(`${API}/pedidos/confirmar`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Basic " + btoa("cliente@bodypaint.com:1234")
            },
            body: JSON.stringify(body)
        });

        if(response.ok){
            // Éxito rotundo: limpiamos el storage y mandamos al home al cerrar el modal
            localStorage.removeItem("carritoId");
            Swal.fire({
                ...swalConfig,
                icon: "success",
                title: "¡Pedido confirmado! 🎉",
                text: "Tu orden ha sido registrada correctamente.",
                confirmButtonText: "Volver al inicio"
            }).then(() => {
                window.location.href = "index.html";
            });
        } else {
            const errorText = await response.text();
            Swal.fire({
                ...swalConfig,
                icon: "error",
                title: "No se pudo procesar la orden",
                text: errorText || "Revisá los datos enviados."
            });
        }
    } catch(err) {
        console.error(err);
        Swal.fire({
            ...swalConfig,
            icon: "error",
            title: "Error interno",
            text: "Error de red al intentar impactar la orden."
        });
    }
}

// Inicialización de la app
iniciar();