/*
 * seguimiento_convivientes_lepraAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Control_convivientes_seguimiento;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Seguimiento_control_pqt;
import healthmanager.modelo.bean.Seguimiento_convivientes_lepra;
import healthmanager.modelo.service.Control_convivientes_seguimientoService;
import healthmanager.modelo.service.Seguimiento_control_pqtService;
import healthmanager.modelo.service.Seguimiento_convivientes_lepraService;
import com.framework.util.Util;

import java.sql.Timestamp;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
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

public class Seguimiento_convivientes_lepraAction extends ZKWindow {

    private static Logger log = Logger
            .getLogger(Seguimiento_convivientes_lepraAction.class);

    private Seguimiento_convivientes_lepraService seguimiento_convivientes_lepraService;
    private Seguimiento_control_pqtService seguimiento_control_pqtService;
    private Control_convivientes_seguimientoService control_convivientes_seguimientoService;

    // Componentes //
    private Admision admision;

	// private Seguimiento_convivientes_lepraService
    // seguimiento_convivientes_lepraService;
    // nuevos campos manuel
    @View
    private Textbox tbxNombre1;
    @View
    private Textbox tbxNombre2;
    @View
    private Textbox tbxNombre3;
    @View
    private Textbox tbxNombre4;
    @View
    private Textbox tbxApellido1;
    @View
    private Textbox tbxApellido2;
    @View
    private Textbox tbxApellido3;
    @View
    private Textbox tbxApellido4;
    @View
    private Textbox tbxIdentificacion1;
    @View
    private Textbox tbxIdentificacion2;
    @View
    private Textbox tbxIdentificacion3;
    @View
    private Textbox tbxIdentificacion4;
    @View
    private Listbox lbxTipo_id1;
    @View
    private Listbox lbxTipo_id2;
    @View
    private Listbox lbxTipo_id3;
    @View
    private Listbox lbxTipo_id4;
    @View
    private Datebox dtbxFecha_nacimiento1;
    @View
    private Datebox dtbxFecha_nacimiento2;
    @View
    private Datebox dtbxFecha_nacimiento3;
    @View
    private Datebox dtbxFecha_nacimiento4;
    @View
    private Listbox lbxSexo1;
    @View
    private Listbox lbxSexo2;
    @View
    private Listbox lbxSexo3;
    @View
    private Listbox lbxSexo4;

	// @View private Textbox tbxNro_identificacion;
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
    // @View(isMacro = true)
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

    private final String[] idsExcluyentes = {"tbxNro_identificacion",
        "btnLimpiarPaciente", "tbxTipo", "btCancel", "btGuardar",
        "tbxAccion", "btNew", "lbxFormato", "btImprimir", "lbxAnio",
        "tbxTipo_id", "tbxEdad", "tbxOcupacion", "tbxSexo", "tbxPais",
        "tbxDpto", "tbxMun", "tbxAdmin", "tbxTipo_usuario", "tbxDireccion",
        "tbxBarrio", "tbxArea_paciente", "tbxPertenencia_etnica",
        "tbxGrupo_poblacional", "tbxNomPaciente", "lbxParameter",
        "tbxValue"};

    @View
    private Textbox tbxNro_ingreso;

    @View
    private Groupbox gbxEvoluion;

    @Override
    public void init() throws Exception {
        listarCombos();
        buscarRegistros();

        accionForm(true, "registrar");
        cargarInfoPaciente(admision.getNro_identificacion(), admision);
        cargarDatosIniciales();
    }

    /*
     * private void inicializarBandboxPaciente() {
     * tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
     * tbxNro_identificacion .setBandboxRegistrosIMG(new
     * BandboxRegistrosIMG<Admision>() {
     * 
     * @Override public void renderListitem(Listitem listitem, Admision
     * registro) { Paciente paciente = new Paciente();
     * paciente.setCodigo_empresa(registro.getCodigo_empresa());
     * paciente.setCodigo_sucursal(registro .getCodigo_sucursal());
     * paciente.setNro_identificacion(registro .getNro_identificacion());
     * paciente = getServiceLocator().getPacienteService() .consultar(paciente);
     * 
     * String apellidos = (paciente != null ? paciente .getApellido1() + " " +
     * paciente.getApellido2() : ""); String nombres = (paciente != null ?
     * paciente .getNombre1() + " " + paciente.getNombre2() : "");
     * 
     * String estado = (registro.getEstado().equals("1") ? "Activo" :
     * "Terminada");
     * 
     * listitem.setValue(registro);
     * 
     * Listcell listcell = new Listcell(); listcell.appendChild(new
     * Label(registro .getNro_ingreso() + "")); listitem.appendChild(listcell);
     * 
     * listcell = new Listcell(); listcell.appendChild(new Label(registro
     * .getNro_identificacion())); listitem.appendChild(listcell);
     * 
     * listcell = new Listcell(); listcell.appendChild(new Label(apellidos));
     * listitem.appendChild(listcell);
     * 
     * listcell = new Listcell(); listcell.appendChild(new Label(nombres));
     * listitem.appendChild(listcell);
     * 
     * listcell = new Listcell(); listcell.appendChild(new Label(new
     * SimpleDateFormat( "yyyy-MM-dd HH:mm").format(registro
     * .getFecha_ingreso()))); listitem.appendChild(listcell);
     * 
     * listcell = new Listcell(); listcell.appendChild(new Label(estado));
     * listitem.appendChild(listcell);
     * 
     * }
     * 
     * @Override public List<Admision> listarRegistros(String valorBusqueda,
     * Map<String, Object> parametros) { parametros.put("codigo_empresa",
     * empresa.getCodigo_empresa()); parametros.put("codigo_sucursal",
     * sucursal.getCodigo_sucursal()); parametros.put("paramTodo", "");
     * parametros.put("value", "%" + valorBusqueda.toUpperCase().trim() + "%");
     * parametros.put("estado", "1");
     * 
     * getServiceLocator().getAdmisionService().setLimit( "limit 25 offset 0");
     * 
     * List<Admision> list = getServiceLocator()
     * .getAdmisionService().listar(parametros);
     * 
     * return list; }
     * 
     * @Override public boolean seleccionarRegistro(Bandbox bandbox, Textbox
     * textboxInformacion, Admision registro) {
     * 
     * try { Seguimiento_control_pqt seguimiento_control_pqtAux = new
     * Seguimiento_control_pqt(); seguimiento_control_pqtAux
     * .setCodigo_empresa(codigo_empresa); seguimiento_control_pqtAux
     * .setCodigo_sucursal(codigo_sucursal); seguimiento_control_pqtAux
     * .setNro_identificacion(registro .getNro_identificacion()); //
     * seguimiento_control_pqtAux.setNro_ingreso(registro.getNro_ingreso());
     * seguimiento_control_pqtAux = seguimiento_control_pqtService
     * .consultar(seguimiento_control_pqtAux);
     * //log.info("seguimiento_control_pqtAux" + seguimiento_control_pqtAux);
     * 
     * if (seguimiento_control_pqtAux == null) {
     * 
     * tbxAccion.setText("registrar"); accionForm(true, "registrar");
     * 
     * bandbox.setValue(registro .getNro_identificacion());
     * tbxNomPaciente.setValue(registro.getPaciente() .getNombreCompleto());
     * cargarInfoPaciente( registro.getNro_identificacion(), registro);
     * 
     * MensajesUtil .notificarAlerta(
     * "Diligenciar ficha de seguimiento y control del paciente durante la PQT",
     * tbxNomPaciente);
     * 
     * //
     * FormularioUtil.deshabilitarComponentes(Seguimiento_convivientes_lepraAction
     * .this, // false, idsExcluyentes);
     * 
     * // limpiarConvivientes();
     * 
     * } else { mostrarDatos(seguimiento_control_pqtAux, admision);
     * FormularioUtil.deshabilitarComponentes( gbxEvoluion, false,
     * idsExcluyentes);
     * 
     * } } catch (Exception e) { log.error(e.getMessage(), e); }
     * 
     * return true; }
     * 
     * @Override public void limpiarSeleccion(Bandbox bandbox) {
     * 
     * try { limpiarDatos(); } catch (Exception e) { //log.info(e.getMessage(),
     * e); }
     * 
     * // FormularioUtil.deshabilitarComponentes(Ficha_inicio_lepraAction.this,
     * // true, idsExcluyentes); } }); }
     */
    private void cargarInfoPaciente(String nro_identificacion, Admision admision) {
        try {
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(codigo_empresa);
            paciente.setCodigo_sucursal(codigo_sucursal);
            paciente.setNro_identificacion(nro_identificacion);
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

			//log.info("Paciente >>>>>>>>>>>>>>>>" + paciente);
            if (paciente != null) {
                tbxNro_ingreso.setValue(admision.getNro_ingreso());
                tbxNro_identificacion
                        .setValue(paciente.getNro_identificacion());
                tbxNomPaciente.setValue(paciente.getNombre1() + " "
                        + paciente.getNombre2() + " " + paciente.getApellido1()
                        + " " + paciente.getApellido2());
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
                        Seguimiento_convivientes_lepraAction.this));

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
                        Seguimiento_convivientes_lepraAction.this));

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
                        Seguimiento_convivientes_lepraAction.this));

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

        for (int i = 1; i <= 4; i++) {
            Listbox lbxTipo_id = (Listbox) this.getFellow("lbxTipo_id" + i);
            Utilidades.listarElementoListbox(lbxTipo_id, true,
                    getServiceLocator());

            Listbox lbxSexo = (Listbox) this.getFellow("lbxSexo" + i);
            Utilidades
                    .listarElementoListbox(lbxSexo, true, getServiceLocator());

        }

        for (int j = 1; j <= 5; j++) {
            for (int i = 1; i <= 4; i++) {
                Listbox lbxResultado_clinico = (Listbox) this
                        .getFellow("lbxResultado_clinico" + j + "_" + i);
                Utilidades.listarElementoListbox(lbxResultado_clinico, true,
                        getServiceLocator());

                Listbox lbxEgreso = (Listbox) this.getFellow("lbxEgreso" + j
                        + "_" + i);
                Utilidades.listarElementoListbox(lbxEgreso, true,
                        getServiceLocator());

            }
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
            buscarDatos();
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

        tbxNomPaciente.setValue("");
        // tbxNro_ingreso.setValue("");
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

        limpiarConvivientes();
        limpiarControlConvivientes();

    }

    public void limpiarConvivientes() {

        for (int i = 1; i <= 4; i++) {
            Textbox tbxNombre = (Textbox) this.getFellow("tbxNombre" + (i));
            Textbox tbxApellido = (Textbox) this.getFellow("tbxApellido" + (i));
            Textbox tbxIdentificacion = (Textbox) this
                    .getFellow("tbxIdentificacion" + (i));
            Listbox lbxTipo_id = (Listbox) this.getFellow("lbxTipo_id" + (i));
            Datebox dtbxFecha_nacimiento = (Datebox) this
                    .getFellow("dtbxFecha_nacimiento" + (i));
            Listbox lbxSexo = (Listbox) this.getFellow("lbxSexo" + (i));

            tbxNombre.setValue("");
            tbxApellido.setValue("");
            tbxIdentificacion.setValue("");
            Utilidades.listarElementoListbox(lbxTipo_id, true,
                    getServiceLocator());
            dtbxFecha_nacimiento.setValue(null);
            Utilidades
                    .listarElementoListbox(lbxSexo, true, getServiceLocator());

        }

    }

    public void limpiarControlConvivientes() {
        for (int j = 1; j <= 5; j++) {
            for (int i = 1; i <= 4; i++) {
                Datebox dtbxFecha_control = (Datebox) this
                        .getFellow("dtbxFecha_control" + j + "_" + i);
                Listbox lbxResultado_clinico = (Listbox) this
                        .getFellow("lbxResultado_clinico" + j + "_" + i);
                Listbox lbxEgreso = (Listbox) this.getFellow("lbxEgreso" + j
                        + "_" + i);

                dtbxFecha_control.setValue(null);
                Utilidades.listarElementoListbox(lbxResultado_clinico, true,
                        getServiceLocator());
                Utilidades.listarElementoListbox(lbxEgreso, true,
                        getServiceLocator());

            }
        }
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxNro_identificacion
                .setStyle("text-transform:uppercase;background-color:white");

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
                mostrarDatos(seguimiento_control_pqt, admision);
                FormularioUtil.deshabilitarComponentes(gbxEvoluion, false,
                        idsExcluyentes);

            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    public void cargarDatosIniciales() {
        Map<String, Object> datos = new HashMap<String, Object>();
        Seguimiento_control_pqt seguimiento_control_pqt = new Seguimiento_control_pqt();
        seguimiento_control_pqt.setNro_identificacion(admision
                .getNro_identificacion());
        seguimiento_control_pqt.setCodigo_sucursal(admision
                .getCodigo_sucursal());
        seguimiento_control_pqt.setCodigo_empresa(admision.getCodigo_empresa());
        seguimiento_control_pqt = seguimiento_control_pqtService
                .consultar(seguimiento_control_pqt);
        if (seguimiento_control_pqt != null) {
            datos.put("codigo_empresa",
                    seguimiento_control_pqt.getCodigo_empresa());
            datos.put("codigo_sucursal",
                    seguimiento_control_pqt.getCodigo_sucursal());
            datos.put("codigo_ficha", seguimiento_control_pqt.getCodigo_ficha());
            tbxCodigo_ficha.setValue(seguimiento_control_pqt.getCodigo_ficha());

        }
        List<Control_convivientes_seguimiento> lista_detalle = control_convivientes_seguimientoService
                .listar(datos);

        tbxNombre1.setValue(lista_detalle.get(0).getNombre());
        tbxApellido1.setValue(lista_detalle.get(0).getApellido());
        tbxIdentificacion1.setValue(lista_detalle.get(0).getIdentificacion());
        Utilidades.seleccionarListitem(lbxTipo_id1, lista_detalle.get(0)
                .getTipo_id());
        dtbxFecha_nacimiento1.setValue(lista_detalle.get(0)
                .getFecha_nacimiento());
        Utilidades
                .seleccionarListitem(lbxSexo1, lista_detalle.get(0).getSexo());

        tbxNombre2.setValue(lista_detalle.get(1).getNombre());
        tbxApellido2.setValue(lista_detalle.get(1).getApellido());
        tbxIdentificacion2.setValue(lista_detalle.get(1).getIdentificacion());
        Utilidades.seleccionarListitem(lbxTipo_id2, lista_detalle.get(1)
                .getTipo_id());
        dtbxFecha_nacimiento2.setValue(lista_detalle.get(1)
                .getFecha_nacimiento());
        Utilidades
                .seleccionarListitem(lbxSexo2, lista_detalle.get(1).getSexo());

        tbxNombre3.setValue(lista_detalle.get(2).getNombre());
        tbxApellido3.setValue(lista_detalle.get(2).getApellido());
        tbxIdentificacion3.setValue(lista_detalle.get(2).getIdentificacion());
        Utilidades.seleccionarListitem(lbxTipo_id3, lista_detalle.get(2)
                .getTipo_id());
        dtbxFecha_nacimiento3.setValue(lista_detalle.get(2)
                .getFecha_nacimiento());
        Utilidades
                .seleccionarListitem(lbxSexo3, lista_detalle.get(2).getSexo());

        tbxNombre4.setValue(lista_detalle.get(3).getNombre());
        tbxApellido4.setValue(lista_detalle.get(3).getApellido());
        tbxIdentificacion4.setValue(lista_detalle.get(3).getIdentificacion());
        Utilidades.seleccionarListitem(lbxTipo_id4, lista_detalle.get(3)
                .getTipo_id());
        dtbxFecha_nacimiento4.setValue(lista_detalle.get(3)
                .getFecha_nacimiento());
        Utilidades
                .seleccionarListitem(lbxSexo4, lista_detalle.get(3).getSexo());
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                // Cargamos los componentes //

                Seguimiento_control_pqt seguimiento_control_pqt = new Seguimiento_control_pqt();
                seguimiento_control_pqt.setNro_identificacion(admision
                        .getNro_identificacion());
                seguimiento_control_pqt.setCodigo_sucursal(admision
                        .getCodigo_sucursal());
                seguimiento_control_pqt.setCodigo_empresa(admision
                        .getCodigo_empresa());
                seguimiento_control_pqt = seguimiento_control_pqtService
                        .consultar(seguimiento_control_pqt);
                if (seguimiento_control_pqt != null) {

                    tbxCodigo_ficha.setValue(seguimiento_control_pqt
                            .getCodigo_ficha());
                }
                Map datos = new HashMap();
                Seguimiento_convivientes_lepra seguimiento_convivientes_lepra = new Seguimiento_convivientes_lepra();
                seguimiento_convivientes_lepra
                        .setCodigo_empresa(codigo_empresa);
                seguimiento_convivientes_lepra
                        .setCodigo_sucursal(codigo_sucursal);
                seguimiento_convivientes_lepra.setCodigo_ficha(tbxCodigo_ficha
                        .getValue().toString());

                List<Seguimiento_convivientes_lepra> lista_detalle = new LinkedList<Seguimiento_convivientes_lepra>();

                for (int j = 1; j <= 5; j++) {
                    for (int i = 1; i <= 4; i++) {
                        Datebox dtbxFecha_control = (Datebox) this
                                .getFellow("dtbxFecha_control" + j + "_" + i);
                        Listbox lbxResultado_clinico = (Listbox) this
                                .getFellow("lbxResultado_clinico" + j + "_" + i);
                        Listbox lbxEgreso = (Listbox) this
                                .getFellow("lbxEgreso" + j + "_" + i);

                        Seguimiento_convivientes_lepra seguimiento_convivientes_lepraAux = new Seguimiento_convivientes_lepra();
                        seguimiento_convivientes_lepraAux.setAnio(j + "");
                        seguimiento_convivientes_lepraAux
                                .setFecha_control(dtbxFecha_control.getValue() != null ? new Timestamp(
                                                dtbxFecha_control.getValue().getTime())
                                        : null);
                        seguimiento_convivientes_lepraAux
                                .setResultado_clinico(lbxResultado_clinico
                                        .getSelectedItem().getValue()
                                        .toString());
                        seguimiento_convivientes_lepraAux.setEgreso(lbxEgreso
                                .getSelectedItem().getValue().toString());

                        lista_detalle.add(seguimiento_convivientes_lepraAux);

                    }
                }

                datos.put("seguimiento_convivientes_lepra",
                        seguimiento_convivientes_lepra);
                datos.put("lista_detalle", lista_detalle);
                datos.put("accion", tbxAccion.getValue());

                seguimiento_convivientes_lepra = seguimiento_convivientes_lepraService
                        .guardarDatos(datos);

				// String codigo_ficha =
                // seguimiento_convivientes_lepra.getCodigo_ficha();
                //log.info("codigo_ficha" + tbxCodigo_ficha.getValue());
                mostrarControlConvivientes(tbxCodigo_ficha.getValue());

                MensajesUtil.mensajeInformacion("Informacion ..",
                        "Los datos se guardaron satisfactoriamente");
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj, Admision admision) throws Exception {
        Seguimiento_control_pqt seguimiento_control_pqt = (Seguimiento_control_pqt) obj;
        try {

            // Admision admision = new Admision();
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
			//log.info("CODIGO DE LA FICHA>>>>>>>>>>"
            //+ tbxCodigo_ficha.getValue().toString());
            tbxNro_identificacion.setValue(seguimiento_control_pqt
                    .getNro_identificacion());
            tbxNomPaciente.setValue(admision.getPaciente().getNombreCompleto());

            cargarInfoPaciente(seguimiento_control_pqt.getNro_identificacion(),
                    admision);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            parameters.put("codigo_ficha",
                    seguimiento_control_pqt.getCodigo_ficha());

            control_convivientes_seguimientoService
                    .setLimit("limit 25 offset 0");

            List<Control_convivientes_seguimiento> lista_datos = control_convivientes_seguimientoService
                    .listar(parameters);
			//log.info("lista_datos" + lista_datos);
            //log.info("lista_datos.size()" + lista_datos.size());

            for (int i = 0; i < lista_datos.size(); i++) {

                Control_convivientes_seguimiento control_convivientes_seguimiento = lista_datos
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
            }

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());

            mostrarControlConvivientes(seguimiento_control_pqt
                    .getCodigo_ficha());
            deshabilitarConvivientes(seguimiento_control_pqt.getCodigo_ficha());

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void mostrarControlConvivientes(String ficha) {

        //log.info("ficha" + ficha);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("codigo_empresa", codigo_empresa);
        parameters.put("codigo_sucursal", codigo_sucursal);
        parameters.put("codigo_ficha", ficha);

        seguimiento_convivientes_lepraService.setLimit("limit 25 offset 0");

        List<Seguimiento_convivientes_lepra> lista_datos = seguimiento_convivientes_lepraService
                .listar(parameters);
        //log.info("lista_datos" + lista_datos);

        if (lista_datos != null) {
            if (!lista_datos.isEmpty()) {
                int cont = 0;
                for (int j = 1; j <= 5; j++) {
                    for (int i = 1; i <= 4; i++) {

                        Seguimiento_convivientes_lepra seguimiento_convivientes_lepra = lista_datos
                                .get(cont);

                        Datebox dtbxFecha_control = (Datebox) this
                                .getFellow("dtbxFecha_control" + j + "_" + i);
                        Listbox lbxResultado_clinico = (Listbox) this
                                .getFellow("lbxResultado_clinico" + j + "_" + i);
                        Listbox lbxEgreso = (Listbox) this
                                .getFellow("lbxEgreso" + j + "_" + i);

                        dtbxFecha_control
                                .setValue(seguimiento_convivientes_lepra
                                        .getFecha_control());
                        Utilidades.seleccionarListitem(lbxResultado_clinico,
                                seguimiento_convivientes_lepra
                                .getResultado_clinico());
                        Utilidades.seleccionarListitem(lbxEgreso,
                                seguimiento_convivientes_lepra.getEgreso());

                        cont++;

                    }
                }
            } else {
                limpiarControlConvivientes();
            }
        }

    }

    public void deshabilitarConvivientes(String ficha) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("codigo_empresa", codigo_empresa);
        parameters.put("codigo_sucursal", codigo_sucursal);
        parameters.put("codigo_ficha", ficha);

        control_convivientes_seguimientoService.setLimit("limit 25 offset 0");

        List<Control_convivientes_seguimiento> lista_datos = control_convivientes_seguimientoService
                .listar(parameters);
		//log.info("lista_datos" + lista_datos);
        //log.info("lista_datos.size()" + lista_datos.size());

        for (int k = 0; k < lista_datos.size(); k++) {

            Control_convivientes_seguimiento control_convivientes_seguimiento = lista_datos
                    .get(k);

			//log.info("id --- "
            //+ control_convivientes_seguimiento.getIdentificacion());
            if (control_convivientes_seguimiento.getIdentificacion() == null
                    || control_convivientes_seguimiento.getIdentificacion() == "") {

                for (int j = 1; j <= 5; j++) {
                    for (int i = 1; i <= 4; i++) {

                        Datebox dtbxFecha_control = (Datebox) this
                                .getFellow("dtbxFecha_control" + j + "_" + i);
                        Listbox lbxResultado_clinico = (Listbox) this
                                .getFellow("lbxResultado_clinico" + j + "_" + i);
                        Listbox lbxEgreso = (Listbox) this
                                .getFellow("lbxEgreso" + j + "_" + i);

                        dtbxFecha_control.setDisabled(true);
                        lbxResultado_clinico.setDisabled(true);
                        lbxEgreso.setDisabled(true);

                    }
                }
            }

        }

    }

    // manuel
    @Override
    public void params(Map<String, Object> map) {
        if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
            admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
            //log.info("admision DEL PARAM" + admision);

        }
        cargarDatosIniciales();
    }

    public void buscarRegistros() {

        try {
			//log.info("admision" + admision);
            //log.info("empresa" + codigo_empresa + " SUCURSAL "
            //+ codigo_sucursal + " id"
            //+ admision.getNro_identificacion());

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", admision.getCodigo_empresa());
            parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
            parameters.put("identificacion", admision.getNro_identificacion());

			//log.info("parameter>>>>>>>>" + parameters);
            List<Seguimiento_convivientes_lepra> lista_datos = seguimiento_convivientes_lepraService
                    .listar(parameters);
            //log.info("lista_datos>>>>" + lista_datos);

            if (lista_datos == null) {
                tbxNro_identificacion
                        .setValue(admision.getNro_identificacion());
                tbxNomPaciente.setValue(admision.getPaciente()
                        .getNombreCompleto());

                cargarInfoPaciente(admision.getNro_identificacion(), admision);
                FormularioUtil.deshabilitarComponentes(
                        Seguimiento_convivientes_lepraAction.this, false,
                        idsExcluyentes);
            } else {
                mostrarDatos(lista_datos, admision);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
