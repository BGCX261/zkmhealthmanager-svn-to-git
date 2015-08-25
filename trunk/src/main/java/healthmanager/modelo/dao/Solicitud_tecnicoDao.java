/*
 * Solicitud_tecnicoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Solicitud_tecnico;

public interface Solicitud_tecnicoDao {

	void crear(Solicitud_tecnico solicitud_tecnico); 

	int actualizar(Solicitud_tecnico solicitud_tecnico); 

	Solicitud_tecnico consultar(Solicitud_tecnico solicitud_tecnico); 

	int eliminar(Solicitud_tecnico solicitud_tecnico); 

	List<Solicitud_tecnico> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	boolean existe(Map<String, Object> param); 

	Solicitud_tecnico consultarP(Solicitud_tecnico solicitud_tecnico);

}