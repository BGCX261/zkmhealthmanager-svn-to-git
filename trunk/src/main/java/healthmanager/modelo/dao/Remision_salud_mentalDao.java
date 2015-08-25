/*
 * Remision_salud_mentalDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Remision_salud_mental;

public interface Remision_salud_mentalDao {

	void crear(Remision_salud_mental remision_salud_mental); 

	int actualizar(Remision_salud_mental remision_salud_mental); 

	Remision_salud_mental consultar(Remision_salud_mental remision_salud_mental); 

	int eliminar(Remision_salud_mental remision_salud_mental); 

	List<Remision_salud_mental> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}