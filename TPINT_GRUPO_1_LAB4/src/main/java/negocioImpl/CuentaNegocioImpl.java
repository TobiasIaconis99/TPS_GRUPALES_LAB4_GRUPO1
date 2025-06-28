package negocioImpl;

import dao.CuentaDao;
import daoImpl.CuentaDaoImpl;
import entidad.Cuenta;
import negocio.CuentaNegocio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CuentaNegocioImpl implements CuentaNegocio {

    private CuentaDao cuentaDao;

    public CuentaNegocioImpl() {
        this.cuentaDao = new CuentaDaoImpl();
    }

    @Override
    public boolean agregar(Cuenta cuenta) {
        // 1. Validación de negocio: Verificar si el cliente ya tiene 3 cuentas activas
        if (!tieneMenos3CuentasAct(cuenta.getCliente().getId())) { // Usamos el nuevo método de negocio
            System.out.println("CuentaNegocioImpl: El cliente ya tiene 3 o más cuentas activas. No se puede agregar.");
            return false;
        }

        // 2. Generar numero de cuenta y CBU (Delegamos al DAO para que haga la consulta de max+1)
        cuenta.setNumeroCuenta(cuentaDao.generarSiguienteNumeroCuenta());
        cuenta.setCbu(cuentaDao.generarSiguienteCBU());
        
        // 3. Asignar valores por defecto que son parte de la lógica de negocio al crear una cuenta
        cuenta.setSaldo(new BigDecimal("10000.00")); // Saldo inicial
        cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now())); // Fecha actual
        cuenta.setEstado(true); // Cuenta activa por defecto

        // 4. Llamar al DAO para persistir la cuenta
        System.out.println("CuentaNegocioImpl: Intentando agregar cuenta con Nro: " + cuenta.getNumeroCuenta() + ", CBU: " + cuenta.getCbu() + ", Saldo: " + cuenta.getSaldo());
        return cuentaDao.agregar(cuenta);
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
}