package casillas;

public class CasillaPoint extends Casilla{

	private int posicion; 
	
	public CasillaPoint(int i, int j, int orden){
		super(i,j);
		this.posicion = orden;
		this.tipo = "CasillaPoint";
	}
	
	
}
