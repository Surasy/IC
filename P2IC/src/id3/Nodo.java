package id3;

import java.util.ArrayList;

public class Nodo {
	private String nombre;
	private ArrayList<Nodo> hijos;
	private ArrayList<String> aristas;
	
	public Nodo(String nombre) {
		this.nombre = nombre;
		this.hijos = new ArrayList<Nodo>();
		this.aristas = new ArrayList<String>();
	}
	
	public void addHijo(Nodo hijo, String nombreArista) {
		this.hijos.add(hijo);
		this.aristas.add(nombreArista);
	}

}
