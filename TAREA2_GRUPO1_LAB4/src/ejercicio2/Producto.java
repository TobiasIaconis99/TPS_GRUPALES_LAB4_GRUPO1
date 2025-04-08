package ejercicio2;

import java.sql.Date;

public class Producto {
	 String fechaDeCaducidad;
	 String numeroLote;
	 
	 // constructor
	 
	 public Producto(String fechaDeCaducidad, String numeroLote) {
	        this.fechaDeCaducidad = fechaDeCaducidad;
	        this.numeroLote = numeroLote;
	    }
	 public Producto() {
		 fechaDeCaducidad = "2025/06/05";
		 numeroLote = "1";
	 }

	// Getter y Setter 
	    public String getFechaDeCaducidad() {
	        return fechaDeCaducidad;
	    }

	    public void setFechaDeCaducidad(String fechaDeCaducidad) {
	        this.fechaDeCaducidad = fechaDeCaducidad;
	    }

	   
	    public String getNumeroLote() {
	        return numeroLote;
	    }

	    public void setNumeroLote(String numeroLote) {
	        this.numeroLote = numeroLote;
	    }
	    // Método para mostrar información
	    
	    public void mostrarInformacion() {
	        System.out.println("Fecha de Caducidad: " + fechaDeCaducidad);
	        System.out.println("Número de Lote: " + numeroLote);
	    }
}
