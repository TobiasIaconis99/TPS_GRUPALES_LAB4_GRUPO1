package negocioImpl;

import java.util.List;

import dao.ClienteDao;
import daoImpl.ClienteDaoImpl;
import entidad.Cliente;
import negocio.ClienteNegocio;

public class ClienteNegocioImpl implements ClienteNegocio{

	private ClienteDao clienteDao = new ClienteDaoImpl();
	
	@Override
	public List<Cliente> listar() {
		return clienteDao.listar();
	}

	@Override
	public boolean agregar(Cliente c) {
	    if (c == null) return false;

	    // Validaciones básicas
	    if (c.getDni() == null || c.getDni().isEmpty()) return false;
	    if (c.getNombre() == null || !c.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) return false;
	    if (c.getApellido() == null || !c.getApellido().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) return false;
	    if (c.getSexo() == null || (!c.getSexo().equalsIgnoreCase("M") && !c.getSexo().equalsIgnoreCase("F"))) return false;
	    if (c.getFechaNacimiento() == null) return false;
	    if (c.getDireccion() == null || c.getDireccion().isEmpty()) return false;

	    // Evitar duplicados
	    if (clienteDao.obtenerPorDni(c.getDni()) != null) return false;

	    return clienteDao.agregar(c);
	}

	@Override
	public boolean modificar(Cliente c) {
	    if (c == null) return false;

	    if (c.getDni() == null || c.getDni().isEmpty()) return false;
	    if (c.getNombre() == null || !c.getNombre().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) return false;
	    if (c.getApellido() == null || !c.getApellido().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) return false;
	    if (c.getSexo() == null || (!c.getSexo().equalsIgnoreCase("M") && !c.getSexo().equalsIgnoreCase("F"))) return false;
	    if (c.getFechaNacimiento() == null) return false;
	    if (c.getDireccion() == null || c.getDireccion().isEmpty()) return false;

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
    public Cliente obtenerPorIdUsuario(int idUsuario) {
        return clienteDao.obtenerPorIdUsuario(idUsuario);
    }

	@Override
	public List<Cliente> listarFiltrado(String busqueda, String sexo, int pagina, int limite) {
		return clienteDao.listarFiltrado(busqueda, sexo, pagina, limite);
	}

	@Override
	public int contarFiltrado(String busqueda, String sexo) {
		return clienteDao.contarFiltrado(busqueda, sexo);
	}
}