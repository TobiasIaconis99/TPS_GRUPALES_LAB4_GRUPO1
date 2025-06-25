package dao;

import entidad.TipoCuenta;
import java.util.List;

public interface TipoCuentaDao {
	
    List<TipoCuenta> listar();
    TipoCuenta obtenerTipoCuentaPorId(int id);
}