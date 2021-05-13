package algoritmos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AlgoritmoKMedias extends Algoritmo{
	
	private ArrayList<ArrayList<Double>> matrizElementos;
	private ArrayList<ArrayList<Double>> centros;
	private ArrayList<ArrayList<Double>> centrosAnteriores;
	private  ArrayList<ArrayList<Double>> probabilidades;
	private double tolerancia;
	private int b;
	private final int LIMITE = 400;
	private ArrayList<String> etiquetas;
	
	public AlgoritmoKMedias(double tolerancia, int b){
		this.tolerancia = tolerancia;
		this.b = b;
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
	
	public void run(){
		centros = recalcularCentros();
		int pasos = 1;
		
		// while vi<tolerancia y no se pase de un limite de pasos
		while(continuar() && pasos < LIMITE) {
			// recalcular vi
			centros = recalcularCentros();
			
			pasos++;
			// evaluar v1<tolerancia y v2<tolerancia
		}
		
	}
	
	public void leerDatos(String nombreArchivo) throws IOException {
		BufferedReader csvReader = new BufferedReader(new FileReader(nombreArchivo));
		String row;
		ArrayList<String> rowData;
		ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		etiquetas = new ArrayList<String>();
		
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
	
	public String predecir(ArrayList<Double> dato) {
		Double menor = Double.MAX_VALUE;
		int mejorClase = -1;
		for(int i = 0; i < centros.size(); i++){
			ArrayList<Double> centro = centros.get(i);
			double resta = 0;
			for(int j = 0; j < dato.size(); j++){
				resta = Math.pow(dato.get(j) - centro.get(j), 2);
			}
			if(resta < menor) {
				menor = resta;
				mejorClase = i;
			}
			
		}
		return etiquetas.get(mejorClase);
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
	
	private ArrayList<ArrayList<Double>> recalcularCentros(){
		ArrayList<ArrayList<Double>> nuevosCentros = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> distancias = calcularDistancias();
		ArrayList<ArrayList<Double>> gradoPertenencia = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < centros.size(); i++){
			ArrayList<Double> pAuxiliar = new ArrayList<Double>();
			for(int j = 0; j < matrizElementos.size(); j++) {
				double p = calcularP(i,j, distancias);
				pAuxiliar.add(p);
			}
			gradoPertenencia.add(pAuxiliar);
		}
		
		for(int i = 0; i < centros.size(); i++){
			ArrayList<Double> numerador = inicializarACeros(matrizElementos.get(0).size());
			double denominador = 0;
			
			for(int j = 0; j < gradoPertenencia.get(i).size(); j++){
				double valor = Math.pow(gradoPertenencia.get(i).get(j), b);
				denominador += valor;
				
				for(int z = 0; z < matrizElementos.get(j).size(); z++) {
					double valorNumerador = numerador.get(z);
					valorNumerador += valor * matrizElementos.get(j).get(z);
					numerador.set(z, valorNumerador);
				}
			}
			
			for(int j = 0; j < matrizElementos.get(0).size(); j++) {
				double valorNumerador = numerador.get(j);
				numerador.set(j, valorNumerador / denominador);
			}
			
			nuevosCentros.add(numerador);
		}
		
		centrosAnteriores = centros;
		return nuevosCentros;
	}
	
	private ArrayList<Double> inicializarACeros(int size){
		ArrayList<Double> nuevoArray = new ArrayList<Double> ();
		
		for(int i = 0; i < size; i++) {
			nuevoArray.add(0.0);
		}
		
		return nuevoArray;
	}
	
	
	
	private double calcularP(int centro, int elemento, ArrayList<ArrayList<Double>> distancias) {
		double exponente = 1/(b - 1);
		double denominador = 0;
		for(int i = 0; i < centros.size(); i++){
			denominador += Math.pow(1/distancias.get(elemento).get(i), exponente);
		}
		
		return Math.pow(1/distancias.get(elemento).get(centro), exponente) / denominador;
	}

	private ArrayList<ArrayList<Double>> calcularDistancias(){
		ArrayList<ArrayList<Double>> distancias = new ArrayList<ArrayList<Double>> ();
		for(int i = 0; i < matrizElementos.size(); i++) {
			ArrayList<Double> filaDistancias = new ArrayList<Double>();
			for(int j = 0; j < centros.size(); j++){
				double sol = 0;
				ArrayList<Double> elemento = matrizElementos.get(i);
				ArrayList<Double> centro = centros.get(j);
				for(int z = 0; z < elemento.size(); z++) {
					sol += Math.pow(elemento.get(z) - centro.get(z), 2);
				}
				
				filaDistancias.add(sol);
			}
			distancias.add(filaDistancias);
		}
		
		return distancias;
	}
	
	

}
