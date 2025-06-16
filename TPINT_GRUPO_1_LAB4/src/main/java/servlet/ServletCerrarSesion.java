package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ServletCerrarSesion")
public class ServletCerrarSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletCerrarSesion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false); // No crea una nueva si no existe
		if (session != null) {
			session.invalidate(); // Cierra sesión
		}
		response.sendRedirect("Login.jsp"); // Vuelve al login
	}
}