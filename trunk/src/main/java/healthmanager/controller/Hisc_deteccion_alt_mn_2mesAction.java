/*
 * hisc_deteccion_alt_mn_2mesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Escala_del_desarrollo;
import healthmanager.modelo.bean.Hisc_deteccion_alt_mn_2mes;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Escala_del_desarrolloService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IHistorias_clinicas;
import com.framework.constantes.ITipos_coordenada;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.EscalaAbreviadaDesarrollo;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_deteccion_alt_mn_2mesAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	// private static Logger log =
	// Logger.getLogger(Hisc_deteccion_alt_mn_2mesAction.class);

	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;

	// Componentes //

	// Manuel
	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsultar;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;
	@View
	private Toolbarbutton btnCancelar;
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Listbox lbxParentesco;
	@View
	private Textbox tbxNombre_del_acompanante;
	@View
	private Textbox tbxNombre_del_padre;
	@View
	private Intbox ibxEdad_padre;
	@View
	private Textbox tbxNombre_de_la_madre;
	@View
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxMotivos_de_la_consulta;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Radiogroup rdbPuede_beber_del_pecho;
	@View
	private Radiogroup rdbHa_tenido_vomito;
	@View
	private Radiogroup rdbVomita_todo;
	@View
	private Radiogroup rdbDificultad_para_respirar;
	@View
	private Radiogroup rdbHa_tenido_fiebre;
	@View
	private Radiogroup rdbHa_tenido_hipotermia;
	@View
	private Radiogroup rdbHa_tenido_convulciones;
	@View
	private Radiogroup rdbTiene_diarrea;
	@View
	private Radiogroup rdbSangre_en_la_heces;
	@View
	private Textbox tbxOtros_observaciones;
	@View
	private Intbox ibxPerinatales_g;
	@View
	private Intbox ibxPerinatales_p;
	@View
	private Intbox ibxPerinatales_a;
	@View
	private Intbox ibxPerinatales_c;
	@View
	private Intbox ibxPerinatales_v;
	@View
	private Radiogroup rdbPerinatales_unico;
	// private Radiogroup rdbPerinatales_riesgo_bajo;
	@View
	private Radiogroup rdbPerinatales_riesgo_alto;
	@View
	private Radiogroup rdbParto_cst;
	@View
	private Radiogroup rdbControl_prenatal;
	@View
	private Listbox lbxHemoclasificacion;
	@View
	private Textbox tbxComplicaciones_embarazo_parto;
	@View
	private Doublebox dbxTalla_al_nacer;
	@View
	private Intbox ibxEdad_de_la_madre_al_nacer;
	@View
	private Textbox tbxObservaciones_perinatales;
	@View
	private Radiogroup rdbHospitalizacion_neonatal;
	@View
	private Intbox ibxApgar_al_mnto;
	@View
	private Radiogroup rdbReanimacion;
	@View
	private Doublebox dbxPeso_nacer;
	@View
	private Intbox ibxApgar_5_minutos;
	@View
	private Listbox lbxVdlr;
	@View
	private Radiogroup rdbInstitucioneal;
	@View
	private Textbox tbxTsh;
	@View
	private Intbox ibxSem_gestacion;
	@View
	private Textbox tbxPatologico_medico;
	@View
	private Textbox tbxPatologico_quirurgicos;
	@View
	private Textbox tbxPatologico_hospitalizaciones;
	@View
	private Textbox tbxPatologico_medicacion;
	// ------------- Jose
	
	@View
	private Textbox tbxPatologicos_alergicos;
	
	//-------------------
	@View
	private Radiogroup rdbDificultad_para_alimentarse;
	@View
	private Radiogroup rdbHa_dejado_de_comer;
	@View
	private Radiogroup rdbLactancia_materna;
	@View
	private Radiogroup rdbLactancia_materna_exclusiva;
	@View
	private Radiogroup rdbRecibe_otras_leches_alimentos;
	@View
	private Radiogroup rdbUtiliza_chupo;
	@View
	private Textbox tbxObservaciones_alimentario;
	@View
	private Radiogroup rdbAntitetanica_materna;
	@View
	private Radiogroup rdbBcg;
	@View
	private Radiogroup rdbHepb1;
	@View
	private Intbox ibxHns_vivos;
	@View
	private Intbox ibxHns_muertos_mn_5;
	@View
	private Intbox ibxHns_desnutridos_mn_5;
	@View
	private Textbox tbxCausas;
	@View
	private Radiogroup rdbSon_parientes_los_padres;
	@View
	private Radiogroup rdbFamiliar_problema_mental_fisico;
	@View
	private Textbox tbxPaternos_medico;
	@View
	private Textbox tbxMaternos_medico;
	@View
	private Textbox tbxPaternos_alergico;
	@View
	private Doublebox dbxPaternos_talla;
	@View
	private Textbox tbxMaternos_alergico;
	@View
	private Doublebox dbxMaternos_talla;
	@View
	private Textbox tbxOtros_antc_familiar;
	@View
	private Doublebox dbxExamen_fisico_peso;
	@View
	private Doublebox dbxExamen_fisico_talla;
	@View
	private Doublebox dbxExamen_fisico_perimetro_cflico;
	@View
	private Doublebox dbxExamen_fisico_fc;
	@View
	private Doublebox dbxExamen_fisico_fr;
	@View
	private Doublebox dbxExamen_fisico_temp;
	@View
	private Radiogroup rdbTendencia_peso;
	@View
	private Radiogroup rdbLatergico;
	@View
	private Radiogroup rdbIrritable;
	@View
	private Radiogroup rdbPalidez;
	@View
	private Radiogroup rdbCianosis;
	@View
	private Radiogroup rdbFr_ma60_mn30;
	@View
	private Radiogroup rdbFc_ma180_mn100;
	@View
	private Radiogroup rdbLctericia;
	@View
	private Radiogroup rdbApnea;
	@View
	private Radiogroup rdbAleteo_nasal;
	@View
	private Radiogroup rdbQuejido;
	@View
	private Radiogroup rdbEstribor;
	@View
	private Radiogroup rdbSibilancia;
	@View
	private Radiogroup rdbTiraje_subcostal_grave;
	@View
	private Radiogroup rdbSupuracion_oido;
	@View
	private Radiogroup rdbEdema_palpebral;
	@View
	private Radiogroup rdbSecrecion_purulenta_conjuntival;
	@View
	private Radiogroup rdbPustulas_vesiculas_piel;
	@View
	private Radiogroup rdbSecrecion_purulenta_ombligo;
	@View
	private Radiogroup rdbEritema_periumbilical;
	@View
	private Radiogroup rdbPlacas_blanquecinas_boca;
	@View
	private Radiogroup rdbEquimosis_petequias;
	@View
	private Radiogroup rdbHemorragias;
	@View
	private Radiogroup rdbDistension_abdominal;
	@View
	private Radiogroup rdbLlenado_capilar_3seg;
	@View
	private Radiogroup rdbFontanela_abombada;
	@View
	private Radiogroup rdbOjos_hundidos;
	@View
	private Radiogroup rdbCabeza_cara;
	@View
	private Radiogroup rdbSigno_pliegue_cutaneo;
	@View
	private Radiogroup rdbOrgarnos_sentidos;
	@View
	private Radiogroup rdbRojo_retiniano;
	@View
	private Radiogroup rdbCuello;
	@View
	private Radiogroup rdbTorax_cardiopulmonar;
	@View
	private Radiogroup rdbRitmo_cardiaco;
	@View
	private Radiogroup rdbSoplo;
	@View
	private Radiogroup rdbMasas;
	@View
	private Radiogroup rdbMegalias;
	@View
	private Radiogroup rdbGenito_urinario;
	@View
	private Radiogroup rdbColumna_vertebral;
	@View
	private Radiogroup rdbExremidades;
	@View
	private Radiogroup rdbPiel_anexos;
	@View
	private Radiogroup rdbReflejo_moro;
	@View
	private Radiogroup rdbReflejo_busqueda;
	@View
	private Radiogroup rdbReflejo_succion;
	@View
	private Radiogroup rdbReflejo_palmar;
	@View
	private Radiogroup rdbReflejo_plantar;
	@View
	private Radiogroup rdbReflejo_cocleo_palpear;
	@View
	private Textbox tbxObservaciones_examen_fisico_sistemas;
	@View
	private Radiogroup rdbTiene_boca_abierta;
	@View
	private Radiogroup rdbToca_seno_menton;
	@View
	private Radiogroup rdbLabio_inferior_evertido;
	@View
	private Radiogroup rdbAreola__visible_encima_labio;
	@View
	private Radiogroup rdbCabeza_cuerpo_nino_derecho;
	@View
	private Radiogroup rdbMadre_sostiene_cuerpo;
	@View
	private Radiogroup rdbHijo_frente_madre;
	@View
	private Radiogroup rdbDireccion_pecho;
	@View
	private Radiogroup rdbAbdomen;
	@View
	private Radiogroup rdbNeurologico;
	@View
	private Textbox tbxEvaluacion_desarrollo;
	@View
	private Textbox tbxAnalisis;
	@View
	private Checkbox chbEstimulo_factores_protectores_terapeutico;
	@View
	private Checkbox chbLactancia_materna_exclusiva_terapeutico;
	@View
	private Checkbox chbRecomendaciones_buen_trato_terapeutico;
	@View
	private Checkbox chbImpoprtancia_asistencia_controles_terapeutico;
	@View
	private Checkbox chbOrientaciones_vacunacion_terapeutico;
	@View
	private Checkbox chbOrientaciones_madre_terapeutico;
	@View
	private Checkbox chbCrecimiento_adecuado;
	@View
	private Checkbox chbRiesgo_de_desnutricion;
	@View
	private Checkbox chbDesnutricion_severa;
	@View
	private Checkbox chbDesnutricion;
	@View
	private Checkbox chbSospecha_retraso_desarrollo;
	@View
	private Checkbox chbRiesgo_problema_desarrollo;
	@View
	private Checkbox chbDesarrollo_normal_factor_riesgo;
	@View
	private Checkbox chbDesarrollo_normal;
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;

	@View
	private Textbox tbxObservaciones_plan_terapeutico;

	@View
	private Doublebox dbxP_e_de;
	@View
	private Doublebox dbxT_e_de;
	@View
	private Doublebox dbxP_t_de;
	@View
	private Doublebox dbxPC_e_de;

	@View
	private ZKWindow formHisc_deteccion_alt_mn_2mes;

	@View
	private Button btnCalcularPesoEdad;
	@View
	private Button btnCalcularPerimetroCefalicoEdad;
	@View
	private Button btnCalcularPesoTalla;
	@View
	private Button btnCalcularTallaEdad;
	@View
	private Div divModuloOrdenamiento;
	@View
	private Div divModuloFarmacologico;
	@View
	private Textbox tbxObservaciones_vacunales;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	private Paciente paciente;
	private Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes;
	private ESexo sexo;
	private Timestamp fecha;

	private RespuestaInt coordenadaPesoEdad;
	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaPesoTalla;
	private RespuestaInt coordenadaPcEdad;

	private List coordenadasPE;
	private List coordenadasTE;
	private List coordenadasPT;
	private List coordenadasPCE;

	private String nro_ingreso_admision;

	@View(isMacro = true)
	private EscalaAbreviadaDesarrollo macroEscalaDesarrollo;

	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;

	// variables para metodo enablebotton

	// private Borderlayout borderlayoutEditar;
	@View
	private Toolbarbutton btGuardar;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "macroEscalaDesarrollo","divModuloRemisiones_externas" };


	@View
	private Toolbarbutton btnImprimir;

	@View
	private Listbox lbxFormato;
	
	/* Observaciones */

	@View private Textbox tbxObservaciones_cabeza;
	@View private Textbox tbxObservaciones_sentidos;
	@View private Textbox tbxObservaciones_rojo;
	@View private Textbox tbxObservaciones_cuello;
	@View private Textbox tbxObservaciones_torax;
	@View private Textbox tbxObservaciones_cardiaco;
	@View private Textbox tbxObservaciones_soplo;
	@View private Textbox tbxObservaciones_abdomen;
	@View private Textbox tbxObservaciones_masas;
	@View private Textbox tbxObservaciones_megalias;
	@View private Textbox tbxObservaciones_genito;
	@View private Textbox tbxObservaciones_columna;
	@View private Textbox tbxObservaciones_extremidades;
	@View private Textbox tbxObservaciones_piel;
	@View private Textbox tbxObservaciones_neurologico;
	@View private Textbox tbxObservaciones_moro;
	@View private Textbox tbxObservaciones_busqueda;
	@View private Textbox tbxObservaciones_succion;
	@View private Textbox tbxObservaciones_palmar;
	@View private Textbox tbxObservaciones_plantar;
	@View private Textbox tbxObservaciones_cocleo;
	
	@View private Row row1;
	@View private Row row2;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	@View private Row row6;
	@View private Row row7;
	
	
	@Override
	public void init() {
		try {
			listarCombos();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * Sobreescritura del metodo params(Map<String, Object> map) para obtener
	 * los parametros iniciales con los que trabajara la historia clinica
	 */
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			paciente = admision.getPaciente();
			sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
					: ESexo.MASCULINO);
			macroEscalaDesarrollo.setAdmision(admision);
			macroEscalaDesarrollo.mostrarColorFila1Edad();
			macroEscalaDesarrollo.mostrarColorFila11Edad();
			macroEscalaDesarrollo.mostrarColorFila12Edad();
			macroEscalaDesarrollo.mostrarColorFila13Edad();
			fecha = paciente.getFecha_nacimiento();
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
		}
	}

	public void alarmaCrecimiento(Checkbox ch) {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		if (ch.isChecked()) {
			String mensaje = "Remitir";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, ch, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
			dbxExamen_fisico_fc
					.setStyle("text-transform:uppercase;background-color:white");
		}
	}

	public void alarmaExamenFisicoFc() {

		UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 161d, 119d,
				"Anormal", "Anormal", false);

	}

	public void alarmaExamenFisicoFr() {

		UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 41d, 29d,
				"Anormal", "Anormal", false);

	}

	public void alarmaExamenFisicoTemperatura() {

		UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_temp, 37.9, 36.5,
				"Anormal", "Anormal", false);

	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxParentesco, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxHemoclasificacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxVdlr, true,
				getServiceLocator());
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

	public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
			throws Exception {
		tbxAccion.setValue(opciones_formulario.toString());
		if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
			onOpcionFormularioRegistrar();
		} else if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)
				|| opciones_formulario.equals(OpcionesFormulario.MOSTRAR)) {
			groupboxConsultar.setVisible(false);
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

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsultar.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
		if (admision != null) {
			this.nro_ingreso_admision = admision.getNro_ingreso();
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloRemisiones();
		}

		btnImprimir.setVisible(false);
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsultar.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		infoPacientes.validarInformacionPaciente();
		FormularioUtil.validarCamposObligatorios(lbxParentesco,
				tbxNombre_del_acompanante, tbxMotivos_de_la_consulta,
				 tbxComplicaciones_embarazo_parto,
				tbxComplicaciones_embarazo_parto, tbxObservaciones_perinatales,
				tbxPatologico_medico, tbxPatologico_quirurgicos,
				tbxPatologico_hospitalizaciones, tbxPatologico_medicacion,
				tbxObservaciones_alimentario, tbxEvaluacion_desarrollo,
				tbxAnalisis, tbxObservaciones_plan_terapeutico,
				dbxExamen_fisico_talla, dbxExamen_fisico_peso,
				dbxExamen_fisico_perimetro_cflico, dbxExamen_fisico_fc,
				dbxExamen_fisico_fr, dbxExamen_fisico_temp,tbxPatologicos_alergicos);

		boolean valida = receta_ripsAction.validarFormExterno();
		// String mensaje = "";
		if (valida)
			valida = orden_servicioAction.validarFormExterno();

		String mensaje = "Los campos marcados con (*) son obligatorios";

//		if (valida) {
//			if (!tallaValida(dbxExamen_fisico_talla.getValue())) {
//				valida = false;
//				mensaje = "La talla no se encuentra en el rango permitido!";
//				dbxExamen_fisico_talla.setFocus(true);
//			}
//		}
//
//		if (valida) {
//			if (!pesoValido(dbxExamen_fisico_peso.getValue())) {
//				valida = false;
//				mensaje = "El peso no se encuentra en el rango permitido!";
//				dbxExamen_fisico_peso.setFocus(true);
//			}
//		}
//
//		if (valida) {
//			if (!perimetroCefalicoValido(dbxExamen_fisico_perimetro_cflico
//					.getValue())) {
//				valida = false;
//				mensaje = "El perimetro cefálico no se encuentra en el rango permitido!";
//				dbxExamen_fisico_perimetro_cflico.setFocus(true);
//			}
//		}

		if (valida)
			valida = remisiones_externasAction.validarRemision();

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

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

			// if (lbxTipo_historia.getSelectedIndex() != 0) {
			// parameters.put("tipo_historia",
			// lbxTipo_historia.getSelectedItem().getValue());
			// }

			parameters.put("limite_paginado","limit 25 offset 0");

			List<Hisc_deteccion_alt_mn_2mes> lista_datos = getServiceLocator()
					.getHisc_deteccion_alt_mn_2mesService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						hisc_deteccion_alt_mn_2mes, this));
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

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes = (Hisc_deteccion_alt_mn_2mes) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_deteccion_alt_mn_2mes
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_deteccion_alt_mn_2mes
				.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				hisc_deteccion_alt_mn_2mes.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

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
						mostrarDatos(hisc_deteccion_alt_mn_2mes);
						btGuardar.setVisible(false);
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
													eliminarDatos(hisc_deteccion_alt_mn_2mes);
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
	
	public Hisc_deteccion_alt_mn_2mes getBean(){
		hisc_deteccion_alt_mn_2mes = new Hisc_deteccion_alt_mn_2mes();
		hisc_deteccion_alt_mn_2mes.setCodigo_empresa(empresa
				.getCodigo_empresa());
		hisc_deteccion_alt_mn_2mes.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		hisc_deteccion_alt_mn_2mes.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_deteccion_alt_mn_2mes
				.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
		hisc_deteccion_alt_mn_2mes.setFecha_inicial(new Timestamp(
				infoPacientes.getFecha_inicial().getTime()));
		hisc_deteccion_alt_mn_2mes.setUltimo_update(new Timestamp(
				(new Date()).getTime()));
		hisc_deteccion_alt_mn_2mes.setNro_ingreso(admision
				.getNro_ingreso());

		hisc_deteccion_alt_mn_2mes.setParentesco(lbxParentesco
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setNombre_del_acompanante(tbxNombre_del_acompanante
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setNombre_del_padre(tbxNombre_del_padre.getValue());
		hisc_deteccion_alt_mn_2mes.setEdad__del_padre((ibxEdad_padre
				.getValue() != null ? ibxEdad_padre.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setNombre_de_la_madre(tbxNombre_de_la_madre.getValue());
		hisc_deteccion_alt_mn_2mes
				.setEdad_de_la_madre_actual((ibxEdad_madre.getValue() != null ? ibxEdad_madre
						.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setMotivos_de_la_consulta(tbxMotivos_de_la_consulta
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setEnfermedad_actual(tbxEnfermedad_actual.getValue());
		hisc_deteccion_alt_mn_2mes
				.setPuede_beber_del_pecho(rdbPuede_beber_del_pecho
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setHa_tenido_vomito(rdbHa_tenido_vomito
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setVomita_todo(rdbVomita_todo
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setDificultad_para_respirar(rdbDificultad_para_respirar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setHa_tenido_fiebre(rdbHa_tenido_fiebre
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setHa_tenido_hipotermia(rdbHa_tenido_hipotermia
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setHa_tenido_convulciones(rdbHa_tenido_convulciones
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setTiene_diarrea(rdbTiene_diarrea
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setSangre_en_la_heces(rdbSangre_en_la_heces
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setOtros_observaciones(tbxOtros_observaciones
						.getValue());
		hisc_deteccion_alt_mn_2mes.setPerinatales_g((ibxPerinatales_g
				.getValue() != null ? ibxPerinatales_g.getValue() : 0));
		hisc_deteccion_alt_mn_2mes.setPerinatales_p((ibxPerinatales_p
				.getValue() != null ? ibxPerinatales_p.getValue() : 0));
		hisc_deteccion_alt_mn_2mes.setPerinatales_a((ibxPerinatales_a
				.getValue() != null ? ibxPerinatales_a.getValue() : 0));
		hisc_deteccion_alt_mn_2mes.setPerinatales_c((ibxPerinatales_c
				.getValue() != null ? ibxPerinatales_c.getValue() : 0));
		hisc_deteccion_alt_mn_2mes.setPerinatales_v((ibxPerinatales_v
				.getValue() != null ? ibxPerinatales_v.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setPerinatales_unico(rdbPerinatales_unico
						.getSelectedItem().getValue().toString());

		// hisc_deteccion_alt_mn_2mes.setPerinatales_riesgo_bajo(rdbPerinatales_riesgo_bajo.getSelectedItem().getValue().toString());

		hisc_deteccion_alt_mn_2mes
				.setPerinatales_riesgo_alto(rdbPerinatales_riesgo_alto
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setParto_cst(rdbParto_cst
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setControl_prenatal(rdbControl_prenatal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setHemoclasificacion(lbxHemoclasificacion
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setComplicaciones_embarazo_parto(tbxComplicaciones_embarazo_parto
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setTalla_al_nacer((dbxTalla_al_nacer.getValue() != null ? dbxTalla_al_nacer
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setEdad_de_la_madre_al_nacer((ibxEdad_de_la_madre_al_nacer
						.getValue() != null ? ibxEdad_de_la_madre_al_nacer
						.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setObservaciones(tbxObservaciones_perinatales
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setHospitalizacion_neonatal(rdbHospitalizacion_neonatal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setApagar_al_mnto((ibxApgar_al_mnto
				.getValue() != null ? ibxApgar_al_mnto.getValue()
				.toString() : "0"));
		hisc_deteccion_alt_mn_2mes.setReanimacion(rdbReanimacion
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setPeso_nacer((dbxPeso_nacer
				.getValue() != null ? dbxPeso_nacer.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setApagar_5_minutos(ibxApgar_5_minutos.getValue() != null ? ibxApgar_5_minutos
						.getValue().toString() : "0");
		hisc_deteccion_alt_mn_2mes.setVdrl_materno(lbxVdlr.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setInstitucioneal(rdbInstitucioneal
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setTsh(tbxTsh.getValue());
		hisc_deteccion_alt_mn_2mes.setSem_gestacion((ibxSem_gestacion
				.getValue() != null ? ibxSem_gestacion.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setPatologico_medico(tbxPatologico_medico.getValue());
		hisc_deteccion_alt_mn_2mes
				.setPatologico_quirurgicos(tbxPatologico_quirurgicos
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setPatologico_hospitalizaciones(tbxPatologico_hospitalizaciones
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setPatologico_medicacion(tbxPatologico_medicacion
						.getValue());
		hisc_deteccion_alt_mn_2mes
		.setAntecedentes_alergicos(tbxPatologicos_alergicos
				.getValue());		
		hisc_deteccion_alt_mn_2mes
				.setDificultad_para_alimentarse(rdbDificultad_para_alimentarse
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setHa_dejado_de_comer(rdbHa_dejado_de_comer
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setLactancia_materna(rdbLactancia_materna
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setLactancia_materna_exclusiva(rdbLactancia_materna_exclusiva
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setRecibe_otras_leches_alimentos(rdbRecibe_otras_leches_alimentos
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setUtiliza_chupo(rdbUtiliza_chupo
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setObservaciones_alimentario(tbxObservaciones_alimentario
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setAntitetanica_materna(rdbAntitetanica_materna
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setBcg(rdbBcg.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_mn_2mes.setHepb1(rdbHepb1.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_mn_2mes.setHns_vivos((ibxHns_vivos
				.getValue() != null ? ibxHns_vivos.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setHns_muertos_mn_5((ibxHns_muertos_mn_5.getValue() != null ? ibxHns_muertos_mn_5
						.getValue() : 0));
		hisc_deteccion_alt_mn_2mes
				.setHns_desnutridos_mn_5((ibxHns_desnutridos_mn_5
						.getValue() != null ? ibxHns_desnutridos_mn_5
						.getValue() : 0));
		hisc_deteccion_alt_mn_2mes.setCausas(tbxCausas.getValue());
		hisc_deteccion_alt_mn_2mes
				.setSon_parientes_los_padres(rdbSon_parientes_los_padres
						.getSelectedItem().getValue().toString());

		hisc_deteccion_alt_mn_2mes
				.setSintomaticos_respiratorio(rdbSintomaticos_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setSintomaticos_piel(rdbSintomaticos_piel
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setFamiliar_problema_mental_fisico(rdbFamiliar_problema_mental_fisico
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setPaternos_medico(tbxPaternos_medico.getValue());
		hisc_deteccion_alt_mn_2mes
				.setMaternos_medico(tbxMaternos_medico.getValue());
		hisc_deteccion_alt_mn_2mes
				.setPaternos_alergico(tbxPaternos_alergico.getValue());
		hisc_deteccion_alt_mn_2mes
				.setPaternos_talla((dbxPaternos_talla.getValue() != null ? dbxPaternos_talla
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setMaternos_alergico(tbxMaternos_alergico.getValue());
		hisc_deteccion_alt_mn_2mes
				.setMaternos_talla((dbxMaternos_talla.getValue() != null ? dbxMaternos_talla
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setOtros_antc_familiar(tbxOtros_antc_familiar
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setExamen_fisico_peso((dbxExamen_fisico_peso
						.getValue() != null ? dbxExamen_fisico_peso
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setExamen_fisico_talla((dbxExamen_fisico_talla
						.getValue() != null ? dbxExamen_fisico_talla
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setExamen_fisico_perimetro_cflico((dbxExamen_fisico_perimetro_cflico
						.getValue() != null ? dbxExamen_fisico_perimetro_cflico
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setExamen_fisico_fc((dbxExamen_fisico_fc.getValue() != null ? dbxExamen_fisico_fc
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setExamen_fisico_fr((dbxExamen_fisico_fr.getValue() != null ? dbxExamen_fisico_fr
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes
				.setExamen_fisico_temp((dbxExamen_fisico_temp
						.getValue() != null ? dbxExamen_fisico_temp
						.getValue() : 0d));
		hisc_deteccion_alt_mn_2mes.setTendencia_peso(rdbTendencia_peso
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setLatergico(rdbLatergico
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setIrritable(rdbIrritable
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setPalidez(rdbPalidez
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setCianosis(rdbCianosis
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setFr_ma60_mn30(rdbFr_ma60_mn30
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setFc_ma180_mn100(rdbFc_ma180_mn100
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setLctericia(rdbLctericia
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setApnea(rdbApnea.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_mn_2mes.setAleteo_nasal(rdbAleteo_nasal
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setQuejido(rdbQuejido
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setEstribor(rdbEstribor
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setSibilancia(rdbSibilancia
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setTiraje_subcostal_grave(rdbTiraje_subcostal_grave
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setSupuracion_oido(rdbSupuracion_oido
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setEdema_palpebral(rdbEdema_palpebral
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setSecrecion_purulenta_conjuntival(rdbSecrecion_purulenta_conjuntival
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setPustulas_vesiculas_piel(rdbPustulas_vesiculas_piel
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setSecrecion_purulenta_ombligo(rdbSecrecion_purulenta_ombligo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setEritema_periumbilical(rdbEritema_periumbilical
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setPlacas_blanquecinas_boca(rdbPlacas_blanquecinas_boca
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setEquimosis_petequias(rdbEquimosis_petequias
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setHemorragias(rdbHemorragias
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setDistension_abdominal(rdbDistension_abdominal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setLlenado_capilar_3seg(rdbLlenado_capilar_3seg
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setFontanela_abombada(rdbFontanela_abombada
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setOjos_hundidos(rdbOjos_hundidos
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setCabeza_cara(rdbCabeza_cara
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setSigno_pliegue_cutaneo(rdbSigno_pliegue_cutaneo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setOrgarnos_sentidos(rdbOrgarnos_sentidos
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setRojo_retiniano(rdbRojo_retiniano
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setCuello(rdbCuello
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setTorax_cardiopulmonar(rdbTorax_cardiopulmonar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setRitmo_cardiaco(rdbRitmo_cardiaco
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setSoplo(rdbSoplo.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_mn_2mes.setMasas(rdbMasas.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_mn_2mes.setMegalias(rdbMegalias
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setGenito_urinario(rdbGenito_urinario
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setColumna_vertebral(rdbColumna_vertebral
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setExremidades(rdbExremidades
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setPiel_anexos(rdbPiel_anexos
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setReflejo_moro(rdbReflejo_moro
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setReflejo_busqueda(rdbReflejo_busqueda
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setReflejo_succion(rdbReflejo_succion
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setReflejo_palmar(rdbReflejo_palmar
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setReflejo_plantar(rdbReflejo_plantar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setReflejo_cocleo_palpear(rdbReflejo_cocleo_palpear
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setObservaciones_examen_fisico_sistemas(tbxObservaciones_examen_fisico_sistemas
						.getValue());
		hisc_deteccion_alt_mn_2mes
				.setTiene_boca_abierta(rdbTiene_boca_abierta
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setToca_seno_menton(rdbToca_seno_menton
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setLabio_inferior_evertido(rdbLabio_inferior_evertido
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setAreola__visible_encima_labio(rdbAreola__visible_encima_labio
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setCabeza_cuerpo_nino_derecho(rdbCabeza_cuerpo_nino_derecho
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setMadre_sostiene_cuerpo(rdbMadre_sostiene_cuerpo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setHijo_frente_madre(rdbHijo_frente_madre
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setDireccion_pecho(rdbDireccion_pecho
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setEvaluacion_desarrollo(tbxEvaluacion_desarrollo
						.getValue());

		hisc_deteccion_alt_mn_2mes.setAnalisis(tbxAnalisis.getValue());
		hisc_deteccion_alt_mn_2mes
				.setEstimulo_factores_protectores_terapeutico(chbEstimulo_factores_protectores_terapeutico
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setLactancia_materna_exclusiva_terapeutico(chbLactancia_materna_exclusiva_terapeutico
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setRecomendaciones_buen_trato_terapeutico(chbRecomendaciones_buen_trato_terapeutico
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setImpoprtancia_asistencia_controles_terapeutico(chbImpoprtancia_asistencia_controles_terapeutico
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setOrientaciones_vacunacion_terapeutico(chbOrientaciones_vacunacion_terapeutico
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setOrientaciones_madre_terapeutico(chbOrientaciones_madre_terapeutico
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setCrecimiento_adecuado(chbCrecimiento_adecuado
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setRiesgo_de_desnutricion(chbRiesgo_de_desnutricion
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setDesnutricion_severa(chbDesnutricion_severa
						.isChecked());
		hisc_deteccion_alt_mn_2mes.setDesnutricion(chbDesnutricion
				.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setSospecha_retraso_desarrollo(chbSospecha_retraso_desarrollo
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setRiesgo_problema_desarrollo(chbRiesgo_problema_desarrollo
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setDesarrollo_normal_factor_riesgo(chbDesarrollo_normal_factor_riesgo
						.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setDesarrollo_normal(chbDesarrollo_normal.isChecked());
		hisc_deteccion_alt_mn_2mes
				.setObservaciones_plan_terapeutico(tbxObservaciones_plan_terapeutico
						.getValue());
		hisc_deteccion_alt_mn_2mes.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_mn_2mes.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_mn_2mes.setCreacion_user(usuarios
				.getCodigo().toString());
		hisc_deteccion_alt_mn_2mes.setDelete_date(null);
		hisc_deteccion_alt_mn_2mes.setUltimo_user(usuarios.getCodigo()
				.toString());
		hisc_deteccion_alt_mn_2mes.setDelete_user(null);
		hisc_deteccion_alt_mn_2mes.setAbdomen(rdbAbdomen
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes.setNeurologico(rdbNeurologico
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_mn_2mes
				.setObservaciones_vacunales(tbxObservaciones_vacunales
						.getValue());
		

		hisc_deteccion_alt_mn_2mes.setObservaciones_cabeza(tbxObservaciones_cabeza.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_sentidos(tbxObservaciones_sentidos.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_rojo(tbxObservaciones_rojo.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_cuello(tbxObservaciones_cuello.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_torax(tbxObservaciones_torax.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_cardiaco(tbxObservaciones_cardiaco.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_soplo(tbxObservaciones_soplo.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_abdomen(tbxObservaciones_abdomen.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_masas(tbxObservaciones_masas.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_megalias(tbxObservaciones_megalias.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_genito(tbxObservaciones_genito.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_columna(tbxObservaciones_columna.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_extremidades(tbxObservaciones_extremidades.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_piel(tbxObservaciones_piel.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_neurologico(tbxObservaciones_neurologico.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_moro(tbxObservaciones_moro.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_busqueda(tbxObservaciones_busqueda.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_succion(tbxObservaciones_succion.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_palmar(tbxObservaciones_palmar.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_plantar(tbxObservaciones_plantar.getValue());
		hisc_deteccion_alt_mn_2mes.setObservaciones_cocleo(tbxObservaciones_cocleo.getValue());
		
		
		return hisc_deteccion_alt_mn_2mes;

	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //
				hisc_deteccion_alt_mn_2mes = getBean();
				
				calcularCoordenadas(false);
				
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", hisc_deteccion_alt_mn_2mes);				
				datos.put("admision", admision);
				datos.put("cita_seleccionada", cita);
				datos.put("escala_del_desarrollo",macroEscalaDesarrollo.obtenerEscala());
				datos.put("accion", tbxAccion.getValue());
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());

				
				if(pesoValido(hisc_deteccion_alt_mn_2mes.getExamen_fisico_peso()) &&
						tallaValida(hisc_deteccion_alt_mn_2mes.getExamen_fisico_talla()) &&
								perimetroCefalicoValido(hisc_deteccion_alt_mn_2mes.getExamen_fisico_perimetro_cflico())){
					Coordenadas_graficas cg1 = new Coordenadas_graficas();
					// Coordenada (P/E)
					cg1.setCodigo_empresa(codigo_empresa);
					cg1.setCodigo_historia(hisc_deteccion_alt_mn_2mes
							.getCodigo_historia());
					cg1.setCodigo_sucursal(codigo_sucursal);
					cg1.setFecha_historia(hisc_deteccion_alt_mn_2mes
							.getFecha_inicial());
					cg1.setTipo_coordenada(ITipos_coordenada.P_E);
					cg1.setIdentificacion(paciente.getNro_identificacion());
					cg1.setValor("" + coordenadaPesoEdad.getValor());
					cg1.setX("" + coordenadaPesoEdad.getX());
					cg1.setY("" + coordenadaPesoEdad.getY());
					cg1.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES);
	
					// Coordenada (T/E)
					Coordenadas_graficas cg2 = new Coordenadas_graficas();
					cg2.setCodigo_empresa(codigo_empresa);
					cg2.setCodigo_historia(hisc_deteccion_alt_mn_2mes
							.getCodigo_historia());
					cg2.setCodigo_sucursal(codigo_sucursal);
					cg2.setFecha_historia(hisc_deteccion_alt_mn_2mes
							.getFecha_inicial());
					cg2.setTipo_coordenada(ITipos_coordenada.T_E);
					cg2.setIdentificacion(paciente.getNro_identificacion());
					cg2.setValor("" + coordenadaTallaEdad.getValor());
					cg2.setX("" + coordenadaTallaEdad.getX());
					cg2.setY("" + coordenadaTallaEdad.getY());
					cg2.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES);
	
					// Coordenada (P/T)
					Coordenadas_graficas cg3 = new Coordenadas_graficas();
					cg3.setCodigo_empresa(codigo_empresa);
					cg3.setCodigo_historia(hisc_deteccion_alt_mn_2mes
							.getCodigo_historia());
					cg3.setCodigo_sucursal(codigo_sucursal);
					cg3.setFecha_historia(hisc_deteccion_alt_mn_2mes
							.getFecha_inicial());
					cg3.setTipo_coordenada(ITipos_coordenada.P_T);
					cg3.setIdentificacion(paciente.getNro_identificacion());
					cg3.setValor("" + coordenadaPesoTalla.getValor());
					cg3.setX("" + coordenadaPesoTalla.getX());
					cg3.setY("" + coordenadaPesoTalla.getY());
					cg3.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES);
	
					// Coordenada (PC/E)
					Coordenadas_graficas cg4 = new Coordenadas_graficas();
					cg4.setCodigo_empresa(codigo_empresa);
					cg4.setCodigo_historia(hisc_deteccion_alt_mn_2mes
							.getCodigo_historia());
					cg4.setCodigo_sucursal(codigo_sucursal);
					cg4.setFecha_historia(hisc_deteccion_alt_mn_2mes
							.getFecha_inicial());
					cg4.setTipo_coordenada(ITipos_coordenada.PC_E);
					cg4.setIdentificacion(paciente.getNro_identificacion());
					cg4.setValor("" + coordenadaPcEdad.getValor());
					cg4.setX("" + coordenadaPcEdad.getX());
					cg4.setY("" + coordenadaPcEdad.getY());
					cg4.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES);
	
					ArrayList<Coordenadas_graficas> coordenadas = new ArrayList<Coordenadas_graficas>();
					coordenadas.add(cg1);
					coordenadas.add(cg2);
					coordenadas.add(cg3);
					coordenadas.add(cg4);
					datos.put("coordenadas", coordenadas);
				}

				// hay que actualualizar los diagnosticos en la receta antes de
				// obtener el objeto receta
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica.obtenerImpresionDiagnostica();

				receta_ripsAction.actualizarDiagnosticos(impresion_diagnostica);

				Map<String, Object> mapReceta = receta_ripsAction.obtenerDatos();
				Map<String, Object> mapProcedimientos = orden_servicioAction.obtenerDatos();
				datos.put("receta_medica", mapReceta);
				datos.put("orden_servicio", mapProcedimientos);

				Remision_interna remision_interna = macroRemision_interna.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);	

				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);

				getServiceLocator().getHisc_deteccion_alt_mn_2mesService().guardarDatos(datos);

				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(false);
				}
				mostrarDatosAutorizacion(datos);
				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hisc_deteccion_alt_mn_2mes.getCodigo_historia());
				Receta_rips receta_rips = (Receta_rips) mapReceta.get("receta_rips");
				if (receta_rips != null)
				receta_ripsAction.mostrarDatos(receta_rips, false);
				//hay que llamar este metodo para validar que salga el boton para imprimir despues de guardar la receta
				receta_ripsAction.validarParaImpresion();
				
				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos.get("orden_servicio");
				if (orden_servicio != null)
				orden_servicioAction.mostrarDatos(orden_servicio);
				//hay que llamar este metodo para validar que salga el boton para imprimir despues de guardar la orden
				orden_servicioAction.validarParaImpresion();

				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision,getInformacionClinica(), this);
				

				MensajesUtil.mensajeInformacion("Informacion ...",
						"Los datos se guardaron satisfactoriamente");
				

				btnImprimir.setVisible(true);
				
			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		hisc_deteccion_alt_mn_2mes = (Hisc_deteccion_alt_mn_2mes) obj;
		try {
			this.nro_ingreso_admision = hisc_deteccion_alt_mn_2mes
					.getNro_ingreso();

			infoPacientes.setCodigo_historia(hisc_deteccion_alt_mn_2mes
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_deteccion_alt_mn_2mes
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_deteccion_alt_mn_2mes.getUltimo_update());
			initMostrar_datos(hisc_deteccion_alt_mn_2mes);

			onMostrarModuloRemisiones();

			cargarInformacion_paciente();
			cargarRemisionInterna(hisc_deteccion_alt_mn_2mes);

			Utilidades.seleccionarListitem(lbxParentesco,
					hisc_deteccion_alt_mn_2mes.getParentesco());

			tbxNombre_del_acompanante.setValue(hisc_deteccion_alt_mn_2mes
					.getNombre_del_acompanante());
			tbxNombre_del_padre.setValue(hisc_deteccion_alt_mn_2mes
					.getNombre_del_padre());
			ibxEdad_padre.setValue(hisc_deteccion_alt_mn_2mes
					.getEdad__del_padre());
			tbxNombre_de_la_madre.setValue(hisc_deteccion_alt_mn_2mes
					.getNombre_de_la_madre());
			ibxEdad_madre.setValue(hisc_deteccion_alt_mn_2mes
					.getEdad_de_la_madre_actual());
			tbxMotivos_de_la_consulta.setValue(hisc_deteccion_alt_mn_2mes
					.getMotivos_de_la_consulta());
			tbxEnfermedad_actual.setValue(hisc_deteccion_alt_mn_2mes
					.getEnfermedad_actual());
			Utilidades.seleccionarRadio(rdbPuede_beber_del_pecho,
					hisc_deteccion_alt_mn_2mes.getPuede_beber_del_pecho());
			Utilidades.seleccionarRadio(rdbHa_tenido_vomito,
					hisc_deteccion_alt_mn_2mes.getHa_tenido_vomito());
			Utilidades.seleccionarRadio(rdbVomita_todo,
					hisc_deteccion_alt_mn_2mes.getVomita_todo());
			Utilidades.seleccionarRadio(rdbDificultad_para_respirar,
					hisc_deteccion_alt_mn_2mes.getDificultad_para_respirar());
			Utilidades.seleccionarRadio(rdbHa_tenido_fiebre,
					hisc_deteccion_alt_mn_2mes.getHa_tenido_fiebre());
			Utilidades.seleccionarRadio(rdbHa_tenido_hipotermia,
					hisc_deteccion_alt_mn_2mes.getHa_tenido_hipotermia());
			Utilidades.seleccionarRadio(rdbHa_tenido_convulciones,
					hisc_deteccion_alt_mn_2mes.getHa_tenido_convulciones());
			Utilidades.seleccionarRadio(rdbTiene_diarrea,
					hisc_deteccion_alt_mn_2mes.getTiene_diarrea());
			Utilidades.seleccionarRadio(rdbSangre_en_la_heces,
					hisc_deteccion_alt_mn_2mes.getSangre_en_la_heces());
			tbxOtros_observaciones.setValue(hisc_deteccion_alt_mn_2mes
					.getOtros_observaciones());
			ibxPerinatales_g.setValue(Integer
					.valueOf(hisc_deteccion_alt_mn_2mes.getPerinatales_g()));
			ibxPerinatales_p.setValue(Integer
					.valueOf(hisc_deteccion_alt_mn_2mes.getPerinatales_p()));
			ibxPerinatales_a.setValue(Integer
					.valueOf(hisc_deteccion_alt_mn_2mes.getPerinatales_a()));
			ibxPerinatales_c.setValue(Integer
					.valueOf(hisc_deteccion_alt_mn_2mes.getPerinatales_c()));
			ibxPerinatales_v.setValue(Integer
					.valueOf(hisc_deteccion_alt_mn_2mes.getPerinatales_v()));
			Utilidades.seleccionarRadio(rdbPerinatales_unico,
					hisc_deteccion_alt_mn_2mes.getPerinatales_unico());
			Utilidades.seleccionarRadio(rdbPerinatales_riesgo_alto,
					hisc_deteccion_alt_mn_2mes.getPerinatales_riesgo_alto());
			Utilidades.seleccionarRadio(rdbParto_cst,
					hisc_deteccion_alt_mn_2mes.getParto_cst());
			Radio Control_prenatal = (Radio) rdbControl_prenatal
					.getFellow("control_prenatal"
							+ hisc_deteccion_alt_mn_2mes.getControl_prenatal());
			Control_prenatal.setChecked(true);
			Utilidades.seleccionarListitem(lbxHemoclasificacion,
					hisc_deteccion_alt_mn_2mes.getHemoclasificacion());
			tbxComplicaciones_embarazo_parto
					.setValue(hisc_deteccion_alt_mn_2mes
							.getComplicaciones_embarazo_parto());
			dbxTalla_al_nacer.setValue(hisc_deteccion_alt_mn_2mes
					.getTalla_al_nacer());
			ibxEdad_de_la_madre_al_nacer.setValue(hisc_deteccion_alt_mn_2mes
					.getEdad_de_la_madre_al_nacer());
			tbxObservaciones_perinatales.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones());
			Utilidades.seleccionarRadio(rdbHospitalizacion_neonatal,
					hisc_deteccion_alt_mn_2mes.getHospitalizacion_neonatal());
			ibxApgar_al_mnto.setValue(Integer
					.valueOf(hisc_deteccion_alt_mn_2mes.getApagar_al_mnto()));
			Utilidades.seleccionarRadio(rdbReanimacion,
					hisc_deteccion_alt_mn_2mes.getReanimacion());
			dbxPeso_nacer.setValue(hisc_deteccion_alt_mn_2mes.getPeso_nacer());
			ibxApgar_5_minutos.setValue(Integer
					.valueOf(hisc_deteccion_alt_mn_2mes.getApagar_5_minutos()));
			Utilidades.seleccionarListitem(lbxVdlr,hisc_deteccion_alt_mn_2mes
					.getVdrl_materno());
			Utilidades.seleccionarRadio(rdbInstitucioneal,
					hisc_deteccion_alt_mn_2mes.getInstitucioneal());
			tbxTsh.setValue(hisc_deteccion_alt_mn_2mes.getTsh());
			ibxSem_gestacion.setValue(hisc_deteccion_alt_mn_2mes
					.getSem_gestacion());
			tbxPatologico_medico.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_medico());
			tbxPatologico_quirurgicos.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_quirurgicos());
			tbxPatologico_hospitalizaciones.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_hospitalizaciones());
			tbxPatologico_medicacion.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_medicacion());
			tbxPatologicos_alergicos.setValue(hisc_deteccion_alt_mn_2mes
					.getAntecedentes_alergicos());
			Utilidades
					.seleccionarRadio(rdbDificultad_para_alimentarse,
							hisc_deteccion_alt_mn_2mes
									.getDificultad_para_alimentarse());
			Utilidades.seleccionarRadio(rdbHa_dejado_de_comer,
					hisc_deteccion_alt_mn_2mes.getHa_dejado_de_comer());
			Utilidades.seleccionarRadio(rdbLactancia_materna,
					hisc_deteccion_alt_mn_2mes.getLactancia_materna());
			Utilidades
					.seleccionarRadio(rdbLactancia_materna_exclusiva,
							hisc_deteccion_alt_mn_2mes
									.getLactancia_materna_exclusiva());
			Utilidades.seleccionarRadio(rdbRecibe_otras_leches_alimentos,
					hisc_deteccion_alt_mn_2mes
							.getRecibe_otras_leches_alimentos());
			Utilidades.seleccionarRadio(rdbUtiliza_chupo,
					hisc_deteccion_alt_mn_2mes.getUtiliza_chupo());
			tbxObservaciones_alimentario.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_alimentario());
			Utilidades.seleccionarRadio(rdbAntitetanica_materna,
					hisc_deteccion_alt_mn_2mes.getAntitetanica_materna());
			Utilidades.seleccionarRadio(rdbBcg,
					hisc_deteccion_alt_mn_2mes.getBcg());
			Utilidades.seleccionarRadio(rdbHepb1,
					hisc_deteccion_alt_mn_2mes.getHepb1());
			ibxHns_vivos.setValue(hisc_deteccion_alt_mn_2mes.getHns_vivos());
			ibxHns_muertos_mn_5.setValue(hisc_deteccion_alt_mn_2mes
					.getHns_muertos_mn_5());
			ibxHns_desnutridos_mn_5.setValue(hisc_deteccion_alt_mn_2mes
					.getHns_desnutridos_mn_5());
			tbxCausas.setValue(hisc_deteccion_alt_mn_2mes.getCausas());
			Utilidades.seleccionarRadio(rdbSon_parientes_los_padres,
					hisc_deteccion_alt_mn_2mes.getSon_parientes_los_padres());
			Utilidades.seleccionarRadio(rdbFamiliar_problema_mental_fisico,
					hisc_deteccion_alt_mn_2mes
							.getFamiliar_problema_mental_fisico());
			tbxPaternos_medico.setValue(hisc_deteccion_alt_mn_2mes
					.getPaternos_medico());
			tbxMaternos_medico.setValue(hisc_deteccion_alt_mn_2mes
					.getMaternos_medico());
			tbxPaternos_alergico.setValue(hisc_deteccion_alt_mn_2mes
					.getPaternos_alergico());
			dbxPaternos_talla.setValue(hisc_deteccion_alt_mn_2mes
					.getPaternos_talla());
			tbxMaternos_alergico.setValue(hisc_deteccion_alt_mn_2mes
					.getMaternos_alergico());
			dbxMaternos_talla.setValue(hisc_deteccion_alt_mn_2mes
					.getMaternos_talla());
			tbxOtros_antc_familiar.setValue(hisc_deteccion_alt_mn_2mes
					.getOtros_antc_familiar());
			dbxExamen_fisico_peso.setValue(hisc_deteccion_alt_mn_2mes
					.getExamen_fisico_peso());
			dbxExamen_fisico_talla.setValue(hisc_deteccion_alt_mn_2mes
					.getExamen_fisico_talla());
			dbxExamen_fisico_perimetro_cflico
					.setValue(hisc_deteccion_alt_mn_2mes
							.getExamen_fisico_perimetro_cflico());
			dbxExamen_fisico_fc.setValue(hisc_deteccion_alt_mn_2mes
					.getExamen_fisico_fc());
			dbxExamen_fisico_fr.setValue(hisc_deteccion_alt_mn_2mes
					.getExamen_fisico_fr());
			dbxExamen_fisico_temp.setValue(hisc_deteccion_alt_mn_2mes
					.getExamen_fisico_temp());
			Utilidades.seleccionarRadio(rdbTendencia_peso,
					hisc_deteccion_alt_mn_2mes.getTendencia_peso());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					hisc_deteccion_alt_mn_2mes.getSintomaticos_respiratorio());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					hisc_deteccion_alt_mn_2mes.getSintomaticos_piel());
			Utilidades.seleccionarRadio(rdbLatergico,
					hisc_deteccion_alt_mn_2mes.getLatergico());
			Utilidades.seleccionarRadio(rdbIrritable,
					hisc_deteccion_alt_mn_2mes.getIrritable());
			Utilidades.seleccionarRadio(rdbPalidez,
					hisc_deteccion_alt_mn_2mes.getPalidez());
			Utilidades.seleccionarRadio(rdbCianosis,
					hisc_deteccion_alt_mn_2mes.getCianosis());
			Utilidades.seleccionarRadio(rdbFr_ma60_mn30,
					hisc_deteccion_alt_mn_2mes.getFr_ma60_mn30());
			Utilidades.seleccionarRadio(rdbFc_ma180_mn100,
					hisc_deteccion_alt_mn_2mes.getFc_ma180_mn100());
			Utilidades.seleccionarRadio(rdbLctericia,
					hisc_deteccion_alt_mn_2mes.getLctericia());
			Utilidades.seleccionarRadio(rdbApnea,
					hisc_deteccion_alt_mn_2mes.getApnea());
			Utilidades.seleccionarRadio(rdbAleteo_nasal,
					hisc_deteccion_alt_mn_2mes.getAleteo_nasal());
			Utilidades.seleccionarRadio(rdbQuejido,
					hisc_deteccion_alt_mn_2mes.getQuejido());
			Utilidades.seleccionarRadio(rdbEstribor,
					hisc_deteccion_alt_mn_2mes.getEstribor());
			Utilidades.seleccionarRadio(rdbSibilancia,
					hisc_deteccion_alt_mn_2mes.getSibilancia());
			Utilidades.seleccionarRadio(rdbTiraje_subcostal_grave,
					hisc_deteccion_alt_mn_2mes.getTiraje_subcostal_grave());
			Utilidades.seleccionarRadio(rdbSupuracion_oido,
					hisc_deteccion_alt_mn_2mes.getSupuracion_oido());
			Utilidades.seleccionarRadio(rdbEdema_palpebral,
					hisc_deteccion_alt_mn_2mes.getEdema_palpebral());
			Utilidades.seleccionarRadio(rdbSecrecion_purulenta_conjuntival,
					hisc_deteccion_alt_mn_2mes
							.getSecrecion_purulenta_conjuntival());
			Utilidades.seleccionarRadio(rdbPustulas_vesiculas_piel,
					hisc_deteccion_alt_mn_2mes.getPustulas_vesiculas_piel());
			Utilidades
					.seleccionarRadio(rdbSecrecion_purulenta_ombligo,
							hisc_deteccion_alt_mn_2mes
									.getSecrecion_purulenta_ombligo());
			Utilidades.seleccionarRadio(rdbEritema_periumbilical,
					hisc_deteccion_alt_mn_2mes.getEritema_periumbilical());
			Utilidades.seleccionarRadio(rdbPlacas_blanquecinas_boca,
					hisc_deteccion_alt_mn_2mes.getPlacas_blanquecinas_boca());
			Utilidades.seleccionarRadio(rdbEquimosis_petequias,
					hisc_deteccion_alt_mn_2mes.getEquimosis_petequias());
			Utilidades.seleccionarRadio(rdbHemorragias,
					hisc_deteccion_alt_mn_2mes.getHemorragias());
			Utilidades.seleccionarRadio(rdbDistension_abdominal,
					hisc_deteccion_alt_mn_2mes.getDistension_abdominal());
			Utilidades.seleccionarRadio(rdbLlenado_capilar_3seg,
					hisc_deteccion_alt_mn_2mes.getLlenado_capilar_3seg());
			Utilidades.seleccionarRadio(rdbFontanela_abombada,
					hisc_deteccion_alt_mn_2mes.getFontanela_abombada());
			Utilidades.seleccionarRadio(rdbOjos_hundidos,
					hisc_deteccion_alt_mn_2mes.getOjos_hundidos());
			Utilidades.seleccionarRadio(rdbCabeza_cara,
					hisc_deteccion_alt_mn_2mes.getCabeza_cara());
			Utilidades.seleccionarRadio(rdbSigno_pliegue_cutaneo,
					hisc_deteccion_alt_mn_2mes.getSigno_pliegue_cutaneo());
			Utilidades.seleccionarRadio(rdbOrgarnos_sentidos,
					hisc_deteccion_alt_mn_2mes.getOrgarnos_sentidos());
			Utilidades.seleccionarRadio(rdbRojo_retiniano,
					hisc_deteccion_alt_mn_2mes.getRojo_retiniano());
			Utilidades.seleccionarRadio(rdbCuello,
					hisc_deteccion_alt_mn_2mes.getCuello());
			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar,
					hisc_deteccion_alt_mn_2mes.getTorax_cardiopulmonar());
			Utilidades.seleccionarRadio(rdbRitmo_cardiaco,
					hisc_deteccion_alt_mn_2mes.getRitmo_cardiaco());
			Utilidades.seleccionarRadio(rdbSoplo,
					hisc_deteccion_alt_mn_2mes.getSoplo());
			Utilidades.seleccionarRadio(rdbMasas,
					hisc_deteccion_alt_mn_2mes.getMasas());
			Utilidades.seleccionarRadio(rdbMegalias,
					hisc_deteccion_alt_mn_2mes.getMegalias());
			Utilidades.seleccionarRadio(rdbGenito_urinario,
					hisc_deteccion_alt_mn_2mes.getGenito_urinario());
			Utilidades.seleccionarRadio(rdbColumna_vertebral,
					hisc_deteccion_alt_mn_2mes.getColumna_vertebral());
			Utilidades.seleccionarRadio(rdbExremidades,
					hisc_deteccion_alt_mn_2mes.getExremidades());
			Utilidades.seleccionarRadio(rdbPiel_anexos,
					hisc_deteccion_alt_mn_2mes.getPiel_anexos());
			Utilidades.seleccionarRadio(rdbReflejo_moro,
					hisc_deteccion_alt_mn_2mes.getReflejo_moro());
			Utilidades.seleccionarRadio(rdbReflejo_busqueda,
					hisc_deteccion_alt_mn_2mes.getReflejo_busqueda());
			Utilidades.seleccionarRadio(rdbReflejo_succion,
					hisc_deteccion_alt_mn_2mes.getReflejo_succion());
			Utilidades.seleccionarRadio(rdbReflejo_plantar,
					hisc_deteccion_alt_mn_2mes.getReflejo_plantar());
			Utilidades.seleccionarRadio(rdbReflejo_cocleo_palpear,
					hisc_deteccion_alt_mn_2mes.getReflejo_cocleo_palpear());
			tbxObservaciones_examen_fisico_sistemas
					.setValue(hisc_deteccion_alt_mn_2mes
							.getObservaciones_examen_fisico_sistemas());
			Utilidades.seleccionarRadio(rdbTiene_boca_abierta,
					hisc_deteccion_alt_mn_2mes.getTiene_boca_abierta());
			Utilidades.seleccionarRadio(rdbToca_seno_menton,
					hisc_deteccion_alt_mn_2mes.getToca_seno_menton());
			Utilidades.seleccionarRadio(rdbLabio_inferior_evertido,
					hisc_deteccion_alt_mn_2mes.getLabio_inferior_evertido());
			Utilidades.seleccionarRadio(rdbAreola__visible_encima_labio,
					hisc_deteccion_alt_mn_2mes
							.getAreola__visible_encima_labio());
			Utilidades.seleccionarRadio(rdbCabeza_cuerpo_nino_derecho,
					hisc_deteccion_alt_mn_2mes.getCabeza_cuerpo_nino_derecho());
			Utilidades.seleccionarRadio(rdbMadre_sostiene_cuerpo,
					hisc_deteccion_alt_mn_2mes.getMadre_sostiene_cuerpo());
			Utilidades.seleccionarRadio(rdbHijo_frente_madre,
					hisc_deteccion_alt_mn_2mes.getHijo_frente_madre());
			Utilidades.seleccionarRadio(rdbDireccion_pecho,
					hisc_deteccion_alt_mn_2mes.getDireccion_pecho());
			tbxEvaluacion_desarrollo.setValue(hisc_deteccion_alt_mn_2mes
					.getEvaluacion_desarrollo());

			tbxAnalisis.setValue(hisc_deteccion_alt_mn_2mes.getAnalisis());
			chbEstimulo_factores_protectores_terapeutico
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getEstimulo_factores_protectores_terapeutico());
			chbLactancia_materna_exclusiva_terapeutico
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getLactancia_materna_exclusiva_terapeutico());
			chbRecomendaciones_buen_trato_terapeutico
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getRecomendaciones_buen_trato_terapeutico());
			chbImpoprtancia_asistencia_controles_terapeutico
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getImpoprtancia_asistencia_controles_terapeutico());
			chbOrientaciones_vacunacion_terapeutico
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getOrientaciones_vacunacion_terapeutico());
			chbOrientaciones_madre_terapeutico
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getOrientaciones_madre_terapeutico());
			chbCrecimiento_adecuado.setChecked(hisc_deteccion_alt_mn_2mes
					.getCrecimiento_adecuado());
			chbRiesgo_de_desnutricion.setChecked(hisc_deteccion_alt_mn_2mes
					.getRiesgo_de_desnutricion());
			chbDesnutricion_severa.setChecked(hisc_deteccion_alt_mn_2mes
					.getDesnutricion_severa());
			chbDesnutricion.setChecked(hisc_deteccion_alt_mn_2mes
					.getDesnutricion());
			chbSospecha_retraso_desarrollo
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getSospecha_retraso_desarrollo());
			chbRiesgo_problema_desarrollo.setChecked(hisc_deteccion_alt_mn_2mes
					.getRiesgo_problema_desarrollo());
			chbDesarrollo_normal_factor_riesgo
					.setChecked(hisc_deteccion_alt_mn_2mes
							.getDesarrollo_normal_factor_riesgo());
			chbDesarrollo_normal.setChecked(hisc_deteccion_alt_mn_2mes
					.getDesarrollo_normal());
			tbxObservaciones_plan_terapeutico
					.setValue(hisc_deteccion_alt_mn_2mes
							.getObservaciones_plan_terapeutico());

			tbxObservaciones_vacunales.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_vacunales());
			
			Utilidades.seleccionarRadio(rdbAbdomen,
					hisc_deteccion_alt_mn_2mes.getObservaciones_abdomen());
			Utilidades.seleccionarRadio(rdbNeurologico,
					hisc_deteccion_alt_mn_2mes.getObservaciones_neurologico());
			
			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2", tbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",tbxObservaciones_abdomen,row1);
			FormularioUtil.mostrarObservaciones(rdbNeurologico,"2",tbxObservaciones_neurologico,row1);
			FormularioUtil.mostrarObservaciones(rdbOrgarnos_sentidos,"2",tbxObservaciones_sentidos,row2);
			FormularioUtil.mostrarObservaciones(rdbMasas,"2",tbxObservaciones_masas,row2);
			FormularioUtil.mostrarObservaciones(rdbReflejo_moro,"2",tbxObservaciones_moro,row2);
			FormularioUtil.mostrarObservaciones(rdbRojo_retiniano, "2", tbxObservaciones_rojo, row3);
			FormularioUtil.mostrarObservaciones(rdbMegalias, "2", tbxObservaciones_megalias, row3);
			FormularioUtil.mostrarObservaciones(rdbReflejo_busqueda, "2", tbxObservaciones_busqueda, row3);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2", tbxObservaciones_cuello, row4);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", tbxObservaciones_genito, row4);
			FormularioUtil.mostrarObservaciones(rdbReflejo_succion, "2", tbxObservaciones_succion, row4);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2", tbxObservaciones_torax, row5);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2", tbxObservaciones_columna, row5);
			FormularioUtil.mostrarObservaciones(rdbReflejo_palmar, "2", tbxObservaciones_palmar, row5);
			FormularioUtil.mostrarObservaciones(rdbRitmo_cardiaco, "2", tbxObservaciones_cardiaco, row6);
			FormularioUtil.mostrarObservaciones(rdbExremidades, "2", tbxObservaciones_extremidades, row6);
			FormularioUtil.mostrarObservaciones(rdbReflejo_plantar, "2", tbxObservaciones_plantar, row6);
			FormularioUtil.mostrarObservaciones(rdbSoplo, "2", tbxObservaciones_soplo, row7);
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "2", tbxObservaciones_piel, row7);
			FormularioUtil.mostrarObservaciones(rdbReflejo_cocleo_palpear, "2", tbxObservaciones_cocleo, row7);

			tbxObservaciones_cabeza.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cabeza());
			tbxObservaciones_abdomen.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_abdomen());
			tbxObservaciones_neurologico.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_neurologico());
			
			tbxObservaciones_sentidos.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_sentidos());
			tbxObservaciones_masas.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_masas());
			tbxObservaciones_moro.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_moro());
			
			tbxObservaciones_rojo.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_rojo());
			tbxObservaciones_megalias.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_megalias());
			tbxObservaciones_busqueda.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_busqueda());
			
			tbxObservaciones_cuello.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cuello());
			tbxObservaciones_genito.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_genito());
			tbxObservaciones_succion.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_succion());

			tbxObservaciones_torax.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_torax());
			tbxObservaciones_columna.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_columna());
			tbxObservaciones_palmar.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_palmar());

			tbxObservaciones_cardiaco.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cardiaco());
			tbxObservaciones_extremidades.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_extremidades());
			tbxObservaciones_plantar.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_plantar());

			tbxObservaciones_soplo.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_soplo());
			tbxObservaciones_piel.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_piel());
			tbxObservaciones_cocleo.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cocleo());
			
			cargarImpresionDiagnostica(hisc_deteccion_alt_mn_2mes);
			cargarEscalaDesarrollo(hisc_deteccion_alt_mn_2mes);
			calcularCoordenadas(false);

			cargarAnexo9_remision(hisc_deteccion_alt_mn_2mes);

			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);
			// inicializarVista(tipo_historia);
			groupboxConsultar.setVisible(false);
			groupboxEditar.setVisible(true);
			btnImprimir.setVisible(true);
			// inicializarVista(tipo_historia);
			// FormularioUtil.deshabilitarComponentes(gbxEvaluacion_anterior,true,
			// new String[] {});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		hisc_deteccion_alt_mn_2mes = (Hisc_deteccion_alt_mn_2mes) obj;
		try {
			int result = getServiceLocator()
					.getHisc_deteccion_alt_mn_2mesService().eliminar(
							hisc_deteccion_alt_mn_2mes);
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
		if (admision != null) {

			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.HC_MENOR_2_MESES);

			macroEscalaDesarrollo.setZkWindow(this);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_MESES);
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_MESES);

			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes = new Hisc_deteccion_alt_mn_2mes();
				hisc_deteccion_alt_mn_2mes.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_deteccion_alt_mn_2mes.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_deteccion_alt_mn_2mes.setIdentificacion(admision
						.getNro_identificacion());
				hisc_deteccion_alt_mn_2mes.setNro_ingreso(admision
						.getNro_ingreso());

				hisc_deteccion_alt_mn_2mes = getServiceLocator()
						.getHisc_deteccion_alt_mn_2mesService().consultar(
								hisc_deteccion_alt_mn_2mes);
				if (hisc_deteccion_alt_mn_2mes != null) {
					accionForm(OpcionesFormulario.MOSTRAR,
							hisc_deteccion_alt_mn_2mes);
					infoPacientes.setCodigo_historia(hisc_deteccion_alt_mn_2mes
							.getCodigo_historia());
				} else {
					groupboxEditar.setVisible(false);
					throw new Exception(
							"NO hay una historia clinica relacionada a este paciente admitido");
				}
			} else {
				if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
					accionForm(OpcionesFormulario.REGISTRAR, null);
					btnCancelar.setVisible(false);
				} else {
					accionForm(OpcionesFormulario.CONSULTAR, null);
					btnCancelar.setVisible(true);
				}
			}

		}
	}

	public Admision cargarAdmision(String nro_identeificacion,
			String nro_ingreso) {
		Admision admision = new Admision();
		admision.setCodigo_empresa(codigo_empresa);
		admision.setCodigo_sucursal(codigo_sucursal);
		admision.setNro_identificacion(nro_identeificacion);
		admision.setNro_ingreso(nro_ingreso);
		return getServiceLocator().getAdmisionService().consultar(admision);
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
		} else {
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() {
						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							toolbarbuttonTipo_historia
									.setLabel("Creando historia clinica");
						}
					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		// Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes =
		// (Hisc_deteccion_alt_mn_2mes) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });
			toolbarbuttonTipo_historia.setLabel("Mostrando Historia Clinica");
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			toolbarbuttonTipo_historia.setLabel("Modificando Historia Clinica");
			((Button) getFellow("btGuardar")).setVisible(true);
		}
	}

	// metodo seleccionar causas externas
	public void seleccion(Listbox listbox, int entero,
			Component[] abstractComponents) {
		try {
			String num = entero + "";
			for (Component abstractComponent : abstractComponents) {
				if (listbox.getSelectedItem().getValue().equals(num)) {
					abstractComponent.setVisible(true);
				} else {
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

	// METODO SELECIONAR DX
	public void selectedDx(Listitem listitem, Bandbox bandbox, Textbox textbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Cie dato = (Cie) listitem.getValue();
			bandbox.setValue(dato.getCodigo());
			textbox.setValue(dato.getNombre());
		}
		bandbox.close();
	}

	// METODO BUSCAR DX
	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Cie> list = getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, 
					parameters);

			lbx.getItems().clear();

			for (Cie dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_superior()));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	// DATOS CURVAS CRECIMIENTO.
	public void calcularCoordenadas(Boolean mostrarAlerta) {
		calcularPesoEdad(mostrarAlerta);
		calcularPerimetroCefalicoEdad(mostrarAlerta);
		calcularPesoTalla(mostrarAlerta);
		calcularTallaEdad(mostrarAlerta);
	}

	private List<RespuestaInt> cargarRespuestas(
			List<Coordenadas_graficas> coordenadas,
			Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes) {
		List<RespuestaInt> respuestas = new ArrayList<RespuestaInt>();
		for (Coordenadas_graficas cg : coordenadas) {
			RespuestaInt r = cargarResuestaInt(cg);
			if (hisc_deteccion_alt_mn_2mes != null
					&& cg.getCodigo_historia().equals(
							hisc_deteccion_alt_mn_2mes.getCodigo_historia())) {
				r.setActual(true);
			}
			respuestas.add(r);
		}
		return respuestas;
	}

	private RespuestaInt cargarResuestaInt(Coordenadas_graficas cg) {
		RespuestaInt r = new RespuestaInt();
		r.setValor(ConvertidorDatosUtil.convertirDato(cg.getValor()));
		r.setX(ConvertidorDatosUtil.convertirDato(cg.getX()));
		r.setY(ConvertidorDatosUtil.convertirDato(cg.getY()));
		return r;
	}

	// METODOS PARA VISTA
	public void calcularPesoEdad(Boolean mostrarAlerta) {
		double peso;
		double talla;

		if (dbxExamen_fisico_peso.getValue() == null) {
			dbxExamen_fisico_peso.setStyle("background-color:#F6BBBE");
			if(mostrarAlerta){
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_peso.setStyle("background-color:white");
			peso = dbxExamen_fisico_peso.getValue();
		}

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if(mostrarAlerta){
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_talla.setStyle("background-color:white");
			talla = dbxExamen_fisico_talla.getValue();
		}

		if (dbxExamen_fisico_talla.getValue() != null
				&& dbxExamen_fisico_peso.getValue() != null
				&& tallaValida(talla) && pesoValido(peso)) {
			coordenadaPesoEdad = calcularPesoEdad(peso, talla, fecha);
			dbxP_e_de.setValue(coordenadaPesoEdad.getValor());
			if (mostrarAlerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxP_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PESO_EDAD);
			}
			btnCalcularPesoEdad.setDisabled(false);
		} else {
			if(mostrarAlerta){
				if (!tallaValida(talla)) {
					Messagebox.show(
							"La talla está por fuera del rango [45.1-95]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				} else if (!(peso >= 1 && peso <= 17)) {
					Messagebox.show("El peso está por fuera del rango [1-17]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		}
	}

	public void calcularTallaEdad(Boolean mostrarAlerta) {
		double talla;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if(mostrarAlerta){
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_talla.setStyle("background-color:white");
			talla = dbxExamen_fisico_talla.getValue();
		}

		if (dbxExamen_fisico_talla.getValue() != null && tallaValida(talla)) {
			coordenadaTallaEdad = calcularTallaEdad(talla, fecha);
			dbxT_e_de.setValue(coordenadaTallaEdad.getValor());
			if (mostrarAlerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxT_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_TALLA_EDAD);
			}
			btnCalcularTallaEdad.setDisabled(false);
		} else {
			if(mostrarAlerta){
				if (!(tallaValida(talla))) {
					Messagebox.show(
							"La talla está por fuera del rango [45.1-95]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		}
	}

	public void calcularPesoTalla(Boolean mostrarAlerta) {

		double talla;
		double peso;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if(mostrarAlerta){
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_talla.setStyle("background-color:white");
			talla = dbxExamen_fisico_talla.getValue();
		}

		if (dbxExamen_fisico_peso.getValue() == null) {
			dbxExamen_fisico_peso.setStyle("background-color:#F6BBBE");
			
			if(mostrarAlerta){
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_peso.setStyle("background-color:white");
			peso = dbxExamen_fisico_peso.getValue();
		}

		if (dbxExamen_fisico_talla.getValue() != null
				&& dbxExamen_fisico_peso.getValue() != null
				&& tallaValida(talla) && pesoValido(peso)) {
			coordenadaPesoTalla = calcularPesoTalla(peso, talla);
			dbxP_t_de.setValue(coordenadaPesoTalla.getValor());
			if (mostrarAlerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxP_t_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PESO_TALLA);
			}
			btnCalcularPesoTalla.setDisabled(false);
		} else {
			if(mostrarAlerta){
				if (!tallaValida(talla)) {
					Messagebox.show(
							"La talla está por fuera del rango [45.1-95]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				} else if (!pesoValido(peso)) {
					Messagebox.show("El peso está por fuera del rango [1-17]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		}
	}

	public void calcularPerimetroCefalicoEdad(Boolean mostrarAlerta) {
		double perimetro_cefalico;

		if (dbxExamen_fisico_perimetro_cflico.getValue() == null) {
			dbxExamen_fisico_perimetro_cflico
					.setStyle("background-color:#F6BBBE");
			if(mostrarAlerta){
			Messagebox
					.show("Debe digitar el perimetro cefálico del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_perimetro_cflico
					.setStyle("background-color:white");
			perimetro_cefalico = dbxExamen_fisico_perimetro_cflico.getValue();
		}

		if (dbxExamen_fisico_perimetro_cflico.getValue() != null
				&& perimetroCefalicoValido(perimetro_cefalico)) {
			coordenadaPcEdad = calcularPerimetroCefalicoEdad(
					perimetro_cefalico, fecha);
			dbxPC_e_de.setValue(coordenadaPcEdad.getValor());
			if (mostrarAlerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxPC_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PC_EDAD);
			}
			btnCalcularPerimetroCefalicoEdad.setDisabled(false);
		} else {
			if(mostrarAlerta){
			Messagebox.show(
					"El perimetro cefálico está por fuera del rango [30-55]!!",
					"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	public boolean pesoValido(Double peso) {
		Double min = 1.0;
		Double max = 17.0;
		return (peso >= min && peso <= max);
	}

	public boolean tallaValida(Double talla) {
		Double min = 45.1;
		Double max = 95.0;
		return (talla >= min && talla <= max);
	}

	public boolean perimetroCefalicoValido(Double perimetro_cefalico) {
		Double min = 33.0;
		Double max = 55.0;
		return (perimetro_cefalico >= min && perimetro_cefalico <= max);
	}

	private RespuestaInt calcularPesoEdad(double peso, double talla,
			Timestamp fecha) {
		return GraficasCalculadorUtils.calcularPesoEdad0$2Anios(sexo, peso,
				talla, fecha);
	}

	private RespuestaInt calcularPerimetroCefalicoEdad(
			double perimetro_cefalico, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
				sexo, perimetro_cefalico, fecha);
	}

	private RespuestaInt calcularPesoTalla(double peso, double talla) {
		return GraficasCalculadorUtils.calcularPesoTalla0$2Anios(sexo, peso,
				talla);
	}

	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularTallaEdad0$2Anios(sexo, talla,
				fecha);
	}

	/* Graficamos */
	public void graficarPesoEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_MESES);
		List<Coordenadas_graficas> pes = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasPE = cargarRespuestas(pes, hisc_deteccion_alt_mn_2mes);

		List coordenadas_pe = coordenadasPE;
		if (!verificarActivo(coordenadasPE)) {
			coordenadas_pe.add(coordenadaPesoEdad);
		}

		// imprimirArreglo(coordenadas_pe);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.PESO_EDAD_0_A_2_ANIOS,
				coordenadas_pe, formHisc_deteccion_alt_mn_2mes, sexo);
	}

	public void mostrarTablaPesoEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.PESO_EDAD_0_A_2_ANIOS,
				formHisc_deteccion_alt_mn_2mes, sexo);
	};

	public void graficarTallaEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.T_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_MESES);
		List<Coordenadas_graficas> tes = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasTE = cargarRespuestas(tes, hisc_deteccion_alt_mn_2mes);

		List coordenadas_te = coordenadasTE;
		if (!verificarActivo(coordenadasTE)) {
			coordenadas_te.add(coordenadaTallaEdad);
		}
		// imprimirArreglo(coordenadas_te);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_0_A_2_ANIOS,
				coordenadas_te, formHisc_deteccion_alt_mn_2mes, sexo);
	}

	public void mostrarTablaTallaEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_0_A_2_ANIOS,
				formHisc_deteccion_alt_mn_2mes, sexo);
	};

	public void graficarPesoTalla() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_T);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_MESES);
		List<Coordenadas_graficas> pts = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasPT = cargarRespuestas(pts, hisc_deteccion_alt_mn_2mes);

		List coordenadas_pt = coordenadasPT;
		if (!verificarActivo(coordenadasPT)) {
			coordenadas_pt.add(coordenadaPesoTalla);
		}
		// imprimirArreglo(coordenadas_pt);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_0_A_2,
				coordenadas_pt, formHisc_deteccion_alt_mn_2mes, sexo);
	}

	public void mostrarTablaPesoTalla() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_0_A_2,
				formHisc_deteccion_alt_mn_2mes, sexo);
	};

	public void graficarPcEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.PC_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_MESES);
		List<Coordenadas_graficas> pces = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);

		coordenadasPCE = cargarRespuestas(pces, hisc_deteccion_alt_mn_2mes);

		List coordenadas_pce = coordenadasPCE;
		if (!verificarActivo(coordenadasPCE)) {
			coordenadas_pce.add(coordenadaPcEdad);
		}
		// imprimirArreglo(coordenadas_pce);
		GraficasCalculadorUtils
				.mostrarGrafica(
						GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5,
						coordenadas_pce, formHisc_deteccion_alt_mn_2mes, sexo);
	}

	public void mostrarTablaPcEdad() {
		GraficasCalculadorUtils
				.mostrarTabla(
						GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5,
						formHisc_deteccion_alt_mn_2mes, sexo);
	};

	public boolean verificarActivo(List<RespuestaInt> arreglo) {
		boolean actual = false;
		for (RespuestaInt ri : arreglo) {
			if (ri.isActual()) {
				actual = true;
				break;
			}
		}
		return actual;
	}

	// FIN CURVAS CRECIMIENTO

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

	private void cargarImpresionDiagnostica(
			Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);

		impresion_diagnostica.setCodigo_historia(hisc_deteccion_alt_mn_2mes
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	private void cargarRemisionInterna(
			Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_deteccion_alt_mn_2mes
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_deteccion_alt_mn_2mes
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_deteccion_alt_mn_2mes
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private void cargarAnexo9_remision(
			Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_deteccion_alt_mn_2mes
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null)
			remisiones_externasAction.detach();
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
		
		parametros.put("opcion_formulario_historia",
				opciones_via_ingreso.toString());

		remisiones_externasAction = (Remisiones_externasAction) Executions
				.createComponents("/pages/remisiones_externas.zul", null,
						parametros);
		remisiones_externasAction.inicializar(this);
		divModuloRemisiones_externas.appendChild(remisiones_externasAction);

	}

	private void cargarEscalaDesarrollo(
			Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes) {
		Escala_del_desarrollo escala_del_desarrollo = new Escala_del_desarrollo();
		escala_del_desarrollo.setCodigo_empresa(codigo_empresa);
		escala_del_desarrollo.setCodigo_sucursal(codigo_sucursal);
		escala_del_desarrollo.setVia_ingreso(IVias_ingreso.HC_MENOR_2_MESES);
		escala_del_desarrollo.setCodigo_historia(hisc_deteccion_alt_mn_2mes
				.getCodigo_historia());
		escala_del_desarrollo = getServiceLocator().getServicio(
				Escala_del_desarrolloService.class).consultar(
				escala_del_desarrollo);
		macroEscalaDesarrollo.mostrarEscalaDesarrollo(escala_del_desarrollo,
				admision);

	}

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :\n");
		informacion_clinica.append("\t")
				.append(tbxMotivos_de_la_consulta.getValue()).append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :\n");
		if (tbxEnfermedad_actual.getValue() != null
				&& !tbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxEnfermedad_actual.getValue()).append("\n");
		} else if (tbxEnfermedad_actual.getValue() != null
				&& !tbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxEnfermedad_actual.getValue()).append("\n");
		}

		informacion_clinica.append("\n");

		informacion_clinica.append("- SIGNOS VITALES\n");
		informacion_clinica.append("\tFrecuencia cardíaca(min): ")
				.append(dbxExamen_fisico_fr.getValue() + ("\n")).append("\t")
				.append("Frecuencia respiratoria(min): ")
				.append(dbxExamen_fisico_fr.getValue() + ("\n"));
		informacion_clinica.append("\tTemperatura: ")
				.append(dbxExamen_fisico_temp.getValue() + ("\n"))
				.append(" \tPeso(kg): ")
				.append(dbxExamen_fisico_peso.getValue() + ("\n"))
				.append(" \tTalla(Cm): ")
				.append(dbxExamen_fisico_talla.getValue() + ("\n"))
				.append("\tPerímetro cefálico (Cm): ")
				.append(dbxExamen_fisico_perimetro_cflico.getValue() + ("\n"))
				.append("\n");
		informacion_clinica.append("\n");

		informacion_clinica.append("- DIAGNOSTICOS\n");
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

		return informacion_clinica.toString();
	}
	
	public void cargarSignosVitalesEnfermera(){
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos.setNro_identificacion(admision.getNro_identificacion()	);
		enfermera_signos = getServiceLocator().getServicio(Enfermera_signosService.class).consultar(enfermera_signos);
		if(enfermera_signos != null){
						
			dbxExamen_fisico_peso.setValue(enfermera_signos.getPeso());
			dbxExamen_fisico_talla.setValue(enfermera_signos.getTalla());
			dbxExamen_fisico_perimetro_cflico.setValue(enfermera_signos.getPerimetro_cefalico());
			dbxExamen_fisico_fc.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxExamen_fisico_fr.setValue(enfermera_signos.getFrecuencia_respiratoria());
			dbxExamen_fisico_temp.setValue(enfermera_signos.getTemperatura());
			
			alarmaExamenFisicoFc();
			alarmaExamenFisicoFr();
			alarmaExamenFisicoTemperatura();
			
		}
	}
	/*
	public void imprimir() throws Exception {
		String nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia.equals("")) {
			Messagebox.show("La historia no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Historia_pyp_menor_2meses");
		paramRequest.put("nro_historia", nro_historia);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue().toString());

		paramRequest.put("sexo", admision.getPaciente().getSexo());
		
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}
	*/
	
	public void imprimir() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		Long nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia != null) {
			String parametros_pagina="?codigo_empresa="+codigo_empresa;
			   parametros_pagina+="&codigo_sucursal="+codigo_sucursal;
			   parametros_pagina+="&codigo_historia="+nro_historia;
			   parametros_pagina+="&nro_ingreso="+admision.getNro_ingreso();
			   parametros_pagina+="&nro_identificacion="+admision.getNro_identificacion();
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/hisc_deteccion_alt_mn_2mes_reporte.zul"+parametros_pagina+"\", '_blank');");
		}
	}
	
	
	
	

}