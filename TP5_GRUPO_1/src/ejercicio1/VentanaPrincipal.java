package ejercicio1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static  DefaultListModel<Peliculas> listModel;

    public VentanaPrincipal() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 475, 399);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("New menu");
        menuBar.add(mnNewMenu);

        listModel = new DefaultListModel<Peliculas>();

        contentPane = new JPanel();
        contentPane.setLayout(null); 
        setContentPane(contentPane); 

        JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentPane.removeAll();
                PanelIngresoPeliculas panelIngreso = new PanelIngresoPeliculas();
                panelIngreso.setBounds(0, 0, 400, 300);
                panelIngreso.setDefaultListModel(listModel);
                contentPane.add(panelIngreso);
                contentPane.repaint();
                contentPane.revalidate();
            }
        });
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
        mnNewMenu.add(mntmNewMenuItem_1);
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
