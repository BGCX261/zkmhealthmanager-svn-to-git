/*
 * ficha_epidemiologia_n19Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Ficha_epidemiologia_n19;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Ficha_epidemiologia_n19Action extends
        FichaEpidemiologiaModel<Ficha_epidemiologia_n19> {

    private static Logger log = Logger
            .getLogger(Ficha_epidemiologia_n19Action.class);

    // Componentes //
    @View
    private Listbox lbxParameter;
    @View
    private Textbox tbxValue;
    @View
    private Textbox tbxAccion;
    @View
    private Borderlayout groupboxEditar;
    @View
    private Groupbox groupboxConsulta;
    @View
    private Rows rowsResultado;
    @View
    private Grid gridResultado;

    @View
    private Textbox tbxCodigo_ficha;
    @View
    private Textbox tbxIdentificacion;

    @View
    private Textbox tbxNombrePaciente;
    @View
    private Textbox tbxTipo_identificacion;

    @View
    private Textbox tbxAseguradora;
    @View
    private Textbox tbxNombre_aseguradora;

    @View
    private Datebox dtbxFecha_creacion;
    @View
    private Textbox tbxCodigo_diagnostico;
    @View
    private Radiogroup rdbTipo_leucemia;
    @View
    private Checkbox chbInfeccion;
    @View
    private Checkbox chbSondrome_lisis_tumoral;
    @View
    private Checkbox chbTrombosis;
    @View
    private Checkbox chbHemorragia;
    @View
    private Checkbox chbCrisis_convulsiva;
    @View
    private Checkbox chbDesconocido;
    @View
    private Checkbox chbOtras_complicaciones;
    @View
    private Textbox tbxCual_complicaciones;
    @View
    private Datebox dtbxHematico_fecha_toma;
    @View
    private Datebox dtbxHematico_fecha_recepcion;
    @View
    private Datebox dtbxHematico_fecha_resultado;
    @View
    private Intbox ibxHematico_valor;
    @View
    private Datebox dtbxFrotis_fecha_toma;
    @View
    private Datebox dtbxFrotis_fecha_recepcion;
    @View
    private Datebox dtbxFrotis_fecha_resultado;
    @View
    private Intbox ibxFrotis_valor;
    @View
    private Datebox dtbxMedula_fecha_toma;
    @View
    private Datebox dtbxMedula_fecha_recepcion;
    @View
    private Datebox dtbxMedula_fecha_resultado;
    @View
    private Intbox ibxMedula_valor;
    @View
    private Radiogroup rdbSitio_defucion;
    @View
    private Textbox tbxOtro_sitio;
    @View
    private Bandbox tbxImpresion_final;
    @View
    private Textbox tbxNombre_impresion;
    @View
    private Radiogroup rdbCausa_muerte;
    @View
    private Textbox tbxCausa_antecedentes;
    @View
    private Datebox dtbxFecha_tratamiento;
    @View
    private Intbox ibxProbable_dias;
    @View
    private Intbox ibxProbable_mes;
    @View
    private Intbox ibxDefinitivo_dias;
    @View
    private Intbox ibxDefinitivo_mes;
    @View
    private Intbox ibxTratamiento_dias;
    @View
    private Intbox ibxTratamiento_mes;
    @View
    private Intbox ibxTelefono;
    @View
    private Textbox tbxObservaciones;
    @View
    private Textbox tbxCodigo_medico;
    @View
    private Textbox tbxNombre_medico;
    private final String[] idsExcluyentes = {};

    @View
    private Toolbarbutton btGuardar;

    @View
    private Label lbOtro_sitio;
    @View
    private Label lbCual_complicaciones;

    private Admision admision;

    private Ficha_epidemiologia_historial ficha_seleccionada;

    @Override
    public void init() {
        try {
            listarCombos();
            obtenerAdmision(admision);
            FormularioUtil.inicializarRadiogroupsDefecto(this);

            if (ficha_seleccionada != null) {
                //log.info("consultar ficha-------> "+ficha_seleccionada);
                Ficha_epidemiologia_n19 ficha = new Ficha_epidemiologia_n19();
                ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
                ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
                ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
                ficha = (Ficha_epidemiologia_n19) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);

				//log.info("consultar ficha 19-------> "+ficha);
                mostrarDatos(ficha);
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    @Override
    public void params(Map<String, Object> map) {
        if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
            admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
            ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
        }
    }

    public void listarCombos() {
        listarParameter();
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("codigo_ficha");
        listitem.setLabel("código Ficha");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("identificacion");
        listitem.setLabel("Identificacion");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        groupboxConsulta.setVisible(!sw);
        groupboxEditar.setVisible(sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            buscarDatos();
            btGuardar.setVisible(false);
        } else {
            btGuardar.setVisible(true);
            dtbxFecha_tratamiento.setValue(null);
            dtbxFrotis_fecha_recepcion.setValue(null);
            dtbxFrotis_fecha_resultado.setValue(null);
            dtbxFrotis_fecha_toma.setValue(null);
            dtbxHematico_fecha_recepcion.setValue(null);
            dtbxHematico_fecha_resultado.setValue(null);
            dtbxHematico_fecha_toma.setValue(null);
            dtbxMedula_fecha_recepcion.setValue(null);
            dtbxMedula_fecha_resultado.setValue(null);
            dtbxMedula_fecha_toma.setValue(null);

            limpiarDatos();
            FormularioUtil.cargarRadiogroupsDefecto(this);
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
    }

    // Metodo para validar campos del formulario //
    public boolean validarFichaEpidemiologia() {

        boolean valida = true;

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
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            if (admision != null) {
                parameters.put("identificacion",
                        admision.getNro_identificacion());
            }

            getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
                    "limit 25 offset 0");

            List<Ficha_epidemiologia_n19> lista_datos = getServiceLocator()
                    .getFicha_epidemiologia_nnService().listar(
                            Ficha_epidemiologia_n19.class, parameters);
            rowsResultado.getChildren().clear();

            for (Ficha_epidemiologia_n19 ficha_epidemiologia_n19 : lista_datos) {
                rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n19,
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

        final Ficha_epidemiologia_n19 ficha_epidemiologia_n19 = (Ficha_epidemiologia_n19) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(ficha_epidemiologia_n19.getCodigo_ficha()
                + ""));
        fila.appendChild(new Label(ficha_epidemiologia_n19.getIdentificacion()
                + ""));
        fila.appendChild(new Label(ficha_epidemiologia_n19.getFecha_creacion()
                + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(ficha_epidemiologia_n19);
            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    // Metodo para guardar la informacion //
    public Ficha_epidemiologia_n19 obtenerFichaEpidemiologia() {

        Ficha_epidemiologia_n19 ficha_epidemiologia_n19 = new Ficha_epidemiologia_n19();
        ficha_epidemiologia_n19.setCodigo_empresa(empresa.getCodigo_empresa());
        ficha_epidemiologia_n19.setCodigo_sucursal(sucursal
                .getCodigo_sucursal());
        ficha_epidemiologia_n19.setCodigo_ficha(tbxCodigo_ficha.getValue());
        ficha_epidemiologia_n19.setIdentificacion(tbxIdentificacion.getValue());
        ficha_epidemiologia_n19.setFecha_creacion(new Timestamp(
                dtbxFecha_creacion.getValue().getTime()));
        ficha_epidemiologia_n19.setCodigo_diagnostico("Z000");
        ficha_epidemiologia_n19.setTipo_leucemia(rdbTipo_leucemia
                .getSelectedItem().getValue().toString());
        ficha_epidemiologia_n19.setInfeccion(chbInfeccion.isChecked());
        ficha_epidemiologia_n19
                .setSondrome_lisis_tumoral(chbSondrome_lisis_tumoral
                        .isChecked());
        ficha_epidemiologia_n19.setTrombosis(chbTrombosis.isChecked());
        ficha_epidemiologia_n19.setHemorragia(chbHemorragia.isChecked());
        ficha_epidemiologia_n19.setCrisis_convulsiva(chbCrisis_convulsiva
                .isChecked());
        ficha_epidemiologia_n19.setDesconocido(chbDesconocido.isChecked());
        ficha_epidemiologia_n19.setOtras_complicaciones(chbOtras_complicaciones
                .isChecked());
        ficha_epidemiologia_n19.setCual_complicaciones(tbxCual_complicaciones
                .getValue());

        if (dtbxHematico_fecha_toma.getValue() != null) {
            ficha_epidemiologia_n19.setHematico_fecha_toma(new Timestamp(
                    dtbxHematico_fecha_toma.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setHematico_fecha_toma(null);
        }

        if (dtbxHematico_fecha_recepcion.getValue() != null) {
            ficha_epidemiologia_n19.setHematico_fecha_recepcion(new Timestamp(
                    dtbxHematico_fecha_recepcion.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setHematico_fecha_recepcion(null);
        }

        if (dtbxHematico_fecha_resultado.getValue() != null) {
            ficha_epidemiologia_n19.setHematico_fecha_resultado(new Timestamp(
                    dtbxHematico_fecha_resultado.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setHematico_fecha_resultado(null);
        }

        ficha_epidemiologia_n19
                .setHematico_valor((ibxHematico_valor.getValue() != null ? ibxHematico_valor
                        .getValue() + ""
                        : ""));

        if (dtbxFrotis_fecha_toma.getValue() != null) {
            ficha_epidemiologia_n19.setFrotis_fecha_toma(new Timestamp(
                    dtbxFrotis_fecha_toma.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setFrotis_fecha_toma(null);
        }

        if (dtbxFrotis_fecha_recepcion.getValue() != null) {
            ficha_epidemiologia_n19.setFrotis_fecha_recepcion(new Timestamp(
                    dtbxFrotis_fecha_recepcion.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setFrotis_fecha_recepcion(null);
        }

        if (dtbxFrotis_fecha_resultado.getValue() != null) {
            ficha_epidemiologia_n19.setFrotis_fecha_resultado(new Timestamp(
                    dtbxFrotis_fecha_resultado.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setFrotis_fecha_resultado(null);
        }
        ficha_epidemiologia_n19
                .setFrotis_valor((ibxTelefono.getValue() != null ? ibxTelefono
                        .getValue() + "" : ""));

        if (dtbxMedula_fecha_toma.getValue() != null) {
            ficha_epidemiologia_n19.setMedula_fecha_toma(new Timestamp(
                    dtbxMedula_fecha_toma.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setMedula_fecha_toma(null);
        }

        if (dtbxMedula_fecha_recepcion.getValue() != null) {
            ficha_epidemiologia_n19.setMedula_fecha_recepcion(new Timestamp(
                    dtbxMedula_fecha_recepcion.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setMedula_fecha_recepcion(null);
        }

        if (dtbxMedula_fecha_resultado.getValue() != null) {
            ficha_epidemiologia_n19.setMedula_fecha_resultado(new Timestamp(
                    dtbxMedula_fecha_resultado.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setMedula_fecha_resultado(null);
        }
        ficha_epidemiologia_n19
                .setMedula_valor((ibxMedula_valor.getValue() != null ? ibxMedula_valor
                        .getValue() + ""
                        : ""));

        ficha_epidemiologia_n19.setSitio_defucion(rdbSitio_defucion
                .getSelectedItem().getValue().toString());
        ficha_epidemiologia_n19.setOtro_sitio(tbxOtro_sitio.getValue());
        ficha_epidemiologia_n19.setImpresion_final(tbxImpresion_final
                .getValue());
        ficha_epidemiologia_n19.setCausa_muerte(rdbCausa_muerte
                .getSelectedItem().getValue().toString());
        ficha_epidemiologia_n19.setCausa_antecedentes(tbxCausa_antecedentes
                .getValue());

        if (dtbxFecha_tratamiento.getValue() != null) {
            ficha_epidemiologia_n19.setFecha_tratamiento(new Timestamp(
                    dtbxFecha_tratamiento.getValue().getTime()));

        } else {
            ficha_epidemiologia_n19.setFecha_tratamiento(null);
        }

        ficha_epidemiologia_n19
                .setProbable_dias((ibxProbable_dias.getValue() != null ? ibxProbable_dias
                        .getValue() + ""
                        : ""));
        ficha_epidemiologia_n19
                .setProbable_mes((ibxProbable_mes.getValue() != null ? ibxProbable_mes
                        .getValue() + ""
                        : ""));
        ficha_epidemiologia_n19.setDefinitivo_dias((ibxDefinitivo_dias
                .getValue() != null ? ibxDefinitivo_dias.getValue() + "" : ""));
        ficha_epidemiologia_n19
                .setDefinitivo_mes((ibxDefinitivo_mes.getValue() != null ? ibxDefinitivo_mes
                        .getValue() + ""
                        : ""));
        ficha_epidemiologia_n19
                .setTratamiento_dias((ibxTratamiento_dias.getValue() != null ? ibxTratamiento_dias
                        .getValue() + ""
                        : ""));
        ficha_epidemiologia_n19.setTratamiento_mes((ibxTratamiento_mes
                .getValue() != null ? ibxTratamiento_mes.getValue() + "" : ""));
        ficha_epidemiologia_n19
                .setTelefono((ibxTelefono.getValue() != null ? ibxTelefono
                        .getValue() + "" : ""));
        ficha_epidemiologia_n19.setObservaciones(tbxObservaciones.getValue());
        ficha_epidemiologia_n19.setCodigo_medico(tbxCodigo_medico.getValue());
        ficha_epidemiologia_n19.setCreacion_date(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        ficha_epidemiologia_n19.setUltimo_update(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        ficha_epidemiologia_n19.setCreacion_user(usuarios.getCodigo()
                .toString());
        ficha_epidemiologia_n19.setDelete_date(null);
        ficha_epidemiologia_n19.setUltimo_user(usuarios.getCodigo().toString());
        ficha_epidemiologia_n19.setDelete_user(null);

        return ficha_epidemiologia_n19;
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Ficha_epidemiologia_n19 obj) throws Exception {
        Ficha_epidemiologia_n19 ficha_epidemiologia_n19 = (Ficha_epidemiologia_n19) obj;
        try {
            tbxCodigo_ficha.setValue(ficha_epidemiologia_n19.getCodigo_ficha());
            tbxIdentificacion.setValue(ficha_epidemiologia_n19
                    .getIdentificacion());

            obtenerAdmision(admision);

            FormularioUtil.deshabilitarComponentes(groupboxEditar, true, new String[]{});

            dtbxFecha_creacion.setValue(ficha_epidemiologia_n19
                    .getFecha_creacion());
            tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n19
                    .getCodigo_diagnostico());
            Utilidades.seleccionarRadio(rdbTipo_leucemia,
                    ficha_epidemiologia_n19.getTipo_leucemia());
            chbInfeccion.setChecked(ficha_epidemiologia_n19.getInfeccion());
            chbSondrome_lisis_tumoral.setChecked(ficha_epidemiologia_n19
                    .getSondrome_lisis_tumoral());
            chbTrombosis.setChecked(ficha_epidemiologia_n19.getTrombosis());
            chbHemorragia.setChecked(ficha_epidemiologia_n19.getHemorragia());
            chbCrisis_convulsiva.setChecked(ficha_epidemiologia_n19
                    .getCrisis_convulsiva());
            chbDesconocido.setChecked(ficha_epidemiologia_n19.getDesconocido());
            chbOtras_complicaciones.setChecked(ficha_epidemiologia_n19
                    .getOtras_complicaciones());

            if (ficha_epidemiologia_n19.getOtras_complicaciones()) {
                lbCual_complicaciones.setVisible(true);
                tbxCual_complicaciones.setVisible(true);
                tbxCual_complicaciones.setValue(ficha_epidemiologia_n19
                        .getCual_complicaciones());

            } else {
                lbCual_complicaciones.setVisible(false);
                tbxCual_complicaciones.setVisible(false);

            }

            dtbxHematico_fecha_toma.setValue(ficha_epidemiologia_n19
                    .getHematico_fecha_toma());
            dtbxHematico_fecha_recepcion.setValue(ficha_epidemiologia_n19
                    .getHematico_fecha_recepcion());
            dtbxHematico_fecha_resultado.setValue(ficha_epidemiologia_n19
                    .getHematico_fecha_resultado());
            ibxHematico_valor.setValue((ficha_epidemiologia_n19
                    .getHematico_valor() != null && !ficha_epidemiologia_n19
                    .getHematico_valor().isEmpty()) ? Integer
                    .parseInt(ficha_epidemiologia_n19.getHematico_valor())
                    : null);
            dtbxFrotis_fecha_toma.setValue(ficha_epidemiologia_n19
                    .getFrotis_fecha_toma());
            dtbxFrotis_fecha_recepcion.setValue(ficha_epidemiologia_n19
                    .getFrotis_fecha_recepcion());
            dtbxFrotis_fecha_resultado.setValue(ficha_epidemiologia_n19
                    .getFrotis_fecha_resultado());
            ibxFrotis_valor
                    .setValue((ficha_epidemiologia_n19.getFrotis_valor() != null && !ficha_epidemiologia_n19
                            .getFrotis_valor().isEmpty()) ? Integer
                            .parseInt(ficha_epidemiologia_n19.getFrotis_valor())
                            : null);
            dtbxMedula_fecha_toma.setValue(ficha_epidemiologia_n19
                    .getMedula_fecha_toma());
            dtbxMedula_fecha_recepcion.setValue(ficha_epidemiologia_n19
                    .getMedula_fecha_recepcion());
            dtbxMedula_fecha_resultado.setValue(ficha_epidemiologia_n19
                    .getMedula_fecha_resultado());
            ibxMedula_valor
                    .setValue((ficha_epidemiologia_n19.getMedula_valor() != null && !ficha_epidemiologia_n19
                            .getMedula_valor().isEmpty()) ? Integer
                            .parseInt(ficha_epidemiologia_n19.getMedula_valor())
                            : null);
            Utilidades.seleccionarRadio(rdbSitio_defucion,
                    ficha_epidemiologia_n19.getSitio_defucion());

            if (ficha_epidemiologia_n19.getSitio_defucion().equals("O")) {
                lbOtro_sitio.setVisible(true);
                tbxOtro_sitio.setVisible(true);
                tbxOtro_sitio.setValue(ficha_epidemiologia_n19.getOtro_sitio());

            } else {
                lbOtro_sitio.setVisible(false);
                tbxOtro_sitio.setVisible(false);

            }

            Cie cie = new Cie();
            cie.setCodigo(ficha_epidemiologia_n19.getImpresion_final());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);
            tbxImpresion_final.setValue(ficha_epidemiologia_n19
                    .getImpresion_final());
            tbxNombre_impresion.setValue((cie != null ? cie.getNombre() : ""));

            Utilidades.seleccionarRadio(rdbCausa_muerte,
                    ficha_epidemiologia_n19.getCausa_muerte());
            tbxCausa_antecedentes.setValue(ficha_epidemiologia_n19
                    .getCausa_antecedentes());
            dtbxFecha_tratamiento.setValue(ficha_epidemiologia_n19
                    .getFecha_tratamiento());
            ibxProbable_dias.setValue((ficha_epidemiologia_n19
                    .getProbable_dias() != null && !ficha_epidemiologia_n19
                    .getProbable_dias().isEmpty()) ? Integer
                    .parseInt(ficha_epidemiologia_n19.getProbable_dias())
                    : null);
            ibxProbable_mes
                    .setValue((ficha_epidemiologia_n19.getProbable_mes() != null && !ficha_epidemiologia_n19
                            .getProbable_mes().isEmpty()) ? Integer
                            .parseInt(ficha_epidemiologia_n19.getProbable_mes())
                            : null);
            ibxDefinitivo_dias.setValue((ficha_epidemiologia_n19
                    .getDefinitivo_dias() != null && !ficha_epidemiologia_n19
                    .getDefinitivo_dias().isEmpty()) ? Integer
                    .parseInt(ficha_epidemiologia_n19.getDefinitivo_dias())
                    : null);
            ibxDefinitivo_mes.setValue((ficha_epidemiologia_n19
                    .getDefinitivo_mes() != null && !ficha_epidemiologia_n19
                    .getDefinitivo_mes().isEmpty()) ? Integer
                    .parseInt(ficha_epidemiologia_n19.getDefinitivo_mes())
                    : null);
            ibxTratamiento_dias.setValue((ficha_epidemiologia_n19
                    .getTratamiento_dias() != null && !ficha_epidemiologia_n19
                    .getTratamiento_dias().isEmpty()) ? Integer
                    .parseInt(ficha_epidemiologia_n19.getTratamiento_dias())
                    : null);
            ibxTratamiento_mes.setValue((ficha_epidemiologia_n19
                    .getTratamiento_mes() != null && !ficha_epidemiologia_n19
                    .getTratamiento_mes().isEmpty()) ? Integer
                    .parseInt(ficha_epidemiologia_n19.getTratamiento_mes())
                    : null);
            ibxTelefono
                    .setValue((ficha_epidemiologia_n19.getTelefono() != null && !ficha_epidemiologia_n19
                            .getTelefono().isEmpty()) ? Integer
                            .parseInt(ficha_epidemiologia_n19.getTelefono())
                            : null);
            tbxObservaciones.setValue(ficha_epidemiologia_n19
                    .getObservaciones());
            tbxCodigo_medico.setValue(ficha_epidemiologia_n19
                    .getCodigo_medico());

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Ficha_epidemiologia_n19 ficha_epidemiologia_n19 = (Ficha_epidemiologia_n19) obj;
        try {
            int result = getServiceLocator().getFicha_epidemiologia_nnService()
                    .eliminar(ficha_epidemiologia_n19);
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

    public void mostrar_conRadio(ZKWindow form, Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            String valor = "O";
            FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup,
                    valor, abstractComponents);

        } catch (Exception e) {
			//  block
            //System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    public void mostrar_conCheck(ZKWindow form, Checkbox checkbox,
            AbstractComponent[] abstractComponents) {
        try {

            FormularioUtil.mostrarComponentes_conCheckbox(form, checkbox,
                    abstractComponents);

        } catch (Exception e) {
			//  block
            //System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    public void obtenerAdmision(Admision admision) {
        Paciente paciente = admision.getPaciente();
        tbxIdentificacion.setValue(admision.getNro_identificacion());
        tbxNombrePaciente.setValue(paciente.getNombreCompleto());
        tbxTipo_identificacion.setValue(paciente.getTipo_identificacion());

        tbxAseguradora.setValue(admision.getCodigo_administradora());
		//log.info("PACIENTE"+paciente);
        //log.info("ADMINISTRADORA"+admision.getCodigo_empresa()+" "+admision.getCodigo_sucursal()+" "+paciente.getCodigo_administradora());

        Administradora administradora = new Administradora();
        administradora.setCodigo_empresa(admision.getCodigo_empresa());
        administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
        administradora.setCodigo(admision.getCodigo_administradora());
        administradora = getServiceLocator().getAdministradoraService().consultar(administradora);
		//log.info("administradora"+administradora);

        tbxNombre_aseguradora.setValue(administradora.getNombre());
    }

    @Override
    public Ficha_epidemiologia_n19 consultarDatos(Map<String, Object> map,
            Ficha_epidemiologia ficha_epidemiologia) throws Exception {
//				Ficha_epidemiologia ficha = (Ficha_epidemiologia)ficha_epidemiologia;

				//log.info("-----------------");
				//log.info("map"+map);
        //log.info("ficha"+ficha);
        Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map.get("impresion_diagnostica");
        Cie_epidemiologia cie_epidemiologia = (Cie_epidemiologia) map.get("cie_epidemiologia");
        Admision admision = (Admision) map.get("admision");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("codigo_empresa", admision.getCodigo_empresa());
        parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
        parameters.put("identificacion", admision.getNro_identificacion());

        if (impresion_diagnostica != null) {
            parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
        } else {
            return null;
        }

        if (cie_epidemiologia != null) {
            parameters.put("codigo_diagnostico", cie_epidemiologia.getCodigo_cie());
        } else {
            return null;
        }

        getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");

        List<Ficha_epidemiologia_n19> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
                Ficha_epidemiologia_n19.class, parameters);

				//log.info("lista_datos"+lista_datos);
        if (!lista_datos.isEmpty()) {
            Ficha_epidemiologia_n19 ficha_n19 = lista_datos.get(lista_datos.size() - 1);

            return ficha_n19;
        } else {

            return null;
        }
    }

}
