package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.GestorConexionBD;
import dao.MovimientoDao;
import entidad.Informes;
import entidad.Movimiento;
import servlet.ServletInformes;

public class MovimientoDaoImpl implements MovimientoDao {

    private static final String AGREGAR_MOVIMIENTO = "INSERT INTO Movimiento (idCuenta, idTipoMovimiento, fecha, detalle, importe) VALUES (?, ?, ?, ?, ?)";
    private static final String OBTENER_MOVIMIENTO_POR_ID = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idMovimiento = ?";
    private static final String OBTENER_MOVIMIENTOS_POR_CUENTA = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idCuenta = ? ORDER BY fecha DESC, idMovimiento DESC";
    private static final String OBTENER_MOVIMIENTOS_POR_CUENTA_Y_FECHAS = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idCuenta = ? AND fecha BETWEEN ? AND ? ORDER BY fecha DESC, idMovimiento DESC";
    private static final String OBTENER_MOVIMIENTOS_POR_TIPOMOVIMIENTO = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento WHERE idTipoMovimiento = ? ORDER BY fecha DESC, idMovimiento DESC";
    private static final String LISTAR = "SELECT idMovimiento, idCuenta, idTipoMovimiento, fecha, detalle, importe FROM Movimiento ORDER BY fecha DESC, idMovimiento DESC";


    @Override
    public boolean agregar(Movimiento movimiento) {
        Connection conexion = null;
        PreparedStatement statement = null;
        boolean exito = false;
        try {
            conexion = GestorConexionBD.getConnection();
            // Permite obtener las claves generadas automáticamente (AUTO_INCREMENT)
            statement = conexion.prepareStatement(AGREGAR_MOVIMIENTO, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, movimiento.getIdCuenta());
            statement.setInt(2, movimiento.getIdTipoMovimiento());
            statement.setDate(3, Date.valueOf(movimiento.getFecha())); // Convertir LocalDate a java.sql.Date
            statement.setString(4, movimiento.getDetalle());
            statement.setBigDecimal(5, movimiento.getImporte());

            int filasAfectadas = statement.executeUpdate();
            exito = filasAfectadas > 0;

            // Si se insertó correctamente, intentamos obtener el ID generado
            if (exito) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    movimiento.setIdMovimiento(generatedKeys.getInt(1)); // Asignar el ID generado al objeto
                }
                generatedKeys.close();
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al agregar movimiento: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, null); // No hay ResultSet de consulta aquí
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
                movimiento.setIdCuenta(resultSet.getInt("idCuenta"));
                movimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                movimiento.setFecha(resultSet.getDate("fecha").toLocalDate()); // Convertir java.sql.Date a LocalDate
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
                movimiento.setIdCuenta(resultSet.getInt("idCuenta"));
                movimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                movimiento.setFecha(resultSet.getDate("fecha").toLocalDate());
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
    public List<Movimiento> obtenerPorCuentaYFechas(int idCuenta, LocalDate fechaInicio, LocalDate fechaFin) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Movimiento> lista = new ArrayList<>();

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_MOVIMIENTOS_POR_CUENTA_Y_FECHAS);
            statement.setInt(1, idCuenta);
            statement.setDate(2, Date.valueOf(fechaInicio));
            statement.setDate(3, Date.valueOf(fechaFin));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setIdMovimiento(resultSet.getInt("idMovimiento"));
                movimiento.setIdCuenta(resultSet.getInt("idCuenta"));
                movimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                movimiento.setFecha(resultSet.getDate("fecha").toLocalDate());
                movimiento.setDetalle(resultSet.getString("detalle"));
                movimiento.setImporte(resultSet.getBigDecimal("importe"));
                lista.add(movimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener movimientos por cuenta y rango de fechas: " + e.getMessage());
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
                movimiento.setIdCuenta(resultSet.getInt("idCuenta"));
                movimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                movimiento.setFecha(resultSet.getDate("fecha").toLocalDate());
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
    public List<Movimiento> listar() {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Movimiento> lista = new ArrayList<>();

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(LISTAR);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setIdMovimiento(resultSet.getInt("idMovimiento"));
                movimiento.setIdCuenta(resultSet.getInt("idCuenta"));
                movimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                movimiento.setFecha(resultSet.getDate("fecha").toLocalDate());
                movimiento.setDetalle(resultSet.getString("detalle"));
                movimiento.setImporte(resultSet.getBigDecimal("importe"));
                lista.add(movimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener todos los movimientos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return lista;
    }
    public List<Informes> obtenerEstadisticasPorTipoMovimiento() {
    	
        List<Informes> lista = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = "SELECT tm.nombre AS tipoMovimiento, COUNT(*) AS cantidad, SUM(m.importe) AS total FROM movimiento m JOIN tipomovimiento tm ON m.idTipoMovimiento = tm.idTipoMovimiento GROUP BY tm.nombre";

        try {
        	conexion = GestorConexionBD.getConnection(); 
            stmt = conexion.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
            	Informes em = new Informes();
                em.setTipoMovimiento(rs.getString("tipoMovimiento"));
                em.setCantidad(rs.getInt("cantidad"));
                em.setTotal(rs.getBigDecimal("total"));
                lista.add(em);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conexion != null) conexion.close(); } catch (Exception e) {}
        }

        return lista;
    }
}