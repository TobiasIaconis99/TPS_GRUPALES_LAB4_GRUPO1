package negocioImpl;

import java.util.List;

import dao.TipoMovimientoDao;
import daoImpl.TipoMovimientoDaoImpl;
import entidad.TipoMovimiento;
import negocio.TipoMovimientoNegocio;

public class TipoMovimientoNegocioImpl implements TipoMovimientoNegocio {

    private TipoMovimientoDao tipoMovimientoDao;

    public TipoMovimientoNegocioImpl() {
        this.tipoMovimientoDao = new TipoMovimientoDaoImpl();
    }

    @Override
    public TipoMovimiento obtenerPorId(int id) {
        if (id <= 0) {
            System.out.println("El id de tipo de movimiento es invalido para su busqueda: " + id);
            return null;
        }
        return tipoMovimientoDao.obtenerPorId(id);
    }

    @Override
    public TipoMovimiento obtenerPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("El nombre del tipo de movimiento no puede estar vacio para su busqueda.");
            return null;
        }
        return tipoMovimientoDao.obtenerPorNombre(nombre.trim());
    }

    @Override
    public List<TipoMovimiento> listar() {
        return tipoMovimientoDao.listar();
    }
}