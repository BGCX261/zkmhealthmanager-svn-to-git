/*
 * Hisc_recien_nacidoDao.java
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

import healthmanager.modelo.bean.Hisc_recien_nacido;

public interface Hisc_recien_nacidoDao {

	void crear(Hisc_recien_nacido hisc_recien_nacido); 

	int actualizar(Hisc_recien_nacido hisc_recien_nacido); 

	Hisc_recien_nacido consultar(Hisc_recien_nacido hisc_recien_nacido); 

	int eliminar(Hisc_recien_nacido hisc_recien_nacido); 

	List<Hisc_recien_nacido> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	Integer total_registros(Map<String, Object> parameters);

}