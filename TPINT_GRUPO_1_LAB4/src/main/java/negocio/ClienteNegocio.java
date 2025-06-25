package negocio;

import java.util.List;

import entidad.Cliente;

public interface ClienteNegocio {

    List<Cliente> listar();
    public boolean agregar(Cliente c);
    public boolean modificar(Cliente c);
    public boolean eliminar(String dniAEliminar);
    public Cliente obtenerPorDni(String dni);
}