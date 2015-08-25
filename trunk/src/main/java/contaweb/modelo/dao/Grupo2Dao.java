/*
 * Grupo2Dao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Grupo2;

public interface Grupo2Dao {

	void crear(Grupo2 grupo2); 

	int actualizar(Grupo2 grupo2); 

	Grupo2 consultar(Grupo2 grupo2); 

	int eliminar(Grupo2 grupo2); 

	List<Grupo2> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}