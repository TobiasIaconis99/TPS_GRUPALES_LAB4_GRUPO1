package servlet;

import excepciones.*;
import negocio.CuentaNegocio;
import negocio.TransferenciaNegocio;
import negocioImpl.CuentaNegocioImpl;
import negocioImpl.TransferenciaNegocioImpl;
import entidad.Cliente;
import entidad.Cuenta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/ServletTransferencia")
public class ServletTransferencia extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TransferenciaNegocio transferenciaNegocio = new TransferenciaNegocioImpl();
    private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");

        if (clienteLogueado == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        int idCuentaOrigen = Integer.parseInt(request.getParameter("cuentaOrigen"));
        String cbuDestino = request.getParameter("cbuDestino");
        BigDecimal monto = new BigDecimal(request.getParameter("monto"));

        try {
            boolean exito = transferenciaNegocio.transferir(idCuentaOrigen, cbuDestino, monto);

            if (exito) {
                request.setAttribute("mensaje", "Transferencia realizada con éxito.");
            } else {
                request.setAttribute("error", "No se pudo completar la transferencia.");
            }

        } catch (SaldoInsuficienteException | CuentaDestinoInvalidaException | CBUNoEncontradoException e) {
            request.setAttribute("error", e.getMessage());
        }

        // 🔁 Cargar cuentas del cliente para mostrar en el select
        request.setAttribute("listaCuentas", cuentaNegocio.listarCuentasPorCliente(clienteLogueado.getId()));

        request.getRequestDispatcher("/Transferencias.jsp").forward(request, response);
    }
    
}
