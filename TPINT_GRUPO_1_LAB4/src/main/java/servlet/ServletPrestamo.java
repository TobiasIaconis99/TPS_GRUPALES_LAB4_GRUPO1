package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.PrestamoDaoImpl;
import entidad.Prestamo;
import entidad.TipoCuenta;
import negocio.PrestamoNegocio;
import negocio.TipoCuentaNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.PrestamoNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

/**
 * Servlet implementation class ServletPrestamo
 */
@WebServlet("/ServletPrestamo")
public class ServletPrestamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPrestamo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TipoCuentaNegocio pNegocio = new TipoCuentaNegocioImpl();
			
//			@Override
//			public TipoCuenta obtenerTipoCuentaPorId(int id) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public List<TipoCuenta> listar() {
//				// TODO Auto-generated method stub
//				return null;
//			}
			
//			TipoCuentaNegocio tpcNegocio = new TipoCuentaNegocioImpl();
//			List<TipoCuenta> listTipoCuenta = tpcNegocio.listar();
//		};
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
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
