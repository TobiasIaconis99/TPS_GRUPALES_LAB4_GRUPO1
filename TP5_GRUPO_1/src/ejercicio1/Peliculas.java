package ejercicio1;

public class Peliculas {
	
	// Atributos
    private int id;
    private String nombre;
    private Categorias categoria;

    // Constructor
    public Peliculas (String nombre, Categorias categoria) {

        this.nombre = nombre;
        this.categoria = categoria;
    }
    
    // Getters y setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Categorias getCategoria() {
		return categoria;
	}
	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}
}