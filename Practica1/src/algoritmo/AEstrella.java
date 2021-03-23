package algoritmo;

import java.util.Queue;

import casillas.Casilla;
import casillas.CasillaInicio;

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
	
	public AEstrella(Matriz m) {
		this.m = m;
		this.ini = m.getInicio();
		this.fin = m.getFin();
		
		//PROBAR
		Comparator<Casilla> lessCost = Comparator.comparing(Casilla::getCosteAcumulado);
		this.colaAbierta = new PriorityQueue<Casilla>(0, lessCost);
		this.colaCerrada = new LinkedList<Casilla>();
		
		//ITERAR
		//TODO
		
	}
	
	public void run() {
		
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