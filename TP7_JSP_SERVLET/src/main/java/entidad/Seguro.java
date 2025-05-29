package entidad;

public class Seguro {
	private int idSeguro;
	private String descripcion;
	private TipoSeguro tipoSeguro;
	private double costoContratacion;
	private double costoAsegurado;
	
	public Seguro() {
		
	}
	
	public Seguro(int idSeguro, String descripcion, TipoSeguro tipoSeguro, double costoContratacion,
			double costoAsegurado) {
		this.idSeguro = idSeguro;
		this.descripcion = descripcion;
		this.tipoSeguro = tipoSeguro;
		this.costoContratacion = costoContratacion;
		this.costoAsegurado = costoAsegurado;
	}

	public int getIdSeguro() {
		return idSeguro;
	}

	public void setIdSeguro(int idSeguro) {
		this.idSeguro = idSeguro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoSeguro getTipoSeguro() {
		return tipoSeguro;
	}

	public void setTipoSeguro(TipoSeguro tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
	}

	public double getCostoContratacion() {
		return costoContratacion;
	}

	public void setCostoContratacion(double costoContratacion) {
		this.costoContratacion = costoContratacion;
	}

	public double getCostoAsegurado() {
		return costoAsegurado;
	}

	public void setCostoAsegurado(double costoAsegurado) {
		this.costoAsegurado = costoAsegurado;
	}

	@Override
	public String toString() {
		return idSeguro + " " + descripcion + " " + tipoSeguro
				+ " " + costoContratacion + " " + costoAsegurado;
	}
	
	
	
	
}
