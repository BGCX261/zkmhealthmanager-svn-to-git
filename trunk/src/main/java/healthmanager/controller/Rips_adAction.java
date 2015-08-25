/*
 * rips_adAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Doublebox;
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

import com.framework.constantes.IConstantesGlosas;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Rips_ad;
import contaweb.modelo.service.Rips_adService;

public class Rips_adAction extends ZKWindow{

	private Rips_adService rips_adService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_comprobante;
	@View private Textbox tbxCodigo_documento;
	@View private Intbox ibxId;
	@View private Textbox tbxCodigo_prestador;
	@View private Textbox tbxCodigo_concepto;
	@View private Intbox ibxCantidad;
	@View private Doublebox dbxValor_unitario;
	@View private Doublebox dbxValor_total;
	private final String[] idsExcluyentes = {};
	
	private Facturacion facturacion;

	
	@Override
	public void init() throws Exception{
		listarCombos();
		buscarDatos();
	}
	
	@Override
	public void params(Map<String, Object> map) {
		facturacion = (Facturacion) map.get("fact"); 
	}
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_comprobante");
		listitem.setLabel("Codigo_comprobante");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("codigo_documento");
		listitem.setLabel("Codigo_documento");
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("nro_factura", facturacion.getDocumento_externo());
			
			rips_adService.setLimit("limit 25 offset 0");
			
			List<Rips_ad> lista_datos = rips_adService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Rips_ad rips_ad : lista_datos) {
				rowsResultado.appendChild(crearFilas(rips_ad, this));
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
		
		final Rips_ad rips_ad = (Rips_ad)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_ad.getCodigo_concepto(), IConstantesGlosas.ELEMENTO_TIPO_CONCEPTO_RIPS_AD, getServiceLocator())));
		fila.appendChild(new Label(rips_ad.getCantidad() + "")); 
		fila.appendChild(new Label(new DecimalFormat("#,##0.##").format(rips_ad.getValor_unitario()))); 
		fila.appendChild(new Label(new DecimalFormat("#,##0.##").format(rips_ad.getValor_total())));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(rips_ad);
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
								eliminarDatos(rips_ad);
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
				
				Rips_ad rips_ad = new Rips_ad();
				rips_ad.setCodigo_empresa(empresa.getCodigo_empresa());
				rips_ad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				rips_ad.setCodigo_comprobante(tbxCodigo_comprobante.getValue());
				rips_ad.setCodigo_documento(tbxCodigo_documento.getValue());
				rips_ad.setId((ibxId.getValue()!=null?ibxId.getValue():0));
				rips_ad.setCodigo_prestador(tbxCodigo_prestador.getValue());
				rips_ad.setCodigo_concepto(tbxCodigo_concepto.getValue());
				rips_ad.setCantidad((ibxCantidad.getValue()!=null?ibxCantidad.getValue():0));
				rips_ad.setValor_unitario((dbxValor_unitario.getValue()!=null?dbxValor_unitario.getValue():0.00));
				rips_ad.setValor_total((dbxValor_total.getValue()!=null?dbxValor_total.getValue():0.00));
				rips_ad.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_ad.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_ad.setCreacion_user(usuarios.getCodigo().toString());
				rips_ad.setUltimo_user(usuarios.getCodigo().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					rips_adService.crear(rips_ad);
					accionForm(true,"registrar");
				}else{
					int result = rips_adService.actualizar(rips_ad);
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
		Rips_ad rips_ad = (Rips_ad)obj;
		try{
			tbxCodigo_comprobante.setValue(rips_ad.getCodigo_comprobante());
			tbxCodigo_documento.setValue(rips_ad.getCodigo_documento());
			ibxId.setValue(rips_ad.getId());
			tbxCodigo_prestador.setValue(rips_ad.getCodigo_prestador());
			tbxCodigo_concepto.setValue(rips_ad.getCodigo_concepto());
			ibxCantidad.setValue(rips_ad.getCantidad());
			dbxValor_unitario.setValue(rips_ad.getValor_unitario());
			dbxValor_total.setValue(rips_ad.getValor_total());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Rips_ad rips_ad = (Rips_ad)obj;
		try{
			int result = rips_adService.eliminar(rips_ad);
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