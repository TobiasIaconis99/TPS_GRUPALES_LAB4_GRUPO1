package main;

import java.awt.EventQueue;

import negocio.PersonaNegocio;
import negocioImpl.PersonaNegocioImpl;
import presentacion.controlador.Controlador;
import presentacion.vista.VentanaPrincipal;

public class Principal {

	public static void main(String[] args) {
		
		
		VentanaPrincipal frame = new VentanaPrincipal();
		PersonaNegocio pNeg = new PersonaNegocioImpl();
		
		Controlador controlador = new Controlador(frame, pNeg);
		
		controlador.inicializar();
	}
}