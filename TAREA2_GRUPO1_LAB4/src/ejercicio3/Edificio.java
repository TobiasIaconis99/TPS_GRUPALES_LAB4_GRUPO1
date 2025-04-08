package ejercicio3;

public class Edificio implements  IEdificio{
	int numOficinas;
	double superficie;
	
	public Edificio(int numOficinas,double superficie) {
		this.numOficinas = numOficinas;
		this.superficie = superficie;
	}
	public Edificio() {
		numOficinas = 10;
		superficie = 2000;
	}
	public int getNumOficinas() {
		return numOficinas;
	}
	public void setNumOficinas(int numOficinas) {
		this.numOficinas = numOficinas;
	}
	@Override
	public String toString() {
		return "Edificio [numOficinas=" + numOficinas + "]";
	}
	@Override
	public double getSuperficieEdificio() {
		
		return superficie;
	}
	
	
}
