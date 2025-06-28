package entidad;

public class TipoMovimiento {
	
    private int id;
    private String nombre;

    public TipoMovimiento() {
    	
    }

    public TipoMovimiento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getIdTipoMovimiento() {
        return id;
    }

    public void setIdTipoMovimiento(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "TipoMovimiento [idTipoMovimiento=" + id + ", nombre=" + nombre + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoMovimiento other = (TipoMovimiento) obj;
        if (id != other.id)
            return false;
        return true;
    }
}