package ejercicio1;

public class Peliculas implements Comparable<Peliculas> {

    // Atributos
    private final int id;
    private String nombre;
    private Categorias categoria;
    private static int cont = 0;

    // Constructores
    public Peliculas(String nombre, Categorias categoria) {
        cont++;
        id = cont;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getId() {
        return id;
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

    // Métodos
    @Override
    public String toString() {
        return "ID " + id + ", NOMBRE: " + nombre + ", CATEGORIA: " + categoria;
    }

    public static int devuelveProximoID() {
        return cont + 1;
    }

	@Override
	public int compareTo(Peliculas o) {		
		return this.nombre.compareToIgnoreCase(o.getNombre());
	}
    
    
}
