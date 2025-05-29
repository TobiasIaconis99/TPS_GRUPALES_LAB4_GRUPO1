package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SeguroDao;
import dao.TipoSeguroDao;
import daoImpl.SeguroDaoImpl;
import daoImpl.TipoSeguroDaoImpl;
import entidad.Seguro;


@WebServlet("/ListarSeguroServlet")
public class ListarSeguroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListarSeguroServlet() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("list") != null) {
			SeguroDao segDao = new SeguroDaoImpl();
			ArrayList<Seguro> sgList = segDao.readAll();
			
			request.setAttribute("listComplete", sgList);
			
			RequestDispatcher rd = request.getRequestDispatcher("/ListarSeguro.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SeguroDao segDao = new SeguroDaoImpl();
		ArrayList<Seguro> sgList = null;
		
		if(request.getParameter("btnFiltrar") != null) {
			TipoSeguroDao tsDao = new TipoSeguroDaoImpl();
			sgList = segDao.filter(tsDao.lookUpId(request.getParameter("SeltipoSeguro")));
		}
		
		if(request.getParameter("btnRecargar") != null) {
			sgList = segDao.readAll();
		}
		
		request.setAttribute("listComplete", sgList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/ListarSeguro.jsp");
		rd.forward(request, response);
	}

}
