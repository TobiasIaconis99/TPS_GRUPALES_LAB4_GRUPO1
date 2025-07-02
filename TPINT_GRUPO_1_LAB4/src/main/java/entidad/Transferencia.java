package entidad;

public class Transferencia {
    private int idTransferencia;
    private int idCuentaOrigen;
    private int idCuentaDestino;
    private int idMovimientoSalida;
    private int idMovimientoEntrada;

    public int getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(int idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public int getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(int idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public int getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(int idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }

    public int getIdMovimientoSalida() {
        return idMovimientoSalida;
    }

    public void setIdMovimientoSalida(int idMovimientoSalida) {
        this.idMovimientoSalida = idMovimientoSalida;
    }

    public int getIdMovimientoEntrada() {
        return idMovimientoEntrada;
    }

    public void setIdMovimientoEntrada(int idMovimientoEntrada) {
        this.idMovimientoEntrada = idMovimientoEntrada;
    }
}
