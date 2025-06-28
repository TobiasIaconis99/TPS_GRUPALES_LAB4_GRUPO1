package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.GestorConexionBD;
import dao.TipoMovimientoDao;
import entidad.TipoMovimiento;

public class TipoMovimientoDaoImpl implements TipoMovimientoDao {

    private static final String OBTENER_POR_ID = "SELECT idTipoMovimiento, nombre FROM TipoMovimiento WHERE idTipoMovimiento = ?";
    private static final String OBTENER_POR_NOMBRE = "SELECT idTipoMovimiento, nombre FROM TipoMovimiento WHERE nombre = ?";
    private static final String LISTAR = "SELECT idTipoMovimiento, nombre FROM TipoMovimiento";

    @Override
    public TipoMovimiento obtenerPorId(int id) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        TipoMovimiento tipoMovimiento = null;

        try {
            // Obtenemos la conexión desde el Gestor
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_POR_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tipoMovimiento = new TipoMovimiento();
                tipoMovimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                tipoMovimiento.setNombre(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener TipoMovimiento por ID: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) { // Capturamos cualquier otra excepción
            System.err.println("Error inesperado al obtener TipoMovimiento por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerramos los recursos usando el método centralizado en GestorConexionBD
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return tipoMovimiento;
    }

    @Override
    public TipoMovimiento obtenerPorNombre(String nombre) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        TipoMovimiento tipoMovimiento = null;

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(OBTENER_POR_NOMBRE);
            statement.setString(1, nombre);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                tipoMovimiento = new TipoMovimiento();
                tipoMovimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                tipoMovimiento.setNombre(resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener TipoMovimiento por nombre: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al obtener TipoMovimiento por nombre: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return tipoMovimiento;
    }

    @Override
    public List<TipoMovimiento> listar() {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<TipoMovimiento> lista = new ArrayList<>();

        try {
            conexion = GestorConexionBD.getConnection();
            statement = conexion.prepareStatement(LISTAR);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TipoMovimiento tipoMovimiento = new TipoMovimiento();
                tipoMovimiento.setIdTipoMovimiento(resultSet.getInt("idTipoMovimiento"));
                tipoMovimiento.setNombre(resultSet.getString("nombre"));
                lista.add(tipoMovimiento);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener todos los TipoMovimiento: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al obtener todos los TipoMovimiento: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conexion, statement, resultSet);
        }
        return lista;
    }
}