package casillas;

public abstract class Casilla {

	protected int i;
	protected int j;
	
	protected double costeAcum;
	
	protected int iPadre;
	protected int jPadre;
	
	protected Casilla(int i, int j) {
		this.i = i;
		this.j = j;
	}
	
	public double getCosteAcumulado(){
		return costeAcum;
	}
	
	public void setCosteAcumulado(double coste){
		this.costeAcum = coste;
	}
	
	public abstract String getTipo();

}
