/*
 * cuadros_aiepi_estadoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuadros_aiepi_estado;
import healthmanager.modelo.service.Cuadros_aiepi_estadoService;

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

public class Cuadros_aiepi_estadoAction extends ZKWindow{

//	private static Logger log = Logger.getLogger(Cuadros_aiepi_estadoAction.class);
	
	
	private Cuadros_aiepi_estadoService cuadros_aiepi_estadoService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCuadro;
	@View private Textbox tbxEstado;
	@View private Textbox tbxDescripcion;
	@View private Textbox tbxSugerencias;
	@View private Textbox tbx_via_ingreso;
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
		listitem.setValue("cuadro");
		listitem.setLabel("Cuadro");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("estado");
		listitem.setLabel("Estado");
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
			
			parameters.put("limite_paginado", "limit 25 offset 0");
			
			List<Cuadros_aiepi_estado> lista_datos = cuadros_aiepi_estadoService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Cuadros_aiepi_estado cuadros_aiepi_estado : lista_datos) {
				rowsResultado.appendChild(crearFilas(cuadros_aiepi_estado, this));
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
		
		final Cuadros_aiepi_estado cuadros_aiepi_estado = (Cuadros_aiepi_estado)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(cuadros_aiepi_estado.getCuadro()+""));
		fila.appendChild(new Label(cuadros_aiepi_estado.getEstado()+""));
		fila.appendChild(new Label(cuadros_aiepi_estado.getDescripcion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(cuadros_aiepi_estado);
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
								eliminarDatos(cuadros_aiepi_estado);
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
				
				Cuadros_aiepi_estado cuadros_aiepi_estado = new Cuadros_aiepi_estado();
				cuadros_aiepi_estado.setCodigo_empresa(empresa.getCodigo_empresa());
				cuadros_aiepi_estado.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				cuadros_aiepi_estado.setCuadro(tbxCuadro.getValue());
				cuadros_aiepi_estado.setEstado(tbxEstado.getValue());
				cuadros_aiepi_estado.setDescripcion(tbxDescripcion.getValue());
				cuadros_aiepi_estado.setVia_ingreso(tbx_via_ingreso.getValue());
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					cuadros_aiepi_estadoService.crear(cuadros_aiepi_estado);
					accionForm(true,"registrar");
				}else{
					int result = cuadros_aiepi_estadoService.actualizar(cuadros_aiepi_estado);
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
		Cuadros_aiepi_estado cuadros_aiepi_estado = (Cuadros_aiepi_estado)obj;
		try{
			tbxCuadro.setValue(cuadros_aiepi_estado.getCuadro());
			tbxEstado.setValue(cuadros_aiepi_estado.getEstado());
			tbxDescripcion.setValue(cuadros_aiepi_estado.getDescripcion());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Cuadros_aiepi_estado cuadros_aiepi_estado = (Cuadros_aiepi_estado)obj;
		try{
			int result = cuadros_aiepi_estadoService.eliminar(cuadros_aiepi_estado);
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