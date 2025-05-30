package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import dao.SeguroDao;
import dao.TipoSeguroDao;
import daoImpl.SeguroDaoImpl;
import daoImpl.TipoSeguroDaoImpl;
import entidad.Seguro;
import entidad.TipoSeguro;
import negocio.SeguroNegocio;
import negocioImpl.SeguroNegocioImpl;

/**
 * Servlet implementation class AgregarSeguroServlet
 */
@WebServlet("/AgregarSeguroServlet")
public class AgregarSeguroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgregarSeguroServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		res.getWriter().append("Served at: ").append(req.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			SeguroNegocio sn = new SeguroNegocioImpl();
			try {
				Seguro seg = new Seguro();
				if (req.getParameter("txtDescripcion") != ""
						&& req.getParameter("txtCostoContratacion")!=""
						&& req.getParameter("txtCostoMaxAseg")!="") {
						
					if(isNumeric((String)req.getParameter("txtCostoContratacion")) == true && isNumeric((String)req.getParameter("txtCostoMaxAseg")) == true) {
						if(Float.parseFloat(req.getParameter("txtCostoMaxAseg")) 
								>= Float.parseFloat(req.getParameter("txtCostoContratacion"))) {
							seg.setIdSeguro(sn.lista().size()+1);
							TipoSeguro tSeg = new TipoSeguro();
							seg.setDescripcion(req.getParameter("txtDescripcion"));
							tSeg.setDescripcion(req.getParameter("SeltipoSeguro"));
							TipoSeguroDao tsDao = new TipoSeguroDaoImpl();
							tSeg.setIdTipoSeguro(tsDao.lookUpId(tSeg.getDescripcion()));
							seg.setTipoSeguro(tSeg);
							seg.setCostoContratacion(Double.parseDouble(req.getParameter("txtCostoContratacion")));
							seg.setCostoAsegurado(Double.parseDouble(req.getParameter("txtCostoMaxAseg")));
							
							req.setAttribute("inserted", sn.insert(seg));
							
						}
					
						else {
							req.setAttribute("msgError", "El costo maximo no puede ser menor al costo de contratacion");
						}
					}
						else {
							req.setAttribute("msgError", "Solo pueden ingresarse numeros en los campos Costo Contratacion y Costo Maximo");
						}

				}
				else {
					req.setAttribute("msgError", "Campos Incompletos");
				}

			} catch (Exception e) {
				System.out.println(e.toString());
				throw e;
			}

			
			RequestDispatcher rd = req.getRequestDispatcher("/AgregarSeguro.jsp");
			rd.forward(req, res);
			
	}
	
	public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

}
