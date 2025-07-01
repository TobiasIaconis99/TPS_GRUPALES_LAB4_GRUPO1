package InformesDTO;

import java.math.BigDecimal;

public class InformeAdto {
	 private String tipoMovimiento;
	    private int cantidad;
	    private BigDecimal total;

	    public InformeAdto() {}

	    public InformeAdto (String tipoMovimiento, int cantidad, BigDecimal total) {
	        this.tipoMovimiento = tipoMovimiento;
	        this.cantidad = cantidad;
	        this.total = total;
	    }

	    public String getTipoMovimiento() {
	        return tipoMovimiento;
	    }

	    public void setTipoMovimiento(String tipoMovimiento) {
	        this.tipoMovimiento = tipoMovimiento;
	    }

	    public int getCantidad() {
	        return cantidad;
	    }

	    public void setCantidad(int cantidad) {
	        this.cantidad = cantidad;
	    }

	    public BigDecimal getTotal() {
	        return total;
	    }

	    public void setTotal(BigDecimal total) {
	        this.total = total;
	    }

}
