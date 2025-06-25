package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.TipoCuenta;
import negocio.TipoCuentaNegocio;
import negocioImpl.TipoCuentaNegocioImpl;

@WebServlet("/ServletTipoCuenta")
public class ServletTipoCuenta extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TipoCuentaNegocio tipoCuentaNegocio;

    public ServletTipoCuenta() {
        super();
        this.tipoCuentaNegocio = new TipoCuentaNegocioImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if ("listar".equals(accion)) {
            List<TipoCuenta> listaTiposCuenta = tipoCuentaNegocio.listar();

            StringBuilder jsonBuilder = new StringBuilder("[");
            for (int i = 0; i < listaTiposCuenta.size(); i++) {
                TipoCuenta tipo = listaTiposCuenta.get(i);
                String nombreEscapado = tipo.getNombreTipoCuenta().replace("\"", "\\\"");

                jsonBuilder.append("{")
                    .append("\"idTipoCuenta\":").append(tipo.getIdTipoCuenta()).append(",")
                    .append("\"nombreTipoCuenta\":\"").append(nombreEscapado).append("\"")
                    .append("}");

                if (i < listaTiposCuenta.size() - 1) {
                    jsonBuilder.append(",");
                }
            }
            jsonBuilder.append("]");

            response.getWriter().write(jsonBuilder.toString());

        } else {
            // Acción inválida o no especificada
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Acción no válida o no especificada. Use 'accion=listar'.\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}