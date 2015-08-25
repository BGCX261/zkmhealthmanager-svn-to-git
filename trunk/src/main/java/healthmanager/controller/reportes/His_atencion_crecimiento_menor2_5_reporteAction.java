/*
 * his_atencion_crecimiento_menor2_5Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Escala_del_desarrollo;
import healthmanager.modelo.bean.His_atencion_crecimiento_menor2_5;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Escala_del_desarrolloService;
import healthmanager.modelo.service.His_atencion_crecimiento_menor2_5Service;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
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
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.EscalaAbreviadaDesarrollo;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculadorUtil;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class His_atencion_crecimiento_menor2_5_reporteAction extends
		HistoriaClinicaModel implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(His_atencion_crecimiento_menor2_5_reporteAction.class);

	// Componentes //

	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;

	@View
	private Row rowAgudeza_visual;
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

	private His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5;
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
	private Label lbxMotivo_consulta;
	@View
	private Label lbxEnfermedad_actual;
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
	private Label lbxOtros_y_observaciones;
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
	private Label lbxComplicaciones_embarazo_parto;
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
	private Label lbxObservaciones1;
	@View
	private Label lbxPatologicos_medicos;
	@View
	private Label lbxPatologicos_quirurgicos;
	@View
	private Label lbxPatologicos_hospitalizaciones;
	@View
	private Label lbxPatologicos_medicacion;
	// ------------- Jose
	
		@View
		private Label lbxPatologicos_alergicos;
		
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
	private Label lbxObservaciones_hab_aliment;
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
	private Label lbxPaternos_medicos;
	@View
	private Textbox tbxPaternos_alergicos;
	@View
	private Doublebox dbxPaternos_talla;
	@View
	private Label lbxMaternos_medicos;
	@View
	private Textbox tbxMaternos_alergicos;
	@View
	private Doublebox dbxMaternos_talla;
	@View
	private Label lbxOtros;
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
	private Label lbxObservacioness;
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
	private Label lbxAnalisis;
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
	private Label lbxDescripciones_recomend_medi;
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
	private Label lbxObservaciones_vacunales;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	private Paciente paciente;
	private His_atencion_crecimiento_menor2_5 hc_menor_2_5;
	private ESexo sexo;
	private Timestamp fecha;
	private Integer edad;

	private Admision admision;
	//private Citas cita;
	//private Opciones_via_ingreso opciones_via_ingreso;

	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaPesoTalla;
	private RespuestaInt coordenadaIMCEdad;
	private RespuestaInt coordenadaPerimetroCefalicoEdad;

	private List coordenadasTE;
	private List coordenadasPT;
	private List coordenadasIE;
	private List coordenadasPCE;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	
	@View(isMacro = true)
	private EscalaAbreviadaDesarrollo macroEscalaDesarrollo;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "formReceta",
			"gridOrdenes_servicio", "macroEscalaDesarrollo" };

	/* Observaciones */

	@View private Label lbxObservaciones_cabeza;
	@View private Label lbxObservaciones_sentidos;
	@View private Label lbxObservaciones_cover;
	@View private Label lbxObservaciones_cuello;
	@View private Label lbxObservaciones_torax;
	@View private Label lbxObservaciones_cardiaco;
	@View private Label lbxObservaciones_soplo;
	@View private Label lbxObservaciones_abdomen;
	@View private Label lbxObservaciones_masas;
	@View private Label lbxObservaciones_megalias;
	@View private Label lbxObservaciones_genito;
	@View private Label lbxObservaciones_columna;
	@View private Label lbxObservaciones_extremidades;
	@View private Label lbxObservaciones_piel;
	@View private Label lbxObservaciones_neurologico;
	@View private Label lbxObservaciones_fijacion;
	@View private Label lbxObservaciones_oclusion;
	@View private Label lbxObservaciones_boca;
	
	@View private Row row1;
	@View private Row row2;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	@View private Row row6;

	@Override
	public void init() {
		try {
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String codigo_historia = request.getParameter("codigo_historia");
			String nro_ingreso = request.getParameter("nro_ingreso");
			String nro_identificacion = request.getParameter("nro_identificacion");
			String codigo_empresa = request.getParameter("codigo_empresa");
			String codigo_sucursal = request.getParameter("codigo_sucursal");
			
            Long id_codigo_historia = null;
			
			if (codigo_historia != null && !codigo_historia.trim().isEmpty()
					&& codigo_historia.matches("[0-9]*$")) {
				id_codigo_historia = Long.parseLong(codigo_historia);
			}
			
			if(nro_identificacion!=null){
				admision = new Admision();
				admision.setCodigo_empresa(codigo_empresa);
				admision.setCodigo_sucursal(codigo_sucursal);
				admision.setNro_identificacion(nro_identificacion);
				admision.setNro_ingreso(nro_ingreso);
				admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);
				if(admision!=null){
					macroImpresion_diagnostica.inicializarMacro(this, admision, 
							IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);
					paciente = admision.getPaciente();
					fecha = paciente.getFecha_nacimiento();
					
					edad = Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat(
							"dd/MM/yyyy").format(admision.getPaciente()
							.getFecha_nacimiento()), "1", false));

					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
					fecha = paciente.getFecha_nacimiento();
				}
			}
			
			listarCombos();
			if (id_codigo_historia != null) {
				his_atencion_crecimiento_menor2_5 = new His_atencion_crecimiento_menor2_5();
				his_atencion_crecimiento_menor2_5.setCodigo_empresa(codigo_empresa);
				his_atencion_crecimiento_menor2_5.setCodigo_sucursal(codigo_sucursal);
				his_atencion_crecimiento_menor2_5.setCodigo_historia(id_codigo_historia);
				his_atencion_crecimiento_menor2_5 = getServiceLocator().getServicio(His_atencion_crecimiento_menor2_5Service.class).consultar(his_atencion_crecimiento_menor2_5);
				if(his_atencion_crecimiento_menor2_5!=null){
					
					if(his_atencion_crecimiento_menor2_5.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DETECcion DE ALTERAcion AL MENOR DE 2 A 5 añoS\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DETECcion DE ALTERAcion AL MENOR DE 2 A 5 añoS\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(his_atencion_crecimiento_menor2_5.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					mostrarDatos(his_atencion_crecimiento_menor2_5);
					FormularioUtil.deshabilitarComponentes(gbxContenido, true, idsExcluyentes);
				}
			}
			
			FormularioUtil.inicializarRadiogroupsDefecto(this);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		
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
		Utilidades
				.listarElementoListbox(lbxRelacion, true, getServiceLocator());
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
		His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) obj;
		try {
			infoPacientes.setCodigo_historia(his_atencion_crecimiento_menor2_5
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(his_atencion_crecimiento_menor2_5
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					his_atencion_crecimiento_menor2_5.getUltimo_update());

			//onMostrarModuloRemisiones();
			initMostrar_datos(his_atencion_crecimiento_menor2_5);
			//cargarRemisionInterna(his_atencion_crecimiento_menor2_5);
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
			lbxMotivo_consulta.setValue(his_atencion_crecimiento_menor2_5
					.getMotivo_consulta());
			lbxEnfermedad_actual.setValue(his_atencion_crecimiento_menor2_5
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
			lbxOtros_y_observaciones.setValue(his_atencion_crecimiento_menor2_5
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
			lbxComplicaciones_embarazo_parto
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
			lbxObservaciones1.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones());
			lbxPatologicos_medicos.setValue(his_atencion_crecimiento_menor2_5
					.getPatologicos_medicos());
			lbxPatologicos_quirurgicos
					.setValue(his_atencion_crecimiento_menor2_5
							.getPatologicos_quirurgicos());
			lbxPatologicos_hospitalizaciones
					.setValue(his_atencion_crecimiento_menor2_5
							.getPatologicos_hospitalizaciones());
			lbxPatologicos_medicacion
					.setValue(his_atencion_crecimiento_menor2_5
							.getPatologicos_medicacion());
			lbxPatologicos_alergicos.setValue(his_atencion_crecimiento_menor2_5
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
			lbxObservaciones_hab_aliment
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
			lbxPaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
					.getPaternos_medicos());
			tbxPaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
					.getPaternos_alergicos());
			dbxPaternos_talla.setValue(his_atencion_crecimiento_menor2_5
					.getPaternos_talla());
			lbxMaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
					.getMaternos_medicos());
			tbxMaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
					.getMaternos_alergicos());
			dbxMaternos_talla.setValue(his_atencion_crecimiento_menor2_5
					.getMaternos_talla());
			lbxOtros.setValue(his_atencion_crecimiento_menor2_5.getOtros());
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
			lbxObservacioness.setValue(his_atencion_crecimiento_menor2_5
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
			lbxAnalisis.setValue(his_atencion_crecimiento_menor2_5
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
			lbxDescripciones_recomend_medi
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

			lbxObservaciones_vacunales
					.setValue(his_atencion_crecimiento_menor2_5
							.getObservaciones_vacunales());
			

			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2", lbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2", lbxObservaciones_cuello, row1);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",lbxObservaciones_abdomen,row1);
			FormularioUtil.mostrarObservaciones(rdbOrgano_de_sentidos,"2",lbxObservaciones_sentidos,row2);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2", lbxObservaciones_torax, row2);
			FormularioUtil.mostrarObservaciones(rdbMasa,"2",lbxObservaciones_masas,row2);
			FormularioUtil.mostrarObservaciones(rdbFijacion_seguimiento,"2",lbxObservaciones_fijacion,row3);
			FormularioUtil.mostrarObservaciones(rdbTirmo_cardiaco, "2", lbxObservaciones_cardiaco, row3);
			FormularioUtil.mostrarObservaciones(rdbMegalias, "2", lbxObservaciones_megalias, row3);
			FormularioUtil.mostrarObservaciones(rdbOclusion_alternante,"2",lbxObservaciones_oclusion,row4);
			FormularioUtil.mostrarObservaciones(rdbSoplo, "2", lbxObservaciones_soplo, row4);
			FormularioUtil.mostrarObservaciones(rdbCover_uncover_test, "2", lbxObservaciones_cover, row4);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2", lbxObservaciones_columna, row5);
			FormularioUtil.mostrarObservaciones(rdbNeurologico_a_n,"2",lbxObservaciones_neurologico,row5);
			FormularioUtil.mostrarObservaciones(rdbBoca,"2",lbxObservaciones_boca,row5);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "2", lbxObservaciones_extremidades, row6);			
			FormularioUtil.mostrarObservaciones(rdbPiel_y_anexos, "2", lbxObservaciones_piel, row6);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", lbxObservaciones_genito, row6);

			lbxObservaciones_cabeza.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cabeza());
			lbxObservaciones_abdomen.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_abdomen());
			lbxObservaciones_neurologico.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_neurologico());
			
			lbxObservaciones_sentidos.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_sentidos());
			lbxObservaciones_masas.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_masas());
			
			lbxObservaciones_cover.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cover());
			lbxObservaciones_megalias.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_megalias());
			
			lbxObservaciones_cuello.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cuello());
			lbxObservaciones_genito.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_genito());

			lbxObservaciones_torax.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_torax());
			lbxObservaciones_columna.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_columna());

			lbxObservaciones_cardiaco.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_cardiaco());
			lbxObservaciones_extremidades.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_extremidades());

			lbxObservaciones_soplo.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_soplo());
			lbxObservaciones_piel.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_piel());
			
			lbxObservaciones_fijacion.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_fijacion());
			lbxObservaciones_oclusion.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_oclusion());
			lbxObservaciones_boca.setValue(his_atencion_crecimiento_menor2_5
					.getObservaciones_boca());

			

			//tipo_historia = his_atencion_crecimiento_menor2_5.getTipo_historia();
			calcularCoordenadas(false);

			
			cargarImpresionDiagnostica(his_atencion_crecimiento_menor2_5);
			cargarEscalaDesarrollo(his_atencion_crecimiento_menor2_5);

			//cargarAnexo9_remision(his_atencion_crecimiento_menor2_5);

			FormularioUtil.deshabilitarComponentes(rowCita_anterior, true,
					new String[] {});

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	
	@Override
	public void initHistoria() throws Exception {
		
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowCita_anterior.setVisible(false);
			rowPerinatales.setVisible(true);
			rowPatologicos.setVisible(true);
			rowAntecedentes_familiares.setVisible(true);
			rowAnalisis.setVisible(true);
			//tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowCita_anterior.setVisible(true);
			rowAnalisis.setVisible(true);
			rowPerinatales.setVisible(false);
			rowPatologicos.setVisible(false);
			rowAntecedentes_familiares.setVisible(false);
			//tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
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
		//codigo_historia_anterior = his_atencion_crecimiento_menor2_5.getCodigo_historia();

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
		lbxComplicaciones_embarazo_parto
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
		lbxObservaciones1.setValue(his_atencion_crecimiento_menor2_5
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
		lbxPaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
				.getPaternos_medicos());
		tbxPaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
				.getPaternos_alergicos());
		dbxPaternos_talla.setValue(his_atencion_crecimiento_menor2_5
				.getPaternos_talla());
		lbxMaternos_medicos.setValue(his_atencion_crecimiento_menor2_5
				.getMaternos_medicos());
		tbxMaternos_alergicos.setValue(his_atencion_crecimiento_menor2_5
				.getMaternos_alergicos());
		dbxMaternos_talla.setValue(his_atencion_crecimiento_menor2_5
				.getMaternos_talla());
		lbxOtros.setValue(his_atencion_crecimiento_menor2_5.getOtros());

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
			//btnCalcularTallaEdad.setDisabled(false);
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
			//btnCalcularPesoTalla.setDisabled(false);
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
			//btnCalcularImcEdad.setDisabled(false);
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
			//btnCalcularPerimetroCefalicoEdad.setDisabled(false);
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
			//	+ impresion_diagnostica);
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		//log.info(">>>>>>>>>>>>(impresion diagnostica)D: "
			//	+ impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
	}

	

	
	public void notificarAlerta(Component comp) {
		MensajesUtil.notificarAlerta("Remitir", comp);
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
		informacion_clinica.append("\t").append(lbxMotivo_consulta.getValue())
				.append("\n");

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

}