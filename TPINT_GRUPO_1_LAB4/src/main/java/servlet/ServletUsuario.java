package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidad.Usuario;
import negocio.UsuarioNegocio;
import negocioImpl.UsuarioNegocioImpl;

@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ServletUsuario() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    String accion = request.getParameter("accion");
	    UsuarioNegocio usuNegocio = new UsuarioNegocioImpl();

	    if (accion != null && accion.equals("listar")) {
	    	
	        List<Usuario> lista = usuNegocio.listar();
	        request.setAttribute("listaUsuarios", lista);

	        RequestDispatcher rd = request.getRequestDispatcher("ABMLUsuarios.jsp");
	        rd.forward(request, response);
	        
	    } else {
	        response.sendRedirect("ABMLUsuarios.jsp");
	    }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String accion = request.getParameter("accion");
	    UsuarioNegocio usuNegocio = new UsuarioNegocioImpl();

		if (accion != null) {
			switch (accion) {

			case "agregar":
				break;

			case "modificar":
				Usuario modificado = new Usuario();
				modificado.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
				modificado.setNombreUsuario(request.getParameter("nombreUsuario"));
				modificado.setClave(request.getParameter("clave"));
				modificado.setTipoUsuario(request.getParameter("tipoUsuario"));

				usuNegocio.modificar(modificado);
				break;

			case "eliminar":
				break;

			default:
				break;
			}
		}
	    // Redirige al listado actualizado
	    response.sendRedirect("ServletUsuario?accion=listar");
	}
}