package healthmanager.modelo.service;

import java.util.List;
import java.util.Map;

import com.framework.modelo.util.DaoUtil;
import healthmanager.modelo.dao.GeneralExtraDao;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("generalExtraService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeneralExtraService
        implements Serializable {

    @Autowired
    private GeneralExtraDao generalExtraDao;

    public <T> void crear(T dato) {
        String queryId = DaoUtil.getQueryId(dato) + DaoUtil.CREAR;
        generalExtraDao.crear(queryId, dato);
    }

    public void crear(String queryId, Object dato) {
        generalExtraDao.crear(queryId, dato);
    }

    public <T> int actualizar(T dato) {
        String queryId = DaoUtil.getQueryId(dato) + DaoUtil.ACTUALIZAR;
        return generalExtraDao.actualizar(queryId, dato);
    }

    public int actualizar(String queryId, Object dato) {
        return generalExtraDao.actualizar(queryId, dato);
    }

    public <T> T consultar(T dato) {
        String queryId = DaoUtil.getQueryId(dato) + DaoUtil.CONSULTAR;
        return generalExtraDao.consultar(queryId, dato);
    }

    public Object consultar(String queryId, Object dato) {
        return generalExtraDao.consultar(queryId, dato);
    }

    public <T> int eliminar(T dato) {
        String queryId = DaoUtil.getQueryId(dato) + DaoUtil.ELIMINAR;
        return generalExtraDao.eliminar(queryId, dato);
    }

    public int eliminar(String queryId, Object dato) {
        return generalExtraDao.eliminar(queryId, dato);
    }
    
    public <T> int eliminar(T dato, String queryId) {
        String queryId_aux = DaoUtil.getQueryId(dato) +"."+queryId;
        return generalExtraDao.eliminar(queryId_aux, dato);
    }

    public <T> List<T> listar(Class<?> clase, Map<String, Object> parametros) {
        String queryId = DaoUtil.getQueryId(clase) + DaoUtil.LISTAR;
        return generalExtraDao.listar(queryId, parametros);
    }

    public <T> List<T> listar(Class<?> clase, String queryId, Map<String, Object> parametros) {
        String queryId_aux = DaoUtil.getQueryId(clase) + "." + queryId;
        return generalExtraDao.listar(queryId_aux, parametros);
    }

    public <T> List<T> listar(String queryId, Map<String, Object> parametros) {
        return generalExtraDao.listar(queryId, parametros);
    }

    public Long totalizar(Class<?> clase, Map<String, Object> parametros) {
        String queryId = DaoUtil.getQueryId(clase) + DaoUtil.TOTALIZAR;
        return generalExtraDao.totalizar(queryId, parametros);
    }

    public Long totalizar(String queryId, Map<String, Object> parametros) {
        return generalExtraDao.totalizar(queryId, parametros);
    }

    public Boolean existe(Class<?> clase, Map<String, Object> parametros) {
        String queryId = DaoUtil.getQueryId(clase) + DaoUtil.EXISTE;
        return generalExtraDao.existe(queryId, parametros);
    }

    public Boolean existe(String queryId, Map<String, Object> parametros) {
        return generalExtraDao.existe(queryId, parametros);
    }

    public GeneralExtraDao getGeneralExtraDao() {
        return generalExtraDao;
    }

    public void setGeneralExtraDao(GeneralExtraDao generalExtraDao) {
        this.generalExtraDao = generalExtraDao;
    }

}
