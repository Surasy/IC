package algoritmo;

import java.util.Queue;

import casillas.Casilla;
import casillas.CasillaAltura;
import casillas.CasillaInicio;
import casillas.ComparadorCasillas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import matriz.Matriz;

public class AEstrella {
	private Matriz m;
	private Queue<Casilla> colaCerrada;
	private PriorityQueue<Casilla> colaAbierta;
	
	private Casilla ini;
	private Casilla fin;
	
	private int altura;
	
	public AEstrella(Matriz m, int altura) {
		this.m = m;
		this.ini = m.getInicio();
		this.fin = m.getFin();
		
		this.altura = altura;
		
		//PROBAR
		this.colaAbierta = new PriorityQueue<Casilla>(new ComparadorCasillas());
		this.colaCerrada = new LinkedList<Casilla>();
		
		
		//TODO
		
	}
	
	//TODO VOLVER HACIA ATRAS FIN -> INI
	public ArrayList<int[]> run() {
		//AJUSTAR PARA LOS POINTS
		//INTRODUCIMOS LA CASILLA INICIAL
		this.colaAbierta.add(ini);
		
		//ITERAR
		while(this.colaAbierta.size() != 0 && this.colaAbierta.peek() != fin){
			Casilla actual = this.colaAbierta.poll();
			
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					int vecinaI = actual.getI() + i;
					int vecinaJ = actual.getJ() + j;
					//TODO BORRAR
					//COMPROBAR SI FUNCIONA ESTADENTRO
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
			//METER EN LA CERRADA
			colaCerrada.add(actual);
		}
		
		
		
		
		return recogerCamino();
	}
	
	private ArrayList<int[]> recogerCamino(){
		ArrayList<int[]> recorrido = new ArrayList<int[]>();
		if(fin.getI() == -1 && fin.getJ() == -1){
			return null;
		}
		else{
			int actualI = fin.getPadre()[0];
			int actualJ = fin.getPadre()[1];
			
			while(actualI!= -1 && actualJ!= -1){
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
	
	//comprobar que cambia los valores
	private void reasignarCoste(Casilla padre) {
		
		//buscar hijos en colaAbierta
		for(Casilla c: colaAbierta){
			int [] pos = c.getPadre();
			if(pos[0] == padre.getI() && pos[1] == padre.getJ()){
				double costeHastaHija = padre.getCosteAcumulado() + m.coste(padre, c);
				colaAbierta.remove(c);
				c.setCosteAcumulado(costeHastaHija);
				colaAbierta.add(c);
				
			}
		}
		
		//for hijo cerrada
		
		/*for(Casilla c: colaCerrada){
			int [] pos = c.getPadre();
			if(pos[0] == padre.getI() && pos[1] == padre.getJ()){
				recursiva(padre, c);
			}
		}*/
			//llamada recursiva
	}
}


