<%@page import="entidad.Cuota"%>
<%@page import="negocioImpl.NegocioCuotaImpl"%>
<%@page import="negocio.NegocioCuota"%>
<%@page import="java.util.List"%>
<%@page import="entidad.Cliente"%>
<%@page import="negocio.CuentaNegocio"%>
<%@page import="entidad.Cuenta"%>
<%@page import="negocioImpl.CuentaNegocioImpl"%>

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
	// Mensaje error
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
						<%=c.getPagada() ? "disabled" : ""%>
						onclick="guardarMontoCuota(<%=c.getMonto()%>, <%=c.getIdCuota()%>)"
						data-bs-toggle="modal" data-bs-target="#modalPagarCuota">
						Pagar</button>
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
			<div class="modal-content">
				<form action="ServletPagarCuota" method="post">
					<input type="hidden" name="accion" value="modificar">

					<div class="modal-header">
						<i class="bi bi-pencil-square fs-5 me-1"></i>
						<h5 class="modal-title" id="modalEditarUsuarioLabel">Pagar
							cuota</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Cerrar"></button>
					</div>

					<div class="modal-body">
						<div class="mb-3">
							<label for="cuentaDestino" class="form-label">Seleccione
								una cuenta para pagar la cuota</label>
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
								name="cuentaDestino" required
								onchange="cuentaSeleccionada(this)">
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

					</div>


					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" disabled="disabled"
							data-bs-toggle="tooltip" data-bs-placement="top"
							id="btnPagarCuota" title="Guardar los cambios">Pagar</button>
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal" data-bs-toggle="tooltip"
							data-bs-placement="top" title="Cancelar y cerrar">Cancelar</button>
						<input id="idPrestamoHidden" type="hidden" name="idPrestamoHidden">
						<input id="idMontoCuota" type="hidden" name="idMontoCuota">
						<input id="idCuota" type="hidden" name="idCuotaHidden"> <input
							id="idCuenta" type="hidden" name="idCuentaHidden"> <input
							id="idCuenta" type="hidden" name="idPrestamo">
					</div>
				</form>
			</div>
		</div>
	</div>


	<script>
	function guardarMontoCuota(montoCuota, idCuota){
		document.getElementById('idMontoCuota').value = montoCuota;
		document.getElementById('idCuota').value = idCuota;
		
		
		const params = new URLSearchParams(window.location.search);
		const idPrestamo = params.get("idPrestamo");
		
		document.getElementById("idPrestamoHidden").value = idPrestamo;
		console.log("idPres:" + document.getElementById("idPrestamoHidden").value)
		
		
		
		console.log("Monto cuota guardado:", montoCuota);
		console.log("id cuota guardado:", idCuota);
		
	}

	function cuentaSeleccionada(selectElement) {
		

		const selectedOption = selectElement.options[selectElement.selectedIndex];
		const texto = selectedOption.textContent.trim(); 

		const partes = texto.split('$');
		if (partes.length > 1) {
			const saldo = parseFloat(partes[1].trim());

			const montoCuota = parseFloat(document.getElementById('idMontoCuota').value);
			

			const btnPagar = document.getElementById('btnPagarCuota');

			if (!isNaN(saldo) && !isNaN(montoCuota) && saldo >= montoCuota) {
				btnPagar.disabled = false;
			} else {
				btnPagar.disabled = true;
			}
		} 
	}
</script>




</body>



</html>
