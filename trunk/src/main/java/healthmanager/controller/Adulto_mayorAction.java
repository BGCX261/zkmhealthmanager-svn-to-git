/*
 * adulto_mayorAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Adulto_mayor;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.GeneralExtraService;
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
import org.zkoss.zul.Bandpopup;
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
import org.zkoss.zul.Spinner;
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
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.ConvertidorXmlToMap;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorParaclinicos;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.UtilidadSignosVitales;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;

public class Adulto_mayorAction extends HistoriaClinicaModel implements
        IHistoria_generica {

    private static Logger log = Logger.getLogger(Adulto_mayorAction.class);

    // Componentes //
    @View(isMacro = true)
    private Remision_internaMacro macroRemision_interna;
    @View(isMacro = true)
    private Agudeza_visualMacro macroAgudeza_visual;
    @View
    private Row rowAgudeza_visual;
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
    private Adulto_mayor historia_anterior;
    @View
    private Textbox tbxNro_inscripcion;
    @View
    private Listbox lbxAtendida;

    @View
    private Toolbarbutton btnCancelar;

    @View(isMacro = true)
    private InformacionPacientesMacro infoPacientes;

    @View
    private Textbox tbxEnfremedad_actual;
    @View
    private Textbox tbxMotivo_consulta;
    @View
    private Textbox tbxDiagnostico;
    @View
    private Listbox lbxDiabetes;
    @View
    private Listbox lbxHipertension;
    @View
    private Listbox lbxDislipidemia;
    @View
    private Listbox lbxEnf_cerebrovascular;
    @View
    private Listbox lbxEnf_coronario;
    @View
    private Listbox lbxEnf_arterial;
    @View
    private Bandbox bandboxCardio_mas;
    @View
    private Bandbox bandboxCardio_fem;
    @View
    private Listbox lbxCa_cuello;
    @View
    private Listbox lbxCa_mama;
    @View
    private Listbox lbxCa_gastrico;
    @View
    private Listbox lbxCa_colorrectal;
    @View
    private Listbox lbxCa_prostata;
    @View
    private Textbox tbxMedico;
    @View
    private Textbox tbxQuirugicos;
    @View
    private Textbox tbxAlergicos;
    @View
    private Textbox tbxTraumaticos;
    @View
    private Textbox tbxHospitalizaciones;
    @View
    private Radiogroup rdbDiscapacidad;
    @View
    private Textbox tbxCual_discapacidad;
    @View
    private Intbox ibxObstetricos_g;
    @View
    private Intbox ibxObstetricos_p;
    @View
    private Intbox ibxObstetricos_a;
    @View
    private Intbox ibxObstetricos_c;
    @View
    private Intbox ibxObstetricos_v;
    @View
    private Checkbox chbPreclampsia;
    @View
    private Checkbox chbDiabetes_gestional;
    @View
    private Checkbox chbMenopausia;
    @View
    private Datebox dtbxFecha_citologia;
    @View
    private Textbox tbxResultado_citologia;
    @View
    private Datebox dtbxFecha_mama;
    @View
    private Textbox tbxResultado_mama;
    @View
    private Radiogroup rdbFecha_mamografia;
    @View
    private Textbox tbxResultado_mamografia;
    @View
    private Radiogroup rdbTabaquismo;
    @View
    private Radiogroup rdbActivoPasivo;
    @View
    private Datebox dtbxFecha_hasta;
    @View
    private Intbox ibxNo_cigarros;
    @View
    private Radiogroup rdbExposicion;
    @View
    private Checkbox chbEventual;
    @View
    private Checkbox chbSocial;
    @View
    private Checkbox chbSemanal;
    @View
    private Checkbox chbDiario;
    @View
    private Radiogroup rdbSustancias;
    @View
    private Textbox tbxCual_sustancias;
    @View
    private Radiogroup rdbEjercicio;
    @View
    private Textbox tbxCual_ejercicio;
    @View
    private Intbox ibxFrecuencia;
    @View
    private Intbox ibxVeces;
    @View
    private Intbox ibxIntensidad_horaria;
    @View
    private Checkbox chbCafalea;
    @View
    private Checkbox chbEpitaxis;
    @View
    private Checkbox chbTinitus;
    @View
    private Checkbox chbPalpitaciones;
    @View
    private Checkbox chbMareo;
    @View
    private Radiogroup rdbAlteraciones_visuales;
    @View
    private Textbox tbxCual_alteraciones_visuales;
    @View
    private Radiogroup rdbEdema;
    @View
    private Textbox tbxDonde_edema;
    @View
    private Checkbox chbPoliuria;
    @View
    private Checkbox chbPolidipsia;
    @View
    private Checkbox chbPolifagia;
    @View
    private Radiogroup rdbPerdida_peso;
    @View
    private Textbox tbxCuanto_peso;
    @View
    private Radiogroup rdbDolor_precordial;
    @View
    private Checkbox chbDisnea;
    @View
    private Checkbox chbDisartria;
    @View
    private Checkbox chbDiplopia;
    @View
    private Checkbox chbVertigo;
    @View
    private Radiogroup rdbDeficit_sensorial;
    @View
    private Textbox tbxDonde_deficit;
    @View
    private Checkbox chbParesias;
    @View
    private Checkbox chbParestesias;
    @View
    private Checkbox chbLipotimias;
    @View
    private Checkbox chbConvulsiones_focales;
    @View
    private Radiogroup rdbDolor_inferiores;
    @View
    private Checkbox chbDiarrea;
    @View
    private Checkbox chbDolor_abdo;
    @View
    private Checkbox chbMelena;
    @View
    private Checkbox chbHematoquexia;
    @View
    private Checkbox chbEstrenimiento;
    @View
    private Checkbox chbDeposiciones;
    @View
    private Checkbox chbAnorexia;
    @View
    private Checkbox chbEpigastralgia;
    @View
    private Checkbox chbDispepsia;
    @View
    private Checkbox chbHematemesis;
    @View
    private Checkbox chbSinusorragia;
    @View
    private Checkbox chbDispareunia;
    @View
    private Checkbox chbMastodinia;
    @View
    private Checkbox chbNicturia;
    @View
    private Checkbox chbUrgencia;
    @View
    private Radiogroup rdbOtros;
    @View
    private Textbox tbxCuales_otros;
    @View
    private Doublebox dbxSentado;
    @View
    private Doublebox dbxSentado2;
    @View
    private Doublebox dbxAcostado;
    @View
    private Doublebox dbxAcostado2;
    @View
    private Doublebox dbxPie;
    @View
    private Doublebox dbxPie2;
    @View
    private Doublebox dbxCardiaca;
    @View
    private Doublebox dbxPeso;
    @View
    private Doublebox dbxTalla;
    @View
    private Doublebox dbxImc;
    @View
    private Doublebox dbxCir_adbominal;
    @View
    private Doublebox dbxCir_cadera;
    @View
    private Doublebox dbxInd_cintura;
    @View
    private Textbox tbxExam_mama;
    @View
    private Label lbExam_mama;
    @View
    private Radiogroup rdbExc_mama;
    @View
    private Radiogroup rdbSintomaticos_respiratorio;
    @View
    private Radiogroup rdbSintomaticos_piel;
    @View
    private Textbox tbxHallazgos;
    @View
    private Textbox tbxTacto_rectal;
    @View
    private Textbox tbxPlan_intervencion;

    @View
    private Checkbox chbColesterol_hdl;
    @View
    private Checkbox chbColesterol_ldl;
    @View
    private Checkbox chbColesterol_total;
    @View
    private Checkbox chbTrigliceridos;
    @View
    private Checkbox chbCreatinina;
    @View
    private Checkbox chbUrianalisis;
    @View
    private Checkbox chbHemograma;
    @View
    private Checkbox chbGlicemia;
    @View
    private Checkbox chbCitologia;

    @View
    private Radiogroup rdbInferiores_esfuerzo;
    @View
    private Radiogroup rdbPrecordial_esfuerzo;

    @View
    private Listbox lbxTamizaje_cuello;

    // Control
    @View
    private Toolbarbutton toolbarbuttonTipo_historia;

    private final String[] idsExcluyentes = {"vboxParaclinicos", "tbxValue",
        "lbxParameter", "northEditar", "macroImpresion_diagnostica",
        "toolbarbuttonPaciente_admisionado1",
        "toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
        "gridOrdenes_servicio", "divModuloRemisiones_externas"};

    // Ajustar a Macro
    private Admision admision;
    private Citas cita;
    private Opciones_via_ingreso opciones_via_ingreso;

    @View
    private Toolbarbutton toolbarbuttonPaciente_admisionado1;
    @View
    private Toolbarbutton toolbarbuttonPaciente_admisionado2;

    @View
    private Toolbarbutton btGuardar;
    @View
    private Listbox lbxTipo_historia;

    // Paraclinicos
    @View
    private Vbox vboxParaclinicos;
    private ManejadorParaclinicos manejadorParaclinicos;

    private String tipo_historia;
    private Long codigo_historia_anterior;

    // Ocultar Campos Gineco-Obstetricos
    @View
    private Row rowCitologia;
    @View
    private Row rowCitologia2;
    @View
    private Row rowObstetricos;
    @View
    private Row rowGinecologica;

    // Campos Ocultos
    @View
    private Label lbCual_discapacidad;
    @View
    private Label lbFecha_hasta;
    @View
    private Label lbNo_cigarros;
    @View
    private Label lbCual_sustancias;
    @View
    private Hlayout hlay_1;
    @View
    private Label lbEventual;
    @View
    private Label lbSocial;
    @View
    private Label lbSemanal;
    @View
    private Label lbDiario;
    @View
    private Row row2; // row ejercicios
    @View
    private Label lbCual_ejercicio;
    @View
    private Label lbFrecuencia;
    @View
    private Label lbVeces;
    @View
    private Label lbIntensidad_horaria;
    @View
    private Label lbResultado_mamografia;
    @View
    private Cell cellTacto_rectal;

    // macro impresion diagnostica
    @View(isMacro = true)
    private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
    @View
    private Div divModuloFarmacologico;
    @View
    private Div divModuloOrdenamiento;
    private Receta_rips_internoAction receta_ripsAction;
    private Orden_servicio_internoAction orden_servicioAction;

    // private String valida_admision;
    private String nro_ingreso_admision;

    @View
    private Div divModuloRemisiones_externas;

    private Remisiones_externasAction remisiones_externasAction;
    private boolean cobrar_agudeza;

    @View
    private Toolbarbutton btnImprimir;

    @View
    private Listbox lbxFormato;

    public void alarmaExamenFisicoFc() {
        UtilidadSignosVitales.validarMaxMin(dbxCardiaca, 61d, 39d, "Anormal",
                "Anormal", false);
    }

    @Override
    public void init() {
        try {
            listarCombos();
            crearResultadosParaclinicos();
            tbxNro_inscripcion.setValue("1");
            dtbxFecha_citologia.setValue(null);
            dtbxFecha_mama.setValue(null);

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void crearResultadosParaclinicos() {
        manejadorParaclinicos = new ManejadorParaclinicos(
                ManejadorParaclinicos.TIPO_PARACLINICO, vboxParaclinicos,
                "00003", admision.getNro_identificacion(), this);
    }

    @Override
    public void params(Map<String, Object> map) {
        if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
            admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
            cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
            opciones_via_ingreso = (Opciones_via_ingreso) map
                    .get(IVias_ingreso.OPCION_VIA_INGRESO);
            macroRemision_interna.deshabilitarCheck(admision,
                    IVias_ingreso.ADMISION_PACIENTE);
        }
    }

    public void listarCombos() {
        listarParameter();
        listarAtendida();
        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar("ante_familiares");
        UtilidadesComponentes.listarElementosListbox(true, elementos, true,
                lbxDiabetes, lbxHipertension, lbxDislipidemia,
                lbxEnf_cerebrovascular, lbxEnf_coronario, lbxEnf_arterial,
                lbxCa_cuello, lbxCa_mama, lbxCa_gastrico, lbxCa_colorrectal,
                lbxCa_prostata, lbxTamizaje_cuello);
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
        if (admision != null) {
            this.nro_ingreso_admision = admision.getNro_ingreso();
            infoPacientes.setFecha_inicio(new Date());
            // valida_admision = null;
            cargarInformacion_paciente();
            onMostrarModuloFarmacologico();
            receta_ripsAction.obtenerBotonAgregar().setVisible(true);
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(true);
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
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        manejadorParaclinicos.limpiarResultados_paraclinicos();
    }

    public void limpiarValidar() {
        // tbxNro_inscripcion.setStyle("text-transform:uppercase;background-color:white");
        tbxEnfremedad_actual
                .setStyle("text-transform:uppercase;background-color:white");
        tbxMotivo_consulta
                .setStyle("text-transform:uppercase;background-color:white");
        tbxDiagnostico
                .setStyle("text-transform:uppercase;background-color:white");
        lbxDiabetes.setStyle("text-transform:uppercase;background-color:white");
        lbxHipertension
                .setStyle("text-transform:uppercase;background-color:white");
        lbxDislipidemia
                .setStyle("text-transform:uppercase;background-color:white");
        lbxEnf_cerebrovascular
                .setStyle("text-transform:uppercase;background-color:white");
        lbxEnf_coronario
                .setStyle("text-transform:uppercase;background-color:white");
        lbxEnf_arterial
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCa_cuello
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCa_mama.setStyle("text-transform:uppercase;background-color:white");
        lbxCa_gastrico
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCa_colorrectal
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCa_prostata
                .setStyle("text-transform:uppercase;background-color:white");
        tbxMedico.setStyle("text-transform:uppercase;background-color:white");
        tbxQuirugicos
                .setStyle("text-transform:uppercase;background-color:white");
        tbxAlergicos
                .setStyle("text-transform:uppercase;background-color:white");
        tbxTraumaticos
                .setStyle("text-transform:uppercase;background-color:white");
        tbxHospitalizaciones
                .setStyle("text-transform:uppercase;background-color:white");
        dbxSentado.setStyle("text-transform:uppercase;background-color:white");
        dbxSentado2.setStyle("text-transform:uppercase;background-color:white");
        dbxAcostado.setStyle("text-transform:uppercase;background-color:white");
        dbxAcostado2
                .setStyle("text-transform:uppercase;background-color:white");
        dbxPie.setStyle("text-transform:uppercase;background-color:white");
        dbxPie2.setStyle("text-transform:uppercase;background-color:white");
        dbxCardiaca.setStyle("text-transform:uppercase;background-color:white");
        dbxPeso.setStyle("text-transform:uppercase;background-color:white");
        dbxTalla.setStyle("text-transform:uppercase;background-color:white");
        dbxImc.setStyle("text-transform:uppercase;background-color:white");
        dbxCir_adbominal
                .setStyle("text-transform:uppercase;background-color:white");
        dbxCir_cadera
                .setStyle("text-transform:uppercase;background-color:white");
        dbxInd_cintura
                .setStyle("text-transform:uppercase;background-color:white");
        tbxPlan_intervencion
                .setStyle("text-transform:uppercase;background-color:white");
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        limpiarValidar();
        boolean valida = true;
        infoPacientes.validarInformacionPaciente();
        FormularioUtil.validarCamposObligatorios(tbxEnfremedad_actual,
                tbxMotivo_consulta, tbxDiagnostico, tbxMedico, tbxQuirugicos,
                tbxAlergicos, tbxTraumaticos, tbxHospitalizaciones, dbxSentado,
                dbxSentado2, dbxAcostado, dbxAcostado2, dbxPie, dbxPie2,
                dbxCardiaca, dbxPeso, dbxTalla, dbxImc, dbxCir_adbominal,
                dbxCir_cadera, dbxInd_cintura, tbxPlan_intervencion);

        if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
            if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
                valida = macroAgudeza_visual.validarCamposObligatorios();
            }
        }

        if (valida) {
            valida = receta_ripsAction.validarFormExterno();
        }

        if (valida) {
            valida = remisiones_externasAction.validarRemision();
        }

        if (valida) {
            valida = orden_servicioAction.validarFormExterno();
        }

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres()
                    + " recuerde que...",
                    "Los campos marcados con (*) son obligatorios");
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

            List<Adulto_mayor> lista_datos = getServiceLocator()
                    .getAdulto_mayorService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Adulto_mayor adulto_mayor : lista_datos) {
                rowsResultado.appendChild(crearFilas(adulto_mayor, this));
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

        final Adulto_mayor adulto_mayor = (Adulto_mayor) objeto;

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(adulto_mayor.getCodigo_historia() + ""));
        fila.appendChild(new Label(adulto_mayor.getIdentificacion() + ""));

        fila.appendChild(new Label(admision.getPaciente().getNombre1()
                + (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
                + admision.getPaciente().getNombre2()) + " "
                + admision.getPaciente().getApellido1() + " "
                + admision.getPaciente().getApellido2() + ""));

        Datebox datebox = new Datebox(adulto_mayor.getFecha_inicial());
        datebox.setButtonVisible(false);
        datebox.setFormat("yyyy-MM-dd");
        datebox.setWidth("98%");
        datebox.setReadonly(true);
        fila.appendChild(datebox);

        fila.appendChild(new Label(adulto_mayor.getTipo_historia().equals(
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
                        mostrarDatos(adulto_mayor);
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
                                            eliminarDatos(adulto_mayor);
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

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            if (validarForm()) {
                FormularioUtil.setUpperCase(groupboxEditar);
                // Cargamos los componentes //
                Adulto_mayor adulto_mayor = new Adulto_mayor();
                adulto_mayor.setCodigo_empresa(empresa.getCodigo_empresa());
                adulto_mayor.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                adulto_mayor.setCodigo_historia(infoPacientes
                        .getCodigo_historia());
                adulto_mayor
                        .setIdentificacion(admision.getNro_identificacion());
                adulto_mayor.setFecha_inicial(new Timestamp(infoPacientes
                        .getFecha_inicial().getTime()));
                adulto_mayor.setUltimo_update(new Timestamp((new Date())
                        .getTime()));
                // adulto_mayor.setFecha_cierre(new Timestamp((new
                // Date()).getTime()));
                adulto_mayor.setNro_inscripcion(tbxNro_inscripcion.getValue());
                adulto_mayor.setEnfremedad_actual(tbxEnfremedad_actual
                        .getValue());
                adulto_mayor.setMotivo_consulta(tbxMotivo_consulta.getValue());
                adulto_mayor.setDiagnostico(tbxDiagnostico.getValue());
                adulto_mayor.setDiabetes(lbxDiabetes.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setHipertension(lbxHipertension.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setDislipidemia(lbxDislipidemia.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setEnf_cerebrovascular(lbxEnf_cerebrovascular
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setEnf_coronario(lbxEnf_coronario
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setEnf_arterial(lbxEnf_arterial.getSelectedItem()
                        .getValue().toString());
                adulto_mayor
                        .setCardio_mas(obtenerXmlTextbox(bandboxCardio_mas));
                adulto_mayor
                        .setCardio_fem(obtenerXmlTextbox(bandboxCardio_fem));

                adulto_mayor.setCa_cuello(lbxCa_cuello.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setCa_mama(lbxCa_mama.getSelectedItem().getValue()
                        .toString());
                adulto_mayor.setCa_gastrico(lbxCa_gastrico.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setCa_colorrectal(lbxCa_colorrectal
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setCa_prostata(lbxCa_prostata.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setMedico(tbxMedico.getValue());
                adulto_mayor.setQuirugicos(tbxQuirugicos.getValue());
                adulto_mayor.setAlergicos(tbxAlergicos.getValue());
                adulto_mayor.setTraumaticos(tbxTraumaticos.getValue());
                adulto_mayor.setHospitalizaciones(tbxHospitalizaciones
                        .getValue());
                adulto_mayor.setDiscapacidad(rdbDiscapacidad.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setCual_discapacidad(tbxCual_discapacidad
                        .getValue());
                adulto_mayor
                        .setObstetricos_g((ibxObstetricos_g.getValue() != null ? ibxObstetricos_g
                                .getValue() + ""
                                : ""));
                adulto_mayor
                        .setObstetricos_p((ibxObstetricos_p.getValue() != null ? ibxObstetricos_p
                                .getValue() + ""
                                : ""));
                adulto_mayor
                        .setObstetricos_a((ibxObstetricos_a.getValue() != null ? ibxObstetricos_a
                                .getValue() + ""
                                : ""));
                adulto_mayor
                        .setObstetricos_c((ibxObstetricos_c.getValue() != null ? ibxObstetricos_c
                                .getValue() + ""
                                : ""));
                adulto_mayor
                        .setObstetricos_c((ibxObstetricos_v.getValue() != null ? ibxObstetricos_v
                                .getValue() + ""
                                : ""));

                adulto_mayor.setPreclampsia(chbPreclampsia.isChecked());
                adulto_mayor.setDiabetes_gestional(chbDiabetes_gestional
                        .isChecked());
                adulto_mayor.setMenopausia(chbMenopausia.isChecked());

                if (dtbxFecha_citologia.getValue() != null) {
                    adulto_mayor.setFecha_citologia(new Timestamp(
                            dtbxFecha_citologia.getValue().getTime()));
                }

                adulto_mayor.setResultado_citologia(tbxResultado_citologia
                        .getValue());

                if (dtbxFecha_mama.getValue() != null) {
                    adulto_mayor.setFecha_mama(new Timestamp(dtbxFecha_mama
                            .getValue().getTime()));
                }
                adulto_mayor.setResultado_mama(tbxResultado_mama.getValue());
                adulto_mayor.setFecha_mamografia(rdbFecha_mamografia
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setResultado_mamografia(tbxResultado_mamografia
                        .getValue());
                adulto_mayor.setTabaquismo(rdbTabaquismo.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setActivoPasivo(rdbActivoPasivo.getSelectedItem()
                        .getValue().toString());

                if (dtbxFecha_hasta.getValue() != null) {
                    adulto_mayor.setFecha_hasta(new Timestamp(dtbxFecha_hasta
                            .getValue().getTime()));
                }

                adulto_mayor
                        .setNo_cigarros((ibxNo_cigarros.getValue() != null ? ibxNo_cigarros
                                .getValue() + ""
                                : ""));

                adulto_mayor.setExposicion(rdbExposicion.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setEventual(chbEventual.isChecked());
                adulto_mayor.setSocial(chbSocial.isChecked());
                adulto_mayor.setSemanal(chbSemanal.isChecked());
                adulto_mayor.setDiario(chbDiario.isChecked());
                adulto_mayor.setSustancias(rdbSustancias.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setCual_sustancias(tbxCual_sustancias.getValue());
                adulto_mayor.setEjercicio(rdbEjercicio.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setCual_ejercicio(tbxCual_ejercicio.getValue());
                adulto_mayor
                        .setFrecuencia((ibxFrecuencia.getValue() != null ? ibxFrecuencia
                                .getValue() + ""
                                : ""));
                adulto_mayor.setVeces((ibxVeces.getValue() != null ? ibxVeces
                        .getValue() + "" : ""));
                adulto_mayor.setIntensidad_horaria((ibxIntensidad_horaria
                        .getValue() != null ? ibxIntensidad_horaria.getValue()
                        + "" : ""));

                adulto_mayor.setCafalea(chbCafalea.isChecked());
                adulto_mayor.setEpitaxis(chbEpitaxis.isChecked());
                adulto_mayor.setTinitus(chbTinitus.isChecked());
                adulto_mayor.setPalpitaciones(chbPalpitaciones.isChecked());
                adulto_mayor.setMareo(chbMareo.isChecked());
                adulto_mayor.setAlteraciones_visuales(rdbAlteraciones_visuales
                        .getSelectedItem().getValue().toString());
                adulto_mayor
                        .setCual_alteraciones_visuales(tbxCual_alteraciones_visuales
                                .getValue());
                adulto_mayor.setEdema(rdbEdema.getSelectedItem().getValue()
                        .toString());
                adulto_mayor.setDonde_edema(tbxDonde_edema.getValue());
                adulto_mayor.setPoliuria(chbPoliuria.isChecked());
                adulto_mayor.setPolidipsia(chbPolidipsia.isChecked());
                adulto_mayor.setPolifagia(chbPolifagia.isChecked());
                adulto_mayor.setPerdida_peso(rdbPerdida_peso.getSelectedItem()
                        .getValue().toString());
                adulto_mayor.setCuanto_peso(tbxCuanto_peso.getValue());
                adulto_mayor.setDolor_precordial(rdbDolor_precordial
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setPrecordial_esfuerzo(rdbPrecordial_esfuerzo
                        .getSelectedItem().getValue().toString());

                adulto_mayor.setDisnea(chbDisnea.isChecked());
                adulto_mayor.setDisartria(chbDisartria.isChecked());
                adulto_mayor.setDiplopia(chbDiplopia.isChecked());
                adulto_mayor.setVertigo(chbVertigo.isChecked());
                adulto_mayor.setDeficit_sensorial(rdbDeficit_sensorial
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setDonde_deficit(tbxDonde_deficit.getValue());
                adulto_mayor.setParesias(chbParesias.isChecked());
                adulto_mayor.setParestesias(chbParestesias.isChecked());
                adulto_mayor.setLipotimias(chbLipotimias.isChecked());
                adulto_mayor.setConvulsiones_focales(chbConvulsiones_focales
                        .isChecked());
                adulto_mayor.setDolor_inferiores(rdbDolor_inferiores
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setInferiores_esfuerzo(rdbInferiores_esfuerzo
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setDiarrea(chbDiarrea.isChecked());
                adulto_mayor.setDolor_abdo(chbDolor_abdo.isChecked());
                adulto_mayor.setMelena(chbMelena.isChecked());
                adulto_mayor.setHematoquexia(chbHematoquexia.isChecked());
                adulto_mayor.setEstrenimiento(chbEstrenimiento.isChecked());
                adulto_mayor.setDeposiciones(chbDeposiciones.isChecked());
                adulto_mayor.setAnorexia(chbAnorexia.isChecked());
                adulto_mayor.setEpigastralgia(chbEpigastralgia.isChecked());
                adulto_mayor.setDispepsia(chbDispepsia.isChecked());
                adulto_mayor.setHematemesis(chbHematemesis.isChecked());
                adulto_mayor.setSinusorragia(chbSinusorragia.isChecked());
                adulto_mayor.setDispareunia(chbDispareunia.isChecked());
                adulto_mayor.setMastodinia(chbMastodinia.isChecked());
                adulto_mayor.setNicturia(chbNicturia.isChecked());
                adulto_mayor.setUrgencia(chbUrgencia.isChecked());
                adulto_mayor.setOtros(rdbOtros.getSelectedItem().getValue()
                        .toString());
                adulto_mayor.setCuales_otros(tbxCuales_otros.getValue());

                adulto_mayor
                        .setSentado(dbxSentado.getValue() != null ? dbxSentado
                                .getValue() + "" : "");
                adulto_mayor
                        .setSentado2(dbxSentado2.getValue() != null ? dbxSentado2
                                .getValue() + ""
                                : "");
                adulto_mayor
                        .setAcostado(dbxAcostado.getValue() != null ? dbxAcostado
                                .getValue() + ""
                                : "");
                adulto_mayor
                        .setAcostado2(dbxAcostado2.getValue() != null ? dbxAcostado2
                                .getValue() + ""
                                : "");
                adulto_mayor.setPie(dbxPie.getValue() != null ? dbxPie
                        .getValue() + "" : "");
                adulto_mayor.setPie2(dbxPie2.getValue() != null ? dbxPie2
                        .getValue() + "" : "");

                adulto_mayor
                        .setCardiaca(dbxCardiaca.getValue() != null ? dbxCardiaca
                                .getValue() + ""
                                : "");

                adulto_mayor.setPeso(dbxPeso.getValue() != null ? dbxPeso
                        .getValue() + "" : "");
                adulto_mayor.setTalla(dbxTalla.getValue() != null ? dbxTalla
                        .getValue() + "" : "");
                adulto_mayor.setImc(dbxImc.getValue() != null ? dbxImc
                        .getValue() + "" : "");

                adulto_mayor.setCir_adbominal(dbxCir_adbominal.getValue()
                        .toString());
                adulto_mayor.setCir_cadera(dbxCir_cadera.getValue().toString());
                adulto_mayor.setInd_cintura(dbxInd_cintura.getValue()
                        .toString());
                adulto_mayor.setExc_mama(rdbExc_mama.getSelectedItem()
                        .getValue().toString());
                adulto_mayor
                        .setSintomaticos_respiratorio(rdbSintomaticos_respiratorio
                                .getSelectedItem().getValue().toString());
                adulto_mayor.setSintomaticos_piel(rdbSintomaticos_piel
                        .getSelectedItem().getValue().toString());
                adulto_mayor.setExam_mama(tbxExam_mama.getValue());
                adulto_mayor.setHallazgos(tbxHallazgos.getValue());
                adulto_mayor.setTacto_rectal(tbxTacto_rectal.getValue());

                adulto_mayor.setPlan_intervencion(tbxPlan_intervencion
                        .getValue());

                adulto_mayor.setColesterol_hdl(chbColesterol_hdl.isChecked());
                adulto_mayor.setColesterol_ldl(chbColesterol_ldl.isChecked());
                adulto_mayor.setColesterol_total(chbColesterol_total
                        .isChecked());
                adulto_mayor.setTrigliceridos(chbTrigliceridos.isChecked());
                adulto_mayor.setCreatinina(chbCreatinina.isChecked());
                adulto_mayor.setUrianalisis(chbUrianalisis.isChecked());
                adulto_mayor.setHemograma(chbHemograma.isChecked());
                adulto_mayor.setGlicemia(chbGlicemia.isChecked());
                adulto_mayor.setCitologia(chbCitologia.isChecked());
                adulto_mayor.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                // adulto_mayor.setUltimo_update(new Timestamp(
                // new GregorianCalendar().getTimeInMillis()));
                adulto_mayor.setCreacion_user(usuarios.getCodigo().toString());
                adulto_mayor.setDelete_date(null);
                adulto_mayor.setUltimo_user(usuarios.getCodigo().toString());
                adulto_mayor.setDelete_user(null);
                adulto_mayor.setTipo_historia(tipo_historia);
                adulto_mayor.setCodigo_historia_anterior(adulto_mayor
                        .getCodigo_historia());
                if (historia_anterior != null) {
                    adulto_mayor.setCodigo_historia_anterior(historia_anterior
                            .getCodigo_historia());
                }

                adulto_mayor.setNro_ingreso(admision.getNro_ingreso());
                adulto_mayor.setTamizaje_cuello(lbxTamizaje_cuello
                        .getSelectedItem().getValue().toString());

                Map<String, Object> datos = new HashMap<String, Object>();
                datos.put("historia_clinica", adulto_mayor);
                datos.put("admision", admision);
                datos.put("listado_paraclinicos",
                        manejadorParaclinicos.obtenerResultados_paraclinicos());
                datos.put("accion", tbxAccion.getValue());
                // log.info("cobrar_agudeza>>>>>>>>>>>>>>" + cobrar_agudeza);
                datos.put("cobrar_agudeza", cobrar_agudeza);

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

                Agudeza_visual agudeza_visual = macroAgudeza_visual
                        .obtenerAgudezaVisual();
                agudeza_visual.setAnio(anio);
                datos.put("agudeza_visual", agudeza_visual);

                datos.put("impresion_diagnostica", impresion_diagnostica);
                Anexo9_entidad anexo9_entidad = remisiones_externasAction
                        .obtenerAnexo9();
                datos.put("anexo9", anexo9_entidad);
                datos.put("cita_seleccionada", cita);
                datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());
                getServiceLocator().getAdulto_mayorService()
                        .guardarDatos(datos);

                mostrarDatosAutorizacion(datos);
                if (anexo9_entidad != null) {
                    remisiones_externasAction.setCodigo_remision(anexo9_entidad
                            .getCodigo());
                    remisiones_externasAction.getBotonImprimir().setDisabled(
                            false);
                }

                tbxAccion.setValue("modificar");
                infoPacientes.setCodigo_historia(adulto_mayor
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
        Adulto_mayor adulto_mayor = (Adulto_mayor) obj;
        try {
            this.nro_ingreso_admision = adulto_mayor.getNro_ingreso();

            infoPacientes.setCodigo_historia(adulto_mayor.getCodigo_historia());
            infoPacientes.setFecha_inicio(adulto_mayor.getFecha_inicial());
            infoPacientes
                    .setFecha_cierre(true, adulto_mayor.getUltimo_update());
            // infoPacientes.setFecha_cierre(true,
            // adultoMayor.getFecha_cierre());

            tbxNro_inscripcion.setValue(adulto_mayor.getNro_inscripcion());
            onMostrarModuloRemisiones();
            initMostrar_datos(adulto_mayor);

            cargarRemisionInterna(adulto_mayor);
            cargarAgudezaVisual(adulto_mayor);

            cargarInformacion_paciente();

            tbxEnfremedad_actual.setValue(adulto_mayor.getEnfremedad_actual());
            tbxMotivo_consulta.setValue(adulto_mayor.getMotivo_consulta());
            tbxDiagnostico.setValue(adulto_mayor.getDiagnostico());
            chbColesterol_hdl.setChecked(adulto_mayor.getColesterol_hdl());
            chbColesterol_ldl.setChecked(adulto_mayor.getColesterol_ldl());
            chbColesterol_total.setChecked(adulto_mayor.getColesterol_total());
            chbTrigliceridos.setChecked(adulto_mayor.getTrigliceridos());
            chbCreatinina.setChecked(adulto_mayor.getCreatinina());
            chbUrianalisis.setChecked(adulto_mayor.getUrianalisis());
            chbHemograma.setChecked(adulto_mayor.getHemograma());
            chbGlicemia.setChecked(adulto_mayor.getGlicemia());
            chbCitologia.setChecked(adulto_mayor.getCitologia());

            for (int i = 0; i < lbxDiabetes.getItemCount(); i++) {
                Listitem listitem = lbxDiabetes.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getDiabetes())) {
                    listitem.setSelected(true);
                    i = lbxDiabetes.getItemCount();
                }
            }

            for (int i = 0; i < lbxHipertension.getItemCount(); i++) {
                Listitem listitem = lbxHipertension.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getHipertension())) {
                    listitem.setSelected(true);
                    i = lbxHipertension.getItemCount();
                }
            }

            for (int i = 0; i < lbxDislipidemia.getItemCount(); i++) {
                Listitem listitem = lbxDislipidemia.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getDislipidemia())) {
                    listitem.setSelected(true);
                    i = lbxDislipidemia.getItemCount();
                }
            }

            for (int i = 0; i < lbxEnf_cerebrovascular.getItemCount(); i++) {
                Listitem listitem = lbxEnf_cerebrovascular.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getEnf_cerebrovascular())) {
                    listitem.setSelected(true);
                    i = lbxEnf_cerebrovascular.getItemCount();
                }
            }
            for (int i = 0; i < lbxEnf_coronario.getItemCount(); i++) {
                Listitem listitem = lbxEnf_coronario.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getEnf_coronario())) {
                    listitem.setSelected(true);
                    i = lbxEnf_coronario.getItemCount();
                }
            }
            for (int i = 0; i < lbxEnf_arterial.getItemCount(); i++) {
                Listitem listitem = lbxEnf_arterial.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getEnf_arterial())) {
                    listitem.setSelected(true);
                    i = lbxEnf_arterial.getItemCount();
                }
            }
            for (int i = 0; i < lbxCa_cuello.getItemCount(); i++) {
                Listitem listitem = lbxCa_cuello.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getCa_cuello())) {
                    listitem.setSelected(true);
                    i = lbxCa_cuello.getItemCount();
                }
            }
            for (int i = 0; i < lbxCa_mama.getItemCount(); i++) {
                Listitem listitem = lbxCa_mama.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getCa_mama())) {
                    listitem.setSelected(true);
                    i = lbxCa_mama.getItemCount();
                }
            }
            for (int i = 0; i < lbxCa_gastrico.getItemCount(); i++) {
                Listitem listitem = lbxCa_gastrico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getCa_gastrico())) {
                    listitem.setSelected(true);
                    i = lbxCa_gastrico.getItemCount();
                }
            }
            for (int i = 0; i < lbxCa_gastrico.getItemCount(); i++) {
                Listitem listitem = lbxCa_gastrico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getCa_colorrectal())) {
                    listitem.setSelected(true);
                    i = lbxCa_gastrico.getItemCount();
                }
            }
            for (int i = 0; i < lbxCa_prostata.getItemCount(); i++) {
                Listitem listitem = lbxCa_prostata.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(adulto_mayor.getCa_prostata())) {
                    listitem.setSelected(true);
                    i = lbxCa_prostata.getItemCount();
                }
            }

            mostrarXmlTextbox(bandboxCardio_mas,
                    ConvertidorXmlToMap.convertirToMap(adulto_mayor
                            .getCardio_mas()));
            mostrarXmlTextbox(bandboxCardio_fem,
                    ConvertidorXmlToMap.convertirToMap(adulto_mayor
                            .getCardio_fem()));

            tbxMedico.setValue(adulto_mayor.getMedico());
            tbxQuirugicos.setValue(adulto_mayor.getQuirugicos());
            tbxAlergicos.setValue(adulto_mayor.getAlergicos());
            tbxTraumaticos.setValue(adulto_mayor.getTraumaticos());
            tbxHospitalizaciones.setValue(adulto_mayor.getHospitalizaciones());
            Utilidades.seleccionarRadio(rdbDiscapacidad,
                    adulto_mayor.getDiscapacidad());

            if (adulto_mayor.getDiscapacidad().equals("1")) {
                lbCual_discapacidad.setVisible(true);
                tbxCual_discapacidad.setVisible(true);
                tbxCual_discapacidad.setValue(adulto_mayor
                        .getCual_discapacidad());
            } else {
                lbCual_discapacidad.setVisible(false);
                tbxCual_discapacidad.setVisible(false);

            }

            ibxObstetricos_g
                    .setValue((adulto_mayor.getObstetricos_g() != null && !adulto_mayor
                            .getObstetricos_g().isEmpty()) ? Integer
                            .parseInt(adulto_mayor.getObstetricos_g()) : null);
            ibxObstetricos_p
                    .setValue((adulto_mayor.getObstetricos_p() != null && !adulto_mayor
                            .getObstetricos_p().isEmpty()) ? Integer
                            .parseInt(adulto_mayor.getObstetricos_p()) : null);
            ibxObstetricos_a
                    .setValue((adulto_mayor.getObstetricos_a() != null && !adulto_mayor
                            .getObstetricos_a().isEmpty()) ? Integer
                            .parseInt(adulto_mayor.getObstetricos_a()) : null);
            ibxObstetricos_c
                    .setValue((adulto_mayor.getObstetricos_c() != null && !adulto_mayor
                            .getObstetricos_c().isEmpty()) ? Integer
                            .parseInt(adulto_mayor.getObstetricos_c()) : null);

            ibxObstetricos_v
                    .setValue((adulto_mayor.getObstetricos_v() != null && !adulto_mayor
                            .getObstetricos_v().isEmpty()) ? Integer
                            .parseInt(adulto_mayor.getObstetricos_v()) : null);

            chbPreclampsia.setChecked(adulto_mayor.getPreclampsia());
            chbDiabetes_gestional.setChecked(adulto_mayor
                    .getDiabetes_gestional());
            chbMenopausia.setChecked(adulto_mayor.getMenopausia());

            dtbxFecha_citologia.setValue(adulto_mayor.getFecha_hasta());
            tbxResultado_citologia.setValue(adulto_mayor
                    .getResultado_citologia());
            dtbxFecha_mama.setValue(adulto_mayor.getFecha_hasta());
            tbxResultado_mama.setValue(adulto_mayor.getResultado_mama());
            Utilidades.seleccionarRadio(rdbFecha_mamografia,
                    adulto_mayor.getFecha_mamografia());

            if (adulto_mayor.getFecha_mamografia().equals("1")) {
                lbResultado_mamografia.setVisible(true);
                tbxResultado_mamografia.setVisible(true);
                tbxResultado_mamografia.setValue(adulto_mayor
                        .getResultado_mamografia());
            } else {
                lbResultado_mamografia.setVisible(false);
                tbxResultado_mamografia.setVisible(false);

            }

            Utilidades.seleccionarRadio(rdbTabaquismo,
                    adulto_mayor.getTabaquismo());
            seleccion_tabaquismo();
            Utilidades.seleccionarRadio(rdbActivoPasivo,
                    adulto_mayor.getActivoPasivo());
            seleccion_activo_pasivo();

            if (adulto_mayor.getTabaquismo().equals("1")) {
                dtbxFecha_hasta.setValue(adulto_mayor.getFecha_hasta());
                ibxNo_cigarros
                        .setValue((adulto_mayor.getNo_cigarros() != null && !adulto_mayor
                                .getNo_cigarros().isEmpty()) ? Integer
                                .parseInt(adulto_mayor.getNo_cigarros()) : null);
            }

            Utilidades.seleccionarRadio(rdbExposicion,
                    adulto_mayor.getExposicion());

            if (adulto_mayor.getExposicion().equals("1")) {

                hlay_1.setVisible(true);
                lbEventual.setVisible(true);
                lbSocial.setVisible(true);
                lbSemanal.setVisible(true);
                lbDiario.setVisible(true);

                chbEventual.setVisible(true);
                chbSocial.setVisible(true);
                chbSemanal.setVisible(true);
                chbDiario.setVisible(true);

                chbEventual.setChecked(adulto_mayor.getEventual());
                chbSocial.setChecked(adulto_mayor.getSocial());
                chbSemanal.setChecked(adulto_mayor.getSemanal());
                chbDiario.setChecked(adulto_mayor.getDiario());
            } else {

                hlay_1.setVisible(false);
                lbEventual.setVisible(false);
                lbSocial.setVisible(false);
                lbSemanal.setVisible(false);
                lbDiario.setVisible(false);

                chbEventual.setVisible(false);
                chbSocial.setVisible(false);
                chbSemanal.setVisible(false);
                chbDiario.setVisible(false);

            }

            Utilidades.seleccionarRadio(rdbSustancias,
                    adulto_mayor.getSustancias());

            if (adulto_mayor.getSustancias().equals("1")) {
                lbCual_sustancias.setVisible(true);
                tbxCual_sustancias.setVisible(true);
                tbxCual_sustancias.setValue(adulto_mayor.getCual_sustancias());
            } else {
                lbCual_sustancias.setVisible(false);
                tbxCual_sustancias.setVisible(false);

            }

            Utilidades.seleccionarRadio(rdbEjercicio,
                    adulto_mayor.getEjercicio());

            if (adulto_mayor.getEjercicio().equals("1")) {
                row2.setVisible(true);
                lbCual_ejercicio.setVisible(true);
                lbFrecuencia.setVisible(true);
                lbVeces.setVisible(true);
                lbIntensidad_horaria.setVisible(true);

                tbxCual_ejercicio.setVisible(true);
                ibxFrecuencia.setVisible(true);
                ibxVeces.setVisible(true);
                ibxIntensidad_horaria.setVisible(true);

                tbxCual_ejercicio.setValue(adulto_mayor.getCual_ejercicio());
                ibxFrecuencia
                        .setValue((adulto_mayor.getFrecuencia() != null && !adulto_mayor
                                .getFrecuencia().isEmpty()) ? Integer
                                .parseInt(adulto_mayor.getFrecuencia()) : null);
                ibxVeces.setValue((adulto_mayor.getVeces() != null && !adulto_mayor
                        .getVeces().isEmpty()) ? Integer.parseInt(adulto_mayor
                                .getVeces()) : null);
                ibxIntensidad_horaria.setValue((adulto_mayor
                        .getIntensidad_horaria() != null && !adulto_mayor
                        .getIntensidad_horaria().isEmpty()) ? Integer
                        .parseInt(adulto_mayor.getIntensidad_horaria()) : null);
            } else {
                row2.setVisible(false);
                lbCual_ejercicio.setVisible(false);
                lbFrecuencia.setVisible(false);
                lbVeces.setVisible(false);
                lbIntensidad_horaria.setVisible(false);

                tbxCual_ejercicio.setVisible(false);
                ibxFrecuencia.setVisible(false);
                ibxVeces.setVisible(false);
                ibxIntensidad_horaria.setVisible(false);

            }

            chbCafalea.setChecked(adulto_mayor.getCafalea());
            chbEpitaxis.setChecked(adulto_mayor.getEpitaxis());
            chbTinitus.setChecked(adulto_mayor.getTinitus());
            chbPalpitaciones.setChecked(adulto_mayor.getPalpitaciones());
            chbMareo.setChecked(adulto_mayor.getMareo());
            Utilidades.seleccionarRadio(rdbAlteraciones_visuales,
                    adulto_mayor.getAlteraciones_visuales());
            tbxCual_alteraciones_visuales.setValue(adulto_mayor
                    .getCual_alteraciones_visuales());
            Utilidades.seleccionarRadio(rdbEdema, adulto_mayor.getEdema());
            tbxDonde_edema.setValue(adulto_mayor.getDonde_edema());
            chbPoliuria.setChecked(adulto_mayor.getPoliuria());
            chbPolidipsia.setChecked(adulto_mayor.getPolidipsia());
            chbPolifagia.setChecked(adulto_mayor.getPolifagia());
            Utilidades.seleccionarRadio(rdbPerdida_peso,
                    adulto_mayor.getPerdida_peso());
            tbxCuanto_peso.setValue(adulto_mayor.getCuanto_peso());
            Utilidades.seleccionarRadio(rdbDolor_precordial,
                    adulto_mayor.getDolor_precordial());
            seleccion_radio2(rdbDolor_precordial,
                    new AbstractComponent[]{rdbPrecordial_esfuerzo});
            Utilidades.seleccionarRadio(rdbPrecordial_esfuerzo,
                    adulto_mayor.getPrecordial_esfuerzo());
            chbDisnea.setChecked(adulto_mayor.getDisnea());
            chbDisartria.setChecked(adulto_mayor.getDisartria());
            chbDiplopia.setChecked(adulto_mayor.getDiplopia());
            chbVertigo.setChecked(adulto_mayor.getVertigo());
            Utilidades.seleccionarRadio(rdbDeficit_sensorial,
                    adulto_mayor.getDeficit_sensorial());
            tbxDonde_deficit.setValue(adulto_mayor.getDonde_deficit());
            chbParesias.setChecked(adulto_mayor.getParesias());
            chbParestesias.setChecked(adulto_mayor.getParestesias());
            chbLipotimias.setChecked(adulto_mayor.getLipotimias());
            chbConvulsiones_focales.setChecked(adulto_mayor
                    .getConvulsiones_focales());
            Utilidades.seleccionarRadio(rdbDolor_inferiores,
                    adulto_mayor.getDolor_inferiores());
            seleccion_radio2(rdbDolor_inferiores,
                    new AbstractComponent[]{rdbInferiores_esfuerzo});
            Utilidades.seleccionarRadio(rdbInferiores_esfuerzo,
                    adulto_mayor.getInferiores_esfuerzo());
            chbDiarrea.setChecked(adulto_mayor.getDiarrea());
            chbDolor_abdo.setChecked(adulto_mayor.getDolor_abdo());
            chbMelena.setChecked(adulto_mayor.getMelena());
            chbHematoquexia.setChecked(adulto_mayor.getHematoquexia());
            chbEstrenimiento.setChecked(adulto_mayor.getEstrenimiento());
            chbDeposiciones.setChecked(adulto_mayor.getDeposiciones());
            chbAnorexia.setChecked(adulto_mayor.getAnorexia());
            chbEpigastralgia.setChecked(adulto_mayor.getEpigastralgia());
            chbDispepsia.setChecked(adulto_mayor.getDispepsia());
            chbHematemesis.setChecked(adulto_mayor.getHematemesis());
            chbSinusorragia.setChecked(adulto_mayor.getSinusorragia());
            chbDispareunia.setChecked(adulto_mayor.getDispareunia());
            chbMastodinia.setChecked(adulto_mayor.getMastodinia());
            chbNicturia.setChecked(adulto_mayor.getNicturia());
            chbUrgencia.setChecked(adulto_mayor.getUrgencia());
            Utilidades.seleccionarRadio(rdbOtros, adulto_mayor.getOtros());
            tbxCuales_otros.setValue(adulto_mayor.getCuales_otros());

            dbxSentado
                    .setValue((adulto_mayor.getSentado() != null && !adulto_mayor
                            .getSentado().isEmpty()) ? Double
                            .valueOf(adulto_mayor.getSentado()) : null);

            dbxSentado2
                    .setValue((adulto_mayor.getSentado2() != null && !adulto_mayor
                            .getSentado2().isEmpty()) ? Double
                            .valueOf(adulto_mayor.getSentado2()) : null);

            dbxAcostado
                    .setValue((adulto_mayor.getAcostado() != null && !adulto_mayor
                            .getAcostado().isEmpty()) ? Double
                            .valueOf(adulto_mayor.getAcostado()) : null);

            dbxAcostado2
                    .setValue((adulto_mayor.getAcostado2() != null && !adulto_mayor
                            .getAcostado2().isEmpty()) ? Double
                            .valueOf(adulto_mayor.getAcostado2()) : null);

            dbxPie.setValue((adulto_mayor.getPie() != null && !adulto_mayor
                    .getPie().isEmpty()) ? ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getPie()) : null);

            dbxPie2.setValue((adulto_mayor.getPie2() != null && !adulto_mayor
                    .getPie2().isEmpty()) ? ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getPie2()) : null);

            dbxCardiaca
                    .setValue((adulto_mayor.getCardiaca() != null && !adulto_mayor
                            .getCardiaca().isEmpty()) ? Double
                            .valueOf(adulto_mayor.getCardiaca()) : null);
            dbxPeso.setValue((adulto_mayor.getPeso() != null && !adulto_mayor
                    .getPeso().isEmpty()) ? ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getPeso()) : null);
            dbxTalla.setValue((adulto_mayor.getTalla() != null && !adulto_mayor
                    .getTalla().isEmpty()) ? ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getTalla()) : null);
            dbxImc.setValue((adulto_mayor.getImc() != null && !adulto_mayor
                    .getImc().isEmpty()) ? ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getImc()) : null);

            dbxCir_adbominal.setValue(ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getCir_adbominal()));
            dbxCir_cadera.setValue(ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getCir_cadera()));
            dbxInd_cintura.setValue(ConvertidorDatosUtil
                    .convertirDato(adulto_mayor.getInd_cintura()));

            Utilidades
                    .seleccionarRadio(rdbExc_mama, adulto_mayor.getExc_mama());

            if (adulto_mayor.getExc_mama().equals("1")) {
                lbExam_mama.setVisible(true);
                tbxExam_mama.setVisible(true);
                tbxExam_mama.setValue(adulto_mayor.getExam_mama());
            } else {
                lbExam_mama.setVisible(false);
                tbxExam_mama.setVisible(false);
            }

            Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
                    adulto_mayor.getSintomaticos_respiratorio());
            Utilidades.seleccionarRadio(rdbSintomaticos_piel,
                    adulto_mayor.getSintomaticos_piel());
            tbxHallazgos.setValue(adulto_mayor.getHallazgos());
            tbxTacto_rectal.setValue(adulto_mayor.getTacto_rectal());

            tbxPlan_intervencion.setValue(adulto_mayor.getPlan_intervencion());

            tipo_historia = adulto_mayor.getTipo_historia();

            Utilidades.seleccionarListitem(lbxTamizaje_cuello,
                    adulto_mayor.getTamizaje_cuello());

            if (!tbxAccion.getValue().equals(
                    OpcionesFormulario.MOSTRAR.toString())) {
                infoPacientes.setCodigo_historia(adulto_mayor
                        .getCodigo_historia());
                inicializarVista(tipo_historia);
            }

            cargarImpresionDiagnostica(adulto_mayor);

            // valida_admision = adulto_mayor.getNro_ingreso();
            onMostrarModuloFarmacologico();
            receta_ripsAction.obtenerBotonAgregar().setVisible(false);
            receta_ripsAction.deshabilitarCampos(true);
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(false);

            tbxAccion.setText("modificar");

            // tbxAccion.setText("modificar");
            // accionForm(true, tbxAccion.getText());
            inicializarVista(tipo_historia);

            cargarAnexo9_remision(adulto_mayor);

            btnImprimir.setVisible(true);

            groupboxConsulta.setVisible(false);
            groupboxEditar.setVisible(true);

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Adulto_mayor adulto_mayor = (Adulto_mayor) obj;
        try {
            adulto_mayor.setDelete_user(getUsuarios().getCodigo());
            int result = getServiceLocator().getAdulto_mayorService().eliminar(
                    adulto_mayor);
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

    public void seleccion(Listbox listbox, int entero,
            AbstractComponent[] abstractComponents) {
        try {
            // log.info("" + listbox.getSelectedItem().getValue());

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
            // block
            // log.info("Excepcion loaded");
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
                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");
                        ((Textbox) abstractComponent).setReadonly(false);

                    }

                    if (abstractComponent instanceof Radiogroup) {
                        Radiogroup rg = (Radiogroup) abstractComponent;
                        Utilidades.seleccionarRadio(
                                ((Radiogroup) abstractComponent), "2");
                        for (Radio r : rg.getItems()) {
                            r.setDisabled(false);
                        }
                    }
                } else {

                    if (abstractComponent instanceof Intbox) {
                        ((Intbox) abstractComponent).setValue(null);
                        ((Intbox) abstractComponent).setReadonly(true);

                    }

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");
                        ((Textbox) abstractComponent).setReadonly(true);

                    }

                    if (abstractComponent instanceof Radiogroup) {
                        Radiogroup rg = (Radiogroup) abstractComponent;
                        // Utilidades.seleccionarRadio(((Radiogroup)
                        // abstractComponent),"2");
                        for (Radio r : rg.getItems()) {
                            r.setDisabled(true);
                            r.setChecked(false);
                        }
                    }

                }
            }
        } catch (Exception e) {
            // block
            // System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

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
            // block
            // System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    @Override
    public void initHistoria() throws Exception {

        macroImpresion_diagnostica.inicializarMacro(this, admision,
                IVias_ingreso.ADULTO_MAYOR);
        macroRemision_interna.inicializarMacro(this, admision,
                IVias_ingreso.ADULTO_MAYOR);

        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
            accionForm(OpcionesFormulario.CONSULTAR, null);
            btnCancelar.setVisible(true);
        } else if (admision != null) {
            toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
                    .getNombreCompleto());
            toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
                    .getNombreCompleto());
            macroAgudeza_visual.inicializarMacro(this, admision,
                    IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);

            if (admision.getAtendida()) {
                opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
                Adulto_mayor adulto_mayor = new Adulto_mayor();
                adulto_mayor.setCodigo_empresa(empresa.getCodigo_empresa());
                adulto_mayor.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                adulto_mayor
                        .setIdentificacion(admision.getNro_identificacion());
                adulto_mayor.setNro_ingreso(admision.getNro_ingreso());

                adulto_mayor = getServiceLocator().getAdulto_mayorService()
                        .consultar(adulto_mayor);
                if (adulto_mayor != null) {
                    accionForm(OpcionesFormulario.MOSTRAR, adulto_mayor);
                    infoPacientes.setCodigo_historia(adulto_mayor
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
        if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
            if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
                cobrar_agudeza = true;
                macroAgudeza_visual.validarCamposObligatorios();
                rowAgudeza_visual.setVisible(true);
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

        if (!admision.getAtendida()) {
            Notificaciones
                    .mostrarNotificacionInformacion(
                            "Advertencia",
                            "Recuerde practicar exámen de mama y educacion al paciente",
                            10000);
        }

        infoPacientes.cargarInformacion(admision, this,
                new InformacionPacienteIMG() {

                    @Override
                    public void ejecutarProceso() {

                        Paciente paciente = admision.getPaciente();
                        // log.info("paciente" + paciente);
                        cambiosPorSexo(paciente.getSexo());
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

                            List<Adulto_mayor> listado_historias = getServiceLocator()
                            .getAdulto_mayorService()
                            .listar(parametros);

                            if (!listado_historias.isEmpty()) {
                                inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
                                historia_anterior = listado_historias.get(0);
                                codigo_historia_anterior = historia_anterior
                                .getCodigo_historia();
                                cargarInformacion_historia_anterior(historia_anterior);
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
                                Adulto_mayor adulto_mayor = new Adulto_mayor();
                                adulto_mayor.setCodigo_empresa(empresa
                                        .getCodigo_empresa());
                                adulto_mayor.setCodigo_sucursal(sucursal
                                        .getCodigo_sucursal());
                                adulto_mayor
                                .setCodigo_historia(codigo_historia_anterior);

                                adulto_mayor = getServiceLocator()
                                .getAdulto_mayorService().consultar(
                                        adulto_mayor);

                                if (adulto_mayor != null) {
                                    cargarInformacion_historia_anterior(adulto_mayor);
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
        Adulto_mayor adulto_mayor = (Adulto_mayor) historia_clinica;

        if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
            FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
                    idsExcluyentes);

            if (adulto_mayor.getTipo_historia().equals(
                    IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
                toolbarbuttonTipo_historia
                        .setLabel("Mostrando Historia Clínica por Primera Vez");
            } else {
                toolbarbuttonTipo_historia
                        .setLabel("Mostrando Historia Clínica de Control/Evolucion");
            }
            ((Button) getFellow("btGuardar")).setVisible(false);
        } else {
            if (adulto_mayor.getTipo_historia().equals(
                    IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
                toolbarbuttonTipo_historia
                        .setLabel("Modificando Historia Clínica por Primera Vez");
            } else {
                toolbarbuttonTipo_historia
                        .setLabel("Modificando Historia Clínica de Control/Evolucion");
            }
            ((Button) getFellow("btGuardar")).setVisible(true);
        }

        codigo_historia_anterior = adulto_mayor.getCodigo_historia_anterior();
        tipo_historia = adulto_mayor.getTipo_historia();

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

    public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
        try {
            // UtilidadSignosVitales.onMostrarAlertaTension(TA_sistolica,
            // TA_diastolica);
            UtilidadSignosVitales
                    .onCalcularTension(TA_sistolica, TA_diastolica);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void calcularIcadera(Doublebox dbxCir_adbominal,
            Doublebox dbxCir_cadera, Doublebox dbxInd_cintura) {
        try {
            // UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
            // UtilidadSignosVitales.TALLA_METROS);
            double icc = (dbxCir_adbominal.getValue() / dbxCir_cadera
                    .getValue());
            dbxInd_cintura.setValue(icc);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void seleccion_tabaquismo() {
        if (rdbTabaquismo.getSelectedItem().getValue().equals("2")) {
            rdbActivoPasivo.setVisible(false);
            lbFecha_hasta.setVisible(false);
            dtbxFecha_hasta.setVisible(false);
            lbNo_cigarros.setVisible(false);
            ibxNo_cigarros.setVisible(false);
            ibxNo_cigarros.setValue(0);
        } else {
            rdbActivoPasivo.setVisible(true);
            seleccion_activo_pasivo();
        }
    }

    public void seleccion_activo_pasivo() {
        if (rdbActivoPasivo.getSelectedItem().getValue().equals("2")) {
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

    public void cambiosPorSexo(String sexo) {
        if (sexo.toUpperCase().equals("M")) {
            cellTacto_rectal.setVisible(true);
            rowCitologia.setVisible(false);
            rowCitologia2.setVisible(false);
            rowObstetricos.setVisible(false);
            rowGinecologica.setVisible(false);
        } else {
            cellTacto_rectal.setVisible(false);
            rowCitologia.setVisible(true);
            rowCitologia2.setVisible(true);
            rowObstetricos.setVisible(true);
            rowGinecologica.setVisible(true);
        }
    }

    private void cargarImpresionDiagnostica(Adulto_mayor adulto_mayor)
            throws Exception {
        Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
        impresion_diagnostica.setCodigo_empresa(codigo_empresa);
        impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
        impresion_diagnostica.setCodigo_historia(adulto_mayor
                .getCodigo_historia());
        impresion_diagnostica = getServiceLocator().getServicio(
                Impresion_diagnosticaService.class).consultar(
                        impresion_diagnostica);
        macroImpresion_diagnostica.mostrarImpresionDiagnostica(
                impresion_diagnostica, true);
    }

    private void cargarAgudezaVisual(Adulto_mayor adulto_mayor)
            throws Exception {
        Agudeza_visual agudeza_visual = new Agudeza_visual();
        agudeza_visual.setCodigo_historia(adulto_mayor.getCodigo_historia());
        agudeza_visual.setCodigo_empresa(adulto_mayor.getCodigo_empresa());
        agudeza_visual.setCodigo_sucursal(adulto_mayor.getCodigo_sucursal());

        agudeza_visual = getServiceLocator().getServicio(
                Agudeza_visualService.class).consultar(agudeza_visual);

        macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
    }

    private void cargarRemisionInterna(Adulto_mayor adulto_mayor)
            throws Exception {
        Remision_interna remision_interna = new Remision_interna();
        remision_interna.setCodigo_historia(adulto_mayor.getCodigo_historia());
        remision_interna.setCodigo_empresa(adulto_mayor.getCodigo_empresa());
        remision_interna.setCodigo_sucursal(adulto_mayor.getCodigo_sucursal());

        remision_interna = getServiceLocator().getServicio(
                Remision_internaService.class).consultar(remision_interna);

        macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
    }

    private void cargarAnexo9_remision(Adulto_mayor adulto_mayor) {
        Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
        anexo9_entidad.setCodigo_empresa(codigo_empresa);
        anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
        anexo9_entidad.setNro_historia(adulto_mayor.getCodigo_historia());
        anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
        anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
                .consultar(anexo9_entidad);
        remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
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
                .append("'" + tbxEnfremedad_actual.getValue() + "'")
                .append("\n");

        informacion_clinica.append("\n");

        informacion_clinica.append("- SIGNOS VITALES\n");
        informacion_clinica.append("\tFrecuencia cardiaca: ")
                .append(dbxCardiaca.getText()).append("\n");
        informacion_clinica.append("\tPeso(kg): ").append(dbxPeso.getText())
                .append("\t");
        informacion_clinica.append("\tTalla: ").append(dbxTalla.getText())
                .append("\t");
        informacion_clinica.append("\tIMC: ").append(dbxImc.getText())
                .append("\t");
        informacion_clinica.append("\tCircunferencia Abdominal (cm): ")
                .append(dbxCir_adbominal.getText()).append("\t");
        informacion_clinica.append("\tCircunferencia de Cadera (cm): ")
                .append(dbxCir_cadera.getText()).append("\t");
        informacion_clinica.append("\tInd. Cintura/Cadera: ")
                .append(dbxInd_cintura.getText()).append("\n").append("\n");

        informacion_clinica.append("- EXAMEN FISICO").append("\n");
        informacion_clinica.append("\tEstado general: ").append("-----")
                .append("\n");
        informacion_clinica.append("\tAnalisis y recomendaciones: ");
        informacion_clinica.append("-----").append("\n");

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

    public void generarContendioAntecedentesFamiliares(
            Bandbox bandbox_antecedente, Integer sexo) {
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
        bandpopup.setWidth("170px");
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

        final Checkbox checkbox_padre = new Checkbox("Padre");
        checkbox_padre.setChecked(mapa_contenido.containsKey("padre"));

        final Checkbox checkbox_hermano = new Checkbox("Hermano");
        checkbox_hermano.setChecked(mapa_contenido.containsKey("hermano"));

        final Spinner spinner_hermano = new Spinner();
        spinner_hermano.setConstraint("no empty,min 1");
        spinner_hermano.setVisible(mapa_contenido.containsKey("hermano"));
        spinner_hermano
                .setValue((mapa_contenido.get("hermano") != null ? (Integer) mapa_contenido
                        .get("hermano") : 1));
        spinner_hermano.setWidth("70px");

        final Checkbox checkbox_abuelo_paterno = new Checkbox(
                "Abuelo (Paterno)");
        checkbox_abuelo_paterno.setChecked(mapa_contenido
                .containsKey("abuelo_paterno"));

        final Checkbox checkbox_abuelo_materno = new Checkbox(
                "Abuelo (Materno)");
        checkbox_abuelo_materno.setChecked(mapa_contenido
                .containsKey("abuelo_materno"));

        final Checkbox checkbox_madre = new Checkbox("Madre");
        checkbox_madre.setChecked(mapa_contenido.containsKey("madre"));

        final Checkbox checkbox_hermana = new Checkbox("Hermana");
        checkbox_hermana.setChecked(mapa_contenido.containsKey("hermana"));

        final Spinner spinner_hermana = new Spinner();
        spinner_hermana.setConstraint("no empty,min 1");
        spinner_hermana.setVisible(mapa_contenido.containsKey("hermana"));
        spinner_hermana
                .setValue((mapa_contenido.get("hermana") != null ? (Integer) mapa_contenido
                        .get("hermana") : 1));
        spinner_hermana.setWidth("70px");

        final Checkbox checkbox_abuela_paterna = new Checkbox(
                "Abuela (Paterna)");
        checkbox_abuela_paterna.setChecked(mapa_contenido
                .containsKey("abuela_paterna"));

        final Checkbox checkbox_abuela_materna = new Checkbox(
                "Abuela (Materna)");
        checkbox_abuela_materna.setChecked(mapa_contenido
                .containsKey("abuela_materna"));

        Row fila = new Row();
        Hlayout hlayout = new Hlayout();
        if (sexo == 0) {
            hlayout.appendChild(checkbox_padre);
        } else {
            hlayout.appendChild(checkbox_madre);
        }
        fila.appendChild(hlayout);
        filas.appendChild(fila);

        fila = new Row();
        hlayout = new Hlayout();
        Space space = new Space();
        space.setWidth("7px");
        if (sexo == 0) {
            hlayout.appendChild(checkbox_hermano);
            hlayout.appendChild(space);
            hlayout.appendChild(spinner_hermano);
        } else {
            hlayout.appendChild(checkbox_hermana);
            hlayout.appendChild(space);
            hlayout.appendChild(spinner_hermana);
        }
        hlayout.appendChild(new Space());
        fila.appendChild(hlayout);
        filas.appendChild(fila);

        fila = new Row();
        hlayout = new Hlayout();

        if (sexo == 0) {
            hlayout.appendChild(checkbox_abuelo_paterno);
        } else {
            hlayout.appendChild(checkbox_abuela_paterna);
        }
        fila.appendChild(hlayout);
        filas.appendChild(fila);

        fila = new Row();
        hlayout = new Hlayout();
        if (sexo == 0) {
            hlayout.appendChild(checkbox_abuelo_materno);
        } else {
            hlayout.appendChild(checkbox_abuela_materna);
        }
        fila.appendChild(hlayout);
        filas.appendChild(fila);

        final Bandbox bandbox = bandbox_antecedente;

        EventListener<Event> eventListener = new EventListener<Event>() {

            @Override
            public void onEvent(Event event) throws Exception {
                if (event.getTarget() != null) {
                    if (checkbox_padre.equals(event.getTarget())) {
                        if (checkbox_padre.isChecked()) {
                            mapa_contenido.put("padre", 1);
                        } else {
                            mapa_contenido.remove("padre");
                        }
                    } else if (checkbox_madre.equals(event.getTarget())) {
                        if (checkbox_madre.isChecked()) {
                            mapa_contenido.put("madre", 1);
                        } else {
                            mapa_contenido.remove("madre");
                        }
                    } else if (checkbox_hermano.equals(event.getTarget())) {
                        spinner_hermano
                                .setVisible(checkbox_hermano.isChecked());
                        if (checkbox_hermano.isChecked()) {
                            mapa_contenido.put("hermano", 1);
                        } else {
                            mapa_contenido.remove("hermano");
                        }
                    } else if (checkbox_hermana.equals(event.getTarget())) {
                        spinner_hermana
                                .setVisible(checkbox_hermana.isChecked());
                        if (checkbox_hermana.isChecked()) {
                            mapa_contenido.put("hermana", 1);
                        } else {
                            mapa_contenido.remove("hermana");
                        }
                    } else if (checkbox_abuelo_paterno
                            .equals(event.getTarget())) {
                        if (checkbox_abuelo_paterno.isChecked()) {
                            mapa_contenido.put("abuelo_paterno", 1);
                        } else {
                            mapa_contenido.remove("abuelo_paterno");
                        }
                    } else if (checkbox_abuelo_materno
                            .equals(event.getTarget())) {
                        if (checkbox_abuelo_materno.isChecked()) {
                            mapa_contenido.put("abuelo_materno", 1);
                        } else {
                            mapa_contenido.remove("abuelo_materno");
                        }
                    } else if (checkbox_abuela_paterna
                            .equals(event.getTarget())) {
                        if (checkbox_abuela_paterna.isChecked()) {
                            mapa_contenido.put("abuela_paterna", 1);
                        } else {
                            mapa_contenido.remove("abuela_paterna");
                        }
                    } else if (checkbox_abuela_materna
                            .equals(event.getTarget())) {
                        if (checkbox_abuela_materna.isChecked()) {
                            mapa_contenido.put("abuela_materna", 1);
                        } else {
                            mapa_contenido.remove("abuela_materna");
                        }
                    } else if (spinner_hermano.equals(event.getTarget())) {
                        mapa_contenido.put("hermano",
                                spinner_hermano.getValue());
                    } else if (spinner_hermana.equals(event.getTarget())) {
                        mapa_contenido.put("hermana",
                                spinner_hermana.getValue());
                    }
                    mostrarXmlTextbox(bandbox, mapa_contenido);
                }
            }
        };

        checkbox_abuela_materna
                .addEventListener(Events.ON_CHECK, eventListener);
        checkbox_abuela_paterna
                .addEventListener(Events.ON_CHECK, eventListener);
        checkbox_abuelo_materno
                .addEventListener(Events.ON_CHECK, eventListener);
        checkbox_abuelo_paterno
                .addEventListener(Events.ON_CHECK, eventListener);
        checkbox_hermano.addEventListener(Events.ON_CHECK, eventListener);
        checkbox_hermana.addEventListener(Events.ON_CHECK, eventListener);
        spinner_hermano.addEventListener(Events.ON_CHANGE, eventListener);
        spinner_hermana.addEventListener(Events.ON_CHANGE, eventListener);
        checkbox_madre.addEventListener(Events.ON_CHECK, eventListener);
        checkbox_padre.addEventListener(Events.ON_CHECK, eventListener);
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

            dbxSentado.setValue(enfermera_signos.getSentado_bd1());
            dbxSentado2.setValue(enfermera_signos.getSentado_bd2());
            dbxAcostado.setValue(enfermera_signos.getDe_cubito1());
            dbxAcostado2.setValue(enfermera_signos.getDe_cubito2());
            dbxPie.setValue(enfermera_signos.getDe_pie1());
            dbxPie2.setValue(enfermera_signos.getDe_pie2());

            dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
            dbxPeso.setValue(enfermera_signos.getPeso());
            dbxTalla.setValue(enfermera_signos.getTalla());
            dbxImc.setValue(enfermera_signos.getImc());

            dbxCir_adbominal.setValue(enfermera_signos
                    .getCircunferencia_abdominal());
            dbxCir_cadera.setValue(enfermera_signos.getCincunferencia_cadera());
            dbxInd_cintura.setValue(enfermera_signos.getInd_cintura_cadera());

            calcularTA(dbxSentado, dbxSentado2);
            calcularTA(dbxAcostado, dbxAcostado2);
            calcularTA(dbxPie, dbxPie2);
            alarmaExamenFisicoFc();
            calcularIMC(dbxPeso, dbxTalla, dbxImc);
            calcularIcadera(dbxCir_adbominal, dbxCir_cadera, dbxInd_cintura);

        }
    }

    /*
     * public void imprimir() throws Exception { String nro_historia =
     * infoPacientes.getCodigo_historia(); if (nro_historia.equals("")) {
     * Messagebox.show("La historia no se ha guardado aún", "Informacion ..",
     * Messagebox.OK, Messagebox.INFORMATION); return; }
     * 
     * Map paramRequest = new HashMap(); paramRequest.put("name_report",
     * "Historia_pyp_adulto_mayor"); paramRequest.put("nro_historia",
     * nro_historia); paramRequest.put("formato",
     * lbxFormato.getSelectedItem().getValue() .toString());
     * 
     * paramRequest.put("sexo", admision.getPaciente().getSexo());
     * 
     * Component componente = Executions.createComponents(
     * "/pages/printInformes.zul", this, paramRequest); final Window window =
     * (Window) componente; window.setMode("modal");
     * window.setMaximizable(true); window.setMaximized(true);
     * 
     * // Clients.evalJavaScript("window.open('"+request.getContextPath()+
     * "/pages/printInformes.zul"+parametros_pagina+"', '_blank');"); }
     */
    public void imprimir() throws Exception {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
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
                    + "/pages/reports/adulto_mayor_reporte.zul"
                    + parametros_pagina + "\", '_blank');");
        }
    }
}
