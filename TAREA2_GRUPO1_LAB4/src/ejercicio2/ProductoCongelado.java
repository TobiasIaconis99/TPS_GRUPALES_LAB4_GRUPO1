package ejercicio2;

public class ProductoCongelado extends Producto {
	
	private int temperaturaRecomendada;
	
	
	// Constructor
	public ProductoCongelado(String fechaDeCaducidad, String numeroLote, int temperaturaRecomendada) {
	    super(fechaDeCaducidad, numeroLote);
	    this.temperaturaRecomendada = temperaturaRecomendada;
	}
	
	public ProductoCongelado() {
	    super("08/04/2025", "1");
	    this.temperaturaRecomendada = 10;
	}
	
	public int getTemperaturaRecomendada() {
		return temperaturaRecomendada;
	}
	
	public void setTemperaturaRecomendada(int temperaturaRecomendada) {
		this.temperaturaRecomendada = temperaturaRecomendada;
	}

	@Override
	public String toString() {
		return "ProductoCongelado [temperaturaRecomendada=" + temperaturaRecomendada + "]";
	}
	
	

}