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
	<!-- Bootstrap 5.3 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<!-- Bootstrap Icons -->
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

			<!-- Filtros -->
			<div class="row mb-3">
				<div class="col-md-6">
					<form action="ServletUsuario" method="get" class="d-flex">
						<input type="hidden" name="accion" value="listar" />
						<select class="form-select me-2" name="tipoFiltro" onchange="this.form.submit()">
							<option value="">-- Todos los tipos --</option>
							<option value="admin" <%= "admin".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Admin</option>
							<option value="cliente" <%= "cliente".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Cliente</option>
						</select>
					</form>
				</div>
			</div>

			<!-- Tabla de usuarios -->
			<%
				List<Usuario> listaUsuarios = null;
				if(request.getAttribute("listaUsuarios") != null){
					listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
				}
			%>
			<table class="table table-bordered">
				<thead class="table-primary">
					<tr>
						<th>Usuario</th>
						<th>Clave</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
						if (listaUsuarios != null)
						for(Usuario u : listaUsuarios ){
					%>					
					<tr>
						<td><%= u.getNombreUsuario() %></td>
						<td><%= u.getClave() %></td>
						<td>
							<button 
							  class="btn btn-sm btn-success"
							  data-bs-toggle="modal"
							  data-bs-target="#modalEditarUsuario"
							  onclick="cargarUsuario('<%= u.getIdUsuario() %>', '<%= u.getNombreUsuario() %>', '<%= u.getClave() %>', '<%= u.getTipoUsuario() %>')">
							  <i class="bi bi-pencil-square"></i>
							</button>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
	
	<script>
		function cargarUsuario(id, nombre, clave, tipo) {
			document.getElementById("edit-idUsuario").value = id;
			document.getElementById("edit-nombreUsuario").value = nombre;
			document.getElementById("edit-clave").value = clave;
			document.getElementById("edit-tipoUsuario").value = tipo;
		}
	</script>
	
	<!-- Modal Editar Usuario -->
	<div class="modal fade" id="modalEditarUsuario" tabindex="-1"
		aria-labelledby="modalEditarUsuarioLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="ServletUsuario" method="post">
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
						<button type="submit" name="accion" value="modificar" class="btn btn-primary">Guardar</button>
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
