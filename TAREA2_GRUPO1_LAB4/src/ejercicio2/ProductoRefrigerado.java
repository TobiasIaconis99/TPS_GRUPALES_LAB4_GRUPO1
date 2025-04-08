package ejercicio2;

import java.sql.Date;

public class ProductoRefrigerado extends Producto {

int codigoOrganismo;

// Constructor
public ProductoRefrigerado(Date fechaDeCaducidad, String numeroLote, int codigoOrganismo) {
    super(fechaDeCaducidad, numeroLote);
    this.codigoOrganismo = codigoOrganismo;
}

public int getCodigoOrganismo() {
	return codigoOrganismo;
}

public void setCodigoOrganismo(int codigoOrganismo) {
	this.codigoOrganismo = codigoOrganismo;
}
// Método para mostrar información

public void mostrarInformacion() {
    super.mostrarInformacion();
    System.out.println("Código del Organismo de Supervisión: " + codigoOrganismo);
}

}