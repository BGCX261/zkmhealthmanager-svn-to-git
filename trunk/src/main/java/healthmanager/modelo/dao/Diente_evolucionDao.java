/*
 * Diente_evolucionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Diente_evolucion;

public interface Diente_evolucionDao {

	void crear(Diente_evolucion diente_evolucion); 

	int actualizar(Diente_evolucion diente_evolucion); 

	Diente_evolucion consultar(Diente_evolucion diente_evolucion); 

	int eliminar(Diente_evolucion diente_evolucion); 

	List<Diente_evolucion> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}