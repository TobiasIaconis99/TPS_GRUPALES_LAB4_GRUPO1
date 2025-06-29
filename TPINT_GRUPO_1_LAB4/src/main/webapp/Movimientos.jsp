<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="entidad.Cuenta"%>
<%@page import="entidad.Movimiento"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Movimientos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<!-- Navbar cliente -->
	<%@ include file="includes/NavbarCliente.jsp" %>

	<div class="container my-5">
		<%
			// Recuperar mensajes de éxito/error de la sesión
		    String mensajeExito = (String) session.getAttribute("mensajeExito");
		    if (mensajeExito != null) {
		        session.removeAttribute("mensajeExito");
		%>
		        <div class="alert alert-success alert-dismissible fade show" role="alert">
		        	<i class="bi bi-check-circle me-1"></i>
		            <%= mensajeExito %>
		            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		        </div>
		<%
		    }
		    String mensajeError = (String) session.getAttribute("mensajeError");
		    if (mensajeError != null) {
		        session.removeAttribute("mensajeError");
		%>
		        <div class="alert alert-danger alert-dismissible fade show" role="alert">
		        	<i class="bi bi-x-circle me-1"></i>
		            <%= mensajeError %>
		            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		        </div>
		<%
		    }
		%>
		
		<div class="list-group mb-4">
			<div class="d-flex justify-content-between align-items-center">
				<h4 class="mb-0">
					Movimientos de cuenta
					<% 
						Cuenta cuentaSeleccionada = (Cuenta) request.getAttribute("cuentaSeleccionada");
						if (cuentaSeleccionada != null) {
					%>
						<small class="text-secondary">
							- <%= cuentaSeleccionada.getNumeroCuenta() %> (<%= cuentaSeleccionada.getTipoCuenta().getNombreTipoCuenta() %>)
						</small>
					<% } %>
				</h4>
				<a href="InicioCliente.jsp" class="btn btn-primary">
					<i class="bi bi-arrow-left-circle me-1"></i> Volver
				</a>
			</div>
			<hr>
		</div>

		<%
			// Obtener la lista de movimientos y datos de paginación desde el Servlet
			List<Movimiento> listaMovimientos = (List<Movimiento>) request.getAttribute("listaMovimientos");
			int paginaActual = (Integer) request.getAttribute("paginaActual");
			int totalPaginas = (Integer) request.getAttribute("totalPaginas");
			int idCuentaActual = (Integer) request.getAttribute("idCuentaActual"); // Necesario para los enlaces de paginación
		%>

		<% if (listaMovimientos != null && !listaMovimientos.isEmpty()) { %>
			<div class="table-responsive">
				<table class="table table-striped table-hover">
					<thead class="table-primary">
						<tr>
							<th>Fecha</th>
							<th>Detalle</th>
							<th>Importe</th>
							<th>Tipo de movimiento</th>
						</tr>
					</thead>
					<tbody>
						<% 
						    // Formato de fecha para la tabla
						    java.text.SimpleDateFormat sdfTabla = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
						    for (Movimiento mov : listaMovimientos) { 
						%>
							<tr>
								<td><%= sdfTabla.format(mov.getFecha()) %></td>
								<td><%= mov.getDetalle() %></td>
								<td class="<%= mov.getImporte().compareTo(BigDecimal.ZERO) >= 0 ? "text-success" : "text-danger" %>">
									<%= mov.getImporte().compareTo(BigDecimal.ZERO) >= 0 ? "+" : "-" %>
									$<%= String.format("%.2f", mov.getImporte().abs()) %>
								</td>
								<td><%= mov.getTipoMovimiento().getNombre() %></td> 
							</tr>
						<% } %>
					</tbody>
				</table>
			</div>
			
			<!-- Paginación -->
			<nav aria-label="Paginación de movimientos">
			  <ul class="pagination justify-content-center">
			
			    <!-- Botón Anterior -->
			    <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
			      <a class="page-link" href="<%= request.getContextPath() %>/ServletMovimiento?accion=listar&idCuenta=<%= idCuentaActual %>&pagina=<%= paginaActual - 1 %>">
			        Anterior
			      </a>
			    </li>
			
			    <!-- Números de página -->
			    <% for (int i = 1; i <= totalPaginas; i++) { %>
			      <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
			        <a class="page-link" href="<%= request.getContextPath() %>/ServletMovimiento?accion=listar&idCuenta=<%= idCuentaActual %>&pagina=<%= i %>">
			          <%= i %>
			        </a>
			      </li>
			    <% } %>
			
			    <!-- Botón Siguiente -->
			    <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
			      <a class="page-link" href="<%= request.getContextPath() %>/ServletMovimiento?accion=listar&idCuenta=<%= idCuentaActual %>&pagina=<%= paginaActual + 1 %>">
			        Siguiente
			      </a>
			    </li>
			  </ul>
			</nav>

		<% } else if (request.getParameter("idCuenta") != null) { %>
			<div class="alert alert-primary">No se encontraron movimientos para la cuenta seleccionada.</div>
		<% } else { %>
            <div class="alert alert-primary">Seleccione una cuenta desde el inicio para ver sus movimientos.</div>
        <% } %>

	</div>
</body>
</html>
