/*
 * His_tarjeta_tuberculosisServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.His_contactos_tuberculosis;
import healthmanager.modelo.bean.His_evaluacion_tuberculosis;
import healthmanager.modelo.bean.His_fases_tuberculosis;
import healthmanager.modelo.bean.His_tarjeta_tuberculosis;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.His_contactos_tuberculosisDao;
import healthmanager.modelo.dao.His_evaluacion_tuberculosisDao;
import healthmanager.modelo.dao.His_tarjeta_tuberculosisDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("his_tarjeta_tuberculosisService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class His_tarjeta_tuberculosisService implements Serializable {

    @Autowired
    private His_tarjeta_tuberculosisDao his_tarjeta_tuberculosisDao;
    @Autowired
    private ConsecutivoService consecutivoService;
    @Autowired
    private His_contactos_tuberculosisDao his_contactos_tuberculosisDao;
    @Autowired
    private His_evaluacion_tuberculosisDao his_evaluacion_tuberculosisDao;
    @Autowired
    private His_fases_tuberculosisService his_fases_tuberculosisService;
    @Autowired
    private AdmisionDao admisionDao;

    @Autowired
    private ProcedimientosService procedimientoService;
    @Autowired
    private PrestadoresService prestadoresService;

    private String limit;

    public His_tarjeta_tuberculosis guardar(Map<String, Object> datos) {
        try {

            His_tarjeta_tuberculosis his_tarjeta_tuberculosis = (His_tarjeta_tuberculosis) datos
                    .get("his_tarjeta_tuberculosis");
            List<His_contactos_tuberculosis> listado_componentes_contactos = (List<His_contactos_tuberculosis>) datos
                    .get("listado_componentes_contactos");
            List<His_evaluacion_tuberculosis> listado_componentes_evaluacion = (List<His_evaluacion_tuberculosis>) datos
                    .get("listado_componentes_evaluacion");
            Map<String, Map<String, His_fases_tuberculosis>> mapa_fases_1 = (Map<String, Map<String, His_fases_tuberculosis>>) datos
                    .get("mapa_fases_1");
            Map<String, Map<String, His_fases_tuberculosis>> mapa_fases_2 = (Map<String, Map<String, His_fases_tuberculosis>>) datos
                    .get("mapa_fases_2");
            Admision admision = (Admision) datos.get("admision");
            Boolean entrega_medicamento = (Boolean) datos
                    .get("entrega_medicamento");

            // Cancela el guardado si la tarno se pasa por parametro la tarjeta
            if (his_tarjeta_tuberculosis == null) {
                return his_tarjeta_tuberculosis;
            }

            if (his_tarjeta_tuberculosis.getCodigo_historia() == null
                    || his_tarjeta_tuberculosis.getCodigo_historia().isEmpty()) {
                String codigo_historia = consecutivoService.crearConsecutivo(
                        his_tarjeta_tuberculosis.getCodigo_empresa(),
                        his_tarjeta_tuberculosis.getCodigo_sucursal(),
                        IConstantes.CONS_HIS_TUBERCULOSIS);

                codigo_historia = consecutivoService.getZeroFill(
                        codigo_historia, 10);
                his_tarjeta_tuberculosis.setCodigo_historia(codigo_historia);

                if (consultar(his_tarjeta_tuberculosis) != null) {
                    throw new HealthmanagerException(
                            " Nro "
                            + codigo_historia
                            + " ya se encuentra en la base de datos actualize el consecutivo del registro");
                }

                his_tarjeta_tuberculosisDao.crear(his_tarjeta_tuberculosis);
                consecutivoService.actualizarConsecutivo(
                        his_tarjeta_tuberculosis.getCodigo_empresa(),
                        his_tarjeta_tuberculosis.getCodigo_sucursal(),
                        IConstantes.CONS_HIS_TUBERCULOSIS,
                        his_tarjeta_tuberculosis.getCodigo_historia());
                // //log.info("5: "+codigo_historia);

            } else {
                his_tarjeta_tuberculosisDao
                        .actualizar(his_tarjeta_tuberculosis);
            }

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa",
                    his_tarjeta_tuberculosis.getCodigo_empresa());
            parameters.put("codigo_sucursal",
                    his_tarjeta_tuberculosis.getCodigo_sucursal());
            parameters.put("identificacion",
                    his_tarjeta_tuberculosis.getIdentificacion());
            his_contactos_tuberculosisDao.eliminar(parameters);

            if (listado_componentes_contactos != null && !entrega_medicamento) {
                for (His_contactos_tuberculosis his_contactos_tuberculosis : listado_componentes_contactos) {

                    String codigo_contactos = consecutivoService
                            .crearConsecutivo(his_tarjeta_tuberculosis
                                    .getCodigo_empresa(),
                                    his_tarjeta_tuberculosis
                                    .getCodigo_sucursal(),
                                    IConstantes.CONS_HIS_TUBERCULOSIS_CONTACTOS);

                    codigo_contactos = consecutivoService.getZeroFill(
                            codigo_contactos, 10);

                    his_contactos_tuberculosis
                            .setCodigo_empresa(his_tarjeta_tuberculosis
                                    .getCodigo_empresa());
                    his_contactos_tuberculosis
                            .setCodigo_sucursal(his_tarjeta_tuberculosis
                                    .getCodigo_sucursal());
                    his_contactos_tuberculosis
                            .setCodigo_historia(codigo_contactos);
                    his_contactos_tuberculosis
                            .setIdentificacion(his_tarjeta_tuberculosis
                                    .getIdentificacion());
                    his_contactos_tuberculosis.setCreacion_date(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    his_contactos_tuberculosis.setUltimo_update(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    his_contactos_tuberculosis
                            .setCreacion_user(his_tarjeta_tuberculosis
                                    .getCreacion_user());
                    his_contactos_tuberculosis.setDelete_date(null);
                    his_contactos_tuberculosis
                            .setUltimo_user(his_tarjeta_tuberculosis
                                    .getUltimo_user());
                    his_contactos_tuberculosis.setDelete_user(null);

                    his_contactos_tuberculosisDao
                            .crear(his_contactos_tuberculosis);

                    consecutivoService.actualizarConsecutivo(
                            his_contactos_tuberculosis.getCodigo_empresa(),
                            his_contactos_tuberculosis.getCodigo_sucursal(),
                            IConstantes.CONS_HIS_TUBERCULOSIS_CONTACTOS,
                            his_contactos_tuberculosis.getCodigo_historia());

                }
            }

            Map<String, Object> parameters_evaluacion = new HashMap<String, Object>();
            parameters_evaluacion.put("codigo_empresa",
                    his_tarjeta_tuberculosis.getCodigo_empresa());
            parameters_evaluacion.put("codigo_sucursal",
                    his_tarjeta_tuberculosis.getCodigo_sucursal());
            parameters_evaluacion.put("identificacion",
                    his_tarjeta_tuberculosis.getIdentificacion());
            his_evaluacion_tuberculosisDao.eliminar(parameters_evaluacion);

            if (listado_componentes_evaluacion != null && !entrega_medicamento) {
                for (His_evaluacion_tuberculosis his_evaluacion_tuberculosis : listado_componentes_evaluacion) {

                    String codigo_evaluacion = consecutivoService
                            .crearConsecutivo(
                                    his_tarjeta_tuberculosis
                                    .getCodigo_empresa(),
                                    his_tarjeta_tuberculosis
                                    .getCodigo_sucursal(),
                                    IConstantes.CONS_HIS_TUBERCULOSIS_EVALUACION);

                    codigo_evaluacion = consecutivoService.getZeroFill(
                            codigo_evaluacion, 10);

                    his_evaluacion_tuberculosis
                            .setCodigo_empresa(his_tarjeta_tuberculosis
                                    .getCodigo_empresa());
                    his_evaluacion_tuberculosis
                            .setCodigo_sucursal(his_tarjeta_tuberculosis
                                    .getCodigo_sucursal());
                    his_evaluacion_tuberculosis
                            .setCodigo_historia(codigo_evaluacion);
                    his_evaluacion_tuberculosis
                            .setIdentificacion(his_tarjeta_tuberculosis
                                    .getIdentificacion());
                    his_evaluacion_tuberculosis.setCreacion_date(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    his_evaluacion_tuberculosis.setUltimo_update(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    his_evaluacion_tuberculosis
                            .setCreacion_user(his_tarjeta_tuberculosis
                                    .getCreacion_user());
                    his_evaluacion_tuberculosis.setDelete_date(null);
                    his_evaluacion_tuberculosis
                            .setUltimo_user(his_tarjeta_tuberculosis
                                    .getUltimo_user());
                    his_evaluacion_tuberculosis.setDelete_user(null);

                    his_evaluacion_tuberculosisDao
                            .crear(his_evaluacion_tuberculosis);

                    consecutivoService.actualizarConsecutivo(
                            his_evaluacion_tuberculosis.getCodigo_empresa(),
                            his_evaluacion_tuberculosis.getCodigo_sucursal(),
                            IConstantes.CONS_HIS_TUBERCULOSIS_EVALUACION,
                            his_evaluacion_tuberculosis.getCodigo_historia());

                }
            }

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("codigo_empresa",
                    his_tarjeta_tuberculosis.getCodigo_empresa());
            param.put("codigo_sucursal",
                    his_tarjeta_tuberculosis.getCodigo_sucursal());
            param.put("codigo_historia",
                    his_tarjeta_tuberculosis.getCodigo_historia());
            List<His_fases_tuberculosis> fases = his_fases_tuberculosisService
                    .listar(param);

            Integer aplicacion1_el = 0;
            // Integer aplicacion2_el = 0;

            for (His_fases_tuberculosis fase : fases) {
                if (fase.getAplicacion().equalsIgnoreCase("1")) {
                    aplicacion1_el++;
                } else {
                    // aplicacion2_el++;
                }
                his_fases_tuberculosisService.eliminar(fase);
            }

            Integer aplicacion1_n = 0;
            // Integer aplicacion2_n = 0;

            for (String key_meses : mapa_fases_1.keySet()) {
                Map<String, His_fases_tuberculosis> mapa_mes = mapa_fases_1
                        .get(key_meses);
                for (String key_dia : mapa_mes.keySet()) {
                    His_fases_tuberculosis his_fases_tuberculosis_aux = mapa_mes
                            .get(key_dia);
                    his_fases_tuberculosis_aux
                            .setCodigo_historia(his_tarjeta_tuberculosis
                                    .getCodigo_historia());
                    his_fases_tuberculosis_aux
                            .setIdentificacion(his_tarjeta_tuberculosis
                                    .getIdentificacion());
                    his_fases_tuberculosisService
                            .crear(his_fases_tuberculosis_aux);

                    if (his_fases_tuberculosis_aux.getAplicacion()
                            .equalsIgnoreCase("1")) {
                        aplicacion1_n++;
                    } else {
                        // aplicacion2_n++;
                    }
                }
            }

            for (String key_meses : mapa_fases_2.keySet()) {
                Map<String, His_fases_tuberculosis> mapa_mes = mapa_fases_2
                        .get(key_meses);
                for (String key_dia : mapa_mes.keySet()) {
                    His_fases_tuberculosis his_fases_tuberculosis_aux = mapa_mes
                            .get(key_dia);
                    his_fases_tuberculosis_aux
                            .setCodigo_historia(his_tarjeta_tuberculosis
                                    .getCodigo_historia());
                    his_fases_tuberculosis_aux
                            .setIdentificacion(his_tarjeta_tuberculosis
                                    .getIdentificacion());
                    his_fases_tuberculosisService
                            .crear(his_fases_tuberculosis_aux);

                    if (his_fases_tuberculosis_aux.getAplicacion()
                            .equalsIgnoreCase("1")) {
                        aplicacion1_n++;
                    } else {
                        // aplicacion2_n++;
                    }
                }
            }

            Integer aplicaciones1 = aplicacion1_n - aplicacion1_el;
            // Integer aplicaciones2 = aplicacion2_n-aplicacion2_el;

            if (admision != null) {
                // Si es por enfermeria (control de tuberculosis)
                if (admision.getVia_ingreso().equals(
                        IVias_ingreso.CONTROL_TUBERCULOSIS)) {
                    admision.setCodigo_medico(his_tarjeta_tuberculosis
                            .getCreacion_user());
                    admision.setAtendida(true);
                    admisionDao.actualizar(admision);
                    crearConsultaTratamiento(admision, aplicaciones1,
                            entrega_medicamento);
                    // facturacion por enfermeria

                } else {
                    // Si es por consulta externa
                    guardarProcedimientosHistoria(admision, aplicaciones1);
                    // facturacion por ce
                }
            }

            return his_tarjeta_tuberculosis;

        } catch (Exception e) {

            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crear(His_tarjeta_tuberculosis his_tarjeta_tuberculosis) {
        try {
            if (consultar(his_tarjeta_tuberculosis) != null) {
                throw new HealthmanagerException(
                        "His_tarjeta_tuberculosis ya se encuentra en la base de datos");
            }
            his_tarjeta_tuberculosisDao.crear(his_tarjeta_tuberculosis);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizar(His_tarjeta_tuberculosis his_tarjeta_tuberculosis) {
        try {
            return his_tarjeta_tuberculosisDao
                    .actualizar(his_tarjeta_tuberculosis);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public His_tarjeta_tuberculosis consultar(
            His_tarjeta_tuberculosis his_tarjeta_tuberculosis) {
        try {
            return his_tarjeta_tuberculosisDao
                    .consultar(his_tarjeta_tuberculosis);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public His_tarjeta_tuberculosis consultar_historia(
            His_tarjeta_tuberculosis his_tarjeta_tuberculosis) {
        try {
            return his_tarjeta_tuberculosisDao
                    .consultar_historia(his_tarjeta_tuberculosis);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(His_tarjeta_tuberculosis his_tarjeta_tuberculosis) {
        try {
            return his_tarjeta_tuberculosisDao
                    .eliminar(his_tarjeta_tuberculosis);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<His_tarjeta_tuberculosis> listar(Map<String, Object> parameters) {
        try {
            parameters.put("limit", limit);
            return his_tarjeta_tuberculosisDao.listar(parameters);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public boolean existe(Map<String, Object> parameters) {
        return his_tarjeta_tuberculosisDao.existe(parameters);
    }

    public void guardarProcedimientosHistoria(Admision admision,
            Integer inyecciones) throws Exception {
        // log.info(">>>>>>>inyecciones>" + inyecciones);
        if (inyecciones > 0) {
            guardarProcedimiento(admision, IConstantes.PROCEDIMIENTO_992990);
        }
        guardarProcedimiento(admision, IConstantes.PROCEDIMIENTO_990101);
        guardarProcedimiento(admision, IConstantes.PROCEDIMIENTO_990201);
    }

    public void crearConsultaTratamiento(Admision admision,
            Integer inyecciones, Boolean entrega_medicamento) throws Exception {
        // log.info(">>>>>>>inyecciones>" + inyecciones);
        if (!entrega_medicamento) {
            Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
            impresion_diagnostica.setCodigo_empresa(admision
                    .getCodigo_empresa());
            impresion_diagnostica.setCodigo_sucursal(admision
                    .getCodigo_sucursal());
            impresion_diagnostica.setFinalidad_consulta("01");
            impresion_diagnostica.setTipo_principal("1");

            Map map = new HashMap();
            map.put("codigo_empresa", admision.getCodigo_empresa());
            map.put("codigo_sucursal", admision.getCodigo_sucursal());
            map.put("nro_identificacion", admision.getNro_identificacion());
            map.put("nro_ingreso", admision.getNro_ingreso());
            map.put("codigo_prestador", admision.getCodigo_medico());
            map.put("codigo_dx", "Z000");
            map.put("creacion_date", admision.getCreacion_date());
            map.put("ultimo_update", admision.getUltimo_update());
            map.put("creacion_user", admision.getCreacion_user());
            map.put("ultimo_user", admision.getUltimo_user());
            map.put("tipo_hc", "");
            map.put("fecha_ingreso", admision.getFecha_ingreso());
            map.put("impresion_diagnostica", impresion_diagnostica);
            map.put("admision", admision);

            ServiciosDisponiblesUtils.generarConsulta(map);

            Prestadores prestador = new Prestadores();
            prestador.setNro_identificacion(admision.getCodigo_medico());
            prestador.setCodigo_empresa(admision.getCodigo_empresa());
            prestador.setCodigo_sucursal(admision.getCodigo_sucursal());

            prestador = prestadoresService.consultar(prestador);

            if (prestador != null) {
				// log.info(">>>>>>>>>prestador>" + prestador.getNombres());
                // log.info(">>>>>>>>>tipo>" + prestador.getTipo_prestador());
                if (prestador.getTipo_prestador().equals("01")) {
                    // Prestador
                    guardarProcedimiento(admision,
                            IConstantes.PROCEDIMIENTO_990101);
                    guardarProcedimiento(admision,
                            IConstantes.PROCEDIMIENTO_990201);
                } else {
                    // Enfermera
                    guardarProcedimiento(admision,
                            IConstantes.PROCEDIMIENTO_990104);
                    guardarProcedimiento(admision,
                            IConstantes.PROCEDIMIENTO_990204);
                }
            }
        }

        if (inyecciones > 0) {
            guardarProcedimiento(admision, IConstantes.PROCEDIMIENTO_990201);
        }
    }

    private void guardarProcedimiento(Admision admision, String codigo)
            throws Exception {
        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);
        Map<String, Object> servicios = new HashMap<String, Object>();
        servicios.put("procedimientoService", procedimientoService);

        Map<String, Object> mapServicios = Utilidades.getProcedimiento(
                manuales_tarifarios, new Long(codigo),
                ServiciosDisponiblesUtils.getServiceLocator());
        ServiciosDisponiblesUtils.generarDatosProcedimientos(admision, codigo,
                mapServicios, manuales_tarifarios);
    }

}
