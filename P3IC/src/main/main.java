package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import algoritmos.Algoritmo;
import algoritmos.AlgoritmoBayes;
import algoritmos.AlgoritmoKMedias;
import algoritmos.AlgoritmoLloyd;

public class main {

	private final static String RUTADATOS = "recursos/Iris2Clases.txt";
	private final static String RUTAEJEMPLO1 = "recursos/TestIris01.txt";
	private final static String RUTAEJEMPLO2 = "recursos/TestIris02.txt";
	private final static String RUTAEJEMPLO3 = "recursos/TestIris03.txt";
	private final static String RUTAEJEMPLO4 = "recursos/TestIris04.txt";
	
	public static void main(String[] args) {
		try {

//			ArrayList<ArrayList<Double>> ejemplo = leerDatos(RUTAEJEMPLO);
			
			
			ArrayList<ArrayList<Double>> ejemplo1 = leerDatos(RUTAEJEMPLO1);
			ArrayList<ArrayList<Double>> ejemplo2 = leerDatos(RUTAEJEMPLO2);
			ArrayList<ArrayList<Double>> ejemplo3 = leerDatos(RUTAEJEMPLO3);
			ArrayList<ArrayList<Double>> ejemplo4 = leerDatos(RUTAEJEMPLO4);
			
			Algoritmo algoritmoKMedias = new AlgoritmoKMedias(0.01 ,2);
			Algoritmo algoritmoLloyd = new AlgoritmoLloyd(Math.pow(10, -10), 0.1, 10);
			Algoritmo algoritmoBayes = new AlgoritmoBayes();
			
			algoritmoKMedias.leerDatos(RUTADATOS);
			algoritmoKMedias.run();
			System.out.println("-----ALGORITMO K MEDIAS-----");
			System.out.println("Ejemplo 1: " + algoritmoKMedias.predecir(ejemplo1.get(0)));
			System.out.println("Ejemplo 2: " + algoritmoKMedias.predecir(ejemplo2.get(0)));
			System.out.println("Ejemplo 3: " + algoritmoKMedias.predecir(ejemplo3.get(0)));
			System.out.println("Ejemplo 4: " + algoritmoKMedias.predecir(ejemplo4.get(0)));
			
			System.out.println("\n-----ALGORITMO LLOYD-----");
			algoritmoLloyd.leerDatos(RUTADATOS);
			algoritmoLloyd.run();
			System.out.println("Ejemplo 1: " + algoritmoLloyd.predecir(ejemplo1.get(0)));
			System.out.println("Ejemplo 2: " + algoritmoLloyd.predecir(ejemplo2.get(0)));
			System.out.println("Ejemplo 3: " + algoritmoLloyd.predecir(ejemplo3.get(0)));
			System.out.println("Ejemplo 4: " + algoritmoLloyd.predecir(ejemplo4.get(0)));
			
			System.out.println("\n-----ALGORITMO BAYES-----");
			algoritmoBayes.leerDatos(RUTADATOS);
			algoritmoBayes.run();
			System.out.println("Ejemplo 1: " + algoritmoBayes.predecir(ejemplo1.get(0)));
			System.out.println("Ejemplo 2: " + algoritmoBayes.predecir(ejemplo2.get(0)));
			System.out.println("Ejemplo 3: " + algoritmoBayes.predecir(ejemplo3.get(0)));
			System.out.println("Ejemplo 4: " + algoritmoBayes.predecir(ejemplo4.get(0)));
			

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
	private static ArrayList<ArrayList<Double>> leerDatos(String nombreArchivo) throws IOException {
		BufferedReader csvReader = new BufferedReader(new FileReader(nombreArchivo));
		String row;
		ArrayList<String> rowData;
		ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		
		while ((row = csvReader.readLine()) != null) {
			rowData = new ArrayList<String>();
			Collections.addAll(rowData, row.split(","));
			
			ArrayList<Double> rowDataDouble = new ArrayList<Double>();
			
			for(int i = 0; i < rowData.size() - 1; i++) {
				rowDataDouble.add(Double.parseDouble(rowData.get(i)));

			}
			
		    data.add(rowDataDouble);
		}
		
		csvReader.close();
		return data;

	}
//	
//	
//	
//	private static HashMap<Integer, ArrayList<ArrayList<Double>>> leerDatosBayes(String nombreArchivo) throws IOException {
//		BufferedReader csvReader = new BufferedReader(new FileReader(nombreArchivo));
//		String row;
//		ArrayList<String> rowData;
//		HashMap<String, Integer> clasesUsadas = new HashMap<String, Integer> ();
//		int claseSiguiente = 0;
//		HashMap<Integer, ArrayList<ArrayList<Double>>> sol = new HashMap<Integer, ArrayList<ArrayList<Double>>> ();
//		
//		while ((row = csvReader.readLine()) != null) {
//			rowData = new ArrayList<String>();
//			Collections.addAll(rowData, row.split(","));
//			
//			ArrayList<Double> rowDataDouble = new ArrayList<Double>();
//			
//			for(int i = 0; i < rowData.size() - 1; i++) {
//				rowDataDouble.add(Double.parseDouble(rowData.get(i)));
//
//			}
//			
//			String claseActual = rowData.get(rowData.size() - 1);
//			// si el diccionario no tiene esa entrada, inicializamos
//			if(!clasesUsadas.containsKey(claseActual)) {
//				clasesUsadas.put(claseActual, claseSiguiente);
//				sol.put(claseSiguiente, new ArrayList<ArrayList<Double>>());
//				claseSiguiente++;
//			}
//			
//			ArrayList<ArrayList<Double>> data = sol.get(clasesUsadas.get(claseActual));
//		    data.add(rowDataDouble);
//		    sol.put(clasesUsadas.get(claseActual), data);
//		}
//		
//		csvReader.close();
//		return sol;
//
//	}
}
