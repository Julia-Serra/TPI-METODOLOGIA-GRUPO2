async function generarCupon() {

    const email =
        document.getElementById("clienteCuponEmail").value;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email)) {

        Swal.fire({
            icon: "warning",
            title: "Email inválido",
            text: "Ingrese una dirección de correo válida.",
            background: "#231932",
            color: "#ffffff",
            confirmButtonColor: "#bc2e7e"
        });

        return;
    }

    const tipoDescuento =
        document.getElementById("tipoDescuento").value;

    const valorDescuento =
        document.getElementById("valorDescuento").value;

    if (
        tipoDescuento === "PORCENTAJE" &&
        valorDescuento > 99
    ) {
        Swal.fire({
            icon: "warning",
            title: "Descuento inválido",
            text: "El porcentaje no puede ser mayor a 99%.",
            background: "#231932",
            color: "#ffffff",
            confirmButtonColor: "#bc2e7e"
        });

        return;
    }

    const fechaDesde =
        document.getElementById("fechaDesde").value;

    const fechaHasta =
        document.getElementById("fechaHasta").value;

    if (!email ||
        !tipoDescuento ||
        !valorDescuento ||
        !fechaDesde ||
        !fechaHasta) {

        Swal.fire({
            icon: "warning",
            title: "Campos incompletos",
            text: "Debe completar todos los campos.",
            background: "#231932",
            color: "#ffffff",
            confirmButtonColor: "#bc2e7e"
        });

        return;
    }

    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);

    const desde = new Date(fechaDesde + "T00:00:00");
    const hasta = new Date(fechaHasta + "T00:00:00");

    if (desde < hoy || hasta < hoy) {

        Swal.fire({
            icon: "warning",
            title: "Fecha inválida",
            text: "No se permiten fechas en el pasado.",
            background: "#231932",
            color: "#ffffff",
            confirmButtonColor: "#bc2e7e"
        });

        return;
    }

    if (hasta < desde) {

        Swal.fire({
            icon: "warning",
            title: "Rango inválido",
            text: "La fecha hasta debe ser posterior o igual a la fecha desde.",
            background: "#231932",
            color: "#ffffff",
            confirmButtonColor: "#bc2e7e"
        });

        return;
    }

    console.log("Hoy:", hoy);
    console.log("Desde:", desde);
    console.log("Hasta:", hasta);

    try {

        // Buscar cliente por email
        const clienteRes = await fetch(
            `${API}/clientes/email/${encodeURIComponent(email)}`,
            {
                headers: {
                    "Authorization": token
                }
            }
        );

        const clienteData = await clienteRes.json();

        if (!clienteRes.ok) {
            throw new Error(
                clienteData.message ||
                "Cliente no encontrado"
            );
        }

        const clienteId = clienteData.data.id;

        console.log({
            clientesIds: [clienteId],
            tipoDescuento,
            valorDescuento: parseFloat(valorDescuento),
            fechaDesde,
            fechaHasta
        });

// Crear cupón
        const res = await fetch(`${API}/cupones`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": token
            },
            body: JSON.stringify({
                clientesIds: [clienteId],
                tipoDescuento,
                valorDescuento: parseFloat(valorDescuento),
                fechaDesde,
                fechaHasta
            })
        });

        const data = await res.json();

        console.log("RESPUESTA CUPON:", data);

        if (!res.ok) {

            throw new Error(
                data.message ||
                "No se pudo generar el cupón"
            );
        }

        Swal.fire({
            icon: "success",
            title: "Cupón generado",
            html: `
            <p><b>Código:</b> ${data.codigo}</p>
            <p><b>Vigencia:</b> ${data.fechaDesde} - ${data.fechaHasta}</p>
            <p><b>Descuento:</b> ${data.valorDescuento}</p>
        `,
                background: "#231932",
                color: "#ffffff",
                confirmButtonColor: "#bc2e7e"
        });

        document.getElementById("clienteCuponEmail").value = "";
        document.getElementById("tipoDescuento").value = "";
        document.getElementById("valorDescuento").value = "";
        document.getElementById("fechaDesde").value = "";
        document.getElementById("fechaHasta").value = "";

    } catch (error) {

        console.error(error);

        Swal.fire({
            icon: "error",
            title: "Error",
            text: error.message,
            background: "#231932",
            color: "#ffffff",
            confirmButtonColor: "#bc2e7e"
        });
    }
}