<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Movimientos</title>
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

	<div class="container my-5">
		<div class="list-group mb-4">
			<h4>
				Movimientos de cuenta
			</h4>
			<hr>
		</div>

		<!-- Selección de cuenta -->
		<form method="get" action="VerMovimientos.jsp" class="mb-4">
			<div class="row">
				<div class="col-md-8">
					<label for="cuentaSeleccionada" class="form-label">Seleccione una cuenta</label>
					<select class="form-select" id="cuentaSeleccionada" name="cuentaSeleccionada" required>
						<option value="">-- Seleccionar --</option>
						<%-- Ejemplo de valores, se deben cargar dinámicamente desde backend --%>
						<option value="CA-123456">Caja de Ahorro - CA-123456</option>
						<option value="CC-987654">Cuenta Corriente - CC-987654</option>
					</select>
				</div>
				<div class="col-md-4 d-flex align-items-end">
					<button type="submit" class="btn btn-primary w-100">Ver movimientos</button>
				</div>
			</div>
		</form>

		<!-- Tabla de movimientos -->
		<%
			// Suponiendo que hay una lista de movimientos en la request
			// List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");

			// Simulo con datos fijos para mostrar estructura
			String[][] movimientos = {
				{"2025-06-01", "Alta de cuenta", "+$100.000", "Alta de cuenta"},
				{"2025-06-03", "Transferencia enviada", "-$20.000", "Transferencia"},
				{"2025-06-04", "Pago de préstamo", "-$10.000", "Pago de préstamo"},
				{"2025-06-05", "Alta de préstamo", "+$50.000", "Alta de préstamo"},
				{"2025-06-06", "Transferencia recibida", "+$5.000", "Transferencia"}
			};
		%>

		<% if (movimientos != null && movimientos.length > 0) { %>
			<div class="table-responsive">
				<table class="table table-striped table-hover">
					<thead class="table-light">
						<tr>
							<th>Fecha</th>
							<th>Detalle</th>
							<th>Importe</th>
							<th>Tipo de movimiento</th>
						</tr>
					</thead>
					<tbody>
						<% for (String[] mov : movimientos) { %>
							<tr>
								<td><%= mov[0] %></td>
								<td><%= mov[1] %></td>
								<td><%= mov[2] %></td>
								<td><%= mov[3] %></td>
							</tr>
						<% } %>
					</tbody>
				</table>
			</div>
		<% } else if (request.getParameter("cuentaSeleccionada") != null) { %>
			<div class="alert alert-warning">No se encontraron movimientos para la cuenta seleccionada.</div>
		<% } %>

	</div>
</body>
</html>