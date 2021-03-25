package casillas;

import java.util.Comparator;

public class ComparadorCasillas implements Comparator<Casilla>{

	@Override
	public int compare(Casilla c1, Casilla c2) {
		if(c1.getCosteAcumulado() > c2.getCosteAcumulado())
			return 1;
		else if(c1.getCosteAcumulado() < c2.getCosteAcumulado())
			return -1;
		else
			return 0;
	}

}
