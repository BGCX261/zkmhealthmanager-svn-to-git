/*
 * rips_usAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;

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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
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

import contaweb.modelo.bean.Rips_us;
import contaweb.modelo.service.Rips_usService;

public class Rips_usAction extends ZKWindow{

	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	private Intbox ibxId;
	private Textbox tbxCodigo_administradora;
	private Textbox tbxTipo_usuario;
	private Textbox tbxPrimer_apellido;
	private Textbox tbxSegundo_apellido;
	private Textbox tbxPrimer_nombre;
	private Textbox tbxSegundo_nombre;
	private Textbox tbxEdad;
	private Textbox tbxUnidad_medida_edad;
	private Textbox tbxSexo;
	private Textbox tbxCodigo_dpto;
	private Textbox tbxCodigo_municipio;
	private Textbox tbxZona_residencia;
	private Textbox tbxTipo_id_usuario;
	private Textbox tbxIdentificacion;
	private final String[] idsExcluyentes = {};

	/* Este es el servicion */
	private Rips_usService rips_usService = null;
	
	
	@Override
	public void init() throws Exception{
		listarCombos();
	}
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Nro identificacion");
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
		
		ibxId.setStyle("background-color:white");
		tbxCodigo_administradora.setStyle("text-transform:uppercase;background-color:white");
		tbxTipo_usuario.setStyle("text-transform:uppercase;background-color:white");
		tbxPrimer_apellido.setStyle("text-transform:uppercase;background-color:white");
		tbxSegundo_apellido.setStyle("text-transform:uppercase;background-color:white");
		tbxPrimer_nombre.setStyle("text-transform:uppercase;background-color:white");
		tbxSegundo_nombre.setStyle("text-transform:uppercase;background-color:white");
		tbxEdad.setStyle("text-transform:uppercase;background-color:white");
		tbxUnidad_medida_edad.setStyle("text-transform:uppercase;background-color:white");
		tbxSexo.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_dpto.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_municipio.setStyle("text-transform:uppercase;background-color:white");
		tbxZona_residencia.setStyle("text-transform:uppercase;background-color:white");
		tbxTipo_id_usuario.setStyle("text-transform:uppercase;background-color:white");
		tbxIdentificacion.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(ibxId.getText().trim().equals("")){
			ibxId.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_administradora.getText().trim().equals("")){
			tbxCodigo_administradora.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxTipo_usuario.getText().trim().equals("")){
			tbxTipo_usuario.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxPrimer_apellido.getText().trim().equals("")){
			tbxPrimer_apellido.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxSegundo_apellido.getText().trim().equals("")){
			tbxSegundo_apellido.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxPrimer_nombre.getText().trim().equals("")){
			tbxPrimer_nombre.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxSegundo_nombre.getText().trim().equals("")){
			tbxSegundo_nombre.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxEdad.getText().trim().equals("")){
			tbxEdad.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxUnidad_medida_edad.getText().trim().equals("")){
			tbxUnidad_medida_edad.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxSexo.getText().trim().equals("")){
			tbxSexo.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_dpto.getText().trim().equals("")){
			tbxCodigo_dpto.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_municipio.getText().trim().equals("")){
			tbxCodigo_municipio.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxZona_residencia.getText().trim().equals("")){
			tbxZona_residencia.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxTipo_id_usuario.getText().trim().equals("")){
			tbxTipo_id_usuario.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxIdentificacion.getText().trim().equals("")){
			tbxIdentificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			
			rips_usService.setLimit("limit 25 offset 0");
			
			List<Rips_us> lista_datos = rips_usService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Rips_us rips_us : lista_datos) {
				rowsResultado.appendChild(crearFilas(rips_us, this));
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
		
		final Rips_us rips_us = (Rips_us)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_us.getTipo_id_usuario(), "tipo_id", getServiceLocator())+""));
		fila.appendChild(new Label(rips_us.getIdentificacion()+""));
		fila.appendChild(new Label(rips_us.getCodigo_administradora()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_us.getTipo_usuario(), "tipo_usuario", getServiceLocator())+""));
		fila.appendChild(new Label(rips_us.getPrimer_apellido()+""));
		fila.appendChild(new Label(rips_us.getSegundo_apellido()+""));
		fila.appendChild(new Label(rips_us.getPrimer_nombre()+""));
		fila.appendChild(new Label(rips_us.getSegundo_nombre()+""));
		fila.appendChild(new Label(rips_us.getEdad()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_us.getUnidad_medida_edad(), "unidad_medidad", getServiceLocator())+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_us.getSexo(), "sexo", getServiceLocator())+""));
		fila.appendChild(new Label(rips_us.getCodigo_dpto()+""));
		fila.appendChild(new Label(rips_us.getCodigo_municipio()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_us.getZona_residencia(), "zona", getServiceLocator())+""));
		
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(rips_us);
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
								eliminarDatos(rips_us);
								buscarDatos();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
//		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				Rips_us rips_us = new Rips_us();
				rips_us.setCodigo_empresa(empresa.getCodigo_empresa());
				rips_us.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				rips_us.setId((ibxId.getValue()!=null?ibxId.getValue():0));
				rips_us.setCodigo_administradora(tbxCodigo_administradora.getValue());
				rips_us.setTipo_usuario(tbxTipo_usuario.getValue());
				rips_us.setPrimer_apellido(tbxPrimer_apellido.getValue());
				rips_us.setSegundo_apellido(tbxSegundo_apellido.getValue());
				rips_us.setPrimer_nombre(tbxPrimer_nombre.getValue());
				rips_us.setSegundo_nombre(tbxSegundo_nombre.getValue());
				rips_us.setEdad(tbxEdad.getValue());
				rips_us.setUnidad_medida_edad(tbxUnidad_medida_edad.getValue());
				rips_us.setSexo(tbxSexo.getValue());
				rips_us.setCodigo_dpto(tbxCodigo_dpto.getValue());
				rips_us.setCodigo_municipio(tbxCodigo_municipio.getValue());
				rips_us.setZona_residencia(tbxZona_residencia.getValue());
				rips_us.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_us.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_us.setCreacion_user(usuarios.getCodigo().toString());
				rips_us.setUltimo_user(usuarios.getCodigo().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					rips_usService.crear(rips_us);
					accionForm(true,"registrar");
				}else{
					int result = rips_usService.actualizar(rips_us);
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
		Rips_us rips_us = (Rips_us)obj;
		try{
			ibxId.setValue(rips_us.getId());
			tbxCodigo_administradora.setValue(rips_us.getCodigo_administradora());
			tbxTipo_usuario.setValue(rips_us.getTipo_usuario());
			tbxPrimer_apellido.setValue(rips_us.getPrimer_apellido());
			tbxSegundo_apellido.setValue(rips_us.getSegundo_apellido());
			tbxPrimer_nombre.setValue(rips_us.getPrimer_nombre());
			tbxSegundo_nombre.setValue(rips_us.getSegundo_nombre());
			tbxEdad.setValue(rips_us.getEdad());
			tbxUnidad_medida_edad.setValue(rips_us.getUnidad_medida_edad());
			tbxSexo.setValue(rips_us.getSexo());
			tbxCodigo_dpto.setValue(rips_us.getCodigo_dpto());
			tbxCodigo_municipio.setValue(rips_us.getCodigo_municipio());
			tbxZona_residencia.setValue(rips_us.getZona_residencia());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Rips_us rips_us = (Rips_us)obj;
		try{
			int result = rips_usService.eliminar(rips_us);
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