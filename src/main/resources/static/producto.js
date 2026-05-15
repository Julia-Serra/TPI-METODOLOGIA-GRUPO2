const API = "http://localhost:8080";

fetch(`${API}/productos`)
.then(res => res.json())
.then(data => {

    console.log(data);

    const productos =
        data.data || data;

    const grid =
        document.getElementById("productosGrid");

    grid.innerHTML = "";

    productos.forEach(p => {

        grid.innerHTML += `

            <div class="producto-card">

                <img
                    src="https://via.placeholder.com/300x250"
                >

                <h3>${p.nombre}</h3>

                <p>${p.marca}</p>

                <p class="precio">
                    $${p.precio}
                </p>

                <p class="stock">
                    Stock: ${p.stock}
                </p>

                <button onclick="agregar(${p.id})">
                    Agregar al carrito
                </button>

            </div>
        `;
    });
})
.catch(err => {
    console.log(err);
});

async function agregar(id){

    let carritoId =
        localStorage.getItem("carritoId");

    try{

        // SI NO EXISTE -> CREAR
        if(!carritoId){

            const resCarrito =
                await fetch(`${API}/carritos`,{
                    method:"POST"
                });

            const dataCarrito =
                await resCarrito.json();

            carritoId = dataCarrito.data.id;

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

        // SI EL CARRITO YA NO EXISTE
        if(res.status === 500){

            localStorage.removeItem("carritoId");

            agregar(id);

            return;
        }

        const data = await res.json();

        if(!res.ok){

            alert(data.errors);

            return;
        }

        alert("✅ Producto agregado");

    }catch(err){

        console.log(err);

        alert("Error de conexión");
    }
}