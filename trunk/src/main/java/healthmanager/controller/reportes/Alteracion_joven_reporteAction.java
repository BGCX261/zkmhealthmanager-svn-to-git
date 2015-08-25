/*
 * alteracion_jovenAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Alteracion_joven;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Alteracion_jovenService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
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
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.TannerMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Alteracion_joven_reporteAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger.getLogger(Alteracion_joven_reporteAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

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

	private Paciente paciente;
	private Alteracion_joven alteracion_joven;
//	private ESexo sexo;
//	private Timestamp fecha;
	
	// Componentes //
//	private OpcionesFormulario opcion_formulario;
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowVolumentesticular;
	@View
	private Row rowAgudeza_visual;
	@View
	private Checkbox chbTetano;
	@View
	private Checkbox chbSarampion;
	@View
	private Checkbox chbFiebre_amarilla;
	@View
	private Checkbox chbvph;
	@View
	private Checkbox chbHemoclasificacion_paraclinicos;
	@View
	private Checkbox chbHto;
	@View
	private Checkbox chbHemoclasificacion_ordenados;
	@View
	private Row rowServicios_amigable;
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Radiogroup rdbAdolecencia;
	@View
	private Radiogroup rdbLee;
	@View
	private Textbox tbxNro_inscripcion;

	@View
	private Label lbxEnfermedad_actual;
	@View
	private Label lbxMotivo_consulta;
	@View
	private Radiogroup rdbDependecia;
	@View
	private Radiogroup rdbPersonas_cargo;
	@View
	private Radiogroup rdbPersonas_cuidado;
	@View
	private Textbox tbxCuantas_quienes;
	@View
	private Textbox tbxOcupacion_padre;
	@View
	private Textbox tbxOcupacion_madre;
	@View
	private Bandbox tbxNombre_padre;
	@View
	private Bandbox tbxNombre_madre;
	@View
	private Bandbox tbxNombre_Educativo_padre;
	@View
	private Bandbox tbxNombre_Educativo_madre;
	@View
	private Textbox tbxEducativo_madre;
	@View
	private Textbox tbxEducativo_padre;
	@View
	private Label lbxRelaciones;
	@View
	private Label lbxSituaciones;
	@View
	private Checkbox chbNecrologicos;
	@View
	private Textbox tbxCual_necrologico;
	@View
	private Checkbox chbMentales;
	@View
	private Textbox tbxCual_mentales;
	@View
	private Checkbox chbAlcoholismo;
	@View
	private Checkbox chbDrogadiccion;
	@View
	private Textbox tbxCual_alcoholismo;
	@View
	private Radiogroup rdbTabaquismo;
	@View
	private Radiogroup rdbActivopasivo;
	@View
	private Datebox dtbxFecha_hasta;
	@View
	private Intbox ibxNo_cigarros;
	@View
	private Checkbox chbCancer;
	@View
	private Textbox tbxCual_tabaquismo;
	@View
	private Checkbox chbDislipidemia;
	@View
	private Textbox tbxQuien_dislipidemia;
	@View
	private Checkbox chbDiabetes;
	@View
	private Textbox tbxQuien_diabetes;
	@View
	private Checkbox chbHipertencion;
	@View
	private Checkbox chbCancer1;
	@View
	private Textbox tbxQuien_cancer1;
	@View
	private Textbox tbxQuien_hepertencion;
	@View
	private Label lbxMedicos;
	@View
	private Label lbxQuirurguicos;
	@View
	private Label lbxAlerguicos;
	@View
	private Label lbxHospitalizacion;
	@View
	private Label lbxTraumatico;
	@View
	private Radiogroup rdbDiscapacidad;
	@View
	private Textbox tbxCual_discapacidad;
	@View
	private Checkbox chbAlcoholismo_personaal;
	@View
	private Checkbox chbDrogadiccion_personal;
	@View
	private Radiogroup rdbTabaquismo_personal;
	@View
	private Radiogroup rdbActivopasivo2;
	@View
	private Datebox dtbxFecha_hasta2;
	@View
	private Intbox ibxNo_cigarros2;
	@View
	private Textbox tbxCual_personal;
	@View
	private Radiogroup rdbEjercicio_fisico;
	@View
	private Textbox tbxCual_ejercicio;
	@View
	private Intbox ibxFrecuencia;
	@View
	private Intbox ibxIntensidad;
//	private String via_ingreso = "13";

	@View
	private Radiogroup rdbGrupo_juvenil;
	@View
	private Textbox tbxCual_grupo;
	@View
	private Radiogroup rdbEquipo_deportivo;
	@View
	private Textbox tbxCual_equipo;
	@View
	private Textbox tbxReligion;
	@View
	private Radiogroup rdbVida_sexual;
	@View
	private Intbox ibxEdad_inicio;
	@View
	private Intbox ibxNo_parejas;
	@View
	private Radiogroup rdbPromiscuidad;
	@View
	private Radiogroup rdbEts;
	@View
	private Textbox tbxCual_ets;
	@View
	private Intbox ibxObstetricos_g;
	@View
	private Intbox ibxObstetricos_p;
	@View
	private Intbox ibxObstetricos_a;
	@View
	private Intbox ibxObstetricos_c;
	@View
	private Intbox ibxNo_hijos;
	@View
	private Datebox dtbxFum;
	@View
	private Listbox lbxTendencias_sexuales;
	@View
	private Checkbox chbPreservativos;
	@View
	private Checkbox chbAnticonceptivos;
	@View
	private Textbox tbxCual_preservativo;
	@View
	private Checkbox chbAbuso_sexual;
	@View
	private Checkbox chbMaltrato_fisico;
	@View
	private Checkbox chbMaltrato_psicologico;
	@View
	private Label lbxAntecedentes_judiciales;
	@View
	private Label lbxRevision_sistemas;
	@View
	private Doublebox dbxCardiaca;
	@View
	private Doublebox dbxRespiratoria;
	@View
	private Doublebox dbxPeso;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxPresion;
	@View
	private Doublebox dbxPresion2;
	@View
	private Doublebox dbxImc;
	@View
	private Label lbxEstado_tanner;
	@View
	private Label lbxOtros_hallagos;
	@View
	private Checkbox chbHb_hto;
	@View
	private Checkbox chbVdrl;
	@View
	private Checkbox chbVih;
	@View
	private Checkbox chbCitologia;
	@View
	private Checkbox chbColesterol;
	@View
	private Label lbxPlan_intervencion;
	@View
	private Label lbFecha_hasta;
	@View
	private Label lbNo_cigarros;
	@View
	private Label lbFecha_hasta2;
	@View
	private Label lbNo_cigarros2;

	private String tipo_historia;
//	private Long codigo_historia_anterior;

	@View
	private Radiogroup rdbImagen_corporal;
	@View
	private Radiogroup rdbAutopercepcion;
	@View
	private Radiogroup rdbReferente_adulto;
	@View
	private Radiogroup rdbProyectos_de_vida;
	@View
	private Label lbxObservaciones1;
//	private boolean cobrar_agudeza;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "macroImpresion_diagnostica",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "btImprimir" };

	// Jose
	@View
	private Radiogroup rdbExcreta;
	@View
	private Radiogroup rdbExamen_neurologico;

	// Ajustar a Macro
	private Admision admision;
//	private Citas cita;
//	private Opciones_via_ingreso opciones_via_ingreso;

	
	
	// Campos ocultos Gineco-Obstetricos
	@View
	private Row rowObstetricos;
	@View
	private Row rowObstetricos2;

	// Campos Ocultos
	@View
	private Row row2;
	@View
	private Label lbCual_ejercicio;
	@View
	private Label lbFrecuencia;
	@View
	private Label lbIntensidad;
	@View
	private Label lbCual_ets;
	@View
	private Label lbCuantas_quienes;
	@View
	private Label lbCual_discapacidad;
	@View
	private Radiogroup rdbAceptacion;
	@View
	private Radiogroup rdbNovio;
	@View
	private Radiogroup rdbAmigos;
	@View
	private Radiogroup rdbActividad_grupal;
	@View
	private Intbox ibxTelevision;
	@View
	private Intbox ibxDeporte;
	@View
	private Radiogroup rdbOtras_actividades;
	@View
	private Textbox tbxCual;
	@View
	private Label lbxObservaciones2;
	@View
	private Radiogroup rdbActiva_pasiva2;
	@View
	private Datebox dtbxMenarca;
	@View
	private Datebox dtbxEsperma;
	@View
	private Radiogroup rdbCiclos_regulares;
	@View
	private Radiogroup rdbDismenorrea;
	@View
	private Radiogroup rdbFlujo_patologico;
	@View
	private Label lbxObservacione3;
	@View
	private Radiogroup rdbConoce_corresponde;
	@View
	private Radiogroup rdbEs_confiable;
	@View
	private Radiogroup rdbTrans_psicologicos;
	@View
	private Radiogroup rdbAlcohol_droga;
	@View
	private Radiogroup rdbViolencia_intrafamili;
	@View
	private Radiogroup rdbMadre_adolesc;
	@View
	private Radiogroup rdbJudiciales;
	@View
	private Radiogroup rdbOtros;
	@View
	private Label lbxCual_otro;
	@View
	private Label lbxObservaciones_antec_famili;
	@View
	private Checkbox chbConvive_madre;
	@View
	private Checkbox chbConvive_padre;
	@View
	private Checkbox chbConvive_madrasta;
	@View
	private Checkbox chbConvive_padrasto;
	@View
	private Checkbox chbConvive_hermanos;
	@View
	private Checkbox chbConvive_pareja;
	@View
	private Checkbox chbConvive_hijo;
	@View
	private Checkbox chbConvive_otros;
	@View
	private Textbox tbxConvive_cual;
	@View
	private Listbox lbxVive_instituto;
	@View
	private Listbox lbxPercepcion_familia;
	@View
	private Radiogroup rdbEnergia_electrica;
	@View
	private Radiogroup rdbAgua_hogar;
	@View
	private Intbox ibxNumero_cuartos;
	@View
	private Label lbxObservaciones_vivienda;
	@View
	private Radiogroup rdbEstudia;
	@View
	private Intbox ibxGrado_cursado;
	@View
	private Intbox ibxAnios_aprobados;
	@View
	private Radiogroup rdbProblemas_escuela;
	@View
	private Intbox ibxAnios_repetidos;
	@View
	private Textbox tbxCausas_anios_repetidos;
	@View
	private Radiogroup rdbDesercion;
	@View
	private Textbox tbxCausa_desercion;
	@View
	private Radiogroup rdbEdu_formal;
	@View
	private Textbox tbxCual_edu_formal;
	@View
	private Label lbxObservaciones_edu;
	@View
	private Listbox lbxTrabajo_actividad;
	@View
	private Intbox ibxEdad_inicio_trabajo;
	@View
	private Intbox ibxTrabajo_horas;
	@View
	private Listbox lbxHorario_trabajo;
	@View
	private Listbox lbxRazon_trabajo;
	@View
	private Textbox tbxOtra_razon_trabajo;
	@View
	private Listbox lbxTrabajo_legalizado;
	@View
	private Listbox lbxTrabajo_insaludable;
	@View
	private Label lbxObservaciones_trab;
	@View
	private Radiogroup rdbTipo_trab_relacion_escolar3;
	@View
	private Radiogroup rdbSuenio;
	@View
	private Radiogroup rdbAlimentacion;
	@View
	private Intbox ibxComida;
	@View
	private Intbox ibxComida_familia;
	@View
	private Intbox ibxEdad_inicio_tabaco;
	@View
	private Intbox ibxEdad_inicio_alcohol;
	@View
	private Radiogroup rdbConduce_vehiculo;
	@View
	private Textbox tbxCual_vehiculo;
	@View
	private Listbox lbxOrientacion_sexual;
	@View
	private Label lbxObservaciones_habitos;
	@View
	private Radiogroup rdbOtro_toxico;
	@View
	private Label lbxCual_toxico;
	@View
	private Radiogroup rdbPiel;
	@View
	private Radiogroup rdbCabeza;
	@View
	private Radiogroup rdbBoca_diente;
	@View
	private Radiogroup rdbCuello_tiroides;
	@View
	private Radiogroup rdbTorax_mamas;
	@View
	private Radiogroup rdbCardio_pulmorar;
	@View
	private Radiogroup rdbGenito_urinario;
	@View
	private Radiogroup rdbAbdomen;
	@View
	private Radiogroup rdbColumna;
	@View
	private Radiogroup rdbExtremidades;
	@View
	private Radiogroup rdbNeurologico;
	@View
	private Radiogroup rdbAspecto_generar;
	@View
	private Radiogroup rdbAgudeza_visual;
	@View
	private Radiogroup rdbAgudeza_auditival;
	@View
	private Doublebox dbxVolumen_tes_izq;
	@View
	private Doublebox dbxVolumen_tes_der;
	@View
	private Radio Adolecencia1;
	@View
	private Radio Adolecencia2;
	@View
	private Radio Adolecencia3;
	@View
	private Radio Adolecencia4;
	@View
	private Radio Adolecencia5;
	@View
	private Listbox lbxTamizaje_cuello;
	@View
	private Row rowEstadioTanner1;
	@View
	private Row rowEstadioTanner2;

//	private String valida_admision;

	@View(isMacro = true)
	private TannerMacro macroTanner;

	public boolean hbRealizado;
	public boolean htoRealizado;

	private Integer anios;

//	private String nro_ingreso_admision;

	/* Observaciones */

	@View private Label lbxObservaciones_piel;
	@View private Label lbxObservaciones_cabeza;
	@View private Label lbxObservaciones_boca;
	@View private Label lbxObservaciones_cuello;
	@View private Label lbxObservaciones_masas;
	@View private Label lbxObservaciones_torax;
	@View private Label lbxObservaciones_genito;
	@View private Label lbxObservaciones_abdomen;
	@View private Label lbxObservaciones_columna;
	@View private Label lbxObservaciones_extremidades;
	@View private Label lbxObservaciones_neurologico;
	
	@View private Label lbxObservaciones_general;
	@View private Label lbxObservaciones_visual;
	@View private Label lbxObservaciones_auditiva;
	
	@View private Row row1;
	@View private Row row2_;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	
	public void habilitarIntbox(Radiogroup r, Intbox t) {
		if (r.getSelectedItem().getValue().equals("1")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void validarcheck() {
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if (edad >= 10 && edad <= 14) {
		}
	}

	public void habilitarTexboxChecbo(Checkbox c, Textbox t) {
		if (c.isChecked()) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void alarmaExamenFisicoTemperatura() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 80 && dbxCardiaca.getValue() <= 86)
				&& (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		}
		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 60 && dbxCardiaca.getValue() <= 80)
				&& (edad > 16)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:red");
		}

	}

	public void alarmaExamenFisicoRespiracion() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() >= 18 && dbxRespiratoria
						.getValue() <= 20) && (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		}
		if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() >= 16 && dbxRespiratoria
						.getValue() <= 20) && (edad > 16)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	public void alarmaExamenTensionSistolica() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxPresion.getText() != "")
				&& (dbxPresion.getValue() == (edad + 100))
				&& (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);

		}
		if ((dbxPresion.getText() != "")
				&& (dbxPresion.getValue() == (edad + 100) && (edad > 16))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		}

	}

	public void alarmaExamenTensionDiastolica() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxPresion2.getText() != "")
				&& (dbxPresion2.getValue() == (((edad + 100) / 2) + 10) && (edad >= 10 && edad <= 15))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);

		}
		if ((dbxPresion2.getText() != "")
				&& (dbxPresion2.getValue() == (((edad + 100) / 2) + 10) && (edad > 16))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		}

	}

	public void habilitarlistbox(Listbox l, Textbox t) {
		if (l.getSelectedItem().getValue().equals("3")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
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
							IVias_ingreso.ALTERACION_JOVEN);
					paciente = admision.getPaciente();
//					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
//					fecha = paciente.getFecha_nacimiento();
				}
			}

			listarCombos();
			if(paciente.getSexo().equals("F")){
				rowObstetricos.setVisible(true);
				rowObstetricos2.setVisible(true);
			}else{
				rowObstetricos.setVisible(false);
				rowObstetricos2.setVisible(false);
			}
			Integer edad = Integer.valueOf(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(admision.getPaciente()
									.getFecha_nacimiento()), "1", false));
			
			if (edad >= 10 && edad <= 17) {
				if (admision.getPaciente().getSexo().equals("MASCULINO")
						|| admision.getPaciente().getSexo().equals("M")) {
					rowVolumentesticular.setVisible(true);
					macroTanner.mostrarFilasMasculino();
					rowEstadioTanner1.setVisible(true);
					// rowEstadioTanner2.setVisible(true);
				}
				if (admision.getPaciente().getSexo().equals("FEMENINO")
						|| admision.getPaciente().getSexo().equals("F")) {
					macroTanner.mostrarFilasFemenino();
					rowEstadioTanner1.setVisible(true);
					// rowEstadioTanner2.setVisible(true);
				}
			}
			
			
			if(id_codigo_historia!=null){
				alteracion_joven = new Alteracion_joven();
				alteracion_joven.setCodigo_empresa(codigo_empresa);
				alteracion_joven.setCodigo_sucursal(codigo_sucursal);
				alteracion_joven.setCodigo_historia(id_codigo_historia);
				alteracion_joven = getServiceLocator().getServicio(Alteracion_jovenService.class).consultar(alteracion_joven);
				if(alteracion_joven!=null){
					
					if(alteracion_joven.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DE ALTERACION AL JOVEN\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DE ALTERACION AL JOVEN\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(alteracion_joven.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					mostrarDatos(alteracion_joven);
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

	public void habilitarServiciosAmigables() {
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if (edad >= 10 && edad <= 17) {
			rowServicios_amigable.setVisible(true);
		}
	}

	public void alarmaTendenciaSexual(Radiogroup r) {
		boolean sw = false;
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		//log.info("Edad>>>>>>>>>>>>>" + infoPacientes.getEdadAnios());
		Integer edad = infoPacientes.getEdadAnios();
		if (edad >= 10 && edad <= 29) {
			sw = true;
		}
		if (r.getSelectedItem().getValue().equals("2") && sw == true) {
			String mensaje = "remitir a psicología";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, r, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
		}
	}

	public void alarmaTendenciaTrabajo(Listbox lbx) {

		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = infoPacientes.getEdadAnios();
		if (edad >= 10 && edad <= 17) {

		}
		if (lbx.getSelectedItem().getValue().equals("2")
				|| lbx.getSelectedItem().getValue().equals("3")) {
			String mensaje = "remitir a psicología";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, lbx, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
		}
	}

	public void alarmaTendenciaTrabajo(Intbox ibx, Textbox t) {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = infoPacientes.getEdadAnios();
		if (edad >= 10 && edad <= 17) {

		}
		if (!ibx.getText().equals("") && ibx.getValue() > 0) {
			String mensaje = "REMITIR A psicología";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, ibx, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
			t.setReadonly(false);
		} else {
			t.setReadonly(false);
			t.setText("");
		}
		if (!ibx.getText().equals("") && ibx.getValue() > 0) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void alarmaTendenciaTrabajo(Radiogroup r) {
		boolean sw = false;
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = infoPacientes.getEdadAnios();
		if (edad >= 10 && edad <= 17) {
			sw = true;
		}
		if (r.getSelectedItem().getValue().equals("2") && sw == true) {
			String mensaje = "REMITIR A psicología";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, r, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
		}
	}

	public void alarmaTendenciaTrabajo(AbstractComponent abstcom) {
		boolean sw = false;
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = infoPacientes.getEdadAnios();
		if (edad >= 10 && edad <= 17) {
			sw = true;
		}
		if (sw) {
			String mensaje = "REMITIR A psicología";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, abstcom,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		}
	}

	
	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxOrientacion_sexual, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTendencias_sexuales, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTamizaje_cuello, true,
				getServiceLocator());
		lbxTamizaje_cuello.setSelectedIndex(6);
	}

	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Alteracion_joven alteracion_joven = (Alteracion_joven) obj;
		try {
//			this.nro_ingreso_admision = alteracion_joven.getNro_ingreso();
			infoPacientes.setCodigo_historia(alteracion_joven
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(alteracion_joven.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					alteracion_joven.getUltimo_update());
			tbxNro_inscripcion.setValue(alteracion_joven.getNro_inscripcion());

			initMostrar_datos(alteracion_joven);

			cargarInformacion_paciente();

			lbxEnfermedad_actual.setValue(alteracion_joven
					.getEnfermedad_actual());
			lbxMotivo_consulta.setValue(alteracion_joven.getMotivo_consulta());
			Utilidades.seleccionarRadio(rdbAdolecencia,
					alteracion_joven.getAdolecencia());
			Utilidades.seleccionarRadio(rdbLee, alteracion_joven.getLee());
			Utilidades.seleccionarRadio(rdbDependecia,
					alteracion_joven.getDependecia());
			Utilidades.seleccionarRadio(rdbPersonas_cargo,
					alteracion_joven.getPersonas_cargo());
			Utilidades.seleccionarRadio(rdbPersonas_cuidado,
					alteracion_joven.getPersonas_cuidado());
			if (alteracion_joven.getPersonas_cuidado().equals("1")) {
				lbCuantas_quienes.setVisible(true);
				tbxCuantas_quienes.setVisible(true);
				tbxCuantas_quienes.setValue(alteracion_joven
						.getCuantas_quienes());
			} else {
				lbCuantas_quienes.setVisible(false);
				tbxCuantas_quienes.setVisible(false);

			}

			Ocupaciones ocupacion_padre = new Ocupaciones();
			ocupacion_padre.setId(alteracion_joven.getOcupacion_padre());
			ocupacion_padre = getServiceLocator().getOcupacionesService()
					.consultar(ocupacion_padre);
			tbxNombre_padre.setValue(ocupacion_padre != null ? ocupacion_padre
					.getNombre() : "");
			tbxOcupacion_padre
					.setValue(ocupacion_padre != null ? ocupacion_padre.getId()
							: "");

			Ocupaciones ocupacion_madre = new Ocupaciones();
			ocupacion_madre.setId(alteracion_joven.getOcupacion_madre());
			ocupacion_madre = getServiceLocator().getOcupacionesService()
					.consultar(ocupacion_madre);
			tbxNombre_madre.setValue(ocupacion_madre != null ? ocupacion_madre
					.getNombre() : "");
			tbxOcupacion_madre
					.setValue(ocupacion_madre != null ? ocupacion_madre.getId()
							: "");

			Nivel_educativo nivel_educativo_padre = new Nivel_educativo();
			nivel_educativo_padre.setId(alteracion_joven.getEducativo_padre());
			nivel_educativo_padre = getServiceLocator()
					.getNivel_educativoService().consultar(
							nivel_educativo_padre);
			tbxNombre_Educativo_padre
					.setValue(nivel_educativo_padre != null ? nivel_educativo_padre
							.getNombre() : "");
			tbxEducativo_padre
					.setValue(nivel_educativo_padre != null ? nivel_educativo_padre
							.getId() : "");

			Nivel_educativo nivel_educativo_madre = new Nivel_educativo();
			nivel_educativo_madre.setId(alteracion_joven.getEducativo_madre());
			nivel_educativo_madre = getServiceLocator()
					.getNivel_educativoService().consultar(
							nivel_educativo_madre);
			tbxNombre_Educativo_madre
					.setValue(nivel_educativo_madre != null ? nivel_educativo_madre
							.getNombre() : "");
			tbxEducativo_madre
					.setValue(nivel_educativo_madre != null ? nivel_educativo_madre
							.getId() : "");

			Nivel_educativo nivel_educativo_joven = new Nivel_educativo();
			nivel_educativo_joven.setId(alteracion_joven.getEducativo_joven());
			nivel_educativo_joven = getServiceLocator()
					.getNivel_educativoService().consultar(
							nivel_educativo_joven);

			lbxRelaciones.setValue(alteracion_joven.getRelaciones());
			lbxSituaciones.setValue(alteracion_joven.getSituaciones());

			chbNecrologicos.setChecked(alteracion_joven.getNecrologicos());
			tbxCual_necrologico
					.setValue(alteracion_joven.getCual_necrologico());
			chbMentales.setChecked(alteracion_joven.getMentales());
			tbxCual_mentales.setValue(alteracion_joven.getCual_mentales());
			chbAlcoholismo.setChecked(alteracion_joven.getAlcoholismo());
			chbDrogadiccion.setChecked(alteracion_joven.getDrogadiccion());
			tbxCual_alcoholismo
					.setValue(alteracion_joven.getCual_alcoholismo());
			Utilidades.seleccionarRadio(rdbTabaquismo,
					alteracion_joven.getTabaquismo());
			Utilidades.seleccionarRadio(rdbActivopasivo,
					alteracion_joven.getActivopasivo());
			dtbxFecha_hasta.setValue(alteracion_joven.getFecha_hasta());
			ibxNo_cigarros.setValue(alteracion_joven.getNo_cigarros());
			chbCancer.setChecked(alteracion_joven.getCancer());
			tbxCual_tabaquismo.setValue(alteracion_joven.getCual_tabaquismo());
			chbDislipidemia.setChecked(alteracion_joven.getDislipidemia());
			tbxQuien_dislipidemia.setValue(alteracion_joven
					.getQuien_dislipidemia());
			chbDiabetes.setChecked(alteracion_joven.getDiabetes());
			tbxQuien_diabetes.setValue(alteracion_joven.getQuien_diabetes());
			chbHipertencion.setChecked(alteracion_joven.getHipertencion());
			tbxQuien_hepertencion.setValue(alteracion_joven
					.getQuien_hepertencion());
			chbCancer1.setChecked(alteracion_joven.getCancer1());
			tbxQuien_cancer1.setValue(alteracion_joven.getQuien_cancer1());

			lbxMedicos.setValue(alteracion_joven.getMedicos());
			lbxQuirurguicos.setValue(alteracion_joven.getQuirurguicos());
			lbxAlerguicos.setValue(alteracion_joven.getAlerguicos());
			lbxHospitalizacion.setValue(alteracion_joven.getHospitalizacion());
			lbxTraumatico.setValue(alteracion_joven.getTraumatico());
			Utilidades.seleccionarRadio(rdbDiscapacidad,
					alteracion_joven.getDiscapacidad());

			if (alteracion_joven.getDiscapacidad().equals("1")) {
				lbCual_discapacidad.setVisible(true);
				tbxCual_discapacidad.setVisible(true);
				tbxCual_discapacidad.setValue(alteracion_joven
						.getCual_discapacidad());
			} else {
				lbCual_discapacidad.setVisible(false);
				tbxCual_discapacidad.setVisible(false);

			}

			chbAlcoholismo_personaal.setChecked(alteracion_joven
					.getAlcoholismo_personaal());
			chbDrogadiccion_personal.setChecked(alteracion_joven
					.getDrogadiccion_personal());
			Utilidades.seleccionarRadio(rdbTabaquismo_personal,
					alteracion_joven.getTabaquismo_personal());
			Utilidades.seleccionarRadio(rdbActivopasivo2,
					alteracion_joven.getActivopasivo2());
			dtbxFecha_hasta2.setValue(alteracion_joven.getFecha_hasta2());
			ibxNo_cigarros2.setValue(alteracion_joven.getNo_cigarros2());
			tbxCual_personal.setValue(alteracion_joven.getCual_personal());

			Utilidades.seleccionarRadio(rdbEjercicio_fisico,
					alteracion_joven.getEjercicio_fisico());
			if (alteracion_joven.getEjercicio_fisico().equals("1")) {
				row2.setVisible(true);
				lbCual_ejercicio.setVisible(true);
				lbFrecuencia.setVisible(true);
				lbIntensidad.setVisible(true);

				tbxCual_ejercicio.setVisible(true);
				ibxFrecuencia.setVisible(true);
				ibxIntensidad.setVisible(true);

				tbxCual_ejercicio
						.setValue(alteracion_joven.getCual_ejercicio());
				ibxFrecuencia
						.setValue((alteracion_joven.getFrecuencia() != null && !alteracion_joven
								.getFrecuencia().isEmpty()) ? Integer
								.parseInt(alteracion_joven.getFrecuencia())
								: null);
				ibxIntensidad
						.setValue((alteracion_joven.getIntensidad() != null && !alteracion_joven
								.getIntensidad().isEmpty()) ? Integer
								.parseInt(alteracion_joven.getIntensidad())
								: null);
			} else {
				row2.setVisible(false);
				lbCual_ejercicio.setVisible(false);
				lbFrecuencia.setVisible(false);
				lbIntensidad.setVisible(false);

				tbxCual_ejercicio.setVisible(false);
				ibxFrecuencia.setVisible(false);
				ibxIntensidad.setVisible(false);

			}
			Utilidades.seleccionarRadio(rdbGrupo_juvenil,
					alteracion_joven.getGrupo_juvenil());
			tbxCual_grupo.setValue(alteracion_joven.getCual_grupo());
			Utilidades.seleccionarRadio(rdbEquipo_deportivo,
					alteracion_joven.getEquipo_deportivo());
			tbxCual_equipo.setValue(alteracion_joven.getCual_equipo());
			tbxReligion.setValue(alteracion_joven.getReligion());
			ibxEdad_inicio
					.setValue((alteracion_joven.getEdad_inicio() != null && !alteracion_joven
							.getEdad_inicio().isEmpty()) ? Integer
							.parseInt(alteracion_joven.getEdad_inicio()) : null);
			ibxNo_parejas
					.setValue((alteracion_joven.getNo_parejas() != null && !alteracion_joven
							.getNo_parejas().isEmpty()) ? Integer
							.parseInt(alteracion_joven.getNo_parejas()) : null);

			Utilidades.seleccionarRadio(rdbPromiscuidad,
					alteracion_joven.getPromiscuidad());
			Utilidades.seleccionarRadio(rdbEts, alteracion_joven.getEts());

			if (alteracion_joven.getEts().equals("1")) {
				lbCual_ets.setVisible(true);
				tbxCual_ets.setVisible(true);
				tbxCual_ets.setValue(alteracion_joven.getCual_ets());
			} else {
				lbCual_ets.setVisible(false);
				tbxCual_ets.setVisible(false);

			}

			ibxObstetricos_g
					.setValue((alteracion_joven.getObstetricos_g() != null && !alteracion_joven
							.getObstetricos_g().isEmpty()) ? Integer
							.parseInt(alteracion_joven.getObstetricos_g())
							: null);
			ibxObstetricos_p
					.setValue((alteracion_joven.getObstetricos_p() != null && !alteracion_joven
							.getObstetricos_p().isEmpty()) ? Integer
							.parseInt(alteracion_joven.getObstetricos_p())
							: null);
			ibxObstetricos_a
					.setValue((alteracion_joven.getObstetricos_a() != null && !alteracion_joven
							.getObstetricos_a().isEmpty()) ? Integer
							.parseInt(alteracion_joven.getObstetricos_a())
							: null);
			ibxObstetricos_c
					.setValue((alteracion_joven.getObstetricos_c() != null && !alteracion_joven
							.getObstetricos_c().isEmpty()) ? Integer
							.parseInt(alteracion_joven.getObstetricos_c())
							: null);
			ibxNo_hijos
					.setValue((alteracion_joven.getNo_hijos() != null && !alteracion_joven
							.getNo_hijos().isEmpty()) ? Integer
							.parseInt(alteracion_joven.getNo_hijos()) : null);

			dtbxFum.setValue(alteracion_joven.getFum());

			/*
			 * tbxTendencias_sexuales.setValue(alteracion_joven
			 * .getTendencias_sexuales());
			 */
			chbPreservativos.setChecked(alteracion_joven.getPreservativos());
			chbAnticonceptivos
					.setChecked(alteracion_joven.getAnticonceptivos());
			tbxCual_preservativo.setValue(alteracion_joven
					.getCual_preservativo());
			chbAbuso_sexual.setChecked(alteracion_joven.getAbuso_sexual());
			chbMaltrato_fisico
					.setChecked(alteracion_joven.getMaltrato_fisico());
			chbMaltrato_psicologico.setChecked(alteracion_joven
					.getMaltrato_psicologico());
			lbxAntecedentes_judiciales.setValue(alteracion_joven
					.getAntecedentes_judiciales());
			lbxRevision_sistemas.setValue(alteracion_joven
					.getRevision_sistemas());
			dbxCardiaca
					.setValue((alteracion_joven.getCardiaca() != null && !alteracion_joven
							.getCardiaca().isEmpty()) ? Double
							.parseDouble(alteracion_joven.getCardiaca()) : null);
			dbxRespiratoria
					.setValue((alteracion_joven.getRespiratoria() != null && !alteracion_joven
							.getRespiratoria().isEmpty()) ? Double
							.parseDouble(alteracion_joven.getRespiratoria())
							: null);
			dbxPeso.setValue((alteracion_joven.getPeso() != null && !alteracion_joven
					.getPeso().isEmpty()) ? Double.parseDouble(alteracion_joven
					.getPeso()) : null);
			dbxTalla.setValue((alteracion_joven.getTalla() != null && !alteracion_joven
					.getTalla().isEmpty()) ? Double
					.parseDouble(alteracion_joven.getTalla()) : null);
			dbxPresion
					.setValue((alteracion_joven.getPresion() != null && !alteracion_joven
							.getPresion().isEmpty()) ? Double
							.valueOf(alteracion_joven.getPresion()) : null);
			dbxPresion2
					.setValue((alteracion_joven.getPresion2() != null && !alteracion_joven
							.getPresion2().isEmpty()) ? Double
							.valueOf(alteracion_joven.getPresion2()) : null);
			dbxImc.setValue((alteracion_joven.getInd_masa() != null && !alteracion_joven
					.getInd_masa().isEmpty()) ? Double
					.parseDouble(alteracion_joven.getInd_masa()) : null);

			lbxEstado_tanner.setValue(alteracion_joven.getEstado_tanner());
			lbxOtros_hallagos.setValue(alteracion_joven.getOtros_hallagos());

			/* relacionado 1 */

			/* relacionado 2 */

			/* relacionado 3 */

			chbHb_hto.setValue(alteracion_joven.getHb_hto());
			chbVdrl.setValue(alteracion_joven.getVdrl());
			chbVih.setValue(alteracion_joven.getVih());
			chbCitologia.setValue(alteracion_joven.getCitologia());
			chbColesterol.setValue(alteracion_joven.getColesterol());
			lbxPlan_intervencion.setValue(alteracion_joven
					.getPlan_intervencion());
			Utilidades.seleccionarRadio(rdbImagen_corporal,
					alteracion_joven.getImagen_corporal());
			Utilidades.seleccionarRadio(rdbAutopercepcion,
					alteracion_joven.getAutopercepcion());
			Utilidades.seleccionarRadio(rdbReferente_adulto,
					alteracion_joven.getReferente_adulto());
			Utilidades.seleccionarRadio(rdbProyectos_de_vida,
					alteracion_joven.getProyectos_de_vida());
			lbxObservaciones1.setValue(alteracion_joven.getObservaciones());

			Utilidades.seleccionarRadio(rdbAceptacion,
					alteracion_joven.getAceptacion());
			Utilidades.seleccionarRadio(rdbNovio, alteracion_joven.getNovio());
			Utilidades
					.seleccionarRadio(rdbAmigos, alteracion_joven.getAmigos());
			Utilidades.seleccionarRadio(rdbActividad_grupal,
					alteracion_joven.getActividad_grupal());
			ibxTelevision.setValue(alteracion_joven.getTelevision());
			ibxDeporte.setValue(alteracion_joven.getDeporte());
			Utilidades.seleccionarRadio(rdbOtras_actividades,
					alteracion_joven.getOtras_actividades());
			tbxCual.setValue(alteracion_joven.getCual());
			lbxObservaciones2.setValue(alteracion_joven.getObservaciones2());
			Utilidades.seleccionarRadio(rdbActiva_pasiva2,
					alteracion_joven.getActiva_pasiva2());
			dtbxMenarca.setValue(alteracion_joven.getMenarca());
			dtbxEsperma.setValue(alteracion_joven.getEsperma());
			chbHemoclasificacion_paraclinicos.setValue(alteracion_joven
					.getHemoclasificacion_paraclinicos());
			chbHemoclasificacion_ordenados.setValue(alteracion_joven
					.getHemoclasificacion_ordenados());
			chbHto.setValue(alteracion_joven.getHto());
			chbTetano.setValue(alteracion_joven.getTetano());
			chbFiebre_amarilla.setValue(alteracion_joven.getFiebre_amarilla());
			chbSarampion.setValue(alteracion_joven.getSarampion());
			Utilidades.seleccionarRadio(rdbCiclos_regulares,
					alteracion_joven.getCiclos_regulares());
			Utilidades.seleccionarRadio(rdbDismenorrea,
					alteracion_joven.getDismenorrea());
			Utilidades.seleccionarRadio(rdbEs_confiable,
					alteracion_joven.getEs_confiable());
			Utilidades.seleccionarRadio(rdbTrans_psicologicos,
					alteracion_joven.getTrans_psicologicos());
			Utilidades.seleccionarRadio(rdbAlcohol_droga,
					alteracion_joven.getAlcohol_droga());
			Utilidades.seleccionarRadio(rdbViolencia_intrafamili,
					alteracion_joven.getViolencia_intrafamili());
			Utilidades.seleccionarRadio(rdbMadre_adolesc,
					alteracion_joven.getMadre_adolesc());
			Utilidades.seleccionarRadio(rdbJudiciales,
					alteracion_joven.getJudiciales());
			Utilidades.seleccionarRadio(rdbOtros, alteracion_joven.getOtros());
			lbxCual_otro.setValue(alteracion_joven.getCual_otro());
			lbxObservaciones_antec_famili.setValue(alteracion_joven
					.getObservaciones_antec_famili());
			chbConvive_madre.setChecked(alteracion_joven.getConvive_madre()
					.equals("S") ? true : false);
			chbConvive_padre.setChecked(alteracion_joven.getConvive_padre()
					.equals("S") ? true : false);
			chbConvive_madrasta.setChecked(alteracion_joven
					.getConvive_madrasta().equals("S") ? true : false);
			chbConvive_padrasto.setChecked(alteracion_joven
					.getConvive_padrasto().equals("S") ? true : false);
			chbConvive_hermanos.setChecked(alteracion_joven
					.getConvive_hermanos().equals("S") ? true : false);
			chbConvive_pareja.setChecked(alteracion_joven.getConvive_pareja()
					.equals("S") ? true : false);
			chbConvive_hijo.setChecked(alteracion_joven.getConvive_hijo()
					.equals("S") ? true : false);
			chbConvive_otros.setChecked(alteracion_joven.getConvive_otros()
					.equals("S") ? true : false);
			tbxConvive_cual.setValue(alteracion_joven.getConvive_cual());

			/*
			 * Utilidades.seleccionarRadio(rdbTp_tra_md_sus_nin,
			 * alteracion_joven.getTp_tra_md_sus_nin());
			 */
			/*
			 * Utilidades.seleccionarRadio(rdbTp_tra_md_sus_no_estable,
			 * alteracion_joven.getTp_tra_md_sus_no_estable());
			 */
			/*
			 * Utilidades.seleccionarRadio(rdbTp_tra_md_sus_estable,
			 * alteracion_joven.getTp_tra_md_sus_estable());
			 */
			/*
			 * Utilidades.seleccionarRadio(rdbTp_tra_pa_sus_nin,
			 * alteracion_joven.getTp_tra_pa_sus_nin());
			 */
			/*
			 * Utilidades.seleccionarRadio(rdbTp_tra_pa_sus_no_estable,
			 * alteracion_joven.getTp_tra_pa_sus_no_estable());
			 */
			/*
			 * Utilidades.seleccionarRadio(rdbTp_tra_pa_sus_estable,
			 * alteracion_joven.getTp_tra_pa_sus_estable());
			 */
			for (int i = 0; i < lbxVive_instituto.getItemCount(); i++) {
				Listitem listitem = lbxVive_instituto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getVive_instituto())) {
					listitem.setSelected(true);
					i = lbxVive_instituto.getItemCount();
				}
			}
			for (int i = 0; i < lbxPercepcion_familia.getItemCount(); i++) {
				Listitem listitem = lbxPercepcion_familia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getPercepcion_familia())) {
					listitem.setSelected(true);
					i = lbxPercepcion_familia.getItemCount();
				}
			}
			Utilidades.seleccionarRadio(rdbEnergia_electrica,
					alteracion_joven.getEnergia_electrica());
			Utilidades.seleccionarRadio(rdbAgua_hogar,
					alteracion_joven.getAgua_hogar());
			ibxNumero_cuartos.setValue(Integer.valueOf(alteracion_joven
					.getNumero_cuartos()));
			lbxObservaciones_vivienda.setValue(alteracion_joven
					.getObservaciones_vivienda());
			Utilidades.seleccionarRadio(rdbEstudia,
					alteracion_joven.getEstudia());
			ibxGrado_cursado.setValue(Integer.valueOf(alteracion_joven
					.getGrado_cursado()));
			ibxAnios_aprobados.setValue(Integer.valueOf(alteracion_joven
					.getAnios_aprobados()));
			Utilidades.seleccionarRadio(rdbProblemas_escuela,
					alteracion_joven.getProblemas_escuela());
			ibxAnios_repetidos.setValue(Integer.valueOf(alteracion_joven
					.getAnios_repetidos()));
			tbxCausas_anios_repetidos.setValue(alteracion_joven
					.getCausas_anios_repetidos());
			Utilidades.seleccionarRadio(rdbDesercion,
					alteracion_joven.getDesercion());
			tbxCausa_desercion.setValue(alteracion_joven.getCausa_desercion());
			Utilidades.seleccionarRadio(rdbEdu_formal,
					alteracion_joven.getEdu_formal());
			tbxCual_edu_formal.setValue(alteracion_joven.getCual_edu_formal());
			lbxObservaciones_edu.setValue(alteracion_joven
					.getObservaciones_edu());
			for (int i = 0; i < lbxTrabajo_actividad.getItemCount(); i++) {
				Listitem listitem = lbxTrabajo_actividad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getTrabajo_actividad())) {
					listitem.setSelected(true);
					i = lbxTrabajo_actividad.getItemCount();
				}
			}
			ibxEdad_inicio_trabajo.setValue(Integer.valueOf(alteracion_joven
					.getEdad_inicio_trabajo()));
			ibxTrabajo_horas.setValue(Integer.valueOf(alteracion_joven
					.getTrabajo_horas()));
			for (int i = 0; i < lbxHorario_trabajo.getItemCount(); i++) {
				Listitem listitem = lbxHorario_trabajo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getHorario_trabajo())) {
					listitem.setSelected(true);
					i = lbxHorario_trabajo.getItemCount();
				}
			}
			for (int i = 0; i < lbxRazon_trabajo.getItemCount(); i++) {
				Listitem listitem = lbxRazon_trabajo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getRazon_trabajo())) {
					listitem.setSelected(true);
					i = lbxRazon_trabajo.getItemCount();
				}
			}
			tbxOtra_razon_trabajo.setValue(alteracion_joven
					.getOtra_razon_trabajo());
			for (int i = 0; i < lbxTrabajo_legalizado.getItemCount(); i++) {
				Listitem listitem = lbxTrabajo_legalizado.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getTrabajo_legalizado())) {
					listitem.setSelected(true);
					i = lbxTrabajo_legalizado.getItemCount();
				}
			}
			for (int i = 0; i < lbxTrabajo_insaludable.getItemCount(); i++) {
				Listitem listitem = lbxTrabajo_insaludable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getTrabajo_insaludable())) {
					listitem.setSelected(true);
					i = lbxTrabajo_insaludable.getItemCount();
				}
			}
			lbxObservaciones_trab.setValue(alteracion_joven
					.getObservaciones_trab());
			Utilidades.seleccionarRadio(rdbTipo_trab_relacion_escolar3,
					alteracion_joven.getTipo_trab_relacion_escolar3());
			Utilidades
					.seleccionarRadio(rdbSuenio, alteracion_joven.getSuenio());
			Utilidades.seleccionarRadio(rdbAlimentacion,
					alteracion_joven.getAlimentacion());
			ibxComida.setValue(Integer.valueOf(alteracion_joven.getComida()));
			ibxComida_familia.setValue(Integer.valueOf(alteracion_joven
					.getComida_familia()));
			ibxEdad_inicio_tabaco.setValue(Integer.valueOf(alteracion_joven
					.getEdad_inicio_tabaco()));
			ibxEdad_inicio_alcohol.setValue(Integer.valueOf(alteracion_joven
					.getEdad_inicio_alcohol()));
			Utilidades.seleccionarRadio(rdbConduce_vehiculo,
					alteracion_joven.getConduce_vehiculo());
			tbxCual_vehiculo.setValue(alteracion_joven.getCual_vehiculo());
			for (int i = 0; i < lbxOrientacion_sexual.getItemCount(); i++) {
				Listitem listitem = lbxOrientacion_sexual.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(alteracion_joven.getOrientacion_sexual())) {
					listitem.setSelected(true);
					i = lbxOrientacion_sexual.getItemCount();
				}
			}
			lbxObservaciones_habitos.setValue(alteracion_joven
					.getObservaciones_habitos());
			Utilidades.seleccionarRadio(rdbOtro_toxico,
					alteracion_joven.getOtro_toxico());
			lbxCual_toxico.setValue(alteracion_joven.getCual_toxico());
			Utilidades.seleccionarRadio(rdbPiel, alteracion_joven.getPiel());
			Utilidades
					.seleccionarRadio(rdbCabeza, alteracion_joven.getCabeza());
			Utilidades.seleccionarRadio(rdbBoca_diente,
					alteracion_joven.getBoca_diente());
			Utilidades.seleccionarRadio(rdbCuello_tiroides,
					alteracion_joven.getCuello_tiroides());
			Utilidades.seleccionarRadio(rdbTorax_mamas,
					alteracion_joven.getTorax_mamas());
			Utilidades.seleccionarRadio(rdbCardio_pulmorar,
					alteracion_joven.getCardio_pulmorar());
			Utilidades.seleccionarRadio(rdbGenito_urinario,
					alteracion_joven.getGenito_urinario());
			Utilidades.seleccionarRadio(rdbAbdomen,
					alteracion_joven.getAbdomen());
			Utilidades.seleccionarRadio(rdbColumna,
					alteracion_joven.getColumna());
			Utilidades.seleccionarRadio(rdbExtremidades,
					alteracion_joven.getExtremidades());
			Utilidades.seleccionarRadio(rdbNeurologico,
					alteracion_joven.getNeurologico());
			Utilidades.seleccionarRadio(rdbAspecto_generar,
					alteracion_joven.getAspecto_generar());
			Utilidades.seleccionarRadio(rdbAgudeza_visual,
					alteracion_joven.getAgudeza_visual());
			Utilidades.seleccionarRadio(rdbAgudeza_auditival,
					alteracion_joven.getAgudeza_auditival());
			dbxVolumen_tes_izq.setValue(Double.valueOf(alteracion_joven
					.getVolumen_tes_izq()));
			dbxVolumen_tes_der.setValue(Double.valueOf(alteracion_joven
					.getVolumen_tes_der()));
			Utilidades.seleccionarRadio(rdbConoce_corresponde,
					alteracion_joven.getConoce_corresponde());
			lbxObservacione3.setValue(alteracion_joven.getObsercacione3());
			Utilidades.seleccionarRadio(rdbConoce_corresponde,
					alteracion_joven.getConoce_corresponde());
			lbxObservacione3.setValue(alteracion_joven.getObsercacione3());

			Utilidades.seleccionarListitem(lbxTamizaje_cuello,
					alteracion_joven.getTamizaje_cuello());

			// Jose
			Utilidades.seleccionarRadio(rdbExcreta,
					alteracion_joven.getExcreta());
			Utilidades.seleccionarRadio(rdbExamen_neurologico,
					alteracion_joven.getExamen_neurologico());

			tipo_historia = alteracion_joven.getTipo_historia();

			cargarImpresionDiagnostica(alteracion_joven);

			cargarAgudezaVisual(alteracion_joven);
			

			
			FormularioUtil.mostrarObservaciones(rdbPiel, "2", lbxObservaciones_piel, row1);
			FormularioUtil.mostrarObservaciones(rdbCabeza, "2", lbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbBoca_diente,"2",lbxObservaciones_boca,row1);
			FormularioUtil.mostrarObservaciones(rdbCuello_tiroides, "2", lbxObservaciones_cuello, row2);
			FormularioUtil.mostrarObservaciones(rdbTorax_mamas,"2",lbxObservaciones_masas,row2);
			FormularioUtil.mostrarObservaciones(rdbCardio_pulmorar, "2", lbxObservaciones_torax, row2);
			FormularioUtil.mostrarObservaciones(rdbColumna, "2", lbxObservaciones_columna, row3);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",lbxObservaciones_abdomen,row3);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", lbxObservaciones_genito, row3);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "2", lbxObservaciones_extremidades, row4);	
			FormularioUtil.mostrarObservaciones(rdbExamen_neurologico,"2",lbxObservaciones_neurologico,row4);
			FormularioUtil.mostrarObservaciones(rdbAspecto_generar,"2",lbxObservaciones_general,row4);
			FormularioUtil.mostrarObservaciones(rdbAgudeza_auditival, "2", lbxObservaciones_auditiva, row5);
			FormularioUtil.mostrarObservaciones(rdbAgudeza_visual, "2", lbxObservaciones_visual, row5);

			lbxObservaciones_cabeza.setValue(alteracion_joven
					.getObservaciones_cabeza());
			lbxObservaciones_abdomen.setValue(alteracion_joven
					.getObservaciones_abdomen());
			lbxObservaciones_neurologico.setValue(alteracion_joven
					.getObservaciones_neurologico());
			
			lbxObservaciones_general.setValue(alteracion_joven
					.getObservaciones_general());
			lbxObservaciones_masas.setValue(alteracion_joven
					.getObservaciones_torax());
			
			lbxObservaciones_cuello.setValue(alteracion_joven
					.getObservaciones_cuello());
			lbxObservaciones_genito.setValue(alteracion_joven
					.getObservaciones_genito());

			lbxObservaciones_torax.setValue(alteracion_joven
					.getObservaciones_cardiaco());
			lbxObservaciones_columna.setValue(alteracion_joven
					.getObservaciones_columna());

			lbxObservaciones_visual.setValue(alteracion_joven
					.getObservaciones_visual());
			lbxObservaciones_extremidades.setValue(alteracion_joven
					.getObservaciones_extremidades());

			lbxObservaciones_auditiva.setValue(alteracion_joven
					.getObservaciones_auditiva());
			lbxObservaciones_piel.setValue(alteracion_joven
					.getObservaciones_piel());
			
			lbxObservaciones_boca.setValue(alteracion_joven
					.getObservaciones_boca());

			
			
			inicializarVista(tipo_historia);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}


	public void buscarOcupaciones(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
					"limit 25 offset 0");

			List<Ocupaciones> list = getServiceLocator()
					.getOcupacionesService().listar(parameters);

			lbx.getItems().clear();

			for (Ocupaciones dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getId() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
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

	public void selectedOcupaciones(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_padre.setValue("");
			tbxOcupacion_padre.setValue("");

		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			tbxNombre_padre.setValue(dato.getNombre());
			tbxOcupacion_padre.setValue(dato.getId());

		}
		tbxNombre_padre.close();
	}

	public void selectedOcupacion_madre(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_madre.setValue("");
			tbxOcupacion_madre.setValue("");

		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			tbxNombre_madre.setValue(dato.getNombre());
			tbxOcupacion_madre.setValue(dato.getId());

		}
		tbxNombre_madre.close();
	}

	public void buscarCodigo_educativo(String value, Listbox lbx)
			throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getNivel_educativoService().setLimit(
					"limit 25 offset 0");

			List<Nivel_educativo> list = getServiceLocator()
					.getNivel_educativoService().listar(parameters);

			lbx.getItems().clear();

			for (Nivel_educativo dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getId() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
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

	public void selectedCodigo_educativo_padre(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_Educativo_padre.setValue("");
			tbxEducativo_padre.setValue("");

		} else {
			Nivel_educativo dato = (Nivel_educativo) listitem.getValue();
			tbxNombre_Educativo_padre.setValue(dato.getNombre());
			tbxEducativo_padre.setValue(dato.getId());

		}
		tbxNombre_Educativo_padre.close();
	}

	public void selectedCodigo_educativo_madre(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_Educativo_madre.setValue("");
			tbxEducativo_madre.setValue("");

		} else {
			Nivel_educativo dato = (Nivel_educativo) listitem.getValue();
			tbxNombre_Educativo_madre.setValue(dato.getNombre());
			tbxEducativo_madre.setValue(dato.getId());

		}
		tbxNombre_Educativo_madre.close();
	}


	public void seleccion_radio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			System.out.println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {
					abstractComponent.setVisible(true);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}
					if (abstractComponent instanceof Listbox) {
						if (((Listbox) abstractComponent).getItemCount() > 0) {
							((Listbox) abstractComponent).setSelectedIndex(0);
						}
						Utilidades.listarElementoListbox(
								((Listbox) abstractComponent), true,
								getServiceLocator());

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						((Listbox) abstractComponent).getChildren().clear();
						for (int i = 0; i < ((Listbox) abstractComponent)
								.getItemCount(); i++) {
							Listitem listitem = ((Listbox) abstractComponent)
									.getItemAtIndex(i);
							if (listitem.getValue().toString().equals("")) {
								listitem.setSelected(true);
								i = ((Listbox) abstractComponent)
										.getItemCount();
							}
						}

					}
					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
					}

					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_radio2(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			System.out.println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(false);

					}
				} else {

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(true);

					}
				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check3(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check2(Checkbox checkbox, Checkbox checkbox2,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked() || checkbox2.isChecked()) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}
				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion(Listbox listbox, int entero,
			Component[] abstractComponents) {
		try {
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
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {

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

		Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(admision
				.getPaciente().getFecha_nacimiento());
		anios = mapa_edades.get("anios");

		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		
//		codigo_historia_anterior = alteracion_joven
//				.getCodigo_historia_anterior();
		tipo_historia = alteracion_joven.getTipo_historia();
	}

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
			e.printStackTrace();

		}
	}

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales
					.onCalcularTension(TA_sistolica, TA_diastolica);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaCardiaca(Doublebox dbxFc) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaCardiaca(dbxFc,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaRespiratoria(Doublebox dbxFr) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaRespiratoria(dbxFr,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void deshabilitar_conCheck(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {

			FormularioUtil.deshabilitarComponentes_conCheckbox(checkbox,
					abstractComponents);

		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_tabaquismo() {
		if (rdbTabaquismo.getSelectedItem().getValue().equals("2")) {
			rdbActivopasivo.setVisible(false);
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbNo_cigarros.setVisible(false);
			ibxNo_cigarros.setVisible(false);
			ibxNo_cigarros.setValue(0);
		} else {
			rdbActivopasivo.setVisible(true);
			seleccion_activo_pasivo();
		}
	}

	public void seleccion_activo_pasivo() {
		if (rdbActivopasivo.getSelectedItem().getValue().equals("2")) {
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbNo_cigarros.setVisible(false);
			ibxNo_cigarros.setVisible(false);
			ibxNo_cigarros.setValue(0);
		} else {
			lbFecha_hasta.setVisible(true);
			dtbxFecha_hasta.setVisible(true);
			lbNo_cigarros.setVisible(true);
			ibxNo_cigarros.setVisible(true);
		}
	}

	public void seleccion_tabaquismo2() {
		if (rdbTabaquismo_personal.getSelectedItem().getValue().equals("2")) {
			rdbActivopasivo2.setVisible(false);
			lbFecha_hasta2.setVisible(false);
			dtbxFecha_hasta2.setVisible(false);
			lbNo_cigarros2.setVisible(false);
			ibxNo_cigarros2.setVisible(false);
			ibxNo_cigarros2.setValue(0);
		} else {
			rdbActivopasivo2.setVisible(true);
			seleccion_activo_pasivo2();
		}
	}

	public void seleccion_activo_pasivo2() {
		if (rdbActivopasivo2.getSelectedItem().getValue().equals("2")) {
			lbFecha_hasta2.setVisible(false);
			dtbxFecha_hasta2.setVisible(false);
			lbNo_cigarros2.setVisible(false);
			ibxNo_cigarros2.setVisible(false);
			ibxNo_cigarros2.setValue(0);
		} else {
			lbFecha_hasta2.setVisible(true);
			dtbxFecha_hasta2.setVisible(true);
			lbNo_cigarros2.setVisible(true);
			ibxNo_cigarros2.setVisible(true);
		}
	}

	
	private void cargarAgudezaVisual(Alteracion_joven alteracion_joven)
			throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual
				.setCodigo_historia(alteracion_joven.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(alteracion_joven.getCodigo_empresa());
		agudeza_visual
				.setCodigo_sucursal(alteracion_joven.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	private void cargarImpresionDiagnostica(Alteracion_joven alteracion_joven)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);

		impresion_diagnostica.setCodigo_historia(alteracion_joven
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
	}

	public void deshabilitar_conRadio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	

	public void seleccionarActividadSexual() {
		Integer res1 = rdbVida_sexual.getSelectedIndex();
		ibxEdad_inicio.setReadonly(res1 == 1);
		Integer res2 = rdbActiva_pasiva2.getSelectedIndex();
		Boolean b = !(res1 == 0 && res2 == 0 && anios >= 10);
		lbxTamizaje_cuello.setDisabled(b);
		if (b) {
			lbxTamizaje_cuello.setSelectedIndex(6);
		} else {
			lbxTamizaje_cuello.setSelectedIndex(0);
		}
	}

	public void onCambioPromiscuidad() {
		ibxNo_parejas.setReadonly(rdbPromiscuidad.getSelectedIndex() == 1);
	}

	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("PYP ALT. JOVEN");

		return serivicio1.toString();
	}

	@Override
	public String getInformacionClinica() throws WrongValueException {
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
					.append(lbxEnfermedad_actual.getValue()).append("\n")
					.append("\n");
		}

		informacion_clinica.append("- SIGNOS VITALES: \t");
		informacion_clinica.append("Tension arterial Sistólica: ")
				.append(dbxPresion.getValue() + (",")).append("\t")
				.append("Tension arterial Diastólica: ")
				.append(dbxPresion2.getValue() + (",")).append("\t")
				.append("Frecuencia cardiaca: ")
				.append(dbxCardiaca.getValue() + (",")).append("\n")
				.append("\t").append("Frecuencia Respiratoria: ")
				.append(dbxRespiratoria.getValue() + (",")).append("\t");
		informacion_clinica.append("Talla: ")
				.append(dbxTalla.getValue() + (",")).append("\t")
				.append("Peso: ").append(dbxPeso.getValue() + (","))
				.append("\t").append("IMC: ").append(dbxImc.getValue() + (","))
				.append("\n").append("\n");

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

		informacion_clinica.append("\n:");

		
		return informacion_clinica.toString();

	}

	public void validarParaclinicosRealizados() {

	}

	public void validar() {
		Map<String, Object> parameters = new HashMap<String, Object>();

		//log.info("codigo_empresa" + codigo_empresa);
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_documento", admision.getNro_identificacion());

		getServiceLocator().getPresultados_paraclinicosService().setLimit(
				"limit 25 offset 0");

		List<Map<String, Object>> list = getServiceLocator()
				.getPresultados_paraclinicosService()
				.listar_validacion_paciente(parameters);

		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		String sexo = admision.getPaciente().getSexo();

		//log.info("list" + list);
		boolean validacion_hb = false;
		boolean validacion_hto = false;
		boolean validacion_citologia = false;
		boolean validacion_hemoclasificacion = false;

		if (list != null) {

			for (Map<String, Object> map : list) {
				//log.info("1----" + map);
				int age = (Integer) map.get("age");
				String codigo_examen = (String) map.get("codigo_examen");
				//log.info("2----" + age);

				if (codigo_examen.equals("00001")) {

					if (edad >= 10 && edad <= 14) {
						if (age >= 10 && age <= 14) {
							validacion_hb = true;
						}
					} else if (edad >= 15 && edad <= 19) {
						if (age >= 15 && age <= 19) {
							validacion_hb = true;
						}
					} else if (edad >= 20 && edad <= 24
							&& sexo.equalsIgnoreCase("F")) {
						if (age >= 20 && age <= 24) {
							validacion_hb = true;
						}
					} else if (edad >= 25 && edad <= 29
							&& sexo.equalsIgnoreCase("F")) {
						if (age >= 25 && age <= 29) {
							validacion_hb = true;
						}
					}

				} else if (codigo_examen.equals("00059")) {
					//log.info("---------" + edad + "---------" + age
							//+ "---------" + sexo);
					if (edad >= 10 && edad <= 14) {
						if (age >= 10 && age <= 14) {
							validacion_hto = true;
						}
					} else if (edad >= 15 && edad <= 19) {
						if (age >= 15 && age <= 19) {
							validacion_hto = true;
						}
					} else if (edad >= 20 && edad <= 24
							&& sexo.equalsIgnoreCase("F")) {
						if (age >= 20 && age <= 24) {
							validacion_hto = true;
						}
					} else if (edad >= 25 && edad <= 29
							&& sexo.equalsIgnoreCase("F")) {
						if (age >= 25 && age <= 29) {
							validacion_hto = true;
						}
					}

				} else if (codigo_examen.equals("00025")) {
					if (edad == age) {
						if (age <= 24) {
							validacion_citologia = true;
						}
					}

				} else if (codigo_examen.equals("00019")) {

					validacion_hemoclasificacion = true;
				}

			}

			if (validacion_hb) {
				chbHb_hto.setDisabled(true);
			}
			if (validacion_hto) {
				chbHto.setDisabled(true);
			}
			if (validacion_citologia) {
				chbCitologia.setDisabled(true);
			}

			if (validacion_hemoclasificacion) {
				chbHemoclasificacion_ordenados.setDisabled(true);
			}

		} else {
			//log.info("5-----");
			chbHemoclasificacion_ordenados.setDisabled(false);
			chbHemoclasificacion_paraclinicos.setDisabled(false);
			chbHb_hto.setDisabled(false);
			chbHto.setDisabled(false);
			chbColesterol.setDisabled(false);
			chbVdrl.setDisabled(false);
			chbVih.setDisabled(false);
			chbCitologia.setDisabled(false);
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

			calcularTA(dbxPresion, dbxPresion2);
			calcularFrecuenciaCardiaca(dbxCardiaca);
			calcularFrecuenciaRespiratoria(dbxRespiratoria);
			calcularIMC(dbxPeso, dbxTalla, dbxImc);

		}
	}
	

}
