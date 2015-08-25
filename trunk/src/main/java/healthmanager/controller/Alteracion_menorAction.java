/*
 * alteracion_menorAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Alteracion_menor;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
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

public class Alteracion_menorAction extends Borderlayout implements
        AfterCompose {

    private static Logger log = Logger.getLogger(Alteracion_menorAction.class);
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

    private Textbox tbxMadre;
    private Textbox tbxPadre;
    private Textbox tbxPaterno_medicos;
    private Textbox tbxPaterno_alergicos;
    private Textbox tbxMaterno_medicos;
    private Textbox tbxMaterno_alergicos;
    private Textbox tbxObstetricos_g;
    private Textbox tbxObstetricos_p;
    private Textbox tbxObstetricos_a;
    private Textbox tbxObstetricos_c;
    private Textbox tbxOtros_antecedentes;
    private Listbox lbxMenor;
    private Listbox lbxNo_hnos;
    private Listbox lbxHnos_muertos;
    private Textbox tbxCausa;
    private Listbox lbxEmbarazo;
    private Radiogroup rdbControl_prenatal;
    private Textbox tbxEdad_madre;
    private Textbox tbxComplicaciones;
    private Textbox tbxSemanas_gestion;
    private Checkbox chbVagina;
    private Checkbox chbCesaria;
    private Textbox tbxPresentacion;
    private Radiogroup rdbParto_institucional;
    private Textbox tbxInstitucion;
    private Textbox tbxComplicaciones_parto;
    private Textbox tbxPeso_nacer;
    private Textbox tbxTalla_nacer;
    private Textbox tbxApgar;
    private Textbox tbxApgar5;
    private Textbox tbxComplicaciones_postparto;
    private Radiogroup rdbReanimacion;
    private Radiogroup rdbHospitalizacion_neonatal;
    private Textbox tbxHospitalizaciones;
    private Textbox tbxMedicos;
    private Textbox tbxQuirurgicos;
    private Textbox tbxTraumaticos;
    private Textbox tbxMedicacion_actual;
    private Radiogroup rdbLactancia;
    private Textbox tbxEdad_leche;
    private Listbox lbxTiempo_leche;
    private Textbox tbxCual_leche;
    private Textbox tbxEdad_alimentacion;
    private Listbox lbxTiempo_alimentacion;
    private Textbox tbxCual_alimento;
    private Textbox tbxEdad_destete;
    private Listbox lbxTiempo_destete;
    private Textbox tbxAlimentacion_actual;
    private Textbox tbxTranstornos_alimenticios;
    private Checkbox chbCabeza;
    private Textbox tbxEdad_cabeza;
    private Listbox lbxTiempo_cabeza;
    private Checkbox chbSonrisa;
    private Textbox tbxEdad_sonrisa;
    private Listbox lbxTiempo_sonrisa;
    private Checkbox chbBalbuceo;
    private Textbox tbxEdad_balbuceo;
    private Listbox lbxTiempo_balbuceo;
    private Checkbox chbVoltear;
    private Textbox tbxEdad_voltear;
    private Listbox lbxTiempo_voltear;
    private Checkbox chbSentado;
    private Textbox tbxEdad_sentado;
    private Listbox lbxTiempo_sentado;
    private Checkbox chbObjetos;
    private Textbox tbxEdad_objetos;
    private Listbox lbxTiempo_objetos;
    private Checkbox chbSilabas;
    private Textbox tbxEdad_silabas;
    private Listbox lbxTiempo_silabas;
    private Checkbox chbSento_solo;
    private Textbox tbxEdad_sento_solo;
    private Listbox lbxTiempo_sento_solo;
    private Checkbox chbPalabra_clara;
    private Textbox tbxEdad_palabra_clara;
    private Listbox lbxTiempo_palabra_clara;
    private Checkbox chbPasa_objetos;
    private Textbox tbxEdad_pasa_objetos;
    private Listbox lbxTiempo_pasa_objetos;
    private Checkbox chbGatear;
    private Textbox tbxEdad_gatear;
    private Listbox lbxTiempo_gatear;
    private Checkbox chbPies_sostenidos;
    private Textbox tbxEdad_pies_sostenidos;
    private Listbox lbxTiempo_pies_sostenidos;
    private Checkbox chbSaca_objetos;
    private Textbox tbxEdad_saca_objetos;
    private Listbox lbxTiempo_saca_objetos;
    private Checkbox chbEntiende;
    private Textbox tbxEdad_entiende;
    private Listbox lbxTiempo_entiende;
    private Textbox tbxEnfermedad_actual;
    private Textbox tbxCardiaca;
    private Textbox tbxRespiratoria;
    private Textbox tbxPeso;
    private Textbox tbxTalla;
    private Textbox tbxTempo;
    private Textbox tbxPerimetro_cefalico;
    private Textbox tbxPerimetro_branquial;
    private Textbox tbxPerimetro_toracico;
    private Textbox tbxInd_masa;
    private Textbox tbxConducta_motora;
    private Textbox tbxConducta_sensorial;
    private Textbox tbxConducta_cognitiva;
    private Textbox tbxConducta_lenguaje;
    private Textbox tbxPeso_edad;
    private Textbox tbxTalla_edad;
    private Listbox lbxTipo_peso;
    private Listbox lbxTipo_talla;
    private Textbox tbxPeso_talla;
    private Listbox lbxTipo_peso_talla;
    private Listbox lbxTendencia_peso;
    private Listbox lbxCodigo_consulta_pyp;
    private Listbox lbxFinalidad_cons;
    private Listbox lbxCausas_externas;
    private Listbox lbxTipo_disnostico;

    private Bandbox tbxTipo_principal;
    private Textbox tbxNomDx;
    // private Textbox tbxPlan;

    private Bandbox tbxTipo_relacionado_1;
    private Bandbox tbxTipo_relacionado_2;
    private Bandbox tbxTipo_relacionado_3;

    private Textbox tbxNomDxRelacionado_1;
    private Textbox tbxNomDxRelacionado_2;
    private Textbox tbxNomDxRelacionado_3;

    private Textbox tbxTratamiento;
    private Textbox tbxEvaluacion;

    @Override
    public void afterCompose() {
        loadComponents();
        cargarDatosSesion();
        listarCombos();
    }

    public void loadComponents() {
        form = (Borderlayout) this.getFellow("formAlteracion_menor");

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

        tbxMadre = (Textbox) form.getFellow("tbxMadre");
        tbxPadre = (Textbox) form.getFellow("tbxPadre");
        tbxPaterno_medicos = (Textbox) form.getFellow("tbxPaterno_medicos");
        tbxPaterno_alergicos = (Textbox) form.getFellow("tbxPaterno_alergicos");
        tbxMaterno_medicos = (Textbox) form.getFellow("tbxMaterno_medicos");
        tbxMaterno_alergicos = (Textbox) form.getFellow("tbxMaterno_alergicos");
        tbxObstetricos_g = (Textbox) form.getFellow("tbxObstetricos_g");
        tbxObstetricos_p = (Textbox) form.getFellow("tbxObstetricos_p");
        tbxObstetricos_a = (Textbox) form.getFellow("tbxObstetricos_a");
        tbxObstetricos_c = (Textbox) form.getFellow("tbxObstetricos_c");
        tbxOtros_antecedentes = (Textbox) form
                .getFellow("tbxOtros_antecedentes");
        lbxMenor = (Listbox) form.getFellow("lbxMenor");
        lbxNo_hnos = (Listbox) form.getFellow("lbxNo_hnos");
        lbxHnos_muertos = (Listbox) form.getFellow("lbxHnos_muertos");
        tbxCausa = (Textbox) form.getFellow("tbxCausa");
        lbxEmbarazo = (Listbox) form.getFellow("lbxEmbarazo");
        rdbControl_prenatal = (Radiogroup) form
                .getFellow("rdbControl_prenatal");
        tbxEdad_madre = (Textbox) form.getFellow("tbxEdad_madre");
        tbxComplicaciones = (Textbox) form.getFellow("tbxComplicaciones");
        tbxSemanas_gestion = (Textbox) form.getFellow("tbxSemanas_gestion");
        chbVagina = (Checkbox) form.getFellow("chbVagina");
        chbCesaria = (Checkbox) form.getFellow("chbCesaria");
        tbxPresentacion = (Textbox) form.getFellow("tbxPresentacion");
        rdbParto_institucional = (Radiogroup) form
                .getFellow("rdbParto_institucional");
        tbxInstitucion = (Textbox) form.getFellow("tbxInstitucion");
        tbxComplicaciones_parto = (Textbox) form
                .getFellow("tbxComplicaciones_parto");
        tbxPeso_nacer = (Textbox) form.getFellow("tbxPeso_nacer");
        tbxTalla_nacer = (Textbox) form.getFellow("tbxTalla_nacer");
        tbxApgar = (Textbox) form.getFellow("tbxApgar");
        tbxApgar5 = (Textbox) form.getFellow("tbxApgar5");
        tbxComplicaciones_postparto = (Textbox) form
                .getFellow("tbxComplicaciones_postparto");
        rdbReanimacion = (Radiogroup) form.getFellow("rdbReanimacion");
        rdbHospitalizacion_neonatal = (Radiogroup) form
                .getFellow("rdbHospitalizacion_neonatal");
        tbxHospitalizaciones = (Textbox) form.getFellow("tbxHospitalizaciones");
        tbxMedicos = (Textbox) form.getFellow("tbxMedicos");
        tbxQuirurgicos = (Textbox) form.getFellow("tbxQuirurgicos");
        tbxTraumaticos = (Textbox) form.getFellow("tbxTraumaticos");
        tbxMedicacion_actual = (Textbox) form.getFellow("tbxMedicacion_actual");
        rdbLactancia = (Radiogroup) form.getFellow("rdbLactancia");
        tbxEdad_leche = (Textbox) form.getFellow("tbxEdad_leche");
        lbxTiempo_leche = (Listbox) form.getFellow("lbxTiempo_leche");
        tbxCual_leche = (Textbox) form.getFellow("tbxCual_leche");
        tbxEdad_alimentacion = (Textbox) form.getFellow("tbxEdad_alimentacion");
        lbxTiempo_alimentacion = (Listbox) form
                .getFellow("lbxTiempo_alimentacion");
        tbxCual_alimento = (Textbox) form.getFellow("tbxCual_alimento");
        tbxEdad_destete = (Textbox) form.getFellow("tbxEdad_destete");
        lbxTiempo_destete = (Listbox) form.getFellow("lbxTiempo_destete");
        tbxAlimentacion_actual = (Textbox) form
                .getFellow("tbxAlimentacion_actual");
        tbxTranstornos_alimenticios = (Textbox) form
                .getFellow("tbxTranstornos_alimenticios");
        chbCabeza = (Checkbox) form.getFellow("chbCabeza");
        tbxEdad_cabeza = (Textbox) form.getFellow("tbxEdad_cabeza");
        lbxTiempo_cabeza = (Listbox) form.getFellow("lbxTiempo_cabeza");
        chbSonrisa = (Checkbox) form.getFellow("chbSonrisa");
        tbxEdad_sonrisa = (Textbox) form.getFellow("tbxEdad_sonrisa");
        lbxTiempo_sonrisa = (Listbox) form.getFellow("lbxTiempo_sonrisa");
        chbBalbuceo = (Checkbox) form.getFellow("chbBalbuceo");
        tbxEdad_balbuceo = (Textbox) form.getFellow("tbxEdad_balbuceo");
        lbxTiempo_balbuceo = (Listbox) form.getFellow("lbxTiempo_balbuceo");
        chbVoltear = (Checkbox) form.getFellow("chbVoltear");
        tbxEdad_voltear = (Textbox) form.getFellow("tbxEdad_voltear");
        lbxTiempo_voltear = (Listbox) form.getFellow("lbxTiempo_voltear");
        chbSentado = (Checkbox) form.getFellow("chbSentado");
        tbxEdad_sentado = (Textbox) form.getFellow("tbxEdad_sentado");
        lbxTiempo_sentado = (Listbox) form.getFellow("lbxTiempo_sentado");
        chbObjetos = (Checkbox) form.getFellow("chbObjetos");
        tbxEdad_objetos = (Textbox) form.getFellow("tbxEdad_objetos");
        lbxTiempo_objetos = (Listbox) form.getFellow("lbxTiempo_objetos");
        chbSilabas = (Checkbox) form.getFellow("chbSilabas");
        tbxEdad_silabas = (Textbox) form.getFellow("tbxEdad_silabas");
        lbxTiempo_silabas = (Listbox) form.getFellow("lbxTiempo_silabas");
        chbSento_solo = (Checkbox) form.getFellow("chbSento_solo");
        tbxEdad_sento_solo = (Textbox) form.getFellow("tbxEdad_sento_solo");
        lbxTiempo_sento_solo = (Listbox) form.getFellow("lbxTiempo_sento_solo");
        chbPalabra_clara = (Checkbox) form.getFellow("chbPalabra_clara");
        tbxEdad_palabra_clara = (Textbox) form
                .getFellow("tbxEdad_palabra_clara");
        lbxTiempo_palabra_clara = (Listbox) form
                .getFellow("lbxTiempo_palabra_clara");
        chbPasa_objetos = (Checkbox) form.getFellow("chbPasa_objetos");
        tbxEdad_pasa_objetos = (Textbox) form.getFellow("tbxEdad_pasa_objetos");
        lbxTiempo_pasa_objetos = (Listbox) form
                .getFellow("lbxTiempo_pasa_objetos");
        chbGatear = (Checkbox) form.getFellow("chbGatear");
        tbxEdad_gatear = (Textbox) form.getFellow("tbxEdad_gatear");
        lbxTiempo_gatear = (Listbox) form.getFellow("lbxTiempo_gatear");
        chbPies_sostenidos = (Checkbox) form.getFellow("chbPies_sostenidos");
        tbxEdad_pies_sostenidos = (Textbox) form
                .getFellow("tbxEdad_pies_sostenidos");
        lbxTiempo_pies_sostenidos = (Listbox) form
                .getFellow("lbxTiempo_pies_sostenidos");
        chbSaca_objetos = (Checkbox) form.getFellow("chbSaca_objetos");
        tbxEdad_saca_objetos = (Textbox) form.getFellow("tbxEdad_saca_objetos");
        lbxTiempo_saca_objetos = (Listbox) form
                .getFellow("lbxTiempo_saca_objetos");
        chbEntiende = (Checkbox) form.getFellow("chbEntiende");
        tbxEdad_entiende = (Textbox) form.getFellow("tbxEdad_entiende");
        lbxTiempo_entiende = (Listbox) form.getFellow("lbxTiempo_entiende");
        tbxEnfermedad_actual = (Textbox) form.getFellow("tbxEnfermedad_actual");
        tbxCardiaca = (Textbox) form.getFellow("tbxCardiaca");
        tbxRespiratoria = (Textbox) form.getFellow("tbxRespiratoria");
        tbxPeso = (Textbox) form.getFellow("tbxPeso");
        tbxTalla = (Textbox) form.getFellow("tbxTalla");
        tbxTempo = (Textbox) form.getFellow("tbxTempo");
        tbxPerimetro_cefalico = (Textbox) form
                .getFellow("tbxPerimetro_cefalico");
        tbxPerimetro_branquial = (Textbox) form
                .getFellow("tbxPerimetro_branquial");
        tbxPerimetro_toracico = (Textbox) form
                .getFellow("tbxPerimetro_toracico");
        tbxInd_masa = (Textbox) form.getFellow("tbxInd_masa");
        tbxConducta_motora = (Textbox) form.getFellow("tbxConducta_motora");
        tbxConducta_sensorial = (Textbox) form
                .getFellow("tbxConducta_sensorial");
        tbxConducta_cognitiva = (Textbox) form
                .getFellow("tbxConducta_cognitiva");
        tbxConducta_lenguaje = (Textbox) form.getFellow("tbxConducta_lenguaje");
        tbxPeso_edad = (Textbox) form.getFellow("tbxPeso_edad");
        tbxTalla_edad = (Textbox) form.getFellow("tbxTalla_edad");
        lbxTipo_peso = (Listbox) form.getFellow("lbxTipo_peso");
        lbxTipo_talla = (Listbox) form.getFellow("lbxTipo_talla");
        tbxPeso_talla = (Textbox) form.getFellow("tbxPeso_talla");
        lbxTipo_peso_talla = (Listbox) form.getFellow("lbxTipo_peso_talla");
        lbxTendencia_peso = (Listbox) form.getFellow("lbxTendencia_peso");
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
        tbxEvaluacion = (Textbox) form.getFellow("tbxEvaluacion");

    }

    public void initAlteracion_menor() throws Exception {

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
        listarElementoListbox(lbxMenor, false);
        listarElementoListbox(lbxNo_hnos, false);
        listarElementoListbox(lbxHnos_muertos, false);
        listarElementoListbox(lbxEmbarazo, false);
        listarElementoListbox(lbxTiempo_leche, false);
        listarElementoListbox(lbxTiempo_alimentacion, false);
        listarElementoListbox(lbxTiempo_destete, false);
        listarElementoListbox(lbxTiempo_cabeza, false);
        listarElementoListbox(lbxTiempo_sonrisa, false);
        listarElementoListbox(lbxTiempo_balbuceo, false);
        listarElementoListbox(lbxTiempo_voltear, false);
        listarElementoListbox(lbxTiempo_sentado, false);
        listarElementoListbox(lbxTiempo_objetos, false);
        listarElementoListbox(lbxTiempo_silabas, false);
        listarElementoListbox(lbxTiempo_sento_solo, false);
        listarElementoListbox(lbxTiempo_palabra_clara, false);
        listarElementoListbox(lbxTiempo_pasa_objetos, false);
        listarElementoListbox(lbxTiempo_gatear, false);
        listarElementoListbox(lbxTiempo_pies_sostenidos, false);
        listarElementoListbox(lbxTiempo_saca_objetos, false);
        listarElementoListbox(lbxTiempo_entiende, false);
        listarElementoListbox(lbxTipo_peso, false);
        listarElementoListbox(lbxTipo_talla, false);
        listarElementoListbox(lbxTipo_peso_talla, false);
        listarElementoListbox(lbxTendencia_peso, false);
        // listarElementoListbox(lbxCodigo_consulta_pyp,true);

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

        //log.info(mensaje);
        return valida;
    }

    private boolean vaidarIgualdad(String... in) {
        Map<String, Object> map = new HashMap<String, Object>();
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

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            getServiceLocator().getAlteracion_menorService().setLimit(
                    "limit 25 offset 0");

            List<Alteracion_menor> lista_datos = getServiceLocator()
                    .getAlteracion_menorService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Alteracion_menor alteracion_menor : lista_datos) {
                rowsResultado.appendChild(crearFilas(alteracion_menor, this));
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

        final Alteracion_menor alteracion_menor = (Alteracion_menor) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(alteracion_menor.getCodigo_historia() + ""));
        fila.appendChild(new Label(alteracion_menor.getIdentificacion() + ""));
        fila.appendChild(new Label(alteracion_menor.getFecha_inicial() + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {

                mostrarDatos(alteracion_menor);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        // img.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
        img.setSrc("/images/eliminar.gif");
        img.setTooltiptext("Eliminar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                Messagebox.show(
                        "Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener<Event>() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    eliminarDatos(alteracion_menor);
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

                Map<String, Object> datos = new HashMap<String, Object>();

                Alteracion_menor alteracion_menor = new Alteracion_menor();
                alteracion_menor.setCodigo_empresa(empresa.getCodigo_empresa());
                alteracion_menor.setCodigo_sucursal(sucursal
                        .getCodigo_sucursal());
                alteracion_menor.setCodigo_historia(tbxCodigo_historia
                        .getValue());
                alteracion_menor
                        .setIdentificacion(tbxIdentificacion.getValue());
                alteracion_menor.setFecha_inicial(new Timestamp(
                        dtbxFecha_inicial.getValue().getTime()));
                alteracion_menor.setMadre(tbxMadre.getValue());
                alteracion_menor.setPadre(tbxPadre.getValue());
                alteracion_menor.setPaterno_medicos(tbxPaterno_medicos
                        .getValue());
                alteracion_menor.setPaterno_alergicos(tbxPaterno_alergicos
                        .getValue());
                alteracion_menor.setMaterno_medicos(tbxMaterno_medicos
                        .getValue());
                alteracion_menor.setMaterno_alergicos(tbxMaterno_alergicos
                        .getValue());
                alteracion_menor.setObstetricos_g(tbxObstetricos_g.getValue());
                alteracion_menor.setObstetricos_p(tbxObstetricos_p.getValue());
                alteracion_menor.setObstetricos_a(tbxObstetricos_a.getValue());
                alteracion_menor.setObstetricos_c(tbxObstetricos_c.getValue());
                alteracion_menor.setOtros_antecedentes(tbxOtros_antecedentes
                        .getValue());
                alteracion_menor.setMenor(lbxMenor.getSelectedItem().getValue()
                        .toString());
                alteracion_menor.setNo_hnos(lbxNo_hnos.getSelectedItem()
                        .getValue().toString());
                alteracion_menor.setHnos_muertos(lbxHnos_muertos
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setCausa(tbxCausa.getValue());
                alteracion_menor.setEmbarazo(lbxEmbarazo.getSelectedItem()
                        .getValue().toString());
                alteracion_menor.setControl_prenatal(rdbControl_prenatal
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setEdad_madre(tbxEdad_madre.getValue());
                alteracion_menor
                        .setComplicaciones(tbxComplicaciones.getValue());
                alteracion_menor.setSemanas_gestion(tbxSemanas_gestion
                        .getValue());
                alteracion_menor.setVagina(chbVagina.isChecked());
                alteracion_menor.setCesaria(chbCesaria.isChecked());
                alteracion_menor.setPresentacion(tbxPresentacion.getValue());
                alteracion_menor.setParto_institucional(rdbParto_institucional
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setInstitucion(tbxInstitucion.getValue());
                alteracion_menor
                        .setComplicaciones_parto(tbxComplicaciones_parto
                                .getValue());
                alteracion_menor.setPeso_nacer(tbxPeso_nacer.getValue());
                alteracion_menor.setTalla_nacer(tbxTalla_nacer.getValue());
                alteracion_menor.setApgar(tbxApgar.getValue());
                alteracion_menor.setApgar5(tbxApgar5.getValue());
                alteracion_menor
                        .setComplicaciones_postparto(tbxComplicaciones_postparto
                                .getValue());
                alteracion_menor.setReanimacion(rdbReanimacion
                        .getSelectedItem().getValue().toString());
                alteracion_menor
                        .setHospitalizacion_neonatal(rdbHospitalizacion_neonatal
                                .getSelectedItem().getValue().toString());
                alteracion_menor.setHospitalizaciones(tbxHospitalizaciones
                        .getValue());
                alteracion_menor.setMedicos(tbxMedicos.getValue());
                alteracion_menor.setQuirurgicos(tbxQuirurgicos.getValue());
                alteracion_menor.setTraumaticos(tbxTraumaticos.getValue());
                alteracion_menor.setMedicacion_actual(tbxMedicacion_actual
                        .getValue());
                alteracion_menor.setLactancia(rdbLactancia.getSelectedItem()
                        .getValue().toString());
                alteracion_menor.setEdad_leche(tbxEdad_leche.getValue());
                alteracion_menor.setTiempo_leche(lbxTiempo_leche
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setCual_leche(tbxCual_leche.getValue());
                alteracion_menor.setEdad_alimentacion(tbxEdad_alimentacion
                        .getValue());
                alteracion_menor.setTiempo_alimentacion(lbxTiempo_alimentacion
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setCual_alimento(tbxCual_alimento.getValue());
                alteracion_menor.setEdad_destete(tbxEdad_destete.getValue());
                alteracion_menor.setTiempo_destete(lbxTiempo_destete
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setAlimentacion_actual(tbxAlimentacion_actual
                        .getValue());
                alteracion_menor
                        .setTranstornos_alimenticios(tbxTranstornos_alimenticios
                                .getValue());
                alteracion_menor.setCabeza(chbCabeza.isChecked());
                alteracion_menor.setEdad_cabeza(tbxEdad_cabeza.getValue());
                alteracion_menor.setTiempo_cabeza(lbxTiempo_cabeza
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setSonrisa(chbSonrisa.isChecked());
                alteracion_menor.setEdad_sonrisa(tbxEdad_sonrisa.getValue());
                alteracion_menor.setTiempo_sonrisa(lbxTiempo_sonrisa
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setBalbuceo(chbBalbuceo.isChecked());
                alteracion_menor.setEdad_balbuceo(tbxEdad_balbuceo.getValue());
                alteracion_menor.setTiempo_balbuceo(lbxTiempo_balbuceo
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setVoltear(chbVoltear.isChecked());
                alteracion_menor.setEdad_voltear(tbxEdad_voltear.getValue());
                alteracion_menor.setTiempo_voltear(lbxTiempo_voltear
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setSentado(chbSentado.isChecked());
                alteracion_menor.setEdad_sentado(tbxEdad_sentado.getValue());
                alteracion_menor.setTiempo_sentado(lbxTiempo_sentado
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setObjetos(chbObjetos.isChecked());
                alteracion_menor.setEdad_objetos(tbxEdad_objetos.getValue());
                alteracion_menor.setTiempo_objetos(lbxTiempo_objetos
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setSilabas(chbSilabas.isChecked());
                alteracion_menor.setEdad_silabas(tbxEdad_silabas.getValue());
                alteracion_menor.setTiempo_silabas(lbxTiempo_silabas
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setSento_solo(chbSento_solo.isChecked());
                alteracion_menor.setEdad_sento_solo(tbxEdad_sento_solo
                        .getValue());
                alteracion_menor.setTiempo_sento_solo(lbxTiempo_sento_solo
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setPalabra_clara(chbPalabra_clara.isChecked());
                alteracion_menor.setEdad_palabra_clara(tbxEdad_palabra_clara
                        .getValue());
                alteracion_menor
                        .setTiempo_palabra_clara(lbxTiempo_palabra_clara
                                .getSelectedItem().getValue().toString());
                alteracion_menor.setPasa_objetos(chbPasa_objetos.isChecked());
                alteracion_menor.setEdad_pasa_objetos(tbxEdad_pasa_objetos
                        .getValue());
                alteracion_menor.setTiempo_pasa_objetos(lbxTiempo_pasa_objetos
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setGatear(chbGatear.isChecked());
                alteracion_menor.setEdad_gatear(tbxEdad_gatear.getValue());
                alteracion_menor.setTiempo_gatear(lbxTiempo_gatear
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setPies_sostenidos(chbPies_sostenidos
                        .isChecked());
                alteracion_menor
                        .setEdad_pies_sostenidos(tbxEdad_pies_sostenidos
                                .getValue());
                alteracion_menor
                        .setTiempo_pies_sostenidos(lbxTiempo_pies_sostenidos
                                .getSelectedItem().getValue().toString());
                alteracion_menor.setSaca_objetos(chbSaca_objetos.isChecked());
                alteracion_menor.setEdad_saca_objetos(tbxEdad_saca_objetos
                        .getValue());
                alteracion_menor.setTiempo_saca_objetos(lbxTiempo_saca_objetos
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setEntiende(chbEntiende.isChecked());
                alteracion_menor.setEdad_entiende(tbxEdad_entiende.getValue());
                alteracion_menor.setTiempo_entiende(lbxTiempo_entiende
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setEnfermedad_actual(tbxEnfermedad_actual
                        .getValue());
                alteracion_menor.setCardiaca(tbxCardiaca.getValue());
                alteracion_menor.setRespiratoria(tbxRespiratoria.getValue());
                alteracion_menor.setPeso(tbxPeso.getValue());
                alteracion_menor.setTalla(tbxTalla.getValue());
                alteracion_menor.setTempo(tbxTempo.getValue());
                alteracion_menor.setPerimetro_cefalico(tbxPerimetro_cefalico
                        .getValue());
                alteracion_menor.setPerimetro_branquial(tbxPerimetro_branquial
                        .getValue());
                alteracion_menor.setPerimetro_toracico(tbxPerimetro_toracico
                        .getValue());
                alteracion_menor.setInd_masa(tbxInd_masa.getValue());
                alteracion_menor.setConducta_motora(tbxConducta_motora
                        .getValue());
                alteracion_menor.setConducta_sensorial(tbxConducta_sensorial
                        .getValue());
                alteracion_menor.setConducta_cognitiva(tbxConducta_cognitiva
                        .getValue());
                alteracion_menor.setConducta_lenguaje(tbxConducta_lenguaje
                        .getValue());
                alteracion_menor.setPeso_edad(tbxPeso_edad.getValue());
                alteracion_menor.setTalla_edad(tbxTalla_edad.getValue());
                alteracion_menor.setTipo_peso(lbxTipo_peso.getSelectedItem()
                        .getValue().toString());
                alteracion_menor.setTipo_talla(lbxTipo_talla.getSelectedItem()
                        .getValue().toString());
                alteracion_menor.setPeso_talla(tbxPeso_talla.getValue());
                alteracion_menor.setTipo_peso_talla(lbxTipo_peso_talla
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setTendencia_peso(lbxTendencia_peso
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setCodigo_consulta_pyp(lbxCodigo_consulta_pyp
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setFinalidad_cons(lbxFinalidad_cons
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setCausas_externas(lbxCausas_externas
                        .getSelectedItem().getValue().toString());
                alteracion_menor.setTipo_disnostico(lbxTipo_disnostico
                        .getSelectedItem().getValue().toString());
                alteracion_menor
                        .setTipo_principal(tbxTipo_principal.getValue());
                alteracion_menor.setTipo_relacionado_1(tbxTipo_relacionado_1
                        .getValue());
                alteracion_menor.setTipo_relacionado_2(tbxTipo_relacionado_2
                        .getValue());
                alteracion_menor.setTipo_relacionado_3(tbxTipo_relacionado_3
                        .getValue());
                alteracion_menor.setTratamiento(tbxTratamiento.getValue());
                alteracion_menor.setEvaluacion(tbxEvaluacion.getValue());

                datos.put("codigo_historia", alteracion_menor);
                datos.put("accion", tbxAccion.getText());

                alteracion_menor = getServiceLocator()
                        .getAlteracion_menorService().guardar(datos);

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
        Alteracion_menor alteracion_menor = (Alteracion_menor) obj;
        try {
            tbxCodigo_historia.setValue(alteracion_menor.getCodigo_historia());
            dtbxFecha_inicial.setValue(alteracion_menor.getFecha_inicial());

            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(alteracion_menor.getCodigo_empresa());
            paciente.setCodigo_sucursal(alteracion_menor.getCodigo_sucursal());
            paciente.setNro_identificacion(alteracion_menor.getIdentificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            Elemento elemento = new Elemento();
            elemento.setCodigo(paciente.getSexo());
            elemento.setTipo("sexo");
            elemento = getServiceLocator().getElementoService().consultar(
                    elemento);

            tbxIdentificacion.setValue(alteracion_menor.getIdentificacion());
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
            contratos.setCodigo_empresa(alteracion_menor.getCodigo_empresa());
            contratos.setCodigo_sucursal(alteracion_menor.getCodigo_sucursal());
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

            tbxMadre.setValue(alteracion_menor.getMadre());
            tbxPadre.setValue(alteracion_menor.getPadre());
            tbxPaterno_medicos.setValue(alteracion_menor.getPaterno_medicos());
            tbxPaterno_alergicos.setValue(alteracion_menor
                    .getPaterno_alergicos());
            tbxMaterno_medicos.setValue(alteracion_menor.getMaterno_medicos());
            tbxMaterno_alergicos.setValue(alteracion_menor
                    .getMaterno_alergicos());
            tbxObstetricos_g.setValue(alteracion_menor.getObstetricos_g());
            tbxObstetricos_p.setValue(alteracion_menor.getObstetricos_p());
            tbxObstetricos_a.setValue(alteracion_menor.getObstetricos_a());
            tbxObstetricos_c.setValue(alteracion_menor.getObstetricos_c());
            tbxOtros_antecedentes.setValue(alteracion_menor
                    .getOtros_antecedentes());
            for (int i = 0; i < lbxMenor.getItemCount(); i++) {
                Listitem listitem = lbxMenor.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getMenor())) {
                    listitem.setSelected(true);
                    i = lbxMenor.getItemCount();
                }
            }
            for (int i = 0; i < lbxNo_hnos.getItemCount(); i++) {
                Listitem listitem = lbxNo_hnos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getNo_hnos())) {
                    listitem.setSelected(true);
                    i = lbxNo_hnos.getItemCount();
                }
            }
            for (int i = 0; i < lbxHnos_muertos.getItemCount(); i++) {
                Listitem listitem = lbxHnos_muertos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getHnos_muertos())) {
                    listitem.setSelected(true);
                    i = lbxHnos_muertos.getItemCount();
                }
            }
            tbxCausa.setValue(alteracion_menor.getCausa());
            for (int i = 0; i < lbxEmbarazo.getItemCount(); i++) {
                Listitem listitem = lbxEmbarazo.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getEmbarazo())) {
                    listitem.setSelected(true);
                    i = lbxEmbarazo.getItemCount();
                }
            }
            Radio radio = (Radio) rdbControl_prenatal
                    .getFellow("Control_prenatal"
                            + alteracion_menor.getControl_prenatal());
            radio.setChecked(true);
            tbxEdad_madre.setValue(alteracion_menor.getEdad_madre());
            tbxComplicaciones.setValue(alteracion_menor.getComplicaciones());
            tbxSemanas_gestion.setValue(alteracion_menor.getSemanas_gestion());
            chbVagina.setChecked(alteracion_menor.getVagina());
            chbCesaria.setChecked(alteracion_menor.getCesaria());
            tbxPresentacion.setValue(alteracion_menor.getPresentacion());
            Radio radio1 = (Radio) rdbParto_institucional
                    .getFellow("Parto_institucional"
                            + alteracion_menor.getParto_institucional());
            radio1.setChecked(true);
            tbxInstitucion.setValue(alteracion_menor.getInstitucion());
            tbxComplicaciones_parto.setValue(alteracion_menor
                    .getComplicaciones_parto());
            tbxPeso_nacer.setValue(alteracion_menor.getPeso_nacer());
            tbxTalla_nacer.setValue(alteracion_menor.getTalla_nacer());
            tbxApgar.setValue(alteracion_menor.getApgar());
            tbxApgar5.setValue(alteracion_menor.getApgar5());
            tbxComplicaciones_postparto.setValue(alteracion_menor
                    .getComplicaciones_postparto());
            Radio radio2 = (Radio) rdbReanimacion.getFellow("Reanimacion"
                    + alteracion_menor.getReanimacion());
            radio2.setChecked(true);
            Radio radio3 = (Radio) rdbHospitalizacion_neonatal
                    .getFellow("Hospitalizacion_neonatal"
                            + alteracion_menor.getHospitalizacion_neonatal());
            radio3.setChecked(true);
            tbxHospitalizaciones.setValue(alteracion_menor
                    .getHospitalizaciones());
            tbxMedicos.setValue(alteracion_menor.getMedicos());
            tbxQuirurgicos.setValue(alteracion_menor.getQuirurgicos());
            tbxTraumaticos.setValue(alteracion_menor.getTraumaticos());
            tbxMedicacion_actual.setValue(alteracion_menor
                    .getMedicacion_actual());
            Radio radio4 = (Radio) rdbLactancia.getFellow("Lactancia"
                    + alteracion_menor.getLactancia());
            radio4.setChecked(true);
            tbxEdad_leche.setValue(alteracion_menor.getEdad_leche());
            for (int i = 0; i < lbxTiempo_leche.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_leche.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_leche())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_leche.getItemCount();
                }
            }
            tbxCual_leche.setValue(alteracion_menor.getCual_leche());
            tbxEdad_alimentacion.setValue(alteracion_menor
                    .getEdad_alimentacion());
            for (int i = 0; i < lbxTiempo_alimentacion.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_alimentacion.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_alimentacion())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_alimentacion.getItemCount();
                }
            }
            tbxCual_alimento.setValue(alteracion_menor.getCual_alimento());
            tbxEdad_destete.setValue(alteracion_menor.getEdad_destete());
            for (int i = 0; i < lbxTiempo_destete.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_destete.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_destete())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_destete.getItemCount();
                }
            }
            tbxAlimentacion_actual.setValue(alteracion_menor
                    .getAlimentacion_actual());
            tbxTranstornos_alimenticios.setValue(alteracion_menor
                    .getTranstornos_alimenticios());
            chbCabeza.setChecked(alteracion_menor.getCabeza());
            tbxEdad_cabeza.setValue(alteracion_menor.getEdad_cabeza());
            for (int i = 0; i < lbxTiempo_cabeza.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_cabeza.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_cabeza())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_cabeza.getItemCount();
                }
            }
            chbSonrisa.setChecked(alteracion_menor.getSonrisa());
            tbxEdad_sonrisa.setValue(alteracion_menor.getEdad_sonrisa());
            for (int i = 0; i < lbxTiempo_sonrisa.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_sonrisa.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_sonrisa())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_sonrisa.getItemCount();
                }
            }
            chbBalbuceo.setChecked(alteracion_menor.getBalbuceo());
            tbxEdad_balbuceo.setValue(alteracion_menor.getEdad_balbuceo());
            for (int i = 0; i < lbxTiempo_balbuceo.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_balbuceo.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_balbuceo())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_balbuceo.getItemCount();
                }
            }
            chbVoltear.setChecked(alteracion_menor.getVoltear());
            tbxEdad_voltear.setValue(alteracion_menor.getEdad_voltear());
            for (int i = 0; i < lbxTiempo_voltear.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_voltear.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_voltear())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_voltear.getItemCount();
                }
            }
            chbSentado.setChecked(alteracion_menor.getSentado());
            tbxEdad_sentado.setValue(alteracion_menor.getEdad_sentado());
            for (int i = 0; i < lbxTiempo_sentado.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_sentado.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_sentado())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_sentado.getItemCount();
                }
            }
            chbObjetos.setChecked(alteracion_menor.getObjetos());
            tbxEdad_objetos.setValue(alteracion_menor.getEdad_objetos());
            for (int i = 0; i < lbxTiempo_objetos.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_objetos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_objetos())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_objetos.getItemCount();
                }
            }
            chbSilabas.setChecked(alteracion_menor.getSilabas());
            tbxEdad_silabas.setValue(alteracion_menor.getEdad_silabas());
            for (int i = 0; i < lbxTiempo_silabas.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_silabas.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_silabas())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_silabas.getItemCount();
                }
            }
            chbSento_solo.setChecked(alteracion_menor.getSento_solo());
            tbxEdad_sento_solo.setValue(alteracion_menor.getEdad_sento_solo());
            for (int i = 0; i < lbxTiempo_sento_solo.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_sento_solo.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_sento_solo())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_sento_solo.getItemCount();
                }
            }
            chbPalabra_clara.setChecked(alteracion_menor.getPalabra_clara());
            tbxEdad_palabra_clara.setValue(alteracion_menor
                    .getEdad_palabra_clara());
            for (int i = 0; i < lbxTiempo_palabra_clara.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_palabra_clara.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_palabra_clara())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_palabra_clara.getItemCount();
                }
            }
            chbPasa_objetos.setChecked(alteracion_menor.getPasa_objetos());
            tbxEdad_pasa_objetos.setValue(alteracion_menor
                    .getEdad_pasa_objetos());
            for (int i = 0; i < lbxTiempo_pasa_objetos.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_pasa_objetos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_pasa_objetos())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_pasa_objetos.getItemCount();
                }
            }
            chbGatear.setChecked(alteracion_menor.getGatear());
            tbxEdad_gatear.setValue(alteracion_menor.getEdad_gatear());
            for (int i = 0; i < lbxTiempo_gatear.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_gatear.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_gatear())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_gatear.getItemCount();
                }
            }
            chbPies_sostenidos
                    .setChecked(alteracion_menor.getPies_sostenidos());
            tbxEdad_pies_sostenidos.setValue(alteracion_menor
                    .getEdad_pies_sostenidos());
            for (int i = 0; i < lbxTiempo_pies_sostenidos.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_pies_sostenidos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_pies_sostenidos())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_pies_sostenidos.getItemCount();
                }
            }
            chbSaca_objetos.setChecked(alteracion_menor.getSaca_objetos());
            tbxEdad_saca_objetos.setValue(alteracion_menor
                    .getEdad_saca_objetos());
            for (int i = 0; i < lbxTiempo_saca_objetos.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_saca_objetos.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_saca_objetos())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_saca_objetos.getItemCount();
                }
            }
            chbEntiende.setChecked(alteracion_menor.getEntiende());
            tbxEdad_entiende.setValue(alteracion_menor.getEdad_entiende());
            for (int i = 0; i < lbxTiempo_entiende.getItemCount(); i++) {
                Listitem listitem = lbxTiempo_entiende.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTiempo_entiende())) {
                    listitem.setSelected(true);
                    i = lbxTiempo_entiende.getItemCount();
                }
            }
            tbxEnfermedad_actual.setValue(alteracion_menor
                    .getEnfermedad_actual());
            tbxCardiaca.setValue(alteracion_menor.getCardiaca());
            tbxRespiratoria.setValue(alteracion_menor.getRespiratoria());
            tbxPeso.setValue(alteracion_menor.getPeso());
            tbxTalla.setValue(alteracion_menor.getTalla());
            tbxTempo.setValue(alteracion_menor.getTempo());
            tbxPerimetro_cefalico.setValue(alteracion_menor
                    .getPerimetro_cefalico());
            tbxPerimetro_branquial.setValue(alteracion_menor
                    .getPerimetro_branquial());
            tbxPerimetro_toracico.setValue(alteracion_menor
                    .getPerimetro_toracico());
            tbxInd_masa.setValue(alteracion_menor.getInd_masa());
            tbxConducta_motora.setValue(alteracion_menor.getConducta_motora());
            tbxConducta_sensorial.setValue(alteracion_menor
                    .getConducta_sensorial());
            tbxConducta_cognitiva.setValue(alteracion_menor
                    .getConducta_cognitiva());
            tbxConducta_lenguaje.setValue(alteracion_menor
                    .getConducta_lenguaje());
            tbxPeso_edad.setValue(alteracion_menor.getPeso_edad());
            tbxTalla_edad.setValue(alteracion_menor.getTalla_edad());
            for (int i = 0; i < lbxTipo_peso.getItemCount(); i++) {
                Listitem listitem = lbxTipo_peso.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTipo_peso())) {
                    listitem.setSelected(true);
                    i = lbxTipo_peso.getItemCount();
                }
            }
            for (int i = 0; i < lbxTipo_talla.getItemCount(); i++) {
                Listitem listitem = lbxTipo_talla.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTipo_talla())) {
                    listitem.setSelected(true);
                    i = lbxTipo_talla.getItemCount();
                }
            }
            tbxPeso_talla.setValue(alteracion_menor.getPeso_talla());
            for (int i = 0; i < lbxTipo_peso_talla.getItemCount(); i++) {
                Listitem listitem = lbxTipo_peso_talla.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTipo_peso_talla())) {
                    listitem.setSelected(true);
                    i = lbxTipo_peso_talla.getItemCount();
                }
            }
            for (int i = 0; i < lbxTendencia_peso.getItemCount(); i++) {
                Listitem listitem = lbxTendencia_peso.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTendencia_peso())) {
                    listitem.setSelected(true);
                    i = lbxTendencia_peso.getItemCount();
                }
            }
            for (int i = 0; i < lbxFinalidad_cons.getItemCount(); i++) {
                Listitem listitem = lbxFinalidad_cons.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getFinalidad_cons())) {
                    listitem.setSelected(true);
                    i = lbxFinalidad_cons.getItemCount();
                }
            }
            seleccionarProgramaPyp();

            for (int i = 0; i < lbxCausas_externas.getItemCount(); i++) {
                Listitem listitem = lbxCausas_externas.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getCausas_externas())) {
                    listitem.setSelected(true);
                    i = lbxCausas_externas.getItemCount();
                }
            }
            for (int i = 0; i < lbxCodigo_consulta_pyp.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_consulta_pyp.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getCodigo_consulta_pyp())) {
                    listitem.setSelected(true);
                    i = lbxCodigo_consulta_pyp.getItemCount();
                }
            }
            for (int i = 0; i < lbxTipo_disnostico.getItemCount(); i++) {
                Listitem listitem = lbxTipo_disnostico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(alteracion_menor.getTipo_disnostico())) {
                    listitem.setSelected(true);
                    i = lbxTipo_disnostico.getItemCount();
                }
            }

            Cie cie = new Cie();
            cie.setCodigo(alteracion_menor.getTipo_principal());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);
            tbxTipo_principal.setValue(alteracion_menor.getTipo_principal());
            tbxNomDx.setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 1 */
            cie = new Cie();
            cie.setCodigo(alteracion_menor.getTipo_relacionado_1());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_1.setValue(alteracion_menor
                    .getTipo_relacionado_1());
            tbxNomDxRelacionado_1
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 2 */
            cie = new Cie();
            cie.setCodigo(alteracion_menor.getTipo_relacionado_2());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_2.setValue(alteracion_menor
                    .getTipo_relacionado_2());
            tbxNomDxRelacionado_2
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 3 */
            cie = new Cie();
            cie.setCodigo(alteracion_menor.getTipo_relacionado_3());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_3.setValue(alteracion_menor
                    .getTipo_relacionado_3());
            tbxNomDxRelacionado_3
                    .setValue((cie != null ? cie.getNombre() : ""));

            tbxTratamiento.setValue(alteracion_menor.getTratamiento());
            tbxEvaluacion.setValue(alteracion_menor.getEvaluacion());

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
        Alteracion_menor alteracion_menor = (Alteracion_menor) obj;
        try {
            int result = getServiceLocator().getAlteracion_menorService()
                    .eliminar(alteracion_menor);
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
                .getDepartamentosService().listar(new HashMap<String, Object>());

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
        Map<String, Object> parameters = new HashMap<String, Object>();
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
            Map<String, Object> parameters = new HashMap<String, Object>();
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

    public void seleccionarProgramaPyp() {
        String codigo_finalidad = ""
                + lbxFinalidad_cons.getSelectedItem().getValue();
        if (!codigo_finalidad.trim().isEmpty()) {
            Map<String, Object> parameters = new HashMap<String, Object>();
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
