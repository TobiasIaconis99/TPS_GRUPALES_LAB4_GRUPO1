<%@page import="java.util.List"%>
<%@page import="entidad.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Usuarios</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="margin: 0; padding: 0;">

	<%@ include file="includes/NavbarAdmin.jsp"%>
	<div class="d-flex">
		<%@ include file="includes/SidebarAdmin.jsp"%>
		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4>Usuarios</h4>
			<hr />
			<br />
		<div class="row mb-3 align-items-end">
			<div class="col-md-9">
				<form action="<%= request.getContextPath() %>/ServletUsuario" method="get" class="row g-2">
					<input type="hidden" name="accion" value="listar" />

					<div class="col-md-4">
						<input type="text" class="form-control" name="nombreFiltro" placeholder="Buscar por nombre de usuario"
						       value="<%= request.getParameter("nombreFiltro") != null ? request.getParameter("nombreFiltro") : "" %>" />
					</div>
					<div class="col-md-3">
						<select class="form-select" name="tipoFiltro"> <%-- Eliminado onchange="this.form.submit()" para que el botón de búsqueda sea el principal --%>
							<option value="">Todos los tipos</option>
							<option value="admin" <%= "admin".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Admin</option>
							<option value="cliente" <%= "cliente".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Cliente</option>
						</select>
					</div>
					<div class="col-md-3">
						<button type="submit" class="btn btn-primary w-100"><i class="bi bi-search"></i> Buscar</button>
					</div>
				</form>
				</div>
			</div>

			<%
				List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
			%>
			<table class="table table-bordered">
				<thead class="table-primary">
					<tr>
						<th>Usuario</th>
						<th>Clave</th>
						<th>Tipo</th> <%-- Agregamos la columna de tipo de usuario --%>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
						if (listaUsuarios != null) {
							for(Usuario u : listaUsuarios ){
					%>
					<tr>
						<td><%= u.getNombreUsuario() %></td>
						<td><%= u.getClave() %></td> <%-- ¡RECUERDA: Claves en texto plano son una VULNERABILIDAD! --%>
						<td><%= u.getTipoUsuario() %></td>
						<td>
							<button class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#modalEditarUsuario"
								onclick="cargarUsuario('<%= u.getIdUsuario() %>', '<%= u.getNombreUsuario() %>', '<%= u.getClave() %>', '<%= u.getTipoUsuario() %>')">
								<i class="bi bi-pencil-square"></i>
							</button>
							<%--
							<button class="btn btn-sm btn-danger"
								data-bs-toggle="modal"
								data-bs-target="#modalConfirmacionEliminarUsuario"
								onclick="setIdUsuarioAEliminar('<%= u.getIdUsuario() %>')">
								<i class="bi bi-trash"></i>
							</button>
							--%>
						</td>
					</tr>
					<% }
					} else { %>
					<tr>
						<td colspan="4" class="text-center">No se encontraron usuarios.</td>
					</tr>
					<% } %>
				</tbody>
			</table>

			<%
			    // Recuperamos filtros y búsqueda actuales para mantenerlos en la URL
			    String nombreFiltro = request.getParameter("nombreFiltro") != null ? request.getParameter("nombreFiltro") : "";
			    String tipoFiltro = request.getParameter("tipoFiltro") != null ? request.getParameter("tipoFiltro") : "";
			
			    int paginaActual = request.getAttribute("paginaActual") != null ? (int) request.getAttribute("paginaActual") : 1;
			    int totalPaginas = request.getAttribute("totalPaginas") != null ? (int) request.getAttribute("totalPaginas") : 1;
			%>
			
			<nav aria-label="Paginación">
			  <ul class="pagination justify-content-center">
			
			    <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
			      <a class="page-link" href="<%= request.getContextPath() %>/ServletUsuario?accion=listar&pagina=<%= paginaActual - 1 %>&nombreFiltro=<%= nombreFiltro %>&tipoFiltro=<%= tipoFiltro %>">
			        Anterior
			      </a>
			    </li>
			
			    <% for (int i = 1; i <= totalPaginas; i++) { %>
			      <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
			        <a class="page-link" href="<%= request.getContextPath() %>/ServletUsuario?accion=listar&pagina=<%= i %>&nombreFiltro=<%= nombreFiltro %>&tipoFiltro=<%= tipoFiltro %>">
			          <%= i %>
			        </a>
			      </li>
			    <% } %>
			
			    <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
			      <a class="page-link" href="<%= request.getContextPath() %>/ServletUsuario?accion=listar&pagina=<%= paginaActual + 1 %>&nombreFiltro=<%= nombreFiltro %>&tipoFiltro=<%= tipoFiltro %>">
			        Siguiente
			      </a>
			    </li>
			
			  </ul>
			</nav>
			</div>
	</div>

	<script>
		function cargarUsuario(id, nombre, clave, tipo) {
			document.getElementById("edit-idUsuario").value = id;
			document.getElementById("edit-nombreUsuario").value = nombre;
			// ¡ADVERTENCIA DE SEGURIDAD: NO MOSTRAR CLAVE EN CLARO!
			// Idealmente, el campo de clave debería estar vacío o permitir "cambiar clave".
			// Aquí solo se muestra para mantener la funcionalidad actual, pero se recomienda modificar.
			document.getElementById("edit-clave").value = clave; 
			document.getElementById("edit-tipoUsuario").value = tipo;
		}

		// Si añades eliminación de usuarios, necesitarías funciones similares a las de Cliente
		/*
		let idUsuarioAEliminar = "";
		function setIdUsuarioAEliminar(id) {
			idUsuarioAEliminar = id;
		}
		function confirmarEliminarUsuario() {
			const form = document.createElement('form');
			form.method = 'POST';
			form.action = '<%= request.getContextPath() %>/ServletUsuario';
			
			const accionInput = document.createElement('input');
			accionInput.type = 'hidden';
			accionInput.name = 'accion';
			accionInput.value = 'eliminar';
			form.appendChild(accionInput);

			const idInput = document.createElement('input');
			idInput.type = 'hidden';
			idInput.name = 'idUsuario';
			idInput.value = idUsuarioAEliminar;
			form.appendChild(idInput);

			document.body.appendChild(form);
			form.submit();
		}
		*/
	</script>

	<div class="modal fade" id="modalEditarUsuario" tabindex="-1" aria-labelledby="modalEditarUsuarioLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="<%= request.getContextPath() %>/ServletUsuario" method="post">
					<input type="hidden" name="accion" value="modificar"> <%-- Acción explícita para modificar --%>
					<div class="modal-header">
						<h5 class="modal-title" id="modalEditarUsuarioLabel">Editar usuario</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
					</div>
					<div class="modal-body">
						<div class="mb-3">
							<label for="edit-idUsuario" class="form-label">ID</label>
							<input type="text" class="form-control" name="idUsuario" id="edit-idUsuario" readonly>
						</div>
						<div class="mb-3">
							<label for="edit-nombreUsuario" class="form-label">Usuario</label>
							<input type="text" class="form-control" name="nombreUsuario" id="edit-nombreUsuario" required>
						</div>
						<div class="mb-3">
							<label for="edit-clave" class="form-label">Clave</label>
							<input type="text" class="form-control" name="clave" id="edit-clave" required>
							</div>
						<div class="mb-3">
							<label for="edit-tipoUsuario" class="form-label">Tipo de usuario</label>
							<select class="form-select" name="tipoUsuario" id="edit-tipoUsuario" required>
								<option value="admin">Admin</option>
								<option value="cliente">Cliente</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">Guardar</button>
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>