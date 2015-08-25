/*
 * Solicitud_e1Dao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Solicitud_e1;

public interface Solicitud_e1Dao {

	void crear(Solicitud_e1 solicitud_e1); 

	int actualizar(Solicitud_e1 solicitud_e1); 

	Solicitud_e1 consultar(Solicitud_e1 solicitud_e1); 

	int eliminar(Solicitud_e1 solicitud_e1); 

	List<Solicitud_e1> listar(Map<String, Object> parameters); 

	void setLimit(String limit);
	
	List<Map> listarMap(Map<String,Object> param);

}