/*
 * Configuracion_admision_ingresoServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Via_ingreso_especialidad;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.dao.Configuracion_admision_ingresoDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("configuracion_admision_ingresoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Configuracion_admision_ingresoService implements Serializable {

    @Autowired
    private Configuracion_admision_ingresoDao configuracion_admision_ingresoDao;
    
    @Autowired
    private GeneralExtraService generalExtraService;

    public void guardarDatos(Map<String, Object> mapa_datos) {
        try {
            String codigo_empresa = (String) mapa_datos.get("codigo_empresa");
            String codigo_sucursal = (String) mapa_datos.get("codigo_sucursal");
            List<Map<String, Object>> listado_datos = (List<Map<String, Object>>) mapa_datos
                    .get("listado_datos");

            Configuracion_admision_ingreso configuracion_admision_ingreso2 = new Configuracion_admision_ingreso();
            configuracion_admision_ingreso2.setCodigo_empresa(codigo_empresa);
            configuracion_admision_ingreso2.setCodigo_sucursal(codigo_sucursal);
            configuracion_admision_ingresoDao
                    .eliminar_via(configuracion_admision_ingreso2);

            for (Map<String, Object> mapa_bean : listado_datos) {
                Configuracion_admision_ingreso configuracion_admision_ingreso = (Configuracion_admision_ingreso) mapa_bean
                        .get("configuracion_admision_ingreso");
                List<Via_ingreso_rol> listado_via_roles = (List<Via_ingreso_rol>) mapa_bean
                        .get("listado_via_roles");
                List<Via_ingreso_especialidad> listado_via_especialidades = (List<Via_ingreso_especialidad>) mapa_bean
                        .get("listado_via_especialidades");

                Via_ingreso_rol via_ingreso_rol_aux = new Via_ingreso_rol();
                via_ingreso_rol_aux
                        .setCodigo_empresa(configuracion_admision_ingreso
                                .getCodigo_empresa());
                via_ingreso_rol_aux
                        .setCodigo_via_ingreso(configuracion_admision_ingreso
                                .getVia_ingreso());

                generalExtraService.eliminar(via_ingreso_rol_aux);

                Via_ingreso_especialidad via_ingreso_especialidad_aux = new Via_ingreso_especialidad();
                via_ingreso_especialidad_aux
                        .setCodigo_empresa(configuracion_admision_ingreso
                                .getCodigo_empresa());
                via_ingreso_especialidad_aux
                        .setCodigo_via_ingreso(configuracion_admision_ingreso
                                .getVia_ingreso());

                generalExtraService
                        .eliminar(via_ingreso_especialidad_aux);

                configuracion_admision_ingresoDao
                        .crear(configuracion_admision_ingreso);

                for (Via_ingreso_rol via_ingreso_rol : listado_via_roles) {
                    generalExtraService.crear(via_ingreso_rol);
                }

                for (Via_ingreso_especialidad via_ingreso_especialidad : listado_via_especialidades) {
                    generalExtraService.crear(via_ingreso_especialidad);
                }
            }
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crear(
            Configuracion_admision_ingreso configuracion_admision_ingreso) {
        try {
            if (consultar(configuracion_admision_ingreso) != null) {
                throw new HealthmanagerException(
                        "Configuracion_admision_ingreso ya se encuentra en la base de datos");
            }
            configuracion_admision_ingresoDao
                    .crear(configuracion_admision_ingreso);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizar(
            Configuracion_admision_ingreso configuracion_admision_ingreso) {
        try {
            return configuracion_admision_ingresoDao
                    .actualizar(configuracion_admision_ingreso);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Configuracion_admision_ingreso consultar(
            Configuracion_admision_ingreso configuracion_admision_ingreso) {
        try {
            return configuracion_admision_ingresoDao
                    .consultar(configuracion_admision_ingreso);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(
            Configuracion_admision_ingreso configuracion_admision_ingreso) {
        try {
            return configuracion_admision_ingresoDao
                    .eliminar(configuracion_admision_ingreso);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar_via(
            Configuracion_admision_ingreso configuracion_admision_ingreso) {
        try {
            return configuracion_admision_ingresoDao
                    .eliminar_via(configuracion_admision_ingreso);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Configuracion_admision_ingreso> listar(
            Map<String, Object> parameters) {
        try {
            return configuracion_admision_ingresoDao.listar(parameters);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public boolean existe(Map<String, Object> parameters) {
        return configuracion_admision_ingresoDao.existe(parameters);
    }

    public List<String> listar_vias(Map<String, Object> parametros) {
        try {
            return configuracion_admision_ingresoDao.listar_vias(parametros);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

}
