package ejercicio1;

import javax.swing.JPanel;
import javax.swing.JMenuBar;

import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

public class PanelMostrarPeliculas extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultListModel<Peliculas> listModel;
	private JList<Peliculas> list;
	/**
	 * Create the panel.
	 */
	public PanelMostrarPeliculas() {
		setLayout(null);
		
		JLabel lblPelículas = new JLabel("Películas");
		lblPelículas.setBounds(43, 107, 62, 13);
		add(lblPelículas);
		
		DefaultListModel<Peliculas> listModel = new DefaultListModel<Peliculas>();
		
		list = new JList();
		list.setBounds(99, 43, 314, 202);
		add(list);
		
		
	}
	
	public void setDefaultListModel(DefaultListModel<Peliculas> listModel)
	{
		this.listModel = listModel;
		list.setModel(this.listModel);
		ordenarPeliculasAlfabeticamente();
	}
	
	
	public void ordenarPeliculasAlfabeticamente() {
	
		
	    TreeSet<Peliculas> peliculasOrdenadasAlfabeticamente = new TreeSet<>();
	    for (int i = 0; i < listModel.size(); i++) {
	    	peliculasOrdenadasAlfabeticamente.add(listModel.getElementAt(i));
	    }

	    listModel.clear();
	    for (Peliculas p : peliculasOrdenadasAlfabeticamente) {
	        listModel.addElement(p);
	    }

	    list.setModel(listModel);
	}
}
