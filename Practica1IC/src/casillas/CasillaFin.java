package casillas;

import java.awt.Color;

public class CasillaFin extends Casilla{
	
	public CasillaFin(int i, int j){
		super(i,j);
		this.tipo = "CasillaFin";
	}
	
	
	public Color print() {
		return Color.BLUE;
		
	}
	
	@Override
	public String getTexto() {
		return "F";
		
	}
}
