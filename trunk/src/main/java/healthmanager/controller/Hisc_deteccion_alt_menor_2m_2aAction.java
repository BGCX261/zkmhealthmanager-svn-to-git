/*
 * hisc_deteccion_alt_menor_2m_2aAction.java
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
import healthmanager.modelo.bean.Hisc_deteccion_alt_menor_2m_2a;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Escala_del_desarrolloService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

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

public class Hisc_deteccion_alt_menor_2m_2aAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_deteccion_alt_menor_2m_2aAction.class);

	// Componentes //
	// Manuel Aguilar
	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;
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
	private Textbox tbxNombre_del_acompanante;
	@View
	private Listbox lbxParentesco;
	@View
	private Textbox tbxNombre_del_padre;
	@View
	private Intbox ibxEdad_padre;
	@View
	private Textbox tbxNombre_de_la_madre;
	@View
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxGestion_actual;
	@View
	private Radiogroup rdbPuede_beber_pecho;
	@View
	private Radiogroup rdbVomita_todo;
	@View
	private Radiogroup rdbConvulsiones;
	@View
	private Radiogroup rdbTiene_tos_disnea;
	@View
	private Radiogroup rdbTiene_sibilancias;
	@View
	private Radiogroup rdbSibilancias_previas;
	@View
	private Radiogroup rdbCuadro_gripal_ultimos_3d;
	@View
	private Radiogroup rdbAntecedente_prematuridad;
	@View
	private Radiogroup rdbTiene_diarrea;
	@View
	private Radiogroup rdbDiarrea_ultimas_24h;
	@View
	private Radiogroup rdbDiarrea_ultimas_4h;
	@View
	private Radiogroup rdbHay_sangre_en_heces;
	@View
	private Radiogroup rdbTiene_vomito;
	@View
	private Radiogroup rdbVomito_ultimas_4h;
	@View
	private Radiogroup rdbTiene_fiebre_mas_5d;
	@View
	private Radiogroup rdbTiene_fiebre_menos_5d;
	@View
	private Radiogroup rdbFiebre_mas_38g;
	@View
	private Radiogroup rdbFiebre_mas_39g;
	@View
	private Radiogroup rdbVive_visito_ultimos_15d_zona_dengue;
	@View
	private Radiogroup rdbVive_visito_ultimos_15d_zona_malria;
	@View
	private Radiogroup rdbTiene_otalgia;
	@View
	private Radiogroup rdbTiene_supuracion_oido;
	@View
	private Radiogroup rdbEpisodios_previos_otitis;
	@View
	private Radiogroup rdbTiene_dolor_garganta;
	@View
	private Radiogroup rdbCefalea_dolor_retroocular;
	@View
	private Radiogroup rdbMialgias_artralgias;
	@View
	private Textbox tbxObservaciones_3;
	@View
	private Intbox ibxNum_embarazos_g;
	@View
	private Intbox ibxNum_partos_p;
	@View
	private Intbox ibxNum_abortos_a;
	@View
	private Intbox ibxNum_cesarias_c;
	@View
	private Intbox ibxNum_mortinatos_v;
	@View
	private Radiogroup rdbRiesgo;
	@View
	private Radiogroup rdbControl_prenatal;
	@View
	private Intbox ibxSem_gestacion;
	@View
	private Intbox ibxEdad_madre_al_nacimiento;
	@View
	private Radiogroup rdbParto;
	@View
	private Radiogroup rdbUnico_multiple;
	@View
	private Radiogroup rdbInstitucional;
	@View
	private Listbox lbxHemoclasificacion;
	@View
	private Doublebox dbxTsh;
	@View
	private Listbox lbxVdlr;
	@View
	private Textbox tbxComplicaciones_embarazo_parto;
	@View
	private Doublebox dbxPeso_al_nacer_grs;
	@View
	private Doublebox dbxTalla_al_nacer_cms;
	@View
	private Doublebox dbxApgar_al_minuto;
	@View
	private Doublebox dbxApgar_5minutos;
	@View
	private Radiogroup rdbReanimacion;
	@View
	private Radiogroup rdbHospitalizacion_neonatal;
	@View
	private Textbox tbxObservaciones_4_1;
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
	private Radiogroup rdbRecibe_leche_materna;
	@View
	private Radiogroup rdbHa_recibido_hierro_en_los_ultimos_6m;
	@View
	private Radiogroup rdbLactancia_materna_exclusiva;
	@View
	private Radiogroup rdbHa_recibido_vitamina_a_en_los_ultimos_6m;
	@View
	private Radiogroup rdbLactancia_materna_nocturna;
	@View
	private Radiogroup rdbHa_recibido_albendazol_en_los_ultimos_6m;
	@View
	private Radiogroup rdbMenos_6m_recibe_otras_leches_alimentos;
	@View
	private Textbox tbxObservaciones_4_4;
	@View
	private Radiogroup rdbDolor_masticar;
	@View
	private Radiogroup rdbLimpieza_boca_medio_dia;
	@View
	private Radiogroup rdbUtiliza_crema;
	@View
	private Radiogroup rdbDolor_en_diente;
	@View
	private Radiogroup rdbLimpieza_boca_noche;
	@View
	private Radiogroup rdbUtiliza_chupo_o_biberon;
	@View
	private Radiogroup rdbPadre_hnos_caries;
	@View
	private Radiogroup rdbLe_limpia_los_dientes;
	@View
	private Radiogroup rdbAsiste_a_odontologia;
	@View
	private Radiogroup rdbLimpieza_boca_manana;
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
	private Radiogroup rdbVop_r1;
	@View
	private Radiogroup rdbRotavirus_1;
	@View
	private Radiogroup rdbHepatitis_a;
	@View
	private Intbox ibxNum_hermanos_vivos;
	@View
	private Intbox ibxNum_hermanos_desnutridos_menor_5anos;
	@View
	private Intbox ibxNum_hermanos_muertos_menor_5anos;
	@View
	private Textbox tbxNum_hermanos_muertos_menor_5anos_causa;
	@View
	private Radiogroup rdbSon_parientes_los_padres;
	@View
	private Radiogroup rdbFamilia_con_problema_mental_o_fisico;
	@View
	private Textbox tbxPaterno_medicos;
	@View
	private Textbox tbxPaterno_alergico;
	@View
	private Doublebox dbxMaterno_talla;
	@View
	private Textbox tbxMaterno_medicos;
	@View
	private Textbox tbxMaterno_alergico;
	@View
	private Doublebox dbxPaterno_talla;
	@View
	private Textbox tbxOtros_5;
	@View
	private Radiogroup rdbTestigo_relata_maltrato;
	@View
	private Radiogroup rdbEsta_descuidado_nino_salud;
	@View
	private Radiogroup rdbComportamiento_anormal_padres;
	@View
	private Radiogroup rdbEsta_descuidado_nino_higiene;
	@View
	private Radiogroup rdbActitud_anormal_nino;
	@View
	private Radiogroup rdbSe_le_pega_nino;
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
	private Radiogroup rdbEmaciacion_visible;
	@View
	private Radiogroup rdbEdema_ambos_pies;
	@View
	private Doublebox dbxP_e_de;
	@View
	private Doublebox dbxT_e_de;
	@View
	private Doublebox dbxP_t_de;
	@View
	private Doublebox dbxPC_e_de;
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
	private Radiogroup rdbOjos_hundidos;
	@View
	private Radiogroup rdbSupuracion_oido;
	@View
	private Radiogroup rdbConfuso;
	@View
	private Radiogroup rdbSigno_del_pliegue_cutaneo;
	@View
	private Radiogroup rdbGanglio_cuello_doloroso;
	@View
	private Radiogroup rdbAgitado_irritable;
	@View
	private Radiogroup rdbManifestaciones_sangrado;
	@View
	private Radiogroup rdbAmigdalas_eritematosas;
	@View
	private Radiogroup rdbRigidez_de_nuca;
	@View
	private Radiogroup rdbErupcion_cutanea_generalizada;
	@View
	private Radiogroup rdbExudado_en_amigdalas;
	@View
	private Radiogroup rdbTiraje_subcostal;
	@View
	private Radiogroup rdbPrueba_torniquete_positiva;
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
	private Radiogroup rdbPulso_rapido_y_fino;
	@View
	private Radiogroup rdbQuemaduras;
	@View
	private Radiogroup rdbSibilancia;
	@View
	private Radiogroup rdbLlenado_capilar_mayor_2seg;
	@View
	private Radiogroup rdbLaceraciones;
	@View
	private Radiogroup rdbApnea;
	@View
	private Radiogroup rdbAscitis;
	@View
	private Radiogroup rdbMordiscos;
	@View
	private Radiogroup rdbBebe_mal_o_no_puede;
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
	private Radiogroup rdbOrgano_de_los_sentidos;
	@View
	private Radiogroup rdbTorax_cardiopulmonar;
	@View
	private Radiogroup rdbAbdomen_masas;
	@View
	private Radiogroup rdbRojo_retiniano;
	@View
	private Radiogroup rdbTorax_cardiopulmonar_ritmo_cardiaco;
	@View
	private Radiogroup rdbAbdomen_megalias;
	@View
	private Radiogroup rdbFijacion_seguimiento;
	@View
	private Radiogroup rdbTorax_cardiopulmonar_soplo;
	@View
	private Radiogroup rdbGenito_urinario;
	@View
	private Radiogroup rdbOclusion_alternante;
	@View
	private Radiogroup rdbColumna_vertebral;
	@View
	private Radiogroup rdbExtremidades;
	@View
	private Radiogroup rdbBoca;
	@View
	private Radiogroup rdbPiel_anexos;
	@View
	private Radiogroup rdbNeurologico;
	@View
	private Textbox tbxObservaciones_7_2;
	@View
	private Radiogroup rdbDiagnostico_crecimiento;
	@View
	private Radiogroup rdbDiagnostico_desarrollo;
	@View
	private Checkbox chbMaltrato_fisico;
	@View
	private Checkbox chbMaltrato_emocional;
	@View
	private Checkbox chbSospecha_de_abuso_sexual;
	@View
	private Checkbox chbNo_hay_sospecha_de_maltrato;
	@View
	private Textbox tbxAnalisis_9;
	@View
	private Checkbox chbEstimulo_factores_protectores;
	@View
	private Checkbox chbRecomendaciones_de_buen_trato;
	@View
	private Checkbox chbOrientacion_vacunacion;
	@View
	private Checkbox chbLactancia_materna_complementa;
	@View
	private Checkbox chbImportancia_asistencia_controles;
	@View
	private Checkbox chbRecomendaciones_higienicas;
	@View
	private Checkbox chbRecomendaciones_para_el_desarrollo;
	@View
	private Checkbox chbRecomendaciones_en_salud_oral;
	@View
	private Radiogroup rdbNecesita_prescripcion_de_vitamina_a;
	@View
	private Radiogroup rdbNecesita_prescripcion_de_albendazol;
	@View
	private Radiogroup rdbNecesita_prescripcion_de_hierro;
	@View
	private Radiogroup rdbNecesita_prescripcion_de_zinc;
	@View
	private Textbox tbxRecomendaciones_10;
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;

	@View
	private Row rowCita_anterior;
	@View
	private Row rowPerinatales;
	@View
	private Row rowPatologicos;
	@View
	private Row rowAntecedentes;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	@View
	private Radiogroup rdbCa_Diagnostico_crecimiento;
	@View
	private Radiogroup rdbCa_Diagnostico_desarrollo;

	private Admision admision;
	
	private Citas cita;

	private Opciones_via_ingreso opciones_via_ingreso;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Toolbarbutton toolbarbuttonTipo_historia;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton btnCancelar;

	@View
	private Button btnCalcularPesoEdad;
	@View
	private Button btnCalcularTallaEdad;
	@View
	private Button btnCalcularPesoTalla;
	@View
	private Button btnCalcularPerimetroCefalicoEdad;
	@View
	private Textbox tbxObservaciones_vacunales;

	@View
	private Div divModuloOrdenamiento;
	@View
	private Div divModuloFarmacologico;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	private Paciente paciente;
	private Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a;
	private ESexo sexo;
	private Timestamp fecha;

	private RespuestaInt coordenadaPesoEdad;
	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaPesoTalla;
	private RespuestaInt coordenadaPerimetroCefalicoEdad;

	private List coordenadasPE;
	private List coordenadasTE;
	private List coordenadasPT;
	private List coordenadasPCE;

	private String nro_ingreso_admision;
	private Integer edad;

	@View(isMacro = true)
	private EscalaAbreviadaDesarrollo macroEscalaDesarrollo;

	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "macroEscalaDesarrollo","divModuloRemisiones_externas" };
	@View
	private Toolbarbutton btnImprimir;
	
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
	@View private Textbox tbxObservaciones_fijacion;
	@View private Textbox tbxObservaciones_oclusion;
	@View private Textbox tbxObservaciones_boca;
	
	@View private Row row1;
	@View private Row row2;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	@View private Row row6;

	
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

			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);

			macroEscalaDesarrollo.setAdmision(admision);
			if (edadmes >= 2 && edadmes <= 6) {
				macroEscalaDesarrollo.mostrarColorRandoEdad4_6();
				macroEscalaDesarrollo.mostrarColorRandoEdad4_6MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad4_6AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad4_6PS();
			} else if (edadmes >= 7 && edadmes <= 9) {
				macroEscalaDesarrollo.mostrarColorRandoEdad7_9();
				macroEscalaDesarrollo.mostrarColorRandoEdad7_9MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad7_9AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad7_9PS();
			} else if (edadmes >= 10 && edadmes <= 12) {
				macroEscalaDesarrollo.mostrarColorRandoEdad10_12();
				macroEscalaDesarrollo.mostrarColorRandoEdad10_12MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad10_12AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad10_12PS();
			} else if (edadmes >= 13 && edadmes <= 18) {
				macroEscalaDesarrollo.mostrarColorRandoEdad12_18();
				macroEscalaDesarrollo.mostrarColorRandoEdad12_18MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad12_18AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad12_18PS();
			} else if (edadmes >= 19 && edadmes <= 24) {
				macroEscalaDesarrollo.mostrarColorRandoEdad19_24();
				macroEscalaDesarrollo.mostrarColorRandoEdad19_24MFA();
				macroEscalaDesarrollo.mostrarColorRandoEdad19_24AL();
				macroEscalaDesarrollo.mostrarColorRandoEdad19_24PS();
			}
		}

	}

	public void alarmaExamenFisicoFc() {
		//log.info(">>>>>>>>" + edad);
		if (edad == 0) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 161d,
					119d, "Anormal", "Anormal", false);
		} else if (edad == 1) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 131d,
					119d, "Anormal", "Anormal", false);
		} else if (edad == 2) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 121d, 99d,
					"Anormal", "Anormal", false);
		}
	}

	public void alarmaExamenFisicoFr() {
		if (edad == 0) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 41d, 29d,
					"Anormal", "Anormal", false);
		} else if (edad == 1) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 31d, 25d,
					"Anormal", "Anormal", false);
		} else if (edad == 2) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 26d, 24d,
					"Anormal", "Anormal", false);
		}
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

	// // Accion del formulario si es nuevo o consultar //
	// public void accionForm(boolean sw, String accion) throws Exception {
	// groupboxConsulta.setVisible(!sw);
	// groupboxEditar.setVisible(sw);
	//
	// if (!accion.equalsIgnoreCase("registrar")) {
	// buscarDatos();
	// } else {
	// limpiarDatos();
	// FormularioUtil.cargarRadiogroupsDefecto(this);
	// if (admision != null)
	// infoPacientes.setFecha_inicio(new Date());
	// cargarInformacion_paciente();
	// FormularioUtil.deshabilitarComponentes(rowCita_anterior, true,new
	// String[] {});
	//
	// valida_admision = null;
	// onMostrarModuloFarmacologico();
	// receta_ripsAction.obtenerBotonAgregar().setVisible(true);
	// onMostrarModuloOrdenamiento();
	// orden_servicioAction.obtenerBotonAgregar().setVisible(true);
	// }
	// tbxAccion.setValue(accion);
	// }

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

		boolean valida = true;
		infoPacientes.validarInformacionPaciente();
		FormularioUtil
				.validarCamposObligatorios(dbxExamen_fisico_perimetro_cflico,
						dbxExamen_fisico_talla, dbxExamen_fisico_peso,
						dbxExamen_fisico_fc, dbxExamen_fisico_fr,
						dbxExamen_fisico_temp, tbxNombre_del_acompanante,
						lbxParentesco, tbxMotivo_consulta,
						tbxObservaciones_4_4, tbxPatologicos_medicos,
						tbxPatologicos_quirurgicos,
						tbxPatologicos_hospitalizaciones,
						tbxPatologicos_medicacion, tbxAnalisis_9,
						tbxRecomendaciones_10,tbxPatologicos_alergicos);

		valida = receta_ripsAction.validarFormExterno();

		String mensaje = "Los campos marcados con (*) son obligatorios";
		// String mensaje = "";

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

			List<Hisc_deteccion_alt_menor_2m_2a> lista_datos = getServiceLocator()
					.getHisc_deteccion_alt_menor_2m_2aService().listar(
							parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						hisc_deteccion_alt_menor_2m_2a, this));
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

		final Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a = (Hisc_deteccion_alt_menor_2m_2a) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_deteccion_alt_menor_2m_2a
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_deteccion_alt_menor_2m_2a
				.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				hisc_deteccion_alt_menor_2m_2a.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_deteccion_alt_menor_2m_2a
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
						mostrarDatos(hisc_deteccion_alt_menor_2m_2a);
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
													eliminarDatos(hisc_deteccion_alt_menor_2m_2a);
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
				// Cargamos los componentes //

				hisc_deteccion_alt_menor_2m_2a = getBean();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", hisc_deteccion_alt_menor_2m_2a);
				datos.put("admision", admision);
				datos.put("escala_del_desarrollo", macroEscalaDesarrollo.obtenerEscala());
				datos.put("accion", tbxAccion.getValue());
				datos.put("cita_seleccionada", cita);
				
				calcularCoordenadas(false);

				if(pesoValido(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_2m_2a.getPeso_grs()))&&
						tallaValida(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_2m_2a.getTalla_al_nacer_cms()))&&
						perimetroCefalicoValido(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_2m_2a.getPerimetro_cefalico_cm()))
						){
					// Coordenada (P/E)
					Coordenadas_graficas cg1 = new Coordenadas_graficas();
					cg1.setCodigo_empresa(codigo_empresa);
					cg1.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
							.getCodigo_historia());
					cg1.setCodigo_sucursal(codigo_sucursal);
					cg1.setFecha_historia(hisc_deteccion_alt_menor_2m_2a
							.getFecha_inicial());
					cg1.setTipo_coordenada(ITipos_coordenada.P_E);
					cg1.setIdentificacion(paciente.getNro_identificacion());
					cg1.setValor("" + coordenadaPesoEdad.getValor());
					cg1.setX("" + coordenadaPesoEdad.getX());
					cg1.setY("" + coordenadaPesoEdad.getY());
					cg1.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS);
	
					// Coordenada (T/E)
					Coordenadas_graficas cg2 = new Coordenadas_graficas();
					cg2.setCodigo_empresa(codigo_empresa);
					cg2.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
							.getCodigo_historia());
					cg2.setCodigo_sucursal(codigo_sucursal);
					cg2.setFecha_historia(hisc_deteccion_alt_menor_2m_2a
							.getFecha_inicial());
					cg2.setTipo_coordenada(ITipos_coordenada.T_E);
					cg2.setIdentificacion(paciente.getNro_identificacion());
					cg2.setValor("" + coordenadaTallaEdad.getValor());
					cg2.setX("" + coordenadaTallaEdad.getX());
					cg2.setY("" + coordenadaTallaEdad.getY());
					cg2.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS);
	
					// Coordenada (P/T)
					Coordenadas_graficas cg3 = new Coordenadas_graficas();
					cg3.setCodigo_empresa(codigo_empresa);
					cg3.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
							.getCodigo_historia());
					cg3.setCodigo_sucursal(codigo_sucursal);
					cg3.setFecha_historia(hisc_deteccion_alt_menor_2m_2a
							.getFecha_inicial());
					cg3.setTipo_coordenada(ITipos_coordenada.P_T);
					cg3.setIdentificacion(paciente.getNro_identificacion());
					cg3.setValor("" + coordenadaPesoTalla.getValor());
					cg3.setX("" + coordenadaPesoTalla.getX());
					cg3.setY("" + coordenadaPesoTalla.getY());
					cg3.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS);
	
					// Coordenada (PC/E)
					Coordenadas_graficas cg4 = new Coordenadas_graficas();
					cg4.setCodigo_empresa(codigo_empresa);
					cg4.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
							.getCodigo_historia());
					cg4.setCodigo_sucursal(codigo_sucursal);
					cg4.setFecha_historia(hisc_deteccion_alt_menor_2m_2a
							.getFecha_inicial());
					cg4.setTipo_coordenada(ITipos_coordenada.PC_E);
					cg4.setIdentificacion(paciente.getNro_identificacion());
					cg4.setValor("" + coordenadaPerimetroCefalicoEdad.getValor());
					cg4.setX("" + coordenadaPerimetroCefalicoEdad.getX());
					cg4.setY("" + coordenadaPerimetroCefalicoEdad.getY());
					cg4.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS);
	
					ArrayList<Coordenadas_graficas> coordenadas = new ArrayList<Coordenadas_graficas>();
					coordenadas.add(cg1);
					coordenadas.add(cg2);
					coordenadas.add(cg3);
					coordenadas.add(cg4);
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
				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction
						.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);

				getServiceLocator().getHisc_deteccion_alt_menor_2m_2aService()
						.guardarDatos(datos);

				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}

				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
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
			}
		}

	}

	private Hisc_deteccion_alt_menor_2m_2a getBean() {
		hisc_deteccion_alt_menor_2m_2a = new Hisc_deteccion_alt_menor_2m_2a();
		hisc_deteccion_alt_menor_2m_2a.setCodigo_empresa(empresa
				.getCodigo_empresa());
		hisc_deteccion_alt_menor_2m_2a.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());

		hisc_deteccion_alt_menor_2m_2a.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_deteccion_alt_menor_2m_2a
				.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
		hisc_deteccion_alt_menor_2m_2a.setFecha_inicial(new Timestamp(
				infoPacientes.getFecha_inicial().getTime()));
		hisc_deteccion_alt_menor_2m_2a
				.setSintomaticos_respiratorio(rdbSintomaticos_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setSintomaticos_piel(rdbSintomaticos_piel
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setUltimo_update(new Timestamp(
				(new Date()).getTime()));
		hisc_deteccion_alt_menor_2m_2a
				.setNro_ingreso(admision != null ? admision
						.getNro_ingreso() : null);

		hisc_deteccion_alt_menor_2m_2a
				.setNombre_del_acompanante(tbxNombre_del_acompanante
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setParentesco((String) lbxParentesco.getSelectedItem()
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setNombre_del_padre(tbxNombre_del_padre.getValue());
		hisc_deteccion_alt_menor_2m_2a.setEdad_padre((ibxEdad_padre
				.getValue() != null ? ibxEdad_padre.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setNombre_de_la_madre(tbxNombre_de_la_madre.getValue());
		hisc_deteccion_alt_menor_2m_2a.setEdad_madre((ibxEdad_madre
				.getValue() != null ? ibxEdad_madre.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setMotivo_consulta(tbxMotivo_consulta.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setGestion_actual(tbxGestion_actual.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setPuede_beber_pecho(rdbPuede_beber_pecho
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setVomita_todo(rdbVomita_todo
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setConvulsiones(rdbConvulsiones
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_tos_disnea(rdbTiene_tos_disnea
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_sibilancias(rdbTiene_sibilancias
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setSibilancias_previas(rdbSibilancias_previas
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setCuadro_gripal_ultimos_3d(rdbCuadro_gripal_ultimos_3d
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setAntecedente_prematuridad(rdbAntecedente_prematuridad
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_diarrea(rdbTiene_diarrea.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setDiarrea_ultimas_24h(rdbDiarrea_ultimas_24h
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setDiarrea_ultimas_4h(rdbDiarrea_ultimas_4h
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHay_sangre_en_heces(rdbHay_sangre_en_heces
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setTiene_vomito(rdbTiene_vomito
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setVomito_ultimas_4h(rdbVomito_ultimas_4h
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_fiebre_mas_5d(rdbTiene_fiebre_mas_5d
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_fiebre_menos_5d(rdbTiene_fiebre_menos_5d
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setFiebre_mas_38g(rdbFiebre_mas_38g.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setFiebre_mas_39g(rdbFiebre_mas_39g.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setVive_visito_ultimos_15d_zona_dengue(rdbVive_visito_ultimos_15d_zona_dengue
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setVive_visito_ultimos_15d_zona_malria(rdbVive_visito_ultimos_15d_zona_malria
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_otalgia(rdbTiene_otalgia.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_supuracion_oido(rdbTiene_supuracion_oido
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setEpisodios_previos_otitis(rdbEpisodios_previos_otitis
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiene_dolor_garganta(rdbTiene_dolor_garganta
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setCefalea_dolor_retroocular(rdbCefalea_dolor_retroocular
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setMialgias_artralgias(rdbMialgias_artralgias
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setObservaciones_3(tbxObservaciones_3.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setNum_embarazos_g((ibxNum_embarazos_g.getValue() != null ? ibxNum_embarazos_g
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a.setNum_partos_p((ibxNum_partos_p
				.getValue() != null ? ibxNum_partos_p.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setNum_abortos_a((ibxNum_abortos_a.getValue() != null ? ibxNum_abortos_a
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setNum_cesarias_c((ibxNum_cesarias_c.getValue() != null ? ibxNum_cesarias_c
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setNum_mortinatos_v((ibxNum_mortinatos_v.getValue() != null ? ibxNum_mortinatos_v
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a.setRiesgo(rdbRiesgo
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setControl_prenatal(rdbControl_prenatal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setSem_gestacion((ibxSem_gestacion.getValue() != null ? ibxSem_gestacion
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setEdad_madre_al_nacimiento((ibxEdad_madre_al_nacimiento
						.getValue() != null ? ibxEdad_madre_al_nacimiento
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a.setParto(rdbParto
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setUnico_multiple(rdbUnico_multiple.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setInstitucional(rdbInstitucional.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHemoclasificacion(lbxHemoclasificacion
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTsh((dbxTsh.getValue() != null ? dbxTsh.getValue()
						.toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a.setVdrl_materno(lbxVdlr
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setComplicaciones_embarazo_parto(tbxComplicaciones_embarazo_parto
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setPeso_al_nacer_grs((dbxPeso_al_nacer_grs.getValue() != null ? dbxPeso_al_nacer_grs
						.getValue().toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a
				.setTalla_al_nacer_cms((dbxTalla_al_nacer_cms
						.getValue() != null ? dbxTalla_al_nacer_cms
						.getValue().toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a
				.setApgar_al_minuto((dbxApgar_al_minuto.getValue() != null ? dbxApgar_al_minuto
						.getValue() : 0d));
		hisc_deteccion_alt_menor_2m_2a
				.setApgar_5minutos((dbxApgar_5minutos.getValue() != null ? dbxApgar_5minutos
						.getValue() : 0d));
		hisc_deteccion_alt_menor_2m_2a.setReanimacion(rdbReanimacion
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHospitalizacion_neonatal(rdbHospitalizacion_neonatal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setObservaciones_4_1(tbxObservaciones_4_1.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setPatologicos_medicos(tbxPatologicos_medicos
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setPatologicos_quirurgicos(tbxPatologicos_quirurgicos
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setPatologicos_hospitalizaciones(tbxPatologicos_hospitalizaciones
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setPatologicos_medicacion(tbxPatologicos_medicacion
						.getValue());
		hisc_deteccion_alt_menor_2m_2a.setAntecedentes_alergicos(tbxPatologicos_alergicos
				.getValue());	
		hisc_deteccion_alt_menor_2m_2a
				.setRecibe_leche_materna(rdbRecibe_leche_materna
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHa_recibido_hierro_en_los_ultimos_6m(rdbHa_recibido_hierro_en_los_ultimos_6m
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setLactancia_materna_exclusiva(rdbLactancia_materna_exclusiva
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHa_recibido_vitamina_a_en_los_ultimos_6m(rdbHa_recibido_vitamina_a_en_los_ultimos_6m
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setLactancia_materna_nocturna(rdbLactancia_materna_nocturna
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHa_recibido_albendazol_en_los_ultimos_6m(rdbHa_recibido_albendazol_en_los_ultimos_6m
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setMenos_6m_recibe_otras_leches_alimentos(rdbMenos_6m_recibe_otras_leches_alimentos
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setObservaciones_4_4(tbxObservaciones_4_4.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setDolor_masticar(rdbDolor_masticar.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setLimpieza_boca_medio_dia(rdbLimpieza_boca_medio_dia
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setUtiliza_crema(rdbUtiliza_crema.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setDolor_en_diente(rdbDolor_en_diente
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setLimpieza_boca_noche(rdbLimpieza_boca_noche
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setUtiliza_chupo_o_biberon(rdbUtiliza_chupo_o_biberon
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setPadre_hnos_caries(rdbPadre_hnos_caries
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setLe_limpia_los_dientes(rdbLe_limpia_los_dientes
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setAsiste_a_odontologia(rdbAsiste_a_odontologia
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setLimpieza_boca_manana(rdbLimpieza_boca_manana
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setUtiliza_cepillo(rdbUtiliza_cepillo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setBcg(rdbBcg.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setVop_2(rdbVop_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setVop_3(rdbVop_3
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setNeumococo_3(rdbNeumococo_3
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHepatitis_b_rn(rdbHepatitis_b_rn.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setPenta_2(rdbPenta_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setPenta_3(rdbPenta_3
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setTriple_viral(rdbTriple_viral
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setVop_1(rdbVop_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setNeumococo_2(rdbNeumococo_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setInfluenza_1(rdbInfluenza_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setFiebre_amarilla(rdbFiebre_amarilla
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setPenta_1(rdbPenta_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setRotavirus_2(rdbRotavirus_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setInfluenza_2(rdbInfluenza_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setDpt_r1(rdbDpt_r1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setNeumococo_1(rdbNeumococo_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setVop_r1(rdbVop_r1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setRotavirus_1(rdbRotavirus_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setHepatitis_a(rdbHepatitis_a
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setNum_hermanos_vivos((ibxNum_hermanos_vivos
						.getValue() != null ? ibxNum_hermanos_vivos
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setNum_hermanos_desnutridos_menor_5anos((ibxNum_hermanos_desnutridos_menor_5anos
						.getValue() != null ? ibxNum_hermanos_desnutridos_menor_5anos
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setNum_hermanos_muertos_menor_5anos((ibxNum_hermanos_muertos_menor_5anos
						.getValue() != null ? ibxNum_hermanos_muertos_menor_5anos
						.getValue() : 0));
		hisc_deteccion_alt_menor_2m_2a
				.setNum_hermanos_muertos_menor_5anos_causa(tbxNum_hermanos_muertos_menor_5anos_causa
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setSon_parientes_los_padres(rdbSon_parientes_los_padres
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setFamilia_con_problema_mental_o_fisico(rdbFamilia_con_problema_mental_o_fisico
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setPaterno_medicos(tbxPaterno_medicos.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setPaterno_alergico(tbxPaterno_alergico.getValue());
		hisc_deteccion_alt_menor_2m_2a.setPaterno_talla(String
				.valueOf(dbxMaterno_talla.getValue()));
		hisc_deteccion_alt_menor_2m_2a
				.setMaterno_medicos(tbxMaterno_medicos.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setMaterno_alergico(tbxMaterno_alergico.getValue());
		hisc_deteccion_alt_menor_2m_2a.setMaterno_talla(String
				.valueOf(dbxPaterno_talla.getValue()));
		hisc_deteccion_alt_menor_2m_2a
				.setOtros_5(tbxOtros_5.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setTestigo_relata_maltrato(rdbTestigo_relata_maltrato
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setEsta_descuidado_nino_salud(rdbEsta_descuidado_nino_salud
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setComportamiento_anormal_padres(rdbComportamiento_anormal_padres
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setEsta_descuidado_nino_higiene(rdbEsta_descuidado_nino_higiene
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setActitud_anormal_nino(rdbActitud_anormal_nino
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setSe_le_pega_nino(rdbSe_le_pega_nino
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setPeso_grs((dbxExamen_fisico_peso.getValue() != null ? dbxExamen_fisico_peso
						.getValue().toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a
				.setTalla_cm((dbxExamen_fisico_talla.getValue() != null ? dbxExamen_fisico_talla
						.getValue().toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a
				.setPerimetro_cefalico_cm((dbxExamen_fisico_perimetro_cflico
						.getValue() != null ? dbxExamen_fisico_perimetro_cflico
						.getValue().toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a.setFc_min((dbxExamen_fisico_fc
				.getValue() != null ? dbxExamen_fisico_fc.getValue()
				.toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a.setFr_min((dbxExamen_fisico_fr
				.getValue() != null ? dbxExamen_fisico_fr.getValue()
				.toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a
				.setTemperatura_gc((dbxExamen_fisico_temp.getValue() != null ? dbxExamen_fisico_temp
						.getValue().toString() : "0.00"));
		hisc_deteccion_alt_menor_2m_2a
				.setEmaciacion_visible(rdbEmaciacion_visible
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setEdema_ambos_pies(rdbEdema_ambos_pies
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTendencia_peso(rdbTendencia_peso.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setLetargico(rdbLetargico
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setBebe_avidamente(rdbBebe_avidamente
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTimpano_rojo_abombado(rdbTimpano_rojo_abombado
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setSomnoliento(rdbSomnoliento
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setOjos_hundidos(rdbOjos_hundidos.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setSupuracion_oido(rdbSupuracion_oido
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setConfuso(rdbConfuso
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setSigno_del_pliegue_cutaneo(rdbSigno_del_pliegue_cutaneo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setGanglio_cuello_doloroso(rdbGanglio_cuello_doloroso
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setAgitado_irritable(rdbAgitado_irritable
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setManifestaciones_sangrado(rdbManifestaciones_sangrado
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setAmigdalas_eritematosas(rdbAmigdalas_eritematosas
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setRigidez_de_nuca(rdbRigidez_de_nuca
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setErupcion_cutanea_generalizada(rdbErupcion_cutanea_generalizada
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setExudado_en_amigdalas(rdbExudado_en_amigdalas
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiraje_subcostal(rdbTiraje_subcostal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setPrueba_torniquete_positiva(rdbPrueba_torniquete_positiva
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setPalidez_palmar(rdbPalidez_palmar.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTiraje_supraclavicular(rdbTiraje_supraclavicular
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setHepatomegalia(rdbHepatomegalia.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setPalidez_conjuntival(rdbPalidez_conjuntival
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setEstridor(rdbEstridor
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setPulso_rapido_y_fino(rdbPulso_rapido_y_fino
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setQuemaduras(rdbQuemaduras
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setSibilancia(rdbSibilancia
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setLlenado_capilar_mayor_2seg(rdbLlenado_capilar_mayor_2seg
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setLaceraciones(rdbLaceraciones
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setApnea(rdbApnea
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setAscitis(rdbAscitis
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setMordiscos(rdbMordiscos
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setBebe_mal_o_no_puede(rdbBebe_mal_o_no_puede
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTumefaccion_retroauricular(rdbTumefaccion_retroauricular
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setCicatrices(rdbCicatrices
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setCabeza_cara(rdbCabeza_cara
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setCuello(rdbCuello
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setAbdomen(rdbAbdomen
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setOrgano_de_los_sentidos(rdbOrgano_de_los_sentidos
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTorax_cardiopulmonar(rdbTorax_cardiopulmonar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setAbdomen_masas(rdbAbdomen_masas.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setRojo_retiniano(rdbRojo_retiniano.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTorax_cardiopulmonar_ritmo_cardiaco(rdbTorax_cardiopulmonar_ritmo_cardiaco
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setAbdomen_megalias(rdbAbdomen_megalias
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setFijacion_seguimiento(rdbFijacion_seguimiento
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setTorax_cardiopulmonar_soplo(rdbTorax_cardiopulmonar_soplo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setGenito_urinario(rdbGenito_urinario
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setOclusion_alternante(rdbOclusion_alternante
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setColumna_vertebral(rdbColumna_vertebral
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setExtremidades(rdbExtremidades
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setBoca(rdbBoca
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setPiel_anexos(rdbPiel_anexos
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a.setNeurologico(rdbNeurologico
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setObservaciones_7_2(tbxObservaciones_7_2.getValue());

		hisc_deteccion_alt_menor_2m_2a
				.setDiagnostico_crecimiento(rdbDiagnostico_crecimiento
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setDiagnostico_desarrollo(rdbDiagnostico_desarrollo
						.getSelectedItem().getValue().toString());

		hisc_deteccion_alt_menor_2m_2a
				.setMaltrato_fisico(chbMaltrato_fisico.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setMaltrato_emocional(chbMaltrato_emocional
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setSospecha_de_abuso_sexual(chbSospecha_de_abuso_sexual
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setNo_hay_sospecha_de_maltrato(chbNo_hay_sospecha_de_maltrato
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a.setAnalisis_9(tbxAnalisis_9
				.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setObservaciones_vacunales(tbxObservaciones_vacunales
						.getValue());
		hisc_deteccion_alt_menor_2m_2a
				.setEstimulo_factores_protectores(chbEstimulo_factores_protectores
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setRecomendaciones_de_buen_trato(chbRecomendaciones_de_buen_trato
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setOrientacion_vacunacion(chbOrientacion_vacunacion
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setLactancia_materna_complementa(chbLactancia_materna_complementa
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setImportancia_asistencia_controles(chbImportancia_asistencia_controles
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setRecomendaciones_higienicas(chbRecomendaciones_higienicas
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setRecomendaciones_para_el_desarrollo(chbRecomendaciones_para_el_desarrollo
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setRecomendaciones_en_salud_oral(chbRecomendaciones_en_salud_oral
						.isChecked());
		hisc_deteccion_alt_menor_2m_2a
				.setNecesita_prescripcion_de_vitamina_a(rdbNecesita_prescripcion_de_vitamina_a
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setNecesita_prescripcion_de_albendazol(rdbNecesita_prescripcion_de_albendazol
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setNecesita_prescripcion_de_hierro(rdbNecesita_prescripcion_de_hierro
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setNecesita_prescripcion_de_zinc(rdbNecesita_prescripcion_de_zinc
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_2m_2a
				.setRecomendaciones_10(tbxRecomendaciones_10.getValue());
		hisc_deteccion_alt_menor_2m_2a.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_menor_2m_2a.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_menor_2m_2a.setCreacion_user(usuarios
				.getCodigo().toString());
		hisc_deteccion_alt_menor_2m_2a.setDelete_date(null);
		hisc_deteccion_alt_menor_2m_2a.setUltimo_user(usuarios
				.getCodigo().toString());
		hisc_deteccion_alt_menor_2m_2a.setDelete_user(null);

		hisc_deteccion_alt_menor_2m_2a.setTipo_historia(tipo_historia);
		hisc_deteccion_alt_menor_2m_2a
				.setCodigo_historia_anterior(codigo_historia_anterior);
		

		hisc_deteccion_alt_menor_2m_2a.setObservaciones_cabeza(tbxObservaciones_cabeza.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_sentidos(tbxObservaciones_sentidos.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_rojo(tbxObservaciones_rojo.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_cuello(tbxObservaciones_cuello.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_torax(tbxObservaciones_torax.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_cardiaco(tbxObservaciones_cardiaco.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_soplo(tbxObservaciones_soplo.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_abdomen(tbxObservaciones_abdomen.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_masas(tbxObservaciones_masas.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_megalias(tbxObservaciones_megalias.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_genito(tbxObservaciones_genito.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_columna(tbxObservaciones_columna.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_extremidades(tbxObservaciones_extremidades.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_piel(tbxObservaciones_piel.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_neurologico(tbxObservaciones_neurologico.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_fijacion(tbxObservaciones_fijacion.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_oclusion(tbxObservaciones_oclusion.getValue());
		hisc_deteccion_alt_menor_2m_2a.setObservaciones_boca(tbxObservaciones_boca.getValue());
		
		
		return hisc_deteccion_alt_menor_2m_2a;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a = (Hisc_deteccion_alt_menor_2m_2a) obj;
		try {
			this.nro_ingreso_admision = hisc_deteccion_alt_menor_2m_2a
					.getNro_ingreso();

			infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_deteccion_alt_menor_2m_2a
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_deteccion_alt_menor_2m_2a.getUltimo_update());
			initMostrar_datos(hisc_deteccion_alt_menor_2m_2a);
			cargarInformacion_paciente();

			onMostrarModuloRemisiones();
			cargarRemisionInterna(hisc_deteccion_alt_menor_2m_2a);

			Utilidades.seleccionarListitem(lbxParentesco,
					hisc_deteccion_alt_menor_2m_2a.getParentesco());

			tbxNombre_del_acompanante.setValue(hisc_deteccion_alt_menor_2m_2a
					.getNombre_del_acompanante());
			tbxNombre_del_padre.setValue(hisc_deteccion_alt_menor_2m_2a
					.getNombre_del_padre());
			ibxEdad_padre.setValue(hisc_deteccion_alt_menor_2m_2a
					.getEdad_padre());
			tbxNombre_de_la_madre.setValue(hisc_deteccion_alt_menor_2m_2a
					.getNombre_de_la_madre());
			ibxEdad_madre.setValue(hisc_deteccion_alt_menor_2m_2a
					.getEdad_madre());
			tbxMotivo_consulta.setValue(hisc_deteccion_alt_menor_2m_2a
					.getMotivo_consulta());
			tbxGestion_actual.setValue(hisc_deteccion_alt_menor_2m_2a
					.getGestion_actual());

			Utilidades.seleccionarRadio(rdbPuede_beber_pecho,
					hisc_deteccion_alt_menor_2m_2a.getPuede_beber_pecho());
			Utilidades.seleccionarRadio(rdbVomita_todo,
					hisc_deteccion_alt_menor_2m_2a.getVomita_todo());
			Utilidades.seleccionarRadio(rdbConvulsiones,
					hisc_deteccion_alt_menor_2m_2a.getConvulsiones());
			Utilidades.seleccionarRadio(rdbTiene_tos_disnea,
					hisc_deteccion_alt_menor_2m_2a.getTiene_tos_disnea());
			Utilidades.seleccionarRadio(rdbTiene_sibilancias,
					hisc_deteccion_alt_menor_2m_2a.getTiene_sibilancias());
			Utilidades.seleccionarRadio(rdbSibilancias_previas,
					hisc_deteccion_alt_menor_2m_2a.getSibilancias_previas());
			Utilidades.seleccionarRadio(rdbCuadro_gripal_ultimos_3d,
					hisc_deteccion_alt_menor_2m_2a
							.getCuadro_gripal_ultimos_3d());
			Utilidades.seleccionarRadio(rdbAntecedente_prematuridad,
					hisc_deteccion_alt_menor_2m_2a
							.getAntecedente_prematuridad());
			Utilidades.seleccionarRadio(rdbTiene_diarrea,
					hisc_deteccion_alt_menor_2m_2a.getTiene_diarrea());
			Utilidades.seleccionarRadio(rdbDiarrea_ultimas_24h,
					hisc_deteccion_alt_menor_2m_2a.getDiarrea_ultimas_24h());
			Utilidades.seleccionarRadio(rdbDiarrea_ultimas_4h,
					hisc_deteccion_alt_menor_2m_2a.getDiarrea_ultimas_4h());
			Utilidades.seleccionarRadio(rdbHay_sangre_en_heces,
					hisc_deteccion_alt_menor_2m_2a.getHay_sangre_en_heces());
			Utilidades.seleccionarRadio(rdbTiene_vomito,
					hisc_deteccion_alt_menor_2m_2a.getTiene_vomito());
			Utilidades.seleccionarRadio(rdbVomito_ultimas_4h,
					hisc_deteccion_alt_menor_2m_2a.getVomito_ultimas_4h());
			Utilidades.seleccionarRadio(rdbTiene_fiebre_mas_5d,
					hisc_deteccion_alt_menor_2m_2a.getTiene_fiebre_mas_5d());
			Utilidades.seleccionarRadio(rdbTiene_fiebre_menos_5d,
					hisc_deteccion_alt_menor_2m_2a.getTiene_fiebre_menos_5d());
			Utilidades.seleccionarRadio(rdbFiebre_mas_38g,
					hisc_deteccion_alt_menor_2m_2a.getFiebre_mas_38g());
			Utilidades.seleccionarRadio(rdbFiebre_mas_39g,
					hisc_deteccion_alt_menor_2m_2a.getFiebre_mas_39g());
			Utilidades.seleccionarRadio(rdbVive_visito_ultimos_15d_zona_dengue,
					hisc_deteccion_alt_menor_2m_2a
							.getVive_visito_ultimos_15d_zona_dengue());
			Utilidades.seleccionarRadio(rdbVive_visito_ultimos_15d_zona_malria,
					hisc_deteccion_alt_menor_2m_2a
							.getVive_visito_ultimos_15d_zona_malria());
			Utilidades.seleccionarRadio(rdbTiene_otalgia,
					hisc_deteccion_alt_menor_2m_2a.getTiene_otalgia());
			Utilidades.seleccionarRadio(rdbTiene_supuracion_oido,
					hisc_deteccion_alt_menor_2m_2a.getTiene_supuracion_oido());
			Utilidades.seleccionarRadio(rdbEpisodios_previos_otitis,
					hisc_deteccion_alt_menor_2m_2a
							.getEpisodios_previos_otitis());
			Utilidades.seleccionarRadio(rdbTiene_dolor_garganta,
					hisc_deteccion_alt_menor_2m_2a.getTiene_dolor_garganta());
			Utilidades.seleccionarRadio(rdbCefalea_dolor_retroocular,
					hisc_deteccion_alt_menor_2m_2a
							.getCefalea_dolor_retroocular());
			Utilidades.seleccionarRadio(rdbMialgias_artralgias,
					hisc_deteccion_alt_menor_2m_2a.getMialgias_artralgias());
			tbxObservaciones_3.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_3());
			ibxNum_embarazos_g.setValue(Integer
					.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getNum_embarazos_g()));
			ibxNum_partos_p.setValue(Integer
					.valueOf(hisc_deteccion_alt_menor_2m_2a.getNum_partos_p()));
			ibxNum_abortos_a
					.setValue(Integer.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getNum_abortos_a()));
			ibxNum_cesarias_c
					.setValue(Integer.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getNum_cesarias_c()));
			ibxNum_mortinatos_v.setValue(Integer
					.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getNum_mortinatos_v()));
			Utilidades.seleccionarRadio(rdbRiesgo,
					hisc_deteccion_alt_menor_2m_2a.getRiesgo());
			Utilidades.seleccionarRadio(rdbControl_prenatal,
					hisc_deteccion_alt_menor_2m_2a.getControl_prenatal());
			ibxSem_gestacion
					.setValue(Integer.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getSem_gestacion()));
			ibxEdad_madre_al_nacimiento.setValue(Integer
					.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getEdad_madre_al_nacimiento()));
			Utilidades.seleccionarRadio(rdbParto,
					hisc_deteccion_alt_menor_2m_2a.getParto());
			Utilidades.seleccionarRadio(rdbUnico_multiple,
					hisc_deteccion_alt_menor_2m_2a.getUnico_multiple());
			Utilidades.seleccionarRadio(rdbInstitucional,
					hisc_deteccion_alt_menor_2m_2a.getInstitucional());
			Utilidades.seleccionarListitem(lbxHemoclasificacion,
					hisc_deteccion_alt_menor_2m_2a.getHemoclasificacion());
			dbxTsh.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_menor_2m_2a.getTsh()));
			Utilidades.seleccionarListitem(lbxVdlr,
					hisc_deteccion_alt_menor_2m_2a.getVdrl_materno());

			tbxComplicaciones_embarazo_parto
					.setValue(hisc_deteccion_alt_menor_2m_2a
							.getComplicaciones_embarazo_parto());
			dbxPeso_al_nacer_grs.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getPeso_al_nacer_grs()));
			dbxTalla_al_nacer_cms.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getTalla_al_nacer_cms()));
			dbxApgar_al_minuto.setValue(hisc_deteccion_alt_menor_2m_2a
					.getApgar_al_minuto());
			dbxApgar_5minutos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getApgar_5minutos());
			Utilidades.seleccionarRadio(rdbReanimacion,
					hisc_deteccion_alt_menor_2m_2a.getReanimacion());
			Utilidades.seleccionarRadio(rdbHospitalizacion_neonatal,
					hisc_deteccion_alt_menor_2m_2a
							.getHospitalizacion_neonatal());
			tbxObservaciones_4_1.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_4_1());
			tbxPatologicos_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPatologicos_medicos());
			tbxPatologicos_quirurgicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPatologicos_quirurgicos());
			tbxPatologicos_hospitalizaciones
					.setValue(hisc_deteccion_alt_menor_2m_2a
							.getPatologicos_hospitalizaciones());
			tbxPatologicos_medicacion.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPatologicos_medicacion());
			tbxPatologicos_alergicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getAntecedentes_alergicos());
			Utilidades.seleccionarRadio(rdbRecibe_leche_materna,
					hisc_deteccion_alt_menor_2m_2a.getRecibe_leche_materna());
			Utilidades.seleccionarRadio(
					rdbHa_recibido_hierro_en_los_ultimos_6m,
					hisc_deteccion_alt_menor_2m_2a
							.getHa_recibido_hierro_en_los_ultimos_6m());
			Utilidades.seleccionarRadio(rdbLactancia_materna_exclusiva,
					hisc_deteccion_alt_menor_2m_2a
							.getLactancia_materna_exclusiva());
			Utilidades.seleccionarRadio(
					rdbHa_recibido_vitamina_a_en_los_ultimos_6m,
					hisc_deteccion_alt_menor_2m_2a
							.getHa_recibido_vitamina_a_en_los_ultimos_6m());
			Utilidades.seleccionarRadio(rdbLactancia_materna_nocturna,
					hisc_deteccion_alt_menor_2m_2a
							.getLactancia_materna_nocturna());
			Utilidades.seleccionarRadio(
					rdbHa_recibido_albendazol_en_los_ultimos_6m,
					hisc_deteccion_alt_menor_2m_2a
							.getHa_recibido_albendazol_en_los_ultimos_6m());
			Utilidades.seleccionarRadio(
					rdbMenos_6m_recibe_otras_leches_alimentos,
					hisc_deteccion_alt_menor_2m_2a
							.getMenos_6m_recibe_otras_leches_alimentos());
			tbxObservaciones_4_4.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_4_4());
			Utilidades.seleccionarRadio(rdbDolor_masticar,
					hisc_deteccion_alt_menor_2m_2a.getDolor_masticar());
			Utilidades
					.seleccionarRadio(rdbLimpieza_boca_medio_dia,
							hisc_deteccion_alt_menor_2m_2a
									.getLimpieza_boca_medio_dia());
			Utilidades.seleccionarRadio(rdbUtiliza_crema,
					hisc_deteccion_alt_menor_2m_2a.getUtiliza_crema());
			Utilidades.seleccionarRadio(rdbDolor_en_diente,
					hisc_deteccion_alt_menor_2m_2a.getDolor_en_diente());
			Utilidades.seleccionarRadio(rdbLimpieza_boca_noche,
					hisc_deteccion_alt_menor_2m_2a.getLimpieza_boca_noche());
			Utilidades
					.seleccionarRadio(rdbUtiliza_chupo_o_biberon,
							hisc_deteccion_alt_menor_2m_2a
									.getUtiliza_chupo_o_biberon());
			Utilidades.seleccionarRadio(rdbPadre_hnos_caries,
					hisc_deteccion_alt_menor_2m_2a.getPadre_hnos_caries());
			Utilidades.seleccionarRadio(rdbLe_limpia_los_dientes,
					hisc_deteccion_alt_menor_2m_2a.getLe_limpia_los_dientes());
			Utilidades.seleccionarRadio(rdbAsiste_a_odontologia,
					hisc_deteccion_alt_menor_2m_2a.getAsiste_a_odontologia());
			Utilidades.seleccionarRadio(rdbLimpieza_boca_manana,
					hisc_deteccion_alt_menor_2m_2a.getLimpieza_boca_manana());
			Utilidades.seleccionarRadio(rdbUtiliza_cepillo,
					hisc_deteccion_alt_menor_2m_2a.getUtiliza_cepillo());
			Utilidades.seleccionarRadio(rdbBcg,
					hisc_deteccion_alt_menor_2m_2a.getBcg());
			Utilidades.seleccionarRadio(rdbVop_2,
					hisc_deteccion_alt_menor_2m_2a.getVop_2());
			Utilidades.seleccionarRadio(rdbVop_3,
					hisc_deteccion_alt_menor_2m_2a.getVop_3());
			Utilidades.seleccionarRadio(rdbNeumococo_3,
					hisc_deteccion_alt_menor_2m_2a.getNeumococo_3());
			Utilidades.seleccionarRadio(rdbHepatitis_b_rn,
					hisc_deteccion_alt_menor_2m_2a.getHepatitis_b_rn());
			Utilidades.seleccionarRadio(rdbPenta_2,
					hisc_deteccion_alt_menor_2m_2a.getPenta_2());
			Utilidades.seleccionarRadio(rdbPenta_3,
					hisc_deteccion_alt_menor_2m_2a.getPenta_3());
			Utilidades.seleccionarRadio(rdbTriple_viral,
					hisc_deteccion_alt_menor_2m_2a.getTriple_viral());
			Utilidades.seleccionarRadio(rdbVop_1,
					hisc_deteccion_alt_menor_2m_2a.getVop_1());
			Utilidades.seleccionarRadio(rdbNeumococo_2,
					hisc_deteccion_alt_menor_2m_2a.getNeumococo_2());
			Utilidades.seleccionarRadio(rdbInfluenza_1,
					hisc_deteccion_alt_menor_2m_2a.getInfluenza_1());
			Utilidades.seleccionarRadio(rdbFiebre_amarilla,
					hisc_deteccion_alt_menor_2m_2a.getFiebre_amarilla());
			Utilidades.seleccionarRadio(rdbPenta_1,
					hisc_deteccion_alt_menor_2m_2a.getPenta_1());
			Utilidades.seleccionarRadio(rdbRotavirus_2,
					hisc_deteccion_alt_menor_2m_2a.getRotavirus_2());
			Utilidades.seleccionarRadio(rdbInfluenza_2,
					hisc_deteccion_alt_menor_2m_2a.getInfluenza_2());
			Utilidades.seleccionarRadio(rdbDpt_r1,
					hisc_deteccion_alt_menor_2m_2a.getDpt_r1());
			Utilidades.seleccionarRadio(rdbNeumococo_1,
					hisc_deteccion_alt_menor_2m_2a.getNeumococo_1());
			Utilidades.seleccionarRadio(rdbVop_r1,
					hisc_deteccion_alt_menor_2m_2a.getVop_r1());
			Utilidades.seleccionarRadio(rdbRotavirus_1,
					hisc_deteccion_alt_menor_2m_2a.getRotavirus_1());
			Utilidades.seleccionarRadio(rdbHepatitis_a,
					hisc_deteccion_alt_menor_2m_2a.getHepatitis_a());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					hisc_deteccion_alt_menor_2m_2a
							.getSintomaticos_respiratorio());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					hisc_deteccion_alt_menor_2m_2a.getSintomaticos_piel());
			ibxNum_hermanos_vivos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getNum_hermanos_vivos());
			ibxNum_hermanos_desnutridos_menor_5anos
					.setValue(hisc_deteccion_alt_menor_2m_2a
							.getNum_hermanos_desnutridos_menor_5anos());
			ibxNum_hermanos_muertos_menor_5anos
					.setValue(hisc_deteccion_alt_menor_2m_2a
							.getNum_hermanos_muertos_menor_5anos());
			tbxNum_hermanos_muertos_menor_5anos_causa
					.setValue(hisc_deteccion_alt_menor_2m_2a
							.getNum_hermanos_muertos_menor_5anos_causa());
			Utilidades.seleccionarRadio(rdbSon_parientes_los_padres,
					hisc_deteccion_alt_menor_2m_2a
							.getSon_parientes_los_padres());
			Utilidades.seleccionarRadio(
					rdbFamilia_con_problema_mental_o_fisico,
					hisc_deteccion_alt_menor_2m_2a
							.getFamilia_con_problema_mental_o_fisico());
			tbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPaterno_medicos());
			tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPaterno_alergico());
			dbxMaterno_talla.setValue(ConvertidorDatosUtil
					.convertirDato((hisc_deteccion_alt_menor_2m_2a
							.getPaterno_talla())));
			tbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getMaterno_medicos());
			tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
					.getMaterno_alergico());
			dbxPaterno_talla.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_menor_2m_2a
							.getMaterno_talla()));
			tbxOtros_5.setValue(hisc_deteccion_alt_menor_2m_2a.getOtros_5());
			Utilidades
					.seleccionarRadio(rdbTestigo_relata_maltrato,
							hisc_deteccion_alt_menor_2m_2a
									.getTestigo_relata_maltrato());
			Utilidades.seleccionarRadio(rdbEsta_descuidado_nino_salud,
					hisc_deteccion_alt_menor_2m_2a
							.getEsta_descuidado_nino_salud());
			Utilidades.seleccionarRadio(rdbComportamiento_anormal_padres,
					hisc_deteccion_alt_menor_2m_2a
							.getComportamiento_anormal_padres());
			Utilidades.seleccionarRadio(rdbEsta_descuidado_nino_higiene,
					hisc_deteccion_alt_menor_2m_2a
							.getEsta_descuidado_nino_higiene());
			Utilidades.seleccionarRadio(rdbActitud_anormal_nino,
					hisc_deteccion_alt_menor_2m_2a.getActitud_anormal_nino());
			Utilidades.seleccionarRadio(rdbSe_le_pega_nino,
					hisc_deteccion_alt_menor_2m_2a.getSe_le_pega_nino());
			dbxExamen_fisico_peso.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_2m_2a.getPeso_grs()));
			dbxExamen_fisico_talla.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_2m_2a.getTalla_cm()));
			dbxExamen_fisico_perimetro_cflico.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_2m_2a
							.getPerimetro_cefalico_cm()));
			dbxExamen_fisico_fc.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_2m_2a.getFc_min()));
			dbxExamen_fisico_fr.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_2m_2a.getFr_min()));
			dbxExamen_fisico_temp.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_menor_2m_2a
							.getTemperatura_gc()));
			Utilidades.seleccionarRadio(rdbEmaciacion_visible,
					hisc_deteccion_alt_menor_2m_2a.getEmaciacion_visible());
			Utilidades.seleccionarRadio(rdbEdema_ambos_pies,
					hisc_deteccion_alt_menor_2m_2a.getEdema_ambos_pies());
			Utilidades.seleccionarRadio(rdbTendencia_peso,
					hisc_deteccion_alt_menor_2m_2a.getTendencia_peso());
			Utilidades.seleccionarRadio(rdbLetargico,
					hisc_deteccion_alt_menor_2m_2a.getLetargico());
			Utilidades.seleccionarRadio(rdbBebe_avidamente,
					hisc_deteccion_alt_menor_2m_2a.getBebe_avidamente());
			Utilidades.seleccionarRadio(rdbTimpano_rojo_abombado,
					hisc_deteccion_alt_menor_2m_2a.getTimpano_rojo_abombado());
			Utilidades.seleccionarRadio(rdbSomnoliento,
					hisc_deteccion_alt_menor_2m_2a.getSomnoliento());
			Utilidades.seleccionarRadio(rdbOjos_hundidos,
					hisc_deteccion_alt_menor_2m_2a.getOjos_hundidos());
			Utilidades.seleccionarRadio(rdbSupuracion_oido,
					hisc_deteccion_alt_menor_2m_2a.getSupuracion_oido());
			Utilidades.seleccionarRadio(rdbConfuso,
					hisc_deteccion_alt_menor_2m_2a.getConfuso());
			Utilidades.seleccionarRadio(rdbSigno_del_pliegue_cutaneo,
					hisc_deteccion_alt_menor_2m_2a
							.getSigno_del_pliegue_cutaneo());
			Utilidades
					.seleccionarRadio(rdbGanglio_cuello_doloroso,
							hisc_deteccion_alt_menor_2m_2a
									.getGanglio_cuello_doloroso());
			Utilidades.seleccionarRadio(rdbAgitado_irritable,
					hisc_deteccion_alt_menor_2m_2a.getAgitado_irritable());
			Utilidades.seleccionarRadio(rdbManifestaciones_sangrado,
					hisc_deteccion_alt_menor_2m_2a
							.getManifestaciones_sangrado());
			Utilidades.seleccionarRadio(rdbAmigdalas_eritematosas,
					hisc_deteccion_alt_menor_2m_2a.getAmigdalas_eritematosas());
			Utilidades.seleccionarRadio(rdbRigidez_de_nuca,
					hisc_deteccion_alt_menor_2m_2a.getRigidez_de_nuca());
			Utilidades.seleccionarRadio(rdbErupcion_cutanea_generalizada,
					hisc_deteccion_alt_menor_2m_2a
							.getErupcion_cutanea_generalizada());
			Utilidades.seleccionarRadio(rdbExudado_en_amigdalas,
					hisc_deteccion_alt_menor_2m_2a.getExudado_en_amigdalas());
			Utilidades.seleccionarRadio(rdbTiraje_subcostal,
					hisc_deteccion_alt_menor_2m_2a.getTiraje_subcostal());
			Utilidades.seleccionarRadio(rdbPrueba_torniquete_positiva,
					hisc_deteccion_alt_menor_2m_2a
							.getPrueba_torniquete_positiva());
			Utilidades.seleccionarRadio(rdbPalidez_palmar,
					hisc_deteccion_alt_menor_2m_2a.getPalidez_palmar());
			Utilidades.seleccionarRadio(rdbTiraje_supraclavicular,
					hisc_deteccion_alt_menor_2m_2a.getTiraje_supraclavicular());
			Utilidades.seleccionarRadio(rdbHepatomegalia,
					hisc_deteccion_alt_menor_2m_2a.getHepatomegalia());
			Utilidades.seleccionarRadio(rdbPalidez_conjuntival,
					hisc_deteccion_alt_menor_2m_2a.getPalidez_conjuntival());
			Utilidades.seleccionarRadio(rdbEstridor,
					hisc_deteccion_alt_menor_2m_2a.getEstridor());
			Utilidades.seleccionarRadio(rdbPulso_rapido_y_fino,
					hisc_deteccion_alt_menor_2m_2a.getPulso_rapido_y_fino());
			Utilidades.seleccionarRadio(rdbQuemaduras,
					hisc_deteccion_alt_menor_2m_2a.getQuemaduras());
			Utilidades.seleccionarRadio(rdbSibilancia,
					hisc_deteccion_alt_menor_2m_2a.getSibilancia());
			Utilidades.seleccionarRadio(rdbLlenado_capilar_mayor_2seg,
					hisc_deteccion_alt_menor_2m_2a
							.getLlenado_capilar_mayor_2seg());
			Utilidades.seleccionarRadio(rdbLaceraciones,
					hisc_deteccion_alt_menor_2m_2a.getLaceraciones());
			Utilidades.seleccionarRadio(rdbApnea,
					hisc_deteccion_alt_menor_2m_2a.getApnea());
			Utilidades.seleccionarRadio(rdbAscitis,
					hisc_deteccion_alt_menor_2m_2a.getAscitis());
			Utilidades.seleccionarRadio(rdbMordiscos,
					hisc_deteccion_alt_menor_2m_2a.getMordiscos());
			Utilidades.seleccionarRadio(rdbBebe_mal_o_no_puede,
					hisc_deteccion_alt_menor_2m_2a.getBebe_mal_o_no_puede());
			Utilidades.seleccionarRadio(rdbTumefaccion_retroauricular,
					hisc_deteccion_alt_menor_2m_2a
							.getTumefaccion_retroauricular());
			Utilidades.seleccionarRadio(rdbCicatrices,
					hisc_deteccion_alt_menor_2m_2a.getCicatrices());
			Utilidades.seleccionarRadio(rdbCabeza_cara,
					hisc_deteccion_alt_menor_2m_2a.getCabeza_cara());
			Utilidades.seleccionarRadio(rdbCuello,
					hisc_deteccion_alt_menor_2m_2a.getCuello());
			Utilidades.seleccionarRadio(rdbAbdomen,
					hisc_deteccion_alt_menor_2m_2a.getAbdomen());
			Utilidades.seleccionarRadio(rdbOrgano_de_los_sentidos,
					hisc_deteccion_alt_menor_2m_2a.getOrgano_de_los_sentidos());
			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar,
					hisc_deteccion_alt_menor_2m_2a.getTorax_cardiopulmonar());
			Utilidades.seleccionarRadio(rdbAbdomen_masas,
					hisc_deteccion_alt_menor_2m_2a.getAbdomen_masas());
			Utilidades.seleccionarRadio(rdbRojo_retiniano,
					hisc_deteccion_alt_menor_2m_2a.getRojo_retiniano());
			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar_ritmo_cardiaco,
					hisc_deteccion_alt_menor_2m_2a
							.getTorax_cardiopulmonar_ritmo_cardiaco());
			Utilidades.seleccionarRadio(rdbAbdomen_megalias,
					hisc_deteccion_alt_menor_2m_2a.getAbdomen_megalias());
			Utilidades.seleccionarRadio(rdbFijacion_seguimiento,
					hisc_deteccion_alt_menor_2m_2a.getFijacion_seguimiento());
			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar_soplo,
					hisc_deteccion_alt_menor_2m_2a
							.getTorax_cardiopulmonar_soplo());
			Utilidades.seleccionarRadio(rdbGenito_urinario,
					hisc_deteccion_alt_menor_2m_2a.getGenito_urinario());
			Utilidades.seleccionarRadio(rdbOclusion_alternante,
					hisc_deteccion_alt_menor_2m_2a.getOclusion_alternante());
			Utilidades.seleccionarRadio(rdbColumna_vertebral,
					hisc_deteccion_alt_menor_2m_2a.getColumna_vertebral());
			Utilidades.seleccionarRadio(rdbExtremidades,
					hisc_deteccion_alt_menor_2m_2a.getExtremidades());
			Utilidades.seleccionarRadio(rdbBoca,
					hisc_deteccion_alt_menor_2m_2a.getBoca());
			Utilidades.seleccionarRadio(rdbPiel_anexos,
					hisc_deteccion_alt_menor_2m_2a.getPiel_anexos());
			Utilidades.seleccionarRadio(rdbNeurologico,
					hisc_deteccion_alt_menor_2m_2a.getNeurologico());
			tbxObservaciones_7_2.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_7_2());

			Utilidades
					.seleccionarRadio(rdbCa_Diagnostico_crecimiento,
							hisc_deteccion_alt_menor_2m_2a
									.getDiagnostico_crecimiento());
			Utilidades.seleccionarRadio(rdbCa_Diagnostico_desarrollo,
					hisc_deteccion_alt_menor_2m_2a.getDiagnostico_desarrollo());

			chbMaltrato_fisico.setChecked(hisc_deteccion_alt_menor_2m_2a
					.getMaltrato_fisico());
			chbMaltrato_emocional.setChecked(hisc_deteccion_alt_menor_2m_2a
					.getMaltrato_emocional());
			chbSospecha_de_abuso_sexual
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getSospecha_de_abuso_sexual());
			chbNo_hay_sospecha_de_maltrato
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getNo_hay_sospecha_de_maltrato());

			tbxAnalisis_9.setValue(hisc_deteccion_alt_menor_2m_2a
					.getAnalisis_9());
			chbEstimulo_factores_protectores
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getEstimulo_factores_protectores());
			chbRecomendaciones_de_buen_trato
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getRecomendaciones_de_buen_trato());
			chbOrientacion_vacunacion.setChecked(hisc_deteccion_alt_menor_2m_2a
					.getOrientacion_vacunacion());
			chbLactancia_materna_complementa
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getLactancia_materna_complementa());
			chbImportancia_asistencia_controles
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getImportancia_asistencia_controles());
			chbRecomendaciones_higienicas
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getRecomendaciones_higienicas());
			chbRecomendaciones_para_el_desarrollo
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getRecomendaciones_para_el_desarrollo());
			chbRecomendaciones_en_salud_oral
					.setChecked(hisc_deteccion_alt_menor_2m_2a
							.getRecomendaciones_en_salud_oral());
			Utilidades.seleccionarRadio(rdbNecesita_prescripcion_de_vitamina_a,
					hisc_deteccion_alt_menor_2m_2a
							.getNecesita_prescripcion_de_vitamina_a());
			Utilidades.seleccionarRadio(rdbNecesita_prescripcion_de_albendazol,
					hisc_deteccion_alt_menor_2m_2a
							.getNecesita_prescripcion_de_albendazol());
			Utilidades.seleccionarRadio(rdbNecesita_prescripcion_de_hierro,
					hisc_deteccion_alt_menor_2m_2a
							.getNecesita_prescripcion_de_hierro());
			Utilidades.seleccionarRadio(rdbNecesita_prescripcion_de_zinc,
					hisc_deteccion_alt_menor_2m_2a
							.getNecesita_prescripcion_de_zinc());
			tbxRecomendaciones_10.setValue(hisc_deteccion_alt_menor_2m_2a
					.getRecomendaciones_10());
			tbxObservaciones_vacunales.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_vacunales());
			
			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2", tbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbOrgano_de_los_sentidos,"2",tbxObservaciones_sentidos,row1);
			FormularioUtil.mostrarObservaciones(rdbRojo_retiniano, "2", tbxObservaciones_rojo, row1);
			FormularioUtil.mostrarObservaciones(rdbFijacion_seguimiento,"2",tbxObservaciones_fijacion,row2);
			FormularioUtil.mostrarObservaciones(rdbOclusion_alternante,"2",tbxObservaciones_oclusion,row2);
			FormularioUtil.mostrarObservaciones(rdbBoca,"2",tbxObservaciones_boca,row2);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2", tbxObservaciones_cuello, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2", tbxObservaciones_torax, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_ritmo_cardiaco, "2", tbxObservaciones_cardiaco, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_soplo, "2", tbxObservaciones_soplo, row4);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2", tbxObservaciones_columna, row4);
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "2", tbxObservaciones_piel, row4);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",tbxObservaciones_abdomen,row5);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_masas,"2",tbxObservaciones_masas,row5);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_megalias, "2", tbxObservaciones_megalias, row5);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", tbxObservaciones_genito, row6);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "2", tbxObservaciones_extremidades, row6);
			FormularioUtil.mostrarObservaciones(rdbNeurologico,"2",tbxObservaciones_neurologico,row6);

			tbxObservaciones_cabeza.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_cabeza());
			tbxObservaciones_abdomen.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_abdomen());
			tbxObservaciones_neurologico.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_neurologico());
			
			tbxObservaciones_sentidos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_sentidos());
			tbxObservaciones_masas.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_masas());
			
			tbxObservaciones_rojo.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_rojo());
			tbxObservaciones_megalias.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_megalias());
			
			tbxObservaciones_cuello.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_cuello());
			tbxObservaciones_genito.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_genito());

			tbxObservaciones_torax.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_torax());
			tbxObservaciones_columna.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_columna());

			tbxObservaciones_cardiaco.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_cardiaco());
			tbxObservaciones_extremidades.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_extremidades());

			tbxObservaciones_soplo.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_soplo());
			tbxObservaciones_piel.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_piel());
			
			tbxObservaciones_fijacion.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_fijacion());
			tbxObservaciones_oclusion.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_oclusion());
			tbxObservaciones_boca.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_boca());

			calcularCoordenadas(false);

			// Mostramos la vista //
			cargarImpresionDiagnostica(hisc_deteccion_alt_menor_2m_2a);
			cargarEscalaDesarrollo(hisc_deteccion_alt_menor_2m_2a);

			cargarAnexo9_remision(hisc_deteccion_alt_menor_2m_2a);

			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);

			// tbxAccion.setText("modificar");
			// accionForm(true, tbxAccion.getText());
			inicializarVista(tipo_historia);
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			btnImprimir.setVisible(true);

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString())) {
				infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
						.getCodigo_historia());
				inicializarVista(tipo_historia);
			}
			FormularioUtil.deshabilitarComponentes(rowCita_anterior, true,
					new String[] {});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a = (Hisc_deteccion_alt_menor_2m_2a) obj;
		try {
			int result = getServiceLocator()
					.getHisc_deteccion_alt_menor_2m_2aService().eliminar(
							hisc_deteccion_alt_menor_2m_2a);
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

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {

			macroEscalaDesarrollo.setZkWindow(this);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);

			edad = Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(admision.getPaciente()
					.getFecha_nacimiento()), "1", false));
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a = new Hisc_deteccion_alt_menor_2m_2a();
				hisc_deteccion_alt_menor_2m_2a.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_deteccion_alt_menor_2m_2a.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_deteccion_alt_menor_2m_2a.setIdentificacion(admision
						.getNro_identificacion());
				hisc_deteccion_alt_menor_2m_2a.setNro_ingreso(admision
						.getNro_ingreso());

				hisc_deteccion_alt_menor_2m_2a = getServiceLocator()
						.getHisc_deteccion_alt_menor_2m_2aService().consultar(
								hisc_deteccion_alt_menor_2m_2a);
				if (hisc_deteccion_alt_menor_2m_2a != null) {
					accionForm(OpcionesFormulario.MOSTRAR,
							hisc_deteccion_alt_menor_2m_2a);
					infoPacientes
							.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
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

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowCita_anterior.setVisible(false);
			rowPerinatales.setVisible(true);
			rowPatologicos.setVisible(true);
			rowAntecedentes.setVisible(true);
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowCita_anterior.setVisible(true);
			rowPerinatales.setVisible(false);
			rowPatologicos.setVisible(false);
			rowAntecedentes.setVisible(false);
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<Hisc_deteccion_alt_menor_2m_2a> listado_historias = getServiceLocator()
									.getHisc_deteccion_alt_menor_2m_2aService()
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
								Hisc_deteccion_alt_menor_2m_2a hisc_det = new Hisc_deteccion_alt_menor_2m_2a();
								hisc_det.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc_det.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc_det.setCodigo_historia(codigo_historia_anterior);

								hisc_det = getServiceLocator()
										.getHisc_deteccion_alt_menor_2m_2aService()
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
		Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a = (Hisc_deteccion_alt_menor_2m_2a) historia_anterior;
		Utilidades.seleccionarRadio(rdbCa_Diagnostico_crecimiento,
				hisc_deteccion_alt_menor_2m_2a.getDiagnostico_crecimiento());
		Utilidades.seleccionarRadio(rdbCa_Diagnostico_desarrollo,
				hisc_deteccion_alt_menor_2m_2a.getDiagnostico_desarrollo());

		// vacunales
		Utilidades.seleccionarRadio(rdbBcg,
				hisc_deteccion_alt_menor_2m_2a.getBcg());
		Utilidades.seleccionarRadio(rdbVop_2,
				hisc_deteccion_alt_menor_2m_2a.getVop_2());
		Utilidades.seleccionarRadio(rdbVop_3,
				hisc_deteccion_alt_menor_2m_2a.getVop_3());
		Utilidades.seleccionarRadio(rdbNeumococo_3,
				hisc_deteccion_alt_menor_2m_2a.getNeumococo_3());
		Utilidades.seleccionarRadio(rdbHepatitis_b_rn,
				hisc_deteccion_alt_menor_2m_2a.getHepatitis_b_rn());
		Utilidades.seleccionarRadio(rdbPenta_2,
				hisc_deteccion_alt_menor_2m_2a.getPenta_2());
		Utilidades.seleccionarRadio(rdbPenta_3,
				hisc_deteccion_alt_menor_2m_2a.getPenta_3());
		Utilidades.seleccionarRadio(rdbTriple_viral,
				hisc_deteccion_alt_menor_2m_2a.getTriple_viral());
		Utilidades.seleccionarRadio(rdbVop_1,
				hisc_deteccion_alt_menor_2m_2a.getVop_1());
		Utilidades.seleccionarRadio(rdbNeumococo_2,
				hisc_deteccion_alt_menor_2m_2a.getNeumococo_2());
		Utilidades.seleccionarRadio(rdbInfluenza_1,
				hisc_deteccion_alt_menor_2m_2a.getInfluenza_1());
		Utilidades.seleccionarRadio(rdbFiebre_amarilla,
				hisc_deteccion_alt_menor_2m_2a.getFiebre_amarilla());
		Utilidades.seleccionarRadio(rdbPenta_1,
				hisc_deteccion_alt_menor_2m_2a.getPenta_1());
		Utilidades.seleccionarRadio(rdbRotavirus_2,
				hisc_deteccion_alt_menor_2m_2a.getRotavirus_2());
		Utilidades.seleccionarRadio(rdbInfluenza_2,
				hisc_deteccion_alt_menor_2m_2a.getInfluenza_2());
		Utilidades.seleccionarRadio(rdbDpt_r1,
				hisc_deteccion_alt_menor_2m_2a.getDpt_r1());
		Utilidades.seleccionarRadio(rdbNeumococo_1,
				hisc_deteccion_alt_menor_2m_2a.getNeumococo_1());
		Utilidades.seleccionarRadio(rdbHepatitis_a,
				hisc_deteccion_alt_menor_2m_2a.getHepatitis_a());
		Utilidades.seleccionarRadio(rdbRotavirus_1,
				hisc_deteccion_alt_menor_2m_2a.getRotavirus_1());
		Utilidades.seleccionarRadio(rdbVop_r1,
				hisc_deteccion_alt_menor_2m_2a.getVop_r1());

		// Perinatales
		ibxNum_embarazos_g.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNum_embarazos_g());
		ibxNum_partos_p.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNum_partos_p());
		ibxNum_abortos_a.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNum_abortos_a());
		ibxNum_cesarias_c.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNum_cesarias_c());
		ibxNum_mortinatos_v.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNum_mortinatos_v());

		Utilidades.seleccionarListitem(lbxVdlr,
				hisc_deteccion_alt_menor_2m_2a.getVdrl_materno());

		ibxEdad_madre_al_nacimiento.setValue(hisc_deteccion_alt_menor_2m_2a
				.getEdad_madre_al_nacimiento());
		ibxSem_gestacion.setValue(hisc_deteccion_alt_menor_2m_2a
				.getSem_gestacion());
		dbxTsh.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_2m_2a.getTsh()));
		Utilidades.seleccionarRadio(rdbParto,
				hisc_deteccion_alt_menor_2m_2a.getParto());
		Utilidades.seleccionarRadio(rdbUnico_multiple,
				hisc_deteccion_alt_menor_2m_2a.getUnico_multiple());
		Utilidades.seleccionarRadio(rdbInstitucional,
				hisc_deteccion_alt_menor_2m_2a.getInstitucional());
		Utilidades.seleccionarRadio(rdbRiesgo,
				hisc_deteccion_alt_menor_2m_2a.getRiesgo());
		Utilidades.seleccionarRadio(rdbControl_prenatal,
				hisc_deteccion_alt_menor_2m_2a.getControl_prenatal());
		Utilidades.seleccionarListitem(lbxHemoclasificacion,
				hisc_deteccion_alt_menor_2m_2a.getHemoclasificacion());
		tbxComplicaciones_embarazo_parto
				.setValue(hisc_deteccion_alt_menor_2m_2a
						.getComplicaciones_embarazo_parto());
		dbxPeso_al_nacer_grs.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_2m_2a
						.getPeso_al_nacer_grs()));
		dbxTalla_al_nacer_cms.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_2m_2a
						.getTalla_al_nacer_cms()));
		dbxApgar_al_minuto.setValue(hisc_deteccion_alt_menor_2m_2a
				.getApgar_al_minuto());
		dbxApgar_5minutos.setValue(hisc_deteccion_alt_menor_2m_2a
				.getApgar_5minutos());
		Utilidades.seleccionarRadio(rdbReanimacion,
				hisc_deteccion_alt_menor_2m_2a.getReanimacion());
		Utilidades.seleccionarRadio(rdbHospitalizacion_neonatal,
				hisc_deteccion_alt_menor_2m_2a.getHospitalizacion_neonatal());
		tbxObservaciones_4_1.setValue(hisc_deteccion_alt_menor_2m_2a
				.getObservaciones_4_1());

		// Antecedentes familiares
		ibxNum_hermanos_vivos.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNum_hermanos_vivos());
		ibxNum_hermanos_desnutridos_menor_5anos
				.setValue(hisc_deteccion_alt_menor_2m_2a
						.getNum_hermanos_desnutridos_menor_5anos());
		ibxNum_hermanos_muertos_menor_5anos
				.setValue(hisc_deteccion_alt_menor_2m_2a
						.getNum_hermanos_muertos_menor_5anos());
		tbxNum_hermanos_muertos_menor_5anos_causa
				.setValue(hisc_deteccion_alt_menor_2m_2a
						.getNum_hermanos_muertos_menor_5anos_causa());
		Utilidades.seleccionarRadio(rdbSon_parientes_los_padres,
				hisc_deteccion_alt_menor_2m_2a.getSon_parientes_los_padres());
		Utilidades.seleccionarRadio(rdbFamilia_con_problema_mental_o_fisico,
				hisc_deteccion_alt_menor_2m_2a
						.getFamilia_con_problema_mental_o_fisico());
		tbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
				.getPaterno_medicos());
		tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
				.getPaterno_alergico());
		dbxPaterno_talla.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_2m_2a
						.getPaterno_talla()));
		tbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
				.getMaterno_medicos());
		tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
				.getMaterno_alergico());
		dbxMaterno_talla.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_2m_2a
						.getMaterno_talla()));
		tbxOtros_5.setValue(hisc_deteccion_alt_menor_2m_2a.getOtros_5());

		// DATOS DEL PACIENTE
		// tbxNombre_del_acompanante.setValue(hisc_deteccion_alt_menor_2m_2a.getNombre_del_acompanante());
		// Utilidades.seleccionarListitem(lbxParentesco,hisc_deteccion_alt_menor_2m_2a.getParentesco());
		tbxNombre_del_padre.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNombre_del_padre());
		ibxEdad_padre.setValue(hisc_deteccion_alt_menor_2m_2a.getEdad_padre());
		tbxNombre_de_la_madre.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNombre_de_la_madre());
		ibxEdad_madre.setValue(hisc_deteccion_alt_menor_2m_2a.getEdad_madre());

		codigo_historia_anterior = hisc_deteccion_alt_menor_2m_2a
				.getCodigo_historia();
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a = (Hisc_deteccion_alt_menor_2m_2a) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });
			if (hisc_deteccion_alt_menor_2m_2a.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			if (hisc_deteccion_alt_menor_2m_2a.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(true);
		}

		codigo_historia_anterior = hisc_deteccion_alt_menor_2m_2a
				.getCodigo_historia_anterior();
		tipo_historia = hisc_deteccion_alt_menor_2m_2a.getTipo_historia();
	}

	// DATOS CURVAS CRECIMIENTO.
	public void calcularCoordenadas(Boolean mostrarAlerta) {
		calcularPerimetroCefalicoEdad(mostrarAlerta);
		calcularPesoEdad(mostrarAlerta);
		calcularPesoTalla(mostrarAlerta);
		calcularTallaEdad(mostrarAlerta);
	}

	private List<RespuestaInt> cargarRespuestas(
			List<Coordenadas_graficas> coordenadas,
			Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a) {
		List<RespuestaInt> respuestas = new ArrayList<RespuestaInt>();
		for (Coordenadas_graficas cg : coordenadas) {
			RespuestaInt r = cargarResuestaInt(cg);
			if (hisc_deteccion_alt_menor_2m_2a != null
					&& cg.getCodigo_historia()
							.equals(hisc_deteccion_alt_menor_2m_2a
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
	public void calcularPesoEdad(Boolean alerta) {

		double talla;
		double peso;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if (alerta) {
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
			if (alerta) {
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
			coordenadaPesoEdad = calcularPesoEdad(peso, talla, fecha);
			dbxP_e_de.setValue(coordenadaPesoEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxP_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PESO_EDAD);
			}
			btnCalcularPesoEdad.setDisabled(false);
		} else {
			if (alerta) {
				if (!(tallaValida(talla))) {
					Messagebox.show(
							"La talla está por fuera del rango [45.1-95]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				} else if (!(pesoValido(peso))) {
					Messagebox.show("El peso está por fuera del rango [1-17]!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		}

	}

	public void calcularTallaEdad(Boolean alerta) {
		double talla;
		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			if (alerta) {
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
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxT_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_TALLA_EDAD);
			}
			btnCalcularTallaEdad.setDisabled(false);
		} else {
			if (alerta) {
				if (!(tallaValida(talla))) {
					Messagebox.show(
							"La talla está por fuera del rango [45.1-95]!!",
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
			if (alerta) {
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
			if (alerta) {
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
				&& dbxExamen_fisico_peso.getValue() != null && talla >= 45.1
				&& talla <= 95 && peso >= 1 && peso <= 17) {
			coordenadaPesoTalla = calcularPesoTalla(peso, talla);
			dbxP_t_de.setValue(coordenadaPesoTalla.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxP_t_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PESO_TALLA);
			}
			btnCalcularPesoTalla.setDisabled(false);
		} else {
			if (alerta) {
				if (!(tallaValida(talla))) {
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

	public void calcularPerimetroCefalicoEdad(Boolean alerta) {
		double perimetro_cefalico;

		if (dbxExamen_fisico_perimetro_cflico.getValue() == null) {
			dbxExamen_fisico_perimetro_cflico
					.setStyle("background-color:#F6BBBE");
			if (alerta) {
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

		coordenadaPerimetroCefalicoEdad = calcularPerimetroCefalicoEdad(perimetro_cefalico);
		dbxPC_e_de.setValue(coordenadaPerimetroCefalicoEdad.getValor());

		btnCalcularPerimetroCefalicoEdad.setDisabled(false);

		if (dbxExamen_fisico_perimetro_cflico.getValue() != null
				&& perimetroCefalicoValido(perimetro_cefalico)) {
			coordenadaPerimetroCefalicoEdad = calcularPerimetroCefalicoEdad(perimetro_cefalico);
			dbxPC_e_de.setValue(coordenadaPerimetroCefalicoEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxPC_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PC_EDAD);
			}
			btnCalcularPerimetroCefalicoEdad.setDisabled(false);
		} else {
			if (alerta) {
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
		Double min = 30.0;
		Double max = 55.0;
		return (perimetro_cefalico >= min && perimetro_cefalico <= max);
	}

	private RespuestaInt calcularPesoEdad(double peso, double talla,
			Timestamp fecha) {
		return GraficasCalculadorUtils.calcularPesoEdad0$2Anios(sexo, peso,
				talla, fecha);
	}

	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularTallaEdad0$2Anios(sexo, talla,
				fecha);
	}

	private RespuestaInt calcularPesoTalla(double peso, double talla) {
		return GraficasCalculadorUtils.calcularPesoTalla0$2Anios(sexo, peso,
				talla);
	}

	private RespuestaInt calcularPerimetroCefalicoEdad(double perimetro_cefalico) {
		return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
				sexo, perimetro_cefalico, fecha);
	}

	/* Graficamos */
	public void graficarPesoEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_E);
		List<Coordenadas_graficas> pes = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		for (int i = 0; i < pes.size(); i++) {
			Coordenadas_graficas cg = pes.get(i);
			if (cg.getIhistoria_clinica() != IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS
					&& cg.getIhistoria_clinica() != IHistorias_clinicas.HC_MENOR_2_MESES) {
				pes.remove(i);
			}
		}
		//log.info(">>>>>>>>>>>(peso,edad)" + pes.size());
		coordenadasPE = cargarRespuestas(pes, hisc_deteccion_alt_menor_2m_2a);

		List coordenadas_pe = coordenadasPE;
		if (!verificarActivo(coordenadasPE)) {
			coordenadas_pe.add(coordenadaPesoEdad);
		}
		imprimirArreglo(coordenadas_pe);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.PESO_EDAD_0_A_2_ANIOS,
				coordenadas_pe, this, sexo);
	}

	public void mostrarTablaPesoEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.PESO_EDAD_0_A_2_ANIOS,
				this, sexo);
	};

	public void graficarTallaEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.T_E);
		List<Coordenadas_graficas> tes = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		for (int i = 0; i < tes.size(); i++) {
			Coordenadas_graficas cg = tes.get(i);
			if (!cg.getIhistoria_clinica().equals(
					IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS)
					&& !cg.getIhistoria_clinica().equals(
							IHistorias_clinicas.HC_MENOR_2_MESES)) {
				tes.remove(i);
			}
		}
		coordenadasTE = cargarRespuestas(tes, hisc_deteccion_alt_menor_2m_2a);

		List coordenadas_te = coordenadasTE;
		if (!verificarActivo(coordenadasTE)) {
			coordenadas_te.add(coordenadaTallaEdad);
		}
		imprimirArreglo(coordenadas_te);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_0_A_2_ANIOS,
				coordenadas_te, this, sexo);
	}

	public void mostrarTablaTallaEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_0_A_2_ANIOS,
				this, sexo);
	};

	public void graficarPesoTalla() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_T);
		List<Coordenadas_graficas> pts = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		for (int i = 0; i < pts.size(); i++) {
			Coordenadas_graficas cg = pts.get(i);
			if (!cg.getIhistoria_clinica().equals(
					IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS)
					&& !cg.getIhistoria_clinica().equals(
							IHistorias_clinicas.HC_MENOR_2_MESES)) {
				pts.remove(i);
			}
		}
		coordenadasPT = cargarRespuestas(pts, hisc_deteccion_alt_menor_2m_2a);

		List coordenadas_pt = coordenadasPT;
		if (!verificarActivo(coordenadasPT)) {
			coordenadas_pt.add(coordenadaPesoTalla);
		}
		imprimirArreglo(coordenadas_pt);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_0_A_2,
				coordenadas_pt, this, sexo);
	}

	public void mostrarTablaPesoTalla() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_0_A_2, this,
				sexo);
	};

	public void graficarPcEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.PC_E);
		List<Coordenadas_graficas> pces = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		for (int i = 0; i < pces.size(); i++) {
			Coordenadas_graficas cg = pces.get(i);
			if (!cg.getIhistoria_clinica().equals(
					IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS)
					&& !cg.getIhistoria_clinica().equals(
							IHistorias_clinicas.HC_MENOR_2_MESES)) {
				pces.remove(i);
			}
		}
		coordenadasPCE = cargarRespuestas(pces, hisc_deteccion_alt_menor_2m_2a);

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

	private void cargarRemisionInterna(
			Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_deteccion_alt_menor_2m_2a
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_deteccion_alt_menor_2m_2a
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private void cargarImpresionDiagnostica(
			Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);

		impresion_diagnostica.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	public void notificarAlerta(Component comp) {
		MensajesUtil.notificarAlerta("Remitir", comp);
	}

	private void cargarEscalaDesarrollo(
			Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a2) {
		Escala_del_desarrollo escala_del_desarrollo = new Escala_del_desarrollo();
		escala_del_desarrollo.setCodigo_empresa(codigo_empresa);
		escala_del_desarrollo.setCodigo_sucursal(codigo_sucursal);
		escala_del_desarrollo.setVia_ingreso(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);
		escala_del_desarrollo
				.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a2
						.getCodigo_historia());
		escala_del_desarrollo = getServiceLocator().getServicio(
				Escala_del_desarrolloService.class).consultar(
				escala_del_desarrollo);
		macroEscalaDesarrollo.mostrarEscalaDesarrollo(escala_del_desarrollo,
				admision);
	}

	private void cargarAnexo9_remision(
			Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_deteccion_alt_menor_2m_2a
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null) {
			remisiones_externasAction.detach();
		}
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

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :\n");
		informacion_clinica.append("\t").append(tbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :\n");
		if (tbxGestion_actual.getValue() != null
				&& !tbxGestion_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxGestion_actual.getValue()).append("\n");
		} else if (tbxGestion_actual.getValue() != null
				&& !tbxGestion_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxGestion_actual.getValue()).append("\n");
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
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/hisc_deteccion_alt_menor_2m_2a_reporte.zul"+parametros_pagina+"\", \"\",\"width=910,top=-400, left=-400\");");
		}
	}
	
}