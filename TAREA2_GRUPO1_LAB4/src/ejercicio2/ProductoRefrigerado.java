package ejercicio2;

public class ProductoRefrigerado extends Producto {

	int codigoOrganismo;
	
	// Constructor
	public ProductoRefrigerado(String fechaDeCaducidad, String numeroLote, int codigoOrganismo) {
	    super(fechaDeCaducidad, numeroLote);
	    this.codigoOrganismo = codigoOrganismo;
	}
	
	public ProductoRefrigerado() {
	    super("10/10/2026", "33");
	    this.codigoOrganismo = 4;
	}
	
	
	public int getCodigoOrganismo() {
		return codigoOrganismo;
	}
	
	public void setCodigoOrganismo(int codigoOrganismo) {
		this.codigoOrganismo = codigoOrganismo;
	}
	
	
	// Método para mostrar información
	public void mostrarInformacion() {
	    super.mostrarInformacion();
	    System.out.println("Código del Organismo de Supervisión: " + codigoOrganismo);
	}

}