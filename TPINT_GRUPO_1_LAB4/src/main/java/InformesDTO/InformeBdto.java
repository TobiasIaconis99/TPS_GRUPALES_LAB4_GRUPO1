package InformesDTO;

public class InformeBdto {
	private String nombreCliente;
	private int DNI;
	private int cantidadMovimientos;

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

	public int getCantidadMovimientos() {
		return cantidadMovimientos;
	}

	public void setCantidadMovimientos(int cantidadMovimientos) {
		this.cantidadMovimientos = cantidadMovimientos;
	}
}