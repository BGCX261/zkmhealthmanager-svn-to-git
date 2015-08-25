/*
 * Detalles_horariosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalles_horarios;

public interface Detalles_horariosDao {

	void crear(Detalles_horarios detalles_horarios); 

	int actualizar(Detalles_horarios detalles_horarios); 

	Detalles_horarios consultar(Detalles_horarios detalles_horarios); 
	
	Detalles_horarios consultarUltimoDisponible(Map<String, Object> parametros);

	int eliminar(Detalles_horarios detalles_horarios); 
	
	int eliminar_horario(Detalles_horarios detalles_horarios); 

	void setLimit(String limit); 
	
	List<Map<String, Object>> getViasIngresoAsignadas(Map<String, Object> map);
	
	List<Detalles_horarios> listar_por_hora(Map<String, Object> parametros);
	
	List<Detalles_horarios> listar(Map<String, Object> parameters);
	
	List<Detalles_horarios> listar_normal(Map<String, Object> parametros);
	
	List<Detalles_horarios> listar_registros(Map<String, Object> parametros);
}