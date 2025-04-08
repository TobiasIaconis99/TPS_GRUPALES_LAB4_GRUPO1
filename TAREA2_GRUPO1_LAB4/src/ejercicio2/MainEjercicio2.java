package ejercicio2;

public class MainEjercicio2 {

	public static void main(String[] args) {
		
		Producto p = new Producto();
		ProductoCongelado pCongelado = new ProductoCongelado();
		ProductoFresco pFresco = new ProductoFresco();
		ProductoRefrigerado pRefrigerado = new ProductoRefrigerado();
		
		
		System.out.println(p.toString());
		System.out.println(pCongelado.toString());
		System.out.println(pFresco.toString());
		System.out.println(pRefrigerado.toString());
		
		
		
	}

}
