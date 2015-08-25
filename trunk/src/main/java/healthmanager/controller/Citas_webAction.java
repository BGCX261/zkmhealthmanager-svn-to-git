package healthmanager.controller;

import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Citas_web;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pyp;
import healthmanager.modelo.service.Citas_webService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.helper.DateFormatterHelper;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.res.L2HContraintDate;
import com.framework.res.Res;
import com.framework.res.ZKSimpleCalendarEvent;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Citas_webAction extends ZKWindow implements
        WindowRegistrosIMG {

//	private static Logger log = Logger.getLogger(Citas_webAction.class);
    @View
    private Borderlayout groupboxEditar;
    @View
    private Textbox tbxNro_identificacion;
    @View
    private Textbox tbxNomPaciente;
    @View
    private Textbox tbxCentro_atencion;
    @View
    private Textbox tbxCodigo_centro;

    @View
    private Textbox tbxAseguradora;
    @View
    private Textbox tbxInfoAseguradora;
    // @View private Listbox lbxPlan;

    @View
    private Textbox tbxDireccion;
    @View
    private Doublebox dbxTel;
    @View
    private Textbox tbxEdad;
    @View
    private Textbox tbxSexo;
    @View
    private Datebox dtbxFecha_solicitada;
    @View
    private Listbox lbxMedio_solicitud;
    @View
    private Listbox lbxTipo_cita;
    @View
    private Listbox lbxVia_ingreso;

    @View
    private Listbox lbxAnios;
    @View
    private Listbox lbxMeses;
    @View
    private Calendars calendars;

    private SimpleCalendarModel simpleCalendarModel;

    private final String[] idsExcluyentes = new String[]{"tbxAccion", "btNew"};

    @Override
    public void init() throws Exception {
        listarCombos();
        limpiarDatos();
        initCalendarModel();
        dtbxFecha_solicitada.setText("");
        cargarInformacionPacinente();
    }

    private void cargarInformacionPacinente() {
        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(empresa.getCodigo_empresa());
        paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        paciente.setNro_identificacion(paciente_session.getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);
        if (paciente != null) {
            tbxNro_identificacion.setText("" + paciente.getNro_identificacion());
            tbxNomPaciente.setText("" + paciente.getNombreCompleto());

            seleccionarPaciente(paciente);

            ServiciosDisponiblesUtils.validarTipoViaIngresoAdmision(
                    lbxVia_ingreso, paciente, paciente.getCodigo_administradora(),
                    true, false, null, false);
        }
//			Utilidades.setValueFrom(lbxVia_ingreso, admision.getVia_ingreso()); 

    }

    @Override
    public void params(Map<String, Object> map) {

    }

    private void initCalendarModel() {
        simpleCalendarModel = new SimpleCalendarModel();
        calendars.setModel(simpleCalendarModel);
        calendars.setDateFormatter(new DateFormatterHelper());
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() {
        FormularioUtil.limpiarComponentes(this, idsExcluyentes);
        lbxMeses.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
        dbxTel.setText("");

        // agregamos la fecha del dia siguiente
        dtbxFecha_solicitada.setValue(null);

        seleccionarTipo_consulta();
        cargarCalendario_medico();
    }

    public void listarAnios() {
        String[] anios = Res.getAnnos(7);
        for (String anio : anios) {
            Listitem listitem = new Listitem();
            listitem.setValue(anio);
            listitem.setLabel("" + anio);
            lbxAnios.appendChild(listitem);
        }
        if (lbxAnios.getItemCount() > 0) {
            lbxAnios.setSelectedIndex(0);
        }
    }

    private void seleccionarPaciente(Paciente paciente) {
        Adicional_paciente adicional_paciente = new Adicional_paciente();
        adicional_paciente.setCodigo_empresa(codigo_empresa);
        adicional_paciente.setCodigo_sucursal(codigo_sucursal);
        adicional_paciente.setNro_identificacion(paciente
                .getNro_identificacion());
        adicional_paciente = getServiceLocator().getServicio(
                GeneralExtraService.class).consultar(adicional_paciente);

        Administradora administradora = new Administradora();
        administradora.setCodigo_empresa(paciente
                .getCodigo_empresa());
        administradora.setCodigo_sucursal(paciente
                .getCodigo_sucursal());
        administradora.setCodigo(paciente
                .getCodigo_administradora());
        administradora = getServiceLocator()
                .getAdministradoraService().consultar(
                        administradora);

        tbxAseguradora.setValue((administradora != null ? administradora.getCodigo() : ""));
        tbxInfoAseguradora.setValue((administradora != null ? administradora.getNombre() : ""));

        cargarDatosPaciente(paciente);

        lbxVia_ingreso.setDisabled(false);
        /*
         * este para validar los servicios que se les puede
         * brindar al paciente
         */
        ServiciosDisponiblesUtils.validarTipoViaIngresoAdmision(
                lbxVia_ingreso, paciente, paciente.getCodigo_administradora(),
                true, true, null, false);
        Listitem listitem_aux = ServiciosDisponiblesUtils
                .getListitem(
                        lbxVia_ingreso,
                        IVias_ingreso.URGENCIA);
        if (listitem_aux != null) {
            listitem_aux.setVisible(false);
        }

        listitem_aux = ServiciosDisponiblesUtils
                .getListitem(
                        lbxVia_ingreso,
                        IVias_ingreso.URGENCIA_ODONTOLOGICO);
        if (listitem_aux != null) {
            listitem_aux.setVisible(false);
        }

        Barrio barrio = new Barrio();
        barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
        barrio = getServiceLocator().getBarrioService()
                .consultar(barrio);
        //log.info("barrio: " + barrio);

        Centro_barrio centro_barrio = new Centro_barrio();
        centro_barrio.setCodigo_barrio(barrio != null ? barrio
                .getCodigo_barrio() : "");
        centro_barrio = getServiceLocator()
                .getServicio(GeneralExtraService.class).consultar(
                        centro_barrio);

        Centro_atencion centro_atencion = new Centro_atencion();
        centro_atencion.setCodigo_empresa(paciente
                .getCodigo_empresa());
        centro_atencion.setCodigo_sucursal(paciente
                .getCodigo_sucursal());
        centro_atencion
                .setCodigo_centro((centro_barrio != null ? centro_barrio
                        .getCodigo_centro() : ""));
        centro_atencion = getServiceLocator()
                .getCentro_atencionService().consultar(
                        centro_atencion);
        tbxCodigo_centro
                .setValue((centro_atencion != null ? centro_atencion
                        .getCodigo_centro() : ""));
        tbxCentro_atencion
                .setValue((centro_atencion != null ? centro_atencion
                        .getNombre_centro() : ""));
    }

    public void cargarDatosPaciente(Paciente paciente) {
        Departamentos departamentos = new Departamentos();
        departamentos.setCodigo((paciente != null ? paciente.getCodigo_dpto()
                : ""));
        departamentos = getServiceLocator().getDepartamentosService()
                .consultar(departamentos);

        Municipios municipios = new Municipios();
        municipios
                .setCoddep((paciente != null ? paciente.getCodigo_dpto() : ""));
        municipios.setCodigo((paciente != null ? paciente.getCodigo_municipio()
                : ""));
        municipios = getServiceLocator().getMunicipiosService().consultar(
                municipios);

        Elemento elementoTipo_af = new Elemento();
        elementoTipo_af.setTipo("tipo_afiliado");
        elementoTipo_af.setCodigo((paciente != null ? paciente
                .getTipo_afiliado() : ""));
        elementoTipo_af = getServiceLocator().getElementoService().consultar(
                elementoTipo_af);

        tbxEdad.setValue((paciente != null ? Util.getEdad(
                new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
                        .getFecha_nacimiento()), "1", true) : ""));
        tbxSexo.setValue((paciente != null ? Utilidades.getNombreElemento(
                paciente.getSexo(), "sexo", this) : ""));

        tbxDireccion
                .setValue((paciente != null ? paciente.getDireccion() : ""));

        dbxTel.setValue(ConvertidorDatosUtil.convertirDato(paciente
                .getTel_res()));

        cargarCalendario_medico();

    }

    public void listarCombos() {
        Utilidades.listarElementoListbox(lbxMedio_solicitud, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxTipo_cita, true,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxVia_ingreso, true,
                getServiceLocator());
        listarAnios();
    }

    public void seleccionarTipo_consulta() {
		// if (lbxTipo_consulta.getSelectedItem().getValue().toString()
        // .equals("4")) {
        // listarAreaIntervencion(lbxArea_intervencion, true);
        // listarAyuda_dx(lbxAyuda_dx, false);
        // lbxArea_intervencion.setVisible(true);
        // rowArea_intervencion.setVisible(true);
        // lbxAyuda_dx.setVisible(false);
        // lbArea_intervencion.setVisible(true);
        // } else if (lbxTipo_consulta.getSelectedItem().getValue().toString()
        // .equals("21")) {
        // listarAreaIntervencion(lbxArea_intervencion, false);
        // listarAyuda_dx(lbxAyuda_dx, true);
        // lbxArea_intervencion.setVisible(false);
        // rowArea_intervencion.setVisible(false);
        // lbxAyuda_dx.setVisible(true);
        // lbArea_intervencion.setVisible(true);
        // } else {
        // listarAreaIntervencion(lbxArea_intervencion, false);
        // listarAyuda_dx(lbxAyuda_dx, false);
        // lbxArea_intervencion.setVisible(true);
        // rowArea_intervencion.setVisible(true);
        // lbxAyuda_dx.setVisible(false);
        // lbxArea_intervencion.setVisible(false);
        // lbArea_intervencion.setVisible(false);
        // }

        Map map = new HashMap();
        map.put("codigo_empresa", codigo_empresa);
        map.put("codigo_sucursal", codigo_sucursal);
        map.put("nro_identificacion", tbxNro_identificacion.getValue());
        map.put("anio",
                Integer.parseInt(lbxAnios.getSelectedItem().getValue()
                        .toString()));
        map.put("tipo_consulta", lbxVia_ingreso.getSelectedItem().getValue().toString());

        getServiceLocator().getServicio(Citas_webService.class).setLimit("limit 1 offset 0");
        List<Citas_web> lista_citas = getServiceLocator().getServicio(Citas_webService.class).listar(
                map);
        if (!lista_citas.isEmpty()) {
            lbxTipo_cita.setSelectedIndex(2);
        } else {
            lbxTipo_cita.setSelectedIndex(1);
        }

        cargarCalendario_medico();

    }

    public void listarAreaIntervencion(Listbox listbox, boolean sw) {

        listbox.getItems().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("-- seleccionar --");
        listbox.appendChild(listitem);

        if (sw) {
            Map map = new HashMap();
            map.put("codigo_empresa", codigo_empresa);
            map.put("codigo_sucursal", codigo_sucursal);
            List<Pyp> pyps = getServiceLocator().getServicio(GeneralExtraService.class).listar(Pyp.class, map);

            for (Pyp pyp : pyps) {
                listitem = new Listitem();
                listitem.setValue(pyp.getCodigo());
                listitem.setLabel(pyp.getNombre());
                listbox.appendChild(listitem);
            }
        }

        if (listbox.getItemCount() > 0) {
            listbox.setSelectedIndex(0);
        }

    }

    public void listarAyuda_dx(Listbox listbox, boolean sw) {

        listbox.getItems().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("-- seleccionar --");
        listbox.appendChild(listitem);

        if (sw) {
            String tipo = listbox.getName();
            List<Elemento> elementos = getServiceLocator().getElementoService()
                    .listar(tipo);

            for (Elemento elemento : elementos) {
                listitem = new Listitem();
                listitem.setValue(elemento.getCodigo());
                listitem.setLabel(elemento.getDescripcion());
                listbox.appendChild(listitem);
            }
            if (listbox.getItemCount() > 0) {
                listbox.setSelectedIndex(0);
            }
        }
    }

    public boolean validarForm() throws Exception {

        FormularioUtil.validarCamposObligatorios(tbxNro_identificacion, tbxDireccion, dbxTel,
                tbxAseguradora, lbxVia_ingreso,
                lbxTipo_cita, dtbxFecha_solicitada);

        // lbxAyuda_dx.setStyle("background-color:white");
        dtbxFecha_solicitada.setStyle("background-color:white");
        lbxVia_ingreso.setStyle("background-color:white");

        boolean valida = true;
        String msj = "Los campos marcados con (*) son obligatorios";

        if (Calendar.getInstance().getTime()
                .compareTo(dtbxFecha_solicitada.getValue()) > 0) {
            valida = false;
            msj = "La fecha solicitada debe ser mayor que la fecha actual";
            dtbxFecha_solicitada.setStyle("background-color:#F6BBBE");
        }

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
            Messagebox.show(msj, paciente_session.getNombreCompleto() + " recuerde que...",
                    Messagebox.OK, Messagebox.EXCLAMATION);
        }

        return valida;
    }

    public void cargarCalendario_medico() {
        if (lbxVia_ingreso.getSelectedItem() != null
                && !lbxVia_ingreso.getSelectedItem().getValue().toString()
                .isEmpty()) {

            String codigo_rol = verificarRolMedico();

            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.set(
                    Calendar.YEAR,
                    Integer.parseInt(lbxAnios.getSelectedItem().getValue()
                            .toString()));
            gregorianCalendar
                    .set(Calendar.MONTH, (lbxMeses.getSelectedIndex()));
            gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);

            simpleCalendarModel = (SimpleCalendarModel) calendars.getModel();
            if (simpleCalendarModel != null) {
                simpleCalendarModel.clear();
            }

            calendars.setCurrentDate(gregorianCalendar.getTime());

            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

            parameters.put("anio", gregorianCalendar.get(Calendar.YEAR));
            parameters.put("mes", (gregorianCalendar.get(Calendar.MONTH) + 1));
            parameters.put("codigo_rol", codigo_rol);

            parameters.put("limit", "limit 25 offset 0");
            // //log.info("parameters horario: "+parameters);
            List<Detalles_horarios> listaDetalles_horarios = getServiceLocator()
                    .getDetallesHorariosService().listar(parameters);
            // //log.info("listaDetalles_horarios: "+listaDetalles_horarios);
            for (Detalles_horarios detalles_horarios : listaDetalles_horarios) {
                ZKSimpleCalendarEvent simpleCalendarEvent = Res
                        .converTo(detalles_horarios);
                simpleCalendarEvent.setContent("");
                // aplicar formato de fecha
                DateFormatterHelper
                        .aplicarFormatoValidoPara(simpleCalendarEvent);

//				String hora_final = new SimpleDateFormat("HH:mm")
//						.format(detalles_horarios.getFecha_final());
                Citas_web citas = new Citas_web();
                citas.setCodigo_empresa(detalles_horarios.getCodigo_empresa());
                citas.setCodigo_sucursal(detalles_horarios.getCodigo_sucursal());
                citas.setCodigo_detalle_horario(detalles_horarios
                        .getConsecutivo());
                citas = getServiceLocator().getServicio(Citas_webService.class).consultar(citas);
                simpleCalendarEvent.setEstado_cita((citas != null ? citas
                        .getEstado() : ""));
                simpleCalendarEvent.setCodigo_cita((citas != null ? citas
                        .getCodigo_cita() : ""));
                if (citas == null) {
                    simpleCalendarEvent.setContent(simpleCalendarEvent
                            .getContent() + " Libre");
                    simpleCalendarEvent.setHeaderColor("");
                } else if (citas.getEstado().equals("1")) {
                    simpleCalendarEvent.setContent(simpleCalendarEvent
                            .getContent() + " Asignada");
                    simpleCalendarEvent.setHeaderColor("yellow");
                } else if (citas.getEstado().equals("2")) {
                    simpleCalendarEvent.setContent(simpleCalendarEvent
                            .getContent() + " Cumplida");
                    simpleCalendarEvent.setHeaderColor("green");
                } else if (citas.getEstado().equals("5")) {
                    simpleCalendarEvent.setContent(simpleCalendarEvent
                            .getContent() + " Cancelada");
                    simpleCalendarEvent.setHeaderColor("red");
                }
                simpleCalendarModel.add(simpleCalendarEvent);
            }
        } else {
            simpleCalendarModel = (SimpleCalendarModel) calendars.getModel();
            if (simpleCalendarModel != null) {
                simpleCalendarModel.clear();
            }
        }

    }

    public String verificarRolMedico() {
        String rol_medico = "05";
        if (lbxVia_ingreso.getSelectedItem() != null) {
            String tipo_consulta = lbxVia_ingreso.getSelectedItem().getValue()
                    .toString();
            if (tipo_consulta.equals(ServiciosDisponiblesUtils.CONSULTA_EXTERNA)) {
                rol_medico = "05";
            } else if (tipo_consulta
                    .equals(ServiciosDisponiblesUtils.CONSULTA_ODONOTOLOGIA)) {
                rol_medico = "12";
            } else if (tipo_consulta
                    .equals(ServiciosDisponiblesUtils.CONSULTA_PSICOLOGIA)
                    || tipo_consulta
                    .equals(ServiciosDisponiblesUtils.CONSULTA_PSIQUIATRIA)) {
                rol_medico = "14";
            } else if (tipo_consulta
                    .equals(ServiciosDisponiblesUtils.CONSULTA_PYP)) {
                rol_medico = "13";
            }
        }
        return rol_medico;

    }

    public void guardarCita(Event arg0) throws Exception {
        try {
            final CalendarsEvent evento = (CalendarsEvent) arg0;
            ZKSimpleCalendarEvent simpleCalendarEvent = (ZKSimpleCalendarEvent) evento
                    .getCalendarEvent();
            if (simpleCalendarEvent.getEstado_cita().equals("")) {
                if (validarForm()) {
                    generarCita(simpleCalendarEvent);
                }
            } else if (simpleCalendarEvent.getEstado_cita().equals("1")) {
                imprimir(simpleCalendarEvent.getCodigo_cita());
            }
        } catch (Exception e) {
            if (!(e instanceof WrongValueException)) {
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    private void generarCita(ZKSimpleCalendarEvent simpleCalendarEvent)
            throws Exception {
        final ZKSimpleCalendarEvent calendarEvent = simpleCalendarEvent;
        try {
            Messagebox
                    .show("Paciente "
                            + paciente_session.getNombreCompleto()
                            + " estas seguro que deseas apartar esta cita, para el dia "
                            + new SimpleDateFormat("yyyy/MM/dd")
                            .format(simpleCalendarEvent.getBeginDate()),
                            "Informacion", Messagebox.OK + Messagebox.CANCEL,
                            Messagebox.INFORMATION, new EventListener() {
                                @Override
                                public void onEvent(Event arg0)
                                throws Exception {
                                    if ("onOK".equals(arg0.getName())) {
                                        try {
                                            Date today = Calendar.getInstance()
                                            .getTime();

                                            Map<String, Object> parametros = new HashMap<String, Object>();
                                            parametros.put("codigo_empresa",
                                                    codigo_empresa);
                                            parametros.put("codigo_sucursal",
                                                    codigo_sucursal);
                                            parametros.put(
                                                    "nro_identificacion",
                                                    tbxNro_identificacion
                                                    .getValue());
                                            parametros.put("tipo_consulta",
                                                    lbxVia_ingreso
                                                    .getSelectedItem()
                                                    .getValue()
                                                    .toString());
                                            parametros.put("anio", anio);
                                            parametros.put("estado", "1");

                                            List<Citas_web> listado_citas = getServiceLocator().getServicio(Citas_webService.class)
                                            .listar(
                                                    parametros);
                                            if (!listado_citas.isEmpty()) {
                                                Citas_web citas_aux = listado_citas
                                                .get(0);
                                                throw new Exception(
                                                        "No se puede hacer el registro de la cita porque este paciente tiene una cita activa "
                                                        + "en esta área '"
                                                        + lbxVia_ingreso
                                                        .getSelectedItem()
                                                        .getLabel()
                                                        + "' para la fecha "
                                                        + new SimpleDateFormat(
                                                                "yyyy/MM/dd")
                                                        .format(citas_aux
                                                                .getFecha_cita()));
                                            }

                                            final Citas_web citas = new Citas_web();
                                            citas.setCodigo_empresa(empresa
                                                    .getCodigo_empresa());
                                            citas.setCodigo_sucursal(sucursal
                                                    .getCodigo_sucursal());
                                            citas.setNro_identificacion(tbxNro_identificacion
                                                    .getValue());
                                            citas.setFecha_cita(new Timestamp(
                                                            calendarEvent
                                                            .getBeginDate()
                                                            .getTime()));
                                            citas.setHora(formatHora
                                                    .format(citas
                                                            .getFecha_cita()));
                                            citas.setValor_cita(0);
                                            citas.setCopago_cita(0);
                                            citas.setEstado("1");
                                            citas.setTipo_consulta(lbxVia_ingreso
                                                    .getSelectedItem()
                                                    .getValue().toString());
                                            citas.setCreacion_date(new Timestamp(
                                                            Calendar.getInstance()
                                                            .getTimeInMillis()));
                                            citas.setUltimo_update(new Timestamp(
                                                            Calendar.getInstance()
                                                            .getTimeInMillis()));
                                            citas.setCreacion_user(getCreacionUser());
                                            citas.setUltimo_user(getCreacionUser());
											// citas.setArea_intervencion(lbxArea_intervencion
                                            // .getSelectedItem()
                                            // .getValue().toString());
                                            citas.setCodigo_plantilla("");
                                            citas.setCodigo_detalle_horario(calendarEvent
                                                    .getConsecutivo());
                                            citas.setFecha_solicitada(new Timestamp(
                                                            dtbxFecha_solicitada
                                                            .getValue()
                                                            .getTime()));
                                            /* calculamos la resolucion 1552 */
                                            citas.setDiferencia1(new Long(
                                                            L2HContraintDate.getDiferentDay(
                                                                    citas.getFecha_solicitada(),
                                                                    today)).intValue());
                                            citas.setDiferencia2(new Long(
                                                            L2HContraintDate.getDiferentDay(
                                                                    citas.getFecha_cita(),
                                                                    today)).intValue());
                                            /* fin de calculo de diferencias */

                                            citas.setCodigo_administradora(tbxAseguradora
                                                    .getValue());
											// citas.setId_plan(lbxPlan
                                            // .getSelectedItem().getValue()
                                            // .toString());
                                            citas.setMedio_solicitud(lbxMedio_solicitud
                                                    .getSelectedItem()
                                                    .getValue().toString());
                                            citas.setTipo_cita(lbxTipo_cita
                                                    .getSelectedItem()
                                                    .getValue().toString());
											// if (lbxAyuda_dx.getSelectedItem()
                                            // != null) {
                                            // citas.setAyuda_dx(lbxAyuda_dx
                                            // .getSelectedItem()
                                            // .getValue().toString());
                                            // }

                                            final Citas_web auxCitas = getServiceLocator()
                                            .getServicio(Citas_webService.class).guardarCita(citas, tbxDireccion.getValue(),
                                                    String.valueOf(dbxTel.getValue()));

                                            cargarCalendario_medico();

                                            Messagebox
                                            .show("Cita apartada exitosamente, Desea imprimir el recordatorio de citas.",
                                                    "Informacion",
                                                    Messagebox.YES
                                                    + +Messagebox.NO,
                                                    Messagebox.INFORMATION,
                                                    new EventListener<Event>() {
                                                        @Override
                                                        public void onEvent(
                                                                Event event)
                                                        throws Exception {
                                                            if ("onYes"
                                                            .equalsIgnoreCase(event
                                                                    .getName())) {
                                                                imprimir(auxCitas
                                                                        .getCodigo_cita());
                                                            }
                                                        }
                                                    });
                                        } catch (Exception e) {
                                            MensajesUtil
                                            .mensajeAlerta(
                                                    "No se puede apartar la cita medica",
                                                    e.getMessage());
                                        }
                                    }
                                }
                            });
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
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

    public void abrirWindowHistorial() {
        if (tbxNro_identificacion.getValue().equals("")) {
            MensajesUtil.mensajeAlerta("Alerta!!",
                    "Debe seleccionar el paciente");
            return;
        }
        Map map = new HashMap();
		// map.put("codigo_empresa", codigo_empresa);
        // map.put("codigo_sucursal", codigo_sucursal);
        map.put("nro_identificacion", tbxNro_identificacion.getValue());
        map.put("anio",
                Integer.parseInt(lbxAnios.getSelectedItem().getValue()
                        .toString()));

        String columnas = "Codigo#100px|Nro id#100px|Paciente|Fecha cita#150px|Estado#100px";
        WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
                "CONSULTAR HISTORIAL DE CITAS", Paquetes.HEALTHMANAGER,
                "Citas_webDao.listar", this, columnas, map);
        windowRegistros.getToolbar().setVisible(false);
        windowRegistros.mostrarWindow(new ArrayList<String>());
        windowRegistros.onConsultarRegistros();

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

}
