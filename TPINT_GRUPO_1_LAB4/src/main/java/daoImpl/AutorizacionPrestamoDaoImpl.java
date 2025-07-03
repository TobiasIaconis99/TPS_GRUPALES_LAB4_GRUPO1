package daoImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import dao.GestorConexionBD;
import dao.AutorizacionPrestamoDao;
import entidad.Prestamo;
import entidad.Cliente;
import entidad.Cuenta;
import entidad.Localidad;
import entidad.Provincia;
import entidad.TipoCuenta;

public class AutorizacionPrestamoDaoImpl implements AutorizacionPrestamoDao {

    private Connection getConnection() throws SQLException {
        return GestorConexionBD.getConnection();
    }

    private static final String BASE_SELECT_SQL =
        "SELECT p.idPrestamo, p.idCliente, p.idCuenta, p.fechaAlta, p.importePedido, " +
        "p.plazoMeses, p.cantidadCuotas, p.montoCuota, p.estado, " + // La columna 'estado' se leerá como String
        "c.id AS idCliente, c.DNI, c.nombre, c.apellido, c.sexo, c.nacionalidad, c.fechaNacimiento, c.direccion, " +
        "l.nombreLocalidad AS nombreLocalidadAlias, " +
        "pr.nombreProvincia AS nombreProvinciaAlias, " +
        "c.email, c.telefono, c.estado AS estadoCliente, " +
        "cu.idCuenta, cu.numeroCuenta, cu.fechaCreacion, cu.tipoCuenta, cu.saldo, cu.CBU, cu.estado AS estadoCuenta " +
        "FROM Prestamo p " +
        "INNER JOIN Cliente c ON p.idCliente = c.id " +
        "INNER JOIN Cuenta cu ON p.idCuenta = cu.idCuenta " +
        "INNER JOIN Localidad l ON c.idLocalidad = l.idLocalidad " +
        "INNER JOIN Provincia pr ON l.idProvincia = pr.idProvincia ";

    private static final String INSERT_CUOTA =
        "INSERT INTO Cuota (idPrestamo, numeroCuota, monto, fechaVencimiento, pagada) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_PRESTAMO_ESTADO = 
        "UPDATE Prestamo SET estado = ? WHERE idPrestamo = ?"; // Ahora el estado será un String (ej. "Aprobado")
    
    private static final String SELECT_PRESTAMO_PARA_CUOTAS = 
        "SELECT cantidadCuotas, montoCuota, fechaAlta FROM Prestamo WHERE idPrestamo = ?";

    @Override
    public boolean modificarEstado(int idPrestamo, int idEstado) {
        Connection conn = null;
        boolean modificado = false;
        String nuevoEstadoTexto = ""; // Variable para almacenar el estado como String

        // Convertir el idEstado numérico a su representación de texto para la DB
        switch(idEstado) {
            case 0: nuevoEstadoTexto = "Rechazado"; break;
            case 1: nuevoEstadoTexto = "Pendiente"; break;
            case 2: nuevoEstadoTexto = "Aprobado"; break;
            default: return false; // Estado no válido
        }

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psUpdate = conn.prepareStatement(UPDATE_PRESTAMO_ESTADO)) {
                psUpdate.setString(1, nuevoEstadoTexto); // <--- CAMBIO CLAVE AQUÍ: setString
                psUpdate.setInt(2, idPrestamo);

                int rowsAffected = psUpdate.executeUpdate();

                if (rowsAffected > 0) {
                    modificado = true;
                    if (idEstado == 2) { // Si se aprueba, generar cuotas
                        try (PreparedStatement psSelect = conn.prepareStatement(SELECT_PRESTAMO_PARA_CUOTAS)) {
                            psSelect.setInt(1, idPrestamo);
                            try (ResultSet rs = psSelect.executeQuery()) {
                                if (rs.next()) {
                                    int cantidadCuotas = rs.getInt("cantidadCuotas");
                                    BigDecimal montoCuota = rs.getBigDecimal("montoCuota");
                                    java.sql.Date fechaAltaPrestamo = rs.getDate("fechaAlta");

                                    try (PreparedStatement psInsertCuota = conn.prepareStatement(INSERT_CUOTA)) {
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(fechaAltaPrestamo);

                                        for (int i = 1; i <= cantidadCuotas; i++) {
                                            cal.add(Calendar.MONTH, 1);
                                            java.sql.Date fechaVencimiento = new java.sql.Date(cal.getTimeInMillis());

                                            psInsertCuota.setInt(1, idPrestamo);
                                            psInsertCuota.setInt(2, i);
                                            psInsertCuota.setBigDecimal(3, montoCuota);
                                            psInsertCuota.setDate(4, fechaVencimiento);
                                            psInsertCuota.setBoolean(5, false);
                                            psInsertCuota.addBatch();
                                        }
                                        psInsertCuota.executeBatch();
                                    }
                                } else {
                                    modificado = false; 
                                }
                            }
                        }
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Error SQL al modificar el estado del préstamo o generar cuotas: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transacción revertida para idPrestamo: " + idPrestamo);
                } catch (SQLException ex) {
                    System.err.println("Error al intentar revertir la transacción: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            modificado = false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión en finally de modificarEstado: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return modificado;
    }

    @Override
    public List<Prestamo> obtenerPrestamosFiltradosPaginados(String busquedaDNI, String filtroEstado, int offset, int limit) {
        List<Prestamo> prestamos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder(BASE_SELECT_SQL);
            List<String> conditions = new ArrayList<>();
            List<Object> params = new ArrayList<>();

            if (busquedaDNI != null && !busquedaDNI.trim().isEmpty()) {
                conditions.add("(c.DNI LIKE ? OR c.nombre LIKE ? OR c.apellido LIKE ?)");
                params.add("%" + busquedaDNI.trim() + "%");
                params.add("%" + busquedaDNI.trim() + "%");
                params.add("%" + busquedaDNI.trim() + "%");
            }

            if (filtroEstado != null && !filtroEstado.isEmpty()) {
                // Aquí convertimos el filtro numérico del JSP a la cadena de texto de la DB
                String estadoDB = "";
                switch(filtroEstado) {
                    case "0": estadoDB = "Rechazado"; break;
                    case "1": estadoDB = "Pendiente"; break;
                    case "2": estadoDB = "Aprobado"; break;
                    default: // Si es un valor inesperado, no agregamos la condición de estado
                        estadoDB = null;
                        break;
                }
                if (estadoDB != null) {
                    conditions.add("p.estado = ?");
                    params.add(estadoDB); // <--- CAMBIO CLAVE AQUÍ: Agregar el String del estado
                }
            }

            if (!conditions.isEmpty()) {
                sql.append(" WHERE ").append(String.join(" AND ", conditions));
            }

            sql.append(" ORDER BY p.fechaAlta DESC, p.idPrestamo DESC "); 
            sql.append(" LIMIT ? OFFSET ?");

            ps = conn.prepareStatement(sql.toString());
            int paramIndex = 1;
            for (Object param : params) {
                ps.setObject(paramIndex++, param);
            }
            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex++, offset);

            rs = ps.executeQuery();
            while (rs.next()) {
                prestamos.add(mapResultSetToPrestamo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al obtener préstamos filtrados y paginados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conn, ps, rs);
        }
        return prestamos;
    }

    @Override
    public int contarTotalPrestamos(String busquedaDNI, String filtroEstado) {
        int total = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder("SELECT COUNT(p.idPrestamo) FROM Prestamo p ");
            sql.append("INNER JOIN Cliente c ON p.idCliente = c.id ");
            sql.append("INNER JOIN Cuenta cu ON p.idCuenta = cu.idCuenta ");
            sql.append("INNER JOIN Localidad l ON c.idLocalidad = l.idLocalidad ");
            sql.append("INNER JOIN Provincia pr ON l.idProvincia = pr.idProvincia ");

            List<String> conditions = new ArrayList<>();
            List<Object> params = new ArrayList<>();

            if (busquedaDNI != null && !busquedaDNI.trim().isEmpty()) {
                conditions.add("(c.DNI LIKE ? OR c.nombre LIKE ? OR c.apellido LIKE ?)");
                params.add("%" + busquedaDNI.trim() + "%");
                params.add("%" + busquedaDNI.trim() + "%");
                params.add("%" + busquedaDNI.trim() + "%");
            }

            if (filtroEstado != null && !filtroEstado.isEmpty()) {
                // Aquí convertimos el filtro numérico del JSP a la cadena de texto de la DB
                String estadoDB = "";
                switch(filtroEstado) {
                    case "0": estadoDB = "Rechazado"; break;
                    case "1": estadoDB = "Pendiente"; break;
                    case "2": estadoDB = "Aprobado"; break;
                    default: 
                        estadoDB = null;
                        break;
                }
                if (estadoDB != null) {
                    conditions.add("p.estado = ?");
                    params.add(estadoDB); // <--- CAMBIO CLAVE AQUÍ
                }
            }

            if (!conditions.isEmpty()) {
                sql.append(" WHERE ").append(String.join(" AND ", conditions));
            }

            ps = conn.prepareStatement(sql.toString());
            int paramIndex = 1;
            for (Object param : params) {
                ps.setObject(paramIndex++, param);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al contar el total de préstamos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            GestorConexionBD.closeResources(conn, ps, rs);
        }
        return total;
    }

    private Prestamo mapResultSetToPrestamo(ResultSet rs) throws SQLException {
        Prestamo p = new Prestamo();
        p.setIdPrestamo(rs.getInt("idPrestamo"));
        p.setFechaAlta(rs.getDate("fechaAlta"));
        p.setImportePedido(rs.getBigDecimal("importePedido"));
        p.setPlazoMeses(rs.getInt("plazoMeses"));
        p.setCantidadCuotas(rs.getInt("cantidadCuotas"));
        p.setMontoCuota(rs.getBigDecimal("montoCuota"));
        p.setEstado(rs.getString("estado")); // <--- CAMBIO CLAVE AQUÍ: Obtener el estado como String

        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("idCliente"));
        cliente.setDni(rs.getString("DNI"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setSexo(rs.getString("sexo"));
        cliente.setNacionalidad(rs.getString("nacionalidad"));
        cliente.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        cliente.setDireccion(rs.getString("direccion"));
        
        Localidad localidad = new Localidad();
        localidad.setNombreLocalidad(rs.getString("nombreLocalidadAlias"));
        
        Provincia provincia = new Provincia();
        provincia.setNombreProvincia(rs.getString("nombreProvinciaAlias"));
        
        localidad.setProvincia(provincia);
        cliente.setLocalidad(localidad);
        
        cliente.setCorreo(rs.getString("email"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setEstado(rs.getBoolean("estadoCliente")); 
        p.setCliente(cliente);

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(rs.getInt("idCuenta"));
        
        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setNombreTipoCuenta(rs.getString("tipoCuenta"));
        cuenta.setTipoCuenta(tipoCuenta); 

        cuenta.setNumeroCuenta(rs.getString("numeroCuenta"));
        cuenta.setCbu(rs.getString("CBU"));
        cuenta.setSaldo(rs.getBigDecimal("saldo"));
        cuenta.setFechaCreacion(rs.getDate("fechaCreacion"));
        cuenta.setEstado(rs.getBoolean("estadoCuenta")); 
        p.setCuenta(cuenta);

        return p;
    }
}