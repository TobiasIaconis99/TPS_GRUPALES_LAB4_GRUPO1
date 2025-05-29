package servlets;

import dao.SeguroDAO;
import modelo.TipoSeguro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/AgregarSeguro")
public class ServletAgregarSeguro extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletAgregarSeguro() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SeguroDAO dao = new SeguroDAO();

        // Obtener tipos de seguros
        List<TipoSeguro> tipos = dao.obtenerTipos();

        // Obtener próximo ID de seguro
        int proximoId = dao.obtenerProximoIdSeguro();

        // Pasar a la vista
        request.setAttribute("tipos", tipos);
        request.setAttribute("proximoId", proximoId);

        // Ir al JSP
        RequestDispatcher rd = request.getRequestDispatcher("AgregarSeguro.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Solo para prueba; si se implementa guardar, irá acá.
    }
}

