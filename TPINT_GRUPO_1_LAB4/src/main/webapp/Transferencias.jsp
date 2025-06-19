<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Transferencias</title>

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
			<h4>Transferencias</h4>
			<hr>
		</div>

		<form id="formTransferencia" method="post" action="ProcesarTransferenciaServlet">
			<!-- Cuenta de origen -->
			<div class="mb-3">
				<label for="cuentaOrigen" class="form-label">Cuenta de origen</label>
				<select class="form-select" id="cuentaOrigen" name="cuentaOrigen" required>
					<option value="">-- Seleccione una cuenta --</option>
					<!-- Ejemplo de cuentas del cliente -->
					<option value="CA-123456">Caja de ahorro 123 (Saldo: $150.000)</option>
					<option value="CC-987654">Cuenta corriente 456 (Saldo: $80.000)</option>
				</select>
			</div>

			<!-- CBU destino -->
			<div class="mb-3">
				<label for="cbuDestino" class="form-label">CBU de destino</label>
				<input type="text" class="form-control" id="cbuDestino" name="cbuDestino" required maxlength="22" placeholder="Ej: 2850590940090418135201">
			</div>

			<!-- Monto -->
			<div class="mb-3">
				<label for="monto" class="form-label">Monto a transferir</label>
				<input type="number" class="form-control" id="monto" name="monto" required min="1" step="0.01" placeholder="Ej: 5000.00">
			</div>

			<!-- Concepto / Detalle -->
			<div class="mb-3">
				<label for="detalle" class="form-label">Detalle (opcional)</label>
				<textarea class="form-control" id="detalle" name="detalle" rows="2" placeholder="Ej: Pago alquiler, préstamo, etc."></textarea>
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
</body>
</html>