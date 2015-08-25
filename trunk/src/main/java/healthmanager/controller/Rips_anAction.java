/*
 * rips_anAction.java
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

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Rips_an;
import contaweb.modelo.service.Rips_anService;

public class Rips_anAction extends ZKWindow{

	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Intbox ibxId;
	@View private Textbox tbxNro_factura;
	@View private Textbox tbxCodigo_factur;
	@View private Textbox tbxCodigo_prestador;
	@View private Textbox tbxTipo_id_madre;
	@View private Textbox tbxNro_id_madre;
	@View private Textbox tbxFecha_nacimiento;
	@View private Textbox tbxHora_nacimiento;
	@View private Textbox tbxEdad_gestacional;
	@View private Textbox tbxControl_prenatal;
	@View private Textbox tbxSexo;
	@View private Textbox tbxPeso;
	@View private Textbox tbxDiagnostico_recien_nacido;
	@View private Textbox tbxCausa_basica_muerte;
	@View private Textbox tbxFecha_muerte;
	@View private Textbox tbxHora_muerte_recien_nacido;
	private final String[] idsExcluyentes = {};


	private Facturacion facturacion;
	private Rips_anService rips_anService;

	
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
		
		ibxId.setStyle("background-color:white");
		tbxNro_factura.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_factur.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_prestador.setStyle("text-transform:uppercase;background-color:white");
		tbxTipo_id_madre.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_id_madre.setStyle("text-transform:uppercase;background-color:white");
		tbxFecha_nacimiento.setStyle("text-transform:uppercase;background-color:white");
		tbxHora_nacimiento.setStyle("text-transform:uppercase;background-color:white");
		tbxEdad_gestacional.setStyle("text-transform:uppercase;background-color:white");
		tbxControl_prenatal.setStyle("text-transform:uppercase;background-color:white");
		tbxSexo.setStyle("text-transform:uppercase;background-color:white");
		tbxPeso.setStyle("text-transform:uppercase;background-color:white");
		tbxDiagnostico_recien_nacido.setStyle("text-transform:uppercase;background-color:white");
		tbxCausa_basica_muerte.setStyle("text-transform:uppercase;background-color:white");
		tbxFecha_muerte.setStyle("text-transform:uppercase;background-color:white");
		tbxHora_muerte_recien_nacido.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(ibxId.getText().trim().equals("")){
			ibxId.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxNro_factura.getText().trim().equals("")){
			tbxNro_factura.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_factur.getText().trim().equals("")){
			tbxCodigo_factur.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_prestador.getText().trim().equals("")){
			tbxCodigo_prestador.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxTipo_id_madre.getText().trim().equals("")){
			tbxTipo_id_madre.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxNro_id_madre.getText().trim().equals("")){
			tbxNro_id_madre.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxFecha_nacimiento.getText().trim().equals("")){
			tbxFecha_nacimiento.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxHora_nacimiento.getText().trim().equals("")){
			tbxHora_nacimiento.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxEdad_gestacional.getText().trim().equals("")){
			tbxEdad_gestacional.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxControl_prenatal.getText().trim().equals("")){
			tbxControl_prenatal.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxSexo.getText().trim().equals("")){
			tbxSexo.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxPeso.getText().trim().equals("")){
			tbxPeso.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDiagnostico_recien_nacido.getText().trim().equals("")){
			tbxDiagnostico_recien_nacido.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCausa_basica_muerte.getText().trim().equals("")){
			tbxCausa_basica_muerte.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxFecha_muerte.getText().trim().equals("")){
			tbxFecha_muerte.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxHora_muerte_recien_nacido.getText().trim().equals("")){
			tbxHora_muerte_recien_nacido.setStyle("text-transform:uppercase;background-color:#F6BBBE");
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

			rips_anService.setLimit("limit 25 offset 0");
			
			List<Rips_an> lista_datos = rips_anService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Rips_an rips_an : lista_datos) {
				rowsResultado.appendChild(crearFilas(rips_an, this));
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
		
		final Rips_an rips_an = (Rips_an)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_an.getTipo_id_madre(), "tipo_id", getServiceLocator())+""));
		fila.appendChild(new Label(rips_an.getNro_id_madre()+""));
		fila.appendChild(new Label(rips_an.getFecha_nacimiento()+""));
		fila.appendChild(new Label(rips_an.getHora_nacimiento()+""));
		fila.appendChild(new Label(rips_an.getEdad_gestacional()+""));
		fila.appendChild(new Label(rips_an.getControl_prenatal().equals("1") ? "SI" : "NO"));
		fila.appendChild(new Label(Utilidades.getDescripcionElemento(rips_an.getSexo(), "sexo", getServiceLocator())+""));
		fila.appendChild(new Label(rips_an.getPeso()+""));
		fila.appendChild(new Label(rips_an.getDiagnostico_recien_nacido()+""));
		fila.appendChild(new Label(rips_an.getCausa_basica_muerte()+""));
		fila.appendChild(new Label(rips_an.getFecha_muerte()+""));
		fila.appendChild(new Label(rips_an.getHora_muerte_recien_nacido()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(rips_an);
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
								eliminarDatos(rips_an);
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
				
				Rips_an rips_an = new Rips_an();
				rips_an.setCodigo_empresa(empresa.getCodigo_empresa());
				rips_an.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				rips_an.setId((ibxId.getValue()!=null?ibxId.getValue():0));
				rips_an.setNro_factura(tbxNro_factura.getValue());
				rips_an.setCodigo_factur(tbxCodigo_factur.getValue());
				rips_an.setCodigo_prestador(tbxCodigo_prestador.getValue());
				rips_an.setTipo_id_madre(tbxTipo_id_madre.getValue());
				rips_an.setNro_id_madre(tbxNro_id_madre.getValue());
				rips_an.setFecha_nacimiento(tbxFecha_nacimiento.getValue());
				rips_an.setHora_nacimiento(tbxHora_nacimiento.getValue());
				rips_an.setEdad_gestacional(tbxEdad_gestacional.getValue());
				rips_an.setControl_prenatal(tbxControl_prenatal.getValue());
				rips_an.setSexo(tbxSexo.getValue());
				rips_an.setPeso(tbxPeso.getValue());
				rips_an.setDiagnostico_recien_nacido(tbxDiagnostico_recien_nacido.getValue());
				rips_an.setCausa_basica_muerte(tbxCausa_basica_muerte.getValue());
				rips_an.setFecha_muerte(tbxFecha_muerte.getValue());
				rips_an.setHora_muerte_recien_nacido(tbxHora_muerte_recien_nacido.getValue());
				rips_an.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_an.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				rips_an.setCreacion_user(usuarios.getCodigo().toString());
				rips_an.setUltimo_user(usuarios.getCodigo().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					rips_anService.crear(rips_an);
					accionForm(true,"registrar");
				}else{
					int result = rips_anService.actualizar(rips_an);
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
		Rips_an rips_an = (Rips_an)obj;
		try{
			ibxId.setValue(rips_an.getId());
			tbxNro_factura.setValue(rips_an.getNro_factura());
			tbxCodigo_factur.setValue(rips_an.getCodigo_factur());
			tbxCodigo_prestador.setValue(rips_an.getCodigo_prestador());
			tbxTipo_id_madre.setValue(rips_an.getTipo_id_madre());
			tbxNro_id_madre.setValue(rips_an.getNro_id_madre());
			tbxFecha_nacimiento.setValue(rips_an.getFecha_nacimiento());
			tbxHora_nacimiento.setValue(rips_an.getHora_nacimiento());
			tbxEdad_gestacional.setValue(rips_an.getEdad_gestacional());
			tbxControl_prenatal.setValue(rips_an.getControl_prenatal());
			tbxSexo.setValue(rips_an.getSexo());
			tbxPeso.setValue(rips_an.getPeso());
			tbxDiagnostico_recien_nacido.setValue(rips_an.getDiagnostico_recien_nacido());
			tbxCausa_basica_muerte.setValue(rips_an.getCausa_basica_muerte());
			tbxFecha_muerte.setValue(rips_an.getFecha_muerte());
			tbxHora_muerte_recien_nacido.setValue(rips_an.getHora_muerte_recien_nacido());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Rips_an rips_an = (Rips_an)obj;
		try{
			int result = rips_anService.eliminar(rips_an);
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