package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PanelAgregarPersona extends JPanel {
	
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDNI;
	private JButton btnAceptar;

	/**
	 * Create the panel.
	 */
	public PanelAgregarPersona() {
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(36, 69, 56, 16);
		add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(124, 66, 116, 22);
		add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(36, 116, 56, 16);
		add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(124, 113, 116, 22);
		add(txtApellido);
		txtApellido.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(36, 184, 56, 16);
		add(lblDni);
		
		txtDNI = new JTextField();
		txtDNI.setBounds(124, 181, 116, 22);
		add(txtDNI);
		txtDNI.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(74, 241, 97, 25);
		add(btnAceptar);

	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}

	public JTextField getTxtDNI() {
		return txtDNI;
	}

	public void setTxtDNI(JTextField txtDNI) {
		this.txtDNI = txtDNI;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}
	
	public void showMen(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
}
