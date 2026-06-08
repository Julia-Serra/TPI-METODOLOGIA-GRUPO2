const API = "http://localhost:8080";

async function cargarPedidos() {

    try {

        const email = getCurrentEmail();

        let url = `${API}/pedidos/pendientes`;

        if (email) {
            url = `${API}/pedidos/pendientes/cliente?email=${encodeURIComponent(email)}`;
        }

        const res = await fetch(url);
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

            let accionesEstadoHtml = "";
            if (!email) { 
                accionesEstadoHtml = `
                    <select id="estado-${pedido.id}">
                        <option value="LISTO">
                            LISTO
                        </option>

                        <option value="RETIRADO_POR_CORREO">
                            RETIRADO POR CORREO
                        </option>

                        <option value="ENTREGADO">
                            ENTREGADO
                        </option>
                    </select>

                    <button onclick="actualizarEstado(${pedido.id})">
                        Actualizar
                    </button>
                `;
            }

            grid.innerHTML += `

                <div class="producto-card">

                    <h3>Pedido #${pedido.id}</h3>

                    <p>
                        Cliente:
                        ${pedido.cliente.nombre}
                        ${pedido.cliente.apellido}
                    </p>

                    <p>Email: ${pedido.cliente.email}</p>

                    <p>Forma de pago: ${pedido.formaPago}</p>

                    <p>Estado actual: ${pedido.estado}</p>

                    <p>
                        Domicilio:
                        ${pedido.domicilioEnvio.calle}
                        ${pedido.domicilioEnvio.numero},
                        ${pedido.domicilioEnvio.localidad}
                    </p>

                    <button
                        onclick="cancelarPedido(${pedido.id})"
                        class="btn-cancelar"
                    >
                        Cancelar pedido
                    </button>

                    <h4>Productos</h4>

                    <ul>
                        ${itemsHtml}
                    </ul>

                    ${accionesEstadoHtml}

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

                mensaje =
                    errorData.message || mensaje;

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

async function cancelarPedido(id) {

    const { value: motivo } = await Swal.fire({
        title: 'Cancelar pedido',
        input: 'text',
        inputLabel: 'Motivo de cancelación',
        inputPlaceholder: 'Escribí el motivo...',
        showCancelButton: true,
        confirmButtonText: 'Cancelar pedido',
        confirmButtonColor: '#bc2e7e'
    });

    if (!motivo || !motivo.trim()) {

        Swal.fire({
            icon: 'error',
            text: 'Debes ingresar un motivo'
        });

        return;
    }

    try {

        const res = await fetch(
            `${API}/pedidos/${id}/cancelar`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    motivo
                })
            }
        );

        if (!res.ok) {

            let message = "Error al cancelar";

            try {

                const err = await res.json();

                message =
                    err.message || message;

            } catch (e) {}

            throw new Error(message);
        }

        Swal.fire({
            icon: 'success',
            text: 'Pedido cancelado correctamente'
        });

        cargarPedidos();

    } catch (error) {

        Swal.fire({
            icon: 'error',
            text: error.message
        });
    }
}

cargarPedidos();