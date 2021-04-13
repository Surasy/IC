package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import id3.Algoritmo;

public class main {

	private final static String RUTAATRIBUTOS = "recursos/AtributosJuego.txt";
	private final static String RUTADATOS = "recursos/Juego.txt";
	
	public static void main(String[] args) {
		try {
			ArrayList<String> atributos = leerAtributos(RUTAATRIBUTOS);
			ArrayList<ArrayList<String>> datos = leerDatos(RUTADATOS);
			
			Algoritmo a = new Algoritmo(atributos, datos);
			
			for(int i = 0; i < datos.size(); i++) {
				for(int j = 0; j < datos.get(i).size(); j++) {
					System.out.print(datos.get(i).get(j) + " ");
					
				}
				System.out.print("\n");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	private static ArrayList<String> leerAtributos(String nombreArchivo) throws IOException {
		BufferedReader csvReader = new BufferedReader(new FileReader(nombreArchivo));
		String row;
		ArrayList<String> data = new ArrayList<String>();
		while ((row = csvReader.readLine()) != null) {
			Collections.addAll(data, row.split(","));
		}
		csvReader.close();
		return data;
	}
	
	private static ArrayList<ArrayList<String>> leerDatos(String nombreArchivo) throws IOException {
		BufferedReader csvReader = new BufferedReader(new FileReader(nombreArchivo));
		String row;
		ArrayList<String> rowData;
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		
		while ((row = csvReader.readLine()) != null) {
			rowData = new ArrayList<String>();
			Collections.addAll(rowData, row.split(","));
		    data.add(rowData);
		}
		
		csvReader.close();
		return data;
	}
}
