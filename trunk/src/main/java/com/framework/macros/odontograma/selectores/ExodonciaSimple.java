package com.framework.macros.odontograma.selectores;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;

public class ExodonciaSimple  extends Convencion implements ISelectorTotal {

	@Override
	public ISectorDiente select(SECTOR sectorSeleccionado, int centerX,
			int centerY, int xTransport, int yTransport, int width_s,
			int heigth_s, int width_i, int heigth_i, int initX, int initY,
			int initXBI, int initXBS, int initYBI, int initYBS) {
		
		return null;
	}

	@Override
	public ISuperficieTotal select(final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS) {
		
		return new ISuperficieTotal() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				
//				RenderingHints rh = new RenderingHints(
//			             RenderingHints.KEY_STROKE_CONTROL,
//			             RenderingHints.VALUE_STROKE_NORMALIZE);
//				graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//				graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//				graphics2d.setRenderingHints(rh);
				graphics2d.setColor(Color.RED);
				graphics2d.setStroke(new BasicStroke(3)); 
				/* linea 1*/
				// Estas son para las lineas de solo en centro
//				Line2D line2dTD = new Line2D.Double(centerX - xTransport,
//		                centerY - yTransport, centerX + xTransport,
//		                centerY + yTransport);
//		        graphics2d.draw(line2dTD);n
//				/* linea 2*/
//		        line2dTD = new Line2D.Double(centerX - xTransport,
//	                centerY + yTransport, centerX + xTransport,
//	                centerY - yTransport);
//                graphics2d.draw(line2dTD);
				
				
				double sepracion = 2D;
				
				Line2D line2dTD = new Line2D.Double(initXBS + sepracion,
                initYBS + sepracion, (width_s + initX) - sepracion,
                (heigth_s + initY) - sepracion);
		        graphics2d.draw(line2dTD);
				/* linea 2*/
		        line2dTD = new Line2D.Double(initXBS + sepracion,
		            (heigth_s + initY) - sepracion, (width_s + initX) - sepracion,
		            initYBS + sepracion);
		        graphics2d.draw(line2dTD);
			}
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.EXODONCIA_SIMPLE;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
