/*
 * NovedadDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Novedad;

public interface NovedadDao {

	void crear(Novedad novedad); 

	int actualizar(Novedad novedad); 

	Novedad consultar(Novedad novedad); 

	int eliminar(Novedad novedad); 

	List<Novedad> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}