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
		setTitle("Contacto");
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
		btnMostrar.setBounds(236, 198, 89, 23);
		getContentPane().add(btnMostrar);

		// Campos para mostrar los datos (Inicialmente invisibles)
		lblMostrarNombre = new JLabel("Nombre ingresado:");
		lblMostrarNombre.setBounds(45, 240, 120, 20);
		lblMostrarNombre.setVisible(false);
		getContentPane().add(lblMostrarNombre);
		
		txtMostrarNombre = new JTextField();
		txtMostrarNombre.setBounds(169, 240, 168, 20);
		txtMostrarNombre.setEditable(false);
		txtMostrarNombre.setVisible(false);
		getContentPane().add(txtMostrarNombre);
		
		lblMostrarApellido = new JLabel("Apellido ingresado:");
		lblMostrarApellido.setBounds(45, 270, 120, 20);
		lblMostrarApellido.setVisible(false);
		getContentPane().add(lblMostrarApellido);
		
		txtMostrarApellido = new JTextField();
		txtMostrarApellido.setBounds(169, 270, 168, 20);
		txtMostrarApellido.setEditable(false);
		txtMostrarApellido.setVisible(false);
		getContentPane().add(txtMostrarApellido);
		
		lblMostrarTelefono = new JLabel("Teléfono ingresado:");
		lblMostrarTelefono.setBounds(45, 300, 120, 20);
		lblMostrarTelefono.setVisible(false);
		getContentPane().add(lblMostrarTelefono);
		
		txtMostrarTelefono = new JTextField();
		txtMostrarTelefono.setBounds(169, 300, 168, 20);
		txtMostrarTelefono.setEditable(false);
		txtMostrarTelefono.setVisible(false);
		getContentPane().add(txtMostrarTelefono);
		
		lblMostrarFechaNacimiento = new JLabel("Fecha nacimiento:");
		lblMostrarFechaNacimiento.setBounds(45, 330, 120, 20);
		lblMostrarFechaNacimiento.setVisible(false);
		getContentPane().add(lblMostrarFechaNacimiento);
		
		txtMostrarFechaNacimiento = new JTextField();
		txtMostrarFechaNacimiento.setBounds(169, 330, 168, 20);
		txtMostrarFechaNacimiento.setEditable(false);
		txtMostrarFechaNacimiento.setVisible(false);
		getContentPane().add(txtMostrarFechaNacimiento);
	}
	
	public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}

