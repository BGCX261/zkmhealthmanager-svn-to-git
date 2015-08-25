/*
 * modulo_firmasAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Modulo_firmas;
import healthmanager.modelo.bean.Modulo_firmas_reportes;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;
import healthmanager.modelo.service.Modulo_firmasService;
import healthmanager.modelo.service.Modulo_firmas_reportesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Modulo_firmasAction extends ZKWindow implements WindowRegistrosIMG{

	
	private Modulo_firmasService modulo_firmasService;
	private Modulo_firmas_reportesService modulo_firmas_reportesService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxNombre_firma;
	@View private Textbox tbxNro_identificacion_frima;
	@View private Rows rowServicios;
	
	public static final String REPORTE = "serv";
	
	@View
	private Image imageUsuario;
	
	private final String[] idsExcluyentes = {};

	private Modulo_firmas modulo_firmas;
	
	private List<String> lista_seleccionados_servicios = new ArrayList<String>();
	private List<Map<String, Object>> lista_datos_reportes = new ArrayList<Map<String,Object>>();

	
	@Override
	public void init(){
		listarCombos();
	}
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("nro_identificacion_frima");
		listitem.setLabel("Nro identificacion");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("nombre_firma");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
		lista_datos_reportes.clear();
		modulo_firmas = null;
		borrarImagen(); 
		rowServicios.getChildren().clear();
	}
	
	public void abrirWindowServicios() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "" + IConstantes.ELEMENTO_TIPO_REPORTE_CON_FIRMA); 
		String columnas = "Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Reportes", Paquetes.HEALTHMANAGER,
				"ElementoDao.listar", this, columnas, parametros);
		if(lista_seleccionados_servicios == null){
			lista_seleccionados_servicios = new ArrayList<String>();
		}
		windowRegistros.mostrarWindow(lista_seleccionados_servicios);
	}
	
	
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		try {
			FormularioUtil.validarCamposObligatorios(tbxNombre_firma, tbxNro_identificacion_frima); 
			if(imageUsuario.getContent() == null){
				valida = false;
				msj = "Para realizar esta accion debe cargar una firma!";
			}
			
			if(valida && lista_datos_reportes.isEmpty()){
				valida = false;
				msj = "Para realizar esta accion debe seleccionar por lo menos un reporte!";
			}
			
			if(!valida){
				MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...", msj);
			}
		} catch (WrongValueException e) {
			valida = false;
		}
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			modulo_firmasService.setLimit("limit 25 offset 0");
			
			List<Modulo_firmas> lista_datos = modulo_firmasService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Modulo_firmas modulo_firmas : lista_datos) {
				rowsResultado.appendChild(crearFilas(modulo_firmas, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Modulo_firmas modulo_firmas = (Modulo_firmas)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label("" + modulo_firmas.getNro_identificacion_frima()));
		fila.appendChild(new Label("" + modulo_firmas.getNombre_firma()));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(modulo_firmas);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(modulo_firmas);
								buscarDatos();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				Modulo_firmas modulo_firmas = new Modulo_firmas();
				modulo_firmas.setCodigo_empresa(empresa.getCodigo_empresa());
				modulo_firmas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				modulo_firmas.setNombre_firma(tbxNombre_firma.getValue());
				modulo_firmas.setFirma(imageUsuario.getContent() != null ? imageUsuario.getContent().getByteData() : null);
				modulo_firmas.setNro_identificacion_frima(tbxNro_identificacion_frima.getValue());
				if(this.modulo_firmas != null){
					modulo_firmas.setId(this.modulo_firmas.getId());
				}
				
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("lista_datos_reportes", lista_datos_reportes);
				parametros.put("modulo_firmas", modulo_firmas);
				parametros.put("accion", tbxAccion.getText());
				parametros.put("usuario", usuarios); 
				modulo_firmasService.guardar(parametros);
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					accionForm(true,"registrar");
				}
				
		        MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		modulo_firmas = (Modulo_firmas)obj; 
		try{
			borrarImagen();
			tbxNombre_firma.setValue(modulo_firmas.getNombre_firma());
			
			imageUsuario.setContent(new AImage("firma_usr", modulo_firmas.getFirma())); 
			tbxNro_identificacion_frima.setValue(modulo_firmas.getNro_identificacion_frima());
			
			
			// Cargamos los reportes correspondientes a la firma
			lista_datos_reportes.clear();
			rowServicios.getChildren().clear();
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("id_modulo_firma", modulo_firmas.getId()); 
			List<Modulo_firmas_reportes> firmas_reportes = modulo_firmas_reportesService.listar(parametros);
			for (Modulo_firmas_reportes modulo_firmas_reportes : firmas_reportes) {
				if(modulo_firmas_reportes.getElemento_reporte() != null){
					onSeleccionarReporte(modulo_firmas_reportes.getElemento_reporte());
				}
			}
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Modulo_firmas modulo_firmas = (Modulo_firmas)obj;
		try{
			int result = modulo_firmasService.eliminar(modulo_firmas);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
		}catch(RuntimeException r){
			MensajesUtil.mensajeError(r, "", this);
		}
	}
	
	/**
	 * Metodo para cargar la imagenes
	 * 
	 * @author EvanSandryHB
	 * */

	public void guardarImagen(Media media) throws Exception {
		try {
			if (media instanceof org.zkoss.image.Image) {
				AImage aImage = new AImage("firma_usr", media.getByteData());
				imageUsuario.setContent(aImage);
			} else {
				Messagebox.show("No es una foto: " + media, "Error",
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error cargando foto!!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * Permite eliminar la imagen de la firma cargada
	 * */
	public void borrarImagen() throws Exception {
		imageUsuario.setSrc("");
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			//log.info("Registro seleccionado: " + registro);
			if (registro instanceof Elemento) {
				onSeleccionarReporte((Elemento) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Elemento){
			return celdasListitemReportes((Elemento)registro);
		}
		return null; 
	}
	
	private Object[] celdasListitemReportes(Elemento registro) {
		return new Object[]{registro.getDescripcion().toUpperCase()};
	}
	
	private void onSeleccionarReporte(Elemento registro) {
	      adicionarDetalleListaReportes(cargarServicio(registro)); 
	}
	
	private Map<String, Object> cargarServicio(Elemento elementoReporte) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(REPORTE, elementoReporte);
		return bean;
	}
	
	private void adicionarDetalleListaReportes(Map<String, Object> bean) {
		try {
			crearFilasReportes(bean); 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	private void crearFilasReportes(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_reportes.add(bean);
		final Elemento reportes = (Elemento) bean.get(REPORTE);
		 
		lista_seleccionados_servicios.add(reportes.toString());
		
		row.setValue(reportes);
		
		Cell cell = new Cell();
		Label label = new Label("" + reportes.getDescripcion().toUpperCase());
		cell.appendChild(label);
		row.appendChild(cell);
		
		
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif"); 
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				 Messagebox.show("¿Estas seguro que deseas eliminar estos registros?", "Advertencia", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {
								@Override
								public void onEvent(Event event) throws Exception {
									if("onYes".equals(event.getName())){
									     lista_datos_reportes.remove(bean);
										 rowServicios.removeChild(row);
									}
								}
							});
			}
		});
		rowServicios.appendChild(row); 
	}
}