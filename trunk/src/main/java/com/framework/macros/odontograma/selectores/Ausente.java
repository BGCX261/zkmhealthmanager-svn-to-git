package com.framework.macros.odontograma.selectores;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.DienteMacro.visibleSectores;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;

@visibleSectores(visible = false)
public class Ausente  extends Convencion implements ISelectorTotal {

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
				graphics2d.setColor(Color.RED);
				graphics2d.setStroke(new BasicStroke(3)); 
//				/* linea 1*/
//				Line2D line2dTD = new Line2D.Double(centerX - xTransport,
//		                centerY - yTransport, centerX + xTransport,
//		                centerY + yTransport);
//		        graphics2d.draw(line2dTD);
//				/* linea 2*/
//		        line2dTD = new Line2D.Double(centerX - xTransport,
//	                centerY + yTransport, centerX + xTransport,
//	                centerY - yTransport);
//                graphics2d.draw(line2dTD);
                
                /* line horizontal*/
                int xi = initXBS;
				int xf = width_s + initX;
				 Line2D line2dSuperior = new Line2D.Double(xi,
						 centerY, xf,
						 centerY);
				 graphics2d.draw(line2dSuperior);
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.AUSENTE;
			}
			
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
