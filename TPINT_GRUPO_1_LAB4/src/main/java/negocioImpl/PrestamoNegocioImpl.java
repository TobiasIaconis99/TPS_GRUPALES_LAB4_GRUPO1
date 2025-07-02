package negocioImpl;

import java.math.BigDecimal;
import java.util.List;

import dao.PrestamoDao;
import daoImpl.PrestamoDaoImpl;
import entidad.Cuenta;
import entidad.Prestamo;
import negocio.CuentaNegocio;
import negocio.PrestamoNegocio;

public class PrestamoNegocioImpl implements PrestamoNegocio, CuentaNegocio {

	private PrestamoDao dao = new PrestamoDaoImpl();
    private CuentaNegocio cuentaNegocio = new CuentaNegocioImpl(); // Agregado
    
	@Override
	public List<Prestamo> listar() {
		
		return dao.listar();
	}

	@Override
	public boolean agregar(Prestamo p) {
        Cuenta cuenta = cuentaNegocio.obtenerCuentaPorId(p.getIdCuenta());
        if (cuenta == null) {
            System.err.println("Cuenta no encontrada con ID: " + p.getIdCuenta());
            return false;
        }

        BigDecimal saldoDisponible = cuenta.getSaldo();
        if (p.getMontoSolicitado().compareTo(saldoDisponible) >= 1000) {
            System.err.println("Monto solicitado de ser minimo del 1000: " + saldoDisponible + ", Solicitado: " + p.getMontoSolicitado());
            return false;
        }
		return dao.agregar(p);
	}

	@Override
	public boolean modificar(Prestamo u) {
		
		return false;
	}

	@Override
	public boolean eliminar(int idPrestamo) {
		
		return false;
	}

	@Override
	public boolean agregar(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificar(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tieneMenos3CuentasAct(int idCliente) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cuenta> listarCuentas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cuenta> listarCuentasActivas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cuenta> listarCuentasPorCliente(int idCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cuenta> obtenerCuentaPorClienteId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existeNumeroCuenta(String numeroCuenta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existeCbu(String cbu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cuenta obtenerCuentaPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cuenta obtenerCuentaPorNumero(String numeroCuenta) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
