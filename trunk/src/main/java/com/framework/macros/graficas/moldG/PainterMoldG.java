package com.framework.macros.graficas.moldG;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.framework.macros.graficas.GraficaMacro;
import com.framework.macros.graficas.moldG.state.LineStatePoints;
import com.framework.macros.graficas.moldG.state.PuntosPointerStatae;
import com.framework.macros.graficas.respuesta.RespuestaInt;

public class PainterMoldG {
	
	public static Logger log = Logger.getLogger(PainterMoldG.class);
	
	
	/* lista de puntos */
//	private List<PointerMoldGState> pointerMoldGStates;
	
    // El graficador
    private Graphics2D g2d;
    private BufferedImage mapper;
    
    // este es el contenedor de la grafica
    private GraficaMacro graficaMacro;
    
    
    //este es el fondo de la grafica
    private InputStream fondo;
	
	// ancho de los sectores
	private Double widthSector;
	private Double heigthSector;
    
    
    public PainterMoldG(GraficaMacro graficaMacro) {
    	this.graficaMacro = graficaMacro;
	}
    
    public void apllyGraficaSectores(){
    	if(graficaMacro != null){
    		/* Calculamos los sectores en el Eje X*/
    		widthSector = (getWidth() / (graficaMacro.getLengthSectX().doubleValue())) ;
    		
    		/* Calculamos los sectores en el Eje Y*/
    		heigthSector = (getHeigth() / (graficaMacro.getLengthSectY().doubleValue()));
    	}else{
    		log.error("Grafica Macro nula..!!"); 
    	}
    }
    
    /**
	 *  calculamos el centro
	 * */
	public Double getCenterX(){
		return (graficaMacro.getFocoX().intValue()  + (getWidth() / 2d));
	}
	
	public Double getCenterY(){
		return (graficaMacro.getY().intValue()  + (getHeigth() / 2d));
	}
	
	/**
	 *  Calculamos los width y heigth 
	 * */
	public int getWidth(){
		return graficaMacro.getX().intValue() - graficaMacro.getFocoX().intValue();
	}
	
	public int getHeigth(){
		return graficaMacro.getFocoY().intValue() - graficaMacro.getY().intValue();
	}
	
	
    /**
     * Este metodo se encarga de graficar la imagen
     * */
	private void draw(){
		try {
			// fondo de la imagen 
			BufferedImage img = ImageIO.read(fondo);  
			// creamos un buffer RGB
			mapper = new BufferedImage(graficaMacro.getWidthI(), graficaMacro.getHeigthI(),
					BufferedImage.TYPE_INT_RGB);
			// obtenemos la grafica de la imagen para la edicion
			g2d = mapper.createGraphics();
			g2d.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// aplicamos el findo de la grafica.
			g2d.drawImage(img, 0, 0, graficaMacro.getWidthI(), graficaMacro.getHeigthI(), null);
		} catch (IOException e) { 
			log.error("Error al generar la Imagen", e);
		}
	}
	
	/** Actualiza el fondo */
    private void apllyDraw(){graficaMacro.setContent(mapper);}
	
	/** Show Back Lines - Este metodo, muestra el
	 *  trasado de las lineas para verificar que esten correctas, las referencias
	 *  
	 *  @author EvanSandryHB
	 *  */
    public void showBackLine(){
    	/* Puntos de Referenciacion */
    	double[][] points = {
    			           {graficaMacro.getFocoX(), graficaMacro.getFocoY(), 1},
    			           {graficaMacro.getX(), graficaMacro.getFocoY(), 1},
    			           {graficaMacro.getFocoX(), graficaMacro.getY(), 1},
    			           {graficaMacro.getX(), graficaMacro.getY(), 1},
    			           {getCenterX(), getCenterY(), 2}
    	                 };
    	for (double[] point : points) {
    		if(point[2] == 1) new PuntosPointerStatae(point[0], point[1]).draw(g2d);  
    		else if(point[2] == 2){
    			PuntosPointerStatae puntosPointerStatae = new PuntosPointerStatae(point[0], point[1]);
    			puntosPointerStatae.setRadio(10);
    			puntosPointerStatae.setBorder(3); 
    			puntosPointerStatae.draw(g2d, "Centro"); 
    		}
		}
    	
    	/* Agregamos puntos en x */
    	for (int i = 1; i <= graficaMacro.getLengthSectX().intValue(); i++) {
    	    new PuntosPointerStatae(graficaMacro.getFocoX().doubleValue() + (widthSector * i), graficaMacro.getFocoY()).draw(g2d);   
		}
    	
    	for (int i = 1; i <= graficaMacro.getLengthSectY().intValue(); i++) {
    		new PuntosPointerStatae(graficaMacro.getFocoX().doubleValue(), graficaMacro.getY() + (int)Math.round(heigthSector * i)).draw(g2d);   
		}
    	
    	/* sentralizamos puntos */
    	LineMoldGState lineMoldGStateX = new LineStatePoints(graficaMacro.getFocoX(), getCenterY(), getCenterX(), getCenterY());
    	LineMoldGState lineMoldGStateY = new LineStatePoints(getCenterX(), getCenterY(), getCenterX(), graficaMacro.getFocoY());
    	
    	/* Dibujamos las lineas */
    	lineMoldGStateX.draw(g2d, 3);
    	lineMoldGStateY.draw(g2d, 3);
    	
    	/* actualizamos grafica */
    	apllyDraw();
    }
    
    public void showPoints(List<RespuestaInt> respuestaInts){
       PuntosPointerStatae puntosPointerStataeTemp = null;
   	   for (RespuestaInt respuestaInt : respuestaInts) {
   		 PuntosPointerStatae puntosPointerStatae = new PuntosPointerStatae(graficaMacro.getFocoX().doubleValue() + (widthSector * respuestaInt.getX()), graficaMacro.getFocoY().doubleValue() - (heigthSector * respuestaInt.getY())); 
	     puntosPointerStatae.draw(g2d, respuestaInt.isActual() ? Color.RED : Color.BLACK);
   		 if(puntosPointerStataeTemp != null){new LineStatePoints(puntosPointerStataeTemp, puntosPointerStatae).draw(g2d);}
   		 puntosPointerStataeTemp = puntosPointerStatae;
   	   }
   	   apllyDraw();
    }


	public InputStream getFondo() {
		return fondo;
	}

	public void setFondo(InputStream fondo) {
		this.fondo = fondo;
		draw();apllyDraw();
	}

	public Double getWidthSector() {
		return widthSector;
	}

	public void setWidthSector(Double widthSector) {
		this.widthSector = widthSector;
	}

	public Double getHeigthSector() {
		return heigthSector;
	}

	public void setHeigthSector(Double heigthSector) {
		this.heigthSector = heigthSector;
	}
}
