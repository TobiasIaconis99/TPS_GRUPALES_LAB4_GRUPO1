package filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Usuario;

@WebFilter("/*") 
public class FiltroSeguridad implements Filter {

    public FiltroSeguridad() { }
    public void destroy() { }
    public void init(FilterConfig fConfig) throws ServletException { }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // No crear una nueva sesion si no existe

        String path = req.getRequestURI().substring(req.getContextPath().length()); // Obtiene la ruta

        // 1. Rutas para cualquiera
        if (path.equals("/Login.jsp") || 
            path.equals("/ServletLogin") || 
            path.equals("/ServletCerrarSesion") ||
            path.startsWith("/css/") || 
            path.startsWith("/js/") ||
            path.startsWith("/images/") ||
            path.startsWith("/resources/")) {
            
            chain.doFilter(request, response);
            return;
        }

        // 2. Verificar si hay un usuario logueado
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            res.sendRedirect(req.getContextPath() + "/Login.jsp"); // Si no hay una sesion o usuario logueado se rtedirige a la pagina login
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        // 3. Control de acceso por tipo de usuario
        // Rutas de admin
        boolean isAdminPage = path.startsWith("/ABML") ||
                path.startsWith("/admin/") ||
                path.equals("/InicioAdmin.jsp") ||
                path.equals("/AgregarCliente.jsp") ||
                path.equals("/ModificarCliente.jsp") ||
                path.endsWith("NavbarAdmin.jsp") ||
                path.endsWith("SidebarAdmin.jsp") ||
                path.equals("/ServletCliente") ||
                path.equals("/ServletCuenta") ||
                path.equals("/ServletUsuario") ||
                path.equals("/ServletTipoCuenta") ||
                path.equals("/ServletProvincia") ||
                path.equals("/ServletLocalidad");

        // Rutas de cliente
        boolean isClientPage = path.startsWith("/cliente/") || 
                               path.equals("/DatosPersonales.jsp") || 
                               path.equals("/InicioCliente.jsp") ||
                               path.equals("/Movimientos.jsp") ||
                               path.equals("/Transferencias.jsp") ||
                               path.equals("/Prestamos.jsp") ||
                               path.endsWith("NavbarCliente.jsp"); 

        if (usuario.getTipoUsuario().equals("admin")) {
            if (isClientPage) {
                // Admin intentando acceder a una pagina de cliente
                res.sendRedirect(req.getContextPath() + "/InicioAdmin.jsp"); // Redirige a su inicio
                return;
            }
        } else if (usuario.getTipoUsuario().equals("cliente")) {
            if (isAdminPage) {
                // Cliente intentando acceder a una pagina de admin
                res.sendRedirect(req.getContextPath() + "/InicioCliente.jsp"); // Redirige a su inicio
                return;
            }
        } else {
            // Tipo de usuario desconocido
            session.invalidate(); // Invalida la sesión por seguridad
            res.sendRedirect(req.getContextPath() + "/Login.jsp"); // Redirige al login
            return;
        }
        
        // Si no se redirigio permite el acceso a la pagina
        chain.doFilter(request, response);
    }
}