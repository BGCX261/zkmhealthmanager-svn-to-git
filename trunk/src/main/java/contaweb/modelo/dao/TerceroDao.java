/*
 * TerceroDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Tercero;

public interface TerceroDao {

	void crear(Tercero tercero); 

	int actualizar(Tercero tercero); 

	Tercero consultar(Tercero tercero); 

	int eliminar(Tercero tercero); 

	List<Tercero> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}