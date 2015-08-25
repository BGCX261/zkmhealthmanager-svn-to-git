/*
 * odontologiaAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Consentimiento_inf_odon;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Indice_dean;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Consentimiento_inf_odonService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Remision_internaService;

import com.framework.res.CargardorDeDatos;
import com.framework.res.Funcion_getEdad;
import com.framework.util.Util;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Consentimiento_inf_odonMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.macros.impl.ModuloConsultaIMG;
import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.IndiceDental;
import com.framework.macros.odontograma.OdontogramaMacro;
import com.framework.macros.odontograma.OdontogramaMacro.DienteOdontograma;
import com.framework.macros.odontograma.OdontogramaMacro.IndiceDean;
import com.framework.macros.odontograma.OdontogramaMacro.noClear;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Odontologia2Action extends HistoriaClinicaModel implements
        IHistoria_generica {

    private static Logger log = Logger.getLogger(Odontologia2Action.class);
    private static final long serialVersionUID = -9145887024839938515L;

    private Orden_servicio_internoAction orden_servicioAction;

    /* Servicios */
    private ElementoService elementoService;

    // Manuel
    private Integer cantidad_detartraje = 0;
    @View
    private Textbox tbxCant_sellante;
    private List<Map<String, Object>> lista_procedimientos;
    @View
    private Radio Sintoma_respiratorio1;
    @View
    private Radio Sintoma_respiratorio2;
    @View
    private Radio Sintoma_piel1;
    @View
    private Radio Sintoma_piel2;
    @View
    private Textbox tbxDesc_hblar_tema;
    @View
    private Textbox tbxCual_riesgo;
    @View
    private Row rowHablar_tema;
    @View
    private Row rowRiesgo;
    @View
    private Row rowTipo_violencia;
    @View
    private Doublebox dbxPresion;
    @View
    private Doublebox dbxPresion2;
    @View
    private Doublebox dbxTalla;
    @View
    private Doublebox dbxPeso;
    @View
    private Textbox ibxCirugia_num_diente;
    @View
    private Textbox ibxEndodoncia_num_diente;
    @View
    private Textbox ibxProtodoncia_num_diente;
    @View
    private Textbox ibxNum_diente_traAmalgama;
    @View
    private Textbox ibxNro_diente_trRecinas;
    @View
    private Textbox ibxNro_diente_traIonimeros;
    @View
    private Div divModuloOrdenamiento;

    // -------manuel------------------------
    @View(isMacro = true)
    private Remision_internaMacro macroRemision_interna;
    @View(isMacro = true)
    private Consentimiento_inf_odonMacro macroConsentimiento_inf_odon;
    @View
    private Div divModuloRemisiones_externas;
    private Remisiones_externasAction remisiones_externasAction;
    public String tipo_historia;
    @View(isMacro = true)
    private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
    @View
    private Listbox lbxAtendida;
    private Admision admision;
    private String via_ingreso;
    @View
    private Toolbarbutton toolbarbuttonPaciente_admisionado1;
    @View
    private Toolbarbutton toolbarbuttonPaciente_admisionado2;
    private Opciones_via_ingreso opciones_via_ingreso;
    // @View
    @View
    private Groupbox groupboxConsulta;
    @View(isMacro = true)
    private InformacionPacientesMacro infoPacientes;
    @View
    private Toolbarbutton toolbarbuttonTipo_historia;

    @View
    private Listbox lbxParameter;
    @View
    private Textbox tbxValue;
    @View
    private Rows rowsResultado;
    @View
    private Grid gridResultado;
    @View
    private Textbox tbxNro_inscripcion;

	// ---------------------------------------
    @View
    @noClear
    private Textbox tbxAccion;
    @View
    private Groupbox groupboxEditar;

    @View
    private Bandbox tbxCodigo_prestador1;
    @View
    private Textbox tbxNomPrestador;

    @View
    private Textbox tbxAcompaniante;
    @View
    private Listbox lbxRelacion;
    @View
    private Doublebox ibxTel_acompaniante;
    @View
    private Textbox tbxMotivo_consulta;
    @View
    private Textbox tbxEnfermedad_actual;

    // este es el contenedor cuando el paciente tiene de 2 - 5 aÃ±os.
    @View
    private Groupbox groupVerificarSaludBucal;

    @View
    private Radiogroup rdbAnam_tratamiento;
    @View
    private Textbox tbxAnam_cual_tratamiento;
    @View
    private Radiogroup rdbAnam_toma_medicamentos;
    @View
    private Textbox tbxAnam_cual_toma_medicamentos;
    @View
    private Radiogroup rdbAnam_alergias;
    @View
    private Textbox tbxAnam_cual_alergias;
    @View
    private Radiogroup rdbAnam_cardiopatias;
    @View
    private Textbox tbxAnam_cual_cardiopatias;
    @View
    private Radiogroup rdbAnam_alteracion_presion;
    @View
    private Textbox tbxAnam_cual_alteracion_presion;
    @View
    private Radiogroup rdbAnam_embarazo;
    @View
    private Textbox tbxAnam_cual_embarazo;
    @View
    private Radiogroup rdbAnam_diabetes;
    @View
    private Listbox lbxAnam_cual_diabetes;
    @View
    private Radiogroup rdbAnam_hepatitis;
    @View
    private Listbox lbxAnam_cual_hepatitis;
    @View
    private Radiogroup rdbAnam_irradiaciones;
    @View
    private Textbox tbxAnam_cual_irradiaciones;
    @View
    private Radiogroup rdbAnam_discracias;
    @View
    private Textbox tbxAnam_cual_discracias;
    @View
    private Radiogroup rdbAnam_fiebre_reumatica;
    @View
    private Textbox tbxAnam_cual_fiebre_reumatica;
    @View
    private Radiogroup rdbAnam_enfermedad_renal;
    @View
    private Textbox tbxAnam_cual_enfermedad_renal;
    @View
    private Radiogroup rdbAnam_inmunosupresion;
    @View
    private Textbox tbxAnam_cual_inmunosupresion;
    @View
    private Radiogroup rdbAnam_trastornos;
    @View
    private Textbox tbxAnam_cual_trastornos;
    @View
    private Radiogroup rdbAnam_patologia;
    @View
    private Textbox tbxAnam_cual_patologia;
    @View
    private Radiogroup rdbAnam_trastornos_gastricos;
    @View
    private Textbox tbxAnam_cual_trastornos_gastricos;
    @View
    private Radiogroup rdbAnam_epilepsia;
    @View
    private Textbox tbxAnam_cual_epilepsia;
    @View
    private Radiogroup rdbAnam_cirugias;
    @View
    private Textbox tbxAnam_cual_cirugias;
    @View
    private Radiogroup rdbAnam_protasis;
    @View
    private Listbox lbxAnam_cual_protasis;
    @View
    private Radiogroup rdbAnam_otro;
    @View
    private Textbox tbxAnam_cual_otros;

    @View
    private Radiogroup rdbSintoma_respiratorio;
    @View
    private Radiogroup rdbSintoma_piel;

    @View
    private Listbox lbxAnte_fam_hipertension;
    @View
    private Listbox lbxAnte_fam_ecv;
    @View
    private Listbox lbxAnte_fam_enf_coronaria;
    @View
    private Listbox lbxAnte_fam_muerte_im_acv;
    @View
    private Listbox lbxAnte_fam_dislipidemia;
    @View
    private Listbox lbxAnte_fam_nefropatias;
    @View
    private Listbox lbxAnte_fam_obesos;
    @View
    private Listbox lbxAnte_fam_diabetes;
    @View
    private Listbox lbxAnte_fam_enf_mental;
    @View
    private Listbox lbxAnte_fam_cancer;
    @View
    private Listbox lbxAnte_fam_hematologia;
    @View
    private Listbox lbxAnte_fam_otros;
    @View
    private Textbox tbxAnte_fam_estecifique;
    @View
    private Textbox tbxAnte_fam_observaciones;

    @View
    private Radiogroup rdbEsto_labio_sup;
    @View
    private Textbox tbxEsto_cual_labio_sup;
    @View
    private Radiogroup rdbEsto_labio_inf;
    @View
    private Textbox tbxEsto_cual_labio_inf;
    @View
    private Radiogroup rdbEsto_comisura;
    @View
    private Textbox tbxEsto_cual_comisura;
    @View
    private Radiogroup rdbEsto_mucosa;
    @View
    private Textbox tbxEsto_cual_mucosa;
    @View
    private Radiogroup rdbEsto_surcos;
    @View
    private Textbox tbxEsto_cual_surcos;
    @View
    private Radiogroup rdbEsto_frenillo;
    @View
    private Textbox tbxEsto_cual_frenillo;
    @View
    private Radiogroup rdbEsto_paladar_blando;
    @View
    private Textbox tbxEsto_cual_paladar_blando;
    @View
    private Radiogroup rdbEsto_paladar_duro;
    @View
    private Textbox tbxEsto_cual_paladar_duro;
    @View
    private Radiogroup rdbEsto_orofaringe;
    @View
    private Textbox tbxEsto_cual_orofaringe;
    @View
    private Radiogroup rdbEsto_lengua;
    @View
    private Textbox tbxEsto_cual_lengua;
    @View
    private Radiogroup rdbEsto_boca;
    @View
    private Textbox tbxEsto_cual_boca;
    @View
    private Radiogroup rdbEsto_reborde;
    @View
    private Textbox tbxEsto_cual_reborde;
    @View
    private Radiogroup rdbEsto_salivares;
    @View
    private Textbox tbxEsto_cual_salivares;
    @View
    private Radiogroup rdbEsto_dolor_muscular;
    @View
    private Textbox tbxEsto_cual_dolor_muscular;
    @View
    private Radiogroup rdbEsto_dolor_articular;
    @View
    private Textbox tbxEsto_cual_dolor_articular;
    @View
    private Radiogroup rdbEsto_riudo_articular;
    @View
    private Textbox tbxEsto_cual_ruido_articular;
    @View
    private Radiogroup rdbEsto_alt_mov;
    @View
    private Textbox tbxEsto_cual_alt_mov;
    @View
    private Radiogroup rdbEsto_oclusion;
    @View
    private Textbox tbxEsto_cual_oclusion;
    @View
    private Radiogroup rdbEsto_bruxismo;
    @View
    private Textbox tbxEsto_cual_bruxismo;
    @View
    private Radiogroup rdbEsto_forma;
    @View
    private Textbox tbxEsto_cual_forma;
    @View
    private Radiogroup rdbEsto_tamano;
    @View
    private Textbox tbxEsto_cual_tamano;
    @View
    private Radiogroup rdbEsto_numero;
    @View
    private Textbox tbxEsto_cual_numero;
    @View
    private Radiogroup rdbEsto_color;
    @View
    private Textbox tbxEsto_cual_color;
    @View
    private Radiogroup rdbEsto_posicion;
    @View
    private Textbox tbxEsto_cual_posicion;
    @View
    private Radiogroup rdbEsto_impactados;
    @View
    private Textbox tbxEsto_cual_impactados;
    @View
    private Radiogroup rdbEsto_abfracciones;
    @View
    private Textbox tbxEsto_cual_abfracciones;
    @View
    private Radiogroup rdbEsto_inflamacion;
    @View
    private Textbox tbxEsto_cual_inflamacion;
    @View
    private Radiogroup rdbEsto_movilidad;
    @View
    private Textbox tbxEsto_cual_movilidad;
    @View
    private Radiogroup rdbEsto_recesiones;
    @View
    private Textbox tbxEsto_cual_recesiones;
    @View
    private Radiogroup rdbEsto_bolsas;
    @View
    private Textbox tbxEsto_cual_bolsas;
    @View
    private Radiogroup rdbEsto_calculo;
    @View
    private Textbox tbxEsto_cual_calculo;
    @View
    private Radiogroup rdbEsto_abceso;
    @View
    private Textbox tbxEsto_cual_abceso;
    @View
    private Radiogroup rdbEsto_sangrado;
    @View
    private Textbox tbxEsto_cual_sangrado;
    @View
    private Radiogroup rdbEsto_alt_vital;
    @View
    private Textbox tbxEsto_cual_alt_vital;
    @View
    private Radiogroup rdbEsto_percusion;
    @View
    private Textbox tbxEsto_cual_percusion;
    @View
    private Radiogroup rdbEsto_mov_dental;
    @View
    private Textbox tbxEsto_cual_mov_dental;
    @View
    private Radiogroup rdbEsto_fistula;
    @View
    private Textbox tbxEsto_cual_fistula;
    @View
    private Radiogroup rdbEsto_diente;
    @View
    private Textbox tbxEsto_cual_diente;
    @View
    private Radiogroup rdbEsto_oral;
    @View
    private Textbox tbxEsto_cual_oral;
    @View
    private Radiogroup rdbEsto_digital;
    @View
    private Textbox tbxEsto_cual_digital;
    @View
    private Radiogroup rdbEsto_lengua_protactil;
    @View
    private Textbox tbxEsto_cual_lengua_protactil;
    @View
    private Radiogroup rdbEsto_queilosfagia;
    @View
    private Textbox tbxEsto_cual_queilosfagia;
    @View
    private Radiogroup rdbEsto_fumador;
    @View
    private Textbox tbxEsto_cual_fumador;
    @View
    private Radiogroup rdbEsto_alcohol;
    @View
    private Textbox tbxEsto_cual_alcohol;
    @View
    private Radiogroup rdbEsto_otro;
    @View
    private Textbox tbxEsto_cual_otros;

    @View
    private Radiogroup rdbPrev_charla;
    @View
    private Textbox tbxPrev_frec_charla;
    @View
    private Radiogroup rdbPrev_cepillado;
    @View
    private Textbox tbxPrev_frec_cepillado;
    @View
    private Radiogroup rdbPrev_seda;
    @View
    private Textbox tbxPrev_frec_seda;
    @View
    private Radiogroup rdbPrev_enjuague;
    @View
    private Textbox tbxPrev_frec_enjuague;
    @View
    private Radiogroup rdbPrev_fluor;
    @View
    private Textbox tbxPrev_frec_fluor;
    @View
    private Radiogroup rdbPrev_sellante;
    @View
    private Textbox tbxPrev_frec_sellante;
    @View
    private Textbox tbxPrev_observacions;

    @View
    private Radiogroup rdbFluorisis;
    @View
    private Listbox lbxTipo_fluorisis;
    @View
    private Checkbox chbTra_educacion_oral;
    @View
    private Checkbox chbTra_control_placa;
    @View
    private Checkbox chbTra_profilaxis;
    @View
    private Checkbox chbSellante;
    @View
    private Checkbox chbFluor;
    @View
    private Checkbox chbTra_amalgamas;
    @View
    private Checkbox chbTra_resinas;
    @View
    private Checkbox chbTra_ionimeros;
    @View
    private Radiogroup rdbTra_endodoncia;

    @View
    private Radiogroup rdbTra_detartraje_supragingival;
    @View
    private Checkbox chbTra_i;
    @View
    private Checkbox chbTra_ii;
    @View
    private Checkbox chbTra_iii;
    @View
    private Checkbox chbTra_iv;
    @View
    private Radiogroup rdbTra_prostodoncia;
    @View
    private Listbox lbxTra_cirugia;
    @View
    private Textbox tbxRemision;
    @View
    private Textbox tbxPronostico;
    @View
    private Radiogroup rdbViolencia;
    @View
    private Radiogroup rdbFisico;
    @View
    private Radiogroup rdbSexual;
    @View
    private Radiogroup rdbEmocional;
    @View
    private Radiogroup rdbRiesgo;
    @View
    private Radiogroup rdbTema;

    @View
    private Listbox lbxFormato;

    /* aiepi */
    @View(classField = Odontologia.class, field = "aiepi_dolor_comer")
    private Radiogroup rdgaiepiDolorComer;
    @View(classField = Odontologia.class, field = "aiepi_dolor_dientes")
    private Radiogroup rdgaiepiDolorDientes;
    @View(classField = Odontologia.class, field = "aiepi_trauma_cara_coca")
    private Radiogroup rdgaiepiTraumaCaraBoca;
    @View(classField = Odontologia.class, field = "aiepi_caries_padres_her")
    private Radiogroup rdgaiepiCariesPadresHermanos;
    @View(classField = Odontologia.class, field = "aiepi_maninia")
    private Radiogroup rdgaiepiManania;
    @View(classField = Odontologia.class, field = "aiepi_medio_dia")
    private Radiogroup rdgaiepiMedioDia;
    @View(classField = Odontologia.class, field = "aiepi_noche")
    private Radiogroup rdgaiepiNoche;
    @View(classField = Odontologia.class, field = "aiepi_limpia_diente")
    private Radiogroup rdgaiepiLimpiaDientes;
    @View(classField = Odontologia.class, field = "aiepi_ninio_solo")
    private Radiogroup rdgaiepiNinioSolo;
    @View(classField = Odontologia.class, field = "aiepi_cepillo")
    private Radiogroup rdgaiepiCepillo;
    @View(classField = Odontologia.class, field = "aiepi_crema")
    private Radiogroup rdgaiepiCrema;
    @View(classField = Odontologia.class, field = "aiepi_seda")
    private Radiogroup rdgaiepiSeda;
    @View(classField = Odontologia.class, field = "aiepi_chupo_biberon")
    private Radiogroup rdgaiepiChupoBiberon;
    @View(classField = Odontologia.class, field = "aiepi_ultima_consulta_odontologica")
    private Datebox tbxaiepiUltimaConsultaOdontologica;
    @View(classField = Odontologia.class, field = "aiepi_observaciones")
    private Textbox tbxObservacionesAiepi;

    // Diagnosticos de aiepi
    @View(classField = Odontologia.class, field = "aiepi_diag_celulitis", isStringCheck = true)
    private Checkbox chkAiepiDiagCelulitis;
    @View(classField = Odontologia.class, field = "aiepi_diag_enfermedadbucalgrave", isStringCheck = true)
    private Checkbox chkAiepiDiagEnfermedadbucalGrave;
    @View(classField = Odontologia.class, field = "aiepi_diag_traumabucodental", isStringCheck = true)
    private Checkbox chkAiepiDiagTraumaBucodental;
    @View(classField = Odontologia.class, field = "aiepi_diag_estomatitis", isStringCheck = true)
    private Checkbox chkAiepiDiagEstomatitis;
    @View(classField = Odontologia.class, field = "aiepi_diag_enfermedaddentalgingival", isStringCheck = true)
    private Checkbox chkAiepiDiagEnfermedadDentalGingival;

    @View(classField = Odontologia.class, field = "aiepi_diag_altoriesgoenfemedadbucal", isStringCheck = true)
    private Radio chkAiepiDiagAltoRiesgoEnfermadadBucal;
    @View(classField = Odontologia.class, field = "aiepi_diag_riesgoenfermedadbucal", isStringCheck = true)
    private Radio chkAiepiDiagBajoRiesgoEnfermadadBucal;

    /* odontograma */
    // odontontograma
    @View(isMacro = true)
    OdontogramaMacro odtodontograma;

    /* indice de dean */
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean16;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean15;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean13;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean12;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean11;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean21;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean22;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean23;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean25;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean26;

    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean46;
    @View(isMacro = true)
    @IndiceDean
    private IndiceDental indiceDean36;

    @View
    private Button btGuardar;
    @View
    private Textbox tbxCedula_acomp;

    /* radio groups */
    @View(field = "odont_grama_presenta_fluorosis", classField = Odontologia.class)
    private Radiogroup rdgPresenta_flourosis;
    @View(field = "odont_grama_clasificacion_lesiones", classField = Odontologia.class)
    private Radiogroup rdgclasificacionlessiones;

    /* supercies manchadas seleccionadas */
    private Odontologia odontologiaTemp;
    private boolean mostrar_evolucion_2 = false;

    protected int select;
    public ModuloConsultaIMG padre;
    private Citas cita_seleccionada;

    private final String[] idsExc = {"infoPacientes", "northEditar",
        "btImprimir", "lbxFormato", "btImprimirRemision",
        "btImprimirConsentimiento", "btImprimirRemisionExterna"};

    public void mostrarRedBuenTrato(Radiogroup r, Row r1) {
        if (r.getSelectedItem().getValue().equals("1")) {
            r1.setVisible(true);
        } else {
            r1.setVisible(false);
        }
    }

    public void mostrarRedBuenTrato(Radiogroup r, Row r1, Textbox t) {
        if (r.getSelectedItem().getValue().equals("1")) {
            r1.setVisible(true);
        } else {
            r1.setVisible(false);
            t.setText("");
        }
    }

    /**
     * Muestra una serie de alarmas dependiendo de la edad del paciente y el
     * valor de los examenes fÃ­sicos
     *
     * @author Manuel Aguilar
     */
    public void alarmaExamenFisicoFr() {
        String POSICION_ALERTA = "end_before";
        Integer TIEMPO_ALERTA = 4000;
        Integer edad = Integer.valueOf(Util.getEdad(
                new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
                        .getPaciente().getFecha_nacimiento()), "1", false));

        if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && (dbxPresion.getValue() == 70 && dbxPresion2.getValue() == 50)
                && (edad < 1)) {
            String mensaje = "Normal";
            Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_INFO,
                    dbxPresion, POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:blue");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:blue");
        } else if (edad != 1) {
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:white");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:white");
        } else if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && (((dbxPresion.getValue() != 70 || dbxPresion2.getValue() != 50) || (dbxPresion
                .getValue() == 70 || dbxPresion2.getValue() != 50)) || (dbxPresion
                .getValue() != 70 || dbxPresion2.getValue() == 50))
                && (edad < 1)) {
            String mensaje = "Anormal";
            Clients.showNotification(mensaje,
                    Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
                    POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:red");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:red");
        }

        if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && (dbxPresion.getValue() == 90 && dbxPresion2.getValue() == 50)
                && (edad >= 1 && edad < 2)) {
            String mensaje = "Normal";
            Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_INFO,
                    dbxPresion, POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:blue");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:blue");
        } else if (edad < 1 || edad >= 2) {
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:blue");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:blue");
        } else if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && ((dbxPresion.getValue() != 90 || dbxPresion2.getValue() != 50)
                || (dbxPresion.getValue() != 90 || dbxPresion2
                .getValue() == 50) || (dbxPresion.getValue() == 90 || dbxPresion2
                .getValue() != 50))
                && (edad >= 1 && edad < 2)) {
            String mensaje = "Anormal";
            Clients.showNotification(mensaje,
                    Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
                    POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:red");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:red");
        }

        if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && (dbxPresion.getValue() == ((edad * 2) + 80) && dbxPresion2
                .getValue() == ((dbxPresion.getValue() / 2) + 10))
                && (edad >= 2 && edad < 10)) {
            //log.info("for2");
            String mensaje = "Normal";
            Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_INFO,
                    dbxPresion, POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:blue");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:blue");
        } else if (edad < 2 || edad > 10) {
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:white");
        } else if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && ((dbxPresion.getValue() != ((edad * 2) + 80) || dbxPresion2
                .getValue() != ((dbxPresion.getValue() / 2) + 10))
                || (dbxPresion.getValue() != ((edad * 2) + 80) || dbxPresion2
                .getValue() == ((dbxPresion.getValue() / 2) + 10)) || (dbxPresion
                .getValue() != ((edad * 2) + 80) || dbxPresion2
                .getValue() == ((dbxPresion.getValue() / 2) + 10)))
                && (edad >= 2 && edad <= 10)) {
            //log.info("for22");

            String mensaje = "Anormal";
            Clients.showNotification(mensaje,
                    Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
                    POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:red");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:red");
        }

        if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && (dbxPresion.getValue() == (edad + 100) && dbxPresion2
                .getValue() == ((dbxPresion.getValue() / 2) + 10))
                && (edad > 10 && edad <= 14)) {
            //log.info("for3");
            String mensaje = "Normal";
            Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_INFO,
                    dbxPresion, POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:blue");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:blue");
        } else if (edad < 10 || edad > 14) {
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:white");
        } else if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && ((dbxPresion.getValue() != (edad + 100) || dbxPresion2
                .getValue() != ((dbxPresion.getValue() / 2) + 10))
                || (dbxPresion.getValue() == (edad + 100) || dbxPresion2
                .getValue() != ((dbxPresion.getValue() / 2) + 10)) || (dbxPresion
                .getValue() != (edad + 100) || dbxPresion2.getValue() == ((dbxPresion
                .getValue() / 2) + 10))) && (edad > 10 && edad <= 14)) {

            String mensaje = "Anormal";
            Clients.showNotification(mensaje,
                    Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
                    POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:red");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:red");
        }

        if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && (dbxPresion.getValue() >= 120 && dbxPresion.getValue() <= 130)
                && (dbxPresion2.getValue() >= 80 && dbxPresion2.getValue() <= 90)
                && (edad > 14 && edad < 45)) {
            //log.info("for4");
            String mensaje = "Normal";
            Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_INFO,
                    dbxPresion, POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:blue");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:blue");
        } else if (edad < 14 || edad >= 45) {
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:white");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:white");
        } else if ((!dbxPresion.getText().isEmpty() && (!dbxPresion2.getText()
                .isEmpty()))
                && (((dbxPresion.getValue() < 120 || dbxPresion.getValue() > 130) && (dbxPresion2
                .getValue() < 80 || dbxPresion2.getValue() > 90))
                || ((dbxPresion.getValue() >= 120 || dbxPresion
                .getValue() <= 130) && (dbxPresion2.getValue() < 80 || dbxPresion2
                .getValue() > 90)) || ((dbxPresion.getValue() < 120 || dbxPresion
                .getValue() > 130)
                && (dbxPresion2.getValue() >= 80 || dbxPresion2
                .getValue() <= 90) && (edad > 14 && edad < 45)))) {
            //log.info("for44");
            String mensaje = "Anormal";
            Clients.showNotification(mensaje,
                    Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
                    POSICION_ALERTA, TIEMPO_ALERTA, true);
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:red");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:red");
        }
        if (dbxPresion.getText().isEmpty() || dbxPresion2.getText().isEmpty()) {
            dbxPresion
                    .setStyle("text-transform:uppercase;background-color:white");
            dbxPresion2
                    .setStyle("text-transform:uppercase;background-color:white");
        }

    }

    public void mostrarComponente(Radiogroup r, Listbox l) {
        if (r.getSelectedItem().getValue().toString().equals("1")) {
            l.setVisible(true);
        }
    }

    public void mostrarComponente(Checkbox s, Textbox t, Label l) {
        if (s.isChecked()) {
            t.setVisible(true);
            l.setVisible(true);
        } else {
            t.setVisible(false);
            l.setVisible(false);
        }
    }

    public void mostrarComponente(Radiogroup r, Textbox t) {
        if (r.getSelectedItem().getValue().toString().equals("1")) {
            t.setVisible(true);
        }
    }

    public void mostrarComponente(Checkbox s, Textbox t) {
        if (s.isChecked()) {
            t.setVisible(true);
        }
    }

    public void mostrarComponente(Listbox l, Textbox t) {
        if (l.getSelectedIndex() > 0) {
            t.setVisible(true);
        }
    }

    public void seleccionRadio2(Radiogroup r, Textbox t) {
        if (r.getSelectedItem().getValue().equals("1")) {
            t.setVisible(true);
        } else {
            t.setVisible(false);
            t.setText("");
        }

    }

    /**
     * Marca el check "consulta externa" de la remision interna
     *
     * @author Manuel Aguilar
     * @param radiogroup
     */
    public void onSelccionarConsultaExtera(Radiogroup radiogroup) {
        macroRemision_interna.onSelccionarConsultaExtera(radiogroup
                .getSelectedItem().getValue().toString().equals("1"));
    }

    public void imprimirRemison() throws Exception {
        macroRemision_interna.imprimirReporte();
    }

    public void imprimirConsentimiento() {
        macroConsentimiento_inf_odon.imprimirReporte();
    }

    public void imprimirRemisionExterna() throws Exception {
        remisiones_externasAction.imprimir();

    }

    // manuel
    public void seleccionRadio(Radiogroup r, Intbox t, Label l) {
        if (r.getSelectedItem().getValue().equals("1")) {
            t.setVisible(true);
            l.setVisible(true);
        } else {
            t.setVisible(false);
            l.setVisible(false);
            t.setText("");
        }
    }

    public void seleccionRadio(Radiogroup r, Textbox t, Label l) {
        if (r.getSelectedItem().getValue().equals("1")) {
            t.setVisible(true);
            l.setVisible(true);
        } else {
            t.setVisible(false);
            l.setVisible(false);
            t.setText("");
        }
    }

    public void mostrarPerinatales(Groupbox gbx,
            AbstractComponent[] abstractComponents) {

        Paciente p1 = new Paciente();
        if (p1.getSexo().equals("FEMENINO") || p1.getSexo().equals("F")) {
            gbx.setVisible(true);

        } else {

            for (AbstractComponent abstractComponent : abstractComponents) {
                ((Intbox) abstractComponent).setText("");
            }

        }
    }

    public void seleccionCheck(Checkbox c, Intbox ib, Label l) {
        if (c.isChecked()) {
            ib.setVisible(true);
            l.setVisible(true);
        } else {
            ib.setVisible(false);
            l.setVisible(false);
            ib.setText("");
        }
    }

    public void seleccionCheck(Checkbox c, Textbox ib, Label l) {
        if (c.isChecked()) {
            ib.setVisible(true);
            l.setVisible(true);
        } else {
            ib.setVisible(false);
            l.setVisible(false);
            ib.setText("");
        }
    }

    /* fin odontograma acciones */
    public void initOdontologia2() throws Exception {

    }

    public void listarCombos() {

        listarAtendida();
        Utilidades
                .listarElementoListbox(lbxRelacion, true, getServiceLocator());
        listarElementoListbox(lbxAnam_cual_diabetes, true);
        listarElementoListbox(lbxAnam_cual_hepatitis, true);
        listarElementoListbox(lbxAnam_cual_protasis, true);

        /* tipos de diasnosticos */
        List<Elemento> elementos = elementoService.listar("tipo_diagnostico");

        elementos = elementoService.listar("ante_familiares");
        listarElementoListboxFromType(lbxAnte_fam_cancer, false, elementos,
                true);
        listarElementoListboxFromType(lbxAnte_fam_diabetes, false, elementos,
                true);
        listarElementoListboxFromType(lbxAnte_fam_dislipidemia, false,
                elementos, true);
        listarElementoListboxFromType(lbxAnte_fam_ecv, false, elementos, true);
        listarElementoListboxFromType(lbxAnte_fam_enf_coronaria, false,
                elementos, true);
        listarElementoListboxFromType(lbxAnte_fam_enf_mental, false, elementos,
                true);
        listarElementoListboxFromType(lbxAnte_fam_hematologia, false,
                elementos, true);
        listarElementoListboxFromType(lbxAnte_fam_hipertension, false,
                elementos, true);
        listarElementoListboxFromType(lbxAnte_fam_muerte_im_acv, false,
                elementos, true);
        listarElementoListboxFromType(lbxAnte_fam_nefropatias, false,
                elementos, true);
        listarElementoListboxFromType(lbxAnte_fam_obesos, false, elementos,
                true);
        listarElementoListboxFromType(lbxAnte_fam_otros, false, elementos, true);

        listarElementoListbox(lbxTipo_fluorisis, true);
        listarElementoListbox(lbxTra_cirugia, true);
        listarParameter();
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

    // Convertimos todos las valores de textbox a mayusculas
    public void setUpperCase() {
        Collection<Component> collection = groupboxEditar.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                Textbox textbox = (Textbox) abstractComponent;
                if (!(textbox instanceof Combobox)) {
                    ((Textbox) abstractComponent)
                            .setText(((Textbox) abstractComponent).getText()
                                    .trim().toUpperCase());
                }
            }
        }
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {

    }

    public void deshabilitarCampos(boolean estado) throws Exception {
        Collection<Component> collection = groupboxEditar.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
                    ((Textbox) abstractComponent).setDisabled(estado);
                }
            }
            if (abstractComponent instanceof Listbox) {
                if (!((Listbox) abstractComponent).getId().equals(
                        "lbxParameter")) {
                    if (((Listbox) abstractComponent).getItemCount() > 0) {
                        ((Listbox) abstractComponent).setDisabled(estado);
                    }
                }
            }
            if (abstractComponent instanceof Doublebox) {
                ((Doublebox) abstractComponent).setDisabled(estado);
            }
            if (abstractComponent instanceof Intbox) {
                ((Intbox) abstractComponent).setDisabled(estado);
            }
            if (abstractComponent instanceof Datebox) {
                ((Datebox) abstractComponent).setDisabled(estado);
            }
            if (abstractComponent instanceof Checkbox) {
                ((Checkbox) abstractComponent).setDisabled(estado);
            }
            if (abstractComponent instanceof Radio) {
                ((Radio) abstractComponent).setDisabled(estado);
            }

            if (abstractComponent instanceof Radiogroup) {
                List list = ((Radiogroup) abstractComponent).getItems();
                for (Object object : list) {
                    if (object instanceof Radio) {
                        ((Radio) object).setDisabled(estado);
                    }
                }
            }
            if (abstractComponent instanceof DienteMacro) {
                ((DienteMacro) abstractComponent).setDisabled(estado);
            }
            if (abstractComponent instanceof IndiceDental) {
                ((IndiceDental) abstractComponent).setDisabled(estado);
            }
        }
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        boolean valida = true;
        infoPacientes.validarInformacionPaciente();
        Integer edad = Integer.valueOf(Util.getEdad(
                new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
                        .getPaciente().getFecha_nacimiento()), "1", false));
        if (edad < 18) {
            FormularioUtil.validarCamposObligatorios(tbxAcompaniante,
                    lbxRelacion, ibxTel_acompaniante, tbxCedula_acomp);
        }

        FormularioUtil.validarCamposObligatorios(tbxCodigo_prestador1,
                tbxMotivo_consulta, tbxEnfermedad_actual,
                tbxPrev_frec_cepillado, tbxPrev_frec_charla,
                tbxPrev_frec_enjuague, tbxPrev_frec_fluor, tbxPrev_frec_seda,
                tbxPrev_frec_sellante);

        valida = orden_servicioAction.validarFormExterno();

        if (valida) {
            valida = remisiones_externasAction.validarRemision();
        }

        if (valida) {
            valida = macroImpresion_diagnostica.validarImpresionDiagnostica();
        }

		// if (!valida) {
        // Messagebox.show("Los campos marcados con (*) son obligatorios",
        // usuarios.getNombres() + " recuerde que...", Messagebox.OK,
        // Messagebox.EXCLAMATION);
        // }
        return valida;
    }

    /**
     *
     * @param r
     * @param titulo
     */
    public void validarSistomatico(Radiogroup r, String titulo) {
        Integer TIEMPO_ALERTA = IConstantes.TIEMPO_NOTIFICACIONES_GENERALES;
        // String mensje="";

        if (r.getSelectedItem().getValue().equals("1")) {
            String mensaje = "Este paciente debe ser remitido internamente para consulta externa por "
                    + titulo;
            Notificaciones.mostrarNotificacionAlerta("Informacion", mensaje,
                    TIEMPO_ALERTA);
        }
    }

    /**
     * Devuelve una lista de codigos cup's, dependiendo de los check. que
     * marcaron en la parte de plan de tratamiento.
     *
     * @author Manuel Aguilar
     * @return listaProcedimeintos
     */
    public List<Map<String, Object>> agregarProcedimientos() {

        List<Map<String, Object>> lista_procedimientos = new ArrayList<Map<String, Object>>();
        Integer indice = 0;

        if (chbTra_educacion_oral.isChecked()) {
            // lista_procedimeintos.add("990212");
            Map<String, Object> procedimeintos = new HashMap<String, Object>();
            procedimeintos.put("codigo", "990212");
            procedimeintos.put("cantidad", null);
            lista_procedimientos.add(procedimeintos);
        } else {
            indice = getIndiceLista("990212", lista_procedimientos);
            if (indice != -1) {
                lista_procedimientos.remove(indice);
            }
        }

        if (chbTra_control_placa.isChecked()) {
            // lista_procedimeintos.add("997310");
            Map<String, Object> procedimeintos = new HashMap<String, Object>();
            procedimeintos.put("codigo", "997310");
            procedimeintos.put("cantidad", null);
            lista_procedimientos.add(procedimeintos);
        } else {
            indice = getIndiceLista("997310", lista_procedimientos);
            if (indice != -1) {
                lista_procedimientos.remove(indice);
            }
        }

        if (chbSellante.isChecked()) {
            // lista_procedimeintos.add("997102");
            Map<String, Object> procedimeintos = new HashMap<String, Object>();
            procedimeintos.put("codigo", "997102");
            procedimeintos.put("cantidad", ConvertidorDatosUtil
                    .convertirDatot(tbxCant_sellante.getValue()));
            lista_procedimientos.add(procedimeintos);
        } else {
            indice = getIndiceLista("997102", lista_procedimientos);
            if (indice != -1) {
                lista_procedimientos.remove(indice);
            }
        }
        if (chbFluor.isChecked()) {
            // lista_procedimeintos.add("997103");
            Map<String, Object> procedimeintos = new HashMap<String, Object>();
            procedimeintos.put("codigo", "997103");
            procedimeintos.put("cantidad", null);
            lista_procedimientos.add(procedimeintos);
        } else {
            indice = getIndiceLista("997103", lista_procedimientos);
            if (indice != -1) {
                lista_procedimientos.remove(indice);
            }
        }
        if (rdbTra_detartraje_supragingival.getSelectedItem().getValue()
                .toString().equals("1")) {
            // lista_procedimeintos.add("997300");
            Map<String, Object> procedimeintos = new HashMap<String, Object>();
            procedimeintos.put("codigo", "997300");
            procedimeintos.put("cantidad", cantidad_detartraje);
            lista_procedimientos.add(procedimeintos);
        } else {
            indice = getIndiceLista("997300", lista_procedimientos);
            if (indice != -1) {
                lista_procedimientos.remove(indice);
            }
        }

        return lista_procedimientos;

    }

    private int getIndiceLista(String codigo_entra,
            List<Map<String, Object>> lista) {
        for (int i = 0; i < lista.size(); i++) {
            Map<String, Object> mapa = lista.get(i);
            if (mapa.get("codigo").toString().equals(codigo_entra)) {
                return i;
            }
        }

        return -1;
    }

    // Metodo para guardar la informaciÃ³n //
    public void guardarDatos() throws Exception {
        try {
            if (validarForm()) {
                FormularioUtil.setUpperCase(groupboxEditar);
                // Cargamos los componentes //
                Messagebox.show(usuarios.getNombres()
                        + " estas seguro de realizar esta acciÃ³n ?",
                        "InformaciÃ³n ..", Messagebox.YES + Messagebox.NO,
                        Messagebox.INFORMATION, new EventListener() {

                            @Override
                            public void onEvent(Event event) throws Exception {

                                if (event.getName().equals("onYes")) {
                                    Odontologia odontologia = getBean();

                                    HashMap<String, Object> datos = new HashMap<String, Object>();
                                    datos.put("accion", tbxAccion.getText());
									// datos.put("sigvitales",
                                    // mcSignosVitales.obtenerSigvitales());
                                    datos.put("codigo_historia", odontologia);
                                    datos.put("dientes",
                                            odtodontograma.getDientes());
                                    datos.put("indice_dean", getIndiceDean());
                                    datos.put("admision", admision);

                                    Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
                                    .obtenerImpresionDiagnostica();

                                    Map<String, Object> mapProcedimientos = orden_servicioAction
                                    .obtenerDatos();

                                    datos.put("orden_servicio",
                                            mapProcedimientos);

                                    Consentimiento_inf_odon consentimineto_inf_odon = macroConsentimiento_inf_odon
                                    .obtenerConsentiminetoInfOdon();

                                    datos.put("consentimineto_inf_odon",
                                            consentimineto_inf_odon);

                                    Remision_interna remision_interna = macroRemision_interna
                                    .obtenerRemisionInterna();
                                    datos.put("remision_interna",
                                            remision_interna);

                                    datos.put("impresion_diagnostica",
                                            impresion_diagnostica);

                                    Anexo9_entidad anexo9_entidad = remisiones_externasAction
                                    .obtenerAnexo9();
                                    datos.put("anexo9", anexo9_entidad);

                                    Parametros_empresa parametros_empresa = new Parametros_empresa();
                                    parametros_empresa
                                    .setCodigo_empresa(codigo_empresa);
                                    parametros_empresa = getServiceLocator()
                                    .getServicio(GeneralExtraService.class)
                                    .consultar(parametros_empresa);

                                    datos.put("parametros_empresa",
                                            parametros_empresa);

                                    odontologia = getServiceLocator()
                                    .getOdontologiaService().guardar(
                                            datos);

                                    if (anexo9_entidad != null) {
                                        remisiones_externasAction
                                        .setCodigo_remision(anexo9_entidad
                                                .getCodigo());
                                        remisiones_externasAction
                                        .getBotonImprimir()
                                        .setDisabled(false);
                                    }

                                    Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
                                    .get("orden_servicio");
                                    if (orden_servicio != null) {
                                        orden_servicioAction
                                        .mostrarDatos(orden_servicio);
                                    }

                                    cantidad_detartraje = 0;

                                    if (chbTra_i.isChecked()) {
                                        cantidad_detartraje += 1;
                                    }
                                    if (chbTra_ii.isChecked()) {
                                        cantidad_detartraje += 1;
                                    }
                                    if (chbTra_iii.isChecked()) {
                                        cantidad_detartraje += 1;
                                    }
                                    if (chbTra_iv.isChecked()) {
                                        cantidad_detartraje += 1;
                                    }

                                    lista_procedimientos = agregarProcedimientos();

                                    List<Map<String, Object>> lista_procedimientos_id = consultarIdProcedimientos(lista_procedimientos);

                                    datos.put("lista_procedimientos_id",
                                            lista_procedimientos_id);

                                    if (odontologia != null) {
                                        padre.cargarEvolucionOdonotologia(
                                                odontologia,
                                                OpcionesFormulario.REGISTRAR,
                                                lista_procedimientos_id, true);
										// padre.cargarRegClinicoHigiene(
                                        // odontologia, true,
                                        // OpcionesFormulario.REGISTRAR);
                                        infoPacientes
                                        .setCodigo_historia(odontologia
                                                .getCodigo_historia());
                                    }

                                    actualizarRemision(admision,
                                            getInformacionClinica(),
                                            Odontologia2Action.this);

                                    tbxAccion.setValue("modificar");

                                    // validamos remision, evolucion y remisiÃ³n
                                    orden_servicioAction.validarParaImpresion();
                                    remisiones_externasAction.validarRemision();

                                    Messagebox
                                    .show("Los datos se guardaron satisfactoriamente",
                                            "InformaciÃ³n ..",
                                            Messagebox.OK,
                                            Messagebox.INFORMATION);
                                    deshabilitarCampos(true);
                                    btGuardar.setDisabled(true);
                                }

                            }

                        });
            }

        } catch (ImpresionDiagnosticaException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            if (!(e instanceof WrongValueException)) {
                MensajesUtil.mensajeError(e, "", this);
            }
        }

    }

    /**
     * Recibe una lista de procedimientos. Devuelve los id de los codigos
     *
     * @author Manuel Aguilar
     * @param List de procedimientos
     * @return listado de id de codigos
	 *
     */
    protected List<Map<String, Object>> consultarIdProcedimientos(
            List<Map<String, Object>> lista_procedimientos) {

		//log.info("Ejecutando metodo @consultarIdProcedimientos() ===> "+lista_procedimientos);
        List<Map<String, Object>> listado_id_procedimientos = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < lista_procedimientos.size(); i++) {
            Map<String, Object> mapa = lista_procedimientos.get(i);
            String codigos_cups = mapa.get("codigo").toString();
            Long id_procedimiento = getServiceLocator().getServicio(
                    ProcedimientosService.class).consultarIDPorCups(
                            codigos_cups);
            if (id_procedimiento != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("codigo", id_procedimiento);
                map.put("cantidad", mapa.get("cantidad"));
                listado_id_procedimientos.add(map);
            } else {
                throw new ValidacionRunTimeException(
                        "Servicio asociado al cÃ³digo cups "
                        + codigos_cups
                        + " no se ha encontrado, por favor verifique los procedimientos");
            }
        }

        return listado_id_procedimientos;
    }

    public Odontologia getBean() {
        Odontologia odontologia = new Odontologia();
        odontologia.setFecha_inicial(new Timestamp(new Date().getTime()));
        odontologia.setCodigo_empresa(admision.getCodigo_empresa());
        odontologia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        odontologia.setCodigo_historia(infoPacientes.getCodigo_historia());
        odontologia.setIdentificacion(admision.getNro_identificacion());
        odontologia.setNro_ingreso(admision.getNro_ingreso());
        odontologia.setHemoclasificacion("");
        odontologia.setVia_ingreso(via_ingreso);

        odontologia.setFecha_ingreso(admision.getFecha_ingreso());

        odontologia.setCodigo_prestador(tbxCodigo_prestador1.getValue());
        odontologia.setAcompaniante(tbxAcompaniante.getValue());
        odontologia.setRelacion(lbxRelacion.getSelectedItem().getValue()
                .toString());
        odontologia.setTel_acompaniante(ibxTel_acompaniante.getValue() + "");
        odontologia.setMotivo_consulta(Util.agregarComillas(tbxMotivo_consulta
                .getValue()));
        odontologia.setEnfermedad_actual(tbxEnfermedad_actual.getValue());

        odontologia.setAnam_tratamiento(rdbAnam_tratamiento.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_tratamiento(tbxAnam_cual_tratamiento
                .getValue());
        odontologia.setAnam_toma_medicamentos(rdbAnam_toma_medicamentos
                .getSelectedItem().getValue().toString());
        odontologia
                .setAnam_cual_toma_medicamentos(tbxAnam_cual_toma_medicamentos
                        .getValue());
        odontologia.setAnam_alergias(rdbAnam_alergias.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_alergias(tbxAnam_cual_alergias.getValue());
        odontologia.setAnam_cardiopatias(rdbAnam_cardiopatias.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_cardiopatias(tbxAnam_cual_cardiopatias
                .getValue());
        odontologia.setAnam_alteracion_presion(rdbAnam_alteracion_presion
                .getSelectedItem().getValue().toString());
        odontologia
                .setAnam_cual_alteracion_presion(tbxAnam_cual_alteracion_presion
                        .getValue());
        odontologia.setAnam_embarazo(rdbAnam_embarazo.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_embarazo(tbxAnam_cual_embarazo.getValue());
        odontologia.setAnam_diabetes(rdbAnam_diabetes.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_diabetes(Utilidades.getValorListBox(lbxAnam_cual_diabetes));
        odontologia.setAnam_hepatitis(rdbAnam_hepatitis.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_hepatitis(lbxAnam_cual_hepatitis
                .getSelectedItem().getValue().toString());
        odontologia.setAnam_irradiaciones(rdbAnam_irradiaciones
                .getSelectedItem().getValue().toString());
        odontologia.setAnam_cual_irradiaciones(tbxAnam_cual_irradiaciones
                .getValue());
        odontologia.setAnam_discracias(rdbAnam_discracias.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_discracias(tbxAnam_cual_discracias.getValue());
        odontologia.setAnam_fiebre_reumatica(rdbAnam_fiebre_reumatica
                .getSelectedItem().getValue().toString());
        odontologia.setAnam_cual_fiebre_reumatica(tbxAnam_cual_fiebre_reumatica
                .getValue());
        odontologia.setAnam_enfermedad_renal(rdbAnam_enfermedad_renal
                .getSelectedItem().getValue().toString());
        odontologia.setAnam_cual_enfermedad_renal(tbxAnam_cual_enfermedad_renal
                .getValue());
        odontologia.setAnam_inmunosupresion(rdbAnam_inmunosupresion
                .getSelectedItem().getValue().toString());
        odontologia.setAnam_cual_inmunosupresion(tbxAnam_cual_inmunosupresion
                .getValue());
        odontologia.setAnam_trastornos(rdbAnam_trastornos.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_trastornos(tbxAnam_cual_trastornos.getValue());
        odontologia.setAnam_patologia(rdbAnam_patologia.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_patologia(tbxAnam_cual_patologia.getValue());
        odontologia.setAnam_trastornos_gastricos(rdbAnam_trastornos_gastricos
                .getSelectedItem().getValue().toString());
        odontologia
                .setAnam_cual_trastornos_gastricos(tbxAnam_cual_trastornos_gastricos
                        .getValue());
        odontologia.setAnam_epilepsia(rdbAnam_epilepsia.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_epilepsia(tbxAnam_cual_epilepsia.getValue());
        odontologia.setAnam_cirugias(rdbAnam_cirugias.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_cirugias(tbxAnam_cual_cirugias.getValue());
        odontologia.setAnam_protasis(rdbAnam_protasis.getSelectedItem()
                .getValue().toString());
        odontologia.setAnam_cual_protasis(lbxAnam_cual_protasis
                .getSelectedItem().getValue().toString());
        odontologia.setAnam_otro(rdbAnam_otro.getSelectedItem().getValue()
                .toString());
        odontologia.setAnam_cual_otros(tbxAnam_cual_otros.getValue());
        odontologia.setSintoma_respiratorio(rdbSintoma_respiratorio
                .getSelectedItem().getValue().toString());
        odontologia.setSintoma_piel(rdbSintoma_piel.getSelectedItem()
                .getValue().toString());

        odontologia.setAnte_fam_hipertension(lbxAnte_fam_hipertension
                .getSelectedItem().getValue().toString());
        odontologia.setAnte_fam_ecv(lbxAnte_fam_ecv.getSelectedItem()
                .getValue().toString());
        odontologia.setAnte_fam_enf_coronaria(lbxAnte_fam_enf_coronaria
                .getSelectedItem().getValue().toString());
        odontologia.setAnte_fam_muerte_im_acv(lbxAnte_fam_muerte_im_acv
                .getSelectedItem().getValue().toString());
        odontologia.setAnte_fam_dislipidemia(lbxAnte_fam_dislipidemia
                .getSelectedItem().getValue().toString());
        odontologia.setAnte_fam_nefropatias(lbxAnte_fam_nefropatias
                .getSelectedItem().getValue().toString());
        odontologia.setAnte_fam_obesos(lbxAnte_fam_obesos.getSelectedItem()
                .getValue().toString());
        odontologia.setAnte_fam_diabetes(lbxAnte_fam_diabetes.getSelectedItem()
                .getValue().toString());
        odontologia.setAnte_fam_enf_mental(lbxAnte_fam_enf_mental
                .getSelectedItem().getValue().toString());
        odontologia.setAnte_fam_cancer(lbxAnte_fam_cancer.getSelectedItem()
                .getValue().toString());
        odontologia.setAnte_fam_hematologia(lbxAnte_fam_hematologia
                .getSelectedItem().getValue().toString());
        odontologia.setAnte_fam_otros(lbxAnte_fam_otros.getSelectedItem()
                .getValue().toString());
        odontologia.setAnte_fam_estecifique(tbxAnte_fam_estecifique.getValue());
        odontologia.setAnte_fam_observaciones(tbxAnte_fam_observaciones
                .getValue());
        odontologia.setEsto_labio_sup(rdbEsto_labio_sup.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_labio_sup(tbxEsto_cual_labio_sup.getValue());
        odontologia.setEsto_labio_inf(rdbEsto_labio_inf.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_labio_inf(tbxEsto_cual_labio_inf.getValue());
        odontologia.setEsto_comisura(rdbEsto_comisura.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_comisura(tbxEsto_cual_comisura.getValue());
        odontologia.setEsto_mucosa(rdbEsto_mucosa.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_mucosa(tbxEsto_cual_mucosa.getValue());
        odontologia.setEsto_surcos(rdbEsto_surcos.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_surcos(tbxEsto_cual_surcos.getValue());
        odontologia.setEsto_frenillo(rdbEsto_frenillo.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_frenillo(tbxEsto_cual_frenillo.getValue());
        odontologia.setEsto_paladar_blando(rdbEsto_paladar_blando
                .getSelectedItem().getValue().toString());
        odontologia.setEsto_cual_paladar_blando(tbxEsto_cual_paladar_blando
                .getValue());
        odontologia.setEsto_paladar_duro(rdbEsto_paladar_duro.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_paladar_duro(tbxEsto_cual_paladar_duro
                .getValue());
        odontologia.setEsto_orofaringe(rdbEsto_orofaringe.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_orofaringe(tbxEsto_cual_orofaringe.getValue());
        odontologia.setEsto_lengua(rdbEsto_lengua.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_lengua(tbxEsto_cual_lengua.getValue());
        odontologia.setEsto_boca(rdbEsto_boca.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_boca(tbxEsto_cual_boca.getValue());
        odontologia.setEsto_reborde(rdbEsto_reborde.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_reborde(tbxEsto_cual_reborde.getValue());
        odontologia.setEsto_salivares(rdbEsto_salivares.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_salivares(tbxEsto_cual_salivares.getValue());
        odontologia.setEsto_dolor_muscular(rdbEsto_dolor_muscular
                .getSelectedItem().getValue().toString());
        odontologia.setEsto_cual_dolor_muscular(tbxEsto_cual_dolor_muscular
                .getValue());
        odontologia.setEsto_dolor_articular(rdbEsto_dolor_articular
                .getSelectedItem().getValue().toString());
        odontologia.setEsto_cual_dolor_articular(tbxEsto_cual_dolor_articular
                .getValue());
        odontologia.setEsto_riudo_articular(rdbEsto_riudo_articular
                .getSelectedItem().getValue().toString());
        odontologia.setEsto_cual_ruido_articular(tbxEsto_cual_ruido_articular
                .getValue());
        odontologia.setEsto_alt_mov(rdbEsto_alt_mov.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_alt_mov(tbxEsto_cual_alt_mov.getValue());
        odontologia.setEsto_oclusion(rdbEsto_oclusion.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_oclusion(tbxEsto_cual_oclusion.getValue());
        odontologia.setEsto_bruxismo(rdbEsto_bruxismo.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_bruxismo(tbxEsto_cual_bruxismo.getValue());
        odontologia.setEsto_forma(rdbEsto_forma.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_forma(tbxEsto_cual_forma.getValue());
        odontologia.setEsto_tamano(rdbEsto_tamano.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_tamano(tbxEsto_cual_tamano.getValue());
        odontologia.setEsto_numero(rdbEsto_numero.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_numero(tbxEsto_cual_numero.getValue());
        odontologia.setEsto_color(rdbEsto_color.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_color(tbxEsto_cual_color.getValue());
        odontologia.setEsto_posicion(rdbEsto_posicion.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_posicion(tbxEsto_cual_posicion.getValue());
        odontologia.setEsto_impactados(rdbEsto_impactados.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_impactados(tbxEsto_cual_impactados.getValue());
        odontologia.setEsto_abfracciones(rdbEsto_abfracciones.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_abfracciones(tbxEsto_cual_abfracciones
                .getValue());
        odontologia.setEsto_inflamacion(rdbEsto_inflamacion.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_inflamacion(tbxEsto_cual_inflamacion
                .getValue());
        odontologia.setEsto_movilidad(rdbEsto_movilidad.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_movilidad(tbxEsto_cual_movilidad.getValue());
        odontologia.setEsto_recesiones(rdbEsto_recesiones.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_recesiones(tbxEsto_cual_recesiones.getValue());
        odontologia.setEsto_bolsas(rdbEsto_bolsas.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_bolsas(tbxEsto_cual_bolsas.getValue());
        odontologia.setEsto_calculo(rdbEsto_calculo.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_calculo(tbxEsto_cual_calculo.getValue());
        odontologia.setEsto_abceso(rdbEsto_abceso.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_abceso(tbxEsto_cual_abceso.getValue());
        odontologia.setEsto_sangrado(rdbEsto_sangrado.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_sangrado(tbxEsto_cual_sangrado.getValue());
        odontologia.setEsto_alt_vital(rdbEsto_alt_vital.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_alt_vital(tbxEsto_cual_alt_vital.getValue());
        odontologia.setEsto_percusion(rdbEsto_percusion.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_percusion(tbxEsto_cual_percusion.getValue());
        odontologia.setEsto_mov_dental(rdbEsto_mov_dental.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_mov_dental(tbxEsto_cual_mov_dental.getValue());
        odontologia.setEsto_fistula(rdbEsto_fistula.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_fistula(tbxEsto_cual_fistula.getValue());
        odontologia.setEsto_diente(rdbEsto_diente.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_diente(tbxEsto_cual_diente.getValue());
        odontologia.setEsto_oral(rdbEsto_oral.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_oral(tbxEsto_cual_oral.getValue());
        odontologia.setEsto_digital(rdbEsto_digital.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_digital(tbxEsto_cual_digital.getValue());
        odontologia.setEsto_lengua_protactil(rdbEsto_lengua_protactil
                .getSelectedItem().getValue().toString());
        odontologia.setEsto_cual_lengua_protactil(tbxEsto_cual_lengua_protactil
                .getValue());
        odontologia.setEsto_queilosfagia(rdbEsto_queilosfagia.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_queilosfagia(tbxEsto_cual_queilosfagia
                .getValue());
        odontologia.setEsto_fumador(rdbEsto_fumador.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_fumador(tbxEsto_cual_fumador.getValue());
        odontologia.setEsto_alcohol(rdbEsto_alcohol.getSelectedItem()
                .getValue().toString());
        odontologia.setEsto_cual_alcohol(tbxEsto_cual_alcohol.getValue());
        odontologia.setEsto_otro(rdbEsto_otro.getSelectedItem().getValue()
                .toString());
        odontologia.setEsto_cual_otros(tbxEsto_cual_otros.getValue());
        odontologia.setPrev_charla(rdbPrev_charla.getSelectedItem().getValue()
                .toString());
        odontologia.setPrev_frec_charla(tbxPrev_frec_charla.getValue());
        odontologia.setPrev_cepillado(rdbPrev_cepillado.getSelectedItem()
                .getValue().toString());
        odontologia.setPrev_frec_cepillado(tbxPrev_frec_cepillado.getValue());
        odontologia.setPrev_seda(rdbPrev_seda.getSelectedItem().getValue()
                .toString());
        odontologia.setPrev_frec_seda(tbxPrev_frec_seda.getValue());
        odontologia.setPrev_enjuague(rdbPrev_enjuague.getSelectedItem()
                .getValue().toString());
        odontologia.setPrev_frec_enjuague(tbxPrev_frec_enjuague.getValue());
        odontologia.setPrev_fluor(rdbPrev_fluor.getSelectedItem().getValue()
                .toString());
        odontologia.setPrev_frec_fluor(tbxPrev_frec_fluor.getValue());
        odontologia.setPrev_sellante(rdbPrev_sellante.getSelectedItem()
                .getValue().toString());
        odontologia.setPrev_frec_sellante(tbxPrev_frec_sellante.getValue());
        odontologia.setPrev_observacions(tbxPrev_observacions.getValue());

        odontologia.setFluorisis(rdbFluorisis.getSelectedItem().getValue()
                .toString());
        odontologia.setTipo_fluorisis(lbxTipo_fluorisis.getSelectedItem()
                .getValue().toString());
        odontologia.setTra_educacion_oral(chbTra_educacion_oral.isChecked());
        odontologia.setTra_control_placa(chbTra_control_placa.isChecked());
        odontologia.setTra_profilaxis(chbTra_profilaxis.isChecked());
        odontologia.setSellante(chbSellante.isChecked());
        odontologia.setFluor(chbFluor.isChecked());
        odontologia.setTra_amalgamas(chbTra_amalgamas.isChecked());
        odontologia.setTra_resinas(chbTra_resinas.isChecked());
        odontologia.setTra_ionimeros(chbTra_ionimeros.isChecked());
        odontologia.setTra_endodoncia(rdbTra_endodoncia.getSelectedItem()
                .getValue().toString());

        odontologia
                .setTra_detartraje_supragingival(rdbTra_detartraje_supragingival
                        .getSelectedItem().getValue().toString());
        odontologia.setTra_i(chbTra_i.isChecked());
        odontologia.setTra_ii(chbTra_ii.isChecked());
        odontologia.setTra_iii(chbTra_iii.isChecked());
        odontologia.setTra_iv(chbTra_iv.isChecked());
        odontologia.setTra_prostodoncia(rdbTra_prostodoncia.getSelectedItem()
                .getValue().toString());
        odontologia.setTra_cirugia(lbxTra_cirugia.getSelectedItem().getValue()
                .toString());
        // manuel
        odontologia.setCirugia_num_diente(ibxCirugia_num_diente.getValue());
        odontologia.setEndodoncia_num_diente(ibxEndodoncia_num_diente
                .getValue());
        odontologia.setProtodoncia_num_diente(ibxProtodoncia_num_diente
                .getValue());
        odontologia.setNum_diente_tra_amalgama(ibxNum_diente_traAmalgama
                .getValue());
        odontologia.setNro_diente_tra_aecinas(ibxNro_diente_trRecinas
                .getValue());
        odontologia.setNro_diente_tra_ionimeros(ibxNro_diente_traIonimeros
                .getValue());
        odontologia.setPresion(dbxPresion.getText());
        odontologia.setPresion2(dbxPresion.getText());
        odontologia.setPeso(dbxPeso.getText());
        odontologia.setTalla(dbxTalla.getText());
        odontologia.setRemision(tbxRemision.getValue());
        odontologia.setCedula_acomp(tbxCedula_acomp.getValue());
        odontologia.setPronostico(tbxPronostico.getValue().toString());
        odontologia.setViolencia(rdbViolencia.getSelectedItem().getValue()
                .toString());
        odontologia
                .setFisico(rdbFisico.getSelectedItem().getValue().toString());
        odontologia
                .setSexual(rdbSexual.getSelectedItem().getValue().toString());
        odontologia.setEmocional(rdbEmocional.getSelectedItem().getValue()
                .toString());
        odontologia
                .setRiesgo(rdbRiesgo.getSelectedItem().getValue().toString());
        odontologia.setTema(rdbTema.getSelectedItem().getValue().toString());

        odontologia.setCreacion_date(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        odontologia.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        odontologia.setCreacion_user(usuarios.getCodigo().toString());
        odontologia.setUltimo_user(usuarios.getCodigo().toString());

        odontologia.setOdont_grama_clasificacion_lesiones("");

        CargardorDeDatos.cargarDatosViewEnBean(Odontologia2Action.this,
                Odontologia.class, odontologia);

        // //System.Out.Println("Reach here");
		/* guardamos odontograma */
        odontologia.setOdont_grama_cariados_adulto(odtodontograma
                .getCariadosAdulto());
        odontologia.setOdont_grama_cariados_leche(odtodontograma
                .getCariadosLeche());
        odontologia.setOdont_grama_ceo_leche(odtodontograma.getCeoLeche());
        odontologia.setOdont_grama_cop_adulto(odtodontograma.getCopAdulto());
        odontologia.setOdont_grama_estraidos_leche(odtodontograma
                .getEstraidosLeche());
        odontologia.setOdont_grama_opturados_adulto(odtodontograma
                .getOpturadosAdulto());
        odontologia.setOdont_grama_opturados_leche(odtodontograma
                .getOpturadoLeche());
        odontologia.setOdont_grama_perdidos_adulto(odtodontograma
                .getPerdidosAdulto());
        odontologia.setOdont_grama_porcentaje_manchado(odtodontograma
                .getSuperficiesManchadas());
        odontologia.setModo_denticion(odtodontograma.getTipoDenticion());
        return odontologia;
    }

    protected List<Indice_dean> getIndiceDean() throws Exception {
        Field[] fields = this.getClass().getDeclaredFields();
        List<Indice_dean> indice_deans = new ArrayList<Indice_dean>();
        for (Field field : fields) {
            field.setAccessible(true);
            IndiceDean view = field.getAnnotation(IndiceDean.class);
            if (view != null) {
                IndiceDental indiceDental = (IndiceDental) field.get(this);

                Indice_dean indice_dean = new Indice_dean();
                indice_dean.setCodigo_empresa(codigo_empresa);
                indice_dean.setCodigo_sucursal(codigo_sucursal);
                indice_dean.setNro_indice(indiceDental.getNumber());
                indice_dean.setConvencion(indiceDental.getCbxConvenciones());
                indice_deans.add(indice_dean);
            }
        }
        return indice_deans;
    }

    private void setIndiceDean(List<Indice_dean> indice_deans) throws Exception {
        for (Indice_dean indice_dean : indice_deans) {
            try {
                Field field = this.getClass().getDeclaredField(
                        "indiceDean" + indice_dean.getNro_indice());
                field.setAccessible(true);
                IndiceDental indiceDental = (IndiceDental) field.get(this);
                if (indiceDental != null) {
                    indiceDental.setConvenciones(indice_dean.getConvencion());
                }
            } catch (Exception e) {
            }
        }
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Odontologia odontologia = (Odontologia) obj;
        try {
            /* odontograma realizado por luis M */
            infoPacientes.setCodigo_historia(odontologia.getCodigo_historia());
            // tbxNro_inscripcion.setValue(odontologia.getNro_inscripcion());
            initMostrar_datos(odontologia);

            onMostrarModuloRemisiones();
            cargarInformacion_paciente();
            cargarRemisionInterna(odontologia);
            cargarConsentimeinto_inf_odon(odontologia);
            cargarPrestador();

            if (odontologia.getOdont_grama_presenta_fluorosis().equals("S")) {
                ((Groupbox) getFellow("groupboxClourosis")).setVisible(true);
            }

            // valores del odontograma
            odtodontograma.setCariadosAdulto(odontologia
                    .getOdont_grama_cariados_adulto());
            odtodontograma.setCariadosLeche(odontologia
                    .getOdont_grama_cariados_leche());
            odtodontograma.setCeoLeche(odontologia.getOdont_grama_ceo_leche());
            odtodontograma
                    .setCopAdulto(odontologia.getOdont_grama_cop_adulto());
            odtodontograma.setEstraidosLeche(odontologia
                    .getOdont_grama_estraidos_leche());
            odtodontograma.setOpturadosAdulto(odontologia
                    .getOdont_grama_opturados_adulto());
            odtodontograma.setOpturadoLeche(odontologia
                    .getOdont_grama_opturados_leche());
            odtodontograma.setPerdidosAdulto(odontologia
                    .getOdont_grama_perdidos_adulto());
            odtodontograma.setSuperficiesManchadas(odontologia
                    .getOdont_grama_porcentaje_manchado());
            odtodontograma.setTipoDenticion(odontologia.getModo_denticion());

            // cargamos datos automaticos
            CargardorDeDatos.mostrarEnVista(this, Odontologia.class,
                    odontologia);

            btGuardar.setDisabled(true);

            tbxAcompaniante.setValue(odontologia.getAcompaniante());
            Utilidades.seleccionarListitem(lbxRelacion,
                    odontologia.getRelacion());
            ibxTel_acompaniante.setValue(ConvertidorDatosUtil
                    .convertirDato(odontologia.getTel_acompaniante()));
            tbxMotivo_consulta.setValue(odontologia.getMotivo_consulta());
            tbxEnfermedad_actual.setValue(odontologia.getEnfermedad_actual());
            Radio radio = (Radio) rdbAnam_tratamiento
                    .getFellow("Anam_tratamiento"
                            + odontologia.getAnam_tratamiento());
            radio.setChecked(true);
            mostrarComponente(rdbAnam_tratamiento, tbxAnam_cual_tratamiento);
            tbxAnam_cual_tratamiento.setValue(odontologia
                    .getAnam_cual_tratamiento());
            Radio radio1 = (Radio) rdbAnam_toma_medicamentos
                    .getFellow("Anam_toma_medicamentos"
                            + odontologia.getAnam_toma_medicamentos());
            radio1.setChecked(true);
            mostrarComponente(rdbAnam_toma_medicamentos,
                    tbxAnam_cual_toma_medicamentos);
            tbxAnam_cual_toma_medicamentos.setValue(odontologia
                    .getAnam_cual_toma_medicamentos());
            Radio radio2 = (Radio) rdbAnam_alergias.getFellow("Anam_alergias"
                    + odontologia.getAnam_alergias());
            radio2.setChecked(true);
            mostrarComponente(rdbAnam_alergias, tbxAnam_cual_alergias);
            tbxAnam_cual_alergias.setValue(odontologia.getAnam_cual_alergias());
            Radio radio3 = (Radio) rdbAnam_cardiopatias
                    .getFellow("Anam_cardiopatias"
                            + odontologia.getAnam_cardiopatias());
            radio3.setChecked(true);
            mostrarComponente(rdbAnam_cardiopatias, tbxAnam_cual_cardiopatias);
            tbxAnam_cual_cardiopatias.setValue(odontologia
                    .getAnam_cual_cardiopatias());
            Radio radio4 = (Radio) rdbAnam_alteracion_presion
                    .getFellow("Anam_alteracion_presion"
                            + odontologia.getAnam_alteracion_presion());
            radio4.setChecked(true);
            mostrarComponente(rdbAnam_alteracion_presion,
                    tbxAnam_cual_alteracion_presion);
            tbxAnam_cual_alteracion_presion.setValue(odontologia
                    .getAnam_cual_alteracion_presion());
            Radio radio5 = (Radio) rdbAnam_embarazo.getFellow("Anam_embarazo"
                    + odontologia.getAnam_embarazo());
            radio5.setChecked(true);
            mostrarComponente(rdbAnam_embarazo, tbxAnam_cual_embarazo);
            tbxAnam_cual_embarazo.setValue(odontologia.getAnam_cual_embarazo());
            Radio radio6 = (Radio) rdbAnam_diabetes.getFellow("Anam_diabetes"
                    + odontologia.getAnam_diabetes());
            radio6.setChecked(true);
            Utilidades.seleccionarListitem(lbxAnam_cual_diabetes,
                    odontologia.getAnam_cual_diabetes());

            cargarImpresionDiagnostica(odontologia);
            Radio radio7 = (Radio) rdbAnam_hepatitis.getFellow("Anam_hepatitis"
                    + odontologia.getAnam_hepatitis());
            radio7.setChecked(true);
            mostrarComponente(rdbAnam_hepatitis, lbxAnam_cual_hepatitis);
            Utilidades.seleccionarListitem(lbxAnam_cual_hepatitis,
                    odontologia.getAnam_cual_hepatitis());

            Radio radio8 = (Radio) rdbAnam_irradiaciones
                    .getFellow("Anam_irradiaciones"
                            + odontologia.getAnam_irradiaciones());
            radio8.setChecked(true);
            mostrarComponente(rdbAnam_irradiaciones, tbxAnam_cual_irradiaciones);
            tbxAnam_cual_irradiaciones.setValue(odontologia
                    .getAnam_cual_irradiaciones());
            Radio radio9 = (Radio) rdbAnam_discracias
                    .getFellow("Anam_discracias"
                            + odontologia.getAnam_discracias());
            radio9.setChecked(true);
            mostrarComponente(rdbAnam_discracias, tbxAnam_cual_discracias);
            tbxAnam_cual_discracias.setValue(odontologia
                    .getAnam_cual_discracias());
            Radio radio10 = (Radio) rdbAnam_fiebre_reumatica
                    .getFellow("Anam_fiebre_reumatica"
                            + odontologia.getAnam_fiebre_reumatica());
            radio10.setChecked(true);
            mostrarComponente(rdbAnam_fiebre_reumatica,
                    tbxAnam_cual_fiebre_reumatica);
            tbxAnam_cual_fiebre_reumatica.setValue(odontologia
                    .getAnam_cual_fiebre_reumatica());
            Radio radio11 = (Radio) rdbAnam_enfermedad_renal
                    .getFellow("Anam_enfermedad_renal"
                            + odontologia.getAnam_enfermedad_renal());
            radio11.setChecked(true);
            mostrarComponente(rdbAnam_enfermedad_renal,
                    tbxAnam_cual_enfermedad_renal);
            tbxAnam_cual_enfermedad_renal.setValue(odontologia
                    .getAnam_cual_enfermedad_renal());
            Radio radio12 = (Radio) rdbAnam_inmunosupresion
                    .getFellow("Anam_inmunosupresion"
                            + odontologia.getAnam_inmunosupresion());
            radio12.setChecked(true);
            mostrarComponente(rdbAnam_inmunosupresion,
                    tbxAnam_cual_inmunosupresion);
            tbxAnam_cual_inmunosupresion.setValue(odontologia
                    .getAnam_cual_inmunosupresion());
            Radio radio13 = (Radio) rdbAnam_trastornos
                    .getFellow("Anam_trastornos"
                            + odontologia.getAnam_trastornos());
            radio13.setChecked(true);
            mostrarComponente(rdbAnam_trastornos, tbxAnam_cual_trastornos);
            tbxAnam_cual_trastornos.setValue(odontologia
                    .getAnam_cual_trastornos());
            Radio radio14 = (Radio) rdbAnam_patologia
                    .getFellow("Anam_patologia"
                            + odontologia.getAnam_patologia());
            radio14.setChecked(true);
            mostrarComponente(rdbAnam_patologia, tbxAnam_cual_patologia);
            tbxAnam_cual_patologia.setValue(odontologia
                    .getAnam_cual_patologia());
            Radio radio15 = (Radio) rdbAnam_trastornos_gastricos
                    .getFellow("Anam_trastornos_gastricos"
                            + odontologia.getAnam_trastornos_gastricos());
            radio15.setChecked(true);
            mostrarComponente(rdbAnam_trastornos_gastricos,
                    tbxAnam_cual_trastornos_gastricos);
            tbxAnam_cual_trastornos_gastricos.setValue(odontologia
                    .getAnam_cual_trastornos_gastricos());
            Radio radio16 = (Radio) rdbAnam_epilepsia
                    .getFellow("Anam_epilepsia"
                            + odontologia.getAnam_epilepsia());
            radio16.setChecked(true);
            mostrarComponente(rdbAnam_epilepsia, tbxAnam_cual_epilepsia);
            tbxAnam_cual_epilepsia.setValue(odontologia
                    .getAnam_cual_epilepsia());
            Radio radio17 = (Radio) rdbAnam_cirugias.getFellow("Anam_cirugias"
                    + odontologia.getAnam_cirugias());
            radio17.setChecked(true);
            mostrarComponente(rdbAnam_cirugias, tbxAnam_cual_cirugias);
            tbxAnam_cual_cirugias.setValue(odontologia.getAnam_cual_cirugias());
            Radio radio18 = (Radio) rdbAnam_protasis.getFellow("Anam_protasis"
                    + odontologia.getAnam_protasis());
            radio18.setChecked(true);
            Utilidades.seleccionarListitem(lbxAnam_cual_protasis,
                    odontologia.getAnam_cual_protasis());

            Radio radio19 = (Radio) rdbAnam_otro.getFellow("Anam_otro"
                    + odontologia.getAnam_otro());
            radio19.setChecked(true);
            mostrarComponente(rdbAnam_otro, tbxAnam_cual_otros);
            tbxAnam_cual_otros.setValue(odontologia.getAnam_cual_otros());
            Radio radio20 = (Radio) rdbSintoma_respiratorio
                    .getFellow("Sintoma_respiratorio"
                            + odontologia.getSintoma_respiratorio());
            radio20.setChecked(true);
            // mostrarComponente(rdbSintoma_respiratorio, t);
            Radio radio21 = (Radio) rdbSintoma_piel.getFellow("Sintoma_piel"
                    + odontologia.getSintoma_piel());
            radio21.setChecked(true);

            Utilidades.seleccionarListitem(lbxAnte_fam_hipertension,
                    odontologia.getAnte_fam_hipertension());

            Utilidades.seleccionarListitem(lbxAnte_fam_ecv,
                    odontologia.getAnte_fam_ecv());

            Utilidades.seleccionarListitem(lbxAnte_fam_enf_coronaria,
                    odontologia.getAnte_fam_enf_coronaria());

            Utilidades.seleccionarListitem(lbxAnte_fam_muerte_im_acv,
                    odontologia.getAnte_fam_muerte_im_acv());

            Utilidades.seleccionarListitem(lbxAnte_fam_dislipidemia,
                    odontologia.getAnte_fam_dislipidemia());

            Utilidades.seleccionarListitem(lbxAnte_fam_nefropatias,
                    odontologia.getAnte_fam_nefropatias());

            Utilidades.seleccionarListitem(lbxAnte_fam_obesos,
                    odontologia.getAnte_fam_obesos());

            Utilidades.seleccionarListitem(lbxAnte_fam_diabetes,
                    odontologia.getAnte_fam_diabetes());

            Utilidades.seleccionarListitem(lbxAnte_fam_enf_mental,
                    odontologia.getAnte_fam_enf_mental());

            Utilidades.seleccionarListitem(lbxAnte_fam_cancer,
                    odontologia.getAnte_fam_cancer());

            Utilidades.seleccionarListitem(lbxAnte_fam_hematologia,
                    odontologia.getAnte_fam_hematologia());

            Utilidades.seleccionarListitem(lbxAnte_fam_otros,
                    odontologia.getAnte_fam_otros());

            tbxAnte_fam_estecifique.setValue(odontologia
                    .getAnte_fam_estecifique());
            tbxAnte_fam_observaciones.setValue(odontologia
                    .getAnte_fam_observaciones());
            Radio radio22 = (Radio) rdbEsto_labio_sup
                    .getFellow("Esto_labio_sup"
                            + odontologia.getEsto_labio_sup());
            radio22.setChecked(true);
            mostrarComponente(rdbEsto_labio_sup, tbxEsto_cual_labio_sup);
            tbxEsto_cual_labio_sup.setValue(odontologia
                    .getEsto_cual_labio_sup());
            Radio radio23 = (Radio) rdbEsto_labio_inf
                    .getFellow("Esto_labio_inf"
                            + odontologia.getEsto_labio_inf());
            radio23.setChecked(true);
            mostrarComponente(rdbEsto_labio_inf, tbxEsto_cual_labio_inf);
            tbxEsto_cual_labio_inf.setValue(odontologia
                    .getEsto_cual_labio_inf());
            Radio radio24 = (Radio) rdbEsto_comisura.getFellow("Esto_comisura"
                    + odontologia.getEsto_comisura());
            radio24.setChecked(true);
            mostrarComponente(rdbEsto_comisura, tbxEsto_cual_comisura);
            tbxEsto_cual_comisura.setValue(odontologia.getEsto_cual_comisura());
            Radio radio25 = (Radio) rdbEsto_mucosa.getFellow("Esto_mucosa"
                    + odontologia.getEsto_mucosa());
            radio25.setChecked(true);
            mostrarComponente(rdbEsto_mucosa, tbxEsto_cual_mucosa);
            tbxEsto_cual_mucosa.setValue(odontologia.getEsto_cual_mucosa());
            Radio radio26 = (Radio) rdbEsto_surcos.getFellow("Esto_surcos"
                    + odontologia.getEsto_surcos());
            radio26.setChecked(true);
            mostrarComponente(rdbEsto_surcos, tbxEsto_cual_surcos);
            tbxEsto_cual_surcos.setValue(odontologia.getEsto_cual_surcos());
            Radio radio27 = (Radio) rdbEsto_frenillo.getFellow("Esto_frenillo"
                    + odontologia.getEsto_frenillo());
            radio27.setChecked(true);
            mostrarComponente(rdbEsto_frenillo, tbxEsto_cual_frenillo);
            tbxEsto_cual_frenillo.setValue(odontologia.getEsto_cual_frenillo());
            Radio radio28 = (Radio) getFellow("Esto_paladar_blando"
                    + odontologia.getEsto_paladar_blando());
            radio28.setChecked(true);
            mostrarComponente(radio28, tbxEsto_cual_paladar_blando);
            tbxEsto_cual_paladar_blando.setValue(odontologia
                    .getEsto_cual_paladar_blando());
            Radio radio29 = (Radio) getFellow("Esto_paladar_duro"
                    + odontologia.getEsto_paladar_duro());
            radio29.setChecked(true);
            mostrarComponente(radio29, tbxEsto_cual_paladar_duro);
            tbxEsto_cual_paladar_duro.setValue(odontologia
                    .getEsto_cual_paladar_duro());
            Radio radio30 = (Radio) rdbEsto_orofaringe
                    .getFellow("Esto_orofaringe"
                            + odontologia.getEsto_orofaringe());
            radio30.setChecked(true);
            mostrarComponente(rdbEsto_orofaringe, tbxEsto_cual_orofaringe);
            tbxEsto_cual_orofaringe.setValue(odontologia
                    .getEsto_cual_orofaringe());
            Radio radio31 = (Radio) rdbEsto_lengua.getFellow("Esto_lengua"
                    + odontologia.getEsto_lengua());
            radio31.setChecked(true);
            mostrarComponente(rdbEsto_lengua, tbxEsto_cual_lengua);
            tbxEsto_cual_lengua.setValue(odontologia.getEsto_cual_lengua());
            Radio radio32 = (Radio) rdbEsto_boca.getFellow("Esto_boca"
                    + odontologia.getEsto_boca());
            radio32.setChecked(true);
            mostrarComponente(rdbEsto_boca, tbxEsto_cual_boca);
            tbxEsto_cual_boca.setValue(odontologia.getEsto_cual_boca());
            Radio radio33 = (Radio) rdbEsto_reborde.getFellow("Esto_reborde"
                    + odontologia.getEsto_reborde());
            radio33.setChecked(true);
            mostrarComponente(rdbEsto_reborde, tbxEsto_cual_reborde);
            tbxEsto_cual_reborde.setValue(odontologia.getEsto_cual_reborde());
            Radio radio34 = (Radio) rdbEsto_salivares
                    .getFellow("Esto_salivares"
                            + odontologia.getEsto_salivares());
            radio34.setChecked(true);
            mostrarComponente(rdbEsto_salivares, tbxEsto_cual_salivares);
            tbxEsto_cual_salivares.setValue(odontologia
                    .getEsto_cual_salivares());
            Radio radio35 = (Radio) rdbEsto_dolor_muscular
                    .getFellow("Esto_dolor_muscular"
                            + odontologia.getEsto_dolor_muscular());
            radio35.setChecked(true);
            mostrarComponente(rdbEsto_dolor_muscular,
                    tbxEsto_cual_dolor_muscular);
            tbxEsto_cual_dolor_muscular.setValue(odontologia
                    .getEsto_cual_dolor_muscular());
            Radio radio36 = (Radio) rdbEsto_dolor_articular
                    .getFellow("Esto_dolor_articular"
                            + odontologia.getEsto_dolor_articular());
            radio36.setChecked(true);
            mostrarComponente(rdbEsto_dolor_articular,
                    tbxEsto_cual_dolor_articular);
            tbxEsto_cual_dolor_articular.setValue(odontologia
                    .getEsto_cual_dolor_articular());
            Radio radio37 = (Radio) rdbEsto_riudo_articular
                    .getFellow("Esto_riudo_articular"
                            + odontologia.getEsto_riudo_articular());
            radio37.setChecked(true);
            mostrarComponente(rdbEsto_riudo_articular,
                    tbxEsto_cual_ruido_articular);
            tbxEsto_cual_ruido_articular.setValue(odontologia
                    .getEsto_cual_ruido_articular());
            Radio radio38 = (Radio) rdbEsto_alt_mov.getFellow("Esto_alt_mov"
                    + odontologia.getEsto_alt_mov());
            radio38.setChecked(true);
            mostrarComponente(rdbEsto_alt_mov, tbxEsto_cual_alt_mov);
            tbxEsto_cual_alt_mov.setValue(odontologia.getEsto_cual_alt_mov());
            Radio radio39 = (Radio) rdbEsto_oclusion.getFellow("Esto_oclusion"
                    + odontologia.getEsto_oclusion());
            radio39.setChecked(true);
            mostrarComponente(rdbEsto_oclusion, tbxEsto_cual_oclusion);
            tbxEsto_cual_oclusion.setValue(odontologia.getEsto_cual_oclusion());
            Radio radio40 = (Radio) rdbEsto_bruxismo.getFellow("Esto_bruxismo"
                    + odontologia.getEsto_bruxismo());
            radio40.setChecked(true);
            mostrarComponente(rdbEsto_bruxismo, tbxEsto_cual_bruxismo);
            tbxEsto_cual_bruxismo.setValue(odontologia.getEsto_cual_bruxismo());
            Radio radio41 = (Radio) rdbEsto_forma.getFellow("Esto_forma"
                    + odontologia.getEsto_forma());
            radio41.setChecked(true);
            mostrarComponente(rdbEsto_forma, tbxEsto_cual_forma);
            tbxEsto_cual_forma.setValue(odontologia.getEsto_cual_forma());
            Radio radio42 = (Radio) rdbEsto_tamano.getFellow("Esto_tamano"
                    + odontologia.getEsto_tamano());
            radio42.setChecked(true);
            mostrarComponente(rdbEsto_tamano, tbxEsto_cual_tamano);
            tbxEsto_cual_tamano.setValue(odontologia.getEsto_cual_tamano());
            Radio radio43 = (Radio) rdbEsto_numero.getFellow("Esto_numero"
                    + odontologia.getEsto_numero());
            radio43.setChecked(true);
            mostrarComponente(rdbEsto_numero, tbxEsto_cual_numero);
            tbxEsto_cual_numero.setValue(odontologia.getEsto_cual_numero());
            Radio radio44 = (Radio) rdbEsto_color.getFellow("Esto_color"
                    + odontologia.getEsto_color());
            radio44.setChecked(true);
            mostrarComponente(rdbEsto_color, tbxEsto_cual_color);
            tbxEsto_cual_color.setValue(odontologia.getEsto_cual_color());
            Radio radio45 = (Radio) rdbEsto_posicion.getFellow("Esto_posicion"
                    + odontologia.getEsto_posicion());
            radio45.setChecked(true);

            tbxEsto_cual_posicion.setValue(odontologia.getEsto_cual_posicion());
            Radio radio46 = (Radio) rdbEsto_impactados
                    .getFellow("Esto_impactados"
                            + odontologia.getEsto_impactados());
            radio46.setChecked(true);
            tbxEsto_cual_impactados.setValue(odontologia
                    .getEsto_cual_impactados());
            Radio radio47 = (Radio) rdbEsto_abfracciones
                    .getFellow("Esto_abfracciones"
                            + odontologia.getEsto_abfracciones());
            radio47.setChecked(true);
            tbxEsto_cual_abfracciones.setValue(odontologia
                    .getEsto_cual_abfracciones());
            Radio radio48 = (Radio) rdbEsto_inflamacion
                    .getFellow("Esto_inflamacion"
                            + odontologia.getEsto_inflamacion());
            radio48.setChecked(true);
            tbxEsto_cual_inflamacion.setValue(odontologia
                    .getEsto_cual_inflamacion());
            Radio radio49 = (Radio) rdbEsto_movilidad
                    .getFellow("Esto_movilidad"
                            + odontologia.getEsto_movilidad());
            radio49.setChecked(true);
            tbxEsto_cual_movilidad.setValue(odontologia
                    .getEsto_cual_movilidad());
            Radio radio50 = (Radio) rdbEsto_recesiones
                    .getFellow("Esto_recesiones"
                            + odontologia.getEsto_recesiones());
            radio50.setChecked(true);
            tbxEsto_cual_recesiones.setValue(odontologia
                    .getEsto_cual_recesiones());
            Radio radio51 = (Radio) rdbEsto_bolsas.getFellow("Esto_bolsas"
                    + odontologia.getEsto_bolsas());
            radio51.setChecked(true);
            tbxEsto_cual_bolsas.setValue(odontologia.getEsto_cual_bolsas());
            Radio radio52 = (Radio) rdbEsto_calculo.getFellow("Esto_calculo"
                    + odontologia.getEsto_calculo());
            radio52.setChecked(true);
            tbxEsto_cual_calculo.setValue(odontologia.getEsto_cual_calculo());
            Radio radio53 = (Radio) rdbEsto_abceso.getFellow("Esto_abceso"
                    + odontologia.getEsto_abceso());
            radio53.setChecked(true);
            tbxEsto_cual_abceso.setValue(odontologia.getEsto_cual_abceso());
            Radio radio54 = (Radio) rdbEsto_sangrado.getFellow("Esto_sangrado"
                    + odontologia.getEsto_sangrado());
            radio54.setChecked(true);
            tbxEsto_cual_sangrado.setValue(odontologia.getEsto_cual_sangrado());
            Radio radio55 = (Radio) rdbEsto_alt_vital
                    .getFellow("Esto_alt_vital"
                            + odontologia.getEsto_alt_vital());
            radio55.setChecked(true);
            tbxEsto_cual_alt_vital.setValue(odontologia
                    .getEsto_cual_alt_vital());
            Radio radio56 = (Radio) rdbEsto_percusion
                    .getFellow("Esto_percusion"
                            + odontologia.getEsto_percusion());
            radio56.setChecked(true);
            tbxEsto_cual_percusion.setValue(odontologia
                    .getEsto_cual_percusion());
            Radio radio57 = (Radio) rdbEsto_mov_dental
                    .getFellow("Esto_mov_dental"
                            + odontologia.getEsto_mov_dental());
            radio57.setChecked(true);
            tbxEsto_cual_mov_dental.setValue(odontologia
                    .getEsto_cual_mov_dental());
            Radio radio58 = (Radio) rdbEsto_fistula.getFellow("Esto_fistula"
                    + odontologia.getEsto_fistula());
            radio58.setChecked(true);
            tbxEsto_cual_fistula.setValue(odontologia.getEsto_cual_fistula());
            Radio radio59 = (Radio) rdbEsto_diente.getFellow("Esto_diente"
                    + odontologia.getEsto_diente());
            radio59.setChecked(true);
            tbxEsto_cual_diente.setValue(odontologia.getEsto_cual_diente());
            Radio radio60 = (Radio) rdbEsto_oral.getFellow("Esto_oral"
                    + odontologia.getEsto_oral());
            radio60.setChecked(true);
            tbxEsto_cual_oral.setValue(odontologia.getEsto_cual_oral());
            Radio radio61 = (Radio) rdbEsto_digital.getFellow("Esto_digital"
                    + odontologia.getEsto_digital());
            radio61.setChecked(true);
            tbxEsto_cual_digital.setValue(odontologia.getEsto_cual_digital());
            Radio radio62 = (Radio) rdbEsto_lengua_protactil
                    .getFellow("Esto_lengua_protactil"
                            + odontologia.getEsto_lengua_protactil());
            radio62.setChecked(true);
            tbxEsto_cual_lengua_protactil.setValue(odontologia
                    .getEsto_cual_lengua_protactil());
            Radio radio63 = (Radio) rdbEsto_queilosfagia
                    .getFellow("Esto_queilosfagia"
                            + odontologia.getEsto_queilosfagia());
            radio63.setChecked(true);
            tbxEsto_cual_queilosfagia.setValue(odontologia
                    .getEsto_cual_queilosfagia());
            Radio radio64 = (Radio) rdbEsto_fumador.getFellow("Esto_fumador"
                    + odontologia.getEsto_fumador());
            radio64.setChecked(true);
            tbxEsto_cual_fumador.setValue(odontologia.getEsto_cual_fumador());
            Radio radio65 = (Radio) rdbEsto_alcohol.getFellow("Esto_alcohol"
                    + odontologia.getEsto_alcohol());
            radio65.setChecked(true);
            tbxEsto_cual_alcohol.setValue(odontologia.getEsto_cual_alcohol());
            Radio radio66 = (Radio) rdbEsto_otro.getFellow("Esto_otro"
                    + odontologia.getEsto_otro());
            radio66.setChecked(true);
            tbxEsto_cual_otros.setValue(odontologia.getEsto_cual_otros());
            Radio radio67 = (Radio) rdbPrev_charla.getFellow("Prev_charla"
                    + odontologia.getPrev_charla());
            radio67.setChecked(true);
            tbxPrev_frec_charla.setValue(odontologia.getPrev_frec_charla());
            Radio radio68 = (Radio) rdbPrev_cepillado
                    .getFellow("Prev_cepillado"
                            + odontologia.getPrev_cepillado());
            radio68.setChecked(true);
            tbxPrev_frec_cepillado.setValue(odontologia
                    .getPrev_frec_cepillado());
            Radio radio69 = (Radio) rdbPrev_seda.getFellow("Prev_seda"
                    + odontologia.getPrev_seda());
            radio69.setChecked(true);
            tbxPrev_frec_seda.setValue(odontologia.getPrev_frec_seda());
            Radio radio70 = (Radio) rdbPrev_enjuague.getFellow("Prev_enjuague"
                    + odontologia.getPrev_enjuague());
            radio70.setChecked(true);
            tbxPrev_frec_enjuague.setValue(odontologia.getPrev_frec_enjuague());
            Radio radio71 = (Radio) rdbPrev_fluor.getFellow("Prev_fluor"
                    + odontologia.getPrev_fluor());
            radio71.setChecked(true);
            tbxPrev_frec_fluor.setValue(odontologia.getPrev_frec_fluor());
            Radio radio72 = (Radio) rdbPrev_sellante.getFellow("Prev_sellante"
                    + odontologia.getPrev_sellante());
            radio72.setChecked(true);
            tbxPrev_frec_sellante.setValue(odontologia.getPrev_frec_sellante());
            tbxPrev_observacions.setValue(odontologia.getPrev_observacions());

            Radio radio73 = (Radio) rdbFluorisis.getFellow("Fluorisis"
                    + odontologia.getFluorisis());
            radio73.setChecked(true);

            Utilidades.seleccionarListitem(lbxTipo_fluorisis,
                    odontologia.getTipo_fluorisis());

            chbTra_educacion_oral.setChecked(odontologia
                    .getTra_educacion_oral());
            chbTra_control_placa.setChecked(odontologia.getTra_control_placa());
            chbTra_profilaxis.setChecked(odontologia.getTra_profilaxis());
            chbSellante.setChecked(odontologia.getSellante());
            chbFluor.setChecked(odontologia.getFluor());
            chbTra_amalgamas.setChecked(odontologia.getTra_amalgamas());
            mostrarComponente(chbTra_amalgamas, ibxNum_diente_traAmalgama);
            chbTra_resinas.setChecked(odontologia.getTra_resinas());
            mostrarComponente(chbTra_resinas, ibxNro_diente_trRecinas);
            chbTra_ionimeros.setChecked(odontologia.getTra_ionimeros());
            Radio radio74 = (Radio) rdbTra_endodoncia
                    .getFellow("Tra_endodoncia"
                            + odontologia.getTra_endodoncia());
            radio74.setChecked(true);
            mostrarComponente(rdbTra_endodoncia, ibxEndodoncia_num_diente);
            Radio radio75 = (Radio) rdbTra_detartraje_supragingival
                    .getFellow("Tra_detartraje_supragingival"
                            + odontologia.getTra_detartraje_supragingival());
            radio75.setChecked(true);
            chbTra_i.setChecked(odontologia.getTra_i());
            chbTra_ii.setChecked(odontologia.getTra_ii());
            chbTra_iii.setChecked(odontologia.getTra_iii());
            chbTra_iv.setChecked(odontologia.getTra_iv());
            Radio radio76 = (Radio) rdbTra_prostodoncia
                    .getFellow("Tra_prostodoncia"
                            + odontologia.getTra_prostodoncia());
            radio76.setChecked(true);
            mostrarComponente(rdbTra_prostodoncia, ibxProtodoncia_num_diente);
            Utilidades.seleccionarListitem(lbxTra_cirugia,
                    odontologia.getTra_cirugia());
            mostrarComponente(lbxTra_cirugia, ibxCirugia_num_diente);
            ibxCirugia_num_diente.setValue(odontologia.getCirugia_num_diente());
            ibxEndodoncia_num_diente.setValue(odontologia
                    .getEndodoncia_num_diente());
            ibxProtodoncia_num_diente.setValue(odontologia
                    .getProtodoncia_num_diente());
            ibxNum_diente_traAmalgama.setValue(odontologia
                    .getNum_diente_tra_amalgama());
            ibxNro_diente_trRecinas.setValue(odontologia
                    .getNro_diente_tra_aecinas());
            ibxNro_diente_traIonimeros.setValue(odontologia
                    .getNro_diente_tra_ionimeros());
            mostrarComponente(chbTra_ionimeros, ibxNro_diente_traIonimeros);
            dbxPresion.setValue(ConvertidorDatosUtil.convertirDato(odontologia
                    .getPresion()));
            dbxPresion2.setValue(ConvertidorDatosUtil.convertirDato(odontologia
                    .getPresion2()));
            dbxTalla.setValue(ConvertidorDatosUtil.convertirDato(odontologia
                    .getTalla()));
            dbxPeso.setValue(ConvertidorDatosUtil.convertirDato(odontologia
                    .getPeso()));

			// tbxTra_num_prostodoncia.setValue(odontologia
            // .getTra_num_prostodoncia());
            tbxRemision.setValue(odontologia.getRemision());
            tbxCedula_acomp.setValue(odontologia.getCedula_acomp());

            tbxPronostico.setValue(odontologia.getPronostico());

            Radio radio79 = (Radio) rdbViolencia.getFellow("Violencia"
                    + odontologia.getViolencia());
            radio79.setChecked(true);
            Radio radio80 = (Radio) rdbFisico.getFellow("Fisico"
                    + odontologia.getFisico());
            radio80.setChecked(true);
            Radio radio81 = (Radio) rdbSexual.getFellow("Sexual"
                    + odontologia.getSexual());
            radio81.setChecked(true);
            Radio radio82 = (Radio) rdbEmocional.getFellow("Emocional"
                    + odontologia.getEmocional());
            radio82.setChecked(true);
            Radio radio83 = (Radio) rdbRiesgo.getFellow("Riesgo"
                    + odontologia.getRiesgo());
            radio83.setChecked(true);
            Radio radio84 = (Radio) rdbTema.getFellow("Tema"
                    + odontologia.getTema());
            radio84.setChecked(true);

            cargarAnexo9_remision(odontologia);

            cargarImpresionDiagnostica(odontologia);
            // calcularCoordenadas();
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(false);

            // Mostramos la vista //
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            Messagebox.show("Este dato no se puede editar", "Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Odontologia odontologia = (Odontologia) obj;
        try {
            int result = getServiceLocator().getOdontologiaService().eliminar(
                    odontologia);
            if (result == 0) {
                throw new Exception(
                        "Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
            }

            Messagebox.show("InformaciÃ³n se eliminÃ³ satisfactoriamente !!",
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

    public void buscarPrestador(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
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

            e.printStackTrace();
            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Mensaje de Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void selectedPrestador(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxCodigo_prestador1.setValue("");
            tbxNomPrestador.setValue("");
        } else {
            Prestadores dato = (Prestadores) listitem.getValue();
            tbxCodigo_prestador1.setValue(dato.getNro_identificacion());
            tbxNomPrestador.setValue(dato.getNombres() + " "
                    + dato.getApellidos());
        }
        tbxCodigo_prestador1.close();
    }

    public void buscarDx(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");
            parameters.put("clasificacion", "01");

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

    public void seleccion(Listbox listbox, int entero,
            AbstractComponent[] abstractComponents) {
        try {

            String num = entero + "";
            for (AbstractComponent abstractComponent : abstractComponents) {

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
            e.printStackTrace();
        }
    }

    public void seleccion_radio(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {

            for (AbstractComponent abstractComponent : abstractComponents) {

                if (radiogroup.getSelectedItem().getValue().equals("1")) {
                    abstractComponent.setVisible(true);
                    if (abstractComponent instanceof Datebox) {
                        ((Datebox) abstractComponent).setValue(new Date());

                    }

                    if (abstractComponent instanceof Radiogroup) {
                        ((Radio) ((Radiogroup) abstractComponent)
                                .getFirstChild()).setChecked(true);
                    }

                    if (abstractComponent instanceof Listbox) {
                        if (((Listbox) abstractComponent).getItemCount() > 0) {
                            ((Listbox) abstractComponent).setSelectedIndex(0);
                        }
                        listarElementoListbox(((Listbox) abstractComponent),
                                true);
                    }

                    if (abstractComponent instanceof Checkbox) {
                        ((Checkbox) abstractComponent).setChecked(false);
                    }

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setText("");
                        ((Textbox) abstractComponent).setVisible(true);

                    }

                    // textbox.setVisible(true);
                } else {
                    // label.setVisible(false);

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");

                    }

                    if (abstractComponent instanceof Radiogroup) {
                        ((Radio) ((Radiogroup) abstractComponent)
                                .getFirstChild()).setChecked(true);
                    }
                    if (abstractComponent instanceof Datebox) {
                        ((Datebox) abstractComponent).setValue(null);

                    }

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");

                    }

                    if (abstractComponent instanceof Listbox) {
                        ((Listbox) abstractComponent).getChildren().clear();
                        for (int i = 0; i < ((Listbox) abstractComponent)
                                .getItemCount(); i++) {
                            Listitem listitem = ((Listbox) abstractComponent)
                                    .getItemAtIndex(i);
                            if (listitem.getValue().toString().equals("")) {
                                listitem.setSelected(true);
                                break;
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
			//  block
            // //System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    public void seleccion_radio2(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            // //System.Out.Println("" + radiogroup.getSelectedItem().getValue());

            for (AbstractComponent abstractComponent : abstractComponents) {

                if (radiogroup.getSelectedItem().getValue().equals("1")) {
                    abstractComponent.setVisible(true);

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");
                    }

                    // textbox.setVisible(true);
                } else {

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");

                    }

                    abstractComponent.setVisible(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo verificamos la edad del paciente, para visualizar el
     * contenedor de edad de 2 - 5
     *
     *
     */
    private void verificarEdadParaAiepi(String nro_identificacion) {
        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(codigo_empresa);
        paciente.setCodigo_sucursal(codigo_sucursal);
        paciente.setNro_identificacion(nro_identificacion);
        paciente = getServiceLocator().getPacienteService().consultar(paciente);
        if (paciente != null) {
            int edad = Integer.parseInt(Funcion_getEdad.getYears(paciente
                    .getFecha_nacimiento()));
            groupVerificarSaludBucal.setVisible(edad >= 2 && edad <= 5);
        }
    }

    public void cargarPrestador() throws Exception {
        if (rol_usuario.equals("05")) {
            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(admision.getCodigo_empresa());
            prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
            prestadores.setNro_identificacion(usuarios.getCodigo());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);
            if (prestadores == null) {
                throw new Exception(
                        "Usuario no esta creado en el modulo de prestadore, actualize informaciÃ³n de usuario");
            }
            tbxCodigo_prestador1.setValue(prestadores.getNro_identificacion());
            tbxNomPrestador.setValue(prestadores.getNombres() + " "
                    + prestadores.getApellidos());
        } else {
            if (admision != null) {
                Prestadores prestadores = new Prestadores();
                prestadores.setCodigo_empresa(admision.getCodigo_empresa());
                prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
                prestadores.setNro_identificacion(admision.getCodigo_medico());
                prestadores = getServiceLocator().getPrestadoresService()
                        .consultar(prestadores);

                tbxCodigo_prestador1
                        .setValue((prestadores != null ? prestadores
                                .getNro_identificacion() : "000000000"));
                tbxNomPrestador.setValue((prestadores != null ? prestadores
                        .getNombres() + " " + prestadores.getApellidos()
                        : "MEDICO POR DEFECTO"));
            } else {
                tbxCodigo_prestador1.setValue("000000000");
                tbxNomPrestador.setValue("MEDICO POR DEFECTO");
            }
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

            parameters.put("limite_paginado", "limit 25 offset 0");

            List<Odontologia> lista_datos = getServiceLocator()
                    .getOdontologiaService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Odontologia odontologia : lista_datos) {
                rowsResultado.appendChild(crearFilas(odontologia, this));
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

        final Odontologia odontologia = (Odontologia) objeto;

        Paciente registro = new Paciente();
        registro.setCodigo_empresa(odontologia.getCodigo_empresa());
        registro.setCodigo_sucursal(odontologia.getCodigo_sucursal());
        registro.setNro_identificacion(odontologia.getIdentificacion());
        registro = getServiceLocator().getPacienteService().consultar(registro);

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(odontologia.getNro_ingreso() + ""));
        fila.appendChild(new Label(odontologia.getIdentificacion() + ""));

        // 03
        fila.appendChild(new Label(registro != null ? registro.getNombre1()
                + " " + registro.getNombre2() + " " + registro.getApellido1()
                + " " + registro.getApellido2() : ""));
        fila.appendChild(new Label(Utilidades.formatDate(
                odontologia.getFecha_inicial(), "yyyy-MM-dd")
                + ""));

        String tipo = "Primera Vez";
        fila.appendChild(new Label(tipo + ""));

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
                        groupboxConsulta.setVisible(false);
                        groupboxEditar.setVisible(true);
                        mostrarDatos(odontologia);
                    }
                });
        hlayout.appendChild(btn_mostrar);

        fila.appendChild(hlayout);

        return fila;
    }

    @Override
    public void init() {
        try {
            listarCombos();
            FormularioUtil.inicializarRadiogroupsDefecto(this);
            macroImpresion_diagnostica.inicializarMacro(this, admision,
                    IVias_ingreso.ODONTOLOGIA);
            macroRemision_interna.inicializarMacro(this, admision,
                    IVias_ingreso.ODONTOLOGIA2);
            macroConsentimiento_inf_odon.inicializarMacro(this, admision,
                    IVias_ingreso.ODONTOLOGIA2);
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void onCreate() throws Exception {
        if (odontologiaTemp != null) {
            Map map = new HashMap();
            map.put("codigo_empresa", codigo_empresa);
            map.put("codigo_sucursal", codigo_sucursal);
            map.put("evolucion", "N");
            map.put("nro_historia", odontologiaTemp.getCodigo_historia());

            /* cargamos odontograma */
            odtodontograma.setDientes(getServiceLocator().getDienteService()
                    .listar(map));
            setIndiceDean(getServiceLocator().getIndice_deanService().listar(
                    map));

            // valores del odontograma
            odtodontograma.setCariadosAdulto(odontologiaTemp
                    .getOdont_grama_cariados_adulto());
            odtodontograma.setCariadosLeche(odontologiaTemp
                    .getOdont_grama_cariados_leche());
            odtodontograma.setCeoLeche(odontologiaTemp
                    .getOdont_grama_ceo_leche());
            odtodontograma.setCopAdulto(odontologiaTemp
                    .getOdont_grama_cop_adulto());
            odtodontograma.setEstraidosLeche(odontologiaTemp
                    .getOdont_grama_estraidos_leche());
            odtodontograma.setOpturadosAdulto(odontologiaTemp
                    .getOdont_grama_opturados_adulto());
            odtodontograma.setOpturadoLeche(odontologiaTemp
                    .getOdont_grama_opturados_leche());
            odtodontograma.setPerdidosAdulto(odontologiaTemp
                    .getOdont_grama_perdidos_adulto());
            odtodontograma.setSuperficiesManchadas(odontologiaTemp
                    .getOdont_grama_porcentaje_manchado());
            odtodontograma
                    .setTipoDenticion(odontologiaTemp.getModo_denticion());

            if (mostrar_evolucion_2) {
                padre.cargarEvolucionOdonotologia(odontologiaTemp,
                        OpcionesFormulario.REGISTRAR, lista_procedimientos,
                        false);
				// padre.cargarRegClinicoHigiene(odontologiaTemp, false,
                // OpcionesFormulario.REGISTRAR);
            }
        }
    }

    @Override
    public void initHistoria() throws Exception {
        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
            via_ingreso = IVias_ingreso.ODONTOLOGIA2;
            accionForm(OpcionesFormulario.CONSULTAR, null);
        } else if (admision != null) {
            toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
                    .getNombreCompleto());
            toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
                    .getNombreCompleto());

            verificarEdadParaAiepi(admision.getNro_identificacion());

            if (admision.getAtendida()) {
                opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
                if (odontologiaTemp != null) {
                    accionForm(OpcionesFormulario.MOSTRAR, odontologiaTemp);
                    infoPacientes.setCodigo_historia(odontologiaTemp
                            .getCodigo_historia());
                    infoPacientes.setFecha_inicio(odontologiaTemp
                            .getFecha_inicial());
                } else {
                    groupboxEditar.setVisible(false);
                    throw new Exception(
                            "NO hay una historia clinica relacionada a este paciente admitido");
                }
            } else {
                if (odontologiaTemp != null) {
                    opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
                    accionForm(OpcionesFormulario.MOSTRAR, odontologiaTemp);
                    infoPacientes.setCodigo_historia(odontologiaTemp
                            .getCodigo_historia());
                    infoPacientes.setFecha_inicio(odontologiaTemp
                            .getFecha_inicial());
                    mostrar_evolucion_2 = true;
                } else {
                    if (opciones_via_ingreso
                            .equals(Opciones_via_ingreso.REGISTRAR)) {
                        accionForm(OpcionesFormulario.REGISTRAR, null);
                    } else {
                        accionForm(OpcionesFormulario.CONSULTAR, null);
                    }
                    cargarPrestador();
                }

            }

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
                        idsExc);
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
            infoPacientes.setFecha_inicio(new Date());
            cargarInformacion_paciente();
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(true);
            onMostrarModuloRemisiones();
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

    @Override
    public void params(Map<String, Object> map) {
        if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
            admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
            macroRemision_interna.deshabilitarCheck(admision,
                    IVias_ingreso.ODONTOLOGIA2);
            opciones_via_ingreso = (Opciones_via_ingreso) map
                    .get(IVias_ingreso.OPCION_VIA_INGRESO);
            if (map.get(IVias_ingreso.PADRE) instanceof Modulo_asistencialAction) {
                padre = (Modulo_asistencialAction) map.get("padre");
            } else if (map.get(IVias_ingreso.PADRE) instanceof Consulta_historiasAction) {
                padre = (Consulta_historiasAction) map.get("padre");
            }
            odontologiaTemp = (Odontologia) map.get("odontologiaTemp");
            cita_seleccionada = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
        }
        if (map.containsKey("via_ingreso")) {
            via_ingreso = (String) map.get("via_ingreso");
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

    @Override
    public void cargarInformacion_paciente() {
        infoPacientes.cargarInformacion(admision, this,
                new InformacionPacienteIMG() {
                    @Override
                    public void ejecutarProceso() throws Exception {
                        //log.info("cita_seleccionada>>>>>" + cita_seleccionada);
                        if (cita_seleccionada != null
                        && cita_seleccionada.getNro_identificacion() != null) {
                            tbxAcompaniante.setValue(cita_seleccionada
                                    .getAcompaniante()
                                    + " "
                                    + cita_seleccionada.getApellidos_acomp());
                            ibxTel_acompaniante.setValue(ConvertidorDatosUtil
                                    .convertirDato(cita_seleccionada
                                            .getTel_acompaniante()));
							//log.info("===> relacion"
                            //+ cita_seleccionada.getRelacion());
                            Utilidades.seleccionarListitem(lbxRelacion,
                                    cita_seleccionada.getRelacion());
                            tbxCedula_acomp.setValue(cita_seleccionada
                                    .getCedula_acomp());
                        }

                    }
                });

    }

    @Override
    public void cargarInformacion_historia_anterior(Object historia_anterior) {
        tbxAccion.setValue(OpcionesFormulario.REGISTRAR.toString());
    }

    @Override
    public void initMostrar_datos(Object historia_clinica) {
        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
            FormularioUtil
                    .deshabilitarComponentes(groupboxEditar, true, idsExc);

            toolbarbuttonTipo_historia
                    .setLabel("Mostrando Historia Clinica por Primera Vez");
        } else {
            toolbarbuttonTipo_historia
                    .setLabel("Modificando Historia Clinica por Primera Vez");
        }
    }

    private void cargarImpresionDiagnostica(Odontologia odontologia)
            throws Exception {
        Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
        impresion_diagnostica.setCodigo_empresa(codigo_empresa);
        impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
        impresion_diagnostica.setCodigo_historia(odontologia
                .getCodigo_historia());
        impresion_diagnostica = getServiceLocator().getServicio(
                Impresion_diagnosticaService.class).consultar(
                        impresion_diagnostica);
        if (impresion_diagnostica != null) {
            macroImpresion_diagnostica.mostrarImpresionDiagnostica(
                    impresion_diagnostica, true);
        }
    }

    public void onMostrarModuloOrdenamiento() throws Exception {
        if (orden_servicioAction != null) {
            orden_servicioAction.detach();
        }
		// if (!cargo_farmacologico) {
        //log.info("creando la ventana receta_ripsAction");
        String nro_ingreso_actual = admision.getNro_ingreso();
        admision.setNro_ingreso(odontologiaTemp != null ? odontologiaTemp
                .getNro_ingreso() : nro_ingreso_actual);
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
        divModuloOrdenamiento.appendChild(orden_servicioAction);
        admision.setNro_ingreso(nro_ingreso_actual);
		// cargo_farmacologico = true;
        // }

    }

    private void cargarRemisionInterna(Odontologia odontologia)
            throws Exception {
        Remision_interna remision_interna = new Remision_interna();
        remision_interna.setCodigo_historia(odontologia.getCodigo_historia());
        remision_interna.setCodigo_empresa(odontologia.getCodigo_empresa());
        remision_interna.setCodigo_sucursal(odontologia.getCodigo_sucursal());

        remision_interna = getServiceLocator().getServicio(
                Remision_internaService.class).consultar(remision_interna);

        macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
    }

    private void cargarConsentimeinto_inf_odon(Odontologia odontologia)
            throws Exception {
        Consentimiento_inf_odon consentimineto_inf_odon = new Consentimiento_inf_odon();
        consentimineto_inf_odon.setCodigo_historia(odontologia
                .getCodigo_historia());
        consentimineto_inf_odon.setCodigo_empresa(odontologia
                .getCodigo_empresa());
        consentimineto_inf_odon.setCodigo_sucursal(odontologia
                .getCodigo_sucursal());

        consentimineto_inf_odon = getServiceLocator().getServicio(
                Consentimiento_inf_odonService.class).consultar(
                        consentimineto_inf_odon);

        macroConsentimiento_inf_odon.mostrarConsentimiento_inf(
                consentimineto_inf_odon, true);
    }

    private void cargarAnexo9_remision(Odontologia odontologia) {
        Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
        anexo9_entidad.setCodigo_empresa(codigo_empresa);
        anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
        anexo9_entidad.setNro_historia(odontologia.getCodigo_historia());
        anexo9_entidad.setNro_ingreso(odontologia.getNro_ingreso());
        anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
                .consultar(anexo9_entidad);

        boolean creada = false;
        if (anexo9_entidad != null) {
            creada = true;
        }
        remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
        if (creada) {
            remisiones_externasAction.getBotonGenerar().setVisible(false);
        } else {
            remisiones_externasAction.setNro_historia(odontologia
                    .getCodigo_historia());
        }
        //log.info("cargando anexo9_entidad === >" + anexo9_entidad);
        remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
    }

    public void onMostrarModuloRemisiones() throws Exception {
        if (remisiones_externasAction != null) {
            remisiones_externasAction.detach();
        }
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
            dbxPresion.setValue(enfermera_signos.getTa_sistolica());
            dbxPresion2.setValue(enfermera_signos.getTa_diastolica());
            dbxTalla.setValue(enfermera_signos.getTalla());
            dbxPeso.setValue(enfermera_signos.getPeso());
        }
    }

    public void imprimir() throws Exception {
        Long nro_historia = infoPacientes.getCodigo_historia();
        if (nro_historia == null) {
            Messagebox.show("La historia no se ha guardado aÃºn",
                    "InformaciÃ³n ..", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }

        Map<String, Object> mapDientes = odtodontograma.getMap(
                DienteOdontograma.ALL, false);

        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "Historia_clinica_odontologica");
        paramRequest.put("nro_historia", nro_historia);
        paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
                .toString());
        paramRequest.put("mapDientes", mapDientes);
        paramRequest.put("porcentaje_manchado", odtodontograma
                .getTbxPorcentajeManchado().getValue());

        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", this, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);

        // Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
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
        if (tbxEnfermedad_actual.getValue() != null
                && !tbxEnfermedad_actual.getValue().isEmpty()) {
            informacion_clinica.append("\t")
                    .append(tbxEnfermedad_actual.getValue()).append("\n");
        }

        if (dbxPresion.getValue() != null || dbxPresion2.getValue() != null
                || dbxTalla.getValue() != null || dbxPeso.getValue() != null) {
            informacion_clinica.append("\n");
            informacion_clinica.append("- EXAMEN FISICO :");
            informacion_clinica
                    .append("\t")
                    .append("TA Sistolica / TA Diastolica: "
                            + dbxPresion.getValue() + "/"
                            + dbxPresion2.getValue()).append("\t");
            informacion_clinica.append("\t");
            informacion_clinica.append("\t")
                    .append("Talla (cm): " + dbxTalla.getValue()).append("\t");
            informacion_clinica.append("\t")
                    .append("Peso (kg): " + dbxPeso.getValue()).append("\n");
        }

        informacion_clinica.append("\n");

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
                                    "tipo_diagnostico", this)).append("\n")
                    .append("\n");
        }

		// informacion_clinica.append("-PLANES Y MANEJO:").append("\n");
        //
        // Map<String, Object> mapReceta = receta_ripsAction.obtenerDatos();
        // List<Detalle_receta_rips> listaDetalles_horarios =
        // (List<Detalle_receta_rips>) mapReceta
        // .get("lista_detalle");
        //
        // informacion_clinica.append("RECETA:").append("\t");
        //
        // for (Detalle_receta_rips detalle_receta_rips :
        // listaDetalles_horarios) {
        // Articulo articulo = new Articulo();
        // articulo.setCodigo_empresa(detalle_receta_rips.getCodigo_empresa());
        // articulo.setCodigo_sucursal(detalle_receta_rips
        // .getCodigo_sucursal());
        // articulo.setCodigo_articulo(detalle_receta_rips
        // .getCodigo_articulo());
        //
        // articulo = getServiceLocator().getArticuloService().consultar(
        // articulo);
        // informacion_clinica
        // .append(detalle_receta_rips.getCantidad_recetada())
        // .append(articulo != null ? articulo.getNombre1() : "")
        // .append(", ").append(detalle_receta_rips.getPosologia())
        // .append("\t");
        //
        // }
        informacion_clinica.append("\n");
		// Manuales_tarifarios manuales_tarifarios =
        // ServiciosPacienteUtils.getManuales_tarifarios(admision_seleccionada);
        // Map<String, Object> mapOrdenamiento = orden_servicioAction
        // .obtenerDatos();
        // //log.info("mapa ordenamiento ===> " +mapOrdenamiento);
        // List<Detalle_orden> listaDetalle_orden =(List<Detalle_orden>)
        // mapOrdenamiento
        // .get("lista_detalle");
        // //log.info("====> lista orden " + listaDetalle_orden);
        // if(!listaDetalle_orden.isEmpty()){
        // informacion_clinica.append("-ORDENES:").append("\t");
        //
        // if(manuales_tarifarios.getManual_tarifario().equals("SOAT")){
        // Procedimiento procedimiento = new Procedimiento();
        //
        // for (Detalle_orden detalle_orden : listaDetalle_orden) {
        // procedimiento.setCodigo_soat(detalle_orden.getCodigo_procedimiento());
        //
        // procedimiento =
        // getServiceLocator().getProcedimientoService().consultar(procedimiento);
        // //log.info("=====> soat " + procedimiento);
        // informacion_clinica.append(procedimiento != null ?
        // procedimiento.getDescripcion() : "").append("- ")
        // .append(detalle_orden != null ? detalle_orden.getUnidades() :
        // "").append("\t");
        // }
        // } if(manuales_tarifarios.getManual_tarifario().equals("ISS02")){
        // Procedimiento_iss01 procedimiento_iss01 = new Procedimiento_iss01();
        //
        // for (Detalle_orden detalle_orden : listaDetalle_orden) {
        // procedimiento_iss01.setCodigo_soat(detalle_orden.getCodigo_procedimiento());
        //
        // procedimiento_iss01 =
        // getServiceLocator().getProcedimiento_iss01Service().consultar(procedimiento_iss01);
        // //log.info("=====> ISS02 " + procedimiento_iss01);
        // informacion_clinica.append(procedimiento_iss01 != null ?
        // procedimiento_iss01.getDescripcion() : "").append("- ")
        // .append(detalle_orden != null ? detalle_orden.getUnidades() :
        // "").append("\t");
        // }
        // }if(manuales_tarifarios.getManual_tarifario().equals("ISS04")){
        //
        // Procedimiento_iss04 procedimiento_iss04 = new Procedimiento_iss04();
        //
        // for (Detalle_orden detalle_orden : listaDetalle_orden) {
        // procedimiento_iss04.setCodigo_soat(detalle_orden.getCodigo_procedimiento());
        //
        // procedimiento_iss04 =
        // getServiceLocator().getProcedimiento_iss04Service().consultar(procedimiento_iss04);
        // //log.info("=====> iss04" + procedimiento_iss04);
        // informacion_clinica.append(procedimiento_iss04 != null ?
        // procedimiento_iss04.getDescripcion() : "").append("- ")
        // .append(detalle_orden != null ? detalle_orden.getUnidades() :
        // "").append("\t");
        //
        // }
        // }
        // }
        return informacion_clinica.toString();
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
        return ibxTel_acompaniante.getText();
    }

    @Override
    public String getServicioSolicitaReferencia1() {
        StringBuffer serivicio1 = new StringBuffer();
        /* */
        serivicio1.append("ODONTOLOgÃ­a GENERAL");
        return serivicio1.toString();
    }

}
