package dao;

import modelo.TipoSeguro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguroDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/SegurosGroup", "root", "PabloUces2018");
    }
    
    public List<TipoSeguro> obtenerTipos() {
        List<TipoSeguro> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipoSeguros";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoSeguro tipo = new TipoSeguro(
                    rs.getInt("idTipo"),
                    rs.getString("descripcion")
                );
                lista.add(tipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int obtenerProximoIdSeguro() {
        int id = 1;
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'SegurosGroup' AND TABLE_NAME = 'seguros'";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                id = rs.getInt("AUTO_INCREMENT");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}