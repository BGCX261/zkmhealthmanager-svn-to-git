/*
 * His_atencion_embarazadaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_atencion_embarazada;

public interface His_atencion_embarazadaDao {

	void crear(His_atencion_embarazada his_atencion_embarazada); 

	int actualizar(His_atencion_embarazada his_atencion_embarazada); 

	His_atencion_embarazada consultar(His_atencion_embarazada his_atencion_embarazada); 

	int eliminar(His_atencion_embarazada his_atencion_embarazada); 

	List<His_atencion_embarazada> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}