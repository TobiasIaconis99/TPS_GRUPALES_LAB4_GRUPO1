package dao;

import java.util.ArrayList;

import entidad.Seguro;


public interface SeguroDao {
	public boolean insert(Seguro seg);
	public boolean delete(Seguro seg);
	public boolean update(Seguro seg);
	public ArrayList<Seguro> filter(int id);
	public ArrayList<Seguro> readAll();
}
