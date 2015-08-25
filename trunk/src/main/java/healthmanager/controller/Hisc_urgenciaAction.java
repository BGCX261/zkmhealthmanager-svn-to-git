package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Antecedentes_personales;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Revision_sistema;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.His_triageService;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.GlasgowMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.SignosVitalesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.macros.impl.ModuloConsultaIMG;
import com.framework.macros.revision_sistemas.TreeItemDinamicRevisionSistemasMacro;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.ConvertidorXmlToMap;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class Hisc_urgenciaAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	/**
	 * Cargamos los campos correspondientes a la vista de la historia
	 * */
	private static Logger log = Logger.getLogger(Hisc_urgenciaAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private ModuloConsultaIMG padre;

	private Admision admision_seleccionada;
	private Opciones_via_ingreso opciones_via_ingreso;

	private His_triageService his_triageService;

	private String nro_ingreso_admision;

	private String via_ingreso = "1";
	private String tipo_urgencia = IVias_ingreso.TIPO_URGENCIA_GENERAL;

	// Componentes //
	@View
	private Textbox tbxDes_vida_Marital;
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
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
	private Toolbarbutton btnCancelar;
	@View
	private Listbox lbxTipo_historia;

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

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Textbox tbxNro_identificacion;

	@View
	private Bandbox bandboxPrestador;
	@View
	private Textbox tbxNomPrestador;

	// Acompañante
	@View
	private Textbox tbxAcompanante;
	@View
	private Doublebox dbxIdentificacion_acompanante;
	@View
	private Listbox lbxRelacion;
	@View
	private Intbox ibxEdad_acompanante;
	@View
	private Listbox lbxEscolaridad_acompanante;
	@View
	private Doublebox dbxTelefono_acompanante;

	@View
	private Textbox tbxNro_ingreso;

	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxEnfermedad_actual;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	/* fin contenedores de adicion */

	@View
	private Grid gridAntecedentes_personales;
	@View
	private Hlayout hlayoutAntecedentes_personales;
	@View
	private Listbox lbx_conf_fur;
	@View
	private Intbox ibxHab_ejercicio_minutos_dias;
	@View
	private Doublebox dbxHab_ejercicio_horas_semana;

	@View
	private Radiogroup chbHab_dietas;
	@View
	private Textbox tbxHab_dietas_frecuencia;
	@View
	private Radiogroup chbHab_factores_psicologicos;
	@View
	private Textbox tbxHab_factores_psicologicos_cual;

	@View
	private Bandbox bandboxAnte_fam_hipertension;
	@View
	private Bandbox bandboxAnte_fam_ecv;
	@View
	private Bandbox bandboxAnte_fam_enf_coronaria;
	@View
	private Bandbox bandboxAnte_fam_muerte_im_acv;
	@View
	private Bandbox bandboxAnte_fam_dislipidemia;
	@View
	private Bandbox bandboxAnte_fam_nefropatias;
	@View
	private Bandbox bandboxAnte_fam_obesos;
	@View
	private Bandbox bandboxAnte_fam_diabetes;
	@View
	private Bandbox bandboxAnte_fam_enf_mental;
	@View
	private Bandbox bandboxAnte_fam_cancer;
	@View
	private Bandbox bandboxAnte_fam_hematologia;
	@View
	private Bandbox bandboxAnte_fam_otros;

	@View
	private Bandbox bandboxAnte_fam_asma;
	@View
	private Bandbox bandboxAnte_fam_alergicos;
	@View
	private Bandbox bandboxAnte_fam_infecciosa_vih;
	@View
	private Bandbox bandboxAnte_fam_infecciosa_sifilis;
	@View
	private Bandbox bandboxAnte_fam_infecciosa_hepatitisb;
	@View
	private Bandbox bandboxAnte_fam_infecciosa_tuberculosis;
	@View
	private Bandbox bandboxAnte_fam_infecciosa_lepra;

	@View
	private Textbox tbxAnte_fam_estecifique;
	@View
	private Textbox tbxAnte_fam_observaciones;

	@View
	private Groupbox gbxGPAC;

	@View
	private Textbox tbxAnt_gin_fecha_ultima_regla;
	@View
	private Datebox dtbxFum;
	@View
	private Datebox dtbxAnt_gin_fecha_espectante_parto;

	@View
	private Intbox ibxGenerales_embarazos;
	@View
	private Intbox ibxMalformaciones_embarazos;
	@View
	private Intbox ibxNacidos_vivos;
	@View
	private Intbox ibxNacidos_muertos;
	@View
	private Intbox ibxPreterminos;
	@View
	private Intbox ibxNro_gestaciones;
	@View
	private Intbox ibxNro_partos;
	@View
	private Intbox ibxNro_abortos;
	@View
	private Intbox ibxNro_cesarias;

	@View
	private Datebox dtbxAnt_gin_menarca;
	@View
	private Textbox tbxAnt_gin_e_menopaudia;
	@View
	private Listbox lbxAnt_gin_ciclo_1;

	@View
	private Textbox tbxHab_frecuencia_alcohol;
	@View
	private Textbox tbxHab_licor_alcohol;
	@View
	private Intbox tbxHab_frecuencia_tabaquismo;
	@View
	private Textbox tbxHab_ejercicio_cual;
	@View
	private Radiogroup chbHab_tabaquismo;
	@View
	private Radiogroup chbHab_alcohol;
	@View
	private Radiogroup chbHab_ejercicio;

	@View
	private Textbox tbxHab_frecuencia_tabaquismo_caja_anio;

	@View
	private Row rowEmbarazoFep;
	@View
	private Row rowEmbarazo3;
	@View
	private Row rowEmbarazo1;
	@View
	private Row rowEmbarazo2;
	@View
	private Row rowEmbarazo4;

	/**
	 * Nueva revison por sistemas.
	 * */
	@View
	private Tree tree;
	private TreeItemDinamicRevisionSistemasMacro treeItemDinamicRevisionSistemasMacro;

	/* nuevos campos fisicos */
	@View
	private Textbox tbxFisico_estado_general;
	@View
	private Textbox tbxFisico_cabeza_cara;
	@View
	private Textbox tbxFisico_ocular;
	@View
	private Textbox tbxFisico_endocrinologo;
	@View
	private Textbox tbxFisico_osteomuscular;
	@View
	private Textbox tbxFisico_cardio_pulmonar;
	@View
	private Row rowFisico_examen_mama;
	@View
	private Textbox tbxFisico_examen_mama;
	@View
	private Textbox tbxFisico_cuello;
	@View
	private Textbox tbxFisico_vacular;
	@View
	private Textbox tbxFisico_piel_fanera;
	@View
	private Textbox tbxFisico_gastointestinal;

	@View
	private Textbox tbxFisico_genitourinario;
	@View
	private Textbox tbxFisico_mental;

	@View
	private Textbox tbxAnalisis_recomendaciones;

	@View
	private Hbox box_ejercicio;
	@View
	private Hbox box_tabaquismo;
	@View
	private Hbox box_alcohol;
	@View
	private Hbox box_dietas;
	@View
	private Hbox box_factores_psicologicos;

	@View
	private Radiogroup rdbseleccion;
	@View
	private Radiogroup rdbEstado_embarazo;

	@View
	private Radio rdSeleccion1;
	@View
	private Radio rdSeleccion2;
	@View
	private Radio rdSeleccion3;

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
	@View
	private Row row13;
	@View
	private Row row14;
	@View
	private Checkbox cbxCabeza;
	@View
	private Checkbox cbxCuello;
	@View
	private Checkbox cbxToraz;
	@View
	private Checkbox cbxAbdomen;

	@View
	private Label lbLocalizacion;
	@View
	private Hbox hbxLocalizacion;
	@View
	private Listbox lbxIntensidad;
	@View
	private Label lbIntensidad;
	@View
	private Label lbEvolucion_cuadro;
	@View
	private Textbox tbxEvolucion;
	@View
	private Textbox tbxlocalizacion;
	@View
	private Label lbRelacionado;
	@View
	private Textbox tbxRelacionado;
	@View
	private Label lbCaracteristicas_Dolor;
	@View
	private Textbox tbxCaracteristicas_dolor;
	@View
	private Label lbIrradiacion;
	@View
	private Textbox tbxIrradiacion;
	@View
	private Label lbActual_presenta;
	@View
	private Textbox tbxActualmente_presenta;
	@View
	private Label lbsintomas_asociados;
	@View
	private Textbox tbxSintomas_asociados;
	@View
	private Label lbFecha_inicio;
	@View
	private Textbox tbxFecha_inicio;
	@View
	private Listbox lbxPrimera_presentacion;
	@View
	private Label lbVeces_repetido;
	@View
	private Spinner spinnerVeces_repetido;
	@View
	private Label lbMan_for_inicio;
	@View
	private Textbox tbxManera_form_inicio;
	@View
	private Label lbTratamiento_recibido;
	@View
	private Textbox tbxTratamiento_recibido;
	@View
	private Label lbEnfermedad_actual;
	@View
	private Textbox tbxParrafo_enfermedad_actual;
	@View
	private Label lbPrimera_presentacion;
	@View
	private Listbox lbxSintomatico_respiratorio;
	@View
	private Textbox tbxSintomatico_respiratorio;
	@View
	private Listbox lbxSintomatico_piel;
	@View
	private Textbox tbxSintomatico_piel;
	@View
	private Radiogroup chbVida_marital;
	@View
	private Textbox tbxFecha_vida_marital;
	@View
	private Radiogroup chbOtro_ginecostetrico;
	@View
	private Textbox tbxOtro_ginecostetrico;
	@View
	private Groupbox gbxAntecedentes_ginecostetrico;

	private String rol_usuario;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	private List<Elemento> elementosAntecentesPersonales;

	@View
	private Groupbox gbxRemisiones;

	@View
	private Textbox tbxNo_farmocologico;
	@View
	private Textbox tbxEducacional_paciente;

	@View
	private Textbox tbxPatologia_embarazo_parto;

	@View
	private Button btnMostrar_escala_glasgow;

	@View(isMacro = true)
	private GlasgowMacro macroGlasgow;
	@View(isMacro = true)
	private SignosVitalesMacro mcSignosVitales;
	@View
	private Groupbox gbxMacroGlasgow;

	@View
	private Textbox tbxPlan_seguimiento;

	@View
	private Button btnMostrar_remisiones;
	@View
	private Groupbox gbxModuloRemisiones;

	@View
	private Radiogroup rdbPsicofarmacos;
	@View
	private Radiogroup rdbOtras_adicciones;
	@View
	private Textbox tbxCual_psicofarmacos;
	@View
	private Textbox tbxCual_adicciones;

	@View
	private Listbox lbxCabezacara_list;
	@View
	private Listbox lbxOcular_list;
	@View
	private Listbox lbxCuello_list;
	@View
	private Listbox lbxCardio_pulmonar_list;
	@View
	private Listbox lbxAdomen_list;
	@View
	private Listbox lbxGenitourinario_list;
	@View
	private Listbox lbxExamen_mama_list;
	@View
	private Listbox lbxEndocrino_list;
	@View
	private Listbox lbxOsteomuscular_list;
	@View
	private Listbox lbxVascular_list;
	@View
	private Listbox lbxPiel_faneras_list;

	@View
	private Hbox box_Psicofarmacos;
	@View
	private Hbox box_Otras_adicciones;

	@View
	private Radiogroup rgpControlado_embarazo;
	@View
	private Checkbox chkEn_puesto_salud;
	@View
	private Checkbox chkPor_especialistas;
	@View
	private Checkbox chkEn_consulta_institucional;
	@View
	private Checkbox chkPor_medicina_general;
	@View
	private Intbox ibxNro_consultas;
	@View
	private Checkbox chkEn_consulta_particular;
	@View
	private Checkbox chkPor_partera;

	@View
	private Radiogroup rgpHistoria_sangrado_vaginal;
	@View
	private Radiogroup rgpCefaleas;
	@View
	private Radiogroup rgpEdema;
	@View
	private Radiogroup rgpHepigastralgia;
	@View
	private Radiogroup rgpVomitos;
	@View
	private Radiogroup rgpTinitus;
	@View
	private Radiogroup rgpEscotomas;
	@View
	private Radiogroup rgpInfeccion_vaginal;
	@View
	private Radiogroup rgpInfeccion_urinaria;
	@View
	private Groupbox gbxEmbarazo_actual;
	@View
	private Groupbox gbxHabitos_paciente;
	@View
	private Groupbox gbxRevision_sistemas;
	// @View
	// private Groupbox gbxEmbarazosAnteriores;
	@View
	private Groupbox groupboxConsejeria;
	@View
	private Toolbarbutton toolbarbuttonCargar_signos;
	@View
	private Textbox tbxDescripcion_menarca;
	@View
	private Textbox tbxOtro_ciclo;
	@View
	private Row rowOtros_ciclos;
	@View
	private Radiogroup chbAnt_gin_tiene_citologia;
	@View
	private Datebox dtbxAnt_gin_fecha_ultima_citologia;
	@View
	private Textbox tbxAnt_gin_citologia_resultado;

	private final String[] idsExc = { "lbxRelacion",
			"lbxEscolaridad_acompanante", "tbxValue", "tbxNro_identificacion",
			"tbxNro_ingreso", "tbxTipo_hc", "tbxEdad", "tbxArea_int",
			"lbxParameter", "lbxTipo_disnostico", "northEditar",
			"btnLimpiar_prestador", "lbxSintomatico_iratorio",
			"lbxSintomatico_piel", "lbxCabezacara_list", "lbxOcular_list",
			"lbxOtorrrino_list", "lbxCuello_list", "lbxCardio_pulmonar_list",
			"lbxAdomen_list", "lbxGenitourinario_list", "lbxExamen_mama_list",
			"lbxEndocrino_list", "lbxOsteomuscular_list", "lbxVascular_list",
			"lbxPiel_faneras_list", "lbxNeurologico_list", "lbxMental_list",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "gbxPartograma",
			"toolbarbuttonCargar_signos", "rowOtros_ciclos",
			"lbxSistema_nervioso_list", "divModuloRemisiones_externas",
			"infoPacientes" };

	// private Component remisionesAction;

	@View
	private Textbox tbxSituacion_uterina;
	@View
	private Textbox tbxPresentacion_uterina;
	@View
	private Textbox tbxPosicion_presentacion;
	@View
	private Textbox tbxVposicion_presentacion;
	@View
	private Textbox tbxTono_uterina;
	@View
	private Textbox tbxECF_uterina;

	@View
	private Listbox lbxDilatacion_list;
	@View
	private Doublebox dbxBorramiento;
	@View
	private Textbox tbxEstacion;
	@View
	private Textbox tbxMembrana;
	@View
	private Toolbarbutton btnAdmitir_hijo;

	@View
	private Groupbox gbxPartograma;

	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;

	private Media file_partograma;

	@View
	private Toolbarbutton btnAdjuntar_partograma;
	@View
	private Toolbarbutton btnEliminar_partograma;
	@View
	private Toolbarbutton btnMostrar_partograma;

	private String url_partograma_anterior;

	@View
	private Textbox tbxSistema_nervioso;

	@View
	private Listbox lbxSistema_nervioso_list;

	@View
	private Textbox tbxFarmocologico;

	@Override
	public void init() throws Exception {
		loadComponents();
		listarCombos();
		mcSignosVitales.setZkWindow(this);
		mcSignosVitales.getLabelCreatinina().setVisible(false);
		mcSignosVitales.getDoubleboxCreatinina().setVisible(false);
		mcSignosVitales.getLabelTFG().setVisible(false);
		mcSignosVitales.getDoubleboxTFG().setVisible(false);
		macroImpresion_diagnostica.inicializarMacro(this,
				admision_seleccionada, IVias_ingreso.URGENCIA);
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision_seleccionada = (Admision) map
					.get(IVias_ingreso.ADMISION_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);

			if (map.get(IVias_ingreso.PADRE) instanceof Modulo_asistencialAction) {
				padre = (Modulo_asistencialAction) map.get(IVias_ingreso.PADRE);
			} else if (map.get(IVias_ingreso.PADRE) instanceof Consulta_historiasAction) {
				padre = (Consulta_historiasAction) map.get(IVias_ingreso.PADRE);
			}

			if (map.containsKey("via_ingreso")) {
				via_ingreso = (String) map.get("via_ingreso");
			}

			if (map.containsKey(IVias_ingreso.TIPO_URGENCIA)) {
				tipo_urgencia = (String) map.get(IVias_ingreso.TIPO_URGENCIA);
			}

			if (admision_seleccionada.getPaciente().getSexo().equals("M")) {

				rowFisico_examen_mama.setVisible(false);
				gbxAntecedentes_ginecostetrico.setVisible(false);
			} else {
				gbxAntecedentes_ginecostetrico.setVisible(true);
				rowFisico_examen_mama.setVisible(true);
			}

			macroRemision_interna.deshabilitarCheck(admision_seleccionada,
					admision_seleccionada.getVia_ingreso());

		}
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		rol_usuario = (String) request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO);
	}

	private void loadComponents() {
		cargarAntecedentesPersonales(new HashMap<String, Antecedentes_personales>());
		treeItemDinamicRevisionSistemasMacro = new TreeItemDinamicRevisionSistemasMacro(
				tree, this, "RPS");
		treeItemDinamicRevisionSistemasMacro.renderizarContenido();
	}

	private void cargarAntecedentesPersonales(
			Map<String, Antecedentes_personales> mapaAntecedentesPersonales) {
		elementosAntecentesPersonales = getServiceLocator()
				.getElementoService().listar("ante_personales");
		if (gridAntecedentes_personales.getRows() != null) {
			gridAntecedentes_personales.getRows().getChildren().clear();
		}
		hlayoutAntecedentes_personales.getChildren().clear();
		if (elementosAntecentesPersonales.isEmpty()) {
			gridAntecedentes_personales.setVisible(false);
		} else {
			Rows rowsFilas = gridAntecedentes_personales.getRows();
			if (rowsFilas == null) {
				rowsFilas = new Rows();
				gridAntecedentes_personales.appendChild(rowsFilas);
			}

			for (int i = 0; i < elementosAntecentesPersonales.size(); i++) {
				Row fila = new Row();
				Elemento antecedente = elementosAntecentesPersonales.get(i);
				crearCeldaAntecedentesPersonales(antecedente, fila,
						mapaAntecedentesPersonales.get(antecedente.getCodigo()));
				i++;
				if (i < elementosAntecentesPersonales.size()) {
					antecedente = elementosAntecentesPersonales.get(i);
					crearCeldaAntecedentesPersonales(antecedente, fila,
							mapaAntecedentesPersonales.get(antecedente
									.getCodigo()));
				}
				rowsFilas.appendChild(fila);
			}

		}

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(gridAntecedentes_personales,
					true, new String[] { "northEditar" });
		}
	}

	// metodo que oculta groubox embarazo cuando el paciente es masculino y
	// menor de edad
	public void OcultarEmbarazoActual() {
		if (admision_seleccionada.getVia_ingreso().equals(
				IVias_ingreso.HOSPITALIZACIONES)) {
			//log.info("===> via ingreso " + IVias_ingreso.HOSPITALIZACIONES);
			int edad = Integer.parseInt(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(admision_seleccionada.getPaciente()
									.getFecha_nacimiento()), "1", false));
			if (admision_seleccionada.getPaciente().getSexo().equals("F")) {
				if (edad >= 9 && edad <= 56) {
					//log.info("====> prueba " + edad);
					gbxEmbarazo_actual.setVisible(true);
				}
			} else {
				gbxEmbarazo_actual.setVisible(false);
			}
		}
	}

	private void crearCeldaAntecedentesPersonales(final Elemento antecedente,
			Row fila, Antecedentes_personales antecedentes_personales) {
		Cell celda = new Cell();
		celda.appendChild(new Label(antecedente.getDescripcion()));
		fila.appendChild(celda);

		celda = new Cell();
		celda.setColspan(2);
		final Radiogroup radiogroup = new Radiogroup();
		radiogroup
				.setId("radiogroup_ant_personales_" + antecedente.getCodigo());
		Radio radio = new Radio();
		radio.setValue("S");
		radiogroup.appendChild(radio);

		Space space = new Space();
		space.setWidth("5px");
		radiogroup.appendChild(space);

		radio = new Radio();
		radio.setValue("N");
		radiogroup.appendChild(radio);

		celda.appendChild(radiogroup);
		radiogroup.setSelectedIndex(1);
		fila.appendChild(celda);

		celda = Utilidades.obtenerCell("", Textbox.class, true, false);
		final Textbox tbxObservaciones = (Textbox) celda.getFirstChild();
		tbxObservaciones.setId("textbox_ant_personales_"
				+ antecedente.getCodigo());

		tbxObservaciones.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						hlayoutAntecedentes_personales.getChildren().clear();
						final Popup popupObservaciones = generarPopupObservaciones(
								antecedente, tbxObservaciones,
								hlayoutAntecedentes_personales);
						popupObservaciones.open(tbxObservaciones, "after_end");
						if (popupObservaciones
								.hasFellow("textbox_observaciones_ant_per_"
										+ antecedente.getCodigo())) {
							((Textbox) popupObservaciones
									.getFellow("textbox_observaciones_ant_per_"
											+ antecedente.getCodigo()))
									.setFocus(true);
						}
					}
				});

		tbxObservaciones.setVisible(false);

		if (antecedentes_personales != null) {
			Utilidades.seleccionarRadio(radiogroup,
					antecedentes_personales.getRespuesta());
			if (!antecedentes_personales.getCodigo_antecente().equals("32"))
				tbxObservaciones.setValue(antecedentes_personales
						.getObservacion());
			else
				mostrarXmlTextbox(tbxObservaciones,
						ConvertidorXmlToMap
								.convertirToMap(antecedentes_personales
										.getObservacion()));
			if (antecedentes_personales.getRespuesta().equals("S")) {
				tbxObservaciones.setVisible(true);
			} else {
				tbxObservaciones.setVisible(false);
				tbxObservaciones.setValue("");
			}
		}

		radiogroup.addEventListener(Events.ON_CHECK,
				new EventListener<CheckEvent>() {

					@Override
					public void onEvent(CheckEvent event) throws Exception {
						Checkbox checkbox = (Checkbox) event.getTarget();
						String valor = checkbox.getValue();
						if (valor.equals("S")) {
							final Popup popupObservaciones = generarPopupObservaciones(
									antecedente, tbxObservaciones,
									hlayoutAntecedentes_personales);
							tbxObservaciones.setVisible(true);
							popupObservaciones.open(tbxObservaciones,
									"after_end");
							if (popupObservaciones
									.hasFellow("textbox_observaciones_ant_per_"
											+ antecedente.getCodigo())) {
								((Textbox) popupObservaciones
										.getFellow("textbox_observaciones_ant_per_"
												+ antecedente.getCodigo()))
										.setFocus(true);
							}

							checkbox.addEventListener(Events.ON_CLICK,
									new EventListener<Event>() {

										@Override
										public void onEvent(Event event)
												throws Exception {
											hlayoutAntecedentes_personales
													.getChildren().clear();
											Popup popupObservaciones_aux = generarPopupObservaciones(
													antecedente,
													tbxObservaciones,
													hlayoutAntecedentes_personales);
											tbxObservaciones.setVisible(true);
											popupObservaciones_aux.open(
													tbxObservaciones,
													"after_end");
											if (popupObservaciones_aux.hasFellow("textbox_observaciones_ant_per_"
													+ antecedente.getCodigo())) {
												((Textbox) popupObservaciones_aux.getFellow("textbox_observaciones_ant_per_"
														+ antecedente
																.getCodigo()))
														.setFocus(true);
											}
										}

									});

						} else {
							tbxObservaciones.setVisible(false);
							hlayoutAntecedentes_personales.getChildren()
									.clear();
							Iterator<EventListener<? extends Event>> eventos = radiogroup
									.getItemAtIndex(0)
									.getEventListeners(Events.ON_CLICK)
									.iterator();
							while (eventos.hasNext()) {
								radiogroup.getItemAtIndex(0)
										.removeEventListener(Events.ON_CLICK,
												eventos.next());
							}
						}
					}

				});

		fila.appendChild(celda);

	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbx_conf_fur, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxIntensidad, true,
				getServiceLocator());
		Utilidades
				.listarElementoListbox(lbxRelacion, true, getServiceLocator());
		Utilidades.listarNivel_educativo(lbxEscolaridad_acompanante, true,
				getServiceLocator());
		lbxDilatacion_list.getChildren().clear();
		for (int i = 0; i <= 10; i++) {
			lbxDilatacion_list.appendItem(i + " cm", i + "");
		}
		lbxDilatacion_list.setSelectedIndex(0);
	}
	
	 private void cargarSignosVitalesEnfermera(His_triage his_triage) {
			if(his_triage != null){
				Sigvitales sigvitales = new Sigvitales();
				sigvitales.setCreatinina_cerica(his_triage
						.getCreatinina_cerica());
				sigvitales.setFrecuencia_cardiaca(his_triage
						.getFrecuencia_cardiaca());
				sigvitales.setFrecuencia_respiratoria(his_triage
						.getFrecuencia_respiratoria());
				sigvitales.setImc(his_triage.getImc());
				sigvitales.setPerimetro_cefalico(his_triage
						.getPerimetro_cefalico());
				sigvitales.setPerimetro_toraxico(his_triage
						.getPerimetro_toraxico());
				sigvitales.setPeso(his_triage.getPeso());
				sigvitales.setSuperficie_corporal(his_triage
						.getSuperficie_corporal());
				sigvitales.setTa_diastolica(his_triage.getTa_diastolica());
				sigvitales.setTa_sistolica(his_triage.getTa_sistolica());
				sigvitales.setTa_media(his_triage.getTa_media());
				sigvitales.setTalla(his_triage.getTalla());
				sigvitales.setTemparatura(his_triage.getTemparatura());
				sigvitales.setTfg(his_triage.getTfg());
				mcSignosVitales.mostrarSigvitalesInformacion(sigvitales);
			}
		}

	public void cargarSignosVitalesEnfermera() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		enfermera_signos.setNro_identificacion(admision_seleccionada
				.getNro_identificacion());
		enfermera_signos = getServiceLocator().getServicio(
				Enfermera_signosService.class).consultar(enfermera_signos);
		if (enfermera_signos != null) {
			Sigvitales sigvitales = new Sigvitales();
			sigvitales.setCreatinina_cerica(enfermera_signos
					.getCreatinina_serica());
			sigvitales.setFrecuencia_cardiaca(enfermera_signos
					.getFrecuencia_cardiaca());
			sigvitales.setFrecuencia_respiratoria(enfermera_signos
					.getFrecuencia_respiratoria());
			sigvitales.setImc(enfermera_signos.getImc());
			sigvitales.setPerimetro_cefalico(enfermera_signos
					.getPerimetro_cefalico());
			sigvitales.setPerimetro_toraxico(enfermera_signos
					.getPerimetro_toraxico());
			sigvitales.setPeso(enfermera_signos.getPeso());
			sigvitales.setSuperficie_corporal(enfermera_signos
					.getSuperficie_corporal());
			sigvitales.setTa_diastolica(enfermera_signos.getTa_diastolica());
			sigvitales.setTa_sistolica(enfermera_signos.getTa_sistolica());
			sigvitales.setTa_media(enfermera_signos.getTa_media());
			sigvitales.setTalla(enfermera_signos.getTalla());
			sigvitales.setTemparatura(enfermera_signos.getTemperatura());
			sigvitales.setTfg(enfermera_signos.getTfg());
			mcSignosVitales.mostrarSigvitalesInformacion(sigvitales);
		}
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

	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(this, sw, idsExc);
		desabilitarRadios(chbAnt_gin_tiene_citologia, sw);

		if (!sw) {
			((Button) getFellow("btGuardar")).setDisabled(false);
			((Button) getFellow("btGuardar")).setImage("/images/Save16.gif");
		} else {
			((Button) getFellow("btGuardar")).setDisabled(true);
			((Button) getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}

		if (rol_usuario.equals(IConstantes.ROL_MEDICO_URGENCIAS)) {
			bandboxPrestador.setDisabled(true);
		} else {
			bandboxPrestador.setDisabled(sw);
		}
	}

	private void desabilitarRadios(Radiogroup radiogroup, boolean sw) {
		for (Object radio : radiogroup.getChildren()) {
			if (radio instanceof Radio)
				((Radio) radio).setDisabled(sw);
		}
	}

	public void seleccion(Radiogroup radiogroup) {
		try {

			if (radiogroup.getSelectedItem().getValue().equals("1")) {
				row2.setVisible(false);
				row3.setVisible(false);
				row4.setVisible(false);
				row5.setVisible(false);
				row6.setVisible(false);
				row7.setVisible(false);
				row8.setVisible(false);
				row9.setVisible(false);
				row10.setVisible(false);
				row11.setVisible(false);
				row12.setVisible(false);
				row13.setVisible(false);
				row14.setVisible(false);
				tbxEnfermedad_actual.setVisible(true);
				lbEnfermedad_actual.setVisible(true);
				// lbxRepetidos.setVisible(false);
				// lbRepetido.setVisible(false);
				lbPrimera_presentacion.setVisible(false);
				lbxPrimera_presentacion.setVisible(false);
				lbFecha_inicio.setVisible(false);
				tbxFecha_inicio.setVisible(false);
				lbMan_for_inicio.setVisible(false);
				tbxManera_form_inicio.setVisible(false);
				lbsintomas_asociados.setVisible(false);
				tbxSintomas_asociados.setVisible(false);
				lbTratamiento_recibido.setVisible(false);
				tbxTratamiento_recibido.setVisible(false);
				lbActual_presenta.setVisible(false);
				tbxActualmente_presenta.setVisible(false);
				lbIrradiacion.setVisible(false);
				tbxIrradiacion.setVisible(false);
				lbLocalizacion.setVisible(false);
				hbxLocalizacion.setVisible(false);
				lbCaracteristicas_Dolor.setVisible(false);
				tbxCaracteristicas_dolor.setVisible(false);
				lbRelacionado.setVisible(false);
				tbxRelacionado.setVisible(false);
				lbEvolucion_cuadro.setVisible(false);
				tbxEvolucion.setVisible(false);
				tbxlocalizacion.setVisible(false);
				lbxIntensidad.setVisible(false);
				lbIntensidad.setVisible(false);
				tbxParrafo_enfermedad_actual.setVisible(false);

			} else if (radiogroup.getSelectedItem().getValue().equals("2")) {
				row2.setVisible(true);
				row3.setVisible(true);
				row4.setVisible(true);
				row5.setVisible(true);
				row6.setVisible(true);
				row7.setVisible(true);
				row8.setVisible(true);
				row9.setVisible(true);
				row10.setVisible(false);
				row11.setVisible(false);
				row12.setVisible(false);
				row13.setVisible(false);
				row14.setVisible(true);
				tbxEnfermedad_actual.setVisible(false);
				lbEnfermedad_actual.setVisible(true);
				// lbxRepetidos.setVisible(true);
				// lbRepetido.setVisible(true);
				lbPrimera_presentacion.setVisible(true);
				lbxPrimera_presentacion.setVisible(true);
				lbFecha_inicio.setVisible(true);
				tbxFecha_inicio.setVisible(true);
				lbMan_for_inicio.setVisible(true);
				tbxManera_form_inicio.setVisible(true);
				lbsintomas_asociados.setVisible(true);
				tbxSintomas_asociados.setVisible(true);
				lbTratamiento_recibido.setVisible(true);
				tbxTratamiento_recibido.setVisible(true);
				lbActual_presenta.setVisible(true);
				tbxActualmente_presenta.setVisible(true);
				lbIrradiacion.setVisible(false);
				tbxIrradiacion.setVisible(false);
				lbLocalizacion.setVisible(false);
				hbxLocalizacion.setVisible(false);
				lbCaracteristicas_Dolor.setVisible(false);
				tbxCaracteristicas_dolor.setVisible(false);
				lbRelacionado.setVisible(true);
				tbxRelacionado.setVisible(true);
				lbEvolucion_cuadro.setVisible(true);
				tbxEvolucion.setVisible(true);
				tbxlocalizacion.setVisible(true);
				lbxIntensidad.setVisible(false);
				lbIntensidad.setVisible(false);
				tbxParrafo_enfermedad_actual.setVisible(true);

			} else if (radiogroup.getSelectedItem().getValue().equals("3")) {
				row2.setVisible(true);
				row3.setVisible(true);
				row4.setVisible(true);
				row5.setVisible(true);
				row6.setVisible(true);
				row7.setVisible(true);
				row8.setVisible(true);
				row9.setVisible(true);
				row10.setVisible(true);
				row11.setVisible(true);
				row12.setVisible(true);
				row13.setVisible(true);
				row14.setVisible(true);
				tbxEnfermedad_actual.setVisible(false);
				lbEnfermedad_actual.setVisible(true);
				// lbxRepetidos.setVisible(true);
				// lbRepetido.setVisible(true);
				lbPrimera_presentacion.setVisible(true);
				lbxPrimera_presentacion.setVisible(true);
				lbFecha_inicio.setVisible(true);
				tbxFecha_inicio.setVisible(true);
				lbMan_for_inicio.setVisible(true);
				tbxManera_form_inicio.setVisible(true);
				lbsintomas_asociados.setVisible(true);
				tbxSintomas_asociados.setVisible(true);
				lbTratamiento_recibido.setVisible(true);
				tbxTratamiento_recibido.setVisible(true);
				lbActual_presenta.setVisible(true);
				tbxActualmente_presenta.setVisible(true);
				lbIrradiacion.setVisible(true);
				tbxIrradiacion.setVisible(true);
				lbLocalizacion.setVisible(true);
				hbxLocalizacion.setVisible(true);
				lbCaracteristicas_Dolor.setVisible(true);
				tbxCaracteristicas_dolor.setVisible(true);
				lbRelacionado.setVisible(true);
				tbxRelacionado.setVisible(true);
				lbEvolucion_cuadro.setVisible(true);
				tbxEvolucion.setVisible(true);
				tbxlocalizacion.setVisible(true);
				lbxIntensidad.setVisible(true);
				lbIntensidad.setVisible(true);
				tbxParrafo_enfermedad_actual.setVisible(true);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Prestadores> list = getServiceLocator()
					.getPrestadoresService().listar(parameters);

			lbx.getItems().clear();

			for (Prestadores dato : list) {

				Especialidad especialidad = new Especialidad();
				especialidad.setCodigo(dato.getCodigo_especialidad());
				especialidad = getServiceLocator().getEspecialidadService()
						.consultar(especialidad);

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombres()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellidos()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						(especialidad != null ? especialidad.getNombre() : "")));
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

	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			bandboxPrestador.setValue("");
			tbxNomPrestador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			bandboxPrestador.setValue(dato.getNro_identificacion());
			tbxNomPrestador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		bandboxPrestador.close();
	}

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
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

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

	private Map<String, Antecedentes_personales> obtenerMapaAntecedentesPersonales(
			Hisc_urgencia hisc_urgencia) {
		Map<String, Antecedentes_personales> mapaAntecedentesPersonales = new HashMap<String, Antecedentes_personales>();
		Map map = new HashMap();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("codigo_historia", hisc_urgencia.getCodigo_historia());

		//log.info(">>>>>>>>>>1 cargando antecedentes");
		List<Antecedentes_personales> listaAntecedentes_personales = getServiceLocator()
				.getAntecedentesPersonalesService().listar(map);
		//log.info(">>>>>>>>>>2 " + listaAntecedentes_personales.size());
		for (Antecedentes_personales antecedentes_personales : listaAntecedentes_personales) {
			mapaAntecedentesPersonales.put(
					antecedentes_personales.getCodigo_antecente(),
					antecedentes_personales);
		}
		return mapaAntecedentesPersonales;
	}

	private List<Revision_sistema> obtenerListadoRevisionSistema(
			Hisc_urgencia hisc_urgencia) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("codigo_historia", hisc_urgencia.getCodigo_historia());

		return getServiceLocator().getRevision_sistemaService().listar(map);

	}

	public void calcularTabaquismo() {
		Integer fecuencia = tbxHab_frecuencia_tabaquismo.getValue();
		if (fecuencia != null) {
			int resultado = Math.round(fecuencia.intValue() * 365);
			tbxHab_frecuencia_tabaquismo_caja_anio.setText(resultado + "");
		} else {
			tbxHab_frecuencia_tabaquismo_caja_anio.setText("");
		}
	}

	public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
			throws Exception {
		//log.info("ejecutando metodo @accionForm()");
		tbxAccion.setValue(opciones_formulario.toString());

		admision_seleccionada.setCodigo_medico(usuarios.getCodigo());

		if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
			onOpcionFormularioRegistrar();
		} else if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)
				|| opciones_formulario.equals(OpcionesFormulario.MOSTRAR)) {
			groupboxConsultar.setVisible(false);
			groupboxEditar.setVisible(true);

			if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {

				mostrarDatosAnterior(dato);
				FormularioUtil.deshabilitarComponentes(groupboxEditar, false,
						idsExc);
				lbxSintomatico_respiratorio.setDisabled(false);
				lbxSintomatico_piel.setDisabled(false);
				lbxCabezacara_list.setDisabled(false);
				lbxOcular_list.setDisabled(false);
				lbxCuello_list.setDisabled(false);
				lbxCardio_pulmonar_list.setDisabled(false);
				lbxAdomen_list.setDisabled(false);
				lbxGenitourinario_list.setDisabled(false);
				lbxExamen_mama_list.setDisabled(false);
				lbxEndocrino_list.setDisabled(false);
				lbxOsteomuscular_list.setDisabled(false);
				lbxVascular_list.setDisabled(false);
				lbxPiel_faneras_list.setDisabled(false);
				lbxRelacion.setDisabled(false);
				lbxEscolaridad_acompanante.setDisabled(false);
				lbxSistema_nervioso_list.setDisabled(false);
			} else {
				mostrarDatos(dato);
			}
		} else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
			onOpcionFormularioConsultar();
		}
	}

	private void onOpcionFormularioRegistrar() throws Exception {
		//log.info("ejecutando metodo @onOpcionFormularioRegistrar()");
		groupboxConsultar.setVisible(false);
		groupboxEditar.setVisible(true);
		seleccion(rdbseleccion);
		limpiarDatos();
		if (admision_seleccionada != null) {
			this.nro_ingreso_admision = admision_seleccionada.getNro_ingreso();
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			onMostrarModuloRemisiones();
			cargarPrestador(admision_seleccionada.getCodigo_medico());
		}
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsultar.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
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
			parameters.put("via_ingreso", via_ingreso);
			parameters.put("tipo_urgencia", tipo_urgencia);

			if (admision_seleccionada != null) {
				parameters.put("nro_identificacion",
						admision_seleccionada.getNro_identificacion());
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

			getServiceLocator().getServicio(Hisc_urgenciaService.class)
					.setLimit("limit 25 offset 0");

			List<Hisc_urgencia> lista_datos = getServiceLocator().getServicio(
					Hisc_urgenciaService.class).listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_urgencia hisc_urgencia : lista_datos) {
				rowsResultado.appendChild(crearFilas(hisc_urgencia, this));
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

		final Hisc_urgencia hisc_urgencia = (Hisc_urgencia) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_urgencia.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_urgencia.getNro_identificacion() + ""));

		fila.appendChild(new Label(
				admision_seleccionada.getPaciente().getNombre1()
						+ (admision_seleccionada.getPaciente().getNombre2()
								.isEmpty() ? "" : " "
								+ admision_seleccionada.getPaciente()
										.getNombre2()) + " "
						+ admision_seleccionada.getPaciente().getApellido1()
						+ " "
						+ admision_seleccionada.getPaciente().getApellido2()
						+ ""));

		Datebox datebox = new Datebox(hisc_urgencia.getFecha_ingreso());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		datebox.setInplace(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_urgencia.getTipo_historia().equals(
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
						groupboxEditar.setVisible(true);
						groupboxConsultar.setVisible(false);
						mostrarDatos(hisc_urgencia);
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
													eliminarDatos(hisc_urgencia);
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

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_urgencia hisc_urgencia = (Hisc_urgencia) obj;
		try {
			int result = getServiceLocator().getServicio(
					Hisc_urgenciaService.class).eliminar(hisc_urgencia);
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

	public void cargarPrestador(String codigo_medico) throws Exception {
		//log.info("ejecutando metodo @cargarPrestador()");
		if (rol_usuario.equals(IConstantes.ROL_MEDICO_URGENCIAS)) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(codigo_medico);
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			if (prestadores == null) {
				throw new Exception(
						"Usuario no esta creado en el modulo de prestadore, actualize informacion de usuario");
			}
			bandboxPrestador.setValue(prestadores.getNro_identificacion());
			tbxNomPrestador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());
		} else {
			if (admision_seleccionada != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(codigo_empresa);
				prestadores.setCodigo_sucursal(codigo_sucursal);
				prestadores.setNro_identificacion(codigo_medico);
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				if (prestadores != null) {
					bandboxPrestador.setValue(prestadores
							.getNro_identificacion());
					tbxNomPrestador.setValue(prestadores.getNombres() + " "
							+ prestadores.getApellidos());
				}
			}
		}

	}

	public void cargarDx() throws Exception {

	}

	private void limpiarAntecedentes() {
		
		bandboxAnte_fam_cancer.setValue("");
		bandboxAnte_fam_diabetes.setValue("");
		bandboxAnte_fam_dislipidemia.setValue("");
		bandboxAnte_fam_ecv.setValue("");
		bandboxAnte_fam_enf_coronaria.setValue("");
		bandboxAnte_fam_enf_mental.setValue("");
		bandboxAnte_fam_hematologia.setValue("");
		bandboxAnte_fam_hipertension.setValue("");
		bandboxAnte_fam_muerte_im_acv.setValue("");
		bandboxAnte_fam_nefropatias.setValue("");
		bandboxAnte_fam_obesos.setValue("");
		bandboxAnte_fam_otros.setValue("");
	}

	private void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExc);
		dtbxAnt_gin_fecha_espectante_parto.setValue(null);
		dtbxAnt_gin_menarca.setValue(null);
		dtbxFum.setValue(null);
		cargarDx();
		limpiarAntecedentes();
		box_alcohol.setVisible(false);
		box_tabaquismo.setVisible(false);
		box_ejercicio.setVisible(false);
		box_dietas.setVisible(false);
		box_factores_psicologicos.setVisible(false);
		box_Psicofarmacos.setVisible(false);
		box_Otras_adicciones.setVisible(false);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		infoPacientes.validarInformacionPaciente();
		boolean valido = true;
		;
		// String mensaje = "";
		if (valido)
			valido = remisiones_externasAction.validarRemision();

		if (valido) {
			FormularioUtil.validarCamposObligatorios(tbxMotivo_consulta,
					tbxEnfermedad_actual, tbxParrafo_enfermedad_actual);
		}

		// if (valido)
		// valido = validarFormulaGinecobsterica();

		return valido;
	}

	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				Hisc_urgencia hisc_urgencia = getBean();
				hisc_urgencia.setTipo_historia(tipo_historia);
				hisc_urgencia.setCodigo_historia_anterior(hisc_urgencia
						.getCodigo_historia());
				hisc_urgencia.setFecha_cierre(new Timestamp((new Date())
						.getTime()));
				hisc_urgencia.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());
				hisc_urgencia.setVia_ingreso(via_ingreso);

				List<Antecedentes_personales> listadoAntecedentes = obtenerAntecedentesPersonales();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("hisc_urgencia", hisc_urgencia);
				datos.put("sigvitales", mcSignosVitales.obtenerSigvitales());
				datos.put("listadoAntecedentes", listadoAntecedentes);
				datos.put("admision", admision_seleccionada);
				datos.put("listadoRevisiones",
						treeItemDinamicRevisionSistemasMacro
								.getElementos_seleccionados());
				datos.put("accion", tbxAccion.getValue());

				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();
				datos.put("impresion_diagnostica", impresion_diagnostica);

				Anexo9_entidad anexo9_entidad = remisiones_externasAction
						.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);

				datos.put(
						"afectar_hoja_gasto",
						parametros_empresa != null ? parametros_empresa
								.getAfectar_hoja_gato_automatica() : "N");

				String url_partogramas = Labels.getLabel("app.partogramas");

				if (file_partograma != null) {
					datos.put("url_partogramas", url_partogramas);
					datos.put("file_partograma",
							file_partograma.getStreamData());
					datos.put("formato_partograma", file_partograma.getFormat());
				}

				datos.put("via_ingreso", via_ingreso);

				datos.put("url_partograma_anterior", url_partograma_anterior);

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);

				datos.put("tipo_urgencia", tipo_urgencia);

				datos.put("usuarios_nombre", usuarios.getNombres() + " "
						+ usuarios.getApellidos());
				datos.put("usuarios_rol", usuarios.getRol());
				datos.put("usuarios_cel", usuarios.getCelular());

				getServiceLocator().getServicio(Hisc_urgenciaService.class)
						.guardarDatos(datos);

				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}

				url_partograma_anterior = hisc_urgencia.getUrl_partograma();

				tbxAccion.setValue("modificar");

				infoPacientes.setCodigo_historia(hisc_urgencia
						.getCodigo_historia());

				if (tipo_urgencia
						.equals(IVias_ingreso.TIPO_URGENCIA_OBSTETRICA)) {
					btnAdmitir_hijo.setVisible(true);
				}

				actualizarAutorizaciones(admision_seleccionada,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision_seleccionada,
						getInformacionClinica(), this);

				MensajesUtil.mensajeInformacion("Informacion ...",
						"Los datos se guardaron satisfactoriamente");

				padre.cargarEpicrisis();

			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	public void mostrarDatos(Object obj) throws Exception {
		//log.info("ejecutando metodo @mostrarDatos()");
		Hisc_urgencia hisc_urgencia = (Hisc_urgencia) obj;
		try {
			this.nro_ingreso_admision = hisc_urgencia.getNro_ingreso();
			this.via_ingreso = hisc_urgencia.getVia_ingreso();

			tbxNro_identificacion.setValue(hisc_urgencia
					.getNro_identificacion());

			infoPacientes.setFecha_inicio(hisc_urgencia.getFecha_ingreso());
			infoPacientes
					.setFecha_cierre(true, hisc_urgencia.getFecha_cierre());
			infoPacientes
					.setCodigo_historia(hisc_urgencia.getCodigo_historia());

			cargarPrestador(hisc_urgencia.getCodigo_prestador());

			onMostrarModuloRemisiones();
			initMostrar_datos(hisc_urgencia);

			// cargarInformacion_paciente();

			cargarRemisionInterna(hisc_urgencia);

			// Acompañante
			tbxAcompanante.setValue(hisc_urgencia.getConyugue());
			dbxIdentificacion_acompanante.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_urgencia.getCedula_conyugue()));
			Utilidades.seleccionarListitem(lbxRelacion,
					hisc_urgencia.getRelacion_acompanante());
			// ibxEdad_acompanante.setValue(ConvertidorDatosUtil.convertirDato(hisc_urgencia.getEdad_conyugue()));
			ibxEdad_acompanante.setValue(hisc_urgencia.getEdad_conyugue());
			Utilidades.seleccionarListitem(lbxEscolaridad_acompanante,
					hisc_urgencia.getEscolaridad_conyugue());
			dbxTelefono_acompanante.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_urgencia.getTelefono_conyugue()));

			tbxMotivo_consulta.setValue(hisc_urgencia.getMotivo_consulta());
			tbxEnfermedad_actual.setValue(hisc_urgencia.getEnfermedad_actual());
			tbxDescripcion_menarca
					.setValue(hisc_urgencia.getDes_vida_marital());

			if (hisc_urgencia.getSelect_enfer_act().equals("1")) {
				rdSeleccion1.setSelected(true);

			}
			if (hisc_urgencia.getSelect_enfer_act().equals("2")) {
				rdSeleccion2.setSelected(true);
				tbxParrafo_enfermedad_actual.setValue(hisc_urgencia
						.getEnfermedad_actual());
			}
			if (hisc_urgencia.getSelect_enfer_act().equals("3")) {
				rdSeleccion3.setSelected(true);
				tbxParrafo_enfermedad_actual.setValue(hisc_urgencia
						.getEnfermedad_actual());
			}
			seleccion((Radiogroup) getFellow("rdbseleccion"));

			StringTokenizer stringTokenizer = new StringTokenizer(
					hisc_urgencia.getPrimera_presentacion(), "|");

			Utilidades.seleccionarListitem(
					lbxPrimera_presentacion,
					stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "");
			spinnerVeces_repetido
					.setText(stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "1");

			tbxCaracteristicas_dolor.setValue(hisc_urgencia
					.getCaracteristicas_del_dolor());

			tbxIrradiacion.setValue(hisc_urgencia.getIrradiacion());
			tbxManera_form_inicio.setValue(hisc_urgencia
					.getManera_forma_de_inicio());
			tbxRelacionado.setValue(hisc_urgencia.getRelacionado_con());
			tbxEvolucion.setValue(hisc_urgencia.getEvolucion_del_cuadro());
			tbxlocalizacion.setValue(hisc_urgencia.getLocalizacion());
			tbxSintomas_asociados
					.setValue(hisc_urgencia.getSintoma_asociados());
			tbxTratamiento_recibido.setValue(hisc_urgencia
					.getTratamientos_recibidos());
			tbxActualmente_presenta.setValue(hisc_urgencia
					.getActualmente_se_presenta_con());
			Utilidades.seleccionarListitem(lbxIntensidad,
					hisc_urgencia.getIntensidad());

			cbxAbdomen.setChecked(hisc_urgencia.getAbdomen_enf_actual());
			cbxCabeza.setChecked(hisc_urgencia.getCabeza_enf_actual());
			cbxToraz.setChecked(hisc_urgencia.getTorax_enf_actual());
			cbxCuello.setChecked(hisc_urgencia.getCuello_enf_actual());
			tbxFecha_inicio.setText(hisc_urgencia.getFecha_inicio_enfermedad());
			tbxDes_vida_Marital.setValue(hisc_urgencia.getDes_vida_marital());

			Utilidades.seleccionarListitem(lbxSintomatico_respiratorio,
					hisc_urgencia.getSintomatico_respiratorio());
			Utilidades.seleccionarListitem(lbxSintomatico_piel,
					hisc_urgencia.getSintomatico_piel());

			onSeleccionarSintomatico(lbxSintomatico_piel, tbxSintomatico_piel);
			onSeleccionarSintomatico(lbxSintomatico_respiratorio,
					tbxSintomatico_respiratorio);
			onSeleccionarOtros_ciclos(lbxAnt_gin_ciclo_1, rowOtros_ciclos);

			mostrarXmlTextbox(bandboxAnte_fam_hipertension,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_hipertension()));

			mostrarXmlTextbox(bandboxAnte_fam_ecv,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_ecv()));

			mostrarXmlTextbox(bandboxAnte_fam_enf_coronaria,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_enf_coronaria()));
			mostrarXmlTextbox(bandboxAnte_fam_muerte_im_acv,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_muerte_im_acv()));

			mostrarXmlTextbox(bandboxAnte_fam_dislipidemia,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_dislipidemia()));

			mostrarXmlTextbox(bandboxAnte_fam_nefropatias,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_nefropatias()));

			mostrarXmlTextbox(bandboxAnte_fam_obesos,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_obesos()));

			mostrarXmlTextbox(bandboxAnte_fam_diabetes,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_diabetes()));

			mostrarXmlTextbox(bandboxAnte_fam_enf_mental,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_enf_mental()));

			mostrarXmlTextbox(bandboxAnte_fam_cancer,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_cancer()));

			mostrarXmlTextbox(bandboxAnte_fam_hematologia,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_hematologia()));

			mostrarXmlTextbox(bandboxAnte_fam_otros,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_otros()));

			mostrarXmlTextbox(bandboxAnte_fam_alergicos,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_alergicos()));

			mostrarXmlTextbox(bandboxAnte_fam_asma,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_asma()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_hepatitisb,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_hepatitisb()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_lepra,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_lepra()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_sifilis,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_sifilis()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_tuberculosis,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_tuberculosis()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_vih,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_vih()));

			tbxAnte_fam_estecifique.setValue(hisc_urgencia
					.getAnte_fam_estecifique());
			tbxAnte_fam_observaciones.setValue(hisc_urgencia
					.getAnte_fam_observaciones());

			ibxGenerales_embarazos
					.setValue(hisc_urgencia.getGineco_generales());
			ibxMalformaciones_embarazos.setValue(hisc_urgencia
					.getGineco_malformaciones());
			ibxNacidos_vivos.setValue(hisc_urgencia.getGineco_nacidos_vivos());
			ibxNacidos_muertos.setValue(hisc_urgencia
					.getGineco_nacidos_muertos());
			ibxPreterminos.setValue(hisc_urgencia.getGineco_preterminos());
			ibxNro_gestaciones.setValue(hisc_urgencia
					.getGineco_nro_gestaciones());
			ibxNro_partos.setValue(hisc_urgencia.getGineco_nro_partos());
			ibxNro_abortos.setValue(hisc_urgencia.getGineco_nro_abortos());
			ibxNro_cesarias.setValue(hisc_urgencia.getGineco_nro_cesarias());

			dtbxAnt_gin_menarca.setValue(hisc_urgencia.getGineco_menarca());
			tbxAnt_gin_e_menopaudia.setValue(hisc_urgencia
					.getGineco_menopaudia());
			Utilidades.seleccionarListitem(lbxAnt_gin_ciclo_1,
					hisc_urgencia.getGineco_ciclo1());

			dtbxFum.setValue(hisc_urgencia.getFum());
			tbxAnt_gin_fecha_ultima_regla.setValue(hisc_urgencia
					.getGineco_fur());
			dtbxAnt_gin_fecha_espectante_parto.setValue(hisc_urgencia
					.getGineco_fep());
			Utilidades.seleccionarListitem(lbx_conf_fur,
					hisc_urgencia.getGineco_conf_fur());
			Utilidades.seleccionarRadio(chbVida_marital,
					hisc_urgencia.getVida_marital());
			tbxFecha_vida_marital.setValue(hisc_urgencia
					.getFecha_vida_marital());
			Utilidades.seleccionarRadio(chbOtro_ginecostetrico,
					hisc_urgencia.getOtro_ginecostetrico());

			tbxOtro_ginecostetrico.setValue(hisc_urgencia
					.getDescripcion_otro_ginecostetrico());
			tbxPatologia_embarazo_parto.setValue(hisc_urgencia
					.getPatologia_embarazo_parto());

			Utilidades.seleccionarRadio(rgpControlado_embarazo,
					hisc_urgencia.getEmbarazo_controlado());
			ibxNro_consultas
					.setValue(hisc_urgencia.getEmbarazo_nro_consultas());
			chkEn_puesto_salud.setChecked(hisc_urgencia
					.getEmbarazo_puesto_salud().equals("S"));
			chkEn_consulta_institucional.setChecked(hisc_urgencia
					.getEmbarazo_consulta_inst().equals("S"));
			chkEn_consulta_particular.setChecked(hisc_urgencia
					.getEmbarazo_consulta_particular().equals("S"));
			chkPor_especialistas.setChecked(hisc_urgencia
					.getEmbarazo_especialistas().equals("S"));
			chkPor_medicina_general.setChecked(hisc_urgencia
					.getEmbarazo_medicina_general().equals("S"));
			chkPor_partera.setChecked(hisc_urgencia.getEmbarazo_partera()
					.equals("S"));

			Utilidades.seleccionarRadio(rgpHistoria_sangrado_vaginal,
					hisc_urgencia.getEmbarazo_controlado());
			Utilidades.seleccionarRadio(rgpHepigastralgia,
					hisc_urgencia.getEmbarazo_hepigastralgia());
			Utilidades.seleccionarRadio(rgpEscotomas,
					hisc_urgencia.getEmbarazo_escotomas());
			Utilidades.seleccionarRadio(rgpCefaleas,
					hisc_urgencia.getEmbarazo_cefaleas());
			Utilidades.seleccionarRadio(rgpVomitos,
					hisc_urgencia.getEmbarazo_vomitos());
			Utilidades.seleccionarRadio(rgpInfeccion_vaginal,
					hisc_urgencia.getEmbarazo_infeccion_urinaria());
			Utilidades.seleccionarRadio(rgpEdema,
					hisc_urgencia.getEmbarazo_edema());
			Utilidades.seleccionarRadio(rgpTinitus,
					hisc_urgencia.getEmbarazo_tinitus());
			Utilidades.seleccionarRadio(rgpInfeccion_urinaria,
					hisc_urgencia.getEmbarazo_infeccion_urinaria());

			tbxFisico_estado_general.setValue(hisc_urgencia
					.getFisico_estado_general());
			tbxFisico_cabeza_cara.setValue(hisc_urgencia
					.getFisico_cabeza_cara());
			tbxFisico_ocular.setValue(hisc_urgencia.getFisico_ocular());
			tbxFisico_endocrinologo.setValue(hisc_urgencia
					.getFisico_endocrinologo());

			tbxFisico_osteomuscular.setValue(hisc_urgencia
					.getFisico_osteomuscular());
			tbxFisico_cardio_pulmonar.setValue(hisc_urgencia
					.getFisico_cardio_pulmonar());
			tbxFisico_cuello.setValue(hisc_urgencia.getFisico_cuello());
			tbxFisico_vacular.setValue(hisc_urgencia.getFisico_vacular());
			tbxFisico_piel_fanera.setValue(hisc_urgencia
					.getFisico_piel_fanera());
			tbxFisico_gastointestinal.setValue(hisc_urgencia
					.getFisico_gastointestinal());
			tbxFisico_genitourinario.setValue(hisc_urgencia
					.getFisico_genitourinario());
			tbxFisico_mental.setValue(hisc_urgencia.getFisico_mental());

			tbxHab_frecuencia_alcohol.setValue(hisc_urgencia
					.getHab_frecuencia_alcohol());
			tbxHab_licor_alcohol.setValue(hisc_urgencia.getHab_licor_alcohol());
			tbxHab_frecuencia_tabaquismo.setText(hisc_urgencia
					.getHab_frecuencia_tabaquismo());
			tbxHab_ejercicio_cual.setValue(hisc_urgencia
					.getHab_ejercicio_cual());
			chbHab_tabaquismo.setSelectedIndex(hisc_urgencia
					.getHab_tabaquismo() ? 0 : 1);
			chbHab_alcohol.setSelectedIndex(hisc_urgencia.getHab_alcohol() ? 0
					: 1);
			chbHab_ejercicio
					.setSelectedIndex(hisc_urgencia.getHab_ejercicio() ? 0 : 1);

			Utilidades.seleccionarRadio(rdbPsicofarmacos,
					hisc_urgencia.getPsicofarmacos());
			Utilidades.seleccionarRadio(rdbEstado_embarazo,
					hisc_urgencia.getEstado_embarazo());

			verificarEmbarazo();

			Utilidades.seleccionarRadio(rdbOtras_adicciones,
					hisc_urgencia.getOtras_adicciones());

			box_alcohol.setVisible(hisc_urgencia.getHab_alcohol());
			box_tabaquismo.setVisible(hisc_urgencia.getHab_tabaquismo());
			box_ejercicio.setVisible(hisc_urgencia.getHab_ejercicio());
			box_dietas.setVisible(hisc_urgencia.getHab_dietas());
			box_factores_psicologicos.setVisible(hisc_urgencia
					.getHab_factores_psicologicos());
			box_Psicofarmacos.setVisible(true);
			box_Otras_adicciones.setVisible(true);

			calcularTabaquismo();

			Utilidades.seleccionarRadio(chbHab_alcohol,
					hisc_urgencia.getHab_alcohol() ? "S" : "N");
			tbxHab_frecuencia_alcohol.setValue(hisc_urgencia
					.getHab_frecuencia_alcohol());

			tbxHab_licor_alcohol.setValue(hisc_urgencia.getHab_licor_alcohol());

			Utilidades.seleccionarRadio(chbHab_tabaquismo,
					hisc_urgencia.getHab_tabaquismo() ? "S" : "N");

			tbxHab_frecuencia_tabaquismo.setValue(hisc_urgencia
					.getHab_frecuencia_tabaquismo().isEmpty() ? null : Integer
					.parseInt(hisc_urgencia.getHab_frecuencia_tabaquismo()));

			tbxHab_frecuencia_tabaquismo_caja_anio.setValue(hisc_urgencia
					.getHab_frecuencia_tabaquismo_caja_anio());

			Utilidades.seleccionarRadio(chbHab_ejercicio,
					hisc_urgencia.getHab_ejercicio() ? "S" : "N");

			tbxHab_ejercicio_cual.setValue(hisc_urgencia
					.getHab_ejercicio_cual());

			ibxHab_ejercicio_minutos_dias
					.setValue(hisc_urgencia.getHab_ejercicio_horas_semana()
							.isEmpty() ? null : new Integer(hisc_urgencia
							.getHab_ejercicio_horas_semana()));

			calcularHorasPorSemana();

			Utilidades.seleccionarRadio(chbHab_dietas,
					hisc_urgencia.getHab_dietas() ? "S" : "N");

			tbxHab_dietas_frecuencia.setValue(hisc_urgencia
					.getHab_dietas_frecuencia());

			Utilidades.seleccionarRadio(chbHab_factores_psicologicos,
					hisc_urgencia.getHab_factores_psicologicos() ? "S" : "N");

			tbxHab_factores_psicologicos_cual.setValue(hisc_urgencia
					.getHab_factores_psicologicos_cual());

			ibxHab_ejercicio_minutos_dias
					.setValue(hisc_urgencia.getHab_ejercicio_horas_semana()
							.isEmpty() ? null : new Integer(hisc_urgencia
							.getHab_ejercicio_horas_semana()));

			calcularHorasPorSemana();

			tbxCual_psicofarmacos.setValue(hisc_urgencia
					.getCual_psicofarmacos());

			tbxCual_adicciones.setValue(hisc_urgencia.getCual_adicciones());

			tbxSituacion_uterina.setValue(hisc_urgencia.getUterina_situacion());
			tbxPresentacion_uterina.setValue(hisc_urgencia
					.getUterina_presentacion());
			tbxPosicion_presentacion.setValue(hisc_urgencia
					.getPresentacion_posicion());
			tbxVposicion_presentacion.setValue(hisc_urgencia
					.getPresentacion_vposicion());
			tbxTono_uterina.setValue(hisc_urgencia.getUterina_tono());
			tbxECF_uterina.setValue(hisc_urgencia.getUterina_ecf());
			Utilidades.seleccionarListitem(lbxDilatacion_list,
					hisc_urgencia.getDilatacion());
			dbxBorramiento.setText(hisc_urgencia.getBorramiento());
			tbxEstacion.setValue(hisc_urgencia.getEstacion());
			tbxMembrana.setValue(hisc_urgencia.getMembrana());

			Utilidades.seleccionarListitem(lbxCabezacara_list,
					hisc_urgencia.getCabezacara_list());
			Utilidades.seleccionarListitem(lbxOcular_list,
					hisc_urgencia.getOcular_list());
			Utilidades.seleccionarListitem(lbxCuello_list,
					hisc_urgencia.getCuello_list());
			Utilidades.seleccionarListitem(lbxCardio_pulmonar_list,
					hisc_urgencia.getCardio_pulmonar_list());
			Utilidades.seleccionarListitem(lbxAdomen_list,
					hisc_urgencia.getAdomen_list());
			Utilidades.seleccionarListitem(lbxGenitourinario_list,
					hisc_urgencia.getGenitourinario_list());
			Utilidades.seleccionarListitem(lbxExamen_mama_list,
					hisc_urgencia.getExamen_mama_list());
			Utilidades.seleccionarListitem(lbxEndocrino_list,
					hisc_urgencia.getEndocrino_list());
			Utilidades.seleccionarListitem(lbxOsteomuscular_list,
					hisc_urgencia.getOsteomuscular_list());
			Utilidades.seleccionarListitem(lbxVascular_list,
					hisc_urgencia.getVascular_list());
			Utilidades.seleccionarListitem(lbxPiel_faneras_list,
					hisc_urgencia.getPiel_faneras_list());

			Utilidades.seleccionarListitem(lbxSistema_nervioso_list,
					hisc_urgencia.getSistema_nervioso_list());

			lbxSintomatico_respiratorio.setDisabled(true);
			lbxSintomatico_piel.setDisabled(true);
			lbxCabezacara_list.setDisabled(true);
			lbxOcular_list.setDisabled(true);
			lbxCuello_list.setDisabled(true);
			lbxCardio_pulmonar_list.setDisabled(true);
			lbxAdomen_list.setDisabled(true);
			lbxGenitourinario_list.setDisabled(true);
			lbxExamen_mama_list.setDisabled(true);
			lbxEndocrino_list.setDisabled(true);
			lbxOsteomuscular_list.setDisabled(true);
			lbxVascular_list.setDisabled(true);
			lbxPiel_faneras_list.setDisabled(true);
			lbxRelacion.setDisabled(true);
			lbxEscolaridad_acompanante.setDisabled(true);
			lbxSistema_nervioso_list.setDisabled(true);

			tbxAnalisis_recomendaciones.setValue(hisc_urgencia
					.getAnalisis_recomendaciones());

			cargarAntecedentesPersonales(obtenerMapaAntecedentesPersonales(hisc_urgencia));

			treeItemDinamicRevisionSistemasMacro = new TreeItemDinamicRevisionSistemasMacro(
					tree, this, "RPS");
			treeItemDinamicRevisionSistemasMacro
					.cargarElemementosIniciales(obtenerListadoRevisionSistema(hisc_urgencia));
			treeItemDinamicRevisionSistemasMacro.renderizarContenido();

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString()))
				treeItemDinamicRevisionSistemasMacro.aplicarSoloLectura(true);

			cargarSignosVitales(hisc_urgencia);

			cargarImpresionDiagnostica(hisc_urgencia);

			macroGlasgow.limpiarEscalaGlasgow();
			if (hisc_urgencia.getEscala_glasgow().equals("S")) {
				gbxMacroGlasgow.setVisible(true);
				macroGlasgow.mostrarEscalaGlasgow(
						hisc_urgencia.getRespuesta_motora(),
						hisc_urgencia.getRespuesta_verbal(),
						hisc_urgencia.getApertura_ocular(), false);
			} else {
				gbxMacroGlasgow.setVisible(false);
			}

			tbxNo_farmocologico.setValue(hisc_urgencia.getNo_farmacologicos());
			tbxEducacional_paciente.setValue(hisc_urgencia
					.getEducacion_paciente());
			tbxPlan_seguimiento.setValue(hisc_urgencia.getPlan_seguimiento());

			tbxDescripcion_menarca.setValue(hisc_urgencia
					.getDescripcion_menarca());
			tbxOtro_ciclo.setValue(hisc_urgencia.getOtro_ciclo());
			tbxSistema_nervioso.setValue(hisc_urgencia.getSistema_nervioso());
			tbxFarmocologico.setValue(hisc_urgencia.getFarmocologico());

			chbAnt_gin_tiene_citologia.setSelectedIndex(hisc_urgencia
					.getAnt_gin_tiene_citologia() ? 0 : 1);
			dtbxAnt_gin_fecha_ultima_citologia.setValue(hisc_urgencia
					.getAnt_gin_fecha_ultima_citologia());
			tbxAnt_gin_citologia_resultado.setValue(hisc_urgencia
					.getAnt_gin_citologia_resultado());

			cargarAnexo9_remision(hisc_urgencia);

			if (hisc_urgencia.getUrl_partograma() != null
					&& !hisc_urgencia.getUrl_partograma().isEmpty()) {
				AImage aImage = new AImage("partograma", new FileInputStream(
						hisc_urgencia.getUrl_partograma()));
				file_partograma = aImage;
				btnAdjuntar_partograma.setVisible(false);
				btnEliminar_partograma.setVisible(false);
				btnMostrar_partograma.setVisible(true);
				url_partograma_anterior = hisc_urgencia.getUrl_partograma();
			} else {
				btnAdjuntar_partograma.setVisible(false);
				btnEliminar_partograma.setVisible(false);
				btnMostrar_partograma.setVisible(false);
			}

			infoPacientes
					.setCodigo_historia(hisc_urgencia.getCodigo_historia());

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "Este dato no se puede editar", this);
		}
	}

	public void mostrarDatosAnterior(Object obj) throws Exception {
		//log.info("ejecutando metodo @mostrarDatos()");
		Hisc_urgencia hisc_urgencia = (Hisc_urgencia) obj;
		try {
			this.nro_ingreso_admision = hisc_urgencia.getNro_ingreso();
			this.via_ingreso = hisc_urgencia.getVia_ingreso();
			infoPacientes
					.setCodigo_historia(hisc_urgencia.getCodigo_historia());

			tbxNro_identificacion.setValue(hisc_urgencia
					.getNro_identificacion());

			cargarPrestador(hisc_urgencia.getCodigo_prestador());

			onMostrarModuloRemisiones();
			initMostrar_datos(hisc_urgencia);

			// cargarInformacion_paciente();

			cargarRemisionInterna(hisc_urgencia);

			// Acompañante
			tbxAcompanante.setValue(hisc_urgencia.getConyugue());
			dbxIdentificacion_acompanante.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_urgencia.getCedula_conyugue()));
			Utilidades.seleccionarListitem(lbxRelacion,
					hisc_urgencia.getRelacion_acompanante());
			// ibxEdad_acompanante.setValue(ConvertidorDatosUtil.convertirDato(hisc_urgencia.getEdad_conyugue()));
			ibxEdad_acompanante.setValue(hisc_urgencia.getEdad_conyugue());
			Utilidades.seleccionarListitem(lbxEscolaridad_acompanante,
					hisc_urgencia.getEscolaridad_conyugue());
			dbxTelefono_acompanante.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_urgencia.getTelefono_conyugue()));

			tbxMotivo_consulta.setValue(hisc_urgencia.getMotivo_consulta());
			tbxEnfermedad_actual.setValue(hisc_urgencia.getEnfermedad_actual());
			tbxDescripcion_menarca
					.setValue(hisc_urgencia.getDes_vida_marital());

			if (hisc_urgencia.getSelect_enfer_act().equals("1")) {
				rdSeleccion1.setSelected(true);

			}
			if (hisc_urgencia.getSelect_enfer_act().equals("2")) {
				rdSeleccion2.setSelected(true);
				tbxParrafo_enfermedad_actual.setValue(hisc_urgencia
						.getEnfermedad_actual());
			}
			if (hisc_urgencia.getSelect_enfer_act().equals("3")) {
				rdSeleccion3.setSelected(true);
				tbxParrafo_enfermedad_actual.setValue(hisc_urgencia
						.getEnfermedad_actual());
			}
			seleccion((Radiogroup) getFellow("rdbseleccion"));

			StringTokenizer stringTokenizer = new StringTokenizer(
					hisc_urgencia.getPrimera_presentacion(), "|");

			Utilidades.seleccionarListitem(
					lbxPrimera_presentacion,
					stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "");
			spinnerVeces_repetido
					.setText(stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "1");

			tbxCaracteristicas_dolor.setValue(hisc_urgencia
					.getCaracteristicas_del_dolor());

			tbxIrradiacion.setValue(hisc_urgencia.getIrradiacion());
			tbxManera_form_inicio.setValue(hisc_urgencia
					.getManera_forma_de_inicio());
			tbxRelacionado.setValue(hisc_urgencia.getRelacionado_con());
			tbxEvolucion.setValue(hisc_urgencia.getEvolucion_del_cuadro());
			tbxlocalizacion.setValue(hisc_urgencia.getLocalizacion());
			tbxSintomas_asociados
					.setValue(hisc_urgencia.getSintoma_asociados());
			tbxTratamiento_recibido.setValue(hisc_urgencia
					.getTratamientos_recibidos());
			tbxActualmente_presenta.setValue(hisc_urgencia
					.getActualmente_se_presenta_con());
			Utilidades.seleccionarListitem(lbxIntensidad,
					hisc_urgencia.getIntensidad());

			cbxAbdomen.setChecked(hisc_urgencia.getAbdomen_enf_actual());
			cbxCabeza.setChecked(hisc_urgencia.getCabeza_enf_actual());
			cbxToraz.setChecked(hisc_urgencia.getTorax_enf_actual());
			cbxCuello.setChecked(hisc_urgencia.getCuello_enf_actual());
			tbxFecha_inicio.setText(hisc_urgencia.getFecha_inicio_enfermedad());
			tbxDes_vida_Marital.setValue(hisc_urgencia.getDes_vida_marital());

			Utilidades.seleccionarListitem(lbxSintomatico_respiratorio,
					hisc_urgencia.getSintomatico_respiratorio());
			Utilidades.seleccionarListitem(lbxSintomatico_piel,
					hisc_urgencia.getSintomatico_piel());

			onSeleccionarSintomatico(lbxSintomatico_piel, tbxSintomatico_piel);
			onSeleccionarSintomatico(lbxSintomatico_respiratorio,
					tbxSintomatico_respiratorio);
			onSeleccionarOtros_ciclos(lbxAnt_gin_ciclo_1, rowOtros_ciclos);

			mostrarXmlTextbox(bandboxAnte_fam_hipertension,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_hipertension()));

			mostrarXmlTextbox(bandboxAnte_fam_ecv,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_ecv()));

			mostrarXmlTextbox(bandboxAnte_fam_enf_coronaria,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_enf_coronaria()));
			mostrarXmlTextbox(bandboxAnte_fam_muerte_im_acv,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_muerte_im_acv()));

			mostrarXmlTextbox(bandboxAnte_fam_dislipidemia,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_dislipidemia()));

			mostrarXmlTextbox(bandboxAnte_fam_nefropatias,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_nefropatias()));

			mostrarXmlTextbox(bandboxAnte_fam_obesos,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_obesos()));

			mostrarXmlTextbox(bandboxAnte_fam_diabetes,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_diabetes()));

			mostrarXmlTextbox(bandboxAnte_fam_enf_mental,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_enf_mental()));

			mostrarXmlTextbox(bandboxAnte_fam_cancer,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_cancer()));

			mostrarXmlTextbox(bandboxAnte_fam_hematologia,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_hematologia()));

			mostrarXmlTextbox(bandboxAnte_fam_otros,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_otros()));

			mostrarXmlTextbox(bandboxAnte_fam_alergicos,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_alergicos()));

			mostrarXmlTextbox(bandboxAnte_fam_asma,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_asma()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_hepatitisb,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_hepatitisb()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_lepra,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_lepra()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_sifilis,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_sifilis()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_tuberculosis,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_tuberculosis()));

			mostrarXmlTextbox(bandboxAnte_fam_infecciosa_vih,
					ConvertidorXmlToMap.convertirToMap(hisc_urgencia
							.getAnte_fam_infecciosa_vih()));

			tbxAnte_fam_estecifique.setValue(hisc_urgencia
					.getAnte_fam_estecifique());
			tbxAnte_fam_observaciones.setValue(hisc_urgencia
					.getAnte_fam_observaciones());

			ibxGenerales_embarazos
					.setValue(hisc_urgencia.getGineco_generales());
			ibxMalformaciones_embarazos.setValue(hisc_urgencia
					.getGineco_malformaciones());
			ibxNacidos_vivos.setValue(hisc_urgencia.getGineco_nacidos_vivos());
			ibxNacidos_muertos.setValue(hisc_urgencia
					.getGineco_nacidos_muertos());
			ibxPreterminos.setValue(hisc_urgencia.getGineco_preterminos());
			ibxNro_gestaciones.setValue(hisc_urgencia
					.getGineco_nro_gestaciones());
			ibxNro_partos.setValue(hisc_urgencia.getGineco_nro_partos());
			ibxNro_abortos.setValue(hisc_urgencia.getGineco_nro_abortos());
			ibxNro_cesarias.setValue(hisc_urgencia.getGineco_nro_cesarias());

			dtbxAnt_gin_menarca.setValue(hisc_urgencia.getGineco_menarca());
			tbxAnt_gin_e_menopaudia.setValue(hisc_urgencia
					.getGineco_menopaudia());
			Utilidades.seleccionarListitem(lbxAnt_gin_ciclo_1,
					hisc_urgencia.getGineco_ciclo1());

			dtbxFum.setValue(hisc_urgencia.getFum());
			tbxAnt_gin_fecha_ultima_regla.setValue(hisc_urgencia
					.getGineco_fur());
			dtbxAnt_gin_fecha_espectante_parto.setValue(hisc_urgencia
					.getGineco_fep());
			Utilidades.seleccionarListitem(lbx_conf_fur,
					hisc_urgencia.getGineco_conf_fur());
			Utilidades.seleccionarRadio(chbVida_marital,
					hisc_urgencia.getVida_marital());
			tbxFecha_vida_marital.setValue(hisc_urgencia
					.getFecha_vida_marital());
			Utilidades.seleccionarRadio(chbOtro_ginecostetrico,
					hisc_urgencia.getOtro_ginecostetrico());

			tbxOtro_ginecostetrico.setValue(hisc_urgencia
					.getDescripcion_otro_ginecostetrico());
			tbxPatologia_embarazo_parto.setValue(hisc_urgencia
					.getPatologia_embarazo_parto());

			Utilidades.seleccionarRadio(rgpControlado_embarazo,
					hisc_urgencia.getEmbarazo_controlado());
			ibxNro_consultas
					.setValue(hisc_urgencia.getEmbarazo_nro_consultas());
			chkEn_puesto_salud.setChecked(hisc_urgencia
					.getEmbarazo_puesto_salud().equals("S"));
			chkEn_consulta_institucional.setChecked(hisc_urgencia
					.getEmbarazo_consulta_inst().equals("S"));
			chkEn_consulta_particular.setChecked(hisc_urgencia
					.getEmbarazo_consulta_particular().equals("S"));
			chkPor_especialistas.setChecked(hisc_urgencia
					.getEmbarazo_especialistas().equals("S"));
			chkPor_medicina_general.setChecked(hisc_urgencia
					.getEmbarazo_medicina_general().equals("S"));
			chkPor_partera.setChecked(hisc_urgencia.getEmbarazo_partera()
					.equals("S"));

			Utilidades.seleccionarRadio(rgpHistoria_sangrado_vaginal,
					hisc_urgencia.getEmbarazo_controlado());
			Utilidades.seleccionarRadio(rgpHepigastralgia,
					hisc_urgencia.getEmbarazo_hepigastralgia());
			Utilidades.seleccionarRadio(rgpEscotomas,
					hisc_urgencia.getEmbarazo_escotomas());
			Utilidades.seleccionarRadio(rgpCefaleas,
					hisc_urgencia.getEmbarazo_cefaleas());
			Utilidades.seleccionarRadio(rgpVomitos,
					hisc_urgencia.getEmbarazo_vomitos());
			Utilidades.seleccionarRadio(rgpInfeccion_vaginal,
					hisc_urgencia.getEmbarazo_infeccion_urinaria());
			Utilidades.seleccionarRadio(rgpEdema,
					hisc_urgencia.getEmbarazo_edema());
			Utilidades.seleccionarRadio(rgpTinitus,
					hisc_urgencia.getEmbarazo_tinitus());
			Utilidades.seleccionarRadio(rgpInfeccion_urinaria,
					hisc_urgencia.getEmbarazo_infeccion_urinaria());

			tbxFisico_estado_general.setValue(hisc_urgencia
					.getFisico_estado_general());
			tbxFisico_cabeza_cara.setValue(hisc_urgencia
					.getFisico_cabeza_cara());
			tbxFisico_ocular.setValue(hisc_urgencia.getFisico_ocular());
			tbxFisico_endocrinologo.setValue(hisc_urgencia
					.getFisico_endocrinologo());

			tbxFisico_osteomuscular.setValue(hisc_urgencia
					.getFisico_osteomuscular());
			tbxFisico_cardio_pulmonar.setValue(hisc_urgencia
					.getFisico_cardio_pulmonar());
			tbxFisico_cuello.setValue(hisc_urgencia.getFisico_cuello());
			tbxFisico_vacular.setValue(hisc_urgencia.getFisico_vacular());
			tbxFisico_piel_fanera.setValue(hisc_urgencia
					.getFisico_piel_fanera());
			tbxFisico_gastointestinal.setValue(hisc_urgencia
					.getFisico_gastointestinal());
			tbxFisico_genitourinario.setValue(hisc_urgencia
					.getFisico_genitourinario());
			tbxFisico_mental.setValue(hisc_urgencia.getFisico_mental());

			tbxHab_frecuencia_alcohol.setValue(hisc_urgencia
					.getHab_frecuencia_alcohol());
			tbxHab_licor_alcohol.setValue(hisc_urgencia.getHab_licor_alcohol());
			tbxHab_frecuencia_tabaquismo.setText(hisc_urgencia
					.getHab_frecuencia_tabaquismo());
			tbxHab_ejercicio_cual.setValue(hisc_urgencia
					.getHab_ejercicio_cual());
			chbHab_tabaquismo.setSelectedIndex(hisc_urgencia
					.getHab_tabaquismo() ? 0 : 1);
			chbHab_alcohol.setSelectedIndex(hisc_urgencia.getHab_alcohol() ? 0
					: 1);
			chbHab_ejercicio
					.setSelectedIndex(hisc_urgencia.getHab_ejercicio() ? 0 : 1);

			Utilidades.seleccionarRadio(rdbPsicofarmacos,
					hisc_urgencia.getPsicofarmacos());
			Utilidades.seleccionarRadio(rdbEstado_embarazo,
					hisc_urgencia.getEstado_embarazo());

			verificarEmbarazo();

			Utilidades.seleccionarRadio(rdbOtras_adicciones,
					hisc_urgencia.getOtras_adicciones());

			box_alcohol.setVisible(hisc_urgencia.getHab_alcohol());
			box_tabaquismo.setVisible(hisc_urgencia.getHab_tabaquismo());
			box_ejercicio.setVisible(hisc_urgencia.getHab_ejercicio());
			box_dietas.setVisible(hisc_urgencia.getHab_dietas());
			box_factores_psicologicos.setVisible(hisc_urgencia
					.getHab_factores_psicologicos());
			box_Psicofarmacos.setVisible(true);
			box_Otras_adicciones.setVisible(true);

			calcularTabaquismo();

			Utilidades.seleccionarRadio(chbHab_alcohol,
					hisc_urgencia.getHab_alcohol() ? "S" : "N");
			tbxHab_frecuencia_alcohol.setValue(hisc_urgencia
					.getHab_frecuencia_alcohol());

			tbxHab_licor_alcohol.setValue(hisc_urgencia.getHab_licor_alcohol());

			Utilidades.seleccionarRadio(chbHab_tabaquismo,
					hisc_urgencia.getHab_tabaquismo() ? "S" : "N");

			tbxHab_frecuencia_tabaquismo.setValue(hisc_urgencia
					.getHab_frecuencia_tabaquismo().isEmpty() ? null : Integer
					.parseInt(hisc_urgencia.getHab_frecuencia_tabaquismo()));

			tbxHab_frecuencia_tabaquismo_caja_anio.setValue(hisc_urgencia
					.getHab_frecuencia_tabaquismo_caja_anio());

			Utilidades.seleccionarRadio(chbHab_ejercicio,
					hisc_urgencia.getHab_ejercicio() ? "S" : "N");

			tbxHab_ejercicio_cual.setValue(hisc_urgencia
					.getHab_ejercicio_cual());

			ibxHab_ejercicio_minutos_dias
					.setValue(hisc_urgencia.getHab_ejercicio_horas_semana()
							.isEmpty() ? null : new Integer(hisc_urgencia
							.getHab_ejercicio_horas_semana()));

			calcularHorasPorSemana();

			Utilidades.seleccionarRadio(chbHab_dietas,
					hisc_urgencia.getHab_dietas() ? "S" : "N");

			tbxHab_dietas_frecuencia.setValue(hisc_urgencia
					.getHab_dietas_frecuencia());

			Utilidades.seleccionarRadio(chbHab_factores_psicologicos,
					hisc_urgencia.getHab_factores_psicologicos() ? "S" : "N");

			tbxHab_factores_psicologicos_cual.setValue(hisc_urgencia
					.getHab_factores_psicologicos_cual());

			ibxHab_ejercicio_minutos_dias
					.setValue(hisc_urgencia.getHab_ejercicio_horas_semana()
							.isEmpty() ? null : new Integer(hisc_urgencia
							.getHab_ejercicio_horas_semana()));

			calcularHorasPorSemana();

			tbxCual_psicofarmacos.setValue(hisc_urgencia
					.getCual_psicofarmacos());

			tbxCual_adicciones.setValue(hisc_urgencia.getCual_adicciones());

			tbxSituacion_uterina.setValue(hisc_urgencia.getUterina_situacion());
			tbxPresentacion_uterina.setValue(hisc_urgencia
					.getUterina_presentacion());
			tbxPosicion_presentacion.setValue(hisc_urgencia
					.getPresentacion_posicion());
			tbxVposicion_presentacion.setValue(hisc_urgencia
					.getPresentacion_vposicion());
			tbxTono_uterina.setValue(hisc_urgencia.getUterina_tono());
			tbxECF_uterina.setValue(hisc_urgencia.getUterina_ecf());
			Utilidades.seleccionarListitem(lbxDilatacion_list,
					hisc_urgencia.getDilatacion());
			dbxBorramiento.setText(hisc_urgencia.getBorramiento());
			tbxEstacion.setValue(hisc_urgencia.getEstacion());
			tbxMembrana.setValue(hisc_urgencia.getMembrana());

			Utilidades.seleccionarListitem(lbxCabezacara_list,
					hisc_urgencia.getCabezacara_list());
			Utilidades.seleccionarListitem(lbxOcular_list,
					hisc_urgencia.getOcular_list());
			Utilidades.seleccionarListitem(lbxCuello_list,
					hisc_urgencia.getCuello_list());
			Utilidades.seleccionarListitem(lbxCardio_pulmonar_list,
					hisc_urgencia.getCardio_pulmonar_list());
			Utilidades.seleccionarListitem(lbxAdomen_list,
					hisc_urgencia.getAdomen_list());
			Utilidades.seleccionarListitem(lbxGenitourinario_list,
					hisc_urgencia.getGenitourinario_list());
			Utilidades.seleccionarListitem(lbxExamen_mama_list,
					hisc_urgencia.getExamen_mama_list());
			Utilidades.seleccionarListitem(lbxEndocrino_list,
					hisc_urgencia.getEndocrino_list());
			Utilidades.seleccionarListitem(lbxOsteomuscular_list,
					hisc_urgencia.getOsteomuscular_list());
			Utilidades.seleccionarListitem(lbxVascular_list,
					hisc_urgencia.getVascular_list());
			Utilidades.seleccionarListitem(lbxPiel_faneras_list,
					hisc_urgencia.getPiel_faneras_list());

			Utilidades.seleccionarListitem(lbxSistema_nervioso_list,
					hisc_urgencia.getSistema_nervioso_list());

			lbxSintomatico_respiratorio.setDisabled(true);
			lbxSintomatico_piel.setDisabled(true);
			lbxCabezacara_list.setDisabled(true);
			lbxOcular_list.setDisabled(true);
			lbxCuello_list.setDisabled(true);
			lbxCardio_pulmonar_list.setDisabled(true);
			lbxAdomen_list.setDisabled(true);
			lbxGenitourinario_list.setDisabled(true);
			lbxExamen_mama_list.setDisabled(true);
			lbxEndocrino_list.setDisabled(true);
			lbxOsteomuscular_list.setDisabled(true);
			lbxVascular_list.setDisabled(true);
			lbxPiel_faneras_list.setDisabled(true);
			lbxRelacion.setDisabled(true);
			lbxEscolaridad_acompanante.setDisabled(true);
			lbxSistema_nervioso_list.setDisabled(true);

			tbxAnalisis_recomendaciones.setValue(hisc_urgencia
					.getAnalisis_recomendaciones());

			cargarAntecedentesPersonales(obtenerMapaAntecedentesPersonales(hisc_urgencia));

			treeItemDinamicRevisionSistemasMacro = new TreeItemDinamicRevisionSistemasMacro(
					tree, this, "RPS");
			treeItemDinamicRevisionSistemasMacro
					.cargarElemementosIniciales(obtenerListadoRevisionSistema(hisc_urgencia));
			treeItemDinamicRevisionSistemasMacro.renderizarContenido();

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString()))
				treeItemDinamicRevisionSistemasMacro.aplicarSoloLectura(true);

			cargarSignosVitales(hisc_urgencia);

			cargarImpresionDiagnostica(hisc_urgencia);

			macroGlasgow.limpiarEscalaGlasgow();
			if (hisc_urgencia.getEscala_glasgow().equals("S")) {
				gbxMacroGlasgow.setVisible(true);
				macroGlasgow.mostrarEscalaGlasgow(
						hisc_urgencia.getRespuesta_motora(),
						hisc_urgencia.getRespuesta_verbal(),
						hisc_urgencia.getApertura_ocular(), false);
			} else {
				gbxMacroGlasgow.setVisible(false);
			}

			tbxNo_farmocologico.setValue(hisc_urgencia.getNo_farmacologicos());
			tbxEducacional_paciente.setValue(hisc_urgencia
					.getEducacion_paciente());
			tbxPlan_seguimiento.setValue(hisc_urgencia.getPlan_seguimiento());

			tbxDescripcion_menarca.setValue(hisc_urgencia
					.getDescripcion_menarca());
			tbxOtro_ciclo.setValue(hisc_urgencia.getOtro_ciclo());
			tbxSistema_nervioso.setValue(hisc_urgencia.getSistema_nervioso());
			tbxFarmocologico.setValue(hisc_urgencia.getFarmocologico());

			chbAnt_gin_tiene_citologia.setSelectedIndex(hisc_urgencia
					.getAnt_gin_tiene_citologia() ? 0 : 1);
			dtbxAnt_gin_fecha_ultima_citologia.setValue(hisc_urgencia
					.getAnt_gin_fecha_ultima_citologia());
			tbxAnt_gin_citologia_resultado.setValue(hisc_urgencia
					.getAnt_gin_citologia_resultado());

			cargarAnexo9_remision(hisc_urgencia);

			if (hisc_urgencia.getUrl_partograma() != null
					&& !hisc_urgencia.getUrl_partograma().isEmpty()) {
				AImage aImage = new AImage("partograma", new FileInputStream(
						hisc_urgencia.getUrl_partograma()));
				file_partograma = aImage;
				btnAdjuntar_partograma.setVisible(false);
				btnEliminar_partograma.setVisible(false);
				btnMostrar_partograma.setVisible(true);
				url_partograma_anterior = hisc_urgencia.getUrl_partograma();
			} else {
				btnAdjuntar_partograma.setVisible(false);
				btnEliminar_partograma.setVisible(false);
				btnMostrar_partograma.setVisible(false);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "Este dato no se puede editar", this);
		}
	}

	public Hisc_urgencia getBean() {
		Hisc_urgencia hisc_urgencia = new Hisc_urgencia();
		hisc_urgencia.setCodigo_empresa(empresa.getCodigo_empresa());
		hisc_urgencia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		hisc_urgencia.setVia_ingreso(via_ingreso);
		hisc_urgencia.setCodigo_historia(infoPacientes.getCodigo_historia());
		hisc_urgencia.setNro_identificacion(admision_seleccionada
				.getNro_identificacion());
		hisc_urgencia.setCodigo_prestador(bandboxPrestador.getValue());
		hisc_urgencia.setFecha_ingreso(infoPacientes.getFecha_inicial());

		// Acompañante
		hisc_urgencia.setConyugue(tbxAcompanante.getValue());
		hisc_urgencia.setCedula_conyugue((dbxIdentificacion_acompanante
				.getText() != null ? dbxIdentificacion_acompanante.getText()
				+ "" : ""));
		hisc_urgencia.setRelacion_acompanante(lbxRelacion.getSelectedItem()
				.getValue().toString());
		hisc_urgencia
				.setEdad_conyugue((ibxEdad_acompanante.getValue() != null ? ibxEdad_acompanante
						.getValue() : 0));
		hisc_urgencia.setEscolaridad_conyugue(lbxEscolaridad_acompanante
				.getSelectedItem().getValue().toString());
		hisc_urgencia
				.setTelefono_conyugue((dbxTelefono_acompanante.getText() != null ? dbxTelefono_acompanante
						.getText() + ""
						: ""));
		// manuel
		hisc_urgencia.setDes_vida_marital(tbxDes_vida_Marital.getValue());

		hisc_urgencia.setMotivo_consulta(tbxMotivo_consulta.getValue());
		hisc_urgencia.setEnfermedad_actual(tbxEnfermedad_actual.getValue());

		if (rdSeleccion1.isSelected()) {
			hisc_urgencia.setSelect_enfer_act("1");
		}
		if (rdSeleccion2.isSelected()) {
			hisc_urgencia.setSelect_enfer_act("2");
			hisc_urgencia.setEnfermedad_actual(tbxParrafo_enfermedad_actual
					.getValue());
		}
		if (rdSeleccion3.isSelected()) {
			hisc_urgencia.setSelect_enfer_act("3");
			hisc_urgencia.setEnfermedad_actual(tbxParrafo_enfermedad_actual
					.getValue());
		}

		String primera_presentacion = lbxPrimera_presentacion.getSelectedItem()
				.getValue().toString();
		primera_presentacion = primera_presentacion + "|"
				+ spinnerVeces_repetido.getText();

		hisc_urgencia.setPrimera_presentacion(primera_presentacion);

		hisc_urgencia.setCaracteristicas_del_dolor(tbxCaracteristicas_dolor
				.getValue());
		hisc_urgencia.setIrradiacion(tbxIrradiacion.getValue());
		hisc_urgencia.setManera_forma_de_inicio(tbxManera_form_inicio
				.getValue());
		hisc_urgencia.setRelacionado_con(tbxRelacionado.getValue());
		hisc_urgencia.setEvolucion_del_cuadro(tbxEvolucion.getValue());
		hisc_urgencia.setLocalizacion(tbxlocalizacion.getValue());
		hisc_urgencia.setSintoma_asociados(tbxSintomas_asociados.getValue());
		hisc_urgencia.setTratamientos_recibidos(tbxTratamiento_recibido
				.getValue());
		hisc_urgencia.setActualmente_se_presenta_con(tbxActualmente_presenta
				.getValue());
		hisc_urgencia.setIntensidad(lbxIntensidad.getSelectedItem().getValue()
				.toString());

		hisc_urgencia.setAbdomen_enf_actual(cbxAbdomen.isChecked());
		hisc_urgencia.setCabeza_enf_actual(cbxCabeza.isChecked());
		hisc_urgencia.setTorax_enf_actual(cbxToraz.isChecked());
		hisc_urgencia.setCuello_enf_actual(cbxCuello.isChecked());

		hisc_urgencia.setFecha_inicio_enfermedad(tbxFecha_inicio.getText());

		hisc_urgencia.setSintomatico_respiratorio(lbxSintomatico_respiratorio
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setSintomatico_piel(lbxSintomatico_piel.getSelectedItem()
				.getValue().toString());

		hisc_urgencia
				.setSintomatico_respiratorio_desc(tbxSintomatico_respiratorio
						.getValue());
		hisc_urgencia.setSintomatico_piel_desc(tbxSintomatico_piel.getValue());

		hisc_urgencia
				.setAnte_fam_hipertension(obtenerXmlTextbox(bandboxAnte_fam_hipertension));
		hisc_urgencia.setAnte_fam_ecv(obtenerXmlTextbox(bandboxAnte_fam_ecv));
		hisc_urgencia
				.setAnte_fam_enf_coronaria(obtenerXmlTextbox(bandboxAnte_fam_enf_coronaria));
		hisc_urgencia
				.setAnte_fam_muerte_im_acv(obtenerXmlTextbox(bandboxAnte_fam_muerte_im_acv));
		hisc_urgencia
				.setAnte_fam_dislipidemia(obtenerXmlTextbox(bandboxAnte_fam_dislipidemia));
		hisc_urgencia
				.setAnte_fam_nefropatias(obtenerXmlTextbox(bandboxAnte_fam_nefropatias));
		hisc_urgencia
				.setAnte_fam_obesos(obtenerXmlTextbox(bandboxAnte_fam_obesos));
		hisc_urgencia
				.setAnte_fam_diabetes(obtenerXmlTextbox(bandboxAnte_fam_diabetes));
		hisc_urgencia
				.setAnte_fam_enf_mental(obtenerXmlTextbox(bandboxAnte_fam_enf_mental));
		hisc_urgencia
				.setAnte_fam_cancer(obtenerXmlTextbox(bandboxAnte_fam_cancer));
		hisc_urgencia
				.setAnte_fam_hematologia(obtenerXmlTextbox(bandboxAnte_fam_hematologia));
		hisc_urgencia
				.setAnte_fam_otros(obtenerXmlTextbox(bandboxAnte_fam_otros));

		hisc_urgencia.setAnte_fam_asma(obtenerXmlTextbox(bandboxAnte_fam_asma));
		hisc_urgencia
				.setAnte_fam_alergicos(obtenerXmlTextbox(bandboxAnte_fam_alergicos));
		hisc_urgencia
				.setAnte_fam_infecciosa_vih(obtenerXmlTextbox(bandboxAnte_fam_infecciosa_vih));
		hisc_urgencia
				.setAnte_fam_infecciosa_sifilis(obtenerXmlTextbox(bandboxAnte_fam_infecciosa_sifilis));
		hisc_urgencia
				.setAnte_fam_infecciosa_hepatitisb(obtenerXmlTextbox(bandboxAnte_fam_infecciosa_hepatitisb));
		hisc_urgencia
				.setAnte_fam_infecciosa_lepra(obtenerXmlTextbox(bandboxAnte_fam_infecciosa_lepra));
		hisc_urgencia
				.setAnte_fam_infecciosa_tuberculosis(obtenerXmlTextbox(bandboxAnte_fam_infecciosa_tuberculosis));

		hisc_urgencia.setAnte_fam_estecifique(tbxAnte_fam_estecifique
				.getValue());
		hisc_urgencia.setAnte_fam_observaciones(tbxAnte_fam_observaciones
				.getValue());

		hisc_urgencia.setGineco_generales(ibxGenerales_embarazos.getValue());
		hisc_urgencia.setGineco_malformaciones(ibxMalformaciones_embarazos
				.getValue());
		hisc_urgencia.setGineco_nacidos_vivos(ibxNacidos_vivos.getValue());
		hisc_urgencia.setGineco_nacidos_muertos(ibxNacidos_muertos.getValue());
		hisc_urgencia.setGineco_preterminos(ibxPreterminos.getValue());
		hisc_urgencia.setGineco_nro_gestaciones(ibxNro_gestaciones.getValue());
		hisc_urgencia.setGineco_nro_partos(ibxNro_partos.getValue());
		hisc_urgencia.setGineco_nro_abortos(ibxNro_abortos.getValue());
		hisc_urgencia.setGineco_nro_cesarias(ibxNro_cesarias.getValue());
		if (dtbxAnt_gin_menarca.getValue() != null) {
			hisc_urgencia.setGineco_menarca(new Timestamp(dtbxAnt_gin_menarca
					.getValue().getTime()));
		}
		if (dtbxFum.getValue() != null) {
			hisc_urgencia.setFum(new Timestamp(dtbxFum.getValue().getTime()));
		}

		hisc_urgencia.setGineco_menopaudia(tbxAnt_gin_e_menopaudia.getValue());
		hisc_urgencia.setGineco_ciclo1(lbxAnt_gin_ciclo_1.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setGineco_ciclo2("1");

		hisc_urgencia.setGineco_fur(tbxAnt_gin_fecha_ultima_regla.getValue());

		hisc_urgencia
				.setGineco_conf_fur(lbx_conf_fur.getSelectedIndex() == 0 ? ""
						: lbx_conf_fur.getSelectedItem().getValue().toString());

		if (dtbxAnt_gin_fecha_espectante_parto.getValue() != null)
			hisc_urgencia.setGineco_fep(new Timestamp(
					dtbxAnt_gin_fecha_espectante_parto.getValue().getTime()));

		hisc_urgencia
				.setVida_marital(chbVida_marital.getSelectedIndex() == 0 ? "S"
						: "N");

		if (chbVida_marital.getSelectedIndex() == 0) {
			hisc_urgencia.setFecha_vida_marital(tbxFecha_vida_marital
					.getValue());
		} else {
			hisc_urgencia.setFecha_vida_marital(null);
		}

		hisc_urgencia.setOtro_ginecostetrico(chbOtro_ginecostetrico
				.getSelectedItem().getValue().toString());

		hisc_urgencia.setDescripcion_otro_ginecostetrico(tbxOtro_ginecostetrico
				.getValue());

		hisc_urgencia.setPatologia_embarazo_parto(tbxPatologia_embarazo_parto
				.getValue());
		hisc_urgencia.setEmbarazo_controlado(rgpControlado_embarazo
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setEmbarazo_nro_consultas(ibxNro_consultas.getValue());
		hisc_urgencia
				.setEmbarazo_puesto_salud(chkEn_puesto_salud.isChecked() ? "S"
						: "N");
		hisc_urgencia.setEmbarazo_consulta_inst(chkEn_consulta_institucional
				.isChecked() ? "S" : "N");
		hisc_urgencia.setEmbarazo_consulta_particular(chkEn_consulta_particular
				.isChecked() ? "S" : "N");
		hisc_urgencia.setEmbarazo_especialistas(chkPor_especialistas
				.isChecked() ? "S" : "N");
		hisc_urgencia.setEmbarazo_medicina_general(chkPor_medicina_general
				.isChecked() ? "S" : "N");
		hisc_urgencia.setEmbarazo_partera(chkPor_partera.isChecked() ? "S"
				: "N");
		hisc_urgencia.setEmbarazo_sangrado_vaginal(rgpHistoria_sangrado_vaginal
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setEmbarazo_hepigastralgia(rgpHepigastralgia
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setEmbarazo_escotomas(rgpEscotomas.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setEmbarazo_cefaleas(rgpCefaleas.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setEstado_embarazo(rdbEstado_embarazo.getSelectedItem()
				.getValue().toString());

		hisc_urgencia.setEmbarazo_vomitos(rgpVomitos.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setEmbarazo_infeccion_vaginal(rgpInfeccion_vaginal
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setEmbarazo_edema(rgpEdema.getSelectedItem().getValue()
				.toString());
		hisc_urgencia.setEmbarazo_tinitus(rgpTinitus.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setEmbarazo_infeccion_urinaria(rgpInfeccion_urinaria
				.getSelectedItem().getValue().toString());

		hisc_urgencia.setFisico_estado_general(tbxFisico_estado_general
				.getValue());
		hisc_urgencia.setFisico_cabeza_cara(tbxFisico_cabeza_cara.getValue());
		hisc_urgencia.setFisico_ocular(tbxFisico_ocular.getValue());
		hisc_urgencia.setFisico_endocrinologo(tbxFisico_endocrinologo
				.getValue());
		hisc_urgencia.setFisico_osteomuscular(tbxFisico_osteomuscular
				.getValue());
		hisc_urgencia.setFisico_cardio_pulmonar(tbxFisico_cardio_pulmonar
				.getValue());
		hisc_urgencia.setFisico_cuello(tbxFisico_cuello.getValue());
		hisc_urgencia.setFisico_vacular(tbxFisico_vacular.getValue());
		hisc_urgencia.setFisico_piel_fanera(tbxFisico_piel_fanera.getValue());
		hisc_urgencia.setFisico_gastointestinal(tbxFisico_gastointestinal
				.getValue());
		hisc_urgencia.setFisico_genitourinario(tbxFisico_genitourinario
				.getValue());
		hisc_urgencia.setFisico_mental(tbxFisico_mental.getValue());

		hisc_urgencia
				.setHab_ejercicio_horas_semana(ibxHab_ejercicio_minutos_dias
						.getText());

		hisc_urgencia.setHab_alcohol(chbHab_alcohol.getSelectedIndex() == 0);
		hisc_urgencia.setHab_frecuencia_alcohol(tbxHab_frecuencia_alcohol
				.getValue());
		hisc_urgencia.setHab_licor_alcohol(tbxHab_licor_alcohol.getValue());

		hisc_urgencia
				.setHab_tabaquismo(chbHab_tabaquismo.getSelectedIndex() == 0);
		hisc_urgencia.setHab_frecuencia_tabaquismo(tbxHab_frecuencia_tabaquismo
				.getText());
		hisc_urgencia
				.setHab_frecuencia_tabaquismo_caja_anio(tbxHab_frecuencia_tabaquismo_caja_anio
						.getValue());

		hisc_urgencia
				.setHab_ejercicio(chbHab_ejercicio.getSelectedIndex() == 0);
		hisc_urgencia.setHab_ejercicio_cual(tbxHab_ejercicio_cual.getValue());
		hisc_urgencia
				.setHab_ejercicio_horas_semana(ibxHab_ejercicio_minutos_dias
						.getText());

		hisc_urgencia.setHab_dietas(chbHab_dietas.getSelectedIndex() == 0);
		hisc_urgencia.setHab_dietas_frecuencia(tbxHab_dietas_frecuencia
				.getValue());

		hisc_urgencia.setHab_factores_psicologicos(chbHab_factores_psicologicos
				.getSelectedIndex() == 0);
		hisc_urgencia
				.setHab_factores_psicologicos_cual(tbxHab_factores_psicologicos_cual
						.getValue());

		hisc_urgencia.setCabezacara_list(lbxCabezacara_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setOcular_list(lbxOcular_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setCuello_list(lbxCuello_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setCardio_pulmonar_list(lbxCardio_pulmonar_list
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setAdomen_list(lbxAdomen_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setGenitourinario_list(lbxGenitourinario_list
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setExamen_mama_list(lbxExamen_mama_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setEndocrino_list(lbxEndocrino_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setOsteomuscular_list(lbxOsteomuscular_list
				.getSelectedItem().getValue().toString());
		hisc_urgencia.setVascular_list(lbxVascular_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setPiel_faneras_list(lbxPiel_faneras_list
				.getSelectedItem().getValue().toString());

		hisc_urgencia.setSistema_nervioso_list(lbxSistema_nervioso_list
				.getSelectedItem().getValue().toString());

		hisc_urgencia.setOtras_adicciones(rdbOtras_adicciones.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setPsicofarmacos(rdbPsicofarmacos.getSelectedItem()
				.getValue().toString());

		hisc_urgencia.setCual_psicofarmacos(tbxCual_psicofarmacos.getValue());
		hisc_urgencia.setCual_adicciones(tbxCual_adicciones.getValue());

		hisc_urgencia.setUterina_situacion(tbxSituacion_uterina.getValue());
		hisc_urgencia.setUterina_presentacion(tbxPresentacion_uterina
				.getValue());
		hisc_urgencia.setPresentacion_posicion(tbxPosicion_presentacion
				.getValue());
		hisc_urgencia.setPresentacion_vposicion(tbxVposicion_presentacion
				.getValue());
		hisc_urgencia.setUterina_tono(tbxTono_uterina.getValue());
		hisc_urgencia.setUterina_ecf(tbxECF_uterina.getValue());
		hisc_urgencia.setDilatacion(lbxDilatacion_list.getSelectedItem()
				.getValue().toString());
		hisc_urgencia.setBorramiento(dbxBorramiento.getValue() + "");
		hisc_urgencia.setEstacion(tbxEstacion.getValue());
		hisc_urgencia.setMembrana(tbxMembrana.getValue());

		hisc_urgencia.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		hisc_urgencia.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		hisc_urgencia.setCreacion_user(usuarios.getCodigo());
		hisc_urgencia.setUltimo_user(usuarios.getCodigo());
		hisc_urgencia.setMotivo_consulta(Util
				.agregarComillas(tbxMotivo_consulta.getValue()));

		hisc_urgencia.setAnalisis_recomendaciones(tbxAnalisis_recomendaciones
				.getValue());

		if (gbxMacroGlasgow.isVisible()) {
			hisc_urgencia.setEscala_glasgow("S");
			hisc_urgencia.setRespuesta_motora(macroGlasgow
					.obtenerRespuestaMotora());
			hisc_urgencia.setRespuesta_verbal(macroGlasgow
					.obtenerRespuestaVerbal());
			hisc_urgencia.setApertura_ocular(macroGlasgow
					.obtenerAperturaOcular());
		} else {
			hisc_urgencia.setEscala_glasgow("S");
		}

		hisc_urgencia.setNo_farmacologicos(tbxNo_farmocologico.getValue());
		hisc_urgencia.setEducacion_paciente(tbxEducacional_paciente.getValue());
		hisc_urgencia.setPlan_seguimiento(tbxPlan_seguimiento.getValue());
		hisc_urgencia.setDescripcion_menarca(tbxDescripcion_menarca.getValue());
		hisc_urgencia.setOtro_ciclo(tbxOtro_ciclo.getValue());
		hisc_urgencia.setTipo_urgencia(tipo_urgencia);
		hisc_urgencia.setSistema_nervioso(tbxSistema_nervioso.getValue());
		hisc_urgencia.setFarmocologico(tbxFarmocologico.getValue());

		hisc_urgencia.setAnt_gin_tiene_citologia(chbAnt_gin_tiene_citologia
				.getSelectedIndex() == 0);
		if (dtbxAnt_gin_fecha_ultima_citologia.getValue() != null)
			hisc_urgencia.setAnt_gin_fecha_ultima_citologia(new Timestamp(
					dtbxAnt_gin_fecha_ultima_citologia.getValue().getTime()));

		hisc_urgencia
				.setAnt_gin_citologia_resultado(tbxAnt_gin_citologia_resultado
						.getText());

		return hisc_urgencia;

	}

	public List<Antecedentes_personales> obtenerAntecedentesPersonales() {
		List<Antecedentes_personales> listadoAntecedentes = new ArrayList<Antecedentes_personales>();
		for (Elemento antecedente : elementosAntecentesPersonales) {
			if (gridAntecedentes_personales
					.hasFellow("radiogroup_ant_personales_"
							+ antecedente.getCodigo())) {
				Radiogroup radiogroup = (Radiogroup) gridAntecedentes_personales
						.getFellow("radiogroup_ant_personales_"
								+ antecedente.getCodigo());
				Textbox textbox = (Textbox) gridAntecedentes_personales
						.getFellow("textbox_ant_personales_"
								+ antecedente.getCodigo());

				String respuesta = radiogroup.getSelectedIndex() == -1 ? "N"
						: radiogroup.getSelectedItem().getValue().toString();

				Antecedentes_personales antecedentes_personales = new Antecedentes_personales();
				antecedentes_personales.setCodigo_antecente(antecedente
						.getCodigo());
				antecedentes_personales.setCodigo_empresa(codigo_empresa);
				antecedentes_personales.setCodigo_sucursal(codigo_sucursal);
				antecedentes_personales.setRespuesta(respuesta);

				if (antecedente.getCodigo().equals("32")) {
					antecedentes_personales
							.setObservacion(obtenerXmlTextbox(textbox));
				} else {
					antecedentes_personales.setObservacion(textbox.getValue());
				}
				listadoAntecedentes.add(antecedentes_personales);
			}
		}
		return listadoAntecedentes;
	}

	private void cargarSignosVitales(Hisc_urgencia hisc_urgencia) {
		Sigvitales sigvitales = new Sigvitales();
		sigvitales.setCodigo_empresa(codigo_empresa);
		sigvitales.setCodigo_sucursal(codigo_sucursal);
		sigvitales.setCodigo_historia(hisc_urgencia.getCodigo_historia());
		sigvitales = getServiceLocator().getSigvitalesService().consultar(
				sigvitales);
		mcSignosVitales.mostrarSigvitales(sigvitales);
	}

	private void cargarAnexo9_remision(Hisc_urgencia hisc_urgencia) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_urgencia.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		boolean creada = false;
		if (anexo9_entidad != null)
			creada = true;
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
		if (creada) {
			remisiones_externasAction.getBotonGenerar().setVisible(false);
		}
		remisiones_externasAction.setNro_historia(hisc_urgencia
				.getCodigo_historia());
	}

	private void cargarRemisionInterna(Hisc_urgencia hisc_urgencia)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_urgencia.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_urgencia.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_urgencia.getCodigo_sucursal());
		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private void cargarImpresionDiagnostica(Hisc_urgencia hisc_urgencia)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc_urgencia
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	private Popup generarPopupObservaciones(Elemento elemento,
			final Textbox textboxObservacion, Component contenedor) {

		final Popup popup = new Popup();
		final Textbox textbox = new Textbox();

		popup.addEventListener(Events.ON_OPEN, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				textbox.setValue(textboxObservacion.getValue());
				textbox.setFocus(true);
			}
		});

		Window window = new Window();
		window.setBorder(true);
		window.setClosable(true);
		window.setTitle("Observaciones " + elemento.getDescripcion());

		window.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				popup.close();
			}

		});

		Vlayout vlayout = new Vlayout();
		vlayout.setStyle("margin:5px");

		if (!elemento.getCodigo().equals("32")) {
			textbox.setId("textbox_observaciones_ant_per_"
					+ elemento.getCodigo());
			textbox.setWidth("350px");
			textbox.setRows(8);

			vlayout.appendChild(textbox);
			textbox.setValue(textboxObservacion.getValue());
			textbox.addEventListener("onChanging", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					InputEvent inputevent = (InputEvent) event;
					textboxObservacion.setValue(inputevent.getValue());
				}
			});

		} else {
			Hbox hbox = new Hbox();

			final Checkbox checkbox1 = new Checkbox("VIH");
			final Checkbox checkbox2 = new Checkbox("Hepatitis B");
			final Checkbox checkbox3 = new Checkbox("Sífilis");
			final Checkbox checkbox4 = new Checkbox("Tuberculosis");
			final Checkbox checkbox5 = new Checkbox("Lepra");

			checkbox1.setValue("VIH");
			checkbox2.setValue("HEPATITISB");
			checkbox3.setValue("SIFILIS");
			checkbox4.setValue("TUBERCULOSIS");
			checkbox5.setValue("LEPRA");

			hbox.appendChild(checkbox1);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox2);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox3);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox4);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox5);

			Map<String, Object> mapa_aux = null;
			try {
				mapa_aux = ConvertidorXmlToMap
						.convertirToMap(obtenerXmlTextbox(textboxObservacion));
			} catch (Exception e) {
				mapa_aux = new HashMap<String, Object>();
			}

			checkbox1.setChecked(mapa_aux.containsKey(checkbox1.getValue()));

			checkbox2.setChecked(mapa_aux.containsKey(checkbox2.getValue()));

			checkbox3.setChecked(mapa_aux.containsKey(checkbox3.getValue()));

			checkbox4.setChecked(mapa_aux.containsKey(checkbox4.getValue()));

			checkbox5.setChecked(mapa_aux.containsKey(checkbox5.getValue()));

			final Textbox textbox_observacion = new Textbox();
			textbox_observacion.setHflex("1");
			textbox_observacion.setRows(2);

			if (mapa_aux.containsKey("OBSERVACION")) {
				textbox_observacion.setValue(mapa_aux.get("OBSERVACION")
						.toString());
			} else {
				textbox_observacion.setValue("");
				mapa_aux.put("OBSERVACION", textbox_observacion.getValue());
			}

			final Map<String, Object> mapa_contenido = mapa_aux;

			EventListener<CheckEvent> eventListener = new EventListener<CheckEvent>() {
				@Override
				public void onEvent(CheckEvent event) throws Exception {
					if (event.getTarget() != null) {
						Checkbox checkbox = (Checkbox) event.getTarget();
						if (checkbox.isChecked()) {
							mapa_contenido.put(checkbox.getValue().toString(),
									checkbox.getValue());
						} else {
							mapa_contenido.remove(checkbox.getValue());
						}

						mostrarXmlTextbox(textboxObservacion, mapa_contenido);

					}

				}
			};

			textbox_observacion.addEventListener(Events.ON_CHANGE,
					new EventListener<InputEvent>() {

						@Override
						public void onEvent(InputEvent event) throws Exception {
							mapa_contenido.put("OBSERVACION", event.getValue());
							mostrarXmlTextbox(textboxObservacion,
									mapa_contenido);

						}

					});

			checkbox1.addEventListener("onCheck", eventListener);
			checkbox2.addEventListener("onCheck", eventListener);
			checkbox3.addEventListener("onCheck", eventListener);
			checkbox4.addEventListener("onCheck", eventListener);
			checkbox5.addEventListener("onCheck", eventListener);

			vlayout.appendChild(hbox);
			Label labelObservacion = new Label("observacion");
			labelObservacion.setStyle("font-weight:bold");
			vlayout.appendChild(labelObservacion);
			vlayout.appendChild(textbox_observacion);

		}
		window.appendChild(vlayout);
		popup.appendChild(window);
		contenedor.appendChild(popup);
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(
					hlayoutAntecedentes_personales, true,
					new String[] { "northEditar" });
		}
		return popup;
	}

	public void onMostrarEscalaGlasgow() {
		Boolean visible = gbxMacroGlasgow.isVisible();
		if (!visible) {
			btnMostrar_escala_glasgow.setLabel("Ocultar Escla de Glasgow");
			gbxMacroGlasgow.setVisible(true);
		} else {
			btnMostrar_escala_glasgow.setLabel("Mostrar Escala de Glasgow");
			gbxMacroGlasgow.setVisible(false);
		}
	}

	public void onMostrarModuloRemisiones() throws Exception {
		//log.info("ejecutando metodo @onMostrarModuloRemisiones()");
		if (remisiones_externasAction != null)
			remisiones_externasAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso_admision);
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
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

	public void generarParrafoEnfermedadActual() {
		StringBuffer strBuffer = new StringBuffer();

		Integer edad_anios = infoPacientes.getEdadAnios();

		if (edad_anios >= 18) {
			strBuffer
					.append("Paciente quien consulta por cuadro clinico presentado ");
		} else {
			strBuffer
					.append("Paciente acompañado por su (   ) quien consulta por cuadro clínico presentado ");
		}
		if (lbxPrimera_presentacion.getSelectedIndex() == 0) {
			strBuffer.append(" por primera vez");
		} else {
			int veces = spinnerVeces_repetido.getValue();
			if (veces == 1) {
				strBuffer.append(" en una ocaSIÓN ");
			} else {
				strBuffer.append(veces + " veces, ");
			}
		}
		strBuffer.append(" que inició ").append(tbxFecha_inicio.getText());
		if (!tbxManera_form_inicio.getValue().isEmpty()) {
			strBuffer.append(" de forma ").append(
					tbxManera_form_inicio.getValue());
		}
		if (!tbxRelacionado.getValue().isEmpty()) {
			strBuffer.append(" el cual el paciente relaciona con ").append(
					tbxRelacionado.getValue() + ("."));
		}
		if (!tbxSintomas_asociados.getValue().isEmpty()) {
			strBuffer.append(" Con los siguientes sintomas y/o signos ")
					.append(tbxSintomas_asociados.getValue());
		}
		if (!tbxEvolucion.getValue().isEmpty()) {
			strBuffer.append(" presentando una evolucion del cuadro ").append(
					tbxEvolucion.getValue() + ("."));
		}
		if (!tbxTratamiento_recibido.getValue().isEmpty()) {
			strBuffer.append(" recibiendo un tratamiento de ").append(
					tbxTratamiento_recibido.getValue());
		}

		if (!tbxActualmente_presenta.getValue().isEmpty()) {
			strBuffer.append(" y actualmente se presenta con ").append(
					tbxActualmente_presenta.getValue() + ("."));
		}

		if (!tbxlocalizacion.getValue().isEmpty()) {
			strBuffer.append("\nDolor localizado en ").append(
					tbxlocalizacion.getValue() + (" "));
		}

		if (rdbseleccion.getSelectedItem().getValue().toString().equals("3")) {
			if (tbxIrradiacion.getValue().equals("no")
					|| tbxIrradiacion.getValue().equals("NO")
					|| tbxIrradiacion.getValue().equals("No")) {
				strBuffer.append(tbxIrradiacion.getValue()).append(
						" irradiado ");
			} else if (!tbxIrradiacion.getValue().isEmpty()) {
				strBuffer.append(" irradiado a ").append(
						tbxIrradiacion.getValue());
			}

			if (!lbxIntensidad.getSelectedItem().getValue().toString()
					.isEmpty()) {
				strBuffer.append(" con una intensidad ").append(
						lbxIntensidad.getSelectedItem().getLabel());
			}

			if (!tbxCaracteristicas_dolor.getValue().isEmpty()) {
				strBuffer.append(" y caracteristica o tipo ").append(
						tbxCaracteristicas_dolor.getValue());
			}

		}

		tbxParrafo_enfermedad_actual.setValue(strBuffer.toString());

	}

	public void onSeleccionarSintomatico(Listbox listboxSintomatico,
			Textbox textboxSintomatico) {
		if (listboxSintomatico.getSelectedItem().getValue().toString()
				.equals("S")) {
			textboxSintomatico.setVisible(true);
		} else {
			textboxSintomatico.setVisible(false);
		}
	}

	public void onSeleccionarAntecendestes1(Listbox listboxSintomatico,
			Textbox textboxAntecedentes) {
		if (listboxSintomatico.getSelectedItem().getValue().toString()
				.equals("S")) {
			textboxAntecedentes.setFocus(true);
		} else {
			textboxAntecedentes.setFocus(true);
		}
	}

	public void generarContendioAntecedentesFamiliares(
			Bandbox bandbox_antecedente) {

		String contenido = obtenerXmlTextbox(bandbox_antecedente);

		Bandpopup bandpopup = null;
		Grid grid = null;
		Rows filas = null;

		if (bandbox_antecedente.getFirstChild() != null
				&& (bandbox_antecedente.getFirstChild() instanceof Bandpopup)) {
			bandpopup = (Bandpopup) bandbox_antecedente.getFirstChild();
		} else {
			bandpopup = new Bandpopup();
		}

		bandpopup.getChildren().clear();

		bandpopup.setWidth("300px");
		grid = new Grid();
		filas = new Rows();
		grid.appendChild(filas);
		bandpopup.appendChild(grid);

		Map<String, Object> mapa_aux = null;

		if (!contenido.isEmpty()) {
			try {
				mapa_aux = ConvertidorXmlToMap.convertirToMap(contenido);
			} catch (Exception e) {
				mapa_aux = new HashMap<String, Object>();
			}
		}

		final Map<String, Object> mapa_contenido = mapa_aux != null ? mapa_aux
				: new HashMap<String, Object>();

		Row fila = new Row();
		Hlayout hlayout = new Hlayout();
		final Checkbox checkbox_padre = new Checkbox("Padre");
		checkbox_padre.setChecked(mapa_contenido.containsKey("padre"));

		final Checkbox checkbox_madre = new Checkbox("Madre");
		checkbox_madre.setChecked(mapa_contenido.containsKey("madre"));

		final Checkbox checkbox_hermano = new Checkbox("Hermano");
		checkbox_hermano.setChecked(mapa_contenido.containsKey("hermano"));

		final Checkbox checkbox_abuelo_paterno = new Checkbox(
				"Abuelo (Paterno)");
		checkbox_abuelo_paterno.setChecked(mapa_contenido
				.containsKey("abuelo_paterno"));

		final Checkbox checkbox_abuelo_materno = new Checkbox(
				"Abuelo (Materno)");
		checkbox_abuelo_materno.setChecked(mapa_contenido
				.containsKey("abuelo_materno"));

		final Checkbox checkbox_abuela_paterna = new Checkbox(
				"Abuela (Paterna)");
		checkbox_abuela_paterna.setChecked(mapa_contenido
				.containsKey("abuela_paterna"));

		final Checkbox checkbox_abuela_materna = new Checkbox(
				"Abuela (Materna)");
		checkbox_abuela_materna.setChecked(mapa_contenido
				.containsKey("abuela_materna"));

		final Checkbox checkbox_otros = new Checkbox("Otros Familiares");
		checkbox_otros.setChecked(mapa_contenido.containsKey("otros"));

		final Textbox textbox_otros = new Textbox();
		textbox_otros.setVisible(mapa_contenido.containsKey("otros"));
		textbox_otros.setHflex("1");

		if (mapa_contenido.containsKey("otros_familiares")) {
			textbox_otros.setValue(mapa_contenido.get("otros_familiares")
					.toString());
		} else {
			textbox_otros.setValue("");
		}

		hlayout.appendChild(checkbox_padre);
		hlayout.appendChild(checkbox_madre);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_hermano);
		hlayout.appendChild(new Space());
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_abuelo_paterno);
		hlayout.appendChild(checkbox_abuelo_materno);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_abuela_paterna);
		hlayout.appendChild(checkbox_abuela_materna);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_otros);
		hlayout.appendChild(textbox_otros);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		final Bandbox bandbox = bandbox_antecedente;

		EventListener<Event> eventListener = new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getTarget() != null) {
					if (checkbox_padre.equals(event.getTarget())) {
						if (checkbox_padre.isChecked()) {
							mapa_contenido.put("padre", "padre");
						} else {
							mapa_contenido.remove("padre");
						}
					} else if (checkbox_madre.equals(event.getTarget())) {
						if (checkbox_madre.isChecked()) {
							mapa_contenido.put("madre", "madre");
						} else {
							mapa_contenido.remove("madre");
						}
					} else if (checkbox_hermano.equals(event.getTarget())) {
						if (checkbox_hermano.isChecked()) {
							mapa_contenido.put("hermano", "hermano");
						} else {
							mapa_contenido.remove("hermano");
						}
					} else if (checkbox_abuelo_paterno
							.equals(event.getTarget())) {
						if (checkbox_abuelo_paterno.isChecked()) {
							mapa_contenido.put("abuelo_paterno",
									"abuelo_paterno");
						} else {
							mapa_contenido.remove("abuelo_paterno");
						}
					} else if (checkbox_abuelo_materno
							.equals(event.getTarget())) {
						if (checkbox_abuelo_materno.isChecked()) {
							mapa_contenido.put("abuelo_materno",
									"abuelo_materno");
						} else {
							mapa_contenido.remove("abuelo_materno");
						}
					} else if (checkbox_abuela_paterna
							.equals(event.getTarget())) {
						if (checkbox_abuela_paterna.isChecked()) {
							mapa_contenido.put("abuela_paterna",
									"abuela_paterna");
						} else {
							mapa_contenido.remove("abuela_paterna");
						}
					} else if (checkbox_abuela_materna
							.equals(event.getTarget())) {
						if (checkbox_abuela_materna.isChecked()) {
							mapa_contenido.put("abuela_materna",
									"abuela_materna");
						} else {
							mapa_contenido.remove("abuela_materna");
						}
					}

					mostrarXmlTextbox(bandbox, mapa_contenido);

				}
			}
		};

		checkbox_otros.addEventListener(Events.ON_CHECK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if (checkbox_otros.isChecked()) {
							textbox_otros.setVisible(true);
							mapa_contenido.put("otros", "otros");
							mostrarXmlTextbox(bandbox, mapa_contenido);
						} else {
							textbox_otros.setVisible(false);
							mapa_contenido.remove("otros");
							mapa_contenido.remove("otros_familiares");

							mostrarXmlTextbox(bandbox, mapa_contenido);
						}
					}

				});

		textbox_otros.addEventListener(Events.ON_CHANGE,
				new EventListener<InputEvent>() {

					@Override
					public void onEvent(InputEvent event) throws Exception {
						mapa_contenido.put("otros_familiares", event.getValue());
						mostrarXmlTextbox(bandbox, mapa_contenido);
					}

				});

		checkbox_abuela_materna
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuela_paterna
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuelo_materno
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuelo_paterno
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_hermano.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_madre.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_padre.addEventListener(Events.ON_CHECK, eventListener);

	}

	// public boolean validarFormulaGinecobsterica() {
	// Integer n_embarazos = ibxNro_gestaciones.getValue() != null ?
	// ibxNro_gestaciones
	// .getValue() : 0;
	// Integer n_partos = ibxNro_partos.getValue() != null ? ibxNro_partos
	// .getValue() : 0;
	// Integer n_cesarias = ibxNro_cesarias.getValue() != null ? ibxNro_cesarias
	// .getValue() : 0;
	// Integer n_abortos = ibxNro_abortos.getValue() != null ? ibxNro_abortos
	// .getValue() : 0;
	// Integer n_vivos = ibxNacidos_vivos.getValue() != null ? ibxNacidos_vivos
	// .getValue() : 0;
	//
	// StringBuffer stringBuffer = new StringBuffer();
	//
	// boolean valida = true;
	//
	// if (n_partos > n_embarazos) {
	// stringBuffer.append("El Número de partos (").append(n_partos)
	// .append(") no puede ser mayor al Número de embarazos (")
	// .append(n_embarazos).append(") ");
	// valida = false;
	// } else if (n_cesarias > n_embarazos) {
	// stringBuffer.append("El Número de cesarias (").append(n_cesarias)
	// .append(") no puede ser mayor al Número de embarazos (")
	// .append(n_embarazos).append(") ");
	// valida = false;
	// } else if (n_abortos > n_embarazos) {
	// stringBuffer.append("El Número de abortos (").append(n_abortos)
	// .append(") no puede ser mayor al Número de embarazos (")
	// .append(n_embarazos).append(") ");
	// valida = false;
	// } else if (n_vivos > n_embarazos) {
	// stringBuffer.append("El Número de nacidos vivos (").append(n_vivos)
	// .append(") no puede ser mayor al Número de embarazos (")
	// .append(n_embarazos).append(") ");
	// valida = false;
	// } else {
	// if (n_embarazos.intValue() != (n_cesarias.intValue()
	// + n_partos.intValue() + n_abortos.intValue())) {
	// stringBuffer
	// .append("El Número de embarazos (")
	// .append(n_embarazos)
	// .append(") debe ser igual a la suma del Número de partos más el Número de cesarias más el Número de abortos. G = (P + C + A)");
	// valida = false;
	// } else if (n_vivos.intValue() > (n_partos.intValue() + n_cesarias
	// .intValue())) {
	// stringBuffer
	// .append("El Número de nacidos vivos (")
	// .append(n_vivos)
	// .append(") no puede ser mayor que la suma del Número de partos más el Número de cesarias");
	// valida = false;
	// }
	// }
	//
	// if (!valida) {
	// MensajesUtil.mensajeAlerta("Verificar la formula Ginecobstetrica",
	// stringBuffer.toString(), new EventListener<Event>() {
	//
	// @Override
	// public void onEvent(Event event) throws Exception {
	// Clients.scrollIntoView(gbxGPAC);
	// }
	// });
	// }
	//
	// return valida;
	//
	// }

	public void calcularFechaEsperadaParto() {
		if (dtbxFum.getText().isEmpty()) {
			dtbxFum.setText("");
		} else if (dtbxFum.getValue() != null) {
			Calendar calendar_fum = Calendar.getInstance();
			calendar_fum.setTime(dtbxFum.getValue());
			calendar_fum.set(Calendar.MONTH,
					calendar_fum.get(Calendar.MONTH) + 9);
			calendar_fum.set(Calendar.DAY_OF_YEAR,
					calendar_fum.get(Calendar.DAY_OF_YEAR) + 7);
			dtbxAnt_gin_fecha_espectante_parto.setValue(calendar_fum.getTime());
		} else {
			dtbxAnt_gin_fecha_espectante_parto.setText("");
		}
	}

	public void mostrarXmlTextbox(Textbox textbox,
			Map<String, Object> mapa_contenido) {
		try {
			textbox.setAttribute("XML_CONTENIDO",
					ConvertidorXmlToMap.convertirToXML(mapa_contenido));
			StringBuffer stringBuffer = new StringBuffer();
			for (Object key : mapa_contenido.keySet()) {
				Object valor = mapa_contenido.get(key);
				if (key.equals(valor)) {
					stringBuffer.append(key).append(";");
				} else {
					if (!valor.toString().isEmpty()) {
						stringBuffer.append(key).append("=").append(valor)
								.append(";");
					}
				}
			}
			textbox.setText(stringBuffer.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public String obtenerXmlTextbox(Textbox textbox) {
		if (textbox.hasAttribute("XML_CONTENIDO")) {
			return (String) textbox.getAttribute("XML_CONTENIDO");
		} else {
			return "";
		}
	}

	public void calcularHorasPorSemana() {
		Integer minutos = ibxHab_ejercicio_minutos_dias.getValue() != null ? ibxHab_ejercicio_minutos_dias
				.getValue() : 0;
		double horas_semana = 7.0 * (minutos.doubleValue() / 60.0);
		dbxHab_ejercicio_horas_semana.setValue(horas_semana);
		dbxHab_ejercicio_horas_semana
				.setTooltiptext(((int) (horas_semana * 60))
						+ " minutos por semana");
		dbxHab_ejercicio_horas_semana.setReadonly(true);
	}

	public void onSeleccionarPresentacion() {
		String presentacion = lbxPrimera_presentacion.getSelectedItem()
				.getValue().toString();
		if (presentacion.equals("N")) {
			lbVeces_repetido.setVisible(true);
			spinnerVeces_repetido.setVisible(true);
			spinnerVeces_repetido.setValue(1);
		}
	}

	@Override
	public void initHistoria() throws Exception {
		//log.info("ejecutando metodo @initHistoria()");

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			// via_ingreso = admision_seleccionada.getVia_ingreso();
			accionForm(OpcionesFormulario.CONSULTAR, null);
			btnCancelar.setVisible(true);
		} else {
			if (admision_seleccionada != null) {
				toolbarbuttonPaciente_admisionado1
						.setLabel(admision_seleccionada.getPaciente()
								.getNombreCompleto());
				toolbarbuttonPaciente_admisionado2
						.setLabel(admision_seleccionada.getPaciente()
								.getNombreCompleto());
				macroRemision_interna.inicializarMacro(this,
						admision_seleccionada,
						admision_seleccionada.getVia_ingreso());

				if (parametros_empresa != null
						&& parametros_empresa.getSignos_enfermera().equals("S")) {
					toolbarbuttonCargar_signos.setVisible(true);
				} else {
					toolbarbuttonCargar_signos.setVisible(false);
				}

				if (admision_seleccionada.getAtendida()) {
					opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
					Hisc_urgencia hisc_urgencia = new Hisc_urgencia();
					hisc_urgencia
							.setCodigo_empresa(empresa.getCodigo_empresa());
					hisc_urgencia.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					hisc_urgencia.setNro_identificacion(admision_seleccionada
							.getNro_identificacion());
					hisc_urgencia.setNro_ingreso(admision_seleccionada
							.getNro_ingreso());
					hisc_urgencia.setVia_ingreso(via_ingreso);

					hisc_urgencia = getServiceLocator().getServicio(
							Hisc_urgenciaService.class)
							.consultar(hisc_urgencia);

					if (hisc_urgencia != null) {
						accionForm(OpcionesFormulario.MOSTRAR, hisc_urgencia);
						infoPacientes.cargarInformacion(admision_seleccionada,
								this, null);
						infoPacientes.setCodigo_historia(hisc_urgencia
								.getCodigo_historia());
					} else {
						groupboxEditar.setVisible(false);
						throw new Exception(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				} else {
					if (opciones_via_ingreso
							.equals(Opciones_via_ingreso.REGISTRAR)) {
						accionForm(OpcionesFormulario.REGISTRAR, null);
						btnCancelar.setVisible(false);
						if (macroImpresion_diagnostica.getLbxCausas_externas() != null) {
							String causa_externa = admision_seleccionada
									.getCausa_externa();
							if (causa_externa == null
									|| causa_externa.trim().isEmpty()) {
								causa_externa = IConstantes.CAUSA_EXTERNA_ENFERMEDAD_GENERAL;
							}
							Utilidades.setValueFrom(macroImpresion_diagnostica
									.getLbxCausas_externas(), causa_externa);
						}
					} else {
						accionForm(OpcionesFormulario.CONSULTAR, null);
						btnCancelar.setVisible(true);
					}
				}
				consultarInformacionAcompanante();
				consultarGlasgow();
			}
		}

		//log.info("tipo_urgencia ===> " + tipo_urgencia);

		if (tipo_urgencia.equals(IVias_ingreso.TIPO_URGENCIA_OBSTETRICA)) {
			//log.info("===> via ingresooo--- "
					//+ IVias_ingreso.TIPO_URGENCIA_OBSTETRICA);
			int edad = Integer.parseInt(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(admision_seleccionada.getPaciente()
									.getFecha_nacimiento()), "1", false));
			if (admision_seleccionada.getPaciente().getSexo().equals("F")) {
				if (edad >= 9 && edad <= 56) {
					//log.info("====> prueba " + edad);
					gbxEmbarazo_actual.setVisible(true);
				}
			}
			gbxHabitos_paciente.setVisible(true);
			gbxRevision_sistemas.setVisible(true);
			// gbxEmbarazosAnteriores.setVisible(true);
			gbxPartograma.setVisible(true);
			rowEmbarazo3.setVisible(true);
			rowEmbarazo2.setVisible(true);
			rowEmbarazo1.setVisible(true);
			rowEmbarazo4.setVisible(true);
			groupboxConsejeria.setVisible(true);
		} else if (tipo_urgencia.equals(IVias_ingreso.TIPO_URGENCIA_GENERAL)) {
			gbxEmbarazo_actual.setVisible(false);
			gbxHabitos_paciente.setVisible(false);
			gbxRevision_sistemas.setVisible(false);
			// gbxEmbarazosAnteriores.setVisible(false);
			gbxPartograma.setVisible(false);
			rowEmbarazo3.setVisible(false);
			rowEmbarazo2.setVisible(false);
			rowEmbarazo1.setVisible(false);
			rowEmbarazo4.setVisible(false);
			groupboxConsejeria.setVisible(false);
		} else {
			gbxPartograma.setVisible(false);
		}

		OcultarEmbarazoActual();

		//log.info("hisc_urgencia ===> " + infoPacientes.getCodigo_historia());

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
		mcSignosVitales.setFecha_nacimiento(admision_seleccionada.getPaciente()
				.getFecha_nacimiento());
		mcSignosVitales
				.setGenero(admision_seleccionada.getPaciente().getSexo());
		mcSignosVitales.inicializarParametrosAlertas();

		infoPacientes.cargarInformacion(admision_seleccionada, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() throws Exception {

						if (tbxAccion.getValue().equalsIgnoreCase(
								OpcionesFormulario.REGISTRAR.toString())) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("nro_identificacion",
									admision_seleccionada
											.getNro_identificacion());
							parametros.put("via_ingreso", via_ingreso);
							parametros.put("order_desc", "order_desc");

							List<Hisc_urgencia> listado_historias = getServiceLocator()
									.getServicio(Hisc_urgenciaService.class)
									.listar(parametros);

							SimpleDateFormat formatoFecha = new SimpleDateFormat(
									"yyyy-MM-dd hh:mm");

							if (!listado_historias.isEmpty()) {
								final Hisc_urgencia hisc_urgencia_aux = listado_historias
										.get(0);
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								Messagebox.show(
										"Existe una historia clinica creada para la fecha "
												+ formatoFecha
														.format(hisc_urgencia_aux
																.getFecha_ingreso())
												+ ". ¿Desea cargar la informacion?",
										"Cargar informacion anterior",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {

											@Override
											public void onEvent(Event event)
													throws Exception {
												if (event.getName().equals(
														"onYes")) {
													cargarInformacion_historia_anterior(hisc_urgencia_aux);
												}
											}
										});
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica");
								admision_seleccionada.setPrimera_vez("N");

							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision_seleccionada.setPrimera_vez("S");
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Hisc_urgencia hisc_hd = new Hisc_urgencia();
								hisc_hd.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc_hd.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc_hd.setCodigo_historia(codigo_historia_anterior);
								hisc_hd.setVia_ingreso(via_ingreso);
								hisc_hd.setTipo_urgencia(tipo_urgencia);

								hisc_hd = getServiceLocator().getServicio(
										Hisc_urgenciaService.class).consultar(
										hisc_hd);

								if (hisc_hd != null) {
									cargarInformacion_historia_anterior(hisc_hd);
								}

							}
						}

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior)
			throws Exception {
		accionForm(OpcionesFormulario.MODIFICAR, historia_anterior);
		tbxAccion.setValue(OpcionesFormulario.REGISTRAR.toString());
		mostrarDatosAnterior(historia_anterior);
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		//log.info("ejecutando metodo @initMostrar_datos()");
		Hisc_urgencia hisc_urgencia = (Hisc_urgencia) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil
					.deshabilitarComponentes(groupboxEditar, true, idsExc);
			if (hisc_urgencia.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);

		} else {
			if (hisc_urgencia.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}

			((Button) getFellow("btGuardar")).setVisible(true);
		}

		codigo_historia_anterior = hisc_urgencia.getCodigo_historia_anterior();
		tipo_historia = hisc_urgencia.getTipo_historia();
		toolbarbuttonTipo_historia.setVisible(false);
	}

	public void mostrarModuloPacientes() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_madre", admision_seleccionada);
		Window window = (Window) Executions.createComponents(
				"/pages/paciente.zul", this, parametros);
		window.setClosable(true);
		window.setTitle("Registrar recien nacido");
		window.setWidth("1000px");
		window.setHeight("80%");
		window.doModal();
	}

	public void adjuntarPartograma(Media media) throws Exception {
		try {
			if (media instanceof org.zkoss.image.Image) {
				// image.setContent((org.zkoss.image.Image)media);
				file_partograma = media;
				btnAdjuntar_partograma.setVisible(false);
				btnEliminar_partograma.setVisible(true);
				btnMostrar_partograma.setVisible(true);
				mostrarPartograma();
			} else {
				Messagebox.show(
						"El archivo que intenta adjuntar no es una imagen",
						"Formato de imagen invalido", Messagebox.OK,
						Messagebox.ERROR);

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void mostrarPartograma() {
		if (file_partograma != null) {
			Image image = new Image();
			image.setContent((org.zkoss.image.Image) file_partograma);

			Borderlayout borderlayout = new Borderlayout();
			borderlayout.setHeight("97%");
			Center center = new Center();
			center.setAutoscroll(true);
			center.appendChild(image);
			borderlayout.appendChild(center);

			Window window = new Window();
			window.setMaximizable(true);
			window.setTitle("Partograma adjunto");
			window.setBorder("normal");
			window.appendChild(borderlayout);
			window.setClosable(true);
			window.setParent(this);
			window.setMaximized(true);
			window.doModal();

		}
	}

	public void eliminarPartograma() {
		file_partograma = null;
		btnEliminar_partograma.setVisible(false);
		btnMostrar_partograma.setVisible(false);
		btnAdjuntar_partograma.setVisible(true);

	}

	public String getAntecedentesFamiliares() {
		StringBuffer ante_familiares = new StringBuffer();

		if (!bandboxAnte_fam_hipertension.getValue().isEmpty()
				|| !bandboxAnte_fam_obesos.getValue().isEmpty()
				|| !bandboxAnte_fam_ecv.getValue().isEmpty()
				|| !bandboxAnte_fam_diabetes.getValue().isEmpty()
				|| !bandboxAnte_fam_enf_coronaria.getValue().isEmpty()
				|| !bandboxAnte_fam_enf_mental.getValue().isEmpty()
				|| !bandboxAnte_fam_muerte_im_acv.getValue().isEmpty()
				|| !bandboxAnte_fam_cancer.getValue().isEmpty()
				|| !bandboxAnte_fam_dislipidemia.getValue().isEmpty()
				|| !bandboxAnte_fam_hematologia.getValue().isEmpty()
				|| !bandboxAnte_fam_alergicos.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_vih.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_sifilis.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_hepatitisb.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_tuberculosis.getValue()
						.isEmpty()
				|| !bandboxAnte_fam_infecciosa_lepra.getValue().isEmpty()
				|| !bandboxAnte_fam_nefropatias.getValue().isEmpty()
				|| !bandboxAnte_fam_asma.getValue().isEmpty()
				|| !bandboxAnte_fam_otros.getValue().isEmpty()) {

			ante_familiares.append("\n").append("- ANTECEDENTES FAMILIARES:\t");

			ante_familiares.append("\n").append("- ANTECEDENTES FAMILIARES:");

			if (!bandboxAnte_fam_hipertension.getValue().isEmpty()) {
				ante_familiares.append("Hipertension:").append(
						bandboxAnte_fam_hipertension.getValue() + "\t");
			}

			if (!bandboxAnte_fam_obesos.getValue().isEmpty()) {
				ante_familiares.append("Obesos:").append(
						bandboxAnte_fam_obesos.getValue() + "\t");
			}
			if (!bandboxAnte_fam_ecv.getValue().isEmpty()) {
				ante_familiares.append("Enfermedad cerebro-vascular:").append(
						bandboxAnte_fam_ecv.getValue() + "\t");
			}
			if (!bandboxAnte_fam_diabetes.getValue().isEmpty()) {
				ante_familiares.append("Diabetes:").append(
						bandboxAnte_fam_diabetes.getValue() + "\t");
			}
			if (!bandboxAnte_fam_enf_coronaria.getValue().isEmpty()) {
				ante_familiares.append("Enf. Coronaria:").append(
						bandboxAnte_fam_enf_coronaria.getValue() + "\t");
			}
			if (!bandboxAnte_fam_enf_mental.getValue().isEmpty()) {
				ante_familiares.append("Enfermedad Mental:").append(
						bandboxAnte_fam_enf_mental.getValue() + "\t");
			}
			if (!bandboxAnte_fam_muerte_im_acv.getValue().isEmpty()) {
				ante_familiares
						.append("Muerte en < 60 años (Infarto al miocardio):\t")
						.append(bandboxAnte_fam_muerte_im_acv.getValue() + "\t");
			}
			if (!bandboxAnte_fam_cancer.getValue().isEmpty()) {
				ante_familiares.append("Neoplásicos:").append(
						bandboxAnte_fam_cancer.getValue() + "\t");
			}
			if (!bandboxAnte_fam_dislipidemia.getValue().isEmpty()) {
				ante_familiares.append("Dislipidemia:").append(
						bandboxAnte_fam_dislipidemia.getValue() + "\t");
			}
			if (!bandboxAnte_fam_hematologia.getValue().isEmpty()) {
				ante_familiares.append("Hematología:").append(
						bandboxAnte_fam_hematologia.getValue() + "\t");
			}
			if (!bandboxAnte_fam_alergicos.getValue().isEmpty()) {
				ante_familiares.append("Alergicos:").append(
						bandboxAnte_fam_alergicos.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_vih.getValue().isEmpty()) {
				ante_familiares.append("VIH:").append(
						bandboxAnte_fam_infecciosa_vih.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_sifilis.getValue().isEmpty()) {
				ante_familiares.append("Sífilis:\t").append(
						bandboxAnte_fam_infecciosa_sifilis.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_hepatitisb.getValue().isEmpty()) {
				ante_familiares.append("Hepatitis B:\t")
						.append(bandboxAnte_fam_infecciosa_hepatitisb
								.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_tuberculosis.getValue().isEmpty()) {
				ante_familiares.append("Tuberculosis:\t").append(
						bandboxAnte_fam_infecciosa_tuberculosis.getValue()
								+ "\t");
			}
			if (!bandboxAnte_fam_infecciosa_lepra.getValue().isEmpty()) {
				ante_familiares.append("Lepra:\t").append(
						bandboxAnte_fam_infecciosa_lepra.getValue() + "\t");
			}
			if (!bandboxAnte_fam_nefropatias.getValue().isEmpty()) {
				ante_familiares.append("Nefropatias:\t").append(
						bandboxAnte_fam_nefropatias.getValue() + ",\t");
			}
			if (!bandboxAnte_fam_asma.getValue().isEmpty()) {
				ante_familiares.append("Asma:\t").append(
						bandboxAnte_fam_asma.getValue() + ",\t");
			}
			if (!bandboxAnte_fam_otros.getValue().isEmpty()) {
				ante_familiares.append("Otros:\t").append(
						bandboxAnte_fam_otros.getValue() + ",\t");
			}
			if (!tbxAnte_fam_observaciones.getValue().isEmpty()) {
				ante_familiares.append("Observaciones:\t").append(
						tbxAnte_fam_observaciones.getValue() + ",\t");
			}
			ante_familiares.append("\n");
		}

		ante_familiares.append("\n");

		return ante_familiares.toString();
	}

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */

		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t")
				.append("'" + tbxMotivo_consulta.getValue() + "'").append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (tbxParrafo_enfermedad_actual.getValue() != null
				&& !tbxParrafo_enfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxParrafo_enfermedad_actual.getValue())
					.append("\n");
		} else if (tbxEnfermedad_actual.getValue() != null
				&& !tbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxEnfermedad_actual.getValue()).append("\n");
		}
		if (!bandboxAnte_fam_hipertension.getValue().isEmpty()
				|| !bandboxAnte_fam_obesos.getValue().isEmpty()
				|| !bandboxAnte_fam_ecv.getValue().isEmpty()
				|| !bandboxAnte_fam_diabetes.getValue().isEmpty()
				|| !bandboxAnte_fam_enf_coronaria.getValue().isEmpty()
				|| !bandboxAnte_fam_enf_mental.getValue().isEmpty()
				|| !bandboxAnte_fam_muerte_im_acv.getValue().isEmpty()
				|| !bandboxAnte_fam_cancer.getValue().isEmpty()
				|| !bandboxAnte_fam_dislipidemia.getValue().isEmpty()
				|| !bandboxAnte_fam_hematologia.getValue().isEmpty()
				|| !bandboxAnte_fam_alergicos.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_vih.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_sifilis.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_hepatitisb.getValue().isEmpty()
				|| !bandboxAnte_fam_infecciosa_tuberculosis.getValue()
						.isEmpty()
				|| !bandboxAnte_fam_infecciosa_lepra.getValue().isEmpty()
				|| !bandboxAnte_fam_nefropatias.getValue().isEmpty()
				|| !bandboxAnte_fam_asma.getValue().isEmpty()
				|| !bandboxAnte_fam_otros.getValue().isEmpty()) {

			informacion_clinica.append("\n").append(
					"- ANTECEDENTES FAMILIARES:\t");

			informacion_clinica.append("\n").append(
					"- ANTECEDENTES FAMILIARES:");

			if (!bandboxAnte_fam_hipertension.getValue().isEmpty()) {
				informacion_clinica.append("Hipertension:").append(
						bandboxAnte_fam_hipertension.getValue() + "\t");
			}

			if (!bandboxAnte_fam_obesos.getValue().isEmpty()) {
				informacion_clinica.append("Obesos:").append(
						bandboxAnte_fam_obesos.getValue() + "\t");
			}
			if (!bandboxAnte_fam_ecv.getValue().isEmpty()) {
				informacion_clinica.append("Enfermedad cerebro-vascular:")
						.append(bandboxAnte_fam_ecv.getValue() + "\t");
			}
			if (!bandboxAnte_fam_diabetes.getValue().isEmpty()) {
				informacion_clinica.append("Diabetes:").append(
						bandboxAnte_fam_diabetes.getValue() + "\t");
			}
			if (!bandboxAnte_fam_enf_coronaria.getValue().isEmpty()) {
				informacion_clinica.append("Enf. Coronaria:").append(
						bandboxAnte_fam_enf_coronaria.getValue() + "\t");
			}
			if (!bandboxAnte_fam_enf_mental.getValue().isEmpty()) {
				informacion_clinica.append("Enfermedad Mental:").append(
						bandboxAnte_fam_enf_mental.getValue() + "\t");
			}
			if (!bandboxAnte_fam_muerte_im_acv.getValue().isEmpty()) {
				informacion_clinica
						.append("Muerte en < 60 años (Infarto al miocardio):\t")
						.append(bandboxAnte_fam_muerte_im_acv.getValue() + "\t");
			}
			if (!bandboxAnte_fam_cancer.getValue().isEmpty()) {
				informacion_clinica.append("Neoplásicos:").append(
						bandboxAnte_fam_cancer.getValue() + "\t");
			}
			if (!bandboxAnte_fam_dislipidemia.getValue().isEmpty()) {
				informacion_clinica.append("Dislipidemia:").append(
						bandboxAnte_fam_dislipidemia.getValue() + "\t");
			}
			if (!bandboxAnte_fam_hematologia.getValue().isEmpty()) {
				informacion_clinica.append("Hematología:").append(
						bandboxAnte_fam_hematologia.getValue() + "\t");
			}
			if (!bandboxAnte_fam_alergicos.getValue().isEmpty()) {
				informacion_clinica.append("Alergicos:").append(
						bandboxAnte_fam_alergicos.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_vih.getValue().isEmpty()) {
				informacion_clinica.append("VIH:").append(
						bandboxAnte_fam_infecciosa_vih.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_sifilis.getValue().isEmpty()) {
				informacion_clinica.append("Sífilis:\t").append(
						bandboxAnte_fam_infecciosa_sifilis.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_hepatitisb.getValue().isEmpty()) {
				informacion_clinica.append("Hepatitis B:\t")
						.append(bandboxAnte_fam_infecciosa_hepatitisb
								.getValue() + "\t");
			}
			if (!bandboxAnte_fam_infecciosa_tuberculosis.getValue().isEmpty()) {
				informacion_clinica.append("Tuberculosis:\t").append(
						bandboxAnte_fam_infecciosa_tuberculosis.getValue()
								+ "\t");
			}
			if (!bandboxAnte_fam_infecciosa_lepra.getValue().isEmpty()) {
				informacion_clinica.append("Lepra:\t").append(
						bandboxAnte_fam_infecciosa_lepra.getValue() + "\t");
			}
			if (!bandboxAnte_fam_nefropatias.getValue().isEmpty()) {
				informacion_clinica.append("Nefropatias:\t").append(
						bandboxAnte_fam_nefropatias.getValue() + ",\t");
			}
			if (!bandboxAnte_fam_asma.getValue().isEmpty()) {
				informacion_clinica.append("Asma:\t").append(
						bandboxAnte_fam_asma.getValue() + ",\t");
			}
			if (!bandboxAnte_fam_otros.getValue().isEmpty()) {
				informacion_clinica.append("Otros:\t").append(
						bandboxAnte_fam_otros.getValue() + ",\t");
			}
			if (!tbxAnte_fam_observaciones.getValue().isEmpty()) {
				informacion_clinica.append("Observaciones:\t").append(
						tbxAnte_fam_observaciones.getValue() + ",\t");
			}
			informacion_clinica.append("\n");
		}

		informacion_clinica.append("\n");

		informacion_clinica.append("- SIGNOS VITALES\n");
		if (!mcSignosVitales.getFrecuencia_cardiaca().isEmpty()) {
			informacion_clinica.append("\tFrecuencia cardiaca: ")
					.append(mcSignosVitales.getFrecuencia_cardiaca())
					.append("\n");
		}

		if (!mcSignosVitales.getFrecuencia_respiratoria().isEmpty()) {
			informacion_clinica.append("\tFrecuencia respiratoria: ")
					.append(mcSignosVitales.getFrecuencia_respiratoria())
					.append("\n");
		}

		if (!mcSignosVitales.getTemperatura().isEmpty()) {
			informacion_clinica.append("\tTemperatura: ")
					.append(mcSignosVitales.getTemperatura()).append("\n");
		}

		if (!mcSignosVitales.getPeso().isEmpty()) {
			informacion_clinica.append("\tPeso(kg): ")
					.append(mcSignosVitales.getPeso()).append("\n");
		}

		if (!mcSignosVitales.getTalla().isEmpty()) {
			informacion_clinica.append("\tTalla: ")
					.append(mcSignosVitales.getTalla()).append("\n");
		}

		if (!mcSignosVitales.getIMC().isEmpty()) {
			informacion_clinica.append("\tIMC: ")
					.append(mcSignosVitales.getIMC()).append(" - ")
					.append(mcSignosVitales.getAlerta_imc()).append("\n")
					.append("\n");
		}

		informacion_clinica.append("- EXAMEN FISICO").append("\n");

		if (!tbxFisico_estado_general.getValue().isEmpty()) {
			informacion_clinica.append("\tEstado general: ")
					.append(tbxFisico_estado_general.getValue()).append("\n");
		}

		if (!tbxAnalisis_recomendaciones.getValue().isEmpty()) {
			informacion_clinica.append("\tAnalisis y recomendaciones: ");
			informacion_clinica.append(tbxAnalisis_recomendaciones.getValue())
					.append("\n");
		}

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

		if (cie != null) {
			informacion_clinica.append("\tRelacionado 1: ")
					.append(impresion_diagnostica.getCie_relacionado1())
					.append(" ").append(cie != null ? cie.getNombre() : "")
					.append("\n");

			informacion_clinica
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado1(),
							"tipo_diagnostico", this)).append("\n");
		}

		/* relacionado 2 */
		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		if (cie != null) {
			informacion_clinica.append("\tRelacionado 2: ")
					.append(impresion_diagnostica.getCie_relacionado2())
					.append(" ").append(cie != null ? cie.getNombre() : "")
					.append("\n");

			informacion_clinica
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado2(),
							"tipo_diagnostico", this)).append("\n");
		}

		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		if (cie != null) {
			informacion_clinica.append("\tRelacionado 3: ")
					.append(impresion_diagnostica.getCie_relacionado3())
					.append(" ").append(cie != null ? cie.getNombre() : "")
					.append("\n");

			informacion_clinica
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado3(),
							"tipo_diagnostico", this)).append("\n");
		}

		return informacion_clinica.toString();
	}

	@Override
	public String getPersonaAcompaniante() {
		return tbxAcompanante.getValue();
	}

	@Override
	public String getIdentificacionAcompaniante() {
		return dbxIdentificacion_acompanante.getText() + "";
	}

	@Override
	public String getTelefonoAcompaniante() {
		return dbxTelefono_acompanante.getText();
	}

	@Override
	public String getServicioSolicitaReferencia1() {
		return "URGENCIAS";
	}

	// public void mostrarFechaParto() {
	// Boolean visible = dtbxAnt_gin_fecha_espectante_parto.isVisible();
	// if (!visible) {
	// btnMostarFecha.setLabel("Ocultar fecha esperada de parto");
	// dtbxAnt_gin_fecha_espectante_parto.setVisible(true);
	// } else {
	// btnMostarFecha.setLabel("Mostrar fecha esperada de parto");
	// dtbxAnt_gin_fecha_espectante_parto.setVisible(false);
	// }
	// }

	public void onSeleccionarOtros_ciclos(Listbox listboxCliclo,
			Row rowOtrosCiclos) {
		if (listboxCliclo.getSelectedItem().getValue().toString().equals("3")) {
			rowOtros_ciclos.setVisible(true);
		} else {
			rowOtros_ciclos.setVisible(false);
		}
	}

	public His_triage consultarTriage() {
		His_triage his_triage = new His_triage();
		his_triage.setCodigo_empresa(codigo_empresa);
		his_triage.setCodigo_sucursal(codigo_sucursal);
		his_triage.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		his_triage.setIdentificacion(admision_seleccionada
				.getNro_identificacion());
		his_triage = his_triageService.consultar(his_triage);

		return his_triage;
	}

	public void consultarInformacionAcompanante() {

		His_triage his_triage = consultarTriage();

		if (his_triage != null) {
			// Acompañante
			tbxAcompanante.setValue(his_triage.getAcompanante());
			dbxIdentificacion_acompanante.setText(his_triage
					.getIdentificacion_acompanante());
			Utilidades.seleccionarListitem(lbxRelacion,
					his_triage.getRelacion());
			ibxEdad_acompanante.setValue(ConvertidorDatosUtil
					.convertirDatot(his_triage.getEdad_acompanante()));

			Utilidades.seleccionarListitem(lbxEscolaridad_acompanante,
					his_triage.getEscolaridad_acompanante());
			dbxTelefono_acompanante.setText(his_triage
					.getTelefono_acompanante());

			if (tbxAcompanante.getValue().isEmpty()) {
				tbxAcompanante.setValue("N/A");
			}

			if (dbxIdentificacion_acompanante.getValue() == null) {
				dbxIdentificacion_acompanante.setValue(0.0);
			}
			if (lbxRelacion.getSelectedIndex() == 0) {
				lbxRelacion.setSelectedIndex(9);
			}
			if (ibxEdad_acompanante.getValue() == 0) {
				ibxEdad_acompanante.setValue(0);
			}
			if (lbxEscolaridad_acompanante.getSelectedIndex() == 0) {
				lbxEscolaridad_acompanante.setSelectedIndex(13);
			}
			if (dbxTelefono_acompanante.getValue() == null) {
				dbxTelefono_acompanante.setValue(0.0);
			}

			if(getParametros_empresa().getCargar_signos_vitales_triage().equals("S")){
				cargarSignosVitalesEnfermera(his_triage);
			}
		}else{
			if(getParametros_empresa().getCargar_signos_vitales_triage().equals("S")){
				cargarSignosVitalesEnfermera();
			}
		}
	}

	public void consultarGlasgow() {

		His_triage his_triage = consultarTriage();
		if (his_triage != null) {
			if (his_triage.getEscala_glasgow().equals("S")) {
				gbxMacroGlasgow.setVisible(true);
				macroGlasgow.mostrarEscalaGlasgow(
						his_triage.getRespuesta_motora(),
						his_triage.getRespuesta_verbal(),
						his_triage.getApertura_ocular(), false);
			} else {
				gbxMacroGlasgow.setVisible(false);
			}
		}
	}

	public void imprimir() throws Exception {
		Long nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia == null) {
			Messagebox.show("La historia no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		if (admision_seleccionada.getVia_ingreso().equals(
				IVias_ingreso.HOSPITALIZACIONES)) {
			paramRequest.put("name_report", "Historia_clinica_hospitalizacion");
		} else {
			paramRequest.put("name_report", "Historia_clinica_urgencia");
		}
		paramRequest.put("nro_historia", nro_historia);
		paramRequest.put("formato", "pdf");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	public void verificarEmbarazo() {
		rowEmbarazoFep.setVisible(rdbEstado_embarazo.getSelectedIndex() == 0);
	}

}
