package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorConexionBD {

	private static final String host = "jdbc:mysql://localhost:3306/";
	private static final String dbname = "bdbanco";
	private static final String user = "root";
	private static final String pass = "root";

	private GestorConexionBD() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver JDBC de MySQL cargado exitosamente.");
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(host + dbname, user, pass);
	}

	public static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.err.println("Error al cerrar recursos de BD: " + e.getMessage());
			e.printStackTrace();
		}
	}
}