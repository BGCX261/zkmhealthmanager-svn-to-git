/*
 * alteracion_jovenAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Alteracion_joven;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Bandbox;
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

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.TannerMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorParaclinicos;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;

import contaweb.modelo.bean.Articulo;
import healthmanager.modelo.service.GeneralExtraService;

public class Alteracion_jovenAction extends HistoriaClinicaModel implements
        IHistoria_generica {

    private static Logger log = Logger.getLogger(Alteracion_jovenAction.class);
    private static final long serialVersionUID = -9145887024839938515L;

	// Componentes //
    // private OpcionesFormulario opcion_formulario;
    @View(isMacro = true)
    private Agudeza_visualMacro macroAgudeza_visual;
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
    @View
    private Div divModuloOrdenamiento;
    @View
    private Div divModuloFarmacologico;
    @View(isMacro = true)
    private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

    @View(isMacro = true)
    private Remision_internaMacro macroRemision_interna;

    private Receta_rips_internoAction receta_ripsAction;
    private Orden_servicio_internoAction orden_servicioAction;
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
    private Row rowVolumentesticular;
    @View
    private Toolbarbutton btnCancelar;

    @View
    private Toolbarbutton toolbarbuttonTipo_historia;

    @View
    private Listbox lbxAtendida;

    @View(isMacro = true)
    private InformacionPacientesMacro infoPacientes;

    @View
    private Radiogroup rdbAdolecencia;
    @View
    private Radiogroup rdbLee;
    @View
    private Textbox tbxNro_inscripcion;

    @View
    private Textbox tbxEnfermedad_actual;
    @View
    private Textbox tbxMotivo_consulta;
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
    private Textbox tbxRelaciones;
    @View
    private Textbox tbxSituaciones;
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
    private Textbox tbxMedicos;
    @View
    private Textbox tbxQuirurguicos;
    @View
    private Textbox tbxAlerguicos;
    @View
    private Textbox tbxHospitalizacion;
    @View
    private Textbox tbxTraumatico;
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
    private String via_ingreso = "13";

	// @View
    // private Bandbox tbxNombre_Educativo_joven;
    // @View
    // private Textbox tbxEducativo_joven;
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
    private Alteracion_joven historia_anterior;
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
    private Textbox tbxAntecedentes_judiciales;
    @View
    private Textbox tbxRevision_sistemas;
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
    private Textbox tbxEstado_tanner;
    @View
    private Textbox tbxOtros_hallagos;
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
    private Textbox tbxPlan_intervencion;
    @View
    private Label lbFecha_hasta;
    @View
    private Label lbNo_cigarros;
    @View
    private Label lbFecha_hasta2;
    @View
    private Label lbNo_cigarros2;

    private String tipo_historia;
    private Long codigo_historia_anterior;

    @View
    private Radiogroup rdbImagen_corporal;
    @View
    private Radiogroup rdbAutopercepcion;
    @View
    private Radiogroup rdbReferente_adulto;
    @View
    private Radiogroup rdbProyectos_de_vida;
    @View
    private Textbox tbxObservaciones1;
    private boolean cobrar_agudeza;

    private final String[] idsExcluyentes = {"tbxValue", "lbxParameter",
        "northEditar", "macroImpresion_diagnostica",
        "toolbarbuttonPaciente_admisionado1",
        "toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
        "gridOrdenes_servicio", "btImprimir", "divModuloRemisiones_externas"};

    // Jose
    @View
    private Radiogroup rdbExcreta;
    @View
    private Radiogroup rdbExamen_neurologico;

    // Ajustar a Macro
    private Admision admision;
    private Citas cita;
    private Opciones_via_ingreso opciones_via_ingreso;

    @View
    private Toolbarbutton toolbarbuttonPaciente_admisionado1;
    @View
    private Toolbarbutton toolbarbuttonPaciente_admisionado2;

	// Paraclinicos
    @View
    private Vbox vboxParaclinicos;
    private ManejadorParaclinicos manejadorParaclinicos;

    @View
    private Toolbarbutton btGuardar;

    @View
    private Listbox lbxTipo_historia;

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
    private Textbox tbxObservaciones2;
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
    private Textbox tbxObservacione3;
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
    private Textbox tbxCual_otro;
    @View
    private Textbox tbxObservaciones_antec_famili;
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
    private Textbox tbxObservaciones_vivienda;
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
    private Textbox tbxObservaciones_edu;
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
    private Textbox tbxObservaciones_trab;
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
    private Textbox tbxObservaciones_habitos;
    @View
    private Radiogroup rdbOtro_toxico;
    @View
    private Textbox tbxCual_toxico;
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

	// private String valida_admision;
    @View(isMacro = true)
    private TannerMacro macroTanner;

    public boolean hbRealizado;
    public boolean htoRealizado;

    private Integer anios;

    private String nro_ingreso_admision;

    @View
    private Div divModuloRemisiones_externas;

    private Remisiones_externasAction remisiones_externasAction;

    @View
    private Toolbarbutton btnImprimir;

    /* Observaciones */
    @View
    private Textbox tbxObservaciones_piel;
    @View
    private Textbox tbxObservaciones_cabeza;
    @View
    private Textbox tbxObservaciones_boca;
    @View
    private Textbox tbxObservaciones_cuello;
    @View
    private Textbox tbxObservaciones_masas;
    @View
    private Textbox tbxObservaciones_torax;
    @View
    private Textbox tbxObservaciones_genito;
    @View
    private Textbox tbxObservaciones_abdomen;
    @View
    private Textbox tbxObservaciones_columna;
    @View
    private Textbox tbxObservaciones_extremidades;
    @View
    private Textbox tbxObservaciones_neurologico;

    @View
    private Textbox tbxObservaciones_general;
    @View
    private Textbox tbxObservaciones_visual;
    @View
    private Textbox tbxObservaciones_auditiva;

    @View
    private Row row1;
    @View
    private Row row2_;
    @View
    private Row row3;
    @View
    private Row row4;
    @View
    private Row row5;

    public void habilitarIntbox(Radiogroup r, Intbox t) {
        if (r.getSelectedItem().getValue().equals("1")) {
            t.setReadonly(false);
        } else {
            t.setReadonly(true);
            t.setText("");
        }
    }

    public void habilitarTexboxRadio(Radiogroup r, Textbox t) {
        if (r.getSelectedItem().getValue().equals("2")) {
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
            listarCombos();
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void crearResultadosParaclinicos() {
        manejadorParaclinicos = new ManejadorParaclinicos(
                ManejadorParaclinicos.TIPO_PARACLINICO, vboxParaclinicos,
                "00004", admision.getNro_identificacion(), this);
    }

    @Override
    public void params(Map<String, Object> map) {
        if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
            admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
            opciones_via_ingreso = (Opciones_via_ingreso) map
                    .get(IVias_ingreso.OPCION_VIA_INGRESO);
            cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
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
        }
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
        // log.info("Edad>>>>>>>>>>>>>" + infoPacientes.getEdadAnios());
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

    /*
     * public void validarSistomatico(Radiogroup r, Radiogroup r2) { String
     * POSICION_ALERTA = "end_before"; Integer TIEMPO_ALERTA = 9000; // String
     * mensje="";
     * 
     * if (r.getSelectedItem().getValue().equals("1") ||
     * r2.getSelectedItem().getValue().equals("1")) { String mensaje =
     * "Este paciente debe ser remitido internamente para consulta externa sistomático respiratorio val1"
     * ; Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_WARNING, r,
     * POSICION_ALERTA, TIEMPO_ALERTA, true); } if
     * (r2.getSelectedItem().getValue().equals("1") &&
     * r.getSelectedItem().getValue().equals("1")) { String mensaje =
     * "Este paciente debe ser remitido internamente para consulta externa sistomático respiratorio val3"
     * ; Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_WARNING, r,
     * POSICION_ALERTA, TIEMPO_ALERTA, true); } }
     */
    public void listarCombos() {
        listarParameter();
        listarAtendida();
        UtilidadesComponentes.listarElementosListbox(true, false,
                getServiceLocator(), lbxOrientacion_sexual,
                lbxTendencias_sexuales, lbxTamizaje_cuello);
        lbxTamizaje_cuello.setSelectedIndex(6);
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

    public void listarAtendida() {
        lbxAtendida.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue(false);
        listitem.setLabel("Pendiente");
        lbxAtendida.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue(true);
        listitem.setLabel("Atendida");
        lbxAtendida.appendChild(listitem);

        if (lbxAtendida.getItemCount() > 0) {
            lbxAtendida.setSelectedIndex(0);
        }
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

    private void onOpcionFormularioConsultar() throws Exception {
        groupboxConsulta.setVisible(true);
        groupboxEditar.setVisible(false);
        buscarDatos();
    }

    private void onOpcionFormularioRegistrar() throws Exception {
        groupboxConsulta.setVisible(false);
        groupboxEditar.setVisible(true);
        limpiarDatos();
        if (admision != null) {
            Integer edad = Integer.valueOf(Util.getEdad(
                    new java.text.SimpleDateFormat("dd/MM/yyyy")
                    .format(admision.getPaciente()
                            .getFecha_nacimiento()), "1", false));
            if (edad >= 10 && edad <= 17) {
                rowServicios_amigable.setVisible(true);
            }
            if (edad >= 10 && edad <= 13) {
                rdbAdolecencia.setSelectedItem(Adolecencia1);
            } else if (edad >= 14 && edad <= 16) {
                rdbAdolecencia.setSelectedItem(Adolecencia2);
            } else if (edad >= 17 && edad <= 21) {
                rdbAdolecencia.setSelectedItem(Adolecencia3);
            } else if (edad >= 22 && edad <= 29) {
                rdbAdolecencia.setSelectedItem(Adolecencia4);
            } else {
                rdbAdolecencia.setSelectedItem(Adolecencia5);
            }

            this.nro_ingreso_admision = admision.getNro_ingreso();
            infoPacientes.setFecha_inicio(new Date());
            cargarInformacion_paciente();
            // valida_admision = null;
            onMostrarModuloFarmacologico();
            receta_ripsAction.obtenerBotonAgregar().setVisible(true);
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(true);
            onMostrarModuloRemisiones();
        }

        btnImprimir.setVisible(false);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        manejadorParaclinicos.limpiarResultados_paraclinicos();

    }

    public void limpiarValidar() {
        tbxNro_inscripcion
                .setStyle("text-transform:uppercase;background-color:white");
        tbxEnfermedad_actual
                .setStyle("text-transform:uppercase;background-color:white");
        tbxMotivo_consulta
                .setStyle("text-transform:uppercase;background-color:white");
        tbxRelaciones
                .setStyle("text-transform:uppercase;background-color:white");
        tbxSituaciones
                .setStyle("text-transform:uppercase;background-color:white");
        tbxMedicos.setStyle("text-transform:uppercase;background-color:white");
        tbxQuirurguicos
                .setStyle("text-transform:uppercase;background-color:white");
        tbxAlerguicos
                .setStyle("text-transform:uppercase;background-color:white");
        tbxHospitalizacion
                .setStyle("text-transform:uppercase;background-color:white");
        tbxTraumatico
                .setStyle("text-transform:uppercase;background-color:white");
        tbxReligion.setStyle("text-transform:uppercase;background-color:white");
        lbxTendencias_sexuales
                .setStyle("text-transform:uppercase;background-color:white");
        tbxAntecedentes_judiciales
                .setStyle("text-transform:uppercase;background-color:white");
        tbxRevision_sistemas
                .setStyle("text-transform:uppercase;background-color:white");
        dbxCardiaca.setStyle("background-color:white");
        dbxRespiratoria.setStyle("background-color:white");
        dbxPeso.setStyle("background-color:white");
        dbxTalla.setStyle("background-color:white");
        dbxPresion.setStyle("background-color:white");
        dbxPresion2.setStyle("background-color:white");
        dbxImc.setStyle("background-color:white");
        tbxEstado_tanner
                .setStyle("text-transform:uppercase;background-color:white");
        tbxOtros_hallagos
                .setStyle("text-transform:uppercase;background-color:white");
        tbxPlan_intervencion
                .setStyle("text-transform:uppercase;background-color:white");
        tbxPlan_intervencion
                .setStyle("text-transform:uppercase;background-color:white");

    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        infoPacientes.validarInformacionPaciente();
        limpiarValidar();

        FormularioUtil.validarCamposObligatorios(
                tbxEnfermedad_actual, tbxMotivo_consulta, tbxRelaciones,
                tbxSituaciones, tbxMedicos, tbxQuirurguicos, tbxAlerguicos,
                tbxHospitalizacion, tbxTraumatico, tbxReligion,
                tbxAntecedentes_judiciales, tbxRevision_sistemas, dbxCardiaca,
                dbxRespiratoria, dbxPeso, dbxTalla, dbxPresion, dbxPresion2,
                dbxImc, tbxOtros_hallagos, tbxPlan_intervencion);

        boolean valida = receta_ripsAction.validarFormExterno();

        if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
            if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
                valida = macroAgudeza_visual.validarCamposObligatorios();
            }
        }

        if (valida) {
            valida = orden_servicioAction.validarFormExterno();
        }

        if (valida) {
            valida = remisiones_externasAction.validarRemision();
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

            List<Alteracion_joven> lista_datos = getServiceLocator()
                    .getAlteracion_jovenService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Alteracion_joven alteracion_joven : lista_datos) {
                rowsResultado.appendChild(crearFilas(alteracion_joven, this));
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

        final Alteracion_joven alteracion_joven = (Alteracion_joven) objeto;

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(alteracion_joven.getCodigo_historia() + ""));
        fila.appendChild(new Label(alteracion_joven.getIdentificacion() + ""));

        fila.appendChild(new Label(admision.getPaciente().getNombre1()
                + (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
                + admision.getPaciente().getNombre2()) + " "
                + admision.getPaciente().getApellido1() + " "
                + admision.getPaciente().getApellido2() + ""));

        Datebox datebox = new Datebox(alteracion_joven.getFecha_inicial());
        datebox.setButtonVisible(false);
        datebox.setFormat("yyyy-MM-dd");
        datebox.setWidth("98%");
        datebox.setReadonly(true);
        fila.appendChild(datebox);

        fila.appendChild(new Label(alteracion_joven.getTipo_historia().equals(
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
                        mostrarDatos(alteracion_joven);
                    }
                });
        hlayout.appendChild(btn_mostrar);

        fila.appendChild(hlayout);
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
                                            eliminarDatos(alteracion_joven);
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

    public Alteracion_joven getBean() {
        Alteracion_joven alteracion_joven = new Alteracion_joven();
        alteracion_joven.setCodigo_empresa(empresa.getCodigo_empresa());
        alteracion_joven.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        alteracion_joven.setCodigo_historia(infoPacientes.getCodigo_historia());
        alteracion_joven.setVia_ingreso(via_ingreso);
        alteracion_joven.setIdentificacion(admision.getNro_identificacion());
        alteracion_joven.setFecha_inicial(new Timestamp(infoPacientes
                .getFecha_inicial().getTime()));
        alteracion_joven
                .setUltimo_update(new Timestamp((new Date()).getTime()));
        alteracion_joven.setNro_inscripcion(tbxNro_inscripcion.getValue());
        alteracion_joven.setNro_ingreso(admision.getNro_ingreso());
        alteracion_joven.setEnfermedad_actual(tbxEnfermedad_actual.getValue());
        alteracion_joven.setMotivo_consulta(tbxMotivo_consulta.getValue());
        alteracion_joven.setAdolecencia(rdbAdolecencia.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setLee(rdbLee.getSelectedItem().getValue().toString());
        alteracion_joven.setDependecia(rdbDependecia.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setPersonas_cargo(rdbPersonas_cargo.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setPersonas_cuidado(rdbPersonas_cuidado
                .getSelectedItem().getValue().toString());
        alteracion_joven.setCuantas_quienes(tbxCuantas_quienes.getValue());
        alteracion_joven.setOcupacion_padre(tbxOcupacion_padre.getValue());
        alteracion_joven.setOcupacion_madre(tbxOcupacion_madre.getValue());
        alteracion_joven.setEducativo_madre(tbxEducativo_madre.getValue());
        alteracion_joven.setEducativo_padre(tbxEducativo_padre.getValue());
        alteracion_joven.setRelaciones(tbxRelaciones.getValue());
        alteracion_joven.setSituaciones(tbxSituaciones.getValue());
        alteracion_joven.setNecrologicos(chbNecrologicos.isChecked());
        alteracion_joven.setCual_necrologico(tbxCual_necrologico.getValue());
        alteracion_joven.setMentales(chbMentales.isChecked());
        alteracion_joven.setCual_mentales(tbxCual_mentales.getValue());
        alteracion_joven.setAlcoholismo(chbAlcoholismo.isChecked());
        alteracion_joven.setDrogadiccion(chbDrogadiccion.isChecked());
        alteracion_joven.setCual_alcoholismo(tbxCual_alcoholismo.getValue());
        alteracion_joven.setTabaquismo(rdbTabaquismo.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setActivopasivo(rdbActivopasivo.getSelectedItem()
                .getValue().toString());

        if (dtbxFecha_hasta.getValue() != null) {
            alteracion_joven.setFecha_hasta(new Timestamp(dtbxFecha_hasta
                    .getValue().getTime()));
        }
        alteracion_joven
                .setNo_cigarros((ibxNo_cigarros.getValue() != null ? ibxNo_cigarros
                        .getValue() : 0));
        alteracion_joven.setCancer(chbCancer.isChecked());
        alteracion_joven.setCual_tabaquismo(tbxCual_tabaquismo.getValue());
        alteracion_joven.setDislipidemia(chbDislipidemia.isChecked());
        alteracion_joven
                .setQuien_dislipidemia(tbxQuien_dislipidemia.getValue());
        alteracion_joven.setDiabetes(chbDiabetes.isChecked());
        alteracion_joven.setQuien_diabetes(tbxQuien_diabetes.getValue());
        alteracion_joven.setHipertencion(chbHipertencion.isChecked());
        alteracion_joven
                .setQuien_hepertencion(tbxQuien_hepertencion.getValue());
        alteracion_joven.setHipertencion(chbCancer1.isChecked());
        alteracion_joven.setQuien_hepertencion(tbxQuien_cancer1.getValue());
        alteracion_joven.setMedicos(tbxMedicos.getValue());
        alteracion_joven.setQuirurguicos(tbxQuirurguicos.getValue());
        alteracion_joven.setAlerguicos(tbxAlerguicos.getValue());
        alteracion_joven.setHospitalizacion(tbxHospitalizacion.getValue());
        alteracion_joven.setTraumatico(tbxTraumatico.getValue());
        alteracion_joven.setDiscapacidad(rdbDiscapacidad.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setCual_discapacidad(tbxCual_discapacidad.getValue());
        alteracion_joven.setAlcoholismo_personaal(chbAlcoholismo_personaal
                .isChecked());
        alteracion_joven.setDrogadiccion_personal(chbDrogadiccion_personal
                .isChecked());
        alteracion_joven.setTabaquismo_personal(rdbTabaquismo_personal
                .getSelectedItem().getValue().toString());
        alteracion_joven.setActivopasivo2(rdbActivopasivo2.getSelectedItem()
                .getValue().toString());

        if (dtbxFecha_hasta2.getValue() != null) {
            alteracion_joven.setFecha_hasta2(new Timestamp(dtbxFecha_hasta2
                    .getValue().getTime()));
        }
        alteracion_joven
                .setNo_cigarros2((ibxNo_cigarros2.getValue() != null ? ibxNo_cigarros2
                        .getValue() : 0));

        alteracion_joven.setCual_personal(tbxCual_personal.getValue());
        alteracion_joven.setEjercicio_fisico(rdbEjercicio_fisico
                .getSelectedItem().getValue().toString());
        alteracion_joven.setCual_ejercicio(tbxCual_ejercicio.getValue());
        alteracion_joven
                .setFrecuencia((ibxFrecuencia.getValue() != null ? ibxFrecuencia
                        .getValue() + ""
                        : ""));
        alteracion_joven
                .setIntensidad((ibxIntensidad.getValue() != null ? ibxIntensidad
                        .getValue() + ""
                        : ""));
        alteracion_joven.setGrupo_juvenil(rdbGrupo_juvenil.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setCual_grupo(tbxCual_grupo.getValue());
        alteracion_joven.setEquipo_deportivo(rdbEquipo_deportivo
                .getSelectedItem().getValue().toString());
        alteracion_joven.setCual_equipo(tbxCual_equipo.getValue());
        alteracion_joven.setReligion(tbxReligion.getValue());
        alteracion_joven.setVida_sexual(rdbVida_sexual.getSelectedItem()
                .getValue().toString());
        alteracion_joven
                .setEdad_inicio((ibxEdad_inicio.getValue() != null ? ibxEdad_inicio
                        .getValue() + ""
                        : ""));
        alteracion_joven
                .setNo_parejas((ibxNo_parejas.getValue() != null ? ibxNo_parejas
                        .getValue() + ""
                        : ""));
        alteracion_joven.setPromiscuidad(rdbPromiscuidad.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setEts(rdbEts.getSelectedItem().getValue().toString());
        alteracion_joven.setCual_ets(tbxCual_ets.getValue());
        alteracion_joven
                .setObstetricos_g((ibxObstetricos_g.getValue() != null ? ibxObstetricos_g
                        .getValue() + ""
                        : ""));
        alteracion_joven
                .setObstetricos_p((ibxObstetricos_p.getValue() != null ? ibxObstetricos_p
                        .getValue() + ""
                        : ""));
        alteracion_joven
                .setObstetricos_a((ibxObstetricos_a.getValue() != null ? ibxObstetricos_a
                        .getValue() + ""
                        : ""));
        alteracion_joven
                .setObstetricos_c((ibxObstetricos_c.getValue() != null ? ibxObstetricos_c
                        .getValue() + ""
                        : ""));
        alteracion_joven
                .setNo_hijos((ibxNo_hijos.getValue() != null ? ibxNo_hijos
                        .getValue() + "" : ""));

        if (dtbxFum.getValue() != null) {
            alteracion_joven
                    .setFum(new Timestamp(dtbxFum.getValue().getTime()));
        }

        /*
         * alteracion_joven.setTendencias_sexuales(tbxTendencias_sexuales
         * .getValue());
         */
        alteracion_joven.setPreservativos(chbPreservativos.isChecked());
        alteracion_joven.setAnticonceptivos(chbAnticonceptivos.isChecked());
        alteracion_joven.setCual_preservativo(tbxCual_preservativo.getValue());
        alteracion_joven.setAbuso_sexual(chbAbuso_sexual.isChecked());
        alteracion_joven.setMaltrato_fisico(chbMaltrato_fisico.isChecked());
        alteracion_joven.setMaltrato_psicologico(chbMaltrato_psicologico
                .isChecked());
        alteracion_joven.setAntecedentes_judiciales(tbxAntecedentes_judiciales
                .getValue());
        alteracion_joven.setRevision_sistemas(tbxRevision_sistemas.getValue());
        alteracion_joven
                .setCardiaca((dbxCardiaca.getValue() != null ? dbxCardiaca
                        .getValue() + "" : ""));
        alteracion_joven
                .setRespiratoria((dbxRespiratoria.getValue() != null ? dbxRespiratoria
                        .getValue() + ""
                        : ""));
        alteracion_joven.setPeso((dbxPeso.getValue() != null ? dbxPeso
                .getValue() + "" : ""));
        alteracion_joven.setTalla((dbxTalla.getValue() != null ? dbxTalla
                .getValue() + "" : ""));
        alteracion_joven.setPresion((dbxPresion.getValue() != null ? dbxPresion
                .getValue() + "" : ""));
        alteracion_joven
                .setPresion2((dbxPresion2.getValue() != null ? dbxPresion2
                        .getValue() + "" : ""));
        alteracion_joven.setInd_masa((dbxImc.getValue() != null ? dbxImc
                .getValue() + "" : ""));
        alteracion_joven.setEstado_tanner(tbxEstado_tanner.getValue());
        alteracion_joven.setOtros_hallagos(tbxOtros_hallagos.getValue());
        alteracion_joven.setHb_hto(String.valueOf(chbHb_hto.isChecked()));
        alteracion_joven.setVdrl(String.valueOf(chbVdrl.isChecked()));
        alteracion_joven.setVih(String.valueOf(chbVih.isChecked()));
        alteracion_joven.setCitologia(String.valueOf(chbCitologia.isChecked()));
        alteracion_joven
                .setColesterol(String.valueOf(chbColesterol.getValue()));
        alteracion_joven.setPlan_intervencion(tbxPlan_intervencion.getValue());
        alteracion_joven.setCodigo_historia_anterior(null);
        alteracion_joven.setTipo_historia("");
        alteracion_joven.setCreacion_date(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        alteracion_joven.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        alteracion_joven.setCreacion_user(usuarios.getCodigo().toString());
        alteracion_joven.setDelete_date(null);
        alteracion_joven.setUltimo_user(usuarios.getCodigo().toString());
        alteracion_joven.setDelete_user(null);
        alteracion_joven.setTipo_historia(tipo_historia);
        // log.info(">>>>>>>>>>>>>>>>>>>>>>>>" + historia_anterior);
        if (historia_anterior != null) {
            alteracion_joven.setCodigo_historia_anterior(historia_anterior
                    .getCodigo_historia());
        }
        alteracion_joven.setCodigo_historia_anterior(codigo_historia_anterior);

        alteracion_joven.setImagen_corporal(rdbImagen_corporal
                .getSelectedItem().getValue().toString());
        alteracion_joven.setAutopercepcion(rdbAutopercepcion.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setReferente_adulto(rdbReferente_adulto
                .getSelectedItem().getValue().toString());
        alteracion_joven.setProyectos_de_vida(rdbProyectos_de_vida
                .getSelectedItem().getValue().toString());

        alteracion_joven.setObservaciones(tbxObservaciones1.getValue());

        alteracion_joven.setAceptacion(rdbAceptacion.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setNovio(rdbNovio.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setAmigos(rdbAmigos.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setActividad_grupal(rdbActividad_grupal
                .getSelectedItem().getValue().toString());
        if (ibxTelevision.getValue() != null) {
            alteracion_joven.setTelevision(ibxTelevision.getValue());
        }
        if (ibxDeporte.getValue() != null) {
            alteracion_joven.setTelevision(ibxDeporte.getValue());
        }

        alteracion_joven.setOtras_actividades(rdbOtras_actividades
                .getSelectedItem().getValue().toString());
        alteracion_joven.setCual(tbxCual.getValue());
        alteracion_joven.setObservaciones2(tbxObservaciones2.getValue());
        alteracion_joven
                .setActiva_pasiva2((rdbActiva_pasiva2.getSelectedItem() != null ? rdbActiva_pasiva2
                        .getSelectedItem().getValue().toString()
                        : ""));

        if (dtbxMenarca.getValue() != null) {
            alteracion_joven.setMenarca(new Timestamp(dtbxMenarca.getValue()
                    .getTime()));
        }
        if (dtbxEsperma.getValue() != null) {
            alteracion_joven.setEsperma(new Timestamp(dtbxEsperma.getValue()
                    .getTime()));
        }
        alteracion_joven.setHemoclasificacion_paraclinicos(String
                .valueOf(chbHemoclasificacion_paraclinicos.isChecked()));
        alteracion_joven.setHemoclasificacion_ordenados(String
                .valueOf(chbHemoclasificacion_ordenados.isChecked()));
        alteracion_joven.setHto(String.valueOf(chbHto.isChecked()));
        alteracion_joven.setTetano(String.valueOf(chbTetano.isChecked()));
        alteracion_joven.setFiebre_amarilla(String.valueOf(chbFiebre_amarilla
                .isChecked()));
        alteracion_joven.setVph(String.valueOf(chbvph.isChecked()));
        alteracion_joven.setSarampion(String.valueOf(chbSarampion.isChecked()));
        alteracion_joven.setCiclos_regulares(rdbCiclos_regulares
                .getSelectedItem().getValue().toString());
        alteracion_joven.setDismenorrea(rdbDismenorrea.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setFlujo_patologico(rdbFlujo_patologico
                .getSelectedItem().getValue().toString());
        alteracion_joven.setEs_confiable(rdbEs_confiable.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setTrans_psicologicos(rdbTrans_psicologicos
                .getSelectedItem().getValue().toString());
        alteracion_joven.setAlcohol_droga(rdbAlcohol_droga.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setViolencia_intrafamili(rdbViolencia_intrafamili
                .getSelectedItem().getValue().toString());
        alteracion_joven.setMadre_adolesc(rdbMadre_adolesc.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setJudiciales(rdbJudiciales.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setOtros(rdbOtros.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setCual_otro(tbxCual_otro.getValue());
        alteracion_joven
                .setObservaciones_antec_famili(tbxObservaciones_antec_famili
                        .getValue());
        alteracion_joven.setConvive_madre(chbConvive_madre.isChecked() ? "S"
                : "N");
        alteracion_joven.setConvive_padre(chbConvive_padre.isChecked() ? "S"
                : "N");
        alteracion_joven
                .setConvive_madrasta(chbConvive_madrasta.isChecked() ? "S"
                        : "N");
        alteracion_joven
                .setConvive_padrasto(chbConvive_padrasto.isChecked() ? "S"
                        : "N");
        alteracion_joven
                .setConvive_hermanos(chbConvive_hermanos.isChecked() ? "S"
                        : "N");
        alteracion_joven.setConvive_pareja(chbConvive_pareja.isChecked() ? "S"
                : "N");
        alteracion_joven.setConvive_hijo(chbConvive_hijo.isChecked() ? "S"
                : "N");
        alteracion_joven.setConvive_otros(chbConvive_otros.isChecked() ? "S"
                : "N");
        alteracion_joven.setConvive_cual(tbxConvive_cual.getValue());
        alteracion_joven.setVive_instituto(lbxVive_instituto.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setPercepcion_familia(lbxPercepcion_familia
                .getSelectedItem().getValue().toString());
        alteracion_joven.setEnergia_electrica(rdbEnergia_electrica
                .getSelectedItem().getValue().toString());
        alteracion_joven.setAgua_hogar(rdbAgua_hogar.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setNumero_cuartos((String.valueOf(ibxNumero_cuartos
                .getValue() != null ? ibxNumero_cuartos.getValue() : 0)));
        alteracion_joven.setObservaciones_vivienda(tbxObservaciones_vivienda
                .getValue());
        alteracion_joven.setEstudia(rdbEstudia.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setGrado_cursado((String.valueOf(ibxGrado_cursado
                .getValue() != null ? ibxGrado_cursado.getValue() : 0)));
        alteracion_joven.setAnios_aprobados((String.valueOf(ibxAnios_aprobados
                .getValue() != null ? ibxAnios_aprobados.getValue() : 0)));
        alteracion_joven.setProblemas_escuela(rdbProblemas_escuela
                .getSelectedItem().getValue().toString());
        alteracion_joven.setAnios_repetidos((String.valueOf(ibxAnios_repetidos
                .getValue() != null ? ibxAnios_repetidos.getValue() : 0)));
        alteracion_joven.setCausas_anios_repetidos(tbxCausas_anios_repetidos
                .getValue());
        alteracion_joven.setDesercion(rdbDesercion.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setCausa_desercion(tbxCausa_desercion.getValue());
        alteracion_joven.setEdu_formal(rdbEdu_formal.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setCual_edu_formal(tbxCual_edu_formal.getValue());
        alteracion_joven.setObservaciones_edu(tbxObservaciones_edu.getValue());
        alteracion_joven.setTrabajo_actividad(lbxTrabajo_actividad
                .getSelectedItem().getValue().toString());
        alteracion_joven
                .setEdad_inicio_trabajo((String.valueOf(ibxEdad_inicio_trabajo
                                .getValue() != null ? ibxEdad_inicio_trabajo.getValue()
                                : 0)));
        alteracion_joven.setTrabajo_horas((String.valueOf(ibxTrabajo_horas
                .getValue() != null ? ibxTrabajo_horas.getValue() : 0)));
        alteracion_joven.setHorario_trabajo(lbxHorario_trabajo
                .getSelectedItem().getValue().toString());
        alteracion_joven.setRazon_trabajo(lbxRazon_trabajo.getSelectedItem()
                .getValue().toString());
        alteracion_joven
                .setOtra_razon_trabajo(tbxOtra_razon_trabajo.getValue());
        alteracion_joven.setTrabajo_legalizado(lbxTrabajo_legalizado
                .getSelectedItem().getValue().toString());
        alteracion_joven.setTrabajo_insaludable(lbxTrabajo_insaludable
                .getSelectedItem().getValue().toString());
        alteracion_joven
                .setObservaciones_trab(tbxObservaciones_trab.getValue());
        alteracion_joven
                .setTipo_trab_relacion_escolar3(rdbTipo_trab_relacion_escolar3
                        .getSelectedItem().getValue().toString());
        alteracion_joven.setSuenio(rdbSuenio.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setAlimentacion(rdbAlimentacion.getSelectedItem()
                .getValue().toString());
        alteracion_joven
                .setComida((String.valueOf(ibxComida.getValue() != null ? ibxComida
                                .getValue() : 0)));
        alteracion_joven.setComida_familia((String.valueOf(ibxComida_familia
                .getValue() != null ? ibxComida_familia.getValue() : 0)));
        alteracion_joven
                .setEdad_inicio_tabaco((String.valueOf(ibxEdad_inicio_tabaco
                                .getValue() != null ? ibxEdad_inicio_tabaco.getValue()
                                : 0)));
        alteracion_joven
                .setEdad_inicio_alcohol((String.valueOf(ibxEdad_inicio_alcohol
                                .getValue() != null ? ibxEdad_inicio_alcohol.getValue()
                                : 0)));
        alteracion_joven.setConduce_vehiculo(rdbConduce_vehiculo
                .getSelectedItem().getValue().toString());
        alteracion_joven.setCual_vehiculo(tbxCual_vehiculo.getValue());
        alteracion_joven.setOrientacion_sexual(lbxOrientacion_sexual
                .getSelectedItem().getValue().toString());
        alteracion_joven.setObservaciones_habitos(tbxObservaciones_habitos
                .getValue());
        alteracion_joven.setOtro_toxico(rdbOtro_toxico.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setCual_toxico(tbxCual_toxico.getValue());
        alteracion_joven.setPiel(rdbPiel.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setCabeza(rdbCabeza.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setBoca_diente(rdbBoca_diente.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setCuello_tiroides(rdbCuello_tiroides
                .getSelectedItem().getValue().toString());
        alteracion_joven.setTorax_mamas(rdbTorax_mamas.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setCardio_pulmorar(rdbCardio_pulmorar
                .getSelectedItem().getValue().toString());
        alteracion_joven.setGenito_urinario(rdbGenito_urinario
                .getSelectedItem().getValue().toString());
        alteracion_joven.setAbdomen(rdbAbdomen.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setColumna(rdbColumna.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setExtremidades(rdbExtremidades.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setNeurologico(rdbNeurologico.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setAspecto_generar(rdbAspecto_generar
                .getSelectedItem().getValue().toString());
        alteracion_joven.setAgudeza_visual(rdbAgudeza_visual.getSelectedItem()
                .getValue().toString());
        alteracion_joven.setAgudeza_auditival(rdbAgudeza_auditival
                .getSelectedItem().getValue().toString());
        alteracion_joven.setTamizaje_cuello(lbxTamizaje_cuello
                .getSelectedItem().getValue().toString());
        alteracion_joven.setVolumen_tes_izq((String.valueOf(dbxVolumen_tes_izq
                .getValue() != null ? dbxVolumen_tes_izq.getValue() : 0.00)));
        alteracion_joven.setVolumen_tes_der((String.valueOf(dbxVolumen_tes_der
                .getValue() != null ? dbxVolumen_tes_der.getValue() : 0.00)));
        alteracion_joven.setConoce_corresponde(rdbConoce_corresponde
                .getSelectedItem().getValue().toString());

        alteracion_joven.setObservacione3(tbxObservacione3.getValue());

        // Jose
        alteracion_joven.setExcreta(rdbExcreta.getSelectedItem().getValue()
                .toString());
        alteracion_joven.setExamen_neurologico(rdbExamen_neurologico
                .getSelectedItem().getValue().toString());

        alteracion_joven.setObservaciones_cabeza(tbxObservaciones_cabeza
                .getValue());
        alteracion_joven.setObservaciones_general(tbxObservaciones_general
                .getValue());
        alteracion_joven.setObservaciones_visual(tbxObservaciones_visual
                .getValue());
        alteracion_joven.setObservaciones_cuello(tbxObservaciones_cuello
                .getValue());
        alteracion_joven.setObservaciones_cardiaco(tbxObservaciones_torax
                .getValue());
        alteracion_joven.setObservaciones_auditiva(tbxObservaciones_auditiva
                .getValue());
        alteracion_joven.setObservaciones_abdomen(tbxObservaciones_abdomen
                .getValue());
        alteracion_joven.setObservaciones_torax(tbxObservaciones_masas
                .getValue());
        alteracion_joven.setObservaciones_genito(tbxObservaciones_genito
                .getValue());
        alteracion_joven.setObservaciones_columna(tbxObservaciones_columna
                .getValue());
        alteracion_joven
                .setObservaciones_extremidades(tbxObservaciones_extremidades
                        .getValue());
        alteracion_joven
                .setObservaciones_piel(tbxObservaciones_piel.getValue());
        alteracion_joven
                .setObservaciones_neurologico(tbxObservaciones_neurologico
                        .getValue());
        alteracion_joven
                .setObservaciones_boca(tbxObservaciones_boca.getValue());

        return alteracion_joven;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {

            if (validarForm()) {
                FormularioUtil.setUpperCase(groupboxEditar);
                // Cargamos los componentes //

                Alteracion_joven alteracion_joven = getBean();

                Map<String, Object> datos = new HashMap<String, Object>();
                datos.put("historia_clinica", alteracion_joven);
                datos.put("admision", admision);
                datos.put("anio_periodo", anio);
                datos.put("listado_paraclinicos",
                        manejadorParaclinicos.obtenerResultados_paraclinicos());
                datos.put("accion", tbxAccion.getValue());
                datos.put("anio", anio);
                datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());

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

                datos.put("cobrar_agudeza", cobrar_agudeza);

                Agudeza_visual agudeza_visual = macroAgudeza_visual
                        .obtenerAgudezaVisual();
                agudeza_visual.setAnio(anio);
                datos.put("agudeza_visual", agudeza_visual);

                datos.put("impresion_diagnostica", impresion_diagnostica);
                Anexo9_entidad anexo9_entidad = remisiones_externasAction
                        .obtenerAnexo9();
                datos.put("anexo9", anexo9_entidad);
                datos.put("cita_seleccionada", cita);

                getServiceLocator().getAlteracion_jovenService().guardarDatos(
                        datos);

                mostrarDatosAutorizacion(datos);
                if (anexo9_entidad != null) {
                    remisiones_externasAction.setCodigo_remision(anexo9_entidad
                            .getCodigo());
                    remisiones_externasAction.getBotonImprimir().setDisabled(
                            false);
                }

                tbxAccion.setValue("modificar");
                infoPacientes.setCodigo_historia(alteracion_joven
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
        Alteracion_joven alteracion_joven = (Alteracion_joven) obj;
        try {
            this.nro_ingreso_admision = alteracion_joven.getNro_ingreso();
            infoPacientes.setCodigo_historia(alteracion_joven
                    .getCodigo_historia());
            infoPacientes.setFecha_inicio(alteracion_joven.getFecha_inicial());
            infoPacientes.setFecha_cierre(true,
                    alteracion_joven.getUltimo_update());
            tbxNro_inscripcion.setValue(alteracion_joven.getNro_inscripcion());

            onMostrarModuloRemisiones();
            initMostrar_datos(alteracion_joven);

            cargarInformacion_paciente();

            tbxEnfermedad_actual.setValue(alteracion_joven
                    .getEnfermedad_actual());
            tbxMotivo_consulta.setValue(alteracion_joven.getMotivo_consulta());
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

            tbxRelaciones.setValue(alteracion_joven.getRelaciones());
            tbxSituaciones.setValue(alteracion_joven.getSituaciones());

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

            tbxMedicos.setValue(alteracion_joven.getMedicos());
            tbxQuirurguicos.setValue(alteracion_joven.getQuirurguicos());
            tbxAlerguicos.setValue(alteracion_joven.getAlerguicos());
            tbxHospitalizacion.setValue(alteracion_joven.getHospitalizacion());
            tbxTraumatico.setValue(alteracion_joven.getTraumatico());
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
            tbxAntecedentes_judiciales.setValue(alteracion_joven
                    .getAntecedentes_judiciales());
            tbxRevision_sistemas.setValue(alteracion_joven
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

            tbxEstado_tanner.setValue(alteracion_joven.getEstado_tanner());
            tbxOtros_hallagos.setValue(alteracion_joven.getOtros_hallagos());

            /* relacionado 1 */

            /* relacionado 2 */

            /* relacionado 3 */
            chbHb_hto.setValue(alteracion_joven.getHb_hto());
            chbVdrl.setValue(alteracion_joven.getVdrl());
            chbVih.setValue(alteracion_joven.getVih());
            chbCitologia.setValue(alteracion_joven.getCitologia());
            chbColesterol.setValue(alteracion_joven.getColesterol());
            tbxPlan_intervencion.setValue(alteracion_joven
                    .getPlan_intervencion());
            Utilidades.seleccionarRadio(rdbImagen_corporal,
                    alteracion_joven.getImagen_corporal());
            Utilidades.seleccionarRadio(rdbAutopercepcion,
                    alteracion_joven.getAutopercepcion());
            Utilidades.seleccionarRadio(rdbReferente_adulto,
                    alteracion_joven.getReferente_adulto());
            Utilidades.seleccionarRadio(rdbProyectos_de_vida,
                    alteracion_joven.getProyectos_de_vida());
            tbxObservaciones1.setValue(alteracion_joven.getObservaciones());

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
            tbxObservaciones2.setValue(alteracion_joven.getObservaciones2());
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
            tbxCual_otro.setValue(alteracion_joven.getCual_otro());
            tbxObservaciones_antec_famili.setValue(alteracion_joven
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
            tbxObservaciones_vivienda.setValue(alteracion_joven
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
            tbxObservaciones_edu.setValue(alteracion_joven
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
            tbxObservaciones_trab.setValue(alteracion_joven
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
            tbxObservaciones_habitos.setValue(alteracion_joven
                    .getObservaciones_habitos());
            Utilidades.seleccionarRadio(rdbOtro_toxico,
                    alteracion_joven.getOtro_toxico());
            tbxCual_toxico.setValue(alteracion_joven.getCual_toxico());
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
            tbxObservacione3.setValue(alteracion_joven.getObsercacione3());
            Utilidades.seleccionarRadio(rdbConoce_corresponde,
                    alteracion_joven.getConoce_corresponde());
            tbxObservacione3.setValue(alteracion_joven.getObsercacione3());

            Utilidades.seleccionarListitem(lbxTamizaje_cuello,
                    alteracion_joven.getTamizaje_cuello());

            // Jose
            Utilidades.seleccionarRadio(rdbExcreta,
                    alteracion_joven.getExcreta());
            Utilidades.seleccionarRadio(rdbExamen_neurologico,
                    alteracion_joven.getExamen_neurologico());

            tipo_historia = alteracion_joven.getTipo_historia();

            cargarImpresionDiagnostica(alteracion_joven);

            cargarRemisionInterna(alteracion_joven);
            cargarAgudezaVisual(alteracion_joven);
			// calcularCoordenadas();

            // valida_admision = alteracion_joven.getNro_ingreso();
            onMostrarModuloFarmacologico();
            receta_ripsAction.obtenerBotonAgregar().setVisible(false);
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(false);

            cargarAnexo9_remision(alteracion_joven);

            if (!tbxAccion.getValue().equals(
                    OpcionesFormulario.MOSTRAR.toString())) {
                infoPacientes.setCodigo_historia(alteracion_joven
                        .getCodigo_historia());
                inicializarVista(tipo_historia);
            }

            tbxAccion.setText("modificar");
            // accionForm(true, tbxAccion.getText());

            inicializarVista(tipo_historia);
            groupboxConsulta.setVisible(false);
            groupboxEditar.setVisible(true);
            btGuardar.setVisible(false);

            btnImprimir.setVisible(true);

            FormularioUtil.mostrarObservaciones(rdbPiel, "2",
                    tbxObservaciones_piel, row1);
            FormularioUtil.mostrarObservaciones(rdbCabeza, "2",
                    tbxObservaciones_cabeza, row1);
            FormularioUtil.mostrarObservaciones(rdbBoca_diente, "2",
                    tbxObservaciones_boca, row1);
            FormularioUtil.mostrarObservaciones(rdbCuello_tiroides, "2",
                    tbxObservaciones_cuello, row2);
            FormularioUtil.mostrarObservaciones(rdbTorax_mamas, "2",
                    tbxObservaciones_masas, row2);
            FormularioUtil.mostrarObservaciones(rdbCardio_pulmorar, "2",
                    tbxObservaciones_torax, row2);
            FormularioUtil.mostrarObservaciones(rdbColumna, "2",
                    tbxObservaciones_columna, row3);
            FormularioUtil.mostrarObservaciones(rdbAbdomen, "2",
                    tbxObservaciones_abdomen, row3);
            FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2",
                    tbxObservaciones_genito, row3);
            FormularioUtil.mostrarObservaciones(rdbExtremidades, "2",
                    tbxObservaciones_extremidades, row4);
            FormularioUtil.mostrarObservaciones(rdbExamen_neurologico, "2",
                    tbxObservaciones_neurologico, row4);
            FormularioUtil.mostrarObservaciones(rdbAspecto_generar, "2",
                    tbxObservaciones_general, row4);
            FormularioUtil.mostrarObservaciones(rdbAgudeza_auditival, "2",
                    tbxObservaciones_auditiva, row5);
            FormularioUtil.mostrarObservaciones(rdbAgudeza_visual, "2",
                    tbxObservaciones_visual, row5);

            tbxObservaciones_cabeza.setValue(alteracion_joven
                    .getObservaciones_cabeza());
            tbxObservaciones_abdomen.setValue(alteracion_joven
                    .getObservaciones_abdomen());
            tbxObservaciones_neurologico.setValue(alteracion_joven
                    .getObservaciones_neurologico());

            tbxObservaciones_general.setValue(alteracion_joven
                    .getObservaciones_general());
            tbxObservaciones_masas.setValue(alteracion_joven
                    .getObservaciones_torax());

            tbxObservaciones_cuello.setValue(alteracion_joven
                    .getObservaciones_cuello());
            tbxObservaciones_genito.setValue(alteracion_joven
                    .getObservaciones_genito());

            tbxObservaciones_torax.setValue(alteracion_joven
                    .getObservaciones_cardiaco());
            tbxObservaciones_columna.setValue(alteracion_joven
                    .getObservaciones_columna());

            tbxObservaciones_visual.setValue(alteracion_joven
                    .getObservaciones_visual());
            tbxObservaciones_extremidades.setValue(alteracion_joven
                    .getObservaciones_extremidades());

            tbxObservaciones_auditiva.setValue(alteracion_joven
                    .getObservaciones_auditiva());
            tbxObservaciones_piel.setValue(alteracion_joven
                    .getObservaciones_piel());

            tbxObservaciones_boca.setValue(alteracion_joven
                    .getObservaciones_boca());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Messagebox.show("Este dato no se puede editar", "Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Alteracion_joven alteracion_joven = (Alteracion_joven) obj;
        try {
            alteracion_joven.setDelete_user(getUsuarios().getCodigo());
            int result = getServiceLocator().getAlteracion_jovenService()
                    .eliminar(alteracion_joven);
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

    public void buscarDx(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
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

	// public void selectedCodigo_educativo_joven(Listitem listitem) {
    // if (listitem.getValue() == null) {
    // tbxNombre_Educativo_joven.setValue("");
    // tbxEducativo_joven.setValue("");
    //
    // } else {
    // Nivel_educativo dato = (Nivel_educativo) listitem.getValue();
    // tbxNombre_Educativo_joven.setValue(dato.getNombre());
    // tbxEducativo_joven.setValue(dato.getId());
    //
    // }
    // tbxNombre_Educativo_joven.close();
    // }
    public void seleccion_radio(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            // System.Out.Println("" + radiogroup.getSelectedItem().getValue());

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
                        UtilidadesComponentes.listarElementosListbox(true,
                                false, getServiceLocator(),
                                ((Listbox) abstractComponent));

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
            // System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    public void seleccion_radio2(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            // System.Out.Println("" + radiogroup.getSelectedItem().getValue());

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
            // System.Out.Println("Excepcion loaded");
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
            // System.Out.Println("Excepcion loaded");
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
            // System.Out.Println("Excepcion loaded");
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
            // System.Out.Println("Excepcion loaded");
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
            // System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    @Override
    public void initHistoria() throws Exception {

        crearResultadosParaclinicos();
        macroImpresion_diagnostica.inicializarMacro(this, admision,
                IVias_ingreso.ALTERACION_JOVEN);
        macroAgudeza_visual.inicializarMacro(this, admision,
                IVias_ingreso.ALTERACION_JOVEN);
        macroRemision_interna.inicializarMacro(this, admision,
                IVias_ingreso.ALTERACION_JOVEN);
        macroRemision_interna.deshabilitarCheck(admision,
                IVias_ingreso.ALTERACION_JOVEN);

        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
            via_ingreso = IVias_ingreso.ALTERACION_JOVEN;
            accionForm(OpcionesFormulario.CONSULTAR, null);
            btnCancelar.setVisible(true);
        } else {
            if (admision != null) {
                // log.info("==> admision " + admision);
                toolbarbuttonPaciente_admisionado1.setLabel(admision
                        .getPaciente().getNombreCompleto());
                toolbarbuttonPaciente_admisionado2.setLabel(admision
                        .getPaciente().getNombreCompleto());

                if (admision.getAtendida()) {
                    opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;

                    Alteracion_joven alteracion_joven = new Alteracion_joven();
                    alteracion_joven.setCodigo_empresa(empresa
                            .getCodigo_empresa());
                    alteracion_joven.setCodigo_sucursal(sucursal
                            .getCodigo_sucursal());
                    alteracion_joven.setIdentificacion(admision
                            .getNro_identificacion());
                    alteracion_joven.setVia_ingreso(admision.getVia_ingreso());
                    alteracion_joven.setNro_ingreso(admision.getNro_ingreso());
                    alteracion_joven.setVia_ingreso(via_ingreso);

                    alteracion_joven = getServiceLocator()
                            .getAlteracion_jovenService().consultar(
                                    alteracion_joven);

                    if (alteracion_joven != null) {
                        accionForm(OpcionesFormulario.MOSTRAR, alteracion_joven);
                        infoPacientes.setCodigo_historia(alteracion_joven
                                .getCodigo_historia());
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
        if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
            if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
                cobrar_agudeza = true;
                rowAgudeza_visual.setVisible(true);
                macroAgudeza_visual.validarCamposObligatorios();

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

        Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(admision
                .getPaciente().getFecha_nacimiento());
        anios = mapa_edades.get("anios");

        infoPacientes.cargarInformacion(admision, this,
                new InformacionPacienteIMG() {

                    @Override
                    public void ejecutarProceso() {

                        Paciente paciente = admision.getPaciente();
                        // log.info("paciente" + paciente);

                        if (paciente.getSexo().toUpperCase().equals("F")) {

                            // log.info("1" + paciente.getSexo());
                            rowObstetricos.setVisible(true);
                            rowObstetricos2.setVisible(false);

                        } else {

                            // log.info("2" + paciente.getSexo());
                            rowObstetricos2.setVisible(true);
                            rowObstetricos.setVisible(false);
                        }

                        manejadorParaclinicos.cargarHistorial_resultados();

						// log.info("tbxAccion.getValue()  === > "
                        // + tbxAccion.getValue());
                        if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
                            Map<String, Object> parametros = new HashMap<String, Object>();
                            parametros.put("codigo_empresa", codigo_empresa);
                            parametros.put("codigo_sucursal", codigo_sucursal);
                            parametros.put("identificacion",
                                    admision.getNro_identificacion());
                            parametros.put("order_desc", "order_desc");

                            List<Alteracion_joven> listado_historias = getServiceLocator()
                            .getAlteracion_jovenService().listar(
                                    parametros);

                            if (!listado_historias.isEmpty()) {
                                inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
                                cargarInformacion_historia_anterior(listado_historias
                                        .get(0));
                                historia_anterior = listado_historias.get(0);
                                codigo_historia_anterior = historia_anterior
                                .getCodigo_historia();
                                toolbarbuttonTipo_historia
                                .setLabel("Creando Historia Clínica de Control/Evolucion");
                                admision.setPrimera_vez("N");
                            } else {
                                toolbarbuttonTipo_historia
                                .setLabel("Creando Historia Clínica por Primera Vez");
                                inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
                                admision.setPrimera_vez("S");
                            }
                        } else {
                            if (tipo_historia
                            .equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
                                Alteracion_joven alteracion_joven = new Alteracion_joven();
                                alteracion_joven.setCodigo_empresa(empresa
                                        .getCodigo_empresa());
                                alteracion_joven.setCodigo_sucursal(sucursal
                                        .getCodigo_sucursal());
                                alteracion_joven
                                .setCodigo_historia(codigo_historia_anterior);

                                alteracion_joven = getServiceLocator()
                                .getAlteracion_jovenService()
                                .consultar(alteracion_joven);

                                if (alteracion_joven != null) {
                                    cargarInformacion_historia_anterior(alteracion_joven);
                                }

                            }
                        }

                    }
                });
    }

    @Override
    public void cargarInformacion_historia_anterior(Object historia_anterior) {

    }

    @Override
    public void initMostrar_datos(Object historia_clinica) {
        Alteracion_joven alteracion_joven = (Alteracion_joven) historia_clinica;

        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {

            FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
                    idsExcluyentes);

            if (alteracion_joven.getTipo_historia().equals(
                    IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
                toolbarbuttonTipo_historia
                        .setLabel("Mostrando Historia Clínica por Primera Vez");
            } else {
                toolbarbuttonTipo_historia
                        .setLabel("Mostrando Historia Clínica de Control/Evolucion");
            }

        } else {

            if (alteracion_joven.getTipo_historia().equals(
                    IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
                toolbarbuttonTipo_historia
                        .setLabel("Modificando Historia Clínica por Primera Vez");
            } else {
                toolbarbuttonTipo_historia
                        .setLabel("Modificando Historia Clínica de Control/Evolucion");
            }

        }

        codigo_historia_anterior = alteracion_joven
                .getCodigo_historia_anterior();
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
            // System.Out.Println("Excepcion loaded");
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

    private void cargarRemisionInterna(Alteracion_joven alteracion_joven)
            throws Exception {
        Remision_interna remision_interna = new Remision_interna();
        remision_interna.setCodigo_historia(alteracion_joven
                .getCodigo_historia());
        remision_interna
                .setCodigo_empresa(alteracion_joven.getCodigo_empresa());
        remision_interna.setCodigo_sucursal(alteracion_joven
                .getCodigo_sucursal());
        remision_interna = getServiceLocator().getServicio(
                Remision_internaService.class).consultar(remision_interna);

        macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
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
        macroImpresion_diagnostica.mostrarImpresionDiagnostica(
                impresion_diagnostica, true);
    }

    public void deshabilitar_conRadio(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            String valor = "S";
            FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup,
                    valor, abstractComponents);

        } catch (Exception e) {
            // System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    private void cargarAnexo9_remision(Alteracion_joven alteracion_joven) {
        Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
        anexo9_entidad.setCodigo_empresa(codigo_empresa);
        anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
        anexo9_entidad.setNro_historia(alteracion_joven.getCodigo_historia());
        anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
        anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
                .consultar(anexo9_entidad);
        // log.info("cargando anexo9_entidad === >" + anexo9_entidad);
        remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
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
        informacion_clinica.append("\t").append(tbxMotivo_consulta.getValue())
                .append("\n");

        informacion_clinica.append("\n");

        informacion_clinica.append("- ENFERMEDAD ACTUAL :");
        if (tbxEnfermedad_actual.getValue() != null
                && !tbxEnfermedad_actual.getValue().isEmpty()) {
            informacion_clinica.append("\t")
                    .append(tbxEnfermedad_actual.getValue()).append("\n")
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

        Map<String, Object> mapReceta = receta_ripsAction.obtenerDatos();

        List<Detalle_receta_rips> listaDetalles_horarios = (List<Detalle_receta_rips>) mapReceta
                .get("lista_detalle");
        // log.info("=====>lista detalle " + listaDetalles_horarios);

        if (!listaDetalles_horarios.isEmpty()) {
            informacion_clinica.append("-RECETA:").append("\t");

            for (Detalle_receta_rips detalle_receta_rips : listaDetalles_horarios) {
                Articulo articulo = new Articulo();
                articulo.setCodigo_empresa(detalle_receta_rips
                        .getCodigo_empresa());
                articulo.setCodigo_sucursal(detalle_receta_rips
                        .getCodigo_sucursal());
                articulo.setCodigo_articulo(detalle_receta_rips
                        .getCodigo_articulo());

                articulo = getServiceLocator().getArticuloService().consultar(
                        articulo);
                // log.info("====>articulo " + articulo);
                informacion_clinica
                        .append(articulo != null ? articulo.getCantidad() : "")
                        .append("\t")
                        .append(articulo != null ? articulo.getNombre1() : "")
                        .append("\t");

            }

            informacion_clinica.append("\n:");

            Map<String, Object> mapOrdenamiento = orden_servicioAction
                    .obtenerDatos();
            // log.info("mapa ordenamiento ===> " + mapOrdenamiento);
            List<Detalle_orden> listaDetalle_orden = (List<Detalle_orden>) mapOrdenamiento
                    .get("lista_detalle");
            // log.info("====> lista orden " + listaDetalle_orden);
            if (!listaDetalle_orden.isEmpty()) {
                informacion_clinica.append("-ORDENES:").append("\t");

                Procedimientos procedimiento = new Procedimientos();

                for (Detalle_orden detalle_orden : listaDetalle_orden) {
                    procedimiento.setId_procedimiento(new Long(detalle_orden
                            .getCodigo_procedimiento()));

                    procedimiento = getServiceLocator()
                            .getProcedimientosService()
                            .consultar(procedimiento);
                    // log.info("=====> soat " + procedimiento);
                    informacion_clinica
                            .append(procedimiento != null ? procedimiento
                                    .getDescripcion() : "")
                            .append("- ")
                            .append(detalle_orden != null ? detalle_orden
                                    .getUnidades() : "").append("\t");
                }
            }

        }

        return informacion_clinica.toString();

    }

    public void validarParaclinicosRealizados() {

    }

    public void validar() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        // log.info("codigo_empresa" + codigo_empresa);
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

        // log.info("list" + list);
        boolean validacion_hb = false;
        boolean validacion_hto = false;
        boolean validacion_citologia = false;
        boolean validacion_hemoclasificacion = false;

        if (list != null) {

            for (Map<String, Object> map : list) {
                // log.info("1----" + map);
                int age = (Integer) map.get("age");
                String codigo_examen = (String) map.get("codigo_examen");
                // log.info("2----" + age);

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
					// log.info("---------" + edad + "---------" + age
                    // + "---------" + sexo);
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
            // log.info("5-----");
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

    /*
     * public void imprimir(){ try { String nro_historia =
     * infoPacientes.getCodigo_historia(); if (nro_historia.equals("")) {
     * Messagebox.show("La historia no se ha guardado aún", "Informacion ..",
     * Messagebox.OK, Messagebox.INFORMATION); return; }
     * 
     * Map paramRequest = new HashMap(); paramRequest.put("name_report",
     * "Historia"); paramRequest.put("dir_reporte",
     * IInformacionReporte.DIR_ALTERACION_JOVEN);
     * paramRequest.put("nro_subreport", IInformacionReporte.NUMERO_SUB_REPORT);
     * paramRequest.put("nombre_reporte",
     * IInformacionReporte.NOMBRE_REPORTE_ALTERACION_JOVEN);
     * paramRequest.put("nro_historia", nro_historia);
     * 
     * 
     * Paciente paciente = new Paciente();
     * paciente.setCodigo_empresa(admision.getCodigo_empresa());
     * paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
     * paciente.setNro_identificacion(admision.getNro_identificacion());
     * paciente =
     * getServiceLocator().getServicio(PacienteService.class).consultar
     * (paciente);
     * 
     * Usuarios usuarios = new Usuarios();
     * usuarios.setCodigo_empresa(admision.getCodigo_empresa());
     * usuarios.setCodigo_sucursal(admision.getCodigo_sucursal());
     * usuarios.setCodigo(admision.getCodigo_medico()); usuarios =
     * getServiceLocator
     * ().getServicio(UsuariosService.class).consultar(usuarios);
     * 
     * 
     * Map<String, Object> map = MapeadorDeComponentesUtil.getMapa(this);
     * map.put("hisc_consulta_externa_fecha_ingreso",
     * admision.getFecha_ingreso());
     * map.put("hisc_consulta_externa_nro_identificacion", "" +
     * admision.getNro_identificacion()); map.put("paciente_fecha_nacimiento",
     * paciente != null ? paciente.getFecha_nacimiento() : null);
     * map.put("paciente_nombre1", paciente != null ?
     * paciente.getNombreCompleto() : ""); map.put("nro_historia",
     * nro_historia); map.put("sexo", paciente != null ? paciente.getSexo() :
     * ""); map.put("hisc_consulta_externa_codigo_prestador", usuarios != null ?
     * usuarios.getCodigo() : ""); map.put("nombre_prestador", usuarios != null
     * ? usuarios.getNombres() : ""); map.put("apellidos_prestador", usuarios !=
     * null ? usuarios.getApellidos() : "");
     * 
     * map.put("mostrar_tanner", rowEstadioTanner1.isVisible());
     * 
     * map.put("firma", usuarios != null ? new
     * ByteArrayInputStream(usuarios.getFirma()) : null);
     * 
     * Prestadores prestadores = new Prestadores();
     * prestadores.setCodigo_empresa(admision.getCodigo_empresa());
     * prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
     * prestadores.setNro_identificacion(admision.getCodigo_medico());
     * prestadores =
     * getServiceLocator().getServicio(PrestadoresService.class).consultar
     * (prestadores); map.put("registro_medico", prestadores != null ?
     * prestadores.getRegistro_medico() : "");
     * 
     * infoPacientes.complementarInformacion(map);
     * macroImpresion_diagnostica.complementarInformacion(map);
     * macroTanner.complementarInformacion(map, paciente != null ?
     * paciente.getSexo() : ""); paramRequest.put("data", map);
     * 
     * Component componente = Executions.createComponents(
     * "/pages/printInformes.zul", this, paramRequest); final Window window =
     * (Window) componente; window.setMode("modal");
     * window.setMaximizable(true); window.setMaximized(true); } catch
     * (Exception e) { MensajesUtil.mensajeError(e, null, this); } }
     */
    public void imprimir() throws Exception {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        // log.info(">>>>>>>>>>>>>>@Imprimir");
        Long nro_historia = infoPacientes.getCodigo_historia();
        if (nro_historia != null) {
            String parametros_pagina = "?codigo_empresa=" + codigo_empresa;
            parametros_pagina += "&codigo_sucursal=" + codigo_sucursal;
            parametros_pagina += "&codigo_historia=" + nro_historia;
            parametros_pagina += "&nro_ingreso=" + admision.getNro_ingreso();
            parametros_pagina += "&nro_identificacion="
                    + admision.getNro_identificacion();
            Clients.evalJavaScript("window.open(\"" + request.getContextPath()
                    + "/pages/reports/alteracion_joven_reporte.zul"
                    + parametros_pagina + "\", '_blank');");
        }
    }
}
