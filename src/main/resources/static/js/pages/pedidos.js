const API = "http://localhost:8080";

requireLogin(["ROLE_VENDEDOR","ROLE_ADMIN"]);

async function cargarPedidos() {
    const res = await fetch(`${API}/pedidos/pendientes`, {
        headers: authHeaders()
    });

    const data = await res.json();
    const grid = document.getElementById("pedidosGrid");
    grid.innerHTML = "";

    data.data.forEach(pedido => {
        grid.innerHTML += `
            <div class="producto-card">
                <h3>Pedido #${pedido.id}</h3>
                <p>${pedido.cliente.email}</p>
            </div>
        `;
    });
}

cargarPedidos();