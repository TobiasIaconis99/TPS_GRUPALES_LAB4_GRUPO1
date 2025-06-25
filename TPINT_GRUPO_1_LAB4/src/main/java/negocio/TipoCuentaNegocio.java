package negocio;

import entidad.TipoCuenta;
import java.util.List;

public interface TipoCuentaNegocio {

    TipoCuenta obtenerTipoCuentaPorId(int id);
    List<TipoCuenta> listar();
}
