package com.framework.macros.graficas;

import java.io.InputStream;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;

import com.framework.macros.graficas.moldG.PainterMoldG;

public class GraficaMacro extends Image implements AfterCompose{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private PainterMoldG painterMoldG;
	
	/* cordenadas */
	private Double focoX;
	private Double focoY;
	private Double x;
	private Double y;
	
	private Integer lengthSectX;
	private Integer lengthSectY;
	
	private boolean showBackLine = false;
	
	
	/* desde donde empieza los sectores X y Y*/
	private Double initSectX = 0d;
	private Double initSectY = 0d;
	
	/**
	 *  Informacion de grupos
	 * */
	private int groups = 1;
	
	/* Estos son los anchos de las graficas por defecto */
	private int widthI;
	private int heigthI;
	
   public void afterCompose() {draw();}
	
	public void draw(){
		if(focoX == null || focoY == null || y == null || x == null || lengthSectX == null || lengthSectY == null){
			   addEventListener(Events.ON_CLICK, new EventListener<MouseEvent >() {
					public void onEvent(MouseEvent mouseEvent) throws Exception { 
			             Messagebox.show("Estas son las coordenadas: X: " + mouseEvent.getX() + " Y: " +  mouseEvent.getY());			
					}
			  });
			  
		   }else{
			    /* Para que realice todos los calculos, internos del graficador */
			    painterMoldG.apllyGraficaSectores();
//			    Messagebox.show("Sectores en X: " + painterMoldG.getWidthSector() + " Y: " + painterMoldG.getHeigthSector()); 
				addEventListener(Events.ON_MOUSE_OVER, new EventListener<MouseEvent>() {
					public void onEvent(MouseEvent event) throws Exception {
						int xTemp = event.getX();
//						int yTemp = event.getY();
						
						// para verificar el contexto
						if(xTemp >= focoX && xTemp <= x){
							setStyle("cursor:pointer");
						}else{
							setStyle("cursor:default");
						}
					}
				});
				// en esta opcion muestra linealmente, las graficas para verificar, que los puntos esten correctos.
				if(showBackLine){
					addEventListener(Events.ON_CREATE, new EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							painterMoldG.showBackLine();
						}
					});
				}
		   }
	}
	
	public void setMapper(String mapper){ 
		InputStream imagenInputStream = Executions.getCurrent().getDesktop().getWebApp().getResourceAsStream(mapper);
		if(painterMoldG == null){
			painterMoldG = new PainterMoldG(this);
		}
		painterMoldG.setFondo(imagenInputStream);
	}
	
	
	/* Getter and Setter*/
	public PainterMoldG getPainterMoldG() {
		return painterMoldG;
	}

	public void setPainterMoldG(PainterMoldG painterMoldG) {
		this.painterMoldG = painterMoldG;
	}

	public Double getFocoX() {
		return focoX;
	}

	public void setFocoX(Double focoX) {
		this.focoX = focoX;
	}

	public Double getFocoY() {
		return focoY;
	}

	public void setFocoY(Double focoY) {
		this.focoY = focoY;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getInitSectX() {
		return initSectX;
	}

	public void setInitSectX(Double initSectX) {
		this.initSectX = initSectX;
	}

	public Double getInitSectY() {
		return initSectY;
	}

	public void setInitSectY(Double initSectY) {
		this.initSectY = initSectY;
	}

	public Integer getLengthSectX() {
		return lengthSectX;
	}

	public void setLengthSectX(Integer lengthSectX) {
		this.lengthSectX = lengthSectX;
	}

	public Integer getLengthSectY() {
		return lengthSectY;
	}

	public void setLengthSectY(Integer lengthSectY) {
		this.lengthSectY = lengthSectY;
	}

	public boolean isShowBackLine() {
		return showBackLine;
	}

	public void setShowBackLine(boolean showBackLine) {
		this.showBackLine = showBackLine;
	}

	public int getGroups() {
		return groups;
	}

	public void setGroups(int groups) {
		this.groups = groups;
	}


	public int getWidthI() {
		return widthI;
	}

	public void setWidthI(int widthI) {
		this.widthI = widthI;
	}

	public int getHeigthI() {
		return heigthI;
	}

	public void setHeigthI(int heigthI) {
		this.heigthI = heigthI;
	}
}
