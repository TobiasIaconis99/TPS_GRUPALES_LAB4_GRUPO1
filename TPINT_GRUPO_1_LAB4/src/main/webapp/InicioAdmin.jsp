<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Inicio</title>

	<!-- Bootstrap 5.3 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

	<!-- Bootstrap Icons -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

	<!-- Bootstrap Bundle JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<!-- Chart.js (opcional si usás gráficos) -->
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body style="margin: 0; padding: 0;">

	<%@ include file="includes/NavbarAdmin.jsp" %>

	<div class="d-flex">
		<%@ include file="includes/SidebarAdmin.jsp" %>

		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4>Inicio</h4>
			<hr>
			<br>

			<!-- Tarjetas de informes -->
			<div class="row">
				<!-- Tarjeta de Informe A -->
				<div class="col-md-6">
					<div class="card mb-4">
						<div class="card-header bg-primary text-white">
							<i class="bi bi-bar-chart-line-fill me-2"></i> Informe A
						</div>
						<div class="card-body">
							<canvas id="areaChart" width="100%" height="40"></canvas>
						</div>
					</div>
				</div>

				<!-- Tarjeta de Informe B -->
				<div class="col-md-6">
					<div class="card mb-4">
						<div class="card-header bg-primary  text-white">
							<i class="bi bi-bar-chart-line-fill me-2"></i> Informe B
						</div>
						<div class="card-body">
							<canvas id="barChart" width="100%" height="40"></canvas>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

</body>
</html>
