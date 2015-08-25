/*
 * rips_auAction.java
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
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Rips_au;
import contaweb.modelo.service.Rips_auService;

public class Rips_auAction extends ZKWindow{

	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxNro_factura;
	@View private Textbox tbxCodigo_factura;
	@View private Textbox tbxCodigo_prestador;
	@View private Textbox tbxTipo_id;
	@View private Textbox tbxNro_id;
	@View private Datebox dtbxFecha_ingreso;
	@View private Textbox tbxHora_ingreso;
	@View private Textbox tbxNro_autorizacion;
	@View private Textbox tbxCausa_externa;
	@View private Textbox tbxDiagnostico_salida;
	@View private Textbox tbxDiagnostico_relacionado_1_salida;
	@View private Textbox tbxDiagnostico_relacionado_2_salida;
	@View private Textbox tbxDiagnostico_relacionado_3_salida;
	@View private Textbox tbxDestino_usuario_salidad;
	@View private Textbox tbxEstado_salida;
	@View private Textbox tbxCausa_basica_muerte;
	@View private Textbox tbxFecha_salida;
	@View private Textbox tbxHora_salida;
	private final String[] idsExcluyentes = {};


	private Facturacion facturacion;
	
	private Rips_auService rips_auService;

	
	@Override
	public void init() throws Exception{
		listarCombos();
		buscarDatos();
	} 
	
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("nro_factura");
		listitem.setLabel("Nro_factura");
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
		
		tbxNro_factura.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_factura.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_prestador.setStyle("text-transform:uppercase;background-color:white");
		tbxTipo_id.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_id.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_ingreso.setStyle("background-color:white");
		tbxHora_ingreso.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_autorizacion.setStyle("text-transform:uppercase;background-color:white");
		tbxCausa_externa.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_salida.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_relacionado_1_salida.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_relacionado_2_salida.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_relacionado_3_salida.setStyle("text-transform:uppercase;background-color:white");
		tbxDestino_usuario_salidad.setStyle("text-transform:uppercase;background-color:white");
		tbxEstado_salida.setStyle("text-transform:uppercase;background-color:white");
		tbxCausa_basica_muerte.setStyle("text-transform:uppercase;background-color:white");
		tbxFecha_salida.setStyle("text-transform:uppercase;background-color:white");
		tbxHora_salida.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(tbxNro_factura.getText().trim().equals("")){
			tbxNro_factura.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_factura.getText().trim().equals("")){
			tbxCodigo_factura.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_prestador.getText().trim().equals("")){
			tbxCodigo_prestador.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxTipo_id.getText().trim().equals("")){
			tbxTipo_id.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxNro_id.getText().trim().equals("")){
			tbxNro_id.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dtbxFecha_ingreso.getText().trim().equals("")){
			dtbxFecha_ingreso.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxHora_ingreso.getText().trim().equals("")){
			tbxHora_ingreso.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxNro_autorizacion.getText().trim().equals("")){
			tbxNro_autorizacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCausa_externa.getText().trim().equals("")){
			tbxCausa_externa.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_salida.getText().trim().equals("")){
			tbxDiagnostico_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_relacionado_1_salida.getText().trim().equals("")){
			tbxDiagnostico_relacionado_1_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_relacionado_2_salida.getText().trim().equals("")){
			tbxDiagnostico_relacionado_2_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_relacionado_3_salida.getText().trim().equals("")){
			tbxDiagnostico_relacionado_3_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDestino_usuario_salidad.getText().trim().equals("")){
			tbxDestino_usuario_salidad.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxEstado_salida.getText().trim().equals("")){
			tbxEstado_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCausa_basica_muerte.getText().trim().equals("")){
			tbxCausa_basica_muerte.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxFecha_salida.getText().trim().equals("")){
			tbxFecha_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxHora_salida.getText().trim().equals("")){
			tbxHora_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...","Los campos marcados con (*) son obligatorios");
		}
		
		return valida;
	}
	
	@Override
	public void params(Map<String, Object> map) {
		facturacion = (Facturacion) map.get("fact");  
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
			parameters.put("nro_factura", facturacion.getDocumento_externo());
			
			rips_auService.setLimit("limit 25 offset 0");
			
			List<Rips_au> lista_datos = rips_auService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Rips_au rips_au : lista_datos) {
				rowsResultado.appendChild(crearFilas(rips_au, this));
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
		
		final Rips_au rips_au = (Rips_au)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_au.getTipo_id(), "tipo_id", getServiceLocator())+""));
		fila.appendChild(new Label(rips_au.getNro_id()+""));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy").format(rips_au.getFecha_ingreso())+""));
		fila.appendChild(new Label(rips_au.getHora_ingreso()+""));
		fila.appendChild(new Label(rips_au.getNro_autorizacion()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_au.getCausa_externa(), "causa_externa", getServiceLocator())+""));
		fila.appendChild(new Label(rips_au.getDiagnostico_salida()+""));
		fila.appendChild(new Label(rips_au.getDiagnostico_relacionado_1_salida()+""));
		fila.appendChild(new Label(rips_au.getDiagnostico_relacionado_2_salida()+""));
		fila.appendChild(new Label(rips_au.getDiagnostico_relacionado_3_salida()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_au.getDestino_usuario_salidad(), "destino_salida", getServiceLocator())+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_au.getEstado_salida(), "estado_salida", getServiceLocator())+""));
		fila.appendChild(new Label(rips_au.getCausa_basica_muerte()+""));
		fila.appendChild(new Label(rips_au.getFecha_salida()+""));
		fila.appendChild(new Label(rips_au.getHora_salida()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(rips_au);
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
								eliminarDatos(rips_au);
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
				
				Rips_au rips_au = new Rips_au();
				rips_au.setCodigo_empresa(empresa.getCodigo_empresa());
				rips_au.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				rips_au.setNro_factura(tbxNro_factura.getValue());
				rips_au.setCodigo_factura(tbxCodigo_factura.getValue());
				rips_au.setCodigo_prestador(tbxCodigo_prestador.getValue());
				rips_au.setTipo_id(tbxTipo_id.getValue());
				rips_au.setNro_id(tbxNro_id.getValue());
				rips_au.setFecha_ingreso(new Timestamp(dtbxFecha_ingreso.getValue().getTime()));
				rips_au.setHora_ingreso(tbxHora_ingreso.getValue());
				rips_au.setNro_autorizacion(tbxNro_autorizacion.getValue());
				rips_au.setCausa_externa(tbxCausa_externa.getValue());
				rips_au.setDiagnostico_salida(tbxDiagnostico_salida.getValue());
				rips_au.setDiagnostico_relacionado_1_salida(tbxDiagnostico_relacionado_1_salida.getValue());
				rips_au.setDiagnostico_relacionado_2_salida(tbxDiagnostico_relacionado_2_salida.getValue());
				rips_au.setDiagnostico_relacionado_3_salida(tbxDiagnostico_relacionado_3_salida.getValue());
				rips_au.setDestino_usuario_salidad(tbxDestino_usuario_salidad.getValue());
				rips_au.setEstado_salida(tbxEstado_salida.getValue());
				rips_au.setCausa_basica_muerte(tbxCausa_basica_muerte.getValue());
				rips_au.setFecha_salida(tbxFecha_salida.getValue());
				rips_au.setHora_salida(tbxHora_salida.getValue());
				rips_au.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_au.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_au.setCreacion_user(usuarios.getCodigo().toString());
				rips_au.setUltimo_user(usuarios.getCodigo().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					rips_auService.crear(rips_au);
					accionForm(true,"registrar");
				}else{
					int result = rips_auService.actualizar(rips_au);
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
		Rips_au rips_au = (Rips_au)obj;
		try{
			tbxNro_factura.setValue(rips_au.getNro_factura());
			tbxCodigo_factura.setValue(rips_au.getCodigo_factura());
			tbxCodigo_prestador.setValue(rips_au.getCodigo_prestador());
			tbxTipo_id.setValue(rips_au.getTipo_id());
			tbxNro_id.setValue(rips_au.getNro_id());
			dtbxFecha_ingreso.setValue(rips_au.getFecha_ingreso());
			tbxHora_ingreso.setValue(rips_au.getHora_ingreso());
			tbxNro_autorizacion.setValue(rips_au.getNro_autorizacion());
			tbxCausa_externa.setValue(rips_au.getCausa_externa());
			tbxDiagnostico_salida.setValue(rips_au.getDiagnostico_salida());
			tbxDiagnostico_relacionado_1_salida.setValue(rips_au.getDiagnostico_relacionado_1_salida());
			tbxDiagnostico_relacionado_2_salida.setValue(rips_au.getDiagnostico_relacionado_2_salida());
			tbxDiagnostico_relacionado_3_salida.setValue(rips_au.getDiagnostico_relacionado_3_salida());
			tbxDestino_usuario_salidad.setValue(rips_au.getDestino_usuario_salidad());
			tbxEstado_salida.setValue(rips_au.getEstado_salida());
			tbxCausa_basica_muerte.setValue(rips_au.getCausa_basica_muerte());
			tbxFecha_salida.setValue(rips_au.getFecha_salida());
			tbxHora_salida.setValue(rips_au.getHora_salida());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Rips_au rips_au = (Rips_au)obj;
		try{
			int result = rips_auService.eliminar(rips_au);
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