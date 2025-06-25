package daoImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.GestorConexionBD;
import dao.LocalidadDao;
import entidad.Localidad;
import entidad.Provincia;

public class LocalidadDaoImpl implements LocalidadDao {

    private Connection getConnection() throws SQLException {
        return GestorConexionBD.getConnection();
    }

    @Override
    public List<Localidad> listar() {
        List<Localidad> lista = new ArrayList<>();
        String query = "SELECT l.idLocalidad, l.nombreLocalidad, l.estado, p.idProvincia, p.nombreProvincia, p.estado as estadoProvincia " +
                       "FROM Localidad l " +
                       "JOIN Provincia p ON l.idProvincia = p.idProvincia " +
                       "WHERE l.estado = 1";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Provincia provincia = new Provincia(
                    rs.getInt("idProvincia"),
                    rs.getString("nombreProvincia"),
                    rs.getBoolean("estadoProvincia")
                );

                Localidad localidad = new Localidad(
                    rs.getInt("idLocalidad"),
                    rs.getString("nombreLocalidad"),
                    provincia
                );

                lista.add(localidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Localidad> obtenerPorProvincia(int idProvincia) {
        List<Localidad> lista = new ArrayList<>();
        String query = "SELECT l.idLocalidad, l.nombreLocalidad, l.estado, p.idProvincia, p.nombreProvincia, p.estado as estadoProvincia " +
                       "FROM Localidad l " +
                       "JOIN Provincia p ON l.idProvincia = p.idProvincia " +
                       "WHERE l.estado = 1 AND p.idProvincia = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idProvincia);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Provincia provincia = new Provincia(
                    rs.getInt("idProvincia"),
                    rs.getString("nombreProvincia"),
                    rs.getBoolean("estadoProvincia")
                );

                Localidad localidad = new Localidad(
                    rs.getInt("idLocalidad"),
                    rs.getString("nombreLocalidad"),
                    provincia
                );

                lista.add(localidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Localidad obtenerPorId(int id) {
        Localidad localidad = null;
        String query = "SELECT l.idLocalidad, l.nombreLocalidad, l.estado, p.idProvincia, p.nombreProvincia, p.estado as estadoProvincia " +
                       "FROM Localidad l " +
                       "JOIN Provincia p ON l.idProvincia = p.idProvincia " +
                       "WHERE l.idLocalidad = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Provincia provincia = new Provincia(
                    rs.getInt("idProvincia"),
                    rs.getString("nombreProvincia"),
                    rs.getBoolean("estadoProvincia")
                );

                localidad = new Localidad(
                    rs.getInt("idLocalidad"),
                    rs.getString("nombreLocalidad"),
                    provincia
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return localidad;
    }
}