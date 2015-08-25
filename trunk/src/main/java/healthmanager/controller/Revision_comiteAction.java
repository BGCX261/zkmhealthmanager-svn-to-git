/*
 * revision_comiteAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_revision_comite;
import healthmanager.modelo.bean.Detalle_solicitud_tecnico;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Revision_comite;
import healthmanager.modelo.bean.Solicitud_tecnico;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Solicitud_tecnicoService;

import com.framework.res.Funcion_getEdad;
import com.framework.res.L2HContraintDate;
import com.framework.res.L2HContraintDate.TypeInit;
import com.framework.util.Util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;

public class Revision_comiteAction extends ZKWindow{

	private static Logger log = Logger.getLogger(Revision_comiteAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxComentarios;
	@View private Textbox tbxAlternativas;
	@View private Textbox tbxNombre_presidente1;
	@View private Textbox tbxNombre_presidente2;
	@View private Textbox tbxNombre1;
	@View private Textbox tbxNombre2;
	@View private Textbox tbxNombre3;
	@View private Textbox tbxNombre4;
	@View private Textbox tbxNombre5;
	@View private Textbox tbxNombre6;
	@View private Datebox dtbxFecha;
	
	@View private Button btGuardar;
	@View private Rows rowMedicamentos;
	@View private Grid gridMedicamentos;
	@View private Toolbarbutton btImprimir;
	
	@View private Textbox tbxNroidentificacion;
	@View private Textbox tbxTipoidentificacion;
	@View private Textbox tbxapellido1Paciente;
	@View private Textbox tbxapellido2paciente;
	@View private Textbox tbxnombre1Paciente;
	@View private Textbox tbxnombre2paciente;
	@View private Textbox tbxEdadPaciente;
	@View private Textbox tbxMesePacientes;
	@View private Textbox tbxAnios;
	@View private Datebox tbxFecha;
	@View private Textbox tbxTipoAfiliado;
	
	@View private Textbox tbxNombre_solicita;
	@View private Textbox tbxTelefono;
	@View private Textbox tbxCargo;
	@View private Textbox tbxCel;
	@View private Textbox tbxRegistroMedico;
	
	@View private Datebox dtbxFecha_inicial;
	@View private Datebox dtbxFecha_final;
	
	private List<Detalle_revision_comite> listMedicamentos;
	private Solicitud_tecnico solicitud;
	private boolean desavilitarSeleccionEstado;
	
	private Revision_comite revision_comite;
	private String ids[] = {"btCancel", "btImprimir", "tbxNroidentificacion",
			"tbxTipoidentificacion", "tbxapellido1Paciente",
			"tbxapellido2paciente", "tbxnombre1Paciente", "btAtras",
			"tbxEdadPaciente", "tbxTipoAfiliado", "tbxNomCie", "tbxResumen",
			"tbxTratamiento", "tbxJustificacion", "tbxCargo", "tbxCel", "tbxRegistroMedico", 
			"tbxNombre_solicita", "tbxnombre2paciente", "tbxTelefono"};
	
	 
	private void cargarDatosDesdePadre() {
	   listMedicamentos = new ArrayList<Detalle_revision_comite>();
	   Map datos = Executions.getCurrent().getArg();
	   solicitud = (Solicitud_tecnico) datos.get("solicitud");  
	   if(solicitud != null){
		    cargamosDatosSolicitud(solicitud); 
		    cargarDatosPaciente(solicitud.getNro_identificacion());
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true); 
	   }
	   verificamosSiYaTieneRespuestaComite();
	}
	 
	private void verificamosSiYaTieneRespuestaComite() {
		try {
			if(solicitud != null){
				Revision_comite revisionComite = new Revision_comite();
				revisionComite.setCodigo_empresa(sucursal.getCodigo_empresa());
				revisionComite.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				revisionComite.setCodigo_solicitud(solicitud.getCodigo());
				revisionComite = getServiceLocator().getRevisionComiteService().consultar(revisionComite);
				
				if(revisionComite == null){
					loadDetallesDeReceta();
				}else{
					mostrarDatos(revisionComite);
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	private void loadDetallesDeReceta() {
		try {
            rowMedicamentos.getChildren().clear();
            listMedicamentos.clear();
            int i = 0;
            for (Detalle_solicitud_tecnico detalleSolicitudTecnico : solicitud.getDetalleSolicitudTecnicos()) {
			   rowMedicamentos.appendChild(crearFilasMedicamentos(convertToDetalleSolicitud(detalleSolicitudTecnico), ++i));
			}
			
			gridMedicamentos.setVisible(true);
			gridMedicamentos.setMold("paging");
			gridMedicamentos.setPageSize(20);
			
			gridMedicamentos.applyProperties();
			gridMedicamentos.invalidate();
			gridMedicamentos.setVisible(true);
		} catch (Exception e) {
			
		}
	}
	
	
	private void cargarDetallesRevision(List<Detalle_revision_comite>  detalleRevisionComites) {
		try {
            rowMedicamentos.getChildren().clear();
            listMedicamentos.clear();
            int i = 0;
            for (Detalle_revision_comite detalleSolicitudTecnico : detalleRevisionComites) {
			   rowMedicamentos.appendChild(crearFilasMedicamentos((detalleSolicitudTecnico), ++i));
			}
			
			gridMedicamentos.setVisible(true);
			gridMedicamentos.setMold("paging");
			gridMedicamentos.setPageSize(20);
			
			gridMedicamentos.applyProperties();
			gridMedicamentos.invalidate();
			gridMedicamentos.setVisible(true);
		} catch (Exception e) {
			
		}
	}
	
	public void deshabilitarCampos(boolean sw){
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw, ids);  
	}
	
	
	public Row crearFilasMedicamentos(final Detalle_revision_comite detalleRevisionComite, int nro)throws Exception{
		Row fila = new Row();
		
		listMedicamentos.add(detalleRevisionComite);
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(sucursal.getCodigo_empresa());
		articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		articulo.setCodigo_articulo(detalleRevisionComite.getCodigo_medicamento());
		articulo = getServiceLocator().getArticuloService().consultar(articulo);
		
//		String nameComercial = "";
		String nameGenerico = "";
//		String concentracion = "";
//		String via  = "";
//		
		if(articulo != null){
//			nameComercial = articulo.getNombre1();
			nameGenerico = articulo.getNombre1();
//			concentracion = articulo.getConcentracion();
//			via = articulo.getVia()+"";
		}
		
		/* agregamos campos  */
		final Listbox listboxEstado = new Listbox();
		listboxEstado.setWidth("95%");
		
		/**/
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(nro+""));
		fila.appendChild(new Label(detalleRevisionComite.getCodigo_medicamento()+""));
		fila.appendChild(new Label(nameGenerico+""));
		fila.appendChild(eventCriterioLoad(detalleRevisionComite, "a")); 
		fila.appendChild(eventCriterioLoad(detalleRevisionComite, "b")); 
		fila.appendChild(eventCriterioLoad(detalleRevisionComite, "c")); 
		fila.appendChild(eventCriterioLoad(detalleRevisionComite, "d")); 
		fila.appendChild(eventCriterioLoad(detalleRevisionComite, "e")); 
		fila.appendChild(listboxEstado);
		
		
		Image imgIm = new Image();
		imgIm.setSrc("/images/print_ico.gif");
		imgIm.setTooltiptext("Imprimir");
		imgIm.setStyle("cursor: pointer");
		imgIm.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
                imprimir(detalleRevisionComite.getConsecutivo(), detalleRevisionComite.getCodigo_revision()); 
			}
		});
		fila.appendChild(revision_comite != null ? imgIm : new Label());
		
		
		/* eventos de los campos */
		initComboBoxTable(listboxEstado);
		listboxEstado.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				detalleRevisionComite.setAutorizado(listboxEstado.getSelectedItem().getValue().toString());
			}
		});
		listboxEstado.setClass("combobox");
		listboxEstado.setMold("select"); 
		
		listboxEstado.setDisabled(desavilitarSeleccionEstado);
		detalleRevisionComite.setAutorizado("S");
		/* fin de eventos */
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
//				mostrarDatos(solicitud_tecnico);
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
//								eliminarDatos(solicitud_tecnico);
//								buscarDatos();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
//		fila.appendChild(hbox);
		
		return fila;
	}
	
	public void imprimir() throws Exception{
		if(revision_comite != null){
			imprimir(revision_comite.getConsecutivo()); 
		}
	}
	
	
	
	public void imprimir(String codigo_revision) throws Exception{
			Map paramRequest = new HashMap();
			paramRequest.put("name_report", "RespuestaSolicitudIndividual");
			paramRequest.put("acta", codigo_revision);
			paramRequest.put("solicitar_firmas", true); 
//			paramRequest.put("caso", revision_comite.getConsecutivo());
			
			Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window)componente;
			window.setWidth("100%");
			window.setHeight("100%"); 
			window.doModal();
	}
	
	public void imprimirSeleccionada() {
		 List<String> lista_actas = new ArrayList<String>(); 
		 List<Component> list = rowsResultado.getChildren();
		 for (Component row : list) {
			 if(row.getFirstChild() instanceof Checkbox){
				 Checkbox checkbox = (Checkbox) row.getFirstChild();
				 if(checkbox.isChecked()){
					 Revision_comite revision_comite = checkbox.getValue();
					 lista_actas.add(revision_comite.getConsecutivo()); 
				 }
			 }
		}
		 if(lista_actas.isEmpty()){
			 MensajesUtil.mensajeAlerta("Advertencia", "Para esta accion debe seleccionar por lo menos un acta");
		 }else{ 
			    Map paramRequest = new HashMap();
				paramRequest.put("name_report", "RespuestaSolicitudIndividual");
				paramRequest.put("actas", lista_actas);
				paramRequest.put("solicitar_firmas", true); 
				
				Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
				final Window window = (Window)componente;
				window.setWidth("100%");
				window.setHeight("100%"); 
				window.doModal();   
		 }
	}
	
	
	public void cambiarEstadoSeleccionaLista(boolean seleccionar){
		 List<Component> list = rowsResultado.getChildren();
		 for (Component row : list) {
			 if(row.getFirstChild() instanceof Checkbox){
				 Checkbox checkbox = (Checkbox) row.getFirstChild();
				 checkbox.setChecked(seleccionar); 
			 }
		}
	}
	
	protected void imprimir(String consecutivo, String codigo_revision) throws Exception{
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "RespuestaSolicitudIndividual");
		paramRequest.put("acta", codigo_revision);
		paramRequest.put("caso", consecutivo);
		paramRequest.put("solicitar_firmas", false); 
		
		Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window)componente;
		window.setWidth("100%");
		window.setHeight("100%"); 
		window.doModal();
	}

	private Component eventCriterioLoad(
			final Detalle_revision_comite detalleRevisionComite, final String index) throws Exception{
		final Checkbox checkbox = new Checkbox();
		final Field field = Detalle_revision_comite.class.getDeclaredField("criterio_" +index);
		field.setAccessible(true);
		checkbox.addEventListener("onCheck", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				 field.set(detalleRevisionComite, checkbox.isChecked() ? "S" : "N");
			}
		});
		String value = (String) field.get(detalleRevisionComite);
		if(value != null){
			checkbox.setChecked(value.equals("S"));
		}else{
			field.set(detalleRevisionComite, "N"); 
		}
		return checkbox;
	}

	private void initComboBoxTable(Listbox listbox){
		listbox.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("S");
		listitem.setLabel("Aceptado");
		listbox.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("N"); 
		listitem.setLabel("No Aceptado");
		listbox.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("P"); 
		listitem.setLabel("Aplazado");
		listbox.appendChild(listitem);
		
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}
	
	private Detalle_revision_comite convertToDetalleSolicitud(
			Detalle_solicitud_tecnico detalleSolicitudTecnico) {
		Detalle_revision_comite detalleRevisionComite = new Detalle_revision_comite();
		detalleRevisionComite.setCodigo_empresa(sucursal.getCodigo_empresa());
		detalleRevisionComite.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		detalleRevisionComite.setCodigo_medicamento(detalleSolicitudTecnico.getCodigo_medicamento());
		detalleRevisionComite.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		detalleRevisionComite.setCreacion_user(usuarios.getCodigo().toString());
		detalleRevisionComite.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		detalleRevisionComite.setUltimo_user(usuarios.getCodigo().toString());
		detalleRevisionComite.setCodigo_empresa(empresa.getCodigo_empresa());
		detalleRevisionComite.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		return detalleRevisionComite;
	}

	
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("pac.nro_identificacion || ' ' || pac.apellido1 || ' ' || pac.apellido2 || ' ' || pac.nombre1 || ' ' || pac.nombre2");
		listitem.setLabel("Paciente");
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
		revision_comite = null;
		btImprimir.setVisible(false);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		boolean valida = true;
		try {
			FormularioUtil.validarCamposObligatorios(tbxComentarios, tbxAlternativas, dtbxFecha); 
		} catch (Exception e) {
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("fecha_inicio", new Timestamp(dtbxFecha_inicial.getValue().getTime())); 
			parameters.put("fecha_final", new Timestamp(dtbxFecha_final.getValue().getTime())); 
			
			getServiceLocator().getRevisionComiteService().setLimit("limit 25 offset 0");
			
			List<Revision_comite> lista_datos = getServiceLocator().getRevisionComiteService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Revision_comite revision_comite : lista_datos) {
				rowsResultado.appendChild(crearFilas(revision_comite, this));
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
		
		final Revision_comite revision_comite = (Revision_comite)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(revision_comite.getNro_identificacion()); 
		paciente = getServiceLocator().getServicio(PacienteService.class).consultar(paciente);
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		Checkbox checkbox = new Checkbox();
		checkbox.setValue(revision_comite); 
		fila.appendChild(checkbox);
		fila.appendChild(new Label(revision_comite.getCodigo_solicitud()+""));
		fila.appendChild(new Label(revision_comite.getConsecutivo()+""));
		
		
		fila.appendChild(getToolbatButtonInfor(revision_comite.getComentarios()+"", "COMENTARIOS")); 
		fila.appendChild(getToolbatButtonInfor(revision_comite.getAlternativas()+"", "ALTERNATIVAS"));
		fila.appendChild(new Label(paciente != null ? paciente.getDocumento() + " " + paciente.getNombreCompleto() : "* NO ENCONTRADO *"));
		fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd").format(revision_comite.getFecha()))); 
		int casos = 0;
		if(revision_comite.getLista_detalle() != null){
			casos = revision_comite.getLista_detalle().size();
		}
		fila.appendChild(new Label(casos + ""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(revision_comite);
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
								eliminarDatos(revision_comite);
								buscarDatos();
							}
						}
					});
			}
		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);
		img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				imprimir(revision_comite.getConsecutivo()); 
			}
		});
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	private Hbox getToolbatButtonInfor(String informacion, String titulo) {
		Hbox hbox = new Hbox();
		Image image = new Image();
		image.setSrc("/images/info_icon.png");
		image.setStyle("cursor:help");  
		image.setTooltiptext(informacion); 
		hbox.appendChild(image);
		hbox.setAlign("center"); 
		return hbox;
	}
	
	
	public void atras() throws Exception{
		if(solicitud != null){
			onClose();
		}else{
			accionForm(false, tbxAccion.getText()); 
		}
	}

	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			setUpperCase();
			if(validarForm()){
				//Cargamos los componentes //
				final Map<String,Object> datos = new HashMap<String,Object>();
				
				Revision_comite revision_comite = new Revision_comite();
//				revision_comite.setCodigo_receta(solicitud.getCodigo_receta());
				revision_comite.setNro_ingreso(solicitud.getNro_ingreso());
				revision_comite.setNro_identificacion(solicitud.getNro_identificacion()); 
				revision_comite.setComentarios(tbxComentarios.getValue());
				revision_comite.setAlternativas(tbxAlternativas.getValue());
				revision_comite.setNombre_presidente1(tbxNombre_presidente1.getValue());
				revision_comite.setCodigo_empresa(empresa.getCodigo_empresa());
				revision_comite.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				revision_comite.setNombre_presidente2(tbxNombre_presidente2.getValue());
				revision_comite.setNombre1(tbxNombre1.getValue());
				revision_comite.setNombre2(tbxNombre2.getValue());
				revision_comite.setNombre3(tbxNombre3.getValue());
				revision_comite.setNombre4(tbxNombre4.getValue());
				revision_comite.setNombre5(tbxNombre5.getValue());
				revision_comite.setNombre6(tbxNombre6.getValue());
				revision_comite.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				revision_comite.setCreacion_user(usuarios.getCodigo().toString());
				revision_comite.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				revision_comite.setUltimo_user(usuarios.getCodigo().toString());
				revision_comite.setCodigo_solicitud(solicitud.getCodigo());
//				revision_comite.setConsecutivo();
				revision_comite.setFecha(new Timestamp(dtbxFecha.getValue().getTime()));
				
				datos.put("comite", revision_comite);
				datos.put("lista_detalle", listMedicamentos);
				datos.put("accion", tbxAccion.getText());
				 
				revision_comite = getServiceLocator().getRevisionComiteService().guardar(datos, getServiceLocator());
				this.revision_comite = revision_comite;
				cargarDetallesRevision(clone(listMedicamentos)); 
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					tbxAccion.setText("modificar");
				}
				deshabilitarCampos(true);
//				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
//					getServiceLocator().getRevisionComiteService().crear(revision_comite);
//					accionForm(true,"registrar");
//				}else{
//					int result = getServiceLocator().getRevisionComiteService().actualizar(revision_comite);
//					if(result==0){
//						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
//					}
//				}
				btGuardar.setDisabled(true); 
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
	
	private List clone(List l){
		List list = new ArrayList();
		for (Object object : l) {
			 list.add(object);
		}
		return list;
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		revision_comite = (Revision_comite)obj; 
		try{
			tbxComentarios.setValue(revision_comite.getComentarios());
			tbxAlternativas.setValue(revision_comite.getAlternativas());
			tbxNombre_presidente1.setValue(revision_comite.getNombre_presidente1());
			tbxNombre_presidente2.setValue(revision_comite.getNombre_presidente2());
			tbxNombre1.setValue(revision_comite.getNombre1());
			tbxNombre2.setValue(revision_comite.getNombre2());
			tbxNombre3.setValue(revision_comite.getNombre3());
			tbxNombre4.setValue(revision_comite.getNombre4());
			tbxNombre5.setValue(revision_comite.getNombre5());
			tbxNombre6.setValue(revision_comite.getNombre6());
			dtbxFecha.setValue(revision_comite.getFecha());
			
			deshabilitarCampos(true);
			desavilitarSeleccionEstado = true; 
			cargarDetallesRevision(revision_comite.getLista_detalle());
			btGuardar.setDisabled(true); 
			
			Solicitud_tecnico solicitud_tecnico = new Solicitud_tecnico();
			solicitud_tecnico.setCodigo_empresa(revision_comite.getCodigo_empresa());
			solicitud_tecnico.setCodigo_sucursal(revision_comite.getCodigo_sucursal());
			solicitud_tecnico.setCodigo(revision_comite.getCodigo_solicitud());
			solicitud_tecnico = getServiceLocator().getServicio(Solicitud_tecnicoService.class).consultar(solicitud_tecnico);
		 
			if(solicitud_tecnico != null)cargamosDatosSolicitud(solicitud_tecnico); 
			cargarDatosPaciente(revision_comite.getNro_identificacion());
			
			btImprimir.setVisible(true);
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
//	private void cargamosDatosDeMedico(Usuarios usuarios) {
//		tbxNombre_solicita.setText("" + usuarios.getNombres() + " "
//				+ usuarios.getApellidos());
//		tbxTelefono.setText("" + usuarios.getTel_res());
//		tbxCargo.setText(usuarios.getTipo_med() == null ? "MEDICO" : (usuarios
//				.getTipo_med().equals("01") ? "MEDICO GENERAL"
//				: "MEDICO ESPECIALISTA"));
//		tbxCel.setText("" + usuarios.getEmail());
//		tbxRegistroMedico.setText("" + usuarios.getRegistro_medico());
//	}
	
	private void cargamosDatosSolicitud(Solicitud_tecnico solicitud_tecnico) {
		tbxNombre_solicita.setText(solicitud_tecnico.getNombre_solicita());
		tbxTelefono.setText(solicitud_tecnico.getTelefono_solicita());
		tbxCargo.setText(solicitud_tecnico.getCargo_solicita());
		tbxCel.setText(solicitud_tecnico.getCelular_solicita());
		tbxRegistroMedico.setText(solicitud_tecnico.getRegistro_medico_solicita());
	}
	
	private void cargarDatosPaciente(String nroIdentificacion) {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(nroIdentificacion);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			cargarDatosPaciente(paciente);
		}
	}

	private void cargarDatosPaciente(Paciente paciente) {
		String tipo_identificacion =  Utilidades.getDescripcionElemento(paciente.getTipo_identificacion(), "tipo_id", getServiceLocator());
		String tipo_afiliado =  Utilidades.getDescripcionElemento(paciente.getTipo_afiliado(), "tipo_afiliado", getServiceLocator());
		
		tbxapellido1Paciente.setText("" + paciente.getApellido1());
		tbxapellido2paciente.setText("" + paciente.getApellido2());
		tbxnombre1Paciente.setText("" + paciente.getNombre1());
		tbxnombre2paciente.setText("" + paciente.getNombre2());
		tbxEdadPaciente.setValue(Util.getEdadPrsentacion(paciente.getFecha_nacimiento())); 
		tbxMesePacientes.setValue(Funcion_getEdad.getMeses(paciente
				.getFecha_nacimiento()));
		tbxAnios.setValue(Funcion_getEdad.getYears(paciente
				.getFecha_nacimiento()));
		tbxFecha.setValue(Calendar.getInstance().getTime());
		tbxNroidentificacion.setValue("" + paciente.getNro_identificacion());
		tbxTipoidentificacion.setValue("(" + paciente.getTipo_identificacion() + ") " +  tipo_identificacion);
		tbxTipoAfiliado.setValue("" + tipo_afiliado);
	} 
	
	public void eliminarDatos(Object obj)throws Exception{
		Revision_comite revision_comite = (Revision_comite)obj;
		try{
			int result = getServiceLocator().getRevisionComiteService().eliminar(revision_comite);
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

	@Override
	public void init() {
		inicializarFechas();
		listarCombos();
		cargarDatosDesdePadre();
	}

	private void inicializarFechas() { 
		dtbxFecha_inicial.setValue(L2HContraintDate.initFechaInHHMMSS(Calendar.getInstance().getTime(), TypeInit.Init00_00_00));
		dtbxFecha_final.setValue(L2HContraintDate.initFechaInHHMMSS(Calendar.getInstance().getTime(), TypeInit.end23_59_59));
	}
}
