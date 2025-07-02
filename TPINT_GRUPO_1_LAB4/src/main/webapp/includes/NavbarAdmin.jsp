<%@ page contentType="text/html; charset=UTF-8" %>
<%
    entidad.Usuario usuario = (entidad.Usuario) session.getAttribute("usuarioLogueado");
    String nombreUsuario = (usuario != null) ? usuario.getNombreUsuario() : "Invitado";
%>

<nav class="navbar navbar-expand bg-primary px-3" style="position: sticky; top: 0; z-index: 1040;">
    <div class="container-fluid">
	    <a class="navbar-brand text-white" href="<%= request.getContextPath() %>/ServletInformes">
		    <i class="bi bi-bank me-2 fs-4"></i>
		    BANCO
		</a>
        <div class="d-flex align-items-center gap-3">
            <a href="#" class="text-white d-flex align-items-center text-decoration-none">
                <span class="me-2"><%= nombreUsuario %></span>
				<i class="bi bi-person-circle fs-3"></i>
            </a>
            <form method="post" action="ServletCerrarSesion" class="m-0">
                <button type="submit" class="btn btn-light btn-sm">Cerrar sesión</button>
            </form>
        </div>
    </div>
</nav>
