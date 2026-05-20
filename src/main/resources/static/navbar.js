// Componente navbar dinámico reutilizable

function renderNavbar() {
  const session = getCurrentSession();
  const navContainer = document.querySelector('.navbar');
  
  if (!navContainer) {
    console.warn('No navbar container found');
    return;
  }

  let navLinksHTML = '<div class="logo">🎨 BodyPaint</div><div class="nav-links">';
  
  if (!session) {
    // Sin autenticación
    navLinksHTML += `
      <a href="/index.html"><button>Productos</button></a>
      <a href="/login.html"><button>Login</button></a>
      <a href="/login.html?mode=register"><button>Registrarse</button></a>
    `;
  } else if (session.role === 'CLIENTE') {
    // Navbar para Cliente
    navLinksHTML += `
      <a href="/index.html"><button>Inicio</button></a>
      <a href="/producto.html"><button>Productos</button></a>
      <a href="/carrito.html"><button>Carrito</button></a>
      <a href="/pedidos.html"><button>Pedidos</button></a>
      <a href="/domicilios.html"><button>Domicilios</button></a>
      <button onclick="logout()" style="background-color: #881c1c; color: white;">Logout</button>
    `;
  } else if (session.role === 'ADMIN') {
    // Navbar para Admin
    navLinksHTML += `
      <a href="/index.html"><button>Inicio</button></a>
      <a href="/admin.html"><button>Admin</button></a>
      <a href="/producto.html"><button>Productos</button></a>
      <button onclick="logout()" style="background-color: #881c1c; color: white;">Logout</button>
    `;
  } else if (session.role === 'VENDEDOR') {
    // Navbar para Vendedor
    navLinksHTML += `
      <a href="/index.html"><button>Inicio</button></a>
      <a href="/vendedor.html"><button>Vendedor</button></a>
      <button onclick="logout()" style="background-color: #881c1c; color: white;">Logout</button>
    `;
  }
  
  navLinksHTML += '</div>';
  navContainer.innerHTML = navLinksHTML;
}

// Renderizar navbar cuando el DOM está listo
document.addEventListener('DOMContentLoaded', renderNavbar);
