/*
 * Egreso_urgenciasDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Egreso_urgencias;

public interface Egreso_urgenciasDao {

	void crear(Egreso_urgencias egreso_urgencias); 

	int actualizar(Egreso_urgencias egreso_urgencias); 

	Egreso_urgencias consultar(Egreso_urgencias egreso_urgencias); 

	int eliminar(Egreso_urgencias egreso_urgencias); 

	List<Egreso_urgencias> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}