package main;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import casillas.Casilla;
import casillas.CasillaFin;
import casillas.CasillaInicio;
import casillas.ComparadorCasillas;
import matriz.Matriz;

public class main {

	public static void main(String[] args) {
		Queue<Casilla> colaCerrada;
		PriorityQueue<Casilla> colaAbierta;
		

		colaAbierta = new PriorityQueue<Casilla>(new ComparadorCasillas());
		colaCerrada = new LinkedList<Casilla>();
		
		Casilla c = new CasillaInicio(0,0);
		Casilla c2 = new CasillaInicio(1,0);
		c.setCosteAcumulado(25);
		
		

		colaAbierta.add(c);
		colaAbierta.add(c2);

		

		System.out.println(colaAbierta.peek().getCosteAcumulado());
		colaAbierta.poll();
		System.out.println(colaAbierta.peek().getCosteAcumulado());
		colaAbierta.poll();
		System.out.println("------------");
		
		
		

		colaAbierta.add(c);
		colaAbierta.remove(c2);
		c2.setCosteAcumulado(200);
		colaAbierta.add(c2);
		
		
		System.out.println(colaAbierta.peek().getCosteAcumulado());
		colaAbierta.poll();
		System.out.println(colaAbierta.peek().getCosteAcumulado());

	}

}
