package entidad;

import java.math.BigDecimal;
import java.sql.Date;

public class Cuota {
	private int idCuota;
	private int idPrestamo;
	private int numeroCuota;
	BigDecimal monto;
	Date fechaPago;
	Boolean pagada;
	public int getIdCuota() {
		return idCuota;
	}
	public void setIdCuota(int idCuota) {
		this.idCuota = idCuota;
	}
	public int getIdPrestamo() {
		return idPrestamo;
	}
	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}
	public int getNumeroCuota() {
		return numeroCuota;
	}
	public void setNumeroCuota(int numeroCuota) {
		this.numeroCuota = numeroCuota;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public Boolean getPagada() {
		return pagada;
	}
	public void setPagada(Boolean pagada) {
		this.pagada = pagada;
	}
	
	
}
