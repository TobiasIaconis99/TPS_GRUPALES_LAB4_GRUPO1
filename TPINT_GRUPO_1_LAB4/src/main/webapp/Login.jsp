<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Login</title>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<style>
		.login-form {
			width: 340px;
		}
		/* Estilos para el campo de contraseña con el icono */
		.password-toggle-container {
			position: relative;
		}
		.password-toggle-icon {
			position: absolute;
			right: 10px;
			top: 50%;
			transform: translateY(-50%);
			cursor: pointer;
			color: #6c757d; /* Color Bootstrap text-secondary */
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
		
			<div class="mb-3 password-toggle-container">
				<input type="password" class="form-control" name="txtClave" id="txtClave" placeholder="Clave" required>
				<span class="password-toggle-icon" id="toggleClave">
					<i class="bi bi-eye-slash" id="iconoClave"></i>
				</span>
			</div>
		
			<div class="d-grid">
				<button type="submit" class="btn btn-light" name="btnIngresar">Ingresar</button>
			</div>
		</form>
	</div>
	
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			// Cambiamos 'contrasenia' a 'txtClave' para que coincida con el ID del input
			const inputClave = document.getElementById('txtClave');
			const toggleSpan = document.getElementById('toggleClave');
			const icono = document.getElementById('iconoClave');

			if (inputClave && toggleSpan && icono) { // Verificamos que los elementos existan
				toggleSpan.addEventListener('click', function() {
					const esPassword = inputClave.type === 'password';
					inputClave.type = esPassword ? 'text' : 'password';
					// Alternamos las clases para cambiar el icono
					icono.classList.toggle('bi-eye', !esPassword);
					icono.classList.toggle('bi-eye-slash', esPassword);
				});
			} else {
				console.error("No se encontraron los elementos del campo de clave o el icono.");
			}
		});
	</script>
</body>
</html>
