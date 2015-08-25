/*
 * Detalle_config_servicios_autorizacionDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_config_servicios_autorizacion;

public interface Detalle_config_servicios_autorizacionDao {

	void crear(Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion); 

	int actualizar(Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion); 

	Detalle_config_servicios_autorizacion consultar(Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion); 

	int eliminar(Detalle_config_servicios_autorizacion detalle_config_servicios_autorizacion); 

	List<Detalle_config_servicios_autorizacion> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}