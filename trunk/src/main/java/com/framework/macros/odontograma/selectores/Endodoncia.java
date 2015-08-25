package com.framework.macros.odontograma.selectores;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.Estado;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;

public class Endodoncia  extends Convencion implements ISelectorTotal {
	
	private Estado estado;
	public Endodoncia(Estado estado) {
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
				int x1 = initXBS;
				int x2 = centerX;
				int x3 = width_s + initX;
				int y1 = initYBS;
				int y2 = initYBS - heigth_i / 2;
				
				int xPoints[] = {x1, x2, x3, x1};
				int yPoints[] = {y1, y2, y1, y1};
				
				/* graficamos */
				GeneralPath filledPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
						xPoints.length);
	            filledPolygon.moveTo(xPoints[0], yPoints[0]);

	            for (int index = 1; index < xPoints.length; index++) {
	                filledPolygon.lineTo(xPoints[index], yPoints[index]);
	            };
	            filledPolygon.closePath();

	            if(estado == Estado.RELLENADO){
	               graphics2d.setColor(Color.BLUE);
	               graphics2d.fill(filledPolygon);
	            }else{
	               graphics2d.setColor(Color.RED);
	               graphics2d.draw(filledPolygon);
	            }
			}
			@Override
			public TypeSector getTypeSector() {
				if(estado == Estado.POR_RELLNAR)
					return TypeSector.ENDODONCIA_POR_RELLENAR;
				else
			    	return TypeSector.ENDODONCIA;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
