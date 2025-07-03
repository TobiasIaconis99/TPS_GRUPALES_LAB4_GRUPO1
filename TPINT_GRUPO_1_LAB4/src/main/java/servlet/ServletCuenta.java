package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors; // Importar Collectors
import java.util.Arrays;
import java.text.Normalizer;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidad.Cliente;
import entidad.Cuenta;
import entidad.TipoCuenta;
import negocio.ClienteNegocio;
import negocio.CuentaNegocio;
import negocio.TipoCuentaNegocio;
import negocioImpl.ClienteNegocioImpl;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TipoCuentaNegocioImpl;

@WebServlet("/ServletCuenta")
public class ServletCuenta extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();
    private ClienteNegocio clienteNegocio = new ClienteNegocioImpl();
    private TipoCuentaNegocio tipoCuentaNegocio = new TipoCuentaNegocioImpl();

    public ServletCuenta() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null || accion.equals("listar")) {
            // Parámetros de filtro y búsqueda
            String filtroTipoCuenta = request.getParameter("filtroTipoCuenta");
            String busquedaCliente = request.getParameter("busquedaCliente");

            // Parámetros de paginación
            int paginaActual = 1;
            int registrosPorPagina = 10;
            try {
                String paginaParam = request.getParameter("pagina");
                if (paginaParam != null && !paginaParam.isEmpty()) {
                    paginaActual = Integer.parseInt(paginaParam);
                }
            } catch (NumberFormatException e) {
                // Se mantiene página 1
            }

            // 1. Obtener todas las cuentas activas
            List<Cuenta> todasLasCuentas = cuentaNegocio.listarCuentasActivas();
            List<Cuenta> cuentasFiltradas = todasLasCuentas;

            // 2. Filtrar por tipo de cuenta
            if (filtroTipoCuenta != null && !filtroTipoCuenta.isEmpty()) {
                try {
                    int idTipo = Integer.parseInt(filtroTipoCuenta);
                    cuentasFiltradas = cuentasFiltradas.stream()
                            .filter(c -> c.getTipoCuenta().getIdTipoCuenta() == idTipo)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    System.err.println("Error al parsear filtroTipoCuenta: " + e.getMessage());
                }
            }

            // 3. Filtrar por búsqueda de cliente
            if (busquedaCliente != null && !busquedaCliente.isEmpty()) {
                String busquedaNormalizada = normalizar(busquedaCliente.trim());
                String[] palabrasClave = busquedaNormalizada.split("\\s+");

                cuentasFiltradas = cuentasFiltradas.stream()
                    .filter(c -> {
                        String combinado = normalizar(
                            c.getCliente().getDni() + " " +
                            c.getCliente().getNombre() + " " +
                            c.getCliente().getApellido()
                        );
                        return Arrays.stream(palabrasClave).allMatch(combinado::contains);
                    })
                    .collect(Collectors.toList());
            }

            // 4. Paginación
            int totalCuentas = cuentasFiltradas.size();
            int totalPaginas = (int) Math.ceil((double) totalCuentas / registrosPorPagina);
            int startIndex = (paginaActual - 1) * registrosPorPagina;
            int endIndex = Math.min(startIndex + registrosPorPagina, totalCuentas);

            List<Cuenta> cuentasPaginadas = cuentasFiltradas.subList(startIndex, endIndex);

            request.setAttribute("listaCuentas", cuentasPaginadas);
            request.setAttribute("paginaActual", paginaActual);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("listaClientes", clienteNegocio.listar());

            RequestDispatcher rd = request.getRequestDispatcher("ABMLCuentas.jsp");
            rd.forward(request, response);
        }
        

    }
    
    private String normalizar(String texto) {
        return Normalizer
                .normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();

        if (accion != null) {
            switch (accion) {
            case "eliminar":
                String cuentaAEliminar = request.getParameter("idCuenta");
                if (cuentaAEliminar != null && !cuentaAEliminar.isEmpty()) {
                    try {
                        if (cuentaNegocio.eliminar(Integer.parseInt(cuentaAEliminar))) {
                            session.setAttribute("mensajeExitoCuenta", "Se eliminó la cuenta exitosamente.");
                        } else {
                            session.setAttribute("mensajeErrorCuenta", "No se pudo eliminar la cuenta. Asegúrese de que no tenga movimientos."); // Ajusta el mensaje si es necesario
                        }
                    } catch (NumberFormatException e) {
                        session.setAttribute("mensajeErrorCuenta", "ID de cuenta no válido.");
                    }
                } else {
                    session.setAttribute("mensajeErrorCuenta", "ID de cuenta no especificado para eliminar.");
                }
                break;

            case "agregar":
                try {
                    String idTipoCuentaStr = request.getParameter("tipoCuenta");
                    if (idTipoCuentaStr == null || idTipoCuentaStr.isEmpty()) {
                        session.setAttribute("mensajeErrorCuenta", "El tipo de cuenta es requerido.");
                        response.sendRedirect(request.getContextPath() + "/ServletCuenta?accion=listar"); // Usar context path
                        return;
                    }

                    int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);
                    TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoCuentaPorId(idTipoCuenta);

                    String dniClienteStr = request.getParameter("dniCliente");
                    if (dniClienteStr == null || dniClienteStr.isEmpty()) {
                        session.setAttribute("mensajeErrorCuenta", "El DNI del cliente es requerido.");
                        response.sendRedirect(request.getContextPath() + "/ServletCuenta?accion=listar"); // Usar context path
                        return;
                    }

                    Cliente cliente = clienteNegocio.obtenerPorDni(dniClienteStr);
                    if (cliente == null) {
                        session.setAttribute("mensajeErrorCuenta", "Cliente no encontrado.");
                        response.sendRedirect(request.getContextPath() + "/ServletCuenta?accion=listar"); // Usar context path
                        return;
                    }

                    // Validar si el cliente ya tiene 3 cuentas activas antes de crear la cuenta
                    if (!cuentaNegocio.tieneMenos3CuentasAct(cliente.getId())) {
                        session.setAttribute("mensajeErrorCuenta", "El cliente ya tiene el máximo de 3 cuentas activas.");
                        response.sendRedirect(request.getContextPath() + "/ServletCuenta?accion=listar"); // Usar context path
                        return;
                    }

                    Cuenta cuenta = new Cuenta();
                    cuenta.setCliente(cliente);
                    cuenta.setTipoCuenta(tipoCuenta);
                    cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
                    cuenta.setSaldo(new BigDecimal("10000.00")); // Asignar el saldo inicial aquí, no en el DAO
                    cuenta.setEstado(true);

                    boolean fueAgregado = cuentaNegocio.agregar(cuenta); // El DAO solo debe guardar, no generar números

                    if (fueAgregado) {
                        session.setAttribute("mensajeExitoCuenta", "Cuenta agregada correctamente.");
                    } else {
                        session.setAttribute("mensajeErrorCuenta", "No se pudo agregar la cuenta. Contacte a soporte."); // Mensaje más genérico si no se sabe la causa exacta
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("mensajeErrorCuenta", "Error al agregar cuenta: " + e.getMessage());
                }
                break;
            case "editar":
            	try {
            	// --- Obtener Tipo de Cuenta ---
            	String idTipoCuentaStr = request.getParameter("tipoCuenta");
            	System.out.println("ServletCuenta: ID Tipo Cuenta recibido = " + idTipoCuentaStr); // Debug

            	if (idTipoCuentaStr == null || idTipoCuentaStr.isEmpty()) {
            	session.setAttribute("mensajeErrorCuenta", "El tipo de cuenta es requerido."); // Específico
            	response.sendRedirect("ServletCuenta?accion=listar");
            	return;
            	}

            	int idTipoCuenta = Integer.parseInt(idTipoCuentaStr);
            	TipoCuenta tipoCuenta = tipoCuentaNegocio.obtenerTipoCuentaPorId(idTipoCuenta); 

            	if (tipoCuenta == null) {
            	System.out.println(
            	"ServletCuenta: ERROR - Tipo de cuenta NO encontrado para ID: " + idTipoCuenta); // Debug
            	session.setAttribute("mensajeErrorCuenta", "Tipo de cuenta no encontrado o inválido."); // Específico
            	response.sendRedirect("ServletCuenta?accion=listar");
            	return;
            	} else {
            	System.out.println(
            	"ServletCuenta: Tipo de cuenta encontrado: " + tipoCuenta.getNombreTipoCuenta()); // Debug
            	}

            	//// --- Obtener Cliente ---
            	String dniClienteStr = request.getParameter("dniClienteHidden");
            	System.out.println("ServletCuenta: DNI Cliente recibido = " + dniClienteStr); // Debug

            	if (dniClienteStr == null || dniClienteStr.isEmpty()) {
            	session.setAttribute("mensajeErrorCuenta", "El DNI del cliente es requerido."); // Específico
            	response.sendRedirect("ServletCuenta?accion=listar");
            	return;
            	}

            	Cliente cliente = clienteNegocio.obtenerPorDni(dniClienteStr);
            	if (cliente == null) {
            	System.out.println("ServletCuenta: ERROR - Cliente NO encontrado para DNI: " + dniClienteStr); // Debug
            	session.setAttribute("mensajeErrorCuenta", "Cliente no encontrado."); // Específico
            	response.sendRedirect("ServletCuenta?accion=listar");
            	return;
            	} else {
            	System.out.println(
            	"ServletCuenta: Cliente encontrado: " + cliente.getDni() + " - " + cliente.getNombre()); // Debug
            	}

            	String idCuentaStr = request.getParameter("idCuenta");
            	if (idCuentaStr == null || idCuentaStr.isEmpty()) {
            	session.setAttribute("mensajeErrorCuenta", "El ID de la cuenta es requerido para editar."); 
            	response.sendRedirect("ServletCuenta?accion=listar");
            	return;
            	}

            	String saldoStr = request.getParameter("saldo");
            	BigDecimal saldo = new BigDecimal(saldoStr);
            	// --- modificar y configurar la Cuenta ---
            	int idCuenta = Integer.parseInt(idCuentaStr);
            	Cuenta cuenta = new Cuenta();
            	cuenta.setIdCuenta(idCuenta);

            	cuenta.setCliente(cliente);
            	cuenta.setTipoCuenta(tipoCuenta);
            	cuenta.setFechaCreacion(java.sql.Date.valueOf(LocalDate.now()));
            	cuenta.setSaldo(saldo); 
            	cuenta.setEstado(true);
            	
            	Cuenta cuentaExistente = cuentaNegocio.obtenerCuentaPorId(idCuenta);
            	if (cuentaExistente == null) {
            	    session.setAttribute("mensajeErrorCuenta", "Cuenta no encontrada.");
            	    response.sendRedirect("ServletCuenta?accion=listar");
            	    return;
            	}
            	
            	cuenta.setNumeroCuenta(cuentaExistente.getNumeroCuenta());
            	cuenta.setCbu(cuentaExistente.getCbu());

            	boolean fueModificado = cuentaNegocio.modificar(cuenta);
            	System.out.println("ServletCuenta: Resultado de cuentaNegocio.agregar = " + fueModificado); // Debug

            	if (fueModificado) {
            	session.setAttribute("mensajeExitoCuenta", "Cuenta modificada correctamente."); // Específico
            	} else {
            	session.setAttribute("mensajeErrorCuenta", "No se pudo modificar la cuenta. Verifique los logs."); // Específico
            	}
            	} catch (NumberFormatException e) {
            	System.err.println("ServletCuenta: NumberFormatException al convertir el ID de Tipo Cuenta: "
            	+ e.getMessage()); // Debug
            	e.printStackTrace(); // Imprimir el stack trace completo
            	session.setAttribute("mensajeErrorCuenta",
            	"Error en el formato del tipo de cuenta. Seleccione un tipo válido."); // Específico
            	} catch (Exception e) {
            	System.err.println("ServletCuenta: ERROR general al agregar cuenta: " + e.getMessage()); // Debug
            	e.printStackTrace(); // Imprimir el stack trace completo para ver la causa raíz
            	session.setAttribute("mensajeErrorCuenta", "Error al agregar cuenta: " + e.getMessage()); // Específico
            	}
                break;
                
            }
        }
        response.sendRedirect(request.getContextPath() + "/ServletCuenta?accion=listar"); // Usar context path
    }
}