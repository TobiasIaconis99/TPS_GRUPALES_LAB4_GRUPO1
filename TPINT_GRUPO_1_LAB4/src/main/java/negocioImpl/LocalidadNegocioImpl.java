package negocioImpl;

import java.util.List;

import dao.LocalidadDao;
import daoImpl.LocalidadDaoImpl;
import entidad.Localidad;
import negocio.LocalidadNegocio;

public class LocalidadNegocioImpl implements LocalidadNegocio {

    private LocalidadDao daoLocalidad = new LocalidadDaoImpl();

    @Override
    public List<Localidad> listar() {
        return daoLocalidad.listar();
    }

    @Override
    public List<Localidad> obtenerPorProvincia(int idProvincia) {
        return daoLocalidad.obtenerPorProvincia(idProvincia);
    }

    @Override
    public Localidad obtenerPorId(int id) {
        return daoLocalidad.obtenerPorId(id);
    }
}