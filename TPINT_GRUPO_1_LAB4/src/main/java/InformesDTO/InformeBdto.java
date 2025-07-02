package InformesDTO;

import java.math.BigDecimal;

public class InformeBdto {
	private String nombreCliente;
	private int DNI;
	private int cantCuentas;
	private BigDecimal saldoTotal;
	
	
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public int getDNI() {
		return DNI;
	}
	public void setDNI(int dNI) {
		DNI = dNI;
	}
	public int getCantCuentas() {
		return cantCuentas;
	}
	public void setCantCuentas(int cantCuentas) {
		this.cantCuentas = cantCuentas;
	}
	public BigDecimal getSaldoTotal() {
		return saldoTotal;
	}
	public void setSaldoTotal(BigDecimal saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

}
