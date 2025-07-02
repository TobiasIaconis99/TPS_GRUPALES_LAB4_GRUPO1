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
				<form action="<%= request.getContextPath() %>/ServletUsuario" method="get" class="row g-2" id="formFiltrosUsuarios">
					<input type="hidden" name="accion" value="listar" />

					<div class="col-md-4">
						<input type="text" class="form-control" name="nombreFiltro" placeholder="Buscar por nombre de usuario"
						       value="<%= request.getParameter("nombreFiltro") != null ? request.getParameter("nombreFiltro") : "" %>" />
					</div>
					<div class="col-md-3">
						<select class="form-select" name="tipoFiltro">
							<option value="">Todos los tipos</option>
							<option value="admin" <%= "admin".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Admin</option>
							<option value="cliente" <%= "cliente".equals(request.getParameter("tipoFiltro")) ? "selected" : "" %>>Cliente</option>
						</select>
					</div>
					<div class="col-md-3 d-flex">
						<button type="submit" class="btn btn-primary w-50 me-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Buscar el usuario"><i class="bi bi-search me-1"></i> Buscar</button>
						<button type="button" class="btn btn-secondary w-50" onclick="limpiarFiltrosUsuarios()" data-bs-toggle="tooltip" data-bs-placement="top" title="Limpiar todos los filtros"><i class="bi bi-funnel me-1"></i> Limpiar</button>
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
						<th>Tipo</th>
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
						<td><%= u.getClave() %></td>
						<td><%= u.getTipoUsuario() %></td>
						<td>
							<button class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#modalEditarUsuario"
								onclick="cargarUsuario('<%= u.getIdUsuario() %>', '<%= u.getNombreUsuario() %>', '<%= u.getClave() %>', '<%= u.getTipoUsuario() %>')">
								<i class="bi bi-pencil-square"></i>
							</button>
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
        document.getElementById("label-idUsuario").textContent = id;

        // Limpiar estilos y mensajes de error previos al cargar un nuevo usuario
        const campos = ["edit-nombreUsuario", "edit-claveUsuario", "edit-repetirClaveUsuario"];
        const errores = ["nombreUsuarioError", "claveUsuarioError", "repetirClaveUsuarioError"];

        campos.forEach(id => {
            const input = document.getElementById(id);
            if (input) {
                input.classList.remove('is-invalid', 'is-valid');
            }
        });
        errores.forEach(id => {
            const errorSpan = document.getElementById(id);
            if (errorSpan) {
                errorSpan.textContent = '';
            }
        });

        // Cargar los valores del usuario
        document.getElementById("edit-nombreUsuario").value = nombre;
        document.getElementById("edit-claveUsuario").value = clave;
        document.getElementById("edit-repetirClaveUsuario").value = clave; // Para que coincida inicialmente

        document.getElementById("edit-tipoUsuario").value = tipo;
        document.getElementById("label-tipoUsuario").textContent = tipo.charAt(0).toUpperCase() + tipo.slice(1);

        // Opcional: Validar al cargar si los campos vienen con algún valor que deba validarse
        validarNombreUsuarioModal();
        validarClaveModal();
        validarRepetirClaveModal();
    }

    // Función para limpiar filtros en el ABML de Usuarios
    function limpiarFiltrosUsuarios() {
        const form = document.getElementById('formFiltrosUsuarios');
        form.elements['nombreFiltro'].value = ''; // Limpiar campo de texto
        form.elements['tipoFiltro'].value = ''; // Seleccionar "Todos los tipos"
        form.submit(); // Enviar el formulario para aplicar los filtros limpios
    }

    // --- Funciones de Validación para el Modal de Edición de Usuario ---

	function validarNombreUsuarioModal() {
	    const usuarioInput = document.getElementById('edit-nombreUsuario');
	    const usuarioError = document.getElementById('nombreUsuarioError');
	    const usuario = usuarioInput.value.trim();
	    // La regex ya incluye la longitud de 3 a 20 caracteres.
	    const regex = /^[a-zA-Z0-9._]{3,20}$/;
	
	    if (usuario === '') {
	        usuarioError.textContent = 'El nombre de usuario es obligatorio.';
	        usuarioInput.classList.add('is-invalid');
	        usuarioInput.classList.remove('is-valid');
	        return false;
	    } else if (!regex.test(usuario)) {
	        usuarioError.textContent = 'El usuario debe tener entre 3 y 20 caracteres y solo puede contener letras, números, puntos y guiones bajos.';
	        usuarioInput.classList.add('is-invalid');
	        usuarioInput.classList.remove('is-valid');
	        return false;
	    } else {
	        usuarioError.textContent = '';
	        usuarioInput.classList.remove('is-invalid');
	        usuarioInput.classList.add('is-valid');
	        return true;
	    }
	}

    function validarClaveModal() {
        const claveInput = document.getElementById('edit-claveUsuario');
        const claveError = document.getElementById('claveUsuarioError');
        const clave = claveInput.value;

        const passwordRegex = /^(?=.*\d).{4,}$/; // Al menos 4 caracteres y al menos 1 número

        if (clave === '') {
            claveError.textContent = 'La clave es obligatoria.';
            claveInput.classList.add('is-invalid');
            claveInput.classList.remove('is-valid');
            return false;
        } else if (!passwordRegex.test(clave)) {
            claveError.textContent = 'La clave debe tener al menos 4 caracteres y contener al menos un número.';
            claveInput.classList.add('is-invalid');
            claveInput.classList.remove('is-valid');
            return false;
        } else {
            claveError.textContent = '';
            claveInput.classList.remove('is-invalid');
            claveInput.classList.add('is-valid');
            return true;
        }
    }

    function validarRepetirClaveModal() {
        const claveInput = document.getElementById('edit-claveUsuario');
        const repetirClaveInput = document.getElementById('edit-repetirClaveUsuario');
        const repetirClaveError = document.getElementById('repetirClaveUsuarioError');

        const clave = claveInput.value;
        const repetirClave = repetirClaveInput.value;

        if (repetirClave === '') {
            repetirClaveError.textContent = 'Debe repetir la clave.';
            repetirClaveInput.classList.add('is-invalid');
            repetirClaveInput.classList.remove('is-valid');
            return false;
        } else if (clave !== repetirClave) {
            repetirClaveError.textContent = 'Las claves no coinciden.';
            repetirClaveInput.classList.add('is-invalid');
            repetirClaveInput.classList.remove('is-valid');
            return false;
        } else {
            repetirClaveError.textContent = '';
            repetirClaveInput.classList.remove('is-invalid');
            repetirClaveInput.classList.add('is-valid');
            return true;
        }
    }

    // Función principal de validación para el formulario del modal de edición
    function validarFormularioEditarUsuario() {
        const isNombreUsuarioValid = validarNombreUsuarioModal();
        const isClaveValid = validarClaveModal();
        const isRepetirClaveValid = validarRepetirClaveModal();

        return isNombreUsuarioValid && isClaveValid && isRepetirClaveValid;
    }
	</script>

	<div class="modal fade" id="modalEditarUsuario" tabindex="-1" aria-labelledby="modalEditarUsuarioLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="<%= request.getContextPath() %>/ServletUsuario" method="post" onsubmit="return validarFormularioEditarUsuario()"> <%-- **Validación al enviar** --%>
					<input type="hidden" name="accion" value="modificar">
					<div class="modal-header">
						<i class="bi bi-pencil-square fs-5 me-1"></i>
						<h5 class="modal-title" id="modalEditarUsuarioLabel">Editar usuario</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
					</div>
					<div class="modal-body">
						<div class="mb-3 d-none">
						    <label class="form-label">ID</label>
						    <input type="hidden" name="idUsuario" id="edit-idUsuario">
						    <span class="form-control bg-light border" id="label-idUsuario"></span>
						</div>
						<div class="mb-3">
							<label for="edit-nombreUsuario" class="form-label">Usuario</label>
							<input type="text" class="form-control" name="nombreUsuario" id="edit-nombreUsuario" required
                                   onkeyup="validarNombreUsuarioModal()" onchange="validarNombreUsuarioModal()">
                            <div id="nombreUsuarioError" class="invalid-feedback"></div>
						</div>
						<div class="mb-3">
							<label for="edit-claveUsuario" class="form-label">Clave</label>
							<input type="text" class="form-control" name="clave" id="edit-claveUsuario" required
                                   onkeyup="validarClaveModal(); validarRepetirClaveModal()" onchange="validarClaveModal(); validarRepetirClaveModal()">
                            <div id="claveUsuarioError" class="invalid-feedback"></div>
						</div>
                        <div class="mb-3">
                            <label for="edit-repetirClaveUsuario" class="form-label">Repetir Clave</label>
                            <input type="text" class="form-control" id="edit-repetirClaveUsuario" required
                                   onkeyup="validarRepetirClaveModal()" onchange="validarRepetirClaveModal()">
                            <div id="repetirClaveUsuarioError" class="invalid-feedback"></div>
                        </div>
						<div class="mb-3">
						    <label for="edit-tipoUsuario" class="form-label">Tipo de usuario</label>
						    <input type="hidden" name="tipoUsuario" id="edit-tipoUsuario">
						    <span class="form-control bg-light border" id="label-tipoUsuario"></span>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" data-bs-toggle="tooltip" data-bs-placement="top" title="Guardar los cambios">Guardar</button>
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal" data-bs-toggle="tooltip" data-bs-placement="top" title="Cancelar y cerrar">Cancelar</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>