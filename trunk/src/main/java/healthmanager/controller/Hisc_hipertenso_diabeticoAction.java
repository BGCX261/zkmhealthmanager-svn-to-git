/*
 * hisc_hipertenso_diabeticoAction.java
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
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_hipertenso_diabetico;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Presultados_paraclinicos;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;

import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;
import com.framework.util.UtilidadesSios;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculoMedico;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorParaclinicos;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import healthmanager.modelo.service.GeneralExtraService;
import sios.modelo.service.Hisc_historialSiosService;

public class Hisc_hipertenso_diabeticoAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_hipertenso_diabeticoAction.class);

	private GeneralExtraService generalExtraService;

	// Componentes //
	// Manuel
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View
	private Div divModuloRemisiones_externas;
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
	private Remisiones_externasAction remisiones_externasAction;
	@View
	private Groupbox gbxRemisiones;

	@View
	public Textbox tbxObservaciones_framingham;
	@View
	public Radiogroup rdbPorcentaje_fram;
	@View
	private Div divModuloOrdenamiento;
	@View
	private Div divModuloFarmacologico;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;
	@View
	private Listbox lbxTipo_historia;

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
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Radiogroup rdbCefalea;
	@View
	private Radiogroup rdbEpistaxis;
	@View
	private Radiogroup rdbTinitus;
	@View
	private Radiogroup rdbVertigo;
	@View
	private Radiogroup rdbDisartria;
	@View
	private Radiogroup rdbConvulsion_focal;
	@View
	private Radiogroup rdbLipotimia;
	@View
	private Radiogroup rdbFotopsias;
	@View
	private Radiogroup rdbEscotomas;
	@View
	private Radiogroup rdbVision_borrosa;
	@View
	private Radiogroup rdbDolor_percordial_esf;
	@View
	private Radiogroup rdbDolor_percordial_rep;
	@View
	private Radiogroup rdbPalpitaciones;
	@View
	private Radiogroup rdbDisnea_esfuerzo;
	@View
	private Radiogroup rdbDisnea_paroxis;
	@View
	private Radiogroup rdbOrtopnea;
	@View
	private Radiogroup rdbPoliruria;
	@View
	private Radiogroup rdbPolidipsia;
	@View
	private Radiogroup rdbPolifagia;
	@View
	private Radiogroup rdbOliguria;
	@View
	private Radiogroup rdbHematuria;
	@View
	private Radiogroup rdbEdemas;
	@View
	private Radiogroup rdbParestesias;
	@View
	private Radiogroup rdbHipoestesias;
	@View
	private Radiogroup rdbDisestesias;
	@View
	private Radiogroup rdbAnestesia;
	@View
	private Radiogroup rdbDeficit_motor;
	@View
	private Radiogroup rdbDisf_erectil;
	@View
	private Radiogroup rdbDiarrea;
	@View
	private Radiogroup rdbEstrenimiento;
	@View
	private Radiogroup rdbDishidrosis;
	@View
	private Radiogroup rdbAnhidrosis;
	@View
	private Radiogroup rdbClaudicacion;
	@View
	private Radiogroup rdbCalambres;
	@View
	private Radiogroup rdbDolor_neuritic;
	@View
	private Radiogroup rdbApnea_suenio;
	@View
	private Textbox tbxObservacion_rs;
	@View
	private Radiogroup rdbHta;
	@View
	private Intbox ibxAnios_hta;
	@View
	private Intbox ibxMeses_hta;
	@View
	private Radiogroup rdbDiabetes;
	@View
	private Intbox ibxAnios_diabetes;
	@View
	private Intbox ibxMeses_diabetes;

	@View
	private Textbox tbxMdant_observaciones;

	@View
	private Listbox listboxMedicamentosAnteriores;
	@View
	private Listbox listboxMedicamentosActuales;

	@View
	private Textbox tbxMdact_observaciones;

	@View
	private Radiogroup rdbDiabetes_gestacional;
	@View
	private Radiogroup rdbDislipidemia;
	@View
	private Radiogroup rdbIsquemico_transitorio;
	@View
	private Radiogroup rdbEnfermedad_cerebrovascular;
	@View
	private Radiogroup rdbInfarto_miocardio;
	@View
	private Radiogroup rdbAngina;
	@View
	private Radiogroup rdbEnf_coronaria_pat;
	@View
	private Radiogroup rdbInsuf_cardiaca;
	@View
	private Radiogroup rdbNefropatia_cronica;
	@View
	private Radiogroup rdbEnf_arterial;
	@View
	private Radiogroup rdbAneurisma_aorta;
	@View
	private Radiogroup rdbEnfermedad_carotidea;
	@View
	private Textbox tbxObservacion_patologico;
	@View
	private Textbox tbxOtros_medicos;
	@View
	private Textbox tbxHospitalizaciones;
	@View
	private Textbox tbxTraumaticos;
	@View
	private Textbox tbxAlergicos;
	@View
	private Textbox tbxQuirurgicos;
	@View
	private Textbox tbxObservaciones_otros;
	@View
	private Datebox dtbxFum;
	@View
	private Radiogroup rdbMenopausia;
	@View
	private Intbox ibxG;
	@View
	private Intbox ibxP;
	@View
	private Intbox ibxA;
	@View
	private Intbox ibxC;
	@View
	private Textbox tbxAnticoncepcion;
	@View
	private Radiogroup rdbHipertension_af;
	@View
	private Radiogroup rdbDiabetes_af;
	@View
	private Radiogroup rdbDislipidemia_af;
	@View
	private Radiogroup rdbEnfermedad_cerebrovascular_af;
	@View
	private Radiogroup rdbEnf_arterial_af;
	@View
	private Radiogroup rdbEnf_coronaria_af;
	@View
	private Radiogroup rdbEnf_coronaria_hom55;
	@View
	private Radiogroup rdbEnf_coronaria_muj65;
	@View
	private Textbox tbxOtros_observaciones;
	@View
	private Radiogroup rdbTabaquismo10;
	@View
	private Radiogroup rdbTabaquismo1020;
	@View
	private Radiogroup rdbTabaquismo20;
	@View
	private Radiogroup rdbTabaquismo_pasivo;
	@View
	private Radiogroup rdbAlcohol815;
	@View
	private Radiogroup rdbAlcohol_diario;
	@View
	private Radiogroup rdbSedentarismo;
	@View
	private Radiogroup rdbAlta_ingesta_sal;
	@View
	private Radiogroup rdbAlta_ingesta_grasa;
	@View
	private Radiogroup rdbUso_aines;
	@View
	private Radiogroup rdbUso_sicoactivos;
	@View
	private Radiogroup rdbEjercicio_diario;
	@View
	private Radiogroup rdbEjercicio_ocasional;
	@View
	private Radiogroup rdbStress;
	@View
	private Textbox tbxObservaciones_habitos;
	@View
	private Doublebox dbxPeso;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxC_abdominal;
	@View
	private Doublebox dbxImc;
	@View
	private Doublebox dbxSup_corporal;
	@View
	private Doublebox dbxFc_lat_min;
	@View
	private Doublebox dbxFp_pul_min;
	@View
	private Doublebox dbxFr_resp_min;
	@View
	private Doublebox dbxTemp_c;
	@View
	private Doublebox dbxTa_sentado_bd;
	@View
	private Doublebox dbxTa_sentado_mmhg;
	@View
	private Doublebox dbxTa_sentado_bi;
	@View
	private Doublebox dbxTa_sentado_mmhg2;
	@View
	private Doublebox dbxTa_decubito;
	@View
	private Doublebox dbxTa_decubito_mmhg;
	@View
	private Doublebox dbxTa_pie;
	@View
	private Doublebox dbxTa_pie_mmhg;
	@View
	private Radiogroup rdbConciencia;
	@View
	private Radiogroup rdbHidratacion;
	@View
	private Radiogroup rdbCondicion_general;
	@View
	private Radiogroup rdbCabeza_cara;
	@View
	private Radiogroup rdbOrgano_sentidos;
	@View
	private Radiogroup rdbFondo_ojo;
	@View
	private Radiogroup rdbCuello_yugular;
	@View
	private Radiogroup rdbCuello_soplo;
	@View
	private Radiogroup rdbCuello_gland;
	@View
	private Radiogroup rdbCuello_masas;
	@View
	private Radiogroup rdbTorax_pmi;
	@View
	private Radiogroup rdbTorax_ritmo;
	@View
	private Radiogroup rdbTorax_soplo;
	@View
	private Radiogroup rdbAbdomen_masas;
	@View
	private Radiogroup rdbAbdomen_megalias;
	@View
	private Radiogroup rdbAbdomen_soplos;
	@View
	private Radiogroup rdbGenito_urinario;
	@View
	private Radiogroup rdbColumna_vertebral;
	@View
	private Radiogroup rdbPiel_anexos;
	@View
	private Radiogroup rdbExtremidades_coloracion;
	@View
	private Radiogroup rdbExtremidades_temperatura;
	@View
	private Radiogroup rdbExtremidades_vellos;
	@View
	private Radiogroup rdbExtremidades_pulsos;
	@View
	private Radiogroup rdbExtremidades_tibial;
	@View
	private Radiogroup rdbExtremidades_edema;
	@View
	private Radiogroup rdbExtremidades_ulcera;
	@View
	private Radiogroup rdbNeurologico_sensibilidad;
	@View
	private Radiogroup rdbNeurologico_motricidad;
	@View
	private Radiogroup rdbNeurologico_reflejos;
	@View
	private Textbox tbxObservaciones_examfisico;
	@View
	private Radiogroup rdbHipertension_1ria;
	@View
	private Radiogroup rdbHipertension_2ria;
	@View
	private Radiogroup rdbDiabetes_tipo2;
	@View
	private Radiogroup rdbAlt_glicemia_ayuna;
	@View
	private Radiogroup rdbIntol_hidratos_carbono;
	@View
	private Radiogroup rdbHipercolesterolemia;
	@View
	private Radiogroup rdbHipertrigliceridemia;
	@View
	private Radiogroup rdbHdl_bajo;
	@View
	private Radiogroup rdbSobrepeso;
	@View
	private Radiogroup rdbObesidad_abdominal;
	@View
	private Radiogroup rdbObesidad;
	@View
	private Radiogroup rdbSind_metabolico;
	@View
	private Radiogroup rdbEnf_cerebro_vascular;
	@View
	private Radiogroup rdbIsquemio_transitorio;
	@View
	private Radiogroup rdbRetinopatia;
	@View
	private Radiogroup rdbEnf_coronaria;
	@View
	private Radiogroup rdbIns_cardiaca;
	@View
	private Radiogroup rdbNefropatia;
	@View
	private Radiogroup rdbEnf_arterial_periferica;
	@View
	private Radiogroup rdbNeuropatia_periferica;
	@View
	private Radiogroup rdbTabaquismo_dfr;
	@View
	private Radiogroup rdbAlcoholismo_dfr;
	@View
	private Radiogroup rdbSedentarismo_dfr;
	@View
	private Radiogroup rdbMalos_habitos_nutricionales;
	@View
	private Textbox tbxAnalisis;
	@View
	private Checkbox chbEjercicio_trat;
	@View
	private Checkbox chbBajar_peso_trat;
	@View
	private Checkbox chbBajar_grasa_trat;
	@View
	private Checkbox chbDieta_hiposodica_trat;
	@View
	private Checkbox chbImptcia_adherencia_trat;
	@View
	private Checkbox chbAumentar_frutas_trat;
	@View
	private Checkbox chbManejo_strees_trat;
	@View
	private Checkbox chbImptcia_asistencia_trat;
	@View
	private Checkbox chbNo_fumar_trat;
	@View
	private Checkbox chbNo_alcohol_trat;
	@View
	private Textbox tbxObservaciones_tract;
	@View
	private Textbox tbxTratamiento_farmacologico;
	@View
	private Checkbox chbHemograma_parac;
	@View
	private Checkbox chbPerfil_lipidico_parac;
	@View
	private Checkbox chbGlicemia_ayunas_parac;
	@View
	private Checkbox chbGlicemia_postcarga_parac;
	@View
	private Checkbox chbGlicemia_postpandrial_parac;
	@View
	private Checkbox chbCreatinina_parac;
	@View
	private Checkbox chbUroanalisis_parac;
	@View
	private Checkbox chbHba1c_parac;
	@View
	private Checkbox chbMicroalbuminuria_parac;
	@View
	private Checkbox chbPotasio_parac;
	@View
	private Checkbox chbEkg_parac;
	@View
	private Vbox vboxParaclinicos;
	private ManejadorParaclinicos manejadorParaclinicos;
	@View
	private Textbox tbxObservaciones_paraclinico;

	@View
	private Groupbox gbxEvaluacion_anterior;
	@View
	private Groupbox gbxAntecendentes_personales;
	@View
	private Groupbox gbxAntecedentes_familiares;
	@View
	private Groupbox gbxHabitos;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	@View
	private Doublebox dbxPesoc;
	@View
	private Doublebox dbxTallac;
	@View
	private Doublebox dbxC_abdominalc;
	@View
	private Doublebox dbxImcc;
	@View
	private Doublebox dbxSup_corporalc;
	@View
	private Doublebox dbxFc_lat_minc;
	@View
	private Doublebox dbxFp_pul_minc;
	@View
	private Doublebox dbxFr_resp_minc;
	@View
	private Doublebox dbxTemp_cc;
	@View
	private Doublebox dbxTa_sentado_bdc;
	@View
	private Doublebox dbxTa_sentado_mmhgc;
	@View
	private Doublebox dbxTa_sentado_bic;
	@View
	private Doublebox dbxTa_sentado_mmhg2c;
	@View
	private Doublebox dbxTa_decubitoc;
	@View
	private Doublebox dbxTa_decubito_mmhgc;
	@View
	private Doublebox dbxTa_piec;
	@View
	private Doublebox dbxTa_pie_mmhgc;
	@View
	private Intbox ibxAv_lejana_od1c;
	@View
	private Intbox ibxAv_lejana_od2c;
	@View
	private Intbox ibxAv_lejana_oi1c;
	@View
	private Intbox ibxAv_lejana_oi2c;
	@View
	private Intbox ibxAv_cercana_od1c;
	@View
	private Intbox ibxAv_cercana_od2c;
	@View
	private Intbox ibxAv_cercana_oi1c;
	@View
	private Intbox ibxAv_cercana_oi2c;
	@View
	private Groupbox gbxGineco_obstetricos;
	@View
	private Doublebox dbxTfg;
	@View
	private Doublebox dbxCreatinina;
	@View
	private Radiogroup rdbLesion_pies;
	@View
	private Radiogroup rdbDisnea;
	@View
	private Radiogroup rdbDisnea_pequenia;
	@View
	private Radiogroup rdbDisnea_grande;
	@View
	private Radiogroup rdbDisnea_mediana;

	@View
	private Intbox ibxColesterol_calculadora;
	@View
	private Intbox ibxHdl_calculadora;
	@View
	private Intbox ibxEdad_calculadora;
	@View
	private Textbox tbxSexo_calculadora;
	@View
	private Doublebox dbxSistolica_calculadora;
	@View
	private Radiogroup rdTratamiento_calculadora;
	@View
	private Radiogroup rdTabaquismo_calculadora;
	@View
	private Textbox tbxPuntaje_calculadora;
	@View
	private Textbox tbxRiesgo_calculadora;

	// ------------------ Jose --------
	@View
	private Listbox lbxParentesco_acompanante;
	@View
	private Textbox tbxAcompaniante;
	@View
	private Textbox tbxId_acompanante;
	@View
	private Textbox tbxTelefono_acompanante;
	@View
	private Radiogroup rdbEx_fumador;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_respiratorio;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_piel;
	@View
	private Intbox ibxV;

	private Boolean validar_observacion;

	private String nro_ingreso_admision;
	private boolean cobrar_agudeza;

	private ZKWindow zkWindow;

	@View
	private Toolbarbutton btnImprimir;

	@View
	private Listbox lbxFormato;
	@View
	private Textbox tbxTratamiento_farmacologicoC;

	@View
	private Textbox tbxObservaciones_conciencia;
	@View
	private Textbox tbxObservaciones_hidratacion;
	@View
	private Textbox tbxObservaciones_general;
	@View
	private Textbox tbxObservaciones_cabeza;
	@View
	private Textbox tbxObservaciones_sentidos;
	@View
	private Textbox tbxObservaciones_fondo;
	@View
	private Textbox tbxObservaciones_yugular;
	@View
	private Textbox tbxObservaciones_soplo_carotideo;
	@View
	private Textbox tbxObservaciones_tiroides;
	@View
	private Textbox tbxObservaciones_masas;
	@View
	private Textbox tbxObservaciones_pmi;
	@View
	private Textbox tbxObservaciones_soplo;
	@View
	private Textbox tbxObservaciones_cardiaco;
	@View
	private Textbox tbxObservaciones_masas_abdomen;
	@View
	private Textbox tbxObservaciones_megalias;
	@View
	private Textbox tbxObservaciones_soplo_abdomen;
	@View
	private Textbox tbxObservaciones_genito;
	@View
	private Textbox tbxObservaciones_columna;
	@View
	private Textbox tbxObservaciones_piel;
	@View
	private Textbox tbxObservaciones_coloracion;
	@View
	private Textbox tbxObservaciones_temperatura;
	@View
	private Textbox tbxObservaciones_vellos;
	@View
	private Textbox tbxObservaciones_pulsos;
	@View
	private Textbox tbxObservaciones_tibial;
	@View
	private Textbox tbxObservaciones_edema;
	@View
	private Textbox tbxObservaciones_ulcera;
	@View
	private Textbox tbxObservaciones_sensibilidad;
	@View
	private Textbox tbxObservaciones_motricidad;
	@View
	private Textbox tbxObservaciones_reflejos;

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
	@View
	private Row row8;
	@View
	private Row row9;
	@View
	private Row row10;
	@View
	private Row row11;
	@View
	private Row row12;

	// manuel aguilar
	@View
	private Radiogroup rdbCefalea_v;
	@View
	private Radiogroup rdbPolifagia_v;
	@View
	private Radiogroup rdbDisf_erectil_v;
	@View
	private Radiogroup rdbEpistaxis_v;
	@View
	private Radiogroup rdbDolor_percordial_esf_v;
	@View
	private Radiogroup rdbOliguria_v;
	@View
	private Radiogroup rdbDiarrea_v;
	@View
	private Radiogroup rdbTinitus_v;
	@View
	private Radiogroup rdbDolor_percordial_rep_v;
	@View
	private Radiogroup rdbHematuria_v;
	@View
	private Radiogroup rdbEstrenimiento_v;
	@View
	private Radiogroup rdbVertigo_v;
	@View
	private Radiogroup rdbPalpitaciones_v;
	@View
	private Radiogroup rdbEdemas_v;
	@View
	private Radiogroup rdbDishidrosis_v;
	@View
	private Radiogroup rdbDisartria_v;
	@View
	private Radiogroup rdbDisnea_esfuerzo_v;
	@View
	private Radiogroup rdbParestesias_v;
	@View
	private Radiogroup rdbAnhidrosis_v;
	@View
	private Radiogroup rdbConvulsion_focal_v;
	@View
	private Radiogroup rdbDisnea_paroxis_v;
	@View
	private Radiogroup rdbHipoestesias_v;
	@View
	private Radiogroup rdbClaudicacion_v;
	@View
	private Radiogroup rdbLipotimia_v;
	@View
	private Radiogroup rdbOrtopnea_v;
	@View
	private Radiogroup rdbDisestesias_v;
	@View
	private Radiogroup rdbCalambres_v;
	@View
	private Radiogroup rdbFotopsias_v;
	@View
	private Radiogroup rdbPoliruria_v;
	@View
	private Radiogroup rdbAnestesia_v;
	@View
	private Radiogroup rdbDolor_neuritic_v;
	@View
	private Radiogroup rdbEscotomas_v;
	@View
	private Radiogroup rdbPolidipsia_v;
	@View
	private Radiogroup rdbDeficit_motor_v;
	@View
	private Radiogroup rdbApnea_suenio_v;
	@View
	private Textbox tbxObservacion_rs_v;

	/*
	 * 
	 * @View private Textbox tbxEstadio_renal;
	 */
	@View
	private Toolbarbutton btGuardar;

	private final String[] idsExcluyentes = { "divParaclinicos", "tbxValue",
			"lbxParameter", "northEditar",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "lbxParentesco_acompanante",
			"toolbarbuttonImprimirRI", "divModuloRemisiones_externas" };

	@Override
	public void init() {
		try {
			this.zkWindow = this;
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
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
		}
	}

	/**
	 * Metodo para cargar la informacion de la historia anterior ya que para
	 * esta historia clinica aplica cargar informacion de la historia anterior
	 * cuando es de control
	 *
	 * @param hisc_hipertenso_diabetico
	 */
	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxParentesco_acompanante, true,
				getServiceLocator());

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		// listitem.setValue("codigo_historia");
		// listitem.setLabel("Codigo historia");
		// lbxParameter.appendChild(listitem);
		//
		// listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void desaparecerBotonn() {
		btGuardar.setVisible(false);
	}

	private void crearFilasMedicamentosAnteriores(Listbox listboxMedicamentos,
			String ids) {
		// log.info("LLamando al metodo crearFilasMedicamentosAnteriores");
		listboxMedicamentos.getItems().clear();
		for (int i = 0; i < 5; i++) {
			Listitem listitem = new Listitem();
			Listcell listcell = Utilidades.obtenerListcell("", Textbox.class,
					false, false);
			Textbox textbox_columna1 = (Textbox) listcell.getFirstChild();
			textbox_columna1.setId("textbox_columna1_" + ids + "_" + i);
			listitem.appendChild(listcell);
			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
					false);
			Textbox textbox_columna2 = (Textbox) listcell.getFirstChild();
			textbox_columna2.setId("textbox_columna2_" + ids + "_" + i);
			listitem.appendChild(listcell);
			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
					false);
			Textbox textbox_columna3 = (Textbox) listcell.getFirstChild();
			textbox_columna3.setId("textbox_columna3_" + ids + "_" + i);
			listitem.appendChild(listcell);
			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
					false);
			Textbox textbox_columna4 = (Textbox) listcell.getFirstChild();
			textbox_columna4.setId("textbox_columna4_" + ids + "_" + i);
			listitem.appendChild(listcell);
			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
					false);
			Textbox textbox_columna5 = (Textbox) listcell.getFirstChild();
			textbox_columna5.setId("textbox_columna5_" + ids + "_" + i);
			listitem.appendChild(listcell);
			listboxMedicamentos.appendChild(listitem);
		}
	}

	private String getValoresMedicamentosAnteriores(
			Listbox listboxMedicamentos, String ids, Integer columna) {
		StringBuffer stringBuffer = new StringBuffer();
		List<Listitem> listado = listboxMedicamentos.getItems();
		for (int i = 0; i < listado.size(); i++) {
			Textbox textbox_columna = (Textbox) listboxMedicamentos
					.getFellow("textbox_columna" + columna + "_" + ids + "_"
							+ i);
			if (i != 0) {
				stringBuffer.append("\n");
			}
			stringBuffer.append(textbox_columna.getValue());
		}
		return stringBuffer.toString();
	}

	private void setValoresMedicamentosAnteriores(Listbox listboxMedicamentos,
			String ids, Integer columna, String valores) {
		StringTokenizer stringTokenizer = new StringTokenizer(valores, "\n");
		List<String> listado_valores = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			listado_valores.add(stringTokenizer.nextToken());
		}
		List<Listitem> listado_filas = listboxMedicamentos.getItems();
		for (int i = 0; i < listado_filas.size(); i++) {
			Textbox textbox_columna = (Textbox) listboxMedicamentos
					.getFellow("textbox_columna" + columna + "_" + ids + "_"
							+ i);
			if (i >= listado_valores.size()) {
				textbox_columna.setValue("");
			} else {
				textbox_columna.setValue(listado_valores.get(i));
			}
		}
	}

	// public void seleccionRiesgo() {
	// //String text = tbxRiesgo_calculadora.getValue().matches("[0-9]*");
	// double r = Double.valueOf(tbxRiesgo_calculadora.getValue());
	// if (r >= 0 && r <= 10) {
	// rdbPorcentaje_fram.setSelectedIndex(0);
	// } else if (r >= 11 && r <= 20) {
	// rdbPorcentaje_fram.setSelectedIndex(1);
	// } else if (r >= 11 && r <= 20) {
	// rdbPorcentaje_fram.setSelectedIndex(2);
	// } else {
	//
	// }
	// }
	// // Accion del formulario si es nuevo o consultar //
	// public void accionForm(boolean sw, String accion) throws Exception {
	// groupboxConsultar.setVisible(!sw);
	// groupboxEditar.setVisible(sw);
	//
	// tbxAccion.setValue(accion);
	//
	// if (!accion.equalsIgnoreCase("registrar")) {
	// buscarDatos();
	// } else {
	// limpiarDatos();
	// if (admision != null){
	// infoPacientes.setFecha_inicio(new Date());
	// cargarInformacion_paciente();
	// cargarDatosCalculadora();
	//
	// valida_admision = null;
	// onMostrarModuloFarmacologico();
	// receta_ripsAction.obtenerBotonAgregar().setVisible(true);
	// onMostrarModuloOrdenamiento();
	// orden_servicioAction.obtenerBotonAgregar().setVisible(true);
	//
	// }
	//
	// FormularioUtil.deshabilitarComponentes(gbxEvaluacion_anterior,
	// true, new String[] {});
	// }
	// }
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
		FormularioUtil.deshabilitarComponentes(gbxEvaluacion_anterior, true,
				new String[] {});

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
		manejadorParaclinicos.limpiarResultados_paraclinicos();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		infoPacientes.validarInformacionPaciente();
		boolean valida = receta_ripsAction.validarFormExterno();
		// String mensaje = "";
		if (valida) {
			valida = orden_servicioAction.validarFormExterno();
		}

		if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
			if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
				valida = macroAgudeza_visual.validarCamposObligatorios();
			}
		}

		if (valida) {
			valida = remisiones_externasAction.validarRemision();
		}

		if (valida) {
			FormularioUtil.validarCamposObligatorios(tbxMotivo_consulta,
					tbxEnfermedad_actual);
		}

		if (valida) {
			validarExamen_fisico();

			if (validar_observacion) {
				FormularioUtil
						.validarCamposObligatorios(tbxObservaciones_examfisico);

			}
		}
		if (valida) {
			String mensaje = manejadorParaclinicos
					.validarResultados_paraclinicos();
			if (!mensaje.isEmpty()) {
				valida = false;
				MensajesUtil.mensajeAlerta(
						"No se pueden guardar los resultados paraclinicos",
						mensaje);
			}
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

			if (lbxTipo_historia.getSelectedIndex() != 0) {
				parameters.put("tipo_historia", lbxTipo_historia
						.getSelectedItem().getValue());
			}

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Hisc_hipertenso_diabetico> lista_datos = getServiceLocator()
					.getHisc_hipertenso_diabeticoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_hipertenso_diabetico hisc_hipertenso_diabetico : lista_datos) {
				rowsResultado.appendChild(crearFilas(hisc_hipertenso_diabetico,
						this));
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

		final Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = (Hisc_hipertenso_diabetico) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_hipertenso_diabetico
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_hipertenso_diabetico
				.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				hisc_hipertenso_diabetico.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_hipertenso_diabetico.getTipo_historia()
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
						mostrarDatos(hisc_hipertenso_diabetico);
						desaparecerBotonn();
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
													eliminarDatos(hisc_hipertenso_diabetico);
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

	public Hisc_hipertenso_diabetico getBean() {
		Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = new Hisc_hipertenso_diabetico();
		hisc_hipertenso_diabetico.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));

		hisc_hipertenso_diabetico
				.setCodigo_empresa(empresa.getCodigo_empresa());
		hisc_hipertenso_diabetico.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		hisc_hipertenso_diabetico.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_hipertenso_diabetico.setIdentificacion(admision != null ? admision
				.getNro_identificacion() : null);
		hisc_hipertenso_diabetico.setNro_ingreso(admision.getNro_ingreso());
		hisc_hipertenso_diabetico.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));
		hisc_hipertenso_diabetico.setUltimo_update(new Timestamp((new Date())
				.getTime()));

		hisc_hipertenso_diabetico.setMotivo_consulta(tbxMotivo_consulta
				.getValue());
		hisc_hipertenso_diabetico.setEnfermedad_actual(tbxEnfermedad_actual
				.getValue());
		hisc_hipertenso_diabetico.setCefalea(rdbCefalea.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico
				.setObservaciones_framingham(tbxObservaciones_framingham
						.getValue());
		hisc_hipertenso_diabetico.setPorcentaje_fram(rdbPorcentaje_fram
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEpistaxis(rdbEpistaxis.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setTinitus(rdbTinitus.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setVertigo(rdbVertigo.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setDisartria(rdbDisartria.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setConvulsion_focal(rdbConvulsion_focal
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setLipotimia(rdbLipotimia.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setFotopsias(rdbFotopsias.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setEscotomas(rdbEscotomas.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setVision_borrosa(rdbVision_borrosa
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setDolor_percordial_esf(rdbDolor_percordial_esf
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setDolor_percordial_rep(rdbDolor_percordial_rep
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setPalpitaciones(rdbPalpitaciones
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDisnea_esfuerzo(rdbDisnea_esfuerzo
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDisnea_paroxis(rdbDisnea_paroxis
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setOrtopnea(rdbOrtopnea.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setPoliruria(rdbPoliruria.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setPolidipsia(rdbPolidipsia.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setPolifagia(rdbPolifagia.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setOliguria(rdbOliguria.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setHematuria(rdbHematuria.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setEdemas(rdbEdemas.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setParestesias(rdbParestesias
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setHipoestesias(rdbHipoestesias
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDisestesias(rdbDisestesias
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAnestesia(rdbAnestesia.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setDeficit_motor(rdbDeficit_motor
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDisf_erectil(rdbDisf_erectil
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDiarrea(rdbDiarrea.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setEstrenimiento(rdbEstrenimiento
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDishidrosis(rdbDishidrosis
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAnhidrosis(rdbAnhidrosis.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setClaudicacion(rdbClaudicacion
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setCalambres(rdbCalambres.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setDolor_neuritic(rdbDolor_neuritic
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setApnea_suenio(rdbApnea_suenio
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setObservacion_rs(tbxObservacion_rs
				.getValue());
		hisc_hipertenso_diabetico.setHta(rdbHta.getSelectedItem().getValue()
				.toString());
		hisc_hipertenso_diabetico
				.setAnios_hta((ibxAnios_hta.getValue() != null ? ibxAnios_hta
						.getValue() : 0));
		hisc_hipertenso_diabetico
				.setMeses_hta((ibxMeses_hta.getValue() != null ? ibxMeses_hta
						.getValue() : 0));
		hisc_hipertenso_diabetico.setDiabetes(rdbDiabetes.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setAnios_diabetes((ibxAnios_diabetes
				.getValue() != null ? ibxAnios_diabetes.getValue() : 0));
		hisc_hipertenso_diabetico.setMeses_diabetes((ibxMeses_diabetes
				.getValue() != null ? ibxMeses_diabetes.getValue() : 0));
		hisc_hipertenso_diabetico
				.setMdant_nombre(getValoresMedicamentosAnteriores(
						listboxMedicamentosAnteriores, "mdanteriores", 1));
		hisc_hipertenso_diabetico
				.setMdant_presentacion(getValoresMedicamentosAnteriores(
						listboxMedicamentosAnteriores, "mdanteriores", 2));
		hisc_hipertenso_diabetico
				.setMdant_dosis(getValoresMedicamentosAnteriores(
						listboxMedicamentosAnteriores, "mdanteriores", 3));
		hisc_hipertenso_diabetico
				.setMdant_uso_hasta(getValoresMedicamentosAnteriores(
						listboxMedicamentosAnteriores, "mdanteriores", 4));
		hisc_hipertenso_diabetico
				.setMdant_causa_suspension(getValoresMedicamentosAnteriores(
						listboxMedicamentosAnteriores, "mdanteriores", 5));
		hisc_hipertenso_diabetico.setMdant_observaciones(tbxMdant_observaciones
				.getValue());
		hisc_hipertenso_diabetico
				.setMdact_nombre(getValoresMedicamentosAnteriores(
						listboxMedicamentosActuales, "mdactuales", 1));
		hisc_hipertenso_diabetico
				.setMdact_presentacion(getValoresMedicamentosAnteriores(
						listboxMedicamentosActuales, "mdactuales", 2));
		hisc_hipertenso_diabetico
				.setMdact_dosis(getValoresMedicamentosAnteriores(
						listboxMedicamentosActuales, "mdactuales", 3));
		hisc_hipertenso_diabetico
				.setMdact_fecha_inicio(getValoresMedicamentosAnteriores(
						listboxMedicamentosActuales, "mdactuales", 4));
		hisc_hipertenso_diabetico
				.setMdact_reaccion(getValoresMedicamentosAnteriores(
						listboxMedicamentosActuales, "mdactuales", 5));
		hisc_hipertenso_diabetico
				.setDiabetes_gestacional(rdbDiabetes_gestacional
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDislipidemia(rdbDislipidemia
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setIsquemico_transitorio(rdbIsquemico_transitorio
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setEnfermedad_cerebrovascular(rdbEnfermedad_cerebrovascular
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setInfarto_miocardio(rdbInfarto_miocardio
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAngina(rdbAngina.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setEnf_coronaria_pat(rdbEnf_coronaria_pat
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setInsuf_cardiaca(rdbInsuf_cardiaca
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setNefropatia_cronica(rdbNefropatia_cronica
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEnf_arterial(rdbEnf_arterial
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAneurisma_aorta(rdbAneurisma_aorta
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setEnfermedad_carotidea(rdbEnfermedad_carotidea
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setObservacion_patologico(tbxObservacion_patologico.getValue());
		hisc_hipertenso_diabetico.setOtros_medicos(tbxOtros_medicos.getValue());
		hisc_hipertenso_diabetico.setHospitalizaciones(tbxHospitalizaciones
				.getValue());
		hisc_hipertenso_diabetico.setTraumaticos(tbxTraumaticos.getValue());
		hisc_hipertenso_diabetico.setAlergicos(tbxAlergicos.getValue());
		hisc_hipertenso_diabetico.setQuirurgicos(tbxQuirurgicos.getValue());
		hisc_hipertenso_diabetico.setObservaciones_otros(tbxObservaciones_otros
				.getValue());
		hisc_hipertenso_diabetico.setFum(dtbxFum.getText());
		hisc_hipertenso_diabetico.setMenopausia(rdbMenopausia.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setG((ibxG.getValue() != null ? ibxG
				.getValue() : 0));
		hisc_hipertenso_diabetico.setP((ibxP.getValue() != null ? ibxP
				.getValue() : 0));
		hisc_hipertenso_diabetico.setA((ibxA.getValue() != null ? ibxA
				.getValue() : 0));
		hisc_hipertenso_diabetico.setC((ibxC.getValue() != null ? ibxC
				.getValue() : 0));
		hisc_hipertenso_diabetico.setAnticoncepcion(tbxAnticoncepcion
				.getValue());
		hisc_hipertenso_diabetico.setHipertension_af(rdbHipertension_af
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDiabetes_af(rdbDiabetes_af
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDislipidemia_af(rdbDislipidemia_af
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setEnfermedad_cerebrovascular_af(rdbEnfermedad_cerebrovascular_af
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEnf_arterial_af(rdbEnf_arterial_af
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEnf_coronaria_af(rdbEnf_coronaria_af
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEnf_coronaria_hom55(rdbEnf_coronaria_hom55
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEnf_coronaria_muj65(rdbEnf_coronaria_muj65
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setOtros_observaciones(tbxOtros_observaciones
				.getValue());
		hisc_hipertenso_diabetico.setTabaquismo10(rdbTabaquismo10
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setTabaquismo1020(rdbTabaquismo1020
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setTabaquismo20(rdbTabaquismo20
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setTabaquismo_pasivo(rdbTabaquismo_pasivo
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAlcohol815(rdbAlcohol815.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setAlcohol_diario(rdbAlcohol_diario
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setSedentarismo(rdbSedentarismo
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAlta_ingesta_sal(rdbAlta_ingesta_sal
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAlta_ingesta_grasa(rdbAlta_ingesta_grasa
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setUso_aines(rdbUso_aines.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setUso_sicoactivos(rdbUso_sicoactivos
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEjercicio_diario(rdbEjercicio_diario
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEjercicio_ocasional(rdbEjercicio_ocasional
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setStress(rdbStress.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico
				.setObservaciones_habitos(tbxObservaciones_habitos.getValue());
		hisc_hipertenso_diabetico.setPeso((dbxPeso.getValue() != null ? dbxPeso
				.getValue() : 0.00));
		hisc_hipertenso_diabetico
				.setTalla((dbxTalla.getValue() != null ? dbxTalla.getValue()
						: 0.00));
		hisc_hipertenso_diabetico
				.setC_abdominal((dbxC_abdominal.getValue() != null ? dbxC_abdominal
						.getValue() : 0.00));
		hisc_hipertenso_diabetico.setImc((dbxImc.getValue() != null ? dbxImc
				.getValue() : 0.00));
		hisc_hipertenso_diabetico
				.setSup_corporal((dbxSup_corporal.getValue() != null ? dbxSup_corporal
						.getValue() : 0.00));
		hisc_hipertenso_diabetico.setFc_lat_min(String.valueOf(dbxFc_lat_min
				.getValue()));
		hisc_hipertenso_diabetico.setFp_pul_min(String.valueOf(dbxFp_pul_min
				.getValue()));
		hisc_hipertenso_diabetico.setFr_resp_min(String.valueOf(dbxFr_resp_min
				.getValue()));
		hisc_hipertenso_diabetico.setTemp_c(ConvertidorDatosUtil
				.convertirDato(dbxTemp_c.getValue()));
		hisc_hipertenso_diabetico
				.setTa_sentado_bd((dbxTa_sentado_bd.getValue() != null ? dbxTa_sentado_bd
						.getValue().toString() : "0"));
		hisc_hipertenso_diabetico.setTa_sentado_mmhg((dbxTa_sentado_mmhg
				.getValue() != null ? dbxTa_sentado_mmhg.getValue().toString()
				: "0"));
		hisc_hipertenso_diabetico
				.setTa_sentado_bi((dbxTa_sentado_bi.getValue() != null ? dbxTa_sentado_bi
						.getValue().toString() : "0"));
		hisc_hipertenso_diabetico.setTa_sentado_mmhg2((dbxTa_sentado_mmhg2
				.getValue() != null ? dbxTa_sentado_mmhg2.getValue().toString()
				: "0"));
		hisc_hipertenso_diabetico
				.setTa_decubito((dbxTa_decubito.getValue() != null ? dbxTa_decubito
						.getValue().toString() : "0"));
		hisc_hipertenso_diabetico.setTa_decubito_mmhg((dbxTa_decubito_mmhg
				.getValue() != null ? dbxTa_decubito_mmhg.getValue().toString()
				: "0"));
		hisc_hipertenso_diabetico
				.setTa_pie((dbxTa_pie.getValue() != null ? dbxTa_pie.getValue()
						.toString() : "0"));
		hisc_hipertenso_diabetico
				.setTa_pie_mmhg((dbxTa_pie_mmhg.getValue() != null ? dbxTa_pie_mmhg
						.getValue().toString() : "0"));

		hisc_hipertenso_diabetico.setConciencia(rdbConciencia.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setHidratacion(rdbHidratacion
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setCondicion_general(rdbCondicion_general
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setCabeza_cara(rdbCabeza_cara
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setOrgano_sentidos(rdbOrgano_sentidos
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setFondo_ojo(rdbFondo_ojo.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setCuello_yugular(rdbCuello_yugular
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setCuello_soplo(rdbCuello_soplo
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setCuello_gland(rdbCuello_gland
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setCuello_masas(rdbCuello_masas
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setTorax_pmi(rdbTorax_pmi.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setTorax_ritmo(rdbTorax_ritmo
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setTorax_soplo(rdbTorax_soplo
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAbdomen_masas(rdbAbdomen_masas
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAbdomen_megalias(rdbAbdomen_megalias
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAbdomen_soplos(rdbAbdomen_soplos
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setGenito_urinario(rdbGenito_urinario
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setColumna_vertebral(rdbColumna_vertebral
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setPiel_anexos(rdbPiel_anexos
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setExtremidades_coloracion(rdbExtremidades_coloracion
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setExtremidades_temperatura(rdbExtremidades_temperatura
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setExtremidades_vellos(rdbExtremidades_vellos
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setExtremidades_pulsos(rdbExtremidades_pulsos
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setExtremidades_tibial(rdbExtremidades_tibial
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setExtremidades_edema(rdbExtremidades_edema
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setExtremidades_ulcera(rdbExtremidades_ulcera
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setNeurologico_sensibilidad(rdbNeurologico_sensibilidad
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setNeurologico_motricidad(rdbNeurologico_motricidad
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setNeurologico_reflejos(rdbNeurologico_reflejos
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setObservaciones_examfisico(tbxObservaciones_examfisico
						.getValue());
		hisc_hipertenso_diabetico.setHipertension_1ria(rdbHipertension_1ria
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setHipertension_2ria(rdbHipertension_2ria
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setDiabetes_tipo2(rdbDiabetes_tipo2
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAlt_glicemia_ayuna(rdbAlt_glicemia_ayuna
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setIntol_hidratos_carbono(rdbIntol_hidratos_carbono
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setHipercolesterolemia(rdbHipercolesterolemia
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setHipertrigliceridemia(rdbHipertrigliceridemia
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setHdl_bajo(rdbHdl_bajo.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setSobrepeso(rdbSobrepeso.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setObesidad_abdominal(rdbObesidad_abdominal
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setObesidad(rdbObesidad.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico.setSind_metabolico(rdbSind_metabolico
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setEnf_cerebro_vascular(rdbEnf_cerebro_vascular
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setIsquemio_transitorio(rdbIsquemio_transitorio
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setRetinopatia(rdbRetinopatia
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setEnf_coronaria(rdbEnf_coronaria
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setIns_cardiaca(rdbIns_cardiaca
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setNefropatia(rdbNefropatia.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico
				.setEnf_arterial_periferica(rdbEnf_arterial_periferica
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setNeuropatia_periferica(rdbNeuropatia_periferica
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setTabaquismo_dfr(rdbTabaquismo_dfr
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAlcoholismo_dfr(rdbAlcoholismo_dfr
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setSedentarismo_dfr(rdbSedentarismo_dfr
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setMalos_habitos_nutricionales(rdbMalos_habitos_nutricionales
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAnalisis(tbxAnalisis.getValue());
		hisc_hipertenso_diabetico.setEjercicio_trat(chbEjercicio_trat
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico.setBajar_peso_trat(chbBajar_peso_trat
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico.setBajar_grasa_trat(chbBajar_grasa_trat
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setDieta_hiposodica_trat(chbDieta_hiposodica_trat.isChecked() ? "S"
						: "N");
		hisc_hipertenso_diabetico
				.setImptcia_adherencia_trat(chbImptcia_adherencia_trat
						.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setAumentar_frutas_trat(chbAumentar_frutas_trat.isChecked() ? "S"
						: "N");
		hisc_hipertenso_diabetico.setManejo_strees_trat(chbManejo_strees_trat
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setImptcia_asistencia_trat(chbImptcia_asistencia_trat
						.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setNo_fumar_trat(chbNo_fumar_trat.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico.setNo_alcohol_trat(chbNo_alcohol_trat
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico.setObservaciones_tract(tbxObservaciones_tract
				.getValue());
		hisc_hipertenso_diabetico
				.setTratamiento_farmacologico(tbxTratamiento_farmacologico
						.getValue());
		hisc_hipertenso_diabetico.setHemograma_parac(chbHemograma_parac
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setPerfil_lipidico_parac(chbPerfil_lipidico_parac.isChecked() ? "S"
						: "N");
		hisc_hipertenso_diabetico
				.setGlicemia_ayunas_parac(chbGlicemia_ayunas_parac.isChecked() ? "S"
						: "N");
		hisc_hipertenso_diabetico
				.setGlicemia_postcarga_parac(chbGlicemia_postcarga_parac
						.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setGlicemia_postpandrial_parac(chbGlicemia_postpandrial_parac
						.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico.setCreatinina_parac(chbCreatinina_parac
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico.setUroanalisis_parac(chbUroanalisis_parac
				.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setHba1c_parac(chbHba1c_parac.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setMicroalbuminuria_parac(chbMicroalbuminuria_parac
						.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico
				.setPotasio_parac(chbPotasio_parac.isChecked() ? "S" : "N");
		hisc_hipertenso_diabetico.setEkg_parac(chbEkg_parac.isChecked() ? "S"
				: "N");

		hisc_hipertenso_diabetico.setTipo_historia(tipo_historia);
		hisc_hipertenso_diabetico
				.setCodigo_historia_anterior(codigo_historia_anterior);
		hisc_hipertenso_diabetico.setTfg((dbxTfg.getText() != null
				&& !dbxTfg.getText().isEmpty() ? dbxTfg.getValue() : 0.00));
		hisc_hipertenso_diabetico
				.setCreatinina((dbxCreatinina.getValue() != null ? dbxCreatinina
						.getValue() : 0.00));

		hisc_hipertenso_diabetico.setLesion_pies(rdbLesion_pies
				.getSelectedItem().getValue().toString());

		hisc_hipertenso_diabetico.setDisnea(rdbDisnea.getSelectedItem()
				.getValue().toString());

		hisc_hipertenso_diabetico.setDisnea_pequenia(rdbDisnea_pequenia
				.getSelectedItem().getValue().toString());

		hisc_hipertenso_diabetico.setDisnea_grande(rdbDisnea_grande
				.getSelectedItem().getValue().toString());

		hisc_hipertenso_diabetico.setDisnea_mediana(rdbDisnea_mediana
				.getSelectedItem().getValue().toString());

		// ----------- Jose ------------
		hisc_hipertenso_diabetico.setRelacion(lbxParentesco_acompanante
				.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setAcompaniante(tbxAcompaniante.getValue());
		hisc_hipertenso_diabetico.setCedula_acomp(tbxId_acompanante.getValue());
		hisc_hipertenso_diabetico.setTel_acompaniante(tbxTelefono_acompanante
				.getValue());
		hisc_hipertenso_diabetico.setEx_fumador(rdbEx_fumador.getSelectedItem()
				.getValue().toString());
		hisc_hipertenso_diabetico
				.setSintomatico_respiratorio(rdbSignos_vitales_sintomaticos_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico
				.setSintomatico_piel(rdbSignos_vitales_sintomaticos_piel
						.getSelectedItem().getValue().toString());
		hisc_hipertenso_diabetico.setV((ibxV.getValue() != null ? ibxV
				.getValue() : 0));

		hisc_hipertenso_diabetico
				.setObservaciones_conciencia(tbxObservaciones_conciencia
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_hidratacion(tbxObservaciones_hidratacion
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_general(tbxObservaciones_general.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_cabeza(tbxObservaciones_cabeza.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_sentidos(tbxObservaciones_sentidos.getValue());
		hisc_hipertenso_diabetico.setObservaciones_fondo(tbxObservaciones_fondo
				.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_yugular(tbxObservaciones_yugular.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_soplo_carotideo(tbxObservaciones_soplo_carotideo
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_tiroides(tbxObservaciones_tiroides.getValue());
		hisc_hipertenso_diabetico.setObservaciones_masas(tbxObservaciones_masas
				.getValue());
		hisc_hipertenso_diabetico.setObservaciones_pmi(tbxObservaciones_pmi
				.getValue());
		hisc_hipertenso_diabetico.setObservaciones_soplo(tbxObservaciones_soplo
				.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_cardiaco(tbxObservaciones_cardiaco.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_masas_abdomen(tbxObservaciones_masas_abdomen
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_megalias(tbxObservaciones_megalias.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_soplo_abdomen(tbxObservaciones_soplo_abdomen
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_genito(tbxObservaciones_genito.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_columna(tbxObservaciones_columna.getValue());
		hisc_hipertenso_diabetico.setObservaciones_piel(tbxObservaciones_piel
				.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_colocacion(tbxObservaciones_coloracion
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_temperatura(tbxObservaciones_temperatura
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_vellos(tbxObservaciones_vellos.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_pulsos(tbxObservaciones_pulsos.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_tibial(tbxObservaciones_tibial.getValue());
		hisc_hipertenso_diabetico.setObservaciones_edema(tbxObservaciones_edema
				.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_ulcera(tbxObservaciones_ulcera.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_sencibilidad(tbxObservaciones_sensibilidad
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_motricidad(tbxObservaciones_motricidad
						.getValue());
		hisc_hipertenso_diabetico
				.setObservaciones_reflejos(tbxObservaciones_reflejos.getValue());
		hisc_hipertenso_diabetico
				.setCodigo_historia_anterior(codigo_historia_anterior);
		hisc_hipertenso_diabetico.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_hipertenso_diabetico.setCreacion_user(usuarios.getCodigo()
				.toString());
		hisc_hipertenso_diabetico.setDelete_date(null);
		hisc_hipertenso_diabetico.setDelete_user(null);
		hisc_hipertenso_diabetico.setUltimo_user(usuarios.getCodigo()
				.toString());

		return hisc_hipertenso_diabetico;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = getBean();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", hisc_hipertenso_diabetico);
				datos.put("admision", admision);
				datos.put("listado_paraclinicos",
						manejadorParaclinicos.obtenerResultados_paraclinicos());
				datos.put("cita_seleccionada", cita);
				datos.put("accion", tbxAccion.getValue());
				datos.put("anio", ConvertidorDatosUtil.convertirDato(anio));
				datos.put("cobrar_agudeza", cobrar_agudeza);

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
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);

				Agudeza_visual agudeza_visual = macroAgudeza_visual
						.obtenerAgudezaVisual();
				agudeza_visual.setAnio(anio);
				datos.put("agudeza_visual", agudeza_visual);

				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction
						.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);

				// Jose
				datos.put("codigo_medico", usuarios.getCodigo().toString());

				getServiceLocator().getHisc_hipertenso_diabeticoService()
						.guardarDatos(datos);

				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}
				mostrarDatosAutorizacion(datos);
				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hisc_hipertenso_diabetico
						.getCodigo_historia());
				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null) {
					receta_ripsAction.mostrarDatos(receta_rips, false);
				}
				// hay que llamar este metodo para validar que salga el boton
				// para imprimir despues de guardar la receta
				receta_ripsAction.validarParaImpresion();

				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
						.get("orden_servicio");
				if (orden_servicio != null) {
					orden_servicioAction.mostrarDatos(orden_servicio);
				}
				// hay que llamar este metodo para validar que salga el boton
				// para imprimir despues de guardar la orden
				orden_servicioAction.validarParaImpresion();

				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision, getInformacionClinica(), this);
				manejadorParaclinicos.limpiarResultados_paraclinicos();
				manejadorParaclinicos.cargarHistorial_resultados();

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
		Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = (Hisc_hipertenso_diabetico) obj;
		try {
			this.nro_ingreso_admision = hisc_hipertenso_diabetico
					.getNro_ingreso();
			infoPacientes.setCodigo_historia(hisc_hipertenso_diabetico
					.getCodigo_historia());

			infoPacientes.setFecha_inicio(hisc_hipertenso_diabetico
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_hipertenso_diabetico.getUltimo_update());

			onMostrarModuloRemisiones();
			initMostrar_datos(hisc_hipertenso_diabetico);

			cargarInformacion_paciente();

			cargarRemisionInterna(hisc_hipertenso_diabetico);
			cargarAgudezaVisual(hisc_hipertenso_diabetico);

			tbxMotivo_consulta.setValue(hisc_hipertenso_diabetico
					.getMotivo_consulta());
			tbxEnfermedad_actual.setValue(hisc_hipertenso_diabetico
					.getEnfermedad_actual());

			Utilidades.seleccionarRadio(rdbCefalea,
					hisc_hipertenso_diabetico.getCefalea());

			// log.info("rdbPorcentaje_fram" + rdbPorcentaje_fram);
			// log.info("hisc_hipertenso_diabetico" +
			// hisc_hipertenso_diabetico);
			Utilidades.seleccionarRadio(rdbPorcentaje_fram,
					hisc_hipertenso_diabetico.getPorcentaje_fram());
			Utilidades.seleccionarRadio(rdbEpistaxis,
					hisc_hipertenso_diabetico.getEpistaxis());
			Utilidades.seleccionarRadio(rdbTinitus,
					hisc_hipertenso_diabetico.getTinitus());
			Utilidades.seleccionarRadio(rdbVertigo,
					hisc_hipertenso_diabetico.getVertigo());
			Utilidades.seleccionarRadio(rdbDisartria,
					hisc_hipertenso_diabetico.getDisartria());
			Utilidades.seleccionarRadio(rdbConvulsion_focal,
					hisc_hipertenso_diabetico.getConvulsion_focal());
			Utilidades.seleccionarRadio(rdbLipotimia,
					hisc_hipertenso_diabetico.getLipotimia());
			Utilidades.seleccionarRadio(rdbFotopsias,
					hisc_hipertenso_diabetico.getFotopsias());
			Utilidades.seleccionarRadio(rdbEscotomas,
					hisc_hipertenso_diabetico.getEscotomas());
			Utilidades.seleccionarRadio(rdbVision_borrosa,
					hisc_hipertenso_diabetico.getVision_borrosa());
			Utilidades.seleccionarRadio(rdbDolor_percordial_esf,
					hisc_hipertenso_diabetico.getDolor_percordial_esf());
			Utilidades.seleccionarRadio(rdbDolor_percordial_rep,
					hisc_hipertenso_diabetico.getDolor_percordial_rep());
			Utilidades.seleccionarRadio(rdbPalpitaciones,
					hisc_hipertenso_diabetico.getPalpitaciones());
			Utilidades.seleccionarRadio(rdbDisnea_esfuerzo,
					hisc_hipertenso_diabetico.getDisnea_esfuerzo());
			Utilidades.seleccionarRadio(rdbDisnea_paroxis,
					hisc_hipertenso_diabetico.getDisnea_paroxis());
			Utilidades.seleccionarRadio(rdbOrtopnea,
					hisc_hipertenso_diabetico.getOrtopnea());
			Utilidades.seleccionarRadio(rdbPoliruria,
					hisc_hipertenso_diabetico.getPoliruria());
			Utilidades.seleccionarRadio(rdbPolidipsia,
					hisc_hipertenso_diabetico.getPolidipsia());
			Utilidades.seleccionarRadio(rdbPolifagia,
					hisc_hipertenso_diabetico.getPolifagia());
			Utilidades.seleccionarRadio(rdbOliguria,
					hisc_hipertenso_diabetico.getOliguria());
			Utilidades.seleccionarRadio(rdbHematuria,
					hisc_hipertenso_diabetico.getHematuria());
			Utilidades.seleccionarRadio(rdbEdemas,
					hisc_hipertenso_diabetico.getEdemas());
			Utilidades.seleccionarRadio(rdbParestesias,
					hisc_hipertenso_diabetico.getParestesias());
			Utilidades.seleccionarRadio(rdbHipoestesias,
					hisc_hipertenso_diabetico.getHipoestesias());
			Utilidades.seleccionarRadio(rdbDisestesias,
					hisc_hipertenso_diabetico.getDisestesias());
			Utilidades.seleccionarRadio(rdbAnestesia,
					hisc_hipertenso_diabetico.getAnestesia());
			Utilidades.seleccionarRadio(rdbDeficit_motor,
					hisc_hipertenso_diabetico.getDeficit_motor());
			Utilidades.seleccionarRadio(rdbDisf_erectil,
					hisc_hipertenso_diabetico.getDisf_erectil());
			Utilidades.seleccionarRadio(rdbDiarrea,
					hisc_hipertenso_diabetico.getDiarrea());
			Utilidades.seleccionarRadio(rdbEstrenimiento,
					hisc_hipertenso_diabetico.getEstrenimiento());
			Utilidades.seleccionarRadio(rdbDishidrosis,
					hisc_hipertenso_diabetico.getDishidrosis());
			Utilidades.seleccionarRadio(rdbAnhidrosis,
					hisc_hipertenso_diabetico.getAnhidrosis());
			Utilidades.seleccionarRadio(rdbClaudicacion,
					hisc_hipertenso_diabetico.getClaudicacion());
			Utilidades.seleccionarRadio(rdbCalambres,
					hisc_hipertenso_diabetico.getCalambres());
			Utilidades.seleccionarRadio(rdbDolor_neuritic,
					hisc_hipertenso_diabetico.getDolor_neuritic());
			Utilidades.seleccionarRadio(rdbApnea_suenio,
					hisc_hipertenso_diabetico.getApnea_suenio());
			tbxObservacion_rs.setValue(hisc_hipertenso_diabetico
					.getObservacion_rs());
			Utilidades.seleccionarRadio(rdbHta,
					hisc_hipertenso_diabetico.getHta());
			ibxAnios_hta.setValue(hisc_hipertenso_diabetico.getAnios_hta());
			ibxMeses_hta.setValue(hisc_hipertenso_diabetico.getMeses_hta());
			Utilidades.seleccionarRadio(rdbDiabetes,
					hisc_hipertenso_diabetico.getDiabetes());
			ibxAnios_diabetes.setValue(hisc_hipertenso_diabetico
					.getAnios_diabetes());
			ibxMeses_diabetes.setValue(hisc_hipertenso_diabetico
					.getMeses_diabetes());

			setValoresMedicamentosAnteriores(listboxMedicamentosAnteriores,
					"mdanteriores", 1,
					hisc_hipertenso_diabetico.getMdant_nombre());
			setValoresMedicamentosAnteriores(listboxMedicamentosAnteriores,
					"mdanteriores", 2,
					hisc_hipertenso_diabetico.getMdant_presentacion());
			setValoresMedicamentosAnteriores(listboxMedicamentosAnteriores,
					"mdanteriores", 3,
					hisc_hipertenso_diabetico.getMdant_dosis());
			setValoresMedicamentosAnteriores(listboxMedicamentosAnteriores,
					"mdanteriores", 4,
					hisc_hipertenso_diabetico.getMdant_uso_hasta());
			setValoresMedicamentosAnteriores(listboxMedicamentosAnteriores,
					"mdanteriores", 5,
					hisc_hipertenso_diabetico.getMdant_causa_suspension());

			tbxMdant_observaciones.setValue(hisc_hipertenso_diabetico
					.getMdant_observaciones());

			setValoresMedicamentosAnteriores(listboxMedicamentosActuales,
					"mdactuales", 1,
					hisc_hipertenso_diabetico.getMdact_nombre());
			setValoresMedicamentosAnteriores(listboxMedicamentosActuales,
					"mdactuales", 2,
					hisc_hipertenso_diabetico.getMdact_presentacion());
			setValoresMedicamentosAnteriores(listboxMedicamentosActuales,
					"mdactuales", 3, hisc_hipertenso_diabetico.getMdact_dosis());
			setValoresMedicamentosAnteriores(listboxMedicamentosActuales,
					"mdactuales", 4,
					hisc_hipertenso_diabetico.getMdact_fecha_inicio());
			setValoresMedicamentosAnteriores(listboxMedicamentosActuales,
					"mdactuales", 5,
					hisc_hipertenso_diabetico.getMdact_reaccion());

			tbxMdact_observaciones.setValue(hisc_hipertenso_diabetico
					.getMdact_observaciones());

			Utilidades.seleccionarRadio(rdbDiabetes_gestacional,
					hisc_hipertenso_diabetico.getDiabetes_gestacional());
			Utilidades.seleccionarRadio(rdbDislipidemia,
					hisc_hipertenso_diabetico.getDislipidemia());
			Utilidades.seleccionarRadio(rdbIsquemico_transitorio,
					hisc_hipertenso_diabetico.getIsquemico_transitorio());
			Utilidades.seleccionarRadio(rdbEnfermedad_cerebrovascular,
					hisc_hipertenso_diabetico.getEnfermedad_cerebrovascular());
			Utilidades.seleccionarRadio(rdbInfarto_miocardio,
					hisc_hipertenso_diabetico.getInfarto_miocardio());
			Utilidades.seleccionarRadio(rdbAngina,
					hisc_hipertenso_diabetico.getAngina());
			Utilidades.seleccionarRadio(rdbEnf_coronaria_pat,
					hisc_hipertenso_diabetico.getEnf_coronaria_pat());
			Utilidades.seleccionarRadio(rdbInsuf_cardiaca,
					hisc_hipertenso_diabetico.getInsuf_cardiaca());
			Utilidades.seleccionarRadio(rdbNefropatia_cronica,
					hisc_hipertenso_diabetico.getNefropatia_cronica());
			Utilidades.seleccionarRadio(rdbEnf_arterial,
					hisc_hipertenso_diabetico.getEnf_arterial());
			Utilidades.seleccionarRadio(rdbAneurisma_aorta,
					hisc_hipertenso_diabetico.getAneurisma_aorta());
			Utilidades.seleccionarRadio(rdbEnfermedad_carotidea,
					hisc_hipertenso_diabetico.getEnfermedad_carotidea());
			tbxObservacion_patologico.setValue(hisc_hipertenso_diabetico
					.getObservacion_patologico());
			tbxOtros_medicos.setValue(hisc_hipertenso_diabetico
					.getOtros_medicos());
			tbxHospitalizaciones.setValue(hisc_hipertenso_diabetico
					.getHospitalizaciones());
			tbxTraumaticos.setValue(hisc_hipertenso_diabetico.getTraumaticos());
			tbxAlergicos.setValue(hisc_hipertenso_diabetico.getAlergicos());
			tbxQuirurgicos.setValue(hisc_hipertenso_diabetico.getQuirurgicos());
			tbxObservaciones_otros.setValue(hisc_hipertenso_diabetico
					.getObservaciones_otros());
			dtbxFum.setText(hisc_hipertenso_diabetico.getFum());
			Utilidades.seleccionarRadio(rdbMenopausia,
					hisc_hipertenso_diabetico.getMenopausia());
			ibxG.setValue(hisc_hipertenso_diabetico.getG());
			ibxP.setValue(hisc_hipertenso_diabetico.getP());
			ibxA.setValue(hisc_hipertenso_diabetico.getA());
			ibxC.setValue(hisc_hipertenso_diabetico.getC());
			tbxAnticoncepcion.setValue(hisc_hipertenso_diabetico
					.getAnticoncepcion());
			Utilidades.seleccionarRadio(rdbHipertension_af,
					hisc_hipertenso_diabetico.getHipertension_af());
			Utilidades.seleccionarRadio(rdbDiabetes_af,
					hisc_hipertenso_diabetico.getDiabetes_af());
			Utilidades.seleccionarRadio(rdbDislipidemia_af,
					hisc_hipertenso_diabetico.getDislipidemia_af());
			Utilidades.seleccionarRadio(rdbEnfermedad_cerebrovascular_af,
					hisc_hipertenso_diabetico
							.getEnfermedad_cerebrovascular_af());
			Utilidades.seleccionarRadio(rdbEnf_arterial_af,
					hisc_hipertenso_diabetico.getEnf_arterial_af());
			Utilidades.seleccionarRadio(rdbEnf_coronaria_af,
					hisc_hipertenso_diabetico.getEnf_coronaria_af());
			Utilidades.seleccionarRadio(rdbEnf_coronaria_hom55,
					hisc_hipertenso_diabetico.getEnf_coronaria_hom55());
			Utilidades.seleccionarRadio(rdbEnf_coronaria_muj65,
					hisc_hipertenso_diabetico.getEnf_coronaria_muj65());
			tbxOtros_observaciones.setValue(hisc_hipertenso_diabetico
					.getOtros_observaciones());
			Utilidades.seleccionarRadio(rdbTabaquismo10,
					hisc_hipertenso_diabetico.getTabaquismo10());
			Utilidades.seleccionarRadio(rdbTabaquismo1020,
					hisc_hipertenso_diabetico.getTabaquismo1020());
			Utilidades.seleccionarRadio(rdbTabaquismo20,
					hisc_hipertenso_diabetico.getTabaquismo20());
			Utilidades.seleccionarRadio(rdbTabaquismo_pasivo,
					hisc_hipertenso_diabetico.getTabaquismo_pasivo());
			Utilidades.seleccionarRadio(rdbAlcohol815,
					hisc_hipertenso_diabetico.getAlcohol815());
			Utilidades.seleccionarRadio(rdbAlcohol_diario,
					hisc_hipertenso_diabetico.getAlcohol_diario());
			Utilidades.seleccionarRadio(rdbSedentarismo,
					hisc_hipertenso_diabetico.getSedentarismo());
			Utilidades.seleccionarRadio(rdbAlta_ingesta_sal,
					hisc_hipertenso_diabetico.getAlta_ingesta_sal());
			Utilidades.seleccionarRadio(rdbAlta_ingesta_grasa,
					hisc_hipertenso_diabetico.getAlta_ingesta_grasa());
			Utilidades.seleccionarRadio(rdbUso_aines,
					hisc_hipertenso_diabetico.getUso_aines());
			Utilidades.seleccionarRadio(rdbUso_sicoactivos,
					hisc_hipertenso_diabetico.getUso_sicoactivos());
			Utilidades.seleccionarRadio(rdbEjercicio_diario,
					hisc_hipertenso_diabetico.getEjercicio_diario());
			Utilidades.seleccionarRadio(rdbEjercicio_ocasional,
					hisc_hipertenso_diabetico.getEjercicio_ocasional());
			Utilidades.seleccionarRadio(rdbStress,
					hisc_hipertenso_diabetico.getStress());
			tbxObservaciones_habitos.setValue(hisc_hipertenso_diabetico
					.getObservaciones_habitos());
			dbxPeso.setValue(hisc_hipertenso_diabetico.getPeso());
			dbxTalla.setValue(hisc_hipertenso_diabetico.getTalla());
			dbxC_abdominal.setValue(hisc_hipertenso_diabetico.getC_abdominal());
			dbxImc.setValue(hisc_hipertenso_diabetico.getImc());
			dbxSup_corporal.setValue(hisc_hipertenso_diabetico
					.getSup_corporal());
			dbxFc_lat_min.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico.getFc_lat_min()));
			dbxFp_pul_min.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico.getFp_pul_min()));
			dbxFr_resp_min.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico.getFr_resp_min()));
			dbxTemp_c.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico.getTemp_c()));
			dbxTa_sentado_bd
					.setValue(ConvertidorDatosUtil
							.convertirDato(hisc_hipertenso_diabetico
									.getTa_sentado_bd()));
			dbxTa_sentado_mmhg.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico
							.getTa_sentado_mmhg()));
			dbxTa_sentado_bi
					.setValue(ConvertidorDatosUtil
							.convertirDato(hisc_hipertenso_diabetico
									.getTa_sentado_bi()));
			dbxTa_sentado_mmhg2.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico
							.getTa_sentado_mmhg2()));
			dbxTa_decubito.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico.getTa_decubito()));
			dbxTa_decubito_mmhg.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico
							.getTa_decubito_mmhg()));
			dbxTa_pie.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico.getTa_pie()));
			dbxTa_pie_mmhg.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_hipertenso_diabetico.getTa_pie_mmhg()));

			tbxObservaciones_framingham.setValue(hisc_hipertenso_diabetico
					.getObservaciones_framingham());
			Utilidades.seleccionarRadio(rdbConciencia,
					hisc_hipertenso_diabetico.getConciencia());
			Utilidades.seleccionarRadio(rdbHidratacion,
					hisc_hipertenso_diabetico.getHidratacion());
			Utilidades.seleccionarRadio(rdbCondicion_general,
					hisc_hipertenso_diabetico.getCondicion_general());
			Utilidades.seleccionarRadio(rdbCabeza_cara,
					hisc_hipertenso_diabetico.getCabeza_cara());
			Utilidades.seleccionarRadio(rdbOrgano_sentidos,
					hisc_hipertenso_diabetico.getOrgano_sentidos());
			Utilidades.seleccionarRadio(rdbFondo_ojo,
					hisc_hipertenso_diabetico.getFondo_ojo());
			Utilidades.seleccionarRadio(rdbCuello_yugular,
					hisc_hipertenso_diabetico.getCuello_yugular());
			Utilidades.seleccionarRadio(rdbCuello_soplo,
					hisc_hipertenso_diabetico.getCuello_soplo());
			Utilidades.seleccionarRadio(rdbCuello_gland,
					hisc_hipertenso_diabetico.getCuello_gland());
			Utilidades.seleccionarRadio(rdbCuello_masas,
					hisc_hipertenso_diabetico.getCuello_masas());
			Utilidades.seleccionarRadio(rdbTorax_pmi,
					hisc_hipertenso_diabetico.getTorax_pmi());
			Utilidades.seleccionarRadio(rdbTorax_ritmo,
					hisc_hipertenso_diabetico.getTorax_ritmo());
			Utilidades.seleccionarRadio(rdbTorax_soplo,
					hisc_hipertenso_diabetico.getTorax_soplo());
			Utilidades.seleccionarRadio(rdbAbdomen_masas,
					hisc_hipertenso_diabetico.getAbdomen_masas());
			Utilidades.seleccionarRadio(rdbAbdomen_megalias,
					hisc_hipertenso_diabetico.getAbdomen_megalias());
			Utilidades.seleccionarRadio(rdbAbdomen_soplos,
					hisc_hipertenso_diabetico.getAbdomen_soplos());
			Utilidades.seleccionarRadio(rdbGenito_urinario,
					hisc_hipertenso_diabetico.getGenito_urinario());
			Utilidades.seleccionarRadio(rdbColumna_vertebral,
					hisc_hipertenso_diabetico.getColumna_vertebral());
			Utilidades.seleccionarRadio(rdbPiel_anexos,
					hisc_hipertenso_diabetico.getPiel_anexos());
			Utilidades.seleccionarRadio(rdbExtremidades_coloracion,
					hisc_hipertenso_diabetico.getExtremidades_coloracion());
			Utilidades.seleccionarRadio(rdbExtremidades_temperatura,
					hisc_hipertenso_diabetico.getExtremidades_temperatura());
			Utilidades.seleccionarRadio(rdbExtremidades_vellos,
					hisc_hipertenso_diabetico.getExtremidades_vellos());
			Utilidades.seleccionarRadio(rdbExtremidades_pulsos,
					hisc_hipertenso_diabetico.getExtremidades_pulsos());
			Utilidades.seleccionarRadio(rdbExtremidades_tibial,
					hisc_hipertenso_diabetico.getExtremidades_tibial());
			Utilidades.seleccionarRadio(rdbExtremidades_edema,
					hisc_hipertenso_diabetico.getExtremidades_edema());
			Utilidades.seleccionarRadio(rdbExtremidades_ulcera,
					hisc_hipertenso_diabetico.getExtremidades_ulcera());
			Utilidades.seleccionarRadio(rdbNeurologico_sensibilidad,
					hisc_hipertenso_diabetico.getNeurologico_sensibilidad());
			Utilidades.seleccionarRadio(rdbNeurologico_motricidad,
					hisc_hipertenso_diabetico.getNeurologico_motricidad());
			Utilidades.seleccionarRadio(rdbNeurologico_reflejos,
					hisc_hipertenso_diabetico.getNeurologico_reflejos());
			tbxObservaciones_examfisico.setValue(hisc_hipertenso_diabetico
					.getObservaciones_examfisico());
			Utilidades.seleccionarRadio(rdbHipertension_1ria,
					hisc_hipertenso_diabetico.getHipertension_1ria());
			Utilidades.seleccionarRadio(rdbHipertension_2ria,
					hisc_hipertenso_diabetico.getHipertension_2ria());
			Utilidades.seleccionarRadio(rdbDiabetes_tipo2,
					hisc_hipertenso_diabetico.getDiabetes_tipo2());
			Utilidades.seleccionarRadio(rdbAlt_glicemia_ayuna,
					hisc_hipertenso_diabetico.getAlt_glicemia_ayuna());
			Utilidades.seleccionarRadio(rdbIntol_hidratos_carbono,
					hisc_hipertenso_diabetico.getIntol_hidratos_carbono());
			Utilidades.seleccionarRadio(rdbHipercolesterolemia,
					hisc_hipertenso_diabetico.getHipercolesterolemia());
			Utilidades.seleccionarRadio(rdbHipertrigliceridemia,
					hisc_hipertenso_diabetico.getHipertrigliceridemia());
			Utilidades.seleccionarRadio(rdbHdl_bajo,
					hisc_hipertenso_diabetico.getHdl_bajo());
			Utilidades.seleccionarRadio(rdbSobrepeso,
					hisc_hipertenso_diabetico.getSobrepeso());
			Utilidades.seleccionarRadio(rdbObesidad_abdominal,
					hisc_hipertenso_diabetico.getObesidad_abdominal());
			Utilidades.seleccionarRadio(rdbObesidad,
					hisc_hipertenso_diabetico.getObesidad());
			Utilidades.seleccionarRadio(rdbSind_metabolico,
					hisc_hipertenso_diabetico.getSind_metabolico());
			Utilidades.seleccionarRadio(rdbEnf_cerebro_vascular,
					hisc_hipertenso_diabetico.getEnf_cerebro_vascular());
			Utilidades.seleccionarRadio(rdbIsquemio_transitorio,
					hisc_hipertenso_diabetico.getIsquemio_transitorio());
			Utilidades.seleccionarRadio(rdbRetinopatia,
					hisc_hipertenso_diabetico.getRetinopatia());
			Utilidades.seleccionarRadio(rdbEnf_coronaria,
					hisc_hipertenso_diabetico.getEnf_coronaria());
			Utilidades.seleccionarRadio(rdbIns_cardiaca,
					hisc_hipertenso_diabetico.getIns_cardiaca());
			Utilidades.seleccionarRadio(rdbNefropatia,
					hisc_hipertenso_diabetico.getNefropatia());
			Utilidades.seleccionarRadio(rdbEnf_arterial_periferica,
					hisc_hipertenso_diabetico.getEnf_arterial_periferica());
			Utilidades.seleccionarRadio(rdbNeuropatia_periferica,
					hisc_hipertenso_diabetico.getNeuropatia_periferica());
			Utilidades.seleccionarRadio(rdbTabaquismo_dfr,
					hisc_hipertenso_diabetico.getTabaquismo_dfr());
			Utilidades.seleccionarRadio(rdbAlcoholismo_dfr,
					hisc_hipertenso_diabetico.getAlcoholismo_dfr());
			Utilidades.seleccionarRadio(rdbSedentarismo_dfr,
					hisc_hipertenso_diabetico.getSedentarismo_dfr());
			Utilidades.seleccionarRadio(rdbMalos_habitos_nutricionales,
					hisc_hipertenso_diabetico.getMalos_habitos_nutricionales());
			tbxAnalisis.setValue(hisc_hipertenso_diabetico.getAnalisis());
			chbEjercicio_trat.setChecked(hisc_hipertenso_diabetico
					.getEjercicio_trat().equals("S") ? true : false);
			chbBajar_peso_trat.setChecked(hisc_hipertenso_diabetico
					.getBajar_peso_trat().equals("S") ? true : false);
			chbBajar_grasa_trat.setChecked(hisc_hipertenso_diabetico
					.getBajar_grasa_trat().equals("S") ? true : false);
			chbDieta_hiposodica_trat.setChecked(hisc_hipertenso_diabetico
					.getDieta_hiposodica_trat().equals("S") ? true : false);
			chbImptcia_adherencia_trat.setChecked(hisc_hipertenso_diabetico
					.getImptcia_adherencia_trat().equals("S") ? true : false);
			chbAumentar_frutas_trat.setChecked(hisc_hipertenso_diabetico
					.getAumentar_frutas_trat().equals("S") ? true : false);
			chbManejo_strees_trat.setChecked(hisc_hipertenso_diabetico
					.getManejo_strees_trat().equals("S") ? true : false);
			chbImptcia_asistencia_trat.setChecked(hisc_hipertenso_diabetico
					.getImptcia_asistencia_trat().equals("S") ? true : false);
			chbNo_fumar_trat.setChecked(hisc_hipertenso_diabetico
					.getNo_fumar_trat().equals("S") ? true : false);
			chbNo_alcohol_trat.setChecked(hisc_hipertenso_diabetico
					.getNo_alcohol_trat().equals("S") ? true : false);
			tbxObservaciones_tract.setValue(hisc_hipertenso_diabetico
					.getObservaciones_tract());
			tbxTratamiento_farmacologico.setValue(hisc_hipertenso_diabetico
					.getTratamiento_farmacologico());
			chbHemograma_parac.setChecked(hisc_hipertenso_diabetico
					.getHemograma_parac().equals("S") ? true : false);
			chbPerfil_lipidico_parac.setChecked(hisc_hipertenso_diabetico
					.getPerfil_lipidico_parac().equals("S") ? true : false);
			chbGlicemia_ayunas_parac.setChecked(hisc_hipertenso_diabetico
					.getGlicemia_ayunas_parac().equals("S") ? true : false);
			chbGlicemia_postcarga_parac.setChecked(hisc_hipertenso_diabetico
					.getGlicemia_postcarga_parac().equals("S") ? true : false);
			chbGlicemia_postpandrial_parac.setChecked(hisc_hipertenso_diabetico
					.getGlicemia_postpandrial_parac().equals("S") ? true
					: false);
			chbCreatinina_parac.setChecked(hisc_hipertenso_diabetico
					.getCreatinina_parac().equals("S") ? true : false);
			chbUroanalisis_parac.setChecked(hisc_hipertenso_diabetico
					.getUroanalisis_parac().equals("S") ? true : false);
			chbHba1c_parac.setChecked(hisc_hipertenso_diabetico
					.getHba1c_parac().equals("S") ? true : false);
			chbMicroalbuminuria_parac.setChecked(hisc_hipertenso_diabetico
					.getMicroalbuminuria_parac().equals("S") ? true : false);
			chbPotasio_parac.setChecked(hisc_hipertenso_diabetico
					.getPotasio_parac().equals("S") ? true : false);
			chbEkg_parac
					.setChecked(hisc_hipertenso_diabetico.getEkg_parac() != null ? hisc_hipertenso_diabetico
							.getEkg_parac().equals("S") ? true : false : false);

			tbxObservaciones_paraclinico.setValue(hisc_hipertenso_diabetico
					.getObservaciones_paraclinico());
			dbxTfg.setValue(hisc_hipertenso_diabetico.getTfg());
			dbxCreatinina.setValue(hisc_hipertenso_diabetico.getCreatinina());

			Utilidades.seleccionarRadio(rdbTorax_pmi,
					hisc_hipertenso_diabetico.getLesion_pies());
			Utilidades.seleccionarRadio(rdbTorax_pmi,
					hisc_hipertenso_diabetico.getDisnea());
			Utilidades.seleccionarRadio(rdbTorax_pmi,
					hisc_hipertenso_diabetico.getDisnea_pequenia());
			Utilidades.seleccionarRadio(rdbTorax_pmi,
					hisc_hipertenso_diabetico.getDisnea_grande());
			Utilidades.seleccionarRadio(rdbTorax_pmi,
					hisc_hipertenso_diabetico.getDisnea_mediana());

			ibxV.setValue(hisc_hipertenso_diabetico.getV());
			Utilidades.seleccionarListitem(lbxParentesco_acompanante,
					hisc_hipertenso_diabetico.getRelacion());
			tbxAcompaniante.setValue(hisc_hipertenso_diabetico
					.getAcompaniante());
			tbxId_acompanante.setValue(hisc_hipertenso_diabetico
					.getCedula_acomp());
			tbxTelefono_acompanante.setValue(hisc_hipertenso_diabetico
					.getTel_acompaniante());
			Utilidades.seleccionarRadio(rdbEx_fumador,
					hisc_hipertenso_diabetico.getEx_fumador());
			Utilidades.seleccionarRadio(
					rdbSignos_vitales_sintomaticos_respiratorio,
					hisc_hipertenso_diabetico.getSintomatico_respiratorio());
			Utilidades.seleccionarRadio(rdbSignos_vitales_sintomaticos_piel,
					hisc_hipertenso_diabetico.getSintomatico_piel());

			FormularioUtil.mostrarObservaciones(rdbConciencia, "A",
					tbxObservaciones_conciencia, row1);
			FormularioUtil.mostrarObservaciones(rdbHidratacion, "A",
					tbxObservaciones_hidratacion, row2);
			FormularioUtil.mostrarObservaciones(rdbTorax_pmi, "A",
					tbxObservaciones_pmi, row2);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_coloracion,
					"A", tbxObservaciones_coloracion, row2);
			FormularioUtil.mostrarObservaciones(rdbCondicion_general, "A",
					tbxObservaciones_general, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_ritmo, "A",
					tbxObservaciones_cardiaco, row3);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_temperatura,
					"A", tbxObservaciones_temperatura, row3);
			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "A",
					tbxObservaciones_cabeza, row4);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_vellos, "A",
					tbxObservaciones_vellos, row4);
			FormularioUtil.mostrarObservaciones(rdbTorax_soplo, "A",
					tbxObservaciones_soplo, row4);
			FormularioUtil.mostrarObservaciones(rdbOrgano_sentidos, "A",
					tbxObservaciones_sentidos, row5);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_pulsos, "A",
					tbxObservaciones_pulsos, row5);
			FormularioUtil.mostrarObservaciones(rdbFondo_ojo, "A",
					tbxObservaciones_fondo, row6);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_masas, "A",
					tbxObservaciones_masas_abdomen, row6);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_tibial, "A",
					tbxObservaciones_tibial, row6);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_megalias, "A",
					tbxObservaciones_megalias, row7);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_edema, "A",
					tbxObservaciones_edema, row7);
			FormularioUtil.mostrarObservaciones(rdbCuello_yugular, "A",
					tbxObservaciones_yugular, row8);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_soplos, "A",
					tbxObservaciones_soplo_abdomen, row8);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_ulcera, "A",
					tbxObservaciones_ulcera, row8);
			FormularioUtil.mostrarObservaciones(rdbCuello_soplo, "A",
					tbxObservaciones_soplo_carotideo, row9);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "A",
					tbxObservaciones_genito, row9);
			FormularioUtil.mostrarObservaciones(rdbCuello_gland, "A",
					tbxObservaciones_tiroides, row10);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "A",
					tbxObservaciones_columna, row10);
			FormularioUtil.mostrarObservaciones(rdbNeurologico_sensibilidad,
					"A", tbxObservaciones_sensibilidad, row10);
			FormularioUtil.mostrarObservaciones(rdbCuello_masas, "A",
					tbxObservaciones_masas, row11);
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "A",
					tbxObservaciones_piel, row11);
			FormularioUtil.mostrarObservaciones(rdbNeurologico_motricidad, "A",
					tbxObservaciones_motricidad, row11);
			FormularioUtil.mostrarObservaciones(rdbNeurologico_reflejos, "A",
					tbxObservaciones_reflejos, row12);

			tbxObservaciones_conciencia.setValue(hisc_hipertenso_diabetico
					.getObservaciones_conciencia());
			tbxObservaciones_hidratacion.setValue(hisc_hipertenso_diabetico
					.getObservaciones_hidratacion());
			tbxObservaciones_general.setValue(hisc_hipertenso_diabetico
					.getObservaciones_general());
			tbxObservaciones_cabeza.setValue(hisc_hipertenso_diabetico
					.getObservaciones_cabeza());
			tbxObservaciones_sentidos.setValue(hisc_hipertenso_diabetico
					.getObservaciones_sentidos());
			tbxObservaciones_fondo.setValue(hisc_hipertenso_diabetico
					.getObservaciones_fondo());
			tbxObservaciones_yugular.setValue(hisc_hipertenso_diabetico
					.getObservaciones_yugular());
			tbxObservaciones_soplo_carotideo.setValue(hisc_hipertenso_diabetico
					.getObservaciones_soplo_carotideo());
			tbxObservaciones_tiroides.setValue(hisc_hipertenso_diabetico
					.getObservaciones_tiroides());
			tbxObservaciones_masas.setValue(hisc_hipertenso_diabetico
					.getObservaciones_masas());
			tbxObservaciones_pmi.setValue(hisc_hipertenso_diabetico
					.getObservaciones_pmi());
			tbxObservaciones_soplo.setValue(hisc_hipertenso_diabetico
					.getObservaciones_soplo());
			tbxObservaciones_cardiaco.setValue(hisc_hipertenso_diabetico
					.getObservaciones_cardiaco());
			tbxObservaciones_masas_abdomen.setValue(hisc_hipertenso_diabetico
					.getObservaciones_masas_abdomen());
			tbxObservaciones_megalias.setValue(hisc_hipertenso_diabetico
					.getObservaciones_megalias());
			tbxObservaciones_soplo_abdomen.setValue(hisc_hipertenso_diabetico
					.getObservaciones_soplo_abdomen());
			tbxObservaciones_genito.setValue(hisc_hipertenso_diabetico
					.getObservaciones_genito());
			tbxObservaciones_columna.setValue(hisc_hipertenso_diabetico
					.getObservaciones_columna());
			tbxObservaciones_piel.setValue(hisc_hipertenso_diabetico
					.getObservaciones_piel());
			tbxObservaciones_coloracion.setValue(hisc_hipertenso_diabetico
					.getObservaciones_colocacion());
			tbxObservaciones_temperatura.setValue(hisc_hipertenso_diabetico
					.getObservaciones_temperatura());
			tbxObservaciones_vellos.setValue(hisc_hipertenso_diabetico
					.getObservaciones_vellos());
			tbxObservaciones_pulsos.setValue(hisc_hipertenso_diabetico
					.getObservaciones_pulsos());
			tbxObservaciones_tibial.setValue(hisc_hipertenso_diabetico
					.getObservaciones_tibial());
			tbxObservaciones_edema.setValue(hisc_hipertenso_diabetico
					.getObservaciones_edema());
			tbxObservaciones_ulcera.setValue(hisc_hipertenso_diabetico
					.getObservaciones_ulcera());
			tbxObservaciones_sensibilidad.setValue(hisc_hipertenso_diabetico
					.getObservaciones_sencibilidad());
			tbxObservaciones_motricidad.setValue(hisc_hipertenso_diabetico
					.getObservaciones_motricidad());
			tbxObservaciones_reflejos.setValue(hisc_hipertenso_diabetico
					.getObservaciones_reflejos());

			cargarImpresionDiagnostica(hisc_hipertenso_diabetico);

			// calcularCoordenadas();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			receta_ripsAction.deshabilitarCampos(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);

			cargarAnexo9_remision(hisc_hipertenso_diabetico);
			inicializarVista(tipo_historia);

			btnImprimir.setVisible(true);

			groupboxConsultar.setVisible(false);
			groupboxEditar.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = (Hisc_hipertenso_diabetico) obj;
		try {
			int result = getServiceLocator()
					.getHisc_hipertenso_diabeticoService().eliminar(
							hisc_hipertenso_diabetico);
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

	public void crearResultadosParaclinicos() {
		manejadorParaclinicos = new ManejadorParaclinicos(
				ManejadorParaclinicos.TIPO_PARACLINICO, vboxParaclinicos,
				"00001", admision.getNro_identificacion(), this);
	}

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularIMCSuperficieCorporal(Doublebox dbxPeso,
			Doublebox dbxTalla, Doublebox dbxImc, Doublebox dbxSup_corporalc) {
		calcularIMC(dbxPeso, dbxTalla, dbxImc);
		calcularSuperficieCorporal(dbxTalla, dbxPeso, dbxSup_corporalc);
	}

	public void calcularCintura_adbominal(Doublebox dbxCintura_abdominal) {
		try {
			double min = 0;
			double max = 0;
			// log.info("sexo" + admision.getPaciente().getSexo().equals("F"));
			if (admision.getPaciente().getSexo().equals("F")) {
				max = 81;
			} else {
				max = 90;
			}
			UtilidadSignosVitales.validarMaxMin(dbxCintura_abdominal, max, min,
					"Valor Anormal", "Valor Normal", false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.HIPERTENSO_DIABETICO);
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HIPERTENSO_DIABETICO);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HIPERTENSO_DIABETICO);

			macroAgudeza_visual.inicializarMacro(this, admision,
					IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);

			crearFilasMedicamentosAnteriores(listboxMedicamentosAnteriores,
					"mdanteriores");
			crearFilasMedicamentosAnteriores(listboxMedicamentosActuales,
					"mdactuales");
			crearResultadosParaclinicos();
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = new Hisc_hipertenso_diabetico();
				hisc_hipertenso_diabetico.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_hipertenso_diabetico.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_hipertenso_diabetico.setIdentificacion(admision
						.getNro_identificacion());
				hisc_hipertenso_diabetico.setNro_ingreso(admision
						.getNro_ingreso());

				hisc_hipertenso_diabetico = getServiceLocator()
						.getHisc_hipertenso_diabeticoService().consultar(
								hisc_hipertenso_diabetico);
				if (hisc_hipertenso_diabetico != null) {
					accionForm(OpcionesFormulario.MOSTRAR,
							hisc_hipertenso_diabetico);
					infoPacientes.setCodigo_historia(hisc_hipertenso_diabetico
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
					macroAgudeza_visual.validarCamposObligatorios();
					rowAgudeza_visual.setVisible(true);
				}
			}
		}
	}

	// sc=peso*talla/3600=sqrt
	public void calcularSuperficieCorporal(Doublebox dbxTalla,
			Doublebox dbxPeso, Doublebox dbxSup_corporalc) {
		if (dbxPeso.getValue() != null & dbxTalla.getValue() != null) {
			Double peso = dbxPeso.getValue() * 100;
			Double talla = dbxTalla.getValue();
			Double supcor = (peso * talla) / 3600;
			dbxSup_corporalc.setValue(Math.sqrt(supcor) / 10);
		}
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			gbxEvaluacion_anterior.setVisible(false);
			gbxAntecendentes_personales.setVisible(true);
			gbxAntecedentes_familiares.setVisible(true);
			gbxHabitos.setVisible(true);
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			gbxEvaluacion_anterior.setVisible(true);
			gbxAntecendentes_personales.setVisible(false);
			gbxAntecedentes_familiares.setVisible(false);
			gbxHabitos.setVisible(false);
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		if (admision.getPaciente().getSexo().equals("M")) {
			gbxGineco_obstetricos.setVisible(false);
		} else {
			gbxGineco_obstetricos.setVisible(true);
		}
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {
						manejadorParaclinicos.cargarHistorial_resultados();
						// log.info("0");
						String val = valorParaclinico(
								admision.getNro_identificacion(), "00002");
						if (val != null && !val.isEmpty()) {
							ibxColesterol_calculadora
									.setValue(ConvertidorDatosUtil
											.convertirDatot(val));
						}

						val = valorParaclinico(
								admision.getNro_identificacion(), "00004");
						if (val != null && !val.isEmpty()) {
							ibxHdl_calculadora.setValue(Integer.valueOf(val));
						}

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							// TODO: DESHABILITAR PARA MANTENER INTEGRIDAD
							// CUANDO SIOS YA NO SEA NECESARIO.
							Boolean previas_sios = UtilidadesSios
									.pacienteTieneRegistro(
											zkWindow.getServiceLocator()
													.getServicio(
															Hisc_historialSiosService.class),
											empresa, admision
													.getNro_identificacion(),
											IVias_ingreso.HIPERTENSO_DIABETICO,
											true);

							if (previas_sios) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica de control/evolucion");
								admision.setPrimera_vez("N");
								gbxEvaluacion_anterior.setVisible(false);
							} else {

								Map<String, Object> parametros = new HashMap<String, Object>();
								parametros
										.put("codigo_empresa", codigo_empresa);
								parametros.put("codigo_sucursal",
										codigo_sucursal);
								parametros.put("identificacion",
										admision.getNro_identificacion());
								parametros.put("nro_identificacion",
										admision.getNro_identificacion());
								parametros
										.put("via_ingreso",
												IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);
								parametros.put("order_desc", "order_desc");

								Boolean previas_manuales = Utilidades
										.tieneHistoriasManuales(parametros,
												generalExtraService);
								if (previas_manuales) {
									inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
									toolbarbuttonTipo_historia
											.setLabel("Creando historia clinica de control/evolucion");
									admision.setPrimera_vez("N");
									gbxEvaluacion_anterior.setVisible(false);
								} else {

									List<Hisc_hipertenso_diabetico> listado_historias = getServiceLocator()
											.getHisc_hipertenso_diabeticoService()
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
								}
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Hisc_hipertenso_diabetico hisc_hd = new Hisc_hipertenso_diabetico();
								hisc_hd.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc_hd.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc_hd.setCodigo_historia(codigo_historia_anterior);
								if (codigo_historia_anterior != null) {
									hisc_hd = getServiceLocator()
											.getHisc_hipertenso_diabeticoService()
											.consultar(hisc_hd);

									if (hisc_hd != null) {
										cargarInformacion_historia_anterior(hisc_hd);
									}
								}

							}
						}

					}
				});

	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

		Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = (Hisc_hipertenso_diabetico) historia_anterior;
		dbxPesoc.setValue(hisc_hipertenso_diabetico.getPeso());
		dbxTallac.setValue(hisc_hipertenso_diabetico.getTalla());
		dbxC_abdominalc.setValue(hisc_hipertenso_diabetico.getC_abdominal());
		dbxImcc.setValue(hisc_hipertenso_diabetico.getImc());
		dbxSup_corporalc.setValue(hisc_hipertenso_diabetico.getSup_corporal());
		dbxFc_lat_minc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getFc_lat_min()));
		dbxFp_pul_minc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getFp_pul_min()));
		dbxFr_resp_minc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getFr_resp_min()));
		dbxTemp_cc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getTemp_c()));
		dbxTa_sentado_bdc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getTa_sentado_bd()));
		dbxTa_sentado_mmhgc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getTa_sentado_mmhg()));
		dbxTa_sentado_bic.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getTa_sentado_bi()));
		dbxTa_sentado_mmhg2c
				.setValue(ConvertidorDatosUtil
						.convertirDato(hisc_hipertenso_diabetico
								.getTa_sentado_mmhg2()));
		dbxTa_decubitoc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getTa_decubito()));
		dbxTa_decubito_mmhgc
				.setValue(ConvertidorDatosUtil
						.convertirDato(hisc_hipertenso_diabetico
								.getTa_decubito_mmhg()));
		dbxTa_piec.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getTa_pie()));
		dbxTa_pie_mmhgc.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_hipertenso_diabetico.getTa_pie_mmhg()));
		ibxAv_lejana_od1c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_lejana_od1()));
		ibxAv_lejana_od2c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_lejana_od2()));
		ibxAv_lejana_oi1c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_lejana_oi1()));
		ibxAv_lejana_oi2c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_lejana_oi2()));
		ibxAv_cercana_od1c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_cercana_od1()));
		ibxAv_cercana_od2c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_cercana_od2()));
		ibxAv_cercana_oi1c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_cercana_oi1()));
		ibxAv_cercana_oi2c.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_hipertenso_diabetico.getAv_cercana_oi2()));
		codigo_historia_anterior = hisc_hipertenso_diabetico
				.getCodigo_historia();

		// manuel
		tbxTratamiento_farmacologicoC.setValue(hisc_hipertenso_diabetico
				.getTratamiento_farmacologico());
		cargarInformacionRevisionSistema(hisc_hipertenso_diabetico);

	}

	public void cargarInformacionRevisionSistema(Object historia_anterior) {
		Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = (Hisc_hipertenso_diabetico) historia_anterior;
		Utilidades.seleccionarRadio(rdbCefalea_v,
				hisc_hipertenso_diabetico.getCefalea());
		Utilidades.seleccionarRadio(rdbPolifagia_v,
				hisc_hipertenso_diabetico.getPolifagia());
		Utilidades.seleccionarRadio(rdbDisf_erectil_v,
				hisc_hipertenso_diabetico.getDisf_erectil());
		Utilidades.seleccionarRadio(rdbEpistaxis_v,
				hisc_hipertenso_diabetico.getEpistaxis());
		Utilidades.seleccionarRadio(rdbDolor_percordial_esf_v,
				hisc_hipertenso_diabetico.getDolor_percordial_esf());
		Utilidades.seleccionarRadio(rdbOliguria_v,
				hisc_hipertenso_diabetico.getOliguria());
		Utilidades.seleccionarRadio(rdbDiarrea_v,
				hisc_hipertenso_diabetico.getDiarrea());
		Utilidades.seleccionarRadio(rdbTinitus_v,
				hisc_hipertenso_diabetico.getTinitus());
		Utilidades.seleccionarRadio(rdbDolor_percordial_rep_v,
				hisc_hipertenso_diabetico.getDolor_percordial_rep());
		Utilidades.seleccionarRadio(rdbHematuria_v,
				hisc_hipertenso_diabetico.getHematuria());
		Utilidades.seleccionarRadio(rdbEstrenimiento_v,
				hisc_hipertenso_diabetico.getEstrenimiento());
		Utilidades.seleccionarRadio(rdbVertigo_v,
				hisc_hipertenso_diabetico.getVertigo());
		Utilidades.seleccionarRadio(rdbPalpitaciones_v,
				hisc_hipertenso_diabetico.getPalpitaciones());
		Utilidades.seleccionarRadio(rdbEdemas_v,
				hisc_hipertenso_diabetico.getEdemas());
		Utilidades.seleccionarRadio(rdbDishidrosis_v,
				hisc_hipertenso_diabetico.getDishidrosis());
		Utilidades.seleccionarRadio(rdbDisartria_v,
				hisc_hipertenso_diabetico.getDisartria());
		Utilidades.seleccionarRadio(rdbDisnea_esfuerzo_v,
				hisc_hipertenso_diabetico.getDisnea_esfuerzo());
		Utilidades.seleccionarRadio(rdbParestesias_v,
				hisc_hipertenso_diabetico.getParestesias());
		Utilidades.seleccionarRadio(rdbAnhidrosis_v,
				hisc_hipertenso_diabetico.getAnhidrosis());
		Utilidades.seleccionarRadio(rdbConvulsion_focal_v,
				hisc_hipertenso_diabetico.getConvulsion_focal());
		Utilidades.seleccionarRadio(rdbDisnea_paroxis_v,
				hisc_hipertenso_diabetico.getDisnea_paroxis());
		Utilidades.seleccionarRadio(rdbHipoestesias_v,
				hisc_hipertenso_diabetico.getHipoestesias());
		Utilidades.seleccionarRadio(rdbClaudicacion_v,
				hisc_hipertenso_diabetico.getClaudicacion());
		Utilidades.seleccionarRadio(rdbLipotimia_v,
				hisc_hipertenso_diabetico.getLipotimia());
		Utilidades.seleccionarRadio(rdbOrtopnea_v,
				hisc_hipertenso_diabetico.getOrtopnea());
		Utilidades.seleccionarRadio(rdbDisestesias_v,
				hisc_hipertenso_diabetico.getDisestesias());
		Utilidades.seleccionarRadio(rdbCalambres_v,
				hisc_hipertenso_diabetico.getCalambres());
		Utilidades.seleccionarRadio(rdbFotopsias_v,
				hisc_hipertenso_diabetico.getFotopsias());
		Utilidades.seleccionarRadio(rdbPoliruria_v,
				hisc_hipertenso_diabetico.getPoliruria());
		Utilidades.seleccionarRadio(rdbAnestesia_v,
				hisc_hipertenso_diabetico.getAnestesia());
		Utilidades.seleccionarRadio(rdbDolor_neuritic_v,
				hisc_hipertenso_diabetico.getDolor_neuritic());
		Utilidades.seleccionarRadio(rdbEscotomas_v,
				hisc_hipertenso_diabetico.getEscotomas());
		Utilidades.seleccionarRadio(rdbPolidipsia_v,
				hisc_hipertenso_diabetico.getPolidipsia());
		Utilidades.seleccionarRadio(rdbDeficit_motor_v,
				hisc_hipertenso_diabetico.getDeficit_motor());
		Utilidades.seleccionarRadio(rdbApnea_suenio_v,
				hisc_hipertenso_diabetico.getApnea_suenio());
		tbxObservacion_rs_v.setValue(hisc_hipertenso_diabetico
				.getObservacion_rs());

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = (Hisc_hipertenso_diabetico) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					idsExcluyentes);
			if (hisc_hipertenso_diabetico.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			if (hisc_hipertenso_diabetico.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(true);
		}

		codigo_historia_anterior = hisc_hipertenso_diabetico
				.getCodigo_historia_anterior();
		tipo_historia = hisc_hipertenso_diabetico.getTipo_historia();
	}

	public void onCalcular_creatinina() {
		List<Presultados_paraclinicos> listado_paraclinicos = manejadorParaclinicos
				.obtenerResultados_paraclinicos();
		String resultado_creatinina = "";

		for (Presultados_paraclinicos presultados_paraclinicos : listado_paraclinicos) {
			// log.info("1: " + presultados_paraclinicos.getCodigo_examen());
			if (presultados_paraclinicos.getCodigo_examen().equals("00040")
					|| presultados_paraclinicos.getCodigo_examen().equals(
							"00011")) {
				resultado_creatinina = presultados_paraclinicos.getResultado();
				// log.info("2: " + resultado_creatinina);
			}
		}

		// log.info("3: " + resultado_creatinina);
		if (resultado_creatinina != null && !resultado_creatinina.isEmpty()) {
			dbxCreatinina.setValue(Double.valueOf(resultado_creatinina));

		} else {
			String val = valorParaclinico(admision.getNro_identificacion(),
					"00011");
			if (val != null && !val.isEmpty()) {
				dbxCreatinina.setValue(Double.valueOf(val));
				dbxTfg.setStyle("text-transform:uppercase;background-color:white");
			} else {
				dbxCreatinina.setValue(Double.valueOf(0.00));
				String mensaje = "No presenta Examen de Creatinina";
				Clients.showNotification(mensaje,
						Clients.NOTIFICATION_TYPE_WARNING, dbxTfg,
						"end_before", 4000, true);
				dbxTfg.setStyle("text-transform:uppercase;background-color:#FFBEA8");
				dbxTfg.setFocus(false);

			}

		}

		onCalcularTasaFiltracionGlomerural();

	}

	public void onCalcularTasaFiltracionGlomerural() {
		Paciente paciente = admision.getPaciente();
		try {
			dbxTfg.setConstraint("");
			dbxTfg.setValue(null);
			Double talla = dbxTalla.getValue();
			Double peso = dbxPeso.getValue();
			Double superfice_corporal = dbxSup_corporal.getValue();
			Double creatinina = dbxCreatinina.getValue();
			Integer edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"1", false));
			Boolean mujer = paciente.getSexo().equalsIgnoreCase("F");
			Boolean afro = paciente.getPertenencia_etnica().equals("5");

			if (talla != null && peso != null && superfice_corporal != null
					&& creatinina != null
					&& paciente.getFecha_nacimiento() != null
					&& paciente.getSexo() != null) {

				Double tfg_final = 0d;

				if (afro) {
					tfg_final = UtilidadSignosVitales.calculoTFG(creatinina,
							edad, mujer, afro);
				} else {
					tfg_final = UtilidadSignosVitales.calculoTFGCockcroft(
							creatinina, edad, peso, mujer);
				}

				// double tfg_parcial = ((140 - edad_anios) *
				// peso.doubleValue()) / (72 * creatinina.doubleValue());
				// double tfg_final = tfg_parcial;
				// if (paciente.getSexo().toUpperCase().equals("F")) {
				// tfg_final = tfg_final * 0.85;
				// }
				// double tfg_x = (tfg_final * 1.73) /
				// superfice_corporal.doubleValue();
				dbxTfg.setValue(tfg_final);
				UtilidadSignosVitales.validarTFG(dbxTfg);

			} else {

				if (superfice_corporal == null) {

					String mensaje = "Falta Calcular Superficie Corporal";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, dbxTfg,
							"end_before", 4000, true);
					dbxTfg.setStyle("text-transform:uppercase;background-color:#FFBEA8");
					dbxTfg.setFocus(false);

				} else {
					dbxTfg.setStyle("text-transform:uppercase;background-color:white");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public String valorParaclinico(String nro_documento, String diagnostico) {

		Presultados_paraclinicos ultimo = new Presultados_paraclinicos();
		ultimo.setCodigo_empresa(empresa.getCodigo_empresa());
		ultimo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		ultimo.setNro_documento(nro_documento);
		ultimo.setCodigo_examen(diagnostico);
		// log.info("antes " + ultimo);

		ultimo = getServiceLocator().getPresultados_paraclinicosService()
				.mostrar_ultimo(ultimo);
		// log.info("despues " + ultimo);

		return (ultimo != null ? ultimo.getResultado() : null);
	}

	public void cargarDatosCalculadora() {
		Map<String, Integer> a = Util.getEdadYYYYMMDD(admision.getPaciente()
				.getFecha_nacimiento());
		ibxEdad_calculadora.setValue(a.get("anios"));
		tbxSexo_calculadora.setValue(admision.getPaciente().getSexo());
		dbxSistolica_calculadora.setValue(dbxTa_sentado_bd.getValue());
		rdTabaquismo_calculadora.setSelectedIndex(rdbTabaquismo_dfr
				.getSelectedIndex());
		if (a.get("anios") != null && admision.getPaciente().getSexo() != null
				&& dbxSistolica_calculadora.getValue() != null
				&& dbxSistolica_calculadora.getValue() > 0
				&& ibxColesterol_calculadora.getValue() != null
				&& ibxHdl_calculadora.getValue() != null) {
			Integer puntos = CalculoMedico.calcularRiesgoCardioVascular(
					admision.getPaciente().getSexo(), a.get("anios"),
					ibxColesterol_calculadora.getValue(),
					ibxHdl_calculadora.getValue(),
					dbxSistolica_calculadora.getValue(),
					(rdTratamiento_calculadora.getSelectedIndex() == 0),
					(rdTabaquismo_calculadora.getSelectedIndex() == 0));
			tbxPuntaje_calculadora.setValue("" + puntos);
			String[] res = CalculoMedico.convertirPuntosFramingham(admision
					.getPaciente().getSexo(), puntos);
			tbxRiesgo_calculadora.setValue(res[0]);
			// if (condition) {
			//
			// }
			// log.info("valor de res>>>>>>>>>>" + res[0]);
			rdbPorcentaje_fram.setSelectedIndex(ConvertidorDatosUtil
					.convertirDatot(res[1]));
			// log.info("valor de res>>>>>>>>>>" + res[1]);
			// log.info("integer de res>>>>>>>>>>"
			// + ConvertidorDatosUtil.convertirDatot(res[1]));
			// log.info("get selectec>>>>>>>>>>"
			// + rdbPorcentaje_fram.getSelectedIndex());
		}
	}

	public void onMostrarModuloFarmacologico() throws Exception {
		if (receta_ripsAction != null) {
			receta_ripsAction.detach();
		}
		// log.info("creando la ventana receta_ripsAction");

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
		if (orden_servicioAction != null) {
			orden_servicioAction.detach();
		}
		// if (!cargo_farmacologico) {
		// log.info("creando la ventana receta_ripsAction");

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
			Hisc_hipertenso_diabetico hisc_hipertenso_diabetico)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);

		impresion_diagnostica.setCodigo_historia(hisc_hipertenso_diabetico
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	private void cargarAnexo9_remision(
			Hisc_hipertenso_diabetico hisc_hipertenso_diabetico) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_hipertenso_diabetico
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		// log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		boolean creada = false;
		if (anexo9_entidad != null) {
			creada = true;
		}
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
		if (creada) {
			remisiones_externasAction.getBotonGenerar().setVisible(false);
		}
		remisiones_externasAction.setNro_historia(hisc_hipertenso_diabetico
				.getCodigo_historia());
	}

	private void cargarRemisionInterna(
			Hisc_hipertenso_diabetico hisc_hipertenso_diabetico)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_hipertenso_diabetico
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_hipertenso_diabetico
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_hipertenso_diabetico
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private void cargarAgudezaVisual(
			Hisc_hipertenso_diabetico hisc_hipertenso_diabetico)
			throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_historia(hisc_hipertenso_diabetico
				.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(hisc_hipertenso_diabetico
				.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(hisc_hipertenso_diabetico
				.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null) {
			remisiones_externasAction.detach();
		}

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
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("PYP HIPERTENSO DIABETICO");

		return serivicio1.toString();
	}

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(tbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (tbxEnfermedad_actual.getValue() != null
				&& !tbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxEnfermedad_actual.getValue()).append("\n");
		}

		informacion_clinica.append("- SIGNOS VITALES\n");
		informacion_clinica.append("\tPeso(kg): ").append(dbxPeso.getText())
				.append("\n");
		informacion_clinica.append("\tTalla: ").append(dbxTalla.getText())
				.append("\n");
		informacion_clinica.append("\tIMC: ").append(dbxImc.getText());
		informacion_clinica.append("\tSuperficie corporal: ")
				.append(dbxSup_corporal.getText()).append("\n");
		informacion_clinica.append("\tCircunferencia abdominal: ")
				.append(dbxC_abdominal.getValue()).append("\n");
		informacion_clinica.append("\tFrecuencia cardiaca: ")
				.append(dbxFc_lat_min.getText()).append("\n");
		informacion_clinica.append("\tFrecuencia pulmonar: ")
				.append(dbxFp_pul_min.getText()).append("\n");
		informacion_clinica.append("\tFrecuencia respiratoria: ")
				.append(dbxFr_resp_min.getValue()).append("\n");
		informacion_clinica.append("\tTemperatura: ")
				.append(dbxTemp_c.getValue()).append("\n");

		informacion_clinica.append("- DIAGNOSTICOS").append("\n");
		Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
				.obtenerImpresionDiagnostica();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = getServiceLocator().getServicio(GeneralExtraService.class)
				.consultar(cie);

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
			cie = getServiceLocator().getServicio(GeneralExtraService.class)
					.consultar(cie);

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
			cie = getServiceLocator().getServicio(GeneralExtraService.class)
					.consultar(cie);

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
			cie = getServiceLocator().getServicio(GeneralExtraService.class)
					.consultar(cie);

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

	public Boolean validarExamen_fisico() {
		validar_observacion = false;
		AbstractComponent[] abstractComponents = new AbstractComponent[] {
				rdbConciencia, rdbHidratacion, rdbCondicion_general,
				rdbCabeza_cara, rdbOrgano_sentidos, rdbFondo_ojo,
				rdbCuello_yugular, rdbCuello_soplo, rdbCuello_gland,
				rdbCuello_masas, rdbTorax_pmi, rdbTorax_ritmo, rdbTorax_soplo,
				rdbAbdomen_masas, rdbAbdomen_megalias, rdbAbdomen_soplos,
				rdbGenito_urinario, rdbColumna_vertebral, rdbPiel_anexos,
				rdbExtremidades_coloracion, rdbExtremidades_temperatura,
				rdbExtremidades_vellos, rdbExtremidades_pulsos,
				rdbExtremidades_tibial, rdbExtremidades_edema,
				rdbExtremidades_ulcera, rdbNeurologico_sensibilidad,
				rdbNeurologico_motricidad, rdbNeurologico_reflejos };

		for (AbstractComponent abstractComponent : abstractComponents) {

			if (((Radiogroup) abstractComponent).getSelectedItem().getValue()
					.equals("A")) {
				validar_observacion = true;
			}
		}

		return validar_observacion;
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
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxImc.setValue(enfermera_signos.getImc());
			dbxFc_lat_min.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxFr_resp_min.setValue(enfermera_signos
					.getFrecuencia_respiratoria());
			dbxFp_pul_min.setValue(enfermera_signos.getFrecuencia_pulmonar());
			dbxTemp_c.setValue(enfermera_signos.getTemperatura());
			dbxC_abdominal.setValue(enfermera_signos
					.getCircunferencia_abdominal());
			dbxSup_corporal.setValue(enfermera_signos.getSuperficie_corporal());

			dbxTa_sentado_bd.setValue(enfermera_signos.getSentado_bd1());
			dbxTa_sentado_mmhg.setValue(enfermera_signos.getSentado_bd2());
			dbxTa_sentado_bi.setValue(enfermera_signos.getSentado_bi1());
			dbxTa_sentado_mmhg2.setValue(enfermera_signos.getSentado_bd2());
			dbxTa_decubito.setValue(enfermera_signos.getDe_cubito1());
			dbxTa_decubito_mmhg.setValue(enfermera_signos.getDe_cubito2());
			dbxTa_pie.setValue(enfermera_signos.getDe_pie1());
			dbxTa_pie_mmhg.setValue(enfermera_signos.getDe_pie2());

			calcularIMCSuperficieCorporal(dbxPeso, dbxTalla, dbxImc,
					dbxSup_corporal);
			calcularCintura_adbominal(dbxC_abdominal);
			cargarDatosCalculadora();

		}
	}

	/*
	 * public void imprimir() throws Exception { String nro_historia =
	 * infoPacientes.getCodigo_historia(); if (nro_historia.equals("")) {
	 * Messagebox.show("La historia no se ha guardado aún", "Informacion ..",
	 * Messagebox.OK, Messagebox.INFORMATION); return; }
	 * 
	 * Map paramRequest = new HashMap(); paramRequest.put("name_report",
	 * "Historia_pyp_hipertenso"); paramRequest.put("nro_historia",
	 * nro_historia); paramRequest.put("formato",
	 * lbxFormato.getSelectedItem().getValue() .toString());
	 * 
	 * paramRequest.put("codigo_medico", admision.getCodigo_medico());
	 * 
	 * paramRequest.put("sexo", admision.getPaciente().getSexo());
	 * 
	 * Map<String, Integer> a = Util.getEdadYYYYMMDD(admision.getPaciente()
	 * .getFecha_nacimiento()); paramRequest.put("edad", a.get("anios"));
	 * paramRequest.put("sistolica", dbxTa_sentado_bd.getValue());
	 * paramRequest.put("tabaquismo",
	 * rdbTabaquismo_dfr.getSelectedItem().getValue().toString());
	 * 
	 * //log.info(a.get("anios")+"---"+dbxTa_sentado_bd.getValue()+"---"+
	 * rdbTabaquismo_dfr.getSelectedIndex());
	 * 
	 * if (a.get("anios") != null && admision.getPaciente().getSexo() != null &&
	 * dbxTa_sentado_bd.getValue() != null && dbxTa_sentado_bd.getValue() > 0 &&
	 * ibxColesterol_calculadora.getValue() != null &&
	 * ibxHdl_calculadora.getValue() != null) {
	 * 
	 * paramRequest.put("colesterol", ibxColesterol_calculadora.getValue());
	 * paramRequest.put("hdl", ibxHdl_calculadora.getValue());
	 * 
	 * //log.info(ibxColesterol_calculadora.getValue()+"---"+ibxHdl_calculadora.
	 * getValue());
	 * 
	 * //log.info(rdbTabaquismo_dfr.getSelectedIndex() == 0);
	 * 
	 * Integer puntos = CalculoMedico.calcularRiesgoCardioVascular(
	 * admision.getPaciente().getSexo(), a.get("anios"),
	 * ibxColesterol_calculadora.getValue(), ibxHdl_calculadora.getValue(),
	 * dbxTa_sentado_bd.getValue(), true,(rdbTabaquismo_dfr.getSelectedIndex()
	 * == 0));
	 * 
	 * //log.info(puntos);
	 * 
	 * paramRequest.put("puntos", puntos);
	 * 
	 * String[] res = CalculoMedico.convertirPuntosFramingham(admision
	 * .getPaciente().getSexo(), puntos); String riesgo = res[0];
	 * paramRequest.put("riesgo", riesgo);
	 * 
	 * Integer porcentaje = ConvertidorDatosUtil.convertirDatot(res[1]);
	 * paramRequest.put("porcentaje", porcentaje);
	 * 
	 * //log.info(porcentaje+"---"+riesgo+"---"+puntos);
	 * 
	 * } Component componente = Executions.createComponents(
	 * "/pages/printInformes.zul", this, paramRequest); final Window window =
	 * (Window) componente; window.setMode("modal");
	 * window.setMaximizable(true); window.setMaximized(true);
	 * 
	 * // Clients.evalJavaScript("window.open('"+request.getContextPath()+
	 * "/pages/printInformes.zul"+parametros_pagina+"', '_blank');"); }
	 */
	public void imprimir() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
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
			// parametros_pagina+="&codigo_medico="+usuarios.getCodigo().toString();

			Clients.evalJavaScript("window.open(\"" + request.getContextPath()
					+ "/pages/reports/hisc_hipertenso_diabetico2_reporte.zul"
					+ parametros_pagina + "\", '_blank');");
		}
	}

}
