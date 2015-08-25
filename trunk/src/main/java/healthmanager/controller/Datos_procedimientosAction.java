package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Plantilla_pypService;
import healthmanager.modelo.service.VacunasService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Datos_procedimientosAction extends ZKWindow {

    private static final long serialVersionUID = 2183354871530698149L;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxPaciente;
    @View
    private Textbox tbxInfoPaciente;
    @View
    private Toolbarbutton btnLimpiarPaciente;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxPrestador;
    @View
    private Textbox tbxInfoPrestador;
    @View
    private Toolbarbutton btnLimpiarPrestador;

    @View
    private Listbox lbxNumeroIngreso;
    @View
    private Textbox tbxInfoAseguradora;
    @View
    private Textbox tbxAutorizacion;
    @View
    private Datebox dtbxFecha;

    @View
    private Doublebox dbxValor;
    @View
    private Spinner spinnerUnidades;
    @View
    private Doublebox dbxCopago;
    @View
    private Doublebox dbxTotalProcedimiento;

    @View
    private Listbox lbxAmbitoRealizacion;
    @View
    private Listbox lbxFinalidadProcedimiento;
    @View
    private Listbox lbxPersonalAtiende;
    @View
    private Listbox lbxFormaRealizacion;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxDiagPrincipal;
    @View
    private Textbox tbxInfoDiagPrincipal;
    @View
    private Toolbarbutton btnLimpiarDiagPrincipal;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxDiagRelacionado;
    @View
    private Textbox tbxInfoDiagRelacionado;
    @View
    private Toolbarbutton btnLimpiarDiagRelacionado;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxComplicacion;
    @View
    private Textbox tbxInfoComplicacion;
    @View
    private Toolbarbutton btnLimpiarComplicacion;
    @View
    private Textbox tbxCodigo_procedimiento;
    @View
    private Textbox tbxNomPcd;
    // @View private Toolbarbutton imgConsultar_pcd;
    // @View private Toolbarbutton imgQuitar_pcd;
    @View
    private Listbox lbxProgramaPYP;
    @View
    private Groupbox groupboxEditar;
    @View
    private Textbox tbxNro_factura;
    @View
    private Textbox tbxAccion;
    @View
    private Listbox lbxParameter;
    @View
    private Textbox tbxValue;
    @View
    private Rows rowsResultado;
    @View
    private Grid gridResultado;
    @View
    private Borderlayout borderlayoutEditar;
    @View
    private Groupbox groupboxConsulta;

    private List<Datos_procedimiento> lista_datos;

    @View
    private Textbox tbxPor_lote;

    private Map<String, Object> datos_seleccion = new HashMap<String, Object>();

    private final String[] idsExcluyentes = new String[]{"bandboxPaciente",
        "btnLimpiarPaciente", "btnLimpiarPrestador", "imgQuitar_pcd",
        "btnLimpiarDiagRelacionado", "btnLimpiarComplicacion",
        "tbxPor_lote", "tbxInfoDiagPrincipal", "bandboxDiagPrincipal"};

    // Jose Ojeda
    @View
    private Listbox listboxProcedimientos;
    private List<String> procedimientosSeleccionados = new ArrayList<String>();

    // private Listitem listitem_current;
    private Manuales_tarifarios manuales_tarifarios;

    private List<Map> lista_procedimientos = new ArrayList<Map>();

    @Override
    public void init() throws Exception {
        lista_datos = new ArrayList<Datos_procedimiento>();
        parametrizarBandbox();
        listarCombos();
        deshabilitarCampos(true);
        deshabilitarComponentes();
        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();
        String modulo_mostrar = request.getParameter("modulo_mostrar");
        String por_lotes = request.getParameter("por_lotes");
        boolean ocultarConsulta = false;
        String nro_ingreso = "";
        String nro_identificacion = "";
        Long codigo_registro = null;
        Map parametros = Executions.getCurrent().getArg();
        if (parametros != null) {
            if (parametros.isEmpty()) {
                parametros = null;
            }
        }
        if (parametros != null) {
            por_lotes = (String) parametros.get("por_lotes");
            modulo_mostrar = (String) parametros.get("modulo_mostrar");
            ocultarConsulta = (Boolean) parametros.get("ocultarConsulta");
            nro_ingreso = (String) parametros.get("nro_ingreso");
            nro_identificacion = (String) parametros.get("nro_identificacion");
            codigo_registro = (Long) parametros.get("codigo_registro");

        }
        tbxPor_lote.setValue((por_lotes != null ? por_lotes : "N"));

        if (tbxPor_lote.getValue().equals("S")) {
            modulo_mostrar = "registrar";
        }

        // activarLote();
        if (parametros != null) {
            cargarDatosModuloFactura(nro_ingreso, nro_identificacion,
                    codigo_registro, ocultarConsulta);
        } else {
            if (modulo_mostrar == null) {
                accionForm(true, "registrar");
            } else {
                if (modulo_mostrar.equalsIgnoreCase("consultar")) {
                    accionForm(false, "registrar");
                } else {
                    accionForm(true, "registrar");
                }
            }
        }

    }

    public void deshabilitarComponentes() {
        if (rol_usuario.equals(IRoles.FACTURADOR_CAPS)) {
            // log.info("rol facturador ===> " + rol_usuario);
            // dbxValor.setReadonly(true);
            dbxCopago.setReadonly(true);
            // dbxTotalProcedimiento.setReadonly(true);
            tbxAutorizacion.setReadonly(true);
        } else {
            dbxCopago.setReadonly(false);
            // dbxValor.setReadonly(false);
            // dbxTotalProcedimiento.setReadonly(false);
            tbxAutorizacion.setReadonly(false);
        }
    }

    private void cargarDatosModuloFactura(String nro_ingreso,
            String nro_identificacion, Long codigo_registro,
            boolean ocultarConsulta) throws Exception {

        bandboxPaciente.setDisabled(true);
        btnLimpiarPaciente.setVisible(false);
        lbxNumeroIngreso.setDisabled(true);
        ocultarBotoConsultar(ocultarConsulta);

        Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
        datos_procedimiento.setCodigo_empresa(empresa.getCodigo_empresa());
        datos_procedimiento.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        datos_procedimiento.setCodigo_registro(codigo_registro);
        datos_procedimiento = getServiceLocator()
                .getDatos_procedimientoService().consultar(datos_procedimiento);
        if (datos_procedimiento != null) {
            listboxProcedimientos.setVisible(false);
            mostrarDatos(datos_procedimiento);
        } else {
            listboxProcedimientos.setVisible(true);
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(empresa.getCodigo_empresa());
            paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            paciente.setNro_identificacion(nro_identificacion);
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);
            if (paciente != null) {
                bandboxPaciente.setValue(paciente.getNro_identificacion());
                tbxInfoPaciente.setValue(paciente.getNombreCompleto());
                datos_seleccion.put("sexo", paciente.getSexo());
                datos_seleccion.put("tipo_identificacion",
                        paciente.getTipo_identificacion());
                datos_seleccion.put("fecha_nac",
                        new java.text.SimpleDateFormat("dd/MM/yyyy")
                        .format(paciente.getFecha_nacimiento()));

                Admision aux2 = new Admision();
                aux2.setCodigo_empresa(empresa.getCodigo_empresa());
                aux2.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                aux2.setNro_identificacion(nro_identificacion);
                aux2.setNro_ingreso(nro_ingreso);
                aux2 = getServiceLocator().getServicio(AdmisionService.class)
                        .consultar(aux2);

                if (aux2 != null) {
                    // //log.info("aux2: "+aux2);

                    Administradora admin = new Administradora();
                    admin.setCodigo_empresa(aux2.getCodigo_empresa());
                    admin.setCodigo_sucursal(aux2.getCodigo_sucursal());
                    admin.setCodigo(aux2.getCodigo_administradora());
                    admin = getServiceLocator().getAdministradoraService()
                            .consultar(admin);

                    lbxNumeroIngreso.getItems().clear();
                    Listitem listitem = new Listitem();
                    listitem.setSelected(true);
                    listitem.setLabel(aux2.getNro_ingreso() + " -- "
                            + (admin != null ? admin.getNombre() : ""));
                    listitem.setValue(aux2);
                    cargarAdmisiones(aux2);
                    lbxNumeroIngreso.appendChild(listitem);
                    btnLimpiarPaciente.setVisible(false);

                }
            }
        }
    }

    private void ocultarBotoConsultar(boolean sw) {

        if (!sw) {
            ((Button) groupboxEditar.getFellow("btCancel")).setVisible(true);
            ((Button) groupboxEditar.getFellow("btNew")).setVisible(true);
        } else {
            ((Button) groupboxEditar.getFellow("btCancel")).setVisible(false);
            ((Button) groupboxEditar.getFellow("btNew")).setVisible(false);
        }
    }

    private void parametrizarBandbox() {
        parametrizarBandboxPaciente();
        parametrizarBandboxPrestador();
        parametrizarBandboxDiagnostico();
    }

    private void parametrizarBandboxPaciente() {
        bandboxPaciente.inicializar(tbxInfoPaciente, btnLimpiarPaciente);
        bandboxPaciente
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Paciente registro) {
                        listitem.setValue(registro);

                        Listcell listcell = new Listcell();
                        listcell.appendChild(new Label(registro
                                        .getTipo_identificacion() + ""));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro
                                        .getNro_identificacion() + ""));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getNombre1()
                                        + " " + registro.getNombre2()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getApellido1()
                                        + " " + registro.getApellido2()));
                        listitem.appendChild(listcell);
                    }

                    @Override
                    public List<Paciente> listarRegistros(String valorBusqueda,
                            Map<String, Object> parametros) {

                        parametros.put("codigo_empresa",
                                empresa.getCodigo_empresa());
                        parametros.put("codigo_sucursal",
                                sucursal.getCodigo_sucursal());
                        parametros.put("paramTodo", "");
                        parametros.put("value", "%"
                                + valorBusqueda.toUpperCase().trim() + "%");

                        parametros.put("limite_paginado", "limit 25 offset 0");

                        return Datos_procedimientosAction.this
                        .getServiceLocator().getPacienteService()
                        .listar(parametros);
                    }

                    @Override
                    public boolean seleccionarRegistro(Bandbox bandbox,
                            Textbox textboxInformacion, Paciente registro) {
                        Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
                        datos_procedimiento.setCodigo_empresa(registro
                                .getCodigo_empresa());
                        datos_procedimiento.setCodigo_sucursal(registro
                                .getCodigo_sucursal());
                        datos_procedimiento.setNro_identificacion(registro
                                .getNro_identificacion());

                        List<Admision> listaAdmisiones = Utilidades
                        .listarAdmisiones(
                                registro.getNro_identificacion(), "",
                                false, Datos_procedimientosAction.this);
                        if (listaAdmisiones.isEmpty()) {
                            Messagebox
                            .show("No se ha registrado el Ingreso del Paciente",
                                    "Paciente no admisionado",
                                    Messagebox.OK,
                                    Messagebox.EXCLAMATION);
                            limpiarDatos();
                            deshabilitarCampos(true);
                            return false;
                        }

                        bandbox.setValue(registro.getNro_identificacion());
                        textboxInformacion.setValue(registro
                                .getNombreCompleto());
                        // imgConsultar_pcd.setVisible(true);
                        datos_seleccion.put("sexo", registro.getSexo());
                        datos_seleccion.put("tipo_identificacion",
                                registro.getTipo_identificacion());
                        datos_seleccion
                        .put("fecha_nac",
                                new java.text.SimpleDateFormat(
                                        "dd/MM/yyyy").format(registro
                                        .getFecha_nacimiento()));

                        deshabilitarCampos(false);

                        Utilidades.listarIngresos(lbxNumeroIngreso,
                                listaAdmisiones, false,
                                Datos_procedimientosAction.this);
                        Admision aux2 = (!listaAdmisiones.isEmpty() ? listaAdmisiones
                        .get(0) : null);
                        cargarAdmisiones(aux2);
                        return true;
                    }

                    @Override
                    public void limpiarSeleccion(Bandbox bandbox) {
                        limpiarDatos();
                        datos_seleccion.remove("sexo");
                        datos_seleccion.remove("fecha_nac");
                        datos_seleccion.remove("tipo_identificacion");
                        Utilidades.listarIngresos(lbxNumeroIngreso,
                                new LinkedList<Admision>(), true,
                                Datos_procedimientosAction.this);
                        deshabilitarCampos(true);
                    }
                });

    }

    private void parametrizarBandboxPrestador() {
        bandboxPrestador.inicializar(tbxInfoPrestador, btnLimpiarPrestador);
        bandboxPrestador
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Map<String, Object> registro) {
                        Listcell listcell = new Listcell();
                        listcell.appendChild(new Label((String) registro
                                        .get(Utilidades.CODIGO_USUARIO)));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label((String) registro
                                        .get(Utilidades.NOMBRES)));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label((String) registro
                                        .get(Utilidades.APELLIDOS)));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(Utilidades
                                        .getTipoMedico((String) registro
                                                .get(Utilidades.TIPO_MEDICO))));
                        listitem.appendChild(listcell);
                    }

                    @Override
                    public List<Map<String, Object>> listarRegistros(
                            String valorBusqueda, Map<String, Object> parametros) {
                                parametros.put("paramTodo", "");
                                parametros.put("value", "%"
                                        + valorBusqueda.toUpperCase().trim() + "%");
                                parametros.put("codigo_empresa",
                                        sucursal.getCodigo_empresa());
                                parametros.put("codigo_sucursal",
                                        sucursal.getCodigo_sucursal());
                                parametros.put("rol", "05");

                                getServiceLocator().getUsuariosService().setLimit(
                                        "limit 25 offset 0");

                                return getServiceLocator().getUsuariosService()
                                .getUsuarioByRol(parametros);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion,
                                    Map<String, Object> registro) {
                                bandbox.setValue((String) registro
                                        .get(Utilidades.CODIGO_USUARIO));
                                textboxInformacion.setValue((String) registro
                                        .get(Utilidades.NOMBRES)
                                        + " "
                                        + (String) registro.get(Utilidades.APELLIDOS));
                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {
                            }

                });
    }

    private void parametrizarBandboxDiagnostico() {
        bandboxDiagPrincipal.inicializar(tbxInfoDiagPrincipal,
                btnLimpiarDiagPrincipal);
        bandboxDiagRelacionado.inicializar(tbxInfoDiagRelacionado,
                btnLimpiarDiagRelacionado);
        bandboxComplicacion.inicializar(tbxInfoComplicacion,
                btnLimpiarComplicacion);
        BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {

            @Override
            public void renderListitem(Listitem listitem, Cie registro) {
                listitem.setValue(registro);

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(registro.getCodigo() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNombre() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getSexo()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getLimite_inferior()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getLimite_superior()));
                listitem.appendChild(listcell);
            }

            @Override
            public List<Cie> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {
                parametros.put("paramTodo", "");
                parametros.put("value", "%"
                        + valorBusqueda.toUpperCase().trim() + "%");

                parametros.put("limite_paginado", "limit 25 offset 0");

                return getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, parametros);

            }

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Cie registro) {
                try {
                    Map map = new HashMap();
                    map.put("nombre_entidad", registro.getNombre());
                    map.put("limite_inferior", registro.getLimite_inferior());
                    map.put("limite_superior", registro.getLimite_superior());
                    map.put("sexo_entidad", registro.getSexo());

                    map.put("fecha_nac", datos_seleccion.get("fecha_nac"));
                    map.put("sexo_pct", datos_seleccion.get("sexo"));
                    Map result = Utilidades.validarInformacionLimiteSexo(
                            "Diagnostico", registro.getCodigo(),
                            registro.getLimite_inferior(),
                            registro.getLimite_superior(), registro.getSexo(),
                            (String) datos_seleccion.get("fecha_nac"),
                            (String) datos_seleccion.get("sexo"));
                    if (!((Boolean) result.get("success"))) {
                        throw new Exception((String) result.get("msg"));
                    }
                    bandbox.setValue(registro.getCodigo());
                    textboxInformacion.setValue(registro.getNombre());

                    if (bandbox.equals(bandboxDiagPrincipal)) {
                        datos_seleccion.put("cie_diagnostico_principal",
                                registro);
                    } else if (bandbox.equals(bandboxDiagRelacionado)) {
                        datos_seleccion.put("cie_diagnostico_relacionado",
                                registro);
                    } else if (bandbox.equals(bandboxComplicacion)) {
                        datos_seleccion.put("cie_complicacion", registro);
                    }

                } catch (Exception e) {
                    MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
                }

                return true;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
                if (bandbox.equals(bandboxDiagPrincipal)) {
                    // log.info("limpiar bandboxDiagPrincipal");
                    datos_seleccion.remove("cie_diagnostico_principal");
                } else if (bandbox.equals(bandboxDiagRelacionado)) {
                    // log.info("limpiar bandboxDiagRelacionado");
                    datos_seleccion.remove("cie_diagnostico_relacionado");
                } else if (bandbox.equals(bandboxComplicacion)) {
                    // log.info("limpiar bandboxComplicacion");
                    datos_seleccion.remove("cie_complicacion");
                }
            }

        };

        bandboxDiagPrincipal.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxDiagRelacionado.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxComplicacion.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    // Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue()
                    .toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");
            parameters.put("es_quirurgico", "N");

            parameters.put("limite_paginado", "limit 25 offset 0");

            List<Datos_procedimiento> lista_datos = getServiceLocator()
                    .getDatos_procedimientoService().listarResultados(
                            parameters);
            rowsResultado.getChildren().clear();

            for (Datos_procedimiento datos_procedimiento : lista_datos) {
                rowsResultado
                        .appendChild(crearFilas(datos_procedimiento, this));
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

        final Datos_procedimiento datos_procedimiento = (Datos_procedimiento) objeto;

        Paciente paciente = datos_procedimiento.getPaciente();
        String nombres_paciente = (paciente != null ? paciente.getNombre1()
                + " " + paciente.getNombre2() : "");
        String apellidos_paciente = (paciente != null ? paciente.getApellido1()
                + " " + paciente.getApellido2() : "");

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(datos_procedimiento.getCodigo_registro()
                + ""));
        fila.appendChild(new Label(datos_procedimiento.getTipo_identificacion()
                + ""));
        fila.appendChild(new Label(datos_procedimiento.getNro_identificacion()
                + ""));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                .format(datos_procedimiento.getFecha_procedimiento()) + ""));
        fila.appendChild(new Label(apellidos_paciente + ""));
        fila.appendChild(new Label(nombres_paciente + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(datos_procedimiento);
            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Datos_procedimiento datos_procedimiento = (Datos_procedimiento) obj;
        try {
            datos_procedimiento = getServiceLocator().getServicio(
                    Datos_procedimientoService.class).consultar(
                            datos_procedimiento);
            deshabilitarCampos(false);
            tbxNro_factura.setValue(datos_procedimiento.getCodigo_registro()
                    + "");
            datos_seleccion.put("tipo_identificacion",
                    datos_procedimiento.getTipo_identificacion());
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
            paciente.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            paciente.setNro_identificacion(datos_procedimiento
                    .getNro_identificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            bandboxPaciente.seleccionarRegistro(paciente,
                    datos_procedimiento.getNro_identificacion(),
                    (paciente != null ? paciente.getNombreCompleto() : ""));
            datos_seleccion.put("sexo", (paciente != null ? paciente.getSexo()
                    : ""));
            datos_seleccion.put(
                    "fecha_nac",
                    (paciente != null ? new SimpleDateFormat("dd/MM/yyyy")
                    .format(paciente.getFecha_nacimiento()) : ""));
            datos_seleccion.put("codigo_administradora",
                    datos_procedimiento.getCodigo_administradora());
            datos_seleccion.put("id_plan", datos_procedimiento.getId_plan());

            Administradora administradora = new Administradora();
            administradora.setCodigo_empresa(datos_procedimiento
                    .getCodigo_empresa());
            administradora.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            administradora.setCodigo(datos_procedimiento
                    .getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            Admision admision = new Admision();
            admision.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
            admision.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            admision.setNro_identificacion(datos_procedimiento
                    .getNro_identificacion());
            admision.setNro_ingreso(datos_procedimiento.getNro_ingreso());
            admision = getServiceLocator().getServicio(AdmisionService.class)
                    .consultar(admision);

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(admision);
            String[] unidades = ServiciosDisponiblesUtils
                    .getServicios(admision);

            Contratos contratos = new Contratos();
            contratos
                    .setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
            contratos.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            contratos.setCodigo_administradora(datos_procedimiento
                    .getCodigo_administradora());
            contratos.setId_plan(datos_procedimiento.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(
                    contratos);
            tbxInfoAseguradora
                    .setValue((administradora != null ? administradora
                            .getNombre() : "")
                            + " - "
                            + (contratos != null ? contratos.getNombre() : ""));

            this.manuales_tarifarios = manuales_tarifarios;

            datos_seleccion.put("manual", manuales_tarifarios);

            datos_seleccion.put(
                    "pyp_plan",
                    (unidades != null ? (ServiciosDisponiblesUtils
                    .isServicioPyp(unidades) ? "S" : "N") : "N"));
            datos_seleccion.put("anio",
                    (contratos != null ? manuales_tarifarios.getAnio() : ""));

            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(datos_procedimiento
                    .getCodigo_empresa());
            prestadores.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            prestadores.setNro_identificacion(datos_procedimiento
                    .getCodigo_prestador());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);

            bandboxPrestador.seleccionarRegistro(prestadores,
                    datos_procedimiento.getCodigo_prestador(),
                    (prestadores != null ? prestadores.getNombres() + " "
                    + prestadores.getApellidos() : ""));

            Utilidades.listarIngresos(lbxNumeroIngreso, Utilidades
                    .listarAdmisiones(
                            datos_procedimiento.getNro_identificacion(),
                            datos_procedimiento.getNro_ingreso(), true, this),
                    false, this);

            Utilidades.seleccionarListitem(lbxNumeroIngreso,
                    datos_procedimiento.getNro_ingreso());

            tbxCodigo_procedimiento.setValue(datos_procedimiento
                    .getCodigo_procedimiento());
            Map<String, Object> bean = Utilidades.getProcedimiento(
                    manuales_tarifarios,
                    new Long(datos_procedimiento.getCodigo_procedimiento()),
                    getServiceLocator());
            tbxNomPcd.setValue((String) bean.get("nombre_procedimiento"));
            datos_seleccion.put("sexo_pcd", (String) bean.get("sexo_pcd"));
            datos_seleccion.put("limite_inferior_pcd",
                    (String) bean.get("limite_inferior_pcd"));
            datos_seleccion.put("limite_superior_pcd",
                    (String) bean.get("limite_superior_pcd"));
            datos_seleccion.put("es_pyp", (String) bean.get("es_pyp"));
            datos_seleccion.put("valor_real",
                    datos_procedimiento.getValor_real());
            datos_seleccion
                    .put("descuento", datos_procedimiento.getDescuento());
            datos_seleccion.put("incremento",
                    datos_procedimiento.getIncremento());
            dtbxFecha.setValue(datos_procedimiento.getFecha_procedimiento());

            tbxAutorizacion.setValue(datos_procedimiento
                    .getNumero_autorizacion());

            dbxValor.setValue(datos_procedimiento.getValor_procedimiento());
            spinnerUnidades.setValue(datos_procedimiento.getUnidades());
            dbxCopago.setValue(datos_procedimiento.getCopago());
            dbxTotalProcedimiento.setValue(datos_procedimiento.getValor_neto());
            Utilidades.seleccionarListitem(lbxAmbitoRealizacion,
                    datos_procedimiento.getAmbito_procedimiento());
            Utilidades.seleccionarListitem(lbxFinalidadProcedimiento,
                    datos_procedimiento.getFinalidad_procedimiento());
            Utilidades.seleccionarListitem(lbxPersonalAtiende,
                    datos_procedimiento.getPersonal_atiende());
            Utilidades.seleccionarListitem(lbxFormaRealizacion,
                    datos_procedimiento.getForma_realizacion());

            Cie cie = new Cie();
            cie.setCodigo(datos_procedimiento.getCodigo_diagnostico_principal());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxDiagPrincipal.seleccionarRegistro(cie,
                    datos_procedimiento.getCodigo_diagnostico_principal(),
                    (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_diagnostico_principal", cie);

            cie = new Cie();
            cie.setCodigo(datos_procedimiento
                    .getCodigo_diagnostico_relacionado());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxDiagRelacionado.seleccionarRegistro(cie,
                    datos_procedimiento.getCodigo_diagnostico_relacionado(),
                    (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_diagnostico_relacionado", cie);

            cie = new Cie();
            cie.setCodigo(datos_procedimiento.getComplicacion());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxComplicacion.seleccionarRegistro(cie,
                    datos_procedimiento.getComplicacion(),
                    (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_complicacion", cie);

            Utilidades.seleccionarListitem(lbxProgramaPYP,
                    datos_procedimiento.getCodigo_programa());

            btnLimpiarPaciente.setVisible(false);
            bandboxPaciente.setDisabled(true);

            validarRegistroEditar(datos_procedimiento);

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    /*
     * public void activarLote() { if (tbxPor_lote.getValue().equals("S")) {
     * gridProcedimientos.setVisible(true); ((Button)
     * groupboxEditar.getFellow("btCancel")).setVisible(false); } else {
     * gridProcedimientos.setVisible(false); ((Button)
     * groupboxEditar.getFellow("btCancel")).setVisible(true); } }
     */
    public String getEstado_admision(Datos_procedimiento datos_procedimiento)
            throws Exception {
        String estado = "1";

        Admision admision = new Admision();
        admision.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
        admision.setCodigo_sucursal(datos_procedimiento.getCodigo_sucursal());
        admision.setNro_ingreso(datos_procedimiento.getNro_ingreso());
        admision.setNro_identificacion(datos_procedimiento
                .getNro_identificacion());
        admision = getServiceLocator().getServicio(AdmisionService.class)
                .consultar(admision);
        estado = (admision != null ? admision.getEstado() : "1");

        return estado;
    }

    private void validarRegistroEditar(Datos_procedimiento datos_procedimiento)
            throws Exception {
        String estado_admision = Utilidades.getEstado_admision(
                datos_procedimiento.getNro_ingreso(),
                datos_procedimiento.getNro_identificacion(), this);
        if (this.getParent() instanceof Facturacion_ripsAction) {
            estado_admision = "1";
        }
        if (estado_admision.equals("2")) {
            bloqueoBotonGuardar(true);
        } else {
            bloqueoBotonGuardar(false);
        }
    }

    private void bloqueoBotonGuardar(boolean sw) {

        if (!sw) {
            ((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(false);
            ((Button) groupboxEditar.getFellow("btGuardar"))
                    .setImage("/images/Save16.gif");
        } else {
            ((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(true);
            ((Button) groupboxEditar.getFellow("btGuardar"))
                    .setImage("/images/Save16_disabled.gif");
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Datos_procedimiento datos_procedimiento = (Datos_procedimiento) obj;
        try {
            int result = getServiceLocator().getDatos_procedimientoService()
                    .eliminarActualizarFactura(datos_procedimiento);
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

    public void listarCombos() {
        listarParameter();
        Utilidades.listarElementoListbox(lbxAmbitoRealizacion, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxFinalidadProcedimiento, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxPersonalAtiende, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxFormaRealizacion, false,
                getServiceLocator());
        Utilidades.listarProgramasPYP(lbxProgramaPYP, true, this);
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("nro_identificacion");
        listitem.setLabel("Identificacion");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("nombre1||' '||nombre2");
        listitem.setLabel("Nombres");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("apellido1||' '||apellido2");
        listitem.setLabel("Apellidos");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("nro_factura");
        listitem.setLabel("Nro registro");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("fecha_procedimiento");
        listitem.setLabel("Fecha(aaaa-mm-dd)");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    public void cargarAdmisiones(Admision aux2) {

        if (aux2 != null) {
            deshabilitarCampos(false);

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(aux2);
            String[] unidad_funcional = ServiciosDisponiblesUtils
                    .getServicios(aux2);

            Administradora admin = new Administradora();
            admin.setCodigo_empresa(aux2.getCodigo_empresa());
            admin.setCodigo_sucursal(aux2.getCodigo_sucursal());
            admin.setCodigo(aux2.getCodigo_administradora());
            admin = getServiceLocator().getAdministradoraService().consultar(
                    admin);

            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
            prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            prestadores.setNro_identificacion(aux2.getCodigo_medico());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);

            Cie cie = new Cie();
            cie.setCodigo(aux2.getDiagnostico_ingreso());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            Contratos plan = new Contratos();
            plan.setCodigo_empresa(empresa.getCodigo_empresa());
            plan.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            plan.setCodigo_administradora(aux2.getCodigo_administradora());
            plan.setId_plan(aux2.getId_plan());
            plan = getServiceLocator().getContratosService().consultar(plan);

            String administradora = "";
            administradora = (admin != null ? admin.getNombre() : "");
            administradora += " - ";
            administradora += (plan != null ? plan.getNombre() : "");
            // String manual = (plan != null ?
            // manuales_tarifarios.getManual_tarifario() : "");
            String pyp = (unidad_funcional != null ? (ServiciosDisponiblesUtils
                    .isServicioPyp(unidad_funcional) ? "S" : "N") : "N");
            String anio = (plan != null ? manuales_tarifarios.getAnio() : "");

            bandboxPrestador.seleccionarRegistro(prestadores,
                    aux2.getCodigo_medico(),
                    (prestadores != null ? prestadores.getNombres() + " "
                    + prestadores.getApellidos() : ""));

            bandboxDiagPrincipal.seleccionarRegistro(cie,
                    aux2.getDiagnostico_ingreso(),
                    (cie != null ? cie.getNombre() : ""));

            bandboxDiagPrincipal.setValue("");
            tbxInfoDiagPrincipal.setValue("");

            this.manuales_tarifarios = manuales_tarifarios;

            datos_seleccion
                    .put("sexo_dxpp", (cie != null ? cie.getSexo() : ""));
            datos_seleccion.put("limite_inferior_dxpp",
                    (cie != null ? cie.getLimite_inferior() : ""));
            datos_seleccion.put("limite_superior_dxpp",
                    (cie != null ? cie.getLimite_superior() : ""));

            tbxInfoAseguradora.setValue(administradora);
            datos_seleccion.put("codigo_administradora",
                    aux2.getCodigo_administradora());
            datos_seleccion.put("id_plan", aux2.getId_plan());
            datos_seleccion.put("manual", manuales_tarifarios);

            datos_seleccion.put("anio", anio);
            datos_seleccion.put("pyp_plan", pyp);

            dtbxFecha.setValue(aux2.getFecha_ingreso());

            tbxAutorizacion.setValue(aux2.getNro_autorizacion() != null ? aux2
                    .getNro_autorizacion() : "");
        }
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        spinnerUnidades.setValue(1);
        bandboxPaciente.setValue("");
        bandboxPaciente.setDisabled(false);
        deshabilitarCampos(true);
        dbxValor.setText("0.00");
        dbxTotalProcedimiento.setText("");
        spinnerUnidades.setText("1");
    }

    public void deshabilitarCampos(boolean sw) {
        FormularioUtil.deshabilitarComponentes(groupboxEditar, sw,
                idsExcluyentes);
        bloqueoBotonGuardar(sw);
        if (sw) {
            Utilidades.listarIngresos(lbxNumeroIngreso,
                    new LinkedList<Admision>(), true, this);
        }
        // Grilla //
        lista_datos.clear();
        // crearFilas();
    }

    public boolean validarDiagnostico() {

        boolean valida = true;

        String mensaje = "El campo Diag Ppal es obligatorio para agregar registros";

        if (bandboxDiagPrincipal.getText().trim().equals("")) {
            MensajesUtil.notificarAlerta(mensaje, bandboxDiagPrincipal);
            valida = false;
        }

        return valida;

    }

    public void openPcd() throws Exception {

        if (validarDiagnostico()) {
            String pages = "";

            String anio = datos_seleccion.get("anio").toString();

            pages = "/pages/openProcedimientos.zul";

            Admision admision = lbxNumeroIngreso.getSelectedItem().getValue();

            Map parametros = new HashMap();
            parametros.put("codigo_empresa", empresa.getCodigo_empresa());
            parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parametros.put("codigo_administradora",
                    datos_seleccion.get("codigo_administradora"));
            parametros.put("id_plan", datos_seleccion.get("id_plan"));
            parametros.put("restringido", "");
            parametros.put("nro_ingreso",
                    (admision != null ? admision.getNro_ingreso() : ""));
            parametros.put("nro_identificacion", bandboxPaciente.getValue());
            parametros.put("anio", anio);
            parametros.put("seleccionados", procedimientosSeleccionados);
            parametros.put("via_ingreso",
                    admision != null ? admision.getVia_ingreso() : null);

            // este filtro es para que no permita facturar consultas en el rips
            // de procedimientos
            parametros.put("consulta", "N");

            parametros.put("pyp", datos_seleccion.get("pyp_plan"));

            if (admision != null) {
                if (admision.getVia_ingreso().equals(
                        IVias_ingreso.VIA_VACUNACION)) {
                    parametros.put("via_ingreso", admision.getVia_ingreso());
                }

                // Esta validacion me permite, mostrar los procedimientos de PyP
                // cuando es odontologia
                if (admision.getVia_ingreso()
                        .equals(IVias_ingreso.ODONTOLOGIA2)) {
                    parametros.remove("pyp");
                }
            }

            parametros.put("ocultar_filtro_procedimiento",
                    "ocultar_filtro_procedimiento");

            Component componente = Executions.createComponents(pages, this,
                    parametros);
            final Window ventana = (Window) componente;
            ventana.setWidth("990px");
            ventana.setHeight("400px");
            ventana.setClosable(true);
            ventana.setMaximizable(true);
            ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
            ventana.setMode("modal");
        }
    }

    @Override
    public void adicionarPcd(Map pcd) throws Exception {
        final String codigo_procedimiento = pcd.get("codigo_procedimiento")
                .toString();
        // log.info("1------" + codigo_procedimiento);

        procedimientosSeleccionados.add(codigo_procedimiento);

        Admision admision = ((Admision) lbxNumeroIngreso.getSelectedItem()
                .getValue());
        String codigo_diagnostico_vacuna = bandboxDiagPrincipal.getValue();
        if (admision != null
                && admision.getVia_ingreso().equals(
                        IVias_ingreso.VIA_VACUNACION)) {
            Vacunas vacunas = new Vacunas();
            vacunas.setCodigo_empresa(admision.getCodigo_empresa());
            vacunas.setCodigo_sucursal(admision.getCodigo_sucursal());
            vacunas.setCodigo_procedimiento(codigo_procedimiento);
            vacunas = getServiceLocator().getServicio(VacunasService.class)
                    .consultar(vacunas);
            if (vacunas != null) {
                codigo_diagnostico_vacuna = vacunas.getCodigo_cie();
                Cie cie = new Cie();
                cie.setCodigo(codigo_diagnostico_vacuna);
                cie = getServiceLocator().getServicio(GeneralExtraService.class)
                        .consultar(cie);
                if (cie != null) {
                    bandboxDiagPrincipal.seleccionarRegistro(cie,
                            cie.getCodigo(), cie.getNombre());
                }
            } else {
                // log.info("vacuna no encontrada; " + codigo_cups);
            }
        }

        lista_procedimientos.add(pcd);
        mostrarInformacion();

    }

    public void mostrarInformacion() {

        listboxProcedimientos.getItems().clear();

        for (final Map mapa_datos : lista_procedimientos) {

            final Listitem listitem = new Listitem();
            listitem.setValue(mapa_datos.get("codigo_procedimiento"));

            listitem.appendChild(new Listcell());

            Listcell listcell = new Listcell();
            Textbox textbox = new Textbox(mapa_datos
                    .get("codigo_procedimiento").toString());
            textbox.setInplace(true);
            textbox.setWidth("95%");
            textbox.setReadonly(true);
            listcell.appendChild(textbox);
            listitem.appendChild(listcell);

            listcell = new Listcell();
            textbox = new Textbox(mapa_datos.get("nombre_procedimiento")
                    .toString());
            textbox.setInplace(true);
            textbox.setWidth("95%");
            textbox.setReadonly(true);
            listcell.appendChild(textbox);
            listitem.appendChild(listcell);

            listcell = new Listcell();
            Datebox datebox = new Datebox(dtbxFecha.getValue());
            datebox.setInplace(true);
            datebox.setWidth("95%");
            datebox.setReadonly(true);
            datebox.setButtonVisible(false);
            listcell.appendChild(datebox);
            listitem.appendChild(listcell);

            listcell = new Listcell();
            textbox = new Textbox(bandboxDiagPrincipal.getValue());
            textbox.setInplace(true);
            textbox.setWidth("95%");
            textbox.setReadonly(true);
            listcell.appendChild(textbox);
            listitem.appendChild(listcell);

            listitem.setAttribute("valor_total", mapa_datos.get("valor_total"));
            listitem.setAttribute("descuento", mapa_datos.get("descuento"));
            listitem.setAttribute("incremento", mapa_datos.get("incremento"));
            listitem.setAttribute("valor_real", mapa_datos.get("valor_real"));
            listitem.setAttribute("valor",
                    mapa_datos.get("valor_procedimiento"));

            mapa_datos.put("codigo_procedimiento",
                    mapa_datos.get("codigo_procedimiento").toString());
            mapa_datos.put("valor", mapa_datos.get("valor_procedimiento"));
            mapa_datos.put("unidades", spinnerUnidades.getValue());
            mapa_datos.put("copago", dbxCopago.getValue());
            mapa_datos.put("total_procedimiento",
                    dbxTotalProcedimiento.getValue());
            mapa_datos.put("ambito_realizacion", lbxAmbitoRealizacion
                    .getSelectedItem().getValue().toString());
            mapa_datos.put("finalidad_procedimiento", lbxFinalidadProcedimiento
                    .getSelectedItem().getValue().toString());
            mapa_datos.put("personal_atiende", lbxPersonalAtiende
                    .getSelectedItem().getValue().toString());
            mapa_datos.put("forma_realizacion", lbxFormaRealizacion
                    .getSelectedItem().getValue().toString());
            mapa_datos.put("diagnostico_principal",
                    bandboxDiagPrincipal.getValue());
            mapa_datos.put("diagnostico_relacionado",
                    bandboxDiagRelacionado.getValue());
            mapa_datos.put("complicacion", bandboxComplicacion.getValue());
            mapa_datos.put("programa", lbxProgramaPYP.getSelectedItem()
                    .getValue().toString());

            listitem.setAttribute("detalle", mapa_datos);
            // log.info("listitem----------" +
            // listitem.getAttribute("detalle"));

            final Image image = new Image("/images/borrar.gif");
            image.setStyle("cursor:pointer");
            image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                @Override
                public void onEvent(Event event) throws Exception {
                    procedimientosSeleccionados.remove(mapa_datos.get(
                            "codigo_procedimiento").toString());
                    listitem.detach();
                }

            });

            listcell = new Listcell();
            listcell.appendChild(image);
            listitem.appendChild(listcell);

            listboxProcedimientos.appendChild(listitem);
        }

    }

    public void onSeleccionarListboxProcedimientos() {
        // Listitem listitem = listboxProcedimientos.getSelectedItem();
        // String codigo_procedimiento = listitem.getValue().toString();
        // //log.info("codigo_procedimiento------"+codigo_procedimiento);
        //
        try {
            // if (listitem_current != null) {
            //
            // Map<String, Object> mapa_datos_aux = new HashMap<String,
            // Object>();
            // mapa_datos_aux.put("codigo_procedimiento", codigo_procedimiento);
            // mapa_datos_aux.put("valor", listitem.getAttribute("valor"));
            // mapa_datos_aux.put("unidades", spinnerUnidades.getValue());
            // mapa_datos_aux.put("copago", dbxCopago.getValue());
            // mapa_datos_aux.put("ambito_realizacion", lbxAmbitoRealizacion
            // .getSelectedItem().getValue().toString());
            // mapa_datos_aux.put("finalidad_procedimiento",
            // lbxFinalidadProcedimiento.getSelectedItem().getValue()
            // .toString());
            // mapa_datos_aux.put("personal_atiende", lbxPersonalAtiende
            // .getSelectedItem().getValue().toString());
            // mapa_datos_aux.put("forma_realizacion", lbxFormaRealizacion
            // .getSelectedItem().getValue().toString());
            // mapa_datos_aux.put("diagnostico_principal",
            // bandboxDiagPrincipal.getValue());
            // mapa_datos_aux.put("diagnostico_relacionado",
            // bandboxDiagRelacionado.getValue());
            // mapa_datos_aux.put("complicacion",
            // bandboxComplicacion.getValue());
            // mapa_datos_aux.put("programa", lbxProgramaPYP.getSelectedItem()
            // .getValue().toString());
            //
            // listitem_current.setAttribute("detalle", mapa_datos_aux);
            // //log.info("1------"+listitem_current
            // .getAttribute("detalle"));
            //
            // //listitem = listitem_current;
            //
            // }

            int indice = listboxProcedimientos.getSelectedIndex();
            editarDatos(lista_procedimientos.get(indice));

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", null);
        }

    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        boolean valida = true;

        String mensaje = "Los campos marcadosson obligatorios";

        if (bandboxPaciente.getText().trim().equals("")) {
            mensaje = "El campo Paciente es obligatorio";
            MensajesUtil.notificarAlerta(mensaje, bandboxPaciente);
            valida = false;
        }

        if (bandboxPrestador.getText().trim().equals("")) {
            mensaje = "El campo Prestador es obligatorio";
            MensajesUtil.notificarAlerta(mensaje, bandboxPrestador);
            valida = false;
        }

        if (bandboxDiagPrincipal.getText().trim().equals("")) {
            mensaje = "El campo Diag Ppal es obligatorio";
            MensajesUtil.notificarAlerta(mensaje, bandboxDiagPrincipal);
            valida = false;
        }

        Admision admision = ((Admision) lbxNumeroIngreso.getSelectedItem()
                .getValue());

        if (admision == null) {
            mensaje = "El campo Nro ingreso es obligatorio";
            MensajesUtil.notificarAlerta(mensaje, lbxNumeroIngreso);
            valida = false;
        }

        if (valida) {
            if (!(spinnerUnidades.getValue() != null && spinnerUnidades
                    .getValue() > 0)) {
                mensaje = "El valor de las unidades debe ser mayor que cero";
                valida = false;
            } else if (lbxFinalidadProcedimiento.getSelectedItem().getValue()
                    .equals("10")
                    && datos_seleccion.get("pyp_plan").toString().equals("S")) {
                mensaje = "Para contratos pyp debe seleccionar finalidad consulta";
                MensajesUtil
                        .notificarAlerta(mensaje, lbxFinalidadProcedimiento);
                valida = false;
            }
        }

        if (valida) {

            Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
            configuracion_admision_ingreso.setCodigo_empresa(codigo_empresa);
            configuracion_admision_ingreso.setCodigo_sucursal(codigo_sucursal);
            configuracion_admision_ingreso.setVia_ingreso(admision
                    .getVia_ingreso());

            configuracion_admision_ingreso = getServiceLocator().getServicio(
                    Configuracion_admision_ingresoService.class).consultar(
                            configuracion_admision_ingreso);

            if (configuracion_admision_ingreso == null) {
                throw new ValidacionRunTimeException(
                        "La via de ingreso "
                        + admision.getVia_ingreso()
                        + " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
            }

            if (datos_seleccion.get("pyp_plan").toString().equals("S")
                    && configuracion_admision_ingreso.getEs_pyp()) {
                if (admision.getVia_ingreso().equals(
                        IVias_ingreso.VIA_VACUNACION)) {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("codigo_empresa", admision.getCodigo_empresa());
                    param.put("codigo_sucursal", admision.getCodigo_sucursal());
                    param.put("codigo_cups", tbxCodigo_procedimiento.getValue());
                    boolean vacuna_registrada = getServiceLocator()
                            .getServicio(VacunasService.class).existe(param);
                    if (!vacuna_registrada) {
                        mensaje = "El procedimiento no esta registrado como vacuna por favor comuniquese con el administrador del sistema";
                        valida = false;
                    }
                } else {
                    // Modificado por las actividades tiene mas de 1 código
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("codigo_pdc_like",
                            tbxCodigo_procedimiento.getValue());
                    param.put("area_intervencion", lbxProgramaPYP
                            .getSelectedItem().getValue().toString());
                    boolean existe = getServiceLocator().getServicio(
                            Plantilla_pypService.class).existe(param);
                    if (!existe) {
                        mensaje = "El procedimiento y finalidad de consulta no se encuntra en el programa pyp";
                        valida = false;
                    }
                }
            }

            if (!valida) {
                Messagebox.show(mensaje, usuarios.getNombres()
                        + " recuerde que...", Messagebox.OK,
                        Messagebox.EXCLAMATION);
            }
        }

        return valida;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                // Cargamos los componentes //

                if (listboxProcedimientos.getSelectedItem() != null) {
                    Map<String, Object> mapa_datos_aux = new HashMap<String, Object>();
                    // mapa_datos_aux.put("codigo_procedimiento",
                    // codigo_procedimiento);

                    mapa_datos_aux.put("valor", dbxValor.getValue());
                    mapa_datos_aux.put("unidades", spinnerUnidades.getValue());
                    mapa_datos_aux.put("copago", dbxCopago.getValue());
                    mapa_datos_aux.put("total_procedimiento",
                            dbxTotalProcedimiento.getValue());
                    mapa_datos_aux.put("ambito_realizacion",
                            lbxAmbitoRealizacion.getSelectedItem().getValue()
                            .toString());
                    mapa_datos_aux.put("finalidad_procedimiento",
                            lbxFinalidadProcedimiento.getSelectedItem()
                            .getValue().toString());
                    mapa_datos_aux.put("personal_atiende", lbxPersonalAtiende
                            .getSelectedItem().getValue().toString());
                    mapa_datos_aux.put("forma_realizacion", lbxFormaRealizacion
                            .getSelectedItem().getValue().toString());
                    mapa_datos_aux.put("diagnostico_principal",
                            bandboxDiagPrincipal.getValue());
                    mapa_datos_aux.put("diagnostico_relacionado",
                            bandboxDiagRelacionado.getValue());
                    mapa_datos_aux.put("complicacion",
                            bandboxComplicacion.getValue());
                    mapa_datos_aux.put("programa", lbxProgramaPYP
                            .getSelectedItem().getValue().toString());
                    listboxProcedimientos.getSelectedItem().setAttribute(
                            "detalle", mapa_datos_aux);

                }

                List<Datos_procedimiento> lista_procedimiento = new ArrayList<Datos_procedimiento>();

                for (Listitem listitem : listboxProcedimientos.getItems()) {

                    String codigo_procedimiento = listitem.getValue()
                            .toString();

                    Admision admision = ((Admision) lbxNumeroIngreso
                            .getSelectedItem().getValue());

                    Map<String, Object> datos_guardar = (Map<String, Object>) listitem
                            .getAttribute("detalle");

                    Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
                    datos_procedimiento.setCodigo_empresa(empresa
                            .getCodigo_empresa());
                    datos_procedimiento.setCodigo_sucursal(sucursal
                            .getCodigo_sucursal());
                    datos_procedimiento.setCodigo_registro(Utilidades
                            .getValorLong(tbxNro_factura.getValue()));
                    datos_procedimiento = getServiceLocator()
                            .getDatos_procedimientoService().consultar(
                                    datos_procedimiento);
                    if (datos_procedimiento == null) {
                        datos_procedimiento = new Datos_procedimiento();
                        datos_procedimiento.setCodigo_empresa(empresa
                                .getCodigo_empresa());
                        datos_procedimiento.setCodigo_sucursal(sucursal
                                .getCodigo_sucursal());
                        datos_procedimiento.setCodigo_registro(Utilidades
                                .getValorLong(tbxNro_factura.getValue()));
                    }
                    datos_procedimiento
                            .setTipo_identificacion((String) datos_seleccion
                                    .get("tipo_identificacion"));
                    datos_procedimiento.setNro_identificacion(bandboxPaciente
                            .getValue());
                    datos_procedimiento
                            .setCodigo_administradora((String) datos_seleccion
                                    .get("codigo_administradora"));
                    datos_procedimiento.setId_plan((String) datos_seleccion
                            .get("id_plan"));
                    datos_procedimiento.setCodigo_prestador(bandboxPrestador
                            .getValue());
                    datos_procedimiento.setNro_ingreso(admision
                            .getNro_ingreso());
                    datos_procedimiento
                            .setCodigo_procedimiento(codigo_procedimiento);
                    String codigo_cups = tbxCodigo_procedimiento.getValue();
                    datos_procedimiento.setCodigo_cups(codigo_cups);
                    datos_procedimiento.setFecha_procedimiento(new Timestamp(
                            dtbxFecha.getValue().getTime()));
                    datos_procedimiento.setNumero_autorizacion(tbxAutorizacion
                            .getValue());

                    datos_procedimiento
                            .setValor_procedimiento((Double) datos_guardar
                                    .get("valor"));
                    datos_procedimiento.setUnidades((Integer) datos_guardar
                            .get("unidades"));
                    datos_procedimiento.setCopago((Double) datos_guardar
                            .get("copago"));
                    Double valor_total = (Double) datos_guardar.get("valor")
                            * (Integer) datos_guardar.get("unidades");
                    datos_procedimiento.setValor_neto(valor_total);
                    datos_procedimiento
                            .setAmbito_procedimiento((String) datos_guardar
                                    .get("ambito_realizacion"));
                    datos_procedimiento
                            .setFinalidad_procedimiento((String) datos_guardar
                                    .get("finalidad_procedimiento"));
                    datos_procedimiento
                            .setPersonal_atiende((String) datos_guardar
                                    .get("personal_atiende"));
                    datos_procedimiento
                            .setForma_realizacion((String) datos_guardar
                                    .get("forma_realizacion"));
                    datos_procedimiento
                            .setCodigo_diagnostico_principal((String) datos_guardar
                                    .get("diagnostico_principal"));
                    datos_procedimiento
                            .setCodigo_diagnostico_relacionado((String) datos_guardar
                                    .get("diagnostico_relacionado"));
                    datos_procedimiento.setComplicacion((String) datos_guardar
                            .get("complicacion"));
                    datos_procedimiento
                            .setCodigo_programa((String) datos_guardar
                                    .get("programa"));

                    datos_procedimiento.setCancelo_copago("N");

                    datos_procedimiento.setCreacion_date(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    datos_procedimiento.setUltimo_update(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    datos_procedimiento.setCreacion_user(usuarios.getCodigo()
                            .toString());
                    datos_procedimiento.setUltimo_user(usuarios.getCodigo()
                            .toString());
                    datos_procedimiento
                            .setValor_real(datos_seleccion.get("valor_real") != null ? ((Double) datos_seleccion
                                    .get("valor_real")) : 0.0);
                    datos_procedimiento
                            .setDescuento(datos_seleccion.get("descuento") != null ? ((Double) datos_seleccion
                                    .get("descuento")) : 0.0);
                    datos_procedimiento
                            .setIncremento(datos_seleccion.get("incremento") != null ? ((Double) datos_seleccion
                                    .get("incremento")) : 0.0);

                    lista_procedimiento.add(datos_procedimiento);
                    // log.info("=====> lista_procedimiento "
                    // + lista_procedimiento);

                }

                Map<String, Object> datos = new HashMap<String, Object>();
                datos.put("lista_procedimiento", lista_procedimiento);
                datos.put("accion", tbxAccion.getValue());

                // log.info("datos" + datos);
                getServiceLocator().getDatos_procedimientoService().guardar(
                        datos);

                tbxAccion.setValue("modificar");

                Messagebox
                        .show("Los datos se guardaron satisfactoriamente",
                                "Informacion ..", Messagebox.OK,
                                Messagebox.INFORMATION);

            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        borderlayoutEditar.setVisible(sw);
        groupboxConsulta.setVisible(!sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            buscarDatos();
        } else {
            limpiarDatos();
            listboxProcedimientos.getItems().clear();
            lista_procedimientos.clear();
        }
        tbxAccion.setValue(accion);
    }

    public void nuevoRegistro() throws Exception {
        Messagebox
                .show("perderá esta informacion que esta digitando, seguro que va a crear un nuevo registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    accionForm(true, "registrar");
                                }
                            }
                        });
    }

    public void guardarDatos_procedimiento() throws Exception {

        // log.info("tbxAccion.getValue()" + tbxAccion.getValue());
        if (!tbxAccion.getValue().equalsIgnoreCase("registrar")) {
            guardarDatos_editar();
        } else {
            guardarDatos();
        }

    }

    public void guardarDatos_editar() throws Exception {

        // Admision admision = ((Admision)
        // lbxNumeroIngreso.getSelectedItem().getValue());
        Admision admision = ((Admision) lbxNumeroIngreso.getSelectedItem()
                .getValue());

        Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
        datos_procedimiento.setCodigo_empresa(empresa.getCodigo_empresa());
        datos_procedimiento.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        datos_procedimiento.setCodigo_registro(Utilidades
                .getValorLong(tbxNro_factura.getValue()));
        datos_procedimiento = getServiceLocator()
                .getDatos_procedimientoService().consultar(datos_procedimiento);
        if (datos_procedimiento == null) {
            datos_procedimiento = new Datos_procedimiento();
            datos_procedimiento.setCodigo_empresa(empresa.getCodigo_empresa());
            datos_procedimiento.setCodigo_sucursal(sucursal
                    .getCodigo_sucursal());
            datos_procedimiento.setCodigo_registro(Utilidades
                    .getValorLong(tbxNro_factura.getValue()));
        }
        datos_procedimiento.setTipo_identificacion((String) datos_seleccion
                .get("tipo_identificacion"));
        datos_procedimiento.setNro_identificacion(bandboxPaciente.getValue());
        datos_procedimiento.setCodigo_administradora((String) datos_seleccion
                .get("codigo_administradora"));
        datos_procedimiento.setId_plan((String) datos_seleccion.get("id_plan"));
        datos_procedimiento.setCodigo_prestador(bandboxPrestador.getValue());
        datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
        datos_procedimiento.setCodigo_procedimiento(tbxCodigo_procedimiento
                .getValue());
        String codigo_cups = tbxCodigo_procedimiento.getValue();
        datos_procedimiento.setCodigo_cups(codigo_cups);
        datos_procedimiento.setFecha_procedimiento(new Timestamp(dtbxFecha
                .getValue().getTime()));
        datos_procedimiento.setNumero_autorizacion(tbxAutorizacion.getValue());

        datos_procedimiento
                .setValor_procedimiento(dbxValor.getValue() != null ? dbxValor
                        .getValue() : 0.0);
        datos_procedimiento.setUnidades(spinnerUnidades.getValue());
        datos_procedimiento.setCopago(dbxCopago.getValue() != null ? dbxCopago
                .getValue() : 0.0);
        datos_procedimiento
                .setValor_neto(dbxTotalProcedimiento.getValue() != null ? dbxTotalProcedimiento
                        .getValue() : 0.0);
        datos_procedimiento.setAmbito_procedimiento(lbxAmbitoRealizacion
                .getSelectedItem().getValue().toString());
        datos_procedimiento
                .setFinalidad_procedimiento(lbxFinalidadProcedimiento
                        .getSelectedItem().getValue().toString());
        datos_procedimiento.setPersonal_atiende(lbxPersonalAtiende
                .getSelectedItem().getValue().toString());
        datos_procedimiento.setForma_realizacion(lbxFormaRealizacion
                .getSelectedItem().getValue().toString());
        datos_procedimiento
                .setCodigo_diagnostico_principal(bandboxDiagPrincipal
                        .getValue());
        datos_procedimiento
                .setCodigo_diagnostico_relacionado(bandboxDiagRelacionado
                        .getValue());
        datos_procedimiento.setComplicacion(bandboxComplicacion.getValue());
        datos_procedimiento.setCodigo_programa(lbxProgramaPYP.getSelectedItem()
                .getValue().toString());

        datos_procedimiento.setCancelo_copago("N");

        datos_procedimiento.setCreacion_date(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        datos_procedimiento.setUltimo_update(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        datos_procedimiento.setCreacion_user(usuarios.getCodigo().toString());
        datos_procedimiento.setUltimo_user(usuarios.getCodigo().toString());
        datos_procedimiento
                .setValor_real(datos_seleccion.get("valor_real") != null ? ((Double) datos_seleccion
                        .get("valor_real")) : 0.0);
        datos_procedimiento
                .setDescuento(datos_seleccion.get("descuento") != null ? ((Double) datos_seleccion
                        .get("descuento")) : 0.0);
        datos_procedimiento
                .setIncremento(datos_seleccion.get("incremento") != null ? ((Double) datos_seleccion
                        .get("incremento")) : 0.0);

        List<Datos_procedimiento> lista_procedimiento = new ArrayList<Datos_procedimiento>();
        lista_procedimiento.add(datos_procedimiento);

        Map<String, Object> datos = new HashMap<String, Object>();
        datos.put("lista_procedimiento", lista_procedimiento);
        datos.put("accion", tbxAccion.getValue());

        // log.info("datos" + datos);
        getServiceLocator().getDatos_procedimientoService().guardar(datos);

        tbxAccion.setValue("modificar");
        // tbxNro_factura.setValue(datos_consulta.getNro_factura());

        Messagebox.show("Los datos se modificaron satisfactoriamente",
                "Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
    }

    public void editarDatos(Map<String, Object> mapa_datos) throws Exception {

        tbxCodigo_procedimiento.setValue((String) mapa_datos
                .get("codigo_procedimiento"));

        Map<String, Object> bean = Utilidades.getProcedimiento(
                manuales_tarifarios,
                new Long(tbxCodigo_procedimiento.getValue()),
                getServiceLocator());

        tbxNomPcd.setValue((String) bean.get("nombre_procedimiento"));

        dbxValor.setValue((Double) mapa_datos.get("valor"));
        spinnerUnidades.setValue((Integer) mapa_datos.get("unidades"));

        Double valor_total = dbxValor.getValue() * spinnerUnidades.getValue();

        dbxCopago.setValue((Double) mapa_datos.get("copago"));
        dbxTotalProcedimiento.setValue(valor_total);

        for (int i = 0; i < lbxAmbitoRealizacion.getItemCount(); i++) {
            Listitem listitem2 = lbxAmbitoRealizacion.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("ambito_realizacion"))) {
                listitem2.setSelected(true);
                i = lbxAmbitoRealizacion.getItemCount();
            }
        }

        for (int i = 0; i < lbxFinalidadProcedimiento.getItemCount(); i++) {
            Listitem listitem2 = lbxFinalidadProcedimiento.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("finalidad_procedimiento"))) {
                listitem2.setSelected(true);
                i = lbxFinalidadProcedimiento.getItemCount();
            }
        }
        for (int i = 0; i < lbxPersonalAtiende.getItemCount(); i++) {
            Listitem listitem2 = lbxPersonalAtiende.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("personal_atiende"))) {
                listitem2.setSelected(true);
                i = lbxPersonalAtiende.getItemCount();
            }
        }
        for (int i = 0; i < lbxFormaRealizacion.getItemCount(); i++) {
            Listitem listitem2 = lbxFormaRealizacion.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("forma_realizacion"))) {
                listitem2.setSelected(true);
                i = lbxFormaRealizacion.getItemCount();
            }
        }

        Cie cie = new Cie();
        cie.setCodigo((String) mapa_datos.get("diagnostico_principal"));
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
        if (cie != null) {
            bandboxDiagPrincipal.seleccionarRegistro(cie, cie.getCodigo(),
                    cie.getNombre());
            tbxInfoDiagPrincipal.setValue(cie.getNombre());
        } else {
            bandboxDiagPrincipal.setValue("");
            tbxInfoDiagPrincipal.setValue("");
        }

        cie = new Cie();
        cie.setCodigo((String) mapa_datos.get("diagnostico_relacionado"));
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
        if (cie != null) {
            bandboxDiagRelacionado.seleccionarRegistro(cie, cie.getCodigo(),
                    cie.getNombre());
            tbxInfoDiagRelacionado.setValue(cie.getNombre());
        } else {
            bandboxDiagRelacionado.setValue("");
            tbxInfoDiagRelacionado.setValue("");
        }

        cie = new Cie();
        cie.setCodigo((String) mapa_datos.get("complicacion"));
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
        if (cie != null) {
            bandboxComplicacion.seleccionarRegistro(cie, cie.getCodigo(),
                    cie.getNombre());
            tbxInfoComplicacion.setValue(cie.getNombre());
        } else {
            bandboxComplicacion.setValue("");
            tbxInfoComplicacion.setValue("");
        }

        for (int i = 0; i < lbxProgramaPYP.getItemCount(); i++) {
            Listitem listitem2 = lbxProgramaPYP.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("programa"))) {
                listitem2.setSelected(true);
                i = lbxProgramaPYP.getItemCount();
            }
        }

        Res.cargarAutomatica(spinnerUnidades, mapa_datos, "unidades", null);
        Res.cargarAutomatica(dbxCopago, mapa_datos, "copago", null);
        Res.cargarAutomatica(lbxFinalidadProcedimiento, mapa_datos,
                "finalidad_procedimiento", null);
        Res.cargarAutomatica(lbxAmbitoRealizacion, mapa_datos,
                "ambito_realizacion", null);
        Res.cargarAutomatica(lbxPersonalAtiende, mapa_datos,
                "personal_atiende", null);
        Res.cargarAutomatica(lbxFormaRealizacion, mapa_datos,
                "forma_realizacion", null);
        Res.cargarAutomatica(bandboxDiagPrincipal, mapa_datos,
                "diagnostico_principal", null);
        Res.cargarAutomatica(bandboxDiagRelacionado, mapa_datos,
                "diagnostico_relacionado", null);
        Res.cargarAutomatica(bandboxComplicacion, mapa_datos, "complicacion",
                null);
        Res.cargarAutomatica(lbxProgramaPYP, mapa_datos, "programa", null);

    }

}
