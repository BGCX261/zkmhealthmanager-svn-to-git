/*
 * Facturacion_servicioDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Facturacion_servicio;

public interface Facturacion_servicioDao {

	void crear(Facturacion_servicio facturacion_servicio); 

	int actualizar(Facturacion_servicio facturacion_servicio); 

	Facturacion_servicio consultar(Facturacion_servicio facturacion_servicio); 

	int eliminar(Facturacion_servicio facturacion_servicio); 

	List<Facturacion_servicio> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Object param); 

}