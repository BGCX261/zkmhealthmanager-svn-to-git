/*
 * Elm_ihcDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Elm_ihc;

public interface Elm_ihcDao {

	void crear(Elm_ihc elm_ihc); 

	int actualizar(Elm_ihc elm_ihc); 

	Elm_ihc consultar(Elm_ihc elm_ihc); 

	int eliminar(Elm_ihc elm_ihc); 

	List<Elm_ihc> listar(Map<String, Object> parameters); 
	
	void setLimit(String limit); 
	
	boolean existe(Map<String, Object> param); 

}