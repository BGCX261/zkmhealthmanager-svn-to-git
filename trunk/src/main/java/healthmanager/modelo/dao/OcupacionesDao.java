/*
 * OcupacionesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Ocupaciones;

public interface OcupacionesDao {

	void crear(Ocupaciones ocupaciones); 

	int actualizar(Ocupaciones ocupaciones); 

	Ocupaciones consultar(Ocupaciones ocupaciones); 

	int eliminar(Ocupaciones ocupaciones); 

	List<Ocupaciones> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}