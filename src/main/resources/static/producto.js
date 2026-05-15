

const API = "http://localhost:8080";

fetch(`${API}/productos`)
.then(res => res.json())
.then(data => {

    const grid =
        document.getElementById("productosGrid");

    data.data.forEach(p => {

        grid.innerHTML += `

            <div class="producto-card">

                <img src="https://via.placeholder.com/300x250">

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
});

async function agregar(id){

    let carritoId =
        localStorage.getItem("carritoId");

    if(!carritoId){

        const res =
            await fetch(`${API}/carritos`,{
                method:"POST"
            });

        const data = await res.json();

        carritoId = data.data.id;

        localStorage.setItem(
            "carritoId",
            carritoId
        );
    }

    fetch(
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
    )
    .then(res => {

        if(!res.ok){
            throw new Error();
        }

        alert("Producto agregado");
    })
    .catch(() => {
        alert("Stock insuficiente");
    });
}