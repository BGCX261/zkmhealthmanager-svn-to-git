/*
 * control_signos_vitalesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Control_signos_vitales;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Control_signos_vitalesService;
import healthmanager.modelo.service.PacienteService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.interfaces.IHistoria_generica;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class Control_signos_vitalesAction extends HistoriaClinicaModel implements
IHistoria_generica{

	private static Logger log = Logger.getLogger(Control_signos_vitalesAction.class);
	
	
	private Control_signos_vitalesService control_signos_vitalesService;
	
	private Admision admision;
//	private Opciones_via_ingreso opciones_via_ingreso;

	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;

	@View
	private Toolbarbutton btGuardar;

	
	@View
	private Bandbox bandboxPrestador;
	@View
	private Textbox tbxNomPrestador;
	
	@View
	private Textbox mcTbxPaciente;
	@View
	private Textbox mcTbxInfoPaciente;
	@View
	private Textbox mcTbxTipo_identificacion;
	@View
	private Textbox mcDtbxFecha_ncto;
	@View
	private Textbox mcTbxEdad;
	@View
	private Textbox mcTbxSexo;

	private final String[] idsExcluyentes = {"tbxAccion",
			"btnLimpiar_prestador", "tbxValue", "lbxParameter",
			"infoPacientes", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar"};


	@View
	private Grid gridControl;
	
	/**
	 * Sobreescritura del metodo params(Map<String, Object> map) para obtener
	 * los parametros iniciales con los que trabajara la historia clinica
	 */
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey("admision_seleccionada")) {
			admision = (Admision) map.get("admision_seleccionada");	
//			opciones_via_ingreso = (Opciones_via_ingreso) map
//					.get(IVias_ingreso.OPCION_VIA_INGRESO);
		}
	}
	
	@Override
	public void init() throws Exception{
		listarCombos();
		try {
			accionForm(true, "registrar");
		} catch (Exception e) {
			//  block
			e.printStackTrace();
		}
	}
	
	
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("consecutivo");
		listitem.setLabel("Consecutivo");
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
			
			mostrarControl();
			
		}else{
			
			limpiarDatos();
			editarPaciente();
			cargarPrestador();
			//mostrarControl();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
		cargarPrestador();
		
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
			
			control_signos_vitalesService.setLimit("limit 25 offset 0");
			
			List<Control_signos_vitales> lista_datos = control_signos_vitalesService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Control_signos_vitales control_signos_vitales : lista_datos) {
				rowsResultado.appendChild(crearFilas(control_signos_vitales, this));
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
		
		final Control_signos_vitales control_signos_vitales = (Control_signos_vitales)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(control_signos_vitales);
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
								//eliminarDatos(control_signos_vitales);
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
				
				Control_signos_vitales control_signos_vitales = new Control_signos_vitales();
				control_signos_vitales.setCodigo_empresa(codigo_empresa);
				control_signos_vitales.setCodigo_sucursal(codigo_sucursal);
				control_signos_vitales.setIdentificacion(admision.getNro_identificacion());
				control_signos_vitales.setNro_ingreso(admision.getNro_ingreso());
				cargarPrestador();
				control_signos_vitales.setPrestador(bandboxPrestador.getValue());
				control_signos_vitales.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				control_signos_vitales.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				control_signos_vitales.setCreacion_user(usuarios.getCodigo().toString());
				control_signos_vitales.setDelete_date(null);
				control_signos_vitales.setUltimo_user(usuarios.getCodigo().toString());
				control_signos_vitales.setDelete_user(null);
				
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("control_signos_vitales", control_signos_vitales);
				datos.put("accion", tbxAccion.getText());
				datos.put("admision", admision);
				datos.put("listado_componentes_control",
						obtenerListadoControl());
				
				//log.info(codigo_empresa+" "+codigo_sucursal);
				control_signos_vitalesService.guardar(datos);
				
				tbxAccion.setValue("modificar");
				
		
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
//		Control_signos_vitales control_signos_vitales = (Control_signos_vitales)obj;
		try{
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Prestadores> list = getServiceLocator()
					.getPrestadoresService().listar(parameters);

			lbx.getItems().clear();

			for (Prestadores dato : list) {

				Especialidad especialidad = new Especialidad();
				especialidad.setCodigo(dato.getCodigo_especialidad());
				especialidad = getServiceLocator().getEspecialidadService()
						.consultar(especialidad);

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombres()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellidos()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						(especialidad != null ? especialidad.getNombre() : "")));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			bandboxPrestador.setValue("");
			tbxNomPrestador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			bandboxPrestador.setValue(dato.getNro_identificacion());
			tbxNomPrestador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		bandboxPrestador.close();
	}

	public void cargarPrestador() throws Exception {
			
			bandboxPrestador.setValue(usuarios.getCodigo());
			tbxNomPrestador.setValue(usuarios.getNombres() + " "
					+ usuarios.getApellidos());
		
	}

	public Usuarios cargarPrestador(String identificacion) throws Exception {
		Usuarios usuarios = new Usuarios();
		usuarios.setCodigo_empresa(empresa.getCodigo_empresa());
		usuarios.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		usuarios.setCodigo(identificacion);
		usuarios = getServiceLocator().getUsuariosService()
				.consultar(usuarios);
		if (usuarios == null) {
			throw new Exception(
					"Usuario no esta creado en el modulo de prestadore, actualize informacion de usuario");
		}
		
		return usuarios;
	
}
	
	public void editarPaciente(){
		Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = getServiceLocator().getServicio(PacienteService.class).consultar(paciente);
			if(paciente != null){
				modificarPaciente(paciente);
			}
		
	}
	
	public void modificarPaciente(Paciente registro) {
		
		mcTbxPaciente.setValue(registro.getNro_identificacion());
		mcTbxInfoPaciente.setValue(registro.getNombre1()+" "+
				registro.getNombre2()+" "+registro.getApellido1()+" "+
				registro.getApellido2());
		mcTbxTipo_identificacion.setValue(registro.getTipo_identificacion());
		mcDtbxFecha_ncto
		.setValue((registro != null ? new java.text.SimpleDateFormat(
				"dd/MM/yyyy").format(registro.getFecha_nacimiento())
				: ""));
		mcTbxEdad.setValue((registro != null ? Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(registro
						.getFecha_nacimiento()), "1", true) : ""));
		
		Elemento sexo = new Elemento();
		sexo.setTipo("sexo");
		sexo.setCodigo((registro != null ? registro
				.getSexo() : ""));
		sexo = getServiceLocator().getElementoService().consultar(
				sexo);
		mcTbxSexo.setValue(sexo != null ? sexo.getDescripcion() :"");


	}
	
	
	public void agregarControl() {
		final Row fila = new Row();
		
		Control_signos_vitales control_signos_vitales = new Control_signos_vitales();
		
		Cell celda1 = new Cell();
		Timebox timebox1 = new Timebox();
		timebox1.setWidth("90%");
		celda1.appendChild(timebox1);
		fila.appendChild(celda1);

		Cell celda2 = new Cell();
		Doublebox doublebox2 = new Doublebox();
		doublebox2.setWidth("90%");
		celda2.appendChild(doublebox2);
		fila.appendChild(celda2);

		Cell celda3 = new Cell();
		Doublebox doublebox3 = new Doublebox();
		doublebox3.setWidth("90%");
		celda3.appendChild(doublebox3);
		fila.appendChild(celda3);

		Cell celda4 = new Cell();
		Doublebox doublebox4 = new Doublebox();
		doublebox4.setWidth("90%");
		celda4.appendChild(doublebox4);
		fila.appendChild(celda4);

		Cell celda5 = new Cell();
		Doublebox doublebox5 = new Doublebox();
		doublebox5.setWidth("90%");
		celda5.appendChild(doublebox5);
		fila.appendChild(celda5);

		Cell celda6 = new Cell();
		Doublebox doublebox6 = new Doublebox();
		doublebox6.setWidth("90%");
		celda6.appendChild(doublebox6);
		fila.appendChild(celda6);

		
		Cell celda7 = Utilidades.obtenerCell("", Textbox.class, false, false);
		fila.appendChild(celda7);

		Toolbarbutton imagen_eliminar = new Toolbarbutton("",
				"/images/borrar.gif");
		imagen_eliminar.setTooltiptext("Eliminar");
		imagen_eliminar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que desea eliminar este registro? ",
										"Eliminar Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													// do the thing
													fila.detach();
												}
											}
										});

					}
				});
		fila.appendChild(imagen_eliminar);
		fila.setValue(control_signos_vitales);

		gridControl.getRows().appendChild(fila);
		// numero_filas++;
	}

	public List<Control_signos_vitales> obtenerListadoControl() {
		List<Component> listado_componentes_control = gridControl.getRows()
				.getChildren();

		List<Control_signos_vitales> listado_controles = new ArrayList<Control_signos_vitales>();

		// int i = 0;
		for (Component componente : listado_componentes_control) {
			Row fila = (Row) componente;
			Control_signos_vitales control_signos = (Control_signos_vitales)fila.getValue();
			
			if(control_signos.getConsecutivo() == null){
				Timebox timebox_fecha = (Timebox) fila.getChildren().get(0).getFirstChild();
				if (timebox_fecha.getValue() != null) {
					control_signos.setFecha_creacion(new Timestamp(timebox_fecha.getValue().getTime()));

				} else {
					control_signos.setFecha_creacion(null);

				}
				Doublebox doublebox_tension = (Doublebox) fila.getChildren()
						.get(1).getFirstChild();
				control_signos.setTension_arterial(doublebox_tension.getValue() != null 
						? doublebox_tension.getValue() + "" : "");

				Doublebox doublebox_fc = (Doublebox) fila.getChildren()
						.get(2).getFirstChild();
				control_signos.setFrecuencia_cardiaca(doublebox_fc.getValue() != null 
						? doublebox_fc.getValue() + "" : "");

				Doublebox doublebox_pulso = (Doublebox) fila.getChildren()
						.get(3).getFirstChild();
				control_signos.setPulso(doublebox_pulso.getValue() != null 
						? doublebox_pulso.getValue() + "" : "");

				Doublebox doublebox_fr = (Doublebox) fila.getChildren()
						.get(4).getFirstChild();
				control_signos.setFrecuencia_respiratoria(doublebox_fr.getValue() != null 
						? doublebox_fr.getValue() + "" : "");

				Doublebox doublebox_temp = (Doublebox) fila.getChildren()
						.get(5).getFirstChild();
				control_signos.setTemperatura(doublebox_temp.getValue() != null 
						? doublebox_temp.getValue() + "" : "");

				
				Textbox textbox_observacion = (Textbox) fila.getChildren().get(6)
						.getFirstChild();
				control_signos.setObservacion(textbox_observacion
						.getValue());
				
				control_signos.setCodigo_empresa(codigo_empresa);
				control_signos.setCodigo_sucursal(codigo_sucursal);
				control_signos.setIdentificacion(admision.getNro_identificacion());
				control_signos.setNro_ingreso(admision.getNro_ingreso());
				//control_signos.setPrestador(prestador);
				
				control_signos.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
				control_signos.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				control_signos.setCreacion_user(usuarios.getCodigo().toString());
				control_signos.setDelete_date(null);
				control_signos.setUltimo_user(usuarios.getCodigo().toString());
				control_signos.setDelete_user(null);
				
			}			
			listado_controles.add(control_signos);

		}

		return listado_controles;
	}
	
	public void mostrarControl() {

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", admision.getNro_ingreso());

		//log.info("parametros" + parametros);

		control_signos_vitalesService.setLimit("limit 25 offset 0");

		List<Control_signos_vitales> listado_control = control_signos_vitalesService
				.listar(parametros);

		//log.info("listado_control" + listado_control);

		if (listado_control != null) {
			for (final Control_signos_vitales control_signos_vitales : listado_control) {
				final Row fila = new Row();
				
				fila.setValue(control_signos_vitales);
				
				Cell celda1 = new Cell();
				Timebox timebox1 = new Timebox();
				timebox1.setValue(control_signos_vitales.getFecha_creacion());
				timebox1.setWidth("90%");
				timebox1.setReadonly(true);
				celda1.appendChild(timebox1);
				fila.appendChild(celda1);

				Cell celda2 = new Cell();
				Doublebox doublebox2 = new Doublebox();
				doublebox2.setWidth("90%");
				doublebox2.setValue((control_signos_vitales.getTension_arterial() != null 
						&& !control_signos_vitales.getTension_arterial().isEmpty()) ? ConvertidorDatosUtil
						.convertirDato(control_signos_vitales.getTension_arterial()) : null);
				doublebox2.setReadonly(true);
				celda2.appendChild(doublebox2);
				fila.appendChild(celda2);

				Cell celda3 = new Cell();
				Doublebox doublebox3 = new Doublebox();
				doublebox3.setWidth("90%");
				doublebox3.setValue((control_signos_vitales.getFrecuencia_cardiaca() != null 
						&& !control_signos_vitales.getFrecuencia_cardiaca().isEmpty()) ? ConvertidorDatosUtil
						.convertirDato(control_signos_vitales.getFrecuencia_cardiaca()) : null);
				doublebox3.setReadonly(true);
				celda3.appendChild(doublebox3);
				fila.appendChild(celda3);

				Cell celda4 = new Cell();
				Doublebox doublebox4 = new Doublebox();
				doublebox4.setWidth("90%");
				doublebox4.setValue((control_signos_vitales.getPulso() != null 
						&& !control_signos_vitales.getPulso().isEmpty()) ? ConvertidorDatosUtil
						.convertirDato(control_signos_vitales.getPulso()) : null);
				doublebox4.setReadonly(true);
				celda4.appendChild(doublebox4);
				fila.appendChild(celda4);

				Cell celda5 = new Cell();
				Doublebox doublebox5 = new Doublebox();
				doublebox5.setWidth("90%");
				doublebox5.setValue((control_signos_vitales.getFrecuencia_respiratoria() != null 
						&& !control_signos_vitales.getFrecuencia_respiratoria().isEmpty()) ? ConvertidorDatosUtil
						.convertirDato(control_signos_vitales.getFrecuencia_respiratoria()) : null);
				doublebox5.setReadonly(true);
				celda5.appendChild(doublebox5);
				fila.appendChild(celda5);

				Cell celda6 = new Cell();
				Doublebox doublebox6 = new Doublebox();
				doublebox6.setWidth("90%");
				doublebox6.setValue((control_signos_vitales.getTemperatura() != null 
						&& !control_signos_vitales.getTemperatura().isEmpty()) ? ConvertidorDatosUtil
						.convertirDato(control_signos_vitales.getTemperatura()) : null);
				doublebox6.setReadonly(true);
				celda6.appendChild(doublebox6);
				fila.appendChild(celda6);

				Cell celda7 = new Cell();
				Textbox textbox_seguimiento = new Textbox();
				textbox_seguimiento.setValue(control_signos_vitales.getObservacion());
				textbox_seguimiento.setReadonly(true);
				textbox_seguimiento.setWidth("90%");
				celda7.appendChild(textbox_seguimiento);
				fila.appendChild(celda7);
				
				
				Toolbarbutton imagen_prestador = new Toolbarbutton("",
						"/images/consentimiento.png");
				imagen_prestador.setTooltiptext("Ver Encargado");
				imagen_prestador.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {

								Usuarios informacion_usuario = cargarPrestador(control_signos_vitales.getCreacion_user());

								MensajesUtil.mensajeInformacion("Informacion ..",
										"Usuario creador: "+informacion_usuario.getNombres()+" "+informacion_usuario.getApellidos());
								
							}
						});
				
				fila.appendChild(imagen_prestador);
				
				
				gridControl.getRows().appendChild(fila);
				// numero_filas++;

			}
		}

	}
	 

	@Override
	public void initHistoria() throws Exception {
		
		if (admision != null) {
			editarPaciente();
			cargarPrestador();
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("identificacion", admision.getNro_identificacion());
			parameters.put("nro_ingreso", admision.getNro_ingreso());

			//log.info("parametros"+parameters);
			
			if(control_signos_vitalesService.existe(parameters)){
				//log.info("consultar");
				accionForm(true, "consultar");
				
			}else{
				//log.info("registrar");
				accionForm(true, "registrar");
			
			}
			
		}
		
	}


	@Override
	public void inicializarVista(String tipo) {
		
		
	}

	@Override
	public void cargarInformacion_paciente() {
		
		
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior)
			throws Exception {
		
		
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		
		
	}


}