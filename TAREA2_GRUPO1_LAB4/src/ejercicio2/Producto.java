package ejercicio2;

public class Producto {
	

	 private String fechaDeCaducidad;

	 private String numeroLote;
	 
	 
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
	
	
	
	@Override
	public String toString() {
		return  "PRODUCTO Fecha De Caducidad= " + fechaDeCaducidad + ", Numero de lote= " + numeroLote +". ";
	}
}
