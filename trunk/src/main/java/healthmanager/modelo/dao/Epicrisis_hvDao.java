/*
 * Epicrisis_hvDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Epicrisis_hv;

public interface Epicrisis_hvDao {

	void crear(Epicrisis_hv epicrisis_hv); 

	int actualizar(Epicrisis_hv epicrisis_hv); 

	Epicrisis_hv consultar(Epicrisis_hv epicrisis_hv); 

	int eliminar(Epicrisis_hv epicrisis_hv); 

	List<Epicrisis_hv> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}