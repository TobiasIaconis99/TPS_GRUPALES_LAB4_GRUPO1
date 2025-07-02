<%@page import="java.util.List"%>
<%@page import="entidad.Provincia"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
    <%@ include file="includes/NavbarAdmin.jsp" %>
    <div class="d-flex">
        <%@ include file="includes/SidebarAdmin.jsp" %>

        <div class="flex-grow-1 p-4" style="margin-left: 250px;">
            <h4>Agregar nuevo cliente</h4>
            <hr />

            <form id="formAgregarCliente" action="ServletCliente" method="post" onsubmit="return validarFormulario()">
                <input type="hidden" name="accion" value="agregar">

                <div class="row mb-3">
                    <div class="col-md-4">
                        <label for="dni" class="form-label">DNI</label>
                        <input type="text" id="dni" name="dni" class="form-control" placeholder="XXXXXXXX" required onblur="validarDni()" oninput="validarDni()">
                        <div id="dniError" class="text-danger"></div>
                    </div>
                    <div class="col-md-4">
                        <label for="cuil" class="form-label">CUIL</label>
                        <input type="text" id="cuil" name="cuil" class="form-control" placeholder="XX-XXXXXXXX-X" required onblur="validarCuil()" oninput="validarCuil()">
                        <div id="cuilError" class="text-danger"></div>
                    </div>
                    <div class="col-md-4">
                        <label for="sexo" class="form-label">Sexo</label>
                        <select id="sexo" name="sexo" class="form-select" required onblur="validarSexo()" onchange="validarSexo()">
                            <option value="">Seleccione</option>
                            <option value="M">Masculino</option>
                            <option value="F">Femenino</option>
                            <option value="O">Otro</option>
                        </select>
                        <div id="sexoError" class="text-danger"></div>
                    </div>
                </div>

                <div class="row mb-3">
					<div class="col-md-6">
						<label for="nombre" class="form-label">Nombre</label>
						<input type="text" id="nombre" name="nombre" class="form-control" placeholder="Ej.: Roberto" required onblur="validarNombre()" oninput="validarNombre()">
						<div id="nombreError" class="text-danger"></div>
					</div>

					<div class="col-md-6">
                        <label for="apellido" class="form-label">Apellido</label>
                        <input type="text" id="apellido" name="apellido" class="form-control" placeholder="Ej.: Acosta" required onblur="validarApellido()" oninput="validarApellido()">
                        <div id="apellidoError" class="text-danger"></div>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="nacionalidad" class="form-label">Nacionalidad</label>
                        <input type="text" id="nacionalidad" name="nacionalidad" class="form-control" placeholder="Ej.: Argentina" required onblur="validarNacionalidad()" oninput="validarNacionalidad()">
                        <div id="nacionalidadError" class="text-danger"></div>
                    </div>
                    <div class="col-md-6">
                        <label for="fechaNacimiento" class="form-label">Fecha de nacimiento</label>
                        <input type="date" id="fechaNacimiento" name="fechaNacimiento" class="form-control" required onblur="validarFechaNacimiento()">
                        <div id="fechaNacimientoError" class="text-danger"></div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="direccion" class="form-label">Dirección</label>
                    <input type="text" id="direccion" name="direccion" class="form-control" placeholder="Ej.: Avenida de Mayo 1000" required onblur="validarDireccion()" oninput="validarDireccion()">
                    <div id="direccionError" class="text-danger"></div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="provincia" class="form-label">Provincia</label>
                        <select class="form-select" name="idProvincia" id="provincia" onchange="cargarLocalidades(this.value); validarProvincia()" onblur="validarProvincia()" required>
                            <option value="">Seleccione una provincia</option>
                            <%
                                List<Provincia> provincias = (List<Provincia>) request.getAttribute("listaProvincias");
                                if (provincias != null) {
                                    for (Provincia p : provincias) {
                            %>
                                <option value="<%= p.getIdProvincia() %>"><%= p.getNombreProvincia() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                        <div id="provinciaError" class="text-danger"></div>
                    </div>
                    <div class="col-md-6">
                        <label for="localidad" class="form-label">Localidad</label>
                        <select class="form-select" name="idLocalidad" id="localidad" required onblur="validarLocalidad()" onchange="validarLocalidad()">
                            <option value="">Seleccione una provincia primero</option>
                        </select>
                        <div id="localidadError" class="text-danger"></div>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="correo" class="form-label">Correo</label>
                        <input type="email" id="correo" name="correo" class="form-control" placeholder="Ej.: roberto.acosta@example.com" required onblur="validarCorreo()" oninput="validarCorreo()">
                        <div id="correoError" class="text-danger"></div>
                    </div>
                    <div class="col-md-6">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="tel" id="telefono" name="telefono" class="form-control" placeholder="Ej.: +5491140111222" required onblur="validarTelefono()" oninput="validarTelefono()">
                        <div id="telefonoError" class="text-danger"></div>
                    </div>
                </div>

				<div class="row mb-3">
				  <div class="col-md-4">
				    <label for="nombreUsuario" class="form-label">Usuario</label>
				    <input type="text" id="nombreUsuario" name="nombreUsuario" class="form-control" placeholder="Ej.: roberto" required onblur="validarNombreUsuario()" oninput="validarNombreUsuario()">
                    <div id="nombreUsuarioError" class="text-danger"></div>
				  </div>
				
				  <div class="col-md-4">
				    <label for="claveUsuario" class="form-label">Clave</label>
				    <input type="text" id="claveUsuario" name="claveUsuario" class="form-control" placeholder="roberto123" required onblur="validarClave()" oninput="validarClave()">
                    <div id="claveUsuarioError" class="text-danger"></div>
				  </div>
				
				  <div class="col-md-4">
				    <label for="repetirClaveUsuario" class="form-label">Repetir clave</label>
				    <input type="text" id="repetirClaveUsuario" name="repetirClaveUsuario" class="form-control" placeholder="roberto123" required onblur="validarRepetirClave()" oninput="validarRepetirClave()">
				    <div id="repetirClaveUsuarioError" class="text-danger"></div>
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
        // No se necesitan idProvinciaCliente ni idLocalidadCliente aquí ya que es un formulario de AGREGAR
        // Tampoco dniClienteJSP, el DNI se ingresa nuevo

    	// Esta funcion sirve para cargar las localidades de la BD segun la provincia que se elija
        function cargarLocalidades(idProvincia) {
            const localidadSelect = document.getElementById('localidad');
            localidadSelect.innerHTML = '<option>Cargando...</option>';
            localidadSelect.disabled = true;

            fetch('ServletLocalidad?accion=listarLocalidades&idProvincia=' + idProvincia)
                .then(res => {
                    if (!res.ok) {
                        throw new Error('Network response was not ok ' + res.statusText);
                    }
                    return res.json();
                })
                .then(data => {
                    localidadSelect.innerHTML = '<option value="">Seleccione una localidad</option>';
                    data.forEach(loc => {
                        const option = document.createElement('option');
                        option.value = loc.idLocalidad;
                        option.textContent = loc.nombreLocalidad;
                        localidadSelect.appendChild(option);
                    });
                    localidadSelect.disabled = false;
                    validarLocalidad(); // Valida la localidad una vez cargada
                })
                .catch(err => {
                    localidadSelect.innerHTML = '<option value="">Error al cargar localidades</option>';
                    localidadSelect.disabled = false;
                    console.error('Error al cargar localidades:', err);
                });
        }
    	
        // Llama a todas las validaciones al cargar para precargar el estado visual (útil para campos con valores por defecto o pre-seleccionados)
        document.addEventListener('DOMContentLoaded', function() {
            // Para "Agregar Cliente", no precargamos validaciones iniciales de todos los campos
            // porque están vacíos y las validaciones se activarán al interactuar.
            // Si tuvieras campos con valores por defecto y quisieras mostrarlos como válidos
            // al cargar, los llamarías aquí.
            // Ejemplo (si tuvieras un DNI precargado para algún test):
            // validarDni(); 
        });
        
        // --- Funciones de Validación de Campos Individuales ---

        function validarDni() {
            const dniInput = document.getElementById('dni');
            const dniError = document.getElementById('dniError');
            const dni = dniInput.value.trim();
            // Regex para DNI: 7 a 8 dígitos (ej. XXXXXXX o XXXXXXXX)
            const dniRegex = /^\d{7,8}$/; 

            if (dni === '') {
                dniError.textContent = 'El DNI es obligatorio.';
                dniInput.classList.add('is-invalid');
                dniInput.classList.remove('is-valid');
                return false;
            } else if (!dniRegex.test(dni)) {
                dniError.textContent = 'El DNI debe contener 7 u 8 dígitos numéricos.';
                dniInput.classList.add('is-invalid');
                dniInput.classList.remove('is-valid');
                return false;
            } else {
                dniError.textContent = '';
                dniInput.classList.remove('is-invalid');
                dniInput.classList.add('is-valid');
                return true;
            }
        }

        function validarCuil() {
            const cuilInput = document.getElementById('cuil');
            const cuilError = document.getElementById('cuilError');
            const cuil = cuilInput.value.trim();
            const dni = document.getElementById('dni').value.trim(); // Obtener el DNI del campo DNI

            // Regex para CUIL: OBLIGA el formato XX-XXXXXXXX-X
            const cuilRegex = /^\d{2}-\d{8}-\d{1}$/; 

            if (cuil === '') {
                cuilError.textContent = 'El CUIL es obligatorio.';
                cuilInput.classList.add('is-invalid');
                cuilInput.classList.remove('is-valid');
                return false;
            } else if (!cuilRegex.test(cuil)) {
                cuilError.textContent = 'El CUIL debe tener el formato XX-XXXXXXXX-X (ej: 20-12345678-9).';
                cuilInput.classList.add('is-invalid');
                cuilInput.classList.remove('is-valid');
                return false;
            } else {
                // Extraer solo los 8 dígitos centrales del CUIL (corresponden al DNI)
                const cuilDniParte = cuil.substring(3, 11); 

                // Validar que la parte central del CUIL coincida con el DNI ingresado
                if (dni === '') { // Si el DNI no ha sido ingresado o es inválido, no podemos comparar
                    cuilError.textContent = 'Ingrese un DNI válido para verificar la coincidencia del CUIL.';
                    cuilInput.classList.add('is-invalid');
                    cuilInput.classList.remove('is-valid');
                    return false;
                } else if (cuilDniParte !== dni) {
                    cuilError.textContent = `Los dígitos centrales del CUIL (${cuilDniParte}) no coinciden con el DNI (${dni}).`;
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
                nombreError.textContent = 'El nombre solo puede contener letras y espacios.';
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
                apellidoError.textContent = 'El apellido solo puede contener letras y espacios.';
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
                nacionalidadError.textContent = 'La nacionalidad solo puede contener letras y espacios.';
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
            const fechaNacimiento = new Date(fechaInput.value + 'T00:00:00'); 
            const hoy = new Date();
            hoy.setHours(0,0,0,0); 
            const edadMinima = 18; 
            const fechaMinimaValida = new Date(hoy.getFullYear() - edadMinima, hoy.getMonth(), hoy.getDate());
            fechaMinimaValida.setHours(0,0,0,0); 

            if (fechaInput.value === '') {
                fechaError.textContent = 'La fecha de nacimiento es obligatoria.';
                fechaInput.classList.add('is-invalid');
                fechaInput.classList.remove('is-valid');
                return false;
            } else if (isNaN(fechaNacimiento.getTime())) { 
                fechaError.textContent = 'Formato de fecha inválido.';
                fechaInput.classList.add('is-invalid');
                fechaInput.classList.remove('is-valid');
                return false;
            } else if (fechaNacimiento > hoy) {
                fechaError.textContent = 'La fecha de nacimiento no puede ser futura.';
                fechaInput.classList.add('is-invalid');
                fechaInput.classList.remove('is-valid');
                return false;
            } else if (fechaNacimiento > fechaMinimaValida) {
                fechaError.textContent = `El cliente debe ser mayor de ${edadMinima} años.`;
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
            const regex = /^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ\s.,#\-\/]+$/;

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
            const regex = /^[0-9+\s\-()]{7,20}$/; 

            if (telefono === '') {
                telefonoError.textContent = 'El teléfono es obligatorio.';
                telefonoInput.classList.add('is-invalid');
                telefonoInput.classList.remove('is-valid');
                return false;
            } else if (!regex.test(telefono)) {
                telefonoError.textContent = 'El teléfono no tiene un formato válido (solo números, +, -, (), espacios).';
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

        function validarRepetirClave() {
            const claveInput = document.getElementById('claveUsuario');
            const repetirClaveInput = document.getElementById('repetirClaveUsuario');
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


        // Función para validar el formulario completo antes de enviar
        function validarFormulario() {
            // Ejecuta todas las validaciones
            const isDniValid = validarDni();
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
            const isRepetirClaveValid = validarRepetirClave();
            
            // Devuelve true si TODAS las validaciones son verdaderas
            return isDniValid && isCuilValid && isSexoValid && isNombreValid && isApellidoValid &&
                   isNacionalidadValid && isFechaNacimientoValid && isDireccionValid &&
                   isProvinciaValid && isLocalidadValid && isTelefonoValid &&
                   isCorreoValid && isNombreUsuarioValid && isClaveValid && isRepetirClaveValid;
        }
    </script>
    
</body>
</html>