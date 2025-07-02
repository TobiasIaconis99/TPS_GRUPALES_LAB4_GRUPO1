package negocioImpl;

import java.util.List;

import dao.ClienteDao;
import daoImpl.ClienteDaoImpl;
import entidad.Cliente;
import negocio.ClienteNegocio;

public class ClienteNegocioImpl implements ClienteNegocio{

	private ClienteDao clienteDao = new ClienteDaoImpl();
	
	@Override
	public List<Cliente> listar() {
		return clienteDao.listar();
	}

	@Override
    public boolean agregar(Cliente c) {
		
        if (c == null) {
            System.err.println("ERROR NEGOCIO: El objeto Cliente es nulo.");
            return false;
        }

        // DNI
        if (c.getDni() == null || c.getDni().trim().isEmpty()) {
            System.err.println("ERROR NEGOCIO: DNI obligatorio.");
            return false;
        }
        // Validar formato del DNI (7 u 8 dígitos)
        String dni = c.getDni().trim();
        if (!dni.matches("^\\d{7,8}$")) {
            System.err.println("ERROR NEGOCIO: DNI debe contener 7 u 8 dígitos numéricos.");
            return false;
        }
        c.setDni(dni); // Asignar el DNI limpio

        // CUIL
        String cuil = c.getCuil() != null ? c.getCuil().trim() : "";
        if (cuil.isEmpty() || !cuil.matches("^\\d{2}-\\d{8}-\\d{1}$")) {
            System.err.println("ERROR NEGOCIO: CUIL inválido o vacío. Formato esperado XX-XXXXXXXX-X.");
            return false;
        }
        // Lógica para comparar DNI con la parte central del CUIL
        String cuilDniParte = cuil.substring(3, 11);
        if (!cuilDniParte.equals(c.getDni())) { // Ya tenemos el DNI limpio en c.getDni()
            System.err.println("ERROR NEGOCIO: Los dígitos centrales del CUIL no coinciden con el DNI.");
            return false;
        }
        c.setCuil(cuil); // Asignar el CUIL limpio

        // Sexo
        String sexo = c.getSexo() != null ? c.getSexo().trim() : "";
        if (sexo.isEmpty() || (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F") && !sexo.equalsIgnoreCase("O"))) {
            System.err.println("ERROR NEGOCIO: Sexo inválido o vacío. Debe ser 'M', 'F' u 'O'.");
            return false;
        }
        c.setSexo(sexo.toUpperCase()); // Asegurar que se guarda en mayúsculas

        // Nombre
        String nombre = c.getNombre() != null ? c.getNombre().trim() : "";
        if (nombre.isEmpty() || !nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) {
            System.err.println("ERROR NEGOCIO: Nombre inválido o vacío. Debe tener entre 2 y 40 letras y espacios.");
            return false;
        }
        c.setNombre(nombre); // Asignar el nombre limpio

        // Apellido
        String apellido = c.getApellido() != null ? c.getApellido().trim() : "";
        if (apellido.isEmpty() || !apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) {
            System.err.println("ERROR NEGOCIO: Apellido inválido o vacío. Debe tener entre 2 y 40 letras y espacios.");
            return false;
        }
        c.setApellido(apellido); // Asignar el apellido limpio

        // Nacionalidad
        String nacionalidad = c.getNacionalidad() != null ? c.getNacionalidad().trim() : "";
        if (nacionalidad.isEmpty() || nacionalidad.length() < 2 || nacionalidad.length() > 50 || !nacionalidad.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
            System.err.println("ERROR NEGOCIO: Nacionalidad inválida o vacía. Debe tener entre 2 y 50 letras y espacios.");
            return false;
        }
        c.setNacionalidad(nacionalidad); // Asignar la nacionalidad limpia
        
	    // Fecha de Nacimiento
	    if (c.getFechaNacimiento() == null) {
	        System.err.println("ERROR NEGOCIO: Fecha de nacimiento es obligatoria.");
	        return false;
	    }
	    // Validación de edad mínima (18 años)
	    java.time.LocalDate fechaNacimiento = new java.sql.Date(c.getFechaNacimiento().getTime()).toLocalDate();
	    java.time.LocalDate hoy = java.time.LocalDate.now();
	    if (fechaNacimiento.isAfter(hoy)) {
	        System.err.println("ERROR NEGOCIO: La fecha de nacimiento no puede ser futura.");
	        return false;
	    }
	    if (java.time.Period.between(fechaNacimiento, hoy).getYears() < 18) {
	        System.err.println("ERROR NEGOCIO: El cliente debe ser mayor de 18 años.");
	        return false;
	    }
        

        // Dirección
        String direccion = c.getDireccion() != null ? c.getDireccion().trim() : "";
        if (direccion.isEmpty() || direccion.length() < 5 || direccion.length() > 100 || !direccion.matches("^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ#\\-\\s,.]+$")) {
            System.err.println("ERROR NEGOCIO: Dirección inválida o vacía. Debe tener entre 5 y 100 caracteres alfanuméricos y especiales básicos.");
            return false;
        }
        c.setDireccion(direccion); // Asignar la dirección limpia

        // Localidad
        if (c.getLocalidad() == null || c.getLocalidad().getIdLocalidad() <= 0) {
            System.err.println("ERROR NEGOCIO: Localidad inválida o no seleccionada.");
            return false;
        }

        // Teléfono
        String telefono = c.getTelefono() != null ? c.getTelefono().trim() : "";
        // Permite dígitos, +, espacios, guiones, paréntesis. Ajusta la regex si tu formato es más estricto.
        if (telefono.isEmpty() || telefono.length() < 7 || telefono.length() > 20 || !telefono.matches("^[0-9+\\s\\-()]{7,20}$")) {
            System.err.println("ERROR NEGOCIO: Teléfono inválido o vacío. Debe tener entre 7 y 20 caracteres (números, +, -, (), espacios).");
            return false;
        }
        c.setTelefono(telefono); // Asignar el teléfono limpio

        // Correo Electrónico
        String correo = c.getCorreo() != null ? c.getCorreo().trim() : "";
        if (correo.isEmpty() || !correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            System.err.println("ERROR NEGOCIO: Correo electrónico inválido o vacío.");
            return false;
        }
        c.setCorreo(correo); // Asignar el correo limpio

        // Nombre de Usuario (asumiendo que Cliente tiene una relación con Usuario)
        if (c.getUsuario() == null) {
            System.err.println("ERROR NEGOCIO: El objeto Usuario es nulo.");
            return false;
        }
        String nombreUsuario = c.getUsuario().getNombreUsuario() != null ? c.getUsuario().getNombreUsuario().trim() : "";
        if (nombreUsuario.isEmpty() || nombreUsuario.length() < 3 || nombreUsuario.length() > 20 || !nombreUsuario.matches("^[a-zA-Z0-9._]{3,20}$")) {
            System.err.println("ERROR NEGOCIO: Nombre de usuario inválido o vacío. Debe tener entre 3 y 20 caracteres (letras, números, puntos, guiones bajos).");
            return false;
        }
        c.getUsuario().setNombreUsuario(nombreUsuario); // Asignar el nombre de usuario limpio

        // Clave
        String clave = c.getUsuario().getClave() != null ? c.getUsuario().getClave() : "";
        if (clave.isEmpty() || !clave.matches("^(?=.*\\d).{4,}$")) { // La clave no puede estar vacía al agregar un nuevo usuario
            System.err.println("ERROR NEGOCIO: Clave de usuario inválida. Debe tener al menos 4 caracteres y contener al menos un número.");
            return false;
        }
        // No es necesario setear la clave de vuelta, ya está en el objeto.

        // Evitar duplicados por DNI (esto ya estaba y es fundamental)
        if (clienteDao.obtenerPorDni(c.getDni()) != null) {
            System.err.println("ERROR NEGOCIO: Ya existe un cliente con el DNI: " + c.getDni());
            return false;
        }
        
        // Evitar duplicados por Nombre de Usuario (si es un campo único en la BD)
        // Esto requeriría un método en ClienteDao para verificar si un nombre de usuario ya existe.
        // Ejemplo:
        // if (clienteDao.existeNombreUsuario(c.getUsuario().getNombreUsuario())) {
        //     System.err.println("ERROR NEGOCIO: El nombre de usuario ya está en uso.");
        //     return false;
        // }

        // Si todas las validaciones pasan, procede con la adición
        System.out.println("DEBUG NEGOCIO: Todas las validaciones de agregar pasaron.");
        return clienteDao.agregar(c);
    }

	@Override
	public boolean modificar(Cliente c) {

	    // Validaciones de Campos Obligatorios y Formato
	    // DNI
	    if (c.getDni() == null || c.getDni().trim().isEmpty()) {
	        System.err.println("ERROR NEGOCIO: DNI obligatorio.");
	        return false;
	    }

	    // Nombre
	    String nombre = c.getNombre() != null ? c.getNombre().trim() : "";
	    if (nombre.isEmpty() || !nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) {
	        System.err.println("ERROR NEGOCIO: Nombre inválido o vacío.");
	        return false;
	    }
	    c.setNombre(nombre);

	    // Apellido
	    String apellido = c.getApellido() != null ? c.getApellido().trim() : "";
	    if (apellido.isEmpty() || !apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,40}$")) {
	        System.err.println("ERROR NEGOCIO: Apellido inválido o vacío.");
	        return false;
	    }
	    c.setApellido(apellido);

	    // Sexo
	    String sexo = c.getSexo() != null ? c.getSexo().trim() : "";
	    if (sexo.isEmpty() || (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F") && !sexo.equalsIgnoreCase("O"))) {
	        System.err.println("ERROR NEGOCIO: Sexo inválido o vacío.");
	        return false;
	    }
	    c.setSexo(sexo.toUpperCase());

	    // CUIL
	    String cuil = c.getCuil() != null ? c.getCuil().trim() : "";
	    if (cuil.isEmpty() || !cuil.matches("^\\d{2}-\\d{8}-\\d{1}$")) {
	        System.err.println("ERROR NEGOCIO: CUIL inválido o vacío. Formato esperado XX-XXXXXXXX-X.");
	        return false;
	    }
	    // Lógica para comparar DNI con la parte central del CUIL
	    String cuilDniParte = cuil.substring(3, 11);
	    if (!cuilDniParte.equals(c.getDni())) {
	        System.err.println("ERROR NEGOCIO: Los dígitos centrales del CUIL no coinciden con el DNI.");
	        return false;
	    }
	    c.setCuil(cuil);

	    // Fecha de Nacimiento
	    if (c.getFechaNacimiento() == null) {
	        System.err.println("ERROR NEGOCIO: Fecha de nacimiento es obligatoria.");
	        return false;
	    }
	    // Validación de edad mínima (18 años)
	    java.time.LocalDate fechaNacimiento = new java.sql.Date(c.getFechaNacimiento().getTime()).toLocalDate();
	    java.time.LocalDate hoy = java.time.LocalDate.now();
	    if (fechaNacimiento.isAfter(hoy)) {
	        System.err.println("ERROR NEGOCIO: La fecha de nacimiento no puede ser futura.");
	        return false;
	    }
	    if (java.time.Period.between(fechaNacimiento, hoy).getYears() < 18) {
	        System.err.println("ERROR NEGOCIO: El cliente debe ser mayor de 18 años.");
	        return false;
	    }

	    // Dirección
	    String direccion = c.getDireccion() != null ? c.getDireccion().trim() : "";
	    if (direccion.isEmpty() || direccion.length() < 5 || direccion.length() > 100 || !direccion.matches("^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñ#\\-\\s,.]+$")) {
	        System.err.println("ERROR NEGOCIO: Dirección inválida o vacía.");
	        return false;
	    }
	    c.setDireccion(direccion);

	    // Localidad
	    if (c.getLocalidad() == null || c.getLocalidad().getIdLocalidad() <= 0) {
	        System.err.println("ERROR NEGOCIO: Localidad inválida o no seleccionada.");
	        return false;
	    }

	    // Teléfono
	    String telefono = c.getTelefono() != null ? c.getTelefono().trim() : "";
	    // Permite dígitos, +, espacios, guiones, paréntesis. Ajusta la regex si tu formato es más estricto.
	    if (telefono.isEmpty() || telefono.length() < 7 || telefono.length() > 20 || !telefono.matches("^[0-9+\\s\\-()]{7,20}$")) {
	        System.err.println("ERROR NEGOCIO: Teléfono inválido o vacío.");
	        return false;
	    }
	    c.setTelefono(telefono);

	    // Correo Electrónico
	    String correo = c.getCorreo() != null ? c.getCorreo().trim() : "";
	    if (correo.isEmpty() || !correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
	        System.err.println("ERROR NEGOCIO: Correo electrónico inválido o vacío.");
	        return false;
	    }
	    c.setCorreo(correo);

	    // Nombre de Usuario (si es parte del Cliente, o si Cliente tiene una relación con Usuario)
	    if (c.getUsuario() == null) {
	        System.err.println("ERROR NEGOCIO: El objeto Usuario es nulo.");
	        return false;
	    }
	    String nombreUsuario = c.getUsuario().getNombreUsuario() != null ? c.getUsuario().getNombreUsuario().trim() : "";
	    if (nombreUsuario.isEmpty() || nombreUsuario.length() < 3 || nombreUsuario.length() > 20 || !nombreUsuario.matches("^[a-zA-Z0-9._]{3,20}$")) {
	        System.err.println("ERROR NEGOCIO: Nombre de usuario inválido o vacío.");
	        return false;
	    }
	    c.getUsuario().setNombreUsuario(nombreUsuario);

	    // Clave
	    String clave = c.getUsuario().getClave() != null ? c.getUsuario().getClave() : "";
	    if (!clave.isEmpty() && !clave.matches("^(?=.*\\d).{4,}$")) { // Solo valida si no está vacía
	        System.err.println("ERROR NEGOCIO: Clave de usuario inválida. Debe tener al menos 4 caracteres y un número.");
	        return false;
	    }

	    // Si todas las validaciones pasan, procede con la modificación
	    return clienteDao.modificar(c);
	}

	@Override
	public boolean eliminar(String dniAEliminar) {
		return clienteDao.eliminar(dniAEliminar); 
	}
	
	@Override
	public Cliente obtenerPorDni(String dni) {
		return clienteDao.obtenerPorDni(dni);
	}
	
	@Override
    public Cliente obtenerPorIdUsuario(int idUsuario) {
        return clienteDao.obtenerPorIdUsuario(idUsuario);
    }
	
	@Override
	public List<Cliente> listarFiltrado(String busqueda, String sexo, int pagina, int limite) {
		 
	        // Validaciones
	        // 1. Validar la paginacion
	        if (pagina < 1) {
	            pagina = 1;
	        }
	        if (limite <= 0) {
	            limite = 10;
	        }
	        
	        final int MAX_LIMITE_PERMITIDO = 100;
	        if (limite > MAX_LIMITE_PERMITIDO) {
	            limite = MAX_LIMITE_PERMITIDO;
	        }

	        // 2. Validar los parametros del filtro
	        if (busqueda != null && busqueda.trim().isEmpty()) {
	            busqueda = null;
	        }

	        // 3. Validar el parametro de sexo
	        if (sexo != null && !sexo.trim().isEmpty()) {
	            String sexoUpperCase = sexo.trim().toUpperCase();
	            if (!sexoUpperCase.equals("M") && !sexoUpperCase.equals("F") && !sexoUpperCase.equals("O")) {
	                sexo = null;
	            } else {
	                sexo = sexoUpperCase;
	            }
	        } else {
	            sexo = null;
	        }
	        return clienteDao.listarFiltrado(busqueda, sexo, pagina, limite);
	    }

	@Override
    public int contarFiltrado(String busqueda, String sexo) {
    	
    	// Validaciones
        if (busqueda != null && busqueda.trim().isEmpty()) {
            busqueda = null;
        }

        if (sexo != null && !sexo.trim().isEmpty()) {
            String sexoUpperCase = sexo.trim().toUpperCase();
            if (!sexoUpperCase.equals("M") && !sexoUpperCase.equals("F") && !sexoUpperCase.equals("O")) {
                sexo = null;
            } else {
                sexo = sexoUpperCase;
            }
        } else {
            sexo = null;
        }
        return clienteDao.contarFiltrado(busqueda, sexo);
    }
}