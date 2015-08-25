/*
 * ficha_epidemiologia_n26Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n26;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Ficha_epidemiologia_n26Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n26> {

    private static Logger log = Logger.getLogger(Ficha_epidemiologia_n26Action.class);

    //Componentes //
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
    private Radiogroup rdbSitio_defucion;
    @View
    private Radiogroup rdbConvivencia;
    @View
    private Textbox tbxCual_convivencia;
    @View
    private Radiogroup rdbEscolaridad;
    @View
    private Radiogroup rdbRegularidad_fecundidad;
    @View
    private Intbox ibxGestiones;
    @View
    private Intbox ibxPartos;
    @View
    private Intbox ibxCesarias;
    @View
    private Intbox ibxMuertos;
    @View
    private Intbox ibxVivos;
    @View
    private Intbox ibxAbortos;
    @View
    private Checkbox chbNinguno;
    @View
    private Checkbox chbHipertension_cronica;
    @View
    private Checkbox chbCardiopatias;
    @View
    private Checkbox chbDiabetes;
    @View
    private Checkbox chbMola_hidatiforme;
    @View
    private Checkbox chbRn_pretermino;
    @View
    private Checkbox chbRn_bajo_peso;
    @View
    private Checkbox chbRn_macrosomicos;
    @View
    private Checkbox chbTrastorno_mental;
    @View
    private Checkbox chbObesidad;
    @View
    private Checkbox chbDesnutricion_cronica;
    @View
    private Checkbox chbIntergenesis;
    @View
    private Checkbox chbIts;
    @View
    private Checkbox chbVih;
    @View
    private Checkbox chbOtras_infecciones;
    @View
    private Checkbox chbRh_negativo;
    @View
    private Checkbox chbTabaquismo;
    @View
    private Checkbox chbAlcoholismo;
    @View
    private Checkbox chbSustancias_psicoactivas;
    @View
    private Checkbox chbDeficiencias;
    @View
    private Checkbox chbSifilis;
    @View
    private Checkbox chbHepatitis_b;
    @View
    private Checkbox chbGingivitis;
    @View
    private Checkbox chbOtros_factores;
    @View
    private Textbox tbxCual_otros_factores;
    @View
    private Checkbox chbPreeclampsia;
    @View
    private Checkbox chbEclampsia;
    @View
    private Checkbox chbSindrome_hellp;
    @View
    private Checkbox chbDiabetes_gestacional;
    @View
    private Checkbox chbSepsis;
    @View
    private Checkbox chbHemorragia1;
    @View
    private Checkbox chbHemorragia2;
    @View
    private Checkbox chbHemorragia3;
    @View
    private Checkbox chbDesproporcion_cefalo;
    @View
    private Checkbox chbRetardo;
    @View
    private Checkbox chbEnfermedad_autoinmune;
    @View
    private Checkbox chbMalaria;
    @View
    private Checkbox chbEmbarazo_no_deseado;
    @View
    private Checkbox chbViolencia_gestante;
    @View
    private Checkbox chbOtras_complicaciones;
    @View
    private Checkbox chbGestion_producto;
    @View
    private Checkbox chbFeto_imcompatible;
    @View
    private Checkbox chbSintomas_depresivos;
    @View
    private Textbox tbxCual_otras_complicaciones;
    @View
    private Intbox ibxCpn;
    @View
    private Intbox ibxSemanas_cpn;
    @View
    private Radiogroup rdbControles;
    @View
    private Radiogroup rdbNivel_atencion;
    @View
    private Radiogroup rdbRemisiones;
    @View
    private Bandbox tbxImpresion_final;
    @View
    private Textbox tbxNombre_impresion;
    @View
    private Radiogroup rdbMomento_muerte_materna;
    @View
    private Intbox ibxSemanas_gestion;
    @View
    private Datebox dtbxFecha_parto;
    @View
    private Timebox tbxHora_parto;
    @View
    private Radiogroup rdbTipo_parto;
    @View
    private Radiogroup rdbParto_atendido;
    @View
    private Textbox tbxCual_parto;
    @View
    private Radiogroup rdbNivel_atencion2;
    @View
    private Radiogroup rdbMomento_muerte_perinatal;
    @View
    private Intbox ibxEdad_gestacional;
    @View
    private Radiogroup rdbEdad_gestacional_confiable;
    @View
    private Intbox ibxEdad_neonatal;
    @View
    private Doublebox dbxPeso;
    @View
    private Doublebox dbxTalla;
    @View
    private Doublebox dbxApgar1;
    @View
    private Doublebox dbxApgar5;
    @View
    private Radiogroup rdbNivel_atencion_inicial;
    @View
    private Radiogroup rdbRemision_oportuna;
    @View
    private Radiogroup rdbAdaptacion;
    @View
    private Textbox tbxCausa_defucion;
    @View
    private Textbox tbxNombre_causa;

    @View
    private Radiogroup rdbCausa_muerte;
    @View
    private Radiogroup rdbDescripcion_demoras;
    @View
    private Radiogroup rdbDescripcion_demoras2;
    @View
    private Radiogroup rdbDescripcion_demoras3;
    @View
    private Radiogroup rdbDescripcion_demoras4;
    @View
    private Textbox tbxCodigo_medico;
    private final String[] idsExcluyentes = {};

    @View
    private Toolbarbutton btGuardar;

    @View
    private Row rowConvivencia;
    @View
    private Row rowParto;
    @View
    private Row rowComplicaciones;
    @View
    private Row rowCual_otros_factores;

    @View
    private Label lbCual_convivencia;
    @View
    private Label lbCual_parto;
    @View
    private Label lbCual_otras_complicaciones;
    @View
    private Label lbCual_otros_factores;

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
                Ficha_epidemiologia_n26 ficha = new Ficha_epidemiologia_n26();
                ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
                ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
                ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
                ficha = (Ficha_epidemiologia_n26) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);

				//log.info("consultar ficha 26-------> "+ficha);
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

    //Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        groupboxConsulta.setVisible(!sw);
        groupboxEditar.setVisible(sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            buscarDatos();
            btGuardar.setVisible(false);
        } else {
            btGuardar.setVisible(true);
            dtbxFecha_parto.setValue(null);
            tbxHora_parto.setValue(null);
            limpiarDatos();
            FormularioUtil.cargarRadiogroupsDefecto(this);
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
    }

    //Metodo para validar campos del formulario //
    public boolean validarFichaEpidemiologia() {

        boolean valida = true;

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres() + " recuerde que...", "Los campos marcados con (*) son obligatorios");
        }

        return valida;
    }

    //Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue().toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            if (admision != null) {
                parameters.put("identificacion",
                        admision.getNro_identificacion());
            }

            getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");

            List<Ficha_epidemiologia_n26> lista_datos = getServiceLocator()
                    .getFicha_epidemiologia_nnService().listar(
                            Ficha_epidemiologia_n26.class, parameters);
            rowsResultado.getChildren().clear();

            for (Ficha_epidemiologia_n26 ficha_epidemiologia_n26 : lista_datos) {
                rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n26, this));
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

        final Ficha_epidemiologia_n26 ficha_epidemiologia_n26 = (Ficha_epidemiologia_n26) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(ficha_epidemiologia_n26.getCodigo_ficha() + ""));
        fila.appendChild(new Label(ficha_epidemiologia_n26.getIdentificacion() + ""));
        fila.appendChild(new Label(ficha_epidemiologia_n26.getFecha_creacion() + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(ficha_epidemiologia_n26);
            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    //Metodo para guardar la informacion //
    public Ficha_epidemiologia_n26 obtenerFichaEpidemiologia() {

        Ficha_epidemiologia_n26 ficha_epidemiologia_n26 = new Ficha_epidemiologia_n26();
        ficha_epidemiologia_n26.setCodigo_empresa(empresa.getCodigo_empresa());
        ficha_epidemiologia_n26.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        ficha_epidemiologia_n26.setCodigo_ficha(tbxCodigo_ficha.getValue());
        ficha_epidemiologia_n26.setIdentificacion(tbxIdentificacion.getValue());
        ficha_epidemiologia_n26.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
        ficha_epidemiologia_n26.setCodigo_diagnostico("Z000");
        ficha_epidemiologia_n26.setSitio_defucion(rdbSitio_defucion.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setConvivencia(rdbConvivencia.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setCual_convivencia(tbxCual_convivencia.getValue());
        ficha_epidemiologia_n26.setEscolaridad(rdbEscolaridad.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setRegularidad_fecundidad(rdbRegularidad_fecundidad.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setGestiones((ibxGestiones.getValue() != null ? ibxGestiones.getValue() + "" : ""));
        ficha_epidemiologia_n26.setPartos((ibxPartos.getValue() != null ? ibxPartos.getValue() + "" : ""));
        ficha_epidemiologia_n26.setCesarias((ibxCesarias.getValue() != null ? ibxCesarias.getValue() + "" : ""));
        ficha_epidemiologia_n26.setMuertos((ibxMuertos.getValue() != null ? ibxMuertos.getValue() + "" : ""));
        ficha_epidemiologia_n26.setVivos((ibxVivos.getValue() != null ? ibxVivos.getValue() + "" : ""));
        ficha_epidemiologia_n26.setAbortos((ibxAbortos.getValue() != null ? ibxAbortos.getValue() + "" : ""));
        ficha_epidemiologia_n26.setNinguno(chbNinguno.isChecked());
        ficha_epidemiologia_n26.setHipertension_cronica(chbHipertension_cronica.isChecked());
        ficha_epidemiologia_n26.setCardiopatias(chbCardiopatias.isChecked());
        ficha_epidemiologia_n26.setDiabetes(chbDiabetes.isChecked());
        ficha_epidemiologia_n26.setMola_hidatiforme(chbMola_hidatiforme.isChecked());
        ficha_epidemiologia_n26.setRn_pretermino(chbRn_pretermino.isChecked());
        ficha_epidemiologia_n26.setRn_bajo_peso(chbRn_bajo_peso.isChecked());
        ficha_epidemiologia_n26.setRn_macrosomicos(chbRn_macrosomicos.isChecked());
        ficha_epidemiologia_n26.setTrastorno_mental(chbTrastorno_mental.isChecked());
        ficha_epidemiologia_n26.setObesidad(chbObesidad.isChecked());
        ficha_epidemiologia_n26.setDesnutricion_cronica(chbDesnutricion_cronica.isChecked());
        ficha_epidemiologia_n26.setIntergenesis(chbIntergenesis.isChecked());
        ficha_epidemiologia_n26.setIts(chbIts.isChecked());
        ficha_epidemiologia_n26.setVih(chbVih.isChecked());
        ficha_epidemiologia_n26.setOtras_infecciones(chbOtras_infecciones.isChecked());
        ficha_epidemiologia_n26.setRh_negativo(chbRh_negativo.isChecked());
        ficha_epidemiologia_n26.setTabaquismo(chbTabaquismo.isChecked());
        ficha_epidemiologia_n26.setAlcoholismo(chbAlcoholismo.isChecked());
        ficha_epidemiologia_n26.setSustancias_psicoactivas(chbSustancias_psicoactivas.isChecked());
        ficha_epidemiologia_n26.setDeficiencias(chbDeficiencias.isChecked());
        ficha_epidemiologia_n26.setSifilis(chbSifilis.isChecked());
        ficha_epidemiologia_n26.setHepatitis_b(chbHepatitis_b.isChecked());
        ficha_epidemiologia_n26.setGingivitis(chbGingivitis.isChecked());
        ficha_epidemiologia_n26.setOtros_factores(chbOtros_factores.isChecked());
        ficha_epidemiologia_n26.setCual_otros_factores(tbxCual_otros_factores.getValue());
        ficha_epidemiologia_n26.setPreeclampsia(chbPreeclampsia.isChecked());
        ficha_epidemiologia_n26.setEclampsia(chbEclampsia.isChecked());
        ficha_epidemiologia_n26.setSindrome_hellp(chbSindrome_hellp.isChecked());
        ficha_epidemiologia_n26.setDiabetes_gestacional(chbDiabetes_gestacional.isChecked());
        ficha_epidemiologia_n26.setHemorragia1(chbHemorragia1.isChecked());
        ficha_epidemiologia_n26.setHemorragia2(chbHemorragia2.isChecked());
        ficha_epidemiologia_n26.setHemorragia3(chbHemorragia3.isChecked());
        ficha_epidemiologia_n26.setDesproporcion_cefalo(chbDesproporcion_cefalo.isChecked());
        ficha_epidemiologia_n26.setRetardo(chbRetardo.isChecked());
        ficha_epidemiologia_n26.setEnfermedad_autoinmune(chbEnfermedad_autoinmune.isChecked());
        ficha_epidemiologia_n26.setMalaria(chbMalaria.isChecked());
        ficha_epidemiologia_n26.setEmbarazo_no_deseado(chbEmbarazo_no_deseado.isChecked());
        ficha_epidemiologia_n26.setViolencia_gestante(chbViolencia_gestante.isChecked());
        ficha_epidemiologia_n26.setOtras_complicaciones(chbOtras_complicaciones.isChecked());
        ficha_epidemiologia_n26.setGestion_producto(chbGestion_producto.isChecked());
        ficha_epidemiologia_n26.setFeto_imcompatible(chbFeto_imcompatible.isChecked());
        ficha_epidemiologia_n26.setSintomas_depresivos(chbSintomas_depresivos.isChecked());
        ficha_epidemiologia_n26.setCual_otras_complicaciones(tbxCual_otras_complicaciones.getValue());
        ficha_epidemiologia_n26.setCpn((ibxCpn.getValue() != null ? ibxCpn.getValue() + "" : ""));
        ficha_epidemiologia_n26.setSemanas_cpn((ibxSemanas_cpn.getValue() != null ? ibxSemanas_cpn.getValue() + "" : ""));
        ficha_epidemiologia_n26.setControles(rdbControles.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setNivel_atencion(rdbNivel_atencion.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setRemisiones(rdbRemisiones.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setImpresion_final(tbxImpresion_final.getValue());
        ficha_epidemiologia_n26.setMomento_muerte_materna(rdbMomento_muerte_materna.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setSemanas_gestion((ibxSemanas_gestion.getValue() != null ? ibxSemanas_gestion.getValue() + "" : ""));

        if (dtbxFecha_parto.getValue() != null) {
            ficha_epidemiologia_n26.setFecha_parto(new Timestamp(dtbxFecha_parto.getValue().getTime()));

        } else {
            ficha_epidemiologia_n26.setFecha_parto(null);
        }

        if (tbxHora_parto.getValue() != null) {
            ficha_epidemiologia_n26.setHora_parto(new Timestamp(tbxHora_parto.getValue().getTime()));

        } else {
            ficha_epidemiologia_n26.setHora_parto(null);
        }

        ficha_epidemiologia_n26.setTipo_parto(rdbTipo_parto.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setParto_atendido(rdbParto_atendido.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setCual_parto(tbxCual_parto.getValue());
        ficha_epidemiologia_n26.setNivel_atencion2(rdbNivel_atencion2.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setMomento_muerte_perinatal(rdbMomento_muerte_perinatal.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setEdad_gestacional((ibxEdad_gestacional.getValue() != null ? ibxEdad_gestacional.getValue() + "" : ""));
        ficha_epidemiologia_n26.setEdad_gestacional_confiable(rdbEdad_gestacional_confiable.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setEdad_neonatal((ibxEdad_neonatal.getValue() != null ? ibxEdad_neonatal.getValue() + "" : ""));
        ficha_epidemiologia_n26.setPeso((dbxPeso.getValue() != null ? dbxPeso.getValue() + "" : ""));
        ficha_epidemiologia_n26.setTalla((dbxTalla.getValue() != null ? dbxTalla.getValue() + "" : ""));
        ficha_epidemiologia_n26.setApgar1((dbxApgar1.getValue() != null ? dbxApgar1.getValue() + "" : ""));
        ficha_epidemiologia_n26.setApgar5((dbxApgar5.getValue() != null ? dbxApgar5.getValue() + "" : ""));
        ficha_epidemiologia_n26.setNivel_atencion_inicial(rdbNivel_atencion_inicial.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setRemision_oportuna(rdbRemision_oportuna.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setAdaptacion(rdbAdaptacion.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setCausa_defucion(tbxCausa_defucion.getValue());
        ficha_epidemiologia_n26.setCausa_muerte(rdbCausa_muerte.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setDescripcion_demoras(rdbDescripcion_demoras.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setDescripcion_demoras2(rdbDescripcion_demoras2.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setDescripcion_demoras3(rdbDescripcion_demoras3.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setDescripcion_demoras4(rdbDescripcion_demoras4.getSelectedItem().getValue().toString());
        ficha_epidemiologia_n26.setCodigo_medico(tbxCodigo_medico.getValue());
        ficha_epidemiologia_n26.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
        ficha_epidemiologia_n26.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
        ficha_epidemiologia_n26.setCreacion_user(usuarios.getCodigo().toString());
        ficha_epidemiologia_n26.setDelete_date(null);
        ficha_epidemiologia_n26.setUltimo_user(usuarios.getCodigo().toString());
        ficha_epidemiologia_n26.setDelete_user(null);
				//log.info("1:"+ficha_epidemiologia_n26);

        return ficha_epidemiologia_n26;

    }

    //Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Ficha_epidemiologia_n26 obj) throws Exception {
        Ficha_epidemiologia_n26 ficha_epidemiologia_n26 = (Ficha_epidemiologia_n26) obj;
        try {
            tbxCodigo_ficha.setValue(ficha_epidemiologia_n26.getCodigo_ficha());
            tbxIdentificacion.setValue(ficha_epidemiologia_n26.getIdentificacion());

            obtenerAdmision(admision);

            FormularioUtil.deshabilitarComponentes(groupboxEditar, true, new String[]{});

            dtbxFecha_creacion.setValue(ficha_epidemiologia_n26.getFecha_creacion());
            tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n26.getCodigo_diagnostico());
            Utilidades.seleccionarRadio(rdbSitio_defucion, ficha_epidemiologia_n26.getSitio_defucion());
            Utilidades.seleccionarRadio(rdbConvivencia, ficha_epidemiologia_n26.getConvivencia());

            if (ficha_epidemiologia_n26.getConvivencia().equals("O")) {
                rowConvivencia.setVisible(true);
                lbCual_convivencia.setVisible(true);
                tbxCual_convivencia.setVisible(true);
                tbxCual_convivencia.setValue(ficha_epidemiologia_n26.getCual_convivencia());

            } else {
                rowConvivencia.setVisible(false);
                lbCual_convivencia.setVisible(false);
                tbxCual_convivencia.setVisible(false);

            }

            Utilidades.seleccionarRadio(rdbEscolaridad, ficha_epidemiologia_n26.getEscolaridad());
            Utilidades.seleccionarRadio(rdbRegularidad_fecundidad, ficha_epidemiologia_n26.getRegularidad_fecundidad());
            ibxGestiones.setValue((ficha_epidemiologia_n26.getGestiones() != null && !ficha_epidemiologia_n26.getGestiones().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getGestiones()) : null);
            ibxPartos.setValue((ficha_epidemiologia_n26.getPartos() != null && !ficha_epidemiologia_n26.getPartos().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getPartos()) : null);
            ibxCesarias.setValue((ficha_epidemiologia_n26.getCesarias() != null && !ficha_epidemiologia_n26.getCesarias().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getCesarias()) : null);
            ibxMuertos.setValue((ficha_epidemiologia_n26.getMuertos() != null && !ficha_epidemiologia_n26.getMuertos().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getMuertos()) : null);
            ibxVivos.setValue((ficha_epidemiologia_n26.getVivos() != null && !ficha_epidemiologia_n26.getVivos().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getVivos()) : null);
            ibxAbortos.setValue((ficha_epidemiologia_n26.getAbortos() != null && !ficha_epidemiologia_n26.getAbortos().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getAbortos()) : null);
            chbNinguno.setChecked(ficha_epidemiologia_n26.getNinguno());
            chbHipertension_cronica.setChecked(ficha_epidemiologia_n26.getHipertension_cronica());
            chbCardiopatias.setChecked(ficha_epidemiologia_n26.getCardiopatias());
            chbDiabetes.setChecked(ficha_epidemiologia_n26.getDiabetes());
            chbMola_hidatiforme.setChecked(ficha_epidemiologia_n26.getMola_hidatiforme());
            chbRn_pretermino.setChecked(ficha_epidemiologia_n26.getRn_pretermino());
            chbRn_bajo_peso.setChecked(ficha_epidemiologia_n26.getRn_bajo_peso());
            chbRn_macrosomicos.setChecked(ficha_epidemiologia_n26.getRn_macrosomicos());
            chbTrastorno_mental.setChecked(ficha_epidemiologia_n26.getTrastorno_mental());
            chbObesidad.setChecked(ficha_epidemiologia_n26.getObesidad());
            chbDesnutricion_cronica.setChecked(ficha_epidemiologia_n26.getDesnutricion_cronica());
            chbIntergenesis.setChecked(ficha_epidemiologia_n26.getIntergenesis());
            chbIts.setChecked(ficha_epidemiologia_n26.getIts());
            chbVih.setChecked(ficha_epidemiologia_n26.getVih());
            chbOtras_infecciones.setChecked(ficha_epidemiologia_n26.getOtras_infecciones());
            chbRh_negativo.setChecked(ficha_epidemiologia_n26.getRh_negativo());
            chbTabaquismo.setChecked(ficha_epidemiologia_n26.getTabaquismo());
            chbAlcoholismo.setChecked(ficha_epidemiologia_n26.getAlcoholismo());
            chbSustancias_psicoactivas.setChecked(ficha_epidemiologia_n26.getSustancias_psicoactivas());
            chbDeficiencias.setChecked(ficha_epidemiologia_n26.getDeficiencias());
            chbSifilis.setChecked(ficha_epidemiologia_n26.getSifilis());
            chbHepatitis_b.setChecked(ficha_epidemiologia_n26.getHepatitis_b());
            chbGingivitis.setChecked(ficha_epidemiologia_n26.getGingivitis());
            chbOtros_factores.setChecked(ficha_epidemiologia_n26.getOtros_factores());

            if (ficha_epidemiologia_n26.getOtros_factores()) {
                rowCual_otros_factores.setVisible(true);
                lbCual_otros_factores.setVisible(true);
                tbxCual_otros_factores.setVisible(true);
                tbxCual_otros_factores.setValue(ficha_epidemiologia_n26.getCual_otros_factores());

            } else {
                rowCual_otros_factores.setVisible(false);
                lbCual_otros_factores.setVisible(false);
                tbxCual_otros_factores.setVisible(false);

            }

            chbPreeclampsia.setChecked(ficha_epidemiologia_n26.getPreeclampsia());
            chbEclampsia.setChecked(ficha_epidemiologia_n26.getEclampsia());
            chbSindrome_hellp.setChecked(ficha_epidemiologia_n26.getSindrome_hellp());
            chbDiabetes_gestacional.setChecked(ficha_epidemiologia_n26.getDiabetes_gestacional());
            chbSepsis.setChecked(ficha_epidemiologia_n26.getSepsis());
            chbHemorragia1.setChecked(ficha_epidemiologia_n26.getHemorragia1());
            chbHemorragia2.setChecked(ficha_epidemiologia_n26.getHemorragia2());
            chbHemorragia3.setChecked(ficha_epidemiologia_n26.getHemorragia3());
            chbDesproporcion_cefalo.setChecked(ficha_epidemiologia_n26.getDesproporcion_cefalo());
            chbRetardo.setChecked(ficha_epidemiologia_n26.getRetardo());
            chbEnfermedad_autoinmune.setChecked(ficha_epidemiologia_n26.getEnfermedad_autoinmune());
            chbMalaria.setChecked(ficha_epidemiologia_n26.getMalaria());
            chbEmbarazo_no_deseado.setChecked(ficha_epidemiologia_n26.getEmbarazo_no_deseado());
            chbViolencia_gestante.setChecked(ficha_epidemiologia_n26.getViolencia_gestante());
            chbOtras_complicaciones.setChecked(ficha_epidemiologia_n26.getOtras_complicaciones());
            chbGestion_producto.setChecked(ficha_epidemiologia_n26.getGestion_producto());
            chbFeto_imcompatible.setChecked(ficha_epidemiologia_n26.getFeto_imcompatible());
            chbSintomas_depresivos.setChecked(ficha_epidemiologia_n26.getSintomas_depresivos());

            if (ficha_epidemiologia_n26.getOtras_complicaciones()) {
                rowComplicaciones.setVisible(true);
                lbCual_otras_complicaciones.setVisible(true);
                tbxCual_otras_complicaciones.setVisible(true);
                tbxCual_otras_complicaciones.setValue(ficha_epidemiologia_n26.getCual_otras_complicaciones());

            } else {
                rowComplicaciones.setVisible(false);
                lbCual_otras_complicaciones.setVisible(false);
                tbxCual_otras_complicaciones.setVisible(false);

            }

            ibxCpn.setValue((ficha_epidemiologia_n26.getCpn() != null && !ficha_epidemiologia_n26.getCpn().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getCpn()) : null);
            ibxSemanas_cpn.setValue((ficha_epidemiologia_n26.getSemanas_cpn() != null && !ficha_epidemiologia_n26.getSemanas_cpn().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getSemanas_cpn()) : null);
            Utilidades.seleccionarRadio(rdbControles, ficha_epidemiologia_n26.getControles());
            Utilidades.seleccionarRadio(rdbNivel_atencion, ficha_epidemiologia_n26.getNivel_atencion());
            Utilidades.seleccionarRadio(rdbRemisiones, ficha_epidemiologia_n26.getRemisiones());

            Cie cie = new Cie();
            cie.setCodigo(ficha_epidemiologia_n26.getImpresion_final());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);
            tbxImpresion_final.setValue(ficha_epidemiologia_n26.getImpresion_final());
            tbxNombre_impresion.setValue((cie != null ? cie.getNombre() : ""));

            Utilidades.seleccionarRadio(rdbMomento_muerte_materna, ficha_epidemiologia_n26.getMomento_muerte_materna());
            ibxSemanas_gestion.setValue((ficha_epidemiologia_n26.getSemanas_gestion() != null && !ficha_epidemiologia_n26.getSemanas_gestion().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getSemanas_gestion()) : null);
            dtbxFecha_parto.setValue(ficha_epidemiologia_n26.getFecha_parto());
            tbxHora_parto.setValue(ficha_epidemiologia_n26.getHora_parto());
            Utilidades.seleccionarRadio(rdbTipo_parto, ficha_epidemiologia_n26.getTipo_parto());
            Utilidades.seleccionarRadio(rdbParto_atendido, ficha_epidemiologia_n26.getParto_atendido());

            if (ficha_epidemiologia_n26.getParto_atendido().equals("O")) {
                rowParto.setVisible(true);
                lbCual_parto.setVisible(true);
                tbxCual_parto.setVisible(true);
                tbxCual_parto.setValue(ficha_epidemiologia_n26.getCual_parto());

            } else {
                rowParto.setVisible(false);
                lbCual_parto.setVisible(false);
                tbxCual_parto.setVisible(false);

            }

            Utilidades.seleccionarRadio(rdbNivel_atencion2, ficha_epidemiologia_n26.getNivel_atencion2());
            Utilidades.seleccionarRadio(rdbMomento_muerte_perinatal, ficha_epidemiologia_n26.getMomento_muerte_perinatal());
            ibxEdad_gestacional.setValue((ficha_epidemiologia_n26.getEdad_gestacional() != null && !ficha_epidemiologia_n26.getEdad_gestacional().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getEdad_gestacional()) : null);
            Utilidades.seleccionarRadio(rdbEdad_gestacional_confiable, ficha_epidemiologia_n26.getEdad_gestacional_confiable());
            ibxEdad_neonatal.setValue((ficha_epidemiologia_n26.getEdad_neonatal() != null && !ficha_epidemiologia_n26.getEdad_neonatal().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n26.getEdad_neonatal()) : null);

            dbxPeso.setValue((ficha_epidemiologia_n26.getPeso() != null && !ficha_epidemiologia_n26
                    .getPeso().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n26.getPeso()) : null);
            dbxTalla.setValue((ficha_epidemiologia_n26.getTalla() != null && !ficha_epidemiologia_n26
                    .getTalla().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n26.getTalla()) : null);
            dbxApgar1.setValue((ficha_epidemiologia_n26.getApgar1() != null && !ficha_epidemiologia_n26
                    .getApgar1().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n26.getApgar1()) : null);
            dbxApgar5.setValue((ficha_epidemiologia_n26.getApgar5() != null && !ficha_epidemiologia_n26
                    .getApgar5().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n26.getApgar5()) : null);

            Utilidades.seleccionarRadio(rdbNivel_atencion_inicial, ficha_epidemiologia_n26.getNivel_atencion_inicial());
            Utilidades.seleccionarRadio(rdbRemision_oportuna, ficha_epidemiologia_n26.getRemision_oportuna());
            Utilidades.seleccionarRadio(rdbAdaptacion, ficha_epidemiologia_n26.getAdaptacion());

            cie = new Cie();
            cie.setCodigo(ficha_epidemiologia_n26.getImpresion_final());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);
            tbxCausa_defucion.setValue(ficha_epidemiologia_n26.getCausa_defucion());
            tbxNombre_causa.setValue((cie != null ? cie.getNombre() : ""));

            Utilidades.seleccionarRadio(rdbCausa_muerte, ficha_epidemiologia_n26.getCausa_muerte());
            Utilidades.seleccionarRadio(rdbDescripcion_demoras, ficha_epidemiologia_n26.getDescripcion_demoras());
            Utilidades.seleccionarRadio(rdbDescripcion_demoras2, ficha_epidemiologia_n26.getDescripcion_demoras2());
            Utilidades.seleccionarRadio(rdbDescripcion_demoras3, ficha_epidemiologia_n26.getDescripcion_demoras3());
            Utilidades.seleccionarRadio(rdbDescripcion_demoras4, ficha_epidemiologia_n26.getDescripcion_demoras4());
            tbxCodigo_medico.setValue(ficha_epidemiologia_n26.getCodigo_medico());

            //Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Ficha_epidemiologia_n26 ficha_epidemiologia_n26 = (Ficha_epidemiologia_n26) obj;
        try {
            int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n26);
            if (result == 0) {
                throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
            }

            Messagebox.show("Informacion se eliminó satisfactoriamente !!",
                    "Information", Messagebox.OK, Messagebox.INFORMATION);
        } catch (HealthmanagerException e) {
            MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
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

    public void mostrar_conRadio(ZKWindow form, Radiogroup radiogroup, AbstractComponent[] abstractComponents) {
        try {
            String valor = "O";
            FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup, valor, abstractComponents);

        } catch (Exception e) {
			//  block
            //System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    public void mostrar_conCheck(ZKWindow form, Checkbox checkbox, AbstractComponent[] abstractComponents) {
        try {

            FormularioUtil.mostrarComponentes_conCheckbox(form, checkbox, abstractComponents);

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
    public Ficha_epidemiologia_n26 consultarDatos(Map<String, Object> map,
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

        List<Ficha_epidemiologia_n26> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
                Ficha_epidemiologia_n26.class, parameters);

				//log.info("lista_datos"+lista_datos);
        if (!lista_datos.isEmpty()) {
            Ficha_epidemiologia_n26 ficha_n26 = lista_datos.get(lista_datos.size() - 1);

            return ficha_n26;
        } else {

            return null;
        }
    }

}
