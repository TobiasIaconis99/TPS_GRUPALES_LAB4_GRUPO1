package ejercicio2;

public class ProductoRefrigerado extends Producto {

	private int codigoOrganismo;
	
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

	@Override
	public String toString() {
		return "ProductoRefrigerado [codigoOrganismo=" + codigoOrganismo + "]";
	}
	
	
	

}