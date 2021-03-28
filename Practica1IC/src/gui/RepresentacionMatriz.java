package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import matriz.Matriz;

public class RepresentacionMatriz extends JPanel{
	Matriz m;
	
	public RepresentacionMatriz (){
//		this.setPreferredSize(new Dimension(700, 700));
		
	}
	
	
	public void setMatriz(Matriz m) {
		this.removeAll();
		this.updateUI();
		this.m = m;
		this.setLayout(new GridLayout(m.getAlto(),m.getAncho(), 5, 5));

		dibujarMatriz();
	}
	
	public void dibujarMatriz() {
		
		for(int i = 0; i < m.getAlto(); i++){
			for(int j = 0; j < m.getAncho(); j++){
				//String archivo = m.getCasilla(i,j).dibujarse();
				JPanel p = new JPanel();
				p.setPreferredSize(new Dimension(20,20));
				p.setBackground(Color.red);
				this.add(p);
			}
		}
		
		this.revalidate();

	}
	
}
