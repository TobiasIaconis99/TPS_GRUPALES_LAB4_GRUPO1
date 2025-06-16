package negocio;

import entidad.Usuario;

public interface UsuarioNegocio {
    public Usuario login(String nombreUsuario, String clave);
}