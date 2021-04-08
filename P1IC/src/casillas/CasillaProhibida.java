package casillas;

import java.awt.Color;

public class CasillaProhibida extends Casilla{
	
	
	public CasillaProhibida(int i, int j){
		super(i,j);
		this.tipo = "CasillaProhibida";
	}

		@Override
	public Color print() {
		return Color.BLACK;
		
	}
	
	@Override
	public String getTexto() {
		return "X";
		
	}
}
