package ejercicio1;

import java.util.ArrayList;
import java.util.Iterator;

public class mainEjercicio1_a {

	public static void main(String[] args) {
		
		ArrayList<Profesor> profesoresList = new ArrayList<Profesor>();
		
		
		profesoresList.add(new Profesor("Ana", 40, "Titular", 15));
		profesoresList.add(new Profesor("Bruno", 35, "Adjunto", 10));
		profesoresList.add(new Profesor("Claudia", 45, "JTP", 20));
		profesoresList.add(new Profesor("Diego", 50, "Titular", 25));
		profesoresList.add(new Profesor("Elena", 30, "Ayudante", 5));
		
		
		Iterator<Profesor> iterator = profesoresList.iterator();
		while (iterator.hasNext()) {
			Profesor p = iterator.next();
			System.out.println(p.toString());
		}
		

	}

}
