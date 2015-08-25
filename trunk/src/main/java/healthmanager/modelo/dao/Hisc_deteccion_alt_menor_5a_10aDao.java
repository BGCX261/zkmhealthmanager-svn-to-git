/*
 * Hisc_deteccion_alt_menor_5a_10aDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hisc_deteccion_alt_menor_5a_10a;

public interface Hisc_deteccion_alt_menor_5a_10aDao {

	void crear(Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a); 

	int actualizar(Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a); 

	Hisc_deteccion_alt_menor_5a_10a consultar(Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a); 

	int eliminar(Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a); 

	List<Hisc_deteccion_alt_menor_5a_10a> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param);
	
	
	Integer total_registros(Map<String, Object> parameters); 

}