package presentacion.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private VentanaAgregar ventanaagregar = new VentanaAgregar(); // como antes
    private PanelEliminarPersona panelEliminar = new PanelEliminarPersona();

    private JPanel panelCentral;

    public VentanaPrincipal() {
        setTitle("Programa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar barra = new JMenuBar();
        JMenu menuPersonas = new JMenu("Personas");

        JMenuItem opcionAgregar = new JMenuItem("Agregar");
        opcionAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Se usa como antes
                VentanaAgregar window = new VentanaAgregar();
                window.frame.setVisible(true);
            }
        });

        JMenuItem opcionModificar = new JMenuItem("Modificar");
        JMenuItem opcionEliminar = new JMenuItem("Eliminar");
        JMenuItem opcionListar = new JMenuItem("Listar");

        menuPersonas.add(opcionAgregar);
        menuPersonas.add(opcionModificar);
        menuPersonas.add(opcionEliminar);
        menuPersonas.add(opcionListar);
        barra.add(menuPersonas);
        setJMenuBar(barra);

        panelCentral = new JPanel(new CardLayout());

        JPanel panelInicio = new JPanel();
        panelCentral.add(panelInicio, "PANEL_INICIO");
        panelCentral.add(panelEliminar, "PANEL_ELIMINAR");

        add(panelCentral, BorderLayout.CENTER);

        opcionEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelEliminar.cargarPersonasDesdeBD();
                CardLayout cl = (CardLayout) panelCentral.getLayout();
                cl.show(panelCentral, "PANEL_ELIMINAR");
            }
        });

        CardLayout cl = (CardLayout) panelCentral.getLayout();
        cl.show(panelCentral, "PANEL_INICIO");
    }

    public void CambiarVisibilidad(boolean estado) {
        setVisible(estado);
    }
}
