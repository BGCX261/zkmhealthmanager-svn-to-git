package com.framework.macros.graficas.moldG.state;

import java.awt.Color;
import java.awt.Graphics2D;

import com.framework.macros.graficas.moldG.PointerMoldGState;

public class PuntosPointerStatae implements PointerMoldGState {
	public TypePoint typePoint = TypePoint.PUNTO;
	
	private int radio = 5;
	private int border = 3;
	
	private int x;
	private int y;
	
	/**
	 * Este metodo es para dibujo...
	 * */
	public PuntosPointerStatae(Double x, Double y) {
		super();
		this.x = (int)Math.round(x);
		this.y = (int)Math.round(y);
	}
	
	
	public void draw(Graphics2D graphics2d) {
		draw(graphics2d, Color.BLACK); 
	}
	
	public void draw(Graphics2D graphics2d, Color color){
		graphics2d.setColor(color);
		graphics2d.fillOval(x - (radio / 2), y - (radio / 2), radio, radio);
		graphics2d.drawOval(x - (radio / 2) - border + (border / 2), y
				- (radio / 2) - border + (border / 2), radio + border, radio
				+ border);
	}

	/** Este metodo es para agregar un texto adicional */
	public void draw(Graphics2D graphics2d, String info) {
		draw(graphics2d);
		graphics2d.drawString(info, x - (radio / 2) + radio + border, y);
	}

	/* los get and setter para cambiar el tamaño*/
	public int getRadio() {
		return radio;
	}

	public void setRadio(int radio) {
		this.radio = radio;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
