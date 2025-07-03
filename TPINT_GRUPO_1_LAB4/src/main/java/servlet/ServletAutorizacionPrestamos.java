package servlet;

import entidad.Prestamo;
import negocio.AutorizacionPrestamoNegocio;
import negocioImpl.AutorizacionPrestamoNegocioImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ServletAutorizacionPrestamos")
public class ServletAutorizacionPrestamos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AutorizacionPrestamoNegocio negocioPrestamo;

    public ServletAutorizacionPrestamos() {
        super();
        this.negocioPrestamo = new AutorizacionPrestamoNegocioImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        int paginaActual = 1;
        int registrosPorPagina = 10;

        try {
            if (request.getParameter("pagina") != null) {
                paginaActual = Integer.parseInt(request.getParameter("pagina"));
            }
        } catch (NumberFormatException e) {
            paginaActual = 1;
        }

        if (accion == null || accion.isEmpty() || accion.equals("listar")) {
            String busqueda = request.getParameter("busqueda");
            String filtroEstado = request.getParameter("filtro");

            String busquedaDNI = (busqueda != null && !busqueda.trim().isEmpty()) ? busqueda.trim() : null;
            String estadoFiltro = (filtroEstado != null && !filtroEstado.trim().isEmpty()) ? filtroEstado.trim() : null;

            int totalPrestamos = negocioPrestamo.contarTotalPrestamos(busquedaDNI, estadoFiltro);
            int totalPaginas = (int) Math.ceil((double) totalPrestamos / registrosPorPagina);

            if (totalPaginas == 0 && totalPrestamos == 0) {
                paginaActual = 1;
            } else if (paginaActual > totalPaginas) {
                paginaActual = totalPaginas;
            } else if (paginaActual < 1) {
                paginaActual = 1;
            }

            List<Prestamo> listaPrestamos = negocioPrestamo.listarPrestamosPaginado(
                busquedaDNI, estadoFiltro, paginaActual, registrosPorPagina
            );
            
            request.setAttribute("listaPrestamos", listaPrestamos);
            request.setAttribute("paginaActual", paginaActual);
            request.setAttribute("totalPaginas", totalPaginas);

            RequestDispatcher rd = request.getRequestDispatcher("/AutorizacionPrestamos.jsp");
            rd.forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción inválida: " + accion);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion != null && !accion.isEmpty()) {
            int idPrestamo = 0;
            try {
                idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "ID de préstamo inválido.");
                request.setAttribute("tipoMensaje", "danger");
                doGet(request, response);
                return;
            }

            boolean exito = false;
            String mensajeError = null;

            try {
                if (accion.equals("aprobarPrestamo")) {
                    exito = negocioPrestamo.modificarEstado(idPrestamo, 2);
                    if (!exito) mensajeError = "No se pudo aprobar el préstamo.";
                } else if (accion.equals("rechazarPrestamo")) {
                    exito = negocioPrestamo.modificarEstado(idPrestamo, 0);
                    if (!exito) mensajeError = "No se pudo rechazar el préstamo.";
                } else {
                    request.setAttribute("mensaje", "Acción no reconocida.");
                    request.setAttribute("tipoMensaje", "danger");
                    doGet(request, response);
                    return;
                }

                if (exito) {
                    request.setAttribute("mensaje", "Operación exitosa para préstamo ID: " + idPrestamo);
                    request.setAttribute("tipoMensaje", "success");
                } else {
                    request.setAttribute("mensaje", mensajeError);
                    request.setAttribute("tipoMensaje", "danger");
                }
            } catch (Exception e) {
                request.setAttribute("mensaje", "Error interno al procesar el préstamo.");
                request.setAttribute("tipoMensaje", "danger");
            }
        } else {
            request.setAttribute("mensaje", "No se especificó ninguna acción.");
            request.setAttribute("tipoMensaje", "warning");
        }

        doGet(request, response);
    }
}
