package negocioImpl;

import java.math.BigDecimal;
import java.util.List;

import dao.MovimientoDao;
import dao.CuentaDao;
import daoImpl.MovimientoDaoImpl;
import daoImpl.CuentaDaoImpl;
import entidad.Movimiento;
import entidad.Cuenta;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio {

    private MovimientoDao movimientoDao;
    private CuentaDao cuentaDao;

    public MovimientoNegocioImpl() {
        this.movimientoDao = new MovimientoDaoImpl();
        this.cuentaDao = new CuentaDaoImpl();
    }

    @Override
    public boolean agregar(Movimiento movimiento) {
        // Validación de datos básicos
        if (movimiento == null || movimiento.getIdCuenta() <= 0 || movimiento.getIdTipoMovimiento() <= 0 ||
            movimiento.getFecha() == null || movimiento.getImporte() == null || movimiento.getImporte().compareTo(BigDecimal.ZERO) < 0) {
            System.err.println("ERROR (Negocio): Datos de movimiento inválidos para el registro.");
            return false;
        }

        // Lógica de negocio para actualizar el saldo de la cuenta
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(movimiento.getIdCuenta());
        if (cuenta == null || !cuenta.isEstado()) { // Verificar si la cuenta existe y está activa
            System.err.println("ERROR (Negocio): Cuenta no encontrada o inactiva para el movimiento.");
            return false;
        }

        BigDecimal nuevoSaldo = cuenta.getSaldo().add(movimiento.getImporte()); // Suma el importe (positivo o negativo)
        cuenta.setSaldo(nuevoSaldo);

        // Aquí deberías iniciar una transacción para asegurar atomicidad
        // Si la BD soporta transacciones y JDBC, usar:
        // try { conexion.setAutoCommit(false); ... }
        // Y al final: conexion.commit();
        // En caso de error: conexion.rollback();

        boolean exitoMovimiento = movimientoDao.agregar(movimiento);
        boolean exitoActualizacionSaldo = false;
        if (exitoMovimiento) {
             exitoActualizacionSaldo = cuentaDao.modificar(cuenta); // Necesitas un método modificar en ICuentaDao
        }

        // Aquí se gestionaría el commit o rollback de la transacción
        if (exitoMovimiento && exitoActualizacionSaldo) {
            System.out.println("DEBUG (Negocio): Movimiento registrado y saldo de cuenta actualizado con éxito.");
            // Transacción: commit
            return true;
        } else {
            System.err.println("ERROR (Negocio): Fallo al registrar movimiento o actualizar saldo. Realizando rollback (simulado).");
            // Transacción: rollback
            return false;
        }
    }

    @Override
    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoDao.obtenerTodosLosMovimientos();
    }

	
    @Override
	public Movimiento obtenerPorId(int id) {
    	return movimientoDao.obtenerPorId(id);
	}

	
    @Override
	public List<Movimiento> obtenerPorCuenta(int idCuenta) {
    	return movimientoDao.obtenerPorCuenta(idCuenta);
	}

	
    @Override
	public List<Movimiento> obtenerPorTipoMovimiento(int idTipoMovimiento) {
    	return movimientoDao.obtenerPorTipoMovimiento(idTipoMovimiento);
	}

}