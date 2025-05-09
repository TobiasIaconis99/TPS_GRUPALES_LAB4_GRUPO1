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

        JMenu mnPeliculas = new JMenu("Peliculas");
        menuBar.add(mnPeliculas);

        listModel = new DefaultListModel<Peliculas>();

        contentPane = new JPanel();
        contentPane.setLayout(null); 
        setContentPane(contentPane); 

        JMenuItem itemAgregarPeliculas = new JMenuItem("Agregar");
        itemAgregarPeliculas.addActionListener(new ActionListener() {
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
        mnPeliculas.add(itemAgregarPeliculas);

        JMenuItem itemListarPelicula = new JMenuItem("Listar");
        itemListarPelicula.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 contentPane.removeAll();
                 PanelMostrarPeliculas panelMostrar = new PanelMostrarPeliculas();
                 panelMostrar.setBounds(0, 0, 400, 300);
                 panelMostrar.setDefaultListModel(listModel);
                 contentPane.add(panelMostrar);
                 contentPane.repaint();
                 contentPane.revalidate();
        	}
        });
        mnPeliculas.add(itemListarPelicula);
    }

    public void CambiarVisibilidad(boolean estado) {
        setVisible(estado);
    }


}
