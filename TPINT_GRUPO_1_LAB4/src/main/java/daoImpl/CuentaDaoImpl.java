package daoImpl;

import dao.CuentaDao;
import dao.ClienteDao; 
import dao.TipoCuentaDao; 
import entidad.Cuenta;
import entidad.Cliente; 
import entidad.TipoCuenta; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.GestorConexionBD;

public class CuentaDaoImpl implements CuentaDao {

    // DAOs para relaciones
    private ClienteDao clienteDao = new ClienteDaoImpl();
    private TipoCuentaDao tipoCuentaDao = new TipoCuentaDaoImpl();

    // Método para obtener la conexión
	private Connection getConnection() throws SQLException {
		return GestorConexionBD.getConnection();
	}
	
	// Método para cerrar los recursos de la base de datos de forma segura
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close(); // Con un pool, esto devuelve la conexión al pool
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos de BD: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static final String INSERT_CUENTA = "INSERT INTO Cuenta (idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_CUENTA_BY_ID = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE idCuenta = ?";
    private static final String SELECT_CUENTA_BY_NUMERO = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE numeroCuenta = ?";
    private static final String UPDATE_CUENTA = "UPDATE Cuenta SET idCliente = ?, idTipoCuenta = ?, numeroCuenta = ?, cbu = ?, saldo = ?, fechaCreacion = ?, estado = ? WHERE idCuenta = ?";
    private static final String DELETE_CUENTA = "UPDATE Cuenta SET estado = 0 WHERE idCuenta = ?"; // Baja lógica
    private static final String LIST_ALL_CUENTAS = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta";
    private static final String LIST_ACTIVE_CUENTAS = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE estado = 1";
    private static final String CHECK_NUMERO_CUENTA_EXISTS = "SELECT COUNT(*) FROM Cuenta WHERE numeroCuenta = ?";
    private static final String CHECK_CBU_EXISTS = "SELECT COUNT(*) FROM Cuenta WHERE cbu = ?";

    @Override
    public boolean agregar(Cuenta cuenta) {
        PreparedStatement statement = null;
        Connection conexion = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(INSERT_CUENTA);
            statement.setInt(1, cuenta.getCliente().getId()); 
            statement.setInt(2, cuenta.getTipoCuenta().getIdTipoCuenta());
            statement.setString(3, cuenta.getNumeroCuenta());
            statement.setString(4, cuenta.getCbu());
            statement.setBigDecimal(5, cuenta.getSaldo());
            statement.setDate(6, cuenta.getFechaCreacion());
            statement.setBoolean(7, cuenta.isEstado());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al agregar Cuenta: " + e.getMessage());
            return false;
        } finally {
            closeResources(conexion, statement, null); // Usar el método closeResources
        }
    }

    @Override
    public Cuenta obtenerCuentaPorId(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        Cuenta cuenta = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(SELECT_CUENTA_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cuenta = new Cuenta();
                cuenta.setIdCuenta(resultSet.getInt("idCuenta"));
                cuenta.setNumeroCuenta(resultSet.getString("numeroCuenta"));
                cuenta.setCbu(resultSet.getString("cbu"));
                cuenta.setSaldo(resultSet.getBigDecimal("saldo"));
                cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
                cuenta.setEstado(resultSet.getBoolean("estado"));

                int idCliente = resultSet.getInt("idCliente");
                Cliente cliente = clienteDao.obtenerPorId(idCliente); 
                cuenta.setCliente(cliente);

                int idTipoCuenta = resultSet.getInt("idTipoCuenta");
                TipoCuenta tipoCuenta = tipoCuentaDao.obtenerTipoCuentaPorId(idTipoCuenta);
                cuenta.setTipoCuenta(tipoCuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener Cuenta por ID: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return cuenta;
    }
    
    @Override
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        Cuenta cuenta = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(SELECT_CUENTA_BY_NUMERO);
            statement.setString(1, numeroCuenta);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cuenta = new Cuenta();
                cuenta.setIdCuenta(resultSet.getInt("idCuenta"));
                cuenta.setNumeroCuenta(resultSet.getString("numeroCuenta"));
                cuenta.setCbu(resultSet.getString("cbu"));
                cuenta.setSaldo(resultSet.getBigDecimal("saldo"));
                cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
                cuenta.setEstado(resultSet.getBoolean("estado"));

                int idCliente = resultSet.getInt("idCliente");
                Cliente cliente = clienteDao.obtenerPorId(idCliente); 
                cuenta.setCliente(cliente);

                int idTipoCuenta = resultSet.getInt("idTipoCuenta");
                TipoCuenta tipoCuenta = tipoCuentaDao.obtenerTipoCuentaPorId(idTipoCuenta);
                cuenta.setTipoCuenta(tipoCuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener Cuenta por número: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return cuenta;
    }

    @Override
    public boolean modificar(Cuenta cuenta) {
        PreparedStatement statement = null;
        Connection conexion = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(UPDATE_CUENTA);
            statement.setInt(1, cuenta.getCliente().getId());
            statement.setInt(2, cuenta.getTipoCuenta().getIdTipoCuenta());
            statement.setString(3, cuenta.getNumeroCuenta());
            statement.setString(4, cuenta.getCbu());
            statement.setBigDecimal(5, cuenta.getSaldo());
            statement.setDate(6, cuenta.getFechaCreacion());
            statement.setBoolean(7, cuenta.isEstado());
            statement.setInt(8, cuenta.getIdCuenta());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al modificar Cuenta: " + e.getMessage());
            return false;
        } finally {
            closeResources(conexion, statement, null); // Usar el método closeResources
        }
    }

    @Override
    public boolean eliminar(int id) {
        PreparedStatement statement = null;
        Connection conexion = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(DELETE_CUENTA);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al eliminar Cuenta (baja lógica): " + e.getMessage());
            return false;
        } finally {
            closeResources(conexion, statement, null); // Usar el método closeResources
        }
    }

    @Override
    public List<Cuenta> listarCuentas() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        List<Cuenta> lista = new ArrayList<>();
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(LIST_ALL_CUENTAS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIdCuenta(resultSet.getInt("idCuenta"));
                cuenta.setNumeroCuenta(resultSet.getString("numeroCuenta"));
                cuenta.setCbu(resultSet.getString("cbu"));
                cuenta.setSaldo(resultSet.getBigDecimal("saldo"));
                cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
                cuenta.setEstado(resultSet.getBoolean("estado"));

                int idCliente = resultSet.getInt("idCliente");
                Cliente cliente = clienteDao.obtenerPorId(idCliente); 
                cuenta.setCliente(cliente);

                int idTipoCuenta = resultSet.getInt("idTipoCuenta");
                TipoCuenta tipoCuenta = tipoCuentaDao.obtenerTipoCuentaPorId(idTipoCuenta);
                cuenta.setTipoCuenta(tipoCuenta);

                lista.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al listar todas las Cuentas: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return lista;
    }

    @Override
    public List<Cuenta> listarCuentasActivas() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        List<Cuenta> lista = new ArrayList<>();
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(LIST_ACTIVE_CUENTAS);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIdCuenta(resultSet.getInt("idCuenta"));
                cuenta.setNumeroCuenta(resultSet.getString("numeroCuenta"));
                cuenta.setCbu(resultSet.getString("cbu"));
                cuenta.setSaldo(resultSet.getBigDecimal("saldo"));
                cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
                cuenta.setEstado(resultSet.getBoolean("estado"));

                int idCliente = resultSet.getInt("idCliente");
                Cliente cliente = clienteDao.obtenerPorId(idCliente); 
                cuenta.setCliente(cliente);

                int idTipoCuenta = resultSet.getInt("idTipoCuenta");
                TipoCuenta tipoCuenta = tipoCuentaDao.obtenerTipoCuentaPorId(idTipoCuenta);
                cuenta.setTipoCuenta(tipoCuenta);

                lista.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al listar Cuentas activas: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return lista;
    }

    @Override
    public boolean existeNumeroCuenta(String numeroCuenta) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(CHECK_NUMERO_CUENTA_EXISTS);
            statement.setString(1, numeroCuenta);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al verificar existencia de número de cuenta: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return false;
    }

    @Override
    public boolean existeCbu(String cbu) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        try {
            conexion = getConnection(); // Usar tu método getConnection()
            statement = conexion.prepareStatement(CHECK_CBU_EXISTS);
            statement.setString(1, cbu);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al verificar existencia de CBU: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet); // Usar el método closeResources
        }
        return false;
    }
}