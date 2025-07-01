<%@page import="java.lang.ProcessHandle.Info"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<%@ page import="java.util.List" %>
		<%@ page import="java.math.BigDecimal" %>
		<%@ page import="InformesDTO.InformeAdto" %> 
		<%@ page import="java.util.Calendar" %> 

<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Inicio</title>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>

	<%@ include file="includes/NavbarAdmin.jsp" %>

	<div class="d-flex">
		<%@ include file="includes/SidebarAdmin.jsp" %>

		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4>Inicio</h4>
			<hr>
			<br>

			<div class="card mb-4 shadow-sm">
				<div class="card-header bg-secondary text-white d-flex align-items-center">
					<i class="bi bi-funnel-fill me-2"></i> Filtros de Informe
				</div>
				<div class="card-body">
					<form action="ServletInformeA" method="get" class="row g-3 align-items-end">
						<div class="col-md-4">
							<label for="mes" class="form-label">Mes:</label>
							<select class="form-select" id="mes" name="mes" required>
								<% 
									// Obtener el mes actual
									int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Calendar.MONTH es 0-11
									String selectedMonth = request.getParameter("mes");
									if (selectedMonth == null || selectedMonth.isEmpty()) {
										selectedMonth = String.valueOf(currentMonth); // Si no hay filtro, usar el mes actual
									}
								%>
								<option value="1" <%= "1".equals(selectedMonth) ? "selected" : "" %>>Enero</option>
								<option value="2" <%= "2".equals(selectedMonth) ? "selected" : "" %>>Febrero</option>
								<option value="3" <%= "3".equals(selectedMonth) ? "selected" : "" %>>Marzo</option>
								<option value="4" <%= "4".equals(selectedMonth) ? "selected" : "" %>>Abril</option>
								<option value="5" <%= "5".equals(selectedMonth) ? "selected" : "" %>>Mayo</option>
								<option value="6" <%= "6".equals(selectedMonth) ? "selected" : "" %>>Junio</option>
								<option value="7" <%= "7".equals(selectedMonth) ? "selected" : "" %>>Julio</option>
								<option value="8" <%= "8".equals(selectedMonth) ? "selected" : "" %>>Agosto</option>
								<option value="9" <%= "9".equals(selectedMonth) ? "selected" : "" %>>Septiembre</option>
								<option value="10" <%= "10".equals(selectedMonth) ? "selected" : "" %>>Octubre</option>
								<option value="11" <%= "11".equals(selectedMonth) ? "selected" : "" %>>Noviembre</option>
								<option value="12" <%= "12".equals(selectedMonth) ? "selected" : "" %>>Diciembre</option>
							</select>
						</div>
						<div class="col-md-4">
							<label for="anio" class="form-label">Año:</label>
							<select class="form-select" id="anio" name="anio" required>
								<% 
									int currentYear = Calendar.getInstance().get(Calendar.YEAR);
									String selectedYear = request.getParameter("anio");
									if (selectedYear == null || selectedYear.isEmpty()) {
										selectedYear = String.valueOf(currentYear); // Si no hay filtro, usar el año actual
									}
									for (int i = currentYear + 1; i >= currentYear - 5; i--) { 
								%>
									<option value="<%= i %>" <%= String.valueOf(i).equals(selectedYear) ? "selected" : "" %>><%= i %></option>
								<%
									}
								%>
							</select>
						</div>
						<div class="col-md-4">
							<button type="submit" class="btn btn-primary">
								<i class="bi bi-funnel-fill me-2"></i> Aplicar Filtros
							</button>
							<a href="ServletInformeA" class="btn btn-secondary">
								<i class="bi bi-x-circle me-2"></i> Limpiar Filtros
							</a>
						</div>
					</form>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="card mb-4 shadow-sm">
						<div class="card-header bg-primary text-white d-flex align-items-center">
							<i class="bi bi-bar-chart-line-fill me-2"></i> Resumen de Ingresos y Egresos de Cuentas
						</div>
						<div class="card-body">
							<div class="card-title mb-3">
								Los ingresos son todos los movimientos que aumentan el saldo de una cuenta. 
								Y los egresos son los movimientos que disminuyen el saldo.
							</div>
							
							<hr>

							<h5 class="mt-4 mb-3">Detalle de Ingresos</h5>
							<table class="table table-bordered table-hover">
								<thead class="table-primary">
									<tr>
										<th>Tipo de Movimiento</th>
										<th>Cantidad</th>
										<th>Importe Total</th> 
									</tr>
								</thead>
								<tbody>
									<%
										List<InformeAdto> lista = (List<InformeAdto>) request.getAttribute("estadisticas");
										if (lista != null && !lista.isEmpty()) { 
											for (InformeAdto e : lista) {
									%>
												<tr>
													<td><%= e.getTipoMovimiento() %></td>
													<td><%= e.getCantidad() %></td>
													<td>$<%= e.getTotal() %></td>
												</tr>
									<%
											}
										} else {
									%>
											<tr>
												<td colspan="3" class="text-center text-muted">No hay datos de ingresos disponibles.</td>
											</tr>
									<%
										}
									%>
								</tbody>
							</table>
							<%
								BigDecimal totalIngresos = BigDecimal.ZERO;
								if (lista != null) {
									for (InformeAdto e : lista) {
										totalIngresos = totalIngresos.add(e.getTotal());
									}
								}
							%>
							<p class="text-end me-2"><strong>Total Ingresos:</strong> <span class="text-success fw-bold">$<%= totalIngresos %></span></p>
							
							<hr>

							<h5 class="mt-4 mb-3">Detalle de Egresos</h5>
							<table class="table table-bordered table-hover">
								<thead class="table-primary">
									<tr>
										<th>Tipo de Movimiento</th>
										<th>Cantidad</th>
										<th>Importe Total</th> 
									</tr>
								</thead>
								<tbody>
									<%
										List<InformeAdto> listaegresos = (List<InformeAdto>) request.getAttribute("egresos");
										if (listaegresos != null && !listaegresos.isEmpty()) { 
											for (InformeAdto e : listaegresos) {
									%>
												<tr>
													<td><%= e.getTipoMovimiento() %></td>
													<td><%= e.getCantidad() %></td>
													<td>$<%= e.getTotal() %></td>
												</tr>
									<%
											}
										} else {
									%>
											<tr>
												<td colspan="3" class="text-center text-muted">No hay datos de egresos disponibles.</td>
											</tr>
									<%
										}
									%>
								</tbody>
							</table>
							<%
								BigDecimal totalEgresos = BigDecimal.ZERO;
								if (listaegresos != null) {
									for (InformeAdto e : listaegresos) {
										totalEgresos = totalEgresos.add(e.getTotal());
									}
								}
							%>
							<p class="text-end me-2"><strong>Total Egresos:</strong> <span class="text-danger fw-bold">$<%= totalEgresos %></span></p>

							<hr>

							<%
								BigDecimal balance = totalIngresos.subtract(totalEgresos);
								String claseBalance = balance.compareTo(BigDecimal.ZERO) >= 0 ? "alert-success" : "alert-danger";
							%>
							<div class="alert <%= claseBalance %> mt-4 text-center fw-bold">
								<strong>Balance (Ingresos - Egresos):</strong> $<%= balance %>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="card mb-4 shadow-sm">
						<div class="card-header bg-primary text-white d-flex align-items-center">
							<i class="bi bi-bar-chart-line-fill me-2"></i> Informe B
						</div>
						<div class="card-body">
							<h5 class="card-title mb-3">Contenido de Informe B</h5>
							<p class="text-muted">Aquí puedes añadir el contenido específico para el Informe B, sin gráficos.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>