/*
 * imagen_diagnosticaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package imagen_diagnostica.controller;


import healthmanager.controller.ZKWindow;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Paciente;
import imagen_diagnostica.modelo.bean.Imagen_diagnostica;

import java.io.File;
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

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.imagen_diagnostica.ImagenDiagnosticaVisualizador;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Imagen_diagnosticaAction extends ZKWindow{	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_sucuesal;
	@View private Textbox tbxNro_identificacion;
	@View private Datebox dtbxFecha_realizacion;
	@View private Textbox tbxLugar;
	@View private Intbox ibxId_cliente;
	@View private Intbox ibxId;
	@View private Textbox tbxDireccion_archivo;
	private final String[] idsExcluyentes = {};


	private Admision admision;


	private Paciente paciente;

	
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
		listitem.setValue("lugar");
		listitem.setLabel("Lugar realizacion");
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
	
	
	@Override 
	public void params(Map<String, Object> map) {
		admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
		paciente = new Paciente(); 
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(admision.getNro_identificacion()); 
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		tbxCodigo_sucuesal.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_realizacion.setStyle("background-color:white");
		tbxLugar.setStyle("text-transform:uppercase;background-color:white");
		ibxId_cliente.setStyle("background-color:white");
		ibxId.setStyle("background-color:white");
		tbxDireccion_archivo.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(tbxCodigo_sucuesal.getText().trim().equals("")){
			tbxCodigo_sucuesal.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxNro_identificacion.getText().trim().equals("")){
			tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dtbxFecha_realizacion.getText().trim().equals("")){
			dtbxFecha_realizacion.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxLugar.getText().trim().equals("")){
			tbxLugar.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(ibxId_cliente.getText().trim().equals("")){
			ibxId_cliente.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(ibxId.getText().trim().equals("")){
			ibxId.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDireccion_archivo.getText().trim().equals("")){
			tbxDireccion_archivo.setStyle("text-transform:uppercase;background-color:#F6BBBE");
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("nro_identificacion", admision.getNro_identificacion());
			parameters.put("value", "%"+value+"%");
			
			getServiceLocator().getImagen_diagnosticaService().setLimit("limit 25 offset 0");
			
			List<Imagen_diagnostica> lista_datos = getServiceLocator().getImagen_diagnosticaService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Imagen_diagnostica imagen_diagnostica : lista_datos) {
				rowsResultado.appendChild(crearFilas(imagen_diagnostica, this));
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
		
		final Imagen_diagnostica imagen_diagnostica = (Imagen_diagnostica)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(imagen_diagnostica.getFecha_realizacion()+""));
		fila.appendChild(new Label(imagen_diagnostica.getLugar()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/mostrar_info.png");
		img.setTooltiptext("Mostrar informacion de imágen diagnostica");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarImagenDiagnostica(imagen_diagnostica); 
			}
		});
		hbox.appendChild(img);
		
//		img = new Image();
//		img.setSrc("/images/borrar.gif");
//		img.setTooltiptext("Eliminar");
//		img.setStyle("cursor: pointer");
//		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				Messagebox.show("Esta seguro que desea eliminar este registro? ",
//					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
//					Messagebox.QUESTION,
//					new org.zkoss.zk.ui.event.EventListener<Event>() {
//						public void onEvent(Event event) throws Exception {
//							if ("onYes".equals(event.getName())) {
//								// do the thing
//								eliminarDatos(imagen_diagnostica);
//								buscarDatos();
//							}
//						}
//					});
//			}
//		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	protected void mostrarImagenDiagnostica(
			Imagen_diagnostica imagen_diagnostica) {
		String directorio = imagen_diagnostica.getDireccion_archivo();
		File file = new File(directorio);
		if(file.exists()){
			ImagenDiagnosticaVisualizador diagnosticaVisualizador = new ImagenDiagnosticaVisualizador();
			diagnosticaVisualizador.mostrarImagenDCM(file, paciente);
			diagnosticaVisualizador.setPage(getPage());
			diagnosticaVisualizador.setClosable(true); 
			diagnosticaVisualizador.doModal();
		}else{
			MensajesUtil.mensajeAlerta("Advertencia", "El examen realizado no ha sido encontrado.."); 
		}
	}

	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				Imagen_diagnostica imagen_diagnostica = new Imagen_diagnostica();
				imagen_diagnostica.setCodigo_empresa(empresa.getCodigo_empresa());
				imagen_diagnostica.setCodigo_sucursal(tbxCodigo_sucuesal.getValue());
				imagen_diagnostica.setNro_identificacion(tbxNro_identificacion.getValue());
				imagen_diagnostica.setFecha_realizacion(new Timestamp(dtbxFecha_realizacion.getValue().getTime()));
				imagen_diagnostica.setLugar(tbxLugar.getValue());
				imagen_diagnostica.setId_cliente((ibxId_cliente.getValue()!=null?ibxId_cliente.getValue():0));
				imagen_diagnostica.setId((ibxId.getValue()!=null?ibxId.getValue():0));
				imagen_diagnostica.setDireccion_archivo(tbxDireccion_archivo.getValue());
				imagen_diagnostica.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					getServiceLocator().getImagen_diagnosticaService().crear(imagen_diagnostica);
					accionForm(true,"registrar");
				}else{
					int result = getServiceLocator().getImagen_diagnosticaService().actualizar(imagen_diagnostica);
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
		Imagen_diagnostica imagen_diagnostica = (Imagen_diagnostica)obj;
		try{
			tbxCodigo_sucuesal.setValue(imagen_diagnostica.getCodigo_sucursal());
			tbxNro_identificacion.setValue(imagen_diagnostica.getNro_identificacion());
			dtbxFecha_realizacion.setValue(imagen_diagnostica.getFecha_realizacion());
			tbxLugar.setValue(imagen_diagnostica.getLugar());
			ibxId_cliente.setValue(imagen_diagnostica.getId_cliente());
			ibxId.setValue(imagen_diagnostica.getId());
			tbxDireccion_archivo.setValue(imagen_diagnostica.getDireccion_archivo());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Imagen_diagnostica imagen_diagnostica = (Imagen_diagnostica)obj;
		try{
			int result = getServiceLocator().getImagen_diagnosticaService().eliminar(imagen_diagnostica);
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