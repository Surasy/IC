package casillas;

import java.awt.Color;

public class CasillaPoint extends Casilla{

	private int posicion; 
	
	public CasillaPoint(int i, int j, int orden){
		super(i,j);
		this.posicion = orden;
		this.tipo = "CasillaPoint";
	}
	
		@Override
	public Color print() {
		return Color.MAGENTA;
		
	}
	
}
