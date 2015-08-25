/**
 *
 */
package healthmanager.controller;

import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.Detalles_horariosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * @author ferney
 *
 */
public class Consultar_citasAction extends ZKWindow {

    private static Logger log = Logger.getLogger(Consultar_citasAction.class);
    private static final long serialVersionUID = -9145887024839938515L;

    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm a", IConstantes.locale);
//	private SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEEE dd MMMMM yyyy", locale);

    @View
    private Groupbox groupboxConsultar;
    @View
    private Datebox dtbxFecha_cita_final;
    @View
    private Listbox listboxCitas_medicas;

    @View
    private Datebox dtbxFecha_cita_b;

    @View(isMacro = true)
    private BandboxRegistrosMacro bandboxPrestador;

    @View
    private Textbox tbxNomPrestador;

    @View
    private Toolbarbutton btnLimpiarPrestador;

    @View
    private Listbox lbxVia_ingreso;

    @View
    private Listbox listboxEstados;

//	@View
//	private Auxheader auxheaderFecha_cita;
    private Map<String, Prestadores> mapa_prestadores = new HashMap<String, Prestadores>();

    private Map<String, String> vias;
    private Map<String, String> estados_cita;
    private List<Elemento> listado_tipo_citas;
    private List<Elemento> listado_vias;

    private List<Citas> listado_citas;
    private Prestadores prestador;

    public void listarCombos() {
        listarTiposCitas();
        listarEstados();
    }

    private void listarTiposCitas() {
        lbxVia_ingreso.getChildren().clear();
        lbxVia_ingreso.appendItem("Todas", "");
        boolean pyp = false;
        Listitem listitem = null;
        for (Elemento elemento : listado_vias) {
            if (!Utilidades.igualConjuncion(elemento.getCodigo(),
                    IVias_ingreso.URGENCIA, IVias_ingreso.HOSPITALIZACIONES,
                    IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO,
                    IVias_ingreso.URGENCIA_ODONTOLOGICO,
                    IVias_ingreso.HC_AIEPI_2_MESES,
                    IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS,
                    IVias_ingreso.REMITIDO)) {

                Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
                configuracion_admision_ingreso
                        .setCodigo_empresa(codigo_empresa);
                configuracion_admision_ingreso
                        .setCodigo_sucursal(codigo_sucursal);
                configuracion_admision_ingreso.setVia_ingreso(elemento
                        .getCodigo());

                configuracion_admision_ingreso = getServiceLocator()
                        .getServicio(
                                Configuracion_admision_ingresoService.class)
                        .consultar(configuracion_admision_ingreso);

                if (configuracion_admision_ingreso == null) {
                    /*
                     throw new ValidacionRunTimeException(
                     "La via de ingreso "
                     + elemento.getDescripcion()
                     + " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
                     */
                } else {
                    if (configuracion_admision_ingreso.getEs_pyp()) {
                        if (!pyp) {
                            pyp = true;
                            listitem = new Listitem();
                            listitem.setValue("PYP");
                            listitem.setLabel("PYP");
                            lbxVia_ingreso.appendChild(listitem);
                        }
                    } else {
                        listitem = new Listitem();
                        listitem.setValue(elemento.getCodigo());
                        listitem.setLabel(elemento.getDescripcion());
                        lbxVia_ingreso.appendChild(listitem);
                    }
                }
            }
        }
        lbxVia_ingreso.setSelectedIndex(0);
    }

    @Override
    public void params(Map<String, Object> map) {

    }

    public void cargarPrestador() throws Exception {

    }

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {

    }

    // Convertimos todos las valores de textbox a mayusculas
    public void setUpperCase() {

    }

    @Override
    public void init() throws Exception {
        inicializarMapas();
        listarCombos();
        parametrizarBandboxPrestador();
    }

    private void inicializarMapas() {
        listado_vias = getServiceLocator().getServicio(
                ElementoService.class).listar("via_ingreso");
        vias = new HashMap<String, String>();
        for (Elemento elemento : listado_vias) {
            vias.put(elemento.getCodigo(), elemento.getDescripcion());
        }

        listado_tipo_citas = getServiceLocator().getServicio(ElementoService.class).listar("estado_cita");
        estados_cita = new HashMap<String, String>();
        for (Elemento elemento : listado_tipo_citas) {
            estados_cita.put(elemento.getCodigo(), elemento.getDescripcion());
        }
    }

    private void parametrizarBandboxPrestador() {
        //log.info("@parametrizarbandbox prestador");
        bandboxPrestador.inicializar(tbxNomPrestador, btnLimpiarPrestador);
        bandboxPrestador
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Map<String, Object> registro) {

                        // Extraemos valores
                        String nro_identificacion = (String) registro.get("nro_identificacion");
                        String nombres = (String) registro.get("nombres");
                        String apellidos = (String) registro.get("apellidos");
                        Integer citas_del_dia = (Integer) registro.get("citas_del_dia");
                        Integer citas_asignadas = (Integer) registro.get("citas_asignadas");
                        Integer citas_pendientes = (Integer) registro.get("citas_pendientes");
                        String tipo_prestador = (String) registro.get("tipo_prestador");

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
                                        .equals("01") ? "MÉDICO" : "ENFERMERA"));
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
                                parametros.put("value", "%" + valorBusqueda.toUpperCase().trim() + "%");
                                parametros.put("codigo_empresa", sucursal.getCodigo_empresa());
                                parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());

                                parametros.put("codigo_centro_dh", centro_atencion_session != null ? centro_atencion_session.getCodigo_centro() : IConstantes.CENTRO_ATENCION_CUALQUIERA);

                                parametros.put("limite_paginado", "limit 25 offset 0");
                                return getServiceLocator().getPrestadoresService().listarPrestadoresPorRolCentro(parametros);
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

                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {

                            }

                });
    }

    public void buscarDatos() {
        String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue();
        if (!ServiciosDisponiblesUtils.isApoyoDiagnostico(via_ingreso)) {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("codigo_empresa", codigo_empresa);
            parametros.put("codigo_sucursal", codigo_sucursal);
            parametros.put("codigo_centro", centro_atencion_session.getCodigo_centro());

            if (dtbxFecha_cita_b.getValue() != null && dtbxFecha_cita_final.getValue() != null) {
                if (dtbxFecha_cita_b.getValue().compareTo(dtbxFecha_cita_final.getValue()) > 0) {
                    throw new ValidacionRunTimeException("La fecha inicial en la busqueda no puede ser mayor a la fecha final");
                }
                parametros.put("rango", "rango");
                parametros.put("fecha_inicial",
                        Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(dtbxFecha_cita_b.getValue())));
                parametros.put("fecha_final",
                        Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(dtbxFecha_cita_final.getValue())));

            } else if (dtbxFecha_cita_b.getValue() != null) {
                parametros.put("fecha_inicio", Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(dtbxFecha_cita_b.getValue())));
            } else if (dtbxFecha_cita_final.getValue() != null) {
                parametros.put("fecha_final", Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(dtbxFecha_cita_final.getValue())));
            }

            if (!via_ingreso.equals("")) {
                List<Via_ingreso_rol> codigo_rols = new ArrayList<Via_ingreso_rol>();
                if (via_ingreso.equals("PYP")) {
                    codigo_rols.add(new Via_ingreso_rol(IRoles.MEDICO_PYP, "", codigo_empresa));
                } else {
                    codigo_rols = getRolViaIngreso();
                }
                if (!codigo_rols.isEmpty()) {
                    List<String> vias_ingreso = new ArrayList<String>();
                    for (Via_ingreso_rol via_ingreso_rol : codigo_rols) {
                        vias_ingreso.add(via_ingreso_rol.getCodigo_via_ingreso());
                    }
                    parametros.put("vias_ingreso", vias_ingreso);
                }

            }

            if (bandboxPrestador.getValue() != null) {
                parametros.put("codigo_prestador_unico", bandboxPrestador.getValue());
                prestador = new Prestadores();
                prestador.setCodigo_empresa(codigo_empresa);
                prestador.setCodigo_sucursal(codigo_sucursal);
                prestador.setNro_identificacion(bandboxPrestador.getValue());
                prestador = getServiceLocator().getServicio(PrestadoresService.class).consultar(prestador);
            }

            List<String> listado_estado = new ArrayList<String>();
            for (Listitem listitem : listboxEstados.getItems()) {
                if (listitem.isSelected()) {
                    listado_estado.add(listitem.getValue().toString());
                }
            }

            // Cuando se valla a filtrar por algun estado
            if (!listado_estado.isEmpty()) {
                parametros.put("estados", listado_estado);
            }

            listado_citas = getServiceLocator().getServicio(CitasService.class).listar(parametros);

            listboxCitas_medicas.getItems().clear();
            for (Citas cita : listado_citas) {
                listboxCitas_medicas.appendChild(crearFila(cita));
            }
        }
    }

    private List<Via_ingreso_rol> getRolViaIngreso() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("codigo_via_ingreso", lbxVia_ingreso.getSelectedItem()
                .getValue().toString());
        param.put("codigo_empresa", codigo_empresa);
        return getServiceLocator().getServicio(GeneralExtraService.class)
                .listar(Via_ingreso_rol.class, param);
    }

    public Listitem crearFila(final Citas citas) {
        final Listitem listitem = new Listitem();

        String estado = "";
        String color = "";
        String background = "";

        estado = estados_cita.get(citas.getEstado());
        if (citas.getEstado().equals("1")) {
            color = "blue";
            //background = ";background-color:gray";
        } else if (citas.getEstado().equals("2")) {
            color = "green";
        } else if (citas.getEstado().equals("5")) {
            color = "red";
        } else if (citas.getEstado().equals("6")) {
            color = "violet";
        }

        listitem.setValue(citas);

        Listcell listcell = new Listcell();
        listcell.setLabel(estado);
        listcell.setStyle("color:" + color + "; font-weight:bold" + background);
        listitem.appendChild(listcell);

        listcell = new Listcell(formatoFechaHora.format(citas
                .getFecha_cita()));
        listcell.setStyle("color:" + color + "; font-weight:bold" + background);
        listitem.appendChild(listcell);

        Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(citas.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		
        Textbox textbox = new Textbox(
                paciente != null ? paciente.getNro_identificacion() + "-"
                + paciente.getNombreCompleto() : "---");
        textbox.setHflex("1");
        textbox.setReadonly(true);

        listcell = new Listcell();
        listcell.appendChild(textbox);
        textbox.setStyle("color:" + color + "; font-weight:bold" + background);
        listcell.setStyle("color:" + color + "; font-weight:bold" + background);
        listitem.appendChild(listcell);

        String via = citas != null ? vias.get(citas.getTipo_consulta()) : "---";
        textbox = new Textbox(via);

        textbox.setReadonly(true);
        textbox.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(textbox);
        textbox.setStyle("color:" + color + "; font-weight:bold" + background);
        listcell.setStyle("color:" + color + "; font-weight:bold" + background);
        listitem.appendChild(listcell);

        Prestadores prestadores = null;

        if (mapa_prestadores.containsKey(citas.getCodigo_prestador())) {
            prestadores = mapa_prestadores.get(citas.getCodigo_prestador());
        } else {
            prestadores = new Prestadores();
            prestadores.setCodigo_empresa(codigo_empresa);
            prestadores.setCodigo_sucursal(codigo_sucursal);
            prestadores.setNro_identificacion(citas.getCodigo_prestador());
            prestadores = getServiceLocator().getPrestadoresService().consultar(prestadores);
            mapa_prestadores.put(citas.getCodigo_prestador(), prestadores);
        }

        textbox = new Textbox(prestadores != null ? prestadores.getNombres()
                + " " + prestadores.getApellidos()
                : citas.getCodigo_prestador());
        textbox.setReadonly(true);
        textbox.setHflex("1");

        listcell = new Listcell();
        listcell.appendChild(textbox);
        textbox.setStyle("color:" + color + "; font-weight:bold" + background);
        listcell.setStyle("color:" + color + "; font-weight:bold" + background);
        listitem.appendChild(listcell);

        listitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

            @Override
            public void onEvent(Event arg0) throws Exception {
                verCitaApartada(citas);
            }

        });

        return listitem;
    }

    public void verCitaApartada(final Citas cita) {
        final Window window = (Window) Executions.createComponents(
                "/pages/cita_apartada.zul", this, null);
        final Textbox tbxCodigo_cita = (Textbox) window
                .getFellow("tbxCodigo_cita");
        tbxCodigo_cita.setValue(cita.getCodigo_cita());
        final Textbox tbxIdentificacion = (Textbox) window
                .getFellow("tbxIdentificacion");
        tbxIdentificacion.setValue(cita.getNro_identificacion());

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(codigo_empresa);
        paciente.setCodigo_sucursal(codigo_sucursal);
        paciente.setNro_identificacion(cita.getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);
        if (paciente != null) {
            Administradora administradora = new Administradora();
            administradora.setCodigo_empresa(paciente.getCodigo_empresa());
            administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
            administradora.setCodigo(paciente.getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            final Textbox tbxNomPaciente = (Textbox) window
                    .getFellow("tbxNomPaciente");
            tbxNomPaciente.setValue(paciente.getNombreCompleto());

            final Textbox tbxCodigoAseguradora = (Textbox) window
                    .getFellow("tbxCodigoAseguradora");
            tbxCodigoAseguradora.setValue(paciente.getCodigo_administradora());

            final Textbox tbxInfoAseguradora = (Textbox) window
                    .getFellow("tbxInfoAseguradora");
            tbxInfoAseguradora
                    .setValue((administradora != null ? administradora
                            .getNombre() : ""));

            Adicional_paciente adicional_paciente = new Adicional_paciente();
            adicional_paciente.setCodigo_empresa(codigo_empresa);
            adicional_paciente.setCodigo_sucursal(codigo_sucursal);
            adicional_paciente.setNro_identificacion(paciente
                    .getNro_identificacion());
            adicional_paciente = getServiceLocator().getServicio(
                    GeneralExtraService.class).consultar(
                            adicional_paciente);

            if (adicional_paciente != null) {
                Barrio barrio = new Barrio();
                barrio.setCodigo_barrio(adicional_paciente.getCodigo_barrio());
                barrio = getServiceLocator().getBarrioService().consultar(
                        barrio);

                Centro_barrio centro_barrio = new Centro_barrio();
                centro_barrio.setCodigo_barrio(barrio != null ? barrio
                        .getCodigo_barrio() : "");
                centro_barrio = getServiceLocator().getServicio(GeneralExtraService.class)
                        .consultar(centro_barrio);

                Centro_atencion centro_atencion = new Centro_atencion();
                centro_atencion.setCodigo_empresa(paciente.getCodigo_empresa());
                centro_atencion.setCodigo_sucursal(paciente
                        .getCodigo_sucursal());
                centro_atencion
                        .setCodigo_centro((centro_barrio != null ? centro_barrio
                                .getCodigo_centro() : ""));
                centro_atencion = getServiceLocator()
                        .getCentro_atencionService().consultar(centro_atencion);

                final Textbox tbxCentro_atencion = (Textbox) window
                        .getFellow("tbxCentro_atencion");
                tbxCentro_atencion
                        .setValue((centro_atencion != null ? centro_atencion
                                .getNombre_centro() : ""));
            }
            final Textbox tbxSexo = (Textbox) window.getFellow("tbxSexo");
            tbxSexo.setValue((paciente != null ? Utilidades.getNombreElemento(
                    paciente.getSexo(), "sexo", this) : ""));

            final Textbox tbxEdad = (Textbox) window.getFellow("tbxEdad");
            tbxEdad.setValue((paciente != null ? Util.getEdad(
                    new java.text.SimpleDateFormat("dd/MM/yyyy")
                    .format(paciente.getFecha_nacimiento()), "1", true)
                    : ""));

            final Textbox tbxConsultorio = (Textbox) window
                    .getFellow("tbxConsultorio");

            Detalles_horarios dh = new Detalles_horarios();
            dh.setCodigo_empresa(cita.getCodigo_empresa());
            dh.setCodigo_sucursal(cita.getCodigo_sucursal());
            dh.setConsecutivo(cita.getCodigo_detalle_horario());
            dh = getServiceLocator().getServicio(Detalles_horariosService.class).consultar(dh);
            if (dh != null) {
                Consultorio consultorio = new Consultorio();
                consultorio.setCodigo_empresa(codigo_empresa);
                consultorio.setCodigo_sucursal(codigo_sucursal);
                consultorio.setCodigo_consultorio(dh
                        .getCodigo_consultorio());
                consultorio = getServiceLocator().getConsultorioService()
                        .consultar(consultorio);
                tbxConsultorio.setValue(consultorio.getNombre());
            } else {
                tbxConsultorio.setValue("");
            }

            final Textbox tbxHora = (Textbox) window.getFellow("tbxHora");
            tbxHora.setValue(cita.getHora());

            Prestadores prestador = new Prestadores();
            prestador.setCodigo_empresa(codigo_empresa);
            prestador.setCodigo_sucursal(codigo_sucursal);
            prestador.setNro_identificacion(cita.getCodigo_prestador());
            prestador = getServiceLocator().getPrestadoresService().consultar(prestador);

            final Textbox tbxPrestador = (Textbox) window
                    .getFellow("tbxPrestador");
            tbxPrestador.setValue(prestador.getNombres() + " "
                    + prestador.getApellidos());

            final Textbox tbxViaingreso = (Textbox) window
                    .getFellow("tbxViaingreso");
            tbxViaingreso.setValue(vias.get(cita.getTipo_consulta()));

            Toolbarbutton toolbarbutton = (Toolbarbutton) window
                    .getFellow("btnImprimir");

            toolbarbutton.addEventListener(Events.ON_CLICK,
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            imprimir(cita.getCodigo_cita());
                            window.onClose();
                        }
                    });

            Toolbarbutton btnReemplazar = (Toolbarbutton) window
                    .getFellow("btnReemplazar");
            btnReemplazar.setVisible(false);

            window.setTitle("Cita " + estados_cita.get(cita.getEstado()).toLowerCase());
            window.setMode("modal");
        }
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

    public void listarEstados() {
        listboxEstados.getItems().clear();

        for (Elemento tipo : listado_tipo_citas) {
            Listitem listitem = new Listitem();
            listitem.appendChild(new Listcell());
            listitem.appendChild(new Listcell(tipo.getDescripcion()));
            listitem.setValue(tipo.getCodigo());
            //listitem.setSelected(true);
            listboxEstados.appendChild(listitem);
        }
        if (listboxEstados.getItemCount() > 0) {
            listboxEstados.setSelectedIndex(0);
        }
    }

    public void guardarDatos() {
        try {

        } catch (ImpresionDiagnosticaException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            if (!(e instanceof WrongValueException)) {
                MensajesUtil.mensajeError(e, "", this);
            } else {
                log.error(((WrongValueException) e).getComponent().getId()
                        + " esta presentando error", e);
            }
        }
    }

    public void imprimir() {
//		"Listado de citas de "
//		+ formatoFecha.format(dtbxFecha_cita_b.getValue())
//		+ " hasta "
//		+ formatoFecha.format(dtbxFecha_cita_final.getValue())

        if (listado_citas != null && !listado_citas.isEmpty()) {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("nombre_reporte", "CITAS: " + prestador.getNombres() + " " + prestador.getApellidos() + "\n" + new SimpleDateFormat("dd/MM/yyyy").format(dtbxFecha_cita_b.getValue()) + " - " + new SimpleDateFormat("dd/MM/yyyy").format(dtbxFecha_cita_b.getValue()));
            parametros.put("formato", "pdf");
            parametros.put("listado_citas", listado_citas);
            parametros.put("name_report", "Listado_citas");

            Component componente = Executions.createComponents(
                    "/pages/printInformes.zul", this, parametros);
            final Window window = (Window) componente;
            window.setMode("modal");
            window.setMaximizable(true);
            window.setMaximized(true);
        } else {
            Messagebox.show("No existen datos para imprimir este reporte",
                    "Sin datos", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

}
