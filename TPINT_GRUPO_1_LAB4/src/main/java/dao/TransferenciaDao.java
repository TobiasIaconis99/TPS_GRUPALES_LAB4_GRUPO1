package dao;

import entidad.Movimiento;
import entidad.Transferencia;
import java.sql.Connection;

public interface TransferenciaDao {
    boolean registrarTransferencia(Movimiento salida, Movimiento entrada, int idCuentaOrigen, int idCuentaDestino);

}
