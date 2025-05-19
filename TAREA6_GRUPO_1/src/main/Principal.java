package main;

import java.awt.EventQueue;

import negocio.PersonaNegocio;
import presentacion.controlador.Controlador;
import presentacion.vista.VentanaPrincipal;

public class Principal {

	public static void main(String[] args) {
		
		VentanaPrincipal frame = new VentanaPrincipal();
		PersonaNegocio pNeg = new PersonaNegocioImpl();
		
		Controlador cont = new Controlador(frame, pNeg);
		
		cont.inicializar();
	}
}