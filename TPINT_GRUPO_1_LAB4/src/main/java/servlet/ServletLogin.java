package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsuarioDao;
import daoImpl.UsuarioDaoImpl;
import entidad.Usuario;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String usuario = request.getParameter("txtUsuario");
        String clave = request.getParameter("txtClave");

        // Validar con base de datos
        UsuarioDao usuDao = new UsuarioDaoImpl(); // Esto lo implementás según tu DAO
        Usuario usu = usuDao.login(usuario, clave); // Si existe, devuelve el usuario; si no, null

        if (usu != null && usu.isEstado()) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usu);
            
            if (usu.getTipoUsuario().equals("admin")) {
                response.sendRedirect("InicioAdmin.jsp");
            } else {
                response.sendRedirect("InicioCliente.jsp");
            }
        } else {
            request.setAttribute("errorLogin", "Usuario o clave incorrectos");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
	}
}