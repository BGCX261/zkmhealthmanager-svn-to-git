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

import healthmanager.modelo.bean.Via_ingreso_contratadas;

import java.util.List;
import java.util.Map;

public interface Via_ingreso_contratadasDao {

	void crear(Via_ingreso_contratadas via_ingreso_contratadas); 

	int actualizar(Via_ingreso_contratadas via_ingreso_contratadas); 

	Via_ingreso_contratadas consultar(Via_ingreso_contratadas via_ingreso_contratadas); 
	
	Via_ingreso_contratadas consultar_informacion(Via_ingreso_contratadas via_ingreso_contratadas); 

	int eliminar(Via_ingreso_contratadas via_ingreso_contratadas); 
	
	int eliminar_contrato(Via_ingreso_contratadas via_ingreso_contratadas); 

	List<Via_ingreso_contratadas> listar(Map<String,Object> parameters); 
	
	List<Map<String, Object>> verificarDuplicados();

	boolean existe(Map<String,Object> parameters); 

}