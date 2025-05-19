package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelModificarPersona extends JPanel {
	
	private JTextField txtApellido;
	private JTextField txtNombre;
	private JTextField txtDNI;
	public JList listPerson;
	public JButton btnModificar;

	public PanelModificarPersona() {
		setLayout(null);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(38, 273, 116, 22);
		add(txtApellido);
		txtApellido.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(178, 273, 116, 22);
		add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDNI = new JTextField();
		txtDNI.setBounds(322, 273, 116, 22);
		add(txtDNI);
		txtDNI.setColumns(10);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(450, 272, 97, 25);
		add(btnModificar);
		
		listPerson = new JList();
		listPerson.setBounds(38, 13, 509, 228);
		add(listPerson);

		
	}


	public void setTxtNombre(String txtNombre) {
		this.txtNombre.setText(txtNombre);
	}


	public void setTxtApellido(String txtApellido) {
		this.txtApellido.setText(txtApellido);
	}


	public void setTxtDNI(String txtDNI) {
		this.txtDNI.setText(txtDNI);
	}
	
	public JButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(JButton btnModificar) {
		this.btnModificar = btnModificar;
	}


	public JList getListPerson() {
		return listPerson;
	}


	public void setListPerson(JList listPerson) {
		this.listPerson = listPerson;
	}
	
	public void showMen(String message) {
		JOptionPane.showMessageDialog(null, message);
	}


	public JTextField getTxtApellido() {
		return txtApellido;
	}


	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}


	public JTextField getTxtNombre() {
		return txtNombre;
	}


	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}


	public JTextField getTxtDNI() {
		return txtDNI;
	}


	public void setTxtDNI(JTextField txtDNI) {
		this.txtDNI = txtDNI;
	}
}
