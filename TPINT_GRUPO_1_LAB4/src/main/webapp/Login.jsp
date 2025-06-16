<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
	
	<%--Estilo para que el logeo se vea en el medio de la pantalla--%>
	<style>
	.login-form {
		width: 340px;
		margin: auto;
	}
	
	.login-form form {
		padding: 20px;
	}
	</style>
</head>

<body class="d-flex align-items-center justify-content-center" style="min-height: 100vh;">
	<div class="login-form">
			<form method="post" action="ServletLogin">
				<h2 class="text-center">Banco</h2>
				<br>
				
				<% 
					// Si el usuario o la clave son incorrectos aparecera un mensaje de error
				    String errorLogin = (String) request.getAttribute("errorLogin");

				    if (errorLogin != null) {
				%>
				    <div class="alert alert-danger text-center" role="alert">
				        <%= errorLogin %>
				    </div>
				<% 
				    } 
				%>	

				<div class="form-group">
					<input type="text" class="form-control" name="txtUsuario" placeholder="Usuario" required>
				</div>
				<div class="form-group">
					<input type="password" class="form-control" name="txtClave" placeholder="Clave" required>
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary btn-block" name="btnIngresar">Ingresar</button>
				</div>       
			</form>
	</div>
</body>
</html>