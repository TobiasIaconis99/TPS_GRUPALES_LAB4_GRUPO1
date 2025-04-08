package ejercicio1;

import java.util.Objects;

public class Profesor extends Empleado implements Comparable<Profesor> {
	
	private String cargo;
	private int antiguedadDocente;

	public Profesor() {
		super();
		this.cargo = "Sin cargo";
		this.antiguedadDocente = 0;
		
	}

	public Profesor(String nombre, int edad, String cargo, int antiguedadDocente) {
		super(nombre, edad);
		this.cargo = cargo;
		this.antiguedadDocente = antiguedadDocente;
	
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public int getAntiguedadDocente() {
		return antiguedadDocente;
	}

	public void setAntiguedadDocente(int antiguedadDocente) {
		this.antiguedadDocente = antiguedadDocente;
	}

	public String toString() {
		
		return "ID: " + super.getId() + ", Nombre: " + super.getNombre() + ", Edad:" + super.getEdad() + ", Cargo: " + cargo +", Antigüedad docente: " + antiguedadDocente;
		//return super.toString() + ", CARGO=" + cargo + ", ANTIGÜEDAD=" + antiguedadDocente;		
		
	}

	@Override
	public int compareTo(Profesor o) {
	    if (this.antiguedadDocente < o.antiguedadDocente) {
	        return -1;
	    } else if (this.antiguedadDocente > o.antiguedadDocente) {
	        return -1;
	    } else {
	        return 0;
	    }
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(antiguedadDocente, cargo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return antiguedadDocente == other.antiguedadDocente && Objects.equals(cargo, other.cargo);
	}



	
}
