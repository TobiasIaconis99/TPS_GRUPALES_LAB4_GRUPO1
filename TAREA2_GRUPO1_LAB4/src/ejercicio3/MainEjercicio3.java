package ejercicio3;

import java.util.ArrayList;
import java.util.Iterator;

public class MainEjercicio3 {

	public static void main(String[] args) {
		 ArrayList<IEdificio> edificios = new ArrayList<>();

		    
	        edificios.add(new Polideportivo("Polideportivo San Martín", 1500.0, 1));
	        edificios.add(new Polideportivo("Polideportivo Belgrano", 1200.0, 2));
	        edificios.add(new Polideportivo("Polideportivo Roca", 1800.0, 1));

	    
	        edificios.add(new Edificio(15, 3000.0));
	        edificios.add(new Edificio(25, 4500.0));

	

	        Iterator<IEdificio> iterator = edificios.iterator();
	        
	        while(iterator.hasNext()) {
	            IEdificio e = iterator.next();

	            if (e instanceof Edificio) {
	                Edificio ed = (Edificio) e;
	                System.out.println(ed.toString());
	            } else if (e instanceof Polideportivo) {
	                Polideportivo p = (Polideportivo) e;
	                System.out.println(p.toString());
	            }
	        }

	}

}
