package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entidades.Persona;
import negocio.PersonaNegocio;
import presentacion.vista.PanelAgregarPersona;
import presentacion.vista.PanelEliminarPersona;
import presentacion.vista.PanelListarPersona;
import presentacion.vista.PanelModificarPersona;
import presentacion.vista.VentanaPrincipal;

public class Controlador implements ActionListener, KeyListener {
	
	private PersonaNegocio personaaNegocio;
	
	private VentanaPrincipal ventanaPrincipal;
	private PanelAgregarPersona panelAgregar;
	private PanelModificarPersona panelModificar;
	private PanelEliminarPersona panelEliminar;
	private PanelListarPersona panelListar;
	
	
	public Controlador(VentanaPrincipal frame, PersonaNegocio pn) {
		this.ventanaPrincipal = frame;
		this.personaaNegocio = pn;
		
		this.panelAgregar = new PanelAgregarPersona();
		this.panelEliminar = new PanelEliminarPersona();
		this.panelListar = new PanelListarPersona();
		this.panelModificar = new PanelModificarPersona();
		
		
		this.ventanaPrincipal.getMntmAgregar().addActionListener(a -> mostrarPanelAgregar(a));
		this.ventanaPrincipal.getMntmModificar().addActionListener(a -> mostrarPanelModificar(a));
		this.ventanaPrincipal.getMntmListar().addActionListener(a -> mostrarPanelListar(a));
		this.ventanaPrincipal.getMntmEliminar().addActionListener(a -> mostrarPanelEliminar(a));
		
		
		this.panelAgregar.getBtnAceptar().addActionListener(a -> OnBtnAgregarClick_Agregar(a));
		this.panelAgregar.getTxtNombre().addKeyListener(this);
		this.panelAgregar.getTxtApellido().addKeyListener(this);
		this.panelAgregar.getTxtDNI().addKeyListener(this);
		
		
		this.panelEliminar.getBtnEliminar().addActionListener(a -> onBtnEliminarClick(a));
		
	
		this.panelModificar.listPerson.addListSelectionListener(a-> onPnlModificar(a));
		this.panelModificar.getBtnModificar().addActionListener(a-> OnBtnModificar(a));
		this.panelModificar.getTxtDNI().setEditable(false);
	}
	
	
	public void mostrarPanelAgregar(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(panelAgregar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
	}
	
	public void mostrarPanelEliminar(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(panelEliminar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
		
		
		JlistLoad();
	}
	
	public void mostrarPanelModificar(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(panelModificar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
	
		JlistLoad();
	}
	
	public void mostrarPanelListar(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(panelListar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
		ArrayList<Persona> list = new ArrayList<Persona>(personaaNegocio.readAll());
		DefaultTableModel model = new DefaultTableModel(new Object[] {"dni", "nombre", "apellido"}, 0);
		
		
		
		for (Persona persona : list) {
			
			model.addRow(new Object[] {persona.getDni(), persona.getNombre(), persona.getApellido()});
		}
		this.panelListar.getTable().setModel(model);
		
	}
	
	public void inicializar() {
		this.ventanaPrincipal.setVisible(true);
	}
	
	public void OnBtnAgregarClick_Agregar(ActionEvent a) {
		String insertMessage;
		boolean isExist = false;
		String nombre = this.panelAgregar.getTxtNombre().getText();
		String apellido = this.panelAgregar.getTxtApellido().getText();
		String dni = this.panelAgregar.getTxtDNI().getText();
		
		if(nombre.trim().isEmpty() 	 || 
		   apellido.trim().isEmpty() ||
		   dni.trim().isEmpty()) {
			this.panelAgregar.showMen("Es necesario completar todos los campos");
			return;
		}
		
		
		Persona nPersona = new Persona(dni, nombre, apellido);
		ArrayList<Persona> list = new ArrayList<Persona>(personaaNegocio.readAll());
		for(Persona auxPersona : list) {
			if(auxPersona.getDni().equals(nPersona.getDni())) {
				isExist = true;
			}
		}
		
		if(!isExist) {
			
			boolean state = personaaNegocio.insert(nPersona);
			if(state==true) {
				insertMessage = "Persona Agregada con exito";
				this.panelAgregar.getTxtNombre().setText("");
				this.panelAgregar.getTxtApellido().setText("");
				this.panelAgregar.getTxtDNI().setText("");
				}
			else {
				insertMessage = "Error al cargar Persona";
				this.panelAgregar.getTxtNombre().setText("");
				this.panelAgregar.getTxtApellido().setText("");
				this.panelAgregar.getTxtDNI().setText("");
			};
		}
		else {
			this.panelAgregar.getTxtNombre().setText("");
			this.panelAgregar.getTxtApellido().setText("");
			this.panelAgregar.getTxtDNI().setText("");
			insertMessage = "El usuario ya existe";
		}
		
		this.panelAgregar.showMen(insertMessage);
	}
	
	public void onBtnEliminarClick(ActionEvent a) {
		String insertMessage;
		Persona persona = new Persona();
		persona = (Persona) this.panelEliminar.getListPerson().getSelectedValue();
		boolean state = personaaNegocio.delete(persona);
		if(state) {
			insertMessage = "Persona eliminada con exito";
			
		} else {
			insertMessage = "Error al eliminar Persona";
		}
		
		this.panelEliminar.showMen(insertMessage);
	
		JlistLoad();
	}
	
	public void onPnlModificar(ListSelectionEvent a) {
		Persona aux = (Persona)this.panelModificar.listPerson.getSelectedValue();
		
		Persona persona = new Persona(aux.getApellido(), aux.getNombre(), aux.getDni());
		
		panelModificar.setTxtApellido(persona.getApellido());
		panelModificar.setTxtNombre(persona.getNombre());
		panelModificar.setTxtDNI(persona.getDni());
	}
	
	public void OnBtnModificar(ActionEvent a) {
		String insertMessage;
		Persona persona = new Persona();
		persona = new Persona(panelModificar.getTxtDNI().getText(), 
				panelModificar.getTxtNombre().getText(), panelModificar.getTxtApellido().getText());
		boolean state = personaaNegocio.update(persona);
		if(state) {
			insertMessage = "Persona modificada con exito";
			
		} else {
			insertMessage = "Error al modificar Persona";
		}
		
		this.panelModificar.showMen(insertMessage);
		
		JlistLoad();
	}
	
	public void JlistLoad() {
		DefaultListModel<Persona> newListModel = new DefaultListModel<Persona>();
		ArrayList<Persona> list = new ArrayList<Persona>(personaaNegocio.readAll());	
		for(Persona persona2 : list) {
			newListModel.addElement(new Persona(persona2.getApellido(),persona2.getNombre() ,persona2.getDni()));
		}
		this.panelEliminar.getListPerson().setModel(newListModel);
		this.panelModificar.listPerson.setModel(newListModel);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if((e.getSource() == panelAgregar.getTxtNombre() || 
		   e.getSource() == panelAgregar.getTxtApellido()) &&
		    Character.isDigit(c))
			e.consume();
		else if (e.getSource() == panelAgregar.getTxtDNI() &&
				!Character.isDigit(c))
			e.consume();
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
