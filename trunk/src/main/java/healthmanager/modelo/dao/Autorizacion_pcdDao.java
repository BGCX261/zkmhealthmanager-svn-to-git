/*
 * Autorizacion_pcdDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Autorizacion_pcd;

public interface Autorizacion_pcdDao {

	void crear(Autorizacion_pcd autorizacion_pcd); 

	int actualizar(Autorizacion_pcd autorizacion_pcd); 

	Autorizacion_pcd consultar(Autorizacion_pcd autorizacion_pcd); 

	int eliminar(Autorizacion_pcd autorizacion_pcd); 

	List<Autorizacion_pcd> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}