package presentacion.vista;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	VentanaAgregar ventanaagregar = new VentanaAgregar ();

	public VentanaPrincipal() {
		
        setTitle("Programa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Crear barra de menu
        JMenuBar barra = new JMenuBar();
        JMenu menuPersonas = new JMenu("Personas");

        // Opciones del menu
        JMenuItem opcionAgregar = new JMenuItem("Agregar");
        opcionAgregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        			
    				VentanaAgregar window = new VentanaAgregar();
    				window.frame.setVisible(true);
    			
        		
        	}
        });
        JMenuItem opcionModificar = new JMenuItem("Modificar");
        JMenuItem opcionEliminar = new JMenuItem("Eliminar");
        JMenuItem opcionListar = new JMenuItem("Listar");

        // Agregar opciones al menu
        menuPersonas.add(opcionAgregar);
        menuPersonas.add(opcionModificar);
        menuPersonas.add(opcionEliminar);
        menuPersonas.add(opcionListar);

        // Agregar menu a la barra y barra a la ventana
        barra.add(menuPersonas);
        setJMenuBar(barra);
        
    }
	
    public void CambiarVisibilidad(boolean estado) {
        setVisible(estado);
    }
}