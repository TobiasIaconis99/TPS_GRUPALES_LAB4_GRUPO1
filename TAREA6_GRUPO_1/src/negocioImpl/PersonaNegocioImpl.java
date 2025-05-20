package negocioImpl;

import java.util.ArrayList;

import dao.Personadao;
import daolmpl.PersonaDaoImpl;
import entidades.Persona;
import negocio.PersonaNegocio;

public class PersonaNegocioImpl implements PersonaNegocio {
	
	Personadao personaDao = new PersonaDaoImpl();

	@Override
	public boolean insert(Persona persona) {
		boolean state = false;
		
		state = personaDao.insert(persona);
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public boolean delete(Persona persona) {
		boolean state = false;
		
		state = personaDao.delete(persona);
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public ArrayList<Persona> readAll() {
		return personaDao.readAll();
	}

	@Override
	public boolean update(Persona persona) {
		boolean state = false;
		state = personaDao.update(persona);
		return state;
	}
}
