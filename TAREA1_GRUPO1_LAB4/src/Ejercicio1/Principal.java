package Ejercicio1;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Empleado[] vEmp = new Empleado[5];
		vEmp[0] = new Empleado("Manuel", 28);
		vEmp[1] = new Empleado("David", 33);
		vEmp[2] = new Empleado();
		vEmp[2].setNombre("Tobias");
		vEmp[2].setEdad(27);
		vEmp[3] = new Empleado();
		vEmp[4] = new Empleado();

		for (Empleado empleado : vEmp) {
		System.out.println(empleado.toString());
		}
		System.out.println("PROXIMO ID: " + Empleado.devuelveProximoID());
		
		
	}

}
