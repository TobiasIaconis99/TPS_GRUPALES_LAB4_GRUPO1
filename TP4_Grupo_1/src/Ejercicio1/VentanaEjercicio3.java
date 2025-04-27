package Ejercicio1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;

public class VentanaEjercicio3 extends JFrame {
    private JTextField textField;
    private JRadioButton rdbtnWindows;
    private JRadioButton rdbtnMac;
    private JRadioButton rdbtnLinux;
    private JCheckBox chckbxProgramacion;
    private JCheckBox chckbxAdministracion;
    private JCheckBox chckbxDisenoGrafico;

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

        rdbtnWindows = new JRadioButton("Windows");
        rdbtnWindows.setBounds(176, 12, 84, 21);
        panelSistOperativo.add(rdbtnWindows);

        rdbtnMac = new JRadioButton("Mac");
        rdbtnMac.setBounds(262, 12, 51, 21);
        panelSistOperativo.add(rdbtnMac);

        rdbtnLinux = new JRadioButton("Linux");
        rdbtnLinux.setBounds(315, 12, 56, 21);
        panelSistOperativo.add(rdbtnLinux);

        ButtonGroup groupSistemas = new ButtonGroup();
        groupSistemas.add(rdbtnWindows);
        groupSistemas.add(rdbtnMac);
        groupSistemas.add(rdbtnLinux);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBounds(28, 99, 377, 154);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblEspecialidad = new JLabel("Elije una Especialidad:");
        lblEspecialidad.setBounds(10, 71, 163, 21);
        panel.add(lblEspecialidad);

        chckbxProgramacion = new JCheckBox("Programación");
        chckbxProgramacion.setBounds(175, 32, 172, 21);
        panel.add(chckbxProgramacion);

        chckbxAdministracion = new JCheckBox("Administración");
        chckbxAdministracion.setBounds(175, 71, 172, 21);
        panel.add(chckbxAdministracion);

        chckbxDisenoGrafico = new JCheckBox("Diseño Gráfico");
        chckbxDisenoGrafico.setBounds(175, 113, 172, 21);
        panel.add(chckbxDisenoGrafico);

        JPanel panelHoras = new JPanel();
        panelHoras.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelHoras.setBounds(28, 260, 377, 110);
        getContentPane().add(panelHoras);
        panelHoras.setLayout(null);

        JLabel lblCantHorasCompu = new JLabel("Cantidad de horas en el computador:");
        lblCantHorasCompu.setBounds(10, 10, 230, 14);
        panelHoras.add(lblCantHorasCompu);

        textField = new JTextField();
        textField.setBounds(250, 7, 86, 20);
        panelHoras.add(textField);
        textField.setColumns(10);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(140, 65, 100, 30);
        panelHoras.add(btnAceptar);

        btnAceptar.addActionListener(e -> mostrarInformacion());
    }

    private void mostrarInformacion() {
        StringBuilder mensaje = new StringBuilder();

        // Validar que eligió las opciones correspondientes
        if (!rdbtnWindows.isSelected() && !rdbtnMac.isSelected() && !rdbtnLinux.isSelected()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un sistema operativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
     
        if (!chckbxProgramacion.isSelected() && !chckbxAdministracion.isSelected() && !chckbxDisenoGrafico.isSelected()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona al menos una especialidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String horasTexto = textField.getText().trim();


     if (horasTexto.isEmpty()) {
         JOptionPane.showMessageDialog(this, "Por favor, ingresa una cantidad de horas.", "Error", JOptionPane.ERROR_MESSAGE);
         return;
     }

        
        if (rdbtnWindows.isSelected()) {
            mensaje.append("Windows");
        } else if (rdbtnMac.isSelected()) {
            mensaje.append("Mac");
        } else if (rdbtnLinux.isSelected()) {
            mensaje.append("Linux");
        }

        if (chckbxProgramacion.isSelected()) {
            mensaje.append(" - Programación");
        }
        if (chckbxAdministracion.isSelected()) {
            mensaje.append(" - Administración");
        }
        if (chckbxDisenoGrafico.isSelected()) {
            mensaje.append(" - Diseño Gráfico");
        }

        String horas = textField.getText();
        mensaje.append(" - ").append(horas).append(" Hs");

        JOptionPane.showMessageDialog(this, mensaje.toString(), "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }

    public void cambiarVisibilidad(boolean estado) {
        setVisible(estado);
    }
}
