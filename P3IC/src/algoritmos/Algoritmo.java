package algoritmos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Algoritmo {
	public abstract void run();
	public abstract String predecir(ArrayList<Double> dato);
	public abstract void leerDatos(String fichero) throws IOException;
}
