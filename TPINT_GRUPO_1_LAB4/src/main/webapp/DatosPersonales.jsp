<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.Provincia"%>
<%@page import="entidad.Localidad"%>
<%@page import="entidad.Usuario"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Datos personales</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<%@ include file="includes/NavbarCliente.jsp" %>

	<div class="container my-5">
		<div class="list-group mb-4">
			<h4>
				Datos personales
			</h4>
			<hr>
		</div>

		<%
			// Recuperar el objeto Cliente de la sesión
			Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");
			
			String dni = "";
			String cuil = "";
			String sexo = "";
			String nombre = "";
			String apellido = "";
			String nacionalidad = "";
			String fechaNacimiento = "";
			String direccion = "";
			String provincia = "";
			String localidad = "";
			String telefono = "";
			String email = "";
			String nombreusuario = "";
			String clave = "";
			
			if (clienteLogueado != null) {
				dni = clienteLogueado.getDni();
				cuil = clienteLogueado.getCuil();
				
				// Formatear sexo para mostrar el nombre completo
				if (clienteLogueado.getSexo() != null) {
					switch (clienteLogueado.getSexo().toUpperCase()) {
						case "M": sexo = "Masculino"; break;
						case "F": sexo = "Femenino"; break;
						case "O": sexo = "Otro"; break;
						default: sexo = "No especificado"; break;
					}
				}
				
				nombre = clienteLogueado.getNombre();
				apellido = clienteLogueado.getApellido();
				nacionalidad = clienteLogueado.getNacionalidad();
				
				// Formatear la fecha para input type="date" (yyyy-MM-dd)
				if (clienteLogueado.getFechaNacimiento() != null) {
					fechaNacimiento = new java.text.SimpleDateFormat("yyyy-MM-dd").format(clienteLogueado.getFechaNacimiento());
				}
				
				direccion = clienteLogueado.getDireccion();
				
				if (clienteLogueado.getLocalidad() != null) {
					localidad = clienteLogueado.getLocalidad().getNombreLocalidad();
					if (clienteLogueado.getLocalidad().getProvincia() != null) {
						provincia = clienteLogueado.getLocalidad().getProvincia().getNombreProvincia();
					}
				}
				
				telefono = clienteLogueado.getTelefono();
				email = clienteLogueado.getCorreo();
				
				if (clienteLogueado.getUsuario() != null) {
					nombreusuario = clienteLogueado.getUsuario().getNombreUsuario();
					clave = clienteLogueado.getUsuario().getClave(); // Ten cuidado al mostrar claves, es mejor no hacerlo en un entorno real.
				}
			}
		%>

		<form id="formDatosPersonales">
			<div class="row mb-3">
				<div class="col-md-4">
					<label for="dni" class="form-label">DNI</label>
					<input type="text" class="form-control" id="dni" name="dni" required value="<%= dni %>" disabled>
				</div>
				<div class="col-md-4">
					<label for="cuil" class="form-label">CUIL</label>
					<input type="text" class="form-control" id="cuil" name="cuil" required value="<%= cuil %>" disabled>
				</div>
				<div class="col-md-4">
					<label for="sexo" class="form-label">Sexo</label>
					<input type="text" class="form-control" id="sexo" name="sexo" required value="<%= sexo %>" disabled>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-6">
					<label for="nombre" class="form-label">Nombre</label>
					<input type="text" class="form-control" id="nombre" name="nombre" required value="<%= nombre %>" disabled>
				</div>
				<div class="col-md-6">
					<label for="apellido" class="form-label">Apellido</label>
					<input type="text" class="form-control" id="apellido" name="apellido" required value="<%= apellido %>" disabled>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-6">
					<label for="nacionalidad" class="form-label">Nacionalidad</label>
					<input type="text" class="form-control" id="nacionalidad" name="nacionalidad" required value="<%= nacionalidad %>" disabled>
				</div>
				<div class="col-md-6">
					<label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
					<input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required value="<%= fechaNacimiento %>" disabled>
				</div>
			</div>

			<div class="mb-3">
				<label for="direccion" class="form-label">Dirección</label>
				<input type="text" class="form-control" id="direccion" name="direccion" required value="<%= direccion %>" disabled>
			</div>

			<div class="row mb-3">
				<div class="col-md-4">
					<label for="provincia" class="form-label">Provincia</label>
					<input type="text" class="form-control" id="provincia" name="provincia" required value="<%= provincia %>" disabled>
				</div>
				<div class="col-md-4">
					<label for="localidad" class="form-label">Localidad</label>
					<input type="text" class="form-control" id="localidad" name="localidad" required value="<%= localidad %>" disabled>
				</div>
				<div class="col-md-4">
					<label for="telefono" class="form-label">Teléfono</label>
					<input type="tel" class="form-control" id="telefono" name="telefono" required value="<%= telefono %>" disabled>
				</div>
			</div>
			
			<div class="row mb-3">
			    <div class="col-md-12">
					<label for="email" class="form-label">Correo electrónico</label>
					<input type="email" class="form-control" id="email" name="email" required value="<%= email %>" disabled>
				</div>
			</div>
			
			<div class="row mb-3">
				<div class="col-md-6">
					<label for="usuario" class="form-label">Usuario</label>
					<input type="text" class="form-control" id="usuario" name="usuario" required value="<%= nombreUsuario %>" disabled>
				</div>
				<div class="col-md-6">
			        <label for="contrasenia" class="form-label">Clave</label>
			        <input type="text" class="form-control" id="contrasenia" name="contrasenia" required value="<%= clave %>" disabled>
			    </div>
			</div>

		</form>
	</div>

</body>
</html>