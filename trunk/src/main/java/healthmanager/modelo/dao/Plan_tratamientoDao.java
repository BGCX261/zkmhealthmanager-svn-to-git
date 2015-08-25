/*
 * Plan_tratamientoDao.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Plan_tratamiento;

public interface Plan_tratamientoDao {

	void crear(Plan_tratamiento plan_tratamiento); 

	int actualizar(Plan_tratamiento plan_tratamiento); 

	Plan_tratamiento consultar(Plan_tratamiento plan_tratamiento); 

	int eliminar(Plan_tratamiento plan_tratamiento); 

	List<Plan_tratamiento> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 
	
	boolean planTratamientoPendiente(Map<String,Object> param);
}