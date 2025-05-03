package ejercicio1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
            PanelIngresoPeliculas panel = new PanelIngresoPeliculas();
            contentPane.add(panel, BorderLayout.CENTER);
            contentPane.revalidate();
            contentPane.repaint();
        });

        // Acción del menú "Listar" (sin implementar aún)
        itemListar.addActionListener(e -> {
            contentPane.removeAll();
            //  PanelListadoPeliculas panel = new ...
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
