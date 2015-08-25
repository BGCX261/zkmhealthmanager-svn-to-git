/*
 * Detalle_solicitud_tecnicoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_solicitud_tecnico;

public interface Detalle_solicitud_tecnicoDao {

	void crear(Detalle_solicitud_tecnico detalle_solicitud_tecnico); 

	int actualizar(Detalle_solicitud_tecnico detalle_solicitud_tecnico); 

	Detalle_solicitud_tecnico consultar(Detalle_solicitud_tecnico detalle_solicitud_tecnico); 

	int eliminar(Detalle_solicitud_tecnico detalle_solicitud_tecnico); 

	List<Detalle_solicitud_tecnico> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}