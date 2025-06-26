<%@page import="java.util.List"%>
<%@page import="entidad.Provincia"%>
<%@page import="entidad.Cliente"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Modificar Cliente</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body style="margin: 0; padding: 0;">

	<%@ include file="includes/NavbarAdmin.jsp" %>

	<div class="d-flex">
		<%@ include file="includes/SidebarAdmin.jsp"%>

		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<%
                // Recupera el cliente a editar y la lista de provincias
                Cliente clienteAEditar = (Cliente) request.getAttribute("clienteAEditar");
                List<Provincia> listaProvincias = (List<Provincia>) request.getAttribute("listaProvincias");

                // Variables para precargar el formulario
                String dniCliente = "";
                String cuilCliente = "";
                String sexoCliente = "";
                String nombreCliente = "";
                String apellidoCliente = "";
                String nacionalidadCliente = "";
                String fechaNacimientoCliente = "";
                String direccionCliente = "";
                int idProvinciaCliente = -1;
                int idLocalidadCliente = -1;
                String telefonoCliente = "";
                String correoCliente = "";
                String nombreUsuarioCliente = "";
                String claveUsuarioCliente = ""; // Precaución: La clave no debe enviarse directamente a la vista por seguridad.
                                                 // Para simplificar el ejemplo, la cargamos, pero en un entorno real,
                                                 // solo cargarías un placeholder o requerirías que se reingrese.

                if (clienteAEditar != null) {
                    dniCliente = clienteAEditar.getDni() != null ? clienteAEditar.getDni() : "";
                    cuilCliente = clienteAEditar.getCuil() != null ? clienteAEditar.getCuil() : "";
                    sexoCliente = clienteAEditar.getSexo() != null ? clienteAEditar.getSexo() : "";
                    nombreCliente = clienteAEditar.getNombre() != null ? clienteAEditar.getNombre() : "";
                    apellidoCliente = clienteAEditar.getApellido() != null ? clienteAEditar.getApellido() : "";
                    nacionalidadCliente = clienteAEditar.getNacionalidad() != null ? clienteAEditar.getNacionalidad() : "";
                    fechaNacimientoCliente = clienteAEditar.getFechaNacimiento() != null ? clienteAEditar.getFechaNacimiento().toString() : "";
                    direccionCliente = clienteAEditar.getDireccion() != null ? clienteAEditar.getDireccion() : "";
                    if (clienteAEditar.getLocalidad() != null && clienteAEditar.getLocalidad().getProvincia() != null) {
                        idProvinciaCliente = clienteAEditar.getLocalidad().getProvincia().getIdProvincia();
                        idLocalidadCliente = clienteAEditar.getLocalidad().getIdLocalidad();
                    }
                    telefonoCliente = clienteAEditar.getTelefono() != null ? clienteAEditar.getTelefono() : "";
                    correoCliente = clienteAEditar.getCorreo() != null ? clienteAEditar.getCorreo() : "";
                    if (clienteAEditar.getUsuario() != null) {
                        nombreUsuarioCliente = clienteAEditar.getUsuario().getNombreUsuario() != null ? clienteAEditar.getUsuario().getNombreUsuario() : "";
                        claveUsuarioCliente = clienteAEditar.getUsuario().getClave() != null ? clienteAEditar.getUsuario().getClave() : ""; // Precaución
                    }
                }
            %>

			<h4>Modificar cliente: <%= dniCliente %></h4>
			<hr />
			<br />
			
			<%-- Mensajes de exito o error --%>
            <%
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

			<form id="formModificarCliente" action="ServletCliente" method="post">
				<input type="hidden" name="accion" value="modificar">
                <input type="hidden" name="dni" value="<%= dniCliente %>">

				<div class="row mb-3">
					<div class="col-md-4">
						<label for="dniDisplay" class="form-label">DNI</label>
						<input type="text" class="form-control" id="dniDisplay" value="<%= dniCliente %>" readonly>
					</div>
					<div class="col-md-4">
						<label for="cuil" class="form-label">CUIL</label>
						<input type="text" class="form-control" id="cuil" name="cuil" value="<%= cuilCliente %>" required>
					</div>
					<div class="col-md-4">
					    <label for="sexo" class="form-label">Sexo</label>
					    <select class="form-select" id="sexo" name="sexo" required>
					        <option value="">Seleccione</option>
					        <option value="M" <%= "M".equals(sexoCliente) ? "selected" : "" %>>Masculino</option>
					        <option value="F" <%= "F".equals(sexoCliente) ? "selected" : "" %>>Femenino</option>
					        <option value="O" <%= "O".equals(sexoCliente) ? "selected" : "" %>>Otro</option>
					    </select>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nombre" class="form-label">Nombre</label>
						<input type="text" class="form-control" id="nombre" name="nombre" value="<%= nombreCliente %>" required>
					</div>
					<div class="col-md-6">
						<label for="apellido" class="form-label">Apellido</label>
						<input type="text" class="form-control" id="apellido" name="apellido" value="<%= apellidoCliente %>" required>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nacionalidad" class="form-label">Nacionalidad</label>
						<input type="text" class="form-control" id="nacionalidad" name="nacionalidad" value="<%= nacionalidadCliente %>" required>
					</div>
					<div class="col-md-6">
						<label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
						<input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" value="<%= fechaNacimientoCliente %>" required>
					</div>
				</div>

				<div class="mb-3">
					<label for="direccion" class="form-label">Dirección</label>
					<input type="text" class="form-control" id="direccion" name="direccion" value="<%= direccionCliente %>" required>
				</div>

				<div class="row mb-3">
					<div class="col-md-4">
					    <label for="provincia" class="form-label">Provincia</label>
					    <select class="form-select" id="provincia" name="idProvincia" required onchange="cargarLocalidades(this.value)">
					        <option value="">Seleccione una provincia</option>
					        <% if (listaProvincias != null) {
					            for (Provincia p : listaProvincias) {
					        %>
					                <option value="<%= p.getIdProvincia() %>" <%= p.getIdProvincia() == idProvinciaCliente ? "selected" : "" %>>
					                    <%= p.getNombreProvincia() %>
					                </option>
					        <%  }
					        } %>
					    </select>
					</div>
					<div class="col-md-4">
					    <label for="localidad" class="form-label">Localidad</label>
					    <select class="form-select" id="localidad" name="idLocalidad" required>
					        <option value="">Seleccione una localidad</option>
					    </select>
					</div>
					<div class="col-md-4">
						<label for="telefono" class="form-label">Teléfono</label>
						<input type="tel" class="form-control" id="telefono" name="telefono" value="<%= telefonoCliente %>" required>
					</div>
				</div>
				
				<div class="mb-3">
					<label for="correo" class="form-label">Correo electrónico</label>
					<input type="email" class="form-control" id="correo" name="correo" value="<%= correoCliente %>" required>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nombreUsuario" class="form-label">Usuario</label>
						<input type="hidden" name="idUsuario" value="${clienteAEditar.usuario.idUsuario}">
						<input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" value="<%= nombreUsuarioCliente %>" required>
					</div>
					<div class="col-md-6">
						<label for="claveUsuario" class="form-label">Clave</label>
						<input type="password" class="form-control" id="claveUsuario" name="claveUsuario" value="<%= claveUsuarioCliente %>" required>
					</div>
				</div>
				
				<div class="d-flex justify-content-end">
				    <button type="submit" class="btn btn-primary me-2">Guardar</button>
				    <a href="ServletCliente?accion=listar" class="btn btn-secondary">Cancelar</a>
				</div>
				
			</form>
		</div>
	</div>

	<script>
	    	// Variables de JSP para usar en JavaScript
	        const idProvinciaCliente = <%= idProvinciaCliente %>;
	        const idLocalidadCliente = <%= idLocalidadCliente %>;
	    	
	    	// Esta funcion sirve para cargar las localidades de la BD segun la provincia que se elija
	        function cargarLocalidades(idProvincia) {
	            const localidadSelect = document.getElementById('localidad');
	            localidadSelect.innerHTML = '<option>Cargando...</option>';
	            localidadSelect.disabled = true;
	
	            fetch('ServletLocalidad?accion=listarLocalidades&idProvincia=' + idProvincia)
	                .then(res => res.json())
	                .then(data => {
	                    localidadSelect.innerHTML = '<option value="">Seleccione una localidad</option>';
	                    data.forEach(loc => {
	                        const option = document.createElement('option');
	                        option.value = loc.idLocalidad;
	                        option.textContent = loc.nombreLocalidad;
	                        localidadSelect.appendChild(option);
	                    });
	                    localidadSelect.disabled = false;
	                    
	                    // Seleccionar la localidad del cliente si está presente y coincide con la provincia actual
	                    if (idProvincia == idProvinciaCliente && idLocalidadCliente != -1) {
	                        localidadSelect.value = idLocalidadCliente;
	                    }
	                })
	                .catch(err => {
	                    localidadSelect.innerHTML = '<option>Error al cargar</option>';
	                    console.error('Error:', err);
	                });
	        }
	
	        // Cargar las localidades al cargar la página si ya hay una provincia seleccionada
	        document.addEventListener('DOMContentLoaded', function() {
	            if (idProvinciaCliente != -1) {
	                cargarLocalidades(idProvinciaCliente);
	            }
	        });
	</script>

</body>
</html>