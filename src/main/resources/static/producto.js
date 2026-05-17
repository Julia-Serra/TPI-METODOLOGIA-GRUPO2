const API = "http://localhost:8080";

fetch(`${API}/productos`)
.then(res => res.json())
.then(data => {

    console.log(data);

    const productos = data.data || data;
    const grid = document.getElementById("productosGrid");

    grid.innerHTML = "";

    productos.forEach(p => {

        const card = document.createElement("div");
        
        // Si el backend marca alerta, le sumamos la clase 'alerta-stock' a la tarjeta
        card.className = p.alertaStockMinimo ? "producto-card alerta-stock" : "producto-card";

        card.innerHTML = `
            <div class="producto-img-container">
                <img src="${API}/uploads/${p.imagen}" 
                    onerror="this.src='https://via.placeholder.com/300x250'">
            </div>
            
            <h3>${p.nombre}</h3>
            <p>${p.marca}</p>

            <p class="precio">
                $${p.precio}
            </p>

            <p class="stock">
                Stock: ${p.stock}
            </p>

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
.catch(err => {
    console.log(err);
});

async function agregar(id){

    let carritoId = localStorage.getItem("carritoId");

    try{
        // SI NO EXISTE -> CREAR
        if(!carritoId){

            const resCarrito = await fetch(`${API}/carritos`,{
                method:"POST"
            });

            const dataCarrito = await resCarrito.json();

            carritoId = dataCarrito.data.id;

            localStorage.setItem(
                "carritoId",
                carritoId
            );
        }

        const res = await fetch(
            `${API}/carritos/${carritoId}/agregar`,
            {
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body: JSON.stringify({
                    productoId:id,
                    cantidad:1
                })
            }
        );

        // SI EL CARRITO YA NO EXISTE
        if(res.status === 500){

            localStorage.removeItem("carritoId");

            agregar(id);

            return;
        }

        const data = await res.json();

        if(!res.ok){
            // Alerta bonita de error personalizada (Fondo oscuro, botón violeta/rosa)
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

        // Alerta bonita de éxito personalizada
        Swal.fire({
            title: '¡Agregado!',
            text: 'Producto agregado al carrito correctamente',
            icon: 'success',
            confirmButtonColor: '#bc2e7e',
            background: '#231932',
            color: '#ffffff',
            timer: 2000 // Se cierra sola a los 2 segundos para que sea más dinámico
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