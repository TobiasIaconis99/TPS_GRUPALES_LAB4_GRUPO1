package ejercicio1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public VentanaPrincipal() {
        setTitle("Programa");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuPeliculas = new JMenu("Películas");
        menuBar.add(menuPeliculas);

        JMenuItem itemAgregar = new JMenuItem("Agregar");
        JMenuItem itemListar = new JMenuItem("Listar");

        menuPeliculas.add(itemAgregar);
        menuPeliculas.add(itemListar);

        // Acción del menú "Agregar"
        itemAgregar.addActionListener(e -> {
            contentPane.removeAll();

            // Crear el panel
            PanelIngresoPeliculas panel = new PanelIngresoPeliculas();

            // Crear y llenar el modelo del combo
            DefaultComboBoxModel<Categorias> modelo = new DefaultComboBoxModel<>();
            modelo.addElement(new Categorias("Seleccione un genero"));
            modelo.addElement(new Categorias("Terror"));
            modelo.addElement(new Categorias("Accion"));
            modelo.addElement(new Categorias("Suspenso"));
            modelo.addElement(new Categorias("Romantica"));

            // Asignar modelo al combo
            panel.setCategoriasComboModel(modelo);

            // Agregar el panel
            contentPane.add(panel, BorderLayout.CENTER);
            contentPane.revalidate();
            contentPane.repaint();
        });

        // Acción del menú "Listar" (sin implementar aún)
        itemListar.addActionListener(e -> {
            contentPane.removeAll();
            // PanelListadoPeliculas panel = new ...
            contentPane.revalidate();
            contentPane.repaint();
        });
    }

    public void CambiarVisibilidad(boolean estado) {
        setVisible(estado);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.CambiarVisibilidad(true);
        });
    }
}
