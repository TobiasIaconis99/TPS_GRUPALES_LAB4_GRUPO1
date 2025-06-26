<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Préstamos</title>

	<!-- Bootstrap 5.3 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	
	<!-- Bootstrap Icons -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	
	<!-- Bootstrap Bundle -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="margin: 0; padding: 0;">

	<!-- NAVBAR ADMIN -->
	<%@ include file="includes/NavbarAdmin.jsp" %>

	<div class="d-flex">
		<!-- SIDEBAR -->
		<%@ include file="includes/SidebarAdmin.jsp" %>
		
		<!-- Contenedor principal -->
		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4>Prestamos</h4>
			<hr />
			<br />
			<div class="row mb-3">
				<div class="col-md-6">
					<!-- espacio para futuros filtros u otros elementos -->
				</div>
				<div class="col-md-6 text-end">
					<!-- espacio para futuros filtros u otros elementos -->
				</div>
			</div>

		<!-- Tabla de préstamos -->
		<div class="table-responsive">
			<table class="table table-bordered">
				<thead class="table-light">
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
								<button class="btn btn-sm btn-success me-2">
									<i class="bi bi-check-circle"></i>
								</button>
								<button class="btn btn-sm btn-danger">
									<i class="bi bi-x-circle"></i>
								</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

</body>
</html>

