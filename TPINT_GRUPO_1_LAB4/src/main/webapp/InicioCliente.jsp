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
	<%@ include file="includes/NavbarCliente.jsp" %>
	<!-- Contenido del panel de cliente -->
	<div class="container mt-5">

		<!-- Cuentas del cliente -->		
		<div>
			<div class="list-group">
			  <button type="button" class="list-group-item list-group-item-action">Caja de ahorro 123</button>
			  <button type="button" class="list-group-item list-group-item-action">Cuenta corriente 456</button>
			</div>
		</div>
		<br>
		
		<!-- Tarjetas de transferencias, prestamos y datos personales -->
		<div class="row">
			<!-- Tarjeta de transferencias -->
			<div class="col-md-4">
				<div class="card bg-primary text-white mb-4">
					<div class="card-body d-flex justify-content-center align-items-center" style="height: 120px;">
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-arrow-left-right" viewBox="0 0 16 16">
						  <path fill-rule="evenodd" d="M1 11.5a.5.5 0 0 0 .5.5h11.793l-3.147 3.146a.5.5 0 0 0 .708.708l4-4a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 11H1.5a.5.5 0 0 0-.5.5m14-7a.5.5 0 0 1-.5.5H2.707l3.147 3.146a.5.5 0 1 1-.708.708l-4-4a.5.5 0 0 1 0-.708l4-4a.5.5 0 1 1 .708.708L2.707 4H14.5a.5.5 0 0 1 .5.5"/>
						</svg>
					</div>
					<div class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="ABMLClientes.jsp">Administrar transferencias.</a>
						<div class="text-white">
							<i class="fas fa-angle-right"></i>
						</div>
					</div>
				</div>
			</div>
			<!-- Tarjeta de prestamos -->
			<div class="col-md-4">
				<div class="card bg-primary text-white mb-4">
					<div class="card-body d-flex justify-content-center align-items-center" style="height: 120px;">
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-cash" viewBox="0 0 16 16">
						  <path d="M8 10a2 2 0 1 0 0-4 2 2 0 0 0 0 4"/>
						  <path d="M0 4a1 1 0 0 1 1-1h14a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1zm3 0a2 2 0 0 1-2 2v4a2 2 0 0 1 2 2h10a2 2 0 0 1 2-2V6a2 2 0 0 1-2-2z"/>
						</svg>
					</div>
					<div
						class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="ABMLUsuarios.jsp">Administrar prestamos.</a>
						<div class="text-white">
							<i class="fas fa-angle-right"></i>
						</div>
					</div>
				</div>
			</div>
			<!-- Tarjeta de datos personales -->
			<div class="col-md-4">
				<div class="card bg-primary text-white mb-4 text-center">
					<div class="card-body d-flex justify-content-center align-items-center" style="height: 120px;">
						<svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
							<path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
						</svg>
					</div>
					<div class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="DatosPersonales.jsp">Ver mis datos personales.</a>
						<div class="text-white">
							<i class="fas fa-angle-right"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>