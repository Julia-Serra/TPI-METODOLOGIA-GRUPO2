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
            
                    <p>Cliente: ${pedido.cliente.nombre} ${pedido.cliente.apellido}</p>
            
                    <p>Email: ${pedido.cliente.email}</p>
            
                    <p>Forma de pago: ${pedido.formaPago}</p>
            
                    <p>Estado actual: ${pedido.estado}</p>
            
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
            
                    <select id="estado-${pedido.id}">
                        <option value="LISTO">LISTO</option>
                        <option value="RETIRADO_POR_CORREO">RETIRADO POR CORREO</option>
                        <option value="ENTREGADO">ENTREGADO</option>
                    </select>
            
                    <button onclick="actualizarEstado(${pedido.id})">
                        Actualizar
                    </button>
            
                </div>
            `;
        });

    } catch (error) {
        console.error(error);
        
        // Alerta estética si se cae el servidor o falla la conexión
        Swal.fire({
            title: 'Error',
            text: 'No se pudieron cargar los pedidos pendientes',
            icon: 'error',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff'
        });

        document.getElementById("pedidosGrid").innerHTML = `
            <p class="empty">
                Error al cargar pedidos
            </p>
        `;
    }
}

async function actualizarEstado(idPedido) {

    const estado =
        document.getElementById(`estado-${idPedido}`).value;

    if (!estado) {

        Swal.fire({
            title: 'Error',
            text: 'Seleccione un estado',
            icon: 'error'
        });

        return;
    }

    try {

        const res = await fetch(
            `${API}/pedidos/${idPedido}/estado`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    estado: estado
                })
            }
        );

        if (!res.ok) {

            let mensaje = "No se pudo actualizar";

            try {
                const errorData = await res.json();
                mensaje = errorData.message || mensaje;
            } catch (e) {}

            Swal.fire({
                title: 'Error',
                text: mensaje,
                icon: 'error',
                confirmButtonColor: '#bc2e7e',
                background: '#231932',
                color: '#ffffff'
            });

            return;
        }

        Swal.fire({
            title: 'Actualizado',
            text: 'Estado cambiado correctamente',
            icon: 'success',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff'
        });

        cargarPedidos();

    } catch (error) {

        console.error(error);

        Swal.fire({
            title: 'Error',
            text: 'Error de conexión',
            icon: 'error',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff'
        });
    }
}

cargarPedidos();