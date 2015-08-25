/*
 * ResolucionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import contaweb.modelo.bean.Resolucion;

@Repository("resolucionContawebDao")
public interface ResolucionDao {

	void crear(Resolucion resolucion); 

	int actualizar(Resolucion resolucion); 

	Resolucion consultar(Resolucion resolucion); 

	int eliminar(Resolucion resolucion); 

	List<Resolucion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}