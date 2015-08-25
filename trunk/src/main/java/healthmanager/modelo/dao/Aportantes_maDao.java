/*
 * Aportantes_maDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Aportantes_ma;

public interface Aportantes_maDao {

	void crear(Aportantes_ma aportantes_ma); 

	int actualizar(Aportantes_ma aportantes_ma); 

	Aportantes_ma consultar(Aportantes_ma aportantes_ma); 

	int eliminar(Aportantes_ma aportantes_ma); 

	List<Aportantes_ma> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	List<Map<String, Object>> listarMap(Map<String, Object> param);
}