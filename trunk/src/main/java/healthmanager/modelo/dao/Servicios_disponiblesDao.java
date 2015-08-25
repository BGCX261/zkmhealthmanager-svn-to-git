/*
 * Servicios_disponiblesDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Servicios_disponibles;

public interface Servicios_disponiblesDao {

	void crear(Servicios_disponibles servicios_disponibles); 

	int actualizar(Servicios_disponibles servicios_disponibles); 

	Servicios_disponibles consultar(Servicios_disponibles servicios_disponibles); 

	int eliminar(Servicios_disponibles servicios_disponibles); 

	List<Servicios_disponibles> listar(Map<String,Object> parameters); 
	
	Long cantidad_hijos(Servicios_disponibles servicios_disponibles);

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	List<String> listar_codigos(Map<String, Object> parametros);

}