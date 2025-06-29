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
                session.setAttribute("mensajeError", "No se indicó una cuenta para ver sus movimientos.");
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

                int paginaActual = 1;
                int registrosPorPagina = 10;

                try {
                    String paginaParam = request.getParameter("pagina");
                    if (paginaParam != null && !paginaParam.isEmpty()) {
                        paginaActual = Integer.parseInt(paginaParam);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Parámetro de página inválido, usando página 1. Error: " + e.getMessage());
                }

                // Obtener el total de movimientos para la cuenta y calcular total de páginas
                int totalMovimientos = movimientoNegocio.contarMovimientosPorCuenta(idCuenta);
                int totalPaginas = (int) Math.ceil((double) totalMovimientos / registrosPorPagina);
                
                // Asegurar que la página actual esté dentro de un rango válido
                if (totalPaginas == 0) { // Si no hay registros, solo hay 1 página (la primera)
                    paginaActual = 1;
                } else if (paginaActual > totalPaginas) {
                    paginaActual = totalPaginas; // Si se pide una página mayor que el total, va a la última
                } else if (paginaActual < 1) {
                    paginaActual = 1; // Si se pide una página menor que 1, va a la primera
                }

                // Obtener la lista de movimientos paginada
                List<Movimiento> movimientos = movimientoNegocio.obtenerMovimientosPorCuentaPaginado(idCuenta, paginaActual, registrosPorPagina); 

                request.setAttribute("listaMovimientos", movimientos); 
                request.setAttribute("paginaActual", paginaActual);
                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("idCuentaActual", idCuenta); // Para usar en los enlaces de paginación

                RequestDispatcher rd = request.getRequestDispatcher("Movimientos.jsp"); 
                rd.forward(request, response);

            } catch (NumberFormatException e) {
                session.setAttribute("mensajeError", "ID de cuenta inválida.");
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
            session.setAttribute("mensajeError", "Acción no válida o faltan parámetros.");
            response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}