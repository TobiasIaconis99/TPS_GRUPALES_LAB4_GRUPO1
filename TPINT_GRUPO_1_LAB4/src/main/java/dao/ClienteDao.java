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
}