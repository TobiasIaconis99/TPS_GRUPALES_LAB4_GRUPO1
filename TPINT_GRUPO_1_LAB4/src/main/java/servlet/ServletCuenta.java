package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
import negocio.TipoCuentaNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
	private TipoCuentaNegocio tipoCuentaNegocio = new TipoCuentaNegocioImpl();

	public ServletCuenta() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");
		if (accion == null || accion.equals("listar")) {

			String filtroTipoCuenta = request.getParameter("filtroTipoCuenta");
			List<Cuenta> listaCuentas;

			if (filtroTipoCuenta != null && !filtroTipoCuenta.isEmpty()) {
				try {
					int idTipo = Integer.parseInt(filtroTipoCuenta);
					listaCuentas = cuentaNegocio.listarCuentasActivas().stream()
							.filter(c -> c.getTipoCuenta().getIdTipoCuenta() == idTipo)
							.toList();
				} catch (NumberFormatException e) {
					listaCuentas = cuentaNegocio.listarCuentasActivas();
				}
			} else {
				listaCuentas = cuentaNegocio.listarCuentasActivas();
			}

			request.setAttribute("listaCuentas", listaCuentas);
			request.setAttribute("listaClientes", clienteNegocio.listar());

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
						session.setAttribute("mensajeExitoCuenta", "Se eliminó la cuenta exitosamente.");
					} else {
						session.setAttribute("mensajeErrorCuenta", "No se pudo eliminar la cuenta.");
					}
				} else {
					session.setAttribute("mensajeErrorCuenta", "ID de cuenta no especificado para eliminar.");
				}
				break;

			case "agregar":
				try {
					String idTipoCuentaStr = request.getParameter("tipoCuenta");
					if (idTipoCuentaStr == null || idTipoCuentaStr.isEmpty()) {
						session.setAttribute("mensajeErrorCuenta", "El tipo de cuenta es requerido.");
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);
					TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoCuentaPorId(idTipoCuenta);

					String dniClienteStr = request.getParameter("dniCliente");
					if (dniClienteStr == null || dniClienteStr.isEmpty()) {
						session.setAttribute("mensajeErrorCuenta", "El DNI del cliente es requerido.");
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					Cliente cliente = clienteNegocio.obtenerPorDni(dniClienteStr);
					if (cliente == null) {
						session.setAttribute("mensajeErrorCuenta", "Cliente no encontrado.");
						response.sendRedirect("ServletCuenta?accion=listar");
						return;
					}

					Cuenta cuenta = new Cuenta();
					cuenta.setCliente(cliente);
					cuenta.setTipoCuenta(tipoCuenta);
					cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
					cuenta.setSaldo(new BigDecimal("0.00"));
					cuenta.setEstado(true);

					boolean fueAgregado = cuentaNegocio.agregar(cuenta);

					if (fueAgregado) {
						session.setAttribute("mensajeExitoCuenta", "Cuenta agregada correctamente.");
					} else {
						session.setAttribute("mensajeErrorCuenta", "No se pudo agregar la cuenta. Verifique que el cliente no tenga más de 3 cuentas activas.");
					}
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("mensajeErrorCuenta", "Error al agregar cuenta: " + e.getMessage());
				}
				break;
			}
		}

		response.sendRedirect("ServletCuenta?accion=listar");
	}
}
