package casillas;

public class CasillaPenalizacion extends Casilla{

	private double penalizacion;
	
	public CasillaPenalizacion(int i, int j, double penalizacion){
		super(i,j);
		this.penalizacion = penalizacion;
	}
	
	public String getTipo(){
		return "CasillaPenalizacion";
	}
}
