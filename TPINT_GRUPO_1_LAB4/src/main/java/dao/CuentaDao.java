package dao;

import entidad.Cuenta;
import java.util.List;

public interface CuentaDao {
    boolean agregar(Cuenta cuenta);
    boolean modificar(Cuenta cuenta);
    boolean eliminar(int id);
    
    int contarCuentasActivasPorCliente(int idCliente);
    String generarSiguienteNumeroCuenta();
    String generarSiguienteCBU();
    
    List<Cuenta> listarCuentas();
    List<Cuenta> listarCuentasActivas();
    List<Cuenta> listarCuentasPorCliente(int idCliente);
    
    boolean existeNumeroCuenta(String numeroCuenta);
    boolean existeCbu(String cbu);
    
    Cuenta obtenerCuentaPorId(int id);
    Cuenta obtenerCuentaPorNumero(String numeroCuenta);
}