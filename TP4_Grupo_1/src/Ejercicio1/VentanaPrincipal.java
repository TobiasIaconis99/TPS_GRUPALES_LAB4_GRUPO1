package Ejercicio1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public VentanaPrincipal() {
		setTitle("Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEjercicio2 = new JButton("Ejercicio 2");
		btnEjercicio2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaEjercicio2 ventana = new VentanaEjercicio2();
				
				ventana.cambiarVisibilidad(true);
			}
		});
		btnEjercicio2.setBounds(165, 139, 142, 21);
		contentPane.add(btnEjercicio2);
		
		JButton btnEjercicio1 = new JButton("Ejercicio 1");
		btnEjercicio1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaEjercicio1 ventana = new VentanaEjercicio1();
				
				ventana.cambiarVisibilidad(true);
			}
		});
		btnEjercicio1.setBounds(165, 108, 142, 21);
		contentPane.add(btnEjercicio1);
		
		JButton btnEjercicio3 = new JButton("Ejercicio 3");
		btnEjercicio3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaEjercicio3 ventana = new VentanaEjercicio3();
				
				ventana.cambiarVisibilidad(true);
			}
		});
		btnEjercicio3.setBounds(165, 170, 142, 21);
		contentPane.add(btnEjercicio3);
		
		JLabel lblGrupoNro1 = new JLabel("Grupo NRO: 1");
		lblGrupoNro1.setBounds(93, 44, 83, 21);
		contentPane.add(lblGrupoNro1);
	}
	
	public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}
