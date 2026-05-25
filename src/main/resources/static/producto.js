// producto.js

const API = "http://localhost:8080";

cargarProductos();

async function cargarProductos(
    url = `${API}/productos`
){

    try{

        const res =
            await fetch(url);

        const data =
            await res.json();

        const productos =
            data.data || data;

        const grid =
            document.getElementById("productosGrid");

        grid.innerHTML = "";

        if(productos.length === 0){

            grid.innerHTML = `
                <p class="empty">
                    No se encontraron productos
                </p>
            `;

            return;
        }

        productos.forEach(p => {

            const card =
                document.createElement("div");

            card.className =
                p.alertaStockMinimo
                    ? "producto-card producto-alerta-stock"
                    : "producto-card";

            card.innerHTML = `

                <div class="producto-img-container">

                    <img
                        src="${API}/uploads/${p.imagen}"
                        onerror="this.src='https://via.placeholder.com/300x250'"
                    >

                </div>

                <p class="producto-id"><strong>ID: ${p.id}</strong></p>

                <h3>${p.nombre}</h3>

                <p>${p.marca}</p>

                <p class="precio">
                    $${p.precio}
                </p>

                <p class="stock">
                    Stock: ${p.stock}
                </p>

                ${p.alertaStockMinimo
                    ? `
                        <div class="badge-alerta">
                            ⚠️ Stock bajo
                        </div>
                    `
                    : ''
                }

                <button onclick="agregar(${p.id})">
                    Agregar al carrito
                </button>
            `;

            grid.appendChild(card);
        });

    }catch(err){

        Swal.fire({
            title:'Error',
            text:'No se pudieron cargar productos',
            icon:'error',
            confirmButtonColor:'#bc2e7e',
            background:'#231932',
            color:'#ffffff'
        });
    }
}

async function filtrarProductos(){

    const nombre =
        document.getElementById("filtroNombre").value;

    const marca =
        document.getElementById("filtroMarca").value;

    const precioMin =
        document.getElementById("precioMin").value;

    const precioMax =
        document.getElementById("precioMax").value;

    const stockMin =
        document.getElementById("stockMin").value;

    const stockMax =
        document.getElementById("stockMax").value;

    const params =
        new URLSearchParams();

    if(nombre)
        params.append("nombre", nombre);

    if(marca)
        params.append("marca", marca);

    if(precioMin)
        params.append("precioMin", precioMin);

    if(precioMax)
        params.append("precioMax", precioMax);

    if(stockMin)
        params.append("stockMin", stockMin);

    if(stockMax)
        params.append("stockMax", stockMax);

    cargarProductos(
        `${API}/productos/buscar?${params.toString()}`
    );
}

async function agregar(id){

    let carritoId =
        localStorage.getItem("carritoId");

    try{

        if(!carritoId){

            const resCarrito =
                await fetch(`${API}/carritos`,{
                    method:"POST"
                });

            const dataCarrito =
                await resCarrito.json();

            carritoId =
                dataCarrito.data.id;

            localStorage.setItem(
                "carritoId",
                carritoId
            );
        }

        const res =
            await fetch(
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

        const data =
            await res.json();

        if(!res.ok){

            Swal.fire({
                title:'Atención',
                text:data.message || "Stock insuficiente",
                icon:'warning',
                confirmButtonColor:'#bc2e7e',
                background:'#231932',
                color:'#ffffff'
            });

            return;
        }

        Swal.fire({
            title:'Producto agregado',
            text:'Se agregó correctamente al carrito',
            icon:'success',
            timer:1500,
            showConfirmButton:false,
            background:'#231932',
            color:'#ffffff'
        });

    }catch(err){

        Swal.fire({
            title:'Error',
            text:'Error de conexión',
            icon:'error',
            confirmButtonColor:'#bc2e7e',
            background:'#231932',
            color:'#ffffff'
        });
    }
}