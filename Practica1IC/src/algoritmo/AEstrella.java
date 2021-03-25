package algoritmo;

import java.util.Queue;

import casillas.Casilla;
import casillas.CasillaAltura;
import casillas.CasillaInicio;
import casillas.ComparadorCasillas;

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
	
	public void run() {
		//AJUSTAR PARA LOS POINTS
		//INTRODUCIMOS LA CASILLA INICIAL
		this.colaAbierta.add(ini);
		
		//ITERAR
		while(this.colaAbierta.size() > 0 || this.colaAbierta.peek() == fin){
			Casilla actual = this.colaAbierta.poll();
			
			
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					int vecinaI = actual.getI() + i;
					int vecinaJ = actual.getJ() + j;
					//TODO BORRAR
					//COMPROBAR SI FUNCIONA ESTÁ DENTRO
					if(i != 0 && j != 0 && m.estaDentro(vecinaI, vecinaJ) && casillaAccesible(vecinaI, vecinaJ)) {
						
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
							
						}
						else{
							vecina.setPadre(actual.getI(), actual.getJ());
							vecina.setCosteAcumulado(costeHastaVecina);
							this.colaAbierta.add(vecina);
						}
					}
					
				}
				
				//METER EN LA CERRADA
				colaCerrada.add(actual);
			}
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
}


/*//Comparator for name field
Comparator<Employee> nameSorter = Comparator.comparing(Employee::getName);
 
PriorityQueue<Employee> priorityQueue = new PriorityQueue<>( nameSorter );
         
priorityQueue.add(new Employee(1l, "AAA", LocalDate.now()));
priorityQueue.add(new Employee(4l, "CCC", LocalDate.now()));
priorityQueue.add(new Employee(5l, "BBB", LocalDate.now()));
priorityQueue.add(new Employee(2l, "FFF", LocalDate.now()));
priorityQueue.add(new Employee(3l, "DDD", LocalDate.now()));
priorityQueue.add(new Employee(6l, "EEE", LocalDate.now()));
 
while(true) 
{
    Employee e = priorityQueue.poll();
    System.out.println(e);
     
    if(e == null) break;
}*/