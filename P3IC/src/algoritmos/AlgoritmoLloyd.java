package algoritmos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AlgoritmoLloyd extends Algoritmo{
	private ArrayList<ArrayList<Double>> centros;
	private ArrayList<ArrayList<Double>> centrosAnteriores;
	private double tolerancia;
	private ArrayList<ArrayList<Double>> matrizElementos;
	private double aprendizaje;
	private int maxIteraciones;
	private ArrayList<String> etiquetas;
	
	
	public AlgoritmoLloyd(double tolerancia, double aprendizaje, int maxIteraciones){
		this.tolerancia = tolerancia;
		this.aprendizaje = aprendizaje;
		this.maxIteraciones = maxIteraciones;
		inicializarCentros();
	}
	
	private void inicializarCentros(){
		centros = new ArrayList<ArrayList<Double>>();
		
		Double[][] centrosPredefinidos = {{4.6, 3.0, 4.0, 0.0}, {6.8, 3.4, 4.6, 0.7}};
		
		for(int i = 0; i < centrosPredefinidos.length; i++) {
			Double[] fila = centrosPredefinidos[i];
			
			ArrayList<Double> nuevo = new ArrayList<Double>();
			Collections.addAll(nuevo, fila);
			centros.add(nuevo);
		}
	}
	

	public void run() {
		int iteracion = 1;
		recalcularCentros();
		while(iteracion < maxIteraciones && continuar()){
//			System.out.println("Iteracion: " + iteracion);
			recalcularCentros();
			iteracion++;
		}
		
	}
	
	
	public void leerDatos(String nombreArchivo) throws IOException {
		BufferedReader csvReader = new BufferedReader(new FileReader(nombreArchivo));
		String row;
		ArrayList<String> rowData;
		ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		etiquetas = new ArrayList<String> ();
		
		while ((row = csvReader.readLine()) != null) {
			rowData = new ArrayList<String>();
			Collections.addAll(rowData, row.split(","));
			
			ArrayList<Double> rowDataDouble = new ArrayList<Double>();
			
			for(int i = 0; i < rowData.size() - 1; i++) {
				rowDataDouble.add(Double.parseDouble(rowData.get(i)));

			}
			
			String clase = rowData.get(rowData.size() - 1);
			if(!etiquetas.contains(clase)){
				etiquetas.add(clase);
			}
			
		    data.add(rowDataDouble);
		}
		
		csvReader.close();
		this.matrizElementos = data;
	}

	private ArrayList<ArrayList<Double>> clonarCentro(ArrayList<ArrayList<Double>> c){
		ArrayList<ArrayList<Double>> salida = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < c.size(); i++) {
			ArrayList<Double> fila = new ArrayList<Double>();
			for(int j = 0; j < c.get(i).size(); j++) {
				fila.add(c.get(i).get(j));
			}
			salida.add(fila);
			
		}
		return salida;
	}
	
	
	private void recalcularCentros() {
		centrosAnteriores = clonarCentro(centros);
		
		for(int i = 0; i < matrizElementos.size(); i++) {
			ArrayList<Double> elemento = matrizElementos.get(i);
			int centroCercano = buscarCentroCercano(elemento);
			ArrayList<Double> centro = centros.get(centroCercano);
			recalcularCentro(elemento, centro);
		}
		
	}
	

	private void recalcularCentro(ArrayList<Double> elemento, ArrayList<Double> centro){
		for(int i = 0; i < centro.size(); i++) {
			double valor = centro.get(i) + this.aprendizaje * (elemento.get(i) - centro.get(i));
			centro.set(i, valor);
		}
	}

	private int buscarCentroCercano(ArrayList<Double> elemento) {
		double mejorDistancia = Double.MAX_VALUE;
		int mejorCentro = -1;
		
		for(int i = 0; i < centros.size(); i++) {
			ArrayList<Double> centro = centros.get(i);
			double distanciaActual = 0;
			for(int j = 0; j < centro.size(); j++) {
				distanciaActual += Math.pow(elemento.get(j) - centro.get(j), 2);
			}
			distanciaActual = Math.sqrt(distanciaActual);
			
			if(distanciaActual < mejorDistancia) {
				mejorCentro = i;
				mejorDistancia = distanciaActual;
			}
		}
		
		return mejorCentro;
	}

	private boolean continuar() {
		double delta = 0;
		for(int i = 0; i < centros.size(); i++) {
			for(int j = 0; j < centros.get(i).size(); j++) {
				delta += Math.pow(centros.get(i).get(j) - centrosAnteriores.get(i).get(j), 2);
			}
			
			delta = Math.sqrt(delta);
			if(delta > tolerancia){
				return true;
			}
		}
		
		return false;
	}

	public String predecir(ArrayList<Double> dato) {
		return etiquetas.get(buscarCentroCercano(dato));
	}
}
