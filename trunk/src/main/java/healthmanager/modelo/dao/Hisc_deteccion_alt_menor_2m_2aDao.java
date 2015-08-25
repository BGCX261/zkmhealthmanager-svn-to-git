/*
 * Hisc_deteccion_alt_menor_2m_2aDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Hisc_deteccion_alt_menor_2m_2a;

import java.util.List;
import java.util.Map;

public interface Hisc_deteccion_alt_menor_2m_2aDao {

	void crear(Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a); 

	int actualizar(Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a); 

	Hisc_deteccion_alt_menor_2m_2a consultar(Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a); 

	int eliminar(Hisc_deteccion_alt_menor_2m_2a hisc_deteccion_alt_menor_2m_2a); 

	List<Hisc_deteccion_alt_menor_2m_2a> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

	Integer total_registros(Map<String, Object> parameters);
}