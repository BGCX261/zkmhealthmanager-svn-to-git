/*
 * UrgenciasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anio_soat;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Estancias_iss;
import healthmanager.modelo.bean.Estancias_soat;
import healthmanager.modelo.bean.Furips2;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Urgencias;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Estancias_soatDao;
import healthmanager.modelo.dao.Furips2Dao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.ProcedimientosDao;
import healthmanager.modelo.dao.UrgenciasDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

@Service("urgenciasService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UrgenciasService implements Serializable {

    private static Logger log = Logger.getLogger(UrgenciasService.class);

    @Autowired
    private UrgenciasDao urgenciasDao;
    @Autowired
    private ConsecutivoService consecutivoService;
    @Autowired
    private PacienteDao pacienteDao;
    @Autowired
    private AdmisionDao admisionDao;
    @Autowired
    private Estancias_soatDao estancias_soatDao;
    @Autowired
    private GeneralExtraService generalExtraService;
    @Autowired
    private FacturacionService facturacionService;
    @Autowired
    private Datos_procedimientoService datos_procedimientoService;
    @Autowired
    private Furips2Dao furips2Dao;
    @Autowired
    private ProcedimientosDao procedimientosDao;

    public void guardarUrgenciasAut(Map map) {
        try {
            String codigo_empresa = (String) map.get("codigo_empresa");
            String codigo_sucursal = (String) map.get("codigo_sucursal");
            String nro_identificacion = (String) map.get("nro_identificacion");
            String nro_ingreso = (String) map.get("nro_ingreso");
            String codigo_prestador = (String) map.get("codigo_prestador");
            String codigo_dx = (String) map.get("codigo_dx");
            String causa_muerte = (String) map.get("causa_muerte");
            String nro_factura = (String) map.get("nro_factura");
            String codigo_administradora = (String) map
                    .get("codigo_administradora");
            String id_plan = (String) map.get("id_plan");
            String estado_salida = (String) map.get("estado_salida");
            String destino_salida = (String) map.get("destino_salida");
            Timestamp fecha_ingreso = (Timestamp) map.get("fecha_ingreso");
            Timestamp fecha_egreso = (Timestamp) map.get("fecha_egreso");
            Timestamp creacion_date = (Timestamp) map.get("creacion_date");
            Timestamp ultimo_update = (Timestamp) map.get("ultimo_update");
            String creacion_user = (String) map.get("creacion_user");
            String ultimo_user = (String) map.get("ultimo_user");
            String tipo_hc = (String) map.get("tipo_hc");

            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(codigo_empresa);
            paciente.setCodigo_sucursal(codigo_sucursal);
            paciente.setNro_identificacion(nro_identificacion);
            paciente = pacienteDao.consultar(paciente);
            String tipo_identificacion = (paciente != null ? paciente
                    .getTipo_identificacion() : "CC");

            Admision admision = new Admision();
            admision.setCodigo_empresa(codigo_empresa);
            admision.setCodigo_sucursal(codigo_sucursal);
            admision.setNro_identificacion(nro_identificacion);
            admision.setNro_ingreso(nro_ingreso);
            admision = admisionDao.consultar(admision);
            String numero_autorizacion = (admision != null ? admision
                    .getNro_autorizacion() : "");

            boolean actualiza_nro_factura = false;
            if (nro_factura.equals("")) {
                nro_factura = consecutivoService.getZeroFill(consecutivoService
                        .crearConsecutivo(codigo_empresa, codigo_sucursal,
                                "URGENCIAS"), 10);
                actualiza_nro_factura = true;
            }

            Urgencias urgencias = new Urgencias();
            urgencias.setCodigo_empresa(codigo_empresa);
            urgencias.setCodigo_sucursal(codigo_sucursal);
            urgencias.setNro_factura(nro_factura);
            urgencias.setTipo_identificacion(tipo_identificacion);
            urgencias.setNro_identificacion(nro_identificacion);
            urgencias.setCodigo_administradora(codigo_administradora);
            urgencias.setId_plan(id_plan);
            urgencias.setCodigo_prestador(codigo_prestador);
            urgencias.setNro_ingreso(nro_ingreso);
            urgencias.setFecha_ingreso(fecha_ingreso);
            urgencias.setNumero_autorizacion(numero_autorizacion);
            urgencias.setCausa_externa("13");
            urgencias.setDestino_salida(destino_salida);
            urgencias.setEstado_salida(estado_salida);
            urgencias.setFecha_egreso(fecha_egreso);
            urgencias.setCodigo_diagnostico_principal(codigo_dx);
            urgencias.setCodigo_diagnostico1("");
            urgencias.setCodigo_diagnostico2("");
            urgencias.setCodigo_diagnostico3("");
            urgencias.setCausa_muerte(causa_muerte);
            urgencias.setCreacion_date(creacion_date);
            urgencias.setUltimo_update(ultimo_update);
            urgencias.setCreacion_user(creacion_user);
            urgencias.setUltimo_user(ultimo_user);
            urgencias.setTipo_hc(tipo_hc);
            urgenciasDao.crear(urgencias);
            if (actualiza_nro_factura) {
                consecutivoService.actualizarConsecutivo(codigo_empresa,
                        codigo_sucursal, "URGENCIAS", nro_factura);
            }

        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Facturacion crear(Urgencias urgencias, Admision admision,
            ServiceLocatorWeb serviceLocator) {
        try {
            String nro_factura = consecutivoService.getZeroFill(
                    consecutivoService.crearConsecutivo(
                            urgencias.getCodigo_empresa(),
                            urgencias.getCodigo_sucursal(), "URGENCIAS"), 10);
            urgencias.setNro_factura(nro_factura);
            if (consultar(urgencias) != null) {
                throw new HealthmanagerException(
                        "Urgencia ya se encuentra en la base de datos");
            }
            urgenciasDao.crear(urgencias);
            consecutivoService.actualizarConsecutivo(
                    urgencias.getCodigo_empresa(),
                    urgencias.getCodigo_sucursal(), "URGENCIAS", nro_factura);

            Facturacion facturacion = actualizarProcedimientosUrgencias(
                    urgencias, admision, serviceLocator);

            if (facturacion != null) {
                urgenciasDao.actualizar(urgencias);
                return null;
            } else {
                return guardarFactura(urgencias, admision, serviceLocator);
            }

        } catch (ValidacionRunTimeException e) {
            throw new ValidacionRunTimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crearUrgencia(Urgencias urgencias, Admision admision,
            ServiceLocatorWeb serviceLocator) {
        log.info("Ejecutando metodo @crearUrgencia()");
        try {
            String nro_factura = consecutivoService.getZeroFill(
                    consecutivoService.crearConsecutivo(
                            urgencias.getCodigo_empresa(),
                            urgencias.getCodigo_sucursal(), "URGENCIAS"), 10);
            urgencias.setNro_factura(nro_factura);
            if (consultar(urgencias) != null) {
                throw new HealthmanagerException(
                        "Urgencia ya se encuentra en la base de datos");
            }
            urgenciasDao.crear(urgencias);
            consecutivoService.actualizarConsecutivo(
                    urgencias.getCodigo_empresa(),
                    urgencias.getCodigo_sucursal(), "URGENCIAS", nro_factura);

            actualizarProcedimientosUrgencias(urgencias, admision,
                    serviceLocator);
        } catch (ValidacionRunTimeException e) {
            throw new ValidacionRunTimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    /**
     * Este metodo me permite actualizar los datos del furips cuando se atienda
     * por urgencia
     *
     * @author Luis Miguel Hernandez
     *
     */
    private void guardarVerificacionFurips(Urgencias urgencias,
            Admision admision) {
        if (admision.getCausa_externa().equals(
                ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
                || admision
                .getCausa_externa()
                .equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO)) {
            Furips2 furips2 = new Furips2();
            furips2.setCodigo_empresa(admision.getCodigo_empresa());
            furips2.setCodigo_sucursal(admision.getCodigo_sucursal());
            furips2.setNro_ingreso(admision.getNro_ingreso());
            furips2.setNro_identificacion(admision.getNro_identificacion());
            furips2 = furips2Dao.consultarPorParametros(furips2);
            if (furips2 != null) {
                furips2.setIx_codigo_diagnostico_egreso(urgencias
                        .getCodigo_diagnostico_principal());
                furips2.setIx_otro_codigo_diagnostico_egreso(urgencias
                        .getCodigo_diagnostico1());
                furips2.setIx_otro_codigo_diagnostico_egreso2(urgencias
                        .getCodigo_diagnostico2());
                furips2Dao.actualizar(furips2);
            }
        }
    }

    public int actualizar(Urgencias urgencias, Admision admision,
            ServiceLocatorWeb serviceLocator) {
        log.info("Ejecutando metodo @actualizar()");
        try {
            actualizarProcedimientosUrgencias(urgencias, admision,
                    serviceLocator);
            return urgenciasDao.actualizar(urgencias);
        } catch (ValidacionRunTimeException e) {
            throw new ValidacionRunTimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Urgencias consultar(Urgencias urgencias) {
        try {
            return urgenciasDao.consultar(urgencias);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(Urgencias urgencias) {
        try {
            return urgenciasDao.eliminar(urgencias);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Urgencias> listar(Map<String, Object> parameter) {
        try {
            return urgenciasDao.listar(parameter);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    private Facturacion actualizarProcedimientosUrgencias(Urgencias urgencias,
            Admision admision, ServiceLocatorWeb serviceLocator) throws Exception {
        log.info("Ejecutando metodo @actualizarProcedimientosUrgencias()");
        // Aqui va la condicion si cobra estancia u observacion //
        long segundos = getSegundosObservacion(urgencias, admision);
        log.info("Segundos de observacion: " + segundos);

        double hora = (segundos / 60d / 60d);
        // log.info("Horas de observacion: " + hora);

        double minute = hora * 60;

        boolean cobraObservacion = false;
        boolean cobraEstancia = false;

        if ((hora >= 2) && (hora <= 6)) {
            hora = Math.round(hora * 10) / 10;
            minute = Math.round(minute * 10) / 10;
            Notificaciones.mostrarNotificacionInformacion("Informacion",
                    "El paciente tiene " + hora + " horas y " + minute
                    + " minútos en observacion", IConstantes.TIEMPO_NOTIFICACIONES_MEDIO);
            cobraObservacion = true;
        } else if (hora > 6) {
            hora = Math.round(hora * 10) / 10;
            minute = Math.round(minute * 10) / 10;
            Notificaciones.mostrarNotificacionInformacion("Informacion",
                    "El paciente tiene " + hora + " horas y " + minute
                    + " minútos en Estancia", IConstantes.TIEMPO_NOTIFICACIONES_MEDIO);
            cobraEstancia = true;
        }

        Datos_procedimiento datos_procedimientoObs = cargamosProcedimientoObservacion(
                urgencias, admision, serviceLocator);
        Datos_procedimiento datos_procedimientoEst = cargamosProcedimientoEstancia(
                urgencias, admision, serviceLocator);

        guardarVerificacionFurips(urgencias, admision);

        Facturacion facturacion = new Facturacion();
        facturacion.setCodigo_empresa(urgencias.getCodigo_empresa());
        facturacion.setCodigo_sucursal(urgencias.getCodigo_sucursal());
        facturacion.setCodigo_tercero(urgencias.getNro_identificacion());
        facturacion.setNro_ingreso(urgencias.getNro_ingreso());
        // //log.info("facturacion antes: "+facturacion);
        facturacion = facturacionService.consultar(facturacion);

        datos_procedimientoService.eliminarRegistro(datos_procedimientoEst);
        datos_procedimientoService.eliminarRegistro(datos_procedimientoObs);

        if (cobraObservacion) {
            datos_procedimientoObs.setCodigo_registro(null);
            datos_procedimientoService.crear(datos_procedimientoObs);
        }
        if (cobraEstancia) {
            datos_procedimientoEst.setCodigo_registro(null);
            datos_procedimientoService.crear(datos_procedimientoEst);
        }

        if (facturacion != null) {
            urgenciasDao.actualizar(urgencias);
            facturacion.setFecha_inicio(urgencias.getFecha_ingreso());
            facturacion.setFecha_final(urgencias.getFecha_egreso());
            facturacionService.actualizar(facturacion);
        }
        return facturacion;
    }

    public long getSegundosObservacion(Urgencias egreso_urgencias,
            Admision admision) {
        return Math.abs(egreso_urgencias.getFecha_egreso().getTime()
                - (admision.getFecha_atencion() != null ? admision
                .getFecha_atencion().getTime() : egreso_urgencias
                .getFecha_ingreso().getTime())) / 1000;
    }

    private Datos_procedimiento cargamosProcedimientoObservacion(
            Urgencias egreso_urgencias, Admision admision,
            ServiceLocatorWeb serviceLocator) throws Exception {

        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(egreso_urgencias.getCodigo_empresa());
        paciente.setCodigo_sucursal(egreso_urgencias.getCodigo_sucursal());
        paciente.setNro_identificacion(egreso_urgencias.getNro_identificacion());
        paciente = pacienteDao.consultar(paciente);

        if (paciente == null) {
            throw new ValidacionRunTimeException(
                    "El paciente admisionado no existe en la base de datos.");
        }

        Procedimientos procedimientos = new Procedimientos();
        procedimientos
                .setCodigo_cups(IConstantes.CODIGO_CUPS_PROCEDIMIENTO_OBSERVACION);
        Long id_procedimiento = procedimientosDao
                .consultarIDPorCups(procedimientos);

        Map<String, Object> mapProcedimiento = Utilidades.getProcedimiento(
                manuales_tarifarios, id_procedimiento, serviceLocator);
        double valor_pcd = (Double) mapProcedimiento.get("valor_pcd");
        double valor_real = (Double) mapProcedimiento.get("valor_real");
        double descuento = (Double) mapProcedimiento.get("descuento");
        double incremento = (Double) mapProcedimiento.get("incremento");
        String codigo_cups = (String) mapProcedimiento.get("codigo_cups");

        Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
        datos_procedimiento.setCodigo_empresa(egreso_urgencias
                .getCodigo_empresa());
        datos_procedimiento.setCodigo_sucursal(egreso_urgencias
                .getCodigo_sucursal());
        datos_procedimiento.setCodigo_registro(null);
        datos_procedimiento.setCodigo_cups(codigo_cups);

        datos_procedimiento.setTipo_identificacion(paciente
                .getTipo_identificacion());
        datos_procedimiento.setNro_identificacion(paciente
                .getNro_identificacion());
        datos_procedimiento.setCodigo_administradora(admision
                .getCodigo_administradora());
        datos_procedimiento.setId_plan(admision.getId_plan());
        datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
        datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
        datos_procedimiento.setCodigo_procedimiento(id_procedimiento + "");
        datos_procedimiento.setFecha_procedimiento(admision.getFecha_ingreso());
        datos_procedimiento.setNumero_autorizacion("");
        datos_procedimiento.setValor_procedimiento(valor_pcd);
        datos_procedimiento.setUnidades(1);
        datos_procedimiento.setCopago(0.0);
        datos_procedimiento.setValor_neto(valor_pcd);

        // ambito de realizacion 3 urgencia
        datos_procedimiento.setAmbito_procedimiento("3");
        datos_procedimiento
                .setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
        datos_procedimiento
                .setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
        datos_procedimiento
                .setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
        datos_procedimiento.setCodigo_diagnostico_principal(admision
                .getDiagnostico_ingreso());
        datos_procedimiento.setCodigo_diagnostico_relacionado("");
        datos_procedimiento.setComplicacion("");
        datos_procedimiento.setCancelo_copago("N");
        datos_procedimiento.setCodigo_programa("");

        datos_procedimiento.setCreacion_date(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        datos_procedimiento.setUltimo_update(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        datos_procedimiento.setCreacion_user(egreso_urgencias
                .getCreacion_user());
        datos_procedimiento.setUltimo_user(egreso_urgencias.getCreacion_user());
        datos_procedimiento.setValor_real(valor_real);
        datos_procedimiento.setDescuento(descuento);
        datos_procedimiento.setIncremento(incremento);
        datos_procedimiento.setRealizado_en("");
        return datos_procedimiento;
    }

    private Datos_procedimiento cargamosProcedimientoEstancia(
            Urgencias egreso_urgencias, Admision admision,
            ServiceLocatorWeb serviceLocator) throws Exception {

        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);

        Maestro_manual maestro_manual = manuales_tarifarios.getMaestro_manual();

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(egreso_urgencias.getCodigo_empresa());
        paciente.setCodigo_sucursal(egreso_urgencias.getCodigo_sucursal());
        paciente.setNro_identificacion(egreso_urgencias.getNro_identificacion());
        paciente = pacienteDao.consultar(paciente);

        if (paciente == null) {
            throw new ValidacionRunTimeException(
                    "El paciente admisionado no existe en la base de datos.");
        }

        Datos_procedimiento datos_procedimiento = new Datos_procedimiento();

        if (maestro_manual.getTipo_manual()
                .equals(IConstantes.TIPO_MANUAL_SOAT)) {
            Empresa empresa = new Empresa();
            empresa.setCodigo_empresa(egreso_urgencias.getCodigo_empresa());
            empresa = generalExtraService.consultar(empresa);
            Integer nivelEmpresa = ConvertidorDatosUtil.convertirDatot(empresa
                    .getNivel());
            // log.info("===>nivel " + nivelEmpresa);

            Estancias_soat estancias_soat = new Estancias_soat();
            estancias_soat.setEstancia("1");
            estancias_soat.setNivel(nivelEmpresa);
            estancias_soat.setNro_camas(4);
            // log.info("====> estancia111 " + estancias_soat);
            estancias_soat = estancias_soatDao
                    .consultarEstancia(estancias_soat);
            // log.info("====> estancia" + estancias_soat);

            double porcentaje = estancias_soat.getPorcentaje();
            String codigo_procedimiento = estancias_soat.getCodigo();

            Anio_soat anio_soat = new Anio_soat();
            anio_soat.setAnio(manuales_tarifarios.getAnio());
            anio_soat = serviceLocator.getServicio(GeneralExtraService.class).consultar(
                    anio_soat);

            double valor_pcd = (anio_soat != null ? (int) (anio_soat
                    .getValor_anio() * porcentaje) : 0);

            Map<String, Object> mapProcedimiento = Utilidades.getProcedimiento(
                    manuales_tarifarios, new Long(codigo_procedimiento),
                    serviceLocator);
            double valor_real = (Double) mapProcedimiento.get("valor_real");
            double descuento = (Double) mapProcedimiento.get("descuento");
            double incremento = (Double) mapProcedimiento.get("incremento");
            String codigo_cups = (String) mapProcedimiento.get("codigo_cups");

            datos_procedimiento.setCodigo_empresa(egreso_urgencias
                    .getCodigo_empresa());
            datos_procedimiento.setCodigo_sucursal(egreso_urgencias
                    .getCodigo_sucursal());
            datos_procedimiento.setCodigo_registro(null);
            datos_procedimiento.setCodigo_cups(codigo_cups);

            datos_procedimiento.setTipo_identificacion(paciente
                    .getTipo_identificacion());
            datos_procedimiento.setNro_identificacion(paciente
                    .getNro_identificacion());
            datos_procedimiento.setCodigo_administradora(admision
                    .getCodigo_administradora());
            datos_procedimiento.setId_plan(admision.getId_plan());
            datos_procedimiento
                    .setCodigo_prestador(admision.getCodigo_medico());
            datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
            datos_procedimiento.setCodigo_procedimiento(codigo_procedimiento);
            datos_procedimiento.setFecha_procedimiento(admision
                    .getFecha_ingreso());
            datos_procedimiento.setNumero_autorizacion("");
            datos_procedimiento.setValor_procedimiento(valor_pcd);
            datos_procedimiento.setUnidades(1);
            datos_procedimiento.setCopago(0.0);
            datos_procedimiento.setValor_neto(valor_pcd);

            // ambito de realizacion 3 urgencia
            datos_procedimiento.setAmbito_procedimiento("3");
            datos_procedimiento
                    .setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
            datos_procedimiento
                    .setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
            datos_procedimiento
                    .setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
            datos_procedimiento.setCodigo_diagnostico_principal(admision
                    .getDiagnostico_ingreso());
            datos_procedimiento.setCodigo_diagnostico_relacionado("");
            datos_procedimiento.setComplicacion("");
            datos_procedimiento.setCancelo_copago("N");
            datos_procedimiento.setCodigo_programa("");

            datos_procedimiento.setCreacion_date(new Timestamp(
                    new GregorianCalendar().getTimeInMillis()));
            datos_procedimiento.setUltimo_update(new Timestamp(
                    new GregorianCalendar().getTimeInMillis()));
            datos_procedimiento.setCreacion_user(egreso_urgencias
                    .getCreacion_user());
            datos_procedimiento.setUltimo_user(egreso_urgencias
                    .getCreacion_user());
            datos_procedimiento.setValor_real(valor_real);
            datos_procedimiento.setDescuento(descuento);
            datos_procedimiento.setIncremento(incremento);
            datos_procedimiento.setRealizado_en("");

        } else if (maestro_manual.getTipo_manual().equals(
                IConstantes.TIPO_MANUAL_ISS01)) {
            Empresa empresa = new Empresa();
            empresa.setCodigo_empresa(egreso_urgencias.getCodigo_empresa());
            empresa = generalExtraService.consultar(empresa);
            Integer nivelEmpresa = ConvertidorDatosUtil.convertirDatot(empresa
                    .getNivel());
            // log.info("===>nivel " + nivelEmpresa);

            Estancias_iss estancias_iss = new Estancias_iss();
            estancias_iss.setEstancia("1");
            estancias_iss.setNivel(nivelEmpresa);
            estancias_iss.setNro_camas(4);
            estancias_iss = serviceLocator.getServicio(
                    Estancias_issService.class)
                    .consultarEstancia(estancias_iss);
            // log.info("====> estancia" + estancias_iss);

            double valor_pcd = (estancias_iss.getValor());
            String codigo_procedimiento = estancias_iss.getCodigo();

            Map<String, Object> mapProcedimiento = Utilidades.getProcedimiento(
                    manuales_tarifarios, new Long(codigo_procedimiento),
                    serviceLocator);
            double valor_real = (Double) mapProcedimiento.get("valor_real");
            double descuento = (Double) mapProcedimiento.get("descuento");
            double incremento = (Double) mapProcedimiento.get("incremento");
            String codigo_cups = (String) mapProcedimiento.get("codigo_cups");

            datos_procedimiento.setCodigo_empresa(egreso_urgencias
                    .getCodigo_empresa());
            datos_procedimiento.setCodigo_sucursal(egreso_urgencias
                    .getCodigo_sucursal());
            datos_procedimiento.setCodigo_registro(null);
            datos_procedimiento.setCodigo_cups(codigo_cups);

            datos_procedimiento.setTipo_identificacion(paciente
                    .getTipo_identificacion());
            datos_procedimiento.setNro_identificacion(paciente
                    .getNro_identificacion());
            datos_procedimiento.setCodigo_administradora(admision
                    .getCodigo_administradora());
            datos_procedimiento.setId_plan(admision.getId_plan());
            datos_procedimiento
                    .setCodigo_prestador(admision.getCodigo_medico());
            datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
            datos_procedimiento.setCodigo_procedimiento(codigo_procedimiento);
            datos_procedimiento.setFecha_procedimiento(admision
                    .getFecha_ingreso());
            datos_procedimiento.setNumero_autorizacion("");
            datos_procedimiento.setValor_procedimiento(valor_pcd);
            datos_procedimiento.setUnidades(1);
            datos_procedimiento.setCopago(0.0);
            datos_procedimiento.setValor_neto(valor_pcd);

            // ambito de realizacion 3 urgencia
            datos_procedimiento.setAmbito_procedimiento("3");
            datos_procedimiento
                    .setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
            datos_procedimiento
                    .setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
            datos_procedimiento
                    .setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
            datos_procedimiento.setCodigo_diagnostico_principal(admision
                    .getDiagnostico_ingreso());
            datos_procedimiento.setCodigo_diagnostico_relacionado("");
            datos_procedimiento.setComplicacion("");
            datos_procedimiento.setCancelo_copago("N");
            datos_procedimiento.setCodigo_programa("");

            datos_procedimiento.setCreacion_date(new Timestamp(
                    new GregorianCalendar().getTimeInMillis()));
            datos_procedimiento.setUltimo_update(new Timestamp(
                    new GregorianCalendar().getTimeInMillis()));
            datos_procedimiento.setCreacion_user(egreso_urgencias
                    .getCreacion_user());
            datos_procedimiento.setUltimo_user(egreso_urgencias
                    .getCreacion_user());
            datos_procedimiento.setValor_real(valor_real);
            datos_procedimiento.setDescuento(descuento);
            datos_procedimiento.setIncremento(incremento);
            datos_procedimiento.setRealizado_en("");

        }

        return datos_procedimiento;
    }

    private Facturacion guardarFactura(Urgencias egreso_urgencias,
            Admision admision, ServiceLocatorWeb serviceLocator) throws Exception {
        GregorianCalendar vence = new GregorianCalendar();
        vence.setTimeInMillis(egreso_urgencias.getFecha_egreso().getTime());
        vence.set(Calendar.DATE, vence.get(Calendar.DATE) + 30);

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(egreso_urgencias.getCodigo_empresa());
        paciente.setCodigo_sucursal(egreso_urgencias.getCodigo_sucursal());
        paciente.setNro_identificacion(egreso_urgencias.getNro_identificacion());
        paciente = pacienteDao.consultar(paciente);

        if (paciente == null) {
            throw new ValidacionRunTimeException(
                    "El paciente admisionado no existe en la base de datos.");
        }

        List<Detalle_factura> lista_detalle = new ArrayList<Detalle_factura>();
        Via_ingreso_contratadas via_ingreso_contratadas = ServiciosDisponiblesUtils
                .getVia_ingreso_contratadas(admision);
        Map<String, Object> serviciosCargados = serviceLocator
                .getFacturacionService().consultarAdmisionFactura(admision,
                        egreso_urgencias.getFecha_egreso(),/*
                         * new ArrayList<
                         * Detalle_factura
                         * >()
                         */ lista_detalle,
                        via_ingreso_contratadas, "registrar");

        lista_detalle = (List<Detalle_factura>) serviciosCargados
                .get("lista_detalle");
        Facturacion facturacion_totales = (Facturacion) serviciosCargados
                .get("facturacion_totales");

        Facturacion facturacion = new Facturacion();
        facturacion.setCodigo_empresa(egreso_urgencias.getCodigo_empresa());
        facturacion.setCodigo_sucursal(egreso_urgencias.getCodigo_sucursal());
        facturacion.setCodigo_comprobante("15");

        facturacion.setTipo_identificacion(paciente.getTipo_identificacion());
        facturacion.setCodigo_tercero(paciente.getNro_identificacion());
        facturacion.setNro_ingreso(admision.getNro_ingreso());
        facturacion.setDocumento_externo("");
        facturacion.setCodigo_empleado("");
        facturacion.setFecha(new Timestamp(egreso_urgencias.getFecha_egreso()
                .getTime()));
        facturacion
                .setFecha_vencimiento(new Timestamp(vence.getTimeInMillis()));
        facturacion.setFecha_inicio(new Timestamp(egreso_urgencias
                .getFecha_ingreso().getTime()));
        facturacion.setFecha_final(new Timestamp(egreso_urgencias
                .getFecha_egreso().getTime()));
        facturacion.setCodigo_administradora(admision
                .getCodigo_administradora());
        facturacion.setId_plan(admision.getId_plan());
        facturacion.setNro_contrato("");
        facturacion.setNro_poliza("");
        facturacion.setTotal_cuotas(1);
        facturacion.setPlazo(30);
        facturacion.setForma_pago("02");
        facturacion.setDescripcion("FACTURACION DE PROCEDIMIENTOS Y CONSULTAS");
        facturacion.setObservacion("");
        facturacion.setValor_total(facturacion_totales.getValor_total());
        facturacion.setValor_cuota(facturacion_totales.getValor_cuota());
        facturacion.setValor_copago(facturacion_totales.getValor_copago());
        facturacion
                .setNocopago(ServiciosDisponiblesUtils.pagaCopago(admision) ? "N"
                        : "S");
        facturacion.setDto_factura(facturacion_totales.getDto_factura());
        facturacion.setDto_copago(facturacion_totales.getDto_copago());
        facturacion.setCop_canc(0);
        // facturacion.setTipo("IND");
        facturacion.setEstado("PEND");
        // facturacion.setFactura(factura);
        facturacion.setPost("N");
        facturacion.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        facturacion.setCreacion_user(egreso_urgencias.getCreacion_user());
        facturacion.setUltimo_user(egreso_urgencias.getUltimo_user());
        facturacion.setCreacion_date(new Timestamp(Calendar.getInstance()
                .getTimeInMillis()));
        facturacion.setCodigo_credito("");
        facturacion.setRemision("");
        facturacion.setPrefijo("");
        facturacion.setAnio(new java.text.SimpleDateFormat("yyyy")
                .format(facturacion.getFecha()));
        facturacion.setMes(new java.text.SimpleDateFormat("MM")
                .format(facturacion.getFecha()));
        facturacion.setClasificacion("");
        facturacion.setVerificado("N");
        facturacion.setRetencion(0.0);
        facturacion.setFactura("");
        facturacion.setRadicado("N");
        facturacion.setAuditado("N");
        facturacion.setValor_pagar_factura(facturacion.getValor_total());
        facturacion.setFact_glosada("N");

        Map<String, Object> datos = new HashMap<String, Object>();
        datos.put("facturacion", facturacion);
        datos.put("lista_detalle", lista_detalle);
        datos.put("accion", "registrar");
        datos.put("contratos",
                ServiciosDisponiblesUtils.getContratosAdmision(admision));
        return facturacionService.guardarDatos(datos);
    }

}
