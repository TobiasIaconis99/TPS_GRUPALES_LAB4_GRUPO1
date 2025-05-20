package daolmpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import java.util.ArrayList;
//import java.util.List;
import dao.Personadao;
import entidades.Persona;

public class PersonaDaoImpl implements Personadao
{
	private static final String userinsert = "INSERT INTO personas (Dni, Nombre, Apellido) VALUES(?, ?, ?)";
	private static final String userSelect = "SELECT * FROM personas";
	private static final String userDelete = "DELETE FROM personas WHERE Dni = ?";
	private static final String userUpdate = "UPDATE personas SET Nombre = ?, Apellido = ? WHERE DNI = ?";

	
	@Override
	public boolean insert(Persona persona) {
		PreparedStatement statement;
		Connection con = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		
		try {
			statement = con.prepareStatement(userinsert);
			statement.setString(1, persona.getDni());
			statement.setString(2, persona.getNombre());
			statement.setString(3, persona.getApellido());
			if(statement.executeUpdate() > 0) {
				con.commit();
				isInsertExitoso = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return isInsertExitoso;
	}

	@Override
	public boolean delete(Persona persona) {
		
		PreparedStatement statement;
		Connection con = Conexion.getConexion().getSQLConexion();
		boolean deleted = false;
		
		try {
			statement = con.prepareStatement(userDelete);
			statement.setString(1, persona.getApellido());
			if(statement.executeUpdate() > 0) {
				con.commit();
				deleted = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return deleted;
	}

	@Override
	public ArrayList<Persona> readAll() {
		ArrayList<Persona> personas = new ArrayList<Persona>();
		Connection cn = Conexion.getConexion().getSQLConexion();
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(userSelect);
			while(rs.next()) {
				Persona persona = new Persona();
				persona.setDni(rs.getString("dni"));
				persona.setNombre(rs.getString("nombre"));
				persona.setApellido(rs.getString("apellido"));
				personas.add(persona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personas;
	}

	@Override
	public boolean update(Persona persona) {
		PreparedStatement statement;
		Connection con = Conexion.getConexion().getSQLConexion();
		boolean update = false;
		
		try {
			statement = con.prepareStatement(userUpdate);
			statement.setString(1, persona.getNombre());
			statement.setString(2, persona.getApellido());
			statement.setString(3, persona.getDni());
			if(statement.executeUpdate() > 0) {
				con.commit();
				update = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return update;
	}
		
		
}
