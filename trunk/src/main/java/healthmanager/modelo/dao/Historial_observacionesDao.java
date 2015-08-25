/*
 * Historial_observacionesDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Historial_observaciones;

public interface Historial_observacionesDao {

	void crear(Historial_observaciones historial_observaciones); 

	int actualizar(Historial_observaciones historial_observaciones); 

	Historial_observaciones consultar(Historial_observaciones historial_observaciones); 

	int eliminar(Historial_observaciones historial_observaciones); 

	List<Historial_observaciones> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}