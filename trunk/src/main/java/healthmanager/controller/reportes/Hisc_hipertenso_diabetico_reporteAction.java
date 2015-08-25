/*
 * hisc_hipertenso_diabeticoAction.java
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
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_hipertenso_diabetico;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Presultados_paraclinicos;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Hisc_hipertenso_diabeticoService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculoMedico;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_hipertenso_diabetico_reporteAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_hipertenso_diabetico_reporteAction.class);

	// Componentes //
	@View
	private Groupbox gbxContenido;
	
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
	
	@View
	public Label lbxObservaciones_framingham;
	@View
	public Radiogroup rdbPorcentaje_fram;
	
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	private Admision admision;
//	private Citas cita;
//	private Opciones_via_ingreso opciones_via_ingreso;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Label lbxMotivo_consulta;
	@View
	private Label lbxEnfermedad_actual;
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
	private Label lbxObservacion_rs;
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
	private Label lbxObservacion_patologico;
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
	private Label lbxOtros_observaciones;
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
	private Label lbxObservaciones_habitos;
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
	private Label lbxObservaciones_examfisico;
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
	private Label lbxAnalisis;
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
	private Label lbxTratamiento_farmacologico;
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
//	private Long codigo_historia_anterior;

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

//	private String nro_ingreso_admision;
//	private boolean cobrar_agudeza;

//	private ZKWindow zkWindow;
	
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
	
	private Paciente paciente;
	private Hisc_hipertenso_diabetico hisc_hipertenso_diabetico;
//	private ESexo sexo;
//	private Timestamp fecha;
	
	private final String[] idsExcluyentes = { "divParaclinicos", "tbxValue",
			"lbxParameter", "northEditar",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "lbxParentesco_acompanante",
			"toolbarbuttonImprimirRI" };



	@View private Label lbxObservaciones_conciencia;
	@View private Label lbxObservaciones_hidratacion;
	@View private Label lbxObservaciones_general;
	@View private Label lbxObservaciones_cabeza;
	@View private Label lbxObservaciones_sentidos;
	@View private Label lbxObservaciones_fondo;
	@View private Label lbxObservaciones_yugular;
	@View private Label lbxObservaciones_soplo_carotideo;
	@View private Label lbxObservaciones_tiroides;
	@View private Label lbxObservaciones_masas;
	@View private Label lbxObservaciones_pmi;
	@View private Label lbxObservaciones_soplo;
	@View private Label lbxObservaciones_cardiaco;
	@View private Label lbxObservaciones_masas_abdomen;
	@View private Label lbxObservaciones_megalias;
	@View private Label lbxObservaciones_soplo_abdomen;
	@View private Label lbxObservaciones_genito;
	@View private Label lbxObservaciones_columna;
	@View private Label lbxObservaciones_piel;
	@View private Label lbxObservaciones_coloracion;
	@View private Label lbxObservaciones_temperatura;
	@View private Label lbxObservaciones_vellos;
	@View private Label lbxObservaciones_pulsos;
	@View private Label lbxObservaciones_tibial;
	@View private Label lbxObservaciones_edema;
	@View private Label lbxObservaciones_ulcera;
	@View private Label lbxObservaciones_sensibilidad;
	@View private Label lbxObservaciones_motricidad;
	@View private Label lbxObservaciones_reflejos;
	
	@View private Row row1;
	@View private Row row2;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	@View private Row row6;
	@View private Row row7;
	@View private Row row8;
	@View private Row row9;
	@View private Row row10;
	@View private Row row11;
	@View private Row row12;
	
	@Override
	public void init() {
		try {
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String codigo_historia = request.getParameter("codigo_historia");
			String nro_ingreso = request.getParameter("nro_ingreso");
			String nro_identificacion = request.getParameter("nro_identificacion");
			String codigo_empresa = request.getParameter("codigo_empresa");
			String codigo_sucursal = request.getParameter("codigo_sucursal");
			//String codigo_medico = request.getParameter("codigo_medico");
			
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
							IVias_ingreso.HIPERTENSO_DIABETICO);
					paciente = admision.getPaciente();
//					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
//					fecha = paciente.getFecha_nacimiento();
				}
			}

			listarCombos();
			if(paciente.getSexo().equals("F")){
				gbxGineco_obstetricos.setVisible(true);
			}else{
				gbxGineco_obstetricos.setVisible(false);
			}
			
			
			if (id_codigo_historia != null) {
				hisc_hipertenso_diabetico = new Hisc_hipertenso_diabetico();
				hisc_hipertenso_diabetico.setCodigo_empresa(codigo_empresa);
				hisc_hipertenso_diabetico.setCodigo_sucursal(codigo_sucursal);
				hisc_hipertenso_diabetico.setCodigo_historia(id_codigo_historia);
				hisc_hipertenso_diabetico = getServiceLocator().getServicio(Hisc_hipertenso_diabeticoService.class).consultar(hisc_hipertenso_diabetico);
				if(hisc_hipertenso_diabetico!=null){
					if(hisc_hipertenso_diabetico.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DE HIPERTENSO - DIABETICO\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DE HIPERTENSO - DIABETICO\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(hisc_hipertenso_diabetico.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					mostrarDatos(hisc_hipertenso_diabetico);
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
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
	public void params(Map<String, Object> map)

	{
		
	}

	/**
	 * Metodo para cargar la informacion de la historia anterior ya que para
	 * esta historia clinica aplica cargar informacion de la historia anterior
	 * cuando es de control
	 * 
	 * @param hisc_hipertenso_diabetico
	 */

	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxParentesco_acompanante, true,
				getServiceLocator());

	}

	

//	private void crearFilasMedicamentosAnteriores(Listbox listboxMedicamentos,
//			String ids) {
//		//log.info("LLamando al metodo crearFilasMedicamentosAnteriores");
//		listboxMedicamentos.getItems().clear();
//		for (int i = 0; i < 5; i++) {
//			Listitem listitem = new Listitem();
//			Listcell listcell = Utilidades.obtenerListcell("", Textbox.class,
//					false, false);
//			Textbox textbox_columna1 = (Textbox) listcell.getFirstChild();
//			textbox_columna1.setId("textbox_columna1_" + ids + "_" + i);
//			listitem.appendChild(listcell);
//			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
//					false);
//			Textbox textbox_columna2 = (Textbox) listcell.getFirstChild();
//			textbox_columna2.setId("textbox_columna2_" + ids + "_" + i);
//			listitem.appendChild(listcell);
//			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
//					false);
//			Textbox textbox_columna3 = (Textbox) listcell.getFirstChild();
//			textbox_columna3.setId("textbox_columna3_" + ids + "_" + i);
//			listitem.appendChild(listcell);
//			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
//					false);
//			Textbox textbox_columna4 = (Textbox) listcell.getFirstChild();
//			textbox_columna4.setId("textbox_columna4_" + ids + "_" + i);
//			listitem.appendChild(listcell);
//			listcell = Utilidades.obtenerListcell("", Textbox.class, false,
//					false);
//			Textbox textbox_columna5 = (Textbox) listcell.getFirstChild();
//			textbox_columna5.setId("textbox_columna5_" + ids + "_" + i);
//			listitem.appendChild(listcell);
//			listboxMedicamentos.appendChild(listitem);
//		}
//	}

//	private String getValoresMedicamentosAnteriores(
//			Listbox listboxMedicamentos, String ids, Integer columna) {
//		StringBuffer stringBuffer = new StringBuffer();
//		List<Listitem> listado = listboxMedicamentos.getItems();
//		for (int i = 0; i < listado.size(); i++) {
//			Textbox textbox_columna = (Textbox) listboxMedicamentos
//					.getFellow("textbox_columna" + columna + "_" + ids + "_"
//							+ i);
//			if (i != 0) {
//				stringBuffer.append("\n");
//			}
//			stringBuffer.append(textbox_columna.getValue());
//		}
//		return stringBuffer.toString();
//	}

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

	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		//manejadorParaclinicos.limpiarResultados_paraclinicos();
	}

	
	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_hipertenso_diabetico hisc_hipertenso_diabetico = (Hisc_hipertenso_diabetico) obj;
		try {
//			this.nro_ingreso_admision = hisc_hipertenso_diabetico
//					.getNro_ingreso();
			infoPacientes.setCodigo_historia(hisc_hipertenso_diabetico
					.getCodigo_historia());

			infoPacientes.setFecha_inicio(hisc_hipertenso_diabetico
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_hipertenso_diabetico.getUltimo_update());

			//onMostrarModuloRemisiones();
			initMostrar_datos(hisc_hipertenso_diabetico);

			cargarInformacion_paciente();

			//cargarRemisionInterna(hisc_hipertenso_diabetico);
			cargarAgudezaVisual(hisc_hipertenso_diabetico);

			lbxMotivo_consulta.setValue(hisc_hipertenso_diabetico
					.getMotivo_consulta());
			lbxEnfermedad_actual.setValue(hisc_hipertenso_diabetico
					.getEnfermedad_actual());

			Utilidades.seleccionarRadio(rdbCefalea,
					hisc_hipertenso_diabetico.getCefalea());

			//log.info("rdbPorcentaje_fram" + rdbPorcentaje_fram);
			//log.info("hisc_hipertenso_diabetico" + hisc_hipertenso_diabetico);

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
			lbxObservacion_rs.setValue(hisc_hipertenso_diabetico
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
			lbxObservacion_patologico.setValue(hisc_hipertenso_diabetico
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
			lbxOtros_observaciones.setValue(hisc_hipertenso_diabetico
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
			lbxObservaciones_habitos.setValue(hisc_hipertenso_diabetico
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

			lbxObservaciones_framingham.setValue(hisc_hipertenso_diabetico
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
			lbxObservaciones_examfisico.setValue(hisc_hipertenso_diabetico
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
			lbxAnalisis.setValue(hisc_hipertenso_diabetico.getAnalisis());
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
			lbxTratamiento_farmacologico.setValue(hisc_hipertenso_diabetico
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
					.setChecked(hisc_hipertenso_diabetico
							.getEkg_parac() != null ? hisc_hipertenso_diabetico
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
			

			FormularioUtil.mostrarObservaciones(rdbConciencia, "A", lbxObservaciones_conciencia, row1);
			FormularioUtil.mostrarObservaciones(rdbHidratacion,"A",lbxObservaciones_hidratacion,row2);
			FormularioUtil.mostrarObservaciones(rdbTorax_pmi,"A",lbxObservaciones_pmi,row2);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_coloracion,"A",lbxObservaciones_coloracion,row2);
			FormularioUtil.mostrarObservaciones(rdbCondicion_general, "A", lbxObservaciones_general, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_ritmo, "A", lbxObservaciones_cardiaco, row3);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_temperatura, "A", lbxObservaciones_temperatura, row3);
			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "A", lbxObservaciones_cabeza, row4);
			FormularioUtil.mostrarObservaciones(rdbExtremidades_vellos, "A", lbxObservaciones_vellos, row4);
			FormularioUtil.mostrarObservaciones(rdbTorax_soplo, "A", lbxObservaciones_soplo, row4);
			FormularioUtil.mostrarObservaciones(rdbOrgano_sentidos, "A", lbxObservaciones_sentidos, row5);	
			FormularioUtil.mostrarObservaciones(rdbExtremidades_pulsos, "A", lbxObservaciones_pulsos, row5);			
			FormularioUtil.mostrarObservaciones(rdbFondo_ojo, "A", lbxObservaciones_fondo, row6);		
			FormularioUtil.mostrarObservaciones(rdbAbdomen_masas, "A", lbxObservaciones_masas_abdomen, row6);		
			FormularioUtil.mostrarObservaciones(rdbExtremidades_tibial, "A", lbxObservaciones_tibial, row6);	
			FormularioUtil.mostrarObservaciones(rdbAbdomen_megalias, "A", lbxObservaciones_megalias, row7);	
			FormularioUtil.mostrarObservaciones(rdbExtremidades_edema, "A", lbxObservaciones_edema, row7);		
			FormularioUtil.mostrarObservaciones(rdbCuello_yugular, "A", lbxObservaciones_yugular, row8);	
			FormularioUtil.mostrarObservaciones(rdbAbdomen_soplos, "A", lbxObservaciones_soplo_abdomen, row8);	
			FormularioUtil.mostrarObservaciones(rdbExtremidades_ulcera, "A", lbxObservaciones_ulcera, row8);
			FormularioUtil.mostrarObservaciones(rdbCuello_soplo, "A", lbxObservaciones_soplo_carotideo, row9);	
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "A", lbxObservaciones_genito, row9);		
			FormularioUtil.mostrarObservaciones(rdbCuello_gland, "A", lbxObservaciones_tiroides, row10);	
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "A", lbxObservaciones_columna, row10);	
			FormularioUtil.mostrarObservaciones(rdbNeurologico_sensibilidad, "A", lbxObservaciones_sensibilidad, row10);
			FormularioUtil.mostrarObservaciones(rdbCuello_masas, "A", lbxObservaciones_masas, row11);	
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "A", lbxObservaciones_piel, row11);	
			FormularioUtil.mostrarObservaciones(rdbNeurologico_motricidad, "A", lbxObservaciones_motricidad, row11);
			FormularioUtil.mostrarObservaciones(rdbNeurologico_reflejos, "A", lbxObservaciones_reflejos, row12);
			
			
			lbxObservaciones_conciencia.setValue(hisc_hipertenso_diabetico.getObservaciones_conciencia());
			lbxObservaciones_hidratacion.setValue(hisc_hipertenso_diabetico.getObservaciones_hidratacion());
			lbxObservaciones_general.setValue(hisc_hipertenso_diabetico.getObservaciones_general());
			lbxObservaciones_cabeza.setValue(hisc_hipertenso_diabetico.getObservaciones_cabeza());
			lbxObservaciones_sentidos.setValue(hisc_hipertenso_diabetico.getObservaciones_sentidos());
			lbxObservaciones_fondo.setValue(hisc_hipertenso_diabetico.getObservaciones_fondo());
			lbxObservaciones_yugular.setValue(hisc_hipertenso_diabetico.getObservaciones_yugular());
			lbxObservaciones_soplo_carotideo.setValue(hisc_hipertenso_diabetico.getObservaciones_soplo_carotideo());
			lbxObservaciones_tiroides.setValue(hisc_hipertenso_diabetico.getObservaciones_tiroides());
			lbxObservaciones_masas.setValue(hisc_hipertenso_diabetico.getObservaciones_masas());
			lbxObservaciones_pmi.setValue(hisc_hipertenso_diabetico.getObservaciones_pmi());
			lbxObservaciones_soplo.setValue(hisc_hipertenso_diabetico.getObservaciones_soplo());
			lbxObservaciones_cardiaco.setValue(hisc_hipertenso_diabetico.getObservaciones_cardiaco());
			lbxObservaciones_masas_abdomen.setValue(hisc_hipertenso_diabetico.getObservaciones_masas_abdomen());
			lbxObservaciones_megalias.setValue(hisc_hipertenso_diabetico.getObservaciones_megalias());
			lbxObservaciones_soplo_abdomen.setValue(hisc_hipertenso_diabetico.getObservaciones_soplo_abdomen());
			lbxObservaciones_genito.setValue(hisc_hipertenso_diabetico.getObservaciones_genito());
			lbxObservaciones_columna.setValue(hisc_hipertenso_diabetico.getObservaciones_columna());
			lbxObservaciones_piel.setValue(hisc_hipertenso_diabetico.getObservaciones_piel());
			lbxObservaciones_coloracion.setValue(hisc_hipertenso_diabetico.getObservaciones_colocacion());
			lbxObservaciones_temperatura.setValue(hisc_hipertenso_diabetico.getObservaciones_temperatura());
			lbxObservaciones_vellos.setValue(hisc_hipertenso_diabetico.getObservaciones_vellos());
			lbxObservaciones_pulsos.setValue(hisc_hipertenso_diabetico.getObservaciones_pulsos());
			lbxObservaciones_tibial.setValue(hisc_hipertenso_diabetico.getObservaciones_tibial());
			lbxObservaciones_edema.setValue(hisc_hipertenso_diabetico.getObservaciones_edema());
			lbxObservaciones_ulcera.setValue(hisc_hipertenso_diabetico.getObservaciones_ulcera());
			lbxObservaciones_sensibilidad.setValue(hisc_hipertenso_diabetico.getObservaciones_sencibilidad());
			lbxObservaciones_motricidad.setValue(hisc_hipertenso_diabetico.getObservaciones_motricidad());
			lbxObservaciones_reflejos.setValue(hisc_hipertenso_diabetico.getObservaciones_reflejos());
			
			cargarImpresionDiagnostica(hisc_hipertenso_diabetico);

			
			inicializarVista(tipo_historia);
			
			
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
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
			//log.info("sexo" + admision.getPaciente().getSexo().equals("F"));
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
		
	}

	
	public void calcularSuperficieCorporal(Doublebox dbxTalla,
			Doublebox dbxPeso, Doublebox dbxSup_corporalc) {
		if (dbxPeso.getValue() != null & dbxTalla.getValue() != null) {
			Double peso = dbxPeso.getValue() * 100;
			Double talla = dbxTalla.getValue();
			Double supcor = (peso * talla) / 3600;
			dbxSup_corporalc.setValue(Math.sqrt(supcor));
		}
	}

	@Override
	public void inicializarVista(String tipo) {
		
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
//		codigo_historia_anterior = hisc_hipertenso_diabetico
//				.getCodigo_historia();
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		
	}

	/*
	 public void onCalcular_creatinina() {
	 
		List<Presultados_paraclinicos> listado_paraclinicos = manejadorParaclinicos
				.obtenerResultados_paraclinicos();
		String resultado_creatinina = "";

		for (Presultados_paraclinicos presultados_paraclinicos : listado_paraclinicos) {
			//log.info("1: " + presultados_paraclinicos.getCodigo_examen());
			if (presultados_paraclinicos.getCodigo_examen().equals("00040")
					|| presultados_paraclinicos.getCodigo_examen().equals(
							"00011")) {
				resultado_creatinina = presultados_paraclinicos.getResultado();
				//log.info("2: " + resultado_creatinina);
			}
		}

		//log.info("3: " + resultado_creatinina);
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
*/
	public void onCalcularTasaFiltracionGlomerural() {
		Paciente paciente = admision.getPaciente();
		try {
			dbxTfg.setConstraint("");
			dbxTfg.setValue(null);
			Double talla = dbxTalla.getValue();
			Double peso = dbxPeso.getValue();
			Double superfice_corporal = dbxSup_corporal.getValue();
			Double creatinina = dbxCreatinina.getValue();
			Integer edad = Integer.parseInt(Util.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente.getFecha_nacimiento()),"1", false));
			Boolean mujer = paciente.getSexo().equalsIgnoreCase("F");
			Boolean afro = paciente.getPertenencia_etnica().equals("5");
					

			
			if (talla != null && peso != null && superfice_corporal != null
					&& creatinina != null
					&& paciente.getFecha_nacimiento() != null
					&& paciente.getSexo() != null) {
					
				Double tfg_final = 0d;
					
				if(afro){
					tfg_final = UtilidadSignosVitales.calculoTFG(creatinina, edad, mujer, afro);
				}else{
					tfg_final = UtilidadSignosVitales.calculoTFGCockcroft(creatinina, edad, peso, mujer);
				}

//				double tfg_parcial = ((140 - edad_anios) * peso.doubleValue()) / (72 * creatinina.doubleValue());
//				double tfg_final = tfg_parcial;
//				if (paciente.getSexo().toUpperCase().equals("F")) {
//					tfg_final = tfg_final * 0.85;
//				}
//				double tfg_x = (tfg_final * 1.73) / superfice_corporal.doubleValue();
				
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
		//log.info("antes " + ultimo);

		ultimo = getServiceLocator().getPresultados_paraclinicosService()
				.mostrar_ultimo(ultimo);
		//log.info("despues " + ultimo);

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
			//log.info("valor de res>>>>>>>>>>" + res[0]);
			rdbPorcentaje_fram.setSelectedIndex(ConvertidorDatosUtil
					.convertirDatot(res[1]));
			//log.info("valor de res>>>>>>>>>>" + res[1]);
			//log.info("integer de res>>>>>>>>>>"
					//+ ConvertidorDatosUtil.convertirDatot(res[1]));
			//log.info("get selectec>>>>>>>>>>"
					//+ rdbPorcentaje_fram.getSelectedIndex());
		}
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
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
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
		informacion_clinica.append("\t").append(lbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (lbxEnfermedad_actual.getValue() != null
				&& !lbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxEnfermedad_actual.getValue()).append("\n");
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
	

}