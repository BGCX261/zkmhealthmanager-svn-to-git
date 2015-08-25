/*
 * Metodos_planificacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Metodos_planificacion;

public interface Metodos_planificacionDao {

	void crear(Metodos_planificacion metodos_planificacion); 

	int actualizar(Metodos_planificacion metodos_planificacion); 

	Metodos_planificacion consultar(Metodos_planificacion metodos_planificacion); 

	int eliminar(Metodos_planificacion metodos_planificacion); 

	List<Metodos_planificacion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}