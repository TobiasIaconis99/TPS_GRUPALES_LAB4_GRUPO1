package negocioImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import dao.MovimientoDao;
import dao.CuentaDao;
import daoImpl.MovimientoDaoImpl;
import daoImpl.CuentaDaoImpl;
import entidad.Movimiento;
import entidad.Cuenta;
import negocio.MovimientoNegocio;

public class MovimientoNegocioImpl implements MovimientoNegocio {

    private MovimientoDao movimientoDao;
    private CuentaDao cuentaDao;

    public MovimientoNegocioImpl() {
        this.movimientoDao = new MovimientoDaoImpl();
        this.cuentaDao = new CuentaDaoImpl();
    }

    /**
     * Registra un nuevo movimiento en la base de datos.
     * Esta es una operación crítica que podría incluir lógica para actualizar saldos de cuentas.
     * @param movimiento El objeto Movimiento a registrar.
     * @return true si el movimiento se registró exitosamente, false en caso contrario.
     */
    @Override
    public boolean registrarMovimiento(Movimiento movimiento) {
        // Validación de datos básicos
        if (movimiento == null || movimiento.getIdCuenta() <= 0 || movimiento.getIdTipoMovimiento() <= 0 ||
            movimiento.getFecha() == null || movimiento.getImporte() == null || movimiento.getImporte().compareTo(BigDecimal.ZERO) < 0) {
            System.err.println("ERROR (Negocio): Datos de movimiento inválidos para el registro.");
            return false;
        }

        // Lógica de negocio para actualizar el saldo de la cuenta
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(movimiento.getIdCuenta());
        if (cuenta == null || !cuenta.isEstado()) { // Verificar si la cuenta existe y está activa
            System.err.println("ERROR (Negocio): Cuenta no encontrada o inactiva para el movimiento.");
            return false;
        }

        BigDecimal nuevoSaldo = cuenta.getSaldo().add(movimiento.getImporte()); // Suma el importe (positivo o negativo)
        cuenta.setSaldo(nuevoSaldo);

        // Aquí deberías iniciar una transacción para asegurar atomicidad
        // Si la BD soporta transacciones y JDBC, usar:
        // try { conexion.setAutoCommit(false); ... }
        // Y al final: conexion.commit();
        // En caso de error: conexion.rollback();

        boolean exitoMovimiento = movimientoDao.agregar(movimiento);
        boolean exitoActualizacionSaldo = false;
        if (exitoMovimiento) {
             exitoActualizacionSaldo = cuentaDao.modificar(cuenta); // Necesitas un método modificar en ICuentaDao
        }

        // Aquí se gestionaría el commit o rollback de la transacción
        if (exitoMovimiento && exitoActualizacionSaldo) {
            System.out.println("DEBUG (Negocio): Movimiento registrado y saldo de cuenta actualizado con éxito.");
            // Transacción: commit
            return true;
        } else {
            System.err.println("ERROR (Negocio): Fallo al registrar movimiento o actualizar saldo. Realizando rollback (simulado).");
            // Transacción: rollback
            return false;
        }
    }

    /**
     * Obtiene los movimientos de una cuenta por rango de fechas.
     */
    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaYFechas(int idCuenta, LocalDate fechaInicio, LocalDate fechaFin) {
        if (idCuenta <= 0 || fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            System.err.println("ERROR (Negocio): Parámetros inválidos para obtener movimientos por cuenta y fechas.");
            return List.of(); // Devuelve una lista vacía
        }
        return movimientoDao.obtenerPorCuentaYFechas(idCuenta, fechaInicio, fechaFin);
    }

    /**
     * Obtiene todos los movimientos.
     */
    @Override
    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoDao.listar();
    }

    /**
     * Realiza una transferencia entre cuentas. Esta es una operación crítica
     * que debe ser transaccional.
     */
    @Override
    public boolean realizarTransferencia(int idCuentaOrigen, int idCuentaDestino, BigDecimal importe, String detalle) {
        // 1. Validaciones iniciales
        if (idCuentaOrigen <= 0 || idCuentaDestino <= 0 || importe == null || importe.compareTo(BigDecimal.ZERO) <= 0) {
            System.err.println("ERROR (Negocio): Datos de transferencia inválidos.");
            return false;
        }
        if (idCuentaOrigen == idCuentaDestino) {
            System.err.println("ERROR (Negocio): No se puede transferir dinero a la misma cuenta.");
            return false;
        }

        // 2. Obtener cuentas
        Cuenta cuentaOrigen = cuentaDao.obtenerCuentaPorId(idCuentaOrigen);
        Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorId(idCuentaDestino);

        if (cuentaOrigen == null || !cuentaOrigen.isEstado()) {
            System.err.println("ERROR (Negocio): Cuenta de origen no encontrada o inactiva.");
            return false;
        }
        if (cuentaDestino == null || !cuentaDestino.isEstado()) {
            System.err.println("ERROR (Negocio): Cuenta de destino no encontrada o inactiva.");
            return false;
        }

        // 3. Validar saldo
        if (cuentaOrigen.getSaldo().compareTo(importe) < 0) {
            System.err.println("ERROR (Negocio): Saldo insuficiente en la cuenta de origen (ID: " + idCuentaOrigen + ").");
            return false;
        }

        // 4. Lógica de negocio y Transacción
        // En un entorno real, aquí se iniciaría una transacción JDBC.
        // GestorConexionBD.startTransaction();

        try {
            // A. Registrar movimiento de salida
            Movimiento movimientoSalida = new Movimiento();
            movimientoSalida.setIdCuenta(idCuentaOrigen);
            // Asumo que el ID de TipoMovimiento para "TransferenciaSalida" es 5 (según tu DDL)
            movimientoSalida.setIdTipoMovimiento(5);
            movimientoSalida.setFecha(LocalDate.now());
            movimientoSalida.setDetalle("Transferencia saliente a CBU " + cuentaDestino.getCbu() + " - " + detalle);
            movimientoSalida.setImporte(importe.negate()); // Importe negativo para salida

            boolean salidaExitosa = movimientoDao.agregar(movimientoSalida);

            // B. Actualizar saldo de cuenta de origen
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(importe));
            boolean actualizacionOrigenExitosa = cuentaDao.modificar(cuentaOrigen);

            // C. Registrar movimiento de entrada
            Movimiento movimientoEntrada = new Movimiento();
            movimientoEntrada.setIdCuenta(idCuentaDestino);
            // Asumo que el ID de TipoMovimiento para "TransferenciaEntrada" es 4 (según tu DDL)
            movimientoEntrada.setIdTipoMovimiento(4);
            movimientoEntrada.setFecha(LocalDate.now());
            movimientoEntrada.setDetalle("Transferencia entrante de CBU " + cuentaOrigen.getCbu() + " - " + detalle);
            movimientoEntrada.setImporte(importe); // Importe positivo para entrada

            boolean entradaExitosa = movimientoDao.agregar(movimientoEntrada);

            // D. Actualizar saldo de cuenta de destino
            cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(importe));
            boolean actualizacionDestinoExitosa = cuentaDao.modificar(cuentaDestino);

            // 5. Commit / Rollback (simulado aquí)
            if (salidaExitosa && actualizacionOrigenExitosa && entradaExitosa && actualizacionDestinoExitosa) {
                // GestorConexionBD.commitTransaction();
                System.out.println("DEBUG (Negocio): Transferencia exitosa.");
                return true;
            } else {
                // GestorConexionBD.rollbackTransaction();
                System.err.println("ERROR (Negocio): Fallo en la transferencia. Se realizó rollback (simulado).");
                return false;
            }

        } catch (Exception e) { // Captura cualquier error que ocurra durante la transacción
            // GestorConexionBD.rollbackTransaction();
            System.err.println("ERROR (Negocio): Excepción durante la transferencia. Se realizó rollback (simulado). " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            // Asegurarse de que la conexión se cierra si se gestiona manualmente o se devuelve al pool
        }
    }
}