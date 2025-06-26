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
			List<Cuenta> listaCuentas = cuentaNegocio.listarCuentas();
			List<Cliente> listaClientes = clienteNegocio.listar();
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
						session.setAttribute("mensajeExito", "Se eliminó la cuenta exitosamente.");
					} else {
						session.setAttribute("mensajeError", "No se pudo eliminar la cuenta.");
					}
				} else {
					session.setAttribute("mensajeError", "id de cuenta no especificado para eliminar.");
				}
				break;
			case "agregar":
				try {
					// --- Obtener Tipo de Cuenta ---
					String idTipoCuentaStr = request.getParameter("tipoCuenta");
					System.out.println("ServletCuenta: ID Tipo Cuenta recibido = " + idTipoCuentaStr); // Debug

					if (idTipoCuentaStr == null || idTipoCuentaStr.isEmpty()) {
						session.setAttribute("mensajeError", "El tipo de cuenta es requerido.");
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);
					TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoCuentaPorId(idTipoCuenta); // Obtener el objeto
																									// TipoCuenta
																									// completo

					if (tipoCuenta == null) {
						System.out.println(
								"ServletCuenta: ERROR - Tipo de cuenta NO encontrado para ID: " + idTipoCuenta); // Debug
						session.setAttribute("mensajeError", "Tipo de cuenta no encontrado o inválido.");
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
						session.setAttribute("mensajeError", "El DNI del cliente es requerido.");
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					Cliente cliente = clienteNegocio.obtenerPorDni(dniClienteStr);
					if (cliente == null) {
						System.out.println("ServletCuenta: ERROR - Cliente NO encontrado para DNI: " + dniClienteStr); // Debug
						session.setAttribute("mensajeError", "Cliente no encontrado.");
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
					// Puedes usar un método más sofisticado para esto,
					// por ahora usaremos UUIDs aleatorios para prueba.
					// Ojo: Esto no garantiza unicidad si no lo verificas contra la DB,
					// pero para depurar el INSERT es suficiente.
					String numeroCuentaGenerado = generateUniqueAccountNumber(); // Implementar este método
					String cbuGenerado = generateUniqueCBU(); // Implementar este método

					cuenta.setNumeroCuenta(numeroCuentaGenerado);
					cuenta.setCbu(cbuGenerado);

					System.out.println("ServletCuenta: Cuenta a agregar - DNI: " + cliente.getDni() + ", Tipo ID: "
							+ tipoCuenta.getIdTipoCuenta() + ", Nro Cuenta: " + numeroCuentaGenerado + ", CBU: "
							+ cbuGenerado); // Debug

					boolean fueAgregado = cuentaNegocio.agregar(cuenta);
					System.out.println("ServletCuenta: Resultado de cuentaNegocio.agregar = " + fueAgregado); // Debug

					if (fueAgregado) {
						session.setAttribute("mensajeExito", "Cuenta agregada correctamente.");
					} else {
						session.setAttribute("mensajeError", "No se pudo agregar la cuenta. Verifique los logs.");
					}
				} catch (NumberFormatException e) {
					System.err.println("ServletCuenta: NumberFormatException al convertir el ID de Tipo Cuenta: "
							+ e.getMessage()); // Debug
					e.printStackTrace(); // Imprimir el stack trace completo
					session.setAttribute("mensajeError",
							"Error en el formato del tipo de cuenta. Seleccione un tipo válido.");
				} catch (Exception e) {
					System.err.println("ServletCuenta: ERROR general al agregar cuenta: " + e.getMessage()); // Debug
					e.printStackTrace(); // Imprimir el stack trace completo para ver la causa raíz
					session.setAttribute("mensajeError", "Error al agregar cuenta: " + e.getMessage());
				}
				
				break;
			}
		}
		
		response.sendRedirect("ServletCuenta?accion=listar");
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