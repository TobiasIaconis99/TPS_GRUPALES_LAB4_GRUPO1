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
    @Override
    public List<Usuario> obtenerPorTipo(String tipo) {
        List<Usuario> lista = new ArrayList<>();
        String query = "SELECT * FROM Usuario WHERE tipoUsuario = ? AND estado = 1";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, tipo);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombreUsuario(rs.getString("nombreUsuario"));
                    u.setClave(rs.getString("clave"));
                    u.setTipoUsuario(rs.getString("tipoUsuario"));
                    u.setEstado(rs.getBoolean("estado"));
                    lista.add(u);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    @Override
    public List<Usuario> buscarConFiltros(String nombre, String tipo, int offset, int cantidad) {
        List<Usuario> lista = new ArrayList<>();
        String query = "SELECT * FROM Usuario WHERE estado = 1";
        if (nombre != null && !nombre.isEmpty()) query += " AND nombreUsuario LIKE ?";
        if (tipo != null && !tipo.isEmpty()) query += " AND tipoUsuario = ?";
        query += " ORDER BY idUsuario LIMIT ? OFFSET ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            int param = 1;
            if (nombre != null && !nombre.isEmpty()) ps.setString(param++, "%" + nombre + "%");
            if (tipo != null && !tipo.isEmpty()) ps.setString(param++, tipo);
            ps.setInt(param++, cantidad);
            ps.setInt(param, offset);

            ResultSet rs = ps.executeQuery();
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
    public int contarConFiltros(String nombre, String tipo) {
        int total = 0;
        String query = "SELECT COUNT(*) FROM Usuario WHERE estado = 1";
        if (nombre != null && !nombre.isEmpty()) query += " AND nombreUsuario LIKE ?";
        if (tipo != null && !tipo.isEmpty()) query += " AND tipoUsuario = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            int param = 1;
            if (nombre != null && !nombre.isEmpty()) ps.setString(param++, "%" + nombre + "%");
            if (tipo != null && !tipo.isEmpty()) ps.setString(param++, tipo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) total = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public Usuario obtenerPorId(int idUsuario) {
        String query = "SELECT idUsuario, nombreUsuario, clave, tipoUsuario, estado FROM Usuario WHERE idUsuario = ? AND estado = 1";
        Usuario usuario = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUsuario);

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
            System.err.println("Error SQL al obtener usuario por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public Usuario obtenerPorNombreUsuario(String nombreUsuario) {
        String query = "SELECT idUsuario, nombreUsuario, clave, tipoUsuario, estado FROM Usuario WHERE LOWER(nombreUsuario) = LOWER(?) AND estado = 1";
        Usuario usuario = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, nombreUsuario);

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
            System.err.println("Error SQL al obtener usuario por nombre de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }
}