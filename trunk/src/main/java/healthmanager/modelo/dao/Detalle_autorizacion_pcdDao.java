/*
 * Detalle_autorizacion_pcdDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_autorizacion_pcd;

public interface Detalle_autorizacion_pcdDao {

	void crear(Detalle_autorizacion_pcd detalle_autorizacion_pcd); 

	int actualizar(Detalle_autorizacion_pcd detalle_autorizacion_pcd); 

	Detalle_autorizacion_pcd consultar(Detalle_autorizacion_pcd detalle_autorizacion_pcd); 
	
	boolean consultarPcd(Map<String, Object> parameters);

	int eliminar(Detalle_autorizacion_pcd detalle_autorizacion_pcd); 

	List<Detalle_autorizacion_pcd> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}