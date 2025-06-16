<%@ page contentType="text/html; charset=UTF-8" %>
<%
    entidad.Usuario usuario = (entidad.Usuario) session.getAttribute("usuarioLogueado");
    String nombreUsuario = (usuario != null) ? usuario.getNombreUsuario() : "Invitado";
%>

<nav class="navbar navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand text-white" href="InicioCliente.jsp">Banco</a>
        <div class="d-flex align-items-center">
            <a href="DatosPersonales.jsp" class="text-white d-flex align-items-center mr-3" style="text-decoration: none;">
                <span class="mr-2"><%= nombreUsuario %></span>
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
					<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
				</svg>
            </a>

            <form method="post" action="ServletCerrarSesion">
                <input type="submit" class="btn btn-light" value="Cerrar sesión" />
            </form>
        </div>
    </div>
</nav>