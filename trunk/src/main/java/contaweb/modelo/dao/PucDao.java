/*
 * PucDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Puc;

public interface PucDao {

	void crear(Puc puc); 

	int actualizar(Puc puc); 

	Puc consultar(Puc puc); 

	int eliminar(Puc puc); 

	List<Puc> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}