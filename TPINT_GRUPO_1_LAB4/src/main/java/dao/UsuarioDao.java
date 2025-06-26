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

}