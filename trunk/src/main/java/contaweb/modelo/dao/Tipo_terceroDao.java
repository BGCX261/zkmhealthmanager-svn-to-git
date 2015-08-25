/*
 * Tipo_terceroDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Tipo_tercero;

public interface Tipo_terceroDao {

	void crear(Tipo_tercero tipo_tercero); 

	int actualizar(Tipo_tercero tipo_tercero); 

	Tipo_tercero consultar(Tipo_tercero tipo_tercero); 

	int eliminar(Tipo_tercero tipo_tercero); 

	List<Tipo_tercero> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}