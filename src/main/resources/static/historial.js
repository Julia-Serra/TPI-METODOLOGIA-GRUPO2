const API = "http://localhost:8080";
async function cargarHistorial() {
    try {
        const email = getCurrentEmail();
        if (!email) {
            Swal.fire({
                icon: 'error',
                text: 'Debe iniciar sesión'
            });
            return;
        }
        const res = await fetch(
            `${API}/pedidos/historial?email=${encodeURIComponent(email)}`
        );
        const data = await res.json();
        const grid = document.getElementById("historialGrid");
        grid.innerHTML = "";
        if (!data.data || data.data.length === 0) {
            grid.innerHTML = `
                <p class="empty">
                    No tiene pedidos registrados
                </p>
            `;
            return;
        }
        data.data.forEach(pedido => {
            let itemsHtml = "";
            pedido.items.forEach(item => {
                itemsHtml += `
                    <li>
                        ${item.producto.nombre}
                        - Cantidad: ${item.cantidad}
                    </li>
                `;
            });
            grid.innerHTML += `
                <div class="producto-card">
                    <h3>Pedido #${pedido.id}</h3>
                    <p>
                        Fecha:
                        ${pedido.fecha ?? 'Sin fecha'}
                    </p>
                    <p>
                        Estado:
                        ${pedido.estado}
                    </p>
                    <p>
                        Forma de pago:
                        ${pedido.formaPago}
                    </p>
                    <p>
                        Total:
                        $${pedido.total}
                    </p>
                    <h4>Productos</h4>
                    <ul>
                        ${itemsHtml}
                    </ul>
                </div>
            `;
        });
    } catch (error) {
        console.error(error);
        Swal.fire({
            icon: 'error',
            text: 'No se pudo cargar el historial'
        });
    }
}
cargarHistorial();