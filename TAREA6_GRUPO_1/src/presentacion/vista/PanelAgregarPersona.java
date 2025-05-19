package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PanelAgregarPersona extends JPanel {
	
	private JTextField jTextFieldNombre;
	private JTextField jTextFieldApellido;
	private JTextField jTextFileldDNI;
	private JButton btnAceptar;
	private JLabel lblDNI;
	public PanelAgregarPersona() {
		setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(49, 37, 70, 14);
		add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(49, 93, 70, 14);
		add(lblApellido);
		
		lblDNI = new JLabel("Dni");
		lblDNI.setBounds(49, 149, 70, 14);
		add(lblDNI);
		
		jTextFieldNombre = new JTextField();
		jTextFieldNombre.setBounds(159, 34, 149, 20);
		add(jTextFieldNombre);
		jTextFieldNombre.setColumns(10);
		
		jTextFieldApellido = new JTextField();
		jTextFieldApellido.setBounds(159, 90, 149, 20);
		add(jTextFieldApellido);
		jTextFieldApellido.setColumns(10);
		
		jTextFileldDNI = new JTextField();
		jTextFileldDNI.setBounds(159, 146, 149, 20);
		add(jTextFileldDNI);
		jTextFileldDNI.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(49, 208, 89, 23);
		add(btnAceptar);
	}
	public JTextField getjTextFieldNombre() {
		return jTextFieldNombre;
	}
	public void setjTextFieldNombre(JTextField jTextFieldNombre) {
		this.jTextFieldNombre = jTextFieldNombre;
	}
	public JTextField getjTextFieldApellido() {
		return jTextFieldApellido;
	}
	public void setjTextFieldApellido(JTextField jTextFieldApellido) {
		this.jTextFieldApellido = jTextFieldApellido;
	}
	public JTextField getjTexrFileldDNI() {
		return jTextFileldDNI;
	}
	public void setjTextFileldDNI(JTextField jTexrFileldDNI) {
		this.jTextFileldDNI = jTexrFileldDNI;
	}
	
	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	
	
}
