/*
 * ElementoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import contaweb.modelo.bean.Elemento;

@Repository("elementoContawebDao")
public interface ElementoDao {

	void crear(Elemento elemento); 

	int actualizar(Elemento elemento); 

	Elemento consultar(Elemento elemento); 

	int eliminar(Elemento elemento); 

	List<Elemento> listar(Map<String, Object> parameters);

	void setLimit(String limit); 

}