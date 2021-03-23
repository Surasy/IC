package casillas;

public class CasillaInicio extends Casilla{
	
	public CasillaInicio(int i, int j){
		super(i,j);
		this.costeAcum = 0;

	}
	
	public String getTipo(){
		return "CasillaInicio";
	}
}
