package Ejercicio1;

// Importo todas las clases del paquete javax.swing, java.awt y eventos de AWT.
import javax.swing.*;
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
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 291, 238, 146);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblPromedio = new JLabel("Promedio:");
        lblPromedio.setBounds(36, 41, 46, 14);
        panel_1.add(lblPromedio);
        
        JLabel lblCondicion = new JLabel("Condicion:");
        lblCondicion.setBounds(36, 82, 46, 14);
        panel_1.add(lblCondicion);
        
        textField_3 = new JTextField();
        textField_3.setBounds(111, 38, 86, 20);
        panel_1.add(textField_3);
        textField_3.setColumns(10);
        
        textField_4 = new JTextField();
        textField_4.setBounds(121, 79, 86, 20);
        panel_1.add(textField_4);
        textField_4.setColumns(10);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(281, 11, 179, 426);
        getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
        JButton btnCalcular = new JButton("CALCULAR");
        btnCalcular.setBounds(62, 56, 89, 23);
        panel_2.add(btnCalcular);
        
        JButton btnNuevo = new JButton("NUEVO");
        btnNuevo.setBounds(62, 112, 89, 23);
        panel_2.add(btnNuevo);
        
        JButton btnSalir = new JButton("SALIR");
        btnSalir.setBounds(62, 180, 89, 23);
        panel_2.add(btnSalir);
        
        JPanel panel = new JPanel();
        panel.setBounds(10, 11, 238, 250);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Nota 1:");
        lblNewLabel.setBounds(25, 44, 46, 14);
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Nota 2:");
        lblNewLabel_1.setBounds(25, 75, 46, 14);
        panel.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(91, 38, 109, 20);
        panel.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(91, 69, 109, 20);
        panel.add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Nota 3:");
        lblNewLabel_2.setBounds(25, 109, 46, 14);
        panel.add(lblNewLabel_2);
        
        textField_2 = new JTextField();
        textField_2.setBounds(91, 103, 109, 20);
        panel.add(textField_2);
        textField_2.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("TPS");
        lblNewLabel_3.setBounds(25, 206, 46, 14);
        panel.add(lblNewLabel_3);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(91, 202, 109, 22);
        panel.add(comboBox);
        
    }
    
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}