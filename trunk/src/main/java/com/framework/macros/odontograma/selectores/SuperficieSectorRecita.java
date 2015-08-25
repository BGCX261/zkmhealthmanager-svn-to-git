package com.framework.macros.odontograma.selectores;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelector;


public class SuperficieSectorRecita  extends Convencion implements ISelector{
 
	@Override 
	public ISectorDiente select(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS) {
		return new ISectorDiente() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				repintarSectorSeleccionado(sectorSeleccionado, centerX,
						centerY, xTransport, yTransport, width_s, heigth_s,
						width_i, heigth_i, initX, initY, initXBI, initXBS,
						initYBI, initYBS, graphics2d, Color.GREEN);	
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.SUPERFICIE_RECINA;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

	
	
	/**
	 * En este metodo se va ha centralizar el funcionamiento de la superficie en recina 
	 * ya que se repite en otros tipos de convenciones.
	 * @author Luis Miguel
	 * */
	public static void repintarSectorSeleccionado(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS,
			Graphics2D graphics2d, Color color){
			repintarSectorSeleccionadoConBorde(sectorSeleccionado, centerX,
					centerY, xTransport, yTransport, width_s, heigth_s, width_i,
					heigth_i, initX, initY, initXBI, initXBS, initYBI, initYBS,
					graphics2d, color, null);			
	}
	
	
	/**
	 * En este metodo se va ha centralizar el funcionamiento de la superficie en recina 
	 * ya que se repite en otros tipos de convenciones.
	 * @author Luis Miguel
	 * */
	public static void repintarSectorSeleccionadoConBorde(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS,
			Graphics2D graphics2d, Color color, Color colorBorde){
		
		RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics2d.setRenderingHints(rh);
		graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		        // realizamos repintado
				int x3Points[] = {};
		        int y3Points[] = {};
		        if (sectorSeleccionado == SECTOR.RIGTH) {
		            int xInit = centerX + xTransport + 1;
		            int xEnd = width_s + initX;

		            int yInit = centerY + yTransport;
		            int y2 = centerY - yTransport;
		            int y3 = initY;
		            int yEnd = heigth_s + initY;

		            int x3PointsT[] = {xInit, xInit, xEnd, xEnd, xInit};
		            int y3PointsT[] = {yInit, y2, y3, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		        }
		        
		        if (sectorSeleccionado == SECTOR.LEFT) {
		            int xInit = initXBS + 1;
		            int xEnd = centerX - xTransport;


		            int yEnd = centerY + yTransport;
		            int y2 = initY + 1;
		            int y3 = centerY - yTransport;
		            int yInit = heigth_s + initY - 1;

		            int x3PointsT[] = {xInit, xInit, xEnd, xEnd, xInit};
		            int y3PointsT[] = {yInit, y2, y3, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		        }

		        if (sectorSeleccionado == SECTOR.TOP) {
		            int xInit = initXBS + 1;
		            int xEnd = centerX - xTransport;
		            int x2 = width_s + initX;
		            int x3 = centerX  + xTransport;


		            int yEnd = centerY - yTransport;
		             int yInit = initY;

		            int x3PointsT[] = {xInit, x2, x3, xEnd, xInit};
		            int y3PointsT[] = {yInit, yInit, yEnd, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		        }

		        if (sectorSeleccionado == SECTOR.BOTTOM) {
		            int xInit = initXBS + 1;
		            int xEnd = centerX - xTransport;
		            int x2 = width_s + initX;
		            int x3 = centerX  + xTransport;


		            int yEnd = centerY + yTransport + 1;
		             int yInit = heigth_s + initY;

		            int x3PointsT[] = {xInit, x2, x3, xEnd, xInit};
		            int y3PointsT[] = {yInit, yInit, yEnd, yEnd, yInit};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		        }

               // modificar los puntos para que queden las lineas bien
		        if (sectorSeleccionado == SECTOR.CENTER) {
		        	int x1 = centerX - xTransport + 1;
		        	int x2 = centerX + xTransport;
		        	
		        	int y1 = centerY + yTransport;
		        	int y2 = centerY - yTransport + 1;
		        	
		        	int x3PointsT[] = {x1, x2, x2, x1, x1};
		            int y3PointsT[] = {y1, y1, y2, y2, y1};
		            x3Points = x3PointsT;
		            y3Points = y3PointsT;
		            
		        	// Esto era un repintado anterior
//		            graphics2d.setColor(color);
//		            Rectangle2D borderInferiorFondo = new Rectangle2D.Double(
//		            		initXBI + 1, initYBI + 1,
//		                    width_i - 1, heigth_i - 1);
//		            graphics2d.fill(borderInferiorFondo);
		        }

		        if (sectorSeleccionado != SECTOR.NONE) {
		            GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
		                    x3Points.length);
		            filledPolygon.moveTo(x3Points[0], y3Points[0]);

		            for (int index = 1; index < x3Points.length; index++) {
		                filledPolygon.lineTo(x3Points[index], y3Points[index]);
		            };
		            filledPolygon.closePath();

		            graphics2d.setColor(color);
		            graphics2d.fill(filledPolygon);
		            
		            if(colorBorde != null){
		            	graphics2d.setStroke(new BasicStroke(2)); 
		            	graphics2d.setColor(colorBorde);
		            	graphics2d.draw(filledPolygon);
		            	graphics2d.setStroke(new BasicStroke(1));
		            }
		        }
	}

}
