/*
 * Evolucion_medicaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Evolucion_medica;

public interface Evolucion_medicaDao {

	void crear(Evolucion_medica evolucion_medica); 

	int actualizar(Evolucion_medica evolucion_medica); 

	Evolucion_medica consultar(Evolucion_medica evolucion_medica); 

	int eliminar(Evolucion_medica evolucion_medica); 

	List<Evolucion_medica> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}