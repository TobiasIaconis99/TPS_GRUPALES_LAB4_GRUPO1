<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Préstamos</title>

	<!-- Bootstrap 5.3 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<!-- Navbar cliente -->
	<%@ include file="includes/NavbarCliente.jsp" %>

	<div class="container my-5">

		<!-- Título -->
		<div class="list-group mb-4">
			<h4>Préstamos</h4>
			<hr>
		</div>

		<!-- Solicitar préstamo -->
		<div class="card mb-5">
			<div class="card-header bg-primary text-white">
				Solicitar nuevo préstamo
			</div>
			<div class="card-body">
				<form id="formNuevoPrestamo" method="post" action="SolicitarPrestamoServlet">
					<div class="row mb-3">
						<div class="col-md-6">
							<label for="montoSolicitado" class="form-label">Importe solicitado</label>
							<input type="number" class="form-control" id="montoSolicitado" name="montoSolicitado" required min="1000" step="0.01" placeholder="Ej: 50000">
						</div>
						<div class="col-md-6">
							<label for="cuotas" class="form-label">Cantidad de cuotas (meses)</label>
							<select class="form-select" id="cuotas" name="cuotas" required>
								<option value="">-- Seleccionar --</option>
								<option value="6">6 meses</option>
								<option value="12">12 meses</option>
								<option value="18">18 meses</option>
								<option value="24">24 meses</option>
							</select>
						</div>
					</div>

					<div class="mb-3">
						<label for="cuentaDestino" class="form-label">Cuenta para depósito</label>
						<select class="form-select" id="cuentaDestino" name="cuentaDestino" required>
							<option value="">-- Seleccionar --</option>
							<option value="CA-123456">Caja de Ahorro - CA-123456</option>
							<option value="CC-987654">Cuenta Corriente - CC-987654</option>
						</select>
					</div>

			<div class="row">
				<div class="col-md-8">
					</div>
					<div class="col-md-4 d-flex align-items-end">
						<button type="submit" class="btn btn-primary w-100">Realizar transferencia</button>
					</div>
				</div>
				</form>
			</div>
		</div>

		<!-- Pagar cuotas -->
		<div class="card">
			<div class="card-header bg-primary text-white">
				Pago de cuotas pendientes
			</div>
			<div class="card-body">

				<!-- Simulación de un préstamo activo -->
				<h6>Préstamo N°1</h6>
				<p>
					Importe: $30.000 &nbsp; | &nbsp;
					Cuotas:12 &nbsp; | &nbsp;
					Importe mensual: $5.500 &nbsp; | &nbsp;
					Cuenta asociada: CA-123456
				</p>
				<!-- Tabla de cuotas -->
				<table class="table table-bordered table-striped">
					<thead class="table-light">
						<tr>
							<th>Cuota</th>
							<th>Importe</th>
							<th>Fecha de vencimiento</th>
							<th>Fecha de pago</th>
							<th>Acción</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>$5.500</td>
							<td>2025-07-01</td>
							<td><em>No pagada</em></td>
							<td>
								<form method="post" action="PagarCuotaServlet" class="d-flex align-items-center">
									<select name="cuentaPago" class="form-select form-select-sm me-2" required>
										<option value="">Seleccionar</option>
										<option value="CA-123456">CA-123</option>
										<option value="CC-987654">CC-456</option>
									</select>
									<button type="submit" class="btn btn-sm btn-primary">
										Pagar
									</button>
								</form>
							</td>
						</tr>
						<tr>
							<td>2</td>
							<td>$5.500</td>
							<td>2025-08-01</td>
							<td><em>No pagada</em></td>
							<td>
								<form method="post" action="PagarCuotaServlet" class="d-flex align-items-center">
									<select name="cuentaPago" class="form-select form-select-sm me-2" required>
										<option value="">Seleccionar</option>
										<option value="CA-123456">CA-123</option>
										<option value="CC-987654">CC-456</option>
									</select>
									<button type="submit" class="btn btn-sm btn-primary">
										Pagar
									</button>
								</form>
							</td>
						</tr>
						<tr class="table-success">
							<td>3</td>
							<td>$5.500</td>
							<td>2025-06-01</td>
							<td>2025-06-05</td>
							<td><i class="bi bi-check-circle-fill text-success"></i> Pagada</td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>

	</div>
</body>
</html>
