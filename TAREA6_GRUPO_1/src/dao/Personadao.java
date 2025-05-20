package dao;
import java.util.ArrayList;
import entidades.Persona;

public interface Personadao {
	
	public boolean agregarPersona(Persona persona);
	
	public boolean modficarPersona(Persona persona);
	
	public boolean borrarPersona(Persona persona);
	
	public ArrayList<Persona> leerPersonas();
}