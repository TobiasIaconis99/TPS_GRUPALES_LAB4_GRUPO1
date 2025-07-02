package negocio;

import java.util.List;

import entidad.Prestamo;

public interface PrestamoNegocio {
	
	List<Prestamo> listar();
    boolean agregar(Prestamo u);
    boolean modificar(Prestamo u);
    boolean eliminar(int idPrestamo);
}
