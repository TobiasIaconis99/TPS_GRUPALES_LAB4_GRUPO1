<%@page import="negocioImpl.NegocioCuotaImpl"%>
<%@page import="negocio.NegocioCuota"%>
<%@page import="negocioImpl.PrestamoNegocioImpl"%>
<%@page import="negocio.PrestamoNegocio"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="entidad.Prestamo"%>
<%@page import="java.util.List"%>
<%@page import="entidad.Cuota"%>
<%@page import="negocio.NegocioCuota"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
	<!-- Pagar cuotas -->
	<div class="card">
		<div class="card-header bg-primary text-white">Pago de cuotas
			pendientes</div>
		<%
		PrestamoNegocio pNegocio = new PrestamoNegocioImpl();
		List<Prestamo> prestamos = pNegocio.listar();
		int nroCuota = 1;
		%>

		<div class="card-body">

			<h6>Listado de Préstamos</h6>

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
					<%
					for (Prestamo p : prestamos) {
						// Simulamos fechas, porque el modelo Prestamo aún no tiene campo de vencimiento ni pago
						String fechaVencimiento = "2025-07-01"; // deberías calcular esto según lógica
						String fechaPago = ""; // vacío si no se pagó

						boolean pagado = false; // deberías obtenerlo desde la base de datos o tu modelo

						// Mostrar la fila con estilo distinto si está pagada
						String rowClass = pagado ? "table-success" : "";
					%>
					<tr class="<%=rowClass%>">
						<td><%=nroCuota++%></td>
						<td>$<%=p.getMontoCuota()%></td>
						<td><%=fechaVencimiento%></td>
						<td>
							<%
							if (pagado) {
							%> <%=fechaPago%> <%
 } else {
 %> <em>No pagada</em> <%
 }
 %>
						</td>
						<td>
							<%
							if (pagado) {
							%> <i class="bi bi-check-circle-fill text-success"></i> Pagada <%
 } else {
 %>
							<form method="post" action="PagarCuotaServlet"
								class="d-flex align-items-center">

								<input type="hidden" name="idPrestamo"
									value="<%=p.getIdPrestamo()%>" />
								<button type="button" data-bs-toggle="modal"
									onclick="guardarPrestamoId(<%=p.getIdPrestamo()%>)"
									data-bs-target="#modalVerCuotas" class="btn btn-sm btn-primary">
									Ver cuotas</button>

								<input id="idPrestamoHidden" type="hidden">
							</form> <%
 }
 %>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

	<div class="modal fade" id="modalVerCuotas" tabindex="-1"
		aria-labelledby="modalEditarUsuarioLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<!-- ampliamos el tamaño -->
			<div class="modal-content">
				<form action="<%=request.getContextPath()%>/ServletUsuario"
					method="post">
					<input type="hidden" name="accion" value="modificar">

					<div class="modal-header">
						<i class="bi bi-pencil-square fs-5 me-1"></i>
						<h5 class="modal-title" id="modalEditarUsuarioLabel">Préstamo
							- Cuotas</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Cerrar"></button>
					</div>

					<div class="modal-body">
						<!-- Tabla de cuotas -->
						<%
						NegocioCuota negocioCuota = new NegocioCuotaImpl();
						List<Cuota> cuotas = negocioCuota.obtenerCuotasPorIdPrestamo(1);
						%>
						<table class="table table-bordered table-striped">
							<thead class="table-light">
								<tr>
									<th>Cuota</th>
									<th>Importe</th>
									<th>Fecha de vencimiento</th>
									<th>Fecha de pago</th>
									<th>Estado</th>
								</tr>
							</thead>
							<tbody>
								<!-- Acá deberías recorrer tu lista de cuotas con JSTL o scriptlet -->
								<tr>
									<td>1</td>
									<td>$5.500</td>
									<td>2025-07-01</td>
									<td><em>No pagada</em></td>
									<td><span class="badge bg-warning text-dark">Pendiente</span></td>
								</tr>
								<tr>
									<td>2</td>
									<td>$5.500</td>
									<td>2025-08-01</td>
									<td><em>No pagada</em></td>
									<td><span class="badge bg-warning text-dark">Pendiente</span></td>
								</tr>
								<tr class="table-success">
									<td>3</td>
									<td>$5.500</td>
									<td>2025-06-01</td>
									<td>2025-06-05</td>
									<td><span class="badge bg-success">Pagada</span></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							data-bs-toggle="tooltip" data-bs-placement="top"
							title="Guardar los cambios">Guardar</button>
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal" data-bs-toggle="tooltip"
							data-bs-placement="top" title="Cancelar y cerrar">Cancelar</button>
					</div>
				</form>
			</div>
		</div>
	</div>



</body>

<script>
	function guardarPrestamoId(idPrestamo){
		console.log("hola");
		document.getElementById('idPrestamoHidden').value = idPrestamo;
		
	}
</script>

</html>