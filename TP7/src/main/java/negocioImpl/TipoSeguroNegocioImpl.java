package negocioImpl;

import java.util.ArrayList;

import dao.TipoSeguroDao;
import daoImpl.TipoSeguroDaoImpl;
import entidad.TipoSeguro;
import negocio.TipoSeguroNegocio;

public class TipoSeguroNegocioImpl implements TipoSeguroNegocio{
	
	private TipoSeguroDao tsDao = new TipoSeguroDaoImpl();

	@Override
	public ArrayList<TipoSeguro> readAll() {
		return tsDao.readAll();
	}

}
