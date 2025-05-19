package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import daolmpl.Conexion;
import entidades.Persona;

public class PanelModificarPersona extends JPanel {

    private JList<Persona> listaPersonas;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDNI;
    private JButton btnModificar;

    public PanelModificarPersona() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Font fuente = new Font("Tahoma", Font.PLAIN, 13);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblSeleccion = new JLabel("Seleccione la persona que desea modificar");
        lblSeleccion.setFont(fuente);
        add(lblSeleccion, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        listaPersonas = new JList<>(new DefaultListModel<>());
        listaPersonas.setFont(fuente);
        JScrollPane scroll = new JScrollPane(listaPersonas);
        add(scroll, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 5);
        gbc.gridx = 0;

        txtNombre = new JTextField(8);
        txtNombre.setFont(fuente);
        add(txtNombre, gbc);

        gbc.gridx = 1;
        txtApellido = new JTextField(8);
        txtApellido.setFont(fuente);
        add(txtApellido, gbc);

        gbc.gridx = 2;
        txtDNI = new JTextField(8);
        txtDNI.setFont(fuente);
        add(txtDNI, gbc);

        gbc.gridx = 3;
        btnModificar = new JButton("Modificar");
        btnModificar.setFont(fuente);
        add(btnModificar, gbc);

        listaPersonas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Persona seleccionada = listaPersonas.getSelectedValue();
                if (seleccionada != null) {
                    txtNombre.setText(seleccionada.getNombre());
                    txtApellido.setText(seleccionada.getApellido());
                    txtDNI.setText(String.valueOf(seleccionada.getDni()));
                    revalidate();
                    repaint();
                }
            }
        });

        btnModificar.addActionListener(e -> modificarPersonaSeleccionada());

        setPreferredSize(new Dimension(400, 220));
    }

    public void cargarPersonasDesdeBD() {
        DefaultListModel<Persona> modelo = (DefaultListModel<Persona>) listaPersonas.getModel();
        modelo.clear();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().getSQLConexion();
            String query = "SELECT Dni, nombre, apellido FROM personas";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int dni = Integer.parseInt(rs.getString("Dni").trim());
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                modelo.addElement(new Persona(dni, nombre, apellido));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar personas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "DNI inválido en base de datos. Verificá que sean numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void modificarPersonaSeleccionada() {
        Persona personaSeleccionada = listaPersonas.getSelectedValue();

        if (personaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccioná una persona para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nuevoNombre = txtNombre.getText().trim();
        String nuevoApellido = txtApellido.getText().trim();
        String nuevoDniTexto = txtDNI.getText().trim();

        if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoDniTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completá todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int nuevoDni;
        try {
            nuevoDni = Integer.parseInt(nuevoDniTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConexion().getSQLConexion();
            String query = "UPDATE personas SET nombre = ?, apellido = ?, dni = ? WHERE dni = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevoApellido);
            stmt.setString(3, String.valueOf(nuevoDni));
            stmt.setString(4, String.valueOf(personaSeleccionada.getDni()));

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                conn.commit();
                JOptionPane.showMessageDialog(this, "Persona modificada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPersonasDesdeBD();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la persona a modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar persona: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public JList<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellido() {
        return txtApellido;
    }

    public JTextField getTxtDNI() {
        return txtDNI;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }
}
