/*
 * Citas_webDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Citas_web;

public interface Citas_webDao {

	void crear(Citas_web citas_web); 

	int actualizar(Citas_web citas_web); 

	Citas_web consultar(Citas_web citas_web); 

	int eliminar(Citas_web citas_web); 

	List<Citas_web> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	int obtenerCitasAseguradora(Map<String,Object> params);

}