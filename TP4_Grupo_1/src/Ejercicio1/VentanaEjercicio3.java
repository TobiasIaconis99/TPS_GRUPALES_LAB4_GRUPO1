package Ejercicio1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class VentanaEjercicio3 extends JFrame {

	public VentanaEjercicio3() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		setTitle("Ejercicio 3");
		getContentPane().setLayout(null);
		
		JPanel panelSistOperativo = new JPanel();
		panelSistOperativo.setBounds(8, 34, 420, 44);
		getContentPane().add(panelSistOperativo);
		panelSistOperativo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Elije un Sistema Operativo:");
		lblNewLabel.setBounds(8, 10, 168, 24);
		panelSistOperativo.add(lblNewLabel);
		
		JRadioButton rdbtnWindows = new JRadioButton("Windows");
		rdbtnWindows.setBounds(180, 12, 96, 21);
		panelSistOperativo.add(rdbtnWindows);
		
		JRadioButton rdbtnMac = new JRadioButton("Mac");
		rdbtnMac.setBounds(276, 12, 70, 21);
		panelSistOperativo.add(rdbtnMac);
		
		JRadioButton rdbtnLinux = new JRadioButton("Linux");
		rdbtnLinux.setBounds(346, 12, 70, 21);
		panelSistOperativo.add(rdbtnLinux);
		
		JPanel panel = new JPanel();
		panel.setBounds(28, 99, 377, 154);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblEspecialidad = new JLabel("Elije una Especialidad:");
		lblEspecialidad.setBounds(8, 71, 163, 21);
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
	}
	
    public void cambiarVisibilidad(boolean estado) {
		setVisible(estado);
	}
}
