/*
 * Respuesta_solicitud_e4Dao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Respuesta_solicitud_e4;

public interface Respuesta_solicitud_e4Dao {

	void crear(Respuesta_solicitud_e4 respuesta_solicitud_e4); 

	int actualizar(Respuesta_solicitud_e4 respuesta_solicitud_e4); 

	Respuesta_solicitud_e4 consultar(Respuesta_solicitud_e4 respuesta_solicitud_e4); 

	int eliminar(Respuesta_solicitud_e4 respuesta_solicitud_e4); 

	List<Respuesta_solicitud_e4> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	List<Map> listarMap(Map<String,Object> param);
}