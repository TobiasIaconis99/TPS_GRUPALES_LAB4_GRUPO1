package negocio;

import java.util.List;

import entidad.Cuota;
import entidad.Usuario;

public interface NegocioCuota {
	
    List<Cuota> listar();
    boolean agregar(Usuario u);
    boolean modificar(Usuario u);
    boolean eliminar(int idUsuario);
    List<Cuota> obtenerCuotasPorIdPrestamo(int idPrestamo);
    boolean pagarCuota(int idCuota);

}
