package ejercicio1;

import javax.swing.*;

import java.awt.*;

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
    	lblId.setBounds(45, 44, 46, 14);
    	add(lblId);
    	
    	JLabel lblNombrePelicula = new JLabel("Nombre");
    	lblNombrePelicula.setBounds(45, 89, 46, 14);
    	add(lblNombrePelicula);
    	
    	JLabel lblGeneroPelicula = new JLabel("Genero");
    	lblGeneroPelicula.setBounds(45, 142, 46, 14);
    	add(lblGeneroPelicula);
    	
    	JLabel lblNewLabel_3 = new JLabel("New label");
    	lblNewLabel_3.setBounds(164, 44, 46, 14);
    	add(lblNewLabel_3);
    	
    	txtNombrePelicula = new JTextField();
    	txtNombrePelicula.setBounds(164, 86, 138, 20);
    	add(txtNombrePelicula);
    	txtNombrePelicula.setColumns(10);
    	
    	JComboBox cbxGeneroPeliculas = new JComboBox();
    	cbxGeneroPeliculas.setBounds(164, 138, 121, 22);
    	add(cbxGeneroPeliculas);
    	
    	JButton btnAgregarPelicula = new JButton("Aceptar");
    	btnAgregarPelicula.setBounds(193, 216, 89, 23);
    	add(btnAgregarPelicula);
    	
        DefaultComboBoxModel<Categorias> modelo = new DefaultComboBoxModel<>();
        modelo.addElement(new Categorias("Seleccione un genero"));
        modelo.addElement(new Categorias("Terror"));
        modelo.addElement(new Categorias("Accion"));
        modelo.addElement(new Categorias("Suspenso"));
        modelo.addElement(new Categorias("Romantica"));


    }
    
	public void setDefaultListModel(DefaultListModel<Peliculas> listModelRecibido)
	{
		this.listModel = listModelRecibido;
	}
}
