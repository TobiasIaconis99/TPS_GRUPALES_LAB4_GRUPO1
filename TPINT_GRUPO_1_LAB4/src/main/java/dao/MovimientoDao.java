package dao; // Asegúrate de que el paquete sea correcto

import java.util.List;
import entidad.Movimiento;

public interface MovimientoDao {
	
	/*
	 * Agrega un nuevo movimiento a la base de datos.
     * @param movimiento El objeto Movimiento a agregar.
     * @return true si el movimiento se agregó exitosamente, false en caso contrario.
     */
    boolean agregar(Movimiento movimiento);

    /**
     * Obtiene un movimiento por su ID único.
     * @param id El ID del movimiento.
     * @return El objeto Movimiento si se encuentra, null en caso contrario.
     */
    Movimiento obtenerPorId(int id);

    /**
     * Obtiene una lista de movimientos asociados a una cuenta específica.
     * @param idCuenta El ID de la cuenta.
     * @return Una lista de movimientos de la cuenta.
     */
    List<Movimiento> obtenerPorCuenta(int idCuenta);

    /**
     * Obtiene una lista de movimientos por tipo de movimiento.
     * @paramidTipoMovimiento El ID del tipo de movimiento.
     * @return Una lista de movimientos del tipo especificado.
     */
    List<Movimiento> obtenerPorTipoMovimiento(int idTipoMovimiento);

    /**
     * Obtiene todos los movimientos registrados en la base de datos.
     * @return Una lista de todos los movimientos.
     */
    List<Movimiento> obtenerTodosLosMovimientos();
    
    /**
     * Obtiene una lista paginada de movimientos para una cuenta específica.
     * @param idCuenta El ID de la cuenta.
     * @param paginaActual La página actual (base 1).
     * @param registrosPorPagina El número de registros por página.
     * @return Una lista de movimientos paginados.
     */
    List<Movimiento> obtenerMovimientosPorCuentaPaginado(int idCuenta, int paginaActual, int registrosPorPagina);

    /**
     * Cuenta el total de movimientos para una cuenta específica (para calcular el total de páginas).
     * @param idCuenta El ID de la cuenta.
     * @return El número total de movimientos.
     */
    int contarMovimientosPorCuenta(int idCuenta);
}