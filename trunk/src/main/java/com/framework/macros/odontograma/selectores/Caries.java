package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Graphics2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelector;

public class Caries  extends Convencion implements ISelector{

	@Override
	public ISectorDiente select(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			final int initXBI, final int initXBS,final int initYBI, final int initYBS) {
		return new ISectorDiente() {
			@Override
			public void onDraw(Graphics2D graphics2d) {
				SuperficieSectorRecita.repintarSectorSeleccionado(
						sectorSeleccionado, centerX, centerY, xTransport,
						yTransport, width_s, heigth_s, width_i, heigth_i,
						initX, initY, initXBI, initXBS, initYBI, initYBS,
						graphics2d, Color.RED);
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.CARIES_OPTURACION;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
