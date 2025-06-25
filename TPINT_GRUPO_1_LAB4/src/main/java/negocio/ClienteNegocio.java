package negocio;

import java.util.List;

import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;

public interface ClienteNegocio {

    List<Cliente> listar();
    public boolean agregar(Cliente c);
    public boolean modificar(Cliente c);
    public boolean eliminar(String dniAEliminar);
    public Cliente obtenerPorDni(String dni);
    
    List<Provincia> listarProvincias();
    List<Localidad> listarLocalidadesPorProvincia(int idProvincia);
}