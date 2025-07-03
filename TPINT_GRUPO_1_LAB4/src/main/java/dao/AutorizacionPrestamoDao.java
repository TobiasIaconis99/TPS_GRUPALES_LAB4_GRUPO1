package dao;

import java.util.List;

import entidad.Prestamo;

public interface AutorizacionPrestamoDao {
    /**
     * Modifica el estado de un préstamo y genera las cuotas si se aprueba.
     * @param idPrestamo El ID del préstamo a modificar.
     * @param idEstado El nuevo estado (0: Rechazado, 1: Pendiente, 2: Aprobado).
     * @return true si el estado fue modificado con éxito, false en caso contrario.
     */
    boolean modificarEstado(int idPrestamo, int idEstado);

    /**
     * Obtiene una lista de préstamos con filtros y paginación.
     * @param busquedaDNI DNI o parte del nombre/apellido del cliente para buscar (opcional).
     * @param filtroEstado El estado del préstamo (0: Rechazado, 1: Pendiente, 2: Aprobado), o null/vacío para todos.
     * @param offset El desplazamiento para la paginación (ej: (pagina - 1) * registrosPorPagina).
     * @param limit La cantidad máxima de registros a devolver por página.
     * @return Una lista de objetos Prestamo.
     */
    List<Prestamo> obtenerPrestamosFiltradosPaginados(String busquedaDNI, String filtroEstado, int offset, int limit);
    
    /**
     * Cuenta el total de préstamos que coinciden con los criterios de búsqueda y filtro.
     * @param busquedaDNI DNI o parte del nombre/apellido del cliente para buscar (opcional).
     * @param filtroEstado El estado del préstamo (0: Rechazado, 1: Pendiente, 2: Aprobado), o null/vacío para todos.
     * @return El número total de préstamos que coinciden.
     */
    int contarTotalPrestamos(String busquedaDNI, String filtroEstado);
}