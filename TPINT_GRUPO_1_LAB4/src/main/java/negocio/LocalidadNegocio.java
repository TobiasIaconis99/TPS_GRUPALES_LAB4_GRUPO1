package negocio;

import java.util.List;
import entidad.Localidad;

public interface LocalidadNegocio {
    public List<Localidad> listar();
    public List<Localidad> listarPorProvincia(int idProvincia);
    public Localidad obtenerPorId(int id);
}