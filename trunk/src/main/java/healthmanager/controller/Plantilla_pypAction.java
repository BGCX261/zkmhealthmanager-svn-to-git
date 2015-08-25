/*
 * plantilla_pypAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_plantilla_pyp;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Pyp;
import healthmanager.modelo.service.Detalle_plantilla_pypService;
import healthmanager.modelo.service.Plantilla_pypService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.res.Res;
import com.framework.res.LabelAlign.AlignText;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import healthmanager.modelo.service.GeneralExtraService;

public class Plantilla_pypAction extends ZKWindow{

	
	private Plantilla_pypService plantilla_pypService;
	
	String[][] sexos_aplica = {{"A", "AMBOS"}, {"M", "MASCULINO"}, {"F", "FEMENINO"}};
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Listbox lbxArea_intervencion;
	@View private Textbox tbxCodigo_pdc;
	@View private Textbox tbxNombre_pcd;
	@View private Doublebox dbxPorcentaje_cont;
	@View private Doublebox dbxPorcentaje_sub;
	
	
	@View private Groupbox groupValidaciones;
	@View private Checkbox chk_validaciones;
	
	@View private Rows rowDetalles;
	
	private final String[] idsExcluyentes = {};
	
	private Toolbarbutton toolbarbutton; 


	private Plantilla_pyp plantilla_pypModificar;
	private List<Detalle_plantilla_pyp> lista_detalle_plantilla_pyps = new ArrayList<Detalle_plantilla_pyp>();

	
	@Override
	public void init(){
		listarCombos();
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
				agregarNuevaFilaEsquemaVacunacion(row, new Detalle_plantilla_pyp());
				adicionarRowAdicion();
				invalidarTabla();
//				if(rowDetalles.getChildren().size() <= 2){
//					Clients.showNotification("Presiones aqui para eliminar un item al esquema del vacunacion", toolbarbutton);
//				}
			}
		});
		rowDetalles.appendChild(row);
	}

	protected Toolbarbutton agregarNuevaFilaEsquemaVacunacion(Row row, Detalle_plantilla_pyp detalle_plantilla_pyp) { 
		if(row != null)
		   row.getChildren().clear();
		else{ 
			row = new Row();
		    rowDetalles.appendChild(row);
		}
		
		/* Edad Inicial */
		Intbox intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		Res.cargarAutomatica(intbox, detalle_plantilla_pyp, "edad_inicial"); 
		
		/* unidad medida */
		Listbox listbox = getListBoxUnidadMedida();
		listbox.setHflex("1");
		row.appendChild(listbox);
		Res.cargarAutomatica(listbox, detalle_plantilla_pyp, "unidad_med_edad_inicial"); 
		
		/* Edad Final */
		intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		Res.cargarAutomatica(intbox, detalle_plantilla_pyp, "edad_final"); 
		
		
		/* unidad medida */
		listbox = getListBoxUnidadMedida();
		listbox.setHflex("1");
		row.appendChild(listbox);
		Res.cargarAutomatica(listbox, detalle_plantilla_pyp, "unidad_med_edad_final"); 
		
		
		
		/* mujeres embarazas*/
		listbox = getListBoxSexo(); 
		listbox.setHflex("1");
		row.appendChild(listbox);
		Res.cargarAutomatica(listbox, detalle_plantilla_pyp, "sexo"); 
		
		
		/* respuesta 4505 */
		intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		Res.cargarAutomatica(intbox, detalle_plantilla_pyp, "tiempo"); 
		
		
		/* unidad medida */
		listbox = getListBoxUnidadMedida();
		listbox.setHflex("1");
		row.appendChild(listbox);
		Res.cargarAutomatica(listbox, detalle_plantilla_pyp, "unidad_tiempo"); 
		
		
		
		lista_detalle_plantilla_pyps.add(detalle_plantilla_pyp);
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setAttribute("row", row);
		toolbarbutton.setAttribute("esquMa", detalle_plantilla_pyp);
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
   							   lista_detalle_plantilla_pyps.remove((Detalle_plantilla_pyp)arg0.getTarget().getAttribute("esquMa"));
   							   rowDetalles.removeChild((Row)arg0.getTarget().getAttribute("row"));
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
	
	private Listbox getListBoxSexo() {
		Listbox listbox = new Listbox();
		listbox.setZclass("combobox");
		listbox.setMold("select");  
		listbox.setName("sexo"); 
		listbox.appendChild(new Listitem("-seleccione-", ""));  
		for (String[] unidad_medida : sexos_aplica) {
			listbox.appendChild(new Listitem(unidad_medida[1], unidad_medida[0])); 
		} 
		if(listbox.getItemCount() > 0){
			listbox.setSelectedIndex(0); 
		}
		return listbox;
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
	
	private void invalidarTabla(){
		getFellow("gridRegistros").invalidate();
	}
	
	public void listarCombos(){
		listarParameter();
		listarAreasIntervencion();
	}

	private void listarAreasIntervencion() {
		List<Pyp> pyps = getServiceLocator().getServicio(GeneralExtraService.class).listar(Pyp.class,new HashMap<String, Object>());
		lbxArea_intervencion.appendChild(new Listitem(" -seleccione- ", ""));
		for (Pyp pyp : pyps) {
			lbxArea_intervencion.appendChild(new Listitem(pyp.getNombre(), pyp.getCodigo()));
		}
		lbxArea_intervencion.addEventListener(Events.ON_SELECT, new  EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
                    validarAreaIntervencion();
			}
		});
		if(lbxArea_intervencion.getItemCount() > 0){
			lbxArea_intervencion.setSelectedIndex(0); 
		}
	}
	
	
	public void validarAreaIntervencion(){
		if (lbxArea_intervencion
				.getSelectedItem()
				.getValue()
				.toString()
				.equals(ServiciosDisponiblesUtils.CODSER_PYP_VACUNACION_PAI)) {
             setOpenGroup(false);
             groupValidaciones.setVisible(false);
		}else if(!groupValidaciones.isVisible()){
			 groupValidaciones.setVisible(true);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("public.pyp.nombre");
		listitem.setLabel("Area intervencion");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("codigo_pdc");
		listitem.setLabel("Codigo Cups/Cum");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	public void setOpenGroup(boolean open){
		chk_validaciones.setChecked(open); 
		groupValidaciones.setOpen(open); 
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
			if(groupboxEditar.isVisible()){
			   Clients.showNotification("Ingrese los codigo(s) cups y/o cum separados por -", tbxCodigo_pdc);
			   adicionarRowAdicion();
			}
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
		dbxPorcentaje_cont.setValue(100);
		dbxPorcentaje_sub.setValue(100); 
		plantilla_pypModificar = null;
		groupValidaciones.setVisible(false);
		rowDetalles.getChildren().clear();
		setOpenGroup(false);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		lbxArea_intervencion.setStyle("background-color:white");
		tbxCodigo_pdc.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre_pcd.setStyle("text-transform:uppercase;background-color:white");
		dbxPorcentaje_cont.setStyle("background-color:white");
		dbxPorcentaje_sub.setStyle("background-color:white");
		
		boolean valida = true;
		
		
		String msj = "Los campos marcados con (*) son obligatorios";
		
		if(lbxArea_intervencion.getSelectedItem().getValue().toString().trim().equals("")){
			lbxArea_intervencion.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxCodigo_pdc.getText().trim().equals("")){
			tbxCodigo_pdc.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}else if(tbxCodigo_pdc.getText().contains("-") &&  (!tbxCodigo_pdc.getText().matches("[a-zA-Z0-9]*[a-zA-Z0-9\\-]*[a-zA-Z0-9]*$")
				   || tbxCodigo_pdc.getText().endsWith("-"))){
			msj = "códigos Cups/Cum no es valido puede escribir solo el código o escribir varios separados por guion";
			valida = false; 
		}
		
		if(tbxNombre_pcd.getText().trim().equals("")){
			tbxNombre_pcd.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dbxPorcentaje_cont.getText().trim().equals("")){
			dbxPorcentaje_cont.setStyle("background-color:#F6BBBE");
			valida = false;
		}else if(dbxPorcentaje_cont.getValue() > 100 && valida){
			dbxPorcentaje_cont.setStyle("background-color:#F6BBBE");
			msj = "Campo porcentaje contributivo no puede ser mayor de 100 porciento";
			valida = false;
		}
		
		
		if(dbxPorcentaje_sub.getText().trim().equals("")){
			dbxPorcentaje_sub.setStyle("background-color:#F6BBBE");
			valida = false;
		}else if(dbxPorcentaje_sub.getValue() > 100 && valida){
			dbxPorcentaje_sub.setStyle("background-color:#F6BBBE");
			msj = "Campo porcentaje subcidiano no puede ser mayor de 100 porciento";
			valida = false;
		}
		
		if(groupValidaciones.isOpen() && lista_detalle_plantilla_pyps.isEmpty()){
			valida = false;
			msj = "Para validar esta actividad, por lo menos de ingresar una restriccion";
		}
		
		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", msj);
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
			
			List<Plantilla_pyp> lista_datos = plantilla_pypService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Plantilla_pyp plantilla_pyp : lista_datos) {
				rowsResultado.appendChild(crearFilas(plantilla_pyp, this));
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
		
		final Plantilla_pyp plantilla_pyp = (Plantilla_pyp)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		
		Pyp pyp = new Pyp();
		pyp.setCodigo(plantilla_pyp.getArea_intervencion()); 
		pyp = getServiceLocator().getServicio(GeneralExtraService.class).consultar(pyp);
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(pyp != null ? pyp.getNombre() : ""));
		fila.appendChild(new Label(plantilla_pyp.getCodigo_pdc()+""));
		fila.appendChild(new Label(plantilla_pyp.getNombre_pcd()+""));
		fila.appendChild(new Label(plantilla_pyp.getPorcentaje_cont()+""));
		fila.appendChild(new Label(plantilla_pyp.getPorcentaje_sub()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(plantilla_pyp);
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
								eliminarDatos(plantilla_pyp);
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
	
	public String getSexo(String sexo){
		for (String sexo_[] : sexos_aplica) {
			if(sexo_[0].equals(sexo)){
				return sexo_[1];
			}
		}
		return "";
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				Plantilla_pyp plantilla_pyp = new Plantilla_pyp();
				plantilla_pyp.setArea_intervencion(lbxArea_intervencion.getSelectedItem().getValue().toString());
				plantilla_pyp.setCodigo_pdc(tbxCodigo_pdc.getValue());
				plantilla_pyp.setNombre_pcd(tbxNombre_pcd.getValue());
				plantilla_pyp.setPorcentaje_cont((dbxPorcentaje_cont.getValue()!=null?dbxPorcentaje_cont.getValue():0.00));
				plantilla_pyp.setPorcentaje_sub((dbxPorcentaje_sub.getValue()!=null?dbxPorcentaje_sub.getValue():0.00));
//				plantilla_pyp.setSexo(lbxSexo.getSelectedItem().getValue().toString());
				plantilla_pyp.setValidaciones(chk_validaciones.isChecked() ? "S" : "N");
				
				if(plantilla_pypModificar != null){
					plantilla_pyp.setId(plantilla_pypModificar.getId()); 
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("plantilla_pyp", plantilla_pyp);
				map.put("accion", tbxAccion.getText());
				map.put("validaciones", lista_detalle_plantilla_pyps);
				plantilla_pypService.guardar(map);
		       MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
		  }
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		plantilla_pypModificar = (Plantilla_pyp)obj; 
		lista_detalle_plantilla_pyps.clear();
		try{
			for(int i=0;i<lbxArea_intervencion.getItemCount();i++){
				Listitem listitem = lbxArea_intervencion.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(plantilla_pypModificar.getArea_intervencion())){
					listitem.setSelected(true);
					i = lbxArea_intervencion.getItemCount();
				}
			}
			tbxCodigo_pdc.setValue(plantilla_pypModificar.getCodigo_pdc());
			tbxNombre_pcd.setValue(plantilla_pypModificar.getNombre_pcd());
			dbxPorcentaje_cont.setValue(plantilla_pypModificar.getPorcentaje_cont());
			dbxPorcentaje_sub.setValue(plantilla_pypModificar.getPorcentaje_sub());
			
			 rowDetalles.getChildren().clear();
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put("id_actividad", plantilla_pypModificar.getId());
			 map.put("area_intervencion", plantilla_pypModificar.getArea_intervencion()); 
			 List<Detalle_plantilla_pyp> list_Detalle_plantilla_pyps =  getServiceLocator().getServicio(Detalle_plantilla_pypService.class).listar(map);
			 for (Detalle_plantilla_pyp detalle_plantilla_pyp : list_Detalle_plantilla_pyps) {
			    agregarNuevaFilaEsquemaVacunacion(null, detalle_plantilla_pyp); 
			 }
				
				/* adicionamos boton adicion */
			adicionarRowAdicion();
//			invalidarTabla();
			
			validarAreaIntervencion();
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText()); 
			Clients.showNotification("Ingrese los codigo(s) cups y/o cum separados por -", tbxCodigo_pdc);
			setOpenGroup(plantilla_pypModificar.getValidaciones().equals("S")); 
 		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Plantilla_pyp plantilla_pyp = (Plantilla_pyp)obj;
		try{
			int result = plantilla_pypService.eliminar(plantilla_pyp);
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