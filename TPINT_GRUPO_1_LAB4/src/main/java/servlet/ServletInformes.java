package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.MovimientoDaoImpl;
import entidad.Informes;


import java.util.List;

/**
 * Servlet implementation class ServletInicioAdmin
 */
@WebServlet("/ServletInformes")
public class ServletInformes extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInformes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    MovimientoDaoImpl dao = new MovimientoDaoImpl(); // o tu interfaz si la tenés
	    List<Informes> estadisticas = dao.obtenerEstadisticasPorTipoMovimiento();

	    request.setAttribute("estadisticas", estadisticas);
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
