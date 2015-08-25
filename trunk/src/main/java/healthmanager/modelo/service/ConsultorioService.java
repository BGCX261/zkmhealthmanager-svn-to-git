/*
 * ConsultorioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.framework.constantes.IConstantes;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Consultorio_prestador;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.ConsultorioDao;
import healthmanager.modelo.dao.Consultorio_prestadorDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("consultorioService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConsultorioService implements Serializable {

    private String limit;

    @Autowired
    private ConsultorioDao consultorioDao;
    @Autowired
    private ConsecutivoService consecutivoService;
    @Autowired
    private Consultorio_prestadorDao consultorio_prestadorDao;

    public Consultorio guardar(Map<String, Object> datos) {
        try {
            Consultorio consultorio = (Consultorio) datos.get("consultorio");
			// List<Map<String, Object>> lista_detalle = (List<Map<String,
            // Object>>) datos
            // .get("lista_detalle");
            String accion = (String) datos.get("accion");

            if (accion.equalsIgnoreCase("registrar")) {
                String codigo_consultorio = consecutivoService
                        .crearConsecutivo(consultorio.getCodigo_empresa(),
                                consultorio.getCodigo_sucursal(),
                                IConstantes.CONS_CONSULTORIO);
                consultorio.setCodigo_consultorio(consecutivoService
                        .getZeroFill(codigo_consultorio, 4));
                if (consultar(consultorio) != null) {
                    throw new HealthmanagerException(
                            "Consultorio"
                            + codigo_consultorio
                            + " ya se encuentra en la base de datos actualize el consecutivo del consultorio");
                }
                consultorioDao.crear(consultorio);
                consecutivoService.actualizarConsecutivo(
                        consultorio.getCodigo_empresa(),
                        consultorio.getCodigo_sucursal(),
                        IConstantes.CONS_CONSULTORIO,
                        consultorio.getCodigo_consultorio());
            } else {
                consultorioDao.actualizar(consultorio);

                Consultorio_prestador auxConsultorio_prestador = new Consultorio_prestador();
                auxConsultorio_prestador.setCodigo_empresa(consultorio
                        .getCodigo_empresa());
                auxConsultorio_prestador.setCodigo_sucursal(consultorio
                        .getCodigo_sucursal());
                auxConsultorio_prestador.setCodigo_consultorio(consultorio
                        .getCodigo_consultorio());
                consultorio_prestadorDao.eliminar(auxConsultorio_prestador);
            }
			// se deja comentado por motivos de cambios
            // int i=1;
            // for (Map<String, Object> map : lista_detalle) {
            // Consultorio_prestador consultorio_prestador =
            // (Consultorio_prestador)map.get("consultorio_prestador");
            // if(consultorio_prestador==null){
            // throw new
            // Exception("Error en la fila  "+i+" registro presenta problemas (Nulo)");
            // }
            //
            // consultorio_prestador.setCodigo_empresa(consultorio.getCodigo_empresa());
            // consultorio_prestador.setCodigo_sucursal(consultorio.getCodigo_sucursal());
            // consultorio_prestador.setCodigo_consultorio(consultorio.getCodigo_consultorio());
            // if(consultorio_prestadorDao.consultar(consultorio_prestador)!=null){
            // throw new
            // Exception("Error en la fila  "+i+" registro ya existe");
            // }
            // consultorio_prestadorDao.crear(consultorio_prestador);
            // i++;
            // }

            return consultorio;
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crear(Consultorio consultorio) {
        try {
            if (consultar(consultorio) != null) {
                throw new HealthmanagerException(
                        "Consultorio ya se encuentra en la base de datos");
            }
            consultorioDao.crear(consultorio);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizar(Consultorio consultorio) {
        try {
            return consultorioDao.actualizar(consultorio);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Consultorio consultar(Consultorio consultorio) {
        try {
            return consultorioDao.consultar(consultorio);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(Consultorio consultorio) {
        try {
            return consultorioDao.eliminar(consultorio);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Consultorio> listar(Map<String, Object> parameter) {
        try {
            parameter.put("limit", limit);
            return consultorioDao.listar(parameter);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public boolean existe(Map<String, Object> parameters) {
        return this.consultorioDao.existe(parameters);
    }

}
