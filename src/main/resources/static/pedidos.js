const API = "http://localhost:8080";
const auth = JSON.parse(localStorage.getItem("auth"));

// 🚨 1. Validar sesión
if (!auth) {
    window.location.href = "login.html";
}

// 🚨 2. Validar rol
if (auth.rol !== "ROLE_ADMIN") {
    window.location.href = "productos.html";
}

async function cargarPedidos() {
    try {
        const res = await fetch(`${API}/pedidos/pendientes`, {
            headers: {
                "Authorization": `Bearer ${auth.token}`
            }
        });

        if (res.status === 403) {
            window.location.href = "productos.html";
            return;
        }

        const data = await res.json();
        const grid = document.getElementById("pedidosGrid");

        grid.innerHTML = "";

        if (!data.data || data.data.length === 0) {
            grid.innerHTML = `<p class="empty">No hay pedidos pendientes</p>`;
            return;
        }

        data.data.forEach(pedido => {

            let itemsHtml = "";

            pedido.items.forEach(item => {
                itemsHtml += `
                    <li>
                        ${item.producto.nombre} - Cantidad: ${item.cantidad}
                    </li>
                `;
            });

            grid.innerHTML += `
                <div class="producto-card">
                    <h3>Pedido #${pedido.id}</h3>

                    <p>Cliente: ${pedido.cliente.nombre} ${pedido.cliente.apellido}</p>
                    <p>Email: ${pedido.cliente.email}</p>
                    <p>Forma de pago: ${pedido.formaPago}</p>

                    <p>
                        Domicilio: 
                        ${pedido.domicilioEnvio.calle} 
                        ${pedido.domicilioEnvio.numero}, 
                        ${pedido.domicilioEnvio.localidad}
                    </p>

                    <h4>Productos</h4>
                    <ul>${itemsHtml}</ul>
                </div>
            `;
        });

    } catch (error) {
        console.error(error);

        Swal.fire({
            title: 'Error',
            text: 'No se pudieron cargar los pedidos pendientes',
            icon: 'error',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff'
        });

        document.getElementById("pedidosGrid").innerHTML =
            `<p class="empty">Error al cargar pedidos</p>`;
    }
}

cargarPedidos();

function logout(){
    localStorage.removeItem("auth");
    window.location.href = "index.html";
}