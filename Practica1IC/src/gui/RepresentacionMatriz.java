package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import casillas.Casilla;
import casillas.CasillaAltura;
import casillas.CasillaBasica;
import casillas.CasillaFin;
import casillas.CasillaInicio;
import casillas.CasillaPenalizacion;
import casillas.CasillaPoint;
import casillas.CasillaProhibida;
import matriz.Matriz;

public class RepresentacionMatriz extends JPanel{
	private Matriz m;
	private int radioButton;
	private int valorAsociado;
	private int contadorPoint;
	private RepresentacionCasilla[][] paneles;
	
	private final int TAMCASILLA = 30;
	public RepresentacionMatriz(){
//		this.setPreferredSize(new Dimension(700, 700));
		
	}
	
	
	public void setMatriz(Matriz m) {
		this.contadorPoint = 0;
		//this.removeAll();
		//this.updateUI();
		this.paneles = new RepresentacionCasilla[m.getAlto()][m.getAncho()];
		this.m = m;
		this.setLayout(new GridLayout(m.getAlto(),m.getAncho(), 5, 5));

		dibujarMatriz();
	}
	
	public void dibujarMatriz() {
		this.removeAll();
		this.updateUI();
		
		for(int i = 0; i < m.getAlto(); i++){
			for(int j = 0; j < m.getAncho(); j++){
				//String archivo = m.getCasilla(i,j).dibujarse();
				RepresentacionCasilla p = new RepresentacionCasilla(i, j);
				paneles[i][j] = p;
				p.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						int i = p.getI();
						int j = p.getJ();
						setCasilla(i,j);
						dibujarMatriz();
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				p.setPreferredSize(new Dimension(TAMCASILLA, TAMCASILLA));
				p.setBackground(m.getCasilla(i, j).print());
				this.add(p);
			}
		}
		
		this.revalidate();

	}
	
	private void setCasilla(int i, int j){
		Casilla c;
		
		switch(radioButton){
			case 1: 
				Casilla ini = m.getInicio();
				if(ini != null) {
					int iIni = ini.getI();
					int jIni = ini.getJ();
					m.setCasilla(iIni, jIni, new CasillaBasica(iIni, jIni));
				}
				c = new CasillaInicio(i, j);
			break;
			case 2: 
				Casilla fin = m.getFin();
				if(fin != null) {
					int iFin = fin.getI();
					int jFin = fin.getJ();
					m.setCasilla(iFin, jFin, new CasillaBasica(iFin, jFin));
				}
				c = new CasillaFin(i, j);
			break;
			case 3: 
				c = new CasillaBasica(i, j);
			break;
			case 4: 
				c= new CasillaProhibida(i, j);
			break;
			case 5: 
				c= new CasillaAltura(i, j, valorAsociado);
			break;
			case 6: 
				c= new CasillaPenalizacion(i, j, valorAsociado);
			break;
			case 7: 
				c= new CasillaPoint(i, j, contadorPoint);
				contadorPoint++;
			break;
			default:
				c = new CasillaBasica(i, j);
			break;
			
		}
		
		m.setCasilla(i, j, c);
	}
	

	public void setRadioButtonSeleccionado(int tipo, int atributo) {
		this.radioButton = tipo;
		this.valorAsociado = atributo;
	}
	
	public void setAtributoSeleccionado(int atributo) {
		this.valorAsociado = atributo;
	}


	public void dibujarCamino(ArrayList<int[]> recorrido) {
		
		for(int[] nodo: recorrido){
			paneles[nodo[0]][nodo[1]].setBackground(Color.yellow);
		}
		
	}
	

}
