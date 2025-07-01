<%@ page contentType="text/html; charset=UTF-8" %>
<%
    entidad.Usuario usuario = (entidad.Usuario) session.getAttribute("usuarioLogueado");
    String nombreUsuario = (usuario != null) ? usuario.getNombreUsuario() : "Invitado";
%>

<nav class="navbar navbar-expand-lg bg-primary px-3">
    <div class="container-fluid">
        <div class="d-flex align-items-center gap-4">
            <a class="navbar-brand text-white" href="ServletInformeA">
                <i class="bi bi-bank me-2 fs-4"></i>
                BANCO
            </a>
            <ul class="navbar-nav flex-row gap-4">
                <li class="nav-item">
                    <a class="nav-link text-white" href="Transferencias.jsp">Transferencias</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="Prestamos.jsp">Préstamos</a>
                </li>
            </ul>
        </div>

        <div class="d-flex align-items-center gap-3">
            <a href="DatosPersonales.jsp" class="text-white d-flex align-items-center text-decoration-none">
                <span class="me-2"><%= nombreUsuario %></span>
                <i class="bi bi-person-circle fs-3"></i>
            </a>
            <form method="post" action="ServletCerrarSesion" class="m-0">
                <button type="submit" class="btn btn-light btn-sm">Cerrar sesión</button>
            </form>
        </div>
    </div>
</nav>

