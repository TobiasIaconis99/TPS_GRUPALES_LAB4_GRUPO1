package dao;


import java.util.List;

import InformesDTO.InformeAdto;

public interface Informesdao {

	public List<InformeAdto> obtenerEstadisticasPorTipoMovimiento();
	public List<InformeAdto> obtenerEgresos();
	public List<InformeAdto> obtenerIngresosPorMesYAnio(int mes, int anio) ;
	public List<InformeAdto> obtenerEgresosPorMesYAnio(int mes, int anio) ;
	
}


