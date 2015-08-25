/*
 * ConsecutivoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Consecutivo;

public interface ConsecutivoDao {

	void crear(Consecutivo consecutivo); 

	int actualizar(Consecutivo consecutivo); 

	Consecutivo consultar(Consecutivo consecutivo); 

	int eliminar(Consecutivo consecutivo); 

	List<Consecutivo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}