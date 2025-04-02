package Ejercicio1;

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

	}

