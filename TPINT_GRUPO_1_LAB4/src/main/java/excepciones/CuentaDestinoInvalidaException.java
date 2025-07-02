package excepciones;

public class CuentaDestinoInvalidaException extends Exception {
	private static final long serialVersionUID = 1L;

	public CuentaDestinoInvalidaException() {
        super("La cuenta destino no es válida.");
    }
}
