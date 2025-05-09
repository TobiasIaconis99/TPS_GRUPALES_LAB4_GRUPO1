package ejercicio1;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelIngresoPeliculas extends JPanel {

    private JTextField txtNombrePelicula;
    private JComboBox<Categorias> comboGenero;
    private JLabel lblIDAutomatico;
    private JButton btnAceptar;
    private DefaultListModel<Peliculas> listModel;

    public PanelIngresoPeliculas() {
        setLayout(null);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(76, 44, 46, 14);
        add(lblId);

        JLabel lblNombrePelicula = new JLabel("Nombre");
        lblNombrePelicula.setBounds(76, 75, 46, 14);
        add(lblNombrePelicula);

        JLabel lblGeneroPelicula = new JLabel("Género");
        lblGeneroPelicula.setBounds(76, 112, 46, 14);
        add(lblGeneroPelicula);

        lblIDAutomatico = new JLabel("");
        lblIDAutomatico.setBounds(187, 44, 46, 14);
        lblIDAutomatico.setText(String.valueOf(Peliculas.devuelveProximoID()));
        add(lblIDAutomatico);

        txtNombrePelicula = new JTextField();
        txtNombrePelicula.setBounds(186, 72, 155, 20);
        add(txtNombrePelicula);
        txtNombrePelicula.setColumns(10);

        comboGenero = new JComboBox<>();
        comboGenero.setBounds(187, 110, 154, 20);
        add(comboGenero);

        comboGenero.addItem(new Categorias(0, "Seleccione un género"));
        comboGenero.addItem(new Categorias(1, "Terror"));
        comboGenero.addItem(new Categorias(2, "Acción"));
        comboGenero.addItem(new Categorias(3, "Suspenso"));
        comboGenero.addItem(new Categorias(4, "Romántica"));

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(76, 157, 114, 23);
        add(btnAceptar);

        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombrePelicula.getText();
                Categorias categoriaSeleccionada = (Categorias) comboGenero.getSelectedItem();

                // Validación
                if (nombre == null || nombre.trim().isEmpty() ||
                    categoriaSeleccionada == null || categoriaSeleccionada.getId() == 0) {

                    JOptionPane.showMessageDialog(null,
                        "Debe completar todos los campos correctamente:\n- Ingrese un nombre.\n- Seleccione un género.",
                        "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Si todo está bien, se crea y agrega la película
                Peliculas pelicula = new Peliculas(nombre.trim(), categoriaSeleccionada);
                listModel.addElement(pelicula);

                // Actualizar campos
                lblIDAutomatico.setText(String.valueOf(Peliculas.devuelveProximoID()));
                txtNombrePelicula.setText("");
                comboGenero.setSelectedIndex(0);
            }
        });
    }

    public void setDefaultListModel(DefaultListModel<Peliculas> listModelRecibido) {
        this.listModel = listModelRecibido;
    }
}
