/*
 * Novedades_neDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Novedades_ne;

public interface Novedades_neDao {

	void crear(Novedades_ne novedades_ne); 

	int actualizar(Novedades_ne novedades_ne); 

	Novedades_ne consultar(Novedades_ne novedades_ne); 

	int eliminar(Novedades_ne novedades_ne); 

	List<Novedades_ne> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	Integer getConsecutivo(Map<String, Object> parameters);
	
	List<Map> listarMap(Map<String,Object> param);

}