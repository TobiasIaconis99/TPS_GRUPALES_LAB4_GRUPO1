<%@page import="negocio.TipoSeguroNegocio"%>
<%@page import="entidad.TipoSeguro"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="negocio.SeguroNegocio"%>
<%@page import="negocioImpl.SeguroNegocioImpl"%>
<%@page import="negocioImpl.TipoSeguroNegocioImpl"%>
<%@page import="entidad.Seguro"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%!String errortext = null; %>
<%!String success = null; %>
<a href="Index.jsp">Inicio</a>
<a href="AgregarSeguro.jsp">Agregar</a>
<a href="ListarSeguro.jsp">Listar</a>
<h1>Agregar seguros</h1>
<form action="AgregarSeguroServlet" method="post">
<div>
	IDSeguro
	<%SeguroNegocio sNegocio = new SeguroNegocioImpl(); %>
	<input type="hidden" name="id" value="<%=sNegocio.lista().size()+1 %>" />
	<%=sNegocio.lista().size()+1 %>
</div>
<div>
	Descripcion
	<input type="text" name="txtDescripcion" />
</div>
<div>
	Tipo de seguro
	<select name="SeltipoSeguro">
		<%TipoSeguroNegocio tsNegocio = new TipoSeguroNegocioImpl();
		for (TipoSeguro tipo : tsNegocio.readAll()) {
		%>
		<option><%=tipo.getDescripcion() %></option>			
		<% } %>
	</select>
</div>
<div>
	Costo contratacion
	<input type="number" name="txtCostoContratacion" />
</div>
<div>
	Costo maximo asegurado
	<input type="number" name="txtCostoMaxAseg" />
</div>
<input type="submit" value="Enviar" name="btnSubmit">
</form>

<% if(request.getAttribute("inserted") != null){
	success = "Agregado con exito"; %>
<p> <%=success %></p>
<%} %>

<% if(request.getAttribute("msgError") != null){
	errortext = (String)request.getAttribute("msgError"); %>
<p> <%=errortext %></p>
<%} %>
</body>
</html>