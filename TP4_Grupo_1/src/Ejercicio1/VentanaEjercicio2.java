package Ejercicio1;

// Importo todas las clases del paquete javax.swing, java.awt y eventos de AWT.
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VentanaEjercicio2 extends JFrame {

	// ATRIBUTOS -------------------------------------------------------------
	private static final long serialVersionUID = 1L;
    private JTextField txtNota1;
    private JTextField txtNota2;
    private JTextField txtNota3;
    private JTextField txt_Condicion;
    private JTextField txt_Promedio;
    private JComboBox<String> comboTps;

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
        		BorderFactory.createTitledBorder("Promedio del estudiante"),
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
        
        comboTps = new JComboBox<>(new String[]{"Aprobado", "Desaprobado"});
        comboTps.setBounds(106, 134, 139, 22);       
        panelNotas.add(comboTps);
        
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBounds(335, 11, 134, 354);
        getContentPane().add(panel_2);
        
        JButton btnCalcular = new JButton("CALCULAR");
        btnCalcular.setBounds(10, 75, 105, 40);
        panel_2.add(btnCalcular);
        
        JButton btnNuevo = new JButton("NUEVO");
        btnNuevo.setBounds(10, 118, 105, 40);
        panel_2.add(btnNuevo);


        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNota1.setText("");
                txtNota2.setText("");
                txtNota3.setText("");
                txt_Promedio.setText("");
                txt_Condicion.setText("");
                comboTps.setSelectedIndex(0); 
                txtNota1.requestFocus(); 
            }
        });

        
        JButton btnSalir = new JButton("SALIR");
        btnSalir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btnSalir.setBounds(10, 161, 105, 40);
        panel_2.add(btnSalir);
        
        // FUNCIONALIDADES DE LA VENTANA -------------------------------------------------------------

        // Funcionalidad del boton CALCULAR
        btnCalcular.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
                calcularCondicion();
            }
        });
        
    }  
    
    
 // FUNCION PARA CALCULAR -------------------------------------------------------------
    private void calcularCondicion() {
        try {
        	// Notas y tp
           /* double nota1 = Double.parseDouble(txtNota1.getText());
            double nota2 = Double.parseDouble(txtNota2.getText());
            double nota3 = Double.parseDouble(txtNota3.getText());*/
        	double nota1 = Double.parseDouble(txtNota1.getText().replace(',', '.'));
            double nota2 = Double.parseDouble(txtNota2.getText().replace(',', '.'));
            double nota3 = Double.parseDouble(txtNota3.getText().replace(',', '.'));
            
            String tp = (String) comboTps.getSelectedItem();
            
            List<Double> notas = List.of(nota1, nota2, nota3);
            if (notas.stream().anyMatch(n -> !esNotaValida(n))) {
                JOptionPane.showMessageDialog(this, "LAS NOTAS DEBEN SER DEL 1 AL 10", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validación de rango 1 a 10
           // if (!esNotaValida(nota1) || !esNotaValida(nota2) || !esNotaValida(nota3)) {
             //   JOptionPane.showMessageDialog(this, "LAS NOTAS DEBEN SER DEL 1 AL 10", "ERROR", JOptionPane.ERROR_MESSAGE);
               // return;
           // }

            // Calculo del promedio
            double promedio = (nota1 + nota2 + nota3) / 3;
            txt_Promedio.setText(String.format("%.2f", promedio));

            //Decido la condicion del estudiante solo con el promedio de notas
            String condicion;
            
            if (tp.equals("Desaprobado") || nota1 < 6 || nota2 < 6 || nota3 < 6) {
            	
	            // Siempre que el TP esté en condición Desaprobado,
	            // la condición del alumno es libre independientemente de las tres notas numéricas obtenidas.
	            // Si alguna de las tres notas del alumno es inferior a 6, la condición del alumno es libre 
	            // independientemente de la nota del TP.
                condicion = "Libre";
                
            }else if (nota1 >= 8 && nota2 >= 8 && nota3 >= 8) {
            	
            	//Si la nota de los tres parciales es superior o igual a 8 y el TP se encuentra aprobado, 
            	// entonces la condición es promocionado. 
                condicion = "Promocionado";
                
            }else {
            	
            	//Si los tres parciales se encuentran en el rango de notas entre 6 y 8 y el Tp se encuentra aprobado,
            	// entonces la condición es regular.
                condicion = "Regular";
            }

            txt_Condicion.setText(condicion);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "DATOS MAL INGRESADOS", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // Valida si la nota esta entre el 1 y el 10.
    private boolean esNotaValida(double nota) {
        return nota >= 1 && nota <= 10;
    }
    
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}