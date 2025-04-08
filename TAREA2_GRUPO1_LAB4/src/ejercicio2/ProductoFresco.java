package ejercicio2;

import java.sql.Date;


public class ProductoFresco extends Producto {
	
	private String fechaEnvasado;
	private String paisOrigen;
	
	//constructor
    // Constructor
    public ProductoFresco(String fechaDeCaducidad, String numeroLote, String fechaEnvasado, String paisOrigen) {
        super(fechaDeCaducidad, numeroLote);
        this.fechaEnvasado = fechaEnvasado;
        this.paisOrigen = paisOrigen;
    }
    
    public ProductoFresco() {
    	super("01/10/2026","1");
    	fechaEnvasado = "30/02/2020";
    	paisOrigen= "Argentina";
    }
	
	
	// getters y setters
	public String getFechaEnvasado() {
		return fechaEnvasado;
	}

	public void setFechaEnvasado(String fechaEnvasado) {
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
