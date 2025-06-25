package negocioImpl;

import java.util.List;

import dao.ProvinciaDao;
import daoImpl.ProvinciaDaoImpl;
import entidad.Provincia;
import negocio.ProvinciaNegocio;

public class ProvinciaNegocioImpl implements ProvinciaNegocio {

    private ProvinciaDao provinciaDao = new ProvinciaDaoImpl();

    @Override
    public List<Provincia> listar() {
        return provinciaDao.listar();
    }

    @Override
    public Provincia obtenerPorId(int id) {
        return provinciaDao.obtenerPorId(id);
    }
}