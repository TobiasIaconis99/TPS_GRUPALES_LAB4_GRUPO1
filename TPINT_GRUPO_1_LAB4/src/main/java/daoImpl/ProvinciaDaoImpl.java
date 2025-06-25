package daoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.GestorConexionBD;
import dao.ProvinciaDao;
import entidad.Provincia;

public class ProvinciaDaoImpl implements ProvinciaDao {

    private Connection getConnection() throws SQLException {
        return GestorConexionBD.getConnection();
    }

    @Override
    public List<Provincia> listar() {
        List<Provincia> provincias = new ArrayList<>();
        String query = "SELECT * FROM Provincia WHERE estado = 1";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Provincia p = new Provincia();
                p.setIdProvincia(rs.getInt("idProvincia"));
                p.setNombreProvincia(rs.getString("nombreProvincia"));
                p.setEstado(rs.getBoolean("estado"));
                provincias.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return provincias;
    }

    @Override
    public Provincia obtenerPorId(int id) {
        Provincia provincia = null;
        String query = "SELECT * FROM Provincia WHERE idProvincia = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                provincia = new Provincia();
                provincia.setIdProvincia(rs.getInt("idProvincia"));
                provincia.setNombreProvincia(rs.getString("nombreProvincia"));
                provincia.setEstado(rs.getBoolean("estado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return provincia;
    }
}