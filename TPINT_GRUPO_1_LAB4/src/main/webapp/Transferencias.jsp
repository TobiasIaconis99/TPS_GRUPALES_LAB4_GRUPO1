<%@page import="negocioImpl.CuentaNegocioImpl"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="entidad.Cuenta"%>
<%@page import="entidad.Cliente"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Transferencias</title>

	<!-- Bootstrap -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<!-- Navbar cliente -->
	<%@ include file="includes/NavbarCliente.jsp" %>

	

	<div class="container my-5">
		<div class="list-group mb-4">
			<div class="d-flex justify-content-between align-items-center">
				<h4 class="mb-0">
					Transferencias
				</h4>
				<a href="InicioCliente.jsp" class="btn btn-primary">
					<i class="bi bi-arrow-left-circle me-1"></i> Volver
				</a>
			</div>
			<hr>
		</div>

		<%
            String mensaje = (String) request.getAttribute("mensaje");
            String error = (String) request.getAttribute("error");

            if (mensaje != null && !mensaje.isEmpty()) {
        %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <%= mensaje %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
        <%
            }
            if (error != null && !error.isEmpty()) {
        %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <%= error %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
        <%
            }
        %>

		<%
            Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");
            List<Cuenta> cuentasCliente = null;

            if (clienteLogueado != null) {
                CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
                cuentasCliente = cuentaNegocio.listarCuentasPorCliente(clienteLogueado.getId());
            }
        %>
		<!-- Formulario -->
		<form method="post" action="<%= request.getContextPath() %>/ServletTransferencia">
			<!-- Cuenta de origen -->
			<div class="mb-3">
				<label for="cuentaOrigen" class="form-label">Cuenta de origen</label>
				<select class="form-select" id="cuentaOrigen" name="cuentaOrigen" required>
			    <option value="">Seleccione una cuenta</option>
			    <% if (cuentasCliente != null && !cuentasCliente.isEmpty()) {
			        for (Cuenta cuenta : cuentasCliente) { %>
			            <option value="<%= cuenta.getIdCuenta() %>">
			                <%= cuenta.getTipoCuenta().getNombreTipoCuenta() %> - <%= cuenta.getNumeroCuenta() %> (Saldo: $<%= cuenta.getSaldo() %>)
			            </option>
			    <%  }
			    } else { %>
			        <option disabled>No tiene cuentas asociadas</option>
			    <% } %>
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

			<!-- Detalle -->
			<div class="mb-3">
				<label for="detalle" class="form-label">Detalle (opcional)</label>
				<textarea class="form-control" id="detalle" name="detalle" rows="2" placeholder="Ej: Pago alquiler, préstamo, etc."></textarea>
			</div>

			<!-- Botón -->
			<div class="row">
				<div class="col-md-8"></div>
				<div class="col-md-4 d-flex align-items-end">
					<button type="submit" class="btn btn-primary w-100">Realizar transferencia</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
