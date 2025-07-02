package negocioImpl;

import java.util.List;

import dao.UsuarioDao;
import daoImpl.UsuarioDaoImpl;
import entidad.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {

    private UsuarioDao dao = new UsuarioDaoImpl();

    @Override
    public Usuario loguear(String nombreUsuarioInput, String claveInput) {
        // 1. Validaciones básicas de entrada
        if (nombreUsuarioInput == null || claveInput == null ||
            nombreUsuarioInput.trim().isEmpty() || claveInput.trim().isEmpty()) {
            return null;
        }

        // 2. Normalizar las entradas (quitar espacios al inicio/final)
        String usuarioTrimmed = nombreUsuarioInput.trim();
        String claveTrimmed = claveInput.trim();

        // 3. Buscar el usuario por nombre de usuario (sin la clave aún)
        Usuario usuarioEnDB = dao.obtenerPorNombreUsuario(usuarioTrimmed);

        // 4. Realizar la validación de las credenciales en la capa de negocio
        if (usuarioEnDB != null && usuarioEnDB.isEstado()) {
            // Comparamos el nombre de usuario y la clave de forma STRICTA (case-sensitive)
            // con lo que se obtuvo de la base de datos.
            // Si la DB es case-insensitive, usuarioEnDB.getNombreUsuario() podría ser "admin"
            // mientras que usuarioTrimmed es "ADMIN". La igualdad debe ser exacta.
            if (usuarioEnDB.getNombreUsuario().equals(usuarioTrimmed) &&
                usuarioEnDB.getClave().equals(claveTrimmed)) {
                return usuarioEnDB;
            }
        }
        
        // Si llegamos aquí, el usuario no existe, no está activo, o las credenciales no coinciden exactamente
        return null; 
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

        Usuario usuarioOriginal = dao.obtenerPorId(u.getIdUsuario());
        if (usuarioOriginal == null) {
            System.err.println("ERROR NEGOCIO (Modificar): Usuario con ID " + u.getIdUsuario() + " no encontrado para modificar.");
            return false;
        }

        // Normalizar el nuevo nombre de usuario
        String nuevoNombreUsuario = u.getNombreUsuario().trim();
        u.setNombreUsuario(nuevoNombreUsuario); 

        // Verificar si el nombre de usuario ha sido modificado y si el nuevo nombre ya existe
        if (!nuevoNombreUsuario.equalsIgnoreCase(usuarioOriginal.getNombreUsuario())) {
            Usuario usuarioExistenteConMismoNombre = dao.obtenerPorNombreUsuario(nuevoNombreUsuario);
            if (usuarioExistenteConMismoNombre != null && usuarioExistenteConMismoNombre.getIdUsuario() != u.getIdUsuario()) {
                System.err.println("ERROR NEGOCIO (Modificar): El nombre de usuario '" + nuevoNombreUsuario + "' ya existe para otro usuario.");
                return false;
            }
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