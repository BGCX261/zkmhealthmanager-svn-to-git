package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelector;

public class SuperficieManchada  extends Convencion implements ISelector {

	@Override
	public ISectorDiente select(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS) {
		
		return new ISectorDiente() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				
				int x3Points[] = {};
		        int y3Points[] = {};
		        if (sectorSeleccionado == SECTOR.RIGTH) {
		            int xInit = centerX + 1;
		            int xEnd = width_s + initX;


		            int yInit = centerY;
		            int y3 = initY;
		            int yEnd = heigth_s + initY;

		            int x3PointsT[] = {xInit, xInit, xEnd, xEnd, xInit};
		            int y3PointsT[] = {yInit, yInit, y3, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;

		        }
		        if (sectorSeleccionado == SECTOR.LEFT) {
		            int xInit = initXBS + 1;
		            int xEnd = centerX;


		            int yEnd = centerY;
		            int y2 = initY + 1;
		            int y3 = centerY;
		            int yInit = heigth_s + initY - 1;

		            int x3PointsT[] = {xInit, xInit, xEnd, xEnd, xInit};
		            int y3PointsT[] = {yInit, y2, y3, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		        }

		        if (sectorSeleccionado == SECTOR.BOTTOM) {
		            int xInit = initXBS + 1;
		            int xEnd = centerX;
		            int x2 = width_s + initX;
		            int x3 = centerX;


		            int yEnd = centerY;
		             int yInit = heigth_s + initY;

		            int x3PointsT[] = {xInit, x2, x3, xEnd, xInit};
		            int y3PointsT[] = {yInit, yInit, yEnd, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		        }
		        
		        if (sectorSeleccionado == SECTOR.TOP) {
		            int xInit = initXBS + 1;
		            int xEnd = centerX;
		            int x2 = width_s + initX;
		            int x3 = centerX;


		            int yEnd = centerY;
		             int yInit = initY;

		            int x3PointsT[] = {xInit, x2, x3, xEnd, xInit};
		            int y3PointsT[] = {yInit, yInit, yEnd, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		        }

		        if (sectorSeleccionado != SECTOR.NONE && sectorSeleccionado != SECTOR.CENTER) {
		            GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
		                    x3Points.length);
		            filledPolygon.moveTo(x3Points[0], y3Points[0]);

		            for (int index = 1; index < x3Points.length; index++) {
		                filledPolygon.lineTo(x3Points[index], y3Points[index]);
		            };
		            filledPolygon.closePath();

		            graphics2d.setColor(Color.red);
		            graphics2d.fill(filledPolygon);
		        }	
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.SUPERFICIE_MANCHADA;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
