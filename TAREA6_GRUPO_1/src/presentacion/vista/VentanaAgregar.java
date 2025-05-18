package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class VentanaAgregar {

	private JFrame frame;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextField textFieldDNI;


	/**
	 * Create the application.
	 */
	public VentanaAgregar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(144, 55, 86, 20);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setColumns(10);
		textFieldApellido.setBounds(144, 91, 86, 20);
		frame.getContentPane().add(textFieldApellido);
		
		textFieldDNI = new JTextField();
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(144, 122, 86, 20);
		frame.getContentPane().add(textFieldDNI);
		
		JLabel lblNombrel = new JLabel("Nombre");
		lblNombrel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombrel.setBounds(64, 58, 46, 14);
		frame.getContentPane().add(lblNombrel);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblApellido.setBounds(64, 94, 46, 14);
		frame.getContentPane().add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDni.setBounds(64, 125, 46, 14);
		frame.getContentPane().add(lblDni);
	}
}
