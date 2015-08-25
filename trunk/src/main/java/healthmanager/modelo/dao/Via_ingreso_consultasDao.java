/*
 * Via_ingreso_consultasDao.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Via_ingreso_consultas;

public interface Via_ingreso_consultasDao {

	void crear(Via_ingreso_consultas via_ingreso_consultas); 

	int actualizar(Via_ingreso_consultas via_ingreso_consultas); 

	Via_ingreso_consultas consultar(Via_ingreso_consultas via_ingreso_consultas); 

	int eliminar(Via_ingreso_consultas via_ingreso_consultas); 

	List<Via_ingreso_consultas> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}