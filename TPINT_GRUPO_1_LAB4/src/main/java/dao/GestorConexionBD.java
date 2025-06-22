package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorConexionBD {

	private static final String host = "jdbc:mysql://localhost:3306/";
	private static final String dbname = "bdbanco";
	private static final String user = "root";
	private static final String pass = "root";

	// Constructor privado para evitar una instancia
	private GestorConexionBD() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver de MySQL");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(host + dbname, user, pass);
	}
}