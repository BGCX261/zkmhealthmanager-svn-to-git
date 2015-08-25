/*
 * resultado_paraclinicoAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */ 
package healthmanager.controller;


import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.*;

public class Resultado_paraclinicoAction extends ZKWindow{

	private static Logger log = Logger.getLogger(Resultado_paraclinicoAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_historia;
	@View private Textbox tbxConsecutivo;
	@View private Textbox tbxCodigo_paraclinico;
	@View private Datebox dtbxFecha;
	@View private Textbox tbxResultado;
	
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
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//		
//		listitem = new Listitem();
		listitem.setValue("consecutivo");
		listitem.setLabel("Consecutivo");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	public void listarElementoListbox(Listbox listbox,boolean select){
		listbox.getChildren().clear();
		Listitem listitem;
		if(select){
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		
		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator().getElementoService().listar(tipo);
		
		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
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
	
	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent).setText(((Textbox) abstractComponent).getText().trim().toUpperCase());
				} 
			}
		}
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection){
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")){
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals("lbxParameter")){
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio)((Radiogroup) abstractComponent).getFirstChild()).setChecked(true);
			}
		}
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		tbxCodigo_historia.setStyle("text-transform:uppercase;background-color:white");
		tbxConsecutivo.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_paraclinico.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha.setStyle("background-color:white");
		tbxResultado.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(tbxCodigo_historia.getText().trim().equals("")){
			tbxCodigo_historia.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxConsecutivo.getText().trim().equals("")){
			tbxConsecutivo.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_paraclinico.getText().trim().equals("")){
			tbxCodigo_paraclinico.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dtbxFecha.getText().trim().equals("")){
			dtbxFecha.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxResultado.getText().trim().equals("")){
			tbxResultado.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(!valida){
			Messagebox.show("Los campos marcados con (*) son obligatorios", 
				usuarios.getNombres()+" recuerde que...", 
				Messagebox.OK, Messagebox.EXCLAMATION);
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			parameters.put("limite_paginado", "limit 25 offset 0");
			
			List<Resultado_paraclinico> lista_datos = getServiceLocator().getResultado_paraclinicoService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Resultado_paraclinico resultado_paraclinico : lista_datos) {
				rowsResultado.appendChild(crearFilas(resultado_paraclinico, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Resultado_paraclinico resultado_paraclinico = (Resultado_paraclinico)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(resultado_paraclinico.getCodigo_historia()+""));
		fila.appendChild(new Label(resultado_paraclinico.getConsecutivo()+""));
		fila.appendChild(new Label(resultado_paraclinico.getCodigo_paraclinico()+""));
		fila.appendChild(new Label(resultado_paraclinico.getFecha()+""));
		fila.appendChild(new Label(resultado_paraclinico.getResultado()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(resultado_paraclinico);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(resultado_paraclinico);
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
			setUpperCase();
			if(validarForm()){
				//Cargamos los componentes //
				
				Resultado_paraclinico resultado_paraclinico = new Resultado_paraclinico();
				resultado_paraclinico.setCodigo_empresa(empresa.getCodigo_empresa());
				resultado_paraclinico.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				resultado_paraclinico.setCodigo_historia(tbxCodigo_historia.getValue());
				resultado_paraclinico.setConsecutivo(tbxConsecutivo.getValue());
				resultado_paraclinico.setCodigo_paraclinico(tbxCodigo_paraclinico.getValue());
				resultado_paraclinico.setFecha(new Timestamp(dtbxFecha.getValue().getTime()));
				resultado_paraclinico.setResultado(tbxResultado.getValue());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					getServiceLocator().getResultado_paraclinicoService().crear(resultado_paraclinico);
					accionForm(true,"registrar");
				}else{
					int result = getServiceLocator().getResultado_paraclinicoService().actualizar(resultado_paraclinico);
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				
				Messagebox.show("Los datos se guardaron satisfactoriamente", 
					"Informacion .." ,
					Messagebox.OK, Messagebox.INFORMATION);
				
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Resultado_paraclinico resultado_paraclinico = (Resultado_paraclinico)obj;
		try{
			tbxCodigo_historia.setValue(resultado_paraclinico.getCodigo_historia());
			tbxConsecutivo.setValue(resultado_paraclinico.getConsecutivo());
			tbxCodigo_paraclinico.setValue(resultado_paraclinico.getCodigo_paraclinico());
			dtbxFecha.setValue(resultado_paraclinico.getFecha());
			tbxResultado.setValue(resultado_paraclinico.getResultado());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Resultado_paraclinico resultado_paraclinico = (Resultado_paraclinico)obj;
		try{
			int result = getServiceLocator().getResultado_paraclinicoService().eliminar(resultado_paraclinico);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}catch(RuntimeException r){
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
}