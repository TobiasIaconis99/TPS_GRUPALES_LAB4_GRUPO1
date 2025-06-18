<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Cuentas</title>

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
			<h4>Cuentas</h4>
			<hr />
			<br />
			<!-- Encabezado -->
			<div class="row mb-3">
				<div class="col-md-6">
					<!-- espacio para futuros filtros u otros elementos -->
				</div>
				<div class="col-md-6 text-end">
					<button class="btn btn-primary">
						Nueva usuario
					</button>
				</div>
			</div>

			<!-- Tabla de clientes -->
    <table class="table table-bordered">
        <thead class="table-light">
            <tr>
                <th>Usuario</th>
                <th>Contraseña</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>janpe</td>
                <td>.....</td>
                <td>
					<button class="btn btn-sm btn-secondary">
						<i class="bi bi-pencil-square me-1"></i> Editar
					</button>
					<button class="btn btn-sm btn-danger">
						<i class="bi bi-trash me-1"></i> Eliminar
					</button>
				</td>
            </tr>
            <tr>
                <td>margo</td>
                <td>.....</td>
                <td>
					<button class="btn btn-sm btn-secondary">
						<i class="bi bi-pencil-square me-1"></i> Editar
					</button>
					<button class="btn btn-sm btn-danger">
						<i class="bi bi-trash me-1"></i> Eliminar
					</button>
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
	</div>

</body>
</html>