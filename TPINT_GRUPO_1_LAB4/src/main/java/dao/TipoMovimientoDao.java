package dao;

import java.util.List;
import entidad.TipoMovimiento;

public interface TipoMovimientoDao {
    TipoMovimiento obtenerPorId(int id);
    TipoMovimiento obtenerPorNombre(String nombre);
    List<TipoMovimiento> listar();
}