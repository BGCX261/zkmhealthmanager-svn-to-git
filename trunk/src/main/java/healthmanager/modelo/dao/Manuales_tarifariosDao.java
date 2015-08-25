/*
 * Manuales_tarifariosDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Servicios_contratados;

import java.util.List;
import java.util.Map;

public interface Manuales_tarifariosDao {

	void crear(Manuales_tarifarios manuales_tarifarios); 

	int actualizar(Manuales_tarifarios manuales_tarifarios); 

	Manuales_tarifarios consultar(Manuales_tarifarios manuales_tarifarios);
	
	Manuales_tarifarios consultarDesdeServicio(Servicios_contratados servicios_contratados);
	
//	Manuales_tarifarios consultarParametrizado(Map<String, Object> map);
	
	Manuales_tarifarios consultar_parametrizado(Map<String, Object> map);
	
	List<Manuales_tarifarios> listar_parametrizado(Map<String, Object> map);

	int eliminar(Manuales_tarifarios manuales_tarifarios); 
	
	int eliminarDeUnContrato(Manuales_tarifarios manuales_tarifarios);

	List<Manuales_tarifarios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}