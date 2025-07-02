package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import InformesDTO.InformeAdto;
import InformesDTO.InformeBdto;
import dao.GestorConexionBD;

import dao.Informesdao;

public class InformesDaoImpl implements Informesdao{

	@Override
	public List<InformeAdto> obtenerEstadisticasPorTipoMovimiento() {	

	        List<InformeAdto> lista = new ArrayList<>();
	        Connection conexion = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        String query = "SELECT tm.nombre AS tipoMovimiento, COUNT(*) AS cantidad, SUM(m.importe) AS total FROM movimiento m JOIN tipomovimiento tm ON m.idTipoMovimiento = tm.idTipoMovimiento WHERE tm.idTipoMovimiento IN (1, 2, 4) GROUP BY tm.nombre;";

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

	public List<InformeAdto> obtenerEgresos(){
		
        List<InformeAdto> lista = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String query = "SELECT tm.nombre AS tipoMovimiento, COUNT(*) AS cantidad, SUM(m.importe) AS total FROM movimiento m JOIN tipomovimiento tm ON m.idTipoMovimiento = tm.idTipoMovimiento WHERE tm.idTipoMovimiento IN (3,5) GROUP BY tm.nombre;";

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
	
	@Override
	public List<InformeAdto> obtenerIngresosPorMesYAnio(int mes, int anio) {
	    List<InformeAdto> lista = new ArrayList<>();
	    StringBuilder query = new StringBuilder(
	        "SELECT tm.nombre AS tipoMovimiento, COUNT(*) AS cantidad, SUM(m.importe) AS total " +
	        "FROM movimiento m " +
	        "JOIN tipomovimiento tm ON m.idTipoMovimiento = tm.idTipoMovimiento " +
	        "WHERE tm.idTipoMovimiento IN (1, 2, 4) "
	    );

	    if (mes != -1) query.append("AND MONTH(m.fecha) = ? ");
	    if (anio != -1) query.append("AND YEAR(m.fecha) = ? ");
	    query.append("GROUP BY tm.nombre");

	    try (Connection conexion = GestorConexionBD.getConnection();
	         PreparedStatement stmt = conexion.prepareStatement(query.toString())) {

	        int index = 1;
	        if (mes != -1) stmt.setInt(index++, mes);
	        if (anio != -1) stmt.setInt(index++, anio);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            InformeAdto em = new InformeAdto();
	            em.setTipoMovimiento(rs.getString("tipoMovimiento"));
	            em.setCantidad(rs.getInt("cantidad"));
	            em.setTotal(rs.getBigDecimal("total"));
	            lista.add(em);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
	}

	@Override
	public List<InformeAdto> obtenerEgresosPorMesYAnio(int mes, int anio) {
	    List<InformeAdto> lista = new ArrayList<>();
	    StringBuilder query = new StringBuilder(
	        "SELECT tm.nombre AS tipoMovimiento, COUNT(*) AS cantidad, SUM(m.importe) AS total " +
	        "FROM movimiento m " +
	        "JOIN tipomovimiento tm ON m.idTipoMovimiento = tm.idTipoMovimiento " +
	        "WHERE tm.idTipoMovimiento IN (3, 5) "
	    );

	    if (mes != -1) query.append("AND MONTH(m.fecha) = ? ");
	    if (anio != -1) query.append("AND YEAR(m.fecha) = ? ");
	    query.append("GROUP BY tm.nombre");

	    try (Connection conexion = GestorConexionBD.getConnection();
	         PreparedStatement stmt = conexion.prepareStatement(query.toString())) {

	        int index = 1;
	        if (mes != -1) stmt.setInt(index++, mes);
	        if (anio != -1) stmt.setInt(index++, anio);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            InformeAdto em = new InformeAdto();
	            em.setTipoMovimiento(rs.getString("tipoMovimiento"));
	            em.setCantidad(rs.getInt("cantidad"));
	            em.setTotal(rs.getBigDecimal("total"));
	            lista.add(em);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
	}

	@Override
	public List<InformeBdto> listarClientesConMasMovimientos(int mes, int anio) {
	    List<InformeBdto> lista = new ArrayList<>();

	    StringBuilder query = new StringBuilder(
	        "SELECT c.nombre AS nombreCliente, c.dni, COUNT(m.idMovimiento) AS cantidadMovimientos " +
	        "FROM cliente c " +
	        "JOIN cuenta ct ON c.id = ct.idCliente " +
	        "JOIN movimiento m ON m.idCuenta = ct.idCuenta " +
	        "WHERE 1=1 "
	    );

	    if (mes != -1) query.append("AND MONTH(m.fecha) = ? ");
	    if (anio != -1) query.append("AND YEAR(m.fecha) = ? ");
	    query.append("GROUP BY c.dni, c.nombre ORDER BY cantidadMovimientos DESC LIMIT 5");

	    try (Connection conexion = GestorConexionBD.getConnection();
	         PreparedStatement stmt = conexion.prepareStatement(query.toString())) {

	        int index = 1;
	        if (mes != -1) stmt.setInt(index++, mes);
	        if (anio != -1) stmt.setInt(index++, anio);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            InformeBdto dto = new InformeBdto();
	            dto.setNombreCliente(rs.getString("nombreCliente"));
	            dto.setDNI(rs.getInt("dni"));
	            dto.setCantidadMovimientos(rs.getInt("cantidadMovimientos"));
	            lista.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
	}

}