/*
 * Restriccion_pcdDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Restriccion_pcd;

public interface Restriccion_pcdDao {

	void crear(Restriccion_pcd Restriccion_pcd); 

	int actualizar(Restriccion_pcd Restriccion_pcd); 

	Restriccion_pcd consultar(Restriccion_pcd Restriccion_pcd); 

	int eliminar(Restriccion_pcd Restriccion_pcd); 

	List<Restriccion_pcd> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}