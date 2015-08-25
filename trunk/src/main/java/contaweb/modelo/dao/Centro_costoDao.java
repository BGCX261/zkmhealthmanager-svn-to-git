/*
 * Centro_costoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Centro_costo;

public interface Centro_costoDao {

	void crear(Centro_costo centro_costo); 

	int actualizar(Centro_costo centro_costo); 

	Centro_costo consultar(Centro_costo centro_costo); 
	
	Centro_costo consultarVia_ingreso(Map<String, Object> parameters);

	int eliminar(Centro_costo centro_costo); 

	List<Centro_costo> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}