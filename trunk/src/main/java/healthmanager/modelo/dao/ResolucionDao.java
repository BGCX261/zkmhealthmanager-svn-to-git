/*
 * ResolucionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Resolucion;

public interface ResolucionDao {

	void crear(Resolucion resolucion); 

	int actualizar(Resolucion resolucion); 

	Resolucion consultar(Resolucion resolucion); 

	int eliminar(Resolucion resolucion); 

	List<Resolucion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}