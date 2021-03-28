package matriz;

import casillas.Casilla;
import casillas.CasillaAltura;
import casillas.CasillaBasica;
import casillas.CasillaFin;
import casillas.CasillaInicio;
import casillas.CasillaPenalizacion;
import casillas.CasillaPoint;
import casillas.CasillaProhibida;


public class Matriz {
	private Casilla[][] m;
	private int ancho;
	private int alto;
	
	public Matriz(int ancho, int alto) {
		this.ancho = ancho;
		this.alto = alto;
		m = new Casilla[alto][ancho];
		inicializarCasillas();
	}
	
	private void inicializarCasillas(){
		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[i].length; j++){
				m[i][j] = new CasillaBasica(i, j);
			}
		}
	}
	
	public double coste(Casilla c1, Casilla c2){
		int i1 = c1.getI();
		int j1 = c1.getJ();
		int i2 = c2.getI();
		int j2 = c2.getJ();
		
		double distancia = Math.sqrt(Math.pow(i1 - i2,2) + Math.pow(j1 - j2, 2));
		
		double penalizacion = 0;
		if(c2.getTipo() == "CasillaPenalizacion") {
			CasillaPenalizacion cPenalizacion = (CasillaPenalizacion) c2;
			penalizacion = cPenalizacion.getPenalizacion();
		}
		
		//TODO CASTEAR A DOUBLES?
		return distancia + penalizacion;
	}
	
	
	private Casilla casillaTipo(String tipo){
		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m[i].length; j++){
				if(tipo.equals(m[i][j].getTipo()))
					return m[i][j];
			}
		}
		
		return null;
	}
	
	public Casilla getInicio() {
		String ini = "CasillaInicio";
		return casillaTipo(ini);
	}
	
	public Casilla getFin() {
		String fin = "CasillaFin";
		return casillaTipo(fin);
	}
	
	public boolean estaDentro(int i, int j) {
		return i >= 0 && j >= 0 && i < this.alto && j < this.ancho;
	}
	
	public Casilla getCasilla(int i, int j) {
		return m[i][j];
	}
	
	public boolean asignarTipoCasilla(String tipo, int i, int j, Object param){
		Casilla c;
		switch(tipo){
			case "Inicio": 
				c = casillaTipo("CasillaInicio");
				if(c != null) {
					m[i][j] = new CasillaInicio(i,j);
					return true;
				}
				break;
			case "Final": 
				c = casillaTipo("CasillaFin");
				if(c != null) {
					m[i][j] = new CasillaFin(i, j);
					return true;
				}
				break;	
			case "Altura": 
				m[i][j] = new CasillaAltura(i, j, (int) param);
				break;
			case "Basica": 
				m[i][j] = new CasillaBasica(i, j);
				break;
			case "Penalizacion": 
				m[i][j] = new CasillaPenalizacion(i, j, (double) param);
				break;
			case "Prohibida": 
				m[i][j] = new CasillaProhibida(i, j);
				break;
			case "Point": 
				m[i][j] = new CasillaPoint(i, j, (int) param);
				break;

		}
		return false;
	}
	
	public int getAncho(){
		return ancho;
	}
	
	public int getAlto(){
		return alto;
	}
}
