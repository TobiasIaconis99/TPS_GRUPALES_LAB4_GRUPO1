package ejercicio1;

import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import java.awt.BorderLayout;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaPrincipal() {
		setTitle("Programa");
		setSize(450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuPeliculas = new JMenu("Películas");
		menuBar.add(menuPeliculas);

		JMenuItem itemAgregar = new JMenuItem("Agregar");
		JMenuItem itemListar = new JMenuItem("Listar");

		menuPeliculas.add(itemAgregar);
		menuPeliculas.add(itemListar);
	}

	public void CambiarVisivilidad(boolean estado) {
		setVisible(estado);
	}
}

