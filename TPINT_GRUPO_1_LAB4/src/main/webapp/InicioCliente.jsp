<%@page import="negocioImpl.CuentaNegocioImpl"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="java.util.List"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.Cuenta"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Inicio</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

	<!-- Navbar cliente -->
	<%@ include file="includes/NavbarCliente.jsp" %>

	<!-- Contenido principal -->
	<div class="container my-5">

		<%
		    // 1. Recuperar el cliente de la sesion
		    Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");
		    List<Cuenta> listaCuentasDelCliente = null;

		    if (clienteLogueado != null) {
		        // 2. Instanciar el CuentaNegocio y obtener las cuentas del cliente
		        CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
		        listaCuentasDelCliente = cuentaNegocio.listarCuentasPorCliente(clienteLogueado.getId());
		    }
		%>
		<div class="list-group mb-4">
		  <a href="#" class="list-group-item list-group-item-action active">Mis cuentas</a>
		
		  <% if (listaCuentasDelCliente != null && !listaCuentasDelCliente.isEmpty()) { %>
		      <% for (Cuenta c : listaCuentasDelCliente) { %>
		           <a href="<%= request.getContextPath() %>/ServletMovimiento?accion=listar&idCuenta=<%= c.getIdCuenta() %>" class="list-group-item list-group-item-action">
		              <small class="text-muted"><%= c.getTipoCuenta().getNombreTipoCuenta() %></small><br>
		              <span>
		                <strong><%= c.getNumeroCuenta() %></strong>
		                <span class="float-end fw-bold text-primary fs-4">$<%= c.getSaldo() %></span>
		              </span>
		              <br>
		              <small class="text-muted">CBU: <%= c.getCbu() %></small>
		          </a>
		      <% } %>
		  <% } else { %>
		      <a href="#" class="list-group-item list-group-item-action">No se encontraron cuentas para este cliente.</a>
		  <% } %>
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
						<a class="text-white stretched-link" href="Transferencias.jsp">Administrar transferencias.</a>
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
						<a class="text-white stretched-link" href="Prestamos.jsp">Administrar préstamos.</a>
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
						<a class="text-white stretched-link" href="DatosPersonales.jsp">Ver mis datos personales.</a>
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