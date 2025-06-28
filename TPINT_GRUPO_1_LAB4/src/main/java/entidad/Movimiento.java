package entidad;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movimiento {
    private int idMovimiento;
    private int idCuenta;
    private int idTipoMovimiento;
    private LocalDate fecha;
    private String detalle;
    private BigDecimal importe;

    public Movimiento() {
    }

    public Movimiento(int idMovimiento, int idCuenta, int idTipoMovimiento, LocalDate fecha, String detalle, BigDecimal importe) {
        this.idMovimiento = idMovimiento;
        this.idCuenta = idCuenta;
        this.idTipoMovimiento = idTipoMovimiento;
        this.fecha = fecha;
        this.detalle = detalle;
        this.importe = importe;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public int getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(int idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "Movimiento [idMovimiento=" + idMovimiento + ", idCuenta=" + idCuenta + ", idTipoMovimiento="
                + idTipoMovimiento + ", fecha=" + fecha + ", detalle=" + detalle + ", importe=" + importe + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idMovimiento;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movimiento other = (Movimiento) obj;
        if (idMovimiento != other.idMovimiento)
            return true;
        return true;
    }
}