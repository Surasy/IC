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
		Nodo nodo = null;
		try {
			nodo = recursiva(atributos, datos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nodo;
	}
	
	public Nodo recursiva(ArrayList<String> atributos, ArrayList<ArrayList<String>> datos) throws Exception {
		// 0 -> elementos diferentes
		// 1 -> todos los elementos positivos
		// -1 -> todos los elementos negativos
		// -2 -> error
		int tipoCaso = comprobarCasoBase(datos);
		
		if(tipoCaso == 1) {
			return new Nodo("Positivo");
		}
		else if(tipoCaso == -1) {
			return new Nodo("Negativo");
		}
		else if(tipoCaso == 0){
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
						fila.remove(mejorI);
						nuevosDatos.add(fila);
					}
				}
				
				nodoActual.addHijo(recursiva(nuevosAtributos, nuevosDatos), hijo);
			}
			return nodoActual;
		}
		else {
			throw new Exception("LA TABLA NO TIENE NINGUNA FILA");
		}

	}
	
	
	private int comprobarCasoBase(ArrayList<ArrayList<String>> datos){
		String dato = "";
		
		if(datos.size() > 0) {
			int ultimaColumna = datos.get(0).size() - 1;
			String datoActual = "";

			for(int i = 0; i < datos.size(); i++) {
				datoActual = datos.get(i).get(ultimaColumna);
				if(dato.equals("")) {
					dato = datoActual;
				}
				else if(!dato.equals(datoActual)) {
					return 0;
				}
			}
			
			if(datoActual.equals("si")) {
				return 1;
			}
			else {
				return -1;
			}
			
		}
		return -2;
		
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
		ArrayList<Double> meritos = new ArrayList<Double>(atributos.size() - 1);
		
		for(int i = 0; i < atributos.size() - 1; i++){
			
			int numeroFilas = datos.size();
			HashMap<String, ArrayList<Integer>> tablaID3 = new HashMap<String, ArrayList<Integer>>();
			
			for(int j = 0; j < numeroFilas; j++) {
				String dato = datos.get(j).get(i);
				String positivo = datos.get(j).get(atributos.size() - 1);
				
				if(!tablaID3.containsKey(dato)) {
					ArrayList<Integer> par = new ArrayList<Integer>(2);
					par.add(0, 0);
					par.add(1, 0);
					tablaID3.put(dato, par);
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
			meritos.add(i, calcularMerito(tablaID3, numeroFilas));
		}
		
		return meritos;
	}
	
	private double calcularMerito(HashMap<String, ArrayList<Integer>> tablaID3, int numeroFilas){
		double acum = 0;

		for(Entry<String, ArrayList<Integer>> entry : tablaID3.entrySet()){
			
			ArrayList<Integer> datos = entry.getValue();
			double datoCero =  datos.get(0);
			double datoUno =  datos.get(1);
			double r = (datoCero + datoUno)/ (double)numeroFilas;
			acum += r * infor(datoUno, datoCero);

		}

		return acum;
	}
	
	// infor(p,n) = -p log2 (p) - n log2(n)

	private double infor(double pEntrada, double nEntrada){
		double p = pEntrada / (pEntrada + nEntrada);
		double n = nEntrada / (pEntrada + nEntrada);
		
		
		double logp = 0;
		double logn = 0;
		if(p != 0) {
			logp = logaritmo(2, p);
		}
		
		if(n != 0) {
			logn = logaritmo(2, n);
		}
		
		return -p * logp - n * logn;
	}
	
	private double logaritmo (int base, double valor){
		//log en base a de x = (log en base b de x / log en base b de a)
		//siendo la base b 10 en este caso
		
		return (double)(Math.log(valor) / Math.log(base));
	}
}
