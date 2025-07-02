package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Usuario;
import entidad.Cliente;
import negocio.ClienteNegocio;
import negocio.UsuarioNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.UsuarioNegocioImpl;

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
	        request.setCharacterEncoding("UTF-8");

	        String usuarioInput = request.getParameter("txtUsuario"); // Lo que ingresa el usuario
	        String claveInput = request.getParameter("txtClave");     // Lo que ingresa el usuario

	        UsuarioNegocio usuarioNegocio = new UsuarioNegocioImpl();
	        
	        // Llamamos al método de negocio que contendrá la lógica de validación
	        Usuario usu = usuarioNegocio.loguear(usuarioInput, claveInput); 

	        if (usu != null && usu.isEstado()) {
	            HttpSession session = request.getSession();
	            session.setAttribute("usuarioLogueado", usu);

	            if (usu.getTipoUsuario().equals("admin")) {
	                response.sendRedirect(request.getContextPath() + "/ServletInformes");
	            } else {
	                ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
	                Cliente clienteLogueado = clienteNegocio.obtenerPorIdUsuario(usu.getIdUsuario());
	                if (clienteLogueado != null) {
	                    session.setAttribute("clienteLogueado", clienteLogueado);
	                }
	                response.sendRedirect(request.getContextPath() + "/InicioCliente.jsp");
	            }
	        } else {
	            String errorMessage = "Usuario o clave incorrectos.";
	            if (usu != null && !usu.isEstado()) { // Esto puede ser útil si el usuario existe pero está inactivo
	                errorMessage = "Usuario no activo.";
	            }
	            request.setAttribute("errorLogin", errorMessage);
	            request.getRequestDispatcher("/Login.jsp").forward(request, response);
	        }
	    }
}