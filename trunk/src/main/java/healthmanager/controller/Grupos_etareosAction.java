/*
 * grupos_etareosAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Condiciones_grupos_etareos;
import healthmanager.modelo.bean.Grupos_etareos;
import healthmanager.modelo.service.Condiciones_grupos_etareosService;
import healthmanager.modelo.service.Grupos_etareosService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
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
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.LabelAlign.AlignText;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Grupos_etareosAction extends ZKWindow{

//	private static Logger log = Logger.getLogger(Grupos_etareosAction.class);
	
	
	private Grupos_etareosService grupos_etareosService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	
	@View private Auxheader auxheaderTitulo;
	
	
	@View private Rows rowCondicionesGruposEtareos;
	
	
	@View private Textbox tbxDescripcion;
	private final String[] idsExcluyentes = {};

	private Toolbarbutton toolbarbutton;
	
	
	private List<Condiciones_grupos_etareos> list_condiciones_grupos_etareos = new ArrayList<Condiciones_grupos_etareos>();

	private Grupos_etareos gruposEtariosTemp;

	
	@Override
	public void init(){
		listarCombos();
		inicializarCondicionesGruposEtareos();
	}
	
	public void listarCombos(){
		listarParameter();
	}
	
	
	private void inicializarCondicionesGruposEtareos() {
		rowCondicionesGruposEtareos.getChildren().clear();
		adicionarRowAdicion();
	}
 
	private void adicionarRowAdicion() {
		final Row row = new Row();
		
		Cell cell = new Cell();
		cell.setColspan(8); 
		cell.setAlign(AlignText.RIGHT.toString().toLowerCase()); 
		row.appendChild(cell);
		
		toolbarbutton = new Toolbarbutton(); 
		toolbarbutton.setImage("/images/add.png");
		cell.appendChild(toolbarbutton);
		
		toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				agregarNuevaFilaEsquemaVacunacion(row, new Condiciones_grupos_etareos());
				adicionarRowAdicion();
				invalidarTabla();
//				if(rowEsquemaVacunacion.getChildren().size() <= 2){
//					Clients.showNotification("Presiones aqui para eliminar un item al esquema del vacunacion", toolbarbutton);
//				}
			}
		});
		rowCondicionesGruposEtareos.appendChild(row);
	}
	
	
	private void invalidarTabla(){
		getFellow("gridRegistros").invalidate();
	}
	
	
	protected Toolbarbutton agregarNuevaFilaEsquemaVacunacion(Row row, final Condiciones_grupos_etareos condiciones_grupos_etarios) { 
		if(row != null)
		   row.getChildren().clear();
		else{ 
			row = new Row();
		    rowCondicionesGruposEtareos.appendChild(row);
		}
		
		/* inicalizamos carga de datos esquema */
		/* descripcion */
		Textbox textbox = new Textbox();
		textbox.setHflex("1"); 
		row.appendChild(textbox);
		Res.cargarAutomatica(textbox, condiciones_grupos_etarios, "descripcion"); 
		
		
		/* Edad Inicial */
		Intbox intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		Res.cargarAutomatica(intbox, condiciones_grupos_etarios, "edad_inicial"); 
		
		/* unidad medida */
		Listbox listbox = getListBoxUnidadMedida();
		listbox.setHflex("1");
		row.appendChild(listbox);
		Res.cargarAutomatica(listbox, condiciones_grupos_etarios, "unidad_edad_inicial"); 
		
		/* Edad Final */
		intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		Res.cargarAutomatica(intbox, condiciones_grupos_etarios, "edad_final"); 
		
		
		/* unidad medida */
		listbox = getListBoxUnidadMedida();
		listbox.setHflex("1");
		row.appendChild(listbox);
		Res.cargarAutomatica(listbox, condiciones_grupos_etarios, "unidad_edad_final"); 
		
		/* diferenciador */
		final Listbox listboxDiferec = getListboxGenero();
		listboxDiferec.setHflex("1");
		row.appendChild(listboxDiferec);
		Res.cargarAutomatica(listboxDiferec, condiciones_grupos_etarios, "genero");
		
		
		/* mujeres embarazas*/
		final Checkbox checkbox = new Checkbox();
		checkbox.setHflex("1");
		row.appendChild(checkbox);
		checkbox.setDisabled(true); 
		Res.cargarAutomatica(checkbox, condiciones_grupos_etarios, "condicion_embarazada"); 
		
		/* eventos internos */
		listboxDiferec.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
               	String sexo = listboxDiferec.getSelectedItem().getValue();
               	if(sexo.equals("F")){
               		checkbox.setDisabled(false);
               	}else{
               		checkbox.setDisabled(true);
               		checkbox.setChecked(false); 
               		condiciones_grupos_etarios.setCondicion_embarazada("N");  
               	}
			}
		});
		
		// agregamos accion
		String sexo = listboxDiferec.getSelectedItem().getValue();
       	if(sexo.equals("F")){
       		checkbox.setDisabled(false);
       	}else{
       		checkbox.setDisabled(true);
       	}
		
		list_condiciones_grupos_etareos.add(condiciones_grupos_etarios);
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setAttribute("row", row);
		toolbarbutton.setAttribute("esquMa", condiciones_grupos_etarios);
		toolbarbutton.setImage("/images/borrar.gif");
		toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event arg0) throws Exception {
               Messagebox.show("Esta seguro que desea eliminar este registro? ",
   					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
   					Messagebox.QUESTION,
   					new org.zkoss.zk.ui.event.EventListener<Event>() {
   						public void onEvent(Event event) throws Exception {
   							if ("onYes".equals(event.getName())) {
   								rowCondicionesGruposEtareos.removeChild((Row)arg0.getTarget().getAttribute("row"));
   								list_condiciones_grupos_etareos.remove((Condiciones_grupos_etareos)arg0.getTarget().getAttribute("esquMa"));
   				               invalidarTabla();
   							}
   						}
   					});
			}
		});
		row.appendChild(toolbarbutton);
		
		/* fin carga de datos esquema */
		return toolbarbutton;
	}
	
	private Listbox getListBoxUnidadMedida() {
		String[][] unidades_medida = {{"01", "Dia(s)"}, {"02", "Mes(es)"}, {"03", "año(s)"}};
		Listbox listbox = new Listbox();
		listbox.setZclass("combobox");
		listbox.setMold("select");  
		for (String[] unidad_medida : unidades_medida) {
			 listbox.appendChild(new Listitem(unidad_medida[1], unidad_medida[0])); 
		} 
		if(listbox.getItemCount() > 0){
			listbox.setSelectedIndex(0); 
		}
		return listbox;
	}
	
	private Listbox getListboxGenero(){
		String[][] unidades_medida = {{"A", "Ambos"}, {"M", "Masculino"}, {"F", "Femenino"}};
		Listbox listbox = new Listbox();
		listbox.setZclass("combobox");
		listbox.setMold("select");  
		for (String[] unidad_medida : unidades_medida) {
			 listbox.appendChild(new Listitem(unidad_medida[1], unidad_medida[0])); 
		} 
		if(listbox.getItemCount() > 0){
			listbox.setSelectedIndex(0); 
		}
		return listbox;
	}
	
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("descripcion");
		listitem.setLabel("Descripcion");
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
		list_condiciones_grupos_etareos.clear();
		auxheaderTitulo.setLabel(".: REGISTRAR GRUPO ETÁREO :."); 
		inicializarCondicionesGruposEtareos();
		gruposEtariosTemp = null; 
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		tbxDescripcion.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		
		if(tbxDescripcion.getText().trim().equals("")){
			tbxDescripcion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(valida && list_condiciones_grupos_etareos.isEmpty()){
			msj = "Para realizar esta accion debe agregar por lo menos un esquema de vacunacion";
			valida = false;
			Clients.showNotification("Presiones aqui para adicionar un item al esquema de vacunacion", toolbarbutton);
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...", msj);
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
			
			grupos_etareosService.setLimit("limit 25 offset 0");
			
			List<Grupos_etareos> lista_datos = grupos_etareosService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Grupos_etareos grupos_etareos : lista_datos) {
				rowsResultado.appendChild(crearFilas(grupos_etareos, this));
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
		
		final Grupos_etareos grupos_etareos = (Grupos_etareos)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(grupos_etareos.getDescripcion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(grupos_etareos);
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
								eliminarDatos(grupos_etareos);
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
				
				Grupos_etareos grupos_etareos = new Grupos_etareos();
				grupos_etareos.setCodigo_empresa(empresa.getCodigo_empresa());
				grupos_etareos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				grupos_etareos.setDescripcion(tbxDescripcion.getValue());
				grupos_etareos.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				grupos_etareos.setUltimo_user(usuarios.getCodigo().toString());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					grupos_etareos.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
					grupos_etareos.setCreacion_user(usuarios.getCodigo().toString());
				}else{
					grupos_etareos.setId(gruposEtariosTemp.getId()); 
					grupos_etareos.setCreacion_date(gruposEtariosTemp.getCreacion_date());
					grupos_etareos.setCreacion_user(gruposEtariosTemp.getCreacion_user());
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("grupos_etareos", grupos_etareos);
				map.put("condiciones", list_condiciones_grupos_etareos);
				map.put("accion", tbxAccion.getText()); 
				grupos_etareosService.guardar(map);
				
				auxheaderTitulo.setLabel(".: MODIFICAR GRUPO ETÁREO :."); 
				Notificaciones.mostrarNotificacionInformacion("Informacion ..","Los datos se guardaron satisfactoriamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			}
		
		}catch (ValidacionRunTimeException e) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia", e.getMessage(), IConstantes.TIEMPO_NOTIFICACIONES_GENERALES); 
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Grupos_etareos grupos_etareos = (Grupos_etareos)obj;
		try{
			
			rowCondicionesGruposEtareos.getChildren().clear();
			list_condiciones_grupos_etareos.clear();
			this.gruposEtariosTemp = grupos_etareos;
			auxheaderTitulo.setLabel(".: MODIFICAR GRUPO ETÁRIO :."); 
			tbxDescripcion.setValue(grupos_etareos.getDescripcion());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", grupos_etareos.getCodigo_empresa());
			map.put("codigo_sucursal", grupos_etareos.getCodigo_sucursal());
			map.put("id_grupo_etareo", grupos_etareos.getId()); 
			List<Condiciones_grupos_etareos> list_Condiciones_grupos_etareos =  getServiceLocator().getServicio(Condiciones_grupos_etareosService.class).listar(map);
			for (Condiciones_grupos_etareos esquema_vacunacion : list_Condiciones_grupos_etareos) {
				 agregarNuevaFilaEsquemaVacunacion(null, esquema_vacunacion); 
			}
			
			/* adicionamos boton adicion */
			adicionarRowAdicion();
			invalidarTabla();
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Grupos_etareos grupos_etareos = (Grupos_etareos)obj;
		try{
			int result = grupos_etareosService.eliminar(grupos_etareos);
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