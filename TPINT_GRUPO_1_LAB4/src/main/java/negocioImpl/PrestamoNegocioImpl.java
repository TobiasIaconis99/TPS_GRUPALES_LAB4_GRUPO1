package negocioImpl;

import java.util.List;

import dao.PrestamoDao;
import dao.UsuarioDao;
import daoImpl.PrestamoDaoImpl;
import daoImpl.UsuarioDaoImpl;
import entidad.Prestamo;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImpl implements PrestamoNegocio {

	private PrestamoDao dao = new PrestamoDaoImpl();
	
	@Override
	public List<Prestamo> listar() {
		
		return null;
	}

	@Override
	public boolean agregar(Prestamo p) {

		return dao.agregar(p);
	}

	@Override
	public boolean modificar(Prestamo u) {
		
		return false;
	}

	@Override
	public boolean eliminar(int idPrestamo) {
		
		return false;
	}

	
	
}
