/*
 * AdminDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Totales_registros;

import java.util.List;
import java.util.Map;

public interface Totales_registrosDao {

	void crear(Totales_registros totales_registros); 

	int actualizar(Totales_registros totales_registros); 

	Totales_registros consultar(Totales_registros totales_registros); 
	
	Totales_registros consultar_informacion(Totales_registros totales_registros); 

	int eliminar(Totales_registros totales_registros); 

	List<Totales_registros> listar(Map<String,Object> parameters); 
	
	List<String> listar_esquemas(Map<String, Object> parametros);
	
	List<String> listar_tablas(Map<String, Object> parametros);


}