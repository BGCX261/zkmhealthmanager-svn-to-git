/*
 * rips_ahAction.java
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

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Rips_ah;
import contaweb.modelo.service.Rips_ahService;

public class Rips_ahAction extends ZKWindow{
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxNro_factura;
	@View private Intbox ibxId;
	@View private Textbox tbxCodigo_prestador;
	@View private Textbox tbxTipo_id;
	@View private Textbox tbxNro_id;
	@View private Textbox tbxVia_ingreso;
	@View private Textbox tbxFecha_ingreso;
	@View private Textbox tbxHora_ingreso;
	@View private Textbox tbxNro_autorizacion;
	@View private Textbox tbxCausa_externa;
	@View private Textbox tbxDiagnostico_principal_ingreso;
	@View private Textbox tbxDiagnostico_principal_egreso;
	@View private Textbox tbxDiagnostico_principal_ingreso_2;
	@View private Textbox tbxDiagnostico_principal_ingreso_3;
	@View private Textbox tbxDiagnostico_complicacion;
	@View private Textbox tbxEstado_salida;
	@View private Textbox tbxDiagnostico_causa_basica;
	@View private Datebox dtbxFecha_egreso;
	private final String[] idsExcluyentes = {};


	private Facturacion facturacion;
	
	private Rips_ahService rips_ahService;

	
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
		ibxId.setStyle("background-color:white");
		tbxCodigo_prestador.setStyle("text-transform:uppercase;background-color:white");
		tbxTipo_id.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_id.setStyle("text-transform:uppercase;background-color:white");
		tbxVia_ingreso.setStyle("text-transform:uppercase;background-color:white");
		tbxFecha_ingreso.setStyle("text-transform:uppercase;background-color:white");
		tbxHora_ingreso.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_autorizacion.setStyle("text-transform:uppercase;background-color:white");
		tbxCausa_externa.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_principal_ingreso.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_principal_egreso.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_principal_ingreso_2.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_principal_ingreso_3.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_complicacion.setStyle("text-transform:uppercase;background-color:white");
		tbxEstado_salida.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_causa_basica.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_egreso.setStyle("background-color:white");
		
		boolean valida = true;
		
		if(tbxNro_factura.getText().trim().equals("")){
			tbxNro_factura.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(ibxId.getText().trim().equals("")){
			ibxId.setStyle("background-color:#F6BBBE");
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
		if(tbxVia_ingreso.getText().trim().equals("")){
			tbxVia_ingreso.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxFecha_ingreso.getText().trim().equals("")){
			tbxFecha_ingreso.setStyle("text-transform:uppercase;background-color:#F6BBBE");
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
		if(tbxDiagnostico_principal_ingreso.getText().trim().equals("")){
			tbxDiagnostico_principal_ingreso.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_principal_egreso.getText().trim().equals("")){
			tbxDiagnostico_principal_egreso.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_principal_ingreso_2.getText().trim().equals("")){
			tbxDiagnostico_principal_ingreso_2.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_principal_ingreso_3.getText().trim().equals("")){
			tbxDiagnostico_principal_ingreso_3.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_complicacion.getText().trim().equals("")){
			tbxDiagnostico_complicacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxEstado_salida.getText().trim().equals("")){
			tbxEstado_salida.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_causa_basica.getText().trim().equals("")){
			tbxDiagnostico_causa_basica.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dtbxFecha_egreso.getText().trim().equals("")){
			dtbxFecha_egreso.setStyle("background-color:#F6BBBE");
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
			
			rips_ahService.setLimit("limit 25 offset 0");
			
			List<Rips_ah> lista_datos = rips_ahService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Rips_ah rips_ah : lista_datos) {
				rowsResultado.appendChild(crearFilas(rips_ah, this));
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
		
		final Rips_ah rips_ah = (Rips_ah)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_ah.getTipo_id(), "tipo_id", getServiceLocator())+""));
		fila.appendChild(new Label(rips_ah.getNro_id()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_ah.getVia_ingreso(), "via_ingreso", getServiceLocator())+""));
		fila.appendChild(new Label(rips_ah.getFecha_ingreso()+""));
		fila.appendChild(new Label(rips_ah.getHora_ingreso()+""));
		fila.appendChild(new Label(rips_ah.getNro_autorizacion()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_ah.getCausa_externa(), "causa_externa", getServiceLocator())+""));
		fila.appendChild(new Label(rips_ah.getDiagnostico_principal_ingreso()+""));
		fila.appendChild(new Label(rips_ah.getDiagnostico_principal_egreso()+""));
		fila.appendChild(new Label(rips_ah.getDiagnostico_principal_egreso_1()+""));
		fila.appendChild(new Label(rips_ah.getDiagnostico_principal_egreso_2()+""));
		fila.appendChild(new Label(rips_ah.getDiagnostico_principal_egreso_3()+""));
		fila.appendChild(new Label(rips_ah.getDiagnostico_complicacion()+""));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_ah.getEstado_salida(), "estado_salida", getServiceLocator())+""));
		fila.appendChild(new Label(rips_ah.getDiagnostico_causa_basica()+""));
		fila.appendChild(new Label(rips_ah.getFecha_egreso() != null ? new SimpleDateFormat("dd/MM/yyyy").format(rips_ah.getFecha_egreso()) : ""));
		fila.appendChild(new Label(rips_ah.getHora_egreso()+""));
		
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(rips_ah);
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
								eliminarDatos(rips_ah);
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
				
				Rips_ah rips_ah = new Rips_ah();
				rips_ah.setCodigo_empresa(empresa.getCodigo_empresa());
				rips_ah.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				rips_ah.setNro_factura(tbxNro_factura.getValue());
				rips_ah.setId((ibxId.getValue()!=null?ibxId.getValue():0));
				rips_ah.setCodigo_prestador(tbxCodigo_prestador.getValue());
				rips_ah.setTipo_id(tbxTipo_id.getValue());
				rips_ah.setNro_id(tbxNro_id.getValue());
				rips_ah.setVia_ingreso(tbxVia_ingreso.getValue());
				rips_ah.setFecha_ingreso(tbxFecha_ingreso.getValue());
				rips_ah.setHora_ingreso(tbxHora_ingreso.getValue());
				rips_ah.setNro_autorizacion(tbxNro_autorizacion.getValue());
				rips_ah.setCausa_externa(tbxCausa_externa.getValue());
				rips_ah.setDiagnostico_principal_ingreso(tbxDiagnostico_principal_ingreso.getValue());
				rips_ah.setDiagnostico_principal_egreso(tbxDiagnostico_principal_egreso.getValue());
				rips_ah.setDiagnostico_principal_egreso_2(tbxDiagnostico_principal_ingreso_2.getValue());
				rips_ah.setDiagnostico_principal_egreso_3(tbxDiagnostico_principal_ingreso_3.getValue());
				rips_ah.setDiagnostico_complicacion(tbxDiagnostico_complicacion.getValue());
				rips_ah.setEstado_salida(tbxEstado_salida.getValue());
				rips_ah.setDiagnostico_causa_basica(tbxDiagnostico_causa_basica.getValue());
				rips_ah.setFecha_egreso(new Timestamp(dtbxFecha_egreso.getValue().getTime()));
				rips_ah.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_ah.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_ah.setCreacion_user(usuarios.getCodigo().toString());
				rips_ah.setUltimo_user(usuarios.getCodigo().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					rips_ahService.crear(rips_ah);
					accionForm(true,"registrar");
				}else{
					int result = rips_ahService.actualizar(rips_ah);
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
		Rips_ah rips_ah = (Rips_ah)obj;
		try{
			tbxNro_factura.setValue(rips_ah.getNro_factura());
			ibxId.setValue(rips_ah.getId());
			tbxCodigo_prestador.setValue(rips_ah.getCodigo_prestador());
			tbxTipo_id.setValue(rips_ah.getTipo_id());
			tbxNro_id.setValue(rips_ah.getNro_id());
			tbxVia_ingreso.setValue(rips_ah.getVia_ingreso());
			tbxFecha_ingreso.setValue(rips_ah.getFecha_ingreso());
			tbxHora_ingreso.setValue(rips_ah.getHora_ingreso());
			tbxNro_autorizacion.setValue(rips_ah.getNro_autorizacion());
			tbxCausa_externa.setValue(rips_ah.getCausa_externa());
			tbxDiagnostico_principal_ingreso.setValue(rips_ah.getDiagnostico_principal_ingreso());
			tbxDiagnostico_principal_egreso.setValue(rips_ah.getDiagnostico_principal_egreso());
			tbxDiagnostico_principal_ingreso_2.setValue(rips_ah.getDiagnostico_principal_egreso_2());
			tbxDiagnostico_principal_ingreso_3.setValue(rips_ah.getDiagnostico_principal_egreso_3());
			tbxDiagnostico_complicacion.setValue(rips_ah.getDiagnostico_complicacion());
			tbxEstado_salida.setValue(rips_ah.getEstado_salida());
			tbxDiagnostico_causa_basica.setValue(rips_ah.getDiagnostico_causa_basica());
			dtbxFecha_egreso.setValue(rips_ah.getFecha_egreso());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Rips_ah rips_ah = (Rips_ah)obj;
		try{
			int result = rips_ahService.eliminar(rips_ah);
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