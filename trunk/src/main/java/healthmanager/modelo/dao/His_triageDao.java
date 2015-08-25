/*
 * His_triageDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.His_triage;

public interface His_triageDao {

	void crear(His_triage his_triage); 

	int actualizar(His_triage his_triage); 

	His_triage consultar(His_triage his_triage); 
	
	His_triage consultar_historia(His_triage his_triage); 

	int eliminar(His_triage his_triage); 

	List<His_triage> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}