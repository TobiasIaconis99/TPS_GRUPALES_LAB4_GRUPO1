<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Inicio</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</head>
<body>

	<!-- Barra de navegacion superior -->
	<%@ include file="includes/NavbarAdmin.jsp" %>

	<!-- Contenido del panel de administracion -->
	<div class="container mt-5">

		<!-- Primer fila con el nombre de la pagina y el boton agregar -->
		<div class="row mb-3">
			<div class="col-md-6">
				<h4>Panel de administración</h4>
			</div>
		</div>
		<!-- Tarjetas de clientes, usuarios, cuentas y prestamos -->
		<div class="row">
			<!-- Tarjeta de clientes -->
			<div class="col-md-3">
				<div class="card bg-primary text-white mb-4">
					<div class="card-body">Clientes</div>
					<div class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="ABMLClientes.jsp">Administrar clientes del sistema.</a>
						<div class="text-white">
							<i class="fas fa-angle-right"></i>
						</div>
					</div>
				</div>
			</div>
			<!-- Tarjeta de usuarios -->
			<div class="col-md-3">
				<div class="card bg-primary text-white mb-4">
					<div class="card-body">Usuarios</div>
					<div
						class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="ABMLUsuarios.jsp">Administrar accesos al sistema.</a>
						<div class="text-white">
							<i class="fas fa-angle-right"></i>
						</div>
					</div>
				</div>
			</div>
			<!-- Tarjeta de cuentas -->
			<div class="col-md-3">
				<div class="card bg-primary text-white mb-4">
					<div class="card-body">Cuentas</div>
					<div
						class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="ABMLCuentas.jsp">Gestionar cuentas bancarias.</a>
						<div class="text-white">
							<i class="fas fa-angle-right"></i>
						</div>
					</div>
				</div>
			</div>
			<!-- Tarjeta de prestamos -->
			<div class="col-md-3">
				<div class="card bg-primary text-white mb-4">
					<div class="card-body">Préstamos</div>
					<div
						class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link"
							href="AutorizacionPrestamos.jsp">Autorizar o rechazar solicitudes.</a>
						<div class="text-white">
							<i class="fas fa-angle-right"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<!-- Tarjeta de informes -->
		<div class="row">
		<!-- Tarjeta de Informe A -->
			<div class="col-md-6">
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-chart-area"></i> Informe A
					</div>
					<div class="card-body">
						<canvas id="areaChart" width="100%" height="40"></canvas>
					</div>
				</div>
			</div>
			<!-- Tarjeta de Informe B -->
			<div class="col-md-6">
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-chart-bar"></i> Informe B
					</div>
					<div class="card-body">
						<canvas id="barChart" width="100%" height="40"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>