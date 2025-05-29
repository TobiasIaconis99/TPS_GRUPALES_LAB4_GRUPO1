package dao;

import java.util.ArrayList;

import entidad.TipoSeguro;

public interface TipoSeguroDao {
	public int lookUpId(String param);
	public ArrayList<TipoSeguro> readAll();
}
