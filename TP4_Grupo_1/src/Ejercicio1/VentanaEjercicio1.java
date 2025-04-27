package Ejercicio1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaEjercicio1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtFechaNacimiento;
	private JTextField txtMostrarNombre;
	private JTextField txtMostrarApellido;
	private JTextField txtMostrarTelefono;
	private JTextField txtMostrarFechaNacimiento;
	private JLabel lblMostrarNombre;
	private JLabel lblMostrarApellido;
	private JLabel lblMostrarTelefono;
	private JLabel lblMostrarFechaNacimiento;

	public VentanaEjercicio1() {
		setTitle("Contactos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(45, 40, 100, 14);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(169, 40, 168, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(45, 77, 100, 14);
		getContentPane().add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(169, 77, 168, 20);
		getContentPane().add(txtApellido);
		txtApellido.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Teléfono");
		lblTelefono.setBounds(45, 118, 100, 14);
		getContentPane().add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(169, 118, 168, 20);
		getContentPane().add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha nacimiento");
		lblFechaNacimiento.setBounds(45, 161, 120, 14);
		getContentPane().add(lblFechaNacimiento);
		
		txtFechaNacimiento = new JTextField();
		txtFechaNacimiento.setBounds(169, 161, 168, 20);
		getContentPane().add(txtFechaNacimiento);
		txtFechaNacimiento.setColumns(10);
		
		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean todosCompletos = true;

				JTextField[] campos = { txtNombre, txtApellido, txtTelefono, txtFechaNacimiento };

				for (JTextField campo : campos) {
					if (campo.getText().trim().isEmpty()) {
						campo.setBackground(java.awt.Color.RED);
						todosCompletos = false;
					} else {
						campo.setBackground(java.awt.Color.WHITE);
					}
				}

				if (todosCompletos) {
					txtMostrarNombre.setText(txtNombre.getText());
					txtMostrarApellido.setText(txtApellido.getText());
					txtMostrarTelefono.setText(txtTelefono.getText());
					txtMostrarFechaNacimiento.setText(txtFechaNacimiento.getText());

					for (JTextField campo : campos) {
						campo.setText("");
					}

					// Hacemos visibles los campos de mostrar datos
					lblMostrarNombre.setVisible(true);
					txtMostrarNombre.setVisible(true);
					lblMostrarApellido.setVisible(true);
					txtMostrarApellido.setVisible(true);
					lblMostrarTelefono.setVisible(true);
					txtMostrarTelefono.setVisible(true);
					lblMostrarFechaNacimiento.setVisible(true);
					txtMostrarFechaNacimiento.setVisible(true);

				} else {
					txtMostrarNombre.setText("");
					txtMostrarApellido.setText("");
					txtMostrarTelefono.setText("");
					txtMostrarFechaNacimiento.setText("");
				}
			}
		});
		btnMostrar.setBounds(258, 204, 89, 23);
		getContentPane().add(btnMostrar);

		// Campos para mostrar los datos (Inicialmente invisibles)
		lblMostrarNombre = new JLabel("Nombre:");
		lblMostrarNombre.setBounds(55, 260, 90, 20);
		lblMostrarNombre.setVisible(false);
		getContentPane().add(lblMostrarNombre);
		
		txtMostrarNombre = new JTextField();
		txtMostrarNombre.setBounds(169, 260, 168, 20);
		txtMostrarNombre.setEditable(false);
		txtMostrarNombre.setVisible(false);
		getContentPane().add(txtMostrarNombre);
		
		lblMostrarApellido = new JLabel("Apellido:");
		lblMostrarApellido.setBounds(55, 290, 90, 20);
		lblMostrarApellido.setVisible(false);
		getContentPane().add(lblMostrarApellido);
		
		txtMostrarApellido = new JTextField();
		txtMostrarApellido.setBounds(169, 290, 168, 20);
		txtMostrarApellido.setEditable(false);
		txtMostrarApellido.setVisible(false);
		getContentPane().add(txtMostrarApellido);
		
		lblMostrarTelefono = new JLabel("Teléfono:");
		lblMostrarTelefono.setBounds(55, 320, 90, 20);
		lblMostrarTelefono.setVisible(false);
		getContentPane().add(lblMostrarTelefono);
		
		txtMostrarTelefono = new JTextField();
		txtMostrarTelefono.setBounds(169, 320, 168, 20);
		txtMostrarTelefono.setEditable(false);
		txtMostrarTelefono.setVisible(false);
		getContentPane().add(txtMostrarTelefono);
		
		lblMostrarFechaNacimiento = new JLabel("Fecha:");
		lblMostrarFechaNacimiento.setBounds(55, 350, 90, 20);
		lblMostrarFechaNacimiento.setVisible(false);
		getContentPane().add(lblMostrarFechaNacimiento);
		
		txtMostrarFechaNacimiento = new JTextField();
		txtMostrarFechaNacimiento.setBounds(169, 350, 168, 20);
		txtMostrarFechaNacimiento.setEditable(false);
		txtMostrarFechaNacimiento.setVisible(false);
		getContentPane().add(txtMostrarFechaNacimiento);
		
		JLabel lblNewLabel = new JLabel("Los datos ingresados fueron:");
		lblNewLabel.setBounds(45, 235, 177, 14);
		getContentPane().add(lblNewLabel);
	}
	
	public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}

