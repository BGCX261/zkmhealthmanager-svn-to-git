/*
 * hisc_deteccion_alt_embarazoAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_deteccion_alt_embarazo;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Presultados_paraclinicos;
import healthmanager.modelo.bean.Puntos_graficas_embarazo;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Puntos_graficas_embarazoService;
import healthmanager.modelo.service.Remision_internaService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

import sios.modelo.service.Hisc_historialSiosService;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.GraficasEmbarazoMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorParaclinicos;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesSios;

public class Hisc_deteccion_alt_embarazoAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_deteccion_alt_embarazoAction.class);

	private GeneralExtraService generalExtraService;

	private Puntos_graficas_embarazo punto_altura_uterina;
	private Puntos_graficas_embarazo punto_incremento_materno;
	private Puntos_graficas_embarazo punto_presion_diastolica;

	private Integer subtotalNum1 = 0;
	private Integer subtotalNum2 = 0;

	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
	@View
	private Vbox vboxParaclinicos;
	private ManejadorParaclinicos manejadorParaclinicos;

	@View
	private Vbox vboxValoracion_obstetrica;
	private ManejadorParaclinicos manejadorValoracion_obstetrica;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	@View
	private Grid gridSubtotalesBiopsicosocial;
	@View
	private Groupbox gbxValoracionRiesgoObstetrico;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	@View
	private Listbox lbxTipoHistoria;

	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;

	// Componentes //
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View
	private Radiogroup rdbSifilis_gestacional;
	@View
	private Radiogroup rdbRecuerda_fum;
	@View
	private Textbox tbxObservaciones_sifilis_gestacional;
	@View
	private Groupbox gbxSifilisGestacional;
	@View
	private Datebox dtbxFecha_dosis1;
	@View
	private Datebox dtbxFecha_dosis2;
	@View
	private Datebox dtbxFecha_dosis3;

	@View
	private Listbox lbxParameter;
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
	private Doublebox dbxPeso;
	@View
	private Doublebox dbxImc;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxGestion_actual;
	@View
	private Radiogroup rdbCefalea_rev_sis;
	@View
	private Radiogroup rdbTranstornos_visuales_rev_siste;
	@View
	private Radiogroup rdbTranstornos_auditivos_rev_sistema;
	@View
	private Radiogroup rdbEpigastralgia_rev_sistema;
	@View
	private Radiogroup rdbEdema_cara_rev_sistema;
	@View
	private Radiogroup rdbEdema_manos_rev_sistema;
	@View
	private Radiogroup rdbEdema_msls_rev_sistema;
	@View
	private Radiogroup rdbAusencia_mov_fetal_rev_sistema;
	@View
	private Radiogroup rdbSangrado_genitales_rev_sistema;
	@View
	private Radiogroup rdbAmniorrea_rev_sistema;
	@View
	private Radiogroup rdbLeucorrea_rev_sistema;
	@View
	private Radiogroup rdbSint_urinarios_rev_sistema;
	@View
	private Textbox tbxObservacion_revision_sistema;
	@View
	private Intbox ibxMenarquia_anios;
	@View
	private Datebox dtbxFecha_ultima_mestruacion;
	@View
	private Radiogroup rdbFecha_ultima_mestrua_confiable;
	@View
	private Radiogroup rdbAbortadora_habitual;
	@View
	private Radiogroup rdbHis_infertibilidad;
	@View
	private Intbox ibxNum_embarazo_g;
	@View
	private Intbox ibxNum_partos_p;
	@View
	private Intbox ibxNum_cesarias_c;
	@View
	private Intbox ibxNum_aborto_a;
	@View
	private Intbox ibxNum_mortinato_v;
	@View
	private Radiogroup rdbCiclo_mestrual;
	@View
	private Datebox dtbxFecha_probable_parto_fpp;
	@View
	private Datebox dtbxFecha_ultimo_parto;
	@View
	private Radiogroup rdbUsaba_anticonceptivo;
	@View
	private Radiogroup rdbFecha_posible_parto_duda;
	@View
	private Radiogroup rdbPadre_feto;
	@View
	private Radiogroup rdbAmenaza_aborto;
	@View
	private Radiogroup rdbAmenaza_parto_pretermino;
	@View
	private Radiogroup rdbRuptura_membrana_prematura;
	@View
	private Radiogroup rdbParto_pretermino;
	@View
	private Radiogroup rdbRetardo_crec_intaute_recien_nacido;
	@View
	private Radiogroup rdbOligohidramos;
	@View
	private Radiogroup rdbGemelar;
	@View
	private Radiogroup rdbPolihidramnios;
	@View
	private Radiogroup rdbEmbarazo_prolongado;
	@View
	private Radiogroup rdbRecien_nacido_macrosomico;
	@View
	private Radiogroup rdbHipertension_preclamsia;
	@View
	private Radiogroup rdbDiabetes_gestacional;
	@View
	private Radiogroup rdbMortinato;
	@View
	private Radiogroup rdbMuerte_neonatal;
	@View
	private Radiogroup rdbHospitalizacion_neonatal;
	@View
	private Textbox tbxObservaciones_embarazos_anteriores;
	@View
	private Radiogroup rdbHipertension_arterial;
	@View
	private Radiogroup rdbDiabetes_ante_personal;
	@View
	private Radiogroup rdbCardiopatia_ante_personal;
	@View
	private Radiogroup rdbNefropatia_cronica;
	@View
	private Radiogroup rdbEnfermedad_autoinmune;
	@View
	private Radiogroup rdbEpilepsia;
	@View
	private Radiogroup rdbAsma;
	@View
	private Radiogroup rdbTranstorno_psiquiatrico;
	@View
	private Radiogroup rdbTranstorno_venoso_perisferico;
	@View
	private Radiogroup rdbSida_ante_personal;
	@View
	private Radiogroup rdbAnemia_cel_falciformes;
	@View
	private Radiogroup rdbOtras_anemias;
	@View
	private Textbox tbxCual_anemia;
	@View
	private Radiogroup rdbCirugia_ginecologivas;
	@View
	private Radiogroup rdbAlergico;
	@View
	private Radiogroup rdbAlfabeta;
	@View
	private Textbox tbxObservaciones_antecedentes_personal;
	@View
	private Radiogroup rdbTabaquismo_activo;
	@View
	private Radiogroup rdbTabaquismo_pasivo;
	@View
	private Radiogroup rdbConsumo_alcohol;
	@View
	private Radiogroup rdbConsumo_drogas;
	@View
	private Radiogroup rdbPromiscuidad;
	@View
	private Textbox tbxObservaciones_habitos;
	@View
	private Radiogroup rdbHipertension_arterial_familiar;
	@View
	private Radiogroup rdbDiabetes_ante_familiar;
	@View
	private Radiogroup rdbPreeclampsia_eclampsia;
	@View
	private Radiogroup rdbEmbarazo_gemelares;
	@View
	private Radiogroup rdbTuberculosis;
	@View
	private Radiogroup rdbVih_sida_ante_familiar;
	@View
	private Radiogroup rdbEnfermedad_congenita;
	@View
	private Radiogroup rdbCardiopatia_ante_familiar;
	@View
	private Textbox tbxObservaciones_ante_familiar;
	@View
	private Datebox dtbxFecha_antitetanica_previa_inmu_cito;
	@View
	private Datebox dtbxFecha_cito_previa_inmu_cito;
	@View
	private Doublebox dbxPresion;
	@View
	private Doublebox dbxPresion2;
	@View
	private Doublebox dbxCardiaca;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxRespiratoria;
	@View
	private Radiogroup rdbConciencia;
	@View
	private Radiogroup rdbHidratacion;
	@View
	private Radiogroup rdbCondicion_general;
	@View
	private Radiogroup rdbCabeza_cara;
	@View
	private Radiogroup rdbOrgarnos_sentidos;
	@View
	private Radiogroup rdbBoca;
	@View
	private Radiogroup rdbCuello;
	@View
	private Radiogroup rdbTorax_cardiopulmonar;
	@View
	private Radiogroup rdbMamas;
	@View
	private Radiogroup rdbAbdomen;
	@View
	private Radiogroup rdbSoporte_familiar2;
	@View
	private Radiogroup rdbSoporte_familiar3;
	@View
	private Radiogroup rdbGenito_urina;
	@View
	private Radiogroup rdbColumna_vertical;
	@View
	private Radiogroup rdbPiel_anexoa;
	@View
	private Radiogroup rdbExtremidades;
	@View
	private Radiogroup rdbNeurologico;
	@View
	private Textbox tbxObservaciones_examen_fisico_general;
	@View
	private Radiogroup rdbDef_alto_riesgo;
	@View
	private Textbox tbxCual_riesgo_obstetricio;

	@View
	private Radiogroup rdbEdad_biopsicosocial;
	@View
	private Radiogroup rdbParidad_biopsicosocial;
	@View
	private Checkbox chbAbortadora_habitual_biopsicosocial;
	@View
	private Checkbox chbRetencion_placentaria_biopsicosocial;
	@View
	private Checkbox chbRecien_nacido_4000_biopsicosocial;
	@View
	private Checkbox chbRecien_nacido_2500_biopsicosocial;
	@View
	private Checkbox chbHipertension_arterial_inducida_embarazo_biopsicosocial;
	@View
	private Checkbox chbEmbarazo_gemelar_biopsicosocial;
	@View
	private Checkbox chbMortinato_biopsicosocial;
	@View
	private Checkbox chbParto_dificil_biopsicosocial;
	@View
	private Checkbox chbCirugia_ginecologia_ectopico_biopsicosocial;
	@View
	private Checkbox chbEnfermedad_renal_cronica_biopsicosocial;
	@View
	private Checkbox chbDiabetes_gestacional_biopsicosocial;
	@View
	private Checkbox chbDiabetes_mellitus_biopsicosocial;
	@View
	private Checkbox chbEnfermedad_cardiaca_biopsicosocial;
	@View
	private Checkbox chbEnfermedad_infecciosa_aguda_biopsicosocial;
	@View
	private Checkbox chbEnfermedad_autoinmune_biopsicosocial;
	@View
	private Checkbox chbAnemia_biopsicosocial;
	@View
	private Intbox tbxSubtotal_i_ii_biopsicosocial;
	@View
	private Checkbox chbHemorragia_vaginal_mayor_biopsicosocial;
	@View
	private Checkbox chbHemorragia_vaginal_menor_biopsicosocial;
	@View
	private Checkbox chbEmbarazo_prolongado_biopsicosocial;
	@View
	private Checkbox chbHipertension_arterial_biopsicosocial;
	@View
	private Checkbox chbRuptura_membrana_prematura_biopsicosocial;
	@View
	private Checkbox chbPolihidramnios_biopsicosocial;
	@View
	private Checkbox chbRetardo_crec_intaute_recien_nacido_biopsicosocial;
	@View
	private Checkbox chbEmbarazos_multiples_biopsicosocial;
	@View
	private Checkbox chbMala_presentacion_biopsicosocial;
	@View
	private Checkbox chbIsoinmunizacion_rh_biopsicosocial;
	@View
	private Intbox tbxSubtotal_emabarzo_actual_biopsicosocial;
	@View
	private Radiogroup rdbTension_emocianal_riesgo_psicosocial;
	@View
	private Radiogroup rdbHumor_depresivo_riesgo_psicosocial;
	@View
	private Radiogroup rdbSintoma_neurovegetariano_riesgo_psicosocial;
	@View
	private Radiogroup rdbSoporte_familiar_riesgo_psicosocial;
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;
	@View
	private Intbox ibxTotalRiesgoBiopsicosocial;
	@View
	private Textbox txtTotalRiesgoBiopsicosocialAlerta;

	@View
	private Textbox tbxAnalisis;
	@View
	private Textbox tbxRecomendaciones_farmacol;

	@View
	private Checkbox chbHemogram;
	@View
	private Checkbox chbHemoclasificacio;
	@View
	private Checkbox chbV_drl;
	@View
	private Checkbox chbPrueba_hbs_ag;
	@View
	private Checkbox chbPrueba_elisa_vih;
	@View
	private Checkbox chbUrocultiv;
	@View
	private Checkbox chbGlicemia_ayuna;
	@View
	private Checkbox chbPtog_p;
	@View
	private Checkbox chbFrotis_gr_vagina;
	@View
	private Checkbox chbEcografia_obste;
	@View
	private Label lbTotalRiesgoBiopsicosocial;
	@View
	private Label lbObservaciones_ecografias;
	@View
	private Row rowCita_anterior;
	@View
	private Row rowCita_anterior2;
	@View
	private Row rowCita_anterior3;

	@View
	private Row rowGineco_ostetricos;
	@View
	private Row rowEmbarazos_anteriores;
	@View
	private Row rowAntecedentes_personales;
	@View
	private Row rowHabitos;
	@View
	private Row rowAntecedentes_familiares;
	@View
	private Row rowInmunizaciones_citologias;
	@View
	private Row rowFum;
	@View
	private Textbox tbxCa_Recomendaciones_farmacol;
	@View
	private Textbox tbxCa_ecografia;
	@View
	private Checkbox chbCa_Gineco_obste;
	@View
	private Checkbox chbCa_Nutricion;
	@View
	private Checkbox chbCa_Odontologia;
	@View
	private Checkbox chbCa_Psicologia;

	@View
	private Checkbox chbCa_Vacunacion;
	@View
	private Checkbox chbCa_Hemogram;
	@View
	private Checkbox chbCa_Hemoclasificacio;
	@View
	private Checkbox chbCa_V_drl;
	@View
	private Checkbox chbCa_Prueba_hbs_ag;
	@View
	private Checkbox chbCa_Prueba_elisa_vih;
	@View
	private Checkbox chbCa_Urocultiv;
	@View
	private Checkbox chbCa_Glicemia_ayuna;
	@View
	private Checkbox chbCa_Ptog_p;
	@View
	private Checkbox chbCa_Frotis_gr_vagina;
	@View
	private Checkbox chbCa_Ecografia_obste;

	@View
	private Toolbarbutton btnCancelar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Cell cellIMC1;
	@View
	private Cell cellIMC2;
	@View
	private Cell cellPesoInicial2;
	@View
	private Cell cellPesoInicial1;
	@View
	private Doublebox dbxPesoInicial;

	@View
	private Textbox tbxObservaciones_ecografias;
	@View
	private Div divModuloOrdenamiento;
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divCitologia;
	@View
	private Div divTamizaje;
	@View
	private Toolbarbutton btGuardar;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	@View
	private Div divModuloRemisiones_externas;

	@View
	private Listbox lbxTamizaje_cuello;

	@View
	private Doublebox dbxTemperatura;

	@View
	private Doublebox dbxFc_fetal;

	@View
	private Listbox lbxSemana_gestacion;

	@View
	private Intbox ibxAltura_uterina;

	@View
	private Radiogroup rdbCitologias;
	@View
	private Textbox txtRes_citologia;

	@View
	private Button btnGraficarPresion;

	private Remisiones_externasAction remisiones_externasAction;

	private Integer edad;
	private boolean cobrar_agudeza;

	private String nro_ingreso_admision;

	private ZKWindow zkwindow;

	@View
	private Toolbarbutton btnImprimir;

	@View
	private Listbox lbxFormato;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "lbxFinalidad_consulta",
			"manejadorParaclinicos", "divModuloRemisiones_externas" };

	// private InputStream uterina;
	// private InputStream incremento_materno;
	// private InputStream diastolica;
	/* Observaciones */
	@View
	private Textbox tbxObservaciones_cabeza;
	@View
	private Textbox tbxObservaciones_sentidos;
	@View
	private Textbox tbxObservaciones_conciencia;
	@View
	private Textbox tbxObservaciones_cuello;
	@View
	private Textbox tbxObservaciones_torax;
	@View
	private Textbox tbxObservaciones_general;
	@View
	private Textbox tbxObservaciones_hidratacion;
	@View
	private Textbox tbxObservaciones_abdomen;
	@View
	private Textbox tbxObservaciones_masas;
	@View
	private Textbox tbxObservaciones_genito;
	@View
	private Textbox tbxObservaciones_columna;
	@View
	private Textbox tbxObservaciones_extremidades;
	@View
	private Textbox tbxObservaciones_piel;
	@View
	private Textbox tbxObservaciones_neurologico;
	@View
	private Textbox tbxObservaciones_boca;

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

	@Override
	public void init() {
		try {
			this.zkwindow = this;
			listarCombos();
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);
			crearResultadosParaclinicosObstetrica();

			FormularioUtil.inicializarRadiogroupsDefecto(this);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void desaparecerBotonn() {
		btGuardar.setVisible(false);
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			edad = Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(admision.getPaciente()
					.getFecha_nacimiento()), "1", false));
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
		}
	}

	public void alarmaExamenFisicoFr() {
		if (edad >= 8 && edad <= 15) {
			UtilidadSignosVitales.validarMaxMin(dbxRespiratoria, 21d, 17d,
					"Anormal", "Anormal", false);
		} else {
			UtilidadSignosVitales.validarMaxMin(dbxRespiratoria, 21d, 15d,
					"Anormal", "Anormal", false);
		}
	}

	public void alarmaExamenFisicoFc() {
		if (edad >= 8 && edad <= 15) {
			UtilidadSignosVitales.validarMaxMin(dbxCardiaca, 87d, 79d,
					"Anormal", "Anormal", false);
		} else {
			UtilidadSignosVitales.validarMaxMin(dbxCardiaca, 81d, 59d,
					"Anormal", "Anormal", false);
		}
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxTamizaje_cuello, true,
				getServiceLocator());
		lbxTamizaje_cuello.setSelectedIndex(6);
		listarSemanasgestacion();
	}

	public void listarSemanasgestacion() {
		lbxSemana_gestacion.getChildren().clear();
		lbxSemana_gestacion.appendChild(new Listitem("-- Seleccione --", ""));
		for (int i = 1; i <= 42; i++) {
			lbxSemana_gestacion.appendChild(new Listitem("" + i, "" + i));
		}
		lbxSemana_gestacion.setSelectedIndex(0);
	}

	public void listarElementoListboxFromType(Listbox listbox, boolean select,
			List<Elemento> elementos, boolean selectEnd) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			if (!selectEnd) {
				listbox.setSelectedIndex(0);
			} else {
				listbox.setSelectedIndex(listbox.getChildren().size() - 1);
			}
		}
	}

	public void mostrarSifilisGestacional() {
		if (rdbSifilis_gestacional.getSelectedItem().getValue().equals("S")) {
			gbxSifilisGestacional.setVisible(true);
		} else {
			gbxSifilisGestacional.setVisible(false);
			tbxObservaciones_sifilis_gestacional.setText("");
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

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	// METODO BUSCAR DX
	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
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
			// System.Out.Println("Excepcion loaded");
			e.printStackTrace();
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
		FormularioUtil.cargarRadiogroupsDefecto(this);
		dtbxFecha_ultima_mestruacion.setText("");
		dtbxFecha_ultimo_parto.setText("");
		dtbxFecha_ultimo_parto.setDisabled(true);

		if (admision != null) {
			this.nro_ingreso_admision = admision.getNro_ingreso();
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
			calcularFechaEsperadaParto();
			FormularioUtil.deshabilitarComponentes(rowCita_anterior, true,
					new String[] {});
			FormularioUtil.deshabilitarComponentes(rowCita_anterior2, true,
					new String[] {});
			FormularioUtil.deshabilitarComponentes(rowCita_anterior3, true,
					new String[] {});
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
		// tbxNro_ingreso.setDisabled(false);
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		// mostrarAlertaTension('1', dbxPresion.getValue());
		manejadorParaclinicos.limpiarResultados_paraclinicos();
		manejadorValoracion_obstetrica.limpiarResultados_paraclinicos();
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

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		String mensaje = "Los campos marcados con (*) son obligatorios";

		boolean valida = receta_ripsAction.validarFormExterno();

		infoPacientes.validarInformacionPaciente();

		if (valida) {
			FormularioUtil.validarCamposObligatorios(tbxMotivo_consulta,
					tbxGestion_actual, dbxPresion, dbxPresion2, dbxTemperatura,
					dbxCardiaca, dbxRespiratoria, dbxTalla, dbxPeso,
					dbxPesoInicial, tbxAnalisis, tbxRecomendaciones_farmacol,
					ibxAltura_uterina);
		}

		String mensaje_error = "Los campos marcados con (*) son obligatorios";

		if (valida) {
			mensaje = manejadorParaclinicos.validarResultados_paraclinicos();
			if (!mensaje.isEmpty()) {
				valida = false;
				mensaje_error = mensaje;
				MensajesUtil.mensajeAlerta(usuarios.getNombres()
						+ " recuerde que...", mensaje_error);
			}
		}

		if (valida) {
			mensaje = manejadorValoracion_obstetrica
					.validarResultados_paraclinicos();
			if (!mensaje.isEmpty()) {
				valida = false;
				mensaje_error = mensaje;
				MensajesUtil.mensajeAlerta(usuarios.getNombres()
						+ " recuerde que...", mensaje_error);
			}
		}

		if (valida) {
			valida = remisiones_externasAction.validarRemision();
		}

		// String mensaje = "";
		if (valida) {
			valida = orden_servicioAction.validarFormExterno();

		}

		return valida;
	}

	public void seleccion(Listbox listbox, int entero,
			Component[] abstractComponents) {
		try {
			// System.Out.Println("" + listbox.getSelectedItem().getValue());

			String num = entero + "";
			for (Component abstractComponent : abstractComponents) {

				if (listbox.getSelectedItem().getValue().equals(num)) {
					abstractComponent.setVisible(true);
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}
					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			// System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
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

			List<Hisc_deteccion_alt_embarazo> lista_datos = getServiceLocator()
					.getHisc_deteccion_alt_embarazoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						hisc_deteccion_alt_embarazo, this));
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

		final Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = (Hisc_deteccion_alt_embarazo) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_deteccion_alt_embarazo
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_deteccion_alt_embarazo
				.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				hisc_deteccion_alt_embarazo.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_deteccion_alt_embarazo
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
						mostrarDatos(hisc_deteccion_alt_embarazo);
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
													eliminarDatos(hisc_deteccion_alt_embarazo);
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

	public Hisc_deteccion_alt_embarazo getBean() {
		Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = new Hisc_deteccion_alt_embarazo();
		hisc_deteccion_alt_embarazo.setFecha_inicial(new Timestamp(
				infoPacientes.getFecha_inicial().getTime()));
		hisc_deteccion_alt_embarazo.setUltimo_update(new Timestamp((new Date())
				.getTime()));

		hisc_deteccion_alt_embarazo.setCodigo_empresa(empresa
				.getCodigo_empresa());
		hisc_deteccion_alt_embarazo.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		hisc_deteccion_alt_embarazo.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_deteccion_alt_embarazo
				.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
		hisc_deteccion_alt_embarazo.setNro_ingreso(admision.getNro_ingreso());

		hisc_deteccion_alt_embarazo.setMotivo_consulta(tbxMotivo_consulta
				.getValue());
		hisc_deteccion_alt_embarazo.setGestion_actual(tbxGestion_actual
				.getValue());
		hisc_deteccion_alt_embarazo.setCefalea_rev_sis(rdbCefalea_rev_sis
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setTranstornos_visuales_rev_siste(rdbTranstornos_visuales_rev_siste
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setTranstornos_auditivos_rev_sistema(rdbTranstornos_auditivos_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setEpigastralgia_rev_sistema(rdbEpigastralgia_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setEdema_cara_rev_sistema(rdbEdema_cara_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setEdema_manos_rev_sistema(rdbEdema_manos_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setEdema_msls_rev_sistema(rdbEdema_msls_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setAusencia_mov_fetal_rev_sistema(rdbAusencia_mov_fetal_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setSangrado_genitales_rev_sistema(rdbSangrado_genitales_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setAmniorrea_rev_sistema(rdbAmniorrea_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setLeucorrea_rev_sistema(rdbLeucorrea_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setSint_urinarios_rev_sistema(rdbSint_urinarios_rev_sistema
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setObservacion_revision_sistema(tbxObservacion_revision_sistema
						.getValue());

		hisc_deteccion_alt_embarazo.setMenarquia_anios(ibxMenarquia_anios
				.getValue() + "");
		hisc_deteccion_alt_embarazo.setRecuerda_fum(rdbRecuerda_fum
				.getSelectedItem().getValue().toString());

		if (dtbxFecha_ultima_mestruacion.getValue() != null) {
			hisc_deteccion_alt_embarazo
					.setFecha_ultima_mestruacion(new Timestamp(
							dtbxFecha_ultima_mestruacion.getValue().getTime()));
		}

		hisc_deteccion_alt_embarazo
				.setFecha_ultima_mestrua_confiable(rdbFecha_ultima_mestrua_confiable
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setAbortadora_habitual(rdbAbortadora_habitual
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setHis_infertibilidad(rdbHis_infertibilidad
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setNum_embarazo_g(""
				+ ibxNum_embarazo_g.getValue());
		hisc_deteccion_alt_embarazo.setNum_partos_p(""
				+ ibxNum_partos_p.getValue());
		hisc_deteccion_alt_embarazo.setNum_cesarias_c(""
				+ ibxNum_cesarias_c.getValue());
		hisc_deteccion_alt_embarazo.setNum_aborto_a(""
				+ ibxNum_aborto_a.getValue());
		hisc_deteccion_alt_embarazo.setNum_mortinato_v(""
				+ ibxNum_mortinato_v.getValue());
		hisc_deteccion_alt_embarazo.setCiclo_mestrual(rdbCiclo_mestrual
				.getSelectedItem().getValue().toString());
		if (dtbxFecha_probable_parto_fpp.getValue() != null) {
			hisc_deteccion_alt_embarazo
					.setFecha_probable_parto_fpp(new Timestamp(
							dtbxFecha_probable_parto_fpp.getValue().getTime()));
		}
		hisc_deteccion_alt_embarazo
				.setAbortadora_habitual_biopsicosocial(chbAbortadora_habitual_biopsicosocial
						.isChecked());
		if (dtbxFecha_ultimo_parto.getValue() != null) {
			hisc_deteccion_alt_embarazo.setFecha_ultimo_parto(new Timestamp(
					dtbxFecha_ultimo_parto.getValue().getTime()));
		}
		hisc_deteccion_alt_embarazo
				.setUsaba_anticonceptivo(rdbUsaba_anticonceptivo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setFecha_posible_parto_duda(rdbFecha_posible_parto_duda
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setPadre_feto(rdbPadre_feto
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setAmenaza_aborto(rdbAmenaza_aborto
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setAmenaza_parto_pretermino(rdbAmenaza_parto_pretermino
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setRuptura_membrana_prematura(rdbRuptura_membrana_prematura
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setParto_pretermino(rdbParto_pretermino
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setRetardo_crec_intaute_recien_nacido(rdbRetardo_crec_intaute_recien_nacido
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setOligohidramos(rdbOligohidramos
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setGemelar(rdbGemelar.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo.setPolihidramnios(rdbPolihidramnios
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setRecien_nacido_macrosomico(rdbRecien_nacido_macrosomico
						.getSelectedItem().getValue().toString());

		hisc_deteccion_alt_embarazo
				.setEmbarazo_prolongado(rdbEmbarazo_prolongado
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setHipertension_preclamsia(rdbHipertension_preclamsia
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setDiabetes_gestacional(rdbDiabetes_gestacional
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setMortinato(rdbMortinato.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo.setMuerte_neonatal(rdbMuerte_neonatal
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setHospitalizacion_neonatal(rdbHospitalizacion_neonatal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setObservaciones_embarazos_anteriores(tbxObservaciones_embarazos_anteriores
						.getValue());
		hisc_deteccion_alt_embarazo
				.setHipertension_arterial(rdbHipertension_arterial
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setDiabetes_ante_personal(rdbDiabetes_ante_personal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setCardiopatia_ante_personal(rdbCardiopatia_ante_personal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setNefropatia_cronica(rdbNefropatia_cronica
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setEnfermedad_autoinmune(rdbEnfermedad_autoinmune
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setEpilepsia(rdbEpilepsia.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo.setAsma(rdbAsma.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo
				.setTranstorno_psiquiatrico(rdbTranstorno_psiquiatrico
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setTranstorno_venoso_perisferico(rdbTranstorno_venoso_perisferico
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setSida_ante_personal(rdbSida_ante_personal
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setAnemia_cel_falciformes(rdbAnemia_cel_falciformes
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setOtras_anemias(rdbOtras_anemias
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setCual_anemia(tbxCual_anemia.getValue());
		hisc_deteccion_alt_embarazo
				.setCirugia_ginecologivas(rdbCirugia_ginecologivas
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setAlergico(rdbAlergico.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo.setAlfabeta(rdbAlfabeta.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo
				.setObservaciones_antecedentes_personal(tbxObservaciones_antecedentes_personal
						.getValue());
		hisc_deteccion_alt_embarazo.setTabaquismo_activo(rdbTabaquismo_activo
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setTabaquismo_pasivo(rdbTabaquismo_pasivo
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setConsumo_alcohol(rdbConsumo_alcohol
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setConsumo_drogas(rdbConsumo_drogas
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setPromiscuidad(rdbPromiscuidad
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setObservaciones_habitos(tbxObservaciones_habitos.getValue());
		hisc_deteccion_alt_embarazo
				.setHipertension_arterial_familiar(rdbHipertension_arterial_familiar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setDiabetes_ante_familiar(rdbDiabetes_ante_familiar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setPreeclampsia_eclampsia(rdbPreeclampsia_eclampsia
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setEmbarazo_gemelares(rdbEmbarazo_gemelares
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setTuberculosis(rdbTuberculosis
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setVih_sida_ante_familiar(rdbVih_sida_ante_familiar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setEnfermedad_congenita(rdbEnfermedad_congenita
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setCardiopatia_ante_familiar(rdbCardiopatia_ante_familiar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setObservaciones_ante_familiar(tbxObservaciones_ante_familiar
						.getValue());
		if (dtbxFecha_antitetanica_previa_inmu_cito.getValue() != null) {
			hisc_deteccion_alt_embarazo
					.setFecha_antitetanica_previa_inmu_cito(new Timestamp(
							dtbxFecha_antitetanica_previa_inmu_cito.getValue()
									.getTime()));
		}
		if (dtbxFecha_cito_previa_inmu_cito.getValue() != null) {

			hisc_deteccion_alt_embarazo
					.setFecha_cito_previa_inmu_cito(new Timestamp(
							dtbxFecha_cito_previa_inmu_cito.getValue()
									.getTime()));
		}
		hisc_deteccion_alt_embarazo.setTension_arterial_fisico(dbxPresion
				.getValue() + "");

		hisc_deteccion_alt_embarazo
				.setSifilis_gestacional(rdbSifilis_gestacional
						.getSelectedItem().getValue().toString());

		if (hisc_deteccion_alt_embarazo.getSifilis_gestacional().equals("S")) {
			if (dtbxFecha_dosis1.getValue() != null) {
				hisc_deteccion_alt_embarazo.setFecha_dosis1(new Timestamp(
						dtbxFecha_dosis1.getValue().getTime()));
			}
			if (dtbxFecha_dosis2.getValue() != null) {
				hisc_deteccion_alt_embarazo.setFecha_dosis2(new Timestamp(
						dtbxFecha_dosis2.getValue().getTime()));
			}
			if (dtbxFecha_dosis3.getValue() != null) {
				hisc_deteccion_alt_embarazo.setFecha_dosis3(new Timestamp(
						dtbxFecha_dosis3.getValue().getTime()));
			}
		}
		hisc_deteccion_alt_embarazo
				.setObservaciones_sifilis_gestacional(tbxObservaciones_sifilis_gestacional
						.getValue());

		hisc_deteccion_alt_embarazo.setDiastolica(dbxPresion2.getValue() + "");

		hisc_deteccion_alt_embarazo.setConciencia(rdbConciencia
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setHidratacion(rdbHidratacion
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setCondicion_general(rdbCondicion_general
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setCabeza_cara(rdbCabeza_cara
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setOrgarnos_sentidos(rdbOrgarnos_sentidos
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setBoca(rdbBoca.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo.setCuello(rdbCuello.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo
				.setTorax_cardiopulmonar(rdbTorax_cardiopulmonar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setMamas(rdbMamas.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo.setAbdomen(rdbAbdomen.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_embarazo.setGenito_urina(rdbGenito_urina
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setColumna_vertical(rdbColumna_vertical
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setPiel_anexoa(rdbPiel_anexoa
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setExtremidades(rdbExtremidades
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setNeurologico(rdbNeurologico
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setObservaciones_examen_fisico_general(tbxObservaciones_examen_fisico_general
						.getValue());
		hisc_deteccion_alt_embarazo
				.setDefinicion_alto_riesgo_obstetricio(rdbDef_alto_riesgo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setCual_riesgo_obstetricio(tbxCual_riesgo_obstetricio
						.getValue());
		hisc_deteccion_alt_embarazo
				.setEdad_biopsicosocial(rdbEdad_biopsicosocial
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setParidad_biopsicosocial(rdbParidad_biopsicosocial
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setRetencion_placentaria_biopsicosocial(chbRetencion_placentaria_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setRecien_nacido_4000_biopsicosocial(chbRecien_nacido_4000_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setRecien_nacido_2500_biopsicosocial(chbRecien_nacido_2500_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setHipertension_arterial_inducida_embarazo_biopsicosocial(chbHipertension_arterial_inducida_embarazo_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setEmbarazo_gemelar_biopsicosocial(chbEmbarazo_gemelar_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setMortinato_biopsicosocial(chbMortinato_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setParto_dificil_biopsicosocial(chbParto_dificil_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setCirugia_ginecologia_ectopico_biopsicosocial(chbCirugia_ginecologia_ectopico_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setEnfermedad_renal_cronica_biopsicosocial(chbEnfermedad_renal_cronica_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setDiabetes_gestacional_biopsicosocial(chbDiabetes_gestacional_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setDiabetes_mellitus_biopsicosocial(chbDiabetes_mellitus_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setEnfermedad_cardiaca_biopsicosocial(chbEnfermedad_cardiaca_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setEnfermedad_infecciosa_aguda_biopsicosocial(chbEnfermedad_infecciosa_aguda_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setEnfermedad_autoinmune_biopsicosocial(chbEnfermedad_autoinmune_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setAnemia_biopsicosocial(chbAnemia_biopsicosocial.isChecked());
		hisc_deteccion_alt_embarazo
				.setSubtotal_i_ii_biopsicosocial(tbxSubtotal_i_ii_biopsicosocial
						.getText());
		hisc_deteccion_alt_embarazo
				.setHemorragia_vaginal_mayor_biopsicosocial(chbHemorragia_vaginal_mayor_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setHemorragia_vaginal_menor_biopsicosocial(chbHemorragia_vaginal_menor_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setEmbarazo_prolongado_biopsicosocial(chbEmbarazo_prolongado_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setHipertension_arterial_biopsicosocial(chbHipertension_arterial_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setRuptura_membrana_prematura_biopsicosocial(chbRuptura_membrana_prematura_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setPolihidramnios_biopsicosocial(chbPolihidramnios_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setRetardo_crec_intaute_recien_nacido_biopsicosocial(chbRetardo_crec_intaute_recien_nacido_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setEmbarazos_multiples_biopsicosocial(chbEmbarazos_multiples_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setMala_presentacion_biopsicosocial(chbMala_presentacion_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setIsoinmunizacion_rh_biopsicosocial(chbIsoinmunizacion_rh_biopsicosocial
						.isChecked());
		hisc_deteccion_alt_embarazo
				.setSubtotal_emabarzo_actual_biopsicosocial(tbxSubtotal_emabarzo_actual_biopsicosocial
						.getText());
		hisc_deteccion_alt_embarazo
				.setTension_emocianal_riesgo_psicosocial(rdbTension_emocianal_riesgo_psicosocial
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setHumor_depresivo_riesgo_psicosocial(rdbHumor_depresivo_riesgo_psicosocial
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setSintoma_neurovegetariano_riesgo_psicosocial(rdbSintoma_neurovegetariano_riesgo_psicosocial
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setCitologias(rdbCitologias
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setSoporte_familiar_riesgo_psicosocial(rdbSoporte_familiar_riesgo_psicosocial
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setSintomaticos_piel(rdbSintomaticos_piel
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setSintomaticos_respiratorio(rdbSintomaticos_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo
				.setSubtotal_item_intenso_riesgo_psicosocial(ibxTotalRiesgoBiopsicosocial
						.getText());

		hisc_deteccion_alt_embarazo
				.setRes_citologia(txtRes_citologia.getText());

		hisc_deteccion_alt_embarazo
				.setObservaciones_ecografias(tbxObservaciones_ecografias
						.getValue());
		hisc_deteccion_alt_embarazo.setAnalisis(tbxAnalisis.getValue());
		hisc_deteccion_alt_embarazo
				.setRecomendaciones_farmacol(tbxRecomendaciones_farmacol
						.getValue());

		hisc_deteccion_alt_embarazo.setHemogram(chbHemogram.isChecked());
		hisc_deteccion_alt_embarazo.setHemoclasificacio(chbHemoclasificacio
				.isChecked());
		hisc_deteccion_alt_embarazo.setV_drl(chbV_drl.isChecked());
		hisc_deteccion_alt_embarazo.setPrueba_hbs_ag(chbPrueba_hbs_ag
				.isChecked());
		hisc_deteccion_alt_embarazo.setPrueba_elisa_vih(chbPrueba_elisa_vih
				.isChecked());
		hisc_deteccion_alt_embarazo.setUrocultiv(chbUrocultiv.isChecked());
		hisc_deteccion_alt_embarazo.setGlicemia_ayuna(chbGlicemia_ayuna
				.isChecked());
		hisc_deteccion_alt_embarazo.setPtog_p(chbPtog_p.isChecked());
		hisc_deteccion_alt_embarazo.setFrotis_gr_vagina(chbFrotis_gr_vagina
				.isChecked());
		hisc_deteccion_alt_embarazo.setEcografia_obste(chbEcografia_obste
				.isChecked());
		hisc_deteccion_alt_embarazo.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_embarazo.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_embarazo.setCreacion_user(usuarios.getCodigo()
				.toString());

		hisc_deteccion_alt_embarazo.setDelete_date(null);
		hisc_deteccion_alt_embarazo.setUltimo_user(usuarios.getCodigo()
				.toString());

		hisc_deteccion_alt_embarazo.setNumero_semana(lbxSemana_gestacion
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setSoporte_familiar2(rdbSoporte_familiar2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_embarazo.setSoporte_familiar3(rdbSoporte_familiar3
				.getSelectedItem().getValue().toString());

		hisc_deteccion_alt_embarazo
				.setPeso_inicial((dbxPesoInicial.getValue() != null ? dbxPesoInicial
						.getValue().toString() : "0.0"));

		hisc_deteccion_alt_embarazo.setTipo_historia(tipo_historia);
		hisc_deteccion_alt_embarazo
				.setCodigo_historia_anterior(codigo_historia_anterior);
		hisc_deteccion_alt_embarazo.setTamizaje_cuello(lbxTamizaje_cuello
				.getSelectedItem().getValue().toString());

		hisc_deteccion_alt_embarazo.setTemperatura(ConvertidorDatosUtil
				.convertirDato(dbxTemperatura.getValue()));
		hisc_deteccion_alt_embarazo
				.setFrecuencia_cardiaca_fetal(ConvertidorDatosUtil
						.convertirDato(dbxFc_fetal.getValue()));
		hisc_deteccion_alt_embarazo
				.setFrec_cardiaca_fisico(ConvertidorDatosUtil
						.convertirDato(dbxCardiaca.getValue()));
		hisc_deteccion_alt_embarazo
				.setFrec_respiratoria_fisica(ConvertidorDatosUtil
						.convertirDato(dbxRespiratoria.getValue()));
		hisc_deteccion_alt_embarazo.setTalla_examen_fisico(ConvertidorDatosUtil
				.convertirDato(dbxTalla.getValue()));
		hisc_deteccion_alt_embarazo.setPeso(ConvertidorDatosUtil
				.convertirDato(dbxPeso.getValue()));
		hisc_deteccion_alt_embarazo.setImc(ConvertidorDatosUtil
				.convertirDato(dbxImc.getValue()));
		hisc_deteccion_alt_embarazo.setAltura_uterina(ibxAltura_uterina
				.getValue());

		hisc_deteccion_alt_embarazo
				.setObservaciones_cabeza(tbxObservaciones_cabeza.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_sentidos(tbxObservaciones_sentidos.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_conciencia(tbxObservaciones_conciencia
						.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_cuello(tbxObservaciones_cuello.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_torax(tbxObservaciones_torax.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_general(tbxObservaciones_general.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_hidratacion(tbxObservaciones_hidratacion
						.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_abdomen(tbxObservaciones_abdomen.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_masas(tbxObservaciones_masas.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_genito(tbxObservaciones_genito.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_columna(tbxObservaciones_columna.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_extremidades(tbxObservaciones_extremidades
						.getValue());
		hisc_deteccion_alt_embarazo.setObservaciones_piel(tbxObservaciones_piel
				.getValue());
		hisc_deteccion_alt_embarazo
				.setObservaciones_neurologico(tbxObservaciones_neurologico
						.getValue());
		hisc_deteccion_alt_embarazo.setObservaciones_boca(tbxObservaciones_boca
				.getValue());

		return hisc_deteccion_alt_embarazo;

	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				if (onGraficarAlturaUterina(false)
						&& onGraficarIncrementoMaterno(false)
						&& onGraficarPresionDiastolica(false)) {
					FormularioUtil.setUpperCase(groupboxEditar);

					Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = getBean();

					Map<String, Object> datos = new HashMap<String, Object>();
					datos.put("historia_clinica", hisc_deteccion_alt_embarazo);
					datos.put("admision", admision);
					datos.put("listado_paraclinicos", manejadorParaclinicos
							.obtenerResultados_paraclinicos());
					datos.put("listado_valoracion_obstetrica",
							manejadorValoracion_obstetrica
									.obtenerResultados_paraclinicos());
					datos.put("accion", tbxAccion.getValue());
					datos.put("anio_periodo",
							ConvertidorDatosUtil.convertirDato(anio));
					datos.put("cobrar_agudeza", cobrar_agudeza);

					// hay que actualualizar los diagnosticos en la receta antes
					// de
					// obtener el objeto receta
					Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
							.obtenerImpresionDiagnostica();

					receta_ripsAction
							.actualizarDiagnosticos(impresion_diagnostica);

					Map<String, Object> mapReceta = receta_ripsAction
							.obtenerDatos();
					Map<String, Object> mapProcedimientos = orden_servicioAction
							.obtenerDatos();
					datos.put("receta_medica", mapReceta);
					datos.put("orden_servicio", mapProcedimientos);

					Remision_interna remision_interna = macroRemision_interna
							.obtenerRemisionInterna();
					datos.put("remision_interna", remision_interna);

					Agudeza_visual agudeza_visual = macroAgudeza_visual
							.obtenerAgudezaVisual();
					agudeza_visual.setAnio(anio);
					datos.put("agudeza_visual", agudeza_visual);
					datos.put("cita_seleccionada", cita);
					datos.put("punto_altura_uterina", punto_altura_uterina);
					datos.put("punto_incremento_materno",
							punto_incremento_materno);
					datos.put("punto_presion_diastolica",
							punto_presion_diastolica);

					datos.put("impresion_diagnostica", impresion_diagnostica);
					Anexo9_entidad anexo9_entidad = remisiones_externasAction
							.obtenerAnexo9();
					datos.put("anexo9", anexo9_entidad);
					datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());

					getServiceLocator().getHisc_deteccion_alt_embarazoService()
							.guardarDatos(datos);

					if (anexo9_entidad != null) {
						remisiones_externasAction
								.setCodigo_remision(anexo9_entidad.getCodigo());
						remisiones_externasAction.getBotonImprimir()
								.setDisabled(false);
					}
					mostrarDatosAutorizacion(datos);
					tbxAccion.setValue("modificar");
					infoPacientes
							.setCodigo_historia(hisc_deteccion_alt_embarazo
									.getCodigo_historia());
					Receta_rips receta_rips = (Receta_rips) mapReceta
							.get("receta_rips");
					if (receta_rips != null) {
						receta_ripsAction.mostrarDatos(receta_rips, false);
					}
					// hay que llamar este metodo para validar que salga el
					// boton para imprimir despues de guardar la receta
					receta_ripsAction.validarParaImpresion();

					Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
							.get("orden_servicio");
					if (orden_servicio != null) {
						orden_servicioAction.mostrarDatos(orden_servicio);
					}
					// hay que llamar este metodo para validar que salga el
					// boton para imprimir despues de guardar la orden
					orden_servicioAction.validarParaImpresion();

					actualizarAutorizaciones(admision,
							impresion_diagnostica.getCausas_externas(),
							impresion_diagnostica.getCie_principal(),
							impresion_diagnostica.getCie_relacionado1(),
							impresion_diagnostica.getCie_relacionado2(), this);
					actualizarRemision(admision, getInformacionClinica(), this);
					manejadorParaclinicos.limpiarResultados_paraclinicos();
					manejadorParaclinicos.cargarHistorial_resultados();
					manejadorValoracion_obstetrica
							.limpiarResultados_paraclinicos();
					manejadorValoracion_obstetrica.cargarHistorial_resultados();

					Messagebox.show(
							"Los datos se guardaron satisfactoriamente",
							"Informacion ..", Messagebox.OK,
							Messagebox.INFORMATION);

					btnImprimir.setVisible(true);
				}
			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = (Hisc_deteccion_alt_embarazo) obj;
		try {
			this.nro_ingreso_admision = hisc_deteccion_alt_embarazo
					.getNro_ingreso();

			infoPacientes.setCodigo_historia(hisc_deteccion_alt_embarazo
					.getCodigo_historia());

			infoPacientes.setFecha_inicio(hisc_deteccion_alt_embarazo
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_deteccion_alt_embarazo.getUltimo_update());

			onMostrarModuloRemisiones();
			initMostrar_datos(hisc_deteccion_alt_embarazo);

			cargarRemisionInterna(hisc_deteccion_alt_embarazo);
			cargarInformacion_paciente();
			cargarAgudezaVisual(hisc_deteccion_alt_embarazo);

			tbxMotivo_consulta.setValue(hisc_deteccion_alt_embarazo
					.getMotivo_consulta());
			tbxGestion_actual.setValue(hisc_deteccion_alt_embarazo
					.getGestion_actual());

			Radio radio = (Radio) rdbCefalea_rev_sis
					.getFellow("radioCefalea_rev_sis"
							+ hisc_deteccion_alt_embarazo.getCefalea_rev_sis());
			radio.setChecked(true);
			Radio radio1 = (Radio) rdbTranstornos_visuales_rev_siste
					.getFellow("radioTranstornos_visuales_rev_siste"
							+ hisc_deteccion_alt_embarazo
									.getTranstornos_visuales_rev_siste());
			radio1.setChecked(true);
			Radio radio2 = (Radio) rdbTranstornos_auditivos_rev_sistema
					.getFellow("radioTranstornos_auditivos_rev_sistema"
							+ hisc_deteccion_alt_embarazo
									.getTranstornos_auditivos_rev_sistema());
			radio2.setChecked(true);
			Radio radio3 = (Radio) rdbEpigastralgia_rev_sistema
					.getFellow("radio_Epigastralgia"
							+ hisc_deteccion_alt_embarazo
									.getEpigastralgia_rev_sistema());
			radio3.setChecked(true);
			Radio radio4 = (Radio) rdbEdema_cara_rev_sistema
					.getFellow("radioEdema_cara_rev_sistema"
							+ hisc_deteccion_alt_embarazo
									.getEdema_cara_rev_sistema());
			radio4.setChecked(true);
			Radio radio5 = (Radio) rdbEdema_manos_rev_sistema
					.getFellow("radio_Edema_manos"
							+ hisc_deteccion_alt_embarazo
									.getEdema_manos_rev_sistema());
			radio5.setChecked(true);
			Radio radio6 = (Radio) rdbEdema_msls_rev_sistema
					.getFellow("radio_Edema_msls"
							+ hisc_deteccion_alt_embarazo
									.getEdema_msls_rev_sistema());
			radio6.setChecked(true);
			Radio radio7 = (Radio) rdbAusencia_mov_fetal_rev_sistema
					.getFellow("radio_Ausencia_mov"
							+ hisc_deteccion_alt_embarazo
									.getAusencia_mov_fetal_rev_sistema());
			radio7.setChecked(true);
			Radio radio8 = (Radio) rdbSangrado_genitales_rev_sistema
					.getFellow("radio_Sangrado_genitales"
							+ hisc_deteccion_alt_embarazo
									.getSangrado_genitales_rev_sistema());
			radio8.setChecked(true);
			Radio radio9 = (Radio) rdbAmniorrea_rev_sistema
					.getFellow("radio_Amniorrea_rev"
							+ hisc_deteccion_alt_embarazo
									.getAmniorrea_rev_sistema());
			radio9.setChecked(true);
			Radio radio10 = (Radio) rdbLeucorrea_rev_sistema
					.getFellow("radioLeucorrea_rev"
							+ hisc_deteccion_alt_embarazo
									.getLeucorrea_rev_sistema());
			radio10.setChecked(true);
			Radio radio11 = (Radio) rdbSint_urinarios_rev_sistema
					.getFellow("radio_Sint_urinarios"
							+ hisc_deteccion_alt_embarazo
									.getSint_urinarios_rev_sistema());
			radio11.setChecked(true);
			tbxObservacion_revision_sistema
					.setValue(hisc_deteccion_alt_embarazo
							.getObservacion_revision_sistema());

			ibxMenarquia_anios.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_embarazo
							.getMenarquia_anios()));

			dtbxFecha_ultima_mestruacion.setValue(hisc_deteccion_alt_embarazo
					.getFecha_ultima_mestruacion());
			Radio radio12 = (Radio) rdbFecha_ultima_mestrua_confiable
					.getFellow("radio_Fecha_ultima"
							+ hisc_deteccion_alt_embarazo
									.getFecha_ultima_mestrua_confiable());
			radio12.setChecked(true);

			Radio radio100 = (Radio) rdbAbortadora_habitual
					.getFellow("radio_abortadora_habitual"
							+ hisc_deteccion_alt_embarazo
									.getAbortadora_habitual());
			radio100.setChecked(true);

			Radio radio13 = (Radio) rdbHis_infertibilidad
					.getFellow("radio_His_infertibilidad"
							+ hisc_deteccion_alt_embarazo
									.getHis_infertibilidad());
			radio13.setChecked(true);
			ibxNum_embarazo_g.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_embarazo
							.getNum_embarazo_g()));
			ibxNum_partos_p.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_embarazo
							.getNum_partos_p()));
			ibxNum_cesarias_c.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_embarazo
							.getNum_cesarias_c()));
			ibxNum_aborto_a.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_embarazo
							.getNum_aborto_a()));
			ibxNum_mortinato_v.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_embarazo
							.getNum_mortinato_v()));
			Radio radio14 = (Radio) rdbCiclo_mestrual
					.getFellow("radio_Ciclo_mestrual"
							+ hisc_deteccion_alt_embarazo.getCiclo_mestrual());
			radio14.setChecked(true);
			dtbxFecha_probable_parto_fpp.setValue(hisc_deteccion_alt_embarazo
					.getFecha_probable_parto_fpp());
			dtbxFecha_ultimo_parto.setValue(hisc_deteccion_alt_embarazo
					.getFecha_ultimo_parto());
			Radio radio16 = (Radio) rdbUsaba_anticonceptivo
					.getFellow("radio_usaba_anticonceptivo"
							+ hisc_deteccion_alt_embarazo
									.getUsaba_anticonceptivo());
			radio16.setChecked(true);
			Radio radio17 = (Radio) rdbFecha_posible_parto_duda
					.getFellow("radi_fecha_posible_parto_duda"
							+ hisc_deteccion_alt_embarazo
									.getFecha_posible_parto_duda());
			radio17.setChecked(true);
			Radio radio18 = (Radio) rdbPadre_feto.getFellow("radio_padre_feto"
					+ hisc_deteccion_alt_embarazo.getPadre_feto());
			radio18.setChecked(true);
			Radio radio19 = (Radio) rdbAmenaza_aborto
					.getFellow("radio_amenaza_aborto"
							+ hisc_deteccion_alt_embarazo.getAmenaza_aborto());
			radio19.setChecked(true);
			Radio radio20 = (Radio) rdbAmenaza_parto_pretermino
					.getFellow("radio_amenaza_parto_pretermino"
							+ hisc_deteccion_alt_embarazo
									.getAmenaza_parto_pretermino());
			radio20.setChecked(true);
			Radio radio21 = (Radio) rdbRuptura_membrana_prematura
					.getFellow("radio_uptura_membrana_prematura"
							+ hisc_deteccion_alt_embarazo
									.getRuptura_membrana_prematura());
			radio21.setChecked(true);
			Radio radio22 = (Radio) rdbParto_pretermino
					.getFellow("radio_parto_pretermino"
							+ hisc_deteccion_alt_embarazo.getParto_pretermino());
			radio22.setChecked(true);
			Radio radio23 = (Radio) rdbRetardo_crec_intaute_recien_nacido
					.getFellow("radio_retardo_crec_intaute_recien_nacido"
							+ hisc_deteccion_alt_embarazo
									.getRetardo_crec_intaute_recien_nacido());
			radio23.setChecked(true);
			Radio radio24 = (Radio) rdbOligohidramos
					.getFellow("radio_oligohidramos"
							+ hisc_deteccion_alt_embarazo.getOligohidramos());
			radio24.setChecked(true);
			Radio radio25 = (Radio) rdbGemelar.getFellow("radio_gemelar"
					+ hisc_deteccion_alt_embarazo.getGemelar());
			radio25.setChecked(true);
			Radio radio26 = (Radio) rdbPolihidramnios
					.getFellow("radio_polihidramnios"
							+ hisc_deteccion_alt_embarazo.getPolihidramnios());
			radio26.setChecked(true);
			Radio radio28 = (Radio) rdbRecien_nacido_macrosomico
					.getFellow("radio_recien_nacido"
							+ hisc_deteccion_alt_embarazo
									.getRecien_nacido_macrosomico());
			radio28.setChecked(true);
			Radio radio29 = (Radio) rdbHipertension_preclamsia
					.getFellow("radio_Hipertension_preclamsia"
							+ hisc_deteccion_alt_embarazo
									.getHipertension_preclamsia());
			radio29.setChecked(true);
			Radio radio30 = (Radio) rdbDiabetes_gestacional
					.getFellow("radio_diabetes_gestacional"
							+ hisc_deteccion_alt_embarazo
									.getDiabetes_gestacional());
			radio30.setChecked(true);
			Radio radio31 = (Radio) rdbMortinato.getFellow("radio_mortinato"
					+ hisc_deteccion_alt_embarazo.getMortinato());
			radio31.setChecked(true);
			Radio radio32 = (Radio) rdbMuerte_neonatal
					.getFellow("radio_muerte_neonatal"
							+ hisc_deteccion_alt_embarazo.getMuerte_neonatal());
			radio32.setChecked(true);
			Radio radio33 = (Radio) rdbHospitalizacion_neonatal
					.getFellow("radio_hospitalizacion_neonatal"
							+ hisc_deteccion_alt_embarazo
									.getHospitalizacion_neonatal());
			radio33.setChecked(true);
			tbxObservaciones_embarazos_anteriores
					.setValue(hisc_deteccion_alt_embarazo
							.getObservaciones_embarazos_anteriores());

			Radio radio34 = (Radio) rdbHipertension_arterial
					.getFellow("radio_hipertension_arterial"
							+ hisc_deteccion_alt_embarazo
									.getHipertension_arterial());
			radio34.setChecked(true);

			Radio radio35 = (Radio) rdbDiabetes_ante_personal
					.getFellow("radio_Diabetes_ante_personal"
							+ hisc_deteccion_alt_embarazo
									.getDiabetes_ante_personal());
			radio35.setChecked(true);
			Radio radio36 = (Radio) rdbCardiopatia_ante_personal
					.getFellow("radio_cardiopatia_ante_personal"
							+ hisc_deteccion_alt_embarazo
									.getCardiopatia_ante_personal());
			radio36.setChecked(true);
			Radio radio37 = (Radio) rdbNefropatia_cronica
					.getFellow("radio_nefropatia_cronica"
							+ hisc_deteccion_alt_embarazo
									.getNefropatia_cronica());
			radio37.setChecked(true);
			Radio radio38 = (Radio) rdbEnfermedad_autoinmune
					.getFellow("radio_enfermedad_autoinmune"
							+ hisc_deteccion_alt_embarazo
									.getEnfermedad_autoinmune());
			radio38.setChecked(true);
			Radio radio39 = (Radio) rdbEpilepsia.getFellow("radio_epilepsia"
					+ hisc_deteccion_alt_embarazo.getEpilepsia());
			radio39.setChecked(true);
			Radio radio40 = (Radio) rdbAsma.getFellow("radio_asma"
					+ hisc_deteccion_alt_embarazo.getAsma());
			radio40.setChecked(true);
			Radio radio41 = (Radio) rdbTranstorno_psiquiatrico
					.getFellow("radio_transtorno_psiquiatrico"
							+ hisc_deteccion_alt_embarazo
									.getTranstorno_psiquiatrico());
			radio41.setChecked(true);
			Radio radio42 = (Radio) rdbTranstorno_venoso_perisferico
					.getFellow("radio_Transtorno_venoso"
							+ hisc_deteccion_alt_embarazo
									.getTranstorno_venoso_perisferico());
			radio42.setChecked(true);
			Radio radio43 = (Radio) rdbSida_ante_personal
					.getFellow("radio_ida_ante_personal"
							+ hisc_deteccion_alt_embarazo
									.getSida_ante_personal());
			radio43.setChecked(true);
			Radio radio44 = (Radio) rdbAnemia_cel_falciformes
					.getFellow("radio_anemia_cel_falciformes"
							+ hisc_deteccion_alt_embarazo
									.getAnemia_cel_falciformes());
			radio44.setChecked(true);
			Radio radio45 = (Radio) rdbOtras_anemias
					.getFellow("radio_otras_anemias"
							+ hisc_deteccion_alt_embarazo.getOtras_anemias());
			radio45.setChecked(true);
			Radio radio47 = (Radio) rdbCirugia_ginecologivas
					.getFellow("radio_cirugia_ginecologivas"
							+ hisc_deteccion_alt_embarazo
									.getCirugia_ginecologivas());
			radio47.setChecked(true);
			Radio radio48 = (Radio) rdbAlergico.getFellow("radio_alergico"
					+ hisc_deteccion_alt_embarazo.getAlergico());
			radio48.setChecked(true);
			Radio radio49 = (Radio) rdbAlfabeta.getFellow("radio_alfabeta"
					+ hisc_deteccion_alt_embarazo.getAlfabeta());
			radio49.setChecked(true);
			tbxObservaciones_antecedentes_personal
					.setValue(hisc_deteccion_alt_embarazo
							.getObservaciones_antecedentes_personal());
			tbxCual_anemia.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_antecedentes_personal());

			// aqui
			Radio radio50 = (Radio) rdbTabaquismo_activo
					.getFellow("radio_tabaquismo_activo"
							+ hisc_deteccion_alt_embarazo
									.getTabaquismo_activo());
			radio50.setChecked(true);
			Radio radio51 = (Radio) rdbTabaquismo_pasivo
					.getFellow("radio_tabaquismo_pasi"
							+ hisc_deteccion_alt_embarazo
									.getTabaquismo_pasivo());
			radio51.setChecked(true);
			Radio radio52 = (Radio) rdbConsumo_alcohol
					.getFellow("radio_consumo_alcohol"
							+ hisc_deteccion_alt_embarazo.getConsumo_alcohol());
			radio52.setChecked(true);
			Radio radio53 = (Radio) rdbConsumo_drogas
					.getFellow("radio_consumo_drogas"
							+ hisc_deteccion_alt_embarazo.getConsumo_drogas());
			radio53.setChecked(true);
			Radio radio54 = (Radio) rdbPromiscuidad
					.getFellow("radio_promiscuidad"
							+ hisc_deteccion_alt_embarazo.getPromiscuidad());
			radio54.setChecked(true);
			tbxObservaciones_habitos.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_antecedentes_personal());

			Radio radio56 = (Radio) rdbHipertension_arterial_familiar
					.getFellow("radio_hipertension_arterial_familiar"
							+ hisc_deteccion_alt_embarazo
									.getHipertension_arterial_familiar());
			radio56.setChecked(true);
			Radio radio57 = (Radio) rdbDiabetes_ante_familiar
					.getFellow("radio_diabetes_ante_familiar"
							+ hisc_deteccion_alt_embarazo
									.getDiabetes_ante_familiar());
			radio57.setChecked(true);
			Radio radio58 = (Radio) rdbPreeclampsia_eclampsia
					.getFellow("radio_preeclampsia_eclampsia"
							+ hisc_deteccion_alt_embarazo
									.getPreeclampsia_eclampsia());
			radio58.setChecked(true);
			Radio radio59 = (Radio) rdbEmbarazo_gemelares
					.getFellow("radio_embarazo_gemelares"
							+ hisc_deteccion_alt_embarazo
									.getEmbarazo_gemelares());
			radio59.setChecked(true);
			Radio radio60 = (Radio) rdbTuberculosis
					.getFellow("radio_tuberculosis"
							+ hisc_deteccion_alt_embarazo.getTuberculosis());
			radio60.setChecked(true);
			Radio radio61 = (Radio) rdbVih_sida_ante_familiar
					.getFellow("radio_vih_sida_ante_familiar"
							+ hisc_deteccion_alt_embarazo
									.getVih_sida_ante_familiar());
			radio61.setChecked(true);
			Radio radio62 = (Radio) rdbEnfermedad_congenita
					.getFellow("radio_Enfermedad_congenita"
							+ hisc_deteccion_alt_embarazo
									.getEnfermedad_congenita());
			radio62.setChecked(true);
			Radio radio63 = (Radio) rdbCardiopatia_ante_familiar
					.getFellow("radio_cardiopatia_ante_familiar"
							+ hisc_deteccion_alt_embarazo
									.getCardiopatia_ante_familiar());
			radio63.setChecked(true);
			tbxObservaciones_ante_familiar.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_ante_familiar());

			dtbxFecha_antitetanica_previa_inmu_cito
					.setValue(hisc_deteccion_alt_embarazo
							.getFecha_antitetanica_previa_inmu_cito());
			dtbxFecha_cito_previa_inmu_cito
					.setValue(hisc_deteccion_alt_embarazo
							.getFecha_cito_previa_inmu_cito());

			dbxPresion.setValue(new Double(hisc_deteccion_alt_embarazo
					.getTension_arterial_fisico()));
			dbxPresion2.setValue(new Double(hisc_deteccion_alt_embarazo
					.getDiastolica()));
			dbxPeso.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_embarazo.getPeso()));
			dbxCardiaca.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_embarazo
							.getFrec_cardiaca_fisico()));
			dbxRespiratoria.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_embarazo
							.getFrec_respiratoria_fisica()));
			dbxTalla.setValue(ConvertidorDatosUtil
					.convertirDato(hisc_deteccion_alt_embarazo
							.getTalla_examen_fisico()));
			dbxImc.setValue(ConvertidorDatosUtil
					.convertirDato((hisc_deteccion_alt_embarazo.getImc())));

			Radio radio64 = (Radio) rdbConciencia.getFellow("radio_conciencia"
					+ hisc_deteccion_alt_embarazo.getConciencia());
			radio64.setChecked(true);
			Radio radio65 = (Radio) rdbHidratacion
					.getFellow("radio_hidratacion"
							+ hisc_deteccion_alt_embarazo.getHidratacion());
			radio65.setChecked(true);
			Radio radio66 = (Radio) rdbCondicion_general
					.getFellow("radio_condicion_general"
							+ hisc_deteccion_alt_embarazo
									.getCondicion_general());
			radio66.setChecked(true);

			Utilidades.seleccionarRadio(rdbCabeza_cara,
					hisc_deteccion_alt_embarazo.getCabeza_cara());

			Utilidades.seleccionarRadio(rdbCitologias,
					hisc_deteccion_alt_embarazo.getCitologias());
			onCambiarCitologia();

			Utilidades.seleccionarRadio(rdbOrgarnos_sentidos,
					hisc_deteccion_alt_embarazo.getCabeza_cara());

			Utilidades.seleccionarRadio(rdbBoca,
					hisc_deteccion_alt_embarazo.getBoca());

			Utilidades.seleccionarRadio(rdbCuello,
					hisc_deteccion_alt_embarazo.getCuello());

			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar,
					hisc_deteccion_alt_embarazo.getTorax_cardiopulmonar());

			Utilidades.seleccionarRadio(rdbRecuerda_fum,
					hisc_deteccion_alt_embarazo.getRecuerda_fum());

			onRecuerdaFum();

			Radio radio67 = (Radio) rdbMamas.getFellow("radio_mamas"
					+ hisc_deteccion_alt_embarazo.getMamas());
			radio67.setChecked(true);
			Radio radio68 = (Radio) rdbAbdomen.getFellow("radio_abdomen"
					+ hisc_deteccion_alt_embarazo.getAbdomen());
			radio68.setChecked(true);
			Radio radio69 = (Radio) rdbGenito_urina
					.getFellow("radio_genito_urina"
							+ hisc_deteccion_alt_embarazo.getGenito_urina());
			radio69.setChecked(true);
			Radio radio70 = (Radio) rdbColumna_vertical
					.getFellow("radio_columna_vertical"
							+ hisc_deteccion_alt_embarazo.getColumna_vertical());
			radio70.setChecked(true);
			Radio radio71 = (Radio) rdbPiel_anexoa
					.getFellow("radio_piel_anexoa"
							+ hisc_deteccion_alt_embarazo.getPiel_anexoa());
			radio71.setChecked(true);
			Radio radio72 = (Radio) rdbExtremidades
					.getFellow("radio_extremidades"
							+ hisc_deteccion_alt_embarazo.getExtremidades());
			radio72.setChecked(true);
			Radio radio73 = (Radio) rdbNeurologico
					.getFellow("radio_neurologico"
							+ hisc_deteccion_alt_embarazo.getNeurologico());
			radio73.setChecked(true);
			tbxObservaciones_examen_fisico_general
					.setValue(hisc_deteccion_alt_embarazo
							.getObservaciones_examen_fisico_general());

			Utilidades.seleccionarRadio(rdbDef_alto_riesgo,
					hisc_deteccion_alt_embarazo
							.getDefinicion_alto_riesgo_obstetricio());

			tbxCual_riesgo_obstetricio.setValue(hisc_deteccion_alt_embarazo
					.getCual_riesgo_obstetricio());
			Radio radio79 = (Radio) rdbEdad_biopsicosocial
					.getFellow("radio_edad_biopsicosocial"
							+ hisc_deteccion_alt_embarazo.getNeurologico());
			radio79.setChecked(true);
			Radio radio80 = (Radio) rdbParidad_biopsicosocial
					.getFellow("radio_paridad_biopsicosocial"
							+ hisc_deteccion_alt_embarazo.getNeurologico());
			radio80.setChecked(true);
			chbAbortadora_habitual_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getAbortadora_habitual_biopsicosocial());
			chbRetencion_placentaria_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getRetencion_placentaria_biopsicosocial());
			chbRecien_nacido_4000_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getRecien_nacido_4000_biopsicosocial());
			chbRecien_nacido_2500_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getRecien_nacido_2500_biopsicosocial());
			chbHipertension_arterial_inducida_embarazo_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getHipertension_arterial_inducida_embarazo_biopsicosocial());
			chbEmbarazo_gemelar_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getEmbarazo_gemelar_biopsicosocial());
			chbMortinato_biopsicosocial.setChecked(hisc_deteccion_alt_embarazo
					.getMortinato_biopsicosocial());
			chbParto_dificil_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getParto_dificil_biopsicosocial());
			chbCirugia_ginecologia_ectopico_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getCirugia_ginecologia_ectopico_biopsicosocial());
			chbEnfermedad_renal_cronica_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getEnfermedad_renal_cronica_biopsicosocial());
			chbDiabetes_gestacional_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getDiabetes_gestacional_biopsicosocial());
			chbDiabetes_mellitus_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getDiabetes_mellitus_biopsicosocial());
			chbEnfermedad_cardiaca_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getEnfermedad_cardiaca_biopsicosocial());
			chbEnfermedad_infecciosa_aguda_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getEnfermedad_infecciosa_aguda_biopsicosocial());
			chbEnfermedad_autoinmune_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getEnfermedad_autoinmune_biopsicosocial());
			chbAnemia_biopsicosocial.setChecked(hisc_deteccion_alt_embarazo
					.getAnemia_biopsicosocial());
			tbxSubtotal_i_ii_biopsicosocial.setText(hisc_deteccion_alt_embarazo
					.getSubtotal_i_ii_biopsicosocial());
			chbHemorragia_vaginal_mayor_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getHemorragia_vaginal_mayor_biopsicosocial());
			chbHemorragia_vaginal_menor_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getHemorragia_vaginal_menor_biopsicosocial());
			chbEmbarazo_prolongado_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getEmbarazo_prolongado_biopsicosocial());
			chbHipertension_arterial_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getHipertension_arterial_biopsicosocial());
			chbRuptura_membrana_prematura_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getRuptura_membrana_prematura_biopsicosocial());
			chbPolihidramnios_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getPolihidramnios_biopsicosocial());
			chbRetardo_crec_intaute_recien_nacido_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getRetardo_crec_intaute_recien_nacido_biopsicosocial());
			chbEmbarazos_multiples_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getEmbarazos_multiples_biopsicosocial());
			chbMala_presentacion_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getMala_presentacion_biopsicosocial());
			chbIsoinmunizacion_rh_biopsicosocial
					.setChecked(hisc_deteccion_alt_embarazo
							.getIsoinmunizacion_rh_biopsicosocial());
			tbxSubtotal_emabarzo_actual_biopsicosocial
					.setText(hisc_deteccion_alt_embarazo
							.getSubtotal_emabarzo_actual_biopsicosocial());
			Radio radio75 = (Radio) rdbTension_emocianal_riesgo_psicosocial
					.getFellow("radio_tension_emocianal_riesgo_psicosocial"
							+ hisc_deteccion_alt_embarazo
									.getTension_emocianal_riesgo_psicosocial());
			radio75.setChecked(true);
			Radio radio76 = (Radio) rdbHumor_depresivo_riesgo_psicosocial
					.getFellow("radio_humor_depresivo_riesgo_psicosocial"
							+ hisc_deteccion_alt_embarazo
									.getHumor_depresivo_riesgo_psicosocial());
			radio76.setChecked(true);
			Radio radio77 = (Radio) rdbSintoma_neurovegetariano_riesgo_psicosocial
					.getFellow("radio_sintoma_neurovegetariano_riesgo_psicosocial"
							+ hisc_deteccion_alt_embarazo
									.getSintoma_neurovegetariano_riesgo_psicosocial());
			radio77.setChecked(true);
			Radio radio78 = (Radio) rdbSoporte_familiar_riesgo_psicosocial
					.getFellow("radio_soporte_familiar_riesgo_psicosocial"
							+ hisc_deteccion_alt_embarazo
									.getSoporte_familiar_riesgo_psicosocial());
			radio78.setChecked(true);

			Utilidades.seleccionarListitem(lbxSemana_gestacion,
					hisc_deteccion_alt_embarazo.getNumero_semana());
			btnGraficarPresion.setDisabled(lbxSemana_gestacion
					.getSelectedIndex() < 24);
			Radio radio91 = (Radio) rdbSoporte_familiar2
					.getFellow("radio_soporte_familiar2"
							+ hisc_deteccion_alt_embarazo
									.getSoporte_familiar2());
			radio91.setChecked(true);
			Radio radio92 = (Radio) rdbSoporte_familiar3
					.getFellow("radio_soporte_familiar3"
							+ hisc_deteccion_alt_embarazo
									.getSoporte_familiar3());
			radio92.setChecked(true);

			Radio radio93 = (Radio) rdbEmbarazo_prolongado
					.getFellow("radio_embarazo_prolongado"
							+ hisc_deteccion_alt_embarazo
									.getEmbarazo_prolongado());
			radio93.setChecked(true);

			Utilidades.seleccionarRadio(rdbSifilis_gestacional,
					hisc_deteccion_alt_embarazo.getSifilis_gestacional());
			dtbxFecha_dosis1.setValue(hisc_deteccion_alt_embarazo
					.getFecha_dosis1());
			dtbxFecha_dosis2.setValue(hisc_deteccion_alt_embarazo
					.getFecha_dosis2());
			dtbxFecha_dosis3.setValue(hisc_deteccion_alt_embarazo
					.getFecha_dosis3());
			tbxObservaciones_sifilis_gestacional
					.setValue(hisc_deteccion_alt_embarazo
							.getObservaciones_sifilis_gestacional());

			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					hisc_deteccion_alt_embarazo.getSintomaticos_piel());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					hisc_deteccion_alt_embarazo.getSintomaticos_respiratorio());

			ibxTotalRiesgoBiopsicosocial.setText(hisc_deteccion_alt_embarazo
					.getSubtotal_item_intenso_riesgo_psicosocial());
			tbxAnalisis.setValue(hisc_deteccion_alt_embarazo.getAnalisis());
			tbxRecomendaciones_farmacol.setValue(hisc_deteccion_alt_embarazo
					.getRecomendaciones_farmacol());

			chbHemogram.setChecked(hisc_deteccion_alt_embarazo.getHemogram());
			chbHemoclasificacio.setChecked(hisc_deteccion_alt_embarazo
					.getHemoclasificacio());
			chbV_drl.setChecked(hisc_deteccion_alt_embarazo.getV_drl());
			chbPrueba_hbs_ag.setChecked(hisc_deteccion_alt_embarazo
					.getPrueba_hbs_ag());
			chbPrueba_elisa_vih.setChecked(hisc_deteccion_alt_embarazo
					.getPrueba_elisa_vih());
			chbUrocultiv.setChecked(hisc_deteccion_alt_embarazo.getUrocultiv());
			chbGlicemia_ayuna.setChecked(hisc_deteccion_alt_embarazo
					.getGlicemia_ayuna());
			chbPtog_p.setChecked(hisc_deteccion_alt_embarazo.getPtog_p());
			chbFrotis_gr_vagina.setChecked(hisc_deteccion_alt_embarazo
					.getFrotis_gr_vagina());
			chbEcografia_obste.setChecked(hisc_deteccion_alt_embarazo
					.getEcografia_obste());

			tbxObservaciones_ecografias.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_ecografias());

			dbxPesoInicial.setText(hisc_deteccion_alt_embarazo
					.getPeso_inicial());

			dbxTemperatura
					.setText(hisc_deteccion_alt_embarazo.getTemperatura());
			dbxFc_fetal.setText(hisc_deteccion_alt_embarazo
					.getFrecuencia_cardiaca_fetal());
			ibxAltura_uterina.setValue(hisc_deteccion_alt_embarazo
					.getAltura_uterina());
			txtRes_citologia.setValue(hisc_deteccion_alt_embarazo
					.getRes_citologia());

			Utilidades.seleccionarListitem(lbxTamizaje_cuello,
					hisc_deteccion_alt_embarazo.getTamizaje_cuello());

			FormularioUtil.mostrarObservaciones(rdbConciencia, "1",
					tbxObservaciones_conciencia, row1);
			FormularioUtil.mostrarObservaciones(rdbHidratacion, "1",
					tbxObservaciones_hidratacion, row1);
			FormularioUtil.mostrarObservaciones(rdbCondicion_general, "1",
					tbxObservaciones_general, row1);
			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "1",
					tbxObservaciones_cabeza, row2);
			FormularioUtil.mostrarObservaciones(rdbOrgarnos_sentidos, "1",
					tbxObservaciones_sentidos, row2);
			FormularioUtil.mostrarObservaciones(rdbBoca, "1",
					tbxObservaciones_boca, row2);
			FormularioUtil.mostrarObservaciones(rdbCuello, "1",
					tbxObservaciones_cuello, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "1",
					tbxObservaciones_torax, row3);
			FormularioUtil.mostrarObservaciones(rdbMamas, "1",
					tbxObservaciones_masas, row3);
			FormularioUtil.mostrarObservaciones(rdbAbdomen, "1",
					tbxObservaciones_abdomen, row4);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertical, "1",
					tbxObservaciones_columna, row4);
			FormularioUtil.mostrarObservaciones(rdbGenito_urina, "1",
					tbxObservaciones_genito, row4);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "1",
					tbxObservaciones_extremidades, row5);
			FormularioUtil.mostrarObservaciones(rdbPiel_anexoa, "1",
					tbxObservaciones_piel, row5);
			FormularioUtil.mostrarObservaciones(rdbNeurologico, "1",
					tbxObservaciones_neurologico, row5);

			tbxObservaciones_cabeza.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_cabeza());
			tbxObservaciones_abdomen.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_abdomen());
			tbxObservaciones_neurologico.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_neurologico());

			tbxObservaciones_sentidos.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_sentidos());
			tbxObservaciones_masas.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_masas());

			tbxObservaciones_conciencia.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_conciencia());

			tbxObservaciones_cuello.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_cuello());
			tbxObservaciones_genito.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_genito());

			tbxObservaciones_torax.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_torax());
			tbxObservaciones_columna.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_columna());

			tbxObservaciones_hidratacion.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_hidratacion());
			tbxObservaciones_extremidades.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_extremidades());

			tbxObservaciones_general.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_general());
			tbxObservaciones_piel.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_piel());

			tbxObservaciones_boca.setValue(hisc_deteccion_alt_embarazo
					.getObservaciones_boca());

			manejadorParaclinicos.cargarHistorial_resultados();
			manejadorValoracion_obstetrica.cargarHistorial_resultados();

			calcularSubtotalesBiopsicosocial();
			verificarRiesgoObstetrico();
			// verificarIMc();

			tipo_historia = hisc_deteccion_alt_embarazo.getTipo_historia();

			// Mostramos la vista //
			cargarImpresionDiagnostica(hisc_deteccion_alt_embarazo);

			cargarAnexo9_remision(hisc_deteccion_alt_embarazo);

			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);

			inicializarVista(tipo_historia);
			btnImprimir.setVisible(true);

			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);

			FormularioUtil.deshabilitarComponentes(rowCita_anterior, true,
					new String[] {});
			FormularioUtil.deshabilitarComponentes(rowCita_anterior2, true,
					new String[] {});
			FormularioUtil.deshabilitarComponentes(rowCita_anterior3, true,
					new String[] {});

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = (Hisc_deteccion_alt_embarazo) obj;
		try {
			hisc_deteccion_alt_embarazo.setDelete_user(getUsuarios()
					.getCodigo());
			int result = getServiceLocator()
					.getHisc_deteccion_alt_embarazoService().eliminar(
							hisc_deteccion_alt_embarazo);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void calcularSubtotales(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(gridSubtotalesBiopsicosocial, subt);
			Integer valor_edad = 0;
			Integer valor_paridad = 0;
			Integer valor_emocional = 0;
			Integer valor_depresivo = 0;
			Integer valor_neurovegetativo = 0;
			Integer valor_familiar = 0;
			Integer valor_familiar2 = 0;
			Integer valor_familiar3 = 0;
			if (rdbEdad_biopsicosocial.getSelectedItem() != null) {
				valor_edad = new Integer(rdbEdad_biopsicosocial
						.getSelectedItem().getValue().toString());
			}
			if (rdbParidad_biopsicosocial.getSelectedItem() != null) {
				valor_paridad = new Integer(rdbParidad_biopsicosocial
						.getSelectedItem().getValue().toString());
			}
			if (rdbTension_emocianal_riesgo_psicosocial.getSelectedItem() != null) {
				valor_emocional = new Integer(
						rdbTension_emocianal_riesgo_psicosocial
								.getSelectedItem().getValue().toString());
			}
			if (rdbHumor_depresivo_riesgo_psicosocial.getSelectedItem() != null) {
				valor_depresivo = new Integer(
						rdbHumor_depresivo_riesgo_psicosocial.getSelectedItem()
								.getValue().toString());
			}
			if (rdbSintoma_neurovegetariano_riesgo_psicosocial
					.getSelectedItem() != null) {
				valor_neurovegetativo = new Integer(
						rdbSintoma_neurovegetariano_riesgo_psicosocial
								.getSelectedItem().getValue().toString());
			}
			if (rdbSoporte_familiar_riesgo_psicosocial.getSelectedItem() != null) {
				valor_familiar = new Integer(
						rdbSoporte_familiar_riesgo_psicosocial
								.getSelectedItem().getValue().toString());
			}
			if (rdbSoporte_familiar2.getSelectedItem() != null) {
				valor_familiar2 = new Integer(rdbSoporte_familiar2
						.getSelectedItem().getValue().toString());
			}

			if (rdbSoporte_familiar3.getSelectedItem() != null) {
				valor_familiar3 = new Integer(rdbSoporte_familiar3
						.getSelectedItem().getValue().toString());
			}

			subtotalNum1 = subtotalNum1 + valor_edad + valor_paridad
					+ valor_emocional + valor_depresivo + valor_neurovegetativo
					+ valor_familiar + valor_familiar2 + valor_familiar3;

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(gridSubtotalesBiopsicosocial, subt);
		}
		tbxSubtotal_i_ii_biopsicosocial.setText(subtotalNum1 + "");
		tbxSubtotal_emabarzo_actual_biopsicosocial.setText(subtotalNum2 + "");
		Integer total = subtotalNum1 + subtotalNum2;
		ibxTotalRiesgoBiopsicosocial.setText(total + "");
		verificarRiesgoObstetrico();
	}

	private void calcularSubtotales(Component component, String subt) {

		List<Component> listado = component.getChildren();
		for (Component component2 : listado) {
			if (component2 instanceof Checkbox) {
				if (!(component2 instanceof Radio)) {
					if (((Checkbox) component2).isChecked()) {
						String valor = ((Checkbox) component2).getValue();
						StringTokenizer stringTokenizer = new StringTokenizer(
								valor, "-");
						if (stringTokenizer.countTokens() == 2) {
							String subtotal = stringTokenizer.nextToken();
							String numero = stringTokenizer.nextToken();
							if (subt.contains(subtotal)
									&& subt.contains("SUB01")) {
								Integer numeroInt = new Integer(numero.trim());
								subtotalNum1 += numeroInt;
							} else if (subt.contains(subtotal)
									&& subt.contains("SUB02")) {
								Integer numeroInt = new Integer(numero.trim());
								subtotalNum2 += numeroInt;
							}
						}
					}
				}
			}
			if (!component2.getChildren().isEmpty()) {
				calcularSubtotales(component2, subt);
			}

		}
	}

	private void calcularSubtotalesBiopsicosocial() {
		calcularSubtotales(gridSubtotalesBiopsicosocial, "SUB01");
		calcularSubtotales(gridSubtotalesBiopsicosocial, "SUB02");
	}

	public void calcularFechaEsperadaParto() {
		if (dtbxFecha_ultima_mestruacion.getText().isEmpty()) {
			dtbxFecha_probable_parto_fpp.setText("");
		} else if (dtbxFecha_ultima_mestruacion.getValue() != null) {
			Calendar calendar_fur = Calendar.getInstance();
			calendar_fur.setTime(dtbxFecha_ultima_mestruacion.getValue());
			calendar_fur.set(Calendar.MONTH,
					calendar_fur.get(Calendar.MONTH) + 9);
			calendar_fur.set(Calendar.DAY_OF_YEAR,
					calendar_fur.get(Calendar.DAY_OF_YEAR) + 7);
			dtbxFecha_probable_parto_fpp.setValue(calendar_fur.getTime());
		} else {
			dtbxFecha_probable_parto_fpp.setText("");
		}
	}

	public void seleccionarSemanasFUM(boolean seleccionar_sem) {
		if (dtbxFecha_ultima_mestruacion.getText().isEmpty()) {

			if (!dtbxFecha_probable_parto_fpp.getText().isEmpty()) {
				Calendar calendar_fpp = Calendar.getInstance();
				calendar_fpp.setTime(dtbxFecha_probable_parto_fpp.getValue());
				Calendar calendar_hoy = Calendar.getInstance();
				calendar_hoy.setTime(new Date());
				int dif = 42 - calendar_fpp.get(Calendar.WEEK_OF_YEAR)
						+ calendar_hoy.get(Calendar.WEEK_OF_YEAR);
				if (dif > -1 && dif < 43) {
					lbxSemana_gestacion.setSelectedIndex(dif);
					btnGraficarPresion.setDisabled(dif < 24);
					if (seleccionar_sem) {
						onSeleccionarSemanaGestacion();
					}
				}
			} else {

				lbxSemana_gestacion.setSelectedIndex(0);
				if (seleccionar_sem) {
					onSeleccionarSemanaGestacion();
				}
			}
		} else if (dtbxFecha_ultima_mestruacion.getValue() != null) {

			Calendar calendar_fum = Calendar.getInstance();
			calendar_fum.setTime(dtbxFecha_ultima_mestruacion.getValue());

			Calendar calendar_hoy = Calendar.getInstance();
			calendar_hoy.setTime(new Date());
			int dif = calendar_hoy.get(Calendar.WEEK_OF_YEAR)
					- calendar_fum.get(Calendar.WEEK_OF_YEAR);
			if (dif > -1 && dif < 43) {
				lbxSemana_gestacion.setSelectedIndex(dif);
				btnGraficarPresion.setDisabled(dif < 24);
				if (seleccionar_sem) {
					onSeleccionarSemanaGestacion();
				}
			}
		}
	}

	public void verificarRiesgoObstetrico() {
		try {
			int total = 0;
			if (ibxTotalRiesgoBiopsicosocial.getValue() != null) {
				total = new java.text.DecimalFormat("##").parse(
						ibxTotalRiesgoBiopsicosocial.getValue().toString())
						.intValue();
				// log.info("" + total);
			}
			if (total >= 0 && total < 3) {
				String alerta = "Bajo Riesgo Biopsicosocial-Prenatal";
				ibxTotalRiesgoBiopsicosocial.setTooltiptext(alerta);
				ibxTotalRiesgoBiopsicosocial
						.setStyle("background-color:#66FF33");

				txtTotalRiesgoBiopsicosocialAlerta.setTooltiptext(alerta);
				txtTotalRiesgoBiopsicosocialAlerta.setText(alerta);
				txtTotalRiesgoBiopsicosocialAlerta
						.setStyle("background-color:#66FF33");
			} else if (total >= 3) {
				String alerta = "Alto Riesgo, Esta persona debe ser valorada por especialista";
				ibxTotalRiesgoBiopsicosocial.setTooltiptext(alerta);
				ibxTotalRiesgoBiopsicosocial
						.setStyle("background-color:#FF5252;color:white");

				txtTotalRiesgoBiopsicosocialAlerta.setTooltiptext(alerta);
				txtTotalRiesgoBiopsicosocialAlerta.setText(alerta);
				txtTotalRiesgoBiopsicosocialAlerta
						.setStyle("background-color:#FF5252;color:white");
			} else {
				String alerta = "ERROR";
				ibxTotalRiesgoBiopsicosocial.setText("");
				ibxTotalRiesgoBiopsicosocial.setTooltiptext(alerta);
				ibxTotalRiesgoBiopsicosocial
						.setStyle("background-color:red;color:white");

				txtTotalRiesgoBiopsicosocialAlerta.setTooltiptext(alerta);
				txtTotalRiesgoBiopsicosocialAlerta.setText(alerta);
				txtTotalRiesgoBiopsicosocialAlerta
						.setStyle("background-color:red;color:white");
			}

		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void crearResultadosParaclinicosObstetrica() {
		manejadorParaclinicos = new ManejadorParaclinicos(
				ManejadorParaclinicos.TIPO_PARACLINICO, vboxParaclinicos,
				"00002", admision.getNro_identificacion(), this);
		manejadorValoracion_obstetrica = new ManejadorParaclinicos(
				ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
				vboxValoracion_obstetrica, "00002",
				admision.getNro_identificacion(), this);
	}

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);

			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());
			macroAgudeza_visual.inicializarMacro(this, admision,
					IVias_ingreso.HC_DETECCION_ALT_EMBARAZO);

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = new Hisc_deteccion_alt_embarazo();
				hisc_deteccion_alt_embarazo.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_deteccion_alt_embarazo.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_deteccion_alt_embarazo.setIdentificacion(admision
						.getNro_identificacion());
				hisc_deteccion_alt_embarazo.setNro_ingreso(admision
						.getNro_ingreso());

				hisc_deteccion_alt_embarazo = getServiceLocator()
						.getHisc_deteccion_alt_embarazoService().consultar(
								hisc_deteccion_alt_embarazo);

				if (hisc_deteccion_alt_embarazo != null) {
					accionForm(OpcionesFormulario.MOSTRAR,
							hisc_deteccion_alt_embarazo);
					infoPacientes
							.setCodigo_historia(hisc_deteccion_alt_embarazo
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

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowCita_anterior.setVisible(false);
			rowGineco_ostetricos.setVisible(true);
			rowEmbarazos_anteriores.setVisible(true);
			rowAntecedentes_personales.setVisible(true);
			rowHabitos.setVisible(true);
			rowAntecedentes_familiares.setVisible(true);
			rowInmunizaciones_citologias.setVisible(true);
			cellPesoInicial1.setVisible(true);
			cellPesoInicial2.setVisible(true);
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowCita_anterior.setVisible(true);
			rowCita_anterior2.setVisible(true);
			rowCita_anterior3.setVisible(true);
			rowGineco_ostetricos.setVisible(true);
			rowEmbarazos_anteriores.setVisible(false);
			rowAntecedentes_personales.setVisible(false);
			rowHabitos.setVisible(false);
			rowAntecedentes_familiares.setVisible(false);
			rowInmunizaciones_citologias.setVisible(false);
			cellPesoInicial1.setVisible(false);
			cellPesoInicial2.setVisible(false);
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {
						manejadorParaclinicos.cargarHistorial_resultados();
						if (tbxAccion.getValue().equalsIgnoreCase(
								OpcionesFormulario.REGISTRAR.toString())) {
							Calendar fecha_hist = Calendar.getInstance();

							fecha_hist.set(Calendar.MONTH,
									fecha_hist.get(Calendar.MONTH) - 9);
							fecha_hist.set(Calendar.DAY_OF_YEAR,
									fecha_hist.get(Calendar.DAY_OF_YEAR) - 7);

							Boolean previas_sios = UtilidadesSios
									.pacienteTieneRegistroDespuesDe(
											zkwindow.getServiceLocator()
													.getServicio(
															Hisc_historialSiosService.class),
											empresa,
											admision.getNro_identificacion(),
											IVias_ingreso.HC_DETECCION_ALT_EMBARAZO,
											true,
											new Timestamp(fecha_hist
													.getTimeInMillis()));

							if (previas_sios) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica de control/evolucion");
								admision.setPrimera_vez("N");
								cellPesoInicial1.setVisible(true);
								cellPesoInicial2.setVisible(true);
								rowCita_anterior.setVisible(false);
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
									cellPesoInicial1.setVisible(true);
									cellPesoInicial2.setVisible(true);
									rowCita_anterior.setVisible(false);
								} else {
									List<Hisc_deteccion_alt_embarazo> listado_historias = getServiceLocator()
											.getHisc_deteccion_alt_embarazoService()
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
							// log.info(">>>>>>>>"+edad);
							if (edad != null) {
								if (edad < 16) {
									rdbEdad_biopsicosocial.setSelectedIndex(0);
								} else if (edad >= 16 && edad <= 35) {
									rdbEdad_biopsicosocial.setSelectedIndex(1);
								} else {
									rdbEdad_biopsicosocial.setSelectedIndex(2);
								}
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Hisc_deteccion_alt_embarazo hisc_det = new Hisc_deteccion_alt_embarazo();
								hisc_det.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc_det.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc_det.setCodigo_historia(codigo_historia_anterior);

								if (codigo_historia_anterior != null) {
									hisc_det = getServiceLocator()
											.getHisc_deteccion_alt_embarazoService()
											.consultar(hisc_det);
									if (hisc_det != null) {
										cargarInformacion_historia_anterior(hisc_det);
									}
								}

							}
						}

					}
				});

	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
		Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = (Hisc_deteccion_alt_embarazo) historia_anterior;
		tbxCa_Recomendaciones_farmacol.setValue(hisc_deteccion_alt_embarazo
				.getRecomendaciones_farmacol());
		tbxCa_ecografia.setValue(hisc_deteccion_alt_embarazo
				.getObservaciones_ecografias());
		chbCa_Gineco_obste.setChecked(hisc_deteccion_alt_embarazo
				.getGineco_obst());
		chbCa_Nutricion.setChecked(hisc_deteccion_alt_embarazo.getNutricion());
		chbCa_Odontologia.setChecked(hisc_deteccion_alt_embarazo
				.getOdontologia());
		chbCa_Psicologia
				.setChecked(hisc_deteccion_alt_embarazo.getPsicologia());
		chbCa_Vacunacion
				.setChecked(hisc_deteccion_alt_embarazo.getVacunacion());
		chbCa_Hemogram.setChecked(hisc_deteccion_alt_embarazo.getHemogram());
		chbCa_Hemoclasificacio.setChecked(hisc_deteccion_alt_embarazo
				.getHemoclasificacio());
		chbCa_V_drl.setChecked(hisc_deteccion_alt_embarazo.getV_drl());
		chbCa_Prueba_hbs_ag.setChecked(hisc_deteccion_alt_embarazo
				.getPrueba_hbs_ag());
		chbCa_Prueba_elisa_vih.setChecked(hisc_deteccion_alt_embarazo
				.getPrueba_elisa_vih());
		chbCa_Urocultiv.setChecked(hisc_deteccion_alt_embarazo.getUrocultiv());
		chbCa_Glicemia_ayuna.setChecked(hisc_deteccion_alt_embarazo
				.getGlicemia_ayuna());
		chbCa_Ptog_p.setChecked(hisc_deteccion_alt_embarazo.getPtog_p());
		chbCa_Frotis_gr_vagina.setChecked(hisc_deteccion_alt_embarazo
				.getFrotis_gr_vagina());
		chbCa_Ecografia_obste.setChecked(hisc_deteccion_alt_embarazo
				.getEcografia_obste());
		codigo_historia_anterior = hisc_deteccion_alt_embarazo
				.getCodigo_historia();
		dbxPesoInicial.setText(hisc_deteccion_alt_embarazo.getPeso_inicial());
		dtbxFecha_ultima_mestruacion.setValue(hisc_deteccion_alt_embarazo
				.getFecha_ultima_mestruacion());
		// dtbxFecha_ultima_mestruacion.setDisabled(true);

		ibxNum_aborto_a.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_deteccion_alt_embarazo.getNum_aborto_a()));
		ibxNum_cesarias_c
				.setValue(ConvertidorDatosUtil
						.convertirDatot(hisc_deteccion_alt_embarazo
								.getNum_cesarias_c()));
		ibxNum_embarazo_g
				.setValue(ConvertidorDatosUtil
						.convertirDatot(hisc_deteccion_alt_embarazo
								.getNum_embarazo_g()));
		ibxNum_mortinato_v.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_deteccion_alt_embarazo
						.getNum_mortinato_v()));
		ibxNum_partos_p.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_deteccion_alt_embarazo.getNum_partos_p()));
		seleccionarSemanasFUM(false);
		onSeleccionarSemanaGestacion();
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo = (Hisc_deteccion_alt_embarazo) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					idsExcluyentes);
			if (hisc_deteccion_alt_embarazo.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			if (hisc_deteccion_alt_embarazo.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		}

		codigo_historia_anterior = hisc_deteccion_alt_embarazo
				.getCodigo_historia_anterior();
		tipo_historia = hisc_deteccion_alt_embarazo.getTipo_historia();
	}

	public void calcularIMC(Doublebox dbxPeso, Doublebox dbxTalla,
			Doublebox dbxImc) {
		try {
			UtilidadSignosVitales.calcularIMC(dbxPeso, dbxTalla, dbxImc,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales.onCalcularTensionEmbarazo(TA_sistolica,
					TA_diastolica);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void alertaTemperatura(Doublebox dbxTemperatura) {
		UtilidadSignosVitales
				.onMostrarAlertaTemperaturaEmbarazo(dbxTemperatura);
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
			Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);

		impresion_diagnostica.setCodigo_historia(hisc_deteccion_alt_embarazo
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	private void cargarAnexo9_remision(
			Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_deteccion_alt_embarazo
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
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
		remisiones_externasAction.setNro_historia(hisc_deteccion_alt_embarazo
				.getCodigo_historia());
	}

	public void onSeleccionarSemanaGestacion() {
		if (lbxSemana_gestacion.getSelectedIndex() != 0) {
			Datebox datebox_semana = manejadorValoracion_obstetrica
					.getDateboxFecha("00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_SEMANA_GESTACION);
			// log.info(">>>>>>>>>>>" + infoPacientes.getFecha_inicial());
			datebox_semana.setValue(infoPacientes.getFecha_inicial());
			Textbox textbox_semana = manejadorValoracion_obstetrica
					.getTextboxFecha("00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_SEMANA_GESTACION);
			textbox_semana.setValue(lbxSemana_gestacion.getSelectedItem()
					.getValue().toString());
		}
		mostrarSemanaEmbarazo();
	}

	public void onChangeAlturaUterina() {

		if (ibxAltura_uterina.getValue() != null) {
			Datebox datebox_altura = manejadorValoracion_obstetrica
					.getDateboxFecha("00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_ALTURA_UTERINA);
			// log.info(">>>>>>>>>>>" + infoPacientes.getFecha_inicial());
			datebox_altura.setValue(infoPacientes.getFecha_inicial());
			Textbox textbox_altura = manejadorValoracion_obstetrica
					.getTextboxFecha("00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_ALTURA_UTERINA);
			textbox_altura.setValue(ibxAltura_uterina.getText());
		}
	}

	public void onChangeTensionArterial() {
		if (dbxPresion.getValue() != null && dbxPresion2.getValue() != null) {
			Datebox datebox_tension = manejadorValoracion_obstetrica
					.getDateboxFecha("00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_TENSION_ARTERIAL);
			datebox_tension.setValue(infoPacientes.getFecha_inicial());
			Textbox textbox_tension = manejadorValoracion_obstetrica
					.getTextboxFecha("00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_TENSION_ARTERIAL);
			textbox_tension.setValue(dbxPresion.getText() + " / "
					+ dbxPresion2.getText());
		}
	}

	public void onChangeFrecuenciaCardiacaFetal() {
		if (dbxFc_fetal.getValue() != null) {
			Datebox datebox_frecuencia = manejadorValoracion_obstetrica
					.getDateboxFecha(
							"00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_FRECUENCIA_CARDIACA_FETAL);
			datebox_frecuencia.setValue(infoPacientes.getFecha_inicial());
			Textbox textbox_frecuencia = manejadorValoracion_obstetrica
					.getTextboxFecha(
							"00002",
							ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
							ManejadorParaclinicos.EXAMEN_FRECUENCIA_CARDIACA_FETAL);
			textbox_frecuencia.setValue(dbxFc_fetal.getText());
		}
	}

	public void onChangeIncrementoPeso() {
		if (dbxPesoInicial.getValue() != null && dbxPeso.getValue() != null) {
			Double peso_inicial = dbxPesoInicial.getValue();
			Double peso_actual = dbxPeso.getValue();

			if (peso_inicial != null && peso_actual != null) {

				Double variacion_peso = peso_actual - peso_inicial;

				Datebox datebox_peso = manejadorValoracion_obstetrica
						.getDateboxFecha(
								"00002",
								ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
								ManejadorParaclinicos.EXAMEN_INCREMENTO_PESO);
				datebox_peso.setValue(infoPacientes.getFecha_inicial());
				Textbox textbox_peso = manejadorValoracion_obstetrica
						.getTextboxFecha(
								"00002",
								ManejadorParaclinicos.TIPO_VALORACION_OBSTETRICA,
								ManejadorParaclinicos.EXAMEN_INCREMENTO_PESO);
				textbox_peso.setValue(variacion_peso.intValue() + "");
			}
		}

	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null) {
			remisiones_externasAction.detach();
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

		parametros.put("opcion_formulario_historia",
				opciones_via_ingreso.toString());

		remisiones_externasAction = (Remisiones_externasAction) Executions
				.createComponents("/pages/remisiones_externas.zul", null,
						parametros);
		remisiones_externasAction.inicializar(this);
		divModuloRemisiones_externas.appendChild(remisiones_externasAction);
	}

	public boolean onGraficarAlturaUterina(boolean mostrar_grafica) {
		try {
			List<Presultados_paraclinicos> lista_resultados = manejadorValoracion_obstetrica
					.obtenerResultados_paraclinicos();
			Presultados_paraclinicos semana_gestacion = null;
			Presultados_paraclinicos altura_uterina = null;

			for (Presultados_paraclinicos presultados_paraclinicos : lista_resultados) {
				if (presultados_paraclinicos.getCodigo_examen().equals(
						ManejadorParaclinicos.EXAMEN_ALTURA_UTERINA)) {
					altura_uterina = presultados_paraclinicos;
				}
				if (presultados_paraclinicos.getCodigo_examen().equals(
						ManejadorParaclinicos.EXAMEN_SEMANA_GESTACION)) {
					semana_gestacion = presultados_paraclinicos;
				}
			}

			// log.info("0- "+altura_uterina+" - "+semana_gestacion);
			if (altura_uterina != null && semana_gestacion != null) {
				int altura = ConvertidorDatosUtil.convertirDatot(altura_uterina
						.getResultado().trim());
				int semana = ConvertidorDatosUtil
						.convertirDatot(semana_gestacion.getResultado().trim());

				if (semana >= 13 && altura >= 9) {
					punto_altura_uterina = new Puntos_graficas_embarazo();
					punto_altura_uterina.setCodigo_empresa(codigo_empresa);
					punto_altura_uterina.setCodigo_sucursal(codigo_sucursal);
					punto_altura_uterina.setNro_identificacion(admision
							.getNro_identificacion());
					punto_altura_uterina.setPunto_x(semana);
					punto_altura_uterina.setPunto_y(altura);
					punto_altura_uterina.setTipo_grafica("1");

					if (mostrar_grafica) {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("nro_identificacion",
								admision.getNro_identificacion());
						parametros.put("tipo_grafica", "1");

						List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
								.getServicio(
										Puntos_graficas_embarazoService.class)
								.listar(parametros);
						listado_puntos.add(punto_altura_uterina);

						GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
								.createComponents(
										"/WEB-INF/macros/graficasEmbarazo.zul",
										this, null);

						ventana_graficas.setRegion_xi(91);
						ventana_graficas.setRegion_xf(750);
						ventana_graficas.setRegion_yi(543);
						ventana_graficas.setRegion_yf(23);
						ventana_graficas.setUnidades_x(23);
						ventana_graficas.setUnidades_y(17);
						ventana_graficas
								.setImagen_fondo("/images/embarazo/altura_uterina.png");
						ventana_graficas.setIncremento_x(2);
						ventana_graficas.setIncremento_y(2);
						ventana_graficas.setCantidad_inicio_x(11);
						ventana_graficas.setCantidad_inicio_y(5);
						ventana_graficas.inicializar(true);
						ventana_graficas.dibujarPuntos(listado_puntos);
						ventana_graficas.dibujarLineasSeguimiento();

					} else {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("nro_identificacion",
								admision.getNro_identificacion());
						parametros.put("tipo_grafica", "1");

						List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
								.getServicio(
										Puntos_graficas_embarazoService.class)
								.listar(parametros);
						GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
								.createComponents(
										"/WEB-INF/macros/graficasEmbarazo.zul",
										this, null);

						ventana_graficas.setRegion_xi(91);
						ventana_graficas.setRegion_xf(750);
						ventana_graficas.setRegion_yi(543);
						ventana_graficas.setRegion_yf(23);
						ventana_graficas.setUnidades_x(23);
						ventana_graficas.setUnidades_y(17);
						ventana_graficas
								.setImagen_fondo("/images/embarazo/altura_uterina.png");
						ventana_graficas.setIncremento_x(2);
						ventana_graficas.setIncremento_y(2);
						ventana_graficas.setCantidad_inicio_x(11);
						ventana_graficas.setCantidad_inicio_y(5);
						ventana_graficas.inicializar(true);
						ventana_graficas.dibujarPuntos(listado_puntos);
						ventana_graficas.dibujarLineasSeguimiento();

						ventana_graficas.detach();
						// uterina = ventana_graficas.getInputStreamGrafica();
						// log.info("1--- "+uterina);
					}
				}
			} else if (altura_uterina == null && mostrar_grafica
					&& !lista_resultados.isEmpty()) {
				ibxAltura_uterina.setFocus(true);
				Clients.showNotification(
						"Debe diligenciar la altura uterina para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, ibxAltura_uterina,
						"end_after", 3000, true);
				return false;
			} else if (semana_gestacion == null && mostrar_grafica
					&& !lista_resultados.isEmpty()) {
				lbxSemana_gestacion.setFocus(true);
				Clients.showNotification(
						"Debe seleccionar las semanas de gestacion para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, lbxSemana_gestacion,
						"end_after", 3000, true);
				return false;
			} else {
				if (mostrar_grafica) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa", codigo_empresa);
					parametros.put("codigo_sucursal", codigo_sucursal);
					parametros.put("nro_identificacion",
							admision.getNro_identificacion());
					parametros.put("tipo_grafica", "1");

					List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
							.getServicio(Puntos_graficas_embarazoService.class)
							.listar(parametros);
					GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
							.createComponents(
									"/WEB-INF/macros/graficasEmbarazo.zul",
									this, null);

					ventana_graficas.setRegion_xi(91);
					ventana_graficas.setRegion_xf(750);
					ventana_graficas.setRegion_yi(543);
					ventana_graficas.setRegion_yf(23);
					ventana_graficas.setUnidades_x(23);
					ventana_graficas.setUnidades_y(17);
					ventana_graficas
							.setImagen_fondo("/images/embarazo/altura_uterina.png");
					ventana_graficas.setIncremento_x(2);
					ventana_graficas.setIncremento_y(2);
					ventana_graficas.setCantidad_inicio_x(11);
					ventana_graficas.setCantidad_inicio_y(5);
					ventana_graficas.inicializar(true);
					ventana_graficas.dibujarPuntos(listado_puntos);
					ventana_graficas.dibujarLineasSeguimiento();

				} else {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa", codigo_empresa);
					parametros.put("codigo_sucursal", codigo_sucursal);
					parametros.put("nro_identificacion",
							admision.getNro_identificacion());
					parametros.put("tipo_grafica", "1");

					List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
							.getServicio(Puntos_graficas_embarazoService.class)
							.listar(parametros);
					GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
							.createComponents(
									"/WEB-INF/macros/graficasEmbarazo.zul",
									this, null);

					ventana_graficas.setRegion_xi(91);
					ventana_graficas.setRegion_xf(750);
					ventana_graficas.setRegion_yi(543);
					ventana_graficas.setRegion_yf(23);
					ventana_graficas.setUnidades_x(23);
					ventana_graficas.setUnidades_y(17);
					ventana_graficas
							.setImagen_fondo("/images/embarazo/altura_uterina.png");
					ventana_graficas.setIncremento_x(2);
					ventana_graficas.setIncremento_y(2);
					ventana_graficas.setCantidad_inicio_x(11);
					ventana_graficas.setCantidad_inicio_y(5);
					ventana_graficas.inicializar(true);
					ventana_graficas.dibujarPuntos(listado_puntos);
					ventana_graficas.dibujarLineasSeguimiento();

					ventana_graficas.detach();
					// uterina = ventana_graficas.getInputStreamGrafica();
					// log.info("2- "+uterina);
				}
			}
		} catch (NumberFormatException e) {
			MensajesUtil
					.mensajeAlerta(
							"Error en los resultados",
							"Los resultados la altura uterina y semana de gestacion deben ser de tipo numerico");
			return false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return true;

	}

	public boolean onGraficarIncrementoMaterno(boolean mostrar_grafica) {
		try {
			if (dbxPesoInicial.getValue() == null && mostrar_grafica) {
				dbxPesoInicial.setFocus(true);
				Clients.showNotification(
						"Debe diligenciar el peso inicial para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, dbxPesoInicial,
						"end_after", 3000, true);
				return false;
			}
			if (dbxPeso.getValue() == null && mostrar_grafica) {
				dbxPeso.setFocus(true);
				Clients.showNotification(
						"Debe diligenciar el peso para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, dbxPeso,
						"end_after", 3000, true);
				return false;
			}

			Double peso_inicial = dbxPesoInicial.getValue();
			Double peso_actual = dbxPeso.getValue();

			Double variacion_peso = peso_actual - peso_inicial;

			if (variacion_peso > 25) {
				dbxPeso.setFocus(true);
				Clients.showNotification(
						"El valor de la variacion de peso supera los 15 kg",
						Clients.NOTIFICATION_TYPE_WARNING, dbxPeso,
						"end_after", 3000, true);
				return false;
			}

			List<Presultados_paraclinicos> lista_resultados = manejadorValoracion_obstetrica
					.obtenerResultados_paraclinicos();
			Presultados_paraclinicos semana_gestacion = null;
			for (Presultados_paraclinicos presultados_paraclinicos : lista_resultados) {
				if (presultados_paraclinicos.getCodigo_examen().equals(
						ManejadorParaclinicos.EXAMEN_SEMANA_GESTACION)) {
					semana_gestacion = presultados_paraclinicos;
				}
			}

			if (semana_gestacion != null) {
				int semana = ConvertidorDatosUtil
						.convertirDatot(semana_gestacion.getResultado().trim());

				if (semana >= 13) {
					punto_incremento_materno = new Puntos_graficas_embarazo();
					punto_incremento_materno.setCodigo_empresa(codigo_empresa);
					punto_incremento_materno
							.setCodigo_sucursal(codigo_sucursal);
					punto_incremento_materno.setNro_identificacion(admision
							.getNro_identificacion());
					punto_incremento_materno.setPunto_x(semana);
					punto_incremento_materno.setPunto_y(variacion_peso
							.intValue());
					punto_incremento_materno.setTipo_grafica("2");

					if (mostrar_grafica) {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("nro_identificacion",
								admision.getNro_identificacion());
						parametros.put("tipo_grafica", "2");

						List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
								.getServicio(
										Puntos_graficas_embarazoService.class)
								.listar(parametros);
						listado_puntos.add(punto_incremento_materno);

						GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
								.createComponents(
										"/WEB-INF/macros/graficasEmbarazo.zul",
										this, null);

						ventana_graficas.setRegion_xi(84);
						ventana_graficas.setRegion_xf(618);
						ventana_graficas.setRegion_yi(520);
						ventana_graficas.setRegion_yf(15);
						ventana_graficas.setUnidades_x(18);
						ventana_graficas.setUnidades_y(30);
						ventana_graficas
								.setImagen_fondo("/images/embarazo/incremento_materno.png");
						ventana_graficas.setIncremento_x(2);
						ventana_graficas.setIncremento_y(2);
						ventana_graficas.setCantidad_inicio_x(12);
						ventana_graficas.setCantidad_inicio_y(-1);
						ventana_graficas.inicializar(true);
						ventana_graficas.dibujarPuntos(listado_puntos);
						ventana_graficas.dibujarLineasSeguimiento();

					} else {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("nro_identificacion",
								admision.getNro_identificacion());
						parametros.put("tipo_grafica", "2");

						List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
								.getServicio(
										Puntos_graficas_embarazoService.class)
								.listar(parametros);
						listado_puntos.add(punto_incremento_materno);

						GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
								.createComponents(
										"/WEB-INF/macros/graficasEmbarazo.zul",
										this, null);

						ventana_graficas.setRegion_xi(84);
						ventana_graficas.setRegion_xf(618);
						ventana_graficas.setRegion_yi(520);
						ventana_graficas.setRegion_yf(15);
						ventana_graficas.setUnidades_x(18);
						ventana_graficas.setUnidades_y(30);
						ventana_graficas
								.setImagen_fondo("/images/embarazo/incremento_materno.png");
						ventana_graficas.setIncremento_x(2);
						ventana_graficas.setIncremento_y(2);
						ventana_graficas.setCantidad_inicio_x(12);
						ventana_graficas.setCantidad_inicio_y(-1);
						ventana_graficas.inicializar(true);
						ventana_graficas.dibujarPuntos(listado_puntos);
						ventana_graficas.dibujarLineasSeguimiento();

						ventana_graficas.detach();
						// incremento_materno =
						// ventana_graficas.getInputStreamGrafica();
						// log.info("incremento_materno"+incremento_materno);
					}
				}
			} else if (lbxSemana_gestacion.getSelectedIndex() == 0) {
				lbxSemana_gestacion.setFocus(true);
				Clients.showNotification(
						"Debe seleccionar las semanas de gestacion para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, lbxSemana_gestacion,
						"end_after", 3000, true);
				return false;
			} else {
				if (mostrar_grafica) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa", codigo_empresa);
					parametros.put("codigo_sucursal", codigo_sucursal);
					parametros.put("nro_identificacion",
							admision.getNro_identificacion());
					parametros.put("tipo_grafica", "2");

					List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
							.getServicio(Puntos_graficas_embarazoService.class)
							.listar(parametros);
					GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
							.createComponents(
									"/WEB-INF/macros/graficasEmbarazo.zul",
									this, null);

					ventana_graficas.setRegion_xi(84);
					ventana_graficas.setRegion_xf(618);
					ventana_graficas.setRegion_yi(520);
					ventana_graficas.setRegion_yf(15);
					ventana_graficas.setUnidades_x(18);
					ventana_graficas.setUnidades_y(30);
					ventana_graficas
							.setImagen_fondo("/images/embarazo/incremento_materno.png");
					ventana_graficas.setIncremento_x(2);
					ventana_graficas.setIncremento_y(2);
					ventana_graficas.setCantidad_inicio_x(12);
					ventana_graficas.setCantidad_inicio_y(-1);
					ventana_graficas.inicializar(true);
					ventana_graficas.dibujarPuntos(listado_puntos);
					ventana_graficas.dibujarLineasSeguimiento();

				} else {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa", codigo_empresa);
					parametros.put("codigo_sucursal", codigo_sucursal);
					parametros.put("nro_identificacion",
							admision.getNro_identificacion());
					parametros.put("tipo_grafica", "2");

					List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
							.getServicio(Puntos_graficas_embarazoService.class)
							.listar(parametros);
					GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
							.createComponents(
									"/WEB-INF/macros/graficasEmbarazo.zul",
									this, null);

					ventana_graficas.setRegion_xi(84);
					ventana_graficas.setRegion_xf(618);
					ventana_graficas.setRegion_yi(520);
					ventana_graficas.setRegion_yf(15);
					ventana_graficas.setUnidades_x(18);
					ventana_graficas.setUnidades_y(30);
					ventana_graficas
							.setImagen_fondo("/images/embarazo/incremento_materno.png");
					ventana_graficas.setIncremento_x(2);
					ventana_graficas.setIncremento_y(2);
					ventana_graficas.setCantidad_inicio_x(12);
					ventana_graficas.setCantidad_inicio_y(-1);
					ventana_graficas.inicializar(true);
					ventana_graficas.dibujarPuntos(listado_puntos);
					ventana_graficas.dibujarLineasSeguimiento();

					ventana_graficas.detach();
					// incremento_materno =
					// ventana_graficas.getInputStreamGrafica();
					// log.info("incremento_materno2"+incremento_materno);
				}

			}
		} catch (NumberFormatException e) {
			MensajesUtil
					.mensajeAlerta("Error en los resultados",
							"Los resultados de la semana de gestacion deben ser de tipo numerico");
			return false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return true;

	}

	public boolean onGraficarPresionDiastolica(boolean mostrar_grafica) {
		try {
			Double tension_sistolica = dbxPresion.getValue();
			Double tension_diastolica = dbxPresion2.getValue();

			if (dbxPresion.getValue() == null && mostrar_grafica) {
				dbxPresion.setFocus(true);
				Clients.showNotification(
						"Debe diligenciar la tension sistólica para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
						"end_after", 3000, true);
				return false;
			}
			if (dbxPresion2.getValue() == null && mostrar_grafica) {
				dbxPresion2.setFocus(true);
				Clients.showNotification(
						"Debe diligenciar la tension diastólica para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
						"end_after", 3000, true);
				return false;
			}

			// int tension_media = ((int) ((tension_diastolica) +
			// ((tension_sistolica - tension_diastolica) / 3)));
			int tension_media = ((int) (2 * tension_diastolica + tension_sistolica) / 3);

			List<Presultados_paraclinicos> lista_resultados = manejadorValoracion_obstetrica
					.obtenerResultados_paraclinicos();
			Presultados_paraclinicos semana_gestacion = null;
			for (Presultados_paraclinicos presultados_paraclinicos : lista_resultados) {
				if (presultados_paraclinicos.getCodigo_examen().equals(
						ManejadorParaclinicos.EXAMEN_SEMANA_GESTACION)) {
					semana_gestacion = presultados_paraclinicos;
				}
			}

			if (semana_gestacion != null) {
				int semana = ConvertidorDatosUtil
						.convertirDatot(semana_gestacion.getResultado().trim());

				if (semana >= 13) {
					punto_presion_diastolica = new Puntos_graficas_embarazo();
					punto_presion_diastolica.setCodigo_empresa(codigo_empresa);
					punto_presion_diastolica
							.setCodigo_sucursal(codigo_sucursal);
					punto_presion_diastolica.setNro_identificacion(admision
							.getNro_identificacion());
					punto_presion_diastolica.setPunto_x(semana);
					punto_presion_diastolica.setPunto_y(tension_media);
					punto_presion_diastolica.setTipo_grafica("3");

					if (mostrar_grafica) {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("nro_identificacion",
								admision.getNro_identificacion());
						parametros.put("tipo_grafica", "3");

						List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
								.getServicio(
										Puntos_graficas_embarazoService.class)
								.listar(parametros);
						listado_puntos.add(punto_presion_diastolica);

						GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
								.createComponents(
										"/WEB-INF/macros/graficasEmbarazo.zul",
										this, null);

						ventana_graficas.setRegion_xi(120);
						ventana_graficas.setRegion_xf(860);
						ventana_graficas.setRegion_yi(760);
						ventana_graficas.setRegion_yf(15);
						ventana_graficas.setUnidades_x(41);
						ventana_graficas.setUnidades_y(11);
						ventana_graficas
								.setImagen_fondo("/images/embarazo/presion_diastolica.png");
						ventana_graficas.setIncremento_x(1);
						ventana_graficas.setIncremento_y(5);
						ventana_graficas.setCantidad_inicio_x(23);
						ventana_graficas.setCantidad_inicio_y(35);

						ventana_graficas.inicializar(false);
						ventana_graficas.dibujarPuntos(listado_puntos);
						ventana_graficas.dibujarLineasSeguimiento();
					} else {

						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("nro_identificacion",
								admision.getNro_identificacion());
						parametros.put("tipo_grafica", "3");

						List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
								.getServicio(
										Puntos_graficas_embarazoService.class)
								.listar(parametros);
						listado_puntos.add(punto_presion_diastolica);

						GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
								.createComponents(
										"/WEB-INF/macros/graficasEmbarazo.zul",
										this, null);

						ventana_graficas.setRegion_xi(120);
						ventana_graficas.setRegion_xf(860);
						ventana_graficas.setRegion_yi(760);
						ventana_graficas.setRegion_yf(15);
						ventana_graficas.setUnidades_x(41);
						ventana_graficas.setUnidades_y(11);
						ventana_graficas
								.setImagen_fondo("/images/embarazo/presion_diastolica.png");
						ventana_graficas.setIncremento_x(1);
						ventana_graficas.setIncremento_y(5);
						ventana_graficas.setCantidad_inicio_x(23);
						ventana_graficas.setCantidad_inicio_y(35);

						ventana_graficas.inicializar(false);
						ventana_graficas.dibujarPuntos(listado_puntos);
						ventana_graficas.dibujarLineasSeguimiento();

						ventana_graficas.detach();
						// diastolica =
						// ventana_graficas.getInputStreamGrafica();
						// log.info("diastolica"+diastolica);
					}
				}
			} else if (lbxSemana_gestacion.getSelectedIndex() == 0) {
				lbxSemana_gestacion.setFocus(true);
				Clients.showNotification(
						"Debe seleccionar las semanas de gestacion para poder graficar",
						Clients.NOTIFICATION_TYPE_WARNING, lbxSemana_gestacion,
						"end_after", 3000, true);
				return false;
			} else {
				if (mostrar_grafica) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa", codigo_empresa);
					parametros.put("codigo_sucursal", codigo_sucursal);
					parametros.put("nro_identificacion",
							admision.getNro_identificacion());
					parametros.put("tipo_grafica", "3");

					List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
							.getServicio(Puntos_graficas_embarazoService.class)
							.listar(parametros);
					GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
							.createComponents(
									"/WEB-INF/macros/graficasEmbarazo.zul",
									this, null);

					ventana_graficas.setRegion_xi(120);
					ventana_graficas.setRegion_xf(860);
					ventana_graficas.setRegion_yi(760);
					ventana_graficas.setRegion_yf(15);
					ventana_graficas.setUnidades_x(41);
					ventana_graficas.setUnidades_y(11);
					ventana_graficas
							.setImagen_fondo("/images/embarazo/presion_diastolica.png");
					ventana_graficas.setIncremento_x(1);
					ventana_graficas.setIncremento_y(5);
					ventana_graficas.setCantidad_inicio_x(23);
					ventana_graficas.setCantidad_inicio_y(35);

					ventana_graficas.inicializar(false);
					ventana_graficas.dibujarPuntos(listado_puntos);
					ventana_graficas.dibujarLineasSeguimiento();
				} else {

					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa", codigo_empresa);
					parametros.put("codigo_sucursal", codigo_sucursal);
					parametros.put("nro_identificacion",
							admision.getNro_identificacion());
					parametros.put("tipo_grafica", "3");

					List<Puntos_graficas_embarazo> listado_puntos = getServiceLocator()
							.getServicio(Puntos_graficas_embarazoService.class)
							.listar(parametros);
					GraficasEmbarazoMacro ventana_graficas = (GraficasEmbarazoMacro) Executions
							.createComponents(
									"/WEB-INF/macros/graficasEmbarazo.zul",
									this, null);

					ventana_graficas.setRegion_xi(120);
					ventana_graficas.setRegion_xf(860);
					ventana_graficas.setRegion_yi(760);
					ventana_graficas.setRegion_yf(15);
					ventana_graficas.setUnidades_x(41);
					ventana_graficas.setUnidades_y(11);
					ventana_graficas
							.setImagen_fondo("/images/embarazo/presion_diastolica.png");
					ventana_graficas.setIncremento_x(1);
					ventana_graficas.setIncremento_y(5);
					ventana_graficas.setCantidad_inicio_x(23);
					ventana_graficas.setCantidad_inicio_y(35);

					ventana_graficas.inicializar(false);
					ventana_graficas.dibujarPuntos(listado_puntos);
					ventana_graficas.dibujarLineasSeguimiento();

					ventana_graficas.detach();
					// diastolica = ventana_graficas.getInputStreamGrafica();
					// log.info("diastolica2"+diastolica);
				}
			}
		} catch (NumberFormatException e) {
			MensajesUtil
					.mensajeAlerta("Error en los resultados",
							"Los resultados de la semana de gestacion deben ser de tipo numerico");
			return false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return true;
	}

	private void cargarRemisionInterna(
			Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_deteccion_alt_embarazo
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_deteccion_alt_embarazo
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_deteccion_alt_embarazo
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private void cargarAgudezaVisual(
			Hisc_deteccion_alt_embarazo hisc_deteccion_alt_embarazo)
			throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_historia(hisc_deteccion_alt_embarazo
				.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(hisc_deteccion_alt_embarazo
				.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(hisc_deteccion_alt_embarazo
				.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	public void mostrarSemanaEmbarazo() {
		if (lbxSemana_gestacion.getSelectedIndex() > 0) {
			Integer semanas = ConvertidorDatosUtil
					.convertirDatot(lbxSemana_gestacion.getSelectedItem()
							.getValue().toString());
			String rango = "";
			gbxValoracionRiesgoObstetrico.setVisible(true);
			if (semanas >= 0 && semanas < 14) {
				rango = "0-13";
				// gbxValoracionRiesgoObstetrico.setVisible(false);
				cellIMC1.setVisible(true);
				cellIMC2.setVisible(true);
			} else {
				cellIMC1.setVisible(true);
				cellIMC2.setVisible(true);
				// gbxValoracionRiesgoObstetrico.setVisible(true);
				if (semanas >= 14 && semanas < 28) {
					rango = "14-27";
				} else if (semanas >= 28 && semanas < 33) {
					rango = "28-32";
				} else if (semanas >= 33 && semanas < 43) {
					rango = "33-42";
				}
			}
			lbTotalRiesgoBiopsicosocial.setValue("Total (" + rango + "):");
			lbObservaciones_ecografias.setValue("Ecografía(" + rango + "):");
		} else {
			gbxValoracionRiesgoObstetrico.setVisible(false);
			cellIMC1.setVisible(true);
			cellIMC2.setVisible(true);
		}
	}

	public void onCambiarCitologia() {
		String value = rdbCitologias.getSelectedItem().getValue();
		if (value.equals("1")) {
			divCitologia.setVisible(true);
			divTamizaje.setVisible(false);
			lbxTamizaje_cuello.setSelectedIndex(6);
		} else {
			divCitologia.setVisible(false);
			divTamizaje.setVisible(true);
			lbxTamizaje_cuello.setSelectedIndex(0);
		}
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
		informacion_clinica.append("\t")
				.append("'" + tbxGestion_actual.getValue() + "'").append("\n");

		informacion_clinica.append("- SIGNOS VITALES\n");
		if (!dbxCardiaca.getText().isEmpty()) {
			informacion_clinica.append("\tFrecuencia cardiaca: ")
					.append(dbxCardiaca.getText()).append("\n");
		}

		if (!dbxRespiratoria.getText().isEmpty()) {
			informacion_clinica.append("\tFrecuencia respiratoria: ")
					.append(dbxRespiratoria.getText()).append("\n");
		}

		if (!dbxTemperatura.getText().isEmpty()) {
			informacion_clinica.append("\tTemperatura: ")
					.append(dbxTemperatura.getText()).append("\n");
		}

		if (!dbxPeso.getText().isEmpty()) {
			informacion_clinica.append("\tPeso(kg): ")
					.append(dbxPeso.getText()).append("\n");
		}

		if (!dbxTalla.getText().isEmpty()) {
			informacion_clinica.append("\tTalla: ").append(dbxTalla.getText())
					.append("\n");
		}

		if (!dbxImc.getText().isEmpty()) {
			informacion_clinica.append("\tIMC: ").append(dbxImc.getText())
					.append("\n");
		}

		informacion_clinica.append("- EXAMEN FISICO").append("\n");

		if (!tbxAnalisis.getValue().isEmpty()) {
			informacion_clinica.append("\tAnalisis y recomendaciones: ");
			informacion_clinica.append(tbxAnalisis.getValue()).append("\n");
			informacion_clinica.append(tbxRecomendaciones_farmacol.getValue())
					.append("\n");
		}

		informacion_clinica.append("- DIAGNOSTICOS").append("\n");
		Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
				.obtenerImpresionDiagnostica();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = getServiceLocator().getServicio(GeneralExtraService.class)
				.consultar(cie);

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
		cie = getServiceLocator().getServicio(GeneralExtraService.class)
				.consultar(cie);

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
		cie = getServiceLocator().getServicio(GeneralExtraService.class)
				.consultar(cie);

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
		cie = getServiceLocator().getServicio(GeneralExtraService.class)
				.consultar(cie);

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
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("PYP ALT. EMBARAZO");

		return serivicio1.toString();
	}

	public void onCambiarCantPartos() {
		if (ibxNum_partos_p.getValue() != null
				&& ibxNum_partos_p.getValue() <= 0) {
			dtbxFecha_ultimo_parto.setText("");
			dtbxFecha_ultimo_parto.setDisabled(true);
		} else {
			dtbxFecha_ultimo_parto.setDisabled(false);
			dtbxFecha_ultimo_parto.setValue(new Date());
		}
	}

	public void onCambiarFpp() {
		Calendar calendar_fpp = Calendar.getInstance();
		calendar_fpp.setTime(dtbxFecha_probable_parto_fpp.getValue());
		Calendar calendar_hoy = Calendar.getInstance();
		calendar_hoy.setTime(new Date());
		Boolean pasado = calendar_hoy.get(Calendar.DAY_OF_YEAR) > calendar_fpp
				.get(Calendar.DAY_OF_YEAR);

		if (dtbxFecha_probable_parto_fpp.getValue() != null && pasado) {
			Clients.showNotification(
					"La fecha seleccionada debe ser superior a la fecha actual",
					Clients.NOTIFICATION_TYPE_ERROR,
					dtbxFecha_probable_parto_fpp, "end_before", 4000, true);
			dtbxFecha_probable_parto_fpp.setText("");
			dtbxFecha_probable_parto_fpp.setFocus(true);
		}
	}

	public void onRecuerdaFum() {
		if (rdbRecuerda_fum.getSelectedItem().getValue().equals("1")) {
			rowFum.setVisible(true);
		} else {
			rowFum.setVisible(false);
		}
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

			dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxRespiratoria.setValue(enfermera_signos
					.getFrecuencia_respiratoria());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxPresion.setValue(enfermera_signos.getTa_sistolica());
			dbxPresion2.setValue(enfermera_signos.getTa_diastolica());
			dbxImc.setValue(enfermera_signos.getImc());
			dbxTemperatura.setValue(enfermera_signos.getTemperatura());
			dbxPesoInicial.setValue(enfermera_signos.getPeso_inicial());
			dbxFc_fetal.setValue(enfermera_signos.getFc_fetal());
			calcularTA(dbxPresion, dbxPresion2);
			alertaTemperatura(dbxTemperatura);
			alarmaExamenFisicoFc();
			alarmaExamenFisicoFr();
			onChangeIncrementoPeso();
			onChangeFrecuenciaCardiacaFetal();
			calcularIMC(dbxPeso, dbxTalla, dbxImc);

		}
	}

	/*
	 * public void imprimir() throws Exception { String nro_historia =
	 * infoPacientes.getCodigo_historia(); if (nro_historia.equals("")) {
	 * Messagebox.show("La historia no se ha guardado aún", "Informacion ..",
	 * Messagebox.OK, Messagebox.INFORMATION); return; }
	 * 
	 * Map paramRequest = new HashMap(); paramRequest.put("name_report",
	 * "Historia_pyp_embarazo"); paramRequest.put("nro_historia", nro_historia);
	 * paramRequest.put("formato",
	 * lbxFormato.getSelectedItem().getValue().toString());
	 * 
	 * paramRequest.put("sexo", admision.getPaciente().getSexo());
	 * 
	 * 
	 * onGraficarAlturaUterina(false); paramRequest.put("img_uterina", uterina);
	 * //log.info("-----uterina---"+uterina);
	 * 
	 * onGraficarIncrementoMaterno(false); paramRequest.put("img_materna",
	 * incremento_materno);
	 * //log.info("-----incremento_materno---"+incremento_materno);
	 * 
	 * onGraficarPresionDiastolica(false); paramRequest.put("img_diastolica",
	 * diastolica); //log.info("-----diastolica---"+diastolica);
	 * 
	 * 
	 * Component componente = Executions.createComponents(
	 * "/pages/printInformes.zul", this, paramRequest); final Window window =
	 * (Window) componente; window.setMode("modal");
	 * window.setMaximizable(true); window.setMaximized(true);
	 * 
	 * // Clients.evalJavaScript("window.open('"+request.getContextPath()+
	 * "/pages/printInformes.zul"+parametros_pagina+"', '_blank');"); }
	 */
	public void cambioGestacion() {
		Integer gestaciones = ibxNum_embarazo_g.getValue();
		if (gestaciones != null) {
			if (gestaciones == 0) {
				rdbParidad_biopsicosocial.setSelectedIndex(0);
			} else if (gestaciones >= 1 && gestaciones <= 4) {
				rdbParidad_biopsicosocial.setSelectedIndex(1);
			} else if (gestaciones > 5) {
				rdbParidad_biopsicosocial.setSelectedIndex(2);
			}
		}
	}

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
			Clients.evalJavaScript("window.open(\"" + request.getContextPath()
					+ "/pages/reports/hisc_deteccion_alt_embarazo_reporte.zul"
					+ parametros_pagina + "\", '_blank');");
		}
	}

}
