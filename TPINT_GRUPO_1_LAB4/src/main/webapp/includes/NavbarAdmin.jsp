<%@ page contentType="text/html; charset=UTF-8" %>
<%
    entidad.Usuario usuario = (entidad.Usuario) session.getAttribute("usuarioLogueado");
    String nombreUsuario = (usuario != null) ? usuario.getNombreUsuario() : "Invitado";
%>

<!-- NAVBAR SUPERIOR -->
<nav class="navbar navbar-expand bg-primary px-3">
    <div class="container-fluid">
        <a class="navbar-brand text-white" href="InicioAdmin.jsp">Banco</a>
        <div class="d-flex align-items-center gap-3">
            <a href="#" class="text-white d-flex align-items-center text-decoration-none">
                <span class="me-2"><%= nombreUsuario %></span>
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16" role="img" aria-label="Usuario">
                    <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                </svg>
            </a>
            <form method="post" action="ServletCerrarSesion" class="m-0">
                <button type="submit" class="btn btn-light btn-sm">Cerrar sesión</button>
            </form>
        </div>
    </div>
</nav>
