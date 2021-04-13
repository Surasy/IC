package id3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class Algoritmo {
	private ArrayList<String> atributos;
	private ArrayList<ArrayList<String>> datos;
	
	public Algoritmo(ArrayList<String> atributos, ArrayList<ArrayList<String>> datos) {
		this.atributos = atributos;
		this.datos = datos;
	}
	
	//TODO completar con recursiva
	public Nodo run() {
		return recursiva(atributos, datos);
	}
	
	public Nodo recursiva(ArrayList<String> atributos, ArrayList<ArrayList<String>> datos) {
		//if caso base
		//	crear nodo base
		//else
		

		ArrayList<Double> meritos = calcularMeritos(atributos, datos);
		int mejorI = menorMerito(meritos);
		
		Nodo nodoActual = new Nodo(atributos.get(mejorI));
		Set<String> hijos = new TreeSet<String>();
		for(int i = 0; i < datos.size(); i++) {
			String valor = datos.get(i).get(mejorI);
			hijos.add(valor);
		}
		
		ArrayList<String> nuevosAtributos = (ArrayList<String>) atributos.clone();
		nuevosAtributos.remove(mejorI);
		
		for(String hijo: hijos){
			ArrayList<ArrayList<String>> nuevosDatos = new ArrayList<ArrayList<String>>();
			for(int i = 0; i < datos.size(); i++) {
				ArrayList<String> fila = datos.get(i);
				if(fila.get(mejorI).equals(hijo)) {
					nuevosDatos.add(fila);
				}
			}
			
			nodoActual.addHijo(recursiva(nuevosAtributos, nuevosDatos), hijo);
		}
		
		return nodoActual;
	}
	
	
	private int menorMerito(ArrayList<Double> meritos) {
		int mejorI = -1;
		double mejor = Double.MAX_VALUE;
		
		for(int i = 0; i < meritos.size(); i++){
			if(meritos.get(i) < mejor){
				mejor = meritos.get(i);
				mejorI = i;
			}
		}
		
		return mejorI;
	}
	
	private ArrayList<Double> calcularMeritos(ArrayList<String> atributos, ArrayList<ArrayList<String>> datos){
		ArrayList<Double> meritos = new ArrayList<Double>(atributos.size());
		
		for(int i = 0; i < atributos.size() - 1; i++){
			
			int numeroFilas = datos.size();
			HashMap<String, ArrayList<Integer>> tablaID3 = new HashMap<String, ArrayList<Integer>>();
			
			for(int j = 0; j < numeroFilas; j++) {
				String dato = datos.get(j).get(i);
				String positivo = datos.get(j).get(atributos.size() - 1);
				
				if(!tablaID3.containsKey(dato)) {
					tablaID3.put(dato, new ArrayList<Integer>(2));
				}
				
				// 0 negativo
				// 1 positivo
				if(positivo.equals("si")) {
					Integer numero = tablaID3.get(dato).get(1);
					tablaID3.get(dato).set(1, numero + 1);
				}
				else {
					Integer numero = tablaID3.get(dato).get(0);
					tablaID3.get(dato).set(0, numero + 1);
				}
			}
			
			// calcular merito de cada atributo
			meritos.set(i, calcularMerito(tablaID3, numeroFilas));
		}
		
		return meritos;
	}
	
	private double calcularMerito(HashMap<String, ArrayList<Integer>> tablaID3, int numeroFilas){
		double acum = 0;

		for(Entry<String, ArrayList<Integer>> entry : tablaID3.entrySet()){
			
			ArrayList<Integer> datos = entry.getValue();
			double r = (datos.get(0) + datos.get(1))/numeroFilas;
			acum += r * infor(datos.get(1), datos.get(0));
		}
		return acum;
	}
	
	// infor(p,n) = -p log2 (p) - n log2(n)

	private double infor(int pEntrada, int nEntrada){
		double p = pEntrada / (pEntrada + nEntrada);
		double n = nEntrada / (pEntrada + nEntrada);
		
		return -p * logaritmo(2, p) - n * logaritmo(2, n);
	}
	
	private double logaritmo (int base, double valor){
		//log en base a de x = (log en base b de x / log en base b de a)
		//siendo la base b 10 en este caso
		
		return (double)(Math.log(valor) / Math.log(base));
	}
}
