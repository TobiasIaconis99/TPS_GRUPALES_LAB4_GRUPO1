package negocio;

import java.util.ArrayList;

import entidad.Seguro;
import entidad.TipoSeguro;

public interface SeguroNegocio {
	public boolean insert(Seguro seguro);
	public boolean update(Seguro seguro);
	public boolean delete(Seguro seguro);
	public ArrayList<Seguro> lista();
}
