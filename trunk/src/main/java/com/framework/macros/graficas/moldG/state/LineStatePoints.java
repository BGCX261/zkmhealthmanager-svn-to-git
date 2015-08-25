package com.framework.macros.graficas.moldG.state;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.framework.macros.graficas.moldG.LineMoldGState;
import com.framework.macros.graficas.moldG.PointerMoldGState;

/**
 * Esta es una linea, para unir los puntos de las graficas.
 * @author Luis Miguel Hernandez Perez
 * */
public class LineStatePoints implements LineMoldGState {
	/* puntos enteros */
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	
	public LineStatePoints(PointerMoldGState pointerMoldGStateInit,
			PointerMoldGState pointerMoldGStateEnd) {
		super();
		x1 = pointerMoldGStateInit.getX();
		y1 = pointerMoldGStateInit.getY();
		x2 = pointerMoldGStateEnd.getX();
		y2 = pointerMoldGStateEnd.getY();
	} 

	public LineStatePoints(Double x1, Double y1, Double x2, Double y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void draw(Graphics2D graphics2d) {
		graphics2d.setColor(Color.BLACK);
		graphics2d.draw(new Line2D.Double(x1, y1,
				x2, y2));
	}
	
	public void draw(Graphics2D graphics2d, int width) {
		graphics2d.setStroke(new BasicStroke(width));
		draw(graphics2d);
	}
}
