package healthmanager.controller;

import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Citas_web;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.ConsultorioService;
import healthmanager.modelo.service.Detalles_horariosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.http.SerializableSession;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;
import healthmanager.modelo.service.GeneralExtraService;

public class Citas_livianaAction extends GeneralComposer implements
        WindowRegistrosIMG {

    // private static Logger log = Logger.getLogger(Citas_webAction.class);
    @WireVariable
    private PrestadoresService prestadoresService;
    @WireVariable
    private ElementoService elementoService;
    @WireVariable
    private Detalles_horariosService detalles_horariosService;
    @WireVariable
    private CitasService citasService;
    @WireVariable
    private ConsultorioService consultorioService;
    @WireVariable
    private GeneralExtraService generalExtraService;

    // private SimpleDateFormat formatoFechaHora = new
    // SimpleDateFormat("hh:mm a",
    // locale);
    private SimpleDateFormat formatoFecha = new SimpleDateFormat(
            "EEEEE dd MMMMM yyyy", IConstantes.locale);

    @View
    private Borderlayout groupboxEditar;
    @View
    private Datebox dtbxFecha;
    @View
    private Listbox lbxVia_ingreso;
    @View
    private Toolbarbutton btnLimpiarPrestador;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_prestador;
    @View
    private Textbox tbxNomPrestador;
    @View
    private Auxheader auxheaderInformacion;
    @View
    private Grid gridResultados;
    @View
    private Rows rowsResultados;
    @View
    private Button btnLunes;
    @View
    private Button btnMartes;
    @View
    private Button btnMiercoles;
    @View
    private Button btnJueves;
    @View
    private Button btnViernes;
    @View
    private Button btnSabado;
    @View
    private Button btnDomingo;

    private Map<String, Consultorio> mapa_consultorios = new HashMap<String, Consultorio>();

    private Map<String, Prestadores> mapa_prestadores = new HashMap<String, Prestadores>();

    // @View private Listbox lbxPlan;
    private final String[] idsExcluyentes = new String[]{"tbxAccion", "btNew"};

    private Date fecha_current;

    @Override
    public void init() throws Exception {
        listarCombos();
        parametrizarBandbox();
        limpiarDatos();
    }

    private void parametrizarBandbox() {
        parametrizarBandboxPrestador();

    }

    private void parametrizarBandboxPrestador() {
        tbxCodigo_prestador.inicializar(tbxNomPrestador, btnLimpiarPrestador);
        tbxCodigo_prestador
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Map<String, Object> registro) {

                        // Extraemos valores
                        String nro_identificacion = (String) registro
                        .get("nro_identificacion");
                        String nombres = (String) registro.get("nombres");
                        String apellidos = (String) registro.get("apellidos");
                        Integer citas_del_dia = (Integer) registro
                        .get("citas_del_dia");
                        Integer citas_asignadas = (Integer) registro
                        .get("citas_asignadas");
                        Integer citas_pendientes = (Integer) registro
                        .get("citas_pendientes");
                        String tipo_prestador = (String) registro
                        .get("tipo_prestador");

                        // Mostramos valores en vista
                        Listcell listcell = new Listcell();
                        listcell.appendChild(new Label(nro_identificacion));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(nombres + " "
                                        + apellidos));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(tipo_prestador
                                        .equals("01") ? "MEDICO" : "ENFERMERA"));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(
                                        citas_del_dia != null ? citas_del_dia + ""
                                        : "0"));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(
                                        citas_asignadas != null ? citas_asignadas + ""
                                        : "0"));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(
                                        citas_pendientes != null ? citas_pendientes
                                        + "" : "0"));
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
                                parametros.put("fecha_solicitada", new Timestamp(
                                                dtbxFecha.getValue().getTime()));
                                parametros
                                .put("codigo_centro_dh",
                                        centro_atencion_session != null ? centro_atencion_session
                                        .getCodigo_centro()
                                        : IConstantes.CENTRO_ATENCION_CUALQUIERA);
                                parametros.put("codigo_programa_excepcion",
                                        lbxVia_ingreso.getSelectedItem().getValue()
                                        .toString());
                                parametros.put("rol_programa", getRolViaIngreso());

                                parametros.put("limite_paginado", "limit 25 offset 0");

                                return prestadoresService
                                .listarPrestadoresPorRolCentro(parametros);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion,
                                    Map<String, Object> registro) {
                                String nro_identificacion = (String) registro
                                .get("nro_identificacion");
                                String nombre = (registro.get("nombres") + " " + registro
                                .get("apellidos"));

                                bandbox.setValue(nro_identificacion);
                                textboxInformacion.setValue(nombre);

                                if (registro != null) {
                                    tbxCodigo_prestador.seleccionarRegistro(registro,
                                            tbxCodigo_prestador.getValue(),
                                            tbxNomPrestador.getValue());
                                }

                                tbxNomPrestador.setHflex("0");
                                tbxNomPrestador.setWidth("210px");

                                // onChangeFechaSolicitud();
                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {
                                tbxNomPrestador.setHflex("1");
                                tbxNomPrestador.setWidth(null);

                                tbxCodigo_prestador.seleccionarRegistro(null,
                                        tbxCodigo_prestador.getValue(),
                                        tbxNomPrestador.getValue());
                                // onChangeFechaSolicitud();
                            }

                });
    }

    private List<Via_ingreso_rol> getRolViaIngreso() {
        if (lbxVia_ingreso.getSelectedItem() != null) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("codigo_via_ingreso", lbxVia_ingreso.getSelectedItem()
                    .getValue().toString());
            param.put("codigo_empresa", codigo_empresa);
            return generalExtraService.listar(Via_ingreso_rol.class, param);
        } else {
            return null;
        }
    }

    @Override
    public void params(Map<String, Object> map) {

    }

    public void buscarDatos() {
        Session session = Executions.getCurrent().getSession();

        for (String key : session.getAttributes().keySet()) {
            log.info(key + " ===> " + session.getAttribute(key));
        }

        SerializableSession serializableSession = (SerializableSession) session
                .getAttribute("javax.zkoss.zk.ui.Session");

        if (serializableSession != null) {

            log.info("---------------- Serializable -----------------");

            for (String key : serializableSession.getAttributes().keySet()) {
                log.info(key + " ===> " + serializableSession.getAttribute(key));
            }
        }

        String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
                .toString();
        if (!via_ingreso.isEmpty() && dtbxFecha.getValue() != null) {
            this.fecha_current = dtbxFecha.getValue();
            actualizarBotonesDias();
            cargarInformacionDia(false, 0);
        }

        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();
        HttpSession shttp = request.getSession();
        Enumeration<?> enumeration = shttp.getAttributeNames();
        log.info("ID ===> " + shttp.getId());
        while (enumeration.hasMoreElements()) {
            Object elemento = enumeration.nextElement();
            log.info(elemento.toString() + " ===> "
                    + shttp.getAttribute(elemento.toString()));
        }
    }

    public void cargarInformacionDia(boolean desde_botones, int dia) {
        rowsResultados.getChildren().clear();
        if (fecha_current != null) {
            if (!isApoyoDiagnostico()) {
                List<Via_ingreso_rol> codigo_rols = getRolViaIngreso();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(fecha_current);
                if (desde_botones) {
                    int dia_semana = gregorianCalendar
                            .get(Calendar.DAY_OF_WEEK) - 2;
                    gregorianCalendar
                            .set(Calendar.DAY_OF_MONTH,
                                    (gregorianCalendar
                                    .get(Calendar.DAY_OF_MONTH) - dia_semana)
                                    + dia);
                }

                Map<String, Object> parameters = new HashMap();
                parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
                parameters
                        .put("codigo_sucursal", sucursal.getCodigo_sucursal());

                if (!codigo_rols.isEmpty()) {
                    parameters.put("codigo_rols", codigo_rols);
                }

                Map<String, Object> prestadores_map = tbxCodigo_prestador
                        .getRegistroSeleccionado();

                String codigo_centro = centro_atencion_session
                        .getCodigo_centro();
                parameters.put("codigo_centro", codigo_centro);

                if (prestadores_map != null) {
                    parameters.put("codigo_medico",
                            (String) prestadores_map.get("nro_identificacion"));
                }

                parameters.put("fecha_solicitada", gregorianCalendar.getTime());
                parameters.put("fecha_final_busqueda",
                        gregorianCalendar.getTime());

                List<Detalles_horarios> listado_detalles_horarios = detalles_horariosService
                        .listar_registros(parameters);

                log.info("listado_detalles_horarios ===> "
                        + listado_detalles_horarios.size());

                int contatdor = 1;
                Row fila_detalles = new Row();
                rowsResultados.appendChild(fila_detalles);
                for (Detalles_horarios detalles_horarios : listado_detalles_horarios) {
                    if (contatdor == 5) {
                        contatdor = 1;
                        fila_detalles = new Row();
                        rowsResultados.appendChild(fila_detalles);
                    }
                    Citas citas;
                    if (true) {
                        Map<String, Object> mapa_citas = new HashMap<String, Object>();
                        mapa_citas.put("codigo_empresa",
                                detalles_horarios.getCodigo_empresa());
                        mapa_citas.put("codigo_sucursal",
                                detalles_horarios.getCodigo_sucursal());
                        mapa_citas.put("codigo_prestador",
                                detalles_horarios.getCodigo_medico());
                        mapa_citas.put("codigo_detalle_horario",
                                detalles_horarios.getConsecutivo());
                        mapa_citas.put("estado_act", "-");

                        citas = citasService.consultar_citas(mapa_citas);
                    }

                    final Map<String, Object> mapa_evento = new HashMap<String, Object>();
                    mapa_evento.put("detalles_horarios", detalles_horarios);
                    mapa_evento.put("citas", citas);

                    Prestadores prestadores = null;

                    if (mapa_prestadores.containsKey(detalles_horarios
                            .getCodigo_medico())) {
                        prestadores = mapa_prestadores.get(detalles_horarios
                                .getCodigo_medico());
                    } else {
                        prestadores = new Prestadores();
                        prestadores.setCodigo_empresa(codigo_empresa);
                        prestadores.setCodigo_sucursal(codigo_sucursal);
                        prestadores.setNro_identificacion(detalles_horarios
                                .getCodigo_medico());
                        prestadores = prestadoresService.consultar(prestadores);
                        mapa_prestadores.put(
                                detalles_horarios.getCodigo_medico(),
                                prestadores);
                    }

                    Consultorio consultorio = null;

                    if (mapa_consultorios.containsKey(detalles_horarios
                            .getCodigo_consultorio())) {
                        consultorio = mapa_consultorios.get(detalles_horarios
                                .getCodigo_consultorio());
                    } else {
                        consultorio = new Consultorio();
                        consultorio.setCodigo_empresa(codigo_empresa);
                        consultorio.setCodigo_sucursal(codigo_sucursal);
                        consultorio.setCodigo_consultorio(detalles_horarios
                                .getCodigo_consultorio());
                        consultorio = consultorioService.consultar(consultorio);
                        mapa_consultorios.put(
                                detalles_horarios.getCodigo_consultorio(),
                                consultorio);
                    }
                    fila_detalles.appendChild(getCeldaCita(citas,
                            detalles_horarios, prestadores, consultorio));
                    contatdor++;
                }

            }

        }
    }

    public void actualizarBotonesDias() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(dtbxFecha.getValue());
        auxheaderInformacion.setLabel("Informacion de citas "
                + formatoFecha.format(gregorianCalendar.getTime()));
        int dia_semana = gregorianCalendar.get(Calendar.DAY_OF_WEEK) - 2;
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,
                (gregorianCalendar.get(Calendar.DAY_OF_MONTH) - dia_semana));
        btnLunes.setLabel("Lunes "
                + formatFecha.format(gregorianCalendar.getTime()));
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,
                (gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        btnMartes.setLabel("Martes "
                + formatFecha.format(gregorianCalendar.getTime()));
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,
                (gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        btnMiercoles.setLabel("Miercoles "
                + formatFecha.format(gregorianCalendar.getTime()));
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,
                (gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        btnJueves.setLabel("Jueves "
                + formatFecha.format(gregorianCalendar.getTime()));
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,
                (gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        btnViernes.setLabel("Viernes "
                + formatFecha.format(gregorianCalendar.getTime()));
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,
                (gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        btnSabado.setLabel("Sabado "
                + formatFecha.format(gregorianCalendar.getTime()));
        gregorianCalendar.set(Calendar.DAY_OF_MONTH,
                (gregorianCalendar.get(Calendar.DAY_OF_MONTH) + 1));
        btnDomingo.setLabel("Domingo "
                + formatFecha.format(gregorianCalendar.getTime()));

    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() {
        FormularioUtil.limpiarComponentes(this, idsExcluyentes);

    }

    public void listarAnios() {
        String[] anios = Res.getAnnos(7);
        for (String anio : anios) {
            Listitem listitem = new Listitem();
            listitem.setValue(anio);
            listitem.setLabel("" + anio);
        }
    }

    private Cell getCeldaCita(Citas citas, Detalles_horarios detalles_horarios,
            Prestadores prestadores, Consultorio consultorio) {
        String estado = "";
        // String color = "";
        // String codigo_estado = "";
        // String background_color = "";

        if (citas == null) {
            estado = "Libre";
            // color = "black";
            // codigo_estado = "0";
        } else if (citas.getEstado().equals("1")) {
            estado = "Asignada";
            // color = "blue";
            // codigo_estado = "1";
        } else if (citas.getEstado().equals("2")) {
            estado = "Cumplida";
            // color = "green";
            // codigo_estado = "2";
        } else if (citas.getEstado().equals("5")) {
            estado = "Cancelada";
            // color = "red";
            // codigo_estado = "5";
        }
        Cell celda_cita = new Cell();
        Vlayout vlayout = new Vlayout();
        vlayout.setStyle("margin-left:10px");
        vlayout.appendChild(new Label(formatFecha.format(detalles_horarios
                .getFecha_inicial())));
        vlayout.appendChild(new Label((formatHora.format(detalles_horarios
                .getFecha_inicial()))
                + " - "
                + formatHora.format(detalles_horarios.getFecha_final())));
        vlayout.appendChild(new Label(estado));
        vlayout.appendChild(new Label(prestadores != null ? (prestadores
                .getNombres() + " " + prestadores.getApellidos()) : ""));
        vlayout.appendChild(new Label(consultorio != null ? (consultorio
                .getNombre()) : ""));
        vlayout.setStyle("border-style:solid;border-width:2px;border-color:black");
        celda_cita.appendChild(vlayout);
        return celda_cita;
    }

    public void listarCombos() {
        listarAnios();
        Utilidades.listarElementoListboxOrdenado(lbxVia_ingreso, true,
                elementoService);
        ocultarListitem(lbxVia_ingreso, IVias_ingreso.URGENCIA);
        ocultarListitem(lbxVia_ingreso, IVias_ingreso.HOSPITALIZACIONES);
        ocultarListitem(lbxVia_ingreso, IVias_ingreso.ELECTROENCEFALOGRAMA);
        ocultarListitem(lbxVia_ingreso, IVias_ingreso.ENDOSCOPIA);
        ocultarListitem(lbxVia_ingreso, IVias_ingreso.TAC);
        ocultarListitem(lbxVia_ingreso, IVias_ingreso.RESONANCIA_MAGNETICA);
        ocultarListitem(lbxVia_ingreso, IVias_ingreso.CONTROL_LEPRA);
    }

    private void ocultarListitem(Listbox listbox, String via_ingreso) {
        Listitem listitem = getListitem(listbox, via_ingreso);
        if (listitem != null) {
            listitem.setVisible(false);
        }
    }

    public Listitem getListitem(Listbox listbox, String item) {
        for (Listitem listitem : listbox.getItems()) {
            if (listitem.getValue() != null && listitem.getValue().equals(item)) {
                return listitem;
            }
        }
        return null;
    }

    private boolean isApoyoDiagnostico() {
        String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue();
        return ServiciosDisponiblesUtils.isApoyoDiagnostico(via_ingreso);
    }

    public boolean validarForm() throws Exception {

        boolean valida = true;
        String msj = "Los campos marcados con (*) son obligatorios";

        // if (valida) {
        // if (lbxTipo_consulta.getSelectedItem().getValue().toString()
        // .equals("4")
        // && lbxArea_intervencion.getSelectedIndex() == 0) {
        // msj = "Para consultas pyp debe el area";
        // lbxArea_intervencion.setStyle("background-color:#F6BBBE");
        // valida = false;
        // }
        // }
        //
        // if (valida) {
        // if (lbxTipo_consulta.getSelectedItem().getValue().toString()
        // .equals("2")
        // && lbxAyuda_dx.getSelectedIndex() == 0) {
        // msj = "Para ayudas diagnosticas seleccione el area";
        // lbxAyuda_dx.setStyle("background-color:#F6BBBE");
        // valida = false;
        // }
        // }
        if (!valida) {
            Messagebox
                    .show(msj, paciente_session.getNombreCompleto()
                            + " recuerde que...", Messagebox.OK,
                            Messagebox.EXCLAMATION);
        }

        return valida;
    }

    public String verificarRolMedico() {
        String rol_medico = "05";

        return rol_medico;

    }

    public void imprimir(String codigo_cita) throws Exception {
        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "RecordatorioCitas");
        paramRequest.put("codigo_cita", codigo_cita + "");

        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", this, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);
    }

    @Override
    public void onSeleccionarRegistro(Object registro) {

    }

    @Override
    public Object[] celdasListitem(Object registro) {
        Citas_web citas = (Citas_web) registro;

        String fecha_cita = new SimpleDateFormat("yyyy-MM-dd hh:mm aa")
                .format(citas.getFecha_cita());

        Elemento elementoEstado = citas.getElementoEstado();

        Paciente paciente = citas.getPaciente();

        return new Object[]{citas.getCodigo_cita(),
            citas.getNro_identificacion(),
            (paciente != null ? paciente.getNombreCompleto() : ""),
            fecha_cita,
            (elementoEstado != null ? elementoEstado.getDescripcion() : "")};
    }

    public void actualizarDetalles() {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("codigo_empresa", codigo_empresa);
        parametros.put("codigo_sucursal", codigo_sucursal);
        parametros.put("tiene_cita_null", "tiene_cita_null");
        parametros.put("fecha_inicio", new Date());
        List<Detalles_horarios> listado = detalles_horariosService
                .listar_normal(parametros);
        Map<String, Object> mapa_citas;
        Citas citas;
        for (int i = 0; i < listado.size(); i++) {
            Detalles_horarios detalles_horarios = listado.get(i);
            mapa_citas = new HashMap<String, Object>();
            mapa_citas.put("codigo_empresa",
                    detalles_horarios.getCodigo_empresa());
            mapa_citas.put("codigo_sucursal",
                    detalles_horarios.getCodigo_sucursal());
            mapa_citas.put("codigo_prestador",
                    detalles_horarios.getCodigo_medico());
            mapa_citas.put("codigo_detalle_horario",
                    detalles_horarios.getConsecutivo());
            mapa_citas.put("estado_act", "-");
            citas = citasService.consultar_citas(mapa_citas);
            log.info("Procesado " + (i + 1) + " de " + listado.size());
        }
    }

}
