package dao;
import java.util.ArrayList;
import entidades.Persona;

public interface Personadao {
	public boolean insert(Persona persona);
	public boolean delete(Persona persona);
	public boolean update(Persona persona);
	public ArrayList<Persona> readAll();
}