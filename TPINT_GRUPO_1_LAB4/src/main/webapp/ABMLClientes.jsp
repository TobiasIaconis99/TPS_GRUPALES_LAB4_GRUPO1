<%@page import="java.util.List"%>
<%@page import="entidad.Cliente"%>
<%@page import="entidad.Provincia"%>
<%@page import="entidad.Localidad"%>
<%@page import="entidad.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Clientes</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body style="margin: 0; padding: 0;">

	<%@ include file="includes/NavbarAdmin.jsp" %>

	<div class="d-flex">
	
		<%@ include file="includes/SidebarAdmin.jsp" %>

		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4>Clientes</h4>
			<hr />
			<br />
			<div class="row mb-3 align-items-end">
				<div class="col-md-9">
					<form method="get" action="ServletCliente" class="row gx-2" id="formFiltros">
						<input type="hidden" name="accion" value="listar">
				
						<div class="col-md-4">
							<input type="text" name="busqueda" class="form-control" placeholder="Buscar por DNI o Nombre y Apellido"
								value="<%= request.getParameter("busqueda") != null ? request.getParameter("busqueda") : "" %>">
						</div>
				
						<div class="col-md-3">
							<select name="filtroSexo" class="form-select">
								<option value="">Todos los sexos</option>
								<option value="M" <%= "M".equals(request.getParameter("filtroSexo")) ? "selected" : "" %>>Masculino</option>
								<option value="F" <%= "F".equals(request.getParameter("filtroSexo")) ? "selected" : "" %>>Femenino</option>
								<option value="O" <%= "O".equals(request.getParameter("filtroSexo")) ? "selected" : "" %>>Otro</option>
							</select>
						</div>
				
						<div class="col-md-3 d-flex"> 
						    <button class="btn btn-primary w-50 me-1" type="submit"> 
						        <i class="bi bi-search me-1"></i> Buscar
						    </button>
						    <button class="btn btn-secondary w-50" type="button" onclick="limpiarFiltros()">
						        <i class="bi bi-funnel me-1"></i> Limpiar
						    </button>
						</div>
					</form>
				</div>
				<!-- Botón de nuevo cliente -->
				<div class="col-md-3 d-flex justify-content-end">
					<a href="ServletCliente?accion=formularioAgregarCliente" class="btn btn-primary">
						<i class="bi bi-plus-circle me-1"></i> Nuevo cliente
					</a>
				</div>
			</div>

			<%
				// Mensaje exito
                String mensajeExito = (String) session.getAttribute("mensajeExito");
                if (mensajeExito != null) {
                    session.removeAttribute("mensajeExito");
            %>
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                    	<i class="bi bi-check-circle me-1"></i>
                        <%= mensajeExito %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
            <%
                }
                // Mensaje error
                String mensajeError = (String) session.getAttribute("mensajeError");
                if (mensajeError != null) {
                    session.removeAttribute("mensajeError");
            %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    	<i class="bi bi-x-circle me-1"></i>
                        <%= mensajeError %>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
            <%
                }
            %>
			<%
				List<Cliente> listaClientes = (List<Cliente>) request.getAttribute("listaClientes");
			%>
			
			<table class="table table-bordered">
				<thead class="table-primary">
					<tr>
						<th>DNI</th>
						<th>CUIL</th>
						<th>Nombre y Apellido</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<%
					// En la BD el sexo esta como M, F y O y para el modal quiero que muestre los nombres.
					if (listaClientes != null) {
						for (Cliente c : listaClientes) {
							String sexo = "";
							if (c.getSexo() != null) {
								switch (c.getSexo().toUpperCase()) {
								case "M":
									sexo = "Masculino";
									break;
								case "F":
									sexo = "Femenino";
									break;
								case "O":
									sexo = "Otro";
									break;
								default:
									sexo = "No especificado";
									break;
								}
							}
					%>
					<tr>
						<td><%= c.getDni() %></td>
						<td><%= c.getCuil() %></td>
						<td><%= c.getNombre() %> <%= c.getApellido() %></td>
						<td> 
							<button class="btn btn-sm btn-primary"
								data-bs-toggle="modal"
								data-bs-target="#modalVerCliente"
								onclick="verClienteDetalles(
									'<%= c.getDni() %>', 
									'<%= c.getCuil() %>', 
									'<%= sexo %>',
									'<%= c.getNombre() %>', 
									'<%= c.getApellido() %>', 
									'<%= c.getNacionalidad() %>', 
									'<%= c.getFechaNacimiento() %>', 
									'<%= c.getDireccion() %>', 
									'<%= c.getLocalidad().getProvincia().getNombreProvincia() %>',
									'<%= c.getLocalidad().getNombreLocalidad() %>',
									'<%= c.getTelefono() %>', 
									'<%= c.getCorreo() %>', 
									'<%= c.getUsuario().getNombreUsuario() %>', 
									'<%= c.getUsuario().getClave() %>'
								)">
								<i class="bi bi-eye"></i>
							</button>
							<a href="ServletCliente?accion=formularioModificarCliente&dni=<%= c.getDni() %>" class="btn btn-sm btn-success">
							    <i class="bi bi-pencil-square"></i>
							</a>
							<button class="btn btn-sm btn-danger"
								data-bs-toggle="modal"
								data-bs-target="#modalConfirmacionEliminar"
								onclick="setDniClienteAEliminar('<%= c.getDni() %>')">
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
			    <li class="page-item <%= (paginaActual == 1) ? "disabled" : "" %>">
			      <a class="page-link" href="ServletCliente?accion=listar&pagina=<%= paginaActual - 1 %>&busqueda=<%= busqueda %>&filtroSexo=<%= filtroSexo %>">
			        Anterior
			      </a>
			    </li>
			
			    <!-- Números de página -->
			    <% for (int i = 1; i <= totalPaginas; i++) { %>
			      <li class="page-item <%= (i == paginaActual) ? "active" : "" %>">
			        <a class="page-link" href="ServletCliente?accion=listar&pagina=<%= i %>&busqueda=<%= busqueda %>&filtroSexo=<%= filtroSexo %>">
			          <%= i %>
			        </a>
			      </li>
			    <% } %>
			
			    <!-- Botón Siguiente -->
			    <li class="page-item <%= (paginaActual == totalPaginas) ? "disabled" : "" %>">
			      <a class="page-link" href="ServletCliente?accion=listar&pagina=<%= paginaActual + 1 %>&busqueda=<%= busqueda %>&filtroSexo=<%= filtroSexo %>">
			        Siguiente
			      </a>
			    </li>
			
			  </ul>
			</nav>
			<!-- Fin de paginacion -->
			
		</div>
	</div>
	
	<script>
	 	// Variable para almacenar el DNI del cliente a eliminar
		let dniClienteAEliminar = "";
		// Función para almacenar el DNI del cliente a eliminar
		function setDniClienteAEliminar(dni) {
			dniClienteAEliminar = dni;
		}
		// Función para confirmar la eliminación del cliente
		function confirmarEliminarCliente() {
			// Crea un formulario dinámicamente
			const form = document.createElement('form');
			form.method = 'POST'; // Define el método como post
			form.action = 'ServletCliente'; // Apunta al servlet

			// Crea un input oculto para la acción
			const accionInput = document.createElement('input');
			accionInput.type = 'hidden';
			accionInput.name = 'accion';
			accionInput.value = 'eliminar'; // La acción que se espera en el doPost
			form.appendChild(accionInput);

			// Crea un input oculto para el DNI del cliente
			const dniInput = document.createElement('input');
			dniInput.type = 'hidden';
			dniInput.name = 'dni';
			dniInput.value = dniClienteAEliminar; // El DNI a eliminar
			form.appendChild(dniInput);

			// Agrega el formulario al body del documento y lo envía
			document.body.appendChild(form); 
			form.submit(); // Esto envía la petición al post
		}
		// Función para cargar los datos en el modal y ponerlos en modo solo lectura
		function verClienteDetalles(dni, cuil, sexo, nombre, apellido, nacionalidad, fechaNacimiento, direccion, nombreProvincia, nombreLocalidad, telefono, correo, usuario, clave) {
            document.getElementById("dni").value = dni;
            document.getElementById("cuil").value = cuil;
            document.getElementById("sexo").value = sexo;
            document.getElementById("nombre").value = nombre;
            document.getElementById("apellido").value = apellido;
            document.getElementById("nacionalidad").value = nacionalidad;
            document.getElementById("fechaNacimiento").value = fechaNacimiento;
            document.getElementById("direccion").value = direccion;
            document.getElementById("provincia").value = nombreProvincia;
            document.getElementById("localidad").value = nombreLocalidad;
            document.getElementById("telefono").value = telefono;
            document.getElementById("email").value = correo;
            document.getElementById("usuario").value = usuario;
            document.getElementById("contrasenia").value = clave;
		}
		
		// Funcion para limpiar filtros
		function limpiarFiltros() {
		    // Obtener el formulario por su ID
		    const form = document.getElementById('formFiltros');
		    // Restablecer el valor del campo de búsqueda a vacío
		    form.elements['busqueda'].value = '';
		    // Restablecer la selección del filtro de sexo a la primera opción (Todos los sexos)
		    form.elements['filtroSexo'].value = '';
		    // Enviar el formulario para aplicar los filtros "limpios" (volver a listar sin filtros)
		    form.submit();
		}
	</script>
	
	<div class="modal fade" id="modalVerCliente" tabindex="-1" aria-labelledby="modalVerClienteLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title fs-5" id="modalVerClienteLabel">
	        	Ver cliente
	        </h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
	      </div>
	      <div class="modal-body">
	        <form id="formNuevoCliente" action="" method="POST"> 
	          <input type="hidden" name="accion" id="form-action-hidden"> 
	
	          <div class="row mb-3">
	            <div class="col-md-4">
	              <label for="dni" class="form-label">DNI</label>
	              <input type="text" class="form-control" id="dni" name="dni" required disabled>
	            </div>
	            <div class="col-md-4">
	              <label for="cuil" class="form-label">CUIL</label>
	              <input type="text" class="form-control" id="cuil" name="cuil" required disabled>
	            </div>
	            <div class="col-md-4">
	              <label for="sexo" class="form-label">Sexo</label>
	              <input type="text" class="form-control" id="sexo" name="sexo" required disabled>
	            </div>
	          </div>
	
	          <div class="row mb-3">
	            <div class="col-md-6">
	              <label for="nombre" class="form-label">Nombre</label>
	              <input type="text" class="form-control" id="nombre" name="nombre" required disabled>
	            </div>
	            <div class="col-md-6">
	              <label for="apellido" class="form-label">Apellido</label>
	              <input type="text" class="form-control" id="apellido" name="apellido" required disabled>
	            </div>
	          </div>
	
	          <div class="row mb-3">
	            <div class="col-md-6">
	              <label for="nacionalidad" class="form-label">Nacionalidad</label>
	              <input type="text" class="form-control" id="nacionalidad" name="nacionalidad" required disabled>
	            </div>
	            <div class="col-md-6">
	              <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
	              <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required disabled>
	            </div>
	          </div>
	
	          <div class="mb-3">
	            <label for="direccion" class="form-label">Dirección</label>
	            <input type="text" class="form-control" id="direccion" name="direccion" required disabled>
	          </div>
	
	          <div class="row mb-3">
	            <div class="col-md-4">
	              <label for="provincia" class="form-label">Provincia</label>
	              <input type="text" class="form-control" id="provincia" name="provincia" required disabled>
	            </div>
	            <div class="col-md-4">
	              <label for="localidad" class="form-label">Localidad</label>
	              <input type="text" class="form-control" id="localidad" name="localidad" required disabled>
	            </div>
	            <div class="col-md-4">
	              <label for="telefono" class="form-label">Teléfono</label>
	              <input type="tel" class="form-control" id="telefono" name="telefono" required disabled>
	            </div>
	          </div>
	          
	          <div class="mb-3">
	            <label for="email" class="form-label">Correo electrónico</label>
	            <input type="email" class="form-control" id="email" name="email" required disabled>
	          </div>
	
	          <div class="row mb-3">
	          
	          	<div class="col-md-6">
	              <label for="usuario" class="form-label">Usuario</label>
	              <input type="text" class="form-control" id="usuario" name="usuario" required disabled>
	            </div>
	            
				<div class="col-md-6">
				  <label for="contrasenia" class="form-label">Clave</label>
				  <div class="input-group">
				    <input type="password" class="form-control" id="contrasenia" name="contrasenia" required disabled>
				    <span class="input-group-text" style="cursor: pointer; background-color: #e9ecef;" id="toggleClave">
				      <i class="bi bi-eye" id="iconoClave"></i>
				    </span>
				  </div>
				</div>
				
				<!-- Script para mostrar/ocultar la clave -->
				<script>
				  document.addEventListener('DOMContentLoaded', function () {
				    const inputClave = document.getElementById('contrasenia');
				    const toggleSpan = document.getElementById('toggleClave');
				    const icono = document.getElementById('iconoClave');
				
				    toggleSpan.addEventListener('click', function () {
				      const esPassword = inputClave.type === 'password';
				      inputClave.type = esPassword ? 'text' : 'password';
				      icono.classList.toggle('bi-eye', !esPassword);
				      icono.classList.toggle('bi-eye-slash', esPassword);
				    });
				  });
				</script>

	          </div>
	
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="modalConfirmacionEliminar" tabindex="-1" aria-labelledby="modalConfirmacionEliminarLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="modalConfirmacionEliminarLabel">Eliminar cliente</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        ¿Estás seguro de que deseas eliminar este cliente?
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
	        <button type="button" class="btn btn-danger" onclick="confirmarEliminarCliente()">Eliminar</button>
	      </div>
	    </div>
	  </div>
	</div>

</body>
</html>