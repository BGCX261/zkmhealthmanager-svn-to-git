/*
 * hisc_deteccion_alt_menor_2m_2aAction.java
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
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Escala_del_desarrollo;
import healthmanager.modelo.bean.Hisc_deteccion_alt_menor_2m_2a;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Escala_del_desarrolloService;
import healthmanager.modelo.service.Hisc_deteccion_alt_menor_2m_2aService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

public class Hisc_deteccion_alt_menor_2m_2a_reporteAction extends HistoriaClinicaModel
		implements IHistoria_generica {

//	private static Logger log = Logger
//			.getLogger(Hisc_deteccion_alt_menor_2m_2a_reporteAction.class);

	// Componentes //

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
	private Label lbxMotivo_consulta;
	@View
	private Label lbxGestion_actual;
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
	private Label lbxObservaciones_3;
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
	private Label lbxComplicaciones_embarazo_parto;
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
	private Label lbxObservaciones_4_1;
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
	private Label lbxObservaciones_4_4;
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
	private Label lbxPaterno_medicos;
	@View
	private Textbox tbxPaterno_alergico;
	@View
	private Doublebox dbxMaterno_talla;
	@View
	private Label lbxMaterno_medicos;
	@View
	private Textbox tbxMaterno_alergico;
	@View
	private Doublebox dbxPaterno_talla;
	@View
	private Label lbxOtros_5;
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
	private Label lbxObservaciones_7_2;
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
	private Label lbxAnalisis_9;
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
	private Label lbxRecomendaciones_10;
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
//	private Long codigo_historia_anterior;

	@View
	private Radiogroup rdbCa_Diagnostico_crecimiento;
	@View
	private Radiogroup rdbCa_Diagnostico_desarrollo;

	private Admision admision;
	
//	private Citas cita;

//	private Opciones_via_ingreso opciones_via_ingreso;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Label lbxObservaciones_vacunales;

	
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
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

//	private String nro_ingreso_admision;
	private Integer edad;

	@View(isMacro = true)
	private EscalaAbreviadaDesarrollo macroEscalaDesarrollo;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "macroEscalaDesarrollo" };
	
	/* Observaciones */

	@View private Label lbxObservaciones_cabeza;
	@View private Label lbxObservaciones_sentidos;
	@View private Label lbxObservaciones_rojo;
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
							IVias_ingreso.HC_MENOR_2_MESES_2_ANOS);
					paciente = admision.getPaciente();
					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
					fecha = paciente.getFecha_nacimiento();
				}
			}
			
			listarCombos();
			if(id_codigo_historia!=null){
				hisc_deteccion_alt_menor_2m_2a = new Hisc_deteccion_alt_menor_2m_2a();
				hisc_deteccion_alt_menor_2m_2a.setCodigo_empresa(codigo_empresa);
				hisc_deteccion_alt_menor_2m_2a.setCodigo_sucursal(codigo_sucursal);
				hisc_deteccion_alt_menor_2m_2a.setCodigo_historia(id_codigo_historia);
				hisc_deteccion_alt_menor_2m_2a = getServiceLocator().getServicio(Hisc_deteccion_alt_menor_2m_2aService.class).consultar(hisc_deteccion_alt_menor_2m_2a);
				if(hisc_deteccion_alt_menor_2m_2a!=null){
					
					if(hisc_deteccion_alt_menor_2m_2a.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DETECcion DE ALTERAcion AL MENOR DE 2 MESES A 2 añoS\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DETECcion DE ALTERAcion AL MENOR DE 2 MESES A 2 añoS\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(hisc_deteccion_alt_menor_2m_2a.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					
					mostrarDatos(hisc_deteccion_alt_menor_2m_2a);
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
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
		Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a = (Hisc_deteccion_alt_menor_2m_2a) obj;
		try {
//			this.nro_ingreso_admision = hisc_deteccion_alt_menor_2m_2a
//					.getNro_ingreso();

			infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_2m_2a
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_deteccion_alt_menor_2m_2a
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_deteccion_alt_menor_2m_2a.getUltimo_update());
			initMostrar_datos(hisc_deteccion_alt_menor_2m_2a);
			cargarInformacion_paciente();

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
			lbxMotivo_consulta.setValue(hisc_deteccion_alt_menor_2m_2a
					.getMotivo_consulta());
			lbxGestion_actual.setValue(hisc_deteccion_alt_menor_2m_2a
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
			lbxObservaciones_3.setValue(hisc_deteccion_alt_menor_2m_2a
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

			lbxComplicaciones_embarazo_parto
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
			lbxObservaciones_4_1.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_4_1());
			lbxPatologicos_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPatologicos_medicos());
			lbxPatologicos_quirurgicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPatologicos_quirurgicos());
			lbxPatologicos_hospitalizaciones
					.setValue(hisc_deteccion_alt_menor_2m_2a
							.getPatologicos_hospitalizaciones());
			lbxPatologicos_medicacion.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPatologicos_medicacion());
			lbxPatologicos_alergicos.setValue(hisc_deteccion_alt_menor_2m_2a
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
			lbxObservaciones_4_4.setValue(hisc_deteccion_alt_menor_2m_2a
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
			lbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPaterno_medicos());
			tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
					.getPaterno_alergico());
			dbxMaterno_talla.setValue(ConvertidorDatosUtil
					.convertirDato((hisc_deteccion_alt_menor_2m_2a
							.getPaterno_talla())));
			lbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getMaterno_medicos());
			tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
					.getMaterno_alergico());
			dbxPaterno_talla.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_menor_2m_2a
							.getMaterno_talla()));
			lbxOtros_5.setValue(hisc_deteccion_alt_menor_2m_2a.getOtros_5());
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
			lbxObservaciones_7_2.setValue(hisc_deteccion_alt_menor_2m_2a
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

			lbxAnalisis_9.setValue(hisc_deteccion_alt_menor_2m_2a
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
			lbxRecomendaciones_10.setValue(hisc_deteccion_alt_menor_2m_2a
					.getRecomendaciones_10());
			lbxObservaciones_vacunales.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_vacunales());

			calcularCoordenadas(false);

			// Mostramos la vista //
			cargarImpresionDiagnostica(hisc_deteccion_alt_menor_2m_2a);
			cargarEscalaDesarrollo(hisc_deteccion_alt_menor_2m_2a);
			

			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2", lbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbOrgano_de_los_sentidos,"2",lbxObservaciones_sentidos,row1);
			FormularioUtil.mostrarObservaciones(rdbRojo_retiniano, "2", lbxObservaciones_rojo, row1);
			FormularioUtil.mostrarObservaciones(rdbFijacion_seguimiento,"2",lbxObservaciones_fijacion,row2);
			FormularioUtil.mostrarObservaciones(rdbOclusion_alternante,"2",lbxObservaciones_oclusion,row2);
			FormularioUtil.mostrarObservaciones(rdbBoca,"2",lbxObservaciones_boca,row2);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2", lbxObservaciones_cuello, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2", lbxObservaciones_torax, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_ritmo_cardiaco, "2", lbxObservaciones_cardiaco, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_soplo, "2", lbxObservaciones_soplo, row4);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2", lbxObservaciones_columna, row4);
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "2", lbxObservaciones_piel, row4);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",lbxObservaciones_abdomen,row5);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_masas,"2",lbxObservaciones_masas,row5);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_megalias, "2", lbxObservaciones_megalias, row5);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", lbxObservaciones_genito, row6);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "2", lbxObservaciones_extremidades, row6);
			FormularioUtil.mostrarObservaciones(rdbNeurologico,"2",lbxObservaciones_neurologico,row6);

			lbxObservaciones_cabeza.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_cabeza());
			lbxObservaciones_abdomen.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_abdomen());
			lbxObservaciones_neurologico.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_neurologico());
			
			lbxObservaciones_sentidos.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_sentidos());
			lbxObservaciones_masas.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_masas());
			
			lbxObservaciones_rojo.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_rojo());
			lbxObservaciones_megalias.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_megalias());
			
			lbxObservaciones_cuello.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_cuello());
			lbxObservaciones_genito.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_genito());

			lbxObservaciones_torax.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_torax());
			lbxObservaciones_columna.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_columna());

			lbxObservaciones_cardiaco.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_cardiaco());
			lbxObservaciones_extremidades.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_extremidades());

			lbxObservaciones_soplo.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_soplo());
			lbxObservaciones_piel.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_piel());
			
			lbxObservaciones_fijacion.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_fijacion());
			lbxObservaciones_oclusion.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_oclusion());
			lbxObservaciones_boca.setValue(hisc_deteccion_alt_menor_2m_2a
					.getObservaciones_boca());


			//cargarAnexo9_remision(hisc_deteccion_alt_menor_2m_2a);

			inicializarVista(tipo_historia);
			
			FormularioUtil.deshabilitarComponentes(rowCita_anterior, true,
					new String[] {});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
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
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {
		
	}

	@Override
	public void inicializarVista(String tipo) {
		
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
		lbxComplicaciones_embarazo_parto
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
		lbxObservaciones_4_1.setValue(hisc_deteccion_alt_menor_2m_2a
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
		lbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
				.getPaterno_medicos());
		tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
				.getPaterno_alergico());
		dbxPaterno_talla.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_2m_2a
						.getPaterno_talla()));
		lbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_2m_2a
				.getMaterno_medicos());
		tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_2m_2a
				.getMaterno_alergico());
		dbxMaterno_talla.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_2m_2a
						.getMaterno_talla()));
		lbxOtros_5.setValue(hisc_deteccion_alt_menor_2m_2a.getOtros_5());

		// DATOS DEL PACIENTE
		// tbxNombre_del_acompanante.setValue(hisc_deteccion_alt_menor_2m_2a.getNombre_del_acompanante());
		// Utilidades.seleccionarListitem(lbxParentesco,hisc_deteccion_alt_menor_2m_2a.getParentesco());
		tbxNombre_del_padre.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNombre_del_padre());
		ibxEdad_padre.setValue(hisc_deteccion_alt_menor_2m_2a.getEdad_padre());
		tbxNombre_de_la_madre.setValue(hisc_deteccion_alt_menor_2m_2a
				.getNombre_de_la_madre());
		ibxEdad_madre.setValue(hisc_deteccion_alt_menor_2m_2a.getEdad_madre());

//		codigo_historia_anterior = hisc_deteccion_alt_menor_2m_2a
//				.getCodigo_historia();
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		
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
			//btnCalcularPesoEdad.setDisabled(false);
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
			//btnCalcularTallaEdad.setDisabled(false);
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
			//btnCalcularPesoTalla.setDisabled(false);
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

		//btnCalcularPerimetroCefalicoEdad.setDisabled(false);

		if (dbxExamen_fisico_perimetro_cflico.getValue() != null
				&& perimetroCefalicoValido(perimetro_cefalico)) {
			coordenadaPerimetroCefalicoEdad = calcularPerimetroCefalicoEdad(perimetro_cefalico);
			dbxPC_e_de.setValue(coordenadaPerimetroCefalicoEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxPC_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_PC_EDAD);
			}
			//btnCalcularPerimetroCefalicoEdad.setDisabled(false);
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
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
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

	
	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :\n");
		informacion_clinica.append("\t").append(lbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :\n");
		if (lbxGestion_actual.getValue() != null
				&& !lbxGestion_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxGestion_actual.getValue()).append("\n");
		} else if (lbxGestion_actual.getValue() != null
				&& !lbxGestion_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxGestion_actual.getValue()).append("\n");
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