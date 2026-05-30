document.getElementById('btnGenerar').addEventListener('click', generarReporte);

async function generarReporte() {
    const anio = document.getElementById('filterAnio').value;
    const mes = document.getElementById('filterMes').value;
    const tbody = document.getElementById('tablaReporteBody');

    // Construimos la URL dinámica hacia tu endpoint
    let url = '/api/reportes/productos-mas-vendidos?';
    if (anio) url += `anio=${anio}&`;
    if (mes) url += `mes=${mes}`;

    try {
        tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 20px;">Calculando ranking...</td></tr>';

        const response = await fetch(url);
        const result = await response.json(); // Leemos la estructura BaseResponse

        // Si el backend responde con error (BadRequestException), response.ok es false
        if (!response.ok) {
            throw new Error(result.message || 'Error al generar el reporte.');
        }

        // Limpiamos la tabla para meter los datos nuevos
        tbody.innerHTML = '';

        // Iteramos sobre la lista que viene adentro de 'data' en tu BaseResponse
        result.data.forEach((item, index) => {
            const row = document.createElement('tr');
            row.classList.add('data-row');
            row.innerHTML = `
                <td style="padding: 12px; border: 1px solid #ddd; text-align: center; font-weight: bold; color: #bc2e7e;">${index + 1}</td>
                <td style="padding: 12px; border: 1px solid #ddd;">${item.productoId}</td>
                <td style="padding: 12px; border: 1px solid #ddd; font-weight: bold;">${item.nombre}</td>
                <td style="padding: 12px; border: 1px solid #ddd;">${item.marca}</td>
                <td style="padding: 12px; border: 1px solid #ddd; color: #2e7d32; font-weight: bold;"><span class="badge-quantity">${item.cantidadTotalVendida} unidades</span></td>
            `;
            tbody.appendChild(row);
        });

    } catch (error) {
        // En caso de falla, vaciamos la tabla y mostramos el mensaje de error del backend
        tbody.innerHTML = `
            <tr>
                <td colspan="5" style="text-align: center; padding: 20px; color: #d32f2f; background-color: #ffebee; font-weight: bold;">
                    ⚠️ ${error.message}
                </td>
            </tr>
        `;

        // Alerta estética con SweetAlert2
        Swal.fire({
            icon: 'error',
            title: 'Atención',
            text: error.message,
            confirmButtonColor: '#002b49'
        });
    }
}