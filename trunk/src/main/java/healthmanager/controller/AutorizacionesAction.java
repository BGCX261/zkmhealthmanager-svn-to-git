package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Orden_autorizacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.Anexo4_entidadService;
import healthmanager.modelo.service.Configuracion_servicios_autorizacionService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Detalle_orden_autorizacionService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Manuales_tarifariosService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.Orden_autorizacionService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.Servicios_contratadosService;
import healthmanager.modelo.service.UsuariosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CargadorServiciosAutorizacionesTabPanel;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class AutorizacionesAction extends ZKWindow {

    private Servicios_contratadosService servicios_contratadosService;

	// Tabs
    @View
    private Tabs tabs;
    @View
    private Tabpanels tabPanelsContenedorServicios;
    @View
    private Tabbox tabBoxServicios;

    // Datos
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
    private North northEditar;

    @View
    private Groupbox groupDatosPrestador;
    @View
    private Groupbox groupDatosPaciente;
    @View
    private Groupbox groupUbicacionPaciente;
    @View
    private Groupbox groupDiagnosticos;
    @View
    private Groupbox groupInformacion;

    @View
    private Caption capServicios;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_prestador;
    @View
    private Textbox tbxNomPrestador;
    @View
    private Toolbarbutton btnLimpiarAseguradora;

    @View
    private Textbox tbxId;
    @View
    private Textbox tbxdirecion;
    @View
    private Textbox tbxDepartamento;
    @View
    private Textbox tbxMunicipio;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxIdentificacionPaciente;
    @View
    private Toolbarbutton btnLimpiarIdentificacion;

    @View
    private Toolbarbutton btn_guardar;
    @View
    private Toolbarbutton btn_nuevo;
    @View
    private Toolbarbutton btn_cancel;

    @View
    private Textbox tbxTipoId;
    @View
    private Textbox tbxapellido1Paciente;
    @View
    private Textbox tbxapellido2paciente;
    @View
    private Textbox tbxnombre1Paciente;
    @View
    private Textbox tbxnombre2paciente;
    @View
    private Textbox tbxdirPaciente;
    @View
    private Textbox tbxtelpaciente;
    @View
    private Datebox tbxFechNacpaciente;
    @View
    private Listbox lbxUbicacion;
    @View
    private Textbox tbxServicio;
    @View
    private Textbox tbxCama;
    @View
    private Toolbarbutton btnLimpiarDx;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_dx;
    @View
    private Textbox tbxNomDx;
    @View
    private Toolbarbutton btnLimpiarDx1;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_dx1;
    @View
    private Textbox tbxNomDx1;
    @View
    private Toolbarbutton btnLimpiarDx2;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_dx2;
    @View
    private Textbox tbxNomDx2;
    @View
    private Textbox tbxNombre_solicita;
    @View
    private Textbox tbxTelefono;
    @View
    private Textbox tbxCargo;
    @View
    private Textbox tbxCel;
    @View
    private Vbox vboxAutorizaciones;

    @View
    private Groupbox groupServicios;

    @View
    private Div divContenedor;
    @View
    private Popup popup;
    @View
    private Toolbarbutton btnImpresion;

    @View
    private Datebox dtbxFecha_inicial;
    @View
    private Datebox dtbxFecha_final;

    public static final String COMPONENTE_CARGARDOR_SERVICIO = "Carg_ser";
    public static final String COMPONENTE_TAB_PANNEL = "tabpanel";
    private EventListener eventListener_impresion;

    private Admision admision;

    private String idDiv;

    public enum MOLD_AUTORIZACION {

        AUTORIZACION_INDEPENDIENTE, AUTORIZACION_INTERNA
    }

    private Orden_autorizacionService orden_autorizacionService;
    private Detalle_orden_autorizacionService detalle_orden_autorizacionService;

    // Todos los codigos de los grupos se tienen que regis por este formato
    public static final String FORMATO_MATCHES_CODIGO_GRUPO = "[_][A-Z]{3}";

    // Estas constantes van a estar en la base de datos
    public static final String PROCEDIMIENTO = "_PRO";
    public static final String CONSULTAS = "_CON";
    public static final String CIRUGIAS = "_CIR";
    public static final String ESTANCIAS = "_EST";
    public static final String IMAGENES_DIAGNOSTICAS = "_IMD";
    public static final String MEDICAMENTOS = "_MED";
    public static final String LABORATORIO = "_LAB";

    public static final String RAYOS_X = "_RX";
    public static final String ECOGRAFIA = "_ECO";
    public static final String ENDOSCOPIA = "_END";
    public static final String TAC = "_TAC";
    public static final String ELECTROFISIOLOGIA = "_ELF";
    public static final String RESONANCIA_MAGNETICA = "_REM";

    public static final String ODONOTOLOGIA_GENERAL = "_ODG";
    public static final String CIRUGIA_ORAL = "_CRO";
    public static final String PERIODONCIA = "_PRD";
    public static final String ENDODONCIA = "_END";
    public static final String ODONTOPEDIATRIA = "_OPA";
    public static final String ORTODONCIA = "_ORT";

    private Orden_autorizacion orden_autorizacion_global;
    private MOLD_AUTORIZACION modo_autorizacion;

    // Este es el padre cuando sea una autorizacion interna
    private Component componentPadre;
    private List<Configuracion_servicios_autorizacion> _listado_config_ser;

    @Override
    public void init() throws Exception {
        if (getSucursal().getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
            listarParametros();
            paramitrizarBandBox();
            incializarPestanias(IConstantes.TIPO_USUARIO_CONFIG_AUDITOR, null);
            limpiarDatos();
            tbxAccion.setValue("registrar");
            cargarEventos();
        }
    }

    private void cargarEventos() {
        btnImpresion.addEventListener(Events.ON_CLICK,
                new EventListener<Event>() {
                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        vboxAutorizaciones.getChildren().clear();
                        if (orden_autorizacion_global != null) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("codigo_empresa", orden_autorizacion_global
                                    .getCodigo_empresa());
                            map.put("codigo_sucursal",
                                    orden_autorizacion_global
                                    .getCodigo_sucursal());
                            map.put("codigo_solicitud",
                                    orden_autorizacion_global.getCodigo_orden());
                            List<Anexo4_entidad> anexo4_entidads = getServiceLocator()
                            .getServicio(Anexo4_entidadService.class)
                            .listar(map);
                            if (!anexo4_entidads.isEmpty()) {
                                for (Anexo4_entidad anexo4_entidad : anexo4_entidads) {
                                    String tipo_servicio = "";
                                    String estado_pago = "";

                                    Configuracion_servicios_autorizacion config = new Configuracion_servicios_autorizacion();
                                    config.setId(anexo4_entidad.getTipo_servicio());
                                    config = getServiceLocator().getServicio(Configuracion_servicios_autorizacionService.class).consultar(config);

                                    if (config != null) {
                                        tipo_servicio = config.getNombre();
                                    }

                                    Elemento elemento = new Elemento();
                                    elemento.setTipo("estado_pago");
                                    elemento.setCodigo(anexo4_entidad
                                            .getEstado_cobro());
                                    elemento = getServiceLocator().getServicio(
                                            ElementoService.class).consultar(
                                            elemento);
                                    if (elemento != null) {
                                        estado_pago = elemento.getDescripcion();
                                    }

                                    Toolbarbutton toolbarbutton = new Toolbarbutton(
                                            "IMPRIMIR "
                                            + tipo_servicio
                                            .toUpperCase()
                                            + " POR " + estado_pago,
                                            "/images/print_ico.gif");
                                    toolbarbutton.setAttribute(
                                            "anexo4_entidad", anexo4_entidad);
                                    agregarEventoImpresion(toolbarbutton);
                                    vboxAutorizaciones
                                    .appendChild(toolbarbutton);
                                }
                                popup.open(btnImpresion);
                            } else {
                                MensajesUtil
                                .mensajeAlerta(
                                        "Advertencia",
                                        "El sistema no encontro autorizaciones, verifique si los servicios son autorizados");
                            }
                        } else {
                            MensajesUtil.mensajeAlerta("Advertencia",
                                    "Se a guardado la autorizacion");
                        }
                    }
                });
    }

    private void agregarEventoImpresion(Toolbarbutton toolbarbutton) {
        if (eventListener_impresion == null) {
            eventListener_impresion = new EventListener<Event>() {
                @Override
                public void onEvent(Event arg0) throws Exception {
                    Anexo4_entidad anexo4_entidad = (Anexo4_entidad) arg0
                            .getTarget().getAttribute("anexo4_entidad");
                    if (anexo4_entidad != null) {
                        imprimir(anexo4_entidad.getCodigo());
                    }
                }
            };
        }
        toolbarbutton
                .addEventListener(Events.ON_CLICK, eventListener_impresion);
    }

    @Override
    public void params(Map<String, Object> map) {

        if (modo_autorizacion == null) {
            modo_autorizacion = MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE;
        }
    }

    @Override
    public void _despuesIniciar() {
        //log.info("Despues de inciar: " + modo_autorizacion);
        if (modo_autorizacion == MOLD_AUTORIZACION.AUTORIZACION_INTERNA) {
            cambiarEstadoAutorizacionInterna();
        }
    }

    /**
     * Este metodo me oculta los componentes no utilizados
	 *
     */
    private void cambiarEstadoAutorizacionInterna() {
        // Ocultamos los grupos
        groupboxConsulta.setVisible(false);
        groupboxEditar.setVisible(true);
        northEditar.setVisible(false);
        groupDatosPrestador.setVisible(false);
        groupDatosPaciente.setVisible(false);
        groupUbicacionPaciente.setVisible(false);
        groupDiagnosticos.setVisible(false);
        groupInformacion.setVisible(false);

        // colocamos visible el grupo de servicios
        groupServicios.setWidth("100%");
        groupServicios.setHeight("350px");
        tabBoxServicios.setHeight("340px");
        groupServicios.setVisible(true);
        groupServicios.setContentStyle("border:0");
        groupServicios.removeChild(capServicios);

		//log.info("Heigth View: " + getHeight()); 
        // Eliminamos los Space
        remove(getFellow("sp1"));
        remove(getFellow("sp2"));
        remove(getFellow("sp3"));
        remove(getFellow("sp4"));
        remove(getFellow("sp5"));
        remove(getFellow("sp6"));
        invalidate();
    }

    private void remove(Component component) {
        component.getParent().removeChild(component);
    }

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

    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue()
                    .toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap();
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");
            parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
            parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());
            parameters.put("fecha_inicio", dtbxFecha_inicial.getValue());
            parameters.put("fecha_final", dtbxFecha_final.getValue());

            orden_autorizacionService.setLimit("limit 25 offset 0");

            List<Orden_autorizacion> lista_datos = orden_autorizacionService
                    .listar(parameters);
            rowsResultado.getChildren().clear();

            for (Orden_autorizacion orden_autorizacion : lista_datos) {
                rowsResultado.appendChild(crearFilas(orden_autorizacion, this));
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

        final Orden_autorizacion orden_autorizacion = (Orden_autorizacion) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        Administradora administradora = new Administradora();
        administradora
                .setCodigo_empresa(orden_autorizacion.getCodigo_empresa());
        administradora.setCodigo_sucursal(orden_autorizacion
                .getCodigo_sucursal());
        administradora.setCodigo(orden_autorizacion.getCodigo_prestador());
        administradora = getServiceLocator().getServicio(
                AdministradoraService.class).consultar(administradora);

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(orden_autorizacion.getCodigo_empresa());
        paciente.setCodigo_sucursal(orden_autorizacion.getCodigo_sucursal());
        paciente.setNro_identificacion(orden_autorizacion
                .getNro_identificacion());
        paciente = getServiceLocator().getServicio(PacienteService.class)
                .consultar(paciente);

        Usuarios usuarios = new Usuarios();
        usuarios.setCodigo_empresa(orden_autorizacion.getCodigo_empresa());
        usuarios.setCodigo_sucursal(orden_autorizacion.getCodigo_sucursal());
        usuarios.setCodigo(orden_autorizacion.getCodigo_persona_autoriza());
        usuarios = getServiceLocator().getServicio(UsuariosService.class)
                .consultar(usuarios);

        fila.setStyle("text-align: justify;nowrap:nowrap");
        Cell cell = new Cell();
        boolean anulada = false;
        if (orden_autorizacion.getEstado().equals(
                IConstantesAutorizaciones.ESTADO_ANULADO)) {
            cell.setStyle("background-color:#ED4553");
            //log.info("Orden anulada");
            anulada = true;

            StringBuilder informacion_anulacion = new StringBuilder(
                    "ORDEN ANULADA");
            if (orden_autorizacion.getDelete_date() != null) {
                informacion_anulacion.append(" EL DIA "
                        + (new SimpleDateFormat("dd-MM-yyyy")
                        .format(orden_autorizacion.getDelete_date())));
            }

            if (orden_autorizacion.getDelete_user() != null) {
                Usuarios usuariosTemp = new Usuarios();
                usuariosTemp.setCodigo_empresa(orden_autorizacion
                        .getCodigo_empresa());
                usuariosTemp.setCodigo_sucursal(orden_autorizacion
                        .getCodigo_sucursal());
                usuariosTemp.setCodigo(orden_autorizacion.getDelete_user());
                usuariosTemp = getServiceLocator().getServicio(
                        UsuariosService.class).consultar(usuariosTemp);
                if (usuariosTemp != null) {
                    informacion_anulacion.append(" POR "
                            + usuariosTemp.getCodigo() + " - "
                            + usuariosTemp.getNombres() + " "
                            + usuariosTemp.getApellidos());
                }
            }
            fila.setTooltiptext(informacion_anulacion.toString());
        }
        fila.appendChild(cell);
        fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd")
                .format(orden_autorizacion.getFecha())));
        fila.appendChild(new Label(administradora != null ? administradora
                .getCodigo() + " - " + administradora.getNombre() : ""));
        fila.appendChild(new Label(paciente != null ? paciente
                .getNro_identificacion() + " - " + paciente.getNombreCompleto()
                : ""));
        fila.appendChild(new Label(usuarios != null ? usuarios.getCodigo()
                + " - " + usuarios.getNombres() + " " + usuarios.getApellidos()
                : ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(orden_autorizacion, true);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        img.setSrc("/images/print_ico.gif");
        img.setTooltiptext("Imprimir");
        img.setVisible(false);
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {

            }
        });
        hbox.appendChild(img);

        img = new Image();
        img.setSrc("/images/cancelar_mini.png");
        img.setTooltiptext("Anular");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                Messagebox.show(
                        "Esta seguro que deseas anular este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    anularAutorizaciones(orden_autorizacion);
                                }
                            }
                        });
            }
        });
        if (!anulada) {
            hbox.appendChild(space);
            hbox.appendChild(img);
        }

        fila.appendChild(hbox);

        return fila;
    }

    public void imprimir(String codigo) throws Exception {
        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "Autorizacion");
        paramRequest.put("codigo", codigo);
        paramRequest.put("mostrar_prestador", true);
        paramRequest.put("reporte", "/report/autorizaciones_simple.jasper");

        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", this, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);
    }

    private void anularAutorizaciones(Orden_autorizacion orden_autorizacion) {
        try {
            orden_autorizacionService.guardarAnulacionAutorizacion(orden_autorizacion,
                    usuarios);
            Notificaciones.mostrarNotificacionInformacion("Informacion",
                    "Autorizacion anulada exitosamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
            buscarDatos();
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, null, this);
        }
    }

    public void mostrarDatos(Object obj, boolean ocultar_tabs) throws Exception {
        try {
            limpiarDatos(ocultar_tabs);
            orden_autorizacion_global = (Orden_autorizacion) obj;

            if (ocultar_tabs) {
                Administradora administradora = new Administradora();
                administradora.setCodigo_empresa(orden_autorizacion_global
                        .getCodigo_empresa());
                administradora.setCodigo_sucursal(orden_autorizacion_global
                        .getCodigo_sucursal());
                administradora.setCodigo(orden_autorizacion_global
                        .getCodigo_prestador());
                administradora = getServiceLocator().getServicio(
                        AdministradoraService.class).consultar(administradora);

                if (administradora != null) {
                    tbxCodigo_prestador.seleccionarRegistro(administradora,
                            administradora.getCodigo(), administradora.getNombre());
                    cargarDatosPrestador(administradora, true);
                }

                Paciente paciente = new Paciente();
                paciente.setCodigo_empresa(orden_autorizacion_global
                        .getCodigo_empresa());
                paciente.setCodigo_sucursal(orden_autorizacion_global
                        .getCodigo_sucursal());
                paciente.setNro_identificacion(orden_autorizacion_global
                        .getNro_identificacion());
                paciente = getServiceLocator().getServicio(PacienteService.class)
                        .consultar(paciente);

                if (paciente != null) {
                    cargarDatosPaciente(paciente);
                }

                Usuarios usuarios = new Usuarios();
                usuarios.setCodigo_empresa(orden_autorizacion_global
                        .getCodigo_empresa());
                usuarios.setCodigo_sucursal(orden_autorizacion_global
                        .getCodigo_sucursal());
                usuarios.setCodigo(orden_autorizacion_global
                        .getCodigo_persona_autoriza());
                usuarios = getServiceLocator().getServicio(UsuariosService.class)
                        .consultar(usuarios);

                if (usuarios != null) {
                    cargarDatosPersonaAutoriza(usuarios);
                }

                tbxCodigo_prestador.setValue(orden_autorizacion_global
                        .getCodigo_prestador());
                tbxIdentificacionPaciente.setValue(orden_autorizacion_global
                        .getNro_identificacion());

                Utilidades.setValueFrom(lbxUbicacion,
                        orden_autorizacion_global.getUbicacion());

                tbxServicio.setValue(orden_autorizacion_global.getServicio());
                tbxCama.setValue(orden_autorizacion_global.getCama());

                cargarDatosCie(orden_autorizacion_global.getCodigo_cie_principal(),
                        tbxCodigo_dx);
                cargarDatosCie(orden_autorizacion_global.getCodigo_cie1(),
                        tbxCodigo_dx1);
                cargarDatosCie(orden_autorizacion_global.getCodigo_cie2(),
                        tbxCodigo_dx1);
            }

			// En esta parte se cargan los detalles
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("codigo_empresa",
                    orden_autorizacion_global.getCodigo_empresa());
            params.put("codigo_sucursal",
                    orden_autorizacion_global.getCodigo_sucursal());
            params.put("codigo_orden",
                    orden_autorizacion_global.getCodigo_orden());
            params.put("order_by", "tipo_servicio ASC");
            List<Detalle_orden_autorizacion> autorizacions = detalle_orden_autorizacionService
                    .listar(params);
            cargarDetalles_autorizaciones(autorizacions);

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    /**
     * Este metodo me permite cargar los detalles de las autorizaciones
	 *
     */
    private void cargarDetalles_autorizaciones(
            List<Detalle_orden_autorizacion> autorizacions) {
        Map<Long, Object> map = new HashMap<Long, Object>();
        for (Detalle_orden_autorizacion detalle_orden_autorizacion : autorizacions) {
            if (map.containsKey(detalle_orden_autorizacion.getTipo_servicio())) {
                List<Detalle_orden_autorizacion> detalle_orden_autorizacions = (List<Detalle_orden_autorizacion>) map
                        .get(detalle_orden_autorizacion.getTipo_servicio());
                detalle_orden_autorizacions.add(detalle_orden_autorizacion);
            } else {
                map.put(detalle_orden_autorizacion.getTipo_servicio(),
                        new ArrayList<Detalle_orden_autorizacion>());
                List<Detalle_orden_autorizacion> detalle_orden_autorizacions = (List<Detalle_orden_autorizacion>) map
                        .get(detalle_orden_autorizacion.getTipo_servicio());
                detalle_orden_autorizacions.add(detalle_orden_autorizacion);
            }
        }

        // Agregamos a las pestanias
        Iterator it = map.entrySet().iterator();
        Tab tab_inicial = null;
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            List<Detalle_orden_autorizacion> detalle_orden_autorizacions = (List<Detalle_orden_autorizacion>) e
                    .getValue();
            Tab tab = getTab((Long) e.getKey());
            if (tab != null) {
                if (tab_inicial == null) {
                    tab_inicial = tab;
                }
                setVisibleTab(tab, true);
                CargadorServiciosAutorizacionesTabPanel panelCargador = (CargadorServiciosAutorizacionesTabPanel) tab
                        .getAttribute(COMPONENTE_CARGARDOR_SERVICIO);
                panelCargador._cargarDetalles(detalle_orden_autorizacions);
            }
        }
        if (tab_inicial != null) {
            tab_inicial.setSelected(true);
        }
    }

    private void cargarDatosCie(String codigo_cie_principal,
            BandboxRegistrosMacro tbxCodigo_dx3) {
        Cie cie = new Cie();
        cie.setCodigo(codigo_cie_principal);
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
        if (cie != null) {
            tbxCodigo_dx3.seleccionarRegistro(cie, cie.getCodigo(),
                    cie.getNombre());
        }
    }

    public boolean validarForm() throws Exception {
        boolean valida = true;
        String mensaje = "Los campos marcados con (*) son obligatorios";
        try {
            FormularioUtil.validarCamposObligatorios(tbxIdentificacionPaciente,
                    tbxCodigo_prestador);
        } catch (WrongValueException e) {
            return false;
        }

        if (valida && tbxCodigo_dx.getRegistroSeleccionado() == null) {
            valida = false;
            mensaje = "Diagnostico principal es obligatorio";
        }

        List<Detalle_orden_autorizacion> detalle_orden_autorizacions = getDetalle_orden_autorizacions();
        if (valida && detalle_orden_autorizacions.isEmpty()) {
            valida = false;
            mensaje = "Para registrar esta autorizacion debe por lo meno agregar un servicio";
        } else if (valida) {
            int i = 1;
            for (Detalle_orden_autorizacion detalle_orden_autorizacion : detalle_orden_autorizacions) {
                if (detalle_orden_autorizacion.getUnidades() == null) {
                    valida = false;
                    mensaje = "La cantidad de procedimiento a ordenar no puede estar vacio, en la fila # "
                            + i;
                    break;
                } else if (detalle_orden_autorizacion.getUnidades().intValue() == 0) {
                    valida = false;
                    mensaje = "La cantidad de procedimiento a ordenar debe ser mayor de cero, en la fila # "
                            + i;
                    break;
                }
                if (detalle_orden_autorizacion.getEstado_cobro().trim()
                        .isEmpty()) {
                    valida = false;
                    mensaje = "Para ordenar el procedimiento en la fila # "
                            + (i) + " debe seleccionar el tipo de orden";
                    break;
                }
                i++;
            }
        }

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres()
                    + " recuerde que...", mensaje);
        }

        return valida;
    }

    public Map<String, Object> obtenerDatos() {
        Map<String, Object> map = new HashMap<String, Object>();
        Orden_autorizacion orden_autorizacion = new Orden_autorizacion();
        if (orden_autorizacion_global != null) {
            orden_autorizacion = orden_autorizacion_global;
        } else {
            orden_autorizacion
                    .setEstado(IConstantesAutorizaciones.ESTADO_AUTORIZACION);
            orden_autorizacion
                    .setTipo(modo_autorizacion == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE ? IConstantesAutorizaciones.TIPO_AUTORIZACION
                            : IConstantesAutorizaciones.TIPO_ORDEN_MEDICO);
        }

        orden_autorizacion.setCodigo_empresa(getEmpresa().getCodigo_empresa());
        orden_autorizacion.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
        orden_autorizacion.setCodigo_prestador(tbxCodigo_prestador.getValue());
        orden_autorizacion.setNro_identificacion(tbxIdentificacionPaciente
                .getValue());
        orden_autorizacion.setUbicacion(lbxUbicacion.getSelectedItem()
                .getValue().toString());
        orden_autorizacion.setServicio(tbxServicio.getValue());
        orden_autorizacion.setCama(tbxCama.getValue());
        orden_autorizacion.setCodigo_persona_autoriza(usuarios.getCodigo());
        orden_autorizacion.setCreacion_date(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        orden_autorizacion.setUltimo_update(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        orden_autorizacion.setCreacion_user(usuarios.getCodigo().toString());
        orden_autorizacion.setUltimo_user(usuarios.getCodigo().toString());
        orden_autorizacion.setFecha(new Timestamp(Calendar.getInstance()
                .getTimeInMillis()));
        orden_autorizacion.setCodigo_cie_principal(tbxCodigo_dx.getValue());
        orden_autorizacion.setCodigo_cie1(tbxCodigo_dx1.getValue());
        orden_autorizacion.setCodigo_cie2(tbxCodigo_dx2.getValue());
        map.put("orden", orden_autorizacion);
        map.put("detalle", getDetalle_orden_autorizacions());
        map.put("usuario", getUsuarios());
        map.put("rol", rol_usuario);
        map.put("prestador_asignado",
                modo_autorizacion == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE ? "S"
                : "N");
        map.put("accion", tbxAccion.getValue());
        return map;
    }

	// public boolean validarAutorizacionInterna(){
    //
    // }
    public List<Detalle_orden_autorizacion> getDetalle_orden_autorizacions() {
        List<Detalle_orden_autorizacion> orden_autorizacions = new ArrayList<Detalle_orden_autorizacion>();
        List<Component> components = tabs.getChildren();
        for (Component component : components) {
            if (component instanceof Tab) {
                Tab tab = (Tab) component;
                CargadorServiciosAutorizacionesTabPanel autorizacionesTabPanel = (CargadorServiciosAutorizacionesTabPanel) tab
                        .getAttribute(COMPONENTE_CARGARDOR_SERVICIO);

				//log.info("* Tipo: " + autorizacionesTabPanel.getTipo_pcd()
                //+ " - Detalles: "
                //+ autorizacionesTabPanel.getDetalles().size()); 
                orden_autorizacions
                        .addAll(autorizacionesTabPanel.getDetalles());
            }
        }
        return orden_autorizacions;
    }

    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                // Cargamos los componentes //
                orden_autorizacion_global = orden_autorizacionService.guardarOrden(obtenerDatos());
                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    tbxAccion.setValue("modificar");
                }
                MensajesUtil.mensajeInformacion("Informacion ..",
                        "Los datos se guardaron satisfactoriamente");
            }
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    private void listarParametros() {
        listarUbicacion();
        listarParameter();
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("pac.nro_identificacion || ' ' || pac.apellido1 || ' ' || pac.apellido2 || ' ' || pac.nombre1 || ' ' || pac.nombre2");
        listitem.setLabel("Paciente");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("adm.codigo || ' ' ||  adm.nombre");
        listitem.setLabel("Prestador");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("us.codigo || ' ' ||  us.nombres || ' ' ||  us.apellidos");
        listitem.setLabel("Persona quien autoriza");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    private void listarUbicacion() {
        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar("ubicacion_anexo3");
        Utilidades.listarElementoListboxFromType(lbxUbicacion, false,
                elementos, false);
    }

    private void cargarDatosPersonaAutoriza(Usuarios usuarios) {
        if (usuarios != null) {
            tbxNombre_solicita.setValue("" + usuarios.getNombres() + " "
                    + usuarios.getApellidos());
            tbxTelefono.setValue("" + usuarios.getTel_res());

            Elemento elemento = new Elemento();
            elemento.setCodigo(rol_usuario);
            elemento.setTipo("rol");
            elemento = getServiceLocator().getServicio(ElementoService.class)
                    .consultar(elemento);

            tbxCargo.setValue(elemento != null ? elemento.getDescripcion() : "");
            tbxCel.setValue("" + usuarios.getCelular());
        }
    }

    private void paramitrizarBandBox() {
        parametrizarBanboxAseguradora();
        parametrizarBanboxPaciente();
        parametrizarBanboxCie();
    }

    public void limpiarDatos() throws Exception {
        limpiarDatos(true);
    }

    public void limpiarDatos(boolean ocultar) throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar);
        tbxCodigo_dx.setButtonVisible(false);
        tbxCodigo_dx1.setButtonVisible(false);
        tbxCodigo_dx2.setButtonVisible(false);
        limpiarTab(ocultar);
        cargarDatosPersonaAutoriza(usuarios);
        tabBoxServicios.invalidate();
        if (componentPadre != null) {
            componentPadre.invalidate();
        }
        orden_autorizacion_global = null;
    }

    private void parametrizarBanboxCie() {
        tbxCodigo_dx.setAttribute("principal", "_k");
        tbxCodigo_dx.inicializar(tbxNomDx, btnLimpiarDx);
        tbxCodigo_dx1.inicializar(tbxNomDx1, btnLimpiarDx1);
        tbxCodigo_dx2.inicializar(tbxNomDx2, btnLimpiarDx2);
        BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Cie registro) {
                bandbox.setValue("" + registro.getCodigo());
                textboxInformacion.setValue("" + registro.getNombre());
                if (bandbox.getAttribute("principal") != null) {
                    tbxCodigo_dx1.setButtonVisible(true);
                    tbxCodigo_dx2.setButtonVisible(true);
                }
                return true;
            }

            @Override
            public void renderListitem(Listitem listitem, Cie registro) {
                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(registro.getCodigo() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNombre() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getSexo()));
                listitem.appendChild(listcell);
            }

            @Override
            public List<Cie> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {

                Paciente paciente = tbxIdentificacionPaciente
                        .getRegistroSeleccionado();
                parametros.put("paramTodo", "");
                parametros.put("sexo", paciente.getSexo().equals("M") ? "H"
                        : "M");
                parametros.put("value", "%"
                        + valorBusqueda.toUpperCase().trim() + "%");

                parametros.put("limite_paginado", "limit 25 offset 0");

                return getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, parametros);
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
                if (bandbox.getAttribute("principal") != null) {
                    tbxCodigo_dx1.setButtonVisible(false);
                    tbxCodigo_dx2.setButtonVisible(false);

                    tbxCodigo_dx1.limpiarSeleccion(false);
                    tbxCodigo_dx2.limpiarSeleccion(false);
                }
            }
        };
        tbxCodigo_dx.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        tbxCodigo_dx1.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        tbxCodigo_dx2.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    private void parametrizarBanboxPaciente() {
        tbxIdentificacionPaciente.inicializar(null, btnLimpiarIdentificacion);
        BandboxRegistrosIMG<Paciente> bandboxRegistrosIMG = new BandboxRegistrosIMG<Paciente>() {

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Paciente registro) {
                tbxCodigo_dx.setButtonVisible(true);

                cargarDatosPaciente(registro);
                return true;
            }

            @Override
            public void renderListitem(Listitem listitem, Paciente registro) {
                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(registro
                        .getTipo_identificacion() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNro_identificacion()
                        + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNombre1() + " "
                        + registro.getNombre2()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getApellido1() + " "
                        + registro.getApellido2()));
                listitem.appendChild(listcell);
            }

            @Override
            public List<Paciente> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {
                parametros.put("codigo_empresa", empresa.getCodigo_empresa());
                parametros
                        .put("codigo_sucursal", sucursal.getCodigo_sucursal());
                parametros.put("paramTodo", "");
                parametros.put("value", "%"
                        + valorBusqueda.toUpperCase().trim() + "%");

                parametros.put("limite_paginado",
                        "limit 25 offset 0");

                return getServiceLocator().getPacienteService().listar(
                        parametros);
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
                cargarDatosPaciente(null);
            }
        };
        tbxIdentificacionPaciente.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    private void cargarDatosPaciente(Paciente paciente) {
        if (paciente != null) {
            tbxnombre1Paciente.setValue("" + paciente.getNombre1());
            tbxnombre2paciente.setValue("" + paciente.getNombre2());
            tbxapellido1Paciente.setValue("" + paciente.getApellido1());
            tbxapellido2paciente.setValue("" + paciente.getApellido2());
            tbxTipoId.setValue("" + paciente.getTipo_identificacion());
            tbxIdentificacionPaciente.setValue(""
                    + paciente.getNro_identificacion());
            tbxIdentificacionPaciente.seleccionarRegistro(paciente,
                    paciente.getNro_identificacion(),
                    paciente.getNombreCompleto());
        } else {
            tbxnombre1Paciente.setValue("");
            tbxnombre2paciente.setValue("");
            tbxapellido1Paciente.setValue("");
            tbxapellido2paciente.setValue("");
            tbxTipoId.setValue("");
            tbxIdentificacionPaciente.setValue("");
            tbxIdentificacionPaciente.limpiarSeleccion(true);
        }
    }

    private void cargarDatosPrestador(Administradora administradora, boolean modificar) {
        if (administradora != null) {
            tbxCodigo_prestador.setValue("" + administradora.getCodigo());
            tbxNomPrestador.setValue("" + administradora.getNombre());
            tbxId.setValue("" + administradora.getNit());
            tbxdirecion.setValue("" + administradora.getDireccion());

            Departamentos departamentos = new Departamentos();
            departamentos.setCodigo(administradora.getCodigo_dpto());
            departamentos = getServiceLocator().getServicio(
                    DepartamentosService.class).consultar(departamentos);

            tbxDepartamento.setValue(""
                    + (departamentos != null ? departamentos.getNombre() : ""));

            Municipios municipios = new Municipios();
            municipios.setCoddep(administradora.getCodigo_dpto());
            municipios.setCodigo(administradora.getCodigo_municipio());
            municipios = getServiceLocator().getServicio(
                    MunicipiosService.class).consultar(municipios);

            tbxMunicipio.setValue(""
                    + (sucursal != null ? sucursal.getNombre_sucursal() : ""));

            // Cargamos los servicios contrados
            cargarServiciosContratdos(administradora, modificar);
        } else {
            tbxCodigo_prestador.setValue("");
            tbxNomPrestador.setValue("");
            tbxId.setValue("");
            tbxdirecion.setValue("");
            tbxDepartamento.setValue("");
            tbxMunicipio.setValue("");

            // Limpiamos los servicios
            limpiarTab(true);
        }
    }

    private void parametrizarBanboxAseguradora() {
        tbxCodigo_prestador.inicializar(tbxNomPrestador, btnLimpiarAseguradora);
        BandboxRegistrosIMG<Administradora> bandboxRegistrosIMG = new BandboxRegistrosIMG<Administradora>() {
            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Administradora registro) {
                cargarDatosPrestador(registro, false);
                return true;
            }

            @Override
            public void renderListitem(Listitem listitem,
                    Administradora registro) {
                listitem.appendChild(new Listcell(registro.getNit()));
                listitem.appendChild(new Listcell(registro.getCodigo()));
                listitem.appendChild(new Listcell(registro.getNombre()));

                String direccionPres = "";
                if (registro != null) {
                    direccionPres += registro.getDireccion();

                    Departamentos departamentos = new Departamentos();
                    departamentos.setCodigo(registro.getCodigo_dpto());
                    departamentos = getServiceLocator()
                            .getDepartamentosService().consultar(departamentos);

                    Municipios municipios = new Municipios();
                    municipios.setCoddep(registro.getCodigo_dpto());
                    municipios.setCodigo(registro.getCodigo_municipio());
                    municipios = getServiceLocator().getMunicipiosService()
                            .consultar(municipios);

                    if (municipios != null) {
                        direccionPres += " - " + municipios.getNombre();
                    }
                    if (departamentos != null) {
                        direccionPres += " - " + departamentos.getNombre();
                    }
                }
                listitem.appendChild(new Listcell(direccionPres.toUpperCase()));
            }

            @Override
            public List<Administradora> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {
                parametros.put("codigo_empresa", empresa.getCodigo_empresa());
                parametros
                        .put("codigo_sucursal", sucursal.getCodigo_sucursal());
                parametros.put("tercerizada", "S");
                parametros.put("paramTodo", "paramTodo");
                parametros.put("value", valorBusqueda);
                return getServiceLocator().getAdministradoraService().listar(
                        parametros);
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
                cargarDatosPrestador(null, false);
            }
        };
        tbxCodigo_prestador.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    @Override
    public void _componenteInicializado(Component component) {
		// if(component instanceof TabMacro){
        // log.info("" + ((TabMacro)component).getTipo_pcd());
        //
        // }
    }

    /**
     * Este metodo verifica los servicios que se contrataron con esa
     * aseguradora, y asi le permite hacer el ordenamineto
     *
     * @param servicios 
	 *
     */
    public void incializarPestanias(String servicios, String via_ingreso) {
        reiniciarTabs();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("codigo_empresa", getSucursal().getCodigo_empresa());
        map.put("codigo_sucursal", getSucursal().getCodigo_sucursal());
        map.put("tipo_usuarios", servicios);
        map.put("via_ingreso", via_ingreso);
        map.put("estado", IConstantesAutorizaciones.ESTADO_CONFIG_ACTIVO);
        map.put("ordenar_nombre", "ordenar_nombre");
        setConfig(getServiceLocator()
                .getServicio(Configuracion_servicios_autorizacionService.class)
                .listar(map));

        if (getConfig().isEmpty()) {
            MensajesUtil.mensajeAlerta("Advertencia", "Para utilizar este modulo debe tener configurado previamente los tipos de servicios");
        } else {
            for (Configuracion_servicios_autorizacion configuracion : getConfig()) {
                agregarTipoProcedimiento(configuracion);
            }
        }
    }

    private void reiniciarTabs() {
        tabs.getChildren().clear();
        tabPanelsContenedorServicios.getChildren().clear();
    }

    /**
     * Este metodo me permite agregar las pestañas
     *
     * @author luis miguel
	 *
     */
    public void agregarTipoProcedimiento(Configuracion_servicios_autorizacion configuracion) {
        Tab tab = new Tab("" + configuracion.getNombre());
        tabs.appendChild(tab);
        Tabpanel tabpanel = new Tabpanel();
        CargadorServiciosAutorizacionesTabPanel component = (CargadorServiciosAutorizacionesTabPanel) Executions
                .createComponents(
                        "/WEB-INF/macros/cargador_servicios_autorizados_macro.zul",
                        tabpanel, null);
        component.setZkWindow(this);
        component.setTabbox(tabBoxServicios);
        component.setComponentPadreContenedor(componentPadre);
        component.setAdmision(getAdmision());
        component.setConfiguracion_servicios_autorizacion(configuracion);

        Window window = new Window();
        window.setWidth("100%");
        window.setHeight("100%");
        window.appendChild(component);
        tabpanel.appendChild(window);

        tab.setAttribute(COMPONENTE_CARGARDOR_SERVICIO, component);
        tab.setAttribute(COMPONENTE_TAB_PANNEL, tabpanel);
        tabPanelsContenedorServicios.appendChild(tabpanel);
    }

    private void limpiarTab(boolean ocultar) {
        List<Component> components = tabs.getChildren();
        for (Component component : components) {
            if (component instanceof Tab) {
                Tab tab = (Tab) component;
                if (ocultar) {
                    setVisibleTab((Tab) component, false);
                }
                CargadorServiciosAutorizacionesTabPanel autorizacionesTabPanel = (CargadorServiciosAutorizacionesTabPanel) tab
                        .getAttribute(COMPONENTE_CARGARDOR_SERVICIO);
                autorizacionesTabPanel.limpiar();
            }
        }
        groupServicios.setVisible(!ocultar);
    }

    private void setVisibleTab(Tab tab, boolean visible) {
        tab.setVisible(visible);
        Tabpanel tabpanel = (Tabpanel) tab.getAttribute(COMPONENTE_TAB_PANNEL);
        if (tabpanel != null) {
            tabpanel.setVisible(visible);
        }
    }

    private Tab getTab(Long id_servicio) {
        List<Component> components = tabs.getChildren();
        for (Component component : components) {
            if (component instanceof Tab) {
                Tab tab = (Tab) component;
                CargadorServiciosAutorizacionesTabPanel autorizacionesTabPanel = (CargadorServiciosAutorizacionesTabPanel) tab
                        .getAttribute(COMPONENTE_CARGARDOR_SERVICIO);
                if (autorizacionesTabPanel
                        .getConfiguracion_servicios_autorizacion().getId()
                        .equals(id_servicio)) {
                    return tab;
                }
            }
        }
        return null;
    }

    private void habilitarServicio(Long id_servicio,
            List<Map<String, Object>> mapsServicios) {
        Tab tab = getTab(id_servicio);
        if (tab != null) {
            setVisibleTab(tab, true);
            CargadorServiciosAutorizacionesTabPanel panelCargador = (CargadorServiciosAutorizacionesTabPanel) tab
                    .getAttribute(COMPONENTE_CARGARDOR_SERVICIO);
            //log.info("Modo autorizacion: " + modo_autorizacion); 
            panelCargador.set_tipo(modo_autorizacion);
            panelCargador.setAdmision(getAdmision());
            if (modo_autorizacion == MOLD_AUTORIZACION.AUTORIZACION_INDEPENDIENTE) {
                panelCargador.setContratacion(mapsServicios);
            }
        }
    }

    /**
     * Este metodo me permite cargar TODO pendiente
	 *
     */
    private void cargarServiciosContratdos(Administradora administradora, boolean modificar) {
        List<Configuracion_servicios_autorizacion> configs = getConfig();
        // verfiicamos si la configuracion aplica 
        // para este prestador
        boolean validado = false;
        for (Configuracion_servicios_autorizacion config_temp : configs) {
            Map<String, Object> map_consultar_contratos = new HashMap<String, Object>();
            map_consultar_contratos.put("codigo_empresa", administradora.getCodigo_empresa());
            map_consultar_contratos.put("codigo_sucursal", administradora.getCodigo_sucursal());
            map_consultar_contratos.put("codigo_administradora", administradora.getCodigo());
            map_consultar_contratos.put("id_configuracion", config_temp.getId());
            map_consultar_contratos.put("mostar_solo_activos", !modificar ? "activos" : null);
            log.info("Mapa contrato: " + map_consultar_contratos);
            List<Map<String, Object>> list_contratos = servicios_contratadosService
                    .getContratosValidos(map_consultar_contratos);
            if (!list_contratos.isEmpty()) {
                validado = true;
                List<Map<String, Object>> list_contratos_validados = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> map_contrato_manual : list_contratos) {
                    String id_contrato = (String) map_contrato_manual.get("id_contrato");
                    Map<String, Object> map_contrato = getMapaContrato(id_contrato, list_contratos_validados);
                    if (map_contrato != null) {
                        alimentarManual(map_contrato_manual, map_contrato);
                    } else {
                        Map<String, Object> map_contrato_nuevo = alimentarContrato(map_contrato_manual);
                        alimentarManual(map_contrato_manual, map_contrato_nuevo);
                        list_contratos_validados.add(map_contrato_nuevo);
                    }
                }
                habilitarServicio(config_temp.getId(), list_contratos_validados);
            }
        }
        if (!validado) {
            groupServicios.setVisible(false);
            MensajesUtil.mensajeAlerta("Advertencia",
                    "Este prestador no tiene ningún contrato activo");
        } else {
            groupServicios.setVisible(true);
        }
        seleccionarPrimeraPestania();
    }

    private Map<String, Object> alimentarContrato(Map<String, Object> map_contrato_manual) {
        String id_contrato = (String) map_contrato_manual.get("id_contrato");
        Contratos contratos = new Contratos();
        contratos.setCodigo_empresa((String) map_contrato_manual.get("codigo_empresa"));
        contratos.setCodigo_sucursal((String) map_contrato_manual.get("codigo_sucursal"));
        contratos.setId_plan(id_contrato);
        contratos.setCodigo_administradora((String) map_contrato_manual.get("codigo_administradora"));
        contratos.setNro_contrato((String) map_contrato_manual.get("nro_contrato"));
        contratos.setNombre((String) map_contrato_manual.get("nombre"));

        Map<String, Object> map_contrato = new HashMap<String, Object>();
        map_contrato.put(IConstantesAutorizaciones.PARAM_CONTRATO, contratos);
        return map_contrato;
    }

    private void alimentarManual(Map<String, Object> map_contrato_manual, Map<String, Object> map_contrato) {
        String id_contrato = (String) map_contrato_manual.get("id_contrato");
        Manuales_tarifarios manuales_tarifarios = new Manuales_tarifarios();
        manuales_tarifarios.setCodigo_empresa((String) map_contrato_manual.get("codigo_empresa"));
        manuales_tarifarios.setCodigo_sucursal((String) map_contrato_manual.get("codigo_sucursal"));
        manuales_tarifarios.setId_contrato(id_contrato);
        manuales_tarifarios.setConsecutivo((Long) map_contrato_manual.get("consecutivo_mt"));
        manuales_tarifarios.setCodigo_administradora((String) map_contrato_manual.get("codigo_administradora"));
        manuales_tarifarios = getServiceLocator().getServicio(
                Manuales_tarifariosService.class).consultar(
                        manuales_tarifarios);
        if (manuales_tarifarios != null) {
            List<Manuales_tarifarios> list_manuales_tarifarios = (List<Manuales_tarifarios>) map_contrato
                    .get(IConstantesAutorizaciones.PARAM_LISTADO_MANUAL_TARIFARIO);
            if (list_manuales_tarifarios == null) {
                list_manuales_tarifarios = new ArrayList<Manuales_tarifarios>();
                map_contrato.put(IConstantesAutorizaciones.PARAM_LISTADO_MANUAL_TARIFARIO, list_manuales_tarifarios);
            }
            list_manuales_tarifarios.add(manuales_tarifarios);
        }
    }

    private Map<String, Object> getMapaContrato(String id_contrato,
            List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            Contratos contratos = (Contratos) map
                    .get(IConstantesAutorizaciones.PARAM_CONTRATO);
            if (contratos != null && contratos.getId_plan().equals(id_contrato)) {
                return map;
            }
        }
        return null;
    }

    /**
     * Este metodo me permite cargar todos los servicios, que pueda ordenar los
     * medicos
     *
     *
     */
    public void cargarServiciosPermitidosMedico() {
        if (getAdmision() != null) {
            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(getUsuarios().getCodigo_empresa());
            prestadores.setCodigo_sucursal(getUsuarios().getCodigo_sucursal());
            prestadores.setNro_identificacion(getUsuarios().getCodigo());
            prestadores = getServiceLocator().getServicio(PrestadoresService.class).consultar(prestadores);

            if (prestadores != null) {
                String tipo_usuario = IConstantes.TIPO_USUARIO_CONFIG_MEDICO_GENERAL;
                if (prestadores.getTipo_prestador().equals(IConstantes.PERSONAL_ATIENDE_MEDICO_ESPECIALISTA)) {
                    tipo_usuario = IConstantes.TIPO_USUARIO_CONFIG_MEDICO_ESPECIALISTA;
                }
                incializarPestanias(tipo_usuario, getAdmision().getVia_ingreso());
            }
        } else {
            log.error("No existe una admision para cargar ls servicios de los medicos");
        }
        seleccionarPrimeraPestania();
    }

    /**
     * Este metodo me permite seleccionar la primera pestania cada ves que se
     * cargue
	 *
     */
    private void seleccionarPrimeraPestania() {
        // Que tome la primera
        if (!tabs.getChildren().isEmpty()) {
            for (Component componentsTab : tabs.getChildren()) {
                if (componentsTab instanceof Tab
                        && ((Tab) componentsTab).isVisible()) {
                    ((Tab) componentsTab).setSelected(true);
                    break;
                }
            }
        }
    }

    public void setModo(String modo) {
        if (modo != null && !modo.trim().isEmpty()) {
            if (modo.equals("1")) {
                log.info("@setModo Cargar modo interno");
                modo_autorizacion = MOLD_AUTORIZACION.AUTORIZACION_INTERNA;
            }
        } else {
            setWidth("100%");
            setHeight("100%");
        }
    }

    public String getIdDiv() {
        return idDiv;
    }

    public void setIdDiv(String idDiv) {
        this.idDiv = idDiv;
    }

    public Admision getAdmision() {
        return admision;
    }

    public void setAdmision(Admision admision) {
        this.admision = admision;
        if (admision != null) {
            List<Component> components = tabs.getChildren();
            for (Component component : components) {
                if (component instanceof Tab) {
                    Tab tab = (Tab) component;
                    CargadorServiciosAutorizacionesTabPanel autorizacionesTabPanel = (CargadorServiciosAutorizacionesTabPanel) tab
                            .getAttribute(COMPONENTE_CARGARDOR_SERVICIO);
                    autorizacionesTabPanel.setAdmision(getAdmision());
                }
            }
        }
    }

    private List<Configuracion_servicios_autorizacion> getConfig() {
        return _listado_config_ser;
    }

    private void setConfig(List<Configuracion_servicios_autorizacion> config) {
        _listado_config_ser = config;
//		for (Configuracion_servicios_autorizacion configuracion_servicios_autorizacion : config) {
//		  log.info("Configuracion encontrada: " + configuracion_servicios_autorizacion.getNombre()); 	 
//		}
    }
}
