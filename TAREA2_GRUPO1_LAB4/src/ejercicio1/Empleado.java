package ejercicio1;

import java.util.Objects;

public class Empleado {

	// Atributos

	private final int id;

	private String nombre;

	private int edad;

	private static int cont=999;


	// Constructores

	public Empleado() {

	cont++;

	id=cont;

	nombre = "sin nombre";

	edad= 99;

	}

	public Empleado(String nombre, int edad) {

	cont++;

	id=cont;

	this.nombre=nombre;

	this.edad=edad;

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

	public int getEdad() {

	return edad;

	}

	public void setEdad(int edad) {

	this.edad = edad;

	}


	// Metodos

	public String toString() {

	return "EMPLEADO ID=" + id + ", NOMBRE=" + nombre + ", EDAD=" + edad ;

	}

	public static int devuelveProximoID() {

	return cont+1;

	}

	@Override
	public int hashCode() {
		return Objects.hash(edad, id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return edad == other.edad && Objects.equals(nombre, other.nombre);
	}


	

	}

