<%@page import="java.util.List"%>
<%@page import="entidad.Provincia"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
    <%@ include file="includes/NavbarAdmin.jsp" %>
    <div class="d-flex">
        <%@ include file="includes/SidebarAdmin.jsp" %>

        <div class="flex-grow-1 p-4" style="margin-left: 250px;">
            <h4>Agregar nuevo cliente</h4>
            <hr />

            <form action="ServletCliente" method="post">
                <input type="hidden" name="accion" value="agregar">

                <div class="row mb-3">
                    <div class="col-md-4">
                        <label class="form-label">DNI</label>
                        <input type="text" name="dni" class="form-control" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">CUIL</label>
                        <input type="text" name="cuil" class="form-control" required>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Sexo</label>
                        <select name="sexo" class="form-select" required>
                            <option value="">Seleccione</option>
                            <option value="M">Masculino</option>
                            <option value="F">Femenino</option>
                            <option value="O">Otro</option>
                        </select>
                    </div>
                </div>

                <div class="row mb-3">
					<div class="col-md-6">
						<label class="form-label">Nombre</label>
						<input type="text"
							name="nombre" class="form-control" required
							oninput="this.setCustomValidity(/^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{2,40}$/.test(this.value) ? '' : 'Ingrese solo letras y entre 2 y 40 caracteres.')">
					</div>

					<div class="col-md-6">
                        <label class="form-label">Apellido</label>
                        <input type="text" name="apellido" class="form-control" required>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Nacionalidad</label>
                        <input type="text" name="nacionalidad" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Fecha de nacimiento</label>
                        <input type="date" name="fechaNacimiento" class="form-control" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Dirección</label>
                    <input type="text" name="direccion" class="form-control" required>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Provincia</label>
                        <select class="form-select" name="idProvincia" id="provincia" onchange="cargarLocalidades(this.value)" required>
                            <option value="">Seleccione una provincia</option>
                            <%
                                List<Provincia> provincias = (List<Provincia>) request.getAttribute("listaProvincias");
                                if (provincias != null) {
                                    for (Provincia p : provincias) {
                            %>
                                <option value="<%= p.getIdProvincia() %>"><%= p.getNombreProvincia() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Localidad</label>
                        <select class="form-select" name="idLocalidad" id="localidad" required>
                            <option value="">Seleccione una provincia primero</option>
                        </select>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Correo</label>
                        <input type="email" name="correo" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Teléfono</label>
                        <input type="tel" name="telefono" class="form-control" required>
                    </div>
                </div>

				<div class="row mb-3">
				  <div class="col-md-4">
				    <label class="form-label">Usuario</label>
				    <input type="text" name="nombreUsuario" class="form-control" required>
				  </div>
				
				  <div class="col-md-4">
				    <label class="form-label">Clave</label>
				    <input type="password" id="claveUsuario" name="claveUsuario" class="form-control" required>
				  </div>
				
				  <div class="col-md-4">
				    <label class="form-label">Repetir clave</label>
				    <input type="password" id="repetirClaveUsuario" name="repetirClaveUsuario" class="form-control" required oninput="compararClaves()">
				    <div id="mensajeClave" class="form-text text-danger d-none">
				      Las contraseñas no coinciden.
				    </div>
				  </div>
				</div>

                <div class="d-flex justify-content-end">
				    <button type="submit" class="btn btn-primary me-2">Guardar</button>
				    <a href="ServletCliente?accion=listar" class="btn btn-secondary">Cancelar</a>
				</div>
            </form>
        </div>
    </div>

    <script>
    	
    	// Esta funcion sirve para cargar las localidades de la BD segun la provincia que se elija
        function cargarLocalidades(idProvincia) {
            const localidadSelect = document.getElementById('localidad');
            localidadSelect.innerHTML = '<option>Cargando...</option>';
            localidadSelect.disabled = true;

            fetch('ServletLocalidad?accion=listarLocalidades&idProvincia=' + idProvincia)
                .then(res => res.json())
                .then(data => {
                    localidadSelect.innerHTML = '<option value="">Seleccione una localidad</option>';
                    data.forEach(loc => {
                        const option = document.createElement('option');
                        option.value = loc.idLocalidad;
                        option.textContent = loc.nombreLocalidad;
                        localidadSelect.appendChild(option);
                    });
                    localidadSelect.disabled = false;
                })
                .catch(err => {
                    localidadSelect.innerHTML = '<option>Error al cargar</option>';
                    console.error('Error:', err);
                });
        }
    	
	     // Esta funcion sirve para comparar las claves al momento de cargarlas
			function compararClaves() { 
				const clave1 = document.getElementById('claveUsuario').value;
				const clave2 = document.getElementById('repetirClaveUsuario').value;
				const mensaje = document.getElementById('mensajeClave');
					
				if (clave1 && clave2 && clave1 !== clave2) {
					mensaje.classList.remove('d-none');
				} else {
					mensaje.classList.add('d-none');
				}
			} 
    </script>
    
</body>
</html>