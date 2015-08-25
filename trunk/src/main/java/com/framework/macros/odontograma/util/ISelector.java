package com.framework.macros.odontograma.util;

import com.framework.macros.odontograma.DienteMacro.SECTOR;

public interface ISelector {
	public ISectorDiente select(final SECTOR sectorSeleccionado,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX, final int initY,
			int initXBI, int initXBS,int initYBI, int initYBS);
}
