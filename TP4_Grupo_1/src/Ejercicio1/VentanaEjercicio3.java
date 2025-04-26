package Ejercicio1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class VentanaEjercicio3 extends JFrame {
	private JTextField textField;

	public VentanaEjercicio3() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		setTitle("Ejercicio 3");
		getContentPane().setLayout(null);
		
		JPanel panelSistOperativo = new JPanel();
		panelSistOperativo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSistOperativo.setBounds(28, 34, 377, 44);
		getContentPane().add(panelSistOperativo);
		panelSistOperativo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Elije un Sistema Operativo:");
		lblNewLabel.setBounds(10, 10, 160, 24);
		panelSistOperativo.add(lblNewLabel);
		
		JRadioButton rdbtnWindows = new JRadioButton("Windows");
		rdbtnWindows.setBounds(176, 12, 84, 21);
		panelSistOperativo.add(rdbtnWindows);
		
		JRadioButton rdbtnMac = new JRadioButton("Mac");
		rdbtnMac.setBounds(262, 12, 51, 21);
		panelSistOperativo.add(rdbtnMac);
		
		JRadioButton rdbtnLinux = new JRadioButton("Linux");
		rdbtnLinux.setBounds(315, 12, 56, 21);
		panelSistOperativo.add(rdbtnLinux);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(28, 99, 377, 154);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEspecialidad = new JLabel("Elije una Especialidad:");
		lblEspecialidad.setBounds(10, 71, 163, 21);
		panel.add(lblEspecialidad);
		
		JCheckBox chckbxProgramación = new JCheckBox("Programación");
		chckbxProgramación.setBounds(175, 32, 172, 21);
		panel.add(chckbxProgramación);
		
		JCheckBox chckbxAdministracin = new JCheckBox("Administración");
		chckbxAdministracin.setBounds(175, 71, 172, 21);
		panel.add(chckbxAdministracin);
		
		JCheckBox chckbxDiseoGrafico = new JCheckBox("Diseño Grafico");
		chckbxDiseoGrafico.setBounds(175, 113, 172, 21);
		panel.add(chckbxDiseoGrafico);
		
		JPanel panelHoras = new JPanel();
		panelHoras.setBounds(28, 260, 377, 110);
		getContentPane().add(panelHoras);
		panelHoras.setLayout(null);
		
		JLabel lblCantHorasCompu = new JLabel("Cantidad de horas en el computador:");
		lblCantHorasCompu.setBounds(10, 25, 230, 14);
		panelHoras.add(lblCantHorasCompu);
		
		textField = new JTextField();
		textField.setBounds(250, 22, 86, 20);
		panelHoras.add(textField);
		textField.setColumns(10);
	}
	
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}
