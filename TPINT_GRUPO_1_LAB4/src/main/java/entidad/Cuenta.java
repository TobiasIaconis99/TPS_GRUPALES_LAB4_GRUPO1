package entidad;

import java.math.BigDecimal;
import java.sql.Date;

public class Cuenta {
    private int idCuenta;
    private Cliente cliente;
    private TipoCuenta tipoCuenta;
    private String numeroCuenta;
    private String cbu;
    private BigDecimal saldo;
    private Date fechaCreacion;
    private boolean estado;

    public Cuenta() {
    }

	public Cuenta(int idCuenta, Cliente cliente, TipoCuenta tipoCuenta, String numeroCuenta, String cbu,
			BigDecimal saldo, Date fechaCreacion, boolean estado) {
		this.idCuenta = idCuenta;
		this.cliente = cliente;
		this.tipoCuenta = tipoCuenta;
		this.numeroCuenta = numeroCuenta;
		this.cbu = cbu;
		this.saldo = saldo;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
	}

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoCuenta getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(TipoCuenta tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCbu() {
		return cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}