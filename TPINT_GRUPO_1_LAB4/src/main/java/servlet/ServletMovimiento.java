package servlet; // Asegúrate de que el paquete sea correcto

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Movimiento;
import negocio.MovimientoNegocio;
import negocioImpl.MovimientoNegocioImpl;

// Necesitarías importar también la entidad Cuenta y TipoMovimiento si se muestran detalles en el JSP
// import entidad.Cuenta;
// import entidad.TipoMovimiento;

@WebServlet("/ServletMovimiento") // URL de acceso al Servlet
public class ServletMovimiento extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MovimientoNegocio movimientoNegocio;

    public ServletMovimiento() {
        super();
        this.movimientoNegocio = new MovimientoNegocioImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if (accion == null || accion.isEmpty()) {
            // Acción por defecto: redirigir a una página de inicio o lista de movimientos general
            // Esto podría ser un panel de control para el administrador o simplemente listar todos los movimientos.
            List<Movimiento> listaMovimientos = movimientoNegocio.obtenerTodosLosMovimientos();
            request.setAttribute("listaMovimientos", listaMovimientos);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/MovimientosListadoAdmin.jsp");
            rd.forward(request, response);

        } else if ("listarPorCuenta".equals(accion)) {
            // Listar movimientos de una cuenta específica
            try {
                int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
                // Aquí se podría añadir validación de sesión para que solo el cliente vea sus propios movimientos
                List<Movimiento> movimientos = movimientoNegocio.obtenerMovimientosPorCuentaYFechas(idCuenta, LocalDate.of(1900, 1, 1), LocalDate.now()); // Desde el inicio hasta hoy
                request.setAttribute("movimientosCuenta", movimientos);
                request.setAttribute("idCuenta", idCuenta); // Para mostrar en el JSP
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/MovimientosPorCuenta.jsp");
                rd.forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("mensajeError", "ID de cuenta inválido.");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
                rd.forward(request, response);
            }

        } else if ("filtrarPorFechas".equals(accion)) {
            // Filtrar movimientos por cuenta y rango de fechas
            try {
                int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
                LocalDate fechaInicio = LocalDate.parse(request.getParameter("fechaInicio"));
                LocalDate fechaFin = LocalDate.parse(request.getParameter("fechaFin"));

                List<Movimiento> movimientos = movimientoNegocio.obtenerMovimientosPorCuentaYFechas(idCuenta, fechaInicio, fechaFin);
                request.setAttribute("movimientosCuenta", movimientos);
                request.setAttribute("idCuenta", idCuenta);
                request.setAttribute("fechaInicio", fechaInicio);
                request.setAttribute("fechaFin", fechaFin);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/MovimientosPorCuenta.jsp");
                rd.forward(request, response);
            } catch (NumberFormatException | DateTimeParseException e) {
                request.setAttribute("mensajeError", "Parámetros de fecha o ID de cuenta inválidos.");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
                rd.forward(request, response);
            }

        } else {
            // Acción no reconocida
            request.setAttribute("mensajeError", "Acción no válida.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
            rd.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("registrarMovimiento".equals(accion)) {
            // Lógica para registrar un movimiento (ej. desde un formulario de depósito/extracción)
            try {
                int idCuenta = Integer.parseInt(request.getParameter("idCuenta"));
                int idTipoMovimiento = Integer.parseInt(request.getParameter("idTipoMovimiento"));
                BigDecimal importe = new BigDecimal(request.getParameter("importe"));
                String detalle = request.getParameter("detalle");

                Movimiento nuevoMovimiento = new Movimiento();
                nuevoMovimiento.setIdCuenta(idCuenta);
                nuevoMovimiento.setIdTipoMovimiento(idTipoMovimiento);
                nuevoMovimiento.setFecha(LocalDate.now()); // Fecha actual del movimiento
                nuevoMovimiento.setDetalle(detalle);
                nuevoMovimiento.setImporte(importe);

                boolean exito = movimientoNegocio.registrarMovimiento(nuevoMovimiento);

                if (exito) {
                    request.setAttribute("mensajeExito", "Movimiento registrado exitosamente.");
                    // Redirigir a la vista de movimientos de la cuenta
                    response.sendRedirect(request.getContextPath() + "/ServletMovimiento?accion=listarPorCuenta&idCuenta=" + idCuenta);
                } else {
                    request.setAttribute("mensajeError", "Error al registrar el movimiento. Verifique los datos o el saldo.");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | NullPointerException e) {
                request.setAttribute("mensajeError", "Datos de movimiento incompletos o inválidos.");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
                rd.forward(request, response);
            }

        } else if ("realizarTransferencia".equals(accion)) {
            // Lógica para realizar una transferencia
            try {
                int idCuentaOrigen = Integer.parseInt(request.getParameter("idCuentaOrigen"));
                int idCuentaDestino = Integer.parseInt(request.getParameter("idCuentaDestino"));
                BigDecimal importe = new BigDecimal(request.getParameter("importe"));
                String detalle = request.getParameter("detalleTransferencia");

                boolean exito = movimientoNegocio.realizarTransferencia(idCuentaOrigen, idCuentaDestino, importe, detalle);

                if (exito) {
                    request.setAttribute("mensajeExito", "Transferencia realizada con éxito.");
                    response.sendRedirect(request.getContextPath() + "/ServletMovimiento?accion=listarPorCuenta&idCuenta=" + idCuentaOrigen);
                } else {
                    request.setAttribute("mensajeError", "Error al realizar la transferencia. Verifique saldos o datos.");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | NullPointerException e) {
                request.setAttribute("mensajeError", "Datos de transferencia incompletos o inválidos.");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
                rd.forward(request, response);
            }
        } else {
            // Acción POST no reconocida
            request.setAttribute("mensajeError", "Acción POST no válida.");
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/vistas/error.jsp");
            rd.forward(request, response);
        }
    }
}
