package servlet;

import java.io.IOException;
import java.util.List;
import java.sql.Date; 

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
import negocioImpl.ClienteNegocioImpl;

@WebServlet("/ServletCliente")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteNegocio clienteNegocio;

	public ServletCliente() {
		super();
		clienteNegocio = new ClienteNegocioImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();

		if (accion != null) {
			switch (accion) {
			case "listar":
				List<Cliente> listaClientes = clienteNegocio.listar();
				request.setAttribute("listaClientes", listaClientes);
				RequestDispatcher rdListar = request.getRequestDispatcher("ABMLClientes.jsp");
				rdListar.forward(request, response);
				break;

			case "abrirFormularioAgregar":
				List<Provincia> provinciasParaAgregar = clienteNegocio.listarProvincias();
				request.setAttribute("listaProvincias", provinciasParaAgregar);
				RequestDispatcher rdAgregar = request.getRequestDispatcher("AgregarModificarCliente.jsp");
				rdAgregar.forward(request, response);
				break;

			case "abrirFormularioModificar":
				String dniModificar = request.getParameter("dni");
				Cliente clienteAEditar = null;
				List<Provincia> provinciasParaModificar = clienteNegocio.listarProvincias();
				// Ya no necesitamos 'localidadesPrecargadas' aquí para pasar al JSP.
				// El JavaScript las cargará dinámicamente.
				// List<Localidad> localidadesPrecargadas = null; 

				if (dniModificar != null && !dniModificar.isEmpty()) {
					clienteAEditar = clienteNegocio.obtenerPorDni(dniModificar);
					System.out.println("ServletCliente -> abirFormularioModificar: Cliente a editar obtenido: " + (clienteAEditar != null ? clienteAEditar.getDni() : "null"));
                    
                    // Verificación más robusta de la localidad y provincia del cliente
					if (clienteAEditar != null && clienteAEditar.getLocalidad() != null
							&& clienteAEditar.getLocalidad().getProvincia() != null) {
						// Antes aquí pasabas 'localidadesPrecargadas', pero el JS lo manejará.
						// Mantengo este bloque para depuración si fuera necesario obtenerlas para otra cosa.
						// localidadesPrecargadas = clienteNegocio.listarLocalidadesPorProvincia(
						// 		clienteAEditar.getLocalidad().getProvincia().getIdProvincia());
						// System.out.println("ServletCliente -> abirFormularioModificar: ID de provincia del cliente: " + clienteAEditar.getLocalidad().getProvincia().getIdProvincia());
						// System.out.println("ServletCliente -> abirFormularioModificar: Localidades precargadas (cantidad): " + (localidadesPrecargadas != null ? localidadesPrecargadas.size() : "0"));
					} else {
						// Si el cliente no tiene localidad o provincia asignada, podría ser un error de datos.
						System.err.println("ServletCliente -> abirFormularioModificar: Cliente con DNI " + dniModificar + " no tiene localidad o provincia asociada, o es nulo.");
						session.setAttribute("mensajeError", "El cliente o su dirección no pudieron ser cargados correctamente.");
						response.sendRedirect("ServletCliente?accion=listar");
						return;
					}
				} else {
					System.err.println("ServletCliente -> abirFormularioModificar: DNI del cliente no especificado.");
					session.setAttribute("mensajeError", "DNI del cliente no especificado para modificar.");
					response.sendRedirect("ServletCliente?accion=listar");
					return;
				}

				request.setAttribute("clienteAEditar", clienteAEditar);
				request.setAttribute("listaProvincias", provinciasParaModificar);
				// NO necesitas setear 'listaLocalidadesPrecargadas' aquí en el request,
				// porque el JavaScript las va a pedir via AJAX.
				// request.setAttribute("listaLocalidadesPrecargadas", localidadesPrecargadas); 
				RequestDispatcher rdModificar = request.getRequestDispatcher("AgregarModificarCliente.jsp");
				rdModificar.forward(request, response);
				break;

			case "listarLocalidades":
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				int idProvincia = -1;
				try {
					String idProvinciaParam = request.getParameter("idProvincia");
                    System.out.println("ServletCliente -> listarLocalidades: ID Provincia recibido en AJAX: " + idProvinciaParam);
					if (idProvinciaParam != null && !idProvinciaParam.isEmpty()) {
						idProvincia = Integer.parseInt(idProvinciaParam);
					} else {
                        System.err.println("ServletCliente -> listarLocalidades: ID de provincia no proporcionado.");
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
						response.getWriter().write("{\"error\":\"ID de provincia no proporcionado\"}");
						return;
					}
				} catch (NumberFormatException e) {
                    System.err.println("ServletCliente -> listarLocalidades: ID de provincia inválido: " + request.getParameter("idProvincia") + " - Error: " + e.getMessage());
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
					response.getWriter().write("{\"error\":\"ID de provincia inválido\"}");
					return;
				}

				List<Localidad> listaLocalidades = clienteNegocio.listarLocalidadesPorProvincia(idProvincia);
                System.out.println("ServletCliente -> listarLocalidades: Cantidad de localidades encontradas para provincia " + idProvincia + ": " + (listaLocalidades != null ? listaLocalidades.size() : "0"));
				
				StringBuilder jsonBuilder = new StringBuilder("[");
				if (listaLocalidades != null && !listaLocalidades.isEmpty()) { 
					for (int i = 0; i < listaLocalidades.size(); i++) {
						Localidad loc = listaLocalidades.get(i);
						jsonBuilder.append("{");
						jsonBuilder.append("\"idLocalidad\":").append(loc.getIdLocalidad()).append(",");
						jsonBuilder.append("\"nombreLocalidad\":\"").append(loc.getNombreLocalidad()).append("\"");
						jsonBuilder.append("}");
						if (i < listaLocalidades.size() - 1) {
							jsonBuilder.append(",");
						}
					}
				}
				jsonBuilder.append("]");
                String finalJson = jsonBuilder.toString();
                System.out.println("ServletCliente -> listarLocalidades: JSON enviado: " + finalJson);
				response.getWriter().write(finalJson);
				return;
				
			default:
				response.sendRedirect("ServletCliente?accion=listar");
				break;
			}
		} else {
			response.sendRedirect("ServletCliente?accion=listar");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
			case "modificar":
				String dni = request.getParameter("dni");
				String cuil = request.getParameter("cuil");
				String sexo = request.getParameter("sexo");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String nacionalidad = request.getParameter("nacionalidad");
				String fechaNacimientoStr = request.getParameter("fechaNacimiento");
				String direccion = request.getParameter("direccion");
				int idLocalidad = -1;
				try {
					idLocalidad = Integer.parseInt(request.getParameter("idLocalidad"));
				} catch (NumberFormatException e) {
					session.setAttribute("mensajeError", "Error: Localidad no válida. Por favor, seleccione una.");
					response.sendRedirect("ServletCliente?accion=listar"); 
					return;
				}
				String telefono = request.getParameter("telefono");
				String correo = request.getParameter("correo");
				String nombreUsuario = request.getParameter("nombreUsuario");
				String claveUsuario = request.getParameter("claveUsuario");

				Date fechaNacimiento = null;
				if (fechaNacimientoStr != null && !fechaNacimientoStr.isEmpty()) {
					try {
						fechaNacimiento = Date.valueOf(fechaNacimientoStr);
					} catch (IllegalArgumentException e) {
						session.setAttribute("mensajeError",
								"Error en el formato de fecha de nacimiento. Use}}_{\text{YYYY-MM-DD}}.");
						response.sendRedirect("ServletCliente?accion=listar");
						return;
					}
				}

				Localidad loc = new Localidad();
				loc.setIdLocalidad(idLocalidad);

				Usuario user = new Usuario();
				user.setNombreUsuario(nombreUsuario);
				user.setClave(claveUsuario);
				user.setTipoUsuario("Cliente");
				user.setEstado(true);

				Cliente c = new Cliente();
				c.setDni(dni);
				c.setCuil(cuil);
				c.setSexo(sexo);
				c.setNombre(nombre);
				c.setApellido(apellido);
				c.setNacionalidad(nacionalidad);
				c.setFechaNacimiento(fechaNacimiento);
				c.setDireccion(direccion);
				c.setLocalidad(loc);
				c.setTelefono(telefono);
				c.setCorreo(correo);
				c.setUsuario(user);
				c.setEstado(true);

				boolean operacionExitosa = false;
				if ("agregar".equals(accion)) {
					operacionExitosa = clienteNegocio.agregar(c);
					if (operacionExitosa) {
						session.setAttribute("mensajeExito", "¡Cliente agregado exitosamente!");
					} else {
						session.setAttribute("mensajeError",
								"Error al agregar el cliente. El DNI o Usuario pueden ya existir.");
					}
				} else if ("modificar".equals(accion)) {
					Cliente clienteExistente = clienteNegocio.obtenerPorDni(dni); 
					if (clienteExistente != null && clienteExistente.getUsuario() != null) {
						c.getUsuario().setIdUsuario(clienteExistente.getUsuario().getIdUsuario());
					} else {
						session.setAttribute("mensajeError", "Error al modificar: No se pudo recuperar el usuario del cliente existente.");
						response.sendRedirect("ServletCliente?accion=listar");
						return;
					}
					operacionExitosa = clienteNegocio.modificar(c);
					if (operacionExitosa) {
						session.setAttribute("mensajeExito", "¡Cliente modificado exitosamente!");
					} else {
						session.setAttribute("mensajeError",
								"Error al modificar el cliente. Verifique los datos o logs.");
					}
				}
				break;
			default:
				session.setAttribute("mensajeError", "Acción POST no válida.");
				break;
			}
		}
		response.sendRedirect("ServletCliente?accion=listar");
	}
}