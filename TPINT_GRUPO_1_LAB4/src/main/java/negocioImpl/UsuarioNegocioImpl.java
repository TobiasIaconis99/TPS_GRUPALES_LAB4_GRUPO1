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
        return dao.loguear(nombreUsuario, clave);
    }

	@Override
	public List<Usuario> listar() {
		return dao.listar();
	}

	@Override
	public boolean agregar(Usuario usuario) {
		return false;
	}

	@Override
	public boolean modificar(Usuario u) {
		return dao.modificar(u);
	}

	@Override
	public boolean eliminar(int idUsuario) {
		return dao.eliminar(idUsuario);
	}
}