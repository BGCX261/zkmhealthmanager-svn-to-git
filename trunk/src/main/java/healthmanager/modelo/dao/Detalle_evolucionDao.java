/*
 * Detalle_evolucionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_evolucion;

public interface Detalle_evolucionDao {

	void crear(Detalle_evolucion detalle_evolucion); 

	int actualizar(Detalle_evolucion detalle_evolucion); 

	Detalle_evolucion consultar(Detalle_evolucion detalle_evolucion); 

	int eliminar(Detalle_evolucion detalle_evolucion); 

	List<Detalle_evolucion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}