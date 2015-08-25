package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

public interface MacrocomponenteDao {
	public List<Object> listar(String statement, Map<String, Object> parametros);
}
