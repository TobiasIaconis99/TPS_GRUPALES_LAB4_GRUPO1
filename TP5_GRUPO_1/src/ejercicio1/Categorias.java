package ejercicio1;

public class Categorias {
	
    private String nombre;
    
    // Constructor
    public Categorias(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
	    return nombre;
	}

}