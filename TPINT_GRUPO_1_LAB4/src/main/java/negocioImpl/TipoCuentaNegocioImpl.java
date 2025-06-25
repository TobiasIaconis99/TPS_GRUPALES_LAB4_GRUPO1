package negocioImpl;

import dao.TipoCuentaDao;
import daoImpl.TipoCuentaDaoImpl;
import entidad.TipoCuenta;
import negocio.TipoCuentaNegocio;
import java.util.List;

public class TipoCuentaNegocioImpl implements TipoCuentaNegocio {

    private TipoCuentaDao tipoCuentaDao;

    public TipoCuentaNegocioImpl() {
        this.tipoCuentaDao = new TipoCuentaDaoImpl();
    }

    @Override
    public TipoCuenta obtenerTipoCuentaPorId(int id) {
        if (id <= 0) {
            System.err.println("Negocio: ID de tipo de cuenta inválido.");
            return null;
        }
        return tipoCuentaDao.obtenerTipoCuentaPorId(id);
    }

    @Override
    public List<TipoCuenta> listar() {
        return tipoCuentaDao.listar();
    }
}