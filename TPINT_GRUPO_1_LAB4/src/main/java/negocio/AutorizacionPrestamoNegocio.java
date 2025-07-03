package negocio;

import java.util.List;

import entidad.Prestamo;

public interface AutorizacionPrestamoNegocio {
    /**
     * Modifica el estado de un préstamo (aprobar/rechazar) y maneja la lógica de negocio asociada.
     * @param idPrestamo El ID del préstamo a modificar.
     * @param idEstado El nuevo estado (0: Rechazado, 1: Pendiente, 2: Aprobado).
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    boolean modificarEstado(int idPrestamo, int idEstado);

    /**
     * Obtiene una lista paginada de préstamos aplicando filtros.
     * @param busquedaDNI DNI o parte del nombre/apellido del cliente para buscar (opcional).
     * @param filtroEstado El estado del préstamo (0: Rechazado, 1: Pendiente, 2: Aprobado), o null/vacío para todos.
     * @param paginaActual El número de página actual.
     * @param registrosPorPagina La cantidad de registros a mostrar por página.
     * @return Una lista de objetos Prestamo para la página actual.
     */
    List<Prestamo> listarPrestamosPaginado(String busquedaDNI, String filtroEstado, int paginaActual, int registrosPorPagina);
    
    /**
     * Obtiene el número total de préstamos que coinciden con los criterios de búsqueda y filtro.
     * @param busquedaDNI DNI o parte del nombre/apellido del cliente para buscar (opcional).
     * @param filtroEstado El estado del préstamo (0: Rechazado, 1: Pendiente, 2: Aprobado), o null/vacío para todos.
     * @return El total de préstamos.
     */
    int obtenerTotalPrestamos(String busquedaDNI, String filtroEstado);
    

    int contarTotalPrestamos(String busquedaDNI, String filtroEstado);

}