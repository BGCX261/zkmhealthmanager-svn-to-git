package com.framework.macros.odontograma.selectores;
/**
 * @author Luis Miguel Hernandez Perez
 * */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.DienteMacro.TODO;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelector;

@TODO
public class SuperficiePorSellar  extends Convencion implements ISelector {

	@Override
	public ISectorDiente select(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS) {
		return new ISectorDiente() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				// renderizamos la imagen
				RenderingHints rh = new RenderingHints(
			             RenderingHints.KEY_TEXT_ANTIALIASING,
			             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				graphics2d.setRenderingHints(rh);
				/* creamos la imagen */
				
				int seccionX = (width_i / 4);
				int seccionY = (heigth_i / 4);
				int posX = centerX - seccionX;
				int posY = centerY + seccionY;
				int fontSize = (centerY + yTransport) -  (centerY - seccionY);
				
				if(sectorSeleccionado == SECTOR.RIGTH){
					posX = centerX + xTransport + seccionX; 
                }else if(sectorSeleccionado == SECTOR.LEFT){
                	posX = centerX - xTransport - (seccionX * 2) - seccionX; 
                }else if(sectorSeleccionado == SECTOR.TOP){
                	posY = centerY - yTransport - seccionY; 
                }else if(sectorSeleccionado == SECTOR.BOTTOM){
                	posY = centerY + yTransport + (seccionY * 3); 
                }else if(sectorSeleccionado == SECTOR.CENTER){
					// init from center
                }
				graphics2d.setColor(Color.RED);
				Font font = new Font("Arial", Font.BOLD, fontSize);
				graphics2d.setFont(font);
				graphics2d.drawString("S", posX, posY);
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.SUPERFICIE_POR_SELLAR;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
