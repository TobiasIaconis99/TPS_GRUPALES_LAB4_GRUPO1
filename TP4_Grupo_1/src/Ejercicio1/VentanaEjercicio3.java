package Ejercicio1;

import javax.swing.JFrame;

public class VentanaEjercicio3 extends JFrame {

	public VentanaEjercicio3() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		setTitle("Ejercicio 3");
		getContentPane().setLayout(null);
	}
	
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
	
}
