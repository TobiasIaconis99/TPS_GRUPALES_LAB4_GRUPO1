package daoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.tomcat.dbcp.dbcp2.PStmtKey;


import dao.TipoSeguroDao;
import entidad.Seguro;
import entidad.TipoSeguro;

public class TipoSeguroDaoImpl implements TipoSeguroDao{
	
	
	public int lookUpId(String param) {
		Connection cn = Conexion.getConexion().getSQLConexion();
		int id = 0;
		try {
			String Query = String.format("SELECT * FROM tiposeguros WHERE tiposeguros.descripcion = '%s'", param);
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(Query);
			while(rs.next())
			id = rs.getInt("idTipo");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public ArrayList<TipoSeguro> readAll() {
		ArrayList<TipoSeguro> tsList = new ArrayList<TipoSeguro>();
		Connection cn = Conexion.getConexion().getSQLConexion();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM segurosgroup.tiposeguros;");
			while(rs.next()) {
				TipoSeguro ts = new TipoSeguro();
				ts.setIdTipoSeguro(rs.getInt("idTipo"));
				ts.setDescripcion(rs.getString("descripcion"));
				tsList.add(ts);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsList;
	}
}
