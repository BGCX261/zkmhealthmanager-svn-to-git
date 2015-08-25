/*
 * control_prenatalAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Control_prenatal;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
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
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.Util;
import healthmanager.modelo.service.GeneralExtraService;

public class Control_prenatalAction extends Borderlayout implements
        AfterCompose {

    private static Logger log = Logger.getLogger(Control_prenatalAction.class);
    private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;
    private Empresa empresa;
    private Sucursal sucursal;
    private Usuarios usuarios;

    private Borderlayout form;

    // Componentes //
    private Listbox lbxParameter;
    private Textbox tbxValue;
    private Textbox tbxAccion;
    private Groupbox groupboxEditar;
    private Groupbox groupboxConsulta;
    private Rows rowsResultado;
    private Grid gridResultado;

    private Textbox tbxCodigo_historia;
    private Datebox dtbxFecha_inicial;
    private Textbox tbxCodigo_eps;
    private Textbox tbxNombre_eps;
    private Listbox lbxCodigo_dpto;
    private Listbox lbxCodigo_municipio;
    private Textbox tbxContrato;
    private Textbox tbxCodigo_contrato;

    private Bandbox tbxIdentificacion;
    private Textbox tbxNomPaciente;
    private Textbox tbxTipoIdentificacion;
    private Datebox dbxNacimiento;
    private Textbox tbxEdad;
    private Textbox tbxSexo;
    private Textbox tbxDireccion;

    private Radiogroup rdbSintomatico_piel;
    private Radiogroup rdbSintomatico_resp;
    private Textbox tbxMotivo;
    private Textbox tbxEnfermedad_actual;
    private Textbox tbxAntecedentes_descritos;
    private Textbox tbxOtros;
    private Listbox lbxConciencia;
    private Textbox tbxObservacion_conciencia;
    private Listbox lbxPiel;
    private Textbox tbxObservacion_piel;
    private Listbox lbxCuello;
    private Textbox tbxObservacion_cuello;
    private Listbox lbxSentidos;
    private Textbox tbxObservacion_sentidos;
    private Listbox lbxTorax;
    private Textbox tbxObservacion_torax;
    private Listbox lbxRespiratorio;
    private Textbox tbxObservacion_respiratorio;
    private Listbox lbxCardiovascular;
    private Textbox tbxObservacion_cardiovascular;
    private Listbox lbxVascular;
    private Textbox tbxObservacion_vascular;
    private Listbox lbxAbdomen;
    private Textbox tbxObservacion_abdomen;
    private Listbox lbxPerianal;
    private Textbox tbxObservacion_perianal;
    private Listbox lbxInguinal;
    private Textbox tbxObservacion_inguinal;
    private Listbox lbxGenitales;
    private Textbox tbxObservacion_genitales;
    private Listbox lbxExtremidades;
    private Textbox tbxObservacion_extremidades;
    private Listbox lbxNeurologico;
    private Textbox tbxObservacion_neurologico;
    private Listbox lbxPeriferico;
    private Textbox tbxObservacion_periferico;
    private Listbox lbxLinfatico;
    private Textbox tbxObservacion_linfatico;
    private Listbox lbxOsteomuscular;
    private Textbox tbxObservacion_osteomuscular;
    private Listbox lbxEndocrino;
    private Textbox tbxObservacion_endocrino;
    private Listbox lbxGastrointestinal;
    private Textbox tbxObservacion_gastrointestinal;
    private Listbox lbxGenitourinario;
    private Textbox tbxObservacion_genitourinario;
    private Listbox lbxLocomotor;
    private Textbox tbxObservacion_locomotor;
    private Textbox tbxEstado_general;
    private Textbox tbxCardiaca;
    private Textbox tbxRespiratoria;
    private Textbox tbxPeso;
    private Textbox tbxTalla;
    private Textbox tbxTempo;
    private Textbox tbxPresion;
    private Textbox tbxPresion2;
    private Textbox tbxInd_masa;
    private Textbox tbxSus_masa;
    private Listbox lbxApariencia;
    private Textbox tbxObservacion_apariencia;
    private Listbox lbxCabeza;
    private Textbox tbxObservacion_cabeza;
    private Listbox lbxExam_torax;
    private Textbox tbxObservacion_exam_torax;
    private Listbox lbxExam_cardio;
    private Textbox tbxObservacion_exam_cardio;
    private Listbox lbxExam_abdomen;
    private Textbox tbxObservacion_exam_abdomen;
    private Listbox lbxExam_pies;
    private Textbox tbxObservacion_exam_pies;
    private Listbox lbxExam_genital;
    private Textbox tbxObservacion_exam_genital;
    private Listbox lbxExam_cuello;
    private Textbox tbxObservacion_exam_cuello;
    private Listbox lbxExam_extremidades;
    private Textbox tbxObservacion_exam_extremidades;
    private Listbox lbxBoca;
    private Textbox tbxObservacion_boca;
    private Listbox lbxSenos;
    private Textbox tbxObservacion_senos;
    private Listbox lbxExam_osteomuscular;
    private Textbox tbxObservacion_exam_osteomuscular;
    private Listbox lbxExam_neuro;
    private Textbox tbxObservacion_exam_neuro;
    private Listbox lbxExam_sentidos;
    private Textbox tbxObservacion_exam_sentidos;
    private Textbox tbxAnalisis_paraclinico;
    private Textbox tbxAnalisis_ganancia;
    private Textbox tbxAnalisis_crecimiento;
    private Textbox tbxAnalisis_presion;
    private Textbox tbxSulfato;
    private Textbox tbxFoloco;
    private Textbox tbxCalcio;
    private Textbox tbxParaclinico;
    private Textbox tbxEducacion_antes;
    private Textbox tbxSignos;
    private Textbox tbxConsejeria;
    private Textbox tbxEducacion_iami;
    private Textbox tbxEntrega_educacion;
    private Listbox lbxCodigo_consulta_pyp;
    private Listbox lbxFinalidad_cons;
    private Listbox lbxCausas_externas;
    private Listbox lbxTipo_disnostico;
    private Textbox tbxTratamiento;

    private Bandbox tbxTipo_principal;
    private Textbox tbxNomDx;
    // private Textbox tbxPlan;

    private Bandbox tbxTipo_relacionado_1;
    private Bandbox tbxTipo_relacionado_2;
    private Bandbox tbxTipo_relacionado_3;

    private Textbox tbxNomDxRelacionado_1;
    private Textbox tbxNomDxRelacionado_2;
    private Textbox tbxNomDxRelacionado_3;

    @Override
    public void afterCompose() {
        loadComponents();
        cargarDatosSesion();
        listarCombos();
    }

    public void loadComponents() {
        form = (Borderlayout) this.getFellow("formControl_prenatal");

        lbxParameter = (Listbox) form.getFellow("lbxParameter");
        tbxValue = (Textbox) form.getFellow("tbxValue");
        tbxAccion = (Textbox) form.getFellow("tbxAccion");
        groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
        groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
        rowsResultado = (Rows) form.getFellow("rowsResultado");
        gridResultado = (Grid) form.getFellow("gridResultado");

        tbxCodigo_historia = (Textbox) form.getFellow("tbxCodigo_historia");
        dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
        tbxCodigo_eps = (Textbox) form.getFellow("tbxCodigo_eps");
        tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");
        lbxCodigo_dpto = (Listbox) form.getFellow("lbxCodigo_dpto");
        lbxCodigo_municipio = (Listbox) form.getFellow("lbxCodigo_municipio");
        tbxContrato = (Textbox) form.getFellow("tbxContrato");
        tbxCodigo_contrato = (Textbox) form.getFellow("tbxCodigo_contrato");

        tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
        tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
        tbxTipoIdentificacion = (Textbox) form
                .getFellow("tbxTipoIdentificacion");
        tbxEdad = (Textbox) form.getFellow("tbxEdad");
        tbxSexo = (Textbox) form.getFellow("tbxSexo");
        dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");
        tbxDireccion = (Textbox) form.getFellow("tbxDireccion");

        rdbSintomatico_piel = (Radiogroup) form
                .getFellow("rdbSintomatico_piel");
        rdbSintomatico_resp = (Radiogroup) form
                .getFellow("rdbSintomatico_resp");
        tbxMotivo = (Textbox) form.getFellow("tbxMotivo");
        tbxEnfermedad_actual = (Textbox) form.getFellow("tbxEnfermedad_actual");
        tbxAntecedentes_descritos = (Textbox) form
                .getFellow("tbxAntecedentes_descritos");
        tbxOtros = (Textbox) form.getFellow("tbxOtros");
        lbxConciencia = (Listbox) form.getFellow("lbxConciencia");
        tbxObservacion_conciencia = (Textbox) form
                .getFellow("tbxObservacion_conciencia");
        lbxPiel = (Listbox) form.getFellow("lbxPiel");
        tbxObservacion_piel = (Textbox) form.getFellow("tbxObservacion_piel");
        lbxCuello = (Listbox) form.getFellow("lbxCuello");
        tbxObservacion_cuello = (Textbox) form
                .getFellow("tbxObservacion_cuello");
        lbxSentidos = (Listbox) form.getFellow("lbxSentidos");
        tbxObservacion_sentidos = (Textbox) form
                .getFellow("tbxObservacion_sentidos");
        lbxTorax = (Listbox) form.getFellow("lbxTorax");
        tbxObservacion_torax = (Textbox) form.getFellow("tbxObservacion_torax");
        lbxRespiratorio = (Listbox) form.getFellow("lbxRespiratorio");
        tbxObservacion_respiratorio = (Textbox) form
                .getFellow("tbxObservacion_respiratorio");
        lbxCardiovascular = (Listbox) form.getFellow("lbxCardiovascular");
        tbxObservacion_cardiovascular = (Textbox) form
                .getFellow("tbxObservacion_cardiovascular");
        lbxVascular = (Listbox) form.getFellow("lbxVascular");
        tbxObservacion_vascular = (Textbox) form
                .getFellow("tbxObservacion_vascular");
        lbxAbdomen = (Listbox) form.getFellow("lbxAbdomen");
        tbxObservacion_abdomen = (Textbox) form
                .getFellow("tbxObservacion_abdomen");
        lbxPerianal = (Listbox) form.getFellow("lbxPerianal");
        tbxObservacion_perianal = (Textbox) form
                .getFellow("tbxObservacion_perianal");
        lbxInguinal = (Listbox) form.getFellow("lbxInguinal");
        tbxObservacion_inguinal = (Textbox) form
                .getFellow("tbxObservacion_inguinal");
        lbxGenitales = (Listbox) form.getFellow("lbxGenitales");
        tbxObservacion_genitales = (Textbox) form
                .getFellow("tbxObservacion_genitales");
        lbxExtremidades = (Listbox) form.getFellow("lbxExtremidades");
        tbxObservacion_extremidades = (Textbox) form
                .getFellow("tbxObservacion_extremidades");
        lbxNeurologico = (Listbox) form.getFellow("lbxNeurologico");
        tbxObservacion_neurologico = (Textbox) form
                .getFellow("tbxObservacion_neurologico");
        lbxPeriferico = (Listbox) form.getFellow("lbxPeriferico");
        tbxObservacion_periferico = (Textbox) form
                .getFellow("tbxObservacion_periferico");
        lbxLinfatico = (Listbox) form.getFellow("lbxLinfatico");
        tbxObservacion_linfatico = (Textbox) form
                .getFellow("tbxObservacion_linfatico");
        lbxOsteomuscular = (Listbox) form.getFellow("lbxOsteomuscular");
        tbxObservacion_osteomuscular = (Textbox) form
                .getFellow("tbxObservacion_osteomuscular");
        lbxEndocrino = (Listbox) form.getFellow("lbxEndocrino");
        tbxObservacion_endocrino = (Textbox) form
                .getFellow("tbxObservacion_endocrino");
        lbxGastrointestinal = (Listbox) form.getFellow("lbxGastrointestinal");
        tbxObservacion_gastrointestinal = (Textbox) form
                .getFellow("tbxObservacion_gastrointestinal");
        lbxGenitourinario = (Listbox) form.getFellow("lbxGenitourinario");
        tbxObservacion_genitourinario = (Textbox) form
                .getFellow("tbxObservacion_genitourinario");
        lbxLocomotor = (Listbox) form.getFellow("lbxLocomotor");
        tbxObservacion_locomotor = (Textbox) form
                .getFellow("tbxObservacion_locomotor");
        tbxEstado_general = (Textbox) form.getFellow("tbxEstado_general");
        tbxCardiaca = (Textbox) form.getFellow("tbxCardiaca");
        tbxRespiratoria = (Textbox) form.getFellow("tbxRespiratoria");
        tbxPeso = (Textbox) form.getFellow("tbxPeso");
        tbxTalla = (Textbox) form.getFellow("tbxTalla");
        tbxTempo = (Textbox) form.getFellow("tbxTempo");
        tbxPresion = (Textbox) form.getFellow("tbxPresion");
        tbxPresion2 = (Textbox) form.getFellow("tbxPresion2");
        tbxInd_masa = (Textbox) form.getFellow("tbxInd_masa");
        tbxSus_masa = (Textbox) form.getFellow("tbxSus_masa");
        lbxApariencia = (Listbox) form.getFellow("lbxApariencia");
        tbxObservacion_apariencia = (Textbox) form
                .getFellow("tbxObservacion_apariencia");
        lbxCabeza = (Listbox) form.getFellow("lbxCabeza");
        tbxObservacion_cabeza = (Textbox) form
                .getFellow("tbxObservacion_cabeza");
        lbxExam_torax = (Listbox) form.getFellow("lbxExam_torax");
        tbxObservacion_exam_torax = (Textbox) form
                .getFellow("tbxObservacion_exam_torax");
        lbxExam_cardio = (Listbox) form.getFellow("lbxExam_cardio");
        tbxObservacion_exam_cardio = (Textbox) form
                .getFellow("tbxObservacion_exam_cardio");
        lbxExam_abdomen = (Listbox) form.getFellow("lbxExam_abdomen");
        tbxObservacion_exam_abdomen = (Textbox) form
                .getFellow("tbxObservacion_exam_abdomen");
        lbxExam_pies = (Listbox) form.getFellow("lbxExam_pies");
        tbxObservacion_exam_pies = (Textbox) form
                .getFellow("tbxObservacion_exam_pies");
        lbxExam_genital = (Listbox) form.getFellow("lbxExam_genital");
        tbxObservacion_exam_genital = (Textbox) form
                .getFellow("tbxObservacion_exam_genital");
        lbxExam_cuello = (Listbox) form.getFellow("lbxExam_cuello");
        tbxObservacion_exam_cuello = (Textbox) form
                .getFellow("tbxObservacion_exam_cuello");
        lbxExam_extremidades = (Listbox) form.getFellow("lbxExam_extremidades");
        tbxObservacion_exam_extremidades = (Textbox) form
                .getFellow("tbxObservacion_exam_extremidades");
        lbxBoca = (Listbox) form.getFellow("lbxBoca");
        tbxObservacion_boca = (Textbox) form.getFellow("tbxObservacion_boca");
        lbxSenos = (Listbox) form.getFellow("lbxSenos");
        tbxObservacion_senos = (Textbox) form.getFellow("tbxObservacion_senos");
        lbxExam_osteomuscular = (Listbox) form
                .getFellow("lbxExam_osteomuscular");
        tbxObservacion_exam_osteomuscular = (Textbox) form
                .getFellow("tbxObservacion_exam_osteomuscular");
        lbxExam_neuro = (Listbox) form.getFellow("lbxExam_neuro");
        tbxObservacion_exam_neuro = (Textbox) form
                .getFellow("tbxObservacion_exam_neuro");
        lbxExam_sentidos = (Listbox) form.getFellow("lbxExam_sentidos");
        tbxObservacion_exam_sentidos = (Textbox) form
                .getFellow("tbxObservacion_exam_sentidos");
        tbxAnalisis_paraclinico = (Textbox) form
                .getFellow("tbxAnalisis_paraclinico");
        tbxAnalisis_ganancia = (Textbox) form.getFellow("tbxAnalisis_ganancia");
        tbxAnalisis_crecimiento = (Textbox) form
                .getFellow("tbxAnalisis_crecimiento");
        tbxAnalisis_presion = (Textbox) form.getFellow("tbxAnalisis_presion");
        tbxSulfato = (Textbox) form.getFellow("tbxSulfato");
        tbxFoloco = (Textbox) form.getFellow("tbxFoloco");
        tbxCalcio = (Textbox) form.getFellow("tbxCalcio");
        tbxParaclinico = (Textbox) form.getFellow("tbxParaclinico");
        tbxEducacion_antes = (Textbox) form.getFellow("tbxEducacion_antes");
        tbxSignos = (Textbox) form.getFellow("tbxSignos");
        tbxConsejeria = (Textbox) form.getFellow("tbxConsejeria");
        tbxEducacion_iami = (Textbox) form.getFellow("tbxEducacion_iami");
        tbxEntrega_educacion = (Textbox) form.getFellow("tbxEntrega_educacion");
        lbxCodigo_consulta_pyp = (Listbox) form
                .getFellow("lbxCodigo_consulta_pyp");
        lbxFinalidad_cons = (Listbox) form.getFellow("lbxFinalidad_cons");
        lbxCausas_externas = (Listbox) form.getFellow("lbxCausas_externas");
        lbxTipo_disnostico = (Listbox) form.getFellow("lbxTipo_disnostico");
        tbxTipo_principal = (Bandbox) form.getFellow("tbxTipo_principal");
        tbxNomDx = (Textbox) form.getFellow("tbxNomDx");
        tbxTipo_relacionado_1 = (Bandbox) form
                .getFellow("tbxTipo_relacionado_1");
        tbxTipo_relacionado_2 = (Bandbox) form
                .getFellow("tbxTipo_relacionado_2");
        tbxTipo_relacionado_3 = (Bandbox) form
                .getFellow("tbxTipo_relacionado_3");
        tbxNomDxRelacionado_1 = (Textbox) form
                .getFellow("tbxNomDxRelacionado_1");
        tbxNomDxRelacionado_2 = (Textbox) form
                .getFellow("tbxNomDxRelacionado_2");
        tbxNomDxRelacionado_3 = (Textbox) form
                .getFellow("tbxNomDxRelacionado_3");

        tbxTratamiento = (Textbox) form.getFellow("tbxTratamiento");

    }

    public void initControl_prenatal() throws Exception {
//		HttpServletRequest request = (HttpServletRequest) Executions
//				.getCurrent().getNativeRequest();
        try {
            accionForm(true, "registrar");

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
                    Messagebox.ERROR);
        }

    }

    public void cargarDatosSesion() {
        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();

        empresa = ServiceLocatorWeb.getEmpresa(request);
        sucursal = ServiceLocatorWeb.getSucursal(request);
        usuarios = ServiceLocatorWeb.getUsuarios(request);
    }

    public void listarCombos() {
        listarParameter();
        listarDepartamentos(lbxCodigo_dpto, true);
        listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
        listarElementoListbox(lbxConciencia, false);
        listarElementoListbox(lbxPiel, false);
        listarElementoListbox(lbxCuello, false);
        listarElementoListbox(lbxSentidos, false);
        listarElementoListbox(lbxTorax, false);
        listarElementoListbox(lbxRespiratorio, false);
        listarElementoListbox(lbxCardiovascular, false);
        listarElementoListbox(lbxVascular, false);
        listarElementoListbox(lbxAbdomen, false);
        listarElementoListbox(lbxPerianal, false);
        listarElementoListbox(lbxInguinal, false);
        listarElementoListbox(lbxGenitales, false);
        listarElementoListbox(lbxExtremidades, false);
        listarElementoListbox(lbxNeurologico, false);
        listarElementoListbox(lbxPeriferico, false);
        listarElementoListbox(lbxLinfatico, false);
        listarElementoListbox(lbxOsteomuscular, false);
        listarElementoListbox(lbxEndocrino, false);
        listarElementoListbox(lbxGastrointestinal, false);
        listarElementoListbox(lbxGenitourinario, false);
        listarElementoListbox(lbxLocomotor, false);
        listarElementoListbox(lbxApariencia, false);
        listarElementoListbox(lbxCabeza, false);
        listarElementoListbox(lbxExam_torax, false);
        listarElementoListbox(lbxExam_cardio, false);
        listarElementoListbox(lbxExam_abdomen, false);
        listarElementoListbox(lbxExam_pies, false);
        listarElementoListbox(lbxExam_genital, false);
        listarElementoListbox(lbxExam_cuello, false);
        listarElementoListbox(lbxExam_extremidades, false);
        listarElementoListbox(lbxBoca, false);
        listarElementoListbox(lbxSenos, false);
        listarElementoListbox(lbxExam_osteomuscular, false);
        listarElementoListbox(lbxExam_neuro, false);
        listarElementoListbox(lbxExam_sentidos, false);

        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar("ante_familiares");
        /* tipos de diasnosticos */
        elementos = this.getServiceLocator().getElementoService()
                .listar("tipo_diagnostico");
        listarElementoListboxFromType(lbxTipo_disnostico, true, elementos,
                false);

        /* listar finalidad */
        elementos = this.getServiceLocator().getElementoService()
                .listar("finalidad_cons");
        listarElementoListboxFromType(lbxFinalidad_cons, false, elementos,
                false);

        /* causas externas */
        elementos = this.getServiceLocator().getElementoService()
                .listar("causa_externa");
        listarElementoListboxFromType(lbxCausas_externas, true, elementos,
                false);

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

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        groupboxConsulta.setVisible(!sw);
        groupboxEditar.setVisible(sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            buscarDatos();
        } else {
            // buscarDatos();
            limpiarDatos();
        }
        tbxAccion.setValue(accion);
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
        Collection<Component> collection = groupboxEditar.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
                    ((Textbox) abstractComponent).setValue("");
                }
            }
            if (abstractComponent instanceof Listbox) {
                if (!((Listbox) abstractComponent).getId().equals(
                        "lbxParameter")) {
                    if (((Listbox) abstractComponent).getItemCount() > 0) {
                        ((Listbox) abstractComponent).setSelectedIndex(0);
                    }
                }
            }
            if (abstractComponent instanceof Doublebox) {
                ((Doublebox) abstractComponent).setText("0.00");
            }
            if (abstractComponent instanceof Intbox) {
                ((Intbox) abstractComponent).setText("");
            }
            if (abstractComponent instanceof Datebox) {
                ((Datebox) abstractComponent).setValue(new Date());
            }
            if (abstractComponent instanceof Checkbox) {
                ((Checkbox) abstractComponent).setChecked(false);
            }
            if (abstractComponent instanceof Radiogroup) {
                ((Radio) ((Radiogroup) abstractComponent).getFirstChild())
                        .setChecked(true);
            }
        }

    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxIdentificacion
                .setStyle("text-transform:uppercase;background-color:white");
        lbxTipo_disnostico
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCodigo_consulta_pyp
                .setStyle("text-transform:uppercase;background-color:white");

        String mensaje = "Los campos marcados con (*) son obligatorios";

        boolean valida = true;

        if (tbxIdentificacion.getText().trim().equals("")) {
            tbxIdentificacion
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (lbxTipo_disnostico.getSelectedIndex() == 0) {
            lbxTipo_disnostico
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (!lbxFinalidad_cons.getSelectedItem().getValue().toString()
                .equalsIgnoreCase("10")
                && lbxCodigo_consulta_pyp.getSelectedIndex() == 0) {
            lbxCodigo_consulta_pyp
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (tbxTipo_principal.getText().trim().equals("")) {
            mensaje = "Debe digitar la impresion diagnostica";
            valida = false;
        } else if (vaidarIgualdad(tbxTipo_principal.getText(),
                tbxTipo_relacionado_1.getText(),
                tbxTipo_relacionado_2.getText(),
                tbxTipo_relacionado_3.getText())) {
            mensaje = "no se puede repetir la impresion diagnostica";
            valida = false;
        }

        if (!valida) {
            Messagebox.show(mensaje,
                    usuarios.getNombres() + " recuerde que...", Messagebox.OK,
                    Messagebox.EXCLAMATION);
        }

        return valida;
    }

    private boolean vaidarIgualdad(String... in) {
        Map map = new HashMap();
        for (String inO : in) {
            if (!inO.trim().isEmpty()) {
                if (map.containsKey(inO)) {
                    return true;
                }
                map.put(inO, inO);
            }
        }
        return false;
    }

    // Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String codigo_empresa = empresa.getCodigo_empresa();
            String codigo_sucursal = sucursal.getCodigo_sucursal();
            String parameter = lbxParameter.getSelectedItem().getValue()
                    .toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            getServiceLocator().getControl_prenatalService().setLimit(
                    "limit 25 offset 0");

            List<Control_prenatal> lista_datos = getServiceLocator()
                    .getControl_prenatalService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Control_prenatal control_prenatal : lista_datos) {
                rowsResultado.appendChild(crearFilas(control_prenatal, this));
            }

            gridResultado.setVisible(true);
            gridResultado.setMold("paging");
            gridResultado.setPageSize(20);

            gridResultado.applyProperties();
            gridResultado.invalidate();
            gridResultado.setVisible(true);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Mensaje de Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Row crearFilas(Object objeto, Component componente) throws Exception {
        Row fila = new Row();

        final Control_prenatal control_prenatal = (Control_prenatal) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(control_prenatal.getCodigo_historia() + ""));
        fila.appendChild(new Label(control_prenatal.getIdentificacion() + ""));
        fila.appendChild(new Label(control_prenatal.getFecha_inicial() + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {

                mostrarDatos(control_prenatal);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        // img.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
        img.setSrc("/images/eliminar.gif");
        img.setTooltiptext("Eliminar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                Messagebox.show(
                        "Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    eliminarDatos(control_prenatal);
                                    buscarDatos();
                                }
                            }
                        });
            }
        });
        hbox.appendChild(space);
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            setUpperCase();
            if (validarForm()) {
                // Cargamos los componentes //

                Map datos = new HashMap();

                Control_prenatal control_prenatal = new Control_prenatal();
                control_prenatal.setCodigo_empresa(empresa.getCodigo_empresa());
                control_prenatal.setCodigo_sucursal(sucursal
                        .getCodigo_sucursal());
                control_prenatal.setCodigo_historia(tbxCodigo_historia
                        .getValue());
                control_prenatal
                        .setIdentificacion(tbxIdentificacion.getValue());
                control_prenatal.setFecha_inicial(new Timestamp(
                        dtbxFecha_inicial.getValue().getTime()));
                control_prenatal.setSintomatico_piel(rdbSintomatico_piel
                        .getSelectedItem().getValue().toString());
                control_prenatal.setSintomatico_resp(rdbSintomatico_resp
                        .getSelectedItem().getValue().toString());
                control_prenatal.setMotivo(tbxMotivo.getValue());
                control_prenatal.setEnfermedad_actual(tbxEnfermedad_actual
                        .getValue());
                control_prenatal
                        .setAntecedentes_descritos(tbxAntecedentes_descritos
                                .getValue());
                control_prenatal.setOtros(tbxOtros.getValue());
                control_prenatal.setConciencia(lbxConciencia.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_conciencia(tbxObservacion_conciencia
                                .getValue());
                control_prenatal.setPiel(lbxPiel.getSelectedItem().getValue()
                        .toString());
                control_prenatal.setObservacion_piel(tbxObservacion_piel
                        .getValue());
                control_prenatal.setCuello(lbxCuello.getSelectedItem()
                        .getValue().toString());
                control_prenatal.setObservacion_cuello(tbxObservacion_cuello
                        .getValue());
                control_prenatal.setSentidos(lbxSentidos.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_sentidos(tbxObservacion_sentidos
                                .getValue());
                control_prenatal.setTorax(lbxTorax.getSelectedItem().getValue()
                        .toString());
                control_prenatal.setObservacion_torax(tbxObservacion_torax
                        .getValue());
                control_prenatal.setRespiratorio(lbxRespiratorio
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_respiratorio(tbxObservacion_respiratorio
                                .getValue());
                control_prenatal.setCardiovascular(lbxCardiovascular
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_cardiovascular(tbxObservacion_cardiovascular
                                .getValue());
                control_prenatal.setVascular(lbxVascular.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_vascular(tbxObservacion_vascular
                                .getValue());
                control_prenatal.setAbdomen(lbxAbdomen.getSelectedItem()
                        .getValue().toString());
                control_prenatal.setObservacion_abdomen(tbxObservacion_abdomen
                        .getValue());
                control_prenatal.setPerianal(lbxPerianal.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_perianal(tbxObservacion_perianal
                                .getValue());
                control_prenatal.setInguinal(lbxInguinal.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_inguinal(tbxObservacion_inguinal
                                .getValue());
                control_prenatal.setGenitales(lbxGenitales.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_genitales(tbxObservacion_genitales
                                .getValue());
                control_prenatal.setExtremidades(lbxExtremidades
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_extremidades(tbxObservacion_extremidades
                                .getValue());
                control_prenatal.setNeurologico(lbxNeurologico
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_neurologico(tbxObservacion_neurologico
                                .getValue());
                control_prenatal.setPeriferico(lbxPeriferico.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_periferico(tbxObservacion_periferico
                                .getValue());
                control_prenatal.setLinfatico(lbxLinfatico.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_linfatico(tbxObservacion_linfatico
                                .getValue());
                control_prenatal.setOsteomuscular(lbxOsteomuscular
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_osteomuscular(tbxObservacion_osteomuscular
                                .getValue());
                control_prenatal.setEndocrino(lbxEndocrino.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_endocrino(tbxObservacion_endocrino
                                .getValue());
                control_prenatal.setGastrointestinal(lbxGastrointestinal
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_gastrointestinal(tbxObservacion_gastrointestinal
                                .getValue());
                control_prenatal.setGenitourinario(lbxGenitourinario
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_genitourinario(tbxObservacion_genitourinario
                                .getValue());
                control_prenatal.setLocomotor(lbxLocomotor.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_locomotor(tbxObservacion_locomotor
                                .getValue());
                control_prenatal
                        .setEstado_general(tbxEstado_general.getValue());
                control_prenatal.setCardiaca(tbxCardiaca.getValue());
                control_prenatal.setRespiratoria(tbxRespiratoria.getValue());
                control_prenatal.setPeso(tbxPeso.getValue());
                control_prenatal.setTalla(tbxTalla.getValue());
                control_prenatal.setTempo(tbxTempo.getValue());
                control_prenatal.setPresion(tbxPresion.getValue());
                control_prenatal.setPresion2(tbxPresion2.getValue());
                control_prenatal.setInd_masa(tbxInd_masa.getValue());
                control_prenatal.setSus_masa(tbxSus_masa.getValue());
                control_prenatal.setApariencia(lbxApariencia.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_apariencia(tbxObservacion_apariencia
                                .getValue());
                control_prenatal.setCabeza(lbxCabeza.getSelectedItem()
                        .getValue().toString());
                control_prenatal.setObservacion_cabeza(tbxObservacion_cabeza
                        .getValue());
                control_prenatal.setExam_torax(lbxExam_torax.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_exam_torax(tbxObservacion_exam_torax
                                .getValue());
                control_prenatal.setExam_cardio(lbxExam_cardio
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_exam_cardio(tbxObservacion_exam_cardio
                                .getValue());
                control_prenatal.setExam_abdomen(lbxExam_abdomen
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_exam_abdomen(tbxObservacion_exam_abdomen
                                .getValue());
                control_prenatal.setExam_pies(lbxExam_pies.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_exam_pies(tbxObservacion_exam_pies
                                .getValue());
                control_prenatal.setExam_genital(lbxExam_genital
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_exam_genital(tbxObservacion_exam_genital
                                .getValue());
                control_prenatal.setExam_cuello(lbxExam_cuello
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_exam_cuello(tbxObservacion_exam_cuello
                                .getValue());
                control_prenatal.setExam_extremidades(lbxExam_extremidades
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_exam_extremidades(tbxObservacion_exam_extremidades
                                .getValue());
                control_prenatal.setBoca(lbxBoca.getSelectedItem().getValue()
                        .toString());
                control_prenatal.setObservacion_boca(tbxObservacion_boca
                        .getValue());
                control_prenatal.setSenos(lbxSenos.getSelectedItem().getValue()
                        .toString());
                control_prenatal.setObservacion_senos(tbxObservacion_senos
                        .getValue());
                control_prenatal.setExam_osteomuscular(lbxExam_osteomuscular
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_exam_osteomuscular(tbxObservacion_exam_osteomuscular
                                .getValue());
                control_prenatal.setExam_neuro(lbxExam_neuro.getSelectedItem()
                        .getValue().toString());
                control_prenatal
                        .setObservacion_exam_neuro(tbxObservacion_exam_neuro
                                .getValue());
                control_prenatal.setExam_sentidos(lbxExam_sentidos
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setObservacion_exam_sentidos(tbxObservacion_exam_sentidos
                                .getValue());
                control_prenatal
                        .setAnalisis_paraclinico(tbxAnalisis_paraclinico
                                .getValue());
                control_prenatal.setAnalisis_ganancia(tbxAnalisis_ganancia
                        .getValue());
                control_prenatal
                        .setAnalisis_crecimiento(tbxAnalisis_crecimiento
                                .getValue());
                control_prenatal.setAnalisis_presion(tbxAnalisis_presion
                        .getValue());
                control_prenatal.setSulfato(tbxSulfato.getValue());
                control_prenatal.setFoloco(tbxFoloco.getValue());
                control_prenatal.setCalcio(tbxCalcio.getValue());
                control_prenatal.setParaclinico(tbxParaclinico.getValue());
                control_prenatal.setEducacion_antes(tbxEducacion_antes
                        .getValue());
                control_prenatal.setSignos(tbxSignos.getValue());
                control_prenatal.setConsejeria(tbxConsejeria.getValue());
                control_prenatal
                        .setEducacion_iami(tbxEducacion_iami.getValue());
                control_prenatal.setEntrega_educacion(tbxEntrega_educacion
                        .getValue());
                control_prenatal.setCodigo_consulta_pyp(lbxCodigo_consulta_pyp
                        .getSelectedItem().getValue().toString());
                control_prenatal.setFinalidad_cons(lbxFinalidad_cons
                        .getSelectedItem().getValue().toString());
                control_prenatal.setCausas_externas(lbxCausas_externas
                        .getSelectedItem().getValue().toString());
                control_prenatal.setTipo_disnostico(lbxTipo_disnostico
                        .getSelectedItem().getValue().toString());
                control_prenatal
                        .setTipo_principal(tbxTipo_principal.getValue());
                control_prenatal.setTipo_relacionado_1(tbxTipo_relacionado_1
                        .getValue());
                control_prenatal.setTipo_relacionado_2(tbxTipo_relacionado_2
                        .getValue());
                control_prenatal.setTipo_relacionado_3(tbxTipo_relacionado_3
                        .getValue());
                control_prenatal.setTratamiento(tbxTratamiento.getValue());

                datos.put("codigo_historia", control_prenatal);
                datos.put("accion", tbxAccion.getText());

                control_prenatal = getServiceLocator()
                        .getControl_prenatalService().guardar(datos);

                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    accionForm(true, "modificar");
                }

                Messagebox.show("Los datos se guardaron satisfactoriamente",
                        "Informacion ..", Messagebox.OK,
                        Messagebox.INFORMATION);

            }

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Mensaje de Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }

    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Control_prenatal control_prenatal = (Control_prenatal) obj;
        try {
            tbxCodigo_historia.setValue(control_prenatal.getCodigo_historia());
            dtbxFecha_inicial.setValue(control_prenatal.getFecha_inicial());

            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(control_prenatal.getCodigo_empresa());
            paciente.setCodigo_sucursal(control_prenatal.getCodigo_sucursal());
            paciente.setNro_identificacion(control_prenatal.getIdentificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            Elemento elemento = new Elemento();
            elemento.setCodigo(paciente.getSexo());
            elemento.setTipo("sexo");
            elemento = getServiceLocator().getElementoService().consultar(
                    elemento);

            tbxIdentificacion.setValue(control_prenatal.getIdentificacion());
            tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
                    + " " + paciente.getApellido1() : ""));
            tbxTipoIdentificacion.setValue((paciente != null ? paciente
                    .getTipo_identificacion() : ""));
            tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
                    paciente.getUnidad_medidad(), false));
            tbxSexo.setValue((elemento != null ? elemento.getDescripcion() : ""));
            dbxNacimiento.setValue(paciente.getFecha_nacimiento());
            tbxDireccion.setValue(paciente.getDireccion());

            Administradora administradora = new Administradora();
            administradora.setCodigo(paciente.getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            tbxCodigo_eps.setValue(administradora != null ? administradora
                    .getCodigo() : "");
            tbxNombre_eps.setValue(administradora != null ? administradora
                    .getNombre() : "");

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(control_prenatal.getCodigo_empresa());
            contratos.setCodigo_sucursal(control_prenatal.getCodigo_sucursal());
            contratos.setCodigo_administradora(paciente.getCodigo_administradora());
//			contratos.setId_plan(paciente.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(contratos);

            tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
                    : "");
            tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

            for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(paciente.getCodigo_dpto())) {
                    listitem.setSelected(true);
                    i = lbxCodigo_dpto.getItemCount();
                }
            }

            listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

            Radio radio = (Radio) rdbSintomatico_piel
                    .getFellow("Sintomatico_piel"
                            + control_prenatal.getSintomatico_piel());
            radio.setChecked(true);
            Radio radio1 = (Radio) rdbSintomatico_resp
                    .getFellow("Sintomatico_resp"
                            + control_prenatal.getSintomatico_resp());
            radio1.setChecked(true);
            tbxMotivo.setValue(control_prenatal.getMotivo());
            tbxEnfermedad_actual.setValue(control_prenatal
                    .getEnfermedad_actual());
            tbxAntecedentes_descritos.setValue(control_prenatal
                    .getAntecedentes_descritos());
            tbxOtros.setValue(control_prenatal.getOtros());
            for (int i = 0; i < lbxConciencia.getItemCount(); i++) {
                Listitem listitem = lbxConciencia.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getConciencia())) {
                    listitem.setSelected(true);
                    i = lbxConciencia.getItemCount();
                }
            }
            tbxObservacion_conciencia.setValue(control_prenatal
                    .getObservacion_conciencia());
            for (int i = 0; i < lbxPiel.getItemCount(); i++) {
                Listitem listitem = lbxPiel.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getPiel())) {
                    listitem.setSelected(true);
                    i = lbxPiel.getItemCount();
                }
            }
            tbxObservacion_piel
                    .setValue(control_prenatal.getObservacion_piel());
            for (int i = 0; i < lbxCuello.getItemCount(); i++) {
                Listitem listitem = lbxCuello.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getCuello())) {
                    listitem.setSelected(true);
                    i = lbxCuello.getItemCount();
                }
            }
            tbxObservacion_cuello.setValue(control_prenatal
                    .getObservacion_cuello());
            for (int i = 0; i < lbxSentidos.getItemCount(); i++) {
                Listitem listitem = lbxSentidos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getSentidos())) {
                    listitem.setSelected(true);
                    i = lbxSentidos.getItemCount();
                }
            }
            tbxObservacion_sentidos.setValue(control_prenatal
                    .getObservacion_sentidos());
            for (int i = 0; i < lbxTorax.getItemCount(); i++) {
                Listitem listitem = lbxTorax.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getTorax())) {
                    listitem.setSelected(true);
                    i = lbxTorax.getItemCount();
                }
            }
            tbxObservacion_torax.setValue(control_prenatal
                    .getObservacion_torax());
            for (int i = 0; i < lbxRespiratorio.getItemCount(); i++) {
                Listitem listitem = lbxRespiratorio.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getRespiratorio())) {
                    listitem.setSelected(true);
                    i = lbxRespiratorio.getItemCount();
                }
            }
            tbxObservacion_respiratorio.setValue(control_prenatal
                    .getObservacion_respiratorio());
            for (int i = 0; i < lbxCardiovascular.getItemCount(); i++) {
                Listitem listitem = lbxCardiovascular.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getCardiovascular())) {
                    listitem.setSelected(true);
                    i = lbxCardiovascular.getItemCount();
                }
            }
            tbxObservacion_cardiovascular.setValue(control_prenatal
                    .getObservacion_cardiovascular());
            for (int i = 0; i < lbxVascular.getItemCount(); i++) {
                Listitem listitem = lbxVascular.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getVascular())) {
                    listitem.setSelected(true);
                    i = lbxVascular.getItemCount();
                }
            }
            tbxObservacion_vascular.setValue(control_prenatal
                    .getObservacion_vascular());
            for (int i = 0; i < lbxAbdomen.getItemCount(); i++) {
                Listitem listitem = lbxAbdomen.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getAbdomen())) {
                    listitem.setSelected(true);
                    i = lbxAbdomen.getItemCount();
                }
            }
            tbxObservacion_abdomen.setValue(control_prenatal
                    .getObservacion_abdomen());
            for (int i = 0; i < lbxPerianal.getItemCount(); i++) {
                Listitem listitem = lbxPerianal.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getPerianal())) {
                    listitem.setSelected(true);
                    i = lbxPerianal.getItemCount();
                }
            }
            tbxObservacion_perianal.setValue(control_prenatal
                    .getObservacion_perianal());
            for (int i = 0; i < lbxInguinal.getItemCount(); i++) {
                Listitem listitem = lbxInguinal.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getInguinal())) {
                    listitem.setSelected(true);
                    i = lbxInguinal.getItemCount();
                }
            }
            tbxObservacion_inguinal.setValue(control_prenatal
                    .getObservacion_inguinal());
            for (int i = 0; i < lbxGenitales.getItemCount(); i++) {
                Listitem listitem = lbxGenitales.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getGenitales())) {
                    listitem.setSelected(true);
                    i = lbxGenitales.getItemCount();
                }
            }
            tbxObservacion_genitales.setValue(control_prenatal
                    .getObservacion_genitales());
            for (int i = 0; i < lbxExtremidades.getItemCount(); i++) {
                Listitem listitem = lbxExtremidades.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExtremidades())) {
                    listitem.setSelected(true);
                    i = lbxExtremidades.getItemCount();
                }
            }
            tbxObservacion_extremidades.setValue(control_prenatal
                    .getObservacion_extremidades());
            for (int i = 0; i < lbxNeurologico.getItemCount(); i++) {
                Listitem listitem = lbxNeurologico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getNeurologico())) {
                    listitem.setSelected(true);
                    i = lbxNeurologico.getItemCount();
                }
            }
            tbxObservacion_neurologico.setValue(control_prenatal
                    .getObservacion_neurologico());
            for (int i = 0; i < lbxPeriferico.getItemCount(); i++) {
                Listitem listitem = lbxPeriferico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getPeriferico())) {
                    listitem.setSelected(true);
                    i = lbxPeriferico.getItemCount();
                }
            }
            tbxObservacion_periferico.setValue(control_prenatal
                    .getObservacion_periferico());
            for (int i = 0; i < lbxLinfatico.getItemCount(); i++) {
                Listitem listitem = lbxLinfatico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getLinfatico())) {
                    listitem.setSelected(true);
                    i = lbxLinfatico.getItemCount();
                }
            }
            tbxObservacion_linfatico.setValue(control_prenatal
                    .getObservacion_linfatico());
            for (int i = 0; i < lbxOsteomuscular.getItemCount(); i++) {
                Listitem listitem = lbxOsteomuscular.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getOsteomuscular())) {
                    listitem.setSelected(true);
                    i = lbxOsteomuscular.getItemCount();
                }
            }
            tbxObservacion_osteomuscular.setValue(control_prenatal
                    .getObservacion_osteomuscular());
            for (int i = 0; i < lbxEndocrino.getItemCount(); i++) {
                Listitem listitem = lbxEndocrino.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getEndocrino())) {
                    listitem.setSelected(true);
                    i = lbxEndocrino.getItemCount();
                }
            }
            tbxObservacion_endocrino.setValue(control_prenatal
                    .getObservacion_endocrino());
            for (int i = 0; i < lbxGastrointestinal.getItemCount(); i++) {
                Listitem listitem = lbxGastrointestinal.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getGastrointestinal())) {
                    listitem.setSelected(true);
                    i = lbxGastrointestinal.getItemCount();
                }
            }
            tbxObservacion_gastrointestinal.setValue(control_prenatal
                    .getObservacion_gastrointestinal());
            for (int i = 0; i < lbxGenitourinario.getItemCount(); i++) {
                Listitem listitem = lbxGenitourinario.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getGenitourinario())) {
                    listitem.setSelected(true);
                    i = lbxGenitourinario.getItemCount();
                }
            }
            tbxObservacion_genitourinario.setValue(control_prenatal
                    .getObservacion_genitourinario());
            for (int i = 0; i < lbxLocomotor.getItemCount(); i++) {
                Listitem listitem = lbxLocomotor.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getLocomotor())) {
                    listitem.setSelected(true);
                    i = lbxLocomotor.getItemCount();
                }
            }
            tbxObservacion_locomotor.setValue(control_prenatal
                    .getObservacion_locomotor());
            tbxEstado_general.setValue(control_prenatal.getEstado_general());
            tbxCardiaca.setValue(control_prenatal.getCardiaca());
            tbxRespiratoria.setValue(control_prenatal.getRespiratoria());
            tbxPeso.setValue(control_prenatal.getPeso());
            tbxTalla.setValue(control_prenatal.getTalla());
            tbxTempo.setValue(control_prenatal.getTempo());
            tbxPresion.setValue(control_prenatal.getPresion());
            tbxPresion2.setValue(control_prenatal.getPresion2());
            tbxInd_masa.setValue(control_prenatal.getInd_masa());
            tbxSus_masa.setValue(control_prenatal.getSus_masa());
            for (int i = 0; i < lbxApariencia.getItemCount(); i++) {
                Listitem listitem = lbxApariencia.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getApariencia())) {
                    listitem.setSelected(true);
                    i = lbxApariencia.getItemCount();
                }
            }
            tbxObservacion_apariencia.setValue(control_prenatal
                    .getObservacion_apariencia());
            for (int i = 0; i < lbxCabeza.getItemCount(); i++) {
                Listitem listitem = lbxCabeza.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getCabeza())) {
                    listitem.setSelected(true);
                    i = lbxCabeza.getItemCount();
                }
            }
            tbxObservacion_cabeza.setValue(control_prenatal
                    .getObservacion_cabeza());
            for (int i = 0; i < lbxExam_torax.getItemCount(); i++) {
                Listitem listitem = lbxExam_torax.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_torax())) {
                    listitem.setSelected(true);
                    i = lbxExam_torax.getItemCount();
                }
            }
            tbxObservacion_exam_torax.setValue(control_prenatal
                    .getObservacion_exam_torax());
            for (int i = 0; i < lbxExam_cardio.getItemCount(); i++) {
                Listitem listitem = lbxExam_cardio.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_cardio())) {
                    listitem.setSelected(true);
                    i = lbxExam_cardio.getItemCount();
                }
            }
            tbxObservacion_exam_cardio.setValue(control_prenatal
                    .getObservacion_exam_cardio());
            for (int i = 0; i < lbxExam_abdomen.getItemCount(); i++) {
                Listitem listitem = lbxExam_abdomen.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_abdomen())) {
                    listitem.setSelected(true);
                    i = lbxExam_abdomen.getItemCount();
                }
            }
            tbxObservacion_exam_abdomen.setValue(control_prenatal
                    .getObservacion_exam_abdomen());
            for (int i = 0; i < lbxExam_pies.getItemCount(); i++) {
                Listitem listitem = lbxExam_pies.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_pies())) {
                    listitem.setSelected(true);
                    i = lbxExam_pies.getItemCount();
                }
            }
            tbxObservacion_exam_pies.setValue(control_prenatal
                    .getObservacion_exam_pies());
            for (int i = 0; i < lbxExam_genital.getItemCount(); i++) {
                Listitem listitem = lbxExam_genital.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_genital())) {
                    listitem.setSelected(true);
                    i = lbxExam_genital.getItemCount();
                }
            }
            tbxObservacion_exam_genital.setValue(control_prenatal
                    .getObservacion_exam_genital());
            for (int i = 0; i < lbxExam_cuello.getItemCount(); i++) {
                Listitem listitem = lbxExam_cuello.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_cuello())) {
                    listitem.setSelected(true);
                    i = lbxExam_cuello.getItemCount();
                }
            }
            tbxObservacion_exam_cuello.setValue(control_prenatal
                    .getObservacion_exam_cuello());
            for (int i = 0; i < lbxExam_extremidades.getItemCount(); i++) {
                Listitem listitem = lbxExam_extremidades.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_extremidades())) {
                    listitem.setSelected(true);
                    i = lbxExam_extremidades.getItemCount();
                }
            }
            tbxObservacion_exam_extremidades.setValue(control_prenatal
                    .getObservacion_exam_extremidades());
            for (int i = 0; i < lbxBoca.getItemCount(); i++) {
                Listitem listitem = lbxBoca.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getBoca())) {
                    listitem.setSelected(true);
                    i = lbxBoca.getItemCount();
                }
            }
            tbxObservacion_boca
                    .setValue(control_prenatal.getObservacion_boca());
            for (int i = 0; i < lbxSenos.getItemCount(); i++) {
                Listitem listitem = lbxSenos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getSenos())) {
                    listitem.setSelected(true);
                    i = lbxSenos.getItemCount();
                }
            }
            tbxObservacion_senos.setValue(control_prenatal
                    .getObservacion_senos());
            for (int i = 0; i < lbxExam_osteomuscular.getItemCount(); i++) {
                Listitem listitem = lbxExam_osteomuscular.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_osteomuscular())) {
                    listitem.setSelected(true);
                    i = lbxExam_osteomuscular.getItemCount();
                }
            }
            tbxObservacion_exam_osteomuscular.setValue(control_prenatal
                    .getObservacion_exam_osteomuscular());
            for (int i = 0; i < lbxExam_neuro.getItemCount(); i++) {
                Listitem listitem = lbxExam_neuro.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_neuro())) {
                    listitem.setSelected(true);
                    i = lbxExam_neuro.getItemCount();
                }
            }
            tbxObservacion_exam_neuro.setValue(control_prenatal
                    .getObservacion_exam_neuro());
            for (int i = 0; i < lbxExam_sentidos.getItemCount(); i++) {
                Listitem listitem = lbxExam_sentidos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getExam_sentidos())) {
                    listitem.setSelected(true);
                    i = lbxExam_sentidos.getItemCount();
                }
            }
            tbxObservacion_exam_sentidos.setValue(control_prenatal
                    .getObservacion_exam_sentidos());
            tbxAnalisis_paraclinico.setValue(control_prenatal
                    .getAnalisis_paraclinico());
            tbxAnalisis_ganancia.setValue(control_prenatal
                    .getAnalisis_ganancia());
            tbxAnalisis_crecimiento.setValue(control_prenatal
                    .getAnalisis_crecimiento());
            tbxAnalisis_presion
                    .setValue(control_prenatal.getAnalisis_presion());
            tbxSulfato.setValue(control_prenatal.getSulfato());
            tbxFoloco.setValue(control_prenatal.getFoloco());
            tbxCalcio.setValue(control_prenatal.getCalcio());
            tbxParaclinico.setValue(control_prenatal.getParaclinico());
            tbxEducacion_antes.setValue(control_prenatal.getEducacion_antes());
            tbxSignos.setValue(control_prenatal.getSignos());
            tbxConsejeria.setValue(control_prenatal.getConsejeria());
            tbxEducacion_iami.setValue(control_prenatal.getEducacion_iami());
            tbxEntrega_educacion.setValue(control_prenatal
                    .getEntrega_educacion());
            for (int i = 0; i < lbxFinalidad_cons.getItemCount(); i++) {
                Listitem listitem = lbxFinalidad_cons.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getFinalidad_cons())) {
                    listitem.setSelected(true);
                    i = lbxFinalidad_cons.getItemCount();
                }
            }
            seleccionarProgramaPyp();

            for (int i = 0; i < lbxCausas_externas.getItemCount(); i++) {
                Listitem listitem = lbxCausas_externas.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getCausas_externas())) {
                    listitem.setSelected(true);
                    i = lbxCausas_externas.getItemCount();
                }
            }
            for (int i = 0; i < lbxCodigo_consulta_pyp.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_consulta_pyp.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getCodigo_consulta_pyp())) {
                    listitem.setSelected(true);
                    i = lbxCodigo_consulta_pyp.getItemCount();
                }
            }
            for (int i = 0; i < lbxTipo_disnostico.getItemCount(); i++) {
                Listitem listitem = lbxTipo_disnostico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(control_prenatal.getTipo_disnostico())) {
                    listitem.setSelected(true);
                    i = lbxTipo_disnostico.getItemCount();
                }
            }

            Cie cie = new Cie();
            cie.setCodigo(control_prenatal.getTipo_principal());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);
            tbxTipo_principal.setValue(control_prenatal.getTipo_principal());
            tbxNomDx.setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 1 */
            cie = new Cie();
            cie.setCodigo(control_prenatal.getTipo_relacionado_1());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_1.setValue(control_prenatal
                    .getTipo_relacionado_1());
            tbxNomDxRelacionado_1
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 2 */
            cie = new Cie();
            cie.setCodigo(control_prenatal.getTipo_relacionado_2());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_2.setValue(control_prenatal
                    .getTipo_relacionado_2());
            tbxNomDxRelacionado_2
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 3 */
            cie = new Cie();
            cie.setCodigo(control_prenatal.getTipo_relacionado_3());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_3.setValue(control_prenatal
                    .getTipo_relacionado_3());
            tbxNomDxRelacionado_3
                    .setValue((cie != null ? cie.getNombre() : ""));

            tbxTratamiento.setValue(control_prenatal.getTratamiento());

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show("Este dato no se puede editar", "Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Control_prenatal control_prenatal = (Control_prenatal) obj;
        try {
            int result = getServiceLocator().getControl_prenatalService()
                    .eliminar(control_prenatal);
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

    public ServiceLocatorWeb getServiceLocator() {
        return ServiceLocatorWeb.getInstance();
    }

    public void listarDepartamentos(Listbox listbox, boolean select) {
        listbox.getChildren().clear();
        Listitem listitem;
        if (select) {
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("-- seleccione --");
            listbox.appendChild(listitem);
        }
        List<Departamentos> departamentos = this.getServiceLocator()
                .getDepartamentosService().listar(new HashMap());

        for (Departamentos dpto : departamentos) {
            listitem = new Listitem();
            listitem.setValue(dpto.getCodigo());
            listitem.setLabel(dpto.getNombre());
            listbox.appendChild(listitem);
        }
        if (listbox.getItemCount() > 0) {
            listbox.setSelectedIndex(0);
        }
    }

    public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
        listboxMun.getChildren().clear();
        Listitem listitem;
        String coddep = listboxDpto.getSelectedItem().getValue().toString();
        Map<String, Object> parameters = new HashMap();
        parameters.put("coddep", coddep);
        List<Municipios> municipios = this.getServiceLocator()
                .getMunicipiosService().listar(parameters);

        for (Municipios mun : municipios) {
            listitem = new Listitem();
            listitem.setValue(mun.getCodigo());
            listitem.setLabel(mun.getNombre());
            listboxMun.appendChild(listitem);
        }
        if (listboxMun.getItemCount() > 0) {
            listboxMun.setSelectedIndex(0);
        }
    }

    public void buscarPaciente(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");

            parameters.put("limite_paginado",
                    "limit 25 offset 0");

            List<Paciente> list = getServiceLocator().getPacienteService()
                    .listar(parameters);

            lbx.getItems().clear();

            for (Paciente dato : list) {

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
                listcell.appendChild(new Label(dato.getNombre1() + " "
                        + dato.getNombre2()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getApellido1() + " "
                        + dato.getApellido2()));
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

    public void selectedPaciente(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxIdentificacion.setValue("");
            tbxNomPaciente.setValue("");
            tbxTipoIdentificacion.setValue("");
            tbxEdad.setValue("");
            tbxSexo.setValue("");
            dbxNacimiento.setValue(null);
            tbxCodigo_eps.setValue("");
            tbxNombre_eps.setValue("");
            tbxDireccion.setValue("");
            tbxCodigo_contrato.setValue("");
            tbxContrato.setValue("");

            for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
                Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
                if (listitem1.getValue().toString().equals("")) {
                    listitem1.setSelected(true);
                    i = lbxCodigo_dpto.getItemCount();
                }
            }

            listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
            for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
                Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
                if (listitem1.getValue().toString().equals("")) {
                    listitem1.setSelected(true);
                    i = lbxCodigo_municipio.getItemCount();
                }
            }

        } else {
            Paciente dato = (Paciente) listitem.getValue();

            Elemento elemento = new Elemento();
            elemento.setCodigo(dato.getSexo());
            elemento.setTipo("sexo");
            elemento = getServiceLocator().getElementoService().consultar(
                    elemento);

            tbxIdentificacion.setValue(dato.getNro_identificacion());
            tbxNomPaciente.setValue(dato.getNombre1() + " " + dato.getNombre2()
                    + " " + dato.getApellido1() + " " + dato.getApellido2());
            tbxTipoIdentificacion.setValue(dato.getTipo_identificacion());
            tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(dato.getFecha_nacimiento()), dato
                    .getUnidad_medidad(), false));
            tbxSexo.setValue(elemento != null ? elemento.getDescripcion() : "");
            dbxNacimiento.setValue(dato.getFecha_nacimiento());
            tbxDireccion.setValue(dato.getDireccion());

            Administradora administradora = new Administradora();
            administradora.setCodigo(dato.getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            tbxCodigo_eps.setValue(administradora != null ? administradora
                    .getCodigo() : "");
            tbxNombre_eps.setValue(administradora != null ? administradora
                    .getNombre() : "");

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(dato.getCodigo_empresa());
            contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
            contratos.setCodigo_administradora(dato.getCodigo_administradora());
//			contratos.setId_plan(dato.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(contratos);

            tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
                    : "");
            tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

            for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
                Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
                if (listitem1.getValue().toString()
                        .equals(dato.getCodigo_dpto())) {
                    listitem1.setSelected(true);
                    i = lbxCodigo_dpto.getItemCount();
                }
            }

            listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
            for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
                Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
                if (listitem1.getValue().toString()
                        .equals(dato.getCodigo_municipio())) {
                    listitem1.setSelected(true);
                    i = lbxCodigo_municipio.getItemCount();
                }
            }
        }

        tbxIdentificacion.close();
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

    public void seleccionarProgramaPyp() {
        String codigo_finalidad = ""
                + lbxFinalidad_cons.getSelectedItem().getValue();
        if (!codigo_finalidad.trim().isEmpty()) {
            Map<String, Object> parameters = new HashMap();
            parameters.put("area_intervencion", codigo_finalidad);
            List<Plantilla_pyp> plantillaPyps = getServiceLocator()
                    .getPlantillaPypService().listar(parameters);
            lbxCodigo_consulta_pyp.getChildren().clear();
            Listitem listitem;
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("-- seleccione --");
            lbxCodigo_consulta_pyp.appendChild(listitem);

            for (Plantilla_pyp plantillaPyp : plantillaPyps) {
                listitem = new Listitem();
                listitem.setValue(plantillaPyp.getCodigo_pdc());
                listitem.setLabel(plantillaPyp.getCodigo_pdc() + "-"
                        + plantillaPyp.getNombre_pcd());
                lbxCodigo_consulta_pyp.appendChild(listitem);
            }
            if (lbxCodigo_consulta_pyp.getItemCount() > 0) {
                lbxCodigo_consulta_pyp.setSelectedIndex(0);
            }
        } else {
            initPypList();
        }

    }

    private void initPypList() {
        lbxCodigo_consulta_pyp.getChildren().clear();
        Listitem listitem;
        listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("-- seleccione --");
        lbxCodigo_consulta_pyp.appendChild(listitem);

        if (lbxCodigo_consulta_pyp.getItemCount() > 0) {
            lbxCodigo_consulta_pyp.setSelectedIndex(0);
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
}
