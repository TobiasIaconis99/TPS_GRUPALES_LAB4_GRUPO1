package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import daolmpl.Conexion;
import entidades.Persona;

public class PanelEliminarPersona extends JPanel {

    private JList<Persona> listaPersonas;
    private JButton btnEliminar;

    public PanelEliminarPersona() {
        setLayout(new BorderLayout(10, 10));

        listaPersonas = new JList<>(new DefaultListModel<>());
        listaPersonas.setVisibleRowCount(8);
        JScrollPane scroll = new JScrollPane(listaPersonas);

        btnEliminar = new JButton("Eliminar");

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPersonaSeleccionada();
            }
        });

        add(new JLabel("Eliminar usuarios", JLabel.CENTER), BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JPanel pie = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pie.add(btnEliminar);
        add(pie, BorderLayout.SOUTH);
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
                int dni = rs.getInt("Dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                modelo.addElement(new Persona(dni, nombre, apellido));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar personas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void eliminarPersonaSeleccionada() {
        Persona personaSeleccionada = listaPersonas.getSelectedValue();

        if (personaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccioná una persona para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que querés eliminar a " + personaSeleccionada.getNombre() + " " +
                        personaSeleccionada.getApellido() + " (DNI: " + personaSeleccionada.getDni() + ")?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConexion().getSQLConexion();
            String query = "DELETE FROM personas WHERE Dni = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, personaSeleccionada.getDni());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                conn.commit();
                JOptionPane.showMessageDialog(this, "Persona eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPersonasDesdeBD();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la persona a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar persona: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    public JButton getBtnEliminar() {
        return btnEliminar;
    }
}
