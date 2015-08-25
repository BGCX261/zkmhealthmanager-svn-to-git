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
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses_5_anios;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Cuadros_aiepi_resultadosService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Hisc_aiepi_mn_2_meses_5_aniosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.CuadroAIEPIMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.CuadroAiepiIMG;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.bean.Hisc_consulta_externa;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_aiepi_mn_2_meses_5_aniosAction extends
        HistoriaClinicaModel implements IHistoria_generica, CuadroAiepiIMG {

    private static Logger log = Logger
            .getLogger(Hisc_aiepi_mn_2_meses_5_aniosAction.class);
    
    private ContenedorPaginasMacro tabboxContendor;

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
    private Radiogroup rdbNino_registrado;
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
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI1;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI2;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI3;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI4;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI5;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI6;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI7;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI8;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI9;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI10;
    @View(isMacro = true)
    private CuadroAIEPIMacro macroCuadroAIEPI11;
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
    @View
    private Textbox tbxObservaciones_cuadro11;

    private Opciones_via_ingreso opciones_via_ingreso;
    private Admision admision;
    private String tipo_historia;
    private Long codigo_historia_anterior;
    private Paciente paciente;
    private ESexo sexo;
    private Timestamp fecha;

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
    private Datebox dtbxVolver_consulta_control;
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
    private Div divModuloRemisiones_externas;

    private Remisiones_externasAction remisiones_externasAction;

    // Modulo Farmacologico y Autorizaciones
    @View
    private Div divModuloFarmacologico;
    @View
    private Div divModuloOrdenamiento;
    @View
    private Hbox hbxLocalizacion;
    @View
    private Label lbActual_presenta;
    @View
    private Label lbCaracteristicas_Dolor;
    @View
    private Label lbEnfermedad_actual;
    @View
    private Label lbEvolucion_cuadro;
    @View
    private Label lbFecha_inicio;
    @View
    private Label lbIntensidad;
    @View
    private Label lbIrradiacion;
    @View
    private Label lbLocalizacion;
    @View
    private Label lbMan_for_inicio;
    @View
    private Label lbPrimera_presentacion;
    @View
    private Label lbRelacionado;
    @View
    private Label lbsintomas_asociados;
    @View
    private Label lbTratamiento_recibido;
    @View
    private Label lbVeces_repetido;
    @View
    private Listbox lbxIntensidad;
    @View
    private Listbox lbxPrimera_presentacion;
    @View
    private Radiogroup rdbseleccion;
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
    private Spinner spinnerVeces_repetido;
    @View
    private Textbox tbxActualmente_presenta;
    @View
    private Textbox tbxCaracteristicas_dolor;
    @View
    private Textbox tbxEvolucion;
    @View
    private Textbox tbxFecha_inicio;
    @View
    private Textbox tbxIrradiacion;
    @View
    private Textbox tbxlocalizacion;
    @View
    private Textbox tbxManera_form_inicio;
    @View
    private Textbox tbxParrafo_enfermedad_actual;
    @View
    private Textbox tbxRelacionado;
    @View
    private Textbox tbxSintomas_asociados;
    @View
    private Textbox tbxTratamiento_recibido;
    @View
    private Checkbox cbxAbdomen;
    @View
    private Checkbox cbxCabeza;
    @View
    private Checkbox cbxCuello;
    @View
    private Checkbox cbxToraz;
    @View
    private Toolbarbutton btImprimir;

    private Receta_rips_internoAction receta_ripsAction;
    private Orden_servicio_internoAction orden_servicioAction;
    private final String[] idsExcluyentes = {"tbxAccion", "tbxValue",
        "lbxParameter", "toolbarbuttonPaciente_admisionado1",
        "toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
        "gridOrdenes_servicio", "divModuloRemisiones_externas"};
    Integer meses;

    private Citas cita;

    @Override
    public void init() {
        try {
            listarCombos();
            FormularioUtil.inicializarRadiogroupsDefecto(this);

            // agudeza_visualAction = new Agudeza_visualAction();
            // agudeza_visualAction.inicializAgudeza(this,admision);
            // deshabilitarCamposCuadros();
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    // METODOS PARA DESABILITAR CHECK
    private void deshabilitarCamposCuadros() {
		// TODO: Deshabilitar los checks automaticos
        // CUADRO 2

        // estado 1:
        String[] ch04 = {"04", "1"};
        macroCuadroAIEPI2.getCheckbox("04", "1").setChecked(true);
        macroCuadroAIEPI2.evaluarCambio("04", "1");
        macroCuadroAIEPI2.deshabilitarChecks(ch04);

        // estado 2:
        String[] ch05 = {"09", "2"};
        macroCuadroAIEPI2.getCheckbox("09", "2").setChecked(true);
        macroCuadroAIEPI2.getCheckbox("09", "2");
        macroCuadroAIEPI2.evaluarCambio("09", "2");
        macroCuadroAIEPI2.deshabilitarChecks(ch05);

        // estado 3:
        String[] ch14 = {"14", "3"};
        // macroCuadroAIEPI2.getCheckbox("14", "3").setChecked(true);
        macroCuadroAIEPI2.getCheckbox("14", "3");
        macroCuadroAIEPI2.evaluarCambio("14", "3");
        macroCuadroAIEPI2.deshabilitarChecks(ch14);

        // estado 4:
        String[] ch19 = {"19", "4"};
        macroCuadroAIEPI2.getCheckbox("19", "4").setChecked(true);
        macroCuadroAIEPI2.getCheckbox("19", "4");
        macroCuadroAIEPI2.evaluarCambio("19", "4");
        macroCuadroAIEPI2.deshabilitarChecks(ch19);

        // estado 5:
        String[] ch24 = {"24", "5"};
        macroCuadroAIEPI2.getCheckbox("24", "5").setChecked(true);
        macroCuadroAIEPI2.getCheckbox("24", "5");
        macroCuadroAIEPI2.evaluarCambio("24", "5");
        macroCuadroAIEPI2.deshabilitarChecks(ch24);

        // estado 6:
        String[] ch29 = {"29", "6"};
        macroCuadroAIEPI2.getCheckbox("29", "6");
        macroCuadroAIEPI2.evaluarCambio("29", "6");
        macroCuadroAIEPI2.deshabilitarChecks(ch29);

        // CUADRO 3 :
        // estado 5
        String[] ch15 = {"15", "5"};
        macroCuadroAIEPI3.getCheckbox("15", "5").setChecked(true);
        macroCuadroAIEPI3.getCheckbox("15", "5");
        macroCuadroAIEPI3.evaluarCambio("15", "5");
        macroCuadroAIEPI3.deshabilitarChecks(ch15);

        // estado 6
        String[] ch17 = {"17", "6"};
        macroCuadroAIEPI3.getCheckbox("17", "6").setChecked(true);
        macroCuadroAIEPI3.getCheckbox("17", "6");
        macroCuadroAIEPI3.evaluarCambio("17", "6");
        macroCuadroAIEPI3.deshabilitarChecks(ch17);

        // CUADRO 4 :
        // estado 1:
        String[] ch1 = {"01", "1"};
        macroCuadroAIEPI4.getCheckbox("01", "1");
        macroCuadroAIEPI4.evaluarCambio("01", "1");
        macroCuadroAIEPI4.deshabilitarChecks(ch1);

        // estado 6:
        String[] ch43 = {"43", "7"};
        macroCuadroAIEPI4.getCheckbox("43", "7");
        macroCuadroAIEPI4.evaluarCambio("43", "7");
        macroCuadroAIEPI4.deshabilitarChecks(ch43);

        // CUADRO 8 :
        // estado 1
        String[] chk1 = {"01", "1"};
        macroCuadroAIEPI8.getCheckbox("01", "1");
        macroCuadroAIEPI8.evaluarCambio("01", "1");
        macroCuadroAIEPI8.deshabilitarChecks(chk1);

        String[] chk2 = {"02", "1"};
        macroCuadroAIEPI8.getCheckbox("02", "1");
        macroCuadroAIEPI8.evaluarCambio("02", "1");
        macroCuadroAIEPI8.deshabilitarChecks(chk2);

        // estado 2
        String[] chk3 = {"03", "2"};
        macroCuadroAIEPI8.getCheckbox("03", "2");
        macroCuadroAIEPI8.evaluarCambio("03", "2");
        macroCuadroAIEPI8.deshabilitarChecks(chk3);

        String[] chk4 = {"04", "2"};
        macroCuadroAIEPI8.getCheckbox("04", "2");
        macroCuadroAIEPI8.evaluarCambio("04", "2");
        macroCuadroAIEPI8.deshabilitarChecks(chk4);

        // estado 3
        String[] chk7 = {"07", "3"};
        macroCuadroAIEPI8.getCheckbox("07", "3");
        macroCuadroAIEPI8.evaluarCambio("07", "3");
        macroCuadroAIEPI8.deshabilitarChecks(chk7);

        String[] chk8 = {"08", "3"};
        macroCuadroAIEPI8.getCheckbox("08", "3");
        macroCuadroAIEPI8.evaluarCambio("08", "3");
        macroCuadroAIEPI8.deshabilitarChecks(chk8);

        // estado 4
        String[] chk9 = {"09", "4"};
        macroCuadroAIEPI8.getCheckbox("09", "4");
        macroCuadroAIEPI8.evaluarCambio("09", "4");
        macroCuadroAIEPI8.deshabilitarChecks(chk9);

        String[] chk10 = {"10", "4"};
        macroCuadroAIEPI8.getCheckbox("10", "4");
        macroCuadroAIEPI8.evaluarCambio("10", "4");
        macroCuadroAIEPI8.deshabilitarChecks(chk10);

        String[] chk11 = {"11", "4"};
        macroCuadroAIEPI8.getCheckbox("11", "4");
        macroCuadroAIEPI8.evaluarCambio("11", "4");
        macroCuadroAIEPI8.deshabilitarChecks(chk11);

        // estado 5
        String[] chk12 = {"12", "5"};
        macroCuadroAIEPI8.getCheckbox("12", "5");
        macroCuadroAIEPI8.evaluarCambio("12", "5");
        macroCuadroAIEPI8.deshabilitarChecks(chk12);

        String[] chk13 = {"13", "5"};
        macroCuadroAIEPI8.getCheckbox("13", "5");
        macroCuadroAIEPI8.evaluarCambio("13", "5");
        macroCuadroAIEPI8.deshabilitarChecks(chk13);

        String[] chk14 = {"14", "5"};
        macroCuadroAIEPI8.getCheckbox("14", "5");
        macroCuadroAIEPI8.evaluarCambio("14", "5");
        macroCuadroAIEPI8.deshabilitarChecks(chk14);

        // estado 6
        String[] chk16 = {"16", "6"};
        macroCuadroAIEPI8.getCheckbox("16", "6");
        macroCuadroAIEPI8.evaluarCambio("16", "6");
        macroCuadroAIEPI8.deshabilitarChecks(chk16);

        String[] ch16 = {"16", "6"};
        macroCuadroAIEPI8.getCheckbox("16", "6");
        macroCuadroAIEPI8.evaluarCambio("16", "6");
        macroCuadroAIEPI8.deshabilitarChecks(ch16);

        String[] chk17 = {"17", "6"};
        macroCuadroAIEPI8.getCheckbox("17", "6");
        macroCuadroAIEPI8.evaluarCambio("17", "6");
        macroCuadroAIEPI8.deshabilitarChecks(chk17);

        String[] chk18 = {"18", "6"};
        macroCuadroAIEPI8.getCheckbox("18", "6");
        macroCuadroAIEPI8.evaluarCambio("18", "6");
        macroCuadroAIEPI8.deshabilitarChecks(chk18);

        String[] chk19 = {"19", "6"};
        macroCuadroAIEPI8.getCheckbox("19", "6");
        macroCuadroAIEPI8.evaluarCambio("19", "6");
        macroCuadroAIEPI8.deshabilitarChecks(chk19);

        String[] chk20 = {"20", "6"};

        macroCuadroAIEPI8.getCheckbox("20", "6");
        macroCuadroAIEPI8.evaluarCambio("20", "6");
        macroCuadroAIEPI8.deshabilitarChecks(chk20);

    }

    public void autoOpcionesCuadros_1() {

        // CUADRO 2
        // estado 1:
        Checkbox chk1_4 = macroCuadroAIEPI2.getCheckbox("04", "1");
        CuadroAIEPIMacro.deshabilitarCheck(chk1_4, (meses < 3));
        macroCuadroAIEPI2.evaluarCambio("04", "1");

        // estado 2:
        Checkbox chk1_5 = macroCuadroAIEPI2.getCheckbox("09", "2");
        CuadroAIEPIMacro.deshabilitarCheck(chk1_5, (meses < 3));
        macroCuadroAIEPI2.evaluarCambio("09", "2");

        // estado 4 :
        Checkbox chk1_19 = macroCuadroAIEPI2.getCheckbox("19", "4");
        CuadroAIEPIMacro.deshabilitarCheck(chk1_19, (meses > 3));
        macroCuadroAIEPI2.evaluarCambio("19", "4");

        // esatdo 5 :
        Checkbox chk1_24 = macroCuadroAIEPI2.getCheckbox("24", "5");
        CuadroAIEPIMacro.deshabilitarCheck(chk1_24, (meses > 3));
        macroCuadroAIEPI2.evaluarCambio("24", "5");

        // CUADRO 3:
        // estado 5:
        Checkbox chk_15 = macroCuadroAIEPI3.getCheckbox("15", "5");
        CuadroAIEPIMacro.deshabilitarCheck(chk_15, (meses < 6));
        macroCuadroAIEPI3.evaluarCambio("15", "5");

        // estado 6:
        Checkbox chk_17 = macroCuadroAIEPI3.getCheckbox("17", "6");
        CuadroAIEPIMacro.deshabilitarCheck(chk_17, (meses > 6));
        macroCuadroAIEPI3.evaluarCambio("17", "6");

        // CUADRO 4:
        // estado 6:
        Checkbox chk_43 = macroCuadroAIEPI4.getCheckbox("43", "7");
        CuadroAIEPIMacro.deshabilitarCheck(chk_43, (meses < 60));
        macroCuadroAIEPI4.evaluarCambio("43", "7");
    }

    public void autoOpcionesCuadros() {
        // estado 1
        if (dbxImc_e_de.getValue() == null) {
        } else {

            Checkbox chk1 = macroCuadroAIEPI8.getCheckbox("01", "1");
            CuadroAIEPIMacro.deshabilitarCheck(chk1,
                    (dbxImc_e_de.getValue() >= 2));
            macroCuadroAIEPI8.evaluarCambio("01", "1");
        }
        if (dbxP_t_de.getValue() == null) {
        } else {
            Checkbox chk2 = macroCuadroAIEPI8.getCheckbox("02", "1");
            CuadroAIEPIMacro
                    .deshabilitarCheck(chk2, (dbxP_t_de.getValue() > 2));
            macroCuadroAIEPI8.evaluarCambio("02", "1");
        }
        if (dbxImc_e_de.getValue() == null) {
        } else {

            // estado 2 :
            Checkbox chk3 = macroCuadroAIEPI8.getCheckbox("03", "2");
            CuadroAIEPIMacro
                    .deshabilitarCheck(chk3, (dbxImc_e_de.getValue() >= 1)
                            && dbxImc_e_de.getValue() < 2);
            macroCuadroAIEPI8.evaluarCambio("03", "2");
        }
        if (dbxP_t_de.getValue() == null) {
        } else {
            Checkbox chk4 = macroCuadroAIEPI8.getCheckbox("04", "2");
            CuadroAIEPIMacro.deshabilitarCheck(chk4, (dbxP_t_de.getValue() > 1)
                    && dbxP_t_de.getValue() <= 2);
            macroCuadroAIEPI8.evaluarCambio("04", "2");
        }
        // //estado 3

        if (dbxP_t_de.getValue() == null) {
        } else {
            Checkbox chk7 = macroCuadroAIEPI8.getCheckbox("07", "3");
            CuadroAIEPIMacro.deshabilitarCheck(chk7,
                    (dbxP_t_de.getValue() < -3));
            macroCuadroAIEPI8.evaluarCambio("07", "3");
        }
        if (dbxP_e_de.getValue() == null) {
        } else {

            Checkbox chk8 = macroCuadroAIEPI8.getCheckbox("08", "3");
            CuadroAIEPIMacro.deshabilitarCheck(chk8,
                    (dbxP_e_de.getValue() < -3));
            macroCuadroAIEPI8.evaluarCambio("08", "3");
        }
        // //estado 4
        if (dbxP_t_de.getValue() == null) {
        } else {
            Checkbox chk9 = macroCuadroAIEPI8.getCheckbox("09", "4");
            CuadroAIEPIMacro.deshabilitarCheck(chk9,
                    (dbxP_t_de.getValue() >= -2) && dbxP_t_de.getValue() <= -3);
            macroCuadroAIEPI8.evaluarCambio("09", "4");
        }
        if (dbxP_e_de.getValue() == null) {

        } else {

            Checkbox chk10 = macroCuadroAIEPI8.getCheckbox("10", "4");
            CuadroAIEPIMacro.deshabilitarCheck(chk10,
                    (dbxP_e_de.getValue() >= -2) && dbxP_e_de.getValue() <= -3);
            macroCuadroAIEPI8.evaluarCambio("10", "4");
        }

        if (dbxT_e_de.getValue() == null) {
        } else {
            Checkbox chk11 = macroCuadroAIEPI8.getCheckbox("11", "4");
            CuadroAIEPIMacro.deshabilitarCheck(chk11,
                    (dbxT_e_de.getValue() < -2));
            macroCuadroAIEPI8.evaluarCambio("11", "4");
        }
        // //estado 5

        if (dbxP_t_de.getValue() == null) {
        } else {
            Checkbox chk12 = macroCuadroAIEPI8.getCheckbox("12", "5");
            CuadroAIEPIMacro.deshabilitarCheck(chk12,
                    (dbxP_t_de.getValue() >= -2) && dbxP_t_de.getValue() < -1);
            macroCuadroAIEPI8.evaluarCambio("12", "5");
        }

        if (dbxP_e_de.getValue() == null) {
        } else {
            Checkbox chk13 = macroCuadroAIEPI8.getCheckbox("13", "5");
            CuadroAIEPIMacro.deshabilitarCheck(chk13,
                    (dbxP_e_de.getValue() >= -2) && dbxP_e_de.getValue() < -1);
            macroCuadroAIEPI8.evaluarCambio("13", "5");
        }
        if (dbxT_e_de.getValue() == null) {
        } else {

            Checkbox chk14 = macroCuadroAIEPI8.getCheckbox("14", "5");
            CuadroAIEPIMacro.deshabilitarCheck(chk14,
                    (dbxT_e_de.getValue() >= -2) && dbxT_e_de.getValue() < -1);
            macroCuadroAIEPI8.evaluarCambio("14", "5");
        }

        // //estado 6
        if (dbxP_t_de.getValue() == null) {
        } else {
            Checkbox chk16 = macroCuadroAIEPI8.getCheckbox("16", "6");
            CuadroAIEPIMacro.deshabilitarCheck(chk16,
                    (dbxP_t_de.getValue() >= -1) && dbxP_t_de.getValue() <= 1);
            macroCuadroAIEPI8.evaluarCambio("16", "6");
        }

        if (dbxP_e_de.getValue() == null) {
        } else {
            Checkbox chk17 = macroCuadroAIEPI8.getCheckbox("17", "6");
            CuadroAIEPIMacro.deshabilitarCheck(chk17,
                    (dbxP_e_de.getValue() >= -1) && dbxP_e_de.getValue() <= 1);
            macroCuadroAIEPI8.evaluarCambio("17", "6");

        }
        if (dbxT_e_de.getValue() == null) {

        } else {
            Checkbox chk18 = macroCuadroAIEPI8.getCheckbox("18", "6");
            CuadroAIEPIMacro.deshabilitarCheck(chk18,
                    (dbxT_e_de.getValue() >= -1));
            macroCuadroAIEPI8.evaluarCambio("18", "6");
        }

        if (dbxImc_e_de.getValue() == null) {

        } else {
            Checkbox chk19 = macroCuadroAIEPI8.getCheckbox("19", "6");
            CuadroAIEPIMacro.deshabilitarCheck(chk19,
                    (dbxImc_e_de.getValue() < 1));
            macroCuadroAIEPI8.evaluarCambio("19", "6");
        }

    }

    public void activar_check_20() {

        Checkbox chk_20 = macroCuadroAIEPI8.getCheckbox("20", "6");
        Boolean check = macroCuadroAIEPI8.cantCheckeadosEstado("1") == 0
                && macroCuadroAIEPI8.cantCheckeadosEstado("2") == 0
                && macroCuadroAIEPI8.cantCheckeadosEstado("3") == 0
                && macroCuadroAIEPI8.cantCheckeadosEstado("4") == 0
                && macroCuadroAIEPI8.cantCheckeadosEstado("5") == 0;
        CuadroAIEPIMacro.deshabilitarCheck(chk_20, check);
        macroCuadroAIEPI8.evaluarCambio("20", "5");

    }

    public void activar_check_oximetria() {
        try {
            double oxiometria = dbxSignos_vitales_oximetria.getValue() != null ? dbxSignos_vitales_oximetria.getValue() : 0.0;
            Checkbox chk1_14 = macroCuadroAIEPI2.getCheckbox("14", "3");
            Checkbox chk1_29 = macroCuadroAIEPI2.getCheckbox("29", "6");
            CuadroAIEPIMacro.deshabilitarCheck(chk1_14,
                    (oxiometria == 84));
            CuadroAIEPIMacro.deshabilitarCheck(chk1_29,
                    (oxiometria > 84));
            macroCuadroAIEPI2.evaluarCambio("14", "3");
            macroCuadroAIEPI2.evaluarCambio("29", "6");
        } catch (WrongValueException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void activar_check_tem() {
        Checkbox chk_1 = macroCuadroAIEPI4.getCheckbox("01", "1");
        CuadroAIEPIMacro.deshabilitarCheck(chk_1, (meses < 3)
                && dbxSignos_vitales_taxilar.getValue() >= 38);
        macroCuadroAIEPI4.evaluarCambio("01", "1");

    }

    // metodos para habilitar filas no visibles
    public void mostrar_filas_ocultas() {
        mostrar_filas_ultimadosis();
        mostrar_fila_fiebre_amarilla();
        mostrar_fila_aiepi_cuadro_2();
        mostrar_fila_aiepi_cuadro_3();
        mostrar_fila_aiepi_cuadro_4();
        mostrar_fila_aiepi_cuadro_5();
        mostrar_fila_aiepi_cuadro_6();
    }

    public void mostrar_filas_ultimadosis() {
        if (cbx_influenza.isChecked() == true) {
            row_ultima_dosis.setVisible(true);
        } else {
            row_ultima_dosis.setVisible(false);

        }
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

        if (rdbTiene_fiebre.getSelectedItem().getValue().equals("S")) {
            row_cuadro_aiepi_4.setVisible(true);
        } else {
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
        Utilidades.listarElementoListbox(lbxParentesco_acompanante, true,
                getServiceLocator());
        parametrizarBandboxOcupacion(tbxOcupacion_padre,
                tbxInfoOcupacion_padre, btnLimpiarOcupacion_padre);
        parametrizarBandboxOcupacion(tbxOcupacion_madre,
                tbxInfoOcupacion_madre, btnLimpiarOcupacion_madre);
        Utilidades.listarElementoListbox(lbxIntensidad, true,
                getServiceLocator());

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
            Utilidades.seleccionarListitem(lbxParentesco_acompanante,
                    cita.getRelacion());
            onSeleccionarRelacionAcompaniante();
            tbxOtro_acompanante.setValue(cita.getOtro_acompaniante());
        }

        if (paciente.getTipo_identificacion().equalsIgnoreCase("AS")
                || paciente.getTipo_identificacion().equalsIgnoreCase("MS")) {
            rdbNino_registrado.setSelectedIndex(1);
        } else {
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
                                //log.info("<>>>>>>>>>>>>>>>>>>>" + hisc);
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
        seleccion(rdbseleccion);
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
        btImprimir.setVisible(false);
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
        FormularioUtil.validarCamposObligatorios(tbxNombres_acompanante,
                tbxApellidos_acompanante, tbxMotivo_consulta,
                tbxEnfermedad_actual, dbxSignos_vitales_peso,
                dbxSignos_vitales_talla, dbxSignos_vitales_pc,
                dbxSignos_vitales_fc, dbxSignos_vitales_fr,
                dbxSignos_vitales_taxilar, lbxParentesco_acompanante);

        String mensaje = "";
        boolean valida = true;

        if (valida) {
            valida = receta_ripsAction.validarFormExterno();
            if (!valida) {
                mensaje = "Error receta rips!";
            }
        }

        if (valida) {
            valida = orden_servicioAction.validarFormExterno();
            if (!valida) {
                mensaje = "Error orden de servicio!";
            }
        }

        if (valida) {
            valida = remisiones_externasAction.validarRemision();
            if (!valida) {
                mensaje = "Error remision externa!";
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

        Prestadores prestadores = new Prestadores();
        prestadores.setCodigo_empresa(codigo_empresa);
        prestadores.setCodigo_sucursal(codigo_sucursal);
        prestadores.setNro_identificacion(hisc_aiepi_mn_2_meses_5_anios.getCreacion_user());
        prestadores = getServiceLocator().getPrestadoresService().consultar(prestadores);

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

        fila.appendChild(new Label(prestadores != null ? (prestadores.getNombres() + " " + prestadores.getApellidos()) : hisc_aiepi_mn_2_meses_5_anios.getCreacion_user()));

        Datebox datebox = new Datebox(
                hisc_aiepi_mn_2_meses_5_anios
                .getFecha_inicial());
        datebox.setButtonVisible(false);
        datebox.setFormat("yyyy-MM-dd hh-mm-ss");
        datebox.setWidth("98%");
        datebox.setReadonly(true);
        datebox.setInplace(true);
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
                        if (parametros_empresa.getMostrar_historias_pag()) {
                            mostrarHistoriaPagina(hisc_aiepi_mn_2_meses_5_anios.getCodigo_historia(), IVias_ingreso.CONSULTA_EXTERNA, tabboxContendor, "Mostrar AIEPI 2M-5A");
                        } else {
                            mostrarDatos(hisc_aiepi_mn_2_meses_5_anios);
                        }

                    }
                });

        hlayout.appendChild(btn_mostrar);
//        btn_mostrar = new Toolbarbutton();
//        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
//            btn_mostrar.setVisible(false);
//        }
//        btn_mostrar.setImage("/images/borrar.gif");
//        btn_mostrar.setTooltiptext("Eliminar");
//        btn_mostrar.setStyle("cursor: pointer");
//        btn_mostrar.addEventListener(Events.ON_CLICK,
//                new EventListener<Event>() {
//                    @Override
//                    public void onEvent(Event arg0) throws Exception {
//                        Messagebox
//                        .show("Esta seguro que desea eliminar este registro? ",
//                                "Eliminar Registro",
//                                Messagebox.YES + Messagebox.NO,
//                                Messagebox.QUESTION,
//                                new org.zkoss.zk.ui.event.EventListener<Event>() {
//                                    public void onEvent(Event event)
//                                    throws Exception {
//                                        if ("onYes".equals(event
//                                                .getName())) {
//                                            // do the thing
//                                            eliminarDatos(hisc_aiepi_mn_2_meses_5_anios);
//                                            buscarDatos();
//                                        }
//                                    }
//                                });
//                    }
//                });
//        hlayout.appendChild(new Space());
//        hlayout.appendChild(btn_mostrar);
        fila.appendChild(hlayout);
        return fila;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            if (validarForm()) {
                FormularioUtil.setUpperCase(groupboxEditar);
                // Cargamos los componentes //
                Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_2_5 = getBean();

                Map<String, Object> datos = new HashMap<String, Object>();
                datos.put("accion", tbxAccion.getText());
                datos.put("historia_clinica", hisc_aiepi_2_5);
                datos.put("admision", admision);

                Remision_interna remision_interna = macroRemision_interna
                        .obtenerRemisionInterna();
                datos.put("remision_interna", remision_interna);

                Anexo9_entidad anexo9_entidad = remisiones_externasAction.obtenerAnexo9();

                datos.put("anexo9", anexo9_entidad);
                Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
                        .obtenerImpresionDiagnostica();
                datos.put("impresion_diagnostica", impresion_diagnostica);
                receta_ripsAction
                        .actualizarDiagnosticos(impresion_diagnostica);
                Map<String, Object> mapReceta = receta_ripsAction
                        .obtenerDatos();
                Map<String, Object> mapProcedimientos = orden_servicioAction
                        .obtenerDatos();

                datos.put("receta_medica", mapReceta);
                datos.put("orden_servicio", mapProcedimientos);
                datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());
                datos.put("cita_seleccionada", cita);

                // TODO: Agregar tantos resultado_cuadro como sean necesarios.
                List<Cuadros_aiepi_resultados> lista = new ArrayList<Cuadros_aiepi_resultados>();
                lista.addAll(macroCuadroAIEPI1.getLista_resultados());
                lista.addAll(macroCuadroAIEPI2.getLista_resultados());
                lista.addAll(macroCuadroAIEPI3.getLista_resultados());
                lista.addAll(macroCuadroAIEPI4.getLista_resultados());
                lista.addAll(macroCuadroAIEPI5.getLista_resultados());
                lista.addAll(macroCuadroAIEPI6.getLista_resultados());
                lista.addAll(macroCuadroAIEPI7.getLista_resultados());
                lista.addAll(macroCuadroAIEPI8.getLista_resultados());
                lista.addAll(macroCuadroAIEPI9.getLista_resultados());
                lista.addAll(macroCuadroAIEPI10.getLista_resultados());
                lista.addAll(macroCuadroAIEPI11.getLista_resultados());
                datos.put("resultado_cuadro", lista);

                hisc_aiepi_mn_2_meses_5_aniosService
                        .guardarDatos(datos);

                tbxAccion.setValue("modificar");
                mostrarDatosAutorizacion(datos);
                if (anexo9_entidad != null) {
                    remisiones_externasAction.setCodigo_remision(anexo9_entidad
                            .getCodigo());
                    remisiones_externasAction.getBotonImprimir().setDisabled(
                            false);
                }

                infoPacientes.setCodigo_historia(hisc_aiepi_2_5
                        .getCodigo_historia());

                Receta_rips receta_rips = (Receta_rips) mapReceta
                        .get("receta_rips");
                if (receta_rips != null) {
                    receta_ripsAction.mostrarDatos(receta_rips, false);
                }

                Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
                        .get("orden_servicio");
                if (orden_servicio != null) {
                    orden_servicioAction.mostrarDatos(orden_servicio);
                }

                orden_servicioAction.validarParaImpresion();
                receta_ripsAction.validarParaImpresion();

                actualizarAutorizaciones(admision,
                        impresion_diagnostica.getCausas_externas(),
                        impresion_diagnostica.getCie_principal(),
                        impresion_diagnostica.getCie_relacionado1(),
                        impresion_diagnostica.getCie_relacionado2(), this);

                actualizarRemision(admision, getInformacionClinica(), this);
                btImprimir.setVisible(true);
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
        Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_2_5 = new Hisc_aiepi_mn_2_meses_5_anios();
        hisc_aiepi_2_5.setCodigo_historia(infoPacientes.getCodigo_historia());
        hisc_aiepi_2_5.setIdentificacion(admision != null ? admision
                .getNro_identificacion() : null);
        hisc_aiepi_2_5.setFecha_inicial(new Timestamp(infoPacientes
                .getFecha_inicial().getTime()));
        hisc_aiepi_2_5.setNro_ingreso(admision.getNro_ingreso());
        hisc_aiepi_2_5.setCodigo_historia_anterior(codigo_historia_anterior);
        hisc_aiepi_2_5.setCodigo_empresa(empresa.getCodigo_empresa());
        hisc_aiepi_2_5.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        hisc_aiepi_2_5
                .setNombre_acompananate(tbxNombres_acompanante.getValue());
        hisc_aiepi_2_5.setApellido_acompanante(tbxApellidos_acompanante
                .getValue());
        hisc_aiepi_2_5.setParentesco_acompanante(lbxParentesco_acompanante
                .getSelectedItem().getValue().toString());

        hisc_aiepi_2_5.setId_acompanante(tbxId_acompanante.getValue());

        hisc_aiepi_2_5.setTelefono_acompanante(tbxTelefono_acompanante
                .getValue());
        hisc_aiepi_2_5.setNombre_padre(tbxNombres_padre.getValue());
        hisc_aiepi_2_5.setApellidos_padre(tbxApellidos_padre.getValue());
        hisc_aiepi_2_5.setId_padre(tbxId_padre.getValue());
        hisc_aiepi_2_5.setTelefono_padre(tbxTelefono_padre.getValue());
        hisc_aiepi_2_5.setOcupacion_padre(tbxOcupacion_padre.getValue());
        hisc_aiepi_2_5
                .setEdad_padre((ibxEdad_padre.getValue() != null ? ibxEdad_padre
                        .getValue() : 0));

        hisc_aiepi_2_5.setNombre_madre(tbxNombres_madre.getValue());
        hisc_aiepi_2_5.setApellidos_madre(tbxApellidos_madre.getValue());
        hisc_aiepi_2_5.setId_madre(tbxId_madre.getValue());
        hisc_aiepi_2_5.setTelefono_madre(tbxTelefono_madre.getValue());

        hisc_aiepi_2_5
                .setEdad_madre((ibxEdad_madre.getValue() != null ? ibxEdad_madre
                        .getValue() : 0));
        hisc_aiepi_2_5.setOcupacion_madre(tbxOcupacion_madre.getValue());
        hisc_aiepi_2_5.setMotivo_consulta(tbxMotivo_consulta.getValue());
        hisc_aiepi_2_5.setEnfermedad_actual(tbxEnfermedad_actual.getValue());
        hisc_aiepi_2_5.setComo_fue_el_embarazo(tbxComo_fue_el_embarazo
                .getValue());
        hisc_aiepi_2_5.setCuanto_duro_embarazo((ibxCuanto_duro_embarazo
                .getValue() != null ? ibxCuanto_duro_embarazo.getValue() : 0));
        hisc_aiepi_2_5.setComo_fue_el_parto(tbxComo_fue_el_parto.getValue());
        hisc_aiepi_2_5.setTalla_al_nacer(String.valueOf((dbxTalla_al_nacer
                .getValue() != null ? dbxTalla_al_nacer.getValue() : 0.00)));
        hisc_aiepi_2_5
                .setPresento_algun_problema_neonatal(tbxPresento_algun_problema_neonatal
                        .getValue());
        hisc_aiepi_2_5.setEnfermedades_previas(tbxEnfermedades_previas
                .getValue());
        hisc_aiepi_2_5.setHospitalizaciones(tbxHospitalizaciones.getValue());
        hisc_aiepi_2_5
                .setSignos_vitales_fc(""
                        + (dbxSignos_vitales_fc.getValue() != null ? dbxSignos_vitales_fc
                        .getValue() : 0.00));
        hisc_aiepi_2_5
                .setSignos_vitales_fr(""
                        + (dbxSignos_vitales_fr.getValue() != null ? dbxSignos_vitales_fr
                        .getValue() : 0.00));
        hisc_aiepi_2_5
                .setSignos_vitales_talla(""
                        + (dbxSignos_vitales_talla.getValue() != null ? dbxSignos_vitales_talla
                        .getValue() : 0.00));
        hisc_aiepi_2_5.setPeso_al_nacer(String.valueOf((dbxPeso_al_nacer
                .getValue() != null ? dbxPeso_al_nacer.getValue() : 0.00)));
        hisc_aiepi_2_5
                .setSignos_vitales_peso(""
                        + (dbxSignos_vitales_peso.getValue() != null ? dbxSignos_vitales_peso
                        .getValue() : 0.00));
        hisc_aiepi_2_5
                .setSignos_vitales_pc(""
                        + (dbxSignos_vitales_pc.getValue() != null ? dbxSignos_vitales_pc
                        .getValue() : 0.00));
        hisc_aiepi_2_5
                .setSignos_vitales_imc(""
                        + (dbxSignos_vitales_imc.getValue() != null ? dbxSignos_vitales_imc
                        .getValue() : 0.00));
        hisc_aiepi_2_5
                .setSignos_vitales_taxilar(""
                        + (dbxSignos_vitales_taxilar.getValue() != null ? dbxSignos_vitales_taxilar
                        .getValue() : 0.00));
        hisc_aiepi_2_5
                .setSignos_vitales_oximetria(""
                        + (dbxSignos_vitales_oximetria.getValue() != null ? dbxSignos_vitales_oximetria
                        .getValue() : 0.00));
        hisc_aiepi_2_5
                .setTiene_tos_o_dificultad_para_respirar(rdbTiene_tos_o_dificultad_para_respirar
                        .getSelectedItem().getValue().toString());
        hisc_aiepi_2_5.setTiene_diarrea(rdbTiene_diarrea.getSelectedItem()
                .getValue().toString());
        hisc_aiepi_2_5.setTiene_fiebre(rdbTiene_fiebre.getSelectedItem()
                .getValue().toString());
        hisc_aiepi_2_5.setTiene_problemas_de_oido(rdbTiene_problemas_de_oido
                .getSelectedItem().getValue().toString());
        hisc_aiepi_2_5
                .setTiene_un_problema_de_garganta(rdbTiene_un_problema_de_garganta
                        .getSelectedItem().getValue().toString());
        hisc_aiepi_2_5
                .setSignos_vitales_sintomaticos_respiratorio(rdbSignos_vitales_sintomaticos_respiratorio
                        .getSelectedItem().getValue().toString());
        hisc_aiepi_2_5
                .setSignos_vitales_sintomaticos_piel(rdbSignos_vitales_sintomaticos_piel
                        .getSelectedItem().getValue().toString());
        hisc_aiepi_2_5.setCreacion_date(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        hisc_aiepi_2_5.setCreacion_user(usuarios.getCodigo().toString());
        hisc_aiepi_2_5.setDelete_date(null);
        hisc_aiepi_2_5.setDelete_user(null);
        hisc_aiepi_2_5.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        hisc_aiepi_2_5.setUltimo_user(usuarios.getCodigo().toString());

        // TODO: guardar datos antecedentes vacunaion
        hisc_aiepi_2_5.setBcg_1(cbx_bcg__1.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setHepatitis_b_rn(cbx_hepatitis_b_rn.isChecked() ? "S"
                : "N");
        hisc_aiepi_2_5.setHepatitis_b1(cbx_hepatitis_b_1.isChecked() ? "S"
                : "N");
        hisc_aiepi_2_5.setHepatitis_b2(cbx_hepatitis_b_2.isChecked() ? "S"
                : "N");
        hisc_aiepi_2_5.setHepatitis_b3(cbx_hepatitis_b_3.isChecked() ? "S"
                : "N");
        hisc_aiepi_2_5.setVop_1(cbx_vop_1.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setVop_2(cbx_vop_2.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setVop_3(cbx_vop_3.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setVop_r1(cbx_vop_r1.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setVop_r2(cbx_vop_r2.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setRotavirus1(cbx_rotavirus_1.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setRotavirus2(cbx_rotavirus_2.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setInfluenza(cbx_influenza.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setOtras_vaunas(tbx_otras_vacunas.getValue());
        hisc_aiepi_2_5.setDpt1(cbx_dtp_1.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setDpt2(cbx_dtp_2.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setDpt3(cbx_dtp_3.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setDpt_r1(cbx_dtp_r1.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setDpt_r2(cbx_dtp_r2.isChecked() ? "S" : "N");
        hisc_aiepi_2_5
                .setHaemophilus_influenza_tipo_b1(cbx_haemophilus_influenza_tipo_b1
                        .isChecked() ? "S" : "N");
        hisc_aiepi_2_5
                .setHaemophilus_influenza_tipo_b2(cbx_haemophilus_influenza_tipo_b2
                        .isChecked() ? "S" : "N");
        hisc_aiepi_2_5
                .setHaemophilus_influenza_tipo_b3(cbx_haemophilus_influenza_tipo_b3
                        .isChecked() ? "S" : "N");
        hisc_aiepi_2_5
                .setHaemophilus_influenza_tipo_r1(cbx_haemophilus_influenza_tipo_r1
                        .isChecked() ? "S" : "N");
        hisc_aiepi_2_5
                .setHaemophilus_influenza_tipo_r2(cbx_haemophilus_influenza_tipo_r2
                        .isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setStrectococo_neumonia1(cbx_strectococo_neumonia_1
                .isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setStrectococo_neumonia2(cbx_strectococo_neumonia_2
                .isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setStrectococo_neumonia3(cbx_strectococo_neumonia_3
                .isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setSrp1(cbx_srp_1.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setSrp2(cbx_srp_2.isChecked() ? "S" : "N");
        hisc_aiepi_2_5.setFiebre_amarilla(cbx_fiebre_amarilla.isChecked() ? "S"
                : "N");
        hisc_aiepi_2_5.setEdad((ibx_edad.getValue() != null ? ibx_edad
                .getValue() : 0));

        if (!cbx_influenza.isChecked()) {
            hisc_aiepi_2_5.setUltima_dosis(null);
        } else {
            hisc_aiepi_2_5.setUltima_dosis(new Timestamp(dbx_ultima_dosis
                    .getValue().getTime()));
        }

        hisc_aiepi_2_5.setObservaciones_cuadro1(tbxObservaciones_cuadro1
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro2(tbxObservaciones_cuadro2
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro3(tbxObservaciones_cuadro3
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro4(tbxObservaciones_cuadro4
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro5(tbxObservaciones_cuadro5
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro6(tbxObservaciones_cuadro6
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro7(tbxObservaciones_cuadro7
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro8(tbxObservaciones_cuadro8
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro9(tbxObservaciones_cuadro9
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro10(tbxObservaciones_cuadro10
                .getValue());
        hisc_aiepi_2_5.setObservaciones_cuadro11(tbxObservaciones_cuadro11
                .getValue());

        hisc_aiepi_2_5.setAbdomen_enf(cbxAbdomen.isChecked());
        hisc_aiepi_2_5.setCabeza_enf(cbxCabeza.isChecked());
        hisc_aiepi_2_5.setCuello_enf(cbxCuello.isChecked());
        hisc_aiepi_2_5.setToraz_enf(cbxToraz.isChecked());
        hisc_aiepi_2_5.setIntensidad(lbxIntensidad.getSelectedItem().getValue()
                .toString());
        hisc_aiepi_2_5.setActualmente_presenta(tbxActualmente_presenta
                .getValue());
        hisc_aiepi_2_5.setCaracteristicas_dolor(tbxCaracteristicas_dolor
                .getValue());
        hisc_aiepi_2_5.setEvolucion(tbxEvolucion.getValue());
        hisc_aiepi_2_5.setFecha_inicio(tbxFecha_inicio.getValue());
        hisc_aiepi_2_5.setIrradiacion(tbxIrradiacion.getValue());
        hisc_aiepi_2_5.setLocalizacion(tbxlocalizacion.getValue());
        hisc_aiepi_2_5.setManera_form_inicio(tbxManera_form_inicio.getValue());
        hisc_aiepi_2_5
                .setParrafo_enfermedad_actual(tbxParrafo_enfermedad_actual
                        .getValue());
        hisc_aiepi_2_5.setRelacionado(tbxRelacionado.getValue());
        hisc_aiepi_2_5.setSintomas_asociados(tbxSintomas_asociados.getValue());
        hisc_aiepi_2_5.setTratamiento_recibido(tbxTratamiento_recibido
                .getValue());

        String primera_presentacion = lbxPrimera_presentacion.getSelectedItem()
                .getValue().toString();
        primera_presentacion = primera_presentacion + "|"
                + spinnerVeces_repetido.getText();

        hisc_aiepi_2_5.setPrimera_presentacion(primera_presentacion);
        hisc_aiepi_2_5.setTipo_historia(tipo_historia);

        return hisc_aiepi_2_5;
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios = (Hisc_aiepi_mn_2_meses_5_anios) obj;
        try {

            infoPacientes
                    .setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
                            .getCodigo_historia());
            infoPacientes
                    .setFecha_inicio(hisc_aiepi_mn_2_meses_5_anios
                            .getFecha_inicial());
            infoPacientes.setFecha_cierre(true,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getUltimo_update());
            initMostrar_datos(hisc_aiepi_mn_2_meses_5_anios);
            cargarInformacion_paciente();

            onMostrarModuloRemisiones();
            cargarRemisionInterna(hisc_aiepi_mn_2_meses_5_anios);

            tbxNombres_acompanante
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getNombre_acompananate());
            tbxApellidos_acompanante
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getApellido_acompanante());
            Utilidades.seleccionarListitem(lbxParentesco_acompanante,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getParentesco_acompanante());
            Ocupaciones ocupaciones = new Ocupaciones();
            String oc = hisc_aiepi_mn_2_meses_5_anios
                    .getOcupacion_padre();
            if (oc != null && !oc.isEmpty()) {
                ocupaciones
                        .setId(hisc_aiepi_mn_2_meses_5_anios
                                .getOcupacion_padre());
                ocupaciones = getServiceLocator().getOcupacionesService()
                        .consultar(ocupaciones);
                tbxOcupacion_padre
                        .setValue(hisc_aiepi_mn_2_meses_5_anios
                                .getOcupacion_padre());
                tbxInfoOcupacion_padre
                        .setValue((ocupaciones != null ? ocupaciones
                                .getNombre() : ""));
            }
            oc = hisc_aiepi_mn_2_meses_5_anios
                    .getOcupacion_madre();
            if (oc != null && !oc.isEmpty()) {
                ocupaciones
                        .setId(hisc_aiepi_mn_2_meses_5_anios
                                .getOcupacion_madre());
                ocupaciones = getServiceLocator().getOcupacionesService()
                        .consultar(ocupaciones);
                tbxOcupacion_padre
                        .setValue(hisc_aiepi_mn_2_meses_5_anios
                                .getOcupacion_madre());
                tbxInfoOcupacion_madre
                        .setValue((ocupaciones != null ? ocupaciones
                                .getNombre() : ""));
            }
            tbxTelefono_acompanante
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getTelefono_acompanante());
            tbxNombres_padre
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getNombre_padre());
            tbxApellidos_padre
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getApellidos_padre());
            ibxEdad_padre
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getEdad_padre());
            tbxNombres_madre
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getNombre_madre());
            tbxApellidos_madre
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getApellidos_madre());
            ibxEdad_madre
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getEdad_madre());
            tbxMotivo_consulta
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getMotivo_consulta());
            tbxEnfermedad_actual
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getEnfermedad_actual());
            tbxComo_fue_el_embarazo
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getComo_fue_el_embarazo());
            ibxCuanto_duro_embarazo
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getCuanto_duro_embarazo());
            tbxComo_fue_el_parto
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getComo_fue_el_parto());

            dbxPeso_al_nacer
                    .setValue(Double
                            .parseDouble(hisc_aiepi_mn_2_meses_5_anios
                                    .getPeso_al_nacer()));

            dbxTalla_al_nacer
                    .setValue(Double
                            .parseDouble(hisc_aiepi_mn_2_meses_5_anios
                                    .getTalla_al_nacer()));
            tbxPresento_algun_problema_neonatal
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getPresento_algun_problema_neonatal());
            tbxEnfermedades_previas
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getEnfermedades_previas());
            tbxHospitalizaciones
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getHospitalizaciones());
            dbxSignos_vitales_fc.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_fc()));
            dbxSignos_vitales_fr.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_fr()));
            dbxSignos_vitales_talla.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_talla()));
            dbxSignos_vitales_peso.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_peso()));
            dbxSignos_vitales_pc.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_pc()));
            dbxSignos_vitales_imc.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_imc()));
            dbxSignos_vitales_taxilar.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_taxilar()));
            dbxSignos_vitales_oximetria.setValue(Double
                    .valueOf(hisc_aiepi_mn_2_meses_5_anios
                            .getSignos_vitales_oximetria()));
            Utilidades.seleccionarRadio(
                    rdbTiene_tos_o_dificultad_para_respirar,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getTiene_tos_o_dificultad_para_respirar());
            Utilidades.seleccionarRadio(rdbTiene_diarrea,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getTiene_diarrea());
            Utilidades.seleccionarRadio(rdbTiene_fiebre,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getTiene_fiebre());
            Utilidades.seleccionarRadio(rdbTiene_problemas_de_oido,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getTiene_problemas_de_oido());
            Utilidades.seleccionarRadio(rdbTiene_un_problema_de_garganta,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getTiene_un_problema_de_garganta());
            Utilidades.seleccionarRadio(rdbSignos_vitales_sintomaticos_piel,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getSignos_vitales_sintomaticos_piel());
            Utilidades.seleccionarRadio(
                    rdbSignos_vitales_sintomaticos_respiratorio,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getSignos_vitales_sintomaticos_respiratorio());

            // TODO: mostrar datos antecedentes vacunaion
            cbx_bcg__1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getBcg_1().equals("S") ? true : false);
            cbx_hepatitis_b_rn
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHepatitis_b_rn().equals("S") ? true : false);
            cbx_hepatitis_b_1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHepatitis_b1().equals("S") ? true : false);
            cbx_hepatitis_b_2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHepatitis_b2().equals("S") ? true : false);
            cbx_hepatitis_b_3
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHepatitis_b3().equals("S") ? true : false);
            cbx_vop_1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getVop_1().equals("S") ? true : false);
            cbx_vop_2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getVop_2().equals("S") ? true : false);
            cbx_vop_3
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getVop_3().equals("S") ? true : false);
            cbx_vop_r1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getVop_r1().equals("S") ? true : false);
            cbx_vop_r2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getVop_r2().equals("S") ? true : false);
            cbx_rotavirus_1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getRotavirus1().equals("S") ? true : false);
            cbx_rotavirus_2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getRotavirus2().equals("S") ? true : false);
            cbx_influenza
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getInfluenza().equals("S") ? true : false);
            tbx_otras_vacunas
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getOtras_vaunas());
            cbx_dtp_1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getDpt1().equals("S") ? true : false);
            cbx_dtp_2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getDpt2().equals("S") ? true : false);
            cbx_dtp_3
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getDpt3().equals("S") ? true : false);
            cbx_dtp_r1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getDpt_r1().equals("S") ? true : false);
            cbx_dtp_r2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getDpt_r2().equals("S") ? true : false);
            cbx_haemophilus_influenza_tipo_b1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHaemophilus_influenza_tipo_b1().equals("S") ? true
                            : false);
            cbx_haemophilus_influenza_tipo_b2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHaemophilus_influenza_tipo_b2().equals("S") ? true
                            : false);
            cbx_haemophilus_influenza_tipo_b3
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHaemophilus_influenza_tipo_b3().equals("S") ? true
                            : false);
            cbx_haemophilus_influenza_tipo_r1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHaemophilus_influenza_tipo_r1().equals("S") ? true
                            : false);
            cbx_haemophilus_influenza_tipo_r2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getHaemophilus_influenza_tipo_r2().equals("S") ? true
                            : false);
            cbx_strectococo_neumonia_1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getStrectococo_neumonia1().equals("S") ? true
                            : false);
            cbx_strectococo_neumonia_2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getStrectococo_neumonia2().equals("S") ? true
                            : false);
            cbx_strectococo_neumonia_3
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getStrectococo_neumonia3().equals("S") ? true
                            : false);
            cbx_srp_1
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getSrp1().equals("S") ? true : false);
            cbx_srp_2
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getSrp2().equals("S") ? true : false);
            cbx_fiebre_amarilla
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getFiebre_amarilla().equals("S") ? true : false);
            ibx_edad.setValue(hisc_aiepi_mn_2_meses_5_anios
                    .getEdad());
            dbx_ultima_dosis
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getUltima_dosis());

            tbxObservaciones_cuadro1
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro1());
            tbxObservaciones_cuadro2
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro2());
            tbxObservaciones_cuadro3
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro3());
            tbxObservaciones_cuadro4
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro4());
            tbxObservaciones_cuadro5
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro5());
            tbxObservaciones_cuadro6
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro6());
            tbxObservaciones_cuadro7
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro7());
            tbxObservaciones_cuadro8
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro8());
            tbxObservaciones_cuadro9
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro9());
            tbxObservaciones_cuadro10
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro10());
            tbxObservaciones_cuadro11
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getObservaciones_cuadro11());

            cbxAbdomen
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getAbdomen_enf());
            cbxCabeza
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getCabeza_enf());
            cbxCuello
                    .setChecked(hisc_aiepi_mn_2_meses_5_anios
                            .getCuello_enf());
            cbxToraz.setChecked(hisc_aiepi_mn_2_meses_5_anios
                    .getToraz_enf());

            tbxActualmente_presenta
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getActualmente_presenta());
            tbxCaracteristicas_dolor
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getCaracteristicas_dolor());
            tbxEvolucion
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getEvolucion());
            tbxFecha_inicio
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getFecha_inicio());
            tbxIrradiacion
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getIrradiacion());
            tbxlocalizacion
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getLocalizacion());
            tbxManera_form_inicio
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getManera_form_inicio());
            tbxParrafo_enfermedad_actual
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getParrafo_enfermedad_actual());
            tbxRelacionado
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getRelacionado());
            tbxSintomas_asociados
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getSintomas_asociados());
            tbxTratamiento_recibido
                    .setValue(hisc_aiepi_mn_2_meses_5_anios
                            .getTratamiento_recibido());

            Utilidades.seleccionarListitem(lbxIntensidad,
                    hisc_aiepi_mn_2_meses_5_anios
                    .getIntensidad());
            StringTokenizer stringTokenizer = new StringTokenizer(
                    hisc_aiepi_mn_2_meses_5_anios
                    .getPrimera_presentacion(),
                    "|");

            Utilidades.seleccionarListitem(
                    lbxPrimera_presentacion,
                    stringTokenizer.hasMoreTokens() ? stringTokenizer
                    .nextToken() : "");
            spinnerVeces_repetido
                    .setText(stringTokenizer.hasMoreTokens() ? stringTokenizer
                            .nextToken() : "1");

            seleccion((Radiogroup) getFellow("rdbseleccion"));

            // TODO: mapas
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("codigo_empresa",
                    hisc_aiepi_mn_2_meses_5_anios
                    .getCodigo_empresa());
            parametros.put("codigo_sucursal",
                    hisc_aiepi_mn_2_meses_5_anios
                    .getCodigo_sucursal());
            parametros.put("identificacion",
                    hisc_aiepi_mn_2_meses_5_anios
                    .getIdentificacion());
            parametros.put("codigo_historia",
                    hisc_aiepi_mn_2_meses_5_anios
                    .getCodigo_historia());
            parametros
                    .put("via_ingreso", IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);

            parametros.put("cuadro", "1");
            List<Cuadros_aiepi_resultados> lista = getServiceLocator()
                    .getServicio(Cuadros_aiepi_resultadosService.class).listar(
                            parametros);
            macroCuadroAIEPI1.cargarDatos(lista);
            //log.info(">>>>>>>>>>>>" + parametros);
            //log.info(">>>>>>>>>>>>" + lista.size());

            parametros.put("cuadro", "2");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI2.cargarDatos(lista);
            //log.info(">>>>>>>>>>>>" + parametros);
            //log.info(">>>>>>>>>>>>" + lista.size());

            parametros.put("cuadro", "3");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI3.cargarDatos(lista);
            //log.info(">>>>>>>>>>>>" + parametros);
            //log.info(">>>>>>>>>>>>" + lista.size());

            parametros.put("cuadro", "4");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI4.cargarDatos(lista);

            parametros.put("cuadro", "5");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI5.cargarDatos(lista);

            parametros.put("cuadro", "6");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI6.cargarDatos(lista);

            parametros.put("cuadro", "7");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI7.cargarDatos(lista);

            parametros.put("cuadro", "8");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI8.cargarDatos(lista);

            parametros.put("cuadro", "9");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI9.cargarDatos(lista);

            parametros.put("cuadro", "10");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI10.cargarDatos(lista);

            parametros.put("cuadro", "11");
            lista.clear();
            lista = getServiceLocator().getServicio(
                    Cuadros_aiepi_resultadosService.class).listar(parametros);
            macroCuadroAIEPI11.cargarDatos(lista);

            mostrar_filas_ocultas();

            cargarImpresionDiagnostica(hisc_aiepi_mn_2_meses_5_anios);

            onMostrarModuloFarmacologico();
            receta_ripsAction.obtenerBotonAgregar().setVisible(false);
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(false);

            cargarAnexo9_remision(hisc_aiepi_mn_2_meses_5_anios);
            infoPacientes
                    .setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
                            .getCodigo_historia());
            // accionForm(true, tbxAccion.getText());
            inicializarVista(tipo_historia);
            groupboxConsulta.setVisible(false);
            groupboxEditar.setVisible(true);
            btImprimir.setVisible(true);

            if (!tbxAccion.getValue().equals(
                    OpcionesFormulario.MOSTRAR.toString())) {
                infoPacientes
                        .setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
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
                    .mensajeError(
                            e,
                            "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
                            this);
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
        if (receta_ripsAction != null) {
            receta_ripsAction.detach();
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
        receta_ripsAction = (Receta_rips_internoAction) Executions
                .createComponents("/pages/receta_rips_interno.zul", null,
                        parametros);
        receta_ripsAction.inicializar(this);
        //log.info(">>>>>>>>>>>>" + parametros);
        divModuloFarmacologico.appendChild(receta_ripsAction);
    }

    public void onMostrarModuloOrdenamiento() throws Exception {
        if (orden_servicioAction != null) {
            orden_servicioAction.detach();
        }
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

            macroImpresion_diagnostica.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
            macroCuadroAIEPI1.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "1");
            macroCuadroAIEPI2.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "2");
            macroCuadroAIEPI3.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "3");
            macroCuadroAIEPI4.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "4");
            macroCuadroAIEPI5.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "5");
            macroCuadroAIEPI6.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "6");
            macroCuadroAIEPI7.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "7");
            macroCuadroAIEPI8.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "8");
            macroCuadroAIEPI9.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "9");
            macroCuadroAIEPI10.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "10");
            macroCuadroAIEPI11.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "11");
            macroRemision_interna.inicializarMacro(this, admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);

            deshabilitarCamposCuadros();
            activar_check_20();
            autoOpcionesCuadros_1();

            toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
                    .getNombreCompleto());
            toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
                    .getNombreCompleto());

            if (admision.getAtendida()) {
                opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
                Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios = new Hisc_aiepi_mn_2_meses_5_anios();
                hisc_aiepi_mn_2_meses_5_anios
                        .setCodigo_empresa(empresa.getCodigo_empresa());
                hisc_aiepi_mn_2_meses_5_anios
                        .setCodigo_sucursal(sucursal.getCodigo_sucursal());
                hisc_aiepi_mn_2_meses_5_anios
                        .setIdentificacion(admision.getNro_identificacion());
                hisc_aiepi_mn_2_meses_5_anios
                        .setNro_ingreso(admision.getNro_ingreso());

                hisc_aiepi_mn_2_meses_5_anios = getServiceLocator()
                        .getServicio(
                                Hisc_aiepi_mn_2_meses_5_aniosService.class)
                        .consultar(
                                hisc_aiepi_mn_2_meses_5_anios);
                if (hisc_aiepi_mn_2_meses_5_anios != null) {
                    accionForm(OpcionesFormulario.MOSTRAR,
                            hisc_aiepi_mn_2_meses_5_anios);
                    infoPacientes
                            .setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
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
        Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_2_5 = (Hisc_aiepi_mn_2_meses_5_anios) historia_anterior;
        codigo_historia_anterior = hisc_aiepi_2_5.getCodigo_historia();
    }

    @Override
    public void initMostrar_datos(Object historia_clinica) {
        Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_mesesA_5_anos = (Hisc_aiepi_mn_2_meses_5_anios) historia_clinica;
        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
            FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
                    new String[]{"northEditar"});
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
        codigo_historia_anterior = hisc_aiepi_mn_2_mesesA_5_anos
                .getCodigo_historia_anterior();
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
            meses = Integer.valueOf(Util.getEdad(
                    new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha),
                    "2", false));
            cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);

            if (cita == null) {
                cita = new Citas();
            }
            opciones_via_ingreso = (Opciones_via_ingreso) map
                    .get(IVias_ingreso.OPCION_VIA_INGRESO);
            macroRemision_interna.deshabilitarCheck(admision,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
        }
        if (map.containsKey(IVias_ingreso.PADRE_TABS)) {
            tabboxContendor = (ContenedorPaginasMacro) map.get(IVias_ingreso.PADRE_TABS);
        }
    }

    public void validarBotonesDE(Integer opc) {
        Integer inicio = 2;
        Integer fin = 60;
        if (meses > inicio && meses <= fin) {
            switch (opc) {
                case 0:
                    validarTallaEdad();
                    validarPesoTalla();
                    break;
                case 1:
                    validarPerimetroCefalicoEdad(meses);
                    break;
                case 2:
                    validarPesoTalla();
                    break;
                default:
                    validarTallaEdad();
                    validarPesoTalla();
                    validarPerimetroCefalicoEdad(meses);
                    break;
            }
        }
    }

    public void validarTallaEdad() {
        double talla;
        double minTalla = 39;
        double maxTalla = 200;
        if (dbxSignos_vitales_talla.getValue() != null) {
            talla = dbxSignos_vitales_talla.getValue();
            if ((talla <= maxTalla && talla > minTalla)) {
                btnCalcularTallaEdad.setDisabled(false);
            } else {
                MensajesUtil.notificarAlerta("Talla: (" + (minTalla + 1) + "-"
                        + maxTalla + ") cm", dbxSignos_vitales_talla);
                btnCalcularTallaEdad.setDisabled(true);
            }
        } else {
            MensajesUtil.notificarAlerta("Talla: (" + (minTalla + 1) + "-"
                    + maxTalla + ") cm", dbxSignos_vitales_talla);
            btnCalcularTallaEdad.setDisabled(true);
        }
    }

    public void validarPesoTalla() {
        double peso = 0;
        double talla = 0;
        double minPeso = 0;
        double maxPeso = 20;
        double minTalla = 39;
        double maxTalla = 200;
        if (dbxSignos_vitales_peso.getValue() != null
                && dbxSignos_vitales_talla.getValue() != null) {
            peso = dbxSignos_vitales_peso.getValue();
            talla = dbxSignos_vitales_talla.getValue();

            if ((peso <= maxPeso && peso > minPeso)
                    && (talla <= maxTalla && talla > minTalla)) {
                btnCalcularPesoTalla.setDisabled(false);
                btnCalcularPesoEdad.setDisabled(false);
                btnCalcularImcEdad.setDisabled(false);
            } else {
                if (!(peso <= maxPeso && peso > minPeso)
                        && !(talla <= maxTalla && talla > minTalla)) {
                    MensajesUtil.notificarAlerta("Peso: (" + (minPeso + 1)
                            + "-" + maxPeso + ") g\nTalla: (" + (minTalla + 1)
                            + "-" + maxTalla + ") cm", dbxSignos_vitales_peso);
                }
                btnCalcularPesoTalla.setDisabled(true);
                btnCalcularPesoEdad.setDisabled(true);
                btnCalcularImcEdad.setDisabled(true);
            }
        } else {
            if (!(peso <= maxPeso && peso > minPeso)
                    && !(talla <= maxTalla && talla > minTalla)) {
                MensajesUtil.notificarAlerta("Peso: (" + (minPeso + 1) + "-"
                        + maxPeso + ") g\nTalla: (" + (minTalla + 1) + "-"
                        + maxTalla + ") cm", dbxSignos_vitales_peso);
            }
            btnCalcularPesoTalla.setDisabled(true);
            btnCalcularPesoEdad.setDisabled(true);
            btnCalcularImcEdad.setDisabled(true);
        }
    }

    public void validarPerimetroCefalicoEdad(Integer meses) {
        double pc;
        double minPc = 29;
        double maxPc = 60;
        if (meses <= 23) {
            if (dbxSignos_vitales_pc.getValue() != null) {
                pc = dbxSignos_vitales_pc.getValue();
                if ((pc <= maxPc && pc > minPc)) {
                    btnCalcularPerimetroCefalicoEdad.setDisabled(false);
                } else {
                    MensajesUtil.notificarAlerta("P.C: (" + (minPc + 1) + "-"
                            + maxPc + ") cm", dbxSignos_vitales_pc);
                    btnCalcularPerimetroCefalicoEdad.setDisabled(true);
                }
            } else {
                MensajesUtil.notificarAlerta("P.C: (" + (minPc + 1) + "-"
                        + maxPc + ") cm", dbxSignos_vitales_pc);
                btnCalcularPerimetroCefalicoEdad.setDisabled(true);
            }
        }
    }

    public void calcularPesoEdad() {
        double peso;
        double talla;
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

        dbxP_e_de.setValue(calcularPesoEdad(peso, talla, fecha).getValor());
        autoOpcionesCuadros();
        alertasPeso(dbxP_e_de);
    }

    public void calcularTallaEdad() {
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

        dbxT_e_de.setValue(calcularTallaEdad(talla, fecha).getValor());
        autoOpcionesCuadros();
        alertasPeso(dbxT_e_de);
    }

    public void calcularPesoTalla() {

        double talla = 0;
        double peso = 0;
        double imc = 0;

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

        if (talla != 0 && peso != 0) {
            imc = dbxSignos_vitales_imc.getValue();
            dbxImc_e_de.setValue(calcularImcEdad(imc, fecha).getValor());
        }

        dbxP_t_de.setValue(calcularPesoTalla(peso, talla).getValor());
        autoOpcionesCuadros();
        alertasPeso(dbxP_t_de);
    }

    public void calcularPerimetroCefalicoEdad() {
        double perimetro_cefalico;
        if (meses <= 23) {
            if (dbxSignos_vitales_pc.getValue() == null) {
                dbxSignos_vitales_pc.setStyle("background-color:#F6BBBE");
                Messagebox
                        .show("Debe digitar el perimetro cefálico del paciente para realizar este cálculo!!",
                                "Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            } else {
                dbxSignos_vitales_pc.setStyle("background-color:white");
                perimetro_cefalico = dbxSignos_vitales_pc.getValue();
            }
            dbxPC_e_de.setValue(calcularPerimetroCefalicoEdad(
                    perimetro_cefalico, fecha).getValor());
            // autoOpcionesCuadros();
        }
    }

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
        if (meses <= 23) {
            return GraficasCalculadorUtils.calcularPesoEdad0$2Anios(sexo, peso,
                    talla, fecha);
        } else {
            return GraficasCalculadorUtils.calcularPesoEdad2$5Anios(sexo, peso,
                    talla, fecha);
        }
    }

    private RespuestaInt calcularPerimetroCefalicoEdad(
            double perimetro_cefalico, Timestamp fecha) {
        // TODO:Solo a menor de 2 años
        return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
                sexo, perimetro_cefalico, fecha);
    }

    private RespuestaInt calcularPesoTalla(double peso, double talla) {
        if (meses <= 23) {
            return GraficasCalculadorUtils.calcularPesoTalla0$2Anios(sexo,
                    peso, talla);
        } else {
            return GraficasCalculadorUtils.calcularPesoTalla2$5Anios(sexo,
                    peso, talla);
        }
    }

    private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
        if (meses <= 23) {
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
        activar_check_tem();
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
        if (admision != null
                && opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
            /*
             CuadroAIEPIMacro.mostrarVentana(new Image(
             "/images/aiepi/lavado_manos_texto.png"), this.getPage(),
             "Lávese las manos", 0, 0, 0);
             */
        }
    }

    @Override
    public void cambioCheck(String cuadro, String estado, String descripcion)
            throws Exception {
        cambioCuadro(cuadro);
    }

    // Validaciones por cambio en los check de los cuadros
    public void cambioCuadro(String cuadro) {
        Integer prox = 365;
        Integer dia = 0;
        if (cuadro.equalsIgnoreCase("2")) {
            dia = 2;
            if (macroCuadroAIEPI2.cantCheckeadosEstado("5") > 0 && prox > dia) {
                prox = dia;
            }
            dia = 2;
            if (macroCuadroAIEPI2.cantCheckeadosEstado("8") > 0 && prox > dia) {
                prox = dia;
            }
            dia = 5;
            if (macroCuadroAIEPI2.cantCheckeadosEstado("9") > 0 && prox > dia) {
                prox = dia;
            }
        }
        if (cuadro.equalsIgnoreCase("3")) {
            dia = 2;
            if (macroCuadroAIEPI3.cantCheckeadosEstado("2") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI3.cantCheckeadosEstado("3") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI3.cantCheckeadosEstado("4") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI3.cantCheckeadosEstado("6") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI3.cantCheckeadosEstado("7") > 0 && prox > dia) {
                prox = dia;
            }
        }

        if (cuadro.equalsIgnoreCase("4")) {
            dia = 2;
            if (macroCuadroAIEPI4.cantCheckeadosEstado("2") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI4.cantCheckeadosEstado("3") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI4.cantCheckeadosEstado("8") > 0 && prox > dia) {
                prox = dia;
            }
        }

        if (cuadro.equalsIgnoreCase("5")) {
            dia = 14;
            if (macroCuadroAIEPI5.cantCheckeadosEstado("2") > 0 && prox > dia) {
                prox = dia;
            }
            dia = 2;
            if (macroCuadroAIEPI5.cantCheckeadosEstado("3") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI5.cantCheckeadosEstado("4") > 0 && prox > dia) {
                prox = dia;
            }
        }

        if (cuadro.equalsIgnoreCase("7")) {
            dia = 2;
            if (macroCuadroAIEPI7.cantCheckeadosEstado("4") > 0 && prox > dia) {
                prox = dia;
            }
            dia = 30;
            if (macroCuadroAIEPI7.cantCheckeadosEstado("6") > 0 && prox > dia) {
                prox = dia;
            }
        }

        if (cuadro.equalsIgnoreCase("8")) {
            dia = 14;
            if (macroCuadroAIEPI8.cantCheckeadosEstado("1") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI8.cantCheckeadosEstado("4") > 0 && prox > dia) {
                prox = dia;
            }
            dia = 30;
            if (macroCuadroAIEPI8.cantCheckeadosEstado("2") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI8.cantCheckeadosEstado("5") > 0 && prox > dia) {
                prox = dia;
            }
            activar_check_20();
        }

        if (cuadro.equalsIgnoreCase("9")) {
            dia = 14;
            if (macroCuadroAIEPI9.cantCheckeadosEstado("2") > 0 && prox > dia) {
                prox = dia;
            }
        }

        if (cuadro.equalsIgnoreCase("10")) {
            dia = 14;
            if (macroCuadroAIEPI10.cantCheckeadosEstado("3") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI10.cantCheckeadosEstado("4") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI10.cantCheckeadosEstado("5") > 0 && prox > dia) {
                prox = dia;
            }
        }

        if (cuadro.equalsIgnoreCase("11")) {
            dia = 14;
            if (macroCuadroAIEPI11.cantCheckeadosEstado("1") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI11.cantCheckeadosEstado("2") > 0 && prox > dia) {
                prox = dia;
            }
            if (macroCuadroAIEPI11.cantCheckeadosEstado("3") > 0 && prox > dia) {
                prox = dia;
            }
        }

        if (prox == 365) {
            prox = 0;
        }
        calcularProximaConsultaControl(prox);
    }

    public void calcularProximaConsultaControl(Integer dias) {
        Calendar prox = Calendar.getInstance();
        prox.setTime(new Date());
        prox.set(Calendar.DAY_OF_YEAR, prox.get(Calendar.DAY_OF_YEAR) + dias);
        dtbxVolver_consulta_control.setValue(prox.getTime());
    }

    private void alertasPeso(Doublebox doublebox) {

        String mensaje = "";
        String tipoMensaje = "";
        Integer valor;

        if (doublebox != null) {
            valor = doublebox.getValue().intValue();

            if (doublebox == dbxP_t_de) {
                if (valor > 2) {
                    mensaje = "OBESIDAD";
                    mensaje += "";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                } else if (valor > 1 && valor <= 2) {
                    mensaje = "SOBREPESO";
                    mensaje += "";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
                } else if (valor > -1 && valor <= 1) {
                    mensaje = "NORMAL: ";
                    mensaje += "Peso adecuado para la talla";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_INFO;
                } else if (valor > -2 && valor <= -1) {
                    mensaje = "A RIESGO: ";
                    mensaje += "Con bajo peso para la talla";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
                } else if (valor > -3 && valor <= -2) {
                    mensaje = "DESNUTRIcion: ";
                    mensaje += "Peso bajo para la talla o desnutricion aguda";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                } else if (valor < -3) {
                    mensaje = "DESNUTRIcion SEVERA: ";
                    mensaje += "Desnutricion aguda severa";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                }
            } else if (doublebox == dbxP_e_de) {
                if (valor > 2) {
                    mensaje = "OBESIDAD";
                    mensaje += "";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                } else if (valor > 1 && valor <= 2) {
                    mensaje = "SOBREPESO";
                    mensaje += "";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
                } else if (valor > -1 && valor <= 1) {
                    mensaje = "NORMAL: ";
                    mensaje += "Peso adecuado para la edad";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_INFO;
                } else if (valor > -2 && valor <= -1) {
                    mensaje = "A RIESGO: ";
                    mensaje += "Con bajo peso para su edad";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
                } else if (valor > -3 && valor <= -2) {
                    mensaje = "DESNUTRIcion: ";
                    mensaje += "Peso bajo para la edad o desnutricion global";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                } else if (valor < -3) {
                    mensaje = "DESNUTRIcion SEVERA: ";
                    mensaje += "Desnutricion global severa";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                }
            } else if (doublebox == dbxT_e_de) {
                if (valor > 2) {
                    mensaje = "OBESIDAD";
                    mensaje += "";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                } else if (valor > 1 && valor <= 2) {
                    mensaje = "SOBREPESO";
                    mensaje += "";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
                } else if (valor > -1 && valor <= 1) {
                    mensaje = "NORMAL: ";
                    mensaje += "Talla adecuada para edad";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_INFO;
                } else if (valor > -2 && valor <= -1) {
                    mensaje = "A RIESGO: ";
                    mensaje += "Con baja talla para la edad";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_WARNING;
                } else if (valor > -3 && valor <= -2) {
                    mensaje = "DESNUTRIcion: ";
                    mensaje += "Retraso del crecimiento o desnutricion crónica";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                } else if (valor < -3) {
                    mensaje = "DESNUTRIcion SEVERA: ";
                    mensaje += "Desnutricion crónica severa";
                    tipoMensaje = Clients.NOTIFICATION_TYPE_ERROR;
                }
            }
            if (!mensaje.isEmpty() && !tipoMensaje.isEmpty()) {
                Clients.showNotification(mensaje, tipoMensaje, doublebox,
                        "after_center", 5000, true);
            }
        }
    }

    private void cargarAnexo9_remision(
            Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios) {
        Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
        anexo9_entidad.setCodigo_empresa(codigo_empresa);
        anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
        anexo9_entidad
                .setNro_historia(hisc_aiepi_mn_2_meses_5_anios
                        .getCodigo_historia());
        anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
        anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
                .consultar(anexo9_entidad);
        //log.info("cargando anexo9_entidad === >" + anexo9_entidad);
        remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
    }

    private void cargarRemisionInterna(
            Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios)
            throws Exception {
        Remision_interna remision_interna = new Remision_interna();
        remision_interna
                .setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
                        .getCodigo_historia());
        remision_interna
                .setCodigo_empresa(hisc_aiepi_mn_2_meses_5_anios
                        .getCodigo_empresa());
        remision_interna
                .setCodigo_sucursal(hisc_aiepi_mn_2_meses_5_anios
                        .getCodigo_sucursal());

        remision_interna = getServiceLocator().getServicio(
                Remision_internaService.class).consultar(remision_interna);

        macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
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

    @Override
    public String getInformacionClinica() {
        StringBuffer informacion_clinica = new StringBuffer();
        /* */
        informacion_clinica.append("- SIGNOS VITALES\n");
        informacion_clinica.append("\tFrecuencia cardiaca: ")
                .append(dbxSignos_vitales_fc.getValue()).append("\n");
        informacion_clinica.append("\tFrecuencia respiratoria: ")
                .append(dbxSignos_vitales_fr.getValue()).append("\n");
        informacion_clinica.append("\tTemperatura: ")
                .append(dbxSignos_vitales_taxilar.getValue()).append("\n");
        informacion_clinica.append("\tPeso(kg): ")
                .append(dbxSignos_vitales_peso.getValue()).append("\n");
        informacion_clinica.append("\tTalla: ")
                .append(dbxSignos_vitales_talla.getValue()).append("\n");
        informacion_clinica.append("\tIMC: ")
                .append(dbxSignos_vitales_imc.getValue()).append("\n")
                .append("\n");

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
        if (lbxParentesco_acompanante.getSelectedItem().getValue().toString()
                .equals("1")) {
            // Madre

            tbxId_madre.setValue(tbxId_acompanante.getValue());
            tbxNombres_madre.setValue(tbxNombres_acompanante.getValue());
            tbxApellidos_madre.setValue(tbxApellidos_acompanante.getValue());
            tbxTelefono_madre.setValue(tbxTelefono_acompanante.getValue());

            if (tbxId_madre.getValue().equals(tbxId_padre.getValue())) {
                tbxId_padre.setValue("");
            }
            if (tbxNombres_madre.getValue().equals(tbxNombres_padre.getValue())) {
                tbxNombres_padre.setValue("");
            }
            if (tbxApellidos_madre.getValue().equals(
                    tbxApellidos_padre.getValue())) {
                tbxApellidos_padre.setValue("");
            }
            if (tbxTelefono_madre.getValue().equals(
                    tbxTelefono_padre.getValue())) {
                tbxTelefono_padre.setValue("");
            }
        } else if (lbxParentesco_acompanante.getSelectedItem().getValue()
                .toString().equals("2")) {
            // Padre

            tbxId_padre.setValue(tbxId_acompanante.getValue());
            tbxNombres_padre.setValue(tbxNombres_acompanante.getValue());
            tbxApellidos_padre.setValue(tbxApellidos_acompanante.getValue());
            tbxTelefono_padre.setValue(tbxTelefono_acompanante.getValue());

            if (tbxId_padre.getValue().equals(tbxId_madre.getValue())) {
                tbxId_madre.setValue("");
            }
            if (tbxNombres_padre.getValue().equals(tbxNombres_madre.getValue())) {
                tbxNombres_madre.setValue("");
            }
            if (tbxApellidos_padre.getValue().equals(
                    tbxApellidos_madre.getValue())) {
                tbxApellidos_madre.setValue("");
            }
            if (tbxTelefono_padre.getValue().equals(
                    tbxTelefono_madre.getValue())) {
                tbxTelefono_madre.setValue("");
            }
        }

        if (lbxParentesco_acompanante.getSelectedItem().getValue().toString()
                .equals("8")) {
            tbxOtro_acompanante.setVisible(true);
        } else {
            tbxOtro_acompanante.setVisible(false);
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
                tbxEnfermedad_actual.invalidate();
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

    public void generarParrafoEnfermedadActual() {
        StringBuffer strBuffer = new StringBuffer();

        Integer edad_anios = infoPacientes.getEdadAnios();

        if (edad_anios >= 18) {
            strBuffer
                    .append("Paciente quien consulta por cuadro clinico presentado ");
        } else {
            strBuffer.append("Paciente acompañado por su "
                    + lbxParentesco_acompanante.getSelectedItem().getLabel()
                    + " quien consulta por cuadro clínico presentado ");
        }
        if (lbxPrimera_presentacion.getSelectedIndex() == 0) {
            strBuffer.append(" por primera vez");
        } else {
            int veces = spinnerVeces_repetido.getValue();
            if (veces == 1) {
                strBuffer.append(" en una ocasion ");
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

            dbxSignos_vitales_fc.setValue(enfermera_signos
                    .getFrecuencia_cardiaca());
            dbxSignos_vitales_fr.setValue(enfermera_signos
                    .getFrecuencia_respiratoria());
            dbxSignos_vitales_peso.setValue(enfermera_signos.getPeso());
            dbxSignos_vitales_talla.setValue(enfermera_signos.getTalla());
            dbxSignos_vitales_imc.setValue(enfermera_signos.getImc());
            dbxSignos_vitales_pc.setValue(enfermera_signos
                    .getPerimetro_cefalico());
            dbxSignos_vitales_taxilar.setValue(enfermera_signos
                    .getTemperatura());
            dbxSignos_vitales_oximetria.setValue(enfermera_signos
                    .getOximetria());

            calcularIMC(dbxSignos_vitales_peso, dbxSignos_vitales_talla,
                    dbxSignos_vitales_imc);
            validarBotonesDE(0);
            validarBotonesDE(1);
            validarBotonesDE(2);
            alertaFc(dbxSignos_vitales_fc);
            alertaFr(dbxSignos_vitales_fr);
            alertaTemperatura(dbxSignos_vitales_taxilar);
            activar_check_oximetria();

        }
    }

    public void imprimir() throws Exception {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        Long nro_historia = infoPacientes.getCodigo_historia();
        if (nro_historia != null) {
            String parametros_pagina = "?codigo_empresa=" + codigo_empresa;
            parametros_pagina += "&codigo_sucursal=" + codigo_sucursal;
            parametros_pagina += "&codigo_historia=" + nro_historia;
            parametros_pagina += "&nro_ingreso=" + admision.getNro_ingreso();
            parametros_pagina += "&nro_identificacion=" + admision.getNro_identificacion();
            Clients.evalJavaScript("window.open(\"" + request.getContextPath() + "/pages/reports/hisc_aiepi_mn_2_meses_5_anios_reporte.zul" + parametros_pagina + "\", \"\",\"width=910, top=0, left=0\");");
        }
    }
}
