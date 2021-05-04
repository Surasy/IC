package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import algoritmos.Algoritmo;
import algoritmos.AlgoritmoKMedias;

public class main {

	private final static String RUTADATOS = "recursos/Iris2Clases.txt";
	private final static String RUTAEJEMPLO = "recursos/TestIris04.txt";
	
	public static void main(String[] args) {
		try {
			ArrayList<ArrayList<Double>> datos = leerDatos(RUTADATOS);
			ArrayList<ArrayList<Double>> ejemplo = leerDatos(RUTAEJEMPLO);
			
			Algoritmo algoritmo = new AlgoritmoKMedias(datos, 0.01,2);
			
			algoritmo.run();
			System.out.println(algoritmo.predecir(ejemplo.get(0)));
			
//			for(int i = 0; i < datos.size(); i++) {
//				for(int j = 0; j < datos.get(i).size(); j++) {
//					System.out.print(datos.get(i).get(j) + " ");
//					
//				}
//				System.out.print("\n");
//			}
			
			
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
}
