package ejercicio1;

import java.util.TreeSet;

public class Principal {

	public static void main(String[] args) {
		
		Archivo archivo = new Archivo();	
		archivo.setRuta("Personas.txt");
		TreeSet<Persona> tsPersonas = new TreeSet<Persona>();
		if(archivo.existe())
		{
			archivo.leer_a_TreeSet(tsPersonas);
			
			
		}
	}

}