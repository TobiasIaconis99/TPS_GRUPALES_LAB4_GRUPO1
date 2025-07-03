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
		int idCuota = Integer.parseInt(idCuotaString);
		String idCuentaString = request.getParameter("cuentaDestino");
		int idCuenta = Integer.parseInt(idCuentaString);
		CuotaDao cDao = new CuotaDaoImpl();
		cDao.pagarCuota(idCuota);
		CuentaDao cuentaDao = new CuentaDaoImpl();
		String montoCuotaString = request.getParameter("idMontoCuota");
		BigDecimal montoCuota = new BigDecimal(montoCuotaString);
		
		cuentaDao.descontarSaldo(idCuenta, montoCuota);
		doGet(request, response);
	}

}
