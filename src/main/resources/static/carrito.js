
const API = "http://localhost:8080";

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

    cargar();
}

async function cargar(){

    const res =
        await fetch(
            `${API}/carritos/${carritoId}`
        );

    const data = await res.json();

    const carrito = data.data;

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