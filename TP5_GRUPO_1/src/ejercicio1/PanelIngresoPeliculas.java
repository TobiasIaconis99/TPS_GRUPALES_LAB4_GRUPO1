package ejercicio1;

import javax.swing.*;
import java.awt.*;

public class PanelIngresoPeliculas extends JPanel {

    private JTextField txtNombre;
    private JComboBox<Categorias> comboGenero;
    private JLabel lblID;
    private JButton btnAceptar;

    public PanelIngresoPeliculas() {
        // INICIO: CONFIGURACIÓN VISUAL DEL PANEL
        setBackground(Color.WHITE); // Fondo blanco para el panel
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        // FIN: CONFIGURACIÓN VISUAL DEL PANEL

        dibujarControles();
    }

    public void dibujarControles() {
        Font fuenteComun = new Font("Tahoma", Font.PLAIN, 18);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("ID"), gbc);

        lblID = new JLabel(String.valueOf(Peliculas.devuelveProximoID()));
        lblID.setFont(fuenteComun);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(lblID, gbc);

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Nombre"), gbc);

        txtNombre = new JTextField(15);
        txtNombre.setFont(fuenteComun);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtNombre, gbc);

        // Género
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Género"), gbc);

        comboGenero = new JComboBox<>();
        comboGenero.setFont(fuenteComun);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(comboGenero, gbc);

        // Botón Aceptar
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(fuenteComun);

        // INICIO: ESTILO VISUAL DEL BOTÓN
        btnAceptar.setFocusPainted(false);
        btnAceptar.setBackground(new Color(240, 240, 240));
        btnAceptar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        // FIN: ESTILO VISUAL DEL BOTÓN

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAceptar, gbc);
    }

    public void setCategoriasComboModel(DefaultComboBoxModel<Categorias> modelo) {
        comboGenero.setModel(modelo);
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public String getNombreIngresado() {
        return txtNombre.getText().trim();
    }

    public Categorias getCategoriaSeleccionada() {
        return (Categorias) comboGenero.getSelectedItem();
    }

    public int getIdMostrado() {
        return Integer.parseInt(lblID.getText());
    }
}
