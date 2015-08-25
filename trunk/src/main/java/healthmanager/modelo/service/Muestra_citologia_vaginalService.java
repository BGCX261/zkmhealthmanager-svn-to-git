/*
 * Muestra_citologia_vaginalServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Muestra_citologia_vaginal;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Muestra_citologia_vaginalDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

@Service("muestra_citologia_vaginalService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Muestra_citologia_vaginalService implements Serializable {

    @Autowired
    private Muestra_citologia_vaginalDao muestra_citologia_vaginalDao;
    @Autowired
    private ConsecutivoService consecutivoService;
    @Autowired
    private AdmisionDao admisionDao;
    private String limit;

    public Muestra_citologia_vaginal guardar(Map<String, Object> datos) {
        try {

            Muestra_citologia_vaginal muestra_citologia_vaginal = (Muestra_citologia_vaginal) datos
                    .get("muestra_citologia_vaginal");
            String accion = (String) datos.get("accion");
            Admision admision = (Admision) datos.get("admision");
            Boolean cancer_seno = (Boolean) datos.get("cobrar_cancer_seno");

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(admision);

			// generamos la factura de procedimientos solamente cuando la
            // admision no es atendida aún.
            // para que cuando se atendida y venga el paciente a traer
            // laboratorios no cobre nuevamente los proced.
            if (admision.getAtendida() != true) {
                guardarCobroProcedimientos(
                        IConstantes.PROCEDIMIENTO_LECTURA_CITOLOGIA,
                        manuales_tarifarios, admision);
                guardarCobroProcedimientos(
                        IConstantes.PROCEDIMIENTO_TOMA_CITOLOGIA,
                        manuales_tarifarios, admision);

                if (cancer_seno) {
                    guardarCobroProcedimientos(
                            IConstantes.PROCEDIMIENTO_CANCER_SENO,
                            manuales_tarifarios, admision);
                    guardarCobroProcedimientos(
                            IConstantes.PROCEDIMIENTO_CANCER_SENO2,
                            manuales_tarifarios, admision);
                }
            }

			// log.info("==> admision ===> " + admision);
            if (accion.equalsIgnoreCase("registrar")) {
                String consecutivo = consecutivoService.crearConsecutivo(
                        muestra_citologia_vaginal.getCodigo_empresa(),
                        muestra_citologia_vaginal.getCodigo_sucursal(),
                        IConstantes.CONS_MUESTRA_CITOLOGIA);
                String codigo_historia = consecutivoService.getZeroFill(
                        consecutivo, 10);
                muestra_citologia_vaginal.setCodigo_historia(codigo_historia);

                if (consultar(muestra_citologia_vaginal) != null) {
                    throw new HealthmanagerException(
                            "La historia clinica ya se encuentra en la base de datos");
                }

                muestra_citologia_vaginalDao.crear(muestra_citologia_vaginal);

                consecutivoService.actualizarConsecutivo(
                        muestra_citologia_vaginal.getCodigo_empresa(),
                        muestra_citologia_vaginal.getCodigo_sucursal(),
                        IConstantes.CONS_MUESTRA_CITOLOGIA, codigo_historia);

            } else {
                muestra_citologia_vaginal
                        .setCodigo_historia(muestra_citologia_vaginal
                                .getCodigo_historia());
                muestra_citologia_vaginalDao
                        .actualizar(muestra_citologia_vaginal);
            }
            admision.setCodigo_medico(muestra_citologia_vaginal
                    .getCodigo_prestador());
            admision.setAtendida(true);
            admisionDao.actualizar(admision);

            return muestra_citologia_vaginal;

        } catch (Exception e) {

            throw new HealthmanagerException(e.getMessage(), e);
        }

    }

    public void crear(Muestra_citologia_vaginal muestra_citologia_vaginal) {
        try {
            if (consultar(muestra_citologia_vaginal) != null) {
                throw new HealthmanagerException(
                        "Muestra_citologia_vaginal ya se encuentra en la base de datos");
            }
            muestra_citologia_vaginalDao.crear(muestra_citologia_vaginal);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizar(Muestra_citologia_vaginal muestra_citologia_vaginal) {
        try {
            return muestra_citologia_vaginalDao
                    .actualizar(muestra_citologia_vaginal);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Muestra_citologia_vaginal consultar(
            Muestra_citologia_vaginal muestra_citologia_vaginal) {
        try {
            return muestra_citologia_vaginalDao
                    .consultar(muestra_citologia_vaginal);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(Muestra_citologia_vaginal muestra_citologia_vaginal) {
        try {
            return muestra_citologia_vaginalDao
                    .eliminar(muestra_citologia_vaginal);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Muestra_citologia_vaginal> listar(Map<String, Object> parameters) {
        try {
            parameters.put("limit", limit);
            return muestra_citologia_vaginalDao.listar(parameters);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public boolean existe(Map<String, Object> parameters) {
        return muestra_citologia_vaginalDao.existe(parameters);
    }

    public Muestra_citologia_vaginal consultarPorFiltros(
            Muestra_citologia_vaginal muestra_citologia_vaginal) {
        return muestra_citologia_vaginalDao
                .consultarPorFiltros(muestra_citologia_vaginal);
    }

    public void guardarCobroProcedimientos(String procedimiento,
            Manuales_tarifarios manuales_tarifarios, Admision admision) {
        try {
            Map<String, Object> mapServicios = Utilidades.getProcedimiento(
                    manuales_tarifarios, new Long(procedimiento),
                    ServiciosDisponiblesUtils.getServiceLocator());
            ServiciosDisponiblesUtils.generarDatosProcedimientos(admision,
                    procedimiento, mapServicios, manuales_tarifarios);
        } catch (Exception e) {
            //  block
            e.printStackTrace();
        }

    }

}
