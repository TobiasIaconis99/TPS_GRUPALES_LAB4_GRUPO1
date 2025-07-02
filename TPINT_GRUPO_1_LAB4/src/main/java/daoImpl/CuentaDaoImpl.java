package daoImpl;

import dao.CuentaDao;
import dao.ClienteDao;
import dao.TipoCuentaDao;
import entidad.Cuenta;
import entidad.Cliente;
import entidad.TipoCuenta;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    // Constantes SQL
    private static final String INSERT_CUENTA = "INSERT INTO Cuenta (idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CUENTA = "UPDATE Cuenta SET idCliente = ?, idTipoCuenta = ?, numeroCuenta = ?, cbu = ?, saldo = ?, fechaCreacion = ?, estado = ? WHERE idCuenta = ?";
    private static final String DELETE_CUENTA = "UPDATE Cuenta SET estado = 0 WHERE idCuenta = ?"; // Baja lógica
   
    private static final String SELECT_CUENTA_BY_ID = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE idCuenta = ?";
    private static final String SELECT_CUENTA_BY_NUMERO = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE numeroCuenta = ?";
    
    private static final String LIST_ALL_CUENTAS = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta";
    private static final String LIST_ACTIVE_CUENTAS = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE estado = 1";
    private static final String LIST_CUENTAS_BY_CLIENTE_ID = "SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE idCliente = ? AND estado = 1"; 
    
    private static final String CHECK_NUMERO_CUENTA_EXISTS = "SELECT COUNT(*) FROM Cuenta WHERE numeroCuenta = ?";
    private static final String CHECK_CBU_EXISTS = "SELECT COUNT(*) FROM Cuenta WHERE cbu = ?";
    private static final String SELECT_LAST_NUMERO_CUENTA = "SELECT MAX(numeroCuenta) FROM Cuenta";
    private static final String SELECT_LAST_CBU = "SELECT MAX(cbu) FROM Cuenta";
    private static final String COUNT_ACTIVE_CUENTAS_BY_CLIENTE_ID = "SELECT COUNT(*) FROM Cuenta WHERE estado = 1 AND idCliente = ?";

    @Override
    public boolean agregar(Cuenta cuenta) {
        PreparedStatement statement = null;
        Connection conexion = null;
        boolean isSuccess = false;
        try {
            conexion = getConnection();
            statement = conexion.prepareStatement(INSERT_CUENTA);
            statement.setInt(1, cuenta.getCliente().getId());
            statement.setInt(2, cuenta.getTipoCuenta().getIdTipoCuenta());
            statement.setString(3, cuenta.getNumeroCuenta()); // Se espera que ya venga generado desde la capa de Negocio
            statement.setString(4, cuenta.getCbu());         // Se espera que ya venga generado desde la capa de Negocio
            statement.setBigDecimal(5, cuenta.getSaldo()); // Se espera que el saldo inicial ya venga desde la capa de Negocio
            statement.setDate(6, cuenta.getFechaCreacion()); // Se espera que la fecha ya venga desde la capa de Negocio
            statement.setBoolean(7, cuenta.isEstado());

            System.out.println("CuentaDaoImpl: Preparando INSERT. Cliente ID: " + cuenta.getCliente().getId()
                    + ", Tipo ID: " + cuenta.getTipoCuenta().getIdTipoCuenta() + ", Nro Cuenta: "
                    + cuenta.getNumeroCuenta() + ", CBU: " + cuenta.getCbu() + ", Saldo: " + cuenta.getSaldo()); // Debug

            int filasAfectadas = statement.executeUpdate();
            isSuccess = (filasAfectadas > 0);
            System.out.println("CuentaDaoImpl: Filas afectadas = " + filasAfectadas + ", Resultado: " + isSuccess); // Debug

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al agregar cuenta: " + e.getMessage());
            // Considerar relanzar una excepción personalizada o devolver un código de error más específico
        } finally {
            closeResources(conexion, statement, null);
        }
        return isSuccess;
    }

    @Override
    public Cuenta obtenerCuentaPorId(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        Cuenta cuenta = null;
        try {
            conexion = getConnection();
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
            closeResources(conexion, statement, resultSet);
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
            conexion = getConnection();
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
            closeResources(conexion, statement, resultSet);
        }
        return cuenta;
    }

    @Override
    public boolean modificar(Cuenta cuenta) {
        PreparedStatement statement = null;
        Connection conexion = null;
        try {
            conexion = getConnection();
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
            closeResources(conexion, statement, null);
        }
    }

    @Override
    public boolean eliminar(int id) {
        PreparedStatement statement = null;
        Connection conexion = null;
        try {
            conexion = getConnection();
            statement = conexion.prepareStatement(DELETE_CUENTA);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al eliminar Cuenta (baja lógica): " + e.getMessage());
            return false;
        } finally {
            closeResources(conexion, statement, null);
        }
    }

    @Override
    public List<Cuenta> listarCuentas() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        List<Cuenta> lista = new ArrayList<>();
        try {
            conexion = getConnection();
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
            closeResources(conexion, statement, resultSet);
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
            conexion = getConnection();
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
                Cliente cliente = clienteDao.obtenerPorId(idCliente); // Obtener el cliente completo
                cuenta.setCliente(cliente);

                int idTipoCuenta = resultSet.getInt("idTipoCuenta");
                TipoCuenta tipoCuenta = tipoCuentaDao.obtenerTipoCuentaPorId(idTipoCuenta);
                cuenta.setTipoCuenta(tipoCuenta);

                // Aquí puedes agregar una condición adicional si solo quieres listar cuentas
                // de clientes que también estén activos, si esa es tu lógica de negocio.
                // if (cliente != null && cliente.isEstado()) {
                     lista.add(cuenta);
                // }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al listar Cuentas activas: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet);
        }
        return lista;
    }

    @Override
    public boolean existeNumeroCuenta(String numeroCuenta) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        try {
            conexion = getConnection();
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
            closeResources(conexion, statement, resultSet);
        }
        return false;
    }

    @Override
    public boolean existeCbu(String cbu) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        try {
            conexion = getConnection();
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
            closeResources(conexion, statement, resultSet);
        }
        return false;
    }

    @Override
    public int contarCuentasActivasPorCliente(int idCliente) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        int count = 0;
        try {
            conexion = getConnection();
            statement = conexion.prepareStatement(COUNT_ACTIVE_CUENTAS_BY_CLIENTE_ID);
            statement.setInt(1, idCliente);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al contar cuentas activas por cliente: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet);
        }
        return count;
    }

    @Override
    public String generarSiguienteNumeroCuenta() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String ultimoNumero = null;
        long siguienteNumero = 1;

        try {
            conn = getConnection(); // Obtener la conexión dentro del método
            ps = conn.prepareStatement(SELECT_LAST_NUMERO_CUENTA);
            rs = ps.executeQuery();
            if (rs.next() && rs.getString(1) != null) {
                ultimoNumero = rs.getString(1);
                try {
                    siguienteNumero = Long.parseLong(ultimoNumero) + 1;
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear ultimoNumeroCuenta a long: " + ultimoNumero + ". Generando desde 1.");
                    siguienteNumero = 1; // Fallback
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al generar siguiente número de cuenta: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs); // Cerrar recursos aquí también
        }
        return String.format("%010d", siguienteNumero);
    }

    @Override
    public String generarSiguienteCBU() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String ultimoCBUString = null;
        BigInteger siguienteCBU = null;
        BigInteger valorInicialCBU = new BigInteger("10000000000000000000"); // Asegura que este sea un valor inicial adecuado

        try {
            conn = getConnection(); // Obtener la conexión dentro del método
            ps = conn.prepareStatement(SELECT_LAST_CBU);
            rs = ps.executeQuery();

            if (rs.next() && rs.getString(1) != null) {
                ultimoCBUString = rs.getString(1);
                try {
                    BigInteger cbuActual = new BigInteger(ultimoCBUString);
                    siguienteCBU = cbuActual.add(BigInteger.ONE);
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear ultimoCBU a BigInteger: " + ultimoCBUString + ". Generando desde valor inicial.");
                    siguienteCBU = valorInicialCBU;
                }
            } else {
                siguienteCBU = valorInicialCBU;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al generar siguiente CBU: " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs); // Cerrar recursos aquí también
        }
        return String.format("%022d", siguienteCBU); // Formato para 22 dígitos
    }

    @Override
    public List<Cuenta> listarCuentasPorCliente(int idCliente) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        List<Cuenta> lista = new ArrayList<>();
        try {
            conexion = getConnection();
            statement = conexion.prepareStatement(LIST_CUENTAS_BY_CLIENTE_ID);
            statement.setInt(1, idCliente); // Pasar el ID del cliente al PreparedStatement
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setIdCuenta(resultSet.getInt("idCuenta"));
                cuenta.setNumeroCuenta(resultSet.getString("numeroCuenta"));
                cuenta.setCbu(resultSet.getString("cbu"));
                cuenta.setSaldo(resultSet.getBigDecimal("saldo"));
                cuenta.setFechaCreacion(resultSet.getDate("fechaCreacion"));
                cuenta.setEstado(resultSet.getBoolean("estado"));

                // El cliente ya lo tenemos, lo pasamos directamente o lo buscamos
                // Aquí, el cliente ya está asociado al ID, así que lo obtenemos.
                // Podrías pasar el objeto cliente directamente si lo tuvieras,
                // pero obtenerlo por ID aquí asegura que el objeto Cliente sea completo.
                Cliente cliente = clienteDao.obtenerPorId(resultSet.getInt("idCliente")); 
                cuenta.setCliente(cliente);

                int idTipoCuenta = resultSet.getInt("idTipoCuenta");
                TipoCuenta tipoCuenta = tipoCuentaDao.obtenerTipoCuentaPorId(idTipoCuenta);
                cuenta.setTipoCuenta(tipoCuenta);

                lista.add(cuenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error en capa DAO al listar Cuentas por Cliente ID: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet);
        }
        return lista;
    }
    @Override
    public Cuenta obtenerCuentaPorCBU(String cbu) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conexion = null;
        Cuenta cuenta = null;

        try {
            conexion = getConnection();
            statement = conexion.prepareStatement("SELECT idCuenta, idCliente, idTipoCuenta, numeroCuenta, cbu, saldo, fechaCreacion, estado FROM Cuenta WHERE cbu = ? AND estado = 1");
            statement.setString(1, cbu);
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
                cuenta.setCliente(clienteDao.obtenerPorId(idCliente));

                int idTipoCuenta = resultSet.getInt("idTipoCuenta");
                cuenta.setTipoCuenta(tipoCuentaDao.obtenerTipoCuentaPorId(idTipoCuenta));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener cuenta por CBU: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, resultSet);
        }

        return cuenta;
    }
    @Override
    public boolean modificarSaldo(int idCuenta, BigDecimal monto, boolean sumar) {
        PreparedStatement statement = null;
        Connection conexion = null;
        boolean exito = false;
        //idCuenta: ID de la cuenta a modificar.

        //monto: el valor a sumar o restar.

        //sumar: true para sumar, false para restar.
        final String SQL = "UPDATE Cuenta SET saldo = saldo + ? WHERE idCuenta = ? AND estado = 1";

        try {
            conexion = getConnection();
            statement = conexion.prepareStatement(SQL);

            // Si es resta, convertimos el monto a negativo
            BigDecimal montoAjustado = sumar ? monto : monto.negate();
            statement.setBigDecimal(1, montoAjustado);
            statement.setInt(2, idCuenta);

            exito = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al modificar el saldo de la cuenta: " + e.getMessage());
        } finally {
            closeResources(conexion, statement, null);
        }

        return exito;
    }
}