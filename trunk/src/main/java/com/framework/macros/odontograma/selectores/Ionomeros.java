package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelector;

public class Ionomeros  extends Convencion implements ISelector {

	@Override
	public ISectorDiente select(final SECTOR sectorSeleccionado, final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS) {
		
		return new  ISectorDiente() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				Line2D line2dDT1 = null;
				Line2D line2dDTY2 = null;
				Line2D line2dDTY3 = null;
				Line2D line2dDTY4 = null;
				Line2D line2dDTY5 = null;
				
				int seccionX = (width_i / 3);
				int seccionY = (heigth_i / 3);
				
				if(sectorSeleccionado == SECTOR.RIGTH){
					int yInicial = centerY - yTransport;
					line2dDT1 = new Line2D.Double(centerX + xTransport,
							yInicial, width_s + initX,
							yInicial);
					line2dDTY2 = new Line2D.Double(centerX + xTransport,
							centerY - seccionY, width_s + initX,
							centerY - seccionY);
					line2dDTY3 = new Line2D.Double(centerX + xTransport,
							centerY, width_s + initX,
							centerY);
					line2dDTY4 = new Line2D.Double(centerX + xTransport,
							centerY + seccionY, width_s + initX,
							centerY + seccionY);
					line2dDTY5 = new Line2D.Double(centerX + xTransport,
							centerY + yTransport, width_s + initX,
							centerY + yTransport);
					
                }else if(sectorSeleccionado == SECTOR.LEFT){
					int yInicial = centerY - yTransport;
					line2dDT1 = new Line2D.Double(initXBS,
							yInicial, centerX - xTransport,
							yInicial);
					line2dDTY2 = new Line2D.Double(initXBS,
							centerY - seccionY, centerX - xTransport,
							centerY - seccionY);
					line2dDTY3 = new Line2D.Double(initXBS,
							centerY, centerX - xTransport,
							centerY);
					line2dDTY4 = new Line2D.Double(initXBS,
							centerY + seccionY, centerX - xTransport,
							centerY + seccionY);
					line2dDTY5 = new Line2D.Double(initXBS,
							centerY + yTransport, centerX - xTransport,
							centerY + yTransport);
                }else if(sectorSeleccionado == SECTOR.TOP){
					int xInicial = centerX - xTransport; 
					line2dDT1 = new Line2D.Double(xInicial,
							initYBS, xInicial,
							centerY - yTransport);
					line2dDTY2 = new Line2D.Double(centerX - seccionX,
							initYBS, centerX - seccionX,
							centerY - yTransport);
					line2dDTY3 = new Line2D.Double(centerX,
							initYBS, centerX,
							centerY - yTransport);
					line2dDTY4 = new Line2D.Double(centerX + seccionX,
							initYBS, centerX + seccionX,
							centerY - yTransport);
					line2dDTY5 = new Line2D.Double(centerX + xTransport,
							initYBS, centerX + xTransport,
							centerY - yTransport);
                }else if(sectorSeleccionado == SECTOR.BOTTOM){
                	int xInicial = centerX - xTransport; 
					line2dDT1 = new Line2D.Double(xInicial,
							centerY + yTransport, xInicial,
							heigth_s + initY);
					line2dDTY2 = new Line2D.Double(centerX - seccionX,
							centerY + yTransport, centerX - seccionX,
							heigth_s + initY);
					line2dDTY3 = new Line2D.Double(centerX,
							centerY + yTransport, centerX,
							heigth_s + initY);
					line2dDTY4 = new Line2D.Double(centerX + seccionX,
							centerY + yTransport, centerX + seccionX,
							heigth_s + initY);
					line2dDTY5 = new Line2D.Double(centerX + xTransport,
							centerY + yTransport, centerX + xTransport,
							heigth_s + initY);
                }else if(sectorSeleccionado == SECTOR.CENTER){
                	int yInicial = centerY - yTransport;
					line2dDT1 = new Line2D.Double(centerX - xTransport + 1,
							yInicial, centerX + xTransport - 1,
							yInicial);
					line2dDTY2 = new Line2D.Double(centerX - xTransport + 1,
							centerY - seccionY, centerX + xTransport - 1,
							centerY - seccionY);
					line2dDTY3 = new Line2D.Double(centerX - xTransport + 1,
							centerY, centerX + xTransport - 1,
							centerY);
					line2dDTY4 = new Line2D.Double(centerX - xTransport + 1,
							centerY + seccionY, centerX + xTransport - 1,
							centerY + seccionY);
					line2dDTY5 = new Line2D.Double(centerX - xTransport + 1,
							centerY + yTransport, centerX + xTransport - 1,
							centerY + yTransport);
                }
				if(line2dDT1 != null){
					graphics2d.setColor(Color.GREEN);
					graphics2d.draw(line2dDT1);
					graphics2d.draw(line2dDTY2);
					graphics2d.draw(line2dDTY3);
					graphics2d.draw(line2dDTY4);
					graphics2d.draw(line2dDTY5);
				}
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.IONOMEROS;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
