package negocio;

import java.util.List;
import entidad.Localidad;

public interface LocalidadNegocio {
    public List<Localidad> listar();
    public List<Localidad> obtenerPorProvincia(int idProvincia);
    public Localidad obtenerPorId(int id);
}