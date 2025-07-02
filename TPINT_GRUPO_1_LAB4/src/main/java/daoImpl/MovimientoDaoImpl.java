package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.CuentaDao;
import dao.GestorConexionBD;
import dao.MovimientoDao;
import dao.TipoMovimientoDao;
import entidad.Movimiento;

public class MovimientoDaoImpl implements MovimientoDao {

    private CuentaDao cuentaDao;
    private TipoMovimientoDao tipoMovimientoDao;

    public MovimientoDaoImpl() {
        this.cuentaDao = new CuentaDaoImpl(); // Asegúrate de que CuentaDaoImpl exista y sea accesible
        this.tipoMovimientoDao = new TipoMovimientoDaoImpl(); // Asegúrate de que TipoMovimientoDaoImpl exista y sea accesible
    }

    private static final String AGREGAR_MOVIMIENTO = "INSERT INTO Movimiento (idCuenta, idTipoMovimiento, fecha, detalle, importe) VALUES (?, ?, ?, ?, ?)";
    private static final String OBTENER_MOVIMIENTO_POR_ID = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idMovimiento = ?";
    private static final String OBTENER_MOVIMIENTOS_POR_CUENTA = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idCuenta = ? ORDER BY fecha DESC, idMovimiento DESC";
    private static final String OBTENER_MOVIMIENTOS_POR_TIPOMOVIMIENTO = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idTipoMovimiento = ? ORDER BY fecha DESC, idMovimiento DESC";
    private static final String OBTENER_TODOS_LOS_MOVIMIENTOS = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento ORDER BY fecha DESC, idMovimiento DESC";
    private static final String OBTENER_MOVIMIENTOS_POR_CUENTA_PAGINADO = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idCuenta = ? ORDER BY fecha DESC, idMovimiento DESC LIMIT ? OFFSET ?";
    private static final String CONTAR_MOVIMIENTOS_POR_CUENTA = "SELECT COUNT(*) FROM Movimiento WHERE idCuenta = ?";

    
    @Override
    public boolean agregar(Movimiento movimiento) {
        Connection conexion = null;
        PreparedStatement statement = null;
        boolean exito = false;
        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(AGREGAR_MOVIMIENTO, Statement.RETURN_GENERATED_KEYS);
            
            // Asegúrate de que los objetos Cuenta y TipoMovimiento no sean null
            if (movimiento.getCuenta() == null || movimiento.getTipoMovimiento() == null) {
                System.err.println("ERROR (DAO): Cuenta o TipoMovimiento son nulos en el objeto Movimiento.");
                return false;
            }
            statement.setInt(1, movimiento.getCuenta().getIdCuenta()); // Usar el ID del objeto Cuenta
            statement.setInt(2, movimiento.getTipoMovimiento().getIdTipoMovimiento()); // Usar el ID del objeto TipoMovimiento
            
            statement.setDate(3, new java.sql.Date(movimiento.getFecha().getTime())); // Convertir java.util.Date a java.sql.Date
            statement.setString(4, movimiento.getDetalle());
            statement.setBigDecimal(5, movimiento.getImporte());

            int filasAfectadas = statement.executeUpdate();
            exito = filasAfectadas > 0;

            if (exito) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    movimiento.setIdMovimiento(generatedKeys.getInt(1));
                }
                generatedKeys.close();
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al agregar movimiento: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, null);
        }
        return exito;
    }

    @Override
    public Movimiento obtenerPorId(int id) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Movimiento movimiento = null;

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_MOVIMIENTO_POR_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movimiento = new Movimiento();
                movimiento.setIdMovimiento(resultSet.getInt("idMovimiento"));
                
                // CRÍTICO: Hidratar objetos relacionados
                int idCuenta = resultSet.getInt("idCuenta");
                movimiento.setCuenta(cuentaDao.obtenerCuentaPorId(idCuenta)); // Obtener objeto Cuenta completo
                
                int idTipoMovimiento = resultSet.getInt("idTipoMovimiento");
                movimiento.setTipoMovimiento(tipoMovimientoDao.obtenerPorId(idTipoMovimiento)); // Obtener objeto TipoMovimiento completo

                movimiento.setFecha(resultSet.getDate("fecha")); // Directamente a java.util.Date
                movimiento.setDetalle(resultSet.getString("detalle"));
                movimiento.setImporte(resultSet.getBigDecimal("importe"));
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener movimiento por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return movimiento;
    }

    @Override
    public List<Movimiento> obtenerPorCuenta(int idCuenta) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Movimiento> lista = new ArrayList<>();

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_MOVIMIENTOS_POR_CUENTA);
            statement.setInt(1, idCuenta);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setIdMovimiento(resultSet.getInt("idMovimiento"));
                
                // CRÍTICO: Hidratar objetos relacionados
                movimiento.setCuenta(cuentaDao.obtenerCuentaPorId(resultSet.getInt("idCuenta")));
                movimiento.setTipoMovimiento(tipoMovimientoDao.obtenerPorId(resultSet.getInt("idTipoMovimiento")));
                
                movimiento.setFecha(resultSet.getDate("fecha")); // Directamente a java.util.Date
                movimiento.setDetalle(resultSet.getString("detalle"));
                movimiento.setImporte(resultSet.getBigDecimal("importe"));
                lista.add(movimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener movimientos por cuenta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return lista;
    }

    @Override
    public List<Movimiento> obtenerPorTipoMovimiento(int idTipoMovimiento) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Movimiento> lista = new ArrayList<>();

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_MOVIMIENTOS_POR_TIPOMOVIMIENTO);
            statement.setInt(1, idTipoMovimiento);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setIdMovimiento(resultSet.getInt("idMovimiento"));
                
                // CRÍTICO: Hidratar objetos relacionados
                movimiento.setCuenta(cuentaDao.obtenerCuentaPorId(resultSet.getInt("idCuenta")));
                movimiento.setTipoMovimiento(tipoMovimientoDao.obtenerPorId(resultSet.getInt("idTipoMovimiento")));
                
                movimiento.setFecha(resultSet.getDate("fecha")); // Directamente a java.util.Date
                movimiento.setDetalle(resultSet.getString("detalle"));
                movimiento.setImporte(resultSet.getBigDecimal("importe"));
                lista.add(movimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener movimientos por tipo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return lista;
    }

    @Override
    public List<Movimiento> obtenerTodosLosMovimientos() {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Movimiento> lista = new ArrayList<>();

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_TODOS_LOS_MOVIMIENTOS);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setIdMovimiento(resultSet.getInt("idMovimiento"));
                
                // CRÍTICO: Hidratar objetos relacionados
                movimiento.setCuenta(cuentaDao.obtenerCuentaPorId(resultSet.getInt("idCuenta")));
                movimiento.setTipoMovimiento(tipoMovimientoDao.obtenerPorId(resultSet.getInt("idTipoMovimiento")));
                
                movimiento.setFecha(resultSet.getDate("fecha")); // Directamente a java.util.Date
                movimiento.setDetalle(resultSet.getString("detalle"));
                movimiento.setImporte(resultSet.getBigDecimal("importe"));
                lista.add(movimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los movimientos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return lista;
    }
    
    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaPaginado(int idCuenta, int offset, int limit) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Movimiento> lista = new ArrayList<>();

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_MOVIMIENTOS_POR_CUENTA_PAGINADO);
            statement.setInt(1, idCuenta);
            statement.setInt(2, limit); // LIMIT: cuántos registros devolver
            statement.setInt(3, offset); // OFFSET: cuántos registros saltar
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setIdMovimiento(resultSet.getInt("idMovimiento"));
                
                // Hidratar objetos relacionados: ¡CRÍTICO para evitar NullPointerExceptions!
                // Asumo que cuentaDao y tipoMovimientoDao están inicializados en el constructor
                movimiento.setCuenta(cuentaDao.obtenerCuentaPorId(resultSet.getInt("idCuenta")));
                movimiento.setTipoMovimiento(tipoMovimientoDao.obtenerPorId(resultSet.getInt("idTipoMovimiento")));
                
                movimiento.setFecha(resultSet.getDate("fecha"));
                movimiento.setDetalle(resultSet.getString("detalle"));
                movimiento.setImporte(resultSet.getBigDecimal("importe"));
                lista.add(movimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener movimientos por cuenta paginado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return lista;
    }

    @Override
    public int contarMovimientosPorCuenta(int idCuenta) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(CONTAR_MOVIMIENTOS_POR_CUENTA);
            statement.setInt(1, idCuenta);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1); // El resultado de COUNT(*) es la primera columna
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al contar movimientos por cuenta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return count;
    }
    
    @Override
    public int agregarYDevolverId(Movimiento movimiento) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        int idGenerado = -1;

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(AGREGAR_MOVIMIENTO, Statement.RETURN_GENERATED_KEYS);

            if (movimiento.getCuenta() == null || movimiento.getTipoMovimiento() == null) {
                System.err.println("ERROR (DAO): Cuenta o TipoMovimiento son nulos en el objeto Movimiento.");
                return -1;
            }

            statement.setInt(1, movimiento.getCuenta().getIdCuenta());
            statement.setInt(2, movimiento.getTipoMovimiento().getIdTipoMovimiento());
            statement.setDate(3, new java.sql.Date(movimiento.getFecha().getTime()));
            statement.setString(4, movimiento.getDetalle());
            statement.setBigDecimal(5, movimiento.getImporte());

            int filasAfectadas = statement.executeUpdate();

            if (filasAfectadas > 0) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1);
                    movimiento.setIdMovimiento(idGenerado);
                }
            }

        } catch (SQLException e) {
            System.err.println("ERROR (DAO): al agregar movimiento y devolver ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, generatedKeys);
        }

        return idGenerado;
    }
}