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
import entidad.Cliente;
import negocio.ClienteNegocio;
import negocioImpl.ClienteNegocioImpl;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletLogin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/Login.jsp"); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Configurar los caracteres
		request.setCharacterEncoding("UTF-8"); 

		String usuario = request.getParameter("txtUsuario");
		String clave = request.getParameter("txtClave");

		UsuarioDao usuDao = new UsuarioDaoImpl();
		Usuario usu = usuDao.loguear(usuario, clave);

		if (usu != null && usu.isEstado()) { // Verifica que el usuario exista y este activop
			HttpSession session = request.getSession();
			session.setAttribute("usuarioLogueado", usu); // Guardo el usuario completo

			if (usu.getTipoUsuario().equals("admin")) { // Si es tipo admin
				// 2. Redireccion al panel de admin
				response.sendRedirect(request.getContextPath() + "/InicioAdmin.jsp");
			} else { // Si no es tipo admin, es cliente
				// Si el usuario es un cliente, obtenemos sus datos completos
				ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
				Cliente clienteLogueado = clienteNegocio.obtenerPorIdUsuario(usu.getIdUsuario()); 
				
				if (clienteLogueado != null) {
					session.setAttribute("clienteLogueado", clienteLogueado); // Guardamos el cliente en la sesion
				}
				// 3. Redireccion al panel de cliente
				response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp"); 
			}
		} else {
			// 4. Mensaje de error si el usuario no esta activo
			String errorMessage = "Usuario o clave incorrectos.";
			if (usu != null && !usu.isEstado()) {
			    errorMessage = "Usuario no activo.";
			}
			request.setAttribute("errorLogin", errorMessage);
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
	}
}