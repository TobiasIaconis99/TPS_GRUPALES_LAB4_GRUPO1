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
		  <a href="#" class="list-group-item list-group-item-action active fw-bold">Mis cuentas</a>
		
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

		<style>
			.square-card {
				aspect-ratio: 1 / 1; /* Relación 1:1 para que sean cuadradas */
				width: 100%;
			}
		</style>
		
		<div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4 mt-5 text-center">
		
			<!-- Transferencias -->
			<div class="col">
				<div class="card square-card border-0 shadow bg-primary text-white position-relative">
						<a href="Transferencias.jsp" class="stretched-link"></a>
					<div class="card-body d-flex flex-column justify-content-center align-items-center">
						<i class="bi bi-arrow-left-right fs-1 mb-3"></i>
						<h5 class="fw-bold fs-5 mb-0">Transferencias</h5>
					</div>
				</div>
			</div>
		
			<!-- Préstamos -->
			<div class="col">
				<div class="card square-card border-0 shadow bg-primary text-white position-relative">
					<a href="Prestamos.jsp" class="stretched-link"></a>
					<div class="card-body d-flex flex-column justify-content-center align-items-center">
						<i class="bi bi-cash fs-1 mb-3"></i>
						<h5 class="fw-bold fs-5 mb-0">Préstamos</h5>
					</div>
				</div>
			</div>
		
			<!-- Datos personales -->
			<div class="col">
				<div class="card square-card border-0 shadow bg-primary text-white position-relative">
					<a href="DatosPersonales.jsp" class="stretched-link"></a>
					<div class="card-body d-flex flex-column justify-content-center align-items-center">
						<i class="bi bi-person-fill fs-1 mb-3"></i>
						<h5 class="fw-bold fs-5 mb-0">Datos personales</h5>
					</div>
				</div>
			</div>
		
			<div class="col">
				<div class="card border-0 shadow bg-light text-white position-relative overflow-hidden square-card">
					<a href="ConoceMas.jsp" class="stretched-link" aria-label="Conocé más sobre nosotros"></a>
			
					<!-- Imagen de fondo -->
					<img src="<%= request.getContextPath() %>/img/logoBanco.png"
					     alt="Logo del Banco"
					     class="w-100 h-100 position-absolute top-0 start-0"
					     style="object-fit: cover;" />
			
					<!-- Capa de color primario semitransparente -->
					<div class="position-absolute top-0 start-0 w-100 h-100" style="background-color: rgba(var(--bs-primary-rgb), 0.5);"></div>
			
					<!-- Contenido centrado sobre la imagen -->
					<div class="position-absolute top-50 start-50 translate-middle text-center">
						<h5 class="fw-bold fs-5 mb-0">Conocé más sobre nosotros</h5>
					</div>
				</div>
			</div>

		</div>


	</div>
</body>
</html>