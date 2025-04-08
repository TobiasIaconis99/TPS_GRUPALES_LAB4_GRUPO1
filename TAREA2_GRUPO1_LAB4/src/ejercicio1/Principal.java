package ejercicio1;

public class Principal {

	public static void main(String[] args) {
		
		
		Profesor profesor = new Profesor("Daniel", 60, "Titular", 20);
		Profesor profesor2 = new Profesor("Daniel", 60, "Titular", 20);		
		
		
		if(profesor.equals(profesor2)) {						
			System.out.println("Es el mismo profesor");
		}		
		else {
			System.out.println("No es el mismo profesor");
		}
		

	}

}
