package dao;

import java.util.List;

import entidad.Prestamo;

public interface PrestamoDao {
	
	List<Prestamo> listar();
    boolean agregar(Prestamo u);
    boolean modificar(Prestamo u);
    boolean eliminar(int idPrestamo);
}
