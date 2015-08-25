/*
 * Estancias_issDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Estancias_iss;

public interface Estancias_issDao {

	void crear(Estancias_iss estancias_iss); 

	int actualizar(Estancias_iss estancias_iss); 

	Estancias_iss consultar(Estancias_iss estancias_iss); 
	
	Estancias_iss consultarEstancia(Estancias_iss estancias_iss); 

	int eliminar(Estancias_iss estancias_iss); 

	List<Estancias_iss> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}