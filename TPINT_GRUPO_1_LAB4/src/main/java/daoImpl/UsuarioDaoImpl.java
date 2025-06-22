package daoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.GestorConexionBD;
import dao.UsuarioDao;
import entidad.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {

	
	private Connection getConnection() throws SQLException {
		return GestorConexionBD.getConnection();
	}

    @Override
    public Usuario loguear(String nombreUsuario, String clave) {
        String query = "SELECT * FROM Usuario WHERE nombreUsuario = ? AND clave = ? AND estado = 1";
        Usuario usuario = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                    usuario.setEstado(rs.getBoolean("estado"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String query = "SELECT * FROM Usuario WHERE estado = 1";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombreUsuario(rs.getString("nombreUsuario"));
                u.setClave(rs.getString("clave"));
                u.setTipoUsuario(rs.getString("tipoUsuario"));
                u.setEstado(rs.getBoolean("estado"));
                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public boolean agregar(Usuario u) {
        String query = "INSERT INTO Usuario (nombreUsuario, clave, tipoUsuario, estado) VALUES (?, ?, ?, 1)";
        boolean agregado = false;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getTipoUsuario());

            agregado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agregado;
    }

    @Override
    public boolean modificar(Usuario u) {
        String query = "UPDATE Usuario SET nombreUsuario = ?, clave = ?, tipoUsuario = ? WHERE idUsuario = ?";
        boolean actualizado = false;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getClave());
            ps.setString(3, u.getTipoUsuario());
            ps.setInt(4, u.getIdUsuario());

            actualizado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actualizado;
    }

    @Override
    public boolean eliminar(int idUsuario) {
        String query = "UPDATE Usuario SET estado = 0 WHERE idUsuario = ?";
        boolean eliminado = false;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUsuario);
            eliminado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }
}