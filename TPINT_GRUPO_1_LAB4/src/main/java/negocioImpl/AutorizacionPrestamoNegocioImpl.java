package negocioImpl;

import dao.AutorizacionPrestamoDao;
import daoImpl.AutorizacionPrestamoDaoImpl;
import entidad.Prestamo;
import negocio.AutorizacionPrestamoNegocio;

import java.util.List;

public class AutorizacionPrestamoNegocioImpl implements AutorizacionPrestamoNegocio {

    private AutorizacionPrestamoDao dao = new AutorizacionPrestamoDaoImpl();

    @Override
    public List<Prestamo> listarPrestamosPaginado(String busquedaDNI, String filtroEstado, int pagina, int registrosPorPagina) {
        int offset = (pagina - 1) * registrosPorPagina;
        return dao.obtenerPrestamosFiltradosPaginados(busquedaDNI, filtroEstado, offset, registrosPorPagina);
    }

    @Override
    public int contarTotalPrestamos(String busquedaDNI, String filtroEstado) {
        return dao.contarTotalPrestamos(busquedaDNI, filtroEstado);
    }

    @Override
    public boolean modificarEstado(int idPrestamo, int idEstado) {
        return dao.modificarEstado(idPrestamo, idEstado);
    }

	@Override
	public int obtenerTotalPrestamos(String busquedaDNI, String filtroEstado) {
		// TODO Auto-generated method stub
		return 0;
	}
}
