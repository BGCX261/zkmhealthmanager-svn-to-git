package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Graphics2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelector;

public class RestauracionDesadaptada extends Convencion implements ISelector {

	@Override
	public ISectorDiente select(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX,
			final int initY, final int initXBI, final int initXBS,
			final int initYBI, final int initYBS) {
		return new ISectorDiente() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				SuperficieSectorRecita.repintarSectorSeleccionadoConBorde(
						sectorSeleccionado, centerX, centerY, xTransport,
						yTransport, width_s, heigth_s, width_i, heigth_i,
						initX, initY, initXBI, initXBS, initYBI, initYBS,
						graphics2d, Color.BLUE, Color.RED);
			}

			@Override
			public TypeSector getTypeSector() {
				return TypeSector.RESTAURACION_DESADAPTADA;
			}

			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

	// public ISuperficieTotal select(final int centerX, final int centerY,
	// final int xTransport,
	// final int yTransport, final int width_s, final int heigth_s,
	// final int width_i, final int heigth_i, final int initX, final int initY,
	// final int initXBI, final int initXBS,final int initYBI, final int
	// initYBS) {
	// 
	// return new ISuperficieTotal() {
	// @Override
	// public void onDraw(Graphics2D graphics2d) {
	// graphics2d.setColor(Color.red);
	/* linea 1 */
	// Line2D line2dTD = new Line2D.Double(centerX - xTransport,
	// centerY - yTransport, centerX + xTransport,
	// centerY + yTransport);
	// graphics2d.draw(line2dTD);
	// /* linea 2*/
	// line2dTD = new Line2D.Double(centerX - xTransport,
	// centerY + yTransport, centerX + xTransport,
	// centerY - yTransport);
	// graphics2d.draw(line2dTD);
	//
	// /* line horizontal*/
	// int xi = initXBS;
	// int xf = width_s + initX;
	// Line2D line2dSuperior = new Line2D.Double(xi,
	// centerY, xf,
	// centerY);
	// graphics2d.draw(line2dSuperior);
	//
	// /* creamos punto superior */
	// // renderizamos la imagen
	// RenderingHints rh = new RenderingHints(
	// RenderingHints.KEY_TEXT_ANTIALIASING,
	// RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	// graphics2d.setRenderingHints(rh);
	//
	// int seccionY = (heigth_i / 4);
	// int fontSize = (centerY + yTransport) - (centerY - seccionY);
	// int posX = width_s + initX;
	// int posY = initXBS;
	//
	// Font font = new Font("Arial", Font.BOLD, fontSize);
	// graphics2d.setFont(font);
	// graphics2d.drawString("x", posX, posY);
	// }
	//
	//
	// @Override
	// public TypeSector getTypeSector() {
	// return TypeSector.RESTAURACION_DESADAPTADA;
	// }
	// };
	// }

}
