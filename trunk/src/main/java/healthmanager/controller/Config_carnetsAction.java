/*
 * config_carnetsAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Config_carnets;
import healthmanager.modelo.bean.Elemento;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.AbstractComponent;
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

public class Config_carnetsAction extends ZKWindow{

	private static Logger log = Logger.getLogger(Config_carnetsAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Image imageFondo;
	@View private Image logo;
	@View private Image imageAtras;
	
	@View private Listbox lbxTypeImagen;

	
	@Override
	public void init(){
		listarCombos();
		varificamosExitencia(); 
	}
	
	/**
	 * Con este metodo verificamos si ya hay una configuracion
	 * */
	private void varificamosExitencia() {
		Config_carnets config_carnets = new Config_carnets();
		config_carnets.setCodigo_empresa(codigo_empresa);
		config_carnets.setCodigo_sucursal(codigo_sucursal);
		config_carnets.setTipo("01");
		config_carnets = getServiceLocator().getConfig_carnetsService().consultar(config_carnets);
		if(config_carnets != null){
			try { 
				mostrarDatos(config_carnets);
			} catch (Exception e) {MensajesUtil.mensajeError(e, null, this);}
		}
	}

	public void listarCombos(){
		listarParameter();
		listarTiposImagen();
	} 
	
	private void listarTiposImagen() {
		lbxTypeImagen.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("0");
		listitem.setLabel("Imagen Fondo");
		lbxTypeImagen.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("1");
		listitem.setLabel("Imagen Logo");
		lbxTypeImagen.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("2");
		listitem.setLabel("Imagen Fondo Atras");
		lbxTypeImagen.appendChild(listitem);
		
		if (lbxTypeImagen.getItemCount() > 0) {
			lbxTypeImagen.setSelectedIndex(0);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("logo");
		listitem.setLabel("Logo");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("fondo");
		listitem.setLabel("Fondo");
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
		
		
		boolean valida = true;
		
		
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
			
			getServiceLocator().getConfig_carnetsService().setLimit("limit 25 offset 0");
			
			List<Config_carnets> lista_datos = getServiceLocator().getConfig_carnetsService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Config_carnets config_carnets : lista_datos) {
				rowsResultado.appendChild(crearFilas(config_carnets, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeError(e, null, this);
		}
	}
	
	public Row crearFilas(Object objeto, AbstractComponent componente)throws Exception{
		Row fila = new Row();
		
		final Config_carnets config_carnets = (Config_carnets)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(config_carnets);
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
								eliminarDatos(config_carnets);
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
				
				Config_carnets config_carnets = new Config_carnets();
				config_carnets.setCodigo_empresa(empresa.getCodigo_empresa());
				config_carnets.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				config_carnets.setLogo(getContent(logo)); 
				config_carnets.setFondo(getContent(imageFondo));
				config_carnets.setImagen_atras(getContent(imageAtras));
				config_carnets.setTipo("01");
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					getServiceLocator().getConfig_carnetsService().crear(config_carnets);
					accionForm(true,"registrar");
					tbxAccion.setValue("modificar");  
				}else{
					int result = getServiceLocator().getConfig_carnetsService().actualizar(config_carnets);
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
			MensajesUtil.mensajeError(e, null, this);
		}
	}
	
	private byte[] getContent(Image image){
		if(image.getContent() != null){
			return image.getContent().getByteData();
		}
		return null;
	}
	
	public void borrarImagen(Image image)throws Exception{
		image.setSrc(null);
	}
	
	public void borrarImagen()throws Exception{
        if(lbxTypeImagen.getSelectedIndex() == 0){
			borrarImagen(imageFondo);
		}else if(lbxTypeImagen.getSelectedIndex() == 1){
			borrarImagen(logo);
		}else{
			borrarImagen(imageAtras);
		}
	}
	
	public void guardarImagen(Media media) throws Exception {
		if(lbxTypeImagen.getSelectedIndex() == 0){
			guardarImagen(media, imageFondo, "fondo"); 
		}else if(lbxTypeImagen.getSelectedIndex() == 1){
			guardarImagen(media, logo, "logo"); 
		}else{
			guardarImagen(media, imageAtras, "fondo_atras"); 
		}
	}
	
	public void guardarImagen(Media media, Image image, String name) throws Exception {
		try {
			if (media instanceof org.zkoss.image.Image){
				AImage aImage = new AImage(""+ name,media.getByteData());
				image.setContent(aImage);
			}else {
	            Messagebox.show("No es una Imagen: "+media, 
	            		"Error", Messagebox.OK, Messagebox.EXCLAMATION);
	        }
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error cargando foto!!", 
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Config_carnets config_carnets = (Config_carnets)obj;
		try{
			if(config_carnets.getLogo() != null){
				AImage aImage = new AImage("logo",config_carnets.getLogo());
	            logo.setContent(aImage);
			}
			
			if(config_carnets.getImagen_atras() != null){
				AImage aImage = new AImage("Imagen atras",config_carnets.getImagen_atras());
	            imageAtras.setContent(aImage);
			}
			
			if(config_carnets.getFondo() != null){
				AImage aImage = new AImage("fondo",config_carnets.getFondo());
	            imageFondo.setContent(aImage);
			}
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeError(e, null, this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Config_carnets config_carnets = (Config_carnets)obj;
		try{
			int result = getServiceLocator().getConfig_carnetsService().eliminar(config_carnets);
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