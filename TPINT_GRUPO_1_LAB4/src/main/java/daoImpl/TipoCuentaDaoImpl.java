package daoImpl;

import dao.GestorConexionBD;
import dao.TipoCuentaDao;
import entidad.TipoCuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoCuentaDaoImpl implements TipoCuentaDao {
	
	private Connection getConnection() throws SQLException {
		return GestorConexionBD.getConnection();
	}
	
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos de BD: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static final String SELECT_TIPOCUENTA_BY_ID = "SELECT idTipoCuenta, nombreTipoCuenta, estado FROM TipoCuenta WHERE idTipoCuenta = ?";
    private static final String LIST_ACTIVE_TIPOSCUENTA = "SELECT idTipoCuenta, nombreTipoCuenta, estado FROM TipoCuenta WHERE estado = 1";

    @Override
    public TipoCuenta obtenerTipoCuentaPorId(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        TipoCuenta tipoCuenta = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(SELECT_TIPOCUENTA_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tipoCuenta = new TipoCuenta();
                tipoCuenta.setIdTipoCuenta(resultSet.getInt("idTipoCuenta"));
                tipoCuenta.setNombreTipoCuenta(resultSet.getString("nombreTipoCuenta"));
                tipoCuenta.setEstado(resultSet.getBoolean("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener TipoCuenta por ID: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return tipoCuenta;
    }
    
    @Override
    public List<TipoCuenta> listar() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        List<TipoCuenta> lista = new ArrayList<>();
        try {
            conexion = getConnection();
            statement = conexion.prepareStatement(LIST_ACTIVE_TIPOSCUENTA);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TipoCuenta tipoCuenta = new TipoCuenta();
                tipoCuenta.setIdTipoCuenta(resultSet.getInt("idTipoCuenta"));
                tipoCuenta.setNombreTipoCuenta(resultSet.getString("nombreTipoCuenta"));
                tipoCuenta.setEstado(resultSet.getBoolean("estado"));
                lista.add(tipoCuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al listar TiposCuenta activos: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return lista;
    }
}