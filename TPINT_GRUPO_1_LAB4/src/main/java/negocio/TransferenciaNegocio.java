package negocio;

import java.math.BigDecimal;
import excepciones.*;

public interface TransferenciaNegocio {
    boolean transferir(int idCuentaOrigen, String cbuDestino, BigDecimal monto)
        throws SaldoInsuficienteException, CuentaDestinoInvalidaException, CBUNoEncontradoException;
}
