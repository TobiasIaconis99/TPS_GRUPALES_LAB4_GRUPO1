package negocioImpl;

import java.util.List;

import dao.UsuarioDao;
import daoImpl.UsuarioDaoImpl;
import entidad.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {

    private UsuarioDao dao = new UsuarioDaoImpl();

    @Override
    public Usuario loguear(String nombreUsuario, String clave) {
        if (nombreUsuario == null || clave == null ||
            nombreUsuario.trim().isEmpty() || clave.trim().isEmpty()) {
            return null;
        }
        return dao.loguear(nombreUsuario.trim(), clave.trim());
    }

    @Override
    public List<Usuario> listar() {
        return dao.listar();
    }

    @Override
    public boolean agregar(Usuario usuario) {
        if (!validarUsuario(usuario, true)) {
            return false;
        }
        return dao.agregar(usuario);
    }

    @Override
    public boolean modificar(Usuario u) {
        if (!validarUsuario(u, false)) {
            return false;
        }
        return dao.modificar(u);
    }

    @Override
    public boolean eliminar(int idUsuario) {
        return idUsuario > 0 && dao.eliminar(idUsuario);
    }

    @Override
    public List<Usuario> obtenerPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return null;
        }
        return dao.obtenerPorTipo(tipo.trim());
    }

    @Override
    public List<Usuario> buscarConFiltros(String nombre, String tipo, int offset, int cantidad) {
        if (offset < 0 || cantidad <= 0) {
            return null;
        }
        nombre = (nombre != null) ? nombre.trim() : "";
        tipo = (tipo != null) ? tipo.trim() : "";
        return dao.buscarConFiltros(nombre, tipo, offset, cantidad);
    }

    @Override
    public int contarConFiltros(String nombre, String tipo) {
        nombre = (nombre != null) ? nombre.trim() : "";
        tipo = (tipo != null) ? tipo.trim() : "";
        return dao.contarConFiltros(nombre, tipo);
    }

    /**
     * Valida un objeto Usuario.
     * 
     * @param usuario El usuario a validar.
     * @param validarId Si es true, se valida también el ID (por ejemplo, para modificar).
     * @return true si es válido, false si hay errores.
     */
    private boolean validarUsuario(Usuario usuario, boolean validarId) {
        if (usuario == null) return false;

        if (validarId && usuario.getIdUsuario() <= 0) return false;

        String nombre = usuario.getNombreUsuario();
        String clave = usuario.getClave();
        String tipo = usuario.getTipoUsuario();

        if (nombre == null || clave == null || tipo == null) return false;
        if (nombre.trim().isEmpty() || clave.trim().isEmpty() || tipo.trim().isEmpty()) return false;
        if (nombre.length() > 50 || clave.length() > 50 || tipo.length() > 20) return false;

        return true;
    }
}