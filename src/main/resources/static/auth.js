// Sistema de autenticación simple con sessionStorage
// Usuarios predefinidos (solo para demostración)

const USERS = {
  'admin@bodypaint.com': { password: '1234', role: 'ADMIN' },
  'cliente@bodypaint.com': { password: '1234', role: 'CLIENTE' },
  'vendedor@bodypaint.com': { password: '1234', role: 'VENDEDOR' }
};

// Obtener sesión actual
function getCurrentSession() {
  const session = sessionStorage.getItem('auth_session');
  return session ? JSON.parse(session) : null;
}

// Obtener rol actual
function getCurrentRole() {
  const session = getCurrentSession();
  return session ? session.role : null;
}

// Verificar si está autenticado
function isAuthenticated() {
  return getCurrentSession() !== null;
}

// Verificar si tiene un rol específico
function hasRole(role) {
  const currentRole = getCurrentRole();
  return currentRole === role;
}

// Login
function login(email, password) {
  const user = USERS[email];
  
  if (!user) {
    return { success: false, message: 'Email no registrado' };
  }
  
  if (user.password !== password) {
    return { success: false, message: 'Contraseña incorrecta' };
  }
  
  // Crear sesión
  const session = {
    email: email,
    role: user.role,
    token: 'Bearer ' + btoa(email + ':' + password),
    loginTime: new Date().toISOString()
  };
  
  sessionStorage.setItem('auth_session', JSON.stringify(session));
  return { success: true, role: user.role };
}

// Registro (solo para rol CLIENTE)
function register(email, password, confirmPassword) {
  if (password !== confirmPassword) {
    return { success: false, message: 'Las contraseñas no coinciden' };
  }
  
  if (password.length < 4) {
    return { success: false, message: 'La contraseña debe tener al menos 4 caracteres' };
  }
  
  if (USERS[email]) {
    return { success: false, message: 'Email ya registrado' };
  }
  
  // Registrar nuevo usuario con rol CLIENTE
  USERS[email] = { password: password, role: 'CLIENTE' };
  
  // Hacer login automático
  return login(email, password);
}

// Logout
function logout() {
  sessionStorage.removeItem('auth_session');
  window.location.href = '/index.html';
}

// Redirigir si no está autenticado
function requireAuth() {
  if (!isAuthenticated()) {
    window.location.href = '/login.html';
  }
}

// Redirigir si no tiene el rol adecuado
function requireRole(role) {
  if (!isAuthenticated() || !hasRole(role)) {
    window.location.href = '/index.html';
  }
}

// Obtener email del usuario actual
function getCurrentEmail() {
  const session = getCurrentSession();
  return session ? session.email : null;
}

// Obtener token del usuario actual
function getCurrentToken() {
  const session = getCurrentSession();
  return session ? session.token : null;
}
