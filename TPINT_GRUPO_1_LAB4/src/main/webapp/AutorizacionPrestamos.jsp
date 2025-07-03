<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="entidad.Cuenta" %>
<%@ page import="entidad.Cliente" %>
<%@ page import="entidad.Prestamo" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Préstamos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="margin: 0; padding: 0;">

	<%@ include file="includes/NavbarAdmin.jsp" %>

	<div class="d-flex">

		<%@ include file="includes/SidebarAdmin.jsp" %>

		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4 class="mb-4">Préstamos</h4>
			<hr />
			<br />
			<div class="row mb-4 align-items-end">
				<div class="col-md-12">
					<%
					    String currentBusqueda = request.getParameter("busqueda") != null ? request.getParameter("busqueda") : "";
					    // Importante: El filtro "filtro" del JSP sigue siendo 0, 1, 2. No lo cambiamos.
					    String currentFiltro = request.getParameter("filtro") != null ? request.getParameter("filtro") : "";
					
					    int paginaActual = request.getAttribute("paginaActual") != null ? (int) request.getAttribute("paginaActual") : 1;
					    int totalPaginas = request.getAttribute("totalPaginas") != null ? (int) request.getAttribute("totalPaginas") : 1;
					%>
					<form method="get" action="ServletAutorizacionPrestamos" class="row gx-2 gy-2">
						<input type="hidden" name="accion" value="listar">
						<div class="col-md-4 col-sm-12">
							<input type="text" name="busqueda" class="form-control" placeholder="Buscar por DNI o Nombre" value="<%= currentBusqueda %>">
						</div>

						<div class="col-md-4 col-sm-12">
							<select name="filtro" class="form-select">
								<option value="" <%= "".equals(currentFiltro) ? "selected" : "" %>>Todos los estados</option>
								<option value="1" <%= "1".equals(currentFiltro) ? "selected" : "" %>>Pendiente</option>
								<option value="2" <%= "2".equals(currentFiltro) ? "selected" : "" %>>Aprobado</option>
								<option value="0" <%= "0".equals(currentFiltro) ? "selected" : "" %>>Rechazado</option>
							</select>
						</div>

						<div class="col-md-2 col-sm-6">
							<button class="btn btn-primary w-100" type="submit">
								<i class="bi bi-search me-1"></i> Buscar
							</button>
						</div>
						<div class="col-md-2 col-sm-6">
						    <a href="ServletAutorizacionPrestamos?accion=listar" class="btn btn-secondary w-100">
						        <i class="bi bi-x-circle me-1"></i> Limpiar
						    </a>
						</div>
					</form>
				</div>
			</div>

		<div class="table-responsive">
			<table class="table table-bordered table-hover align-middle">
				<thead class="table-primary">
					<tr>
						<th>ID Préstamo</th>
						<th>Cliente (DNI)</th>
						<th>Cuenta destino</th>
						<th>Fecha solicitud</th>
						<th>Importe solicitado</th>
						<th>Plazo (meses)</th>
						<th>Cuotas</th>
						<th>Cuota mensual</th>
						<th>Estado</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
<%
List<Prestamo> listaPrestamos = (List<Prestamo>) request.getAttribute("listaPrestamos");

if (listaPrestamos != null && !listaPrestamos.isEmpty()) {
    for (Prestamo p : listaPrestamos) {
%>
    <tr>
        <td><%= p.getIdPrestamo() %></td>
        <td><%= p.getCliente().getNombre() %> <%= p.getCliente().getApellido() %> (<%= p.getCliente().getDni() %>)</td>
        <td><%= p.getCuenta().getNumeroCuenta() %></td>
        <td><%= p.getFechaAlta() %></td>
        <td>$<%= String.format("%,.2f", p.getImportePedido()) %></td>
        <td><%= p.getPlazoMeses() %></td>
        <td><%= p.getCantidadCuotas() %></td>
        <td>$<%= String.format("%,.2f", p.getMontoCuota()) %></td>
        <td>
            <%
                String estadoDisplay = "Desconocido";
                String badgeClass = "bg-secondary";
                // AQUI ES EL CAMBIO: Usar las cadenas de texto de la BD
                switch(p.getEstado()) {
                    case "Rechazado": estadoDisplay = "Rechazado"; badgeClass = "bg-danger"; break;
                    case "Pendiente": estadoDisplay = "Pendiente"; badgeClass = "bg-warning text-dark"; break;
                    case "Aprobado": estadoDisplay = "Aprobado"; badgeClass = "bg-success"; break;
                    default: estadoDisplay = p.getEstado(); // Para mostrar cualquier otro valor inesperado
                }
            %>
            <span class="badge <%= badgeClass %>"><%= estadoDisplay %></span>
        </td>
        <td>
            <div class="d-flex flex-nowrap">
                <% 
                // AQUI ES EL CAMBIO: Comparar con la cadena de texto "Pendiente"
                if ("Pendiente".equals(p.getEstado())) { 
                %>
                <form action="ServletAutorizacionPrestamos" method="post" style="display:inline;" class="me-1">
                    <input type="hidden" name="accion" value="aprobarPrestamo" />
                    <input type="hidden" name="idPrestamo" value="<%= p.getIdPrestamo() %>" />
                    <button class="btn btn-sm btn-success" title="Aprobar préstamo">
                        <i class="bi bi-check-circle"></i>
                    </button>
                </form>
                <form action="ServletAutorizacionPrestamos" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="rechazarPrestamo" />
                    <input type="hidden" name="idPrestamo" value="<%= p.getIdPrestamo() %>" />
                    <button class="btn btn-sm btn-danger" title="Rechazar préstamo">
                        <i class="bi bi-x-circle"></i>
                    </button>
                </form>
                <% } else { %>
                    <span class="text-muted fst-italic">Acción no disponible</span>
                <% } %>
            </div>
        </td>
    </tr>
<%
    }
} else {
%>
    <tr>
        <td colspan="10" class="text-center py-4">No se encontraron préstamos con los criterios especificados.</td>
    </tr>
<%
}
%>
				</tbody>
			</table>
		</div>

		<nav aria-label="Paginación de Préstamos">
		  <ul class="pagination justify-content-center mt-4">

		    <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
		      <a class="page-link" href="ServletAutorizacionPrestamos?accion=listar&pagina=<%= paginaActual - 1 %>&busqueda=<%= currentBusqueda %>&filtro=<%= currentFiltro %>">Anterior</a>
		    </li>

		    <% for (int i = 1; i <= totalPaginas; i++) { %>
		        <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
		            <a class="page-link" href="ServletAutorizacionPrestamos?accion=listar&pagina=<%= i %>&busqueda=<%= currentBusqueda %>&filtro=<%= currentFiltro %>"><%= i %></a>
		        </li>
		    <% } %>

		    <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
		      <a class="page-link" href="ServletAutorizacionPrestamos?accion=listar&pagina=<%= paginaActual + 1 %>&busqueda=<%= currentBusqueda %>&filtro=<%= currentFiltro %>">Siguiente</a>
		    </li>

		  </ul>
		</nav>
	</div>
</div>

</body>
</html>