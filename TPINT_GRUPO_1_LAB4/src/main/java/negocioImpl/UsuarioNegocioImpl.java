package negocioImpl;

import dao.UsuarioDao;
import daoImpl.UsuarioDaoImpl;
import entidad.Usuario;
import negocio.UsuarioNegocio;

public class UsuarioNegocioImpl implements UsuarioNegocio {

    private UsuarioDao dao = new UsuarioDaoImpl();

    @Override
    public Usuario login(String nombreUsuario, String clave) {
        return dao.login(nombreUsuario, clave);
    }
}