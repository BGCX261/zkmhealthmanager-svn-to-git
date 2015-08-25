package com.framework.macros.odontograma.selectores;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.Estado;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.DienteMacro.TODO;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;
@TODO(estado = Estado.DESADAPTADA)
public class CoronaBuena  extends Convencion implements ISelectorTotal {
 
	private Estado estado;
	public CoronaBuena(Estado estado) {
		this.estado = estado;
	}
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
				int xMove = width_i / 2;
				int yMove = heigth_i / 2;
				int x1 = centerX - xTransport - xMove;
				int x2 = width_i + (xMove * 2);
				int y1 = centerY - yTransport - yMove;
				int y2 = heigth_i + (yMove * 2);
				graphics2d.setColor(Color.BLUE);
				Rectangle2D rectangle2d = new Rectangle2D.Double(
		                x1, y1, x2,
		                y2);
				if(estado == Estado.DESADAPTADA){
					graphics2d.setColor(Color.RED);
					graphics2d.setStroke(new BasicStroke(3));
				}
				graphics2d.draw(rectangle2d);
			}
			
			@Override
			public TypeSector getTypeSector() {
				if(estado == Estado.DESADAPTADA)
					return TypeSector.CORONA_DESADAPTADA;
				else
				    return TypeSector.CORONABUENA;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
