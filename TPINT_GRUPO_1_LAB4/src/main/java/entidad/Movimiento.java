package entidad;

import java.math.BigDecimal;
import java.util.Date;

public class Movimiento {
	
    private int idMovimiento;
    private Cuenta cuenta;
    private TipoMovimiento tipoMovimiento;
    private Date fecha;
    private String detalle;
    private BigDecimal importe;

    public Movimiento() {}

    public Movimiento(int idMovimiento, Cuenta cuenta, TipoMovimiento tipoMovimiento, Date fecha, String detalle, BigDecimal importe) {
        this.idMovimiento = idMovimiento;
        this.cuenta = cuenta;
        this.tipoMovimiento = tipoMovimiento;
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

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public int getIdCuenta() {
        return (cuenta != null) ? cuenta.getIdCuenta() : 0;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    
    public int getIdTipoMovimiento() {
        return (tipoMovimiento != null) ? tipoMovimiento.getIdTipoMovimiento() : 0;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
}