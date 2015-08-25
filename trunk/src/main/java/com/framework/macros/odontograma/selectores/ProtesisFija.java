package com.framework.macros.odontograma.selectores;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;
import com.framework.macros.odontograma.util.ISectorDiente.TypeSector;

public class ProtesisFija  extends Convencion implements ISelectorTotal {

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
				//log.info("Diente: " + getDienteMacro().getNumber() + " - nivel: " + getDienteMacro().getNivel());
				unirNodosProtesisFija(getDienteMacro(), centerX, centerY,
						xTransport, yTransport, width_s, heigth_s, width_i,
						heigth_i, initX, initY, initXBI, initXBS, initYBI,
						initYBS);
			}
			
			@Override
			public TypeSector getTypeSector() {
				return TypeSector.PROTESIS_FIJA;
			}
			
			@Override
			public void onRemover(DienteMacro dienteMacro, SECTOR sector) {
				//log.info("Remover protesis fija - sector: " + sector); 
				actualizarNodos(dienteMacro); 
			}
		};
	}

	/**
	 * Este metodo me permite actualizar los nodos tanto derecho como izquierdo
	 * @author Luis Miguel Hernandez Perez
	 * */
	public void actualizarNodos(DienteMacro dienteMacro){
		DienteMacro dienteMacroDer = dienteMacro.getDienteMacroNodoDer();
		DienteMacro dienteMacroIzq = dienteMacro.getDienteMacroNodoIzq();
		
		if(isProtesisFija(dienteMacroDer)){
			//log.info("Diente: " + dienteMacroDer.getNumber() + " - nivel: " + dienteMacroDer.getNivel());
			// le inyectamos el diente para que no aiga concurrencia
			setDienteMacro(dienteMacroDer);
			dienteMacroDer.setNivel(2); 
			dienteMacroDer.apllyAll();
		}
		if(isProtesisFija(dienteMacroIzq)){
			//log.info("Diente: " + dienteMacroIzq.getNumber() + " - nivel: " + dienteMacroIzq.getNivel());
			// le inyectamos el diente para queno aiga concurrencia
			setDienteMacro(dienteMacroIzq);
			dienteMacroIzq.setNivel(2); 
			dienteMacroIzq.apllyAll();
		}
	}
	
	
	
	/**
	 *  Este metodo me permite 
	 *  unir los dientes dependiendo de la seleccion
	 * */
	public  void unirNodosProtesisFija(DienteMacro dienteMacro,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX,
			final int initY, final int initXBI, final int initXBS,
			final int initYBI, final int initYBS) {
		
		// pintamos el diente inicial
		//log.info("Diente: " + dienteMacro.getNumber() + " - nivel: " + dienteMacro.getNivel()); 
		pintarUnion(dienteMacro, centerX, centerY, xTransport, yTransport, width_s, heigth_s, width_i, heigth_i, initX, initY, initXBI, initXBS, initYBI, initYBS);
		// verificamos nivel para repintado
		
		if(dienteMacro.getNivel() == 1){
			DienteMacro dienteMacroDer = dienteMacro.getDienteMacroNodoDer();
			DienteMacro dienteMacroIzq = dienteMacro.getDienteMacroNodoIzq();
			
			if(isProtesisFija(dienteMacroDer)){
				//log.info("Diente: " + dienteMacroDer.getNumber() + " - nivel: " + dienteMacroDer.getNivel());
				// le inyectamos el diente para que no aiga concurrencia
				setDienteMacro(dienteMacroDer);
				dienteMacroDer.setNivel(2); 
				dienteMacroDer.apllyAll();
			}
			if(isProtesisFija(dienteMacroIzq)){
				//log.info("Diente: " + dienteMacroIzq.getNumber() + " - nivel: " + dienteMacroIzq.getNivel());
				// le inyectamos el diente para queno aiga concurrencia
				setDienteMacro(dienteMacroIzq);
				dienteMacroIzq.setNivel(2); 
				dienteMacroIzq.apllyAll();
			}
		}else{
			// si no es nivel 1, lo colocamos para que tenga vuelva al nivel inicial
			dienteMacro.setNivel(1); 
		}
	}

	/* Este metodo era para recorrer el */
//	private static void recorrer(DienteMacro dienteMacro) {
//		 System.out.print("" + dienteMacro.getNumber() + ", ");
//		 DienteMacro dienteMacroNodo = dienteMacro.getDienteMacroNodoIzq();
//		 if(dienteMacroNodo != null){ 
//			 recorrer(dienteMacroNodo);  
//		 }
//	}

//	/**
//	 * Este metodo me permite visualizar
//	 * la union de los dientes
//	 * @param dienteMacro 
//	 * */
//	private static void visualizarUnionDienteDerechaIzquierda(
//			DienteMacro dienteMacroPrimerDienteDerecha,
//			final int centerX, final int centerY, final int xTransport,
//			final int yTransport, final int width_s, final int heigth_s,
//			final int width_i, final int heigth_i, final int initX,
//			final int initY, final int initXBI, final int initXBS,
//			final int initYBI, final int initYBS, DienteMacro dienteMacroInicial) { 
//		/* pintamos el diente */
//		pintarUnion(dienteMacroPrimerDienteDerecha, centerX, centerY,
//				xTransport, yTransport, width_s, heigth_s, width_i, heigth_i,
//				initX, initY, initXBI, initXBS, initYBI, initYBS);
//		if(!dienteMacroPrimerDienteDerecha.getNumber().equals(dienteMacroInicial.getNumber())){
//			  // pasamos a nivel 2 de repintado
//			  dienteMacroPrimerDienteDerecha.setNivel(2); 
//		      dienteMacroPrimerDienteDerecha.apllyAll();
//		}else{
//			if (dienteMacroPrimerDienteDerecha.getDienteMacroNodoIzq() != null
//					&& isProtesisFija(dienteMacroPrimerDienteDerecha.getDienteMacroNodoIzq())) { 
//	            visualizarUnionDienteDerechaIzquierda(dienteMacroPrimerDienteDerecha.getDienteMacroNodoIzq(), centerX, centerY,
//	    				xTransport, yTransport, width_s, heigth_s, width_i, heigth_i,
//	    				initX, initY, initXBI, initXBS, initYBI, initYBS, dienteMacroInicial); 
//			}
//		}
//	}
 
	private static void pintarUnion(DienteMacro diente,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX,
			final int initY, final int initXBI, final int initXBS,
			final int initYBI, final int initYBS) {
		// 1 nodo izquierda
       if(!isProtesisFija(diente.getDienteMacroNodoDer()) && isProtesisFija(diente.getDienteMacroNodoIzq())){ 
    	   //log.info("1 nodo izquierdo"); 
			pintarUnNodoIzq(diente, centerX, centerY, xTransport, yTransport,
					width_s, heigth_s, width_i, heigth_i, initX, initY,
					initXBI, initXBS, initYBI, initYBS); 
    	// 1 nodo derecha   
       }else if(isProtesisFija(diente.getDienteMacroNodoDer()) && !isProtesisFija(diente.getDienteMacroNodoIzq())){ 
    	   //log.info("1 nodo derecho"); 
    	   pintarUnNodoDer(diente, centerX, centerY, xTransport, yTransport,
					width_s, heigth_s, width_i, heigth_i, initX, initY,
					initXBI, initXBS, initYBI, initYBS); 
    	// 2 nodo   
       }else if(isProtesisFija(diente.getDienteMacroNodoDer()) && isProtesisFija(diente.getDienteMacroNodoIzq())){ 
    	   //log.info("2 nodos");  
			pintarDosNodos(diente, centerX, centerY, xTransport, yTransport,
					width_s, heigth_s, width_i, heigth_i, initX, initY,
					initXBI, initXBS, initYBI, initYBS);
       }else{// 0 nodos 
    	   //log.info("0 nodos"); 
			pintarCeroNodos(diente, centerX, centerY, xTransport, yTransport,
					width_s, heigth_s, width_i, heigth_i, initX, initY,
					initXBI, initXBS, initYBI, initYBS);
       }
	}
 
	private static void pintarCeroNodos(DienteMacro diente,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX,
			final int initY, final int initXBI, final int initXBS,
			final int initYBI, final int initYBS) {
		// pintar en ceros
		int xi = initXBS;
		int xf = width_s + initX;
		Graphics2D graphics2d = diente.getGraphics2d();
		graphics2d.setColor(Color.BLUE);
		graphics2d.setStroke(new BasicStroke(3));
		/* linea superior */
		int y1 = centerY - yTransport;
		 Line2D line2dSuperior = new Line2D.Double(xi,
				 y1, xf,
				 y1);
		 graphics2d.draw(line2dSuperior);
		/* linea inferior*/
		int y2 = centerY + yTransport;
		 Line2D line2dInferior = new Line2D.Double(xi,
				 y2, xf,
				 y2);
		 graphics2d.draw(line2dInferior);
		 
		 // pintamos lines superior
		 graphics2d.setStroke(new BasicStroke(2));
		 int posicionYFinal = 5;
		 graphics2d.draw(new Line2D.Double(centerX - xTransport, posicionYFinal, centerX + xTransport,  posicionYFinal));
		 graphics2d.draw(new Line2D.Double(centerX - xTransport, initYBS, centerX - xTransport,  posicionYFinal));
		 graphics2d.draw(new Line2D.Double(centerX + xTransport, initYBS, centerX + xTransport,  posicionYFinal));
		 
		 
		 // pintamos linea inferior
		 posicionYFinal = heigth_s + initY + 5;
		 graphics2d.draw(new Line2D.Double(centerX - xTransport, posicionYFinal, centerX + xTransport,  posicionYFinal));
		 graphics2d.draw(new Line2D.Double(centerX - xTransport, heigth_s + initY, centerX - xTransport,  posicionYFinal));
		 graphics2d.draw(new Line2D.Double(centerX + xTransport, heigth_s + initY, centerX + xTransport,  posicionYFinal));
		 graphics2d.dispose();
	}
	
	
	private static void pintarDosNodos(DienteMacro diente,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX,
			final int initY, final int initXBI, final int initXBS,
			final int initYBI, final int initYBS) {
		Graphics2D graphics2d = diente.getGraphics2d();
		graphics2d.setStroke(new BasicStroke(3));  
		int xi = -10;
		int xf = diente.getWidthSpacio() + initX + 20;
		graphics2d.setColor(Color.BLUE);
		/* linea superior */
		int y1 = centerY - yTransport;
		 Line2D line2dSuperior = new Line2D.Double(xi,
				 y1, xf,
				 y1);
		 graphics2d.draw(line2dSuperior);
		/* linea inferior*/
		int y2 = centerY + yTransport;
		 Line2D line2dInferior = new Line2D.Double(xi,
				 y2, xf,
				 y2);
		 graphics2d.draw(line2dInferior);
		 
		 graphics2d.setStroke(new BasicStroke(2));
		 /* pintamos union de linea  */
		 int posicionYFinal = 5;
		 
		 // linea superior
		 graphics2d.draw(new Line2D.Double(xi, posicionYFinal, xf,  posicionYFinal));
		 
		 // linea inferio
		 
		 posicionYFinal = heigth_s + initY + 5;
		 
		 graphics2d.draw(new Line2D.Double(xi, posicionYFinal, xf,  posicionYFinal));
		 
		 graphics2d.dispose();
	}
	
	
	private static void pintarUnNodoIzq(DienteMacro diente,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX,
			final int initY, final int initXBI, final int initXBS,
			final int initYBI, final int initYBS) {
		int xi = initXBS;
		int xf = diente.getWidthSpacio() + initX + 20;
		Graphics2D graphics2d = diente.getGraphics2d();
		graphics2d.setColor(Color.BLUE);
		graphics2d.setStroke(new BasicStroke(3));
		/* linea superior */
		int y1 = centerY - yTransport;
		 Line2D line2dSuperior = new Line2D.Double(xi,
				 y1, xf,
				 y1);
		 graphics2d.draw(line2dSuperior);
		/* linea inferior*/
		int y2 = centerY + yTransport;
		 Line2D line2dInferior = new Line2D.Double(xi,
				 y2, xf,
				 y2);
		 graphics2d.draw(line2dInferior);
		 
		 graphics2d.setStroke(new BasicStroke(2));
		 /* pintamos union de linea  */
		 int posicionXInicial = centerX - xTransport;
		 int pocisionYInical = initYBS;
		 int posicionYFinal = 5;
		 
		 // linea superior
		 // horizontal
		 graphics2d.draw(new Line2D.Double(posicionXInicial, posicionYFinal, xf,  posicionYFinal));
		 // vertical
		 graphics2d.draw(new Line2D.Double(posicionXInicial, pocisionYInical, posicionXInicial,  posicionYFinal));
		 
		 // linea inferio
		 
		 posicionYFinal = heigth_s + initY + 5;
		 
		 // linea horinzontal
		 graphics2d.draw(new Line2D.Double(posicionXInicial, posicionYFinal, xf,  posicionYFinal));
		 
		 // linea vertical
		 graphics2d.draw(new Line2D.Double(posicionXInicial, heigth_s + initY + 1, posicionXInicial,  posicionYFinal));
		 
		 graphics2d.dispose();
	}
	
	
	private static void pintarUnNodoDer(DienteMacro diente,
			final int centerX, final int centerY, final int xTransport,
			final int yTransport, final int width_s, final int heigth_s,
			final int width_i, final int heigth_i, final int initX,
			final int initY, final int initXBI, final int initXBS,
			final int initYBI, final int initYBS) {
		int xi = -10;
		int xf = width_s + initX;
		Graphics2D graphics2d = diente.getGraphics2d();
		graphics2d.setColor(Color.BLUE);
		graphics2d.setStroke(new BasicStroke(3));
		/* linea superior */
		int y1 = centerY - yTransport;
		 Line2D line2dSuperior = new Line2D.Double(xi,
				 y1, xf,
				 y1);
		 graphics2d.draw(line2dSuperior);
		/* linea inferior*/
		int y2 = centerY + yTransport;
		 Line2D line2dInferior = new Line2D.Double(xi,
				 y2, xf,
				 y2);
		 graphics2d.draw(line2dInferior);
		 
		 graphics2d.setStroke(new BasicStroke(2));
		 /* pintamos union de linea  */
		 int posicionXInicial = centerX + xTransport;
		 int pocisionYInical = initYBS;
		 int posicionYFinal = 5;
		 
		 
		 graphics2d.draw(new Line2D.Double(posicionXInicial, posicionYFinal, -10,  posicionYFinal));
		 graphics2d.draw(new Line2D.Double(posicionXInicial, pocisionYInical, posicionXInicial,  posicionYFinal));
		 
         // linea inferio
		 
		 posicionYFinal = heigth_s + initY + 5;
		 
		 graphics2d.draw(new Line2D.Double(posicionXInicial, posicionYFinal, -10,  posicionYFinal));
		 graphics2d.draw(new Line2D.Double(posicionXInicial, heigth_s + initY + 1, posicionXInicial, posicionYFinal));
		 
		 graphics2d.dispose();
	}

//	private static DienteMacro getPrimerNodo(DienteMacro dienteMacro) { 
//		if (dienteMacro.getDienteMacroNodoDer() != null && isProtesisFija(dienteMacro.getDienteMacroNodoDer())) {
//			return getPrimerNodo(dienteMacro.getDienteMacroNodoDer());
//		} else {
//			return dienteMacro;
//		}
//	}
	
	private static boolean isProtesisFija(DienteMacro dienteMacro){
		if(dienteMacro != null){
			if(dienteMacro.getiSectorDienteSuperficieSuperior() != null){
				return (dienteMacro.getiSectorDienteSuperficieSuperior().getTypeSector() == TypeSector.PROTESIS_FIJA);
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
}
