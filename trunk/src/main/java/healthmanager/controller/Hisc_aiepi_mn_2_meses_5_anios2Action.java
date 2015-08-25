/*
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Cuadros_aiepi_estado;
import healthmanager.modelo.bean.Cuadros_aiepi_sugerencias;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses_5_anios;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Anexo9_entidadService;
import healthmanager.modelo.service.Cuadros_aiepi_estadoService;
import healthmanager.modelo.service.Cuadros_aiepi_sugerenciasService;
import healthmanager.modelo.service.Hisc_aiepi_mn_2_meses_5_aniosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
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
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IHistorias_clinicas;
import com.framework.constantes.ITipos_coordenada;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CuadroAIEPIMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculadorUtil;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_aiepi_mn_2_meses_5_anios2Action extends
		HistoriaClinicaModel implements IHistoria_generica {
		
	private static Logger log = Logger
			.getLogger(Hisc_aiepi_mn_2_meses_5_aniosAction.class);
	private Hisc_aiepi_mn_2_meses_5_aniosService hisc_aiepi_mn_2_meses_5_aniosService;
	// Componentes //
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View
	private Listbox lbxTipoHistoria;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;
	@View
	private Groupbox groupboxEditar;
	@View
	private Textbox tbxId_acompanante;
	@View
	private Textbox tbxNombres_acompanante;
	@View
	private Textbox tbxApellidos_acompanante;
	@View
	private Listbox lbxParentesco_acompanante;
	@View
	private Textbox tbxTelefono_acompanante;
	@View
	private Textbox tbxNombres_padre;
	@View
	private Textbox tbxApellidos_padre;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxOcupacion_padre;
	@View
	private Textbox tbxInfoOcupacion_padre;
	@View
	private Toolbarbutton btnLimpiarOcupacion_padre;

	@View
	private Intbox ibxEdad_padre;
	@View
	private Textbox tbxNombres_madre;
	@View
	private Textbox tbxApellidos_madre;
	@View
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxOtro_acompanante;
	@View
	private Textbox tbxId_madre;
	@View
	private Textbox tbxId_padre;
	@View
	private Textbox tbxTelefono_madre;
	@View
	private Textbox tbxTelefono_padre;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxOcupacion_madre;
	@View
	private Textbox tbxInfoOcupacion_madre;
	@View
	private Toolbarbutton btnLimpiarOcupacion_madre;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Textbox tbxComo_fue_el_embarazo;
	@View
	private Intbox ibxCuanto_duro_embarazo;
	@View
	private Textbox tbxComo_fue_el_parto;
	@View
	private Doublebox dbxPeso_al_nacer;
	@View
	private Doublebox dbxTalla_al_nacer;
	@View
	private Textbox tbxPresento_algun_problema_neonatal;
	@View
	private Textbox tbxEnfermedades_previas;
	@View
	private Textbox tbxHospitalizaciones;
	@View
	private Doublebox dbxSignos_vitales_fc;
	@View
	private Doublebox dbxSignos_vitales_fr;
	@View
	private Doublebox dbxSignos_vitales_talla;
	@View
	private Doublebox dbxSignos_vitales_peso;
	@View
	private Doublebox dbxSignos_vitales_pc;
	@View
	private Doublebox dbxSignos_vitales_imc;
	@View
	private Doublebox dbxSignos_vitales_taxilar;
	@View
	private Doublebox dbxSignos_vitales_oximetria;
	@View
	private Radiogroup rdbTiene_tos_o_dificultad_para_respirar;
	@View
	private Radiogroup rdbTiene_diarrea;
	@View
	private Radiogroup rdbTiene_fiebre;
	@View
	private Radiogroup rdbTiene_problemas_de_oido;
	@View
	private Radiogroup rdbTiene_un_problema_de_garganta;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_respiratorio;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_piel;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;
	@View
	private Toolbarbutton btGuardar;
	@View
	private Toolbarbutton btnCancelar;
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;
	@View
	private Doublebox dbxP_e_de;
	@View
	private Doublebox dbxT_e_de;
	@View
	private Doublebox dbxP_t_de;
	@View
	private Doublebox dbxPC_e_de;
	@View
	private Doublebox dbxImc_e_de;

	@View
	private Textbox tbxObservaciones_cuadro1;
	@View
	private Textbox tbxObservaciones_cuadro2;
	@View
	private Textbox tbxObservaciones_cuadro3;
	@View
	private Textbox tbxObservaciones_cuadro4;
	@View
	private Textbox tbxObservaciones_cuadro5;
	@View
	private Textbox tbxObservaciones_cuadro6;
	@View
	private Textbox tbxObservaciones_cuadro7;
	@View
	private Textbox tbxObservaciones_cuadro8;
	@View
	private Textbox tbxObservaciones_cuadro9;
	@View
	private Textbox tbxObservaciones_cuadro10;
	
	private Opciones_via_ingreso opciones_via_ingreso;
	private Admision admision;
	private Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_2_5;
	private String tipo_historia;
	private Long codigo_historia_anterior;
	private Paciente paciente;
	private ESexo sexo;
	private Timestamp fecha;
	private String ihistoria_clinica;

	// groubox de antecedentes vacunales
	@View
	private Row row_ultima_dosis;
	@View
	private Checkbox cbx_influenza;
	@View
	private Datebox dbx_ultima_dosis;
	@View
	private Row row_fiebre_amarilla;
	@View
	private Intbox ibx_edad;
	@View
	private Checkbox cbx_fiebre_amarilla;
	@View
	private Checkbox cbx_bcg__1;
	@View
	private Checkbox cbx_hepatitis_b_rn;
	@View
	private Checkbox cbx_hepatitis_b_1;
	@View
	private Checkbox cbx_hepatitis_b_2;
	@View
	private Checkbox cbx_hepatitis_b_3;
	@View
	private Checkbox cbx_vop_1;
	@View
	private Checkbox cbx_vop_2;
	@View
	private Checkbox cbx_vop_3;
	@View
	private Checkbox cbx_vop_r1;
	@View
	private Checkbox cbx_vop_r2;
	@View
	private Checkbox cbx_rotavirus_1;
	@View
	private Checkbox cbx_rotavirus_2;
	@View
	private Textbox tbx_otras_vacunas;
	@View
	private Checkbox cbx_dtp_1;
	@View
	private Checkbox cbx_dtp_2;
	@View
	private Checkbox cbx_dtp_3;
	@View
	private Checkbox cbx_dtp_r1;
	@View
	private Checkbox cbx_dtp_r2;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_b1;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_b2;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_b3;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_r1;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_r2;
	@View
	private Checkbox cbx_strectococo_neumonia_1;
	@View
	private Checkbox cbx_strectococo_neumonia_2;
	@View
	private Checkbox cbx_strectococo_neumonia_3;
	@View
	private Checkbox cbx_srp_2;
	@View
	private Checkbox cbx_srp_1;
	@View
	private Datebox dtxVolver_consulta_control;
	// @View private Textbox tbx_examen_fisico;
	// @View private Textbox tbx_servicio_inmediato;
	// @View private Textbox tbx_volver_sonsulta_de_control;
	// @View private Textbox tbx_medidas_preventivas_especificas;
	// @View private Textbox tbx_recomendaciones_buen_trato;
	// @View private Textbox tbx_tratamineto;
	// @View private Textbox tbx_recomendaciones_alimentacion;
	// @View private Textbox tbx_evolucion_servicio;
	@View
	private Row row_cuadro_aiepi_2;
	@View
	private Row row_cuadro_aiepi_3;
	@View
	private Row row_cuadro_aiepi_4;
	@View
	private Row row_cuadro_aiepi_5;
	@View
	private Row row_cuadro_aiepi_6;
	@View
	private Button btnCalcularPesoEdad;
	@View
	private Button btnCalcularPerimetroCefalicoEdad;
	@View
	private Button btnCalcularPesoTalla;
	@View
	private Button btnCalcularTallaEdad;
	@View
	private Button btnCalcularImcEdad;
	@View
	private Button btnGraficarPesoTalla;
	@View
	private Button btnGraficarTallaEdad;
	@View
	private Button btnGraficarImcEdad;
	@View
	private Button btnMostrarTablaPesoEdad;
	@View
	private Button btnMostrarTablaPerimetroCefalicoEdad;
	@View
	private Button btnGraficarPesoEdad;
	@View
	private Button btnGraficarPerimetroCefalicoEdad;
	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;

	// Modulo Farmacologico y Autorizaciones
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divModuloOrdenamiento;
	
	@View
	private Radiogroup rdbNino_registrado;
	@View
	private Listbox lbxHemoclasificacion;
	@View
	private Checkbox chbSignos_no_puede_beber;
	@View
	private Button btnCuadro1_boton1;
	@View
	private Checkbox chbSignos_letargico_o_inconciente;
	@View
	private Checkbox chbSignos_vomita_todo;
	@View
	private Checkbox chbSignos_convulsiones;
	@View
	private Intbox ibxTos_dias;
	@View
	private Intbox ibxTos_resppm;
	@View
	private Button btnCuadro2_boton1;
	@View
	private Button btnCuadro2_boton2;
	@View
	private Button btnCuadro2_boton3;
	@View
	private Button btnCuadro2_boton4;
	@View
	private Button btnCuadro2_boton5;
	@View
	private Button btnCuadro2_boton6;
	@View
	private Button btnCuadro2_boton7;
	@View
	private Button btnCuadro2_boton8;
	@View
	private Radiogroup rdbTos_respiracion_rapida;
	@View
	private Radiogroup rdbTos_primer_sibilancia;
	@View
	private Radiogroup rdbTos_tiraje_subcostal;
	@View
	private Radiogroup rdbTos_sibilancia_recurrente;
	@View
	private Radiogroup rdbTos_tiraje_supraclavicular;
	@View
	private Radiogroup rdbTos_cuadro_gripal_3dias;
	@View
	private Radiogroup rdbTos_sat_oxigeno;
	@View
	private Radiogroup rdbTos_antecedentes_prematuridad;
	@View
	private Radiogroup rdbTos_estridor;
	@View
	private Radiogroup rdbTos_apnea;
	@View
	private Radiogroup rdbTos_sibilancias;
	@View
	private Radiogroup rdbTos_somnoliento;
	@View
	private Radiogroup rdbTos_incapacidad_hablar_beber;
	@View
	private Radiogroup rdbTos_agitado;
	@View
	private Radiogroup rdbTos_confuso;
	@View
	private Intbox ibxDiarrea_dias;
	@View
	private Radiogroup rdbDiarrea_letargico_comatoso;
	@View
	private Button btnCuadro3_boton1;
	@View
	private Button btnCuadro3_boton2;
	@View
	private Button btnCuadro3_boton3;
	@View
	private Button btnCuadro3_boton4;
	@View
	private Button btnCuadro3_boton5;
	@View
	private Button btnCuadro3_boton6;
	@View
	private Button btnCuadro3_boton7;
	@View
	private Radiogroup rdbDiarrea_sangre_heces;
	@View
	private Radiogroup rdbDiarrea_intranquilo_irritable;
	@View
	private Radiogroup rdbDiarrea_tiene_vomito;
	@View
	private Radiogroup rdbDiarrea_ojos_hundidos;
	@View
	private Intbox ibxDiarrea_cant_vomitos_24h;
	@View
	private Radiogroup rdbDiarrea_bebe_mal_no_puede;
	@View
	private Intbox ibxDiarrea_cant_diarreas_24h;
	@View
	private Radiogroup rdbDiarrea_bebe_abidamente_con_sed;
	@View
	private Intbox ibxDiarrea_cant_diarreas_4h;
	@View
	private Radiogroup rdbDiarrea_pliegue_cutaneo;
	@View
	private Intbox ibxFiebre_dias;
	@View
	private Radiogroup rdbFiebre_rigidez_de_nuca;
	@View
	private Button btnCuadro4_boton1;
	@View
	private Button btnCuadro4_boton2;
	@View
	private Button btnCuadro4_boton3;
	@View
	private Button btnCuadro4_boton4;
	@View
	private Button btnCuadro4_boton5;
	@View
	private Button btnCuadro4_boton6;
	@View
	private Button btnCuadro4_boton7;
	@View
	private Button btnCuadro4_boton8;
	@View
	private Radiogroup rdbFiebre_apariencia_enfermo_grave;
	@View
	private Radiogroup rdbFiebre_mayor5_todos_los_dias;
	@View
	private Radiogroup rdbFiebre_manifestaciones_sangrado;
	@View
	private Radiogroup rdbFiebre_aspecto_toxico;
	@View
	private Radiogroup rdbFiebre_mayor_38;
	@View
	private Radiogroup rdbFiebre_mayor_39;
	@View
	private Radiogroup rdbFiebre_respuesta_social;
	@View
	private Radiogroup rdbFiebre_piel;
	@View
	private Radiogroup rdbFiebre_erupcion_cutanea;
	@View
	private Radiogroup rdbFiebre_dolor_abdominal;
	@View
	private Radiogroup rdbFiebre_vive_visito_15_dias_zona_dengue;
	@View
	private Listbox lbxFiebre_zona_malaria;
	@View
	private Radiogroup rdbFiebre_cefaleas;
	@View
	private Radiogroup rdbFiebre_mialgias;
	@View
	private Radiogroup rdbFiebre_altralgias;
	@View
	private Radiogroup rdbFiebre_dolor_retroociular;
	@View
	private Radiogroup rdbFiebre_postracion;
	@View
	private Radiogroup rdbFiebre_ptorniquete;
	@View
	private Radiogroup rdbFiebre_lipotimia;
	@View
	private Radiogroup rdbFiebre_hepatomegalia;
	@View
	private Radiogroup rdbFiebre_disminucion_diuresis;
	@View
	private Radiogroup rdbFiebre_pulso_rapido;
	@View
	private Radiogroup rdbFiebre_ascitis;
	@View
	private Radiogroup rdbFiebre_laboratorio_ch_leucocitos_mayor_15000;
	@View
	private Radiogroup rdbFiebre_laboratorio_neutrofilos_menor_4000;
	@View
	private Radiogroup rdbFiebre_laboratorio_plaquetas_mayor_10000;
	@View
	private Radiogroup rdbFiebre_laboratorio_plaquetas_menor_100000;
	@View
	private Radiogroup rdbFiebre_parcial_orina_positiva;
	@View
	private Radiogroup rdbOido_dolor;
	@View
	private Radiogroup rdbOido_tumefaccion_dolorosa_detras_oreja;
	@View
	private Button btnCuadro5_boton1;
	@View
	private Button btnCuadro5_boton2;
	@View
	private Button btnCuadro5_boton3;
	@View
	private Button btnCuadro5_boton4;
	@View
	private Button btnCuadro5_boton5;
	@View
	private Radiogroup rdbOido_supuracion;
	@View
	private Label lbOido_supuracion_dias;
	@View
	private Intbox ibxOido_supuracion_dias;
	@View
	private Intbox ibxOido_episodios_previos;
	@View
	private Intbox ibxOido_episodios_previos_meses;
	@View
	private Radiogroup rdbOido_timpano_rojo_abombado;
	@View
	private Radiogroup rdbGarganta_dolor;
	@View
	private Radiogroup rdbGarganta_ganglios_cuello_crecidos_dolorosos;
	@View
	private Button btnCuadro6_boton1;
	@View
	private Button btnCuadro6_boton2;
	@View
	private Button btnCuadro6_boton3;
	@View
	private Radiogroup rdbGarganta_amialgias_eritematosas;
	@View
	private Radiogroup rdbGarganta_exudado_blanquecino_amarillento_amigdalas;
	@View
	private Radiogroup rdbSalud_bucal_dolor_comer_masticar;
	@View
	private Radiogroup rdbSalud_bucal_inflamacion_dolorosa_labio;
	@View
	private Button btnCuadro7_boton1;
	@View
	private Button btnCuadro7_boton2;
	@View
	private Button btnCuadro7_boton3;
	@View
	private Button btnCuadro7_boton4;
	@View
	private Button btnCuadro7_boton5;
	@View
	private Button btnCuadro7_boton6;
	@View
	private Button btnCuadro7_boton7;
	@View
	private Label lbSalud_bucal_no_involucra_surco;
	@View
	private Radiogroup rdbSalud_bucal_no_involucra_surco;
	@View
	private Radiogroup rdbSalud_bucal_dolor_dientes;
	@View
	private Listbox lbxSalud_bucal_enrojecimiento_inflamacion_encia;
	@View
	private Radiogroup rdbSalud_bucal_trauma_cara_boca;
	@View
	private Radiogroup rdbSalud_bucal_padres_hermanos_caries;
	@View
	private Radiogroup rdbSalud_bucal_deformacion_contorno_encia;
	@View
	private Listbox lbxSalud_bucal_vesiculas;
	@View
	private Listbox lbxSalud_bucal_ulceras;
	@View
	private Listbox lbxSalud_bucal_placas;
	@View
	private Radiogroup rdbSalud_bucal_fractura;
	@View
	private Radiogroup rdbSalud_bucal_mobilidad;
	@View
	private Radiogroup rdbSalud_bucal_desplazamiento;
	@View
	private Radiogroup rdbSalud_bucal_extrusion;
	@View
	private Radiogroup rdbSalud_bucal_intrusion;
	@View
	private Radiogroup rdbSalud_bucal_avulsion;
	@View
	private Listbox lbxSalud_bucal_herida;
	@View
	private Listbox lbxSalud_bucal_manchas;
	@View
	private Radiogroup rdbSalud_bucal_caries_cavitacionales;
	@View
	private Radiogroup rdbSalud_bucal_placa_bacteriana;
	@View
	private Radiogroup rdbSalud_bucal_cuando_limpia_boca_manana;
	@View
	private Radiogroup rdbSalud_bucal_cuando_limpia_boca_tarde;
	@View
	private Radiogroup rdbSalud_bucal_cuando_limpia_boca_noche;
	@View
	private Radiogroup rdbSalud_bucal_como_supervisa_limpieza_le_limpia_dientes;
	@View
	private Radiogroup rdbSalud_bucal_como_supervisa_limpieza_nino_solo;
	@View
	private Radiogroup rdbSalud_bucal_utiliza_cepillo;
	@View
	private Radiogroup rdbSalud_bucal_utiliza_crema;
	@View
	private Radiogroup rdbSalud_bucal_utiliza_seda;
	@View
	private Radiogroup rdbSalud_bucal_chupo_biberon;
	@View
	private Datebox dtxSalud_bucal_ultima_consulta_odontologo;
	@View
	private Radiogroup rdbCrecimiento_emanciacion_visible;
	@View
	private Textbox tbxCrecimiento_pe_de;
	@View
	private Button btnCuadro8_boton1;
	@View
	private Button btnCuadro8_boton2;
	@View
	private Button btnCuadro8_boton3;
	@View
	private Button btnCuadro8_boton4;
	@View
	private Button btnCuadro8_boton5;
	@View
	private Button btnCuadro8_boton6;
	@View
	private Radiogroup rdbCrecimiento_edema_ambos;
	@View
	private Textbox tbxCrecimiento_apariencia;
	@View
	private Textbox tbxCrecimiento_imc_edad;
	@View
	private Textbox tbxCrecimiento_imc_edad_de;
	@View
	private Textbox tbxCrecimiento_talla_edad_de;
	@View
	private Textbox tbxCrecimiento_peso_talla_de;
	@View
	private Listbox lbxCrecimiento_tendencia_peso;
	@View
	private Div divAnemia_hierro;
	@View
	private Radiogroup rdbAnemia_hierro_ultimos_6meses;
	@View
	private Datebox dtxAnemia_hierro_ultimos_6meses_cuando;
	@View
	private Textbox tbxAnemia_hierro_ultimos_6meses_cuanto_tiempo;
	@View
	private Button btnCuadro9_boton1;
	@View
	private Button btnCuadro9_boton2;
	@View
	private Button btnCuadro9_boton3;
	@View
	private Listbox lbxAnemia_palidez_palmar;
	@View
	private Listbox lbxAnemia_palidez_conjutival;
	@View
	private Textbox tbxMaltrato_como_se_produjeron_lesiones;
	@View
	private Button btnCuadro10_boton1;
	@View
	private Button btnCuadro10_boton2;
	@View
	private Button btnCuadro10_boton3;
	@View
	private Button btnCuadro10_boton4;
	@View
	private Button btnCuadro10_boton5;
	@View
	private Button btnCuadro10_boton6;
	@View
	private Radiogroup rdbMaltrato_nino_relata_maltrato;
	@View
	private Checkbox chbMaltrato_nino_relata_maltrato_cual_fisico;
	@View
	private Checkbox chbMaltrato_nino_relata_maltrato_cual_sexual;
	@View
	private Checkbox chbMaltrato_nino_relata_maltrato_cual_negligencia;
	@View
	private Textbox tbxMaltrato_nino_relata_maltrato_quien;
	@View
	private Radiogroup rdbMaltrato_incongruencia_al_explicar_un_trauma_significante;
	@View
	private Radiogroup rdbMaltrato_incongruencia_lesion_edad_desarrollo_del_nino;
	@View
	private Radiogroup rdbMaltrato_hay_diferentes_versiones;
	@View
	private Radiogroup rdbMaltrato_es_tardia_la_consulta;
	@View
	private Textbox tbxMaltrato_frecuencia_pegarle_a_su_hijo;
	@View
	private Textbox tbxMaltrato_desobediente_es_su_hijo_obligado_a_pegarle;
	@View
	private Listbox lbxMaltrato_comportamiento_anormal_padres;
	@View
	private Radiogroup rdbMaltrato_agresividad_consulta;
	@View
	private Radiogroup rdbMaltrato_descuidado_nino_salud;
	@View
	private Textbox tbxMaltrato_descuidado_nino_salud_por;
	@View
	private Radiogroup rdbMaltrato_descuidado_nino_higiene;
	@View
	private Radiogroup rdbMaltrato_descuidado_nino_proteccion;
	@View
	private Radiogroup rdbMaltrato_descuidado_nino_alimentacion;
	@View
	private Radiogroup rdbMaltrato_descuidado_nino_de_la_calle;
	@View
	private Textbox tbxMaltrato_factor_riesgo;
	@View
	private Textbox tbxMaltrato_hiperactivo;
	@View
	private Radiogroup rdbMaltrato_actividad_anormal_nino;
	@View
	private Listbox lbxMaltrato_lesiones_craneo;
	@View
	private Listbox lbxMaltrato_quemaduras;
	@View
	private Radiogroup rdbMaltrato_equimosis;
	@View
	private Radiogroup rdbMaltrato_hematomas;
	@View
	private Radiogroup rdbMaltrato_laceraciones;
	@View
	private Radiogroup rdbMaltrato_mordiscos;
	@View
	private Radiogroup rdbMaltrato_cicatrices_lejos_prominencia_oseo_con_patron;
	@View
	private Radiogroup rdbMaltrato_diferente_evolucion_en_ninos_que_no_deambulan;
	@View
	private Radiogroup rdbMaltrato_sugestivas_de_maltrato;
	@View
	private Listbox lbxMaltrato_fracturas;
	@View
	private Radiogroup rdbMaltrato_trauma_viceral;
	@View
	private Radiogroup rdbMaltrato_trauma_grave;
	@View
	private Textbox tbxMaltrato_lesion_fisica_sugestiva;
	@View
	private Radiogroup rdbMaltrato_sangrado_vaginal_o_analtraumatico;
	@View
	private Listbox lbxMaltrato_trauma_genital;
	@View
	private Radiogroup rdbMaltrato_juego_contenido_sexual;
	@View
	private Radiogroup rdbMaltrato_boca_en_genitales;
	@View
	private Radiogroup rdbMaltrato_vih;
	@View
	private Radiogroup rdbMaltrato_gonorrea;
	@View
	private Radiogroup rdbMaltrato_sifilis;
	@View
	private Radiogroup rdbMaltrato_trichomona_vaginalis;
	@View
	private Radiogroup rdbMaltrato_clamidia_trachomatis;
	@View
	private Checkbox chkMaltrato_temeroso;
	@View
	private Checkbox chkMaltrato_retraido;
	@View
	private Checkbox chkMaltrato_rechazo_adulto;
	@View
	private Checkbox chkMaltrato_deprimido;
	@View
	private Checkbox chkMaltrato_evita_contacto_visual;
	@View
	private Checkbox chkMaltrato_trastorno_sueno;
	@View
	private Checkbox chkMaltrato_trastorno_alimentario;
	@View
	private Checkbox chkMaltrato_problemas_psicosomaticos;
	@View
	private Checkbox chkMaltrato_conductas_agresivas;
	@View
	private Checkbox chkMaltrato_desarrollo_estancado;
	@View
	private Checkbox chkMaltrato_violencia_intrafamiliar;
	@View
	private Checkbox chkMaltrato_familia_caotica;
	@View
	private Checkbox chkMaltrato_cuidadores_adictos;
	@View
	private Div divRelataMaltrato;
	@View
	private Div divDescuidoNinoSalud;
	@View
	private Div divDescuidoNinoHigiene;
	@View
	private Div divDescuidoNinoProteccion;
	@View
	private Div divDescuidoNinoAlimentacion;
	@View
	private Div divDescuidoNinoCalle;
	@View
	private Textbox tbxMaltrato_descuidado_nino_higiene_por;
	@View
	private Textbox tbxMaltrato_descuidado_nino_proteccion_por;
	@View
	private Textbox tbxMaltrato_descuidado_nino_alimentacion_por;
	@View
	private Textbox tbxMaltrato_descuidado_nino_de_la_calle_por;
	@View
	private Label lbObligatorioPc;
	
	private RespuestaInt coordenadaPesoEdad;
	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaPesoTalla;
	private RespuestaInt coordenadaIMCEdad;
	private RespuestaInt coordenadaPerimetroCefalicoEdad;
	
	private List coordenadasPE;
	private List coordenadasTE;
	private List coordenadasPT;
	private List coordenadasIE;
	private List coordenadasPCE;
	
	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;
	private final String[] idsExcluyentes = { "tbxAccion", "tbxValue",
			"lbxParameter", 	"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar",
			"tbxTelefono_acompanante","macroImpresion_diagnostica","divModuloRemisiones_externas" };
	
	private Integer edad_meses;
	private Integer edad;

	private Citas cita;
	
	//Componentes en cuadros

	
	@Override
	public void init() {
		try {
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			precargarSugernecias();
//			agudeza_visualAction = new Agudeza_visualAction();
//			agudeza_visualAction.inicializAgudeza(this,admision);

			// deshabilitarCamposCuadros();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// metodos para habilitar filas no visibles
	public void mostrar_filas_ultimadosis() {
		if (cbx_influenza.isChecked() == true) {
			row_ultima_dosis.setVisible(true);
		} else {
			row_ultima_dosis.setVisible(false);

		}
	}
	
	public void mostrar_filas_ocultas(){
		mostrar_fila_fiebre_amarilla();
		mostrar_fila_aiepi_cuadro_2();
		mostrar_fila_aiepi_cuadro_3();
		mostrar_fila_aiepi_cuadro_4();
		mostrar_fila_aiepi_cuadro_5();
		mostrar_fila_aiepi_cuadro_6();
	}

	public void mostrar_fila_fiebre_amarilla() {
		if (cbx_fiebre_amarilla.isChecked() == true) {
			row_fiebre_amarilla.setVisible(true);
		} else {
			row_fiebre_amarilla.setVisible(false);
			ibx_edad.setText("");
		}
	}

	public void mostrar_fila_aiepi_cuadro_2() {

		if (rdbTiene_tos_o_dificultad_para_respirar.getSelectedItem()
				.getValue().equals("S")) {
			row_cuadro_aiepi_2.setVisible(true);
		} else {
			row_cuadro_aiepi_2.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_3() {

		if (rdbTiene_diarrea.getSelectedItem().getValue().equals("S")) {
			row_cuadro_aiepi_3.setVisible(true);
		} else {
			row_cuadro_aiepi_3.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_4() {
		if(!dbxSignos_vitales_taxilar.getText().isEmpty() && dbxSignos_vitales_taxilar.getValue()>37){
			rdbTiene_fiebre.setSelectedIndex(0);
		}else{
			//rdbTiene_fiebre.setSelectedIndex(1);
		}
		
		if(rdbTiene_fiebre.getSelectedIndex()==0){
			row_cuadro_aiepi_4.setVisible(true);
		}else{
			row_cuadro_aiepi_4.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_5() {

		if (rdbTiene_problemas_de_oido.getSelectedItem().getValue().equals("S")) {
			row_cuadro_aiepi_5.setVisible(true);
		} else {
			row_cuadro_aiepi_5.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_6() {

		if (rdbTiene_un_problema_de_garganta.getSelectedItem().getValue()
				.equals("S")) {
			row_cuadro_aiepi_6.setVisible(true);
		} else {
			row_cuadro_aiepi_6.setVisible(false);
		}
	}

	public void listarCombos() {
		listarParameter();
		
		Utilidades.listarElementoListbox(lbxParentesco_acompanante, true,getServiceLocator());
		Utilidades.listarElementoListbox(lbxHemoclasificacion, true,getServiceLocator());
		Utilidades.listarElementoListbox(lbxSalud_bucal_enrojecimiento_inflamacion_encia, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxSalud_bucal_vesiculas, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxSalud_bucal_ulceras, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxSalud_bucal_placas, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxSalud_bucal_herida, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxSalud_bucal_manchas, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxCrecimiento_tendencia_peso, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxAnemia_palidez_palmar, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxAnemia_palidez_conjutival, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxMaltrato_comportamiento_anormal_padres, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxMaltrato_lesiones_craneo, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxMaltrato_fracturas, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxMaltrato_trauma_genital, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxMaltrato_quemaduras, false,getServiceLocator());
		Utilidades.listarElementoListbox(lbxFiebre_zona_malaria, false,getServiceLocator());	
				
		parametrizarBandboxOcupacion(tbxOcupacion_padre, tbxInfoOcupacion_padre, btnLimpiarOcupacion_padre);
		parametrizarBandboxOcupacion(tbxOcupacion_madre, tbxInfoOcupacion_madre, btnLimpiarOcupacion_madre);
		
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		if (cita != null) {
			tbxId_acompanante.setValue(cita.getCedula_acomp());
			tbxNombres_acompanante.setValue(cita.getAcompaniante());
			tbxApellidos_acompanante.setValue(cita.getApellidos_acomp());
			tbxTelefono_acompanante.setValue(cita.getTel_acompaniante());
			Utilidades.seleccionarListitem(lbxParentesco_acompanante, cita.getRelacion());
			onSeleccionarRelacionAcompaniante();
			tbxOtro_acompanante.setValue(cita.getOtro_acompaniante());	
		}

		if(paciente.getTipo_identificacion().equalsIgnoreCase("AS")||paciente.getTipo_identificacion().equalsIgnoreCase("MS")){
			rdbNino_registrado.setSelectedIndex(1);
		}else{
			rdbNino_registrado.setSelectedIndex(0);
		}
		
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

							List<Hisc_aiepi_mn_2_meses_5_anios> listado_historias = hisc_aiepi_mn_2_meses_5_aniosService
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
								Hisc_aiepi_mn_2_meses_5_anios hisc = new Hisc_aiepi_mn_2_meses_5_anios();
								hisc.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc.setCodigo_historia(codigo_historia_anterior);
								hisc = hisc_aiepi_mn_2_meses_5_aniosService
										.consultar(hisc);
								if (hisc != null) {
									cargarInformacion_historia_anterior(hisc);
								}
							}
						}

					}
				});
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

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
		if (admision != null) {
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloRemisiones();
		}
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
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
		 
		ArrayList<XulElement> componentes = new ArrayList<XulElement>();
		componentes.add(tbxNombres_acompanante);
		componentes.add(tbxApellidos_acompanante);
		componentes.add(tbxMotivo_consulta);
		componentes.add(tbxEnfermedad_actual);
		componentes.add(dbxSignos_vitales_peso);
		componentes.add(dbxSignos_vitales_talla);
		if(edad<4){
			componentes.add(dbxSignos_vitales_pc);
		}
		componentes.add(dbxSignos_vitales_fc);
		componentes.add(dbxSignos_vitales_fr);
		componentes.add(dbxSignos_vitales_taxilar);
		componentes.add(lbxParentesco_acompanante);
		 
		XulElement[] comp = componentes.toArray(new XulElement[0]);
		FormularioUtil.validarCamposObligatorios(comp);

		String mensaje = "";
		boolean valida = true;
		
		if(valida){
			valida = receta_ripsAction.validarFormExterno();
			if(!valida){
				mensaje="Error receta rips!";
			}
		}

		if (valida){
			valida = orden_servicioAction.validarFormExterno();
			if(!valida){
				mensaje="Error orden de servicio!";
			}
		}

		if (valida){
			valida = remisiones_externasAction.validarRemision();
			if(!valida){
				mensaje="Error remision externa!";
			}		
		}
			

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

			parameters.put("limite_paginado", "limit 25 offset 0");
			
			List<Hisc_aiepi_mn_2_meses_5_anios> lista_datos = hisc_aiepi_mn_2_meses_5_aniosService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios : lista_datos) {
				rowsResultado
						.appendChild(crearFilas(
								hisc_aiepi_mn_2_meses_5_anios,
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

		final Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios = (Hisc_aiepi_mn_2_meses_5_anios) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(
				hisc_aiepi_mn_2_meses_5_anios
						.getCodigo_historia() + ""));
		fila.appendChild(new Label(
				hisc_aiepi_mn_2_meses_5_anios
						.getIdentificacion() + ""));
		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				hisc_aiepi_mn_2_meses_5_anios
						.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(
				hisc_aiepi_mn_2_meses_5_anios
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
						mostrarDatos(hisc_aiepi_mn_2_meses_5_anios);
					}
				});
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
				Hisc_aiepi_mn_2_meses_5_anios aiepi_2_5 = getBean();

				calcularCoordenadas(false);
				// Coordenada (P/E)
				Coordenadas_graficas cg0 = new Coordenadas_graficas();
				cg0.setCodigo_empresa(codigo_empresa);
				cg0.setCodigo_historia(aiepi_2_5.getCodigo_historia());
				cg0.setCodigo_sucursal(codigo_sucursal);
				cg0.setFecha_historia(aiepi_2_5.getFecha_inicial());
				cg0.setTipo_coordenada(ITipos_coordenada.P_E);
				cg0.setIdentificacion(paciente.getNro_identificacion());
				cg0.setValor("" + coordenadaPesoEdad.getValor());
				cg0.setX("" + coordenadaPesoEdad.getX());
				cg0.setY("" + coordenadaPesoEdad.getY());
				cg0.setIhistoria_clinica(ihistoria_clinica);

				// Coordenada (T/E)
				Coordenadas_graficas cg1 = new Coordenadas_graficas();
				cg1.setCodigo_empresa(codigo_empresa);
				cg1.setCodigo_historia(aiepi_2_5.getCodigo_historia());
				cg1.setCodigo_sucursal(codigo_sucursal);
				cg1.setFecha_historia(aiepi_2_5.getFecha_inicial());
				cg1.setTipo_coordenada(ITipos_coordenada.T_E);
				cg1.setIdentificacion(paciente.getNro_identificacion());
				cg1.setValor("" + coordenadaTallaEdad.getValor());
				cg1.setX("" + coordenadaTallaEdad.getX());
				cg1.setY("" + coordenadaTallaEdad.getY());
				cg1.setIhistoria_clinica(ihistoria_clinica);

				// Coordenada (P/T)
				Coordenadas_graficas cg2 = new Coordenadas_graficas();
				cg2.setCodigo_empresa(codigo_empresa);
				cg2.setCodigo_historia(aiepi_2_5.getCodigo_historia());
				cg2.setCodigo_sucursal(codigo_sucursal);
				cg2.setFecha_historia(aiepi_2_5.getFecha_inicial());
				cg2.setTipo_coordenada(ITipos_coordenada.P_T);
				cg2.setIdentificacion(paciente.getNro_identificacion());
				cg2.setValor("" + coordenadaPesoTalla.getValor());
				cg2.setX("" + coordenadaPesoTalla.getX());
				cg2.setY("" + coordenadaPesoTalla.getY());
				cg2.setIhistoria_clinica(ihistoria_clinica);

				// Coordenada (IMC/T)
				Coordenadas_graficas cg3 = new Coordenadas_graficas();
				cg3.setCodigo_empresa(codigo_empresa);
				cg3.setCodigo_historia(aiepi_2_5.getCodigo_historia());
				cg3.setCodigo_sucursal(codigo_sucursal);
				cg3.setFecha_historia(aiepi_2_5.getFecha_inicial());
				cg3.setTipo_coordenada(ITipos_coordenada.IMC_E);
				cg3.setIdentificacion(paciente.getNro_identificacion());
				cg3.setValor("" + coordenadaIMCEdad.getValor());
				cg3.setX("" + coordenadaIMCEdad.getX());
				cg3.setY("" + coordenadaIMCEdad.getY());
				cg3.setIhistoria_clinica(ihistoria_clinica);

				// Coordenada (PC/T)
				Coordenadas_graficas cg4 = new Coordenadas_graficas();
				if (edad < 4) {
					cg4.setCodigo_empresa(codigo_empresa);
					cg4.setCodigo_historia(aiepi_2_5.getCodigo_historia());
					cg4.setCodigo_sucursal(codigo_sucursal);
					cg4.setFecha_historia(aiepi_2_5.getFecha_inicial());
					cg4.setTipo_coordenada(ITipos_coordenada.PC_E);
					cg4.setIdentificacion(paciente.getNro_identificacion());
					cg4.setValor("" + coordenadaPerimetroCefalicoEdad.getValor());
					cg4.setX("" + coordenadaPerimetroCefalicoEdad.getX());
					cg4.setY("" + coordenadaPerimetroCefalicoEdad.getY());
					cg4.setIhistoria_clinica(ihistoria_clinica);
				}

				ArrayList<Coordenadas_graficas> coordenadas = new ArrayList<Coordenadas_graficas>();
				coordenadas.add(cg0);
				coordenadas.add(cg1);
				coordenadas.add(cg2);
				coordenadas.add(cg3);
				if (edad < 4) {
					coordenadas.add(cg4);
				}
				
				
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("accion", tbxAccion.getText());
				datos.put("historia_clinica",	aiepi_2_5);
				datos.put("coordenadas", coordenadas);
				datos.put("admision", admision);
				datos.put("cita_seleccionada",cita);
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());
				
				// hay que actualualizar los diagnosticos en la receta antes de
				// obtener el objeto receta
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();
				datos.put("impresion_diagnostica", impresion_diagnostica);
				receta_ripsAction
				.actualizarDiagnosticos(impresion_diagnostica);
				
				Map<String, Object> mapReceta = receta_ripsAction.obtenerDatos();
				Map<String, Object> mapProcedimientos = orden_servicioAction.obtenerDatos();
				//log.info("Mapa de recetas"+mapReceta);
				datos.put("receta_medica", mapReceta);
				datos.put("orden_servicio", mapProcedimientos);
				
				Remision_interna remision_interna = macroRemision_interna.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);
				
				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);
				
				datos.put("impresion_diagnostica", impresion_diagnostica);

				hisc_aiepi_mn_2_meses_5_aniosService.guardarDatos(datos);

				mostrarDatosAutorizacion(datos);
				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(false);
				}
				
				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(aiepi_2_5.getCodigo_historia());

				Receta_rips receta_rips = (Receta_rips) mapReceta.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, false);

				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos.get("orden_servicio");
				if (orden_servicio != null)
					orden_servicioAction.mostrarDatos(orden_servicio);

				orden_servicioAction.validarParaImpresion();
				receta_ripsAction.validarParaImpresion();
				
				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);

				actualizarRemision(admision, getInformacionClinica(), this);
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				throw new Exception(e.getMessage(), e);
			} else {
				//log.info("el compoente del error es: ===> "
						//+ ((WrongValueException) e).getComponent().getId());
			}
		}
	}

	private Hisc_aiepi_mn_2_meses_5_anios getBean() {
		Hisc_aiepi_mn_2_meses_5_anios aiepi_2_5 = new Hisc_aiepi_mn_2_meses_5_anios();
		aiepi_2_5
				.setCodigo_historia(infoPacientes.getCodigo_historia());
		aiepi_2_5
				.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
		aiepi_2_5
				.setFecha_inicial(new Timestamp(infoPacientes
						.getFecha_inicial().getTime()));
		aiepi_2_5
				.setNro_ingreso(admision.getNro_ingreso());
		aiepi_2_5
				.setCodigo_historia_anterior(codigo_historia_anterior);
		aiepi_2_5
				.setCodigo_empresa(empresa.getCodigo_empresa());
		aiepi_2_5
				.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		aiepi_2_5
				.setNombre_acompananate(tbxNombres_acompanante
						.getValue());
		aiepi_2_5
				.setApellido_acompanante(tbxApellidos_acompanante
						.getValue());
		aiepi_2_5
				.setVolver_consulta_control(new Timestamp(
						dtxVolver_consulta_control.getValue()
								.getTime()));
		aiepi_2_5
				.setParentesco_acompanante(lbxParentesco_acompanante
						.getSelectedItem().getValue().toString());
		
		aiepi_2_5.setId_acompanante(tbxId_acompanante.getValue());
		
		aiepi_2_5
				.setTelefono_acompanante(tbxTelefono_acompanante
						.getValue());
		aiepi_2_5
				.setNombre_padre(tbxNombres_padre.getValue());
		aiepi_2_5
				.setApellidos_padre(tbxApellidos_padre.getValue());
		aiepi_2_5
			.setId_padre(tbxId_padre.getValue());
		aiepi_2_5
			.setTelefono_padre(tbxTelefono_padre.getValue());		
		aiepi_2_5
				.setOcupacion_padre(tbxOcupacion_padre.getValue());
		aiepi_2_5
				.setEdad_padre((ibxEdad_padre.getValue() != null ? ibxEdad_padre
						.getValue() : 0));
		
		aiepi_2_5
				.setNombre_madre(tbxNombres_madre.getValue());
		aiepi_2_5
				.setApellidos_madre(tbxApellidos_madre.getValue());
		aiepi_2_5
			.setId_madre(tbxId_madre.getValue());
		aiepi_2_5
			.setTelefono_madre(tbxTelefono_madre.getValue());	

		aiepi_2_5
				.setEdad_madre((ibxEdad_madre.getValue() != null ? ibxEdad_madre
						.getValue() : 0));
		aiepi_2_5
				.setOcupacion_madre(tbxOcupacion_madre.getValue());
		aiepi_2_5
				.setMotivo_consulta(tbxMotivo_consulta.getValue());
		aiepi_2_5
				.setEnfermedad_actual(tbxEnfermedad_actual.getValue());
		aiepi_2_5
				.setComo_fue_el_embarazo(tbxComo_fue_el_embarazo
						.getValue());
		aiepi_2_5
				.setCuanto_duro_embarazo((ibxCuanto_duro_embarazo
						.getValue() != null ? ibxCuanto_duro_embarazo
						.getValue() : 0));
		aiepi_2_5
				.setComo_fue_el_parto(tbxComo_fue_el_parto.getValue());
		aiepi_2_5
				.setTalla_al_nacer(String.valueOf((dbxTalla_al_nacer
						.getValue() != null ? dbxTalla_al_nacer
						.getValue() : 0.00)));
		aiepi_2_5
				.setPresento_algun_problema_neonatal(tbxPresento_algun_problema_neonatal
						.getValue());
		aiepi_2_5
				.setEnfermedades_previas(tbxEnfermedades_previas
						.getValue());
		aiepi_2_5
				.setHospitalizaciones(tbxHospitalizaciones.getValue());
		aiepi_2_5
				.setSignos_vitales_fc(""
						+ (dbxSignos_vitales_fc.getValue() != null ? dbxSignos_vitales_fc
								.getValue() : 0.00));
		aiepi_2_5
				.setSignos_vitales_fr(""
						+ (dbxSignos_vitales_fr.getValue() != null ? dbxSignos_vitales_fr
								.getValue() : 0.00));
		aiepi_2_5
				.setSignos_vitales_talla(""
						+ (dbxSignos_vitales_talla.getValue() != null ? dbxSignos_vitales_talla
								.getValue() : 0.00));
		aiepi_2_5
				.setPeso_al_nacer(String.valueOf((dbxPeso_al_nacer
						.getValue() != null ? dbxPeso_al_nacer
						.getValue() : 0.00)));
		aiepi_2_5
				.setSignos_vitales_peso(""
						+ (dbxSignos_vitales_peso.getValue() != null ? dbxSignos_vitales_peso
								.getValue() : 0.00));
		aiepi_2_5
				.setSignos_vitales_pc(""
						+ (dbxSignos_vitales_pc.getValue() != null ? dbxSignos_vitales_pc
								.getValue() : 0.00));
		aiepi_2_5
				.setSignos_vitales_imc(""
						+ (dbxSignos_vitales_imc.getValue() != null ? dbxSignos_vitales_imc
								.getValue() : 0.00));
		aiepi_2_5
				.setSignos_vitales_taxilar(""
						+ (dbxSignos_vitales_taxilar.getValue() != null ? dbxSignos_vitales_taxilar
								.getValue() : 0.00));
		aiepi_2_5
				.setSignos_vitales_oximetria(""
						+ (dbxSignos_vitales_oximetria.getValue() != null ? dbxSignos_vitales_oximetria
								.getValue() : 0.00));
		aiepi_2_5
				.setTiene_tos_o_dificultad_para_respirar(rdbTiene_tos_o_dificultad_para_respirar
						.getSelectedItem().getValue().toString());
		aiepi_2_5
				.setTiene_diarrea(rdbTiene_diarrea.getSelectedItem()
						.getValue().toString());
		aiepi_2_5
				.setTiene_fiebre(rdbTiene_fiebre.getSelectedItem()
						.getValue().toString());
		aiepi_2_5
				.setTiene_problemas_de_oido(rdbTiene_problemas_de_oido
						.getSelectedItem().getValue().toString());
		aiepi_2_5
				.setTiene_un_problema_de_garganta(rdbTiene_un_problema_de_garganta
						.getSelectedItem().getValue().toString());
		aiepi_2_5
				.setSignos_vitales_sintomaticos_respiratorio(rdbSignos_vitales_sintomaticos_respiratorio
						.getSelectedItem().getValue().toString());
		aiepi_2_5
				.setSignos_vitales_sintomaticos_piel(rdbSignos_vitales_sintomaticos_piel
						.getSelectedItem().getValue().toString());
		aiepi_2_5
				.setTipo_historia(tipo_historia);
		aiepi_2_5
				.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
		aiepi_2_5
				.setCreacion_user(usuarios.getCodigo().toString());
		aiepi_2_5
				.setDelete_date(null);
		aiepi_2_5
				.setDelete_user(null);
		aiepi_2_5
				.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
		aiepi_2_5
				.setUltimo_user(usuarios.getCodigo().toString());

		// TODO: guardar datos antecedentes vacunaion
		aiepi_2_5
				.setBcg_1(cbx_bcg__1.isChecked() ? "S" : "N");
		aiepi_2_5
				.setHepatitis_b_rn(cbx_hepatitis_b_rn.isChecked() ? "S"
						: "N");
		aiepi_2_5
				.setHepatitis_b1(cbx_hepatitis_b_1.isChecked() ? "S"
						: "N");
		aiepi_2_5
				.setHepatitis_b2(cbx_hepatitis_b_2.isChecked() ? "S"
						: "N");
		aiepi_2_5
				.setHepatitis_b3(cbx_hepatitis_b_3.isChecked() ? "S"
						: "N");
		aiepi_2_5
				.setVop_1(cbx_vop_1.isChecked() ? "S" : "N");
		aiepi_2_5
				.setVop_2(cbx_vop_2.isChecked() ? "S" : "N");
		aiepi_2_5
				.setVop_3(cbx_vop_3.isChecked() ? "S" : "N");
		aiepi_2_5
				.setVop_r1(cbx_vop_r1.isChecked() ? "S" : "N");
		aiepi_2_5
				.setVop_r2(cbx_vop_r2.isChecked() ? "S" : "N");
		aiepi_2_5
				.setRotavirus1(cbx_rotavirus_1.isChecked() ? "S" : "N");
		aiepi_2_5
				.setRotavirus2(cbx_rotavirus_2.isChecked() ? "S" : "N");
		aiepi_2_5
				.setInfluenza(cbx_influenza.isChecked() ? "S" : "N");
		aiepi_2_5
				.setOtras_vaunas(tbx_otras_vacunas.getValue());
		aiepi_2_5
				.setDpt1(cbx_dtp_1.isChecked() ? "S" : "N");
		aiepi_2_5
				.setDpt2(cbx_dtp_2.isChecked() ? "S" : "N");
		aiepi_2_5
				.setDpt3(cbx_dtp_3.isChecked() ? "S" : "N");
		aiepi_2_5
				.setDpt_r1(cbx_dtp_r1.isChecked() ? "S" : "N");
		aiepi_2_5
				.setDpt_r2(cbx_dtp_r2.isChecked() ? "S" : "N");
		aiepi_2_5
				.setHaemophilus_influenza_tipo_b1(cbx_haemophilus_influenza_tipo_b1
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setHaemophilus_influenza_tipo_b2(cbx_haemophilus_influenza_tipo_b2
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setHaemophilus_influenza_tipo_b3(cbx_haemophilus_influenza_tipo_b3
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setHaemophilus_influenza_tipo_r1(cbx_haemophilus_influenza_tipo_r1
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setHaemophilus_influenza_tipo_r2(cbx_haemophilus_influenza_tipo_r2
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setStrectococo_neumonia1(cbx_strectococo_neumonia_1
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setStrectococo_neumonia2(cbx_strectococo_neumonia_2
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setStrectococo_neumonia3(cbx_strectococo_neumonia_3
						.isChecked() ? "S" : "N");
		aiepi_2_5
				.setSrp1(cbx_srp_1.isChecked() ? "S" : "N");
		aiepi_2_5
				.setSrp2(cbx_srp_2.isChecked() ? "S" : "N");
		aiepi_2_5
				.setFiebre_amarilla(cbx_fiebre_amarilla.isChecked() ? "S"
						: "N");
		aiepi_2_5
				.setEdad((ibx_edad.getValue() != null ? ibx_edad
						.getValue() : 0));

		if (!cbx_influenza.isChecked()) {
			aiepi_2_5
					.setUltima_dosis(null);
		} else {
			aiepi_2_5
					.setUltima_dosis(new Timestamp(dbx_ultima_dosis
							.getValue().getTime()));
		}
		
		aiepi_2_5.setObservaciones_cuadro1(tbxObservaciones_cuadro1.getValue());
		aiepi_2_5.setObservaciones_cuadro2(tbxObservaciones_cuadro2.getValue());
		aiepi_2_5.setObservaciones_cuadro3(tbxObservaciones_cuadro3.getValue());
		aiepi_2_5.setObservaciones_cuadro4(tbxObservaciones_cuadro4.getValue());
		aiepi_2_5.setObservaciones_cuadro5(tbxObservaciones_cuadro5.getValue());
		aiepi_2_5.setObservaciones_cuadro6(tbxObservaciones_cuadro6.getValue());
		aiepi_2_5.setObservaciones_cuadro7(tbxObservaciones_cuadro7.getValue());
		aiepi_2_5.setObservaciones_cuadro8(tbxObservaciones_cuadro8.getValue());
		aiepi_2_5.setObservaciones_cuadro9(tbxObservaciones_cuadro9.getValue());
		aiepi_2_5.setObservaciones_cuadro10(tbxObservaciones_cuadro10.getValue());
		
		//Nuevos campos
		aiepi_2_5.setNino_registrado(rdbNino_registrado.getSelectedItem().getValue().toString());
		aiepi_2_5.setHemoclasificacion(lbxHemoclasificacion.getSelectedItem().getValue().toString());
		aiepi_2_5.setSignos_no_puede_beber(chbSignos_no_puede_beber.isChecked() ? "S" : "N");
		aiepi_2_5.setSignos_letargico_o_inconciente(chbSignos_letargico_o_inconciente.isChecked() ? "S" : "N");
		aiepi_2_5.setSignos_vomita_todo(chbSignos_vomita_todo.isChecked() ? "S" : "N");
		aiepi_2_5.setSignos_convulsiones(chbSignos_convulsiones.isChecked() ? "S" : "N");
		aiepi_2_5.setTos_dias(ibxTos_dias.getValue()+"");
		aiepi_2_5.setTos_resppm(ibxTos_resppm.getValue()+"");
		aiepi_2_5.setTos_respiracion_rapida(rdbTos_respiracion_rapida.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_primer_sibilancia(rdbTos_primer_sibilancia.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_tiraje_subcostal(rdbTos_tiraje_subcostal.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_sibilancia_recurrente(rdbTos_sibilancia_recurrente.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_tiraje_supraclavicular(rdbTos_tiraje_supraclavicular.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_cuadro_gripal_3dias(rdbTos_cuadro_gripal_3dias.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_sat_oxigeno(rdbTos_sat_oxigeno.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_antecedentes_prematuridad(rdbTos_antecedentes_prematuridad.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_estridor(rdbTos_estridor.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_apnea(rdbTos_apnea.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_sibilancias(rdbTos_sibilancias.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_somnoliento(rdbTos_somnoliento.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_incapacidad_hablar_beber(rdbTos_incapacidad_hablar_beber.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_agitado(rdbTos_agitado.getSelectedItem().getValue().toString());
		aiepi_2_5.setTos_confuso(rdbTos_confuso.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_dias(ibxDiarrea_dias.getValue()+"");
		aiepi_2_5.setDiarrea_letargico_comatoso(rdbDiarrea_letargico_comatoso.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_sangre_heces(rdbDiarrea_sangre_heces.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_intranquilo_irritable(rdbDiarrea_intranquilo_irritable.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_tiene_vomito(rdbDiarrea_tiene_vomito.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_ojos_hundidos(rdbDiarrea_ojos_hundidos.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_cant_vomitos_24h(ibxDiarrea_cant_vomitos_24h.getValue()+"");
		aiepi_2_5.setDiarrea_bebe_mal_no_puede(rdbDiarrea_bebe_mal_no_puede.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_cant_diarreas_24h(ibxDiarrea_cant_diarreas_24h.getValue()+"");
		aiepi_2_5.setDiarrea_bebe_abidamente_con_sed(rdbDiarrea_bebe_abidamente_con_sed.getSelectedItem().getValue().toString());
		aiepi_2_5.setDiarrea_cant_diarreas_4h(ibxDiarrea_cant_diarreas_4h.getValue()+"");
		aiepi_2_5.setDiarrea_pliegue_cutaneo(rdbDiarrea_pliegue_cutaneo.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_dias(ibxFiebre_dias.getValue()+"");
		aiepi_2_5.setFiebre_rigidez_de_nuca(rdbFiebre_rigidez_de_nuca.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_apariencia_enfermo_grave(rdbFiebre_apariencia_enfermo_grave.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_mayor5_todos_los_dias(rdbFiebre_mayor5_todos_los_dias.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_manifestaciones_sangrado(rdbFiebre_manifestaciones_sangrado.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_aspecto_toxico(rdbFiebre_aspecto_toxico.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_mayor_38(rdbFiebre_mayor_38.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_mayor_39(rdbFiebre_mayor_39.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_respuesta_social(rdbFiebre_respuesta_social.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_piel(rdbFiebre_piel.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_erupcion_cutanea(rdbFiebre_erupcion_cutanea.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_dolor_abdominal(rdbFiebre_dolor_abdominal.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_vive_visito_15_dias_zona_dengue(rdbFiebre_vive_visito_15_dias_zona_dengue.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_zona_malaria(lbxFiebre_zona_malaria.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_cefaleas(rdbFiebre_cefaleas.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_mialgias(rdbFiebre_mialgias.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_altralgias(rdbFiebre_altralgias.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_dolor_retroociular(rdbFiebre_dolor_retroociular.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_postracion(rdbFiebre_postracion.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_ptorniquete(rdbFiebre_ptorniquete.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_lipotimia(rdbFiebre_lipotimia.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_hepatomegalia(rdbFiebre_hepatomegalia.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_disminucion_diuresis(rdbFiebre_disminucion_diuresis.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_pulso_rapido(rdbFiebre_pulso_rapido.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_ascitis(rdbFiebre_ascitis.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_laboratorio_ch_leucocitos_mayor_15000(rdbFiebre_laboratorio_ch_leucocitos_mayor_15000.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_laboratorio_neutrofilos_menor_4000(rdbFiebre_laboratorio_neutrofilos_menor_4000.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_laboratorio_plaquetas_mayor_10000(rdbFiebre_laboratorio_plaquetas_mayor_10000.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_laboratorio_plaquetas_menor_100000(rdbFiebre_laboratorio_plaquetas_menor_100000.getSelectedItem().getValue().toString());
		aiepi_2_5.setFiebre_parcial_orina_positiva(rdbFiebre_parcial_orina_positiva.getSelectedItem().getValue().toString());
		aiepi_2_5.setOido_dolor(rdbOido_dolor.getSelectedItem().getValue().toString());
		aiepi_2_5.setOido_tumefaccion_dolorosa_detras_oreja(rdbOido_tumefaccion_dolorosa_detras_oreja.getSelectedItem().getValue().toString());
		aiepi_2_5.setOido_supuracion(rdbOido_supuracion.getSelectedItem().getValue().toString());
		aiepi_2_5.setOido_supuracion_dias(ibxOido_supuracion_dias.getValue()+"");
		aiepi_2_5.setOido_episodios_previos(ibxOido_episodios_previos.getValue()+"");
		aiepi_2_5.setOido_episodios_previos_meses(ibxOido_episodios_previos_meses.getValue()+"");
		aiepi_2_5.setOido_timpano_rojo_abombado(rdbOido_timpano_rojo_abombado.getSelectedItem().getValue().toString());
		aiepi_2_5.setGarganta_dolor(rdbGarganta_dolor.getSelectedItem().getValue().toString());
		aiepi_2_5.setGarganta_ganglios_cuello_crecidos_dolorosos(rdbGarganta_ganglios_cuello_crecidos_dolorosos.getSelectedItem().getValue().toString());
		aiepi_2_5.setGarganta_amialgias_eritematosas(rdbGarganta_amialgias_eritematosas.getSelectedItem().getValue().toString());
		aiepi_2_5.setGarganta_exudado_blanquecino_amarillento_amigdalas(rdbGarganta_exudado_blanquecino_amarillento_amigdalas.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_dolor_comer_masticar(rdbSalud_bucal_dolor_comer_masticar.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_inflamacion_dolorosa_labio(rdbSalud_bucal_inflamacion_dolorosa_labio.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_no_involucra_surco(rdbSalud_bucal_no_involucra_surco.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_dolor_dientes(rdbSalud_bucal_dolor_dientes.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_enrojecimiento_inflamacion_encia(lbxSalud_bucal_enrojecimiento_inflamacion_encia.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_trauma_cara_boca(rdbSalud_bucal_trauma_cara_boca.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_padres_hermanos_caries(rdbSalud_bucal_padres_hermanos_caries.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_deformacion_contorno_encia(rdbSalud_bucal_deformacion_contorno_encia.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_vesiculas(lbxSalud_bucal_vesiculas.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_ulceras(lbxSalud_bucal_ulceras.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_placas(lbxSalud_bucal_placas.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_fractura(rdbSalud_bucal_fractura.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_mobilidad(rdbSalud_bucal_mobilidad.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_desplazamiento(rdbSalud_bucal_desplazamiento.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_extrusion(rdbSalud_bucal_extrusion.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_intrusion(rdbSalud_bucal_intrusion.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_avulsion(rdbSalud_bucal_avulsion.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_herida(lbxSalud_bucal_herida.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_manchas(lbxSalud_bucal_manchas.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_caries_cavitacionales(rdbSalud_bucal_caries_cavitacionales.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_placa_bacteriana(rdbSalud_bucal_placa_bacteriana.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_cuando_limpia_boca_manana(rdbSalud_bucal_cuando_limpia_boca_manana.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_cuando_limpia_boca_tarde(rdbSalud_bucal_cuando_limpia_boca_tarde.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_cuando_limpia_boca_noche(rdbSalud_bucal_cuando_limpia_boca_noche.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_como_supervisa_limpieza_le_limpia_dientes(rdbSalud_bucal_como_supervisa_limpieza_le_limpia_dientes.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_como_supervisa_limpieza_nino_solo(rdbSalud_bucal_como_supervisa_limpieza_nino_solo.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_utiliza_cepillo(rdbSalud_bucal_utiliza_cepillo.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_utiliza_crema(rdbSalud_bucal_utiliza_crema.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_utiliza_seda(rdbSalud_bucal_utiliza_seda.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_chupo_biberon(rdbSalud_bucal_chupo_biberon.getSelectedItem().getValue().toString());
		aiepi_2_5.setSalud_bucal_ultima_consulta_odontologo(dtxSalud_bucal_ultima_consulta_odontologo.getValue().getTime()+"");
		aiepi_2_5.setCrecimiento_emanciacion_visible(rdbCrecimiento_emanciacion_visible.getSelectedItem().getValue().toString());
		aiepi_2_5.setCrecimiento_pe_de(tbxCrecimiento_pe_de.getValue());
		aiepi_2_5.setCrecimiento_edema_ambos(rdbCrecimiento_edema_ambos.getSelectedItem().getValue().toString());
		aiepi_2_5.setCrecimiento_apariencia(tbxCrecimiento_apariencia.getValue());
		aiepi_2_5.setCrecimiento_imc_edad(tbxCrecimiento_imc_edad.getValue());
		aiepi_2_5.setCrecimiento_imc_edad_de(tbxCrecimiento_imc_edad_de.getValue());
		aiepi_2_5.setCrecimiento_talla_edad_de(tbxCrecimiento_talla_edad_de.getValue());
		aiepi_2_5.setCrecimiento_peso_talla_de(tbxCrecimiento_peso_talla_de.getValue());
		aiepi_2_5.setCrecimiento_tendencia_peso(lbxCrecimiento_tendencia_peso.getSelectedItem().getValue().toString());
		aiepi_2_5.setAnemia_hierro_ultimos_6meses(rdbAnemia_hierro_ultimos_6meses.getSelectedItem().getValue().toString());
		aiepi_2_5.setAnemia_hierro_ultimos_6meses_cuando(dtxAnemia_hierro_ultimos_6meses_cuando.getValue().getTime()+"");
		aiepi_2_5.setAnemia_hierro_ultimos_6meses_cuanto_tiempo(tbxAnemia_hierro_ultimos_6meses_cuanto_tiempo.getValue());
		aiepi_2_5.setAnemia_palidez_palmar(lbxAnemia_palidez_palmar.getSelectedItem().getValue().toString());
		aiepi_2_5.setAnemia_palidez_conjutival(lbxAnemia_palidez_conjutival.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_como_se_produjeron_lesiones(tbxMaltrato_como_se_produjeron_lesiones.getValue());
		aiepi_2_5.setMaltrato_nino_relata_maltrato(rdbMaltrato_nino_relata_maltrato.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_nino_relata_maltrato_cual_fisico(chbMaltrato_nino_relata_maltrato_cual_fisico.isChecked() ? "S" : "N");
		aiepi_2_5.setMaltrato_nino_relata_maltrato_cual_sexual(chbMaltrato_nino_relata_maltrato_cual_sexual.isChecked() ? "S" : "N");
		aiepi_2_5.setMaltrato_nino_relata_maltrato_cual_negligencia(chbMaltrato_nino_relata_maltrato_cual_negligencia.isChecked() ? "S" : "N");
		aiepi_2_5.setMaltrato_nino_relata_maltrato_quien(tbxMaltrato_nino_relata_maltrato_quien.getValue());
		aiepi_2_5.setMaltrato_incongruencia_al_explicar_un_trauma_significante(rdbMaltrato_incongruencia_al_explicar_un_trauma_significante.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_incongruencia_lesion_edad_desarrollo_del_nino(rdbMaltrato_incongruencia_lesion_edad_desarrollo_del_nino.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_hay_diferentes_versiones(rdbMaltrato_hay_diferentes_versiones.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_es_tardia_la_consulta(rdbMaltrato_es_tardia_la_consulta.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_frecuencia_pegarle_a_su_hijo(tbxMaltrato_frecuencia_pegarle_a_su_hijo.getValue());
		aiepi_2_5.setMaltrato_desobediente_es_su_hijo_obligado_a_pegarle(tbxMaltrato_desobediente_es_su_hijo_obligado_a_pegarle.getValue());
		aiepi_2_5.setMaltrato_comportamiento_anormal_padres(lbxMaltrato_comportamiento_anormal_padres.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_agresividad_consulta(rdbMaltrato_agresividad_consulta.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_descuidado_nino_salud(rdbMaltrato_descuidado_nino_salud.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_descuidado_nino_salud_por(tbxMaltrato_descuidado_nino_salud_por.getValue());
		aiepi_2_5.setMaltrato_descuidado_nino_higiene(rdbMaltrato_descuidado_nino_higiene.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_descuidado_nino_proteccion(rdbMaltrato_descuidado_nino_proteccion.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_descuidado_nino_alimentacion(rdbMaltrato_descuidado_nino_alimentacion.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_descuidado_nino_de_la_calle(rdbMaltrato_descuidado_nino_de_la_calle.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_factor_riesgo(tbxMaltrato_factor_riesgo.getValue());
		aiepi_2_5.setMaltrato_hiperactivo(tbxMaltrato_hiperactivo.getValue());
		aiepi_2_5.setMaltrato_actividad_anormal_nino(rdbMaltrato_actividad_anormal_nino.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_lesiones_craneo(lbxMaltrato_lesiones_craneo.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_quemaduras(lbxMaltrato_quemaduras.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_equimosis(rdbMaltrato_equimosis.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_hematomas(rdbMaltrato_hematomas.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_laceraciones(rdbMaltrato_laceraciones.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_mordiscos(rdbMaltrato_mordiscos.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_cicatrices_lejos_prominencia_oseo_con_patron(rdbMaltrato_cicatrices_lejos_prominencia_oseo_con_patron.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_diferente_evolucion_en_ninos_que_no_deambulan(rdbMaltrato_diferente_evolucion_en_ninos_que_no_deambulan.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_sugestivas_de_maltrato(rdbMaltrato_sugestivas_de_maltrato.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_fracturas(lbxMaltrato_fracturas.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_trauma_viceral(rdbMaltrato_trauma_viceral.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_trauma_grave(rdbMaltrato_trauma_grave.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_lesion_fisica_sugestiva(tbxMaltrato_lesion_fisica_sugestiva.getValue());
		aiepi_2_5.setMaltrato_sangrado_vaginal_o_analtraumatico(rdbMaltrato_sangrado_vaginal_o_analtraumatico.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_trauma_genital(lbxMaltrato_trauma_genital.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_juego_contenido_sexual(rdbMaltrato_juego_contenido_sexual.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_boca_en_genitales(rdbMaltrato_boca_en_genitales.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_vih(rdbMaltrato_vih.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_gonorrea(rdbMaltrato_gonorrea.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_sifilis(rdbMaltrato_sifilis.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_trichomona_vaginalis(rdbMaltrato_trichomona_vaginalis.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_clamidia_trachomatis(rdbMaltrato_clamidia_trachomatis.getSelectedItem().getValue().toString());
		aiepi_2_5.setMaltrato_temeroso(chkMaltrato_temeroso.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_retraido(chkMaltrato_retraido.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_rechazo_adulto(chkMaltrato_rechazo_adulto.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_deprimido(chkMaltrato_deprimido.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_evita_contacto_visual(chkMaltrato_evita_contacto_visual.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_trastorno_sueno(chkMaltrato_trastorno_sueno.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_trastorno_alimentario(chkMaltrato_trastorno_alimentario.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_problemas_psicosomaticos(chkMaltrato_problemas_psicosomaticos.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_conductas_agresivas(chkMaltrato_conductas_agresivas.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_desarrollo_estancado(chkMaltrato_desarrollo_estancado.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_violencia_intrafamiliar(chkMaltrato_violencia_intrafamiliar.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_familia_caotica(chkMaltrato_familia_caotica.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_cuidadores_adictos(chkMaltrato_cuidadores_adictos.isChecked()?"S":"N");
		aiepi_2_5.setMaltrato_descuidado_nino_higiene_por(tbxMaltrato_descuidado_nino_higiene_por.getText());
		aiepi_2_5.setMaltrato_descuidado_nino_proteccion_por(tbxMaltrato_descuidado_nino_proteccion_por.getText());
		aiepi_2_5.setMaltrato_descuidado_nino_alimentacion_por(tbxMaltrato_descuidado_nino_alimentacion_por.getText());
		aiepi_2_5.setMaltrato_descuidado_nino_de_la_calle_por(tbxMaltrato_descuidado_nino_de_la_calle_por.getText());
		aiepi_2_5.setTipo_historia(tipo_historia);
		return aiepi_2_5;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_aiepi_mn_2_meses_5_anios aiepi_2_5 = (Hisc_aiepi_mn_2_meses_5_anios) obj;
		try {

			infoPacientes.setCodigo_historia(aiepi_2_5.getCodigo_historia());
			infoPacientes.setFecha_inicio(aiepi_2_5.getFecha_inicial());
			infoPacientes.setFecha_cierre(true, aiepi_2_5.getUltimo_update());
			initMostrar_datos(aiepi_2_5);
			//cargarAgudezaVisual(aiepi_2_5);
			cargarInformacion_paciente();
			onMostrarModuloRemisiones();
			cargarRemisionInterna(aiepi_2_5);
			
			tbxNombres_acompanante.setValue(aiepi_2_5.getNombre_acompananate());
			tbxApellidos_acompanante.setValue(aiepi_2_5	.getApellido_acompanante());
			Utilidades.seleccionarListitem(lbxParentesco_acompanante, aiepi_2_5.getParentesco_acompanante());
			Ocupaciones ocupaciones = new Ocupaciones();
			String oc = aiepi_2_5.getOcupacion_padre();
			if (oc != null && !oc.isEmpty()) {
				ocupaciones.setId(aiepi_2_5.getOcupacion_padre());
				ocupaciones = getServiceLocator().getOcupacionesService().consultar(ocupaciones);
				tbxOcupacion_padre.setValue(aiepi_2_5.getOcupacion_padre());
				tbxInfoOcupacion_padre.setValue((ocupaciones != null ? ocupaciones.getNombre() : ""));
			}
			oc = aiepi_2_5.getOcupacion_madre();
			if (oc != null && !oc.isEmpty()) {
				ocupaciones.setId(aiepi_2_5.getOcupacion_madre());
				ocupaciones = getServiceLocator().getOcupacionesService().consultar(ocupaciones);
				tbxOcupacion_madre.setValue(aiepi_2_5.getOcupacion_madre());
				tbxInfoOcupacion_madre.setValue((ocupaciones != null ? ocupaciones.getNombre() : ""));
			}
			tbxId_acompanante.setValue(aiepi_2_5.getId_acompanante());

			tbxTelefono_acompanante.setValue(aiepi_2_5
					.getTelefono_acompanante());
			tbxNombres_padre.setValue(aiepi_2_5.getNombre_padre());
			tbxApellidos_padre.setValue(aiepi_2_5.getApellidos_padre());
			tbxId_padre.setValue(aiepi_2_5.getId_padre());
			tbxTelefono_padre.setValue(aiepi_2_5.getTelefono_padre());
			if(aiepi_2_5.getEdad_padre()!=0){
				ibxEdad_padre.setValue(aiepi_2_5.getEdad_padre());
			}
			tbxNombres_madre.setValue(aiepi_2_5.getNombre_madre());
			tbxApellidos_madre.setValue(aiepi_2_5.getApellidos_madre());
			if(aiepi_2_5.getEdad_madre()!=0){
				tbxId_madre.setValue(aiepi_2_5.getId_madre());
			}
			tbxTelefono_madre.setValue(aiepi_2_5.getTelefono_madre());
			ibxEdad_madre.setValue(aiepi_2_5.getEdad_madre());
			tbxMotivo_consulta.setValue(aiepi_2_5.getMotivo_consulta());
			tbxEnfermedad_actual.setValue(aiepi_2_5.getEnfermedad_actual());
			tbxComo_fue_el_embarazo.setValue(aiepi_2_5
					.getComo_fue_el_embarazo());
			ibxCuanto_duro_embarazo.setValue(aiepi_2_5
					.getCuanto_duro_embarazo());
			tbxComo_fue_el_parto.setValue(aiepi_2_5.getComo_fue_el_parto());

			dbxPeso_al_nacer.setValue(Double.parseDouble(aiepi_2_5
					.getPeso_al_nacer()));

			dbxTalla_al_nacer.setValue(Double.parseDouble(aiepi_2_5
					.getTalla_al_nacer()));
			tbxPresento_algun_problema_neonatal.setValue(aiepi_2_5
					.getPresento_algun_problema_neonatal());
			tbxEnfermedades_previas.setValue(aiepi_2_5
					.getEnfermedades_previas());
			tbxHospitalizaciones.setValue(aiepi_2_5.getHospitalizaciones());
			dbxSignos_vitales_fc.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_fc()));
			dbxSignos_vitales_fr.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_fr()));
			dbxSignos_vitales_talla.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_talla()));
			dbxSignos_vitales_peso.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_peso()));
			dbxSignos_vitales_pc.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_pc()));
			dbxSignos_vitales_imc.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_imc()));
			dbxSignos_vitales_taxilar.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_taxilar()));
			dbxSignos_vitales_oximetria.setValue(Double.valueOf(aiepi_2_5
					.getSignos_vitales_oximetria()));
			Utilidades.seleccionarRadio(
					rdbTiene_tos_o_dificultad_para_respirar,
					aiepi_2_5.getTiene_tos_o_dificultad_para_respirar());
			Utilidades.seleccionarRadio(rdbTiene_diarrea,
					aiepi_2_5.getTiene_diarrea());
			Utilidades.seleccionarRadio(rdbTiene_fiebre,
					aiepi_2_5.getTiene_fiebre());
			Utilidades.seleccionarRadio(rdbTiene_problemas_de_oido,
					aiepi_2_5.getTiene_problemas_de_oido());
			Utilidades.seleccionarRadio(rdbTiene_un_problema_de_garganta,
					aiepi_2_5.getTiene_un_problema_de_garganta());
			Utilidades.seleccionarRadio(rdbSignos_vitales_sintomaticos_piel,
					aiepi_2_5.getSignos_vitales_sintomaticos_piel());
			Utilidades.seleccionarRadio(
					rdbSignos_vitales_sintomaticos_respiratorio,
					aiepi_2_5.getSignos_vitales_sintomaticos_respiratorio());

			// TODO: mostrar datos antecedentes vacunacion

			cbx_bcg__1.setChecked(aiepi_2_5.getBcg_1().equals("S") ? true
					: false);
			cbx_hepatitis_b_rn.setChecked(aiepi_2_5.getHepatitis_b_rn().equals(
					"S") ? true : false);
			cbx_hepatitis_b_1.setChecked(aiepi_2_5.getHepatitis_b1()
					.equals("S") ? true : false);
			cbx_hepatitis_b_2.setChecked(aiepi_2_5.getHepatitis_b2()
					.equals("S") ? true : false);
			cbx_hepatitis_b_3.setChecked(aiepi_2_5.getHepatitis_b3()
					.equals("S") ? true : false);
			cbx_vop_1.setChecked(aiepi_2_5.getVop_1().equals("S") ? true
					: false);
			cbx_vop_2.setChecked(aiepi_2_5.getVop_2().equals("S") ? true
					: false);
			cbx_vop_3.setChecked(aiepi_2_5.getVop_3().equals("S") ? true
					: false);
			cbx_vop_r1.setChecked(aiepi_2_5.getVop_r1().equals("S") ? true
					: false);
			cbx_vop_r2.setChecked(aiepi_2_5.getVop_r2().equals("S") ? true
					: false);
			cbx_rotavirus_1
					.setChecked(aiepi_2_5.getRotavirus1().equals("S") ? true
							: false);
			cbx_rotavirus_2
					.setChecked(aiepi_2_5.getRotavirus2().equals("S") ? true
							: false);
			cbx_influenza
					.setChecked(aiepi_2_5.getInfluenza().equals("S") ? true
							: false);
			tbx_otras_vacunas.setValue(aiepi_2_5.getOtras_vaunas());
			cbx_dtp_1
					.setChecked(aiepi_2_5.getDpt1().equals("S") ? true : false);
			cbx_dtp_2
					.setChecked(aiepi_2_5.getDpt2().equals("S") ? true : false);
			cbx_dtp_3
					.setChecked(aiepi_2_5.getDpt3().equals("S") ? true : false);
			cbx_dtp_r1.setChecked(aiepi_2_5.getDpt_r1().equals("S") ? true
					: false);
			cbx_dtp_r2.setChecked(aiepi_2_5.getDpt_r2().equals("S") ? true
					: false);
			cbx_haemophilus_influenza_tipo_b1.setChecked(aiepi_2_5
					.getHaemophilus_influenza_tipo_b1().equals("S") ? true
					: false);
			cbx_haemophilus_influenza_tipo_b2.setChecked(aiepi_2_5
					.getHaemophilus_influenza_tipo_b2().equals("S") ? true
					: false);
			cbx_haemophilus_influenza_tipo_b3.setChecked(aiepi_2_5
					.getHaemophilus_influenza_tipo_b3().equals("S") ? true
					: false);
			cbx_haemophilus_influenza_tipo_r1.setChecked(aiepi_2_5
					.getHaemophilus_influenza_tipo_r1().equals("S") ? true
					: false);
			cbx_haemophilus_influenza_tipo_r2.setChecked(aiepi_2_5
					.getHaemophilus_influenza_tipo_r2().equals("S") ? true
					: false);
			cbx_strectococo_neumonia_1.setChecked(aiepi_2_5
					.getStrectococo_neumonia1().equals("S") ? true : false);
			cbx_strectococo_neumonia_2.setChecked(aiepi_2_5
					.getStrectococo_neumonia2().equals("S") ? true : false);
			cbx_strectococo_neumonia_3.setChecked(aiepi_2_5
					.getStrectococo_neumonia3().equals("S") ? true : false);
			cbx_srp_1
					.setChecked(aiepi_2_5.getSrp1().equals("S") ? true : false);
			cbx_srp_2
					.setChecked(aiepi_2_5.getSrp2().equals("S") ? true : false);
			cbx_fiebre_amarilla.setChecked(aiepi_2_5.getFiebre_amarilla()
					.equals("S") ? true : false);
			ibx_edad.setValue(aiepi_2_5.getEdad());
			dbx_ultima_dosis.setValue(aiepi_2_5.getUltima_dosis());

			tbxObservaciones_cuadro1.setValue(aiepi_2_5.getObservaciones_cuadro1());
			tbxObservaciones_cuadro2.setValue(aiepi_2_5.getObservaciones_cuadro2());
			tbxObservaciones_cuadro3.setValue(aiepi_2_5.getObservaciones_cuadro3());
			tbxObservaciones_cuadro4.setValue(aiepi_2_5.getObservaciones_cuadro4());
			tbxObservaciones_cuadro5.setValue(aiepi_2_5.getObservaciones_cuadro5());
			tbxObservaciones_cuadro6.setValue(aiepi_2_5.getObservaciones_cuadro6());
			tbxObservaciones_cuadro7.setValue(aiepi_2_5.getObservaciones_cuadro7());
			tbxObservaciones_cuadro8.setValue(aiepi_2_5.getObservaciones_cuadro8());
			tbxObservaciones_cuadro9.setValue(aiepi_2_5.getObservaciones_cuadro9());
			tbxObservaciones_cuadro10.setValue(aiepi_2_5.getObservaciones_cuadro10());
			Utilidades.seleccionarRadio(rdbNino_registrado,aiepi_2_5.getNino_registrado());
			Utilidades.seleccionarListitem(lbxHemoclasificacion,aiepi_2_5.getHemoclasificacion());
			chbSignos_no_puede_beber.setChecked(aiepi_2_5.getSignos_no_puede_beber().equals("S") ? true : false);
			chbSignos_letargico_o_inconciente.setChecked(aiepi_2_5.getSignos_letargico_o_inconciente().equals("S") ? true : false);
			chbSignos_vomita_todo.setChecked(aiepi_2_5.getSignos_vomita_todo().equals("S") ? true : false);
			chbSignos_convulsiones.setChecked(aiepi_2_5.getSignos_convulsiones().equals("S") ? true : false);
			ibxTos_dias.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getTos_dias()));
			ibxTos_resppm.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getTos_resppm()));
			Utilidades.seleccionarRadio(rdbTos_respiracion_rapida,aiepi_2_5.getTos_respiracion_rapida());
			Utilidades.seleccionarRadio(rdbTos_primer_sibilancia,aiepi_2_5.getTos_primer_sibilancia());
			Utilidades.seleccionarRadio(rdbTos_tiraje_subcostal,aiepi_2_5.getTos_tiraje_subcostal());
			Utilidades.seleccionarRadio(rdbTos_sibilancia_recurrente,aiepi_2_5.getTos_sibilancia_recurrente());
			Utilidades.seleccionarRadio(rdbTos_tiraje_supraclavicular,aiepi_2_5.getTos_tiraje_supraclavicular());
			Utilidades.seleccionarRadio(rdbTos_cuadro_gripal_3dias,aiepi_2_5.getTos_cuadro_gripal_3dias());
			Utilidades.seleccionarRadio(rdbTos_sat_oxigeno,aiepi_2_5.getTos_sat_oxigeno());
			Utilidades.seleccionarRadio(rdbTos_antecedentes_prematuridad,aiepi_2_5.getTos_antecedentes_prematuridad());
			Utilidades.seleccionarRadio(rdbTos_estridor,aiepi_2_5.getTos_estridor());
			Utilidades.seleccionarRadio(rdbTos_apnea,aiepi_2_5.getTos_apnea());
			Utilidades.seleccionarRadio(rdbTos_sibilancias,aiepi_2_5.getTos_sibilancias());
			Utilidades.seleccionarRadio(rdbTos_somnoliento,aiepi_2_5.getTos_somnoliento());
			Utilidades.seleccionarRadio(rdbTos_incapacidad_hablar_beber,aiepi_2_5.getTos_incapacidad_hablar_beber());
			Utilidades.seleccionarRadio(rdbTos_agitado,aiepi_2_5.getTos_agitado());
			Utilidades.seleccionarRadio(rdbTos_confuso,aiepi_2_5.getTos_confuso());
			ibxDiarrea_dias.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getDiarrea_dias()));
			Utilidades.seleccionarRadio(rdbDiarrea_letargico_comatoso,aiepi_2_5.getDiarrea_letargico_comatoso());
			Utilidades.seleccionarRadio(rdbDiarrea_sangre_heces,aiepi_2_5.getDiarrea_sangre_heces());
			Utilidades.seleccionarRadio(rdbDiarrea_intranquilo_irritable,aiepi_2_5.getDiarrea_intranquilo_irritable());
			Utilidades.seleccionarRadio(rdbDiarrea_tiene_vomito,aiepi_2_5.getDiarrea_tiene_vomito());
			Utilidades.seleccionarRadio(rdbDiarrea_ojos_hundidos,aiepi_2_5.getDiarrea_ojos_hundidos());
			ibxDiarrea_cant_vomitos_24h.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getDiarrea_cant_vomitos_24h()));
			Utilidades.seleccionarRadio(rdbDiarrea_bebe_mal_no_puede,aiepi_2_5.getDiarrea_bebe_mal_no_puede());
			ibxDiarrea_cant_diarreas_24h.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getDiarrea_cant_diarreas_24h()));
			Utilidades.seleccionarRadio(rdbDiarrea_bebe_abidamente_con_sed,aiepi_2_5.getDiarrea_bebe_abidamente_con_sed());
			ibxDiarrea_cant_diarreas_4h.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getDiarrea_cant_diarreas_4h()));
			Utilidades.seleccionarRadio(rdbDiarrea_pliegue_cutaneo,aiepi_2_5.getDiarrea_pliegue_cutaneo());
			ibxFiebre_dias.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getFiebre_dias()));
			Utilidades.seleccionarRadio(rdbFiebre_rigidez_de_nuca,aiepi_2_5.getFiebre_rigidez_de_nuca());
			Utilidades.seleccionarRadio(rdbFiebre_apariencia_enfermo_grave,aiepi_2_5.getFiebre_apariencia_enfermo_grave());
			Utilidades.seleccionarRadio(rdbFiebre_mayor5_todos_los_dias,aiepi_2_5.getFiebre_mayor5_todos_los_dias());
			Utilidades.seleccionarRadio(rdbFiebre_manifestaciones_sangrado,aiepi_2_5.getFiebre_manifestaciones_sangrado());
			Utilidades.seleccionarRadio(rdbFiebre_aspecto_toxico,aiepi_2_5.getFiebre_aspecto_toxico());
			Utilidades.seleccionarRadio(rdbFiebre_mayor_38,aiepi_2_5.getFiebre_mayor_38());
			Utilidades.seleccionarRadio(rdbFiebre_mayor_39,aiepi_2_5.getFiebre_mayor_39());
			Utilidades.seleccionarRadio(rdbFiebre_respuesta_social,aiepi_2_5.getFiebre_respuesta_social());
			Utilidades.seleccionarRadio(rdbFiebre_piel,aiepi_2_5.getFiebre_piel());
			Utilidades.seleccionarRadio(rdbFiebre_erupcion_cutanea,aiepi_2_5.getFiebre_erupcion_cutanea());
			Utilidades.seleccionarRadio(rdbFiebre_dolor_abdominal,aiepi_2_5.getFiebre_dolor_abdominal());
			Utilidades.seleccionarRadio(rdbFiebre_vive_visito_15_dias_zona_dengue,aiepi_2_5.getFiebre_vive_visito_15_dias_zona_dengue());
			Utilidades.seleccionarListitem(lbxFiebre_zona_malaria,aiepi_2_5.getFiebre_zona_malaria());
			Utilidades.seleccionarRadio(rdbFiebre_cefaleas,aiepi_2_5.getFiebre_cefaleas());
			Utilidades.seleccionarRadio(rdbFiebre_mialgias,aiepi_2_5.getFiebre_mialgias());
			Utilidades.seleccionarRadio(rdbFiebre_altralgias,aiepi_2_5.getFiebre_altralgias());
			Utilidades.seleccionarRadio(rdbFiebre_dolor_retroociular,aiepi_2_5.getFiebre_dolor_retroociular());
			Utilidades.seleccionarRadio(rdbFiebre_postracion,aiepi_2_5.getFiebre_postracion());
			Utilidades.seleccionarRadio(rdbFiebre_ptorniquete,aiepi_2_5.getFiebre_ptorniquete());
			Utilidades.seleccionarRadio(rdbFiebre_lipotimia,aiepi_2_5.getFiebre_lipotimia());
			Utilidades.seleccionarRadio(rdbFiebre_hepatomegalia,aiepi_2_5.getFiebre_hepatomegalia());
			Utilidades.seleccionarRadio(rdbFiebre_disminucion_diuresis,aiepi_2_5.getFiebre_disminucion_diuresis());
			Utilidades.seleccionarRadio(rdbFiebre_pulso_rapido,aiepi_2_5.getFiebre_pulso_rapido());
			Utilidades.seleccionarRadio(rdbFiebre_ascitis,aiepi_2_5.getFiebre_ascitis());
			Utilidades.seleccionarRadio(rdbFiebre_laboratorio_ch_leucocitos_mayor_15000,aiepi_2_5.getFiebre_laboratorio_ch_leucocitos_mayor_15000());
			Utilidades.seleccionarRadio(rdbFiebre_laboratorio_neutrofilos_menor_4000,aiepi_2_5.getFiebre_laboratorio_neutrofilos_menor_4000());
			Utilidades.seleccionarRadio(rdbFiebre_laboratorio_plaquetas_mayor_10000,aiepi_2_5.getFiebre_laboratorio_plaquetas_mayor_10000());
			Utilidades.seleccionarRadio(rdbFiebre_laboratorio_plaquetas_menor_100000,aiepi_2_5.getFiebre_laboratorio_plaquetas_menor_100000());
			Utilidades.seleccionarRadio(rdbFiebre_parcial_orina_positiva,aiepi_2_5.getFiebre_parcial_orina_positiva());
			Utilidades.seleccionarRadio(rdbOido_dolor,aiepi_2_5.getOido_dolor());
			Utilidades.seleccionarRadio(rdbOido_tumefaccion_dolorosa_detras_oreja,aiepi_2_5.getOido_tumefaccion_dolorosa_detras_oreja());
			Utilidades.seleccionarRadio(rdbOido_supuracion,aiepi_2_5.getOido_supuracion());
			ibxOido_supuracion_dias.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getOido_supuracion_dias()));
			ibxOido_episodios_previos.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getOido_episodios_previos()));
			ibxOido_episodios_previos_meses.setValue(ConvertidorDatosUtil.convertirDatot(aiepi_2_5.getOido_episodios_previos_meses()));
			Utilidades.seleccionarRadio(rdbOido_timpano_rojo_abombado,aiepi_2_5.getOido_timpano_rojo_abombado());
			Utilidades.seleccionarRadio(rdbGarganta_dolor,aiepi_2_5.getGarganta_dolor());
			Utilidades.seleccionarRadio(rdbGarganta_ganglios_cuello_crecidos_dolorosos,aiepi_2_5.getGarganta_ganglios_cuello_crecidos_dolorosos());
			Utilidades.seleccionarRadio(rdbGarganta_amialgias_eritematosas,aiepi_2_5.getGarganta_amialgias_eritematosas());
			Utilidades.seleccionarRadio(rdbGarganta_exudado_blanquecino_amarillento_amigdalas,aiepi_2_5.getGarganta_exudado_blanquecino_amarillento_amigdalas());
			Utilidades.seleccionarRadio(rdbSalud_bucal_dolor_comer_masticar,aiepi_2_5.getSalud_bucal_dolor_comer_masticar());
			Utilidades.seleccionarRadio(rdbSalud_bucal_inflamacion_dolorosa_labio,aiepi_2_5.getSalud_bucal_inflamacion_dolorosa_labio());
			Utilidades.seleccionarRadio(rdbSalud_bucal_no_involucra_surco,aiepi_2_5.getSalud_no_involucra_surco());
			Utilidades.seleccionarRadio(rdbSalud_bucal_dolor_dientes,aiepi_2_5.getSalud_bucal_dolor_dientes());
			Utilidades.seleccionarListitem(lbxSalud_bucal_enrojecimiento_inflamacion_encia,aiepi_2_5.getSalud_bucal_enrojecimiento_inflamacion_encia());
			Utilidades.seleccionarRadio(rdbSalud_bucal_trauma_cara_boca,aiepi_2_5.getSalud_bucal_trauma_cara_boca());
			Utilidades.seleccionarRadio(rdbSalud_bucal_padres_hermanos_caries,aiepi_2_5.getSalud_bucal_padres_hermanos_caries());
			Utilidades.seleccionarRadio(rdbSalud_bucal_deformacion_contorno_encia,aiepi_2_5.getSalud_bucal_deformacion_contorno_encia());
			Utilidades.seleccionarListitem(lbxSalud_bucal_vesiculas,aiepi_2_5.getSalud_bucal_vesiculas());
			Utilidades.seleccionarListitem(lbxSalud_bucal_ulceras,aiepi_2_5.getSalud_bucal_ulceras());
			Utilidades.seleccionarListitem(lbxSalud_bucal_placas,aiepi_2_5.getSalud_bucal_placas());
			Utilidades.seleccionarRadio(rdbSalud_bucal_fractura,aiepi_2_5.getSalud_bucal_fractura());
			Utilidades.seleccionarRadio(rdbSalud_bucal_mobilidad,aiepi_2_5.getSalud_bucal_mobilidad());
			Utilidades.seleccionarRadio(rdbSalud_bucal_desplazamiento,aiepi_2_5.getSalud_bucal_desplazamiento());
			Utilidades.seleccionarRadio(rdbSalud_bucal_extrusion,aiepi_2_5.getSalud_bucal_extrusion());
			Utilidades.seleccionarRadio(rdbSalud_bucal_intrusion,aiepi_2_5.getSalud_bucal_intrusion());
			Utilidades.seleccionarRadio(rdbSalud_bucal_avulsion,aiepi_2_5.getSalud_bucal_avulsion());
			Utilidades.seleccionarListitem(lbxSalud_bucal_herida,aiepi_2_5.getSalud_bucal_herida());
			Utilidades.seleccionarListitem(lbxSalud_bucal_manchas,aiepi_2_5.getSalud_bucal_manchas());
			Utilidades.seleccionarRadio(rdbSalud_bucal_caries_cavitacionales,aiepi_2_5.getSalud_bucal_caries_cavitacionales());
			Utilidades.seleccionarRadio(rdbSalud_bucal_placa_bacteriana,aiepi_2_5.getSalud_bucal_placa_bacteriana());
			Utilidades.seleccionarRadio(rdbSalud_bucal_cuando_limpia_boca_manana,aiepi_2_5.getSalud_bucal_cuando_limpia_boca_manana());
			Utilidades.seleccionarRadio(rdbSalud_bucal_cuando_limpia_boca_tarde,aiepi_2_5.getSalud_bucal_cuando_limpia_boca_tarde());
			Utilidades.seleccionarRadio(rdbSalud_bucal_cuando_limpia_boca_noche,aiepi_2_5.getSalud_bucal_cuando_limpia_boca_noche());
			Utilidades.seleccionarRadio(rdbSalud_bucal_como_supervisa_limpieza_le_limpia_dientes,aiepi_2_5.getSalud_bucal_como_supervisa_limpieza_le_limpia_dientes());
			Utilidades.seleccionarRadio(rdbSalud_bucal_como_supervisa_limpieza_nino_solo,aiepi_2_5.getSalud_bucal_como_supervisa_limpieza_nino_solo());
			Utilidades.seleccionarRadio(rdbSalud_bucal_utiliza_cepillo,aiepi_2_5.getSalud_bucal_utiliza_cepillo());
			Utilidades.seleccionarRadio(rdbSalud_bucal_utiliza_crema,aiepi_2_5.getSalud_bucal_utiliza_crema());
			Utilidades.seleccionarRadio(rdbSalud_bucal_utiliza_seda,aiepi_2_5.getSalud_bucal_utiliza_seda());
			Utilidades.seleccionarRadio(rdbSalud_bucal_chupo_biberon,aiepi_2_5.getSalud_bucal_chupo_biberon());
			dtxSalud_bucal_ultima_consulta_odontologo.setValue(new Date(Long.valueOf(aiepi_2_5.getSalud_bucal_ultima_consulta_odontologo())));
			Utilidades.seleccionarRadio(rdbCrecimiento_emanciacion_visible,aiepi_2_5.getCrecimiento_emanciacion_visible());
			tbxCrecimiento_pe_de.setText(aiepi_2_5.getCrecimiento_pe_de());
			Utilidades.seleccionarRadio(rdbCrecimiento_edema_ambos,aiepi_2_5.getCrecimiento_edema_ambos());
			tbxCrecimiento_apariencia.setText(aiepi_2_5.getCrecimiento_apariencia());
			tbxCrecimiento_imc_edad.setText(aiepi_2_5.getCrecimiento_imc_edad());
			tbxCrecimiento_imc_edad_de.setText(aiepi_2_5.getCrecimiento_imc_edad_de());
			tbxCrecimiento_talla_edad_de.setText(aiepi_2_5.getCrecimiento_talla_edad_de());
			tbxCrecimiento_peso_talla_de.setText(aiepi_2_5.getCrecimiento_peso_talla_de());
			Utilidades.seleccionarListitem(lbxCrecimiento_tendencia_peso,aiepi_2_5.getCrecimiento_tendencia_peso());
			Utilidades.seleccionarRadio(rdbAnemia_hierro_ultimos_6meses,aiepi_2_5.getAnemia_hierro_ultimos_6meses());
			dtxAnemia_hierro_ultimos_6meses_cuando.setValue(new Date(Long.valueOf(aiepi_2_5.getAnemia_hierro_ultimos_6meses_cuando())));
			tbxAnemia_hierro_ultimos_6meses_cuanto_tiempo.setText(aiepi_2_5.getAnemia_hierro_ultimos_6meses_cuanto_tiempo());
			Utilidades.seleccionarListitem(lbxAnemia_palidez_palmar,aiepi_2_5.getAnemia_palidez_palmar());
			Utilidades.seleccionarListitem(lbxAnemia_palidez_conjutival,aiepi_2_5.getAnemia_palidez_conjutival());
			tbxMaltrato_como_se_produjeron_lesiones.setText(aiepi_2_5.getMaltrato_como_se_produjeron_lesiones());
			Utilidades.seleccionarRadio(rdbMaltrato_nino_relata_maltrato,aiepi_2_5.getMaltrato_nino_relata_maltrato());
			chbMaltrato_nino_relata_maltrato_cual_fisico.setChecked(aiepi_2_5.getMaltrato_nino_relata_maltrato_cual_fisico().equals("S") ? true : false);
			chbMaltrato_nino_relata_maltrato_cual_sexual.setChecked(aiepi_2_5.getMaltrato_nino_relata_maltrato_cual_sexual().equals("S") ? true : false);
			chbMaltrato_nino_relata_maltrato_cual_negligencia.setChecked(aiepi_2_5.getMaltrato_nino_relata_maltrato_cual_negligencia().equals("S") ? true : false);
			tbxMaltrato_nino_relata_maltrato_quien.setText(aiepi_2_5.getMaltrato_nino_relata_maltrato_quien());
			Utilidades.seleccionarRadio(rdbMaltrato_incongruencia_al_explicar_un_trauma_significante,aiepi_2_5.getMaltrato_incongruencia_al_explicar_un_trauma_significante());
			Utilidades.seleccionarRadio(rdbMaltrato_incongruencia_lesion_edad_desarrollo_del_nino,aiepi_2_5.getMaltrato_incongruencia_lesion_edad_desarrollo_del_nino());
			Utilidades.seleccionarRadio(rdbMaltrato_hay_diferentes_versiones,aiepi_2_5.getMaltrato_hay_diferentes_versiones());
			Utilidades.seleccionarRadio(rdbMaltrato_es_tardia_la_consulta,aiepi_2_5.getMaltrato_es_tardia_la_consulta());
			tbxMaltrato_frecuencia_pegarle_a_su_hijo.setText(aiepi_2_5.getMaltrato_frecuencia_pegarle_a_su_hijo());
			tbxMaltrato_desobediente_es_su_hijo_obligado_a_pegarle.setText(aiepi_2_5.getMaltrato_desobediente_es_su_hijo_obligado_a_pegarle());
			Utilidades.seleccionarListitem(lbxMaltrato_comportamiento_anormal_padres,aiepi_2_5.getMaltrato_comportamiento_anormal_padres());
			Utilidades.seleccionarRadio(rdbMaltrato_agresividad_consulta,aiepi_2_5.getMaltrato_agresividad_consulta());
			Utilidades.seleccionarRadio(rdbMaltrato_descuidado_nino_salud,aiepi_2_5.getMaltrato_descuidado_nino_salud());
			tbxMaltrato_descuidado_nino_salud_por.setText(aiepi_2_5.getMaltrato_descuidado_nino_salud_por());
			Utilidades.seleccionarRadio(rdbMaltrato_descuidado_nino_higiene,aiepi_2_5.getMaltrato_descuidado_nino_higiene());
			Utilidades.seleccionarRadio(rdbMaltrato_descuidado_nino_proteccion,aiepi_2_5.getMaltrato_descuidado_nino_proteccion());
			Utilidades.seleccionarRadio(rdbMaltrato_descuidado_nino_alimentacion,aiepi_2_5.getMaltrato_descuidado_nino_alimentacion());
			Utilidades.seleccionarRadio(rdbMaltrato_descuidado_nino_de_la_calle,aiepi_2_5.getMaltrato_descuidado_nino_de_la_calle());
			tbxMaltrato_factor_riesgo.setText(aiepi_2_5.getMaltrato_factor_riesgo());
			tbxMaltrato_hiperactivo.setText(aiepi_2_5.getMaltrato_hiperactivo());
			Utilidades.seleccionarRadio(rdbMaltrato_actividad_anormal_nino,aiepi_2_5.getMaltrato_actividad_anormal_nino());
			Utilidades.seleccionarListitem(lbxMaltrato_lesiones_craneo,aiepi_2_5.getMaltrato_lesiones_craneo());
			Utilidades.seleccionarListitem(lbxMaltrato_quemaduras,aiepi_2_5.getMaltrato_quemaduras());
			Utilidades.seleccionarRadio(rdbMaltrato_equimosis,aiepi_2_5.getMaltrato_equimosis());
			Utilidades.seleccionarRadio(rdbMaltrato_hematomas,aiepi_2_5.getMaltrato_hematomas());
			Utilidades.seleccionarRadio(rdbMaltrato_laceraciones,aiepi_2_5.getMaltrato_laceraciones());
			Utilidades.seleccionarRadio(rdbMaltrato_mordiscos,aiepi_2_5.getMaltrato_mordiscos());
			Utilidades.seleccionarRadio(rdbMaltrato_cicatrices_lejos_prominencia_oseo_con_patron,aiepi_2_5.getMaltrato_cicatrices_lejos_prominencia_oseo_con_patron());
			Utilidades.seleccionarRadio(rdbMaltrato_diferente_evolucion_en_ninos_que_no_deambulan,aiepi_2_5.getMaltrato_diferente_evolucion_en_ninos_que_no_deambulan());
			Utilidades.seleccionarRadio(rdbMaltrato_sugestivas_de_maltrato,aiepi_2_5.getMaltrato_sugestivas_de_maltrato());
			Utilidades.seleccionarListitem(lbxMaltrato_fracturas,aiepi_2_5.getMaltrato_fracturas());
			Utilidades.seleccionarRadio(rdbMaltrato_trauma_viceral,aiepi_2_5.getMaltrato_trauma_viceral());
			Utilidades.seleccionarRadio(rdbMaltrato_trauma_grave,aiepi_2_5.getMaltrato_trauma_grave());
			tbxMaltrato_lesion_fisica_sugestiva.setText(aiepi_2_5.getMaltrato_lesion_fisica_sugestiva());
			Utilidades.seleccionarRadio(rdbMaltrato_sangrado_vaginal_o_analtraumatico,aiepi_2_5.getMaltrato_sangrado_vaginal_o_analtraumatico());
			Utilidades.seleccionarListitem(lbxMaltrato_trauma_genital,aiepi_2_5.getMaltrato_trauma_genital());
			Utilidades.seleccionarRadio(rdbMaltrato_juego_contenido_sexual,aiepi_2_5.getMaltrato_juego_contenido_sexual());
			Utilidades.seleccionarRadio(rdbMaltrato_boca_en_genitales,aiepi_2_5.getMaltrato_boca_en_genitales());
			Utilidades.seleccionarRadio(rdbMaltrato_vih,aiepi_2_5.getMaltrato_vih());
			Utilidades.seleccionarRadio(rdbMaltrato_gonorrea,aiepi_2_5.getMaltrato_gonorrea());
			Utilidades.seleccionarRadio(rdbMaltrato_sifilis,aiepi_2_5.getMaltrato_sifilis());
			Utilidades.seleccionarRadio(rdbMaltrato_clamidia_trachomatis,aiepi_2_5.getMaltrato_clamidia_trachomatis());
			Utilidades.seleccionarRadio(rdbMaltrato_trichomona_vaginalis,aiepi_2_5.getMaltrato_trichomona_vaginalis());
			
			chkMaltrato_temeroso.setChecked(aiepi_2_5.getMaltrato_temeroso().equals("s")?true:false);
			chkMaltrato_retraido.setChecked(aiepi_2_5.getMaltrato_retraido().equals("s")?true:false);
			chkMaltrato_rechazo_adulto.setChecked(aiepi_2_5.getMaltrato_rechazo_adulto().equals("s")?true:false);
			chkMaltrato_deprimido.setChecked(aiepi_2_5.getMaltrato_deprimido().equals("s")?true:false);
			chkMaltrato_evita_contacto_visual.setChecked(aiepi_2_5.getMaltrato_evita_contacto_visual().equals("s")?true:false);
			chkMaltrato_trastorno_sueno.setChecked(aiepi_2_5.getMaltrato_trastorno_sueno().equals("s")?true:false);
			chkMaltrato_trastorno_alimentario.setChecked(aiepi_2_5.getMaltrato_trastorno_alimentario().equals("s")?true:false);
			chkMaltrato_problemas_psicosomaticos.setChecked(aiepi_2_5.getMaltrato_problemas_psicosomaticos().equals("s")?true:false);
			chkMaltrato_conductas_agresivas.setChecked(aiepi_2_5.getMaltrato_conductas_agresivas().equals("s")?true:false);
			chkMaltrato_desarrollo_estancado.setChecked(aiepi_2_5.getMaltrato_desarrollo_estancado().equals("s")?true:false);
			chkMaltrato_violencia_intrafamiliar.setChecked(aiepi_2_5.getMaltrato_violencia_intrafamiliar().equals("s")?true:false);
			chkMaltrato_familia_caotica.setChecked(aiepi_2_5.getMaltrato_familia_caotica().equals("s")?true:false);
			chkMaltrato_cuidadores_adictos.setChecked(aiepi_2_5.getMaltrato_cuidadores_adictos().equals("s")?true:false);
			
			tbxMaltrato_descuidado_nino_higiene_por.setText(aiepi_2_5.getMaltrato_descuidado_nino_higiene_por());
			tbxMaltrato_descuidado_nino_proteccion_por.setText(aiepi_2_5.getMaltrato_descuidado_nino_proteccion_por());
			tbxMaltrato_descuidado_nino_alimentacion_por.setText(aiepi_2_5.getMaltrato_descuidado_nino_alimentacion_por());
			tbxMaltrato_descuidado_nino_de_la_calle_por.setText(aiepi_2_5.getMaltrato_descuidado_nino_de_la_calle_por());

			mostrar_filas_ocultas();
			cargarEventosCuadros();
			
			calcularCoordenadas(false);
			
			cargarImpresionDiagnostica(aiepi_2_5);
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);
			cargarAnexo9_remision(aiepi_2_5);
			infoPacientes.setCodigo_historia(aiepi_2_5.getCodigo_historia());
			
			inicializarVista(tipo_historia);
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			
			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString())) {
				infoPacientes.setCodigo_historia(aiepi_2_5
						.getCodigo_historia());
				inicializarVista(tipo_historia);
			}
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios = (Hisc_aiepi_mn_2_meses_5_anios) obj;
		try {
			hisc_aiepi_mn_2_meses_5_anios.setDelete_user(getUsuarios().getCodigo()); 
			int result = hisc_aiepi_mn_2_meses_5_aniosService
					.eliminar(hisc_aiepi_mn_2_meses_5_anios);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(e,"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",	this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	private void cargarImpresionDiagnostica(
			Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios)
			throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica
				.setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
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
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("estado", admision.getEstado());
		parametros.put("codigo_administradora",	admision.getCodigo_administradora());
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
		//log.info(">>>>>>>>>>>>" + parametros);
		divModuloFarmacologico.appendChild(receta_ripsAction);
	}

	public void onMostrarModuloOrdenamiento() throws Exception {
		if (orden_servicioAction != null)
			orden_servicioAction.detach();
		// if (!cargo_farmacologico) {
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", admision.getNro_ingreso());
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
		//log.info(">>>>>>>>>>>>" + parametros);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }
	}

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			
			cargarEventosCuadros();
			
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());
			
//			btnMostrarTablaPesoEdad.setDisabled(edad >= 2);
//			btnGraficarPesoEdad.setDisabled(edad >= 2);
			btnMostrarTablaPerimetroCefalicoEdad.setDisabled(edad >= 4);
			//btnGraficarPerimetroCefalicoEdad.setDisabled(edad >= 4);
			btnCalcularPerimetroCefalicoEdad.setDisabled(edad >= 4);
			dbxSignos_vitales_pc.setDisabled(edad >= 4);
			lbObligatorioPc.setVisible(edad < 4);
			
			if(edad_meses<2){
				ihistoria_clinica =	IHistorias_clinicas.HC_MENOR_2_MESES;
			}else if(edad < 2 && edad_meses>= 2){
				ihistoria_clinica =	IHistorias_clinicas.HC_MENOR_2_MESES_2_ANOS;
			}else  if(edad >= 2 && edad<5){
				ihistoria_clinica =	IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS;
			}

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_2_5 = new Hisc_aiepi_mn_2_meses_5_anios();
				hisc_aiepi_2_5	.setCodigo_empresa(empresa.getCodigo_empresa());
				hisc_aiepi_2_5	.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				hisc_aiepi_2_5	.setIdentificacion(admision.getNro_identificacion());
				hisc_aiepi_2_5.setNro_ingreso(admision.getNro_ingreso());
				hisc_aiepi_2_5 = getServiceLocator()	.getServicio(Hisc_aiepi_mn_2_meses_5_aniosService.class).consultar(hisc_aiepi_2_5);
				if (hisc_aiepi_2_5 != null) {
					accionForm(OpcionesFormulario.MOSTRAR,	hisc_aiepi_2_5);
					infoPacientes.setCodigo_historia(hisc_aiepi_2_5.getCodigo_historia());				
				} else {
					groupboxEditar.setVisible(false);
					throw new Exception("No hay una historia clinica relacionada a este paciente admitido");
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

			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {

			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}

	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
		Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios = (Hisc_aiepi_mn_2_meses_5_anios) historia_anterior;
		codigo_historia_anterior = hisc_aiepi_mn_2_meses_5_anios
				.getCodigo_historia();
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_mesesA_5_anos = (Hisc_aiepi_mn_2_meses_5_anios) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });
			if (hisc_aiepi_mn_2_mesesA_5_anos.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			if (hisc_aiepi_mn_2_mesesA_5_anos.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(true);
		}

		codigo_historia_anterior = hisc_aiepi_mn_2_mesesA_5_anos.getCodigo_historia_anterior();
		tipo_historia = hisc_aiepi_mn_2_mesesA_5_anos.getTipo_historia();
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			paciente = admision.getPaciente();
			sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
					: ESexo.MASCULINO);
			fecha = paciente.getFecha_nacimiento();
			edad_meses = Integer.valueOf(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha),
					"2", false));
			edad = Integer.valueOf(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha),
					"1", false));
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
		
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
		}
	}

	// DATOS CURVAS CRECIMIENTO.
	public void calcularCoordenadas(Boolean mostrarAlerta) {
		calcularImcEdad(mostrarAlerta);
		calcularPerimetroCefalicoEdad(mostrarAlerta);
		calcularPesoTalla(mostrarAlerta);
		calcularTallaEdad(mostrarAlerta);
	}
	
	public void calcularImcEdad(Boolean alerta) {

		double talla;
		double peso;

		if (dbxSignos_vitales_talla.getValue() == null) {
			dbxSignos_vitales_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_talla.setStyle("background-color:white");
			talla = dbxSignos_vitales_talla.getValue();
		}

		if (dbxSignos_vitales_peso.getValue() == null) {
			dbxSignos_vitales_peso.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_peso.setStyle("background-color:white");
			peso = dbxSignos_vitales_peso.getValue();
		}

		if (dbxSignos_vitales_talla.getValue() != null
				&& dbxSignos_vitales_peso.getValue() != null
				&& tallaValida(talla) && pesoValido(peso)) {
			double imc = CalculadorUtil.calcularIMC(peso, talla / 100);
			coordenadaIMCEdad = calcularImcEdad(imc, fecha);
			dbxImc_e_de.setValue(coordenadaIMCEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(
						dbxImc_e_de, paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_IMC_EDAD);
			}
			btnGraficarImcEdad.setDisabled(false);
		} else {
			if (!(tallaValida(talla))) {
				Messagebox.show("La talla está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			} else if (!(pesoValido(peso))) {
				Messagebox.show("El peso está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}
	
	public void calcularPesoEdad(Boolean alerta) {

		double talla;
		double peso;

		if (dbxSignos_vitales_talla.getValue() == null) {
			dbxSignos_vitales_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_talla.setStyle("background-color:white");
			talla = dbxSignos_vitales_talla.getValue();
		}

		if (dbxSignos_vitales_peso.getValue() == null) {
			dbxSignos_vitales_peso.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_peso.setStyle("background-color:white");
			peso = dbxSignos_vitales_peso.getValue();
		}

		if (dbxSignos_vitales_talla.getValue() != null
				&& dbxSignos_vitales_peso.getValue() != null
				&& tallaValida(talla) && pesoValido(peso)) {
			coordenadaPesoEdad = calcularPesoEdad(peso, talla, fecha);
			dbxP_e_de.setValue(coordenadaPesoEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxP_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PESO_EDAD);
			}
			btnGraficarPesoEdad.setDisabled(false);
		} else {
			if (!(tallaValida(talla))) {
				Messagebox.show(
						"La talla está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			} else if (!(pesoValido(peso))) {
				Messagebox.show("El peso está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}

	}

	public void calcularTallaEdad(Boolean alerta) {
		double talla;
		if (dbxSignos_vitales_talla.getValue() == null) {
			dbxSignos_vitales_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_talla.setStyle("background-color:white");
			talla = dbxSignos_vitales_talla.getValue();
		}

		if (dbxSignos_vitales_talla.getValue() != null && tallaValida(talla)) {
			coordenadaTallaEdad = calcularTallaEdad(talla, fecha);
			dbxT_e_de.setValue(coordenadaTallaEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxT_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_TALLA_EDAD);
			}
			btnGraficarTallaEdad.setDisabled(false);
		} else {
			if (!(tallaValida(talla))) {
				Messagebox.show(
						"La talla está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	public void calcularPesoTalla(Boolean alerta) {

		double talla;
		double peso;

		if (dbxSignos_vitales_talla.getValue() == null) {
			dbxSignos_vitales_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_talla.setStyle("background-color:white");
			talla = dbxSignos_vitales_talla.getValue();
		}

		if (dbxSignos_vitales_peso.getValue() == null) {
			dbxSignos_vitales_peso.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_peso.setStyle("background-color:white");
			peso = dbxSignos_vitales_peso.getValue();
		}

		if (dbxSignos_vitales_talla.getValue() != null
				&& dbxSignos_vitales_peso.getValue() != null && tallaValida(talla) && pesoValido(peso)) {
			coordenadaPesoTalla = calcularPesoTalla(peso, talla);
			dbxP_t_de.setValue(coordenadaPesoTalla.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxP_t_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PESO_TALLA);
			}
			btnGraficarPesoTalla.setDisabled(false);
		} else {
			if (!(tallaValida(talla))) {
				Messagebox.show(
						"La talla está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			} else if (!pesoValido(peso)) {
				Messagebox.show("El peso está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}

	public void calcularPerimetroCefalicoEdad(Boolean alerta) {
		if(edad<4){
			double perimetro_cefalico;
			if (dbxSignos_vitales_pc.getValue() == null) {

					dbxSignos_vitales_pc
							.setStyle("background-color:#F6BBBE");
					Messagebox
							.show("Debe digitar el perimetro cefálico del paciente para realizar este cálculo!!",
									"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
					return;
			} else {
				dbxSignos_vitales_pc
						.setStyle("background-color:white");
				perimetro_cefalico = dbxSignos_vitales_pc.getValue();
			}

			coordenadaPerimetroCefalicoEdad = calcularPerimetroCefalicoEdad(perimetro_cefalico);
			dbxPC_e_de.setValue(coordenadaPerimetroCefalicoEdad.getValor());

			btnGraficarPerimetroCefalicoEdad.setDisabled(false);

			if (dbxSignos_vitales_pc.getValue() != null
					&& perimetroCefalicoValido(perimetro_cefalico)) {
				coordenadaPerimetroCefalicoEdad = calcularPerimetroCefalicoEdad(perimetro_cefalico);
				dbxPC_e_de.setValue(coordenadaPerimetroCefalicoEdad.getValor());
				if (alerta) {
					UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxPC_e_de,
							paciente.getFecha_nacimiento(),
							UtilidadSignosVitales.DE_PC_EDAD);
				}
				btnGraficarPerimetroCefalicoEdad.setDisabled(false);
			} else {
				Messagebox.show(
						"El perimetro cefálico está por fuera del rango!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}		
	}

	public boolean pesoValido(Double peso) {
		Double min = 1d;
		Double max = 20d;
		return (peso >= min && peso <= max);
	}

	public boolean tallaValida(Double talla) {
		Double min = 39d;
		Double max = 200d;
		return (talla >= min && talla <= max);
	}

	public boolean perimetroCefalicoValido(Double perimetro_cefalico) {
		Double min = 29.0;
		Double max = 60.0;
		return (perimetro_cefalico >= min && perimetro_cefalico <= max);
	}

	public void mostrarTablaTallaEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_DE_0_A_5, this,
				sexo);
	};
	
	public void mostrarTablaPesoTalla() {
		if(edad>2 && edad <5){
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_2_A_5, this,
				sexo);
		}else{
			GraficasCalculadorUtils.mostrarTabla(
					GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_0_A_2, this,
					sexo);
		}
	};
	
	public void mostrarTablaIMCEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_0_A_5, this, sexo);
	};
	
	public void mostrarTablaPcEdad() {
		GraficasCalculadorUtils
				.mostrarTabla(
						GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5,
						this, sexo);
	};
	
	public void mostrarTablaPesoEdad() {
		GraficasCalculadorUtils.mostrarTabla(GraficasCalculadorUtils.TipoGrafica.PESO_EDAD_0_A_2_ANIOS,	this, sexo);
	};
	
	private void parametrizarBandboxOcupacion(
			BandboxRegistrosMacro tbxCodigo_ocupacion,
			Textbox tbxInfoOcupacion, Toolbarbutton btnLimpiarOcupacion) {
		tbxCodigo_ocupacion.inicializar(tbxInfoOcupacion, btnLimpiarOcupacion);
		tbxCodigo_ocupacion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Ocupaciones>() {

					@Override
					public void renderListitem(Listitem listitem,
							Ocupaciones registro) {
						listitem.appendChild(new Listcell(registro.getId()));
						listitem.appendChild(new Listcell(registro.getNombre()));
					}

					@Override
					public List<Ocupaciones> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						return getServiceLocator().getOcupacionesService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Ocupaciones registro) {
						bandbox.setValue(registro.getId());
						textboxInformacion.setValue(registro.getNombre());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	private RespuestaInt calcularPesoEdad(double peso, double talla,
			Timestamp fecha) {
		if (edad<2) {
			return GraficasCalculadorUtils.calcularPesoEdad0$2Anios(sexo, peso,
					talla, fecha);
		} else {
			return GraficasCalculadorUtils.calcularPesoEdad2$5Anios(sexo, peso,
					talla, fecha);
		}
	}

	private RespuestaInt calcularPesoTalla(double peso, double talla) {
		if (edad<2) {
			return GraficasCalculadorUtils.calcularPesoTalla0$2Anios(sexo,
					peso, talla);
		} else {
			return GraficasCalculadorUtils.calcularPesoTalla2$5Anios(sexo,
					peso, talla);
		}
	}

	private RespuestaInt calcularPerimetroCefalicoEdad(double perimetro_cefalico) {
			return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
					sexo, perimetro_cefalico, fecha);
	}
	
	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		if (edad<2) {
			return GraficasCalculadorUtils.calcularTallaEdad0$2Anios(sexo,
					talla, fecha);
		} else {
			return GraficasCalculadorUtils.calcularTallaEdad2$5Anios(sexo,
					talla, fecha);
		}
	}

	private RespuestaInt calcularImcEdad(double imc, Timestamp fecha) {
		return GraficasCalculadorUtils
				.calcularIMCEdad0$5Anios(sexo, imc, fecha);
	}

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			if (dbxP.getValue() != null && dbxT.getValue() != null) {
				double imc = dbxP.getValue()
						/ (Math.pow(dbxT.getValue() / 100, 2));
				dbxI.setValue(imc);
				// UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
				// UtilidadSignosVitales.TALLA_CENTIMETROS,
				// UtilidadSignosVitales.PESO_KILOS);
			}
		} catch (Exception e) {
			// MensajesUtil.mensajeError(e, "", this);
			// e.printStackTrace();
		}
	}

	public void alertaFc(Doublebox dbxSignos_vitales_fc) {
		UtilidadSignosVitales.onMostrarAlertaFrecuenciaCardiaca(
				dbxSignos_vitales_fc, fecha, paciente.getSexo());
	}

	public void alertaFr(Doublebox dbxSignos_vitales_fr) {
		UtilidadSignosVitales.onMostrarAlertaFrecuenciaRespiratoria(
				dbxSignos_vitales_fr, fecha, paciente.getSexo());
	}

	public void alertaTemperatura(Doublebox dbxSignos_vitales_taxilar) {
		UtilidadSignosVitales.onMostrarAlertaTemperatura(
				dbxSignos_vitales_taxilar, fecha, paciente.getSexo());
	}

	public void mostrarAnexo(Integer i) {
		switch (i) {
		case 0:
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/observacion_lactancia.png"), this.getPage(),
					"observacion de lactancia materna", 0, 0, 0);
			break;
		case 1:
			CuadroAIEPIMacro
					.mostrarVentana(
							new Image("/images/aiepi/vacunacion_ninos.png"),
							this.getPage(),
							"Verificar los antecedentes de vacunacion de todos los niños",
							0, 0, 0);
			break;
		case 2:
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/plan_a_tratar_diarrea.png"), this.getPage(),
					"Tratar la diarrea en la casa", 0, 0, 0);
			break;
		case 3:
			CuadroAIEPIMacro
					.mostrarVentana(
							new Image("/images/aiepi/tos_resfriado.png"),
							this.getPage(),
							"Cuidados del niño con tos o resfriado en casa y medidas preventivas",
							0, 0, 0);
			break;
		case 4:
			CuadroAIEPIMacro
					.mostrarVentana(
							new Image("/images/aiepi/febril.png"),
							this.getPage(),
							"Medidas preventivas para los niños con enfermedades febriles",
							0, 0, 0);
			break;
		case 5:
			CuadroAIEPIMacro
					.mostrarVentana(new Image("/images/aiepi/sro.png"),
							this.getPage(), "Tratar la deshidratacion con sro",
							0, 0, 0);
			break;
		case 6:
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/buen_trato_2_5.png"), this.getPage(),
					"Medidas de buen trato", 0, 0, 0);
			break;
		}
	}

	public void mostrarIni() {
		if (admision != null	&& opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
				CuadroAIEPIMacro.mostrarVentana(new Image(	"/images/aiepi/lavado_manos_texto.png"), this.getPage(),"Lávese las manos", 0, 0,0);
		}
	}

	public void calcularProximaConsultaControl(Integer dias) {
		Calendar prox = Calendar.getInstance();
		prox.setTime(new Date());
		prox.set(Calendar.DAY_OF_YEAR, prox.get(Calendar.DAY_OF_YEAR) + dias);
		dtxVolver_consulta_control.setValue(prox.getTime());
	}

	public void graficarPesoEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_E);
		
		List<Coordenadas_graficas> pes = getServiceLocator()	.getCoordenadas_graficasService().listar(parameters);
		for (int i = 0; i < pes.size(); i++) {
			Coordenadas_graficas cg = pes.get(i);
			if (cg.getIhistoria_clinica() == IHistorias_clinicas.HC_MENOR_2_MESES) {
				pes.remove(i);
			}
		}
		coordenadasPE = cargarRespuestas(pes, hisc_aiepi_2_5);

		List coordenadas_pe = coordenadasPE;
		if (!verificarActivo(coordenadasPE)) {
			coordenadas_pe.add(coordenadaPesoEdad);
		}
		imprimirArreglo(coordenadas_pe);
		GraficasCalculadorUtils.mostrarGrafica(GraficasCalculadorUtils.TipoGrafica.PESO_EDAD_0_A_2_ANIOS, coordenadas_pe, this, sexo);
	}
	
	public void graficarTallaEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.T_E);
		parameters.put("ihistoria_clinica",	ihistoria_clinica);
		List<Coordenadas_graficas> tes = getServiceLocator()	.getCoordenadas_graficasService().listar(parameters);
		coordenadasTE = cargarRespuestas(tes, hisc_aiepi_2_5);

		List coordenadas_te = coordenadasTE;
		if (!verificarActivo(coordenadasTE)) {
			coordenadas_te.add(coordenadaTallaEdad);
		}
		imprimirArreglo(coordenadas_te);
		GraficasCalculadorUtils.mostrarGrafica(GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_DE_0_A_5,	coordenadas_te, this, sexo);
	}

	public void graficarPesoTalla() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_T);
		parameters.put("ihistoria_clinica", ihistoria_clinica);
		List<Coordenadas_graficas> pts = getServiceLocator().getCoordenadas_graficasService().listar(parameters);
		coordenadasPT = cargarRespuestas(pts, hisc_aiepi_2_5);

		List coordenadas_pt = coordenadasPT;
		if (!verificarActivo(coordenadasPT)) {
			coordenadas_pt.add(coordenadaPesoTalla);
		}
		imprimirArreglo(coordenadas_pt);
		if(edad>=2 && edad<5){
			GraficasCalculadorUtils.mostrarGrafica(GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_2_A_5,	coordenadas_pt, this, sexo);
		}else if(edad_meses >= 2 && edad<2){
			GraficasCalculadorUtils.mostrarGrafica(GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_0_A_2,	coordenadas_pt, this, sexo);
		}
	}

	public void graficarIMCEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.IMC_E);
		parameters.put("ihistoria_clinica",	ihistoria_clinica);
		List<Coordenadas_graficas> imces = getServiceLocator()	.getCoordenadas_graficasService().listar(parameters);
		coordenadasIE = cargarRespuestas(imces,	hisc_aiepi_2_5);

		List coordenadas_imce = coordenadasIE;
		if (!verificarActivo(coordenadasIE)) {
			coordenadas_imce.add(coordenadaIMCEdad);
		}

		imprimirArreglo(coordenadas_imce);
		GraficasCalculadorUtils.mostrarGrafica(GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_0_A_5,	coordenadas_imce, this, sexo);
	}
	
	public void graficarPcEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.PC_E);
		parameters.put("ihistoria_clinica", ihistoria_clinica);
		List<Coordenadas_graficas> pces = getServiceLocator().getCoordenadas_graficasService().listar(parameters);

		coordenadasPCE = cargarRespuestas(pces, hisc_aiepi_2_5);

		List coordenadas_pce = coordenadasPCE;
		if (!verificarActivo(coordenadasPCE)) {
			coordenadas_pce.add(coordenadaPerimetroCefalicoEdad);
		}
		imprimirArreglo(coordenadas_pce);
		GraficasCalculadorUtils.mostrarGrafica(GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5, coordenadas_pce, this, sexo);
	}
	
	public void imprimirArreglo(List<RespuestaInt> arreglo) {
//		for (RespuestaInt ri : arreglo) {
//			//log.info(">>>>>>>>>>>>" + ri.isActual());
//		}
	}
	
	private List<RespuestaInt> cargarRespuestas(List<Coordenadas_graficas> coordenadas,	Hisc_aiepi_mn_2_meses_5_anios his_aiepiA_5_anos) {
		List<RespuestaInt> respuestas = new ArrayList<RespuestaInt>();
		for (Coordenadas_graficas cg : coordenadas) {
			RespuestaInt r = cargarResuestaInt(cg);
			if (his_aiepiA_5_anos != null && cg.getCodigo_historia().equals(his_aiepiA_5_anos.getCodigo_historia())) {
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
	
//	private void alertasPeso(Doublebox doublebox) {
//
//		String mensaje = "";
//		String tipoMensaje = "";
//		Integer valor;
//
//		if (doublebox != null) {
//			valor = doublebox.getValue().intValue();
//
//			if (doublebox == dbxP_t_de) {
//				if (valor > 2) {
//					mensaje = "OBESIDAD";
//					mensaje += "";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				} else if (valor > 1 && valor <= 2) {
//					mensaje = "SOBREPESO";
//					mensaje += "";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
//				} else if (valor > -1 && valor <= 1) {
//					mensaje = "NORMAL: ";
//					mensaje += "Peso adecuado para la talla";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_INFO;
//				} else if (valor > -2 && valor <= -1) {
//					mensaje = "A RIESGO: ";
//					mensaje += "Con bajo peso para la talla";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
//				} else if (valor > -3 && valor <= -2) {
//					mensaje = "DESNUTRIcion: ";
//					mensaje += "Peso bajo para la talla o desnutricion aguda";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				} else if (valor < -3) {
//					mensaje = "DESNUTRIcion SEVERA: ";
//					mensaje += "Desnutricion aguda severa";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				}
//			} else if (doublebox == dbxP_e_de) {
//				if (valor > 2) {
//					mensaje = "OBESIDAD";
//					mensaje += "";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				} else if (valor > 1 && valor <= 2) {
//					mensaje = "SOBREPESO";
//					mensaje += "";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
//				} else if (valor > -1 && valor <= 1) {
//					mensaje = "NORMAL: ";
//					mensaje += "Peso adecuado para la edad";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_INFO;
//				} else if (valor > -2 && valor <= -1) {
//					mensaje = "A RIESGO: ";
//					mensaje += "Con bajo peso para su edad";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
//				} else if (valor > -3 && valor <= -2) {
//					mensaje = "DESNUTRIcion: ";
//					mensaje += "Peso bajo para la edad o desnutricion global";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				} else if (valor < -3) {
//					mensaje = "DESNUTRIcion SEVERA: ";
//					mensaje += "Desnutricion global severa";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				}
//			} else if (doublebox == dbxT_e_de) {
//				if (valor > 2) {
//					mensaje = "OBESIDAD";
//					mensaje += "";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				} else if (valor > 1 && valor <= 2) {
//					mensaje = "SOBREPESO";
//					mensaje += "";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
//				} else if (valor > -1 && valor <= 1) {
//					mensaje = "NORMAL: ";
//					mensaje += "Talla adecuada para edad";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_INFO;
//				} else if (valor > -2 && valor <= -1) {
//					mensaje = "A RIESGO: ";
//					mensaje += "Con baja talla para la edad";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
//				} else if (valor > -3 && valor <= -2) {
//					mensaje = "DESNUTRIcion: ";
//					mensaje += "Retraso del crecimiento o desnutricion crónica";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				} else if (valor < -3) {
//					mensaje = "DESNUTRIcion SEVERA: ";
//					mensaje += "Desnutricion crónica severa";
//					tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
//				}
//			}
//			if (!mensaje.isEmpty() && !tipoMensaje.isEmpty()) {
//				Clients.showNotification(mensaje, tipoMensaje, doublebox,
//						"after_center", 5000, true);
//			}
//		}
//	}

	private void cargarAnexo9_remision(
			Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_aiepi_mn_2_meses_5_anios.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		anexo9_entidad = getServiceLocator().getServicio(Anexo9_entidadService.class).consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad (r)=== >" + anexo9_entidad);
		if(anexo9_entidad!=null){
			remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
		}
	}

	private void cargarRemisionInterna(
			Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
						.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_aiepi_mn_2_meses_5_anios
						.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_aiepi_mn_2_meses_5_anios
						.getCodigo_sucursal());
		//log.info(">>>>>>>>>>>>"+remision_interna);
		remision_interna = getServiceLocator().getServicio(Remision_internaService.class).consultar(remision_interna);
		//log.info(">>>>>>>>>>>>"+remision_interna);
		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null)
			remisiones_externasAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", admision.getNro_ingreso());
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
		informacion_clinica.append("- SIGNOS VITALES\n");
		informacion_clinica.append("\tFrecuencia cardiaca: ")
				.append(dbxSignos_vitales_fc.getValue()).append("\n");
		informacion_clinica.append("\tFrecuencia respiratoria: ")
				.append(dbxSignos_vitales_fr.getValue())
				.append("\n");
		informacion_clinica.append("\tTemperatura: ")
				.append(dbxSignos_vitales_taxilar.getValue()).append("\n");
		informacion_clinica.append("\tPeso(kg): ")
				.append(dbxSignos_vitales_peso.getValue()).append("\n");
		informacion_clinica.append("\tTalla: ")
				.append(dbxSignos_vitales_talla.getValue()).append("\n");
		informacion_clinica.append("\tIMC: ").append(dbxSignos_vitales_imc.getValue())
				.append("\n").append("\n");

		informacion_clinica.append("- DIAGNOSTICOS").append("\n");
		Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
				.obtenerImpresionDiagnostica();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\timpresion diagnóstica: ")
				.append(impresion_diagnostica.getCie_principal()).append(" ")
				.append(cie != null ? cie.getNombre() : "").append("\n");
		;
		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_principal(),
						"tipo_diagnostico", this)).append("\n");

		/* relacionado 1 */
		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado1());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\tRelacionado 1: ")
				.append(impresion_diagnostica.getCie_relacionado1())
				.append(" ").append(cie != null ? cie.getNombre() : "")
				.append("\n");

		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_relacionado1(),
						"tipo_diagnostico", this)).append("\n");

		/* relacionado 2 */
		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\tRelacionado 2: ")
				.append(impresion_diagnostica.getCie_relacionado2())
				.append(" ").append(cie != null ? cie.getNombre() : "")
				.append("\n");

		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_relacionado2(),
						"tipo_diagnostico", this)).append("\n");

		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\tRelacionado 3: ")
				.append(impresion_diagnostica.getCie_relacionado3())
				.append(" ").append(cie != null ? cie.getNombre() : "")
				.append("\n");

		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_relacionado3(),
						"tipo_diagnostico", this)).append("\n");
		return informacion_clinica.toString();
	}
	
	public void onSeleccionarRelacionAcompaniante() {
		if(lbxParentesco_acompanante.getSelectedItem().getValue().toString().equals("1")){
			//Madre
			
			tbxId_madre.setValue(tbxId_acompanante.getValue());
			tbxNombres_madre.setValue(tbxNombres_acompanante.getValue());
			tbxApellidos_madre.setValue(tbxApellidos_acompanante.getValue());
			tbxTelefono_madre.setValue(tbxTelefono_acompanante.getValue());
			
			if(tbxId_madre.getValue().equals(tbxId_padre.getValue())){
				tbxId_padre.setValue("");
			}
			if(tbxNombres_madre.getValue().equals(tbxNombres_padre.getValue())){
				tbxNombres_padre.setValue("");
			}
			if(tbxApellidos_madre.getValue().equals(tbxApellidos_padre.getValue())){
				tbxApellidos_padre.setValue("");
			}
			if(tbxTelefono_madre.getValue().equals(tbxTelefono_padre.getValue())){
				tbxTelefono_padre.setValue("");
			}						
		}else if(lbxParentesco_acompanante.getSelectedItem().getValue().toString().equals("2")){
			//Padre
		
			tbxId_padre.setValue(tbxId_acompanante.getValue());
			tbxNombres_padre.setValue(tbxNombres_acompanante.getValue());
			tbxApellidos_padre.setValue(tbxApellidos_acompanante.getValue());
			tbxTelefono_padre.setValue(tbxTelefono_acompanante.getValue());
			
			if(tbxId_padre.getValue().equals(tbxId_madre.getValue())){
				tbxId_madre.setValue("");
			}
			if(tbxNombres_padre.getValue().equals(tbxNombres_madre.getValue())){
				tbxNombres_madre.setValue("");
			}
			if(tbxApellidos_padre.getValue().equals(tbxApellidos_madre.getValue())){
				tbxApellidos_madre.setValue("");
			}
			if(tbxTelefono_padre.getValue().equals(tbxTelefono_madre.getValue())){
				tbxTelefono_madre.setValue("");
			}
		}
		
		if (lbxParentesco_acompanante.getSelectedItem().getValue().toString().equals("8")) {
			tbxOtro_acompanante.setVisible(true);
		} else {
			tbxOtro_acompanante.setVisible(false);
		}
	}
	
	private void cargarEventosCuadros(){
		Component[] componentes_cuadro1 = {
				chbSignos_no_puede_beber,chbSignos_letargico_o_inconciente,
				chbSignos_vomita_todo,chbSignos_convulsiones
				};
		
		Component[] componentes_cuadro2 = {
				//Incluye los signos generales de peligro
				chbSignos_no_puede_beber,chbSignos_letargico_o_inconciente,
				chbSignos_vomita_todo,chbSignos_convulsiones,
				
				dbxSignos_vitales_oximetria, rdbTiene_tos_o_dificultad_para_respirar, ibxTos_dias,dbxSignos_vitales_fr,ibxTos_resppm,rdbTos_respiracion_rapida,rdbTos_primer_sibilancia,rdbTos_tiraje_subcostal,
				rdbTos_sibilancia_recurrente,rdbTos_tiraje_supraclavicular,rdbTos_cuadro_gripal_3dias,
				rdbTos_sat_oxigeno,rdbTos_antecedentes_prematuridad,rdbTos_estridor,rdbTos_apnea,
				rdbTos_sibilancias,rdbTos_somnoliento,rdbTos_incapacidad_hablar_beber,rdbTos_agitado,rdbTos_confuso
				};

		Component[] componentes_cuadro3 = {
				rdbTiene_diarrea, ibxDiarrea_dias,rdbDiarrea_letargico_comatoso,rdbDiarrea_sangre_heces,rdbDiarrea_intranquilo_irritable,
				rdbDiarrea_tiene_vomito,rdbDiarrea_ojos_hundidos,ibxDiarrea_cant_vomitos_24h,rdbDiarrea_bebe_mal_no_puede,
				ibxDiarrea_cant_diarreas_24h,rdbDiarrea_bebe_abidamente_con_sed,ibxDiarrea_cant_diarreas_4h,rdbDiarrea_pliegue_cutaneo
		};
		
		Component[] componentes_cuadro4 = {
				//Incluye los signos generales de peligro
				chbSignos_no_puede_beber,chbSignos_letargico_o_inconciente,
				chbSignos_vomita_todo,chbSignos_convulsiones,
				
				dbxSignos_vitales_taxilar, rdbTiene_fiebre, ibxFiebre_dias,rdbFiebre_rigidez_de_nuca,rdbFiebre_apariencia_enfermo_grave,rdbFiebre_mayor5_todos_los_dias,
				rdbFiebre_manifestaciones_sangrado,rdbFiebre_aspecto_toxico,rdbFiebre_mayor_38,rdbFiebre_mayor_39,
				rdbFiebre_respuesta_social,rdbFiebre_piel,rdbFiebre_erupcion_cutanea,rdbFiebre_dolor_abdominal,rdbFiebre_vive_visito_15_dias_zona_dengue,lbxFiebre_zona_malaria,
				rdbFiebre_cefaleas,rdbFiebre_mialgias,rdbFiebre_altralgias,rdbFiebre_dolor_retroociular,rdbFiebre_postracion,
				rdbFiebre_ptorniquete,rdbFiebre_lipotimia,rdbFiebre_hepatomegalia,rdbFiebre_disminucion_diuresis,
				rdbFiebre_pulso_rapido,rdbFiebre_ascitis,rdbFiebre_laboratorio_ch_leucocitos_mayor_15000,rdbFiebre_laboratorio_neutrofilos_menor_4000,
				rdbFiebre_laboratorio_plaquetas_mayor_10000,rdbFiebre_laboratorio_plaquetas_menor_100000,rdbFiebre_parcial_orina_positiva
		};

		Component[] componentes_cuadro5 = {
				rdbTiene_problemas_de_oido, rdbOido_dolor,rdbOido_tumefaccion_dolorosa_detras_oreja,
				rdbOido_supuracion,ibxOido_supuracion_dias,ibxOido_episodios_previos,
				ibxOido_episodios_previos_meses,rdbOido_timpano_rojo_abombado
		};
		
		Component[] componentes_cuadro6 = {
				rdbTiene_un_problema_de_garganta, rdbGarganta_dolor,rdbGarganta_ganglios_cuello_crecidos_dolorosos,
				rdbGarganta_amialgias_eritematosas,rdbGarganta_exudado_blanquecino_amarillento_amigdalas
		};

		Component[] componentes_cuadro7 = {
				rdbSalud_bucal_dolor_comer_masticar,rdbSalud_bucal_inflamacion_dolorosa_labio,rdbSalud_bucal_no_involucra_surco,
				rdbSalud_bucal_dolor_dientes,lbxSalud_bucal_enrojecimiento_inflamacion_encia,rdbSalud_bucal_trauma_cara_boca,
				rdbSalud_bucal_padres_hermanos_caries,rdbSalud_bucal_deformacion_contorno_encia,lbxSalud_bucal_vesiculas,
				lbxSalud_bucal_ulceras,lbxSalud_bucal_placas,rdbSalud_bucal_fractura,rdbSalud_bucal_mobilidad,
				rdbSalud_bucal_desplazamiento,rdbSalud_bucal_extrusion,rdbSalud_bucal_intrusion,rdbSalud_bucal_avulsion,
				lbxSalud_bucal_herida,lbxSalud_bucal_manchas,rdbSalud_bucal_caries_cavitacionales,rdbSalud_bucal_placa_bacteriana,
				rdbSalud_bucal_cuando_limpia_boca_manana,rdbSalud_bucal_cuando_limpia_boca_tarde,rdbSalud_bucal_cuando_limpia_boca_noche,
				rdbSalud_bucal_como_supervisa_limpieza_le_limpia_dientes,rdbSalud_bucal_como_supervisa_limpieza_nino_solo,
				rdbSalud_bucal_utiliza_cepillo,rdbSalud_bucal_utiliza_crema,rdbSalud_bucal_utiliza_seda,rdbSalud_bucal_chupo_biberon,dtxSalud_bucal_ultima_consulta_odontologo
		};
		
		Component[] componentes_cuadro8 = {
				rdbCrecimiento_emanciacion_visible,tbxCrecimiento_pe_de,rdbCrecimiento_edema_ambos,
				tbxCrecimiento_apariencia,tbxCrecimiento_imc_edad,tbxCrecimiento_imc_edad_de,
				tbxCrecimiento_talla_edad_de,tbxCrecimiento_peso_talla_de,lbxCrecimiento_tendencia_peso,
				dbxT_e_de, dbxP_e_de, dbxP_t_de, dbxPC_e_de, dbxImc_e_de, dbxSignos_vitales_imc, btnCalcularPesoEdad, btnCalcularPerimetroCefalicoEdad, btnCalcularPesoTalla, btnCalcularTallaEdad, btnCalcularImcEdad
		};
		
		Component[] componentes_cuadro9 = {
				rdbAnemia_hierro_ultimos_6meses,tbxAnemia_hierro_ultimos_6meses_cuanto_tiempo,
				lbxAnemia_palidez_palmar,lbxAnemia_palidez_conjutival
		};

		Component[] componentes_cuadro10 = {
				tbxMaltrato_como_se_produjeron_lesiones,rdbMaltrato_nino_relata_maltrato,chbMaltrato_nino_relata_maltrato_cual_fisico,
				chbMaltrato_nino_relata_maltrato_cual_sexual,chbMaltrato_nino_relata_maltrato_cual_negligencia,
				tbxMaltrato_nino_relata_maltrato_quien,rdbMaltrato_incongruencia_al_explicar_un_trauma_significante,
				rdbMaltrato_incongruencia_lesion_edad_desarrollo_del_nino,rdbMaltrato_hay_diferentes_versiones,
				rdbMaltrato_es_tardia_la_consulta,tbxMaltrato_frecuencia_pegarle_a_su_hijo,
				tbxMaltrato_desobediente_es_su_hijo_obligado_a_pegarle,lbxMaltrato_comportamiento_anormal_padres,
				rdbMaltrato_agresividad_consulta,rdbMaltrato_descuidado_nino_salud,tbxMaltrato_descuidado_nino_salud_por,
				rdbMaltrato_descuidado_nino_higiene,rdbMaltrato_descuidado_nino_proteccion,rdbMaltrato_descuidado_nino_alimentacion,
				rdbMaltrato_descuidado_nino_de_la_calle,tbxMaltrato_factor_riesgo,tbxMaltrato_hiperactivo,rdbMaltrato_actividad_anormal_nino,
				lbxMaltrato_lesiones_craneo,lbxMaltrato_quemaduras,rdbMaltrato_equimosis,rdbMaltrato_hematomas,rdbMaltrato_laceraciones,
				rdbMaltrato_mordiscos,rdbMaltrato_cicatrices_lejos_prominencia_oseo_con_patron,
				rdbMaltrato_diferente_evolucion_en_ninos_que_no_deambulan,rdbMaltrato_sugestivas_de_maltrato,lbxMaltrato_fracturas,
				rdbMaltrato_trauma_viceral,rdbMaltrato_trauma_grave,tbxMaltrato_lesion_fisica_sugestiva,
				rdbMaltrato_sangrado_vaginal_o_analtraumatico,lbxMaltrato_trauma_genital,rdbMaltrato_juego_contenido_sexual,
				rdbMaltrato_boca_en_genitales,rdbMaltrato_vih,rdbMaltrato_gonorrea,rdbMaltrato_sifilis,rdbMaltrato_trichomona_vaginalis,
				rdbMaltrato_clamidia_trachomatis,chkMaltrato_temeroso,chkMaltrato_retraido,chkMaltrato_rechazo_adulto,chkMaltrato_deprimido,chkMaltrato_evita_contacto_visual,
				chkMaltrato_trastorno_sueno,chkMaltrato_trastorno_alimentario,chkMaltrato_problemas_psicosomaticos,chkMaltrato_conductas_agresivas,
				chkMaltrato_desarrollo_estancado,chkMaltrato_violencia_intrafamiliar,chkMaltrato_familia_caotica,chkMaltrato_cuidadores_adictos
		};
		
		eventoCuadro(componentes_cuadro1,1);
		eventoCuadro(componentes_cuadro2,2);
		eventoCuadro(componentes_cuadro3,3);
		eventoCuadro(componentes_cuadro4,4);
		eventoCuadro(componentes_cuadro5,5);
		eventoCuadro(componentes_cuadro6,6);
		eventoCuadro(componentes_cuadro7,7);
		eventoCuadro(componentes_cuadro8,8);
		eventoCuadro(componentes_cuadro9,9);
		eventoCuadro(componentes_cuadro10,10);
		
		cargarCuadro(componentes_cuadro1,1);
		cargarCuadro(componentes_cuadro2,2);
		cargarCuadro(componentes_cuadro3,3);
		cargarCuadro(componentes_cuadro4,4);
		cargarCuadro(componentes_cuadro5,5);
		cargarCuadro(componentes_cuadro6,6);
		cargarCuadro(componentes_cuadro7,7);
		cargarCuadro(componentes_cuadro8,8);
		cargarCuadro(componentes_cuadro9,9);
		cargarCuadro(componentes_cuadro10,10);
	}
	
	private void animarBoton(Button boton, Boolean deshabilitar){
		Integer tiempo_efecto = 300;
		Integer tiempo_wait = 50;
		if(!deshabilitar && boton.isDisabled()){
			boton.setWidgetListener("onBind", "jq(this).fadeIn("+tiempo_efecto+").delay("+tiempo_wait+").fadeOut("+tiempo_efecto+").fadeIn("+tiempo_efecto+").delay("+tiempo_wait+").fadeOut("+tiempo_efecto+").fadeIn("+tiempo_efecto+")");
			boton.setDisabled(false);
		}else{
			if(!boton.isDisabled() && deshabilitar){
				boton.setWidgetListener("onBind", "jq(this).fadeOut("+tiempo_efecto+").delay("+tiempo_wait+").fadeIn("+tiempo_efecto+").fadeOut("+tiempo_efecto+").delay("+tiempo_wait+").fadeIn("+tiempo_efecto+")");
				boton.setDisabled(true);
			}
		}
	}
	
	private void cargarCuadro(final Component[] componentes,final  Integer cuadro) {
		for (Component component : componentes) {
			if (component instanceof Checkbox) {
				final Checkbox ch = (Checkbox) component;
								algoritmoSwitcher(componentes, ch,cuadro);
			} else if (component instanceof Textbox) {
				final Textbox tbx = (Textbox) component;
								algoritmoSwitcher(componentes, tbx,cuadro);
			} else if (component instanceof Doublebox) {
				final Doublebox dbx = (Doublebox) component;
								algoritmoSwitcher(componentes, dbx,cuadro);
			}else if (component instanceof Intbox) {
				final Intbox ibx = (Intbox) component;
								algoritmoSwitcher(componentes, ibx,cuadro);
			} else if (component instanceof Datebox) {
				final Datebox dtx = (Datebox) component;
								algoritmoSwitcher(componentes, dtx,cuadro);
			} else if (component instanceof Radiogroup) {
				final Radiogroup rdg = (Radiogroup) component;
								algoritmoSwitcher(componentes, rdg,cuadro);
			}else if (component instanceof Listbox) {
				final Listbox lbx = (Listbox) component;
								algoritmoSwitcher(componentes, lbx,cuadro);
			}else if (component instanceof Button) {
				final Button btn = (Button) component;
								algoritmoSwitcher(componentes, btn,cuadro);
			}
		}
	}
	
	private void eventoCuadro(final Component[] componentes,final  Integer cuadro) {
		for (Component component : componentes) {
			if (component instanceof Checkbox) {
				final Checkbox ch = (Checkbox) component;
				ch.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, ch,cuadro);
							}
						});
			} else if (component instanceof Textbox) {
				final Textbox tbx = (Textbox) component;
				tbx.addEventListener(Events.ON_CHANGE,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, tbx,cuadro);
							}
						});
			} else if (component instanceof Doublebox) {
				final Doublebox dbx = (Doublebox) component;
				dbx.addEventListener(Events.ON_CHANGE,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, dbx,cuadro);
							}
						});
			}else if (component instanceof Intbox) {
				final Intbox ibx = (Intbox) component;
				ibx.addEventListener(Events.ON_CHANGE,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, ibx,cuadro);
							}
						});
			} else if (component instanceof Datebox) {
				final Datebox dtx = (Datebox) component;
				dtx.addEventListener(Events.ON_CHANGE,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, dtx,cuadro);
							}
						});
			} else if (component instanceof Radiogroup) {
				final Radiogroup rdg = (Radiogroup) component;
				rdg.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, rdg,cuadro);
							}
						});
			}else if (component instanceof Listbox) {
				final Listbox lbx = (Listbox) component;
				lbx.addEventListener(Events.ON_SELECT,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, lbx,cuadro);
							}
						});
			}else if (component instanceof Button) {
				final Button btn = (Button) component;
				btn.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								algoritmoSwitcher(componentes, btn,cuadro);
							}
						});
			}
		}
	}
	
	private void algoritmoSwitcher(Component[] componentes, Component seleccionado, Integer cuadro){
		switch(cuadro){
		case 1:
			algoritmoCuadro1(componentes, seleccionado);
			break;
		case 2:
			algoritmoCuadro2(componentes, seleccionado);
			break;
		case 3:
			algoritmoCuadro3(componentes, seleccionado);
			break;
		case 4:
			algoritmoCuadro4(componentes, seleccionado);
			break;
		case 5:
			algoritmoCuadro5(componentes, seleccionado);
			break;
		case 6:
			algoritmoCuadro6(componentes, seleccionado);
			break;
		case 7:
			algoritmoCuadro7(componentes, seleccionado);
			break;
		case 8:
			algoritmoCuadro8(componentes, seleccionado);
			break;
		case 9:
			algoritmoCuadro9(componentes, seleccionado);
			break;
		case 10:
			algoritmoCuadro10(componentes, seleccionado);
			break;
		}
	}
	
	private boolean signo_general = false;
	private void algoritmoCuadro1(Component[] componentes,Component component){
		Integer contador = 0;
		for (Component componente : componentes) {
			Checkbox chk = (Checkbox) componente;
			if(chk.isChecked()){
				contador++;
			}
		}
		signo_general = !(contador==0);
		animarBoton(btnCuadro1_boton1, (contador==0));
	}
	
	private void algoritmoCuadro2(Component[] componentes,Component component){
		if(!dbxSignos_vitales_fr.getText().isEmpty()){
			Double resp = dbxSignos_vitales_fr.getValue(); 
			ibxTos_resppm.setValue(resp.intValue());
		}
		
		if(!ibxTos_resppm.getText().isEmpty()){
			if((edad_meses<2 && ibxTos_resppm.getValue()<=60)||((edad_meses>=2 && edad_meses<12) && ibxTos_resppm.getValue()<=50)||((edad_meses>=12) && ibxTos_resppm.getValue()<=40)){
				rdbTos_respiracion_rapida.setSelectedIndex(1);
			}else{
				rdbTos_respiracion_rapida.setSelectedIndex(0);
			}
		}
		
		//Verifica oximetria
		List<Radio> radios = rdbTos_sat_oxigeno.getItems();
		Boolean tiene_oximetria = !dbxSignos_vitales_oximetria.getText().isEmpty();
		for (Radio radio : radios) {
			radio.setDisabled(tiene_oximetria);
		}
		
		if(tiene_oximetria){
			if(dbxSignos_vitales_oximetria.getValue()<92d){
				rdbTos_sat_oxigeno.setSelectedIndex(1);
			}else{
				rdbTos_sat_oxigeno.setSelectedIndex(0);
			}
		}
		
		Boolean crup_grave =rdbTos_estridor.getSelectedIndex()==0; 
		crup_grave = crup_grave && 
				((rdbTos_somnoliento.getSelectedIndex()==0 ||
				rdbTos_confuso.getSelectedIndex()==0 ||
				rdbTos_agitado.getSelectedIndex()==0) ||
				(rdbTos_tiraje_subcostal.getSelectedIndex()==0 ||
				rdbTos_tiraje_supraclavicular.getSelectedIndex()==0)||
				rdbTos_sat_oxigeno.getSelectedIndex()==0 ||
				edad_meses < 3	);
		
		Boolean crup =rdbTos_estridor.getSelectedIndex()==0;
		crup = crup && 
				(
						(rdbTos_incapacidad_hablar_beber.getSelectedIndex()==1 &&
						rdbTos_somnoliento.getSelectedIndex()==1 &&
						rdbTos_confuso.getSelectedIndex()==1 &&
						rdbTos_agitado.getSelectedIndex()==1) &&
						(rdbTos_tiraje_subcostal.getSelectedIndex()==1 &&
								rdbTos_tiraje_supraclavicular.getSelectedIndex()==1)&&
						rdbTos_sat_oxigeno.getSelectedIndex()==0 && edad_meses>3);

		if(crup_grave){
			animarBoton(btnCuadro2_boton1, !crup_grave);
			animarBoton(btnCuadro2_boton3, false);
		}		
		
		Boolean bronquiolitis_grave = edad_meses<24 && rdbTos_primer_sibilancia.getSelectedIndex()==0  && rdbTos_cuadro_gripal_3dias.getSelectedIndex()==0; 
		bronquiolitis_grave = bronquiolitis_grave && 
				(rdbTos_tiraje_subcostal.getSelectedIndex()==0 ||
				rdbTos_apnea.getSelectedIndex()==0 ||
				rdbTos_sat_oxigeno.getSelectedIndex()==0||
				rdbTos_respiracion_rapida.getSelectedIndex()==0||
				edad_meses <3||(edad_meses <6 && rdbTos_antecedentes_prematuridad.getSelectedIndex()==0));
		
		
		Boolean bronquiolitis = edad_meses<24 && rdbTos_primer_sibilancia.getSelectedIndex()==0  && rdbTos_cuadro_gripal_3dias.getSelectedIndex()==0; 
		bronquiolitis = bronquiolitis && 
				(rdbTos_tiraje_subcostal.getSelectedIndex()==1 &&
				rdbTos_apnea.getSelectedIndex()==1 &&
				rdbTos_sat_oxigeno.getSelectedIndex()==1 &&
				rdbTos_respiracion_rapida.getSelectedIndex()==1&&
				edad_meses >3 &&
				 rdbTos_antecedentes_prematuridad.getSelectedIndex()==1
				 &&  ( rdbTos_antecedentes_prematuridad.getSelectedIndex()==0 && edad_meses>6)
				 );
		
		if(bronquiolitis_grave){
			animarBoton(btnCuadro2_boton2, !bronquiolitis_grave);
			animarBoton(btnCuadro2_boton4, bronquiolitis);
		}else{
			animarBoton(btnCuadro2_boton4, !bronquiolitis);	
		}
		
		
//		Boolean sibilancia_grave = (edad_meses>=24 && rdbTos_sibilancias.getSelectedIndex()==0)  || rdbTos_sibilancia_recurrente.getSelectedIndex()==0 || !bronquiolitis_grave; 
//		sibilancia_grave = sibilancia_grave && 
//				(rdbTos_incapacidad_hablar_beber.getSelectedIndex()==0 ||
//						(rdbTos_somnoliento.getSelectedIndex()==0 ||
//						rdbTos_confuso.getSelectedIndex()==0 ||
//						rdbTos_agitado.getSelectedIndex()==0) ||
//						(rdbTos_tiraje_subcostal.getSelectedIndex()==0||rdbTos_respiracion_rapida.getSelectedIndex()==0)||
//						rdbTos_sat_oxigeno.getSelectedIndex()==0);
//		animarBoton(btnCuadro2_boton3, !sibilancia_grave);
		
		Boolean sibilancia_recurrente =((edad_meses>=24 && rdbTos_sibilancias.getSelectedIndex()==0)  || rdbTos_sibilancia_recurrente.getSelectedIndex()==0) && !crup  && !bronquiolitis && !bronquiolitis_grave && !crup_grave; 
		sibilancia_recurrente = sibilancia_recurrente && 
				(rdbTos_incapacidad_hablar_beber.getSelectedIndex()==0 &&
						(rdbTos_somnoliento.getSelectedIndex()==0 &&
						rdbTos_confuso.getSelectedIndex()==0 &&
						rdbTos_agitado.getSelectedIndex()==0) &&
						rdbTos_sat_oxigeno.getSelectedIndex()==0);
		animarBoton(btnCuadro2_boton5, !sibilancia_recurrente);
		
		Boolean neumonia_grave =  
				(signo_general  || 
						rdbTos_tiraje_subcostal.getSelectedIndex()==0 ||
						rdbTos_sat_oxigeno.getSelectedIndex()==0)	;		
		animarBoton(btnCuadro2_boton6, !neumonia_grave);
		
		Boolean neumonia =  
				(rdbTos_respiracion_rapida.getSelectedIndex()==0);		
		animarBoton(btnCuadro2_boton7, !neumonia);

		Boolean tos_resfriado =  
				(rdbTiene_tos_o_dificultad_para_respirar.getSelectedIndex()==0 &&   !crup  && !bronquiolitis && !bronquiolitis_grave && !crup_grave  && !neumonia && !neumonia_grave && !sibilancia_recurrente)	;		
		animarBoton(btnCuadro2_boton8, !tos_resfriado);
	}
	
	private void algoritmoCuadro3(Component[] componentes,Component component){
		Integer contador = 0;
		
		if(rdbDiarrea_tiene_vomito.getSelectedIndex()==0){
			
		}
		
		if(rdbDiarrea_letargico_comatoso.getSelectedIndex()==0 ){
			contador ++;
		}
		if(rdbDiarrea_ojos_hundidos.getSelectedIndex()==0 ){
			contador ++;
		}
		if(rdbDiarrea_bebe_mal_no_puede.getSelectedIndex()==0 ){
			contador ++;
		}
		if(rdbDiarrea_pliegue_cutaneo.getSelectedIndex()!=0 ){
			contador ++;
		}
		Boolean diarrea_deshidratacion_grave =(contador>=2);
		animarBoton(btnCuadro3_boton1, !diarrea_deshidratacion_grave);
		
		contador = 0;
		if(rdbDiarrea_intranquilo_irritable.getSelectedIndex()==0){
			contador ++;
		}
		if(rdbDiarrea_ojos_hundidos.getSelectedIndex()==0 ){
			contador ++;
		}
		if(rdbDiarrea_bebe_abidamente_con_sed.getSelectedIndex()==0 ){
			contador ++;
		}
		if(rdbDiarrea_pliegue_cutaneo.getSelectedIndex()!=0 ){
			contador ++;
		}
		Boolean diarrea_deshidratacion_grado =(contador>=2);
		animarBoton(btnCuadro3_boton2, !diarrea_deshidratacion_grado);
		
		Boolean diarrea_deshidratacion_alto_riesgo =	(!ibxDiarrea_cant_diarreas_24h.getText().isEmpty() && ibxDiarrea_cant_diarreas_24h.getValue()>8);
		
		diarrea_deshidratacion_alto_riesgo = diarrea_deshidratacion_alto_riesgo ||
		(!ibxDiarrea_cant_vomitos_24h.getText().isEmpty() && ibxDiarrea_cant_vomitos_24h.getValue()>8);
		//TODO: Completar cuadro 3 boton 3 segun los criterios medicos
		animarBoton(btnCuadro3_boton3, !diarrea_deshidratacion_alto_riesgo);
		
		Boolean diarrea_sin_deshidratacion = 	(!diarrea_deshidratacion_grave && !diarrea_deshidratacion_grado && !diarrea_deshidratacion_alto_riesgo) ;
		animarBoton(btnCuadro3_boton4, !diarrea_sin_deshidratacion);
		
		Boolean diarrea_persistente_grave =(!ibxDiarrea_dias.getText().isEmpty() && ibxDiarrea_dias.getValue()>13) ;
		diarrea_persistente_grave = diarrea_persistente_grave && (!diarrea_sin_deshidratacion || edad_meses < 6);
		animarBoton(btnCuadro3_boton5, !diarrea_persistente_grave);
		
		Boolean diarrea_persistente =(!ibxDiarrea_dias.getText().isEmpty() && ibxDiarrea_dias.getValue()>13);
		diarrea_persistente = diarrea_persistente &&( diarrea_sin_deshidratacion && edad_meses > 6);
		animarBoton(btnCuadro3_boton6, !diarrea_persistente);
		
		Boolean disenteria = (!ibxDiarrea_dias.getText().isEmpty() && ibxDiarrea_dias.getValue()>13)  && rdbTos_respiracion_rapida.getSelectedIndex()==0;
		animarBoton(btnCuadro3_boton7, !disenteria);
	}
	
	private void algoritmoCuadro4(Component[] componentes,Component component){		
		if(!dbxSignos_vitales_taxilar.getText().isEmpty()){
			Double temp =dbxSignos_vitales_taxilar.getValue();
			for (Radio radio : rdbFiebre_mayor5_todos_los_dias.getItems()) {
					radio.setDisabled(!(!ibxFiebre_dias.getText().isEmpty() && ibxFiebre_dias.getValue()>5));
			}
				
			if(temp>38){
				rdbFiebre_mayor_38.setSelectedIndex(0);
			}else{
				rdbFiebre_mayor_38.setSelectedIndex(1);
			}
			if(temp>39){
				rdbFiebre_mayor_39.setSelectedIndex(0);
			}else{
				rdbFiebre_mayor_39.setSelectedIndex(1);
			}
				
			Boolean febril_alto = edad_meses < 3 && temp >=38;
			febril_alto = febril_alto || ((edad_meses >3 && edad_meses < 6) && temp>=39) ||
				signo_general || rdbFiebre_rigidez_de_nuca.getSelectedIndex()==0 ||
				rdbFiebre_apariencia_enfermo_grave.getSelectedIndex()==0 ||
				rdbFiebre_respuesta_social.getSelectedIndex()==0 ||
				rdbFiebre_piel.getSelectedIndex()==0 ||
				rdbFiebre_manifestaciones_sangrado.getSelectedIndex()==0;
				//TODO: rash y manifestaciones focales?
			animarBoton(btnCuadro4_boton1, !febril_alto);
			
			Boolean febril_medio = (ibxFiebre_dias.getText().isEmpty()?false:ibxFiebre_dias.getValue()>=5);
			febril_medio = febril_medio || ((edad_meses >=6 && edad_meses < 24) && temp>=39) ||
				rdbFiebre_respuesta_social.getSelectedIndex()==0;
			animarBoton(btnCuadro4_boton2, !febril_medio);
			
			Boolean febril_bajo = !febril_alto && !febril_medio;
			animarBoton(btnCuadro4_boton3, !febril_bajo);
			
			Boolean malaria_complicada = lbxFiebre_zona_malaria.getSelectedIndex()!=0;
			malaria_complicada = malaria_complicada &&  febril_alto;
			animarBoton(btnCuadro4_boton4, !malaria_complicada);
			
			Boolean malaria = lbxFiebre_zona_malaria.getSelectedIndex()!=0;
			//TODO: Fiebre sin causa?
			animarBoton(btnCuadro4_boton5, !malaria);

			Integer cant_sintomas = 0;
			if(rdbFiebre_cefaleas.getSelectedIndex() == 0){
				cant_sintomas ++;
			}
			if(rdbFiebre_dolor_retroociular.getSelectedIndex() == 0){
				cant_sintomas ++;
			}
			if(rdbFiebre_mialgias.getSelectedIndex() == 0){
				cant_sintomas ++;
			}
			if(rdbFiebre_altralgias.getSelectedIndex() == 0){
				cant_sintomas ++;
			}
			if(rdbFiebre_postracion.getSelectedIndex() == 0){
				cant_sintomas ++;
			}
			//TODO: falta exantema o hemograma sugestivo de enfermedad viral (Verificar todo dengue general)
//			Boolean dengue_general = lbxFiebre_vive_visito_15_dias_zona_dengue.getSelectedIndex()!=0 && temp>= 39
//					 && cant_sintomas >=2;
//			Boolean dengue_grave = dengue_general && 
					
			//TODO: OJO VERIFICAR DENGUE
		}
		
		
	}
	private void algoritmoCuadro5(Component[] componentes,Component component){
		
		if(rdbOido_supuracion.getSelectedIndex()==0){
			lbOido_supuracion_dias.setVisible(true);
			ibxOido_supuracion_dias.setVisible(true);
		}else{
			lbOido_supuracion_dias.setVisible(false);
			ibxOido_supuracion_dias.setVisible(false);
		}

		Boolean mastoiditis = (rdbOido_tumefaccion_dolorosa_detras_oreja.getSelectedIndex()==0);
		animarBoton(btnCuadro5_boton1, !mastoiditis);
		
		Boolean otitis_media_cronica =false;
		if(rdbOido_supuracion.getSelectedIndex()==0){
			otitis_media_cronica = ( !ibxOido_supuracion_dias.getText().isEmpty() && ibxOido_supuracion_dias.getValue()>=14);
		}		
		animarBoton(btnCuadro5_boton2, !otitis_media_cronica);
		
		Boolean otitis_media_recurrente =(
				((!ibxOido_episodios_previos.getText().isEmpty() && !ibxOido_episodios_previos_meses.getText().isEmpty()) && ibxOido_episodios_previos.getValue() >= 3 && ibxOido_episodios_previos.getValue() <= 6) ||
				((!ibxOido_episodios_previos.getText().isEmpty() && !ibxOido_episodios_previos_meses.getText().isEmpty()) && ibxOido_episodios_previos.getValue() == 4 && ibxOido_episodios_previos.getValue() <= 12)
				);
		animarBoton(btnCuadro5_boton3, !otitis_media_recurrente);
		
		Boolean otitis_media_aguda =(rdbOido_timpano_rojo_abombado.getSelectedIndex()==0) || (rdbOido_dolor.getSelectedIndex() == 0) || ( !ibxOido_supuracion_dias.getText().isEmpty() && ibxOido_supuracion_dias.getValue()<14);
		animarBoton(btnCuadro5_boton4, !otitis_media_aguda);
		
		Boolean no_tiene_otitis = !mastoiditis &&  !otitis_media_cronica &&  !otitis_media_aguda;
		animarBoton(btnCuadro5_boton5, !no_tiene_otitis);
	}
	private void algoritmoCuadro6(Component[] componentes,Component component){
		boolean faringoamigdalitis_estreptococica = (edad_meses >= 36 && rdbTiene_fiebre.getSelectedIndex()==0) &&
				rdbGarganta_exudado_blanquecino_amarillento_amigdalas.getSelectedIndex()==0 && rdbGarganta_amialgias_eritematosas.getSelectedIndex() ==0 && rdbGarganta_ganglios_cuello_crecidos_dolorosos.getSelectedIndex()==0;
		animarBoton(btnCuadro6_boton1, !faringoamigdalitis_estreptococica);
		
		boolean faringoamigdalitis_viral = (edad_meses < 36 && rdbTiene_fiebre.getSelectedIndex()==1) &&
				rdbGarganta_exudado_blanquecino_amarillento_amigdalas.getSelectedIndex()==0 && rdbGarganta_amialgias_eritematosas.getSelectedIndex() ==0 && rdbGarganta_ganglios_cuello_crecidos_dolorosos.getSelectedIndex()==0;
		faringoamigdalitis_viral = faringoamigdalitis_viral || (rdbGarganta_amialgias_eritematosas.getSelectedIndex()==0 &&rdbGarganta_ganglios_cuello_crecidos_dolorosos.getSelectedIndex()==1);
		animarBoton(btnCuadro6_boton2, !faringoamigdalitis_viral);
		
		boolean no_tiene_faringoamigdalitis = (!faringoamigdalitis_estreptococica && !faringoamigdalitis_viral);
		animarBoton(btnCuadro6_boton3, !no_tiene_faringoamigdalitis);
	}
	
	private void algoritmoCuadro7(Component[] componentes,Component component){
		boolean celulitis_facial = rdbSalud_bucal_inflamacion_dolorosa_labio.getSelectedIndex() == 0;
		animarBoton(btnCuadro7_boton1, !celulitis_facial);
		
		lbSalud_bucal_no_involucra_surco.setVisible(rdbSalud_bucal_inflamacion_dolorosa_labio.getSelectedIndex()==0);
		rdbSalud_bucal_no_involucra_surco.setVisible(rdbSalud_bucal_inflamacion_dolorosa_labio.getSelectedIndex()==0);
		
		boolean enfermedad_bucal_grave = rdbSalud_bucal_inflamacion_dolorosa_labio.getSelectedIndex() == 0;
		enfermedad_bucal_grave = (enfermedad_bucal_grave && rdbSalud_bucal_no_involucra_surco.getSelectedIndex()==1) ||
				(lbxSalud_bucal_enrojecimiento_inflamacion_encia.getSelectedIndex()==1) ||
				(lbxSalud_bucal_enrojecimiento_inflamacion_encia.getSelectedIndex()==3) ||
				(rdbSalud_bucal_caries_cavitacionales.getSelectedIndex()==0)||
				(rdbSalud_bucal_dolor_dientes.getSelectedIndex()==0);
		animarBoton(btnCuadro7_boton2, !celulitis_facial);
		
		boolean trauma_bucodental = 
				lbxSalud_bucal_manchas.getSelectedIndex() !=0 ||
				rdbSalud_bucal_fractura.getSelectedIndex()==0 ||
				rdbSalud_bucal_mobilidad.getSelectedIndex()==0 ||
				rdbSalud_bucal_avulsion.getSelectedIndex() == 0 ||
				(lbxSalud_bucal_herida.getSelectedIndex()==2 ||lbxSalud_bucal_herida.getSelectedIndex()==1);
		animarBoton(btnCuadro7_boton3, !trauma_bucodental);
				
		boolean estomatitis = 
				(
				lbxSalud_bucal_vesiculas.getSelectedIndex() !=0 ||
				lbxSalud_bucal_ulceras.getSelectedIndex()!=0 ||
				lbxSalud_bucal_placas.getSelectedIndex()!=0 
				)||
				lbxSalud_bucal_enrojecimiento_inflamacion_encia.getSelectedIndex() == 1;
		animarBoton(btnCuadro7_boton4, !estomatitis);
		
		boolean enfermedad_dental_gingival =
				lbxSalud_bucal_enrojecimiento_inflamacion_encia.getSelectedIndex() == 1 || lbxSalud_bucal_manchas.getSelectedIndex() !=0;
		animarBoton(btnCuadro7_boton5, !enfermedad_dental_gingival);
			
		Calendar fecha_odontologo = Calendar.getInstance();
		fecha_odontologo.setTime(dtxSalud_bucal_ultima_consulta_odontologo.getValue());
		Calendar calendar_hoy = Calendar.getInstance();
		calendar_hoy.setTime(new Date());

		boolean odontologo_6_meses = false;
			
		odontologo_6_meses = Util.getDiferenciaEntreMeses(calendar_hoy.getTime(), fecha_odontologo.getTime())>6;
		
		boolean alto_riesgo_enfermedad_gingival = lbxSalud_bucal_placas.getSelectedIndex() != 0 ||
				( (rdbSalud_bucal_como_supervisa_limpieza_le_limpia_dientes.getSelectedIndex()==1)||odontologo_6_meses)||
				rdbSalud_bucal_chupo_biberon.getSelectedIndex()==0||
				rdbSalud_bucal_padres_hermanos_caries.getSelectedIndex() == 0;
		animarBoton(btnCuadro7_boton6, !alto_riesgo_enfermedad_gingival);	
		
		boolean bajo_riesgo_enfermedad_bucal = !celulitis_facial && !enfermedad_bucal_grave && !trauma_bucodental && !estomatitis && !enfermedad_dental_gingival && !alto_riesgo_enfermedad_gingival &&
		rdbSalud_bucal_como_supervisa_limpieza_le_limpia_dientes.getSelectedIndex()==0 && !odontologo_6_meses;
		animarBoton(btnCuadro7_boton7, !bajo_riesgo_enfermedad_bucal);	
	}
	
	private void algoritmoCuadro8(Component[] componentes,Component component){
		Double imc_de = 0d;
		DecimalFormat format = new DecimalFormat("#.##");
		
		if(!dbxImc_e_de.getText().isEmpty()){
			imc_de = dbxImc_e_de.getValue();
			tbxCrecimiento_imc_edad_de.setText(format.format(imc_de));
			String formato = format.format(dbxSignos_vitales_imc.getValue()); 
			tbxCrecimiento_imc_edad.setText(formato);
		}
		Double p_t_de = 0d;
		if(!dbxP_t_de.getText().isEmpty()){
			p_t_de = dbxP_t_de.getValue();
			String formato = format.format(p_t_de); 
			tbxCrecimiento_peso_talla_de.setText(formato);
		}
		Double p_e_de = 0d;
		if(!dbxP_e_de.getText().isEmpty()){
			p_e_de = dbxP_e_de.getValue();
			String formato = format.format(p_e_de); 
			tbxCrecimiento_pe_de.setText(formato);
		}
		Double t_e_de = 0d;
		if(!dbxT_e_de.getText().isEmpty()){
			t_e_de = dbxT_e_de.getValue();
			String formato = format.format(t_e_de); 
			tbxCrecimiento_talla_edad_de.setText(formato);
		}
		
		if(imc_de>=1 || p_t_de>=1){
			boolean obeso =(imc_de>=2) || (p_t_de>2);
			animarBoton(btnCuadro8_boton1, !obeso);
			boolean sobrepeso=(imc_de>=1 && imc_de<2 ) || (p_t_de<=2 && p_t_de>1 );
			animarBoton(btnCuadro8_boton2, !sobrepeso);	
		}else{
			boolean desnutricion_severa = 
					rdbCrecimiento_emanciacion_visible.getSelectedIndex()==0 ||
					rdbCrecimiento_edema_ambos.getSelectedIndex()==0 ||
					p_e_de <-3 ||
					p_t_de <-3;
			animarBoton(btnCuadro8_boton3, !desnutricion_severa);	
			
			boolean desnutricion= (p_t_de < -2  && p_t_de <= -3 ) || (p_e_de < -2  && p_e_de <= -3 ) || (t_e_de < -2 );
			animarBoton(btnCuadro8_boton4, !desnutricion);
			
			boolean riesgo_desnutricion =  (p_t_de < -2  && p_t_de < -1 ) || (p_e_de < -2  && p_e_de < -1 ) || (t_e_de < -2  && t_e_de < -1 ) || lbxCrecimiento_tendencia_peso.getSelectedIndex()>=1;
			animarBoton(btnCuadro8_boton5, !riesgo_desnutricion);
			
			boolean adecuado_estado_nutricional = (p_t_de >= -1  && p_t_de <= 1 ) && (p_e_de >= -1  && p_e_de <= 1 ) && (t_e_de >= -1 ) && imc_de<1 && lbxCrecimiento_tendencia_peso.getSelectedIndex()==0;
			animarBoton(btnCuadro8_boton6, !adecuado_estado_nutricional);
			
		}
	}
	private void algoritmoCuadro9(Component[] componentes,Component component){
		divAnemia_hierro.setVisible(rdbAnemia_hierro_ultimos_6meses.getSelectedIndex()==0);
		
		boolean anemia_severa = lbxAnemia_palidez_palmar.getSelectedIndex() == 1 || lbxAnemia_palidez_conjutival.getSelectedIndex() == 1 ;
		animarBoton(btnCuadro9_boton1, !anemia_severa);
		
		boolean anemia = lbxAnemia_palidez_palmar.getSelectedIndex() == 2 || lbxAnemia_palidez_conjutival.getSelectedIndex() == 2 ;
		animarBoton(btnCuadro9_boton2, !anemia);
	
		boolean no_tiene_anemia = lbxAnemia_palidez_palmar.getSelectedIndex() == 0 && lbxAnemia_palidez_conjutival.getSelectedIndex() == 0 ;
		animarBoton(btnCuadro9_boton3, !no_tiene_anemia);
	}
	private void algoritmoCuadro10(Component[] componentes,Component component){
		boolean relata_maltrato = rdbMaltrato_nino_relata_maltrato.getSelectedIndex()==0;

		divRelataMaltrato.setVisible(relata_maltrato);
		if(!relata_maltrato){
			chbMaltrato_nino_relata_maltrato_cual_fisico.setChecked(false);
			chbMaltrato_nino_relata_maltrato_cual_negligencia.setChecked(false);
			chbMaltrato_nino_relata_maltrato_cual_sexual.setChecked(false);
		}
		
		divDescuidoNinoSalud.setVisible( rdbMaltrato_descuidado_nino_salud.getSelectedIndex()==0);
		divDescuidoNinoHigiene.setVisible( rdbMaltrato_descuidado_nino_higiene.getSelectedIndex()==0);
		divDescuidoNinoProteccion.setVisible( rdbMaltrato_descuidado_nino_proteccion.getSelectedIndex()==0);
		divDescuidoNinoAlimentacion.setVisible( rdbMaltrato_descuidado_nino_alimentacion.getSelectedIndex()==0);
		divDescuidoNinoCalle.setVisible( rdbMaltrato_descuidado_nino_de_la_calle.getSelectedIndex()==0);
		
		boolean maltrato_fisico_muy_grave = rdbMaltrato_sugestivas_de_maltrato.getSelectedIndex()==0 ||
				lbxMaltrato_lesiones_craneo.getSelectedIndex()!=0||
				lbxMaltrato_quemaduras.getSelectedIndex()!=0||
				lbxMaltrato_fracturas.getSelectedIndex()!=0||
				rdbMaltrato_equimosis.getSelectedIndex()==0||
						rdbMaltrato_hematomas.getSelectedIndex()==0||
								rdbMaltrato_laceraciones.getSelectedIndex()==0||
										rdbMaltrato_mordiscos.getSelectedIndex()==0||
												rdbMaltrato_mordiscos.getSelectedIndex()==0||
												rdbMaltrato_incongruencia_lesion_edad_desarrollo_del_nino.getSelectedIndex()==0||
												rdbMaltrato_nino_relata_maltrato.getSelectedIndex() == 0;
			animarBoton(btnCuadro10_boton1, !maltrato_fisico_muy_grave);
			
			boolean abuso_sexual=rdbMaltrato_sangrado_vaginal_o_analtraumatico.getSelectedIndex()==0 ||
					lbxMaltrato_trauma_genital.getSelectedIndex()!=0||
					rdbMaltrato_gonorrea.getSelectedIndex()==0||rdbMaltrato_sifilis.getSelectedIndex()==0||rdbMaltrato_vih.getSelectedIndex()==0||rdbMaltrato_juego_contenido_sexual.getSelectedIndex()==0||chbMaltrato_nino_relata_maltrato_cual_sexual.isChecked();
			animarBoton(btnCuadro10_boton2, !abuso_sexual);
		
			//TODO:analizar detalladamente maltrato fisico
			boolean maltrato_fisico = chbMaltrato_nino_relata_maltrato_cual_fisico.isChecked() && lbxMaltrato_trauma_genital.getSelectedIndex()!=0;
			animarBoton(btnCuadro10_boton3, !maltrato_fisico);
			
			boolean sospecha_abuso_sexual = (rdbMaltrato_trichomona_vaginalis.getSelectedIndex()==0 ||rdbMaltrato_clamidia_trachomatis.getSelectedIndex()==0)||lbxMaltrato_trauma_genital.getSelectedIndex()==10||rdbMaltrato_descuidado_nino_de_la_calle.getSelectedIndex()==0||chkMaltrato_rechazo_adulto.isChecked();
			animarBoton(btnCuadro10_boton4, !sospecha_abuso_sexual);
			
			boolean maltrato_emocional_negligencia = chbMaltrato_nino_relata_maltrato_cual_negligencia.isChecked()||chkMaltrato_cuidadores_adictos.isChecked()||
					(chkMaltrato_temeroso.isChecked() || chkMaltrato_retraido.isChecked() || chkMaltrato_rechazo_adulto.isChecked() ||
					chkMaltrato_deprimido.isChecked() || chkMaltrato_evita_contacto_visual.isChecked() || chkMaltrato_trastorno_sueno.isChecked() ||
					chkMaltrato_trastorno_alimentario.isChecked() || chkMaltrato_problemas_psicosomaticos.isChecked() ||
					chkMaltrato_conductas_agresivas.isChecked() || chkMaltrato_desarrollo_estancado.isChecked() || chkMaltrato_violencia_intrafamiliar.isChecked())||
					rdbMaltrato_descuidado_nino_salud.getSelectedIndex()==0 || rdbMaltrato_descuidado_nino_higiene.getSelectedIndex()==0 || rdbMaltrato_descuidado_nino_proteccion.getSelectedIndex()==0 ||
					rdbMaltrato_descuidado_nino_alimentacion.getSelectedIndex()==0 ||rdbMaltrato_descuidado_nino_de_la_calle.getSelectedIndex()==0 ; 
			animarBoton(btnCuadro10_boton5, !maltrato_emocional_negligencia);
			
			boolean no_hay_sospecha_maltrato = !maltrato_fisico_muy_grave && !abuso_sexual && !maltrato_fisico && !sospecha_abuso_sexual && !maltrato_emocional_negligencia;
			animarBoton(btnCuadro10_boton6, !no_hay_sospecha_maltrato);
	}
	
	private void precargarSugernecias(){
		cargarSugerencias(btnCuadro1_boton1, "1", "1",40);
		
		cargarSugerencias(btnCuadro2_boton1, "2", "1",40);
		cargarSugerencias(btnCuadro2_boton2, "2", "2",40);
		//El boton 3 del formato no coincide con la guia aiepi
		cargarSugerencias(btnCuadro2_boton3, "2", "4",40);
		cargarSugerencias(btnCuadro2_boton4, "2", "5",40);
		cargarSugerencias(btnCuadro2_boton5, "2", "6",40);
		cargarSugerencias(btnCuadro2_boton6, "2", "7",40);
		cargarSugerencias(btnCuadro2_boton7, "2", "8",40);
		cargarSugerencias(btnCuadro2_boton8, "2", "9",40);
		
		cargarSugerencias(btnCuadro3_boton1, "3", "1",40);
		cargarSugerencias(btnCuadro3_boton2, "3", "2",40);
		cargarSugerencias(btnCuadro3_boton3, "3", "3",40);
		cargarSugerencias(btnCuadro3_boton4, "3", "4",40);
		cargarSugerencias(btnCuadro3_boton5, "3", "5",40);
		cargarSugerencias(btnCuadro3_boton6, "3", "6",40);
		cargarSugerencias(btnCuadro3_boton7, "3", "7",40);
		
		cargarSugerencias(btnCuadro4_boton1, "4", "1",40);
		cargarSugerencias(btnCuadro4_boton2, "4", "2",40);
		cargarSugerencias(btnCuadro4_boton3, "4", "3",40);
		cargarSugerencias(btnCuadro4_boton4, "4", "4",40);
		cargarSugerencias(btnCuadro4_boton5, "4", "5",40);
		cargarSugerencias(btnCuadro4_boton6, "4", "6",40);
		cargarSugerencias(btnCuadro4_boton7, "4", "7",40);
		cargarSugerencias(btnCuadro4_boton8, "4", "8",40);
		
		cargarSugerencias(btnCuadro5_boton1, "5", "1",40);
		cargarSugerencias(btnCuadro5_boton2, "5", "2",40);
		cargarSugerencias(btnCuadro5_boton3, "5", "3",40);
		cargarSugerencias(btnCuadro5_boton4, "5", "4",40);
		cargarSugerencias(btnCuadro5_boton5, "5", "5",40);
		
		cargarSugerencias(btnCuadro6_boton1, "6", "2",40);
		cargarSugerencias(btnCuadro6_boton2, "6", "3",40);
		cargarSugerencias(btnCuadro6_boton3, "6", "3",40);
		
		cargarSugerencias(btnCuadro7_boton1, "7", "1",40);
		cargarSugerencias(btnCuadro7_boton2, "7", "2",40);
		cargarSugerencias(btnCuadro7_boton3, "7", "3",40);
		cargarSugerencias(btnCuadro7_boton4, "7", "4",40);
		cargarSugerencias(btnCuadro7_boton5, "7", "5",40);
		cargarSugerencias(btnCuadro7_boton6, "7", "6",40);
		cargarSugerencias(btnCuadro7_boton7, "7", "7",40);
		
		cargarSugerencias(btnCuadro8_boton1, "8", "1",40);
		cargarSugerencias(btnCuadro8_boton2, "8", "2",40);
		cargarSugerencias(btnCuadro8_boton3, "8", "3",40);
		cargarSugerencias(btnCuadro8_boton4, "8", "4",40);
		cargarSugerencias(btnCuadro8_boton5, "8", "5",40);
		cargarSugerencias(btnCuadro8_boton6, "8", "6",40);
		
		cargarSugerencias(btnCuadro9_boton1, "9", "1",40);
		cargarSugerencias(btnCuadro9_boton2, "9", "2",40);
		cargarSugerencias(btnCuadro9_boton3, "9", "3",40);
		
		cargarSugerencias(btnCuadro10_boton1, "10", "1",80);
		cargarSugerencias(btnCuadro10_boton2, "10", "2",80);
		cargarSugerencias(btnCuadro10_boton3, "10", "3",80);
		cargarSugerencias(btnCuadro10_boton4, "10", "4",80);
		cargarSugerencias(btnCuadro10_boton5, "10", "5",80);
		cargarSugerencias(btnCuadro10_boton6, "10", "6",80);
	}
	
	private void cargarSugerencias(Button btn, String cuadro, String estado, Integer largo_boton){
		final Grid gridSugerencias = new Grid();
		
		Cuadros_aiepi_estado estd = new Cuadros_aiepi_estado();
		estd.setCodigo_empresa(codigo_empresa);
		estd.setCodigo_sucursal(codigo_sucursal);
		estd.setCuadro(cuadro);
		estd.setEstado(estado);
		estd.setVia_ingreso( IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
		final Cuadros_aiepi_estado est = getServiceLocator().getServicio(Cuadros_aiepi_estadoService.class).consultar(estd);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("codigo_empresa", codigo_empresa);
		param.put("codigo_sucursal", codigo_sucursal);
		param.put("via_ingreso", IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
		param.put("cuadro", cuadro);
		param.put("estado",estado);
		
		List<Cuadros_aiepi_sugerencias> sugerencias = getServiceLocator().getServicio(Cuadros_aiepi_sugerenciasService.class).listar(param);
		Columns cs = new Columns();
		Column c1 = new Column();
		c1.setWidth("30px");
		cs.appendChild(c1);
		Rows rs = new Rows();
		for (Cuadros_aiepi_sugerencias sugerencia : sugerencias) {
			Row r1 = new Row();
			Cell cell0 = new Cell();
			cell0.setStyle("background:"+est.getColor_fondo());
			Radio rd = new Radio();
			rd.setSelected(true);
			rd.setDisabled(true);
			cell0.appendChild(rd);
			Cell cell1 = new Cell();
			final Label lbl = new Label(sugerencia.getSugerencia());
			lbl.setMultiline(true);
			lbl.setStyle("font-weight:bold");
			final Cuadros_aiepi_sugerencias sug = sugerencia;
			
			//Muestra como "link" o label dependiendo del enlace
			if(sug.getEnlace()!=null && !sug.getEnlace().isEmpty()){
				lbl.setStyle("font-weight:bold;color:blue");
				lbl.addEventListener(Events.ON_MOUSE_OVER, new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						lbl.setStyle("font-weight:bold;color:blue;text-decoration:underline;cursor:pointer");
					}
				});
				lbl.addEventListener(Events.ON_MOUSE_OUT, new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						lbl.setStyle("font-weight:bold;color:blue;cursor: default");
					}
				});
				lbl.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						CuadroAIEPIMacro.mostrarVentana(new Image(sug.getEnlace()), getPage(), " ", 0,0,0);
					}
				});	
			}
			
			cell1.appendChild(lbl);
			cell1.setStyle("background:"+est.getColor_fondo());
			r1.appendChild(cell0);
			r1.appendChild(cell1);
			rs.appendChild(r1);
		}
		gridSugerencias.appendChild(cs);
		gridSugerencias.appendChild(rs);
		gridSugerencias.setMold("paging");
		gridSugerencias.setPageSize(9);
		gridSugerencias.applyProperties();
		gridSugerencias.invalidate();
								
		//Fin carga Sugerencias
		btn.setHeight(largo_boton+"px");
		btn.setStyle("font-weight:bold;background:"+est.getColor_fondo());
		btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				CuadroAIEPIMacro.mostrarVentana(gridSugerencias, getPage(), "SUGERENCIAS: "+est.getDescripcion(),400,0,0);
			}
		});	
	}
}