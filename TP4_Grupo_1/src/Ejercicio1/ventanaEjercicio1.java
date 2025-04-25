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

	public VentanaEjercicio1() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 414, 326);
		setTitle("Ejercicio 1");
		getContentPane().setLayout(null);
		
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(45, 40, 68, 14);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(169, 40, 168, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellidos");
		lblApellido.setBounds(45, 77, 68, 14);
		getContentPane().add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(169, 77, 168, 20);
		getContentPane().add(txtApellido);
		txtApellido.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(45, 118, 68, 14);
		getContentPane().add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(169, 118, 168, 20);
		getContentPane().add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha nacimiento");
		lblFechaNacimiento.setBounds(46, 161, 112, 14);
		getContentPane().add(lblFechaNacimiento);
		
		txtFechaNacimiento = new JTextField();
		txtFechaNacimiento.setBounds(168, 161, 169, 20);
		getContentPane().add(txtFechaNacimiento);
		txtFechaNacimiento.setColumns(10);
		
		JLabel lblDatosIngresados = new JLabel("Los datos ingresados fueron:");
		lblDatosIngresados.setBounds(32, 249, 342, 27);
		getContentPane().add(lblDatosIngresados);
		
		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				        boolean todosCompletos = true;

				        // Metemos los campos en un array
				        JTextField[] campos = { txtNombre, txtApellido, txtTelefono, txtFechaNacimiento };

				        // Recorremos y validamos
				        for (JTextField campo : campos) {
				            if (campo.getText().trim().isEmpty()) {
				                campo.setBackground(java.awt.Color.RED);
				                todosCompletos = false;
				            } else {
				                campo.setBackground(java.awt.Color.WHITE);
				            }
				        }

				        if (todosCompletos) {
				        	lblDatosIngresados.setText("Los datos ingresados fueron: " +
				            		txtNombre.getText() + " " + txtApellido.getText() + ", " + txtTelefono.getText() + ", " + txtFechaNacimiento.getText());
				                for(JTextField campo : campos) {
				                	campo.setText("");
				                }
				            
				        } else {
				        	lblDatosIngresados.setText("COMPLETE TODOS LOS CAMPOS");
				        }
				    }
			
		});
		btnMostrar.setBounds(236, 198, 89, 23);
		getContentPane().add(btnMostrar);
		
		
	}
	public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}
