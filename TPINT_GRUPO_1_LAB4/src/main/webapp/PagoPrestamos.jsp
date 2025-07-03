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
	<%@ include file="includes/NavbarCliente.jsp"%>
	<!-- Pagar cuotas -->
	<div class="card">

		<%
		PrestamoNegocio pNegocio = new PrestamoNegocioImpl();
		NegocioCuota cNegocio = new NegocioCuotaImpl();
		List<Prestamo> prestamos = pNegocio.listar();
		%>



		<div class="card">


			<div class="card-body">
				<table class="table table-bordered table-striped">
					<thead class="table-light">
						<tr>
							<th hidden="hidden">ID Préstamo</th>
							<th hidden="hidden">ID Cliente</th>
							<th hidden="hidden">ID Cuenta</th>
							<th>Fecha Alta</th>
							<th>Monto Solicitado</th>
							<th>Plazo (Meses)</th>
							<th>Cantidad de Cuotas</th>
							<th>Monto por Cuota</th>
							<th>Estado</th>
							<th>Acciones</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Prestamo p : prestamos) {
						%>
						<tr>
							<td hidden="hidden"><%=p.getIdPrestamo()%></td>
							<td hidden="hidden"><%=p.getIdCliente()%></td>
							<td hidden="hidden"><%=p.getIdCuenta()%></td>
							<td><%=p.getFechaAlta()%></td>
							<td>$<%=p.getImportePedido()%></td>
							<td><%=p.getPlazoMeses()%></td>
							<td><%=p.getCantidadCuotas()%></td>
							<td>$<%=p.getMontoCuota()%></td>
							<td><%=p.getEstado()%></td>
							<td><a
								href="CuotasPrestamo.jsp?idPrestamo=<%=p.getIdPrestamo()%>"
								class="btn btn-primary"> Ver cuotas </a>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
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
								<%
								for (Cuota cuota : cuotas) {
								%>
								<tr>
									<td><%=cuota.getNumeroCuota()%></td>
									<td>$<%=cuota.getMonto()%></td>
									<td><%=cuota.getFechaPago()%></td>
									<td><%=cuota.getFechaPago() == null ? "<em>No pagada</em>" : cuota.getFechaPago()%>
									</td>
									<td>
										<%
										if (cuota.getPagada()) {
										%> <span class="badge bg-success">Pagada</span> <%
 } else {
 %> <span class="badge bg-warning text-dark">Pendiente</span> <%
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


					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							data-bs-toggle="tooltip" data-bs-placement="top"
							title="Guardar los cambios">Guardar</button>
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal" data-bs-toggle="tooltip"
							data-bs-placement="top" title="Cancelar y cerrar">Cancelar</button>
						<input id="idPrestamoHidden" type="hidden">
					</div>
				</form>
			</div>
		</div>
	</div>



</body>

<script>
	function guardarPrestamoId(idPrestamo) {
		console.log("ID préstamo:", idPrestamo);
		document.getElementById('idPrestamoHidden').value = idPrestamo;
	}
</script>

</html>