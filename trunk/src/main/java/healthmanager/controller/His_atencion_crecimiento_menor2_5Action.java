/*
 * his_atencion_crecimiento_menor2_5Action.java
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
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Escala_del_desarrollo;
import healthmanager.modelo.bean.His_atencion_crecimiento_menor2_5;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Escala_del_desarrolloService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
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
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.EscalaAbreviadaDesarrollo;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculadorUtil;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class His_atencion_crecimiento_menor2_5Action extends
		HistoriaClinicaModel implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(His_atencion_crecimiento_menor2_5Action.class);

	// Componentes //

	// Manuel
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;

	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;
	@View
	private Row rowAgudeza_visual;
	@View
	private Listbox lbxParameter;
	@View
	private Listbox lbxTipoHistoria;
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
	private Textbox tbxNom_acompanante;
	@View
	private Listbox lbxRelacion;
	@View
	private Intbox ibxEdad_padre;
	@View
	private Textbox tbxNom_padre;
	@View
	private Textbox tbxNom_madre;
	@View
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Radiogroup rdbVomita_todo;
	@View
	private Radiogroup rdbDiarrea_ultimas;
	@View
	private Radiogroup rdbVive_zona_dengue;
	@View
	private Radiogroup rdbConvulsiones;
	@View
	private Radiogroup rdbSangre_heces;
	@View
	private Radiogroup rdbVive_zona_malaria;
	@View
	private Radiogroup rdbTiene_tos_disnea;
	@View
	private Radiogroup rdbTiene_movito;
	@View
	private Radiogroup rdbTiene_otalgia;
	@View
	private Radiogroup rdbTiene_sibilancia;
	@View
	private Radiogroup rdbVomito_ultimas_hora;
	@View
	private Radiogroup rdbTiene_supuracion_oido;
	@View
	private Radiogroup rdbSibigilancias_previas;
	@View
	private Radiogroup rdbTiene_fibre_mayor5_dias;
	@View
	private Radiogroup rdbEpisodios_previos_otitis;
	@View
	private Radiogroup rdbCuadro_gripal_ultimos_dias;
	@View
	private Radiogroup rdbTiene_fiebre_menor5;
	@View
	private Radiogroup rdbTien_dolor_gargan;
	@View
	private Radiogroup rdbTien_diarrea;
	@View
	private Radiogroup rdbFiebre_mayor38;
	@View
	private Radiogroup rdbCefalea_dolor_retocular;
	@View
	private Radiogroup rdbDiarrea_ultimas_24horas;
	@View
	private Radiogroup rdbFiebre_mayor39;
	@View
	private Radiogroup rdbMialgias_artralgias;
	@View
	private Textbox tbxOtros_y_observaciones;
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
	private Radiogroup rdbRiesgo_bajo_alt;
	@View
	private Radiogroup rdbControl_prenatal;
	@View
	private Intbox ibxSem_gestacion;
	@View
	private Intbox ibxEdad_de_la_madre_al_nacer;
	@View
	private Radiogroup rdbParto_cst;
	@View
	private Radiogroup rdbUnico_multiple;
	@View
	private Radiogroup rdbIntitucional;
	@View
	private Listbox lbxHemoclasificacion;
	@View
	private Doublebox dbxTsh;
	@View
	private Listbox lbxVdlr;
	@View
	private Textbox tbxComplicaciones_embarazo_parto;
	@View
	private Doublebox dbxExamen_fisico_peso_al_nacer;
	@View
	private Doublebox dbxExamen_fisico_talla_al_nacer;
	@View
	private Doublebox dbxApgar_minutos;
	@View
	private Doublebox dbxApgar_5_minutos;
	@View
	private Radiogroup rdbReanimacion;
	@View
	private Radiogroup rdbHospitalizacion_neonatal;
	@View
	private Textbox tbxObservaciones1;
	@View
	private Textbox tbxPatologicos_medicos;
	@View
	private Textbox tbxPatologicos_quirurgicos;
	@View
	private Textbox tbxPatologicos_hospitalizaciones;
	@View
	private Textbox tbxPatologicos_medicacion;
	// ------------- Jose
	
	@View
	private Textbox tbxPatologicos_alergicos;
	
	//-------------------
		
	@View
	private Radiogroup rdbRecibido_hierro_ultimos_6meses;
	@View
	private Radiogroup rdbRecibido_albendazol_ultimos_6meses;
	@View
	private Radiogroup rdbRecibido_vitamina_a_ultimos_6meses;
	@View
	private Radiogroup rdbIntolerancia_alimentarias;
	@View
	private Textbox tbxObservaciones_hab_aliment;
	@View
	private Radiogroup rdbDolor_masticar;
	@View
	private Radiogroup rdbLimpieza_boca_mediodia;
	@View
	private Radiogroup rdbUtiliza_crema;
	@View
	private Radiogroup rdbDolor_diente;
	@View
	private Radiogroup rdbLiempieza_boca_noche;
	@View
	private Radiogroup rdbUtiliza_chupo_biberon;
	@View
	private Radiogroup rdbPadre_hnos_con_caries;
	@View
	private Radiogroup rdbLimpia_los_dientes;
	@View
	private Radiogroup rdbAsiste_a_odontologia;
	@View
	private Radiogroup rdbUtiliza_cepillo;
	@View
	private Radiogroup rdbBcg;
	@View
	private Radiogroup rdbVop_2;
	@View
	private Radiogroup rdbVop_3;
	@View
	private Radiogroup rdbNeumococo_3;
	@View
	private Radiogroup rdbHepatitis_b_rn;
	@View
	private Radiogroup rdbPenta_2;
	@View
	private Radiogroup rdbPenta_3;
	@View
	private Radiogroup rdbTriple_viral;
	@View
	private Radiogroup rdbVop_1;
	@View
	private Radiogroup rdbNeumococo_2;
	@View
	private Radiogroup rdbInfluenza_1;
	@View
	private Radiogroup rdbFiebre_amarilla;
	@View
	private Radiogroup rdbPenta_1;
	@View
	private Radiogroup rdbRotavirus_2;
	@View
	private Radiogroup rdbInfluenza_2;
	@View
	private Radiogroup rdbDpt_r1;
	@View
	private Radiogroup rdbNeumococo_1;
	@View
	private Radiogroup rdbRotavirus_1;
	@View
	private Radiogroup rdbDpt_2;
	@View
	private Radiogroup rdbVop2_2;
	@View
	private Radiogroup rdbTriple_viral_refuerzo;
	@View
	private Intbox ibxHermanos_vivos;
	@View
	private Intbox ibxHermanos_desnutridos;
	@View
	private Intbox ibxHermanos_muertos;
	@View
	private Textbox tbxCausa;
	@View
	private Radiogroup rdbSon_parientes_los_padre;
	@View
	private Radiogroup rdbFamiliar_con_problema;
	@View
	private Textbox tbxPaternos_medicos;
	@View
	private Textbox tbxPaternos_alergicos;
	@View
	private Doublebox dbxPaternos_talla;
	@View
	private Textbox tbxMaternos_medicos;
	@View
	private Textbox tbxMaternos_alergicos;
	@View
	private Doublebox dbxMaternos_talla;
	@View
	private Textbox tbxOtros;
	@View
	private Radiogroup rdbTestigo_relata_maltrato;
	@View
	private Radiogroup rdbComportamiento_anormal_de_padres;
	@View
	private Radiogroup rdbEmaciacion_visible;
	@View
	private Radiogroup rdbEdema_ambos_pies;
	@View
	private Radiogroup rdbTendencia_peso;
	@View
	private Radiogroup rdbLetargico;
	@View
	private Radiogroup rdbBebe_avidamente;
	@View
	private Radiogroup rdbTimpano_rojo_abombado;
	@View
	private Radiogroup rdbSomnoliento;
	@View
	private Radiogroup rdbOjos_hundios;
	@View
	private Radiogroup rdbSuparacion_oido;
	@View
	private Radiogroup rdbConfuso;
	@View
	private Radiogroup rdbSigno_pliegue_cutaneo;
	@View
	private Radiogroup rdbGnaglio_cuello_doloroso;
	@View
	private Radiogroup rdbAgitado_irritable;
	@View
	private Radiogroup rdbManifestaciones_sangrado;
	@View
	private Radiogroup rdbAmigdalas_etirematosas;
	@View
	private Radiogroup rdbRigidez_nuca;
	@View
	private Radiogroup rdbErupcion_cutanea_generalizada;
	@View
	private Radiogroup rdbExudado_amigdalas;
	@View
	private Radiogroup rdbTiraje_subcostal;
	@View
	private Radiogroup rdbPrueba_tornique_positiva;
	@View
	private Radiogroup rdbPalidez_palmar;
	@View
	private Radiogroup rdbTiraje_supraclavicular;
	@View
	private Radiogroup rdbHepatomegalia;
	@View
	private Radiogroup rdbPalidez_conjuntival;
	@View
	private Radiogroup rdbEstridor;
	@View
	private Radiogroup rdbPulso_rapido_fino;
	@View
	private Radiogroup rdbQuemaduras;
	@View
	private Radiogroup rdbSibilancia;
	@View
	private Radiogroup rdbLlenado_capilar_mayo2;
	@View
	private Radiogroup rdbLaceraciones;
	@View
	private Radiogroup rdbApnea;
	@View
	private Radiogroup rdbAscitis;
	@View
	private Radiogroup rdbMordiscos;
	@View
	private Radiogroup rdbBebe_mal_no_puede;
	@View
	private Radiogroup rdbTumefaccion_retroauricular;
	@View
	private Radiogroup rdbCicatrices;
	@View
	private Radiogroup rdbCabeza_cara;
	@View
	private Radiogroup rdbCuello;
	@View
	private Radiogroup rdbAbdomen;
	@View
	private Radiogroup rdbOrgano_de_sentidos;
	@View
	private Radiogroup rdbTorax_cardiopulmonar;
	@View
	private Radiogroup rdbMasa;
	@View
	private Radiogroup rdbFijacion_seguimiento;
	@View
	private Radiogroup rdbTirmo_cardiaco;
	@View
	private Radiogroup rdbMegalias;
	@View
	private Radiogroup rdbOclusion_alternante;
	@View
	private Radiogroup rdbSoplo;
	@View
	private Radiogroup rdbGenito_urinario;
	@View
	private Radiogroup rdbCover_uncover_test;
	@View
	private Radiogroup rdbColumna_vertebral;
	@View
	private Radiogroup rdbNeurologico_a_n;
	@View
	private Radiogroup rdbBoca;
	@View
	private Radiogroup rdbExtremidades;
	@View
	private Radiogroup rdbPiel_y_anexos;
	@View
	private Textbox tbxObservacioness;
	@View
	private Doublebox dbxT_e_de;
	@View
	private Doublebox dbxP_t_de;
	@View
	private Doublebox dbxImc_e_de;
	@View
	private Doublebox dbxPC_e_de;
	@View
	private Checkbox chbDesnutricion_sev_pes_bajo;
	@View
	private Checkbox chbObesidad_crecimiento;
	@View
	private Checkbox chbDesnutricion_crecimiento;
	@View
	private Checkbox chbSobrepeso_crecimiento;
	@View
	private Checkbox chbRiesgo_desnutricion_crecimiento;
	@View
	private Checkbox chbCrecimiento_adecuado_crecimiento;
	@View
	private Checkbox chbSosp_retras_desarrollo2;
	@View
	private Checkbox chbDesarrollo_normal_fac_riesgo22;
	@View
	private Checkbox chbRiesgo_problema_desarrollo2;
	@View
	private Checkbox chbDesarrollo_normal2;
	@View
	private Checkbox chbMaltrato_fisico;
	@View
	private Checkbox chbMaltrato_emocional;
	@View
	private Checkbox chbSospechas_abuso_sexual;
	@View
	private Checkbox chbNo_hay_sospechas_maltrato;
	@View
	private Textbox tbxAnalisis;
	@View
	private Checkbox chbEstimulo_factores_protectores;
	@View
	private Checkbox chbRecomendaciones_de_buentrato;
	@View
	private Checkbox chbOrientacion_vacunacion;
	@View
	private Checkbox chbRecomendaciones_nutricionales;
	@View
	private Checkbox chbImportancia_asistencia_controles;
	@View
	private Checkbox chbRecomendaciones_higienicas;
	@View
	private Checkbox chbRecomendaciones_pra_desarrollo;
	@View
	private Checkbox chbRecomendaciones_en_salud_oral;
	@View
	private Checkbox chbNecesita_prescripcion_vitamina_a;
	@View
	private Checkbox chbNecesita_prescripcion_albendazol;
	@View
	private Checkbox chbNecesita_prescripcion_hierro;
	@View
	private Checkbox chbNecesita_prescripcion_zinc;
	@View
	private Textbox tbxDescripciones_recomend_medi;
	@View
	private Radiogroup rdbLimpieza_boca_manana;
	@View
	private Radiogroup rdbEsta_descuidado_nino_en_salud;
	@View
	private Radiogroup rdbEsta_descuidado_el_nino_higiene;
	@View
	private Radiogroup rdbActitud_anormal_del_nino;
	@View
	private Radiogroup rdbSe_le_pega_al_nino;
	@View
	private Checkbox chbCa_Sosp_retras_desarrollo2;
	@View
	private Checkbox chbCa_Desarrollo_normal_fac_riesgo22;
	@View
	private Checkbox chbCa_Riesgo_problema_desarrollo2;
	@View
	private Checkbox chbCa_Desarrollo_normal2;
	@View
	private Checkbox chbCa_Sospechas_abuso_sexual;
	@View
	private Checkbox chbCa_No_hay_sospechas_maltrato;
	@View
	private Checkbox chbCa_Maltrato_fisico;
	@View
	private Checkbox chbCa_Maltrato_emocional;
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;
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
	private Doublebox dbxImc;
	@View
	private Label lblPerimetro_cefalico_obligatorio;
	@View
	private Row rowCita_anterior;
	@View
	private Row rowPerinatales;
	@View
	private Row rowPatologicos;
	@View
	private Row rowAntecedentes_familiares;
	@View
	private Row rowAnalisis;
	@View
	private Radiogroup rdbHepatitis_a;
	@View
	private Radiogroup rdbVop_r1;
	@View
	private Textbox tbxObservaciones_vacunales;

	@View
	private Toolbarbutton toolbarbuttonTipo_historia;
	private String tipo_historia;
	private Long codigo_historia_anterior;

	@View
	private Toolbarbutton btnCancelar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;

	@View
	private Button btnCalcularTallaEdad;
	@View
	private Button btnCalcularPesoTalla;
	@View
	private Button btnCalcularImcEdad;
	@View
	private Button btnCalcularPerimetroCefalicoEdad;
	@View
	private Button btnCalcularPcEdad;

	@View
	private Toolbarbutton btGuardar;

	private Paciente paciente;
	private His_atencion_crecimiento_menor2_5 hc_menor_2_5;
	private ESexo sexo;
	private Timestamp fecha;
	private Integer edad;

	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;

	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaPesoTalla;
	private RespuestaInt coordenadaIMCEdad;
	private RespuestaInt coordenadaPerimetroCefalicoEdad;

	private List coordenadasTE;
	private List coordenadasPT;
	private List coordenadasIE;
	private List coordenadasPCE;

	private String nro_ingreso_admision;
	private boolean cobrar_agudeza;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divModuloOrdenamiento;
	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	@View(isMacro = true)
	private EscalaAbreviadaDesarrollo macroEscalaDesarrollo;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "macroEscalaDesarrollo","divModuloRemisiones_externas" };


	@View
	private Toolbarbutton btnImprimir;

	/* Observaciones */

	@View private Textbox tbxObservaciones_cabeza;
	@View private Textbox tbxObservaciones_sentidos;
	@View private Textbox tbxObservaciones_cover;
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
	@View private Textbox tbxObservaciones_fijacion;
	@View private Textbox tbxObservaciones_oclusion;
	@View private Textbox tbxObservaciones_boca;
	
	@View private Row row1;
	@View private Row row2;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	@View private Row row6;
	
	@Override
	public void init() {
		try {
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {

			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);

			paciente = admision.getPaciente();
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			
			sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
					: ESexo.MASCULINO);
			fecha = paciente.getFecha_nacimiento();
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);

			Integer edadmes = Integer.valueOf(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(admision.getPaciente()
									.getFecha_nacimiento()), "2", false));

			edad = Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(admision.getPaciente()
					.getFecha_nacimiento()), "1", false));

			dbxExamen_fisico_perimetro_cflico.setDisabled(!(edad < 4));
			lblPerimetro_cefalico_obligatorio.setVisible(edad < 4);
			btnCalcularPcEdad.setDisabled(!(edad < 4));

			edad = Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(admision.getPaciente()
					.getFecha_nacimiento()), "1", false));

			dbxExamen_fisico_perimetro_cflico.setDisabled(!(edad < 4));
			lblPerimetro_cefalico_obligatorio.setVisible(edad < 4);
			btnCalcularPcEdad.setDisabled(!(edad < 4));

			macroEscalaDesarrollo.setAdmision(admision);

			if (edadmes >= 25 && edadmes <= 36) {
				macroEscalaDesarrollo.mostrarColorRandoEdad25_36();
				macroEscalaDesarrollo.mostrarColorRandoEdad25_36MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad25_36AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad25_36PS();
			} else if (edadmes >= 37 && edadmes <= 48) {
				macroEscalaDesarrollo.mostrarColorRandoEdad37_48();
				macroEscalaDesarrollo.mostrarColorRandoEdad37_48MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad37_48AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad37_48PS();
			} else if (edadmes >= 49 && edadmes <= 60) {
				macroEscalaDesarrollo.mostrarColorRandoEdad49_60();
				macroEscalaDesarrollo.mostrarColorRandoEdad49_60MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad49_60AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad49_60PS();
			} else if (edadmes >= 61 && edadmes <= 72) {
				macroEscalaDesarrollo.mostrarColorRandoEdad60_72();
				macroEscalaDesarrollo.mostrarColorRandoEdad60_72MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad60_72AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad60_72PS();
			}
		}
	}

	public void alarmaCrecimiento(Radio r) {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		r.isChecked();
		if (r.isChecked()) {
			String mensaje = "Remitir";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, r, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
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
		}
	}

	public void alarmaExamenFisicoTemperatura() {
		if (edad >= 2 && edad < 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_temp, 37.9,
					36.5, "Anormal", "Anormal", false);
		} else if (edad >= 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_temp, 37.1,
					36.4, "Anormal", "Anormal", false);
		}
	}

	public void alarmaExamenFisicoRespiracion() {
		if (edad >= 2 && edad < 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 26d, 24d,
					"Anormal", "Anormal", false);
		} else if (edad >= 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 26d, 19d,
					"Anormal", "Anormal", false);
		}
	}

	public void alarmaExamenFisicoFc() {
		//log.info(">>>>>>>>" + edad);
		if (edad == 2) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 121d, 99d,
					"Anormal", "Anormal", false);
		} else if (edad == 3) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 101d, 89d,
					"Anormal", "Anormal", false);
		} else if (edad >= 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 91d, 85d,
					"Anormal", "Anormal", false);
		}
	}

	public void listarCombos() {
		listarParameter();
		Utilidades
				.listarElementoListbox(lbxRelacion, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxHemoclasificacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxVdlr, true, getServiceLocator());
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

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
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
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		String mensaje = "Los campos marcados con (*) son obligatorios";

		boolean valida = true;
		Doublebox d = new Doublebox();
		d.setValue(1d);

		if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
			if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
				valida = macroAgudeza_visual.validarCamposObligatorios();
			}
		}

		infoPacientes.validarInformacionPaciente();
		if (edad < 4) {
			FormularioUtil.validarCamposObligatorios(tbxNom_acompanante,
					lbxRelacion, tbxMotivo_consulta, tbxPatologicos_medicos,
					tbxPatologicos_quirurgicos,tbxPatologicos_alergicos,
					tbxPatologicos_hospitalizaciones,
					tbxPatologicos_medicacion, dbxExamen_fisico_peso,
					dbxExamen_fisico_talla, dbxExamen_fisico_perimetro_cflico,
					dbxExamen_fisico_fc, dbxExamen_fisico_fr,
					dbxExamen_fisico_temp, tbxAnalisis,
					tbxDescripciones_recomend_medi);
		} else {
			FormularioUtil.validarCamposObligatorios(tbxNom_acompanante,
					lbxRelacion, tbxMotivo_consulta, tbxEnfermedad_actual,
					tbxPatologicos_medicos, tbxPatologicos_quirurgicos,
					tbxPatologicos_hospitalizaciones,tbxPatologicos_alergicos,
					tbxPatologicos_medicacion, dbxExamen_fisico_peso,
					dbxExamen_fisico_talla, dbxExamen_fisico_fc,
					dbxExamen_fisico_fr, dbxExamen_fisico_temp, tbxAnalisis,
					tbxDescripciones_recomend_medi);

		}

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
//			if (edad < 4
//					&& !(perimetroCefalicoValido(dbxExamen_fisico_perimetro_cflico
//							.getValue()))) {
//				valida = false;
//				mensaje = "El perimetro cefálico no se encuentra en el rango permitido!";
//				dbxExamen_fisico_perimetro_cflico.setFocus(true);
//			}
//		}

		if (valida)
			valida = receta_ripsAction.validarFormExterno();

		if (valida)
			valida = orden_servicioAction.validarFormExterno();

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

			if (lbxTipoHistoria.getSelectedIndex() != 0) {
				parameters.put("tipo_historia", lbxTipoHistoria
						.getSelectedItem().getValue());
			}

			parameters.put("limite_paginado","limit 25 offset 0");

			List<His_atencion_crecimiento_menor2_5> lista_datos = getServiceLocator()
					.getHis_atencion_crecimiento_menor2_5Service().listar(
							parameters);
			rowsResultado.getChildren().clear();

			for (His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						his_atencion_crecimiento_menor2_5, this));
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

		final His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(his_atencion_crecimiento_menor2_5
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(his_atencion_crecimiento_menor2_5
				.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				his_atencion_crecimiento_menor2_5.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(his_atencion_crecimiento_menor2_5
				.getTipo_historia().equals(
						IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
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
						mostrarDatos(his_atencion_crecimiento_menor2_5);
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
													eliminarDatos(his_atencion_crecimiento_menor2_5);
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

			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);

				hc_menor_2_5 = getBean();

				//log.info("accion ====> " + tbxAccion.getValue());

				HashMap<String, Object> datos = new HashMap<String, Object>();
				datos.put("accion", tbxAccion.getText());
				datos.put("historia_clinica", hc_menor_2_5);
				datos.put("admision", admision);
				datos.put("escala_del_desarrollo",
						macroEscalaDesarrollo.obtenerEscala());
				datos.put("accion", tbxAccion.getValue());
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());
				
				calcularCoordenadas(false);

				Boolean val = (tallaValida(hc_menor_2_5.getTalla())&&
				pesoValido(hc_menor_2_5.getPeso())&&
				(edad<4 && perimetroCefalicoValido(hc_menor_2_5.getPerimetro_cefalico())));
				
				if(val){
					// Coordenada (T/E)
					Coordenadas_graficas cg1 = new Coordenadas_graficas();
					cg1.setCodigo_empresa(codigo_empresa);
					cg1.setCodigo_historia(hc_menor_2_5.getCodigo_historia());
					cg1.setCodigo_sucursal(codigo_sucursal);
					cg1.setFecha_historia(hc_menor_2_5.getFecha_inicial());
					cg1.setTipo_coordenada(ITipos_coordenada.T_E);
					cg1.setIdentificacion(paciente.getNro_identificacion());
					cg1.setValor("" + coordenadaTallaEdad.getValor());
					cg1.setX("" + coordenadaTallaEdad.getX());
					cg1.setY("" + coordenadaTallaEdad.getY());
					cg1.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
	
					// Coordenada (P/T)
					Coordenadas_graficas cg2 = new Coordenadas_graficas();
					cg2.setCodigo_empresa(codigo_empresa);
					cg2.setCodigo_historia(hc_menor_2_5.getCodigo_historia());
					cg2.setCodigo_sucursal(codigo_sucursal);
					cg2.setFecha_historia(hc_menor_2_5.getFecha_inicial());
					cg2.setTipo_coordenada(ITipos_coordenada.P_T);
					cg2.setIdentificacion(paciente.getNro_identificacion());
					cg2.setValor("" + coordenadaPesoTalla.getValor());
					cg2.setX("" + coordenadaPesoTalla.getX());
					cg2.setY("" + coordenadaPesoTalla.getY());
					cg2.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
	
					// Coordenada (IMC/T)
					Coordenadas_graficas cg3 = new Coordenadas_graficas();
					cg3.setCodigo_empresa(codigo_empresa);
					cg3.setCodigo_historia(hc_menor_2_5.getCodigo_historia());
					cg3.setCodigo_sucursal(codigo_sucursal);
					cg3.setFecha_historia(hc_menor_2_5.getFecha_inicial());
					cg3.setTipo_coordenada(ITipos_coordenada.IMC_E);
					cg3.setIdentificacion(paciente.getNro_identificacion());
					cg3.setValor("" + coordenadaIMCEdad.getValor());
					cg3.setX("" + coordenadaIMCEdad.getX());
					cg3.setY("" + coordenadaIMCEdad.getY());
					cg3.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
	
					// Coordenada (PC/T)
					Coordenadas_graficas cg4 = new Coordenadas_graficas();
					if (edad < 4) {
						cg4.setCodigo_empresa(codigo_empresa);
						cg4.setCodigo_historia(hc_menor_2_5.getCodigo_historia());
						cg4.setCodigo_sucursal(codigo_sucursal);
						cg4.setFecha_historia(hc_menor_2_5.getFecha_inicial());
						cg4.setTipo_coordenada(ITipos_coordenada.PC_E);
						cg4.setIdentificacion(paciente.getNro_identificacion());
						cg4.setValor(""
								+ coordenadaPerimetroCefalicoEdad.getValor());
						cg4.setX("" + coordenadaPerimetroCefalicoEdad.getX());
						cg4.setY("" + coordenadaPerimetroCefalicoEdad.getY());
						cg4.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
					}
	
					ArrayList<Coordenadas_graficas> coordenadas = new ArrayList<Coordenadas_graficas>();
					coordenadas.add(cg1);
					coordenadas.add(cg2);
					coordenadas.add(cg3);
					if (edad < 4) {
						coordenadas.add(cg4);
					}
					datos.put("coordenadas", coordenadas);
				}



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

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);
				datos.put("cobrar_agudeza", cobrar_agudeza);

				Agudeza_visual agudeza_visual = macroAgudeza_visual
						.obtenerAgudezaVisual();
				agudeza_visual
						.setAnio(anio);
				datos.put("agudeza_visual", agudeza_visual);

				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction
						.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);
				datos.put("cita_seleccionada",cita);
				getServiceLocator()
						.getHis_atencion_crecimiento_menor2_5Service()
						.guardarDatos(datos);
				mostrarDatosAutorizacion(datos);
				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}

				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hc_menor_2_5
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

				MensajesUtil.mensajeInformacion("Informacion ...",
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

	private His_atencion_crecimiento_menor2_5 getBean() {
		His_atencion_crecimiento_menor2_5 hc_menor_2_5 = new His_atencion_crecimiento_menor2_5();

		hc_menor_2_5.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));

		hc_menor_2_5.setCodigo_empresa(empresa.getCodigo_empresa());
		hc_menor_2_5.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		hc_menor_2_5.setCodigo_historia(infoPacientes.getCodigo_historia());

		hc_menor_2_5.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));
		hc_menor_2_5.setUltimo_update(new Timestamp((new Date()).getTime()));
		hc_menor_2_5.setIdentificacion(admision != null ? admision
				.getNro_identificacion() : null);
		hc_menor_2_5.setSintomaticos_respiratorio(rdbSintomaticos_respiratorio
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setSintomaticos_piel(rdbSintomaticos_piel
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setNro_ingreso(admision != null ? admision
				.getNro_ingreso() : null);

		hc_menor_2_5.setNom_acompanante(tbxNom_acompanante.getValue());
		hc_menor_2_5.setParentesco(lbxRelacion.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setEdad_padre(ibxEdad_padre.getValue());
		hc_menor_2_5.setNom_padre(tbxNom_padre.getValue());
		hc_menor_2_5.setNom_madre(tbxNom_madre.getValue());
		hc_menor_2_5.setEdad_madre(ibxEdad_madre.getValue());
		hc_menor_2_5.setMotivo_consulta(tbxMotivo_consulta.getValue());
		hc_menor_2_5.setEnfermedad_actual(tbxEnfermedad_actual.getValue());
		hc_menor_2_5.setVomita_todo(rdbVomita_todo.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setDiarrea_ultimas(rdbDiarrea_ultimas.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setVive_zona_dengue(rdbVive_zona_dengue.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setConvulsiones(rdbConvulsiones.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setSangre_heces(rdbSangre_heces.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setVive_zona_malaria(rdbVive_zona_malaria
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTiene_tos_disnea(rdbTiene_tos_disnea.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setTiene_movito(rdbTiene_movito.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setTiene_otalgia(rdbTiene_otalgia.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setTiene_sibilancia(rdbTiene_sibilancia.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setVomito_ultimas_hora(rdbVomito_ultimas_hora
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTiene_supuracion_oido(rdbTiene_supuracion_oido
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setSibigilancias_previas(rdbSibigilancias_previas
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTiene_fibre_mayor5_dias(rdbTiene_fibre_mayor5_dias
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setEpisodios_previos_otitis(rdbEpisodios_previos_otitis
				.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setCuadro_gripal_ultimos_dias(rdbCuadro_gripal_ultimos_dias
						.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTiene_fiebre_menor5(rdbTiene_fiebre_menor5
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTien_dolor_gargan(rdbTien_dolor_gargan
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTien_diarrea(rdbTien_diarrea.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setFiebre_mayor38(rdbFiebre_mayor38.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setCefalea_dolor_retocular(rdbCefalea_dolor_retocular
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setDiarrea_ultimas_24horas(rdbDiarrea_ultimas_24horas
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setFiebre_mayor39(rdbFiebre_mayor39.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setMialgias_artralgias(rdbMialgias_artralgias
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setOtros_y_observaciones(tbxOtros_y_observaciones
				.getValue());
		hc_menor_2_5
				.setPerinatales_g((ibxPerinatales_g.getValue() != null ? ibxPerinatales_g
						.getValue() : 0));
		hc_menor_2_5
				.setPerinatales_p((ibxPerinatales_p.getValue() != null ? ibxPerinatales_p
						.getValue() : 0));
		hc_menor_2_5
				.setPerinatales_a((ibxPerinatales_a.getValue() != null ? ibxPerinatales_a
						.getValue() : 0));
		hc_menor_2_5
				.setPerinatales_c((ibxPerinatales_c.getValue() != null ? ibxPerinatales_c
						.getValue() : 0));
		hc_menor_2_5
				.setPerinatales_v((ibxPerinatales_v.getValue() != null ? ibxPerinatales_v
						.getValue() : 0));
		hc_menor_2_5.setRiesgo_bajo_alt(rdbRiesgo_bajo_alt.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setControl_prenatal(rdbControl_prenatal.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5
				.setSem_gestacion((ibxSem_gestacion.getValue() != null ? ibxSem_gestacion
						.getValue() : 0));
		hc_menor_2_5.setEdad_de_la_madre_al_nacer((ibxEdad_de_la_madre_al_nacer
				.getValue() != null ? ibxEdad_de_la_madre_al_nacer.getValue()
				: 0));
		hc_menor_2_5.setParto_cst(rdbParto_cst.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setUnico_multiple(rdbUnico_multiple.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setIntitucional(rdbIntitucional.getSelectedItem()
				.getValue().toString());

		hc_menor_2_5.setHemoclasificacion(lbxHemoclasificacion
				.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setTsh((dbxTsh.getValue() != null ? dbxTsh.getValue() : 0));
		hc_menor_2_5.setVdrl_materno(lbxVdlr.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5
				.setComplicaciones_embarazo_parto(tbxComplicaciones_embarazo_parto
						.getValue());
		hc_menor_2_5.setPeso_al_nacer((dbxExamen_fisico_peso_al_nacer
				.getValue() != null ? dbxExamen_fisico_peso_al_nacer.getValue()
				: 0));
		hc_menor_2_5.setTalla_al_nacer((dbxExamen_fisico_talla_al_nacer
				.getValue() != null ? dbxExamen_fisico_talla_al_nacer
				.getValue() : 0));
		hc_menor_2_5
				.setApgar_minutos((dbxApgar_minutos.getValue() != null ? dbxApgar_minutos
						.getValue() : 0d));
		hc_menor_2_5
				.setApgar_5_minutos((dbxApgar_5_minutos.getValue() != null ? dbxApgar_5_minutos
						.getValue() : 0d));
		hc_menor_2_5.setReanimacion(rdbReanimacion.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setHospitalizacion_neonatal(rdbHospitalizacion_neonatal
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setObservaciones(tbxObservaciones1.getValue());
		hc_menor_2_5.setPatologicos_medicos(tbxPatologicos_medicos.getValue());
		hc_menor_2_5.setPatologicos_quirurgicos(tbxPatologicos_quirurgicos
				.getValue());
		hc_menor_2_5
				.setPatologicos_hospitalizaciones(tbxPatologicos_hospitalizaciones
						.getValue());
		hc_menor_2_5.setPatologicos_medicacion(tbxPatologicos_medicacion
				.getValue());
		hc_menor_2_5.setAntecedentes_alergicos(tbxPatologicos_alergicos
				.getValue());	
		hc_menor_2_5
				.setRecibido_hierro_ultimos_6meses(rdbRecibido_hierro_ultimos_6meses
						.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setRecibido_albendazol_ultimos_6meses(rdbRecibido_albendazol_ultimos_6meses
						.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setRecibido_vitamina_a_ultimos_6meses(rdbRecibido_vitamina_a_ultimos_6meses
						.getSelectedItem().getValue().toString());
		hc_menor_2_5.setIntolerancia_alimentarias(rdbIntolerancia_alimentarias
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setObservaciones_hab_aliment(tbxObservaciones_hab_aliment
				.getValue());
		hc_menor_2_5.setDolor_masticar(rdbDolor_masticar.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setLimpieza_boca_mediodia(rdbLimpieza_boca_mediodia
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setUtiliza_crema(rdbUtiliza_crema.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setDolor_diente(rdbDolor_diente.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setLiempieza_boca_noche(rdbLiempieza_boca_noche
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setUtiliza_chupo_biberon(rdbUtiliza_chupo_biberon
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setPadre_hnos_con_caries(rdbPadre_hnos_con_caries
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setLimpia_los_dientes(rdbLimpia_los_dientes
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setAsiste_a_odontologia(rdbAsiste_a_odontologia
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setUtiliza_cepillo(rdbUtiliza_cepillo.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setBcg(rdbBcg.getSelectedItem().getValue().toString());
		hc_menor_2_5.setVop_2(rdbVop_2.getSelectedItem().getValue().toString());
		hc_menor_2_5.setVop_3(rdbVop_3.getSelectedItem().getValue().toString());
		hc_menor_2_5.setNeumococo_3(rdbNeumococo_3.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setVop_r1(rdbVop_r1.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setHepatitis_a(rdbHepatitis_a.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setHepatitis_b_rn(rdbHepatitis_b_rn.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setPenta_2(rdbPenta_2.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setPenta_3(rdbPenta_3.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setTriple_viral(rdbTriple_viral.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setVop_1(rdbVop_1.getSelectedItem().getValue().toString());
		hc_menor_2_5.setNeumococo_2(rdbNeumococo_2.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setInfluenza_1(rdbInfluenza_1.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setFiebre_amarilla(rdbFiebre_amarilla.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setPenta_1(rdbPenta_1.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setRotavirus_2(rdbRotavirus_2.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setInfluenza_2(rdbInfluenza_2.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setDpt_r1(rdbDpt_r1.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setNeumococo_1(rdbNeumococo_1.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setRotavirus_1(rdbRotavirus_1.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setDpt_2(rdbDpt_2.getSelectedItem().getValue().toString());
		hc_menor_2_5.setVop2_2(rdbVop2_2.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setTriple_viral_refuerzo(rdbTriple_viral_refuerzo
				.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setHermanos_vivos((ibxHermanos_vivos.getValue() != null ? ibxHermanos_vivos
						.getValue() : 0));
		hc_menor_2_5.setHermanos_desnutridos((ibxHermanos_desnutridos
				.getValue() != null ? ibxHermanos_desnutridos.getValue() : 0));
		hc_menor_2_5
				.setHermanos_muertos((ibxHermanos_muertos.getValue() != null ? ibxHermanos_muertos
						.getValue() : 0));
		hc_menor_2_5.setCausa(tbxCausa.getValue());
		hc_menor_2_5.setSon_parientes_los_padre(rdbSon_parientes_los_padre
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setFamiliar_con_problema(rdbFamiliar_con_problema
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setPaternos_medicos(tbxPaternos_medicos.getValue());
		hc_menor_2_5.setPaternos_alergicos(tbxPaternos_alergicos.getValue());
		hc_menor_2_5
				.setPaternos_talla((dbxPaternos_talla.getValue() != null ? dbxPaternos_talla
						.getValue() : 0));
		hc_menor_2_5.setMaternos_medicos(tbxMaternos_medicos.getValue());
		hc_menor_2_5.setMaternos_alergicos(tbxMaternos_alergicos.getValue());
		hc_menor_2_5
				.setMaternos_talla((dbxMaternos_talla.getValue() != null ? dbxMaternos_talla
						.getValue() : 0));
		hc_menor_2_5.setOtros(tbxOtros.getValue());
		hc_menor_2_5.setTestigo_relata_maltrato(rdbTestigo_relata_maltrato
				.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setComportamiento_anormal_de_padres(rdbComportamiento_anormal_de_padres
						.getSelectedItem().getValue().toString());
		hc_menor_2_5.setPeso(dbxExamen_fisico_peso.getValue());
		hc_menor_2_5
				.setTalla(dbxExamen_fisico_talla.getValue() != null ? dbxExamen_fisico_talla
						.getValue() : 0d);
		hc_menor_2_5.setPerimetro_cefalico(dbxExamen_fisico_perimetro_cflico
				.getValue() != null ? dbxExamen_fisico_perimetro_cflico
				.getValue() : 0d);
		hc_menor_2_5
				.setFrec_cardiaca(dbxExamen_fisico_fc.getValue() != null ? dbxExamen_fisico_fc
						.getValue() : 0d);
		hc_menor_2_5
				.setFrec_respiratoria(dbxExamen_fisico_fr.getValue() != null ? dbxExamen_fisico_fr
						.getValue() : 0d);
		hc_menor_2_5.setTemperatura(dbxExamen_fisico_temp.getValue());
		hc_menor_2_5.setImc(dbxImc.getValue());
		hc_menor_2_5.setEmaciacion_visible(rdbEmaciacion_visible
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setEdema_ambos_pies(rdbEdema_ambos_pies.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setTendencia_peso(rdbTendencia_peso.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setLetargico(rdbLetargico.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setBebe_avidamente(rdbBebe_avidamente.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setTimpano_rojo_abombado(rdbTimpano_rojo_abombado
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setSomnoliento(rdbSomnoliento.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setOjos_hundios(rdbOjos_hundios.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setSuparacion_oido(rdbSuparacion_oido.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setConfuso(rdbConfuso.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setSigno_pliegue_cutaneo(rdbSigno_pliegue_cutaneo
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setGnaglio_cuello_doloroso(rdbGnaglio_cuello_doloroso
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setAgitado_irritable(rdbAgitado_irritable
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setManifestaciones_sangrado(rdbManifestaciones_sangrado
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setAmigdalas_etirematosas(rdbAmigdalas_etirematosas
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setRigidez_nuca(rdbRigidez_nuca.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5
				.setErupcion_cutanea_generalizada(rdbErupcion_cutanea_generalizada
						.getSelectedItem().getValue().toString());
		hc_menor_2_5.setExudado_amigdalas(rdbExudado_amigdalas
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTiraje_subcostal(rdbTiraje_subcostal.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setPrueba_tornique_positiva(rdbPrueba_tornique_positiva
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setPalidez_palmar(rdbPalidez_palmar.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setTiraje_supraclavicular(rdbTiraje_supraclavicular
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setHepatomegalia(rdbHepatomegalia.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setPalidez_conjuntival(rdbPalidez_conjuntival
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setEstridor(rdbEstridor.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setPulso_rapido_fino(rdbPulso_rapido_fino
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setQuemaduras(rdbQuemaduras.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setSibilancia(rdbSibilancia.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setLlenado_capilar_mayo2(rdbLlenado_capilar_mayo2
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setLaceraciones(rdbLaceraciones.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setApnea(rdbApnea.getSelectedItem().getValue().toString());
		hc_menor_2_5.setAscitis(rdbAscitis.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setMordiscos(rdbMordiscos.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setBebe_mal_no_puede(rdbBebe_mal_no_puede
				.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setTumefaccion_retroauricular(rdbTumefaccion_retroauricular
						.getSelectedItem().getValue().toString());
		hc_menor_2_5.setCicatrices(rdbCicatrices.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setCabeza_cara(rdbCabeza_cara.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setCuello(rdbCuello.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setAbdomen(rdbAbdomen.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setOrgano_de_sentidos(rdbOrgano_de_sentidos
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTorax_cardiopulmonar(rdbTorax_cardiopulmonar
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setMasa(rdbMasa.getSelectedItem().getValue().toString());
		hc_menor_2_5.setFijacion_seguimiento(rdbFijacion_seguimiento
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setTirmo_cardiaco(rdbTirmo_cardiaco.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setMegalias(rdbMegalias.getSelectedItem().getValue()
				.toString());
		hc_menor_2_5.setOclusion_alternante(rdbOclusion_alternante
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setSoplo(rdbSoplo.getSelectedItem().getValue().toString());
		hc_menor_2_5.setGenito_urinario(rdbGenito_urinario.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setCover_uncover_test(rdbCover_uncover_test
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setColumna_vertebral(rdbColumna_vertebral
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setNeurologico_a_n(rdbNeurologico_a_n.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setBoca(rdbBoca.getSelectedItem().getValue().toString());
		hc_menor_2_5.setExtremidades(rdbExtremidades.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setPiel_y_anexos(rdbPiel_y_anexos.getSelectedItem()
				.getValue().toString());
		hc_menor_2_5.setObservacioness(tbxObservacioness.getValue());
		hc_menor_2_5.setDesnutricion_sev_pes_bajo(chbDesnutricion_sev_pes_bajo
				.isChecked());
		hc_menor_2_5.setObesidad_crecimiento(chbObesidad_crecimiento
				.isChecked());
		hc_menor_2_5.setDesnutricion_crecimiento(chbDesnutricion_crecimiento
				.isChecked());
		hc_menor_2_5.setSobrepeso_crecimiento(chbSobrepeso_crecimiento
				.isChecked());
		hc_menor_2_5
				.setRiesgo_desnutricion_crecimiento(chbRiesgo_desnutricion_crecimiento
						.isChecked());
		hc_menor_2_5
				.setCrecimiento_adecuado_crecimiento(chbCrecimiento_adecuado_crecimiento
						.isChecked());
		hc_menor_2_5.setSosp_retras_desarrollo2(chbSosp_retras_desarrollo2
				.isChecked());
		hc_menor_2_5
				.setDesarrollo_normal_fac_riesgo22(chbDesarrollo_normal_fac_riesgo22
						.isChecked());
		hc_menor_2_5
				.setRiesgo_problema_desarrollo2(chbRiesgo_problema_desarrollo2
						.isChecked());
		hc_menor_2_5.setDesarrollo_normal2(chbDesarrollo_normal2.isChecked());
		hc_menor_2_5.setMaltrato_fisico(chbMaltrato_fisico.isChecked());
		hc_menor_2_5.setMaltrato_emocional(chbMaltrato_emocional.isChecked());
		hc_menor_2_5.setSospechas_abuso_sexual(chbSospechas_abuso_sexual
				.isChecked());
		hc_menor_2_5.setNo_hay_sospechas_maltrato(chbNo_hay_sospechas_maltrato
				.isChecked());
		hc_menor_2_5.setAnalisis(tbxAnalisis.getValue());
		hc_menor_2_5
				.setEstimulo_factores_protectores(chbEstimulo_factores_protectores
						.isChecked());
		hc_menor_2_5
				.setRecomendaciones_de_buentrato(chbRecomendaciones_de_buentrato
						.isChecked());
		hc_menor_2_5.setOrientacion_vacunacion(chbOrientacion_vacunacion
				.isChecked());
		hc_menor_2_5
				.setRecomendaciones_nutricionales(chbRecomendaciones_nutricionales
						.isChecked());
		hc_menor_2_5
				.setImportancia_asistencia_controles(chbImportancia_asistencia_controles
						.isChecked());
		hc_menor_2_5
				.setRecomendaciones_higienicas(chbRecomendaciones_higienicas
						.isChecked());
		hc_menor_2_5
				.setRecomendaciones_pra_desarrollo(chbRecomendaciones_pra_desarrollo
						.isChecked());
		hc_menor_2_5
				.setRecomendaciones_en_salud_oral(chbRecomendaciones_en_salud_oral
						.isChecked());
		hc_menor_2_5
				.setNecesita_prescripcion_vitamina_a(chbNecesita_prescripcion_vitamina_a
						.isChecked());
		hc_menor_2_5
				.setNecesita_prescripcion_albendazol(chbNecesita_prescripcion_albendazol
						.isChecked());
		hc_menor_2_5
				.setNecesita_prescripcion_hierro(chbNecesita_prescripcion_hierro
						.isChecked());
		hc_menor_2_5
				.setNecesita_prescripcion_zinc(chbNecesita_prescripcion_zinc
						.isChecked());
		hc_menor_2_5
				.setDescripciones_recomend_medi(tbxDescripciones_recomend_medi
						.getValue());
		hc_menor_2_5.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		hc_menor_2_5.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		hc_menor_2_5.setCreacion_user(usuarios.getCodigo().toString());
		hc_menor_2_5.setDelete_date(null);
		hc_menor_2_5.setUltimo_user(usuarios.getCodigo().toString());
		hc_menor_2_5.setDelete_user(null);
		hc_menor_2_5.setLimpieza_boca_manana(rdbLimpieza_boca_manana
				.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setEsta_descuidado_nino_en_salud(rdbEsta_descuidado_nino_en_salud
						.getSelectedItem().getValue().toString());
		hc_menor_2_5
				.setEsta_descuidado_el_nino_higiene(rdbEsta_descuidado_el_nino_higiene
						.getSelectedItem().getValue().toString());
		hc_menor_2_5.setActitud_anormal_del_nino(rdbActitud_anormal_del_nino
				.getSelectedItem().getValue().toString());
		hc_menor_2_5.setSe_le_pega_al_nino(rdbSe_le_pega_al_nino
				.getSelectedItem().getValue().toString());

		hc_menor_2_5.setObservaciones_vacunales(tbxObservaciones_vacunales
				.getValue());

		hc_menor_2_5.setTipo_historia(tipo_historia);
		hc_menor_2_5.setCodigo_historia_anterior(codigo_historia_anterior);
		


		hc_menor_2_5.setObservaciones_cabeza(tbxObservaciones_cabeza.getValue());
		hc_menor_2_5.setObservaciones_sentidos(tbxObservaciones_sentidos.getValue());
		hc_menor_2_5.setObservaciones_cover(tbxObservaciones_cover.getValue());
		hc_menor_2_5.setObservaciones_cuello(tbxObservaciones_cuello.getValue());
		hc_menor_2_5.setObservaciones_torax(tbxObservaciones_torax.getValue());
		hc_menor_2_5.setObservaciones_cardiaco(tbxObservaciones_cardiaco.getValue());
		hc_menor_2_5.setObservaciones_soplo(tbxObservaciones_soplo.getValue());
		hc_menor_2_5.setObservaciones_abdomen(tbxObservaciones_abdomen.getValue());
		hc_menor_2_5.setObservaciones_masas(tbxObservaciones_masas.getValue());
		hc_menor_2_5.setObservaciones_megalias(tbxObservaciones_megalias.getValue());
		hc_menor_2_5.setObservaciones_genito(tbxObservaciones_genito.getValue());
		hc_menor_2_5.setObservaciones_columna(tbxObservaciones_columna.getValue());
		hc_menor_2_5.setObservaciones_extremidades(tbxObservaciones_extremidades.getValue());
		hc_menor_2_5.setObservaciones_piel(tbxObservaciones_piel.getValue());
		hc_menor_2_5.setObservaciones_neurologico(tbxObservaciones_neurologico.getValue());
		hc_menor_2_5.setObservaciones_fijacion(tbxObservaciones_fijacion.getValue());
		hc_menor_2_5.setObservaciones_oclusion(tbxObservaciones_oclusion.getValue());
		hc_menor_2_5.setObservaciones_boca(tbxObservaciones_boca.getValue());
		
		return hc_menor_2_5;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) obj;
		try {
			this.nro_ingreso_admision = his_atencion_crecimiento_menor2_5
					.getNro_ingreso();
			infoPacientes.setCodigo_historia(his_atencion_crecimiento_menor2_5
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(his_atencion_crecimiento_menor2_5
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					his_atencion_crecimiento_menor2_5.getUltimo_update());

			onMostrarModuloRemisiones();
			initMostrar_datos(his_atencion_crecimiento_menor2_5);
			cargarRemisionInterna(his_atencion_crecimiento_menor2_5);
			cargarAgudezaVisual(his_atencion_crecimiento_menor2_5);
			cargarInformacion_paciente();

			tbxNom_acompanante.setValue(his_atencion_crecimiento_menor2_5
					.getNom_acompanante());

			Utilidades.seleccionarListitem(lbxRelacion,
					his_atencion_crecimiento_menor2_5.getParentesco());

			ibxEdad_padre.setValue(his_atencion_crecimiento_menor2_5
					.getEdad_padre());
			tbxNom_padre.setValue(his_atencion_crecimiento_menor2_5
					.getNom_padre());
			tbxNom_madre.setValue(his_atencion_crecimiento_menor2_5
					.getNom_madre());
			ibxEdad_madre.setValue(his_atencion_crecimiento_menor2_5
					.getEdad_madre());
			tbxMotivo_consulta.setValue(his_atencion_crecimiento_menor2_5
					.getMotivo_consulta());
			tbxEnfermedad_actual.setValue(his_atencion_crecimiento_menor2_5
					.getEnfermedad_actual());
			Utilidades.seleccionarRadio(rdbVomita_todo,
					his_atencion_crecimiento_menor2_5.getVomita_todo());
			Utilidades.seleccionarRadio(rdbDiarrea_ultimas,
					his_atencion_crecimiento_menor2_5.getDiarrea_ultimas());
			Utilidades.seleccionarRadio(rdbVive_zona_dengue,
					his_atencion_crecimiento_menor2_5.getVive_zona_dengue());
			Utilidades.seleccionarRadio(rdbConvulsiones,
					his_atencion_crecimiento_menor2_5.getConvulsiones());
			Utilidades.seleccionarRadio(rdbSangre_heces,
					his_atencion_crecimiento_menor2_5.getSangre_heces());
			Utilidades.seleccionarRadio(rdbVive_zona_malaria,
					his_atencion_crecimiento_menor2_5.getVive_zona_malaria());
			Utilidades.seleccionarRadio(rdbTiene_tos_disnea,
					his_atencion_crecimiento_menor2_5.getTiene_tos_disnea());
			Utilidades.seleccionarRadio(rdbTiene_movito,
					his_atencion_crecimiento_menor2_5.getTiene_movito());
			Utilidades.seleccionarRadio(rdbTiene_otalgia,
					his_atencion_crecimiento_menor2_5.getTiene_otalgia());
			Utilidades.seleccionarRadio(rdbTiene_sibilancia,
					his_atencion_crecimiento_menor2_5.getTiene_sibilancia());
			Utilidades.seleccionarRadio(rdbVomito_ultimas_hora,
					his_atencion_crecimiento_menor2_5.getVomito_ultimas_hora());
			Utilidades.seleccionarRadio(rdbTiene_supuracion_oido,
					his_atencion_crecimiento_menor2_5
							.getTiene_supuracion_oido());
			Utilidades.seleccionarRadio(rdbSibigilancias_previas,
					his_atencion_crecimiento_menor2_5
							.getSibigilancias_previas());
			Utilidades.seleccionarRadio(rdbTiene_fibre_mayor5_dias,
					his_atencion_crecimiento_menor2_5
							.getTiene_fibre_mayor5_dias());
			Utilidades.seleccionarRadio(rdbEpisodios_previos_otitis,
					his_atencion_crecimiento_menor2_5
							.getEpisodios_previos_otitis());
			Utilidades.seleccionarRadio(rdbCuadro_gripal_ultimos_dias,
					his_atencion_crecimiento_menor2_5
							.getCuadro_gripal_ultimos_dias());
			Utilidades.seleccionarRadio(rdbTiene_fiebre_menor5,
					his_atencion_crecimiento_menor2_5.getTiene_fiebre_menor5());
			Utilidades.seleccionarRadio(rdbTien_dolor_gargan,
					his_atencion_crecimiento_menor2_5.getTien_dolor_gargan());
			Utilidades.seleccionarRadio(rdbTien_diarrea,
					his_atencion_crecimiento_menor2_5.getTien_diarrea());
			Utilidades.seleccionarRadio(rdbFiebre_mayor38,
					his_atencion_crecimiento_menor2_5.getFiebre_mayor38());
			Utilidades.seleccionarRadio(rdbCefalea_dolor_retocular,
					his_atencion_crecimiento_menor2_5
							.getCefalea_dolor_retocular());
			Utilidades.seleccionarRadio(rdbDiarrea_ultimas_24horas,
					his_atencion_crecimiento_menor2_5
							.getDiarrea_ultimas_24horas());
			Utilidades.seleccionarRadio(rdbFiebre_mayor39,
					his_atencion_crecimiento_menor2_5.getFiebre_mayor39());
			Utilidades.seleccionarRadio(rdbMialgias_artralgias,
					his_atencion_crecimiento_menor2_5.getMialgias_artralgias());
			tbxOtros_y_observaciones.setValue(his_atencion_crecimiento_menor2_5
					.getOtros_y_observaciones());
			ibxPerinatales_g.setValue(his_atencion_crecimiento_menor2_5
					.getPerinatales_g());
			ibxPerinatales_p.setValue(his_atencion_crecimiento_menor2_5
					.getPerinatales_p());
			ibxPerinatales_a.setValue(his_atencion_crecimiento_menor2_5
					.getPerinatales_a());
			ibxPerinatales_c.setValue(his_atencion_crecimiento_menor2_5
					.getPerinatales_c());
			ibxPerinatales_v.setValue(his_atencion_crecimiento_menor2_5
					.getPerinatales_v());
			Utilidades.seleccionarRadio(rdbRiesgo_bajo_alt,
					his_atencion_crecimiento_menor2_5.getRiesgo_bajo_alt());
			Utilidades.seleccionarRadio(rdbControl_prenatal,
					his_atencion_crecimiento_menor2_5.getControl_prenatal());
			ibxSem_gestacion.setValue(his_atencion_crecimiento_menor2_5
					.getSem_gestacion());
			ibxEdad_de_la_madre_al_nacer
					.setValue(his_atencion_crecimiento_menor2_5
							.getEdad_de_la_madre_al_nacer());
			Utilidades.seleccionarRadio(rdbParto_cst,
					his_atencion_crecimiento_menor2_5.getParto_cst());
			Utilidades.seleccionarRadio(rdbUnico_multiple,
					his_atencion_crecimiento_menor2_5.getUnico_multiple());
			Utilidades.seleccionarRadio(rdbIntitucional,
					his_atencion_crecimiento_menor2_5.getIntitucional());
			Utilidades.seleccionarListitem(lbxHemoclasificacion,
					his_atencion_crecimiento_menor2_5.getHemoclasificacion());
			dbxTsh.setValue(his_atencion_crecimiento_menor2_5.getTsh());
			Utilidades.seleccionarListitem(lbxVdlr,
					his_atencion_crecimiento_menor2_5.getVdrl_materno());
			tbxComplicaciones_embarazo_parto
					.setValue(his_atencion_crecimiento_menor2_5
							.getComplicaciones_embarazo_parto());
			dbxExamen_fisico_peso_al_nacer
					.setValue(his_atencion_crecimiento_menor2_5
							.getPeso_al_nacer());
			dbxExamen_fisico_talla_al_nacer
					.setValue(his_atencion_crecimiento_menor2_5
							.getTalla_al_nacer());
			dbxApgar_minutos.setValue(his_atencion_crecimiento_menor2_5
					.getApgar_minutos());
			dbxApgar_5_minutos.setValue(his_atencion_crecimiento_menor2_5
					.getApgar_5_minutos());
			Utilidades.seleccionarRadio(rdbReanimacion,
					his_atencion_crecimiento_menor2_5.getReanimacion());
			Utilidades.seleccionarRadio(rdbHospitalizacion_neonatal,
					his_atencion_crecimiento_menor2_5
							.getHospitalizacion_neonatal());
			tbxObservaciones1.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones());
			tbxPatologicos_medicos.setValue(his_atencion_crecimiento_menor2_5
					.getPatologicos_medicos());
			tbxPatologicos_quirurgicos
					.setValue(his_atencion_crecimiento_menor2_5
							.getPatologicos_quirurgicos());
			tbxPatologicos_hospitalizaciones
					.setValue(his_atencion_crecimiento_menor2_5
							.getPatologicos_hospitalizaciones());
			tbxPatologicos_medicacion
					.setValue(his_atencion_crecimiento_menor2_5
							.getPatologicos_medicacion());
			tbxPatologicos_alergicos.setValue(his_atencion_crecimiento_menor2_5
					.getAntecedentes_alergicos());
			
			Utilidades.seleccionarRadio(rdbRecibido_hierro_ultimos_6meses,
					his_atencion_crecimiento_menor2_5
							.getRecibido_hierro_ultimos_6meses());
			Utilidades.seleccionarRadio(rdbRecibido_albendazol_ultimos_6meses,
					his_atencion_crecimiento_menor2_5
							.getRecibido_albendazol_ultimos_6meses());
			Utilidades.seleccionarRadio(rdbRecibido_vitamina_a_ultimos_6meses,
					his_atencion_crecimiento_menor2_5
							.getRecibido_vitamina_a_ultimos_6meses());
			Utilidades.seleccionarRadio(rdbIntolerancia_alimentarias,
					his_atencion_crecimiento_menor2_5
							.getIntolerancia_alimentarias());
			tbxObservaciones_hab_aliment
					.setValue(his_atencion_crecimiento_menor2_5
							.getObservaciones_hab_aliment());
			Utilidades.seleccionarRadio(rdbDolor_masticar,
					his_atencion_crecimiento_menor2_5.getDolor_masticar());
			Utilidades.seleccionarRadio(rdbLimpieza_boca_mediodia,
					his_atencion_crecimiento_menor2_5
							.getLimpieza_boca_mediodia());
			Utilidades.seleccionarRadio(rdbUtiliza_crema,
					his_atencion_crecimiento_menor2_5.getUtiliza_crema());
			Utilidades.seleccionarRadio(rdbDolor_diente,
					his_atencion_crecimiento_menor2_5.getDolor_diente());
			Utilidades
					.seleccionarRadio(rdbLiempieza_boca_noche,
							his_atencion_crecimiento_menor2_5
									.getLiempieza_boca_noche());
			Utilidades.seleccionarRadio(rdbUtiliza_chupo_biberon,
					his_atencion_crecimiento_menor2_5
							.getUtiliza_chupo_biberon());
			Utilidades.seleccionarRadio(rdbPadre_hnos_con_caries,
					his_atencion_crecimiento_menor2_5
							.getPadre_hnos_con_caries());
			Utilidades.seleccionarRadio(rdbLimpia_los_dientes,
					his_atencion_crecimiento_menor2_5.getLimpia_los_dientes());
			Utilidades
					.seleccionarRadio(rdbAsiste_a_odontologia,
							his_atencion_crecimiento_menor2_5
									.getAsiste_a_odontologia());
			Utilidades.seleccionarRadio(rdbUtiliza_cepillo,
					his_atencion_crecimiento_menor2_5.getUtiliza_cepillo());
			Utilidades.seleccionarRadio(rdbBcg,
					his_atencion_crecimiento_menor2_5.getBcg());
			Utilidades.seleccionarRadio(rdbVop_r1,
					his_atencion_crecimiento_menor2_5.getVop_r1());
			Utilidades.seleccionarRadio(rdbVop_2,
					his_atencion_crecimiento_menor2_5.getVop_2());
			Utilidades.seleccionarRadio(rdbVop_3,
					his_atencion_crecimiento_menor2_5.getVop_3());
			Utilidades.seleccionarRadio(rdbNeumococo_3,
					his_atencion_crecimiento_menor2_5.getNeumococo_3());
			Utilidades.seleccionarRadio(rdbHepatitis_a,
					his_atencion_crecimiento_menor2_5.getHepatitis_a());
			Utilidades.seleccionarRadio(rdbHepatitis_b_rn,
					his_atencion_crecimiento_menor2_5.getHepatitis_b_rn());
			Utilidades.seleccionarRadio(rdbPenta_2,
					his_atencion_crecimiento_menor2_5.getPenta_2());
			Utilidades.seleccionarRadio(rdbPenta_3,
					his_atencion_crecimiento_menor2_5.getPenta_3());
			Utilidades.seleccionarRadio(rdbTriple_viral,
					his_atencion_crecimiento_menor2_5.getTriple_viral());
			Utilidades.seleccionarRadio(rdbVop_1,
					his_atencion_crecimiento_menor2_5.getVop_1());
			Utilidades.seleccionarRadio(rdbNeumococo_2,
					his_atencion_crecimiento_menor2_5.getNeumococo_2());
			Utilidades.seleccionarRadio(rdbInfluenza_1,
					his_atencion_crecimiento_menor2_5.getInfluenza_1());
			Utilidades.seleccionarRadio(rdbFiebre_amarilla,
					his_atencion_crecimiento_menor2_5.getFiebre_amarilla());
			Utilidades.seleccionarRadio(rdbPenta_1,
					his_atencion_crecimiento_menor2_5.getPenta_1());
			Utilidades.seleccionarRadio(rdbRotavirus_2,
					his_atencion_crecimiento_menor2_5.getRotavirus_2());
			Utilidades.seleccionarRadio(rdbInfluenza_2,
					his_atencion_crecimiento_menor2_5.getInfluenza_2());
			Utilidades.seleccionarRadio(rdbDpt_r1,
					his_atencion_crecimiento_menor2_5.getDpt_r1());
			Utilidades.seleccionarRadio(rdbNeumococo_1,
					his_atencion_crecimiento_menor2_5.getNeumococo_1());
			Utilidades.seleccionarRadio(rdbRotavirus_1,
					his_atencion_crecimiento_menor2_5.getRotavirus_1());
			Utilidades.seleccionarRadio(rdbDpt_2,
					his_atencion_crecimiento_menor2_5.getDpt_2());
			Utilidades.seleccionarRadio(rdbVop2_2,
					his_atencion_crecimiento_menor2_5.getVop2_2());
			Utilidades.seleccionarRadio(rdbTriple_viral_refuerzo,
					his_atencion_crecimiento_menor2_5
							.getTriple_viral_refuerzo());
			ibxHermanos_vivos.setValue(his_atencion_crecimiento_menor2_5
					.getHermanos_vivos());
			ibxHermanos_desnutridos.setValue(his_atencion_crecimiento_menor2_5
					.getHermanos_desnutridos());
			ibxHermanos_muertos.setValue(his_atencion_crecimiento_menor2_5
					.getHermanos_muertos());
			tbxCausa.setValue(his_atencion_crecimiento_menor2_5.getCausa());
			Utilidades.seleccionarRadio(rdbSon_parientes_los_padre,
					his_atencion_crecimiento_menor2_5
							.getSon_parientes_los_padre());
			Utilidades.seleccionarRadio(rdbFamiliar_con_problema,
					his_atencion_crecimiento_menor2_5
							.getFamiliar_con_problema());
			tbxPaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
					.getPaternos_medicos());
			tbxPaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
					.getPaternos_alergicos());
			dbxPaternos_talla.setValue(his_atencion_crecimiento_menor2_5
					.getPaternos_talla());
			tbxMaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
					.getMaternos_medicos());
			tbxMaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
					.getMaternos_alergicos());
			dbxMaternos_talla.setValue(his_atencion_crecimiento_menor2_5
					.getMaternos_talla());
			tbxOtros.setValue(his_atencion_crecimiento_menor2_5.getOtros());
			Utilidades.seleccionarRadio(rdbTestigo_relata_maltrato,
					his_atencion_crecimiento_menor2_5
							.getTestigo_relata_maltrato());
			Utilidades.seleccionarRadio(rdbComportamiento_anormal_de_padres,
					his_atencion_crecimiento_menor2_5
							.getComportamiento_anormal_de_padres());
			dbxExamen_fisico_peso.setValue(his_atencion_crecimiento_menor2_5
					.getPeso());
			dbxExamen_fisico_talla.setValue(his_atencion_crecimiento_menor2_5
					.getTalla());
			dbxExamen_fisico_perimetro_cflico
					.setValue(his_atencion_crecimiento_menor2_5
							.getPerimetro_cefalico());
			dbxExamen_fisico_fc.setValue(his_atencion_crecimiento_menor2_5
					.getFrec_cardiaca());
			dbxExamen_fisico_fr.setValue(his_atencion_crecimiento_menor2_5
					.getFrec_respiratoria());
			dbxExamen_fisico_temp.setValue(his_atencion_crecimiento_menor2_5
					.getTemperatura());
			dbxImc.setValue(his_atencion_crecimiento_menor2_5.getImc());
			Utilidades.seleccionarRadio(rdbEmaciacion_visible,
					his_atencion_crecimiento_menor2_5.getEmaciacion_visible());
			Utilidades.seleccionarRadio(rdbEdema_ambos_pies,
					his_atencion_crecimiento_menor2_5.getEdema_ambos_pies());
			Utilidades.seleccionarRadio(rdbTendencia_peso,
					his_atencion_crecimiento_menor2_5.getTendencia_peso());
			Utilidades.seleccionarRadio(rdbLetargico,
					his_atencion_crecimiento_menor2_5.getLetargico());
			Utilidades.seleccionarRadio(rdbBebe_avidamente,
					his_atencion_crecimiento_menor2_5.getBebe_avidamente());
			Utilidades.seleccionarRadio(rdbTimpano_rojo_abombado,
					his_atencion_crecimiento_menor2_5
							.getTimpano_rojo_abombado());
			Utilidades.seleccionarRadio(rdbSomnoliento,
					his_atencion_crecimiento_menor2_5.getSomnoliento());
			Utilidades.seleccionarRadio(rdbOjos_hundios,
					his_atencion_crecimiento_menor2_5.getOjos_hundios());
			Utilidades.seleccionarRadio(rdbSuparacion_oido,
					his_atencion_crecimiento_menor2_5.getSuparacion_oido());
			Utilidades.seleccionarRadio(rdbConfuso,
					his_atencion_crecimiento_menor2_5.getConfuso());
			Utilidades.seleccionarRadio(rdbSigno_pliegue_cutaneo,
					his_atencion_crecimiento_menor2_5
							.getSigno_pliegue_cutaneo());
			Utilidades.seleccionarRadio(rdbGnaglio_cuello_doloroso,
					his_atencion_crecimiento_menor2_5
							.getGnaglio_cuello_doloroso());
			Utilidades.seleccionarRadio(rdbAgitado_irritable,
					his_atencion_crecimiento_menor2_5.getAgitado_irritable());
			Utilidades.seleccionarRadio(rdbManifestaciones_sangrado,
					his_atencion_crecimiento_menor2_5
							.getManifestaciones_sangrado());
			Utilidades.seleccionarRadio(rdbAmigdalas_etirematosas,
					his_atencion_crecimiento_menor2_5
							.getAmigdalas_etirematosas());
			Utilidades.seleccionarRadio(rdbRigidez_nuca,
					his_atencion_crecimiento_menor2_5.getRigidez_nuca());
			Utilidades.seleccionarRadio(rdbErupcion_cutanea_generalizada,
					his_atencion_crecimiento_menor2_5
							.getErupcion_cutanea_generalizada());
			Utilidades.seleccionarRadio(rdbExudado_amigdalas,
					his_atencion_crecimiento_menor2_5.getExudado_amigdalas());
			Utilidades.seleccionarRadio(rdbTiraje_subcostal,
					his_atencion_crecimiento_menor2_5.getTiraje_subcostal());
			Utilidades.seleccionarRadio(rdbPrueba_tornique_positiva,
					his_atencion_crecimiento_menor2_5
							.getPrueba_tornique_positiva());
			Utilidades.seleccionarRadio(rdbPalidez_palmar,
					his_atencion_crecimiento_menor2_5.getPalidez_palmar());
			Utilidades.seleccionarRadio(rdbTiraje_supraclavicular,
					his_atencion_crecimiento_menor2_5
							.getTiraje_supraclavicular());
			Utilidades.seleccionarRadio(rdbHepatomegalia,
					his_atencion_crecimiento_menor2_5.getHepatomegalia());
			Utilidades.seleccionarRadio(rdbPalidez_conjuntival,
					his_atencion_crecimiento_menor2_5.getPalidez_conjuntival());
			Utilidades.seleccionarRadio(rdbEstridor,
					his_atencion_crecimiento_menor2_5.getEstridor());
			Utilidades.seleccionarRadio(rdbPulso_rapido_fino,
					his_atencion_crecimiento_menor2_5.getPulso_rapido_fino());
			Utilidades.seleccionarRadio(rdbQuemaduras,
					his_atencion_crecimiento_menor2_5.getQuemaduras());
			Utilidades.seleccionarRadio(rdbSibilancia,
					his_atencion_crecimiento_menor2_5.getSibilancia());
			Utilidades.seleccionarRadio(rdbLlenado_capilar_mayo2,
					his_atencion_crecimiento_menor2_5
							.getLlenado_capilar_mayo2());
			Utilidades.seleccionarRadio(rdbLaceraciones,
					his_atencion_crecimiento_menor2_5.getLaceraciones());
			Utilidades.seleccionarRadio(rdbApnea,
					his_atencion_crecimiento_menor2_5.getApnea());
			Utilidades.seleccionarRadio(rdbAscitis,
					his_atencion_crecimiento_menor2_5.getAscitis());
			Utilidades.seleccionarRadio(rdbMordiscos,
					his_atencion_crecimiento_menor2_5.getMordiscos());
			Utilidades.seleccionarRadio(rdbBebe_mal_no_puede,
					his_atencion_crecimiento_menor2_5.getBebe_mal_no_puede());
			Utilidades.seleccionarRadio(rdbTumefaccion_retroauricular,
					his_atencion_crecimiento_menor2_5
							.getTumefaccion_retroauricular());
			Utilidades.seleccionarRadio(rdbCicatrices,
					his_atencion_crecimiento_menor2_5.getCicatrices());
			Utilidades.seleccionarRadio(rdbCabeza_cara,
					his_atencion_crecimiento_menor2_5.getCabeza_cara());
			Utilidades.seleccionarRadio(rdbCuello,
					his_atencion_crecimiento_menor2_5.getCuello());
			Utilidades.seleccionarRadio(rdbAbdomen,
					his_atencion_crecimiento_menor2_5.getAbdomen());
			Utilidades.seleccionarRadio(rdbOrgano_de_sentidos,
					his_atencion_crecimiento_menor2_5.getOrgano_de_sentidos());
			Utilidades
					.seleccionarRadio(rdbTorax_cardiopulmonar,
							his_atencion_crecimiento_menor2_5
									.getTorax_cardiopulmonar());
			Utilidades.seleccionarRadio(rdbMasa,
					his_atencion_crecimiento_menor2_5.getMasa());
			Utilidades
					.seleccionarRadio(rdbFijacion_seguimiento,
							his_atencion_crecimiento_menor2_5
									.getFijacion_seguimiento());
			Utilidades.seleccionarRadio(rdbTirmo_cardiaco,
					his_atencion_crecimiento_menor2_5.getTirmo_cardiaco());
			Utilidades.seleccionarRadio(rdbMegalias,
					his_atencion_crecimiento_menor2_5.getMegalias());
			Utilidades.seleccionarRadio(rdbOclusion_alternante,
					his_atencion_crecimiento_menor2_5.getOclusion_alternante());
			Utilidades.seleccionarRadio(rdbSoplo,
					his_atencion_crecimiento_menor2_5.getSoplo());
			Utilidades.seleccionarRadio(rdbGenito_urinario,
					his_atencion_crecimiento_menor2_5.getGenito_urinario());
			Utilidades.seleccionarRadio(rdbCover_uncover_test,
					his_atencion_crecimiento_menor2_5.getCover_uncover_test());
			Utilidades.seleccionarRadio(rdbColumna_vertebral,
					his_atencion_crecimiento_menor2_5.getColumna_vertebral());
			Utilidades.seleccionarRadio(rdbNeurologico_a_n,
					his_atencion_crecimiento_menor2_5.getNeurologico_a_n());
			Utilidades.seleccionarRadio(rdbBoca,
					his_atencion_crecimiento_menor2_5.getBoca());
			Utilidades.seleccionarRadio(rdbExtremidades,
					his_atencion_crecimiento_menor2_5.getExtremidades());
			Utilidades.seleccionarRadio(rdbPiel_y_anexos,
					his_atencion_crecimiento_menor2_5.getPiel_y_anexos());
			tbxObservacioness.setValue(his_atencion_crecimiento_menor2_5
					.getObservacioness());
			chbDesnutricion_sev_pes_bajo
					.setChecked(his_atencion_crecimiento_menor2_5
							.getDesnutricion_sev_pes_bajo());
			chbObesidad_crecimiento
					.setChecked(his_atencion_crecimiento_menor2_5
							.getObesidad_crecimiento());
			chbDesnutricion_crecimiento
					.setChecked(his_atencion_crecimiento_menor2_5
							.getDesnutricion_crecimiento());
			chbSobrepeso_crecimiento
					.setChecked(his_atencion_crecimiento_menor2_5
							.getSobrepeso_crecimiento());
			chbRiesgo_desnutricion_crecimiento
					.setChecked(his_atencion_crecimiento_menor2_5
							.getRiesgo_desnutricion_crecimiento());
			chbCrecimiento_adecuado_crecimiento
					.setChecked(his_atencion_crecimiento_menor2_5
							.getCrecimiento_adecuado_crecimiento());
			chbSosp_retras_desarrollo2
					.setChecked(his_atencion_crecimiento_menor2_5
							.getSosp_retras_desarrollo2());
			chbDesarrollo_normal_fac_riesgo22
					.setChecked(his_atencion_crecimiento_menor2_5
							.getDesarrollo_normal_fac_riesgo22());
			chbRiesgo_problema_desarrollo2
					.setChecked(his_atencion_crecimiento_menor2_5
							.getRiesgo_problema_desarrollo2());
			chbDesarrollo_normal2.setChecked(his_atencion_crecimiento_menor2_5
					.getDesarrollo_normal2());
			chbMaltrato_fisico.setChecked(his_atencion_crecimiento_menor2_5
					.getMaltrato_fisico());
			chbMaltrato_emocional.setChecked(his_atencion_crecimiento_menor2_5
					.getMaltrato_emocional());
			chbSospechas_abuso_sexual
					.setChecked(his_atencion_crecimiento_menor2_5
							.getSospechas_abuso_sexual());
			chbNo_hay_sospechas_maltrato
					.setChecked(his_atencion_crecimiento_menor2_5
							.getNo_hay_sospechas_maltrato());
			tbxAnalisis.setValue(his_atencion_crecimiento_menor2_5
					.getAnalisis());
			chbEstimulo_factores_protectores
					.setChecked(his_atencion_crecimiento_menor2_5
							.getEstimulo_factores_protectores());
			chbRecomendaciones_de_buentrato
					.setChecked(his_atencion_crecimiento_menor2_5
							.getRecomendaciones_de_buentrato());
			chbOrientacion_vacunacion
					.setChecked(his_atencion_crecimiento_menor2_5
							.getOrientacion_vacunacion());
			chbRecomendaciones_nutricionales
					.setChecked(his_atencion_crecimiento_menor2_5
							.getRecomendaciones_nutricionales());
			chbImportancia_asistencia_controles
					.setChecked(his_atencion_crecimiento_menor2_5
							.getImportancia_asistencia_controles());
			chbRecomendaciones_higienicas
					.setChecked(his_atencion_crecimiento_menor2_5
							.getRecomendaciones_higienicas());
			chbRecomendaciones_pra_desarrollo
					.setChecked(his_atencion_crecimiento_menor2_5
							.getRecomendaciones_pra_desarrollo());
			chbRecomendaciones_en_salud_oral
					.setChecked(his_atencion_crecimiento_menor2_5
							.getRecomendaciones_en_salud_oral());
			chbNecesita_prescripcion_vitamina_a
					.setChecked(his_atencion_crecimiento_menor2_5
							.getNecesita_prescripcion_vitamina_a());
			chbNecesita_prescripcion_albendazol
					.setChecked(his_atencion_crecimiento_menor2_5
							.getNecesita_prescripcion_albendazol());
			chbNecesita_prescripcion_hierro
					.setChecked(his_atencion_crecimiento_menor2_5
							.getNecesita_prescripcion_hierro());
			chbNecesita_prescripcion_zinc
					.setChecked(his_atencion_crecimiento_menor2_5
							.getNecesita_prescripcion_zinc());
			tbxDescripciones_recomend_medi
					.setValue(his_atencion_crecimiento_menor2_5
							.getDescripciones_recomend_medi());
			Utilidades
					.seleccionarRadio(rdbLimpieza_boca_manana,
							his_atencion_crecimiento_menor2_5
									.getLimpieza_boca_manana());
			Utilidades.seleccionarRadio(rdbEsta_descuidado_nino_en_salud,
					his_atencion_crecimiento_menor2_5
							.getEsta_descuidado_nino_en_salud());
			Utilidades.seleccionarRadio(rdbEsta_descuidado_el_nino_higiene,
					his_atencion_crecimiento_menor2_5
							.getEsta_descuidado_el_nino_higiene());
			Utilidades.seleccionarRadio(rdbActitud_anormal_del_nino,
					his_atencion_crecimiento_menor2_5
							.getActitud_anormal_del_nino());
			Utilidades.seleccionarRadio(rdbSe_le_pega_al_nino,
					his_atencion_crecimiento_menor2_5.getSe_le_pega_al_nino());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					his_atencion_crecimiento_menor2_5
							.getSintomaticos_respiratorio());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					his_atencion_crecimiento_menor2_5.getSintomaticos_piel());

			tbxObservaciones_vacunales
					.setValue(his_atencion_crecimiento_menor2_5
							.getObservaciones_vacunales());

			tipo_historia = his_atencion_crecimiento_menor2_5
					.getTipo_historia();
			
			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2", tbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2", tbxObservaciones_cuello, row1);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",tbxObservaciones_abdomen,row1);
			FormularioUtil.mostrarObservaciones(rdbOrgano_de_sentidos,"2",tbxObservaciones_sentidos,row2);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2", tbxObservaciones_torax, row2);
			FormularioUtil.mostrarObservaciones(rdbMasa,"2",tbxObservaciones_masas,row2);
			FormularioUtil.mostrarObservaciones(rdbFijacion_seguimiento,"2",tbxObservaciones_fijacion,row3);
			FormularioUtil.mostrarObservaciones(rdbTirmo_cardiaco, "2", tbxObservaciones_cardiaco, row3);
			FormularioUtil.mostrarObservaciones(rdbMegalias, "2", tbxObservaciones_megalias, row3);
			FormularioUtil.mostrarObservaciones(rdbOclusion_alternante,"2",tbxObservaciones_oclusion,row4);
			FormularioUtil.mostrarObservaciones(rdbSoplo, "2", tbxObservaciones_soplo, row4);
			FormularioUtil.mostrarObservaciones(rdbCover_uncover_test, "2", tbxObservaciones_cover, row4);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2", tbxObservaciones_columna, row5);
			FormularioUtil.mostrarObservaciones(rdbNeurologico_a_n,"2",tbxObservaciones_neurologico,row5);
			FormularioUtil.mostrarObservaciones(rdbBoca,"2",tbxObservaciones_boca,row5);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "2", tbxObservaciones_extremidades, row6);			
			FormularioUtil.mostrarObservaciones(rdbPiel_y_anexos, "2", tbxObservaciones_piel, row6);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", tbxObservaciones_genito, row6);

			tbxObservaciones_cabeza.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cabeza());
			tbxObservaciones_abdomen.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_abdomen());
			tbxObservaciones_neurologico.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_neurologico());
			
			tbxObservaciones_sentidos.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_sentidos());
			tbxObservaciones_masas.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_masas());
			
			tbxObservaciones_cover.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cover());
			tbxObservaciones_megalias.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_megalias());
			
			tbxObservaciones_cuello.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cuello());
			tbxObservaciones_genito.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_genito());

			tbxObservaciones_torax.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_torax());
			tbxObservaciones_columna.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_columna());

			tbxObservaciones_cardiaco.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cardiaco());
			tbxObservaciones_extremidades.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_extremidades());

			tbxObservaciones_soplo.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_soplo());
			tbxObservaciones_piel.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_piel());
			
			tbxObservaciones_fijacion.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_fijacion());
			tbxObservaciones_oclusion.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_oclusion());
			tbxObservaciones_boca.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_boca());

			
			calcularCoordenadas(false);

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString())) {
				infoPacientes
						.setCodigo_historia(his_atencion_crecimiento_menor2_5
								.getCodigo_historia());
				inicializarVista(tipo_historia);
			}

			cargarImpresionDiagnostica(his_atencion_crecimiento_menor2_5);
			cargarEscalaDesarrollo(his_atencion_crecimiento_menor2_5);

			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);
			cargarAnexo9_remision(his_atencion_crecimiento_menor2_5);

			tbxAccion.setText("modificar");

			FormularioUtil.deshabilitarComponentes(rowCita_anterior, true,
					new String[] {});

			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			btnImprimir.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) obj;
		try {
			his_atencion_crecimiento_menor2_5.setDelete_user(getUsuarios().getCodigo()); 
			int result = getServiceLocator()
					.getHis_atencion_crecimiento_menor2_5Service().eliminar(
							his_atencion_crecimiento_menor2_5);
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
			macroEscalaDesarrollo.setZkWindow(this);
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
			macroAgudeza_visual.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);

			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = new His_atencion_crecimiento_menor2_5();
				his_atencion_crecimiento_menor2_5.setCodigo_empresa(empresa
						.getCodigo_empresa());
				his_atencion_crecimiento_menor2_5.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				his_atencion_crecimiento_menor2_5.setIdentificacion(admision
						.getNro_identificacion());
				his_atencion_crecimiento_menor2_5.setNro_ingreso(admision
						.getNro_ingreso());

				his_atencion_crecimiento_menor2_5 = getServiceLocator()
						.getHis_atencion_crecimiento_menor2_5Service()
						.consultar(his_atencion_crecimiento_menor2_5);
				if (his_atencion_crecimiento_menor2_5 != null) {
					accionForm(OpcionesFormulario.MOSTRAR,
							his_atencion_crecimiento_menor2_5);
					infoPacientes
							.setCodigo_historia(his_atencion_crecimiento_menor2_5
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
			if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
				if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
					cobrar_agudeza = true;
					rowAgudeza_visual.setVisible(true);
					macroAgudeza_visual.validarCamposObligatorios();

				}
			}

		}
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowCita_anterior.setVisible(false);
			rowPerinatales.setVisible(true);
			rowPatologicos.setVisible(true);
			rowAntecedentes_familiares.setVisible(true);
			rowAnalisis.setVisible(true);
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowCita_anterior.setVisible(true);
			rowAnalisis.setVisible(true);
			rowPerinatales.setVisible(false);
			rowPatologicos.setVisible(false);
			rowAntecedentes_familiares.setVisible(false);
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						//log.info("tbxAccion.getValue()  === > "
								//+ tbxAccion.getValue());

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<His_atencion_crecimiento_menor2_5> listado_historias = getServiceLocator()
									.getHis_atencion_crecimiento_menor2_5Service()
									.listar(parametros);

							if (!listado_historias.isEmpty()) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica de control/evolucion");
								admision.setPrimera_vez("N");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica por primera vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision.setPrimera_vez("S");
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								His_atencion_crecimiento_menor2_5 hisc_det = new His_atencion_crecimiento_menor2_5();
								hisc_det.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc_det.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc_det.setCodigo_historia(codigo_historia_anterior);

								hisc_det = getServiceLocator()
										.getHis_atencion_crecimiento_menor2_5Service()
										.consultar(hisc_det);

								if (hisc_det != null) {
									cargarInformacion_historia_anterior(hisc_det);
								}

							}
						}

					}
				});

	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
		His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) historia_anterior;
		chbCa_Sosp_retras_desarrollo2
				.setChecked(his_atencion_crecimiento_menor2_5
						.getSosp_retras_desarrollo2());
		chbCa_Desarrollo_normal_fac_riesgo22
				.setChecked(his_atencion_crecimiento_menor2_5
						.getDesarrollo_normal_fac_riesgo22());
		chbCa_Riesgo_problema_desarrollo2
				.setChecked(his_atencion_crecimiento_menor2_5
						.getRiesgo_problema_desarrollo2());
		chbCa_Desarrollo_normal2.setChecked(his_atencion_crecimiento_menor2_5
				.getDesarrollo_normal2());
		chbCa_Sospechas_abuso_sexual
				.setChecked(his_atencion_crecimiento_menor2_5
						.getSospechas_abuso_sexual());
		chbCa_No_hay_sospechas_maltrato
				.setChecked(his_atencion_crecimiento_menor2_5
						.getNo_hay_sospechas_maltrato());
		chbCa_Maltrato_fisico.setChecked(his_atencion_crecimiento_menor2_5
				.getMaltrato_fisico());
		chbCa_Maltrato_emocional.setChecked(his_atencion_crecimiento_menor2_5
				.getMaltrato_emocional());
		codigo_historia_anterior = his_atencion_crecimiento_menor2_5
				.getCodigo_historia();

		// vacunales
		Utilidades.seleccionarRadio(rdbBcg,
				his_atencion_crecimiento_menor2_5.getBcg());
		Utilidades.seleccionarRadio(rdbVop_2,
				his_atencion_crecimiento_menor2_5.getVop_2());
		Utilidades.seleccionarRadio(rdbVop_3,
				his_atencion_crecimiento_menor2_5.getVop_3());
		Utilidades.seleccionarRadio(rdbNeumococo_3,
				his_atencion_crecimiento_menor2_5.getNeumococo_3());
		Utilidades.seleccionarRadio(rdbHepatitis_b_rn,
				his_atencion_crecimiento_menor2_5.getHepatitis_b_rn());
		Utilidades.seleccionarRadio(rdbPenta_2,
				his_atencion_crecimiento_menor2_5.getPenta_2());
		Utilidades.seleccionarRadio(rdbPenta_3,
				his_atencion_crecimiento_menor2_5.getPenta_3());
		Utilidades.seleccionarRadio(rdbTriple_viral,
				his_atencion_crecimiento_menor2_5.getTriple_viral());
		Utilidades.seleccionarRadio(rdbVop_1,
				his_atencion_crecimiento_menor2_5.getVop_1());
		Utilidades.seleccionarRadio(rdbNeumococo_2,
				his_atencion_crecimiento_menor2_5.getNeumococo_2());
		Utilidades.seleccionarRadio(rdbInfluenza_1,
				his_atencion_crecimiento_menor2_5.getInfluenza_1());
		Utilidades.seleccionarRadio(rdbFiebre_amarilla,
				his_atencion_crecimiento_menor2_5.getFiebre_amarilla());
		Utilidades.seleccionarRadio(rdbPenta_1,
				his_atencion_crecimiento_menor2_5.getPenta_1());
		Utilidades.seleccionarRadio(rdbRotavirus_2,
				his_atencion_crecimiento_menor2_5.getRotavirus_2());
		Utilidades.seleccionarRadio(rdbInfluenza_2,
				his_atencion_crecimiento_menor2_5.getInfluenza_2());
		Utilidades.seleccionarRadio(rdbDpt_r1,
				his_atencion_crecimiento_menor2_5.getDpt_r1());
		Utilidades.seleccionarRadio(rdbNeumococo_1,
				his_atencion_crecimiento_menor2_5.getNeumococo_1());
		Utilidades.seleccionarRadio(rdbHepatitis_a,
				his_atencion_crecimiento_menor2_5.getHepatitis_a());
		Utilidades.seleccionarRadio(rdbRotavirus_1,
				his_atencion_crecimiento_menor2_5.getRotavirus_1());
		Utilidades.seleccionarRadio(rdbVop_r1,
				his_atencion_crecimiento_menor2_5.getVop_r1());

		// Perinatales
		ibxPerinatales_g.setValue(his_atencion_crecimiento_menor2_5
				.getPerinatales_g());
		ibxPerinatales_p.setValue(his_atencion_crecimiento_menor2_5
				.getPerinatales_p());
		ibxPerinatales_a.setValue(his_atencion_crecimiento_menor2_5
				.getPerinatales_a());
		ibxPerinatales_c.setValue(his_atencion_crecimiento_menor2_5
				.getPerinatales_c());
		ibxPerinatales_v.setValue(his_atencion_crecimiento_menor2_5
				.getPerinatales_v());
		Utilidades.seleccionarRadio(rdbRiesgo_bajo_alt,
				his_atencion_crecimiento_menor2_5.getRiesgo_bajo_alt());
		Utilidades.seleccionarRadio(rdbControl_prenatal,
				his_atencion_crecimiento_menor2_5.getControl_prenatal());
		ibxSem_gestacion.setValue(his_atencion_crecimiento_menor2_5
				.getSem_gestacion());
		ibxEdad_de_la_madre_al_nacer.setValue(his_atencion_crecimiento_menor2_5
				.getEdad_de_la_madre_al_nacer());
		Utilidades.seleccionarRadio(rdbParto_cst,
				his_atencion_crecimiento_menor2_5.getParto_cst());
		Utilidades.seleccionarRadio(rdbUnico_multiple,
				his_atencion_crecimiento_menor2_5.getUnico_multiple());
		Utilidades.seleccionarRadio(rdbIntitucional,
				his_atencion_crecimiento_menor2_5.getIntitucional());
		Utilidades.seleccionarListitem(lbxHemoclasificacion,
				his_atencion_crecimiento_menor2_5.getHemoclasificacion());
		Utilidades.seleccionarListitem(lbxVdlr,
				his_atencion_crecimiento_menor2_5.getVdrl_materno());
		dbxTsh.setValue(his_atencion_crecimiento_menor2_5.getTsh());
		tbxComplicaciones_embarazo_parto
				.setValue(his_atencion_crecimiento_menor2_5
						.getComplicaciones_embarazo_parto());
		dbxExamen_fisico_peso_al_nacer
				.setValue(his_atencion_crecimiento_menor2_5.getPeso_al_nacer());
		dbxExamen_fisico_talla_al_nacer
				.setValue(his_atencion_crecimiento_menor2_5.getTalla_al_nacer());
		dbxApgar_minutos.setValue(his_atencion_crecimiento_menor2_5
				.getApgar_minutos());
		dbxApgar_5_minutos.setValue(his_atencion_crecimiento_menor2_5
				.getApgar_5_minutos());
		Utilidades.seleccionarRadio(rdbReanimacion,
				his_atencion_crecimiento_menor2_5.getReanimacion());
		Utilidades
				.seleccionarRadio(rdbHospitalizacion_neonatal,
						his_atencion_crecimiento_menor2_5
								.getHospitalizacion_neonatal());
		tbxObservaciones1.setValue(his_atencion_crecimiento_menor2_5
				.getObservaciones());

		// Antecedentes familiares
		ibxHermanos_vivos.setValue(his_atencion_crecimiento_menor2_5
				.getHermanos_vivos());
		ibxHermanos_desnutridos.setValue(his_atencion_crecimiento_menor2_5
				.getHermanos_desnutridos());
		ibxHermanos_muertos.setValue(his_atencion_crecimiento_menor2_5
				.getHermanos_muertos());
		tbxCausa.setValue(his_atencion_crecimiento_menor2_5.getCausa());
		Utilidades.seleccionarRadio(rdbSon_parientes_los_padre,
				his_atencion_crecimiento_menor2_5.getSon_parientes_los_padre());
		Utilidades.seleccionarRadio(rdbFamiliar_con_problema,
				his_atencion_crecimiento_menor2_5.getFamiliar_con_problema());
		tbxPaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
				.getPaternos_medicos());
		tbxPaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
				.getPaternos_alergicos());
		dbxPaternos_talla.setValue(his_atencion_crecimiento_menor2_5
				.getPaternos_talla());
		tbxMaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
				.getMaternos_medicos());
		tbxMaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
				.getMaternos_alergicos());
		dbxMaternos_talla.setValue(his_atencion_crecimiento_menor2_5
				.getMaternos_talla());
		tbxOtros.setValue(his_atencion_crecimiento_menor2_5.getOtros());

		// DATOS DEL PACIENTE
		// tbxNombre_del_acompanante.setValue(his_atencion_crecimiento_menor2_5.getNombre_del_acompanante());
		// Utilidades.seleccionarListitem(lbxParentesco,his_atencion_crecimiento_menor2_5.getParentesco());
		tbxNom_padre.setValue(his_atencion_crecimiento_menor2_5.getNom_padre());
		ibxEdad_padre.setValue(his_atencion_crecimiento_menor2_5
				.getEdad_padre());
		tbxNom_madre.setValue(his_atencion_crecimiento_menor2_5.getNom_madre());
		ibxEdad_madre.setValue(his_atencion_crecimiento_menor2_5
				.getEdad_madre());

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					idsExcluyentes);
			if (his_atencion_crecimiento_menor2_5.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			if (his_atencion_crecimiento_menor2_5.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(true);
		}
		codigo_historia_anterior = his_atencion_crecimiento_menor2_5
				.getCodigo_historia_anterior();
		tipo_historia = his_atencion_crecimiento_menor2_5.getTipo_historia();
	}

	// DATOS CURVAS CRECIMIENTO.
	public void calcularCoordenadas(Boolean mostrarAlerta) {
		calcularImcEdad(mostrarAlerta);
		calcularPerimetroCefalicoEdad(mostrarAlerta);
		calcularPesoTalla(mostrarAlerta);
		calcularTallaEdad(mostrarAlerta);
	}

	private List<RespuestaInt> cargarRespuestas(
			List<Coordenadas_graficas> coordenadas,
			His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5) {
		List<RespuestaInt> respuestas = new ArrayList<RespuestaInt>();
		for (Coordenadas_graficas cg : coordenadas) {
			RespuestaInt r = cargarResuestaInt(cg);
			if (his_atencion_crecimiento_menor2_5 != null
					&& cg.getCodigo_historia().equals(
							his_atencion_crecimiento_menor2_5
									.getCodigo_historia())) {
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
	public void calcularTallaEdad(Boolean alerta) {
		double talla;
		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if(alerta){
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_talla.setStyle("background-color:white");
			talla = dbxExamen_fisico_talla.getValue();
		}

		if (dbxExamen_fisico_talla.getValue() != null && talla >= 80
				&& talla <= 120) {
			coordenadaTallaEdad = calcularTallaEdad(talla, fecha);
			dbxT_e_de.setValue(coordenadaTallaEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxT_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_TALLA_EDAD);
			}
			btnCalcularTallaEdad.setDisabled(false);
		} else {
			if(alerta){
			if (!(talla >= 80 && talla <= 120)) {
				Messagebox.show("La talla está por fuera del rango [80-120]!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			}
		}
	}

	public void calcularPesoTalla(Boolean alerta) {

		double talla;
		double peso;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if(alerta){
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
			if(alerta){
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
				&& dbxExamen_fisico_peso.getValue() != null && talla >= 80
				&& talla <= 120 && peso >= 6 && peso <= 30) {
			coordenadaPesoTalla = calcularPesoTalla(peso, talla);
			dbxP_t_de.setValue(coordenadaPesoTalla.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxP_t_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PESO_TALLA);
			}
			btnCalcularPesoTalla.setDisabled(false);
		} else {
			if(alerta){
			if (!(talla >= 80 && talla <= 120)) {
				Messagebox.show("La talla está por fuera del rango [80-120]!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			} else if (!(peso >= 6 && peso <= 30)) {
				Messagebox.show("El peso está por fuera del rango [6-30]!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			}
		}
	}

	public void calcularImcEdad(Boolean alerta) {

		double talla;
		double peso;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if(alerta){
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
			if(alerta){
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
			double imc = CalculadorUtil.calcularIMC(peso, talla / 100);
			coordenadaIMCEdad = calcularImcEdad(imc, fecha);
			dbxImc_e_de.setValue(coordenadaIMCEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(
						dbxImc_e_de, paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_IMC_EDAD);
			}
			btnCalcularImcEdad.setDisabled(false);
		} else {
			if(alerta){
			if (!(tallaValida(talla))) {
				Messagebox.show("La talla está por fuera del rango [80-120]!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			} else if (!(pesoValido(peso))) {
				Messagebox.show("El peso está por fuera del rango [6-30]!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			}
		}
	}

	public void calcularPerimetroCefalicoEdad(Boolean alerta) {

		if (edad < 4 && dbxExamen_fisico_perimetro_cflico.getValue() == null) {
			dbxExamen_fisico_perimetro_cflico
					.setStyle("background-color:#F6BBBE");
			if(alerta){
			Messagebox
					.show("Debe digitar el perimetro cefálico del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxExamen_fisico_perimetro_cflico
					.setStyle("background-color:white");
		}

		if (dbxExamen_fisico_perimetro_cflico.getValue() != null
				&& perimetroCefalicoValido(dbxExamen_fisico_perimetro_cflico
						.getValue())) {
			coordenadaPerimetroCefalicoEdad = calcularPerimetroCefalicoEdad(dbxExamen_fisico_perimetro_cflico
					.getValue());
			dbxPC_e_de.setValue(coordenadaPerimetroCefalicoEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxPC_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PC_EDAD);
			}
			btnCalcularPerimetroCefalicoEdad.setDisabled(false);
		} else {
			if(alerta){
			if (edad < 4) {
				Messagebox
						.show("El perimetro cefálico está por fuera del rango [30-55]!!",
								"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			}
		}
	}

	public boolean pesoValido(Double peso) {
		Double min = 6.0;
		Double max = 30.0;
		return (peso >= min && peso <= max);
	}

	public boolean tallaValida(Double talla) {
		Double min = 80.0;
		Double max = 120.0;
		return (talla >= min && talla <= max);
	}

	public boolean perimetroCefalicoValido(Double perimetro_cefalico) {
		Double min = 30.0;
		Double max = 55.0;
		return (perimetro_cefalico >= min && perimetro_cefalico <= max);
	}

	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularTallaEdad2$5Anios(sexo, talla,
				fecha);
	}

	private RespuestaInt calcularPesoTalla(double peso, double talla) {
		return GraficasCalculadorUtils.calcularPesoTalla2$5Anios(sexo, peso,
				talla);
	}

	private RespuestaInt calcularImcEdad(double imc, Timestamp fecha) {
		return GraficasCalculadorUtils
				.calcularIMCEdad0$5Anios(sexo, imc, fecha);
	}

	private RespuestaInt calcularPerimetroCefalicoEdad(double perimetro_cefalico) {
		return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
				sexo, perimetro_cefalico, fecha);
	}

	/* Graficamos */
	public void graficarTallaEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.T_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> tes = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasTE = cargarRespuestas(tes, hc_menor_2_5);

		List coordenadas_te = coordenadasTE;
		if (!verificarActivo(coordenadasTE)) {
			coordenadas_te.add(coordenadaTallaEdad);
		}
		imprimirArreglo(coordenadas_te);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_DE_0_A_5,
				coordenadas_te, this, sexo);
	}

	public void mostrarTablaTallaEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_DE_0_A_5, this,
				sexo);
	};

	public void graficarPesoTalla() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_T);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> pts = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasPT = cargarRespuestas(pts, hc_menor_2_5);

		List coordenadas_pt = coordenadasPT;
		if (!verificarActivo(coordenadasPT)) {
			coordenadas_pt.add(coordenadaPesoTalla);
		}
		imprimirArreglo(coordenadas_pt);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_2_A_5,
				coordenadas_pt, this, sexo);
	}

	public void mostrarTablaPesoTalla() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_2_A_5, this,
				sexo);
	};

	public void graficarIMCEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.IMC_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> imces = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasIE = cargarRespuestas(imces, hc_menor_2_5);

		List coordenadas_imce = coordenadasIE;
		if (!verificarActivo(coordenadasIE)) {
			coordenadas_imce.add(coordenadaIMCEdad);
		}

		imprimirArreglo(coordenadas_imce);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_0_A_5,
				coordenadas_imce, this, sexo);
	}

	public void mostrarTablaIMCEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_0_A_5, this, sexo);
	};

	public void graficarPcEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.PC_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> pces = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);

		coordenadasPCE = cargarRespuestas(pces, hc_menor_2_5);

		List coordenadas_pce = coordenadasPCE;
		if (!verificarActivo(coordenadasPCE)) {
			coordenadas_pce.add(coordenadaPerimetroCefalicoEdad);
		}
		imprimirArreglo(coordenadas_pce);
		GraficasCalculadorUtils
				.mostrarGrafica(
						GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5,
						coordenadas_pce, this, sexo);
	}

	public void mostrarTablaPcEdad() {
		GraficasCalculadorUtils
				.mostrarTabla(
						GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5,
						this, sexo);
	};

	public void imprimirArreglo(List<RespuestaInt> arreglo) {
//		for (RespuestaInt ri : arreglo) {
//			//log.info(">>>>>>>>>>>>" + ri.isActual());
//		}
	}

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
	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.calcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void cargarImpresionDiagnostica(
			His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica
				.setCodigo_historia(his_atencion_crecimiento_menor2_5
						.getCodigo_historia());
		//log.info(">>>>>>>>>>>>(impresion diagnostica)A: "
				//+ impresion_diagnostica);
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		//log.info(">>>>>>>>>>>>(impresion diagnostica)D: "
				//+ impresion_diagnostica);
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

	private void cargarAnexo9_remision(
			His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(his_atencion_crecimiento_menor2_5
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

	public void notificarAlerta(Component comp) {
		MensajesUtil.notificarAlerta("Remitir", comp);
	}

	private void cargarRemisionInterna(
			His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(his_atencion_crecimiento_menor2_5
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(his_atencion_crecimiento_menor2_5
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(his_atencion_crecimiento_menor2_5
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private void cargarAgudezaVisual(
			His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5)
			throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_historia(his_atencion_crecimiento_menor2_5
				.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(his_atencion_crecimiento_menor2_5
				.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(his_atencion_crecimiento_menor2_5
				.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	private void cargarEscalaDesarrollo(
			His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5) {
		Escala_del_desarrollo escala_del_desarrollo = new Escala_del_desarrollo();
		escala_del_desarrollo.setCodigo_empresa(codigo_empresa);
		escala_del_desarrollo.setCodigo_sucursal(codigo_sucursal);
		escala_del_desarrollo
				.setVia_ingreso(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
		escala_del_desarrollo
				.setCodigo_historia(his_atencion_crecimiento_menor2_5
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
		informacion_clinica.append("\t").append(tbxMotivo_consulta.getValue())
				.append("\n");

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
				.append(dbxExamen_fisico_fc.getValue() + ("\n")).append("\t")
				.append("Frecuencia respiratoria(min): ")
				.append(dbxExamen_fisico_fr.getValue() + ("\n"));
		informacion_clinica.append("\tTemperatura: ")
				.append(dbxExamen_fisico_temp.getValue() + ("\n"))
				.append(" \tPeso(kg): ")
				.append(dbxExamen_fisico_peso.getValue() + ("\n"))
				.append(" \tTalla(Cm): ")
				.append(dbxExamen_fisico_talla.getValue() + ("\n"));

		if (dbxExamen_fisico_perimetro_cflico.getValue() != null) {
			informacion_clinica.append("\tPerímetro cefálico (Cm): ").append(
					dbxExamen_fisico_perimetro_cflico.getValue() + ("\n"));
		}

		informacion_clinica.append("\n").append("\n");

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
			dbxImc.setValue(enfermera_signos.getImc());

			alarmaExamenFisicoTemperatura();
			alarmaExamenFisicoRespiracion();
			alarmaExamenFisicoFc();
			calcularIMC(dbxExamen_fisico_peso, dbxExamen_fisico_talla, dbxImc);

		}
	}
	
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
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/his_atencion_crecimiento_menor2_5_reporte.zul"+parametros_pagina+"\", '_blank');");
		}
	}
	

}