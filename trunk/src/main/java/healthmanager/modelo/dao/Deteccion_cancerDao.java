/*
 * Deteccion_cancerDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Deteccion_cancer;

public interface Deteccion_cancerDao {

	void crear(Deteccion_cancer deteccion_cancer); 

	int actualizar(Deteccion_cancer deteccion_cancer); 

	Deteccion_cancer consultar(Deteccion_cancer deteccion_cancer); 

	int eliminar(Deteccion_cancer deteccion_cancer); 

	List<Deteccion_cancer> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}