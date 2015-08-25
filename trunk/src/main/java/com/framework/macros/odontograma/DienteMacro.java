package com.framework.macros.odontograma;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Image;

import com.framework.macros.odontograma.util.Convencion;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISectorDiente.TypeSector;
import com.framework.macros.odontograma.util.ISelector;
import com.framework.macros.odontograma.util.ISelectorTotal;
import com.framework.macros.odontograma.util.ISuperficieTotal;
import com.framework.macros.odontograma.util.OnClickDiente;

/**
 * Esta clase nos permite simular un diente en el odontograma
 * @author Luis Miguel Hernández 
 * */
public class DienteMacro extends Image {
	
//	private static Logger log = Logger.getLogger(DienteMacro.class);

	/* TamAnios del diente */
	private final int width = 40;
    private final int heigth = 40;
    private final int initX = 10;
    private final int initY = 10;
    
    /* especifica si el diente es de permanente(Adulto) o temporal*/
    private boolean isAdulto = false;
    private boolean isParteSuperior = false;
    
    /* Nodos de los dientes derecha y izquierda */
    private DienteMacro dienteMacroNodoIzq;
    private DienteMacro dienteMacroNodoDer;
    
    /* Niveles de repintado */
    private int nivel = 1;
    
    /* esto especifica con que tipo de diente va ha trabajar, si con 5 sectores o 4 serctores. */
    public static final String MOLD_5SECTORES = "sectores5";
    public static final String MOLD_4SECTORES = "sectores4";
 
     // border externo
    private final int width_border_superior = width;
    private final int heigth_border_superior = heigth;
    private final int initXBorderSuperior = initX;
    private final int initYBorderSuperior = initY;
    // border interno
    private final int width_border_interno = 14;
    private final int heigth_border_interno = 14;
    private final int initXBorderInferior = (width_border_superior / 2)
            - (width_border_interno / 2) + initXBorderSuperior;
    private final int initYBorderInferior = (width_border_superior / 2)
            - (heigth_border_interno / 2) + initYBorderSuperior;
    
    /**
     * Este objeto es el que se encarga de graficar 
     * @author Luis Miguel
     * */
    private Graphics2D graphics2d;
    
    /* estos son los limites de cada sector tanto en x como en y*/
    /* derecha */
    private int xf_r;
    private int xi_r;
    private int yi_r;
    private int yf_r;
    
    /* izquierda */
    private int xf_l;
    private int xi_l;
    private int yi_l;
    private int yf_l;
    
    /* arriba */
    private int xf_t;
    private int xi_t;
    private int yi_t;
    private int yf_t;
    
    /* abajo */
    private int xf_b;
    private int xi_b;
    private int yi_b;
    private int yf_b;
    
    /* centro */
    private int xf_c;
    private int xi_c;
    private int yi_c;
    private int yf_c;
    
    /**
     * Este campo lleva el numero del diente
     * @author Luis Miguel
     * */
    private String number;
    
    /**
     * Este lleva el tipo de diente
     * */
    private String moldDiente;
    
    /* sectores de dibujo */
    /* cada uno lleva el dibujo de cada sector */
    private ISectorDiente iSectorDienteRigth;
    private ISectorDiente iSectorDienteLeft;
    private ISectorDiente iSectorDienteTop;
    private ISectorDiente iSectorDienteBottom;
    private ISectorDiente iSectorDienteCenter;
    
    @Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Cleaner{}
    
    /**
     * Lleva los distintos tipos de estado de los dientes
     * @author Luis Miguel
     * */
    public enum Estado {NONE, BUENA, DESADAPTADA, POR_RELLNAR, RELLENADO};
    
    
    /**
     * Esta anotacion, identifica que el procedimiento es marcado por hacer.
     * @author Luis Miguel Hernandez perez
     * */
    @Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface TODO{
    	Estado estado() default Estado.NONE;
    }
    
    @Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface visibleSectores{
    	boolean visible();
    }
    
    /* superficie superior */
    private ISuperficieTotal iSectorDienteSuperficieSuperior;
    
    private ISelector iSelector;
    
    /**
     * Este objeto lleva el evento en el cual se selecciona el diente
     * @author Luis Miguel
     * */
    private OnClickDiente onClickDiente;

    // estos son los sectores del diente
    /**
     * Estos son los tipos de sectores que lleva el diente
     * @author Luis Miguel
     * */
    public enum SECTOR {
        LEFT, TOP, CENTER, RIGTH, BOTTOM, NONE
    };
    
    
    private SECTOR sectorSelectCache;
    
    /**
     * Este es el centro en el eje de las Y
     * @author Luis Miguel
     * */
    private int centerY;
    
    /**
     * Este es el centro en el eje de las X
     * @author Luis Miguel
     * */
    private int centerX;
    
    /**
     * esta es la mitad del rectando centro en Y
     * @author Luis Miguel
     * */
    private int yTransport;
    
    /**
     * esta es la mitad del rectando centro en X
     * @author Luis Miguel
     * */
    private int xTransport;
    
    private boolean visibleSector;
    private EventListener eventListener;

	private BufferedImage bufferedImage_;

	public BufferedImage getBufferedImage_() {
		return bufferedImage_;
	}

	public DienteMacro() {
		visibleSector = true;
		moldDiente = MOLD_5SECTORES;
		sectorSelectCache = SECTOR.NONE;
		loadEvents();
		setStyle("cursor:pointer");
	}
	
	
	private void limpiarBuffer(){
		if(bufferedImage_ != null){
			bufferedImage_.flush();			
		}
	}
	
	/**
	 * Este metodo incializa todos los eventos del diente
	 * @author Luis Miguel
	 * */
	private void loadEvents(){
		addEventListener("onCreate", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				setContent(draw()); 
			}
		});
		
		eventListener = new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				// cargamos nuevo iselector
				if(onClickDiente != null){
					onClickDiente.onClick(DienteMacro.this);
				}
				
				// cargamos el evento del diente
				MouseEvent mouseEvent = (MouseEvent) event;
				seleccionaPosicion(mouseEvent);
				
				// Cargamos la ultima accion
				if(onClickDiente != null){
					onClickDiente.onDespuesDelClick(DienteMacro.this);
				}
			}
		};
		addEventListener("onClick", eventListener);
	}

	/**
	 * Este metodo es el encargo de realizar la accion cuando se selecciona una posicion
	 * @author Luis Miguel
	 * */ 
	private void seleccionaPosicion(MouseEvent mouseEvent) {
		if (graphics2d != null) {
			// calculamos pocision seleccionada.
			int x = mouseEvent.getX();
			int y = mouseEvent.getY();
			BufferedImage  pinterSelector = draw();
			sectorSelectCache = SECTOR.NONE;
			if(iSelector != null){
				if(iSelector instanceof ISelectorTotal){
					iSectorDienteSuperficieSuperior = getISuperficieTotal();
				}else{
					if(validateSeleccionInvidual()){ 
						calculateSectorSelected(x, y);
					}else{
						calculateSectorSelectedAll();
					}
				}
				
				ISectorDiente[] sectores = { iSectorDienteBottom,
						iSectorDienteCenter, iSectorDienteLeft,
						iSectorDienteRigth, iSectorDienteTop};
				if(visibleSector)
				for (ISectorDiente iSectorDiente : sectores) {
					 if(iSectorDiente != null){
						 iSectorDiente.onDraw(graphics2d);
					 }
				}
				if(iSectorDienteSuperficieSuperior != null){
					 iSectorDienteSuperficieSuperior.onDraw(graphics2d);
				}
			}
			setContent(pinterSelector);
			applyProperties();
		}
	}
	
	
	public BufferedImage getPaint(){
		BufferedImage  pinterSelector = draw();
		ISectorDiente[] sectores = { iSectorDienteBottom,
				iSectorDienteCenter, iSectorDienteLeft,
				iSectorDienteRigth, iSectorDienteTop};
		for (ISectorDiente iSectorDiente : sectores) {
			 if(iSectorDiente != null){ 
				 iSectorDiente.onDraw(graphics2d);
			 }
		}
		if(iSectorDienteSuperficieSuperior != null){
			 iSectorDienteSuperficieSuperior.onDraw(graphics2d);
		}
		return pinterSelector;
	}
	
	
	/**
	 * Actualizar un selector de un diente, sin sar click
	 * */
	public void apllyAll(){
			BufferedImage  pinterSelector = draw();
			ISectorDiente[] sectores = { iSectorDienteBottom,
					iSectorDienteCenter, iSectorDienteLeft,
					iSectorDienteRigth, iSectorDienteTop};
			for (ISectorDiente iSectorDiente : sectores) {
				 if(iSectorDiente != null){ 
					 iSectorDiente.onDraw(graphics2d);
				 }
			}
			if(iSectorDienteSuperficieSuperior != null){
				 iSectorDienteSuperficieSuperior.onDraw(graphics2d);
			}
			setContent(pinterSelector);
			applyProperties();
	}
	
	public void aplySuper(){
		if(iSelector != null){
			BufferedImage  pinterSelector = draw();
			iSectorDienteSuperficieSuperior = getISuperficieTotal();
			if(iSectorDienteSuperficieSuperior != null){
				 iSectorDienteSuperficieSuperior.onDraw(graphics2d);
			}
			setContent(pinterSelector);
			applyProperties();
		}
	}
	
	public void setContent(BufferedImage image) {
//		try {
			super.setContent(image);
			limpiarBuffer(); 
//		} finally{
//			try {
				// pendiente por probar
//				final ByteArrayOutputStream output = new ByteArrayOutputStream() {
//				    @Override
//				    public synchronized byte[] toByteArray() {// de esta manera ahorramos memoria
//				        return this.buf;
//				    }
//				};
//				ImageIO.write(image, "png", output);
//				new ByteArrayInputStream(output.toByteArray(), 0, output.size()).close();
//			} catch (Exception e) { 
//				e.printStackTrace();
//			}
//		}
	}
	
	public void aplySector(){
		if(iSelector != null){
			BufferedImage  pinterSelector = draw();
			iSectorDienteBottom=
					iSectorDienteCenter= iSectorDienteLeft=
					iSectorDienteRigth= iSectorDienteTop = iSectorDienteSuperficieSuperior = null;
			visibleSector = true;
			setContent(pinterSelector);
			applyProperties();
		}
	}
	
	private boolean validateSeleccionInvidual() {
		if(iSelector.getClass().getAnnotation(Cleaner.class) != null){
			return false;
		}
		return true;
	}

	private void calculateSectorSelected(int x, int y){
		ISectorDiente iSectorDienteAnterior = null;
		ISectorDiente iSectorDienteNuevo = null;
		if (validateArea(x, y, xi_r, xf_r, yi_r, yf_r)) {
			sectorSelectCache = SECTOR.RIGTH;
			iSectorDienteNuevo = getISectorDiente();
			iSectorDienteAnterior = iSectorDienteRigth;
			onClickDiente.onChangeStateDiente(validateValor(iSectorDienteRigth), validateValor(iSectorDienteNuevo), isAdulto, this, sectorSelectCache); 
			iSectorDienteRigth = iSectorDienteNuevo;
		}else if (validateArea(x, y, xi_l, xf_l, yi_l, yf_l)) {
			sectorSelectCache = SECTOR.LEFT;
			iSectorDienteNuevo = getISectorDiente();
			iSectorDienteAnterior = iSectorDienteLeft;
			onClickDiente.onChangeStateDiente(validateValor(iSectorDienteLeft), validateValor(iSectorDienteNuevo), isAdulto, this, sectorSelectCache);
			iSectorDienteLeft  = iSectorDienteNuevo;
		}else if (validateArea(x, y, xi_t, xf_t, yi_t, yf_t)) {
			sectorSelectCache = SECTOR.TOP;
			iSectorDienteNuevo = getISectorDiente();
			iSectorDienteAnterior = iSectorDienteTop;
			onClickDiente.onChangeStateDiente(validateValor(iSectorDienteTop), validateValor(iSectorDienteNuevo), isAdulto, this, sectorSelectCache);
			iSectorDienteTop  = iSectorDienteNuevo;
		}else if (validateArea(x, y, xi_b, xf_b, yi_b, yf_b)) {
			sectorSelectCache = SECTOR.BOTTOM;
            iSectorDienteNuevo = getISectorDiente();
			iSectorDienteAnterior = iSectorDienteBottom;
			onClickDiente.onChangeStateDiente(validateValor(iSectorDienteBottom), validateValor(iSectorDienteNuevo), isAdulto, this, sectorSelectCache);
			iSectorDienteBottom  = iSectorDienteNuevo;
		}else if (validateArea(x, y, xi_c, xf_c, yi_c, yf_c) && this.moldDiente == MOLD_5SECTORES) {
			sectorSelectCache = SECTOR.CENTER;
			iSectorDienteNuevo = getISectorDiente();
			iSectorDienteAnterior = iSectorDienteCenter;
			onClickDiente.onChangeStateDiente(validateValor(iSectorDienteCenter), validateValor(iSectorDienteNuevo), isAdulto, this, sectorSelectCache);
			iSectorDienteCenter  = iSectorDienteNuevo;
		}
		removerSelectorAnterior(iSectorDienteAnterior, sectorSelectCache, iSectorDienteNuevo); 
	}
	
	/**
	 * Esto me permite saber si el diente removio una denticion
	 * @param iSectorDienteNuevo 
	 * */
	private void removerSelectorAnterior(ISectorDiente iSectorDienteAnterior, SECTOR sector, ISectorDiente iSectorDienteNuevo){
		if(iSectorDienteAnterior != null && iSectorDienteNuevo != null){ 
			if(iSectorDienteAnterior.getTypeSector() != iSectorDienteNuevo.getTypeSector()) 
			 iSectorDienteAnterior.onRemover(this, sector); 
		}
	}
	
	
	public void setSectorFromIselector(SECTOR sector){
        if(sector ==  SECTOR.BOTTOM){
        	sectorSelectCache = SECTOR.BOTTOM;
			ISectorDiente iSectorDienteNuevo = getISectorDiente();
			iSectorDienteBottom  = iSectorDienteNuevo;
        }else if(sector ==  SECTOR.CENTER && this.moldDiente == MOLD_5SECTORES){
        	sectorSelectCache = SECTOR.CENTER;
			ISectorDiente iSectorDienteNuevo = getISectorDiente();
			iSectorDienteCenter  = iSectorDienteNuevo;
        }else if(sector ==  SECTOR.LEFT){
        	sectorSelectCache = SECTOR.LEFT;
			ISectorDiente iSectorDienteNuevo = getISectorDiente();
			iSectorDienteLeft  = iSectorDienteNuevo;
        }else if(sector ==  SECTOR.RIGTH){
        	sectorSelectCache = SECTOR.RIGTH;
			ISectorDiente iSectorDienteNuevo = getISectorDiente();
			iSectorDienteRigth = iSectorDienteNuevo;
        }else if(sector ==  SECTOR.TOP){
        	sectorSelectCache = SECTOR.TOP;
			ISectorDiente iSectorDienteNuevo = getISectorDiente();
			iSectorDienteTop  = iSectorDienteNuevo;
        }else if(sector == SECTOR.NONE){
        	sectorSelectCache = SECTOR.NONE;
        	ISuperficieTotal dienteNuevo = getISuperficieTotal();
        	iSectorDienteSuperficieSuperior = dienteNuevo;
        }
	}
	
	private void calculateSectorSelectedAll(){
		ISectorDiente dienteNuevo = getISectorDiente();
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteRigth), validateValor(dienteNuevo), isAdulto, this, SECTOR.RIGTH);
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteLeft), validateValor(dienteNuevo), isAdulto, this, SECTOR.LEFT);
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteTop), validateValor(dienteNuevo), isAdulto, this, SECTOR.TOP);
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteBottom), validateValor(dienteNuevo), isAdulto, this, SECTOR.BOTTOM);
		if(this.moldDiente == MOLD_5SECTORES)
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteCenter), validateValor(dienteNuevo), isAdulto, this, SECTOR.CENTER);
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteSuperficieSuperior), validateValor(dienteNuevo), isAdulto, this, SECTOR.NONE);
		iSectorDienteRigth = iSectorDienteLeft = iSectorDienteTop = iSectorDienteBottom = iSectorDienteCenter = dienteNuevo;
		iSectorDienteSuperficieSuperior = null;
	}
	
	private ISectorDiente getISectorDiente(){
		return iSelector.select(sectorSelectCache, centerX, centerY,
				xTransport, yTransport, width_border_superior,
				heigth_border_superior, width_border_interno,
				heigth_border_interno, initX, initY, initXBorderInferior,
				initXBorderSuperior, initYBorderInferior, initYBorderSuperior);
	}
	
	private ISuperficieTotal getISuperficieTotal(){
		ISuperficieTotal iSuperficieTotalNUevo = ((ISelectorTotal)iSelector).select(centerX, centerY,
				xTransport, yTransport, width_border_superior,
				heigth_border_superior, width_border_interno,
				heigth_border_interno, initX, initY, initXBorderInferior,
				initXBorderSuperior, initYBorderInferior, initYBorderSuperior);
		//log.info("Superficie total: " + iSectorDienteSuperficieSuperior); 
		ISectorDiente iSectorDienteAnterior = iSectorDienteSuperficieSuperior;
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteAnterior), validateValor(iSuperficieTotalNUevo), isAdulto, this, SECTOR.NONE); 
		ISuperficieTotal iSuperficieTotal = iSuperficieTotalNUevo;
		visibleSectores vb = iSelector.getClass().getAnnotation(visibleSectores.class); 
		Cleaner cleaner =  iSelector.getClass().getAnnotation(Cleaner.class);
		if(vb != null){
			visibleSector = vb.visible();
			if(!visibleSector){
				actualizarCambioDiente();
			}
		}
		else{visibleSector = true;}
		if(cleaner != null){
			actualizarCambioDiente();
//			onClickDiente.onChangeStateDiente(validateValor(iSectorDienteSuperficieSuperior), validateValor(null), isAdulto, this, SECTOR.NONE);
			reset();
		}
		removerSelectorAnterior(iSectorDienteAnterior, SECTOR.NONE, iSuperficieTotalNUevo);
		return iSuperficieTotal;
	}
	
	private void actualizarCambioDiente(){
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteCenter), validateValor(null), isAdulto, this, SECTOR.NONE);
		iSectorDienteCenter = null;
		
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteLeft), validateValor(null), isAdulto, this, SECTOR.NONE);
		iSectorDienteLeft = null;
		
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteRigth), validateValor(null), isAdulto, this, SECTOR.NONE);
		iSectorDienteRigth = null;
		
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteTop), validateValor(null), isAdulto, this, SECTOR.NONE);
		iSectorDienteTop = null;
		
		onClickDiente.onChangeStateDiente(validateValor(iSectorDienteBottom), validateValor(null), isAdulto, this, SECTOR.NONE);
		iSectorDienteBottom = null;
	}
	
	private TypeSector validateValor(ISectorDiente diente){
        if(diente != null){
        	return diente.getTypeSector();
        }
		return TypeSector.NONE;
	}

	/**
	 * Este metodo me valida el area donde selecciono el usuario
	 * y me verifica si selecciono en el sector de la DERECHA, IZQUIERDA, ETC.
	 * @author Luis Miguel Hernandez Perez
	 * */
	private boolean validateArea(int x, int y, int xi, int xf, int yi, int yf) {
		if (x >= xi && x <= xf) {
            if (yi <= y && y <= yf) {
               return true;
            }
        }
		return false;
	}

	/**
	 * Este metodo es el que dibuja el diente
	 * y devuelve las coordenadas
	 * @author Luis Miguel
	 * */
	private BufferedImage draw() {
		// creamos un buffer RGB
		
		bufferedImage_ = new BufferedImage(getWidthSpacio(), getHeigthSpacio(),
				BufferedImage.TYPE_INT_RGB); 
		// obtenemos la grafica de la imagen para la edicion
		graphics2d = bufferedImage_.createGraphics();

		// creamos el fondo de color blanco
		graphics2d.setColor(Color.WHITE);
        graphics2d.fill(new Rectangle2D.Double(0, 0, getWidthSpacio(), getHeigthSpacio()));

        // agregamos el borde superior
        graphics2d.setColor(Color.BLACK);
        Rectangle2D borderSuperior = new Rectangle2D.Double(
                initXBorderSuperior, initYBorderSuperior,
                width_border_superior, heigth_border_superior);
        graphics2d.draw(borderSuperior);

        Line2D line2dTD = new Line2D.Double(initXBorderSuperior,
                initYBorderSuperior, width_border_superior + initX,
                heigth_border_superior + initY);
        graphics2d.draw(line2dTD);

        // colocamos lineas
        Line2D line2dDT = new Line2D.Double(initXBorderSuperior,
                heigth_border_superior + initY, width_border_superior + initX,
                initXBorderSuperior);
        graphics2d.draw(line2dDT);

        if(moldDiente.equalsIgnoreCase(MOLD_5SECTORES)){
        	// agregamos el borde interno
            Rectangle2D borderInferior = new Rectangle2D.Double(
                    initXBorderInferior, initYBorderInferior, width_border_interno,
                    heigth_border_interno);
            graphics2d.draw(borderInferior);
            
            graphics2d.setColor(Color.WHITE);
            Rectangle2D borderInferiorFondo = new Rectangle2D.Double(
                    initXBorderInferior + 1, initYBorderInferior + 1,
                    width_border_interno - 1, heigth_border_interno - 1);
            graphics2d.fill(borderInferiorFondo);
        }

        // calculamos el centro y transport del diente, para hallar sectores
        yTransport = (heigth_border_interno / 2);
        xTransport = (width_border_interno / 2);
        centerY = initYBorderInferior + yTransport;
        centerX = initXBorderInferior + xTransport;

        // calculamos sectores
		calculateRightSector();
		calculateLeftSector();
        calculateTopSector();
        calculateBottomSector();
        calculateCenterSector();
        
		// actualizamos la imagen
		return bufferedImage_;
	}
	
	
	public int getWidthSpacio(){
		return width + (initX * 2);
	}
	
    public int getHeigthSpacio(){
		return heigth + (initY * 2);
	}

	/**
	 * Este metodo me permite calculas las coordenadas del sector derecho
	 * @author Luis Miguel
	 * */
    private void calculateRightSector() {
        xf_r = width_border_superior + initX;
        xi_r = centerX + xTransport;
        yf_r = centerY + yTransport;
        yi_r = centerY - yTransport;
    }

    /**
	 * Este metodo me permite calculas las coordenadas del sector izquierdo
	 * @author Luis Miguel
	 * */
    private void calculateLeftSector() {
        xf_l = centerX - xTransport;
        xi_l = initXBorderSuperior;
        yi_l = centerY - yTransport;
        yf_l = centerY + yTransport;
    }

    /**
	 * Este metodo me permite calculas las coordenadas del sector arriba
	 * @author Luis Miguel
	 * */
    private void calculateTopSector() {
        xf_t = centerX + xTransport;
        xi_t = centerX - xTransport;
        yi_t = initYBorderSuperior;
        yf_t = centerY - yTransport;
    }

    /**
	 * Este metodo me permite calculas las coordenadas del sector abajo
	 * @author Luis Miguel
	 * */
    private void calculateBottomSector() {
        xf_b = centerX + xTransport;
        xi_b = centerX - xTransport;
        yi_b = centerY + yTransport;
        yf_b = heigth_border_superior + initY;
    }

    /**
	 * Este metodo me permite calculas las coordenadas del sector centro
	 * @author Luis Miguel
	 * */
    private void calculateCenterSector() {
        xf_c = centerX + xTransport;
        xi_c = centerX - xTransport;
        yi_c = centerY - yTransport;
        yf_c = centerY + yTransport;
    }
    
	public ISelector getiSelector() {
		return iSelector;
	}

	public void setiSelector(ISelector iSelector) {
		this.iSelector = iSelector;
		
		// Este metodo es para la convencion lleve la instancia del diente
		if(iSelector instanceof Convencion){
			((Convencion)iSelector).setDienteMacro(this);
//			//log.info("@setiSelector Convencion localizada se realizo la inyeccion del diente");  
		}
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public OnClickDiente getOnClickDiente() {
		return onClickDiente;
	}

	public void setOnClickDiente(OnClickDiente onClickDiente) {
		this.onClickDiente = onClickDiente;
	}

	public String getMoldDiente() {
		return moldDiente;
	}
	
	public void setMoldDiente(String mold) {
		this.moldDiente = mold;
		draw();
	}
	
	/**
	 * Este metodo reinicia todo el diente.
	 * @author Luis Miguel
	 * */
	public void reset(){
		iSectorDienteBottom=
			iSectorDienteCenter= iSectorDienteLeft=
			iSectorDienteRigth= iSectorDienteTop = iSectorDienteSuperficieSuperior = null;
	    visibleSector = true;
		draw();
	}

	public boolean isAdulto() {
		return isAdulto;
	}

	public void setAdulto(boolean isAdulto) {
		this.isAdulto = isAdulto;
	}

	public ISectorDiente getiSectorDienteRigth() {
		return iSectorDienteRigth;
	}

	public void setiSectorDienteRigth(ISectorDiente iSectorDienteRigth) {
		this.iSectorDienteRigth = iSectorDienteRigth;
	}

	public ISectorDiente getiSectorDienteLeft() {
		return iSectorDienteLeft;
	}

	public void setiSectorDienteLeft(ISectorDiente iSectorDienteLeft) {
		this.iSectorDienteLeft = iSectorDienteLeft;
	}

	public ISectorDiente getiSectorDienteTop() {
		return iSectorDienteTop;
	}

	public void setiSectorDienteTop(ISectorDiente iSectorDienteTop) {
		this.iSectorDienteTop = iSectorDienteTop;
	}

	public ISectorDiente getiSectorDienteBottom() {
		return iSectorDienteBottom;
	}

	public void setiSectorDienteBottom(ISectorDiente iSectorDienteBottom) {
		this.iSectorDienteBottom = iSectorDienteBottom;
	}

	public ISectorDiente getiSectorDienteCenter() {
		return iSectorDienteCenter;
	}

	public void setiSectorDienteCenter(ISectorDiente iSectorDienteCenter) {
		this.iSectorDienteCenter = iSectorDienteCenter;
	}

	public ISuperficieTotal getiSectorDienteSuperficieSuperior() {
		return iSectorDienteSuperficieSuperior;
	}

	public void setiSectorDienteSuperficieSuperior(
			ISuperficieTotal iSectorDienteSuperficieSuperior) {
		this.iSectorDienteSuperficieSuperior = iSectorDienteSuperficieSuperior;
	}
	
	public boolean isParteSuperior() {
		return isParteSuperior;
	}

	public void setParteSuperior(boolean isParteSuperior) {
		this.isParteSuperior = isParteSuperior;
	}

	/**
	 * Este metodo me permite deshabilitar o habilitar el diente
	 * @author Luis Miguel
	 * */
	public void setDisabled(boolean estado){
		removeEventListener("onClick", eventListener);
		if(!estado) addEventListener("onClick", eventListener);
		if(estado){
			setStyle("cursor:default");
		}else{
			setStyle("cursor:pointer");
		}
	}
	
	/**
	 * Este metodo me grupa el desabilitar y limpiar
	 * @author Luis Miguel
	 * */
	public void resetAndDisabled(){
		setDisabled(true); 
		iSectorDienteBottom=
			iSectorDienteCenter= iSectorDienteLeft=
			iSectorDienteRigth= iSectorDienteTop = iSectorDienteSuperficieSuperior = null;
	    visibleSector = true;
		setContent(draw());
	}
	
	
	public boolean isMarcadoPorHacer(){
		ISectorDiente[] iSectorDientes = {iSectorDienteBottom, iSectorDienteCenter, iSectorDienteLeft, iSectorDienteRigth, iSectorDienteSuperficieSuperior, iSectorDienteTop};
		for (ISectorDiente iSectorDiente : iSectorDientes) {
			 if(iSectorDiente != null){
				 //log.info("Tipo sector:: " + iSectorDiente.getTypeSector()); 
				 if(continePorHacer(iSectorDiente.getTypeSector())){
					 return true;
				 } 
			 }
		}
		return false;
	}
 
	private boolean continePorHacer(TypeSector typeSector) {
		if(typeSector == TypeSector.SUPERFICIE_POR_SELLAR){
			return true;
		}else if(typeSector == TypeSector.AUSENTE){
			return true;
		}else if(typeSector == TypeSector.CORONA_DESADAPTADA){
			return true;
		}else if(typeSector == TypeSector.CARIES_OPTURACION){
			return true;
		}else if(typeSector == TypeSector.EXODONCIA_SIMPLE){
			return true;
		}else if(typeSector == TypeSector.ENDODONCIA_POR_RELLENAR){
			return true;
		}else if(typeSector == TypeSector.RESTAURACION_DESADAPTADA){
			return true;
		}
		return true;
	}

	public DienteMacro getDienteMacroNodoIzq() {
		return dienteMacroNodoIzq;
	}

	public void setDienteMacroNodoIzq(DienteMacro dienteMacroNodoIzq) {
		this.dienteMacroNodoIzq = dienteMacroNodoIzq;
	}

	public DienteMacro getDienteMacroNodoDer() {
		return dienteMacroNodoDer;
	}

	public void setDienteMacroNodoDer(DienteMacro dienteMacroNodoDer) {
		this.dienteMacroNodoDer = dienteMacroNodoDer;
	}

	public Graphics2D getGraphics2d() {
		return graphics2d;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	/**
	 * Este metodo me permite validar si ese diente esta utilizado,
	 * para guaradar el diente
	 * @author Luis Miguel
	 * */
	public boolean isUtilizado(){
		ISectorDiente[] sectores = { iSectorDienteBottom,
				iSectorDienteCenter, iSectorDienteLeft,
				iSectorDienteRigth, iSectorDienteTop, iSectorDienteSuperficieSuperior};
		for (ISectorDiente iSectorDiente : sectores) {
			if(iSectorDiente != null){
				return true;
			}
		}
		return false;
	}
}
