package com.framework.locator;

import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.*;
import imagen_diagnostica.modelo.service.Imagen_diagnosticaService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tablas_crecimiento_desarrollo.modelo.service.Tabla_anios_mesesService;
import tablas_crecimiento_desarrollo.modelo.service.Tabla_talla_pesoService;
import contaweb.modelo.service.ArticuloService;
import contaweb.modelo.service.BancoService;
import contaweb.modelo.service.BodegaService;
import contaweb.modelo.service.Bodega_articuloService;
import contaweb.modelo.service.CajaService;
import contaweb.modelo.service.Centro_costoService;
import contaweb.modelo.service.ComprobanteService;
import contaweb.modelo.service.ContabilizacionService;
import contaweb.modelo.service.Detalle_cajaService;
import contaweb.modelo.service.Detalle_facturaService;
import contaweb.modelo.service.Detalle_inventarioService;
import contaweb.modelo.service.Detalle_notaService;
import contaweb.modelo.service.FacturacionService;
import contaweb.modelo.service.Grupo1Service;
import contaweb.modelo.service.Grupo2Service;
import contaweb.modelo.service.Grupo_cuentaService;
import contaweb.modelo.service.InventarioService;
import contaweb.modelo.service.KardexService;
import contaweb.modelo.service.Nota_contableService;
import contaweb.modelo.service.PagareService;
import contaweb.modelo.service.PucService;
import contaweb.modelo.service.Tarifa_medicamentoService;
import contaweb.modelo.service.TerceroService;
import contaweb.modelo.service.Tipo_cuentaService;
import contaweb.modelo.service.Tipo_terceroService;

public class ServiceLocatorAux {

    private static ServiceLocatorAux instance;
    private ApplicationContext appContext;

    private ServiceLocatorAux(ServletContext servletContext) {
        this.appContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);
    }

    private ServiceLocatorAux() {
        this.appContext = new ClassPathXmlApplicationContext(
                "classpath:applicationContext-test.xml");
    }

    public static Usuarios getUsuarios(HttpServletRequest request) {
        Usuarios usuarios = null;
        Object object = request.getSession().getAttribute("usuarios");
        if (object instanceof Usuarios) {
            usuarios = (Usuarios) object;
        }
        return usuarios;
    }

    public static Empresa getEmpresa(HttpServletRequest request) {
        Empresa empresa = (Empresa) request.getSession()
                .getAttribute("empresa");

        return empresa;
    }

    public static Sucursal getSucursal(HttpServletRequest request) {
        Sucursal sucursal = (Sucursal) request.getSession().getAttribute(
                "sucursal");

        return sucursal;
    }

    /**
     * Metodo para obtener un bean configurado en un aplicationContext a partir
     * del nombre, que este caso es el id que se le da al bean en dicho archivo
     *
     * @param serviceBeanName
     * @return
     */
    private Object lookupService(String serviceBeanName) {
        return appContext.getBean(serviceBeanName);
    }

    /**
     * Este metodo te permite cargar un servicio del spring
     *
     * @param clase - la clase
     * @param <T> - el servicio a buscar
     * @return El servicio que esta en el bean, si no esta arroja una excepcion
     * @author Luis Miguel
	 *
     */
    @SuppressWarnings("unchecked")
	public <T> T getServicio(Class<T> clase) {
        Service service = clase.getAnnotation(Service.class);
        if (service != null) {
            return (T) lookupService(service.value());
        } else {
            throw new RuntimeException(
                    "Servicio no valido, para ser un servicio valido debe tener la anotacion @Service de Mybatis");
        }
    }

    public Tipo_cuentaService getTipo_cuentaService() {
        return getServicio(Tipo_cuentaService.class);
    }

    public Grupo_cuentaService getGrupo_cuentaService() {
        return getServicio(Grupo_cuentaService.class);
    }

    public ReportService getReportService() {
        return getServicio(ReportService.class);
    }

    public UsuariosService getUsuariosService() {
        return getServicio(UsuariosService.class);
    }

    public Hisc_deteccion_alt_mn_2mesService getHisc_deteccion_alt_mn_2mesService() {
        return getServicio(Hisc_deteccion_alt_mn_2mesService.class);
    }

    public Contrato_prestadoresService getContratoPrestadoresService() {
        return getServicio(Contrato_prestadoresService.class);
    }

    public PacienteService getPacienteService() {
        return getServicio(PacienteService.class);
    }

    public PrestadoresService getPrestadoresService() {
        return getServicio(PrestadoresService.class);
    }

    public AdministradoraService getAdministradoraService() {
        return getServicio(AdministradoraService.class);
    }

    public ContratosService getContratosService() {
        return getServicio(ContratosService.class);
    }

    public ElementoService getElementoService() {
        return getServicio(ElementoService.class);
    }

    public EspecialidadService getEspecialidadService() {
        return getServicio(EspecialidadService.class);
    }

    public ConsecutivoService getConsecutivoService() {
        return getServicio(ConsecutivoService.class);
    }

    public ResolucionService getResolucionService() {
        return getServicio(ResolucionService.class);
    }

    public DepartamentosService getDepartamentosService() {
        return getServicio(DepartamentosService.class);
    }

    public MunicipiosService getMunicipiosService() {
        return getServicio(MunicipiosService.class);
    }

    public Evolucion_medicaService getEvolucion_medicaService() {
        return getServicio(Evolucion_medicaService.class);
    }

    public Detalle_evolucionService getDetalle_evolucionService() {
        return getServicio(Detalle_evolucionService.class);
    }

    public HabitacionService getHabitacionService() {
        return getServicio(HabitacionService.class);
    }

    public CamaService getCamaService() {
        return getServicio(CamaService.class);
    }

    public PabellonService getPabellonService() {
        return getServicio(PabellonService.class);
    }

    public Copago_estratoService getCopago_estratoService() {
        return getServicio(Copago_estratoService.class);
    }

    public Restriccion_pcdService getRestriccion_pcdService() {
        return getServicio(Restriccion_pcdService.class);
    }

    public ProcedimientosService getProcedimientosService() {
        return getServicio(ProcedimientosService.class);
    }

    public Detalle_ordenService getDetalle_ordenService() {
        return getServicio(Detalle_ordenService.class);
    }

    public Orden_servicioService getOrden_servicioService() {
        return getServicio(Orden_servicioService.class);
    }

    public Autorizacion_pcdService getAutorizacion_pcdService() {
        return getServicio(Autorizacion_pcdService.class);
    }

    public Detalle_autorizacion_pcdService getDetalle_autorizacion_pcdService() {
        return getServicio(Detalle_autorizacion_pcdService.class);
    }

    public Nota_enfermeriaService getNota_enfermeriaService() {
        return getServicio(Nota_enfermeriaService.class);
    }

    public Detalle_nota_enfService getDetalle_nota_enfService() {
        return getServicio(Detalle_nota_enfService.class);
    }

    public Receta_ripsService getReceta_ripsService() {
        return getServicio(Receta_ripsService.class);
    }

    public Detalle_receta_ripsService getDetalle_receta_ripsService() {
        return getServicio(Detalle_receta_ripsService.class);
    }

    public ArticuloService getArticuloService() {
        return getServicio(ArticuloService.class);
    }

    public Grupo1Service getGrupo1Service() {
        return getServicio(Grupo1Service.class);
    }

    public Grupo2Service getGrupo2Service() {
        return getServicio(Grupo2Service.class);
    }

    public TerceroService getTerceroService() {
        return getServicio(TerceroService.class);
    }

    public Tipo_terceroService getTipo_terceroService() {
        return getServicio(Tipo_terceroService.class);
    }

    public Tarifa_medicamentoService getTarifa_medicamentoService() {
        return getServicio(Tarifa_medicamentoService.class);
    }

    public Bodega_articuloService getBodega_articuloService() {
        return getServicio(Bodega_articuloService.class);
    }

    public IpsService getIpsService() {
        return getServicio(IpsService.class);
    }

    public Roles_usuariosService getRoles_usuariosService() {
        return getServicio(Roles_usuariosService.class);
    }

    public Datos_consultaService getDatos_consultaService() {
        return getServicio(Datos_consultaService.class);
    }

    public Parametro_codigo_consultaService getParametro_codigo_consultaService() {
        return getServicio(Parametro_codigo_consultaService.class);
    }

    public UrgenciasService getUrgenciasService() {
        return getServicio(UrgenciasService.class);
    }

    public HospitalizacionService getHospitalizacionService() {
        return getServicio(HospitalizacionService.class);
    }

    public Cuota_moderadoraService getCuota_moderadoraService() {
        return getServicio(Cuota_moderadoraService.class);
    }

    public Antecedentes_personalesService getAntecedentesPersonalesService() {
        return getServicio(Antecedentes_personalesService.class);
    }

    public Plantilla_pypService getPlantillaPypService() {
        return getServicio(Plantilla_pypService.class);
    }



    public Justificacion_negacionService getJustificacionNegacionService() {
        return getServicio(Justificacion_negacionService.class);
    }

    public Anexo10_entidadService getAnexo10EntidadService() {
        return getServicio(Anexo10_entidadService.class);
    }

    public Anexo3_entidadService getAnexo3EntidadService() {
        return getServicio(Anexo3_entidadService.class);
    }

    public Anexo4_entidadService getAnexo4EntidadService() {
        return getServicio(Anexo4_entidadService.class);
    }

    public Anexo9_entidadService getAnexo9EntidadService() {
        return getServicio(Anexo9_entidadService.class);
    }

    public AdmisionService getAdmisionService() {
        return getServicio(AdmisionService.class);
    }

    public NegacionService getNegacionService() {
        return getServicio(NegacionService.class);
    }

    public CitasService getCitasService() {
        return getServicio(CitasService.class);
    }

    public CajaService getCajaService() {
        return getServicio(CajaService.class);
    }

    public PagareService getPagareService() {
        return getServicio(PagareService.class);
    }

    public Detalle_cajaService getDetalle_cajaService() {
        return getServicio(Detalle_cajaService.class);
    }

    public Anio_cuota_moderadoraService getAnio_cuota_moderadoraService() {
        return getServicio(Anio_cuota_moderadoraService.class);
    }

    public BancoService getBancoService() {
        return getServicio(BancoService.class);
    }

    public ComprobanteService getComprobanteService() {
        return getServicio(ComprobanteService.class);
    }

    public contaweb.modelo.service.ConsecutivoService getConsecutivo_contService() {
        return getServicio(contaweb.modelo.service.ConsecutivoService.class);
    }

    public Detalle_notaService getDetalle_notaService() {
        return getServicio(Detalle_notaService.class);
    }

    public contaweb.modelo.service.ElementoService getElemento_contService() {
        return getServicio(contaweb.modelo.service.ElementoService.class);
    }

    public Nota_contableService getNota_contableService() {
        return getServicio(Nota_contableService.class);
    }

    public contaweb.modelo.service.ResolucionService getResolucion_contService() {
        return getServicio(contaweb.modelo.service.ResolucionService.class);
    }

    public contaweb.modelo.service.ReportService getReport_contService() {
        return getServicio(contaweb.modelo.service.ReportService.class);
    }

    public InventarioService getInventarioService() {
        return getServicio(InventarioService.class);
    }

    public Detalle_inventarioService getDetalleInventarioService() {
        return getServicio(Detalle_inventarioService.class);
    }

    public KardexService getKardexService() {
        return getServicio(KardexService.class);
    }

    public Facturacion_medicamentoService getFacturacionMedicamentoService() {
        return getServicio(Facturacion_medicamentoService.class);
    }

    public Datos_medicamentosService getDatosMedicamentosService() {
        return getServicio(Datos_medicamentosService.class);
    }

    public Revision_ciiuService getRevisionCiiuService() {
        return getServicio(Revision_ciiuService.class);
    }

    public Afiliaciones_meService getAfiliacionesMeService() {
        return getServicio(Afiliaciones_meService.class);
    }

    public Aportantes_maService getAportantesMaService() {
        return getServicio(Aportantes_maService.class);
    }

    public Aportes_cotizacionesService getAportesCotizacionesService() {
        return getServicio(Aportes_cotizacionesService.class);
    }

    public Especificaciones_aportesService getEspecificacionesAportesService() {
        return getServicio(Especificaciones_aportesService.class);
    }

    public Novedades_neService getNovedadesNeService() {
        return getServicio(Novedades_neService.class);
    }

    public Respuesta_solicitud_e4Service getRespuestaSolicitudE4Service() {
        return getServicio(Respuesta_solicitud_e4Service.class);
    }

    public Solicitud_e1Service getSolicitudE1Service() {
        return getServicio(Solicitud_e1Service.class);
    }

    public Salario_anualService getSalarioAnualService() {
        return getServicio(Salario_anualService.class);
    }

    public Registro_controlService getRegistro_controlService() {
        return getServicio(Registro_controlService.class);
    }

    public Registro_detalleService getRegistro_detalleService() {
        return getServicio(Registro_detalleService.class);
    }

    public RiesgoService getRiesgoService() {
        return getServicio(RiesgoService.class);
    }

    public Riesgo_intervencionService getRiesgo_intervencionService() {
        return getServicio(Riesgo_intervencionService.class);
    }

    public Pertenencia_etnicaService getPertenencia_etnicaService() {
        return getServicio(Pertenencia_etnicaService.class);
    }

    public OcupacionesService getOcupacionesService() {
        return getServicio(OcupacionesService.class);
    }

    public Nivel_educativoService getNivel_educativoService() {
        return getServicio(Nivel_educativoService.class);
    }

    public NovedadService getNovedadService() {
        return getServicio(NovedadService.class);
    }

    public His_cancer_infantilService getHis_cancer_infantilService() {
        return getServicio(His_cancer_infantilService.class);
    }

    public His_atencion_mesesService getHis_atencion_mesesService() {
        return getServicio(His_atencion_mesesService.class);
    }

    public His_atencion_embarazadaService getHis_atencion_embarazadaService() {
        return getServicio(His_atencion_embarazadaService.class);
    }

    public Agudeza_visualService getAgudeza_visualService() {
        return getServicio(Agudeza_visualService.class);
    }

    public Adulto_mayorService getAdulto_mayorService() {
        return getServicio(Adulto_mayorService.class);
    }

    public Alteracion_menorService getAlteracion_menorService() {
        return getServicio(Alteracion_menorService.class);
    }

    public Alteracion_jovenService getAlteracion_jovenService() {
        return getServicio(Alteracion_jovenService.class);
    }

    public Control_prenatalService getControl_prenatalService() {
        return getServicio(Control_prenatalService.class);
    }

    public Evolucion_menorService getEvolucion_menorService() {
        return getServicio(Evolucion_menorService.class);
    }

    public Deteccion_cancerService getDeteccion_cancerService() {
        return getServicio(Deteccion_cancerService.class);
    }

    public His_partoService getHis_partoService() {
        return getServicio(His_partoService.class);
    }

    public His_recien_nacidoService getHis_recien_nacidoService() {
        return getServicio(His_recien_nacidoService.class);
    }

    public Nota_pypService getNota_pypService() {
        return getServicio(Nota_pypService.class);
    }

    public Planificacion_familiarService getPlanificacion_familiarService() {
        return getServicio(Planificacion_familiarService.class);
    }

    public Control_menorService getControl_menorService() {
        return getServicio(Control_menorService.class);
    }

    public Detalle_solicitud_tecnicoService getDetalleSolicitudTecnicoService() {
        return getServicio(Detalle_solicitud_tecnicoService.class);
    }

    public Solicitud_tecnicoService getSolicitudTecnicoService() {
        return getServicio(Solicitud_tecnicoService.class);
    }

    public Revision_comiteService getRevisionComiteService() {
        return getServicio(Revision_comiteService.class);
    }

    public Detalle_revision_comiteService getDetalleRevisionComiteService() {
        return getServicio(Detalle_revision_comiteService.class);
    }

    public FacturacionService getFacturacionService() {
        return getServicio(FacturacionService.class);
    }

    public Detalles_horariosService getDetallesHorariosService() {
        return getServicio(Detalles_horariosService.class);
    }

    public Horarios_medicoService getHorariosMedicoService() {
        return getServicio(Horarios_medicoService.class);
    }

    public ListMapService getListMapService() {
        return getServicio(ListMapService.class);
    }

    public Control_citaService getControl_citaService() {
        return getServicio(Control_citaService.class);
    }

    public Frecuencia_ordenamientoService getFrecuencia_ordenamientoService() {
        return getServicio(Frecuencia_ordenamientoService.class);
    }

    public Edad_vencimientoService getEdad_vencimientoService() {
        return getServicio(Edad_vencimientoService.class);
    }

    public PsicologiaService getPsicologiaService() {
        return getServicio(PsicologiaService.class);
    }

    public Visita_domiciliariaService getVisita_domiciliariaService() {
        return getServicio(Visita_domiciliariaService.class);
    }

    public Remision_salud_mentalService getRemision_salud_mentalService() {
        return getServicio(Remision_salud_mentalService.class);
    }

    public BarrioService getBarrioService() {
        return getServicio(BarrioService.class);
    }

    public Hisc_deteccion_alt_embarazoService getHisc_deteccion_alt_embarazoService() {
        return getServicio(Hisc_deteccion_alt_embarazoService.class);
    }
    
    public Centro_atencionService getCentro_atencionService() {
        return getServicio(Centro_atencionService.class);
    }

    public EcografiaService getEcografiaService() {
        return getServicio(EcografiaService.class);
    }

    public FirmaService getFirmaService() {
        return getServicio(FirmaService.class);
    }

    public Firma_certificadoService getFirma_certificadoService() {
        return getServicio(Firma_certificadoService.class);
    }

    public ContabilizacionService getContabilizacionService() {
        return getServicio(ContabilizacionService.class);
    }

    public Tipo_procedimientoService getTipo_procedimientoService() {
        return getServicio(Tipo_procedimientoService.class);
    }

    public Datos_procedimientoService getDatos_procedimientoService() {
        return getServicio(Datos_procedimientoService.class);
    }

    public Porcentajes_cirugiasService getPorcentajes_cirugiasService() {
        return getServicio(Porcentajes_cirugiasService.class);
    }

    public Grupos_iss01Service getGrupos_iss01Service() {
        return getServicio(Grupos_iss01Service.class);
    }

    public Grupos_quirurgicosService getGrupos_quirurgicosService() {
        return getServicio(Grupos_quirurgicosService.class);
    }

    public Materiales_suturaService getMateriales_suturaService() {
        return getServicio(Materiales_suturaService.class);
    }

    public Sala_cirugiaService getSala_cirugiaService() {
        return getServicio(Sala_cirugiaService.class);
    }

    public Facturacion_medicamentoService getFacturacion_medicamentoService() {
        return getServicio(Facturacion_medicamentoService.class);
    }

    public Datos_medicamentosService getDatos_medicamentosService() {
        return getServicio(Datos_medicamentosService.class);
    }

    public Centro_costoService getCentro_costoService() {
        return getServicio(Centro_costoService.class);
    }

    public Facturacion_servicioService getFacturacion_servicioService() {
        return getServicio(Facturacion_servicioService.class);
    }

    public Datos_servicioService getDatos_servicioService() {
        return getServicio(Datos_servicioService.class);
    }

    public BodegaService getBodegaService() {
        return getServicio(BodegaService.class);
    }

    public Recien_nacidoService getRecien_nacidoService() {
        return getServicio(Recien_nacidoService.class);
    }

    public Resultado_paraclinicoService getResultado_paraclinicoService() {
        return getServicio(Resultado_paraclinicoService.class);
    }

    public Generacion_ripsService getGeneracion_ripsService() {
        return getServicio(Generacion_ripsService.class);
    }

    public Detalle_facturaService getDetalle_facturaService() {
        return getServicio(Detalle_facturaService.class);
    }

    public FuripsService getFuripsService() {
        return getServicio(FuripsService.class);
    }

    public Solicitud_medicamentoService getSolicitud_medicamentoService() {
        return getServicio(Solicitud_medicamentoService.class);
    }

    public Config_carnetsService getConfig_carnetsService() {
        return getServicio(Config_carnetsService.class);
    }

    public Porcentaje_adicional_medicamentoService getPorcentaje_adicional_medicamentoService() {
        return getServicio(Porcentaje_adicional_medicamentoService.class);
    }

    public OdontologiaService getOdontologiaService() {
        return getServicio(OdontologiaService.class);
    }

    public DienteService getDienteService() {
        return getServicio(DienteService.class);
    }

    public Indice_deanService getIndice_deanService() {
        return getServicio(Indice_deanService.class);
    }

    public Elm_ihcService getElm_ihcService() {
        return getServicio(Elm_ihcService.class);
    }

    public PucService getPucService() {
        return getServicio(PucService.class);
    }

    public Hisc_deteccion_alt_menor_2m_2aService getHisc_deteccion_alt_menor_2m_2aService() {
        return getServicio(Hisc_deteccion_alt_menor_2m_2aService.class);
    }

    public Hisc_deteccion_alt_menor_5a_10aService getHisc_deteccion_alt_menor_5a_10aService() {
        return getServicio(Hisc_deteccion_alt_menor_5a_10aService.class);
    }

    /* Tablas de desarrollo y crecimiento */
    public Tabla_anios_mesesService getTabla_anios_mesesService() {
        return getServicio(Tabla_anios_mesesService.class);
    }

    public Tabla_talla_pesoService getTalla_pesoService() {
        return getServicio(Tabla_talla_pesoService.class);
    }

    /**
     * Meotodo para obtener una instancia del ServiceLocator a partir de un
     * ServletContext con el aplicationContext configurado en la aplicacion.
     *
     * @param servletContext
     * @return
     */
    public static ServiceLocatorAux getInstance(ServletContext servletContext) {
        if (instance == null) {
            instance = new ServiceLocatorAux(servletContext);
        }
        return instance;
    }

    /**
     * Meotodo para obtener una instancia del ServiceLocator con el
     * aplicationContext de test, creandolo desde la ruta fisica del archivo
     *
     * @return
     */
    public static ServiceLocatorAux getInstance() {
        if (instance == null) {
            instance = new ServiceLocatorAux();
        }
        return instance;
    }

    public Hisc_hipertenso_diabeticoService getHisc_hipertenso_diabeticoService() {
        return getServicio(Hisc_hipertenso_diabeticoService.class);
    }

    public Phistorias_clinicasService getPhistorias_clinicasService() {
        return getServicio(Phistorias_clinicasService.class);
    }

    public Phistorias_paraclinicosService getPhistorias_paraclinicosService() {
        return getServicio(Phistorias_paraclinicosService.class);
    }

    public Pexamenes_paraclinicosService getPexamenes_paraclinicosService() {
        return getServicio(Pexamenes_paraclinicosService.class);
    }

    public Presultados_paraclinicosService getPresultados_paraclinicosService() {
        return getServicio(Presultados_paraclinicosService.class);
    }

    public His_atencion_crecimiento_menor2_5Service getHis_atencion_crecimiento_menor2_5Service() {
        return getServicio(His_atencion_crecimiento_menor2_5Service.class);
    }

    public PsiquiatriaService getPsiquiatriaService() {
        return getServicio(PsiquiatriaService.class);
    }

    public Coordenadas_graficasService getCoordenadas_graficasService() {
        return getServicio(Coordenadas_graficasService.class);
    }

    public MacrocomponenteService getMacrocomponenteService() {
        return getServicio(MacrocomponenteService.class);
    }

    public Evolucion_odontologiaService getEvolucion_odontologiaService() {
        return getServicio(Evolucion_odontologiaService.class);
    }

    public ConsultorioService getConsultorioService() {
        return getServicio(ConsultorioService.class);
    }

    public Consultorio_prestadorService getConsultorio_prestadorService() {
        return getServicio(Consultorio_prestadorService.class);
    }

    public Hisc_urgencia_odontologicoService getHisc_urgencia_odontologicoService() {
        return getServicio(Hisc_urgencia_odontologicoService.class);
    }

    public Hisc_consulta_externaService getHisc_consulta_externaService() {
        return getServicio(Hisc_consulta_externaService.class);
    }

    public Revision_sistemaService getRevision_sistemaService() {
        return getServicio(Revision_sistemaService.class);
    }

    public Admision_camaService getAdmision_camaService() {
        return getServicio(Admision_camaService.class);
    }

    public Detalle_orden2Service getDetalle_orden2Service() {
        return getServicio(Detalle_orden2Service.class);
    }

    public Orden_servicio2Service getOrden_servicio2Service() {
        return getServicio(Orden_servicio2Service.class);
    }

    public SigvitalesService getSigvitalesService() {
        return getServicio(SigvitalesService.class);
    }

    public Hisc_historialService getHisc_historialService() {
        return getServicio(Hisc_historialService.class);
    }

    public Imagen_diagnosticaService getImagen_diagnosticaService() {
        return getServicio(Imagen_diagnosticaService.class);
    }

    public Ficha_epidemiologia_nnService getFicha_epidemiologia_nnService() {
        return getServicio(Ficha_epidemiologia_nnService.class);
    }

    public His_triageService getHis_triageService() {
        return getServicio(His_triageService.class);
    }

}
