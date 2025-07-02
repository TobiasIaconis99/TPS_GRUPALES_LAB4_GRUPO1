package daoImpl;

import dao.GestorConexionBD;
import dao.MovimientoDao;
import dao.TransferenciaDao;
import entidad.Movimiento;
import entidad.Transferencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransferenciaDaoImpl implements TransferenciaDao {

    private static final String INSERT_TRANSFERENCIA =
        "INSERT INTO Transferencia (IDCuentaOrigen, IDCuentaDestino, IDMovimientoSalida, IDMovimientoEntrada) VALUES (?, ?, ?, ?)";

    private MovimientoDao movimientoDao = new MovimientoDaoImpl();

    @Override
    public boolean registrarTransferencia(Movimiento salida, Movimiento entrada, int idCuentaOrigen, int idCuentaDestino) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = GestorConexionBD.getConnection();
            conn.setAutoCommit(false);

            int idSalida = ((MovimientoDaoImpl) movimientoDao).agregarYDevolverId(salida);
            int idEntrada = ((MovimientoDaoImpl) movimientoDao).agregarYDevolverId(entrada);

            
            
            Transferencia transferencia = new Transferencia();
            transferencia.setIdCuentaOrigen(idCuentaOrigen);
            transferencia.setIdCuentaDestino(idCuentaDestino);
            transferencia.setIdMovimientoSalida(idSalida);
            transferencia.setIdMovimientoEntrada(idEntrada);

            stmt = conn.prepareStatement(INSERT_TRANSFERENCIA);
            stmt.setInt(1, transferencia.getIdCuentaOrigen());
            stmt.setInt(2, transferencia.getIdCuentaDestino());
            stmt.setInt(3, transferencia.getIdMovimientoSalida());
            stmt.setInt(4, transferencia.getIdMovimientoEntrada());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;

        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
