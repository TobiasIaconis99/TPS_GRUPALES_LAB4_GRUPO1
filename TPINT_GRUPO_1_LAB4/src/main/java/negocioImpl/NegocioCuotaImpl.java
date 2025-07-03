package negocioImpl;

import java.util.List;

import dao.CuotaDao;
import daoImpl.CuotaDaoImpl;
import entidad.Cuota;
import entidad.Usuario;
import negocio.NegocioCuota;

public class NegocioCuotaImpl implements NegocioCuota {
	
	CuotaDao cDao = new CuotaDaoImpl();

	@Override
	public List<Cuota> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean agregar(Usuario u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificar(Usuario u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(int idUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cuota> obtenerCuotasPorIdPrestamo(int idPrestamo) {
		// TODO Auto-generated method stub
		return cDao.obtenerCuotasPorIdPrestamo(idPrestamo);
	}

	@Override
	public boolean pagarCuota(int idCuota) {
		// TODO Auto-generated method stub
		return cDao.pagarCuota(idCuota);
	}

}
