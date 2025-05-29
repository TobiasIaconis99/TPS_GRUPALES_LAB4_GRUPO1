<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="modelo.TipoSeguro" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agregar Seguro</title>
</head>
<body>

	<nav>
		<a href="Inicio.jsp">Inicio</a> | <a href="AgregarSeguro.jsp">Agregar seguro</a> | <a href="ListarSeguros.jsp">Listar seguros</a>
	</nav>

	<h2>Agregar Seguro</h2>

	<form action="AgregarSeguro" method="post">
		<table>
			<tr>
				<td>IdSeguro:</td>
				<td><input type="text" value="<%= request.getAttribute("proximoId") %>" readonly></td>
			</tr>
			<tr>
				<td>Descripción:</td>
				<td><input type="text" name="descripcion" style="width: 150px;"></td>
			</tr>
			<tr>
				<td>Tipo de Seguro:</td>
				<td><select name="idTipo" style="width: 156px;">
						<option value="" disabled selected>Seleccionar...</option>
						<!-- Opciones dinámicas desde el servlet -->
				</select></td>
			</tr>
			<tr>
				<td>Costo Contratación:</td>
				<td><input type="text" name="costoContratacion"
					style="width: 150px;"></td>
			</tr>
			<tr>
				<td>Costo Máximo Asegurado:</td>
				<td><input type="text" name="costoMaximo" style="width: 150px;"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: right;"><input type="submit"
					value="Aceptar"></td>
			</tr>
		</table>
	</form>

</body>
</html>