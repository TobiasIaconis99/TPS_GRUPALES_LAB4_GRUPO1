package entidad;

public class TipoSeguro {
	private int idTipoSeguro;
	private String descripcion;
	
	public TipoSeguro() {
		
	}
	
	public TipoSeguro(int idTipoSeguro, String descripcion) {
		this.idTipoSeguro = idTipoSeguro;
		this.descripcion = descripcion;
	}

	public int getIdTipoSeguro() {
		return idTipoSeguro;
	}

	public void setIdTipoSeguro(int idTipoSeguro) {
		this.idTipoSeguro = idTipoSeguro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
