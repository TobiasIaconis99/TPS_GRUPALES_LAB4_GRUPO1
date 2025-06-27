package negocio;

import entidad.Cuenta;
import java.util.List;

public interface CuentaNegocio {
    boolean agregar(Cuenta cuenta);
    boolean modificar(Cuenta cuenta);
    boolean eliminar(int id);
    
    // NUEVO MÉTODO PARA EL NEGOCIO:
    boolean tieneMenos3CuentasAct(int idCliente); // Agregamos este método aquí
    
    List<Cuenta> listarCuentas();
    List<Cuenta> listarCuentasActivas();
    
    boolean existeNumeroCuenta(String numeroCuenta);
    boolean existeCbu(String cbu);
    
    Cuenta obtenerCuentaPorId(int id);
    Cuenta obtenerCuentaPorNumero(String numeroCuenta);
}