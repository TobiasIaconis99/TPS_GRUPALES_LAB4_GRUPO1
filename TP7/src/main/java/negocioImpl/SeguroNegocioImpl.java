package negocioImpl;

import java.util.ArrayList;

import dao.SeguroDao;
import dao.TipoSeguroDao;
import daoImpl.SeguroDaoImpl;
import daoImpl.TipoSeguroDaoImpl;
import entidad.Seguro;
import entidad.TipoSeguro;
import negocio.SeguroNegocio;

public class SeguroNegocioImpl implements SeguroNegocio {

	private SeguroDao sDao = new SeguroDaoImpl();
	@Override
	public boolean insert(Seguro seguro) {
		if(seguro.getDescripcion().trim().length()>0 && seguro.getTipoSeguro() != null && 
				seguro.getCostoContratacion() > 0 && seguro.getCostoAsegurado() > 0)
		{
			return sDao.insert(seguro);
		}
		return false;
	}

	@Override
	public boolean update(Seguro seguro) {
		for (Seguro item : this.lista()) {
			if(seguro.getIdSeguro() == item.getIdSeguro()) {
				return sDao.update(seguro);
			}
		}
		return false;
	}

	@Override
	public boolean delete(Seguro seguro) {
		for (Seguro item : this.lista()) {
			if(seguro.getIdSeguro() == item.getIdSeguro()) {
				return sDao.delete(seguro);
			}
		}
		return false;
	}

	@Override
	public ArrayList<Seguro> lista() {
		return sDao.readAll();
	}

}
