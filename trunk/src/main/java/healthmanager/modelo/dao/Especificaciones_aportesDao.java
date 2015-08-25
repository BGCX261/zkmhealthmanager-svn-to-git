/*
 * Especificaciones_aportesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Especificaciones_aportes;

public interface Especificaciones_aportesDao {

	void crear(Especificaciones_aportes especificaciones_aportes); 

	int actualizar(Especificaciones_aportes especificaciones_aportes); 

	Especificaciones_aportes consultar(Especificaciones_aportes especificaciones_aportes); 

	int eliminar(Especificaciones_aportes especificaciones_aportes); 

	List<Especificaciones_aportes> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}