package ejercicio2;

import java.sql.Date;

public class Producto {
	 Date fechaDeCaducidad;
	 String numeroLote;
	 
	 // constructor
	 
	 public Producto(Date fechaDeCaducidad, String numeroLote) {
	        this.fechaDeCaducidad = fechaDeCaducidad;
	        this.numeroLote = numeroLote;
	    }

	// Getter y Setter 
	    public Date getFechaDeCaducidad() {
	        return fechaDeCaducidad;
	    }

	    public void setFechaDeCaducidad(Date fechaDeCaducidad) {
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
