package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Graphics2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelector;

public class SuperficieEnAmalgama  extends Convencion implements ISelector {

	@Override
	public ISectorDiente select(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS) {
		
		return new ISectorDiente() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				
				// Este es el codigo de la anterior superficien en amalgama.
				// No lo borre por puede que se utilice, después.
				/*
				Line2D line2dDT = null;
				if(sectorSeleccionado == SECTOR.RIGTH){
					line2dDT = new Line2D.Double(centerX + xTransport + 1,
			                centerY, width_s + initX,
			                centerY);
                }else if(sectorSeleccionado == SECTOR.LEFT){
					line2dDT = new Line2D.Double(initXBS + 1,
			                centerY, centerX - xTransport - 1,
			                centerY);
                }else if(sectorSeleccionado == SECTOR.TOP){
					line2dDT = new Line2D.Double(centerX,
			                initYBS, centerX,
			                initYBI);
                }else if(sectorSeleccionado == SECTOR.BOTTOM){
					line2dDT = new Line2D.Double(centerX,
			                centerY + yTransport + 1, centerX,
			                heigth_s + initY - 1);
                }else if(sectorSeleccionado == SECTOR.CENTER){
					line2dDT = new Line2D.Double(centerX - xTransport + 1,
			                centerY, centerX + xTransport - 1,
			                centerY);
                }
				if(line2dDT != null){
					graphics2d.setColor(Color.blue);
					graphics2d.draw(line2dDT);
				}*/
				 
				SuperficieSectorRecita.repintarSectorSeleccionado(
						sectorSeleccionado, centerX, centerY, xTransport,
						yTransport, width_s, heigth_s, width_i, heigth_i,
						initX, initY, initXBI, initXBS, initYBI, initYBS,
						graphics2d, Color.BLUE);
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.SUPERFICIE_AMALGAMA;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
