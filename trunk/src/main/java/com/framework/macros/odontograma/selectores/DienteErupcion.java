package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;

public class DienteErupcion  extends Convencion implements ISelectorTotal {

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
				
				int xi = initXBS;
				int xf = width_s + initX;
				graphics2d.setColor(Color.BLUE);
				/* linea superior */
				int y1 = centerY - (heigth_i / 4);
				 Line2D line2dSuperior = new Line2D.Double(xi,
						 y1, xf,
						 y1);
				 graphics2d.draw(line2dSuperior);
				/* linea inferior*/
				int y2 = centerY + (heigth_i / 4);
				 Line2D line2dInferior = new Line2D.Double(xi,
						 y2, xf,
						 y2);
				 graphics2d.draw(line2dInferior);
			}
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.DIENTEERUPCION;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
