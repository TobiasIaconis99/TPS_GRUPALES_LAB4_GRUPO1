<%@page import="dao.ClienteDao"%>
<%@page import="daoImpl.ClienteDaoImpl"%>
<%@page import="daoImpl.CuentaDaoImpl"%>
<%@page import="negocioImpl.TipoCuentaNegocioImpl"%>
<%@page import="dao.CuentaDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entidad.Cuenta"%>
<%@ page import="entidad.Cliente"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Cuentas</title>
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

	<%@ include file="includes/NavbarAdmin.jsp"%>
	<div class="d-flex">
		<%@ include file="includes/SidebarAdmin.jsp"%>

		<div class="flex-grow-1 p-4" style="margin-left: 250px;">
			<h4>Cuentas</h4>
			<hr />

			<div class="text-end mb-3">
				<button class="btn btn-primary" data-bs-toggle="modal"
					data-bs-target="#modalAgregarCuenta">
					<i class="bi bi-plus-circle me-1"></i> Nueva cuenta
				</button>
			</div>

			<%
			// Mensaje exito para Cuentas
			String mensajeExitoCuenta = (String) session.getAttribute("mensajeExitoCuenta");
			if (mensajeExitoCuenta != null) {
				session.removeAttribute("mensajeExitoCuenta");
			%>
			<div class="alert alert-success alert-dismissible fade show"
				role="alert">
				<i class="bi bi-check-circle me-1"></i>
				<%=mensajeExitoCuenta%>
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<%
			}
			// Mensaje error para Cuentas
			String mensajeErrorCuenta = (String) session.getAttribute("mensajeErrorCuenta");
			if (mensajeErrorCuenta != null) {
			session.removeAttribute("mensajeErrorCuenta");
			%>
			<div class="alert alert-danger alert-dismissible fade show"
				role="alert">
				<i class="bi bi-x-circle me-1"></i>
				<%=mensajeErrorCuenta%>
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<%
			}
			%>

			<!-- Tabla -->
			<table class="table table-bordered">
				<thead class="table-light">
					<tr>
						<th>N° Cuenta</th>
						<th>Cliente</th>
						<th>Fecha creación</th>
						<th>Tipo</th>
						<th>CBU</th>
						<th>Saldo</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
					CuentaDaoImpl cuentas = new CuentaDaoImpl();
					List<Cuenta> listaCuentas = cuentas.listarCuentasActivas();
					//(List<Cuenta>) request.getAttribute("listaCuentas");
					if (listaCuentas != null) {
						for (Cuenta c : listaCuentas) {
					%>
					<tr>
						<td><%=c.getNumeroCuenta()%></td>
						<td><%=c.getCliente().getNombre()%> <%=c.getCliente().getApellido()%></td>
						<td><%=c.getFechaCreacion()%></td>
						<td><%=c.getTipoCuenta().getNombreTipoCuenta()%></td>
						<td><%=c.getCbu()%></td>
						<td>$<%=c.getSaldo()%></td>
						<td>
							<!-- Editar / Eliminar si se desea -->

							<button class="btn btn-sm btn-success"
								data-bs-target="#modalEditarCuenta"
								onclick="setCuentaAModificar('<%=c.getIdCuenta()%>')"
								data-bs-toggle="modal">
								<i class="bi bi-pencil-square"></i>
							</button>
							<button class="btn btn-sm btn-danger" data-bs-toggle="modal"
								data-bs-target="#modalConfirmacionEliminarCuenta"
								onclick="setCuentaAEliminar('<%=c.getIdCuenta()%>')">
								<i class="bi bi-trash"></i>
							</button>

						</td>
					</tr>
					<%
					}
					}
					%>
				</tbody>
			</table>
		</div>
	</div>

	<script>
		// Variable para almacenar el id de la cuenta a eliminar
		let idCuentaAEliminar = "";
		// Función para almacenar el ID de la cuenta a eliminar
		function setCuentaAEliminar(id) {
			idCuentaAEliminar = id;
		}
		// Función para confirmar la eliminación del cliente
		function confirmarEliminarCuenta() {
			// Crea un formulario dinámicamente
			const form = document.createElement('form');
			form.method = 'POST'; // Define el método como post
			form.action = 'ServletCuenta'; // Apunta al servlet

			// Crea un input oculto para la acción
			const accionInput = document.createElement('input');
			accionInput.type = 'hidden';
			accionInput.name = 'accion';
			accionInput.value = 'eliminar'; // La acción que se espera en el doPost
			form.appendChild(accionInput);

			// Crea un input oculto para el DNI del cliente
			const idCuentaInput = document.createElement('input');
			idCuentaInput.type = 'hidden';
			idCuentaInput.name = 'idCuenta';
			idCuentaInput.value = idCuentaAEliminar; // El DNI a eliminar
			form.appendChild(idCuentaInput);

			// Agrega el formulario al body del documento y lo envía
			document.body.appendChild(form);
			form.submit(); // Esto envía la petición al post
		}

		function setCuentaAModificar(idCuenta) {
			document.getElementById('hiddenIdCuenta').value = idCuenta;
			console.log(idCuenta)
		}
	</script>

	<!-- Modal Agregar Cuenta -->
	<div class="modal fade" id="modalAgregarCuenta" tabindex="-1"
		aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<form class="modal-content" method="post" action="ServletCuenta">
				<div class="modal-header">
					<h5 class="modal-title" id="modalLabel">Agregar nueva cuenta</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="accion" value="agregar" />

					<div class="mb-3">
						<label for="dniCliente" class="form-label">Cliente</label> <select
							class="form-select" name="dniCliente" required>
							<option value="">Seleccionar...</option>
							<%
							ClienteDao cliente = new ClienteDaoImpl();
							List<Cliente> listaClientes = cliente.listar();
							if (listaClientes != null) {
								for (Cliente cl : listaClientes) {
							%>
							<option value="<%=cl.getDni()%>"><%=cl.getNombre()%>
								<%=cl.getApellido()%></option>
							<%
							}
							}
							%>
						</select>
					</div>
					<div class="mb-3">
						<label for="tipoCuenta" class="form-label">Tipo de cuenta</label>
						<select class="form-select" name="tipoCuenta" required>
							<option value="">Seleccionar...</option>
							<option value="1">Caja de ahorro</option>
							<option value="2">Cuenta corriente</option>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Agregar</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancelar</button>
				</div>
			</form>
		</div>
	</div>

	<!-- Modal Editar Cuenta -->
	<div class="modal fade" id="modalEditarCuenta" tabindex="-1"
		aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<form class="modal-content" method="post" action="ServletCuenta">
				<div class="modal-header">
					<h5 class="modal-title" id="modalLabel">Modificar cuenta</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="accion" value="editar" />

					<div class="mb-3">
						<label for="dniCliente" class="form-label">Cliente</label> <select
							class="form-select" name="dniCliente" required>
							<option value="">Seleccionar...</option>
							<%
							ClienteDao clienteEditar = new ClienteDaoImpl();
							List<Cliente> listaClientesEditar = cliente.listar();
							if (listaClientes != null) {
								for (Cliente cl : listaClientesEditar) {
							%>
							<option value="<%=cl.getDni()%>"><%=cl.getNombre()%>
								<%=cl.getApellido()%></option>
							<%
							}
							}
							%>
						</select>
					</div>
					<div class="mb-3">
						<label for="tipoCuenta" class="form-label">Tipo de cuenta</label>
						<select class="form-select" name="tipoCuenta" required>
							<option value="">Seleccionar...</option>
							<option value="1">Caja de ahorro</option>
							<option value="2">Cuenta corriente</option>
						</select>
					</div>
					<div class="mb-3">
						<label for="lblSaldo" class="form-label">Saldo</label>
						<input type="number" value="0" name="saldo" id="inputSaldo">
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Modificar</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancelar</button>
					<input type="hidden" name="idCuenta" id="hiddenIdCuenta" />
				</div>
			</form>
		</div>
	</div>

	<!-- Modal Eliminar Cuenta -->
	<div class="modal fade" id="modalConfirmacionEliminarCuenta"
		tabindex="-1" aria-labelledby="modalConfirmacionEliminarLabel"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modalConfirmacionEliminarLabel">Eliminar
						Cuenta</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">¿Estás seguro de que deseas eliminar
					esta cuenta?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-danger"
						onclick="confirmarEliminarCuenta()">Eliminar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
