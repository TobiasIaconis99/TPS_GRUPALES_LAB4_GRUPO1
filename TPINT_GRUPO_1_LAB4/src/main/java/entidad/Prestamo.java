package entidad;

import java.math.BigDecimal;

public class Prestamo {
	
    private int idPrestamo;
    private int idCliente;
    private int idCuenta;
    private String fechaAlta; 
    private BigDecimal montoSolicitado;
    private int plazoMeses;
    private int cantidadCuotas;
    private BigDecimal montoCuota;
    private int estado;
	public int getIdPrestamo() {
		return idPrestamo;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public int getIdCuenta() {
		return idCuenta;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public BigDecimal getMontoSolicitado() {
		return montoSolicitado;
	}
	public void setMontoSolicitado(BigDecimal montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
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
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}
    
    

}
