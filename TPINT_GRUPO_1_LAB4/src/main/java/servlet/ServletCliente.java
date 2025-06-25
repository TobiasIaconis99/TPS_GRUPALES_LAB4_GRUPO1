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

		String accion = request.getParameter("accion");

		if (accion != null) {
			switch (accion) {
			case "listar":
				List<Cliente> listaClientes = clienteNegocio.listar();
				request.setAttribute("listaClientes", listaClientes);
				RequestDispatcher rdListar = request.getRequestDispatcher("ABMLClientes.jsp");
				rdListar.forward(request, response);
				break;
				
			case "formularioAgregarCliente": // Esto podria estar en un servlet de Provincias
				ProvinciaNegocio provinciaNegocio = new ProvinciaNegocioImpl();  
				List<Provincia> listaProvincias = provinciaNegocio.listar();
				request.setAttribute("listaProvincias", listaProvincias);
				RequestDispatcher rdAgregar = request.getRequestDispatcher("AgregarCliente.jsp");
				rdAgregar.forward(request, response);
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

		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();

		System.out.println("ServletCliente -> doPost: Acción recibida: " + accion);

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
				// --- 1. VALIDACIÓN DE DATOS (Ej: Claves) ---
				String clave1 = request.getParameter("claveUsuario");
				String clave2 = request.getParameter("repetirClaveUsuario");
				
				if (!clave1.equals(clave2)) {
					session.setAttribute("mensajeError", "Las contraseñas no coinciden. No se pudo agregar el cliente.");
					// Redirigimos de vuelta al formulario de agregar para que corrija
					response.sendRedirect("ServletCliente?accion=formularioAgregarCliente");
					return; // Importante para detener la ejecución
				}
				
				// --- 2. RECUPERACIÓN DE PARÁMETROS ---
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

				// --- 3. PARSEO Y MANEJO DE ERRORES ---
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

				// --- 4. CREACIÓN DE OBJETOS ---
				// Objeto Usuario
				Usuario usuario = new Usuario();
				usuario.setNombreUsuario(nombreUsuario);
				usuario.setClave(clave1); // Usamos la clave ya validada
				usuario.setTipoUsuario("Cliente");
				usuario.setEstado(true);

				// Objeto Localidad (solo necesitamos el ID para la relación)
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
				cliente.setUsuario(usuario); // Asignamos el objeto Usuario
				cliente.setLocalidad(localidad); // Asignamos el objeto Localidad

				// --- 5. LLAMADA A LA CAPA DE NEGOCIO ---
				boolean fueAgregado = clienteNegocio.agregar(cliente);
				
				// --- 6. GESTIÓN DE LA RESPUESTA ---
				if (fueAgregado) {
					session.setAttribute("mensajeExito", "Se agrego el cliente exitosamente.");
				} else {
					session.setAttribute("mensajeError", "No se pudo agregar el cliente. El DNI o el nombre de usuario ya pueden existir.");
				}
				break;
				
			case "modificar":
				// Falta la logica para modificar
				break;
				
			default:
				session.setAttribute("mensajeError", "Acción del post no válida.");
				break;
			}
		}
		response.sendRedirect("ServletCliente?accion=listar");
	}
}