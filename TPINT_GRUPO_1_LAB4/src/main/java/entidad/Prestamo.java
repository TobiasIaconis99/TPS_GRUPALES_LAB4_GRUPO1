package entidad;

import java.math.BigDecimal;
import java.sql.Date;

public class Prestamo {

    private int idPrestamo;
    private int idCliente; // ← nuevo campo (FK)
    private int idCuenta;  // ← renombrado desde idCuentaDestino
    private Date fechaAlta;
    private BigDecimal importePedido; // ← renombrado desde montoSolicitado
    private int plazoMeses; // ← nuevo campo
    private int cantidadCuotas; // ← renombrado desde cuotasTotales
    private BigDecimal montoCuota; // ← renombrado desde montoPorCuota
    private String estado; // ← tipo ENUM en DB, lo manejamos como String en Java

    // Getters y Setters
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public BigDecimal getImportePedido() {
        return importePedido;
    }

    public void setImportePedido(BigDecimal importePedido) {
        this.importePedido = importePedido;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
