<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Movimientos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
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

		<!-- Tabla de movimientos -->
		<div class="table-responsive">
		  <table class="table table-striped table-hover">
		    <thead class="table-primary">
		      <tr>
		        <th>Fecha</th>
		        <th>Detalle</th>
		        <th>Importe</th>
		        <th>Tipo de movimiento</th>
		      </tr>
		    </thead>
		    <tbody>
		      <tr>
		        <td>2025-06-01</td>
		        <td>Alta de cuenta</td>
		        <td>+$100.000</td>
		        <td>Alta de cuenta</td>
		      </tr>
		      <tr>
		        <td>2025-06-03</td>
		        <td>Transferencia enviada</td>
		        <td>-$20.000</td>
		        <td>Transferencia</td>
		      </tr>
		      <tr>
		        <td>2025-06-04</td>
		        <td>Pago de préstamo</td>
		        <td>-$10.000</td>
		        <td>Pago de préstamo</td>
		      </tr>
		      <tr>
		        <td>2025-06-05</td>
		        <td>Alta de préstamo</td>
		        <td>+$50.000</td>
		        <td>Alta de préstamo</td>
		      </tr>
		      <tr>
		        <td>2025-06-06</td>
		        <td>Transferencia recibida</td>
		        <td>+$5.000</td>
		        <td>Transferencia</td>
		      </tr>
		    </tbody>
		  </table>
		</div>
	</div>
</body>
</html>