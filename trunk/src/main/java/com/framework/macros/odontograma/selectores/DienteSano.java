package com.framework.macros.odontograma.selectores;

import java.awt.Graphics2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.Cleaner;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;

@Cleaner 
public class DienteSano  extends Convencion implements ISelectorTotal {

	@Override
	public ISectorDiente select(SECTOR sectorSeleccionado, int centerX,
			int centerY, int xTransport, int yTransport, int width_s,
			int heigth_s, int width_i, int heigth_i, int initX, int initY,
			int initXBI, int initXBS, int initYBI, int initYBS) {
		
		return null;
	}

	@Override
	public ISuperficieTotal select(int centerX, int centerY, int xTransport,
			int yTransport, int width_s, int heigth_s, int width_i,
			int heigth_i, int initX, int initY, int initXBI, int initXBS,
			int initYBI, int initYBS) {
		
		return new ISuperficieTotal() {
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.NONE;
			}
			
			@Override
			public void onDraw(Graphics2D graphics2d) {
				
				
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

	

}
