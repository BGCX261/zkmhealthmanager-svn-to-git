/*
 * Hisc_servicios_amigablesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_servicios_amigables;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Hisc_servicios_amigablesService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.TannerMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_servicios_amigablesAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_servicios_amigablesAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// Componentes //
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
	@View
	private Div divModuloOrdenamiento;
	@View
	private Div divModuloFarmacologico;
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;
	@View
	private Toolbarbutton btnCancelar;

	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	@View
	private Listbox lbxAtendida;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Radiogroup rdbEts;
	@View
	private Textbox tbxCual_ets;
	@View
	private Intbox ibxObstetricos_g;
	@View
	private Intbox ibxObstetricos_p;
	@View
	private Intbox ibxObstetricos_a;
	@View
	private Intbox ibxObstetricos_c;
	@View
	private Intbox ibxObstetricos_v;
	private Hisc_servicios_amigables historia_anterior;
	@View
	private Datebox dtbxFum;
	@View
	private Doublebox dbxCardiaca;
	@View
	private Doublebox dbxRespiratoria;
	@View
	private Doublebox dbxPeso;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxPresion;
	@View
	private Doublebox dbxPresion2;
	@View
	private Doublebox dbxImc;
	@View
	private Textbox tbxEstado_tanner;
	@View
	private Textbox tbxPlan_intervencion;

	private String tipo_historia;
	private Long codigo_historia_anterior;
	private boolean cobrar_agudeza;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2" };

	// Ajustar a Macro
	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;

	@View
	private Toolbarbutton btGuardar;

	@View
	private Listbox lbxTipo_historia;

	// Campos ocultos Gineco-Obstetricos
	@View
	private Row rowObstetricos;
	@View
	private Row rowObstetricos2;

	// Campos Ocultos
	@View
	private Label lbCual_ets;
	@View
	private Datebox dtbxMenarca;
	@View
	private Datebox dtbxEsperma;
	@View
	private Radiogroup rdbCiclos_regulares;
	@View
	private Radiogroup rdbDismenorrea;
	@View
	private Radiogroup rdbFlujo_patologico;
	@View
	private Textbox tbxObservacion;
	@View
	private Radiogroup rdbConoce_corresponde;
	@View
	private Radiogroup rdbEs_confiable;
	@View
	private Row rowEstadioTanner1;
	@View
	private Row rowEstadioTanner2;

	@View(isMacro = true)
	private TannerMacro macroTanner;

	public boolean hbRealizado;
	public boolean htoRealizado;

	private String nro_ingreso_admision;

	@View
	private Toolbarbutton btnImprimir;

	public void habilitarIntbox(Radiogroup r, Intbox t) {
		if (r.getSelectedItem().getValue().equals("1")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void habilitarTexboxRadio(Radiogroup r, Textbox t) {
		if (r.getSelectedItem().getValue().equals("2")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void validarcheck() {
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if (edad >= 10 && edad <= 14) {
		}
	}

	public void habilitarTexboxChecbo(Checkbox c, Textbox t) {
		if (c.isChecked()) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void alarmaExamenFisicoTemperatura() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 80 && dbxCardiaca.getValue() <= 86)
				&& (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		}
		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 60 && dbxCardiaca.getValue() <= 80)
				&& (edad > 16)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:red");
		}

	}

	public void alarmaExamenFisicoRespiracion() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() >= 18 && dbxRespiratoria
						.getValue() <= 20) && (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		}
		if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() >= 16 && dbxRespiratoria
						.getValue() <= 20) && (edad > 16)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	public void alarmaExamenTensionSistolica() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxPresion.getText() != "")
				&& (dbxPresion.getValue() == (edad + 100))
				&& (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);

		}
		if ((dbxPresion.getText() != "")
				&& (dbxPresion.getValue() == (edad + 100) && (edad > 16))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		}

	}

	public void alarmaExamenTensionDiastolica() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxPresion2.getText() != "")
				&& (dbxPresion2.getValue() == (((edad + 100) / 2) + 10) && (edad >= 10 && edad <= 15))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);

		}
		if ((dbxPresion2.getText() != "")
				&& (dbxPresion2.getValue() == (((edad + 100) / 2) + 10) && (edad > 16))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		}

	}

	public void habilitarlistbox(Listbox l, Textbox t) {
		if (l.getSelectedItem().getValue().equals("3")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	@Override
	public void init() {
		try {
			listarCombos();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);

			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			Integer edad = Integer.valueOf(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(admision.getPaciente()
									.getFecha_nacimiento()), "1", false));
			if (edad >= 10 && edad <= 17) {
				if (admision.getPaciente().getSexo().equals("MASCULINO")
						|| admision.getPaciente().getSexo().equals("M")) {
					macroTanner.mostrarFilasMasculino();
					rowEstadioTanner1.setVisible(true);
					// rowEstadioTanner2.setVisible(true);
				}
				if (admision.getPaciente().getSexo().equals("FEMENINO")
						|| admision.getPaciente().getSexo().equals("F")) {
					macroTanner.mostrarFilasFemenino();
					rowEstadioTanner1.setVisible(true);
					// rowEstadioTanner2.setVisible(true);
				}
			}
		}
	}

	public void listarCombos() {
		listarParameter();
		listarAtendida();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void listarAtendida() {
		lbxAtendida.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue(false);
		listitem.setLabel("Pendiente");
		lbxAtendida.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue(true);
		listitem.setLabel("Atendida");
		lbxAtendida.appendChild(listitem);

		if (lbxAtendida.getItemCount() > 0) {
			lbxAtendida.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
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

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
		if (admision != null) {

			this.nro_ingreso_admision = admision.getNro_ingreso();
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			// valida_admision = null;
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
		}

		btnImprimir.setVisible(false);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	public void limpiarValidar() {
		tbxMotivo_consulta
				.setStyle("text-transform:uppercase;background-color:white");
		dbxCardiaca.setStyle("background-color:white");
		dbxRespiratoria.setStyle("background-color:white");
		dbxPeso.setStyle("background-color:white");
		dbxTalla.setStyle("background-color:white");
		dbxPresion.setStyle("background-color:white");
		dbxPresion2.setStyle("background-color:white");
		dbxImc.setStyle("background-color:white");
		tbxEstado_tanner
				.setStyle("text-transform:uppercase;background-color:white");
		tbxPlan_intervencion
				.setStyle("text-transform:uppercase;background-color:white");
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		infoPacientes.validarInformacionPaciente();
		limpiarValidar();

		FormularioUtil.validarCamposObligatorios(tbxMotivo_consulta,
				dbxCardiaca, dbxRespiratoria, dbxPeso, dbxTalla, dbxPresion,
				dbxPresion2, dbxImc, tbxPlan_intervencion);

		boolean valida = receta_ripsAction.validarFormExterno();

		if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
			if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
				valida = macroAgudeza_visual.validarCamposObligatorios();
			}
		}

		if (valida)
			valida = orden_servicioAction.validarFormExterno();
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {

			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}

			if (parameter.equalsIgnoreCase("fecha_inicial")) {
				parameters.put("fecha_string", value);
			} else {
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");
			}

			if (lbxTipo_historia.getSelectedIndex() != 0) {
				parameters.put("tipo_historia", lbxTipo_historia
						.getSelectedItem().getValue());
			}

			getServiceLocator().getServicio(
					Hisc_servicios_amigablesService.class).setLimit(
					"limit 25 offset 0");

			List<Hisc_servicios_amigables> lista_datos = getServiceLocator()
					.getServicio(Hisc_servicios_amigablesService.class).listar(
							parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_servicios_amigables hisc_servicios_amigables : lista_datos) {
				rowsResultado.appendChild(crearFilas(hisc_servicios_amigables,
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

		final Hisc_servicios_amigables hisc_servicios_amigables = (Hisc_servicios_amigables) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_servicios_amigables
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_servicios_amigables.getIdentificacion()
				+ ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				hisc_servicios_amigables.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_servicios_amigables.getTipo_historia()
				.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
				: "CONTROL"));

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar historia Clinica");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(hisc_servicios_amigables);
					}
				});
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);
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
													eliminarDatos(hisc_servicios_amigables);
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

	public Hisc_servicios_amigables getBean() {
		Hisc_servicios_amigables hisc_servicios_amigables = new Hisc_servicios_amigables();
		hisc_servicios_amigables.setCodigo_empresa(empresa.getCodigo_empresa());
		hisc_servicios_amigables.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		hisc_servicios_amigables.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_servicios_amigables.setIdentificacion(admision
				.getNro_identificacion());
		hisc_servicios_amigables.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));
		hisc_servicios_amigables.setUltimo_update(new Timestamp((new Date())
				.getTime()));
		hisc_servicios_amigables.setNro_ingreso(admision.getNro_ingreso());
		hisc_servicios_amigables.setMotivo_consulta(tbxMotivo_consulta
				.getValue());
		hisc_servicios_amigables.setTa_sistolica(!dbxPresion.getText()
				.isEmpty() ? dbxPresion.getValue() + "" : "");
		hisc_servicios_amigables.setTa_diastolica(!dbxPresion2.getText()
				.isEmpty() ? dbxPresion2.getValue() + "" : "");

		hisc_servicios_amigables.setFrec_cardiaca(!dbxCardiaca.getText()
				.isEmpty() ? dbxCardiaca.getValue() + "" : "");
		hisc_servicios_amigables.setFrec_respiratoria(!dbxRespiratoria
				.getText().isEmpty() ? dbxRespiratoria.getValue() + "" : "");

		hisc_servicios_amigables
				.setTalla(!dbxTalla.getText().isEmpty() ? dbxTalla.getValue()
						+ "" : "");
		hisc_servicios_amigables.setPeso(!dbxPeso.getText().isEmpty() ? dbxPeso
				.getValue() + "" : "");
		hisc_servicios_amigables.setImc(!dbxImc.getText().isEmpty() ? dbxImc
				.getValue() + "" : "");

		hisc_servicios_amigables.setTanner_1(macroTanner.getValorFila1());
		hisc_servicios_amigables.setTanner_2(macroTanner.getValorFila2());
		hisc_servicios_amigables.setTanner_3(macroTanner.getValorFila3());
		hisc_servicios_amigables.setTanner_4(macroTanner.getValorFila4());

		hisc_servicios_amigables.setPlan_intervencion(tbxPlan_intervencion
				.getText());
		hisc_servicios_amigables.setGestaciones(!ibxObstetricos_g.getText()
				.isEmpty() ? ibxObstetricos_g.getValue() + "" : "");
		hisc_servicios_amigables.setPartos(!ibxObstetricos_p.getText()
				.isEmpty() ? ibxObstetricos_p.getValue() + "" : "");
		hisc_servicios_amigables.setAbortos(!ibxObstetricos_a.getText()
				.isEmpty() ? ibxObstetricos_a.getValue() + "" : "");
		hisc_servicios_amigables.setCesareas(!ibxObstetricos_c.getText()
				.isEmpty() ? ibxObstetricos_c.getValue() + "" : "");
		hisc_servicios_amigables.setNacidos_vivos(!ibxObstetricos_v.getText()
				.isEmpty() ? ibxObstetricos_v.getValue() + "" : "");

		hisc_servicios_amigables.setMenarca(new Timestamp(dtbxMenarca
				.getValue().getTime()));
		hisc_servicios_amigables.setEsperma(new Timestamp(dtbxEsperma
				.getValue().getTime()));
		hisc_servicios_amigables.setFlujo_patologico(rdbFlujo_patologico
				.getSelectedItem().getValue().toString());
		hisc_servicios_amigables.setCiclos_regulares(rdbCiclos_regulares
				.getSelectedItem().getValue().toString());
		hisc_servicios_amigables.setDismenorrea(rdbDismenorrea
				.getSelectedItem().getValue().toString());

		hisc_servicios_amigables.setFum(new Timestamp(dtbxEsperma.getValue()
				.getTime()));
		hisc_servicios_amigables.setFum_no_conoce(rdbConoce_corresponde
				.getSelectedItem().getValue().toString());
		hisc_servicios_amigables.setFum_es_confiable(rdbEs_confiable
				.getSelectedItem().getValue().toString());
		hisc_servicios_amigables.setIts(rdbEts.getSelectedItem().getValue()
				.toString());
		hisc_servicios_amigables.setCual_its(tbxCual_ets.getValue());
		hisc_servicios_amigables.setObservaciones(tbxObservacion.getValue());
		hisc_servicios_amigables.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_servicios_amigables.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_servicios_amigables.setCreacion_user(usuarios.getCodigo()
				.toString());
		hisc_servicios_amigables.setDelete_date(null);
		hisc_servicios_amigables
				.setUltimo_user(usuarios.getCodigo().toString());
		hisc_servicios_amigables.setDelete_user(null);
		hisc_servicios_amigables.setTipo_historia(tipo_historia);

		if (historia_anterior != null) {
			hisc_servicios_amigables
					.setCodigo_historia_anterior(historia_anterior
							.getCodigo_historia());
		}

		return hisc_servicios_amigables;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {

			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Hisc_servicios_amigables hisc_servicios_amigables = getBean();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", hisc_servicios_amigables);
				datos.put("admision", admision);
				datos.put("anio_periodo", anio);
				datos.put("accion", tbxAccion.getValue());
				datos.put("anio", anio);
				datos.put("cita_seleccionada", cita);

				// hay que actualualizar los diagnosticos en la receta antes de
				// obtener el objeto receta
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();

				receta_ripsAction.actualizarDiagnosticos(impresion_diagnostica);

				Map<String, Object> mapReceta = receta_ripsAction
						.obtenerDatos();
				Map<String, Object> mapProcedimientos = orden_servicioAction
						.obtenerDatos();
				datos.put("receta_medica", mapReceta);
				datos.put("orden_servicio", mapProcedimientos);

				datos.put("cobrar_agudeza", cobrar_agudeza);
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());

				Agudeza_visual agudeza_visual = macroAgudeza_visual
						.obtenerAgudezaVisual();
				agudeza_visual.setAnio(anio);
				datos.put("agudeza_visual", agudeza_visual);

				datos.put("impresion_diagnostica", impresion_diagnostica);

				getServiceLocator().getServicio(
						Hisc_servicios_amigablesService.class).guardarDatos(
						datos);
				mostrarDatosAutorizacion(datos);
				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hisc_servicios_amigables
						.getCodigo_historia());
				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, false);
				// hay que llamar este metodo para validar que salga el boton
				// para imprimir despues de guardar la receta
				receta_ripsAction.validarParaImpresion();

				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
						.get("orden_servicio");
				if (orden_servicio != null)
					orden_servicioAction.mostrarDatos(orden_servicio);
				// hay que llamar este metodo para validar que salga el boton
				// para imprimir despues de guardar la orden
				orden_servicioAction.validarParaImpresion();

				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision, getInformacionClinica(), this);

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");

				btnImprimir.setVisible(true);

			}

		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			} else {
				log.error(((WrongValueException) e).getComponent().getId()
						+ " esta presentando error", e);
			}
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_servicios_amigables hisc_servicios_amigables = (Hisc_servicios_amigables) obj;
		try {
			this.nro_ingreso_admision = hisc_servicios_amigables
					.getNro_ingreso();
			infoPacientes.setCodigo_historia(hisc_servicios_amigables
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_servicios_amigables
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_servicios_amigables.getUltimo_update());

			initMostrar_datos(hisc_servicios_amigables);
			cargarInformacion_paciente();

			tbxMotivo_consulta.setValue(hisc_servicios_amigables
					.getMotivo_consulta());

			dbxPresion.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_servicios_amigables.getTa_sistolica()));
			dbxPresion2
					.setValue(ConvertidorDatosUtil
							.convertirDato(hisc_servicios_amigables
									.getTa_diastolica()));
			dbxCardiaca
					.setValue(ConvertidorDatosUtil
							.convertirDato(hisc_servicios_amigables
									.getFrec_cardiaca()));
			dbxRespiratoria.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_servicios_amigables
							.getFrec_respiratoria()));
			dbxPeso.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_servicios_amigables.getPeso()));
			dbxTalla.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_servicios_amigables.getTalla()));
			dbxImc.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_servicios_amigables.getImc()));

			macroTanner
					.cargarValorFila1(hisc_servicios_amigables.getTanner_1());
			macroTanner
					.cargarValorFila2(hisc_servicios_amigables.getTanner_2());
			macroTanner
					.cargarValorFila3(hisc_servicios_amigables.getTanner_3());
			macroTanner
					.cargarValorFila4(hisc_servicios_amigables.getTanner_4());

			tbxPlan_intervencion.setValue(hisc_servicios_amigables
					.getPlan_intervencion());

			ibxObstetricos_g
					.setValue((hisc_servicios_amigables.getGestaciones() != null && !hisc_servicios_amigables
							.getGestaciones().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getGestaciones())
							: null);
			ibxObstetricos_p
					.setValue((hisc_servicios_amigables.getPartos() != null && !hisc_servicios_amigables
							.getPartos().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getPartos())
							: null);
			ibxObstetricos_a
					.setValue((hisc_servicios_amigables.getAbortos() != null && !hisc_servicios_amigables
							.getAbortos().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getAbortos())
							: null);
			ibxObstetricos_c
					.setValue((hisc_servicios_amigables.getCesareas() != null && !hisc_servicios_amigables
							.getCesareas().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getCesareas())
							: null);
			ibxObstetricos_v.setValue((hisc_servicios_amigables
					.getNacidos_vivos() != null && !hisc_servicios_amigables
					.getNacidos_vivos().isEmpty()) ? Integer
					.parseInt(hisc_servicios_amigables.getNacidos_vivos())
					: null);

			if (hisc_servicios_amigables.getMenarca() != null) {
				dtbxMenarca.setValue(hisc_servicios_amigables.getMenarca());
			}

			if (hisc_servicios_amigables.getEsperma() != null) {
				dtbxEsperma.setValue(hisc_servicios_amigables.getEsperma());
			}

			Utilidades.seleccionarRadio(rdbFlujo_patologico,
					hisc_servicios_amigables.getFlujo_patologico());
			Utilidades.seleccionarRadio(rdbCiclos_regulares,
					hisc_servicios_amigables.getCiclos_regulares());
			Utilidades.seleccionarRadio(rdbDismenorrea,
					hisc_servicios_amigables.getDismenorrea());

			if (hisc_servicios_amigables.getFum() != null) {
				dtbxFum.setValue(hisc_servicios_amigables.getFum());
			}

			Utilidades.seleccionarRadio(rdbConoce_corresponde,
					hisc_servicios_amigables.getFum_no_conoce());
			Utilidades.seleccionarRadio(rdbEs_confiable,
					hisc_servicios_amigables.getFum_es_confiable());

			if (hisc_servicios_amigables.getIts().equals("1")) {
				lbCual_ets.setVisible(true);
				tbxCual_ets.setVisible(true);
				tbxCual_ets.setValue(hisc_servicios_amigables.getCual_its());
			} else {
				lbCual_ets.setVisible(false);
				tbxCual_ets.setVisible(false);
			}

			tbxObservacion
					.setValue(hisc_servicios_amigables.getObservaciones());

			tipo_historia = hisc_servicios_amigables.getTipo_historia();

			// Mostramos la vista //
			cargarImpresionDiagnostica(hisc_servicios_amigables);

			cargarAgudezaVisual(hisc_servicios_amigables);
			// calcularCoordenadas();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString())) {
				infoPacientes.setCodigo_historia(hisc_servicios_amigables
						.getCodigo_historia());
				inicializarVista(tipo_historia);
			}

			tbxAccion.setText("modificar");
			// accionForm(true, tbxAccion.getText());

			inicializarVista(tipo_historia);
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			btGuardar.setVisible(false);
			btnImprimir.setVisible(true);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_servicios_amigables hisc_servicios_amigables = (Hisc_servicios_amigables) obj;
		try {
			int result = getServiceLocator().getServicio(
					Hisc_servicios_amigablesService.class).eliminar(
					hisc_servicios_amigables);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void buscarOcupaciones(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
					"limit 25 offset 0");

			List<Ocupaciones> list = getServiceLocator()
					.getOcupacionesService().listar(parameters);

			lbx.getItems().clear();

			for (Ocupaciones dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getId() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
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

	public void seleccion_radio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			//System.Out.Println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {
					abstractComponent.setVisible(true);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}
					if (abstractComponent instanceof Listbox) {
						if (((Listbox) abstractComponent).getItemCount() > 0) {
							((Listbox) abstractComponent).setSelectedIndex(0);
						}
						Utilidades.listarElementoListbox(
								((Listbox) abstractComponent), true,
								getServiceLocator());

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						((Listbox) abstractComponent).getChildren().clear();
						for (int i = 0; i < ((Listbox) abstractComponent)
								.getItemCount(); i++) {
							Listitem listitem = ((Listbox) abstractComponent)
									.getItemAtIndex(i);
							if (listitem.getValue().toString().equals("")) {
								listitem.setSelected(true);
								i = ((Listbox) abstractComponent)
										.getItemCount();
							}
						}

					}
					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
					}

					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check3(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

				}
			}
		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check2(Checkbox checkbox, Checkbox checkbox2,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked() || checkbox2.isChecked()) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

				}
			}
		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}
				}
			}
		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion(Listbox listbox, int entero,
			Component[] abstractComponents) {
		try {
			String num = entero + "";
			for (Component abstractComponent : abstractComponents) {

				if (listbox.getSelectedItem().getValue().equals(num)) {
					abstractComponent.setVisible(true);
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}
					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {

		macroImpresion_diagnostica.inicializarMacro(this, admision,
				IVias_ingreso.SERVICIOS_AMIGABLES);
		macroAgudeza_visual.inicializarMacro(this, admision,
				IVias_ingreso.SERVICIOS_AMIGABLES);

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			accionForm(OpcionesFormulario.CONSULTAR, null);
			btnCancelar.setVisible(true);
		} else {
			if (admision != null) {
				//log.info("==> admision " + admision);
				toolbarbuttonPaciente_admisionado1.setLabel(admision
						.getPaciente().getNombreCompleto());
				toolbarbuttonPaciente_admisionado2.setLabel(admision
						.getPaciente().getNombreCompleto());

				if (admision.getAtendida()) {
					opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;

					Hisc_servicios_amigables hisc_servicios_amigables = new Hisc_servicios_amigables();
					hisc_servicios_amigables.setCodigo_empresa(empresa
							.getCodigo_empresa());
					hisc_servicios_amigables.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					hisc_servicios_amigables.setIdentificacion(admision
							.getNro_identificacion());
					hisc_servicios_amigables.setNro_ingreso(admision
							.getNro_ingreso());

					hisc_servicios_amigables = getServiceLocator().getServicio(
							Hisc_servicios_amigablesService.class).consultar(
							hisc_servicios_amigables);

					if (hisc_servicios_amigables != null) {
						accionForm(OpcionesFormulario.MOSTRAR,
								hisc_servicios_amigables);
						infoPacientes
								.setCodigo_historia(hisc_servicios_amigables
										.getCodigo_historia());
					} else {
						groupboxEditar.setVisible(false);
						throw new ValidacionRunTimeException(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				} else {
					if (opciones_via_ingreso
							.equals(Opciones_via_ingreso.REGISTRAR)) {
						accionForm(OpcionesFormulario.REGISTRAR, null);
						btnCancelar.setVisible(false);
					} else {
						accionForm(OpcionesFormulario.CONSULTAR, null);
						btnCancelar.setVisible(true);
					}
				}

			}
		}
		if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
			if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
				cobrar_agudeza = true;
				rowAgudeza_visual.setVisible(true);
				macroAgudeza_visual.validarCamposObligatorios();

			}
		}

	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {

		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						Paciente paciente = admision.getPaciente();
						//log.info("paciente" + paciente);

						if (paciente.getSexo().toUpperCase().equals("F")) {

							rowObstetricos.setVisible(true);
							rowObstetricos2.setVisible(false);

						} else {

							//log.info("2" + paciente.getSexo());
							rowObstetricos2.setVisible(true);
							rowObstetricos.setVisible(false);
						}

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<Hisc_servicios_amigables> listado_historias = getServiceLocator()
									.getServicio(
											Hisc_servicios_amigablesService.class)
									.listar(parametros);

							if (!listado_historias.isEmpty()) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));
								historia_anterior = listado_historias.get(0);
								codigo_historia_anterior = historia_anterior
										.getCodigo_historia();
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica de Control/Evolucion");
								admision.setPrimera_vez("N");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica por Primera Vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision.setPrimera_vez("S");
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Hisc_servicios_amigables hisc_servicios_amigables = new Hisc_servicios_amigables();
								hisc_servicios_amigables
										.setCodigo_empresa(empresa
												.getCodigo_empresa());
								hisc_servicios_amigables
										.setCodigo_sucursal(sucursal
												.getCodigo_sucursal());
								hisc_servicios_amigables
										.setCodigo_historia(codigo_historia_anterior);

								hisc_servicios_amigables = getServiceLocator()
										.getServicio(
												Hisc_servicios_amigablesService.class)
										.consultar(hisc_servicios_amigables);

								if (hisc_servicios_amigables != null) {
									cargarInformacion_historia_anterior(hisc_servicios_amigables);
								}

							}
						}

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Hisc_servicios_amigables hisc_servicios_amigables = (Hisc_servicios_amigables) historia_clinica;

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					idsExcluyentes);

			if (hisc_servicios_amigables.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica de Control/Evolucion");
			}

		} else {

			if (hisc_servicios_amigables.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica de Control/Evolucion");
			}

		}

		codigo_historia_anterior = hisc_servicios_amigables
				.getCodigo_historia_anterior();
		tipo_historia = hisc_servicios_amigables.getTipo_historia();
	}

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
			e.printStackTrace();

		}
	}

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales
					.onCalcularTension(TA_sistolica, TA_diastolica);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaCardiaca(Doublebox dbxFc) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaCardiaca(dbxFc,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaRespiratoria(Doublebox dbxFr) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaRespiratoria(dbxFr,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void deshabilitar_conCheck(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {

			FormularioUtil.deshabilitarComponentes_conCheckbox(checkbox,
					abstractComponents);

		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	private void cargarImpresionDiagnostica(
			Hisc_servicios_amigables hisc_servicios_amigables) throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);

		impresion_diagnostica.setCodigo_historia(hisc_servicios_amigables
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	public void onMostrarModuloFarmacologico() throws Exception {
		if (receta_ripsAction != null)
			receta_ripsAction.detach();
		//log.info("creando la ventana receta_ripsAction");

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso_admision);
		parametros.put("estado", admision.getEstado());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");

		parametros.put("ocultarRecomendaciones", "_Ocultar");

		receta_ripsAction = (Receta_rips_internoAction) Executions
				.createComponents("/pages/receta_rips_interno.zul", null,
						parametros);
		receta_ripsAction.inicializar(this);
		divModuloFarmacologico.appendChild(receta_ripsAction);

	}

	public void onMostrarModuloOrdenamiento() throws Exception {
		if (orden_servicioAction != null)
			orden_servicioAction.detach();
		// if (!cargo_farmacologico) {
		//log.info("creando la ventana receta_ripsAction");

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso_admision);
		parametros.put("estado", admision.getEstado());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }

	}

	private void cargarAgudezaVisual(
			Hisc_servicios_amigables hisc_servicios_amigables) throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_historia(hisc_servicios_amigables
				.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(hisc_servicios_amigables
				.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(hisc_servicios_amigables
				.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	public void deshabilitar_conRadio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("PYP SERVICIOS AMIGABLES");

		return serivicio1.toString();
	}

	@Override
	public String getInformacionClinica() throws WrongValueException {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(tbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- SIGNOS VITALES: \t");
		informacion_clinica.append("Tension arterial Sistólica: ")
				.append(dbxPresion.getValue() + (",")).append("\t")
				.append("Tension arterial Diastólica: ")
				.append(dbxPresion2.getValue() + (",")).append("\t")
				.append("Frecuencia cardiaca: ")
				.append(dbxCardiaca.getValue() + (",")).append("\n")
				.append("\t").append("Frecuencia Respiratoria: ")
				.append(dbxRespiratoria.getValue() + (",")).append("\t");
		informacion_clinica.append("Talla: ")
				.append(dbxTalla.getValue() + (",")).append("\t")
				.append("Peso: ").append(dbxPeso.getValue() + (","))
				.append("\t").append("IMC: ").append(dbxImc.getValue() + (","))
				.append("\n").append("\n");

		informacion_clinica.append("- DIAGNOSTICOS").append("\n");
		Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
				.obtenerImpresionDiagnostica();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica
				.append("\tDiagnostico principal: ")
				.append(impresion_diagnostica.getCie_principal())
				.append(" ")
				.append(cie != null ? cie.getNombre() : "")
				.append("\t")
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_principal(),
						"tipo_diagnostico", this)).append("\n");

		/* relacionado 1 */
		if (!impresion_diagnostica.getCie_relacionado1().isEmpty()) {
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado1());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica
					.append("\tRelacionado 1: ")
					.append(impresion_diagnostica.getCie_relacionado1())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado1(),
							"tipo_diagnostico", this)).append("\n");
		}
		if (!impresion_diagnostica.getCie_relacionado2().isEmpty()) {

			/* relacionado 2 */
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica
					.append("\tRelacionado 2: ")
					.append(impresion_diagnostica.getCie_relacionado2())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado2(),
							"tipo_diagnostico", this)).append("\n");

		}
		if (!impresion_diagnostica.getCie_relacionado3().isEmpty()) {

			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica
					.append("\tRelacionado 3: ")
					.append(impresion_diagnostica.getCie_relacionado3())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado3(),
							"tipo_diagnostico", this)).append("\n");
		}

		informacion_clinica.append("\n:");

		Map<String, Object> mapReceta = receta_ripsAction.obtenerDatos();

		List<Detalle_receta_rips> listaDetalles_horarios = (List<Detalle_receta_rips>) mapReceta
				.get("lista_detalle");
		//log.info("=====>lista detalle " + listaDetalles_horarios);

		if (!listaDetalles_horarios.isEmpty()) {
			informacion_clinica.append("-RECETA:").append("\t");

			for (Detalle_receta_rips detalle_receta_rips : listaDetalles_horarios) {
				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(detalle_receta_rips
						.getCodigo_empresa());
				articulo.setCodigo_sucursal(detalle_receta_rips
						.getCodigo_sucursal());
				articulo.setCodigo_articulo(detalle_receta_rips
						.getCodigo_articulo());

				articulo = getServiceLocator().getArticuloService().consultar(
						articulo);
				//log.info("====>articulo " + articulo);
				informacion_clinica
						.append(articulo != null ? articulo.getCantidad() : "")
						.append("\t")
						.append(articulo != null ? articulo.getNombre1() : "")
						.append("\t");

			}

			informacion_clinica.append("\n:");

			Map<String, Object> mapOrdenamiento = orden_servicioAction
					.obtenerDatos();
			//log.info("mapa ordenamiento ===> " + mapOrdenamiento);
			List<Detalle_orden> listaDetalle_orden = (List<Detalle_orden>) mapOrdenamiento
					.get("lista_detalle");
			//log.info("====> lista orden " + listaDetalle_orden);
			if (!listaDetalle_orden.isEmpty()) {
				informacion_clinica.append("-ORDENES:").append("\t");

				Procedimientos procedimiento = new Procedimientos();

				for (Detalle_orden detalle_orden : listaDetalle_orden) {
					procedimiento.setId_procedimiento(new Long(detalle_orden
							.getCodigo_procedimiento()));

					procedimiento = getServiceLocator()
							.getProcedimientosService()
							.consultar(procedimiento);
					//log.info("=====> soat " + procedimiento);
					informacion_clinica
							.append(procedimiento != null ? procedimiento
									.getDescripcion() : "")
							.append("- ")
							.append(detalle_orden != null ? detalle_orden
									.getUnidades() : "").append("\t");
				}
			}

		}

		return informacion_clinica.toString();

	}

	public void validarParaclinicosRealizados() {

	}

	public void cargarSignosVitalesEnfermera() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos
				.setNro_identificacion(admision.getNro_identificacion());
		enfermera_signos = getServiceLocator().getServicio(
				Enfermera_signosService.class).consultar(enfermera_signos);
		if (enfermera_signos != null) {

			dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxRespiratoria.setValue(enfermera_signos
					.getFrecuencia_respiratoria());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxPresion.setValue(enfermera_signos.getTa_sistolica());
			dbxPresion2.setValue(enfermera_signos.getTa_diastolica());
			dbxImc.setValue(enfermera_signos.getImc());

			calcularTA(dbxPresion, dbxPresion2);
			calcularFrecuenciaCardiaca(dbxCardiaca);
			calcularFrecuenciaRespiratoria(dbxRespiratoria);
			calcularIMC(dbxPeso, dbxTalla, dbxImc);

		}
	}

	public void imprimir() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		Long nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia != null) {
			String parametros_pagina = "?codigo_empresa=" + codigo_empresa;
			parametros_pagina += "&codigo_sucursal=" + codigo_sucursal;
			parametros_pagina += "&codigo_historia=" + nro_historia;
			parametros_pagina += "&nro_ingreso=" + admision.getNro_ingreso();
			parametros_pagina += "&nro_identificacion="
					+ admision.getNro_identificacion();
			Clients.evalJavaScript("window.open(\"" + request.getContextPath()
					+ "/pages/reports/hisc_servicios_amigables_reporte.zul"
					+ parametros_pagina + "\", '_blank');");
		}
	}

}
