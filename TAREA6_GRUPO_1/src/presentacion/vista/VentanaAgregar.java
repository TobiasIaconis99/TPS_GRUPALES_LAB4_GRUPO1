package presentacion.vista;

import java.awt.EventQueue;
import dao.Personadao;

import javax.swing.JFrame;
import javax.swing.JTextField;

import dao.Personadao;
import daolmpl.PersonaDaoImpl;
import entidades.Persona;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaAgregar {

	JFrame frame;
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldNombre = new JTextField();
		textFieldNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (Character.isDigit(c)) {
		            e.consume();
		        }				
			}
		});
		textFieldNombre.setBounds(144, 55, 161, 20);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldApellido = new JTextField();
		textFieldApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (Character.isDigit(c)) {
		            e.consume();
		        }	
			}
		});
		textFieldApellido.setColumns(10);
		textFieldApellido.setBounds(144, 91, 161, 20);
		frame.getContentPane().add(textFieldApellido);
		
		textFieldDNI = new JTextField();
		textFieldDNI.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (!Character.isDigit(c)) {
		            e.consume();
		        }	
			}
		});
		textFieldDNI.setColumns(10);
		textFieldDNI.setBounds(144, 122, 161, 20);
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
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textFieldNombre.getText().isEmpty() || textFieldApellido.getText().isEmpty() || textFieldDNI.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos", "Error", JOptionPane.ERROR_MESSAGE);
				}
				Persona persona = new Persona();
				persona.setNombre(textFieldNombre.getText());
				persona.setApellido(textFieldApellido.getText());
				persona.setDni(Integer.parseInt(textFieldDNI.getText()));
				Personadao p= new PersonaDaoImpl(); 
				
				 try {
           p.insert(persona);
            JOptionPane.showMessageDialog(null, "Registro agregado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
				
				
			}
		});
		btnNewButton.setBounds(90, 170, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
