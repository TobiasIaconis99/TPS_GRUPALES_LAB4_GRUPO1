package negocio; // Asegúrate de que el paquete sea correcto

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import entidad.Movimiento;

public interface MovimientoNegocio {
    /**
     * Registra un nuevo movimiento en la base de datos.
     * Esta es una operación crítica que podría incluir lógica para actualizar saldos de cuentas.
     * @param movimiento El objeto Movimiento a registrar.
     * @return true si el movimiento se registró exitosamente, false en caso contrario.
     */
    boolean registrarMovimiento(Movimiento movimiento);

    /**
     * Obtiene los movimientos de una cuenta para un período específico.
     * @param idCuenta El ID de la cuenta.
     * @param fechaInicio La fecha de inicio del período.
     * @param fechaFin La fecha de fin del período.
     * @return Una lista de movimientos filtrados.
     */
    List<Movimiento> obtenerMovimientosPorCuentaYFechas(int idCuenta, LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Obtiene un resumen de todos los movimientos (puede ser para un administrador).
     * @return Una lista de todos los movimientos.
     */
    List<Movimiento> obtenerTodosLosMovimientos();

    /**
     * Realiza una transferencia de dinero entre dos cuentas.
     * Esta es una operación compleja que involucra múltiples movimientos y actualización de saldos.
     * @param idCuentaOrigen ID de la cuenta que envía el dinero.
     * @param idCuentaDestino ID de la cuenta que recibe el dinero.
     * @param importe El monto a transferir.
     * @param detalle Un detalle para la transferencia.
     * @return true si la transferencia fue exitosa, false en caso contrario (ej. saldo insuficiente).
     */
    boolean realizarTransferencia(int idCuentaOrigen, int idCuentaDestino, BigDecimal importe, String detalle);

}