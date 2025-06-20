<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Login</title>

	<!-- Bootstrap 5.3 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- Bootstrap Icons -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
	<!-- Bootstrap Bundle JS -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<style>
		.login-form {
			width: 340px;
		}
	</style>
</head>

<body class="d-flex align-items-center justify-content-center" style="min-height: 100vh; margin:0;">
	<div class="login-form">
		<form method="post" action="ServletLogin" class="p-4 border rounded bg-primary text-white shadow-sm">
			<h2 class="text-center mb-4 text-white">
			<i class="bi bi-bank me-2 fs-1"></i>
			<br>
			BANCO
			</h2>
		
			<%
				String errorLogin = (String) request.getAttribute("errorLogin");
				if (errorLogin != null) {
			%>
			<div class="alert alert-danger text-center" role="alert">
				<%= errorLogin %>
			</div>
			<% } %>
		
			<div class="mb-3">
				<input type="text" class="form-control" name="txtUsuario" placeholder="Usuario" required autofocus>
			</div>
		
			<div class="mb-3">
				<input type="password" class="form-control" name="txtClave" placeholder="Clave" required>
			</div>
		
			<div class="d-grid">
				<button type="submit" class="btn btn-light" name="btnIngresar">Ingresar</button>
			</div>
		</form>
	</div>
</body>
</html>
