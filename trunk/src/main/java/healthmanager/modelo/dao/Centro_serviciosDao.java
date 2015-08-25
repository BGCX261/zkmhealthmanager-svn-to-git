/*
 * Centro_serviciosDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Centro_servicios;

public interface Centro_serviciosDao {

	void crear(Centro_servicios centro_servicios); 

	int actualizar(Centro_servicios centro_servicios); 

	Centro_servicios consultar(Centro_servicios centro_servicios); 

	int eliminar(Centro_servicios centro_servicios); 

	List<Centro_servicios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}