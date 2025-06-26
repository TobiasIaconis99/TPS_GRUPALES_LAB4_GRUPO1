package dao;

import java.util.List;
import entidad.Cliente;

public interface ClienteDao {
	
    List<Cliente> listar();
    boolean agregar(Cliente c);
    boolean modificar(Cliente c);
    boolean eliminar(String dniAEliminar);
    Cliente obtenerPorDni(String dni);
    Cliente obtenerPorId(int id);
	Cliente obtenerPorIdUsuario(int idUsuario);
	
	// Para el listado por filtro o busquedas
	List<Cliente> listarFiltrado(String busqueda, String sexo, int pagina, int limite);
    int contarFiltrado(String busqueda, String sexo);
}