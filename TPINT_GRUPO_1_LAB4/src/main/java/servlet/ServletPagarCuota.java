package servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CuentaDao;
import dao.CuotaDao;
import daoImpl.CuentaDaoImpl;
import daoImpl.CuotaDaoImpl;

/**
 * Servlet implementation class ServletPagarCuota
 */
@WebServlet("/ServletPagarCuota")
public class ServletPagarCuota extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPagarCuota() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String idCuotaString = request.getParameter("idCuotaHidden");
	    String idCuentaString = request.getParameter("cuentaDestino");
	    String montoCuotaString = request.getParameter("idMontoCuota");
	    String idPrestamoString = request.getParameter("idPrestamoHidden");

	    if (idCuotaString == null || idCuentaString == null || montoCuotaString == null || idPrestamoString == null ||
	        idCuotaString.isEmpty() || idCuentaString.isEmpty() || montoCuotaString.isEmpty() || idPrestamoString.isEmpty()) {
	        throw new ServletException("Faltan parámetros obligatorios.");
	    }

	    int idCuota;
	    int idCuenta;
	    int idPrestamo;
	    BigDecimal montoCuota;

	    try {
	        idCuota = Integer.parseInt(idCuotaString);
	        idCuenta = Integer.parseInt(idCuentaString);
	        idPrestamo = Integer.parseInt(idPrestamoString);
	        montoCuota = new BigDecimal(montoCuotaString);
	    } catch (NumberFormatException e) {
	        throw new ServletException("Parámetros numéricos inválidos.", e);
	    }

	    CuotaDao cDao = new CuotaDaoImpl();
	    cDao.pagarCuota(idCuota);

	    CuentaDao cuentaDao = new CuentaDaoImpl();
	    cuentaDao.descontarSaldo(idCuenta, montoCuota);

	    request.getSession().setAttribute("mensajeExito", "La cuota se pago exitosamente");
	   

	    response.sendRedirect("CuotasPrestamo.jsp?idPrestamo=" + idPrestamo);
	}



}
