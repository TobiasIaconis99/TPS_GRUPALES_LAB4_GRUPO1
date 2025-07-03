package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CuotaDao;
import dao.GestorConexionBD;
import entidad.Cuota;

public class CuotaDaoImpl implements CuotaDao {

    private static final String SELECT_CUOTAS_BY_ID_PRESTAMO =
        "SELECT idCuota, idPrestamo, numeroCuota, monto, fechaPago, pagada " +
        "FROM cuota WHERE idPrestamo = ? ORDER BY numeroCuota ASC";

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
                    c.setPagada(rs.getBoolean("pagada")); // BIT(1) → getBoolean
                    
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
}
