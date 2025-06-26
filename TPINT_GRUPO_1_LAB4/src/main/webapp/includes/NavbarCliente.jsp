<%@ page contentType="text/html; charset=UTF-8" %>
<%
    entidad.Usuario usuario = (entidad.Usuario) session.getAttribute("usuarioLogueado");
    String nombreUsuario = (usuario != null) ? usuario.getNombreUsuario() : "Invitado";
%>

<nav class="navbar navbar-expand-lg bg-primary px-3">
    <div class="container-fluid">
        <div>
            <a class="navbar-brand text-white" href="InicioCliente.jsp">
                <i class="bi bi-bank me-2 fs-4"></i>
                BANCO
            </a>
        </div>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
            <ul class="navbar-nav gap-4">
            <li class="nav-item">
                    <a class="nav-link text-white d-flex flex-column align-items-center" href="Movimientos.jsp">
                        <i class="bi bi-card-checklist fs-5"></i>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white d-flex flex-column align-items-center" href="Transferencias.jsp">
                        <i class="bi bi-arrow-left-right fs-5"></i>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white d-flex flex-column align-items-center" href="Prestamos.jsp">
                        <i class="bi bi-cash fs-5"></i>
                    </a>
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
