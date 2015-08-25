/*
 * revision_ciiuAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Revision_ciiu;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
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

import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.FormularioUtil;

public class Revision_ciiuAction extends ZKWindow{

	private static Logger log = Logger.getLogger(Revision_ciiuAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo;
	@View private Textbox tbxDescripcion;
	@View private Checkbox chbActivo;
	
	
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("descripcion");
		listitem.setLabel("Descripcion");
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
		FormularioUtil.limpiarComponentes(groupboxEditar); 
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		boolean valida = true;
		try {
			FormularioUtil.validarCamposObligatorios(tbxCodigo, tbxDescripcion); 
		} catch (WrongValueException e) {
			 valida = false;
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
			parameters.put("codigo_empresa", usuarios.getCodigo_empresa()); 
			parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());
			
			getServiceLocator().getRevisionCiiuService().setLimit("limit 25 offset 0");
			
			List<Revision_ciiu> lista_datos = getServiceLocator().getRevisionCiiuService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Revision_ciiu revision_ciiu : lista_datos) {
				rowsResultado.appendChild(crearFilas(revision_ciiu, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Revision_ciiu revision_ciiu = (Revision_ciiu)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(revision_ciiu.getCodigo()+""));
		fila.appendChild(new Label(revision_ciiu.getDescripcion().toUpperCase()+""));
		fila.appendChild(new Label(revision_ciiu.getActivo().equalsIgnoreCase("S")? "Activo" : "Inactivo")); 
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(revision_ciiu);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(revision_ciiu);
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
			setUpperCase();
			if(validarForm()){
				//Cargamos los componentes //
				
				Revision_ciiu revision_ciiu = new Revision_ciiu();
				revision_ciiu.setCodigo_empresa(empresa.getCodigo_empresa());
				revision_ciiu.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				revision_ciiu.setCodigo(tbxCodigo.getValue());
				revision_ciiu.setDescripcion(tbxDescripcion.getValue());
				revision_ciiu.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				revision_ciiu.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				revision_ciiu.setCreacion_user(usuarios.getCodigo().toString());
				revision_ciiu.setUltimo_user(usuarios.getCodigo().toString());
				revision_ciiu.setActivo(chbActivo.isChecked() ? "S" : "N");
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					getServiceLocator().getRevisionCiiuService().crear(revision_ciiu);
					accionForm(true,"registrar");
				}else{
					int result = getServiceLocator().getRevisionCiiuService().actualizar(revision_ciiu);
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				
				Messagebox.show("Los datos se guardaron satisfactoriamente", 
					"Informacion .." ,
					Messagebox.OK, Messagebox.INFORMATION);
				
			}
			
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Revision_ciiu revision_ciiu = (Revision_ciiu)obj;
		try{
			tbxCodigo.setValue(revision_ciiu.getCodigo());
			tbxDescripcion.setValue(revision_ciiu.getDescripcion());
			chbActivo.setChecked(revision_ciiu.getActivo().equalsIgnoreCase("S"));
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Revision_ciiu revision_ciiu = (Revision_ciiu)obj;
		try{
			int result = getServiceLocator().getRevisionCiiuService().eliminar(revision_ciiu);
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
	
	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}


	@Override
	public void init() throws Exception {
		listarCombos();
	}
}
