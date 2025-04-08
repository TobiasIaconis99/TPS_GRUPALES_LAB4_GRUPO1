package ejercicio2;

import java.sql.Date;


public class ProductoFresco extends Producto {
	
	private Date fechaEnvasado;
	private String paisOrigen;
	
	//constructor
    // Constructor
    public ProductoFresco(Date fechaDeCaducidad, String numeroLote, Date fechaEnvasado, String paisOrigen) {
        super(fechaDeCaducidad, numeroLote);
        this.fechaEnvasado = fechaEnvasado;
        this.paisOrigen = paisOrigen;
    }
	
	
	// getters y setters
	 public Date getFechaEnvasado() {
	        return fechaEnvasado;
	    }

	    public void setFechaEnvasado(Date fechaEnvasado) {
	        this.fechaEnvasado = fechaEnvasado;
	    }

	    // Getter y Setter para paisOrigen
	    public String getPaisOrigen() {
	        return paisOrigen;
	    }

	    public void setPaisOrigen(String paisOrigen) {
	        this.paisOrigen = paisOrigen;
	    }
	    
	    // metodo para mostrar informacion
	    public void mostrarInformacion() {
	        super.mostrarInformacion();
	        System.out.println("Fecha de Envasado: " + fechaEnvasado);
	        System.out.println("País de Origen: " + paisOrigen);
	    }

}
