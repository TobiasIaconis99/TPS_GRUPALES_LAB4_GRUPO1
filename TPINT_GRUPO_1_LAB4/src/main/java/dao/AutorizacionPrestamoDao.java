package dao;
import java.util.List;

import entidad.Prestamo;

public interface AutorizacionPrestamoDao {
	List<Prestamo> listarPrestamosPendientes();
	List<Prestamo> listarPrestamosEstadoFiltrado(int idEstado,String nombreCliente);
    boolean modificarEstado(int idPrestamo,int idEstado);
    
}
