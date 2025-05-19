package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import daolmpl.Conexion;

public class PanelEliminarPersona extends JPanel {

    private JList<String> listaPersonas;
    private JButton btnEliminar;

    public PanelEliminarPersona() {
        setLayout(new BorderLayout(10, 10));

        // Lista vacía
        listaPersonas = new JList<>(new DefaultListModel<>());
        listaPersonas.setVisibleRowCount(8);
        JScrollPane scroll = new JScrollPane(listaPersonas);

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar");

        // Armado
        add(new JLabel("Eliminar usuarios", JLabel.CENTER), BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JPanel pie = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pie.add(btnEliminar);
        add(pie, BorderLayout.SOUTH);
    }

    // Cargar personas desde la base de datos
    public void cargarPersonasDesdeBD() {
        DefaultListModel<String> modelo = (DefaultListModel<String>) listaPersonas.getModel();
        modelo.clear();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion().getSQLConexion();
            String query = "SELECT nombre FROM personas"; // ajustá si la tabla/columna se llama distinto
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                modelo.addElement(nombre);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar personas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // No cierres la conexión aquí porque la maneja la clase Conexion
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public JList<String> getListaPersonas() {
        return listaPersonas;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }
}
