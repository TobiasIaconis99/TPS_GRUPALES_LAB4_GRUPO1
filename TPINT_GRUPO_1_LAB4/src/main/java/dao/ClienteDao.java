package dao;

import java.util.List;
import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;

public interface ClienteDao {
	
    List<Cliente> listar();
    boolean agregar(Cliente c);
    boolean modificar(Cliente c);
    boolean eliminar(String dniAEliminar);
    Cliente obtenerPorDni(String dni);
    
    // Métodos para la carga dinámica de provincias y localidades
    List<Provincia> listarProvincias();
    List<Localidad> listarLocalidadesPorProvincia(int idProvincia);
}