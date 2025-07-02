package dao;


import java.util.List;

import InformesDTO.InformeAdto;
import InformesDTO.InformeBdto;

public interface Informesdao {
	
	// Indorme A

	public List<InformeAdto> obtenerEstadisticasPorTipoMovimiento();
	public List<InformeAdto> obtenerEgresos();
	public List<InformeAdto> obtenerIngresosPorMesYAnio(int mes, int anio) ;
	public List<InformeAdto> obtenerEgresosPorMesYAnio(int mes, int anio) ;
	
	// Informe B
	List<InformeBdto> listarClientesConMasMovimientos(int mes, int anio);
}


