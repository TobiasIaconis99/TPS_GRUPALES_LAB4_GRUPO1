package daoImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.GestorConexionBD;
//import dao.PrestamoDao;
import dao.AutorizacionPrestamoDao;
import entidad.Prestamo;

public class AutorizacionPrestamoDaoImpl implements AutorizacionPrestamoDao {

	private Connection getConnection() throws SQLException {
		return GestorConexionBD.getConnection();
	}

	private static final String INSERT_PRESTAMO = 
		    "INSERT INTO Prestamo (idCliente, idCuenta, fechaAlta, importePedido, plazoMeses, cantidadCuotas, montoCuota, estado) " +
		    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SELECT_PRESTAMOS = 
		    "SELECT idPrestamo, idCliente, idCuenta, fechaAlta, importePedido, plazoMeses, cantidadCuotas, "
		    + "montoCuota, estado FROM Prestamo WHERE ESTADO = ?";

	private static final String INSERT_CUOTA = 
		    "INSERT INTO Cuota (idPrestamo, numeroCuota, monto, fechaPago, pagada) VALUES (?, ?, ?, ?, ?)";



	public boolean modificarEstado(int idPrestamo, int idEstado) {
    boolean modificado = false;

    String sqlUpdate = "UPDATE Prestamo SET estado = ? WHERE idPrestamo = ?";

    try (Connection conn = getConnection();
         PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {

        // actualizar estado
        psUpdate.setInt(1, idEstado);
        psUpdate.setInt(2, idPrestamo);

        int rowsAffected = psUpdate.executeUpdate();

        if (rowsAffected > 0) {
            modificado = true;

            // si el nuevo estado es Aprobado genera cuotas
            if (idEstado == 2) {  // 2 = Aprobado

                // obtener datos del préstamo
                String sqlSelect = "SELECT cantidadCuotas, montoCuota FROM Prestamo WHERE idPrestamo = ?";
                try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {
                    psSelect.setInt(1, idPrestamo);

                    try (ResultSet rs = psSelect.executeQuery()) {
                        if (rs.next()) {
                            int cantidadCuotas = rs.getInt("cantidadCuotas");
                            BigDecimal montoCuota = rs.getBigDecimal("montoCuota");

                            // creo las cuotas
                            try (PreparedStatement psInsertCuota = conn.prepareStatement(INSERT_CUOTA)) {
                                for (int i = 1; i <= cantidadCuotas; i++) {
                                    psInsertCuota.setInt(1, idPrestamo); 
                                    psInsertCuota.setInt(2, i); // numeroCuota
                                    psInsertCuota.setBigDecimal(3, montoCuota); 
                                    psInsertCuota.setNull(4, java.sql.Types.DATE); 
                                    psInsertCuota.setBoolean(5, false); 

                                    psInsertCuota.addBatch(); 
                                }
                                psInsertCuota.executeBatch();
                            }
                        }
                    }
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return modificado;
}

	@Override
	public List<Prestamo> listarPrestamosPendientes() {
	    List<Prestamo> prestamos = new ArrayList<>();

	    String sql = "SELECT idPrestamo, idCliente, idCuenta, fechaAlta, importePedido, plazoMeses, "
	    		+ "cantidadCuotas, montoCuota, estado FROM Prestamo where estado = 1";

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Prestamo p = new Prestamo();

	            p.setIdPrestamo(rs.getInt("idPrestamo"));
	            p.setIdCliente(rs.getInt("idCliente"));
	            p.setIdCuenta(rs.getInt("idCuenta"));
	            p.setFechaAlta(rs.getDate("fechaAlta"));
	            p.setImportePedido(rs.getBigDecimal("importePedido"));
	            p.setPlazoMeses(rs.getInt("plazoMeses"));
	            p.setCantidadCuotas(rs.getInt("cantidadCuotas"));
	            p.setMontoCuota(rs.getBigDecimal("montoCuota"));
	            p.setEstado(rs.getString("estado")); // 'Pendiente', 'Aprobado', etc.

	            prestamos.add(p);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return prestamos;
	}
	
	public List<Prestamo> listarPrestamosEstadoFiltrado(int idEstado, String nombreCliente) {
	    List<Prestamo> prestamos = new ArrayList<>();

	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT p.idPrestamo, p.idCliente, p.idCuenta, p.fechaAlta, p.importePedido, ")
	       .append("p.plazoMeses, p.cantidadCuotas, p.montoCuota, p.estado ")
	       .append("FROM Prestamo p ");

	    // Si hay nombreCliente, hacemos join
	    if (nombreCliente != null && !nombreCliente.isEmpty()) {
	        sql.append("INNER JOIN Cliente c ON p.idCliente = c.idCliente ");
	    }

	    sql.append("WHERE p.estado = ? ");

	    if (nombreCliente != null && !nombreCliente.isEmpty()) {
	        sql.append("AND c.nombre LIKE ? ");
	    }

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

	        ps.setInt(1, idEstado);

	        if (nombreCliente != null && !nombreCliente.isEmpty()) {
	            ps.setString(2, "%" + nombreCliente + "%");
	        }

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Prestamo p = new Prestamo();
	                p.setIdPrestamo(rs.getInt("idPrestamo"));
	                p.setIdCliente(rs.getInt("idCliente"));
	                p.setIdCuenta(rs.getInt("idCuenta"));
	                p.setFechaAlta(rs.getDate("fechaAlta"));
	                p.setImportePedido(rs.getBigDecimal("importePedido"));
	                p.setPlazoMeses(rs.getInt("plazoMeses"));
	                p.setCantidadCuotas(rs.getInt("cantidadCuotas"));
	                p.setMontoCuota(rs.getBigDecimal("montoCuota"));
	                p.setEstado(rs.getString("estado"));

	                prestamos.add(p);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return prestamos;
	}
}
