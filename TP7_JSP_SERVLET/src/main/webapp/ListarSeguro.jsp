<%@page import="entidad.Seguro"%>
<%@page import="java.util.ArrayList"%>
<%@page import="negocio.TipoSeguroNegocio"%>
<%@page import="entidad.TipoSeguro"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="negocio.SeguroNegocio"%>
<%@page import="negocioImpl.SeguroNegocioImpl"%>
<%@page import="negocioImpl.TipoSeguroNegocioImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="Index.jsp">Inicio</a>
<a href="AgregarSeguro.jsp">Agregar</a>
<a href="ListarSeguro.jsp">Listar</a>

<% ArrayList<Seguro> listaSeguros = null;
	if(request.getAttribute("listComplete") != null){
		listaSeguros = (ArrayList<Seguro>)request.getAttribute("listComplete");
	}	
%>
<br>
<br>
<form action="ListarSeguroServlet" method="post">
	<div>
		Busqueda por tipo de seguros: 
		<select name="SeltipoSeguro">
				<%TipoSeguroNegocio tsNegocio = new TipoSeguroNegocioImpl();
				for (TipoSeguro tipo : tsNegocio.readAll()) {
				%>
				<option><%=tipo.getDescripcion() %></option>			
				<% } %>
		</select>
		<input type="submit" value="Filtrar" name="btnFiltrar">
	</div>
</form>
<br>
<br>
<table border="1">
	<tr> <th>ID</th> <th>Descripcion</th> <th>Descripcion Tipo de Seguro</th> <th>Costo Contratacion</th> 
		<th>Costo Asegurado</th>
		
	</tr>
	<% if(listaSeguros != null){
		for(Seguro item : listaSeguros){
	%>		
		<tr>
			<th><%= item.getIdSeguro() %></th> <th><%= item.getDescripcion() %></th> <th><%= item.getTipoSeguro().getDescripcion() %></th> <th><%= item.getCostoAsegurado() %></th> 
			<th><%=item.getCostoAsegurado() %></th>
		</tr>	
	<%	}
	}%>
</table>
<br>
<br>
<form action="ListarSeguroServlet" method="post">
	<input type="submit" value="Recargar lista" name="btnRecargar">
</form>
</body>
</html>