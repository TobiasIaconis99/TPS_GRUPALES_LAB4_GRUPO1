<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Prestamos</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</head>
<body>

	<!-- Barra de navegacion superior -->
	<%@ include file="includes/NavbarAdmin.jsp" %>

<div class="container mt-5">
	<!-- Primer fila con el nombre de la pagina y el boton agregar -->
	<div class="row mb-3">
		<div class="col-md-6">
			<h4>Solicitudes de Préstamos</h4>
		</div>
	</div>

	<!-- Tabla de préstamos -->
	<table class="table table-bordered">
		<thead class="thead-light">
			<tr>
				<th>Cliente</th>
				<th>Cuenta destino</th>
				<th>Fecha de solicitud</th>
				<th>Importe solicitado</th>
				<th>Plazo (meses)</th>
				<th>Cuota mensual</th>
				<th>Estado</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Pablo Torres</td>
				<td>10001</td>
				<td>2025-06-12</td>
				<td>$50.000</td>
				<td>12</td>
				<td>$4.800</td>
				<td>Pendiente</td>
				<td>
					<div class="d-flex">
						<button class="btn btn-sm btn-primary mr-2">Autorizar</button>
						<button class="btn btn-sm btn-danger">Rechazar</button>
					</div>
				</td>
				</tr>
			<tr>
				<td>Laura Gómez</td>
				<td>10003</td>
				<td>2025-06-10</td>
				<td>$30.000</td>
				<td>6</td>
				<td>$5.200</td>
				<td>Pendiente</td>
				<td>
					<button class="btn btn-sm btn-primary">Autorizar</button>
					<button class="btn btn-sm btn-danger">Rechazar</button>
				</td>
			</tr>
		</tbody>
	</table>
</div>

</body>
</html>
