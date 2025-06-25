package dao;

import java.util.List;
import entidad.Localidad;

public interface LocalidadDao {
    public List<Localidad> listar();
    public List<Localidad> listarPorProvincia(int idProvincia);
    public Localidad obtenerPorId(int id);
}