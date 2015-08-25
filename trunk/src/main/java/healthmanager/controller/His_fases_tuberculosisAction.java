/*
 * his_fases_tuberculosisAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_fases_tuberculosis;
import healthmanager.modelo.service.His_fases_tuberculosisService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
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
import com.framework.util.Utilidades;

public class His_fases_tuberculosisAction extends ZKWindow{

//	private static Logger log = Logger.getLogger(His_fases_tuberculosisAction.class);
	
	
	private His_fases_tuberculosisService his_fases_tuberculosisService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Datebox dtbxFecha;
	@View private Listbox lbxFase;
	@View private Textbox tbxObservacion;
//	private Map paramRequest;
	private final String[] idsExcluyentes = {};
	

	
	@Override
	public void init(){
		listarCombos();
	}
	
	public void listarCombos(){
		listarParameter();
		Utilidades.listarElementoListbox(lbxFase,true,getServiceLocator());
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//		
//		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
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
			
			his_fases_tuberculosisService.setLimit("limit 25 offset 0");
			
			List<His_fases_tuberculosis> lista_datos = his_fases_tuberculosisService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (His_fases_tuberculosis his_fases_tuberculosis : lista_datos) {
				rowsResultado.appendChild(crearFilas(his_fases_tuberculosis, this));
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
		
		final His_fases_tuberculosis his_fases_tuberculosis = (His_fases_tuberculosis)objeto;
		
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
				mostrarDatos(his_fases_tuberculosis);
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
								eliminarDatos(his_fases_tuberculosis);
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
				
				His_fases_tuberculosis his_fases_tuberculosis = new His_fases_tuberculosis();
				his_fases_tuberculosis.setCodigo_empresa(empresa.getCodigo_empresa());
				his_fases_tuberculosis.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				//his_fases_tuberculosis.setCodigo_historia(tbxCodigo_historia.getValue());
				//his_fases_tuberculosis.setIdentificacion(tbxIdentificacion.getValue());
				//his_fases_tuberculosis.setFecha(new Timestamp(dtbxFecha.getValue().getTime()));
				his_fases_tuberculosis.setFase(lbxFase.getSelectedItem().getValue().toString());
				his_fases_tuberculosis.setObservacion(tbxObservacion.getValue());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					his_fases_tuberculosisService.crear(his_fases_tuberculosis);
					accionForm(true,"registrar");
				}else{
					int result = his_fases_tuberculosisService.actualizar(his_fases_tuberculosis);
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
		His_fases_tuberculosis his_fases_tuberculosis = (His_fases_tuberculosis)obj;
		try{
			//tbxCodigo_historia.setValue(his_fases_tuberculosis.getCodigo_historia());
			//tbxIdentificacion.setValue(his_fases_tuberculosis.getIdentificacion());
			//dtbxFecha.setValue(his_fases_tuberculosis.getFecha());
			for(int i=0;i<lbxFase.getItemCount();i++){
				Listitem listitem = lbxFase.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(his_fases_tuberculosis.getFase())){
					listitem.setSelected(true);
					i = lbxFase.getItemCount();
				}
			}
			tbxObservacion.setValue(his_fases_tuberculosis.getObservacion());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		His_fases_tuberculosis his_fases_tuberculosis = (His_fases_tuberculosis)obj;
		try{
			int result = his_fases_tuberculosisService.eliminar(his_fases_tuberculosis);
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