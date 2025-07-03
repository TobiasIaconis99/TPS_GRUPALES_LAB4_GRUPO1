package negocioImpl;

import dao.CuentaDao;
import dao.MovimientoDao;
import dao.TipoMovimientoDao;
import daoImpl.CuentaDaoImpl;
import daoImpl.MovimientoDaoImpl;
import daoImpl.TipoMovimientoDaoImpl;
import entidad.Cuenta;
import entidad.Movimiento;
import entidad.TipoMovimiento;
import negocio.CuentaNegocio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CuentaNegocioImpl implements CuentaNegocio {

    private CuentaDao cuentaDao;

    public CuentaNegocioImpl() {
        this.cuentaDao = new CuentaDaoImpl();
    }

    public boolean agregar(Cuenta cuenta) {
        // 1. Validación de negocio
        if (!tieneMenos3CuentasAct(cuenta.getCliente().getId())) {
            System.out.println("CuentaNegocioImpl: El cliente ya tiene 3 o más cuentas activas. No se puede agregar.");
            return false;
        }

        // 2. Generar datos de la cuenta
        cuenta.setNumeroCuenta(cuentaDao.generarSiguienteNumeroCuenta());
        cuenta.setCbu(cuentaDao.generarSiguienteCBU());
        cuenta.setSaldo(new BigDecimal("10000.00")); // Saldo inicial
        cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
        cuenta.setEstado(true);

        // 3. Persistir cuenta
        System.out.println("CuentaNegocioImpl: Intentando agregar cuenta con Nro: " + cuenta.getNumeroCuenta());
        boolean cuentaAgregada = cuentaDao.agregar(cuenta);

        if (!cuentaAgregada) {
            return false;
        }
        
        Cuenta cuentaInsertada = cuentaDao.obtenerCuentaPorCBU(cuenta.getCbu());
        cuenta.setIdCuenta(cuentaInsertada.getIdCuenta());

        // 4. Crear movimiento de tipo AltaCuenta
        TipoMovimientoDao tipoMovimientoDao = new TipoMovimientoDaoImpl();
        MovimientoDao movimientoDao = new MovimientoDaoImpl();

        TipoMovimiento tipoAltaCuenta = tipoMovimientoDao.obtenerPorNombre("AltaCuenta");
        if (tipoAltaCuenta == null) {
            System.err.println("Error: Tipo de movimiento 'AltaCuenta' no definido.");
            return false;
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setTipoMovimiento(tipoAltaCuenta);
        movimiento.setFecha(new Date());
        movimiento.setDetalle("Alta de cuenta nueva");
        movimiento.setImporte(new BigDecimal("10000.00")); // Mismo que el saldo inicial

        boolean movimientoRegistrado = movimientoDao.agregar(movimiento);
        if (!movimientoRegistrado) {
            System.err.println("Error: No se pudo registrar el movimiento de AltaCuenta.");
            return false;
        }

        return true;
    }

    @Override
    public Cuenta obtenerCuentaPorId(int id) {
        return cuentaDao.obtenerCuentaPorId(id);
    }

    @Override
    public boolean modificar(Cuenta cuenta) {
        return cuentaDao.modificar(cuenta);
    }

    @Override
    public boolean eliminar(int id) {
        // Puedes agregar lógica de negocio aquí antes de eliminar,
        // por ejemplo, verificar si el saldo es cero.
        return cuentaDao.eliminar(id);
    }

    @Override
    public List<Cuenta> listarCuentas() {
        return cuentaDao.listarCuentas();
    }

    @Override
    public List<Cuenta> listarCuentasActivas() {
        return cuentaDao.listarCuentasActivas();
    }
    
    @Override
    public boolean existeNumeroCuenta(String numeroCuenta) {
        return cuentaDao.existeNumeroCuenta(numeroCuenta.trim());
    }

    @Override
    public boolean existeCbu(String cbu) {
        return cuentaDao.existeCbu(cbu.trim());
    }
    
    @Override
    public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
        return cuentaDao.obtenerCuentaPorNumero(numeroCuenta.trim());
    }

    // IMPLEMENTACIÓN DEL NUEVO MÉTODO DE NEGOCIO
    @Override
    public boolean tieneMenos3CuentasAct(int idCliente) {
        // La lógica para contar las cuentas activas reside en el DAO,
        // el negocio solo la usa para tomar una decisión.
        return cuentaDao.contarCuentasActivasPorCliente(idCliente) < 3;
    }

    @Override
    public List<Cuenta> listarCuentasPorCliente(int idCliente) {
        return cuentaDao.listarCuentasPorCliente(idCliente);
    }

	@Override
	public List<Cuenta> obtenerCuentaPorClienteId(int id) {
		return cuentaDao.listarCuentasPorCliente(id);
	}
}