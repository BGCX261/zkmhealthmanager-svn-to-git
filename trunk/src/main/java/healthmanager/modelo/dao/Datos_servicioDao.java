/*
 * Datos_servicioDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Datos_servicio;

import java.util.List;
import java.util.Map;

public interface Datos_servicioDao {

	void crear(Datos_servicio datos_servicio); 

	int actualizar(Datos_servicio datos_servicio); 

	Datos_servicio consultar(Datos_servicio datos_servicio); 

	int eliminar(Datos_servicio datos_servicio); 

	List<Datos_servicio> listar(Map<String, Object> parameters); 
	
	List<Datos_servicio> listar_factura(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 
	
	List<Map<String,Object>> servicios_hoja_gastos(Map<String,Object> param);

	Map<String, Object> getFechaRealizacion(Map<String, Object> parametro); 

}