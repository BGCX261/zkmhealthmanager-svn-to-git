/*
 * detalle_ordenAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Laboratorios_respuestas;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Resultado_laboratorios;
import healthmanager.modelo.dao.Detalle_ordenDao;
import healthmanager.modelo.service.Detalle_ordenService;
import healthmanager.modelo.service.Laboratorios_respuestasService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Resultado_laboratoriosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
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
import org.zkoss.zul.Toolbarbutton;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Detalle_ordenAction extends ZKWindow{

	private Detalle_ordenService detalle_ordenService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;

	private final String[] idsExcluyentes = {};
	
	
	@View private Textbox tbxIdentificacionPaciente;
	@View private Textbox tbxTipoId;
	@View private Textbox tbxapellido1Paciente;
	@View private Textbox tbxapellido2paciente;
	@View private Textbox tbxnombre1Paciente;
	@View private Textbox tbxnombre2paciente;
	@View private Textbox tbxdirPaciente;
	@View private Textbox tbxtelpaciente;
	@View private Datebox tbxFechNacpaciente;
	
	@View private Textbox tbxObservaciones;
	
	@View private Listbox lbxEstado;
	
	@View Toolbarbutton btGuardar;
	
	
	@View private Textbox tbxAdministradora;
	
    // medico
	@View private Textbox tbxMedico;
	
	// laboratorio
	@View private Listbox lbxResultadoLaboratorio;


	private Map<String, Object> detalle_orden;
	
	

	
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
		listitem.setValue("dtt_orden.codigo_orden");
		listitem.setLabel("Nro orden");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("pac.nro_identificacion ||' '|| pac.nombre1||' '||pac.nombre2 ||' '|| pac.apellido1||' '||pac.apellido2");
		listitem.setLabel("Paciente");
		lbxParameter.appendChild(listitem);
		
		
		listitem = new Listitem();
		listitem.setValue("admin.codigo ||' '|| admin.nombre");
		listitem.setLabel("Administradora");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("us.codigo ||' '|| us.nombres ||' '|| us.apellidos");
		listitem.setLabel("Medico");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	
	private void cargamosDatosDelPaciente(String codigo_empresa, String codigo_sucursal, String nro_identificacion) {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(nro_identificacion);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			tbxIdentificacionPaciente.setText(""
					+ paciente.getNro_identificacion());
			tbxTipoId.setText("" + paciente.getTipo_identificacion());
			tbxapellido1Paciente.setText("" + paciente.getApellido1());
			tbxapellido2paciente.setText("" + paciente.getApellido2());
			tbxnombre1Paciente.setText("" + paciente.getNombre1());
			tbxnombre2paciente.setText("" + paciente.getNombre2());
			tbxdirPaciente.setText("" + paciente.getDireccion());
			tbxtelpaciente.setText("" + paciente.getTel_res());
			tbxFechNacpaciente.setValue(paciente.getFecha_nacimiento());
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
		lbxResultadoLaboratorio.setDisabled(false); 
		btGuardar.setDisabled(false); 
		detalle_orden = null;
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		boolean valida = true;
		lbxResultadoLaboratorio.setStyle("background-color:white");
		
		if(lbxResultadoLaboratorio.getSelectedIndex() == 0){
			lbxResultadoLaboratorio.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			Clients.scrollIntoView(lbxResultadoLaboratorio); 
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			parameters.put("realizado", lbxEstado.getSelectedItem().getValue());
			
			
			detalle_ordenService.setLimit("limit 25 offset 0");
			
			List<Map<String, Object>> lista_datos = detalle_ordenService.listarLaboratoriosRegistrado(parameters);
			rowsResultado.getChildren().clear(); 
			
			for (Map<String, Object> detalle_orden : lista_datos) {
				rowsResultado.appendChild(crearFilas(detalle_orden, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			
//			if(!lista_datos.isEmpty())
//			Clients.showNotification("Los laboratorios de color son marcados como no realizados.", gridResultado); 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		final Map<String, Object> detalle_orden = (Map<String, Object>)objeto;
		
		String codigo_procedimiento =  detalle_orden.get(Detalle_ordenDao.CODIGO_PROCEDIMIENTO) + ""; 
		
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(codigo_procedimiento));
		procedimientos = getServiceLocator().getServicio(ProcedimientosService.class).consultar(procedimientos);
		
		Timestamp fecha_orden = (Timestamp) detalle_orden.get(Detalle_ordenDao.FECHA_ORDEN);
		
		String nombre_paciente = detalle_orden.get(Detalle_ordenDao.NOMBRE_PACIENTE) + "";
		String nombre_procedimiento =  "";
		String medico  = detalle_orden.get(Detalle_ordenDao.NOMBRES_MEDICO) + "";
		
		if(procedimientos != null){
			nombre_procedimiento = procedimientos.getDescripcion();
		}
		
		String codigo_orden = detalle_orden.get(Detalle_ordenDao.CODIGO_ORDEN) + ""; 
		String realizado = detalle_orden.get(Detalle_ordenDao.REALIZADO) + "";
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		String add = "";
		if(realizado.equals("N")){add = "background-color:#FFBEB8";}
		
		fila.setStyle("text-align: justify;nowrap:nowrap;" + add);
		fila.appendChild(new Label(codigo_orden+ ""));
		fila.appendChild(new Label(nombre_procedimiento + ""));
		fila.appendChild(new Label(nombre_paciente + ""));
		fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(fecha_orden))); 
		fila.appendChild(new Label(medico));
		fila.appendChild(new Label(realizado.equals("S") ? "REALIZADO" : "PENDIENTES"));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/generar.png");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(detalle_orden);
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
								eliminarDatos(detalle_orden);
								buscarDatos();
							}
						}
					});
			}
		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				
				String codigo_empresa = detalle_orden.get(Detalle_ordenDao.CODIGO_EMPRESA) + "";
				String codigo_sucursal = detalle_orden.get(Detalle_ordenDao.CODIGO_SUCURSAL) + "";
				String codigo_orden = detalle_orden.get(Detalle_ordenDao.CODIGO_ORDEN) + "";
				String consecutivo = detalle_orden.get(Detalle_ordenDao.CONSECUTIVO) + ""; 
				String codigo_dx = detalle_orden.get(Detalle_ordenDao.CODIGO_DX) + ""; 
				String codigo_cups = detalle_orden.get(Detalle_ordenDao.CODIGO_CUPS) + "";
				String nro_identificacion_paciente = detalle_orden.get(Detalle_ordenDao.NRO_IDENTIFICACION) + ""; 
				
				Detalle_orden detalle_orden = new Detalle_orden();
				detalle_orden.setCodigo_empresa(codigo_empresa);
				detalle_orden.setCodigo_sucursal(codigo_sucursal);
//				detalle_orden.setCodigo_orden(codigo_orden);
				detalle_orden.setConsecutivo(consecutivo);
				detalle_orden = getServiceLocator().getDetalle_ordenService().consultar(detalle_orden);
				
				if(detalle_orden != null){
					detalle_orden.setRealizado("S");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("detalle", detalle_orden); 
					
					Resultado_laboratorios resultado_laboratorios = new Resultado_laboratorios();
					resultado_laboratorios.setCodigo_cups(codigo_cups);
					resultado_laboratorios.setCodigo_respuesta(lbxResultadoLaboratorio.getSelectedItem().getValue().toString());
					resultado_laboratorios.setCodigo_empresa(codigo_empresa);
					resultado_laboratorios.setCodigo_sucursal(codigo_sucursal);
					resultado_laboratorios.setNro_identificacion(nro_identificacion_paciente);
					resultado_laboratorios.setFecha_resultado(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					resultado_laboratorios.setCreacion_date(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					resultado_laboratorios.setUltimo_update(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					resultado_laboratorios.setCreacion_user(usuarios.getCodigo().toString());
					resultado_laboratorios.setUltimo_user(usuarios.getCodigo().toString());
					resultado_laboratorios.setCodigo_diagnostico(codigo_dx); 
					resultado_laboratorios.setObservaciones(tbxObservaciones.getValue());
					resultado_laboratorios.setCodigo_orden(codigo_orden); 
					
					map.put("resultado", resultado_laboratorios); 
					
					getServiceLocator().getServicio(Resultado_laboratoriosService.class).guardarDatos(map);
					
					int result = detalle_ordenService.actualizar(detalle_orden);
					if(result==0){
						throw new ValidacionRunTimeException("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}else{
					throw new ValidacionRunTimeException("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
				}
				
				MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
			}
		}catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());  
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{ 
		detalle_orden = (Map<String, Object>)obj; 
		lbxResultadoLaboratorio.setDisabled(false); 
		btGuardar.setDisabled(false); 
		try{
			/* bucamos paciente*/
			String codigo_empresa = detalle_orden.get(Detalle_ordenDao.CODIGO_EMPRESA) + "";
			String codigo_sucursal = detalle_orden.get(Detalle_ordenDao.CODIGO_SUCURSAL) + "";
			String nro_identificacion_paciente = detalle_orden.get(Detalle_ordenDao.NRO_IDENTIFICACION) + ""; 
			
			String codigo_orden = detalle_orden.get(Detalle_ordenDao.CODIGO_ORDEN) + "";
			cargamosDatosDelPaciente(codigo_empresa, codigo_sucursal, nro_identificacion_paciente);
			
			/* mostramo adminitradora */
			String codigo_administradora = detalle_orden.get(Detalle_ordenDao.CODIGO_ADMINISTRADORA) + "";
			String nombre_adminoistradora = detalle_orden.get(Detalle_ordenDao.NOMBRE_ADMINISTRADORA) + "";
			tbxAdministradora.setValue(codigo_administradora + " - " + nombre_adminoistradora);
			
			String codigo_medico = detalle_orden.get(Detalle_ordenDao.CODIGO_MEDICO) + "";
			String nombre_medico = detalle_orden.get(Detalle_ordenDao.NOMBRES_MEDICO) + "";
			tbxMedico.setValue(codigo_medico + " " + nombre_medico); 
			
			String codigo_cups = detalle_orden.get(Detalle_ordenDao.CODIGO_CUPS) + "";
			
			String realizado = detalle_orden.get(Detalle_ordenDao.REALIZADO) + "";
			
			if(realizado.equals("S")){
				if(parametros_empresa.getPermitir_cambiar_result_lab().equals("N")){
					btGuardar.setDisabled(true); 
					lbxResultadoLaboratorio.setDisabled(true); 
				}
				btGuardar.setLabel("Modificar resultado"); 
			}else{
				btGuardar.setLabel("Guardar resultado"); 
			}
			cargarRespuestasParaProcedimiento(codigo_empresa, codigo_sucursal, codigo_cups); 
			if(realizado.equals("S"))  
			cargarResultadoLaboratorio(codigo_empresa, codigo_sucursal, codigo_orden, nro_identificacion_paciente, codigo_cups); 
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	private void cargarRespuestasParaProcedimiento(String codigo_empresa, String codigo_cusursal, String codigo_cups) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_empresa", codigo_empresa);
		param.put("codigo_sucursal", codigo_cusursal);
		param.put("codigo_cups", codigo_cups);
		List<Laboratorios_respuestas> laboratorios_respuestas = getServiceLocator().getServicio(Laboratorios_respuestasService.class).listar(param);
		lbxResultadoLaboratorio.getItems().clear();
		lbxResultadoLaboratorio.appendChild(new Listitem(" -seleccione- ", ""));
		for (Laboratorios_respuestas laboratorios_respuestasTemp : laboratorios_respuestas) {
			lbxResultadoLaboratorio.appendChild(new Listitem(laboratorios_respuestasTemp.getDescripcion(), laboratorios_respuestasTemp.getCodigo_respuesta()));  
		}
		if(lbxResultadoLaboratorio.getItemCount() > 0){
			lbxResultadoLaboratorio.setSelectedIndex(0); 
		}
	}
	
	private void cargarResultadoLaboratorio(String codigo_empresa, String codigo_sucursal, String codigo_orden, String nro_identificacion, String codigo_cups){
		Resultado_laboratorios resultado_laboratorios = new Resultado_laboratorios();
		resultado_laboratorios.setCodigo_empresa(codigo_empresa);
		resultado_laboratorios.setCodigo_sucursal(codigo_sucursal);
		resultado_laboratorios.setCodigo_cups(codigo_cups);
		resultado_laboratorios.setNro_identificacion(nro_identificacion);
		resultado_laboratorios.setCodigo_orden(codigo_orden); 
		resultado_laboratorios = getServiceLocator().getServicio(Resultado_laboratoriosService.class).consultar(resultado_laboratorios);
		if(resultado_laboratorios != null){
			Utilidades.setValueFrom(lbxResultadoLaboratorio, resultado_laboratorios.getCodigo_respuesta()); 
		}
	}

	public void eliminarDatos(Object obj)throws Exception{
		Detalle_orden detalle_orden = (Detalle_orden)obj;
		try{
			int result = detalle_ordenService.eliminar(detalle_orden);
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