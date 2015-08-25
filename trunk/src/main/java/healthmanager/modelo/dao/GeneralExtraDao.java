package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

public interface GeneralExtraDao {

    void crear(String queryId, Object dato);

    int actualizar(String queryId, Object dato);

    int eliminar(String queryId, Object dato);

    <T> T consultar(String queryId, T dato);

    <T> List<T> listar(String queryId, Map<String, Object> parametros);

    Long totalizar(String queryId, Map<String, Object> parametros);

    Boolean existe(String queryId, Map<String, Object> parametros);

}
