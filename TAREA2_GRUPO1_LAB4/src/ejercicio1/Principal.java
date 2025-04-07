package ejercicio1;

public class Principal {

	public static void main(String[] args) {
		Profesor profesor = new Profesor("Daniel", 60, "Titular", 20);
		Profesor profesor2 = new Profesor("Daniel", 60, "Titular", 20);		
		
		System.out.println(profesor.equals(profesor2));

	}

}
