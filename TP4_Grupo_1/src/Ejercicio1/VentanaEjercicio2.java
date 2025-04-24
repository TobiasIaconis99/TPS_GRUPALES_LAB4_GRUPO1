package Ejercicio1;

// Importo todas las clases del paquete javax.swing, java.awt y eventos de AWT.
import javax.swing.*;
import java.awt.*;

public class VentanaEjercicio2 extends JFrame {

	// ATRIBUTOS -------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private JTextField txtNota1, txtNota2, txtNota3; // Notas
    private JTextField txtPromedio, txtCondicion; // Promedio y condicion del estudiante
    private JComboBox<String> comboTps; // Pelector del estado de los tps
    private JTextField textField;
    private JTextField textField_1;

    // CONFIGURACION DE LA VENTANA -------------------------------------------------------------
    
    public VentanaEjercicio2() {
        super("Promedio"); // Titulo de ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra la ventana actual
        setSize(500, 500); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en pantalla
        getContentPane().setLayout(new GridLayout(1, 2)); // Divide la ventana en 2 columnas. Una para los campos y otra para los botones


        // PANEL IZQUIERDO de ingreso y salida de datos
        // Dentro contendra otros dos paneles: uno para ingresar datos y otro para mostrar los resultados
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS)); // BoxLayout.Y_AXIS indica que los componentes que ponga dentro de este panel se van a acomodar uno debajo del otro en vertical
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));

        // SUBPANEL IZQUIERDO ARRIBA de entrada (panelEntrada)
        // Dentro estaras las notas a ingresar
        JPanel panelEntrada = new JPanel(new GridLayout(5, 2, 5, 10));
        panelEntrada.setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createTitledBorder("Notas del estudiante"),
        	    BorderFactory.createEmptyBorder(20, 10, 20, 10)
        ));

        // Cuadros de texto y combobox para el TP.
        panelEntrada.add(new JLabel("Nota 1:"));
        txtNota1 = new JTextField();
        panelEntrada.add(txtNota1);

        panelEntrada.add(new JLabel("Nota 2:"));
        txtNota2 = new JTextField();
        panelEntrada.add(txtNota2);

        panelEntrada.add(new JLabel("Nota 3:"));
        txtNota3 = new JTextField();
        panelEntrada.add(txtNota3);

        panelEntrada.add(new JLabel("TPS:"));
        comboTps = new JComboBox<>(new String[]{"Aprobado", "Desaprobado"});
        panelEntrada.add(comboTps);

        panelIzquierdo.add(panelEntrada);

        // SUBPANEL IZQUIERDO ABAJO de salida (panelSalida)
        // Solo para mostrar el promedio y la condicion del alumno     
        JPanel panelSalida = new JPanel();
        panelSalida.setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createTitledBorder("Notas del estudiante"),
        	    BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(panelSalida);
        panelSalida.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(10, 28, 46, 14);
        panelSalida.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(10, 60, 46, 14);
        panelSalida.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(66, 25, 86, 20);
        panelSalida.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(66, 57, 86, 20);
        panelSalida.add(textField_1);
        textField_1.setColumns(10);
        getContentPane().add(panelIzquierdo); // Agrego los campos de notas y tp al panel

        // PANEL DERECHO para los botones (panelBotones)
        JPanel panelBotones = new JPanel();
        panelBotones.setBorder(BorderFactory.createEmptyBorder(150, 50, 150, 50));
        getContentPane().add(panelBotones); // Agrego los botones al panel
        panelBotones.setLayout(null);
        
        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBounds(96, 98, 124, 47);
        panelBotones.add(btnCalcular);
        
        JButton btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(96, 152, 124, 47);
        panelBotones.add(btnNuevo);
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(96, 207, 124, 47);
        panelBotones.add(btnSalir);
        
    }
    
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}