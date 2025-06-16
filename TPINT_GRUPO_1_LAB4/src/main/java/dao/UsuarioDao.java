package dao;

import entidad.Usuario;

public interface UsuarioDao {
	public Usuario login(String nombreUsuario, String clave);
	public String host = "jdbc:mysql://localhost:3306/";
	public String user = "root";
	public String pass = "root";
	public String dbName = "bdbanco";
}