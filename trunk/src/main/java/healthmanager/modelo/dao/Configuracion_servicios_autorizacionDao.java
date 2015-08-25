/*
 * Configuracion_servicios_autorizacionDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;

public interface Configuracion_servicios_autorizacionDao {

	void crear(Configuracion_servicios_autorizacion configuracion_servicios_autorizacion); 

	int actualizar(Configuracion_servicios_autorizacion configuracion_servicios_autorizacion); 

	Configuracion_servicios_autorizacion consultar(Configuracion_servicios_autorizacion configuracion_servicios_autorizacion); 

	int eliminar(Configuracion_servicios_autorizacion configuracion_servicios_autorizacion); 

	List<Configuracion_servicios_autorizacion> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param);

	List<String> listarServiciosPermitidos(Map<String, Object> parametros);  

}