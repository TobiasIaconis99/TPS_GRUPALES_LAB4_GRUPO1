package negocio; // Asegúrate de que el paquete sea correcto

import java.util.List;
import entidad.Movimiento;

public interface MovimientoNegocio {
	
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
}