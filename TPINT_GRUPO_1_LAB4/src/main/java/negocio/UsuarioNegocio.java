package negocio;

import java.util.List;

import entidad.Usuario;

public interface UsuarioNegocio {
    public Usuario loguear(String nombreUsuario, String clave);
    public List<Usuario> listar();
    public boolean agregar(Usuario u);
    public boolean modificar(Usuario u);
    public boolean eliminar(int idUsuario);
    public List<Usuario> obtenerPorTipo(String tipo);

}