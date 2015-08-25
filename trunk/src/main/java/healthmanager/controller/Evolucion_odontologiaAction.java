/*
 * evolucion_odontologiaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Diente;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Evolucion_odontologia;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Plan_tratamiento;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Plan_tratamientoService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.Remision_internaService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IAccionEvento;
import com.framework.interfaces.IHistoria_generica;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.odontograma.OdontogramaMacro;
import com.framework.macros.odontograma.util.ISectorDiente.TypeSector;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.Res;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorPoblacion;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class Evolucion_odontologiaAction extends HistoriaClinicaModel implements
        ISeleccionarComponente, IHistoria_generica {

    private Plan_tratamientoService plan_tratamientoService;

    private static Logger log = Logger
            .getLogger(Evolucion_odontologiaAction.class);

	// manuel
    // private List<String> lista_procedimientos;
    private List<Map<String, Object>> lista_procedimientos;
    @View
    private Radiogroup rdbEstrategia_casa;
    @View
    private Radiogroup rdbUsa_seda;
    @View
    private Radiogroup rdbUsa_enjuague;
    @View
    private Radiogroup rdbConsulta_odontologo;
    @View
    private Textbox tbxTotal_si;
    @View
    private Textbox tbxTotal_no;
    @View
    private Groupbox gbxAutocuidado;
    @View
    private Checkbox chbTratamiento;
    // ------------------------------------
    @View
    private Textbox tbxMotivo_consulta;
    @View
    private Textbox tbxEnfermedad_actual;
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
    private Listbox lbxRelacion;

    @View
    private Div divModuloRemisiones_externas;

    @View
    private Longbox tbxCodigo_evolucion_historia;

    @View
    private Toolbarbutton btCancel;

    @View
    private Button botonNuevo;

    /*
     * @View(isMacro = true) private InformacionPacientesMacro infoPacientes;
     */
    @View
    private Textbox tbxProcedimiento_realizado;
    private final String[] idsExcluyentes = {"lbxParameter", "tbxValue",
        "tbxCodigo_prestadorEvolucion", "tbxNomPrestador",
        "dtbxFecha_ingreso", "btCancel"};

    @View(isMacro = true)
    BandboxRegistrosMacro tbxCodigo_prestadorEvolucion;
    @View(isMacro = true)
    OdontogramaMacro odtodontograma;
    @View(isMacro = true)
    OdontogramaMacro odtodontograma_evolucion;
    @View
    Textbox tbxNomPrestador;

    @View
    private Datebox dtbxFecha_ingreso;

    @View
    private Button btGuardar;
    @View
    private Toolbarbutton btnLimpiarPrestador;

    /* historia odontologica */
    private Odontologia odontologiaTemp;

    private boolean primera_vez;

    private OpcionesFormulario opcion_formulario;

    @View
    private Listbox lbxProcedimientoRealizados;

    @View
    Listbox lbxProcedimientoPorRealizar;

    private List<Diente> dientesOdontologia;

    private String nro_ingreso;

    @View
    private Textbox tbxAcompaniante;
    @View
    private Textbox tbxTel_acompaniante;

    @View
    private Div divModuloOrdenamiento;
    @View
    private Div divModuloFarmacologico;

    // admision segunda vez
    @View
    private Groupbox groupPlanesManejos;
    @View
    private Groupbox groupPronostico;
    @View
    private Groupbox gbxRemisiones;

    @View
    private Groupbox gbxRemisionesInternas;

    @View
    private Listbox lbxPronostico;

    @View(isMacro = true)
    private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

    private Remisiones_externasAction remisiones_externasAction;
    private Orden_servicio_internoAction orden_servicioAction;
    private Receta_rips_internoAction receta_ripsAction;

    @View(isMacro = true)
    private Remision_internaMacro macroRemision_interna;

    private String nro_ingreso_temp;

    private Admision admision;

    private List<String> seleccionados = new ArrayList<String>();

    public void mostrarAlerta(boolean valida) {
        Integer TIEMPO_ALERTA = IConstantes.TIEMPO_NOTIFICACIONES_GENERALES;
        if (valida) {
            String mensaje = "Este paciente ha terminado su plan de tratamiento de forma exitosa\n diligencie las preguntas que se encuentran al final de la historia";
            Notificaciones.mostrarNotificacionAlerta("paciente terminado",
                    mensaje, TIEMPO_ALERTA);
        }

    }

    public void checkAutocuidado(Checkbox checkbox) {
        if (checkbox.isChecked()) {
            gbxAutocuidado.setOpen(true);
            gbxAutocuidado.setClosable(true);
        } else {
            gbxAutocuidado.setOpen(false);
            gbxAutocuidado.setClosable(false);
        }
    }

    public void calcularTotal() {
        Integer valor_si = 0;
        Integer valor_no = 0;
        if (rdbEstrategia_casa.getSelectedItem().getValue().equals("1")) {
            valor_si += 25;
        } else {
            valor_no += 25;
        }
        if (rdbUsa_seda.getSelectedItem().getValue().equals("1")) {
            valor_si += 25;
        } else {
            valor_no += 25;
        }
        if (rdbUsa_enjuague.getSelectedItem().getValue().equals("1")) {
            valor_si += 25;
        } else {
            valor_no += 25;
        }
        if (rdbConsulta_odontologo.getSelectedItem().getValue().equals("1")) {
            valor_si += 25;
        } else {
            valor_no += 25;
        }
        tbxTotal_si
                .setValue(ConvertidorDatosUtil.convertirDato(valor_si) + "%");
        tbxTotal_no
                .setValue(ConvertidorDatosUtil.convertirDato(valor_no) + "%");

    }

    @Override
    public void init() throws Exception {
        listarCombos();
        inicializarOdontograma();
        parametrizarBandbox();
        macroImpresion_diagnostica.inicializarMacro(this, admision,
                IVias_ingreso.ODONTOLOGIA);
        macroRemision_interna.inicializarMacro(this, admision,
                IVias_ingreso.ODONTOLOGIA2);
        initDatos();
    }

    private void initDatos() {
        try {
            dtbxFecha_ingreso.setValue(Calendar.getInstance().getTime());
        } catch (Exception e) {

        }
    }

    private void parametrizarBandbox() {
        tbxCodigo_prestadorEvolucion.inicializar(tbxNomPrestador,
                btnLimpiarPrestador);
        inicializarBandboxPrestadores();
    }

    private void inicializarBandboxPrestadores() {
        BandboxRegistrosIMG<Prestadores> bandboxRegistrosIMG = new BandboxRegistrosIMG<Prestadores>() {
            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Prestadores registro) {
                bandbox.setValue(registro.getNro_identificacion());
                textboxInformacion.setValue(registro.getNombres());
                return true;
            }

            @Override
            public void renderListitem(Listitem listitem, Prestadores registro) {
                Especialidad especialidad = new Especialidad();
                especialidad.setCodigo(registro.getCodigo_especialidad());
                especialidad = getServiceLocator().getEspecialidadService()
                        .consultar(especialidad);

                listitem.setValue(registro);

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(registro
                        .getTipo_identificacion() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNro_identificacion()
                        + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNombres()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getApellidos()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(
                        (especialidad != null ? especialidad.getNombre() : "")));
                listitem.appendChild(listcell);
            }

            @Override
            public List<Prestadores> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {
                Map<String, Object> parameters = new HashMap();
                parameters.put("codigo_empresa", codigo_empresa);
                parameters.put("codigo_sucursal", codigo_sucursal);
                parameters.put("paramTodo", "");
                parameters.put("value", "%"
                        + valorBusqueda.toUpperCase().trim() + "%");
                parametros.put("limite_paginado", "limit 25 offset 0");
                return getServiceLocator().getPrestadoresService().listar(
                        parameters);
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
            }
        };
        /* inyectamos el mismo evento */
        tbxCodigo_prestadorEvolucion
                .setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    /**
     * Este metodo me habilita cuando la evolucion es por primera vez o de
     * control
     *
     * @author Luis Miguel
	 *
     */
    private void accionPrimeraVezControl() {
        if (primera_vez) {
            // groupPlanesManejos.setVisible(false);
            divModuloOrdenamiento.setVisible(false);
            btCancel.setVisible(false);
			// groupPronostico.setVisible(false);
            // groupPreguntasBuentrabajo.setVisible(false);
            // groupPreguntasPandas.setVisible(false);
            gbxRemisiones.setVisible(false);
            gbxRemisionesInternas.setVisible(false);
            // lbxProcedimientoPorRealizar.setVisible(true);
        } else {
            // groupPlanesManejos.setVisible(true);
            divModuloOrdenamiento.setVisible(false);
            btCancel.setVisible(true);
			// groupPronostico.setVisible(true);
            // groupPreguntasBuentrabajo.setVisible(true);
            // groupPreguntasPandas.setVisible(true);
            gbxRemisiones.setVisible(true);
            gbxRemisionesInternas.setVisible(true);
            // lbxProcedimientoPorRealizar.setVisible(false);

        }
    }

    public void cargarPrestador() throws Exception {
        if (rol_usuario.equals("05")) {
            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
            prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            prestadores.setNro_identificacion(usuarios.getCodigo());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);
            if (prestadores == null) {
                throw new Exception(
                        "Usuario no esta creado en el modulo de prestadore, actualize informacion de usuario");
            }
            tbxCodigo_prestadorEvolucion.setValue(prestadores
                    .getNro_identificacion());
            tbxNomPrestador.setValue(prestadores.getNombres() + " "
                    + prestadores.getApellidos());
        } else {
            if (admision != null) {
                Prestadores prestadores = new Prestadores();
                prestadores.setCodigo_empresa(admision.getCodigo_empresa());
                prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
                prestadores.setNro_identificacion(admision.getCodigo_medico());
                prestadores = getServiceLocator().getPrestadoresService()
                        .consultar(prestadores);

                tbxCodigo_prestadorEvolucion
                        .setValue((prestadores != null ? prestadores
                                .getNro_identificacion() : "000000000"));
                tbxNomPrestador.setValue((prestadores != null ? prestadores
                        .getNombres() + " " + prestadores.getApellidos()
                        : "MEDICO POR DEFECTO"));
            } else {
                tbxCodigo_prestadorEvolucion.setValue("000000000");
                tbxNomPrestador.setValue("MEDICO POR DEFECTO");
            }
        }
    }

    @Override
    public void params(Map<String, Object> map) {
		// admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
        // //log.info("ADMISION>>>>>>>>>>>>" + admision);
        // if (admision != null) {
        // //log.info("via de ingreso >>>>>>>>>>" + admision.getVia_ingreso());
        // }

		// if (!admision.getVia_ingreso().equals(
        // IVias_ingreso.URGENCIA_ODONTOLOGICO)) {
        lista_procedimientos = (List<Map<String, Object>>) map
                .get("lista_codigos_fac");

        if (lista_procedimientos == null) {
            lista_procedimientos = new ArrayList<Map<String, Object>>();
        }

        /*
         * lista_procedimientos = (List<String>) map.get("lista_codigos_fac");
         * if (lista_procedimientos == null) { lista_procedimientos = new
         * ArrayList<String>(); }
         */
        odontologiaTemp = (Odontologia) map.get("odontologiaTemp");
        admision = (Admision) map.get("admision");
        nro_ingreso_temp = nro_ingreso = (String) map.get("nro_ingreso");
        primera_vez = (Boolean) map.get("primera_vez");
        macroRemision_interna.deshabilitarCheck(admision,
                IVias_ingreso.ODONTOLOGIA2);
        opcion_formulario = (OpcionesFormulario) map.get("opcion_formulario");
		// if (odontologiaTemp != null) {
        // cargarDatosDeOdontologia();

        accionPrimeraVezControl();

        // }
    }

	// }
    /**
     * Aqui cargo los procedimientos por realizar del odontologo.
	 *
     */
    private void cargarProcedimientosRealizados() {
        cargamosDetalleOrden();
        lista_procedimientos.clear();
    }

    public void openPcd() throws Exception {

        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);

        String anio = (manuales_tarifarios != null ? (manuales_tarifarios
                .getAnio() != null ? manuales_tarifarios.getAnio() : "") : "");

        String pages = "/pages/openProcedimientos.zul";

        Map parametros = new HashMap();
        parametros.put("codigo_empresa", empresa.getCodigo_empresa());
        parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
        parametros.put("codigo_administradora",
                admision.getCodigo_administradora());
        parametros.put("id_plan", admision.getId_plan());
        parametros.put("restringido", "");
        parametros.put("nro_ingreso", admision.getNro_ingreso());
        parametros.put("nro_identificacion", admision.getNro_identificacion());
        // parametros.put("estrato", "");
        parametros.put("admision", admision);
        // parametros.put("quirurgico", "");
        parametros.put("anio", anio);
        parametros.put("seleccionados", seleccionados);
        parametros.put("via_ingreso", admision.getVia_ingreso());
        parametros.put("manual_tarifario", manuales_tarifarios);

		//log.info("Paginas: " + pages);
        Component componente = Executions.createComponents(pages, null,
                parametros);
        final Window ventana = (Window) componente;
        if (ventana instanceof OpenProcedimientosAction) {
            ((OpenProcedimientosAction) ventana)
                    .setSeleccionar_componente(this);
        }
        ventana.setWidth("990px");
        ventana.setHeight("400px");
        ventana.setClosable(true);
        ventana.setMaximizable(true);
        ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
        ventana.setMode("modal");
    }

    private void cargamosDetalleOrden() {
        try {
            Map<String, Object> mapOrdenServicio = new HashMap<String, Object>();
            mapOrdenServicio
                    .put("codigo_empresa", admision.getCodigo_empresa());
            mapOrdenServicio.put("codigo_sucursal",
                    admision.getCodigo_sucursal());
            mapOrdenServicio.put("nro_identificacion",
                    admision.getNro_identificacion());
			// mapOrdenServicio.put("nro_ingreso",
            // odontologiaTemp.getNro_ingreso());
            // mapOrdenServicio.put("cumplida", "O");

            // mapOrdenServicio.put("detalles_faltantes", "detalles_faltantes");
            List<Plan_tratamiento> detalle_ordens = getServiceLocator()
                    .getServicio(Plan_tratamientoService.class).listar(
                            mapOrdenServicio);
            lbxProcedimientoRealizados.getItems().clear();
            lbxProcedimientoPorRealizar.getItems().clear();

            orden_servicioAction.limpiar();
			// Manuales_tarifarios manuales_tarifarios =
            // ServiciosDisponiblesUtils
            // .getManuales_tarifarios(admision);

            Map<String, Object[]> mapa_id_procedimientos = new HashMap<String, Object[]>();

            for (int i = 0; i < lista_procedimientos.size(); i++) {
                Map<String, Object> mapa = lista_procedimientos.get(i);
                String codigo = mapa.get("codigo").toString();
                mapa_id_procedimientos.put(codigo,
                        new Object[]{codigo, mapa.get("cantidad")});
            }
            /*
             * for (String codigo : lista_procedimientos.get("codigo")) {
             * mapa_id_procedimientos.put(codigo, codigo); }
             */

            for (Plan_tratamiento detalle_orden : detalle_ordens) {
                if (mapa_id_procedimientos.containsKey(detalle_orden
                        .getCodigo_procedimiento())) {
                    mapa_id_procedimientos.remove(detalle_orden
                            .getCodigo_procedimiento());
                }
                cargarProcedimientoALista(detalle_orden, false);
            }

            for (String codigo_procedimiento : mapa_id_procedimientos.keySet()) {
                Integer CANTIDAD_INICIALIZAR = (Integer) mapa_id_procedimientos
                        .get(codigo_procedimiento)[1];
                Map<String, Object> pcd = Utilidades.getProcedimiento(
                        ServiciosDisponiblesUtils
                        .getManuales_tarifarios(admision), new Long(
                                codigo_procedimiento), getServiceLocator());

                String nombre_procedimiento = (String) pcd
                        .get("nombre_procedimiento");

                if (nombre_procedimiento != null
                        && !nombre_procedimiento.isEmpty()) {

                    Plan_tratamiento plan_tratamiento = new Plan_tratamiento();
                    plan_tratamiento.setCodigo_empresa(admision
                            .getCodigo_empresa());
                    plan_tratamiento.setCodigo_sucursal(admision
                            .getCodigo_sucursal());
                    plan_tratamiento.setCodigo_cups((String) pcd
                            .get("codigo_cups"));
                    plan_tratamiento.setNro_identificacion(admision
                            .getNro_identificacion());
                    plan_tratamiento
                            .setCodigo_procedimiento(codigo_procedimiento);
                    plan_tratamiento
                            .setDescuento((Double) pcd.get("descuento"));
                    plan_tratamiento.setEstado("0");
                    plan_tratamiento.setIncremento((Double) pcd
                            .get("incremento"));
                    plan_tratamiento.setRealizado("N");
                    plan_tratamiento.setTipo_procedimiento((String) pcd
                            .get("tipo_procedimiento"));
                    plan_tratamiento
                            .setUnidades(CANTIDAD_INICIALIZAR != null ? CANTIDAD_INICIALIZAR
                                    : 1);
                    plan_tratamiento.setUnidades_realizadas(0);
                    plan_tratamiento.setValor_procedimiento((Double) pcd
                            .get("valor_pcd"));
                    plan_tratamiento.setValor_real((Double) pcd
                            .get("valor_real"));

                    // Este es si ya tiene un plan de tratamiento
                    Plan_tratamiento plan_tratamiento_aux = plan_tratamientoService
                            .consultar(plan_tratamiento);

                    if (plan_tratamiento_aux != null) {
                        plan_tratamiento_aux.setUnidades(plan_tratamiento_aux
                                .getUnidades().intValue()
                                + plan_tratamiento_aux.getUnidades());
                        plan_tratamiento_aux.setUltimo_user(getUsuarios()
                                .getCodigo());
                        plan_tratamiento_aux.setUltimo_update(new Timestamp(
                                System.currentTimeMillis()));
                        plan_tratamientoService
                                .actualizar(plan_tratamiento_aux);
                    } else {
                        plan_tratamiento.setCreacion_user(getUsuarios()
                                .getCodigo());
                        plan_tratamiento.setUltimo_user(getUsuarios()
                                .getCodigo());
                        plan_tratamiento.setCreacion_date(new Timestamp(System
                                .currentTimeMillis()));
                        plan_tratamiento.setUltimo_update(new Timestamp(System
                                .currentTimeMillis()));
                        plan_tratamiento.setEstado("0"); // 0 NO REALIZADO
                        plan_tratamientoService.crear(plan_tratamiento);
                    }
                    cargarProcedimientoALista(plan_tratamiento, false);
                }

            }

            if (lbxProcedimientoPorRealizar.getItems().isEmpty()) {
                //log.info("entrando al s¿Del chek");
                chbTratamiento.setChecked(true);
                checkAutocuidado(chbTratamiento);
                mostrarAlerta(true);
            }

			// lbxProcedimientoPorRealizar.invalidate();
            orden_servicioAction.invalidate();
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, null, this);
        }
    }

    private void cargarProcedimientoALista(Plan_tratamiento plan_tratamiento,
            boolean habilitarEdicion) {

        if (!plan_tratamiento.getUnidades().equals(
                plan_tratamiento.getUnidades_realizadas())) {
            lbxProcedimientoPorRealizar
                    .appendChild(crearListItemProcedimientoPorRealizar(
                                    plan_tratamiento, habilitarEdicion, null));
        }

        if (plan_tratamiento.getUnidades_realizadas().intValue() != 0) {
            lbxProcedimientoRealizados
                    .appendChild(crearListitemProcedimientoRealizado(plan_tratamiento));
        }

    }

	// private Map<String, Object> convertirHaMapa(Detalle_orden detalle_orden,
    // Manuales_tarifarios manuales_tarifarios) throws Exception {
    // Map<String, Object> pcd = Utilidades.getProcedimiento(
    // manuales_tarifarios, detalle_orden.getCodigo_procedimiento(),
    // getServiceLocator());
    // Map<String, Object> map = new HashMap<String, Object>();
    // map.put("codigo_empresa", detalle_orden.getCodigo_empresa());
    // map.put("codigo_sucursal", detalle_orden.getCodigo_sucursal());
    // map.put("codigo_procedimiento", detalle_orden.getCodigo_procedimiento());
    // map.put("valor_procedimiento", detalle_orden.getValor_procedimiento());
    // map.put("descuento", detalle_orden.getDescuento());
    // map.put("incremento", detalle_orden.getIncremento());
    // map.put("valor_real", detalle_orden.getValor_real());
    // map.put("codigo_cups", detalle_orden.getCodigo_cups());
    // map.put("nombre_procedimiento",
    // (String) pcd.get("nombre_procedimiento"));
    // return map;
    // }
    private Listitem crearListItemProcedimientoPorRealizar(
            Plan_tratamiento plan_tratamiento, boolean habilitarEdicion,
            Integer cantidad_inicializar) {

        final Listitem listitem = new Listitem();
        listitem.setSelected(false);
        listitem.setValue(plan_tratamiento);

        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);
        String nombre_procedimiento = "";
        String codigo_cups = plan_tratamiento.getCodigo_procedimiento();
        int cantidad_maxima = 0;
        try {
            Map<String, Object> mapProcedimiento = Utilidades.getProcedimiento(
                    manuales_tarifarios,
                    new Long(plan_tratamiento.getCodigo_procedimiento()),
                    getServiceLocator());
            nombre_procedimiento = (String) mapProcedimiento
                    .get("nombre_procedimiento");

            codigo_cups = (String) mapProcedimiento.get("codigo_cups");
            cantidad_maxima = (Integer) mapProcedimiento.get("cantidad_maxima");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String cons_nombre_procedimiento = nombre_procedimiento;
        final int cons_cantidad_maxima = cantidad_maxima;

        listitem.appendChild(new Listcell("" + codigo_cups));
        listitem.appendChild(new Listcell("" + nombre_procedimiento));

        Listcell listcell = new Listcell();
        final Intbox intbox_realizadas = new Intbox();
        intbox_realizadas.setHflex("1");

		// intbox_realizadas.setId("intbox_realizadas_"
        // + detalle_orden.getCodigo_empresa() + "_"
        // + detalle_orden.getCodigo_sucursal() + "_"
        // + detalle_orden.getConsecutivo());
        // intbox_realizadas.setValue(detalle_orden.getUnidades_realizadas());
        listcell.appendChild(intbox_realizadas);
        if (cantidad_inicializar != null) {
            intbox_realizadas.setValue(cantidad_inicializar);
        }

        intbox_realizadas.addEventListener(Events.ON_CHANGE,
                new EventListener<Event>() {
                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        if (intbox_realizadas.getValue() != null) {
                            if (intbox_realizadas.getValue() != 0) {
                                listitem.setSelected(true);
                            } else {
                                listitem.setSelected(false);
                            }

                        } else {
                            listitem.setSelected(false);
                        }

                    }
                });
        listitem.appendChild(listcell);
        listitem.setAttribute("intbox_realizadas", intbox_realizadas);

        final Intbox intbox_por_realizar = new Intbox(
                (plan_tratamiento.getUnidades() - plan_tratamiento
                .getUnidades_realizadas()));
        intbox_por_realizar.setHflex("1");
        intbox_por_realizar.setReadonly(!habilitarEdicion);
        intbox_por_realizar.setInplace(!habilitarEdicion);

        if (habilitarEdicion) {
            plan_tratamiento.setUnidades(1);
            Res.cargarAutomatica(intbox_por_realizar, plan_tratamiento,
                    "unidades", new IAccionEvento() {
                        @Override
                        public void accion() {
                            FormularioUtil.onAccionValidarCantidadMaxima(
                                    cons_cantidad_maxima, intbox_por_realizar,
                                    cons_nombre_procedimiento, "PROCEDIMIENTO");
                        }
                    });
        }

        listcell = new Listcell();
        listcell.appendChild(intbox_por_realizar);
        listitem.appendChild(listcell);

        return listitem;
    }

    private Listitem crearListitemProcedimientoRealizado(
            Plan_tratamiento plan_tratamiento) {
        Listitem listitem = new Listitem();
        listitem.setValue(plan_tratamiento);

        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);
        String nombre_procedimiento = "";
        String codigo_cups = plan_tratamiento.getCodigo_procedimiento();
        try {
            Map<String, Object> mapProcedimiento = Utilidades.getProcedimiento(
                    manuales_tarifarios,
                    new Long(plan_tratamiento.getCodigo_procedimiento()),
                    getServiceLocator());
            codigo_cups = (String) mapProcedimiento.get("codigo_cups");
            nombre_procedimiento = (String) mapProcedimiento
                    .get("nombre_procedimiento");

        } catch (Exception e) {
        }

        listitem.appendChild(new Listcell(codigo_cups));
        listitem.appendChild(new Listcell(nombre_procedimiento));
        listitem.appendChild(new Listcell(plan_tratamiento
                .getUnidades_realizadas() + ""));
        listitem.appendChild(new Listcell(plan_tratamiento.getUnidades() + ""));

        return listitem;
    }

    public void cargarDatosDeOdontologia() {
        odtodontograma.setCariadosAdulto(odontologiaTemp
                .getOdont_grama_cariados_adulto());
        odtodontograma.setCariadosLeche(odontologiaTemp
                .getOdont_grama_cariados_leche());
        odtodontograma.setCeoLeche(odontologiaTemp.getOdont_grama_ceo_leche());
        odtodontograma
                .setCopAdulto(odontologiaTemp.getOdont_grama_cop_adulto());
        odtodontograma.setEstraidosLeche(odontologiaTemp
                .getOdont_grama_estraidos_leche());
        odtodontograma.setOpturadosAdulto(odontologiaTemp
                .getOdont_grama_opturados_adulto());
        odtodontograma.setOpturadoLeche(odontologiaTemp
                .getOdont_grama_opturados_leche());
        odtodontograma.setPerdidosAdulto(odontologiaTemp
                .getOdont_grama_perdidos_adulto());
        odtodontograma.setSuperficiesManchadas(odontologiaTemp
                .getOdont_grama_porcentaje_manchado());
        odtodontograma.setTipoDenticion(odontologiaTemp.getModo_denticion());
        odtodontograma_evolucion.setTipoDenticion(odontologiaTemp
                .getModo_denticion());
    }

    public void onCreateVista() throws Exception {
		//log.info("Ejecutando metodo @onCreateVista()");
        // actualice la vista inicial de evolucion
        accionForm(OpcionesFormulario.REGISTRAR, null);
        inicializarProcedimientosRealizar();
        if (odontologiaTemp != null) {
            //log.info("@oncretae cargar la informacion de la his");
            mostrarHistoriaOdontologica();
            cargarProcedimientosRealizados();
            actualizarOdontogramaEvolucion();
            if (opcion_formulario.equals(OpcionesFormulario.MOSTRAR)) {
                FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
                        new String[]{"macroImpresion_diagnostica"});
                btCancel.setVisible(true);
                botonNuevo.setVisible(false);

                accionForm(OpcionesFormulario.CONSULTAR, null);
            } else {
                btCancel.setVisible(false);
            }
        } else {
            btGuardar.setDisabled(true);
            btCancel.setVisible(false);
        }
    }

    private void mostrarHistoriaOdontologica() throws Exception {
        Map map = new HashMap();
        map.put("codigo_empresa", codigo_empresa);
        map.put("codigo_sucursal", codigo_sucursal);
        map.put("evolucion", "N");
        map.put("nro_historia", odontologiaTemp.getCodigo_historia());

        /* cargamos odontograma */
        dientesOdontologia = getServiceLocator().getDienteService().listar(map);
        odtodontograma.setDientes(dientesOdontologia);
        /*
         * odtodontograma_evolucion.setTipoDenticion(odontologia
         * .getModo_denticion());
         */
		// odtodontograma_evolucion.habilitarSoloLosMarcadoPorHacer();

		// cargamos evolucion si existe
        Evolucion_odontologia evolucion_odontologia = new Evolucion_odontologia();
        evolucion_odontologia.setCodigo_empresa(admision.getCodigo_empresa());
        evolucion_odontologia.setCodigo_sucursal(admision.getCodigo_sucursal());
        evolucion_odontologia.setIdentificacion(admision
                .getNro_identificacion());
        evolucion_odontologia.setNro_ingreso(admision.getNro_ingreso());
        evolucion_odontologia = getServiceLocator()
                .getEvolucion_odontologiaService().consultarDesdeAdmision(
                        evolucion_odontologia);
		//log.info("@mostrarHistoriaOdontologica Evolucionn consultada: "
        //+ evolucion_odontologia);
        if (evolucion_odontologia != null) {
            mostrarDatos(evolucion_odontologia);
            cargarAnexo9_remision(evolucion_odontologia);
        } else {
            btGuardar.setDisabled(false);
            btGuardar.setVisible(true);
            btCancel.setVisible(false);
            Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
            impresion_diagnostica.setCodigo_empresa(codigo_empresa);
            impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
            impresion_diagnostica.setCodigo_historia(odontologiaTemp
                    .getCodigo_historia());
            impresion_diagnostica = getServiceLocator().getServicio(
                    Impresion_diagnosticaService.class).consultar(
                            impresion_diagnostica);
            if (primera_vez) {
                macroImpresion_diagnostica.mostrarImpresionDiagnostica(
                        impresion_diagnostica, true);
            }
            /*
             * odtodontograma_evolucion
             * .setDientes(existEvolucion(dientesOdontologia));
             */
        }
    }

    private List<Diente> existEvolucion(List<Diente> dientes) {
        /* Verificamos la evolucion */
        Evolucion_odontologia evolucion_odontologia = new Evolucion_odontologia();
        evolucion_odontologia.setCodigo_empresa(codigo_empresa);
        evolucion_odontologia.setCodigo_sucursal(codigo_sucursal);
        evolucion_odontologia.setCodigo_historia(odontologiaTemp
                .getCodigo_historia());
        evolucion_odontologia = getServiceLocator()
                .getEvolucion_odontologiaService().consultarUltimaEvolucion(
                        evolucion_odontologia);

        //log.info("Evolucion odontologia::: " + evolucion_odontologia);
        if (evolucion_odontologia != null) {
            Map map = new HashMap();
            map.put("codigo_empresa", codigo_empresa);
            map.put("codigo_sucursal", codigo_sucursal);
            map.put("evolucion", "S");
            map.put("nro_historia",
                    evolucion_odontologia.getCodigo_evolucion_historia());

			// cargamos los datos
            // esto fue requerimiento, que saliera en blanco
            // tbxMotivo_consulta.setValue(""
            // + evolucion_odontologia.getMotivo_consulta());
            // tbxEnfermedad_actual.setValue(""
            // + evolucion_odontologia.getEnfermedad_actual());
            // retornamos los valores.
            return getServiceLocator().getDienteService().listar(map);
        } else {
			// Esto fue requerimiento que saliera en blanco
            // tbxMotivo_consulta.setValue(""
            // + odontologiaTemp.getMotivo_consulta());
            // tbxEnfermedad_actual.setValue(""
            // + odontologiaTemp.getEnfermedad_actual());
            // tbxAcompaniante.setValue("" + odontologiaTemp.getAcompaniante());
            // for (int i = 0; i < lbxRelacion.getItemCount(); i++) {
            // Listitem listitem = lbxRelacion.getItemAtIndex(i);
            // if (listitem.getValue().toString()
            // .equals(odontologiaTemp.getRelacion())) {
            // listitem.setSelected(true);
            // i = lbxRelacion.getItemCount();
            // }
            // }
            // tbxTel_acompaniante.setValue(odontologiaTemp.getTel_acompaniante());

            // lbxRelacion
        }
        return dientes;
    }

    /**
     * Con este metodo especificamos, como va ha trabajar el odontograma
	 *
     */
    private void inicializarOdontograma() throws Exception {
        odtodontograma.showIndicedean(false);
        odtodontograma.showAccion_odontograma(false);
        odtodontograma.showIndiceDental(false);
        odtodontograma.showTipo_denticion(false);
        odtodontograma.setTitleOdontograma("ESTADO INICIAL - ODONTOGRAMA");
        odtodontograma.setDisabledDientes(true);

        /* Odontograma de evolucion */
        odtodontograma_evolucion.showIndicedean(false);
        odtodontograma_evolucion.showIndiceDental(false);
        odtodontograma_evolucion
                .setTitleOdontograma("EVOLUcion DEL TRATAMIENTO - ODONTOGRAMA");
        // odtodontograma_evolucion.showTipo_denticion(false);
    }

    public void listarCombos() {
        listarParameter();
        Utilidades
                .listarElementoListbox(lbxRelacion, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxPronostico, true,
                getServiceLocator());
    }

    public void seleccion_radio(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            // //System.Out.Println("" + radiogroup.getSelectedItem().getValue());

            for (AbstractComponent abstractComponent : abstractComponents) {

                if (radiogroup.getSelectedItem().getValue().equals("1")) {
                    abstractComponent.setVisible(true);
                    if (abstractComponent instanceof Datebox) {
                        ((Datebox) abstractComponent).setValue(new Date());

                    }

                    if (abstractComponent instanceof Radiogroup) {
                        ((Radio) ((Radiogroup) abstractComponent)
                                .getFirstChild()).setChecked(true);
                    }

                    if (abstractComponent instanceof Listbox) {
                        if (((Listbox) abstractComponent).getItemCount() > 0) {
                            ((Listbox) abstractComponent).setSelectedIndex(0);
                        }
                        Utilidades.listarElementoListbox(
                                ((Listbox) abstractComponent), true,
                                getServiceLocator());
                    }

                    if (abstractComponent instanceof Checkbox) {
                        ((Checkbox) abstractComponent).setChecked(false);
                    }

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setText("");
                        ((Textbox) abstractComponent).setVisible(true);

                    }

                    // textbox.setVisible(true);
                } else {
                    // label.setVisible(false);

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");

                    }

                    if (abstractComponent instanceof Radiogroup) {
                        ((Radio) ((Radiogroup) abstractComponent)
                                .getFirstChild()).setChecked(true);
                    }
                    if (abstractComponent instanceof Datebox) {
                        ((Datebox) abstractComponent).setValue(null);

                    }

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");

                    }

                    if (abstractComponent instanceof Listbox) {
                        ((Listbox) abstractComponent).getChildren().clear();
                        for (int i = 0; i < ((Listbox) abstractComponent)
                                .getItemCount(); i++) {
                            Listitem listitem = ((Listbox) abstractComponent)
                                    .getItemAtIndex(i);
                            if (listitem.getValue().toString().equals("")) {
                                listitem.setSelected(true);
                                break;
                            }
                        }

                    }
                    if (abstractComponent instanceof Checkbox) {
                        ((Checkbox) abstractComponent).setChecked(false);
                    }

                    abstractComponent.setVisible(false);
                }
            }
        } catch (Exception e) {
			//  block
            // //System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    public void seleccion_radio2(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            // //System.Out.Println("" + radiogroup.getSelectedItem().getValue());

            for (AbstractComponent abstractComponent : abstractComponents) {

                if (radiogroup.getSelectedItem().getValue().equals("1")) {
                    abstractComponent.setVisible(true);

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");
                    }

                    // textbox.setVisible(true);
                } else {

                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");

                    }

                    abstractComponent.setVisible(false);
                }
            }
        } catch (Exception e) {
			//  block
            // //System.Out.Println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("codigo_evolucion_historia");
        listitem.setLabel("Codigo Evolucion");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("creacion_date::varchar");
        listitem.setLabel("Fecha");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

	// Accion del formulario si es nuevo o consultar //
    // public void accionForm(boolean sw, String accion) throws Exception {
    // groupboxConsulta.setVisible(!sw);
    // groupboxEditar.setVisible(sw);
    //
    // if (!accion.equalsIgnoreCase("registrar")) {
    // buscarDatos();
    // } else {
    // // buscarDatos();
    // limpiarDatos();
    // }
    // tbxAccion.setValue(accion);
    // }
    public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
            throws Exception {
        tbxAccion.setValue(opciones_formulario.toString());
        if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
            onOpcionFormularioRegistrar();
        } else if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)
                || opciones_formulario.equals(OpcionesFormulario.MOSTRAR)) {
            groupboxConsulta.setVisible(false);
            groupboxEditar.setVisible(true);
            mostrarDatos(dato);
            if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {
                FormularioUtil.deshabilitarComponentes(groupboxEditar, false,
                        idsExcluyentes);
            }
        } else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
            onOpcionFormularioConsultar();
        }

    }

    private void onOpcionFormularioConsultar() throws Exception {
        groupboxConsulta.setVisible(true);
        groupboxEditar.setVisible(false);
        buscarDatos();
    }

    private void onOpcionFormularioRegistrar() throws Exception {
        groupboxConsulta.setVisible(false);
        groupboxEditar.setVisible(true);
        limpiarDatos();
        if (admision != null) {
            cargarInformacion_paciente();
            onMostrarModuloFarmacologico();
            receta_ripsAction.obtenerBotonAgregar().setVisible(true);
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(true);
            onMostrarModuloRemisiones();
        }
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        if (odtodontograma_evolucion != null && odontologiaTemp != null) {
            odtodontograma_evolucion.resetOdontograma();
            odtodontograma_evolucion.setTipoDenticion(odontologiaTemp
                    .getModo_denticion());

            List<Diente> dientes = existEvolucion(dientesOdontologia);

            if (dientes != null) {
				// solo se pueden cargar los dientes que esten ausentes o
                // extraidos
                List<Diente> dientesAusenteOExtraido = new ArrayList<Diente>();
                for (Diente diente : dientes) {
                    if (diente.getSector_superior().equals(
                            TypeSector.EXTRAIDO.toString())
                            || diente.getSector_superior().equals(
                                    TypeSector.AUSENTE.toString())) {
                        dientesAusenteOExtraido.add(diente);
                    }
                }

                // cargamos solos los dientes ausentes o extraidos
                if (!dientesAusenteOExtraido.isEmpty()) {
                    odtodontograma_evolucion
                            .setDientes(dientesAusenteOExtraido);
                }
				// odtodontograma_evolucion.setDientes(
                // existEvolucion(dientesOdontologia);
                // );
            }
        } else {
            groupboxConsulta.setVisible(true);
            groupboxEditar.setVisible(false);
        }
        btGuardar.setDisabled(false);
        cargarPrestador();
        nro_ingreso_temp = nro_ingreso;
    }

    private void actualizarOdontogramaEvolucion() throws Exception {
        odtodontograma_evolucion.resetOdontograma();
        odtodontograma_evolucion.setTipoDenticion(odontologiaTemp
                .getModo_denticion());

        List<Diente> dientes = existEvolucion(dientesOdontologia);

        if (dientes != null) {
            // solo se pueden cargar los dientes que esten ausentes o extraidos
            List<Diente> dientesAusenteOExtraido = new ArrayList<Diente>();
            for (Diente diente : dientes) {
                if (diente.getSector_superior().equals(
                        TypeSector.EXTRAIDO.toString())
                        || diente.getSector_superior().equals(
                                TypeSector.AUSENTE.toString())) {
                    dientesAusenteOExtraido.add(diente);
                }
            }

            // cargamos solos los dientes ausentes o extraidos
            if (!dientesAusenteOExtraido.isEmpty() && primera_vez) {
                odtodontograma_evolucion.setDientes(dientesAusenteOExtraido);
            } else if (!primera_vez && !dientes.isEmpty()) {
                odtodontograma_evolucion.setDientes(dientes);
            }
			// odtodontograma_evolucion.setDientes(
            // existEvolucion(dientesOdontologia);
            // );
        }

        if (primera_vez) {
            if (odontologiaTemp != null) {
                tbxMotivo_consulta.setValue(""
                        + odontologiaTemp.getMotivo_consulta());
                tbxEnfermedad_actual.setValue(""
                        + odontologiaTemp.getEnfermedad_actual());

                // Actualiza los campos del acompañanate
                tbxAcompaniante.setValue(odontologiaTemp.getAcompaniante());
                tbxTel_acompaniante.setValue(odontologiaTemp
                        .getTel_acompaniante());
                Utilidades.setValueFrom(lbxRelacion,
                        odontologiaTemp.getRelacion());
                // cargarImpresionDiagnostica(odontologiaTemp);
            }
        }
    }

	// private void cargarImpresionDiagnostica(Odontologia odontologia)
    // throws Exception {
    // Impresion_diagnostica impresion_diagnostica = new
    // Impresion_diagnostica();
    // impresion_diagnostica.setCodigo_empresa(codigo_empresa);
    // impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
    // impresion_diagnostica.setCodigo_historia(odontologia.getCodigo_historia());
    // impresion_diagnostica = getServiceLocator().getServicio(
    // Impresion_diagnosticaService.class).consultar(
    // impresion_diagnostica);
    // macroImpresion_diagnostica.mostrarImpresionDiagnostica(
    // impresion_diagnostica, true);
    // }
    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        tbxProcedimiento_realizado
                .setStyle("text-transform:uppercase;background-color:white");
        tbxCodigo_prestadorEvolucion
                .setStyle("text-transform:uppercase;background-color:white");
        tbxMotivo_consulta
                .setStyle("text-transform:uppercase;background-color:white");
        tbxEnfermedad_actual
                .setStyle("text-transform:uppercase;background-color:white");

        boolean valida = true;
        String msj = "Los campos marcados con (*) son obligatorios";

        if (tbxMotivo_consulta.getText().trim().equals("")) {
            tbxMotivo_consulta
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            Clients.scrollIntoView(tbxMotivo_consulta);
            valida = false;
        }

        if (tbxEnfermedad_actual.getText().trim().equals("") && valida) {
            tbxEnfermedad_actual
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            Clients.scrollIntoView(tbxEnfermedad_actual);
            valida = false;
        }

        if (tbxCodigo_prestadorEvolucion.getText().trim().equals("") && valida) {
            tbxCodigo_prestadorEvolucion
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            Clients.scrollIntoView(tbxCodigo_prestadorEvolucion);
            valida = false;
        }

        if (tbxProcedimiento_realizado.getText().trim().equals("") && valida) {
            tbxProcedimiento_realizado
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            Clients.scrollIntoView(tbxProcedimiento_realizado);
            valida = false;
        }

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres()
                    + " recuerde que...", msj);
        }

        if (admision.getPrimera_vez().equalsIgnoreCase("N")) {
            if (valida) {
                valida = receta_ripsAction.validarFormExterno();
            }
        }

        if (valida) {
            valida = orden_servicioAction.validarFormExterno();
        }

        validarProcedimientosRealizados();

        if (valida) {
            valida = remisiones_externasAction.validarRemision();
        }

        return valida;
    }

    public void validarProcedimientosRealizados() throws WrongValueException {
        Set<Listitem> listado_seleccionados = lbxProcedimientoPorRealizar
                .getSelectedItems();
        if (!listado_seleccionados.isEmpty()) {
            for (Listitem listitem : listado_seleccionados) {
                Plan_tratamiento plan_tratamiento = (Plan_tratamiento) listitem
                        .getValue();
                Intbox intbox_realizadas = (Intbox) listitem
                        .getAttribute("intbox_realizadas");
                if (intbox_realizadas.getValue() != null) {
                    if (intbox_realizadas.getValue() > plan_tratamiento
                            .getUnidades()) {
                        throw new WrongValueException(
                                intbox_realizadas,
                                "La cantidad de procedimientos realizados para el procedimiento "
                                + plan_tratamiento
                                .getCodigo_procedimiento()
                                + " es mayor que la cantidad de procediminetos ordenados");
                    }
                } else {
                    throw new WrongValueException(intbox_realizadas,
                            "Debe digitar la cantidad de procedimientos realizados para el procedimiento "
                            + plan_tratamiento
                            .getCodigo_procedimiento());
                }

                if (plan_tratamiento.getUnidades_realizadas() == null) {
                    throw new WrongValueException(
                            intbox_realizadas,
                            "Debe digitar la cantidad de procedimientos por realizar para el procedimiento "
                            + plan_tratamiento
                            .getCodigo_procedimiento());
                } else if (plan_tratamiento.getUnidades_realizadas().intValue() < 0) {
                    throw new WrongValueException(intbox_realizadas,
                            "la cantidad de procedimientos por realizar para el procedimiento "
                            + plan_tratamiento
                            .getCodigo_procedimiento()
                            + " debe ser mayor que cero");
                }
            }
        }
    }

    // Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue()
                    .toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            parameters.put("identificacion", admision.getNro_identificacion());

            if (lbxParameter.getSelectedIndex() > 0) {
                parameters.put("parameter", parameter);
                parameters.put("value", value);
            } else if (value != null && !value.isEmpty()) {
                try {
                    Integer codigo = Integer.parseInt(value);
                    parameters.put("codigo_evolucion_historia", codigo);
                } catch (NumberFormatException nfe) {
                    MensajesUtil
                            .mensajeAlerta("Alerta", "El código de evolucion digitado, no es un número válido!");
                }
            }

            parameters.put("limite_paginado", "limit 25 offset 0");

            List<Evolucion_odontologia> lista_datos = getServiceLocator()
                    .getEvolucion_odontologiaService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Evolucion_odontologia evolucion_odontologia : lista_datos) {
                rowsResultado.appendChild(crearFilas(evolucion_odontologia,
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

        final Evolucion_odontologia evolucion_odontologia = (Evolucion_odontologia) objeto;

        String nombre_medico = "MEDICO NO REGISTRADO";

        Prestadores prestadores = new Prestadores();
        prestadores.setCodigo_empresa(admision.getCodigo_empresa());
        prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
        prestadores.setNro_identificacion(evolucion_odontologia
                .getCodigo_prestador());
        prestadores = getServiceLocator().getServicio(PrestadoresService.class)
                .consultar(prestadores);

        if (prestadores != null) {
            nombre_medico = "" + prestadores.getNro_identificacion() + "-"
                    + prestadores.getApellidos() + " "
                    + prestadores.getNombres();
        }

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(evolucion_odontologia
                .getCodigo_evolucion_historia() + ""));
        fila.appendChild(new Label(nombre_medico + ""));
        fila.appendChild(new Label(evolucion_odontologia
                .getProcedimiento_realizado() + ""));
        fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd hh:mm a")
                .format(evolucion_odontologia.getCreacion_date())));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(evolucion_odontologia);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        img.setSrc("/images/borrar.gif");
        img.setTooltiptext("Eliminar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                Messagebox.show(
                        "Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener<Event>() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    eliminarDatos(evolucion_odontologia);
                                    buscarDatos();
                                }
                            }
                        });
            }
        });
		// hbox.appendChild(space);
        // hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    private Evolucion_odontologia getBean() {
        Evolucion_odontologia evolucion_odontologia = new Evolucion_odontologia();
        evolucion_odontologia.setCodigo_empresa(empresa
                .getCodigo_empresa());
        evolucion_odontologia
                .setCodigo_sucursal(sucursal
                        .getCodigo_sucursal());
        evolucion_odontologia
                .setCodigo_evolucion_historia(tbxCodigo_evolucion_historia
                        .getValue());
        evolucion_odontologia
                .setCodigo_historia(odontologiaTemp
                        .getCodigo_historia());
		//log.info("===> cod historia "
        //+ odontologiaTemp.getCodigo_historia());
        evolucion_odontologia
                .setProcedimiento_realizado(tbxProcedimiento_realizado
                        .getValue());
        evolucion_odontologia
                .setCreacion_date(new Timestamp(
                                new GregorianCalendar()
                                .getTimeInMillis()));
        evolucion_odontologia
                .setUltimo_update(new Timestamp(
                                new GregorianCalendar()
                                .getTimeInMillis()));
        evolucion_odontologia.setCreacion_user(usuarios
                .getCodigo().toString());
        evolucion_odontologia.setUltimo_user(usuarios
                .getCodigo().toString());
        evolucion_odontologia
                .setIdentificacion(odontologiaTemp
                        .getIdentificacion());
        evolucion_odontologia
                .setFecha_inicial(odontologiaTemp
                        .getFecha_inicial());
        evolucion_odontologia
                .setNro_ingreso(nro_ingreso_temp);
        evolucion_odontologia
                .setCodigo_prestador(tbxCodigo_prestadorEvolucion
                        .getValue());
        evolucion_odontologia
                .setFecha_ingreso(new Timestamp(
                                dtbxFecha_ingreso.getValue()
                                .getTime()));
        evolucion_odontologia
                .setAcompaniante(tbxAcompaniante
                        .getValue());
        evolucion_odontologia.setRelacion(lbxRelacion
                .getSelectedItem().getValue()
                .toString());
        evolucion_odontologia
                .setTel_acompaniante(tbxTel_acompaniante
                        .getValue());
        evolucion_odontologia.setMotivo_consulta(Util
                .agregarComillas(tbxMotivo_consulta
                        .getValue()));
        evolucion_odontologia
                .setEnfermedad_actual(tbxEnfermedad_actual
                        .getValue());

        // agregamos los nuevos cambios de evolucion
        evolucion_odontologia
                .setPronostico(lbxPronostico
                        .getSelectedItem().getValue()
                        .toString());
        evolucion_odontologia
                .setEstrategia_casa(rdbEstrategia_casa
                        .getSelectedItem().getValue()
                        .toString());
        evolucion_odontologia.setUsa_seda(rdbUsa_seda
                .getSelectedItem().getValue()
                .toString());
        evolucion_odontologia
                .setUsa_enjuague(rdbUsa_enjuague
                        .getSelectedItem().getValue()
                        .toString());
        evolucion_odontologia
                .setConsulta_odontologo(rdbConsulta_odontologo
                        .getSelectedItem().getValue()
                        .toString());
        evolucion_odontologia.setTotal_si(tbxTotal_si
                .getValue());
        evolucion_odontologia.setTotal_no(tbxTotal_no
                .getValue());
        return evolucion_odontologia;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {

        FormularioUtil.setUpperCase(groupboxEditar);
        if (validarForm()) {
            // Cargamos los componentes //
            Messagebox.show(getUsuarios().getNombres()
                    + " estas seguro de realizar esta accion ?",
                    "Informacion ..", Messagebox.YES + Messagebox.NO,
                    Messagebox.INFORMATION, new EventListener() {

                        @Override
                        public void onEvent(Event event) throws Exception {
                            try {
                                Map<String, Object> datos = new HashMap<String, Object>();
                                datos.put("evolucion", getBean());
                                datos.put("dientes",
                                        odtodontograma_evolucion.getDientes());
                                datos.put("accion", tbxAccion.getText());
                                datos.put("admision", admision);
                                datos.put(PARAM_AUTORIZACION,
                                        obtenerDatosAutorizacion());
                                datos.put("procedimientos_realizados",
                                        getProcedimientosRealizar());
                                datos.put("odontologia", odontologiaTemp);
                                Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
                                .obtenerImpresionDiagnostica();
                                datos.put("impresion_diagnostica",
                                        impresion_diagnostica);
                                receta_ripsAction
                                .actualizarDiagnosticos(impresion_diagnostica);

                                final Map<String, Object> mapReceta = receta_ripsAction
                                .obtenerDatos();
                                final Map<String, Object> mapProcedimientos = orden_servicioAction
                                .obtenerDatos();
                                datos.put("receta_medica", mapReceta);
                                datos.put("orden_servicio", mapProcedimientos);

                                final Anexo9_entidad anexo9_entidad = remisiones_externasAction
                                .obtenerAnexo9();

                                datos.put("anexo9", anexo9_entidad);
                                Remision_interna remision_interna = macroRemision_interna
                                .obtenerRemisionInterna();
                                datos.put("remision_interna", remision_interna);

                                datos.put("primera_vez", primera_vez);
                                datos.put("sucursal", getSucursal());
                                datos.put("usuarios", getUsuarios());
                                datos.put("rol_usuario", getRol_usuario());

                                Parametros_empresa parametros_empresa = new Parametros_empresa();
                                parametros_empresa
                                .setCodigo_empresa(codigo_empresa);
                                parametros_empresa = getServiceLocator()
                                .getServicio(GeneralExtraService.class)
                                .consultar(parametros_empresa);

                                datos.put("parametros_empresa",
                                        parametros_empresa);

                                tbxCodigo_evolucion_historia.setValue(getServiceLocator()
                                        .getEvolucion_odontologiaService()
                                        .guardar(datos)
                                        .getCodigo_evolucion_historia());

                                btGuardar.setDisabled(true);
                                tbxAccion.setValue("modificar");

                                MensajesUtil
                                .mensajeInformacion("Informacion ..",
                                        "Los datos se guardaron satisfactoriamente", new EventListener<Event>() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        cargarProcedimientosRealizados();
                                        Receta_rips receta_rips = (Receta_rips) mapReceta
                                        .get("receta_rips");
                                        if (receta_rips != null) {
                                            receta_ripsAction.mostrarDatos(
                                                    receta_rips, false);
                                            receta_ripsAction.validarParaImpresion();
                                        }
                                        if (!primera_vez) {
                                            Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
                                            .get("orden_servicio");
                                            if (orden_servicio != null) {
                                                orden_servicioAction
                                                .mostrarDatos(orden_servicio);
                                            }

                                            if (anexo9_entidad != null) {
                                                remisiones_externasAction
                                                .setCodigo_remision(anexo9_entidad
                                                        .getCodigo());
                                                remisiones_externasAction
                                                .getBotonImprimir()
                                                .setDisabled(false);
                                            }
                                            orden_servicioAction.validarParaImpresion();
                                            remisiones_externasAction.validarRemision();
                                        }
                                    }
                                });
                            } catch (ImpresionDiagnosticaException e) {
                                log.error(e.getMessage(), e);
                            } catch (Exception e) {
                                MensajesUtil.mensajeError(e, "modificar",
                                        Evolucion_odontologiaAction.this);
                            }
                        }
                    });
        }
    }

    private List<Map<String, Object>> getProcedimientosRealizar() {
        Set<Listitem> listitems = lbxProcedimientoPorRealizar
                .getSelectedItems();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for (Listitem listitem : listitems) {
            Plan_tratamiento plan_tratamiento = listitem.getValue();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("plan", plan_tratamiento);

            // Cargamos las cantidades realizadas
            Intbox intboxCantidad = (Intbox) listitem
                    .getAttribute("intbox_realizadas");
            map.put("cantidad", intboxCantidad.getValue());

            map.put("nuevo", !intboxCantidad.isReadonly());
            listMap.add(map);
        }
        return listMap;
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Evolucion_odontologia evolucion_odontologia = (Evolucion_odontologia) obj;
        try {
            btGuardar.setDisabled(true);
            Admision admision = new Admision();
            admision.setCodigo_empresa(evolucion_odontologia
                    .getCodigo_empresa());
            admision.setCodigo_sucursal(evolucion_odontologia
                    .getCodigo_sucursal());
            admision.setNro_identificacion(evolucion_odontologia
                    .getIdentificacion());
            admision.setNro_ingreso(evolucion_odontologia.getNro_ingreso());
            admision = getServiceLocator().getServicio(AdmisionService.class)
                    .consultar(admision);

            if (admision == null) {
                Notificaciones
                        .mostrarNotificacionAlerta(
                                "Advertencia",
                                "La admision que corresponde a esta historia no existe",
                                3000);
            } else {
                accionPrimeraVezControl();
                cargarProcedimientosRealizados();
            }

            tbxCodigo_evolucion_historia.setValue(evolucion_odontologia
                    .getCodigo_evolucion_historia());
            tbxProcedimiento_realizado.setValue(evolucion_odontologia
                    .getProcedimiento_realizado());
            nro_ingreso_temp = (evolucion_odontologia.getNro_ingreso());
            tbxCodigo_prestadorEvolucion.setValue(evolucion_odontologia
                    .getCodigo_prestador());
            dtbxFecha_ingreso
                    .setValue(evolucion_odontologia.getFecha_ingreso());
            tbxAcompaniante.setValue(evolucion_odontologia.getAcompaniante());
            Utilidades.setValueFrom(lbxRelacion,
                    evolucion_odontologia.getRelacion());
            tbxTel_acompaniante.setValue(evolucion_odontologia
                    .getTel_acompaniante());
            tbxMotivo_consulta.setValue(evolucion_odontologia
                    .getMotivo_consulta());
            tbxEnfermedad_actual.setValue(evolucion_odontologia
                    .getEnfermedad_actual());
            // --------------campos nuevos----------------------
            Utilidades.seleccionarRadio(rdbEstrategia_casa,
                    evolucion_odontologia.getEstrategia_casa());
            Utilidades.seleccionarRadio(rdbUsa_seda,
                    evolucion_odontologia.getUsa_seda());
            Utilidades.seleccionarRadio(rdbUsa_enjuague,
                    evolucion_odontologia.getUsa_enjuague());
            Utilidades.seleccionarRadio(rdbConsulta_odontologo,
                    evolucion_odontologia.getConsulta_odontologo());
            tbxTotal_si.setValue(evolucion_odontologia.getTotal_si());
            tbxTotal_no.setValue(evolucion_odontologia.getTotal_no());
            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(codigo_empresa);
            prestadores.setCodigo_sucursal(codigo_sucursal);
            prestadores.setNro_identificacion(evolucion_odontologia
                    .getCodigo_prestador());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);
            if (prestadores != null) {
                tbxCodigo_prestadorEvolucion.seleccionarRegistro(prestadores,
                        prestadores.getNro_identificacion(),
                        prestadores.getNombres());
            }

            onMostrarModuloFarmacologico();
            receta_ripsAction.obtenerBotonAgregar().setVisible(false);
            onMostrarModuloOrdenamiento();
            orden_servicioAction.obtenerBotonAgregar().setVisible(false);
            onMostrarModuloRemisiones();
            cargarRemisionInterna(evolucion_odontologia);
            /* Mostar odontograma */
            odtodontograma_evolucion.resetOdontograma();
            Map map = new HashMap();
            map.put("codigo_empresa", codigo_empresa);
            map.put("codigo_sucursal", codigo_sucursal);
            map.put("evolucion", "S");
            map.put("nro_historia",
                    evolucion_odontologia.getCodigo_evolucion_historia());

            /* cargamos odontograma */
            List<Diente> dientes = getServiceLocator().getDienteService()
                    .listar(map);
            odtodontograma_evolucion.setDientes(dientes);
            btGuardar.setDisabled(true);
            // Mostramos la vista //
            tbxAccion.setText("modificar");
            groupboxConsulta.setVisible(false);
            groupboxEditar.setVisible(true);

            Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
            impresion_diagnostica.setCodigo_empresa(codigo_empresa);
            impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
            impresion_diagnostica.setCodigo_historia(evolucion_odontologia
                    .getCodigo_historia());
            impresion_diagnostica = getServiceLocator().getServicio(
                    Impresion_diagnosticaService.class).consultar(
                            impresion_diagnostica);

            macroImpresion_diagnostica.mostrarImpresionDiagnostica(
                    impresion_diagnostica, true);

            cargarAnexo9_remision(evolucion_odontologia);
            // calcularTotal();
            btGuardar.setDisabled(true);
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Evolucion_odontologia evolucion_odontologia = (Evolucion_odontologia) obj;
        try {
            evolucion_odontologia.setDelete_user(getUsuarios().getCodigo());
            int result = getServiceLocator().getEvolucion_odontologiaService()
                    .eliminar(evolucion_odontologia);
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

    public void onMostrarModuloFarmacologico() throws Exception {
        if (receta_ripsAction != null) {
            receta_ripsAction.detach();
        }
        divModuloFarmacologico.getChildren().clear();
        //log.info("creando la ventana receta_ripsAction");
        Map parametros = new HashMap();
        parametros.put("nro_identificacion", admision.getNro_identificacion());
        parametros.put("nro_ingreso", admision.getNro_ingreso());
        parametros.put("estado", admision.getEstado());
        parametros.put("codigo_administradora",
                admision.getCodigo_administradora());
        parametros.put("id_plan", admision.getId_plan());
        parametros.put("tipo_hc", "");
        parametros.put("ocultaValor", "");
        parametros.put("admision", admision);
        parametros.put("opcion_formulario", tbxAccion.getValue());
        /* este parametros es para que oculte las vistas */
        parametros.put("ocultarInformacion", "_Ocultar");

        parametros.put("ocultarRecomendaciones", "_Ocultar");

        receta_ripsAction = (Receta_rips_internoAction) Executions
                .createComponents("/pages/receta_rips_interno.zul", null,
                        parametros);
        receta_ripsAction.inicializar(this);
        divModuloFarmacologico.appendChild(receta_ripsAction);
    }

    public void onMostrarModuloOrdenamiento() throws Exception {
        if (orden_servicioAction != null) {
            orden_servicioAction.detach();
        }
        divModuloOrdenamiento.getChildren().clear();
		// if (!cargo_farmacologico) {
        //log.info("creando la ventana receta_ripsAction");
        Map parametros = new HashMap();
        parametros.put("nro_identificacion", admision.getNro_identificacion());
        parametros.put("nro_ingreso", admision.getNro_ingreso());
        parametros.put("estado", admision.getEstado());
        parametros.put("codigo_administradora",
                admision.getCodigo_administradora());
        parametros.put("id_plan", admision.getId_plan());
        parametros.put("tipo_hc", "");
        parametros.put("ocultaValor", "");
        parametros.put("admision", admision);
        parametros.put("opcion_formulario", tbxAccion.getValue());
        /* este parametros es para que oculte las vistas */
        parametros.put("ocultarInformacion", "_Ocultar");
        orden_servicioAction = (Orden_servicio_internoAction) Executions
                .createComponents("/pages/orden_servicio_interno.zul", null,
                        parametros);
        orden_servicioAction.inicializar(this);
        divModuloOrdenamiento.appendChild(orden_servicioAction);
        orden_servicioAction.obtenerBotonImprimir().setVisible(false);
    }

    public void inicializarProcedimientosRealizar() throws Exception {
		//log.info("Ejectando metodo @inicializarProcedimientosRealizar() ===> "
        //+ lista_procedimientos);
        if (lista_procedimientos != null) {
            for (int i = 0; i < lista_procedimientos.size(); i++) {
                Map<String, Object> mapa = lista_procedimientos.get(i);
                String codigo_procedimiento = mapa.get("codigo").toString();
                Integer cantidad = null;
                if (mapa.containsKey("cantidad")) {
                    cantidad = (Integer) mapa.get("cantidad");
                }
                Map<String, Object> pcd = Utilidades.getProcedimiento(
                        ServiciosDisponiblesUtils
                        .getManuales_tarifarios(admision),
                        ConvertidorDatosUtil
                        .convertirDatoLong(codigo_procedimiento),
                        getServiceLocator());
                if (cantidad != null) {
                    pcd.put("CANTIDAD_INICIALIZAR", cantidad);
                }
                onSeleccionar(pcd);
            }
        }
    }

    private void cargarRemisionInterna(
            Evolucion_odontologia evolucion_odontologia) throws Exception {
        Remision_interna remision_interna = new Remision_interna();
        remision_interna.setCodigo_historia(evolucion_odontologia
                .getCodigo_evolucion_historia());
        remision_interna.setCodigo_empresa(evolucion_odontologia
                .getCodigo_empresa());
        remision_interna.setCodigo_sucursal(evolucion_odontologia
                .getCodigo_sucursal());

        remision_interna = getServiceLocator().getServicio(
                Remision_internaService.class).consultar(remision_interna);

        macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
    }

    private void cargarAnexo9_remision(
            Evolucion_odontologia evolucion_odontologia) {
        Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
        anexo9_entidad.setCodigo_empresa(codigo_empresa);
        anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
        anexo9_entidad.setNro_historia(evolucion_odontologia
                .getCodigo_evolucion_historia());
        anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
        anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
                .consultar(anexo9_entidad);
        boolean creada = false;
        if (anexo9_entidad != null) {
            creada = true;
        }
        remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
        if (creada) {
            remisiones_externasAction.getBotonGenerar().setVisible(false);
        }
        remisiones_externasAction.setNro_historia(evolucion_odontologia
                .getCodigo_historia());
    }

    public void onMostrarModuloRemisiones() throws Exception {
        if (remisiones_externasAction != null) {
            remisiones_externasAction.detach();
        }
        //log.info("creando la ventana receta_ripsAction");
        Map parametros = new HashMap();
        parametros.put("nro_identificacion", admision.getNro_identificacion());
        parametros.put("nro_ingreso", admision.getNro_ingreso());
        parametros.put("estado", admision.getEstado());
        parametros.put("codigo_administradora",
                admision.getCodigo_administradora());
        parametros.put("id_plan", admision.getId_plan());
        parametros.put("tipo_hc", "");
        parametros.put("ocultaValor", "");
        parametros.put("admision", admision);
        parametros.put("opcion_formulario", tbxAccion.getValue());
        /* este parametros es para que oculte las vistas */
        parametros.put("ocultarInformacion", "_Ocultar");

        parametros.put("ocultarRecomendaciones", "_Ocultar");

        parametros.put("opcion_formulario_historia", "MOSTRAR");

        remisiones_externasAction = (Remisiones_externasAction) Executions
                .createComponents("/pages/remisiones_externas.zul", null,
                        parametros);
        remisiones_externasAction.inicializar(this);
        divModuloRemisiones_externas.appendChild(remisiones_externasAction);

    }

    @Override
    public void adicionarPcd(Map<String, Object> pcd) throws Exception {
        Long id_procedimiento = (Long) pcd.get("id_procedimiento");

        Integer CANTIDAD_INICIALIZAR = (Integer) pcd
                .get("CANTIDAD_INICIALIZAR");

        Plan_tratamiento plan_tratamiento = new Plan_tratamiento();
        plan_tratamiento.setCodigo_empresa(codigo_empresa);
        plan_tratamiento.setCodigo_sucursal(codigo_sucursal);
        plan_tratamiento
                .setCodigo_procedimiento(id_procedimiento != null ? id_procedimiento
                        + ""
                        : "");

        Double doubleValorpcdTemp = (Double) pcd.get("valor_pcd");
        if (doubleValorpcdTemp == null) {
            doubleValorpcdTemp = (Double) pcd.get("valor_procedimiento");
        }
        plan_tratamiento.setValor_procedimiento(doubleValorpcdTemp);
        plan_tratamiento.setDescuento((Double) pcd.get("descuento"));
        plan_tratamiento.setIncremento((Double) pcd.get("incremento"));
        plan_tratamiento.setValor_real((Double) pcd.get("valor_real"));

        if (CANTIDAD_INICIALIZAR != null) {
            plan_tratamiento.setUnidades(CANTIDAD_INICIALIZAR);
        } else {
            plan_tratamiento.setUnidades(0);
        }

        plan_tratamiento.setUnidades_realizadas(0);
        plan_tratamiento.setCodigo_cups((String) pcd.get("codigo_cups"));
        plan_tratamiento.setRealizado("N");
        plan_tratamiento
                .setNro_identificacion(admision.getNro_identificacion());
        plan_tratamiento.setTipo_procedimiento((String) pcd
                .get("tipo_procedimiento"));

        lbxProcedimientoPorRealizar
                .appendChild(crearListItemProcedimientoPorRealizar(
                                plan_tratamiento, true, CANTIDAD_INICIALIZAR));
        seleccionados.add(plan_tratamiento.getCodigo_procedimiento());

		//log.info("Tipo procedimiento: "
        //+ plan_tratamiento.getTipo_procedimiento());
        Integer frecuencia = (Integer) pcd.get("frecuencia");

        String nombre_pcd = (String) pcd.get("nombre_procedimiento");

        ManejadorPoblacion.validarFrecuenciaOrdenamiento(plan_tratamiento
                .getCodigo_cups(), frecuencia != null ? frecuencia : 0,
                nombre_pcd, admision, getServiceLocator());
    }

    @Override
    public void onSeleccionar(Map<String, Object> pcd) throws Exception {
        //log.info("onSeleccionar");
        adicionarPcd(pcd);
    }

    @Override
    public String getInformacionClinica() {
        StringBuffer informacion_clinica = new StringBuffer();
        /* */
        informacion_clinica.append("- MOTIVO DE CONSULTA :");
        informacion_clinica.append("\t")
                .append("'" + tbxMotivo_consulta.getValue() + "'").append("\n");

        informacion_clinica.append("\n");

        informacion_clinica.append("- ENFERMEDAD ACTUAL :");
        if (tbxEnfermedad_actual.getValue() != null
                && !tbxEnfermedad_actual.getValue().isEmpty()) {
            informacion_clinica.append("\t")
                    .append(tbxEnfermedad_actual.getValue()).append("\n");
        }

        informacion_clinica.append("\n");

        informacion_clinica.append("- DIAGNOSTICOS").append("\n");
        Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
                .obtenerImpresionDiagnostica();
        Cie cie = new Cie();
        cie.setCodigo(impresion_diagnostica.getCie_principal());
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

        informacion_clinica
                .append("\tDiagnostico principal: ")
                .append(impresion_diagnostica.getCie_principal())
                .append(" ")
                .append(cie != null ? cie.getNombre() : "")
                .append("\t")
                .append("\tTipo: ")
                .append(Utilidades.getNombreElemento(
                                impresion_diagnostica.getTipo_principal(),
                                "tipo_diagnostico", this)).append("\n");

        /* relacionado 1 */
        if (!impresion_diagnostica.getCie_relacionado1().isEmpty()) {
            cie = new Cie();
            cie.setCodigo(impresion_diagnostica.getCie_relacionado1());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            informacion_clinica
                    .append("\tRelacionado 1: ")
                    .append(impresion_diagnostica.getCie_relacionado1())
                    .append(" ")
                    .append(cie != null ? cie.getNombre() : "")
                    .append("\t")
                    .append("\tTipo: ")
                    .append(Utilidades.getNombreElemento(
                                    impresion_diagnostica.getTipo_relacionado1(),
                                    "tipo_diagnostico", this)).append("\n");
        }
        if (!impresion_diagnostica.getCie_relacionado2().isEmpty()) {

            /* relacionado 2 */
            cie = new Cie();
            cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            informacion_clinica
                    .append("\tRelacionado 2: ")
                    .append(impresion_diagnostica.getCie_relacionado2())
                    .append(" ")
                    .append(cie != null ? cie.getNombre() : "")
                    .append("\t")
                    .append("\tTipo: ")
                    .append(Utilidades.getNombreElemento(
                                    impresion_diagnostica.getTipo_relacionado2(),
                                    "tipo_diagnostico", this)).append("\n");

        }
        if (!impresion_diagnostica.getCie_relacionado3().isEmpty()) {

            cie = new Cie();
            cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            informacion_clinica
                    .append("\tRelacionado 3: ")
                    .append(impresion_diagnostica.getCie_relacionado3())
                    .append(" ")
                    .append(cie != null ? cie.getNombre() : "")
                    .append("\t")
                    .append("\tTipo: ")
                    .append(Utilidades.getNombreElemento(
                                    impresion_diagnostica.getTipo_relacionado3(),
                                    "tipo_diagnostico", this)).append("\n")
                    .append("\n");
        }

		// informacion_clinica.append("-PLANES Y MANEJO:").append("\n");
        //
        // Map<String, Object> mapReceta = receta_ripsAction.obtenerDatos();
        // List<Detalle_receta_rips> listaDetalles_horarios =
        // (List<Detalle_receta_rips>) mapReceta
        // .get("lista_detalle");
        //
        // informacion_clinica.append("RECETA:").append("\t");
        //
        // for (Detalle_receta_rips detalle_receta_rips :
        // listaDetalles_horarios) {
        // Articulo articulo = new Articulo();
        // articulo.setCodigo_empresa(detalle_receta_rips.getCodigo_empresa());
        // articulo.setCodigo_sucursal(detalle_receta_rips
        // .getCodigo_sucursal());
        // articulo.setCodigo_articulo(detalle_receta_rips
        // .getCodigo_articulo());
        //
        // articulo = getServiceLocator().getArticuloService().consultar(
        // articulo);
        // informacion_clinica
        // .append(detalle_receta_rips.getCantidad_recetada())
        // .append(articulo != null ? articulo.getNombre1() : "")
        // .append(", ").append(detalle_receta_rips.getPosologia())
        // .append("\t");
        //
        // }
        informacion_clinica.append("\n");
		// Manuales_tarifarios manuales_tarifarios =
        // ServiciosDisponiblesUtils.getManuales_tarifarios(admision_seleccionada);
        // Map<String, Object> mapOrdenamiento = orden_servicioAction
        // .obtenerDatos();
        // //log.info("mapa ordenamiento ===> " +mapOrdenamiento);
        // List<Detalle_orden> listaDetalle_orden =(List<Detalle_orden>)
        // mapOrdenamiento
        // .get("lista_detalle");
        // //log.info("====> lista orden " + listaDetalle_orden);
        // if(!listaDetalle_orden.isEmpty()){
        // informacion_clinica.append("-ORDENES:").append("\t");
        //
        // if(manuales_tarifarios.getManual_tarifario().equals("SOAT")){
        // Procedimiento procedimiento = new Procedimiento();
        //
        // for (Detalle_orden detalle_orden : listaDetalle_orden) {
        // procedimiento.setCodigo_soat(detalle_orden.getCodigo_procedimiento());
        //
        // procedimiento =
        // getServiceLocator().getProcedimientoService().consultar(procedimiento);
        // //log.info("=====> soat " + procedimiento);
        // informacion_clinica.append(procedimiento != null ?
        // procedimiento.getDescripcion() : "").append("- ")
        // .append(detalle_orden != null ? detalle_orden.getUnidades() :
        // "").append("\t");
        // }
        // } if(manuales_tarifarios.getManual_tarifario().equals("ISS02")){
        // Procedimiento_iss01 procedimiento_iss01 = new Procedimiento_iss01();
        //
        // for (Detalle_orden detalle_orden : listaDetalle_orden) {
        // procedimiento_iss01.setCodigo_soat(detalle_orden.getCodigo_procedimiento());
        //
        // procedimiento_iss01 =
        // getServiceLocator().getProcedimiento_iss01Service().consultar(procedimiento_iss01);
        // //log.info("=====> ISS02 " + procedimiento_iss01);
        // informacion_clinica.append(procedimiento_iss01 != null ?
        // procedimiento_iss01.getDescripcion() : "").append("- ")
        // .append(detalle_orden != null ? detalle_orden.getUnidades() :
        // "").append("\t");
        // }
        // }if(manuales_tarifarios.getManual_tarifario().equals("ISS04")){
        //
        // Procedimiento_iss04 procedimiento_iss04 = new Procedimiento_iss04();
        //
        // for (Detalle_orden detalle_orden : listaDetalle_orden) {
        // procedimiento_iss04.setCodigo_soat(detalle_orden.getCodigo_procedimiento());
        //
        // procedimiento_iss04 =
        // getServiceLocator().getProcedimiento_iss04Service().consultar(procedimiento_iss04);
        // //log.info("=====> iss04" + procedimiento_iss04);
        // informacion_clinica.append(procedimiento_iss04 != null ?
        // procedimiento_iss04.getDescripcion() : "").append("- ")
        // .append(detalle_orden != null ? detalle_orden.getUnidades() :
        // "").append("\t");
        //
        // }
        // }
        // }
        return informacion_clinica.toString();
    }

    @Override
    public String getPersonaAcompaniante() {
        return tbxAcompaniante.getValue();
    }

    @Override
    public String getIdentificacionAcompaniante() {
        return odontologiaTemp.getCedula_acomp();
    }

    @Override
    public String getTelefonoAcompaniante() {
        return tbxTel_acompaniante.getText();
    }

    @Override
    public String getServicioSolicitaReferencia1() {
        StringBuffer serivicio1 = new StringBuffer();
        /* */
        serivicio1.append("ODONTOLOgía GENERAL");
        return serivicio1.toString();
    }

    @Override
    public void initHistoria() throws Exception {
        //log.info("Ejecutando metodo metodo @initHistoria()");
        cargarPrestador();
    }

    @Override
    public void inicializarVista(String tipo) {

    }

    @Override
    public void cargarInformacion_paciente() {
    }

    @Override
    public void cargarInformacion_historia_anterior(Object historia_anterior)
            throws Exception {

        tbxAccion.setValue(OpcionesFormulario.REGISTRAR.toString());
    }

    @Override
    public void initMostrar_datos(Object historia_clinica) {

    }

    public void imprimir() throws Exception {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        Evolucion_odontologia evolucion_odontologia = new Evolucion_odontologia();
        evolucion_odontologia.setCodigo_empresa(codigo_empresa);
        evolucion_odontologia.setCodigo_sucursal(codigo_sucursal);
        evolucion_odontologia.setCodigo_historia(odontologiaTemp
                .getCodigo_historia());

        evolucion_odontologia = getServiceLocator()
                .getEvolucion_odontologiaService().consultarUltimaEvolucion(
                        evolucion_odontologia);
        // String nro_historia = infoPacientes.getCodigo_historia();
        Long nro_historia = evolucion_odontologia
                .getCodigo_evolucion_historia();
        Long nro_historia_primera_vez = evolucion_odontologia
                .getCodigo_historia();
        if (nro_historia != null) {
            String parametros_pagina = "?codigo_empresa=" + codigo_empresa;
            parametros_pagina += "&codigo_sucursal=" + codigo_sucursal;
            parametros_pagina += "&codigo_historia=" + nro_historia;
            parametros_pagina += "&nro_historia_primera_vez=" + nro_historia_primera_vez;
            parametros_pagina += "&nro_ingreso=" + admision.getNro_ingreso();
            parametros_pagina += "&nro_identificacion="
                    + admision.getNro_identificacion();
            Clients.evalJavaScript("window.open(\"" + request.getContextPath()
                    + "/pages/reports/evolucion_odontologia_reporte.zul"
                    + parametros_pagina + "\", '_blank');");
        }
    }

}
