package casillas;

public class CasillaProhibida extends Casilla{
	
	
	public CasillaProhibida(int i, int j){
		super(i,j);
	}
	
	public String getTipo(){
		return "CasillaProhibida";
	}
}
