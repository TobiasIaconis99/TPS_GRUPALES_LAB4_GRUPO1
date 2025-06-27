package servlet;

import java.io.IOException;
import java.math.BigDecimal; // Importar BigDecimal
import java.time.LocalDate;
import java.util.List;
import java.util.UUID; // Para generar números de cuenta/CBU aleatorios

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoCuenta;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio; // Importar TipoCuentaNegocio
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl; // Importar TipoCuentaNegocioImpl

/**
 * Servlet implementation class ServletCuenta
 */
@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
	private TipoCuentaNegocio tipoCuentaNegocio = new TipoCuentaNegocioImpl(); // Instanciar TipoCuentaNegocio

	public ServletCuenta() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		if (accion == null || accion.equals("listar")) {
			// Los mensajes se manejan a través de la sesión en doPost
			// Aquí solo se obtienen los datos para la tabla
			List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
			List<Cliente> listaClientes = clienteNegocio.listar(); // Necesario para el modal de agregar
			request.setAttribute("listaCuentas", listaCuentas);
			request.setAttribute("listaClientes", listaClientes);
			RequestDispatcher rd = request.getRequestDispatcher("ABMLCuentas.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();

		if (accion != null) {
			switch (accion) {
			case "eliminar":
				String cuentaAEliminar = request.getParameter("idCuenta");
				if (cuentaAEliminar != null && !cuentaAEliminar.isEmpty()) {
										
					if (cuentaNegocio.eliminar(Integer.parseInt(cuentaAEliminar))) {
						// Usar un nombre de atributo de sesión específico para cuentas
						session.setAttribute("mensajeExitoCuenta", "Se eliminó la cuenta exitosamente.");
					} else {
						// Usar un nombre de atributo de sesión específico para cuentas
						session.setAttribute("mensajeErrorCuenta", "No se pudo eliminar la cuenta.");
					}
				} else {
					// Usar un nombre de atributo de sesión específico para cuentas
					session.setAttribute("mensajeErrorCuenta", "ID de cuenta no especificado para eliminar.");
				}
				break;
			case "agregar":
				try {
					// --- Obtener Tipo de Cuenta ---
					String idTipoCuentaStr = request.getParameter("tipoCuenta");
					System.out.println("ServletCuenta: ID Tipo Cuenta recibido = " + idTipoCuentaStr); // Debug

					if (idTipoCuentaStr == null || idTipoCuentaStr.isEmpty()) {
						session.setAttribute("mensajeErrorCuenta", "El tipo de cuenta es requerido."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);
					TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoCuentaPorId(idTipoCuenta); 

					if (tipoCuenta == null) {
						System.out.println(
								"ServletCuenta: ERROR - Tipo de cuenta NO encontrado para ID: " + idTipoCuenta); // Debug
						session.setAttribute("mensajeErrorCuenta", "Tipo de cuenta no encontrado o inválido."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					} else {
						System.out.println(
								"ServletCuenta: Tipo de cuenta encontrado: " + tipoCuenta.getNombreTipoCuenta()); // Debug
					}

					// --- Obtener Cliente ---
					String dniClienteStr = request.getParameter("dniCliente");
					System.out.println("ServletCuenta: DNI Cliente recibido = " + dniClienteStr); // Debug

					if (dniClienteStr == null || dniClienteStr.isEmpty()) {
						session.setAttribute("mensajeErrorCuenta", "El DNI del cliente es requerido."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					Cliente cliente = clienteNegocio.obtenerPorDni(dniClienteStr);
					if (cliente == null) {
						System.out.println("ServletCuenta: ERROR - Cliente NO encontrado para DNI: " + dniClienteStr); // Debug
						session.setAttribute("mensajeErrorCuenta", "Cliente no encontrado."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					} else {
						System.out.println(
								"ServletCuenta: Cliente encontrado: " + cliente.getDni() + " - " + cliente.getNombre()); // Debug
					}

					// --- Crear y configurar la Cuenta ---
					Cuenta cuenta = new Cuenta();
					cuenta.setCliente(cliente);
					cuenta.setTipoCuenta(tipoCuenta);
					cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
					cuenta.setSaldo(new BigDecimal("0.00")); // Usar BigDecimal para saldo
					cuenta.setEstado(true);

					// --- GENERAR NUMERO DE CUENTA Y CBU ---
					// En tu implementación real, aquí se llamaría a CuentaDaoImpl.generarSiguienteCBU()
					// Asegúrate que los métodos generateUniqueAccountNumber y generateUniqueCBU sean robustos para producción
					String numeroCuentaGenerado = generateUniqueAccountNumber(); 
					String cbuGenerado = generateUniqueCBU(); 

					cuenta.setNumeroCuenta(numeroCuentaGenerado);
					cuenta.setCbu(cbuGenerado);

					System.out.println("ServletCuenta: Cuenta a agregar - DNI: " + cliente.getDni() + ", Tipo ID: "
							+ tipoCuenta.getIdTipoCuenta() + ", Nro Cuenta: " + numeroCuentaGenerado + ", CBU: "
							+ cbuGenerado); // Debug

					boolean fueAgregado = cuentaNegocio.agregar(cuenta);
					System.out.println("ServletCuenta: Resultado de cuentaNegocio.agregar = " + fueAgregado); // Debug

					if (fueAgregado) {
						session.setAttribute("mensajeExitoCuenta", "Cuenta agregada correctamente."); // Específico
					} else {
						session.setAttribute("mensajeErrorCuenta", "No se pudo agregar la cuenta. Verifique los logs."); // Específico
					}
				} catch (NumberFormatException e) {
					System.err.println("ServletCuenta: NumberFormatException al convertir el ID de Tipo Cuenta: "
							+ e.getMessage()); // Debug
					e.printStackTrace(); // Imprimir el stack trace completo
					session.setAttribute("mensajeErrorCuenta",
							"Error en el formato del tipo de cuenta. Seleccione un tipo válido."); // Específico
				} catch (Exception e) {
					System.err.println("ServletCuenta: ERROR general al agregar cuenta: " + e.getMessage()); // Debug
					e.printStackTrace(); // Imprimir el stack trace completo para ver la causa raíz
					session.setAttribute("mensajeErrorCuenta", "Error al agregar cuenta: " + e.getMessage()); // Específico
				}
				
			case "editar":
				try {
					// --- Obtener Tipo de Cuenta ---
					String idTipoCuentaStr = request.getParameter("tipoCuenta");
					System.out.println("ServletCuenta: ID Tipo Cuenta recibido = " + idTipoCuentaStr); // Debug

					if (idTipoCuentaStr == null || idTipoCuentaStr.isEmpty()) {
						session.setAttribute("mensajeErrorCuenta", "El tipo de cuenta es requerido."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);
					TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoCuentaPorId(idTipoCuenta); 

					if (tipoCuenta == null) {
						System.out.println(
								"ServletCuenta: ERROR - Tipo de cuenta NO encontrado para ID: " + idTipoCuenta); // Debug
						session.setAttribute("mensajeErrorCuenta", "Tipo de cuenta no encontrado o inválido."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					} else {
						System.out.println(
								"ServletCuenta: Tipo de cuenta encontrado: " + tipoCuenta.getNombreTipoCuenta()); // Debug
					}

					// --- Obtener Cliente ---
					String dniClienteStr = request.getParameter("dniCliente");
					System.out.println("ServletCuenta: DNI Cliente recibido = " + dniClienteStr); // Debug

					if (dniClienteStr == null || dniClienteStr.isEmpty()) {
						session.setAttribute("mensajeErrorCuenta", "El DNI del cliente es requerido."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					Cliente cliente = clienteNegocio.obtenerPorDni(dniClienteStr);
					if (cliente == null) {
						System.out.println("ServletCuenta: ERROR - Cliente NO encontrado para DNI: " + dniClienteStr); // Debug
						session.setAttribute("mensajeErrorCuenta", "Cliente no encontrado."); // Específico
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					} else {
						System.out.println(
								"ServletCuenta: Cliente encontrado: " + cliente.getDni() + " - " + cliente.getNombre()); // Debug
					}
					
					String idCuentaStr = request.getParameter("idCuenta");
					if (idCuentaStr == null || idCuentaStr.isEmpty()) {
						session.setAttribute("mensajeErrorCuenta", "El ID de la cuenta es requerido para editar."); 
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}
					
					String saldoStr = request.getParameter("saldo");
					BigDecimal saldo = new BigDecimal(saldoStr);
					// --- modificar y configurar la Cuenta ---
					int idCuenta = Integer.parseInt(idCuentaStr);
					Cuenta cuenta = new Cuenta();
					cuenta.setIdCuenta(idCuenta);
					
					cuenta.setCliente(cliente);
					cuenta.setTipoCuenta(tipoCuenta);
					cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
					cuenta.setSaldo(saldo); // Usar BigDecimal para saldo
					cuenta.setEstado(true);

	
					boolean fueModificado = cuentaNegocio.modificar(cuenta);
					System.out.println("ServletCuenta: Resultado de cuentaNegocio.agregar = " + fueModificado); // Debug

					if (fueModificado) {
						session.setAttribute("mensajeExitoCuenta", "Cuenta modificada correctamente."); // Específico
					} else {
						session.setAttribute("mensajeErrorCuenta", "No se pudo modificar la cuenta. Verifique los logs."); // Específico
					}
				} catch (NumberFormatException e) {
					System.err.println("ServletCuenta: NumberFormatException al convertir el ID de Tipo Cuenta: "
							+ e.getMessage()); // Debug
					e.printStackTrace(); // Imprimir el stack trace completo
					session.setAttribute("mensajeErrorCuenta",
							"Error en el formato del tipo de cuenta. Seleccione un tipo válido."); // Específico
				} catch (Exception e) {
					System.err.println("ServletCuenta: ERROR general al agregar cuenta: " + e.getMessage()); // Debug
					e.printStackTrace(); // Imprimir el stack trace completo para ver la causa raíz
					session.setAttribute("mensajeErrorCuenta", "Error al agregar cuenta: " + e.getMessage()); // Específico
				}
				
				
				break;
			}
		}
		
		response.sendRedirect("ServletCuenta?accion=listar"); // Siempre redirige a listar para recargar la tabla y mostrar el mensaje
	}

	// Métodos auxiliares para generar números de cuenta y CBU (pueden ser más
	// sofisticados)
	private String generateUniqueAccountNumber() {
		// Genera un número de cuenta simple para propósitos de prueba
		// En un entorno real, esto debería ser más robusto y verificar unicidad en la
		// DB
		return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}

	private String generateUniqueCBU() {
		// Genera un CBU simple para propósitos de prueba
		// En un entorno real, esto es más complejo y debe verificar unicidad
		return "CBU-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
	}
}