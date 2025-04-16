package ejercicio1;

public class Persona {
private String Nombre;
private String Apellido;
private String DNI;

public Persona() {

}

public Persona(String Nombre, String Apellido, String DNI) {
this.Nombre = Nombre;
this.Apellido = Apellido;
this.DNI = DNI;
}

public boolean verificarDniInvalido(String dni) throws DniInvalido {
    for (int i = 0; i < dni.length(); i++) {
        if (!Character.isDigit(dni.charAt(i))) {
            throw new DniInvalido();
        }
    }
    return false;
}

public String getNombre() {
return Nombre;
}

public void setNombre(String nombre) {
Nombre = nombre;
}

public String getApellido() {
return Apellido;
}

public void setApellido(String apellido) {
Apellido = apellido;
}

public String getDNI() {
return DNI;
}

public void setDNI(String dNI) {
DNI = dNI;
}

@Override
public String toString() {
return "Persona [Nombre=" + Nombre + ", Apellido=" + Apellido + ", DNI=" + DNI + "]";
}

}