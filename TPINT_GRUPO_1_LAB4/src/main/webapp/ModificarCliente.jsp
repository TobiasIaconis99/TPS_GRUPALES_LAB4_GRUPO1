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

			<form id="formModificarCliente" action="ServletCliente" method="post" onsubmit="return validarFormulario()">
				<input type="hidden" name="accion" value="modificar">
                <input type="hidden" name="dni" value="<%= dniCliente %>">
                <input type="hidden" name="idUsuario" value="${clienteAEditar.usuario.idUsuario}">

				<div class="row mb-3">
					<div class="col-md-4">
					    <label for="dniDisplay" class="form-label">DNI</label>
					    <input type="text" class="form-control bg-light" id="dniDisplay" value="<%= dniCliente %>" placeholder="XXXXXXXX" readonly>
					</div>
					<div class="col-md-4">
					    <label for="cuil" class="form-label">CUIL</label>
					    <input type="text" class="form-control" id="cuil" name="cuil" value="<%= cuilCliente %>" placeholder="XX-XXXXXX-X" required onblur="validarCuil()" oninput="validarCuil()">
					    <div id="cuilError" class="text-danger"></div>
					</div>
					<div class="col-md-4">
					    <label for="sexo" class="form-label">Sexo</label>
					    <select class="form-select" id="sexo" name="sexo" required onblur="validarSexo()" onchange="validarSexo()">
					        <option value="">Seleccione</option>
					        <option value="M" <%= "M".equals(sexoCliente) ? "selected" : "" %>>Masculino</option>
					        <option value="F" <%= "F".equals(sexoCliente) ? "selected" : "" %>>Femenino</option>
					        <option value="O" <%= "O".equals(sexoCliente) ? "selected" : "" %>>Otro</option>
					    </select>
					    <div id="sexoError" class="text-danger"></div>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nombre" class="form-label">Nombre</label>
						<input type="text" class="form-control" id="nombre" name="nombre" value="<%= nombreCliente %> " 
							required onblur="validarNombre()" oninput="validarNombre() " placeholder="Ej.: Roberto">
						<div id="nombreError" class="text-danger"></div>
					</div>
					<div class="col-md-6">
						<label for="apellido" class="form-label">Apellido</label>
						<input type="text" class="form-control" id="apellido" name="apellido" value="<%= apellidoCliente %>" 
							required onblur="validarApellido()" oninput="validarApellido()" placeholder="Ej.: Acosta">
						<div id="apellidoError" class="text-danger"></div>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nacionalidad" class="form-label">Nacionalidad</label>
						<input type="text" class="form-control" id="nacionalidad" name="nacionalidad" value="<%= nacionalidadCliente %>" 
							required onblur="validarNacionalidad()" oninput="validarNacionalidad()" placeholder="Ej.: Acosta">
						<div id="nacionalidadError" class="text-danger"></div>
					</div>
					<div class="col-md-6">
						<label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
						<input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" value="<%= fechaNacimientoCliente %>" 
							required onblur="validarFechaNacimiento()">
						<div id="fechaNacimientoError" class="text-danger"></div>
					</div>
				</div>

				<div class="mb-3">
					<label for="direccion" class="form-label">Dirección</label>
					<input type="text" class="form-control" id="direccion" name="direccion" value="<%= direccionCliente %>" 
						required onblur="validarDireccion()" oninput="validarDireccion()" placeholder="Ej.: Avenida de Mayo 1000">
					<div id="direccionError" class="text-danger"></div>
				</div>

				<div class="row mb-3">
					<div class="col-md-4">
					    <label for="provincia" class="form-label">Provincia</label>
					    <select class="form-select" id="provincia" name="idProvincia" required onchange="cargarLocalidades(this.value); validarProvincia()" onblur="validarProvincia()">
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
					    <div id="provinciaError" class="text-danger"></div>
					</div>
					<div class="col-md-4">
					    <label for="localidad" class="form-label">Localidad</label>
					    <select class="form-select" id="localidad" name="idLocalidad" required onchange="validarLocalidad()" onblur="validarLocalidad()">
					        <option value="">Seleccione una localidad</option>
					    </select>
					    <div id="localidadError" class="text-danger"></div>
					</div>
					<div class="col-md-4">
						<label for="telefono" class="form-label">Teléfono</label>
						<input type="tel" class="form-control" id="telefono" name="telefono" value="<%= telefonoCliente %>" 
							required onblur="validarTelefono()" oninput="validarTelefono()" placeholder="Ej.: +5491140111222">
						<div id="telefonoError" class="text-danger"></div>
					</div>
				</div>
				
				<div class="mb-3">
					<label for="correo" class="form-label">Correo electrónico</label>
					<input type="email" class="form-control" id="correo" name="correo" value="<%= correoCliente %>" 
						required onblur="validarCorreo()" oninput="validarCorreo()" placeholder="Ej.: roberto.acosta@example.com">
					<div id="correoError" class="text-danger"></div>
				</div>

				<div class="row mb-3">
					<div class="col-md-6">
						<label for="nombreUsuario" class="form-label">Usuario</label>
						<input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" value="<%= nombreUsuarioCliente %>" 
							required onblur="validarNombreUsuario()" oninput="validarNombreUsuario()" placeholder="Ej.: roberto">
						<div id="nombreUsuarioError" class="text-danger"></div>
					</div>
					<div class="col-md-6">
						<label for="claveUsuario" class="form-label">Clave</label>
						<input type="text" class="form-control" id="claveUsuario" name="claveUsuario" value="<%= claveUsuarioCliente %>" 
							required onblur="validarClave()" oninput="validarClave()" placeholder="Ej.: roberto.acosta@example.com">
						<div id="claveUsuarioError" class="text-danger"></div>
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
	        const dniClienteJSP = "<%= dniCliente %>"; // DNI del cliente para la validación del CUIL
	    	
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
	                    validarLocalidad(); // Valida la localidad una vez cargada
	                })
	                .catch(err => {
	                    localidadSelect.innerHTML = '<option>Primero seleccionar una provincia</option>';
	                    console.error('Error:', err);
	                });
	        }
	
	        // Cargar las localidades al cargar la página si ya hay una provincia seleccionada
	        document.addEventListener('DOMContentLoaded', function() {
	            if (idProvinciaCliente != -1) {
	                cargarLocalidades(idProvinciaCliente);
	            }
	            // Llama a todas las validaciones al cargar para precargar el estado visual
	            validarFormulario(); 
	        });
	        
	        // --- Funciones de Validación de Campos Individuales ---

            function validarCuil() {
                const cuilInput = document.getElementById('cuil');
                const cuilError = document.getElementById('cuilError');
                const cuil = cuilInput.value.trim();
                const dni = document.getElementById('dniDisplay').value.trim(); // Obtener el DNI del campo readonly

                // Regex para CUIL: OBLIGA el formato XX-XXXXXXXX-X
                const cuilRegex = /^\d{2}-\d{8}-\d{1}$/; 

                if (cuil === '') {
                    cuilError.textContent = 'El CUIL es obligatorio.';
                    cuilInput.classList.add('is-invalid');
                    cuilInput.classList.remove('is-valid');
                    return false;
                } else if (!cuilRegex.test(cuil)) {
                    cuilError.textContent = 'El CUIL no tiene el formato obligatorio XX-XXXXXXXX-X.';
                    cuilInput.classList.add('is-invalid');
                    cuilInput.classList.remove('is-valid');
                    return false;
                } else {
                    // Extraer solo los 8 dígitos centrales del CUIL (corresponden al DNI)
                    const cuilDniParte = cuil.substring(3, 11); 

                    // Validar que la parte central del CUIL coincida con el DNI
                    if (cuilDniParte !== dni) {
                        cuilError.textContent = 'Los dígitos centrales del CUIL no coinciden con el DNI.';
                        cuilInput.classList.add('is-invalid');
                        cuilInput.classList.remove('is-valid');
                        return false;
                    }

                    // Si todas las validaciones pasaron
                    cuilError.textContent = '';
                    cuilInput.classList.remove('is-invalid');
                    cuilInput.classList.add('is-valid');
                    return true;
                }
            }

            function validarSexo() {
                const sexoInput = document.getElementById('sexo');
                const sexoError = document.getElementById('sexoError');
                if (sexoInput.value === '') {
                    sexoError.textContent = 'Debe seleccionar un sexo.';
                    sexoInput.classList.add('is-invalid');
                    sexoInput.classList.remove('is-valid');
                    return false;
                } else {
                    sexoError.textContent = '';
                    sexoInput.classList.remove('is-invalid');
                    sexoInput.classList.add('is-valid');
                    return true;
                }
            }

            // --- Funciones de Validación de Campos de Texto Individuales (sin función genérica) ---

            // Para Nombre (letras y espacios, incluyendo tildes y ñ)
            function validarNombre() {
                const nombreInput = document.getElementById('nombre');
                const nombreError = document.getElementById('nombreError');
                const nombre = nombreInput.value.trim();
                const minLength = 2;
                const maxLength = 40;
                const regex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/;

                if (nombre === '') {
                    nombreError.textContent = 'El nombre es obligatorio.';
                    nombreInput.classList.add('is-invalid');
                    nombreInput.classList.remove('is-valid');
                    return false;
                } else if (nombre.length < minLength || nombre.length > maxLength) {
                    nombreError.textContent = `El nombre debe tener entre ${minLength} y ${maxLength} caracteres.`;
                    nombreInput.classList.add('is-invalid');
                    nombreInput.classList.remove('is-valid');
                    return false;
                } else if (!regex.test(nombre)) {
                    nombreError.textContent = 'El nombre contiene caracteres inválidos.';
                    nombreInput.classList.add('is-invalid');
                    nombreInput.classList.remove('is-valid');
                    return false;
                } else {
                    nombreError.textContent = '';
                    nombreInput.classList.remove('is-invalid');
                    nombreInput.classList.add('is-valid');
                    return true;
                }
            }

            // Para Apellido
            function validarApellido() {
                const apellidoInput = document.getElementById('apellido');
                const apellidoError = document.getElementById('apellidoError');
                const apellido = apellidoInput.value.trim();
                const minLength = 2;
                const maxLength = 40;
                const regex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/;

                if (apellido === '') {
                    apellidoError.textContent = 'El apellido es obligatorio.';
                    apellidoInput.classList.add('is-invalid');
                    apellidoInput.classList.remove('is-valid');
                    return false;
                } else if (apellido.length < minLength || apellido.length > maxLength) {
                    apellidoError.textContent = `El apellido debe tener entre ${minLength} y ${maxLength} caracteres.`;
                    apellidoInput.classList.add('is-invalid');
                    apellidoInput.classList.remove('is-valid');
                    return false;
                } else if (!regex.test(apellido)) {
                    apellidoError.textContent = 'El apellido contiene caracteres inválidos.';
                    apellidoInput.classList.add('is-invalid');
                    apellidoInput.classList.remove('is-valid');
                    return false;
                } else {
                    apellidoError.textContent = '';
                    apellidoInput.classList.remove('is-invalid');
                    apellidoInput.classList.add('is-valid');
                    return true;
                }
            }

            // Para Nacionalidad
            function validarNacionalidad() {
                const nacionalidadInput = document.getElementById('nacionalidad');
                const nacionalidadError = document.getElementById('nacionalidadError');
                const nacionalidad = nacionalidadInput.value.trim();
                const minLength = 2;
                const maxLength = 50;
                const regex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/;

                if (nacionalidad === '') {
                    nacionalidadError.textContent = 'La nacionalidad es obligatoria.';
                    nacionalidadInput.classList.add('is-invalid');
                    nacionalidadInput.classList.remove('is-valid');
                    return false;
                } else if (nacionalidad.length < minLength || nacionalidad.length > maxLength) {
                    nacionalidadError.textContent = `La nacionalidad debe tener entre ${minLength} y ${maxLength} caracteres.`;
                    nacionalidadInput.classList.add('is-invalid');
                    nacionalidadInput.classList.remove('is-valid');
                    return false;
                } else if (!regex.test(nacionalidad)) {
                    nacionalidadError.textContent = 'La nacionalidad contiene caracteres inválidos.';
                    nacionalidadInput.classList.add('is-invalid');
                    nacionalidadInput.classList.remove('is-valid');
                    return false;
                } else {
                    nacionalidadError.textContent = '';
                    nacionalidadInput.classList.remove('is-invalid');
                    nacionalidadInput.classList.add('is-valid');
                    return true;
                }
            }

            function validarFechaNacimiento() {
                const fechaInput = document.getElementById('fechaNacimiento');
                const fechaError = document.getElementById('fechaNacimientoError');
                const fechaNacimiento = new Date(fechaInput.value + 'T00:00:00'); // Añadir 'T00:00:00' para evitar problemas de zona horaria
                const hoy = new Date();
                hoy.setHours(0,0,0,0); // Normalizar a medianoche
                const edadMinima = 18; 
                // Calcula la fecha mínima válida para tener la edad mínima
                const fechaMinimaValida = new Date(hoy.getFullYear() - edadMinima, hoy.getMonth(), hoy.getDate());
                fechaMinimaValida.setHours(0,0,0,0); // Normalizar a medianoche

                if (fechaInput.value === '') {
                    fechaError.textContent = 'La fecha de nacimiento es obligatoria.';
                    fechaInput.classList.add('is-invalid');
                    fechaInput.classList.remove('is-valid');
                    return false;
                } else if (fechaNacimiento > hoy) {
                    fechaError.textContent = 'La fecha de nacimiento no puede ser futura.';
                    fechaInput.classList.add('is-invalid');
                    fechaInput.classList.remove('is-valid');
                    return false;
                } else if (fechaNacimiento > fechaMinimaValida) {
                    fechaError.textContent = `El cliente debe ser mayor de 18 años.`;
                    fechaInput.classList.add('is-invalid');
                    fechaInput.classList.remove('is-valid');
                    return false;
                } else {
                    fechaError.textContent = '';
                    fechaInput.classList.remove('is-invalid');
                    fechaInput.classList.add('is-valid');
                    return true;
                }
            }

            function validarDireccion() {
                const direccionInput = document.getElementById('direccion');
                const direccionError = document.getElementById('direccionError');
                const direccion = direccionInput.value.trim();
                const minLength = 5;
                const maxLength = 100;
                // Permite letras, números, espacios y algunos caracteres especiales para direcciones
                const regex = /^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ#\-\s,.]+$/;

                if (direccion === '') {
                    direccionError.textContent = 'La dirección es obligatoria.';
                    direccionInput.classList.add('is-invalid');
                    direccionInput.classList.remove('is-valid');
                    return false;
                } else if (direccion.length < minLength || direccion.length > maxLength) { 
                    direccionError.textContent = `La dirección debe tener entre ${minLength} y ${maxLength} caracteres.`;
                    direccionInput.classList.add('is-invalid');
                    direccionInput.classList.remove('is-valid');
                    return false;
                } else if (!regex.test(direccion)) {
                    direccionError.textContent = 'La dirección contiene caracteres inválidos.';
                    direccionInput.classList.add('is-invalid');
                    direccionInput.classList.remove('is-valid');
                    return false;
                } else {
                    direccionError.textContent = '';
                    direccionInput.classList.remove('is-invalid');
                    direccionInput.classList.add('is-valid');
                    return true;
                }
            }

            function validarProvincia() {
                const provinciaSelect = document.getElementById('provincia');
                const provinciaError = document.getElementById('provinciaError');
                if (provinciaSelect.value === '') {
                    provinciaError.textContent = 'Debe seleccionar una provincia.';
                    provinciaSelect.classList.add('is-invalid');
                    provinciaSelect.classList.remove('is-valid');
                    return false;
                } else {
                    provinciaError.textContent = '';
                    provinciaSelect.classList.remove('is-invalid');
                    provinciaSelect.classList.add('is-valid');
                    return true;
                }
            }

            function validarLocalidad() {
                const localidadSelect = document.getElementById('localidad');
                const localidadError = document.getElementById('localidadError');
                if (localidadSelect.value === '') {
                    localidadError.textContent = 'Debe seleccionar una localidad.';
                    localidadSelect.classList.add('is-invalid');
                    localidadSelect.classList.remove('is-valid');
                    return false;
                } else {
                    localidadError.textContent = '';
                    localidadSelect.classList.remove('is-invalid');
                    localidadSelect.classList.add('is-valid');
                    return true;
                }
            }

            function validarTelefono() {
                const telefonoInput = document.getElementById('telefono');
                const telefonoError = document.getElementById('telefonoError');
                const telefono = telefonoInput.value.trim();
                const minLength = 7;
                const maxLength = 20;
                // Permite dígitos, +, espacios, guiones, paréntesis (ajustar según tu necesidad)
                const regex = /^[0-9+\s\-()]{7,20}$/; 

                if (telefono === '') {
                    telefonoError.textContent = 'El teléfono es obligatorio.';
                    telefonoInput.classList.add('is-invalid');
                    telefonoInput.classList.remove('is-valid');
                    return false;
                } else if (!regex.test(telefono)) {
                    telefonoError.textContent = 'El teléfono no tiene un formato válido.';
                    telefonoInput.classList.add('is-invalid');
                    telefonoInput.classList.remove('is-valid');
                    return false;
                } else if (telefono.length < minLength || telefono.length > maxLength) { 
                    telefonoError.textContent = `El teléfono debe tener entre ${minLength} y ${maxLength} caracteres.`;
                    telefonoInput.classList.add('is-invalid');
                    telefonoInput.classList.remove('is-valid');
                    return false;
                } else {
                    telefonoError.textContent = '';
                    telefonoInput.classList.remove('is-invalid');
                    telefonoInput.classList.add('is-valid');
                    return true;
                }
            }

            function validarCorreo() {
                const correoInput = document.getElementById('correo');
                const correoError = document.getElementById('correoError');
                const correo = correoInput.value.trim();
                // Regex estándar para email
                const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

                if (correo === '') {
                    correoError.textContent = 'El correo electrónico es obligatorio.';
                    correoInput.classList.add('is-invalid');
                    correoInput.classList.remove('is-valid');
                    return false;
                } else if (!emailRegex.test(correo)) {
                    correoError.textContent = 'El correo electrónico no tiene un formato válido.';
                    correoInput.classList.add('is-invalid');
                    correoInput.classList.remove('is-valid');
                    return false;
                } else {
                    correoError.textContent = '';
                    correoInput.classList.remove('is-invalid');
                    correoInput.classList.add('is-valid');
                    return true;
                }
            }

            function validarNombreUsuario() {
                const usuarioInput = document.getElementById('nombreUsuario');
                const usuarioError = document.getElementById('nombreUsuarioError');
                const usuario = usuarioInput.value.trim();
                const minLength = 3;
                const maxLength = 20;
                // Permite letras, números, puntos y guiones bajos. Mínimo 3 caracteres.
                const regex = /^[a-zA-Z0-9._]{3,20}$/; 

                if (usuario === '') {
                    usuarioError.textContent = 'El nombre de usuario es obligatorio.';
                    usuarioInput.classList.add('is-invalid');
                    usuarioInput.classList.remove('is-valid');
                    return false;
                } else if (usuario.length < minLength || usuario.length > maxLength) {
                    usuarioError.textContent = `El usuario debe tener entre ${minLength} y ${maxLength} caracteres.`;
                    usuarioInput.classList.add('is-invalid');
                    usuarioInput.classList.remove('is-valid');
                    return false;
                } else if (!regex.test(usuario)) {
                    usuarioError.textContent = 'El usuario solo puede contener letras, números, puntos y guiones bajos.';
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

            function validarClave() {
                const claveInput = document.getElementById('claveUsuario');
                const claveError = document.getElementById('claveUsuarioError');
                const clave = claveInput.value; 
                
                // Regex: al menos 4 caracteres y al menos 1 número
                const passwordRegex = /^(?=.*\d).{4,}$/;

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

            // Función para validar el formulario completo antes de enviar
            function validarFormulario() {
                // Ejecuta todas las validaciones
                const isCuilValid = validarCuil();
                const isSexoValid = validarSexo();
                const isNombreValid = validarNombre();
                const isApellidoValid = validarApellido();
                const isNacionalidadValid = validarNacionalidad();
                const isFechaNacimientoValid = validarFechaNacimiento();
                const isDireccionValid = validarDireccion();
                const isProvinciaValid = validarProvincia();
                const isLocalidadValid = validarLocalidad();
                const isTelefonoValid = validarTelefono();
                const isCorreoValid = validarCorreo();
                const isNombreUsuarioValid = validarNombreUsuario();
                const isClaveValid = validarClave();
                
                // Devuelve true si TODAS las validaciones son verdaderas
                return isCuilValid && isSexoValid && isNombreValid && isApellidoValid &&
                       isNacionalidadValid && isFechaNacimientoValid && isDireccionValid &&
                       isProvinciaValid && isLocalidadValid && isTelefonoValid &&
                       isCorreoValid && isNombreUsuarioValid && isClaveValid;
            }
	</script>

</body>
</html>