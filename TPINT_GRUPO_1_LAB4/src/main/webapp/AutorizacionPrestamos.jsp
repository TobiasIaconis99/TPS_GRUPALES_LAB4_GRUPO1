<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Préstamos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="margin: 0; padding: 0;">

	<%@ include file="includes/NavbarAdmin.jsp" %>
	
	<div class="d-flex">
	
		<%@ include file="includes/SidebarAdmin.jsp" %>
		
		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4>Préstamos</h4>
			<hr />
			<br />
			<div class="row mb-3 align-items-end">
				<div class="col-md-9">
					<form method="get" action="" class="row gx-2">
						<input type="hidden" name="accion" value="listar">
						<div class="col-md-4">
							<input type="text" name="busqueda" class="form-control" placeholder="Buscar por DNI" value="">
						</div>
				
						<div class="col-md-3">
							<select name="filtro" class="form-select">
								<option value="">Todos los estados</option>
								<option>Pendiente</option>
								<option>Aprobado</option>
								<option>Rechazado</option>
							</select>
						</div>
				
						<div class="col-md-3">
							<button class="btn btn-primary w-100" type="submit">
								<i class="bi bi-search me-1"></i> Buscar
							</button>
						</div>
					</form>
				</div>
			</div>

		<!-- Tabla de préstamos -->
		<div class="table-responsive">
			<table class="table table-bordered">
				<thead class="table-primary">
					<tr>
						<th>Cliente</th>
						<th>Cuenta destino</th>
						<th>Fecha de solicitud</th>
						<th>Importe solicitado</th>
						<th>Plazo (meses)</th>
						<th>Cantida de cuotas</th>
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
		
		<!-- Inicio de paginacion -->
			<%
			    // Recuperamos filtros y búsqueda
			    String busqueda = request.getParameter("busqueda") != null ? request.getParameter("busqueda") : "";
			    String filtroSexo = request.getParameter("filtroSexo") != null ? request.getParameter("filtroSexo") : "";
			
			    int paginaActual = request.getAttribute("paginaActual") != null ? (int) request.getAttribute("paginaActual") : 1;
			    int totalPaginas = request.getAttribute("totalPaginas") != null ? (int) request.getAttribute("totalPaginas") : 1;
			%>
			
			<nav aria-label="Paginación">
			  <ul class="pagination justify-content-center">
			
			    <!-- Botón Anterior -->
			    <li class="page-item disabled">
			      <a class="page-link" href="#">Anterior</a>
			    </li>
			
			    <!-- Números de página -->
			    <li class="page-item"><a class="page-link" href="#">1</a></li>
			    <li class="page-item active"><a class="page-link" href="#">2</a></li>
			    <li class="page-item"><a class="page-link" href="#">3</a></li>
			
			    <!-- Botón Siguiente -->
			    <li class="page-item disabled">
			      <a class="page-link" href="#">Siguiente</a>
			    </li>
			
			  </ul>
			</nav>
	</div>
</div>

</body>
</html>

