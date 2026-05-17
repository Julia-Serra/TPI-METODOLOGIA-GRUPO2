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
                        <option value="PENDIENTE_PREPARACION">PENDIENTE PREPARACION</option>
                        <option value="CONFIRMADO">CONFIRMADO</option>
                        <option value="LISTO">LISTO</option>
                        <option value="ENTREGADO_POR_CORRERO">ENTREGADO POR CORREO</option>
                        <option value="CANCELADO">CANCELADO</option>
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
        alert("Seleccione un estado");
        return;
    }

    try {

        const res = await fetch(
            `${API}/pedidos/${idPedido}/estado?estado=${estado}`,
            {
                method: "PUT"
            }
        );

        const data = await res.json();

        if (!res.ok) {
            Swal.fire({
                title: 'Error',
                text: data.message || "No se pudo actualizar",
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