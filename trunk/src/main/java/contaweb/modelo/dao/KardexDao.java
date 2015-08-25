/*
 * KardexDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Kardex;

public interface KardexDao {

	void crear(Kardex kardex); 

	int actualizar(Kardex kardex); 

	Kardex consultar(Kardex kardex); 

	int eliminar(Kardex kardex); 

	List<Kardex> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}