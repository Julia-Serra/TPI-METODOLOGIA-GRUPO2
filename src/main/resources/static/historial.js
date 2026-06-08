// historial.js
const API = "http://localhost:8080";

// Configuración estética centralizada (Misma de carrito.js)
const swalConfig = {
    confirmButtonColor: '#bc2e7e',
    background: '#231932',
    color: '#ffffff'
};

async function cargarHistorial() {
    try {
        const email = getCurrentEmail();
        
        if (!email) {
            Swal.fire({
                ...swalConfig,
                icon: 'error',
                title: 'Acceso restringido',
                text: 'Debe iniciar sesión para ver su historial de pedidos.'
            });
            return;
        }

        // Bloqueo estético de pantalla mientras consulta al backend en Spring Boot
        Swal.fire({
            ...swalConfig,
            title: 'Cargando historial...',
            text: 'Buscando tus órdenes registradas.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        const res = await fetch(
            `${API}/pedidos/historial?email=${encodeURIComponent(email)}`
        );
        const data = await res.json();

        // Cerramos el loader al recibir la respuesta
        Swal.close();

        const grid = document.getElementById("historialGrid");
        grid.innerHTML = "";

        if (!data.data || data.data.length === 0) {
            grid.innerHTML = `
                <p class="empty">
                    No tenés pedidos registrados u órdenes previas 🛒
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

            // Formateo de fecha limpia (Día/Mes/Año)
            let fechaFormateada = pedido.fecha ?? 'Sin fecha';
            if (pedido.fecha && !isNaN(Date.parse(pedido.fecha))) {
                fechaFormateada = new Date(pedido.fecha).toLocaleDateString('es-AR', {
                    day: '2-digit',
                    month: '2-digit',
                    year: 'numeric'
                });
            }

            // Identificación visual por estados usando la paleta oscura/fucsia de la app
            let estadoEstilo = 'color: #bc2e7e; font-weight: bold;'; // Fucsia por defecto (Pendiente/Listo)
            if (pedido.estado === 'CANCELADO') {
                estadoEstilo = 'color: #ef4444; font-weight: bold; text-decoration: line-through;';
            } else if (pedido.estado === 'ENTREGADO') {
                estadoEstilo = 'color: #10b981; font-weight: bold;'; // Verde para completados
            }

            // Inyección de HTML calcando la estructura visual de carrito.html (Clase .item y subtotales)
            grid.innerHTML += `
                <div class="item">
                    <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid rgba(188, 46, 126, 0.15); padding-bottom: 8px; margin-bottom: 12px;">
                        <h3 style="margin: 0;">Pedido #${pedido.id}</h3>
                        <span style="font-size: 0.85rem; color: #b3a7c4;">${fechaFormateada}</span>
                    </div>

                    <p>Forma de pago: <span style="color: #ffffff;">${pedido.formaPago}</span></p>
                    <p>Estado actual: <span style="${estadoEstilo}">${pedido.estado}</span></p>
                    
                    <div style="background: rgba(255, 255, 255, 0.03); padding: 12px; border-radius: 8px; margin: 12px 0; border: 1px solid rgba(255, 255, 255, 0.05);">
                        <h4 style="margin: 0 0 8px 0; font-size: 0.95rem; color: #bc2e7e;">Detalle de Artículos</h4>
                        <ul style="margin: 0; padding-left: 20px; font-size: 0.9rem; color: #ffffff; line-height: 1.5;">
                            ${itemsHtml}
                        </ul>
                    </div>

                    <p style="font-size: 1.1rem; font-weight: bold; margin-top: 10px; color: #ffffff;">
                        Total: <span style="color: #bc2e7e;">$${pedido.total}</span>
                    </p>
                </div>
            `;
        });
    } catch (error) {
        console.error(error);
        Swal.fire({
            ...swalConfig,
            icon: 'error',
            title: 'Error de conexión',
            text: 'No se pudo sincronizar el historial con el servidor.'
        });

        document.getElementById("historialGrid").innerHTML = `
            <p class="empty">
                Error al cargar el historial
            </p>
        `;
    }
}

// Inicialización automática
cargarHistorial();