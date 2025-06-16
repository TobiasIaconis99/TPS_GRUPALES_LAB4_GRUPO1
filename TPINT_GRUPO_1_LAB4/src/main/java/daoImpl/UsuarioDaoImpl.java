package daoImpl;

import java.sql.*;
import dao.UsuarioDao;
import entidad.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

    private Connection getConnection() throws SQLException {
        
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return DriverManager.getConnection(host + dbName, user, pass);
    }

    @Override
    public Usuario login(String nombreUsuario, String clave) {
    	
        Usuario usuario = null;
        String sql = "SELECT * FROM Usuario WHERE nombreUsuario = ? AND clave = ? AND estado = 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setClave(rs.getString("clave"));
                usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                usuario.setEstado(rs.getBoolean("estado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}