package entidad;

public class Provincia {
	
    private int idProvincia;
    private String nombreProvincia;
    private boolean estado;

	public Provincia() {
	}

	public Provincia(int idProvincia, String nombreProvincia, boolean estado) {
		this.idProvincia = idProvincia;
		this.nombreProvincia = nombreProvincia;
		this.estado = estado;
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}