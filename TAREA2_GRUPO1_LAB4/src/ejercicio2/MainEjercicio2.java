package ejercicio2;

public class MainEjercicio2 {

	public static void main(String[] args) {
		
		Producto p = new Producto();
		ProductoCongelado pCongelado = new ProductoCongelado();
		ProductoFresco pFresco = new ProductoFresco();
		ProductoRefrigerado pRefrigerado = new ProductoRefrigerado();
		
		
		p.mostrarInformacion();
		pCongelado.mostrarInformacion();
		pFresco.mostrarInformacion();
		pRefrigerado.mostrarInformacion();
		
	}

}
