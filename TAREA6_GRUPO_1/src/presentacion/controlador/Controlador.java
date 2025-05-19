package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import negocio.PersonaNegocio;
import presentacion.vista.PanelAgregarPersona;
import presentacion.vista.VentanaPrincipal;

public class Controlador implements ActionListener, KeyListener {
	
	private VentanaPrincipal ventanaPrincipal;
	private PanelAgregarPersona pnlAgregar;
	//private PanelListarPersona pnlListar;
	//private PanelEliminarPersona pnlEliminar;
	//private PanelModificarPersona pnlModificar;
	private PersonaNegocio pNegocio;

	public Controlador(VentanaPrincipal frame, PersonaNegocio pn) {
		this.ventanaPrincipal = frame;
		this.pNegocio = pn;
		
		this.pnlAgregar = new PanelAgregarPersona();
		//this.pnlEliminar = new PanelEliminarPersona();
		//this.pnlListar = new PanelListarPersona();
		//this.pnlModificar = new PanelModificarPersona();
		
		
		//this.ventanaPrincipal.getMntmAgregar().addActionListener(a -> changeToAdd(a));
		//this.ventanaPrincipal.getMntmModificar().addActionListener(a -> changeToModify(a));
		//this.ventanaPrincipal.getMntmListar().addActionListener(a -> changeToList(a));
		//this.ventanaPrincipal.getMntmEliminar().addActionListener(a -> changeToDelete(a));
		
		
		this.pnlAgregar.getBtnAceptar().addActionListener(a -> btnAgregarPersona(a));
		this.pnlAgregar.getjTextFieldNombre().addKeyListener(this);
		this.pnlAgregar.getjTextFieldApellido().addKeyListener(this);
		this.pnlAgregar.getjTexrFileldDNI().addKeyListener(this);
		
		
		/* EVENTOS PANEL ELIMINAR*/
		//this.pnlEliminar.getBtnEliminar().addActionListener(a -> onBtnEliminarClick(a));
		
		/* Eventos Panel Modificar */
		//this.pnlModificar.listPerson.addListSelectionListener(a-> onPnlModificar(a));
		//this.pnlModificar.getBtnModificar().addActionListener(a-> OnBtnModificar(a));
		//this.pnlModificar.getTxtDNI().setEditable(false);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if((e.getSource() == pnlAgregar.getjTextFieldApellido() || 
		   e.getSource() == pnlAgregar.getjTextFieldNombre()) &&
		   !Character.isLetter(c))
			e.consume();
		else if (e.getSource() == pnlAgregar.getjTexrFileldDNI() &&
				!Character.isDigit(c))
			e.consume();
	}
	
	public void btnAgregarPersona (ActionEvent a) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
