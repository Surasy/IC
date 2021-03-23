package casillas;

public class CasillaAltura extends Casilla{
	
	private int altura;
	public CasillaAltura(int i, int j, int altura){
		super(i,j);
		this.altura = altura;
	}
	
	public String getTipo(){
		return "CasillaAltura";
	}
}
