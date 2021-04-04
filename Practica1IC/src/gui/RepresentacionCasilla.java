package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RepresentacionCasilla extends JPanel {
	private int i;
	private int j;
	private JLabel letra;
	public RepresentacionCasilla(int i, int j){
		this.i = i;
		this.j = j;
		this.letra = new JLabel();
		this.add(letra);
		
	}
	
	public void setLetra(String texto) {
		this.letra.setText(texto); 
	}
	
	public int getI(){
		return i;
	}
	
	public int getJ(){
		return j;
	}
}
