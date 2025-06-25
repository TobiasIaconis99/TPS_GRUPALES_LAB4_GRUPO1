package dao;

import java.util.List;
import entidad.Provincia;

public interface ProvinciaDao {
	
    public List<Provincia> listar();
    public Provincia obtenerPorId(int id);
}