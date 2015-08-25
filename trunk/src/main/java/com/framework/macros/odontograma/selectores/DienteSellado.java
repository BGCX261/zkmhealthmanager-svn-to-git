package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;

public class DienteSellado  extends Convencion implements ISelectorTotal {

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
				// renderizamos la imagen
				RenderingHints rh = new RenderingHints(
			             RenderingHints.KEY_TEXT_ANTIALIASING,
			             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				graphics2d.setRenderingHints(rh);
				/* creamos la imagen */
				graphics2d.setColor(Color.blue);
				Font font = new Font("Arial", Font.BOLD, 40);
				graphics2d.setFont(font);
				int seccionX = (width_i / 4);
				int seccionY = (heigth_i / 4);
				int posX = centerX - xTransport - (seccionX * 2); 
				int posY = centerY + yTransport + (seccionY * 2);
//				int posX = ((width_s + initX) - (centerX + xTransport)) - (initX / 2);
//				int posY = (centerY + yTransport - 1)
//				+ (((heigth_s + initY) - (centerY + yTransport)) - (initY));
				graphics2d.drawString("S", posX, posY);
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.DIENTE_SELLADO;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
