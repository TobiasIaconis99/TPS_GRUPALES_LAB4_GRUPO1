package Ejercicio1;

// Importo todas las clases del paquete javax.swing, java.awt y eventos de AWT.
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class VentanaEjercicio2 extends JFrame {

	// ATRIBUTOS -------------------------------------------------------------
	private static final long serialVersionUID = 1L;
    private JTextField txtPromedio, txtCondicion; // Promedio y condicion del estudiante
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;

    // CONFIGURACION DE LA VENTANA -------------------------------------------------------------
    
    public VentanaEjercicio2() {
        super("Promedio"); // Titulo de ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra la ventana actual
        setSize(500, 500); // Tamaño de la ventana
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JPanel panelPromedio = new JPanel();
        panelPromedio.setBounds(25, 256, 263, 122);
        getContentPane().add(panelPromedio);
        panelPromedio.setLayout(null);
        panelPromedio.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createTitledBorder("Notas del estudiante"),
        		BorderFactory.createEmptyBorder(10, 10, 10, 10)
        		));
        
        JLabel lblPromedio = new JLabel("Promedio:");
        lblPromedio.setBounds(36, 41, 46, 14);
        panelPromedio.add(lblPromedio);
        
        JLabel lblCondicion = new JLabel("Condicion:");
        lblCondicion.setBounds(36, 82, 46, 14);
        panelPromedio.add(lblCondicion);
        
        textField_3 = new JTextField();
        textField_3.setBounds(111, 38, 86, 20);
        panelPromedio.add(textField_3);
        textField_3.setColumns(10);
        
        textField_4 = new JTextField();
        textField_4.setBounds(111, 79, 86, 20);
        panelPromedio.add(textField_4);
        textField_4.setColumns(10);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(335, 11, 125, 426);
        getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
        JButton btnCalcular = new JButton("CALCULAR");
        btnCalcular.setBounds(26, 55, 89, 23);
        panel_2.add(btnCalcular);
        
        JButton btnNuevo = new JButton("NUEVO");
        btnNuevo.setBounds(26, 118, 89, 23);
        panel_2.add(btnNuevo);
        
        JButton btnSalir = new JButton("SALIR");
        btnSalir.setBounds(26, 179, 89, 23);
        panel_2.add(btnSalir);
        
        JPanel panelNotas = new JPanel();
        panelNotas.setBounds(25, 36, 263, 200);
        getContentPane().add(panelNotas);
        panelNotas.setLayout(null);
        panelNotas.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createTitledBorder("Notas del estudiante"),
        		BorderFactory.createEmptyBorder(10, 10, 10, 10)
        		));
        
        JLabel lblNewLabel = new JLabel("Nota 1:");
        lblNewLabel.setBounds(25, 44, 46, 14);
        panelNotas.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Nota 2:");
        lblNewLabel_1.setBounds(25, 75, 46, 14);
        panelNotas.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(106, 41, 116, 20);
        panelNotas.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(106, 72, 116, 20);
        panelNotas.add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Nota 3:");
        lblNewLabel_2.setBounds(25, 106, 46, 14);
        panelNotas.add(lblNewLabel_2);
        
        textField_2 = new JTextField();
        textField_2.setBounds(106, 103, 116, 20);
        panelNotas.add(textField_2);
        textField_2.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("TPS");
        lblNewLabel_3.setBounds(25, 138, 46, 14);
        panelNotas.add(lblNewLabel_3);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(106, 134, 116, 22);
        panelNotas.add(comboBox);
        
    }
    
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}