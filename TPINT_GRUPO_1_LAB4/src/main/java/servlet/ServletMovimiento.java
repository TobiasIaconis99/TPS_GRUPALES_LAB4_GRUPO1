package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cuenta;
import entidad.Movimiento;
import negocio.MovimientoNegocio;
import negocio.CuentaNegocio;
import negocioImpl.MovimientoNegocioImpl;
import negocioImpl.CuentaNegocioImpl;

@WebServlet("/ServletMovimiento")
public class ServletMovimiento extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    private MovimientoNegocio movimientoNegocio;
    private CuentaNegocio cuentaNegocio;

    public ServletMovimiento() {
        super();
        this.movimientoNegocio = new MovimientoNegocioImpl();
        this.cuentaNegocio = new CuentaNegocioImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();

        if ("listar".equals(accion)) {
            String idCuentaStr = request.getParameter("idCuenta");
            
            if (idCuentaStr == null || idCuentaStr.isEmpty()) {
                session.setAttribute("mensajeError", "No se indico una cuenta para ver sus movimientos.");
                response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp");
                return;
            }

            try {
            	
                int idCuenta = Integer.parseInt(idCuentaStr);
                
                // Obtener la cuenta seleccionada para mostrar sus detalles
                Cuenta cuentaSeleccionada = cuentaNegocio.obtenerCuentaPorId(idCuenta);
                if (cuentaSeleccionada == null) {
                    session.setAttribute("mensajeError", "La cuenta seleccionada no existe.");
                    response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp");
                    return;
                }
                request.setAttribute("cuentaSeleccionada", cuentaSeleccionada);

                // Obtener los movimientos de la cuenta seleccionada
                List<Movimiento> movimientos = movimientoNegocio.obtenerPorCuenta(idCuenta); 
                request.setAttribute("listaMovimientos", movimientos); 

                RequestDispatcher rd = request.getRequestDispatcher("Movimientos.jsp");
                rd.forward(request, response);

            } catch (NumberFormatException e) {
                session.setAttribute("mensajeError", "ID de cuenta invalida.");
                response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp");
                System.err.println("Error en Servlet: " + e.getMessage());
            } catch (Exception e) {
                session.setAttribute("mensajeError", "Error al cargar los movimientos de la cuenta.");
                response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp");
                System.err.println("Error en Servlet: " + e.getMessage());
                e.printStackTrace();
            }
        } 
        else {
            session.setAttribute("mensajeError", "Accion no valida o faltan parametros");
            response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}