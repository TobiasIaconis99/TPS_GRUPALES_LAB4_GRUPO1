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
	
	private VentanaPrincipal ventanaPrincipal;
	private PanelAgregarPersona pnlAgregar;
	private PanelEliminarPersona pnlEliminar;
	private PanelListarPersona pnlListar;
	private PanelModificarPersona pnlModificar;
	private PersonaNegocio pNegocio;
	
	public Controlador(VentanaPrincipal frame, PersonaNegocio pn) {
		this.ventanaPrincipal = frame;
		this.pNegocio = pn;
		
		this.pnlAgregar = new PanelAgregarPersona();
		this.pnlEliminar = new PanelEliminarPersona();
		this.pnlListar = new PanelListarPersona();
		this.pnlModificar = new PanelModificarPersona();
		
		
		this.ventanaPrincipal.getMntmAgregar().addActionListener(a -> changeToAdd(a));
		this.ventanaPrincipal.getMntmModificar().addActionListener(a -> changeToModify(a));
		this.ventanaPrincipal.getMntmListar().addActionListener(a -> changeToList(a));
		this.ventanaPrincipal.getMntmEliminar().addActionListener(a -> changeToDelete(a));
		
		/* Eventos Panel Agregar */
		this.pnlAgregar.getBtnAceptar().addActionListener(a -> OnBtnAgregarClick_Agregar(a));
		this.pnlAgregar.getTxtNombre().addKeyListener(this);
		this.pnlAgregar.getTxtApellido().addKeyListener(this);
		this.pnlAgregar.getTxtDNI().addKeyListener(this);
		
		/* EVENTOS PANEL ELIMINAR*/
		this.pnlEliminar.getBtnEliminar().addActionListener(a -> onBtnEliminarClick(a));
		
		/* Eventos Panel Modificar */
		this.pnlModificar.listPerson.addListSelectionListener(a-> onPnlModificar(a));
		this.pnlModificar.getBtnModificar().addActionListener(a-> OnBtnModificar(a));
		this.pnlModificar.getTxtDNI().setEditable(false);
	}
	
	
	public void changeToAdd(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(pnlAgregar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
	}
	
	public void changeToDelete(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(pnlEliminar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
		
		/*Carga los datos en JList*/
		JlistLoad();
	}
	
	public void changeToModify(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(pnlModificar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
		/*Carga los datos en JList*/
		JlistLoad();
	}
	
	public void changeToList(ActionEvent a) {
		this.ventanaPrincipal.getContentPane().removeAll();
		this.ventanaPrincipal.getContentPane().add(pnlListar);
		this.ventanaPrincipal.getContentPane().repaint();
		this.ventanaPrincipal.getContentPane().revalidate();
		ArrayList<Persona> list = new ArrayList<Persona>(pNegocio.readAll());
		DefaultTableModel model = new DefaultTableModel(new Object[] {"dni", "nombre", "apellido"}, 0);
		
		//DefaultListModel demoList = new DefaultListModel();
		
		for (Persona persona : list) {
			//demoList.addElement(persona);	
			model.addRow(new Object[] {persona.getDni(), persona.getNombre(), persona.getApellido()});
		}
		this.pnlListar.getTable().setModel(model);
		//this.pnlModificar.listPerson = new JList(demoList);
	}
	
	public void initialize() {
		this.ventanaPrincipal.setVisible(true);
	}
	
	public void OnBtnAgregarClick_Agregar(ActionEvent a) {
		String insertMessage;
		boolean isExist = false;
		String nombre = this.pnlAgregar.getTxtNombre().getText();
		String apellido = this.pnlAgregar.getTxtApellido().getText();
		String dni = this.pnlAgregar.getTxtDNI().getText();
		if(nombre.trim().isEmpty() 	 || 
		   apellido.trim().isEmpty() ||
		   dni.trim().isEmpty()) {
			this.pnlAgregar.showMen("Es necesario completar todos los campos");
			return;
		}
		
		/* Guarda los datos de los textbox en una instancia de Persona, trae todos los datos de la base y los compara para ver si existe */
		Persona nPersona = new Persona(dni, nombre, apellido);
		ArrayList<Persona> list = new ArrayList<Persona>(pNegocio.readAll());
		for(Persona auxPersona : list) {
			if(auxPersona.getDni().equals(nPersona.getDni())) {
				isExist = true;
			}
		}
		
		if(!isExist) {
			/* Prosigue en caso de que no exista */
			boolean state = pNegocio.insert(nPersona);
			if(state==true) {
				insertMessage = "Persona Agregada con exito";
				this.pnlAgregar.getTxtNombre().setText("");
				this.pnlAgregar.getTxtApellido().setText("");
				this.pnlAgregar.getTxtDNI().setText("");
				}
			else {
				insertMessage = "Error al cargar Persona";
				this.pnlAgregar.getTxtNombre().setText("");
				this.pnlAgregar.getTxtApellido().setText("");
				this.pnlAgregar.getTxtDNI().setText("");
			};
		}
		else {
			this.pnlAgregar.getTxtNombre().setText("");
			this.pnlAgregar.getTxtApellido().setText("");
			this.pnlAgregar.getTxtDNI().setText("");
			insertMessage = "El usuario ya existe";
		}
		
		this.pnlAgregar.showMen(insertMessage);
	}
	
	public void onBtnEliminarClick(ActionEvent a) {
		String insertMessage;
		Persona persona = new Persona();
		persona = (Persona) this.pnlEliminar.getListPerson().getSelectedValue();
		boolean state = pNegocio.delete(persona);
		if(state) {
			insertMessage = "Persona eliminada con exito";
			
		} else {
			insertMessage = "Error al eliminar Persona";
		}
		
		this.pnlEliminar.showMen(insertMessage);
		//refresh de JList
		JlistLoad();
	}
	
	public void onPnlModificar(ListSelectionEvent a) {
		Persona aux = (Persona)this.pnlModificar.listPerson.getSelectedValue();
		//Leandro: Esto lo agrego porque se recupera mal la info del JList (a modificar a futuro)
		Persona persona = new Persona(aux.getApellido(), aux.getNombre(), aux.getDni());
		//pnlModificar.setTxtNombre(persona.toString());
		pnlModificar.setTxtApellido(persona.getApellido());
		pnlModificar.setTxtNombre(persona.getNombre());
		pnlModificar.setTxtDNI(persona.getDni());
	}
	
	public void OnBtnModificar(ActionEvent a) {
		String insertMessage;
		Persona persona = new Persona();
		persona = new Persona(pnlModificar.getTxtDNI().getText(), 
				pnlModificar.getTxtNombre().getText(), pnlModificar.getTxtApellido().getText());
		boolean state = pNegocio.update(persona);
		if(state) {
			insertMessage = "Persona modificada con exito";
			
		} else {
			insertMessage = "Error al modificar Persona";
		}
		
		this.pnlModificar.showMen(insertMessage);
		//refresh de JList
		JlistLoad();
	}
	
	public void JlistLoad() {
		DefaultListModel<Persona> newListModel = new DefaultListModel<Persona>();
		ArrayList<Persona> list = new ArrayList<Persona>(pNegocio.readAll());	
		for(Persona persona2 : list) {
			newListModel.addElement(new Persona(persona2.getApellido(),persona2.getNombre() ,persona2.getDni()));
		}
		this.pnlEliminar.getListPerson().setModel(newListModel);
		this.pnlModificar.listPerson.setModel(newListModel);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if((e.getSource() == pnlAgregar.getTxtNombre() || 
		   e.getSource() == pnlAgregar.getTxtApellido()) &&
		   !Character.isLetter(c))
			e.consume();
		else if (e.getSource() == pnlAgregar.getTxtDNI() &&
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
