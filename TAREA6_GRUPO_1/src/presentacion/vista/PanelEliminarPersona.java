package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import daolmpl.Conexion;
import entidades.Persona;

public class PanelEliminarPersona extends JPanel {

	private JList listPerson;
	private JButton btnEliminar;
	/**
	 * Create the panel.
	 */
	public PanelEliminarPersona() {
		setLayout(null);
		
		listPerson = new JList();
		listPerson.setBounds(122, 28, 205, 244);
		add(listPerson);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(160, 285, 137, 41);
		add(btnEliminar);

	}
	
	
	public JList getListPerson() {
		return listPerson;
	}
	
	public void setListPerson(JList listPerson) {
		this.listPerson = listPerson;
	}
	public JButton getBtnEliminar() {
		return btnEliminar;
	}
	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
	
	public void showMen(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
}
