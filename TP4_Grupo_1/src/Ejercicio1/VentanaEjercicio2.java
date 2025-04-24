package Ejercicio1;

// Importo todas las clases del paquete javax.swing, java.awt y eventos de AWT.
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class VentanaEjercicio2 extends JFrame {

	// ATRIBUTOS -------------------------------------------------------------
	private static final long serialVersionUID = 1L;
    private JTextField txtPromedio, txtCondicion; // Promedio y condicion del estudiante
    private JTextField txtNota1;
    private JTextField txtNota2;
    private JTextField txtNota3;
    private JTextField txt_Condicion;
    private JTextField txt_Promedio;

    // CONFIGURACION DE LA VENTANA -------------------------------------------------------------
    
    public VentanaEjercicio2() {
        super("Promedio"); // Titulo de ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra la ventana actual
        setSize(495, 437); // Tamaño de la ventana
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JPanel panelPromedio = new JPanel();
        panelPromedio.setBounds(25, 256, 284, 122);
        getContentPane().add(panelPromedio);
        panelPromedio.setLayout(null);
        panelPromedio.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createTitledBorder("Notas del estudiante"),
        		BorderFactory.createEmptyBorder(10, 10, 10, 10)
        		));
        
        JLabel lblPromedio = new JLabel("Promedio:");
        lblPromedio.setBounds(27, 41, 67, 14);
        panelPromedio.add(lblPromedio);
        
        JLabel lblCondicion = new JLabel("Condicion:");
        lblCondicion.setBounds(27, 82, 67, 14);
        panelPromedio.add(lblCondicion);
        
        txt_Condicion = new JTextField();
        txt_Condicion.setBounds(104, 79, 139, 20);
        panelPromedio.add(txt_Condicion);
        txt_Condicion.setColumns(10);
        
        txt_Promedio = new JTextField();
        txt_Promedio.setColumns(10);
        txt_Promedio.setBounds(104, 38, 139, 20);
        panelPromedio.add(txt_Promedio);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(335, 11, 134, 354);
        getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
        JPanel panelNotas = new JPanel();
        panelNotas.setBounds(25, 36, 284, 200);
        getContentPane().add(panelNotas);
        panelNotas.setLayout(null);
        panelNotas.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createTitledBorder("Notas del estudiante"),
        		BorderFactory.createEmptyBorder(10, 10, 10, 10)
        		));
        
        JLabel lblNota1 = new JLabel("Nota 1:");
        lblNota1.setBounds(25, 44, 46, 14);
        panelNotas.add(lblNota1);
        
        JLabel lblNota2 = new JLabel("Nota 2:");
        lblNota2.setBounds(25, 75, 46, 14);
        panelNotas.add(lblNota2);
        
        txtNota1 = new JTextField();
        txtNota1.setBounds(106, 41, 139, 20);
        panelNotas.add(txtNota1);
        txtNota1.setColumns(10);
        
        txtNota2 = new JTextField();
        txtNota2.setBounds(106, 72, 139, 20);
        panelNotas.add(txtNota2);
        txtNota2.setColumns(10);
        
        JLabel lblNota3 = new JLabel("Nota 3:");
        lblNota3.setBounds(25, 106, 46, 14);
        panelNotas.add(lblNota3);
        
        txtNota3 = new JTextField();
        txtNota3.setBounds(106, 103, 139, 20);
        panelNotas.add(txtNota3);
        txtNota3.setColumns(10);
        
        JLabel lblTPS = new JLabel("TPS");
        lblTPS.setBounds(25, 138, 46, 14);
        panelNotas.add(lblTPS);
        
        JComboBox comboTps = new JComboBox();
        comboTps.setBounds(106, 134, 139, 22);
        comboTps.addItem("Aprobado");
        comboTps.addItem("Desaprobado");
        panelNotas.add(comboTps);
        
    }
    
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}