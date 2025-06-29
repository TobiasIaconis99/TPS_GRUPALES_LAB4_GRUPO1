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

        int pagina = 1;
        int registrosPorPagina = 10;

        try {
            if (paginaStr != null && !paginaStr.isEmpty()) {
                pagina = Integer.parseInt(paginaStr);
                if (pagina <= 0) pagina = 1;
            }
        } catch (NumberFormatException e) {
            pagina = 1;
        }

        int offset = (pagina - 1) * registrosPorPagina;

        UsuarioNegocio usuNegocio = new UsuarioNegocioImpl();

        if ("listar".equalsIgnoreCase(accion)) {
            try {
                List<Usuario> listaUsuarios = usuNegocio.buscarConFiltros(nombreFiltro, tipoFiltro, offset, registrosPorPagina);
                int totalRegistros = usuNegocio.contarConFiltros(nombreFiltro, tipoFiltro);
                int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

                request.setAttribute("listaUsuarios", listaUsuarios);
                request.setAttribute("paginaActual", pagina);
                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("nombreFiltro", nombreFiltro);
                request.setAttribute("tipoFiltro", tipoFiltro);

            } catch (Exception e) {
                request.setAttribute("error", "Error al listar usuarios: " + e.getMessage());
            }

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
                case "modificar":
                    try {
                        String idStr = request.getParameter("idUsuario");
                        String nombre = request.getParameter("nombreUsuario");
                        String clave = request.getParameter("clave");
                        String tipo = request.getParameter("tipoUsuario");

                        // Validacion campos completos
                        if (idStr == null || nombre == null || clave == null || tipo == null ||
                            idStr.isEmpty() || nombre.isEmpty() || clave.isEmpty() || tipo.isEmpty()) {
                            throw new IllegalArgumentException("Todos los campos son obligatorios.");
                        }
                        
                        // Validacion campos completos
                        int id = Integer.parseInt(idStr);
                        if (id <= 0) throw new IllegalArgumentException("ID de usuario inválido.");

                        if (nombre.length() > 50 || clave.length() > 50 || tipo.length() > 20) {
                            throw new IllegalArgumentException("Los campos exceden la longitud permitida.");
                        }

                        Usuario modificado = new Usuario();
                        modificado.setIdUsuario(id);
                        modificado.setNombreUsuario(nombre);
                        modificado.setClave(clave);
                        modificado.setTipoUsuario(tipo);

                        boolean exito = usuNegocio.modificar(modificado);
                        if (!exito) {
                            request.setAttribute("error", "No se pudo modificar el usuario.");
                        }

                    } catch (Exception e) {
                        request.setAttribute("error", "Error al modificar usuario: " + e.getMessage());
                    }
                    break;

                default:
                    break;
            }
        }

        response.sendRedirect("ServletUsuario?accion=listar");
    }
}
