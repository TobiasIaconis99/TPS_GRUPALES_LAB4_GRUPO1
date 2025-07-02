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
import InformesDTO.InformeBdto;
import daoImpl.InformesDaoImpl;

/**
 * Servlet implementation class ServletInformeA
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


		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    InformesDaoImpl dao = new InformesDaoImpl();

		    // Obtener parámetros del filtro
		    String mesParam = request.getParameter("mes");
		    String anioParam = request.getParameter("anio");

		    int mes = (mesParam != null && !mesParam.isEmpty()) ? Integer.parseInt(mesParam) : -1;
		    int anio = (anioParam != null && !anioParam.isEmpty()) ? Integer.parseInt(anioParam) : -1;

		    // informe a
		    List<InformeAdto> estadisticas = dao.obtenerIngresosPorMesYAnio(mes, anio);
		    List<InformeAdto> egresos = dao.obtenerEgresosPorMesYAnio(mes, anio);
		    //informe b
		    List<InformeBdto> clientesActivos = dao.listarClientesConMasMovimientos(mes, anio);

		    // request informe a
		    request.setAttribute("estadisticas", estadisticas);
		    request.setAttribute("egresos", egresos);
		    request.setAttribute("mesSeleccionado", mes);
		    request.setAttribute("anioSeleccionado", anio);
		    // request informe b
		    request.setAttribute("clientesActivos", clientesActivos);

		    RequestDispatcher rd = request.getRequestDispatcher("InicioAdmin.jsp");
		    rd.forward(request, response);
		}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
