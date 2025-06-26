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
        String tipoFiltro = request.getParameter("tipoFiltro");
        String nombreFiltro = request.getParameter("nombreFiltro");
        String paginaStr = request.getParameter("pagina");

        int pagina = (paginaStr != null && !paginaStr.isEmpty()) ? Integer.parseInt(paginaStr) : 1;
        int registrosPorPagina = 10;
        int offset = (pagina - 1) * registrosPorPagina;

        UsuarioNegocio usuNegocio = new UsuarioNegocioImpl();

        if ("listar".equals(accion)) {
            List<Usuario> listaUsuarios = usuNegocio.buscarConFiltros(nombreFiltro, tipoFiltro, offset, registrosPorPagina);
            int totalRegistros = usuNegocio.contarConFiltros(nombreFiltro, tipoFiltro);
            int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

            request.setAttribute("listaUsuarios", listaUsuarios);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);

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
                    Usuario eliminado = new Usuario();
                    eliminado.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
                    usuNegocio.eliminar(eliminado.getIdUsuario());
                    break;
                default:
                    break;
            }
        }
        response.sendRedirect("ServletUsuario?accion=listar");
    }
}
