package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Antecedentes_personales;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.His_formato_tuberculosis;
import healthmanager.modelo.bean.Hisc_consulta_externa;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Revision_sistema;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.His_formato_tuberculosisService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.Util;

import java.sql.Timestamp;
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

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.Clients;
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
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
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
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.GlasgowMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.SignosVitalesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.macros.revision_sistemas.TreeItemDinamicRevisionSistemasMacro;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.ConvertidorXmlToMap;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_consulta_externaAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static final long serialVersionUID = -4103271389225457188L;

	private Admision admision_seleccionada;
	private Citas cita_seleccionada;
	private Opciones_via_ingreso opciones_via_ingreso;
	private String via_ingreso = "1";
	private Boolean mostrar_boton_consultar = true;

	private ContenedorPaginasMacro tabboxContendor;

	// Componentes //
	@View
	private Textbox tbxDes_vida_Marital;
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
	private Textbox tbxobservacion_confiable;

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

	@View
	private Textbox tbxAcompaniante;
	@View
	private Listbox lbxRelacion;
	@View
	private Textbox tbxOtro_acompaniante;
	@View
	private Textbox tbxCedula_acomp;
	@View
	private Textbox tbxEsquema_vacunacion;
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
	private Vbox box_citologia;
	@View
	private Vbox box_mamo;
	@View
	private Hbox box_anticonceptivos;

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
	private Textbox bandboxAnte_fam_otros;

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
	private Intbox ibxGenerales_embarazos;
	@View
	private Intbox ibxMalformaciones_embarazos;
	@View
	private Radiogroup chbAnt_gin_tiene_citologia;
	@View
	private Datebox dtbxAnt_gin_fecha_ultima_citologia;
	@View
	private Radiogroup chbAnt_gin_tiene_mamografia;
	@View
	private Datebox dtbxAnt_gin_fecha_mamografia;
	@View
	private Radiogroup chbAnt_gin_tiene_planificacion;
	@View
	private Datebox dtbxAnt_gin_fecha_incio_planificacion;
	@View
	private Textbox tbxAnt_gin_citologia_resultado;
	@View
	private Textbox tbxAnt_gin_mamografia_resultado;

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

	/* para cuando es ninio */
	@View
	private Intbox tbxAnt_nios_peso;
	@View
	private Intbox tbxAnt_nios_talla;
	@View
	private Textbox tbxAnt_nios_psicomotor;
	@View
	private Textbox tbxAnt_nios_dieta;
	@View
	private Textbox tbxAnt_nios_vacunas;
	@View
	private Doublebox dbxTel_acompaniante;
	/**
	 * Nueva revison por sistemas.
	 *
	 */
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
	private Textbox tbxFisico_otorrino;
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
	private Textbox tbxFisico_neurologico;
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
	private Textbox tbxlocalizacion;
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
	private Datebox dtbxFecha_vida_marital;
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
	private List<Elemento> elementosMetodosPlanificacion;

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
	private Div divModuloFarmacologico;

	@View
	private Textbox tbxPlan_seguimiento;

	@View
	private Div divModuloOrdenamiento;

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
	private Listbox lbxOtorrrino_list;
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
	private Listbox lbxNeurologico_list;
	@View
	private Listbox lbxMental_list;

	@View
	private Hbox box_Psicofarmacos;
	@View
	private Hbox box_Otras_adicciones;

	@View
	private Textbox tbxAnalisis_del_caso;

	@View
	private Toolbarbutton toolbarbuttonCargar_signos;

	private String nro_ingreso_admision;

	private final String[] idsExc = { "celdaPrestador",
			"macroImpresion_diagnostica", "dtbxAnt_gin_fecha_ultima_regla",
			"dtbxAnt_gin_fecha_espectante_parto", "tbxValue",
			"tbxNro_identificacion", "tbxNro_ingreso", "tbxTipo_hc", "tbxEdad",
			"tbxArea_int", "lbxParameter", "lbxTipo_disnostico", "northEditar",
			"btnLimpiar_prestador", "lbxSintomatico_respiratorio",
			"lbxSintomatico_piel", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "dtbxPpd", "dtbxTorax", "tbxAccion",
			"toolbarbuttonCargar_signos", "dtbxAnt_gin_menarca",
			"divModuloRemisiones_externas", "groupboxConsultar",
			"lbxTipo_historia" };

	// private Component remisionesAction;
	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;

	// ------- Evaluacion Tuberculosis
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	private His_formato_tuberculosisService his_formato_tuberculosisService;
	@View
	private Button btnMostrar_tuberculosis;
	@View
	private Groupbox gbxTuberculosis;

	@View
	private Radiogroup rdbContacto;
	@View
	private Textbox tbxContacto_quien;
	@View
	private Radiogroup rdbTos_persistente;
	@View
	private Intbox ibxTos_dias;
	@View
	private Radiogroup rdbFiebre;
	@View
	private Intbox ibxFiebre_dias;
	@View
	private Radiogroup rdbPerdida_peso;
	@View
	private Radiogroup rdbAdenomegalia;
	@View
	private Textbox tbxAdenomegalia_explique;
	@View
	private Datebox dtbxPpd;
	@View
	private Textbox tbxPpd_resultado;
	@View
	private Datebox dtbxTorax;
	@View
	private Textbox tbxTorax_resultado;
	@View
	private Checkbox chbSigno_general;
	@View
	private Checkbox chbRigidez;
	@View
	private Checkbox chbComportamiento;
	@View
	private Checkbox chbTiraje;
	@View
	private Checkbox chbInfeccion;
	@View
	private Checkbox chbDesnutricion;
	@View
	private Checkbox chbCultivo_esputo;
	@View
	private Checkbox chbCultivo_jugo;
	@View
	private Checkbox chbBaciloscopia;
	@View
	private Checkbox chbHistopatologia;
	@View
	private Checkbox chbPcr;
	@View
	private Textbox tbxObservaciones_tuberculosis;

	@View
	private Label lbContacto_quien;
	@View
	private Label lbTos_dias;
	@View
	private Label lbdias;
	@View
	private Label lbFiebre_dias;
	@View
	private Label lbdias2;
	@View
	private Label lbAdenomegalia_explique;

	// ginecostetricos
	@View
	private Listbox lbx_conf_fur;

	@View
	private Groupbox gbxGPAC;

	@View
	private Datebox dtbxAnt_gin_fecha_ultima_regla;
	@View
	private Textbox tbxAnt_gin_fecha_ultima_regla_desc;
	@View
	private Datebox dtbxAnt_gin_fecha_espectante_parto;

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
	private Toolbarbutton btnImprimir;

	@View
	private Listbox lbxFormato;

	@View
	private Textbox tbxOtro_ciclo;
	@View
	private Row rowOtros_ciclos;
	@View
	private Textbox tbxDescripcion_menarca;

	@Override
	public void init() throws Exception {
		loadComponents();
		listarCombos();
		mcSignosVitales.setZkWindow(this);
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision_seleccionada = (Admision) map
					.get(IVias_ingreso.ADMISION_PACIENTE);

			macroRemision_interna.deshabilitarCheck(admision_seleccionada,
					admision_seleccionada.getVia_ingreso());

			cita_seleccionada = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			if (map.containsKey("via_ingreso")) {
				via_ingreso = (String) map.get("via_ingreso");
			}
		}
		if (map.containsKey("_MOSTRAR_BTN_CONSULTAR")) {
			mostrar_boton_consultar = (Boolean) map
					.get("_MOSTRAR_BTN_CONSULTAR");
		}
		if (map.containsKey(IVias_ingreso.PADRE_TABS)) {
			tabboxContendor = (ContenedorPaginasMacro) map
					.get(IVias_ingreso.PADRE_TABS);
		}
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		rol_usuario = (String) request.getSession().getAttribute(
				IParametrosSesion.PARAM_ROL_USUARIO);
	}

	private void loadComponents() {
		cargarAntecedentesPersonales(new HashMap<String, Antecedentes_personales>());
		loadMetodosPlanificacion();
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
			if (!antecedentes_personales.getCodigo_antecente().equals("32")) {
				tbxObservaciones.setValue(antecedentes_personales
						.getObservacion());
			} else {
				Utilidades.mostrarXmlTextbox(tbxObservaciones,
						ConvertidorXmlToMap
								.convertirToMap(antecedentes_personales
										.getObservacion()));
			}
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
							tbxObservaciones.setText("");
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

	private void loadMetodosPlanificacion() {
		elementosMetodosPlanificacion = getServiceLocator()
				.getElementoService().listar("mtd_planificar");
		if (elementosMetodosPlanificacion.isEmpty()) {
			Label label = new Label(
					"No hay metodos de planificacion disponibles..!!!");
			box_anticonceptivos.appendChild(label);
		} else {
			Rows rows = crearGridMetodos_anticonceptivos();
			int max = 4;
			int count = 0;
			for (Elemento elemento : elementosMetodosPlanificacion) {
				// row_metodos_anticonceptivos.appendChild(createRowMetdoPlanificacion(elemento));
				rows.appendChild(crearRowMetdoPlanificacion(elemento));
				if ((++count) > max) {
					rows = crearGridMetodos_anticonceptivos();
					count = 0;
				}
			}
		}
	}

	private Rows crearGridMetodos_anticonceptivos() {
		Grid grid = new Grid();
		grid.setStyle("border:none");
		grid.setClass("GridSinBorde");
		Columns columns = new Columns();

		Column column = new Column("");
		column.setWidth("5%");
		columns.appendChild(column);
		column = new Column("");
		columns.appendChild(column);
		column = new Column("");
		column.setWidth("5%");
		columns.appendChild(column);
		grid.appendChild(columns);

		Rows rows = new Rows();
		grid.appendChild(rows);
		box_anticonceptivos.appendChild(grid);
		return rows;
	}

	private Row crearRowMetdoPlanificacion(Elemento elemento) {
		Row row = new Row();
		row.appendChild(new Cell());
		Checkbox checkbox = new Checkbox();
		checkbox.setLabel("" + elemento.getDescripcion());
		checkbox.setValue(elemento.getCodigo());
		checkbox.setId("checkbox_mtd_planificar_" + elemento.getCodigo());
		row.appendChild(checkbox);
		row.appendChild(new Cell());
		return row;
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbx_conf_fur, true,
				getServiceLocator());
		Utilidades
				.listarElementoListbox(lbxRelacion, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxIntensidad, true,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw, idsExc);
		desavilitarRadios(chbAnt_gin_tiene_citologia, sw);
		desavilitarRadios(chbAnt_gin_tiene_mamografia, sw);
		desavilitarRadios(chbAnt_gin_tiene_planificacion, sw);
		lbxSintomatico_piel.setDisabled(sw);
		lbxSintomatico_respiratorio.setDisabled(sw);

		if (!sw) {
			((Button) getFellow("btGuardar")).setDisabled(false);
			((Button) getFellow("btGuardar")).setImage("/images/Save16.gif");
		} else {
			((Button) getFellow("btGuardar")).setDisabled(true);
			((Button) getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}

		if (rol_usuario.equals("05")) {
			bandboxPrestador.setDisabled(true);
		} else {
			bandboxPrestador.setDisabled(sw);
		}
	}

	private void desavilitarRadios(Radiogroup radiogroup, boolean sw) {
		for (Object radio : radiogroup.getChildren()) {
			if (radio instanceof Radio) {
				((Radio) radio).setDisabled(sw);
			}
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
				tbxlocalizacion.setVisible(false);
				lbIrradiacion.setVisible(false);
				tbxIrradiacion.setVisible(false);
				lbLocalizacion.setVisible(false);
				hbxLocalizacion.setVisible(false);
				tbxlocalizacion.setVisible(false);
				lbCaracteristicas_Dolor.setVisible(false);
				tbxCaracteristicas_dolor.setVisible(false);
				lbRelacionado.setVisible(false);
				tbxRelacionado.setVisible(false);
				lbEvolucion_cuadro.setVisible(false);
				tbxEvolucion.setVisible(false);
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
				tbxlocalizacion.setVisible(false);
				lbIrradiacion.setVisible(false);
				tbxIrradiacion.setVisible(false);
				lbLocalizacion.setVisible(false);
				hbxLocalizacion.setVisible(false);
				tbxlocalizacion.setVisible(false);
				lbCaracteristicas_Dolor.setVisible(false);
				tbxCaracteristicas_dolor.setVisible(false);
				lbRelacionado.setVisible(true);
				tbxRelacionado.setVisible(true);
				lbEvolucion_cuadro.setVisible(true);
				tbxEvolucion.setVisible(true);
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
				tbxlocalizacion.setVisible(true);
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
				lbxIntensidad.setVisible(true);
				lbIntensidad.setVisible(true);
				tbxParrafo_enfermedad_actual.setVisible(true);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void onSeleccionarRelacionAcompaniante() {
		if (lbxRelacion.getSelectedItem().getValue().toString().equals("8")) {
			tbxOtro_acompaniante.setVisible(true);
		} else {
			tbxOtro_acompaniante.setVisible(false);
		}
	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
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
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Cie> list = getServiceLocator().getServicio(
					GeneralExtraService.class).listar(Cie.class, parameters);

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
			Hisc_consulta_externa hisc_consulta_externa) {
		Map<String, Antecedentes_personales> mapaAntecedentesPersonales = new HashMap<String, Antecedentes_personales>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("codigo_historia", hisc_consulta_externa.getCodigo_historia());

		List<Antecedentes_personales> listaAntecedentes_personales = getServiceLocator()
				.getAntecedentesPersonalesService().listar(map);
		for (Antecedentes_personales antecedentes_personales : listaAntecedentes_personales) {
			mapaAntecedentesPersonales.put(
					antecedentes_personales.getCodigo_antecente(),
					antecedentes_personales);
		}
		return mapaAntecedentesPersonales;
	}

	private List<Revision_sistema> obtenerListadoRevisionSistema(
			Hisc_consulta_externa hisc_consulta_externa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("codigo_historia", hisc_consulta_externa.getCodigo_historia());
		map.put("nro_identificacion",
				hisc_consulta_externa.getNro_identificacion());

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

	public void mostrarPlanificacion(String value) {
		if (value.equals("S")) {
			box_anticonceptivos.setVisible(true);
			dtbxAnt_gin_fecha_incio_planificacion.setVisible(true);
		} else {
			box_anticonceptivos.setVisible(false);
			dtbxAnt_gin_fecha_incio_planificacion.setVisible(false);
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
			boolean deshabilitar = true;
			if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {
				bandboxPrestador.setDisabled(true);
				bandboxPrestador.setButtonVisible(false);
				deshabilitar = false;
			}
			mostrarDatos(dato, opciones_formulario, deshabilitar);
			if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {
				tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
			}
		} else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
			onOpcionFormularioConsultar();
		}
	}

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsultar.setVisible(false);
		groupboxEditar.setVisible(true);
		seleccion(rdbseleccion);
		limpiarDatos();
		if (admision_seleccionada != null) {
			this.nro_ingreso_admision = admision_seleccionada.getNro_ingreso();
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloRemisiones();
			cargarPrestador(usuarios.getCodigo());
			// cargarPrestador(admision_seleccionada.getCodigo_medico());
		}
		btnImprimir.setVisible(false);
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
			parameters.put("tipo_area_asistencial", via_ingreso);

			// se adiciona para separar la historia de consulta externa con la
			// de especialista
			parameters.put("via_ingreso", via_ingreso);

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

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Hisc_consulta_externa> lista_datos = getServiceLocator()
					.getHisc_consulta_externaService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_consulta_externa hisc_consulta_externa : lista_datos) {
				rowsResultado.appendChild(crearFilas(hisc_consulta_externa,
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

		final Hisc_consulta_externa hisc_consulta_externa = (Hisc_consulta_externa) objeto;

		Prestadores prestadores = new Prestadores();
		prestadores.setCodigo_empresa(codigo_empresa);
		prestadores.setCodigo_sucursal(codigo_sucursal);
		prestadores.setNro_identificacion(hisc_consulta_externa
				.getCreacion_user());
		prestadores = getServiceLocator().getPrestadoresService().consultar(
				prestadores);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_consulta_externa.getCodigo_historia()
				+ ""));
		fila.appendChild(new Label(hisc_consulta_externa
				.getNro_identificacion() + ""));

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

		fila.appendChild(new Label(prestadores != null ? (prestadores
				.getNombres() + " " + prestadores.getApellidos())
				: hisc_consulta_externa.getCreacion_user()));

		Datebox datebox = new Datebox(hisc_consulta_externa.getFecha_ingreso());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd hh-mm-ss");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		datebox.setInplace(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_consulta_externa.getTipo_historia()
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
						if (parametros_empresa.getMostrar_historias_pag()) {
							mostrarHistoriaPagina(
									hisc_consulta_externa.getCodigo_historia(),
									via_ingreso, tabboxContendor,
									"CONSULTA EXTERNA");
						} else {
							groupboxConsultar.setVisible(false);
							groupboxEditar.setVisible(true);
							tipo_historia = hisc_consulta_externa
									.getTipo_historia();
							cargarInformacion_paciente();
							mostrarDatos(hisc_consulta_externa,
									OpcionesFormulario.MOSTRAR, true);
						}

					}
				});
		hlayout.appendChild(btn_mostrar);

		// btn_mostrar = new Toolbarbutton();
		// if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
		// btn_mostrar.setVisible(false);
		// }
		// btn_mostrar.setImage("/images/borrar.gif");
		// btn_mostrar.setTooltiptext("Eliminar");
		// btn_mostrar.setStyle("cursor: pointer");
		// btn_mostrar.addEventListener(Events.ON_CLICK,
		// new EventListener<Event>() {
		// @Override
		// public void onEvent(Event arg0) throws Exception {
		// Messagebox
		// .show("Esta seguro que desea eliminar este registro? ",
		// "Eliminar Registro",
		// Messagebox.YES + Messagebox.NO,
		// Messagebox.QUESTION,
		// new org.zkoss.zk.ui.event.EventListener<Event>() {
		// public void onEvent(Event event)
		// throws Exception {
		// if ("onYes".equals(event
		// .getName())) {
		// // do the thing
		// eliminarDatos(hisc_consulta_externa);
		// buscarDatos();
		// }
		// }
		// });
		// }
		// });
		// hlayout.appendChild(new Space());
		// hlayout.appendChild(btn_mostrar);
		fila.appendChild(hlayout);

		return fila;
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_consulta_externa hisc_consulta_externa = (Hisc_consulta_externa) obj;
		try {
			int result = getServiceLocator().getHisc_consulta_externaService()
					.eliminar(hisc_consulta_externa, getUsuarios().getCodigo());
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
		// log.info("ejecutando metodo @cargarPrestador()");
		if (rol_usuario.equals(IConstantes.ROL_MEDICO_CONSULTA_EXTERNA)) {
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
				} else {
					bandboxPrestador
							.setValue((prestadores != null ? prestadores
									.getNro_identificacion() : "000000000"));
					tbxNomPrestador.setValue((prestadores != null ? prestadores
							.getNombres() + " " + prestadores.getApellidos()
							: "MEDICO POR DEFECTO"));
				}
			} else {
				bandboxPrestador.setValue("000000000");
				tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			}
		}
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
		limpiarAntecedentes();
		box_alcohol.setVisible(false);
		dtbxAnt_gin_menarca.setValue(null);
		box_tabaquismo.setVisible(false);
		box_ejercicio.setVisible(false);
		box_dietas.setVisible(false);
		box_factores_psicologicos.setVisible(false);
		box_Psicofarmacos.setVisible(false);
		box_Otras_adicciones.setVisible(false);

		mostrarPlanificacion("N");
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		infoPacientes.validarInformacionPaciente();
		boolean valido = receta_ripsAction.validarFormExterno();
		// String mensaje = "";
		if (valido) {
			valido = orden_servicioAction.validarFormExterno();
		}

		if (valido) {
			valido = remisiones_externasAction.validarRemision();
		}

		if (valido) {
			FormularioUtil.validarCamposObligatorios(tbxOtro_acompaniante,
					tbxMotivo_consulta, tbxEnfermedad_actual,
					tbxParrafo_enfermedad_actual, tbxFisico_estado_general,
					tbxAnalisis_del_caso, tbxEducacional_paciente,
					tbxNo_farmocologico, tbxPlan_seguimiento);
		}

		return valido;
	}

	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				Hisc_consulta_externa hisc_consulta_externa = getBean();
				hisc_consulta_externa.setTipo_historia(tipo_historia);
				hisc_consulta_externa
						.setCodigo_historia_anterior(hisc_consulta_externa
								.getCodigo_historia());
				hisc_consulta_externa.setFecha_cierre(new Timestamp(
						(new Date()).getTime()));
				hisc_consulta_externa.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());

				List<Antecedentes_personales> listadoAntecedentes = obtenerAntecedentesPersonales();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("hisc_consulta_externa", hisc_consulta_externa);
				datos.put("sigvitales", mcSignosVitales.obtenerSigvitales());
				datos.put("listadoAntecedentes", listadoAntecedentes);
				datos.put("admision", admision_seleccionada);
				datos.put("listadoRevisiones",
						treeItemDinamicRevisionSistemasMacro
								.getElementos_seleccionados());
				datos.put("cita_seleccionada", cita_seleccionada);
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());
				datos.put("accion", tbxAccion.getValue());

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

				getServiceLocator().getHisc_consulta_externaService()
						.guardarDatos(datos);

				// log.info("admision_seleccionada ====> Estado despues de guarda '"
				// + admision_seleccionada.getEstado() + "'");
				mostrarDatosAutorizacion(datos);
				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}

				// Guardar Tuberculosis
				guardarTuberculosis();

				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hisc_consulta_externa
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

				actualizarAutorizaciones(admision_seleccionada,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision_seleccionada,
						getInformacionClinica(), this);

				btnImprimir.setVisible(true);

				MensajesUtil.mensajeInformacion("Informacion ...",
						"Los datos se guardaron satisfactoriamente");
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

	public void mostrarDatos(Object obj, OpcionesFormulario opcionesFormulario,
			boolean desabilitar) throws Exception {
		Hisc_consulta_externa hisc_consulta_externa = (Hisc_consulta_externa) obj;
		try {
			if (opcionesFormulario == OpcionesFormulario.MOSTRAR) {
				this.nro_ingreso_admision = hisc_consulta_externa
						.getNro_ingreso();
				this.via_ingreso = hisc_consulta_externa.getVia_ingreso();
				infoPacientes.setCodigo_historia(hisc_consulta_externa
						.getCodigo_historia());
				cargarPrestador(hisc_consulta_externa.getCodigo_prestador());

				onMostrarModuloRemisiones();

				infoPacientes.setFecha_inicio(hisc_consulta_externa
						.getFecha_ingreso());
				infoPacientes.setFecha_cierre(true,
						hisc_consulta_externa.getFecha_cierre());

				tbxMotivo_consulta.setValue(hisc_consulta_externa
						.getMotivo_consulta());
				tbxEnfermedad_actual.setValue(hisc_consulta_externa
						.getEnfermedad_actual());

				infoPacientes.setCodigo_historia(hisc_consulta_externa
						.getCodigo_historia());
				tbxNro_identificacion.setValue(hisc_consulta_externa
						.getNro_identificacion());

				// Cargamos el prestador
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(hisc_consulta_externa
						.getCodigo_empresa());
				prestadores.setCodigo_sucursal(hisc_consulta_externa
						.getCodigo_sucursal());
				prestadores.setNro_identificacion(hisc_consulta_externa
						.getCodigo_prestador());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				bandboxPrestador.setValue(hisc_consulta_externa
						.getCodigo_prestador());
				tbxNomPrestador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos() : ""));

				tbxAcompaniante.setValue(hisc_consulta_externa
						.getAcompaniante());
				tbxCedula_acomp.setValue(hisc_consulta_externa
						.getCedula_acomp());

				Utilidades.seleccionarListitem(lbxRelacion,
						hisc_consulta_externa.getRelacion());
				tbxOtro_acompaniante.setValue(hisc_consulta_externa
						.getOtro_acompaniante());

				dbxTel_acompaniante.setValue(ConvertidorDatosUtil
						.convertirDato(hisc_consulta_externa
								.getTel_acompaniante()));
			}
			initMostrar_datos(hisc_consulta_externa);

			His_formato_tuberculosis his_formato_tuberculosis = new His_formato_tuberculosis();

			his_formato_tuberculosis = existeTuberculosis(his_formato_tuberculosis);

			if (his_formato_tuberculosis != null
					&& !his_formato_tuberculosis.getObservaciones().isEmpty()) {
				gbxTuberculosis.setVisible(true);
			} else {
				gbxTuberculosis.setVisible(false);
			}
			mostrarTuberculosis(his_formato_tuberculosis);

			if (opcionesFormulario == OpcionesFormulario.MOSTRAR) {
				// hay que vargar la remision interna despues de ejecutar el
				// metodo
				// cargarInformacion_paciente()
				cargarRemisionInterna(hisc_consulta_externa);
			}

			tbxEvolucion.setValue(hisc_consulta_externa
					.getEvolucion_del_cuadro());
			tbxTratamiento_recibido.setValue(hisc_consulta_externa
					.getTratamientos_recibidos());
			tbxManera_form_inicio.setValue(hisc_consulta_externa
					.getManera_forma_de_inicio());
			tbxSintomas_asociados.setValue(hisc_consulta_externa
					.getSintoma_asociados());
			tbxActualmente_presenta.setValue(hisc_consulta_externa
					.getActualmente_se_presenta_con());
			tbxlocalizacion.setValue(hisc_consulta_externa.getLocalizacion());
			tbxIrradiacion.setValue(hisc_consulta_externa.getIrradiacion());
			tbxRelacionado.setValue(hisc_consulta_externa.getRelacionado_con());

			StringTokenizer stringTokenizer = new StringTokenizer(
					hisc_consulta_externa.getPrimera_presentacion(), "|");

			Utilidades.seleccionarListitem(
					lbxPrimera_presentacion,
					stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "");
			spinnerVeces_repetido
					.setText(stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "1");

			tbxCaracteristicas_dolor.setValue(hisc_consulta_externa
					.getCaracteristicas_del_dolor());
			// manuel
			tbxDes_vida_Marital.setValue(hisc_consulta_externa
					.getDes_vida_marital());

			cbxAbdomen
					.setChecked(hisc_consulta_externa.getAbdomen_enf_actual());
			cbxCabeza.setChecked(hisc_consulta_externa.getCabeza_enf_actual());
			cbxToraz.setChecked(hisc_consulta_externa.getTorax_enf_actual());
			cbxCuello.setChecked(hisc_consulta_externa.getCuello_enf_actual());

			Utilidades.seleccionarListitem(lbxIntensidad,
					hisc_consulta_externa.getIntensidad());

			if (hisc_consulta_externa.getSelect_enfer_act().equals("1")) {
				rdSeleccion1.setSelected(true);

			}
			if (hisc_consulta_externa.getSelect_enfer_act().equals("2")) {
				rdSeleccion2.setSelected(true);
				tbxParrafo_enfermedad_actual.setValue(hisc_consulta_externa
						.getEnfermedad_actual());
			}
			if (hisc_consulta_externa.getSelect_enfer_act().equals("3")) {
				rdSeleccion3.setSelected(true);
				tbxParrafo_enfermedad_actual.setValue(hisc_consulta_externa
						.getEnfermedad_actual());
			}
			seleccion((Radiogroup) getFellow("rdbseleccion"));
			/* fin actualizacion manuel */

			tbxobservacion_confiable.setValue(hisc_consulta_externa
					.getObservacion_confiable());

			tbxEsquema_vacunacion.setValue(hisc_consulta_externa
					.getEsquema_vacunacion());
			/* antecedentes ginecontreticos */
			ibxGenerales_embarazos.setValue(hisc_consulta_externa
					.getGineco_generales());
			ibxMalformaciones_embarazos.setValue(hisc_consulta_externa
					.getGineco_malformaciones());
			chbAnt_gin_tiene_citologia.setSelectedIndex(hisc_consulta_externa
					.getAnt_gin_tiene_citologia() ? 0 : 1);
			box_citologia.setVisible(hisc_consulta_externa
					.getAnt_gin_tiene_citologia());
			dtbxAnt_gin_fecha_ultima_citologia.setValue(hisc_consulta_externa
					.getAnt_gin_fecha_ultima_citologia());
			tbxAnt_gin_fecha_ultima_regla_desc.setText(hisc_consulta_externa
					.getAnt_gin_fecha_ultima_regla_desc());
			chbAnt_gin_tiene_mamografia.setSelectedIndex(hisc_consulta_externa
					.getAnt_gin_tiene_mamografia() ? 0 : 1);
			box_mamo.setVisible(hisc_consulta_externa
					.getAnt_gin_tiene_mamografia());
			dtbxAnt_gin_fecha_mamografia.setValue(hisc_consulta_externa
					.getAnt_gin_fecha_mamografia());
			dtbxAnt_gin_fecha_mamografia.setVisible(hisc_consulta_externa
					.getAnt_gin_tiene_planificacion());
			tbxAnt_gin_citologia_resultado.setValue(hisc_consulta_externa
					.getAnt_gin_citologia_resultado());
			tbxAnt_gin_mamografia_resultado.setValue(hisc_consulta_externa
					.getAnt_gin_mamografia_resultado());
			chbAnt_gin_tiene_planificacion
					.setSelectedIndex(hisc_consulta_externa
							.getAnt_gin_tiene_planificacion() ? 0 : 1);
			box_anticonceptivos.setVisible(hisc_consulta_externa
					.getAnt_gin_tiene_planificacion());
			dtbxAnt_gin_fecha_incio_planificacion
					.setVisible(hisc_consulta_externa
							.getAnt_gin_tiene_planificacion());
			dtbxAnt_gin_fecha_incio_planificacion
					.setValue(hisc_consulta_externa
							.getAnt_gin_fecha_incio_planificacion());

			/* este es la parte de habitos */
			tbxHab_frecuencia_alcohol.setValue(hisc_consulta_externa
					.getHab_frecuencia_alcohol());
			tbxHab_licor_alcohol.setValue(hisc_consulta_externa
					.getHab_licor_alcohol());
			tbxHab_frecuencia_tabaquismo.setText(hisc_consulta_externa
					.getHab_frecuencia_tabaquismo());
			tbxHab_ejercicio_cual.setValue(hisc_consulta_externa
					.getHab_ejercicio_cual());
			chbHab_tabaquismo.setSelectedIndex(hisc_consulta_externa
					.getHab_tabaquismo() ? 0 : 1);
			chbHab_alcohol.setSelectedIndex(hisc_consulta_externa
					.getHab_alcohol() ? 0 : 1);
			chbHab_ejercicio.setSelectedIndex(hisc_consulta_externa
					.getHab_ejercicio() ? 0 : 1);

			Utilidades.seleccionarRadio(rdbPsicofarmacos,
					hisc_consulta_externa.getPsicofarmacos());

			Utilidades.seleccionarRadio(rdbOtras_adicciones,
					hisc_consulta_externa.getOtras_adicciones());

			box_alcohol.setVisible(hisc_consulta_externa.getHab_alcohol());
			box_tabaquismo
					.setVisible(hisc_consulta_externa.getHab_tabaquismo());
			box_ejercicio.setVisible(hisc_consulta_externa.getHab_ejercicio());
			box_dietas.setVisible(hisc_consulta_externa.getHab_dietas());
			box_factores_psicologicos.setVisible(hisc_consulta_externa
					.getHab_factores_psicologicos());
			box_Psicofarmacos.setVisible(true);
			box_Otras_adicciones.setVisible(true);

			/* calculamos el tabaquismo */
			calcularTabaquismo();

			/* antecedentes niños */
			tbxAnt_nios_peso.setText(hisc_consulta_externa.getAnt_nios_peso());
			tbxAnt_nios_talla
					.setText(hisc_consulta_externa.getAnt_nios_talla());
			tbxAnt_nios_psicomotor.setValue(hisc_consulta_externa
					.getAnt_nios_psicomotor());
			tbxAnt_nios_dieta.setValue(hisc_consulta_externa
					.getAnt_nios_dieta());
			tbxAnt_nios_vacunas.setValue(hisc_consulta_externa
					.getAnt_nios_vacunas());

			tbxFisico_estado_general.setValue(hisc_consulta_externa
					.getFisico_estado_general());
			tbxFisico_cabeza_cara.setValue(hisc_consulta_externa
					.getFisico_cabeza_cara());
			tbxFisico_ocular.setValue(hisc_consulta_externa.getFisico_ocular());
			tbxFisico_endocrinologo.setValue(hisc_consulta_externa
					.getFisico_endocrinologo());
			tbxFisico_otorrino.setValue(hisc_consulta_externa
					.getFisico_otorrino());
			tbxFisico_osteomuscular.setValue(hisc_consulta_externa
					.getFisico_osteomuscular());
			tbxFisico_cardio_pulmonar.setValue(hisc_consulta_externa
					.getFisico_cardio_pulmonar());
			tbxFisico_cuello.setValue(hisc_consulta_externa.getFisico_cuello());
			tbxFisico_vacular.setValue(hisc_consulta_externa
					.getFisico_vacular());
			tbxFisico_piel_fanera.setValue(hisc_consulta_externa
					.getFisico_piel_fanera());
			tbxFisico_gastointestinal.setValue(hisc_consulta_externa
					.getFisico_gastointestinal());
			tbxFisico_neurologico.setValue(hisc_consulta_externa
					.getFisico_neurologico());
			tbxFisico_genitourinario.setValue(hisc_consulta_externa
					.getFisico_genitourinario());
			tbxFisico_mental.setValue(hisc_consulta_externa.getFisico_mental());
			tbxobservacion_confiable.setValue(hisc_consulta_externa
					.getOtro_ginecostetrico());

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_hipertension,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_hipertension()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_ecv,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_ecv()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_enf_coronaria,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_enf_coronaria()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_muerte_im_acv,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_muerte_im_acv()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_dislipidemia,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_dislipidemia()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_nefropatias,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_nefropatias()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_obesos,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_obesos()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_diabetes,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_diabetes()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_enf_mental,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_enf_mental()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_cancer,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_cancer()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_hematologia,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_hematologia()));

			bandboxAnte_fam_otros.setValue(hisc_consulta_externa
					.getAnte_fam_otros());

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_alergicos,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_alergicos()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_asma,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_asma()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_hepatitisb,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_infecciosa_hepatitisb()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_lepra,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_infecciosa_lepra()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_sifilis,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_infecciosa_sifilis()));

			Utilidades.mostrarXmlTextbox(
					bandboxAnte_fam_infecciosa_tuberculosis,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_infecciosa_tuberculosis()));

			Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_vih,
					ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
							.getAnte_fam_infecciosa_vih()));

			tbxAnte_fam_estecifique.setValue(hisc_consulta_externa
					.getAnte_fam_estecifique());

			tbxAnte_fam_observaciones.setValue(hisc_consulta_externa
					.getAnte_fam_observaciones());

			mostrarPlanificacion(chbAnt_gin_tiene_planificacion
					.getSelectedItem().getValue().toString());

			tbxAnalisis_recomendaciones.setValue(hisc_consulta_externa
					.getAnalisis_recomendaciones());

			ibxHab_ejercicio_minutos_dias.setValue(hisc_consulta_externa
					.getHab_ejercicio_horas_semana().isEmpty() ? null
					: new Integer(hisc_consulta_externa
							.getHab_ejercicio_horas_semana()));

			calcularHorasPorSemana();

			// habitos
			Utilidades.seleccionarRadio(chbHab_alcohol,
					hisc_consulta_externa.getHab_alcohol() ? "S" : "N");
			tbxHab_frecuencia_alcohol.setValue(hisc_consulta_externa
					.getHab_frecuencia_alcohol());

			tbxHab_licor_alcohol.setValue(hisc_consulta_externa
					.getHab_licor_alcohol());

			Utilidades.seleccionarRadio(chbHab_tabaquismo,
					hisc_consulta_externa.getHab_tabaquismo() ? "S" : "N");

			tbxHab_frecuencia_tabaquismo.setValue(hisc_consulta_externa
					.getHab_frecuencia_tabaquismo().isEmpty() ? null : Integer
					.parseInt(hisc_consulta_externa
							.getHab_frecuencia_tabaquismo()));

			tbxHab_frecuencia_tabaquismo_caja_anio
					.setValue(hisc_consulta_externa
							.getHab_frecuencia_tabaquismo_caja_anio());

			Utilidades.seleccionarRadio(chbHab_ejercicio,
					hisc_consulta_externa.getHab_ejercicio() ? "S" : "N");

			tbxHab_ejercicio_cual.setValue(hisc_consulta_externa
					.getHab_ejercicio_cual());

			ibxHab_ejercicio_minutos_dias.setValue(hisc_consulta_externa
					.getHab_ejercicio_horas_semana().isEmpty() ? null
					: new Integer(hisc_consulta_externa
							.getHab_ejercicio_horas_semana()));

			calcularHorasPorSemana();

			Utilidades.seleccionarRadio(chbHab_dietas,
					hisc_consulta_externa.getHab_dietas() ? "S" : "N");

			tbxHab_dietas_frecuencia.setValue(hisc_consulta_externa
					.getHab_dietas_frecuencia());

			Utilidades.seleccionarRadio(chbHab_factores_psicologicos,
					hisc_consulta_externa.getHab_factores_psicologicos() ? "S"
							: "N");

			tbxHab_factores_psicologicos_cual.setValue(hisc_consulta_externa
					.getHab_factores_psicologicos_cual());

			hisc_consulta_externa.setMetodos_orales("");

			Utilidades.seleccionarListitem(lbxSintomatico_respiratorio,
					hisc_consulta_externa.getSintomatico_respiratorio());
			Utilidades.seleccionarListitem(lbxSintomatico_piel,
					hisc_consulta_externa.getSintomatico_piel());

			Utilidades.seleccionarListitem(lbxCabezacara_list,
					hisc_consulta_externa.getCabezacara_list());
			Utilidades.seleccionarListitem(lbxOcular_list,
					hisc_consulta_externa.getOcular_list());
			Utilidades.seleccionarListitem(lbxOtorrrino_list,
					hisc_consulta_externa.getOtorrrino_list());
			Utilidades.seleccionarListitem(lbxCuello_list,
					hisc_consulta_externa.getCuello_list());
			Utilidades.seleccionarListitem(lbxCardio_pulmonar_list,
					hisc_consulta_externa.getCardio_pulmonar_list());
			Utilidades.seleccionarListitem(lbxAdomen_list,
					hisc_consulta_externa.getAdomen_list());
			Utilidades.seleccionarListitem(lbxGenitourinario_list,
					hisc_consulta_externa.getGenitourinario_list());
			Utilidades.seleccionarListitem(lbxExamen_mama_list,
					hisc_consulta_externa.getExamen_mama_list());
			Utilidades.seleccionarListitem(lbxEndocrino_list,
					hisc_consulta_externa.getEndocrino_list());
			Utilidades.seleccionarListitem(lbxOsteomuscular_list,
					hisc_consulta_externa.getOsteomuscular_list());
			Utilidades.seleccionarListitem(lbxVascular_list,
					hisc_consulta_externa.getVascular_list());
			Utilidades.seleccionarListitem(lbxPiel_faneras_list,
					hisc_consulta_externa.getPiel_faneras_list());
			Utilidades.seleccionarListitem(lbxNeurologico_list,
					hisc_consulta_externa.getNeurologico_list());
			Utilidades.seleccionarListitem(lbxMental_list,
					hisc_consulta_externa.getMental_list());

			onSeleccionarSintomatico(lbxSintomatico_piel, tbxSintomatico_piel);
			onSeleccionarSintomatico(lbxSintomatico_respiratorio,
					tbxSintomatico_respiratorio);

			onSeleccionarAntecendestes1(lbxCabezacara_list,
					tbxFisico_cabeza_cara);
			onSeleccionarAntecendestes1(lbxOcular_list, tbxFisico_ocular);
			onSeleccionarAntecendestes1(lbxOtorrrino_list, tbxFisico_otorrino);
			onSeleccionarAntecendestes1(lbxCuello_list, tbxFisico_cuello);
			onSeleccionarAntecendestes1(lbxCardio_pulmonar_list,
					tbxFisico_cardio_pulmonar);
			onSeleccionarAntecendestes1(lbxAdomen_list,
					tbxFisico_gastointestinal);
			onSeleccionarAntecendestes1(lbxGenitourinario_list,
					tbxFisico_genitourinario);
			onSeleccionarAntecendestes1(lbxExamen_mama_list,
					tbxFisico_examen_mama);
			onSeleccionarAntecendestes1(lbxEndocrino_list,
					tbxFisico_endocrinologo);
			onSeleccionarAntecendestes1(lbxOsteomuscular_list,
					tbxFisico_osteomuscular);
			onSeleccionarAntecendestes1(lbxVascular_list, tbxFisico_vacular);
			onSeleccionarAntecendestes1(lbxPiel_faneras_list,
					tbxFisico_piel_fanera);
			onSeleccionarAntecendestes1(lbxNeurologico_list,
					tbxFisico_neurologico);
			onSeleccionarAntecendestes1(lbxMental_list, tbxFisico_mental);

			onSeleccionarOtros_ciclos(lbxAnt_gin_ciclo_1, rowOtros_ciclos);

			tbxSintomatico_respiratorio.setValue(hisc_consulta_externa
					.getSintomatico_respiratorio_desc());
			tbxSintomatico_piel.setValue(hisc_consulta_externa
					.getSintomatico_piel_desc());

			tbxCual_psicofarmacos.setValue(hisc_consulta_externa
					.getCual_psicofarmacos());

			tbxCual_adicciones.setValue(hisc_consulta_externa
					.getCual_adicciones());

			lbxSintomatico_respiratorio.setDisabled(true);
			lbxSintomatico_piel.setDisabled(true);
			lbxCabezacara_list.setDisabled(true);
			lbxOcular_list.setDisabled(true);
			lbxOtorrrino_list.setDisabled(true);
			lbxCuello_list.setDisabled(true);
			lbxCardio_pulmonar_list.setDisabled(true);
			lbxAdomen_list.setDisabled(true);
			lbxGenitourinario_list.setDisabled(true);
			lbxExamen_mama_list.setDisabled(true);
			lbxEndocrino_list.setDisabled(true);
			lbxOsteomuscular_list.setDisabled(true);
			lbxVascular_list.setDisabled(true);
			lbxPiel_faneras_list.setDisabled(true);
			lbxNeurologico_list.setDisabled(true);
			lbxMental_list.setDisabled(true);

			tbxFecha_inicio.setText(hisc_consulta_externa
					.getFecha_inicio_enfermedad());

			Utilidades.seleccionarRadio(chbVida_marital,
					hisc_consulta_externa.getVida_marital());
			if (hisc_consulta_externa.getFecha_vida_marital() != null) {
				dtbxFecha_vida_marital.setValue(hisc_consulta_externa
						.getFecha_vida_marital());
			} else {
				dtbxFecha_vida_marital.setText("");
			}

			cargarAntecedentesPersonales(obtenerMapaAntecedentesPersonales(hisc_consulta_externa));

			treeItemDinamicRevisionSistemasMacro = new TreeItemDinamicRevisionSistemasMacro(
					tree, this, "RPS");
			treeItemDinamicRevisionSistemasMacro
					.cargarElemementosIniciales(obtenerListadoRevisionSistema(hisc_consulta_externa));
			treeItemDinamicRevisionSistemasMacro.renderizarContenido();

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString())) {
				treeItemDinamicRevisionSistemasMacro.aplicarSoloLectura(true);
			}

			cargarMetodosPlanificicacion(hisc_consulta_externa
					.getMetodos_planificacion());

			if (opcionesFormulario == OpcionesFormulario.MOSTRAR) {
				cargarSignosVitales(hisc_consulta_externa);
				cargarImpresionDiagnostica(hisc_consulta_externa);
			}

			macroGlasgow.limpiarEscalaGlasgow();
			if (hisc_consulta_externa.getEscala_glasgow().equals("S")) {
				gbxMacroGlasgow.setVisible(true);
				macroGlasgow.mostrarEscalaGlasgow(
						hisc_consulta_externa.getRespuesta_motora(),
						hisc_consulta_externa.getRespuesta_verbal(),
						hisc_consulta_externa.getApertura_ocular(), false);
			} else {
				gbxMacroGlasgow.setVisible(false);
			}

			tbxOtro_acompaniante.setValue(hisc_consulta_externa
					.getOtro_acompaniante());

			tbxNo_farmocologico.setValue(hisc_consulta_externa
					.getNo_farmacologicos());
			tbxEducacional_paciente.setValue(hisc_consulta_externa
					.getEducacion_paciente());
			tbxPlan_seguimiento.setValue(hisc_consulta_externa
					.getPlan_seguimiento());

			ibxNacidos_vivos.setValue(hisc_consulta_externa
					.getGineco_nacidos_vivos());
			ibxNacidos_muertos.setValue(hisc_consulta_externa
					.getGineco_nacidos_muertos());
			ibxPreterminos.setValue(hisc_consulta_externa
					.getGineco_preterminos());
			ibxNro_gestaciones.setValue(hisc_consulta_externa
					.getGineco_nro_gestaciones());
			ibxNro_partos
					.setValue(hisc_consulta_externa.getGineco_nro_partos());
			ibxNro_abortos.setValue(hisc_consulta_externa
					.getGineco_nro_abortos());
			ibxNro_cesarias.setValue(hisc_consulta_externa
					.getGineco_nro_cesarias());
			dtbxAnt_gin_menarca.setValue(hisc_consulta_externa
					.getGineco_menarca());
			tbxAnt_gin_e_menopaudia.setValue(hisc_consulta_externa
					.getGineco_menopaudia());
			Utilidades.seleccionarListitem(lbxAnt_gin_ciclo_1,
					hisc_consulta_externa.getGineco_ciclo1());

			dtbxAnt_gin_fecha_ultima_regla.setValue(hisc_consulta_externa
					.getGineco_fur());
			dtbxAnt_gin_fecha_espectante_parto.setValue(hisc_consulta_externa
					.getGineco_fep());
			Utilidades.seleccionarListitem(lbx_conf_fur,
					hisc_consulta_externa.getGineco_conf_fur());

			tbxAnalisis_del_caso.setValue(hisc_consulta_externa
					.getAnalisis_del_caso());

			tbxOtro_ciclo.setValue(hisc_consulta_externa.getOtro_ciclo());
			tbxDescripcion_menarca.setValue(hisc_consulta_externa
					.getDescripcion_menarca());

			if (opcionesFormulario == OpcionesFormulario.MOSTRAR) {
				onMostrarModuloFarmacologico();
				receta_ripsAction.obtenerBotonAgregar().setVisible(false);
				receta_ripsAction.deshabilitarCampos(true);
				onMostrarModuloOrdenamiento();
				orden_servicioAction.obtenerBotonAgregar().setVisible(false);
				orden_servicioAction.validarParaImpresion();
				cargarAnexo9_remision(hisc_consulta_externa);

				btnImprimir.setVisible(true);
			}

			deshabilitarCampos(desabilitar);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "Este dato no se puede editar", this);
		}
	}

	// public void mostrarDatosAnterior(Object obj) throws Exception {
	// Hisc_consulta_externa hisc_consulta_externa = (Hisc_consulta_externa)
	// obj;
	// try {
	// initMostrar_datos(hisc_consulta_externa);
	//
	// His_formato_tuberculosis his_formato_tuberculosis = new
	// His_formato_tuberculosis();
	//
	// his_formato_tuberculosis = existeTuberculosis(his_formato_tuberculosis);
	//
	// if (his_formato_tuberculosis != null
	// && !his_formato_tuberculosis.getObservaciones().isEmpty()) {
	// gbxTuberculosis.setVisible(true);
	// } else {
	// gbxTuberculosis.setVisible(false);
	// }
	// mostrarTuberculosis(his_formato_tuberculosis);
	//
	// tbxEnfermedad_actual.setValue(hisc_consulta_externa
	// .getEnfermedad_actual());
	// tbxEvolucion.setValue(hisc_consulta_externa
	// .getEvolucion_del_cuadro());
	// tbxTratamiento_recibido.setValue(hisc_consulta_externa
	// .getTratamientos_recibidos());
	// tbxManera_form_inicio.setValue(hisc_consulta_externa
	// .getManera_forma_de_inicio());
	// tbxSintomas_asociados.setValue(hisc_consulta_externa
	// .getSintoma_asociados());
	// tbxActualmente_presenta.setValue(hisc_consulta_externa
	// .getActualmente_se_presenta_con());
	// tbxlocalizacion.setValue(hisc_consulta_externa.getLocalizacion());
	// tbxIrradiacion.setValue(hisc_consulta_externa.getIrradiacion());
	// tbxRelacionado.setValue(hisc_consulta_externa.getRelacionado_con());
	//
	// StringTokenizer stringTokenizer = new StringTokenizer(
	// hisc_consulta_externa.getPrimera_presentacion(), "|");
	//
	// Utilidades.seleccionarListitem(
	// lbxPrimera_presentacion,
	// stringTokenizer.hasMoreTokens() ? stringTokenizer
	// .nextToken() : "");
	// spinnerVeces_repetido
	// .setText(stringTokenizer.hasMoreTokens() ? stringTokenizer
	// .nextToken() : "1");
	//
	// tbxCaracteristicas_dolor.setValue(hisc_consulta_externa
	// .getCaracteristicas_del_dolor());
	// // manuel
	// tbxDes_vida_Marital.setValue(hisc_consulta_externa
	// .getDes_vida_marital());
	//
	// cbxAbdomen
	// .setChecked(hisc_consulta_externa.getAbdomen_enf_actual());
	// cbxCabeza.setChecked(hisc_consulta_externa.getCabeza_enf_actual());
	// cbxToraz.setChecked(hisc_consulta_externa.getTorax_enf_actual());
	// cbxCuello.setChecked(hisc_consulta_externa.getCuello_enf_actual());
	//
	// Utilidades.seleccionarListitem(lbxIntensidad,
	// hisc_consulta_externa.getIntensidad());
	//
	// if (hisc_consulta_externa.getSelect_enfer_act().equals("1")) {
	// rdSeleccion1.setSelected(true);
	//
	// }
	// if (hisc_consulta_externa.getSelect_enfer_act().equals("2")) {
	// rdSeleccion2.setSelected(true);
	// tbxParrafo_enfermedad_actual.setValue(hisc_consulta_externa
	// .getEnfermedad_actual());
	// }
	// if (hisc_consulta_externa.getSelect_enfer_act().equals("3")) {
	// rdSeleccion3.setSelected(true);
	// tbxParrafo_enfermedad_actual.setValue(hisc_consulta_externa
	// .getEnfermedad_actual());
	// }
	// seleccion((Radiogroup) getFellow("rdbseleccion"));
	// /* fin actualizacion manuel */
	//
	// tbxobservacion_confiable.setValue(hisc_consulta_externa
	// .getObservacion_confiable());
	//
	// tbxEsquema_vacunacion.setValue(hisc_consulta_externa
	// .getEsquema_vacunacion());
	// tbxMotivo_consulta.setValue(hisc_consulta_externa
	// .getMotivo_consulta());
	// tbxEnfermedad_actual.setValue(hisc_consulta_externa
	// .getEnfermedad_actual());
	//
	// /* antecedentes ginecontreticos */
	// ibxGenerales_embarazos.setValue(hisc_consulta_externa
	// .getGineco_generales());
	// ibxMalformaciones_embarazos.setValue(hisc_consulta_externa
	// .getGineco_malformaciones());
	// chbAnt_gin_tiene_citologia.setSelectedIndex(hisc_consulta_externa
	// .getAnt_gin_tiene_citologia() ? 0 : 1);
	// box_citologia.setVisible(hisc_consulta_externa
	// .getAnt_gin_tiene_citologia());
	// dtbxAnt_gin_fecha_ultima_citologia.setValue(hisc_consulta_externa
	// .getAnt_gin_fecha_ultima_citologia());
	// tbxAnt_gin_fecha_ultima_regla_desc.setText(hisc_consulta_externa
	// .getAnt_gin_fecha_ultima_regla_desc());
	// chbAnt_gin_tiene_mamografia.setSelectedIndex(hisc_consulta_externa
	// .getAnt_gin_tiene_mamografia() ? 0 : 1);
	// box_mamo.setVisible(hisc_consulta_externa
	// .getAnt_gin_tiene_mamografia());
	// dtbxAnt_gin_fecha_mamografia.setValue(hisc_consulta_externa
	// .getAnt_gin_fecha_mamografia());
	// dtbxAnt_gin_fecha_mamografia.setVisible(hisc_consulta_externa
	// .getAnt_gin_tiene_planificacion());
	// tbxAnt_gin_citologia_resultado.setValue(hisc_consulta_externa
	// .getAnt_gin_citologia_resultado());
	// tbxAnt_gin_mamografia_resultado.setValue(hisc_consulta_externa
	// .getAnt_gin_mamografia_resultado());
	// chbAnt_gin_tiene_planificacion
	// .setSelectedIndex(hisc_consulta_externa
	// .getAnt_gin_tiene_planificacion() ? 0 : 1);
	// box_anticonceptivos.setVisible(hisc_consulta_externa
	// .getAnt_gin_tiene_planificacion());
	// dtbxAnt_gin_fecha_incio_planificacion
	// .setVisible(hisc_consulta_externa
	// .getAnt_gin_tiene_planificacion());
	// dtbxAnt_gin_fecha_incio_planificacion
	// .setValue(hisc_consulta_externa
	// .getAnt_gin_fecha_incio_planificacion());
	//
	// /* este es la parte de habitos */
	// tbxHab_frecuencia_alcohol.setValue(hisc_consulta_externa
	// .getHab_frecuencia_alcohol());
	// tbxHab_licor_alcohol.setValue(hisc_consulta_externa
	// .getHab_licor_alcohol());
	// tbxHab_frecuencia_tabaquismo.setText(hisc_consulta_externa
	// .getHab_frecuencia_tabaquismo());
	// tbxHab_ejercicio_cual.setValue(hisc_consulta_externa
	// .getHab_ejercicio_cual());
	// chbHab_tabaquismo.setSelectedIndex(hisc_consulta_externa
	// .getHab_tabaquismo() ? 0 : 1);
	// chbHab_alcohol.setSelectedIndex(hisc_consulta_externa
	// .getHab_alcohol() ? 0 : 1);
	// chbHab_ejercicio.setSelectedIndex(hisc_consulta_externa
	// .getHab_ejercicio() ? 0 : 1);
	//
	// Utilidades.seleccionarRadio(rdbPsicofarmacos,
	// hisc_consulta_externa.getPsicofarmacos());
	//
	// Utilidades.seleccionarRadio(rdbOtras_adicciones,
	// hisc_consulta_externa.getOtras_adicciones());
	//
	// box_alcohol.setVisible(hisc_consulta_externa.getHab_alcohol());
	// box_tabaquismo
	// .setVisible(hisc_consulta_externa.getHab_tabaquismo());
	// box_ejercicio.setVisible(hisc_consulta_externa.getHab_ejercicio());
	// box_dietas.setVisible(hisc_consulta_externa.getHab_dietas());
	// box_factores_psicologicos.setVisible(hisc_consulta_externa
	// .getHab_factores_psicologicos());
	// box_Psicofarmacos.setVisible(hisc_consulta_externa.getPsicofarmacos().equalsIgnoreCase("S"));
	// box_Otras_adicciones.setVisible(hisc_consulta_externa.getOtras_adicciones().equalsIgnoreCase("S"));
	//
	// /* calculamos el tabaquismo */
	// calcularTabaquismo();
	//
	// /* antecedentes niños */
	// tbxAnt_nios_peso.setText(hisc_consulta_externa.getAnt_nios_peso());
	// tbxAnt_nios_talla
	// .setText(hisc_consulta_externa.getAnt_nios_talla());
	// tbxAnt_nios_psicomotor.setValue(hisc_consulta_externa
	// .getAnt_nios_psicomotor());
	// tbxAnt_nios_dieta.setValue(hisc_consulta_externa
	// .getAnt_nios_dieta());
	// tbxAnt_nios_vacunas.setValue(hisc_consulta_externa
	// .getAnt_nios_vacunas());
	//
	// tbxFisico_estado_general.setValue(hisc_consulta_externa
	// .getFisico_estado_general());
	// tbxFisico_cabeza_cara.setValue(hisc_consulta_externa
	// .getFisico_cabeza_cara());
	// tbxFisico_ocular.setValue(hisc_consulta_externa.getFisico_ocular());
	// tbxFisico_endocrinologo.setValue(hisc_consulta_externa
	// .getFisico_endocrinologo());
	// tbxFisico_otorrino.setValue(hisc_consulta_externa
	// .getFisico_otorrino());
	// tbxFisico_osteomuscular.setValue(hisc_consulta_externa
	// .getFisico_osteomuscular());
	// tbxFisico_cardio_pulmonar.setValue(hisc_consulta_externa
	// .getFisico_cardio_pulmonar());
	// tbxFisico_cuello.setValue(hisc_consulta_externa.getFisico_cuello());
	// tbxFisico_vacular.setValue(hisc_consulta_externa
	// .getFisico_vacular());
	// tbxFisico_piel_fanera.setValue(hisc_consulta_externa
	// .getFisico_piel_fanera());
	// tbxFisico_gastointestinal.setValue(hisc_consulta_externa
	// .getFisico_gastointestinal());
	// tbxFisico_neurologico.setValue(hisc_consulta_externa
	// .getFisico_neurologico());
	// tbxFisico_genitourinario.setValue(hisc_consulta_externa
	// .getFisico_genitourinario());
	// tbxFisico_mental.setValue(hisc_consulta_externa.getFisico_mental());
	// tbxobservacion_confiable.setValue(hisc_consulta_externa
	// .getOtro_ginecostetrico());
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_hipertension,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_hipertension()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_ecv,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_ecv()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_enf_coronaria,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_enf_coronaria()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_muerte_im_acv,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_muerte_im_acv()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_dislipidemia,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_dislipidemia()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_nefropatias,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_nefropatias()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_obesos,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_obesos()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_diabetes,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_diabetes()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_enf_mental,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_enf_mental()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_cancer,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_cancer()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_hematologia,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_hematologia()));
	//
	// bandboxAnte_fam_otros.setValue(hisc_consulta_externa
	// .getAnte_fam_otros());
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_alergicos,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_alergicos()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_asma,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_asma()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_hepatitisb,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_infecciosa_hepatitisb()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_lepra,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_infecciosa_lepra()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_sifilis,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_infecciosa_sifilis()));
	//
	// Utilidades.mostrarXmlTextbox(
	// bandboxAnte_fam_infecciosa_tuberculosis,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_infecciosa_tuberculosis()));
	//
	// Utilidades.mostrarXmlTextbox(bandboxAnte_fam_infecciosa_vih,
	// ConvertidorXmlToMap.convertirToMap(hisc_consulta_externa
	// .getAnte_fam_infecciosa_vih()));
	//
	// tbxAnte_fam_estecifique.setValue(hisc_consulta_externa
	// .getAnte_fam_estecifique());
	//
	// tbxAnte_fam_observaciones.setValue(hisc_consulta_externa
	// .getAnte_fam_observaciones());
	//
	// mostrarPlanificacion(chbAnt_gin_tiene_planificacion
	// .getSelectedItem().getValue().toString());
	//
	// tbxAnalisis_recomendaciones.setValue(hisc_consulta_externa
	// .getAnalisis_recomendaciones());
	//
	// ibxHab_ejercicio_minutos_dias.setValue(hisc_consulta_externa
	// .getHab_ejercicio_horas_semana().isEmpty() ? null
	// : new Integer(hisc_consulta_externa
	// .getHab_ejercicio_horas_semana()));
	//
	// calcularHorasPorSemana();
	//
	// // habitos
	// Utilidades.seleccionarRadio(chbHab_alcohol,
	// hisc_consulta_externa.getHab_alcohol() ? "S" : "N");
	// tbxHab_frecuencia_alcohol.setValue(hisc_consulta_externa
	// .getHab_frecuencia_alcohol());
	//
	// tbxHab_licor_alcohol.setValue(hisc_consulta_externa
	// .getHab_licor_alcohol());
	//
	// Utilidades.seleccionarRadio(chbHab_tabaquismo,
	// hisc_consulta_externa.getHab_tabaquismo() ? "S" : "N");
	//
	// tbxHab_frecuencia_tabaquismo.setValue(hisc_consulta_externa
	// .getHab_frecuencia_tabaquismo().isEmpty() ? null : Integer
	// .parseInt(hisc_consulta_externa
	// .getHab_frecuencia_tabaquismo()));
	//
	// tbxHab_frecuencia_tabaquismo_caja_anio
	// .setValue(hisc_consulta_externa
	// .getHab_frecuencia_tabaquismo_caja_anio());
	//
	// Utilidades.seleccionarRadio(chbHab_ejercicio,
	// hisc_consulta_externa.getHab_ejercicio() ? "S" : "N");
	//
	// tbxHab_ejercicio_cual.setValue(hisc_consulta_externa
	// .getHab_ejercicio_cual());
	//
	// ibxHab_ejercicio_minutos_dias.setValue(hisc_consulta_externa
	// .getHab_ejercicio_horas_semana().isEmpty() ? null
	// : new Integer(hisc_consulta_externa
	// .getHab_ejercicio_horas_semana()));
	//
	// calcularHorasPorSemana();
	//
	// Utilidades.seleccionarRadio(chbHab_dietas,
	// hisc_consulta_externa.getHab_dietas() ? "S" : "N");
	//
	// tbxHab_dietas_frecuencia.setValue(hisc_consulta_externa
	// .getHab_dietas_frecuencia());
	//
	// Utilidades.seleccionarRadio(chbHab_factores_psicologicos,
	// hisc_consulta_externa.getHab_factores_psicologicos() ? "S"
	// : "N");
	//
	// tbxHab_factores_psicologicos_cual.setValue(hisc_consulta_externa
	// .getHab_factores_psicologicos_cual());
	//
	// hisc_consulta_externa.setMetodos_orales("");
	//
	// Utilidades.seleccionarListitem(lbxSintomatico_respiratorio,
	// hisc_consulta_externa.getSintomatico_respiratorio());
	// Utilidades.seleccionarListitem(lbxSintomatico_piel,
	// hisc_consulta_externa.getSintomatico_piel());
	//
	// Utilidades.seleccionarListitem(lbxCabezacara_list,
	// hisc_consulta_externa.getCabezacara_list());
	// Utilidades.seleccionarListitem(lbxOcular_list,
	// hisc_consulta_externa.getOcular_list());
	// Utilidades.seleccionarListitem(lbxOtorrrino_list,
	// hisc_consulta_externa.getOtorrrino_list());
	// Utilidades.seleccionarListitem(lbxCuello_list,
	// hisc_consulta_externa.getCuello_list());
	// Utilidades.seleccionarListitem(lbxCardio_pulmonar_list,
	// hisc_consulta_externa.getCardio_pulmonar_list());
	// Utilidades.seleccionarListitem(lbxAdomen_list,
	// hisc_consulta_externa.getAdomen_list());
	// Utilidades.seleccionarListitem(lbxGenitourinario_list,
	// hisc_consulta_externa.getGenitourinario_list());
	// Utilidades.seleccionarListitem(lbxExamen_mama_list,
	// hisc_consulta_externa.getExamen_mama_list());
	// Utilidades.seleccionarListitem(lbxEndocrino_list,
	// hisc_consulta_externa.getEndocrino_list());
	// Utilidades.seleccionarListitem(lbxOsteomuscular_list,
	// hisc_consulta_externa.getOsteomuscular_list());
	// Utilidades.seleccionarListitem(lbxVascular_list,
	// hisc_consulta_externa.getVascular_list());
	// Utilidades.seleccionarListitem(lbxPiel_faneras_list,
	// hisc_consulta_externa.getPiel_faneras_list());
	// Utilidades.seleccionarListitem(lbxNeurologico_list,
	// hisc_consulta_externa.getNeurologico_list());
	// Utilidades.seleccionarListitem(lbxMental_list,
	// hisc_consulta_externa.getMental_list());
	//
	// onSeleccionarSintomatico(lbxSintomatico_piel, tbxSintomatico_piel);
	// onSeleccionarSintomatico(lbxSintomatico_respiratorio,
	// tbxSintomatico_respiratorio);
	//
	// onSeleccionarAntecendestes1(lbxCabezacara_list,
	// tbxFisico_cabeza_cara);
	// onSeleccionarAntecendestes1(lbxOcular_list, tbxFisico_ocular);
	// onSeleccionarAntecendestes1(lbxOtorrrino_list, tbxFisico_otorrino);
	// onSeleccionarAntecendestes1(lbxCuello_list, tbxFisico_cuello);
	// onSeleccionarAntecendestes1(lbxCardio_pulmonar_list,
	// tbxFisico_cardio_pulmonar);
	// onSeleccionarAntecendestes1(lbxAdomen_list,
	// tbxFisico_gastointestinal);
	// onSeleccionarAntecendestes1(lbxGenitourinario_list,
	// tbxFisico_genitourinario);
	// onSeleccionarAntecendestes1(lbxExamen_mama_list,
	// tbxFisico_examen_mama);
	// onSeleccionarAntecendestes1(lbxEndocrino_list,
	// tbxFisico_endocrinologo);
	// onSeleccionarAntecendestes1(lbxOsteomuscular_list,
	// tbxFisico_osteomuscular);
	// onSeleccionarAntecendestes1(lbxVascular_list, tbxFisico_vacular);
	// onSeleccionarAntecendestes1(lbxPiel_faneras_list,
	// tbxFisico_piel_fanera);
	// onSeleccionarAntecendestes1(lbxNeurologico_list,
	// tbxFisico_neurologico);
	// onSeleccionarAntecendestes1(lbxMental_list, tbxFisico_mental);
	//
	// onSeleccionarOtros_ciclos(lbxAnt_gin_ciclo_1, rowOtros_ciclos);
	//
	// tbxSintomatico_respiratorio.setValue(hisc_consulta_externa
	// .getSintomatico_respiratorio_desc());
	// tbxSintomatico_piel.setValue(hisc_consulta_externa
	// .getSintomatico_piel_desc());
	//
	// tbxCual_psicofarmacos.setValue(hisc_consulta_externa
	// .getCual_psicofarmacos());
	//
	// tbxCual_adicciones.setValue(hisc_consulta_externa
	// .getCual_adicciones());
	//
	// lbxSintomatico_respiratorio.setDisabled(true);
	// lbxSintomatico_piel.setDisabled(true);
	// lbxCabezacara_list.setDisabled(true);
	// lbxOcular_list.setDisabled(true);
	// lbxOtorrrino_list.setDisabled(true);
	// lbxCuello_list.setDisabled(true);
	// lbxCardio_pulmonar_list.setDisabled(true);
	// lbxAdomen_list.setDisabled(true);
	// lbxGenitourinario_list.setDisabled(true);
	// lbxExamen_mama_list.setDisabled(true);
	// lbxEndocrino_list.setDisabled(true);
	// lbxOsteomuscular_list.setDisabled(true);
	// lbxVascular_list.setDisabled(true);
	// lbxPiel_faneras_list.setDisabled(true);
	// lbxNeurologico_list.setDisabled(true);
	// lbxMental_list.setDisabled(true);
	//
	// tbxFecha_inicio.setText(hisc_consulta_externa
	// .getFecha_inicio_enfermedad());
	//
	// Utilidades.seleccionarRadio(chbVida_marital,
	// hisc_consulta_externa.getVida_marital());
	// if (hisc_consulta_externa.getFecha_vida_marital() != null) {
	// dtbxFecha_vida_marital.setValue(hisc_consulta_externa
	// .getFecha_vida_marital());
	// } else {
	// dtbxFecha_vida_marital.setText("");
	// }
	//
	// cargarAntecedentesPersonales(obtenerMapaAntecedentesPersonales(hisc_consulta_externa));
	//
	// treeItemDinamicRevisionSistemasMacro = new
	// TreeItemDinamicRevisionSistemasMacro(
	// tree, this, "RPS");
	// treeItemDinamicRevisionSistemasMacro
	// .cargarElemementosIniciales(obtenerListadoRevisionSistema(hisc_consulta_externa));
	// treeItemDinamicRevisionSistemasMacro.renderizarContenido();
	//
	// if (!tbxAccion.getValue().equals(
	// OpcionesFormulario.MOSTRAR.toString())) {
	// treeItemDinamicRevisionSistemasMacro.aplicarSoloLectura(true);
	// }
	//
	// cargarMetodosPlanificicacion(hisc_consulta_externa
	// .getMetodos_planificacion());
	//
	// cargarSignosVitales(hisc_consulta_externa);
	//
	// //cargarImpresionDiagnostica(hisc_consulta_externa);
	//
	// macroGlasgow.limpiarEscalaGlasgow();
	// if (hisc_consulta_externa.getEscala_glasgow().equals("S")) {
	// gbxMacroGlasgow.setVisible(true);
	// macroGlasgow.mostrarEscalaGlasgow(
	// hisc_consulta_externa.getRespuesta_motora(),
	// hisc_consulta_externa.getRespuesta_verbal(),
	// hisc_consulta_externa.getApertura_ocular(),false);
	// } else {
	// gbxMacroGlasgow.setVisible(false);
	// }
	//
	// tbxOtro_acompaniante.setValue(hisc_consulta_externa
	// .getOtro_acompaniante());
	//
	// tbxNo_farmocologico.setValue(hisc_consulta_externa
	// .getNo_farmacologicos());
	// tbxEducacional_paciente.setValue(hisc_consulta_externa
	// .getEducacion_paciente());
	// tbxPlan_seguimiento.setValue(hisc_consulta_externa
	// .getPlan_seguimiento());
	//
	// ibxNacidos_vivos.setValue(hisc_consulta_externa
	// .getGineco_nacidos_vivos());
	// ibxNacidos_muertos.setValue(hisc_consulta_externa
	// .getGineco_nacidos_muertos());
	// ibxPreterminos.setValue(hisc_consulta_externa
	// .getGineco_preterminos());
	// ibxNro_gestaciones.setValue(hisc_consulta_externa
	// .getGineco_nro_gestaciones());
	// ibxNro_partos
	// .setValue(hisc_consulta_externa.getGineco_nro_partos());
	// ibxNro_abortos.setValue(hisc_consulta_externa
	// .getGineco_nro_abortos());
	// ibxNro_cesarias.setValue(hisc_consulta_externa
	// .getGineco_nro_cesarias());
	// dtbxAnt_gin_menarca.setValue(hisc_consulta_externa
	// .getGineco_menarca());
	// tbxAnt_gin_e_menopaudia.setValue(hisc_consulta_externa
	// .getGineco_menopaudia());
	// Utilidades.seleccionarListitem(lbxAnt_gin_ciclo_1,
	// hisc_consulta_externa.getGineco_ciclo1());
	//
	// dtbxAnt_gin_fecha_ultima_regla.setValue(hisc_consulta_externa
	// .getGineco_fur());
	// dtbxAnt_gin_fecha_espectante_parto.setValue(hisc_consulta_externa
	// .getGineco_fep());
	// Utilidades.seleccionarListitem(lbx_conf_fur,
	// hisc_consulta_externa.getGineco_conf_fur());
	//
	// tbxAnalisis_del_caso.setValue(hisc_consulta_externa
	// .getAnalisis_del_caso());
	//
	// tbxOtro_ciclo.setValue(hisc_consulta_externa.getOtro_ciclo());
	// tbxDescripcion_menarca.setValue(hisc_consulta_externa
	// .getDescripcion_menarca());
	//
	// if (tbxAccion.getValue().equals(
	// OpcionesFormulario.REGISTRAR.toString())) {
	// deshabilitarCampos(false);
	// }
	//
	// } catch (Exception e) {
	// MensajesUtil.mensajeError(e, "Este dato no se puede editar", this);
	// }
	// }
	public Hisc_consulta_externa getBean() {
		Hisc_consulta_externa hisc_consulta_externa = new Hisc_consulta_externa();
		hisc_consulta_externa.setCodigo_empresa(empresa.getCodigo_empresa());
		hisc_consulta_externa.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		hisc_consulta_externa.setVia_ingreso(via_ingreso);
		hisc_consulta_externa.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_consulta_externa.setNro_identificacion(admision_seleccionada
				.getNro_identificacion());
		hisc_consulta_externa.setCodigo_prestador(bandboxPrestador.getValue());
		hisc_consulta_externa
				.setFecha_ingreso(infoPacientes.getFecha_inicial());
		hisc_consulta_externa.setAcompaniante(tbxAcompaniante.getValue());
		hisc_consulta_externa.setRelacion(lbxRelacion.getSelectedItem()
				.getValue().toString());
		hisc_consulta_externa
				.setTel_acompaniante(dbxTel_acompaniante.getText());
		hisc_consulta_externa.setCedula_acomp(tbxCedula_acomp.getValue());

		// manuel
		hisc_consulta_externa.setDes_vida_marital(tbxDes_vida_Marital
				.getValue());

		/**/
		hisc_consulta_externa.setEsquema_vacunacion(tbxEsquema_vacunacion
				.getValue());
		hisc_consulta_externa.setMotivo_consulta(tbxMotivo_consulta.getValue());
		hisc_consulta_externa.setEnfermedad_actual(tbxEnfermedad_actual
				.getValue());

		hisc_consulta_externa.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_consulta_externa.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_consulta_externa.setCreacion_user(usuarios.getCodigo());
		hisc_consulta_externa.setUltimo_user(usuarios.getCodigo());
		hisc_consulta_externa.setMotivo_consulta(Util
				.agregarComillas(tbxMotivo_consulta.getValue()));

		/* cargamos los antedentes personales */
		hisc_consulta_externa.setAnte_fam_hipertension(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_hipertension));
		hisc_consulta_externa.setAnte_fam_ecv(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_ecv));
		hisc_consulta_externa.setAnte_fam_enf_coronaria(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_enf_coronaria));
		hisc_consulta_externa.setAnte_fam_muerte_im_acv(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_muerte_im_acv));
		hisc_consulta_externa.setAnte_fam_dislipidemia(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_dislipidemia));
		hisc_consulta_externa.setAnte_fam_nefropatias(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_nefropatias));
		hisc_consulta_externa.setAnte_fam_obesos(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_obesos));
		hisc_consulta_externa.setAnte_fam_diabetes(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_diabetes));
		hisc_consulta_externa.setAnte_fam_enf_mental(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_enf_mental));
		hisc_consulta_externa.setAnte_fam_cancer(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_cancer));
		hisc_consulta_externa.setAnte_fam_hematologia(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_hematologia));
		hisc_consulta_externa.setAnte_fam_otros(bandboxAnte_fam_otros
				.getValue());

		hisc_consulta_externa.setAnte_fam_asma(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_asma));
		hisc_consulta_externa.setAnte_fam_alergicos(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_alergicos));
		hisc_consulta_externa.setAnte_fam_infecciosa_vih(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_infecciosa_vih));
		hisc_consulta_externa.setAnte_fam_infecciosa_sifilis(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_infecciosa_sifilis));
		hisc_consulta_externa.setAnte_fam_infecciosa_hepatitisb(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_infecciosa_hepatitisb));
		hisc_consulta_externa.setAnte_fam_infecciosa_lepra(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_infecciosa_lepra));
		hisc_consulta_externa.setAnte_fam_infecciosa_tuberculosis(Utilidades
				.obtenerXmlTextbox(bandboxAnte_fam_infecciosa_tuberculosis));

		hisc_consulta_externa.setAnte_fam_estecifique(tbxAnte_fam_estecifique
				.getValue());
		hisc_consulta_externa
				.setAnte_fam_observaciones(tbxAnte_fam_observaciones.getValue());

		/* antecedentes ginecontreticos */
		hisc_consulta_externa.setGineco_generales(ibxGenerales_embarazos
				.getValue());
		hisc_consulta_externa
				.setGineco_malformaciones(ibxMalformaciones_embarazos
						.getValue());

		hisc_consulta_externa
				.setAnt_gin_tiene_citologia(chbAnt_gin_tiene_citologia
						.getSelectedIndex() == 0);
		if (dtbxAnt_gin_fecha_ultima_citologia.getValue() != null) {
			hisc_consulta_externa
					.setAnt_gin_fecha_ultima_citologia(new Timestamp(
							dtbxAnt_gin_fecha_ultima_citologia.getValue()
									.getTime()));
		}
		hisc_consulta_externa
				.setAnt_gin_tiene_mamografia(chbAnt_gin_tiene_mamografia
						.getSelectedIndex() == 0);
		if (dtbxAnt_gin_fecha_mamografia.getValue() != null) {
			hisc_consulta_externa.setAnt_gin_fecha_mamografia(new Timestamp(
					dtbxAnt_gin_fecha_mamografia.getValue().getTime()));
		}

		hisc_consulta_externa
				.setAnt_gin_mamografia_resultado(tbxAnt_gin_mamografia_resultado
						.getText());
		hisc_consulta_externa
				.setAnt_gin_citologia_resultado(tbxAnt_gin_citologia_resultado
						.getText());
		hisc_consulta_externa
				.setAnt_gin_tiene_planificacion(chbAnt_gin_tiene_planificacion
						.getSelectedIndex() == 0);
		if (dtbxAnt_gin_fecha_incio_planificacion.getValue() != null) {
			hisc_consulta_externa
					.setAnt_gin_fecha_incio_planificacion(new Timestamp(
							dtbxAnt_gin_fecha_incio_planificacion.getValue()
									.getTime()));
		}
		hisc_consulta_externa.setAnt_nios_peso(tbxAnt_nios_peso.getText());
		hisc_consulta_externa.setAnt_nios_talla(tbxAnt_nios_talla.getText());
		hisc_consulta_externa.setAnt_nios_psicomotor(tbxAnt_nios_psicomotor
				.getValue());
		hisc_consulta_externa.setAnt_nios_dieta(tbxAnt_nios_dieta.getValue());
		hisc_consulta_externa.setAnt_nios_vacunas(tbxAnt_nios_vacunas
				.getValue());

		hisc_consulta_externa.setFisico_estado_general(tbxFisico_estado_general
				.getValue());
		hisc_consulta_externa.setFisico_cabeza_cara(tbxFisico_cabeza_cara
				.getValue());
		hisc_consulta_externa.setFisico_ocular(tbxFisico_ocular.getValue());
		hisc_consulta_externa.setFisico_endocrinologo(tbxFisico_endocrinologo
				.getValue());
		hisc_consulta_externa.setFisico_otorrino(tbxFisico_otorrino.getValue());
		hisc_consulta_externa.setFisico_osteomuscular(tbxFisico_osteomuscular
				.getValue());
		hisc_consulta_externa
				.setFisico_cardio_pulmonar(tbxFisico_cardio_pulmonar.getValue());
		hisc_consulta_externa.setFisico_cuello(tbxFisico_cuello.getValue());
		hisc_consulta_externa.setFisico_vacular(tbxFisico_vacular.getValue());
		hisc_consulta_externa.setFisico_piel_fanera(tbxFisico_piel_fanera
				.getValue());
		hisc_consulta_externa
				.setFisico_gastointestinal(tbxFisico_gastointestinal.getValue());
		hisc_consulta_externa.setFisico_neurologico(tbxFisico_neurologico
				.getValue());
		hisc_consulta_externa.setFisico_genitourinario(tbxFisico_genitourinario
				.getValue());
		hisc_consulta_externa.setFisico_mental(tbxFisico_mental.getValue());
		hisc_consulta_externa.setObservacion_confiable(tbxobservacion_confiable
				.getValue());
		/* campos agregados por manuel */
		hisc_consulta_externa.setIntensidad(tbxIrradiacion.getValue());

		String primera_presentacion = lbxPrimera_presentacion.getSelectedItem()
				.getValue().toString();
		primera_presentacion = primera_presentacion + "|"
				+ spinnerVeces_repetido.getText();

		hisc_consulta_externa.setPrimera_presentacion(primera_presentacion);

		// hisc_consulta_externa.setRepetido(lbxRepetidos.getSelectedItem().getValue().toString());
		hisc_consulta_externa
				.setCaracteristicas_del_dolor(tbxCaracteristicas_dolor
						.getValue());
		hisc_consulta_externa.setIrradiacion(tbxIrradiacion.getValue());
		hisc_consulta_externa.setManera_forma_de_inicio(tbxManera_form_inicio
				.getValue());
		hisc_consulta_externa.setRelacionado_con(tbxRelacionado.getValue());
		hisc_consulta_externa.setEvolucion_del_cuadro(tbxEvolucion.getValue());
		hisc_consulta_externa.setSintoma_asociados(tbxSintomas_asociados
				.getValue());
		hisc_consulta_externa.setTratamientos_recibidos(tbxTratamiento_recibido
				.getValue());
		hisc_consulta_externa
				.setActualmente_se_presenta_con(tbxActualmente_presenta
						.getValue());
		hisc_consulta_externa.setLocalizacion(tbxlocalizacion.getValue());
		hisc_consulta_externa.setIntensidad(lbxIntensidad.getSelectedItem()
				.getValue().toString());

		hisc_consulta_externa.setAbdomen_enf_actual(cbxAbdomen.isChecked());
		hisc_consulta_externa.setCabeza_enf_actual(cbxCabeza.isChecked());
		hisc_consulta_externa.setTorax_enf_actual(cbxToraz.isChecked());
		hisc_consulta_externa.setCuello_enf_actual(cbxCuello.isChecked());

		if (rdSeleccion1.isSelected()) {
			hisc_consulta_externa.setSelect_enfer_act("1");
		}
		if (rdSeleccion2.isSelected()) {
			hisc_consulta_externa.setSelect_enfer_act("2");
			hisc_consulta_externa
					.setEnfermedad_actual(tbxParrafo_enfermedad_actual
							.getValue());
		}
		if (rdSeleccion3.isSelected()) {
			hisc_consulta_externa.setSelect_enfer_act("3");
			hisc_consulta_externa
					.setEnfermedad_actual(tbxParrafo_enfermedad_actual
							.getValue());
		}

		hisc_consulta_externa.setFecha_inicio_enfermedad(tbxFecha_inicio
				.getText());
		hisc_consulta_externa
				.setSintomatico_respiratorio_desc(tbxSintomatico_respiratorio
						.getValue());
		hisc_consulta_externa.setSintomatico_piel_desc(tbxSintomatico_piel
				.getValue());

		/* fin agregado por manuel */
		hisc_consulta_externa
				.setAnalisis_recomendaciones(tbxAnalisis_recomendaciones
						.getValue());
		hisc_consulta_externa
				.setHab_ejercicio_horas_semana(ibxHab_ejercicio_minutos_dias
						.getText());

		// habitos
		hisc_consulta_externa
				.setHab_alcohol(chbHab_alcohol.getSelectedIndex() == 0);
		hisc_consulta_externa
				.setHab_frecuencia_alcohol(tbxHab_frecuencia_alcohol.getValue());
		hisc_consulta_externa.setHab_licor_alcohol(tbxHab_licor_alcohol
				.getValue());

		hisc_consulta_externa.setHab_tabaquismo(chbHab_tabaquismo
				.getSelectedIndex() == 0);
		hisc_consulta_externa
				.setHab_frecuencia_tabaquismo(tbxHab_frecuencia_tabaquismo
						.getText());
		hisc_consulta_externa
				.setHab_frecuencia_tabaquismo_caja_anio(tbxHab_frecuencia_tabaquismo_caja_anio
						.getValue());

		hisc_consulta_externa.setHab_ejercicio(chbHab_ejercicio
				.getSelectedIndex() == 0);
		hisc_consulta_externa.setHab_ejercicio_cual(tbxHab_ejercicio_cual
				.getValue());
		hisc_consulta_externa
				.setHab_ejercicio_horas_semana(ibxHab_ejercicio_minutos_dias
						.getText());

		hisc_consulta_externa
				.setHab_dietas(chbHab_dietas.getSelectedIndex() == 0);
		hisc_consulta_externa.setHab_dietas_frecuencia(tbxHab_dietas_frecuencia
				.getValue());

		hisc_consulta_externa
				.setHab_factores_psicologicos(chbHab_factores_psicologicos
						.getSelectedIndex() == 0);
		hisc_consulta_externa
				.setHab_factores_psicologicos_cual(tbxHab_factores_psicologicos_cual
						.getValue());

		hisc_consulta_externa.setMetodos_orales("");

		hisc_consulta_externa
				.setSintomatico_respiratorio(lbxSintomatico_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setSintomatico_piel(lbxSintomatico_piel
				.getSelectedItem().getValue().toString());

		hisc_consulta_externa.setCabezacara_list(lbxCabezacara_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setOcular_list(lbxOcular_list.getSelectedItem()
				.getValue().toString());
		hisc_consulta_externa.setOtorrrino_list(lbxOtorrrino_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setCuello_list(lbxCuello_list.getSelectedItem()
				.getValue().toString());
		hisc_consulta_externa.setCardio_pulmonar_list(lbxCardio_pulmonar_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setAdomen_list(lbxAdomen_list.getSelectedItem()
				.getValue().toString());
		hisc_consulta_externa.setGenitourinario_list(lbxGenitourinario_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setExamen_mama_list(lbxExamen_mama_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setEndocrino_list(lbxEndocrino_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setOsteomuscular_list(lbxOsteomuscular_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setVascular_list(lbxVascular_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setPiel_faneras_list(lbxPiel_faneras_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setNeurologico_list(lbxNeurologico_list
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setMental_list(lbxMental_list.getSelectedItem()
				.getValue().toString());

		hisc_consulta_externa.setVida_marital(chbVida_marital
				.getSelectedIndex() == 0 ? "S" : "N");

		if (chbVida_marital.getSelectedIndex() == 0) {
			if (dtbxFecha_vida_marital.getValue() != null) {
				hisc_consulta_externa.setFecha_vida_marital(new Timestamp(
						dtbxFecha_vida_marital.getValue().getTime()));
			}
		} else {
			hisc_consulta_externa.setFecha_vida_marital(null);
		}

		hisc_consulta_externa.setOtro_ginecostetrico(chbOtro_ginecostetrico
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setOtras_adicciones(rdbOtras_adicciones
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setPsicofarmacos(rdbPsicofarmacos
				.getSelectedItem().getValue().toString());

		hisc_consulta_externa
				.setDescripcion_otro_ginecostetrico(tbxOtro_ginecostetrico
						.getValue());
		hisc_consulta_externa
				.setMetodos_planificacion(obtenerMetodosPlanificacion());

		hisc_consulta_externa
				.setPatologia_embarazo_parto(tbxPatologia_embarazo_parto
						.getValue());

		hisc_consulta_externa.setCual_psicofarmacos(tbxCual_psicofarmacos
				.getValue());
		hisc_consulta_externa.setCual_adicciones(tbxCual_adicciones.getValue());

		if (gbxMacroGlasgow.isVisible()) {
			hisc_consulta_externa.setEscala_glasgow("S");
			hisc_consulta_externa.setRespuesta_motora(macroGlasgow
					.obtenerRespuestaMotora());
			hisc_consulta_externa.setRespuesta_verbal(macroGlasgow
					.obtenerRespuestaVerbal());
			hisc_consulta_externa.setApertura_ocular(macroGlasgow
					.obtenerAperturaOcular());
		} else {
			hisc_consulta_externa.setEscala_glasgow("N");
		}

		if (lbxRelacion.getSelectedItem().getValue().toString().equals("8")) {
			hisc_consulta_externa.setOtro_acompaniante(tbxOtro_acompaniante
					.getValue());
		} else {
			tbxOtro_acompaniante.setValue("");
		}

		hisc_consulta_externa.setGineco_nacidos_vivos(ibxNacidos_vivos
				.getValue());
		hisc_consulta_externa.setGineco_nacidos_muertos(ibxNacidos_muertos
				.getValue());
		hisc_consulta_externa.setGineco_preterminos(ibxPreterminos.getValue());
		hisc_consulta_externa.setGineco_nro_gestaciones(ibxNro_gestaciones
				.getValue());
		hisc_consulta_externa.setGineco_nro_partos(ibxNro_partos.getValue());
		hisc_consulta_externa.setGineco_nro_abortos(ibxNro_abortos.getValue());
		hisc_consulta_externa
				.setGineco_nro_cesarias(ibxNro_cesarias.getValue());
		if (dtbxAnt_gin_menarca.getValue() != null) {
			hisc_consulta_externa.setGineco_menarca(new Timestamp(
					dtbxAnt_gin_menarca.getValue().getTime()));
		}
		hisc_consulta_externa.setGineco_menopaudia(tbxAnt_gin_e_menopaudia
				.getValue());
		hisc_consulta_externa.setGineco_ciclo1(lbxAnt_gin_ciclo_1
				.getSelectedItem().getValue().toString());
		hisc_consulta_externa.setGineco_ciclo2("1");

		if (dtbxAnt_gin_fecha_ultima_regla.getValue() != null) {
			hisc_consulta_externa.setGineco_fur(new Timestamp(
					dtbxAnt_gin_fecha_ultima_regla.getValue().getTime()));
		}

		hisc_consulta_externa.setGineco_conf_fur(lbx_conf_fur
				.getSelectedIndex() == 0 ? "" : lbx_conf_fur.getSelectedItem()
				.getValue().toString());

		hisc_consulta_externa
				.setAnt_gin_fecha_ultima_regla_desc(tbxAnt_gin_fecha_ultima_regla_desc
						.getText());

		if (dtbxAnt_gin_fecha_espectante_parto.getValue() != null) {
			hisc_consulta_externa.setGineco_fep(new Timestamp(
					dtbxAnt_gin_fecha_espectante_parto.getValue().getTime()));
		}

		hisc_consulta_externa.setNo_farmacologicos(tbxNo_farmocologico
				.getValue());
		hisc_consulta_externa.setEducacion_paciente(tbxEducacional_paciente
				.getValue());
		hisc_consulta_externa.setPlan_seguimiento(tbxPlan_seguimiento
				.getValue());

		hisc_consulta_externa.setAnalisis_del_caso(tbxAnalisis_del_caso
				.getValue());

		hisc_consulta_externa.setOtro_ciclo(tbxOtro_ciclo.getValue());
		hisc_consulta_externa.setDescripcion_menarca(tbxDescripcion_menarca
				.getValue());

		return hisc_consulta_externa;

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
					antecedentes_personales.setObservacion(Utilidades
							.obtenerXmlTextbox(textbox));
				} else {
					antecedentes_personales.setObservacion(textbox.getValue());
				}
				listadoAntecedentes.add(antecedentes_personales);
			}
		}
		return listadoAntecedentes;
	}

	private String obtenerMetodosPlanificacion() {
		StringBuffer stringBuffer = new StringBuffer();
		int i = 0;
		for (Elemento elemento : elementosMetodosPlanificacion) {
			if (box_anticonceptivos.hasFellow("checkbox_mtd_planificar_"
					+ elemento.getCodigo())) {
				Checkbox checkbox = (Checkbox) box_anticonceptivos
						.getFellow("checkbox_mtd_planificar_"
								+ elemento.getCodigo());
				stringBuffer.append(elemento.getCodigo() + "="
						+ (checkbox.isChecked() ? "S" : "N"));
			}
			i++;
			if (i != elementosMetodosPlanificacion.size()) {
				stringBuffer.append("|");
			}
		}
		return stringBuffer.toString();
	}

	private void cargarMetodosPlanificicacion(String metodos_planificacion) {
		if (metodos_planificacion != null && !metodos_planificacion.isEmpty()) {
			Map<String, String> mapa_elementos = new HashMap<String, String>();
			StringTokenizer stringTokenizer = new StringTokenizer(
					metodos_planificacion, "|");
			while (stringTokenizer.hasMoreTokens()) {
				String metodo = stringTokenizer.nextToken();
				StringTokenizer stringTokenizer2 = new StringTokenizer(metodo,
						"=");
				mapa_elementos.put(stringTokenizer2.nextToken(),
						stringTokenizer2.nextToken());
			}

			for (String llave_mapa : mapa_elementos.keySet()) {
				String valor = mapa_elementos.get(llave_mapa);
				if (box_anticonceptivos.hasFellow("checkbox_mtd_planificar_"
						+ llave_mapa)) {
					Checkbox checkbox = (Checkbox) box_anticonceptivos
							.getFellow("checkbox_mtd_planificar_" + llave_mapa);
					checkbox.setChecked(valor.equals("S"));
				}
			}

		}
	}

	private void cargarSignosVitales(Hisc_consulta_externa hisc_consulta_externa) {
		Sigvitales sigvitales = new Sigvitales();
		sigvitales.setCodigo_empresa(codigo_empresa);
		sigvitales.setCodigo_sucursal(codigo_sucursal);
		sigvitales.setCodigo_historia(hisc_consulta_externa
				.getCodigo_historia());
		sigvitales = getServiceLocator().getSigvitalesService().consultar(
				sigvitales);
		mcSignosVitales.mostrarSigvitales(sigvitales);
	}

	private void cargarImpresionDiagnostica(
			Hisc_consulta_externa hisc_consulta_externa) throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc_consulta_externa
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	private void cargarAnexo9_remision(
			Hisc_consulta_externa hisc_consulta_externa) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_consulta_externa
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(hisc_consulta_externa.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		boolean creada = false;
		if (anexo9_entidad != null) {
			creada = true;
		}
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
		if (creada) {
			remisiones_externasAction.getBotonGenerar().setVisible(false);
		}
		remisiones_externasAction.setNro_historia(hisc_consulta_externa
				.getCodigo_historia());
	}

	private Popup generarPopupObservaciones(Elemento elemento,
			final Textbox textboxObservacion, Component contenedor) {

		final Popup popup = new Popup();
		final Textbox textbox = new Textbox();

		popup.addEventListener(Events.ON_OPEN, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				textbox.setValue(textboxObservacion.getValue());
				// textbox.setFocus(true);
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
			textbox.addEventListener("onChanging", new EventListener<Event>() {

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
				mapa_aux = ConvertidorXmlToMap.convertirToMap(Utilidades
						.obtenerXmlTextbox(textboxObservacion));
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

						Utilidades.mostrarXmlTextbox(textboxObservacion,
								mapa_contenido);

					}

				}
			};

			textbox_observacion.addEventListener(Events.ON_CHANGE,
					new EventListener<InputEvent>() {

						@Override
						public void onEvent(InputEvent event) throws Exception {
							mapa_contenido.put("OBSERVACION", event.getValue());
							Utilidades.mostrarXmlTextbox(textboxObservacion,
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
					hlayoutAntecedentes_personales, true, new String[] {
							"northEditar", "groupboxConsultar" });
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

	public void onMostrarTuberculosis() {
		Boolean visible = gbxTuberculosis.isVisible();
		if (!visible) {
			btnMostrar_tuberculosis.setLabel("Ocultar Evaluacion Tuberculosis");
			gbxTuberculosis.setVisible(true);
		} else {
			btnMostrar_tuberculosis.setLabel("Mostrar Evaluacion Tuberculosis");
			gbxTuberculosis.setVisible(false);
		}
	}

	public void onMostrarModuloFarmacologico() throws Exception {
		if (receta_ripsAction != null) {
			receta_ripsAction.detach();
		}
		// log.info("creando la ventana receta_ripsAction");
		Map<String, Object> parametros = new HashMap<String, Object>();
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

		receta_ripsAction = (Receta_rips_internoAction) Executions
				.createComponents("/pages/receta_rips_interno.zul", null,
						parametros);
		receta_ripsAction.inicializar(this);
		divModuloFarmacologico.appendChild(receta_ripsAction);

	}

	public void onMostrarModuloOrdenamiento() throws Exception {
		// log.info("Llamado al metodo onMostrarModuloOrdenamiento()");
		if (orden_servicioAction != null) {
			orden_servicioAction.detach();
		}
		// if (!cargo_farmacologico) {
		// log.info("creando la ventana receta_ripsAction");
		Map<String, Object> parametros = new HashMap<String, Object>();
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
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }

	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null) {
			remisiones_externasAction.detach();
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
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
			strBuffer.append("Paciente acompañado por su "
					+ lbxRelacion.getSelectedItem().getLabel()
					+ " quien consulta por cuadro clínico presentado ");
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
			// textboxAntecedentes.setFocus(true);
		} else {
			// textboxAntecedentes.setFocus(true);
		}
	}

	public void onSeleccionarOtros_ciclos(Listbox listboxCliclo,
			Row rowOtrosCiclos) {
		if (listboxCliclo.getSelectedItem().getValue().toString().equals("3")) {
			rowOtros_ciclos.setVisible(true);
		} else {
			rowOtros_ciclos.setVisible(false);
		}
	}

	public void generarContendioAntecedentesFamiliares(
			Bandbox bandbox_antecedente) {
		Utilidades.generarContendioAntecedentesFamiliares(bandbox_antecedente);
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
		} else {
			lbVeces_repetido.setVisible(false);
			spinnerVeces_repetido.setVisible(false);
			spinnerVeces_repetido.setValue(0);
		}
	}

	@Override
	public void initHistoria() throws Exception {
		macroImpresion_diagnostica.inicializarMacro(this,
				admision_seleccionada, admision_seleccionada.getVia_ingreso());
		macroRemision_interna.inicializarMacro(this, admision_seleccionada,
				admision_seleccionada.getVia_ingreso());
		if (parametros_empresa != null
				&& parametros_empresa.getSignos_enfermera().equals("S")) {
			toolbarbuttonCargar_signos.setVisible(true);
		} else {
			toolbarbuttonCargar_signos.setVisible(false);
		}

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

				if (admision_seleccionada.getAtendida()) {
					opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
					btnCancelar.setVisible(mostrar_boton_consultar);
					Hisc_consulta_externa hisc_consulta_externa = new Hisc_consulta_externa();
					hisc_consulta_externa.setCodigo_empresa(empresa
							.getCodigo_empresa());
					hisc_consulta_externa.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					hisc_consulta_externa
							.setNro_identificacion(admision_seleccionada
									.getNro_identificacion());
					hisc_consulta_externa.setVia_ingreso(admision_seleccionada
							.getVia_ingreso());
					hisc_consulta_externa.setNro_ingreso(admision_seleccionada
							.getNro_ingreso());
					hisc_consulta_externa.setVia_ingreso(admision_seleccionada
							.getVia_ingreso());

					hisc_consulta_externa = getServiceLocator()
							.getHisc_consulta_externaService().consultar(
									hisc_consulta_externa);

					if (hisc_consulta_externa != null) {
						accionForm(OpcionesFormulario.MOSTRAR,
								hisc_consulta_externa);
						infoPacientes.cargarInformacion(admision_seleccionada,
								this, null);
						infoPacientes.setCodigo_historia(hisc_consulta_externa
								.getCodigo_historia());
						// log.info("Codigo de la historia ===> "
						// + infoPacientes.getCodigo_historia());
					} else {
						groupboxEditar.setVisible(false);
						throw new ValidacionRunTimeException(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				} else {
					if (opciones_via_ingreso
							.equals(Opciones_via_ingreso.REGISTRAR)) {
						accionForm(OpcionesFormulario.REGISTRAR, null);
						btnCancelar.setVisible(false);
					} else {
						accionForm(OpcionesFormulario.CONSULTAR, null);
						btnCancelar.setVisible(true);
					}
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
	public void cargarInformacion_paciente() {
		if (admision_seleccionada.getPaciente().getSexo().equals("M")) {
			gbxAntecedentes_ginecostetrico.setVisible(false);
			rowFisico_examen_mama.setVisible(false);
		} else {
			gbxAntecedentes_ginecostetrico.setVisible(true);
			rowFisico_examen_mama.setVisible(true);

		}
		mcSignosVitales.setFecha_nacimiento(admision_seleccionada.getPaciente()
				.getFecha_nacimiento());
		mcSignosVitales
				.setGenero(admision_seleccionada.getPaciente().getSexo());

		mcSignosVitales.inicializarParametrosAlertas();

		if (cita_seleccionada != null) {
			tbxAcompaniante.setValue(cita_seleccionada.getAcompaniante() + " "
					+ cita_seleccionada.getApellidos_acomp());
			dbxTel_acompaniante.setValue(ConvertidorDatosUtil
					.convertirDato(cita_seleccionada.getTel_acompaniante()));
			tbxCedula_acomp.setValue(cita_seleccionada.getCedula_acomp());
			Utilidades.seleccionarListitem(lbxRelacion,
					cita_seleccionada.getRelacion());
			onSeleccionarRelacionAcompaniante();
			tbxOtro_acompaniante.setValue(cita_seleccionada
					.getOtro_acompaniante());
		}

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

							List<Hisc_consulta_externa> listado_historias = getServiceLocator()
									.getHisc_consulta_externaService().listar(
											parametros);

							if (!listado_historias.isEmpty()) {
								final Hisc_consulta_externa hisc_consulta_externa = listado_historias
										.get(0);

								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								Messagebox.show(
										"Existe una historia clinica creada para la fecha "
												+ formatFecha
														.format(hisc_consulta_externa
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
													cargarInformacion_historia_anterior(hisc_consulta_externa);
												}
											}
										});

								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica");
								admision_seleccionada.setPrimera_vez("N");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica por primera vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision_seleccionada.setPrimera_vez("S");
							}
						} else {
							if (codigo_historia_anterior != null
									&& tipo_historia
											.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Hisc_consulta_externa hisc_hd = new Hisc_consulta_externa();
								hisc_hd.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc_hd.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc_hd.setCodigo_historia(codigo_historia_anterior);
								hisc_hd.setVia_ingreso(via_ingreso);

								hisc_hd = getServiceLocator()
										.getHisc_consulta_externaService()
										.consultar(hisc_hd);

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
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Hisc_consulta_externa hisc_consulta_externa = (Hisc_consulta_externa) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil
					.deshabilitarComponentes(groupboxEditar, true, idsExc);
			if (hisc_consulta_externa.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);

		} else {
			if (hisc_consulta_externa.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}

			((Button) getFellow("btGuardar")).setVisible(true);
		}

		codigo_historia_anterior = hisc_consulta_externa
				.getCodigo_historia_anterior();
		tipo_historia = hisc_consulta_externa.getTipo_historia();
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
				informacion_clinica.append("Nefropatías:\t").append(
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

		informacion_clinica.append("- SIGNOS VITALES: \t");
		informacion_clinica.append("Frecuencia cardiaca(min): ")
				.append(mcSignosVitales.getFrecuencia_cardiaca() + (","))
				.append("\t").append("Frecuencia respiratoria(min): ")
				.append(mcSignosVitales.getFrecuencia_respiratoria() + (","));
		informacion_clinica.append("\tTemperatura: ")
				.append(mcSignosVitales.getTemperatura() + (","))
				.append(" \tPeso(kg): ")
				.append(mcSignosVitales.getPeso() + (","))
				.append(" \t Talla(Cm): ")
				.append(mcSignosVitales.getTalla() + (",")).append("\t")
				.append("S. Corporal(Mts2): ")
				.append(mcSignosVitales.getMcDbxSuperficie_corporal() + (","))
				.append("\t").append("\n").append("C. Serica(mg/dl): ")
				.append(mcSignosVitales.getMcDbxCreatinina_serica() + (","))
				.append("\t").append("IMC: ").append(mcSignosVitales.getIMC())
				.append(" - ").append(mcSignosVitales.getAlerta_imc() + (","))
				.append("\tTA Sistolica: ")
				.append(mcSignosVitales.getMcDbxTA_sistolica() + (","))
				.append("\tTA Diastolica: ")
				.append(mcSignosVitales.getMcDbxTA_diastolica() + (","))
				.append("\t").append("\tTA Media: ")
				.append(mcSignosVitales.getMcDbxTA_media() + (","))
				.append("\t").append("\tT.F.C: ")
				.append(mcSignosVitales.getMcDbxTFG() + (".")).append("\n");

		informacion_clinica.append("\n");

		if (!tbxFisico_estado_general.getValue().isEmpty()) {
			informacion_clinica.append("- EXAMEN FISICO").append("\n")
					.append("\tEstado general: ")
					.append(tbxFisico_estado_general.getValue()).append("\n");
		}
		if (!tbxFisico_cabeza_cara.getValue().isEmpty()) {
			informacion_clinica.append("\tCabeza y cara: ")
					.append(tbxFisico_cabeza_cara.getValue()).append("\n");
		}
		if (!tbxFisico_ocular.getValue().isEmpty()) {
			informacion_clinica.append("\tOcular: ")
					.append(tbxFisico_ocular.getValue()).append("\n");
		}
		if (!tbxFisico_otorrino.getValue().isEmpty()) {
			informacion_clinica.append("\tOtorrino: ")
					.append(tbxFisico_otorrino.getValue()).append("\n");
		}
		if (!tbxFisico_cuello.getValue().isEmpty()) {
			informacion_clinica.append("\tCuello: ")
					.append(tbxFisico_cuello.getValue()).append("\n");
		}
		if (!tbxFisico_cardio_pulmonar.getValue().isEmpty()) {
			informacion_clinica.append("\tCardio pulmonar: ")
					.append(tbxFisico_cardio_pulmonar.getValue()).append("\n");
		}
		if (!tbxFisico_examen_mama.getValue().isEmpty()) {
			informacion_clinica.append("\tExámen de mama: ")
					.append(tbxFisico_examen_mama.getValue()).append("\n");
		}
		if (!tbxFisico_gastointestinal.getValue().isEmpty()) {
			informacion_clinica.append("\tAbdomen: ")
					.append(tbxFisico_gastointestinal.getValue()).append("\n");
		}
		if (!tbxFisico_genitourinario.getValue().isEmpty()) {
			informacion_clinica.append("\tGenitourinario: ")
					.append(tbxFisico_genitourinario.getValue()).append("\n");
		}
		if (!tbxFisico_endocrinologo.getValue().isEmpty()) {
			informacion_clinica.append("\tEndocrino: ")
					.append(tbxFisico_endocrinologo.getValue()).append("\n");
		}
		if (!tbxFisico_osteomuscular.getValue().isEmpty()) {
			informacion_clinica.append("\tExtremidades: ")
					.append(tbxFisico_osteomuscular.getValue()).append("\n");
		}
		if (!tbxFisico_vacular.getValue().isEmpty()) {
			informacion_clinica.append("\tVascular: ")
					.append(tbxFisico_vacular.getValue()).append("\n");
		}
		if (!tbxFisico_piel_fanera.getValue().isEmpty()) {
			informacion_clinica.append("\tPiel y Faneras: ")
					.append(tbxFisico_piel_fanera.getValue()).append("\n");
		}
		if (!tbxFisico_neurologico.getValue().isEmpty()) {
			informacion_clinica.append("\tNeurologico: ")
					.append(tbxFisico_neurologico.getValue()).append("\n");
		}
		if (!tbxFisico_mental.getValue().isEmpty()) {
			informacion_clinica.append("\tMental: ")
					.append(tbxFisico_mental.getValue()).append("\n");
			if (!tbxobservacion_confiable.getValue().isEmpty()) {
				informacion_clinica.append("\tObservacion:");
			}
		}
		if (!tbxAnalisis_recomendaciones.getValue().isEmpty()) {
			informacion_clinica.append("\tExamenes traidos por el paciente: ")
					.append(tbxAnalisis_recomendaciones.getValue())
					.append("\n");
		}

		informacion_clinica.append("\n");

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
							"tipo_diagnostico", this)).append("\n")
					.append("\n");
		}

		informacion_clinica.append("\n");
		informacion_clinica.append("\n:");

		if (!tbxNo_farmocologico.getValue().isEmpty()) {
			informacion_clinica.append("\tNO FARMACOLÓGICOS/RECOMENDACIONES:")
					.append("\t").append(tbxNo_farmocologico.getValue())
					.append("\n");
		}
		if (!tbxEducacional_paciente.getValue().isEmpty()) {
			informacion_clinica
					.append("\tEDUCACIONAL AL PACIENTE Y LA FAMILIA:");
			informacion_clinica.append("\t")
					.append(tbxEducacional_paciente.getValue()).append("\n");
		}
		if (!tbxPlan_seguimiento.getValue().isEmpty()) {
			informacion_clinica.append("\tPLAN DE SEGUIMIENTO Y CONTROL:");
			informacion_clinica.append("\t")
					.append(tbxPlan_seguimiento.getValue()).append("\n");
		}

		return informacion_clinica.toString();
	}

	// ---------- Inicio Tuberculosis
	// Metodo para guardar la informacion de tuberculosis //
	public void guardarTuberculosis() throws Exception {
		try {

			His_formato_tuberculosis his_formato_tuberculosis = new His_formato_tuberculosis();
			his_formato_tuberculosis.setCodigo_empresa(empresa
					.getCodigo_empresa());
			his_formato_tuberculosis.setCodigo_sucursal(sucursal
					.getCodigo_sucursal());
			his_formato_tuberculosis
					.setIdentificacion(admision_seleccionada != null ? admision_seleccionada
							.getNro_identificacion() : null);
			his_formato_tuberculosis.setFecha_inicial(new Timestamp(
					infoPacientes.getFecha_inicial().getTime()));
			his_formato_tuberculosis.setContacto(rdbContacto.getSelectedItem()
					.getValue().toString());
			his_formato_tuberculosis.setContacto_quien(tbxContacto_quien
					.getValue());
			his_formato_tuberculosis.setTos_persistente(rdbTos_persistente
					.getSelectedItem().getValue().toString());
			his_formato_tuberculosis
					.setTos_dias((ibxTos_dias.getValue() != null ? ibxTos_dias
							.getValue() + "" : ""));
			his_formato_tuberculosis.setFiebre(rdbFiebre.getSelectedItem()
					.getValue().toString());
			his_formato_tuberculosis
					.setFiebre_dias((ibxFiebre_dias.getValue() != null ? ibxFiebre_dias
							.getValue() + ""
							: ""));
			his_formato_tuberculosis.setPerdida_peso(rdbPerdida_peso
					.getSelectedItem().getValue().toString());
			his_formato_tuberculosis.setAdenomegalia(rdbAdenomegalia
					.getSelectedItem().getValue().toString());
			his_formato_tuberculosis
					.setAdenomegalia_explique(tbxAdenomegalia_explique
							.getValue());

			if (dtbxPpd.getValue() != null) {
				his_formato_tuberculosis.setPpd(new Timestamp(dtbxPpd
						.getValue().getTime()));

			} else {
				his_formato_tuberculosis.setPpd(null);
			}

			his_formato_tuberculosis.setPpd_resultado(tbxPpd_resultado
					.getValue());

			if (dtbxTorax.getValue() != null) {
				his_formato_tuberculosis.setTorax(new Timestamp(dtbxTorax
						.getValue().getTime()));

			} else {
				his_formato_tuberculosis.setTorax(null);
			}

			his_formato_tuberculosis.setTorax_resultado(tbxTorax_resultado
					.getValue());
			his_formato_tuberculosis.setSigno_general(chbSigno_general
					.isChecked());
			his_formato_tuberculosis.setRigidez(chbRigidez.isChecked());
			his_formato_tuberculosis.setComportamiento(chbComportamiento
					.isChecked());
			his_formato_tuberculosis.setTiraje(chbTiraje.isChecked());
			his_formato_tuberculosis.setInfeccion(chbInfeccion.isChecked());
			his_formato_tuberculosis.setDesnutricion(chbDesnutricion
					.isChecked());
			his_formato_tuberculosis.setCultivo_esputo(chbCultivo_esputo
					.isChecked());
			his_formato_tuberculosis.setCultivo_jugo(chbCultivo_jugo
					.isChecked());
			his_formato_tuberculosis.setBaciloscopia(chbBaciloscopia
					.isChecked());
			his_formato_tuberculosis.setHistopatologia(chbHistopatologia
					.isChecked());
			his_formato_tuberculosis.setPcr(chbPcr.isChecked());
			his_formato_tuberculosis
					.setObservaciones(tbxObservaciones_tuberculosis.getValue());

			his_formato_tuberculosis.setCreacion_date(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			his_formato_tuberculosis.setUltimo_update(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			his_formato_tuberculosis.setCreacion_user(usuarios.getCodigo()
					.toString());
			his_formato_tuberculosis.setDelete_date(null);
			his_formato_tuberculosis.setUltimo_user(usuarios.getCodigo()
					.toString());
			his_formato_tuberculosis.setDelete_user(null);

			// log.info("his_formato_tuberculosis" + his_formato_tuberculosis);
			if (existeTuberculosis(his_formato_tuberculosis) == null) {
				his_formato_tuberculosisService.crear(his_formato_tuberculosis);
			} else {
				his_formato_tuberculosisService
						.actualizar(his_formato_tuberculosis);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarTuberculosis(Object obj) throws Exception {
		His_formato_tuberculosis his_formato_tuberculosis = (His_formato_tuberculosis) obj;
		try {

			if (his_formato_tuberculosis != null) {

				Utilidades.seleccionarRadio(rdbContacto,
						his_formato_tuberculosis.getContacto());
				if (his_formato_tuberculosis.getContacto().equals("S")) {
					lbContacto_quien.setVisible(true);
					tbxContacto_quien.setVisible(true);
					tbxContacto_quien.setValue(his_formato_tuberculosis
							.getContacto_quien());
				} else {
					lbContacto_quien.setVisible(false);
					tbxContacto_quien.setVisible(false);

				}
				Utilidades.seleccionarRadio(rdbTos_persistente,
						his_formato_tuberculosis.getTos_persistente());
				if (his_formato_tuberculosis.getTos_persistente().equals("S")) {
					lbTos_dias.setVisible(true);
					lbdias.setVisible(true);
					ibxTos_dias.setVisible(true);
					ibxTos_dias.setValue((his_formato_tuberculosis
							.getTos_dias() != null && !his_formato_tuberculosis
							.getTos_dias().isEmpty()) ? Integer
							.parseInt(his_formato_tuberculosis.getTos_dias())
							: null);
				} else {
					lbTos_dias.setVisible(false);
					lbdias.setVisible(false);
					ibxTos_dias.setVisible(false);

				}

				Utilidades.seleccionarRadio(rdbFiebre,
						his_formato_tuberculosis.getFiebre());
				if (his_formato_tuberculosis.getFiebre().equals("S")) {
					lbFiebre_dias.setVisible(true);
					lbdias2.setVisible(true);
					ibxFiebre_dias.setVisible(true);
					ibxFiebre_dias
							.setValue((his_formato_tuberculosis
									.getFiebre_dias() != null && !his_formato_tuberculosis
									.getFiebre_dias().isEmpty()) ? Integer
									.parseInt(his_formato_tuberculosis
											.getFiebre_dias()) : null);
				} else {
					lbFiebre_dias.setVisible(false);
					lbdias2.setVisible(false);
					ibxFiebre_dias.setVisible(false);

				}

				Utilidades.seleccionarRadio(rdbPerdida_peso,
						his_formato_tuberculosis.getPerdida_peso());
				Utilidades.seleccionarRadio(rdbAdenomegalia,
						his_formato_tuberculosis.getAdenomegalia());
				if (his_formato_tuberculosis.getAdenomegalia().equals("S")) {
					lbAdenomegalia_explique.setVisible(true);
					tbxAdenomegalia_explique.setVisible(true);
					tbxAdenomegalia_explique.setValue(his_formato_tuberculosis
							.getAdenomegalia_explique());
				} else {
					lbAdenomegalia_explique.setVisible(false);
					tbxAdenomegalia_explique.setVisible(false);

				}
				dtbxPpd.setValue(his_formato_tuberculosis.getPpd());
				tbxPpd_resultado.setValue(his_formato_tuberculosis
						.getPpd_resultado());
				dtbxTorax.setValue(his_formato_tuberculosis.getTorax());
				tbxTorax_resultado.setValue(his_formato_tuberculosis
						.getTorax_resultado());
				chbSigno_general.setChecked(his_formato_tuberculosis
						.getSigno_general());
				chbRigidez.setChecked(his_formato_tuberculosis.getRigidez());
				chbComportamiento.setChecked(his_formato_tuberculosis
						.getComportamiento());
				chbTiraje.setChecked(his_formato_tuberculosis.getTiraje());
				chbInfeccion
						.setChecked(his_formato_tuberculosis.getInfeccion());
				chbDesnutricion.setChecked(his_formato_tuberculosis
						.getDesnutricion());
				chbCultivo_esputo.setChecked(his_formato_tuberculosis
						.getCultivo_esputo());
				chbCultivo_jugo.setChecked(his_formato_tuberculosis
						.getCultivo_jugo());
				chbBaciloscopia.setChecked(his_formato_tuberculosis
						.getBaciloscopia());
				chbHistopatologia.setChecked(his_formato_tuberculosis
						.getHistopatologia());
				chbPcr.setChecked(his_formato_tuberculosis.getPcr());
				tbxObservaciones_tuberculosis.setValue(his_formato_tuberculosis
						.getObservaciones());

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public His_formato_tuberculosis existeTuberculosis(
			His_formato_tuberculosis his_formato_tuberculosis) {

		his_formato_tuberculosis.setCodigo_empresa(empresa.getCodigo_empresa());
		his_formato_tuberculosis.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		his_formato_tuberculosis
				.setIdentificacion(admision_seleccionada != null ? admision_seleccionada
						.getNro_identificacion() : null);

		his_formato_tuberculosis = his_formato_tuberculosisService
				.consultar(his_formato_tuberculosis);
		return his_formato_tuberculosis;

	}

	public void mostrar_conRadio(ZKWindow form, Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			// System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	private void cargarRemisionInterna(
			Hisc_consulta_externa hisc_consulta_externa) throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_consulta_externa
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_consulta_externa
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_consulta_externa
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	public void deshabilitarResultado(Datebox datebox, Textbox textbox) {
		try {

			if (datebox.getValue() != null) {
				textbox.setReadonly(false);
				textbox.setValue("");
			} else {
				textbox.setReadonly(true);
				textbox.setValue("");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
			e.printStackTrace();
		}
	}

	// ---------------- Fin Tuberculosis
	public boolean validarFormulaGinecobsterica() {
		Integer n_embarazos = ibxNro_gestaciones.getValue() != null ? ibxNro_gestaciones
				.getValue() : 0;
		Integer n_partos = ibxNro_partos.getValue() != null ? ibxNro_partos
				.getValue() : 0;
		Integer n_cesarias = ibxNro_cesarias.getValue() != null ? ibxNro_cesarias
				.getValue() : 0;
		Integer n_abortos = ibxNro_abortos.getValue() != null ? ibxNro_abortos
				.getValue() : 0;
		Integer n_vivos = ibxNacidos_vivos.getValue() != null ? ibxNacidos_vivos
				.getValue() : 0;

		StringBuffer stringBuffer = new StringBuffer();

		boolean valida = true;

		if (n_partos > n_embarazos) {
			stringBuffer.append("El Número de partos (").append(n_partos)
					.append(") no puede ser mayor al Número de embarazos (")
					.append(n_embarazos).append(") ");
			valida = false;
		} else if (n_cesarias > n_embarazos) {
			stringBuffer.append("El Número de cesarias (").append(n_cesarias)
					.append(") no puede ser mayor al Número de embarazos (")
					.append(n_embarazos).append(") ");
			valida = false;
		} else if (n_abortos > n_embarazos) {
			stringBuffer.append("El Número de abortos (").append(n_abortos)
					.append(") no puede ser mayor al Número de embarazos (")
					.append(n_embarazos).append(") ");
			valida = false;
		} else if (n_vivos > n_embarazos) {
			stringBuffer.append("El Número de nacidos vivos (").append(n_vivos)
					.append(") no puede ser mayor al Número de embarazos (")
					.append(n_embarazos).append(") ");
			valida = false;
		} else {
			if (n_embarazos.intValue() != (n_cesarias.intValue()
					+ n_partos.intValue() + n_abortos.intValue())) {
				stringBuffer
						.append("El Número de embarazos (")
						.append(n_embarazos)
						.append(") debe ser igual a la suma del Número de partos más el Número de cesarias más el Número de abortos. G = (P + C + A)");
				valida = false;
			} else if (n_vivos.intValue() > (n_partos.intValue() + n_cesarias
					.intValue())) {
				stringBuffer
						.append("El Número de nacidos vivos (")
						.append(n_vivos)
						.append(") no puede ser mayor que la suma del Número de partos más el Número de cesarias");
				valida = false;
			}
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta("Verificar la formula Ginecobstetrica",
					stringBuffer.toString(), new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							Clients.scrollIntoView(gbxGPAC);
						}
					});
		}

		return valida;

	}

	public void calcularFechaEsperadaParto() {
		if (dtbxAnt_gin_fecha_ultima_regla.getText().isEmpty()) {
			dtbxAnt_gin_fecha_espectante_parto.setText("");
			Clients.showNotification(
					"Seleccione para Calcular Fecha utima menstruacion",
					Clients.NOTIFICATION_TYPE_WARNING,
					dtbxAnt_gin_fecha_espectante_parto, "end_before", 4000,
					true);
		} else if (dtbxAnt_gin_fecha_ultima_regla.getValue() != null) {
			Calendar calendar_fur = Calendar.getInstance();
			calendar_fur.setTime(dtbxAnt_gin_fecha_ultima_regla.getValue());
			calendar_fur.set(Calendar.MONTH,
					calendar_fur.get(Calendar.MONTH) + 9);
			dtbxAnt_gin_fecha_espectante_parto.setValue(calendar_fur.getTime());
		} else {
			dtbxAnt_gin_fecha_espectante_parto.setText("");
		}
	}

	@Override
	public String getPersonaAcompaniante() {
		return tbxAcompaniante.getValue();
	}

	@Override
	public String getIdentificacionAcompaniante() {
		return tbxCedula_acomp.getValue();
	}

	@Override
	public String getTelefonoAcompaniante() {
		return dbxTel_acompaniante.getText();
	}

	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("CONSULTA EXTERNA");
		return serivicio1.toString();
	}

	public void LimpiarPlantilla() {
		tbxEnfermedad_actual.setText("");
		tbxFecha_inicio.setText("");
		lbxPrimera_presentacion.setSelectedIndex(0);
		spinnerVeces_repetido.setText("1");
		tbxManera_form_inicio.setText("");
		tbxRelacionado.setText("");
		tbxSintomas_asociados.setText("");
		tbxEvolucion.setText("");
		tbxTratamiento_recibido.setText("");
		tbxActualmente_presenta.setText("");
		tbxlocalizacion.setText("");
		tbxIrradiacion.setText("");
		lbxIntensidad.setSelectedIndex(0);
		tbxCaracteristicas_dolor.setText("");
		tbxParrafo_enfermedad_actual.setText("");
	}

	public void LimpiarMotivo_consulta() {
		tbxMotivo_consulta.setText("");
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

	public void imprimir() throws Exception {
		Long nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia == null) {
			Messagebox.show("La historia no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Historia_clinica_consulta_externa");
		paramRequest.put("nro_historia", nro_historia);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

}
