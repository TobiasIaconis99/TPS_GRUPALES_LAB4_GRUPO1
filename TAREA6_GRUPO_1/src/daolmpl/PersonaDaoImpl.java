package daolmpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import dao.Personadao;
import entidades.Persona;




	public class PersonaDaoImpl implements Personadao
	{
		private static final String insert = "INSERT INTO personas(Nombre, Apellido, Dni) VALUES(?, ?, ?)";
	
			
		public boolean insert(Persona persona)
		{
			PreparedStatement statement;
			Connection conexion = Conexion.getConexion().getSQLConexion();
			boolean isInsertExitoso = false;
			try
			{
				statement = conexion.prepareStatement(insert);
				statement.setString(1, persona.getNombre());
				statement.setString(2, persona.getApellido());
				statement.setInt(3, persona.getDni());
				if(statement.executeUpdate() > 0)
				{
					conexion.commit();
					isInsertExitoso = true;
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				try {
					conexion.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			
			return isInsertExitoso;
		}
		
		
	
		
		
	}


