/*
 * ElementoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Elemento;

public interface ElementoDao {

	void crear(Elemento elemento); 

	int actualizar(Elemento elemento); 

	Elemento consultar(Elemento elemento); 

	int eliminar(Elemento elemento); 

	List<Elemento> listar(Map<String, Object> parameters); 
	
	List<Elemento> listar(String tipo); 

	void setLimit(String limit); 
	
	boolean existe(Map<String,Object> param);

}