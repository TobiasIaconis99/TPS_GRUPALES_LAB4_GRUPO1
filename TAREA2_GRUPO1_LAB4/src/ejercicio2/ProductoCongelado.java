package ejercicio2;

import java.sql.Date;

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
	
	// Método para mostrar información
	public void mostrarInformacion() {
	    super.mostrarInformacion();
	    System.out.println("Temperatura Recomendada: " + temperaturaRecomendada + "°C");
	}


}