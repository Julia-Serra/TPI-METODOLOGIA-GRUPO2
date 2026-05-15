const API = "http://localhost:8080";

async function cargarPedidos() {

    try {

        const res = await fetch(`${API}/pedidos/pendientes`);
        const data = await res.json();
        const grid = document.getElementById("pedidosGrid");

        grid.innerHTML = "";

        if (!data.data || data.data.length === 0) {
            grid.innerHTML = `
                <p class="empty">
                    No hay pedidos pendientes
                </p>
            `;
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

                    <p>
                        Cliente:
                        ${pedido.cliente.nombre}
                        ${pedido.cliente.apellido}
                    </p>

                    <p>
                        Email:
                        ${pedido.cliente.email}
                    </p>

                    <p>
                        Forma de pago:
                        ${pedido.formaPago}
                    </p>

                    <p>
                        Domicilio:
                        ${pedido.domicilioEnvio.calle}
                        ${pedido.domicilioEnvio.numero},
                        ${pedido.domicilioEnvio.localidad}
                    </p>

                    <h4>Productos</h4>

                    <ul>
                        ${itemsHtml}
                    </ul>

                </div>
            `;
        });

    } catch (error) {

        document.getElementById("pedidosGrid").innerHTML = `
            <p class="empty">
                Error al cargar pedidos
            </p>
        `;
    }
}

cargarPedidos();