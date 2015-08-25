package healthmanager.controller;

import healthmanager.controller.Facturacion_ripsAction.IOnGuardar;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Epicrisis_ese;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Urgencias;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Epicrisis_eseService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Hisc_urgenciaService;

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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.interfaces.OnGuardar;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class UrgenciasAction extends ZKWindow {

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxPaciente;
    @View
    private Textbox tbxNro_factura;
    @View
    private Textbox tbxInfoPaciente;
    @View
    private Toolbarbutton btnLimpiarPaciente;
    @View
    private Textbox tbxNacimiento;
    @View
    private Textbox tbxEdad;
    @View
    private Textbox tbxSexo;
    @View
    private Textbox tbxEstrato;
    @View
    private Textbox tbxTipoAfiliado;

    @View
    private Listbox lbxNumeroIngreso;
    @View
    private Textbox tbxInfoAseguradora;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxPrestador;
    @View
    private Textbox tbxInfoPrestador;
    @View
    private Toolbarbutton btnLimpiarPrestador;

    @View
    private Textbox tbxAutorizacion;
    @View
    private Listbox lbxCausaExterna;
    @View
    private Datebox dtbxFechaIngreso;

    @View
    private Listbox lbxDestinoSalida;
    @View
    private Listbox lbxEstadoSalida;
    @View
    private Datebox dtbxFechaEgreso;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxDiagPrincipal;
    @View
    private Textbox tbxInfoDiagPrincipal;
    @View
    private Toolbarbutton btnLimpiarDiagPrincipal;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxDiagR1;
    @View
    private Textbox tbxInfoDiagR1;
    @View
    private Toolbarbutton btnLimpiarDiagR1;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxDiagR2;
    @View
    private Textbox tbxInfoDiagR2;
    @View
    private Toolbarbutton btnLimpiarDiagR2;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxDiagR3;
    @View
    private Textbox tbxInfoDiagR3;
    @View
    private Toolbarbutton btnLimpiarDiagR3;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxCausaMuerte;
    @View
    private Textbox tbxInfoCausaMuerte;
    @View
    private Toolbarbutton btnLimpiarCausaMuerte;
    @View
    private Groupbox groupboxEditar;
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

    private OnGuardar onGuardar;

    private Map<String, Object> datos_seleccion = new HashMap<String, Object>();

    private IOnGuardar ionGuardar;

    private Boolean confirmacion;

    private String mensaje;

    private Map<String, Object> parametrosAction;

    private final String[] idsExcluyentes = new String[]{"bandboxPaciente",
        "tbxInfoPaciente", "btnLimpiarPaciente", "btnLimpiarPrestador",
        "lbxCausaExterna", "lbxDestinoSalida", "btnLimpiarDiagRelacionado",
        "btnLimpiarDiagR1", "lbxEstadoSalida", "lbxNumeroIngreso",
        "btnLimpiarDiagR2", "btnLimpiarDiagR3", "btnLimpiarCausaMuerte",
        "tbxAutorizacion", "bandboxCausaMuerte"};

    @Override
    public void init() throws Exception {
        bandboxPaciente.inicializar(tbxInfoPaciente, btnLimpiarPaciente);
        bandboxPrestador.inicializar(tbxInfoPrestador, btnLimpiarPrestador);
        bandboxDiagPrincipal.inicializar(tbxInfoDiagPrincipal,
                btnLimpiarDiagPrincipal);
        bandboxDiagR1.inicializar(tbxInfoDiagR1, btnLimpiarDiagR1);
        bandboxDiagR2.inicializar(tbxInfoDiagR2, btnLimpiarDiagR2);
        bandboxDiagR3.inicializar(tbxInfoDiagR3, btnLimpiarDiagR3);
        bandboxCausaMuerte.inicializar(tbxInfoCausaMuerte,
                btnLimpiarCausaMuerte);

        parametrizarBandbox();
        listarCombos();
        deshabilitarCampos(true);
        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();
        String modulo_mostrar = request.getParameter("modulo_mostrar");

        boolean ocultarConsulta = false;
        String nro_ingreso = "";
        String nro_identificacion = "";

        if (parametrosAction != null) {
            if (parametrosAction.isEmpty()) {
                parametrosAction = null;
            }
        }
        if (parametrosAction != null) {
            ionGuardar = (IOnGuardar) parametrosAction.get("IOnGuardar");
            confirmacion = (Boolean) parametrosAction.get("confirmacion");
            mensaje = (String) parametrosAction.get("mensaje");

            ocultarConsulta = (Boolean) parametrosAction.get("ocultarConsulta");
            nro_ingreso = (String) parametrosAction.get("nro_ingreso");
            nro_identificacion = (String) parametrosAction
                    .get("nro_identificacion");
        }

        if (parametrosAction != null) {
            cargarDatosModuloFactura(nro_ingreso, nro_identificacion,
                    ocultarConsulta);
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

    @Override
    public void params(Map<String, Object> map) {
        parametrosAction = map;
    }

    private void cargarDatosModuloFactura(String nro_ingreso,
            String nro_identificacion, boolean ocultarConsulta)
            throws Exception {
        log.info("Ejecutando metodo @cargarDatosModuloFactura");
        accionForm(true, "Registrar");
        final String nro_identificacion_aux = nro_identificacion;
        bandboxPaciente.setDisabled(true);
        btnLimpiarPaciente.setVisible(false);
        lbxNumeroIngreso.setDisabled(true);
        ocultarBotoConsultar(ocultarConsulta);

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(codigo_empresa);
        paciente.setCodigo_sucursal(codigo_sucursal);
        paciente.setNro_identificacion(nro_identificacion);
        paciente = getServiceLocator().getPacienteService().consultar(paciente);

        log.info("paciente ===> " + (paciente != null));
        if (paciente != null) {
            bandboxPaciente.seleccionarRegistro(paciente,
                    paciente.getNro_identificacion(),
                    paciente.getNombreCompleto());
            datos_seleccion.put("sexo", paciente.getSexo());
            datos_seleccion.put("tipo_identificacion",
                    paciente.getTipo_identificacion());
            datos_seleccion.put("fecha_nac", new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()));

            tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()), "1",
                    false));
            tbxSexo.setValue(Utilidades.getNombreElemento(paciente.getSexo(),
                    "sexo", UrgenciasAction.this));
            tbxEstrato.setValue(paciente.getEstrato());
            tbxTipoAfiliado.setValue(Utilidades.getNombreElemento(
                    paciente.getTipo_afiliado(), "tipo_afiliado",
                    UrgenciasAction.this));
            tbxNacimiento.setValue(new java.text.SimpleDateFormat("dd/MM/yyyy")
                    .format(paciente.getFecha_nacimiento()));

            Admision aux2 = new Admision();
            aux2.setCodigo_empresa(codigo_empresa);
            aux2.setCodigo_sucursal(codigo_sucursal);
            aux2.setNro_identificacion(nro_identificacion_aux);
            aux2.setNro_ingreso(nro_ingreso);
            aux2 = getServiceLocator().getServicio(AdmisionService.class)
                    .consultar(aux2);

            if (aux2 != null) {
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
                Utilidades.setValueFrom(lbxCausaExterna,
                        aux2.getCausa_externa());
            }
        }
    }

    public void ocultarBotoConsultar(boolean sw) {
        if (!sw) {
            ((Button) groupboxEditar.getFellow("btCancel")).setVisible(true);
            ((Button) groupboxEditar.getFellow("btNew")).setVisible(true);
        } else {
            ((Button) groupboxEditar.getFellow("btCancel")).setVisible(false);
            ((Button) groupboxEditar.getFellow("btNew")).setVisible(false);
        }
    }

    private void parametrizarBandbox() {
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

                        return UrgenciasAction.this.getServiceLocator()
                        .getPacienteService().listar(parametros);
                    }

                    @Override
                    public boolean seleccionarRegistro(Bandbox bandbox,
                            Textbox textboxInformacion, Paciente registro) {
                        final Urgencias urgencia = new Urgencias();
                        urgencia.setCodigo_empresa(registro.getCodigo_empresa());
                        urgencia.setCodigo_sucursal(registro
                                .getCodigo_sucursal());
                        urgencia.setNro_identificacion(registro
                                .getNro_identificacion());

                        List<Admision> listaAdmisiones = listarAdmisiones(
                                urgencia, false);
                        List<Admision> listaAdmisionesReal = new ArrayList<Admision>();

                        for (Admision admision : listaAdmisiones) {
                            Urgencias u = new Urgencias();
                            u.setCodigo_empresa(admision.getCodigo_empresa());
                            u.setCodigo_sucursal(admision.getCodigo_sucursal());
                            u.setNro_identificacion(admision
                                    .getNro_identificacion());
                            u.setNro_ingreso(admision.getNro_ingreso());
                            u = getServiceLocator().getUrgenciasService()
                            .consultar(u);

                            if (admision.getUrgencias().equals("S")
                            && u == null) {
                                listaAdmisionesReal.add(admision);
                            }
                        }

                        // En caso de no encontrar admisiones validas
                        if (listaAdmisionesReal.isEmpty()) {
                            Messagebox
                            .show("No se ha registrado el ingreso del paciente para su atencion en urgencias",
                                    "Paciente no admisionado",
                                    Messagebox.OK,
                                    Messagebox.EXCLAMATION);
                            limpiarDatos();
                            deshabilitarCampos(true);
                            return false;
                        } else {
                            listarIngresos(lbxNumeroIngreso, listaAdmisiones,
                                    false);
                            Admision aux2 = listaAdmisionesReal.get(0);
                            urgencia.setNro_ingreso(aux2.getNro_ingreso());
                            final Urgencias urg = getServiceLocator()
                            .getUrgenciasService().consultar(urgencia);
                            if (urg != null) {
								// Si encuentra una admision valida y una
                                // urgencia con los datos seleccionados
                                return msgExistencia(urg);
                            } else {
								// Si encuentra una admision valida y sin
                                // urgencias con los datos seleccionados
                                Elemento tipoAfiliado = new Elemento();
                                tipoAfiliado.setCodigo(registro
                                        .getTipo_afiliado());
                                tipoAfiliado.setTipo("tipo_afiliado");
                                tipoAfiliado = getServiceLocator()
                                .getElementoService().consultar(
                                        tipoAfiliado);

                                bandbox.setValue(registro
                                        .getNro_identificacion());
                                textboxInformacion.setValue(registro
                                        .getNombreCompleto());
                                // TODO: colocar detalles
                                tbxEdad.setValue(Util.getEdad(
                                                new java.text.SimpleDateFormat(
                                                        "dd/MM/yyyy").format(registro
                                                        .getFecha_nacimiento()), "1",
                                                false));
                                tbxSexo.setValue(registro.getSexo());
                                tbxEstrato.setValue(registro.getEstrato());
                                tbxTipoAfiliado.setValue(tipoAfiliado
                                        .getDescripcion());
                                tbxNacimiento
                                .setValue(new java.text.SimpleDateFormat(
                                                "dd/MM/yyyy").format(registro
                                                .getFecha_nacimiento()));

                                datos_seleccion.put("sexo", registro.getSexo());
                                datos_seleccion.put("tipo_identificacion",
                                        registro.getTipo_identificacion());
                                datos_seleccion.put("fecha_nac",
                                        new java.text.SimpleDateFormat(
                                                "dd/MM/yyyy").format(registro
                                                .getFecha_nacimiento()));

                                for (Admision admision : listaAdmisiones) {
                                    if (admision.getUrgencias().equals("S")) {
                                        for (int i = 0; i < lbxNumeroIngreso
                                        .getItemCount(); i++) {
                                            if (lbxNumeroIngreso
                                            .getItemAtIndex(i)
                                            .getValue()
                                            .equals(admision)) {
                                                lbxNumeroIngreso
                                                .setSelectedIndex(i);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                datos_seleccion.put("list_item_admision",
                                        lbxNumeroIngreso.getSelectedItem());
                                deshabilitarCampos(false);
                                cargarAdmisiones(aux2);
                                return true;
                            }
                        }
                    }

                    @Override
                    public void limpiarSeleccion(Bandbox bandbox) {
                        limpiarDatos();
                        datos_seleccion.remove("sexo");
                        datos_seleccion.remove("fecha_nac");
                        datos_seleccion.remove("tipo_identificacion");
                        deshabilitarCampos(true);
                    }
                });

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
                                return false;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {
                            }

                });

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
                    } else if (bandbox.equals(bandboxDiagR1)) {
                        datos_seleccion.put("cie_diagnostico_relacionado1",
                                registro);
                    } else if (bandbox.equals(bandboxDiagR2)) {
                        datos_seleccion.put("cie_diagnostico_relacionado2",
                                registro);
                    } else if (bandbox.equals(bandboxDiagR3)) {
                        datos_seleccion.put("cie_diagnostico_relacionado3",
                                registro);
                    } else if (bandbox.equals(bandboxCausaMuerte)) {
                        datos_seleccion.put("cie_causa_muerte", registro);
                    }

                } catch (Exception e) {
                    MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
                }
                return false;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
                if (bandbox.equals(bandboxDiagPrincipal)) {
                    // log.info("limpiar bandboxDiagPrincipal");
                    datos_seleccion.remove("cie_diagnostico_principal");
                } else if (bandbox.equals(bandboxDiagR1)) {
                    // log.info("limpiar bandboxDiagRelacionado1");
                    datos_seleccion.remove("cie_diagnostico_relacionado1");
                } else if (bandbox.equals(bandboxDiagR2)) {
                    // log.info("limpiar bandboxDiagRelacionado2");
                    datos_seleccion.remove("cie_diagnostico_relacionado2");
                } else if (bandbox.equals(bandboxDiagR3)) {
                    // log.info("limpiar bandboxDiagRelacionado3");
                    datos_seleccion.remove("cie_diagnostico_relacionado3");
                } else if (bandbox.equals(bandboxCausaMuerte)) {
                    // log.info("limpiar bandboxCausaMuerte");
                    datos_seleccion.remove("cie_causa_muerte");
                }
            }

        };

        bandboxDiagPrincipal.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxDiagR1.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxDiagR2.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxDiagR3.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxCausaMuerte.setBandboxRegistrosIMG(bandboxRegistrosIMG);
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
            parameters.put("limite_paginado", "limit 25 offset 0");

            List<Urgencias> urgencias = getServiceLocator()
                    .getUrgenciasService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Urgencias urgencia : urgencias) {
                rowsResultado.appendChild(crearFilas(urgencia, this));
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

        final Urgencias urgencia = (Urgencias) objeto;

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(urgencia.getCodigo_empresa());
        paciente.setCodigo_sucursal(urgencia.getCodigo_sucursal());
        paciente.setNro_identificacion(urgencia.getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);
        String nombres_paciente = (paciente != null ? paciente.getNombre1()
                + " " + paciente.getNombre2() : "");
        String apellidos_paciente = (paciente != null ? paciente.getApellido1()
                + " " + paciente.getApellido2() : "");

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(urgencia.getNro_factura() + ""));
        fila.appendChild(new Label(urgencia.getTipo_identificacion() + ""));
        fila.appendChild(new Label(urgencia.getNro_identificacion() + ""));
        fila.appendChild(new Label(urgencia.getNro_ingreso() + ""));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                .format(urgencia.getFecha_ingreso()) + ""));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                .format(urgencia.getFecha_egreso()) + ""));
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
                mostrarDatos(urgencia);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        img.setSrc("/images/borrar.gif");
        img.setTooltiptext("Eliminar");
        img.setStyle("cursor: pointer");
        String estado_admision = Utilidades.getEstado_admision(
                urgencia.getNro_ingreso(), urgencia.getNro_identificacion(),
                this);
        if (estado_admision.equals("2")) {
            img.setVisible(false);
        }
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
                                    eliminarDatos(urgencia);
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

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Urgencias urgencia = (Urgencias) obj;
        try {
            deshabilitarCampos(false);
            tbxNro_factura.setValue(urgencia.getNro_factura());
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(urgencia.getCodigo_empresa());
            paciente.setCodigo_sucursal(urgencia.getCodigo_sucursal());
            paciente.setNro_identificacion(urgencia.getNro_identificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);
            bandboxPaciente.seleccionarRegistro(paciente, urgencia
                    .getNro_identificacion(),
                    (paciente != null ? paciente.getNombreCompleto() : ""));
            datos_seleccion
                    .put("tipo_identificacion",
                            (paciente != null ? paciente
                            .getTipo_identificacion() : ""));
            datos_seleccion.put("sexo", (paciente != null ? paciente.getSexo()
                    : ""));
            datos_seleccion.put(
                    "fecha_nac",
                    (paciente != null ? new SimpleDateFormat("dd/MM/yyyy")
                    .format(paciente.getFecha_nacimiento()) : ""));

            // TODO: colocar detalles
            tbxSexo.setValue(paciente.getSexo());
            tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()), "1",
                    false));

            tbxTipoAfiliado.setValue(Utilidades.getNombreElemento(
                    paciente.getTipo_afiliado(), "tipo_afiliado",
                    UrgenciasAction.this));
            tbxNacimiento.setValue(new java.text.SimpleDateFormat("dd/MM/yyyy")
                    .format(paciente.getFecha_nacimiento()));
            tbxEstrato.setValue(paciente.getEstrato());

            datos_seleccion.put("codigo_administradora",
                    urgencia.getCodigo_administradora());
            datos_seleccion.put("id_plan", urgencia.getId_plan());

            Administradora administradora = new Administradora();
            administradora.setCodigo_empresa(urgencia.getCodigo_empresa());
            administradora.setCodigo_sucursal(urgencia.getCodigo_sucursal());
            administradora.setCodigo(urgencia.getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(urgencia.getCodigo_empresa());
            prestadores.setCodigo_sucursal(urgencia.getCodigo_sucursal());
            prestadores.setNro_identificacion(urgencia.getCodigo_prestador());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);

            bandboxPrestador.seleccionarRegistro(prestadores, urgencia
                    .getCodigo_prestador(),
                    (prestadores != null ? prestadores.getNombres() + " "
                    + prestadores.getApellidos() : ""));

            listarIngresos(lbxNumeroIngreso, listarAdmisiones(urgencia, true),
                    false);
            Utilidades.seleccionarListitem(lbxNumeroIngreso,
                    urgencia.getNro_ingreso());

            dtbxFechaEgreso.setValue(urgencia.getFecha_egreso());
            dtbxFechaIngreso.setValue(urgencia.getFecha_ingreso());

            tbxAutorizacion.setValue(urgencia.getNumero_autorizacion());
            Utilidades.seleccionarListitem(lbxCausaExterna,
                    urgencia.getCausa_externa());
            Utilidades.seleccionarListitem(lbxEstadoSalida,
                    urgencia.getEstado_salida());
            Utilidades.seleccionarListitem(lbxDestinoSalida,
                    urgencia.getDestino_salida());

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(urgencia.getCodigo_empresa());
            contratos.setCodigo_sucursal(urgencia.getCodigo_sucursal());
            contratos.setCodigo_administradora(urgencia
                    .getCodigo_administradora());
            contratos.setId_plan(urgencia.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(
                    contratos);
            tbxInfoAseguradora
                    .setValue((administradora != null ? administradora
                            .getNombre() : "")
                            + " - "
                            + (contratos != null ? contratos.getNombre() : ""));
            // Cie-> diagnostico principal
            Cie cie = new Cie();
            cie.setCodigo(urgencia.getCodigo_diagnostico_principal());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxDiagPrincipal.seleccionarRegistro(cie, urgencia
                    .getCodigo_diagnostico_principal(),
                    (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_diagnostico_principal", cie);

            // Cie-> diagnostico relacionado 1
            cie = new Cie();
            cie.setCodigo(urgencia.getCodigo_diagnostico1());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxDiagR1.seleccionarRegistro(cie,
                    urgencia.getCodigo_diagnostico1(),
                    (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_diagnostico_relacionado1", cie);

            // Cie-> diagnostico relacionado 2
            cie = new Cie();
            cie.setCodigo(urgencia.getCodigo_diagnostico2());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxDiagR2.seleccionarRegistro(cie,
                    urgencia.getCodigo_diagnostico2(),
                    (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_diagnostico_relacionado2", cie);

            // Cie-> diagnostico relacionado 3
            cie = new Cie();
            cie.setCodigo(urgencia.getCodigo_diagnostico3());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxDiagR3.seleccionarRegistro(cie,
                    urgencia.getCodigo_diagnostico3(),
                    (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_diagnostico_relacionado3", cie);

            // Cie-> diagnostico relacionado 4
            cie = new Cie();
            cie.setCodigo(urgencia.getCausa_muerte());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            bandboxCausaMuerte.seleccionarRegistro(cie, urgencia
                    .getCausa_muerte(), (cie != null ? cie.getNombre() : ""));

            datos_seleccion.put("cie_causa_muerte", cie);

            btnLimpiarPaciente.setVisible(false);
            bandboxPaciente.setDisabled(true);

			// validarRegistroEditar(urgencia);
            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

	// private void validarRegistroEditar(Urgencias urgencia) throws Exception {
    // String estado_admision = getEstado_admision(urgencia);
    // if (this.getParent() instanceof Facturacion_ripsAction) {
    // estado_admision = "1";
    // }
    // if (estado_admision.equals("2")) {
    // bloqueoBotonGuardar();
    // } else {
    // bloqueoBotonGuardar();
    // }
    // }
    private void bloqueoBotonGuardar() {
        ((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(false);
    }

    public void eliminarDatos(Object obj) throws Exception {
        Urgencias urgencia = (Urgencias) obj;
        try {
            int result = getServiceLocator().getUrgenciasService().eliminar(
                    urgencia);
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
                            "Este objeto no se puede eliminar porque esta relacionado con otra tabla en la base de datos",
                            this);
        } catch (RuntimeException r) {
            MensajesUtil.mensajeError(r, "", this);
        }
    }

    public void listarCombos() {
        listarParameter();
        Utilidades.listarElementoListbox(lbxNumeroIngreso, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxCausaExterna, true,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxDestinoSalida, true,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxEstadoSalida, true,
                getServiceLocator());
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("t2.nro_identificacion");
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
        listitem.setValue("nro_factura");
        listitem.setLabel("Nro registro");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("fecha_consulta");
        listitem.setLabel("Fecha(aaaa-mm-dd)");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    public void cargarAdmisiones(Admision aux2) {
        if (aux2 != null) {
            deshabilitarCampos(false);
            Historia_clinica hc = new Historia_clinica();
            hc.setCodigo_empresa(aux2.getCodigo_empresa());
            hc.setCodigo_sucursal(aux2.getCodigo_sucursal());
            hc.setNro_ingreso(aux2.getNro_ingreso());
            hc.setNro_identificacion(aux2.getNro_identificacion());
            hc = this.getServiceLocator()
                    .getServicio(GeneralExtraService.class).consultar(hc);

            if (hc != null) {
                Hisc_urgencia historia_clinica = new Hisc_urgencia();
                historia_clinica.setCodigo_empresa(hc.getCodigo_empresa());
                historia_clinica.setCodigo_sucursal(hc.getCodigo_sucursal());
                historia_clinica.setCodigo_historia(hc.getCodigo_historia());
                historia_clinica = this.getServiceLocator()
                        .getServicio(Hisc_urgenciaService.class)
                        .consultar(historia_clinica);
                if (historia_clinica != null) {
                    try {
                        Epicrisis_ese epicrisis_ese = new Epicrisis_ese();
                        epicrisis_ese.setCodigo_empresa(empresa
                                .getCodigo_empresa());
                        epicrisis_ese.setCodigo_sucursal(sucursal
                                .getCodigo_sucursal());
                        epicrisis_ese.setNro_ingreso(aux2.getNro_ingreso());
                        epicrisis_ese.setCodigo_historia(historia_clinica
                                .getCodigo_historia());
                        epicrisis_ese.setIdentificacion(aux2
                                .getNro_identificacion());
                        epicrisis_ese = getServiceLocator().getServicio(
                                Epicrisis_eseService.class).consultar(
                                        epicrisis_ese);
                        // log.info("====> epicrisis " + epicrisis_ese);

                        if (epicrisis_ese != null) {
                            if (epicrisis_ese.getDestino().equals("001")
                                    || epicrisis_ese.getDestino().equals("005")) {
                                lbxDestinoSalida.setSelectedIndex(1);
                            } else if (epicrisis_ese.getDestino().equals("002")
                                    || epicrisis_ese.getDestino().equals("003")) {
                                lbxDestinoSalida.setSelectedIndex(3);
                            } else if (epicrisis_ese.getDestino().equals("004")) {
                                lbxDestinoSalida.setSelectedIndex(2);
                            }

							// Utilidades.seleccionarListitem(lbxEstadoSalida,
                            // );
                            if (epicrisis_ese.getEstado_salida().equals("2")) {
                                lbxEstadoSalida.setSelectedIndex(2);
                            } else {
                                lbxEstadoSalida.setSelectedIndex(1);
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }

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

            String administradora = "";
            administradora = (admin != null ? admin.getNombre() : "");
            administradora += " - ";

            bandboxPrestador.seleccionarRegistro(prestadores,
                    aux2.getCodigo_medico(),
                    (prestadores != null ? prestadores.getNombres() + " "
                    + prestadores.getApellidos() : ""));

            bandboxDiagPrincipal.seleccionarRegistro(cie,
                    aux2.getDiagnostico_ingreso(),
                    (cie != null ? cie.getNombre() : ""));

            tbxInfoAseguradora.setValue(administradora);
            datos_seleccion.put("codigo_administradora",
                    aux2.getCodigo_administradora());
            datos_seleccion.put("id_plan", aux2.getId_plan());

            // En esta parte sacamos las fecha de atencion del paciente
            Timestamp fecha_atencion = aux2.getFecha_atencion() != null ? aux2
                    .getFecha_atencion() : aux2.getFecha_ingreso();
            dtbxFechaIngreso.setValue(fecha_atencion);
            tbxAutorizacion.setValue(aux2.getNro_autorizacion());
        }
    }

    public List<Admision> listarAdmisiones(Urgencias urgencia, boolean sw) {
        Map paramAdmision = new HashMap();
        paramAdmision.put("codigo_empresa", urgencia.getCodigo_empresa());
        paramAdmision.put("codigo_sucursal", urgencia.getCodigo_sucursal());
        paramAdmision.put("nro_identificacion",
                urgencia.getNro_identificacion());
        paramAdmision.put("order", "fecha_ingreso desc");

        if (sw) {
            paramAdmision.put("nro_ingreso", urgencia.getNro_ingreso());
        } else {
            // paramAdmision.put("urgencias", "S");
            paramAdmision.put("estado", "1");
        }

        List<Admision> listaAdmisiones = getServiceLocator()
                .getAdmisionService().listarTabla(paramAdmision);

        return listaAdmisiones;
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        bandboxPaciente.setValue("");
        bandboxPaciente.setDisabled(false);
        deshabilitarCampos(true);
    }

    public void deshabilitarCampos(boolean sw) {
        FormularioUtil.deshabilitarComponentes(groupboxEditar, sw,
                idsExcluyentes);
        bloqueoBotonGuardar();
        if (sw) {
            listarIngresos(lbxNumeroIngreso, new LinkedList<Admision>(), true);
        }
    }

    public void listarIngresos(Listbox listbox, List<Admision> listaAdmisiones,
            boolean select) {
        listbox.getItems().clear();
        Listitem listitem;
        if (select) {
            listitem = new Listitem();
            listitem.setValue(null);
            listitem.setLabel("-- --");
            listbox.appendChild(listitem);
        }

        for (Admision a : listaAdmisiones) {
            Administradora admin = new Administradora();
            admin.setCodigo_empresa(a.getCodigo_empresa());
            admin.setCodigo_sucursal(a.getCodigo_sucursal());
            admin.setCodigo(a.getCodigo_administradora());
            admin = getServiceLocator().getAdministradoraService().consultar(
                    admin);

            listitem = new Listitem();
            listitem.setValue(a);
            listitem.setLabel(a.getNro_ingreso() + " -- "
                    + (admin != null ? admin.getNombre() : ""));
            listbox.appendChild(listitem);
        }
        if (listbox.getItemCount() > 0) {
            listbox.setSelectedIndex(0);
        }

        if (!listaAdmisiones.isEmpty()) {
            Utilidades.setValueFrom(lbxCausaExterna, listaAdmisiones.get(0)
                    .getCausa_externa());
        }
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        bandboxPaciente
                .setStyle("text-transform:uppercase;background-color:white");
        bandboxPrestador
                .setStyle("text-transform:uppercase;background-color:white");
        bandboxDiagPrincipal
                .setStyle("text-transform:uppercase;background-color:white");
        bandboxCausaMuerte
                .setStyle("text-transform:uppercase;background-color:white");

        Admision admision = ((Admision) lbxNumeroIngreso.getSelectedItem()
                .getValue());

        boolean valida = true;

        String mensaje = "Los campos marcados con (*) son obligatorios";

        if (bandboxPaciente.getText().trim().equals("")) {
            bandboxPaciente
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (bandboxPrestador.getText().trim().equals("")) {
            bandboxPrestador
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (admision == null) {
            lbxNumeroIngreso.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (bandboxDiagPrincipal.getText().trim().equals("")) {
            bandboxDiagPrincipal
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (valida) {
            if (lbxEstadoSalida.getSelectedItem().getValue().equals("2")
                    && bandboxCausaMuerte.getText().trim().equals("")) {
                bandboxCausaMuerte
                        .setStyle("text-transform:uppercase;background-color:#F6BBBE");
                mensaje = "Usted selecciono el estado de la salida como muerto(a) \npor lo tanto debe seleccionar la causa de muerte";
                valida = false;
            }
        }

        if (valida) {
            if (!lbxEstadoSalida.getSelectedItem().getValue().equals("2")
                    && !bandboxCausaMuerte.getText().trim().equals("")) {
                lbxEstadoSalida.setStyle("background-color:#F6BBBE");
                mensaje = "Usted selecciono una casua de muerte \npor lo tanto debe seleccionar el estado a la salida como muerto (a)";
                valida = false;
            }
        }

        if (lbxCausaExterna.getSelectedIndex() == 0) {
            lbxCausaExterna
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (lbxDestinoSalida.getSelectedIndex() == 0) {
            lbxDestinoSalida
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (lbxEstadoSalida.getSelectedIndex() == 0) {
            lbxEstadoSalida
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (!valida) {
            Messagebox.show(mensaje,
                    usuarios.getNombres() + " recuerde que...", Messagebox.OK,
                    Messagebox.EXCLAMATION);
        }

        return valida;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                // Cargamos los componentes //
                Urgencias urgencia = getUrgencia();
                Admision admision = (Admision) lbxNumeroIngreso
                        .getSelectedItem().getValue();
                String complemento = "";
                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    getServiceLocator().getUrgenciasService().crearUrgencia(
                            urgencia, admision, getServiceLocator());
                } else {
                    int result = getServiceLocator()
                            .getUrgenciasService()
                            .actualizar(urgencia, admision, getServiceLocator());
                    if (result == 0) {
                        throw new Exception(
                                "Lo sentimos pero los datos a modificar no se encuentran en base de datos");
                    }
                }

                if (ionGuardar != null) {
                    if (confirmacion != null && confirmacion) {
                        Messagebox.show("" + mensaje, usuarios.getNombres()
                                + " recuerde que...", Messagebox.YES
                                + Messagebox.NO, Messagebox.QUESTION,
                                new org.zkoss.zk.ui.event.EventListener() {
                                    public void onEvent(Event event)
                                    throws Exception {
                                        if ("onYes".equals(event.getName())) {
                                            ionGuardar.guardar();
                                        }
                                    }
                                });
                    } else {
                        ionGuardar.guardar();
                    }
                } else {
                    Messagebox.show(
                            "Los datos se guardaron satisfactoriamente."
                            + complemento, "Informacion? ..",
                            Messagebox.OK, Messagebox.INFORMATION);
                    tbxNro_factura.setValue(urgencia.getNro_factura());
                    tbxAccion.setValue("modificar");
                }

                if (this.getParent() instanceof Facturacion_ripsAction) {
                    ((Facturacion_ripsAction) this.getParent())
                            .refrescarInformacion(false);
                    this.detach();
                }
            }
        } catch (ValidacionRunTimeException e) {
            MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    private Urgencias getUrgencia() {
        Admision admision = ((Admision) lbxNumeroIngreso.getSelectedItem()
                .getValue());

        Urgencias urgencia = new Urgencias();
        urgencia.setCodigo_empresa(empresa.getCodigo_empresa());
        urgencia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        urgencia.setNro_factura(tbxNro_factura.getValue());
        urgencia = getServiceLocator().getUrgenciasService()
                .consultar(urgencia);
        if (urgencia == null) {
            urgencia = new Urgencias();
            urgencia.setCodigo_empresa(empresa.getCodigo_empresa());
            urgencia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            urgencia.setNro_factura(tbxNro_factura.getValue());
        }

        urgencia.setNro_identificacion(bandboxPaciente.getValue());
        urgencia.setTipo_identificacion((String) datos_seleccion
                .get("tipo_identificacion"));
        urgencia.setCodigo_administradora((String) datos_seleccion
                .get("codigo_administradora"));
        urgencia.setId_plan((String) datos_seleccion.get("id_plan"));
        urgencia.setCodigo_prestador(bandboxPrestador.getValue());
        urgencia.setNro_ingreso(admision.getNro_ingreso());
        urgencia.setFecha_ingreso(new Timestamp(dtbxFechaIngreso.getValue()
                .getTime()));
        urgencia.setFecha_egreso(new Timestamp(dtbxFechaEgreso.getValue()
                .getTime()));

        urgencia.setCausa_externa((String) lbxCausaExterna.getSelectedItem()
                .getValue());
        urgencia.setDestino_salida((String) lbxDestinoSalida.getSelectedItem()
                .getValue());
        urgencia.setEstado_salida((String) lbxEstadoSalida.getSelectedItem()
                .getValue());

        urgencia.setNumero_autorizacion(tbxAutorizacion.getValue());

        urgencia.setCodigo_diagnostico_principal(bandboxDiagPrincipal
                .getValue());
        urgencia.setCodigo_diagnostico1(bandboxDiagR1.getValue());
        urgencia.setCodigo_diagnostico2(bandboxDiagR2.getValue());
        urgencia.setCodigo_diagnostico3(bandboxDiagR3.getValue());
        urgencia.setCausa_muerte(bandboxCausaMuerte.getValue());

        urgencia.setCreacion_date(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        urgencia.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        urgencia.setCreacion_user(usuarios.getCodigo().toString());
        urgencia.setUltimo_user(usuarios.getCodigo().toString());
        return urgencia;
    }

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        borderlayoutEditar.setVisible(sw);
        groupboxConsulta.setVisible(!sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            buscarDatos();
        } else {
            limpiarDatos();
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

    public void selectedAdmision(Admision admision) {
        if (admision == null) {
            datos_seleccion.remove("list_item_admision");
            limpiarDatos();
            deshabilitarCampos(true);
        } else {
            Urgencias urgencia = new Urgencias();

            urgencia.setCodigo_empresa(admision.getCodigo_empresa());
            urgencia.setCodigo_sucursal(admision.getCodigo_sucursal());
            urgencia.setNro_identificacion(admision.getNro_identificacion());
            urgencia.setNro_ingreso(admision.getNro_ingreso());
            final Urgencias urg = getServiceLocator().getUrgenciasService()
                    .consultar(urgencia);

            if (admision.getUrgencias().equals("S") && urg != null) {
                msgExistencia(urg);
            } else if (admision.getUrgencias().equals("N")) {
                Messagebox
                        .show("No se ha registrado el Ingreso del Paciente por urgencias",
                                "Paciente no admisionado", Messagebox.OK,
                                Messagebox.EXCLAMATION);
                Listitem listitem = (Listitem) datos_seleccion
                        .get("list_item_admision");
                lbxNumeroIngreso.setSelectedItem(listitem);
                Admision admisionTemp = listitem.getValue();
                if (admisionTemp != null) {
                    Utilidades.setValueFrom(lbxCausaExterna,
                            admisionTemp.getCausa_externa());
                }
                return;
            } else {
                datos_seleccion.put("list_item_admision",
                        lbxNumeroIngreso.getSelectedItem());
                cargarAdmisiones(admision);
                Utilidades.setValueFrom(lbxCausaExterna,
                        admision.getCausa_externa());
            }
        }
    }

    public void selectedAdmision(Listitem listitem) throws Exception {
        Admision admision = ((Admision) listitem.getValue());
        selectedAdmision(admision);
    }

    public boolean msgExistencia(final Urgencias urg) {
        Messagebox
                .show("Ya existe un registro de esta urgencia, ¿Desea modificar los datos?",
                        "Urgencia ya registrada", Messagebox.YES
                        + Messagebox.NO, Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    mostrarDatos(urg);
                                }
                            }
                        });
        return true;
    }

    public OnGuardar getOnGuardar() {
        return onGuardar;
    }

    public void setOnGuardar(OnGuardar onGuardar) {
        this.onGuardar = onGuardar;
    }
}
