package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_cita;
import healthmanager.modelo.bean.Elemento;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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

public class ControlCitasPorMedicoAction extends ZKWindow {
	private static Logger log = Logger.getLogger(Control_citaAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Listbox lbxCodigo_administradora;
	@View private Listbox lbxServicio;
	@View private Intbox ibxTotal_citas;
	@View private Listbox lbxTipo_control;
	@View private Listbox lbxAlcanze_control;
	
	@Override
	public void init(){
		listarCombos();
	}
	
	public void listarCombos(){
		listarParameter();
		listarElementoListbox(lbxCodigo_administradora,true);
		listarElementoListbox(lbxServicio,true);
		listarElementoListbox(lbxTipo_control,true);
		listarElementoListbox(lbxAlcanze_control,true);
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_administradora");
		listitem.setLabel("Codigo_administradora");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("servicio");
		listitem.setLabel("Servicio");
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
		
		lbxCodigo_administradora.setStyle("background-color:white");
		lbxServicio.setStyle("background-color:white");
		ibxTotal_citas.setStyle("background-color:white");
		lbxTipo_control.setStyle("background-color:white");
		lbxAlcanze_control.setStyle("background-color:white");
		
		boolean valida = true;
		
		if(lbxCodigo_administradora.getSelectedItem().getValue().toString().trim().equals("")){
			lbxCodigo_administradora.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(lbxServicio.getSelectedItem().getValue().toString().trim().equals("")){
			lbxServicio.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(ibxTotal_citas.getText().trim().equals("")){
			ibxTotal_citas.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		
		if(lbxTipo_control.getSelectedItem().getValue().toString().trim().equals("")){
			lbxTipo_control.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(lbxAlcanze_control.getSelectedItem().getValue().toString().trim().equals("")){
			lbxAlcanze_control.setStyle("background-color:#F6BBBE");
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
			
			getServiceLocator().getControl_citaService().setLimit("limit 25 offset 0");
			
			List<Control_cita> lista_datos = getServiceLocator().getControl_citaService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Control_cita control_cita : lista_datos) {
				rowsResultado.appendChild(crearFilas(control_cita, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "",this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Control_cita control_cita = (Control_cita)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(control_cita.getCodigo_administradora()+""));
		fila.appendChild(new Label(control_cita.getServicio()+""));
		fila.appendChild(new Label(control_cita.getTotal_citas()+""));
		fila.appendChild(new Label(control_cita.getDelete_date()+""));
		fila.appendChild(new Label(control_cita.getTipo_control()+""));
		fila.appendChild(new Label(control_cita.getAlcanze_control()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(control_cita);
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
								eliminarDatos(control_cita);
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
				
				Control_cita control_cita = new Control_cita();
				control_cita.setCodigo_empresa(empresa.getCodigo_empresa());
				control_cita.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				control_cita.setCodigo_administradora(lbxCodigo_administradora.getSelectedItem().getValue().toString());
				control_cita.setServicio(lbxServicio.getSelectedItem().getValue().toString());
				control_cita.setTotal_citas((ibxTotal_citas.getValue()!=null?ibxTotal_citas.getValue():0));
				control_cita.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				control_cita.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				control_cita.setCreacion_user(usuarios.getCodigo().toString());
				control_cita.setUltimo_user(usuarios.getCodigo().toString());
				control_cita.setTipo_control(lbxTipo_control.getSelectedItem().getValue().toString());
				control_cita.setAlcanze_control(lbxAlcanze_control.getSelectedItem().getValue().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					getServiceLocator().getControl_citaService().crear(control_cita);
					accionForm(true,"registrar");
				}else{
					int result = getServiceLocator().getControl_citaService().actualizar(control_cita);
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				
				Messagebox.show("Los datos se guardaron satisfactoriamente", 
					"Informacion .." ,
					Messagebox.OK, Messagebox.INFORMATION);
				
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "",this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Control_cita control_cita = (Control_cita)obj;
		try{
			for(int i=0;i<lbxCodigo_administradora.getItemCount();i++){
				Listitem listitem = lbxCodigo_administradora.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(control_cita.getCodigo_administradora())){
					listitem.setSelected(true);
					i = lbxCodigo_administradora.getItemCount();
				}
			}
			for(int i=0;i<lbxServicio.getItemCount();i++){
				Listitem listitem = lbxServicio.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(control_cita.getServicio())){
					listitem.setSelected(true);
					i = lbxServicio.getItemCount();
				}
			}
			ibxTotal_citas.setValue(control_cita.getTotal_citas());
			for(int i=0;i<lbxTipo_control.getItemCount();i++){
				Listitem listitem = lbxTipo_control.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(control_cita.getTipo_control())){
					listitem.setSelected(true);
					i = lbxTipo_control.getItemCount();
				}
			}
			for(int i=0;i<lbxAlcanze_control.getItemCount();i++){
				Listitem listitem = lbxAlcanze_control.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(control_cita.getAlcanze_control())){
					listitem.setSelected(true);
					i = lbxAlcanze_control.getItemCount();
				}
			}
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "",this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Control_cita control_cita = (Control_cita)obj;
		try{
			int result = getServiceLocator().getControl_citaService().eliminar(control_cita);
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
