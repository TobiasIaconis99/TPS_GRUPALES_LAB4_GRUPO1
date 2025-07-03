package dao;

import java.util.List;

import entidad.Cuota;
import entidad.Usuario;

public interface CuotaDao {

    List<Cuota> listar();
    boolean agregar(Usuario u);
    boolean modificar(Usuario u);
    boolean eliminar(int idUsuario);
    boolean pagarCuota(int idCuota);
    List<Cuota> obtenerCuotasPorIdPrestamo(int idPrestamo);
    
    

}
