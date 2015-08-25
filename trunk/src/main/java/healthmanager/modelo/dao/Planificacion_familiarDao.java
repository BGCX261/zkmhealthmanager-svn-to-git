/*
 * Planificacion_familiarDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Planificacion_familiar;

public interface Planificacion_familiarDao {

	void crear(Planificacion_familiar planificacion_familiar); 

	int actualizar(Planificacion_familiar planificacion_familiar); 

	Planificacion_familiar consultar(Planificacion_familiar planificacion_familiar); 

	Planificacion_familiar consultar_historia(Planificacion_familiar planificacion_familiar); 

	int eliminar(Planificacion_familiar planificacion_familiar); 

	List<Planificacion_familiar> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 
	
	Integer total_registros(Map<String, Object> parameters);
	

}