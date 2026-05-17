const API = "http://localhost:8080";

let carritoId = localStorage.getItem("carritoId");

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

    const carrito = data.data;

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

    document.getElementById("total")
        .innerHTML = `Total: $${carrito.total}`;
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

    cargar();
}

iniciar();