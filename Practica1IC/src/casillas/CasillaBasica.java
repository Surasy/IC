package casillas;

import java.awt.Color;

public class CasillaBasica extends Casilla{

	public CasillaBasica(int i, int j){
		super(i,j);
		this.tipo = "CasillaBasica";
	}
	
	public Color print() {
		return Color.GREEN;
		
	}
	
}
