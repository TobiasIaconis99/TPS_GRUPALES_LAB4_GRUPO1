package dao;

import java.util.List;
import entidad.Usuario;

public interface UsuarioDao {
	
    Usuario loguear(String nombreUsuario, String clave);
    List<Usuario> listar();
    boolean agregar(Usuario u);
    boolean modificar(Usuario u);
    boolean eliminar(int idUsuario);
    public List<Usuario> obtenerPorTipo(String tipo);
    public List<Usuario> buscarConFiltros(String nombre, String tipo, int offset, int cantidad);
    public int contarConFiltros(String nombre, String tipo);


}