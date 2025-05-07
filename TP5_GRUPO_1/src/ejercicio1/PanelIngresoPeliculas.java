package ejercicio1;

import javax.swing.*;

import java.awt.*;

public class PanelIngresoPeliculas extends JPanel {

    private JTextField txtNombre;
    private JComboBox<Categorias> comboGenero;
    private JLabel lblID;
    private JButton btnAceptar;
    private DefaultListModel<Peliculas> listModel;
    private JTextField textField;

    public PanelIngresoPeliculas() {
    	setLayout(null);
    	
    	JLabel lblNewLabel = new JLabel("ID");
    	lblNewLabel.setBounds(45, 44, 46, 14);
    	add(lblNewLabel);
    	
    	JLabel lblNewLabel_1 = new JLabel("Nombre");
    	lblNewLabel_1.setBounds(45, 89, 46, 14);
    	add(lblNewLabel_1);
    	
    	JLabel lblNewLabel_2 = new JLabel("Genero");
    	lblNewLabel_2.setBounds(45, 142, 46, 14);
    	add(lblNewLabel_2);
    	
    	JLabel lblNewLabel_3 = new JLabel("New label");
    	lblNewLabel_3.setBounds(164, 44, 46, 14);
    	add(lblNewLabel_3);
    	
    	textField = new JTextField();
    	textField.setBounds(164, 86, 138, 20);
    	add(textField);
    	textField.setColumns(10);
    	
    	JComboBox comboBox = new JComboBox();
    	comboBox.setBounds(164, 138, 121, 22);
    	add(comboBox);
    	
    	JButton btnNewButton = new JButton("New button");
    	btnNewButton.setBounds(193, 216, 89, 23);
    	add(btnNewButton);


    }
    
	public void setDefaultListModel(DefaultListModel<Peliculas> listModelRecibido)
	{
		this.listModel = listModelRecibido;
	}
}
