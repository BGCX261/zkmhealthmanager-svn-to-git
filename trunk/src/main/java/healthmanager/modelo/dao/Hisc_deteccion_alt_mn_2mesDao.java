/*
 * Hisc_deteccion_alt_mn_2mesDao.java
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
import healthmanager.modelo.bean.Hisc_deteccion_alt_mn_2mes;

public interface Hisc_deteccion_alt_mn_2mesDao {

	void crear(Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes); 

	int actualizar(Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes); 

	Hisc_deteccion_alt_mn_2mes consultar(Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes); 

	int eliminar(Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes); 

	List<Hisc_deteccion_alt_mn_2mes> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

	Integer total_registros(Map<String, Object> parameters);
}