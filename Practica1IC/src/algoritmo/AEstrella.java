package algoritmo;

import java.util.Queue;

import casillas.Casilla;
import casillas.CasillaAltura;
import casillas.CasillaPoint;
import casillas.ComparadorCasillas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import matriz.Matriz;

public class AEstrella {
	private Matriz m;
	private Queue<Casilla> colaCerrada;
	private PriorityQueue<Casilla> colaAbierta;
	
	private Casilla iniTotal;
	private Casilla finTotal;
	
	private int altura;
	
	public AEstrella(Matriz m, int altura) {
		this.m = m;
		this.iniTotal = m.getInicio();
		this.finTotal = m.getFin();
		
		this.altura = altura;
		
		this.colaAbierta = new PriorityQueue<Casilla>(new ComparadorCasillas());
		this.colaCerrada = new LinkedList<Casilla>();

		
	}
	
	
	public ArrayList<int[]> run() {
		ArrayList<CasillaPoint> waypoints = m.buscarWaypoints();
		ArrayList<int[]> caminoAcum = new ArrayList<int[]>();
		
		Casilla inicial = this.iniTotal;
		for(Casilla waypoint: waypoints){
			irDeAaB (inicial, waypoint);
			ArrayList<int[]> actual = recogerCamino(inicial, waypoint);
			if(actual == null)
				return null;
			caminoAcum.addAll(actual);
			inicial = waypoint;
		}
		
		irDeAaB(inicial, this.finTotal);
		ArrayList<int[]> actual = recogerCamino(inicial,  this.finTotal);
		if(actual == null)
			return null;
		caminoAcum.addAll(actual);

		return caminoAcum;
	}
	

	
	private void irDeAaB (Casilla ini, Casilla fin){
		
		this.colaAbierta.clear();
		this.colaCerrada.clear();
		this.colaAbierta.add(ini);
		
		
		while(this.colaAbierta.size() != 0 && this.colaAbierta.peek() != fin){
			Casilla actual = this.colaAbierta.poll();
			
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					int vecinaI = actual.getI() + i;
					int vecinaJ = actual.getJ() + j;
					
					if(!(i == 0 && j == 0) && m.estaDentro(vecinaI, vecinaJ) && casillaAccesible(vecinaI, vecinaJ)) {
						
						Casilla vecina = m.getCasilla(vecinaI, vecinaJ);
						double costeHastaVecina = actual.getCosteAcumulado() + m.coste(actual, vecina);
						
						if(this.colaAbierta.contains(vecina)){
							if(vecina.getCosteAcumulado() > costeHastaVecina){
								colaAbierta.remove(vecina);
								vecina.setCosteAcumulado(costeHastaVecina);
								vecina.setPadre(actual.getI(), actual.getJ());
								colaAbierta.add(vecina);
							}
							
						}
						else if(this.colaCerrada.contains(vecina)){
							if(vecina.getCosteAcumulado() > costeHastaVecina){
								
								vecina.setCosteAcumulado(costeHastaVecina);
								vecina.setPadre(actual.getI(), actual.getJ());
								reasignarCoste(vecina);
							}
							
						}
						else{
							vecina.setPadre(actual.getI(), actual.getJ());
							vecina.setCosteAcumulado(costeHastaVecina);
							this.colaAbierta.add(vecina);
						}
					}
					
				}
			
			}
			colaCerrada.add(actual);
		}

	}
	
	private ArrayList<int[]> recogerCamino(Casilla ini, Casilla fin){
		ArrayList<int[]> recorrido = new ArrayList<int[]>();
		//System.out.println(fin.getI() +" "+ fin.getJ());
		if(fin.getPadre()[0] == -1 || fin.getPadre()[1] == -1){
			return null;
		}
		else{
			int actualI = fin.getPadre()[0];
			int actualJ = fin.getPadre()[1];
			
			while(actualI!= ini.getI() || actualJ!= ini.getJ() ){
				
				Casilla casillaActual = m.getCasilla(actualI, actualJ);
				int[] par = new int[2];
				par[0] = actualI;
				par[1] = actualJ;
				recorrido.add(par);
				actualI = casillaActual.getPadre()[0];
				actualJ = casillaActual.getPadre()[1];
				
			}
			
			return recorrido;
		}

	}
	
	
	private boolean casillaAccesible(int i, int j) {
		Casilla c = m.getCasilla(i, j);
		if(c.getTipo() == "CasillaProhibida")
			return false;
		else if(c.getTipo() == "CasillaAltura") {
			CasillaAltura cAltura = (CasillaAltura) c;
			if(cAltura.getAltura() > altura)
				return false;
		}
		
		return true;
	}
	
	
	private void reasignarCoste(Casilla padre) {
		
		for(Casilla c: colaAbierta){
			int [] pos = c.getPadre();
			if(pos[0] == padre.getI() && pos[1] == padre.getJ()){
				double costeHastaHija = padre.getCosteAcumulado() + m.coste(padre, c);
				colaAbierta.remove(c);
				c.setCosteAcumulado(costeHastaHija);
				colaAbierta.add(c);
				
			}
		}
		
	}
}


