package negocio;

import java.util.List;

import entidad.Prestamo;

public interface AutorizacionPrestamoNegocio {
	List<Prestamo> listar();
    boolean modificar(Prestamo u);
    
}
