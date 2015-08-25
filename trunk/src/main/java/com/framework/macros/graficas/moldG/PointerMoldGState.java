package com.framework.macros.graficas.moldG;

import java.awt.Graphics2D;

public interface PointerMoldGState {
	public static enum TypePoint {PUNTO, SECTOR};
	
	void draw(Graphics2D graphics2d);
	double getX();
	double getY();
}
