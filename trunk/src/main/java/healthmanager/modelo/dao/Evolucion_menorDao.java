/*
 * Evolucion_menorDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Evolucion_menor;

public interface Evolucion_menorDao {

	void crear(Evolucion_menor evolucion_menor); 

	int actualizar(Evolucion_menor evolucion_menor); 

	Evolucion_menor consultar(Evolucion_menor evolucion_menor); 

	int eliminar(Evolucion_menor evolucion_menor); 

	List<Evolucion_menor> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}