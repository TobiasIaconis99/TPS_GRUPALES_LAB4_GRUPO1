package ejercicio1;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelIngresoPeliculas extends JPanel {

    private JTextField txtNombre;
    private JComboBox<Categorias> comboGenero;
    private JLabel lblID;
    private JButton btnAceptar;
    private DefaultListModel<Peliculas> listModel;
    private JTextField txtNombrePelicula;

    public PanelIngresoPeliculas() {
    	setLayout(null);
    	
    	JLabel lblId = new JLabel("ID");
    	lblId.setBounds(76, 44, 46, 14); 
    	add(lblId);
    	
    	JLabel lblNombrePelicula = new JLabel("Nombre");
    	lblNombrePelicula.setBounds(76, 75, 46, 14);
    	add(lblNombrePelicula);
    	
    	JLabel lblGeneroPelicula = new JLabel("Genero");
    	lblGeneroPelicula.setBounds(76, 112, 46, 14);    	
    	add(lblGeneroPelicula);
    	
    	
    	JLabel lblIDAutomatico = new JLabel("");
    	lblIDAutomatico.setBounds(187, 44, 46, 14);
    	lblIDAutomatico.setText(String.valueOf(Peliculas.devuelveProximoID()));
    	add(lblIDAutomatico);
    	
    	
    	txtNombrePelicula = new JTextField();
    	txtNombrePelicula.setBounds(187, 72, 138, 20);
    	add(txtNombrePelicula);
    	txtNombrePelicula.setColumns(10);
    	
    	comboGenero = new JComboBox<>();
    	comboGenero.setBounds(187, 110, 138, 18);
    	add(comboGenero);

    	comboGenero.addItem(new Categorias(0, "Seleccione un genero"));
    	comboGenero.addItem(new Categorias(1, "Terror"));
    	comboGenero.addItem(new Categorias(2, "Accion"));
    	comboGenero.addItem(new Categorias(3, "Suspenso"));
    	comboGenero.addItem(new Categorias(4, "Romantica"));
    	
    	
    	JButton btnAgregarPelicula = new JButton("Aceptar");
    	btnAgregarPelicula.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			Categorias categoriaSeleccionada = (Categorias) comboGenero.getSelectedItem();
    			Peliculas pelicula = new Peliculas(txtNombrePelicula.getText(), categoriaSeleccionada);    			
    			listModel.addElement(pelicula);
    			lblIDAutomatico.setText(String.valueOf(Peliculas.devuelveProximoID()));
    			txtNombrePelicula.setText("");
    			comboGenero.setSelectedIndex(0);
    			
    		}
    	});
    	btnAgregarPelicula.setBounds(76, 157, 114, 23);
    	add(btnAgregarPelicula);
    	



    }
    
	public void setDefaultListModel(DefaultListModel<Peliculas> listModelRecibido)
	{
		this.listModel = listModelRecibido;
	}
}
