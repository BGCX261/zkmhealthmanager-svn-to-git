/*
 * Receta_ripsDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Receta_rips;

public interface Receta_ripsDao {

	void crear(Receta_rips receta_rips); 

	int actualizar(Receta_rips receta_rips); 

	Receta_rips consultar(Receta_rips receta_rips); 

	int eliminar(Receta_rips receta_rips); 

	List<Receta_rips> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	Integer totalResultados(Map<String, Object> parameters);

}