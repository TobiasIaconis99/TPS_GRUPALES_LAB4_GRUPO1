<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Datos personales</title>
	<!-- Bootstrap 5.3 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<!-- Bootstrap Icons -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<!-- Navbar cliente -->
	<%@ include file="includes/NavbarCliente.jsp" %>

	<!-- Contenido principal -->
	<div class="container my-5">
		<!-- List Group para encabezado -->
		<div class="list-group mb-4">
			<h4>
				Datos personales
			</h4>
			<hr>
		</div>

		<!-- Formulario de datos personales con inputs tipo texto -->
		<form id="formDatosPersonales">
			<div class="row mb-3">
				<div class="col-md-4">
					<label for="dni" class="form-label">DNI</label>
					<input type="text" class="form-control" id="dni" name="dni" required value="12345678" readonly>
				</div>
				<div class="col-md-4">
					<label for="cuil" class="form-label">CUIL</label>
					<input type="text" class="form-control" id="cuil" name="cuil" required value="20-12345678-9" readonly>
				</div>
				<div class="col-md-4">
					<label for="sexo" class="form-label">Sexo</label>
					<input type="text" class="form-control" id="sexo" name="sexo" required value="Masculino" readonly>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-6">
					<label for="nombre" class="form-label">Nombre</label>
					<input type="text" class="form-control" id="nombre" name="nombre" required value="Juan" readonly>
				</div>
				<div class="col-md-6">
					<label for="apellido" class="form-label">Apellido</label>
					<input type="text" class="form-control" id="apellido" name="apellido" required value="Pérez" readonly>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-6">
					<label for="nacionalidad" class="form-label">Nacionalidad</label>
					<input type="text" class="form-control" id="nacionalidad" name="nacionalidad" required value="Argentina" readonly>
				</div>
				<div class="col-md-6">
					<label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
					<input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required value="1990-05-15" readonly>
				</div>
			</div>

			<div class="mb-3">
				<label for="direccion" class="form-label">Dirección</label>
				<input type="text" class="form-control" id="direccion" name="direccion" required value="Calle Falsa 123" readonly>
			</div>

			<div class="row mb-3">
				<div class="col-md-4">
					<label for="provincia" class="form-label">Provincia</label>
					<input type="text" class="form-control" id="provincia" name="provincia" required value="Buenos Aires" readonly>
				</div>
				<div class="col-md-4">
					<label for="localidad" class="form-label">Localidad</label>
					<input type="text" class="form-control" id="localidad" name="localidad" required value="La Plata" readonly>
				</div>
				<div class="col-md-4">
					<label for="telefono" class="form-label">Teléfono</label>
					<input type="tel" class="form-control" id="telefono" name="telefono" required value="+54 9 221 1234567" readonly>
				</div>
			</div>

			<div class="row mb-3">
				<div class="col-md-6">
					<label for="email" class="form-label">Correo electrónico</label>
					<input type="email" class="form-control" id="email" name="email" required value="juan.perez@gmail.com" readonly>
				</div>
				<div class="col-md-6">
					<label for="usuario" class="form-label">Usuario</label>
					<input type="text" class="form-control" id="usuario" name="usuario" required value="jperez" readonly>
				</div>
			</div>

			<!-- Contraseñas no se muestran por seguridad -->

		</form>
	</div>

</body>
</html>