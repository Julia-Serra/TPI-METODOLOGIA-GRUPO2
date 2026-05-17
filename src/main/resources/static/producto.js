const API = "http://localhost:8080";
const auth = localStorage.getItem("auth");

// helper para headers
function authHeaders(extra = {}) {
    return {
        "Authorization": auth,
        ...extra
    };
}

// ================== CARGA DE PRODUCTOS ==================

fetch(`${API}/productos`, {
    headers: authHeaders()
})
.then(res => res.json())
.then(data => {

    const productos = data.data || data;
    const grid = document.getElementById("productosGrid");

    grid.innerHTML = "";

    productos.forEach(p => {

        const card = document.createElement("div");
        card.className = p.alertaStockMinimo
            ? "producto-card alerta-stock"
            : "producto-card";

        card.innerHTML = `
            <div class="producto-img-container">
                <img src="${API}/uploads/${p.imagen}" 
                     onerror="this.src='https://via.placeholder.com/300x250'">
            </div>
            
            <h3>${p.nombre}</h3>
            <p>${p.marca}</p>

            <p class="precio">$${p.precio}</p>
            <p class="stock">Stock: ${p.stock}</p>

            ${p.alertaStockMinimo ? `
                <div class="badge-advertencia">
                    ⚠️ Stock bajo (Min: ${p.stockMinimo})
                </div>
            ` : ''}

            <button onclick="agregar(${p.id})">
                Agregar al carrito
            </button>
        `;

        grid.appendChild(card);
    });
})
.catch(console.log);


// ================== AGREGAR AL CARRITO ==================

async function agregar(id){

    let carritoId = localStorage.getItem("carritoId");

    try{

        // ===== CREAR CARRITO SI NO EXISTE =====
        if(!carritoId){

            const resCarrito = await fetch(`${API}/carritos`,{
                method:"POST",
                headers: authHeaders()
            });

            const dataCarrito = await resCarrito.json();
            carritoId = dataCarrito.data.id;

            localStorage.setItem("carritoId", carritoId);
        }

        // ===== AGREGAR PRODUCTO =====
        const res = await fetch(
            `${API}/carritos/${carritoId}/agregar`,
            {
                method:"POST",
                headers: authHeaders({
                    "Content-Type":"application/json"
                }),
                body: JSON.stringify({
                    productoId:id,
                    cantidad:1
                })
            }
        );

        // Si el carrito ya no existe, lo recrea
        if(res.status === 500){
            localStorage.removeItem("carritoId");
            agregar(id);
            return;
        }

        const data = await res.json();

        if(!res.ok){
            Swal.fire({
                title: '¡Atención!',
                text: data.errors || 'Stock insuficiente',
                icon: 'warning',
                confirmButtonColor: '#bc2e7e',
                background: '#231932',
                color: '#ffffff'
            });
            return;
        }

        Swal.fire({
            title: '¡Agregado!',
            text: 'Producto agregado al carrito correctamente',
            icon: 'success',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff',
            timer: 2000
        });

    }catch(err){
        console.log(err);

        Swal.fire({
            title: 'Error',
            text: 'Error de conexión con el servidor',
            icon: 'error',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff'
        });
    }
}

function logout(){
    localStorage.removeItem("auth");
    window.location.href = "index.html";
}