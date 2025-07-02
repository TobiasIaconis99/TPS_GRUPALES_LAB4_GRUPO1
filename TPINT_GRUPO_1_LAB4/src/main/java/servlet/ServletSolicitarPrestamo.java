package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImpl.PrestamoDaoImpl;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.Prestamo;
import entidad.TipoCuenta;
import negocio.CuentaNegocio;
import negocio.PrestamoNegocio;
import negocio.TipoCuentaNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.PrestamoNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

/**
 * Servlet implementation class ServletPrestamo
 */
@WebServlet("/ServletSolicitarPrestamo")
public class ServletSolicitarPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletSolicitarPrestamo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    PrestamoNegocio pNegocio = new PrestamoNegocioImpl();
	    HttpSession session = request.getSession();
	    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

	    if (cliente != null) {
	        int idCliente = cliente.getId();

	        CuentaNegocio cNegocio = new CuentaNegocioImpl();
	        List<Cuenta> cuentas = cNegocio.obtenerCuentaPorClienteId(idCliente);

	        // Guardar la lista en el request
	        request.setAttribute("cuentasCliente", cuentas);

	        // Reenviar la solicitud al JSP sin perder los datos
	        request.getRequestDispatcher("/prestamos.jsp").forward(request, response);

	    } else {
	        // Redirigir al login si no hay sesión activa
	        response.sendRedirect(request.getContextPath() + "/Login.jsp");
	    }
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrestamoNegocio pNegocio = new PrestamoNegocioImpl();

		try {
			Prestamo p = new Prestamo();

			p.setIdCuenta(Integer.parseInt(request.getParameter("idCuenta")));
			p.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
			p.setFechaAlta(request.getParameter("fechaAlta"));
			p.setMontoSolicitado(new BigDecimal(request.getParameter("montoSolicitado")));
			p.setPlazoMeses(Integer.parseInt(request.getParameter("plazoMeses")));
			p.setCantidadCuotas(Integer.parseInt(request.getParameter("cantidadCuotas")));
			p.setMontoCuota(new BigDecimal(request.getParameter("montoCuota")));
			p.setEstado(1);

			pNegocio.agregar(p);

			response.sendRedirect("prestamos.jsp");

		} catch (Exception e) {
			e.printStackTrace(); // importante para depurar
			request.setAttribute("error", "Hubo un error al registrar el préstamo.");
			request.getRequestDispatcher("nuevoPrestamo.jsp").forward(request, response);
		}
	}

}
