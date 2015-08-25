/*
 * Orden_servicioDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Orden_servicio;

public interface Orden_servicioDao {

	void crear(Orden_servicio orden_servicio); 

	int actualizar(Orden_servicio orden_servicio); 

	Orden_servicio consultar(Orden_servicio orden_servicio); 

	int eliminar(Orden_servicio orden_servicio); 

	List<Orden_servicio> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	int anularOrdenesAnteriores(Map<String, Object> param);

}