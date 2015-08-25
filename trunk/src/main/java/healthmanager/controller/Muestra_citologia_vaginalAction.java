/*
 * muestra_citologia_vaginalAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Muestra_citologia_vaginal;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.service.Muestra_citologia_vaginalService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Muestra_citologia_vaginalAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Muestra_citologia_vaginalAction.class);

	private Muestra_citologia_vaginalService muestra_citologia_vaginalService;

	private Admision admision_seleccionada;
	private Opciones_via_ingreso opciones_via_ingreso;

	@Autowired
	private ConsecutivoService consecutivoService;
	
	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxPrimera;
	@View
	private Groupbox groupboxSegunda;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Toolbarbutton btnCancelar;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Textbox tbxIdentificacion;

	@View
	private Textbox tbxIdentificacio_unidad;
	@View
	private Radiogroup rdbDeteccion_cancer;
	@View
	private Datebox dtbxFum;
	@View
	private Intbox ibxPerinatales_g;
	@View
	private Intbox ibxPerinatales_p;
	@View
	private Intbox ibxPerinatales_c;
	@View
	private Intbox ibxPerinatales_a;
	@View
	private Intbox ibxPerinatales_v;
	@View
	private Checkbox chbCauterizacion_cevix;
	@View
	private Checkbox chbConizacion_cervix;
	@View
	private Checkbox chbHisterectomia;
	@View
	private Checkbox chbRadioterapia;
	@View
	private Checkbox chbPost_evento;
	@View
	private Checkbox chbEmbarazo;
	@View
	private Checkbox chbAnticoncepcion;
	@View
	private Checkbox chbDiu;
	@View
	private Checkbox chbPostmenopausia;
	@View
	private Radiogroup rdbResul_cita_anterio;
	@View
	private Checkbox chbAspecto_normal;
	@View
	private Checkbox chbAspecto_hipertrofico;
	@View
	private Checkbox chbAspecto_erosion;
	@View
	private Checkbox chbAspecto_leucorrea;
	@View
	private Checkbox chbAspecto_sangrado;
	@View
	private Checkbox chbAspecto_diu_visible;
	@View
	private Checkbox chbOtro;
	@View
	private Textbox tbxOtro_cual;
	@View
	private Radiogroup rdbMedico_o_enfermera;
	@View
	private Textbox tbxNombre_del_profesional;
	@View
	private Datebox dtbxFecha_toma;
	@View
	private Textbox tbxLaboratorio;
	@View
	private Datebox dtbxFecha_lectura;
	@View
	private Intbox ibxNro_placa;
	@View
	private Radiogroup rdbCalidad_muestra;
	@View
	private Textbox tbxEspecifique_motivo;
	@View
	private Radiogroup rdbEvaluacion_general;
	@View
	private Radiogroup rdbMicroorganismos;
	@View
	private Textbox tbxCual_otro;
	@View
	private Radiogroup rdbCelulas_escamosas;
	@View
	private Radiogroup rdbLesion_bajo_grado;
	@View
	private Radiogroup rdbLesion_alto_grado;
	@View
	private Checkbox chbEndocervicales;
	@View
	private Checkbox chbEndometriales;
	@View
	private Checkbox chbGlandulares;
	@View
	private Checkbox chbEdenocarcinomia_insitu;
	@View
	private Checkbox chbAdenocarcinoma;
	@View
	private Datebox dtbxFecha_control;
	@View
	private Datebox dtbxRepetir_citologia;
	@View
	private Radiogroup rdbColposcopia_biopsia;
	@View
	private Textbox tbxResponsable_toma;
	@View
	private Textbox tbxResponsable_lectura;
	@View
	private Toolbarbutton btGuardar;

	private boolean cobrar_cancer_seno;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "tbxAccion", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", };

	@Override
	public void init() {
		listarCombos();
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision_seleccionada = (Admision) map
					.get(IVias_ingreso.ADMISION_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			if (admision_seleccionada != null) {
				cargarInformacion_paciente();
			}
		}
		dtbxFum.setText("");
		dtbxFecha_control.setText("");
		dtbxRepetir_citologia.setText("");
		dtbxFecha_lectura.setText("");
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

//		listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
			throws Exception {
		tbxAccion.setValue(opciones_formulario.toString());
		if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
			onOpcionFormularioRegistrar();
		} else if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)
				|| opciones_formulario.equals(OpcionesFormulario.MOSTRAR)) {
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			mostrarDatos(dato);
			if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {
				FormularioUtil.deshabilitarComponentes(groupboxEditar, false,
						idsExcluyentes);
			}
		} else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
			onOpcionFormularioConsultar();
		}

	}

	
	public void imprimir() throws Exception {
		String nro_historia = consecutivoService.getZeroFill(infoPacientes.getCodigo_historia()+"", 10);
		
		Muestra_citologia_vaginal muestra_citologia_vaginal = new Muestra_citologia_vaginal();
		muestra_citologia_vaginal.setCodigo_empresa(admision_seleccionada.getCodigo_empresa());
		muestra_citologia_vaginal.setCodigo_sucursal(admision_seleccionada.getCodigo_sucursal());
		muestra_citologia_vaginal.setCodigo_historia(nro_historia);
		
		muestra_citologia_vaginal = getServiceLocator().getServicio(Muestra_citologia_vaginalService.class).consultar(muestra_citologia_vaginal);
		
		if(muestra_citologia_vaginal == null) {
			Messagebox.show("La Citología no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Citologia");
		paramRequest.put("codigo_historia", nro_historia);
		paramRequest.put("codigo_empresa", codigo_empresa);
		paramRequest.put("codigo_sucursal", codigo_sucursal);
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}
	
	
	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
		if (admision_seleccionada != null) {
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
		}
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(this, sw,
				new String[] { "northEditar" });
		if (!sw) {
			((Button) getFellow("btGuardar")).setDisabled(false);
			((Button) getFellow("btGuardar")).setImage("/images/Save16.gif");
		} else {
			((Button) getFellow("btGuardar")).setDisabled(true);
			((Button) getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		dtbxFum.setValue(null);
		dtbxFecha_control.setValue(null);
		dtbxRepetir_citologia.setValue(null);
		dtbxFecha_lectura.setValue(null);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		infoPacientes.validarInformacionPaciente();
		boolean valida = true;
		
		String mensaje = "Los campos marcados con (*) son obligatorios";
		dtbxFum.setStyle("text-transform:uppercase;background-color:white");
		
		if (dtbxFum.getText().trim().equals("")) {
			dtbxFum.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			mensaje = "Debe seleccionar la fecha de mestruacion de la paciente";
		}
		
		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres(),
					mensaje + "");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			
			if (admision_seleccionada != null) {
				parameters.put("identificacion",
						admision_seleccionada.getNro_identificacion());
			}

			
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			muestra_citologia_vaginalService.setLimit("limit 25 offset 0");

			List<Muestra_citologia_vaginal> lista_datos = muestra_citologia_vaginalService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Muestra_citologia_vaginal muestra_citologia_vaginal : lista_datos) {
				rowsResultado.appendChild(crearFilas(muestra_citologia_vaginal,
						this));
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

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Muestra_citologia_vaginal muestra_citologia_vaginal = (Muestra_citologia_vaginal) objeto;

	
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(muestra_citologia_vaginal
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(muestra_citologia_vaginal
				.getIdentificacion() + ""));
		fila.appendChild(new Label(admision_seleccionada.getPaciente().getNombre1()
				+ (admision_seleccionada.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision_seleccionada.getPaciente().getNombre2()) + " "
				+ admision_seleccionada.getPaciente().getApellido1() + " "
				+ admision_seleccionada.getPaciente().getApellido2() + ""));
		fila.appendChild(new Label(muestra_citologia_vaginal.getFecha_ingreso()
				+ ""));

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar citología");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						groupboxConsulta.setVisible(false);
						groupboxEditar.setVisible(true);
						cargarInformacion_paciente();
						mostrarDatos(muestra_citologia_vaginal);
					}
				});
		hlayout.appendChild(btn_mostrar);

		btn_mostrar = new Toolbarbutton();
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			btn_mostrar.setVisible(false);
		}
		btn_mostrar.setImage("/images/borrar.gif");
		btn_mostrar.setTooltiptext("Eliminar");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.addEventListener(Events.ON_CLICK,
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
													eliminarDatos(muestra_citologia_vaginal);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(new Space());
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Muestra_citologia_vaginal muestra_citologia_vaginal = new Muestra_citologia_vaginal();
				muestra_citologia_vaginal.setCodigo_empresa(empresa
						.getCodigo_empresa());
				muestra_citologia_vaginal.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				
//				infoPacientes.setCodigo_historia(muestra_citologia_vaginal
//						.getCodigo_historia());
				String nro_historia = consecutivoService.getZeroFill(infoPacientes.getCodigo_historia()+"", 10);
				muestra_citologia_vaginal.setCodigo_historia(nro_historia);
				muestra_citologia_vaginal.setFecha_ingreso(infoPacientes
						.getFecha_inicial());
				muestra_citologia_vaginal
						.setIdentificacion(admision_seleccionada
								.getNro_identificacion());
				muestra_citologia_vaginal.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());
				muestra_citologia_vaginal
						.setIdentificacio_unidad(tbxIdentificacio_unidad
								.getValue());
				muestra_citologia_vaginal.setCodigo_prestador(usuarios.getCodigo());
				muestra_citologia_vaginal
						.setDeteccion_cancer(rdbDeteccion_cancer
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal.setFum(new Timestamp(dtbxFum
						.getValue().getTime()));
				muestra_citologia_vaginal.setPerinatales_g((ibxPerinatales_g
						.getValue() != null ? ibxPerinatales_g.getValue() : 0));
				muestra_citologia_vaginal.setPerinatales_p((ibxPerinatales_p
						.getValue() != null ? ibxPerinatales_p.getValue() : 0));
				muestra_citologia_vaginal.setPerinatales_c((ibxPerinatales_c
						.getValue() != null ? ibxPerinatales_c.getValue() : 0));
				muestra_citologia_vaginal.setPerinatales_a((ibxPerinatales_a
						.getValue() != null ? ibxPerinatales_a.getValue() : 0));
				muestra_citologia_vaginal.setPerinatales_v((ibxPerinatales_v
						.getValue() != null ? ibxPerinatales_v.getValue() : 0));
				muestra_citologia_vaginal
						.setCauterizacion_cevix(chbCauterizacion_cevix
								.isChecked());
				muestra_citologia_vaginal
						.setConizacion_cervix(chbConizacion_cervix.isChecked());
				muestra_citologia_vaginal.setHisterectomia(chbHisterectomia
						.isChecked());
				muestra_citologia_vaginal.setRadioterapia(chbRadioterapia
						.isChecked());
				muestra_citologia_vaginal.setPost_evento(chbPost_evento
						.isChecked());
				muestra_citologia_vaginal.setEmbarazo(chbEmbarazo.isChecked());
				muestra_citologia_vaginal.setAnticoncepcion(chbAnticoncepcion
						.isChecked());
				muestra_citologia_vaginal.setDiu(chbDiu.isChecked());
				muestra_citologia_vaginal.setPostmenopausia(chbPostmenopausia
						.isChecked());
				muestra_citologia_vaginal
						.setResul_cita_anterio(rdbResul_cita_anterio
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal.setAspecto_normal(chbAspecto_normal
						.isChecked());
				muestra_citologia_vaginal
						.setAspecto_hipertrofico(chbAspecto_hipertrofico
								.isChecked());
				muestra_citologia_vaginal.setAspecto_erosion(chbAspecto_erosion
						.isChecked());
				muestra_citologia_vaginal
						.setAspecto_leucorrea(chbAspecto_leucorrea.isChecked());
				muestra_citologia_vaginal
						.setAspecto_sangrado(chbAspecto_sangrado.isChecked());
				muestra_citologia_vaginal
						.setAspecto_diu_visible(chbAspecto_diu_visible
								.isChecked());
				muestra_citologia_vaginal.setOtro(chbOtro.isChecked());
				muestra_citologia_vaginal.setOtro_cual(tbxOtro_cual.getValue());
				muestra_citologia_vaginal
						.setMedico_o_enfermera(rdbMedico_o_enfermera
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal
						.setNombre_del_profesional(tbxNombre_del_profesional
								.getValue());
				muestra_citologia_vaginal.setFecha_toma(new Timestamp(
						dtbxFecha_toma.getValue().getTime()));
				muestra_citologia_vaginal.setLaboratorio(tbxLaboratorio
						.getValue());
				if(dtbxFecha_lectura.getValue() != null){
				muestra_citologia_vaginal.setFecha_lectura(new Timestamp(
						dtbxFecha_lectura.getValue().getTime()));
				}
				muestra_citologia_vaginal
						.setNro_placa((ibxNro_placa.getValue() != null ? ibxNro_placa
								.getValue() + ""
								: ""));
				muestra_citologia_vaginal.setCalidad_muestra(rdbCalidad_muestra
						.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal
						.setEspecifique_motivo(tbxEspecifique_motivo.getValue());
				muestra_citologia_vaginal
						.setEvaluacion_general(rdbEvaluacion_general
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal.setMicroorganismos(rdbMicroorganismos
						.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal.setCual_otro(tbxCual_otro.getValue());
				muestra_citologia_vaginal
						.setCelulas_escamosas(rdbCelulas_escamosas
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal
						.setLesion_bajo_grado(rdbLesion_bajo_grado
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal
						.setLesion_alto_grado(rdbLesion_alto_grado
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal.setEndocervicales(chbEndocervicales
						.isChecked());
				muestra_citologia_vaginal.setEndometriales(chbEndometriales
						.isChecked());
				muestra_citologia_vaginal.setGlandulares(chbGlandulares
						.isChecked());
				muestra_citologia_vaginal
						.setEdenocarcinomia_insitu(chbEdenocarcinomia_insitu
								.isChecked());
				muestra_citologia_vaginal.setAdenocarcinoma(chbAdenocarcinoma
						.isChecked());
				if(dtbxFecha_control.getValue() != null){
				muestra_citologia_vaginal.setFecha_control(new Timestamp(
						dtbxFecha_control.getValue().getTime()));
				}
				if(dtbxRepetir_citologia.getValue() != null){
				muestra_citologia_vaginal.setRepetir_citologia(new Timestamp(
						dtbxRepetir_citologia.getValue().getTime()));
				}
				muestra_citologia_vaginal
						.setColposcopia_biopsia(rdbColposcopia_biopsia
								.getSelectedItem().getValue().toString());
				muestra_citologia_vaginal
						.setResponsable_toma(tbxResponsable_toma.getValue());
				muestra_citologia_vaginal
						.setResponsable_lectura(tbxResponsable_lectura
								.getValue());
				muestra_citologia_vaginal.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				muestra_citologia_vaginal.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				muestra_citologia_vaginal.setCreacion_user(usuarios.getCodigo()
						.toString());
				muestra_citologia_vaginal.setDelete_date(null);
				muestra_citologia_vaginal.setUltimo_user(usuarios.getCodigo()
						.toString());
				muestra_citologia_vaginal.setDelete_user(null);

				if (!admision_seleccionada.getAtendida()) {
					facturarCancerSeno();
				}

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("muestra_citologia_vaginal",
						muestra_citologia_vaginal);
				params.put("accion", tbxAccion.getValue());
				params.put("admision", admision_seleccionada);
				params.put("cobrar_cancer_seno", cobrar_cancer_seno);

				muestra_citologia_vaginal = getServiceLocator().getServicio(
						Muestra_citologia_vaginalService.class).guardar(params);
				infoPacientes.setCodigo_historia(new Long(muestra_citologia_vaginal.getCodigo_historia())); 

				tbxAccion.setValue("modificar");
				
				if (admision_seleccionada.getAtendida()) {
				FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
						 new String[] { "northEditar" });
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			MensajesUtil.mensajeError(e, null,
					Muestra_citologia_vaginalAction.this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Muestra_citologia_vaginal muestra_citologia_vaginal = (Muestra_citologia_vaginal) obj;
		try {
			infoPacientes.setCodigo_historia(new Long(muestra_citologia_vaginal
					.getCodigo_historia())); 
			infoPacientes.setFecha_inicio(muestra_citologia_vaginal
					.getFecha_ingreso());
			tbxIdentificacion.setValue(muestra_citologia_vaginal
					.getIdentificacion());

			initMostrar_datos(muestra_citologia_vaginal);

			tbxIdentificacio_unidad.setValue(muestra_citologia_vaginal
					.getIdentificacio_unidad());
			Utilidades.seleccionarRadio(rdbDeteccion_cancer,
					muestra_citologia_vaginal.getDeteccion_cancer());
			dtbxFum.setValue(muestra_citologia_vaginal.getFum());
			ibxPerinatales_g.setValue(muestra_citologia_vaginal
					.getPerinatales_g());
			ibxPerinatales_p.setValue(muestra_citologia_vaginal
					.getPerinatales_p());
			ibxPerinatales_c.setValue(muestra_citologia_vaginal
					.getPerinatales_c());
			ibxPerinatales_a.setValue(muestra_citologia_vaginal
					.getPerinatales_a());
			ibxPerinatales_v.setValue(muestra_citologia_vaginal
					.getPerinatales_v());
			chbCauterizacion_cevix.setChecked(muestra_citologia_vaginal
					.getCauterizacion_cevix());
			chbConizacion_cervix.setChecked(muestra_citologia_vaginal
					.getConizacion_cervix());
			chbHisterectomia.setChecked(muestra_citologia_vaginal
					.getHisterectomia());
			chbRadioterapia.setChecked(muestra_citologia_vaginal
					.getRadioterapia());
			chbPost_evento.setChecked(muestra_citologia_vaginal
					.getPost_evento());
			chbEmbarazo.setChecked(muestra_citologia_vaginal.getEmbarazo());
			chbAnticoncepcion.setChecked(muestra_citologia_vaginal
					.getAnticoncepcion());
			chbDiu.setChecked(muestra_citologia_vaginal.getDiu());
			chbPostmenopausia.setChecked(muestra_citologia_vaginal
					.getPostmenopausia());
			Utilidades.seleccionarRadio(rdbResul_cita_anterio,
					muestra_citologia_vaginal.getResul_cita_anterio());
			chbAspecto_normal.setChecked(muestra_citologia_vaginal
					.getAspecto_normal());
			chbAspecto_hipertrofico.setChecked(muestra_citologia_vaginal
					.getAspecto_hipertrofico());
			chbAspecto_erosion.setChecked(muestra_citologia_vaginal
					.getAspecto_erosion());
			chbAspecto_leucorrea.setChecked(muestra_citologia_vaginal
					.getAspecto_leucorrea());
			chbAspecto_sangrado.setChecked(muestra_citologia_vaginal
					.getAspecto_sangrado());
			chbAspecto_diu_visible.setChecked(muestra_citologia_vaginal
					.getAspecto_diu_visible());
			chbOtro.setChecked(muestra_citologia_vaginal.getOtro());
			tbxOtro_cual.setValue(muestra_citologia_vaginal.getOtro_cual());
			Utilidades.seleccionarRadio(rdbMedico_o_enfermera,
					muestra_citologia_vaginal.getMedico_o_enfermera());
			tbxNombre_del_profesional.setValue(muestra_citologia_vaginal
					.getNombre_del_profesional());
			dtbxFecha_toma.setValue(muestra_citologia_vaginal.getFecha_toma());
			tbxLaboratorio.setValue(muestra_citologia_vaginal.getLaboratorio());
			dtbxFecha_lectura.setValue(muestra_citologia_vaginal
					.getFecha_lectura());
			ibxNro_placa
					.setValue((muestra_citologia_vaginal.getNro_placa() != null && !muestra_citologia_vaginal
							.getNro_placa().isEmpty()) ? Integer
							.parseInt(muestra_citologia_vaginal.getNro_placa())
							: null);
			Utilidades.seleccionarRadio(rdbCalidad_muestra,
					muestra_citologia_vaginal.getCalidad_muestra());
			tbxEspecifique_motivo.setValue(muestra_citologia_vaginal
					.getEspecifique_motivo());
			Utilidades.seleccionarRadio(rdbEvaluacion_general,
					muestra_citologia_vaginal.getEvaluacion_general());
			Utilidades.seleccionarRadio(rdbMicroorganismos,
					muestra_citologia_vaginal.getMicroorganismos());
			tbxCual_otro.setValue(muestra_citologia_vaginal.getCual_otro());
			Utilidades.seleccionarRadio(rdbCelulas_escamosas,
					muestra_citologia_vaginal.getCelulas_escamosas());
			Utilidades.seleccionarRadio(rdbLesion_bajo_grado,
					muestra_citologia_vaginal.getLesion_bajo_grado());
			Utilidades.seleccionarRadio(rdbLesion_alto_grado,
					muestra_citologia_vaginal.getLesion_alto_grado());
			chbEndocervicales.setChecked(muestra_citologia_vaginal
					.getEndocervicales());
			chbEndometriales.setChecked(muestra_citologia_vaginal
					.getEndometriales());
			chbGlandulares.setChecked(muestra_citologia_vaginal
					.getGlandulares());
			chbEdenocarcinomia_insitu.setChecked(muestra_citologia_vaginal
					.getEdenocarcinomia_insitu());
			chbAdenocarcinoma.setChecked(muestra_citologia_vaginal
					.getAdenocarcinoma());
			dtbxFecha_control.setValue(muestra_citologia_vaginal
					.getFecha_control());
			dtbxRepetir_citologia.setValue(muestra_citologia_vaginal
					.getRepetir_citologia());
			Utilidades.seleccionarRadio(rdbColposcopia_biopsia,
					muestra_citologia_vaginal.getColposcopia_biopsia());
			tbxResponsable_toma.setValue(muestra_citologia_vaginal
					.getResponsable_toma());
			tbxResponsable_lectura.setValue(muestra_citologia_vaginal
					.getResponsable_lectura());

			// Mostramos la vista //
			// tbxAccion.setText("modificar");
			// accionForm(true, tbxAccion.getText());

			if (tbxAccion.getValue().equals(
					OpcionesFormulario.REGISTRAR.toString())) {
				deshabilitarCampos(false);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Muestra_citologia_vaginal muestra_citologia_vaginal = (Muestra_citologia_vaginal) obj;
		try {
			int result = muestra_citologia_vaginalService
					.eliminar(muestra_citologia_vaginal);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	@Override
	public void initHistoria() throws Exception {

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			// via_ingreso = admision_seleccionada.getVia_ingreso();
			accionForm(OpcionesFormulario.CONSULTAR, null);
			btnCancelar.setVisible(true);
			groupboxPrimera.setVisible(true);
			FormularioUtil.deshabilitarComponentes(groupboxPrimera, true,
					new String[] { " " });
			groupboxSegunda.setVisible(true);
		} else {
			if (admision_seleccionada != null) {
				toolbarbuttonPaciente_admisionado1
						.setLabel(admision_seleccionada.getPaciente()
								.getNombreCompleto());
				toolbarbuttonPaciente_admisionado2
						.setLabel(admision_seleccionada.getPaciente()
								.getNombreCompleto());

				if (admision_seleccionada.getAtendida()) {
					opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;

					Muestra_citologia_vaginal muestra = new Muestra_citologia_vaginal();
					muestra.setCodigo_empresa(empresa.getCodigo_empresa());
					muestra.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					muestra.setNro_ingreso(admision_seleccionada
							.getNro_ingreso());
					muestra.setIdentificacion(admision_seleccionada
							.getNro_identificacion());
					muestra = getServiceLocator().getServicio(
							Muestra_citologia_vaginalService.class)
							.consultarPorFiltros(muestra);

					if (muestra != null) {
						accionForm(OpcionesFormulario.MOSTRAR, muestra);
						groupboxPrimera.setVisible(true);
						FormularioUtil.deshabilitarComponentes(groupboxPrimera,
								true, new String[] { " " });
						groupboxSegunda.setVisible(true);
						infoPacientes.cargarInformacion(admision_seleccionada,
								this, null);
						infoPacientes.setCodigo_historia(new Long(muestra
								.getCodigo_historia())); 
					} else {
						groupboxEditar.setVisible(false);
						throw new ValidacionRunTimeException(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				} else {
					if (opciones_via_ingreso
							.equals(Opciones_via_ingreso.REGISTRAR)) {
						accionForm(OpcionesFormulario.REGISTRAR, null);
						groupboxPrimera.setVisible(true);
						btnCancelar.setVisible(false);
					} else {
						accionForm(OpcionesFormulario.CONSULTAR, null);
						btnCancelar.setVisible(true);
					}
				}

			}

		}
	}

	@Override
	public void inicializarVista(String tipo) {

	}

	@Override
	public void cargarInformacion_paciente() {
		tbxIdentificacio_unidad.setValue(centro_atencion_session
				.getCodigo_centro()
				+ "-"
				+ centro_atencion_session.getNombre_centro());
		tbxNombre_del_profesional.setValue(usuarios.getNombres() + " "
				+ usuarios.getApellidos());
		//log.info("usuario " + usuarios.getNombres());

		if (!admision_seleccionada.getAtendida()) {
			Notificaciones
					.mostrarNotificacionInformacion(
							"Enfermer@",
							"Recuerde practicar exámen de mama y educacion al paciente",
							10000);
		}

		infoPacientes.cargarInformacion(admision_seleccionada, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() {
					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
		

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			
//				FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
//						 new String[] { "northEditar" });				

			((Button) getFellow("btGuardar")).setVisible(true);
		} else {
			((Button) getFellow("btGuardar")).setVisible(true);
		}

	}

	public void deshabilitar_conRadio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void deshabilitar_conCheck(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {

			FormularioUtil.deshabilitarComponentes_conCheckbox(checkbox,
					abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void facturarCancerSeno() {
		Messagebox
				.show("Le practicó exámen de mama y educacion al paciente? \n está segur@ de haberlo realizado?",
						"Cancer de seno", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									cobrar_cancer_seno = true;
								}
							}
						});
	}

}