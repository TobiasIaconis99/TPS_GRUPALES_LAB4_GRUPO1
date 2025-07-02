package negocioImpl;

import dao.*;
import daoImpl.*;
import entidad.*;
import excepciones.*;
import negocio.TransferenciaNegocio;

import java.math.BigDecimal;
import java.util.Date;

public class TransferenciaNegocioImpl implements TransferenciaNegocio {

    private MovimientoDao movimientoDao = new MovimientoDaoImpl();
    private CuentaDao cuentaDao = new CuentaDaoImpl();
    private TipoMovimientoDao tipoMovimientoDao = new TipoMovimientoDaoImpl();
    private TransferenciaDao transferenciaDao = new TransferenciaDaoImpl();

    @Override
    public boolean transferir(int idCuentaOrigen, String cbuDestino, BigDecimal monto)
        throws SaldoInsuficienteException, CuentaDestinoInvalidaException, CBUNoEncontradoException {

        Cuenta cuentaOrigen = cuentaDao.obtenerCuentaPorId(idCuentaOrigen);
        Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorCBU(cbuDestino);

        if (cuentaDestino == null) throw new CBUNoEncontradoException();
        if (cuentaDestino.getIdCuenta() == idCuentaOrigen) throw new CuentaDestinoInvalidaException();
        if (cuentaOrigen.getSaldo().compareTo(monto) < 0) throw new SaldoInsuficienteException();

        // Crear movimientos
        Movimiento movSalida = new Movimiento();
        movSalida.setCuenta(cuentaOrigen);
        movSalida.setTipoMovimiento(tipoMovimientoDao.obtenerPorNombre("TransferenciaSalida"));
        movSalida.setFecha(new Date());
        movSalida.setDetalle("Transferencia a cuenta CBU: " + cbuDestino);
        movSalida.setImporte(monto.negate());
        boolean exitoSalida=cuentaDao.modificarSaldo(idCuentaOrigen, monto, false);

        Movimiento movEntrada = new Movimiento();
        movEntrada.setCuenta(cuentaDestino);
        movEntrada.setTipoMovimiento(tipoMovimientoDao.obtenerPorNombre("TransferenciaEntrada"));
        movEntrada.setFecha(new Date());
        movEntrada.setDetalle("Transferencia desde cuenta ID: " + idCuentaOrigen);
        movEntrada.setImporte(monto);
        boolean exitoEntrada=cuentaDao.modificarSaldo(cuentaDestino.getIdCuenta(), monto, true);

        // Ahora que tenemos los objetos, delegamos la persistencia al DAO
        return transferenciaDao.registrarTransferencia(movSalida, movEntrada, idCuentaOrigen, cuentaDestino.getIdCuenta());
    }
}
