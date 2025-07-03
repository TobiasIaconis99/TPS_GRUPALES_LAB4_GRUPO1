package negocio;

import entidad.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaNegocio {
    boolean agregar(Cuenta cuenta);
    boolean modificar(Cuenta cuenta);
    boolean eliminar(int id);
    
    boolean tieneMenos3CuentasAct(int idCliente);
    
    List<Cuenta> listarCuentas();
    List<Cuenta> listarCuentasActivas();
    List<Cuenta> listarCuentasPorCliente(int idCliente);
    List<Cuenta> obtenerCuentaPorClienteId(int id);
    
    
    
    
    boolean existeNumeroCuenta(String numeroCuenta);
    boolean existeCbu(String cbu);
    public boolean descontarSaldo(int idCuenta, BigDecimal monto);
    
    Cuenta obtenerCuentaPorId(int id);
    Cuenta obtenerCuentaPorNumero(String numeroCuenta);
}