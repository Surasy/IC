package casillas;

import java.awt.Color;

public class CasillaInicio extends Casilla{
	
	public CasillaInicio(int i, int j){
		super(i,j);
		this.costeAcum = 0;
		this.tipo = "CasillaInicio";

	}
	
	public Color print() {
		return Color.CYAN;
		
	}
	
	@Override
	public String getTexto() {
		return "I";
		
	}
}
