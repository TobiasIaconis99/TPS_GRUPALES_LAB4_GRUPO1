package negocio;

import java.util.List;
import entidad.TipoMovimiento;

public interface TipoMovimientoNegocio {

    TipoMovimiento obtenerPorId(int id);
    TipoMovimiento obtenerPorNombre(String nombre);
    List<TipoMovimiento> listar();
}