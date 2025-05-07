package ejercicio1;

public class Categorias {
	
    private int id;        
    private String nombre; 

    public Categorias(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre; 
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
