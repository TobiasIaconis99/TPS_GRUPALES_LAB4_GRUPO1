package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import InformesDTO.InformeAdto;
import dao.GestorConexionBD;
import dao.Informesdao;

public class InformesDaoImpl implements Informesdao{

	@Override
	public List<InformeAdto> obtenerEstadisticasPorTipoMovimiento() {	

	        List<InformeAdto> lista = new ArrayList<>();
	        Connection conexion = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        String query = "SELECT tm.nombre AS tipoMovimiento, COUNT(*) AS cantidad, SUM(m.importe) AS total FROM movimiento m JOIN tipomovimiento tm ON m.idTipoMovimiento = tm.idTipoMovimiento GROUP BY tm.nombre";

	        try {
	        	conexion = GestorConexionBD.getConnection(); 
	            stmt = conexion.prepareStatement(query);
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	            	InformeAdto em = new InformeAdto();
	                em.setTipoMovimiento(rs.getString("tipoMovimiento"));
	                em.setCantidad(rs.getInt("cantidad"));
	                em.setTotal(rs.getBigDecimal("total"));
	                lista.add(em);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try { if (rs != null) rs.close(); } catch (Exception e) {}
	            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
	            try { if (conexion != null) conexion.close(); } catch (Exception e) {}
	        }

	        return lista;
	    }
	}

}
