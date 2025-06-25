package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDao;
import dao.GestorConexionBD;
import entidad.Cliente;
import entidad.Localidad;
import entidad.Provincia;
import entidad.Usuario;

public class ClienteDaoImpl implements ClienteDao {

	private Connection getConnection() throws SQLException {
		return GestorConexionBD.getConnection();
	}

	@Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String query = "SELECT c.*, " +
                        "u.idUsuario, u.nombreUsuario, u.clave, u.tipoUsuario, u.estado AS usuarioEstado, " +
                        "l.idLocalidad, l.nombreLocalidad, l.idProvincia AS lIdProvincia, l.estado AS localidadEstado, " +
                        "p.idProvincia AS pIdProvincia, p.nombreProvincia, p.estado AS provinciaEstado " +
                        "FROM Cliente c " +
                        "JOIN Usuario u ON c.idUsuario = u.idUsuario " +
                        "JOIN Localidad l ON c.idLocalidad = l.idLocalidad " +
                        "JOIN Provincia p ON l.idProvincia = p.idProvincia " +
                        "WHERE c.estado = 1"; // Filtra solo clientes activos

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setDni(rs.getString("dni"));
                c.setCuil(rs.getString("cuil"));
                c.setNombre(rs.getString("nombre"));
                c.setApellido(rs.getString("apellido"));
                c.setSexo(rs.getString("sexo"));
                c.setNacionalidad(rs.getString("nacionalidad"));
                c.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                c.setDireccion(rs.getString("direccion"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                c.setEstado(rs.getBoolean("estado"));

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombreUsuario(rs.getString("nombreUsuario"));
                u.setClave(rs.getString("clave"));
                u.setTipoUsuario(rs.getString("tipoUsuario"));
                u.setEstado(rs.getBoolean("usuarioEstado"));
                c.setUsuario(u);

                Provincia p = new Provincia();
                p.setIdProvincia(rs.getInt("pIdProvincia"));
                p.setNombreProvincia(rs.getString("nombreProvincia"));
                p.setEstado(rs.getBoolean("provinciaEstado"));

                Localidad l = new Localidad();
                l.setIdLocalidad(rs.getInt("idLocalidad"));
                l.setNombreLocalidad(rs.getString("nombreLocalidad"));
                l.setProvincia(p);
                l.setEstado(rs.getBoolean("localidadEstado"));
                c.setLocalidad(l);

                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Cliente obtenerPorDni(String dni) {
        Cliente cliente = null;
        String query = "SELECT c.*, " +
                       "u.idUsuario, u.nombreUsuario, u.clave, u.tipoUsuario, u.estado AS usuarioEstado, " +
                       "l.idLocalidad, l.nombreLocalidad, l.estado AS localidadEstado, " +
                       "p.idProvincia, p.nombreProvincia, p.estado AS provinciaEstado " +
                       "FROM Cliente c " +
                       "JOIN Usuario u ON c.idUsuario = u.idUsuario " +
                       "JOIN Localidad l ON c.idLocalidad = l.idLocalidad " +
                       "JOIN Provincia p ON l.idProvincia = p.idProvincia " +
                       "WHERE c.dni = ? AND c.estado = 1"; 

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setDni(rs.getString("dni"));
                    cliente.setCuil(rs.getString("cuil"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setSexo(rs.getString("sexo"));
                    cliente.setNacionalidad(rs.getString("nacionalidad"));
                    cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setCorreo(rs.getString("correo"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setEstado(rs.getBoolean("estado"));

                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombreUsuario(rs.getString("nombreUsuario"));
                    u.setClave(rs.getString("clave"));
                    u.setTipoUsuario(rs.getString("tipoUsuario"));
                    u.setEstado(rs.getBoolean("usuarioEstado"));
                    cliente.setUsuario(u);

                    Provincia p = new Provincia();
                    p.setIdProvincia(rs.getInt("idProvincia"));
                    p.setNombreProvincia(rs.getString("nombreProvincia"));
                    p.setEstado(rs.getBoolean("provinciaEstado"));

                    Localidad l = new Localidad();
                    l.setIdLocalidad(rs.getInt("idLocalidad"));
                    l.setNombreLocalidad(rs.getString("nombreLocalidad"));
                    l.setProvincia(p); 
                    l.setEstado(rs.getBoolean("localidadEstado"));
                    cliente.setLocalidad(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

	@Override
	public boolean agregar(Cliente c) {
		String queryInsertUsuario = "INSERT INTO Usuario (nombreUsuario, clave, tipoUsuario, estado) VALUES (?, ?, ?, ?)";
		String queryInsertCliente = "INSERT INTO Cliente (dni, cuil, sexo, nombre, apellido, nacionalidad, fechaNacimiento, direccion, idLocalidad, telefono, correo, idUsuario, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement psUsuario = null;
		PreparedStatement psCliente = null;
		ResultSet rs = null;
		boolean exito = false;

		try {
			conn = getConnection();
			conn.setAutoCommit(false); 

			psUsuario = conn.prepareStatement(queryInsertUsuario, Statement.RETURN_GENERATED_KEYS);
			psUsuario.setString(1, c.getUsuario().getNombreUsuario());
			psUsuario.setString(2, c.getUsuario().getClave());
			psUsuario.setString(3, c.getUsuario().getTipoUsuario());
			psUsuario.setBoolean(4, c.getUsuario().isEstado());
			
			int filasUsuarioAfectadas = psUsuario.executeUpdate();

			if (filasUsuarioAfectadas > 0) {
				rs = psUsuario.getGeneratedKeys();
				if (rs.next()) {
					int idUsuarioGenerado = rs.getInt(1); 
					c.getUsuario().setIdUsuario(idUsuarioGenerado); 
				}
				
				psCliente = conn.prepareStatement(queryInsertCliente);
				psCliente.setString(1, c.getDni());
				psCliente.setString(2, c.getCuil());
				psCliente.setString(3, c.getSexo());
				psCliente.setString(4, c.getNombre());
				psCliente.setString(5, c.getApellido());
				psCliente.setString(6, c.getNacionalidad());
				psCliente.setDate(7, c.getFechaNacimiento());
				psCliente.setString(8, c.getDireccion());
				psCliente.setInt(9, c.getLocalidad().getIdLocalidad()); 
				psCliente.setString(10, c.getTelefono());
				psCliente.setString(11, c.getCorreo());
				psCliente.setInt(12, c.getUsuario().getIdUsuario()); 
				psCliente.setBoolean(13, c.isEstado());
				
				int filasClienteAfectadas = psCliente.executeUpdate();
				
				if (filasClienteAfectadas > 0) {
					conn.commit(); 
					exito = true;
				} else {
					conn.rollback(); 
				}
			} else {
				conn.rollback(); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try {
				if (rs != null) rs.close();
				if (psUsuario != null) psUsuario.close();
				if (psCliente != null) psCliente.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exito;
	}

	@Override
	public boolean modificar(Cliente c) {
		String queryUpdateUsuario = "UPDATE Usuario SET nombreUsuario = ?, clave = ?, tipoUsuario = ?, estado = ? WHERE idUsuario = ?";
		String queryUpdateCliente = "UPDATE Cliente SET cuil = ?, sexo = ?, nombre = ?, apellido = ?, nacionalidad = ?, fechaNacimiento = ?, direccion = ?, idLocalidad = ?, telefono = ?, correo = ?, estado = ? WHERE dni = ?";
		
		Connection conn = null;
		PreparedStatement psUsuario = null;
		PreparedStatement psCliente = null;
		boolean exito = false;

		try {
			conn = getConnection();
			conn.setAutoCommit(false); 

			// 1. Modificar el Usuario
			psUsuario = conn.prepareStatement(queryUpdateUsuario);
			psUsuario.setString(1, c.getUsuario().getNombreUsuario());
			psUsuario.setString(2, c.getUsuario().getClave());
			psUsuario.setString(3, c.getUsuario().getTipoUsuario());
			psUsuario.setBoolean(4, c.getUsuario().isEstado());
			psUsuario.setInt(5, c.getUsuario().getIdUsuario()); // Usar el ID de usuario para la actualización
			int filasUsuarioAfectadas = psUsuario.executeUpdate();

			// 2. Modificar el Cliente
			psCliente = conn.prepareStatement(queryUpdateCliente);
			psCliente.setString(1, c.getCuil());
			psCliente.setString(2, c.getSexo());
			psCliente.setString(3, c.getNombre());
			psCliente.setString(4, c.getApellido());
			psCliente.setString(5, c.getNacionalidad());
			psCliente.setDate(6, c.getFechaNacimiento());
			psCliente.setString(7, c.getDireccion());
			psCliente.setInt(8, c.getLocalidad().getIdLocalidad());
			psCliente.setString(9, c.getTelefono());
			psCliente.setString(10, c.getCorreo());
			psCliente.setBoolean(11, c.isEstado());
			psCliente.setString(12, c.getDni()); // Usar el DNI para identificar al cliente
			
			int filasClienteAfectadas = psCliente.executeUpdate();
			
			// Confirmar si ambas operaciones fueron exitosas.
			// Ajusta esta condición si el usuario puede no ser actualizado o no es mandatorio.
			if (filasClienteAfectadas > 0 && filasUsuarioAfectadas > 0) {
				conn.commit();
				exito = true;
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try {
				if (psUsuario != null) psUsuario.close();
				if (psCliente != null) psCliente.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exito;
	}

	@Override
	public boolean eliminar(String dniAEliminar) {
		String queryCliente = "UPDATE Cliente SET estado = 0 WHERE DNI = ?";
		String queryUsuario = "UPDATE Usuario SET estado = 0 WHERE idUsuario = (SELECT idUsuario FROM Cliente WHERE DNI = ?)"; 

		Connection conn = null;
		PreparedStatement psCliente = null;
		PreparedStatement psUsuario = null;
		boolean exito = false;

		try {
			conn = getConnection();
			conn.setAutoCommit(false); 

			// Intentar obtener el idUsuario asociado para deshabilitarlo
			String selectUserIdQuery = "SELECT idUsuario FROM Cliente WHERE DNI = ?";
			int idUsuarioAsociado = -1;
			try (PreparedStatement psSelect = conn.prepareStatement(selectUserIdQuery)) {
				psSelect.setString(1, dniAEliminar);
				try (ResultSet rs = psSelect.executeQuery()) {
					if (rs.next()) {
						idUsuarioAsociado = rs.getInt("idUsuario");
					}
				}
			}

			// Deshabilitar el Cliente (baja lógica)
			psCliente = conn.prepareStatement(queryCliente);
			psCliente.setString(1, dniAEliminar);
			int filasClienteAfectadas = psCliente.executeUpdate();

			// Deshabilitar el Usuario asociado si se encontró uno
			int filasUsuarioAfectadas = 0;
			if (idUsuarioAsociado != -1) {
				psUsuario = conn.prepareStatement(queryUsuario);
				psUsuario.setString(1, dniAEliminar); 
				filasUsuarioAfectadas = psUsuario.executeUpdate();
			}

			// Confirmar si el cliente fue afectado y (si había usuario) el usuario también
			if (filasClienteAfectadas > 0 && (idUsuarioAsociado == -1 || filasUsuarioAfectadas > 0)) {
				conn.commit(); 
				exito = true;
			} else {
				conn.rollback(); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try {
				if (psCliente != null) psCliente.close();
				if (psUsuario != null) psUsuario.close();
				if (conn != null) conn.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exito;
	}
	
}