<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Cuentas</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
</head>
<body>
	<!-- Barra de navegacion superior -->
	<nav class="navbar navbar-dark bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand text-white">Banco</a>
	
			<!-- Contenedor para usuario y botón -->
			<div class="d-flex align-items-center">
				<span class="text-white mr-3">Administrador</span>
				<form action="Login.jsp">
					<input type="submit" class="btn btn-danger" name="btnCerrarSesion" value="Cerrar sesión" />
				</form>
			</div>
		</div>
	</nav>
	
	<div class="container mt-5">
	
		<!-- Encabezado y botón agregar -->
		<div class="row mb-3">
			<div class="col-md-6">
				<h4>Cuentas</h4>
			</div>
			<div class="col-md-6 text-right">
				<button class="btn btn-primary">Agregar nueva cuenta</button>
			</div>
		</div>
	
		<!-- Tabla de cuentas -->
		<table class="table table-bordered">
			<thead class="thead-light">
				<tr>
					<th>N° Cuenta</th>
					<th>Cliente</th>
					<th>Fecha creación</th>
					<th>Tipo de cuenta</th>
					<th>CBU</th>
					<th>Saldo</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>10001</td>
					<td>Pablo Torres</td>
					<td>2024-10-12</td>
					<td>Caja de ahorro</td>
					<td>2850003100000010001</td>
					<td>$10.000</td>
					<td>
						<button class="btn btn-sm btn-secondary">Editar</button>
						<button class="btn btn-sm btn-danger">Eliminar</button>
					</td>
				</tr>
				<tr>
					<td>10002</td>
					<td>Laura Gómez</td>
					<td>2024-11-02</td>
					<td>Cuenta corriente</td>
					<td>2850003100000010002</td>
					<td>$12.500</td>
					<td>
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
					<li class="page-item"><a class="page-link" href="#">Próximo</a></li>
				</ul>
			</nav>
		</div>
	
	</div>
</body>
</html>