/*
 * seguimiento_control_pqtAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Control_convivientes_seguimiento;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Seguimiento_control_pqt;
import healthmanager.modelo.service.Seguimiento_control_pqtService;
import com.framework.util.Util;

import java.sql.Timestamp;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.GridGradoDiscapacidadMacro;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Seguimiento_control_pqtAction extends ZKWindow {

    private static Logger log = Logger
            .getLogger(Seguimiento_control_pqtAction.class);

    private Seguimiento_control_pqtService seguimiento_control_pqtService;
	// private Control_convivientes_seguimientoService
    // control_convivientes_seguimientoService;

    private Admision admision;

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

    // @View private Textbox tbxNro_identificacion;
    @View
    private Bandbox tbxNro_identificacion;
    @View
    private Textbox tbxNomPaciente;
    @View
    private Toolbarbutton btnLimpiarPaciente;
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

    @View(isMacro = true)
    private GridGradoDiscapacidadMacro macroDisca1;
    @View(isMacro = true)
    private GridGradoDiscapacidadMacro macroDisca2;

    private Radiogroup rdbGrado_6_ojos_derecho;
    private Radiogroup rdbGrado_6_ojos_izquierdo;
    private Radiogroup rdbGrado_6_manos_derecho;
    private Radiogroup rdbGrado_6_manos_izquierdo;
    private Radiogroup rdbGrado_6_pies_derecho;
    private Radiogroup rdbGrado_6_pies_izquierdo;
    private Radiogroup rdbGrado_6_maximo;
    private Datebox dtbxFecha_6_grado;

    private Radiogroup rdbGrado_12_ojos_derecho;
    private Radiogroup rdbGrado_12_ojos_izquierdo;
    private Radiogroup rdbGrado_12_manos_derecho;
    private Radiogroup rdbGrado_12_manos_izquierdo;
    private Radiogroup rdbGrado_12_pies_derecho;
    private Radiogroup rdbGrado_12_pies_izquierdo;
    private Radiogroup rdbGrado_12_maximo;
    private Datebox dtbxFecha_12_grado;

    @View
    private Checkbox chbPresenta_reaccion_tipo1;
    @View
    private Datebox dtbxFecha_reaccion_tipo1;
    @View
    private Checkbox chbPresenta_reaccion_tipo2;
    @View
    private Datebox dtbxFecha_reaccion_tipo2;

    @View
    private Checkbox chbCharla_lepra;
    @View
    private Checkbox chbCharla_autocuidado;
    @View
    private Checkbox chbAdecuacion_utensilios;
    @View
    private Checkbox chbEntrega_material_educativo;
    @View
    private Checkbox chbUtilizacion_cachuchas;
    @View
    private Checkbox chbUtilizacion_guantes;
    @View
    private Checkbox chbUtilizacion_plantilla;
    @View
    private Checkbox chbUtilizacion_zapatos;

    @View
    private Datebox dtbxFecha_remitido1;
    @View
    private Listbox lbxCodigo_especialidad1;
    @View
    private Datebox dtbxFecha_remitido2;
    @View
    private Listbox lbxCodigo_especialidad2;

    @View
    private Datebox dtbxFecha_diagnostico;
    @View
    private Radiogroup rdbResultado_bacilo;

    @View
    private Checkbox chbTratamiento_pqt;
    @View
    private Datebox dtbxFecha_tratamiento_pqt;
    @View
    private Checkbox chbPaciente_fallecido;
    @View
    private Datebox dtbxFecha_paciente_fallecido;
    @View
    private Checkbox chbPaciente_perdido;
    @View
    private Datebox dtbxFecha_paciente_perdido;
    @View
    private Checkbox chbError_diagnostico;
    @View
    private Datebox dtbxFecha_error_diagnostico;
    @View
    private Checkbox chbTraslado_otro;
    @View
    private Datebox dtbxFecha_traslado_otro;

    @View
    private Textbox tbxNro_ingreso;
    private final String[] idsExcluyentes = {"tbxNro_identificacion",
        "btnLimpiarPaciente", "tbxTipo", "btCancel", "btGuardar",
        "tbxAccion", "btNew", "lbxFormato", "btImprimir", "lbxAnio",
        "tbxTipo_id", "tbxEdad", "tbxOcupacion", "tbxSexo", "tbxPais",
        "tbxDpto", "tbxMun", "tbxAdmin", "tbxTipo_usuario", "tbxDireccion",
        "tbxBarrio", "tbxArea_paciente", "tbxPertenencia_etnica",
        "tbxGrupo_poblacional", "tbxNomPaciente", "lbxParameter",
        "tbxValue"};

    @Override
    public void init() throws Exception {
        inicializarRadios();
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
            //log.info("admision" + admision);

        }
    }

    public void inicializarRadios() {
        rdbGrado_6_ojos_derecho = macroDisca1.getRdbRadioOjosDerechoMc();
        rdbGrado_6_ojos_izquierdo = macroDisca1.getRdbRadioOjosIzquierdoMc();
        rdbGrado_6_manos_derecho = macroDisca1.getRdbRadioManosDerechoMc();
        rdbGrado_6_manos_izquierdo = macroDisca1.getRdbRadioManosIzquierdoMc();
        rdbGrado_6_pies_derecho = macroDisca1.getRdbRadioPiesDerechoMc();
        rdbGrado_6_pies_izquierdo = macroDisca1.getRdbRadioPiesIzquierdoMc();
        rdbGrado_6_maximo = macroDisca1.getRdbRadioGradoMaximoMc();
        dtbxFecha_6_grado = macroDisca1.getDtbFechaMc();

        rdbGrado_12_ojos_derecho = macroDisca2.getRdbRadioOjosDerechoMc();
        rdbGrado_12_ojos_izquierdo = macroDisca2.getRdbRadioOjosIzquierdoMc();
        rdbGrado_12_manos_derecho = macroDisca2.getRdbRadioManosDerechoMc();
        rdbGrado_12_manos_izquierdo = macroDisca2.getRdbRadioManosIzquierdoMc();
        rdbGrado_12_pies_derecho = macroDisca2.getRdbRadioPiesDerechoMc();
        rdbGrado_12_pies_izquierdo = macroDisca2.getRdbRadioPiesIzquierdoMc();
        rdbGrado_12_maximo = macroDisca2.getRdbRadioGradoMaximoMc();
        dtbxFecha_12_grado = macroDisca2.getDtbFechaMc();
    }

    public void buscarRegistros() {

        try {
            //log.info("admision" + admision);

            Seguimiento_control_pqt seguimiento_control_pqtAux = new Seguimiento_control_pqt();
            seguimiento_control_pqtAux.setCodigo_empresa(codigo_empresa);
            seguimiento_control_pqtAux.setCodigo_sucursal(codigo_sucursal);
            seguimiento_control_pqtAux.setNro_identificacion(admision
                    .getNro_identificacion());
            seguimiento_control_pqtAux = seguimiento_control_pqtService
                    .consultar(seguimiento_control_pqtAux);

            if (seguimiento_control_pqtAux == null) {
                tbxNro_identificacion
                        .setValue(admision.getNro_identificacion());
                tbxNomPaciente.setValue(admision.getPaciente()
                        .getNombreCompleto());

                cargarInfoPaciente(admision.getNro_identificacion(), admision);
                FormularioUtil.deshabilitarComponentes(
                        Seguimiento_control_pqtAction.this, false,
                        idsExcluyentes);

            } else {
                mostrarDatos(seguimiento_control_pqtAux);
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
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            if (paciente != null) {
                tbxNro_ingreso.setValue(admision.getNro_ingreso());

                Elemento tipo_id = new Elemento();
                tipo_id.setTipo("tipo_id");
                tipo_id.setCodigo(paciente.getTipo_identificacion());
                tipo_id = getServiceLocator().getElementoService().consultar(
                        tipo_id);
                tbxTipo_id.setValue((tipo_id != null ? tipo_id.getDescripcion()
                        : ""));

                Map<String, Integer> mapa_edades = Util
                        .getEdadYYYYMMDD(paciente.getFecha_nacimiento());
                Integer anios = mapa_edades.get("anios");
                tbxEdad.setValue(anios + (anios == 1 ? " año " : " años "));

                Ocupaciones ocupaciones = new Ocupaciones();
                ocupaciones.setId(paciente.getCodigo_ocupacion());
                ocupaciones = getServiceLocator().getOcupacionesService()
                        .consultar(ocupaciones);
                tbxOcupacion.setValue(ocupaciones != null ? ocupaciones
                        .getNombre() : "");

                tbxSexo.setValue(Utilidades.getNombreElemento(
                        paciente.getSexo(), "sexo",
                        Seguimiento_control_pqtAction.this));

                tbxPais.setValue("Colombia");

                Departamentos departamentos = new Departamentos(
                        paciente.getCodigo_dpto(), "");
                departamentos = getServiceLocator().getDepartamentosService()
                        .consultar(departamentos);

                tbxDpto.setValue((departamentos != null ? departamentos
                        .getNombre() : ""));

                Municipios municipios = new Municipios(
                        paciente.getCodigo_municipio(),
                        paciente.getCodigo_dpto(), "");
                municipios = getServiceLocator().getMunicipiosService()
                        .consultar(municipios);
                tbxMun.setValue((municipios != null ? municipios.getNombre()
                        : ""));

                tbxAdmin.setValue(admision.getAdministradora() != null ? (admision
                        .getAdministradora().getCodigo() + "-" + admision
                        .getAdministradora().getNombre()) : "");

                tbxTipo_usuario.setValue(Utilidades.getNombreElemento(
                        paciente.getTipo_usuario(), "tipo_usuario",
                        Seguimiento_control_pqtAction.this));

                tbxDireccion.setValue((paciente != null ? paciente
                        .getDireccion() : ""));

                Adicional_paciente adicional_paciente = new Adicional_paciente();
                adicional_paciente.setCodigo_empresa(codigo_empresa);
                adicional_paciente.setCodigo_sucursal(codigo_sucursal);
                adicional_paciente.setNro_identificacion(paciente
                        .getNro_identificacion());
                adicional_paciente = getServiceLocator().getServicio(
                        GeneralExtraService.class).consultar(
                                adicional_paciente);

                Barrio barrio = new Barrio();
                barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente
                        .getCodigo_barrio() : "");
                barrio = getServiceLocator().getBarrioService().consultar(
                        barrio);
                tbxBarrio
                        .setValue(barrio != null ? ("("
                                + barrio.getCodigo_barrio() + ") " + barrio
                                .getBarrio()) : "");

                tbxArea_paciente.setValue(Utilidades.getNombreElemento(
                        paciente.getArea_paciente(), "area_paciente",
                        Seguimiento_control_pqtAction.this));

                Pertenencia_etnica etnica = new Pertenencia_etnica();
                etnica.setId(admision.getPaciente().getPertenencia_etnica());
                etnica = getServiceLocator().getPertenencia_etnicaService()
                        .consultar(etnica);

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
        listarEspecialidad(lbxCodigo_especialidad1, true);
        listarEspecialidad(lbxCodigo_especialidad2, true);

        for (int i = 1; i <= 4; i++) {
            Listbox lbxTipo_id = (Listbox) this.getFellow("lbxTipo_id" + i);
            Utilidades.listarElementoListbox(lbxTipo_id, true,
                    getServiceLocator());

            Listbox lbxSexo = (Listbox) this.getFellow("lbxSexo" + i);
            Utilidades
                    .listarElementoListbox(lbxSexo, true, getServiceLocator());

            Listbox lbxBcg = (Listbox) this.getFellow("lbxBcg" + i);
            Utilidades.listarElementoListbox(lbxBcg, true, getServiceLocator());

            Listbox lbxResultado_clinico = (Listbox) this
                    .getFellow("lbxResultado_clinico" + i);
            Utilidades.listarElementoListbox(lbxResultado_clinico, true,
                    getServiceLocator());

            Listbox lbxClasificacion = (Listbox) this
                    .getFellow("lbxClasificacion" + i);
            Utilidades.listarElementoListbox(lbxClasificacion, true,
                    getServiceLocator());
        }
    }

    public void listarEspecialidad(Listbox listbox, boolean select) {
        try {
            listbox.getItems().clear();
            Listitem listitem = null;
            if (select) {
                listitem = new Listitem();
                listitem.setValue("");
                listitem.setLabel("-- seleccione --");
                listbox.appendChild(listitem);
            }

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            List<Especialidad> listaEspecialidades = getServiceLocator()
                    .getEspecialidadService().listar(parameters);
            for (Especialidad especialidad : listaEspecialidades) {
                listitem = new Listitem();
                listitem.setValue(especialidad.getCodigo());
                listitem.setLabel(especialidad.getNombre());
                listbox.appendChild(listitem);
            }

            if (listbox.getItemCount() > 0) {
                listbox.setSelectedIndex(0);
            }
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
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
        } else {
            // buscarDatos();
            limpiarDatos();
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);

        tbxNro_identificacion.setValue("");
        tbxNro_identificacion.setDisabled(false);
        btnLimpiarPaciente.setVisible(false);
        FormularioUtil.deshabilitarComponentes(this, false, idsExcluyentes);

        macroDisca1.limpiar();
        macroDisca2.limpiar();

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

        dtbxFecha_reaccion_tipo1.setValue(null);
        dtbxFecha_reaccion_tipo1.setDisabled(true);

        dtbxFecha_reaccion_tipo2.setValue(null);
        dtbxFecha_reaccion_tipo2.setDisabled(true);

        dtbxFecha_remitido1.setValue(null);
        dtbxFecha_remitido2.setValue(null);

        dtbxFecha_diagnostico.setValue(null);

        dtbxFecha_tratamiento_pqt.setValue(null);
        dtbxFecha_tratamiento_pqt.setDisabled(true);

        dtbxFecha_paciente_fallecido.setValue(null);
        dtbxFecha_paciente_fallecido.setDisabled(true);

        dtbxFecha_paciente_perdido.setValue(null);
        dtbxFecha_paciente_perdido.setDisabled(true);

        dtbxFecha_error_diagnostico.setValue(null);
        dtbxFecha_error_diagnostico.setDisabled(true);

        dtbxFecha_traslado_otro.setValue(null);
        dtbxFecha_traslado_otro.setDisabled(true);

        for (int i = 1; i <= 4; i++) {
            Datebox dtbxFecha_nacimiento = (Datebox) this
                    .getFellow("dtbxFecha_nacimiento" + i);
            dtbxFecha_nacimiento.setValue(null);

            Datebox dtbxFecha_incripcion = (Datebox) this
                    .getFellow("dtbxFecha_incripcion" + i);
            dtbxFecha_incripcion.setValue(null);

            Datebox dtbxFecha_revision = (Datebox) this
                    .getFellow("dtbxFecha_revision" + i);
            dtbxFecha_revision.setValue(null);

            Datebox dtbxDiagnostico_confirmado = (Datebox) this
                    .getFellow("dtbxDiagnostico_confirmado" + i);
            dtbxDiagnostico_confirmado.setValue(null);
        }
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxNro_identificacion
                .setStyle("text-transform:uppercase;background-color:white");

        dtbxFecha_reaccion_tipo1.setStyle("background-color:white");
        dtbxFecha_reaccion_tipo2.setStyle("background-color:white");
        dtbxFecha_tratamiento_pqt.setStyle("background-color:white");
        dtbxFecha_paciente_fallecido.setStyle("background-color:white");
        dtbxFecha_paciente_perdido.setStyle("background-color:white");
        dtbxFecha_error_diagnostico.setStyle("background-color:white");
        dtbxFecha_traslado_otro.setStyle("background-color:white");

        String mensaje = "Los campos marcados con (*) son obligatorios";

        boolean valida = true;

        if (tbxNro_identificacion.getText().trim().equals("")) {
            tbxNro_identificacion
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (valida && chbPresenta_reaccion_tipo1.isChecked()
                && dtbxFecha_reaccion_tipo1.getText().trim().isEmpty()) {
            dtbxFecha_reaccion_tipo1.setStyle("background-color:#F6BBBE");
            mensaje = "Debe digitar la fecha";
            valida = false;
        }

        if (valida && chbPresenta_reaccion_tipo2.isChecked()
                && dtbxFecha_reaccion_tipo2.getText().trim().isEmpty()) {
            dtbxFecha_reaccion_tipo2.setStyle("background-color:#F6BBBE");
            mensaje = "Debe digitar la fecha";
            valida = false;
        }

        if (valida && chbTratamiento_pqt.isChecked()
                && dtbxFecha_tratamiento_pqt.getText().trim().isEmpty()) {
            dtbxFecha_tratamiento_pqt.setStyle("background-color:#F6BBBE");
            mensaje = "Debe digitar la fecha";
            valida = false;
        }

        if (valida && chbPaciente_fallecido.isChecked()
                && dtbxFecha_paciente_fallecido.getText().trim().isEmpty()) {
            dtbxFecha_paciente_fallecido.setStyle("background-color:#F6BBBE");
            mensaje = "Debe digitar la fecha";
            valida = false;
        }

        if (valida && chbPaciente_perdido.isChecked()
                && dtbxFecha_paciente_perdido.getText().trim().isEmpty()) {
            dtbxFecha_paciente_perdido.setStyle("background-color:#F6BBBE");
            mensaje = "Debe digitar la fecha";
            valida = false;
        }

        if (valida && chbError_diagnostico.isChecked()
                && dtbxFecha_error_diagnostico.getText().trim().isEmpty()) {
            dtbxFecha_error_diagnostico.setStyle("background-color:#F6BBBE");
            mensaje = "Debe digitar la fecha";
            valida = false;
        }

        if (valida && chbTraslado_otro.isChecked()
                && dtbxFecha_traslado_otro.getText().trim().isEmpty()) {
            dtbxFecha_traslado_otro.setStyle("background-color:#F6BBBE");
            mensaje = "Debe digitar la fecha";
            valida = false;
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
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            seguimiento_control_pqtService.setLimit("limit 25 offset 0");

            List<Seguimiento_control_pqt> lista_datos = seguimiento_control_pqtService
                    .listar(parameters);
            rowsResultado.getChildren().clear();

            for (Seguimiento_control_pqt seguimiento_control_pqt : lista_datos) {
                rowsResultado.appendChild(crearFilas(seguimiento_control_pqt,
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

        final Seguimiento_control_pqt seguimiento_control_pqt = (Seguimiento_control_pqt) objeto;

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(seguimiento_control_pqt.getCodigo_empresa());
        paciente.setCodigo_sucursal(seguimiento_control_pqt
                .getCodigo_sucursal());
        paciente.setNro_identificacion(seguimiento_control_pqt
                .getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(seguimiento_control_pqt.getCodigo_ficha()
                + ""));
        fila.appendChild(new Label(seguimiento_control_pqt
                .getNro_identificacion() + ""));
        fila.appendChild(new Label((paciente != null ? paciente.getApellido1()
                + " " + paciente.getApellido2() : "")));
        fila.appendChild(new Label((paciente != null ? paciente.getNombre1()
                + " " + paciente.getNombre2() : "")));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(seguimiento_control_pqt);
            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                // Cargamos los componentes //

                Map datos = new HashMap();

                Seguimiento_control_pqt seguimiento_control_pqt = new Seguimiento_control_pqt();
                seguimiento_control_pqt.setCodigo_empresa(empresa
                        .getCodigo_empresa());
                seguimiento_control_pqt.setCodigo_sucursal(sucursal
                        .getCodigo_sucursal());
                seguimiento_control_pqt.setCodigo_ficha(tbxCodigo_ficha
                        .getValue());
                seguimiento_control_pqt
                        .setNro_identificacion(tbxNro_identificacion.getValue());
                seguimiento_control_pqt
                        .setGrado_6_ojos_derecho(rdbGrado_6_ojos_derecho
                                .getSelectedItem() != null ? rdbGrado_6_ojos_derecho
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_6_ojos_izquierdo(rdbGrado_6_ojos_izquierdo
                                .getSelectedItem() != null ? rdbGrado_6_ojos_izquierdo
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_6_manos_derecho(rdbGrado_6_manos_derecho
                                .getSelectedItem() != null ? rdbGrado_6_manos_derecho
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_6_manos_izquierdo(rdbGrado_6_manos_izquierdo
                                .getSelectedItem() != null ? rdbGrado_6_manos_izquierdo
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_6_pies_derecho(rdbGrado_6_pies_derecho
                                .getSelectedItem() != null ? rdbGrado_6_pies_derecho
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_6_pies_izquierdo(rdbGrado_6_pies_izquierdo
                                .getSelectedItem() != null ? rdbGrado_6_pies_izquierdo
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt.setGrado_6_maximo(rdbGrado_6_maximo
                        .getSelectedItem() != null ? rdbGrado_6_maximo
                        .getSelectedItem().getValue().toString() : "");
                seguimiento_control_pqt
                        .setGrado_12_ojos_derecho(rdbGrado_12_ojos_derecho
                                .getSelectedItem() != null ? rdbGrado_12_ojos_derecho
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_12_ojos_izquierdo(rdbGrado_12_ojos_izquierdo
                                .getSelectedItem() != null ? rdbGrado_12_ojos_izquierdo
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_12_manos_derecho(rdbGrado_12_manos_derecho
                                .getSelectedItem() != null ? rdbGrado_12_manos_derecho
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_12_manos_izquierdo(rdbGrado_12_manos_izquierdo
                                .getSelectedItem() != null ? rdbGrado_12_manos_izquierdo
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_12_pies_derecho(rdbGrado_12_pies_derecho
                                .getSelectedItem() != null ? rdbGrado_12_pies_derecho
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt
                        .setGrado_12_pies_izquierdo(rdbGrado_12_pies_izquierdo
                                .getSelectedItem() != null ? rdbGrado_12_pies_izquierdo
                                .getSelectedItem().getValue().toString()
                                : "");
                seguimiento_control_pqt.setGrado_12_maximo(rdbGrado_12_maximo
                        .getSelectedItem() != null ? rdbGrado_12_maximo
                        .getSelectedItem().getValue().toString() : "");
                seguimiento_control_pqt.setFecha_6_grado(dtbxFecha_6_grado
                        .getValue() != null ? new Timestamp(dtbxFecha_6_grado
                                .getValue().getTime()) : null);
                seguimiento_control_pqt.setFecha_12_grado(dtbxFecha_12_grado
                        .getValue() != null ? new Timestamp(dtbxFecha_12_grado
                                .getValue().getTime()) : null);
                seguimiento_control_pqt
                        .setPresenta_reaccion_tipo1(chbPresenta_reaccion_tipo1
                                .isChecked());
                seguimiento_control_pqt
                        .setFecha_reaccion_tipo1(dtbxFecha_reaccion_tipo1
                                .getValue() != null ? new Timestamp(
                                        dtbxFecha_reaccion_tipo1.getValue().getTime())
                                : null);
                seguimiento_control_pqt
                        .setPresenta_reaccion_tipo2(chbPresenta_reaccion_tipo2
                                .isChecked());
                seguimiento_control_pqt
                        .setFecha_reaccion_tipo2(dtbxFecha_reaccion_tipo2
                                .getValue() != null ? new Timestamp(
                                        dtbxFecha_reaccion_tipo2.getValue().getTime())
                                : null);
                seguimiento_control_pqt.setCharla_lepra(chbCharla_lepra
                        .isChecked());
                seguimiento_control_pqt
                        .setCharla_autocuidado(chbCharla_autocuidado
                                .isChecked());
                seguimiento_control_pqt
                        .setAdecuacion_utensilios(chbAdecuacion_utensilios
                                .isChecked());
                seguimiento_control_pqt
                        .setEntrega_material_educativo(chbEntrega_material_educativo
                                .isChecked());
                seguimiento_control_pqt
                        .setUtilizacion_cachuchas(chbUtilizacion_cachuchas
                                .isChecked());
                seguimiento_control_pqt
                        .setUtilizacion_guantes(chbUtilizacion_guantes
                                .isChecked());
                seguimiento_control_pqt
                        .setUtilizacion_plantilla(chbUtilizacion_plantilla
                                .isChecked());
                seguimiento_control_pqt
                        .setUtilizacion_zapatos(chbUtilizacion_zapatos
                                .isChecked());
                seguimiento_control_pqt.setFecha_remitido1(dtbxFecha_remitido1
                        .getValue() != null ? new Timestamp(dtbxFecha_remitido1
                                .getValue().getTime()) : null);
                seguimiento_control_pqt
                        .setCodigo_especialidad1(lbxCodigo_especialidad1
                                .getSelectedItem().getValue().toString());
                seguimiento_control_pqt.setFecha_remitido2(dtbxFecha_remitido2
                        .getValue() != null ? new Timestamp(dtbxFecha_remitido2
                                .getValue().getTime()) : null);
                seguimiento_control_pqt
                        .setCodigo_especialidad2(lbxCodigo_especialidad2
                                .getSelectedItem().getValue().toString());
                seguimiento_control_pqt
                        .setFecha_diagnostico(dtbxFecha_diagnostico.getValue() != null ? new Timestamp(
                                        dtbxFecha_diagnostico.getValue().getTime())
                                : null);
                seguimiento_control_pqt.setResultado_bacilo(rdbResultado_bacilo
                        .getSelectedItem() != null ? rdbResultado_bacilo
                        .getSelectedItem().getValue().toString() : "");
                seguimiento_control_pqt.setTratamiento_pqt(chbTratamiento_pqt
                        .isChecked());
                seguimiento_control_pqt
                        .setFecha_tratamiento_pqt(dtbxFecha_tratamiento_pqt
                                .getValue() != null ? new Timestamp(
                                        dtbxFecha_tratamiento_pqt.getValue().getTime())
                                : null);
                seguimiento_control_pqt
                        .setPaciente_fallecido(chbPaciente_fallecido
                                .isChecked());
                seguimiento_control_pqt
                        .setFecha_paciente_fallecido(dtbxFecha_paciente_fallecido
                                .getValue() != null ? new Timestamp(
                                        dtbxFecha_paciente_fallecido.getValue()
                                        .getTime()) : null);
                seguimiento_control_pqt.setPaciente_perdido(chbPaciente_perdido
                        .isChecked());
                seguimiento_control_pqt
                        .setFecha_paciente_perdido(dtbxFecha_paciente_perdido
                                .getValue() != null ? new Timestamp(
                                        dtbxFecha_paciente_perdido.getValue().getTime())
                                : null);
                seguimiento_control_pqt
                        .setError_diagnostico(chbError_diagnostico.isChecked());
                seguimiento_control_pqt
                        .setFecha_error_diagnostico(dtbxFecha_error_diagnostico
                                .getValue() != null ? new Timestamp(
                                        dtbxFecha_error_diagnostico.getValue()
                                        .getTime()) : null);
                seguimiento_control_pqt.setTraslado_otro(chbTraslado_otro
                        .isChecked());
                seguimiento_control_pqt
                        .setFecha_traslado_otro(dtbxFecha_traslado_otro
                                .getValue() != null ? new Timestamp(
                                        dtbxFecha_traslado_otro.getValue().getTime())
                                : null);
                seguimiento_control_pqt.setNro_ingreso(tbxNro_ingreso
                        .getValue());
                seguimiento_control_pqt.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                seguimiento_control_pqt.setUltimo_update(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                seguimiento_control_pqt.setCreacion_user(usuarios.getCodigo()
                        .toString());
                seguimiento_control_pqt.setUltimo_user(usuarios.getCodigo()
                        .toString());

                List<Control_convivientes_seguimiento> lista_detalle = new LinkedList<Control_convivientes_seguimiento>();
                for (int i = 1; i <= 4; i++) {
                    Textbox tbxNombre = (Textbox) this.getFellow("tbxNombre"
                            + i);
                    Textbox tbxApellido = (Textbox) this
                            .getFellow("tbxApellido" + i);
                    Textbox tbxIdentificacion = (Textbox) this
                            .getFellow("tbxIdentificacion" + i);
                    Listbox lbxTipo_id = (Listbox) this.getFellow("lbxTipo_id"
                            + i);
                    Datebox dtbxFecha_nacimiento = (Datebox) this
                            .getFellow("dtbxFecha_nacimiento" + i);
                    Listbox lbxSexo = (Listbox) this.getFellow("lbxSexo" + i);
                    Datebox dtbxFecha_incripcion = (Datebox) this
                            .getFellow("dtbxFecha_incripcion" + i);
                    Datebox dtbxFecha_revision = (Datebox) this
                            .getFellow("dtbxFecha_revision" + i);
                    Listbox lbxBcg = (Listbox) this.getFellow("lbxBcg" + i);
                    Listbox lbxResultado_clinico = (Listbox) this
                            .getFellow("lbxResultado_clinico" + i);
                    Datebox dtbxDiagnostico_confirmado = (Datebox) this
                            .getFellow("dtbxDiagnostico_confirmado" + i);
                    Listbox lbxClasificacion = (Listbox) this
                            .getFellow("lbxClasificacion" + i);

                    Control_convivientes_seguimiento control_convivientes_seguimiento = new Control_convivientes_seguimiento();
                    control_convivientes_seguimiento
                            .setCodigo_empresa(codigo_empresa);
                    control_convivientes_seguimiento
                            .setCodigo_sucursal(codigo_sucursal);
                    control_convivientes_seguimiento.setCodigo_conviviente(i
                            + "");
                    control_convivientes_seguimiento.setNombre(tbxNombre
                            .getValue().trim());
                    control_convivientes_seguimiento.setApellido(tbxApellido
                            .getValue().trim());
                    control_convivientes_seguimiento
                            .setIdentificacion(tbxIdentificacion.getValue()
                                    .trim());
                    control_convivientes_seguimiento.setTipo_id(lbxTipo_id
                            .getSelectedItem().getValue().toString());
                    control_convivientes_seguimiento
                            .setFecha_nacimiento(dtbxFecha_nacimiento
                                    .getValue() != null ? new Timestamp(
                                            dtbxFecha_nacimiento.getValue().getTime())
                                    : null);
                    control_convivientes_seguimiento.setSexo(lbxSexo
                            .getSelectedItem().getValue().toString());
                    control_convivientes_seguimiento
                            .setFecha_incripcion(dtbxFecha_incripcion
                                    .getValue() != null ? new Timestamp(
                                            dtbxFecha_incripcion.getValue().getTime())
                                    : null);
                    control_convivientes_seguimiento
                            .setFecha_revision(dtbxFecha_revision.getValue() != null ? new Timestamp(
                                            dtbxFecha_revision.getValue().getTime())
                                    : null);
                    control_convivientes_seguimiento.setBcg(lbxBcg
                            .getSelectedItem().getValue().toString());
                    control_convivientes_seguimiento
                            .setResultado_clinico(lbxResultado_clinico
                                    .getSelectedItem().getValue().toString());
                    control_convivientes_seguimiento
                            .setDiagnostico_confirmado(dtbxDiagnostico_confirmado
                                    .getValue() != null ? new Timestamp(
                                            dtbxDiagnostico_confirmado.getValue()
                                            .getTime()) : null);
                    control_convivientes_seguimiento
                            .setClasificacion(lbxClasificacion
                                    .getSelectedItem().getValue().toString());
                    lista_detalle.add(control_convivientes_seguimiento);

                }

                datos.put("seguimiento_control_pqt", seguimiento_control_pqt);
                datos.put("lista_detalle", lista_detalle);
                datos.put("accion", tbxAccion.getValue());
                datos.put("admision", admision);

                seguimiento_control_pqt = seguimiento_control_pqtService
                        .guardarDatos(datos);

                mostrarDatos(seguimiento_control_pqt);

                MensajesUtil.mensajeInformacion("Informacion ..",
                        "Los datos se guardaron satisfactoriamente");
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Seguimiento_control_pqt seguimiento_control_pqt = (Seguimiento_control_pqt) obj;
        try {
            Admision admision = new Admision();
            admision.setCodigo_empresa(seguimiento_control_pqt
                    .getCodigo_empresa());
            admision.setCodigo_sucursal(seguimiento_control_pqt
                    .getCodigo_sucursal());
            admision.setNro_identificacion(seguimiento_control_pqt
                    .getNro_identificacion());
            admision.setNro_ingreso(seguimiento_control_pqt.getNro_ingreso());
            admision = getServiceLocator().getAdmisionService().consultar(
                    admision);

            tbxCodigo_ficha.setValue(seguimiento_control_pqt.getCodigo_ficha());
            tbxNro_identificacion.setValue(seguimiento_control_pqt
                    .getNro_identificacion());
            tbxNomPaciente.setValue(admision.getPaciente().getNombreCompleto());

            cargarInfoPaciente(seguimiento_control_pqt.getNro_identificacion(),
                    admision);

            Utilidades.seleccionarRadio(rdbGrado_6_ojos_derecho,
                    seguimiento_control_pqt.getGrado_6_ojos_derecho());
            Utilidades.seleccionarRadio(rdbGrado_6_ojos_izquierdo,
                    seguimiento_control_pqt.getGrado_6_ojos_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_6_manos_derecho,
                    seguimiento_control_pqt.getGrado_6_manos_derecho());
            Utilidades.seleccionarRadio(rdbGrado_6_manos_izquierdo,
                    seguimiento_control_pqt.getGrado_6_manos_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_6_pies_derecho,
                    seguimiento_control_pqt.getGrado_6_pies_derecho());
            Utilidades.seleccionarRadio(rdbGrado_6_pies_izquierdo,
                    seguimiento_control_pqt.getGrado_6_pies_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_6_maximo,
                    seguimiento_control_pqt.getGrado_6_maximo());
            Utilidades.seleccionarRadio(rdbGrado_12_ojos_derecho,
                    seguimiento_control_pqt.getGrado_12_ojos_derecho());
            Utilidades.seleccionarRadio(rdbGrado_12_ojos_izquierdo,
                    seguimiento_control_pqt.getGrado_12_ojos_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_12_manos_derecho,
                    seguimiento_control_pqt.getGrado_12_manos_derecho());
            Utilidades.seleccionarRadio(rdbGrado_12_manos_izquierdo,
                    seguimiento_control_pqt.getGrado_12_manos_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_12_pies_derecho,
                    seguimiento_control_pqt.getGrado_12_pies_derecho());
            Utilidades.seleccionarRadio(rdbGrado_12_pies_izquierdo,
                    seguimiento_control_pqt.getGrado_12_pies_izquierdo());
            Utilidades.seleccionarRadio(rdbGrado_12_maximo,
                    seguimiento_control_pqt.getGrado_12_maximo());
            dtbxFecha_6_grado.setValue(seguimiento_control_pqt
                    .getFecha_6_grado());
            dtbxFecha_12_grado.setValue(seguimiento_control_pqt
                    .getFecha_12_grado());
            chbPresenta_reaccion_tipo1.setChecked(seguimiento_control_pqt
                    .getPresenta_reaccion_tipo1());
            dtbxFecha_reaccion_tipo1.setValue(seguimiento_control_pqt
                    .getFecha_reaccion_tipo1());
            chbPresenta_reaccion_tipo2.setChecked(seguimiento_control_pqt
                    .getPresenta_reaccion_tipo2());
            dtbxFecha_reaccion_tipo2.setValue(seguimiento_control_pqt
                    .getFecha_reaccion_tipo2());
            chbCharla_lepra.setChecked(seguimiento_control_pqt
                    .getCharla_lepra());
            chbCharla_autocuidado.setChecked(seguimiento_control_pqt
                    .getCharla_autocuidado());
            chbAdecuacion_utensilios.setChecked(seguimiento_control_pqt
                    .getAdecuacion_utensilios());
            chbEntrega_material_educativo.setChecked(seguimiento_control_pqt
                    .getEntrega_material_educativo());
            chbUtilizacion_cachuchas.setChecked(seguimiento_control_pqt
                    .getUtilizacion_cachuchas());
            chbUtilizacion_guantes.setChecked(seguimiento_control_pqt
                    .getUtilizacion_guantes());
            chbUtilizacion_plantilla.setChecked(seguimiento_control_pqt
                    .getUtilizacion_plantilla());
            chbUtilizacion_zapatos.setChecked(seguimiento_control_pqt
                    .getUtilizacion_zapatos());
            dtbxFecha_remitido1.setValue(seguimiento_control_pqt
                    .getFecha_remitido1());
            for (int i = 0; i < lbxCodigo_especialidad1.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_especialidad1.getItemAtIndex(i);
                if (listitem
                        .getValue()
                        .toString()
                        .equals(seguimiento_control_pqt
                                .getCodigo_especialidad1())) {
                    listitem.setSelected(true);
                    i = lbxCodigo_especialidad1.getItemCount();
                }
            }
            dtbxFecha_remitido2.setValue(seguimiento_control_pqt
                    .getFecha_remitido2());
            for (int i = 0; i < lbxCodigo_especialidad2.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_especialidad2.getItemAtIndex(i);
                if (listitem
                        .getValue()
                        .toString()
                        .equals(seguimiento_control_pqt
                                .getCodigo_especialidad2())) {
                    listitem.setSelected(true);
                    i = lbxCodigo_especialidad2.getItemCount();
                }
            }
            dtbxFecha_diagnostico.setValue(seguimiento_control_pqt
                    .getFecha_diagnostico());
            Utilidades.seleccionarRadio(rdbResultado_bacilo,
                    seguimiento_control_pqt.getResultado_bacilo());
            chbTratamiento_pqt.setChecked(seguimiento_control_pqt
                    .getTratamiento_pqt());
            dtbxFecha_tratamiento_pqt.setValue(seguimiento_control_pqt
                    .getFecha_tratamiento_pqt());
            chbPaciente_fallecido.setChecked(seguimiento_control_pqt
                    .getPaciente_fallecido());
            dtbxFecha_paciente_fallecido.setValue(seguimiento_control_pqt
                    .getFecha_paciente_fallecido());
            chbPaciente_perdido.setChecked(seguimiento_control_pqt
                    .getPaciente_perdido());
            dtbxFecha_paciente_perdido.setValue(seguimiento_control_pqt
                    .getFecha_paciente_perdido());
            chbError_diagnostico.setChecked(seguimiento_control_pqt
                    .getError_diagnostico());
            dtbxFecha_error_diagnostico.setValue(seguimiento_control_pqt
                    .getFecha_error_diagnostico());
            chbTraslado_otro.setChecked(seguimiento_control_pqt
                    .getTraslado_otro());
            dtbxFecha_traslado_otro.setValue(seguimiento_control_pqt
                    .getFecha_traslado_otro());
            tbxNro_ingreso.setValue(seguimiento_control_pqt.getNro_ingreso());

            seleccionarCheckFecha(chbPresenta_reaccion_tipo1.isChecked(),
                    dtbxFecha_reaccion_tipo1);
            seleccionarCheckFecha(chbPresenta_reaccion_tipo2.isChecked(),
                    dtbxFecha_reaccion_tipo2);

            seleccionarCheckFecha(chbTratamiento_pqt.isChecked(),
                    dtbxFecha_tratamiento_pqt);
            seleccionarCheckFecha(chbPaciente_fallecido.isChecked(),
                    dtbxFecha_paciente_fallecido);
            seleccionarCheckFecha(chbPaciente_perdido.isChecked(),
                    dtbxFecha_paciente_perdido);
            seleccionarCheckFecha(chbError_diagnostico.isChecked(),
                    dtbxFecha_error_diagnostico);
            seleccionarCheckFecha(chbTraslado_otro.isChecked(),
                    dtbxFecha_traslado_otro);

            tbxNro_identificacion.setDisabled(true);
            btnLimpiarPaciente.setVisible(false);
            FormularioUtil.deshabilitarComponentes(this, false, idsExcluyentes);

            List<Control_convivientes_seguimiento> lista_detalle = seguimiento_control_pqt
                    .getLista_detalle();
            for (int i = 0; i < lista_detalle.size(); i++) {

                Control_convivientes_seguimiento control_convivientes_seguimiento = lista_detalle
                        .get(i);

                Textbox tbxNombre = (Textbox) this.getFellow("tbxNombre"
                        + (i + 1));
                Textbox tbxApellido = (Textbox) this.getFellow("tbxApellido"
                        + (i + 1));
                Textbox tbxIdentificacion = (Textbox) this
                        .getFellow("tbxIdentificacion" + (i + 1));
                Listbox lbxTipo_id = (Listbox) this.getFellow("lbxTipo_id"
                        + (i + 1));
                Datebox dtbxFecha_nacimiento = (Datebox) this
                        .getFellow("dtbxFecha_nacimiento" + (i + 1));
                Listbox lbxSexo = (Listbox) this.getFellow("lbxSexo" + (i + 1));
                Datebox dtbxFecha_incripcion = (Datebox) this
                        .getFellow("dtbxFecha_incripcion" + (i + 1));
                Datebox dtbxFecha_revision = (Datebox) this
                        .getFellow("dtbxFecha_revision" + (i + 1));
                Listbox lbxBcg = (Listbox) this.getFellow("lbxBcg" + (i + 1));
                Listbox lbxResultado_clinico = (Listbox) this
                        .getFellow("lbxResultado_clinico" + (i + 1));
                Datebox dtbxDiagnostico_confirmado = (Datebox) this
                        .getFellow("dtbxDiagnostico_confirmado" + (i + 1));
                Listbox lbxClasificacion = (Listbox) this
                        .getFellow("lbxClasificacion" + (i + 1));

                tbxNombre
                        .setValue(control_convivientes_seguimiento.getNombre());
                tbxApellido.setValue(control_convivientes_seguimiento
                        .getApellido());
                tbxIdentificacion.setValue(control_convivientes_seguimiento
                        .getIdentificacion());
                Utilidades.seleccionarListitem(lbxTipo_id,
                        control_convivientes_seguimiento.getTipo_id());
                dtbxFecha_nacimiento.setValue(control_convivientes_seguimiento
                        .getFecha_nacimiento());
                Utilidades.seleccionarListitem(lbxSexo,
                        control_convivientes_seguimiento.getSexo());
                dtbxFecha_incripcion.setValue(control_convivientes_seguimiento
                        .getFecha_incripcion());
                dtbxFecha_revision.setValue(control_convivientes_seguimiento
                        .getFecha_revision());
                Utilidades.seleccionarListitem(lbxBcg,
                        control_convivientes_seguimiento.getBcg());
                Utilidades
                        .seleccionarListitem(lbxResultado_clinico,
                                control_convivientes_seguimiento
                                .getResultado_clinico());
                dtbxDiagnostico_confirmado
                        .setValue(control_convivientes_seguimiento
                                .getDiagnostico_confirmado());
                Utilidades.seleccionarListitem(lbxClasificacion,
                        control_convivientes_seguimiento.getClasificacion());
            }

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Seguimiento_control_pqt seguimiento_control_pqt = (Seguimiento_control_pqt) obj;
        try {
            int result = seguimiento_control_pqtService
                    .eliminar(seguimiento_control_pqt);
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

    public void seleccionarCheckFecha(boolean checked, Datebox datebox) {
        datebox.setDisabled(!checked);
        if (!checked) {
            datebox.setValue(null);
        }
    }
}
