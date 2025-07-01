package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import InformesDTO.InformeAdto;
import daoImpl.InformesDaoImpl;

/**
 * Servlet implementation class ServletInformeA
 */
@WebServlet("/ServletInformeA")
public class ServletInformeA extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInformeA() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InformesDaoImpl dao = new InformesDaoImpl(); 
		    List<InformeAdto> estadisticas = dao.obtenerEstadisticasPorTipoMovimiento();
		    List<InformeAdto> egresos = dao.obtenerEgresos();

		    request.setAttribute("estadisticas", estadisticas);
		    request.setAttribute("egresos", egresos);
		    RequestDispatcher rd = request.getRequestDispatcher("InicioAdmin.jsp");
		    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
