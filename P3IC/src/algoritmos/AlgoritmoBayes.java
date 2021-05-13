package algoritmos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AlgoritmoBayes extends Algoritmo{
	
	private final static String RUTADATOS = "recursos/Iris2Clases.txt";
	private HashMap<Integer, ArrayList<ArrayList<Double>>> mapaElementos;
	private HashMap<Integer, ArrayList<Double>> mapaMedias;
	private HashMap<Integer, ArrayList<ArrayList<Double>>> diferenciaElementoMedia;
	private HashMap<Integer, ArrayList<ArrayList<Double>>> matricesDeCovarianza;
	private ArrayList<ArrayList<Double>> centros;
	private ArrayList<String> etiquetas;

	
	public AlgoritmoBayes(){
		mapaMedias = new HashMap<Integer, ArrayList<Double>>();
		diferenciaElementoMedia = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
		matricesDeCovarianza = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
		
	}
	
	@Override
	public void run() {
		inicializarMedias();
		calcularDiferenciaElementoMedia();
		calcularMatricesDeCovarianza();
		
	}

	@Override
	public String predecir(ArrayList<Double> dato) {
		
		double menorDistancia = Double.MAX_VALUE;
		Integer clasePerteneciente = 0;
		int tamanioEjemplo = dato.size();
		
		for(Entry<Integer, ArrayList<Double>> entry : mapaMedias.entrySet()) {
			Integer clase = entry.getKey();
			ArrayList<Double> media = entry.getValue();
			double distanciaActual = 0;
			ArrayList<Double> diferencia = new ArrayList<Double> ();
			
			//calculamos la diferencia del valor dado con la media de la clase
			for(int j = 0; j < tamanioEjemplo;  j++) {
				double valorRestado = dato.get(j) - media.get(j);
				diferencia.add(valorRestado);
			}
			
			for(int j = 0; j < tamanioEjemplo;  j++) {
				double valorMultiplicado = diferencia.get(j) * diferencia.get(j);
				distanciaActual += valorMultiplicado;
			}
			
			if(distanciaActual < menorDistancia) {
				menorDistancia = distanciaActual;
				clasePerteneciente = clase;
			}
		
		}
		
		return etiquetas.get(clasePerteneciente);
	}
	
	private void inicializarMedias() {
		
		for(Entry<Integer, ArrayList<ArrayList<Double>>> entry : mapaElementos.entrySet()) {
			
			Integer clase = entry.getKey();
			ArrayList<ArrayList<Double>> miembrosClase = entry.getValue();
			int numeroEjemplos = miembrosClase.size();
			int tamanioEjemplo = miembrosClase.get(0).size();
			ArrayList<Double> mediaClase = inicializarArray(tamanioEjemplo);
			
			//recorremos los elementos sumando sus valores
			for(int i = 0; i < numeroEjemplos;  i++) {
				for(int j = 0; j < tamanioEjemplo;  j++) {
					double valorSumado = mediaClase.get(j) + miembrosClase.get(i).get(j);
					mediaClase.set(j, valorSumado);
				}
			}
			
			//dividimos el valor acumulado entre el numero de ejemplos
			for(int j = 0; j < tamanioEjemplo;  j++) {
				double valorDividido = mediaClase.get(j) / numeroEjemplos;
				mediaClase.set(j, valorDividido);
			}
			
			//lo guardamos en el mapa de medias
			mapaMedias.put(clase, mediaClase);
		}
		
	}
	
	private void calcularDiferenciaElementoMedia() {
			
		for(Entry<Integer, ArrayList<ArrayList<Double>>> entry : mapaElementos.entrySet()) {
			Integer clase = entry.getKey();
			ArrayList<ArrayList<Double>> miembrosClase = entry.getValue();
			ArrayList<Double> mediaDeClase = mapaMedias.get(clase);
			int numeroEjemplos = miembrosClase.size();
			int tamanioEjemplo = miembrosClase.get(0).size();
			ArrayList<Double> diferencia = new ArrayList<Double> ();
			
			//recorremos los elementos calculando la diferencia con la media de su clase
			for(int i = 0; i < numeroEjemplos;  i++) {
				for(int j = 0; j < tamanioEjemplo;  j++) {
					double valorRestado = miembrosClase.get(i).get(j) - mediaDeClase.get(j);
					diferencia.add(valorRestado);
				}
				
				if(!diferenciaElementoMedia.containsKey(clase)) {
					diferenciaElementoMedia.put(clase, new ArrayList<ArrayList<Double>>());
				}
				
				ArrayList<ArrayList<Double>> data = diferenciaElementoMedia.get(clase);
			    data.add(diferencia);
			    diferenciaElementoMedia.put(clase, data);
			}
		}
	}
	
	private void calcularMatricesDeCovarianza() {
		
		for(Entry<Integer, ArrayList<ArrayList<Double>>> entry : diferenciaElementoMedia.entrySet()) {
			Integer clase = entry.getKey();
			ArrayList<ArrayList<Double>> restasConMediaClase = entry.getValue();
			
			int numeroEjemplos = restasConMediaClase.size();
			int tamanioEjemplo = restasConMediaClase.get(0).size();
			ArrayList<ArrayList<Double>> acum = inicializarMatriz (tamanioEjemplo);
			
			//recorremos las restas acumulandolas en una matriz
			for(int i = 0; i < numeroEjemplos;  i++) {
				for(int j = 0; j < tamanioEjemplo;  j++) {
					
					for(int k = 0; k < tamanioEjemplo;  k++) {
						double valorMultiplicado = restasConMediaClase.get(i).get(j) * restasConMediaClase.get(i).get(k);
						double valorAculmulado = valorMultiplicado + acum.get(j).get(k);
						acum.get(j).set(k, valorAculmulado);
					}
				}
			}
			
			//dividimos la matriz acumulada resultante entre el número de elementos
			for(int j = 0; j < tamanioEjemplo;  j++) {
				for(int k = 0; k < tamanioEjemplo;  k++) {
					double valorDividido = acum.get(j).get(k) / numeroEjemplos;
					acum.get(j).set(k, valorDividido);
				}
			}
			
			matricesDeCovarianza.put(clase, acum);
		}
	}
	
	private ArrayList<ArrayList<Double>> inicializarMatriz (int tamaño){
		ArrayList<ArrayList<Double>> array = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < tamaño; i++) {
			for(int j = 0; j < tamaño; j++) {
				if(j == 0) {
					array.add(new ArrayList<Double>());
				}
				array.get(i).add(0.0);
			}
		}
		
		return array;
	}
	
	private ArrayList<Double> inicializarArray (int tamaño){
		ArrayList<Double> array = new ArrayList<Double>();
		
		for(int i = 0; i < tamaño; i++) {
			array.add(0.0);
		}
		
		return array;
	}
	
	
	public void leerDatos(String nombreArchivo) throws IOException {
		BufferedReader csvReader = new BufferedReader(new FileReader(nombreArchivo));
		String row;
		ArrayList<String> rowData;
		HashMap<String, Integer> clasesUsadas = new HashMap<String, Integer> ();
		int claseSiguiente = 0;
		etiquetas = new ArrayList<String>();
		HashMap<Integer, ArrayList<ArrayList<Double>>> sol = new HashMap<Integer, ArrayList<ArrayList<Double>>> ();
		
		while ((row = csvReader.readLine()) != null) {
			rowData = new ArrayList<String>();
			Collections.addAll(rowData, row.split(","));
			
			ArrayList<Double> rowDataDouble = new ArrayList<Double>();
			
			for(int i = 0; i < rowData.size() - 1; i++) {
				rowDataDouble.add(Double.parseDouble(rowData.get(i)));

			}
			
			String claseActual = rowData.get(rowData.size() - 1);
			// si el diccionario no tiene esa entrada, inicializamos
			if(!clasesUsadas.containsKey(claseActual)) {
				clasesUsadas.put(claseActual, claseSiguiente);
				sol.put(claseSiguiente, new ArrayList<ArrayList<Double>>());
				claseSiguiente++;
			}
			
			if(!etiquetas.contains(claseActual)){
				etiquetas.add(claseActual);
			}
			
			ArrayList<ArrayList<Double>> data = sol.get(clasesUsadas.get(claseActual));
		    data.add(rowDataDouble);
		    sol.put(clasesUsadas.get(claseActual), data);
		}
		
		csvReader.close();
		this.mapaElementos = sol;

	}
	

}
