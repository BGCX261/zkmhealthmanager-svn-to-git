/*
 * ficha_inicio_lepraAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Control_ficha_inicio_lepra;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Ficha_inicio_lepra;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Control_ficha_inicio_lepraService;
import healthmanager.modelo.service.Ficha_inicio_lepraService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Ficha_inicio_lepraAction extends ZKWindow {

    private static Logger log = Logger.getLogger(Ficha_inicio_lepraAction.class);

    private Ficha_inicio_lepraService ficha_inicio_lepraService;
    private Control_ficha_inicio_lepraService control_ficha_inicio_lepraService;

    private Admision admision;

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

    //@View private Textbox tbxNro_identificacion;
    @View
    private Bandbox tbxNro_identificacion;
    @View
    private Textbox tbxNomPaciente;
    @View
    private Textbox tbxTipo_id;
    @View
    private Textbox tbxEdad;
    @View
    private Textbox tbxOcupacion;
    @View
    private Textbox tbxSexo;
    @View
    private Textbox tbxPais;
    @View
    private Textbox tbxDpto;
    @View
    private Textbox tbxMun;
    @View
    private Textbox tbxAdmin;
    @View
    private Textbox tbxTipo_usuario;
    @View
    private Textbox tbxDireccion;
    @View
    private Textbox tbxBarrio;
    @View
    private Textbox tbxArea_paciente;
    @View
    private Textbox tbxPertenencia_etnica;
    @View
    private Textbox tbxGrupo_poblacional;

    @View
    private Datebox dtbxFecha_diagnostico;
    @View
    private Listbox lbxCriterio_diagnostico;
    @View
    private Listbox lbxCausa_ingreso;
    @View
    private Listbox lbxNro_lesiones_derma;
    @View
    private Listbox lbxResultado_bacilo;
    @View
    private Textbox tbxResultado_biopsia;
    @View
    private Listbox lbxClasificacion_ingreso;
    @View
    private Listbox lbxFuente_contagio;
    @View
    private Textbox tbxPrimeros_sintomas;
    @View
    private Intbox ibxTiempo_evolucion;
    @View
    private Listbox lbxTipo_lesiones;
    @View
    private Listbox lbxSensibilidad_lesiones;
    @View
    private Checkbox chbNervios_derecho;
    @View
    private Checkbox chbAuricular_derecho;
    @View
    private Checkbox chbCubital_derecho;
    @View
    private Checkbox chbMediano_derecho;
    @View
    private Checkbox chbRadial_derecho;
    @View
    private Checkbox chbCiaticopopliteo_derecho;
    @View
    private Checkbox chbTivial_derecho;
    @View
    private Checkbox chbNervios_izquierdo;
    @View
    private Checkbox chbAuricular_izquierdo;
    @View
    private Checkbox chbCubital_izquierdo;
    @View
    private Checkbox chbMediano_izquierdo;
    @View
    private Checkbox chbRadial_izquierdo;
    @View
    private Checkbox chbCiaticopopliteo_izquierdo;
    @View
    private Checkbox chbTivial_izquierdo;
    @View
    private Radiogroup rdbGrado_ojos_derecho;
    @View
    private Radiogroup rdbGrado_manos_derecho;
    @View
    private Radiogroup rdbGrado_pies_derecho;
    @View
    private Radiogroup rdbGrado_ojos_izquierdo;
    @View
    private Radiogroup rdbGrado_manos_izquierdo;
    @View
    private Radiogroup rdbGrado_pies_izquierdo;
    @View
    private Radiogroup rdbGrado_maximo_izquierdo;
    @View
    private Datebox dtbxFecha_diligenciamiento;
    @View
    private Intbox ibxPeriodo_requerido;
    @View
    private Intbox ibxNumero_dosis;
    @View
    private Textbox tbxNro_ingreso;

    @View
    private Listbox lbxAnio;

    @View
    private Grid gridControl;
    @View
    private Rows rowsControl;

    List<Map<String, Object>> listaControl;

    private final String[] idsExcluyentes = {"dtbxFecha_diagnostico", "tbxNro_identificacion",
        "tbxTipo", "btCancel", "btGuardar", "tbxAccion", "tbxNro_ingreso", "tbxCodigo_ficha",
        "btNew", "lbxFormato", "btImprimir", "lbxAnio", "tbxTipo_id", "tbxEdad", "tbxOcupacion",
        "tbxSexo", "tbxPais", "tbxDpto", "tbxMun", "tbxAdmin", "tbxTipo_usuario", "tbxDireccion", "tbxBarrio",
        "tbxArea_paciente", "tbxPertenencia_etnica", "tbxGrupo_poblacional", "tbxNomPaciente", "lbxParameter", "tbxValue"};

    @Override
    public void init() throws Exception {
        listaControl = new ArrayList<Map<String, Object>>();
        listarCombos();
        buscarRegistros();
    }

    /**
     * Sobreescritura del metodo params(Map<String, Object> map) para obtener
     * los parametros iniciales con los que trabajara la historia clinica
     */
    @Override
    public void params(Map<String, Object> map) {
        if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
            admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			//log.info("admision"+admision);

        }
    }

    public void buscarRegistros() {

        try {
			//log.info("admision"+admision);

            Ficha_inicio_lepra ficha_inicio_lepraAux = new Ficha_inicio_lepra();
            ficha_inicio_lepraAux.setCodigo_empresa(codigo_empresa);
            ficha_inicio_lepraAux.setCodigo_sucursal(codigo_sucursal);
            ficha_inicio_lepraAux.setNro_identificacion(admision.getNro_identificacion());
            ficha_inicio_lepraAux = ficha_inicio_lepraService.consultar(ficha_inicio_lepraAux);

			//log.info("ficha_inicio_lepraAux"+ficha_inicio_lepraAux);
            if (ficha_inicio_lepraAux == null) {
                tbxNro_identificacion.setValue(admision.getNro_identificacion());
                tbxNomPaciente.setValue(admision.getPaciente().getNombreCompleto());

                cargarInfoPaciente(admision.getNro_identificacion(), admision);
                FormularioUtil.deshabilitarComponentes(Ficha_inicio_lepraAction.this, false, idsExcluyentes);
                dtbxFecha_diagnostico.setButtonVisible(true);
                dtbxFecha_diagnostico.setReadonly(true);
            } else {
                mostrarDatos(ficha_inicio_lepraAux);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private void cargarInfoPaciente(String nro_identificacion, Admision admision) {
        try {

            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(codigo_empresa);
            paciente.setCodigo_sucursal(codigo_sucursal);
            paciente.setNro_identificacion(nro_identificacion);
            paciente = getServiceLocator().getPacienteService().consultar(paciente);
			//log.info(">>>>>>"+paciente);

            if (paciente != null) {

                Adicional_paciente adicional_paciente = new Adicional_paciente();
                adicional_paciente.setCodigo_empresa(codigo_empresa);
                adicional_paciente.setCodigo_sucursal(codigo_sucursal);
                adicional_paciente.setNro_identificacion(paciente
                        .getNro_identificacion());
                adicional_paciente = getServiceLocator().getServicio(
                        GeneralExtraService.class).consultar(adicional_paciente);

                tbxNro_ingreso.setValue(admision.getNro_ingreso());

                Elemento tipo_id = new Elemento();
                tipo_id.setTipo("tipo_id");
                tipo_id.setCodigo(paciente.getTipo_identificacion());
                tipo_id = getServiceLocator().getElementoService().consultar(tipo_id);
                tbxTipo_id.setValue((tipo_id != null ? tipo_id.getDescripcion() : ""));

                Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(paciente
                        .getFecha_nacimiento());
                Integer anios = mapa_edades.get("anios");
                tbxEdad.setValue(anios + (anios == 1 ? " año " : " años "));

                Ocupaciones ocupaciones = new Ocupaciones();
                ocupaciones.setId(paciente.getCodigo_ocupacion());
                ocupaciones = getServiceLocator().getOcupacionesService()
                        .consultar(ocupaciones);
                tbxOcupacion.setValue(ocupaciones != null ? ocupaciones
                        .getNombre() : "");

                tbxSexo.setValue(Utilidades.getNombreElemento(paciente.getSexo(),
                        "sexo", Ficha_inicio_lepraAction.this));

                tbxPais.setValue("Colombia");

                Departamentos departamentos = new Departamentos(
                        paciente.getCodigo_dpto(), "");
                departamentos = getServiceLocator()
                        .getDepartamentosService().consultar(departamentos);

                tbxDpto.setValue((departamentos != null ? departamentos.getNombre() : ""));

                Municipios municipios = new Municipios(
                        paciente.getCodigo_municipio(), paciente.getCodigo_dpto(),
                        "");
                municipios = getServiceLocator().getMunicipiosService()
                        .consultar(municipios);
                tbxMun.setValue((municipios != null ? municipios.getNombre() : ""));

                tbxAdmin
                        .setValue(admision.getAdministradora() != null ? (admision
                                .getAdministradora().getCodigo() + "-" + admision
                                .getAdministradora().getNombre()) : "");

                tbxTipo_usuario.setValue(Utilidades.getNombreElemento(
                        paciente.getTipo_usuario(), "tipo_usuario", Ficha_inicio_lepraAction.this));

                tbxDireccion.setValue((paciente != null ? paciente.getDireccion() : ""));

                Barrio barrio = new Barrio();
                barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
                barrio = getServiceLocator().getBarrioService()
                        .consultar(barrio);
                tbxBarrio.setValue(barrio != null ? ("("
                        + barrio.getCodigo_barrio() + ") " + barrio.getBarrio())
                        : "");

                tbxArea_paciente.setValue(Utilidades.getNombreElemento(paciente.getArea_paciente(),
                        "area_paciente", Ficha_inicio_lepraAction.this));

                Pertenencia_etnica etnica = new Pertenencia_etnica();
                etnica.setId(admision.getPaciente().getPertenencia_etnica());
                etnica = getServiceLocator()
                        .getPertenencia_etnicaService().consultar(etnica);

                tbxPertenencia_etnica.setValue("("
                        + admision.getPaciente().getPertenencia_etnica() + ") "
                        + (etnica != null ? etnica.getNombre() : ""));

                tbxGrupo_poblacional.setValue("");

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MensajesUtil.mensajeAlerta("Alerta !!!", e.getMessage());
        }
    }

    public void listarCombos() {
        listarParameter();
        Utilidades.listarElementoListbox(lbxCriterio_diagnostico, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxCausa_ingreso, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxNro_lesiones_derma, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxResultado_bacilo, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxClasificacion_ingreso, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxFuente_contagio, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxTipo_lesiones, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxSensibilidad_lesiones, true, getServiceLocator());
        //Utilidades.listarAnios(Calendar.getInstance().get(Calendar.YEAR), lbxAnio, false, 5, true);
        Utilidades.listarAnios(lbxAnio, 5);
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("fil.nro_identificacion");
        listitem.setLabel("Identificacion");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("t2.nombre1||' '||t2.nombre2");
        listitem.setLabel("Nombres");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("t2.apellido1||' '||t2.apellido2");
        listitem.setLabel("Apellidos");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("fil.codigo_ficha");
        listitem.setLabel("código");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("fil.fecha_diligenciamiento");
        listitem.setLabel("Fecha dilig.");
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
        } else {
            //buscarDatos();
            limpiarDatos();
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);

        tbxNro_identificacion.setValue("");
        tbxNro_identificacion.setDisabled(false);
        FormularioUtil.deshabilitarComponentes(this, true, idsExcluyentes);

        dtbxFecha_diagnostico.setValue(null);

        rdbGrado_ojos_derecho.setSelectedItem(null);
        rdbGrado_manos_derecho.setSelectedItem(null);
        rdbGrado_pies_derecho.setSelectedItem(null);

        rdbGrado_ojos_izquierdo.setSelectedItem(null);
        rdbGrado_manos_izquierdo.setSelectedItem(null);
        rdbGrado_pies_izquierdo.setSelectedItem(null);

        rdbGrado_maximo_izquierdo.setSelectedItem(null);

        Utilidades.seleccionarAnio(lbxAnio);

        tbxNomPaciente.setValue("");
        tbxNro_ingreso.setValue("");
        tbxTipo_id.setValue("");
        tbxEdad.setValue("");
        tbxOcupacion.setValue("");
        tbxSexo.setValue("");
        tbxPais.setValue("");
        tbxDpto.setValue("");
        tbxMun.setValue("");
        tbxAdmin.setValue("");
        tbxTipo_usuario.setValue("");
        tbxDireccion.setValue("");
        tbxBarrio.setValue("");
        tbxArea_paciente.setValue("");
        tbxPertenencia_etnica.setValue("");
        tbxGrupo_poblacional.setValue("");

        listaControl.clear();

        try {
            crearFilas();
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    //Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:white");
        dtbxFecha_diagnostico.setStyle("background-color:white");
        lbxCriterio_diagnostico.setStyle("background-color:white");
        lbxCausa_ingreso.setStyle("background-color:white");
        lbxNro_lesiones_derma.setStyle("background-color:white");
        //lbxResultado_bacilo.setStyle("background-color:white");
        lbxClasificacion_ingreso.setStyle("background-color:white");
        lbxFuente_contagio.setStyle("background-color:white");
        tbxPrimeros_sintomas.setStyle("text-transform:uppercase;background-color:white");
        ibxTiempo_evolucion.setStyle("background-color:white");
        lbxTipo_lesiones.setStyle("background-color:white");
        lbxSensibilidad_lesiones.setStyle("background-color:white");
        dtbxFecha_diligenciamiento.setStyle("background-color:white");
        ibxPeriodo_requerido.setStyle("background-color:white");
        tbxNro_ingreso.setStyle("text-transform:uppercase;background-color:white");

        boolean valida = true;

        if (tbxNro_identificacion.getText().trim().equals("")) {
            tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (dtbxFecha_diagnostico.getText().trim().equals("")) {
            dtbxFecha_diagnostico.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (lbxCriterio_diagnostico.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxCriterio_diagnostico.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (lbxCausa_ingreso.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxCausa_ingreso.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (lbxNro_lesiones_derma.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxNro_lesiones_derma.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        /*if(lbxResultado_bacilo.getSelectedItem().getValue().toString().trim().equals("")){
         lbxResultado_bacilo.setStyle("background-color:#F6BBBE");
         valida = false;
         }*/
        if (lbxClasificacion_ingreso.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxClasificacion_ingreso.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (lbxFuente_contagio.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxFuente_contagio.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (tbxPrimeros_sintomas.getText().trim().equals("")) {
            tbxPrimeros_sintomas.setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (ibxTiempo_evolucion.getText().trim().equals("")) {
            ibxTiempo_evolucion.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (lbxTipo_lesiones.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxTipo_lesiones.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (lbxSensibilidad_lesiones.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxSensibilidad_lesiones.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (dtbxFecha_diligenciamiento.getText().trim().equals("")) {
            dtbxFecha_diligenciamiento.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (ibxPeriodo_requerido.getText().trim().equals("")) {
            ibxPeriodo_requerido.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (tbxNro_ingreso.getText().trim().equals("")) {
            tbxNro_ingreso.setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

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

            ficha_inicio_lepraService.setLimit("limit 25 offset 0");

            List<Ficha_inicio_lepra> lista_datos = ficha_inicio_lepraService.listar(parameters);
            rowsResultado.getChildren().clear();

            for (Ficha_inicio_lepra ficha_inicio_lepra : lista_datos) {
                rowsResultado.appendChild(crearFilas(ficha_inicio_lepra, this));
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

        final Ficha_inicio_lepra ficha_inicio_lepra = (Ficha_inicio_lepra) objeto;

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(ficha_inicio_lepra.getCodigo_empresa());
        paciente.setCodigo_sucursal(ficha_inicio_lepra.getCodigo_sucursal());
        paciente.setNro_identificacion(ficha_inicio_lepra.getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(ficha_inicio_lepra.getCodigo_ficha() + ""));
        fila.appendChild(new Label(ficha_inicio_lepra.getNro_identificacion() + ""));
        fila.appendChild(new Label((paciente != null ? paciente.getApellido1() + " " + paciente.getApellido2() : "")));
        fila.appendChild(new Label((paciente != null ? paciente.getNombre1() + " " + paciente.getNombre2() : "")));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy").format(ficha_inicio_lepra.getFecha_diligenciamiento())));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(ficha_inicio_lepra);
            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    //Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                //Cargamos los componentes //
                actualizarPagina();
                Map datos = new HashMap();

                Ficha_inicio_lepra ficha_inicio_lepra = new Ficha_inicio_lepra();
                ficha_inicio_lepra.setCodigo_empresa(empresa.getCodigo_empresa());
                ficha_inicio_lepra.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                ficha_inicio_lepra.setCodigo_ficha(tbxCodigo_ficha.getValue());
                ficha_inicio_lepra.setNro_identificacion(tbxNro_identificacion.getValue());
                ficha_inicio_lepra.setFecha_diagnostico(new Timestamp(dtbxFecha_diagnostico.getValue().getTime()));
                ficha_inicio_lepra.setCriterio_diagnostico(lbxCriterio_diagnostico.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setCausa_ingreso(lbxCausa_ingreso.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setNro_lesiones_derma(lbxNro_lesiones_derma.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setResultado_bacilo(lbxResultado_bacilo.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setResultado_biopsia(tbxResultado_biopsia.getValue());
                ficha_inicio_lepra.setClasificacion_ingreso(lbxClasificacion_ingreso.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setFuente_contagio(lbxFuente_contagio.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setPrimeros_sintomas(tbxPrimeros_sintomas.getValue());
                ficha_inicio_lepra.setTiempo_evolucion((ibxTiempo_evolucion.getValue() != null ? ibxTiempo_evolucion.getValue() : 0));
                ficha_inicio_lepra.setTipo_lesiones(lbxTipo_lesiones.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setSensibilidad_lesiones(lbxSensibilidad_lesiones.getSelectedItem().getValue().toString());
                ficha_inicio_lepra.setNervios_derecho(chbNervios_derecho.isChecked());
                ficha_inicio_lepra.setAuricular_derecho(chbAuricular_derecho.isChecked());
                ficha_inicio_lepra.setCubital_derecho(chbCubital_derecho.isChecked());
                ficha_inicio_lepra.setMediano_derecho(chbMediano_derecho.isChecked());
                ficha_inicio_lepra.setRadial_derecho(chbRadial_derecho.isChecked());
                ficha_inicio_lepra.setCiaticopopliteo_derecho(chbCiaticopopliteo_derecho.isChecked());
                ficha_inicio_lepra.setTivial_derecho(chbTivial_derecho.isChecked());
                ficha_inicio_lepra.setNervios_izquierdo(chbNervios_izquierdo.isChecked());
                ficha_inicio_lepra.setAuricular_izquierdo(chbAuricular_izquierdo.isChecked());
                ficha_inicio_lepra.setCubital_izquierdo(chbCubital_izquierdo.isChecked());
                ficha_inicio_lepra.setMediano_izquierdo(chbMediano_izquierdo.isChecked());
                ficha_inicio_lepra.setRadial_izquierdo(chbRadial_izquierdo.isChecked());
                ficha_inicio_lepra.setCiaticopopliteo_izquierdo(chbCiaticopopliteo_izquierdo.isChecked());
                ficha_inicio_lepra.setTivial_izquierdo(chbTivial_izquierdo.isChecked());
                ficha_inicio_lepra.setGrado_ojos_derecho(rdbGrado_ojos_derecho.getSelectedItem() != null ? rdbGrado_ojos_derecho.getSelectedItem().getValue().toString() : "");
                ficha_inicio_lepra.setGrado_manos_derecho(rdbGrado_manos_derecho.getSelectedItem() != null ? rdbGrado_manos_derecho.getSelectedItem().getValue().toString() : "");
                ficha_inicio_lepra.setGrado_pies_derecho(rdbGrado_pies_derecho.getSelectedItem() != null ? rdbGrado_pies_derecho.getSelectedItem().getValue().toString() : "");
                ficha_inicio_lepra.setGrado_ojos_izquierdo(rdbGrado_ojos_izquierdo.getSelectedItem() != null ? rdbGrado_ojos_izquierdo.getSelectedItem().getValue().toString() : "");
                ficha_inicio_lepra.setGrado_manos_izquierdo(rdbGrado_manos_izquierdo.getSelectedItem() != null ? rdbGrado_manos_izquierdo.getSelectedItem().getValue().toString() : "");
                ficha_inicio_lepra.setGrado_pies_izquierdo(rdbGrado_pies_izquierdo.getSelectedItem() != null ? rdbGrado_pies_izquierdo.getSelectedItem().getValue().toString() : "");
                ficha_inicio_lepra.setGrado_maximo_izquierdo(rdbGrado_maximo_izquierdo.getSelectedItem() != null ? rdbGrado_maximo_izquierdo.getSelectedItem().getValue().toString() : "");
                ficha_inicio_lepra.setFecha_diligenciamiento(new Timestamp(dtbxFecha_diligenciamiento.getValue().getTime()));
                ficha_inicio_lepra.setPeriodo_requerido((ibxPeriodo_requerido.getValue() != null ? ibxPeriodo_requerido.getValue() : 0));
                ficha_inicio_lepra.setNro_ingreso(tbxNro_ingreso.getValue());
                ficha_inicio_lepra.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                ficha_inicio_lepra.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                ficha_inicio_lepra.setCreacion_user(usuarios.getCodigo().toString());
                ficha_inicio_lepra.setUltimo_user(usuarios.getCodigo().toString());

                List<Control_ficha_inicio_lepra> lista_detalle = generarControl();
				////log.info("lista_detalle: "+lista_detalle);

                datos.put("ficha_inicio_lepra", ficha_inicio_lepra);
                datos.put("lista_detalle", lista_detalle);
                datos.put("accion", tbxAccion.getText());

                ficha_inicio_lepra = ficha_inicio_lepraService.guardarDatos(datos);
                mostrarDatos(ficha_inicio_lepra);

                /*if(tbxAccion.getText().equalsIgnoreCase("registrar")){
                 ficha_inicio_lepraService.crear(ficha_inicio_lepra);
                 accionForm(true,"registrar");
                 }else{
                 int result = ficha_inicio_lepraService.actualizar(ficha_inicio_lepra);
                 if(result==0){
                 throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
                 }
                 }*/
                MensajesUtil.mensajeInformacion("Informacion ..", "Los datos se guardaron satisfactoriamente");
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    //Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Ficha_inicio_lepra ficha_inicio_lepra = (Ficha_inicio_lepra) obj;
        try {
            Admision admision = new Admision();
            admision.setCodigo_empresa(ficha_inicio_lepra.getCodigo_empresa());
            admision.setCodigo_sucursal(ficha_inicio_lepra.getCodigo_sucursal());
            admision.setNro_identificacion(ficha_inicio_lepra.getNro_identificacion());
            admision.setNro_ingreso(ficha_inicio_lepra.getNro_ingreso());
            admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);

            tbxCodigo_ficha.setValue(ficha_inicio_lepra.getCodigo_ficha());
            tbxNro_identificacion.setValue(ficha_inicio_lepra.getNro_identificacion());
            tbxNomPaciente.setValue(admision.getPaciente().getNombreCompleto());
            cargarInfoPaciente(ficha_inicio_lepra.getNro_identificacion(), admision);
            dtbxFecha_diagnostico.setValue(ficha_inicio_lepra.getFecha_diagnostico());
            for (int i = 0; i < lbxCriterio_diagnostico.getItemCount(); i++) {
                Listitem listitem = lbxCriterio_diagnostico.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getCriterio_diagnostico())) {
                    listitem.setSelected(true);
                    i = lbxCriterio_diagnostico.getItemCount();
                }
            }
            for (int i = 0; i < lbxCausa_ingreso.getItemCount(); i++) {
                Listitem listitem = lbxCausa_ingreso.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getCausa_ingreso())) {
                    listitem.setSelected(true);
                    i = lbxCausa_ingreso.getItemCount();
                }
            }
            for (int i = 0; i < lbxNro_lesiones_derma.getItemCount(); i++) {
                Listitem listitem = lbxNro_lesiones_derma.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getNro_lesiones_derma())) {
                    listitem.setSelected(true);
                    i = lbxNro_lesiones_derma.getItemCount();
                }
            }
            for (int i = 0; i < lbxResultado_bacilo.getItemCount(); i++) {
                Listitem listitem = lbxResultado_bacilo.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getResultado_bacilo())) {
                    listitem.setSelected(true);
                    i = lbxResultado_bacilo.getItemCount();
                }
            }
            tbxResultado_biopsia.setValue(ficha_inicio_lepra.getResultado_biopsia());
            for (int i = 0; i < lbxClasificacion_ingreso.getItemCount(); i++) {
                Listitem listitem = lbxClasificacion_ingreso.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getClasificacion_ingreso())) {
                    listitem.setSelected(true);
                    i = lbxClasificacion_ingreso.getItemCount();
                }
            }
            for (int i = 0; i < lbxFuente_contagio.getItemCount(); i++) {
                Listitem listitem = lbxFuente_contagio.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getFuente_contagio())) {
                    listitem.setSelected(true);
                    i = lbxFuente_contagio.getItemCount();
                }
            }
            tbxPrimeros_sintomas.setValue(ficha_inicio_lepra.getPrimeros_sintomas());
            ibxTiempo_evolucion.setValue(ficha_inicio_lepra.getTiempo_evolucion());
            for (int i = 0; i < lbxTipo_lesiones.getItemCount(); i++) {
                Listitem listitem = lbxTipo_lesiones.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getTipo_lesiones())) {
                    listitem.setSelected(true);
                    i = lbxTipo_lesiones.getItemCount();
                }
            }
            for (int i = 0; i < lbxSensibilidad_lesiones.getItemCount(); i++) {
                Listitem listitem = lbxSensibilidad_lesiones.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(ficha_inicio_lepra.getSensibilidad_lesiones())) {
                    listitem.setSelected(true);
                    i = lbxSensibilidad_lesiones.getItemCount();
                }
            }
            chbNervios_derecho.setChecked(ficha_inicio_lepra.getNervios_derecho());
            chbAuricular_derecho.setChecked(ficha_inicio_lepra.getAuricular_derecho());
            chbCubital_derecho.setChecked(ficha_inicio_lepra.getCubital_derecho());
            chbMediano_derecho.setChecked(ficha_inicio_lepra.getMediano_derecho());
            chbRadial_derecho.setChecked(ficha_inicio_lepra.getRadial_derecho());
            chbCiaticopopliteo_derecho.setChecked(ficha_inicio_lepra.getCiaticopopliteo_derecho());
            chbTivial_derecho.setChecked(ficha_inicio_lepra.getTivial_derecho());
            chbNervios_izquierdo.setChecked(ficha_inicio_lepra.getNervios_izquierdo());
            chbAuricular_izquierdo.setChecked(ficha_inicio_lepra.getAuricular_izquierdo());
            chbCubital_izquierdo.setChecked(ficha_inicio_lepra.getCubital_izquierdo());
            chbMediano_izquierdo.setChecked(ficha_inicio_lepra.getMediano_izquierdo());
            chbRadial_izquierdo.setChecked(ficha_inicio_lepra.getRadial_izquierdo());
            chbCiaticopopliteo_izquierdo.setChecked(ficha_inicio_lepra.getCiaticopopliteo_izquierdo());
            chbTivial_izquierdo.setChecked(ficha_inicio_lepra.getTivial_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_ojos_derecho, ficha_inicio_lepra.getGrado_ojos_derecho());
            Utilidades.seleccionarRadio(rdbGrado_manos_derecho, ficha_inicio_lepra.getGrado_manos_derecho());
            Utilidades.seleccionarRadio(rdbGrado_pies_derecho, ficha_inicio_lepra.getGrado_pies_derecho());
            Utilidades.seleccionarRadio(rdbGrado_ojos_izquierdo, ficha_inicio_lepra.getGrado_ojos_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_manos_izquierdo, ficha_inicio_lepra.getGrado_manos_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_pies_izquierdo, ficha_inicio_lepra.getGrado_pies_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_maximo_izquierdo, ficha_inicio_lepra.getGrado_maximo_izquierdo());
            dtbxFecha_diligenciamiento.setValue(ficha_inicio_lepra.getFecha_diligenciamiento());
            ibxPeriodo_requerido.setValue(ficha_inicio_lepra.getPeriodo_requerido());
            tbxNro_ingreso.setValue(ficha_inicio_lepra.getNro_ingreso());

            Map<String, Object> paramControl = new HashMap<String, Object>();
            paramControl.put("codigo_empresa", ficha_inicio_lepra.getCodigo_empresa());
            paramControl.put("codigo_sucursal", ficha_inicio_lepra.getCodigo_sucursal());
            paramControl.put("codigo_ficha", ficha_inicio_lepra.getCodigo_ficha());
            List<Map<String, Object>> listaAnios = control_ficha_inicio_lepraService.listarAnio(paramControl);

            /*List<Control_ficha_inicio_lepra> lista_detalle = control_ficha_inicio_lepraService.listar(paramControl);
             //log.info("lista_detalle: "+lista_detalle);*/
            cargarControl(listaAnios);

            tbxNro_identificacion.setDisabled(true);
            FormularioUtil.deshabilitarComponentes(this, false, idsExcluyentes);

            //Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void adicionarControl() throws Exception {
        Map<String, Object> mapAnio = new HashMap<String, Object>();

        String anio = lbxAnio.getSelectedItem().getValue();
        if (existeAnio(anio)) {
            MensajesUtil.mensajeAlerta("Alerta !!", "El año seleccionado ya se encuentra registrado");
            return;
        }
        List<String> listaMeses = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            listaMeses.add("");
        }
        mapAnio.put(anio, listaMeses);
        adicionarControl(mapAnio);
    }

    public void adicionarControl(Map<String, Object> mapAnio) {
        try {
            listaControl.add(mapAnio);
            crearFilas();
            // repintarGridCuentas();
        } catch (Exception e) {

            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void crearFilas() throws Exception {
        rowsControl.getChildren().clear();
        for (int j = 0; j < listaControl.size(); j++) {
            Map<String, Object> mapAnio = listaControl.get(j);
            crearFilaControl(mapAnio, j);
        }

    }

    public void crearFilaControl(Map<String, Object> mapAnio, int j) throws Exception {

        final int index = j;

        final Row fila = new Row();
        fila.setStyle("text-align: center;nowrap:nowrap");

        String anio = mapAnio.keySet().iterator().next();
        List<String> listaMeses = (List<String>) mapAnio.get(anio);

        // Columna año //
        Cell cell = new Cell();
        cell.appendChild(new Label(anio));
        fila.appendChild(cell);

		////log.info("año: "+anio);
        int i = 1;
        for (String dia : listaMeses) {

			//String mes = (i<10?"0"+i:""+i);
            Calendar fecha = Calendar.getInstance();
            fecha.set(Calendar.YEAR, Integer.parseInt(anio));
            fecha.set(Calendar.MONTH, (i - 1));
            int diaMaximo = fecha.getActualMaximum(Calendar.DAY_OF_MONTH);
            fecha.set(Calendar.DAY_OF_MONTH, diaMaximo);

            Combobox combobox = new Combobox();
            combobox.setId("mes_" + index + "_" + i);
            combobox.setHflex("1");
            combobox.setReadonly(true);

			////log.info("combobox: "+combobox.getId());
            Comboitem comboitem = new Comboitem();
            comboitem.setValue("");
            comboitem.setLabel("-- --");
            combobox.appendChild(comboitem);

            int selected = 0;
            for (int k = 1; k <= diaMaximo; k++) {
                comboitem = new Comboitem();
                comboitem.setValue(k + "");
                comboitem.setLabel(k + "");
                combobox.appendChild(comboitem);
                if (dia.equals(k + "")) {
                    selected = k;
                }
            }
            combobox.setSelectedIndex(selected);

            cell = new Cell();
            cell.appendChild(combobox);
            fila.appendChild(cell);

            i++;
        }

        // Columna borrar //
        cell = new Cell();
        cell.setAlign("left");
        cell.setValign("top");
        Image img = new Image("/images/borrar.gif");
        img.setId("img_" + j);
        img.setTooltiptext("Quitar registro");
        img.setStyle("cursor:pointer");
        cell.appendChild(img);
        fila.appendChild(cell);

        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {

                Messagebox.show(
                        "Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    actualizarPagina();
                                    listaControl.remove(index);
                                    crearFilas();
                                }
                            }
                        });
            }
        });

        fila.setId("fila_" + j);

        rowsControl.appendChild(fila);

        gridControl.setVisible(true);
        gridControl.setMold("paging");
        gridControl.setPageSize(20);
        gridControl.applyProperties();
        gridControl.invalidate();

    }

    public void actualizarPagina() throws Exception {
        for (int i = 0; i < listaControl.size(); i++) {
            Map<String, Object> mapAnio = listaControl.get(i);

            String anio = mapAnio.keySet().iterator().next();
            List<String> listaMeses = (List<String>) mapAnio.get(anio);

            for (int j = 0; j < listaMeses.size(); j++) {
                String dia = listaMeses.get(j);
                Combobox combobox = (Combobox) groupboxEditar.getFellow("mes_" + i + "_" + (j + 1));
                dia = combobox.getSelectedItem().getValue().toString();
                listaMeses.set(j, dia);
            }
        }
    }

    public boolean existeAnio(String anio) {
        for (int i = 0; i < listaControl.size(); i++) {
            Map<String, Object> mapAnio = listaControl.get(i);

            String anioRegistro = mapAnio.keySet().iterator().next();
            if (anio.equals(anioRegistro)) {
                return true;
            }
        }

        return false;
    }

    public List<Control_ficha_inicio_lepra> generarControl() {
        List<Control_ficha_inicio_lepra> lista_detalle = new LinkedList<Control_ficha_inicio_lepra>();

        for (int i = 0; i < listaControl.size(); i++) {
            Map<String, Object> mapAnio = listaControl.get(i);

            String anio = mapAnio.keySet().iterator().next();
            List<String> listaMeses = (List<String>) mapAnio.get(anio);

            for (int j = 0; j < listaMeses.size(); j++) {
                String dia = listaMeses.get(j);
                if (!dia.isEmpty()) {
                    Calendar fecha = Calendar.getInstance();
                    fecha.set(Calendar.YEAR, Integer.parseInt(anio));
                    fecha.set(Calendar.MONTH, j);
                    fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));

                    Control_ficha_inicio_lepra control = new Control_ficha_inicio_lepra();
                    control.setCodigo_empresa(codigo_empresa);
                    control.setCodigo_sucursal(codigo_sucursal);
                    control.setFecha(new Date(fecha.getTimeInMillis()));
                    lista_detalle.add(control);
                }
            }
        }

        return lista_detalle;
    }

    public void cargarControl(List<Map<String, Object>> listaAnios) throws Exception {
        listaControl.clear();
        int sumContol = 0;
        for (Map<String, Object> map : listaAnios) {
            String anio = (String) map.get("anio");
            Map<String, Object> mapAnio = new HashMap<String, Object>();
            List<String> listaMeses = new ArrayList<String>();
            for (int i = 1; i <= 12; i++) {
                String mes = (i < 10 ? "0" + i : "" + i);
                String dia = "";

                Map<String, Object> paramControl = new HashMap<String, Object>();
                paramControl.put("codigo_empresa", codigo_empresa);
                paramControl.put("codigo_sucursal", codigo_sucursal);
                paramControl.put("codigo_ficha", tbxCodigo_ficha.getValue());
                paramControl.put("anio", anio);
                paramControl.put("mes", mes);
                Map<String, Object> objectDia = control_ficha_inicio_lepraService.consultarDiaMes(paramControl);
                if (objectDia != null) {
                    if (objectDia.get("dia") != null) {
                        dia = (String) objectDia.get("dia");
                        sumContol++;
                    }
                }
                listaMeses.add(dia);
            }

            mapAnio.put(anio, listaMeses);
            listaControl.add(mapAnio);
        }

        ibxNumero_dosis.setValue(sumContol);

        crearFilas();

    }

}
