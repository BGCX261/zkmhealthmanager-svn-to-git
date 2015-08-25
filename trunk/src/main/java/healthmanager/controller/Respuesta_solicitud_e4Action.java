/*
 * respuesta_solicitud_e4Action.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Respuesta_solicitud_e4;
import healthmanager.modelo.bean.Solicitud_e1;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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

import com.framework.constantes.IConstantes;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.LabelState;
import com.framework.res.LineStringToList;
import com.framework.util.MensajesUtil;

public class Respuesta_solicitud_e4Action extends ZKWindow{

	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Intbox ibxId;
	@View private Listbox lbxSeparado_por;
	
	private String respuesta_e4;
	
	
	public void listarCombos(){
		listarParameter();
		listarElementoListbox(lbxSeparado_por,false);
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("serial_bdua");
		listitem.setLabel("Serial BDUA");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Nro identificacion");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("fecha_aprobacion::varchar");
		listitem.setLabel("fecha Aprobacion");
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
		respuesta_e4 = null;
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		boolean valida = true;
		
		if(respuesta_e4 == null){
			valida = false;
		}
		
		if(!valida){
			Messagebox.show("Debe cargar el archivo TXT", 
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
			
			getServiceLocator().getRespuestaSolicitudE4Service().setLimit("limit 25 offset 0");
			
			List<Respuesta_solicitud_e4> lista_datos = getServiceLocator().getRespuestaSolicitudE4Service().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Respuesta_solicitud_e4 respuesta_solicitud_e4 : lista_datos) {
				rowsResultado.appendChild(crearFilas(respuesta_solicitud_e4, this));
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
		
		final Respuesta_solicitud_e4 respuesta_solicitud_e4 = (Respuesta_solicitud_e4)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		
		String color = "";
		if(respuesta_solicitud_e4.getEstado_translado() != null
				   && !respuesta_solicitud_e4.getEstado_translado().isEmpty()){
			if(respuesta_solicitud_e4.getEstado_translado().equals("1")){
				color = "color:#55AED4;";
			}else{
				color = "color:#E62117;";
			}
		}
	
		hbox.appendChild(space);
		fila.appendChild(new Label(respuesta_solicitud_e4.getSerial_bdua()+""));
		fila.appendChild(new Label(respuesta_solicitud_e4.getNro_identificacion()+""));
		fila.appendChild(new Label(respuesta_solicitud_e4.getEstado_translado().equals("1") ? "Aprobado" : "Negado"));
		fila.appendChild(new LabelState(getDescripcionElement(respuesta_solicitud_e4.getEstado_translado() + respuesta_solicitud_e4.getCausa(), "causas_solicitudes"), color));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MMMMMMMMM/yyyy").format(respuesta_solicitud_e4.getFecha_aprobacion()).toUpperCase()));
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(respuesta_solicitud_e4);
			}
		});
//		hbox.appendChild(img);
		
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
								eliminarDatos(respuesta_solicitud_e4);
								buscarDatos();
							}
						}
					});
			}
		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);
		
//		fila.appendChild(hbox);
		
		return fila;
	}
	
	private String getDescripcionElement(String codigo, String tipo){
		Elemento elemento = new Elemento();
		elemento.setTipo(tipo);
		elemento.setCodigo(codigo);
		elemento = getServiceLocator().getElementoService().consultar(elemento);
		return elemento != null ? elemento.getDescripcion() : "";
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			setUpperCase();
			if(validarForm()){
				//Cargamos los componentes //
				int pos = 0;
				List<Respuesta_solicitud_e4> list_ap = new ArrayList<Respuesta_solicitud_e4>();
				StringTokenizer stringTokenizer = new StringTokenizer(respuesta_e4, "\n");
				while (stringTokenizer.hasMoreTokens()) { 
					pos++;
					String in = stringTokenizer.nextToken();
					List<String> list = LineStringToList.toList(in, lbxSeparado_por.getSelectedItem().getValue().toString());
					
					Respuesta_solicitud_e4 respuesta_solicitud_e4 = new Respuesta_solicitud_e4();
					respuesta_solicitud_e4.setCodigo_empresa(empresa.getCodigo_empresa());
					respuesta_solicitud_e4.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					respuesta_solicitud_e4.setSerial_bdua(list.get(0)+""); 
					respuesta_solicitud_e4.setTipo_identificacion(list.get(2)+"");
					respuesta_solicitud_e4.setNro_identificacion(list.get(3)+"");
					respuesta_solicitud_e4.setCodigo_entedidad_solicitante(list.get(1)+"");
					respuesta_solicitud_e4.setEstado_translado(list.get(4)+"");
					respuesta_solicitud_e4.setCausa(list.get(5)+""); 
					respuesta_solicitud_e4.setFecha_aprobacion(LineStringToList.convertTo(list.get(6)+""));
					respuesta_solicitud_e4.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
					respuesta_solicitud_e4.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
					respuesta_solicitud_e4.setCreacion_user(usuarios.getCodigo().toString());
					respuesta_solicitud_e4.setUltimo_user(usuarios.getCodigo().toString());
					respuesta_solicitud_e4.setId((ibxId.getValue()!=null?ibxId.getValue():0));
					
					Solicitud_e1 solicitudE1 = new Solicitud_e1();
					solicitudE1.setCodigo_empresa(empresa.getCodigo_empresa());
					solicitudE1.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					solicitudE1.setNro_identificacion(respuesta_solicitud_e4.getNro_identificacion());
					
					if(getServiceLocator().getSolicitudE1Service().consultar(solicitudE1) == null){
						throw new Exception("La solicitud para la persona, con el numero de identificacion ?1 no se encuentra como solicitud hecha en esta entidad\n".replace("?1", respuesta_solicitud_e4.getNro_identificacion()) + " verificar en la line " + pos); 
					} 
					
					if(respuesta_solicitud_e4.getEstado_translado().trim().isEmpty())
						throw new Exception("Estado de tranlado no puede ser vacia en la linea " + pos);
					
					if(!respuesta_solicitud_e4.getEstado_translado().equals("1") && !respuesta_solicitud_e4.getEstado_translado().equals("0"))
					    throw new Exception("Estado de tranlado no valida para la linea " + pos + " debe ser (0 ó 1)");
					
					if(respuesta_solicitud_e4.getEstado_translado().trim().isEmpty())
						throw new Exception("Causa no puede ser vacia en la linea " + pos);
					
					if(respuesta_solicitud_e4.getEstado_translado().equals("1")){
						if(!respuesta_solicitud_e4.getCausa().equals("1")
								&& !respuesta_solicitud_e4.getCausa().equals("3")
								&& !respuesta_solicitud_e4.getCausa().equals("4")
								     && !respuesta_solicitud_e4.getCausa().equals("7")
								         && !respuesta_solicitud_e4.getCausa().equals("10")
								             && !respuesta_solicitud_e4.getCausa().equals("11"))
							throw new Exception("Causa no valida en la linea " + pos + " Causas validas (1,3,4,7,10 y 11 para aceptacion");
					}else{
							if(!respuesta_solicitud_e4.getCausa().equals("1")
									&& !respuesta_solicitud_e4.getCausa().equals("5")
									     && !respuesta_solicitud_e4.getCausa().equals("7")
									         && !respuesta_solicitud_e4.getCausa().equals("8")
									             && !respuesta_solicitud_e4.getCausa().equals("9")
									                  && !respuesta_solicitud_e4.getCausa().equals("10"))
								throw new Exception("Causa no valida en la linea " + pos + " Causas validas (1,5,7,8,9 y 10 para negaciones");
					}
					
					list_ap.add(respuesta_solicitud_e4);
				}

				for (Respuesta_solicitud_e4 respuestaSolicitudE4 : list_ap) {
					getServiceLocator().getRespuestaSolicitudE4Service().crear(respuestaSolicitudE4);
				}
				accionForm(true,"registrar");
				Notificaciones.mostrarNotificacionInformacion("Informacion", "Los datos se guardaron satisfactoriamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			}
		}catch (IndexOutOfBoundsException e) {
			MensajesUtil.mensajeAlerta("Advertencia", "El archivo de respuesta no es valido"); 
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Respuesta_solicitud_e4 respuesta_solicitud_e4 = (Respuesta_solicitud_e4)obj;
		try{
			ibxId.setValue(respuesta_solicitud_e4.getId());
			
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
		Respuesta_solicitud_e4 respuesta_solicitud_e4 = (Respuesta_solicitud_e4)obj;
		try{
			int result = getServiceLocator().getRespuestaSolicitudE4Service().eliminar(respuesta_solicitud_e4);
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
	
	public String getRespuesta_e4() {
		return respuesta_e4;
	}

	public void setRespuesta_e4(String respuestaE4) {
		respuesta_e4 = respuestaE4;
	}

	@Override
	public void init() throws Exception {
		listarCombos();
	}
}
