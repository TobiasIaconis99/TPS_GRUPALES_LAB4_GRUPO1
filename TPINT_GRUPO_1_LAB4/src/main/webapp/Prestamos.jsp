<%@page import="negocioImpl.CuentaNegocioImpl"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="dao.CuentaDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="entidad.Prestamo"%>
<%@page import="entidad.TipoCuenta"%>
<%@page import="negocio.TipoCuentaNegocio"%>
<%@page import="java.util.List"%>
<%@page import="negocioImpl.TipoCuentaNegocioImpl"%>
<%@page import="entidad.Cuenta"%>
<%@page import="entidad.Cliente"%>



<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Préstamos</title>

<!-- Bootstrap 5.3 -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<!-- Navbar cliente -->
	<%@ include file="includes/NavbarCliente.jsp"%>

	<div class="container my-5">



		<!-- Título -->
		<div class="list-group mb-4">
			<h4>Préstamos</h4>
			<hr>
		</div>

		<%
		// Mensaje exito
		String mensajeExito = (String) session.getAttribute("mensajeExito");
		if (mensajeExito != null) {
			session.removeAttribute("mensajeExito");
		%>
		<div class="alert alert-success alert-dismissible fade show"
			role="alert">
			<i class="bi bi-check-circle me-1"></i>
			<%=mensajeExito%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
		}
		
		String mensajeError = (String) session.getAttribute("mensajeError");
		if (mensajeError != null) {
		session.removeAttribute("mensajeError");
		%>
		<div class="alert alert-danger alert-dismissible fade show"
			role="alert">
			<i class="bi bi-x-circle me-1"></i>
			<%=mensajeError%>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>
		<%
		}
		%>



		<!-- Solicitar préstamo -->
		<div class="card mb-5">
			<div class="card-header bg-primary text-white">Solicitar nuevo
				préstamo</div>
			<div class="card-body">
				<form id="formNuevoPrestamo" method="post"
					action="ServletSolicitarPrestamo">
					<div class="row mb-3">
						<div class="col-md-6">
							<label for="montoSolicitado" class="form-label">Importe
								solicitado</label> <input type="number" class="form-control"
								id="montoSolicitado" name="montoSolicitado" required min="1000"
								step="0.01" placeholder="Ej: 50000">
						</div>
						<div class="col-md-6">
							<label for="cuotas" class="form-label">Cantidad de cuotas
								(meses)</label> <select class="form-select" id="cuotas" name="cuotas"
								required>
								<option value="">-- Seleccionar --</option>
								<option value="6">6 meses</option>
								<option value="12">12 meses</option>
								<option value="18">18 meses</option>
								<option value="24">24 meses</option>
							</select>
						</div>
					</div>



					<div class="mb-3">
						<label for="cuentaDestino" class="form-label">Cuenta para
							depósito</label>
						<%
						Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
						int idCliente = -1;
						if (cliente != null) {
							idCliente = cliente.getId();
						}

						CuentaNegocio cNegocio = new CuentaNegocioImpl();
						List<Cuenta> cuentas = cNegocio.obtenerCuentaPorClienteId(idCliente);
						%>

						<select class="form-select" id="cuentaDestino"
							name="cuentaDestino" required>
							<option value="">Seleccione una cuenta...</option>
							<%
							for (Cuenta c : cuentas) {
							%>
							<option value="<%=c.getIdCuenta()%>">
								<%=c.getTipoCuenta().getNombreTipoCuenta()%> -
								<%=c.getNumeroCuenta()%> - $<%=c.getSaldo()%>
							</option>

							<%
							}
							%>
						</select>
					</div>





					<div class="row">
						<div class="col-md-8"></div>
						<div class="col-md-4 d-flex align-items-end">
							<button type="submit" class="btn btn-primary w-100">Solicitar
								prestamo</button>
						</div>
					</div>
					<div class="row">
						<div class="col-md-4 d-flex justify-content-start">
							<a href="PagoPrestamos.jsp" target="_blank"
								class="btn btn-primary"> Ver mis prestamos </a>
						</div>
						<div class="col-md-8"></div>
					</div>

				</form>
			</div>
		</div>


	</div>
</body>
</html>
