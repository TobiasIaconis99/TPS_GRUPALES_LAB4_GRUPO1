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
			
			

			archivo.setRuta("Resultado.txt");
			
			if(!archivo.existe()) {
				archivo.creaArchivo();
			}
			System.out.println("datos cargados en Resultado.txt: ");
			  for (Persona p : tsPersonas) {
		 	        System.out.println(p);
		 	    	archivo.escribe_lineas(p.ObjetoPersonatoString());
		 	    }
		}
	}

}