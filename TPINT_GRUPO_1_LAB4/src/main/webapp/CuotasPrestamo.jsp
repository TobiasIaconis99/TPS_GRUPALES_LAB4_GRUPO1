<%@page import="entidad.Cuota"%>
<%@page import="negocioImpl.NegocioCuotaImpl"%>
<%@page import="negocio.NegocioCuota"%>
<%@page import="java.util.List"%>

<%
int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
NegocioCuota negocioCuota = new NegocioCuotaImpl();
List<Cuota> cuotas = negocioCuota.obtenerCuotasPorIdPrestamo(idPrestamo);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cuotas del Préstamo <%=idPrestamo%></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>

</head>
<body>

<%@ include file="includes/NavbarCliente.jsp"%>
	<h2>
		Cuotas del préstamo N°
		<%=idPrestamo%></h2>

	<table class="table table-bordered table-striped mt-3">
		<thead class="table-light">
			<tr>
				<th>N° Cuota</th>
				<th>Monto</th>
				<th>Fecha Vencimiento</th>
				<th>Fecha Pago</th>
				<th>Estado</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Cuota c : cuotas) {
			%>
			<tr>
				<td><%=c.getNumeroCuota()%></td>
				<td>$<%=c.getMonto()%></td>
				<td><%=c.getFechaPago()%></td>
				<td><%=c.getFechaPago() != null ? c.getFechaPago() : "<em>No pagada</em>"%></td>
				<td><span
					class="badge <%=c.getPagada() ? "bg-success" : "bg-warning text-dark"%>">
						<%=c.getPagada() ? "Pagada" : "Sin pagar"%>
				</span></td>
				<td>
					<button type="button" class="btn btn-primary btn-sm"
						data-bs-toggle="modal" data-bs-target="#modalPagarCuota">Pagar</button>
				</td>

			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<a href="PagoPrestamos.jsp" class="btn btn-secondary">Volver a
		préstamos</a>


	<div class="modal fade" id="modalPagarCuota" tabindex="-1"
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

					<div class="modal-body"></div>


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
</html>
