package casillas;

import java.awt.Color;

public class CasillaPenalizacion extends Casilla{
	//TODO CAMBIAR A INT SEGURAMENTE
	private double penalizacion;
	
	public CasillaPenalizacion(int i, int j, double penalizacion){
		super(i,j);
		this.penalizacion = penalizacion;
		this.tipo = "CasillaPenalizacion";
	}
	
	public double getPenalizacion(){
		return penalizacion;
	}
	
	public Color print() {
		return Color.PINK;
		
	}
}
