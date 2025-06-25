<%@page import="java.util.List"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.Provincia"%>
<%@page import="entidad.Localidad"%>
<%@page import="entidad.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Clientes</title>
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
                // Recupera el cliente si está en modo modificación
                Cliente clienteAEditar = (Cliente) request.getAttribute("clienteAEditar");
                // Recupera la lista de provincias (necesaria para ambos modos)
                List<Provincia> listaProvincias = (List<Provincia>) request.getAttribute("listaProvincias");
                
                // Determina el modo (agregar o modificar) y el título
                boolean esModificacion = (clienteAEditar != null);
                String tituloPagina = esModificacion ? "Modificar cliente" : "Agregar nuevo cliente";

                // Obtenemos el ID de provincia y localidad del cliente a editar para pasarlo al JS
                int idProvinciaCliente = -1;
                int idLocalidadCliente = -1;
                if (esModificacion && clienteAEditar.getLocalidad() != null && clienteAEditar.getLocalidad().getProvincia() != null) {
                    idProvinciaCliente = clienteAEditar.getLocalidad().getProvincia().getIdProvincia();
                    idLocalidadCliente = clienteAEditar.getLocalidad().getIdLocalidad();
                }
            %>

			<h4><%= tituloPagina %></h4>
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

			<form id="formCliente" action="ServletCliente" method="POST">
				<%-- Campo oculto para la acción (agregar/modificar) --%>
				<input type="hidden" name="accion" id="form-accion" value="<%= esModificacion ? "modificar" : "agregar" %>">

				<div class="row mb-3">
					<div class="col-md-4">
						<label for="dni" class="form-label">DNI</label>
						<input type="text" class="form-control" id="dni" name="dni" required
							value="<%= esModificacion ? clienteAEditar.getDni() : "" %>"
							<%= esModificacion ? "readonly" : "" %>> <%-- DNI solo lectura en modificación --%>
					</div>
					<div class="col-md-4">
						<label for="cuil" class="form-label">CUIL</label>
						<input type="text" class="form-control" id="cuil" name="cuil" required
							value="<%= esModificacion ? clienteAEditar.getCuil() : "" %>">
					</div>
					<div class="col-md-4">
					    <label for="sexo" class="form-label">Sexo</label>
					    <select class="form-select" id="sexo" name="sexo" required>
					        <option value="">Seleccione</option>
					        <%
					            String sexoCliente = esModificacion ? clienteAEditar.getSexo().trim() : "";
					        %>
					        <option value="M" <%= "M".equalsIgnoreCase(sexoCliente) ? "selected" : "" %>>Masculino</option>
					        <option value="F" <%= "F".equalsIgnoreCase(sexoCliente) ? "selected" : "" %>>Femenino</option>
					        <option value="O" <%= "O".equalsIgnoreCase(sexoCliente) ? "selected" : "" %>>Otro</option>
					    </select>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nombre" class="form-label">Nombre</label>
						<input type="text" class="form-control" id="nombre" name="nombre" required
							value="<%= esModificacion ? clienteAEditar.getNombre() : "" %>">
					</div>
					<div class="col-md-6">
						<label for="apellido" class="form-label">Apellido</label>
						<input type="text" class="form-control" id="apellido" name="apellido" required
							value="<%= esModificacion ? clienteAEditar.getApellido() : "" %>">
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nacionalidad" class="form-label">Nacionalidad</label>
						<input type="text" class="form-control" id="nacionalidad" name="nacionalidad" required
							value="<%= esModificacion ? clienteAEditar.getNacionalidad() : "" %>">
					</div>
					<div class="col-md-6">
						<label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
						<input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required
							value="<%= esModificacion && clienteAEditar.getFechaNacimiento() != null ? clienteAEditar.getFechaNacimiento().toString() : "" %>">
					</div>
				</div>

				<div class="mb-3">
					<label for="direccion" class="form-label">Dirección</label>
					<input type="text" class="form-control" id="direccion" name="direccion" required
						value="<%= esModificacion ? clienteAEditar.getDireccion() : "" %>">
				</div>

				<div class="row mb-3">
					<div class="col-md-4">
					    <label for="provincia" class="form-label">Provincia</label>
					    <select class="form-select" id="provincia" name="idProvincia" required onchange="cargarLocalidades(this.value)">
					        <option value="">Seleccione una provincia</option>
					        <% if (listaProvincias != null) {
					            for (Provincia p : listaProvincias) {
					                String selected = "";
					                if (esModificacion && clienteAEditar.getLocalidad() != null &&
					                    clienteAEditar.getLocalidad().getProvincia() != null &&
					                    clienteAEditar.getLocalidad().getProvincia().getIdProvincia() == p.getIdProvincia()) {
					                    selected = "selected";
					                }
					        %>
					                <option value="<%= p.getIdProvincia() %>" <%= selected %>><%= p.getNombreProvincia() %></option>
					        <%  }
					        } %>
					    </select>
					</div>
					<div class="col-md-4">
					    <label for="localidad" class="form-label">Localidad</label>
					    <select class="form-select" id="localidad" name="idLocalidad" required>
					        <option value="">Seleccione una localidad</option>
					        <%-- Las localidades se cargarán dinámicamente vía JavaScript --%>
					    </select>
					</div>
					<div class="col-md-4">
						<label for="telefono" class="form-label">Teléfono</label>
						<input type="tel" class="form-control" id="telefono" name="telefono" required
							value="<%= esModificacion ? clienteAEditar.getTelefono() : "" %>">
					</div>
				</div>
				
				<div class="mb-3">
					<label for="email" class="form-label">Correo electrónico</label>
					<input type="email" class="form-control" id="email" name="correo" required
						value="<%= esModificacion ? clienteAEditar.getCorreo() : "" %>">
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nombreUsuario" class="form-label">Usuario</label>
						<input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" required
							value="<%= esModificacion && clienteAEditar.getUsuario() != null ? clienteAEditar.getUsuario().getNombreUsuario() : "" %>"
							<%= esModificacion ? "readonly" : "" %>> <%-- Nombre de Usuario solo lectura en modificación --%>
					</div>
					<div class="col-md-6">
						<label for="claveUsuario" class="form-label">Contraseña</label>
						<input type="password" class="form-control" id="claveUsuario" name="claveUsuario" required
							value="<%= esModificacion && clienteAEditar.getUsuario() != null ? clienteAEditar.getUsuario().getClave() : "" %>">
					</div>
				</div>

				<div class="d-flex justify-content-between mt-4">
					<button type="submit" class="btn btn-primary">
						<i class="bi bi-save me-1"></i> Guardar
					</button>
					<a href="ServletCliente?accion=listar" class="btn btn-secondary">
						<i class="bi bi-x-circle me-1"></i> Cancelar
					</a>
				</div>
			</form>
		</div>
	</div>

<script>
	document.addEventListener('DOMContentLoaded', function () {
		const esModificacion = "<%= esModificacion %>" === "true";
		const idProvinciaCliente = <%= idProvinciaCliente %>;
		const idLocalidadCliente = <%= idLocalidadCliente %>;

		console.log("DEBUG - DOMContentLoaded: esModificacion =", esModificacion);
		console.log("DEBUG - DOMContentLoaded: idProvinciaCliente =", idProvinciaCliente);
		console.log("DEBUG - DOMContentLoaded: idLocalidadCliente =", idLocalidadCliente);

		if (esModificacion && idProvinciaCliente !== -1) {
			// Si estamos en modo modificación, cargamos las localidades para la provincia actual
			cargarLocalidades(idProvinciaCliente, idLocalidadCliente);
		}
	});

	/**
	 * Carga las localidades para una provincia, ya sea desde evento onchange o directamente por ID.
	 * @param {string|number} provinciaIdSource - ID de la provincia o el valor del select.
	 * @param {number} idLocalidadASeleccionar - ID de la localidad a seleccionar, si aplica.
	 */
	function cargarLocalidades(provinciaIdSource, idLocalidadASeleccionar = -1) {
		const localidadSelect = document.getElementById('localidad');
		
		// Validar si provinciaIdSource es un evento (poco probable con onchange="this.value")
		// o si es el valor directo. Si es el valor directo, ya es lo que necesitamos.
		// Si es undefined (llamada sin argumentos), lo leeremos del select.
		let provinciaId;
		if (typeof provinciaIdSource === 'number' && provinciaIdSource !== -1) {
			provinciaId = provinciaIdSource; // Valor viene del DOMContentLoaded
		} else if (typeof provinciaIdSource === 'string' && provinciaIdSource !== "") {
		    provinciaId = parseInt(provinciaIdSource); // Valor viene del onchange="this.value"
		} else {
		    // Caso de fallback, aunque con onchange="this.value" no debería ser necesario.
		    const provinciaSelect = document.getElementById('provincia');
		    provinciaId = parseInt(provinciaSelect.value);
		}


		console.log("DEBUG - cargarLocalidades: provinciaId FINAL =", provinciaId);

		// Mensaje de carga
		localidadSelect.innerHTML = '<option value="">Cargando...</option>';
		localidadSelect.disabled = true;

		// Validar ID
		if (!provinciaId || isNaN(provinciaId) || provinciaId <= 0) {
			console.warn("ID de provincia no válido (o vacío):", provinciaId);
			localidadSelect.innerHTML = '<option value="">Seleccione una provincia primero</option>';
			localidadSelect.disabled = false;
			return;
		}

		// Llamado AJAX
		fetch(`ServletCliente?accion=listarLocalidades&idProvincia=${provinciaId}`)
			.then(response => {
				if (!response.ok) {
					throw new Error(`Respuesta del servidor no válida: ${response.status} ${response.statusText}`);
				}
				return response.json();
			})
			.then(data => {
				localidadSelect.innerHTML = '<option value="">Seleccione una localidad</option>';

				if (Array.isArray(data)) {
					data.forEach(localidad => {
						const option = document.createElement('option');
						option.value = localidad.idLocalidad;
						option.textContent = localidad.nombreLocalidad;
						localidadSelect.appendChild(option);
					});

					// Si hay una localidad para seleccionar (modo modificación)
					if (idLocalidadASeleccionar !== -1) {
						localidadSelect.value = idLocalidadASeleccionar;
					}
				} else {
					console.error('La respuesta no es un array:', data);
					localidadSelect.innerHTML = '<option value="">Error: Datos no válidos</option>';
				}

				localidadSelect.disabled = false;
			})
			.catch(error => {
				console.error('Error al cargar localidades:', error);
				localidadSelect.innerHTML = '<option value="">Error al cargar localidades</option>';
				localidadSelect.disabled = false;
			});
	}
</script>

</body>
</html>