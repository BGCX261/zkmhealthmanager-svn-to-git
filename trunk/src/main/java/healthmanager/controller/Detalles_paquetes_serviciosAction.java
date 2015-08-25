/*
 * detalles_paquetes_serviciosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.service.Detalles_paquetes_serviciosService;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Detalles_paquetes_serviciosAction extends ZKWindow{

//	private static Logger log = Logger.getLogger(Detalles_paquetes_serviciosAction.class);
	
	
	private Detalles_paquetes_serviciosService detalles_paquetes_serviciosService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_paquete;
	@View private Textbox tbxConsecutivo;
	@View private Textbox tbxCodigo_detalle;
	@View private Textbox tbxTipo_detalle;
	private final String[] idsExcluyentes = {};

	
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
		listitem.setValue("codigo_paquete");
		listitem.setLabel("Codigo_paquete");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("consecutivo");
		listitem.setLabel("Consecutivo");
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
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		
		boolean valida = true;
		
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...","Los campos marcados con (*) son obligatorios");
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
			
			detalles_paquetes_serviciosService.setLimit("limit 25 offset 0");
			
			List<Detalles_paquetes_servicios> lista_datos = detalles_paquetes_serviciosService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Detalles_paquetes_servicios detalles_paquetes_servicios : lista_datos) {
				rowsResultado.appendChild(crearFilas(detalles_paquetes_servicios, this));
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
		
		final Detalles_paquetes_servicios detalles_paquetes_servicios = (Detalles_paquetes_servicios)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(detalles_paquetes_servicios);
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
								eliminarDatos(detalles_paquetes_servicios);
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
				
				Detalles_paquetes_servicios detalles_paquetes_servicios = new Detalles_paquetes_servicios();

				detalles_paquetes_servicios.setCodigo_detalle(tbxCodigo_detalle.getValue());
				detalles_paquetes_servicios.setTipo_detalle(tbxTipo_detalle.getValue());
				detalles_paquetes_servicios.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				detalles_paquetes_servicios.setCreacion_user(usuarios.getCodigo().toString());
			
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					detalles_paquetes_serviciosService.crear(detalles_paquetes_servicios);
					accionForm(true,"registrar");
				}else{
					int result = detalles_paquetes_serviciosService.actualizar(detalles_paquetes_servicios);
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				
		MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Detalles_paquetes_servicios detalles_paquetes_servicios = (Detalles_paquetes_servicios)obj;
		try{
	
			tbxCodigo_detalle.setValue(detalles_paquetes_servicios.getCodigo_detalle());
			tbxTipo_detalle.setValue(detalles_paquetes_servicios.getTipo_detalle());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Detalles_paquetes_servicios detalles_paquetes_servicios = (Detalles_paquetes_servicios)obj;
		try{
			int result = detalles_paquetes_serviciosService.eliminar(detalles_paquetes_servicios);
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
}