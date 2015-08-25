package com.framework.macros.odontograma.selectores;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.framework.constantes.IConstantes;
import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;
import com.framework.notificaciones.Notificaciones;


/*** Se cambio por Diente en Erupcion*/
public class ProtesisRemovible  extends Convencion implements ISelectorTotal {

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
				/* ocultamos lineas*/
//				graphics2d.setColor(Color.white);
//				Line2D line2dTD = new Line2D.Double(initXBS,
//		                initYBS, width_s + initX,
//		                heigth_s + initY);
//				graphics2d.draw(line2dTD);
//
//		        // colocamos lineas
//		        Line2D line2dDT = new Line2D.Double(initXBS,
//		                heigth_s + initY, width_s + initX,
//		                initXBS);
//		        graphics2d.draw(line2dDT);
//		        
//		        /* creamo un linea en el centro*/
//		        graphics2d.setColor(Color.BLUE);
//		        /* line horizontal*/
//		         int xMove = width_i / 2;
//                 int xi = initXBS + xMove;
//				 int xf = width_s + initX - xMove;
//				 Line2D line2dSuperior = new Line2D.Double(xi,
//						 centerY, xf,
//						 centerY);
//				 graphics2d.setStroke(new BasicStroke(2));
//				 graphics2d.draw(line2dSuperior);
				 
				 // Cambiamos por un flecha
				try {
					 DienteMacro dienteMacro = getDienteMacro();
					 if(dienteMacro != null){
						 int xMove = (width_i / 2) + 3;
//					     int yMove =  (heigth_i / 2) + 3;
					     int x1 = centerX - xTransport - xMove;
					     int x2 = width_i + (xMove * 2);
//					     int y1 = initY;
					     int y2 = heigth_s + initY + initXBS;
					     
						 // aplicamos renderizado
						 RenderingHints rh = new RenderingHints(
					             RenderingHints.KEY_TEXT_ANTIALIASING,
					             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
						graphics2d.setRenderingHints(rh);
						graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
						graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
						graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						
						// rotamos la imagen si no es superior
						if(dienteMacro.isParteSuperior()){
							InputStream inputStream =  DientesSanosChuloSelector.class.getResourceAsStream("flecha_abajo.png");
							graphics2d.drawImage(ImageIO.read(inputStream), x1 - 1, initXBS * (-1), x2  + 1, y2 + ((heigth_s / 2)  - (heigth_i / 2)) + initY, null);
						}else{ 
							InputStream inputStream =  DientesSanosChuloSelector.class.getResourceAsStream("flecha_.png");
							graphics2d.drawImage(ImageIO.read(inputStream), x1 - 1, initXBS * (-1), x2  + 5, y2 + ((heigth_s / 2)  - (heigth_i / 2)) + initY, null);
						}
						graphics2d.dispose();
					 }else{
						Notificaciones
								.mostrarNotificacionAlerta(
										"Advertencia",
										"Para realizar esta opcion el diente debe extender de una convencion",
										3000); 
					 }
				} catch (Exception e) {
					 Notificaciones.mostrarNotificacionAlerta("Adverncia", "Ha ocurrido un error al cargar ", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
				}
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.PRETESIS_REMOVIBLE;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				
			}
		};
	}

}
