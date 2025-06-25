package entidad;

public class TipoCuenta {
	
    private int idTipoCuenta;
    private String nombreTipoCuenta;
    private boolean estado;

    // Constructores
    public TipoCuenta() {
    }

    public TipoCuenta(int idTipoCuenta, String nombreTipoCuenta, boolean estado) {
        this.idTipoCuenta = idTipoCuenta;
        this.nombreTipoCuenta = nombreTipoCuenta;
        this.estado = estado;
    }

	public int getIdTipoCuenta() {
		return idTipoCuenta;
	}

	public void setIdTipoCuenta(int idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	public String getNombreTipoCuenta() {
		return nombreTipoCuenta;
	}

	public void setNombreTipoCuenta(String nombreTipoCuenta) {
		this.nombreTipoCuenta = nombreTipoCuenta;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
