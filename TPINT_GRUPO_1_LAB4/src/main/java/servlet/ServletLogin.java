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
import entidad.Cliente; // Importar Cliente
import negocio.ClienteNegocio; // Importar ClienteNegocio
import negocioImpl.ClienteNegocioImpl; // Importar ClienteNegocioImpl

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

		UsuarioDao usuDao = new UsuarioDaoImpl();
		Usuario usu = usuDao.loguear(usuario, clave);

		if (usu != null && usu.isEstado()) {
			HttpSession session = request.getSession();
			session.setAttribute("usuarioLogueado", usu);

			if (usu.getTipoUsuario().equals("admin")) {
				response.sendRedirect("InicioAdmin.jsp");
			} else {
				// Si el usuario es un cliente, obtenemos sus datos completos
				ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
				Cliente clienteLogueado = clienteNegocio.obtenerPorIdUsuario(usu.getIdUsuario()); // Asume que tienes un método para obtener cliente por ID de usuario
				
				if (clienteLogueado != null) {
					session.setAttribute("clienteLogueado", clienteLogueado); // Guardamos el objeto Cliente en sesión
				}
				response.sendRedirect("InicioCliente.jsp");
			}
		} else {
			request.setAttribute("errorLogin", "Usuario o clave incorrectos");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
	}
}