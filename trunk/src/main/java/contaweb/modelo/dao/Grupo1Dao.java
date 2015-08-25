/*
 * Grupo1Dao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Grupo1;

public interface Grupo1Dao {

	void crear(Grupo1 grupo1); 

	int actualizar(Grupo1 grupo1); 

	Grupo1 consultar(Grupo1 grupo1); 

	int eliminar(Grupo1 grupo1); 

	List<Grupo1> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}