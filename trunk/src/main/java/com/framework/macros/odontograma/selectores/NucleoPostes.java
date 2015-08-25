package com.framework.macros.odontograma.selectores;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;

public class NucleoPostes  extends Convencion implements ISelectorTotal {

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
				RenderingHints rh = new RenderingHints(
			             RenderingHints.KEY_STROKE_CONTROL,
			             RenderingHints.VALUE_STROKE_NORMALIZE);
				graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				graphics2d.setRenderingHints(rh);
				
				
				int xMove = width_i / 2;
				// calculamos el eje de las x
				int x1 = centerX - xTransport - xMove;
				int x2 = centerX;
				int x3 = centerX + xTransport + xMove;
				
				// calculamos el eje de las y
				int y1 = heigth_s + initY;
				int y2 = initYBS;
				
				/* creamo linea 1*/
				 graphics2d.setColor(Color.BLUE);
				 graphics2d.setStroke(new BasicStroke(3)); 
				 Line2D line2dSuperior = new Line2D.Double(x1,
						 y1, x2,
						 y2);
				 graphics2d.draw(line2dSuperior);
				 // creamo la segunda linea
				 Line2D line2d2 = new Line2D.Double(x2,
						 y2, x3,
						 y1);
				 graphics2d.draw(line2d2);
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.NUCLEO_POSTES;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
