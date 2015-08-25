/*
 * His_atencion_crecimiento_menor2_5ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Escala_del_desarrollo;
import healthmanager.modelo.bean.His_atencion_crecimiento_menor2_5;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.CitasDao;
import healthmanager.modelo.dao.Escala_del_desarrolloDao;
import healthmanager.modelo.dao.His_atencion_crecimiento_menor2_5Dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("his_atencion_crecimiento_menor2_5Service")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class His_atencion_crecimiento_menor2_5Service implements Serializable {

    @Autowired
    private His_atencion_crecimiento_menor2_5Dao his_atencion_crecimiento_menor2_5Dao;
    @Autowired
    private ConsecutivoService consecutivoService;
    @Autowired
    private Coordenadas_graficasService coordenadas_graficasService;
    @Autowired
    private AdmisionDao admisionDao;
    @Autowired
    private Escala_del_desarrolloDao escala_del_desarrolloDao;
    @Autowired
    private Receta_ripsService receta_ripsService;
    @Autowired
    private Orden_servicioService orden_servicioService;
    @Autowired
    private Impresion_diagnosticaService impresion_diagnosticaService;
    @Autowired
    private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;
    @Autowired
    private Remision_internaService remision_internaService;
    @Autowired
    private Agudeza_visualService agudeza_visualService;

    @Autowired
    private Anexo9_entidadDao anexo9_entidadDao;
    // manuel

    @Autowired
    private CitasDao citasDao;

    public boolean cobrar_agudeza;
    @Autowired
    private Orden_autorizacionService orden_autorizacionService;

    @Autowired
    private GeneralExtraService generalExtraService;

    public void crear(
            His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5) {
        try {
            if (consultar(his_atencion_crecimiento_menor2_5) != null) {
                throw new Exception(
                        "La historia clinica ya se encuentra en la base de datos");
            }
            his_atencion_crecimiento_menor2_5Dao
                    .crear(his_atencion_crecimiento_menor2_5);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void guardarDatos(Map<String, Object> datos) {
        try {
            His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) datos
                    .get("historia_clinica");
            Admision admision = (Admision) datos.get("admision");
            Citas cita = (Citas) datos.get("cita_seleccionada");
            if (admision == null) {
                throw new Exception("No hay admision que actualizar");
            }
            Integer anio = (Integer) datos.get("anio");

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(admision);

            ArrayList<Coordenadas_graficas> coordenadas = (ArrayList<Coordenadas_graficas>) datos
                    .get("coordenadas");
            Escala_del_desarrollo escala_del_desarrollo = (Escala_del_desarrollo) datos
                    .get("escala_del_desarrollo");

            String accion = (String) datos.get("accion");
            Map<String, Object> mapRecetas = (Map<String, Object>) datos
                    .get("receta_medica");
            Map<String, Object> mapOrdenServicio = (Map<String, Object>) datos
                    .get("orden_servicio");

            Remision_interna remision_interna = (Remision_interna) datos
                    .get("remision_interna");
            Agudeza_visual agudeza_visual = (Agudeza_visual) datos
                    .get("agudeza_visual");

            agudeza_visual.setCodigo_historia(his_atencion_crecimiento_menor2_5
                    .getCodigo_historia());
            agudeza_visual.setIdentificacion(admision.getPaciente()
                    .getNro_identificacion());

            cobrar_agudeza = (Boolean) datos.get("cobrar_agudeza");

            if (cobrar_agudeza) {
                Map<String, Object> mapServicios = Utilidades.getProcedimiento(
                        manuales_tarifarios, new Long(
                                IConstantes.PROCEDIMIENTO_AGUDEZA_VISUAL),
                        ServiciosDisponiblesUtils.getServiceLocator());
                ServiciosDisponiblesUtils.generarDatosProcedimientos(admision,
                        IConstantes.PROCEDIMIENTO_AGUDEZA_VISUAL, mapServicios,
                        manuales_tarifarios);
            }

            remision_interna.setCodigo_paciente(admision.getPaciente()
                    .getNro_identificacion());
            remision_interna
                    .setCodigo_historia(his_atencion_crecimiento_menor2_5
                            .getCodigo_historia());
            remision_interna.setFecha_inicio(his_atencion_crecimiento_menor2_5
                    .getFecha_inicial());

            Historia_clinica historia_clinica = new Historia_clinica();
            historia_clinica
                    .setCodigo_empresa(his_atencion_crecimiento_menor2_5
                            .getCodigo_empresa());
            historia_clinica
                    .setCodigo_sucursal(his_atencion_crecimiento_menor2_5
                            .getCodigo_sucursal());
            historia_clinica
                    .setFecha_historia(his_atencion_crecimiento_menor2_5
                            .getFecha_inicial());
            historia_clinica.setNro_identificacion(admision
                    .getNro_identificacion());
            historia_clinica.setNro_ingreso(admision.getNro_ingreso());
            historia_clinica.setVia_ingreso(admision.getVia_ingreso());
            historia_clinica.setPrestador(his_atencion_crecimiento_menor2_5
                    .getCreacion_user());

            if (accion.equalsIgnoreCase("registrar")) {
                generalExtraService.crear(historia_clinica);
                his_atencion_crecimiento_menor2_5
                        .setCodigo_historia(historia_clinica
                                .getCodigo_historia());
                his_atencion_crecimiento_menor2_5Dao
                        .crear(his_atencion_crecimiento_menor2_5);
                remision_interna
                        .setCodigo_historia(his_atencion_crecimiento_menor2_5
                                .getCodigo_historia());
                agudeza_visual
                        .setCodigo_historia(his_atencion_crecimiento_menor2_5
                                .getCodigo_historia());

                if (coordenadas != null) {
                    for (Coordenadas_graficas cg : coordenadas) {
                        cg.setCodigo_historia(his_atencion_crecimiento_menor2_5
                                .getCodigo_historia());
                        coordenadas_graficasService.crear(cg);
                    }
                }
            } else {

                if (generalExtraService.consultar(historia_clinica) != null) {
                    if (consultar(his_atencion_crecimiento_menor2_5) == null) {
                        throw new Exception(
                                "El dato que desea actualizar no existe en la base de datos");
                    }
                    his_atencion_crecimiento_menor2_5Dao
                            .actualizar(his_atencion_crecimiento_menor2_5);
                }
                if (coordenadas != null) {
                    for (Coordenadas_graficas cg : coordenadas) {
                        coordenadas_graficasService.actualizar(cg);
                    }
                }

            }

            if (remision_internaService.consultar(remision_interna) == null) {
                remision_internaService.crear(remision_interna);
            } else {
                remision_internaService.actualizar(remision_interna);
            }

            if (cobrar_agudeza) {
                if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
                    if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
                        if (agudeza_visualService.consultar(agudeza_visual) == null) {
                            agudeza_visualService.crear(agudeza_visual);
                        } else {
                            agudeza_visualService.actualizar(agudeza_visual);
                        }
                    }
                }
            }

            Orden_servicio orden_servicio = (Orden_servicio) ((Map<String, Object>) datos
                    .get("orden_servicio")).get("orden_servicio");

            Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
                    .get("impresion_diagnostica");

            orden_servicio.setCodigo_dx(impresion_diagnostica
                    .getCie_principal());

            /* guardamos receta */
            List<Detalle_orden> detalle_ordens = (List<Detalle_orden>) mapOrdenServicio
                    .get("lista_detalle");
            List<Detalle_receta_rips> detalle_receta_rips = (List<Detalle_receta_rips>) mapRecetas
                    .get("lista_detalle");

            if (!detalle_receta_rips.isEmpty()) {
                mapRecetas.put("receta_rips",
                        receta_ripsService.guardar(mapRecetas));
            } else {
                mapRecetas.put("receta_rips", null);
            }
            if (!detalle_ordens.isEmpty()) {
                mapOrdenServicio.put("orden_servicio",
                        orden_servicioService.guardar(mapOrdenServicio));
            } else {
                mapOrdenServicio.put("orden_servicio", null);
            }

            escala_del_desarrollo
                    .setCodigo_historia(his_atencion_crecimiento_menor2_5
                            .getCodigo_historia());
            escala_del_desarrollo
                    .setVia_ingreso(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS);

            if (escala_del_desarrolloDao.consultar(escala_del_desarrollo) != null) {
                escala_del_desarrolloDao.actualizar(escala_del_desarrollo);
            } else {
                escala_del_desarrolloDao.crear(escala_del_desarrollo);
            }

            admision.setCodigo_medico(historia_clinica.getPrestador());
            admision.setAtendida(true);
            if (cita != null) {
                cita.setEstado("2");
                citasDao.actualizar(cita);
            }
            admision.setDiagnostico_ingreso(impresion_diagnostica
                    .getCie_principal());
            admisionDao.actualizar(admision);

            // Creamos autorizacion
            Map<String, Object> mapDatos = (Map<String, Object>) datos
                    .get("autorizacion");
            if (mapDatos != null) {
                orden_autorizacionService.guardarOrden(mapDatos,
                        impresion_diagnostica, admision);
            }
            /* generamos la consulta */
            crearConsulta(his_atencion_crecimiento_menor2_5, admision,
                    impresion_diagnostica);

            FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
                    his_atencion_crecimiento_menor2_5.getCodigo_historia(),
                    datos, ficha_epidemiologia_nnService,
                    impresion_diagnosticaService,
                    his_atencion_crecimiento_menor2_5.getCreacion_user());

            Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
                    .get("anexo9");

            if (anexo9_entidad != null) {
                anexo9_entidad
                        .setNro_historia(his_atencion_crecimiento_menor2_5
                                .getCodigo_historia());
                anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
                Anexo9_entidad anexo9_aux = anexo9_entidadDao
                        .consultar(anexo9_entidad);
                if (anexo9_aux != null) {
                    anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
                    anexo9_entidadDao.actualizar(anexo9_entidad);
                } else {
                    String consecutivo = consecutivoService.crearConsecutivo(
                            his_atencion_crecimiento_menor2_5
                            .getCodigo_empresa(),
                            his_atencion_crecimiento_menor2_5
                            .getCodigo_sucursal(),
                            IConstantes.CONS_ANEXO_9);
                    String codigo_anexo = consecutivoService.getZeroFill(
                            consecutivo, 10);
                    anexo9_entidad.setCodigo(codigo_anexo);
                    anexo9_entidadDao.crear(anexo9_entidad);
                    consecutivoService.actualizarConsecutivo(
                            his_atencion_crecimiento_menor2_5
                            .getCodigo_empresa(),
                            his_atencion_crecimiento_menor2_5
                            .getCodigo_sucursal(),
                            IConstantes.CONS_ANEXO_9, codigo_anexo);
                }
            } else {
                anexo9_entidad = new Anexo9_entidad();
                anexo9_entidad.setCodigo_empresa(admision.getCodigo_empresa());
                anexo9_entidad
                        .setCodigo_sucursal(admision.getCodigo_sucursal());
                anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
                anexo9_entidad.setCodigo_paciente(admision
                        .getNro_identificacion());
                anexo9_entidadDao.eliminar(anexo9_entidad);
            }

        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crearConsulta(Object historiasConsultas, Admision admision,
            Impresion_diagnostica impresion_diagnostica) throws Exception {
        His_atencion_crecimiento_menor2_5 atencion_crecimiento_menor2_5 = (His_atencion_crecimiento_menor2_5) historiasConsultas;
        Map map = new HashMap();
        map.put("codigo_empresa",
                atencion_crecimiento_menor2_5.getCodigo_empresa());
        map.put("codigo_sucursal",
                atencion_crecimiento_menor2_5.getCodigo_sucursal());
        map.put("nro_identificacion",
                atencion_crecimiento_menor2_5.getIdentificacion());
        map.put("nro_ingreso", atencion_crecimiento_menor2_5.getNro_ingreso());
        map.put("codigo_prestador",
                atencion_crecimiento_menor2_5.getCreacion_user());
        map.put("codigo_dx", impresion_diagnostica.getCie_principal());
        map.put("creacion_date",
                atencion_crecimiento_menor2_5.getCreacion_date());
        map.put("ultimo_update",
                atencion_crecimiento_menor2_5.getUltimo_update());
        map.put("creacion_user",
                atencion_crecimiento_menor2_5.getCreacion_user());
        map.put("ultimo_user", atencion_crecimiento_menor2_5.getUltimo_user());
        map.put("tipo_hc", "");
        map.put("fecha_ingreso", admision.getFecha_ingreso());
        map.put("impresion_diagnostica", impresion_diagnostica);
        map.put("admision", admision);
        ServiciosDisponiblesUtils.generarConsulta(map);
    }

    public int actualizar(
            His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5) {
        try {
            return his_atencion_crecimiento_menor2_5Dao
                    .actualizar(his_atencion_crecimiento_menor2_5);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public His_atencion_crecimiento_menor2_5 consultar(
            His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5) {
        try {
            return his_atencion_crecimiento_menor2_5Dao
                    .consultar(his_atencion_crecimiento_menor2_5);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Integer total_registros(Map<String, Object> parameters) {
        try {
            return his_atencion_crecimiento_menor2_5Dao
                    .total_registros(parameters);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(
            His_atencion_crecimiento_menor2_5 his_atencion_crecimiento_menor2_5) {
        try {
        	Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(his_atencion_crecimiento_menor2_5.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(his_atencion_crecimiento_menor2_5
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(his_atencion_crecimiento_menor2_5
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<His_atencion_crecimiento_menor2_5> listar(
            Map<String, Object> parameter) {
        try {
            return his_atencion_crecimiento_menor2_5Dao.listar(parameter);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public boolean existe(Map<String, Object> parameters) {
        return this.his_atencion_crecimiento_menor2_5Dao.existe(parameters);
    }

}
