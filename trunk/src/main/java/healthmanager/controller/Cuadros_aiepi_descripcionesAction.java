/*
 * cuadros_aiepi_descripcionesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cuadros_aiepi_descripciones;
import healthmanager.modelo.service.Cuadros_aiepi_descripcionesService;

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

public class Cuadros_aiepi_descripcionesAction extends ZKWindow{

//	private static Logger log = Logger.getLogger(Cuadros_aiepi_descripcionesAction.class);
	
	
	private Cuadros_aiepi_descripcionesService cuadros_aiepi_descripcionesService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCuadro;
	@View private Textbox tbxOpcion;
	@View private Textbox tbxEstado;
	@View private Textbox tbxDescripcion;
	private final String[] idsExcluyentes = {};

	
	
	public void init(ZKWindow zkWindow){
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
		listitem.setValue("opcion");
		listitem.setLabel("Opcion");
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
			
			List<Cuadros_aiepi_descripciones> lista_datos = cuadros_aiepi_descripcionesService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Cuadros_aiepi_descripciones cuadros_aiepi_descripciones : lista_datos) {
				rowsResultado.appendChild(crearFilas(cuadros_aiepi_descripciones, this));
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
		
		final Cuadros_aiepi_descripciones cuadros_aiepi_descripciones = (Cuadros_aiepi_descripciones)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(cuadros_aiepi_descripciones.getCuadro()+""));
		fila.appendChild(new Label(cuadros_aiepi_descripciones.getOpcion()+""));
		fila.appendChild(new Label(cuadros_aiepi_descripciones.getEstado()+""));
		fila.appendChild(new Label(cuadros_aiepi_descripciones.getDescripcion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(cuadros_aiepi_descripciones);
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
								eliminarDatos(cuadros_aiepi_descripciones);
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
				
				Cuadros_aiepi_descripciones cuadros_aiepi_descripciones = new Cuadros_aiepi_descripciones();
				cuadros_aiepi_descripciones.setCodigo_empresa(empresa.getCodigo_empresa());
				cuadros_aiepi_descripciones.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				cuadros_aiepi_descripciones.setCuadro(tbxCuadro.getValue());
				cuadros_aiepi_descripciones.setOpcion(tbxOpcion.getValue());
				cuadros_aiepi_descripciones.setEstado(tbxEstado.getValue());
				cuadros_aiepi_descripciones.setDescripcion(tbxDescripcion.getValue());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					cuadros_aiepi_descripcionesService.crear(cuadros_aiepi_descripciones);
					accionForm(true,"registrar");
				}else{
					int result = cuadros_aiepi_descripcionesService.actualizar(cuadros_aiepi_descripciones);
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
		Cuadros_aiepi_descripciones cuadros_aiepi_descripciones = (Cuadros_aiepi_descripciones)obj;
		try{
			tbxCuadro.setValue(cuadros_aiepi_descripciones.getCuadro());
			tbxOpcion.setValue(cuadros_aiepi_descripciones.getOpcion());
			tbxEstado.setValue(cuadros_aiepi_descripciones.getEstado());
			tbxDescripcion.setValue(cuadros_aiepi_descripciones.getDescripcion());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Cuadros_aiepi_descripciones cuadros_aiepi_descripciones = (Cuadros_aiepi_descripciones)obj;
		try{
			int result = cuadros_aiepi_descripcionesService.eliminar(cuadros_aiepi_descripciones);
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

	@Override
	public void init() throws Exception {
		listarCombos();
		
		
	}
}