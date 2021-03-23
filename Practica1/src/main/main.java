package main;

import casillas.CasillaFin;
import matriz.Matriz;

public class main {

	public static void main(String[] args) {
		Matriz m = new Matriz(5, 5);
		//m.existeTipo(null);
		CasillaFin c = new CasillaFin(3,4);
		
		System.out.println(m.existeTipo(c));

	}

}
