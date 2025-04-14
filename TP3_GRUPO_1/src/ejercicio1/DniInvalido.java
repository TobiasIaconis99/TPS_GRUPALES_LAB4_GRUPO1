package ejercicio1;

public class DniInvalido extends Exception {
	
	public DniInvalido() {
		
	}

	@Override
	public String getMessage() {
		
		return "DNI INVALIDO";
	}
}