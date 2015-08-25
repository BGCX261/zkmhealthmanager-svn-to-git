/*
 * UrgenciasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Urgencias;

public interface UrgenciasDao {

	void crear(Urgencias urgencias); 

	int actualizar(Urgencias urgencias); 

	Urgencias consultar(Urgencias urgencias); 

	int eliminar(Urgencias urgencias); 

	List<Urgencias> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}