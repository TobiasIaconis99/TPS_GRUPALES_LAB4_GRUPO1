package servlet;

import java.io.IOException;
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
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentaNegocioImpl;

/**
 * Servlet implementation class ServletCuenta
 */
@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
	private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
	
    public ServletCuenta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accion = request.getParameter("accion");
		HttpSession session = request.getSession();
		if ("agregar".equals(accion)) {
			try {
				String idTipoCuentaStr = request.getParameter("tipoCuenta");
				int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);
				TipoCuenta tipoCuenta = new TipoCuenta();
				tipoCuenta.setIdTipoCuenta(idTipoCuenta);

				Cliente cliente = clienteNegocio.obtenerPorDni(dniCliente);
				if (cliente == null) {
					session.setAttribute("mensajeError", "Cliente no encontrado.");
					response.sendRedirect("ServletCuenta?accion=listar");
					return;
				}

				Cuenta cuenta = new Cuenta();
				cuenta.setCliente(cliente);
				cuenta.setTipoCuenta(tipoCuenta);
				cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
				cuenta.setSaldo(0.0); // Inicialmente en 0
				cuenta.setEstado(true);

				boolean fueAgregado = cuentaNegocio.agregar(cuenta);

				if (fueAgregado) {
					session.setAttribute("mensajeExito", "Cuenta agregada correctamente.");
				} else {
					session.setAttribute("mensajeError", "No se pudo agregar la cuenta.");
				}
			} catch (Exception e) {
				session.setAttribute("mensajeError", "Error al agregar cuenta: " + e.getMessage());
			}
			response.sendRedirect("ServletCuenta?accion=listar");
		}
	
	}

}
