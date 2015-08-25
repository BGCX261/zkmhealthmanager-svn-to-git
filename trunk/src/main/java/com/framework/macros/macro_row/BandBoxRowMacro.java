package com.framework.macros.macro_row;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

import com.framework.constantes.IConstantes;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class BandBoxRowMacro extends Bandbox {
	
	private static Logger log = Logger.getLogger(BandBoxRowMacro.class);
   
	/**
	 * Por medio de esta interface se va ha poder configurar de que manera va ha trabajar el bandbox 
	 * de lista
	 * */
	private IConfiguracionBandbox configuracionBandbox;
	private Listbox mcListboxResultados;
	private Textbox mcTextboxValor;
	
	private Textbox mcTextboxInformacion;
	private Toolbarbutton mcButtonLimpiar;

	private Object registroSeleccionado;
	
	private static final String ITEM_ROWC = "$iTms_:";
	private String valor_bandbox_temporal = "";
	
	
	public BandBoxRowMacro() {
		setStyle("text-transform:uppercase;margin-right:5px;cursor:pointer");
		setReadonly(true); 
		addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mcTextboxValor.setValue(getValue());
				onConsultarRegistros();
			}
		});
		inicializarEventoSeguridad();
	}

	private void inicializarEventoSeguridad() {
		validarLimpieza(Events.ON_BLUR);
		validarLimpieza(Events.ON_CHANGE);
		validarLimpieza(Events.ON_CHANGING); 
	}

	private void validarLimpieza(String metodo){
		addEventListener(metodo, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if(getRegistroSeleccionado() != null &&
			    		!valor_bandbox_temporal.equals(getText())){ 
			    	limpiarSeleccion(true); 
			    }
			}
		});
	}
	
	/**
	 * Este metodo me va ha permitir, configurar las consultas
	 * y todo lo que valla a realizar
	 * */
	public BandBoxRowMacro configurar(IConfiguracionBandbox configuracionBandbox){
		getChildren().clear();
		this.configuracionBandbox =  configuracionBandbox;
		cargarConfiguracion(); 
		return this;
	}
	
	
	private void cargarConfiguracion() {
		if(configuracionBandbox != null){
			inicializarBanbox(); 
		}
	}

	/**
	 * Eventos para inicializar
	 * */
	public interface IConfiguracionBandbox<T>{
		public void onSeleccionar(T t, Bandbox bandbox);
		
		/**
		 * Para esta cabecera, se muestra de la siguiente forma
		 * 
		 * nombre#ancho + unidad (px) separados con |
		 * */
		public String getCabecera(Bandbox bandbox);
		
		
		public String getWidthListBox();
		
		
		public String getHeightListBox();
		
		
		public abstract void renderListitem(Listitem listitem, T registro, Bandbox bandbox);

		public abstract List<?> listarRegistros(String valorBusqueda,
				Map<String, Object> parametros, Bandbox bandbox);

		public abstract boolean seleccionarRegistro(Bandbox bandbox, T registro);
		
		public abstract void limpiarSeleccion(Bandbox bandbox);
		
	}
	
	
	
	public void agregarColumnas(String columnas, Listbox listbox) {
		listbox.getListhead().getChildren().clear();
		for (String valor : columnas.split("\\|")) {
			Listheader listheader = new Listheader();
			if (valor.contains("#")) {
				String vals[] = valor.split("\\#");
				listheader.setLabel(vals[0]);
				if (vals.length > 1)
					listheader.setWidth(vals[1]);
			} else {
				listheader.setLabel(valor);
			}
			listbox.getListhead().appendChild(listheader);
		}
	}
	
	private void inicializarBanbox() {
		Vbox vbox = new Vbox();
		
		/* Creamos cabecera */
		Div div= new Div();
		mcTextboxValor = new Textbox(); 
		
		div.appendChild(new Label("Busqueda: ")); 
		div.appendChild(mcTextboxValor);
		
		mcTextboxValor.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				onConsultarRegistros();
			}
		});
		
		Toolbarbutton toolbarbuttonBuscar = new Toolbarbutton();
		toolbarbuttonBuscar.setTooltiptext("Consultar registros");
		toolbarbuttonBuscar.setImage("/images/Magnifier.gif");
		toolbarbuttonBuscar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				 onConsultarRegistros();
			}
		});
		div.appendChild(toolbarbuttonBuscar);
		
		vbox.appendChild(div);
			
		/* Listbox */
		mcListboxResultados = new Listbox(); 
		mcListboxResultados.setMold("paging");
		mcListboxResultados.setPageSize(10); 
		mcListboxResultados.setHeight(configuracionBandbox.getHeightListBox()); 
		mcListboxResultados.setWidth(configuracionBandbox.getWidthListBox());
		mcListboxResultados.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event evts) throws Exception {
				Object object =  mcListboxResultados.getSelectedItem().getAttribute(ITEM_ROWC);
				seleccionarRegistro(object); 
			}
		});
		
		// Columnas del la cabecera
		Listhead listhead = new Listhead();
		mcListboxResultados.appendChild(listhead);
		agregarColumnas(configuracionBandbox.getCabecera(this), mcListboxResultados);
		vbox.appendChild(mcListboxResultados);
		
		/**
		 *  Adicionamos el popap
		 * */
		Bandpopup bandpopup = new Bandpopup();
		bandpopup.appendChild(vbox);
		appendChild(bandpopup);
	}
	
	
	public void seleccionarRegistro(Object registro, String codigo) {
		registroSeleccionado = registro;
		this.setValue(codigo);
		valor_bandbox_temporal = getValue();
		setTooltiptext(getValue()); 
		if(configuracionBandbox != null){
			configuracionBandbox.onSeleccionar(registro, this);
		}
	}
	
	
	public void onAbrirCerrarBandbox(Event event) {
		try {
			OpenEvent openEvent = (OpenEvent) event;
			if (!openEvent.isOpen()) {
				mcListboxResultados.getItems().clear();
				mcTextboxValor.setValue("");
			} else {
				onConsultarRegistros();
				mcTextboxValor.focus();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void onConsultarRegistros() {
		try {
			String valorBusqueda = mcTextboxValor.getValue().toLowerCase();
			mcListboxResultados.getItems().clear();
			Map<String, Object> parametros = new HashMap<String, Object>();
            //log.info("@onConsultarRegistros Consultar");   
			List<Object> listado = configuracionBandbox.listarRegistros(valorBusqueda, parametros, this);
			
			if(listado.isEmpty()){
				Notificaciones.mostrarNotificacionInformacion("Informacion", "No a devuelto ningun registro", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES); 
			}else if(listado.size() == 1){
				seleccionarRegistro(listado.get(0));    
			}else{
				for (Object registro : listado) {
					Listitem listitem = new Listitem();
					listitem.setAttribute(ITEM_ROWC, registro);
					configuracionBandbox.renderListitem(listitem, registro, this);
					listitem.setValue(registro);
					mcListboxResultados.appendChild(listitem);
				}
                setOpen(true);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}
	}
	
	

	private void seleccionarRegistro(Object registro) {
		   boolean select = configuracionBandbox.seleccionarRegistro(
				this, registro);
			if (select) {
				valor_bandbox_temporal = getValue();
				registroSeleccionado = registro;
				this.close();
				mcListboxResultados.getItems().clear();
				if(mcTextboxValor != null)mcTextboxValor.setValue("");
				if(mcButtonLimpiar != null)mcButtonLimpiar.setVisible(true);
				if(configuracionBandbox != null){
					configuracionBandbox.onSeleccionar(registro, this);
				}
			}
			setTooltiptext(getValue());
	}


	public void onSeleccionarRegistro() {
		try {
			Listitem listitem = mcListboxResultados.getSelectedItem();
			Object registro = listitem.getValue();
			if(registro != null){
				seleccionarRegistro(registro); 
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void limpiarSeleccion(boolean limpiarIMG) {
		try {
			FormularioUtil.limpiarComponentesRestricciones(this);
			if (limpiarIMG) {
				configuracionBandbox.limpiarSeleccion(this);
			}
			valor_bandbox_temporal = ""; 
			this.setValue("");
			registroSeleccionado = null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}


	public Listbox getMcListboxResultados() {
		return mcListboxResultados;
	}


	public void setMcListboxResultados(Listbox mcListboxResultados) {
		this.mcListboxResultados = mcListboxResultados;
	}


	public Textbox getMcTextboxValor() {
		return mcTextboxValor;
	}


	public void setMcTextboxValor(Textbox mcTextboxValor) {
		this.mcTextboxValor = mcTextboxValor;
	}


	public Textbox getMcTextboxInformacion() {
		return mcTextboxInformacion;
	}


	public void setMcTextboxInformacion(Textbox mcTextboxInformacion) {
		this.mcTextboxInformacion = mcTextboxInformacion;
	}


	public Object getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(Object registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
}
