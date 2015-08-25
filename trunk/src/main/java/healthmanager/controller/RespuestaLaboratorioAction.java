/*
 * orden_servicioAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.service.Orden_servicioService;

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
import org.zkoss.zul.Checkbox;
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

public class RespuestaLaboratorioAction extends ZKWindow{

//	private static Logger log = Logger.getLogger(Orden_servicioAction.class);
	
	
	private Orden_servicioService orden_servicioService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_orden;
	@View private Datebox dtbxFecha_orden;
	@View private Textbox tbxNro_ingreso;
	@View private Textbox tbxCodigo_paciente;
	@View private Textbox tbxCodigo_administradora;
	@View private Textbox tbxId_plan;
	@View private Textbox tbxCodigo_ordenador;
	@View private Textbox tbxCodigo_dx;
	@View private Textbox tbxId_prestador;
	@View private Textbox tbxEstado;
	@View private Textbox tbxFacturacion;
	@View private Datebox dtbxDelete_date;
	@View private Textbox tbxDelete_user;
	@View private Checkbox chbActualizado;
	@View private Textbox tbxTipo_hc;
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
		listitem.setValue("codigo_orden");
		listitem.setLabel("Codigo_orden");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("fecha_orden");
		listitem.setLabel("Fecha_orden");
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
			
			orden_servicioService.setLimit("limit 25 offset 0");
			
			List<Orden_servicio> lista_datos = orden_servicioService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Orden_servicio orden_servicio : lista_datos) {
				rowsResultado.appendChild(crearFilas(orden_servicio, this));
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
		
		final Orden_servicio orden_servicio = (Orden_servicio)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(orden_servicio.getCodigo_orden()+""));
		fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd").format(orden_servicio.getFecha_orden()))); 
		fila.appendChild(new Label(orden_servicio.getNro_ingreso()+""));
		fila.appendChild(new Label(orden_servicio.getCodigo_paciente()+""));
		fila.appendChild(new Label(orden_servicio.getCodigo_administradora()+""));
		fila.appendChild(new Label(orden_servicio.getId_plan()+""));
		fila.appendChild(new Label(orden_servicio.getCodigo_ordenador()+""));
		fila.appendChild(new Label(orden_servicio.getCodigo_dx()+""));
		fila.appendChild(new Label(orden_servicio.getId_prestador()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/generar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(orden_servicio);
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
								eliminarDatos(orden_servicio);
								buscarDatos();
							}
						}
					});
			}
		});
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				Orden_servicio orden_servicio = new Orden_servicio();
				orden_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
				orden_servicio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				orden_servicio.setCodigo_orden(tbxCodigo_orden.getValue());
				orden_servicio.setFecha_orden(new Timestamp(dtbxFecha_orden.getValue().getTime()));
				orden_servicio.setNro_ingreso(tbxNro_ingreso.getValue());
				orden_servicio.setCodigo_paciente(tbxCodigo_paciente.getValue());
				orden_servicio.setCodigo_administradora(tbxCodigo_administradora.getValue());
				orden_servicio.setId_plan(tbxId_plan.getValue());
				orden_servicio.setCodigo_ordenador(tbxCodigo_ordenador.getValue());
				orden_servicio.setCodigo_dx(tbxCodigo_dx.getValue());
				orden_servicio.setId_prestador(tbxId_prestador.getValue());
				orden_servicio.setEstado(tbxEstado.getValue());
				orden_servicio.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				orden_servicio.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				orden_servicio.setDelete_date(new Timestamp(dtbxDelete_date.getValue().getTime()));
				orden_servicio.setCreacion_user(usuarios.getCodigo().toString());
				orden_servicio.setUltimo_user(usuarios.getCodigo().toString());
				orden_servicio.setDelete_user(tbxDelete_user.getValue());
//				orden_servicio.setActualizado(chbActualizado.isChecked()?"S":"N");
				orden_servicio.setTipo_hc(tbxTipo_hc.getValue());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					orden_servicioService.crear(orden_servicio);
					accionForm(true,"registrar");
				}else{
					int result = orden_servicioService.actualizar(orden_servicio);
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
		Orden_servicio orden_servicio = (Orden_servicio)obj;
		try{
			tbxCodigo_orden.setValue(orden_servicio.getCodigo_orden());
			dtbxFecha_orden.setValue(orden_servicio.getFecha_orden());
			tbxNro_ingreso.setValue(orden_servicio.getNro_ingreso());
			tbxCodigo_paciente.setValue(orden_servicio.getCodigo_paciente());
			tbxCodigo_administradora.setValue(orden_servicio.getCodigo_administradora());
			tbxId_plan.setValue(orden_servicio.getId_plan());
			tbxCodigo_ordenador.setValue(orden_servicio.getCodigo_ordenador());
			tbxCodigo_dx.setValue(orden_servicio.getCodigo_dx());
			tbxId_prestador.setValue(orden_servicio.getId_prestador());
			tbxEstado.setValue(orden_servicio.getEstado());
			dtbxDelete_date.setValue(orden_servicio.getDelete_date());
			tbxDelete_user.setValue(orden_servicio.getDelete_user());
//			chbActualizado.setChecked(orden_servicio.getActualizado().equals("S"));
			tbxTipo_hc.setValue(orden_servicio.getTipo_hc());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Orden_servicio orden_servicio = (Orden_servicio)obj;
		try{
			int result = orden_servicioService.eliminar(orden_servicio);
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