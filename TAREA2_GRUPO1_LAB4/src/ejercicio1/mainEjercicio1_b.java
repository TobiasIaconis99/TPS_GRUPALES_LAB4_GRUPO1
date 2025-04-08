package ejercicio1;

import java.util.Iterator;
import java.util.TreeSet;

public class mainEjercicio1_b {

	public static void main(String[] args) {
		
		
		TreeSet<Profesor> profesoresList = new TreeSet<Profesor>();
		
		
		profesoresList.add(new Profesor("Pepe", 40, "Titular", 15));
		profesoresList.add(new Profesor("Marilu", 35, "Adjunto", 10));
		profesoresList.add(new Profesor("Camila", 45, "JTP", 20));
		profesoresList.add(new Profesor("Diego", 50, "Titular", 25));
		profesoresList.add(new Profesor("Elena", 30, "Ayudante", 5));
		
		
		Iterator<Profesor> iterator = profesoresList.iterator();
		while (iterator.hasNext()) {
			Profesor p = iterator.next();
			System.out.println(p.toString());
		}

	}

}
