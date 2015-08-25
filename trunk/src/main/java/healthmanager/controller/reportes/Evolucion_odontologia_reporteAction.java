/*
 * evolucion_odontologiaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.controller.Orden_servicio_internoAction;
import healthmanager.controller.Receta_rips_internoAction;
import healthmanager.controller.Remisiones_externasAction;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Diente;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Evolucion_odontologia;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Plan_tratamiento;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Evolucion_odontologiaService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;

import java.sql.Timestamp;
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
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.odontograma.OdontogramaMacro;
import com.framework.macros.odontograma.util.ISectorDiente.TypeSector;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.Res;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorPoblacion;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class Evolucion_odontologia_reporteAction extends HistoriaClinicaModel
        implements ISeleccionarComponente, IHistoria_generica {

//	private Plan_tratamientoService plan_tratamientoService;
    private static Logger log = Logger
            .getLogger(Evolucion_odontologia_reporteAction.class);

    // manuel
//	private List<String> lista_procedimientos;
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

//	private Borderlayout groupboxEditar;
//	private Groupbox groupboxConsulta;
    @View
    private Listbox lbxRelacion;
    
    @View
    private Longbox tbxCodigo_evolucion_historia;
    
    @View
    private Textbox tbxProcedimiento_realizado;
    private final String[] idsExcluyentes = {"lbxParameter", "tbxValue",
        "tbxCodigo_prestadorEvolucion", "tbxNomPrestador",
        "dtbxFecha_ingreso"};
    
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
    private Toolbarbutton btnLimpiarPrestador;

    /* historia odontologica */
    private Odontologia odontologiaTemp;
    
    private boolean primera_vez;
    
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

//	@View
//	private Div divModuloOrdenamiento;
//	@View
//	private Div divModuloFarmacologico;
    @View
    private Groupbox groupPronostico;
//	@View
//	private Groupbox gbxRemisiones;

    @View
    private Listbox lbxPronostico;
    
    @View(isMacro = true)
    private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
    
    private Remisiones_externasAction remisiones_externasAction;
    private Orden_servicio_internoAction orden_servicioAction;
    private Receta_rips_internoAction receta_ripsAction;
    
    private String nro_ingreso_temp;
    
    private Admision admision;

	// nuevos
//	private Paciente paciente;
//	private Evolucion_odontologia evolucion;
//	private ESexo sexo;
//	private Timestamp fecha;
    @View
    private Groupbox gbxContenido;
    @View
    private Image imgLogo;
    @View
    private Label lblTitulo;
    @View
    private Label lblMedicoTratante;
    @View
    private Label lblRegMedico;
    @View
    private Image imgFirma;
    
    private List<String> seleccionados = new ArrayList<String>();
    
    @Override
    public void init() throws Exception {
        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();
        String codigo_historia = request.getParameter("codigo_historia");
        String nro_ingreso = request.getParameter("nro_ingreso");
        String nro_identificacion = request.getParameter("nro_identificacion");
        String codigo_empresa = request.getParameter("codigo_empresa");
        String codigo_sucursal = request.getParameter("codigo_sucursal");
        String nro_historia_primera_vez = request.getParameter("nro_historia_primera_vez");        
        
        Long id_codigo_historia = null;
        Long id_nro_historia_primera_vez = null;
        
        if (codigo_historia != null && !codigo_historia.trim().isEmpty()
                && codigo_historia.matches("[0-9]*$")) {
            id_codigo_historia = Long.parseLong(codigo_historia);
        }
        
        if (nro_historia_primera_vez != null && !nro_historia_primera_vez.trim().isEmpty()
                && nro_historia_primera_vez.matches("[0-9]*$")) {
            id_nro_historia_primera_vez = Long.parseLong(nro_historia_primera_vez);
        }
        
        if (nro_identificacion != null) {
            admision = new Admision();
            admision.setCodigo_empresa(codigo_empresa);
            admision.setCodigo_sucursal(codigo_sucursal);
            admision.setNro_identificacion(nro_identificacion);
            admision.setNro_ingreso(nro_ingreso);
            admision = getServiceLocator().getServicio(AdmisionService.class)
                    .consultar(admision);
            if (admision != null) {
                macroImpresion_diagnostica.inicializarMacro(this, admision,
                        IVias_ingreso.ADULTO_MAYOR);
//				paciente = admision.getPaciente();
//				sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
//						: ESexo.MASCULINO);
//				fecha = paciente.getFecha_nacimiento();
            }
            
        }
        
        listarCombos();
        
        if (id_codigo_historia != null) {
            
            Evolucion_odontologia evolucion = new Evolucion_odontologia();
            evolucion.setCodigo_empresa(codigo_empresa);
            evolucion.setCodigo_sucursal(codigo_sucursal);
            evolucion.setCodigo_evolucion_historia(id_codigo_historia);
            evolucion.setCodigo_historia(id_nro_historia_primera_vez);            
            
            evolucion = getServiceLocator().getServicio(
                    Evolucion_odontologiaService.class).consultar(evolucion);
            
            if (evolucion != null) {
                
                lblTitulo.setValue("HISTORIA CLINICA ODONTOLOGICA");
                
                resolucion = new Resolucion();
                resolucion.setCodigo_empresa(codigo_empresa);
                resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
                imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
                
                Usuarios prestador = new Usuarios();
                prestador.setCodigo_empresa(codigo_empresa);
                prestador.setCodigo_sucursal(codigo_sucursal);
                prestador.setCodigo(usuarios.getCodigo());
                prestador = getServiceLocator().getServicio(UsuariosService.class)
                        .consultar(prestador);
                if (prestador != null) {
                    lblRegMedico.setValue(prestador.getRegistro_medico());
                    lblMedicoTratante.setValue(prestador.getNombres() + " "
                            + prestador.getApellidos());
                    if (prestador.getFirma() != null) {
                        imgFirma.setContent(new AImage("firma", prestador
                                .getFirma()));
                    }
                }
                
                FormularioUtil.deshabilitarComponentes(gbxContenido, true,
                        idsExcluyentes);
                mostrarDatos(evolucion);
            } else {
                MensajesUtil.mensajeAlerta("Advertencia",
                        "La evolucion a imprimir, el software no la encuentra\n id historia: "
                        + id_nro_historia_primera_vez
                        + " - id evolucion: " + id_codigo_historia);                
            }
        }
        
        listarCombos();
        parametrizarBandbox();
        inicializarVista("control");
        macroImpresion_diagnostica.inicializarMacro(this, admision,
                IVias_ingreso.ODONTOLOGIA);

        /*
         * macroRemision_interna.inicializarMacro(this, admision,
         * IVias_ingreso.ODONTOLOGIA2);
         */
        initDatos();
        inicializarOdontograma();
    }
    
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
//	private void accionPrimeraVezControl() {
//		if (primera_vez) {
//			// groupPlanesManejos.setVisible(false);
////			divModuloOrdenamiento.setVisible(false);
//			// btCancel.setVisible(false);
//			// groupPronostico.setVisible(false);
//			// groupPreguntasBuentrabajo.setVisible(false);
//			// groupPreguntasPandas.setVisible(false);
////			gbxRemisiones.setVisible(false);
////			gbxRemisionesInternas.setVisible(false);
//			// lbxProcedimientoPorRealizar.setVisible(true);
//		} else {
//			// groupPlanesManejos.setVisible(true);
////			divModuloOrdenamiento.setVisible(false);
//			// btCancel.setVisible(true);
//			// groupPronostico.setVisible(true);
//			// groupPreguntasBuentrabajo.setVisible(true);
//			// groupPreguntasPandas.setVisible(true);
////			gbxRemisiones.setVisible(true);
////			gbxRemisionesInternas.setVisible(true);
//			// lbxProcedimientoPorRealizar.setVisible(false);
//
//		}
//	}
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
                        "Usuario no esta creado en el modulo de prestadore, actualize informaciÃƒÂ³n de usuario");
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

    /*
     * @Override public void params(Map<String, Object> map) { // admision =
     * (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE); //
     * //log.info("ADMISION>>>>>>>>>>>>" + admision); // if (admision != null) {
     * // //log.info("via de ingreso >>>>>>>>>>" + admision.getVia_ingreso()); //
     * }
     * 
     * // if (!admision.getVia_ingreso().equals( //
     * IVias_ingreso.URGENCIA_ODONTOLOGICO)) { lista_procedimientos =
     * (List<String>) map.get("lista_codigos_fac"); if (lista_procedimientos ==
     * null) { lista_procedimientos = new ArrayList<String>(); } odontologiaTemp
     * = (Odontologia) map.get("odontologiaTemp"); admision = (Admision)
     * map.get("admision"); nro_ingreso_temp = nro_ingreso = (String)
     * map.get("nro_ingreso"); primera_vez = (Boolean) map.get("primera_vez");
     * macroRemision_interna.deshabilitarCheck(admision,
     * IVias_ingreso.ODONTOLOGIA2); opcion_formulario = (OpcionesFormulario)
     * map.get("opcion_formulario"); // if (odontologiaTemp != null) { //
     * cargarDatosDeOdontologia();
     * 
     * accionPrimeraVezControl();
     * 
     * // } }
     */
	// }
    public void openPcd() throws Exception {

//		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
//				.getManuales_tarifarios(admision);
        String pages = "";
        
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("codigo_empresa", empresa.getCodigo_empresa());
        parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
        parametros.put("codigo_administradora",
                admision.getCodigo_administradora());
        parametros.put("id_plan", admision.getId_plan());
        parametros.put("restringido", "");
        parametros.put("nro_ingreso", admision.getNro_ingreso());
        parametros.put("nro_identificacion", admision.getNro_identificacion());
        // parametros.put("estrato", "");
        parametros.put("consulta", "N");
        // parametros.put("quirurgico", "");
        parametros.put("anio", anio);
        parametros.put("seleccionados", seleccionados);
        parametros.put("via_ingreso", admision.getVia_ingreso());

		//log.info("Paginas: " + pages);
        Component componente = Executions.createComponents(pages, null,
                parametros);
        final Window ventana = (Window) componente;
        
        ventana.setWidth("990px");
        ventana.setHeight("400px");
        ventana.setClosable(true);
        ventana.setMaximizable(true);
        // ventana.setTitle("CONSULTAR PROCEDIMIENTOS " + manual);
        ventana.setMode("modal");
    }
    
    private Listitem crearListItemProcedimientoPorRealizar(
            Plan_tratamiento plan_tratamiento, boolean habilitarEdicion) {
        
        final Listitem listitem = new Listitem();
        listitem.setSelected(false);
        listitem.setValue(plan_tratamiento);

//		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
//				.getManuales_tarifarios(admision);
        String nombre_procedimiento = "";
        String codigo_cups = plan_tratamiento.getCodigo_procedimiento();
        try {
            /*
             * Map<String, Object> mapProcedimiento =
             * Utilidades.getProcedimiento( manuales_tarifarios,
             * plan_tratamiento.getCodigo_procedimiento(), getServiceLocator());
             * nombre_procedimiento = (String) mapProcedimiento
             * .get("nombre_procedimiento");
             * 
             * codigo_cups = (String) mapProcedimiento.get("codigo_cups");
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        
        Intbox intbox_por_realizar = new Intbox(
                (plan_tratamiento.getUnidades() - plan_tratamiento
                .getUnidades_realizadas()));
        intbox_por_realizar.setHflex("1");
        intbox_por_realizar.setReadonly(!habilitarEdicion);
        intbox_por_realizar.setInplace(!habilitarEdicion);
        
        if (habilitarEdicion) {
            plan_tratamiento.setUnidades(1);
            Res.cargarAutomatica(intbox_por_realizar, plan_tratamiento,
                    "unidades");
        }
        
        listcell = new Listcell();
        listcell.appendChild(intbox_por_realizar);
        listitem.appendChild(listcell);
        
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
            Map<String, Object> map = new HashMap<String, Object>();
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
                .setTitleOdontograma("EVOLUCIÃ“N DEL TRATAMIENTO - ODONTOGRAMA");
        // odtodontograma_evolucion.showTipo_denticion(false);
    }
    
    public void listarCombos() {
        // listarParameter();
        Utilidades
                .listarElementoListbox(lbxRelacion, true, getServiceLocator());
        Utilidades.listarElementoListbox(lbxPronostico, true,
                getServiceLocator());
    }
    
    public void seleccion_radio(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            // System.out.println("" + radiogroup.getSelectedItem().getValue());

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
            // System.out.println("Excepcion loaded");
            e.printStackTrace();
        }
    }
    
    public void seleccion_radio2(Radiogroup radiogroup,
            AbstractComponent[] abstractComponents) {
        try {
            // System.out.println("" + radiogroup.getSelectedItem().getValue());

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
            // System.out.println("Excepcion loaded");
            e.printStackTrace();
        }
    }

    /*
     * public void listarParameter() { lbxParameter.getChildren().clear();
     * 
     * Listitem listitem = new Listitem();
     * listitem.setValue("codigo_evolucion_historia");
     * listitem.setLabel("Codigo Evolucion");
     * lbxParameter.appendChild(listitem);
     * 
     * listitem = new Listitem(); listitem.setValue("creacion_date::varchar");
     * listitem.setLabel("Fecha"); lbxParameter.appendChild(listitem);
     * 
     * if (lbxParameter.getItemCount() > 0) { lbxParameter.setSelectedIndex(0);
     * } }
     */
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
    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
//		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
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
			// groupboxConsulta.setVisible(true);
            // groupboxEditar.setVisible(false);
        }
        // btGuardar.setDisabled(false);
        cargarPrestador();
        nro_ingreso_temp = nro_ingreso;
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

    // Metodo para guardar la informaciÃƒÂ³n //
    public void guardarDatos() throws Exception {

//		FormularioUtil.setUpperCase(groupboxEditar);
        if (validarForm()) {
            // Cargamos los componentes //
            Messagebox.show(usuarios.getNombres()
                    + " estas seguro de realizar esta acciÃƒÂ³n ?",
                    "InformaciÃƒÂ³n ..", Messagebox.YES + Messagebox.NO,
                    Messagebox.INFORMATION, new EventListener() {
                        
                        @Override
                        public void onEvent(Event event) throws Exception {
                            try {
                                Evolucion_odontologia evolucion_odontologia = new Evolucion_odontologia();
                                evolucion_odontologia.setCodigo_empresa(empresa
                                        .getCodigo_empresa());
                                evolucion_odontologia
                                .setCodigo_sucursal(sucursal
                                        .getCodigo_sucursal());
                                evolucion_odontologia.setCodigo_evolucion_historia(tbxCodigo_evolucion_historia
                                        .getValue());
                                evolucion_odontologia
                                .setCodigo_historia(odontologiaTemp
                                        .getCodigo_historia());
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
                                evolucion_odontologia
                                .setMotivo_consulta(Util
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
                                
                                Map<String, Object> datos = new HashMap<String, Object>();
                                datos.put("evolucion", evolucion_odontologia);
                                datos.put("dientes",
                                        odtodontograma_evolucion.getDientes());
                                // datos.put("accion", tbxAccion.getText());
                                datos.put("admision", admision);
                                datos.put("autorizacion",
                                        obtenerDatosAutorizacion());
								// datos.put("orden_adicional",
                                // orden_servicioAdicionalMap);
                                datos.put("procedimientos_realizados",
                                        getProcedimientosRealizar());
                                datos.put("odontologia", odontologiaTemp);
                                Impresion_diagnostica impresion_diagnostica = null;
                                if (admision.getPrimera_vez().equals("N")) {
                                    impresion_diagnostica = macroImpresion_diagnostica
                                    .obtenerImpresionDiagnostica();
                                    datos.put("impresion_diagnostica",
                                            impresion_diagnostica);
                                }
                                
                                datos.put("primera_vez", primera_vez);
                                
                                Parametros_empresa parametros_empresa = new Parametros_empresa();
                                parametros_empresa
                                .setCodigo_empresa(codigo_empresa);
                                parametros_empresa = getServiceLocator()
                                .getServicio(GeneralExtraService.class)
                                .consultar(parametros_empresa);
                                
                                datos.put("parametros_empresa",
                                        parametros_empresa);
                                
                                evolucion_odontologia = getServiceLocator()
                                .getEvolucion_odontologiaService()
                                .guardar(datos);
                                tbxCodigo_evolucion_historia.setValue(evolucion_odontologia
                                        .getCodigo_evolucion_historia());
								// btGuardar.setDisabled(true);

							 // mostrarDatos(evolucion_odontologia);
                                // tbxAccion.setValue("modificar");
                                MensajesUtil
                                .mensajeInformacion("InformaciÃƒÂ³n ..",
                                        "Los datos se guardaron satisfactoriamente");
                            } catch (ImpresionDiagnosticaException e) {
                                log.error(e.getMessage(), e);
                            } catch (Exception e) {
                                MensajesUtil
                                .mensajeError(
                                        e,
                                        "modificar",
                                        Evolucion_odontologia_reporteAction.this);
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
            // btGuardar.setDisabled(true);
            Admision admision = new Admision();
            admision.setCodigo_empresa(evolucion_odontologia
                    .getCodigo_empresa());
            admision.setCodigo_sucursal(evolucion_odontologia
                    .getCodigo_sucursal());
            admision.setNro_identificacion(evolucion_odontologia
                    .getIdentificacion());
            admision.setNro_ingreso(evolucion_odontologia.getNro_ingreso());
            /*
             * admision = getServiceLocator().getAdmisionService().consultar(
             * admision);
             */

//			if (admision == null) {
//				Notificaciones
//						.mostrarNotificacionAlerta(
//								"Advertencia",
//								"La admision que corresponde a esta historia no existe",
//								3000);
//			} else {
//				accionPrimeraVezControl();
//			}
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
            /* Mostar odontograma */
            odtodontograma_evolucion.resetOdontograma();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("codigo_empresa", codigo_empresa);
            map.put("codigo_sucursal", codigo_sucursal);
            map.put("evolucion", "S");
            map.put("nro_historia",
                    evolucion_odontologia.getCodigo_evolucion_historia());

            /* cargamos odontograma */
            List<Diente> dientes = getServiceLocator().getDienteService()
                    .listar(map);
            odtodontograma_evolucion.setDientes(dientes);
			// btGuardar.setDisabled(true);
            // Mostramos la vista //
            // tbxAccion.setText("modificar");
            // groupboxConsulta.setVisible(false);
            // groupboxEditar.setVisible(true);

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

			// calcularTotal();
            // btGuardar.setDisabled(true);
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
            
        }
    }
    
    @Override
    public void adicionarPcd(Map<String, Object> pcd) throws Exception {

		//log.info("Mapa: " + pcd);
        Plan_tratamiento plan_tratamiento = new Plan_tratamiento();
        plan_tratamiento.setCodigo_empresa(codigo_empresa);
        plan_tratamiento.setCodigo_sucursal(codigo_sucursal);
        plan_tratamiento.setCodigo_procedimiento((String) pcd
                .get("codigo_procedimiento"));
        
        Double doubleValorpcdTemp = (Double) pcd.get("valor_pcd");
        if (doubleValorpcdTemp == null) {
            doubleValorpcdTemp = (Double) pcd.get("valor_procedimiento");
        }
        plan_tratamiento.setValor_procedimiento(doubleValorpcdTemp);
        plan_tratamiento.setDescuento((Double) pcd.get("descuento"));
        plan_tratamiento.setIncremento((Double) pcd.get("incremento"));
        plan_tratamiento.setValor_real((Double) pcd.get("valor_real"));
        
        plan_tratamiento.setUnidades(0);
        plan_tratamiento.setUnidades_realizadas(0);
        plan_tratamiento.setCodigo_cups((String) pcd.get("codigo_cups"));
        plan_tratamiento.setRealizado("N");
        plan_tratamiento
                .setNro_identificacion(admision.getNro_identificacion());
        plan_tratamiento.setTipo_procedimiento((String) pcd
                .get("tipo_procedimiento"));

		//log.info("plan tratamiento: " + plan_tratamiento);
        lbxProcedimientoPorRealizar
                .appendChild(crearListItemProcedimientoPorRealizar(
                                plan_tratamiento, true));
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
        serivicio1.append("ODONTOLOGIA GENERAL");
        return serivicio1.toString();
    }
    
    @Override
    public void initHistoria() throws Exception {
        
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

        // tbxAccion.setValue(OpcionesFormulario.REGISTRAR.toString());
    }
    
    @Override
    public void initMostrar_datos(Object historia_clinica) {
        
    }
    
}
