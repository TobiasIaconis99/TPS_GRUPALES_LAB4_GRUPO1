package negocioImpl;

import java.util.List;

import dao.ClienteDao;
import daoImpl.ClienteDaoImpl;
import entidad.Cliente;
import entidad.Provincia;
import entidad.Localidad;
import negocio.ClienteNegocio;

public class ClienteNegocioImpl implements ClienteNegocio{

	private ClienteDao clienteDao = new ClienteDaoImpl();
	
	@Override
	public List<Cliente> listar() {
		return clienteDao.listar();
	}

	@Override
	public boolean agregar(Cliente c) {

		return clienteDao.agregar(c);
	}

	@Override
	public boolean modificar(Cliente c) {
		return clienteDao.modificar(c);
	}

	@Override
	public boolean eliminar(String dniAEliminar) {
		return clienteDao.eliminar(dniAEliminar); 
	}
	
	@Override
	public Cliente obtenerPorDni(String dni) {
		return clienteDao.obtenerPorDni(dni);
	}
	
    @Override
    public List<Provincia> listarProvincias() {
        return clienteDao.listarProvincias();
    }

    @Override
    public List<Localidad> listarLocalidadesPorProvincia(int idProvincia) {
        return clienteDao.listarLocalidadesPorProvincia(idProvincia);
    }
}