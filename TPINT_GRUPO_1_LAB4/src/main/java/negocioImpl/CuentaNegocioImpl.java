package negocioImpl;

import dao.CuentaDao;
import daoImpl.CuentaDaoImpl;
import entidad.Cuenta;
import negocio.CuentaNegocio;
import java.util.List;


public class CuentaNegocioImpl implements CuentaNegocio {

    private CuentaDao cuentaDao;

    public CuentaNegocioImpl() {
        this.cuentaDao = new CuentaDaoImpl();
    }

    @Override
    public boolean agregar(Cuenta cuenta) {
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
}