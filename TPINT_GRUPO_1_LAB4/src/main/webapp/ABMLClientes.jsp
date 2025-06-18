<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Clientes</title>

	<!-- Bootstrap 5.3 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />

	<!-- Bootstrap Icons -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />

	<!-- Bootstrap JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body style="margin: 0; padding: 0;">

	<!-- NAVBAR ADMIN -->
	<%@ include file="includes/NavbarAdmin.jsp" %>

	<div class="d-flex">
		<!-- SIDEBAR -->
		<%@ include file="includes/SidebarAdmin.jsp" %>

		<!-- Contenedor principal -->
		<div class="flex-grow-1" style="margin-left: 250px; padding: 20px;">
			<h4>Clientes</h4>
			<hr />
			<br />
			<div class="row mb-3">
				<div class="col-md-6">
					<!-- espacio para futuros filtros u otros elementos -->
				</div>
				<div class="col-md-6 text-end">
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
					  Nuevo cliente
					</button>
				</div>
			</div>

			<!-- Tabla de clientes -->
			<table class="table table-bordered">
				<thead class="table-light">
					<tr>
						<th>DNI</th>
						<th>Nombre</th>
						<th>Apellido</th>
						<th>Acciones</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>12345678</td>
						<td>Juan</td>
						<td>Pérez</td>
						<td>
							<button class="btn btn-sm btn-primary">Ver más</button>
							<button class="btn btn-sm btn-secondary">Editar</button>
							<button class="btn btn-sm btn-danger">Eliminar</button>
						</td>
					</tr>
					<tr>
						<td>23456789</td>
						<td>María</td>
						<td>Gómez</td>
						<td>
							<button class="btn btn-sm btn-primary">Ver más</button>
							<button class="btn btn-sm btn-secondary">Editar</button>
							<button class="btn btn-sm btn-danger">Eliminar</button>
						</td>
					</tr>
				</tbody>
			</table>

			<!-- Paginación -->
			<div class="d-flex justify-content-center">
				<nav>
					<ul class="pagination">
						<li class="page-item"><a class="page-link" href="#">Anterior</a></li>
						<li class="page-item"><a class="page-link" href="#">1</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">Siguiente</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
	
	
<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg"> <!-- más ancho para mayor comodidad -->
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Nuevo cliente</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        <form id="formNuevoCliente">
          <div class="row mb-3">
            <div class="col-md-4">
              <label for="dni" class="form-label">DNI</label>
              <input type="text" class="form-control" id="dni" name="dni" required>
            </div>
            <div class="col-md-4">
              <label for="cuil" class="form-label">CUIL</label>
              <input type="text" class="form-control" id="cuil" name="cuil" required>
            </div>
            <div class="col-md-4">
              <label for="sexo" class="form-label">Sexo</label>
              <select class="form-select" id="sexo" name="sexo" required>
                <option value="">Seleccione...</option>
                <option value="M">Masculino</option>
                <option value="F">Femenino</option>
                <option value="X">Otro</option>
              </select>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-md-6">
              <label for="nombre" class="form-label">Nombre</label>
              <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>
            <div class="col-md-6">
              <label for="apellido" class="form-label">Apellido</label>
              <input type="text" class="form-control" id="apellido" name="apellido" required>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-md-6">
              <label for="nacionalidad" class="form-label">Nacionalidad</label>
              <input type="text" class="form-control" id="nacionalidad" name="nacionalidad" required>
            </div>
            <div class="col-md-6">
              <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
              <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" required>
            </div>
          </div>

          <div class="mb-3">
            <label for="direccion" class="form-label">Dirección</label>
            <input type="text" class="form-control" id="direccion" name="direccion" required>
          </div>

          <div class="row mb-3">
            <div class="col-md-4">
              <label for="provincia" class="form-label">Provincia</label>
              <select class="form-select" id="provincia" name="provincia" required>
                <option value="">Seleccione...</option>
                <option value="Buenos Aires">Buenos Aires</option>
                <option value="CABA">Ciudad Autónoma de Buenos Aires</option>
                <option value="Córdoba">Córdoba</option>
                <option value="Santa Fe">Santa Fe</option>
                <!-- Agrega más provincias si es necesario -->
              </select>
            </div>
            <div class="col-md-4">
              <label for="localidad" class="form-label">Localidad</label>
              <select class="form-select" id="localidad" name="localidad" required>
                <option value="">Seleccione...</option>
                <!-- Se puede completar dinámicamente con JS si es necesario -->
              </select>
            </div>
            <div class="col-md-4">
              <label for="telefono" class="form-label">Teléfono</label>
              <input type="tel" class="form-control" id="telefono" name="telefono" required>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-md-6">
              <label for="email" class="form-label">Correo electrónico</label>
              <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="col-md-6">
              <label for="usuario" class="form-label">Usuario</label>
              <input type="text" class="form-control" id="usuario" name="usuario" required>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-md-6">
              <label for="contrasenia" class="form-label">Contraseña</label>
              <input type="password" class="form-control" id="contrasenia" name="contrasenia" required>
            </div>
            <div class="col-md-6">
              <label for="repetirContrasenia" class="form-label">Repetir contraseña</label>
              <input type="password" class="form-control" id="repetirContrasenia" name="repetirContrasenia" required>
            </div>
          </div>

        </form>
      </div>
      <div class="modal-footer">
        <button type="submit" form="formNuevoCliente" class="btn btn-primary">Guardar</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
      </div>
    </div>
  </div>
</div>


</body>
</html>
