/*
 * Riesgo_intervencionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Riesgo_intervencion;

public interface Riesgo_intervencionDao {

	void crear(Riesgo_intervencion riesgo_intervencion); 

	int actualizar(Riesgo_intervencion riesgo_intervencion); 

	Riesgo_intervencion consultar(Riesgo_intervencion riesgo_intervencion); 

	int eliminar(Riesgo_intervencion riesgo_intervencion); 

	List<Riesgo_intervencion> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}