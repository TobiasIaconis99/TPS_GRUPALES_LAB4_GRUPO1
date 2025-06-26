package dao;

import entidad.Cuenta;
import java.util.List;

public interface CuentaDao {
    boolean agregar(Cuenta cuenta);
    boolean modificar(Cuenta cuenta);
    boolean eliminar(int id);
    
    List<Cuenta> listarCuentas();
    List<Cuenta> listarCuentasActivas();
    
    boolean existeNumeroCuenta(String numeroCuenta);
    boolean existeCbu(String cbu);
    boolean tieneMenos3CuentasAct(int idCliente);
    
    Cuenta obtenerCuentaPorId(int id);
    Cuenta obtenerCuentaPorNumero(String numeroCuenta);
}