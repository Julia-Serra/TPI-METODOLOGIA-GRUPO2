# 🎨 BodyPaint

BodyPaint es una aplicación web desarrollada con Spring Boot para la gestión integral de una tienda de productos. El sistema permite administrar productos, clientes, pedidos, carritos de compra, cupones de descuento y reportes de ventas y stock.

## Funcionalidades

### Gestión de Productos
- Alta de productos.
- Baja lógica de productos.
- Configuración de stock mínimo.
- Carga de imágenes.
- Filtros por:
  - Nombre
  - Marca
  - Precio
  - Stock

###  Gestión de Carrito
- Creación de carritos.
- Agregar productos.
- Modificar cantidades.
- Eliminar productos.
- Vaciar carrito.

###  Gestión de Pedidos
- Confirmación de pedidos.
- Actualización de estados.
- Cancelación de pedidos.
- Historial de compras por cliente.

###  Gestión de Clientes
- Registro de clientes.
- Gestión de domicilios.
- Consulta de información.

###  Sistema de Cupones
- Generación de cupones.
- Aplicación de descuentos.
- Validación de vigencia.
- Cupones por cliente.
- Descuentos:
  - Porcentaje.
  - Monto fijo.

### Reportes
- Productos más vendidos.
- Productos próximos al stock mínimo.
- Alertas de reposición.

---

## Arquitectura

El proyecto sigue una arquitectura en capas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Base de Datos
```

### Capas principales

- Controllers: Exponen los endpoints REST.
- Services: Contienen la lógica de negocio.
- Repositories: Acceso a datos mediante Spring Data JPA.
- DTOs: Transferencia de datos entre capas.
- Mappers: Conversión entre entidades y DTOs.
- Specifications: Filtros dinámicos para búsquedas avanzadas.

---

##  Tecnologías utilizadas

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Maven
- Lombok
- Hibernate

### Frontend
- HTML5
- CSS3
- JavaScript
- SweetAlert2

### Base de Datos
- Compatible con cualquier motor soportado por JPA
  (MySQL, PostgreSQL, H2, etc.)

---

##  Estructura del proyecto

```text
src/
 ├── main/
 │   ├── java/
 │   │   └── com.metodologia.bodyPaint
 │   │       ├── config
 │   │       ├── controllers
 │   │       ├── dtos
 │   │       ├── models
 │   │       ├── repositories
 │   │       ├── services
 │   │       └── specifications
 │   └── resources/
 │       ├── static
 │       └── application.properties
 └── test/
```

##  Instalación

### Clonar repositorio

```bash
git clone https://github.com/usuario/bodypaint.git
cd bodypaint
```

### Ejecutar aplicación

```bash
./mvnw spring-boot:run
```

o en Windows:

```bash
mvnw.cmd spring-boot:run
```

### Compilar

```bash
./mvnw clean install
```

---

##  Acceso

Una vez iniciada la aplicación:

```text
http://localhost:8080
```

La interfaz web se encuentra en los recursos estáticos de Spring Boot.

---

##  Endpoints principales

### Productos

```http
GET    /productos
POST   /productos
DELETE /api/productos/{id}/baja
PUT    /api/productos/{id}/stock-minimo
```

### Pedidos

```http
POST /pedidos/confirmar
PUT  /pedidos/{id}/estado
```

### Cupones

```http
POST /cupones
POST /cupones/aplicar
GET  /cupones/cliente/{email}
```

### Reportes

```http
GET /api/reportes/productos-mas-vendidos
GET /api/reportes/stock-minimo
```
---

##  Historias de Usuario Implementadas

###  Gestión de Clientes

#### US1 - Registrar Cliente
Como cliente quiero registrarme en el sistema con mis datos personales y domicilio para poder realizar compras.

**Funcionalidades implementadas:**
- Registro de datos personales.
- Validación de email.
- Registro de domicilio de envío.
- Control de emails duplicados.

---

#### US8 - Validaciones de Registro
Como cliente quiero que el formulario valide los datos ingresados para evitar errores de carga.

**Validaciones implementadas:**
- Campos obligatorios.
- Validación de email.
- Restricción de caracteres inválidos.
- Mensajes de error para entradas incorrectas.

---

#### US14 - Registrar Domicilio de Envío
Como cliente quiero agregar un domicilio de envío para recibir mis pedidos.

**Funcionalidades implementadas:**
- Registro de país, provincia, localidad, calle y número.
- Validación de datos obligatorios.
- Prevención de domicilios duplicados.

---

###  Gestión de Productos

#### US2 - Crear Producto
Como administrador quiero dar de alta un producto con nombre, marca, precio y stock.

**Funcionalidades implementadas:**
- Alta de productos.
- Validación de precio.
- Validación de stock.
- Disponibilidad inmediata para visualización.

---

#### US3 - Visualizar Productos
Como administrador quiero visualizar los productos existentes.

**Funcionalidades implementadas:**
- Listado completo de productos.
- Visualización de nombre, marca, precio y stock.
- Actualización dinámica.

---

#### US7 - Búsqueda y Filtrado de Productos
Como administrador quiero buscar productos por diferentes criterios para encontrarlos rápidamente.

**Filtros implementados:**
- Nombre.
- Marca.
- Precio mínimo y máximo.
- Stock mínimo y máximo.
- Combinación de múltiples filtros.

---

#### US9 - Configurar Stock Mínimo
Como administrador quiero configurar el stock mínimo de los productos para controlar reposiciones.

**Funcionalidades implementadas:**
- Definición de stock mínimo.
- Validación de valores positivos.
- Control sobre productos existentes.

---

#### US10 - Dar de Baja Producto
Como administrador quiero dar de baja productos para evitar su comercialización.

**Funcionalidades implementadas:**
- Baja lógica de productos.
- Validación de productos ya dados de baja.
- Restricción para productos pertenecientes a kits activos.

---

#### US13 - Importar Imagen para Producto
Como administrador quiero asociar una imagen a un producto para mejorar su visualización.

**Funcionalidades implementadas:**
- Carga de imágenes al crear productos.
- Asociación directa producto-imagen.
- Validaciones de carga.

---

###  Gestión de Compras

#### US4 - Armar Carro de Compras
Como cliente quiero agregar y quitar productos del carrito antes de confirmar una compra.

**Funcionalidades implementadas:**
- Agregar productos.
- Modificar cantidades.
- Eliminar productos.
- Cálculo automático del total.
- Validación de stock disponible.

---

#### US5 - Confirmar Pedido
Como cliente quiero confirmar un pedido con pago contra entrega para adquirir los productos seleccionados.

**Funcionalidades implementadas:**
- Confirmación de pedidos.
- Asociación con cliente y domicilio.
- Selección de forma de pago.
- Validación de stock y datos obligatorios.

---

###  Gestión de Pedidos

#### US6 - Visualizar Pedidos Pendientes
Como vendedor quiero visualizar los pedidos pendientes para gestionar su preparación y entrega.

**Funcionalidades implementadas:**
- Listado de pedidos confirmados.
- Visualización de cliente.
- Productos solicitados.
- Domicilio de entrega.
- Forma de pago.

---

#### US11 - Cancelar Entrega de Pedido
Como vendedor quiero cancelar la entrega de un pedido para evitar envíos incorrectos.

**Funcionalidades implementadas:**
- Cancelación de pedidos.
- Registro obligatorio de motivo.
- Restricción según estado del pedido.

---

#### US12 - Registrar Cumplimiento de Pedido
Como vendedor quiero actualizar el estado de un pedido para reflejar su avance.

**Estados implementados:**
- LISTO
- RETIRADO_POR_CORREO
- ENTREGADO

---

###  Sistema de Cupones

#### Generar Cupón de Descuento
Como vendedor quiero generar cupones de descuento para premiar clientes.

**Funcionalidades implementadas:**
- Generación automática de código.
- Definición de vigencia.
- Descuentos por monto o porcentaje.
- Asociación a clientes específicos.

---

#### Aplicar Cupón de Descuento
Como cliente quiero aplicar un cupón para obtener un descuento en mi compra.

**Validaciones implementadas:**
- Cupón vigente.
- Cupón perteneciente al cliente.
- Cupón no utilizado.
- Código válido.
- Descuento menor al total del pedido.

---

###  Reportes

#### Reporte de Productos Cerca del Stock Mínimo
Como administrador quiero visualizar productos próximos al stock mínimo para anticipar reposiciones.

**Información mostrada:**
- Nombre del producto.
- Código.
- Stock actual.
- Stock mínimo.
- Estado de alerta.

---

#### Reporte de Productos Más Vendidos
Como administrador quiero conocer los productos con mayor volumen de ventas.

**Funcionalidades implementadas:**
- Consulta de productos más vendidos.
- Filtro por mes.
- Filtro por año.
- Validación de filtros inválidos.

---
#### US16 - Reporte de Productos Más Vendidos

Como vendedor quiero generar un reporte de productos más vendidos para conocer cuáles son los artículos con mayor demanda y apoyar la toma de decisiones comerciales.

**Funcionalidades implementadas:**
- Generación automática del ranking de productos más vendidos.
- Visualización de cantidad de ventas por producto.
- Ordenamiento descendente según volumen de ventas.
- Consulta histórica de ventas.
- Información útil para reposición de stock y planificación comercial.

---
### Historial de Pedidos

#### US15 - Visualizar Historial de Pedidos

Como cliente quiero visualizar mi historial de pedidos realizados para consultar compras anteriores y conocer el estado de cada una.

**Funcionalidades implementadas:**
- Visualización de todos los pedidos asociados al cliente.
- Ordenamiento por fecha descendente.
- Consulta de fecha del pedido.
- Consulta de estado actual.
- Consulta de forma de pago utilizada.
- Consulta del total del pedido.
- Visualización de los productos incluidos en cada pedido.
- Restricción de acceso para usuarios no autenticados.

---

##  Seguridad y Validaciones

El sistema incorpora distintas validaciones de negocio:

- Validación de emails.
- Validación de fechas de vigencia.
- Validación de stock.
- Validación de estados de pedido.
- Validación de cupones.
- Manejo global de excepciones.
- Respuestas estructuradas para errores y operaciones exitosas.

---


##  Equipo de Desarrollo

| Integrante | Rol |
|------------|------|
| Meloni Gregorio| Product Owner|
| Pagniez Tobias | Scrum Master |
| Luna Nazareno | developer |
| Lozano Valentino | developer |
| Ledesma Valentina | developer |
| Serra Julia | developer |

---

##  Mejoras Futuras

- Integración con pasarelas de pago.
- Envío de correos automáticos.
- Dashboard administrativo con métricas.
- Gestión de proveedores.
- Control de inventario avanzado.
- Exportación de reportes en PDF y Excel.
- Notificaciones en tiempo real.
- Implementación de JWT para autenticación completa.
- Despliegue en la nube.

---

##  Testing

Para realizar pruebas de los endpoints se puede utilizar:

- Postman
- Insomnia
- Archivo `test.http` incluido en el proyecto

Ejemplo:

```http
POST /pedidos/confirmar
```

```json
{
  "carritoId": 1,
  "emailCliente": "cliente@mail.com",
  "formaPago": "TARJETA"
}
```
---
 Metodología de Trabajo

El proyecto fue desarrollado siguiendo prácticas ágiles inspiradas en Scrum. La planificación y seguimiento de funcionalidades se realizó mediante Historias de Usuario, criterios de aceptación, estimación por Story Points y definición de criterios de inicio y finalización.

Definition of Ready (DoR)

Una Historia de Usuario se consideró lista para comenzar cuando cumplía las siguientes condiciones:

Estaba redactada en formato Como / Quiero / Para.
Poseía criterios de aceptación claros y completos.
Era comprendida por todos los integrantes del equipo.
Estaba estimada en Story Points.
No tenía dependencias bloqueantes.
Poseía un alcance bien definido.
Contaba con información funcional suficiente.
Tenía definidos los datos de entrada y salida necesarios.
Definition of Done (DoD)

Una Historia de Usuario se consideró terminada cuando:

El código estaba implementado.
Se cumplían todos los criterios de aceptación.
Se realizaron pruebas funcionales.
No presentaba errores críticos.
El código se encontraba versionado en Git.
Se realizó revisión de código cuando correspondía.
La funcionalidad estaba integrada sin afectar otras características existentes.
Fue validada por el equipo.
La documentación fue actualizada cuando resultó necesario.
Planning Poker y Estimación

Las historias de usuario fueron estimadas utilizando la técnica Planning Poker, considerando:

### Tabla de Estimaciones (Planning Poker)

| Historia de Usuario | Complejidad | Esfuerzo | Incertidumbre | Story Points |
|---------------------|------------|-----------|---------------|-------------:|
| US1 - Registrar cliente | Media | Media | Baja | 5 |
| US2 - Crear producto | Media | Baja | Baja | 3 |
| US3 - Visualizar productos | Baja | Baja | Nula | 1 |
| US4 - Armar carrito de compras | Media | Media | Baja | 5 |
| US5 - Confirmar pedido | Media | Media | Baja | 5 |
| US6 - Visualizar pedidos pendientes | Baja | Baja | Nula | 2 |
| US7 - Búsqueda y filtrado de productos | Media | Media | Baja | 5 |
| US8 - Validaciones de registro | Baja | Baja | Nula | 2 |
| US9 - Configurar stock mínimo | Media | Media | Baja | 3 |
| US10 - Dar de baja producto | Media | Media | Baja | 3 |
| US11 - Cancelar entrega de pedido | Media | Media | Baja | 5 |
| US12 - Registrar cumplimiento de pedido | Media | Media | Baja | 5 |
| US13 - Importar imagen de producto | Media | Media | Baja | 3 |
| US14 - Registrar domicilio de envío | Media | Media | Baja | 5 |
| US15 - Visualizar historial de pedidos | Media | Media | Baja | 5 |
| US16 - Generar cupón de descuento | Alta | Alta | Media | 5 |
| US17 - Aplicar cupón de descuento | Media | Media | Baja | 5 |
| US18 - Reporte de productos más vendidos | Media | Media | Baja | 3 |
| US19 - Reporte de productos cerca del stock mínimo | Media | Media | Baja | 3 |


---

a
