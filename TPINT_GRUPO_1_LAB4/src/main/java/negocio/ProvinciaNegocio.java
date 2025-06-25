package negocio;

import java.util.List;
import entidad.Provincia;

public interface ProvinciaNegocio {
	
    public List<Provincia> listar();
    public Provincia obtenerPorId(int id);
}