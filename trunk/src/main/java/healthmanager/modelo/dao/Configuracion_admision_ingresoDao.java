/*
 * Configuracion_admision_ingresoDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;

public interface Configuracion_admision_ingresoDao {

	void crear(Configuracion_admision_ingreso configuracion_admision_ingreso); 

	int actualizar(Configuracion_admision_ingreso configuracion_admision_ingreso); 

	Configuracion_admision_ingreso consultar(Configuracion_admision_ingreso configuracion_admision_ingreso); 

	int eliminar(Configuracion_admision_ingreso configuracion_admision_ingreso);
	
	int eliminar_via(Configuracion_admision_ingreso configuracion_admision_ingreso);

	List<Configuracion_admision_ingreso> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	List<String> listar_vias(Map<String, Object> parametros);

}