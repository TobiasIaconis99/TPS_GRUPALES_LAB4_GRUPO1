<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Clientes</title>
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
			<span class="text-white mr-3">Juan Pérez</span>
			<form action="Login.jsp">
				<input type="submit" class="btn btn-danger" name="btnCerrarSesion" value="Cerrar sesión" />
			</form>
		</div>
	</div>
</nav>

<div class="container mt-5">
	<!-- Primer fila con el nombre de la pagina y el boton agregar -->
	<div class="row mb-3">
			<div class="col-md-6">
				<h4>Clientes</h4>
			</div>
			<div class="col-md-6 text-right">
				<button class="btn btn-primary">Agregar nuevo cliente</button>
			</div>
	</div>
	
    <!-- Tabla de clientes -->
    <table class="table table-bordered">
        <thead class="thead-light">
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

	<!-- Paginas -->
		<div class="d-flex justify-content-center">
			<nav>
				<ul class="pagination">
					<li class="page-item"><a class="page-link" href="#">Anterior</a></li>
					<li class="page-item"><a class="page-link" href="#">1</a></li>
					<li class="page-item"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#">Proximo</a></li>
				</ul>
			</nav>
		</div>
	</div>
</body>
</html>