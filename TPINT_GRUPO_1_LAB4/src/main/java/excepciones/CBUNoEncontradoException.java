package excepciones;

public class CBUNoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public CBUNoEncontradoException() {
        super("No se encontró una cuenta con el CBU ingresado.");
    }
}
