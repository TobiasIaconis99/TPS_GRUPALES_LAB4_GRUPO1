package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;
import entidad.Usuario;
import negocio.ClienteNegocio;
import negocio.ProvinciaNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.ProvinciaNegocioImpl;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ClienteNegocio clienteNegocio;

	public ServletCliente() {
		super();
		this.clienteNegocio = new ClienteNegocioImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();

		if (accion != null) {
			switch (accion) {
			case "listar":
			    String busqueda = request.getParameter("busqueda");
			    String filtroSexo = request.getParameter("filtroSexo");

			    // Normalizar parámetros vacíos
			    if (busqueda != null && busqueda.trim().isEmpty()) busqueda = null;
			    if (filtroSexo != null && filtroSexo.trim().isEmpty()) filtroSexo = null;

			    // Validar filtroSexo (solo acepta M, F, O)
			    if (filtroSexo != null && !filtroSexo.matches("M|F|O")) {
			        session.setAttribute("mensajeError", "Filtro de sexo inválido.");
			        response.sendRedirect("ServletCliente?accion=listar");
			        return;
			    }

			    // Validar y obtener número de página
			    int pagina = 1;
			    int cantidadPorPagina = 10;

			    try {
			        if (request.getParameter("pagina") != null) {
			            pagina = Integer.parseInt(request.getParameter("pagina"));
			            if (pagina < 1) pagina = 1;
			        }
			    } catch (NumberFormatException e) {
			        session.setAttribute("mensajeError", "Número de página inválido.");
			        response.sendRedirect("ServletCliente?accion=listar");
			        return;
			    }

			    // Obtener datos filtrados con paginación
			    List<Cliente> listaClientesFiltrados = clienteNegocio.listarFiltrado(busqueda, filtroSexo, pagina, cantidadPorPagina);
			    int totalRegistros = clienteNegocio.contarFiltrado(busqueda, filtroSexo);
			    int totalPaginas = (int) Math.ceil((double) totalRegistros / cantidadPorPagina);

			    // Evitar redireccionar a una página que no existe (por ejemplo: página 10 cuando hay solo 2)
			    if (pagina > totalPaginas && totalPaginas > 0) {
			        response.sendRedirect("ServletCliente?accion=listar&pagina=" + totalPaginas +
			                (busqueda != null ? "&busqueda=" + busqueda : "") +
			                (filtroSexo != null ? "&filtroSexo=" + filtroSexo : ""));
			        return;
			    }

			    // Enviar datos a la vista
			    request.setAttribute("listaClientes", listaClientesFiltrados);
			    request.setAttribute("paginaActual", pagina);
			    request.setAttribute("totalPaginas", totalPaginas);
			    request.setAttribute("busqueda", busqueda != null ? busqueda : "");
			    request.setAttribute("filtroSexo", filtroSexo != null ? filtroSexo : "");

			    RequestDispatcher rd = request.getRequestDispatcher("ABMLClientes.jsp");
			    rd.forward(request, response);
			    break;

				
			case "formularioAgregarCliente":
				ProvinciaNegocio provinciaNegocio = new ProvinciaNegocioImpl();  
				List<Provincia> listaProvincias = provinciaNegocio.listar();
				request.setAttribute("listaProvincias", listaProvincias);
				RequestDispatcher rdAgregar = request.getRequestDispatcher("AgregarCliente.jsp");
				rdAgregar.forward(request, response);
				break;
				
			case "formularioModificarCliente":
				String dniModificar = request.getParameter("dni");
				if (dniModificar == null || dniModificar.isEmpty()) {
					session.setAttribute("mensajeError", "DNI del cliente no especificado para modificar.");
					response.sendRedirect("ServletCliente?accion=listar");
					return;
				}
				
				Cliente clienteAEditar = clienteNegocio.obtenerPorDni(dniModificar);
				if (clienteAEditar == null) {
					session.setAttribute("mensajeError", "Cliente no encontrado para modificar.");
					response.sendRedirect("ServletCliente?accion=listar");
					return;
				}
				
				ProvinciaNegocio provinciaNegocioModificar = new ProvinciaNegocioImpl();
				List<Provincia> provinciasParaModificar = provinciaNegocioModificar.listar();
				
				request.setAttribute("clienteAEditar", clienteAEditar);
				request.setAttribute("listaProvincias", provinciasParaModificar);
				
				RequestDispatcher rdModificar = request.getRequestDispatcher("ModificarCliente.jsp");
				rdModificar.forward(request, response);
				break;
				
			default:
				response.sendRedirect("ServletCliente?accion=listar");
				break;
			}
		} else {
			response.sendRedirect("ServletCliente?accion=listar");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); // Establecer la codificación de caracteres para la solicitud
		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();

		if (accion != null) {
			switch (accion) {
			
			case "eliminar":
				String dniAEliminar = request.getParameter("dni");
				if (dniAEliminar != null && !dniAEliminar.isEmpty()) {
					if (clienteNegocio.eliminar(dniAEliminar)) {
						session.setAttribute("mensajeExito", "Se eliminó el cliente exitosamente.");
					} else {
						session.setAttribute("mensajeError", "No se pudo eliminar el cliente.");
					}
				} else {
					session.setAttribute("mensajeError", "DNI del cliente no especificado para eliminar.");
				}
				break;
				
			case "agregar":
				// 1. VALIDACION DE DATOS
				String clave1 = request.getParameter("claveUsuario");
				String clave2 = request.getParameter("repetirClaveUsuario");
				
				if (!clave1.equals(clave2)) { // Ya lo hice en el jsp con javascript pero lo dejo por las dudas
					session.setAttribute("mensajeError", "Las contraseñas no coinciden. No se pudo agregar el cliente.");
					response.sendRedirect("ServletCliente?accion=formularioAgregarCliente");
					return;
				}
				
				// 2. RECUPERACION DE PARAMETROS
				String dni = request.getParameter("dni");
				String cuil = request.getParameter("cuil");
				String sexo = request.getParameter("sexo");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String nacionalidad = request.getParameter("nacionalidad");
				String fechaNacimientoStr = request.getParameter("fechaNacimiento");
				String direccion = request.getParameter("direccion");
				String correo = request.getParameter("correo");
				String telefono = request.getParameter("telefono");
				String nombreUsuario = request.getParameter("nombreUsuario");

				// 3. MANEJO DE ERRORES
				int idLocalidad = -1;
				Date fechaNacimiento = null;
				try {
					idLocalidad = Integer.parseInt(request.getParameter("idLocalidad"));
					if (fechaNacimientoStr != null && !fechaNacimientoStr.isEmpty()) {
						fechaNacimiento = Date.valueOf(fechaNacimientoStr);
					}
				} catch (Exception e) {
					session.setAttribute("mensajeError", "Error en los datos del formulario (Localidad o Fecha). " + e.getMessage());
					response.sendRedirect("ServletCliente?accion=listar");
					return;
				}

				// 4. CREACION DE OBJETOS
				// Objeto Usuario
				Usuario usuario = new Usuario();
				usuario.setNombreUsuario(nombreUsuario);
				usuario.setClave(clave1); // Usamos la clave ya validada
				usuario.setTipoUsuario("Cliente");
				usuario.setEstado(true);

				// Objeto Localidad (solo necesitamos el ID)
				Localidad localidad = new Localidad();
				localidad.setIdLocalidad(idLocalidad);

				// Objeto Cliente
				Cliente cliente = new Cliente();
				cliente.setDni(dni);
				cliente.setCuil(cuil);
				cliente.setSexo(sexo);
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setNacionalidad(nacionalidad);
				cliente.setFechaNacimiento(fechaNacimiento);
				cliente.setDireccion(direccion);
				cliente.setCorreo(correo);
				cliente.setTelefono(telefono);
				cliente.setEstado(true);
				cliente.setUsuario(usuario);
				cliente.setLocalidad(localidad);

				// 5. LLAMADA A CAPA NEGOCIO
				boolean fueAgregado = clienteNegocio.agregar(cliente);
				
				// 6. RESPUESTA
				if (fueAgregado) {
					session.setAttribute("mensajeExito", "Se agrego el cliente exitosamente.");
				} else {
					session.setAttribute("mensajeError", "No se pudo agregar el cliente. El DNI o el nombre de usuario ya pueden existir.");
				}
				break;
				
			case "modificar":
				// 1. RECUPERACION DE PARAMETROS
				String dniModificarCliente = request.getParameter("dni"); 
				String cuilModificar = request.getParameter("cuil");
				String sexoModificar = request.getParameter("sexo");
				String nombreModificar = request.getParameter("nombre");
				String apellidoModificar = request.getParameter("apellido");
				String nacionalidadModificar = request.getParameter("nacionalidad");
				String fechaNacimientoStrModificar = request.getParameter("fechaNacimiento");
				String direccionModificar = request.getParameter("direccion");
				String correoModificar = request.getParameter("correo");
				String telefonoModificar = request.getParameter("telefono");
				String nombreUsuarioModificar = request.getParameter("nombreUsuario");
				String claveUsuarioModificar = request.getParameter("claveUsuario");

				// 2. MANEJO DE ERRORES
				int idLocalidadModificar = -1;
				Date fechaNacimientoModificar = null;
				try {
					idLocalidadModificar = Integer.parseInt(request.getParameter("idLocalidad"));
					if (fechaNacimientoStrModificar != null && !fechaNacimientoStrModificar.isEmpty()) {
						fechaNacimientoModificar = Date.valueOf(fechaNacimientoStrModificar);
					}
				} catch (Exception e) {
					session.setAttribute("mensajeError", "Error en los datos del formulario de modificación (Localidad o Fecha). " + e.getMessage());
					response.sendRedirect("ServletCliente?accion=listar");
					return;
				}
				
				// 3. OBJETOS MODIFICADOS
				// Objeto Usuario
				int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
				Usuario usuarioModificar = new Usuario();
				usuarioModificar.setIdUsuario(idUsuario);
				usuarioModificar.setNombreUsuario(nombreUsuarioModificar);
				usuarioModificar.setClave(claveUsuarioModificar); 
				usuarioModificar.setTipoUsuario("Cliente");
				usuarioModificar.setEstado(true);

				// Objeto Localidad (solo necesitamos el ID)
				Localidad localidadModificar = new Localidad();
				localidadModificar.setIdLocalidad(idLocalidadModificar);

				// Objeto Cliente
				Cliente clienteModificado = new Cliente();
				clienteModificado.setDni(dniModificarCliente);
				clienteModificado.setCuil(cuilModificar);
				clienteModificado.setSexo(sexoModificar);
				clienteModificado.setNombre(nombreModificar);
				clienteModificado.setApellido(apellidoModificar);
				clienteModificado.setNacionalidad(nacionalidadModificar);
				clienteModificado.setFechaNacimiento(fechaNacimientoModificar);
				clienteModificado.setDireccion(direccionModificar);
				clienteModificado.setCorreo(correoModificar);
				clienteModificado.setTelefono(telefonoModificar);
				clienteModificado.setEstado(true);
				clienteModificado.setUsuario(usuarioModificar);
				clienteModificado.setLocalidad(localidadModificar);

				// 4. LLAMADA A LA CAPA DE NEGOCIO PARA MODIFICAR
				boolean fueModificado = clienteNegocio.modificar(clienteModificado);
				
				// 5. RESPUESTA
				if (fueModificado) {
					session.setAttribute("mensajeExito", "Se modificó el cliente exitosamente.");
				} else {
					session.setAttribute("mensajeError", "No se pudo modificar el cliente. Verifique los datos o si el DNI existe.");
				}
				break;
				
			default:
				session.setAttribute("mensajeError", "Acción del post no válida.");
				break;
			}
		}
		response.sendRedirect("ServletCliente?accion=listar");
	}
}