<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Inicio</title>

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

		<!-- Cuentas del cliente -->
		<div class="list-group mb-4">
			<button type="button" class="list-group-item list-group-item-action">Caja de ahorro 123</button>
			<button type="button" class="list-group-item list-group-item-action">Cuenta corriente 456</button>
		</div>

		<!-- Tarjetas de transferencias, prestamos y datos personales -->
		<div class="row g-4">
			<!-- Tarjeta de transferencias -->
			<div class="col-md-4">
				<div class="card bg-primary text-white h-100">
					<div class="card-body d-flex justify-content-center align-items-center" style="height: 120px;">
						<i class="bi bi-arrow-left-right fs-1"></i>
					</div>
					<div class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="">Administrar transferencias.</a>
						<div class="text-white">
							<i class="bi bi-chevron-right"></i>
						</div>
					</div>
				</div>
			</div>
			<!-- Tarjeta de prestamos -->
			<div class="col-md-4">
				<div class="card bg-primary text-white h-100">
					<div class="card-body d-flex justify-content-center align-items-center" style="height: 120px;">
						<i class="bi bi-cash fs-1"></i>
					</div>
					<div class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="">Administrar préstamos.</a>
						<div class="text-white">
							<i class="bi bi-chevron-right"></i>
						</div>
					</div>
				</div>
			</div>
			<!-- Tarjeta de datos personales -->
			<div class="col-md-4">
				<div class="card bg-primary text-white h-100 text-center">
					<div class="card-body d-flex justify-content-center align-items-center" style="height: 120px;">
						<i class="bi bi-person-fill fs-1"></i>
					</div>
					<div class="card-footer d-flex align-items-center justify-content-between">
						<a class="text-white small stretched-link" href="DatosPersonales.jsp">Ver mis datos personales.</a>
						<div class="text-white">
							<i class="bi bi-chevron-right"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>