/*
 * hisc_deteccion_alt_mn_2mesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Escala_del_desarrollo;
import healthmanager.modelo.bean.Hisc_deteccion_alt_mn_2mes;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Escala_del_desarrolloService;
import healthmanager.modelo.service.Hisc_deteccion_alt_mn_2mesService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IHistorias_clinicas;
import com.framework.constantes.ITipos_coordenada;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.EscalaAbreviadaDesarrollo;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_deteccion_alt_mn_2mes_reporteAction extends
		HistoriaClinicaModel implements IHistoria_generica {

	@View
	private Groupbox gbxContenido;

	@View
	private Image imgLogo;
	@View
	private Label lblTitulo;
	@View
	private Label lblMedicoTratante;
	@View
	private Label lblRegMedico;
	@View
	private Image imgFirma;

	private Admision admision;

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
	private Label lbxMotivos_de_la_consulta;
	@View
	private Label lbxEnfermedad_actual;
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
	private Label lbxComplicaciones_embarazo_parto;
	@View
	private Doublebox dbxTalla_al_nacer;
	@View
	private Intbox ibxEdad_de_la_madre_al_nacer;
	@View
	private Label lbxObservaciones_perinatales;
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
	private Label lbxPatologico_medico;
	@View
	private Label lbxPatologico_quirurgicos;
	@View
	private Label lbxPatologico_hospitalizaciones;
	@View
	private Label lbxPatologico_medicacion;
	// ------------- Jose

	@View
	private Label lbxPatologicos_alergicos;

	// -------------------

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
	private Label lbxObservaciones_alimentario;
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
	private Label lbxPaternos_medico;
	@View
	private Label lbxMaternos_medico;
	@View
	private Textbox tbxPaternos_alergico;
	@View
	private Doublebox dbxPaternos_talla;
	@View
	private Textbox tbxMaternos_alergico;
	@View
	private Doublebox dbxMaternos_talla;
	@View
	private Label lbxOtros_antc_familiar;
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
	private Label lbxObservaciones_examen_fisico_sistemas;
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
	private Label lbxEvaluacion_desarrollo;
	@View
	private Label lbxAnalisis;
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
	private Label lbxObservaciones_plan_terapeutico;

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
	private Label lbxObservaciones_vacunales;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

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

	@View(isMacro = true)
	private EscalaAbreviadaDesarrollo macroEscalaDesarrollo;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "macroEscalaDesarrollo" };

	/* Observaciones */

	@View
	private Label lbxObservaciones_cabeza;
	@View
	private Label lbxObservaciones_sentidos;
	@View
	private Label lbxObservaciones_rojo;
	@View
	private Label lbxObservaciones_cuello;
	@View
	private Label lbxObservaciones_torax;
	@View
	private Label lbxObservaciones_cardiaco;
	@View
	private Label lbxObservaciones_soplo;
	@View
	private Label lbxObservaciones_abdomen;
	@View
	private Label lbxObservaciones_masas;
	@View
	private Label lbxObservaciones_megalias;
	@View
	private Label lbxObservaciones_genito;
	@View
	private Label lbxObservaciones_columna;
	@View
	private Label lbxObservaciones_extremidades;
	@View
	private Label lbxObservaciones_piel;
	@View
	private Label lbxObservaciones_neurologico;
	@View
	private Label lbxObservaciones_moro;
	@View
	private Label lbxObservaciones_busqueda;
	@View
	private Label lbxObservaciones_succion;
	@View
	private Label lbxObservaciones_palmar;
	@View
	private Label lbxObservaciones_plantar;
	@View
	private Label lbxObservaciones_cocleo;

	@View
	private Row row1;
	@View
	private Row row2;
	@View
	private Row row3;
	@View
	private Row row4;
	@View
	private Row row5;
	@View
	private Row row6;
	@View
	private Row row7;

	@Override
	public void init() {
		try {
			HttpServletRequest request = (HttpServletRequest) Executions
					.getCurrent().getNativeRequest();
			String codigo_historia = request.getParameter("codigo_historia");
			String nro_ingreso = request.getParameter("nro_ingreso");
			String nro_identificacion = request
					.getParameter("nro_identificacion");
			String codigo_empresa = request.getParameter("codigo_empresa");
			String codigo_sucursal = request.getParameter("codigo_sucursal");

			Long id_codigo_historia = null;

			if (codigo_historia != null && !codigo_historia.trim().isEmpty()
					&& codigo_historia.matches("[0-9]*$")) {
				id_codigo_historia = Long.parseLong(codigo_historia);
			}

			if (nro_identificacion != null) {
				admision = new Admision();
				admision.setCodigo_empresa(codigo_empresa);
				admision.setCodigo_sucursal(codigo_sucursal);
				admision.setNro_identificacion(nro_identificacion);
				admision.setNro_ingreso(nro_ingreso);
				admision = getServiceLocator().getServicio(
						AdmisionService.class).consultar(admision);
				if (admision != null) {
					macroImpresion_diagnostica.inicializarMacro(this, admision,
							IVias_ingreso.HC_MENOR_2_MESES);
					paciente = admision.getPaciente();
					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
							: ESexo.MASCULINO);
					fecha = paciente.getFecha_nacimiento();
				}
			}

			listarCombos();

			if (id_codigo_historia != null) {
				hisc_deteccion_alt_mn_2mes = new Hisc_deteccion_alt_mn_2mes();
				hisc_deteccion_alt_mn_2mes.setCodigo_empresa(codigo_empresa);
				hisc_deteccion_alt_mn_2mes.setCodigo_sucursal(codigo_sucursal);
				hisc_deteccion_alt_mn_2mes
						.setCodigo_historia(id_codigo_historia);
				hisc_deteccion_alt_mn_2mes = getServiceLocator().getServicio(
						Hisc_deteccion_alt_mn_2mesService.class).consultar(
						hisc_deteccion_alt_mn_2mes);
				if (hisc_deteccion_alt_mn_2mes != null) {

					lblTitulo
							.setValue("HISTORIA CLINICA DETECcion DE ALTERAcion AL MENOR DE 2 MESES");

					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService()
							.consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));

					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(hisc_deteccion_alt_mn_2mes
							.getCreacion_user());
					prestador = getServiceLocator().getServicio(
							UsuariosService.class).consultar(prestador);
					if (prestador != null) {
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres() + " "
								+ prestador.getApellidos());
						if (prestador.getFirma() != null) {
							imgFirma.setContent(new AImage("firma", prestador
									.getFirma()));
						}
					}
					mostrarDatos(hisc_deteccion_alt_mn_2mes);
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,
							idsExcluyentes);
				}
			}

			FormularioUtil.inicializarRadiogroupsDefecto(this);
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
			paciente = admision.getPaciente();
			sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
					: ESexo.MASCULINO);
			macroEscalaDesarrollo.setAdmision(admision);
			macroEscalaDesarrollo.mostrarColorFila1Edad();
			macroEscalaDesarrollo.mostrarColorFila11Edad();
			macroEscalaDesarrollo.mostrarColorFila12Edad();
			macroEscalaDesarrollo.mostrarColorFila13Edad();
			fecha = paciente.getFecha_nacimiento();
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
		Utilidades.listarElementoListbox(lbxParentesco, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxHemoclasificacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxVdlr, true, getServiceLocator());
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		hisc_deteccion_alt_mn_2mes = (Hisc_deteccion_alt_mn_2mes) obj;
		try {

			infoPacientes.setCodigo_historia(hisc_deteccion_alt_mn_2mes
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_deteccion_alt_mn_2mes
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_deteccion_alt_mn_2mes.getUltimo_update());
			initMostrar_datos(hisc_deteccion_alt_mn_2mes);

			// onMostrarModuloRemisiones();

			cargarInformacion_paciente();
			// cargarRemisionInterna(hisc_deteccion_alt_mn_2mes);

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
			lbxMotivos_de_la_consulta.setValue(hisc_deteccion_alt_mn_2mes
					.getMotivos_de_la_consulta());
			lbxEnfermedad_actual.setValue(hisc_deteccion_alt_mn_2mes
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
			lbxComplicaciones_embarazo_parto
					.setValue(hisc_deteccion_alt_mn_2mes
							.getComplicaciones_embarazo_parto());
			dbxTalla_al_nacer.setValue(hisc_deteccion_alt_mn_2mes
					.getTalla_al_nacer());
			ibxEdad_de_la_madre_al_nacer.setValue(hisc_deteccion_alt_mn_2mes
					.getEdad_de_la_madre_al_nacer());
			lbxObservaciones_perinatales.setValue(hisc_deteccion_alt_mn_2mes
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
			Utilidades.seleccionarListitem(lbxVdlr,
					hisc_deteccion_alt_mn_2mes.getVdrl_materno());
			Utilidades.seleccionarRadio(rdbInstitucioneal,
					hisc_deteccion_alt_mn_2mes.getInstitucioneal());
			tbxTsh.setValue(hisc_deteccion_alt_mn_2mes.getTsh());
			ibxSem_gestacion.setValue(hisc_deteccion_alt_mn_2mes
					.getSem_gestacion());
			lbxPatologico_medico.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_medico());
			lbxPatologico_quirurgicos.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_quirurgicos());
			lbxPatologico_hospitalizaciones.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_hospitalizaciones());
			lbxPatologico_medicacion.setValue(hisc_deteccion_alt_mn_2mes
					.getPatologico_medicacion());
			lbxPatologicos_alergicos.setValue(hisc_deteccion_alt_mn_2mes
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
			lbxObservaciones_alimentario.setValue(hisc_deteccion_alt_mn_2mes
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
			lbxPaternos_medico.setValue(hisc_deteccion_alt_mn_2mes
					.getPaternos_medico());
			lbxMaternos_medico.setValue(hisc_deteccion_alt_mn_2mes
					.getMaternos_medico());
			tbxPaternos_alergico.setValue(hisc_deteccion_alt_mn_2mes
					.getPaternos_alergico());
			dbxPaternos_talla.setValue(hisc_deteccion_alt_mn_2mes
					.getPaternos_talla());
			tbxMaternos_alergico.setValue(hisc_deteccion_alt_mn_2mes
					.getMaternos_alergico());
			dbxMaternos_talla.setValue(hisc_deteccion_alt_mn_2mes
					.getMaternos_talla());
			lbxOtros_antc_familiar.setValue(hisc_deteccion_alt_mn_2mes
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
			lbxObservaciones_examen_fisico_sistemas
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
			lbxEvaluacion_desarrollo.setValue(hisc_deteccion_alt_mn_2mes
					.getEvaluacion_desarrollo());

			lbxAnalisis.setValue(hisc_deteccion_alt_mn_2mes.getAnalisis());
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
			lbxObservaciones_plan_terapeutico
					.setValue(hisc_deteccion_alt_mn_2mes
							.getObservaciones_plan_terapeutico());

			lbxObservaciones_vacunales.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_vacunales());

			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2",
					lbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbAbdomen, "2",
					lbxObservaciones_abdomen, row1);
			FormularioUtil.mostrarObservaciones(rdbNeurologico, "2",
					lbxObservaciones_neurologico, row1);
			FormularioUtil.mostrarObservaciones(rdbOrgarnos_sentidos, "2",
					lbxObservaciones_sentidos, row2);
			FormularioUtil.mostrarObservaciones(rdbMasas, "2",
					lbxObservaciones_masas, row2);
			FormularioUtil.mostrarObservaciones(rdbReflejo_moro, "2",
					lbxObservaciones_moro, row2);
			FormularioUtil.mostrarObservaciones(rdbRojo_retiniano, "2",
					lbxObservaciones_rojo, row3);
			FormularioUtil.mostrarObservaciones(rdbMegalias, "2",
					lbxObservaciones_megalias, row3);
			FormularioUtil.mostrarObservaciones(rdbReflejo_busqueda, "2",
					lbxObservaciones_busqueda, row3);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2",
					lbxObservaciones_cuello, row4);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2",
					lbxObservaciones_genito, row4);
			FormularioUtil.mostrarObservaciones(rdbReflejo_succion, "2",
					lbxObservaciones_succion, row4);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2",
					lbxObservaciones_torax, row5);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2",
					lbxObservaciones_columna, row5);
			FormularioUtil.mostrarObservaciones(rdbReflejo_palmar, "2",
					lbxObservaciones_palmar, row5);
			FormularioUtil.mostrarObservaciones(rdbRitmo_cardiaco, "2",
					lbxObservaciones_cardiaco, row6);
			FormularioUtil.mostrarObservaciones(rdbExremidades, "2",
					lbxObservaciones_extremidades, row6);
			FormularioUtil.mostrarObservaciones(rdbReflejo_plantar, "2",
					lbxObservaciones_plantar, row6);
			FormularioUtil.mostrarObservaciones(rdbSoplo, "2",
					lbxObservaciones_soplo, row7);
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "2",
					lbxObservaciones_piel, row7);
			FormularioUtil.mostrarObservaciones(rdbReflejo_cocleo_palpear, "2",
					lbxObservaciones_cocleo, row7);

			lbxObservaciones_cabeza.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cabeza());
			lbxObservaciones_abdomen.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_abdomen());
			lbxObservaciones_neurologico.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_neurologico());

			lbxObservaciones_sentidos.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_sentidos());
			lbxObservaciones_masas.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_masas());
			lbxObservaciones_moro.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_moro());

			lbxObservaciones_rojo.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_rojo());
			lbxObservaciones_megalias.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_megalias());
			lbxObservaciones_busqueda.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_busqueda());

			lbxObservaciones_cuello.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cuello());
			lbxObservaciones_genito.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_genito());
			lbxObservaciones_succion.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_succion());

			lbxObservaciones_torax.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_torax());
			lbxObservaciones_columna.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_columna());
			lbxObservaciones_palmar.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_palmar());

			lbxObservaciones_cardiaco.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cardiaco());
			lbxObservaciones_extremidades.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_extremidades());
			lbxObservaciones_plantar.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_plantar());

			lbxObservaciones_soplo.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_soplo());
			lbxObservaciones_piel.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_piel());
			lbxObservaciones_cocleo.setValue(hisc_deteccion_alt_mn_2mes
					.getObservaciones_cocleo());

			cargarImpresionDiagnostica(hisc_deteccion_alt_mn_2mes);
			cargarEscalaDesarrollo(hisc_deteccion_alt_mn_2mes);
			calcularCoordenadas(false);

			cargarAnexo9_remision(hisc_deteccion_alt_mn_2mes);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// metodo listar datos del prestador
	public void buscarAdmision(String value, String valor_estado, Listbox lbx)
			throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			if (!valor_estado.equals("")) {
				parameters.put("estado", valor_estado);
			}

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Admision> list = getServiceLocator().getAdmisionService()
					.listarResultados(parameters);

			lbx.getItems().clear();

			for (Admision dato : list) {

				Paciente paciente = admision.getPaciente();

				String apellidos = (paciente != null ? paciente.getApellido1()
						+ " " + paciente.getApellido2() : "");
				String nombres = (paciente != null ? paciente.getNombre1()
						+ " " + paciente.getNombre2() : "");

				String estado = (dato.getEstado().equals("1") ? "Activo"
						: "Terminada");

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_ingreso() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(apellidos));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(nombres));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(new SimpleDateFormat(
						"yyyy-MM-dd HH:mm").format(dato.getFecha_ingreso())));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(estado));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {

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

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {

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
			System.out.println("Excepcion loaded");
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
			System.out.println("Excepcion loaded");
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
			if (mostrarAlerta) {
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
			if (mostrarAlerta) {
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
			// btnCalcularPesoEdad.setDisabled(false);
		} else {
			if (mostrarAlerta) {
				if (!tallaValida(talla)) {
					Messagebox.show(
							"La talla está por fuera del rango [45.1-95]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				} else if (!(peso >= 1 && peso <= 17)) {
					Messagebox.show(
							"El peso está por fuera del rango [1-17]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		}
	}

	public void calcularTallaEdad(Boolean mostrarAlerta) {
		double talla;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if (mostrarAlerta) {
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
			// btnCalcularTallaEdad.setDisabled(false);
		} else {
			if (mostrarAlerta) {
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
			if (mostrarAlerta) {
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

			if (mostrarAlerta) {
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
			// btnCalcularPesoTalla.setDisabled(false);
		} else {
			if (mostrarAlerta) {
				if (!tallaValida(talla)) {
					Messagebox.show(
							"La talla está por fuera del rango [45.1-95]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				} else if (!pesoValido(peso)) {
					Messagebox.show(
							"El peso está por fuera del rango [1-17]!!",
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
			if (mostrarAlerta) {
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
			// btnCalcularPerimetroCefalicoEdad.setDisabled(false);
		} else {
			if (mostrarAlerta) {
				Messagebox
						.show("El perimetro cefálico está por fuera del rango [30-55]!!",
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
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
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
		// log.info("cargando anexo9_entidad === >" + anexo9_entidad);
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
				.append(lbxMotivos_de_la_consulta.getValue()).append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :\n");
		if (lbxEnfermedad_actual.getValue() != null
				&& !lbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxEnfermedad_actual.getValue()).append("\n");
		} else if (lbxEnfermedad_actual.getValue() != null
				&& !lbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxEnfermedad_actual.getValue()).append("\n");
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

			dbxExamen_fisico_peso.setValue(enfermera_signos.getPeso());
			dbxExamen_fisico_talla.setValue(enfermera_signos.getTalla());
			dbxExamen_fisico_perimetro_cflico.setValue(enfermera_signos
					.getPerimetro_cefalico());
			dbxExamen_fisico_fc.setValue(enfermera_signos
					.getFrecuencia_cardiaca());
			dbxExamen_fisico_fr.setValue(enfermera_signos
					.getFrecuencia_respiratoria());
			dbxExamen_fisico_temp.setValue(enfermera_signos.getTemperatura());

			alarmaExamenFisicoFc();
			alarmaExamenFisicoFr();
			alarmaExamenFisicoTemperatura();

		}
	}

}