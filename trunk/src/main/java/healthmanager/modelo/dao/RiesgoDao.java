/*
 * RiesgoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Riesgo;

public interface RiesgoDao {

	void crear(Riesgo riesgo); 

	int actualizar(Riesgo riesgo); 

	Riesgo consultar(Riesgo riesgo); 

	int eliminar(Riesgo riesgo); 

	List<Riesgo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}