package casillas;

import java.awt.Color;

public class CasillaAltura extends Casilla{
	
	private int altura;
	public CasillaAltura(int i, int j, int altura){
		super(i,j);
		this.altura = altura;
		this.tipo = "CasillaAltura";
	}
	
	public int getAltura() {
		return altura;
	}

	@Override
	public Color print() {
		return Color.RED;
		
	}

}
