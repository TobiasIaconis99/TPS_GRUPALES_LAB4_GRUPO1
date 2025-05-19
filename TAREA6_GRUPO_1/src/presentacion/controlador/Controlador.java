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
	private PersonaNegocio pNegocio;

	public Controlador(VentanaPrincipal frame, PersonaNegocio pn) {
		this.ventanaPrincipal = frame;
		this.pNegocio = pn;
		
		this.pnlAgregar = new PanelAgregarPersona();
		this.ventanaPrincipal.getMntmAgregar().addActionListener(a -> mostrarPanelAgregar(a));	
		this.pnlAgregar.getBtnAceptar().addActionListener(a -> btnAgregarPersona(a));
		this.pnlAgregar.getjTextFieldNombre().addKeyListener(this);
		this.pnlAgregar.getjTextFieldApellido().addKeyListener(this);
		this.pnlAgregar.getjTexrFileldDNI().addKeyListener(this);
		
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
	
	public void mostrarPanelAgregar(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(pnlAgregar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
	}
	
	public void btnAgregarPersona (ActionEvent a) {
		
	}
	
	public void inicializar() {
		this.ventanaPrincipal.setVisible(true);
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
