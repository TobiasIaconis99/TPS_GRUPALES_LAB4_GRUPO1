package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dao.GestorConexionBD;
import dao.PrestamoDao;
import entidad.Prestamo;
import entidad.Usuario;

public class PrestamoDaoImpl implements PrestamoDao {
	
	private Connection getConnection() throws SQLException {
		return GestorConexionBD.getConnection();
	}
	
   
    @Override
    public boolean agregar(Prestamo p) {
        String query = "INSERT INTO Prestamos (idCliente, idCuenta, fechaAlta, montoSolicitado, plazoMeses, cantidadCuotas, montoCuota, estado) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean agregado = false;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, p.getIdCliente());
            ps.setInt(2, p.getIdCuenta());
            ps.setString(3, p.getFechaAlta());
            ps.setBigDecimal(4, p.getMontoSolicitado());
            ps.setInt(5, p.getPlazoMeses());
            ps.setInt(6, p.getCantidadCuotas());
            ps.setBigDecimal(7, p.getMontoCuota());
            ps.setInt(8, p.getEstado());

            agregado = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agregado;
    }


	@Override
	public List<Prestamo> listar() {
		// TODO Auto-generated method stub
		return null;
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
