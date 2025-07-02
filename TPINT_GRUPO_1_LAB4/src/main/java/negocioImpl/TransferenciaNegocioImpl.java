package negocioImpl;

import dao.*;
import daoImpl.*;
import entidad.*;
import excepciones.*;
import negocio.TransferenciaNegocio;

import java.math.BigDecimal;
import java.util.Date;

public class TransferenciaNegocioImpl implements TransferenciaNegocio {

    private CuentaDao cuentaDao = new CuentaDaoImpl();
    private TipoMovimientoDao tipoMovimientoDao = new TipoMovimientoDaoImpl();
    private TransferenciaDao transferenciaDao = new TransferenciaDaoImpl();

    @Override
    public boolean transferir(int idCuentaOrigen, String cbuDestino, BigDecimal monto)
            throws SaldoInsuficienteException, CuentaDestinoInvalidaException, CBUNoEncontradoException {

        // Validar monto positivo
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transferencia debe ser mayor a cero.");
        }

        // Obtener cuentas
        Cuenta cuentaOrigen = cuentaDao.obtenerCuentaPorId(idCuentaOrigen);
        Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorCBU(cbuDestino);

        // Validar cuenta destino existente
        if (cuentaDestino == null) {
            throw new CBUNoEncontradoException();
        }

        // Validar que no se transfiera a sí mismo
        if (cuentaDestino.getIdCuenta() == idCuentaOrigen) {
            throw new CuentaDestinoInvalidaException();
        }

        // Validar que ambas cuentas estén activas
        if (!cuentaOrigen.isEstado()) {
            throw new IllegalStateException("La cuenta de origen está inactiva.");
        }

        if (!cuentaDestino.isEstado()) {
            throw new IllegalStateException("La cuenta de destino está inactiva.");
        }

        // Validar saldo suficiente
        if (cuentaOrigen.getSaldo().compareTo(monto) < 0) {
            throw new SaldoInsuficienteException();
        }

        // Obtener tipos de movimiento
        TipoMovimiento tipoSalida = tipoMovimientoDao.obtenerPorNombre("TransferenciaSalida");
        TipoMovimiento tipoEntrada = tipoMovimientoDao.obtenerPorNombre("TransferenciaEntrada");

        if (tipoSalida == null || tipoEntrada == null) {
            throw new IllegalStateException("Los tipos de movimiento no están definidos en la base de datos.");
        }

        // Crear movimientos
        Movimiento movSalida = new Movimiento();
        movSalida.setCuenta(cuentaOrigen);
        movSalida.setTipoMovimiento(tipoSalida);
        movSalida.setFecha(new Date());
        movSalida.setDetalle("Transferencia a cuenta CBU: " + cbuDestino);
        movSalida.setImporte(monto.negate());

        Movimiento movEntrada = new Movimiento();
        movEntrada.setCuenta(cuentaDestino);
        movEntrada.setTipoMovimiento(tipoEntrada);
        movEntrada.setFecha(new Date());
        movEntrada.setDetalle("Transferencia desde cuenta ID: " + idCuentaOrigen);
        movEntrada.setImporte(monto);

        // Actualizar saldos
        boolean exitoSalida = cuentaDao.modificarSaldo(idCuentaOrigen, monto, false); // resta
        boolean exitoEntrada = cuentaDao.modificarSaldo(cuentaDestino.getIdCuenta(), monto, true); // suma

        if (!exitoSalida || !exitoEntrada) {
            throw new IllegalStateException("Error al actualizar los saldos de las cuentas involucradas.");
        }

        // Registrar transferencia y movimientos en BD
        return transferenciaDao.registrarTransferencia(
            movSalida, movEntrada,
            cuentaOrigen.getIdCuenta(),
            cuentaDestino.getIdCuenta()
        );
    }

}
