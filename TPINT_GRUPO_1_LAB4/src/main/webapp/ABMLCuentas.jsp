<%@page import="daoImpl.CuentaDaoImpl"%>
<%@page import="entidad.Cuenta"%>
<%@page import="entidad.Cliente"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Cuentas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<%@ include file="includes/NavbarAdmin.jsp"%>
<div class="d-flex">
	<%@ include file="includes/SidebarAdmin.jsp"%>

	<div class="flex-grow-1 p-4" style="margin-left: 250px;">
		<h4>Cuentas</h4>
		<hr />

		<div class="row mb-3 align-items-end">
		    <div class="col-md-9">
		        <form method="get" action="<%= request.getContextPath() %>/ServletCuenta" class="row gx-2">
		            <input type="hidden" name="accion" value="listar" />
		            
		            <div class="col-md-5">
		                <input type="text" name="busquedaCliente" class="form-control" 
		                       placeholder="Buscar por DNI, nombre o apellido del cliente"
		                       value="<%= request.getParameter("busquedaCliente") != null ? request.getParameter("busquedaCliente") : "" %>" />
		            </div>
		            
		            <div class="col-md-4">
		                <select class="form-select" name="filtroTipoCuenta">
		                    <option value="">Todos los tipos</option>
		                    <option value="1" <%= "1".equals(request.getParameter("filtroTipoCuenta")) ? "selected" : "" %>>Caja de ahorro</option>
		                    <option value="2" <%= "2".equals(request.getParameter("filtroTipoCuenta")) ? "selected" : "" %>>Cuenta corriente</option>
		                </select>
		            </div>
		            
		            <div class="col-md-3">
		                <button class="btn btn-primary w-100" type="submit">
		                    <i class="bi bi-search me-1"></i> Buscar
		                </button>
		            </div>
		        </form>
		    </div>
		    <div class="col-md-3 d-flex justify-content-end">
		        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalAgregarCuenta">
		            <i class="bi bi-plus-circle me-1"></i> Nueva cuenta
		        </button>
		    </div>
		</div>

		<%
		    String mensajeExitoCuenta = (String) session.getAttribute("mensajeExitoCuenta");
		    if (mensajeExitoCuenta != null) {
		        session.removeAttribute("mensajeExitoCuenta");
		%>
		        <div class="alert alert-success alert-dismissible fade show" role="alert">
		        	<i class="bi bi-check-circle me-1"></i>
		            <%= mensajeExitoCuenta %>
		            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		        </div>
		<%
		    }
		    String mensajeErrorCuenta = (String) session.getAttribute("mensajeErrorCuenta");
		    if (mensajeErrorCuenta != null) {
		        session.removeAttribute("mensajeErrorCuenta");
		%>
		        <div class="alert alert-danger alert-dismissible fade show" role="alert">
		        	<i class="bi bi-x-circle me-1"></i>
		            <%= mensajeErrorCuenta %>
		            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		        </div>
		<%
		    }
		%>

		<table class="table table-bordered">
			<thead class="table-light">
				<tr>
					<th>N° Cuenta</th>
					<th>Cliente</th>
					<th>Fecha creación</th>
					<th>Tipo</th>
					<th>CBU</th>
					<th>Saldo</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<Cuenta> listaCuentas = (List<Cuenta>) request.getAttribute("listaCuentas");
				if (listaCuentas != null && !listaCuentas.isEmpty()) { 
					for (Cuenta c : listaCuentas) {
				%>
				<tr>
					<td><%=c.getNumeroCuenta()%></td>
					<td><%=c.getCliente().getNombre()%> <%=c.getCliente().getApellido()%></td>
					<td><%=c.getFechaCreacion()%></td>
					<td><%=c.getTipoCuenta().getNombreTipoCuenta()%></td>
					<td><%=c.getCbu()%></td>
					<td>$<%=c.getSaldo()%></td>
					<td>
						<button class="btn btn-sm btn-success">
							<i class="bi bi-pencil-square"></i>
						</button>
						<button class="btn btn-sm btn-danger" data-bs-toggle="modal"
							data-bs-target="#modalConfirmacionEliminarCuenta"
							onclick="setCuentaAEliminar('<%=c.getIdCuenta()%>')">
							<i class="bi bi-trash"></i>
						</button>
					</td>
				</tr>
				<%
					}
				} else { 
				%>
				<tr>
					<td colspan="7" class="text-center">No se encontraron cuentas que coincidan con los criterios.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<%
			    // Recuperamos filtros y búsqueda para mantenerlos en la URL
			    String busquedaCliente = request.getParameter("busquedaCliente") != null ? request.getParameter("busquedaCliente") : "";
			    String filtroTipoCuenta = request.getParameter("filtroTipoCuenta") != null ? request.getParameter("filtroTipoCuenta") : "";
			
			    int paginaActual = request.getAttribute("paginaActual") != null ? (int) request.getAttribute("paginaActual") : 1;
			    int totalPaginas = request.getAttribute("totalPaginas") != null ? (int) request.getAttribute("totalPaginas") : 1;
			%>
			
			<nav aria-label="Paginación">
			  <ul class="pagination justify-content-center">
			
			    <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
			      <a class="page-link" href="<%= request.getContextPath() %>/ServletCuenta?accion=listar&pagina=<%= paginaActual - 1 %>&busquedaCliente=<%= busquedaCliente %>&filtroTipoCuenta=<%= filtroTipoCuenta %>">
			        Anterior
			      </a>
			    </li>
			
			    <% for (int i = 1; i <= totalPaginas; i++) { %>
			      <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
			        <a class="page-link" href="<%= request.getContextPath() %>/ServletCuenta?accion=listar&pagina=<%= i %>&busquedaCliente=<%= busquedaCliente %>&filtroTipoCuenta=<%= filtroTipoCuenta %>">
			          <%= i %>
			        </a>
			      </li>
			    <% } %>
			
			    <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
			      <a class="page-link" href="<%= request.getContextPath() %>/ServletCuenta?accion=listar&pagina=<%= paginaActual + 1 %>&busquedaCliente=<%= busquedaCliente %>&filtroTipoCuenta=<%= filtroTipoCuenta %>">
			        Siguiente
			      </a>
			    </li>
			
			  </ul>
			</nav>
			</div>
</div>

<script>
	let idCuentaAEliminar = "";
	function setCuentaAEliminar(id) {
		idCuentaAEliminar = id;
	}
	function confirmarEliminarCuenta() {
		const form = document.createElement('form');
		form.method = 'POST';
		form.action = '<%= request.getContextPath() %>/ServletCuenta'; 

		const accionInput = document.createElement('input');
		accionInput.type = 'hidden';
		accionInput.name = 'accion';
		accionInput.value = 'eliminar';
		form.appendChild(accionInput);

		const idCuentaInput = document.createElement('input');
		idCuentaInput.type = 'hidden';
		idCuentaInput.name = 'idCuenta';
		idCuentaInput.value = idCuentaAEliminar;
		form.appendChild(idCuentaInput);

		document.body.appendChild(form);
		form.submit();
	}
</script>

<div class="modal fade" id="modalAgregarCuenta" tabindex="-1"
	aria-labelledby="modalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<form class="modal-content" method="post" action="<%= request.getContextPath() %>/ServletCuenta">
			<div class="modal-header">
				<h5 class="modal-title" id="modalLabel">Agregar nueva cuenta</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<input type="hidden" name="accion" value="agregar" />

				<div class="mb-3">
					<label for="dniCliente" class="form-label">Cliente</label>
					<select class="form-select" name="dniCliente" required>
						<option value="">Seleccionar...</option>
						<%
						List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
						if (listaClientes != null) {
							for (Cliente cl : listaClientes) {
						%>
						<option value="<%=cl.getDni()%>"><%=cl.getNombre()%> <%=cl.getApellido()%></option>
						<%
							}
						}
						%>
					</select>
				</div>
				<div class="mb-3">
					<label for="tipoCuenta" class="form-label">Tipo de cuenta</label>
					<select class="form-select" name="tipoCuenta" required>
						<option value="">Seleccionar...</option>
						<option value="1">Caja de ahorro</option>
						<option value="2">Cuenta corriente</option>
					</select>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-success">Agregar</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
			</div>
		</form>
	</div>
</div>

<div class="modal fade" id="modalConfirmacionEliminarCuenta" tabindex="-1"
	aria-labelledby="modalConfirmacionEliminarLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalConfirmacionEliminarLabel">Eliminar Cuenta</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">¿Estás seguro de que deseas eliminar esta cuenta?</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
				<button type="button" class="btn btn-danger" onclick="confirmarEliminarCuenta()">Eliminar</button>
			</div>
		</div>
	</div>
</div>

</body>
</html>