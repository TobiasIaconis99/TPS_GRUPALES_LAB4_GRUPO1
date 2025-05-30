package daoImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dao.SeguroDao;
import entidad.Seguro;
import entidad.TipoSeguro;

public class SeguroDaoImpl implements SeguroDao {

	@Override
	public boolean insert(Seguro seg) {
		TipoSeguro ts = new TipoSeguro();
		PreparedStatement statement;
		Connection con = Conexion.getConexion().getSQLConexion();
		boolean Isinserted = false;
		
		try {
			statement = con.prepareStatement("INSERT into seguros values(?,?,?,?,?)");
			statement.setInt(1, seg.getIdSeguro());
			statement.setString(2, seg.getDescripcion());
			ts = seg.getTipoSeguro();
			statement.setInt(3, ts.getIdTipoSeguro());
			statement.setBigDecimal(4, BigDecimal.valueOf(seg.getCostoContratacion()));
			statement.setBigDecimal(5, BigDecimal.valueOf(seg.getCostoAsegurado()));
			
			if(statement.executeUpdate() > 0) {
				con.commit();
				Isinserted = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return Isinserted;
	}

	@Override
	public boolean delete(Seguro seg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Seguro seg) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Seguro> readAll() {
		ArrayList<Seguro> seguros = new ArrayList<Seguro>();
		Connection cn = Conexion.getConexion().getSQLConexion();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM seguros INNER JOIN tiposeguros ON seguros.idTipo = tiposeguros.idTipo");
			while(rs.next()) {
				Seguro seg = new Seguro();
				seg.setIdSeguro(rs.getInt("idSeguro"));
				seg.setDescripcion(rs.getString("descripcion"));
				seg.setTipoSeguro(new TipoSeguro(rs.getInt("idTipo"), rs.getString("tiposeguros.descripcion")));
				seg.setCostoContratacion(rs.getDouble("costoContratacion"));
				seg.setCostoAsegurado(rs.getDouble("costoAsegurado"));
				seguros.add(seg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seguros;
	}

	@Override
	public ArrayList<Seguro> filter(int IDtipoSeguro) {
		ArrayList<Seguro> seguros = new ArrayList<Seguro>();
		Connection cn = Conexion.getConexion().getSQLConexion();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM seguros INNER JOIN tiposeguros ON seguros.idTipo = tiposeguros.idTipo where tiposeguros.idtipo=" + IDtipoSeguro);
			while(rs.next()) {
				Seguro seg = new Seguro();
				seg.setIdSeguro(rs.getInt("idSeguro"));
				seg.setDescripcion(rs.getString("descripcion"));
				seg.setTipoSeguro(new TipoSeguro(rs.getInt("idTipo"), rs.getString("tiposeguros.descripcion")));
				seg.setCostoContratacion(rs.getDouble("costoContratacion"));
				seg.setCostoAsegurado(rs.getDouble("costoAsegurado"));
				seguros.add(seg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seguros;
	}

}
