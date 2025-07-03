package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.GestorConexionBD;
import dao.PrestamoDao;
import entidad.Prestamo;
import entidad.Usuario;

public class PrestamoDaoImpl implements PrestamoDao {

	private Connection getConnection() throws SQLException {
		return GestorConexionBD.getConnection();
	}

	private static final String INSERT_PRESTAMO = 
		    "INSERT INTO Prestamo (idCliente, idCuenta, fechaAlta, importePedido, plazoMeses, cantidadCuotas, montoCuota, estado) " +
		    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";



	@Override
	public boolean agregar(Prestamo p) {
	    boolean agregado = false;

	    try (Connection conn = getConnection(); 
	         PreparedStatement ps = conn.prepareStatement(INSERT_PRESTAMO)) {

	        ps.setInt(1, p.getIdCliente()); // idCliente
	        ps.setInt(2, p.getIdCuenta());  // idCuenta
	        ps.setDate(3, p.getFechaAlta()); // fechaAlta
	        ps.setBigDecimal(4, p.getImportePedido()); // importePedido
	        ps.setInt(5, p.getPlazoMeses()); // plazoMeses
	        ps.setInt(6, p.getCantidadCuotas()); // cantidadCuotas
	        ps.setBigDecimal(7, p.getMontoCuota()); // montoCuota
	        ps.setString(8, p.getEstado()); // estado ENUM ('Pendiente', 'Aprobado', 'Rechazado')

	        agregado = ps.executeUpdate() > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return agregado;
	}


	@Override
	public List<Prestamo> listar() {
	    List<Prestamo> prestamos = new ArrayList<>();

	    String sql = "SELECT idPrestamo, idCliente, idCuenta, fechaAlta, importePedido, plazoMeses, cantidadCuotas, montoCuota, estado FROM Prestamo";

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


	@Override
	public boolean modificar(Prestamo u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int idPrestamo) {
		// TODO Auto-generated method stub
		return false;
	}

}
