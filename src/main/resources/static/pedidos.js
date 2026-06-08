const API = "http://localhost:8080";

// Configuración estética centralizada (Misma de carrito.js e historial.js)
const swalConfig = {
    confirmButtonColor: '#bc2e7e',
    cancelButtonColor: '#444444',
    background: '#231932',
    color: '#ffffff'
};

async function cargarPedidos() {
    // Spinner estético de bloqueo inicial mientras pega al endpoint de Spring Boot
    Swal.fire({
        ...swalConfig,
        title: 'Cargando pedidos...',
        text: 'Obteniendo listado de órdenes pendientes.',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const email = getCurrentEmail();
        let url = `${API}/pedidos/pendientes`;

        if (email) {
            url = `${API}/pedidos/pendientes/cliente?email=${encodeURIComponent(email)}`;
        }

        const res = await fetch(url);
        const data = await res.json();

        // Cerramos el loader al recibir la respuesta con éxito
        Swal.close();

        const grid = document.getElementById("pedidosGrid");
        grid.innerHTML = "";

        if (!data.data || data.data.length === 0) {
            grid.innerHTML = `
                <p class="empty">
                    No hay pedidos pendientes de preparación 🛒
                </p>
            `;
            return;
        }

        data.data.forEach(pedido => {
            let itemsHtml = "";

            pedido.items.forEach(item => {
                itemsHtml += `
                    <li>
                        ${item.producto.nombre} <span style="color: #b3a7c4;">(Cantidad: ${item.cantidad})</span>
                    </li>
                `;
            });

            // Sección para operarios/administradores estilizada como el selector de cupones/domicilios
            let accionesEstadoHtml = "";
            if (!email) { 
                accionesEstadoHtml = `
                    <div style="margin-top: 15px; display: flex; gap: 10px; align-items: center; border-top: 1px dashed rgba(188, 46, 126, 0.15); padding-top: 15px;">
                        <select id="estado-${pedido.id}" style="flex-grow: 1; padding: 10px; border-radius: 6px; background: #2a1f35; color: white; border: 1px solid rgba(188, 46, 126, 0.3); font-size: 0.9rem;">
                            <option value="LISTO">LISTO</option>
                            <option value="RETIRADO_POR_CORREO">RETIRADO POR CORREO</option>
                            <option value="ENTREGADO">ENTREGADO</option>
                        </select>
                        <button class="btn-sumar" style="margin: 0; padding: 10px 15px; font-size: 0.9rem; border-radius: 6px;" onclick="actualizarEstado(${pedido.id})">
                            Actualizar
                        </button>
                    </div>
                `;
            }

            // Inyección usando las clases nativas .item de tu CSS de carrito
            grid.innerHTML += `
                <div class="item">
                    <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid rgba(188, 46, 126, 0.15); padding-bottom: 8px; margin-bottom: 12px;">
                        <h3 style="margin: 0;">Pedido #${pedido.id}</h3>
                        <span style="font-size: 0.85rem; color: #bc2e7e; font-weight: bold;">${pedido.estado}</span>
                    </div>

                    <p>Cliente: <span style="color: #ffffff;">${pedido.cliente.nombre} ${pedido.cliente.apellido}</span></p>
                    <p>Email: <span style="color: #b3a7c4; font-size: 0.9rem;">${pedido.cliente.email}</span></p>
                    <p>Forma de pago: <span style="color: #ffffff;">${pedido.formaPago}</span></p>
                    <p style="font-size: 0.9rem; color: #b3a7c4; line-height: 1.4;">
                        📍 Domicilio: ${pedido.domicilioEnvio.calle} ${pedido.domicilioEnvio.numero}, ${pedido.domicilioEnvio.localidad}
                    </p>
                    
                    <div style="background: rgba(255, 255, 255, 0.03); padding: 12px; border-radius: 8px; margin: 12px 0; border: 1px solid rgba(255, 255, 255, 0.05);">
                        <h4 style="margin: 0 0 8px 0; font-size: 0.95rem; color: #bc2e7e;">Productos a preparar</h4>
                        <ul style="margin: 0; padding-left: 20px; font-size: 0.9rem; color: #ffffff; line-height: 1.5;">
                            ${itemsHtml}
                        </ul>
                    </div>

                    <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 15px;">
                        <button onclick="cancelarPedido(${pedido.id})" class="btn-eliminar" style="margin: 0; font-size: 0.9rem; padding: 8px 16px;">
                            Cancelar pedido
                        </button>
                    </div>

                    ${accionesEstadoHtml}
                </div>
            `;
        });

    } catch (error) {
        console.error(error);
        Swal.fire({
            ...swalConfig,
            title: 'Error',
            text: 'No se pudieron sincronizar los pedidos pendientes.',
            icon: 'error'
        });

        document.getElementById("pedidosGrid").innerHTML = `
            <p class="empty">
                Error al cargar pedidos
            </p>
        `;
    }
}

async function actualizarEstado(idPedido) {
    const estado = document.getElementById(`estado-${idPedido}`).value;

    if (!estado) {
        Swal.fire({
            ...swalConfig,
            title: 'Error',
            text: 'Seleccione un estado válido.',
            icon: 'error'
        });
        return;
    }

    // Bloqueo estético de UI mientras corre la mutación
    Swal.fire({
        ...swalConfig,
        title: 'Actualizando...',
        text: 'Sincronizando nuevo estado en el servidor.',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const res = await fetch(`${API}/pedidos/${idPedido}/estado`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ estado: estado })
        });

        if (!res.ok) {
            let mensaje = "No se pudo actualizar el estado";
            try {
                const errorData = await res.json();
                mensaje = errorData.message || mensaje;
            } catch (e) {}

            Swal.fire({
                ...swalConfig,
                title: 'Error',
                text: mensaje,
                icon: 'error'
            });
            return;
        }

        Swal.fire({
            ...swalConfig,
            title: '¡Actualizado!',
            text: 'Estado cambiado correctamente',
            icon: 'success',
            timer: 1500,
            showConfirmButton: false
        });

        cargarPedidos();

    } catch (error) {
        console.error(error);
        Swal.fire({
            ...swalConfig,
            title: 'Error',
            text: 'Falla de red al intentar conectar con el servidor.',
            icon: 'error'
        });
    }
}

async function cancelarPedido(id) {
    // Alerta interactiva adaptada al layout oscuro y tipografía limpia
    const { value: motivo } = await Swal.fire({
        ...swalConfig,
        title: `Cancelar pedido #${id}`,
        input: 'text',
        inputLabel: 'Escribí detalladamente el motivo de la baja:',
        inputPlaceholder: 'Ingresá el motivo acá...',
        showCancelButton: true,
        confirmButtonText: 'Confirmar cancelación',
        cancelButtonText: 'Volver',
        inputValidator: (value) => {
            if (!value || !value.trim()) {
                return 'Es obligatorio ingresar un motivo para dar de baja el pedido.';
            }
        }
    });

    if (!motivo) return;

    // Spinner intermedio mientras se procesa la cancelación
    Swal.fire({
        ...swalConfig,
        title: 'Procesando baja...',
        text: 'Dando de baja la orden y reestableciendo stock en base de datos.',
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading();
        }
    });

    try {
        const res = await fetch(`${API}/pedidos/${id}/cancelar`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ motivo: motivo.trim() })
        });

        if (!res.ok) {
            let message = "Error al cancelar";
            try {
                const err = await res.json();
                message = err.message || message;
            } catch (e) {}
            throw new Error(message);
        }

        Swal.fire({
            ...swalConfig,
            icon: 'success',
            title: 'Pedido cancelado',
            text: 'La orden fue dada de baja correctamente.',
            timer: 1500,
            showConfirmButton: false
        });

        cargarPedidos();

    } catch (error) {
        Swal.fire({
            ...swalConfig,
            icon: 'error',
            title: 'No se pudo cancelar',
            text: error.message
        });
    }
}

// Inicialización automática al cargar el componente script
cargarPedidos();