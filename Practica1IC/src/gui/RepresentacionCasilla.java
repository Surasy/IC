package gui;

import javax.swing.JPanel;

public class RepresentacionCasilla extends JPanel {
	private int i;
	private int j;
	
	public RepresentacionCasilla(int i, int j){
		this.i = i;
		this.j = j;
	}
	
	
	public int getI(){
		return i;
	}
	
	public int getJ(){
		return j;
	}
}
