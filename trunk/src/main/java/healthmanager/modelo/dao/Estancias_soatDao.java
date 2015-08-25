/*
 * Estancias_soatDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Estancias_soat;

public interface Estancias_soatDao {

	void crear(Estancias_soat estancias_soat); 

	int actualizar(Estancias_soat estancias_soat); 

	Estancias_soat consultar(Estancias_soat estancias_soat); 
	
	Estancias_soat consultarEstancia(Estancias_soat estancias_soat); 

	int eliminar(Estancias_soat estancias_soat); 

	List<Estancias_soat> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 

}