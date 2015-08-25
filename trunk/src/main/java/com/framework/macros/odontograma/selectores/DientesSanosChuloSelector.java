package com.framework.macros.odontograma.selectores;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.framework.constantes.IConstantes;
import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.DienteMacro.visibleSectores;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;
import com.framework.notificaciones.Notificaciones;
@visibleSectores(visible = false)
public class DientesSanosChuloSelector  extends Convencion implements ISelectorTotal {

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
			public TypeSector getTypeSector() {
				return TypeSector.DIENTES_SANOS_CHULO;
			}
			
			@Override
			public void onDraw(Graphics2D graphics2d) {
				try {
					int xMove = width_i / 2;
					int yMove = heigth_i / 2;
					int x1 = centerX - xTransport - xMove;
					int x2 = width_i + (xMove * 2);
					int y1 = centerY - yTransport - yMove;
					int y2 = heigth_i + (yMove * 2);
					
					InputStream inputStream =  DientesSanosChuloSelector.class.getResourceAsStream("chulo_.png");
					RenderingHints rh = new RenderingHints(
				             RenderingHints.KEY_TEXT_ANTIALIASING,
				             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					graphics2d.setRenderingHints(rh);
					graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					graphics2d.drawImage(ImageIO.read(inputStream), x1, y1, x2, y2, null);
				} catch (Exception e) { 
					e.printStackTrace();
					Notificaciones.mostrarNotificacionError("Error", "Error al cargar dientes sanos", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES); 
				} 
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
