package dao;


import java.util.List;

import InformesDTO.InformeAdto;

public interface Informesdao {

	public List<InformeAdto> obtenerEstadisticasPorTipoMovimiento();
	public List<InformeAdto> obtenerEgresos();
	
}
