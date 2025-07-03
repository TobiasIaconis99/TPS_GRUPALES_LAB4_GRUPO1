package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.CuotaDao;
import dao.GestorConexionBD;
import entidad.Cuota;

public class CuotaDaoImpl implements CuotaDao {

    private static final String SELECT_CUOTAS_BY_ID_PRESTAMO =
        "SELECT idCuota, idPrestamo, numeroCuota, monto, fechaPago, pagada " +
        "FROM cuota WHERE idPrestamo = ? ORDER BY numeroCuota ASC";
    private static final String UPDATE_CUOTA_PAGAR =
    	    "UPDATE cuota SET pagada = 1 WHERE idCuota = ?";

    @Override
    public List<Cuota> obtenerCuotasPorIdPrestamo(int idPrestamo) {
        List<Cuota> cuotas = new ArrayList<>();

        try (Connection conn = GestorConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CUOTAS_BY_ID_PRESTAMO)) {
            
            ps.setInt(1, idPrestamo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cuota c = new Cuota();
                    c.setIdCuota(rs.getInt("idCuota"));
                    c.setIdPrestamo(rs.getInt("idPrestamo"));
                    c.setNumeroCuota(rs.getInt("numeroCuota"));
                    c.setMonto(rs.getBigDecimal("monto"));
                    c.setFechaPago(rs.getDate("fechaPago"));
                    c.setPagada(rs.getBoolean("pagada")); 
                    
                    cuotas.add(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cuotas;
    }

    // Métodos sin implementar correctamente aún:
    @Override
    public List<Cuota> listar() {
        return new ArrayList<>();
    }

    @Override
    public boolean agregar(entidad.Usuario u) {
        return false;
    }

    @Override
    public boolean modificar(entidad.Usuario u) {
        return false;
    }

    @Override
    public boolean eliminar(int idUsuario) {
        return false;
    }


    
    @Override
    public boolean pagarCuota(int idCuota) {
        int seActualizo = 0;
        try (Connection conn = GestorConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_CUOTA_PAGAR)) {

            ps.setInt(1, idCuota); 
            seActualizo = ps.executeUpdate(); 

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return seActualizo > 0; 
    }
}
