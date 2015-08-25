package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;
import healthmanager.modelo.bean.Orden_autorizacion;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tab;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRutas_historia;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.impl.ModuloConsultaIMG;
import com.framework.util.MensajesUtil;

public abstract class HistoriaClinicaModel extends ZKWindow {
    
    private final String CAMPO_AUTORIZACIONES = "divModuloAutorizacion";
    protected final String PARAM_AUTORIZACION = "autorizacion";
    
    public ModuloConsultaIMG hcuciAction;
    public Modulo_asistencialAction modulo_asistencialAction;
    private AutorizacionesAction orden_autorizacionAction;
    
    @Override
    public abstract void init() throws Exception;
    
    public void setParentBean(ModuloConsultaIMG hcuciAction) {
        this.hcuciAction = hcuciAction;
    }
    
    public void setParentBean(Modulo_asistencialAction modulo_asistencialAction) {
        this.modulo_asistencialAction = modulo_asistencialAction;
    }
    
    public Admision getAdmision() {
        return null;
    }
    
    @Override
    public void _despuesIniciar() {
        try {
            // Si es regimen especial cambiar por autorizaciones
            if (getSucursal().getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
                Component component = getFellowIfAny(CAMPO_AUTORIZACIONES);
                if (component != null) {
                    Admision admision = (Admision) Executions.getCurrent().getArg().get(IVias_ingreso.ADMISION_PACIENTE);
                    // Este es para el caso de evolucion
                    if (admision == null) {
                        admision = (Admision) Executions.getCurrent().getArg().get("admision");
                    }
                    Component componentTemp = component.getFirstChild();
                    if (componentTemp instanceof AutorizacionesAction) {
                        orden_autorizacionAction = (AutorizacionesAction) componentTemp;
                        orden_autorizacionAction.setAdmision(admision);
                        orden_autorizacionAction.cargarServiciosPermitidosMedico();
                        orden_autorizacionAction.setVisible(true);
                    }
                    
                    if (orden_autorizacionAction != null && orden_autorizacionAction.getIdDiv() != null
                            && !orden_autorizacionAction.getIdDiv().trim().isEmpty()) {
                        Component componentVivOrdenAnterior = getFellowIfAny(orden_autorizacionAction.getIdDiv());
                        if (componentVivOrdenAnterior != null) {
                            componentVivOrdenAnterior.setVisible(false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ValidacionRunTimeException("Error al cargar autorizaciones, comuniquese con el administrador");
        }
    }

    /**
     * Este metodo te permite actualizar el modulo de autorizaciones
     *
     * @author Luis Miguel
     *
     */
    public void actualizarAutorizaciones(Admision admision,
            String causas_externas, String diagnostico_principal,
            String diagnostico_relacionado1, String diagnostico_relacionado2,
            ZKWindow zkWindow) {
        //log.info("Padre: " + modulo_asistencialAction);
        try {
            if (modulo_asistencialAction != null) {
                Map<String, Component> map = modulo_asistencialAction
                        .getMapa_componentes();
                Tab tab = (Tab) map.get("TAB_"
                        + IRutas_historia.PAGINA_AUTORIZACIONES);
                if (tab != null) {
                    //log.info("@adtualizarAutorizaciones - Tab de autorizaciones: "
                    //+ tab);
                    //log.info("@adtualizarAutorizaciones - Titulo tab: "
                    //+ tab.getLabel());
                    EventListener<Event> eventListener = (EventListener<Event>) tab
                            .getAttribute("EVENTO_TAB");
                    Map<String, Object> parametros = (Map<String, Object>) tab
                            .getAttribute("PARAMETROS_MAP");

                    /* primero actualizamos los parametros */
                    if (parametros != null) {
                        //log.info("parametros: " + parametros);
                        parametros.put(IVias_ingreso.ADMISION_PACIENTE,
                                admision);
                        parametros.put(IVias_ingreso.CAUSAS_EXTERNAS,
                                causas_externas);
                        parametros.put(IVias_ingreso.DIAGNOSTICO_PRINCIPAL,
                                diagnostico_principal);
                        parametros.put(IVias_ingreso.DIAGNOSTICO_RELACIONADO1,
                                diagnostico_relacionado1);
                        parametros.put(IVias_ingreso.DIAGNOSTICO_RELACIONADO2,
                                diagnostico_relacionado2);
                    }

                    // actualizamos la vista..
                    if (eventListener != null) {
                        eventListener.onEvent(null);
                        //log.info("Evento: " + eventListener);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajesUtil.mensajeError(e, null, zkWindow);
        }
    }
    
    public void actualizarRemision(Admision admision,
            String informacion_clinica, ZKWindow window) {
        try {
            if (modulo_asistencialAction != null) {
                Map<String, Component> map = modulo_asistencialAction
                        .getMapa_componentes();
                Tab tab = (Tab) map.get("TAB_"
                        + IRutas_historia.PAGINA_REMISIONES);
                if (tab != null) {
                    EventListener<Event> eventListener = (EventListener<Event>) tab
                            .getAttribute("EVENTO_TAB");
                    Map<String, Object> parametros = (Map<String, Object>) tab
                            .getAttribute("PARAMETROS_MAP");
                    /* primero actualizamos los parametros */
                    if (parametros != null) {
                        //log.info("parametros: " + parametros);
                        parametros.put(IVias_ingreso.ADMISION_PACIENTE,
                                admision);
                        parametros.put(IVias_ingreso.INFORMACION_CLINICA,
                                informacion_clinica);
                    }
                    // actualizamos la vista..
                    if (eventListener != null) {
                        eventListener.onEvent(null);
                        //log.info("Evento: " + eventListener);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajesUtil.mensajeError(e, null, window);
        }
    }

    /**
     * Metodo para retornar el resumen de una historia clinica, el cual puede
     * ser usado cuando se este generando una remision o un proceso asi por el
     * estilo
     */
    public String getInformacionClinica() throws WrongValueException {
//		return "@Override Debe reescribir el metodo getInformacionClinica()";
        return "";
    }
    
    public String getPersonaAcompaniante() throws WrongValueException {
//		return "@Override Debe reescribir el metodo getPersonaAcompaniante()";
        return "";
    }
    
    public String getIdentificacionAcompaniante() throws WrongValueException {
//		return "@Override Debe reescribir el metodo getIdentificacionAcompaniante()";
        return "";
    }
    
    public String getTelefonoAcompaniante() throws WrongValueException {
//		return "@Override Debe reescribir el metodo getTelefonoAcompaniante()";
        return "";
    }
    
    public String getServicioSolicitaReferencia1() {
//		return "@Override Debe reescribir el metodo getTelefonoAcompaniante()";
        return "";
    }

    /**
     * Este emtodo me permite obtener la informacion de la autorizacion en todas
     * las historias
     *
     * @author Luis miguel Hernandez
     *
     */
    public Map<String, Object> obtenerDatosAutorizacion() {
        if (orden_autorizacionAction != null) {
            Map<String, Object> map = orden_autorizacionAction.obtenerDatos();
            if (map.containsKey("detalle")) {
                List<Detalle_orden_autorizacion> detalle_orden_autorizacions = (List<Detalle_orden_autorizacion>) map.get("detalle");
                if (detalle_orden_autorizacions.isEmpty()) {
                    return null;
                } else {
                    //log.info("Va realizar un autorizacion");
                    return map;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Este metodo me permite mostrar la autorizacion
     *
     */
    public void mostrarDatosAutorizacion(Map<String, Object> map_orden_autorizacion) {
        try {
            if (getSucursal().getTipo()
                    .equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)
                    && map_orden_autorizacion != null
                    && orden_autorizacionAction != null) {
                Map<String, Object> map_orden = (Map<String, Object>) map_orden_autorizacion.get(PARAM_AUTORIZACION);
                if (map_orden != null) {
                    Orden_autorizacion orden_autorizacion = (Orden_autorizacion) map_orden.get("orden");
                    orden_autorizacionAction.mostrarDatos(orden_autorizacion, false);
                }
            }
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, null, this);
        }
    }
    
    public void mostrarHistoriaPagina(Long codigo_historia, String via_ingreso, ContenedorPaginasMacro tabboxContendor, String label) {
        if (tabboxContendor != null) {
            tabboxContendor.abrirPaginaTab(true, "/pages/historias_clinicas_tab.zul?via_ingreso=" + via_ingreso
                    + "&codigo_historia=" + codigo_historia, label+" "+codigo_historia);
        } else {
            log.info("No esta llegando la referencia de tabboxContendor");
        }
    }
    
}
